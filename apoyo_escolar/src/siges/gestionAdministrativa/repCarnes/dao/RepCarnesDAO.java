/**
 * 
 */
package siges.gestionAdministrativa.repCarnes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.repCarnes.vo.FiltroRepCarnesVO;
import siges.gestionAdministrativa.repCarnes.vo.ParamsVO;
import siges.gestionAdministrativa.repCarnes.vo.ReporteVO;
import siges.login.beans.Login;

/**
 * 17/05/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class RepCarnesDAO extends Dao {
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public RepCarnesDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.repCarnes.bundle.repCarnes");
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
	 * @return
	 * @throws Exception
	 */
	public List getLocalidades() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getLocalidades"));
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre("(" + itemVO.getCodigo() + ") "
						+ rs.getString(posicion++));
				list.add(itemVO);
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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getColegios(long codLoc, long tipoCol) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getColegios"));
			st.setLong(posicion++, codLoc);
			st.setLong(posicion++, tipoCol);
			st.setLong(posicion++, tipoCol);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getJornada(long codInst, long codSede) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getJornada"));
			st.setLong(posicion++, codInst);
			// System.out.println("SEDES ---------------");
			// System.out.println("codInst " + codInst);
			st.setLong(posicion++, codSede);
			st.setLong(posicion++, codSede);
			// System.out.println("codSede " + codSede);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getMetodologia(long codInst) throws Exception {
		// System.out.println("getMetodologia " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getMetodologia"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}

			// System.out.println("list.size() " + list.size() );
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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getGrado(FiltroRepCarnesVO plantillaBoletionVO)
			throws Exception {
		// System.out.println("getGrado ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getGrado"));

			st.setLong(posicion++, plantillaBoletionVO.getInst());
			st.setLong(posicion++, plantillaBoletionVO.getSede());
			st.setLong(posicion++, plantillaBoletionVO.getJornd());
			// st.setLong(posicion++,plantillaBoletionVO.getMetodo() );
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size() );

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
		return list;
	}

	/**
	 * @function:
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public List getGrupo(FiltroRepCarnesVO plantillaBoletionVO)
			throws Exception {
		// System.out.println("getGrupo " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getGrupo"));

			st.setLong(posicion++, plantillaBoletionVO.getInst());
			// System.out.println("plantillaBoletionVO.getPlabolinst() " +
			// plantillaBoletionVO.getInst());
			st.setLong(posicion++, plantillaBoletionVO.getSede());
			// System.out.println("plantillaBoletionVO.getPlabolsede() " +
			// plantillaBoletionVO.getSede());
			st.setLong(posicion++, plantillaBoletionVO.getJornd());
			// System.out.println("plantillaBoletionVO.getPlaboljornd() " +
			// plantillaBoletionVO.getJornd());
			st.setLong(posicion++, plantillaBoletionVO.getGrado());
			// System.out.println("plantillaBoletionVO.getPlabolgrado()" +
			// plantillaBoletionVO.getGrado()) ;

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size() );

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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getSede(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getSede"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
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
		return list;
	}

	public Login paramsInst(Login login) {
		// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION COMPARATIVOS*** ");
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			// cn=c.getConnection(1);
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("getParametrosInstitucion"));
			long vigenciaActual = getVigenciaNumerico();
			pst.setLong(1, vigenciaActual);
			pst.setLong(2, Long.parseLong(login.getInstId()));
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0, f = 0;
			if (rs.next()) {
				// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** OK VIGENCIA: "+vigenciaActual+" ** INSTITUCION: "+login.getInstId());
				f = 1;
				login.setLogNumPer(rs.getLong(f++));
				login.setLogTipoPer(rs.getLong(f++));
				login.setLogNomPerDef(rs.getString(f++));
				login.setLogNivelEval(rs.getLong(f++));
			}
			// else
			// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** NO ENCONTRO DATOS ");
			rs.close();
			pst.close();

		} catch (Exception e) {
			System.out.println("error:: " + e);
		} finally {
			try {
				// c.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return login;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public int getNumAsigParam() {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		int numAsig = 2;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getNumAsigParam"));
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				numAsig = rs.getInt(posicion++);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return numAsig;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return numAsig;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return numAsig;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getAsignaturas(long codInst, long codMetod, long codGrado,
			String[] asig) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.getAsignaturas"));
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codMetod);
			st.setLong(posicion++, codMetod);
			st.setLong(posicion++, codGrado);
			st.setLong(posicion++, codGrado);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				itemVO.setCodigo2(rs.getString(posicion++));
				if (asig != null && asig.length > 0) {
					for (int i = 0; i < asig.length; i++) {
						if (GenericValidator.isLong(asig[i])) {
							if (itemVO.getCodigo() == Long.parseLong(asig[i])) {
								itemVO.setPadre2("checked");
							}
						}
					}
				}
				list.add(itemVO);
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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean insertarSolicitud(FiltroRepCarnesVO filtro) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativo.insertar"));
			st.setLong(posicion++, filtro.getTipoReporte());
			st.setLong(posicion++, filtro.getVig());
			st.setInt(posicion++, filtro.getNivelEval());
			st.setLong(posicion++, filtro.getLoc());
			st.setString(posicion++, filtro.getLoc_nombre());
			st.setLong(posicion++, filtro.getInst());
			st.setString(posicion++, filtro.getInst_nombre());
			st.setLong(posicion++, filtro.getZona());
			st.setString(posicion++, filtro.getZona_nombre());
			st.setLong(posicion++, filtro.getSede());
			st.setString(posicion++, filtro.getSede_nombre());
			st.setLong(posicion++, filtro.getJornd());
			st.setString(posicion++, filtro.getJornd_nombre());
			st.setLong(posicion++, filtro.getMetodo());
			st.setString(posicion++, filtro.getMetodo_nombre());
			st.setLong(posicion++, filtro.getGrado());
			st.setString(posicion++, filtro.getGrado_nombre());
			st.setLong(posicion++, filtro.getGrupo());
			st.setString(posicion++, filtro.getGrupo_nombre());
			st.setString(posicion++, filtro.getAsigCodigos());
			st.setString(posicion++, filtro.getAsigNombres());
			st.setLong(posicion++, filtro.getPeriodo());
			st.setDouble(posicion++, filtro.getValorIni());
			st.setDouble(posicion++, filtro.getValorFin());
			st.setLong(posicion++, filtro.getEscala());
			st.setString(posicion++, filtro.getEscala_nombre());
			st.setLong(posicion++, filtro.getOrdenReporte());
			st.setLong(posicion++, filtro.getNumPer());
			st.setString(posicion++, filtro.getNomPerFinal());
			st.setString(posicion++, filtro.getFecha());
			st.setString(posicion++, filtro.getFecha());
			st.setString(posicion++, filtro.getFecha());
			st.setString(posicion++, filtro.getNombre_zip());
			st.setString(posicion++, filtro.getNombre_pdf());
			st.setString(posicion++, filtro.getNombre_xls());
			st.setInt(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			st.setString(posicion++, filtro.getUsuario());
			st.setString(posicion++, "");
			st.setString(posicion++, "");
			st.executeUpdate();
			return true;

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
	}

	public void insertarReporte(FiltroRepCarnesVO filtro) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("comparativo.insertarReporte"));
			String ruta = rb.getString("comparativo.Pathcomparativo");
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, filtro.getUsuario());
			pst.setString(posicion++, ruta + filtro.getNombre_zip());
			pst.setString(posicion++, "zip");
			pst.setString(posicion++, filtro.getNombre_zip());
			pst.setString(posicion++, ParamsVO.REP_MODULO);
			pst.setString(posicion++, "" + ParamsVO.ESTADO_REPORTE_COLA);
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
	 * @return
	 * @throws Exception
	 */
	public List getSolicitudes() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		FiltroRepCarnesVO rep = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.rep_generar"));
			posicion = 1;
			st.clearParameters();
			st.setLong(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			rs = st.executeQuery();
			// System.out.println("ejecutn boletin_a_generar");
			while (rs.next()) {
				posicion = 1;
				rep = new FiltroRepCarnesVO();
				rep.setConsec(rs.getLong(posicion++));
				rep.setTipoReporte(rs.getInt(posicion++));
				rep.setVig(rs.getLong(posicion++));
				rep.setNivelEval(rs.getInt(posicion++));
				rep.setLoc(rs.getLong(posicion++));
				rep.setLoc_nombre(rs.getString(posicion++));
				rep.setInst(rs.getLong(posicion++));
				rep.setInst_nombre(rs.getString(posicion++));
				rep.setZona(rs.getLong(posicion++));
				rep.setZona_nombre(rs.getString(posicion++));
				rep.setSede(rs.getLong(posicion++));
				rep.setSede_nombre(rs.getString(posicion++));
				rep.setJornd(rs.getLong(posicion++));
				rep.setJornd_nombre(rs.getString(posicion++));
				rep.setMetodo(rs.getLong(posicion++));
				rep.setMetodo_nombre(rs.getString(posicion++));
				rep.setGrado(rs.getLong(posicion++));
				rep.setGrado_nombre(rs.getString(posicion++));
				rep.setGrupo(rs.getLong(posicion++));
				rep.setGrupo_nombre(rs.getString(posicion++));
				rep.setAsigCodigos(rs.getString(posicion++));
				rep.setAsigNombres(rs.getString(posicion++));
				rep.setPeriodo(rs.getLong(posicion++));
				rep.setValorIni(rs.getDouble(posicion++));
				rep.setValorFin(rs.getDouble(posicion++));
				rep.setEscala(rs.getLong(posicion++));
				rep.setEscala_nombre(rs.getString(posicion++));
				rep.setOrdenReporte(rs.getInt(posicion++));
				rep.setNumPer(rs.getLong(posicion++));
				rep.setNomPerFinal(rs.getString(posicion++));
				rep.setFecha(rs.getString(posicion++));
				rep.setFecha(rs.getString(posicion++));
				rep.setFecha(rs.getString(posicion++));
				rep.setNombre_zip(rs.getString(posicion++));
				rep.setNombre_pdf(rs.getString(posicion++));
				rep.setNombre_xls(rs.getString(posicion++));
				rep.setEstado(rs.getInt(posicion++));
				rep.setUsuario(rs.getString(posicion++));
				list.add(rep);

			}

			// if(!list.isEmpty()){
			// System.out.println("REP COMPARATIVOS:nnnnSI HAY REPORTES POR GENERAR!!!!");
			// }
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
		return list;
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
	public boolean llenarTablas(FiltroRepCarnesVO reporte, ReporteVO rep) {
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (reporte.getConsec() > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con
						.prepareCall("{call PK_COMPARATIVO.PK_COM_INICIAL(?)}");
				cstmt.setLong(posicion++, reporte.getConsec());
				// System.out.println("REP COMPARATIVOS:consec: "+reporte.getConsec()+" Inicia procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("REP COMPARATIVOS:consec: "+reporte.getConsec()+" Fin procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out.println("REP COMPARATIVOS:LLENAR TABLAS: excepcinnSQL: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, reporte.getFechaGen(),
						reporte.getFechaFin());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(reporte.getConsec());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			System.out.println("REP COMPARATIVOS:LLENAR TABLAS: excepcinn: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, reporte.getFechaGen(),
						reporte.getFechaFin());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR EXCP: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(reporte.getConsec());
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
			String fFin) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.updateDaCom"));
			st.setInt(posicion++, estado);
			st.setString(posicion++, fGen);
			st.setString(posicion++, fFin);
			st.setLong(posicion++, consec);
			st.executeUpdate();
			return true;

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
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean updateReporte(ReporteVO reporte) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			java.sql.Date fecActual = new java.sql.Date(new Date().getTime());
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("repCarnes.updateReporte"));
			st.setInt(posicion++, reporte.getEstado());
			st.setString(posicion++, reporte.getMensaje());
			st.setDate(posicion++, fecActual);
			// where
			st.setString(posicion++, reporte.getUsuario());
			st.setString(posicion++, reporte.getRecurso());
			st.setString(posicion++, reporte.getTipo());
			st.setString(posicion++, reporte.getNombre());
			st.setInt(posicion++, reporte.getModulo());
			st.executeUpdate();
			return true;

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
	public FiltroRepCarnesVO datosConv(FiltroRepCarnesVO rep, ReporteVO reporte) {
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		try {
			if (rep != null) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rb
						.getString("repCarnes.getDatosConvenciones"));
				posicion = 1;
				pst.setLong(1, rep.getConsec());
				rs = pst.executeQuery();
				if (rs.next()) {
					rep.setConvInts(rs.getString(posicion++));
					rep.setConvMen(rs.getString(posicion++));
					rep.setTipoEscala(rs.getInt(posicion++));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getFechaGen(), rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR SQL: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				updateReporte(reporte);
				limpiarTablas(rep.getConsec());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return rep;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getFechaGen(), rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR EXCP: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				updateReporte(reporte);
				limpiarTablas(rep.getConsec());
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
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		try {
			if (consec > 0) {
				con = cursor.getConnection();
				pst = con
						.prepareStatement(rb.getString("validarEstadoReporte"));
				posicion = 1;
				pst.setLong(1, consec);
				rs = pst.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			limpiarTablas(consec);
			return estado;
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consec);
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
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
	public boolean validarDatosReporte(FiltroRepCarnesVO rep) {
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		int tipoRep = 1;
		int tipoEscala = 1;
		boolean cont = true;
		try {
			if (rep.getConsec() > 0) {
				tipoRep = rep.getTipoReporte();
				tipoEscala = rep.getTipoEscala();
				if (tipoRep < 1)
					tipoRep = 1;
				if (tipoEscala < 1)
					tipoEscala = 1;

				String sql = rb.getString("comp.validarDatosReporte." + tipoRep
						+ "." + tipoEscala);
				con = cursor.getConnection();
				pst = con.prepareStatement(sql);
				posicion = 1;
				pst.setLong(posicion++, rep.getConsec());
				rs = pst.executeQuery();
				if (rs.next())
					cont = true;
				else
					cont = false;
			} else
				cont = false;
		} catch (SQLException e) {
			e.printStackTrace();
			limpiarTablas(rep.getConsec());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(rep.getConsec());
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
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
			pst = con.prepareStatement(rb.getString("activo"));
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
					.prepareCall("{call PK_COMPARATIVO.PK_COM_BORRAR_TABLAS(?)}");
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
	 * Funcinn: Ejecuta los Procedimientos para llenar tablas temporales para
	 * generar el reporte<BR>
	 * 
	 * @param FiltroRepResultadosVO
	 *            filtro
	 * 
	 **/
	public boolean llenarTabla(FiltroRepCarnesVO filtro, String usu,
			String fecha) {
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int foto = 0;
		try {
			if (filtro.getTipoReporte() < 3) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con
						.prepareCall("{call ACT_CONS_FOTO(?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setLong(posicion++, filtro.getLoc());
				cstmt.setLong(posicion++, filtro.getInst());
				cstmt.setLong(posicion++, filtro.getSede());
				cstmt.setLong(posicion++, filtro.getJornd());
				cstmt.setLong(posicion++, filtro.getGrado());
				cstmt.setLong(posicion++, filtro.getGrupo());
				cstmt.setString(posicion++, usu);
				cstmt.setString(posicion++, fecha);
				if (filtro.getTipoReporte() == 1) {
					foto = 0;
				} else if (filtro.getTipoReporte() == 2) {
					foto = 1;
				}
				cstmt.setInt(posicion++, foto);
				cstmt.setLong(posicion++, filtro.getTipoCol());
				// System.out.println("REP CARNES:consec:  Inicia procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("REP CARNES:consec:  Fin procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.close();
			}

		} catch (SQLException e) {
			System.out.println("REP CARNES:LLENAR TABLAS: excepcinnSQL: "
					+ e.getMessage());
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("REP CARNES:LLENAR TABLAS: excepcinn: "
					+ e.getMessage());
			e.printStackTrace();
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

}
