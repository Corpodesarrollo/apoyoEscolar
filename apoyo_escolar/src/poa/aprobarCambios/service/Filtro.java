package poa.aprobarCambios.service;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.common.vo.Params;
import siges.login.beans.Login;
import siges.common.service.Service;
import poa.aprobarCambios.vo.ParamsVO;
import poa.aprobarCambios.dao.CambiosDAO;
import poa.aprobarCambios.vo.FiltroCambiosVO;


/**
 * Procesa las peticiones referentes al filtro de busqueda
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Filtro extends Service{
	
	private static final long serialVersionUID = 6170013753285143307L;
	
	/**
	 * Ruta de la pagina de filtro de actividades con recursos
	 */
	public String FICHA_FILTRO;
		
	/**
	 * Objeto de acceso a datos
	 */
	private CambiosDAO cambiosDAO = new CambiosDAO(new Cursor());

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
	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		
		FICHA_FILTRO = config.getInitParameter("FICHA_FILTRO");
		
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		Login usuVO = (Login) session.getAttribute("login");
		FiltroCambiosVO filtro = (FiltroCambiosVO)session.getAttribute("filtroCambioVO");
		
		switch (TIPO) {
			case ParamsVO.FICHA_CAMBIO:
				FICHA = FICHA_FILTRO;
				planeacionNuevo(request, session, usuVO, filtro);
			break;			
		}
		
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		
		return dispatcher;
		
	}

	
	/**
	 *   Inicializa los objetos de la pagina de filtro de actividades con recursos
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de busqueda
	 * @throws ServletException
	 */
	public void planeacionNuevo(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			request.setAttribute("listaVigencia", cambiosDAO.getListaVigenciaActual());
			
			int nivelUsuario = Integer.parseInt(usuVO.getNivel());
			
			filtro = cambiosDAO.getLabels(filtro);
			filtro.setFilNivel(3);//3. NIVEL COLEGIO
			
			if(nivelUsuario == 2) {
				if(usuVO.getMunId() != null && !usuVO.getMunId().equals("")) {
					filtro.setFilLoc(Integer.parseInt(usuVO.getMunId()));
				}
			}
			
			if(filtro.getFilLoc() > 0) {
				
				request.setAttribute("listaLocalidades", cambiosDAO.getListaLocalidadesTodas());
				List colegios = cambiosDAO.getListaColegios(filtro.getFilVigencia(), filtro.getFilLoc());
				
				if(colegios != null && colegios.size() > 0)
					request.setAttribute("listaColegios", colegios);
				else
					request.setAttribute(ParamsVO.SMS, "No hay colegios con solicitudes de cambio para la vigencia actual.");
				
			} else {
				
				List localidades = cambiosDAO.getListaLocalidades(filtro.getFilVigencia());
				
				if(localidades != null && localidades.size() > 0)
					request.setAttribute("listaLocalidades", localidades);
				else
					request.setAttribute(ParamsVO.SMS, "No hay colegios con solicitudes de cambio para la vigencia actual.");
			}
			
			filtro.setFilUsuario(usuVO.getUsuarioId());
			filtro.setFilNivel(3);
			
			session.setAttribute("filtroCambioVO", filtro);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
		
	}
	
}
