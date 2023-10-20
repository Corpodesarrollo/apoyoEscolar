/**
 * 
 */
package siges.gestionAdministrativa.cierreVigencia.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.gestionAdministrativa.cierreVigencia.vo.CierreHilosVO;
import siges.gestionAdministrativa.cierreVigencia.vo.ParamsVO;

public class CierreVigenciaDAO extends Dao {
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public CierreVigenciaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.cierreVigencia.bundle.cierreVigencia");
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

	/**
	 * @function: Actualiza la tabla DATOS_HILOS y realiza el llamado al
	 *            procedimiento.
	 * @throws Exception
	 */
	public void llenarTablaDatosCierreVig() throws Exception {
		// System.out.println(formaFecha.format(new Date())
		// + "llenarTablaDatosMatricula");
		Connection cn = null;
		CallableStatement cst = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		int vigencia = 0;
		long inst = 0;
		try {
			/**
			 * ESTADOS (-1)Nuevo, (0) Procesando, (1)Terminado, (2) Error
			 * */
			// System.out.println(formaFecha.format(new Date())
			// + "[APOYO] Hilo de cierre corriendo (ESTADO NUEVO)");
			cn = cursor.getConnection();
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
				inst = rs.getInt(2);
			} else {
				return;
			}
			rs.close();
			st.close();

			// cambiar el estado a 1
			// System.out.println(formaFecha.format(new Date())
			// + "[APOYO] Hilo de cierre corriendo (ESTADO PROCESANDO)");
			st = cn.prepareStatement(rb.getString("actualizarEstadoCierre"));
			posicion = 1;

			st.setInt(posicion++, 0);
			st.setInt(posicion++, ParamsVO.HILO_TIPO_CIERRE_VIG);
			st.setInt(posicion++, vigencia);
			st.setLong(posicion++, inst);

			st.executeUpdate();
			st.close();

			// ejecutar el call
			// System.out
			// .println(formaFecha.format(new Date())
			// +
			// " [APOYO] Hilo de CIERRE_VIGENCIA corriendo (ESTADO EJECUTANDO PROCEDIMINETO)");

			cst = cn.prepareCall(rb.getString("procedimientoCierreVig"));
			posicion = 1;
			cst.setLong(posicion++, (vigencia + 1));
			cst.setLong(posicion++, inst);
			cst.executeUpdate();
			// System.out
			// .println(formaFecha.format(new Date())
			// + "[APOYO] hilo de CIERRE_VIGENCIA: ESTADO tabla actualizada");

			// cambiar el estado a 1
			// System.out
			// .println(formaFecha.format(new Date())
			// +
			// " [APOYO] Hilo de CIERRE_VIGENCIA (ESTADO EJECUTANDO FINALIZADO)");
			st = cn.prepareStatement(rb.getString("actualizarEstadoCierre"));
			posicion = 1;
			st.setInt(posicion++, 1);
			st.setInt(posicion++, ParamsVO.HILO_TIPO_CIERRE_VIG);
			st.setInt(posicion++, vigencia);
			st.setLong(posicion++, inst);
			st.executeUpdate();
			st.close();
		} catch (SQLException sqle) {
			System.out.println("SQL Error en hillo cierre vigencia: "
					+ sqle.getMessage());
			sqle.printStackTrace();
			// cambiar el estado a 2
			try {
				st = cn.prepareStatement(rb
						.getString("actualizarEstadoCierreError"));
				posicion = 1;
				st.setInt(posicion++, 2);
				st.setString(posicion++, sqle.getMessage());
				st.setInt(posicion++, ParamsVO.HILO_TIPO_CIERRE_VIG);
				st.setInt(posicion++, vigencia);
				st.setLong(posicion++, inst);
				st.executeUpdate();
				st.close();
				System.out
						.println("SQL Actualizo con actualizarEstadoCierreError");
			} catch (Exception s) {
				s.printStackTrace();
				System.out.println(formaFecha.format(new Date())
						+ "Error en hillo cierre colegio: " + s.getMessage());
			}
		} catch (Exception sqle) {
			System.out.println("Error en hillo cierre vigencia: "
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
				st.setLong(posicion++, inst);
				st.executeUpdate();
				st.close();
				System.out.println("Actualizo con actualizarEstadoCierreError");
			} catch (Exception s) {
				s.printStackTrace();
				System.out.println(formaFecha.format(new Date())
						+ "Error en hillo cierre colegio: " + s.getMessage());
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
	public List getCierreHilosVO(long codInst, String usuario) throws Exception {
		// System.out.println(formaFecha.format(new Date())+
		// " - getCierreHilosVO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		List listaHilos = new ArrayList();
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getCierreHilosVO"));
			// System.out.println("CONSULTA " +
			// rb.getString("getCierreHilosVO"));
			posicion = 1;
			st.setInt(posicion++, ParamsVO.HILO_TIPO_CIERRE_VIG);
			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst);
			// System.out.println("usuario " + usuario);

			rs = st.executeQuery();
			while (rs.next()) {
				CierreHilosVO cierreHilosVO = new CierreHilosVO();
				posicion = 1;

				cierreHilosVO.setVigencia(rs.getInt(posicion++));
				cierreHilosVO.setCodInst(rs.getInt(posicion++));
				cierreHilosVO.setEstado(rs.getString(posicion++));
				cierreHilosVO.setFechaSistema(rs.getString(posicion++));
				cierreHilosVO.setMensaje(rs.getString(posicion++));
				cierreHilosVO.setCodigoEstado(rs.getInt(posicion++));
				listaHilos.add(cierreHilosVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("error sql " + sqle.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();

		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		return listaHilos;
	}

	public List getPromo(long codInst, long sede, long jor, long grado,
			long grupo) throws Exception {
		// System.out.println(formaFecha.format(new Date())
		// + " - getCierreHilosVO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		List listaHilos = new ArrayList();
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getPromociones"));
			// System.out.println("CONSULTA " +
			// rb.getString("getCierreHilosVO"));
			posicion = 1;
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, grado);
			st.setLong(posicion++, sede);
			st.setLong(posicion++, jor);
			st.setLong(posicion++, grupo);
			// System.out.println("codInst " + codInst);
			rs = st.executeQuery();
			while (rs.next()) {
				CierreHilosVO cierreHilosVO = new CierreHilosVO();
				posicion = 1;
				// System.out.println("vig " + rs.getInt(posicion++));
				cierreHilosVO.setVigencia(rs.getInt(posicion++));
				listaHilos.add(cierreHilosVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("error sql " + sqle.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();

		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		return listaHilos;
	}

	/**
	 * @function:
	 * @return
	 * @throws Exception
	 */
	/*
	 * public List getlistaResultadoVO() throws Exception { Connection cn =
	 * null; PreparedStatement st=null; ResultSet rs=null; int posicion=0; List
	 * listaresultadoVO = new ArrayList(); try {
	 * 
	 * cn=cursor.getConnection();
	 * st=cn.prepareStatement(rb.getString("getlistaResultadoVO"));
	 * rs=st.executeQuery(); while(rs.next()){ ResultadoVO resultadoVO = new
	 * ResultadoVO(); posicion = 1;
	 * 
	 * resultadoVO.setCodigo(rs.getString(posicion++));
	 * resultadoVO.setTipo(rs.getString(posicion++));
	 * resultadoVO.setDescripcion(rs.getString(posicion++));
	 * resultadoVO.setResultado(rs.getString(posicion++));
	 * resultadoVO.setFecha(rs.getString(posicion++));
	 * 
	 * listaresultadoVO.add(resultadoVO); } } catch (Exception sqle) {
	 * sqle.printStackTrace();
	 * 
	 * } finally { OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeConnection(cn); } return listaresultadoVO; }
	 */

	/**
	 * @function: Registra en la tabla de hilos el proceso para cierre de
	 *            vigencia Recordar que el hilo consulta esta tabla para ir
	 *            ejecuntado el correspondiente cierre deacurdo a los parametros
	 *            que tenga, en esta caso tipo de cierre, vigencia e
	 *            institucion. Por defecto el estado es NUEVO (-1)
	 * @param vigencia
	 * @param usuarioSesion
	 * @throws Exception
	 */
	public void guardarHiloCierre(int vigencia, long codInst, String usuario)
			throws Exception {
		// System.out.println(formaFecha.format(new Date())
		// + " - guardarHiloCierreVig");
		Connection cn = null;
		PreparedStatement pst = null;

		int posicion = 1;
		try {
			cn = cursor.getConnection();

			pst = cn.prepareStatement(rb.getString("eliminarTablaHilos"));

			pst.setInt(posicion++, ParamsVO.HILO_TIPO_CIERRE_VIG);
			pst.setInt(posicion++, (vigencia));
			pst.setLong(posicion++, codInst);

			pst.executeUpdate();
			pst.close();

			posicion = 1;
			pst = cn.prepareStatement(rb.getString("guardarTablaHilos"));
			pst.setInt(posicion++, ParamsVO.HILO_TIPO_CIERRE_VIG);
			pst.setString(posicion++, usuario);
			pst.setInt(posicion++, vigencia);
			pst.setLong(posicion++, codInst);
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
	public boolean getValidarHiloVigencia(int vigencia, long codInst)
			throws Exception {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getValidarHiloVigencia"));
			pst.setInt(posicion++, ParamsVO.HILO_TIPO_CIERRE_VIG);
			pst.setInt(posicion++, vigencia);
			pst.setLong(posicion++, codInst);

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

	/**
	 * Metodo encargado de validar si existe estudiantes evaluados
	 * 
	 * @param codInst
	 * @param vigDestino
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteEstEval(long codInst, long vigDestino)
			throws Exception {
		// System.out
		// .println(formaFecha.format(new Date()) + " - isExisteEstEval");

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();

			// System.out.println("CONSULTA - "
			// + rb.getString("duplicarPlanEstd.isExisteEstEval.1")
			// + rb.getString("duplicarPlanEstd.isExisteEstEval.2"));
			st = cn.prepareStatement(rb
					.getString("duplicarPlanEstd.isExisteEstEval.1")
					+ rb.getString("duplicarPlanEstd.isExisteEstEval.2"));
			posicion = 1;

			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();
			while (rs.next()) {
				int resul = rs.getInt(1);
				// System.out.println("resul " + resul);
				if (resul > 0) {
					posicion = 1;
					// System.out.println("si existe estudiantes evaluados "
					// + resul);
					return true;
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("error sql " + sqle.getMessage());
			throw new Exception(sqle.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		// System.out.println("No existen estudiante evaluados");
		return false;
	}

	/**
	 * Metodo encargado de validar si existe promocion para la vigencia destino
	 * 
	 * @param codInst
	 * @param vigDestino
	 * @return
	 * @throws Exception
	 */
	public boolean isExistePromocion(long codInst, long vigDestino)
			throws Exception {
		// System.out.println(formaFecha.format(new Date())
		// + " - isExistePromocion");

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();

			// System.out.println("CONSULTA - " +
			// rb.getString("duplicarPlanEstd.isExistePromocion"));
			st = cn.prepareStatement(rb
					.getString("duplicarPlanEstd.isExistePromocion"));
			posicion = 1;

			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("error sql " + sqle.getMessage());
			throw new Exception(sqle.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		// System.out.println("No existen estudiante promocionados");
		return false;
	}

	/**
	 * @param codInst
	 * @param vigDestino
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteObserAsig(long codInst, long vigDestino)
			throws Exception {
		// System.out.println(formaFecha.format(new Date())
		// + " - isExisteObserAsig");

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("duplicarPlanEstd.isExisteObserAsig"));
			posicion = 1;

			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("error sql " + sqle.getMessage());
			throw new Exception(sqle.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		// System.out.println("No existen observacion de asignaturas");
		return false;
	}

	/**
	 * Metodo encargado de validar si existe registro de plande estudio
	 * 
	 * @param codInst
	 * @param vigDestino
	 * @return
	 * @throws Exception
	 */
	public boolean isExistePlanEstd(long codInst, long vigDestino)
			throws Exception {
		// System.out.println(formaFecha.format(new Date())
		// + " - isExistePlanEstd");

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();

			// System.out.println("CONSULTA "
			// + rb.getString("duplicarPlanEstd.isExistePlanEstd.1")
			// + rb.getString("duplicarPlanEstd.isExistePlanEstd.2")
			// + rb.getString("duplicarPlanEstd.isExistePlanEstd.3")
			// + rb.getString("duplicarPlanEstd.isExistePlanEstd.4")
			// + rb.getString("duplicarPlanEstd.isExistePlanEstd.5")
			// + rb.getString("duplicarPlanEstd.isExistePlanEstd.6"));
			st = cn.prepareStatement(rb
					.getString("duplicarPlanEstd.isExistePlanEstd.1")
					+ rb.getString("duplicarPlanEstd.isExistePlanEstd.2")
					+ rb.getString("duplicarPlanEstd.isExistePlanEstd.3")
					+ rb.getString("duplicarPlanEstd.isExistePlanEstd.4")
					+ rb.getString("duplicarPlanEstd.isExistePlanEstd.5")
					+ rb.getString("duplicarPlanEstd.isExistePlanEstd.6"));
			posicion = 1;

			// 1
			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			// 2
			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			// 3
			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			// 4
			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			// 5
			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			// 6
			st.setLong(posicion++, vigDestino);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				// System.out.println("si existe registro de plan de estudios "
				// + rs.getInt(posicion++));
				return true;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("error sql " + sqle.getMessage());
			throw new Exception(sqle.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		return false;
	}

	/**
	 * Metodo que realiza el llamado al procedimiento de duplicar Plan de
	 * estudios para vigencias anteriores
	 * 
	 * @param vigOrigen
	 * @param vigDestino
	 * @return
	 * @throws Exception
	 */
	public void callDuplicarPlanEstd(long codInst, long vigOrigen,
			long vigDestino) throws Exception {
		// System.out.println(formaFecha.format(new Date())
		// + " - callDuplicarPlanEstd");

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("duplicarPlanEstd.callDuplicarPlanEstudios"));
			posicion = 1;

			// 1
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigOrigen);
			st.setLong(posicion++, vigDestino);

			int resul = st.executeUpdate();
			// System.out.println("resul " + resul);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("error sql " + sqle.getMessage());
			throw new Exception(sqle.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
		}
		// System.out.println("No existen registros");

	}

}
