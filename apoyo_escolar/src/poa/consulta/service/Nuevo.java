/**
 * 
 */
package poa.consulta.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.consulta.dao.ConsultaDAO;
import poa.consulta.io.ConsultaIO;
import poa.consulta.vo.FiltroConsultaVO;
import poa.consulta.vo.ParamsVO;
import poa.consulta.vo.ResultadoVO;
import siges.common.service.Service;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * Procesa las peticiones del formulario de consulta 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {
	private static final long serialVersionUID = 6270013753285143307L;
	/**
	 * Ruta de la pagina de ingreso/edicion de actividad con recursos
	 */
	public String FICHA_CONSULTA;
	/**
	 * Ruta de la pagina de ERROR generando una consulta
	 */
	public String FICHA_ERROR_CONSULTA;
	/**
	 * de la pagina de Descarga generando una consulta
	 */
	public String FICHA_DESCARGA_CONSULTA;

	/**
	 * Archivo de acceso a datos
	 */
	private ConsultaDAO consultaDAO = new ConsultaDAO(new Cursor());

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
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		FICHA_CONSULTA = config.getInitParameter("FICHA_CONSULTA");
		FICHA_ERROR_CONSULTA = config.getInitParameter("FICHA_ERROR_CONSULTA");
		FICHA_DESCARGA_CONSULTA = config
				.getInitParameter("FICHA_DESCARGA_CONSULTA");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		FiltroConsultaVO filtro = (FiltroConsultaVO) session
				.getAttribute("filtroConsultaPOA");

		// System.out.println(this.getClass() +" TIPO " + TIPO +" cmd " + CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_CONSULTA:
			FICHA = FICHA_CONSULTA;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				session.removeAttribute("aprobacionVO");
				session.removeAttribute("filtroConsultaPOA");
				session.removeAttribute("filtroConsultaPOA");

				break;
			case ParamsVO.CMD_BUSCAR:
				FICHA = consultaBuscar(request, session, usuVO, filtro);
				break;
			case ParamsVO.CMD_BUSCAR2:
				consultaBuscar2(request, response, usuVO, filtro);
				return null;
			case ParamsVO.CMD_BUSCAR_SEGUIMIENTO:
				consultaBuscarSeguimiento(request, response, usuVO, filtro);
				return null;
			}
			consultaNuevo(request, session, usuVO, filtro);
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
	public void consultaNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroConsultaVO filtro) throws ServletException {
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
	public String consultaBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroConsultaVO filtro)
			throws ServletException {
		try {
			ResultadoVO resultado = null;
			if (filtro != null && filtro.getFilVigencia() != 0) {
				filtro.setFilNivel(Integer.parseInt(usuVO.getNivel()));
				// request.setAttribute("listaConsulta",
				// consultaDAO.getListaPOA(filtro));
				// Logger.print(usuVO.getUsuarioId(),"POA: Buscar actividad con recursos. "+filtro.getFilInstitucion()+". "+filtro.getFilVigencia(),2,1,this.toString());
				filtro.setFilRutaBase(context.getRealPath("/"));
				filtro.setFilUsuario(usuVO.getUsuarioId());
				ConsultaIO consultaIO = new ConsultaIO(consultaDAO);
				if (filtro.getFilNivel() < 4) {// es admnistrador
					resultado = consultaIO.buscarPOASED(filtro);
				} else {
					resultado = consultaIO.buscarPOA(filtro);
				}
				if (resultado.isGenerado()) {
					request.setAttribute("resultadoConsulta", resultado);
					request.setAttribute(ParamsVO.SMS,
							"Plantilla generada satisfactoriamente");
					return FICHA_DESCARGA_CONSULTA;
				} else {
					request.setAttribute(ParamsVO.SMS,
							"Plantilla no generada: No hay datos para generar el reporte");
					return FICHA_ERROR_CONSULTA;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		return FICHA_ERROR_CONSULTA;
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
	public void consultaBuscar2(HttpServletRequest request,
			HttpServletResponse response, Login usuVO, FiltroConsultaVO filtro)
			throws ServletException {
		
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		File f = null;
		int BUFFER_SIZE = 100000;
		int count;
		// System.out.println("ENTRO A GENERAR REPORTE POA ****** ");
		try {
			filtro = new FiltroConsultaVO();
			filtro.setFilVigencia(Integer.parseInt(request
					.getParameter("filVigencia")));
			filtro.setFilInstitucion(Long.parseLong(request
					.getParameter("filInstitucion")));
			filtro.setFilLocalidad(Integer.parseInt(request
					.getParameter("filLocalidad")));
			// }
			ResultadoVO resultado = null;
			if (filtro != null && filtro.getFilVigencia() != 0) {
				filtro.setFilNivel(Integer.parseInt(usuVO.getNivel()));
				filtro.setFilRutaBase(context.getRealPath("/"));
				filtro.setFilUsuario(usuVO.getUsuarioId());
				ConsultaIO consultaIO = new ConsultaIO(consultaDAO);
				if (filtro.getFilNivel() < 4) {// es admnistrador
					resultado = consultaIO.buscarPOASED(filtro);
				} else {
					resultado = consultaIO.buscarPOA(filtro);
				}
				if (resultado.isGenerado()) {
					response.setContentType("application/zip");
					StringBuffer contentDisposition = new StringBuffer();
					contentDisposition.append("attachment;");
					contentDisposition.append("filename=\"");
					contentDisposition.append(resultado.getArchivo());
					contentDisposition.append("\"");
					response.setHeader("Content-Disposition",
							contentDisposition.toString());
					f = new File(resultado.getRutaDisco());
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
				} else {
					request.setAttribute(ParamsVO.SMS,
							"No hay datos para generar el reporte");
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
				if (f != null && f.exists()) {
					// System.out
					// .println("++++++++++++++++++++++ BORRO ARCHVIO EXISTENTE "
					// + f.getPath());
					f.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
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
	public void consultaBuscarSeguimiento(HttpServletRequest request,
			HttpServletResponse response, Login usuVO, FiltroConsultaVO filtro)
			throws ServletException {
		response.setContentType("application/zip");
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		File f = null;
		int BUFFER_SIZE = 100000;
		int count;
		try {
			// if(filtro==null){
			filtro = new FiltroConsultaVO();
			filtro.setFilVigencia(Integer.parseInt(request
					.getParameter("filVigencia")));
			filtro.setFilInstitucion(Long.parseLong(request
					.getParameter("filInstitucion")));
			filtro.setFilLocalidad(Integer.parseInt(request
					.getParameter("filLocalidad")));
			// }
			ResultadoVO resultado = null;
			if (filtro != null && filtro.getFilVigencia() != 0) {
				filtro.setFilNivel(Integer.parseInt(usuVO.getNivel()));
				// request.setAttribute("listaConsulta",
				// consultaDAO.getListaPOA(filtro));
				// Logger.print(usuVO.getUsuarioId(),"POA: Buscar actividad con recursos. "+filtro.getFilInstitucion()+". "+filtro.getFilVigencia(),2,1,this.toString());
				filtro.setFilRutaBase(context.getRealPath("/"));
				filtro.setFilUsuario(usuVO.getUsuarioId());
				ConsultaIO consultaIO = new ConsultaIO(consultaDAO);
				if (filtro.getFilNivel() < 4) {// es admnistrador
					resultado = consultaIO.buscarPOASeguimientoSED(filtro);
				} else {
					resultado = consultaIO.buscarPOASeguimiento(filtro);
				}
				if (resultado.isGenerado()) {
					StringBuffer contentDisposition = new StringBuffer();
					contentDisposition.append("attachment;");
					contentDisposition.append("filename=\"");
					contentDisposition.append(resultado.getArchivo());
					contentDisposition.append("\"");
					response.setHeader("Content-Disposition",
							contentDisposition.toString());
					f = new File(resultado.getRutaDisco());
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
				if (f != null && f.exists()) {
					f.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
