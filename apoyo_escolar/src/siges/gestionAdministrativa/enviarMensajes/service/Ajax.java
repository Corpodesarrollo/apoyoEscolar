package siges.gestionAdministrativa.enviarMensajes.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

 
import siges.common.service.AjaxCommon;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor; 
import siges.gestionAdministrativa.enviarMensajes.dao.EnviarMensajesDAO;
import siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO;
import siges.gestionAdministrativa.enviarMensajes.vo.ParamsVO;
import siges.gestionAdministrativa.enviarMensajes.vo.FiltroEnviarVO;
import siges.login.beans.Login;
import siges.login.dao.LoginDAO;

public class Ajax extends AjaxCommon{
	private String FICHA;
	private String LISTA_AJAX;
	private String LISTA_ASIG;
	private Cursor cursor; 
	private EnviarMensajesDAO enviarMensajesDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");   
		enviarMensajesDAO= new EnviarMensajesDAO(cursor); 
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
			case ParamsVO.FICHA_MSJ:
				switch(CMD){
				case ParamsVO.CMD_AJAX_INST:
					getInst(request,usuVO,CMD);
					break;
				case ParamsVO.CMD_AJAX_SED:
					getSede(request,usuVO,CMD);
					break;
				case ParamsVO.CMD_AJAX_JORD:
					getJornada(request,usuVO,CMD);
					break;
		   	    case ParamsVO.CMD_AJAX_GRAD:
					getGrado(request,usuVO,CMD);
					break;		
				case ParamsVO.CMD_AJAX_GRUP:
					getGrupo(request,usuVO,CMD);
					break;	
					
				case ParamsVO.ADD_PER: 
					addPerfil(request,usuVO);
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
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroVO
	 * @throws ServletException
	 */
	private void addPerfil(HttpServletRequest request,Login usuVO ) throws ServletException {
		
	 System.out.println("addPerfil");
	 FiltroEnviarVO filtroVO = (FiltroEnviarVO)request.getSession().getAttribute("filtroMensajeVO");
		try {
			
		    String addcodPerfil = request.getParameter("codPerfil");
		    String addNomPerfil = request.getParameter("nomPerfil");
		    
		    if (GenericValidator.isInt(addcodPerfil) && filtroVO != null) {
		    	filtroVO.getListaPerfiles().add(new ItemVO(Long.parseLong(addcodPerfil),addNomPerfil));
				
			}
		    System.out.println("filtroVO.getListaPerfiles() " + filtroVO.getListaPerfiles().size() );
		    request.getSession().setAttribute("filtroMensajeVO",filtroVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	
	protected void getJornada(HttpServletRequest request, Login usuVO, int cmd )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " d Ajax getJornada");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			request.getSession().removeAttribute("listaJornada");
			if(params != null && params[0] != null 
					          && params[1] != null  && params[1]. length() > 1){
				String codInst = params[0];
				String codSede = params[1];
			
				
				codInst = MensajesVO.formatoInst(codInst);
				codSede = MensajesVO.formatoSede(codSede);
				
				request.getSession().setAttribute("listaJornada",enviarMensajesDAO.getListaJornd(codInst, codSede));
			}else{
			  System.out.println("params es null o invalido en ajax");
			}
			request.getSession().setAttribute("ajaxParam",String.valueOf(cmd));
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	} 
}
