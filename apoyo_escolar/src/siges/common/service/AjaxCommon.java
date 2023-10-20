package siges.common.service;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.FiltroCommonVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.login.beans.Login;

/**
 * Clase que implementa el servicio de ajax comun
 * 
 * @version 1, 06/08/2010
 * @author desarrollo
 */
public class AjaxCommon extends Service {
	protected Login usuVO = null;
	protected String FRM_AJAX = null;
	protected String FICHA = null;
	protected Dao dao = null;
	protected Cursor cursor;
	protected SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * @throws ServletException
	 *             , IOExceptio
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		FRM_AJAX = "/siges/common/Ajax.jsp";
		usuVO = (Login) request.getSession().getAttribute("login");

		try {
			HttpSession session = request.getSession();
			int CMD = getCmd(request, session, Params.CMD_AJAX);

			// System.out.println(this.getClass() + "  CMD " + CMD);
			FICHA = FRM_AJAX;
			switch (CMD) {
			case Params.CMD_AJAX_LOC:
				getLocal(request, usuVO, CMD);
				break;
			case Params.CMD_AJAX_INST:
				getInst(request, usuVO, CMD);
				break;
			case Params.CMD_AJAX_SED:
				getSede(request, usuVO, CMD);
				break;
			case Params.CMD_AJAX_JORD:
				getJornada(request, usuVO, CMD);
				break;
			case Params.CMD_AJAX_METD:
				getMetodologia(request, usuVO, CMD);
				break;
			case Params.CMD_AJAX_GRAD:
				getGrado(request, usuVO, CMD);
				break;
			case Params.CMD_AJAX_GRUP:
				getGrupo(request, usuVO, CMD);
				break;
			case Params.CMD_AJAX_AREA:
				getArea(request, usuVO, CMD);
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		// System.out.println("FICHA = "+FICHA);
		return dispatcher;
	}

	/**
	 * @param request
	 * @param usuVO
	 * @param cmd
	 * @throws ServletException
	 */
	protected void getLocal(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) + " Ajax getLocal");
		try {

			long codLoc = getCodigo(usuVO.getMunId());
			long codInst = getCodigo(usuVO.getInstId());
			long codJor = getCodigo(usuVO.getJornadaId());
			long codSed = getCodigo(usuVO.getSedeId());
			long codMetodo = getCodigo(usuVO.getMetodologiaId());
			long codGrad = getCodigo(usuVO.getGradoId());
			long codGru = getCodigo(usuVO.getGrupoId());
			this.cursor = new Cursor();

			newDAO();

			request.getSession().setAttribute("listaLocalidad",
					dao.getListaLocal());

			/*
			 * System.out.println("codLoc " + codLoc);
			 * System.out.println("codInst " + codInst);
			 * System.out.println("codJor " + codJor);
			 * System.out.println("codSed " + codSed);
			 * System.out.println("codMetodo " + codMetodo);
			 * System.out.println("codGrad "+ codGrad);
			 * System.out.println("codGru " + codGru);
			 */

			if (codLoc > -99) {
				request.getSession().setAttribute("listaColegio",
						dao.getListaInst(codLoc));
			} else {
				request.getSession().removeAttribute("listaColegio");
			}

			if (codInst > -99) {
				request.getSession().setAttribute("listaSede",
						dao.getSede(codInst));
			} else {
				request.getSession().removeAttribute("listaSede");
			}

			if (codInst > -99 && codSed > -99) {
				request.getSession().setAttribute("listaJornada",
						dao.getListaJornd(codInst, codSed));
			} else {
				request.getSession().removeAttribute("listaJornada");
			}

			if (codInst > -99) {
				request.getSession().setAttribute("listaMetodo",
						dao.getListaMetod(codInst));
			} else {
				request.getSession().removeAttribute("listaMetodo");
			}

			request.getSession().setAttribute("ajaxParam", String.valueOf(cmd));
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
	protected void getInst(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) + " Ajax getInst");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isLong(params[0])) {
				newDAO();
				long codLoc = Long.parseLong(params[0]);
				// carneDAO = new Util(cursor);
				request.getSession().setAttribute("listaColegio",
						dao.getListaInst(codLoc));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

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
	protected void getSede(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) + "  Ajax getSede");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(params[0]);

				newDAO();

				request.getSession().setAttribute("listaSede",
						dao.getSede(codInst));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

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
	protected void getJornada(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// " Ajax getJornada");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isLong(params[0])
					&& GenericValidator.isLong(params[1])) {
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);
				newDAO();
				request.getSession().setAttribute("listaJornada",
						dao.getListaJornd(codInst, codSede));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

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
	protected void getMetodologia(HttpServletRequest request, Login usuVO,
			int cmd) throws ServletException {
		// System.out.println(formaFecha.format(new Date())
		// + " Ajax getMetodologia");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isLong(params[0])) {
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);
				newDAO();
				request.getSession().setAttribute("listaMetodo",
						dao.getListaMetod(codInst));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

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
	protected void getGrado(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// "  Ajax getGrado");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");
			if (params != null && GenericValidator.isInt(params[0])
					&& GenericValidator.isInt(params[1])
					&& GenericValidator.isInt(params[2])
					&& GenericValidator.isInt(params[3])) {

				FiltroCommonVO filVO = new FiltroCommonVO();
				filVO.setFilinst(Integer.parseInt(params[0]));
				filVO.setFilsede(Integer.parseInt(params[1]));
				filVO.setFiljornd(Integer.parseInt(params[2]));
				filVO.setFilmetod(Integer.parseInt(params[3]));
				newDAO();
				request.getSession().setAttribute("listaGrado",
						dao.getListaGrado(filVO));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

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
	protected void getGrupo(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) +
		// "  Ajax getGrupo");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isInt(params[0])
					&& GenericValidator.isInt(params[1])
					&& GenericValidator.isInt(params[2])
					&& GenericValidator.isInt(params[3])
					&& GenericValidator.isInt(params[4])) {

				newDAO();
				FiltroCommonVO filVO = new FiltroCommonVO();
				filVO.setFilinst(Integer.parseInt(params[0]));
				filVO.setFilsede(Integer.parseInt(params[1]));
				filVO.setFiljornd(Integer.parseInt(params[2]));
				filVO.setFilmetod(Integer.parseInt(params[3]));
				filVO.setFilgrado(Integer.parseInt(params[4]));

				request.getSession().setAttribute("listaGrupo",
						dao.getListaGrupo(filVO));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

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
	protected void getArea(HttpServletRequest request, Login usuVO, int cmd)
			throws ServletException {
		// System.out.println(formaFecha.format(new Date()) + "  Ajax getArea");
		try {
			String params[] = null;
			params = request.getParameterValues("ajax");

			if (params != null && GenericValidator.isInt(params[0])
					&& GenericValidator.isInt(params[1])
					&& GenericValidator.isInt(params[2])
					&& GenericValidator.isInt(params[3])
					&& GenericValidator.isInt(params[4])) {

				newDAO();
				FiltroCommonVO filVO = new FiltroCommonVO();
				filVO.setFilinst(Integer.parseInt(params[0]));
				filVO.setFilsede(Integer.parseInt(params[1]));
				filVO.setFiljornd(Integer.parseInt(params[2]));
				filVO.setFilmetod(Integer.parseInt(params[3]));
				filVO.setFilgrado(Integer.parseInt(params[4]));

				request.getSession().setAttribute("listaArea",
						dao.getListaArea(filVO));
				request.getSession().setAttribute("ajaxParam",
						String.valueOf(cmd));

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
	 * @param usu
	 * @return
	 */
	protected long getCodigo(String cod) {
		if (GenericValidator.isLong(cod)) {
			return Long.parseLong(cod);
		}
		return -99;
	}

	private void newDAO() {
		if (this.cursor == null)
			this.cursor = new Cursor();
		if (dao == null) {
			dao = new Dao(this.cursor);
		}
	}
}
