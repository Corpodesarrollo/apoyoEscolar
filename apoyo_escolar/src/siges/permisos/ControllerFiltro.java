package siges.permisos;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.dao.*;
import siges.permisos.dao.*;
import siges.login.beans.Login;
import siges.util.Recursos;
import siges.util.Logger;

/**
 * Nombre: ControllerFiltro<br>
 * Descripcinn: Filtro de Perfiles<br>
 * Funciones de la pngina: Controla la vista del formulario a la hora de filtrar
 * Privilegios<br>
 * Entidades afectadas: Permisos, Perfil<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class ControllerFiltro extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String mensaje;// mensaje en caso de error

	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario

	private Cursor cursor;// objeto que maneja las sentencias sql

	private Util util;

	private PermisosDAO permDAO;

	private Login login;

	private HttpSession session;

	private Collection colGrupo;

	private Collection list;

	private Object o[];

	private ResourceBundle rbPermisos;

	private Integer cadena = new Integer(java.sql.Types.VARCHAR);

	private Integer entero = new Integer(java.sql.Types.INTEGER);


	/**
	 * Funcinn: Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		rbPermisos = ResourceBundle.getBundle("permisos");
		String sig = "/permisos/NuevoPermisos.jsp";
		String boton;
		String id;
		String[] id2;
		cursor = new Cursor();
		err = false;
		mensaje = null;

		try {
			util = new Util(cursor);
			permDAO = new PermisosDAO(cursor);
			asignarBeans(request);
			boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("");

			if (boton.equals("cambio")) {
				id = (request.getParameter("id") != null) ? request.getParameter("id") : new String("");
				request.setAttribute("id", id);
				list = new ArrayList();
				o = new Object[2];
				o[0] = entero;
				o[1] = login.getNivel();
				list.add(o);
				colGrupo = util.getFiltro(rbPermisos.getString("ConsultaGrupo"), list);
				request.setAttribute("filtroGrupo", colGrupo);
				Logger.print(login.getUsuarioId(), "Resultados de Grupo de servicio." + request.getParameter("id"), 2, 1, this.toString());
			}

			if (boton.equals("Guardar")) {
				int count = Integer.parseInt(request.getParameter("total"));
				permDAO.eliminarPermisos(request.getParameter("id"));
				for (int i = 0; i < count; i++) {
					id2 = request.getParameterValues("id" + i);
					if (id2 != null) {
						list = new ArrayList();
						o = new Object[2];
						o[0] = cadena;
						o[1] = request.getParameter("id");
						list.add(o);
						o = new Object[2];
						o[0] = cadena;
						o[1] = id2[0];
						list.add(o);
						o = new Object[2];
						o[0] = entero;
						o[1] = "" + id2.length;
						list.add(o);
						cursor.registrar(rbPermisos.getString("ServicioPerfilInsertar"), list);
					}
				}

				Recursos.setRecurso(Recursos.SERVICIOPERFIL);
				setMensaje("La información fue ingresada satisfactoriamente");
				Logger.print(login.getUsuarioId(), "Modificacinn de Privilegios para el perfil: " + request.getParameter("id"), 6, 1, this.toString());
				request.setAttribute("mensaje", mensaje);
			}
			//nuevo
			return sig;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
	}

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException {
		session.removeAttribute("nuevoPermiso");
	}

	/**
	 * Funcinn: Inserta los valores por defecto para el bean de información
	 * bnsica con respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request) throws ServletException, IOException {
		if (request.getSession().getAttribute("login") != null) {
			login = (Login) request.getSession().getAttribute("login");
			return true;
		}
		return true;
	}

	/**
	 * Funcinn: Inserta los valores por defecto para el bean de información
	 * bnsica con respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void asignarLista(HttpServletRequest request) throws ServletException, IOException {
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Funcinn: Redirige el control a otro servlet
	 * 
	 * @param int
	 *            a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Funcinn: Concatena el parametro en una variable que contiene los mensajes
	 * que se van a enviar a la vista
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

}