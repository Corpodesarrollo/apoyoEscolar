package siges.personal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;

import siges.personal.beans.GrupoVO;

import siges.dao.Cursor;
import siges.estudiante.beans.AsistenciaVO;
import siges.estudiante.beans.Convivencia;
import siges.login.beans.Login;
import siges.personal.beans.FormacionVO;
import siges.personal.beans.LaboralVO;
import siges.personal.beans.Personal;
import siges.personal.dao.PersonalDAO;
import siges.util.Properties;

/**
 * Nombre: ControllerNuevoEdit Descripcion: Controla el formulario de nuevo
 * registro por parte de la orientadora Parametro de entrada: HttpServletRequest
 * request, HttpServletResponse response Parametro de salida: HttpServletRequest
 * request, HttpServletResponse response Funciones de la pagina: Procesar la
 * peticion y enviar el control al formulario de nuevo registro Entidades
 * afectadas: Tablas maestras en modo de solo lectura Fecha de modificacinn:
 * 01/12/04
 * 
 * @author Pasantes UD
 * @version $v 1.2 $
 */
public class ControllerNuevoEdit extends HttpServlet {
	private String mensaje;// mensaje en caso de error

	private String buscar;

	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario

	private Cursor cursor;// objeto que maneja las sentencias sql

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

	private static final int TIPO_FOTO = 9;
	
	private static final int TIPO_LISTA_CARGA = 10;
	
	private static final int TIPO_AJAX_LISTA_CARGA = 11;
	
	private static final int TIPO_GRUPOS_CARGA = 12;
	
	private static final int TIPO_GRUPOS_GUARDAR = 13;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		rb = ResourceBundle.getBundle("siges.personal.bundle.personal");
		String ant;
		String er;
		String home;
		request.removeAttribute("mensaje");

		int tipo;
		String sig = getServletConfig().getInitParameter("sig");
		String sig1 = getServletConfig().getInitParameter("sig1");
		String sig2 = getServletConfig().getInitParameter("sig2");
		String sig3 = getServletConfig().getInitParameter("sig3");
		String sig4 = getServletConfig().getInitParameter("sig4");
		String sig5 = getServletConfig().getInitParameter("sig5");
		String sig6 = getServletConfig().getInitParameter("sig6");
		String sig7 = getServletConfig().getInitParameter("sig7");
		String sig8 = getServletConfig().getInitParameter("sig8");
		String sig9 = getServletConfig().getInitParameter("sig9");
		String sig10 = getServletConfig().getInitParameter("sig10");
		String sig12 = getServletConfig().getInitParameter("sig12");
		//String sig13 = getServletConfig().getInitParameter("sig13");
		String ajax1 = getServletConfig().getInitParameter("ajax1");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		err = false;

		mensaje = null;
		try {
			personalDAO = new PersonalDAO(cursor);

			if (request.getParameter("tipo") == null || ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				request.getSession().removeAttribute("editar");
				tipo = 1;
			} else {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			}

			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return (er);
			}
			Login login = (Login) request.getSession().getAttribute("login");
			Personal personal = (Personal) request.getSession().getAttribute("nuevoPersonal2");

			if (personal != null) {
				// System.out.println("personal " + personal.getPernombre1());
				// System.out.println("personal " + personal.getPernumdocum());
				if (personal.getPerVigencia() == 0) {
					if (GenericValidator.isLong(login.getInstId().trim())) {
						personal.setPerVigencia((int) personalDAO
								.getVigenciaInst(Long.parseLong(login
										.getInstId().trim())));
					} else {
						personal.setPerVigencia((int) personalDAO
								.getVigenciaNumerico());
					}
				}
			}
			switch (tipo) {
			case TIPO_BASICA:
				sig = sig1;
				break;
			case TIPO_SALUD:
				sig = sig4;
				break;
			case TIPO_CONVIVENCIA:
				editarConvivencia(request, personal);
				sig = sig2;
				break;
			case TIPO_DOCXJOR:
				editarSedeJornada(request, login);
				sig = sig3;
				break;
			case TIPO_CARGA:
				editarCarga(request, login, personal);
				sig = sig8;
				break;
			case TIPO_LABORAL:
				editarLaboral(request, personal);
				sig = sig5;
				break;
			case TIPO_ACADEMICA:
				editarFormacion(request, personal);
				sig = sig6;
				break;
			case TIPO_ASISTENCIA:

				editarAsistencia(request, personal);
				sig = sig7;
				break;
			case TIPO_FOTO:
				sig = sig9;
				break;
			case TIPO_LISTA_CARGA:
				editarCarga(request, login, personal);
				sig = sig10;
				break;
			case TIPO_AJAX_LISTA_CARGA:
				asignarValoresFiltros(request, login, personal);
				sig = ajax1;
				break;
			case TIPO_GRUPOS_CARGA:
				gruposGrado(request, login, personal);
				sig = sig12;
				break;
			case TIPO_GRUPOS_GUARDAR:
				guardarGruposDocente(request, login, personal);
				sig = sig12;
				break;
			}
			if (err) {
				// System.out.println("mensaje " + mensaje);
				request.setAttribute("mensaje", mensaje);
				return er;
			}

			return sig;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
		}
	}

	public void editarLaboral(HttpServletRequest request, Personal personal)
			throws ServletException, IOException {
		String boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		String r = (request.getParameter("r") != null) ? request
				.getParameter("r") : new String("");
		if (boton.equals("Editar") && personal != null) {
			LaboralVO laboralVO = personalDAO.asignarLaboral(personal, r);
			if (laboralVO != null) {
				laboralVO.setLabEstado("1");
				request.getSession().setAttribute("laboralVO", laboralVO);
				request.getSession().setAttribute("laboralVO2",
						laboralVO.clone());
			} else {
				setMensaje(personalDAO.getMensaje());
			}
		}
		if (boton.equals("Nuevo")) {
			request.getSession().removeAttribute("laboralVO");
			request.getSession().removeAttribute("laboralVO2");
		}
		if (boton.equals("Eliminar") && personal != null) {
			if (personalDAO.eliminarLaboral(personal, r)) {
				request.getSession().removeAttribute("laboralVO");
				request.getSession().removeAttribute("laboralVO2");
			} else {
				setMensaje(personalDAO.getMensaje());
			}
		}
		if (personal != null) {
			request.setAttribute("filtroLaboral",personalDAO.getLaborales(personal));
		}
	}

	public void editarAsistencia(HttpServletRequest request, Personal personal)
			throws ServletException, IOException {
		// System.out.println("editarAsistencia ");

		request.getSession().removeAttribute("asistenciaVO");
		request.getSession().removeAttribute("asistenciaVO2");

		try {
			String boton = (request.getParameter("cmd") != null) ? request
					.getParameter("cmd") : new String("");

			String r = (request.getParameter("r") != null) ? request
					.getParameter("r") : new String("");
			String tipo = "2";
			String msgk = (String) request.getAttribute("mensaje");
			// System.out.println("msg " + msgk);
			if (boton.equals("Editar") && personal != null) {
				AsistenciaVO asistenciaVO = personalDAO.asignarAsistencia(
						personal, tipo, r);
				if (asistenciaVO != null) {
					asistenciaVO.setAsiEstado("1");
					request.getSession().setAttribute("asistenciaVO",
							asistenciaVO);
					request.getSession().setAttribute("asistenciaVO2",
							asistenciaVO.clone());
				} else {
					setMensaje(personalDAO.getMensaje());
				}
			}

			if (boton.equals("Nuevo")) {
				request.getSession().removeAttribute("asistenciaVO");
				request.getSession().removeAttribute("asistenciaVO2");
			}

			if (boton.equals("Eliminar") && personal != null) {
				if (personalDAO.eliminarAsistencia(personal, tipo, r)) {
					request.getSession().removeAttribute("asistenciaVO");
					request.getSession().removeAttribute("asistenciaVO2");
				} else {
					setMensaje(personalDAO.getMensaje());
				}
			}
			if (personal != null) {
				request.setAttribute("filtroAsistencia",
						personalDAO.getAsistencias(tipo, personal));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("" + e.getMessage());
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
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("login") == null
				|| request.getSession().getAttribute("nuevoPersonal2") == null)
			return false;
		return true;
	}

	public void editarSedeJornada(HttpServletRequest request, Login login)
			throws ServletException, IOException {
		int z = 0;
		Collection list = null;
		Object[] o = null;
		try {
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			request.getSession().setAttribute(
					"filtroSedeF",
					personalDAO.getFiltro(
							rb.getString("filtroSedeInstitucion"), list));
			request.getSession()
					.setAttribute(
							"filtroJornadaF",
							personalDAO.getFiltro(rb
									.getString("filtroSedeJornadaInstitucion"),
									list));
			request.getSession().setAttribute(
					"filtroJornadasInstitucion",
					personalDAO.getFiltro(
							rb.getString("filtroJornadasInstitucion"), list));
			request.getSession().setAttribute("filtroPerfilF",
					personalDAO.getPerfiles());
		} catch (Exception e) {
			System.out.println("Excepcion " + e);
		}
	}

	/**
	 * trae de la base de datos los registros de las tablas maestras dependiendo
	 * del tipo de informacion que se desee mostrar de la tabla de informacion
	 * de opinion
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void editarConvivencia(HttpServletRequest request, Personal personal)
			throws ServletException, IOException {
		// System.out.println("entor editarConvivencia");
		String boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		String r = (request.getParameter("r") != null) ? request
				.getParameter("r") : new String("");
		if (boton.equals("Editar") && personal != null) {
			Convivencia convivencia = personalDAO.asignarConvivencia(r);
			if (convivencia != null) {
				convivencia.setEstado("1");
				request.getSession().setAttribute("nuevoConvivencia",
						convivencia);
				request.getSession().setAttribute("nuevoConvivencia2",
						convivencia.clone());
			} else
				setMensaje(personalDAO.getMensaje());
		}
		if (boton.equals("Nuevo")) {
			request.getSession().removeAttribute("nuevoConvivencia");
			request.getSession().removeAttribute("nuevoConvivencia2");
		}
		if (boton.equals("Eliminar") && personal != null) {
			if (personalDAO.eliminarConvivencia(r)) {
				request.getSession().removeAttribute("nuevoConvivencia");
				request.getSession().removeAttribute("nuevoConvivencia2");
			} else
				setMensaje(personalDAO.getMensaje());
		}
		try {
			if (request.getSession().getAttribute("filtroTipoConvivencia") == null)
				request.getSession().setAttribute("filtroTipoConvivencia",personalDAO.getFiltro(rb.getString("personal.convivencia")));
			if (personal != null) {
				Collection lista = new ArrayList();
				Object[] o = new Object[2];
				o[0] = Properties.ENTEROLARGO;
				o[1] = personal.getPernumdocum();
				// System.out.println("personal.getPertipo(); "
				// + personal.getPertipo());
				// System.out.println("personal.getPernumdocum()"
				// + personal.getPernumdocum());
				lista.add(o);
				Collection cc = personalDAO.getFiltro(
						rb.getString("personal.convivencias"), lista);
				// System.out.println("tamano" + cc.size());
				request.getSession().setAttribute("filtroConvivencia", cc);
			} else {
				// System.out.println("NO HAY PERSONAL");
				Collection co = null;
				request.getSession().setAttribute("filtroConvivencia", co);
			}
		} catch (Exception th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
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
		doPost(request, response);
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
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}

	public void editarFormacion(HttpServletRequest request, Personal personal)
			throws ServletException, IOException {
		String boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		String r = (request.getParameter("r") != null) ? request
				.getParameter("r") : new String("");
		if (boton.equals("Editar") && personal != null) {
			FormacionVO formacionVO = personalDAO.asignarFormacion(personal, r);
			if (formacionVO != null) {
				formacionVO.setForEstado("1");
				request.getSession().setAttribute("formacionVO", formacionVO);
				request.getSession().setAttribute("formacionVO2",formacionVO.clone());
			} else {
				setMensaje(personalDAO.getMensaje());
			}
		}
		if (boton.equals("Nuevo")) {
			request.getSession().removeAttribute("formacionVO");
			request.getSession().removeAttribute("formacionVO2");
		}
		if (boton.equals("Eliminar") && personal != null) {
			if (personalDAO.eliminarFormacion(personal, r)) {
				request.getSession().removeAttribute("formacionVO");
				request.getSession().removeAttribute("formacionVO2");
			} else {
				setMensaje(personalDAO.getMensaje());
			}
		}
		if (personal != null) {
			request.setAttribute("filtroFormacion",personalDAO.getFormaciones(personal));
		}
	}
	
	public void gruposGrado(HttpServletRequest request, Login login,Personal personal) throws ServletException, IOException {
		
		request.setAttribute("nombreGradoSeleccionado",request.getParameter("nombreGradoSeleccionado"));
		request.setAttribute("NombreAsignaturaSeleccionada",request.getParameter("nombreAsignaturaSeleccionada"));
		
		if(request.getParameter("sedeSeleccionada")!= null && request.getParameter("jornadaSeleccionada") != null && request.getParameter("gradoSeleccionado") != null
				&& request.getParameter("asignaturaSeleccionada")!= null ){
			int sedeSeleccionada = Integer.parseInt(request.getParameter("sedeSeleccionada"));
			int jornadaSeleccionada = Integer.parseInt(request.getParameter("jornadaSeleccionada"));
			int gradoSeleccionado = Integer.parseInt(request.getParameter("gradoSeleccionado"));
			int asignaturaSeleccionada = Integer.parseInt(request.getParameter("asignaturaSeleccionada"));
			request.setAttribute("asignaturaSeleccionada", ""+asignaturaSeleccionada);
			
			List listaGruposDocente = new ArrayList();
			if(request.getParameter("rotdagdocente")!= null && request.getParameter("rotdagVigencia")!= null){
				long idDocente = Long.parseLong(request.getParameter("rotdagdocente"));
				int vigencia = Integer.parseInt(request.getParameter("rotdagVigencia"));
				listaGruposDocente = personalDAO.getGruposDocente(idDocente, asignaturaSeleccionada, vigencia); 
				request.getSession().setAttribute("listaGruposDocente", listaGruposDocente);
			}
			
			
			List listaGrupos = new ArrayList();
			try {
				int metodologia = Integer.parseInt(request.getParameter("metodologiaSeleccionada"));
				listaGrupos = personalDAO.getGruposInstSedeJornadaGradoMetodologia(Long.parseLong(login.getInstId()),sedeSeleccionada,jornadaSeleccionada,gradoSeleccionado, metodologia);
				request.getSession().removeAttribute("listaGrupos");
				if(listaGrupos != null && listaGrupos.size() > 0){
					request.getSession().setAttribute("listaGrupos",listaGrupos);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void asignarValoresFiltros(HttpServletRequest request, Login login,Personal personal) throws ServletException, IOException {
		if(request.getParameter("sedeSeleccionada")!= null && Integer.parseInt(request.getParameter("sedeSeleccionada"))>0){
			int sedeSeleccionada = Integer.parseInt(request.getParameter("sedeSeleccionada"));
			request.getSession().removeAttribute("sedeSeleccionada");
			request.getSession().setAttribute("sedeSeleccionada", ""+sedeSeleccionada);
		}else{
			request.getSession().removeAttribute("sedeSeleccionada");
		}
		if(request.getParameter("jornadaSeleccionada")!= null && Integer.parseInt(request.getParameter("jornadaSeleccionada"))>0){
			int jornadaSeleccionada = Integer.parseInt(request.getParameter("jornadaSeleccionada"));
			request.getSession().removeAttribute("jornadaSeleccionada");
			request.getSession().setAttribute("jornadaSeleccionada", ""+jornadaSeleccionada);
		}else{
			request.getSession().removeAttribute("jornadaSeleccionada");
		}
		int metodologiaSeleccionada = 0;
		if(request.getParameter("metodologiaSeleccionada")!= null && Integer.parseInt(request.getParameter("metodologiaSeleccionada"))>0){
			metodologiaSeleccionada = Integer.parseInt(request.getParameter("metodologiaSeleccionada"));
			request.getSession().removeAttribute("metodologiaSeleccionada");
			request.getSession().setAttribute("metodologiaSeleccionada", ""+metodologiaSeleccionada);
		}else{
			request.getSession().removeAttribute("metodologiaSeleccionada");
		}
		if(metodologiaSeleccionada > 0){
			request.getSession().setAttribute("maximoHorasAsignaturas",personalDAO.getMaximoIntensidadHorariaAsignatura(Long.parseLong(login.getInstId()), personal.getPerVigencia(), metodologiaSeleccionada));
		}else{
			request.getSession().removeAttribute("maximoHorasAsignaturas");
		}
			
		
		
	}
	
	public void guardarGruposDocente(HttpServletRequest request, Login login,Personal personal) throws ServletException, IOException {
		if(request.getParameter("asignaturaSeleccionada")!= null && request.getParameter("rotdagdocente")!=null && request.getParameter("rotdagVigencia")!= null){
			String[] gruposAIngresar= null;
			if(request.getParameterValues("checkBoxGruposDocente_")!= null){
				gruposAIngresar= request.getParameterValues("checkBoxGruposDocente_");
			}
			
			long asignatura = Integer.parseInt(request.getParameter("asignaturaSeleccionada"));
			long idDocente = Long.parseLong(request.getParameter("rotdagdocente"));
			int vigencia = Integer.parseInt(request.getParameter("rotdagVigencia"));
			
			List listaGrupos = (List)request.getSession().getAttribute("listaGrupos");
			List listaGruposDeseleccionados = new ArrayList();
			listaGruposDeseleccionados =  ((List) ((ArrayList) listaGrupos).clone());
			
			if(listaGrupos != null && listaGrupos.size()>0 && gruposAIngresar != null){
			
				Iterator iteradorGrupos = listaGrupos.iterator();
				while (iteradorGrupos.hasNext()){
					GrupoVO grupo = (GrupoVO) iteradorGrupos.next();
					for(int j = 0; j< gruposAIngresar.length; j++){
						long grupoAIngresar = Long.parseLong(gruposAIngresar[j]);
						if(grupo.getGruCodigoJerarquiaGrupo().longValue() == grupoAIngresar){
							personalDAO.insertarGruposDocente(asignatura, idDocente, grupoAIngresar, vigencia);
							listaGruposDeseleccionados.remove(grupo);
						}
					}
				}
			}
			//Remover no seleccionados
			Iterator iteradorGruposDeseleccionados = listaGruposDeseleccionados.iterator();
			GrupoVO grupoDeseleccionado = null;
			while (iteradorGruposDeseleccionados.hasNext()){
				grupoDeseleccionado = (GrupoVO) iteradorGruposDeseleccionados.next();
				boolean eliminaRegistro = personalDAO.eliminarGruposDocente(idDocente, asignatura, vigencia, grupoDeseleccionado.getGruCodigoJerarquiaGrupo().longValue());
				String llaveCompuesta = grupoDeseleccionado.getGruCodigoJerarquiaGrupo().longValue() + "-" + idDocente + "-" + asignatura + "-" + vigencia;
				if (eliminaRegistro) { personalDAO.insetrarMensajeRegistroBorrado(llaveCompuesta, "borrado desde la funcion guardarGruposDocente");}
			}
			
			setMensaje("Operacion Satisfecha");
		}
	}
	
		
	
	public void editarCarga(HttpServletRequest request, Login login,Personal personal) throws ServletException, IOException {
		// String
		// boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new
		// String("");
		int z = 0;
		Collection list = null;
		Object[] o = null;
		// String dato,dato2,dato3;
		try {
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			list.add(o);
			// traer la lista de metodologias
			request.setAttribute("filtroMetodologiaF",personalDAO.getFiltro(rb.getString("Lista.MetodologiaInstitucion"), list));
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = personal.getPernumdocum();
			list.add(o);
			request.setAttribute("ihTotal", (personalDAO.getFiltroArray(rb.getString("Lista.ihTotal"), list))[0]);
			// sedes,jornadas
			request.setAttribute("filtroSedeF",personalDAO.getFiltro(rb.getString("Lista.filtroSedeInstitucion"), list));
			request.setAttribute("filtroJornadaF", personalDAO.getFiltro(rb.getString("Lista.filtroSedeJornadaInstitucion"), list));
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = String.valueOf(personal.getPerVigencia());
			list.add(o);
			// carga
			request.setAttribute("cargaDocente", personalDAO.getFiltro(rb.getString("Lista.FijarDocente"), list));
			request.setAttribute("cargaTotal",personalDAO.getCargaHorariaTotal(Long.parseLong(personal.getPernumdocum()), Long.parseLong(login.getInstId()), personal.getPerVigencia()));
			
			//request.setAttribute("cargaTotal",personalDAO.getFiltroMatriz(rb.getString("Lista.FijarDocente2"), list));
			request.setAttribute("ihTotal2", (personalDAO.getFiltroArray(rb.getString("Lista.ihTotal2"), list))[0]);
			list = new ArrayList();
			// traer asignaturas
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			list.add(o);
			int metodologiaSeleccionada = 0;
			if(request.getSession().getAttribute("metodologiaSeleccionada")!= null && Integer.parseInt(request.getSession().getAttribute("metodologiaSeleccionada").toString())>0){
				metodologiaSeleccionada = Integer.parseInt(request.getSession().getAttribute("metodologiaSeleccionada").toString());
				request.getSession().removeAttribute("metodologiaSeleccionada");
				request.getSession().setAttribute("metodologiaSeleccionada", ""+metodologiaSeleccionada);
			}else{
				request.getSession().removeAttribute("metodologiaSeleccionada");
			}
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = String.valueOf(personal.getPerVigencia());
			list.add(o);
			/*
			 * o = new Object[2]; o[0] = Properties.ENTEROLARGO; o[1] =
			 * login.getMetodologiaId(); list.add(o);
			 */
			request.getSession().setAttribute("filtroAsignatura", personalDAO.getAsignaturasMetodologia(rb.getString("Lista.AsignaturaMetodologia"),login.getInstId(),personal.getPerVigencia(),metodologiaSeleccionada));
			// traer los grados asignatura
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = personal.getPernumdocum();
			list.add(o);
			request.setAttribute("filtroGradoAsignatura",personalDAO.getFiltro(rb.getString("Lista.filtroGradoAsignatura"), list));
			// traer todos los grados de la institucion-metodologia
			list = new ArrayList();
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			list.add(o);
			/*
			 * o = new Object[2]; o[0] = Properties.ENTEROLARGO; o[1] =
			 * login.getMetodologiaId(); list.add(o);
			 */

			request.getSession().setAttribute("filtroGrados",personalDAO.getFiltro(rb.getString("listaGrados"), list));
			// VIGENCIA
			/*
			 * int vig = (int) personalDAO.getVigenciaNumerico(); List l = new
			 * ArrayList(); l.add(String.valueOf(vig)); l.add(String.valueOf(vig
			 * + 1)); l.add(String.valueOf(vig + 2));
			 * request.setAttribute("listaVigencia", l);
			 */
			request.removeAttribute("listaVigencia");
			request.setAttribute("listaVigencia",personalDAO.getListaVigencia());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Excepcion Carga Edit:" + e);
		}
	}
}
