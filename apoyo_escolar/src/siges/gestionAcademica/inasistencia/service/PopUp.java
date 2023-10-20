package siges.gestionAcademica.inasistencia.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAcademica.inasistencia.dao.InasistenciaDAO;
import siges.gestionAcademica.inasistencia.vo.FiltroInasistencia;
import siges.gestionAcademica.inasistencia.vo.ParamVO;
import siges.login.beans.Login;
import siges.util.Recursos;

public class PopUp extends Service{

	private String FICHA;
	private String FICHA_INASISTENCIA;
	private String FICHA_INASISTENCIA_SIMPLE;
	private InasistenciaDAO inasistenciaDAO;
	private Cursor cursor;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		//try{
			cursor=new Cursor();
			inasistenciaDAO=new InasistenciaDAO(cursor);
			FICHA_INASISTENCIA=config.getInitParameter("FICHA_INASISTENCIA");
			FICHA_INASISTENCIA_SIMPLE=config.getInitParameter("FICHA_INASISTENCIA_SIMPLE");
			int TIPO=getTipo(request,session,ParamVO.FICHA_DEFAULT);
			System.out.println("Valores PopUp: "+TIPO);
			switch(TIPO){
				case ParamVO.FICHA_INASISTENCIA:
					popInasistencia(request);
					FICHA=FICHA_INASISTENCIA;
				break;
				case ParamVO.FICHA_INASISTENCIA_SIMPLE:
					popInasistenciaSimple(request);
					FICHA=FICHA_INASISTENCIA_SIMPLE;
				break;
			}
		//}catch(Exception e){e.printStackTrace();}
		dispatcher[0]=String.valueOf(Params.FORWARD);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	private void popInasistencia(HttpServletRequest request){
		String params[]=null;
		params=request.getParameterValues("param");
		System.out.println("DON-PopUp: "+params[0]+"//"+params[1]+"//"+params[2]+"//"+params[3]+"//"+params[4]+"//");
		if(params==null || params.length<7 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null|| params[5]==null|| params[6]==null) return;
		request.setAttribute("popAsignatura",inasistenciaDAO.getMaterias(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Long.parseLong(params[3]),Integer.parseInt(params[4]),Long.parseLong(params[6])));
		request.setAttribute("popMotivo",inasistenciaDAO.getListaMotivo());
		request.setAttribute("popIndice",params[5]);
	}

	private void popInasistenciaSimple(HttpServletRequest request){
		String params[]=null;
		params=request.getParameterValues("param");
		System.out.println("DON-PopUp: "+params[0]+"//"+params[1]+"//"+params[2]+"//"+params[3]+"//"+params[4]+"//");
		if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null|| params[5]==null) return;
		request.setAttribute("popMotivo",inasistenciaDAO.getListaMotivo());
		request.setAttribute("popIndice",params[5]);
	}
}


