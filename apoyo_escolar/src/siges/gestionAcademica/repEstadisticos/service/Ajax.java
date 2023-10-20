package siges.gestionAcademica.repEstadisticos.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.service.AjaxCommon;
import siges.common.vo.FiltroCommonVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAcademica.repEstadisticos.dao.RepEstadisticosDAO;
import siges.gestionAcademica.repEstadisticos.vo.EstudianteVO;
import siges.gestionAcademica.repEstadisticos.vo.ParamsVO;
import siges.login.beans.Login;

public class Ajax extends AjaxCommon{
	private String FICHA;
	private String LISTA_AJAX; 
	private Cursor cursor; 
	private RepEstadisticosDAO  repEstadisticosDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");   
		repEstadisticosDAO= new RepEstadisticosDAO(cursor); 
		dao = repEstadisticosDAO;
		usuVO = (Login)request.getSession().getAttribute("login");
		
		try{
			HttpSession session = request.getSession();
			cursor=new Cursor();  
			int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
			int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
			
			System.out.println(this.getClass() +" CMD "+ CMD +" TIPO " + TIPO);
			String params[]=null;
			FICHA=LISTA_AJAX;
	 
				switch(CMD){
				case ParamsVO.CMD_AJAX_SED: 
					getSede(request,usuVO, CMD);
					break;
				case ParamsVO.CMD_AJAX_JORD: 
					getJornada(request,usuVO, CMD);
					break;
				case ParamsVO.CMD_AJAX_METD:
					getMetodologia(request,usuVO, CMD);
					break;	
				case ParamsVO.CMD_AJAX_GRAD:
					getGrado(request,usuVO, CMD);
					break;		
				case ParamsVO.CMD_AJAX_GRUP:
					getGrupo(request,usuVO, CMD);
					break;	
				case ParamsVO.CMD_AJAX_EST:
					getEstud(request,usuVO, CMD);
					break;	
				case ParamsVO.CMD_GRUPO_AREA:
					getGrupoArea(request,usuVO, CMD);
					
					break;	
				 	
			}
		}catch(Exception e){e.printStackTrace();}	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		System.out.println(this.getClass() + " ficha="+FICHA);
		return dispatcher;
	}
	
	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	protected void getEstud(HttpServletRequest request, Login usuVO, int cmd  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getEstud");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			
			
			if(params != null && GenericValidator.isInt(params[0])
					&& GenericValidator.isInt(params[1])
					&& GenericValidator.isInt(params[2])
					&& GenericValidator.isInt(params[3])
					&& GenericValidator.isInt(params[4])
					&& GenericValidator.isInt(params[5])){
				
				
				FiltroCommonVO filVO =  new FiltroCommonVO();
				filVO.setFilinst( Integer.parseInt( params[0]));
				filVO.setFilsede( Integer.parseInt( params[1]));
				filVO.setFiljornd(Integer.parseInt( params[2]));
				filVO.setFilmetod(Integer.parseInt( params[3]));
				filVO.setFilgrado(Integer.parseInt( params[4]));
				filVO.setFilgrupo(Integer.parseInt( params[5]));
				request.getSession().setAttribute("listaEstudiante",  repEstadisticosDAO.getListaEstudiante(filVO) );
				request.getSession().setAttribute("ajaxParam",String.valueOf(cmd));
				
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	} 
	

	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	protected void getGrupoArea(HttpServletRequest request, Login usuVO, int cmd  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getGrupo");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			
			
			if(params != null && GenericValidator.isInt(params[0])
					&& GenericValidator.isInt(params[1])
					&& GenericValidator.isInt(params[2])
					&& GenericValidator.isInt(params[3])
					&& GenericValidator.isInt(params[4])){
				
			 
				FiltroCommonVO filVO =  new FiltroCommonVO();
				filVO.setFilinst( Integer.parseInt( params[0]));
				filVO.setFilsede( Integer.parseInt( params[1]));
				filVO.setFiljornd(Integer.parseInt( params[2]));
				filVO.setFilmetod(Integer.parseInt( params[3]));
				filVO.setFilgrado(Integer.parseInt( params[4]));
				
				
				getGrupo(request,usuVO, ParamsVO.CMD_AJAX_GRUP);
				getArea(request,usuVO, ParamsVO.CMD_AJAX_AREA);
				request.getSession().setAttribute("ajaxParam",String.valueOf(cmd));
				
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	} 
	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	protected void getSede(HttpServletRequest request, Login usuVO, int cmd  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getSede");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			
			
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( params[0] );
				
				//newDAO();
				
				request.getSession().setAttribute("listaSede",dao.getSede(codInst));
				
				if(GenericValidator.isInt(usuVO.getSedeId())){
					request.getSession().setAttribute("listaJornada",dao.getListaJornd(codInst, Long.parseLong(usuVO.getSedeId())));
				}
				
				if(GenericValidator.isInt(usuVO.getInstId())){
					request.getSession().setAttribute("listaMetodo",dao.getListaMetod(Long.parseLong(usuVO.getInstId())));
				}
				
				request.getSession().setAttribute("ajaxParam",String.valueOf(cmd));
				
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	} 
	
}
