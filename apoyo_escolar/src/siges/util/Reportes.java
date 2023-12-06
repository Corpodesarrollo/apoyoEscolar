package siges.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;

/**
 * Nombre: Reportes<br>
 * Descripción: ejecución de reportes<br>
 * Funciones de la página: lista de reportes<br>
 * Fecha de modificación: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class Reportes extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int REP_GACADEMICAART = 19;
	private static final int REP_INFORMESART = 21;

	private ServletContext ctx;
	ResourceBundle rb;

	/**
	 * 
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	private String process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		ctx = req.getSession().getServletContext();
		rb = ResourceBundle.getBundle("siges.util.bundle.reportes");

		String view = "/Reportes.jsp";
		ReporteVO reporteVO = (ReporteVO) req.getSession().getAttribute(
				"reporteVO");
		Login login = (Login) req.getSession().getAttribute("login");

		req.getSession().setAttribute("listaRep", ReporteVO.lista);

		/*
		 * Colocar valores por defecto para el bean ReporteVO
		 */
		if (reporteVO == null || reporteVO.getRepTipo() < 1) {
			reporteVO = new ReporteVO();
			ItemVO item = (ItemVO) ReporteVO.lista.get(0);
			reporteVO.setRepTipo(item.getCodigo());
			reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
		}

		// System.out.println("reporteVO.getRepTipo() ::  " +
		// reporteVO.getRepTipo());

		try {
			if (login != null) {

				String accion = req.getParameter("accion");

				if (accion != null && accion.equals("1")) {// borrar
					borrarArchivos(req.getParameterValues("chk"),
							req.getSession());
					getReportesDefault(req, login, reporteVO);
				} else {
					getReportesDefault(req, login, reporteVO);
					req.getSession().setAttribute("reporteVO", reporteVO);
				}

				return view;

			}
			return view;
		} catch (Exception th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	/**
	 * @param items
	 * @param session
	 */
	private void borrarArchivos(String[] items, HttpSession session) {

		Connection cn = null;
		PreparedStatement pst = null;
		Login login = (Login) session.getAttribute("login");
		try {
			if (login != null) {
				ResourceBundle rb3 = ResourceBundle.getBundle("operaciones");
				cn = DataSourceManager.getConnection(1);
				pst = cn.prepareStatement(rb3.getString("ReporteEliminar"));
				pst.clearBatch();
				for (int i = 0; i < items.length; i++) {
					String path = Ruta.getArchivo(ctx, items[i]);
					File f = new File(path);
					if (f.exists())
						FileUtils.forceDelete(f);
					pst.setString(1, login.getUsuarioId());
					pst.setString(2, items[i]);
					pst.addBatch();
				}
				pst.executeBatch();
			}
		} catch (Exception x) {
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	private void getReportesDefault(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		try {

			int tipoRep = (int) reporteVO.getRepTipo();
			// System.out.println("tipoRep-- " + tipoRep +
			// " ReporteVO._REP_LIBRO_NOTAS " + ReporteVO._REP_LIBRO_NOTAS);
			switch (tipoRep) {

			case ReporteVO._REP_BOLETINES:
			case ReporteVO._REP_RESUMENES_AREA:
			case ReporteVO._REP_RESUMENES_ASIG:
			case ReporteVO._REP_RESUMENES_AREA_ASIG:
			case ReporteVO._REP_CERTIFICADOS:
			case ReporteVO._REP_LIBRO_NOTAS:
			case ReporteVO._REP_RESULT_ACADEMICOS:
				getRep_Datos_boletin(req, login, reporteVO);
				break;

			case ReporteVO._REP_CONSTANCIAS:
			case ReporteVO._REP_INF_ESTUD_Y_DOCEN:
			case ReporteVO._REP_COMPARATIVOS:
			case ReporteVO._REP_HORARIOS_GRUP:
			case ReporteVO._REP_PLANTILLA_EVAL:
			case ReporteVO._REP_PLANTILLA_LOGROS:
			case ReporteVO._REP_PLANTILLA_MASIVAS:
			case ReporteVO._REP_ENCUESTA_EST_X_GRUP:
			case ReporteVO._REP_RESULT_PUESTOS://REP_PUESTOS

				getRep_Generico(req, login, reporteVO);
				break;

			case ReporteVO._REP_CARNES:
				getRep_Datos_carne(req, login, reporteVO);
				break;

			case ReporteVO._REP_ESTADISTICOS:
				getReportesTipoEstadisticos(req, login, reporteVO);
				break;
			case ReporteVO._REP_PLAN_ESTUDIO:
				getReportesTipoReportePlan(req, login, reporteVO);
				break;

			// ///////////////// pendiente /////////////////////////

			case ReporteVO._REP_RESULT_IMPORTACION:
				// RESULTADOS_IMPORTACION:
				getReportesTipoResultadosImportacion(req, login, reporteVO);
				break;
			case ReporteVO._REP_ENCUESTA:
				// REP_ENCUESTA:
				getRepEncuesta(req, login, reporteVO);
				break;
			case ReporteVO._REP_HORARIO_ART:
				// REP_HORARIOS_ART:
				getRepHorariosArt(req, login, reporteVO);
				break;
			case // ReporteVO.rep_
			REP_GACADEMICAART:
				getRepGAcademicaArt(req, login, reporteVO);
				break;
			case ReporteVO._REP_ESTUDIO_ART:
				// REP_ESTUDIANTESART:
				getRepEstudiantesArt(req, login, reporteVO);
				break;
			case REP_INFORMESART:
				getRepInformesArt(req, login, reporteVO);
				break;
			case ReporteVO._REP_RESULT_COMPARATIVO_VIGENCIA_ANTERIOR:
				getRepCompVigAnt(req, login, reporteVO);
				break;
			}
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		}
	}

	/**
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getReportesTipoEstadisticos(HttpServletRequest req,
			Login login, ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb
					.getString("listaReportesTipoEstadisticos")
					+ " "
					+ rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rb
					.getString("total_en_cola_asignatura_grupo"));
			rs = pst.executeQuery();
			while (rs.next()) {
				req.setAttribute("total_asignatura_grupo", rs.getString(1));
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rb.getString("total_en_cola_logro_grupo"));
			rs = pst.executeQuery();
			while (rs.next()) {
				req.setAttribute("total_logro_grupo", rs.getString(1));
			}

		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	private void getReportesTipoResultadosImportacion(HttpServletRequest req,
			Login login, ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb
					.getString("listaReportesTipoResultadosImportacion")
					+ " "
					+ rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getReportesTipoReportePlan(HttpServletRequest req,
			Login login, ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb
					.getString("listaReportesTipoReportePlan")
					+ " "
					+ rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
		} catch (Exception th) {
			th.printStackTrace();

			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Collection getCollection(ResultSet rs) throws SQLException {
		Collection list = null;
		int m = rs.getMetaData().getColumnCount();
		list = new ArrayList();
		Object[] o;
		int i = 0;
		int ff = 0;
		while (rs.next()) {
			ff++;
			o = new Object[m];
			for (i = 1; i <= m; i++) {
				o[i - 1] = rs.getString(i);

			}
			list.add(o);
		}
		return list;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String s = process(req, res);
		if (s != null && !s.equals(""))
			ir(1, s, req, res);
	}

	/**
	 * recibe el url de destino, el request y el response y manda el control a
	 * la pagina indicada
	 * 
	 * @param: int a
	 * @param: String s
	 * @param: HttpServletRequest request
	 * @param: HttpServletResponse response
	 **/
	private void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	// REPORTES ENCUESTA
	/**
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRepEncuesta(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			if (reporteVO.getRepOrden() == 0) {
				reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
			}
			pst = cn.prepareStatement(rb.getString("listaReportesEncuesta")
					+ " " + rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
			rs.close();
			pst.close();

			// System.out.println("orden: "+rb.getString("orden"+reporteVO.getRepOrden()));
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	private void getRepHorariosArt(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb
					.getString("listaReportesTipoHorarioArt")
					+ " "
					+ rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
			rs.close();
			pst.close();
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	// REPORTES ESTUDIANTES ARTICULACION
	/**
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRepEstudiantesArt(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			if (reporteVO.getRepOrden() == 0) {
				reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
			}
			pst = cn.prepareStatement(rb
					.getString("listaReportesEstudiantesArt")
					+ " "
					+ rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
			rs.close();
			pst.close();

			// System.out.println("orden: "+rb.getString("orden"+reporteVO.getRepOrden()));
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	// REPORTES GESTION ACADEMICA
	/**
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRepGAcademicaArt(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			if (reporteVO.getRepOrden() == 0) {
				reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
			}
			pst = cn.prepareStatement(rb
					.getString("listaReportesGAcademicaArt")
					+ " "
					+ rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
			rs.close();
			pst.close();

			// System.out.println("orden: "+rb.getString("orden"+reporteVO.getRepOrden()));
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	// REPORTES INFORMES ARTICULACION
	/**
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRepInformesArt(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			if (reporteVO.getRepOrden() == 0) {
				reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
			}
			pst = cn.prepareStatement(rb.getString("listaReportesInformesArt")
					+ " " + rb.getString("orden" + reporteVO.getRepOrden()));
			pst.setString(1, login.getUsuarioId());
			rs = pst.executeQuery();
			req.setAttribute("lista", getCollection(rs));
			rs.close();
			pst.close();

			// System.out.println("orden: "+rb.getString("orden"+reporteVO.getRepOrden()));
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::: MODIFICADO 20-10-10
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	/**
	 * @function Metodó encargado de obtener el listado de reportes que estan
	 *           relacionados con la tabla DATOS_BOLETIN.
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRep_Datos_boletin(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		// System.out.println("getRep_Datos_boletin ");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int daboltiporep = 0;
		int tipo = (int) reporteVO.getRepTipo();

		// daboltiporep

		switch (tipo) {
		case ReporteVO._REP_BOLETINES:
			daboltiporep = Params.TIPOREP_BOLETINES;
			break;
		case ReporteVO._REP_RESUMENES_AREA:
			daboltiporep = Params.TIPOREP_ResumAreas;
			break;
		case ReporteVO._REP_RESUMENES_ASIG:
			daboltiporep = Params.TIPOREP_ResumAsigS;
			break;
		case ReporteVO._REP_RESUMENES_AREA_ASIG: 
			daboltiporep = Params.TIPOREP_ResumAreaAsigS;
			break;	
		case ReporteVO._REP_CERTIFICADOS:
			daboltiporep = Params.TIPOREP_Certificados;
			break;
		case ReporteVO._REP_RESULT_ACADEMICOS:
			daboltiporep = Params.TIPOREP_RepResultados;
			break;
		case ReporteVO._REP_LIBRO_NOTAS:
			daboltiporep = Params.TIPOREP_RepLibroNota;
			break;

		default:
			break;
		}

		String sql = ""
				+ " select "
				+ "  distinct rep.repnombre,  "
				+ "  rep.reprecurso,"
				+ "  substr(rep.RepTipo,1,3), "
				+ "  rep.repfecha,   "
				+ "  rep.repestado,    "
				+ "  rep.repmodulo,   "
				+ "  rep.repmensaje,   "
				+ "  dbol.dabolpuesto,  "
				+ "  rep.repusuario  "
				+ " from   reporte rep      "
				+ " inner join datos_boletin dbol on  dabolusuario = repusuario "
				+ "                              and dabolnombrezip = repnombre "
				+ " where   repusuario = ? " + "  and repmodulo = ? "
				+ "  and daboltiporep = ? "
				+ rb.getString("orden_datos_boletin" + reporteVO.getRepOrden());

		try {
			cn = DataSourceManager.getConnection(1);
			// System.out.println("consulta " + sql);
			pst = cn.prepareStatement(sql);
			int posicion = 1;
			pst.setString(posicion++, login.getUsuarioId());
			// System.out.println("login.getUsuarioId() " +
			// login.getUsuarioId());
			pst.setInt(posicion++, tipo);
			// System.out.println("tipo " + tipo);
			pst.setInt(posicion++, daboltiporep);
			// System.out.println("daboltiporep " + daboltiporep);

			rs = pst.executeQuery();
			Collection lista = getCollection(rs);
			// System.out.println("lista " + lista.size() );
			req.setAttribute("lista", lista);
			rs.close();
			pst.close();

			sql = "select count(*) as numero  " + " from DATOS_BOLETIN  "
					+ " WHERE DABOLESTADO=-1 " + " and daboltiporep = ? "
					+ " ORDER BY DABOLFECHA ASC";

			pst = cn.prepareStatement(sql);
			posicion = 1;
			pst.setInt(posicion++, daboltiporep);
			rs = pst.executeQuery();
			while (rs.next()) {
				req.setAttribute("total_reporte", rs.getString(1));
			}

			rs.close();
			pst.close();
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @function Metodó encargado de obtener el listado de reportes de carnet.
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRep_Datos_carne(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		// System.out.println("getRep_Datos_carne");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "" + " select repNombre,       " + "   RepRecurso,"
				+ "   substr(RepTipo,1,3),       " + "   repFecha,       "
				+ "   REPESTADO,       " + "   Repmodulo,       "
				+ "   repmensaje,       " + "   DACARPUESTO "
				+ "from reporte,  " + "     DATOS_CARNE  "
				+ "where Repusuario = ?  " + " and DACARUSUARIO = Repusuario  "
				+ " AND DACARNOMBREZIP = REPNOMBRE " + " and repmodulo = ? "
				+ rb.getString("orden_carnet_" + reporteVO.getRepOrden());
		try {
			cn = DataSourceManager.getConnection(1);
			// System.out.println("CARNET +++++++++++++++++++++++++++++++   Sconsulta "
			// + sql);
			pst = cn.prepareStatement(sql);
			int posicion = 1;
			pst.setString(posicion++, login.getUsuarioId());
			pst.setLong(posicion++, reporteVO.getRepTipo());

			rs = pst.executeQuery();
			Collection lista = getCollection(rs);
			req.setAttribute("lista", lista);
			rs.close();
			pst.close();

			pst = cn.prepareStatement(rb.getString("total_en_cola_carne"));
			rs = pst.executeQuery();
			while (rs.next()) {
				req.setAttribute("total_reporte", rs.getString(1));
			}

			rs.close();
			pst.close();
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @function Metodó encargado de obtener el listado de reportes según el
	 *           tipo de reporte o código del módulo
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRep_Generico(HttpServletRequest req, Login login,
			ReporteVO reporteVO) {
		// System.out.println("getRep_Generico");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "" + " select repNombre," + "        RepRecurso,"
				+ "        substr(RepTipo,1,3)," + "        repFecha,"
				+ "        REPESTADO," + "        Repmodulo,"
				+ "        repmensaje " + " from reporte "
				+ " where repusuario = ?" + "  and   repmodulo = ? "
				+ rb.getString("orden" + reporteVO.getRepOrden());
		try {
			cn = DataSourceManager.getConnection(1);
			// System.out.println("consulta " + sql);
			pst = cn.prepareStatement(sql);
			int posicion = 1;
			pst.setString(posicion++, login.getUsuarioId());
			pst.setLong(posicion++, reporteVO.getRepTipo());

			rs = pst.executeQuery();
			Collection lista = getCollection(rs);
			req.setAttribute("lista", lista);
			rs.close();
			pst.close();

			rs.close();
			pst.close();
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}
	

	/**
	 * @function Metodó encargado de obtener el listado de reportes de comparativo vigencia anteriror
	 *           tipo de reporte o código del módulo
	 * @param req
	 * @param login
	 * @param reporteVO
	 */
	private void getRepCompVigAnt(HttpServletRequest req, Login login,
			ReporteVO reporteVO){
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "" + " select repNombre," + "        RepRecurso,"
				+ "        substr(RepTipo,1,3)," + "        repFecha,"
				+ "        REPESTADO," + "        Repmodulo,"
				+ "        repmensaje " + " from reporte "
				+ " where repusuario = ?" + "  and   repmodulo = ? "
				+ rb.getString("orden" + reporteVO.getRepOrden());
		try {
			cn = DataSourceManager.getConnection(1);
			// System.out.println("consulta " + sql);
			pst = cn.prepareStatement(sql);
			int posicion = 1;
			pst.setString(posicion++, login.getUsuarioId());
			pst.setLong(posicion++, reporteVO.getRepTipo());

			rs = pst.executeQuery();
			Collection lista = getCollection(rs);
			req.setAttribute("lista", lista);
			rs.close();
			pst.close();

			rs.close();
			pst.close();
		} catch (Exception th) {
			th.printStackTrace();
			req.setAttribute("mensaje", "Ocurrio error" + th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		
	}

}