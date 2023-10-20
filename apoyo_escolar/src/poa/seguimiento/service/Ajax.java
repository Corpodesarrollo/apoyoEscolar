package poa.seguimiento.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.seguimiento.dao.SeguimientoDAO;
import poa.seguimiento.vo.ParamsVO;
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
	private static final long serialVersionUID = 6320013753285143307L;
	/**
	 * Ruta de la pagina de ajax de una actividad con recursos
	 */
	public String FICHA_ACTIVIDAD;
	/**
	 * Objeto de accesos a datos
	 */
	private SeguimientoDAO seguimientoDAO=new SeguimientoDAO(new Cursor());

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
		String FICHA=null;
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FICHA_ACTIVIDAD=config.getInitParameter("FICHA_ACTIVIDAD");
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
				}
			break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
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
			request.setAttribute("listaLineaAccion", seguimientoDAO.getListaLineaAccion(Integer.parseInt(params[0])));
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
			request.setAttribute("listaAreaGestion", seguimientoDAO.getListaAreaGestion(Integer.parseInt(params[0]),Long.parseLong(params[1]),Long.parseLong(params[2])));
			request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_AREA));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
