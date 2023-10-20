/**
 * 
 */
package siges.indicadores.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.dao.Cursor;
import siges.indicadores.dao.IndicadoresDAO;
import siges.indicadores.vo.ParamsVO;
import siges.login.beans.Login;

/**
 * 22/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service {
	public String FICHA_LOGRO;
	public String FICHA_DESCRIPTOR;
	private IndicadoresDAO indicadoresDAO = new IndicadoresDAO(new Cursor());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_LOGRO = config.getInitParameter("FICHA_LOGRO");
		FICHA_DESCRIPTOR = config.getInitParameter("FICHA_DESCRIPTOR");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		// System.out.println("entra ajax: TIPO:"+TIPO+"//"+CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_LOGRO:
			FICHA = FICHA_LOGRO;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_GRADO:
			case ParamsVO.CMD_AJAX_GRADO2:
				logroGrado(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_ASIGNATURA:
			case ParamsVO.CMD_AJAX_ASIGNATURA2:
				logroAsignatura(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_DOCENTE:
			case ParamsVO.CMD_AJAX_DOCENTE2:
				logroDocente(request, session, usuVO, CMD);
				break;
			}
			break;
		case ParamsVO.FICHA_DESCRIPTOR:
			FICHA = FICHA_DESCRIPTOR;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_GRADO:
			case ParamsVO.CMD_AJAX_GRADO2:
				descriptorGrado(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_AREA:
			case ParamsVO.CMD_AJAX_AREA2:
				descriptorArea(request, session, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_DOCENTE:
			case ParamsVO.CMD_AJAX_DOCENTE2:
				descriptorDocente(request, session, usuVO, CMD);
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void logroAsignatura(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 4 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null)
				return;
			request.setAttribute("listaAsignatura", indicadoresDAO
					.getListaAsignatura(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void descriptorArea(HttpServletRequest request, HttpSession session,
			Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 4 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null)
				return;
			request.setAttribute("listaArea", indicadoresDAO.getListaArea(
					Long.parseLong(params[0]), Integer.parseInt(params[1]),
					Integer.parseInt(params[2]), Integer.parseInt(params[3])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void logroDocente(HttpServletRequest request, HttpSession session,
			Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 5 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null || params[4] == null)
				return;
			request.setAttribute(
					"listaDocente",
					indicadoresDAO.getListaDocenteAsignatura(
							Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3]),
							Long.parseLong(params[4])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void descriptorDocente(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 5 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null || params[4] == null)
				return;
			request.setAttribute(
					"listaDocente",
					indicadoresDAO.getListaDocenteArea(
							Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3]),
							Long.parseLong(params[4])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void logroGrado(HttpServletRequest request, HttpSession session,
			Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 2 || params[0] == null
					|| params[1] == null)
				return;
			request.setAttribute("listaGrado", indicadoresDAO.getListaGrado(
					Long.parseLong(params[0]), Integer.parseInt(params[1])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void descriptorGrado(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 2 || params[0] == null
					|| params[1] == null)
				return;
			request.setAttribute("listaGrado", indicadoresDAO.getListaGrado(
					Long.parseLong(params[0]), Integer.parseInt(params[1])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
