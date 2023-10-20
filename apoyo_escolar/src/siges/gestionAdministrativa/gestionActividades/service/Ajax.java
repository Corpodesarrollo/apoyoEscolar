package siges.gestionAdministrativa.gestionActividades.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

 
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor; 
import siges.gestionAdministrativa.padreFlia.dao.PadreFliaDAO;
import siges.gestionAdministrativa.padreFlia.vo.FiltroBoletinVO;
import siges.gestionAdministrativa.padreFlia.vo.ParamsVO; 
import siges.login.beans.Login;

public class Ajax extends Service{
	private String FICHA;
	private String LISTA_AJAX;
	private Cursor cursor; 
	private PadreFliaDAO padreFliaDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");  
		padreFliaDAO= new PadreFliaDAO(cursor); 
		usuVO = (Login)request.getSession().getAttribute("login");
		
		try{
			HttpSession session = request.getSession();
			cursor=new Cursor();  
			int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
			int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
			
			System.out.println("padre flia ajax CMD " + CMD +" TIPO " + TIPO);
			String params[]=null;
			FICHA=LISTA_AJAX;
			switch(TIPO){
			case ParamsVO.FICHA_PADRE_FLIA:
				switch(CMD){
				case ParamsVO.CMD_AJAX_JORD:
					getJornada(request,usuVO);
					break;
				case ParamsVO.CMD_AJAX_METD:
					getMetodologia(request,usuVO);
					break;	
			 	case ParamsVO.CMD_AJAX_GRAD:
					getGrado(request,usuVO);
					break;		
				case ParamsVO.CMD_AJAX_GRUP:
					getGrupo(request,usuVO);
					break;		
				case ParamsVO.CMD_AJAX_EST:
					getEst(request,usuVO);
					break;	
				
				}
				
				break;	
				
		 
			}
		}catch(Exception e){e.printStackTrace();}	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		System.out.println("**********ficha="+FICHA);
		return dispatcher;
	}
	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getJornada(HttpServletRequest request, Login usuVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getJornada");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				long codSede = Long.parseLong(params[0]);
				
				request.getSession().setAttribute("listaJornada",padreFliaDAO.getJornada(codInst, codSede));
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_JORD));
			 	
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
	private void getMetodologia(HttpServletRequest request, Login usuVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + " Ajax getMetodologia");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				long codSede = Long.parseLong(params[0]);
				
				request.getSession().setAttribute("listaMetodo",padreFliaDAO.getMetodologia(codInst));
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_METD));
			 		
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
	private void getGrado(HttpServletRequest request, Login usuVO  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getGrado");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){
			    long codInst = Long.parseLong( usuVO.getInstId());
			    long codSede = Long.parseLong( params[0]);
			    long codJord = Long.parseLong( params[1]);
				long codMetodo = Long.parseLong(params[2]);
				
			   
				FiltroBoletinVO plantillaBoletionVO =  new FiltroBoletinVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				 
				
				
				
				request.getSession().setAttribute("listaGrado",padreFliaDAO.getGrado(plantillaBoletionVO));
			 	request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRAD));
			 	
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
	private void getGrupo(HttpServletRequest request, Login usuVO  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getGrupo");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			 
			
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				long codSede = Long.parseLong(params[0]);
				long codJord = Long.parseLong(params[1]);
				long codMetodo = Long.parseLong(params[2]);
				long codGrado = Long.parseLong(params[3]);
			   
				
				FiltroBoletinVO plantillaBoletionVO =  new FiltroBoletinVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				plantillaBoletionVO.setPlabolgrado(codGrado);
				 
				
				  request.getSession().setAttribute("listaGrupo", padreFliaDAO.getGrupo(plantillaBoletionVO) );
			 
			 	request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUP));
			 	
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
	private void getEst(HttpServletRequest request, Login usuVO  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getEst");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			 
			
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				String numDoc =  params[0];
				long  tipoDoc = Long.parseLong(params[1]);
			   
				request.getSession().removeAttribute("nomEst");
				request.getSession().setAttribute("nomEst",padreFliaDAO.getNombreEstudiante(codInst, tipoDoc, numDoc));
			 	request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_EST));
			 	
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	} 
	
}
