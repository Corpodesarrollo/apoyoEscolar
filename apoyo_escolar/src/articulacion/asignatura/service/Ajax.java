package articulacion.asignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.asignatura.dao.*;
import articulacion.asignatura.vo.ParamsVO;

public class Ajax extends Service{
	private String FICHA;
	private String FICHA_ASIG;
	private Cursor cursor;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		try{
		HttpSession session = request.getSession();
		cursor=new Cursor();
		PruebaDAO pruebaDAO=new PruebaDAO(cursor);
		AsignaturaDAO asignaturaDAO=new AsignaturaDAO(cursor);
		FICHA_ASIG=config.getInitParameter("FICHA_ASIG");
		int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		//System.out.println("los valores="+CMD+"//"+TIPO);
		String params[]=null;
		FICHA=FICHA_ASIG;
		switch(TIPO){
			case ParamsVO.FICHA_ASIGNATURA:
				switch(CMD){
					case ParamsVO.CMD_AJAX_AREA:
						//System.out.println("Entra a ajax area");
						params=request.getParameterValues("ajax");
						if(params==null || params.length<4 || params[0]==null || params[1]==null || params[2]==null || params[3]==null) break;
						request.setAttribute("ajaxArea",asignaturaDAO.getAjaxArea(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_AREA));
					break;
					case ParamsVO.CMD_AJAX_ASIGNATURA:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null) break;
						/*	System.out.println(params[0]);
						System.out.println(params[1]);
						System.out.println(params[2]);
						System.out.println(params[3]);
						System.out.println(params[4]);
						System.out.println(params[5]);
						System.out.println(params[6]);
						System.out.println(params[7]);*/
						request.setAttribute("listaAsignaturaPVO",pruebaDAO.ajaxAsignatura(Long.parseLong(params[0]),params[1],Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Long.parseLong(params[6]),Integer.parseInt(params[7])));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIGNATURA));
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
