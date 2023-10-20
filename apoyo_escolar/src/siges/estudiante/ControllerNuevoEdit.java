package siges.estudiante;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.estudiante.beans.AcademicaVO;
import siges.estudiante.beans.AsistenciaVO;
import siges.estudiante.beans.AtencionEspecial;
import siges.estudiante.beans.Basica;
import siges.estudiante.beans.Convivencia;
import siges.estudiante.beans.Familiar;
import siges.estudiante.dao.EstudianteDAO;
import siges.login.beans.Login;
import siges.util.Recursos;

/**
 * Nombre: ControllerNuevoEdit<BR>
 * Descripcinn: Controla la vista del formulario de edicinn de datos de
 * estudiante <BR>
 * Funciones de la pngina: Redirige a la pagina de vista de las categorias de
 * datos del estudiante <BR>
 * Entidades afectadas: Las tablas de informacion del Estudiante <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerNuevoEdit extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private EstudianteDAO estudianteDAO;//
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;
	private static final int TIPO_BASICA = 1;
	private static final int TIPO_FAMILIAR = 2;
	private static final int TIPO_SALUD = 3;
	private static final int TIPO_CONVIVENCIA = 4;
	private static final int TIPO_ACADEMICA = 5;
	private static final int TIPO_ASISTENCIA = 6;
	private static final int TIPO_ATENCION = 7;
	private static final int TIPO_FOTO = 8;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Basica basica;
		Login login;
		HttpSession session = request.getSession();
		rb = ResourceBundle.getBundle("siges.estudiante.bundle.estudiante");
		String ant;// pagina a la que ira en caso de que no se pueda procesar
					// esta pagina
		String er;// nombre de la pagina a la que ira si hay errores
		String home;// nombre de la pagina a la que ira si hay errores
		int tipo;
		String sig = "/estudiante/NuevoBasica.jsp";
		String sig1 = "/estudiante/NuevoBasica.jsp";
		String sig2 = "/estudiante/NuevoFamiliar.jsp";
		String sig3 = "/estudiante/NuevoSalud.jsp";
		String sig4 = "/estudiante/NuevoConvivencia.jsp";
		String sig5 = "/estudiante/NuevoAcademica.jsp";
		String sig6 = "/estudiante/NuevoAsistencia.jsp";
		String sig7 = "/estudiante/NuevoAtencion.jsp";
		String sig8 = "/subirFotografia/Nuevo.jsp";
		cursor = new Cursor();
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		err = false;
		mensaje = null;
		String boton;
		try {
			boton = (request.getParameter("cmd") != null) ? request
					.getParameter("cmd") : new String("Cancelar");
			estudianteDAO = new EstudianteDAO(cursor);
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				session.removeAttribute("editar");
				session.removeAttribute("filtroConvivencia");
				tipo = 1;
			} else
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			basica = (Basica) session.getAttribute("nuevoBasica2");
			login = (Login) session.getAttribute("login");
			switch (tipo) {
			case TIPO_BASICA:// basica
				editarBasica(request, login);
				sig = sig1;
				break;
			case TIPO_FAMILIAR:
				editarFamiliar(request, basica);
				sig = sig2;
				break;
			case TIPO_SALUD:
				sig = sig3;
				break;
			case TIPO_CONVIVENCIA:
				// System.out.println("** TIPO_CONVIVENCIA **");
				editarConvivencia(request, basica);
				sig = sig4;
				break;
			case TIPO_ACADEMICA://
				editarAcademica(request, basica);
				sig = sig5;
				break;
			case TIPO_ASISTENCIA:// ASISTENCIA
				editarAsistencia(request, basica);
				sig = sig6;
				break;
			case TIPO_ATENCION:
				editarAtencion(request, basica);
				sig = sig7;
				break;
			case TIPO_FOTO:
				sig = sig8;
				break;
			}
			if (err) {
				request.setAttribute("mensaje", mensaje);
				return er;
			}
			return sig;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (estudianteDAO != null)
				estudianteDAO.cerrar();
		}
	}

	public void editarAsistencia(HttpServletRequest request, Basica basica)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		String r = (request.getParameter("r") != null) ? request
				.getParameter("r") : new String("");
		String tipo = "1";
		if (boton.equals("Editar") && basica != null) {
			AsistenciaVO asistenciaVO = estudianteDAO.asignarAsistencia(basica,
					tipo, r);
			if (asistenciaVO != null) {
				asistenciaVO.setAsiEstado("1");
				session.setAttribute("asistenciaVO", asistenciaVO);
				session.setAttribute("asistenciaVO2", asistenciaVO.clone());
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		if (boton.equals("Nuevo")) {
			session.removeAttribute("asistenciaVO");
			session.removeAttribute("asistenciaVO2");
		}
		if (boton.equals("Eliminar") && basica != null) {
			if (estudianteDAO.eliminarAsistencia(basica, tipo, r)) {
				session.removeAttribute("asistenciaVO");
				session.removeAttribute("asistenciaVO2");
				request.setAttribute("mensaje",
						"La información fue eliminada satisfactotiamente");
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		request.setAttribute("listaMotivoAusencia",
				estudianteDAO.getComboMotivo());
		request.setAttribute("filtroAsistencia",
				estudianteDAO.getAsistencias(tipo, basica));
		request.setAttribute("lPeriodo",
				Recursos.recursoEstatico[Recursos.PERIODO]);
	}

	public void editarAcademica(HttpServletRequest request, Basica basica)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		String r = (request.getParameter("r") != null) ? request
				.getParameter("r") : new String("");
		if (boton.equals("Editar") && basica != null) {
			AcademicaVO academicaVO = estudianteDAO.asignarAcademica(basica, r);
			if (academicaVO != null) {
				academicaVO.setAcaEstado("1");
				session.setAttribute("academicaVO", academicaVO);
				session.setAttribute("academicaVO2", academicaVO.clone());
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		if (boton.equals("Nuevo")) {
			session.removeAttribute("academicaVO");
			session.removeAttribute("academicaVO2");
		}
		if (boton.equals("Eliminar") && basica != null) {
			if (estudianteDAO.eliminarAcademica(basica, r)) {
				session.removeAttribute("academicaVO");
				session.removeAttribute("academicaVO2");
				request.setAttribute("mensaje",
						"La información fue eliminada satisfactotiamente");
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		request.setAttribute("filtroAcademica",
				estudianteDAO.getAcademicas(basica));
	}

	/**
	 * Pone en la session los datos necesarios para pintar el fomulario de
	 * edicion de convivencia
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void editarConvivencia(HttpServletRequest request, Basica basica)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		String r = (request.getParameter("r") != null) ? request
				.getParameter("r") : new String("");
		if (boton.equals("Editar") && basica != null) {
			Convivencia convivencia = estudianteDAO.asignarConvivencia(r);
			if (convivencia != null) {
				convivencia.setEstado("1");
				session.setAttribute("nuevoConvivencia", convivencia);
				session.setAttribute("nuevoConvivencia2", convivencia.clone());
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		if (boton.equals("Nuevo")) {
			session.removeAttribute("nuevoConvivencia");
			session.removeAttribute("nuevoConvivencia2");
			session.removeAttribute("filtroConvivencia");
		}
		if (boton.equals("Eliminar") && basica != null) {
			if (estudianteDAO.eliminarConvivencia(r)) {
				session.removeAttribute("nuevoConvivencia");
				session.removeAttribute("nuevoConvivencia2");
				request.setAttribute("mensaje",
						"La convivencia fue eliminada satisfactotiamente");
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		try {
			if (basica != null) {
				int z = 0;
				list = new ArrayList();
				o = new Object[2];
				o[z++] = new Integer(java.sql.Types.INTEGER);
				o[z++] = basica.getEstcodigo();
				list.add(o);
				session.setAttribute("filtroConvivencia", estudianteDAO
						.getFiltro(rb.getString("filtroEstudianteConvivencia"),
								list));
				// agregar combos de clase de conflicto y tipo de conflicto
				session.setAttribute("filtroClaseConflicto", estudianteDAO
						.getFiltro(rb.getString("filtroClaseConflicto")));
				session.setAttribute("filtroTipoConflicto", estudianteDAO
						.getFiltro(rb.getString("filtroTipoConflicto")));
			}
		} catch (Throwable th) {
			throw new ServletException(th);
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
		HttpSession session = request.getSession();
		session.removeAttribute("nuevoBasica");
		session.removeAttribute("nuevoFamiliar");
		session.removeAttribute("nuevoSalud");
		session.removeAttribute("nuevoConvivencia");
		session.removeAttribute("nuevoBasica2");
		session.removeAttribute("nuevoFamiliar2");
		session.removeAttribute("nuevoSalud2");
		session.removeAttribute("nuevoConvivencia2");
	}

	/**
	 * Pone en la session los datos de informacion basica necesarios para la
	 * vista de formulario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void editarBasica(HttpServletRequest request, Login login)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			Collection list;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			request.setAttribute(
					"filtroSedeF",
					estudianteDAO.getFiltro(
							rb.getString("filtroSedeInstitucion"), list));
			request.setAttribute(
					"filtroJornadaF",
					estudianteDAO.getFiltro(
							rb.getString("filtroSedeJornadaInstitucion"), list));
			request.setAttribute(
					"filtroMetodologiaF",
					estudianteDAO.getFiltro(
							rb.getString("listaMetodologiaInstitucion"), list));
			request.setAttribute("filtroGradoF2", estudianteDAO.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list));
			request.setAttribute("filtroGrupoF", estudianteDAO.getFiltro(
					rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
					list));
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Pone en session los datos necesarios para pintar el formulario de
	 * ediciion de informacion familiar
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void editarFamiliar(HttpServletRequest request, Basica basica)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (basica != null && !basica.getEstcodfamilia().equals("")) {
			Familiar familiar = (Familiar) session
					.getAttribute("nuevoFamiliar2");

			if (familiar != null) {
				if (!familiar.getFamcodigo().equals(basica.getEstcodfamilia())) {
					familiar = estudianteDAO.asignarFamiliar(basica
							.getEstcodfamilia());
					session.setAttribute("nuevoFamiliar", familiar);
					session.setAttribute("nuevoFamiliar2", familiar.clone());
				}
			} else {
				familiar = estudianteDAO.asignarFamiliar(basica
						.getEstcodfamilia());
				session.setAttribute("nuevoFamiliar", familiar);
				session.setAttribute("nuevoFamiliar2", familiar.clone());
			}
		}
	}

	/**
	 * Pone en la session los datos necesarios para pintar el fomulario de
	 * edicion de convivencia
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void editarAtencion(HttpServletRequest request, Basica basica)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		String r = (request.getParameter("r") != null) ? request
				.getParameter("r") : new String("");
		if (boton.equals("Editar") && basica != null) {
			AtencionEspecial atencion = estudianteDAO.asignarAtencion(r);
			if (atencion != null) {
				atencion.setEstado("1");
				session.setAttribute("nuevoAtencion", atencion);
				session.setAttribute("nuevoAtencion2", atencion.clone());
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		if (boton.equals("Nuevo")) {
			session.removeAttribute("nuevoAtencion");
			session.removeAttribute("nuevoAtencion2");
			session.removeAttribute("filtroAtencion");
		}
		if (boton.equals("Eliminar") && basica != null) {
			if (estudianteDAO.eliminarAtencion(r)) {
				session.removeAttribute("nuevoAtencion");
				session.removeAttribute("nuevoAtencion2");
				request.setAttribute("mensaje",
						"La atencinn especial del estudiante fue eliminada satisfactotiamente");
			} else
				setMensaje(estudianteDAO.getMensaje());
		}
		try {
			if (basica != null) {
				int z = 0;
				list = new ArrayList();
				o = new Object[2];
				o[z++] = new Integer(java.sql.Types.INTEGER);
				o[z++] = basica.getEstcodigo();
				list.add(o);
				session.setAttribute("filtroAtencion", estudianteDAO.getFiltro(
						rb.getString("AtencionFiltro"), list));
			}
		} catch (Throwable th) {
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
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}
}