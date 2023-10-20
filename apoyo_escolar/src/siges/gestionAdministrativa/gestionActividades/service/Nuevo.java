/**
 * 
 */
package siges.gestionAdministrativa.gestionActividades.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.gestionActividades.dao.GestionActividadesDAO;
import siges.gestionAdministrativa.gestionActividades.vo.FiltroGestionVO;
import siges.gestionAdministrativa.gestionActividades.vo.GestionActVO;
import siges.gestionAdministrativa.gestionActividades.vo.ParamsVO;
import siges.login.beans.Login;
/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String FICHA_GESTION_ACTIVIDADES;
	private ResourceBundle rb;
	private GestionActividadesDAO gestionDAO = null;
	private Login usuVO;
	private String mensaje;
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
		gestionDAO = new GestionActividadesDAO(new Cursor());
		FICHA_GESTION_ACTIVIDADES = config.getInitParameter("FICHA_GESTION_ACTIVIDADES");
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
		FICHA_GESTION_ACTIVIDADES= config.getInitParameter("FICHA_GESTION_ACTIVIDADES");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int TIPO=getTipo(request,session,ParamsVO.FICHA_GESTION_ACTIVIDADES); 
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		FiltroGestionVO filtroGestion = (FiltroGestionVO)request.getSession().getAttribute("filtroGestionActividadesVO");
		GestionActVO gestionAct = (GestionActVO)request.getSession().getAttribute("gestionAct");

		
			FICHA = FICHA_GESTION_ACTIVIDADES;				
			//switch (TIPO){
			//case ParamsVO.FICHA_GESTION_ACTIVIDADES:
				switch (CMD) {

				case ParamsVO.CMD_NUEVO:
					request.getSession().removeAttribute("filtroGestionActividadesVO");
					request.getSession().removeAttribute("gestionAct");
					request.removeAttribute("listaActividades");
					gestionAct = new GestionActVO();
					filtroGestion = new FiltroGestionVO();
					request.getSession().setAttribute("gestionAct", gestionAct);
					request.getSession().setAttribute("filtroActividadesVO", filtroGestion);
					break;
				case ParamsVO.CMD_GUARDAR:
					if(gestionAct.getCodigo() == 0)
						FICHA = guardarActividad(request,session, usuVO, gestionAct); //Nuevo regsitro		
					else
						FICHA = editarActividad(request,session, usuVO, gestionAct);
					break;
				case ParamsVO.CMD_EDITAR:
						getActividad(request, TIPO);
					break;
				case ParamsVO.CMD_BUSCAR:
					buscarActividades(request,session, usuVO, filtroGestion);
					break;
				}
			//	break;
		//	}
		llenarListas(request, session, filtroGestion, gestionAct);	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}

	private String editarActividad(HttpServletRequest request,
			HttpSession session, Login usuVO2, GestionActVO gestionAct) {
		gestionAct.setUsuario(usuVO2.getUsuarioId());
		if(gestionDAO.updateActividad(gestionAct)){
			request.setAttribute(ParamsVO.SMS,
					"La actividad fue actualizada exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo actualizar la actividad.");
		}
		return FICHA_GESTION_ACTIVIDADES;
	}

	private String guardarActividad(HttpServletRequest request,
			HttpSession session, Login usuVO2, GestionActVO gestionAct) {
		gestionAct.setUsuario(usuVO2.getUsuarioId());
		if(gestionDAO.saveActividad(gestionAct)){
			request.setAttribute(ParamsVO.SMS,
					"El actividad fue creada exitosamente");
		} else {
			request.setAttribute(ParamsVO.SMS,
					"No se pudo guardar la actividad.");
		}
		return FICHA_GESTION_ACTIVIDADES;
	}

	/**
	 * Trae el listado de actividades segnn los criterios seleccionados en el filtr
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param filtroGestion
	 */
	private void buscarActividades(HttpServletRequest request,
			HttpSession session, Login usuVO2, FiltroGestionVO filtroGestion) {
		try {
			request.setAttribute("listaActividades", gestionDAO.getActividades(filtroGestion)); //filtroGestionActividadesVO
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}
	
	private void getActividad(HttpServletRequest request, long codigo){
		request.getSession().setAttribute("gestionAct", gestionDAO.getActividad(codigo));
	}

	/**
	 * Inicializa los valores de la listas del filtro
	 */
	private void llenarListas(HttpServletRequest request, HttpSession session, FiltroGestionVO filtroGestion, GestionActVO gestionAct) {
		try {
			if(filtroGestion == null){
				filtroGestion = new FiltroGestionVO();
				/**
				 * Por defecto se selecciona el mes actual
				 */
				int month = Calendar.getInstance().get(Calendar.MONTH);
				filtroGestion.setMES(month+1);
				
				/**
				 * Por defecto se selecciona el estado activo
				 */
				filtroGestion.setEstado(1);
				/**
				 * Por defecto se selecciona el nivel 1
				 */
				filtroGestion.setNivel(1);
				
				session.setAttribute("filtroGestionActividadesVO", filtroGestion);
			}
			
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
			e.printStackTrace();
		}
		
	}
	
	
	
}
