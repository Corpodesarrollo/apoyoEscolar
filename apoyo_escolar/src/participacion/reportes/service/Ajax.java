/**
 * 
 */
package participacion.reportes.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import participacion.reportes.dao.ReportesDAO;
import participacion.reportes.vo.ParamsVO;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service {

	/**
	 * 
	 */
	public String FICHA_LIDERES;
	public String FICHA_ACTAS;

	/**
	 * 
	 */
	private ReportesDAO reportesDAO = new ReportesDAO(new Cursor());

	/*
	 * @function:
	 * 
	 * @param config
	 * 
	 * @throws ServletException (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
		FICHA_ACTAS = config.getInitParameter("FICHA_ACTAS");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
		FICHA_ACTAS = config.getInitParameter("FICHA_ACTAS");
		String FICHA = null;
		HttpSession session = request.getSession();
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		switch (TIPO) {
		case ParamsVO.FICHA_LIDERES:
			FICHA = FICHA_LIDERES;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_INSTANCIA:
			case ParamsVO.CMD_AJAX_INSTANCIA0:
				lideresInstancia(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_RANGO:
			case ParamsVO.CMD_AJAX_RANGO0:
				// System.out.println("***entro x case CMD_AJAX_RANGO***");
				lideresRango(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_COLEGIO:
			case ParamsVO.CMD_AJAX_COLEGIO0:
				lideresColegio(request, session, CMD);
				break;
			}
			break;

		case ParamsVO.FICHA_ACTAS:
			FICHA = FICHA_ACTAS;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_INSTANCIA:
			case ParamsVO.CMD_AJAX_INSTANCIA0:
				lideresInstancia(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_RANGO:
			case ParamsVO.CMD_AJAX_RANGO0:
				// System.out.println("***entro x case CMD_AJAX_RANGO***");
				lideresRango(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_COLEGIO:
			case ParamsVO.CMD_AJAX_COLEGIO0:
				lideresColegio(request, session, CMD);
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void lideresInstancia(HttpServletRequest request,
			HttpSession session, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			// System.out.println("ENTRO AJAX INSTANCIA " + params[0]);
			request.setAttribute("listaInstanciaVO",
					reportesDAO.getListaInstancia(Integer.parseInt(params[0])));
			request.setAttribute("listaRolVO",
					reportesDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresRango(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaRangoVO",
					reportesDAO.getListaRango(Integer.parseInt(params[0])));
			request.setAttribute("listaRolVO",
					reportesDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresColegio(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaColegioVO",
					reportesDAO.getListaColegio(Integer.parseInt(params[0])));
			request.setAttribute("listaRolVO",
					reportesDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
