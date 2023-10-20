/**
 * Paquete que atiende las solicitudes para el mndulo de Inscripción
 */
package siges.gestionAdministrativa.inscripcion.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.inscripcion.dao.InscripcionDAO;
import articulacion.inscripcion.vo.FiltroInscripcionVO;
import articulacion.inscripcion.vo.ParamsVO;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
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
	public String FICHA_NUEVO;
	private Login usuVO;
	InscripcionDAO insDAO = new InscripcionDAO(new Cursor());
	InscripcionDAO inscripcionDAO = null;
  
	 
	 /**
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		inscripcionDAO = new InscripcionDAO(new Cursor());
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
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
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int TIPO=getTipo(request,session,ParamsVO.FICHA_NUEVO); 
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		FiltroInscripcionVO filtroInscripcion = (FiltroInscripcionVO) session.getAttribute("filtroInscripcionVO");
		
		
		
		FICHA = FICHA_NUEVO;				
		switch (TIPO){
			case ParamsVO.FICHA_NUEVO:
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					nuevo(request,session, usuVO, filtroInscripcion);
					break;
				case ParamsVO.CMD_BUSCAR:
					buscar(request,session, usuVO, filtroInscripcion);
					break;	
				case ParamsVO.CMD_GUARDAR:
					String []inscripcion = request.getParameterValues("x");
					if(inscripcion != null && inscripcion.length > 0){
						guardar(request,session, usuVO, inscripcion, filtroInscripcion);
						buscar(request,session, usuVO, filtroInscripcion);
					}
					break;	
			}
		}
		session.setAttribute("listaEspecialidad",insDAO.getListaespecialidadesInst(usuVO.getInstId()));
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;	
	}
	
	/**
	 * Aprueba las inscripciones solicitadas por los estudiantes
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param inscripcion
	 * @param filtroInscripcion
	 */
	private void guardar(HttpServletRequest request, HttpSession session,
			Login usuVO2, String[] inscripcion,
			FiltroInscripcionVO filtroInscripcion) {
		if(insDAO.aprobarInscripciones(filtroInscripcion,inscripcion, usuVO2)){
			request.setAttribute(Params.SMS, "Las incripciones fueron realizadas satisfactoriamente");
		} else {
			request.setAttribute(Params.SMS, "Hubo un problema procesando tu solicitud");
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
			HttpSession session, Login usuVO2, FiltroInscripcionVO inscripcion) {
		inscripcion = new FiltroInscripcionVO();
		request.getSession().setAttribute("filtroInscripcionVO", inscripcion);
		session.removeAttribute("listaAsignaturas");
		request.getSession().removeAttribute("listaAsignaturas");
		request.removeAttribute("listaAsignaturas");
	}
	
	/**
	 * LLena las asignaturas seleccionadas en los criterios del filtro
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 */
	private void buscar(HttpServletRequest request,
			HttpSession session, Login usuVO2, FiltroInscripcionVO filtroInscripcion) {
		session.setAttribute("filtroInscripcionVO",filtroInscripcion);
		request.setAttribute("inscripciones",inscripcionDAO.getGrupoInscritos(usuVO2, filtroInscripcion));
		session.setAttribute("listaAsignaturas",inscripcionDAO.getListaAsignaturasFromEspecialidad(usuVO.getInstId(), String.valueOf(filtroInscripcion.getFilEspecialidad())));
	}
	
}
