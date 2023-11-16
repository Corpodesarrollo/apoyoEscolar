package siges.estudiante;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.persistence.Convert;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import siges.boletines.dao.ReporteLogrosDAO;
import siges.dao.Cursor;
import siges.estudiante.beans.Basica;
import siges.estudiante.dao.EstudianteDAO;
import siges.login.beans.Login;
import siges.util.Logger;
import util.BitacoraCOM;

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
public class ControllerFiltroAdd extends HttpServlet {
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
	
	private BitacoraCOM bitacoraCOM;

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
		sig = "/addestudiante/Filtro.jsp";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		cursor = new Cursor();
		err = false;
		mensaje = null;
		
		estudianteDAO = new EstudianteDAO(cursor);
		reportesDAO = new ReporteLogrosDAO(cursor);
		
		HttpSession session = request.getSession();
		bitacoraCOM = new BitacoraCOM();
		String loginBitacora = (String)session.getAttribute("loginBitacora");
		
		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			return er;
		}
		try {
			request.getSession().removeAttribute("editar");
			// request.getSession().removeAttribute("filtro");

			String cmd2 = request.getParameter("cmd2");
			if (!("buscar").equals(cmd2)) {
				request.getSession().removeAttribute("listaGrupo");
				request.getSession().removeAttribute("listaGrado");
				request.getSession().removeAttribute("listaMetodo");
				request.getSession().removeAttribute("listaJornada");
				request.getSession().removeAttribute("listaSede");
				request.getSession().removeAttribute("filtroEst");

			}

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
			request.setAttribute("filtroTiposDoc", reportesDAO.getTiposDoc());
			Collection col = estudianteDAO.getFiltro(
					rb.getString("filtroSedeInstitucion"), list);
			request.getSession().setAttribute("filtroSedeF", col);
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
			Gson gson = new Gson();
			String jsonString = gson.toJson(list);
			try {

				Login usuVO = (Login) session.getAttribute("login");
				
				bitacoraCOM.insertarBitacora(Long.parseLong(usuVO.getInstId()), 
										Integer.parseInt(usuVO.getJornadaId()), 2, 
										usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
										30, 4, loginBitacora, jsonString);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error " + this + ":" + e.toString());
				Logger.print(login.getUsuarioId(), "Ingreso de HOJAS DE VIDA (Bitacora), módulo de Adicionar Estudiante. ",
						7, 1, this.toString());
			}
			
		} catch (Exception e) {
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (estudianteDAO != null)
				estudianteDAO.cerrar();
			if (reportesDAO != null)
				reportesDAO.cerrar();
			Logger.print(login.getUsuarioId(), "Ingreso de HOJAS DE VIDA, módulo de Adicionar Estudiante. ",
					7, 1, this.toString());
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