/**
 * 
 */
package articulacion.artRotacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artRotacion.dao.ArtRotacionDAO;
import articulacion.artRotacion.vo.ParamsVO;
import siges.common.service.Service;
import siges.dao.Cursor;

/**
 * 6/10/2007 
 * @author Latined
 * @version 1.2
 */
public class Ajax extends Service{
	private String FICHA;
	private String FICHA_ESTRUCTURA;
	private String FICHA_RECESO;
	private String FICHA_ESPACIO_ASIGNATURA;
	private String FICHA_ASIGNATURA_BLOQUE;
	private String FICHA_ESPACIO_DOCENTE;
	private String FICHA_INHABILITAR_ESPACIO;
	private String FICHA_INHABILITAR_DOCENTE;
	private String FICHA_INHABILITAR_HORA;
	private String FICHA_DOCENTE_ASIGNATURA_GRUPO;
	private String FICHA_HORARIO;
	private String FICHA_BORRAR_HORARIO;
	private Cursor cursor;
	private ArtRotacionDAO artRotacionDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		try{
		HttpSession session = request.getSession();
		cursor=new Cursor();
		artRotacionDAO=new ArtRotacionDAO(cursor);
		FICHA_ESTRUCTURA = config.getInitParameter("FICHA_ESTRUCTURA");
		FICHA_RECESO = config.getInitParameter("FICHA_RECESO");
		FICHA_HORARIO = config.getInitParameter("FICHA_HORARIO");
		FICHA_BORRAR_HORARIO = config.getInitParameter("FICHA_BORRAR_HORARIO");
		FICHA_ESPACIO_ASIGNATURA=config.getInitParameter("FICHA_ESPACIO_ASIGNATURA");
		FICHA_ASIGNATURA_BLOQUE=config.getInitParameter("FICHA_ASIGNATURA_BLOQUE");
		FICHA_ESPACIO_DOCENTE=config.getInitParameter("FICHA_ESPACIO_DOCENTE");
		FICHA_INHABILITAR_ESPACIO=config.getInitParameter("FICHA_INHABILITAR_ESPACIO");
		FICHA_INHABILITAR_DOCENTE=config.getInitParameter("FICHA_INHABILITAR_DOCENTE");
		FICHA_INHABILITAR_HORA=config.getInitParameter("FICHA_INHABILITAR_HORA");
		FICHA_DOCENTE_ASIGNATURA_GRUPO=config.getInitParameter("FICHA_DOCENTE_ASIGNATURA_GRUPO");
		int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
//		System.out.println("RotacionArticulacionAjax="+CMD+"//"+TIPO);
		String params[]=null;
		switch(TIPO){
			case ParamsVO.FICHA_HORARIO:
				FICHA=FICHA_HORARIO;
				switch(CMD){
					case ParamsVO.CMD_AJAX_DOCENTE:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<5 || params[0]==null || params[1]==null || params[2]==null|| params[3]==null|| params[4]==null) break;
						request.setAttribute("ajaxDocente",artRotacionDAO.getAjaxDocente(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Long.parseLong(params[3]),Long.parseLong(params[4])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_DOCENTE));
					break;
					case ParamsVO.CMD_AJAX_ESPECIALIDAD:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<1 || params[0]==null) break;
						request.setAttribute("ajaxEspecialidad",artRotacionDAO.getAjaxEspecialidad(Long.parseLong(params[0])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ESPECIALIDAD));
					break;
					case ParamsVO.CMD_AJAX_GRUPO:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<8 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null || params[6]==null || params[7]==null){ 
//							System.out.println("se rompio"+CMD+"//"+TIPO);
							break;
						}
						request.setAttribute("ajaxGrupo",artRotacionDAO.getAjaxGrupo(Long.parseLong(params[0]),Integer.parseInt(params[6]),Integer.parseInt(params[7]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Long.parseLong(params[4]),Integer.parseInt(params[5])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
					case ParamsVO.CMD_AJAX_ASIGNATURA:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<9 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null || params[6]==null || params[7]==null|| params[8]==null){ 
//							System.out.println("se rompio"+CMD+"//"+TIPO);
							break;
						}
						request.setAttribute("ajaxAsignatura",artRotacionDAO.getAjaxAsignatura(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Long.parseLong(params[4]),Integer.parseInt(params[5]),Long.parseLong(params[8])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIGNATURA));
					break;
				}
			break;	
			case ParamsVO.FICHA_BORRAR_HORARIO:
				FICHA=FICHA_HORARIO;
				switch(CMD){
					case ParamsVO.CMD_AJAX_ESPECIALIDAD:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<1 || params[0]==null) break;
						request.setAttribute("ajaxEspecialidad",artRotacionDAO.getAjaxEspecialidad(Long.parseLong(params[0])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ESPECIALIDAD));
					break;
					case ParamsVO.CMD_AJAX_GRUPO:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<8 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null || params[6]==null || params[7]==null){ 
//							System.out.println("se rompio"+CMD+"//"+TIPO);
							break;
						}
						request.setAttribute("ajaxGrupo",artRotacionDAO.getAjaxGrupo(Long.parseLong(params[0]),Integer.parseInt(params[6]),Integer.parseInt(params[7]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Long.parseLong(params[4]),Integer.parseInt(params[5])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					break;
					case ParamsVO.CMD_AJAX_ASIGNATURA:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<9 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null || params[6]==null || params[7]==null|| params[8]==null){ 
//							System.out.println("se rompio"+CMD+"//"+TIPO);
							break;
						}
						request.setAttribute("ajaxAsignatura",artRotacionDAO.getAjaxAsignatura(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Long.parseLong(params[4]),Integer.parseInt(params[5]),Long.parseLong(params[8])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIGNATURA));
					break;
				}
			break;
			case ParamsVO.FICHA_RECESO:
				FICHA=FICHA_RECESO;
				switch(CMD){
					case ParamsVO.CMD_AJAX_ESTRUCTURA:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<5 || params[0]==null|| params[1]==null|| params[2]==null|| params[3]==null|| params[4]==null) break;
						request.setAttribute("lEstructuraVO",artRotacionDAO.getAjaxEstructura(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ESTRUCTURA));
					break;
				}
			break;	
		}
		}catch(Exception e){e.printStackTrace();}	
		dispatcher[0]=String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
}
