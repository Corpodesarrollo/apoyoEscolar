package articulacion.asigTutor.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.asigTutor.dao.AsigTutorDAO;
import articulacion.asigTutor.vo.ParamsVO;

public class Ajax extends Service{
	private String FICHA;
	private String FICHA_DOC;
	private Cursor cursor;
	private AsigTutorDAO asigTutorDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		try{
		HttpSession session = request.getSession();
		cursor=new Cursor();
		asigTutorDAO=new AsigTutorDAO(cursor);
		FICHA_DOC=config.getInitParameter("FICHA_DOC");
		int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String params[]=null;
		switch(TIPO){
			case ParamsVO.FICHA_ASIG_TUTOR:
				FICHA=FICHA_DOC;
				switch(CMD){
					case ParamsVO.CMD_AJAX_DOCENTE:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<3 || params[0]==null || params[1]==null || params[2]==null) break;
						session.setAttribute("ajaxDocente",asigTutorDAO.getAjaxDocente(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_DOCENTE));
					break;
				}
			break;	
		}
		}catch(Exception e){e.printStackTrace();}	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
}
