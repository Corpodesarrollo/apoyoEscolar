package siges.gestionAdministrativa.agenda.service;

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
import siges.gestionAdministrativa.agenda.vo.ParamsVO;
import siges.gestionAdministrativa.boletin.dao.BoletinDAO;
import siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO;
import siges.login.beans.Login;

public class Ajax extends Service{
	
	private static final long serialVersionUID = 1L;
	private String FICHA;
	private String LISTA_AJAX;
	private String LISTA_TAREAS;
	private String FICHA_CIRCULARES;
	private Cursor cursor; 
	private BoletinDAO boletinDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");  
		LISTA_TAREAS = config.getInitParameter("FICHA_TAREAS");
		FICHA_CIRCULARES = config.getInitParameter("FICHA_CIRCULARES");
		boletinDAO= new BoletinDAO(cursor); 
		usuVO = (Login)request.getSession().getAttribute("login");
		
		try{
			HttpSession session = request.getSession();
			cursor=new Cursor();  
			int CMD=getCmd(request,session,ParamsVO.CMD_AJAX_INSTANCIA0);
			int TIPO=getTipo(request,session,ParamsVO.FICHA_TAREAS);
			
			switch(TIPO){
				case ParamsVO.FICHA_TAREAS:
					FICHA=LISTA_TAREAS;
					switch(CMD){
					case ParamsVO.CMD_AJAX_AREA:
						getAsignaturas(request,usuVO, "listaAsignaturas1");
						break;
					case ParamsVO.CMD_AJAX_METD:
						getMetodologia(request,usuVO, "listaMetodo1");
						break;	
				 	case ParamsVO.CMD_AJAX_GRAD:
						getGrado(request,usuVO, "listaGrado1");
						break;		
					case ParamsVO.CMD_AJAX_GRUP:
						getGrupo(request,usuVO, "listaGrupo1");
						break;		
					}
				break;	
				case ParamsVO.CMD_AJAX_INSTANCIA0:
					FICHA=LISTA_AJAX;
					switch(CMD){
					case ParamsVO.CMD_AJAX_AREA:
						getAsignaturas(request,usuVO, "listaAsignaturas");
						break;
					case ParamsVO.CMD_AJAX_METD:
						getMetodologia(request,usuVO, "listaMetodo");
						break;	
				 	case ParamsVO.CMD_AJAX_GRAD:
						getGrado(request,usuVO, "listaGrado");
						break;		
					case ParamsVO.CMD_AJAX_GRUP:
						getGrupo(request,usuVO, "listaGrupo");
						break;		
					}
				break;
				case ParamsVO.CMD_AJAX:
					FICHA=FICHA_CIRCULARES;
					switch(CMD){
					case ParamsVO.CMD_AJAX_AREA:
						getDatos(request,usuVO);
						break;	
					}
				break;
			}
		} catch(Exception e){
			e.printStackTrace();
		}	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		System.out.println("**********ficha="+FICHA);
		return dispatcher;
	}
	
	
	
	/**
	 * Trae la información de la lista desplegable segnn los criterios de circulares
	 * @param request
	 * @param usuVO2
	 */
	private void getDatos(HttpServletRequest request, Login usuVO2) {
		String params[]=null;
		params=request.getParameterValues("ajax");
		List list = new ArrayList();
		try {
			if(params[0].equals("1")){ // Colegio
				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(1);
				itemVO.setNombre(usuVO2.getInst());
				list.add(itemVO);
			} else if(params[0].equals("2")){ // Jornada
				list = boletinDAO.getJornada(Integer.parseInt(usuVO2.getInstId()), Integer.parseInt(usuVO2.getSedeId()));
			} else if(params[0].equals("3")){ // Sede
				list = boletinDAO.getSede(Integer.parseInt(usuVO2.getInstId()));
			} else if(params[0].equals("4")){ // Sede - Jornanda
				list = boletinDAO.getJornada(Integer.parseInt(usuVO2.getInstId()), Integer.parseInt(usuVO2.getSedeId()));
			} else if(params[0].equals("5")){ // Metodologia - Grado
				list = boletinDAO.getMetodologia(Integer.parseInt(usuVO2.getInstId()));
			} else if(params[0].equals("6")){ // Grupo
				list = boletinDAO.getGrupoInst(Long.parseLong(usuVO2.getInstId()), Long.parseLong(usuVO2.getSedeId()), Long.parseLong(usuVO2.getJornadaId()));
			}
			request.setAttribute("listaDatos", list);
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRAD));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getMetodologia(HttpServletRequest request, Login usuVO , String string)  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + " Ajax getMetodologia");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				
				request.getSession().setAttribute(string,boletinDAO.getMetodologia(codInst));
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
	private void getGrado(HttpServletRequest request, Login usuVO, String string  )  throws ServletException{
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
				 
				request.setAttribute(string,boletinDAO.getGrado(plantillaBoletionVO));
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
	private void getGrupo(HttpServletRequest request, Login usuVO  , String string)  throws ServletException{
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
				 
				
				request.setAttribute(string, boletinDAO.getGrupo(plantillaBoletionVO) );
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
	private void getAsignaturas(HttpServletRequest request, Login usuVO, String string)  throws ServletException{
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			 
			
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong( usuVO.getInstId());
				long codMetodo = Long.parseLong(params[0]);
			   
				
				PlantillaBoletionVO plantillaBoletionVO =  new PlantillaBoletionVO();
				plantillaBoletionVO.setPlabolinst(codInst);
				plantillaBoletionVO.setPlabolmetodo(codMetodo);
				
				request.setAttribute(string, boletinDAO.getAsignaturas(plantillaBoletionVO) );
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
