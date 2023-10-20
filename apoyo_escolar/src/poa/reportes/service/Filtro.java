package poa.reportes.service;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.common.vo.ItemVO;
import siges.login.beans.Login;
import poa.reportes.vo.ParamsVO;
import poa.reportes.dao.ReportesDAO;
import siges.common.service.Service;
import poa.reportes.vo.FiltroReportesVO;


/**
 * Procesa las peticiones del filtro de busqueda
 * 15/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Filtro extends Service {
	
	private static final long serialVersionUID = 6300013753285143307L;
	
	/**
	 * Ruta de la pagina de filtro de consulta de colegios
	 */
	public String FICHA_REPORTES_COLEGIO;
	
	/**
	 * Ruta de la pagina de filtro de consulta de SED
	 */
	public String FICHA_REPORTES_SED;
	
	/**
	 * Ruta de la pagina de filtro de consulta de Localidad
	 */
	public String FICHA_REPORTES_LOC;
	
	/**
	 * Objeto de acceso a datos
	 */
	private ReportesDAO reportesDAO = new ReportesDAO(new Cursor());

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
	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		
		FICHA_REPORTES_SED = config.getInitParameter("FICHA_REPORTES_SED");
		FICHA_REPORTES_LOC = config.getInitParameter("FICHA_REPORTES_LOC");
		FICHA_REPORTES_COLEGIO = config.getInitParameter("FICHA_REPORTES_COLEGIO");
		
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		Login usuVO = (Login) session.getAttribute("login");
		FiltroReportesVO filtro = (FiltroReportesVO)session.getAttribute("filtroReportesPOA");
		
		switch (TIPO) {
			case ParamsVO.FICHA_REPORTES:
				FICHA = consultaNuevo(request, session, usuVO, filtro);
				break;
		}
		
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		
		return dispatcher;
		
	}
	
	/**
	 * Inicializa los objetos de la pagina de filtro de reportes  
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtro
	 * @return Ruta del form de filtro
	 * @throws ServletException
	 */
	public String consultaNuevo(HttpServletRequest request, HttpSession session, Login usuVO, FiltroReportesVO filtro) throws ServletException {
		
		try {
			
			List listaReportes = new ArrayList();
			List listaPeriodos = new ArrayList();
			
			int niv = Integer.parseInt(usuVO.getNivel());
			
			ItemVO itemVO = new ItemVO(ParamsVO.CMD_REPORTE_POA, " Reporte POA");
			listaReportes.add(itemVO);
			
			itemVO = new ItemVO(ParamsVO.CMD_REPORTE_GEN_SEGUIMIENTO, " Reporte General Seguimiento");
			listaReportes.add(itemVO);
			
			if(niv == 1 || niv == 2) {
				itemVO = new ItemVO(ParamsVO.CMD_REPORTE_NEC_AREA_GESTION, " Necesidades por Área de Gestión");
				listaReportes.add(itemVO);
				
				//itemVO = new ItemVO(ParamsVO.CMD_REPORTE_NEC_LIN_ACCION, " Necesidades por Línea de Acción");
				//listaReportes.add(itemVO);
				
				itemVO = new ItemVO(ParamsVO.CMD_CONSULTA_GEN_ACT, " Consulta General de Actividades");
				listaReportes.add(itemVO);
				
				itemVO = new ItemVO(ParamsVO.CMD_CONSULTA_GEN_NEC, " Consulta General de Necesidades");
				listaReportes.add(itemVO);
			}
			
			//itemVO=new ItemVO(ParamsVO.CMD_REPORTE_CUMP_METAS_LINEAS_ACCION," Cumplimiento de Metas por Lnnea de Accinn");
			//listaReportes.add(itemVO);
			
			itemVO = new ItemVO(ParamsVO.CMD_REPORTE_AVANCE_PON_AREAS, " Avance del Ponderador por Áreas de Gestión");
			listaReportes.add(itemVO);
			
			itemVO = new ItemVO(ParamsVO.CMD_REPORTE_AVANCE_PON_LINEAS_ACCION, " Avance del Ponderador por Líneas de Acción");
			listaReportes.add(itemVO);
			
			itemVO = new ItemVO(ParamsVO.CMD_REPORTE_POA_CONSOLIDADO, " Reporte General Consolidado");
			listaReportes.add(itemVO);
			
			//itemVO = new ItemVO(ParamsVO.CMD_REPORTE_ESTADO_META_LINEAS_ACCION, " Estado Actual de la Meta por Línea de Acción");
			//listaReportes.add(itemVO);
			
			if(filtro == null) {
				
				filtro = new FiltroReportesVO();
				filtro.setFilVigencia((int)reportesDAO.getVigenciaNumericoPOA());
				session.setAttribute("filtroReportesPOA", filtro);
			}
			
			filtro.setFilUsuario(usuVO.getUsuarioId());
			
			request.setAttribute("listaVigencia", reportesDAO.getListaVigenciaActual());
			request.getSession().setAttribute("listaObjetivos", reportesDAO.getlistaObjetivos());
			
			request.setAttribute("listaReportes", listaReportes);
			request.setAttribute("listaPeriodos", reportesDAO.getListaPeriodos(filtro.getFilVigencia()));
			
			request.setAttribute("listaAreas", reportesDAO.getListaAreasGestion());			
			request.setAttribute("listaFuentesFin", reportesDAO.getListaFuentesFin());
			
			//System.out.println("************************************NIVEL USUARIO "+niv+" USUARUIO"+filtro.getFilUsuario());
			if(niv == 1) {
				//System.out.println("************************************ENTRO NIVEL SED "+niv+"   pag "+FICHA_REPORTES_SED );
				request.setAttribute("listaLocalidad", reportesDAO.getListaLocalidad());
				if(filtro.getFilLocalidad() != 0) {
					request.setAttribute("listaColegio", reportesDAO.getListaColegio(filtro.getFilLocalidad(), filtro.getFilVigencia()));
				}
				
				return FICHA_REPORTES_SED;
				
			} else if(niv == 2) {
				
				//request.setAttribute("listaLocalidad", reportesDAO.getListaLocalidad());
				int loc = 0;
				
				if(!usuVO.getMunId().equals("") && usuVO.getMunId() != null) {
					loc = Integer.parseInt(usuVO.getMunId());
				}
				
				//System.out.println("************************************ENTRO NIVEL LOCALIDAD "+niv+"   **LOC "+loc+"  LOCIDUSU "+usuVO.getLocId()+" MUN "+usuVO.getMunId());
				filtro.setFilLocalidad(loc);
				if(filtro.getFilLocalidad() != 0) {
					request.setAttribute("listaColegio", reportesDAO.getListaColegio(filtro.getFilLocalidad(), filtro.getFilVigencia()));
				}
				
				return FICHA_REPORTES_LOC;
				
			} else {
				
				//System.out.println("************************************ENTRO NIVEL COLEGIO "+niv);
				//int loc=0;
				//if(!usuVO.getMunId().equals("") && usuVO.getMunId()!=null){
				//	loc=Integer.parseInt(usuVO.getMunId());
				//}
				
				int inst = -99;
				if(!usuVO.getInstId().equals("") && usuVO.getInstId() != null) {
					inst = Integer.parseInt(usuVO.getInstId());
				}
				
				filtro.setFilInstitucion(inst);
				
				return FICHA_REPORTES_COLEGIO;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		
	}
	
}
