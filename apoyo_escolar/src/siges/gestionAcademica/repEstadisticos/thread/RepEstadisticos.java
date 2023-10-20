package siges.gestionAcademica.repEstadisticos.thread;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import siges.boletines.ControllerAuditoriaReporte;
import siges.dao.Cursor;
import siges.exceptions.InternalErrorException;
import siges.gestionAcademica.repEstadisticos.dao.RepEstadisticosDAO;
import siges.gestionAcademica.repEstadisticos.io.RepEstadisticosIO;
import siges.gestionAcademica.repEstadisticos.vo.DatosBoletinVO;
import siges.gestionAcademica.repEstadisticos.vo.FiltroRepEstadisticoVO;
import siges.gestionAcademica.repEstadisticos.vo.ParamsVO;
import siges.gestionAcademica.repEstadisticos.vo.ReporteEstadisticoVO;
import siges.gestionAcademica.repEstadisticos.vo.ResultadoConsultaVO;

/**
 * Nombre: Boletin<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class RepEstadisticos extends Thread {

	private static boolean ocupado = false;
	private Cursor cursor = null;
	private Thread t = null;
	private String mensaje = null;
	private ResourceBundle rPath;
	private ResourceBundle rbEstdc;

	private java.sql.Timestamp f2 = null;
	private String context = null;
	private String vigencia = null;
	private long consecRep = 0;
	private RepEstadisticosDAO repEstadisticosDAO = null;
	private int dormir = 0;
	private Logger logger = Logger.getLogger(RepEstadisticos.class.getName());
	ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();

	/**
	 * @param cursorString
	 * @param contexto
	 */
	public RepEstadisticos(RepEstadisticosDAO repEstadisticosDAO,
			String contexto) {
		super("HILO_REP_ESTADISTICOS");

		this.setName("HILO_REP_ESTADISTICOS");
		this.context = contexto;
		this.rPath = ResourceBundle.getBundle("path");
		this.rbEstdc = ResourceBundle
				.getBundle("siges.gestionAcademica.repEstadisticos.bundle.repEstadisticos");
		this.repEstadisticosDAO = repEstadisticosDAO;
	}

	public void run() {

		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rbEstdc.getString("estadisticos.Dormir"));

			while (ocupado) {
				logger.fine(this.getName() + ":Espera Thread");
				sleep(dormir);
			}

			ocupado = true;
			logger.fine(this.getName() + ":Entra Thread");
			while (true) {
				try {
					procesar_solicitudes();
				} catch (Exception e) {
					auditoriaReporte.insertarAuditoria("REP_ESTADISTICOS", 0, "HILO REP ESTADISTICOS.: EXECPCION WHILE INTERNO: "+e.getMessage(),0L);
				}
			}
		} catch (InterruptedException ex) {
			logger.fine(new Date()+ " - REP ESTADISTICOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			auditoriaReporte.insertarAuditoria("REP_ESTADISTICOS", 0, "HILO REP ESTADISTICOS.: EXECPCION WHILE InterruptedException: "+ex.getMessage(),0L);
			ex.printStackTrace();
			// limpiarTablas(consecBol);
		} catch (Exception ex) {
			logger.fine(new Date()+ " - REP ESTADISTICOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			auditoriaReporte.insertarAuditoria("REP_ESTADISTICOS", 0, "HILO REP ESTADISTICOS.: EXECPCION WHILE Exception: "+ex.getMessage(),0L);
			ex.printStackTrace();
			// limpiarTablas(consecBol);
		} finally {
			ocupado = false;
		}
	}

	/**
	 * @return
	 */
	public boolean procesar_solicitudes() {
		ReporteEstadisticoVO reporte = null;
		DatosBoletinVO daBolVO = null;

		try {
			List reportes = reportes_Generar();
			Iterator reps = reportes.iterator();
			if (reportes != null && reportes.size() > 0) {

				logger.fine("REP ESTADISTICOS: SI HAY REPORTES POR GENERAR: CANTIDAD  "
						+ reportes.size());
				while (reps.hasNext()) {
					daBolVO = (DatosBoletinVO) reps.next();
					reporte = null;
					logger.fine("REP ESTADISTICOS: REPORTE A GENERAR CONSEC: "
							+ daBolVO.getDABOLCONSEC());
					System.out
							.println("REP ESTADISTICOS: REPORTE A GENERAR CONSEC: "
									+ daBolVO.getDABOLCONSEC());
					reporte = setReporte(daBolVO);

					if (!procesar(daBolVO, reporte)) {

						logger.fine("REP ESTADISTICOS: PROCESAR SOLICITUDES FALLO.");
						this.vigencia = repEstadisticosDAO
								.getVigenciaInst(daBolVO.getDABOLINST()) + "";
						daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());

						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
						repEstadisticosDAO.updateSolicitud(daBolVO);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
						repEstadisticosDAO.ponerReporteMensaje(reporte);
						repEstadisticosDAO.limpiarTablas(daBolVO
								.getDABOLCONSEC());
						continue;
					}

					logger.fine("TERMINO PROCES , ENTRO ACTUALIZAR PUESTO");
					// Actualizar el puesto de los demas reportes que estan en
					// cola
					reporte.setPuesto(-999);
					if (!repEstadisticosDAO.updatePuestoBoletin(reporte)) {
						logger.fine(" **NO Se actualizn el puesto del reporte Estadisticas**");

					}
					// Actualizar el puesto de los demas reportes que estan en
					// cola
					if (!repEstadisticosDAO.update_cola_boletines()) {
						logger.fine(" **NO Se actualizn el puesto del reporte Estadisticas**");

					}

					logger.fine("REP ESTADISTICOS: " + getName()
							+ ":Sale While reportes Estadisticos");
				}
			} else {
				Thread.sleep(dormir);
				// System.out.print("*");
			}
			return true;
		} catch (InterruptedException ex) {
			logger.fine(new Date()
					+ " - REP ESTADISTICOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				logger.fine("REP ESTADISTICOS: PROCESAR SOLICITUDES FALLO.");
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				daBolVO.setDABOLESTACT(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		} catch (SQLException ex) {
			logger.fine(new Date()
					+ " - REP ESTADISTICOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				logger.fine("REP ESTADISTICOS: PROCESAR SOLICITUDES FALLO.");
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} catch (Exception ex) {
			logger.fine(new Date()
					+ " - REP ESTADISTICOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				logger.fine("REP ESTADISTICOS: PROCESAR SOLICITUDES FALLO.");
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} finally {
			ocupado = false;
		}
	}

	public List reportes_Generar() {
		int posicion = 1;
		String[][] array1 = null;
		// FiltroReportesVO rep=null;
		List list = new ArrayList();
		try {
			list = repEstadisticosDAO.getSolicitudes();
			return list;
		} catch (Exception e) {
			logger.fine("REP ESTADISTICOS: ERROR LISTADO REPORTES A GENERAR, MOTIVO: "
					+ e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
			} catch (Exception e) {
			}
		}

	}

	public boolean procesar(DatosBoletinVO daBolVO, ReporteEstadisticoVO reporte) {

		try {

			repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			daBolVO.setDABOLFECHA(new java.sql.Timestamp(System
					.currentTimeMillis()).toString());
			// daBolVO.setDABOLFECHAFIN("");

			reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
			daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_EJE);
			repEstadisticosDAO.updateSolicitud(daBolVO);
			reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: "
					+ daBolVO.getDABOLCONSEC());
			repEstadisticosDAO.ponerReporteMensaje(reporte);

			if (daBolVO.getDABOLCONSEC() > 0) {
				logger.fine("REP ESTADISTICOS: ENTRO A GENERAR REPORTE. TIPO: "
						+ daBolVO.getDABOLTIPOREP());

				// LLAMADO AL PROCEDIMIENTO
				if (!repEstadisticosDAO.llenarTablas(daBolVO, reporte)) {
					logger.fine("REP ESTADISTICOS: ERROR  consecutivo: "
							+ daBolVO.getDABOLCONSEC());
					// errorReporte(daBolVO,reporte,"REP ESTADISTICOS: ERROR PROCEDIMIENTO BOLETIN consecutivo: "
					// + daBolVO.getDABOLCONSEC());
					return true;
				}

				// VALIDAR SI NO HUBO ERROR EN EL PROC
				if (repEstadisticosDAO.validarEstadoReporte(daBolVO
						.getDABOLCONSEC()) == ParamsVO.ESTADO_REPORTE_NOGEN) {
					logger.fine("REP ESTADISTICOS: INCOSISTENCIA  consecutivo: "
							+ daBolVO.getDABOLCONSEC());
					// errorReporte(daBolVO,reporte,"REP ESTADISTICOS: INCOSISTENCIA EN PROCEDIMIENTO BOLETIN consecutivo:"
					// + daBolVO.getDABOLCONSEC());
					return true;
				}

				// Validar que si hay datos
				if (repEstadisticosDAO.validarDatosReporte(daBolVO)) {

					// Consulta para traer convensiones
					daBolVO = repEstadisticosDAO.datosConv(daBolVO, reporte);

					// Armar reporte
					RepEstadisticosIO repEstadisticosIO = new RepEstadisticosIO(
							repEstadisticosDAO);
					ResultadoConsultaVO resultadoConsultaVO = new ResultadoConsultaVO();
					resultadoConsultaVO = repEstadisticosIO.generarRep(daBolVO,
							reporte, context, daBolVO.getDABOLTIPOREP());

					logger.fine("resultadoConsultaVO.isGenerado() "
							+ resultadoConsultaVO.isGenerado());
					System.out.println("resultadoConsultaVO.isGenerado() "
							+ resultadoConsultaVO.isGenerado());
					// Valida si se genero
					if (resultadoConsultaVO.isGenerado()) {
						daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
						daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_GENOK);
						repEstadisticosDAO.updateSolicitud(daBolVO);
						reporte.setMensaje("REPORTE GENERADO SATISFACTORIAMENTE");
						repEstadisticosDAO.ponerReporteMensaje(reporte);
						repEstadisticosDAO.limpiarTablas(daBolVO
								.getDABOLCONSEC());
						return true;
					} else {
						daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
						repEstadisticosDAO.updateSolicitud(daBolVO);
						reporte.setMensaje("Error al generar reportes jasper, "
								+ resultadoConsultaVO.getMensaje());
						repEstadisticosDAO.ponerReporteMensaje(reporte);
						repEstadisticosDAO.limpiarTablas(daBolVO
								.getDABOLCONSEC());

					}
				}// fin de rs
				else {
					daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
							.currentTimeMillis()).toString());
					daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
					repEstadisticosDAO.updateSolicitud(daBolVO);
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
					reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
					repEstadisticosDAO.ponerReporteMensaje(reporte);
					repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());

				}
				return true;
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			try {
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		} catch (JRException e) {
			e.printStackTrace();
			try {
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			logger.fine(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
					+ e.toString());
			try {
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, ERROR MEMORIA, EXCEPCION: "
						+ e.getMessage());
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			try {
				daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
				repEstadisticosDAO.updateSolicitud(daBolVO);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				repEstadisticosDAO.ponerReporteMensaje(reporte);
				repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e) {
			}
		}
		return true;
	}// Fin de Run

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/

	public ReporteEstadisticoVO setReporte(DatosBoletinVO daBolVO) {
		ReporteEstadisticoVO reporte = new ReporteEstadisticoVO();
		if (daBolVO != null) {
			reporte.setNombre(daBolVO.getDABOLNOMBREZIP());
			reporte.setNombre_zip(daBolVO.getDABOLNOMBREZIP());
			reporte.setModulo(ParamsVO.MODULO_RepEstadisticos);
			reporte.setFecha(daBolVO.getDABOLFECHA());
			reporte.setTipo(ParamsVO.ARCHIVO_TIPO_ZIP);
			reporte.setUsuario(daBolVO.getDABOLUSUARIO());
			reporte.setRecurso(daBolVO.getDABOLNOMBREZIP());
			reporte.setConsec(daBolVO.getDABOLCONSEC());
		}
		return reporte;
	}

	public Map setParametros(FiltroRepEstadisticoVO rep) {
		Map parametros = new HashMap();
		if (rep != null) {
			/*
			 * parametros=new HashMap();
			 * parametros.put("usuario",daBolVO.getUsuario());
			 * parametros.put("ESCUDO_SED", path_img +
			 * rbEstdc.getString("imagen")); parametros.put("PERIODO",new
			 * Integer((int)daBolVO.getFilperd()));
			 * parametros.put("VIGENCIA",new
			 * Integer((int)daBolVO.getFilvigencia()));
			 * parametros.put("CONSECCOM",new Long(daBolVO.getDABOLCONSEC()));
			 * parametros
			 * .put("NOMBRE_PER_FINAL",daBolVO.getFilperd_nomb_final());
			 * parametros.put("NUM_PERIODOS",new
			 * Integer((int)daBolVO.getFilperd_num()));
			 * parametros.put("INSTITUCION",daBolVO.getFilinst_nomb());
			 * parametros.put("SEDE",daBolVO.getFilsede_nomb());
			 * parametros.put("JORNADA",daBolVO.getFiljornd_nomb());
			 */
		}
		return parametros;
	}

	/**
	 * @param daBolVO
	 * @param reporte
	 * @param msg
	 * @throws Exception
	 */
	private void errorReporte(DatosBoletinVO daBolVO,
			ReporteEstadisticoVO reporte, String msg) throws Exception {
		try {
			daBolVO.setDABOLFECHAFIN(new java.sql.Timestamp(System
					.currentTimeMillis()).toString());
			reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
			daBolVO.setDABOLESTADO(ParamsVO.ESTADO_REPORTE_NOGEN);
			repEstadisticosDAO.updateSolicitud(daBolVO);
			reporte.setMensaje(msg);
			repEstadisticosDAO.ponerReporteMensaje(reporte);
			repEstadisticosDAO.limpiarTablas(daBolVO.getDABOLCONSEC());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
