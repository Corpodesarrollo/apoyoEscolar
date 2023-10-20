package poa.aprobacionActividades.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.aprobacionActividades.dao.PlaneacionDAO;
import poa.aprobacionActividades.vo.FiltroPlaneacionVO;
import poa.aprobacionActividades.vo.ParamsVO;
import poa.aprobacionActividades.vo.PlaneacionVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * Procesa las peticiones del formulario de edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	private static final long serialVersionUID = 6240013753285143307L;
	/**
	 * Ruta de la pagina de ingreso/edicion de actividad con recursos
	 */
	public String FICHA_ACTIVIDAD;
	/**
	 * Ruta de la pagina de ingreso/edicion de actividad sin recursos
	 */
	public String FICHA_ACTIVIDAD_SIN;
	/**
	 * Archivo de acceso a datos
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
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		PlaneacionVO planeacion=(PlaneacionVO)session.getAttribute("aprobacionActividadesVO");
		FiltroPlaneacionVO filtro=(FiltroPlaneacionVO)session.getAttribute("filtroAprobacionActividadesVO");
		PlaneacionVO planeacionSin=(PlaneacionVO)session.getAttribute("aprobacionActividadesSinVO");
		switch (TIPO){
		case ParamsVO.FICHA_ACTIVIDAD:
			FICHA=FICHA_ACTIVIDAD;
			switch (CMD){
				case ParamsVO.CMD_NUEVO:
					session.removeAttribute("aprobacionActividadesVO");
					session.removeAttribute("filtroAprobacionActividadesVO");
					
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_BUSCAR_COL:
					planeacionBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_BUSCAR:
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_EDITAR:
					planeacionEditar(request, session, usuVO,filtro);
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
					planeacion=(PlaneacionVO)session.getAttribute("aprobacionActividadesVO");
				break;	
				case ParamsVO.CMD_APROBAR_SED:
					planeacionAprobar(request, session, usuVO,filtro);
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
					session.removeAttribute("aprobacionActividadesVO");
				break;
				case ParamsVO.CMD_RECHAZAR_SED:
					planeacionRechazar(request, session, usuVO,filtro);
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
					session.removeAttribute("aprobacionActividadesVO");
				break;
			}
			planeacionNuevo(request, session, usuVO,planeacion,filtro);
		break;
		case ParamsVO.FICHA_ACTIVIDAD_SIN:
			FICHA=FICHA_ACTIVIDAD_SIN;
			switch (CMD){
				case ParamsVO.CMD_NUEVO:
					session.removeAttribute("aprobacionActividadesSinVO");
					planeacionSinBuscar(request, session, usuVO,filtro);
					planeacionSinBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_BUSCAR_COL:
					planeacionSinBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_BUSCAR:
					planeacionSinBuscar(request, session, usuVO,filtro);
					planeacionSinBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_EDITAR:
					planeacionSinEditar(request, session, usuVO,filtro);
					planeacionSinBuscar(request, session, usuVO,filtro);
					planeacionSinBuscarCol(request, session, usuVO,filtro);
					planeacionSin=(PlaneacionVO)session.getAttribute("aprobacionActividadesSinVO");
				break;
				case ParamsVO.CMD_APROBAR_SED:
					planeacionAprobar(request, session, usuVO,filtro);
					planeacionSinBuscar(request, session, usuVO,filtro);
					planeacionSinBuscarCol(request, session, usuVO,filtro);
					session.removeAttribute("aprobacionActividadesSinVO");
				break;
				case ParamsVO.CMD_RECHAZAR_SED:
					planeacionRechazar(request, session, usuVO,filtro);
					planeacionSinBuscar(request, session, usuVO,filtro);
					planeacionSinBuscarCol(request, session, usuVO,filtro);
					session.removeAttribute("aprobacionActividadesSinVO");
				break;
			}
			planeacionSinNuevo(request, session, usuVO,planeacionSin);
		break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}

	/**
	 *  Inicializa los objetos necesarios para la pagina de ingreso/edicion de actividad con recursos
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param planeacion Actividad
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionNuevo(HttpServletRequest request, HttpSession session, Login usuVO,PlaneacionVO planeacion,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			request.setAttribute("listaVigencia", planeacionDAO.getListaVigenciaActual());
			request.setAttribute("listaAreaGestion", planeacionDAO.getListaAreaGestion());
			request.setAttribute("listaTipoMeta", planeacionDAO.getListaTipoMeta());
			request.setAttribute("listaUnidadMedida", planeacionDAO.getListaUnidadMedida());
			request.setAttribute("listaFuenteFinanciera", planeacionDAO.getListaFuenteFinanciera());
			request.setAttribute("listaAccionMejoramiento",planeacionDAO.getListaAccionMejoramiento());
			request.getSession().setAttribute("listaObjetivos", planeacionDAO.getlistaObjetivos());
			if(planeacion!=null && planeacion.getPlaAreaGestion()!=0){
				request.setAttribute("listaLineaAccion", planeacionDAO.getListaLineaAccion(planeacion.getPlaAreaGestion()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 *  Inicializa los objetos necesarios para la pagina de ingreso/edicion de actividad sin recursos
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param planeacion Actividad
	 * @throws ServletException
	 */
	public void planeacionSinNuevo(HttpServletRequest request, HttpSession session, Login usuVO,PlaneacionVO planeacion) throws ServletException {
		try {
			request.setAttribute("listaVigencia", planeacionDAO.getListaVigenciaActual());
			request.setAttribute("listaAreaGestion", planeacionDAO.getListaAreaGestion());
			request.setAttribute("listaUnidadMedida", planeacionDAO.getListaUnidadMedida());
			request.setAttribute("listaFuenteFinanciera", planeacionDAO.getListaFuenteFinancieraSin());
			if(planeacion!=null && planeacion.getPlaAreaGestion()!=0){
				request.setAttribute("listaLineaAccion", planeacionDAO.getListaLineaAccion(planeacion.getPlaAreaGestion()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 *  Calcula la lista de actividades con recursos para los parametros de filtro indicados
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionBuscar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaActividades", planeacionDAO.getListaActividades(filtro));
				Logger.print(usuVO.getUsuarioId(),"POA: Buscar actividad con recursos. "+filtro.getFilInstitucion()+". "+filtro.getFilVigencia(),2,1,this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 *  Calcula la lista de colegios
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionBuscarCol(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaColegio", planeacionDAO.getListaColegio(filtro));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 *  Calcula la lista de actividades sin recursos para los parametros de filtro indicados
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionSinBuscar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaActividades", planeacionDAO.getListaActividadesSin(filtro));
				Logger.print(usuVO.getUsuarioId(),"POA: Buscar actividad sin recursos. "+filtro.getFilInstitucion()+". "+filtro.getFilVigencia(),2,1,this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 * Obtiene la actividad con recursos solicitada  
	 * @param request peticinn
	 * @param session Sesinn de usuario
	 * @param usuVO Login de usuario 
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionEditar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			String id=request.getParameter("id");
			String id2=request.getParameter("id2");
			String id3=request.getParameter("id3");
			if(id!=null && id2!=null && id3!=null){
				PlaneacionVO planeacion = planeacionDAO.getActividad(Integer.parseInt(id),Long.parseLong(id2),Long.parseLong(id3),filtro.getFilEstatoPOA()); 
				session.setAttribute("aprobacionActividadesVO",planeacion);
				request.setAttribute("listaAccionMejoramiento",planeacionDAO.getListaAccionMejoramiento());
				request.setAttribute("listaFuenteFinanciacion",planeacionDAO.getListaFuenteFinanciacion());
				request.setAttribute("listaRubroGasto", planeacionDAO.getListaRubroGasto(planeacion.getTipoGasto()));
				Logger.print(usuVO.getUsuarioId(),"POA: Edicion de actividad con recursos. "+id+". "+id2+". "+id3,2,1,this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Obtiene la actividad sin recursos solicitada 
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario 
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionSinEditar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			String id=request.getParameter("id");
			String id2=request.getParameter("id2");
			String id3=request.getParameter("id3");
			if(id!=null && id2!=null && id3!=null){
				session.setAttribute("aprobacionActividadesSinVO",planeacionDAO.getActividadSin(Integer.parseInt(id),Long.parseLong(id2),Long.parseLong(id3),filtro.getFilEstatoPOA()));
				Logger.print(usuVO.getUsuarioId(),"POA: Edicion de actividad sin recursos. "+id+". "+id2+". "+id3,2,1,this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 *  Aprueba el POA para el colegio y la vigencia indicados
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO login de usuario
	 * @param filtro Filtro de bnsqueda de actividadees
	 * @throws ServletException
	 */
	public void planeacionAprobar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			if(filtro!=null){
				if(filtro.getFilVigencia()!=0){
					planeacionDAO.aprobarSed(filtro);
					request.setAttribute(ParamsVO.SMS, "El POA fue aprobado satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(),"POA: Aprobacion de POA SED. "+filtro.getFilVigencia()+". "+filtro.getFilInstitucion(),6,1,this.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El POA no fue aprobado: "+e.getMessage());
		}
	}

	
	/**
	 *  Rechaza el POA para el colegio y la vigencia indicados
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionRechazar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			if(filtro!=null){
				if(filtro.getFilVigencia()!=0){
					planeacionDAO.rechazarSed(filtro);
					request.setAttribute(ParamsVO.SMS, "El POA fue devuelto satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(),"POA: Devolucion de POA SED. "+filtro.getFilVigencia()+". "+filtro.getFilInstitucion(),6,1,this.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El POA no fue devuelto: "+e.getMessage());
		}
	}

	/**
	 *  Calcula la lista de colegios
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionSinBuscarCol(HttpServletRequest request, HttpSession session, Login usuVO,FiltroPlaneacionVO filtro) throws ServletException {
		try {
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaColegio", planeacionDAO.getListaColegio(filtro));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
