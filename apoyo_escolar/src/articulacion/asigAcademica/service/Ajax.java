package articulacion.asigAcademica.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asigAcademica.dao.AsignacionDAO;
import articulacion.asigAcademica.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;

public class Ajax extends Service {
	private String FICHA;

	private String FICHA_ASIGNACION;

	private Cursor cursor;

	private AsignacionDAO asignacionDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			asignacionDAO = new AsignacionDAO(cursor);
			FICHA_ASIGNACION = config.getInitParameter("FICHA_ASIGNACION");
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			// System.out.println("los valores="+CMD+"//"+TIPO);
			String params[] = null;
			switch (TIPO) {
			case ParamsVO.FICHA_ASIGNACION:
				FICHA = FICHA_ASIGNACION;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_DOCENTE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 3 || params[0] == null || params[1] == null || params[2] == null)
						break;
					request.setAttribute("lDocenteVO", asignacionDAO.getAjaxDocente(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2])));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_DOCENTE));
					break;
				case ParamsVO.CMD_AJAX_ESPECIALIDAD:
					params=request.getParameterValues("ajax");
					if(params==null || params.length<1 || params[0]==null) break;
					request.setAttribute("lEspecialidadVO",asignacionDAO.getAjaxEspecialidad(Long.parseLong(params[0])));
					request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ESPECIALIDAD));
				break;
				case ParamsVO.CMD_AJAX_ASIGNATURA:
					params=request.getParameterValues("ajax");
					if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null){ 
//						System.out.println("se rompio"+CMD+"//"+TIPO);
						break;
					}
					int anho=Integer.parseInt(params[4]);
					int per=Integer.parseInt(params[5]);
					request.setAttribute("lAsignaturaVO",asignacionDAO.getAjaxAsignatura(Long.parseLong(params[0]),anho,per,Integer.parseInt(params[1]),Long.parseLong(params[2]),Integer.parseInt(params[3])));
					request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIGNATURA));
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
