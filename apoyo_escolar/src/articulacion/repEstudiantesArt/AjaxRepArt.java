package articulacion.repEstudiantesArt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.repEstudiantesArt.vo.ParamsVO;
import articulacion.repEstudiantesArt.dao.repEstArtDAO;

public class AjaxRepArt extends Service {
	private String FICHA;
	private String FICHA_PARAMS;
	private String FICHA_EST_TUTOR;
	private Cursor cursor;
	private repEstArtDAO repEstArt;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			repEstArt = new repEstArtDAO(cursor);
			FICHA_PARAMS = config.getInitParameter("FICHA_PARAMS");
			FICHA_EST_TUTOR = config.getInitParameter("FICHA_EST");
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			// System.out.println("los valores="+CMD+"//"+TIPO);
			String params[] = null;
			switch (TIPO) {
			case ParamsVO.FICHA_EST_X_TUTOR:
				FICHA = FICHA_EST_TUTOR;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_DOCENTE:
					// System.out.println("ENTRO AL AJAX TUTOR");
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 3
							|| params[0] == null || params[1] == null
							|| params[2] == null)
						break;
					request.setAttribute("ajaxDocente", repEstArt
							.getAjaxDocente(Long.parseLong(params[0]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_DOCENTE));
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
