package siges.personal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import siges.dao.Cursor;
import siges.dao.Util;
import siges.estudiante.beans.Convivencia;
import siges.estudiante.beans.Salud;
import siges.login.beans.Login;
import siges.personal.beans.ParamsVO;
import siges.personal.beans.Personal;
import siges.personal.dao.PersonalDAO;
import util.BitacoraCOM;

/**
 * Nombre: ControllerFiltroEdit Descripcion: Controla el formulario de nuevo
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
public class ControllerFiltroEdit extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Util util;//
	private Login login;//
	private ResourceBundle rb;
	PersonalDAO personalDAO;

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
		rb = ResourceBundle.getBundle("siges.personal.bundle.personal");
		String sig;
		String sig2;
		String ant;
		String er;
		String home;

		int tipo;
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		err = false;
		mensaje = null;
		util = new Util(cursor);

		login = (Login) request.getSession().getAttribute("login");

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			return er;
		}
		Collection list;
		try {
			request.getSession().removeAttribute("editar");
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			if (request.getSession().getAttribute("filtroSedeF") == null)
				request.getSession().setAttribute("filtroSedeF",util.getFiltro(rb.getString("filtroSedeInstitucion"),list));
			if (request.getSession().getAttribute("filtroJornadaF") == null)
				request.getSession().setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}

		if (login.getPerfil().trim().equals(ParamsVO.PERFIL_RECTOR)
				|| login.getPerfil().trim()
						.equals(ParamsVO.PERFIL_ADMIN_ACADEMICO)) {
			// Mostra el filtro
			// System.out.println("Entra filtro login.getPerfil()" +
			// login.getPerfil());
			return sig;
		}
		// Mostrar formulario
		// System.out.println("Entra formualrio login.getPerfil()" +
		// login.getPerfil());
		asignarPersona(request);
		BitacoraCOM.insertarBitacora(Long.parseLong(login.getInstId()), 
				Integer.parseInt(login.getJornadaId()), 2, 
				login.getPerfil(), Integer.parseInt(login.getSedeId()), 
				121, 4/*Consulta Generada*/, login.getUsuarioId(), new Gson().toJson(list));
		return sig2;
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

	private void asignarPersona(HttpServletRequest request)
			throws ServletException {
		Personal personal = null;
		Convivencia convivencia = null;
		personalDAO = new PersonalDAO(cursor);
		// System.out.println("login.getUsuarioId() " + login.getUsuarioId());
		// System.out.println("login.getUsuario() " + login.getUsuario());
		try {
			personal = personalDAO.asignarPersonal(login.getUsuarioId());
			if (personal != null) {
				// convivencia
				convivencia = personalDAO.asignarConvivencia(login
						.getUsuarioId());
				if (convivencia != null) {
					convivencia.setEstado("1");
					request.getSession().setAttribute("nuevoConvivencia",
							convivencia);
					request.getSession().setAttribute("nuevoConvivencia2",
							convivencia.clone());
				}
				// salud
				Salud salud = personalDAO.asignarSalud("2",
						login.getUsuarioId());
				if (salud == null) {
					salud = new Salud();
				}
				// sede y jornada de la persona buscada
				if (!personal.getPercodjerar().equals("")) {
					personal.setSedejornada(personalDAO.getSedeJornadaPerfil(
							Long.parseLong(login.getInstId()),
							Long.parseLong(personal.getPernumdocum())));
				}
				request.getSession().setAttribute("nuevoPersonal", personal);
				request.getSession().setAttribute("nuevoPersonal2",
						personal.clone());
				request.getSession().setAttribute("nuevoSalud", salud);
				request.getSession().setAttribute("nuevoSalud2", salud.clone());
				// sig=sig2+"?tipo=1";
				request.getSession().setAttribute("subframePer",
						"/personal/FiltroResultado.jsp");
			} else {
				setMensaje(personalDAO.getMensaje());
				request.setAttribute("mensaje", mensaje);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			request.setAttribute("mensaje", e.getMessage());
		}
	}
}