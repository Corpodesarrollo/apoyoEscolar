package siges.adminParamsInst.service;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Ajax extends Service {
	private String FICHA;
	private String LISTA_AJAX;
	private Cursor cursor;
	private AdminParametroInstDAO adminParametroInstDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");
		adminParametroInstDAO = new AdminParametroInstDAO(cursor);
		usuVO = (Login) request.getSession().getAttribute("login");

		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			String params[] = null;
			FICHA = LISTA_AJAX;
			switch (TIPO) {
			case ParamsVO.FICHA_INST_NIVEL_EVAL:
				switch (CMD) {
				case ParamsVO.CMD_AJAX_JORD:
					getJornada(request, usuVO, ParamsVO.FICHA_INST_NIVEL_EVAL);
					break;
				case ParamsVO.CMD_AJAX_METD:
					getMetodologia(request, usuVO,
							ParamsVO.FICHA_INST_NIVEL_EVAL);
					break;
				case ParamsVO.CMD_AJAX_NVL:
					getNivel(request, usuVO, ParamsVO.FICHA_INST_NIVEL_EVAL);
					break;
				case ParamsVO.CMD_AJAX_GRAD:
					getGrado(request, usuVO, ParamsVO.FICHA_INST_NIVEL_EVAL);
					break;

				}

				break;

			case ParamsVO.FICHA_ESCL_NUM:
				switch (CMD) {
				case ParamsVO.CMD_AJAX_JORD:
					getJornada(request, usuVO, ParamsVO.FICHA_ESCL_NUM);
					break;
				case ParamsVO.CMD_AJAX_METD:
					getMetodologia(request, usuVO, ParamsVO.FICHA_ESCL_NUM);
					break;
				case ParamsVO.CMD_AJAX_NVL:
					getNivel(request, usuVO, ParamsVO.FICHA_ESCL_NUM);
					break;
				case ParamsVO.CMD_AJAX_GRAD:
					getGrado(request, usuVO, ParamsVO.FICHA_ESCL_NUM);
					break;

				}

				break;

			case ParamsVO.FICHA_ESCL_CONCEPT:
				switch (CMD) {
				case ParamsVO.CMD_AJAX_JORD:
					getJornada(request, usuVO, ParamsVO.FICHA_ESCL_CONCEPT);
					break;
				case ParamsVO.CMD_AJAX_METD:
					getMetodologia(request, usuVO, ParamsVO.FICHA_ESCL_CONCEPT);
					break;
				case ParamsVO.CMD_AJAX_NVL:
					getNivel(request, usuVO, ParamsVO.FICHA_ESCL_CONCEPT);
					break;
				case ParamsVO.CMD_AJAX_GRAD:
					getGrado(request, usuVO, ParamsVO.FICHA_ESCL_CONCEPT);
					break;

				}

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		// //system.out.println("**********ficha="+FICHA);
		return dispatcher;
	}

	/**
	 * @function:
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getJornada(HttpServletRequest request, Login usuVO, int tipo)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// " Ajax getJornada");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(usuVO.getInstId());
				long codSede = Long.parseLong(params[0]);

				request.getSession().setAttribute("listaJornada",
						adminParametroInstDAO.getJornada(codInst, codSede));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(ParamsVO.CMD_AJAX_JORD));
				request.getSession().setAttribute("ajaxTipo",
						String.valueOf(tipo));
				request.getSession().setAttribute("formulario", formulario);

			}
			// else {
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
	private void getMetodologia(HttpServletRequest request, Login usuVO,
			int tipo) throws ServletException {
		// System.out.println(formaFecha.format(new Date())
		// + " Ajax getMetodologia");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(usuVO.getInstId());
				long codSede = Long.parseLong(params[0]);

				request.getSession().setAttribute("listaMetodo",
						adminParametroInstDAO.getMetodologia(codInst));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(ParamsVO.CMD_AJAX_METD));
				request.getSession().setAttribute("ajaxTipo",
						String.valueOf(tipo));
				request.getSession().removeAttribute("formulario");
				// System.out.println("formulario ** " + formulario);
				request.getSession().setAttribute("formulario", formulario);

			}
			// else {
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
	private void getNivel(HttpServletRequest request, Login usuVO, int tipo)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) + " Ajax getNivel");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			request.getSession().removeAttribute("listaNivel");
			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(usuVO.getInstId());
				long codMed = Long.parseLong(params[0]);

				// System.out.println("codMed " + codMed);
				if (codMed > -99) {
					request.getSession().setAttribute("listaNivel",
							adminParametroInstDAO.getNivel(codInst, codMed));
				} else {
					request.getSession().setAttribute("listaNivel",
							adminParametroInstDAO.getNivel(codInst));
				}
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(ParamsVO.CMD_AJAX_NVL));
				request.getSession().setAttribute("ajaxTipo",
						String.valueOf(tipo));
				request.getSession().setAttribute("formulario", formulario);

			}
			// else {
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
	private void getGrado(HttpServletRequest request, Login usuVO, int tipo)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// "  Ajax getGrado");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(usuVO.getInstId());
				long codMetodo = Long.parseLong(params[0]);
				long codNivel = Long.parseLong(params[1]);

				if (codMetodo > -99 && codNivel > -99) {
					request.getSession().setAttribute(
							"listaGrado",
							adminParametroInstDAO.getGrado(codInst, codMetodo,
									codNivel));
				} else if (codMetodo > -99) {
					request.getSession().setAttribute(
							"listaGrado",
							adminParametroInstDAO.getGradoMetodologia(codInst,
									codMetodo));
				} else if (codNivel > -99) {
					request.getSession().setAttribute("listaGrado",
							adminParametroInstDAO.getGrado(codInst, codNivel));
				} else {
					request.getSession().setAttribute("listaGrado",
							adminParametroInstDAO.getGrado(codInst));
				}

				request.getSession().setAttribute("ajaxParam",
						String.valueOf(ParamsVO.CMD_AJAX_GRAD));
				request.getSession().setAttribute("ajaxTipo",
						String.valueOf(tipo));
				request.getSession().removeAttribute("formulario");
				request.getSession().setAttribute("formulario", formulario);

			} else {
				// System.out.println("params es null o invalido en ajax");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
