package siges.reporte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.reporte.beans.FiltroBeanAsistencia;
import siges.reporte.beans.FiltroBeanEvaluacionArea;
import siges.reporte.beans.FiltroBeanEvaluacionAreaGrupo;
import siges.reporte.beans.FiltroBeanEvaluacionAsignatura;
import siges.reporte.beans.FiltroBeanEvaluacionAsignaturaGrupo;
import siges.reporte.beans.FiltroBeanEvaluacionLogro;
import siges.reporte.beans.FiltroBeanEvaluacionLogroGrupo;
import siges.reporte.beans.FiltroBeanEvaluacionTipoDescriptor;

;

/**
 * Nombre: ControllerFiltroSave<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 04/10/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerFiltroSave extends HttpServlet {

	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Login login;
	private HttpSession session;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Integer gradocod = new Integer(java.sql.Types.INTEGER);
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	private ResourceBundle r, rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanAsistencia filtroAsistencia;
	private FiltroBeanEvaluacionLogro filtroEvaluacionLogro;
	private FiltroBeanEvaluacionAsignatura filtroEvaluacionAsignatura;
	private FiltroBeanEvaluacionArea filtroEvaluacionArea;
	private FiltroBeanEvaluacionLogroGrupo filtroLogrosGrupo;
	private FiltroBeanEvaluacionAsignaturaGrupo filtroAsignaturasGrupo;
	private FiltroBeanEvaluacionAreaGrupo filtroAreasGrupo;
	private FiltroBeanEvaluacionTipoDescriptor filtroTipoDescriptor;
	private Util util;
	private final String moduloAsistencia = "14";
	private final String moduloLogro = "15";
	private final String moduloAsignatura = "16";
	private final String moduloArea = "17";
	private final String moduloLogroGrupo = "18";
	private final String moduloAsignaturaGrupo = "19";
	private final String moduloAreaGrupo = "35";
	private final String moduloTipoDescriptor = "36";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s;// Reportes.do
	private String s1;// ControllerFiltroEdit
	private String archivo, archivozip;
	private String archivopdf;
	private String buscar, buscarjasper;
	private String insertar;
	private String ant;
	private String er;
	private String sig;
	private String sig2;
	private String sig3;
	private String sig4;
	private String sig5;
	private String sig6;
	private String sig7;
	private String home;
	private byte[] bytes, bytes2, bytes3, bytes4, bytes5, bytes6;
	private Map parameters;
	private File reportFile;
	private File reportFile2;
	private File reportFile3;
	private File reportFile4;
	private File reportFile5;
	private File reportFile6;
	private File reportFile7;
	private File reportFile8;
	private File reportFile9;
	private String path;
	private String path1;
	private String requerido;
	private int requeridos;
	private String alert;
	private String contextoTotal;
	private String vigencia;
	private Dao dao;
	private String context;
	private String codigoestudiante;
	private String respuesta;
	private PreparedStatement pst;
	private CallableStatement cstmt;
	private static final String tipoasignatura = "1";
	private static final String tipoarea = "2";

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/

	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		Connection con = null;
		s = null;
		s1 = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int count;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle
				.getBundle("preparedstatements_reportesEstadisticas");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		String existeboletin, boton;
		String nom;
		s = "/Reportes.do";
		s1 = "/reporte/ControllerFiltroEdit.do";
		sig = "/reporte/filtroAsistencia.jsp";
		sig2 = "/reporte/filtroEvaluacionLogro.jsp";
		sig3 = "/reporte/filtroEvaluacionAsignatura.jsp";
		sig4 = "/reporte/filtroEvaluacionArea.jsp";
		sig5 = "/reporte/filtroEvaluacionLogroGrupo.jsp";
		sig6 = "/reporte/filtroEvaluacionAsignaturaGrupo.jsp";
		sig7 = "/reporte/filtroEvaluacionAreaGrupo.jsp";
		ant = "/index.jsp";
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		buscar = buscarjasper = insertar = existeboletin = null;
		err = false;
		respuesta = null;

		if (request.getParameter("reporte") == null) {
			setMensaje("**Error al mandar generar el tipo de reporte estadistico solicitado**");
			request.setAttribute("mensaje", mensaje);
			ir(2, er, request, response);
			return er;
		}
		// System.out.println("REPORTE SOLICITADO: "
		// + request.getParameter("reporte"));
		alert = "El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			ir(2, er, request, response);
			return null;
		}

		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);
			vigencia = dao.getVigencia();

			if (vigencia == null) {
				System.out.println("*W* LA VIGENCIA ES NULA *W* ");
				return er;
			}
			// System.out.println("VIGENCIA_ DE DAO: " + vigencia);
			requeridos = Integer.parseInt(request.getParameter("reporte"));

			ServletContext context = (ServletContext) request.getSession()
					.getServletContext();
			contextoTotal = context.getRealPath("/");
			path = context.getRealPath(rb3.getString("ruta_jaspers"));
			path1 = context.getRealPath(rb3.getString("ruta_img"));
			reportFile = new File(path + File.separator
					+ rb3.getString("jasper_asistencia"));
			reportFile2 = new File(path + File.separator
					+ rb3.getString("jasper_evaluacion_logro"));
			reportFile3 = new File(path + File.separator
					+ rb3.getString("jasper_asignatura"));
			reportFile4 = new File(path + File.separator
					+ rb3.getString("jasper_area"));
			reportFile5 = new File(path + File.separator
					+ rb3.getString("jasper_evaluacion_logro_grupo"));
			reportFile6 = new File(path + File.separator
					+ rb3.getString("jasper_evaluacion_asignatura_grupo"));
			reportFile7 = new File(path + File.separator
					+ rb3.getString("jasper_evaluacion_area_grupo"));

			switch (requeridos) {
			case 1:
				if (!asistencias(request, vigencia, contextoTotal))
					return s1;
				break;
			case 2:
				if (!logros(request, vigencia, contextoTotal))
					return s1;
				break;
			case 3:
				if (!asignaturas(request, vigencia, contextoTotal))
					return s1;
				break;
			case 4:
				if (!areas(request, vigencia, contextoTotal))
					return s1;
				break;
			case 5:
				if (!logrosGrupo(request, vigencia, contextoTotal))
					return s1;
				break;
			case 6:
				if (!asignaturasGrupo(request, vigencia, contextoTotal))
					return s1;
				break;
			case 7:
				if (!areasGrupo(request, vigencia, contextoTotal))
					return s1;
				break;
			case 8:
				if (!tipoDescriptor(request, vigencia, contextoTotal))
					return s1;
				break;
			}
			if (err) {
				request.setAttribute("mensaje", mensaje);
				return er;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cursor != null)
					cursor.cerrar();
				if (util != null)
					util.cerrar();
			} catch (Exception e) {
			}
		}
		return s;
	}

	public boolean asistencias(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, nom = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR ASISTENCIAS!**********//**********");
			if (!asignarBeansAsistencia(request)) {
				setMensaje("Error capturando datos de asistencias para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			/* PARAMETROS PARA el JASPER DE ASISTENCIAS */
			parameters.put("usuario", login.getUsuarioId());
			// parameters.put("LogoCundinamarca",path1+"\\"+rb3.getString("imagen"));

			pst = con.prepareStatement(rb3
					.getString("existe_asistencia_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				con.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE ASISTENCIA GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				met = (!filtroAsistencia.getMetodologia().equals("-9") ? "_Metd_"
						+ filtroAsistencia.getMetodologia()
						: "");
				grado = (!filtroAsistencia.getGrado().equals("-9") ? "_Gra_"
						+ filtroAsistencia.getGrado() : "");
				grupo = (!filtroAsistencia.getGrupo().equals("-9") ? "_Gru_"
						+ filtroAsistencia.getGrupo() : "");

				nom = "Sede_"
						+ filtroAsistencia.getSede()
						+ "_Jrd_"
						+ filtroAsistencia.getJornada()
						+ met
						+ grado
						+ grupo
						+ "_Fec_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Rep_Asist_Bol_" + nom + ".xls";
				ponerReporte(moduloAsistencia, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivo + "",
						"xls", "" + archivo, "-1", "ReporteInsertarEstado");// Estado
																			// -1
																			// System.out
				// .println("Se insertn el ARCHIVO en Reporte con estado -1");

				if (!proceso_asistencia(archivo, vigencia)) {// estado cero del
																// reporte
																// System.out
					// .println("nNO TERMINn PROCESO DE ASISTENCIAS... OCURRIn ERROR..MIRAR TRACE!");
					ponerReporteMensaje(
							"2",
							moduloAsistencia,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivo + "",
							"xls", "" + archivo,
							"ReporteActualizarBoletinPaila",
							"Ocurrio problema en process de asistencia");
					limpiarTablasAsistencia(login.getUsuarioId());
					if (!updateReporteFecha("update_reporte_general",
							moduloAsistencia, archivo, login.getUsuarioId(),
							"2")) {
						ponerReporteMensaje("2", moduloAsistencia,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte") + archivo
										+ "", "xls", "" + archivo,
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha reporte");
						limpiarTablasAsistencia(login.getUsuarioId());
						return false;
					}
					return false;
				}
				String htmlfilename = archivo;
				con = cursor.getConnection();
				pst = con.prepareStatement(rb3.getString("jasper_asistencias"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				rs = pst.executeQuery();

				if (rs.next()) {
					rs.close();
					pst.close();
					// System.out
					// .println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE ASISTENCIASnnnnnnnnnnnnnnnnnnnnn");
					if ((reportFile.getPath() != null) && (parameters != null)
							&& (!parameters.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("***Se mandn ejecutar el jasper de ASISTENCIAS****");
						archivosalida = Ruta
								.get(contextoTotal,
										rb3.getString("reportes.PathReportesEstadisticos"));
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								reportFile.getPath(), parameters, con);
						String xlsFileName = archivo;
						String xlsFilesSource = archivosalida;
						File f = new File(xlsFilesSource);
						if (!f.exists())
							FileUtils.forceMkdir(f);
						// System.out.println("SALIDA DEL XLS: " +
						// xlsFilesSource
						// + xlsFileName);
						// Creacion del XLS
						JRXlsExporter exporter = new JRXlsExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,
								jasperPrint);
						exporter.setParameter(
								JRExporterParameter.OUTPUT_FILE_NAME,
								xlsFilesSource + xlsFileName);
						exporter.setParameter(
								JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
								Boolean.TRUE);
						exporter.exportReport();
						// System.out
						// .println("***SE EXPORTn EL ARCHIVO EXCEL****");
					}
					if (!updateReporteEstado(
							moduloAsistencia,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivo + "",
							"xls", "" + archivo, "ReporteActualizarListo")) {
						ponerReporteMensaje("2", moduloAsistencia,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte") + archivo
										+ "", "xls", "" + archivo,
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha final del reporte");
						limpiarTablasAsistencia(login.getUsuarioId());
						return false;
					}
					if (!updateReporteFecha("update_reporte_general",
							moduloAsistencia, archivo, login.getUsuarioId(),
							"1")) {
						limpiarTablasAsistencia(login.getUsuarioId());
						return false;
					}
					limpiarTablasAsistencia(login.getUsuarioId());
				} else {
					rs.close();
					pst.close();
					ponerReporteMensaje(
							"2",
							moduloAsistencia,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivo + "",
							"xls", "" + archivo,
							"ReporteActualizarBoletinGenerando",
							"nNo se encontraron registros para generar el reporte de Asistencias!");
					// System.out
					// .println("nNo se encontraron registros para generar el reporte de Asistencias!");
					if (!updateReporteFecha("update_reporte_general",
							moduloAsistencia, archivo, login.getUsuarioId(),
							"2")) {
						limpiarTablasAsistencia(login.getUsuarioId());
						return false;
					}
					limpiarTablasAsistencia(login.getUsuarioId());
					return true;
				}
			} else {
				rs.close();
				pst.close();
				con.close();
				setMensaje("Usted ya mandn generar un repote de Asistencia para Boletines \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloAsistencia, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivo + "", "xls",
					"" + archivo, "ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn en Asistencias");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean logros(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, periodo = null, nom = null, estudiante = null, est = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		bytes = null;

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR LOGROS!**********//**********");
			if (!asignarBeansLogro(request)) {
				setMensaje("Error capturando datos de logros para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3.getString("cedula_estudiante"));
			posicion = 1;
			pst.clearParameters();
			if (!filtroEvaluacionLogro.getEstudiante().equals("-9")) {
				pst.setString(posicion++, filtroEvaluacionLogro.getEstudiante());
			} else {
				if (!filtroEvaluacionLogro.getId().equals(""))
					pst.setString(posicion++, filtroEvaluacionLogro.getId());
			}
			rs = pst.executeQuery();
			if (rs.next())
				codigoestudiante = rs.getString(1);
			rs.close();
			pst.close();

			if (codigoestudiante == null) {
				System.out.println("CnDIGO DEL ESTUDIANTE ES NULO ");
				return false;
			}
			/* PARAMETROS PARA el JASPER DE ASISTENCIAS */
			parameters.put("usuario", login.getUsuarioId());
			parameters.put("estudiante", codigoestudiante);
			// parameters.put("LogoCundinamarca",path1+"\\"+rb3.getString("imagen"));
			// System.out.println("PARAMETERS DEL JASPER DE LOGROS: "
			// + parameters.values());

			pst = con.prepareStatement(rb3
					.getString("existe_logro_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				con.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE LOGRO GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");

				sed = (!filtroEvaluacionLogro.getSede().equals("-9") ? "_Sede_"
						+ filtroEvaluacionLogro.getSede() : "");
				jor = (!filtroEvaluacionLogro.getJornada().equals("-9") ? "_Jornada_"
						+ filtroEvaluacionLogro.getJornada()
						: "");
				met = (!filtroEvaluacionLogro.getMetodologia().equals("-9") ? "_Metodologia_"
						+ filtroEvaluacionLogro.getMetodologia()
						: "");
				grado = (!filtroEvaluacionLogro.getGrado().equals("-9") ? "_Grado_"
						+ filtroEvaluacionLogro.getGrado()
						: "");
				grupo = (!filtroEvaluacionLogro.getGrupo().equals("-9") ? "_Grupo_"
						+ filtroEvaluacionLogro.getGrupo()
						: "");
				periodo = (!filtroEvaluacionLogro.getPeriodo().trim()
						.equals("-9") ? "_Periodo_"
						+ filtroEvaluacionLogro.getPeriodo().trim() : "");
				estudiante = (!filtroEvaluacionLogro.getEstudiante().equals(
						"-9") ? "_Estudiante_"
						+ filtroEvaluacionLogro.getEstudiante() : "");
				est = (!filtroEvaluacionLogro.getId().equals("") ? "_Num_Doc_Estudiante_"
						+ filtroEvaluacionLogro.getId()
						: "");

				nom = sed
						+ jor
						+ met
						+ grado
						+ grupo
						+ periodo
						+ estudiante
						+ est
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivopdf = "Reporte_Evaluacion_Logros_" + nom + ".pdf";

				ponerReporte(moduloLogro, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivopdf + "",
						"pdf", "" + archivopdf, "-1", "ReporteInsertarEstado");// Estado
																				// -1
																				// System.out.println("Se insertn el PDF en Reporte con estado -1");

				if (!proceso_logro(archivopdf, vigencia, codigoestudiante)) {// estado
																				// cero
																				// del
																				// reporte
																				// System.out
					// .println("nNO TERMINn PROCESO DE LOGROS... OCURRIn ERROR..MIRAR TRACE!");
					ponerReporteMensaje("2", moduloLogro, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarBoletinPaila",
							"Ocurrio problema en process de evaluacion logros");
					limpiarTablasLogros(login.getUsuarioId());
					if (!updateReporteFecha("update_reporte_general",
							moduloLogro, archivo, login.getUsuarioId(), "2")) {
						ponerReporteMensaje("2", moduloLogro,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivopdf + "", "pdf", ""
										+ archivopdf,
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha reporte");
						limpiarTablasLogros(login.getUsuarioId());
						return false;
					}
					return false;
				}
				con = cursor.getConnection();
				pst = con.prepareStatement(rb3
						.getString("jasper_evaluacion_logros"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				rs = pst.executeQuery();

				if (rs.next()) {
					rs.close();
					pst.close();
					// System.out
					// .println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE LOGROSnnnnnnnnnnnnnnnnnnnnn");
					if ((reportFile2.getPath() != null) && (parameters != null)
							&& (!parameters.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("***Se mandn ejecutar el jasper de LOGROS****");
						archivosalida = Ruta
								.get(contextoTotal,
										rb3.getString("reportes.PathReportesEstadisticos"));
						bytes = JasperRunManager.runReportToPdf(
								reportFile2.getPath(), parameters, con);

						if (!ponerArchivo(moduloLogro, path, bytes, archivopdf,
								contextoTotal))
							limpiarTablasLogros(login.getUsuarioId());
					}
					if (!updateReporteEstado(moduloLogro, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarListo")) {
						ponerReporteMensaje("2", moduloLogro,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivopdf + "", "pdf", ""
										+ archivopdf,
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha final del reporte");
						limpiarTablasLogros(login.getUsuarioId());
						return false;
					}
					if (!updateReporteFecha("update_reporte_general",
							moduloLogro, archivopdf, login.getUsuarioId(), "1")) {
						limpiarTablasLogros(login.getUsuarioId());
						return false;
					}
					limpiarTablasLogros(login.getUsuarioId());
				} else {
					rs.close();
					pst.close();
					ponerReporteMensaje("2", moduloLogro, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarBoletinGenerando",
							"nNo se encontraron registros para generar el reporte de Evaluacinn de logros!");
					// System.out
					// .println("nNo se encontraron registros para generar el reporte de Evaluacinn de logros!");
					if (!updateReporteFecha("update_reporte_general",
							moduloLogro, archivopdf, login.getUsuarioId(), "2")) {
						limpiarTablasLogros(login.getUsuarioId());
						return false;
					}
					limpiarTablasLogros(login.getUsuarioId());
					return true;
				}
			} else {
				rs.close();
				pst.close();
				con.close();
				setMensaje("Usted ya mandn generar un repote de Evaluacinn de Logros \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloLogro, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivopdf + "",
					"pdf", "" + archivopdf, "ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn en Evaluacion logros");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean logrosGrupo(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, periodo = null, nom = null, estudiante = null, est = null;
		String puesto = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		bytes = null;

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR LOGROS POR GRUPO O GRADO!**********//**********");
			if (!asignarBeansLogroGrupo(request)) {
				setMensaje("Error capturando datos de logros por grupo para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3
					.getString("existe_logro_grupo_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE LOGRO POR GRUPO GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");

				sed = (!filtroLogrosGrupo.getSede().equals("-9") ? "_Sede_"
						+ filtroLogrosGrupo.getSede() : "");
				jor = (!filtroLogrosGrupo.getJornada().equals("-9") ? "_Jornada_"
						+ filtroLogrosGrupo.getJornada()
						: "");
				met = (!filtroLogrosGrupo.getMetodologia().equals("-9") ? "_Metodologia_"
						+ filtroLogrosGrupo.getMetodologia()
						: "");
				grado = (!filtroLogrosGrupo.getGrado().equals("-9") ? "_Grado_"
						+ filtroLogrosGrupo.getGrado() : "");
				grupo = (!filtroLogrosGrupo.getGrupo().equals("-9") ? "_Grupo_"
						+ filtroLogrosGrupo.getGrupo() : "");
				periodo = (!filtroLogrosGrupo.getPeriodo().trim().equals("-9") ? "_Periodo_"
						+ filtroLogrosGrupo.getPeriodo().trim()
						: "");

				nom = sed
						+ jor
						+ met
						+ grado
						+ grupo
						+ periodo
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivopdf = "Reporte_Evaluacion_Logros_" + nom + ".pdf";
				archivozip = "Reporte_Evaluacion_Logros_" + nom + ".zip";

				/* SE INSERTA EN LA TABLA DATOS_LOGRO_GRUPO */
				// System.out
				// .println("nnSe mando insertar el reporte en DATOS_LOGRO_GRUPO!!!!");
				posicion = 1;
				cstmt = con
						.prepareCall("{call PK_ESTADISTICOS.insertar_logros_grupo(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getInsitucion()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getSede()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getJornada()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getMetodologia()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getGrado()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getGrupo()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getPeriodo().trim()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroLogrosGrupo.getParametro().trim()));
				cstmt.setLong(posicion++, Long.parseLong(vigencia));
				cstmt.setString(posicion++, f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-'));
				cstmt.setString(posicion++, archivozip);
				cstmt.setString(posicion++, archivopdf);
				cstmt.setLong(posicion++, Long.parseLong("-1"));
				cstmt.setLong(posicion++, Long.parseLong(login.getUsuarioId()));

				// System.out
				// .println("Inicia procedimiento estadisticos_logros_grupo.. Hora: "
				// + new java.sql.Timestamp(System
				// .currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("nnSe insertn en DATOS_LOGRO_GRUPO!!!!");
				cstmt.close();

				ponerReporte(moduloLogroGrupo, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip4", "" + archivozip, "-1", "ReporteInsertarEstado");// Estado
																				// -1
																				// System.out.println("Se insertn el Reporte con estado -1");

				puesto = cola_reportes("puesto_del_reporte");

				if (puesto != null && !puesto.equals("")) {
					updatePuestoDatos(puesto, archivozip, login.getUsuarioId(),
							"update_puesto_reporte_logro");
					// System.out
					// .println("Insertn el puesto del reporte en DATOS_LOGRO_GRUPO, ya que esta en la cola");
				}
			} else {
				rs.close();
				pst.close();
				setMensaje("Usted ya mandn generar un repote de Evaluacinn de Logros por Grupo \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloLogroGrupo, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip + "",
					"zip4", "" + archivozip, "ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn en Evaluacion logros por Grupo");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean asignaturasGrupo(HttpServletRequest request,
			String vigencia, String contextoTotal) throws ServletException,
			IOException {
		Connection con = null;
		PreparedStatement pst = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, periodo = null, nom = null, estudiante = null, est = null;
		String puesto = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		bytes = null;
		String valor = "";
		String[] array = null;

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR ASIGNATURAS POR GRUPO!**********//**********");
			if (!asignarBeanAsignaturaGrupo(request)) {
				setMensaje("Error capturando datos de asignaturas por grupo para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3
					.getString("existe_asignatura_grupo_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE ASIGNATURA POR GRUPO GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");

				sed = (!filtroAsignaturasGrupo.getSede().equals("-9") ? "_Sede_"
						+ filtroAsignaturasGrupo.getSede()
						: "");
				jor = (!filtroAsignaturasGrupo.getJornada().equals("-9") ? "_Jornada_"
						+ filtroAsignaturasGrupo.getJornada()
						: "");
				met = (!filtroAsignaturasGrupo.getMetodologia().equals("-9") ? "_Metodologia_"
						+ filtroAsignaturasGrupo.getMetodologia()
						: "");
				grado = (!filtroAsignaturasGrupo.getGrado().equals("-9") ? "_Grado_"
						+ filtroAsignaturasGrupo.getGrado()
						: "");
				grupo = (!filtroAsignaturasGrupo.getGrupo().equals("-9") ? "_Grupo_"
						+ filtroAsignaturasGrupo.getGrupo()
						: "");
				periodo = (!filtroAsignaturasGrupo.getPeriodo().trim()
						.equals("-9") ? "_Periodo_"
						+ filtroAsignaturasGrupo.getPeriodo().trim() : "");

				nom = sed
						+ jor
						+ met
						+ grado
						+ grupo
						+ periodo
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivopdf = "Reporte_Evaluacion_Asignaturas" + nom + ".pdf";
				archivozip = "Reporte_Evaluacion_Asignaturas" + nom + ".zip";

				array = filtroAsignaturasGrupo.getEvaluaciones();
				for (int i = 0; i < array.length; i++)
					valor += array[i] + ".";
				valor = valor.substring(0, valor.length() - 1);

				// System.out.println("INSTITUCION: "
				// + filtroAsignaturasGrupo.getInsitucion());
				// System.out.println("SEDE: " +
				// filtroAsignaturasGrupo.getSede());
				// System.out.println("JORNADA: "
				// + filtroAsignaturasGrupo.getJornada());
				// System.out.println("METOD: "
				// + filtroAsignaturasGrupo.getMetodologia());
				// System.out.println("GRADO: "
				// + filtroAsignaturasGrupo.getGrado());
				// System.out.println("GRUPO: "
				// + filtroAsignaturasGrupo.getGrupo());
				// System.out.println("PERIODO: "
				// + filtroAsignaturasGrupo.getPeriodo().trim());
				// System.out.println("EVALUACIONES: " + valor.trim());
				// System.out.println("VIGENCIA: " + vigencia);
				// System.out.println("FECHA: "
				// + f2.toString().replace(' ', '_').replace(':', '-')
				// .replace('.', '-'));
				// System.out.println("ARCHIVOZIP: " + archivozip);
				// System.out.println("ARCHIVOPDF: " + archivopdf);
				// System.out.println("USER: " + login.getUsuarioId());

				/* SE INSERTA EN LA TABLA DATOS_LOGRO_GRUPO */
				// System.out
				// .println("nnSe mando insertar el reporte en DATOS_ASIG_GRUPO!!!!");
				posicion = 1;
				cstmt = con
						.prepareCall("{call PK_ESTADISTICOS.insertar_asignaturas_grupo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAsignaturasGrupo.getInsitucion()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAsignaturasGrupo.getSede()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAsignaturasGrupo.getJornada()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAsignaturasGrupo.getMetodologia()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAsignaturasGrupo.getGrado()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAsignaturasGrupo.getGrupo()));
				cstmt.setLong(posicion++, Long.parseLong(filtroAsignaturasGrupo
						.getPeriodo().trim()));
				cstmt.setString(posicion++, valor);
				cstmt.setLong(posicion++, Long.parseLong(vigencia));
				cstmt.setString(posicion++, f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-'));
				cstmt.setString(posicion++, archivozip);
				cstmt.setString(posicion++, archivopdf);
				cstmt.setLong(posicion++, Long.parseLong("-1"));
				cstmt.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				cstmt.setString(posicion++, tipoasignatura);

				// System.out
				// .println("Inicia procedimiento estadisticos_asignaturas_grupo.. Hora: "
				// + new java.sql.Timestamp(System
				// .currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("nnSe insertn en DATOS_ASIG_GRUPO!!!!");
				cstmt.close();

				ponerReporte(moduloAsignaturaGrupo, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip5", "" + archivozip, "-1", "ReporteInsertarEstado");// Estado
																				// -1
																				// System.out.println("Se insertn el Reporte con estado -1");

				puesto = cola_reportes_asignaturas(tipoasignatura);
				if (puesto != null && !puesto.equals("")) {
					updatePuestoDatosEvaluaciones(puesto, archivozip,
							login.getUsuarioId(),
							"update_puesto_reporte_asignatura", tipoasignatura);
					// System.out
					// .println("Insertn el puesto del reporte en DATOS_ASIG_GRUPO, ya que esta en la cola");
				}

			} else {
				rs.close();
				pst.close();
				setMensaje("Usted ya mandn generar un repote de Evaluacinn de Asignaturas \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloAsignaturaGrupo,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip5", "" + archivozip,
					"ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn en Evaluacion Asignaturas por Grupo");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean areasGrupo(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, periodo = null, nom = null, estudiante = null, est = null;
		String puesto = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		bytes = null;
		String valor = "";
		String[] array = null;

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR AREAS POR GRUPO!**********//**********");
			if (!asignarBeanAreaGrupo(request)) {
				setMensaje("Error capturando datos de areas por grupo para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3
					.getString("existe_area_grupo_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE AREA POR GRUPO GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");

				sed = (!filtroAreasGrupo.getSede().equals("-9") ? "_Sede_"
						+ filtroAreasGrupo.getSede() : "");
				jor = (!filtroAreasGrupo.getJornada().equals("-9") ? "_Jornada_"
						+ filtroAreasGrupo.getJornada()
						: "");
				met = (!filtroAreasGrupo.getMetodologia().equals("-9") ? "_Metodologia_"
						+ filtroAreasGrupo.getMetodologia()
						: "");
				grado = (!filtroAreasGrupo.getGrado().equals("-9") ? "_Grado_"
						+ filtroAreasGrupo.getGrado() : "");
				grupo = (!filtroAreasGrupo.getGrupo().equals("-9") ? "_Grupo_"
						+ filtroAreasGrupo.getGrupo() : "");
				periodo = (!filtroAreasGrupo.getPeriodo().trim().equals("-9") ? "_Periodo_"
						+ filtroAreasGrupo.getPeriodo().trim()
						: "");

				nom = sed
						+ jor
						+ met
						+ grado
						+ grupo
						+ periodo
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivopdf = "Reporte_Evaluacion_Areas_" + nom + ".pdf";
				archivozip = "Reporte_Evaluacion_Areas_" + nom + ".zip";

				array = filtroAreasGrupo.getEvaluaciones();
				for (int i = 0; i < array.length; i++)
					valor += array[i] + ".";
				valor = valor.substring(0, valor.length() - 1);

				// System.out.println("INSTITUCION: "
				// + filtroAreasGrupo.getInsitucion());
				// System.out.println("SEDE: " + filtroAreasGrupo.getSede());
				// System.out.println("JORNADA: " +
				// filtroAreasGrupo.getJornada());
				// System.out.println("METOD: "
				// + filtroAreasGrupo.getMetodologia());
				// System.out.println("GRADO: " + filtroAreasGrupo.getGrado());
				// System.out.println("GRUPO: " + filtroAreasGrupo.getGrupo());
				// System.out.println("PERIODO: "
				// + filtroAreasGrupo.getPeriodo().trim());
				// System.out.println("EVALUACIONES: " + valor.trim());
				// System.out.println("VIGENCIA: " + vigencia);
				// System.out.println("FECHA: "
				// + f2.toString().replace(' ', '_').replace(':', '-')
				// .replace('.', '-'));
				// System.out.println("ARCHIVOZIP: " + archivozip);
				// System.out.println("ARCHIVOPDF: " + archivopdf);
				// System.out.println("USER: " + login.getUsuarioId());

				/* SE INSERTA EN LA TABLA DATOS_LOGRO_GRUPO */
				// System.out
				// .println("nnSe mando insertar el reporte en DATOS_ASIG_GRUPO!!!!");
				posicion = 1;
				cstmt = con
						.prepareCall("{call PK_ESTADISTICOS.insertar_asignaturas_grupo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAreasGrupo.getInsitucion()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAreasGrupo.getSede()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAreasGrupo.getJornada()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAreasGrupo.getMetodologia()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAreasGrupo.getGrado()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAreasGrupo.getGrupo()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroAreasGrupo.getPeriodo().trim()));
				cstmt.setString(posicion++, valor);
				cstmt.setLong(posicion++, Long.parseLong(vigencia));
				cstmt.setString(posicion++, f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-'));
				cstmt.setString(posicion++, archivozip);
				cstmt.setString(posicion++, archivopdf);
				cstmt.setLong(posicion++, Long.parseLong("-1"));
				cstmt.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				cstmt.setString(posicion++, tipoarea);

				// System.out
				// .println("Inicia procedimiento estadisticos_asignaturas_grupo.. Hora: "
				// + new java.sql.Timestamp(System
				// .currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("nnSe insertn en DATOS_ASIG_GRUPO!!!!");
				cstmt.close();

				ponerReporte(moduloAreaGrupo, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "-1", "ReporteInsertarEstado");// Estado
																				// -1
																				// System.out.println("Se insertn el Reporte con estado -1");

				puesto = cola_reportes_asignaturas(tipoarea);
				if (puesto != null && !puesto.equals("")) {
					updatePuestoDatosEvaluaciones(puesto, archivozip,
							login.getUsuarioId(),
							"update_puesto_reporte_asignatura", tipoarea);
					// System.out
					// .println("Insertn el puesto del reporte en DATOS_ASIG_GRUPO, ya que esta en la cola");
				}
			} else {
				rs.close();
				pst.close();
				setMensaje("Usted ya mandn generar un repote de Evaluacinn de Areas \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloAreaGrupo, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip + "",
					"zip", "" + archivozip, "ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn en Evaluacion Areas");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean tipoDescriptor(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, periodo = null, nom = null, estudiante = null, est = null;
		String puesto = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		bytes = null;
		String valor = "1";
		String[] array = null;

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR TIPO DESCRIPTOR!**********//**********");
			if (!asignarBeanTipoDescriptor(request)) {
				setMensaje("Error capturando datos para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3
					.getString("existe_tipo_descriptor_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE TIPO DESCRIPTOR GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");

				sed = (!filtroTipoDescriptor.getSede().equals("-9") ? "_Sede_"
						+ filtroTipoDescriptor.getSede() : "");
				jor = (!filtroTipoDescriptor.getJornada().equals("-9") ? "_Jornada_"
						+ filtroTipoDescriptor.getJornada()
						: "");
				met = (!filtroTipoDescriptor.getMetodologia().equals("-9") ? "_Metodologia_"
						+ filtroTipoDescriptor.getMetodologia()
						: "");
				grado = (!filtroTipoDescriptor.getGrado().equals("-9") ? "_Grado_"
						+ filtroTipoDescriptor.getGrado()
						: "");
				grupo = (!filtroTipoDescriptor.getGrupo().equals("-9") ? "_Grupo_"
						+ filtroTipoDescriptor.getGrupo()
						: "");
				periodo = (!filtroTipoDescriptor.getPeriodo().trim()
						.equals("-9") ? "_Periodo_"
						+ filtroTipoDescriptor.getPeriodo().trim() : "");

				nom = sed
						+ jor
						+ met
						+ grado
						+ grupo
						+ periodo
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivopdf = "Reporte_Evaluacion_Tipo_Descriptor_" + nom
						+ ".pdf";
				archivozip = "Reporte_Evaluacion_Tipo_Descriptor_" + nom
						+ ".zip";
				/*
				 * array=filtroTipoDescriptor.getTipodescriptores(); for(int
				 * i=0;i<array.length;i++) valor+=array[i]+".";
				 * valor=valor.substring(0,valor.length()-1);
				 */
				// System.out.println("INSTITUCION: "
				// + filtroTipoDescriptor.getInsitucion());
				// System.out.println("SEDE: " +
				// filtroTipoDescriptor.getSede());
				// System.out.println("JORNADA: "
				// + filtroTipoDescriptor.getJornada());
				// System.out.println("METOD: "
				// + filtroTipoDescriptor.getMetodologia());
				// System.out.println("GRADO: " +
				// filtroTipoDescriptor.getGrado());
				// System.out.println("GRUPO: " +
				// filtroTipoDescriptor.getGrupo());
				// System.out.println("PERIODO: "
				// + filtroTipoDescriptor.getPeriodo().trim());
				// System.out.println("EVALUACIONES: " + valor.trim());
				// System.out.println("VIGENCIA: " + vigencia);
				// System.out.println("FECHA: "
				// + f2.toString().replace(' ', '_').replace(':', '-')
				// .replace('.', '-'));
				// System.out.println("ARCHIVOZIP: " + archivozip);
				// System.out.println("ARCHIVOPDF: " + archivopdf);
				// System.out.println("USER: " + login.getUsuarioId());

				/* SE INSERTA EN LA TABLA DATOS_LOGRO_GRUPO */
				// System.out
				// .println("nnSe mando insertar el reporte en DATOS_TIP_DESC!!!!");
				posicion = 1;
				cstmt = con
						.prepareCall("{call PK_ESTADISTICOS.insertar_tipos_desc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setLong(posicion++,
						Long.parseLong(filtroTipoDescriptor.getInsitucion()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroTipoDescriptor.getSede()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroTipoDescriptor.getJornada()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroTipoDescriptor.getMetodologia()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroTipoDescriptor.getGrado()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroTipoDescriptor.getGrupo()));
				cstmt.setLong(posicion++,
						Long.parseLong(filtroTipoDescriptor.getArea()));
				cstmt.setLong(posicion++, Long.parseLong(filtroTipoDescriptor
						.getPeriodo().trim()));
				cstmt.setString(posicion++, valor);
				cstmt.setLong(posicion++, Long.parseLong(vigencia));
				cstmt.setString(posicion++, f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-'));
				cstmt.setString(posicion++, archivozip);
				cstmt.setString(posicion++, archivopdf);
				cstmt.setLong(posicion++, Long.parseLong("-1"));
				cstmt.setLong(posicion++, Long.parseLong(login.getUsuarioId()));

				// System.out
				// .println("Inicia procedimiento insertar_tipos_desc.. Hora: "
				// + new java.sql.Timestamp(System
				// .currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("nnSe insertn en DATOS_TIP_DESC!!!!");
				cstmt.close();

				ponerReporte(moduloTipoDescriptor, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "-1", "ReporteInsertarEstado");// Estado
																				// -1
																				// System.out.println("Se insertn el Reporte con estado -1");

				puesto = cola_reportes("puesto_tipo_descriptor");
				if (puesto != null && !puesto.equals("")) {
					updatePuestoDatos(puesto, archivozip, login.getUsuarioId(),
							"update_puesto_reporte_tipo");
					// System.out
					// .println("Insertn el puesto del reporte en DATOS_TIP_DESC, ya que esta en la cola");
				}
			} else {
				rs.close();
				pst.close();
				setMensaje("Usted ya mandn generar un repote de Evaluacinn de Tipo de Descriptor \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloTipoDescriptor,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn en Evaluacion Tipo de Descriptor");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean asignaturas(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, periodo = null, nom = null, estudiante = null, est = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		bytes = null;

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR ASIGNATURAS!**********//**********");
			if (!asignarBeansAsignatura(request)) {
				setMensaje("Error capturando datos de asignaturas para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3.getString("cedula_estudiante"));
			posicion = 1;
			pst.clearParameters();
			if (!filtroEvaluacionAsignatura.getEstudiante().equals("-9")) {
				pst.setString(posicion++,
						filtroEvaluacionAsignatura.getEstudiante());
			} else {
				if (!filtroEvaluacionAsignatura.getId().equals(""))
					pst.setString(posicion++,
							filtroEvaluacionAsignatura.getId());
			}
			rs = pst.executeQuery();
			if (rs.next())
				codigoestudiante = rs.getString(1);
			rs.close();
			pst.close();

			if (codigoestudiante == null) {
				System.out.println("CnDIGO DEL ESTUDIANTE ES NULO ");
				return false;
			}
			/* PARAMETROS PARA el JASPER DE ASISTENCIAS */
			parameters.put("usuario", login.getUsuarioId());
			parameters.put("estudiante", codigoestudiante);
			parameters.put(
					"escalas_valorativas",
					path + File.separator
							+ rb3.getString("escalas_valorativas"));
			// System.out.println("PARAMETERS DEL JASPER DE ASIGNATURAS: "
			// + parameters.values());

			pst = con.prepareStatement(rb3
					.getString("existe_asignatura_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				con.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE ASIGNATURA GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");

				sed = (!filtroEvaluacionAsignatura.getSede().equals("-9") ? "_Sede_"
						+ filtroEvaluacionAsignatura.getSede()
						: "");
				jor = (!filtroEvaluacionAsignatura.getJornada().equals("-9") ? "_Jornada_"
						+ filtroEvaluacionAsignatura.getJornada()
						: "");
				met = (!filtroEvaluacionAsignatura.getMetodologia()
						.equals("-9") ? "_Metodologia_"
						+ filtroEvaluacionAsignatura.getMetodologia() : "");
				grado = (!filtroEvaluacionAsignatura.getGrado().equals("-9") ? "_Grado_"
						+ filtroEvaluacionAsignatura.getGrado()
						: "");
				grupo = (!filtroEvaluacionAsignatura.getGrupo().equals("-9") ? "_Grupo_"
						+ filtroEvaluacionAsignatura.getGrupo()
						: "");
				periodo = (!filtroEvaluacionAsignatura.getPeriodo().trim()
						.equals("-9") ? "_Periodo_"
						+ filtroEvaluacionAsignatura.getPeriodo().trim() : "");
				estudiante = (!filtroEvaluacionAsignatura.getEstudiante()
						.equals("-9") ? "_Estudiante_"
						+ filtroEvaluacionAsignatura.getEstudiante() : "");
				est = (!filtroEvaluacionAsignatura.getId().equals("") ? "_Num_Doc_Estudiante_"
						+ filtroEvaluacionAsignatura.getId()
						: "");

				nom = sed
						+ jor
						+ met
						+ grado
						+ grupo
						+ periodo
						+ estudiante
						+ est
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivopdf = "Reporte_Evaluacion_Asignaturas_" + nom + ".pdf";

				ponerReporte(moduloAsignatura, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivopdf + "",
						"pdf", "" + archivopdf, "-1", "ReporteInsertarEstado");// Estado
																				// -1
																				// System.out
				// .println("Se insertn el PDF en Reporte con estado -1");

				if (!proceso_asignatura(archivopdf, vigencia, codigoestudiante)) {// estado
																					// cero
																					// del
																					// reporte
																					// System.out
					// .println("nNO TERMINn PROCESO DE ASIGNATURAS... OCURRIn ERROR..MIRAR TRACE!");
					ponerReporteMensaje("2", moduloAsignatura,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarBoletinPaila",
							"Ocurrio problema en process de evaluacion asignaturas");
					limpiarTablasAsignaturas(login.getUsuarioId());
					if (!updateReporteFecha("update_reporte_general",
							moduloAsignatura, archivo, login.getUsuarioId(),
							"2")) {
						ponerReporteMensaje("2", moduloAsignatura,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivopdf + "", "pdf", ""
										+ archivopdf,
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha reporte");
						limpiarTablasAsignaturas(login.getUsuarioId());
						return false;
					}
					return false;
				}
				con = cursor.getConnection();
				pst = con.prepareStatement(rb3
						.getString("jasper_evaluacion_asignaturas"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				rs = pst.executeQuery();

				if (rs.next()) {
					rs.close();
					pst.close();
					// System.out
					// .println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE ASIGNATURASnnnnnnnnnnnnnnnnnnnnn");
					if ((reportFile3.getPath() != null) && (parameters != null)
							&& (!parameters.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("***Se mandn ejecutar el jasper de ASIGNATURAS****");
						archivosalida = Ruta
								.get(contextoTotal,
										rb3.getString("reportes.PathReportesEstadisticos"));
						bytes = JasperRunManager.runReportToPdf(
								reportFile3.getPath(), parameters, con);

						if (!ponerArchivo(moduloAsignatura, path, bytes,
								archivopdf, contextoTotal))
							limpiarTablasAsignaturas(login.getUsuarioId());
					}
					if (!updateReporteEstado(moduloAsignatura,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarListo")) {
						ponerReporteMensaje("2", moduloAsignatura,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivopdf + "", "pdf", ""
										+ archivopdf,
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha final del reporte");
						limpiarTablasAsignaturas(login.getUsuarioId());
						return false;
					}
					if (!updateReporteFecha("update_reporte_general",
							moduloAsignatura, archivopdf, login.getUsuarioId(),
							"1")) {
						limpiarTablasAsignaturas(login.getUsuarioId());
						return false;
					}
					limpiarTablasAsignaturas(login.getUsuarioId());

				} else {
					rs.close();
					pst.close();
					ponerReporteMensaje(
							"2",
							moduloAsignatura,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "",
							"pdf",
							"" + archivopdf,
							"ReporteActualizarBoletinGenerando",
							"nNo se encontraron registros para generar el reporte de Evaluacinn de asignaturas!");
					// System.out
					// .println("nNo se encontraron registros para generar el reporte de Evaluacinn de asignaturas!");
					if (!updateReporteFecha("update_reporte_general",
							moduloAsignatura, archivopdf, login.getUsuarioId(),
							"2")) {
						limpiarTablasAsignaturas(login.getUsuarioId());
						return false;
					}
					limpiarTablasAsignaturas(login.getUsuarioId());
					return true;
				}
			} else {
				rs.close();
				pst.close();
				con.close();
				setMensaje("Usted ya mandn generar un repote de Evaluacinn de Asignaturas \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloAsignatura, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivopdf + "",
					"pdf", "" + archivopdf, "ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn en Evaluacion asignaturas");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean areas(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sed = null, jor = null, met = null, grado = null, grupo = null, periodo = null, nom = null, estudiante = null, est = null;
		int posicion = 1;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		bytes = null;

		try {
			con = cursor.getConnection();
			// System.out
			// .println("*********////*********nENTRn A GENERAR AREAS!**********//**********");
			if (!asignarBeansArea(request)) {
				setMensaje("Error capturando datos de areas para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3.getString("cedula_estudiante"));
			posicion = 1;
			pst.clearParameters();
			if (!filtroEvaluacionArea.getEstudiante().equals("-9")) {
				pst.setString(posicion++, filtroEvaluacionArea.getEstudiante());
			} else {
				if (!filtroEvaluacionArea.getId().equals(""))
					pst.setString(posicion++, filtroEvaluacionArea.getId());
			}
			rs = pst.executeQuery();
			if (rs.next())
				codigoestudiante = rs.getString(1);
			rs.close();
			pst.close();

			if (codigoestudiante == null) {
				System.out.println("CnDIGO DEL ESTUDIANTE ES NULO ");
				return false;
			}
			/* PARAMETROS PARA el JASPER DE ASISTENCIAS */
			parameters.put("usuario", login.getUsuarioId());
			parameters.put("estudiante", codigoestudiante);
			parameters.put(
					"escalas_valorativas",
					path + File.separator
							+ rb3.getString("escalas_valorativas"));
			// System.out.println("PARAMETERS DEL JASPER DE AREAS: "
			// + parameters.values());

			pst = con
					.prepareStatement(rb3.getString("existe_area_generandose"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
				con.close();
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE AREA GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");

				sed = (!filtroEvaluacionArea.getSede().equals("-9") ? "_Sede_"
						+ filtroEvaluacionArea.getSede() : "");
				jor = (!filtroEvaluacionArea.getJornada().equals("-9") ? "_Jornada_"
						+ filtroEvaluacionArea.getJornada()
						: "");
				met = (!filtroEvaluacionArea.getMetodologia().equals("-9") ? "_Metodologia_"
						+ filtroEvaluacionArea.getMetodologia()
						: "");
				grado = (!filtroEvaluacionArea.getGrado().equals("-9") ? "_Grado_"
						+ filtroEvaluacionArea.getGrado()
						: "");
				grupo = (!filtroEvaluacionArea.getGrupo().equals("-9") ? "_Grupo_"
						+ filtroEvaluacionArea.getGrupo()
						: "");
				periodo = (!filtroEvaluacionArea.getPeriodo().trim()
						.equals("-9") ? "_Periodo_"
						+ filtroEvaluacionArea.getPeriodo().trim() : "");
				estudiante = (!filtroEvaluacionArea.getEstudiante()
						.equals("-9") ? "_Estudiante_"
						+ filtroEvaluacionArea.getEstudiante() : "");
				est = (!filtroEvaluacionArea.getId().equals("") ? "_Num_Doc_Estudiante_"
						+ filtroEvaluacionArea.getId()
						: "");

				nom = sed
						+ jor
						+ met
						+ grado
						+ grupo
						+ periodo
						+ estudiante
						+ est
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivopdf = "Reporte_Evaluacion_Areas_" + nom + ".pdf";

				ponerReporte(moduloArea, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivopdf + "",
						"pdf", "" + archivopdf, "-1", "ReporteInsertarEstado");// Estado
																				// -1
																				// System.out
				// .println("Se insertn el PDF en Reporte con estado -1");

				if (!proceso_area(archivopdf, vigencia, codigoestudiante)) {// estado
																			// cero
																			// del
																			// reporte
																			// System.out
					// .println("nNO TERMINn PROCESO DE AREAS... OCURRIn ERROR..MIRAR TRACE!");
					ponerReporteMensaje("2", moduloArea, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarBoletinPaila",
							"ocurrio problema en process de evaluacion areas");
					limpiarTablasAreas(login.getUsuarioId());
					if (!updateReporteFecha("update_reporte_general",
							moduloArea, archivo, login.getUsuarioId(), "2")) {
						ponerReporteMensaje("2", moduloArea,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivopdf + "", "pdf", ""
										+ archivopdf,
								"ReporteActualizarBoletinPaila",
								"ocurrio problema al actualizar fecha reporte");
						limpiarTablasAreas(login.getUsuarioId());
						return false;
					}
					return false;
				}
				con = cursor.getConnection();
				pst = con.prepareStatement(rb3
						.getString("jasper_evaluacion_areas"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				rs = pst.executeQuery();

				if (rs.next()) {
					rs.close();
					pst.close();
					// System.out
					// .println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE AREASnnnnnnnnnnnnnnnnnnnnn");
					if ((reportFile4.getPath() != null) && (parameters != null)
							&& (!parameters.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("***Se mandn ejecutar el jasper de AREAS****");
						archivosalida = Ruta
								.get(contextoTotal,
										rb3.getString("reportes.PathReportesEstadisticos"));
						bytes = JasperRunManager.runReportToPdf(
								reportFile4.getPath(), parameters, con);

						if (!ponerArchivo(moduloArea, path, bytes, archivopdf,
								contextoTotal))
							limpiarTablasAreas(login.getUsuarioId());
					}
					if (!updateReporteEstado(moduloArea, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarListo")) {
						ponerReporteMensaje("2", moduloArea,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivopdf + "", "pdf", ""
										+ archivopdf,
								"ReporteActualizarBoletinPaila",
								"ocurrio problema al actualizar fecha final del reporte");
						limpiarTablasAreas(login.getUsuarioId());
						return false;
					}
					if (!updateReporteFecha("update_reporte_general",
							moduloArea, archivopdf, login.getUsuarioId(), "1")) {
						limpiarTablasAreas(login.getUsuarioId());
						return false;
					}
					limpiarTablasAreas(login.getUsuarioId());
				} else {
					rs.close();
					pst.close();
					ponerReporteMensaje("2", moduloArea, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivopdf
									+ "", "pdf", "" + archivopdf,
							"ReporteActualizarBoletinGenerando",
							"nNo se encontraron registros para generar el reporte de Evaluacinn de areas!");
					// System.out
					// .println("nNo se encontraron registros para generar el reporte de Evaluacinn de areas!");
					if (!updateReporteFecha("update_reporte_general",
							moduloArea, archivopdf, login.getUsuarioId(), "2")) {
						limpiarTablasAreas(login.getUsuarioId());
						return false;
					}
					limpiarTablasAreas(login.getUsuarioId());
					return true;
				}
			} else {
				rs.close();
				pst.close();
				con.close();
				setMensaje("Usted ya mandn generar un repote de Evaluacinn de Areas \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloArea, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivopdf + "",
					"pdf", "" + archivopdf, "ReporteActualizarBoletinPaila",
					"ocurrio excepcinn en Evaluacion areas");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Ejecuta el proceso para generar el reporte de Areas y
	 * Asignaturas <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public boolean proceso_asistencia(String archivozip1, String vigencia) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		bytes = null;

		try {
			limpiarTablasAsistencia(login.getUsuarioId());
			// System.out.println("nSe limpin la tabla antes de ser llenada!");
			updateReporteEstado(moduloAsistencia, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip1 + "",
					"xls", "" + archivozip1, "ReporteActualizarGenerando");
			updateReporteFecha("update_reporte_general", moduloAsistencia,
					archivozip1, login.getUsuarioId(), "0");
			con = cursor.getConnection();

			if (!filtroAsistencia.getSede().equals("-9")
					&& !filtroAsistencia.getJornada().equals("-9")
					&& !filtroAsistencia.getMetodologia().equals("-9")
					&& !filtroAsistencia.getGrado().equals("-9")
					&& !filtroAsistencia.getGrupo().equals("-9")) {
				// System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-GRUPO");
				// System.out.println("SEDE: " + filtroAsistencia.getSede());
				// System.out.println("JORNADA: " +
				// filtroAsistencia.getJornada());
				// System.out.println("METOD: "
				// + filtroAsistencia.getMetodologia());
				// System.out.println("GRADO: " + filtroAsistencia.getGrado());
				// System.out.println("GRUPO: " + filtroAsistencia.getGrupo());
				// System.out.println("viegncia: " + vigencia);

				pst = con.prepareStatement(rb3
						.getString("insert_encabezado_asistencia"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				pst.setLong(posicion++,
						Long.parseLong(filtroAsistencia.getInsitucion()));
				pst.setLong(posicion++,
						Long.parseLong(filtroAsistencia.getSede()));
				pst.setLong(posicion++,
						Long.parseLong(filtroAsistencia.getJornada()));
				pst.setLong(posicion++,
						Long.parseLong(filtroAsistencia.getMetodologia()));
				pst.setLong(posicion++,
						Long.parseLong(filtroAsistencia.getGrado()));
				pst.setLong(posicion++,
						Long.parseLong(filtroAsistencia.getGrupo()));
				pst.executeUpdate();
				pst.close();
				// System.out.println("ejecutn insert_encabezado_asistencia");
			} else {
				// System.out
				// .println("nnnSEDE-JORNADA-METODOLOGIA-GRADO-GRUPO NULOS EN PROCESO ASITENCIA!!!");
				return false;
			}
			return true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Ejecuta el proceso para generar el reporte de Areas y
	 * Asignaturas <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public boolean proceso_logro(String archivozip1, String vigencia,
			String codigoestudiante1) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			limpiarTablasLogros(login.getUsuarioId());
			// System.out.println("nSe limpin la tabla antes de ser llenada!");
			updateReporteEstado(moduloLogro, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip1 + "",
					"pdf", "" + archivozip1, "ReporteActualizarGenerando");
			updateReporteFecha("update_reporte_general", moduloLogro,
					archivozip1, login.getUsuarioId(), "0");
			con = cursor.getConnection();

			// System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-GRUPO-ESTUDIANTE");
			// System.out.println("SEDE: "+filtroEvaluacionLogro.getSede());
			// System.out.println("JORNADA: "+filtroEvaluacionLogro.getJornada());
			// System.out.println("METOD: "+filtroEvaluacionLogro.getMetodologia());
			// System.out.println("GRADO: "+filtroEvaluacionLogro.getGrado());
			// System.out.println("GRUPO: "+filtroEvaluacionLogro.getGrupo());
			// System.out.println("PERIODO: "+filtroEvaluacionLogro.getPeriodo().trim());
			// System.out.println("ESTUDIANTE: "+filtroEvaluacionLogro.getEstudiante());
			// System.out.println("ID ESTUDIANTE: "+filtroEvaluacionLogro.getId());
			// System.out.println("COD_ESTUDIANTE: "+codigoestudiante1);
			// System.out.println("Vigencia: "+vigencia);

			cstmt = con
					.prepareCall("{call PK_ESTADISTICOS.estadisticos_logros(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionLogro.getInsitucion()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionLogro.getSede()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionLogro.getJornada()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionLogro.getMetodologia()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionLogro.getGrado()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionLogro.getGrupo()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionLogro.getPeriodo().trim()));
			cstmt.setString(posicion++, filtroEvaluacionLogro.getEstudiante());
			cstmt.setLong(posicion++, Long.parseLong(vigencia));
			cstmt.setString(posicion++,
					(!codigoestudiante1.equals("") ? codigoestudiante1 : "0"));
			cstmt.setString(
					posicion++,
					(!filtroEvaluacionLogro.getId().equals("") ? filtroEvaluacionLogro
							.getId() : "0"));

			// System.out
			// .println("Inicia procedimiento estadisticos_logros.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("Fin procedimiento estadisticos_logros.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta el proceso para generar el reporte de Areas y
	 * Asignaturas <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public boolean proceso_asignatura(String archivozip1, String vigencia,
			String codigoestudiante1) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			limpiarTablasAsignaturas(login.getUsuarioId());
			// System.out.println("nSe limpin la tabla antes de ser llenada!");
			updateReporteEstado(moduloAsignatura, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip1 + "",
					"pdf", "" + archivozip1, "ReporteActualizarGenerando");
			updateReporteFecha("update_reporte_general", moduloAsignatura,
					archivozip1, login.getUsuarioId(), "0");
			con = cursor.getConnection();

			// System.out.println("SEDE: " +
			// filtroEvaluacionAsignatura.getSede());
			// System.out.println("JORNADA: "
			// + filtroEvaluacionAsignatura.getJornada());
			// System.out.println("METOD: "
			// + filtroEvaluacionAsignatura.getMetodologia());
			// System.out.println("GRADO: "
			// + filtroEvaluacionAsignatura.getGrado());
			// System.out.println("GRUPO: "
			// + filtroEvaluacionAsignatura.getGrupo());
			// System.out.println("PERIODO: "
			// + filtroEvaluacionAsignatura.getPeriodo().trim());
			// System.out.println("ESTUDIANTE: "
			// + filtroEvaluacionAsignatura.getEstudiante());
			// System.out.println("ID ESTUDIANTE: "
			// + filtroEvaluacionAsignatura.getId());
			// System.out.println("COD_ESTUDIANTE: " + codigoestudiante1);
			// System.out.println("Vigencia: " + vigencia);

			cstmt = con
					.prepareCall("{call PK_ESTADISTICOS.estadisticos_asignaturas(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionAsignatura.getInsitucion()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionAsignatura.getSede()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionAsignatura.getJornada()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionAsignatura.getMetodologia()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionAsignatura.getGrado()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionAsignatura.getGrupo()));
			cstmt.setLong(posicion++, Long.parseLong(filtroEvaluacionAsignatura
					.getPeriodo().trim()));
			cstmt.setString(posicion++,
					filtroEvaluacionAsignatura.getEstudiante());
			cstmt.setLong(posicion++, Long.parseLong(vigencia));
			cstmt.setString(posicion++,
					(!codigoestudiante1.equals("") ? codigoestudiante1 : "0"));
			cstmt.setString(posicion++, (!filtroEvaluacionAsignatura.getId()
					.equals("") ? filtroEvaluacionAsignatura.getId() : "0"));
			// System.out
			// .println("Inicia procedimiento estadisticos_asignaturas.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out
			// .println("Fin procedimiento estadisticos_asignaturas.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta el proceso para generar el reporte de Areas y
	 * Asignaturas <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public boolean proceso_area(String archivozip1, String vigencia,
			String codigoestudiante1) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			limpiarTablasAreas(login.getUsuarioId());
			// System.out.println("nSe limpin la tabla antes de ser llenada!");
			updateReporteEstado(moduloArea, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip1 + "",
					"pdf", "" + archivozip1, "ReporteActualizarGenerando");
			updateReporteFecha("update_reporte_general", moduloArea,
					archivozip1, login.getUsuarioId(), "0");
			con = cursor.getConnection();

			// System.out.println("SEDE: " + filtroEvaluacionArea.getSede());
			// System.out.println("JORNADA: " +
			// filtroEvaluacionArea.getJornada());
			// System.out.println("METOD: "
			// + filtroEvaluacionArea.getMetodologia());
			// System.out.println("GRADO: " + filtroEvaluacionArea.getGrado());
			// System.out.println("GRUPO: " + filtroEvaluacionArea.getGrupo());
			// System.out.println("PERIODO: "
			// + filtroEvaluacionArea.getPeriodo().trim());
			// System.out.println("ESTUDIANTE: "
			// + filtroEvaluacionArea.getEstudiante());
			// System.out
			// .println("ID ESTUDIANTE: " + filtroEvaluacionArea.getId());
			// System.out.println("COD_ESTUDIANTE: " + codigoestudiante1);
			// System.out.println("Vigencia: " + vigencia);

			cstmt = con
					.prepareCall("{call PK_ESTADISTICOS.estadisticos_areas(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionArea.getInsitucion()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionArea.getSede()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionArea.getJornada()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionArea.getMetodologia()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionArea.getGrado()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionArea.getGrupo()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtroEvaluacionArea.getPeriodo().trim()));
			cstmt.setString(posicion++, filtroEvaluacionArea.getEstudiante());
			cstmt.setLong(posicion++, Long.parseLong(vigencia));
			cstmt.setString(posicion++,
					(!codigoestudiante1.equals("") ? codigoestudiante1 : "0"));
			cstmt.setString(
					posicion++,
					(!filtroEvaluacionArea.getId().equals("") ? filtroEvaluacionArea
							.getId() : "0"));
			// System.out
			// .println("Inicia procedimiento estadisticos_areas.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("Fin procedimiento estadisticos_areas.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	public void ponerReporte(String modulo, String us, String rec, String tipo,
			String nombre, String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (estado));
			pst.executeUpdate();
			pst.close();
			// System.out.println("nnnInsertn en la tabla Reporte!!!");

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void ponerReporteMensaje(String estado, String modulo, String us,
			String rec, String tipo, String nombre, String prepared,
			String mensaje) {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (estado));
			pst.setString(posicion++, (mensaje));
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			pst.close();
			// System.out
			// .println("nActualizn la tabla Reporte con el mensaje recibido!");
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public boolean updateReporteEstado(String modulo, String us, String rec,
			String tipo, String nombre, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			con.commit();
			pst.close();
			// System.out.println("nnSE ACTUALIZn EL ESTADO DEL REPORTE!!");
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) session.getAttribute("login");
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansAsistencia(HttpServletRequest request)
			throws ServletException, IOException {
		filtroAsistencia = (FiltroBeanAsistencia) session
				.getAttribute("filtroA");
		filtroAsistencia.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansLogro(HttpServletRequest request)
			throws ServletException, IOException {
		filtroEvaluacionLogro = (FiltroBeanEvaluacionLogro) session
				.getAttribute("filtroB");
		filtroEvaluacionLogro.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansLogroGrupo(HttpServletRequest request)
			throws ServletException, IOException {
		filtroLogrosGrupo = (FiltroBeanEvaluacionLogroGrupo) session
				.getAttribute("filtroE");
		filtroLogrosGrupo.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeanAsignaturaGrupo(HttpServletRequest request)
			throws ServletException, IOException {
		filtroAsignaturasGrupo = (FiltroBeanEvaluacionAsignaturaGrupo) session
				.getAttribute("filtroF");
		filtroAsignaturasGrupo.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeanAreaGrupo(HttpServletRequest request)
			throws ServletException, IOException {
		filtroAreasGrupo = (FiltroBeanEvaluacionAreaGrupo) session
				.getAttribute("filtroG");
		filtroAreasGrupo.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeanTipoDescriptor(HttpServletRequest request)
			throws ServletException, IOException {
		filtroTipoDescriptor = (FiltroBeanEvaluacionTipoDescriptor) session
				.getAttribute("filtroH");
		filtroTipoDescriptor.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansAsignatura(HttpServletRequest request)
			throws ServletException, IOException {
		filtroEvaluacionAsignatura = (FiltroBeanEvaluacionAsignatura) session
				.getAttribute("filtroC");
		filtroEvaluacionAsignatura.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansArea(HttpServletRequest request)
			throws ServletException, IOException {
		filtroEvaluacionArea = (FiltroBeanEvaluacionArea) session
				.getAttribute("filtroD");
		filtroEvaluacionArea.setInstitucion(login.getInstId());
		String sentencia = "";
		return true;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablasAsistencia(String usuarioid) {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3
					.getString("delete_reporte_asistencia"));
			pst.clearParameters();
			pst.setLong(1, Long.parseLong(usuarioid));
			pst.executeUpdate();
			pst.close();
			con.commit();
			// System.out.println("**SE LIMPIn ENCABEZADO_ASISTENCIA**");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablasLogros(String usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con.prepareCall("{call PK_ESTADISTICOS.logros_borrar(?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			// System.out.println("Inicia borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("Fin borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();
			// System.out.println("**SE LIMPIARON LAS TABLAS DE LOGROS**");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablasLogrosGrupo(String usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_ESTADISTICOS.logros_grupo_borrar(?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			// System.out.println("Inicia logros_grupo_borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("Fin logros_grupo_borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();
			// System.out.println("**SE LIMPIARON LAS TABLAS DE LOGROS_GRUPO**");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablasAsignaturas(String usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_ESTADISTICOS.asignaturas_borrar(?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			// System.out.println("Inicia borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("Fin borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();
			// System.out.println("**SE LIMPIARON LAS TABLAS DE ASIGNATURAS**");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablasAreas(String usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con.prepareCall("{call PK_ESTADISTICOS.areas_borrar(?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			// System.out.println("Inicia borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("Fin borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();
			// System.out.println("**SE LIMPIARON LAS TABLAS DE AREAS**");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public boolean updateReporteFecha(String preparedstatement, String modulo,
			String nombreboletin, String user, String estado) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(preparedstatement));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombreboletin));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
			con.commit();
			// System.out
			// .println("nnSe actualizn la fecha en la tabla Reporte!!!!");

		} catch (InternalErrorException e) {
			System.out.println("Error Interno: " + e.toString());
			return false;
		} catch (Exception e) {
			System.out.println("Error Exception: " + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes, y luego poder ser visualizado en <BR>
	 * la lista de reportes generados <BR>
	 * 
	 * @param byte bit
	 **/

	public boolean ponerArchivo(String modulo, String path, byte[] bit,
			String archivostatic, String context1) {

		Connection con = null;
		try {
			con = cursor.getConnection();
			// System.out.println("ENTRO A PONER EL ARCHIVO");
			String archivosalida = Ruta.get(context1,
					rb3.getString("reportes.PathReportesEstadisticos"));
			// System.out.println("archivosalida: " + archivosalida);
			File f = new File(archivosalida);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(f + File.separator
					+ archivostatic);
			CopyUtils.copy(bit, fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error poniendo archivo" + e);
			return false;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			System.out.println("Error:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public String cola_reportes(String prepared) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String cant = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();
			if (rs.next())
				cant = rs.getString(1);
			rs.close();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		// System.out.println("PUESTO: " + cant);
		return cant;
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public String cola_reportes_asignaturas(String tipo) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String p = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3
					.getString("puesto_del_reporte_asignatura"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(1, tipo);
			rs = pst.executeQuery();
			// System.out.println("nnSe ejecutn puesto_del_reporte!!!!");
			if (rs.next())
				p = rs.getString(1);
			rs.close();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		// System.out.println("PUESTO ASIGNATURA: " + p);
		return p;
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void updatePuestoDatos(String puesto, String nombrereportezip,
			String user, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (puesto));
			pst.setString(posicion++, (nombrereportezip));
			pst.setString(posicion++, (user));
			pst.executeUpdate();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void updatePuestoDatosEvaluaciones(String puesto,
			String nombrereportezip, String user, String prepared, String tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (puesto));
			pst.setString(posicion++, (nombrereportezip));
			pst.setString(posicion++, (user));
			pst.setString(posicion++, (tipo));
			pst.executeUpdate();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje = "  - " + s + "\n";
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void ir(int a, String s, HttpServletRequest rq,
			HttpServletResponse rs) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(rq, rs);
		else
			rd.forward(rq, rs);
	}

	/*
	 * Cierra cursor
	 */
	public void destroy() {
		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

}
