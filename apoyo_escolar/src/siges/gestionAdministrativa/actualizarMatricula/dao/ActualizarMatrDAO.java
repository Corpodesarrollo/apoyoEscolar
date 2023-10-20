/**
 * 
 */
package siges.gestionAdministrativa.actualizarMatricula.dao;

//package siges.gestionAdministrativa.actualizarMatricula.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.actualizarMatricula.vo.CierreHilosVO;
import siges.gestionAdministrativa.actualizarMatricula.vo.ResultadoVO;

public class ActualizarMatrDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public ActualizarMatrDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.actualizarMatricula.bundle.actualizar");
	}

	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}

	public int callProcedimientoMatr(int vigencia) throws Exception {
		Connection cn = null;
		CallableStatement cst = null;

		int flag = 0;
		try {

			cn = cursor.getConnection();
			cst = cn.prepareCall(rb.getString("procedimientoMatricula"));
			cst.setInt(1, vigencia);
			cst.executeQuery();

			// System.out.println("Sin problema en callProcedimientoMatr");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return flag;
	}

	public int getParVigencia() throws Exception {
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;

		int flag = 0;
		try {
			cn = cursor.getConnection();
			pt = cn.prepareCall(rb.getString("getParVigencia"));
			rs = pt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return -9;
	}

	/**
	 * @function:
	 * @throws Exception
	 */
	public void llenarTablaDatosMatricula() throws Exception {
		Connection cn = null;
		CallableStatement cst = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		int vigencia = 0;
		long codJerarquia = 0;
		long codInstitucion = 0;
		try {
			cn = cursor.getConnection();

			// 1.Borrar
			st = cn.prepareStatement(rb.getString("dropSequences"));
			st.executeUpdate();
			st.close();

			// 2. Obtener codigo
			st = cn.prepareStatement(rb.getString("obtenerCodigoJerarquia"));
			posicion = 1;
			rs = st.executeQuery();

			if (rs.next()) {
				codJerarquia = rs.getLong(posicion++);
			}
			rs.close();
			st.close();

			// 3.Crear
			// System.out.println("Nuevo codigo para sequences Jerarquia:"
			// + codJerarquia);
			st = cn.prepareStatement("CREATE SEQUENCE CODIGO_JERARQUIA START WITH "
					+ codJerarquia
					+ " MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE NOCACHE NOORDER");

			posicion = 1;
			st.executeUpdate();
			st.close();

			// CREAR SECUECNIA DE INSTITUCION

			// 1.Borrar
			st = cn.prepareStatement(rb.getString("borrarSecInst"));
			st.executeUpdate();
			st.close();

			// 2. Obtener codigo
			st = cn.prepareStatement(rb.getString("selectMaxInstitucion"));
			posicion = 1;
			rs = st.executeQuery();

			if (rs.next()) {
				codInstitucion = rs.getLong(posicion++);
			}
			rs.close();
			st.close();

			// 3.Crear
			// System.out.println("Nuevo codigo para sequences Insititucion :"
			// + codInstitucion);
			st = cn.prepareStatement("CREATE SEQUENCE CODIGO_INSTITUCION START WITH "
					+ codInstitucion
					+ " MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE NOCACHE NOORDER");

			posicion = 1;
			st.executeUpdate();
			st.close();

			/**
			 * ESTADOS (-1)Nuevo, (0) Procesando, (1)Terminado, (2) Error
			 * */
			System.out
					.println("[APOYO] Hilo de cierre corriendo (ESTADO NUEVO)");

			st = cn.prepareStatement(rb.getString("actualizarEstadoTotal"));
			posicion = 1;
			st.setInt(posicion++, -1);
			st.executeUpdate();
			st.close();
			// traer al registro en estado 0 mas viejo
			st = cn.prepareStatement(rb.getString("getSolicitudCierre"));
			rs = st.executeQuery();
			if (rs.next()) {
				// usuario=rs.getLong(1);
				vigencia = rs.getInt(1);
			} else {
				return;
			}
			rs.close();
			st.close();

			// cambiar el estado a 1
			System.out
					.println("[APOYO] Hilo de cierre corriendo (ESTADO PROCESANDO)");
			st = cn.prepareStatement(rb.getString("actualizarEstadoCierre"));
			posicion = 1;

			st.setInt(posicion++, 0);
			// st.setLong(posicion++,usuario);
			st.setInt(posicion++, vigencia);
			// st.setInt(posicion++,-1);

			st.executeUpdate();
			st.close();
			// System.out.println("[DUC] hilo cierre: ESTADO PROCESANDO vigencia "
			// + vigencia);

			// ejecutar el call
			System.out
					.println("[APOYO] Hilo de ACTUALIZA_MATRICULA corriendo (ESTADO EJECUTANDO PROCEDIMINETO)");

			cst = cn.prepareCall(rb.getString("procedimientoMatricula"));
			posicion = 1;
			cst.setLong(posicion++, vigencia);
			cst.executeUpdate();
			System.out
					.println("[APOYO] hilo de ACTUALIZA_MATRICULA: ESTADO tabla actualizada");

			// cambiar el estado a 1
			System.out
					.println("[APOYO] Hilo de ACTUALIZA_MATRICULA (ESTADO EJECUTANDO FINALIZADO)");
			st = cn.prepareStatement(rb.getString("actualizarEstadoCierre"));
			posicion = 1;
			st.setInt(posicion++, 1);
			// st.setLong(posicion++,usuario);
			st.setInt(posicion++, vigencia);
			// st.setInt(posicion++,0);
			st.executeUpdate();
			st.close();
		} catch (Exception sqle) {
			System.out.println("Error en hillo cierre colegio: "
					+ sqle.getMessage());
			sqle.printStackTrace();
			// cambiar el estado a 2
			try {
				st = cn.prepareStatement(rb
						.getString("actualizarEstadoCierreError"));
				posicion = 1;
				st.setInt(posicion++, 2);
				st.setString(posicion++, sqle.getMessage());
				st.setInt(posicion++, vigencia);
				st.executeUpdate();
				st.close();
			} catch (Exception s) {
				s.printStackTrace();
				System.out.println("Error en hillo cierre colegio: "
						+ s.getMessage());
			}
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeCallableStatement(cst);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
	}

	/**
	 * @function:
	 * @return
	 * @throws Exception
	 */
	public List getCierreHilosVO() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		List listaHilos = new ArrayList();
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getCierreHilosVO"));
			rs = st.executeQuery();
			while (rs.next()) {
				CierreHilosVO cierreHilosVO = new CierreHilosVO();
				posicion = 1;

				// System.out.println(" Tabla hilos " + rs.getInt(1));
				cierreHilosVO.setVigencia(rs.getInt(posicion++));
				cierreHilosVO.setEstado(rs.getString(posicion++));
				cierreHilosVO.setFechaSistema(rs.getString(posicion++));
				cierreHilosVO.setMensaje(rs.getString(posicion++));
				cierreHilosVO.setCodigoEstado(rs.getInt(posicion++));
				listaHilos.add(cierreHilosVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();

		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		return listaHilos;
	}

	public List getlistaResultadoVO() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		List listaresultadoVO = new ArrayList();
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getlistaResultadoVO"));
			rs = st.executeQuery();
			while (rs.next()) {
				ResultadoVO resultadoVO = new ResultadoVO();
				posicion = 1;

				resultadoVO.setCodigo(rs.getString(posicion++));
				resultadoVO.setTipo(rs.getString(posicion++));
				resultadoVO.setDescripcion(rs.getString(posicion++));
				resultadoVO.setResultado(rs.getString(posicion++));
				resultadoVO.setFecha(rs.getString(posicion++));

				listaresultadoVO.add(resultadoVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();

		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		return listaresultadoVO;
	}

	/**
	 * @function:
	 * @param vigencia
	 * @param usuarioSesion
	 * @throws Exception
	 */
	public void guardarHiloCierre(int vigencia) throws Exception {

		Connection cn = null;
		PreparedStatement pst = null;

		int posicion = 1;
		try {
			cn = cursor.getConnection();

			pst = cn.prepareStatement(rb.getString("eliminarTablaHilos"));
			pst.setInt(posicion++, vigencia);

			pst.executeUpdate();

			pst.close();

			posicion = 1;
			pst = cn.prepareStatement(rb.getString("guardarTablaHilos"));
			pst.setInt(posicion++, vigencia);
			pst.setInt(posicion++, -1);// Estado nuevo

			pst.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new Exception(exception.getMessage(), exception);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception(exception.getMessage(), exception);
		} finally {
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}

	}

	/**
	 * @function:
	 * @param vigencia
	 * @return
	 * @throws Exception
	 */
	public boolean getValidarHiloVigencia(int vigencia) throws Exception {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getValidarHiloVigencia"));
			pst.setInt(posicion++, vigencia);

			rs = pst.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new Exception(exception.getMessage(), exception);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception(exception.getMessage(), exception);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return false;
	}

}
