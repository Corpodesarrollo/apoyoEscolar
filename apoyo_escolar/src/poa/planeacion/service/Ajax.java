package poa.planeacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.planeacion.dao.PlaneacionDAO;
import poa.planeacion.vo.ParamsVO;
import poa.planeacion.vo.PlaneacionVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * Procesa las peticinn referentes a calculo de componentes de formulario
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service{
	private static final long serialVersionUID = 6160013753285143307L;
	/**
	 * Ruta de la pagina de ajax de una actividad con recursos
	 */
	public String FICHA_ACTIVIDAD;
	/**
	 * Ruta de la pagina de ajax de una actividad sin recursos
	 */
	public String FICHA_ACTIVIDAD_SIN;
	/**
	 * Objeto de accesos a datos
	 */
	private PlaneacionDAO planeacionDAO=new PlaneacionDAO(new Cursor());

	/*
	 *  Procesa la peticinn
	 * @param request peticinn
	 * @param response respuesta
	 * @return Ruta de redireccinn
	 * @throws ServletException
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String FICHA=null;
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FICHA_ACTIVIDAD=config.getInitParameter("FICHA_ACTIVIDAD");
		FICHA_ACTIVIDAD_SIN=config.getInitParameter("FICHA_ACTIVIDAD_SIN");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		switch (TIPO){
			case ParamsVO.FICHA_ACTIVIDAD:
				FICHA=FICHA_ACTIVIDAD;
				switch (CMD){
					case ParamsVO.CMD_AJAX_LINEA:
						planeacionLinea(request, session, usuVO);
					break;	
					case ParamsVO.CMD_AJAX_AREA:
						planeacionArea(request, session, usuVO);
					break;
					case ParamsVO.CMD_AJAX_TIPOGASTO:
						planeacionTipoGasto(request, session, usuVO);
					break;
				}
			break;
			case ParamsVO.FICHA_ACTIVIDAD_SIN:
				FICHA=FICHA_ACTIVIDAD_SIN;
				switch (CMD){
					case ParamsVO.CMD_AJAX_LINEA:
						planeacionSinLinea(request, session, usuVO);
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
			
			PlaneacionVO planeacionVO = (PlaneacionVO) session.getAttribute("planeacionVO");
			session.removeAttribute("planeacionVO");
			planeacionVO.setTipoGasto(params[3]);
			session.setAttribute("planeacionVO", planeacionVO);
			
			request.removeAttribute("listaRubroGasto");
			request.setAttribute("listaRubroGasto",planeacionDAO.getListaRubroGasto(planeacionVO.getTipoGasto()));
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_TIPOGASTO));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 *   Calcula la lista de lineas de accion de un area de gestinn
	 * @param request peticinn  
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @throws ServletException
	 */
	public void planeacionLinea(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[0]==null) return;
			request.setAttribute("listaLineaAccion", planeacionDAO.getListaLineaAccion(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_LINEA));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 *  Calcula la lista de lineas de accinn de una area de gestinn  
	 * @param request peticinn 
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @throws ServletException
	 */
	public void planeacionSinLinea(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[0]==null) return;
			request.setAttribute("listaLineaAccion", planeacionDAO.getListaLineaAccion(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_LINEA));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 *   Calcula la lista de areas de gestion
	 * @param request peticinn  
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @throws ServletException
	 */
	public void planeacionArea(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<3 || params[0]==null) return;
			request.setAttribute("listaAreaGestion", planeacionDAO.getListaAreaGestion(Integer.parseInt(params[0]),Long.parseLong(params[1]),Long.parseLong(params[2])));
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_AREA));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
