package poa.aprobarCambios.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.aprobarCambios.vo.ParamsVO;
import poa.aprobarCambios.dao.CambiosDAO;
import poa.planeacion.vo.PlaneacionVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * Maneja las peticiones referentes a calculo de valores de los formularios 
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service{
	private static final long serialVersionUID = 6220013753285143307L;	
	/**
	 * Ruta de la pagina de ajax para actividad con recursos
	 */
	public String FICHA_CAMBIO;
	
	/**
	 * Ruta de la pagina de ajax para la actividad sin recursos
	 */
	
	
	/**
	 * Objeto de acceso a datos 
	 */
	private CambiosDAO cambiosDAO=new CambiosDAO(new Cursor());
	/*
	 * Procesa la peticinn
	 * @param request peticinn de usuario
	 * @param response respuesta al usuario
	 * @return Strign Ruta de destino
	 * @throws ServletException
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String FICHA=null;
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FICHA_CAMBIO=config.getInitParameter("FICHA_CAMBIO");		
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		switch (TIPO){
			case ParamsVO.FICHA_CAMBIO:
				FICHA=FICHA_CAMBIO;
				switch (CMD){					
					case ParamsVO.CMD_AJAX_COLEGIO:
						planeacionColegio(request, session, usuVO);
					break;	
					case ParamsVO.CMD_AJAX_TIPOGASTO:
						planeacionTipoGasto(request, session, usuVO);
					break;
				}
			break;
			
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	public void planeacionTipoGasto(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[3]==null) return;
			
			PlaneacionVO planeacionVO = (PlaneacionVO) session.getAttribute("cambioActividadesVO");
			session.removeAttribute("cambioActividadesVO");
			planeacionVO.setTipoGasto(params[3]);
			session.setAttribute("cambioActividadesVO", planeacionVO);
			
			request.removeAttribute("listaRubroGasto");
			request.setAttribute("listaRubroGasto",cambiosDAO.getListaRubroGasto(planeacionVO.getTipoGasto()));
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_TIPOGASTO));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	
	
	

	/**
	 * Calcula la lista de colegios de una localidad  
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @throws ServletException
	 */
	public void planeacionColegio(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[0]==null || params[1]==null) return;
			request.setAttribute("listaColegio", cambiosDAO.getListaColegios(Long.parseLong(params[0]),Long.parseLong(params[1])));
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_COLEGIO));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	
}
