package siges.personal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import com.google.gson.Gson;

import siges.dao.Cursor;
import siges.estudiante.beans.AsistenciaVO;
import siges.estudiante.beans.Convivencia;
import siges.estudiante.beans.Salud;
import siges.login.beans.Login;
import siges.personal.beans.Carga;
import siges.personal.beans.FormacionVO;
import siges.personal.beans.LaboralVO;
import siges.personal.beans.Personal;
import siges.personal.dao.PersonalDAO;
import util.BitacoraCOM;
import util.LogPersonalCargaDto;
import util.LogPersonalDto;

/**
 * Nombre: Descripcion: Controla la peticion de insertar un nuevo registro
 * Parametro de entrada: HttpServletRequest request, HttpServletResponse
 * response Parametro de salida: HttpServletRequest request, HttpServletResponse
 * response Funciones de la pagina: Valida, inserta un nuevo registro y sede el
 * control a la pagina de resultados Entidades afectadas: Tablas de información
 * del egresado Fecha de modificacinn: 01/12/04
 * 
 * @author Pasantes UD
 * @version $v 1.2 $
 */
public class ControllerNuevoSave extends HttpServlet {
	private Cursor cursor;// objeto que maneja las sentencias sql

	private String mensaje;// mensaje en caso de error

	private boolean band;

	private boolean err;// variable que inidica si hay o no errores en la

	// validacion de los datos del formulario

	private String sig;// nombre de la pagina a la que ira despues de ejecutar

	// los comandos de esta

	private String ant;// pagina a la que ira en caso de que no se pueda

	// procesar esta pagina

	private String er;// nombre de la pagina a la que ira si hay errores

	private String home;// nombre de la pagina a la que ira si hay errores

	private PersonalDAO personalDAO;//

	private ResourceBundle rb;

	private static final int TIPO_BASICA = 1;

	private static final int TIPO_CONVIVENCIA = 2;

	private static final int TIPO_DOCXJOR = 3;

	private static final int TIPO_SALUD = 4;

	private static final int TIPO_LABORAL = 5;

	private static final int TIPO_ACADEMICA = 6;

	private static final int TIPO_ASISTENCIA = 7;

	private static final int TIPO_CARGA = 8;
	
	//private static final int TIPO_GRUPOS_GUARDAR = 13;

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("guardar	");
		rb = ResourceBundle.getBundle("siges.personal.bundle.personal");
		LaboralVO laboralVO = null, laboralVO2 = null;
		AsistenciaVO asistenciaVO = null, asistenciaVO2 = null;
		Salud salud = null, salud2 = null;
		FormacionVO formacionVO = null;
		FormacionVO formacionVO2 = null;
		String boton;
		String buscar = null;
		String respuesta = "";
		sig = getServletConfig().getInitParameter("sig");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		err = false;
		band = true;
		mensaje = null;
		respuesta = "La información fue ingresada satisfactoriamente ";
		cursor = new Cursor();
		personalDAO = new PersonalDAO(cursor);
		try {
			boton = (request.getParameter("cmd") != null) ? request
					.getParameter("cmd") : new String("Cancelar");
			if (boton.equals("Cancelar")) {
				borrarBeans(request);
				return home;
			}
			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return (er);
			}
			Login login = (Login) session.getAttribute("login");
			Personal personal = (Personal) session.getAttribute("nuevoPersonal");
			Convivencia convivencia = (Convivencia) session.getAttribute("nuevoConvivencia");
			Carga carga = (Carga) session.getAttribute("carga");
			salud = (Salud) request.getSession().getAttribute("nuevoSalud");
			laboralVO = (LaboralVO) request.getSession().getAttribute("laboralVO");
			asistenciaVO = (AsistenciaVO) request.getSession().getAttribute("asistenciaVO");
			formacionVO = (FormacionVO) request.getSession().getAttribute("formacionVO");

			Personal personal2 = null;
			Convivencia convivencia2 = null;
			Carga carga2 = null;
			if (personal.getEstado().equals("1")) {
				personal2 = (Personal) session.getAttribute("nuevoPersonal2");
				convivencia2 = (Convivencia) session
						.getAttribute("nuevoConvivencia2");
				carga2 = (Carga) session.getAttribute("carga2");
			}
			if (salud.getSalestado().equals("1")) {
				salud2 = (Salud) request.getSession().getAttribute(
						"nuevoSalud2");
			}
			if (laboralVO.getLabEstado().equals("1")) {
				laboralVO2 = (LaboralVO) request.getSession().getAttribute(
						"laboralVO2");
			}
			if (asistenciaVO.getAsiEstado().equals("1")) {
				asistenciaVO2 = (AsistenciaVO) request.getSession()
						.getAttribute("asistenciaVO2");
			}
			if (formacionVO.getForEstado().equals("1")) {
				formacionVO2 = (FormacionVO) request.getSession().getAttribute(
						"formacionVO2");
			}
			if (boton.equals("Nuevo")) {
				if (request.getParameter("tipo") == null
						|| request.getParameter("tipo").equals("")) {
					setMensaje("Acceso denegado no hay una ficha definida");
					request.setAttribute("mensaje", getMensaje());
					return er;
				}
				int tipo = Integer.parseInt((String) request
						.getParameter("tipo"));
				switch (tipo) {
				case TIPO_CARGA:
					session.removeAttribute("carga");
					session.removeAttribute("carga2");
					return (ant += "?tipo=" + tipo);
				}
			}
			if (boton.equals("Guardar")) {
				if (request.getParameter("tipo") == null
						|| request.getParameter("tipo").equals("")) {
					setMensaje("Acceso denegado no hay una ficha definida");
					request.setAttribute("mensaje", getMensaje());
					return er;
				}
				int tipo = 1;
				if (GenericValidator.isInt(request.getParameter("tipo"))) {
					tipo = Integer.parseInt((String) request
							.getParameter("tipo"));
				}

//				System.out.println("tipo en save " + tipo);
				if (tipo == 8 || tipo == 12) {
					request.setAttribute("horasref",
							request.getParameter("rotdaghoras"));
					request.setAttribute("sederef",
							request.getParameter("rotdagsede"));
					request.setAttribute("jornadaref",
							request.getParameter("rotdagjornada"));
					request.setAttribute("metodologiaref",
							request.getParameter("rotdagmetodologia"));
				}
				switch (tipo) {
				case TIPO_BASICA:// basica
					if (personal2 == null
							|| personal2.getPernumdocum().equals("")) {
						setMensaje("No se puede guardar la información de datos bnsicos");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					String peremail = request.getParameter("peremail");
					if (!GenericValidator.isEmail(peremail)) {
						request.setAttribute("mensaje",
								"VERIFIQUE LA SIGUIENTE información: \n\n   - Email");
					}
					if (personal2.getPernumdocum() != null && peremail != null) {
						updateEmail(request, personal2.getPernumdocum(),
								peremail,login);
						request.setAttribute("mensaje", getMensaje());
						request.setAttribute("mensaje",
								"La información fue ingresada satisfactoriamente");
						return ("/personal/NuevoPersonal.jsp");
					}

					break;

				case TIPO_CONVIVENCIA:// convivencia
					if (personal2 == null
							|| personal2.getPernumdocum().equals("")) {
						setMensaje("No se puede guardar la información de convivencia \nSi no se ha registrado un personal en la ficha de información bnsica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (convivencia.getEstado().equals("")) {
						convivencia.setConcodinst(login.getInstId());
						convivencia.setContipoperso(personal2.getPertipo());
						convivencia.setConcodperso(personal2.getPernumdocum());
						insertarRegistroConvivencia(request, convivencia);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (convivencia.getEstado().equals("1")) {
						actualizarRegistroConvivencia(request, convivencia,
								convivencia2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
				case TIPO_SALUD:
					if (personal2 == null
							|| personal2.getPernumdocum().equals("")) {
						setMensaje("No se puede guardar la información de salud \nSi no hay registrado un personal en la ficha de información bnsica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (salud.getSalestado().equals("")) {
						salud.setSaltipoperso(personal2.getPertipo());
						salud.setSalcodperso(personal2.getPernumdocum());
						insertarRegistroSalud(request, salud);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (salud.getSalestado().equals("1")) {
						actualizarRegistroSalud(request, salud, salud2,
								personal2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
				case TIPO_LABORAL:
					System.out.println("TIPO_ASISTENCIA ");
					if (personal2 == null
							|| personal2.getPernumdocum().equals("")) {
						setMensaje("No se puede guardar la información laboral \nSi no se ha registrado un personal en la ficha de información bnsica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (laboralVO.getLabEstado().equals("")) {
						laboralVO.setLabCodPerso(personal2.getPernumdocum());
						insertarRegistroLaboral(request, laboralVO);
						request.getSession().removeAttribute("laboralVO");
						request.getSession().removeAttribute("laboralVO2");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (laboralVO.getLabEstado().equals("1")) {
						actualizarRegistroLaboral(request, laboralVO,
								laboralVO2);
						request.getSession().removeAttribute("laboralVO");
						request.getSession().removeAttribute("laboralVO2");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
				case TIPO_ACADEMICA:
					if (personal2 == null
							|| personal2.getPernumdocum().equals("")) {
						setMensaje("No se puede guardar la información de formacinn academica \nSi no se ha registrado un personal en la ficha de información bnsica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					System.out.println("formacionVO.getForEstado() "
							+ formacionVO.getForEstado());
					System.out.println("personal2.getPernumdocum() "
							+ personal2.getPernumdocum());
					if (formacionVO.getForEstado().trim().equals("")) {
						formacionVO.setForCodPerso(personal2.getPernumdocum());
						System.out.println("formacionVO.getForEstado() "
								+ formacionVO.getForEstado());
						insertarRegistroFormacion(request, formacionVO);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (formacionVO.getForEstado().equals("1")) {
						actualizarRegistroFormacion(request, formacionVO,
								formacionVO2);
						request.getSession().removeAttribute("asistenciaVO");
						request.getSession().removeAttribute("asistenciaVO2");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
				case TIPO_ASISTENCIA:
					System.out.println("personal2 " + personal2.getEstado());
					System.out.println("personal2.getPernumdocum() "
							+ personal2.getPernumdocum());
					if (personal2 == null
							|| personal2.getPernumdocum().equals("")) {

						setMensaje("No se puede guardar la información \nSi no se ha registrado un personal en la ficha de información bnsica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					System.out.println("asistenciaVO.getAsiEstado() "
							+ asistenciaVO.getAsiEstado());
					if (asistenciaVO.getAsiEstado().equals("")) {
						asistenciaVO.setAsiCodPer(personal2.getPernumdocum());
						insertarRegistroAsistencia(request, asistenciaVO);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (asistenciaVO.getAsiEstado().equals("1")) {
						actualizarRegistroAsistencia(request, asistenciaVO,
								asistenciaVO2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
				case TIPO_DOCXJOR:// docente-sede-jornada
					if (personal2 == null
							|| personal2.getPernumdocum().equals("")) {
						setMensaje("No se puede guardar la información de sedes y jornadas \nSi no hay registrado un personal en la ficha de información bnsica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					insertarDocenteJornada(request, login, personal);
					request.setAttribute("mensaje", getMensaje());
					return (ant += "?tipo=" + tipo);
				case TIPO_CARGA:// carga academica
					if (personal2 == null || personal2.getPernumdocum().equals("")) {
						setMensaje("No se puede guardar la información de carga acadnmica \nSi no hay registrado un personal en la ficha de información bnsica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					if (carga.getEstado().equals("")) {
						insertarCarga(request, login, carga, carga2);
						request.setAttribute("mensaje", getMensaje());
						// System.out.println(ant+="?tipo="+tipo);
						return (ant += "?tipo=" + tipo);
					}
				}
			}
			
			
			
			return er;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (personalDAO != null)
				personalDAO.cerrar();
		}
	}

	public boolean compararFichasAsistencia(AsistenciaVO asistenciaVO,
			AsistenciaVO asistenciaVO2) {
		return personalDAO.compararBeans(asistenciaVO, asistenciaVO2);
	}

	public boolean compararFichasLaboral(LaboralVO laboralVO,
			LaboralVO laboralVO2) {
		return personalDAO.compararBeans(laboralVO, laboralVO2);
	}

	public boolean compararFichasSalud(Salud salud, Salud salud2) {
		return personalDAO.compararBeans(salud, salud2);
	}

	public void actualizarRegistroLaboral(HttpServletRequest request,
			LaboralVO laboralVO, LaboralVO laboralVO2) throws ServletException,
			IOException {
		if (compararFichasLaboral(laboralVO, laboralVO2)) {
			setMensaje("La información fue actualizada satisfactoriamente -");
			return;
		}
		if (!personalDAO.actualizar(laboralVO)) {
			setMensaje(personalDAO.getMensaje());
			restaurarBeansLaboral(request, laboralVO, laboralVO2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		recargarBeansLaboral(request, laboralVO, laboralVO2);
	}

	public void actualizarRegistroAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO, AsistenciaVO asistenciaVO2)
			throws ServletException, IOException {
		System.out.println("actualizarRegistroAsistencia ");
		if (compararFichasAsistencia(asistenciaVO, asistenciaVO2)) {
			setMensaje("La información fue actualizada satisfactoriamente -");
			return;
		}
		if (!personalDAO.actualizar(asistenciaVO)) {
			setMensaje(personalDAO.getMensaje());
			restaurarBeansAsistencia(request, asistenciaVO2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		recargarBeansAsistencia(request, asistenciaVO);
	}

	public void actualizarRegistroSalud(HttpServletRequest request,
			Salud salud, Salud salud2, Personal personal2)
			throws ServletException, IOException {
		if (compararFichasSalud(salud, salud2)) {
			setMensaje("La información fue actualizada satisfactoriamente -");
			return;
		}
		String valores[] = { "2", personal2.getPernumdocum() };
		if (!personalDAO.actualizar(salud, valores)) {
			setMensaje(personalDAO.getMensaje());
			restaurarBeansSalud(request, salud, salud2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		recargarBeansSalud(request, salud, salud2);
	}

	/**
	 * inserta las cinco fichas en la base de datos
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void insertarRegistroLaboral(HttpServletRequest request,
			LaboralVO laboralVO) throws ServletException, IOException {
		String[] resul;
		if (!personalDAO.insertar(laboralVO)) {
			setMensaje(personalDAO.getMensaje());
			return;
		}
		request.getSession().removeAttribute("laboralVO");
		request.getSession().removeAttribute("laboralVO2");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	public void insertarRegistroAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO) throws ServletException, IOException {
		String[] resul;
		asistenciaVO.setAsiTipoPer("2");
		if (!personalDAO.insertar(asistenciaVO)) {
			setMensaje(personalDAO.getMensaje());
			return;
		}
		request.getSession().removeAttribute("asistenciaVO");
		request.getSession().removeAttribute("asistenciaVO2");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	/**
	 * inserta las cinco fichas en la base de datos
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void insertarRegistroSalud(HttpServletRequest request, Salud salud)
			throws ServletException, IOException {
		String[] resul;
		salud.setSaltipoperso("2");
		if (!personalDAO.insertar(salud)) {
			setMensaje(personalDAO.getMensaje());
			return;
		}
		salud.setSalestado("1");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	public boolean compararFichasConvivencia(Convivencia convivencia,
			Convivencia convivencia2) {
		return personalDAO.compararBeans(convivencia, convivencia2);
	}

	public void actualizarRegistroConvivencia(HttpServletRequest request,
			Convivencia convivencia, Convivencia convivencia2)
			throws ServletException, IOException {
		if (compararFichasConvivencia(convivencia, convivencia2)) {
			setMensaje("La información fue actualizada satisfactoriamente -");
			return;
		}
		if (!personalDAO.actualizar(convivencia)) {
			setMensaje(personalDAO.getMensaje());
			restaurarBeansConvivencia(request, convivencia, convivencia2);
			return;
		}
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoConvivencia2");
		setMensaje("La información fue actualizada satisfactoriamente");
	}

	public boolean compararFichas(Carga carga, Carga carga2) {
		return personalDAO.compararBeans(carga, carga2);
	}

	/**
	 * inserta las cinco fichas en la base de datos
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void insertarRegistroConvivencia(HttpServletRequest request,
			Convivencia convivencia) throws ServletException, IOException {
		String[] resul;
		if (!personalDAO.insertar(convivencia)) {
			setMensaje(personalDAO.getMensaje());
			return;
		}
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoConvivencia2");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	/**
	 * inserta las cinco fichas en la base de datos
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void insertarCarga(HttpServletRequest request, Login login,Carga carga, Carga carga2) throws ServletException, IOException {
		try {
			
			List listaGrados = (List) request.getSession().getAttribute("filtroGrados");
			List listaAsignaturas = (List) request.getSession().getAttribute("filtroAsignatura");
			List listaCargaAsignaturaGrado = new ArrayList();
			String[] cargaAsignaturaGrado = new String[3];
			int ASIGNATURA = 0;
			int GRADO = 1;
			int CARGA = 2;
			
			Iterator iteradorGrados = listaGrados.iterator();
			Iterator iteradorAsignaturas = listaAsignaturas.iterator();
			
			List<LogPersonalCargaDto> lstCargaDto= new ArrayList<>();
			while(iteradorAsignaturas.hasNext()){
				
				Object[] asignatura = (Object[]) iteradorAsignaturas.next();
				
				while (iteradorGrados.hasNext()){
					
					Object[] grado = (Object[]) iteradorGrados.next();
					String nombreCaja = "cargaGradoAsignatura_"+grado[0].toString()+"_"+asignatura[0].toString();
					
					if(request.getParameter(nombreCaja) != null){
						
						String parametro = request.getParameter(nombreCaja).toString();
						
						if(!parametro.trim().equals("") && !parametro.trim().equals("0")){
							
							cargaAsignaturaGrado = new String[3];
							cargaAsignaturaGrado[ASIGNATURA]= asignatura[0].toString();
							cargaAsignaturaGrado[GRADO]= grado[0].toString();
							cargaAsignaturaGrado[CARGA]= parametro;
							listaCargaAsignaturaGrado.add(cargaAsignaturaGrado);
							LogPersonalCargaDto cargaDto= new LogPersonalCargaDto();
							cargaDto.setInstitucion(login.getInst());
							cargaDto.setDocente(carga.getRotdagdocente().trim());
							cargaDto.setAsignatura(asignatura[0].toString());
							cargaDto.setGrado(grado[0].toString());
							cargaDto.setHoras(parametro.toString());
							
							lstCargaDto.add(cargaDto);							
						}
						else{
							boolean eliminaDatos = personalDAO.eliminarGruposGrado(Long.parseLong(login.getInstId()),Integer.parseInt(carga.getRotdagsede().trim()), Integer.parseInt(carga.getRotdagjornada().trim()),Integer.parseInt(grado[0].toString()),Long.parseLong(asignatura[0].toString()), Long.parseLong(carga.getRotdagdocente().trim()), carga.getRotdagVigencia());
							String llaveCompuesta = Long.parseLong(login.getInstId())+"-"+Integer.parseInt(carga.getRotdagsede().trim())+"-"+Integer.parseInt(carga.getRotdagjornada().trim())+"-"+Integer.parseInt(grado[0].toString())+"-"+Long.parseLong(asignatura[0].toString());
							if (eliminaDatos) {personalDAO.insetrarMensajeRegistroBorrado(llaveCompuesta, "borrado desde la funcion InsertarCarga");}
							try
							{
								LogPersonalCargaDto cargaDto= new LogPersonalCargaDto();
								cargaDto.setInstitucion(login.getInst());
								cargaDto.setDocente(carga.getRotdagdocente().trim());
								cargaDto.setAsignatura(asignatura[0].toString());
								cargaDto.setGrado(grado[0].toString());
								cargaDto.setHoras(parametro.toString());								
								BitacoraCOM.insertarBitacora(
										Long.parseLong(login.getInstId()), 
										Integer.parseInt(login.getJornadaId()),
										2 ,
										login.getPerfil(), 
										Integer.parseInt(login.getSede()), 
										20002, 
										3, 
										login.getUsuarioId(), 
										new Gson().toJson(cargaDto)
										);
							}catch(Exception e){
								e.printStackTrace();
								System.out.println("Error " + this + ":" + e.toString());
							}
						}
					}
					
				}
				
				
				
				if(listaCargaAsignaturaGrado != null && listaCargaAsignaturaGrado.size()>0){
					Iterator iteradorListaCargaAsignaturaGrado = listaCargaAsignaturaGrado.iterator();
					String[] vectorGrados = new String[listaCargaAsignaturaGrado.size()];
					String[] vectorHoras = new String[listaCargaAsignaturaGrado.size()];
					int i = 0;
					while (iteradorListaCargaAsignaturaGrado.hasNext()){
						String[] itemLista = (String[]) iteradorListaCargaAsignaturaGrado.next();
						vectorGrados[i]=itemLista[GRADO];
						vectorHoras[i]=itemLista[CARGA];
						i++;
					}
					carga.setRotdaggrados(vectorGrados);
					carga.setRotdagIHgrados_(vectorHoras);
				listaCargaAsignaturaGrado = new ArrayList();
				}else{
					carga.setRotdaggrados(null);
					carga.setRotdagIHgrados_(null);
				}
				iteradorGrados = listaGrados.iterator();
				carga.setRotdagasignatura(asignatura[0].toString());
				if (!personalDAO.insertarFijarDocente(carga, login)) {
					setMensaje(personalDAO.getMensaje());
					return;
				}
			}
			try
			{
				BitacoraCOM.insertarBitacora(
						Long.parseLong(login.getInstId()), 
						Integer.parseInt(login.getJornadaId()),
						2 ,
						login.getPerfil(), 
						Integer.parseInt(login.getSede()), 
						20003, 
						1, 
						login.getUsuarioId(), 
						new Gson().toJson(lstCargaDto)
						);
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Error " + this + ":" + e.toString());
			}
			request.removeAttribute("guia");
			setMensaje("La información fue ingresada satisfactoriamente");
			siges.util.Logger.print(login != null ? login.getUsuarioId() : "","Insercinn en Tabla 'rot_doc_asig_grado'", 7, 1, this.toString());
			request.getSession().removeAttribute("carga");
			request.getSession().removeAttribute("carga2");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error Insertando Carga Academica: " + e);
			setMensaje("Error ingresando información " + e);
		}
	}

	/**
	 * inserta las cinco fichas en la base de datos
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void insertarDocenteJornada(HttpServletRequest request, Login login,Personal personal) throws ServletException, IOException {
		String[] resul;
		try {
			String[] jornada = request.getParameterValues("jornada");
			if (jornada != null) {
				for (int k = 0; k < (jornada != null ? jornada.length : 0); k++)
					System.out.println("jornada: " + jornada[k]);
				if (!personalDAO.insertarSedeJornada(personal,
						Long.parseLong(login.getInstId()), jornada)) {
					setMensaje(personalDAO.getMensaje());
					return;
				}
			}
			if (!personal.getPercodjerar().equals("")) {
				personal.setSedejornada(personalDAO.getSedeJornadaPerfil(
						Long.parseLong(login.getInstId()),
						Long.parseLong(personal.getPernumdocum())));
			}
			request.getSession().setAttribute("nuevoPersonal",
					(Personal) personal);
			request.getSession().setAttribute("nuevoPersonal2",
					(Personal) personal.clone());
			setMensaje("La información fue ingresada satisfactoriamente");
		} catch (Exception e) {
			System.out.println("error Insertando docente sede jornada: " + e);
			setMensaje("Error ingresando información " + e);
		}
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		request.getSession().removeAttribute("nuevoPersonal");
		request.getSession().removeAttribute("nuevoPersonal2");
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoConvivencia2");
		request.getSession().removeAttribute("encabezado");
	}

	/**
	 * Asigna a variables locales el contenido de los beans de session
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean true=asigno todos los beans; false=no existe alguno de
	 *         los beans en la session
	 */

	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("login") == null
				|| request.getSession().getAttribute("nuevoPersonal") == null
				|| request.getSession().getAttribute("nuevoConvivencia") == null)
			return false;
		return true;
	}

	/**
	 * Busca en la base de datos un registro que coincida con el id ingresado
	 * por el usuario
	 * 
	 * @return boolean
	 */

	public boolean validarExistencia(String cod, String jerar) {
		return personalDAO.existePersona(cod, jerar);
	}

	/**
	 * Referencia al bean del usuario con la información proporcionada por el
	 * bean de respaldo
	 * 
	 * @param int n
	 * @param HttpServletRequest
	 *            request
	 */
	public void restaurarBeans(HttpServletRequest request, Personal personal2)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoPersonal",
				(Personal) personal2.clone());
	}

	public void restaurarBeansConvivencia(HttpServletRequest request,
			Convivencia convivencia, Convivencia convivencia2)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoConvivencia",
				(Convivencia) convivencia2.clone());
	}

	/**
	 * Referencia al bean de respaldo con la nueva información proporcionada por
	 * el bean modificado por el usuario
	 * 
	 * @param int n
	 * @param HttpServletRequest
	 *            request
	 */
	public void recargarBeansLaboral(HttpServletRequest request,
			LaboralVO laboralVO, LaboralVO laboralVO2) throws ServletException,
			IOException {
		request.getSession().setAttribute("laboralVO2",
				(LaboralVO) laboralVO.clone());
	}

	public void recargarBeansSalud(HttpServletRequest request, Salud salud,
			Salud salud2) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoSalud", (Salud) salud.clone());
	}

	public void recargarBeansAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO) throws ServletException, IOException {
		request.getSession().setAttribute("asistenciaVO2",
				(AsistenciaVO) asistenciaVO.clone());
	}

	public void recargarBeans(HttpServletRequest request, Personal personal)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoPersonal2",
				(Personal) personal.clone());
	}

	public void recargarBeansConvivencia(HttpServletRequest request,
			Convivencia convivencia, Convivencia convivencia2)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoConvivencia2",
				(Convivencia) convivencia.clone());
	}

	public void restaurarBeansLaboral(HttpServletRequest request,
			LaboralVO laboralVO, LaboralVO laboralVO2) throws ServletException,
			IOException {
		request.getSession().setAttribute("laboralVO",
				(LaboralVO) laboralVO2.clone());
	}

	public void restaurarBeansSalud(HttpServletRequest request, Salud salud,
			Salud salud2) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoSalud", (Salud) salud2.clone());
	}

	public void restaurarBeansAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO2) throws ServletException, IOException {
		request.getSession().setAttribute("asistenciaVO",
				(AsistenciaVO) asistenciaVO2.clone());
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		band = false;
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * inserta las cinco fichas en la base de datos
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void insertarRegistroFormacion(HttpServletRequest request,
			FormacionVO formacionVO) throws ServletException, IOException {
		try {
			System.out.println("insertarRegistroFormacion");

			String[] resul;
			if (!personalDAO.insertar(formacionVO)) {
				setMensaje(personalDAO.getMensaje());
				return;
			}
			request.getSession().removeAttribute("formacionVO");
			request.getSession().removeAttribute("formacionVO2");
			setMensaje("La información fue ingresada satisfactoriamente");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void actualizarRegistroFormacion(HttpServletRequest request,
			FormacionVO formacionVO, FormacionVO formacionVO2)
			throws ServletException, IOException {
		System.out.println("actualizarRegistroFormacion ");
		if (compararFichasFormacion(formacionVO, formacionVO2)) {
			setMensaje("La información fue actualizada satisfactoriamente -");
			return;
		}
		if (!personalDAO.actualizar(formacionVO)) {
			setMensaje(personalDAO.getMensaje());
			restaurarBeansFormacion(request, formacionVO, formacionVO2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		recargarBeansFormacion(request, formacionVO, formacionVO2);
	}

	public boolean compararFichasFormacion(FormacionVO formacionVO,
			FormacionVO formacionVO2) {
		return personalDAO.compararBeans(formacionVO, formacionVO2);
	}

	public void recargarBeansFormacion(HttpServletRequest request,
			FormacionVO formacionVO, FormacionVO formacionVO2)
			throws ServletException, IOException {
		request.getSession().setAttribute("formacionVO2",
				(FormacionVO) formacionVO.clone());
	}

	public void restaurarBeansFormacion(HttpServletRequest request,
			FormacionVO formacionVO, FormacionVO formacionVO2)
			throws ServletException, IOException {
		request.getSession().setAttribute("formacionVO",
				(FormacionVO) formacionVO2.clone());
	}

	public void updateEmail(HttpServletRequest request, String pernumdoc,
			String peremail,Login login) throws ServletException, IOException {
		System.out.println("updateEmail");
		if (!personalDAO.updatePersonal(pernumdoc, peremail)) {
			setMensaje(personalDAO.getMensaje());
			return;
		}
		//bitacora
		try
		{
			LogPersonalDto log = new LogPersonalDto();
			log.setCorreo(peremail);
			log.setFecha(LocalDateTime.now().toString());
			BitacoraCOM.insertarBitacora(
					Long.parseLong(login.getInstId()), 
					Integer.parseInt(login.getJornadaId()),
					2 ,
					login.getPerfil(), 
					Integer.parseInt(login.getSede()), 
					20002, 
					2, 
					login.getUsuarioId(), 
					new Gson().toJson(log)
					);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error " + this + ":" + e.toString());
		}
		setMensaje("La información fue ingresada satisfactoriamente");
	}
}