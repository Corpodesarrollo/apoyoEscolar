package siges.boletines.dao;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		10/11/2020	JOHN HEREDIA	Se agregó el método: getParamsEstudiante3, para realizar un ajuste en los Certificados

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;


import siges.boletines.ControllerAuditoriaReporte;

import siges.boletines.beans.Estudiante;
import siges.boletines.beans.FiltroBeanFormulario;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.vo.DatosBoletinVO;
import siges.boletines.vo.ParamsVO;
import siges.boletines.vo.ReporteVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.evaluacion.beans.NivelEvalVO;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.login.beans.Login;

/**
 * @author Latined
 * 
 *         Realiza todas las operaciones que interactuan con la base de datos
 * 
 */
public class ReporteLogrosDAO extends Dao {
	public String sentencia;
	public String buscar;
	private ResourceBundle rb3;
	private ResourceBundle rbBol;
	private static final int codigo_tipo = 3;

	public ReporteLogrosDAO(Cursor cur) {
		super(cur);
		rb3 = ResourceBundle.getBundle("preparedstatements_logros_pendientes");
		rbBol = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
	}
	
	/**
	 * Inserta en la tabla Consulta Externa un nuevo registro de un archivo fisico generado
	 * 
	 */
	
	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}
	
	
	public void insertarConsultasExternas(long consecutivoConsultaExterna ,String rutaArchivo, String nombreArchivo, String extensionArchivo, String tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			
			pst = con.prepareStatement(rbBol.getString("insertar_consulta_externa"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, tipo);
			pst.setLong(posicion++, consecutivoConsultaExterna);
			pst.setString(posicion++, rutaArchivo);
			pst.setString(posicion++, nombreArchivo);
			pst.setString(posicion++, extensionArchivo);
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
	
	public void updateConsultasExternas(long consecutivoConsultaExterna, String rutaArchivo, String nombreArchivo, String extensionArchivo, String tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbBol.getString("actualizar_consulta_externa"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, tipo);
			pst.setString(posicion++, rutaArchivo);
			pst.setString(posicion++, nombreArchivo);
			pst.setString(posicion++, extensionArchivo);
			pst.setLong(posicion++, consecutivoConsultaExterna);
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
	
	public long getConsecutivoConsultasExternas() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long consecutivoConsultaExterna = 0;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbBol.getString("consecutivo_consulta_externa"));
			pst.clearParameters();
			rs = pst.executeQuery();
			if (rs.next())
				consecutivoConsultaExterna = rs.getLong(1);
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
		return consecutivoConsultaExterna;
	}

	public boolean insertarDatosReporte(FiltroBeanFormulario filtro,
			String InstId, java.sql.Timestamp f2, String archivozip,
			String archivo, String UsuarioId) {
		int posicion = 1;
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("insert_datos_reporte_logro"));
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
			pst.setString(posicion++,
					!filtro.getEstudiante().equals("") ? filtro.getEstudiante()
							: "");
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo().trim()));
			pst.setString(posicion++,
					!filtro.getPeriodonom().trim().equals("") ? filtro
							.getPeriodonom().trim() : "");
			pst.setLong(posicion++, Long.parseLong(!filtro.getOrden().equals(
					"-9") ? filtro.getOrden() : "-9"));
			pst.setString(
					posicion++,
					f2.toString().replace(' ', '_').replace(':', '-')
							.replace('.', '-'));
			pst.setString(posicion++, archivozip);
			pst.setString(posicion++, archivo);
			pst.setLong(posicion++, Long.parseLong("-1"));
			pst.setString(posicion++, UsuarioId);
			pst.executeUpdate();
			pst.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener información académica. Posible problema: ");
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

	public String colaReportes() {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String cant = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("puesto_del_reporte"));
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
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public boolean updateColaReportesEstadoCero(String prepared) {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
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
			String estadoanterior, String user, String prepared) {
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
			String nombrereporte, String user, String prepared) {
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

	public String[][] reporteGenerar(FiltroBeanFormulario filtro,
			String modulo, String prepared) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[][] array1 = null;
		Collection list;
		Object[] o = new Object[2];

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			pst.clearParameters();
			rs = pst.executeQuery();

			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int i = 0;
			while (rs.next()) {
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}

			if (!list.isEmpty()) {
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
					filtro.getUsuarioid(), "update_datos_reporte");
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin");
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

	public boolean updateColaReportes(String prepared) {

		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
			pst.clearParameters();
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
					.prepareCall("{call PK_LOGROS_PEND.logros_pend_borrar(?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			cstmt.setInt(posicion++, codigo_tipo);
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
			cstmt = con
					.prepareCall("{call PK_LOGROS_PEND.logros_pend_vaciar(?)}");
			cstmt.setInt(posicion++, codigo_tipo);
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
			String nombrereporte, String user, String prepared) {
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
		String vig = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("vigencia"));
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
	public boolean callableStatements(FiltroBeanFormulario filtro,
			String vigencia, String modulo, int tipo) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call PK_LOGROS_PEND.logros_pendientes(?,?,?,?,?,?,?,?,?,?,?,?)}");
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
			cstmt.setInt(posicion++, tipo);
			cstmt.executeUpdate();
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
					filtro.getUsuarioid(), "update_datos_reporte");
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin");
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			limpiarTablas(filtro.getUsuarioid());
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
					filtro.getUsuarioid(), "update_datos_reporte");
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			limpiarTablas(filtro.getUsuarioid());
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
	public byte[] getArrayBytes(FiltroBeanFormulario filtro, File reportFile,
			Map parameterscopy, String modulo) {
		byte[] bytes = null;
		Connection con = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
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
					filtro.getUsuarioid(), "update_datos_reporte");
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin");
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			limpiarTablas(filtro.getUsuarioid());
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
					filtro.getUsuarioid(), "update_datos_reporte");
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin");
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			limpiarTablas(filtro.getUsuarioid());
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
					filtro.getUsuarioid(), "update_datos_reporte");
			updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrereportezip(), filtro.getUsuarioid(),
					"update_datos_reporte_fecha_fin");
			updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			limpiarTablas(filtro.getUsuarioid());
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

	public void ponerArchivo(String usuarioId, String modulo, String path,
			byte[] bit, String archivostatic, String context) {

		Connection con = null;
		try {
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
			limpiarTablas(usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(usuarioId);
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
		String act = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("hilo_activo"));
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

	public boolean ejecutarJasper(String usuarioId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;

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

	/**
	 * @return
	 * @throws Exception
	 */
	public List getTiposDoc() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb3.getString("getTiposDoc"));
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				ItemVO n = new ItemVO();
				n.setCodigo(rs.getInt(posicion++));
				n.setNombre(rs.getString(posicion++));
				list.add(n);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		// System.out.println("list " + list.size());
		return list;
	}
	
	public Estudiante getDatosEstudiante(int tipoDocumento,String noDocumento) throws Exception{
		
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Estudiante estudianteConsultado =null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			
			st = cn.prepareStatement(rbBol.getString("getEstudiante"));
			st.setInt(1, tipoDocumento);
			st.setString(2, noDocumento);
			rs = st.executeQuery();
			while (rs.next()) {
				estudianteConsultado = new Estudiante();
				estudianteConsultado.setEstCodigo(rs.getLong(posicion++));
				estudianteConsultado.setEstTipoDocumento(rs.getInt(posicion++));
				estudianteConsultado.setEstNoDocumento(rs.getString(posicion++));
				estudianteConsultado.setEstNombre1(rs.getString(posicion++));
				estudianteConsultado.setEstNombre2(rs.getString(posicion++));
				estudianteConsultado.setEstApellido1(rs.getString(posicion++));
				estudianteConsultado.setEstApellido2(rs.getString(posicion++));
				estudianteConsultado.setEstCodigoFamilia(rs.getLong(posicion++));
				estudianteConsultado.setEstVigencia(rs.getInt(posicion++));
				estudianteConsultado.setEstInstitucion(rs.getLong(posicion++));
				estudianteConsultado.setEstSede(rs.getLong(posicion++));
				estudianteConsultado.setEstJornada(rs.getLong(posicion++));
				estudianteConsultado.setEstGrado(rs.getLong(posicion++));
				estudianteConsultado.setEstGrupo(rs.getLong(posicion++));
				estudianteConsultado.setEstMetodologia(rs.getLong(posicion++));
				
			}
			
			rs.close();
			st.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return estudianteConsultado;
	}
	
public Estudiante getDatosEstudiante2(String noDocumento) throws Exception{
		
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Estudiante estudianteConsultado =null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			
			st = cn.prepareStatement(rbBol.getString("getEstudiante2"));
			st.setString(1, noDocumento);
			rs = st.executeQuery();
			while (rs.next()) {
				estudianteConsultado = new Estudiante();
				estudianteConsultado.setEstCodigo(rs.getLong(posicion++));
				estudianteConsultado.setEstTipoDocumento(rs.getInt(posicion++));
				estudianteConsultado.setEstNoDocumento(rs.getString(posicion++));
				estudianteConsultado.setEstNombre1(rs.getString(posicion++));
				estudianteConsultado.setEstNombre2(rs.getString(posicion++));
				estudianteConsultado.setEstApellido1(rs.getString(posicion++));
				estudianteConsultado.setEstApellido2(rs.getString(posicion++));
				estudianteConsultado.setEstCodigoFamilia(rs.getLong(posicion++));
				estudianteConsultado.setEstVigencia(rs.getInt(posicion++));
				estudianteConsultado.setEstInstitucion(rs.getLong(posicion++));
				estudianteConsultado.setEstSede(rs.getLong(posicion++));
				estudianteConsultado.setEstJornada(rs.getLong(posicion++));
				estudianteConsultado.setEstGrado(rs.getLong(posicion++));
				estudianteConsultado.setEstGrupo(rs.getLong(posicion++));
				estudianteConsultado.setEstMetodologia(rs.getLong(posicion++));
				
			}
			
			rs.close();
			st.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return estudianteConsultado;
	}
	
	
	public List getHijosFamilia(long idUsuario) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {
			long noDocumentoUsuario = 0;
			cn = cursor.getConnection();
			st = cn.prepareStatement("SELECT USUPERNUMDOCUM FROM USUARIO WHERE USULOGIN = ?");
			st.setString(1, ""+idUsuario);
			rs = st.executeQuery();
			while (rs.next()) {
				noDocumentoUsuario = rs.getLong(1);
			}
			
			rs.close();
			st.close();
			
			st = cn.prepareStatement(rbBol.getString("getHijosMadreDeFamilia"));
			st.setString(1, ""+noDocumentoUsuario);
			rs = st.executeQuery();
			while (rs.next()) {
				Estudiante estudiante = new Estudiante();
				posicion = 1;
				estudiante.setEstGrupo(rs.getLong(posicion++));
				estudiante.setEstCodigo(rs.getLong(posicion++));
				estudiante.setEstTipoDocumento(rs.getInt(posicion++));
				estudiante.setEstNoDocumento(rs.getString(posicion++));
				estudiante.setEstNombre1(rs.getString(posicion++));
				estudiante.setEstNombre2(rs.getString(posicion++));
				estudiante.setEstApellido1(rs.getString(posicion++));
				estudiante.setEstApellido2(rs.getString(posicion++));
				estudiante.setEstCodigoFamilia(rs.getLong(posicion++));
				estudiante.setEstVigencia(rs.getInt(posicion++));
				list.add(estudiante);
			}
			
			rs.close();
			st.close();
			
			
			st = cn.prepareStatement(rbBol.getString("getHijosPadreDeFamilia"));
			st.setString(1, ""+noDocumentoUsuario);
			rs = st.executeQuery();
			while (rs.next()) {
				Estudiante estudiante = new Estudiante();
				posicion = 1;
				estudiante.setEstGrupo(rs.getLong(posicion++));
				estudiante.setEstCodigo(rs.getLong(posicion++));
				estudiante.setEstTipoDocumento(rs.getInt(posicion++));
				estudiante.setEstNoDocumento(rs.getString(posicion++));
				estudiante.setEstNombre1(rs.getString(posicion++));
				estudiante.setEstNombre2(rs.getString(posicion++));
				estudiante.setEstApellido1(rs.getString(posicion++));
				estudiante.setEstApellido2(rs.getString(posicion++));
				estudiante.setEstCodigoFamilia(rs.getLong(posicion++));
				estudiante.setEstVigencia(rs.getInt(posicion++));
				
				boolean existe = false;
				Iterator iteradorLista = list.iterator();
				while(iteradorLista.hasNext()){
					Estudiante estudianteComparar = (Estudiante)iteradorLista.next();
					if(estudiante.getEstNoDocumento().equalsIgnoreCase(estudianteComparar.getEstNoDocumento())){
						existe = true;
						break;
					}
				}
				if(!existe){
					list.add(estudiante);
				}
			}
			
			rs.close();
			st.close();
			
			st = cn.prepareStatement(rbBol.getString("getHijosEstudiantesAcudiente"));
			st.setString(1, ""+noDocumentoUsuario);
			rs = st.executeQuery();
			while (rs.next()) {
				Estudiante estudiante = new Estudiante();
				posicion = 1;
				estudiante.setEstGrupo(rs.getLong(posicion++));
				estudiante.setEstCodigo(rs.getLong(posicion++));
				estudiante.setEstTipoDocumento(rs.getInt(posicion++));
				estudiante.setEstNoDocumento(rs.getString(posicion++));
				estudiante.setEstNombre1(rs.getString(posicion++));
				estudiante.setEstNombre2(rs.getString(posicion++));
				estudiante.setEstApellido1(rs.getString(posicion++));
				estudiante.setEstApellido2(rs.getString(posicion++));
				estudiante.setEstCodigoFamilia(rs.getLong(posicion++));
				estudiante.setEstVigencia(rs.getInt(posicion++));
				
				boolean existe = false;
				Iterator iteradorLista = list.iterator();
				while(iteradorLista.hasNext()){
					Estudiante estudianteComparar = (Estudiante)iteradorLista.next();
					if(estudiante.getEstNoDocumento().equalsIgnoreCase(estudianteComparar.getEstNoDocumento())){
						existe = true;
						break;
					}
				}
				if(!existe){
					list.add(estudiante);
				}
			}
			rs.close();
			st.close();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		// System.out.println("list " + list.size());
		return list;
	}
	
	

	//
	/**
	 * @return
	 * @throws Exception
	 */
	public synchronized long getConsecReporte() throws Exception {
		// System.out
		// .println("BOLETINES Y RESUMENES: ENTRO CODIGO NUEVO CONSEC DAO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		long codigo = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("boletinRepGetConsec"));
			rs = st.executeQuery();
			if (rs.next())
				codigo = rs.getLong(1);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return codigo;
	}

	//
	/**
	 * @return
	 * @throws Exception
	 */
	public String getResolInst(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String resol = "";
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("boletinGetResolInst"));
			st.setLong(1, inst);
			rs = st.executeQuery();
			if (rs.next())
				resol = rs.getString(1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return resol;
	}

	public FiltroBeanReports paramsInst(FiltroBeanReports filtro) {
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Object[] o;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbBol
					.getString("getParametrosInstitucion"));
			pst.setLong(1, filtro.getFilVigencia());
			pst.setLong(2, filtro.getFilInst());
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			int j = 0, f = 0;
			if (rs.next()) {
				f = 1;
				filtro.setNumPer(rs.getLong(f++));
				filtro.setNomPerFinal(rs.getString(f++));
				filtro.setNivelEval(rs.getInt(f++));
			}
			rs.close();
			pst.close();

		} catch (Exception e) {
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return filtro;
	}
	
	
	
	
	
	// METODO TIPO DE EVALUACION PREESCOLAR

	// CAMBIOS EVALUACION PARAMETROS INSTITUCION

	public int getTipoEval(FiltroBeanReports filtroReporte, Login login) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		int i = 1;
		int tipoEval = 0;
		long codigoNivelEval = 0;
		
		NivelEvalVO nivelEval = new NivelEvalVO();

		try {
			
			cn = cursor.getConnection();
			
			st = cn.prepareStatement(rbBol.getString("getNivelEval"));
			st.setLong(i++, filtroReporte.getFilVigencia());
			st.setLong(i++, Long.parseLong(login.getInstId()));
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			
			i = 1;
			// Traer parametros de nivel evaluacion
			st = cn.prepareStatement(rbBol.getString("getParamsNivelEval"));
			st.setLong(i++, codigoNivelEval);
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));
			}
			rs.close();
			st.close();

			// TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql = rbBol.getString("getTipoEval.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rbBol.getString("getTipoEval.sede");
			
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rbBol.getString("getTipoEval.jornada");
			
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rbBol.getString("getTipoEval.metod");
			
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rbBol.getString("getTipoEval.nivel");
			
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rbBol.getString("getTipoEval.grado");
			
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, filtroReporte.getFilVigencia());
			st.setLong(i++, Long.parseLong(login.getInstId()));

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, Long.parseLong(login.getSedeId()));
			
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, Long.parseLong(login.getJornadaId()));
			
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, Long.parseLong(filtroReporte.getMetodologia()));
			
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(Integer.parseInt(filtroReporte.getGrado()));
				st.setInt(i++, nivelGrado);
			}
			
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, Long.parseLong(filtroReporte.getGrado()));

			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				tipoEval = rs.getInt(i++);
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return tipoEval;
		
	}
	
	
	
	
	
	public int getNivelGrado(int grado) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("getNivelGrado"));
			st.setInt(i++, grado);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	//
	/**
	 * @return
	 * @throws Exception
	 */
	public FiltroBeanReports getParamsEstudiante(FiltroBeanReports filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("gerParamsEstudiante"));
			st.setLong(1, filtro.getFilTipoDoc());
			st.setString(2, filtro.getId());
			rs = st.executeQuery();
			if (rs.next()) {
				int pos = 1;
				filtro.setInstitucion(rs.getString(pos++));
				filtro.setSede(rs.getString(pos++));
				filtro.setJornada(rs.getString(pos++));
				filtro.setMetodologia(rs.getString(pos++));
				filtro.setGrado(rs.getString(pos++));
				filtro.setGrupo(rs.getString(pos++));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return filtro;
	}

	//
	/**
	 * @return
	 * @throws Exception
	 */
	public FiltroBeanReports getParamsEstudiante_anterior(
			FiltroBeanReports filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol
					.getString("gerParamsEstudiante_anterior"));
			st.setLong(1, filtro.getFilTipoDoc());
			st.setString(2, filtro.getId());
			st.setLong(3, filtro.getFilVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				int pos = 1;
				filtro.setInstitucion(rs.getString(pos++));
				filtro.setSede(rs.getString(pos++));
				filtro.setJornada(rs.getString(pos++));
				filtro.setMetodologia(rs.getString(pos++));
				filtro.setGrado(rs.getString(pos++));
				filtro.setGrupo(rs.getString(pos++));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return filtro;
	}

	// CONSULTAS HILO BOLETINES

	/**
	 * @return
	 * @throws Exception
	 */
	public List getSolicitudes() {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosBoletinVO db = null;
		List listdb = new ArrayList();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("boletin_a_generar"));
			st.clearParameters();
			rs = st.executeQuery();
			while (rs.next()) {
				int pos = 1;
				db = new DatosBoletinVO();
				db.setDABOLINST(rs.getLong(pos++));
				db.setDABOLSEDE(rs.getLong(pos++));
				db.setDABOLJORNADA(rs.getLong(pos++));
				db.setDABOLMETODOLOGIA(rs.getLong(pos++));
				db.setDABOLGRADO(rs.getLong(pos++));
				db.setDABOLGRUPO(rs.getLong(pos++));
				db.setDABOLPERIODO(rs.getLong(pos++));
				db.setDABOLPERIODONOM(rs.getString(pos++));
				db.setDABOLCEDULA(rs.getString(pos++));
				db.setDABOLFECHA(rs.getString(pos++));
				db.setDABOLUSUARIO(rs.getString(pos++));
				db.setDABOLNOMBREZIP(rs.getString(pos++));
				db.setDABOLNOMBREPDF(rs.getString(pos++));
				db.setDABOLNOMBREPDFPRE(rs.getString(pos++));
				db.setDABOLDESCRIPTORES(rs.getInt(pos++));
				db.setDABOLLOGROS(rs.getInt(pos++));
				db.setDABOLTOTLOGROS(rs.getInt(pos++));
				db.setDABOLAREA(rs.getInt(pos++));
				db.setDABOLASIG(rs.getInt(pos++));
				db.setDABOLCONSEC(rs.getLong(pos++));
				db.setDABOLINSNOMBRE(rs.getString(pos++));
				db.setDABOLSEDNOMBRE(rs.getString(pos++));
				db.setDABOLJORNOMBRE(rs.getString(pos++));
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
				db.setDABOLLOGROPEND(rs.getInt(pos++));
				db.setDABOLFIRREC(rs.getInt(pos++));
				db.setDABOLFIRDIR(rs.getInt(pos++));
				listdb.add(db);

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			listdb = null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			listdb = null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listdb;
	}

	public List getSolicitudesCertificados() {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosBoletinVO db = null;
		List listdb = new ArrayList();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("certificado_a_generar"));
			st.clearParameters();
			rs = st.executeQuery();
			while (rs.next()) {
				int pos = 1;
				db = new DatosBoletinVO();
				db.setDABOLINST(rs.getLong(pos++));
				db.setDABOLSEDE(rs.getLong(pos++));
				db.setDABOLJORNADA(rs.getLong(pos++));
				db.setDABOLMETODOLOGIA(rs.getLong(pos++));
				db.setDABOLGRADO(rs.getLong(pos++));
				db.setDABOLGRUPO(rs.getLong(pos++));
				db.setDABOLPERIODO(rs.getLong(pos++));
				db.setDABOLPERIODONOM(rs.getString(pos++));
				db.setDABOLCEDULA(rs.getString(pos++));
				db.setDABOLFECHA(rs.getString(pos++));
				db.setDABOLUSUARIO(rs.getString(pos++));
				db.setDABOLNOMBREZIP(rs.getString(pos++));
				db.setDABOLNOMBREPDF(rs.getString(pos++));
				db.setDABOLNOMBREPDFPRE(rs.getString(pos++));
				db.setDABOLDESCRIPTORES(rs.getInt(pos++));
				db.setDABOLLOGROS(rs.getInt(pos++));
				db.setDABOLTOTLOGROS(rs.getInt(pos++));
				db.setDABOLAREA(rs.getInt(pos++));
				db.setDABOLASIG(rs.getInt(pos++));
				db.setDABOLCONSEC(rs.getLong(pos++));
				db.setDABOLINSNOMBRE(rs.getString(pos++));
				db.setDABOLSEDNOMBRE(rs.getString(pos++));
				db.setDABOLJORNOMBRE(rs.getString(pos++));
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
				db.setDANE12(rs.getString(pos++));
				listdb.add(db);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			listdb = null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			listdb = null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listdb;
	}

	public List getSolicitudesLibros() {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosBoletinVO db = null;
		List listdb = new ArrayList();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("libro_a_generar"));
			st.clearParameters();
			rs = st.executeQuery();
			while (rs.next()) {
				int pos = 1;
				db = new DatosBoletinVO();
				db.setDABOLINST(rs.getLong(pos++));
				db.setDABOLSEDE(rs.getLong(pos++));
				db.setDABOLJORNADA(rs.getLong(pos++));
				db.setDABOLMETODOLOGIA(rs.getLong(pos++));
				db.setDABOLGRADO(rs.getLong(pos++));
				db.setDABOLGRUPO(rs.getLong(pos++));
				db.setDABOLPERIODO(rs.getLong(pos++));
				db.setDABOLPERIODONOM(rs.getString(pos++));
				db.setDABOLCEDULA(rs.getString(pos++));
				db.setDABOLFECHA(rs.getString(pos++));
				db.setDABOLUSUARIO(rs.getString(pos++));
				db.setDABOLNOMBREZIP(rs.getString(pos++));
				db.setDABOLNOMBREPDF(rs.getString(pos++));
				db.setDABOLNOMBREPDFPRE(rs.getString(pos++));
				db.setDABOLDESCRIPTORES(rs.getInt(pos++));
				db.setDABOLLOGROS(rs.getInt(pos++));
				db.setDABOLTOTLOGROS(rs.getInt(pos++));
				db.setDABOLAREA(rs.getInt(pos++));
				db.setDABOLASIG(rs.getInt(pos++));
				db.setDABOLCONSEC(rs.getLong(pos++));
				db.setDABOLINSNOMBRE(rs.getString(pos++));
				db.setDABOLSEDNOMBRE(rs.getString(pos++));
				db.setDABOLJORNOMBRE(rs.getString(pos++));
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
				db.setDABOLFIRREC(rs.getInt(pos++));
				listdb.add(db);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			listdb = null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			listdb = null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listdb;
	}

	/**
	 * Funcinn: Ejecuta los Procedimientos para llenar tablas temporales para
	 * generar el boletin<BR>
	 * 
	 * @param DatosBoletinVO
	 *            reporte
	 * @param ReporteEstadisticoVO
	 *            rep
	 **/
	public boolean llenarTablas(DatosBoletinVO reporte, ReporteVO rep) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (reporte.getDABOLCONSEC() > 0) {
				con = cursor.getConnection();
				cstmt = con.prepareCall("{call PK_BOLETIN.PK_BOL_INICIAL(?)}");
				cstmt.setLong(posicion++, reporte.getDABOLCONSEC());
				cstmt.executeUpdate();
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out.println("REP BOLETINES:LLENAR TABLAS: excepcinnSQL: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			System.out.println("REP BOLETINES:LLENAR TABLAS: excepcinn: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
	 * Funcinn: Ejecuta los Procedimientos para llenar tablas temporales para
	 * generar el boletin<BR>
	 * 
	 * @param DatosBoletinVO
	 *            reporte
	 * @param ReporteEstadisticoVO
	 *            rep
	 **/
	public boolean llenarTablasDim(DatosBoletinVO reporte, ReporteVO rep) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (reporte.getDABOLCONSEC() > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con
						.prepareCall("{call PK_BOLETIN_DIMENSIONES.BOL_DIM_INICIAL(?)}");
				cstmt.setLong(posicion++, reporte.getDABOLCONSEC());
				cstmt.executeUpdate();
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out.println("REP BOLETINES:LLENAR TABLAS: excepcinnSQL: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablasDim(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			System.out.println("REP BOLETINES:LLENAR TABLAS: excepcinn: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablasDim(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
	 * Funcinn: Ejecuta los Procedimientos para llenar tablas temporales para
	 * generar el boletin<BR>
	 * 
	 * @param DatosBoletinVO
	 *            reporte
	 * @param ReporteEstadisticoVO
	 *            rep
	 **/
	public boolean llenarTablasCertLibro(DatosBoletinVO reporte, ReporteVO rep) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (reporte.getDABOLCONSEC() > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con.prepareCall("{call PK_LIBRO.PK_BOL_INICIAL(?)}");
				cstmt.setLong(posicion++, reporte.getDABOLCONSEC());
				cstmt.executeUpdate();
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out
					.println("REP CERTFICADOS LIBROS:LLENAR TABLAS: excepcinnSQL: "
							+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			System.out
					.println("REP CERTFICADOS LIBROS:LLENAR TABLAS: excepcinn: "
							+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
	 * Funcinn: Ejecuta los Procedimientos para llenar tablas temporales para
	 * generar el boletin<BR>
	 * 
	 * @param DatosBoletinVO
	 *            reporte
	 * @param ReporteEstadisticoVO
	 *            rep
	 **/
	public boolean llenarTablasDimCertLibro(DatosBoletinVO reporte,
			ReporteVO rep) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (reporte.getDABOLCONSEC() > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con
						.prepareCall("{call PK_LIBRO_DIMENSIONES.PK_BOL_INICIAL(?)}");
				cstmt.setLong(posicion++, reporte.getDABOLCONSEC());
				cstmt.executeUpdate();
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out
					.println("REP CERTFICADOS LIBROS DIM:LLENAR TABLAS: excepcinnSQL: "
							+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablasDim(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			System.out
					.println("REP CERTFICADOS LIBROS DIM:LLENAR TABLAS: excepcinn: "
							+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN,
						reporte.getDABOLFECHAGEN(), reporte.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablasDim(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
	 * @return
	 * @throws Exception
	 */
	public boolean updateSolicitud(long consec, int estado, String fGen,
			String fFin) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("boletines.updateDaBol"));
			st.setInt(posicion++, estado);
			st.setString(posicion++, fGen);
			st.setString(posicion++, fFin);
			st.setLong(posicion++, consec);
			st.executeUpdate();

			auditoriaReporte.insertarAuditoria("INDEFINIDO", 0, "Reporte estado: " + estado,consec);
			
			return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean updateReporte(ReporteVO reporte) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			java.sql.Date fecActual = new java.sql.Date(new Date().getTime());
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbBol.getString("boletines.updateReporte"));
			st.setInt(posicion++, reporte.getEstado());
			st.setString(posicion++, reporte.getMensaje());
			st.setDate(posicion++, fecActual);
			st.setString(posicion++, reporte.getUsuario());
			st.setString(posicion++, reporte.getRecurso());
			st.setString(posicion++, reporte.getTipo());
			st.setString(posicion++, reporte.getNombre());
			st.setInt(posicion++, reporte.getModulo());
			st.executeUpdate();
			return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
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
	public DatosBoletinVO datosConv(DatosBoletinVO rep, ReporteVO reporte) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (rep != null) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rbBol
						.getString("boletines.getDatosConvenciones"));
				posicion = 1;
				pst.setLong(1, rep.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					rep.setDABOLCONVINST(rs.getString(posicion++));
					rep.setDABOLCONVMEN(rs.getString(posicion++));

				}
			}
			// return rep;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR SQL: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				updateReporte(reporte);
				limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return rep;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR EXCP: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				updateReporte(reporte);
				limpiarTablas(rep.getDABOLCONSEC());
				limpiarTablasDim(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return rep;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return rep;
	}// fin de preparedstatements

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
	public int validarEstadoReporte(long consec) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int estado = 0;
		try {
			if (consec > 0) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rbBol
						.getString("validarEstadoReporte"));
				pst.setLong(1, consec);
				rs = pst.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			limpiarTablas(consec);
			limpiarTablasDim(consec);
			return estado;
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consec);
			limpiarTablasDim(consec);
			System.out
					.println("BOLETINES: nSe limpiaron las tablas despuns de generada la excepcinn!");
			return estado;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return estado;
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
	public boolean validarDatosReporte(DatosBoletinVO rep) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean cont = true;
		try {
			if (rep.getDABOLCONSEC() > 0) {
				String sql = rbBol.getString("validarDatosBoletin");
				con = cursor.getConnection();
				pst = con.prepareStatement(sql);
				posicion = 1;
				pst.setLong(posicion++, rep.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next())
					cont = true;
				else
					cont = false;
			} else
				cont = false;
		} catch (SQLException e) {
			e.printStackTrace();
			limpiarTablas(rep.getDABOLCONSEC());
			limpiarTablasDim(rep.getDABOLCONSEC());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(rep.getDABOLCONSEC());
			limpiarTablasDim(rep.getDABOLCONSEC());
			System.out
					.println("BOLETINES: consec:"
							+ rep.getDABOLCONSEC()
							+ "nSe limpiaron las tablas despuns de generada la excepcinn!");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return cont;
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablas(long consec) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN.pk_bol_borrar_tablas(?)}");
			cstmt.setLong(posicion++, consec);
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

	public void limpiarTablasDim(long consec) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN_DIMENSIONES.PK_BOL_DIM_BORRAR_TABLAS(?)}");
			cstmt.setLong(posicion++, consec);
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

	// FIN CONSULTAS HILO BOLETINES
	
	
	
	
	//
	/**
	 * @return
	 * @throws Exception
	 */
	public FiltroBeanReports getParamsEstudiante3(FiltroBeanReports filtro) throws Exception {
		
	    Connection cn = null;
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    
	    try {
	    	
	        cn = cursor.getConnection();
	        st = cn.prepareStatement(rbBol.getString("gerParamsEstudiante3"));
	        
	        st.setLong(1, filtro.getFilTipoDoc());
	        st.setString(2, filtro.getId());
	        st.setLong(3, filtro.getFilVigencia());
	        rs = st.executeQuery();
	        
	        if (rs.next()) {
	            int pos = 1;
	            filtro.setInstitucion(rs.getString(pos++));
	            filtro.setSede(rs.getString(pos++));
	            filtro.setJornada(rs.getString(pos++));
	            filtro.setMetodologia(rs.getString(pos++));
	            filtro.setGrado(rs.getString(pos++));
	            filtro.setGrupo(rs.getString(pos++));
	        }	 

	    } catch (SQLException sqle) {
	        sqle.printStackTrace();
	        throw new Exception("Error de datos: " + sqle.getMessage());
	    } catch (Exception sqle) {
	        sqle.printStackTrace();
	        throw new Exception("Error interno: " + sqle.getMessage());
	    } finally {
	        try {
	            OperacionesGenerales.closeResultSet(rs);
	            OperacionesGenerales.closeStatement(st);
	            OperacionesGenerales.closeConnection(cn);
	        } catch (InternalErrorException inte) {
	        }
	    }
	    
	    return filtro;
	    
	}
	
	

}
