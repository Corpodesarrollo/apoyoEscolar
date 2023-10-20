package articulacion.artEncuesta.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artEncuesta.dao.EncuestaDAO;
import articulacion.artEncuesta.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;


public class Ajax extends Service {
	private String FICHA;

	private String FICHA_FILTRO;	

	private EncuestaDAO encuestaDAO=new EncuestaDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			Login usuVO = (Login) session.getAttribute("login");
			FICHA_FILTRO = config.getInitParameter("FICHA_FILTRO");			
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			String params[] = null;
			String metodologia=null;
			switch (TIPO) {
			case ParamsVO.FICHA_FILTRO:
				FICHA = FICHA_FILTRO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_COLEGIO:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;					
					request.setAttribute("ajaxColegio", encuestaDAO.getColegiosFiltro(Long.parseLong(params[0])));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_COLEGIO));					
				break;
				case ParamsVO.CMD_AJAX_SEDE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxSede", encuestaDAO.getSedes(params[0]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_SEDE));
				break;
				case ParamsVO.CMD_AJAX_JORNADA:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 2 || params[0] == null || params[1] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxJornada", encuestaDAO.getJornada(params[0],params[1]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_JORNADA));
				break;
					case ParamsVO.CMD_AJAX_GRUPO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null || params[2] == null || params[3] == null)
							break;
						metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrupo", encuestaDAO.getAjaxGrupo(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
					case ParamsVO.CMD_AJAX_GRADO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null  || params[2] == null)
							break;
						metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrado", encuestaDAO.getAjaxGrado(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRADO));
					break;
				}
				break;
				
			case ParamsVO.FICHA_FILTRO2:
				FICHA = FICHA_FILTRO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_COLEGIO:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;					
					request.setAttribute("ajaxColegio", encuestaDAO.getColegiosFiltro(Long.parseLong(params[0])));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_COLEGIO));					
				break;
				case ParamsVO.CMD_AJAX_SEDE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxSede", encuestaDAO.getSedes(params[0]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_SEDE));
				break;
				case ParamsVO.CMD_AJAX_JORNADA:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 2 || params[0] == null || params[1] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxJornada", encuestaDAO.getJornada(params[0],params[1]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_JORNADA));
				break;
					case ParamsVO.CMD_AJAX_GRUPO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null || params[2] == null || params[3] == null)
							break;
						metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrupo", encuestaDAO.getAjaxGrupo(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
					case ParamsVO.CMD_AJAX_GRADO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null  || params[2] == null)
							break;
						metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrado", encuestaDAO.getAjaxGrado(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRADO));
					break;
				}
				break;
				
			case ParamsVO.FICHA_FILTRO3:
				FICHA = FICHA_FILTRO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_COLEGIO:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;					
					request.setAttribute("ajaxColegio", encuestaDAO.getColegiosFiltro(Long.parseLong(params[0])));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_COLEGIO));					
				break;
				case ParamsVO.CMD_AJAX_SEDE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxSede", encuestaDAO.getSedes(params[0]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_SEDE));
				break;
				case ParamsVO.CMD_AJAX_JORNADA:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 2 || params[0] == null || params[1] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxJornada", encuestaDAO.getJornada(params[0],params[1]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_JORNADA));
				break;
					case ParamsVO.CMD_AJAX_GRUPO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null || params[2] == null || params[3] == null)
							break;
						metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrupo", encuestaDAO.getAjaxGrupo(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
					case ParamsVO.CMD_AJAX_GRADO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null  || params[2] == null)
							break;
						metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrado", encuestaDAO.getAjaxGrado(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRADO));
					break;
				}
				break;
				
			case ParamsVO.FICHA_FILTRO4:
				FICHA = FICHA_FILTRO;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_COLEGIO:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;					
					request.setAttribute("ajaxColegio", encuestaDAO.getColegiosFiltro(Long.parseLong(params[0])));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_COLEGIO));					
				break;
				case ParamsVO.CMD_AJAX_SEDE:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 1 || params[0] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxSede", encuestaDAO.getSedes(params[0]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_SEDE));
				break;
				case ParamsVO.CMD_AJAX_JORNADA:
					params = request.getParameterValues("ajax");
					if (params == null || params.length < 2 || params[0] == null || params[1] == null)
						break;
					metodologia = usuVO.getMetodologiaId();
					request.setAttribute("ajaxJornada", encuestaDAO.getJornada(params[0],params[1]));
					request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_JORNADA));
				break;				
					case ParamsVO.CMD_AJAX_GRADO:
						params = request.getParameterValues("ajax");
						if (params == null || params.length < 3 || params[0] == null || params[1] == null  || params[2] == null)
							break;
						metodologia = usuVO.getMetodologiaId();
						request.setAttribute("ajaxGrado", encuestaDAO.getAjaxGrado(Long.parseLong(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), metodologia));
						request.setAttribute("ajaxParam", String.valueOf(ParamsVO.CMD_AJAX_GRADO));
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
