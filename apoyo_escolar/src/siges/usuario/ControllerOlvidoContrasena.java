package siges.usuario;

import java.io.IOException;
import java.util.ResourceBundle;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.usuario.beans.Usuario;
import siges.usuario.dao.UsuarioDAO;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		08/05/2018		JORGE CAMACHO		CREACION DEL SERVLET
 * 
*/

public class ControllerOlvidoContrasena extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Variable que indica si hay o no errores en la validacion de los datos del formulario
	private boolean error;

	// Objeto que maneja las sentencias sql
	private Cursor cursor;

	// Mensaje en caso de error
	private String mensaje;

	private UsuarioDAO usuDAO;

	
	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 */
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int tipo;
		String numeroDocumento;
		HttpSession session = request.getSession();
		
		String p_login = this.getServletContext().getInitParameter("login");
		String p_mensaje = this.getServletContext().getInitParameter("mensaje2");
		String p_olvidoContrasena = this.getServletContext().getInitParameter("olvidoContrasena"); 

		error = false;
		mensaje = null;
		cursor = new Cursor();
		
		try {

			usuDAO = new UsuarioDAO(cursor);
			
			if (request.getParameter("tipo") == null || ((String) request.getParameter("tipo")).equals("")) {
				
				tipo = 0;
				
			} else {
				
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
				
			}
			
			switch (tipo) {
			case 0:
				
				return p_olvidoContrasena;
				
			case 1:
				
				numeroDocumento = (String) request.getParameter("documento");
				Usuario objUsuario = usuDAO.asignarPersonalUsuario(numeroDocumento);
				
				if (objUsuario != null) {
					
					if (objUsuario.getPercorreoinstitucional() == null || objUsuario.getPercorreoinstitucional().equals("")) {
						
						error = true;
						setMensaje("Usted no tiene registrado un correo electrónico. Por favor actualice sus datos contactándose a la Mesa de Ayuda.");
						request.setAttribute("mensaje", getMensaje());
						
						return p_mensaje;
						
					} else {
						
						ResourceBundle props = ResourceBundle.getBundle("mail");
						String correoApoyo = props.getString("mail.fromAddress");
						
						if(objUsuario.getPercorreoinstitucional().equals(correoApoyo)) {
							
							usuDAO.actualizarUsuario(objUsuario);
							setMensaje("Usted no tiene registrado un correo electrónico. Por favor actualice sus datos contactándose a la Mesa de Ayuda o enviando un correo a apoyoescolardexon@educacionbogota.gov.co.");
							request.setAttribute("mensaje", getMensaje());
							
						} else {
							
							usuDAO.actualizarUsuario(objUsuario);
							setMensaje("Se envió un mensaje con la nueva contraseña a su Correo Institucional " +
										objUsuario.getPercorreoinstitucional().substring(0, 3).toLowerCase() + "xxxx" + 
										objUsuario.getPercorreoinstitucional().substring(objUsuario.getPercorreoinstitucional().indexOf('@'), objUsuario.getPercorreoinstitucional().length()).toLowerCase() +  
										" registrado en el sistema.");
							request.setAttribute("mensaje", getMensaje());
							
						}
						
						// Es necesario setear esta variable para que al volver a la página de login no tenga valores preestablecidos
						session.setAttribute("login", null);
						return p_mensaje;
					}
					
				} else {
					
					error = true;
					setMensaje("El Número de Documento no se encuentra registrado en el sistema.");
					request.setAttribute("mensaje", getMensaje());
					
					return p_mensaje;
				}
				
			}
			
			return p_login;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
		}
		
	}

	public void enviarCorreoRecordatorioContrasena(Usuario objUsuario) {
		
		try {
			
			usuDAO.actualizarUsuario(objUsuario);
			
		} catch (Exception e) {
			e.printStackTrace();
			setMensaje("No fue posible enviar la nueva contraseña.");
		}
		
	}

	/**
	 * Encripta una cadena String a MD5
	 **/
	public static String md5(String clear) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		byte[] b = md.digest(clear.getBytes());
		int size = b.length;
		StringBuffer h = new StringBuffer(size);
		
		for (int i = 0; i < size; i++) {
			int u = b[i] & 255;
			if (u < 16) {
				h.append("0" + Integer.toHexString(u));
			} else {
				h.append(Integer.toHexString(u));
			}
		}
		
		// Clave encriptada
		return h.toString();
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String s = process(request, response);
		
		if (s != null && !s.equals(""))
			ir(2, s, request, response);
		
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 */
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
		
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	 */
	public void setMensaje(String s) {
		
		if (error)
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION:\n\n" + s;
		else
			mensaje = s + "\n";
		
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la vista
	 */
	public String getMensaje() {
		return mensaje;
	}
	
}
