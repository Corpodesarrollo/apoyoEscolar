package siges.institucion;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.institucion.beans.Cafeteria;
import siges.institucion.beans.Espacio;
import siges.institucion.beans.Institucion;
import siges.institucion.beans.Jornada;
import siges.institucion.beans.Nivel;
import siges.institucion.beans.Sede;
import siges.institucion.beans.Transporte;
import siges.institucion.dao.InstitucionDAO;

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
	private Institucion institucion, institucion2;
	private Jornada jornada, jornada2;
	private Sede sede, sede2;
	private Espacio espacio, espacio2;
	private Nivel nivel, nivel2;
	private Transporte transporte, transporte2;
	private Cafeteria cafeteria, cafeteria2;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private InstitucionDAO institucionDAO;
	private String mensaje;// mensaje en caso de error
	private boolean band;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private int tipo;
	private String sig;// nombre de la pagina a la que ira despues de ejecutar
						// los comandos de esta
	private String ant;// pagina a la que ira en caso de que no se pueda
						// procesar esta pagina
	private String er;// nombre de la pagina a la que ira si hay errores
	private String home;// nombre de la pagina a la que ira si hay errores
	private String nombre[];// contendra los datos del formulario de filtro
							// ordenados
	private HttpSession session;

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String boton;
		String buscar = null;
		String respuesta = "";
		nombre = new String[2];

		ant = "/institucion/ControllerNuevoEdit.do";// pagina a la que se dara
													// el control si algo falla
		sig = "/institucion/NuevoConflictoEscolar.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		err = false;
		band = true;
		mensaje = null;
		respuesta = "La información fue ingresada satisfactoriamente ";
		cursor = new Cursor();
		try {
			institucionDAO = new InstitucionDAO(cursor);
			boton = (request.getParameter("cmd") != null) ? request
					.getParameter("cmd") : new String("Cancelar");
			if (boton.equals("Cancelar")) {
				borrarBeans(request);
				return home;
			}
			if (boton.equals("Guardar")) {
				if (request.getParameter("tipo2") == null
						|| request.getParameter("tipo2").equals("")) {
					setMensaje("Acceso denegado no hay una ficha definida");
					request.setAttribute("mensaje", getMensaje());
					return er;
				}
				tipo = Integer.parseInt((String) request.getParameter("tipo2"));
				asignarBeans(request);
				switch (tipo) {
				case 1:
					if (institucion.getEstado().equals("")) {
						insertarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (institucion.getEstado().equals("1")) {
						actualizarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					break;
				case 2:
					if (institucion2 == null
							|| institucion2.getInscodigo().equals("")) {
						setMensaje("No se puede insertar las jornadas ya que la ficha de informacion bnsica no se ha guardado");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (sede.getEstado().equals("")) {
						insertarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (sede.getEstado().equals("1")) {
						actualizarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					break;
				case 3:
					if (institucion2 == null
							|| institucion2.getInscodigo().equals("")) {
						setMensaje("No se puede insertar el espacio ya que la ficha de informacion bnsica no se ha guardado");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (espacio.getEstado().equals("")) {
						insertarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (espacio.getEstado().equals("1")) {
						actualizarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					break;
				case 6:
					if (institucion2 == null
							|| institucion2.getInscodigo().equals("")) {
						setMensaje("No se puede insertar el transporte ya que la ficha de informacion bnsica no se ha guardado");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (transporte.getEstado().equals("")) {
						insertarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (transporte.getEstado().equals("1")) {
						actualizarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					break;
				case 7:
					if (institucion2 == null
							|| institucion2.getInscodigo().equals("")) {
						setMensaje("No se puede insertar el servicio de cafeteria ya que la ficha de informacion bnsica no se ha guardado");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (cafeteria.getEstado().equals("")) {
						insertarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					if (cafeteria.getEstado().equals("1")) {
						actualizarRegistro(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo2=" + tipo);
					}
					break;
				case 8:
					// System.out.println("Entra al form ");
					return sig;
				}
			}
			return er;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (institucionDAO != null)
				institucionDAO.cerrar();
		}
	}

	public boolean compararFichas(int n) {
		switch (n) {
		case 1:
			return institucionDAO.compararBeans(institucion, institucion2);
		case 12:
			return institucionDAO.compararBeans(jornada, jornada2);
		case 2:
			return institucionDAO.compararBeans(sede, sede2);
		case 3:
			return institucionDAO.compararBeans(espacio, espacio2);
		case 13:
			return institucionDAO.compararBeans(nivel, nivel2);
		case 6:
			return institucionDAO.compararBeans(transporte, transporte2);
		case 7:
			return institucionDAO.compararBeans(cafeteria, cafeteria2);
		}
		return true;
	}

	public boolean actualizarRegistro(HttpServletRequest request)
			throws ServletException, IOException {
		switch (tipo) {
		case 1:
			if (!compararFichas(1)) {
				if (!institucion.getInscoddane().equals(
						institucion2.getInscoddane())) {
					if (validarExistencia(institucion.getInscoddane())) {
						setMensaje("El nuevo codigo DANE no se puede asignar, porque ya ha sido asignado a otro colegio");
						restaurarBeans(request, 1);
						return false;
					}
				}
				if (!institucion.getInscalendario().equals("3"))
					institucion.setInscalendariootro("");
				if (!institucionDAO.actualizar(institucion)) {
					setMensaje(institucionDAO.getMensaje());
					restaurarBeans(request, 1);
					return false;
				}
				recargarBeans(request, 1);
			}
			// JORNADA
			if (compararFichas(12)) {
			} else {
				if (!institucionDAO.actualizar(jornada)) {
					setMensaje(institucionDAO.getMensaje());
					restaurarBeans(request, 12);
					return false;
				}
				// setMensaje("La información fue actualizada satisfactoriamente");
				recargarBeans(request, 12);
			}
			// NIVEL
			if (compararFichas(13)) {
				// setMensaje("La información fue actualizada satisfactoriamente -");
			} else {
				if (!institucionDAO.actualizar(nivel)) {
					setMensaje(institucionDAO.getMensaje());
					restaurarBeans(request, 13);
					return false;
				}
				// setMensaje("La información fue actualizada satisfactoriamente");
				recargarBeans(request, 13);
			}
			setMensaje("La información fue actualizada satisfactoriamente");
			break;
		case 2:
			if (compararFichas(2)) {
				setMensaje("La información fue actualizada satisfactoriamente -");
				session.removeAttribute("nuevoSede");
				session.removeAttribute("nuevoSede2");
				return true;
			}
			if (sede.getSedinternet().equals("0")) {
				sede.setSedinternetprov("");
				sede.setSedinternettipo("0");
			}
			if (!institucionDAO.actualizar(sede)) {
				setMensaje(institucionDAO.getMensaje());
				restaurarBeans(request, 2);
				return false;
			}
			setMensaje("La información fue actualizada satisfactoriamente");
			recargarBeans(request, tipo);
			session.removeAttribute("nuevoSede");
			session.removeAttribute("nuevoSede2");
			break;
		case 3:
			if (compararFichas(3)) {
				setMensaje("La información fue actualizada satisfactoriamente -");
				session.removeAttribute("nuevoEspacio");
				session.removeAttribute("nuevoEspacio2");
				return true;
			}
			if (!institucionDAO.actualizar(espacio)) {
				setMensaje(institucionDAO.getMensaje());
				restaurarBeans(request, 3);
				return false;
			}
			setMensaje("La información fue actualizada satisfactoriamente");
			recargarBeans(request, tipo);
			session.removeAttribute("nuevoEspacio");
			session.removeAttribute("nuevoEspacio2");
			break;
		case 6:
			if (compararFichas(6)) {
				setMensaje("La información fue actualizada satisfactoriamente -");
				session.removeAttribute("nuevoTransporte");
				session.removeAttribute("nuevoTransporte2");
				return true;
			}
			if (!institucionDAO.actualizar(transporte)) {
				setMensaje(institucionDAO.getMensaje());
				restaurarBeans(request, 6);
				return false;
			}
			setMensaje("La información fue actualizada satisfactoriamente");
			recargarBeans(request, tipo);
			session.removeAttribute("nuevoTransporte");
			session.removeAttribute("nuevoTransporte2");
			break;
		case 7:
			if (compararFichas(7)) {
				setMensaje("La información fue actualizada satisfactoriamente -");
				session.removeAttribute("nuevoCafeteria");
				session.removeAttribute("nuevoCafeteria2");
				return true;
			}
			if (!institucionDAO.actualizar(cafeteria)) {
				setMensaje(institucionDAO.getMensaje());
				restaurarBeans(request, 7);
				return false;
			}
			setMensaje("La información fue actualizada satisfactoriamente");
			recargarBeans(request, tipo);
			session.removeAttribute("nuevoCafeteria");
			session.removeAttribute("nuevoCafeteria2");
			break;
		}
		return true;
	}

	/**
	 * inserta las cinco fichas en la base de datos
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */

	public boolean insertarRegistro(HttpServletRequest request)
			throws ServletException, IOException {
		String[] resul;
		String r, s;
		switch (tipo) {
		case 1:
			if (validarExistencia(institucion.getInscoddane())) {
				setMensaje("El codigo DANE es de un colegio ya esta registrado, no se puede registrar");
				return false;
			}
			if (!institucionDAO.insertar(institucion)) {
				setMensaje(institucionDAO.getMensaje());
				return false;
			}
			resul = cursor
					.getFila("select inscodigo from institucion where inscoddane="
							+ institucion.getInscoddane());
			institucion.setInscodigo(resul[0]);
			institucion.setEstado("1");
			recargarBeans(request, 1);
			// JORNADAS
			jornada.setJorcodins(institucion.getInscodigo());
			if (!institucionDAO.insertar(jornada)) {
				setMensaje(institucionDAO.getMensaje());
				return false;
			}
			jornada.setEstado("1");
			recargarBeans(request, 12);
			// NIVEL
			nivel.setNivcodinst(institucion.getInscodigo());
			if (!institucionDAO.insertar(nivel)) {
				setMensaje(institucionDAO.getMensaje());
				return false;
			}
			nivel.setEstado("1");
			recargarBeans(request, 13);
			setMensaje("La información fue ingresada satisfactoriamente");
			break;
		case 2:
			sede.setSedcodins(institucion.getInscodigo());
			s = "select max(sedcodigo)+1 from sede where sedcodins="
					+ sede.getSedcodins();
			resul = cursor.getFila(s);
			if (resul != null && resul[0] != null && !resul[0].equals(""))
				r = resul[0];
			else
				r = "1";
			sede.setSedcodigo(r);
			String[] jer = { institucion.getInscoddepto(),
					institucion.getInscodmun(), institucion.getInscodlocal() };
			if (!institucionDAO.insertar(sede, jer)) {
				setMensaje(institucionDAO.getMensaje());
				return false;
			}
			sede.setEstado("1");
			session.setAttribute("nuevoSede2", (Sede) sede.clone());
			session.removeAttribute("nuevoSede");
			session.removeAttribute("nuevoSede2");
			setMensaje("La información fue ingresada satisfactoriamente");
			break;
		case 3:
			espacio.setEspcodins(institucion.getInscodigo());
			if (!institucionDAO.insertar(espacio)) {
				setMensaje(institucionDAO.getMensaje());
				return false;
			}
			espacio.setEstado("1");
			session.setAttribute("nuevoSede2", (Sede) sede.clone());
			session.removeAttribute("nuevoEspacio");
			session.removeAttribute("nuevoEspacio2");
			setMensaje("La información fue ingresada satisfactoriamente");
			break;
		case 6:
			s = "select max(traCodRuta)+1 from transporte where traCodJerar="
					+ transporte.getTracodjerar();
			resul = cursor.getFila(s);
			if (resul != null && resul[0] != null && !resul[0].equals(""))
				r = resul[0];
			else
				r = "1";
			transporte.setTracodruta(r);
			if (!institucionDAO.insertar(transporte)) {
				setMensaje(institucionDAO.getMensaje());
				return false;
			}
			transporte.setEstado("1");
			session.removeAttribute("nuevoTransporte");
			session.removeAttribute("nuevoTransporte2");
			setMensaje("La información fue ingresada satisfactoriamente");
			break;
		case 7:
			cafeteria.setRescodjerar(institucion.getInscodigo());
			if (!institucionDAO.insertar(cafeteria)) {
				setMensaje(institucionDAO.getMensaje());
				return false;
			}
			cafeteria.setEstado("1");
			session.removeAttribute("nuevoCafeteria");
			session.removeAttribute("nuevoCafeteria2");
			setMensaje("La información fue ingresada satisfactoriamente");
			break;
		}
		return true;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("nuevoInstitucion");
		session.removeAttribute("nuevoInstitucion2");
		session.removeAttribute("nuevoJornada");
		session.removeAttribute("nuevoJornada2");
		session.removeAttribute("nuevoSede");
		session.removeAttribute("nuevoSede2");
		session.removeAttribute("nuevoEspacio");
		session.removeAttribute("nuevoEspacio2");
		session.removeAttribute("nuevoNivel");
		session.removeAttribute("nuevoNivel2");
		session.removeAttribute("nuevoTransporte");
		session.removeAttribute("nuevoTransporte2");
		session.removeAttribute("nuevoCafeteria");
		session.removeAttribute("nuevoCafeteria2");
	}

	/**
	 * Asigna a variables locales el contenido de los beans de session
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean true=asigno todos los beans; false=no existe alguno de
	 *         los beans en la session
	 */

	public void asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		institucion = (Institucion) session.getAttribute("nuevoInstitucion");
		if (institucion.getEstado().equals("1"))
			institucion2 = (Institucion) session
					.getAttribute("nuevoInstitucion2");
		jornada = (Jornada) session.getAttribute("nuevoJornada");
		if (jornada.getEstado().equals("1"))
			jornada2 = (Jornada) session.getAttribute("nuevoJornada2");
		sede = (Sede) session.getAttribute("nuevoSede");
		if (sede.getEstado().equals("1"))
			sede2 = (Sede) session.getAttribute("nuevoSede2");
		espacio = (Espacio) session.getAttribute("nuevoEspacio");
		if (espacio.getEstado().equals("1"))
			espacio2 = (Espacio) session.getAttribute("nuevoEspacio2");
		nivel = (Nivel) session.getAttribute("nuevoNivel");
		if (nivel.getEstado().equals("1"))
			nivel2 = (Nivel) session.getAttribute("nuevoNivel2");
		transporte = (Transporte) session.getAttribute("nuevoTransporte");
		if (transporte.getEstado().equals("1"))
			transporte2 = (Transporte) session.getAttribute("nuevoTransporte2");
		cafeteria = (Cafeteria) session.getAttribute("nuevoCafeteria");
		if (cafeteria.getEstado().equals("1"))
			cafeteria2 = (Cafeteria) session.getAttribute("nuevoCafeteria2");
	}

	/**
	 * Busca en la base de datos un registro que coincida con el id ingresado
	 * por el usuario
	 * 
	 * @return boolean
	 */

	public boolean validarExistencia(String cod) {
		String sentencia = "Select InsCodDANE from institucion where InsCodDANE= "
				+ cod;
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Referencia al bean del usuario con la información proporcionada por el
	 * bean de respaldo
	 * 
	 * @param int n
	 * @param HttpServletRequest
	 *            request
	 */
	public void restaurarBeans(HttpServletRequest request, int n)
			throws ServletException, IOException {
		switch (n) {
		case 1:
			session.setAttribute("nuevoInstitucion",
					(Institucion) institucion2.clone());
			break;
		case 12:
			session.setAttribute("nuevoJornada", (Jornada) jornada2.clone());
			break;
		case 2:
			session.setAttribute("nuevoSede", (Sede) sede2.clone());
			break;
		case 3:
			session.setAttribute("nuevoEspacio", (Espacio) espacio2.clone());
			break;
		case 13:
			session.setAttribute("nuevoNivel", (Nivel) nivel2.clone());
			break;
		case 6:
			session.setAttribute("nuevoTransporte",
					(Transporte) transporte2.clone());
			break;
		}
	}

	/**
	 * Referencia al bean de respaldo con la nueva información proporcionada por
	 * el bean modificado por el usuario
	 * 
	 * @param int n
	 * @param HttpServletRequest
	 *            request
	 */
	public void recargarBeans(HttpServletRequest request, int n)
			throws ServletException, IOException {
		switch (n) {
		case 1:
			session.setAttribute("nuevoInstitucion2",
					(Institucion) institucion.clone());
			break;
		case 12:
			session.setAttribute("nuevoJornada2", (Jornada) jornada.clone());
			break;
		case 2:
			session.setAttribute("nuevoSede2", (Sede) sede.clone());
			break;
		case 3:
			session.setAttribute("nuevoEspacio2", (Espacio) espacio.clone());
			break;
		case 13:
			session.setAttribute("nuevoNivel2", (Nivel) nivel.clone());
			break;
		case 6:
			session.setAttribute("nuevoTransporte2",
					(Transporte) transporte.clone());
			break;
		}
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
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
	 **/
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
	 **/
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
	 **/
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
	 **/
	public String getMensaje() {
		return mensaje;
	}
}