package siges.gestionAdministrativa.repCarnes.service;

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
import siges.gestionAdministrativa.repCarnes.dao.RepCarnesDAO;
import siges.gestionAdministrativa.repCarnes.vo.ParamsVO;
import siges.gestionAdministrativa.repCarnes.vo.FiltroRepCarnesVO;
import siges.login.beans.Login;

public class Ajax extends Service{
	private String FICHA;
	private String LISTA_AJAX;
	private String LISTA_ASIG;
	private Cursor cursor; 
	private RepCarnesDAO comDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");  
		LISTA_ASIG = config.getInitParameter("LISTA_ASIG");
		comDAO= new RepCarnesDAO(cursor); 
		usuVO = (Login)request.getSession().getAttribute("login");
		
		try{
			HttpSession session = request.getSession();
			cursor=new Cursor();  
			int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
			int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
			
			System.out.println(" ajax CMD " + CMD +" TIPO " + TIPO);
			String params[]=null;
			FICHA=LISTA_AJAX;
			switch(TIPO){
			case ParamsVO.FICHA_REPORTES:
				switch(CMD){
				case ParamsVO.CMD_AJAX_INST:
					getColegios(request,usuVO);
					break;
				case ParamsVO.CMD_AJAX_SEDE:
					getSedes(request,usuVO);
					break;
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
					
				case ParamsVO.CMD_AJAX_ASIG:
					getAsignaturas(request,usuVO);
					FICHA=LISTA_ASIG;
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
	private void getColegios(HttpServletRequest request, Login usuVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getCOLEGIOS");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) && GenericValidator.isLong(params[1]) ){				
				long codLoc= Long.parseLong(params[0]);
				long tipoCol= Long.parseLong(params[1]);				
				request.getSession().setAttribute("listaColegio",comDAO.getColegios(codLoc, tipoCol));
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_INST));
			 	
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
	private void getSedes(HttpServletRequest request, Login usuVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getSEDES");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong(params[0]);			
				request.getSession().setAttribute("listaSede",comDAO.getSede(codInst));
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_SEDE));
			 	
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
	private void getJornada(HttpServletRequest request, Login usuVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getJornada");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) && GenericValidator.isLong(params[1]) ){
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);				
				request.getSession().setAttribute("listaJornada",comDAO.getJornada(codInst, codSede));
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
				
				request.getSession().setAttribute("listaMetodo",comDAO.getMetodologia(codInst));
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
			if(params != null && GenericValidator.isLong(params[0])  ){
			    long codInst = Long.parseLong( params[0]);
			    long codSede = Long.parseLong( params[1]);
			    long codJord = Long.parseLong( params[2]);
				//long codMetodo = Long.parseLong(params[2]);
				
			   
				FiltroRepCarnesVO plantillaBoletionVO =  new FiltroRepCarnesVO();
				plantillaBoletionVO.setInst(codInst);
				plantillaBoletionVO.setSede(codSede);
				plantillaBoletionVO.setJornd(codJord);
				//plantillaBoletionVO.setMetodo(codMetodo);
				 		
	
				request.getSession().setAttribute("listaGrado",comDAO.getGrado(plantillaBoletionVO));
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
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);
				long codJord = Long.parseLong(params[2]);				
				long codGrado = Long.parseLong(params[3]);
			   
				
				FiltroRepCarnesVO plantillaBoletionVO =  new FiltroRepCarnesVO();
				plantillaBoletionVO.setInst(codInst);
				plantillaBoletionVO.setSede(codSede);
				plantillaBoletionVO.setJornd(codJord);
				
				plantillaBoletionVO.setGrado(codGrado);
				 
				
				  request.getSession().setAttribute("listaGrupo", comDAO.getGrupo(plantillaBoletionVO) );
			 
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
				long codMetodo = Long.parseLong(params[0]);
				long codNivel = Long.parseLong(params[1]);
			   
				
				//request.getSession().setAttribute("nomEst",comDAO.getGrado(codInst, codMetodo));
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
	private void getAsignaturas(HttpServletRequest request, Login usuVO  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getASIGNATURAS");
		try{
			
			String inst=request.getParameter("inst");
			String metod=request.getParameter("metod");
			String grado=request.getParameter("grado");
			String codsAsig=request.getParameter("codsAsig");
			String asig[]=null;
			long codInst =-99;			
			long codMetodo = -99;
			long codGrado = -99;
		   
			
			if(inst != null && GenericValidator.isLong(inst) ){
				codInst = Long.parseLong(inst);	
			}
			if(metod != null && GenericValidator.isLong(metod) ){
				codMetodo = Long.parseLong(metod);	
			}
			if(grado != null && GenericValidator.isLong(grado) ){
				codGrado = Long.parseLong(grado);	
			}	
			if(codsAsig != null && codsAsig.length()>0){
				asig=codsAsig.replace(',',':').split(":");
			}
				
			request.setAttribute("listaAsignaturas", comDAO.getAsignaturas(codInst, codMetodo, codGrado, asig));			 
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIG));
				
			
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			System.out.println("ERROR GET ASIGNATURAS");
			e.printStackTrace();
		}
	} 
	
}
