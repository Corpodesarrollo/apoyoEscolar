/**
 * 
 */
package poa.consulta.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.consulta.dao.ConsultaDAO;
import poa.consulta.vo.FiltroConsultaVO;
import poa.consulta.vo.ParamsVO;
import siges.common.service.Service;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * Procesa las peticiones del filtro de busqueda
 * 15/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Filtro extends Service{
	private static final long serialVersionUID = 6260013753285143307L;
	/**
	 * Ruta de la pagina de filtro de consulta de colegios
	 */
	public String FICHA_CONSULTA_COLEGIO;
	/**
	 * Ruta de la pagina de filtro de consulta de SED
	 */
	public String FICHA_CONSULTA_SED;
	/**
	 * Objeto de acceso a datos
	 */
	private ConsultaDAO consultaDAO=new ConsultaDAO(new Cursor());

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
		FICHA_CONSULTA_COLEGIO=config.getInitParameter("FICHA_CONSULTA_COLEGIO");
		FICHA_CONSULTA_SED=config.getInitParameter("FICHA_CONSULTA_SED");
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		FiltroConsultaVO filtro=(FiltroConsultaVO)session.getAttribute("filtroConsultaPOA");
		switch (TIPO){
			case ParamsVO.FICHA_CONSULTA:
				FICHA=consultaNuevo(request, session, usuVO,filtro);
			break;
		}
		dispatcher[0]=String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	/**
	 * Inicializa los objetos de la pagina de filtro de consulta  
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtro
	 * @return Ruta del form de filtro
	 * @throws ServletException
	 */
	public String consultaNuevo(HttpServletRequest request, HttpSession session, Login usuVO,FiltroConsultaVO filtro) throws ServletException {
		try {
			if(filtro==null){
				filtro=new FiltroConsultaVO();
				filtro.setFilVigencia((int)consultaDAO.getVigenciaNumericoPOA());
				if(usuVO.getMunId()!=null && !usuVO.getMunId().equals("")){
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilTieneLocalidad(true);
				}
				session.setAttribute("filtroConsultaPOA", filtro);
			}
			request.setAttribute("listaVigencia", consultaDAO.getListaVigenciaActual());
			int niv=Integer.parseInt(usuVO.getNivel());
			if(niv<4){
				request.setAttribute("listaLocalidad", consultaDAO.getListaLocalidad());
				if(filtro.getFilLocalidad()!=0){
					request.setAttribute("listaColegio", consultaDAO.getListaColegio(filtro.getFilLocalidad()));
				}
				return FICHA_CONSULTA_SED;
			}else{
				return FICHA_CONSULTA_COLEGIO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
