/**
 * 
 */
package siges.gestionAcademica.regNotasVigencia.service;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAcademica.regNotasVigencia.dao.RegNotasVigenciaDAO;
import siges.gestionAcademica.regNotasVigencia.vo.ParamsVO;
import siges.login.beans.Login;
import siges.reporte.beans.FiltroBoletinVO;

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
	public String FICHA_AJAX;
	Login usuVO = null;
	private Cursor cursor;
	private RegNotasVigenciaDAO regNotasVigenciaDAO;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * 
	 */

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
		cursor = new Cursor();
		regNotasVigenciaDAO = new RegNotasVigenciaDAO(cursor);
		FICHA_AJAX = config.getInitParameter("FICHA_AJAX");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		String FICHA = null;

		usuVO = (Login) request.getSession().getAttribute("login");
		HttpSession session = request.getSession();
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);

		switch (TIPO) {
		case ParamsVO.FICHA_FILTRO_REG_NOTAS:
			FICHA = FICHA_AJAX;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_SED:
				getSed(request, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_JORD:
				getJornada(request, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_METD:
				getMetodologia(request, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_GRAD:
				getGrado(request, usuVO, CMD);
				break;
			case ParamsVO.CMD_AJAX_GRUP:
				getGrupo(request, usuVO, CMD);
				break;

			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * @function:
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getJornada(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// " Ajax getJornada");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);

				request.getSession().setAttribute("listaJornada",
						regNotasVigenciaDAO.getJornada(codInst, codSede));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

			}
			// else{
			// System.out.println("params es null o invalido en ajax");
			// }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @function:
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getMetodologia(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// " Ajax getMetodologia");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);

				request.getSession().setAttribute("listaMetodo",
						regNotasVigenciaDAO.getMetodologia(codInst));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

			}
			// else{
			// System.out.println("params es null o invalido en ajax");
			// }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @function:
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getGrado(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// "  Ajax getGrado");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);
				long codJord = Long.parseLong(params[2]);
				long codMetodo = Long.parseLong(params[3]);

				FiltroBoletinVO plantillaBoletionVO = new FiltroBoletinVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);

				request.getSession().setAttribute("listaGrado",
						regNotasVigenciaDAO.getGrado(plantillaBoletionVO));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

			}
			// else{
			// System.out.println("params es null o invalido en ajax");
			// }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @function:
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getGrupo(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// "  Ajax getGrupo");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);
				long codJord = Long.parseLong(params[2]);
				long codMetodo = Long.parseLong(params[3]);
				long codGrado = Long.parseLong(params[4]);

				FiltroBoletinVO plantillaBoletionVO = new FiltroBoletinVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				plantillaBoletionVO.setPlabolgrado(codGrado);

				request.getSession().setAttribute("listaGrupo",
						regNotasVigenciaDAO.getGrupo(plantillaBoletionVO));

				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

			}
			// else{
			// System.out.println("params es null o invalido en ajax");
			// }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @function:
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getSed(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) + "  Ajax getSede");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(params[0]);

				request.getSession().setAttribute("listaSede",
						regNotasVigenciaDAO.getSede(codInst));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

			}
			// else{
			// System.out.println("params es null o invalido en ajax");
			// }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param usu
	 * @return
	 */
	private long getCodigo(String cod) {
		if (GenericValidator.isLong(cod)) {
			return Long.parseLong(cod);
		}
		return -99;
	}
}
