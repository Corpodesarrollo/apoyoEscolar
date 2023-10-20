/**
 * Paquete que atiende las solicitudes para el mndulo de Inscripción
 */
package siges.gestionAcademica.inscripcion.service;

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

import articulacion.inscripcion.dao.InscripcionDAO;
import articulacion.inscripcion.vo.FiltroInscripcionVO;
import articulacion.inscripcion.vo.InscripcionVO;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.gestionAcademica.inscripcion.vo.ParamsVO;
import siges.login.beans.Login;
/**
 * 19/09/2013 
 * @author Mauricio A. Coral Lozada
 * @version 1.1
 */
public class Nuevo extends Service{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String FICHA_INSCRIPCION;
	private Login usuVO;
	
	InscripcionDAO inscripcionDAO = null;
  
	 
	 /**
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		inscripcionDAO = new InscripcionDAO(new Cursor());
		FICHA_INSCRIPCION = config.getInitParameter("FICHA_INSCRIPCION");
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
		
		/*String dispatcher[]=new String[2];
		context=config.getServletContext();
		String FICHA=null;
		FICHA_INSCRIPCION = config.getInitParameter("FICHA_INSCRIPCION");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int TIPO=getTipo(request,session,ParamsVO.FICHA_INSCRIPCION); 
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		FiltroInscripcionVO filtroInscripcion = (FiltroInscripcionVO) session.getAttribute("filtroInscripcion2VO");
		String [] inscripcion = request.getParameterValues("x");

		
		String params[]= null;
		
		FICHA = FICHA_INSCRIPCION;				
		switch (TIPO){
			case ParamsVO.FICHA_INSCRIPCION:
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					//nuevo(request,session, usuVO, inscripcion);
					break;
				case ParamsVO.CMD_GUARDAR:
					FICHA = guardarInscripcion(filtroInscripcion, inscripcion, request); //Nuevo registro
					//nuevo(request,session, usuVO, inscripcion);
					break;
				case ParamsVO.CMD_EDITAR:
						params = request.getParameterValues("ajax");
						long tarea = Long.parseLong(params[0]);
						getTarea(request, tarea);
						//buscarTareas(request,session, usuVO, inscripcion);
					break;
				case ParamsVO.CMD_BUSCAR:
					//buscarTareas(request,session, usuVO, inscripcion);
					break;
				}
				break;
				
			//}
		//llenarListas(request,usuVO.getInstId());
		//dispatcher[0]=String.valueOf(Params.INCLUDE);
		//dispatcher[1]=FICHA;
		//return dispatcher;
*/	
		return null;
		}
	
	
	
	
	
	
	
	/**
	 * Trae la información de la lista desplegable según el nivel de la circular
	 * @param request
	 * @param usuVO2
	 */
/*	private void getDatos(HttpServletRequest request, String nivel, Login usuVO2) {
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
	}*/

	/**
	 * Limpia los datos de Tareas
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 */
	private void nuevo(HttpServletRequest request,
			HttpSession session, Login usuVO2, InscripcionVO inscripcion) {
		request.getSession().removeAttribute("filtroTareasVO");
		request.getSession().removeAttribute("tareasAct");
		request.removeAttribute("listaTareas");
		inscripcion = new InscripcionVO();
		FiltroInscripcionVO filtroItemsVO = new FiltroInscripcionVO();
		request.getSession().setAttribute("inscripcionAct", inscripcion);
		request.getSession().setAttribute("filtroInscripcionVO", filtroItemsVO);
	}
	
	

	/**
	 * Edita la tarea seleccionada
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	/*private String editarTarea(HttpServletRequest request,
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
	}*/
	
	
	
	

	/**
	 * Guarda una nueva tarea
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	private String guardarInscripcion(FiltroInscripcionVO filtroInscripcion, String[] inscripcion, HttpServletRequest request) {
		try {
			if(inscripcionDAO.insertar(filtroInscripcion,inscripcion)){
				request.setAttribute(Params.SMS, "La información fue ingresada satisfactoriamente");
			}else{
				request.setAttribute(Params.SMS, "No se puede registrar la inscripcinn: " + inscripcionDAO.getMensaje());	
			}
		} catch (Exception e) {
			request.setAttribute(Params.SMS, "No se puede registrar la inscripcinn: " + inscripcionDAO.getMensaje());	
			e.printStackTrace();
		}
		return FICHA_INSCRIPCION;
	}
	
	

	/**
	 * Trae el listado de Tareas segnn los criterios seleccionados en el filtr
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param filtroGestion
	 */
	/*private void buscarTareas(HttpServletRequest request,
			HttpSession session, Login usuVO2, TareasVO filtroGestion) {
		try {
			request.setAttribute("listaTareas", tareaDAO.getTarea(filtroGestion, request, usuVO2)); //filtroTareasVO
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}*/
	
	
	
	/**
	 * Trae la tarea especificada en las condiciones del filtro
	 * @param request
	 * @param codigo
	 */
	/*private void getInscripcion(HttpServletRequest request, long codigo){
		request.getSession().setAttribute("tareasAct", inscripcionDAO.getTarea(codigo));
	}*/
	
	
	
	/**
	 * Elimina la inscripción especificado en las condiciones del filtro
	 * @param request
	 * @param codigo
	 */
	/*private void eliminarInscripcion(HttpServletRequest request, long id){
		if(tareaDAO.deletePermiso(id)){
			request.setAttribute(ParamsVO.SMS,
					"El permiso fue eliminado exitosamente.");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}*/
	
	
	
	
	
	/**
	 * LLena las listas
	 */
	/*private void llenarListas(HttpServletRequest request, String idInst){
		try {
			*//**
			 * Lista de metodolognas
			 *//*
			request.setAttribute("filtroMetodologiaF", tareaDAO.getListaMetodologia(Long.parseLong(idInst)));
			*//**
			 * Lista de permisos
			 *//*
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
	}*/
	
	
}
