package siges.estudiante;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.estudiante.beans.Basica;
import siges.estudiante.beans.Familiar;
import siges.estudiante.beans.FiltroBean;
import siges.estudiante.beans.Salud;
import siges.estudiante.dao.EstudianteDAO;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.util.Logger;
import siges.util.beans.ReporteVO;

/**
 * Nombre: ControllerFiltroSave<BR>
 * Descripción: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la página: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Estudiante <BR>
 * Fecha de modificación: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerFiltroRegistrarInactivar extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private EstudianteDAO estudianteDAO;//
	private Basica basica;
	private Login login;
	private FiltroBean filtro;
	private ResourceBundle rb;
	// VARIABLE PARA GENERAR REPORTE
	private HttpServletRequest request;
	private String er;

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
//		String er;
		String home;
//		String sentencia;
//		String boton;
//		String id;
		sig = "/estudiante/ControllerFiltroInactivar.do?cmd=!";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		cursor = new Cursor();
		err = false;
		mensaje = null;

		try {
			estudianteDAO = new EstudianteDAO(cursor);
			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesión para el usuario");
				request.setAttribute("mensaje", mensaje);
				return er;
			}
			asignarListaNuevo(request);
			registrarAlumno(request);
			//Limpiamos las listas
			borrarBeans(request);
			filtro = new FiltroBean();
			request.getSession().setAttribute("filtroEst", filtro);
			Logger.print(login.getUsuarioId(), "Registro Estudiante de HOJAS DE VIDA, m�dulo de Adicionar Estudiante. ",
					7, 1, this.toString());
			return sig;
		} catch (Exception e) {
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (estudianteDAO != null)
				estudianteDAO.cerrar();
		}
	}

	public void registrarAlumno(HttpServletRequest request)
			throws ServletException, IOException {
		//1. Validamos si el estudiantes ya está registrado.
		boolean resultado;
		String mensaje="";
		if(estudianteDAO.getEstudiante(filtro.getTipoDocumento(),filtro.getId())){
			//2. Si existe debemos actualizarle la ubicacion
			resultado = estudianteDAO.inactivarGrupoAlumno(filtro);
			mensaje = " El estudiante fue actualizado satisfactoriamente.";
		}else{
			//2. Si no existe, lo registramos
			resultado=true;
			mensaje = " El estudiante no se encuentra registrado.";
		}
		if (resultado) {
			request.setAttribute("mensaje","Proceso exitoso."+mensaje);
		} else {
			request.setAttribute("mensaje","Se presento un error.");
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
		request.getSession().removeAttribute("listaGrupo");
		request.getSession().removeAttribute("listaGrado");
		request.getSession().removeAttribute("listaMetodo");
		request.getSession().removeAttribute("listaJornada");
		request.getSession().removeAttribute("listaSede");
		request.getSession().removeAttribute("filtroEst");
	}

	/**
	 * Trae de sesion los datos de la credencial del usuario y el formulario de
	 * filtro
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) request.getSession().getAttribute("login");
		filtro = (FiltroBean) request.getSession().getAttribute("filtroEst");
		return true;
	}


	public void asignarListaNuevo(HttpServletRequest request)
			throws ServletException, IOException {

		try {
			Long inst = new Long(Long.parseLong(login.getInstId()));
			filtro.setInstitucion(inst);

//			request.getSession().setAttribute("filtroEstudiantes",
//					estudianteDAO.getBuscarEstudiante(filtro));

		} catch (Throwable th) {
			System.out.println("error " + th);
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