/**
 * 
 */
package siges.rotacion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.rotacion.beans.ParamsVO;
import siges.rotacion.dao.RotacionDAO;

/**
 * 11/08/2007 
 * @author Latined
 * @version 1.2
 */
public class Ajax extends Service{
	public static final long serialVersionUID = 1;
	private String FICHA=null;
	private String FICHA_DOCENTE_GRUPO;
	private String FICHA_ESPACIO_GRADO;
	private Cursor cursor;
	private RotacionDAO rotacionDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		rotacionDAO=new RotacionDAO(cursor);
		//FiltroDocXGrupoVO filtro=(FiltroDocXGrupoVO)session.getAttribute("filtroDocXGrupoVO");
		FICHA_DOCENTE_GRUPO=config.getInitParameter("FICHA_DOCENTE_GRUPO");
		FICHA_ESPACIO_GRADO=config.getInitParameter("FICHA_ESPACIO_GRADO");
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,0);
		System.out.println("LOS VALORES==="+CMD+"//"+TIPO);
		String params[]=null;
		try{
			switch(TIPO){
			case ParamsVO.FICHA_DOCENTE_GRUPO_NUEVO:
				FICHA=FICHA_DOCENTE_GRUPO;
				switch(CMD){
					case ParamsVO.CMD_AJAX_DOCENTE_GRUPO_DOC0:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<3 || params[0]==null || params[1]==null || params[2]==null) break;
						request.setAttribute("ajaxDocente",rotacionDAO.getAjaxDocente(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_DOCENTE_GRUPO_DOC0));
					break;
					case ParamsVO.CMD_AJAX_DOCENTE_GRUPO_DOC:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<3 || params[0]==null || params[1]==null || params[2]==null) break;
						request.setAttribute("ajaxDocente",rotacionDAO.getAjaxDocente(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_DOCENTE_GRUPO_DOC));
					break;
					case ParamsVO.CMD_AJAX_DOCENTE_GRUPO_ASIG:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null|| params[3]==null|| params[4]==null || params[5]==null) break;
						request.setAttribute("ajaxAsignatura",rotacionDAO.getAjaxAsignatura(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Long.parseLong(params[4]),Long.parseLong(params[5])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_DOCENTE_GRUPO_ASIG));
					break;
					case ParamsVO.CMD_AJAX_DOCENTE_GRUPO_GRA:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null) break;
						request.setAttribute("ajaxGrado",rotacionDAO.getAjaxGrado(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Long.parseLong(params[4]),Long.parseLong(params[5])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_DOCENTE_GRUPO_GRA));
					break;
					case ParamsVO.CMD_AJAX_DOCENTE_GRUPO_GRU:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null|| params[6]==null) break;
						request.setAttribute("ajaxGrupo",rotacionDAO.getAjaxGrupo(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Long.parseLong(params[4]),Long.parseLong(params[5]),Long.parseLong(params[6])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_DOCENTE_GRUPO_GRU));
					break;
				}
				break;
				case ParamsVO.FICHA_ESPACIO_GRADO:
				FICHA=FICHA_ESPACIO_GRADO;
					switch(CMD){
						case ParamsVO.CMD_AJAX_ESPACIO_GRADO_ESP:
							params=request.getParameterValues("ajax");
							if(params==null || params.length<4 || params[0]==null || params[1]==null || params[2]==null || params[3]==null) break;
							request.setAttribute("ajaxEspacio",rotacionDAO.getAjaxEspacio(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])));
							request.setAttribute("ajaxGrado",rotacionDAO.getAjaxGrado2(Long.parseLong(params[0]),Integer.parseInt(params[3]),Integer.parseInt(params[1]),Integer.parseInt(params[2])));
							request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ESPACIO_GRADO_ESP));
						break;
					}
			}	
			dispatcher[0]=String.valueOf(Params.INCLUDE);
			dispatcher[1]=FICHA;
			return dispatcher;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}
}
