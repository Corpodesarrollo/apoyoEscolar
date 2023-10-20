package articulacion.horarioArticulacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.horarioArticulacion.dao.HorarioArticulacionDAO;
import articulacion.horarioArticulacion.vo.ParamsVO;

public class Ajax extends Service {
	private String FICHA;
	private String FICHA_PARAMS;
	private String FICHA_HORARIO;
	private Cursor cursor;
	private HorarioArticulacionDAO horarioArticulacionDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			horarioArticulacionDAO = new HorarioArticulacionDAO(cursor);
			FICHA_PARAMS = config.getInitParameter("FICHA_PARAMS");
			FICHA_HORARIO = config.getInitParameter("FICHA_HORARIO");
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			// System.out.println("los valores="+CMD+"//"+TIPO);
			String params[] = null;
			switch (TIPO) {
			case ParamsVO.FICHA_HORARIO:
				FICHA = FICHA_HORARIO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_DOCENTE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 5
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null
							|| params[4] == null)
						break;
					request.setAttribute(
							"ajaxDocente",
							horarioArticulacionDAO.getAjaxDocente(
									Long.parseLong(params[0]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2]),
									Long.parseLong(params[3]),
									Long.parseLong(params[4])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_DOCENTE));
					break;
				case ParamsVO.CMD_AJAX_ESPECIALIDAD:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1
							|| params[0] == null)
						break;
					request.setAttribute("ajaxEspecialidad",
							horarioArticulacionDAO.getAjaxEspecialidad(Long
									.parseLong(params[0])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_ESPECIALIDAD));
					break;
				case ParamsVO.CMD_AJAX_GRUPO:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 8
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null
							|| params[4] == null || params[5] == null
							|| params[6] == null || params[7] == null) {
						// System.out.println("se rompio"+CMD+"//"+TIPO);
						break;
					}
					request.setAttribute(
							"ajaxGrupo",
							horarioArticulacionDAO.getAjaxGrupo(
									Long.parseLong(params[0]),
									Integer.parseInt(params[6]),
									Integer.parseInt(params[7]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2]),
									Integer.parseInt(params[3]),
									Long.parseLong(params[4]),
									Integer.parseInt(params[5])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
				case ParamsVO.CMD_AJAX_ASIGNATURA:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 9
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null
							|| params[4] == null || params[5] == null
							|| params[6] == null || params[7] == null
							|| params[8] == null) {
						// System.out.println("se rompio"+CMD+"//"+TIPO);
						break;
					}
					request.setAttribute(
							"ajaxAsignatura",
							horarioArticulacionDAO.getAjaxAsignatura(
									Long.parseLong(params[0]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2]),
									Integer.parseInt(params[3]),
									Long.parseLong(params[4]),
									Integer.parseInt(params[5]),
									Long.parseLong(params[8])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_ASIGNATURA));
					break;
				}
				break;
			case ParamsVO.FICHA_HORARIO_GRUPO:
				FICHA = FICHA_HORARIO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_ESPECIALIDAD:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1
							|| params[0] == null)
						break;
					request.setAttribute("ajaxEspecialidad",
							horarioArticulacionDAO.getAjaxEspecialidad(Long
									.parseLong(params[0])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_ESPECIALIDAD));
					break;
				case ParamsVO.CMD_AJAX_ASIGNATURA2:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 8
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null
							|| params[4] == null || params[5] == null
							|| params[6] == null || params[7] == null) {
						break;
					}
					request.setAttribute(
							"ajaxGrupo",
							horarioArticulacionDAO.getAjaxGrupo(
									Long.parseLong(params[0]),
									Integer.parseInt(params[6]),
									Integer.parseInt(params[7]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2]),
									Integer.parseInt(params[3]),
									Long.parseLong(params[4]),
									Integer.parseInt(params[5])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_ASIGNATURA2));
					break;
				}
				break;
			case ParamsVO.FICHA_HORARIO_DOCENTE:
				FICHA = FICHA_HORARIO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_DOCENTE2:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 3
							|| params[0] == null || params[1] == null
							|| params[2] == null)
						break;
					request.setAttribute(
							"ajaxDocente",
							horarioArticulacionDAO.getAjaxDocenteSede(
									Long.parseLong(params[0]),
									Integer.parseInt(params[1]),
									Integer.parseInt(params[2])));
					request.setAttribute("ajaxParam",
							String.valueOf(ParamsVO.CMD_AJAX_DOCENTE2));
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
