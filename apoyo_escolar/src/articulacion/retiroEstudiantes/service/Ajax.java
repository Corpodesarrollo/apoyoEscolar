package articulacion.retiroEstudiantes.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import articulacion.retiroEstudiantes.dao.EvaluacionDAO;
import articulacion.retiroEstudiantes.vo.ParamsVO;

public class Ajax extends Service{
	private String FICHA;
	private String FICHA_EVA;
	private Cursor cursor;
	private EvaluacionDAO evaluacionDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		try{
		HttpSession session = request.getSession();
		cursor=new Cursor();
		evaluacionDAO=new EvaluacionDAO(cursor);
		FICHA_EVA=config.getInitParameter("FICHA_EVA");
		Login usuVO=(Login)session.getAttribute("login");
		int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String params[]=null;
		FICHA=FICHA_EVA;
	//	System.out.println("Llego hasta el servicio que queria");
		switch(TIPO){
			case ParamsVO.FICHA_EVALUACION:
				switch(CMD){
				case ParamsVO.CMD_AJAX_GRUPO:
					params=request.getParameterValues("ajax");
			/*		System.out.println("llega "+params.length);
					System.out.println("llega p1 "+params[0]);
					System.out.println("llega p2"+params[1]);
					System.out.println("llega p3"+params[2]);
					System.out.println("llega p4"+params[3]);
					System.out.println("llega p5"+params[4]);*/
					if(params==null || params.length<5 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null) break;
					//System.out.print("pasa");
					session.setAttribute("ajaxGrupo",evaluacionDAO.getAjaxGrupo(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4])));
					request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
				break;
				case ParamsVO.CMD_AJAX_ESTUDIANTE:
					params=request.getParameterValues("ajax");
					/*System.out.println("llega "+params.length);
					System.out.println("llega p1 "+params[0]);
					System.out.println("llega p2 "+params[1]);
					System.out.println("llega p3 "+params[2]);
					System.out.println("llega p4 "+params[3]);
					System.out.println("llega p5 "+params[4]);*/
					if(params==null || params.length<5 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null) break;
					//System.out.print("pasa");
					session.setAttribute("ajaxEstudiante",evaluacionDAO.getAjaxEstudiante(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4])));
					request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ESTUDIANTE));
				break;
		}
		}
		/*switch(TIPO){
		case ParamsVO.FICHA_EVALUACION:
			switch(CMD){
				case ParamsVO.CMD_AJAX_GRUPO:
					params=request.getParameterValues("ajax");
					if(params==null || params.length<8 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null || params[6]==null || params[7]==null) break;
					session.setAttribute("ajaxGrupo",evaluacionDAO.getAjaxGrupo(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6]),Long.parseLong(params[7])));
					request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
				break;	
				case ParamsVO.CMD_AJAX_ASIGNATURA:
					params=request.getParameterValues("ajax");
					if(params==null || params.length<2 || params[0]==null) break;
					session.setAttribute("ajaxAsignatura",evaluacionDAO.getAjaxAsignatura(Long.parseLong(params[0])));
					request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIGNATURA));
				break;	
				case ParamsVO.CMD_AJAX_PRUEBA:
					params=request.getParameterValues("ajax");
				//	System.out.println("antes");
					if(params==null || params.length<5 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null ) break;
					session.setAttribute("ajaxPrueba",evaluacionDAO.getAjaxPrueba(Long.parseLong(params[0])));
					request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_PRUEBA));
				break;
			}
		break;	
	}*/
		}catch(Exception e){e.printStackTrace();}	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
}
