package poa.seguimientoSED.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.seguimientoSED.dao.SeguimientoDAO;
import poa.seguimientoSED.vo.FiltroSeguimientoVO;
import poa.seguimientoSED.vo.ParamsVO;
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
	private static final long serialVersionUID = 6360013753285143307L;
	/**
	 * Ruta de la pagina de filtro de actividades con recursos
	 */
	public String FICHA_ACTIVIDAD;
	/**
	 * Objeto de acceso a datos
	 */
	private SeguimientoDAO seguimientoDAO=new SeguimientoDAO(new Cursor());

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
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		FiltroSeguimientoVO filtro=(FiltroSeguimientoVO)session.getAttribute("filtroSeguimientoSEDVO");
		switch (TIPO){
			case ParamsVO.FICHA_ACTIVIDAD:
				FICHA=FICHA_ACTIVIDAD;
				planeacionNuevo(request, session, usuVO,filtro);
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
	public void planeacionNuevo(HttpServletRequest request, HttpSession session, Login usuVO,FiltroSeguimientoVO filtro) throws ServletException {
		try {
			request.setAttribute("listaLocalidad", seguimientoDAO.getListaLocalidad());
			request.setAttribute("listaVigencia", seguimientoDAO.getListaVigenciaActual());
			//request.setAttribute("listaAreaGestion", seguimientoDAO.getListaAreaGestion());
			//if(filtro!=null && filtro.getFilVigencia()!=0){
			//	request.setAttribute("listaColegio", seguimientoDAO.getListaColegio(filtro.getFilLocalidad()));
			//}
			//if(filtro==null || filtro.getLblVigencia()==null){ //se pone en comentario esta linea que genera inconsistencia la expresion "filtro.getLblVigencia()==null"
		if(filtro==null ){ 
			filtro=seguimientoDAO.getLabels(filtro);
				if(usuVO.getMunId()!=null && !usuVO.getMunId().equals("")){
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilTieneLocalidad(true);
				}
				session.setAttribute("filtroSeguimientoSEDVO",filtro);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
