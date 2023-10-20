/**
 * 
 */
package siges.gestionAcademica.planDeEstudios.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.dao.Cursor;
import siges.gestionAcademica.planDeEstudios.dao.PlanDeEstudiosDAO;
import siges.gestionAcademica.planDeEstudios.vo.ParamsVO;
import siges.login.beans.Login;

/**
 * 27/10/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service {
	public String FICHA_PLAN;
	public String FICHA_AREA;
	public String FICHA_ASIGNATURA;
	private PlanDeEstudiosDAO planDeEstudiosDAO = new PlanDeEstudiosDAO(
			new Cursor());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_PLAN = config.getInitParameter("FICHA_PLAN");
		FICHA_AREA = config.getInitParameter("FICHA_AREA");
		FICHA_ASIGNATURA = config.getInitParameter("FICHA_ASIGNATURA");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession(true);
		String dispatcher[] = new String[2];
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		System.out.println(this.toString() + ":" + TIPO + "//" + CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_PLAN:
			FICHA = FICHA_PLAN;
			break;
		case ParamsVO.FICHA_AREA:
			FICHA = FICHA_AREA;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_AREA_BASE:
				areaAreaBase(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_GRADO:
				areaGrado(request, session, usuVO, CMD);
				break;
			}
			break;
		case ParamsVO.FICHA_ASIGNATURA:
			FICHA = FICHA_ASIGNATURA;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_AREA0:
				asignaturaArea0(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_AREA:
				asignaturaArea(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_ASIGNATURA:
				asignaturaAsignatura(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_ASIGNATURA_BASE:
				asignaturaAsignaturaBase(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_GRADO:
				asignaturaGrado(request, session, usuVO, CMD);
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void asignaturaArea(HttpServletRequest request, HttpSession session,
			Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				return;
			request.setAttribute("listaAreaBase", planDeEstudiosDAO
					.getListaArea(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void asignaturaArea0(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				return;
			request.setAttribute("listaAreaBase0", planDeEstudiosDAO
					.getListaArea(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void asignaturaAsignatura(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			// ACA TOCA MIRAR COMO SE PONE EL EVENTO QEU CALCULA LOS GRUPOS
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaAsignaturaBase", planDeEstudiosDAO
					.getListaAsignaturaBase(Long.parseLong(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void areaAreaBase(HttpServletRequest request, HttpSession session,
			Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaAreaBase",
					planDeEstudiosDAO.getAreaBase(Long.parseLong(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void areaGrado(HttpServletRequest request, HttpSession session,
			Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 2 || params[0] == null
					|| params[1] == null)
				return;
			request.setAttribute(
					"listaGrado",
					planDeEstudiosDAO.getListaGradoBase(
							Long.parseLong(params[0]),
							Integer.parseInt(params[1])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void asignaturaAsignaturaBase(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaAsignaturaBase", planDeEstudiosDAO
					.getAsignaturaBase(Long.parseLong(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void asignaturaGrado(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 4 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null)
				return;
			request.setAttribute(
					"listaGrado",
					planDeEstudiosDAO.getListaGradoArea(
							Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Long.parseLong(params[3])));
			request.setAttribute("listaAsignaturaBase", planDeEstudiosDAO
					.getListaAsignaturaBase(Long.parseLong(params[3])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
			request.getSession().setAttribute(
					"listavalvalidaplan1",
					planDeEstudiosDAO.valorvalplans(String.valueOf(params[0]),
							"1", "5", String.valueOf(params[1]),
							String.valueOf(params[2]),
							String.valueOf(params[3])));
			request.getSession().setAttribute(
					"listavalvalidaplan2",
					planDeEstudiosDAO.valorvalplans(String.valueOf(params[0]),
							"6", "9", String.valueOf(params[1]),
							String.valueOf(params[2]),
							String.valueOf(params[3])));
			request.getSession().setAttribute(
					"listavalvalidaplan3",
					planDeEstudiosDAO.valorvalplans(String.valueOf(params[0]),
							"10", "12", String.valueOf(params[1]),
							String.valueOf(params[2]),
							String.valueOf(params[3])));
			request.getSession().setAttribute(
					"listavalvalidaplanciclo1",
					planDeEstudiosDAO.valorvalplans(String.valueOf(params[0]),
							"20", "21", String.valueOf(params[1]),
							String.valueOf(params[2]),
							String.valueOf(params[3])));
			request.getSession().setAttribute(
					"listavalvalidaplanciclo2",
					planDeEstudiosDAO.valorvalplans(String.valueOf(params[0]),
							"22", "23", String.valueOf(params[1]),
							String.valueOf(params[2]),
							String.valueOf(params[3])));
			request.getSession().setAttribute(
					"listavalvalidaplanciclo3",
					planDeEstudiosDAO.valorvalplans(String.valueOf(params[0]),
							"24", "25", String.valueOf(params[1]),
							String.valueOf(params[2]),
							String.valueOf(params[3])));
			System.out.println("valor var " + String.valueOf(params[0]) + " "
					+ String.valueOf(params[1]) + " "
					+ String.valueOf(params[2]) + " "
					+ String.valueOf(params[3]));
			request.getSession().setAttribute(
					"listavalvalidaplan1ins",
					planDeEstudiosDAO.valorvalplansins(
							String.valueOf(params[0]),
							String.valueOf(params[1]),
							String.valueOf(params[2]),
							String.valueOf(params[3])));
			System.out.println("session attr "
					+ request.getSession().getAttribute(
							"listavalvalidaplan1ins"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
