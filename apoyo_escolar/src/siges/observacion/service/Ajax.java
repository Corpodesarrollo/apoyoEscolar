/**
 * 
 */
package siges.observacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.dao.Cursor;
import siges.observacion.dao.ObservacionDAO;
import siges.observacion.vo.ParamsVO;

/**
 * 25/11/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Ajax extends Service {

	private String FICHA;
	private String FICHA_OBSERVACION_PERIODO;
	private String FICHA_OBSERVACION_GRUPO;
	private String FICHA_OBSERVACION_ASIGNATURA;

	private ObservacionDAO observacionDAO = new ObservacionDAO(new Cursor());

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		FICHA_OBSERVACION_PERIODO = config
				.getInitParameter("FICHA_OBSERVACION_PERIODO");
		FICHA_OBSERVACION_GRUPO = config
				.getInitParameter("FICHA_OBSERVACION_GRUPO");
		FICHA_OBSERVACION_ASIGNATURA = config
				.getInitParameter("FICHA_OBSERVACION_ASIGNATURA");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		// System.out.println("VALORESajax: "+TIPO+"_"+CMD);
		FICHA = FICHA_OBSERVACION_PERIODO;
		switch (TIPO) {
		case ParamsVO.FICHA_AJAX:
			switch (CMD) {
			case ParamsVO.CMD_AJAX_GRADO:
				ajaxGrado(request, session);
				FICHA = FICHA_OBSERVACION_ASIGNATURA;
				break;
			case ParamsVO.CMD_AJAX_GRUPO:
				ajaxGrupo(request, session);
				FICHA = FICHA_OBSERVACION_ASIGNATURA;
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void ajaxGrado(HttpServletRequest request, HttpSession session) {
		try {
			String param[] = request.getParameterValues("param");
			if (param != null && param.length >= 4 && param[0] != null
					&& param[1] != null && param[2] != null && param[3] != null) {
				request.setAttribute("listaGrado", observacionDAO.getAllGrado(
						Long.parseLong(param[0]), Long.parseLong(param[1]),
						Long.parseLong(param[2]), Long.parseLong(param[3])));
				request.setAttribute("ajaxParam",
						String.valueOf(ParamsVO.CMD_AJAX_GRADO));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ajaxGrupo(HttpServletRequest request, HttpSession session) {
		try {
			String param[] = request.getParameterValues("param");
			if (param != null && param.length >= 5 && param[0] != null
					&& param[1] != null && param[2] != null && param[3] != null
					&& param[4] != null) {
				request.setAttribute("listaGrupo", observacionDAO.getAllGrupo(
						Long.parseLong(param[0]), Long.parseLong(param[1]),
						Long.parseLong(param[2]), Long.parseLong(param[3]),
						Long.parseLong(param[4])));
				request.setAttribute("ajaxParam",
						String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
