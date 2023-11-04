package siges.boletines;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.InstParVO;
import siges.boletines.beans.AlarmaBean;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.beans.FiltroBeanResumenAreas;
import siges.boletines.beans.FiltroBeanResumenAsignaturas;
import siges.boletines.beans.FiltroConsultaAsignaturasPerdidas;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.evaluacion.beans.FiltroBeanEvaluacion;
import siges.evaluacion.beans.ParamsVO;
import siges.evaluacion.beans.TipoEvalVO;
import siges.evaluacion.dao.EvaluacionDAO;
import siges.exceptions.InternalErrorException;
import siges.grupoPeriodo.beans.AdminVO;
import siges.grupoPeriodo.dao.AdminDAO;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;


/**
 * Nombre: ControllerResumenFiltroSave<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerResumenFiltroSave extends HttpServlet {

	private Cursor cursor;
	private Zip zip;
	private Login login;
	private HttpSession session;
	private String mensaje;
	private boolean err;
	private ResourceBundle rb3;
	private ResourceBundle rb4;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanResumenAreas filtroAreas;
	private FiltroBeanResumenAsignaturas filtroAsignaturas;
	private FiltroConsultaAsignaturasPerdidas filtroConsultaAisgnaturasPerdidas;
	private Util util;
	private String[] codigo;
	private String buscarcodigo;
	private static final String moduloArea = "22";
	private static final String moduloAsignatura = "23";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s;
	private String s1;
	private String ant;
	private String er;
	private String sig;
	private String sig2;
	private String sig3;
	private String home;
	private File reportFile_area;
	private File reportFile_asignatura;
	private String path;
	private Dao dao;
	private String vigencia;
	private int requerido;
	private String respuesta;
	private String alert;

	private ArrayList listalarmas;
	private ArrayList listaObservacion;

	private ReporteLogrosDAO reportesDAO;
	private EvaluacionDAO evaluacionDAO;
	private AdminDAO adminDAO;

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
		String contextoTotal ="";
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int count;
		rb3 = ResourceBundle.getBundle("siges.boletines.bundle.resumen");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		String nom;
		sig = "/boletines/GenerarResumenAreas.jsp";
		sig2 = "/boletines/GenerarResumenAsignaturas.jsp";
		sig3 = "/boletines/NuevoConsultaPerdidaAsignaturas.jsp";
		s = "/Reportes.do";
		s1 = "/boletines/ControllerResumenFiltroEdit.do";
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		session = request.getSession();
		util = new Util(cursor);
		reportesDAO = new ReporteLogrosDAO(cursor);
		evaluacionDAO = new EvaluacionDAO(cursor);
		adminDAO = new AdminDAO(cursor);

		err = false;
		respuesta = null;

		if (request.getParameter("reporte_solicitado") == null) {
			setMensaje("**Error al mandar generar el tipo de reporte solicitado**");
			request.setAttribute("mensaje", mensaje);
			ir(2, er, request, response);
			return er;
		}
		// System.out.println("REPORTE SOLICITADO: "
		// + request.getParameter("reporte_solicitado"));
		alert = "El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opci贸n de menu 'Reportes generados'";

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

			// nota de traer vugencia de login mejor...

			vigencia = dao.getVigencia();

			if (vigencia == null) {
				// System.out.println("*W* LA VIGENCIA ES NULA *W* ");
				return er;
			}
			// System.out.println("VIGENCIA_ DE DAO: "+vigencia);
			requerido = Integer.parseInt(request
					.getParameter("reporte_solicitado"));

			ServletContext context = request.getSession()
					.getServletContext();
			contextoTotal = context.getRealPath("/");
			String path = context.getRealPath("/boletines/reports");
			String path1 = context.getRealPath("/etc/img");
			String path2 = context.getRealPath("/private/escudo");
			reportFile_area = new File(path + File.separator
					+ rb3.getString("resumen_area"));
			reportFile_asignatura = new File(path + File.separator
					+ rb3.getString("resumen_asignatura"));

			switch (requerido) {
			case 1:
				if (!resumen_Areas(request, vigencia, contextoTotal))					
					return s;
				else{
					ReporteVO reporteVO = new ReporteVO();
					reporteVO.setRepTipo(ReporteVO._REP_RESUMENES_AREA);
					reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
					request.getSession().setAttribute("reporteVO", reporteVO);
					//Se redirecciona a boletines para proceder con la generaci髇					 
					ResumenArea rA = new ResumenArea(cursor,contextoTotal,path);	  
					rA.procesar_solicitudes();// se dispara el procesamiento de solicitudes
				}
					
				break;
			case 2:
				if (!resumen_Asignaturas(request, vigencia, contextoTotal)) {
					return s;
				}
				ReporteVO reporteVO2 = new ReporteVO();
				reporteVO2.setRepTipo(ReporteVO._REP_RESUMENES_ASIG);
				reporteVO2.setRepOrden(ReporteVO.ORDEN_DESC);
				request.getSession().setAttribute("reporteVO", reporteVO2);
				break;
			case 3:
				session.removeAttribute("filtroResultado");
				session.removeAttribute("rolususario");
				/*
				 * System.out.println("sede mantener " + (String)
				 * request.getParameter("sede"));
				 * System.out.println("sede mantener " + (String)
				 * request.getParameter("jornada"));
				 * System.out.println("sede mantener " + (String)
				 * request.getParameter("metodologia"));
				 * System.out.println("sede mantener " + (String)
				 * request.getParameter("grado"));
				 * System.out.println("sede mantener " + (String)
				 * request.getParameter("grupo"));
				 * System.out.println("sede mantener " + (String)
				 * request.getParameter("periodo"));
				 */
				request.setAttribute("sedeMantenerFil",
						request.getParameter("sede"));
				request.setAttribute("jornadaMantenerFil",
						request.getParameter("jornada"));
				request.setAttribute("metodologiaMantenerFil",
						request.getParameter("metodologia"));
				request.setAttribute("gradoMantenerFil",
						request.getParameter("grado"));
				request.setAttribute("grupoMantenerFil",
						request.getParameter("grupo"));
				request.setAttribute("periodoMantenerFil",
						request.getParameter("periodo"));
				Collection[] est = null;
				int opcionconsulta = 1;
				if (!resumen_ConsultaAsignaturas(request, vigencia,
						contextoTotal)) {
					return s1;
				}

				session.removeAttribute("alarma");

				filtroConsultaPerdidaAsignaturas(request);
				session.setAttribute("rolususario", login.getPerfil());
				// System.out.println(Integer
				// .parseInt(filtroConsultaAisgnaturasPerdidas
				// .getTipoconsulta()));
				switch (Integer.parseInt(filtroConsultaAisgnaturasPerdidas
						.getTipoconsulta())) {

				case 1:
					session.removeAttribute("listaalarmas");
					listalarmas = new ArrayList();
					listaObservacion = new ArrayList();
					est = obtenerConsultaEstudiantes(request, vigencia,
							contextoTotal);
					opcionconsulta = Integer
							.parseInt(filtroConsultaAisgnaturasPerdidas
									.getTipoconsulta());
					break;
				case 2:
					session.removeAttribute("listaalarmas");
					listalarmas = new ArrayList();
					listaObservacion = new ArrayList();
					est = obtenerConsultaEstudiantesxPeriodo(request, vigencia,
							contextoTotal);
					opcionconsulta = Integer
							.parseInt(filtroConsultaAisgnaturasPerdidas
									.getTipoconsulta());
					request.getSession().setAttribute("gradoconsulta",
							filtroConsultaAisgnaturasPerdidas.getGrado());
					break;
				case 3:
					session.removeAttribute("listaalarmas");
					listalarmas = new ArrayList();
					listaObservacion = new ArrayList();
					est = obtenerConsultaEstudiantesxPeriodoeinasistencia(
							request, vigencia, contextoTotal);
					opcionconsulta = Integer
							.parseInt(filtroConsultaAisgnaturasPerdidas
									.getTipoconsulta());
					request.getSession().setAttribute("gradoconsulta",
							filtroConsultaAisgnaturasPerdidas.getGrado());
					break;
				case 4:
					session.removeAttribute("listaalarmas");
					listalarmas = new ArrayList();
					listaObservacion = new ArrayList();
					ArrayList listagrados = (ArrayList) session
							.getAttribute("gradosconsultar");
					ArrayList metodologiasaconsultar = (ArrayList) session
							.getAttribute("metodologiasaconsultar");

					est = obtenerConsultaEstudiantesxPeriodo2(request,
							vigencia, contextoTotal, listagrados,
							metodologiasaconsultar);
					opcionconsulta = Integer
							.parseInt(filtroConsultaAisgnaturasPerdidas
									.getTipoconsulta());
					// request.getSession().setAttribute("gradoconsulta",
					// filtroConsultaAisgnaturasPerdidas.getGrado());
					break;
				case 5:
					session.removeAttribute("listaalarmas");
					listalarmas = new ArrayList();
					listaObservacion = new ArrayList();
					ArrayList listagrados2 = (ArrayList) session
							.getAttribute("gradosconsultar");
					ArrayList metodologiasaconsultar2 = (ArrayList) session
							.getAttribute("metodologiasaconsultar");

					est = obtenerConsultaEstudiantesxPeriodoeinasistencia2(
							request, vigencia, contextoTotal, listagrados2,
							metodologiasaconsultar2);
					opcionconsulta = Integer
							.parseInt(filtroConsultaAisgnaturasPerdidas
									.getTipoconsulta());
					break;
				// // caso para ejecucinn de alarmas
				case 6:

					ArrayList listaalarmasget = (ArrayList) session
							.getAttribute("listaalarmas");

					try {
						for (int i = 0; i < listaObservacion.size(); i++) {
							AlarmaBean a = (AlarmaBean) listaObservacion.get(i);
							// System.out.println(a.getAlar_nombreestudiante()
							// + " - " + a.getAlar_codest() + " - "
							// + a.getAlar_asi() + " - "
							// + a.getAlar_periodo());

							evaluacionDAO
									.guardarObservacionEstudiante((AlarmaBean) listaObservacion
											.get(i));

						}
						for (int i = 0; i < listaalarmasget.size(); i++) {
							AlarmaBean b = (AlarmaBean) listaalarmasget.get(i);
							// System.out.println(b.getAlar_nombreestudiante()
							// + " - " + b.getAlar_codest() + " - "
							// + b.getAlar_asi() + " - "
							// + b.getAlar_periodo());

							evaluacionDAO
									.insertarAlarma((AlarmaBean) listaalarmasget
											.get(i));

						}

						if (session.getAttribute("alarma") == null) {
							session.setAttribute("alarma",
									"Alarmas agregadas correctamente.");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						if (session.getAttribute("alarma") == null) {
							session.setAttribute("alarma", "Error alarmas:"
									+ ex.getMessage());
						}

					}

					break;

				}

				/* session.setAttribute("filtroResultado", est[0]); */
				/*
				 * session.setAttribute("filtroAsignaturaNom", est[2]);
				 * session.setAttribute("filtroAreaNom", est[3]);
				 * session.setAttribute("filtroGrupoNom", est[4]);
				 */
				// ReporteVO reporteVO3 = new ReporteVO();
				/*
				 * reporteVO3.setRepTipo(ReporteVO._REP_RESUMENES_ASIG);
				 * reporteVO3.setRepOrden(ReporteVO.ORDEN_DESC);
				 */
				// request.getSession().setAttribute("reporteVO", reporteVO3);

				request.getSession().setAttribute("filtroResultado", est);
				request.getSession().setAttribute("opcionseleccionada",
						String.valueOf(opcionconsulta));
				request.getSession().setAttribute("listaalarmas", listalarmas);
				s = "/boletines/NuevoConsultaPerdidaAsignaturas.jsp";
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

	public boolean resumen_Areas(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sede = null;
		String jornada = null;
		String met = null;
		String grado = null;
		String grupo = null;
		String periodo = null;
		String nom = null;
		String puesto = null;
		int posicion = 1;
		String archivosalida = null;
		int zise;
		String cont = null;
		String archivozip = null;
		String archivo = null;
		String archivoxls = null;
		String alert = "El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opci贸n de menu 'Reportes generados'";
		// System.out.println("RESUMEN AREAS: INSERTAR DATOS SLICITUD");
		try {
			con = cursor.getConnection();

			if (!asignarBeansAreas(request)) {
				setMensaje("Error capturando datos para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}

			pst = con.prepareStatement(rb3
					.getString("existe_mismo_reporte_en_cola"));
			posicion = 1;
			pst.clearParameters();
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			pst.setLong(posicion++, Long.parseLong(!filtroAreas.getSede()
					.equals("-9") ? filtroAreas.getSede() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtroAreas.getJornada()
					.equals("-9") ? filtroAreas.getJornada() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtroAreas
					.getMetodologia().equals("-9") ? filtroAreas
					.getMetodologia() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtroAreas.getGrado()
					.equals("-9") ? filtroAreas.getGrado() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtroAreas.getGrupo()
					.equals("-9") ? filtroAreas.getGrupo() : "-9"));
			pst.setLong(posicion++,
					Long.parseLong(filtroAreas.getPeriodo().trim()));
			pst.setLong(posicion++, dao.getVigenciaNumerico());
			pst.setString(posicion++, filtroAreas.getareassel());

			/*
			 * pst.setLong(posicion++,Long.parseLong(login.getInstId()));
			 * pst.setLong
			 * (posicion++,Long.parseLong(!filtroAreas.getSede().equals
			 * ("-9")?filtroAreas.getSede():"-9"));
			 * pst.setLong(posicion++,Long.parseLong
			 * (!filtroAreas.getJornada().equals
			 * ("-9")?filtroAreas.getJornada():"-9"));
			 * pst.setLong(posicion++,Long
			 * .parseLong(!filtroAreas.getMetodologia(
			 * ).equals("-9")?filtroAreas.getMetodologia():"-9"));
			 * pst.setLong(posicion
			 * ++,Long.parseLong(!filtroAreas.getGrado().equals
			 * ("-9")?filtroAreas.getGrado():"-9"));
			 * pst.setLong(posicion++,Long.
			 * parseLong(!filtroAreas.getGrupo().equals
			 * ("-9")?filtroAreas.getGrupo():"-9"));
			 * pst.setLong(posicion++,Long.
			 * parseLong(filtroAreas.getPeriodo().trim()));
			 */
			rs = pst.executeQuery();

			if (!rs.next()) {
				// System.out
				// .println("RESUMEN AREAS: ******nNO HAY NINGUNO CON LOS MISMOS PARAMETROS!...EN ESTADO CERO O -1!*********");
				rs.close();
				pst.close();
				sede = (!filtroAreas.getSede().equals("-9") ? "_Sed_"
						+ filtroAreas.getSede().trim() : "");
				jornada = (!filtroAreas.getJornada().equals("-9") ? "_Jor_"
						+ filtroAreas.getJornada().trim() : "");
				met = (!filtroAreas.getMetodologia().equals("-9") ? "_Met_"
						+ filtroAreas.getMetodologia()
						: "");
				grado = (!filtroAreas.getGrado().equals("-9") ? "_Gra_"
						+ filtroAreas.getGrado() : "");
				grupo = (!filtroAreas.getGrupo().equals("-9") ? "_Gru_"
						+ filtroAreas.getGrupo() : "");

				if (Long.parseLong(filtroAreas.getPeriodo().trim()) == 7) {
					filtroAreas.setPeriodonom("FINAL");
				}

				nom = sede
						+ jornada
						+ met
						+ grado
						+ grupo
						+ "_Periodo_"
						+ filtroAreas.getPeriodonom().trim()
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Resumen_Evaluacion_Areas_" + nom + ".pdf";
				archivoxls = "Resumen_Evaluacion_Areas_" + nom + ".xls";
				archivozip = "Resumen_Evaluacion_Areas_" + nom + ".zip";

				pst = con.prepareStatement(rb3.getString("insert_datos_area"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getInstId()));
				pst.setLong(posicion++, Long.parseLong(!filtroAreas.getSede()
						.equals("-9") ? filtroAreas.getSede() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtroAreas
						.getJornada().equals("-9") ? filtroAreas.getJornada()
						: "-9"));
				pst.setLong(
						posicion++,
						Long.parseLong(!filtroAreas.getMetodologia().equals(
								"-9") ? filtroAreas.getMetodologia() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtroAreas.getGrado()
						.equals("-9") ? filtroAreas.getGrado() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtroAreas.getGrupo()
						.equals("-9") ? filtroAreas.getGrupo() : "-9"));
				pst.setLong(posicion++,
						Long.parseLong(filtroAreas.getPeriodo().trim()));
				pst.setString(posicion++, !filtroAreas.getPeriodonom().trim()
						.equals("") ? filtroAreas.getPeriodonom().trim() : "");
				pst.setString(posicion++, "");

				pst.setString(posicion++, f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-'));
				pst.setString(posicion++, archivozip);
				pst.setString(posicion++, archivo);
				pst.setString(posicion++, "");
				pst.setLong(posicion++, Long.parseLong("-1"));
				pst.setString(posicion++, login.getUsuarioId());
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));

				// long consecRepBol= reportesDAO.getConsecReporte();
				// pst.setLong(posicion++,consecRepBol);
				pst.setString(posicion++, login.getInst());
				pst.setString(posicion++, login.getSede());
				pst.setString(posicion++, login.getJornada());
				pst.setLong(posicion++, dao.getVigenciaNumerico());

				String resol = reportesDAO.getResolInst(Long.parseLong(login
						.getInstId()));
				pst.setString(posicion++, resol);
				pst.setLong(posicion++, login.getLogNivelEval());
				pst.setLong(posicion++, login.getLogNumPer());
				pst.setString(posicion++, login.getLogNomPerDef());
				FiltroBeanReports filtroRep = new FiltroBeanReports();
				// filtroRep.set
				login.setSedeId(filtroAreas.getSede());
				login.setJornadaId(filtroAreas.getJornada());
				filtroRep.setMetodologia(filtroAreas.getMetodologia());
				filtroRep.setGrado(filtroAreas.getGrado());
				int tipoPrees = reportesDAO.getTipoEval(filtroRep, login);
				// System.out.println("RESUMENES: INSERT TIPOEVALPREES: "
				// + tipoPrees + "   NIVEL EVAL: "
				// + login.getLogNivelEval());
				pst.setInt(posicion++, tipoPrees);
				// pst.setInt(posicion++,1);
				pst.setInt(posicion++, -9);
				pst.setString(posicion++, filtroAreas.getareassel());
				if (login.getPerfil().equals("410")
						|| login.getPerfil().equals("421")) {
					pst.setString(posicion++, "1");
				} else {
					pst.setString(posicion++, "0");
				}
				// System.out.println("areas seleccionadas "+filtroAreas.getareassel()+"****************************************");
				pst.executeUpdate();
				// System.out
				// .println("RESUMEN AREAS: Se insertn en datos_boletin!!!!");

				con.commit();
				// System.out.println("nnSe insertn en DATOS_RESUMEN_AREA!!!!");
				pst.close();
				con.commit();

				ponerReporte(moduloArea, login.getUsuarioId(),
						rb3.getString("reportes.PathReportes") + archivozip
								+ "", "zip", "" + archivozip, "-1",
						"ReporteInsertarEstado");// Estado -1
				// System.out
				// .println("Se insertn el ZIP en Reporte con estado -1");
				// siges.util.Logger.print(login.getUsuarioId(),"Peticinn de Resumen_Area:_Institucion:_"+login.getInstId()+"_Usuario:_"+login.getUsuarioId()+"_NombreReporte:_"+archivozip+"",3,1,this.toString());
				cont = cola_reportes("puesto_del_reporte_area");

				if (cont != null && !cont.equals(""))
					updatePuestoReporte(cont, archivozip, login.getUsuarioId(),
							"update_puesto_reporte_area");

				return true;
			} else {
				rs.close();
				pst.close();
				setMensaje("nYa existe una peticinn de este reporte con los mismos parnmetros!");
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("mensaje2",
						"nYa existe una peticinn de este reporte con los mismos parnmetros!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloArea, login.getUsuarioId(),
					rb3.getString("reportes.PathReportes") + archivozip + "",
					"zip22", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en FiltroSave");
			return false;
		} finally {
			try {
				if (cursor != null)
					cursor.cerrar();
				if (util != null)
					util.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public boolean resumen_ConsultaAsignaturas(HttpServletRequest request,
			String vigencia, String contextoTotal) throws ServletException,
			IOException {
		Connection con = null;
		PreparedStatement pst = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sede = null;
		String jornada = null;
		String met = null;
		String grado = null;
		String grupo = null;
		String periodo = null;
		String nom = null;
		String puesto = null;
		int posicion = 1;
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String cont = null;
		String archivozip = null;
		String archivo = null;
		String archivoxls = null;
		String alert = "El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opci贸n de menu 'Reportes generados'";

		try {
			con = cursor.getConnection();

			if (!asignarBeansConsultaPerdiaAsignaturas(request)) {
				setMensaje("Error capturando datos para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			return true;

			/*
			 * pst = con.prepareStatement(rb3
			 * .getString("existe_mismo_reporte_as_en_cola")); posicion = 1;
			 * pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			 * pst.setLong(posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas.getSede()
			 * .equals("-9") ? filtroConsultaAisgnaturasPerdidas.getSede() :
			 * "-9")); pst.setLong(posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getJornada().equals("-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getJornada() : "-9"));
			 * pst.setLong( posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getMetodologia().equals( "-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getMetodologia() : "-9"));
			 * pst.setLong(posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getGrado().equals("-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getGrado() : "-9"));
			 * pst.setLong(posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getGrupo().equals("-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getGrupo() : "-9"));
			 * pst.setLong(posicion++,
			 * Long.parseLong(filtroConsultaAisgnaturasPerdidas
			 * .getPeriodo().trim())); pst.setLong(posicion++,
			 * dao.getVigenciaNumerico()); pst.setString(posicion++,
			 * filtroConsultaAisgnaturasPerdidas.getareassel());
			 * pst.setString(posicion++,
			 * filtroConsultaAisgnaturasPerdidas.getasigsel()); rs =
			 * pst.executeQuery();
			 * 
			 * if (!rs.next()) { // System.out // .println(
			 * "******nNO HAY NINGUNO CON LOS MISMOS PARAMETROS!...EN ESTADO CERO O -1!*********"
			 * ); rs.close(); pst.close(); sede =
			 * (!filtroConsultaAisgnaturasPerdidas.getSede().equals("-9") ?
			 * "_Sede_" + filtroConsultaAisgnaturasPerdidas.getSede().trim() :
			 * ""); jornada =
			 * (!filtroConsultaAisgnaturasPerdidas.getJornada().equals("-9") ?
			 * "_Jornada_"
			 * +filtroConsultaAisgnaturasPerdidas.getJornada().trim() : ""); met
			 * =
			 * (!filtroConsultaAisgnaturasPerdidas.getMetodologia().equals("-9")
			 * ? "_Metodologia_" +
			 * filtroConsultaAisgnaturasPerdidas.getMetodologia() : ""); grado =
			 * (!filtroConsultaAisgnaturasPerdidas.getGrado().equals("-9") ?
			 * "_Grado_" + filtroConsultaAisgnaturasPerdidas.getGrado() : "");
			 * grupo =
			 * (!filtroConsultaAisgnaturasPerdidas.getGrupo().equals("-9") ?
			 * "_Grupo_" + filtroConsultaAisgnaturasPerdidas.getGrupo() : "");
			 * 
			 * if
			 * (Long.parseLong(filtroConsultaAisgnaturasPerdidas.getPeriodo().
			 * trim()) == 7) {
			 * filtroConsultaAisgnaturasPerdidas.setPeriodonom("FINAL"); }
			 * 
			 * nom = sede + jornada + met + grado + grupo + "_Periodo_" +
			 * filtroConsultaAisgnaturasPerdidas.getPeriodonom().trim() +
			 * "_Fecha_" + f2.toString().replace(' ', '_').replace(':', '-')
			 * .replace('.', '-'); archivo = "Consulta_Asignaturas_Perdidas_" +
			 * nom + ".pdf"; archivoxls = "Consulta_Asignaturas_Perdidas_" + nom
			 * + ".xls"; archivozip = "Consulta_Asignaturas_Perdidas_" + nom +
			 * ".zip";
			 * 
			 * posicion = 1; pst = con.prepareStatement(rb3.getString(
			 * "insert_datos_asig_consulta_perdida")); posicion = 1;
			 * pst.clearParameters(); pst.setLong(posicion++,
			 * Long.parseLong(login.getInstId())); pst.setLong(posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getSede().equals("-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getSede() : "-9"));
			 * pst.setLong( posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getJornada().equals( "-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getJornada() : "-9"));
			 * pst.setLong(posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getMetodologia().equals("-9") ?
			 * filtroConsultaAisgnaturasPerdidas .getMetodologia() : "-9"));
			 * pst.setLong( posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getGrado().equals( "-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getGrado() : "-9"));
			 * pst.setLong( posicion++,
			 * Long.parseLong(!filtroConsultaAisgnaturasPerdidas
			 * .getGrupo().equals( "-9") ?
			 * filtroConsultaAisgnaturasPerdidas.getGrupo() : "-9"));
			 * pst.setLong(posicion++,
			 * Long.parseLong(filtroConsultaAisgnaturasPerdidas
			 * .getPeriodo().trim())); pst.setString(posicion++,
			 * !filtroConsultaAisgnaturasPerdidas.getPeriodonom()
			 * .trim().equals("") ?
			 * filtroConsultaAisgnaturasPerdidas.getPeriodonom() .trim() : "");
			 * pst.setString(posicion++, "");
			 * 
			 * pst.setString(posicion++, f2.toString().replace(' ', '_')
			 * .replace(':', '-').replace('.', '-')); pst.setString(posicion++,
			 * archivozip); pst.setString(posicion++, archivo);
			 * pst.setString(posicion++, ""); pst.setLong(posicion++,
			 * Long.parseLong("-1")); pst.setString(posicion++,
			 * login.getUsuarioId()); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1")); pst.setLong(posicion++,
			 * Long.parseLong("1"));
			 * 
			 * // long consecRepBol= reportesDAO.getConsecReporte(); //
			 * pst.setLong(posicion++,consecRepBol); pst.setString(posicion++,
			 * login.getInst()); pst.setString(posicion++, login.getSede());
			 * pst.setString(posicion++, login.getJornada());
			 * pst.setLong(posicion++, dao.getVigenciaNumerico());
			 * 
			 * String resol = reportesDAO.getResolInst(Long.parseLong(login
			 * .getInstId())); pst.setString(posicion++, resol);
			 * pst.setLong(posicion++, login.getLogNivelEval());
			 * pst.setLong(posicion++, login.getLogNumPer());
			 * pst.setString(posicion++, login.getLogNomPerDef());
			 * FiltroBeanReports filtroRep = new FiltroBeanReports(); //
			 * filtroRep.set
			 * login.setSedeId(filtroConsultaAisgnaturasPerdidas.getSede());
			 * login
			 * .setJornadaId(filtroConsultaAisgnaturasPerdidas.getJornada());
			 * filtroRep
			 * .setMetodologia(filtroConsultaAisgnaturasPerdidas.getMetodologia
			 * ());
			 * filtroRep.setGrado(filtroConsultaAisgnaturasPerdidas.getGrado());
			 * int tipoPrees = reportesDAO.getTipoEval(filtroRep, login); //
			 * System.out.println("RESUMENES: INSERT TIPOEVALPREES: " // +
			 * tipoPrees + "   NIVEL EVAL: " // + login.getLogNivelEval());
			 * pst.setInt(posicion++, tipoPrees); // pst.setInt(posicion++,1);
			 * pst.setInt(posicion++, -9); pst.setString(posicion++,
			 * filtroConsultaAisgnaturasPerdidas.getareassel());
			 * pst.setString(posicion++,
			 * filtroConsultaAisgnaturasPerdidas.getasigsel()); if
			 * (login.getPerfil().equals("410") ||
			 * login.getPerfil().equals("421")) { pst.setString(posicion++,
			 * "1"); } else { pst.setString(posicion++, "0"); }
			 * pst.executeUpdate(); // System.out //
			 * .println("RESUMEN ASIG: Se insertn en datos_boletin!!!!"); //
			 * System
			 * .out.println("nnSe insertn en DATOS_RESUMEN_ASIGNATURA!!!!");
			 * pst.close(); con.commit();
			 * 
			 * ponerReporte(moduloAsignatura, login.getUsuarioId(),
			 * rb3.getString("reportes.PathReportes") + archivozip + "", "zip",
			 * "" + archivozip, "-1", "ReporteInsertarEstado");// Estado -1 //
			 * System.out //
			 * .println("Se insertn el ZIP en Reporte con estado -1");
			 * siges.util.Logger.print( login.getUsuarioId(),
			 * "Peticinn de Consulta_Asignaturas_Perdidas_:_Institucion:_" +
			 * login.getInstId() + "_Usuario:_" + login.getUsuarioId() +
			 * "_NombreReporte:_" + archivozip + "", 3, 1, this.toString());
			 * cont =
			 * cola_reportes("puesto_del_reporte_consulta_Asignaturas_Perdidas"
			 * );
			 * 
			 * if (cont != null && !cont.equals("")) updatePuestoReporte(cont,
			 * archivozip, login.getUsuarioId(),
			 * "update_puesto_reporte_consulta_Asignaturas_Perdidas"); } else {
			 * rs.close(); pst.close(); setMensaje(
			 * "nYa existe una peticinn de este reporte con los mismos parnmetros!"
			 * ); request.setAttribute("mensaje", mensaje); return false; }
			 * 
			 * } catch (Exception e) { e.printStackTrace();
			 * ponerReporteMensaje("2", moduloAsignatura, login.getUsuarioId(),
			 * rb3.getString("reportes.PathReportes") + archivozip + "", "zip",
			 * "" + archivozip, "ReporteActualizarBoletin",
			 * "Ocurrin excepcinn en FiltroSave"); return false; } finally { try
			 * { if (cursor != null) cursor.cerrar(); if (util != null)
			 * util.cerrar(); OperacionesGenerales.closeResultSet(rs);
			 * OperacionesGenerales.closeStatement(pst);
			 * OperacionesGenerales.closeCallableStatement(cstmt);
			 * OperacionesGenerales.closeConnection(con); } catch (Exception e)
			 * { } } return true;
			 * 
			 * }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Lista de estudiantes por grado que estan perdiendo las asignaturas opc 2
	 * 
	 * @param request
	 * @param vigencia
	 * @param contextoTotal
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public Collection[] obtenerConsultaEstudiantesxPeriodo(
			HttpServletRequest request, String vigencia, String contextoTotal)
			throws ServletException, IOException {

		List colperiodos = null;

		Collection[] totalEstudiantes = new Collection[15];

		TipoEvalVO tipoEval;

		try {
			tipoEval = evaluacionDAO.getTipoEval(
					filtroConsultaAisgnaturasPerdidas.getMetodologia(),
					filtroConsultaAisgnaturasPerdidas.getGrado(), login);
			session.setAttribute("tipoEvalAprobMin",
					new Double(tipoEval.getTipoEvalAprobMin()));
			session.setAttribute("tipoEval",
					new Long(tipoEval.getCod_tipo_eval()));

			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
				session.setAttribute("filtroNota2", evaluacionDAO
						.getEscalaConceptual(filtroConsultaAisgnaturasPerdidas
								.getMetodologia(),
								filtroConsultaAisgnaturasPerdidas.getGrado(),
								login));

			}

			FiltroBeanEvaluacion f = new FiltroBeanEvaluacion();

			f.setInstitucion(filtroConsultaAisgnaturasPerdidas.getInsitucion());
			f.setGrado(filtroConsultaAisgnaturasPerdidas.getGrado());
			f.setSede(filtroConsultaAisgnaturasPerdidas.getSede());
			f.setJornada(filtroConsultaAisgnaturasPerdidas.getJornada());
			f.setMetodologia(filtroConsultaAisgnaturasPerdidas.getMetodologia());
			f.setPeriodo(filtroConsultaAisgnaturasPerdidas.getPeriodo());
			f.setVigencia(vigencia);
			Collection colgrupos = (Collection) request
					.getAttribute("filtroGrupoF");
			int contadorgruposanadidios = 0;
			ArrayList listadocoordinadores = evaluacionDAO
					.getCoordinadores(login);
			for (Iterator it = colgrupos.iterator(); it.hasNext();) {

				Object[] element = (Object[]) it.next();
				if (f.getSede().equals(element[2])
						&& f.getJornada().equals(element[3])
						&& f.getGrado().equals(element[4])
						&& f.getMetodologia().equals(element[5])) {
					f.setGrupo((String) element[0]);

					f.setOrden("0");
					Collection listagrupos = new ArrayList();
					String per = login.getPerfil();
					// System.out.println("nombre perfil "+per);
					Collection colgg[];
					if (per.equals("422")) {
						if (request.getAttribute("FiltroAreas") == null
								&& request.getAttribute("FiltroAsignaturas") == null
								&& request.getAttribute("roldocasig") == null) {
							// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
							colgg = util.getfiltroasdoc(login.getInstId(),
									Long.toString(login.getVigencia_inst()),
									login.getUsuarioId());
							request.setAttribute("FiltroAreas", colgg[0]);
							request.setAttribute("FiltroAsignaturas", colgg[1]);
							request.setAttribute("roldocasig", "1");
						} else {
							colgg = new Collection[3];
							colgg[0] = (Collection) request
									.getAttribute("FiltroAreas");
							colgg[1] = (Collection) request
									.getAttribute("FiltroAsignaturas");
						}
					} else {
						if (request.getAttribute("FiltroAreas") != null
								&& request.getAttribute("FiltroAsignaturas") != null
								&& request.getAttribute("roldocasig") != null) {
							colgg = util.getfiltroas(login.getInstId(),
									Long.toString(login.getVigencia_inst()));
							request.setAttribute("FiltroAreas", colgg[0]);
							request.setAttribute("FiltroAsignaturas", colgg[1]);
							request.setAttribute("roldocasig", "0");
						} else {
							colgg = new Collection[3];
							colgg[0] = (Collection) request
									.getAttribute("FiltroAreas");
							colgg[1] = (Collection) request
									.getAttribute("FiltroAsignaturas");
						}
					}

					String asignaturatmp = "";
					String areatmp = "";

					for (Iterator it3 = colgg[1].iterator(); it3.hasNext();) {
						boolean agregarlista = false;
						Collection listaAsignaturas = new ArrayList();
						Object[] element3 = (Object[]) it3.next();

						if (((String) element3[3]).equals(f.getMetodologia())) {
							agregarlista = true;
							f.setAsignatura((String) element3[2] + "|"
									+ (String) element3[1]);
							asignaturatmp = (String) element3[2];

							for (Iterator it2 = colgg[0].iterator(); it2
									.hasNext();) {
								Object[] element2 = (Object[]) it2.next();

								if (((String) element2[0])
										.equals(element3[0])
										&& ((String) element2[2])
												.equals(element3[3])) {
									areatmp = (String) element2[1];
									break;
								}

							}
							AdminVO av = new AdminVO();
							av.setAdminInst(f.getInstitucion());
							av = adminDAO.getAdminHorario(av);

							ArrayList listadocenetsasignatura = null;
							if (av.getAdminHorario().trim().equals("1")) {
								listadocenetsasignatura = evaluacionDAO
										.getDocenteDictaAsignatura(f);
							} else {
								listadocenetsasignatura = evaluacionDAO
										.getDocenteInstJornSed(f);
							}

							Collection[] col = new Collection[6];

							colperiodos = (List) request
									.getAttribute("filtroPeriodoF");

							col[0] = evaluacionDAO
									.getEstudiantesAsignaturaxPeriodoConsultaperdida(
											f, tipoEval.getCod_tipo_eval(),
											tipoEval.getTipoEvalAprobMin());
							// ,
							// tipoEval.getCod_tipo_eval(),colperiodos.size(),tipoEval.getTipoEvalAprobMin()
							Collection c = new ArrayList();
							/*
							 * String[] d1 = new String[1]; d1[0]=asignaturatmp;
							 */
							c.add(asignaturatmp);
							col[2] = c;

							Collection c2 = new ArrayList();
							/*
							 * String[] d2 = new String[1]; d2[0]=areatmp;
							 */
							c2.add(areatmp);
							col[3] = c2;

							Collection c3 = new ArrayList();
							/*
							 * String[] d3 = new String[1]; d3[0]=f.getGrupo();
							 */
							c3.add(f.getGrupo());
							col[4] = c3;

							boolean agregarlistado = false;
							for (Iterator it4 = col[0].iterator(); it4
									.hasNext();) {
								Object[] element2 = (Object[]) it4.next();
								if (element2[0] != null
										|| !((String) element2[0]).equals("")) {
									agregarlistado = true;
								}
								if (agregarlistado) {

									AlarmaBean ala = new AlarmaBean();
									ala.setAlar_area(areatmp);
									ala.setAlar_asi(asignaturatmp);
									ala.setAlar_grupo(f.getGrupo());

									ala.setAlar_estado("0");
									ala.setAlar_grado(f.getGrado());
									ala.setAlar_motivobajorendimiento("X");
									ala.setAlar_nombreestudiante(String
											.valueOf(element2[2])
											+ " "
											+ String.valueOf(element2[3])
											+ " "
											+ String.valueOf(element2[4])
											+ " "
											+ String.valueOf(element2[5]));
									ala.setAlar_grujerar(f.getJerarquiagrupo());
									ala.setAlar_vigencia(f.getVigencia());

									ala.setAlar_periodo(f.getPeriodo());
									ala.setAlar_codest(String
											.valueOf(element2[0]));
									listaObservacion.add(ala);

									if (listadocenetsasignatura != null) {
										for (int i = 0; i < listadocenetsasignatura
												.size(); i++) {
											// System.out.println("Doc "
											// + listadocenetsasignatura
											// .get(i));
											AlarmaBean alarma = new AlarmaBean();
											alarma.setAlar_area(areatmp);
											alarma.setAlar_asi(asignaturatmp);
											alarma.setAlar_grupo(f.getGrupo());
											alarma.setAlar_docdocente(String
													.valueOf(listadocenetsasignatura
															.get(i)));
											alarma.setAlar_estado("0");
											alarma.setAlar_grado(f.getGrado());
											alarma.setAlar_motivobajorendimiento("X");
											alarma.setAlar_nombreestudiante(String
													.valueOf(element2[2])
													+ " "
													+ String.valueOf(element2[3])
													+ " "
													+ String.valueOf(element2[4])
													+ " "
													+ String.valueOf(element2[5]));
											alarma.setAlar_grujerar(f
													.getJerarquiagrupo());
											alarma.setAlar_vigencia(f
													.getVigencia());

											alarma.setAlar_periodo(f
													.getPeriodo());
											alarma.setAlar_codest(String
													.valueOf(element2[0]));
											listalarmas.add(alarma);
										}

										for (int i = 0; i < listadocoordinadores
												.size(); i++) {
											// System.out.println("Coor "
											// + listadocoordinadores
											// .get(i));
											AlarmaBean alarma = new AlarmaBean();
											alarma.setAlar_area(areatmp);
											alarma.setAlar_asi(asignaturatmp);
											alarma.setAlar_grupo(f.getGrupo());
											alarma.setAlar_docdocente(String
													.valueOf(listadocoordinadores
															.get(i)));
											alarma.setAlar_estado("0");
											alarma.setAlar_grado(f.getGrado());
											alarma.setAlar_motivobajorendimiento("X");
											alarma.setAlar_nombreestudiante(String
													.valueOf(element2[2])
													+ " "
													+ String.valueOf(element2[3])
													+ " "
													+ String.valueOf(element2[4])
													+ " "
													+ String.valueOf(element2[5]));
											alarma.setAlar_grujerar(f
													.getJerarquiagrupo());
											alarma.setAlar_vigencia(f
													.getVigencia());

											alarma.setAlar_periodo(f
													.getPeriodo());
											alarma.setAlar_codest(String
													.valueOf(element2[0]));
											listalarmas.add(alarma);

										}

									}
								}
							}

							if (agregarlistado) {
								listaAsignaturas.add(col);
							}
						}

						boolean agregarlistadoasig = false;
						for (Iterator it4 = listaAsignaturas.iterator(); it4
								.hasNext();) {
							Object[] element2 = (Object[]) it4.next();
							if (element2[0] != null
									|| !((String) element2[0]).equals("")) {
								agregarlistadoasig = true;
							}
						}

						if (agregarlista && agregarlistadoasig) {
							listaAsignaturas.removeAll(Collections
									.singletonList(null));
							listagrupos.add(listaAsignaturas);
						}
						// cierre metodologia asignatura
					}
					listagrupos.removeAll(Collections.singletonList(null));
					totalEstudiantes[contadorgruposanadidios] = listagrupos;
					contadorgruposanadidios = contadorgruposanadidios + 1;
					// cierre asignaturas
				}

				// cierre grupos

			}

			// session.setAttribute("colperiodos",
			// String.valueOf(colperiodos.size()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalEstudiantes;
	}

	/**
	 * opc 3
	 * 
	 * @param request
	 * @param vigencia
	 * @param contextoTotal
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws InternalErrorException
	 */
	public Collection[] obtenerConsultaEstudiantesxPeriodoeinasistencia(
			HttpServletRequest request, String vigencia, String contextoTotal)
			throws ServletException, IOException, InternalErrorException {
		List colperiodos = null;
		ArrayList listadocoordinadores = evaluacionDAO.getCoordinadores(login);
		Collection[] totalEstudiantes = new Collection[100];

		TipoEvalVO tipoEval;

		try {
			tipoEval = evaluacionDAO.getTipoEval(
					filtroConsultaAisgnaturasPerdidas.getMetodologia(),
					filtroConsultaAisgnaturasPerdidas.getGrado(), login);
			session.setAttribute("tipoEvalAprobMin",
					new Double(tipoEval.getTipoEvalAprobMin()));
			session.setAttribute("tipoEval",
					new Long(tipoEval.getCod_tipo_eval()));

			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
				session.setAttribute("filtroNota2", evaluacionDAO
						.getEscalaConceptual(filtroConsultaAisgnaturasPerdidas
								.getMetodologia(),
								filtroConsultaAisgnaturasPerdidas.getGrado(),
								login));

			}

			FiltroBeanEvaluacion f = new FiltroBeanEvaluacion();

			f.setInstitucion(filtroConsultaAisgnaturasPerdidas.getInsitucion());
			f.setGrado(filtroConsultaAisgnaturasPerdidas.getGrado());
			f.setSede(filtroConsultaAisgnaturasPerdidas.getSede());
			f.setJornada(filtroConsultaAisgnaturasPerdidas.getJornada());
			f.setMetodologia(filtroConsultaAisgnaturasPerdidas.getMetodologia());
			f.setPeriodo(filtroConsultaAisgnaturasPerdidas.getPeriodo());
			f.setVigencia(vigencia);

			Collection colgrupos = (Collection) request
					.getAttribute("filtroGrupoF");
			int contadorgruposanadidios = 0;

			// System.out.println("nombre perfil "+per);
			Collection colgg[];
			String per = login.getPerfil();
			if (per.equals("422")) {
				if (request.getAttribute("FiltroAreas") == null
						&& request.getAttribute("FiltroAsignaturas") == null
						&& request.getAttribute("roldocasig") == null) {
					// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
					colgg = util.getfiltroasdoc(login.getInstId(),
							Long.toString(login.getVigencia_inst()),
							login.getUsuarioId());
					request.setAttribute("FiltroAreas", colgg[0]);
					request.setAttribute("FiltroAsignaturas", colgg[1]);
					request.setAttribute("roldocasig", "1");
				} else {
					colgg = new Collection[3];
					colgg[0] = (Collection) request.getAttribute("FiltroAreas");
					colgg[1] = (Collection) request
							.getAttribute("FiltroAsignaturas");
				}
			} else {
				if (request.getAttribute("FiltroAreas") != null
						&& request.getAttribute("FiltroAsignaturas") != null
						&& request.getAttribute("roldocasig") != null) {
					colgg = util.getfiltroas(login.getInstId(),
							Long.toString(login.getVigencia_inst()));
					request.setAttribute("FiltroAreas", colgg[0]);
					request.setAttribute("FiltroAsignaturas", colgg[1]);
					request.setAttribute("roldocasig", "0");
				} else {
					colgg = new Collection[3];
					colgg[0] = (Collection) request.getAttribute("FiltroAreas");
					colgg[1] = (Collection) request
							.getAttribute("FiltroAsignaturas");
				}
			}

			ArrayList idasignaturas = new ArrayList();
			ArrayList asignaturasrefarea = new ArrayList();
			ArrayList areas = new ArrayList();

			for (Iterator it3 = colgg[1].iterator(); it3.hasNext();) {
				boolean agregarlista = false;
				/* Collection listaAsignaturas=new ArrayList(); */
				Object[] element3 = (Object[]) it3.next();

				if (((String) element3[3]).equals(f.getMetodologia())) {
					agregarlista = true;
					// f.setAsignatura((String)element3[2]+"|"+(String)element3[1]);
					idasignaturas
							.add(((String) element3[2] + "|" + (String) element3[1]));
					Object[] objasig = new Object[3];
					objasig[0] = element3[2];
					objasig[1] = element3[1];

					for (Iterator it2 = colgg[0].iterator(); it2.hasNext();) {
						Object[] element2 = (Object[]) it2.next();

						if (((String) element2[0]).equals(element3[0])
								&& ((String) element2[2])
										.equals(element3[3])) {
							Object[] obj = new Object[2];
							objasig[2] = element2[1];
							obj[0] = element2[2];
							obj[1] = element2[1];
							areas.add(obj);
							asignaturasrefarea.add(objasig);
							break;
						}

					}

				}
			}
			int[] ihasignaturas = consultartotalasistenciasporasignatura(
					request, login, f, idasignaturas);

			ArrayList listadocenetsasignatura = null;

			for (Iterator it = colgrupos.iterator(); it.hasNext();) {

				Collection listagrupos = new ArrayList();
				Object[] element = (Object[]) it.next();
				if (f.getSede().equals(element[2])
						&& f.getJornada().equals(element[3])
						&& f.getGrado().equals(element[4])
						&& f.getMetodologia().equals(element[5])) {
					f.setGrupo((String) element[0]);

					f.setOrden("0");

					Collection[] col = new Collection[6];

					colperiodos = (List) request.getAttribute("filtroPeriodoF");

					col[0] = evaluacionDAO
							.getEstudiantesAsignaturaxPeriodoConsultaperdidaxInasistencia(
									f, tipoEval.getCod_tipo_eval(),
									ihasignaturas, idasignaturas, true);

					Collection c0 = new ArrayList();
					c0.add(f.getGrupo());
					col[1] = c0;

					// ,
					// tipoEval.getCod_tipo_eval(),colperiodos.size(),tipoEval.getTipoEvalAprobMin()
					Collection c = new ArrayList();
					/*
					 * String[] d1 = new String[1]; d1[0]=asignaturatmp;
					 */
					c.add(asignaturasrefarea);
					col[2] = c;

					Collection c2 = new ArrayList();
					/*
					 * String[] d2 = new String[1]; d2[0]=areatmp;
					 */
					c2.add(ihasignaturas);
					col[3] = c2;

					Collection c3 = new ArrayList();
					/*
					 * String[] d3 = new String[1]; d3[0]=f.getGrupo();
					 */
					c3.add(f.getGrupo());
					col[4] = c3;

					boolean agregarlistado = false;
					try {
						if (col[0] != null) {
							for (Iterator it4 = col[0].iterator(); it4
									.hasNext();) {
								Object[] element2 = (Object[]) it4.next();

								// for (int i = 0; i < element2.length; i++) {
								// System.out.println(element2[i]);
								// }

								if (element2[0] != null
										|| !((String) element2[0]).trim()
												.equals("")) {
									agregarlistado = true;
								}

								if (agregarlistado) {

									AlarmaBean ala = new AlarmaBean();

									ala.setAlar_grupo(f.getGrupo());
									ala.setAlar_estado("0");
									ala.setAlar_grado(f.getGrado());
									ala.setAlar_motivoinasistencia("X");
									ala.setAlar_nombreestudiante(String
											.valueOf(element2[2])
											+ " "
											+ String.valueOf(element2[3])
											+ " "
											+ String.valueOf(element2[4])
											+ " "
											+ String.valueOf(element2[5]));
									ala.setAlar_grujerar(f.getJerarquiagrupo());
									ala.setAlar_vigencia(f.getVigencia());

									ala.setAlar_periodo(f.getPeriodo());
									ala.setAlar_codest(String
											.valueOf(element2[0]));
									listaObservacion.add(ala);

									for (int k = 0; k < idasignaturas.size(); k++) {
										if (Integer
												.parseInt((String) element2[k + 6]) > ihasignaturas[k]
												&& ihasignaturas[k] != 0) {

											Object[] objasig = (Object[]) asignaturasrefarea
													.get(k);

											f.setAsignatura(String
													.valueOf(idasignaturas
															.get(k)));

											AdminVO av = new AdminVO();
											av.setAdminInst(f.getInstitucion());
											av = adminDAO.getAdminHorario(av);

											if (av.getAdminHorario().trim()
													.equals("1")) {
												listadocenetsasignatura = evaluacionDAO
														.getDocenteDictaAsignatura(f);
											} else {
												listadocenetsasignatura = evaluacionDAO
														.getDocenteInstJornSed(f);
											}

											if (agregarlistado
													&& listadocenetsasignatura != null) {

												for (int i = 0; i < listadocenetsasignatura
														.size(); i++) {
													AlarmaBean alarma = new AlarmaBean();
													alarma.setAlar_area((String) objasig[2]);
													alarma.setAlar_asi((String) objasig[0]);
													alarma.setAlar_grupo(f
															.getGrupo());
													alarma.setAlar_docdocente(String
															.valueOf(listadocenetsasignatura
																	.get(i)));
													alarma.setAlar_estado("0");
													alarma.setAlar_grado(f
															.getGrado());
													alarma.setAlar_motivoinasistencia("X");
													alarma.setAlar_nombreestudiante(String
															.valueOf(element2[2])
															+ " "
															+ String.valueOf(element2[3])
															+ " "
															+ String.valueOf(element2[4])
															+ " "
															+ String.valueOf(element2[5]));
													alarma.setAlar_grujerar(f
															.getJerarquiagrupo());
													alarma.setAlar_vigencia(f
															.getVigencia());

													alarma.setAlar_periodo(f
															.getPeriodo());
													alarma.setAlar_codest(String
															.valueOf(element2[0]));
													listalarmas.add(alarma);
												}
												for (int i = 0; i < listadocoordinadores
														.size(); i++) {
													AlarmaBean alarma = new AlarmaBean();
													alarma.setAlar_area((String) objasig[2]);
													alarma.setAlar_asi((String) objasig[0]);
													alarma.setAlar_grupo(f
															.getGrupo());
													alarma.setAlar_docdocente(String
															.valueOf(listadocoordinadores
																	.get(i)));
													alarma.setAlar_estado("0");
													alarma.setAlar_grado(f
															.getGrado());
													alarma.setAlar_motivoinasistencia("X");
													alarma.setAlar_nombreestudiante(String
															.valueOf(element2[2])
															+ " "
															+ String.valueOf(element2[3])
															+ " "
															+ String.valueOf(element2[4])
															+ " "
															+ String.valueOf(element2[5]));
													alarma.setAlar_grujerar(f
															.getJerarquiagrupo());
													alarma.setAlar_vigencia(f
															.getVigencia());

													alarma.setAlar_periodo(f
															.getPeriodo());
													alarma.setAlar_codest(String
															.valueOf(element2[0]));
													listalarmas.add(alarma);

												}

											}

										}

									}

								}

							}

							if (agregarlistado) {
								// listaAsignaturas.add(col);
								listagrupos.add(col);
							}
						}
					} catch (NullPointerException ex) {
						ex.printStackTrace();
					}

					listagrupos.removeAll(Collections.singletonList(null));

					if (listagrupos.size() > 0) {
						totalEstudiantes[contadorgruposanadidios] = listagrupos;
						contadorgruposanadidios = contadorgruposanadidios + 1;
					}

				}

				/*
				 * boolean agregarlistadoasig=false; for(Iterator
				 * it4=listaAsignaturas.iterator();it4.hasNext();){ Object[]
				 * element2=(Object[]) it4.next(); if(element2[0]!=null ||
				 * !((String)element2[0]).equals("")) { agregarlistadoasig=true;
				 * } }
				 */

				/*
				 * if(agregarlista && agregarlistadoasig){
				 * listaAsignaturas.removeAll(Collections.singletonList(null));
				 * listagrupos.add(listaAsignaturas); } //cierre metodologia
				 * asignatura }
				 */

				// cierre asignaturas
			}

			// cierre grupos

			// session.setAttribute("colperiodos",
			// String.valueOf(colperiodos.size()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalEstudiantes;
	}

	/**
	 * Lista de estudiantes que pierden asignatura por diferentes grados de la
	 * misma sede y jornada. opc 4 viende del CU-0034
	 * 
	 * @param request
	 * @param vigencia
	 * @param contextoTotal
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws InternalErrorException
	 */
	public Collection[] obtenerConsultaEstudiantesxPeriodo2(
			HttpServletRequest request, String vigencia, String contextoTotal,
			ArrayList grados, ArrayList metodologia) throws ServletException,
			IOException, InternalErrorException {
		ArrayList listadocoordinadores = evaluacionDAO.getCoordinadores(login);
		List colperiodos = null;

		Collection[] totalGrados = new Collection[grados.size()];
		Collection totalMetodologia = new ArrayList();

		Collection totalEstudiantes = new ArrayList();

		TipoEvalVO tipoEval;

		try {
			tipoEval = evaluacionDAO.getTipoEval(
					filtroConsultaAisgnaturasPerdidas.getMetodologia(),
					filtroConsultaAisgnaturasPerdidas.getGrado(), login);
			session.setAttribute("tipoEvalAprobMin",
					new Double(tipoEval.getTipoEvalAprobMin()));
			session.setAttribute("tipoEval",
					new Long(tipoEval.getCod_tipo_eval()));

			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
				session.setAttribute("filtroNota2", evaluacionDAO
						.getEscalaConceptual(filtroConsultaAisgnaturasPerdidas
								.getMetodologia(),
								filtroConsultaAisgnaturasPerdidas.getGrado(),
								login));

			}
			int contadorgruposanadidios = 0;
			for (int w = 0; w < grados.size(); w++) {
				FiltroBeanEvaluacion f = new FiltroBeanEvaluacion();
				f.setGrado((String) grados.get(w));

				for (int z = 0; z < metodologia.size(); z++) {
					f.setMetodologia((String) metodologia.get(z));

					f.setInstitucion(filtroConsultaAisgnaturasPerdidas
							.getInsitucion());
					f.setSede(filtroConsultaAisgnaturasPerdidas.getSede());
					f.setJornada(filtroConsultaAisgnaturasPerdidas.getJornada());
					f.setPeriodo(filtroConsultaAisgnaturasPerdidas.getPeriodo());
					f.setVigencia(vigencia);
					Collection colgrupos = (Collection) request
							.getAttribute("filtroGrupoF");

					for (Iterator it = colgrupos.iterator(); it.hasNext();) {

						Object[] element = (Object[]) it.next();
						if (f.getSede().equals(element[2])
								&& f.getJornada().equals(element[3])
								&& f.getGrado().equals(element[4])
								&& f.getMetodologia().equals(element[5])) {
							f.setGrupo((String) element[0]);

							f.setOrden("0");
							Collection listagrupos = new ArrayList();
							String per = login.getPerfil();
							// System.out.println("nombre perfil "+per);
							Collection colgg[];
							if (per.equals("422")) {
								if (request.getAttribute("FiltroAreas") == null
										&& request
												.getAttribute("FiltroAsignaturas") == null
										&& request.getAttribute("roldocasig") == null) {
									// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
									colgg = util.getfiltroasdoc(login
											.getInstId(), Long.toString(login
											.getVigencia_inst()), login
											.getUsuarioId());
									request.setAttribute("FiltroAreas",
											colgg[0]);
									request.setAttribute("FiltroAsignaturas",
											colgg[1]);
									request.setAttribute("roldocasig", "1");
								} else {
									colgg = new Collection[3];
									colgg[0] = (Collection) request
											.getAttribute("FiltroAreas");
									colgg[1] = (Collection) request
											.getAttribute("FiltroAsignaturas");
								}
							} else {
								if (request.getAttribute("FiltroAreas") != null
										&& request
												.getAttribute("FiltroAsignaturas") != null
										&& request.getAttribute("roldocasig") != null) {
									colgg = util.getfiltroas(login.getInstId(),
											Long.toString(login
													.getVigencia_inst()));
									request.setAttribute("FiltroAreas",
											colgg[0]);
									request.setAttribute("FiltroAsignaturas",
											colgg[1]);
									request.setAttribute("roldocasig", "0");
								} else {
									colgg = new Collection[3];
									colgg[0] = (Collection) request
											.getAttribute("FiltroAreas");
									colgg[1] = (Collection) request
											.getAttribute("FiltroAsignaturas");
								}
							}

							String asignaturatmp = "";
							String areatmp = "";

							for (Iterator it3 = colgg[1].iterator(); it3
									.hasNext();) {
								boolean agregarlista = false;
								Collection listaAsignaturas = new ArrayList();
								Object[] element3 = (Object[]) it3.next();

								if (((String) element3[3]).equals(f
										.getMetodologia())) {
									agregarlista = true;
									f.setAsignatura((String) element3[2] + "|"
											+ (String) element3[1]);
									asignaturatmp = (String) element3[2];

									for (Iterator it2 = colgg[0].iterator(); it2
											.hasNext();) {
										Object[] element2 = (Object[]) it2
												.next();

										if (((String) element2[0])
												.equals(element3[0])
												&& ((String) element2[2])
														.equals(element3[3])) {
											areatmp = (String) element2[1];
											break;
										}

									}
									AdminVO av = new AdminVO();
									av.setAdminInst(f.getInstitucion());
									av = adminDAO.getAdminHorario(av);

									ArrayList listadocenetsasignatura = null;
									if (av.getAdminHorario().trim().equals("1")) {
										listadocenetsasignatura = evaluacionDAO
												.getDocenteDictaAsignatura(f);
									} else {
										listadocenetsasignatura = evaluacionDAO
												.getDocenteInstJornSed(f);
									}

									Collection[] col = new Collection[7];

									colperiodos = (List) request
											.getAttribute("filtroPeriodoF");

									col[0] = evaluacionDAO
											.getEstudiantesAsignaturaxPeriodoConsultaperdida(
													f,
													tipoEval.getCod_tipo_eval(),
													tipoEval.getTipoEvalAprobMin());
									// ,
									// tipoEval.getCod_tipo_eval(),colperiodos.size(),tipoEval.getTipoEvalAprobMin()
									Collection c = new ArrayList();
									/*
									 * String[] d1 = new String[1];
									 * d1[0]=asignaturatmp;
									 */
									c.add(asignaturatmp);
									col[2] = c;

									Collection c2 = new ArrayList();
									/*
									 * String[] d2 = new String[1];
									 * d2[0]=areatmp;
									 */
									c2.add(areatmp);
									col[3] = c2;

									Collection c3 = new ArrayList();
									/*
									 * String[] d3 = new String[1];
									 * d3[0]=f.getGrupo();
									 */
									c3.add(f.getGrupo());
									col[4] = c3;

									Collection c4 = new ArrayList();
									c4.add(grados.get(w));
									col[5] = c4;

									boolean agregarlistado = false;
									for (Iterator it4 = col[0].iterator(); it4
											.hasNext();) {
										Object[] element2 = (Object[]) it4
												.next();
										if (element2[0] != null
												|| !((String) element2[0])
														.equals("")) {
											agregarlistado = true;
										}
										if (agregarlistado) {

											AlarmaBean ala = new AlarmaBean();
											ala.setAlar_area(areatmp);
											ala.setAlar_asi(asignaturatmp);
											ala.setAlar_grupo(f.getGrupo());
											ala.setAlar_estado("0");
											ala.setAlar_grado(f.getGrado());
											ala.setAlar_motivobajorendimiento("X");
											ala.setAlar_nombreestudiante(String
													.valueOf(element2[2])
													+ " "
													+ String.valueOf(element2[3])
													+ " "
													+ String.valueOf(element2[4])
													+ " "
													+ String.valueOf(element2[5]));
											ala.setAlar_grujerar(f
													.getJerarquiagrupo());
											ala.setAlar_vigencia(f
													.getVigencia());

											ala.setAlar_periodo(f.getPeriodo());
											ala.setAlar_codest(String
													.valueOf(element2[0]));
											listaObservacion.add(ala);

											if (listadocenetsasignatura != null) {
												for (int i = 0; i < listadocenetsasignatura
														.size(); i++) {

													AlarmaBean alarma = new AlarmaBean();
													alarma.setAlar_area(areatmp);
													alarma.setAlar_asi(asignaturatmp);
													alarma.setAlar_grupo(f
															.getGrupo());
													alarma.setAlar_docdocente(String
															.valueOf(listadocenetsasignatura
																	.get(i)));
													alarma.setAlar_estado("0");
													alarma.setAlar_grado(f
															.getGrado());
													alarma.setAlar_motivobajorendimiento("X");
													alarma.setAlar_nombreestudiante(String
															.valueOf(element2[2])
															+ " "
															+ String.valueOf(element2[3])
															+ " "
															+ String.valueOf(element2[4])
															+ " "
															+ String.valueOf(element2[5]));
													alarma.setAlar_grujerar(f
															.getJerarquiagrupo());
													alarma.setAlar_vigencia(f
															.getVigencia());

													alarma.setAlar_periodo(f
															.getPeriodo());
													alarma.setAlar_codest(String
															.valueOf(element2[0]));

													listalarmas.add(alarma);
												}
												for (int i = 0; i < listadocoordinadores
														.size(); i++) {
													AlarmaBean alarma = new AlarmaBean();
													alarma.setAlar_area(areatmp);
													alarma.setAlar_asi(asignaturatmp);
													alarma.setAlar_grupo(f
															.getGrupo());
													alarma.setAlar_docdocente(String
															.valueOf(listadocoordinadores
																	.get(i)));
													alarma.setAlar_estado("0");
													alarma.setAlar_grado(f
															.getGrado());
													alarma.setAlar_motivoinasistencia("X");
													alarma.setAlar_nombreestudiante(String
															.valueOf(element2[2])
															+ " "
															+ String.valueOf(element2[3])
															+ " "
															+ String.valueOf(element2[4])
															+ " "
															+ String.valueOf(element2[5]));
													alarma.setAlar_grujerar(f
															.getJerarquiagrupo());
													alarma.setAlar_vigencia(f
															.getVigencia());

													alarma.setAlar_periodo(f
															.getPeriodo());
													alarma.setAlar_codest(String
															.valueOf(element2[0]));
													listalarmas.add(alarma);

												}
											}
										}
									}

									if (agregarlistado) {
										listaAsignaturas.add(col);
									}
								}

								boolean agregarlistadoasig = false;
								for (Iterator it4 = listaAsignaturas.iterator(); it4
										.hasNext();) {
									Object[] element2 = (Object[]) it4.next();
									if (element2[0] != null
											|| !((String) element2[0])
													.equals("")) {
										agregarlistadoasig = true;
									}
								}

								if (agregarlista && agregarlistadoasig) {
									listaAsignaturas.removeAll(Collections
											.singletonList(null));
									listagrupos.add(listaAsignaturas);
								}
								// cierre metodologia asignatura
							}
							listagrupos.removeAll(Collections
									.singletonList(null));
							totalEstudiantes.add(listagrupos);

							// cierre asignaturas
						}

						// cierre grupos

					}

					// session.setAttribute("colperiodos",
					// String.valueOf(colperiodos.size()));
					totalMetodologia.add(totalEstudiantes);
				}
				// cierre for metodologia
			}
			totalGrados[contadorgruposanadidios] = totalMetodologia;
			contadorgruposanadidios = contadorgruposanadidios + 1;
			// cierre for grado
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalGrados;
	}

	/**
	 * Metodo que ejecua consulta estudiantes que piernen asignatura por
	 * inasistencia del CU-0034 cuando viende del modulo Abrir/cerrar periodo
	 * opc 5
	 * 
	 * @param request
	 * @param vigencia
	 * @param contextoTotal
	 * @param grados
	 * @param metodologia
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws InternalErrorException
	 */
	public Collection[] obtenerConsultaEstudiantesxPeriodoeinasistencia2(
			HttpServletRequest request, String vigencia, String contextoTotal,
			ArrayList grados, ArrayList metodologia) throws ServletException,
			IOException, InternalErrorException {
		Collection totalEstudiantescompleto = new ArrayList();
		ArrayList listadocoordinadores = evaluacionDAO.getCoordinadores(login);
		List colperiodos = null;

		Collection[] totalGrados = new Collection[grados.size()];
		Collection totalMetodologia = new ArrayList();

		TipoEvalVO tipoEval;

		try {
			tipoEval = evaluacionDAO.getTipoEval(
					filtroConsultaAisgnaturasPerdidas.getMetodologia(),
					filtroConsultaAisgnaturasPerdidas.getGrado(), login);
			session.setAttribute("tipoEvalAprobMin",
					new Double(tipoEval.getTipoEvalAprobMin()));
			session.setAttribute("tipoEval",
					new Long(tipoEval.getCod_tipo_eval()));

			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
				session.setAttribute("filtroNota2", evaluacionDAO
						.getEscalaConceptual(filtroConsultaAisgnaturasPerdidas
								.getMetodologia(),
								filtroConsultaAisgnaturasPerdidas.getGrado(),
								login));

			}
			int contadorgruposanadidios = 0;
			for (int w = 0; w < grados.size(); w++) {
				boolean agregargruposagrado = false;
				FiltroBeanEvaluacion f = new FiltroBeanEvaluacion();
				f.setGrado((String) grados.get(w));

				for (int z = 0; z < metodologia.size(); z++) {
					f.setMetodologia((String) metodologia.get(z));

					f.setInstitucion(filtroConsultaAisgnaturasPerdidas
							.getInsitucion());
					f.setSede(filtroConsultaAisgnaturasPerdidas.getSede());
					f.setJornada(filtroConsultaAisgnaturasPerdidas.getJornada());
					f.setPeriodo(filtroConsultaAisgnaturasPerdidas.getPeriodo());
					f.setVigencia(vigencia);
					Collection colgrupos = (Collection) request
							.getAttribute("filtroGrupoF");

					// System.out.println("nombre perfil "+per);
					Collection colgg[];
					String per = login.getPerfil();

					if (per.equals("422")) {
						if (request.getAttribute("FiltroAreas") == null
								&& request.getAttribute("FiltroAsignaturas") == null
								&& request.getAttribute("roldocasig") == null) {
							// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
							colgg = util.getfiltroasdoc(login.getInstId(),
									Long.toString(login.getVigencia_inst()),
									login.getUsuarioId());
							request.setAttribute("FiltroAreas", colgg[0]);
							request.setAttribute("FiltroAsignaturas", colgg[1]);
							request.setAttribute("roldocasig", "1");
						} else {
							colgg = new Collection[3];
							colgg[0] = (Collection) request
									.getAttribute("FiltroAreas");
							colgg[1] = (Collection) request
									.getAttribute("FiltroAsignaturas");
						}
					} else {
						if (request.getAttribute("FiltroAreas") != null
								&& request.getAttribute("FiltroAsignaturas") != null
								&& request.getAttribute("roldocasig") != null) {
							colgg = util.getfiltroas(login.getInstId(),
									Long.toString(login.getVigencia_inst()));
							request.setAttribute("FiltroAreas", colgg[0]);
							request.setAttribute("FiltroAsignaturas", colgg[1]);
							request.setAttribute("roldocasig", "0");
						} else {
							colgg = new Collection[3];
							colgg[0] = (Collection) request
									.getAttribute("FiltroAreas");
							colgg[1] = (Collection) request
									.getAttribute("FiltroAsignaturas");
						}
					}

					ArrayList idasignaturas = new ArrayList();
					ArrayList asignaturasrefarea = new ArrayList();
					ArrayList areas = new ArrayList();

					for (Iterator it3 = colgg[1].iterator(); it3.hasNext();) {
						boolean agregarlista = false;
						/* Collection listaAsignaturas=new ArrayList(); */
						Object[] element3 = (Object[]) it3.next();

						if (((String) element3[3]).equals(f.getMetodologia())) {
							agregarlista = true;
							// f.setAsignatura((String)element3[2]+"|"+(String)element3[1]);
							idasignaturas
									.add(((String) element3[2] + "|" + (String) element3[1]));
							Object[] objasig = new Object[3];
							objasig[0] = element3[2];
							objasig[1] = element3[1];

							for (Iterator it2 = colgg[0].iterator(); it2
									.hasNext();) {
								Object[] element2 = (Object[]) it2.next();

								if (((String) element2[0])
										.equals(element3[0])
										&& ((String) element2[2])
												.equals(element3[3])) {
									Object[] obj = new Object[2];
									objasig[2] = element2[1];
									obj[0] = element2[2];
									obj[1] = element2[1];
									areas.add(obj);
									asignaturasrefarea.add(objasig);
									break;
								}

							}

						}
					}
					ArrayList listadocenetsasignatura = null;
					int[] ihasignaturas = consultartotalasistenciasporasignatura(
							request, login, f, idasignaturas);

					for (Iterator it = colgrupos.iterator(); it.hasNext();) {

						Object[] element = (Object[]) it.next();
						if (f.getSede().equals(element[2])
								&& f.getJornada().equals(element[3])
								&& f.getGrado().equals(element[4])
								&& f.getMetodologia().equals(element[5])) {
							f.setGrupo((String) element[0]);

							f.setOrden("0");
							Collection listagrupos = new ArrayList();

							Collection[] col = new Collection[7];

							colperiodos = (List) request
									.getAttribute("filtroPeriodoF");

							col[0] = evaluacionDAO
									.getEstudiantesAsignaturaxPeriodoConsultaperdidaxInasistencia(
											f, tipoEval.getCod_tipo_eval(),
											ihasignaturas, idasignaturas, true);
							// ,
							// tipoEval.getCod_tipo_eval(),colperiodos.size(),tipoEval.getTipoEvalAprobMin()

							Collection c0 = new ArrayList();
							c0.add(f.getGrupo());
							col[1] = c0;

							Collection c = new ArrayList();
							/*
							 * String[] d1 = new String[1]; d1[0]=asignaturatmp;
							 */
							c.add(asignaturasrefarea);
							col[2] = c;

							Collection c2 = new ArrayList();
							/*
							 * String[] d2 = new String[1]; d2[0]=areatmp;
							 */
							c2.add(ihasignaturas);
							col[3] = c2;

							Collection c4 = new ArrayList();
							c4.add(grados.get(w));
							col[5] = c4;

							boolean agregarlistado = false;
							try {
								if (col[0] != null) {
									for (Iterator it4 = col[0].iterator(); it4
											.hasNext();) {
										Object[] element2 = (Object[]) it4
												.next();
										if (element2[0] != null
												|| !((String) element2[0])
														.equals("")) {
											agregarlistado = true;
										}

										if (agregarlistado) {

											AlarmaBean ala = new AlarmaBean();

											ala.setAlar_grupo(f.getGrupo());
											ala.setAlar_estado("0");
											ala.setAlar_grado(f.getGrado());
											ala.setAlar_motivoinasistencia("X");
											ala.setAlar_nombreestudiante(String
													.valueOf(element2[2])
													+ " "
													+ String.valueOf(element2[3])
													+ " "
													+ String.valueOf(element2[4])
													+ " "
													+ String.valueOf(element2[5]));
											ala.setAlar_grujerar(f
													.getJerarquiagrupo());
											ala.setAlar_vigencia(f
													.getVigencia());

											ala.setAlar_periodo(f.getPeriodo());
											ala.setAlar_codest(String
													.valueOf(element2[0]));
											listaObservacion.add(ala);

											for (int k = 0; k < idasignaturas
													.size(); k++) {
												if (Integer
														.parseInt((String) element2[k + 6]) > ihasignaturas[k]
														&& ihasignaturas[k] != 0) {

													Object[] objasig = (Object[]) asignaturasrefarea
															.get(k);

													f.setAsignatura(String
															.valueOf(idasignaturas
																	.get(k)));

													AdminVO av = new AdminVO();
													av.setAdminInst(f
															.getInstitucion());
													av = adminDAO
															.getAdminHorario(av);

													if (av.getAdminHorario()
															.trim().equals("1")) {
														listadocenetsasignatura = evaluacionDAO
																.getDocenteDictaAsignatura(f);
													} else {
														listadocenetsasignatura = evaluacionDAO
																.getDocenteInstJornSed(f);
													}

													if (agregarlistado
															&& listadocenetsasignatura != null) {
														for (int i = 0; i < listadocenetsasignatura
																.size(); i++) {
															AlarmaBean alarma = new AlarmaBean();
															alarma.setAlar_area((String) objasig[2]);
															alarma.setAlar_asi((String) objasig[0]);
															alarma.setAlar_grupo(f
																	.getGrupo());
															alarma.setAlar_docdocente(String
																	.valueOf(listadocenetsasignatura
																			.get(i)));
															alarma.setAlar_estado("0");
															alarma.setAlar_grado(f
																	.getGrado());
															alarma.setAlar_motivoinasistencia("X");
															alarma.setAlar_nombreestudiante(String
																	.valueOf(element2[2])
																	+ " "
																	+ String.valueOf(element2[3])
																	+ " "
																	+ String.valueOf(element2[4])
																	+ " "
																	+ String.valueOf(element2[5]));
															alarma.setAlar_grujerar(f
																	.getJerarquiagrupo());
															alarma.setAlar_vigencia(f
																	.getVigencia());

															alarma.setAlar_periodo(f
																	.getPeriodo());
															alarma.setAlar_codest(String
																	.valueOf(element2[0]));
															listalarmas
																	.add(alarma);
														}
														for (int i = 0; i < listadocoordinadores
																.size(); i++) {
															AlarmaBean alarma = new AlarmaBean();
															alarma.setAlar_area((String) objasig[2]);
															alarma.setAlar_asi((String) objasig[0]);
															alarma.setAlar_grupo(f
																	.getGrupo());
															alarma.setAlar_docdocente(String
																	.valueOf(listadocoordinadores
																			.get(i)));
															alarma.setAlar_estado("0");
															alarma.setAlar_grado(f
																	.getGrado());
															alarma.setAlar_motivoinasistencia("X");
															alarma.setAlar_nombreestudiante(String
																	.valueOf(element2[2])
																	+ " "
																	+ String.valueOf(element2[3])
																	+ " "
																	+ String.valueOf(element2[4])
																	+ " "
																	+ String.valueOf(element2[5]));
															alarma.setAlar_grujerar(f
																	.getJerarquiagrupo());
															alarma.setAlar_vigencia(f
																	.getVigencia());

															alarma.setAlar_periodo(f
																	.getPeriodo());
															alarma.setAlar_codest(String
																	.valueOf(element2[0]));
															listalarmas
																	.add(alarma);

														}
													}

												}

											}

										}

									}

									if (agregarlistado) {
										// listaAsignaturas.add(col);
										listagrupos.add(col);
									}
								}
							} catch (NullPointerException ex) {
								ex.printStackTrace();
							}

							listagrupos.removeAll(Collections
									.singletonList(null));

							if (listagrupos.size() > 0) {
								totalEstudiantescompleto.add(listagrupos);
								contadorgruposanadidios = contadorgruposanadidios + 1;
								agregargruposagrado = true;
							}

						}

					}
				}
				if (agregargruposagrado) {
					totalGrados[contadorgruposanadidios] = totalEstudiantescompleto;
					contadorgruposanadidios = contadorgruposanadidios + 1;
				}
			}

			// session.setAttribute("colperiodos",
			// String.valueOf(colperiodos.size()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalGrados;
	}

	/**
	 * Metodo para consultar estudiantes que estan perdiendo asignatura por bajo
	 * rendimiento opc 1
	 * 
	 * @param request
	 * @param vigencia
	 * @param contextoTotal
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws InternalErrorException
	 */
	public Collection[] obtenerConsultaEstudiantes(HttpServletRequest request,
			String vigencia, String contextoTotal) throws ServletException,
			IOException, InternalErrorException {

		List colperiodos = null;
		ArrayList listadocoordinadores = evaluacionDAO.getCoordinadores(login);
		Collection[] totalEstudiantes = new Collection[15];

		TipoEvalVO tipoEval;

		try {
			tipoEval = evaluacionDAO.getTipoEval(
					filtroConsultaAisgnaturasPerdidas.getMetodologia(),
					filtroConsultaAisgnaturasPerdidas.getGrado(), login);
			session.setAttribute("tipoEvalAprobMin",
					new Double(tipoEval.getTipoEvalAprobMin()));
			session.setAttribute("tipoEval",
					new Long(tipoEval.getCod_tipo_eval()));

			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
				session.setAttribute("filtroNota2", evaluacionDAO
						.getEscalaConceptual(filtroConsultaAisgnaturasPerdidas
								.getMetodologia(),
								filtroConsultaAisgnaturasPerdidas.getGrado(),
								login));

			}

			FiltroBeanEvaluacion f = new FiltroBeanEvaluacion();

			f.setInstitucion(filtroConsultaAisgnaturasPerdidas.getInsitucion());
			f.setGrado(filtroConsultaAisgnaturasPerdidas.getGrado());
			f.setSede(filtroConsultaAisgnaturasPerdidas.getSede());
			f.setJornada(filtroConsultaAisgnaturasPerdidas.getJornada());
			f.setMetodologia(filtroConsultaAisgnaturasPerdidas.getMetodologia());

			f.setVigencia(vigencia);
			Collection colgrupos = (Collection) request
					.getAttribute("filtroGrupoF");
			int contadorgruposanadidios = 0;

			String per = login.getPerfil();
			// System.out.println("nombre perfil "+per);
			Collection colgg[];
			if (per.equals("422")) {
				if (request.getAttribute("FiltroAreas") == null
						&& request.getAttribute("FiltroAsignaturas") == null
						&& request.getAttribute("roldocasig") == null) {
					// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
					colgg = util.getfiltroasdoc(login.getInstId(),
							Long.toString(login.getVigencia_inst()),
							login.getUsuarioId());
					request.setAttribute("FiltroAreas", colgg[0]);
					request.setAttribute("FiltroAsignaturas", colgg[1]);
					request.setAttribute("roldocasig", "1");
				} else {
					colgg = new Collection[3];
					colgg[0] = (Collection) request.getAttribute("FiltroAreas");
					colgg[1] = (Collection) request
							.getAttribute("FiltroAsignaturas");
				}
			} else {
				if (request.getAttribute("FiltroAreas") != null
						&& request.getAttribute("FiltroAsignaturas") != null
						&& request.getAttribute("roldocasig") != null) {
					colgg = util.getfiltroas(login.getInstId(),
							Long.toString(login.getVigencia_inst()));
					request.setAttribute("FiltroAreas", colgg[0]);
					request.setAttribute("FiltroAsignaturas", colgg[1]);
					request.setAttribute("roldocasig", "0");
				} else {
					colgg = new Collection[3];
					colgg[0] = (Collection) request.getAttribute("FiltroAreas");
					colgg[1] = (Collection) request
							.getAttribute("FiltroAsignaturas");
				}
			}

			for (Iterator it = colgrupos.iterator(); it.hasNext();) {

				Object[] element = (Object[]) it.next();
				if (f.getSede().equals(element[2])
						&& f.getJornada().equals(element[3])
						&& f.getGrado().equals(element[4])
						&& f.getMetodologia().equals(element[5])) {
					f.setGrupo((String) element[0]);

					f.setOrden("0");
					Collection listagrupos = new ArrayList();

					String asignaturatmp = "";
					String areatmp = "";
					String idasig = "";

					for (Iterator it3 = colgg[1].iterator(); it3.hasNext();) {
						boolean agregarlista = false;
						Collection listaAsignaturas = new ArrayList();
						Object[] element3 = (Object[]) it3.next();

						if (((String) element3[3]).equals(f.getMetodologia())) {
							agregarlista = true;
							f.setAsignatura((String) element3[2] + "|"
									+ (String) element3[1]);
							asignaturatmp = (String) element3[2];

							AdminVO av = new AdminVO();
							av.setAdminInst(f.getInstitucion());
							av = adminDAO.getAdminHorario(av);

							ArrayList listadocenetsasignatura = null;

							if (av.getAdminHorario().trim().equals("1")) {
								listadocenetsasignatura = evaluacionDAO
										.getDocenteDictaAsignatura(f);
							} else {
								listadocenetsasignatura = evaluacionDAO
										.getDocenteInstJornSed(f);
							}

							for (Iterator it2 = colgg[0].iterator(); it2
									.hasNext();) {
								Object[] element2 = (Object[]) it2.next();

								if (((String) element2[0])
										.equals(element3[0])
										&& ((String) element2[2])
												.equals(element3[3])) {
									areatmp = (String) element2[1];
									break;
								}

							}

							Collection[] col = new Collection[6];

							colperiodos = (List) request
									.getAttribute("filtroPeriodoF");

							col[0] = evaluacionDAO
									.getEstudiantesConsultaPierdenAsignatura(f,
											tipoEval.getCod_tipo_eval(),
											colperiodos.size(),
											tipoEval.getTipoEvalAprobMin());

							Collection c = new ArrayList();
							/*
							 * String[] d1 = new String[1]; d1[0]=asignaturatmp;
							 */
							c.add(asignaturatmp);
							col[2] = c;

							Collection c2 = new ArrayList();
							/*
							 * String[] d2 = new String[1]; d2[0]=areatmp;
							 */
							c2.add(areatmp);
							col[3] = c2;

							Collection c3 = new ArrayList();
							/*
							 * String[] d3 = new String[1]; d3[0]=f.getGrupo();
							 */
							c3.add(f.getGrupo());
							col[4] = c3;

							Collection c4 = new ArrayList();
							/*
							 * String[] d4 = new String[1]; d4[0]=
							 * String.valueOf(colperiodos.size());
							 */
							c4.add(String.valueOf(colperiodos.size()));
							col[5] = c4;

							boolean agregarlistado = false;
							for (Iterator it4 = col[0].iterator(); it4
									.hasNext();) {
								Object[] element2 = (Object[]) it4.next();

								int periodo = 1;
								int contador = 0;
								int w = 6;
								while (w < element2.length) {
									if (!element2[w].equals("NR")
											&& Double
													.parseDouble((element2[w] + "")
															.replace(',', '.')) < tipoEval
													.getTipoEvalAprobMin()) {

										periodo = (w / 2) - 2;

										contador = contador + 1;

									}
									w = w + 2;
								}

								if (element2[0] != null
										&& !((String) element2[0]).equals("")
										&& contador >= 2 && periodo > 1) {

									agregarlistado = true;

									// System.out.println("* "
									// + String.valueOf(element2[2]) + " "
									// + String.valueOf(element2[3]) + " "
									// + String.valueOf(element2[4]) + " "
									// + String.valueOf(element2[5])
									// + " - " + asignaturatmp + " - "
									// + periodo);
								} else {
									agregarlistado = false;
								}
								if (agregarlistado) {

									// System.out.println("* "
									// + String.valueOf(element2[2]) + " "
									// + String.valueOf(element2[3]) + " "
									// + String.valueOf(element2[4]) + " "
									// + String.valueOf(element2[5])
									// + " - " + asignaturatmp + " - "
									// + listadocenetsasignatura.size());
									AlarmaBean ala = new AlarmaBean();
									ala.setAlar_area(areatmp);
									ala.setAlar_asi(asignaturatmp);
									ala.setAlar_grupo(f.getGrupo());
									ala.setAlar_estado("0");
									ala.setAlar_grado(f.getGrado());
									ala.setAlar_motivobajorendimiento("X");
									ala.setAlar_nombreestudiante(String
											.valueOf(element2[2])
											+ " "
											+ String.valueOf(element2[3])
											+ " "
											+ String.valueOf(element2[4])
											+ " "
											+ String.valueOf(element2[5]));
									ala.setAlar_grujerar(f.getJerarquiagrupo());
									ala.setAlar_vigencia(f.getVigencia());

									ala.setAlar_periodo(periodo + "");
									ala.setAlar_codest(String
											.valueOf(element2[0]));
									if (periodo > 1 && contador >= 2) {
										listaObservacion.add(ala);
									}

									if (listadocenetsasignatura != null) {

										// System.out
										// .println(listadocenetsasignatura
										// .size());

										for (int i = 0; i < listadocenetsasignatura
												.size(); i++) {
											// System.out
											// .println(listadocenetsasignatura
											// .get(i));

											AlarmaBean alarma = new AlarmaBean();
											alarma.setAlar_area(areatmp);
											alarma.setAlar_asi(asignaturatmp);
											alarma.setAlar_grupo(f.getGrupo());
											alarma.setAlar_docdocente(String
													.valueOf(listadocenetsasignatura
															.get(i)));

											alarma.setAlar_estado("0");
											alarma.setAlar_grado(f.getGrado());
											alarma.setAlar_motivobajorendimiento("X");
											alarma.setAlar_nombreestudiante(String
													.valueOf(element2[2])
													+ " "
													+ String.valueOf(element2[3])
													+ " "
													+ String.valueOf(element2[4])
													+ " "
													+ String.valueOf(element2[5]));
											alarma.setAlar_grujerar(f
													.getJerarquiagrupo());
											alarma.setAlar_vigencia(f
													.getVigencia());

											alarma.setAlar_periodo(periodo + "");
											alarma.setAlar_codest(String
													.valueOf(element2[0]));
											if (periodo > 1 && contador >= 2) {
												listalarmas.add(alarma);
											}
										}

										for (int i = 0; i < listadocoordinadores
												.size(); i++) {
											AlarmaBean alarma = new AlarmaBean();
											alarma.setAlar_area(areatmp);
											alarma.setAlar_asi(asignaturatmp);
											alarma.setAlar_grupo(f.getGrupo());
											alarma.setAlar_docdocente(String
													.valueOf(listadocoordinadores
															.get(i)));
											alarma.setAlar_estado("0");
											alarma.setAlar_grado(f.getGrado());
											alarma.setAlar_motivobajorendimiento("X");
											alarma.setAlar_nombreestudiante(String
													.valueOf(element2[2])
													+ " "
													+ String.valueOf(element2[3])
													+ " "
													+ String.valueOf(element2[4])
													+ " "
													+ String.valueOf(element2[5]));
											alarma.setAlar_grujerar(f
													.getJerarquiagrupo());
											alarma.setAlar_vigencia(f
													.getVigencia());
											f.setPeriodo(contador + "");
											alarma.setAlar_periodo(periodo + "");
											alarma.setAlar_codest(String
													.valueOf(element2[0]));
											if (periodo > 1 && contador >= 2) {
												listalarmas.add(alarma);
											}

										}

									}
								}
							}

							if (agregarlistado) {

								listaAsignaturas.add(col);

							}
						}

						boolean agregarlistadoasig = false;
						for (Iterator it4 = listaAsignaturas.iterator(); it4
								.hasNext();) {
							Object[] element2 = (Object[]) it4.next();

							if (element2[0] != null
									|| !((String) element2[0]).equals("")) {
								agregarlistadoasig = true;
							}
						}

						if (agregarlista && agregarlistadoasig) {
							listaAsignaturas.removeAll(Collections
									.singletonList(null));
							listagrupos.add(listaAsignaturas);
						}
						// cierre metodologia asignatura
						// se reincian variables temporales
						asignaturatmp = "";
						areatmp = "";
						idasig = "";
					}
					listagrupos.removeAll(Collections.singletonList(null));
					totalEstudiantes[contadorgruposanadidios] = listagrupos;
					contadorgruposanadidios = contadorgruposanadidios + 1;
					// cierre asignaturas
				}

				// cierre grupos

			}

			// session.setAttribute("colperiodos",
			// String.valueOf(colperiodos.size()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalEstudiantes;
	}

	public boolean resumen_Asignaturas(HttpServletRequest request,
			String vigencia, String contextoTotal) throws ServletException,
			IOException {
		Connection con = null;
		PreparedStatement pst = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sede = null;
		String jornada = null;
		String met = null;
		String grado = null;
		String grupo = null;
		String periodo = null;
		String nom = null;
		String puesto = null;
		int posicion = 1;
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String cont = null;
		String archivozip = null;
		String archivo = null;
		String archivoxls = null;
		String alert = "El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opci贸n de menu 'Reportes generados'";

		try {
			con = cursor.getConnection();

			if (!asignarBeansAsignaturas(request)) {
				setMensaje("Error capturando datos para el reporte");
				request.setAttribute("mensaje", mensaje);
				ir(2, er, request, response);
				return false;
			}
			pst = con.prepareStatement(rb3
					.getString("existe_mismo_reporte_as_en_cola"));
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			pst.setLong(posicion++, Long.parseLong(!filtroAsignaturas.getSede()
					.equals("-9") ? filtroAsignaturas.getSede() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtroAsignaturas
					.getJornada().equals("-9") ? filtroAsignaturas.getJornada()
					: "-9"));
			pst.setLong(
					posicion++,
					Long.parseLong(!filtroAsignaturas.getMetodologia().equals(
							"-9") ? filtroAsignaturas.getMetodologia() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtroAsignaturas
					.getGrado().equals("-9") ? filtroAsignaturas.getGrado()
					: "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtroAsignaturas
					.getGrupo().equals("-9") ? filtroAsignaturas.getGrupo()
					: "-9"));
			pst.setLong(posicion++,
					Long.parseLong(filtroAsignaturas.getPeriodo().trim()));
			pst.setLong(posicion++, dao.getVigenciaNumerico());
			pst.setString(posicion++, filtroAsignaturas.getareassel());
			pst.setString(posicion++, filtroAsignaturas.getasigsel());
			rs = pst.executeQuery();

			if (!rs.next()) {
				// System.out
				// .println("******nNO HAY NINGUNO CON LOS MISMOS PARAMETROS!...EN ESTADO CERO O -1!*********");
				rs.close();
				pst.close();
				sede = (!filtroAsignaturas.getSede().equals("-9") ? "_Sed_"
						+ filtroAsignaturas.getSede().trim() : "");
				jornada = (!filtroAsignaturas.getJornada().equals("-9") ? "_Jor_"
						+ filtroAsignaturas.getJornada().trim()
						: "");
				met = (!filtroAsignaturas.getMetodologia().equals("-9") ? "_Met_"
						+ filtroAsignaturas.getMetodologia()
						: "");
				grado = (!filtroAsignaturas.getGrado().equals("-9") ? "_Gra_"
						+ filtroAsignaturas.getGrado() : "");
				grupo = (!filtroAsignaturas.getGrupo().equals("-9") ? "_Gru_"
						+ filtroAsignaturas.getGrupo() : "");

				if (Long.parseLong(filtroAsignaturas.getPeriodo().trim()) == 7) {
					filtroAsignaturas.setPeriodonom("FINAL");
				}

				nom = sede
						+ jornada
						+ met
						+ grado
						+ grupo
						+ "_Periodo_"
						+ filtroAsignaturas.getPeriodonom().trim()
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Resumen_Evaluacion_Asignaturas_" + nom + ".pdf";
				archivoxls = "Resumen_Evaluacion_Asignaturas_" + nom + ".xls";
				archivozip = "Resumen_Evaluacion_Asignaturas_" + nom + ".zip";

				posicion = 1;
				pst = con.prepareStatement(rb3.getString("insert_datos_asig"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getInstId()));
				pst.setLong(posicion++, Long.parseLong(!filtroAsignaturas
						.getSede().equals("-9") ? filtroAsignaturas.getSede()
						: "-9"));
				pst.setLong(
						posicion++,
						Long.parseLong(!filtroAsignaturas.getJornada().equals(
								"-9") ? filtroAsignaturas.getJornada() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtroAsignaturas
						.getMetodologia().equals("-9") ? filtroAsignaturas
						.getMetodologia() : "-9"));
				pst.setLong(
						posicion++,
						Long.parseLong(!filtroAsignaturas.getGrado().equals(
								"-9") ? filtroAsignaturas.getGrado() : "-9"));
				pst.setLong(
						posicion++,
						Long.parseLong(!filtroAsignaturas.getGrupo().equals(
								"-9") ? filtroAsignaturas.getGrupo() : "-9"));
				pst.setLong(posicion++,
						Long.parseLong(filtroAsignaturas.getPeriodo().trim()));
				pst.setString(posicion++, !filtroAsignaturas.getPeriodonom()
						.trim().equals("") ? filtroAsignaturas.getPeriodonom()
						.trim() : "");
				pst.setString(posicion++, "");

				pst.setString(posicion++, f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-'));
				pst.setString(posicion++, archivozip);
				pst.setString(posicion++, archivo);
				pst.setString(posicion++, "");
				pst.setLong(posicion++, Long.parseLong("-1"));
				pst.setString(posicion++, login.getUsuarioId());
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));
				pst.setLong(posicion++, Long.parseLong("1"));

				// long consecRepBol= reportesDAO.getConsecReporte();
				// pst.setLong(posicion++,consecRepBol);
				pst.setString(posicion++, login.getInst());
				pst.setString(posicion++, login.getSede());
				pst.setString(posicion++, login.getJornada());
				pst.setLong(posicion++, dao.getVigenciaNumerico());

				String resol = reportesDAO.getResolInst(Long.parseLong(login
						.getInstId()));
				pst.setString(posicion++, resol);
				pst.setLong(posicion++, login.getLogNivelEval());
				pst.setLong(posicion++, login.getLogNumPer());
				pst.setString(posicion++, login.getLogNomPerDef());
				FiltroBeanReports filtroRep = new FiltroBeanReports();
				// filtroRep.set
				login.setSedeId(filtroAsignaturas.getSede());
				login.setJornadaId(filtroAsignaturas.getJornada());
				filtroRep.setMetodologia(filtroAsignaturas.getMetodologia());
				filtroRep.setGrado(filtroAsignaturas.getGrado());
				int tipoPrees = reportesDAO.getTipoEval(filtroRep, login);
				// System.out.println("RESUMENES: INSERT TIPOEVALPREES: "
				// + tipoPrees + "   NIVEL EVAL: "
				// + login.getLogNivelEval());
				pst.setInt(posicion++, tipoPrees);
				// pst.setInt(posicion++,1);
				pst.setInt(posicion++, -9);
				pst.setString(posicion++, filtroAsignaturas.getareassel());
				pst.setString(posicion++, filtroAsignaturas.getasigsel());
				if (login.getPerfil().equals("410")
						|| login.getPerfil().equals("421")) {
					pst.setString(posicion++, "1");
				} else {
					pst.setString(posicion++, "0");
				}
				pst.executeUpdate();
				// System.out
				// .println("RESUMEN ASIG: Se insertn en datos_boletin!!!!");
				// System.out.println("nnSe insertn en DATOS_RESUMEN_ASIGNATURA!!!!");
				pst.close();
				con.commit();

				ponerReporte(moduloAsignatura, login.getUsuarioId(),
						rb3.getString("reportes.PathReportes") + archivozip
								+ "", "zip", "" + archivozip, "-1",
						"ReporteInsertarEstado");// Estado -1
				// System.out
				// .println("Se insertn el ZIP en Reporte con estado -1");
				siges.util.Logger.print(
						login.getUsuarioId(),
						"Peticinn de Resumen_Asignatura:_Institucion:_"
								+ login.getInstId() + "_Usuario:_"
								+ login.getUsuarioId() + "_NombreReporte:_"
								+ archivozip + "", 3, 1, this.toString());
				cont = cola_reportes("puesto_del_reporte_asignatura");

				if (cont != null && !cont.equals(""))
					updatePuestoReporte(cont, archivozip, login.getUsuarioId(),
							"update_puesto_reporte_asignatura");
			} else {
				rs.close();
				pst.close();
				setMensaje("nYa existe una peticinn de este reporte con los mismos parnmetros!");
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("mensaje2",
						"nYa existe una peticinn de este reporte con los mismos parnmetros!");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", moduloAsignatura, login.getUsuarioId(),
					rb3.getString("reportes.PathReportes") + archivozip + "",
					"zip", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en FiltroSave");
			return false;
		} finally {
			try {
				if (cursor != null)
					cursor.cerrar();
				if (util != null)
					util.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
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

	public void updatePuestoReporte(String puesto, String nombreboletinzip,
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
			pst.setString(posicion++, (nombreboletinzip));
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
		return cant;
	}

	/**
	 * Inserta los valores por defecto para el bean de informaci贸n bnsica con
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
	 * Inserta los valores por defecto para el bean de informaci贸n bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansAreas(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) session.getAttribute("login");
		filtroAreas = (FiltroBeanResumenAreas) session
				.getAttribute("filtroResumenAreas");
		filtroAreas.setInstitucion(login.getInstId());
		filtroAreas.setFormato("1");
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de informaci贸n bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansAsignaturas(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) session.getAttribute("login");
		filtroAsignaturas = (FiltroBeanResumenAsignaturas) session
				.getAttribute("filtroResumenAsignaturas");
		filtroAsignaturas.setInstitucion(login.getInstId());
		filtroAsignaturas.setFormato("1");
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de informaci贸n bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeansConsultaPerdiaAsignaturas(
			HttpServletRequest request) throws ServletException, IOException {
		login = (Login) session.getAttribute("login");
		filtroConsultaAisgnaturasPerdidas = (FiltroConsultaAsignaturasPerdidas) session
				.getAttribute("filtroConsultaAsignaturasPerdidas");
		filtroConsultaAisgnaturasPerdidas.setInstitucion(login.getInstId());
		filtroConsultaAisgnaturasPerdidas.setFormato("1");
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
	@Override
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
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
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
			mensaje = "VERIFIQUE LA SIGUIENTE informaci贸n: \n\n";
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
	@Override
	public void destroy() {
		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

	public void filtroConsultaPerdidaAsignaturas(HttpServletRequest request)
			throws ServletException, IOException {

		try {
			ResourceBundle rb = ResourceBundle
					.getBundle("siges.boletines.bundle.resumen");
			session.removeAttribute("filtroResumenAsignaturas");
			// System.out
			// .println("*********////*********nENTRn CONSULTA ASIGNATURAS PERIDA !**********//**********");
			Collection list;
			String per;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);

			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(
						rb.getString("filtroSedeInstitucion"), list));
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(
						rb.getString("filtroSedeJornadaInstitucion"), list));
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));

			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));
			if (request.getAttribute("filtroPeriodoF") == null) {
				List l = new ArrayList();
				ItemVO item = null;
				for (int i = 1; i <= login.getLogNumPer(); i++) {
					item = new ItemVO(i, "" + i);
					l.add(item);
				}
				item = new ItemVO(7, login.getLogNomPerDef());
				l.add(item);

				request.setAttribute("filtroPeriodoF", l);

			}

			per = login.getPerfil();
			// System.out.println("nombre perfil "+per);
			if (per.equals("422")) {
				// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
				Collection colgg[] = util.getfiltroasdoc(login.getInstId(),
						Long.toString(login.getVigencia_inst()),
						login.getUsuarioId());
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "1");
			} else {
				Collection colgg[] = util.getfiltroas(login.getInstId(),
						Long.toString(login.getVigencia_inst()));
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "0");
			}
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}

	}

	/**
	 * Metodo que calcula total de minimas inasistencias para determinar si un
	 * estudianteb pierde o no una materia
	 * 
	 * @param request
	 * @param login
	 * @param filtroEvaluacion
	 * @return
	 * @throws Exception
	 */
	public int[] consultartotalasistenciasporasignatura(
			HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion, ArrayList idasignaturas)
			throws Exception {
		ResourceBundle rb = ResourceBundle
				.getBundle("siges.evaluacion.bundle.evaluacion");

		String[] asig = new String[idasignaturas.size()];
		int[] ihtodasasignaturas = new int[idasignaturas.size()];

		for (int i = 0; i < idasignaturas.size(); i++) {
			asig[i] = String.valueOf(idasignaturas.get(i)).substring(
					String.valueOf(idasignaturas.get(i)).lastIndexOf('|') + 1,
					String.valueOf(idasignaturas.get(i)).length());
		}

		Cursor cursor = new Cursor();
		AdminParametroInstDAO adminDAO = new AdminParametroInstDAO(cursor);

		InstParVO instParVO = adminDAO.getInstdeVigenciaMayor(Integer
				.parseInt(login.getInstId()));

		for (int i = 0; i < asig.length; i++) {
			int ih_asignatura = evaluacionDAO
					.consultarIntensidadHorariaporasignatura(login.getInstId(),
							filtroEvaluacion.getGrado(),
							filtroEvaluacion.getMetodologia(), asig[i],
							String.valueOf(instParVO.getInsparvigencia()));
			int semanasAcademicas = Integer.parseInt(rb
					.getString("semanasacademicas"));

			ihtodasasignaturas[i] = (int) ((ih_asignatura * semanasAcademicas) * instParVO
					.getPorcentajeperdida()) / 100;
		}

		return ihtodasasignaturas;
	}
}
