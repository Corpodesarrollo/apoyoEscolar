package articulacion.artAusencias.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.artAusencias.dao.AusenciasDAO;
import articulacion.artAusencias.vo.ParamsVO;

public class Ajax extends Service{
	private String FICHA;
	private String FICHA_EVA;
	private Cursor cursor;
	private AusenciasDAO ausenciasDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		try{
		HttpSession session = request.getSession();
		cursor=new Cursor();
		ausenciasDAO=new AusenciasDAO(cursor);
		FICHA_EVA=config.getInitParameter("FICHA_EVA");
		int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String params[]=null;
		FICHA=FICHA_EVA;
	
		switch(TIPO){
			case ParamsVO.FICHA_EVALUACION:
				switch(CMD){
					case ParamsVO.CMD_AJAX_GRUPO:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<8 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null || params[6]==null || params[7]==null) break;
						session.setAttribute("ajaxGrupo",ausenciasDAO.getAjaxGrupo(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6]),Long.parseLong(params[7])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
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
