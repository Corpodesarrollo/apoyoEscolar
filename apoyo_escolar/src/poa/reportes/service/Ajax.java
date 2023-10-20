/**
 * 
 */
package poa.reportes.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.reportes.dao.ReportesDAO;
import poa.reportes.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * Procesa las peticiones que tienen que ver con el calculo de componentes de
 * formulario 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service {
	private static final long serialVersionUID = 6290013753285143307L;
	/**
	 * /** Ruta de la pagina de filtro de consulta de colegios
	 */
	public String FICHA_REPORTES_COLEGIO;
	/**
	 * Ruta de la pagina de filtro de consulta de SED
	 */
	public String FICHA_REPORTES_SED;

	/**
	 * Ruta de la pagina de filtro de consulta de Localidad
	 */
	public String FICHA_REPORTES_LOC;
	/**
	 * Objeto de acceso a datos
	 */
	private ReportesDAO consultaDAO = new ReportesDAO(new Cursor());

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de redireccinn
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		FICHA_REPORTES_LOC = config.getInitParameter("FICHA_REPORTES_LOC");
		FICHA_REPORTES_COLEGIO = config
				.getInitParameter("FICHA_REPORTES_COLEGIO");
		FICHA_REPORTES_SED = config.getInitParameter("FICHA_REPORTES_SED");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		int niv = Integer.parseInt(usuVO.getNivel());
		switch (TIPO) {
		case ParamsVO.FICHA_REPORTES:
			if (niv == 1)
				FICHA = FICHA_REPORTES_SED;
			else if (niv == 2)
				FICHA = FICHA_REPORTES_LOC;
			else
				FICHA = FICHA_REPORTES_COLEGIO;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_COLEGIO:
				consultaColegio(request, session, usuVO);
				break;
			case ParamsVO.CMD_AJAX_LINEAS:
				consultaLineas(request, session, usuVO);
				break;

			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * Calcula la lista de colegios de una localidad
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesion de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @throws ServletException
	 */
	public void consultaColegio(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			// System.out.println("hace ajax");
			request.setAttribute("listaColegio", consultaDAO.getListaColegio(
					Integer.parseInt(params[0]), Integer.parseInt(params[1])));
			request.setAttribute("ajaxParam",
					String.valueOf(ParamsVO.CMD_AJAX_COLEGIO));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Calcula la lista de lineas de una area de gestion
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesion de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @throws ServletException
	 */
	public void consultaLineas(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			// System.out.println("hace ajax lineas");
			request.setAttribute("listaLineas", consultaDAO
					.getListaLineasAccion(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",
					String.valueOf(ParamsVO.CMD_AJAX_LINEAS));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
