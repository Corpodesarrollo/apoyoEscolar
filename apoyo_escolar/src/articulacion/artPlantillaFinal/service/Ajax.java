package articulacion.artPlantillaFinal.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artPlantillaFinal.dao.PlantillaFinalDAO;
import articulacion.artPlantillaFinal.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Ajax extends Service {
	private String FICHA;

	private String FICHA_FILTRO;

	private PlantillaFinalDAO plantillaDAO=new PlantillaFinalDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			Login usuVO = (Login) session.getAttribute("login");
			FICHA_FILTRO = config.getInitParameter("FICHA_FILTRO");
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			String params[] = null;
			switch (TIPO) {
			case ParamsVO.FICHA_FILTRO:
			case ParamsVO.FICHA_FILTRO_EVAL:
				FICHA = FICHA_FILTRO;
				switch (CMD) {
					case ParamsVO.CMD_AJAX_GRUPO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null || params[2] == null || params[3] == null)
							break;
						String metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrupo", plantillaDAO.getAjaxGrupo(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
					case ParamsVO.CMD_AJAX_ESPECIALIDAD:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 1 || params[0] == null)
							break;
						request.setAttribute("ajaxEspecialidad", plantillaDAO.getAjaxEspecialidad(Long.parseLong(params[0])));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_ESPECIALIDAD));
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

}
