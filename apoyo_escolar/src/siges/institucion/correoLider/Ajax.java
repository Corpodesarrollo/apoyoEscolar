package siges.institucion.correoLider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.institucion.correoLider.beans.ParamsVO;
import siges.institucion.correoLider.dao.CorreoDAO;

/**
 * 24/08/2007 
 * @author Latined
 * @version 1.2
 */
public class Ajax extends Service{
	private String FICHA;
	private String FICHA_CORREO;
	private Cursor cursor;
	private CorreoDAO correoDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		correoDAO=new CorreoDAO(cursor);   
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		FICHA_CORREO=config.getInitParameter("FICHA_CORREO");
		System.out.println("LOS VALORES==="+CMD+"//"+TIPO);
		String params[]=null;
		try{
			switch(TIPO){
				case ParamsVO.FICHA_CORREO:
					FICHA=FICHA_CORREO;
					switch(CMD){
						case ParamsVO.CMD_AJAX_INSTITUCION:
							params=request.getParameterValues("ajax");
							if(params==null || params.length<1 || params[0]==null) break;
							request.setAttribute("lInstitucion",correoDAO.getAjaxInstitucion(Integer.parseInt(params[0])));
							request.setAttribute("ajaxParam",String.valueOf(CMD));
						break;
						case ParamsVO.CMD_AJAX_CARGO:
							params=request.getParameterValues("ajax");
							if(params==null || params.length<1 || params[0]==null) break;
							request.setAttribute("lCargo",correoDAO.getAjaxCargo(Integer.parseInt(params[0])));
							request.setAttribute("ajaxParam",String.valueOf(CMD));
						break;
					}
				break;
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
