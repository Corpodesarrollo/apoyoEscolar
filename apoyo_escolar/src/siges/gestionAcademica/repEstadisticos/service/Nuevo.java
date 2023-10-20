/**
 * 
 */
package siges.gestionAcademica.repEstadisticos.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.gestionAcademica.repEstadisticos.dao.RepEstadisticosDAO;
import siges.gestionAcademica.repEstadisticos.io.RepEstadisticosIO;
import siges.gestionAcademica.repEstadisticos.vo.EstudianteVO;
import siges.gestionAcademica.repEstadisticos.vo.FiltroRepEstadisticoVO;
import siges.gestionAcademica.repEstadisticos.vo.ParamsVO;
import siges.gestionAcademica.repEstadisticos.vo.ReporteEstadisticoVO;
import siges.login.beans.Login;
import siges.util.Acceso;
import siges.util.beans.ReporteVO;

;
/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	private String FICHA_REP_ASISTENCIA;
	private String FICHA_REP_ASIGNATURAS;
	private String FICHA_REP_AREAS;
	private String FICHA_REP_LOGROS;
	private String FICHA_REP_DESCRIPTOR;
	private String FICHA_REP_LOGROS_GRADO;
	private String FICHA_REPORTES;

	private ResourceBundle rb;
	private RepEstadisticosDAO repEstadisticosDAO;
	private Login usuVO;
	private java.sql.Timestamp f2;

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
		repEstadisticosDAO = new RepEstadisticosDAO(new Cursor());
		FICHA_REP_ASISTENCIA = config.getInitParameter("FICHA_REP_ASISTENCIA");
		FICHA_REP_ASIGNATURAS = config
				.getInitParameter("FICHA_REP_ASIGNATURAS");
		FICHA_REP_AREAS = config.getInitParameter("FICHA_REP_AREAS");
		FICHA_REP_LOGROS = config.getInitParameter("FICHA_REP_LOGROS");
		FICHA_REP_DESCRIPTOR = config.getInitParameter("FICHA_REP_DESCRIPTOR");
		FICHA_REP_LOGROS_GRADO = config
				.getInitParameter("FICHA_REP_LOGROS_GRADO");
		FICHA_REPORTES = "/Reportes.do";
		rb = ResourceBundle
				.getBundle("siges.gestionAcademica.repEstadisticos.bundle.repEstadisticos");
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

		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_REP_ASISTENCIA);
		request.getSession().setAttribute(
				"logroanddesc",
				Acceso.getlogrosdesc(usuVO.getInstId(),
						Long.toString(usuVO.getVigencia_inst())));
		// System.out.println(this.getClass() + " - "+ TIPO +"   CMD "+ CMD);
		switch (TIPO) {
		case 1:
		case ParamsVO.FICHA_REP_ASISTENCIA:
			FICHA = FICHA_REP_ASISTENCIA;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				limpiarListados(session);
				cargarFiltroReportes(request, usuVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				FICHA = generarReporteAsistencia(request, session, usuVO);
				break;
			}
			cargarFiltroReportes(request, usuVO);
			break;
		case ParamsVO.FICHA_REP_ASIGNATURAS:
			FICHA = FICHA_REP_ASIGNATURAS;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				limpiarListados(session);
				cargarFiltroReportes(request, usuVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				FICHA = colocarReporte(request, session, usuVO, TIPO);
				break;
			}
			cargarFiltroReportes(request, usuVO);
			break;
		case ParamsVO.FICHA_REP_AREAS:
			FICHA = FICHA_REP_AREAS;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				limpiarListados(session);
				cargarFiltroReportes(request, usuVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				FICHA = colocarReporte(request, session, usuVO, TIPO);
				break;
			}
			cargarFiltroReportes(request, usuVO);
			break;
		case ParamsVO.FICHA_REP_LOGROS:
			FICHA = FICHA_REP_LOGROS;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				limpiarListados(session);
				cargarFiltroReportes(request, usuVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				FICHA = colocarReporte(request, session, usuVO, TIPO);
				break;
			}
			cargarFiltroReportes(request, usuVO);
			break;
		case ParamsVO.FICHA_REP_DESCRIPTOR:
			FICHA = FICHA_REP_DESCRIPTOR;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				limpiarListados(session);
				cargarFiltroReportes(request, usuVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				FICHA = colocarReporte(request, session, usuVO, TIPO);
				break;
			}
			cargarFiltroReportes(request, usuVO);
			break;
		case ParamsVO.FICHA_REP_LOGROS_GRADO:
			FICHA = FICHA_REP_LOGROS_GRADO;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				limpiarListados(session);
				cargarFiltroReportes(request, usuVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				FICHA = colocarReporte(request, session, usuVO, TIPO);
				break;
			}
			cargarFiltroReportes(request, usuVO);
			break;
		default:
			FICHA = FICHA_REP_ASISTENCIA;
			limpiarListados(session);
			cargarFiltroReportes(request, usuVO);

		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	// ASISTENCIA

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @return
	 * @throws ServletException
	 */
	private String generarReporteAsistencia(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		// System.out.println("repEstadisticos: guardarSolicituAsisgencia");

		f2 = new java.sql.Timestamp(new java.util.Date().getTime());

		FiltroRepEstadisticoVO filtroVO = (FiltroRepEstadisticoVO) session
				.getAttribute("filtroRepEstadisticoVO");
		ReporteEstadisticoVO reporteVO = new ReporteEstadisticoVO();
		String rutabase = context.getRealPath("/");

		try {
			if (filtroVO != null) {

				filtroVO.setUsuario(usuVO.getUsuarioId());
				filtroVO.setFilinstNomb(usuVO.getInst());
				/** Colocar nombres a los archivos */
				reporteVO.setNombre_xls(getNombRepTipo(
						ParamsVO.NOMBRE_REP_ASISTENCIA, filtroVO,
						ParamsVO.ARCHIVO_TIPO_XLS));
				reporteVO.setNombre_zip(getNombRepTipo(
						ParamsVO.NOMBRE_REP_ASISTENCIA, filtroVO,
						ParamsVO.ARCHIVO_TIPO_ZIP));
				reporteVO.setNombre_pdf(getNombRepTipo(
						ParamsVO.NOMBRE_REP_ASISTENCIA, filtroVO,
						ParamsVO.ARCHIVO_TIPO_PDF));
				reporteVO.setNombre(reporteVO.getNombre_zip());

				reporteVO.setFecha(f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-')
						+ "");
				reporteVO.setModulo(ParamsVO.MODULO_RepEstadisticos);
				reporteVO.setUsuario(usuVO.getUsuarioId());
				reporteVO.setRecurso(Ruta.getRelativo(
						rutabase,
						rb.getString("estadisticos.path")
								+ filtroVO.getUsuario()).concat(
						reporteVO.getNombre_zip()));
				reporteVO.setTipo(ParamsVO.ARCHIVO_TIPO_ZIP);

				/** Insertar registro del reporte en estado EN COLA */
				reporteVO.setEstado(ParamsVO.ESTADO_REPORTE_COLA);
				repEstadisticosDAO.ponerReporte(reporteVO);

				/** Llamar procedimiento asistencia EJECUTANDO */
				reporteVO.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
				// repEstadisticosDAO.proceso_asistencia(filtroVO,usuVO);
				repEstadisticosDAO.callHojaVida(filtroVO, reporteVO);

				/** Validar si existen datos para el reporte */
				if (!repEstadisticosDAO.isListaAsistencia(filtroVO, reporteVO,
						usuVO)) {
					reporteVO.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
					reporteVO
							.setMensaje("No hay datos para generar el reporte");
					repEstadisticosDAO.ponerReporteMensaje(reporteVO);
					request.setAttribute(ParamsVO.SMS,
							"No hay datos para generar el reporte");

					/** Limpiar tablas */
					repEstadisticosDAO.limpiarTablasAsistencia(reporteVO);

					return FICHA_REP_ASISTENCIA;
				}

				/** Generar reporte, jasper */
				RepEstadisticosIO repEstadisticosIO = new RepEstadisticosIO(
						repEstadisticosDAO);
				repEstadisticosIO.generarRepAsistencia(filtroVO, reporteVO,
						rutabase);

				/** Se Genero el reporte */
				reporteVO.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
				siges.util.beans.ReporteVO reporteVO2 = new siges.util.beans.ReporteVO();

				/** Cargar bean para mostra en el listado de reportes */
				reporteVO2.setRepTipo(ReporteVO._REP_ESTADISTICOS);
				reporteVO2.setRepOrden(ReporteVO.ORDEN_DESC);

				request.getSession().setAttribute("reporteVO", reporteVO2);
				reporteVO.setMensaje("Reporte Generado satisfactoriamente.");
				repEstadisticosDAO.ponerReporteMensaje(reporteVO);

				/** Limpiar tablas */
				repEstadisticosDAO.limpiarTablasAsistencia(reporteVO);
			} else {
				System.out.println("filtro es null");
				return FICHA_REP_ASISTENCIA;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
			e.printStackTrace();

			try {
				/** Error al generar */
				reporteVO.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporteVO.setMensaje(e.getMessage());
				repEstadisticosDAO.ponerReporteMensaje(reporteVO);
				/** Limpiar tablas */
				repEstadisticosDAO.limpiarTablasAsistencia(reporteVO);

			} catch (Exception ee) {
				System.out.println(ee.getMessage());
				request.setAttribute(ParamsVO.SMS, ee.getMessage());
				ee.printStackTrace();
			}
			return FICHA_REP_ASISTENCIA;
		}

		ReporteVO reporteVO2 = new ReporteVO();
		reporteVO2.setRepTipo(ReporteVO._REP_ESTADISTICOS);
		reporteVO2.setRepOrden(ReporteVO.ORDEN_DESC);
		request.getSession().setAttribute("reporteVO", reporteVO2);
		return FICHA_REPORTES;
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @return
	 * @throws ServletException
	 */
	private String colocarReporte(HttpServletRequest request,
			HttpSession session, Login usuVO, int TIPO_REPORTE)
			throws ServletException {
		// System.out.println("repEstadisticos: colocarReporte");
		String FICHA_URL = "";
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		FiltroRepEstadisticoVO filtroVO = (FiltroRepEstadisticoVO) session
				.getAttribute("filtroRepEstadisticoVO");
		ReporteEstadisticoVO reporteVO = new ReporteEstadisticoVO();
		String rutabase = context.getRealPath("/");

		try {
			if (filtroVO != null) {

				filtroVO.setUsuario(usuVO.getUsuarioId());
				filtroVO.setFilinstNomb(usuVO.getInst());
				filtroVO.setFilperd_num((int) usuVO.getLogNumPer());
				filtroVO.setFilperdNomb_final(usuVO.getLogNomPerDef());
				reporteVO.setFecha(f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-')
						+ "");
				filtroVO.setFecha(reporteVO.getFecha());
				filtroVO.setFilvigencia(repEstadisticosDAO.getVigenciaInst(Long
						.parseLong(usuVO.getInstId())));

				/**
				 * Si en el filtro se seleccion un solo estudiante se debe hacer
				 * esta validaciones
				 */

				if (filtroVO.getFilest() > 0) {
					EstudianteVO e = repEstadisticosDAO
							.getNombreEstudiante(filtroVO.getFilest());
					filtroVO.setFilnumdoc(e.getEstnumdoc());
					filtroVO.setFiltipodoc((int) e.getEsttipodoc());
				} else {
					if (filtroVO.getFilnumdoc() != null
							&& filtroVO.getFilnumdoc().trim().length() > 0) {
						EstudianteVO estudianteVO = repEstadisticosDAO
								.getNombreEstudiante(filtroVO.getFiltipodoc(),
										filtroVO.getFilnumdoc());
						filtroVO = repEstadisticosDAO.getDatosJerarquia(
								filtroVO, estudianteVO);
						filtroVO.setFilsedeNomb(getItemNombre(filtroVO
								.getFilsede(), repEstadisticosDAO
								.getSede(filtroVO.getFilinst())));
						filtroVO.setFiljorndNomb(getItemNombre(
								filtroVO.getFiljornd(),
								repEstadisticosDAO.getListaJornd(
										filtroVO.getFilinst(),
										filtroVO.getFilsede())));
					}
				}

				/** Colocar nombres a los archivos */
				if (TIPO_REPORTE == ParamsVO.FICHA_REP_ASIGNATURAS) {
					reporteVO.setNombre_xls(getNombRepTipo(
							ParamsVO.NOMBRE_REP_ASIG, filtroVO,
							ParamsVO.ARCHIVO_TIPO_XLS));
					reporteVO.setNombre_zip(getNombRepTipo(
							ParamsVO.NOMBRE_REP_ASIG, filtroVO,
							ParamsVO.ARCHIVO_TIPO_ZIP));
					reporteVO.setNombre_pdf(getNombRepTipo(
							ParamsVO.NOMBRE_REP_ASIG, filtroVO,
							ParamsVO.ARCHIVO_TIPO_PDF));
					reporteVO.setTipoReporte(ParamsVO.TIPOREP_RepEstdcAsig);
					FICHA_URL = FICHA_REP_ASIGNATURAS;
				} else if (TIPO_REPORTE == ParamsVO.FICHA_REP_AREAS) {
					reporteVO.setNombre_xls(getNombRepTipo(
							ParamsVO.NOMBRE_REP_AREA, filtroVO,
							ParamsVO.ARCHIVO_TIPO_XLS));
					reporteVO.setNombre_zip(getNombRepTipo(
							ParamsVO.NOMBRE_REP_AREA, filtroVO,
							ParamsVO.ARCHIVO_TIPO_ZIP));
					reporteVO.setNombre_pdf(getNombRepTipo(
							ParamsVO.NOMBRE_REP_AREA, filtroVO,
							ParamsVO.ARCHIVO_TIPO_PDF));
					FICHA_URL = FICHA_REP_AREAS;
					reporteVO.setTipoReporte(ParamsVO.TIPOREP_RepEstdcArea);
				} else if (TIPO_REPORTE == ParamsVO.FICHA_REP_LOGROS) {
					reporteVO.setNombre_xls(getNombRepTipo(
							ParamsVO.NOMBRE_REP_LOGRO, filtroVO,
							ParamsVO.ARCHIVO_TIPO_XLS));
					reporteVO.setNombre_zip(getNombRepTipo(
							ParamsVO.NOMBRE_REP_LOGRO, filtroVO,
							ParamsVO.ARCHIVO_TIPO_ZIP));
					reporteVO.setNombre_pdf(getNombRepTipo(
							ParamsVO.NOMBRE_REP_LOGRO, filtroVO,
							ParamsVO.ARCHIVO_TIPO_PDF));
					FICHA_URL = FICHA_REP_LOGROS;
					reporteVO.setTipoReporte(ParamsVO.TIPOREP_RepEstdcLogro);
				} else if (TIPO_REPORTE == ParamsVO.FICHA_REP_DESCRIPTOR) {
					reporteVO.setNombre_xls(getNombRepTipo(
							ParamsVO.NOMBRE_REP_DESCPT, filtroVO,
							ParamsVO.ARCHIVO_TIPO_XLS));
					reporteVO.setNombre_zip(getNombRepTipo(
							ParamsVO.NOMBRE_REP_DESCPT, filtroVO,
							ParamsVO.ARCHIVO_TIPO_ZIP));
					reporteVO.setNombre_pdf(getNombRepTipo(
							ParamsVO.NOMBRE_REP_DESCPT, filtroVO,
							ParamsVO.ARCHIVO_TIPO_PDF));
					reporteVO.setTipoReporte(ParamsVO.TIPOREP_RepEstdcDesct);
					System.out.println("REPORTE DE DESCRIPTORES ");
					System.out.println(filtroVO.getFilarea());
					FICHA_URL = FICHA_REP_DESCRIPTOR;
				} else if (TIPO_REPORTE == ParamsVO.FICHA_REP_LOGROS_GRADO) {
					reporteVO.setNombre_xls(getNombRepTipo(
							ParamsVO.NOMBRE_REP_LOGRO_GRAD, filtroVO,
							ParamsVO.ARCHIVO_TIPO_XLS));
					reporteVO.setNombre_zip(getNombRepTipo(
							ParamsVO.NOMBRE_REP_LOGRO_GRAD, filtroVO,
							ParamsVO.ARCHIVO_TIPO_ZIP));
					reporteVO.setNombre_pdf(getNombRepTipo(
							ParamsVO.NOMBRE_REP_LOGRO_GRAD, filtroVO,
							ParamsVO.ARCHIVO_TIPO_PDF));
					reporteVO
							.setTipoReporte(ParamsVO.TIPOREP_RepEstdcLogroGrad);
					FICHA_URL = FICHA_REP_LOGROS_GRADO;
				}

				/** Validar si el periodo esta cerrado */
				if (!repEstadisticosDAO.isPerCerrado(filtroVO)) {
					request.setAttribute(ParamsVO.SMS,
							"No se puede generar el reporte, el periodo ann no estn cerrado.");
					return FICHA_URL;
				}

				reporteVO.setModulo(ParamsVO.MODULO_RepEstadisticos);

				reporteVO.setNombre(reporteVO.getNombre_zip());
				reporteVO.setFecha(f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-')
						+ "");

				reporteVO.setUsuario(usuVO.getUsuarioId());
				reporteVO.setRecurso(Ruta.getRelativo(
						rutabase,
						rb.getString("estadisticos.path")
								+ filtroVO.getUsuario()).concat(
						reporteVO.getNombre_zip()));
				reporteVO.setTipo(ParamsVO.ARCHIVO_TIPO_ZIP);

				/** Insertar registro del reporte en estado EN COLA */
				reporteVO.setEstado(ParamsVO.ESTADO_REPORTE_COLA);
				repEstadisticosDAO.ponerReporte(reporteVO);
				repEstadisticosDAO.insertarDatosBoletin(filtroVO, reporteVO);

				/** Calcular el puesto con el cual entra el reporte */
				reporteVO.setPuesto(repEstadisticosDAO.cola_boletines() + 1);
				reporteVO = repEstadisticosDAO.getReporteConsecutivo(reporteVO);
				repEstadisticosDAO.updatePuestoBoletin(reporteVO);

				/** Cargar bean para mostra en el listado de reportes */
				siges.util.beans.ReporteVO reporteVO2 = new siges.util.beans.ReporteVO();
				reporteVO2
						.setRepTipo(siges.util.beans.ReporteVO._REP_ESTADISTICOS);
				reporteVO2.setRepOrden(siges.util.beans.ReporteVO.ORDEN_DESC);
				request.getSession().setAttribute("reporteVO", reporteVO2);
				reporteVO.setMensaje("Reporte en cola.");
				repEstadisticosDAO.ponerReporteMensaje(reporteVO);

			} else {
				// System.out.println("filtro es null");

				return FICHA_URL;

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
			e.printStackTrace();

			try {

				/** Error al generar */
				reporteVO.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporteVO.setMensaje(e.getMessage());
				repEstadisticosDAO.ponerReporteMensaje(reporteVO);

			} catch (Exception ee) {
				System.out.println(ee.getMessage());
				request.setAttribute(ParamsVO.SMS, ee.getMessage());
				ee.printStackTrace();
			}
			return FICHA_URL;
		}
		ReporteVO reporteVO2 = new ReporteVO();
		reporteVO2.setRepTipo(ReporteVO._REP_ESTADISTICOS);
		reporteVO2.setRepOrden(ReporteVO.ORDEN_DESC);
		request.getSession().setAttribute("reporteVO", reporteVO2);
		return FICHA_REPORTES;
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarFiltroReportes(HttpServletRequest request, Login usuVO)
			throws ServletException {
		// System.out.println(this.getClass()+" :: cargarFiltroReportes");
		try {
			request.setAttribute("listaVigencia", repEstadisticosDAO
					.getListaVigenciaInst(Long.parseLong(usuVO.getInstId())));
			request.setAttribute("listaOrden",
					repEstadisticosDAO.getListarden());
			request.getSession().setAttribute(
					"listaPeriodos",
					getListaPeriodo(usuVO.getLogNumPer(),
							usuVO.getLogNomPerDef()));
			request.getSession().setAttribute(
					"listaPeriodos",
					getListaPeriodo(usuVO.getLogNumPer(),
							usuVO.getLogNomPerDef()));
			request.getSession().setAttribute("listaTiposDoc",
					repEstadisticosDAO.getTiposDoc());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param nombre
	 * @return
	 * @throws Exception
	 */
	private String getNombRepTipo(String nombreReporte,
			FiltroRepEstadisticoVO filtroVO, String tipo) throws Exception {
		try {
			Timestamp f2 = new java.sql.Timestamp(
					new java.util.Date().getTime());
			StringBuffer strgBuff = new StringBuffer();

			/* institucinn */
			strgBuff = new StringBuffer();
			String inst = (filtroVO.getFilinst() > 0 ? strgBuff
					.append("_Inst_").append(filtroVO.getFilinst()).toString()
					: "");

			/* Localidad */
			strgBuff = new StringBuffer();
			String sede = (filtroVO.getFilsede() > 0 ? strgBuff
					.append("_Sede_").append(filtroVO.getFilsede()).toString()
					: "");

			/* jornada */
			strgBuff = new StringBuffer();
			String jornada = (filtroVO.getFiljornd() > 0 ? strgBuff
					.append("_Jor_").append(filtroVO.getFiljornd()).toString()
					: "");

			/* Grado */
			strgBuff = new StringBuffer();
			String grado = (filtroVO.getFilgrado() >= 0 ? strgBuff
					.append("_Gra_").append(filtroVO.getFilgrado()).toString()
					: "");

			strgBuff = new StringBuffer();
			nombreReporte = strgBuff
					.append(nombreReporte)
					.append(inst)
					.append(sede)
					.append(jornada)
					.append(grado)
					.append("_fec_")
					.append(f2.toString().toString().replace(' ', '_')
							.replace(':', '-').replace('.', '-')).append(".")
					.append(tipo).toString();

			return nombreReporte;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * @param codigo
	 * @param listaItem
	 * @return
	 */
	private String getItemNombre(int codigo, List listaItem) {
		for (int i = 0; i < listaItem.size(); i++) {
			ItemVO itemVO = (ItemVO) listaItem.get(i);
			if (itemVO.getCodigo() == codigo) {
				return itemVO.getNombre();
			}

		}
		return " ";
	}

	private void limpiarListados(HttpSession session) {
		session.removeAttribute("filtroRepEstadisticoVO");
		session.removeAttribute("listaSede");
		session.removeAttribute("listaJornada");
		session.removeAttribute("listaMetodo");
		session.removeAttribute("listaGrado");
		session.removeAttribute("listaGrupo");
	}
}
