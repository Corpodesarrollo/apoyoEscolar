/**
 * Paquete que atiende las solicitudes para el mndulo de AGENDA
 */
package siges.gestionAdministrativa.agenda.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.gestionAdministrativa.agenda.dao.GestionTareasDAO;
import siges.gestionAdministrativa.agenda.vo.CircularVO;
import siges.gestionAdministrativa.agenda.vo.FiltroCircularesVO;
import siges.gestionAdministrativa.agenda.vo.FiltroPermisosVO;
import siges.gestionAdministrativa.agenda.vo.FiltroTareasVO;
import siges.gestionAdministrativa.agenda.vo.ParamsVO;
import siges.gestionAdministrativa.agenda.vo.PermisosVO;
import siges.gestionAdministrativa.agenda.vo.TareasVO;
import siges.gestionAdministrativa.boletin.dao.BoletinDAO;
import siges.login.beans.Login;
/**
 * 26/07/2013 
 * @author Mauricio A. Coral Lozada
 * @version 1.1
 */
public class Nuevo extends Service{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String FICHA_TAREAS;
	public String FICHA_CIRCULARES;
	private String FICHA_PERMISOS;
	private GestionTareasDAO tareaDAO = null;
	private BoletinDAO boletinDAO = null;
	private Login usuVO;
	private ResourceBundle rPath;
  
	 
	 /**
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tareaDAO = new GestionTareasDAO(new Cursor());
		boletinDAO= new BoletinDAO(new Cursor()); 
		FICHA_TAREAS = config.getInitParameter("FICHA_TAREAS");
		FICHA_CIRCULARES = config.getInitParameter("FICHA_CIRCULARES");
		FICHA_PERMISOS = config.getInitParameter("FICHA_PERMISOS");
		rPath = ResourceBundle.getBundle("path");
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
		FICHA_TAREAS = config.getInitParameter("FICHA_TAREAS");
		FICHA_CIRCULARES = config.getInitParameter("FICHA_CIRCULARES");
		FICHA_PERMISOS = config.getInitParameter("FICHA_PERMISOS");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int TIPO=getTipo(request,session,ParamsVO.FICHA_TAREAS); 
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		TareasVO tareas = null;
		FiltroCircularesVO circularFiltro = null;
		CircularVO circular = null;
		FiltroPermisosVO permisosFiltro = null;
		PermisosVO permiso = null;
		
		try{
		/**
		 * Tareas
		 */
			tareas = (TareasVO)request.getSession().getAttribute("tareasAct");
		} catch (Exception e){}
		
		try{
		/**
		 * Circulares
		 */
			circularFiltro = (FiltroCircularesVO)request.getSession().getAttribute("circularFiltro");
			circular = (CircularVO)request.getSession().getAttribute("circularesAct");
		} catch (Exception e){}
		
		try{
		/**
		 * Permisos
		 */
			permisosFiltro = (FiltroPermisosVO)request.getSession().getAttribute("permisosFiltro");
			permiso = (PermisosVO)request.getSession().getAttribute("permisosAct");
		} catch (Exception e){}
		
		
		if(permiso == null)
			permiso = new PermisosVO();
		permiso.setNivel(Integer.parseInt(usuVO.getPerfil()));
		if(tareas == null)
			tareas= new TareasVO();
		if(circularFiltro == null)
			circularFiltro = new FiltroCircularesVO();
		circularFiltro.setNivelUsuario(Integer.parseInt(usuVO.getPerfil()));
		if(circular == null)
			circular = new CircularVO();
		circular.setNivelUsuario(Integer.parseInt(usuVO.getPerfil()));
		if(permisosFiltro == null)
			permisosFiltro = new FiltroPermisosVO();
		
		String params[]= null;
		
		FICHA = FICHA_TAREAS;				
		switch (TIPO){
			case ParamsVO.FICHA_TAREAS:
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					nuevo(request,session, usuVO, tareas);
					break;
				case ParamsVO.CMD_GUARDAR:
					if(tareas.getCodigo() == 0){
						FICHA = guardarTarea(request,session, usuVO, tareas); //Nuevo registro
					}
					else{
						FICHA = editarTarea(request,session, usuVO, tareas);
					}
					nuevo(request,session, usuVO, tareas);
					break;
				case ParamsVO.CMD_EDITAR:
						params = request.getParameterValues("ajax");
						long tarea = Long.parseLong(params[0]);
						getTarea(request, tarea);
						buscarTareas(request,session, usuVO, tareas);
					break;
				case ParamsVO.CMD_BUSCAR:
					buscarTareas(request,session, usuVO, tareas);
					break;
				}
				break;
			case ParamsVO.FICHA_CIRCULARES:	
				FICHA = FICHA_CIRCULARES;	
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					nuevoCircular(request,session, usuVO, circular);
					request.getSession().setAttribute("tareasAct", circular);
					break;
				case ParamsVO.CMD_GUARDAR:
					if(circular.getCodigo() == 0){
						guardarCircular(request,session, usuVO, circular);
					}
					else {
						editarCircular(request,session, usuVO, circular);
					}
					nuevoCircular(request,session, usuVO, circular);
					buscarCircular(request,session, usuVO, circularFiltro);
					break;
				case ParamsVO.CMD_EDITAR:
						params=request.getParameterValues("ajax");
						getCircular(request, Integer.parseInt(params[0]), session);
						buscarCircular(request,session, usuVO, circularFiltro);
						CircularVO c = tareaDAO.getCircular(Integer.parseInt(params[0]), session);
						getDatos(request, c.getNivel()+"", usuVO);
						buscarCircular(request,session, usuVO, circularFiltro);
					break;
				case ParamsVO.CMD_BUSCAR:
						nuevoCircular(request,session, usuVO, circular);
						buscarCircular(request,session, usuVO, circularFiltro);
					break;
				}
				break;
			case ParamsVO.FICHA_PERMISOS:	
				FICHA = FICHA_PERMISOS;	
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					nuevoPermiso(request,session, usuVO);
					break;
				case ParamsVO.CMD_GUARDAR:
					if(permiso.getCodigo() == 0){
						guardarPermiso(request,session, usuVO, permiso);
					}
					else {
						editarPermiso(request,session, usuVO, permiso);
					}
					nuevoPermiso(request,session, usuVO);
					break;
				case ParamsVO.CMD_EDITAR:
					params=request.getParameterValues("ajax");
						getPermiso(request, Integer.parseInt(params[0]), usuVO);
						buscarPermiso(request,session, usuVO, permisosFiltro);
					break;
				case ParamsVO.CMD_BUSCAR:
					buscarPermiso(request,session, usuVO, permisosFiltro);
					break;
				case ParamsVO.CMD_ELIMINAR:
					params = request.getParameterValues("ajax");
					eliminarPermiso(request, Integer.parseInt(params[0]));
					FiltroPermisosVO temp = permisosFiltro;
					nuevoPermiso(request,session, usuVO);
					buscarPermiso(request,session, usuVO, temp);
					break;
				case ParamsVO.CMD_GENERAR_HISTORICO:
					params = request.getParameterValues("ajax");
					permisosFiltro.setFile(descargarPermiso(request, Integer.parseInt(params[0]), usuVO, permiso, response));
					buscarPermiso(request,session, usuVO, permisosFiltro);
					permisosFiltro.setDownload(true);
					break;
				}
				break;	
			}
		llenarListas(request,usuVO.getInstId());
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	
	
	
	
	
	
	/**
	 * Trae la información de la lista desplegable segnn el nivel de la circular
	 * @param request
	 * @param usuVO2
	 */
	private void getDatos(HttpServletRequest request, String nivel, Login usuVO2) {
		try {
			List list = new ArrayList();
			if(nivel.equals("1")){ // Colegio
				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(1);
				itemVO.setNombre(usuVO2.getInst());
				list.add(itemVO);
			} else if(nivel.equals("2")){ // Jornada
				list = boletinDAO.getJornada(Integer.parseInt(usuVO2.getInstId()), Integer.parseInt(usuVO2.getSedeId()));
			} else if(nivel.equals("3")){ // Sede
				list = boletinDAO.getSede(Integer.parseInt(usuVO2.getInstId()));
			} else if(nivel.equals("4")){ // Sede - Jornanda
				list = boletinDAO.getJornada(Integer.parseInt(usuVO2.getInstId()), Integer.parseInt(usuVO2.getSedeId()));
			} else if(nivel.equals("5")){ // Metodologia - Grado
				list = boletinDAO.getMetodologia(Integer.parseInt(usuVO2.getInstId()));
			} else if(nivel.equals("6")){ // Grupo
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
	 * Limpia los datos de Tareas
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 */
	private void nuevo(HttpServletRequest request,
			HttpSession session, Login usuVO2, TareasVO tareas) {
		request.getSession().removeAttribute("filtroTareasVO");
		request.getSession().removeAttribute("tareasAct");
		request.removeAttribute("listaTareas");
		tareas = new TareasVO();
		FiltroTareasVO filtroItemsVO = new FiltroTareasVO();
		request.getSession().setAttribute("tareasAct", tareas);
		request.getSession().setAttribute("filtroTareasVO", filtroItemsVO);
	}
	
	/**
	 * Limpia los datos de sesion de circulares
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 */
	private void nuevoCircular(HttpServletRequest request,
			HttpSession session, Login usuVO2, CircularVO c) {
		request.getSession().removeAttribute("filtroCircularVO");
		request.getSession().removeAttribute("circularesAct");
		session.removeAttribute("idCircular");
		request.removeAttribute("listaDatos");
		CircularVO circular = new CircularVO();
		FiltroCircularesVO  filtroItemsVO = new FiltroCircularesVO();
		filtroItemsVO.setNivelUsuario(Integer.parseInt(usuVO.getPerfil()));
		circular.setNivelUsuario(Integer.parseInt(usuVO.getPerfil()));
		request.getSession().setAttribute("circularesAct", circular);
		request.getSession().setAttribute("filtroCircularVO", filtroItemsVO);
	}
	
	/**
	 * Limpia los datos de sesion de permisos
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 */
	private void nuevoPermiso(HttpServletRequest request,
			HttpSession session, Login usuVO2) {
		request.getSession().removeAttribute("permisosFiltro");
		request.getSession().removeAttribute("permisosAct");
		PermisosVO permiso = new PermisosVO();
		permiso.setNivel(Integer.parseInt(usuVO.getPerfil()));
		FiltroPermisosVO  filtroItemsVO = new FiltroPermisosVO();
		request.getSession().setAttribute("permisosFiltro", filtroItemsVO);
		request.getSession().setAttribute("permisosAct", permiso);
	}

	/**
	 * Edita la tarea seleccionada
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	private String editarTarea(HttpServletRequest request,
			HttpSession session, Login usuVO2, TareasVO tareas) {
		tareas.setUsuario(usuVO2.getUsuarioId());
		if(tareaDAO.updateTarea(request, session, usuVO2, tareas)){
			request.setAttribute(ParamsVO.SMS,
					"La Tarea fue actualizada exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo actualizar la Tarea.");
		}
		return FICHA_TAREAS;
	}
	
	/**
	 * Edita la circular seleccionada
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	private String editarCircular(HttpServletRequest request,
			HttpSession session, Login usuVO2, CircularVO c) {
		c.setUsuario(Long.parseLong(usuVO2.getUsuarioId()));
		if(tareaDAO.updateCircular(request, session, usuVO2, c)){
			request.setAttribute(ParamsVO.SMS,
					"La Circular fue actualizada exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo actualizar la Circular.");
		}
		return FICHA_TAREAS;
	}
	
	/**
	 * Edita el permiso seleccionado
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	private String editarPermiso(HttpServletRequest request,
			HttpSession session, Login usuVO2, PermisosVO c) {
		c.setUsuario(Long.parseLong(usuVO2.getUsuarioId()));
		if(tareaDAO.updatePermiso(request, session, usuVO2, c)){
			request.setAttribute(ParamsVO.SMS,
					"El permiso fue actualizado exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo actualizar el permiso.");
		}
		return FICHA_TAREAS;
	}

	/**
	 * Guarda una nueva tarea
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	private String guardarTarea(HttpServletRequest request,
			HttpSession session, Login usuVO2, TareasVO c) {
		c.setUsuario(usuVO2.getUsuarioId());
		if(tareaDAO.saveTarea( request,	 session,  usuVO2, c)){
			request.setAttribute(ParamsVO.SMS,
					"La Tarea fue creada exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo guardar la Tarea.");
		}
		return FICHA_TAREAS;
	}
	
	/**
	 * Guarda una nueva circular
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	private String guardarCircular(HttpServletRequest request,
			HttpSession session, Login usuVO2, CircularVO c) {
		c.setUsuario(Long.parseLong(usuVO2.getUsuarioId()));
		if(tareaDAO.saveCircular( request,	 session,  usuVO2, c)){
			request.setAttribute(ParamsVO.SMS,
					"La Circular fue creada exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo guardar la Circular.");
		}
		return FICHA_TAREAS;
	}
	
	/**
	 * Guarda una nueva circular
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	private String guardarPermiso(HttpServletRequest request,
			HttpSession session, Login usuVO2, PermisosVO c) {
		c.setUsuario(Long.parseLong(usuVO2.getUsuarioId()));
		if(tareaDAO.savePermiso( request,	 session,  usuVO2, c)){
			request.setAttribute(ParamsVO.SMS,
					"El permiso fue creado exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo guardar el Permiso.");
		}
		return FICHA_PERMISOS;
	}

	/**
	 * Trae el listado de Tareas segnn los criterios seleccionados en el filtr
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param filtroGestion
	 */
	private void buscarTareas(HttpServletRequest request,
			HttpSession session, Login usuVO2, TareasVO filtroGestion) {
		try {
			request.setAttribute("listaTareas", tareaDAO.getTarea(filtroGestion, request, usuVO2)); //filtroTareasVO
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}
	
	/**
	 * Trae el listado de Tareas según los criterios seleccionados en el filtr
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param filtroGestion
	 */
	private void buscarCircular(HttpServletRequest request,
			HttpSession session, Login usuVO2, FiltroCircularesVO c) {
		try {
			request.setAttribute("listaCirculares", tareaDAO.getCircular(c, request, usuVO2)); //filtroTareasVO
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}
	
	/**
	 * Trae el listado de Permisos segnn los criterios seleccionados en el filtro
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param filtroGestion
	 */
	private void buscarPermiso(HttpServletRequest request,
			HttpSession session, Login usuVO2, FiltroPermisosVO c) {
		try {
			request.setAttribute("listaPermisos", tareaDAO.getPermisos(c, request, usuVO2)); //filtroTareasVO
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}
	
	/**
	 * Trae la tarea especificada en las condiciones del filtro
	 * @param request
	 * @param codigo
	 */
	private void getTarea(HttpServletRequest request, long codigo){
		request.getSession().setAttribute("tareasAct", tareaDAO.getTarea(codigo));
	}
	
	/**
	 * Trae la circular especificada en las condiciones del filtro
	 * @param request
	 * @param codigo
	 */
	private void getCircular(HttpServletRequest request, long codigo, HttpSession session){
		request.getSession().setAttribute("circularesAct", tareaDAO.getCircular(codigo, session));
	}
	
	/**
	 * Trae el permiso especificado en las condiciones del filtro
	 * @param request
	 * @param codigo
	 */
	private void getPermiso(HttpServletRequest request, long filtro, Login u){
		request.getSession().setAttribute("permisosAct", tareaDAO.getPermiso(filtro,u));
	}
	
	/**
	 * Elimina el permiso especificado en las condiciones del filtro
	 * @param request
	 * @param codigo
	 */
	private void eliminarPermiso(HttpServletRequest request, long id){
		if(tareaDAO.deletePermiso(id)){
			request.setAttribute(ParamsVO.SMS,
					"El permiso fue eliminado exitosamente.");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}
	
	/**
	 * Descargar el permiso especificado en las condiciones del filtro
	 * @param request
	 * @param codigo
	 */
	private String descargarPermiso(HttpServletRequest request, long id, Login login, PermisosVO permiso, HttpServletResponse response){
		try {
			String nombrePdf = "Permiso_" + id+"_" +login.getInstId() + ".pdf";
			String path = Ruta.get(context,
					rPath.getString("resultados.PathResultados")) + "permisos/";
			String rutaJasper = Ruta2.get(context.getRealPath("/"),"siges.gestionAdministrativa.agenda.jasper");
			
			Map map=new HashMap();
			
			map.put("INSTITUCION",  login.getInst());
			map.put("estudiante", new Long(id));
			map.put("sede",  login.getSede());
			map.put("jornada",  login.getJornada());
	
		    File reportFile= new File(rutaJasper + "/permisos.jasper");
			tareaDAO.getArchivoPDF(reportFile,map,path,nombrePdf);
			return nombrePdf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	/**
	 * Trae las metodologias dela institucinn
	 */
	private void llenarListas(HttpServletRequest request, String idInst){
		try {
			/**
			 * Lista de metodolognas
			 */
			request.setAttribute("filtroMetodologiaF", tareaDAO.getListaMetodologia(Long.parseLong(idInst)));
			/**
			 * Lista de permisos
			 */
			request.setAttribute("tipoPermiso", tareaDAO.getListaPermisos());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}
	
	
}
