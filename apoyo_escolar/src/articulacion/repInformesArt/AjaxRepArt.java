package articulacion.repInformesArt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.repInformesArt.vo.ParamsVO;
import articulacion.repInformesArt.dao.RepInfArtDAO;

public class AjaxRepArt extends Service {
	private String FICHA;
	private String FICHA_PARAMS;
	private String CONSTANCIAS;
	private Cursor cursor;
	private RepInfArtDAO repInfArt;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			repInfArt = new RepInfArtDAO(cursor);
			FICHA_PARAMS = config.getInitParameter("FICHA_PARAMS");
			CONSTANCIAS = config.getInitParameter("FICHA_CONS");
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			// System.out.println("los valores="+CMD+"//"+TIPO);
			String params[] = null;
			switch (TIPO) {
			case ParamsVO.FICHA_CONSTANCIA:
				FICHA = CONSTANCIAS;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_ESTUDIANTE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 4
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null)
						break;
					request.setAttribute(
							"ajaxEstudiante",
							repInfArt.getAjaxEstudiantes(
									Long.parseLong(params[0]),
									Integer.parseInt(params[2]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[3])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_ESTUDIANTE));
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
