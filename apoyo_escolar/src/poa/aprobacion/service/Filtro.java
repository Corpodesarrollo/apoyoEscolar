package poa.aprobacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.aprobacion.dao.PlaneacionDAO;
import poa.aprobacion.vo.FiltroPlaneacionVO;
import poa.aprobacion.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * Procesa las peticiones para el filtro de bnsqueda	
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Filtro extends Service{
	private static final long serialVersionUID = 6200013753285143307L;
	/**
	 * Ruta de la pagina de filtro de actividades con recursos
	 */
	public String FICHA_ACTIVIDAD;
	/**
	 * Ruta de la pagina de filtro de actividades sin recursos
	 */
	public String FICHA_ACTIVIDAD_SIN;
	/**
	 * Objeto de acceso a datos
	 */
	private PlaneacionDAO planeacionDAO=new PlaneacionDAO(new Cursor());

	/*
	 * Procesa la peticinn
	 * @param request peticinn
	 * @param response respuesta
	 * @return Ruta de direccionamiento
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
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		FiltroPlaneacionVO filtro=(FiltroPlaneacionVO)session.getAttribute("filtroAprobacionVO");
		switch (TIPO){
			case ParamsVO.FICHA_ACTIVIDAD:
				FICHA=FICHA_ACTIVIDAD;
				planeacionNuevo(request, session, usuVO,filtro);
			break;
			case ParamsVO.FICHA_ACTIVIDAD_SIN:
				FICHA=FICHA_ACTIVIDAD_SIN;
				planeacionSinNuevo(request, session, usuVO,filtro);
			break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}

	/**
	 *  Inicializa los objetos de la pagina de filtro de actividades con recursos
	 * @param request peticinn
	 * @param session session de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionNuevo(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			request.setAttribute("listaLocalidad", planeacionDAO.getListaLocalidad());
			request.setAttribute("listaVigencia", planeacionDAO.getListaVigenciaActual());
			request.setAttribute("listaAreaGestion", planeacionDAO.getListaAreaGestion());
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaColegio", planeacionDAO.getListaColegio(filtro.getFilLocalidad()));
			}
			if(filtro==null || filtro.getLblVigencia()==null){
				filtro=planeacionDAO.getLabels(filtro);
				session.setAttribute("filtroAprobacionVO",filtro);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 *  Inicializa los objetos de la pagina de filtro de actividades sin recursos
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionSinNuevo(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			request.setAttribute("listaLocalidad", planeacionDAO.getListaLocalidad());
			request.setAttribute("listaVigencia", planeacionDAO.getListaVigenciaActual());
			request.setAttribute("listaAreaGestion", planeacionDAO.getListaAreaGestion());
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaColegio", planeacionDAO.getListaColegio(filtro.getFilLocalidad()));
			}
			if(filtro==null || filtro.getLblVigencia()==null){
				filtro=planeacionDAO.getLabelsSin(filtro);
				session.setAttribute("filtroAprobacionVO",filtro);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
