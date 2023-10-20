package siges.reporte;

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
import siges.dao.Util;
import siges.login.beans.Login;

/**
 * siges.reporte<br>
 * Funcinn: <br>
 */
public class ControllerCarneFiltroEdit extends HttpServlet {
	private Util util;

	private Cursor cursor;

	private ResourceBundle rb;

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		rb = ResourceBundle.getBundle("siges.reporte.bundle.carnes");
		String sig;
		String sig2;
		String ant;
		String ant2;
		String er;
		String home;
		int tipo;
		// sig="/reporte/GenerarCarne.jsp";
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		ant = getServletConfig().getInitParameter("ant");
		ant2 = getServletConfig().getInitParameter("ant2");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		Login login = null;
		try {
			cursor = new Cursor();
			util = new Util(cursor);
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals(""))
				tipo = 1;
			else {
				String valorCampo = (String) request.getParameter("tipo");
				try {
					tipo = Integer.parseInt((String) request
							.getParameter("tipo"));
				} catch (NumberFormatException e) {
					tipo = Integer.parseInt(valorCampo.substring(
							valorCampo.length() - 1, valorCampo.length()));
				}
			}
			login = (Login) session.getAttribute("login");

			// System.out.println("tipo+++++++++++++++++++++++++++++++++++++++++++++ "
			// + tipo);
			if (tipo == 2) {
				return sig2;
			}
			// carnes(request,login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sig;
	}

	public void carnes(HttpServletRequest request, Login login)
			throws ServletException, IOException {
		try {
			borrarBeansCarnes(request);
			String sede = login.getSedeId();
			String jornada = login.getJornadaId();
			Collection list;
			Object[] o;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			request.setAttribute("filtroSedeF",
					util.getFiltro(rb.getString("filtroSedeInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			// System.out.println("inst_" + login.getInstId());
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			// System.out.println("sede_" + sede);
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			// System.out.println("jorn_" + jornada);
			list.add(o);
			request.setAttribute("filtroJornadaF", util.getFiltro(
					rb.getString("filtroSedeJornadaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			request.setAttribute("filtroMetodologiaF", util.getFiltro(
					rb.getString("listaMetodologiaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			list.add(o);
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			request.setAttribute("filtroGrupoF", util.getFiltro(
					rb.getString("filtroSedeJornadaGradoGrupoIns"), list));
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansCarnes(HttpServletRequest request)
			throws ServletException, IOException {
		request.getSession().removeAttribute("filtroCarne");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * @param a
	 * @param s
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * @param s
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public String setMensaje(String s) {
		String mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		return mensaje += "  - " + s + "\n";
	}
}