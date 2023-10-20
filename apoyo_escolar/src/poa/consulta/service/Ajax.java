/**
 * 
 */
package poa.consulta.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.consulta.dao.ConsultaDAO;
import poa.consulta.vo.ParamsVO;
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
	private static final long serialVersionUID = 6250013753285143307L;
	/**
	 * Ruta de la pagina de ajax para consulta de colegio
	 */
	public String FICHA_CONSULTA_COLEGIO;
	/**
	 * Ruta de la pagina de ajax para consulta de SED
	 */
	public String FICHA_CONSULTA_SED;

	/**
	 * Objeto de acceso a datos
	 */
	private ConsultaDAO consultaDAO = new ConsultaDAO(new Cursor());

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
		FICHA_CONSULTA_COLEGIO = config
				.getInitParameter("FICHA_CONSULTA_COLEGIO");
		FICHA_CONSULTA_SED = config.getInitParameter("FICHA_CONSULTA_SED");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		// System.out.println("Ajax"+TIPO+"//"+CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_CONSULTA:
			FICHA = FICHA_CONSULTA_SED;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_COLEGIO:
				// System.out.println("hace cmd");
				consultaColegio(request, session, usuVO);
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
			request.setAttribute("listaColegio",
					consultaDAO.getListaColegio(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",
					String.valueOf(ParamsVO.CMD_AJAX_COLEGIO));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
