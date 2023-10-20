package articulacion.repPlanEstudiosArt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import articulacion.repPlanEstudiosArt.dao.repPlanArtDAO;
import articulacion.repPlanEstudiosArt.vo.*;
import siges.dao.*;
import siges.common.vo.ItemVO;
import siges.login.beans.Login;

;

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
	private repPlanArtDAO repPlanArt;

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
		String sig, sig2;
		String ant;
		String er;
		String home;
		int tipo;
		sig = "/articulacion/repPlanEstudiosArt/FiltroPlanEstudios.jsp";
		sig2 = "/articulacion/repPlanEstudiosArt/FiltroAsigAcademica.jsp";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		err = false;
		try {
			Login login = (Login) session.getAttribute("login");
			cursor = new Cursor();
			util = new Util(cursor);
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				session.removeAttribute("filtroPlan");
				tipo = 1;
			} else
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
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
				filtroPlan(login, session, request);
				break;
			case 2:
				filtroAsig(login, session, request);
				sig = sig2;
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
	public void filtroPlan(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {

		try {
			borrarBeansPlan(session, request);
			repPlanArt = new repPlanArtDAO(cursor);
			long inst = -99;
			inst = Long.parseLong(login.getInstId());
			request.setAttribute("lEspecialidadVO",
					repPlanArt.getAjaxEspecialidad(inst));
			// ano vigencia
			List l = new ArrayList();
			l.add(String.valueOf(repPlanArt.getVigenciaSis() - 1));
			l.add(String.valueOf(repPlanArt.getVigenciaSis()));
			l.add(String.valueOf(repPlanArt.getVigenciaSis() + 1));
			request.setAttribute("VigenciaArtPlan", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("PeriodoArtPlan", l);
			// componente
			l = new ArrayList();
			ItemVO itemCom = null;
			itemCom = new ItemVO();
			itemCom.setCodigo(1);
			itemCom.setNombre("Acadnmico");
			l.add(itemCom);
			ItemVO itemCom2 = null;
			itemCom2 = new ItemVO();
			itemCom2.setCodigo(2);
			itemCom2.setNombre("Tncnico");
			l.add(itemCom2);
			request.setAttribute("comArt", l);
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	// FILTRO ASIGNACION ACADEMICA (por:Wilmar Gnmez - 15/04/2008)
	public void filtroAsig(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		try {
			borrarBeansAsig(session, request);
			repPlanArt = new repPlanArtDAO(cursor);
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(login.getInstId());
			// SEDE
			request.setAttribute("lSedeVO", repPlanArt.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					repPlanArt.getJornada(inst, sede, jor));

			// ano vigencia
			List l = new ArrayList();
			l.add(String.valueOf(repPlanArt.getVigenciaSis() - 1));
			l.add(String.valueOf(repPlanArt.getVigenciaSis()));
			l.add(String.valueOf(repPlanArt.getVigenciaSis() + 1));
			request.setAttribute("VigenciaArtPlan", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("PeriodoArtPlan", l);
			// componente
			l = new ArrayList();
			ItemVO itemCom = null;
			itemCom = new ItemVO();
			itemCom.setCodigo(1);
			itemCom.setNombre("Acadnmico");
			l.add(itemCom);
			ItemVO itemCom2 = null;
			itemCom2 = new ItemVO();
			itemCom2.setCodigo(2);
			itemCom2.setNombre("Tncnico");
			l.add(itemCom2);
			request.setAttribute("comArt", l);
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
	public void borrarBeansPlan(HttpSession session, HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroPlan");
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
		// System.out.println("VAR S: "+s);
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