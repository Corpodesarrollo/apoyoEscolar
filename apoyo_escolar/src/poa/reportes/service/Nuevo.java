package poa.reportes.service;


import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import poa.reportes.vo.ParamsVO;
import poa.reportes.io.ReportesIO;
import poa.reportes.dao.ReportesDAO;
import poa.reportes.vo.FiltroReportesVO;
import poa.reportes.vo.ResultadoReportesVO;

import siges.dao.Cursor;
import siges.util.Logger;
import siges.login.beans.Login;
import siges.common.service.Service;


/**
 * Procesa las peticiones del formulario de consulta 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {
	
	private static final long serialVersionUID = 6310013753285143307L;
	
	/**
	 * Ruta de la pagina de ingreso/edicion de actividad con recursos
	 */
	public String FICHA_REPORTES;
	
	/**
	 * Ruta de la pagina de ERROR generando una consulta
	 */
	public String FICHA_ERROR_REPORTES;
	
	/**
	 * de la pagina de Descarga generando una consulta
	 */
	public String FICHA_DESCARGA_REPORTES;

	/**
	 * Archivo de acceso a datos
	 */
	private ReportesDAO reportesDAO = new ReportesDAO(new Cursor());

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de redireccion
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
				
		FICHA_REPORTES = config.getInitParameter("FICHA_REPORTES");
		FICHA_ERROR_REPORTES = config.getInitParameter("FICHA_ERROR_REPORTES");
		FICHA_DESCARGA_REPORTES = config.getInitParameter("FICHA_DESCARGA_REPORTES");
		
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		Login usuVO = (Login) session.getAttribute("login");
		FiltroReportesVO filtro = (FiltroReportesVO) session.getAttribute("filtroReportesPOA");
		
		switch (TIPO) {
		
			case ParamsVO.FICHA_REPORTES:
				
				FICHA = FICHA_REPORTES;
				
				switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						session.removeAttribute("filtroReportesPOA");
						ResultadoReportesVO resultadoOld = (ResultadoReportesVO) request.getSession().getAttribute("resultadoReportes");
				
						if (resultadoOld != null) {
							resultadoOld.setGenerado(false);
						}
				
						request.getSession().setAttribute("resultadoReportes", resultadoOld);

						break;
			
					case ParamsVO.CMD_BUSCAR:
						filtro = (FiltroReportesVO) session.getAttribute("filtroReportesPOA");
						FICHA = consultaBuscar(request, session, usuVO, filtro);
						break;
				}
				
				filtro = (FiltroReportesVO) session.getAttribute("filtroReportesPOA");
				//consultaNuevo(request, session, usuVO, filtro);
				break;
		}
		
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		
		return dispatcher;
		
	}
	

	/**
	 * Inicializa los objetos necesarios para la pagina de consulta de poa
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void consultaNuevo(HttpServletRequest request, HttpSession session, Login usuVO, FiltroReportesVO filtro) throws ServletException {
		
		filtro = new FiltroReportesVO();
		
		session.setAttribute("filtroReportesPOA", filtro);
		
		Logger.print(usuVO.getUsuarioId(), "Consulta de POA, módulo de Reportes. ", 7, 1, this.toString());
		
	}
	

	/**
	 * Calcula la lista de actividades del POA de los parametros de bnsqueda
	 * indicados
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public String consultaBuscar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroReportesVO filtro) throws ServletException {
		
		try {
			// Codigo para borrar reporte basura
			ResultadoReportesVO resultadoOld = (ResultadoReportesVO) request.getSession().getAttribute("resultadoReportes");
			
			if (resultadoOld != null && resultadoOld.getRutaDisco() != null) {
				File f = new File(resultadoOld.getRutaDisco());
				if (f.exists()) {
					f.delete();
				}
			}

			ResultadoReportesVO resultado = null;
			
			if (filtro != null && filtro.getFilVigencia() != 0) {
				
				filtro.setFilNivel(Integer.parseInt(usuVO.getNivel()));
				filtro.setFilRutaBase(context.getRealPath("/"));
				filtro.setFilUsuario(usuVO.getUsuarioId());
				
				ReportesIO reportesIO = new ReportesIO(reportesDAO);
				resultado = reportesIO.buscarReporteSED(filtro);
				
				if (resultado.isGenerado()) {
					
					request.getSession().setAttribute("resultadoReportes", resultado);
					request.setAttribute(ParamsVO.SMS, "Reporte generado satisfactoriamente");
					return FICHA_DESCARGA_REPORTES;
					
				} else {
					
					resultado.setGenerado(false);
					request.getSession().setAttribute("resultadoReportes", resultado);
					request.setAttribute(ParamsVO.SMS, "No hay datos para generar el reporte");
					
					return FICHA_ERROR_REPORTES;
				}
				
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		
		return FICHA_ERROR_REPORTES;
		
	}
	

	/**
	 * Calcula la lista de actividades del POA de los parametros de bnsqueda
	 * indicados
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void consultaBuscar2(HttpServletRequest request,	HttpServletResponse response, Login usuVO, FiltroReportesVO filtro) throws ServletException {
		
		response.setContentType("application/zip");
		
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		
		FileInputStream fi = null;
		
		int BUFFER_SIZE = 100000;
		int count;
		
		try {
			// if(filtro==null){
			filtro = new FiltroReportesVO();
			filtro.setFilVigencia(Integer.parseInt(request.getParameter("filVigencia")));
			filtro.setFilInstitucion(Long.parseLong(request.getParameter("filInstitucion")));
			filtro.setFilLocalidad(Integer.parseInt(request.getParameter("filLocalidad")));
			
			// }
			ResultadoReportesVO resultado = null;
			
			if (filtro != null && filtro.getFilVigencia() != 0) {
			
				filtro.setFilNivel(Integer.parseInt(usuVO.getNivel()));
				filtro.setFilRutaBase(context.getRealPath("/"));
				filtro.setFilUsuario(usuVO.getUsuarioId());
				
				if (resultado.isGenerado()) {
					
					StringBuffer contentDisposition = new StringBuffer();
					
					contentDisposition.append("attachment;");
					contentDisposition.append("filename=\"");
					contentDisposition.append(resultado.getArchivo());
					contentDisposition.append("\"");
					
					response.setHeader("Content-Disposition", contentDisposition.toString());
					
					File f = new File(resultado.getRutaDisco());
					if (!f.exists()) {
						return;
					}
					
					byte[] data = new byte[BUFFER_SIZE];
					fi = new FileInputStream(f);
					origin = new BufferedInputStream(fi, BUFFER_SIZE);
					ouputStream = response.getOutputStream();
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
						ouputStream.write(data, 0, count);
					}
					ouputStream.flush();
					ouputStream.close();
					origin.close();
					fi.close();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();
			} catch (Exception e) {
			}
		}
		
	}
	

	/**
	 * Calcula la lista de actividades del POA de los parametros de bnsqueda
	 * indicados
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void consultaBuscarSeguimiento(HttpServletRequest request, HttpServletResponse response, Login usuVO, FiltroReportesVO filtro) throws ServletException {
		
		response.setContentType("application/zip");
		
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		
		FileInputStream fi = null;
		
		int BUFFER_SIZE = 100000;
		int count;
		
		try {
			
			filtro = new FiltroReportesVO();
			filtro.setFilVigencia(Integer.parseInt(request.getParameter("filVigencia")));
			filtro.setFilInstitucion(Long.parseLong(request.getParameter("filInstitucion")));
			filtro.setFilLocalidad(Integer.parseInt(request.getParameter("filLocalidad")));
			
			ResultadoReportesVO resultado = null;
			
			if (filtro != null && filtro.getFilVigencia() != 0) {
				
				filtro.setFilNivel(Integer.parseInt(usuVO.getNivel()));
				filtro.setFilRutaBase(context.getRealPath("/"));
				filtro.setFilUsuario(usuVO.getUsuarioId());
				
				ReportesIO consultaIO = new ReportesIO(reportesDAO);
				
				if (resultado.isGenerado()) {
					StringBuffer contentDisposition = new StringBuffer();
					contentDisposition.append("attachment;");
					contentDisposition.append("filename=\"");
					contentDisposition.append(resultado.getArchivo());
					contentDisposition.append("\"");
					response.setHeader("Content-Disposition", contentDisposition.toString());
					
					File f = new File(resultado.getRutaDisco());
					if (!f.exists()) {
						return;
					}
					
					byte[] data = new byte[BUFFER_SIZE];
					fi = new FileInputStream(f);
					origin = new BufferedInputStream(fi, BUFFER_SIZE);
					ouputStream = response.getOutputStream();
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
						ouputStream.write(data, 0, count);
					}
					ouputStream.flush();
					ouputStream.close();
					origin.close();
					fi.close();
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();
			} catch (Exception e) {
			}
		}
	}
	
}
