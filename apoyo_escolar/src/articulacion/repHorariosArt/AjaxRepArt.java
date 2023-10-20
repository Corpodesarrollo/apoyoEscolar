package articulacion.repHorariosArt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.repHorariosArt.vo.ParamsVO;
import articulacion.repHorariosArt.dao.repHorariosArtDAO;

public class AjaxRepArt extends Service {
	private String FICHA;
	private String FICHA_PARAMS;
	private String FICHA_HORARIO;
	private Cursor cursor;
	private repHorariosArtDAO repHorariosArt;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			repHorariosArt = new repHorariosArtDAO(cursor);
			FICHA_PARAMS = config.getInitParameter("FICHA_PARAMS");
			FICHA_HORARIO = config.getInitParameter("FICHA_HORARIO");
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			// System.out.println("los valores="+CMD+"//"+TIPO);
			String params[] = null;
			switch (TIPO) {
			case ParamsVO.FICHA_HORARIO_ESTUDIANTE:
				FICHA = FICHA_HORARIO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_ESTUDIANTE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 4
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null)
						break;
					request.setAttribute(
							"ajaxEstudiante",
							repHorariosArt.getAjaxEstudiantes(
									Long.parseLong(params[0]),
									Integer.parseInt(params[2]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[3])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_ESTUDIANTE));
					break;
				}
				break;
			case ParamsVO.FICHA_HORARIO_GRUPO:
				// System.out.println("ENTRO FICHA GRUPO");
				FICHA = FICHA_HORARIO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_GRUPO:
					// System.out.println("ENTRO AJAX GRUPO");
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 7
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null
							|| params[4] == null || params[5] == null
							|| params[6] == null)
						break;
					// System.out.println("ENTRO AJAX GRUPO 2");
					request.setAttribute(
							"ajaxGrupo",
							repHorariosArt.getAjaxGrupo(
									Long.parseLong(params[0]),
									Long.parseLong(params[1]),
									Long.parseLong(params[2]),
									Long.parseLong(params[3]),
									Long.parseLong(params[4]),
									Long.parseLong(params[5]),
									Long.parseLong(params[6])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
				}
				break;
			case ParamsVO.FICHA_HORARIO_DOCENTE:
				FICHA = FICHA_HORARIO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_DOCENTE:
					// System.out.println("ENTRO AL AJAX DOCENTE");
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 3
							|| params[0] == null || params[1] == null
							|| params[2] == null)
						break;
					request.setAttribute(
							"ajaxDocente",
							repHorariosArt.getAjaxDocente(
									Long.parseLong(params[0]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_DOCENTE));
					break;
				}
				break;
			case ParamsVO.FICHA_HORARIO_ESPFISICO:
				FICHA = FICHA_HORARIO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_ESPFISICO:
					// System.out.println("ENTRO AL AJAX ESPACIO");
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 4
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null)
						break;
					request.setAttribute(
							"ajaxEspacio",
							repHorariosArt.getAjaxEspacio(
									Long.parseLong(params[0]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2]),
									Integer.parseInt(params[3])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_ESPFISICO));
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
