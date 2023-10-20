package siges.gestionAdministrativa.RepPuestoEstudiante.service;

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
import siges.gestionAdministrativa.RepPuestoEstudiante.dao.ReporteDAO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.NivelEvalVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.ParamsVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.FiltroReportesVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.TipoEvalVO;
import siges.login.beans.Login;

public class Ajax extends Service{
	private String FICHA;
	private String LISTA_AJAX;
	private String LISTA_ASIG;
	private String LISTA_COL;
	private String LISTA_GRADOS;
	private String RANGO;
	private Cursor cursor; 
	private ReporteDAO comDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");  
		LISTA_ASIG = config.getInitParameter("LISTA_ASIG");
		LISTA_COL = config.getInitParameter("LISTA_COL");
		LISTA_GRADOS = config.getInitParameter("LISTA_GRADOS");
		RANGO = config.getInitParameter("RANGO");
		comDAO= new ReporteDAO(cursor); 
		usuVO = (Login)request.getSession().getAttribute("login");
		FiltroReportesVO filtroVO = (FiltroReportesVO) request.getSession().getAttribute("filtroReportesVO");
		
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
				case ParamsVO.CMD_AJAX_MUN:
					getMunicipios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_INST:
					getColegios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_SEDE:
					getSedes(request,usuVO,filtroVO);
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
					
				case ParamsVO.CMD_AJAX_RANGO:
					getRango(request,usuVO,filtroVO);
					FICHA=RANGO;
					break;	
					
				case ParamsVO.CMD_AJAX_COLS:
					getColegios(request,usuVO);
					FICHA=LISTA_COL;
					break;	
					
				case ParamsVO.CMD_AJAX_GRADOS:
					getGrados(request,usuVO);
					FICHA=LISTA_GRADOS;
					break;
				
				}
				
				break;	
				
			case ParamsVO.FICHA_REPORTES2:
				switch(CMD){
				case ParamsVO.CMD_AJAX_MUN:
					getMunicipios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_INST:
					getColegios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_SEDE:
					getSedes(request,usuVO,filtroVO);
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
					
				case ParamsVO.CMD_AJAX_RANGO:
					getRango(request,usuVO,filtroVO);
					FICHA=RANGO;
					break;	
				
				}
				
				break;	
			case ParamsVO.FICHA_REPORTES3:
				switch(CMD){
				case ParamsVO.CMD_AJAX_MUN:
					getMunicipios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_INST:
					getColegios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_SEDE:
					getSedes(request,usuVO,filtroVO);
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
					
				case ParamsVO.CMD_AJAX_RANGO:
					getRango(request,usuVO,filtroVO);
					FICHA=RANGO;
					break;	
				
				}
				
				break;	
				
			case ParamsVO.FICHA_REPORTES4:
				switch(CMD){
				case ParamsVO.CMD_AJAX_MUN:
					getMunicipios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_INST:
					getColegios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_SEDE:
					getSedes(request,usuVO,filtroVO);
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
					
				case ParamsVO.CMD_AJAX_RANGO:
					getRango(request,usuVO,filtroVO);
					FICHA=RANGO;
					break;	
				
				case ParamsVO.CMD_AJAX_COLS:
					getColegios(request,usuVO);
					FICHA=LISTA_COL;
					break;	
					
				case ParamsVO.CMD_AJAX_GRADOS:
					getGrados(request,usuVO);
					FICHA=LISTA_GRADOS;
					break;
				}
				
				break;	
		 
			case ParamsVO.FICHA_REPORTES5:
				switch(CMD){
				case ParamsVO.CMD_AJAX_MUN:
					getMunicipios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_INST:
					getColegios(request,usuVO,filtroVO);
					break;
				case ParamsVO.CMD_AJAX_SEDE:
					getSedes(request,usuVO,filtroVO);
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
					
				case ParamsVO.CMD_AJAX_RANGO:
					getRango(request,usuVO,filtroVO);
					FICHA=RANGO;
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
	private void getMunicipios(HttpServletRequest request, Login usuVO,FiltroReportesVO filtroVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getCOLEGIOS");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){				
				long codProv= Long.parseLong(params[0]);				
				request.getSession().setAttribute("listaLocalidad",comDAO.getLocalidades(codProv));
				request.setAttribute("listaMetodo", comDAO.getMetodologiaGlobal());
				request.setAttribute("listaJornada", comDAO.getJornadaGlobal());
				request.setAttribute("listaGrado", comDAO.getGradoGlobal());
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_MUN));
				
				if(filtroVO.getFechaProm()!=null && !filtroVO.getFechaProm().equals(""))
					filtroVO.setFechaPromValida(1);
				else
					filtroVO.setFechaPromValida(0);
				
				//VALIDACION PARA REPORTE 4 
				NivelEvalVO nivelVO=new NivelEvalVO();
				nivelVO.setCod_nivel_eval(1);
				TipoEvalVO tipoEval=null;
				
				tipoEval=new TipoEvalVO();
				tipoEval.setCod_tipo_eval(3);
				tipoEval.setEval_min(0);
				tipoEval.setEval_max(100);				
				tipoEval.setTipo_eval_prees(-9);
				tipoEval.setModo_eval(3);
				tipoEval.setEscala(comDAO.getEscalaMEN());
				filtroVO.setValorIni(tipoEval.getEval_min());
				filtroVO.setValorFin(tipoEval.getEval_max());
				filtroVO.setTipoEscala(tipoEval.getModo_eval());
				
				request.setAttribute("tipoEval", tipoEval);
				
				//
				request.getSession().setAttribute("filtroReportesVO",filtroVO);
				request.getSession().setAttribute("fechaProm",filtroVO.getFechaProm());
				request.getSession().setAttribute("fechaPromValida",""+filtroVO.getFechaPromValida());
			 	
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
	private void getColegios(HttpServletRequest request, Login usuVO,FiltroReportesVO filtroVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getCOLEGIOS");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){				
				long codLoc= Long.parseLong(params[0]);				
				request.getSession().setAttribute("listaColegio",comDAO.getColegios(codLoc));
				
				request.setAttribute("listaMetodo", comDAO.getMetodologiaGlobal());
				request.setAttribute("listaJornada", comDAO.getJornadaGlobal());
				request.setAttribute("listaGrado", comDAO.getGradoGlobal());
				
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_INST));
				
				if(filtroVO.getFechaProm()!=null && !filtroVO.getFechaProm().equals(""))
					filtroVO.setFechaPromValida(1);
				else
					filtroVO.setFechaPromValida(0);
				
				//VALIDACION PARA REPORTE 4 
				NivelEvalVO nivelVO=new NivelEvalVO();
				nivelVO.setCod_nivel_eval(1);
				TipoEvalVO tipoEval=null;
				
				tipoEval=new TipoEvalVO();
				tipoEval.setCod_tipo_eval(3);
				tipoEval.setEval_min(0);
				tipoEval.setEval_max(100);				
				tipoEval.setTipo_eval_prees(-9);
				tipoEval.setModo_eval(3);
				tipoEval.setEscala(comDAO.getEscalaMEN());
				filtroVO.setValorIni(tipoEval.getEval_min());
				filtroVO.setValorFin(tipoEval.getEval_max());
				filtroVO.setTipoEscala(tipoEval.getModo_eval());
				
				request.setAttribute("tipoEval", tipoEval);
				//
				
				request.getSession().setAttribute("filtroReportesVO",filtroVO);
				request.getSession().setAttribute("fechaProm",filtroVO.getFechaProm());
				request.getSession().setAttribute("fechaPromValida",""+filtroVO.getFechaPromValida());
			 	
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
	private void getSedes(HttpServletRequest request, Login usuVO,FiltroReportesVO filtroVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getSEDES ");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong(params[0]);
				long vig = Long.parseLong(params[1]);
				request.getSession().setAttribute("listaSede",comDAO.getSede(codInst));				
				request.setAttribute("listaMetodo", comDAO.getMetodologia(codInst));
				request.setAttribute("listaJornada", comDAO.getJornada(codInst,-99));
				System.out.println(formaFecha.format(new Date()) +  " Ajax getSEDES INST "+codInst);
				if(filtroVO==null)
					filtroVO =  new FiltroReportesVO();
				filtroVO.setInst(codInst);
				filtroVO.setVig(vig);
				NivelEvalVO nivelEval=comDAO.getNivelEval(codInst);
				filtroVO.setSede(-9);
				filtroVO.setJornd(-9);
				filtroVO.setMetodo(-9);
				filtroVO.setGrado(-9);
				
				request.setAttribute("listaGrado", comDAO.getGrado(filtroVO));
				
				request.setAttribute("listaPeriodo",getListaPeriodo(nivelEval.getNumPer(), nivelEval.getNomPerFinal()));
				
				request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_SEDE));
				
				
				request.setAttribute("listaAsignatura",comDAO.getAsignaturas(filtroVO.getInst(), filtroVO.getMetodo(), filtroVO.getGrado(), null, filtroVO.getVig()));
				//filtroVO.setFechaProm(comDAO.getFechaPromedios(ParamsVO.NIVEL_INST, codInst));
				/*System.out.println("AJAX INST FECHA PROM: "+filtroVO.getFechaProm());
				if(filtroVO.getFechaProm()!=null && !filtroVO.getFechaProm().equals(""))
					filtroVO.setFechaPromValida(1);
				else
					filtroVO.setFechaPromValida(0);
				*/
				//VALIDACION PARA REPORTE 4 
				NivelEvalVO nivelVO=new NivelEvalVO();
				nivelVO.setCod_nivel_eval(1);
				TipoEvalVO tipoEval=null;
				
				nivelVO=comDAO.getNivelEval(filtroVO.getInst());
				tipoEval=comDAO.getTipoEval(filtroVO);
				filtroVO.setNumPer(nivelVO.getNumPer());
				filtroVO.setNomPerFinal(nivelVO.getNomPerFinal());
				filtroVO.setTipoEscala((int)tipoEval.getCod_tipo_eval());
				filtroVO.setValorIni(tipoEval.getEval_min());
				filtroVO.setValorFin(tipoEval.getEval_max());
				
				if(tipoEval.getCod_tipo_eval()==2){
					tipoEval.setEscala(comDAO.getEscalaConceptual(filtroVO));
				}else if(tipoEval.getCod_tipo_eval()==3){
					tipoEval.setEscala(comDAO.getEscalaMEN());
				}
				request.setAttribute("tipoEval", tipoEval);
				
				//				
				request.getSession().setAttribute("filtroReportesVO",filtroVO);
				/*request.getSession().setAttribute("fechaProm",filtroVO.getFechaProm());
				request.getSession().setAttribute("fechaPromValida",""+filtroVO.getFechaPromValida());*/
			 	
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
	private void getJornada(HttpServletRequest request, Login usuVO )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getJornada");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax");
			String formulario = request.getParameter("formulario");
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);
				
				request.getSession().setAttribute("listaJornada",comDAO.getJornada(codInst, codSede));
				
				
				
				FiltroReportesVO filtroVO =  new FiltroReportesVO();
				filtroVO.setInst(codInst);
				filtroVO.setSede(codSede);
				filtroVO.setJornd(-99);
				filtroVO.setMetodo(-9);
				
				request.setAttribute("listaGrado", comDAO.getGrado(filtroVO));
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
			if(params != null && GenericValidator.isLong(params[0]) && GenericValidator.isLong(params[1]) && GenericValidator.isLong(params[2]) ){
				long codInst = Long.parseLong(params[0]);
				long codSede = Long.parseLong(params[1]);
				long codJorn = Long.parseLong(params[2]);
				long vig = Long.parseLong(params[3]);


				request.getSession().setAttribute("listaMetodo",comDAO.getMetodologia(codInst));				
				
				FiltroReportesVO filtroVO =  new FiltroReportesVO();
				filtroVO.setVig(vig);
				filtroVO.setInst(codInst);
				filtroVO.setSede(codSede);
				filtroVO.setJornd(codJorn);
				filtroVO.setMetodo(-9);
				filtroVO.setGrado(-9);
				
				request.setAttribute("listaGrado", comDAO.getGrado(filtroVO));
				request.setAttribute("listaDocente", comDAO.getDocentes(filtroVO));
				request.setAttribute("listaAsignatura",comDAO.getAsignaturas(filtroVO.getInst(), filtroVO.getMetodo(), filtroVO.getGrado(), null, filtroVO.getVig()));
				
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
			    long codInst = Long.parseLong( params[0]);
			    long codSede = Long.parseLong( params[1]);
			    long codJord = Long.parseLong( params[2]);
				long codMetodo = Long.parseLong(params[3]);
				long vig = Long.parseLong(params[4]);
				
				
			   
				FiltroReportesVO filtroVO =  new FiltroReportesVO();
				filtroVO.setVig(vig);
				filtroVO.setInst(codInst);
				filtroVO.setSede(codSede);
				filtroVO.setJornd(codJord);
				filtroVO.setMetodo(codMetodo);
				filtroVO.setGrado(-9);	
	
				request.getSession().setAttribute("listaGrado",comDAO.getGrado(filtroVO));
			 	request.getSession().setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRAD));
			 	request.setAttribute("listaAsignatura",comDAO.getAsignaturas(filtroVO.getInst(), filtroVO.getMetodo(), filtroVO.getGrado(), null, filtroVO.getVig()));
			 	
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
				long codInst = Long.parseLong( params[0]);
				long codSede = Long.parseLong(params[1]);
				long codJord = Long.parseLong(params[2]);
				long codMetodo = Long.parseLong(params[3]);
				long codGrado = Long.parseLong(params[4]);
				long vig = Long.parseLong(params[5]);
				
			   
				
				FiltroReportesVO plantillaBoletionVO =  new FiltroReportesVO();
				plantillaBoletionVO.setVig(vig);
				plantillaBoletionVO.setInst(codInst);
				plantillaBoletionVO.setSede(codSede);
				plantillaBoletionVO.setJornd(codJord);
				plantillaBoletionVO.setMetodo(codMetodo);
				plantillaBoletionVO.setGrado(codGrado);
				 
				
				  request.getSession().setAttribute("listaGrupo", comDAO.getGrupo(plantillaBoletionVO) );
				  request.setAttribute("listaAsignatura",comDAO.getAsignaturas(plantillaBoletionVO.getInst(), plantillaBoletionVO.getMetodo(), plantillaBoletionVO.getGrado(), null, plantillaBoletionVO.getVig()));
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
	private void getAsignaturas(HttpServletRequest request, Login usuVO  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getASIGNATURAS");
		try{
			
			String inst=request.getParameter("inst");
			String metod=request.getParameter("metod");
			String grado=request.getParameter("grado");
			String codsAsig=request.getParameter("codsAsig");
			String conAreAsig=request.getParameter("conAreAsig");
			String tipoRep=request.getParameter("tipoRep");
			String vig=request.getParameter("vig");
			//String docente=request.getParameter("docente");
			String asig[]=null;
			long codInst =-99;			
			long codMetodo = -99;
			long codGrado = -99;
			long conAreAsig_ = -99;
			long tipoRep_ = -99;
			long vig_ = -99;
		   
			
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
			
			if(tipoRep != null && GenericValidator.isLong(tipoRep)){
				tipoRep_=Long.parseLong(tipoRep);
			}

			if(vig != null && GenericValidator.isLong(vig)){
				vig_=Long.parseLong(vig);
			}
			
			if(conAreAsig != null && GenericValidator.isLong(conAreAsig)){
				conAreAsig_=Long.parseLong(conAreAsig);
			}
			List areAsig=null;
			if(conAreAsig_==1){
				if(codInst>0){
					System.out.println("APOYO:COMP:AJAX:AREAS NORMAL");
					areAsig=comDAO.getAreas(codInst, codMetodo, asig, vig_);
				}
				else{
					System.out.println("APOYO:COMP:AJAX:AREAS GLOBAL");
					areAsig=comDAO.getAreasGlobal(asig);
				}
			}else if(conAreAsig_==2){
				if(codInst>0){					
					System.out.println("APOYO:COMP:AJAX:ASIG NORMAL");
					areAsig=comDAO.getAsignaturas(codInst, codMetodo, codGrado, asig, vig_);
				}	
				else{
					System.out.println("APOYO:COMP:AJAX:ASIG GLOBAL");
					areAsig=comDAO.getAsignaturasGlobal(asig);
				}
			} 
			request.setAttribute("listaAreasAsignaturas",areAsig);			 
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIG));
			System.out.println("AJAX ASIG COMPARATIVOS, CON AREASIG: "+conAreAsig_+", TIPOREP:"+tipoRep_);
			request.setAttribute("ajaxConAreAsig",""+conAreAsig_);
			request.setAttribute("ajaxTipoRep",""+tipoRep_);
				
			
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
			System.out.println("ERROR GET ASIGNATURAS");
			e.printStackTrace();
		}
	} 
	
	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getRango(HttpServletRequest request, Login usuVO,FiltroReportesVO filtro  )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getTIPO EVAL RANGOS");
		try{
			NivelEvalVO nivelVO=new NivelEvalVO();
			nivelVO.setCod_nivel_eval(1);
			TipoEvalVO tipoEval=null;
			String params[]=null;
			params=request.getParameterValues("ajax");
			
			String inst=null;
			String sede=null;
			String jornada=null;
			String metod=null;
			String grado=null;
			 
			
			if(params != null && GenericValidator.isLong(params[0]) && GenericValidator.isLong(params[1]) && GenericValidator.isLong(params[2]) && GenericValidator.isLong(params[3]) && GenericValidator.isLong(params[4]) ){
				inst=params[0];
				sede=params[1];
				jornada=params[2];
				metod=params[3];
				grado=params[4];
			}		
			
			//String asig[]=null;
			long codInst =-99;			
			long codMetodo = -99;
			long codGrado = -99;
			long codSede = -99;
			long codJornada = -99;
		    			
			if(inst != null && GenericValidator.isLong(inst) ){
				codInst = Long.parseLong(inst);	
			}
			if(metod != null && GenericValidator.isLong(metod) ){
				codMetodo = Long.parseLong(metod);	
			}
			if(grado != null && GenericValidator.isLong(grado) ){
				codGrado = Long.parseLong(grado);	
			}	
			if(sede != null && sede.length()>0){
				codSede = Long.parseLong(sede);
			}
			if(jornada != null && jornada.length()>0){
				codJornada = Long.parseLong(jornada);
			}
			filtro.setInst(codInst);
			filtro.setSede(codSede);
			filtro.setJornd(codJornada);
			filtro.setMetodo(codMetodo);
			filtro.setGrado(codGrado);
			
			if(codInst>0){
				nivelVO=comDAO.getNivelEval(filtro.getInst());
				tipoEval=comDAO.getTipoEval(filtro);
				filtro.setNumPer(nivelVO.getNumPer());
				filtro.setNomPerFinal(nivelVO.getNomPerFinal());
				filtro.setTipoEscala((int)tipoEval.getCod_tipo_eval());
			}else{
				tipoEval=new TipoEvalVO();
				tipoEval.setCod_tipo_eval(3);
				tipoEval.setEval_min(0);
				tipoEval.setEval_max(100);				
				tipoEval.setTipo_eval_prees(-9);
				tipoEval.setModo_eval(-9);
			}
			if(tipoEval.getCod_tipo_eval()==2){
				tipoEval.setEscala(comDAO.getEscalaConceptual(filtro));
			}else if(tipoEval.getCod_tipo_eval()==3){
				tipoEval.setEscala(comDAO.getEscalaMEN());
			}
			
			
			filtro.setValorIni(tipoEval.getEval_min());
			filtro.setValorFin(tipoEval.getEval_max());
			filtro.setTipoEscala(tipoEval.getModo_eval());
			
			
			request.getSession().setAttribute("filtroReportesVO",filtro);
			
			request.setAttribute("tipoEval", tipoEval);			 
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_RANGO));
				
			
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
			System.out.println("ERROR GET ASIGNATURAS");
			e.printStackTrace();
		}
	} 
	
	public List getListaPeriodo(long numPer, String nomPerDef){
		List l=new ArrayList();
		ItemVO item=null;
		System.out.println("ENTRO A CARGAR PERIODOS ** FILTRO EVALUACION EDIT"+numPer);
		for(int i=1;i<=numPer;i++){
			item=new ItemVO(i,""+i); l.add(item);
		}		
		item=new ItemVO(7,nomPerDef); l.add(item);
		return l;
	}
	
	
	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getColegios(HttpServletRequest request, Login usuVO)  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getCOLEGIOS SELECCIONAR");
		try{
			
			String inst=request.getParameter("loc");
			String codsAsig=request.getParameter("codsAsig");
			String tipoRep=request.getParameter("tipoRep");
			String vig=request.getParameter("vig");
			//String docente=request.getParameter("docente");
			String asig[]=null;
			long codInst =-99;			
			long tipoRep_ = -99;
			long vig_ = -99;
		   
			
			if(inst != null && GenericValidator.isLong(inst) ){
				codInst = Long.parseLong(inst);	
			}	
			if(codsAsig != null && codsAsig.length()>0){
				asig=codsAsig.replace(',',':').split(":");
			}
			
			if(tipoRep != null && GenericValidator.isLong(tipoRep)){
				tipoRep_=Long.parseLong(tipoRep);
			}

			if(vig != null && GenericValidator.isLong(vig)){
				vig_=Long.parseLong(vig);
			}
			List cols_=null;
			System.out.println("APOYO:REPORTES:AJAX:COLEGIOS SEL: "+codInst);
			cols_=comDAO.getColegios(codInst,asig);
			System.out.println("APOYO:REPORTES:AJAX:COLEGIOS SEL:LIST: "+cols_.size());
			request.setAttribute("listaColegios2",cols_);			 
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIG));
			request.setAttribute("ajaxTipoRep",""+tipoRep_);
				
			
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
			System.out.println("ERROR GET ASIGNATURAS");
			e.printStackTrace();
		}
	} 
	
	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	private void getGrados(HttpServletRequest request, Login usuVO)  throws ServletException{
		System.out.println(formaFecha.format(new Date()) + "  Ajax getGRADOS SELECCIONAR");
		try{
			
			String inst=request.getParameter("loc");
			String codsAsig=request.getParameter("codsAsig");
			String tipoRep=request.getParameter("tipoRep");
			String vig=request.getParameter("vig");
			//String docente=request.getParameter("docente");
			String asig[]=null;
			long codInst =-99;			
			long tipoRep_ = -99;
			long vig_ = -99;
		   
			
			if(inst != null && GenericValidator.isLong(inst) ){
				codInst = Long.parseLong(inst);	
			}	
			if(codsAsig != null && codsAsig.length()>0){
				asig=codsAsig.replace(',',':').split(":");
			}
			
			if(tipoRep != null && GenericValidator.isLong(tipoRep)){
				tipoRep_=Long.parseLong(tipoRep);
			}

			if(vig != null && GenericValidator.isLong(vig)){
				vig_=Long.parseLong(vig);
			}
			List cols_=null;
			System.out.println("APOYO:COMP:AJAX:AREAS NORMAL");
			cols_=comDAO.getGradoGlobal();
			
			request.setAttribute("listaGrados2",cols_);			 
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_ASIG));
			request.setAttribute("ajaxTipoRep",""+tipoRep_);
				
			
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			// TODO: handle exception
			System.out.println("ERROR GET ASIGNATURAS");
			e.printStackTrace();
		}
	} 
 
	
	
}
