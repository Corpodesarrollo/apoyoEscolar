package siges.gestionAcademica.inscripcion.service;

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
import siges.gestionAdministrativa.agenda.vo.ParamsVO;
import siges.gestionAdministrativa.boletin.dao.BoletinDAO;
import siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO;
import siges.login.beans.Login;

public class AjaxNuevo extends Service{
	
	private static final long serialVersionUID = 1L;
	private String FICHA;
	private String LISTA_AJAX;
	private Cursor cursor; 
	private BoletinDAO boletinDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("FICHA_TAREAS"); 
		boletinDAO= new BoletinDAO(cursor); 
		usuVO = (Login)request.getSession().getAttribute("login");
		
		try{
			HttpSession session = request.getSession();
			cursor=new Cursor();  
			int CMD=getCmd(request,session,ParamsVO.CMD_AJAX_INSTANCIA0);
			int TIPO=getTipo(request,session,ParamsVO.FICHA_TAREAS);
			
			System.out.println(" ajax CMD " + CMD +" TIPO " + TIPO);
			FICHA=LISTA_AJAX;
			switch(TIPO){
			case ParamsVO.FICHA_TAREAS:
				switch(CMD){
				case ParamsVO.CMD_AJAX_AREA:
					getAsignaturas(request,usuVO);
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
	private void getMetodologia(HttpServletRequest request, Login usuVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + " Ajax getMetodologia");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				
				request.getSession().setAttribute("listaMetodo1",boletinDAO.getMetodologia(codInst));
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
	 * @function:  Trae los grados  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getGrado(HttpServletRequest request, Login usuVO  )  throws ServletException{
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			if(params != null && GenericValidator.isLong(params[0]) ){
			    long codInst = Long.parseLong( usuVO.getInstId());
			    long codSede = Long.parseLong( usuVO.getSedeId());
			    long codJord = Long.parseLong( usuVO.getJornadaId() );
				long codMetodo = Long.parseLong(params[0]);
				
				PlantillaBoletionVO plantillaBoletionVO =  new PlantillaBoletionVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				 
				request.setAttribute("listaGrado1",boletinDAO.getGrado(plantillaBoletionVO));
				request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRAD));
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
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			 
			
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
			    long codSede = Long.parseLong( usuVO.getSedeId());
			    long codJord = Long.parseLong( usuVO.getJornadaId() );
				long codMetodo = Long.parseLong(params[0]);
				long codGrado = Long.parseLong(params[1]);
			   
				
				PlantillaBoletionVO plantillaBoletionVO =  new PlantillaBoletionVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				plantillaBoletionVO.setPlabolgrado(codGrado);
				 
				
				request.setAttribute("listaGrupo1", boletinDAO.getGrupo(plantillaBoletionVO) );
			 	request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUP));
			 	
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	} 
	
	/**
	 * LLena la lista con las Asignaturas del instituo
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getAsignaturas(HttpServletRequest request, Login usuVO  )  throws ServletException{
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			 
			
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				long codMetodo = Long.parseLong(params[0]);
			   
				
				PlantillaBoletionVO plantillaBoletionVO =  new PlantillaBoletionVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				
				request.setAttribute("listaAsignaturas1", boletinDAO.getAsignaturas(plantillaBoletionVO) );
			 	request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_AREA));
			 	
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	}

	
}
