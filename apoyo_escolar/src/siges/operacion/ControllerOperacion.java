package siges.operacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.boletines.dao.ReporteLogrosDAO;
import siges.dao.Cursor;
import siges.estudiante.beans.Basica;
import siges.estudiante.dao.EstudianteDAO;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * Nombre: ControllerFiltroEdit<BR>
 * Descripcinn: Controla la vista del formulario de filtro de estudiantes <BR>
 * Funciones de la pngina: Poner los dartos del formulario y redirigir el
 * control a la vista <BR>
 * Entidades afectadas: Estudiante <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerOperacion extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private EstudianteDAO estudianteDAO;//
	private ReporteLogrosDAO reportesDAO;
	private Basica basica;
	private Login login;
	private ResourceBundle rb;

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
		rb = ResourceBundle.getBundle("siges.estudiante.bundle.estudiante");
		String sig;
		String ant;
		String er;
		String home;
		int tipo;
		sig = "/operaciones/FiltroOperaciones.jsp";
		er = "/error.jsp";
		cursor = new Cursor();
		err = false;
		mensaje = null;
		String script = request.getParameter("taOperacion");
		String descripcionCambio = request.getParameter("taDescripcion");
		estudianteDAO = new EstudianteDAO(cursor);
		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			return er;
		}
		try {
			if(script != null && !script.equals("")){
				String user = login != null ? login.getUsuarioId() : "";
				boolean resultado = estudianteDAO.ejecutarScript(script, user,descripcionCambio);
				if(resultado){
					setMensaje("Consulta ejecutada satisfactoriamente");
				}else{
					setMensaje("Error ejecutando la consulta: " + estudianteDAO.getMensaje());
				}				
				request.setAttribute("mensaje", mensaje);
			}
			
		} catch (Exception e) {
			setMensaje("Error ejecutando la consulta: " + e.getMessage());
			request.setAttribute("mensaje", mensaje);
		}
		
		return sig;
	}

	/**
	 * Trae de la sesion la credencial del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) request.getSession().getAttribute("login");
		
		return true;
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