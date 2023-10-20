/**
 * 
 */
package siges.gestionAdministrativa.repResultadosAca.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.boletines.dao.ReporteLogrosDAO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.repResultadosAca.dao.RepResultadosAcaDAO;
import siges.gestionAdministrativa.repResultadosAca.vo.FiltroRendimientoVigenciaAnteriorVO;
import siges.gestionAdministrativa.repResultadosAca.vo.FiltroRepResultadosVO;
import siges.gestionAdministrativa.repResultadosAca.vo.ParamsVO;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String FICHA_FILTRO_REPORTES;
	public String FICHA_REPORTES;
	public String FICHA_REPORTE_COLEGIO;
	public String FICHA_COMPRENDIMIENTOACADEMICO;
	private RepResultadosAcaDAO repResultadosDAO;
	private Login usuVO;
	private java.sql.Timestamp f2;
	private String mensaje;

	/**
	 * 
	 /*
	 * 
	 * @function:
	 * @param config
	 * @throws ServletException
	 *             (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		repResultadosDAO = new RepResultadosAcaDAO(new Cursor());
		FICHA_FILTRO_REPORTES = config.getInitParameter("FICHA_FILTRO_REPORTES");
		FICHA_REPORTE_COLEGIO = config.getInitParameter("FICHA_FILTRO_REPORTES_COLEGIO");
		FICHA_COMPRENDIMIENTOACADEMICO=config.getInitParameter("FICHA_FILTRO_COMPRENDIMIENTOACADEMICO");
		FICHA_REPORTES = config.getInitParameter("FICHA_REPORTES");
	}

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de redireccinn
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
		String dispatcher[] = new String[2];
		String FICHA = null;
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		FiltroRepResultadosVO filtroVO = (FiltroRepResultadosVO) session
				.getAttribute("filtroRepResultadosVO");
		FiltroRendimientoVigenciaAnteriorVO filtroRendimientoVO = (FiltroRendimientoVigenciaAnteriorVO) session
				.getAttribute("filtroRendimientoVigenciaAnteriorVO");
										int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_REPORTES);

		switch (TIPO) {
		case ParamsVO.FICHA_REPORTES:
			FICHA = FICHA_FILTRO_REPORTES;
			if(usuVO.getInst() != null && usuVO.getInst().length() > 0){ //Si es un colegio
				FICHA = FICHA_REPORTE_COLEGIO;
			}
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				request.removeAttribute("filtroRepResultadosVO");
				break;
			case ParamsVO.CMD_GUARDAR:
				filtroVO.setTodas(true);
				FICHA = guardarSolicitud(request, session, usuVO, filtroVO);
				request.setAttribute("mensaje", mensaje);
				break;
			}
			cargarFiltroReportes(request, session, usuVO, filtroVO);
			break;
		case ParamsVO.FICHA_COMPRENDIMIENTOACADEMICO:
			FICHA = FICHA_COMPRENDIMIENTOACADEMICO;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				request.removeAttribute("filtroRepResultadosVO");
				break;
			case ParamsVO.CMD_GUARDAR:
				try {
					filtroRendimientoVO.setConsec(repResultadosDAO.obtenersecuenciaParaReporteCompVigAnteriror());
					FICHA = guardarSolicitudRendimientoAnterior(request, session, usuVO, filtroRendimientoVO);
					generardatosReporte(request, session, usuVO, filtroRendimientoVO);
					request.setAttribute("mensaje", mensaje);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			cargarFiltroReporteVigenciaAnterior(request, session, usuVO, filtroRendimientoVO);
			break;
		case ParamsVO.FICHA_REPORTES_COLEGIO:
			FICHA = FICHA_FILTRO_REPORTES;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				request.removeAttribute("filtroRepResultadosVO");
				break;
			case ParamsVO.CMD_GUARDAR:
				filtroVO.setTodas(false);
				filtroVO.setInst(Long.parseLong(usuVO.getInstId()));
				FICHA = guardarSolicitud(request, session, usuVO, filtroVO);
				request.setAttribute("mensaje", mensaje);
				break;
			}
			cargarFiltroReportes(request, session, usuVO, filtroVO);
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarFiltroReportes(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroRepResultadosVO filtroVO)
			throws ServletException {
		try {
			getPeriodos(request, usuVO);
			int nivelUsuario = Integer.parseInt(usuVO.getNivel());
			if (filtroVO == null) {
				filtroVO = new FiltroRepResultadosVO();
				if (nivelUsuario >= 2) {
					if (usuVO.getLocId() != null
							&& usuVO.getLocId().length() > 0)
						filtroVO.setLoc(Long.parseLong(usuVO.getLocId()));
					filtroVO.setHab_loc(0);
					if (nivelUsuario >= 4) {
						if (usuVO.getLocId() != null
								&& usuVO.getLocId().length() > 0) {
							filtroVO.setInst(Long.parseLong(usuVO.getInstId()));
							filtroVO.setNivelEval((int) usuVO.getLogNivelEval());
							filtroVO.setNumPer(usuVO.getLogNumPer());
							filtroVO.setNomPerFinal(usuVO.getLogNomPerDef());
							filtroVO.setHab_inst(0);
						}

					}
				}
			}

			request.setAttribute("listaLocalidad",
					repResultadosDAO.getLocalidades());
			if (filtroVO.getLoc() >= 0) {
				filtroVO.setTipoCol(1);
				request.setAttribute("listaColegio", repResultadosDAO
						.getColegios(filtroVO.getLoc(), filtroVO.getTipoCol()));
				if (filtroVO.getInst() >= 0) {
					long vigenciaActual = repResultadosDAO
							.getVigenciaInst(filtroVO.getInst());
					filtroVO = repResultadosDAO.paramsInst(filtroVO);
					request.setAttribute(
							"listaPeriodo",
							getListaPeriodo(filtroVO.getNumPer(),
									filtroVO.getNomPerFinal()));
				}
			}
			request.getSession()
					.setAttribute("filtroRepResultadosVO", filtroVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void getPeriodos(HttpServletRequest request, Login usuVO)
			throws ServletException {
		request.getSession().setAttribute("listaPeriodo", 
				getListaPeriodo(6,"Final"));
		request.getSession().setAttribute("ajaxParam",
				String.valueOf(ParamsVO.CMD_AJAX_PER));
	}

	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}
	
	/**
	 * Se generan datos para reporte de com vig anterior
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroVO
	 */
	public void generardatosReporte(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroRendimientoVigenciaAnteriorVO filtroVO){
				
		
		
		long vigenciaActual;
		try {
			repResultadosDAO.cargarDatosReporte(request, session, usuVO, filtroVO);
			
			vigenciaActual = repResultadosDAO
					.getVigenciaInst(filtroVO.getInst());
			FiltroRendimientoVigenciaAnteriorVO tmp=filtroVO;
			tmp.setFilVigencia(vigenciaActual);
			
			repResultadosDAO.cargarDatosReporte(request, session, usuVO, tmp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * MEtodo que permite guardar reporte de comparacinn de vigencia anterior por vigencia y periodo
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroVO
	 * @return
	 * @throws Exception 
	 */
	private String guardarSolicitudRendimientoAnterior(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroRendimientoVigenciaAnteriorVO filtroVO)
			throws Exception {
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());

		int tipoRep = filtroVO.getTipoReporte();
		String zip = "";
		String pdf = "";
		String xls = "";
		String nombreReporte = "";
		String vig = (filtroVO.getFilVigencia() >= 0 ? "_Loc_" + filtroVO.getFilVigencia() : "");
		String per = (filtroVO.getPeriodo() > 0 ? "_Inst_" + filtroVO.getPeriodo()
				: "");
		String nomRep = vig + per;
		/*
		 * if(tipoRep==ParamsVO.TIPO_REPORTE_1){
		 * nombreReporte="Listado_Estudiantes";
		 * nombreReporte=nombreReporte+nomRep; }else
		 * if(tipoRep==ParamsVO.TIPO_REPORTE_2){
		 * nombreReporte="Cuadro_resumen_areas";
		 * nombreReporte=nombreReporte+nomRep; }else
		 * if(tipoRep==ParamsVO.TIPO_REPORTE_3){
		 * nombreReporte="Cuadro_resumen_grados";
		 * nombreReporte=nombreReporte+nomRep; }
		 */
		nombreReporte = "Comparativo_vigencia_anteriror";
		nombreReporte = nombreReporte + nomRep;
		filtroVO.setFecha(f2.toString().replace(' ', '_').replace(':', '-')
				.replace('.', '-'));
		zip = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".zip";
		pdf = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".pdf";
		xls = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".xls";
		filtroVO.setNombre_zip(zip);
		filtroVO.setNombre_pdf(pdf);
		filtroVO.setNombre_xls(xls);
		filtroVO.setUsuario(usuVO.getUsuarioId());

		// System.out.println("REP_RESULTAODS:: GUARDAR SOLICITUD ");
		try {
			if (repResultadosDAO.insertarSolicitudCompVigenciaAnterior(filtroVO, usuVO)) {
				System.out.println("REP_RESULTAODS:se inserto solicitud");
				repResultadosDAO.insertarReporteCompVigenciaAnterior(filtroVO);
				// System.out.println("REP_RESULTAODS:se inserto Reporte");
				mensaje = "Se inserto la solicitud correctamente, revisar en el servicio reportes generados.";
				ReporteVO reporteVO = new ReporteVO();
				reporteVO.setRepTipo(ReporteVO._REP_RESULT_COMPARATIVO_VIGENCIA_ANTERIOR);
				reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
				request.getSession().setAttribute("reporteVO", reporteVO);
				return FICHA_REPORTES;
			} else {
				// System.out.println("REP_RESULTAODS:NOOOO se inserto solicitud");
				mensaje = "No se pudo ingresar la solicitud, intentelo mas tarde.";
				return FICHA_FILTRO_REPORTES;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FICHA_FILTRO_REPORTES;
	}

	
	/**
	 * @param request
	 * @throws Exception
	 */
	private String guardarSolicitud(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroRepResultadosVO filtroVO)
			throws ServletException {
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());

		int tipoRep = filtroVO.getTipoReporte();
		String zip = "";
		String pdf = "";
		String xls = "";
		String nombreReporte = "";
		String loc = (filtroVO.getLoc() >= 0 ? "_Loc_" + filtroVO.getLoc() : "");
		String inst = (filtroVO.getInst() > 0 ? "_Inst_" + filtroVO.getInst()
				: "");
		String nomRep = loc + inst;
		nombreReporte = "Resultados_academicos";
		nombreReporte = nombreReporte + nomRep;
		
		String ext = filtroVO.isTodas() ? ".csv" : ".zip";
		
		filtroVO.setFecha(f2.toString().replace(' ', '_').replace(':', '-')
				.replace('.', '-'));
		zip = nombreReporte
			+ "_fecha_"
			+ f2.toString().replace(' ', '_').replace(':', '-')
					.replace('.', '-') + ext;	
			
		pdf = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".pdf";
		xls = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".xls";
		
			
		filtroVO.setNombre_zip(zip);
		filtroVO.setNombre_pdf(pdf);
		filtroVO.setNombre_xls(xls);
		filtroVO.setUsuario(usuVO.getUsuarioId());
		try {
			/**
			 * Si solicitaron reporte para toda la localidad
			 * Ingrese una solicitud por cada uno
			 */
			if(filtroVO.isTodas()){
				String r = repResultadosDAO.getColegiosFromLocalidad(filtroVO.getLoc(), 2013);
				String[] resultadosColegios = r.split(";");
				for(int i = 0; i < resultadosColegios.length && !resultadosColegios[i].equals(""); i++){ 
					filtroVO.setInst(Long.parseLong(resultadosColegios[i]));
					filtroVO.setOrden(resultadosColegios.length);
					if (repResultadosDAO.insertarSolicitud(filtroVO, usuVO)) {
						System.out.println("REP_RESULTAODS:se inserto solicitud");
						if(i == 0)
							repResultadosDAO.insertarReporte(filtroVO);
						mensaje = "Se inserto la solicitud correctamente, revisar en el servicio reportes generados.";
						ReporteVO reporteVO = new ReporteVO();
						reporteVO.setRepTipo(ReporteVO._REP_RESULT_ACADEMICOS);
						reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
						request.getSession().setAttribute("reporteVO", reporteVO);
					} else {
						mensaje = "No se pudo ingresar la solicitud, intentelo mas tarde.";
						return FICHA_FILTRO_REPORTES;
					}
				}	
			} else {
				filtroVO.setOrden(1);
				if (repResultadosDAO.insertarSolicitud(filtroVO, usuVO)) {
					System.out.println("REP_RESULTAODS:se inserto solicitud");
					repResultadosDAO.insertarReporte(filtroVO);
					mensaje = "Se inserto la solicitud correctamente, revisar en el servicio reportes generados.";
					ReporteVO reporteVO = new ReporteVO();
					reporteVO.setRepTipo(ReporteVO._REP_RESULT_ACADEMICOS);
					reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
					request.getSession().setAttribute("reporteVO", reporteVO);
				} else {
					mensaje = "No se pudo ingresar la solicitud, intentelo mas tarde.";
					return FICHA_FILTRO_REPORTES;
				}
			}
			return FICHA_REPORTES;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FICHA_FILTRO_REPORTES;
	}
	
	private void cargarFiltroReporteVigenciaAnterior(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroRendimientoVigenciaAnteriorVO filtroVO)
			throws ServletException {

		try {

			int nivelUsuario = Integer.parseInt(usuVO.getNivel());

			if (filtroVO == null) {
				filtroVO = new FiltroRendimientoVigenciaAnteriorVO();
				if (nivelUsuario >= 2) {
					if (usuVO.getLocId() != null
							&& usuVO.getLocId().length() > 0)
						filtroVO.setLoc(Long.parseLong(usuVO.getLocId()));
					filtroVO.setHab_loc(0);
					if (nivelUsuario >= 4) {
						if (usuVO.getLocId() != null
								&& usuVO.getLocId().length() > 0) {
							filtroVO.setInst(Long.parseLong(usuVO.getInstId()));
							filtroVO.setNivelEval((int) usuVO.getLogNivelEval());
							filtroVO.setNumPer(usuVO.getLogNumPer());
							filtroVO.setNomPerFinal(usuVO.getLogNomPerDef());
							filtroVO.setHab_inst(0);
						}

					}
				}
			}
			
			
			long vigenciaActual = repResultadosDAO
					.getVigenciaInst(filtroVO.getInst());
			
			List vigencia = new ArrayList();
			for (int vig = 2005; vig <= vigenciaActual; vig++) {
				ItemVO itemVig = new ItemVO();
				itemVig.setCodigo(vig);
				itemVig.setNombre(String.valueOf(vig));
				vigencia.add(itemVig);
			}

			request.setAttribute("filtroVigencia", vigencia);

			request.setAttribute("listaLocalidad",
					repResultadosDAO.getLocalidades());

			if (filtroVO.getLoc() >= 0) {
				// if(filtroVO.getTipoCol()<0)
				filtroVO.setTipoCol(1);
				request.setAttribute("listaColegio", repResultadosDAO
						.getColegios(filtroVO.getLoc(), filtroVO.getTipoCol()));
				if (filtroVO.getInst() >= 0) {
					
					filtroVO = repResultadosDAO.paramsInstconRendVigenciaAnterior(filtroVO);
					request.setAttribute(
							"listaPeriodo",
							getListaPeriodo(filtroVO.getNumPer(),
									filtroVO.getNomPerFinal()));

				}
			}

			request.getSession()
					.setAttribute("filtroRendimientoVigenciaAnteriorVO", filtroVO);

	
	} catch (Exception e) {
		e.printStackTrace();
		throw new ServletException("Errror interno: " + e.getMessage());
	}
	}
}
