package articulacion.repEstudiantesArt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import articulacion.repEstudiantesArt.dao.repEstArtDAO;
import siges.dao.*;
import siges.login.beans.Login;

/**
 * Nombre: ControllerBoletinFiltroEdit<BR>
 * Descripcinn: Controla la vista del formulario de filtro de Boletines <BR>
 * Funciones de la pngina: Poner los dartos del formulario y redirigir el
 * control a la vista <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 29/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerFiltroEdit extends HttpServlet {
	private boolean err;
	private Cursor cursor;
	private Util util;
	private repEstArtDAO repEstArt;

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
		HttpSession session = request.getSession();
		String sig;
		String ant;
		String er;
		String home;
		int tipo;
		sig = "/articulacion/repEstudiantesArt/FiltroEstXTutor.jsp";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		err = false;
		try {
			cursor = new Cursor();
			util = new Util(cursor);
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				session.removeAttribute("filtro");
				tipo = 1;
			} else
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			Login login = (Login) session.getAttribute("login");
			String sede = login.getSedeId();
			String jornada = login.getJornadaId();
			// System.out.println("**SEDE**"+sede);
			// System.out.println("**JORNADA**"+jornada);
			if (sede.equals("") || jornada.equals("")) {
				request.setAttribute("mensaje", "Sede o Jornada no estan");
				return home;
			}
			// System.out.println("TIPO:"+tipo);
			switch (tipo) {
			case 1:
				filtroEstXTutor(login, session, request);
				break;

			}

			if (err) {
				request.setAttribute("mensaje", "Error interno");
				return er;
			}
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
		return sig;
	}

	// FILTRO PLAN ESTUDIOS (por:Wilmar Gnmez - 15/04/2008)
	public void filtroEstXTutor(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {

		try {
			borrarBeansEstXTutor(session, request);
			// FiltroBeanReporteEstXTutor filtro = (FiltroBeanReporteEstXTutor)
			// request.getSession().getAttribute("filtro");
			repEstArt = new repEstArtDAO(cursor);
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(login.getInstId());
			// SEDE
			request.setAttribute("lSedeVO", repEstArt.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					repEstArt.getJornada(inst, sede, jor));

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
	public void borrarBeansEstXTutor(HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		session.removeAttribute("filtro");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansAsig(HttpSession session, HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroAsig");
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

}