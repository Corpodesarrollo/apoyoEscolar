package siges.adminParamsInst.thread;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.apache.commons.validator.GenericValidator;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.io.MailIO;
import siges.adminParamsInst.vo.MailVO;
import siges.adminParamsInst.vo.ParamsVO;
import siges.adminParamsInst.vo.PeriodoPrgfechasVO;
import siges.gestionAdministrativa.enviarMensajes.dao.EnviarMensajesDAO;
import siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO;
import siges.gestionAdministrativa.enviarMensajes.vo.PersonalVO;

/**
 * Clase hilo que se encarga de procesar los funciones de cierre Periodo,
 * actualizar Mensajes y envio de correos
 * 
 * @date 29/09/2010 <BR>
 * @author Athenea <BR>
 * @version v 1.0 <BR>
 */

public class HiloTareasMsjPerPrg extends Thread {

	private static boolean ocupado = false;
	private ResourceBundle rb;
	private AdminParametroInstDAO adminParametroInstDAO = null;
	private int dormir = 0;
	private static Logger logger = Logger.getLogger(HiloTareasMsjPerPrg.class
			.getName());

	/**
	 * @param cursorString
	 * @param contexto
	 */
	public HiloTareasMsjPerPrg(AdminParametroInstDAO adminParametroInstDAO) {
		super("PER_PROG_FECHAS");
		this.setName("PER_PROG_FECHAS");
		this.adminParametroInstDAO = adminParametroInstDAO;
		rb = ResourceBundle
				.getBundle("siges.adminParamsInst.bundle.AdminParamsInst");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {

			dormir = Integer.parseInt(rb.getString("hiloPerProgFecha.dormir"));
			Thread.sleep(dormir);
			while (ocupado) {
				logger.fine(this.getName() + ":Espera Thread");
				sleep(dormir);
			}
			ocupado = true;
			logger.fine(this.getName() + ":Entra Thread");
			while (true) {

				synchronized (HiloTareasMsjPerPrg.class) {
					// preguntar por la hora
					Calendar fechaActual = Calendar.getInstance();
					int horaActual = fechaActual.get(Calendar.HOUR_OF_DAY);

					// System.out.println(new Date() +
					// " . PER_PROG_FECHAS:  horaActual " + horaActual);

					/*
					 * Validar si los parametros traidos de la BD son correctos,
					 * si no colocar hora por defecto
					 * "PeriodoPrgfechasVO.DIA_HORA"
					 */

					String horaProcesar[] = adminParametroInstDAO.getHorasTipo(
							6).split("\\,");
					if (horaProcesar == null || horaProcesar.length == 0
							|| !GenericValidator.isInt(horaProcesar[0])) {
						horaProcesar = new String[1];
						horaProcesar[0] = "" + PeriodoPrgfechasVO.DIA_HORA;
					}

					// System.out.println("horaProcesar " + horaProcesar[0]);
					/*
					 * Actualizar y realizar cierre de periodos
					 */
					if (isValidarHora(horaActual + "", horaProcesar)) {
						realizarCierrePeriodo();
						actualizarMensajes();
					}

					/*
					 * Validar si los parametros traidos de la BD son correctos,
					 * si no colocar horas por defecto
					 */
					String horaProcesarMail[] = adminParametroInstDAO
							.getHorasTipo(7).split("\\,");
					if (horaProcesarMail == null
							|| horaProcesarMail.length == 0
							|| !GenericValidator.isLong(horaProcesarMail[0])) {
						horaProcesarMail = new String[1];
						horaProcesarMail[0] = "9";
						horaProcesarMail[1] = "14";
					}
					/*
					 * Envio de mensaje correo
					 */
					// System.out.println("horaProcesarMail[0] " +
					// horaProcesarMail[0]);
					if (isValidarHora(horaActual + "", horaProcesarMail)) {
						actualizarMensajes();
						enviarCorreos();
					}
				}
			}
		} catch (InterruptedException ex) {
			logger.severe(new Date()
					+ " - PER_PROG_FECHAS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO "
					+ ex);
			ex.printStackTrace();
			// limpiarTablas(consecBol);
		} catch (Exception ex) {
			logger.severe(new Date()
					+ " - PER_PROG_FECHAS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO "
					+ ex);
			ex.printStackTrace();
			// limpiarTablas(consecBol);
		} finally {
			ocupado = false;
		}
	}

	/**
	 * @return
	 */
	public boolean realizarCierrePeriodo() {
		// System.out.println(new Date() + " realizarCierrePeriodo");

		try {

			/*
			 * 1. Obtener los registro de los periodos que sernn cerrados
			 */
			List listRegistro = new ArrayList();
			List listRegTodo = adminParametroInstDAO
					.getListaPredProgFechasCierre();
			// System.out.println("listRegTodo " + listRegTodo.size() );

			/*
			 * 2. Validar que el periodo este cerrado, si no esta cerrado lo
			 * adicionamos a la lista listRegistro
			 */
			for (int i = 0; i < listRegTodo.size(); i++) {
				PeriodoPrgfechasVO pVO = (PeriodoPrgfechasVO) listRegTodo
						.get(i);
				pVO = adminParametroInstDAO.getPerProgFechas(pVO);

				/*
				 * Validar si el periodo ya esta cerrado
				 */
				if (isValidarCerrado(pVO, pVO.getPrf_periodo())) {
					listRegistro.add(pVO);
				}
			}
			// System.out.println("listRegistro " + listRegistro.size() );

			/*
			 * 2. Registrarlos en DATOS_HILOS estado -99 significa que no existe
			 */

			for (int i = 0; i < listRegistro.size(); i++) {
				PeriodoPrgfechasVO pVO = (PeriodoPrgfechasVO) listRegistro
						.get(i);

				int estadoActual = adminParametroInstDAO.isValidarHiloPerPrg(
						pVO, ParamsVO.ESTADO_REPORTE_EJE, "-");
				if (estadoActual == -99) {
					adminParametroInstDAO.registrarHilosPerprgF(pVO,
							ParamsVO.HILO_TIPO_PER_PROG_FECHAS);
				} else {
					adminParametroInstDAO.updateHiloPerPrg(pVO, estadoActual,
							ParamsVO.ESTADO_REPORTE_COLA, "-");
				}
			}

			Iterator reps = listRegistro.iterator();
			if (listRegistro != null && listRegistro.size() > 0) {

				logger.fine("PER_PROG_FECHAS: SI HAY REG PARA REALIZAR CIERRE DE PERIODO: CANTIDAD  "
						+ listRegistro.size());
				while (reps.hasNext()) {
					PeriodoPrgfechasVO p = (PeriodoPrgfechasVO) reps.next();
					// System.out.println("PROCESAR " + p.getPrfcodinst() );
					try {
						adminParametroInstDAO.updateHiloPerPrg(p,
								ParamsVO.ESTADO_REPORTE_COLA,
								ParamsVO.ESTADO_REPORTE_EJE, "-");
						adminParametroInstDAO.cerrarPeriodoTotal(p);
						adminParametroInstDAO.deleteHiloPerPrg(p);

					} catch (Exception e) {
						System.out.println("ERROR");
						e.printStackTrace();
						adminParametroInstDAO.updateHiloPerPrg(p,
								ParamsVO.ESTADO_REPORTE_EJE,
								ParamsVO.ESTADO_REPORTE_NOGEN,
								"Error: " + e.getMessage());
					}
					logger.fine("PER_PROG_FECHAS: " + getName()
							+ ":Sale While PER_PROG_FECHAS");
				}
			} else {
				Thread.sleep(dormir);
				System.out.print("*");
			}

			return true;
		} catch (InterruptedException ex) {
			logger.severe("PER_PROG_FECHAS: InterruptedException PROCESAR SOLICITUDES FALLO.");
			ex.printStackTrace();
			return false;
		} catch (SQLException ex) {
			logger.severe("PER_PROG_FECHAS: SQLException PROCESAR SOLICITUDES FALLO.");
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			logger.severe(" - PER_PROG_FECHAS: Exception EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO "
					+ ex);
			ex.printStackTrace();
			return false;
		} finally {
			ocupado = false;
		}
	}

	/**
	 * Obtiene los la informacion de los periodos programados de forma autmntica
	 * y actualiza los mensajes de alerta
	 * 
	 * @return
	 */
	public boolean actualizarMensajes() {
		// System.out.println(new Date()+ " actualizarMensajes");
		PeriodoPrgfechasVO pVO = null;
		try {
			List listRegistro = adminParametroInstDAO.getListaPredProgFechas();
			// System.out.println("en actuALIZAR MENS " + listRegistro.size() );

			Iterator reps = listRegistro.iterator();
			if (listRegistro != null && listRegistro.size() > 0) {
				while (reps.hasNext()) {
					pVO = (PeriodoPrgfechasVO) reps.next();

					/*
					 * 2. Actualizar el mensaje Nota: Cuando a transcurrido un
					 * dia el mensaje debe actualizar el texto que hace
					 * referencia a los dias que falta u horas.
					 */
					EnviarMensajesDAO enviarMensajesDAO = new EnviarMensajesDAO(
							adminParametroInstDAO.getCursor());
					MensajesVO mVO = null;

					/*
					 * Crear la instancia de MensajesVO para registrar los
					 * mensajes alerta correspondientes
					 */
					mVO = new MensajesVO();
					mVO.setMsjenviadopor(ParamsVO.ENV_SISTEM);
					mVO.setMsjenviadoacoleg("" + pVO.getPrfcodinst());
					mVO.setMsjenviadoaperfil(adminParametroInstDAO
							.getListaPerfiles());
					mVO.setMsjusuario("-999");

					configurarMensaje(enviarMensajesDAO, pVO,
							pVO.getPrf_alertap1(), 1, pVO.getPrf_diasp1(),
							pVO.getPrfvigencia(), mVO, pVO.getPrf_ini_per1(),
							pVO.getPrf_fin_per1());
					configurarMensaje(enviarMensajesDAO, pVO,
							pVO.getPrf_alertap2(), 2, pVO.getPrf_diasp2(),
							pVO.getPrfvigencia(), mVO, pVO.getPrf_ini_per2(),
							pVO.getPrf_fin_per2());
					configurarMensaje(enviarMensajesDAO, pVO,
							pVO.getPrf_alertap3(), 3, pVO.getPrf_diasp3(),
							pVO.getPrfvigencia(), mVO, pVO.getPrf_ini_per3(),
							pVO.getPrf_fin_per3());
					configurarMensaje(enviarMensajesDAO, pVO,
							pVO.getPrf_alertap4(), 4, pVO.getPrf_diasp4(),
							pVO.getPrfvigencia(), mVO, pVO.getPrf_ini_per4(),
							pVO.getPrf_fin_per4());
					configurarMensaje(enviarMensajesDAO, pVO,
							pVO.getPrf_alertap5(), 5, pVO.getPrf_diasp5(),
							pVO.getPrfvigencia(), mVO, pVO.getPrf_ini_per5(),
							pVO.getPrf_fin_per5());
					configurarMensaje(enviarMensajesDAO, pVO,
							pVO.getPrf_alertap6(), 6, pVO.getPrf_diasp6(),
							pVO.getPrfvigencia(), mVO, pVO.getPrf_ini_per6(),
							pVO.getPrf_fin_per6());
					configurarMensaje(enviarMensajesDAO, pVO,
							pVO.getPrf_alertap7(), 7, pVO.getPrf_diasp7(),
							pVO.getPrfvigencia(), mVO, pVO.getPrf_ini_per7(),
							pVO.getPrf_fin_per7());
				}
			} else {
				Thread.sleep(dormir);
				// System.out.print("*");
			}
			return true;
		} catch (InterruptedException ex) {
			logger.severe("PER_PROG_FECHAS: InterruptedException PROCESAR SOLICITUDES FALLO.");
			ex.printStackTrace();
			return false;
		} catch (SQLException ex) {
			logger.severe("PER_PROG_FECHAS: SQLException PROCESAR SOLICITUDES FALLO.");
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			logger.severe(" - PER_PROG_FECHAS: Exception EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO "
					+ ex);
			ex.printStackTrace();
			return false;
		} finally {
			ocupado = false;
		}
	}

	/**
	 * Se encarga de crear los mensajes o alertas segnn lo configurado por el
	 * usuario en el formulario de promagracinn de periodo.
	 * 
	 * @param enviarMensajesDAO
	 * @param bandAlert
	 * @param perd
	 * @param diaMsj
	 * @param vig
	 * @param msg
	 * @param fechIni
	 * @param fechFin
	 * @throws Exception
	 */

	private void configurarMensaje(EnviarMensajesDAO enviarMensajesDAO,
			PeriodoPrgfechasVO pVO, int bandAlert, int perd, int diaMsj,
			int vig, MensajesVO msj, String fechIni, String fechFin)
			throws Exception {

		StringBuffer sbAsunto = new StringBuffer();
		StringBuffer sbContenido = new StringBuffer();
		int horaCierre_ = 0;

		msj.setMsjfechaini(fechIni);
		msj.setMsjfechafin(fechFin);

		sbAsunto.append(MensajesVO.MSJ_ASUNTO);
		sbAsunto.append(perd);
		sbAsunto.append(MensajesVO.MSJ_RAYITA);
		sbAsunto.append(vig);

		msj.setMsjasunto(sbAsunto.toString());

		if (msj.getMsjfechafin() != null) {

			// Calcular los datos referentes a la fecha
			String str[] = msj.getMsjfechafin().split("\\/");
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaFinal = new GregorianCalendar(
					Integer.parseInt(str[2]), Integer.parseInt(str[1]) - 1,
					Integer.parseInt(str[0]));

			int dias = fechaFinal.get(Calendar.DAY_OF_YEAR)
					- fechaActual.get(Calendar.DAY_OF_YEAR);
			// int horas = PeriodoPrgfechasVO.DIA_HORA -
			// fechaActual.get(Calendar.HOUR_OF_DAY);
			String horaProcesar[] = adminParametroInstDAO.getHorasTipo(6)
					.split("\\,");
			if (horaProcesar != null && horaProcesar.length > 0
					&& GenericValidator.isInt(horaProcesar[0])) {
				horaCierre_ = Integer.parseInt(horaProcesar[0]);
			} else {
				horaCierre_ = PeriodoPrgfechasVO.DIA_HORA;
			}

			// System.out.println("horaCierre_  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  "
			// + horaCierre_);

			int horas = horaCierre_ - fechaActual.get(Calendar.HOUR_OF_DAY);

			/*
			 * Si checkearon mostrar alerta; configura rmensaje si no, borrar si
			 * ya existe el mensaje
			 */
			if (bandAlert == 1 && dias <= diaMsj && isValidarCerrado(pVO, perd)) {
				/***/
				/*
				 * Si el resultado de restar las dos fecha (fechaActual y
				 * fechaFinal para el cierre automatico) es menor a los dias
				 * registrado en para mostrar el mensaje de alerta, entoces
				 * Insertar un registro en la tabla MENSAJES
				 */

				sbContenido.append(MensajesVO.MSJ_CONTENIDO_01);
				sbContenido.append(perd);
				sbContenido.append(MensajesVO.MSJ_RAYITA);
				sbContenido.append(vig);
				sbContenido.append(MensajesVO.MSJ_CONTENIDO_02);
				sbContenido.append(adminParametroInstDAO.getNombreInst(pVO
						.getPrfcodinst()));

				if (dias > 0) {
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_1_03);
					sbContenido.append(fechFin);
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_1_04);
					sbContenido.append(dias + "");
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_1_05);
				} else {

					sbContenido.append(MensajesVO.MSJ_CONTENIDO_2_03);
					sbContenido.append(MensajesVO.MSJ_ABRE_PARENTESIS);
					sbContenido.append(fechFin);
					sbContenido.append(MensajesVO.MSJ_CIERRE_PARENTESIS);
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_2_04);
					sbContenido.append(horas);
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_2_05);
				}// fin de if if(dias > 0){
				msj.setMsjcontenido(sbContenido.toString());

				/*
				 * Si el mensaje ann no a sido registro, lo insertamos en la
				 * tabla MENSAJES, si no lo actualizamos.
				 */
				if (enviarMensajesDAO.isValidaExisteMsg(msj) < 1) {
					enviarMensajesDAO.guardarMensajes(msj);
				} else {
					msj.setMsjcodigo(enviarMensajesDAO.isValidaExisteMsg(msj));

					/*
					 * Valida si el mensaje ya fue actualizado por el sistema,
					 * es decir si los dos mensajes son identicos no es
					 * necesario actualizar el mensaje.
					 */
					if (!enviarMensajesDAO.obtenerMensajes(msj.getMsjcodigo())
							.equals(msj)) {

						/*
						 * Actualiza el mensaje
						 */
						enviarMensajesDAO.updateMensajes(msj);
					}
				}// fin if if( enviarMensajesDAO.isValidaExisteMsg(msg)< 1 ){

				/***/

			} else {
				/*
				 * Si existe el mensaje se debe eliminar
				 */
				if (!(enviarMensajesDAO.isValidaExisteMsg(msj) < 1)) {
					msj.setMsjcodigo(enviarMensajesDAO.isValidaExisteMsg(msj));
					enviarMensajesDAO.deleteMensaje(msj.getMsjcodigo());
				}
			}// fin if(bandAlert == 1 && dias <= diaMsj ){
		}

	}

	/**
	 * Valida si el periodo esta cerrado o abierto
	 * 
	 * @param pVO
	 * @param perd
	 * @return
	 * @throws Exception
	 */
	private boolean isValidarCerrado(PeriodoPrgfechasVO pVO, int perd)
			throws Exception {
		switch (perd) {
		case 1:
			if (pVO.getPerestado1() != 1)
				return true;
			break;
		case 2:
			if (pVO.getPerestado2() != 1)
				return true;
			break;
		case 3:
			if (pVO.getPerestado3() != 1)
				return true;
			break;
		case 4:
			if (pVO.getPerestado4() != 1)
				return true;
			break;
		case 5:
			if (pVO.getPerestado5() != 1)
				return true;
			break;
		case 6:
			if (pVO.getPerestado6() != 1)
				return true;
			break;
		case 7:
			if (pVO.getPerestado7() != 1)
				return true;
			break;
		}
		return false;
	}

	/**
	 * @param hora
	 * @param horas
	 * @return
	 */
	private boolean isValidarHora(String hora, String horas[]) {
		for (int i = 0; i < horas.length; i++) {
			// System.out.println("horas[i] " + horas[i]);
			// System.out.println("hora " + hora);
			if (horas[i].equals(hora)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @throws Exception
	 */
	private void enviarCorreos() {
		// System.out.println("enviarCorreos " );

		try {

			List listaErrores = new ArrayList();
			Map p = this.adminParametroInstDAO.getMailParams();

			List listaCorreo = new ArrayList();

			/*
			 * Obtener listado de mensajes para ser enviados
			 */
			List listaMensaje = this.adminParametroInstDAO.getListaMensaje();
			// System.out.println("listaMensaje " + listaMensaje.size() );

			for (int j = 0; j < listaMensaje.size(); j++) {
				MensajesVO msjVO = (MensajesVO) listaMensaje.get(j);
				this.adminParametroInstDAO.updateEstadoMsj(msjVO,
						MensajesVO.EMAIL_ENVIANDO);
				MailIO mIO = new MailIO();
				MailVO mailVO = new MailVO();
				mailVO.setMailParams(p);
				mailVO.setMailAsunto(msjVO.getMsjasunto());
				mailVO.setMailMensaje(msjVO.getMsjcontenido());
				mailVO.setMailBandera(1);

				String codPefiles = msjVO.getMsjenviadoaperfil();
				String codLocals = msjVO.getMsjenviadoalocal();
				String codInsts = msjVO.getMsjenviadoacoleg();
				String codSedes = msjVO.getMsjenviadoasede();
				String codJords = msjVO.getMsjenviadoajorn();

				List listaPersonas = this.adminParametroInstDAO
						.getListaPersona(codPefiles, codLocals, codInsts,
								codSedes, codJords);
				for (int i = 0; i < listaPersonas.size(); i++) {
					PersonalVO personaVO = (PersonalVO) listaPersonas.get(i);
					if (personaVO.getPeremail() != null
							&& GenericValidator
									.isEmail(personaVO.getPeremail())) {
						listaCorreo.add(personaVO.getPeremail());
					} else {
						StringBuffer msjError = new StringBuffer();
						msjError.append("No se envio correo a la persona");
						msjError.append(personaVO.getPernombre1());
						msjError.append("\n cedula ");
						msjError.append(personaVO.getPernumdocum());
						listaErrores.add(msjError.toString());
					}

					if (i % 10 == 0) {
						mailVO.setMailCorreos(listaCorreo);
						mailVO.setMailBandera(0);
						mIO.enviarCorreo(mailVO);
						listaCorreo = new ArrayList();
					}
				} // for personas

				if (listaCorreo.size() > 0) {
					mailVO.setMailCorreos(listaCorreo);
					mailVO.setMailBandera(0);
					mIO.enviarCorreo(mailVO);
					listaCorreo = new ArrayList();

				}
				this.adminParametroInstDAO.updateEstadoMsj(msjVO,
						MensajesVO.EMAIL_ENVIADO);

				// CAMBIAR ESTADO MENSAJE
				// this.adminParametroInstDAO.updateEstadoMsj(msjVO,
				// ParamsVO.C);
			}// for mensajes

		} catch (Exception e) {
			System.out.println();
			logger.severe("Error en hilo enviar correos" + e);
			e.printStackTrace();
		}
	}

}
