/**
 * 
 */
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
import siges.gestionAcademica.inasistencia.vo.ParamVO;
import siges.login.beans.Login;

/**
 * 11/10/2007 
 * @author Latined
 * @version 1.2
 */
public class Ajax  extends Service{
	private String FICHA;
	private String FICHA_INASISTENCIA;
	private String FICHA_RETARDO;
	private String FICHA_SALIDA_TARDE;
	private InasistenciaDAO inasistenciaDAO;
	private Cursor cursor;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		String params[]=null;
		//try{
			cursor=new Cursor();
			inasistenciaDAO=new InasistenciaDAO(cursor);
			FICHA_INASISTENCIA=config.getInitParameter("FICHA_INASISTENCIA");
			FICHA_RETARDO=config.getInitParameter("FICHA_RETARDO");
			FICHA_SALIDA_TARDE=config.getInitParameter("FICHA_SALIDA_TARDE");
			Login usuVO=(Login)session.getAttribute("login");
			//FiltroInasistencia filtroInasistencia=(FiltroInasistencia)session.getAttribute("filtroInasistencia");
			int CMD=getCmd(request,session,Params.CMD_NUEVO);
			int TIPO=getTipo(request,session,ParamVO.FICHA_DEFAULT);
			System.out.println("Valores inasistenciaAjax: "+TIPO+"//"+CMD);
			switch(TIPO){
				case ParamVO.FICHA_INASISTENCIA:
					FICHA=FICHA_INASISTENCIA;
					switch(CMD){
						case ParamVO.CMD_AJAX_ASIGNATURA:
							params=request.getParameterValues("ajax");
							if(params==null || params.length<3 || params[0]==null || params[1]==null || params[2]==null){
								System.out.println("Se sale de InasistenciaAjax: "+params);
								break;
							}
							System.out.println("Se sale de InasistenciaAjax: "+params[0]+"//"+params[1]+"//"+params[2]);
							request.setAttribute("lAsignatura",inasistenciaDAO.getAjaxAsignatura(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2])));
							request.setAttribute("ajaxParam",String.valueOf(ParamVO.CMD_AJAX_ASIGNATURA));
						break;
					}
				break;
			}
		//}catch(Exception e){e.printStackTrace();}
		dispatcher[0]=String.valueOf(Params.FORWARD);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
}
