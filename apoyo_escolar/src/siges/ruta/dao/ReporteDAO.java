package siges.ruta.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
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
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.ruta.vo.FiltroBeanGestVO;
import siges.ruta.vo.FiltroBeanVO;

/**
 * @author Latined
 * 
 *         Realiza todas las operaciones que interactuan con la base de datos
 * 
 */
public class ReporteDAO extends Dao {
	public String sentencia;
	public String buscar;
	private ResourceBundle rb3;
	private byte[] bytes;
	private Zip zip;
	private static final int codigo_tipoN = 1;
	private static final int codigo_tipoG = 2;

	public ReporteDAO(Cursor cur) {
		super(cur);
		bytes = null;
		rb3 = ResourceBundle.getBundle("siges.ruta.reportes_ruta");
	}

	public boolean insertarDatosReporte(FiltroBeanVO filtro, String InstId,
			java.sql.Timestamp f2, String archivozip, String archivo,
			String UsuarioId) {
		int posicion = 1;
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = cursor.getConnection();
			System.out.println("entro por DAO");
			pst = con.prepareStatement(rb3
					.getString("insert_datos_reporte_nutricion"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(InstId));
			pst.setLong(posicion++, Long.parseLong(!filtro.getSede().equals(
					"-9") ? filtro.getSede() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getJornada().equals(
					"-9") ? filtro.getJornada() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia()
					.equals("-9") ? filtro.getMetodologia() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrado().equals(
					"-9") ? filtro.getGrado() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo().equals(
					"-9") ? filtro.getGrupo() : "-9"));
			pst.setString(
					posicion++,
					!filtro.getEstudiante().equals("-9") ? filtro
							.getEstudiante() : "-9");
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo().trim()));
			pst.setString(posicion++,
					!filtro.getPeriodonom().trim().equals("") ? filtro
							.getPeriodonom().trim() : "");
			pst.setLong(posicion++, Long.parseLong(!filtro.getOrden().equals(
					"-9") ? filtro.getOrden() : "-9"));
			pst.setString(posicion++,
					f2.toString().replace(':', '-').replace('.', '-'));
			pst.setString(posicion++, archivozip);
			pst.setString(posicion++, archivo);
			pst.setLong(posicion++, Long.parseLong("-1"));
			pst.setString(posicion++, UsuarioId);
			pst.setInt(posicion++, codigo_tipoN);
			pst.executeUpdate();
			System.out.println("nnSe insertn en DATOS_REPORTE_NUTRICION!!!!");
			pst.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener información acadnmica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception in) {
			System.out.println("se jodio en dao");
			in.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarDatosReporteG(FiltroBeanGestVO filtro,
			String InstId, java.sql.Timestamp f2, String archivozip,
			String archivo, String UsuarioId) {
		int posicion = 1;
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3
					.getString("insert_datos_reporte_nutricion"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(InstId));
			pst.setLong(posicion++, Long.parseLong(!filtro.getSede().equals(
					"-9") ? filtro.getSede() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getJornada().equals(
					"-9") ? filtro.getJornada() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia()
					.equals("-9") ? filtro.getMetodologia() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrado().equals(
					"-9") ? filtro.getGrado() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo().equals(
					"-9") ? filtro.getGrupo() : "-9"));
			pst.setString(
					posicion++,
					!filtro.getEstudiante().equals("-9") ? filtro
							.getEstudiante() : "-9");
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo().trim()));
			pst.setString(posicion++,
					!filtro.getPeriodonom().trim().equals("") ? filtro
							.getPeriodonom().trim() : "");
			pst.setLong(posicion++, Long.parseLong(!filtro.getOrden().equals(
					"-9") ? filtro.getOrden() : "-9"));
			pst.setString(posicion++,
					f2.toString().replace(':', '-').replace('.', '-'));
			pst.setString(posicion++, archivozip);
			pst.setString(posicion++, archivo);
			pst.setLong(posicion++, Long.parseLong("-1"));
			pst.setString(posicion++, UsuarioId);
			pst.setInt(posicion++, codigo_tipoG);
			pst.executeUpdate();
			System.out.println("nnSe insertn en DATOS_REPORTE_NUTRICIONG!!!!");
			pst.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener información acadnmica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
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

	public boolean ponerReporte(String modulo, String us, String rec,
			String tipo, String nombre, String estado, String prepared) {
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
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ponerReporte: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
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

	public boolean updatePuestoReporte(String puesto, String nombrereportezip,
			String user, String prepared, int tipo) {
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
			pst.setInt(posicion++, tipo);
			pst.executeUpdate();
			pst.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando updatePuestoReporte: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
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

	public boolean ponerReporteMensaje(String estado, String modulo, String us,
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
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ponerReporteMensaje: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
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

	public String colaReportes(int tipo) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String cant = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("puesto_del_reporte"));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, tipo);
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
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public boolean updateColaReportesEstadoCero(String prepared, int tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, tipo);
			pst.executeUpdate();
			pst.close();
			con.commit();
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

	public void updateDatosReporte(String nuevoestado, String nombrereporte,
			String estadoanterior, String user, String prepared, int tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(nuevoestado));
			pst.setString(posicion++, (nombrereporte));
			pst.setLong(posicion++, Long.parseLong(estadoanterior));
			pst.setString(posicion++, (user));
			pst.setInt(posicion++, tipo);
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

	public void updateDatosReporteFechaFin(String nuevafecha, String estado,
			String nombrereporte, String user, String prepared, int tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nuevafecha));
			pst.setString(posicion++, (nombrereporte));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.setString(posicion++, (user));
			pst.setInt(posicion++, tipo);
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

	public void updateReporte(String modulo, String nombrereporte, String user,
			String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombrereporte));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
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

	public String[][] reporteGenerar(FiltroBeanVO filtro, String modulo,
			String prepared) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String[][] array1 = null;
		Collection list;
		Object[] o = new Object[2];

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, codigo_tipoN);
			rs = pst.executeQuery();

			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int i = 0;
			int f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}

			if (!list.isEmpty()) {
				// System.out.println("nnnnHay reportes por generar!!!!");
				i = 0;
				int j = -1;
				Iterator iterator = list.iterator();
				o = ((Object[]) iterator.next());
				array1 = new String[list.size()][o.length];
				iterator = list.iterator();
				while (iterator.hasNext()) {
					j++;
					o = ((Object[]) iterator.next());
					for (i = 0; i < o.length; i++) {
						array1[j][i] = (String) o[i];
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en reporteGenerar: " + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "-1",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return array1;
	}

	public String[][] reporteGenerarG(FiltroBeanGestVO filtro, String modulo,
			String prepared) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String[][] array1 = null;
		Collection list;
		Object[] o = new Object[2];

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, codigo_tipoG);
			rs = pst.executeQuery();

			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int i = 0;
			int f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}

			if (!list.isEmpty()) {
				System.out.println("nnnnHay reportes por generar!!!!");
				i = 0;
				int j = -1;
				Iterator iterator = list.iterator();
				o = ((Object[]) iterator.next());
				array1 = new String[list.size()][o.length];
				iterator = list.iterator();
				while (iterator.hasNext()) {
					j++;
					o = ((Object[]) iterator.next());
					for (i = 0; i < o.length; i++) {
						array1[j][i] = (String) o[i];
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en reporteGenerar: " + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "-1",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return array1;
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

	public boolean updateColaReportes(String prepared, int tipo) {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, tipo);
			pst.executeUpdate();
			con.commit();
			pst.close();
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
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablas(String usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_NUTRICION.nutricion_borrar(?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			cstmt.setInt(posicion++, codigo_tipoN);
			cstmt.executeUpdate();
			cstmt.close();
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
	 * limpia las tablas de boletines cuando el hilo esta durmiendo
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void vaciarTablas() {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			cstmt = con.prepareCall("{call PK_NUTRICION.nutricion_vaciar(?)}");
			cstmt.setInt(posicion++, codigo_tipoN);
			cstmt.executeUpdate();
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

	public void limpiarTablasG(String usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_NUTRICION.gestacion_borrar(?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			cstmt.setInt(posicion++, codigo_tipoG);
			cstmt.executeUpdate();
			cstmt.close();
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
	 * limpia las tablas de boletines cuando el hilo esta durmiendo
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void vaciarTablasG() {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			cstmt = con.prepareCall("{call PK_NUTRICION.gestacion_vaciar(?)}");
			cstmt.setInt(posicion++, codigo_tipoG);
			cstmt.executeUpdate();
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

	public void ponerReporteMensajeListo(String modulo, String us, String rec,
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

	public void updateDatosReporteFechaGen(String nuevafecha, String estado,
			String nombrereporte, String user, String prepared, int tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nuevafecha));
			pst.setString(posicion++, (nombrereporte));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.setString(posicion++, (user));
			pst.setInt(posicion++, tipo);
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
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public String getVigenciaActual() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String vig = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("vigencia"));
			posicion = 1;
			rs = pst.executeQuery();
			if (rs.next())
				vig = rs.getString(1);
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
		return vig;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 **/
	public boolean callableStatements(FiltroBeanVO filtro, String vigencia,
			String modulo) {
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		Object[] o = new Object[2];
		int posicion = 1;

		try {
			con = cursor.getConnection();
			System.out.println("!usuario:! " + filtro.getUsuarioid());
			System.out.println("!inst:! " + filtro.getInsitucion());
			System.out.println("!Sede:! " + filtro.getSede());
			System.out.println("!Jornada:! " + filtro.getJornada());
			System.out.println("!metod:! " + filtro.getMetodologia());
			System.out.println("!grado:! " + filtro.getGrado());
			System.out.println("!Grupo:! " + filtro.getGrupo());
			System.out.println("!Periodo:! " + filtro.getPeriodo().trim());
			System.out.println("!Id:! "
					+ (!filtro.getEstudiante().equals("") ? filtro
							.getEstudiante() : "0"));

			/* callable statement */
			cstmt = con
					.prepareCall("{call PK_NUTRICION.estudiantes_nutricion(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInsitucion()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtro.getPeriodo().trim()));
			cstmt.setString(posicion++, filtro.getPeriodonom().trim());
			cstmt.setLong(posicion++, Long.parseLong(vigencia));
			cstmt.setString(
					posicion++,
					(!filtro.getEstudiante().equals("") ? filtro
							.getEstudiante() : "0"));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setInt(posicion++, codigo_tipoN);
			System.out
					.println("Inicia procedimiento estudiantes_nutricion	.. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.executeUpdate();
			System.out
					.println("Fin procedimiento estudiantes_nutricion .. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion SQL:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinPaila",
					"Ocurrin excepcinn prepareds:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 **/
	public boolean callableStatementsG(FiltroBeanGestVO filtro,
			String vigencia, String modulo) {
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		Object[] o = new Object[2];
		int posicion = 1;

		try {
			con = cursor.getConnection();
			System.out.println("!usuario:! " + filtro.getUsuarioid());
			System.out.println("!inst:! " + filtro.getInsitucion());
			System.out.println("!Sede:! " + filtro.getSede());
			System.out.println("!Jornada:! " + filtro.getJornada());
			System.out.println("!metod:! " + filtro.getMetodologia());
			System.out.println("!grado:! " + filtro.getGrado());
			System.out.println("!Grupo:! " + filtro.getGrupo());
			System.out.println("!Periodo:! " + filtro.getPeriodo().trim());
			System.out.println("!Id:! "
					+ (!filtro.getEstudiante().equals("") ? filtro
							.getEstudiante() : "0"));

			/* callable statement */
			cstmt = con
					.prepareCall("{call PK_NUTRICION.estudiantes_gestacion(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInsitucion()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtro.getPeriodo().trim()));
			cstmt.setString(posicion++, filtro.getPeriodonom().trim());
			cstmt.setLong(posicion++, Long.parseLong(vigencia));
			cstmt.setString(
					posicion++,
					(!filtro.getEstudiante().equals("") ? filtro
							.getEstudiante() : "0"));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setInt(posicion++, codigo_tipoG);
			System.out
					.println("Inicia procedimiento estudiantes_gestacion	.. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.executeUpdate();
			System.out
					.println("Fin procedimiento estudiantes_gestacion .. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion SQL:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinPaila",
					"Ocurrin excepcinn prepareds:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/
	public byte[] getArrayBytesPdf(FiltroBeanVO filtro, File reportFile,
			Map parameterscopy, String modulo) {
		byte[] bytes = null;
		Connection con = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				System.out.println("***Se mandn ejecutar el jasper****");
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
						parameterscopy, con);
			}
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}

		return bytes;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/
	public boolean getExcel(FiltroBeanVO filtro, File reportFile,
			Map parameterscopy, String modulo, String contextoTotal) {
		byte[] bytes = null;
		Connection con = null;
		String archivosalida = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				System.out.println("***Se mandn ejecutar el jasper****");
				archivosalida = Ruta.get(contextoTotal,
						rb3.getString("reportes.PathReporte"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportFile.getPath(), parameterscopy, con);
				String xlsFileName = filtro.getNombrereporte();
				String xlsFilesSource = archivosalida;
				File f = new File(xlsFilesSource);
				if (!f.exists())
					FileUtils.forceMkdir(f);
				System.out.println("SALIDA DEL XLS: " + xlsFilesSource
						+ xlsFileName);
				// Creacion del XLS
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						xlsFilesSource + xlsFileName);
				exporter.setParameter(
						JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.TRUE);
				exporter.exportReport();
				System.out.println("***SE EXPORTn EL ARCHIVO EXCEL****");
			}
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 1);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 1);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}

		return true;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/
	public byte[] getArrayBytesGPdf(FiltroBeanGestVO filtro, File reportFile,
			Map parameterscopy, String modulo) {
		byte[] bytes = null;
		Connection con = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				System.out.println("***Se mandn ejecutar el jasper****");
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
						parameterscopy, con);
			}
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}

		return bytes;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/
	public boolean getGExcel(FiltroBeanGestVO filtro, File reportFile,
			Map parameterscopy, String modulo, String contextoTotal) {
		Connection con = null;
		String archivosalida = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				System.out.println("***Se mandn ejecutar el jasper****");
				archivosalida = Ruta.get(contextoTotal,
						rb3.getString("reportes.PathReporte"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportFile.getPath(), parameterscopy, con);
				String xlsFileName = filtro.getNombrereporte();
				String xlsFilesSource = archivosalida;
				File f = new File(xlsFilesSource);
				if (!f.exists())
					FileUtils.forceMkdir(f);
				System.out.println("SALIDA DEL XLS: " + xlsFilesSource
						+ xlsFileName);
				// Creacion del XLS
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						xlsFilesSource + xlsFileName);
				exporter.setParameter(
						JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.TRUE);
				exporter.exportReport();
				System.out.println("***SE EXPORTn EL ARCHIVO EXCEL****");
			}
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			updateDatosReporte("2", filtro.getNombrereportezip(), "0",
					filtro.getUsuarioid(), "update_datos_reporte", 2);
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin", 2);
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			return false;
		} finally {
			try {
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

	public void ponerArchivo(String usuarioId, String modulo, String path,
			byte[] bit, String archivostatic, String context) {

		Connection con = null;
		try {
			con = cursor.getConnection();
			String archivosalida = Ruta.get(context,
					rb3.getString("reportes.PathReporte"));
			File f = new File(archivosalida);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(f + "\\"
					+ archivostatic);
			CopyUtils.copy(bit, fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public boolean activo() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String act = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("hilo_activo"));
			posicion = 1;
			rs = pst.executeQuery();
			if (rs.next())
				act = rs.getString(1);
			if (act.equals("0"))
				return false;
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
		return true;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public boolean ejecutarJasper(String usuarioId, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String act = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(usuarioId));
			rs = pst.executeQuery();

			if (!rs.next())
				return false;
			rs.close();
			pst.close();

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
		return true;
	}

}
