package siges.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Util;
import siges.dao.Cursor;
import siges.util.Acceso;
import siges.util.Logger;
import siges.login.beans.Login;
import siges.usuario.dao.UsuarioDAO;
import siges.exceptions.InternalErrorException;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		27/04/2018		JORGE CAMACHO		CODIGO ESPAGUETI Y SE EDITÓ LA FUNCIÓN insertarRegistro() para cambiar la contraseña temporal
 * 
*/


/**
 * Nombre: ControllerEditarContrasena<br>
 * Descripcinn: Editar Contrasena<br>
 * Funciones de la pngina: Se encarga de controlar el proceso de cambiar
 * contrasena<br>
 * Entidades afectadas: Usuario<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class ControllerEditarContrasena extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Objeto que maneja las sentencias sql
	private Cursor cursor;

	private Util util;

	private UsuarioDAO usuDAO;

	// Mensaje en caso de error
	private String mensaje;

	private boolean band;

	// Variable que inidica si hay o no errores en la validacion de los datos del formulario
	private boolean err;
	
	// Nombre de la pagina a la que ira despues de ejecutar  los comandos de esta
	private String sig;

	private ResourceBundle rbUsuario;

	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String boton;
		HttpSession session = request.getSession();
		
		rbUsuario = ResourceBundle.getBundle("usuarios");
		sig = "/usuario/EditarContrasena.jsp";
		
		String p_home = this.getServletContext().getInitParameter("home");
		String p_error = this.getServletContext().getInitParameter("error");
		String p_login = this.getServletContext().getInitParameter("login");
		String p_inte = this.getServletContext().getInitParameter("integrador");
		
		err = false;
		band = true;
		mensaje = null;
		cursor = new Cursor();
		
		try {
			
			util = new Util(cursor);
			usuDAO = new UsuarioDAO(cursor);
			boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("Cancelar");

			if (boton.equals("Cancelar")) {
				return p_home;
			}

			if (boton.equals("Guardar")) {
				
				if (request.getParameter("tipo") == null || request.getParameter("tipo").equals("")) {
					setMensaje("Acceso denegado no hay una ficha definida");
					request.setAttribute("mensaje", getMensaje());
					return p_error;
				}

				int tipo = Integer.parseInt((String) request.getParameter("tipo"));

				switch (tipo) {
				case 1:
					Login login = (Login) session.getAttribute("login");
					
					insertarRegistro(session, request, tipo);
					request.setAttribute("mensaje", getMensaje());
					
					if (login == null) {
						return p_login;
					} else {
						return p_home;
					}

				}
			}
			
			return p_error;
			
		} catch (Exception e) {
			e.printStackTrace();
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
	 * Funcinn: Cambiar Contrasena<br>
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void insertarRegistro(HttpSession session, HttpServletRequest request, int tipo) throws ServletException, IOException, InternalErrorException {

		Login login = (Login) session.getAttribute("login");
		String nuevo = (String) request.getParameter("nuevo");
		String actual = (String) request.getParameter("actual");
		String numdoc = (String) request.getParameter("documento");
		
		try {
			
			// Si el objeto es nulo se debe a que se desea cambiar una contraseña temporal
			if (login == null) {
				
				// Con los datos que vienen en el formulario de cambio de contraseña se valida si el usuario se autentica 
				String[][] params = Acceso.autorizado(numdoc, actual);
				
				if (params == null) {
					setMensaje("La combinación usuario / contraseña no es válida.");
					return;
				} else {					
					login = Acceso.getUsuario(numdoc, actual, params, null, null, null, null);
				}
			}

			switch (tipo) {
			case 1:
				if (!validarExistencia(numdoc, login.getUsuarioJerar(), actual)) {
					setMensaje("El usuario no existe");
					return;
				}
				
				if (!usuDAO.actualizar(nuevo, numdoc)) {
					setMensaje(usuDAO.getMensaje());
					return;
				}
				
				setMensaje("La información fue actualizada satisfactoriamente");
				Logger.print(login.getUsuarioId(), "Cambio de Contrasena para el usuario: "	+ login.getUsuarioId(), 1, 1, this.toString());
				break;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			setMensaje("No fue posible actualizar la contraseña.");
		}
		
	}

	/**
	 * Funcinn: Encripta una cadena String a MD5
	 * 
	 * @param Usuario
	 * 
	 * @return String
	 **/
	public static String md5(String clear) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(clear.getBytes());
		int size = b.length;
		StringBuffer h = new StringBuffer(size);
		// algoritmo y arreglo md5
		for (int i = 0; i < size; i++) {
			int u = b[i] & 255;
			if (u < 16) {
				h.append("0" + Integer.toHexString(u));
			} else {
				h.append(Integer.toHexString(u));
			}
		}
		// clave encriptada
		return h.toString();
	}

	/**
	 * Funcinn: Validar que el usuario existe<br>
	 * 
	 * @param String
	 *            doc
	 * @param String
	 *            jerar
	 * @param String
	 *            actual
	 * @return
	 */

	public boolean validarExistencia(String doc, String jerar, String actual) throws InternalErrorException {
		
		Object o[];
		Collection list;
		
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = doc;
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = jerar;
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		
		try {
			o[1] = md5(actual.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		list.add(o);
		Collection list2 = util.getFiltro(rbUsuario.getString("ExisteUsuario"), list);
		if (!list2.isEmpty()) {
			// Si existe
			return true;
		}

		return false;
		
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
			ir(2, s, request, response);
		
	}

	/**
	 * Funcinn: Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
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
		band = false;
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}

	/**
	 * Funcinn: Retorna una variable que contiene los mensajes que se van a
	 * enviar a la vista
	 * 
	 * @return String
	 */
	public String getMensaje() {
		return mensaje;
	}
	
}
