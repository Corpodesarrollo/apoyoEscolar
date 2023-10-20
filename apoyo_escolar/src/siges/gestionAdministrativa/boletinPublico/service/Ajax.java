package siges.gestionAdministrativa.boletinPublico.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

 
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor; 
import siges.gestionAdministrativa.boletinPublico.dao.BoletinPublicoDAO;
import siges.gestionAdministrativa.boletinPublico.vo.EstudianteVO;
import siges.gestionAdministrativa.boletinPublico.vo.ParamsVO;
import siges.gestionAdministrativa.boletinPublico.vo.PlantillaBoletionPubVO;
import siges.login.beans.Login;

public class Ajax extends Service{
	private String FICHA;
	private String LISTA_AJAX;
	private Cursor cursor; 
	private BoletinPublicoDAO boletinPublicoDAO= null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");  
		boletinPublicoDAO= new BoletinPublicoDAO(cursor); 
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
			case ParamsVO.FICHA_PLANTILLA_BOLETIN:
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
		dispatcher[0]=String.valueOf(Params.FORWARD);
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
				
				request.getSession().setAttribute("listaJornada",boletinPublicoDAO.getJornada(codInst, codSede));
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_JORD));
			 	
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
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
				
				request.getSession().setAttribute("listaMetodo",boletinPublicoDAO.getMetodologia(codInst));
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_METD));
			 		
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
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
				
			   
				PlantillaBoletionPubVO plantillaBoletionVO =  new PlantillaBoletionPubVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				 
				
				
				
				//request.getSession().setAttribute("listaGrado",boletinPublicoDAO.getGrado(plantillaBoletionVO));
			 	request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRAD));
			 	
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
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
			   
				
				PlantillaBoletionPubVO plantillaBoletionVO =  new PlantillaBoletionPubVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolsede(codSede);
				plantillaBoletionVO.setPlaboljornd(codJord);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				plantillaBoletionVO.setPlabolgrado(codGrado);
				 
				
				//  request.getSession().setAttribute("listaGrupo", boletinPublicoDAO.getGrupo(plantillaBoletionVO) );
			 
			 	request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUP));
			 	
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
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
			 
			
			if(params != null && GenericValidator.isLong(params[1]) ){
				
				String numDoc =  params[0];
				long  tipoDoc = Long.parseLong(params[1]);
				long  codigoEst =  Long.parseLong(params[2]);
			   
				
				EstudianteVO estudianteVO  = boletinPublicoDAO.getNombreEstudiante(tipoDoc, numDoc, codigoEst);
				PlantillaBoletionPubVO plantillaBoletionPubVO =boletinPublicoDAO.getDatosJerarquia(estudianteVO);
				Login login = boletinPublicoDAO.login4Params(plantillaBoletionPubVO.getPlabolinst());
				
				login.setUsuario("666");
				login.setUsuarioId("666");
				request.getSession().setAttribute("login",usuVO);
				
				request.getSession().setAttribute("estudianteVO",estudianteVO);
				request.getSession().setAttribute("codigoInst", ""+plantillaBoletionPubVO.getPlabolinst() );
				request.getSession().setAttribute("plabolperidoNomb", ""+login.getLogNomPerDef() );
				request.getSession().setAttribute("plabolperidoNum", ""+login.getLogNumPer());
								
				request.getSession().setAttribute("nombreInst",boletinPublicoDAO.getnombreInst(plantillaBoletionPubVO.getPlabolinst() ));
				request.getSession().setAttribute("listaSedes",boletinPublicoDAO.getSede(plantillaBoletionPubVO.getPlabolinst() ));
			 	request.getSession().setAttribute("listaJornada",boletinPublicoDAO.getJornada(plantillaBoletionPubVO.getPlabolinst(), plantillaBoletionPubVO.getPlabolsede()));
			 	request.getSession().setAttribute("listaMetodo",boletinPublicoDAO.getMetodologia( plantillaBoletionPubVO.getPlabolinst() ));
				request.getSession().setAttribute("listaGrado",boletinPublicoDAO.getGrado(plantillaBoletionPubVO));
				request.getSession().setAttribute("listaGrupo", boletinPublicoDAO.getGrupo(plantillaBoletionPubVO) );
				request.getSession().setAttribute("plantillaBoletionPubVO",plantillaBoletionPubVO);
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_EST));
				request.getSession().setAttribute("filtroPeriodo", getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef()));
				
				
				request.getSession().removeAttribute("resultadoConsulta");
			}else{
				System.out.println("params es null o invalido en ajax");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
			e.printStackTrace();
		}
	} 
	
	/**
	 * @function:  Carga la lista de los periodos
	 * @param numPer
	 * @param nomPerDef
	 * @return
	 */
	
	
	public List getListaPeriodo(long numPer, String nomPerDef){
		List l=new ArrayList();
		ItemVO item=null;
		System.out.println("numPer " + numPer);
		for(int i=1;i<=numPer;i++){
			item=new ItemVO(i,""+i); l.add(item);
		}		
		if(nomPerDef != null && nomPerDef.length()>0){
		 item=new ItemVO(7,nomPerDef); l.add(item);
		}
		return l;
	}
	
}
