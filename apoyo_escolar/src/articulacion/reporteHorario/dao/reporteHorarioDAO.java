package articulacion.reporteHorario.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import articulacion.reporteHorario.beans.FiltroBeanReporte;
import articulacion.reporteHorario.beans.FiltroBeanReporteDocente;
import articulacion.reporteHorario.beans.FiltroBeanReporteEspacio;

/**
 * @author Latined
 * 
 *         Realiza todas las operaciones que interactuan con la base de datos
 * 
 */
public class reporteHorarioDAO extends Dao {
	public String sentencia;
	public String buscar;
	private ResourceBundle rb3;
	private byte[] bytes;
	private Zip zip;
	private static final int codigo_tipo = 3;

	public reporteHorarioDAO(Cursor cur) {
		super(cur);
		bytes = null;
		rb3 = ResourceBundle
				.getBundle("articulacion.reporteHorario.bundle.reporteHorario");
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
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
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
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
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

	public boolean updatePuestoReporte(String puesto, String nombrereportezip,
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

	public Long getDaneInstitucion(Long codigoInstitucion) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		Long daneInstitucion = new Long(0);

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("getDaneInstitucion"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, codigoInstitucion.longValue());
			rs = pst.executeQuery();
			if (rs.next())
				daneInstitucion = new Long(rs.getLong(1));
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando getDaneInstitucion");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return daneInstitucion;
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
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/
	public byte[] getArrayBytes(Long idInstitucion, String idUsuario,
			String nombreReporteZip, File reportFile, Map parameterscopy,
			String modulo) {
		byte[] bytes = null;
		Connection con = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				// System.out.println("***Se mandn ejecutar el jasper****");
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
						parameterscopy, con);
			}
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, idUsuario,
					rb3.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(idUsuario,
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			ponerReporteMensaje("2", modulo, idUsuario,
					rb3.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(String.valueOf(idUsuario),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, idUsuario,
					rb3.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(idUsuario,
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
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
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes, y luego poder ser visualizado en <BR>
	 * la lista de reportes generados <BR>
	 * 
	 * @param byte bit
	 **/

	public void ponerArchivo(byte[] bit, String archivostatic, String context) {

		Connection con = null;
		try {
			con = cursor.getConnection();
			String archivosalida = Ruta.get(context,
					rb3.getString("reportes.PathReporte"));
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public boolean isEmpty(FiltroBeanReporte filtroBeanReporte)
			throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("isEmpty"));
			posicion = 1;
			pst.setLong(posicion++, filtroBeanReporte.getInstitucion()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporte.getSede().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getJornada().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getGrado().longValue());
			pst.setInt(posicion++, filtroBeanReporte.getGrupo().intValue());
			pst.setLong(posicion++, filtroBeanReporte.getVigencia().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getInstitucion()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporte.getSede().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getJornada().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getGrado().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getMetodologia()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporte.getVigencia().longValue());
			pst.setInt(posicion++, filtroBeanReporte.getGrupo().intValue());
			pst.setLong(posicion++, filtroBeanReporte.getInstitucion()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporte.getMetodologia()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporte.getGrado().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getInstitucion()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporte.getSede().longValue());
			pst.setLong(posicion++, filtroBeanReporte.getJornada().longValue());
			rs = pst.executeQuery();
			if (rs.next())
				return false;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}

	public boolean isEmptyDoc(FiltroBeanReporteDocente filtroBeanReporteDocente)
			throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("isEmptyDoc"));
			posicion = 1;
			pst.setLong(posicion++, filtroBeanReporteDocente.getDocente()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporteDocente.getInstitucion()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporteDocente.getSede()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporteDocente.getJornada()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporteDocente.getEstructura()
					.longValue());
			rs = pst.executeQuery();
			if (rs.next())
				return false;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}

	public boolean isEmptyEsp(FiltroBeanReporteEspacio filtroBeanReporteEspacio)
			throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("isEmptyEsp"));
			posicion = 1;
			pst.setInt(posicion++, filtroBeanReporteEspacio.getEspacioFisico()
					.intValue());
			pst.setLong(posicion++, filtroBeanReporteEspacio.getInstitucion()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporteEspacio.getSede()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporteEspacio.getJornada()
					.longValue());
			pst.setLong(posicion++, filtroBeanReporteEspacio.getEstructura()
					.longValue());
			rs = pst.executeQuery();
			if (rs.next())
				return false;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}

	public boolean isWorking(String idUsuario, String modulo) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("isWorking"));
			posicion = 1;
			pst.setString(posicion++, idUsuario);
			pst.setString(posicion++, modulo);
			rs = pst.executeQuery();
			if (rs.next())
				return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return false;
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

	public boolean ejecutarJasper(String usuarioId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String act = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("queryEjecutarjasper"));
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

	public void updateReporteFecha(String preparedstatement, String modulo,
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
			// System.out.println("nnSe actualizn la fecha en la tabla Reporte!!!!");

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

	public void updateReporteEstado(String modulo, String us, String rec,
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

}
