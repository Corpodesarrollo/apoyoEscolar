/**
 * 
 */
package siges.gestionAdministrativa.integracionNomina.service;

import java.io.IOException; 

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.integracionNomina.dao.IntegracionNominaDAO;
import siges.gestionAdministrativa.integracionNomina.vo.ParamsVO;
import siges.login.beans.Login;
/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	
	private static String FICHA_INTEGRACION_NOMINA;
	//private ResourceBundle rb; 
	private IntegracionNominaDAO integracionNominaDAO  ;
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
		integracionNominaDAO = new IntegracionNominaDAO(new Cursor());
	//	rb = ResourceBundle.getBundle("siges.gestionAdministrativa.integracionNomina.bundle.integracionNomina");
		FICHA_INTEGRACION_NOMINA = config.getInitParameter("FICHA_INTEGRACION_NOMINA");
		
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
		context=config.getServletContext();
		String FICHA=null; 
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);  
		System.out.println(Nuevo.class.getName() + TIPO +"   CMD "+ CMD);
		switch (TIPO){
		case ParamsVO.FICHA_INTEGRACION_NOMINA:
			
			FICHA = FICHA_INTEGRACION_NOMINA;				
			
			switch (CMD){
			case ParamsVO.CMD_CALL_NOMINA:
				callProcedNomina(request,session, usuVO);
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
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	private void callProcedNomina(HttpServletRequest request, HttpSession session,Login usuVO ) throws ServletException {
		System.out.println("cargarListas ");
		
		try {	
			integracionNominaDAO.callProcedNomina(usuVO.getUsuarioId());
			request.setAttribute(ParamsVO.SMS,"Integracinn realizada satisfactoriamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new  ServletException("Errror interno: " + e.getMessage());
		}
	}
	}
