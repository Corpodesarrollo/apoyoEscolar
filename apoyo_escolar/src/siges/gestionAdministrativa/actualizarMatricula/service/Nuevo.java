/**
 * 
 */
package siges.gestionAdministrativa.actualizarMatricula.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;



import siges.gestionAdministrativa.actualizarMatricula.dao.ActualizarMatrDAO;
import siges.gestionAdministrativa.actualizarMatricula.thread.Hilo;
import siges.gestionAdministrativa.actualizarMatricula.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.gestionAdministrativa.actualizarMatricula.vo.ItemVO;
/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	
	public String FICHA_ACTUALIZACION_MATR;
	private ResourceBundle rb;
	private String contextoTotal;
	private ActualizarMatrDAO actualizarMatrDAO;
	private Login usuVO;
	/**
	 *  
	 
	 /*
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		actualizarMatrDAO = new ActualizarMatrDAO(new Cursor());
		FICHA_ACTUALIZACION_MATR=config.getInitParameter("FICHA_ACTUALIZAR");
	}
	
	/*
	 * Procesa la peticinn
	 * @param request peticinn
	 * @param response respuesta
	 * @return Ruta de redireccinn
	 * @throws ServletException
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		rb = ResourceBundle.getBundle("participacion.acta.bundle.acta");
		contextoTotal=context.getRealPath("/");
		String FICHA=null;
		
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		System.out.println("valoresActualizacinMatricula: "+TIPO+"_"+CMD);
		switch (TIPO){
		case ParamsVO.FICHA_ACTUALIZACION_MATR:
			
			FICHA=FICHA_ACTUALIZACION_MATR;				
			cargarFiltro(request);
			switch (CMD){
			case ParamsVO.CMD_GUARDAR:
				actualizarMatriculas(request);
				cargarFiltro(request);
				break;	
				
			}
			
			break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		System.out.println("FICHA " + FICHA);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	/**
	 * @function:  
	 * @param request
	 * @throws ServletException
	 */
	private void actualizarMatriculas(HttpServletRequest request) throws ServletException{
		try{
			
			String vigencia = request.getParameter("perVigencia");
			
			if(vigencia != null && GenericValidator.isInt(vigencia )){
				System.out.println("vigencia " + vigencia);
				//actualizarMatrDAO.callProcedimientoMatr(Integer.valueOf(vigencia).intValue());
				
				if(!actualizarMatrDAO.getValidarHiloVigencia(Integer.valueOf(vigencia).intValue()) ){
					actualizarMatrDAO.guardarHiloCierre(Integer.valueOf(vigencia).intValue());
					
					Hilo t=new Hilo();
					t.start();
				}else{
					request.setAttribute(ParamsVO.SMS,"No se puede realizar la operacinn:\n. Existe un proceso realizando esta actualizacinn.");
				}
				//request.setAttribute(ParamsVO.SMS,"La actualizacinn se realizn con exito");
			}else{
				request.setAttribute(ParamsVO.SMS,"Error actualizando matricula");
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println( e.getMessage() );
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		
	}
	
	private void cargarFiltro(HttpServletRequest request) throws ServletException{
		try{
			List listaVigencia = new ArrayList();
			
			
			//Vigencias
			int parVigencia = actualizarMatrDAO.getParVigencia();
			
			ItemVO itemVO = new  ItemVO(parVigencia,parVigencia+"");
			ItemVO itemVO2 = new ItemVO( parVigencia+1 , (parVigencia +1 ) + "");
			
			listaVigencia.add(itemVO);
			listaVigencia.add(itemVO2);
			request.setAttribute("listaVigencia", listaVigencia);
			
			//Estado de la tabla DATOS_MATRICULA
			List listaTablaHilo = actualizarMatrDAO.getCierreHilosVO();
			request.setAttribute("listaTablaHilo",listaTablaHilo);
			
			//ResultadoVO
			List listaResultadoVO = actualizarMatrDAO.getlistaResultadoVO();
			request.setAttribute("listaResultadoVO",listaResultadoVO);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println( e.getMessage() );
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		
	}
	
}
