/**
 * 
 */
package siges.gestionAdministrativa.comparativos.dao;

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
import siges.gestionAdministrativa.comparativos.vo.FiltroReportesVO;
import siges.gestionAdministrativa.comparativos.vo.NivelEvalVO;
import siges.gestionAdministrativa.comparativos.vo.ParamsVO;
import siges.gestionAdministrativa.comparativos.vo.ReporteVO;
import siges.gestionAdministrativa.comparativos.vo.TipoEvalVO;
import siges.login.beans.Login;

/**
 * 17/05/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class ComparativosDAO extends Dao {
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public ComparativosDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.comparativos.bundle.comparativos");
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
	public List getProvincias() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.provincias"));
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

	public long getProvMun(long codigo) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		// System.out.println("ENTRO A GETFECHA PROMEDIOS, NIVEL: "+nivel+"   , CODIGO: "+codigo);
		ResultSet rs = null;
		int i = 1;
		long prov = 0;
		try {
			cn = cursor.getConnection();
			String sql = rb.getString("getProvinciaMun");
			st = cn.prepareStatement(sql);
			st.setLong(i++, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				prov = rs.getLong(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return -9;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return -9;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return prov;
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
			st = cn.prepareStatement(rb
					.getString("comparativos.getLocalidades"));

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
	public List getColegios(long codLoc) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.getColegios"));
			st.setLong(posicion++, codLoc);
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
			st = cn.prepareStatement(rb.getString("comparativos.getJornada"));
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
	public List getJornadaGlobal() throws Exception {
		// System.out.println("getJornadaGlobal" );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("comparativos.getJornadaGlobal"));
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
			st = cn.prepareStatement(rb
					.getString("comparativos.getMetodologia"));
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
	public List getMetodologiaGlobal() throws Exception {
		// System.out.println("getMetodologiaGlobal" );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("comparativos.getMetodologiaGlobal"));

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
	public List getGrado(FiltroReportesVO plantillaBoletionVO) throws Exception {
		// System.out.println("getGrado ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.getGrado"));

			st.setLong(posicion++, plantillaBoletionVO.getInst());
			st.setLong(posicion++, plantillaBoletionVO.getSede());
			st.setLong(posicion++, plantillaBoletionVO.getSede());
			st.setLong(posicion++, plantillaBoletionVO.getJornd());
			st.setLong(posicion++, plantillaBoletionVO.getJornd());
			st.setLong(posicion++, plantillaBoletionVO.getMetodo());
			st.setLong(posicion++, plantillaBoletionVO.getMetodo());
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
	 * @return
	 * @throws Exception
	 */
	public List getGradoGlobal() throws Exception {
		// System.out.println("getGradoGlobal ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("comparativos.getGradoGlobal"));
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
	public List getGrupo(FiltroReportesVO plantillaBoletionVO) throws Exception {
		// System.out.println("getGrupo " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.getGrupo"));

			st.setLong(posicion++, plantillaBoletionVO.getInst());
			// System.out.println("plantillaBoletionVO.getPlabolinst() " +
			// plantillaBoletionVO.getInst());
			st.setLong(posicion++, plantillaBoletionVO.getSede());
			// System.out.println("plantillaBoletionVO.getPlabolsede() " +
			// plantillaBoletionVO.getSede());
			st.setLong(posicion++, plantillaBoletionVO.getJornd());
			// System.out.println("plantillaBoletionVO.getPlaboljornd() " +
			// plantillaBoletionVO.getJornd());
			st.setLong(posicion++, plantillaBoletionVO.getMetodo());
			// System.out.println("plantillaBoletionVO.getPlabolmetodo() " +
			// plantillaBoletionVO.getMetodo());
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
	public List getSede(long codInst, long zona) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.getSede"));
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, zona);
			st.setLong(posicion++, zona);
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
		// System.out.println("APOYO:ENTRO A CARGAR PARAMETROS DE LA INSTITUCION COMPARATIVOS*** ");
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
				// System.out.println("APOYO:ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** OK VIGENCIA: "+vigenciaActual+" ** INSTITUCION: "+login.getInstId());
				f = 1;
				login.setLogNumPer(rs.getLong(f++));
				login.setLogTipoPer(rs.getLong(f++));
				login.setLogNomPerDef(rs.getString(f++));
				login.setLogNivelEval(rs.getLong(f++));
			}
			// else
			// System.out.println("APOYO:ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** NO ENCONTRO DATOS ");
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
			st = cn.prepareStatement(rb
					.getString("comparativos.getNumAsigParam"));
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

	public List getAsignaturasGlobal(String[] asig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int pos = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("comparativos.getAsignaturasGlobal"));
			rs = st.executeQuery();
			while (rs.next()) {
				pos = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(pos++));
				itemVO.setNombre(rs.getString(pos++));
				itemVO.setCodigo2(rs.getString(pos++));
				if (asig != null && asig.length > 0) {
					for (int i = 0; i < asig.length; i++) {
						if (GenericValidator.isLong(asig[i])) {
							if (itemVO.getCodigo() == Long.parseLong(asig[i])) {
								itemVO.setPadre2("checked");
							}
						}
					}
				}
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getAsignaturas(long codInst, long codMetod, long codGrado,
			String[] asig, long vig) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("comparativos.getAsignaturas"));
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vig);
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

	public List getAreasGlobal(String[] asig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int pos = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("comparativos.getAreasGlobal"));
			rs = st.executeQuery();
			while (rs.next()) {
				pos = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(pos++));
				itemVO.setNombre(rs.getString(pos++));
				itemVO.setCodigo2(rs.getString(pos++));
				if (asig != null && asig.length > 0) {
					for (int i = 0; i < asig.length; i++) {
						if (GenericValidator.isLong(asig[i])) {
							if (itemVO.getCodigo() == Long.parseLong(asig[i])) {
								itemVO.setPadre2("checked");
							}
						}
					}
				}
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getAreas(long codInst, long codMetod, String[] asig, long vig)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.getAreas"));
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vig);
			st.setLong(posicion++, codMetod);
			st.setLong(posicion++, codMetod);
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

	public List getEscalaMEN() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getEscalaMEN"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre2(rs.getString(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public long getPerMax() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long maxNumPer = 0;

		int tipoEval = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getMaxPeriodoGlobal"));
			rs = st.executeQuery();
			if (rs.next()) {
				maxNumPer = rs.getLong(1);
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
		return maxNumPer;
	}

	public NivelEvalVO getNivelEval(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		nivelEval.setCod_nivel_eval(1);
		nivelEval.setNumPer(-99);
		nivelEval.setNomPerFinal("FINAL");
		int tipoEval = 0;
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaNumerico();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, inst);
			rs = st.executeQuery();
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(1));
				nivelEval.setNumPer(rs.getInt(2));
				nivelEval.setNomPerFinal(rs.getString(3));
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
		return nivelEval;
	}

	public TipoEvalVO getTipoEval(FiltroReportesVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		TipoEvalVO tipoEvalVO = new TipoEvalVO();
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaNumerico();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, filtro.getInst());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
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
			String sql = rb.getString("getTipoEval.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getTipoEval.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getTipoEval.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getTipoEval.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, filtro.getInst());

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, filtro.getSede());
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, filtro.getJornd());
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, filtro.getJornd());
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(filtro.getGrado());
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, filtro.getGrado());

			rs = st.executeQuery();
			i = 1;

			if (rs.next()) {
				tipoEvalVO.setCod_tipo_eval(rs.getLong(i++));
				tipoEvalVO.setEval_min(rs.getDouble(i++));
				tipoEvalVO.setEval_max(rs.getDouble(i++));
				tipoEvalVO.setTipo_eval_prees(rs.getInt(i++));
				tipoEvalVO.setModo_eval(rs.getInt(i++));
			} else {
				tipoEvalVO.setCod_tipo_eval(3);
				tipoEvalVO.setEval_min(0);
				tipoEvalVO.setEval_max(100);
				tipoEvalVO.setTipo_eval_prees(-9);
				tipoEvalVO.setModo_eval(3);
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
		return tipoEvalVO;
	}

	public List getEscalaConceptual(FiltroReportesVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		List l = new ArrayList();
		ItemVO item = null;
		// Collection list = null;
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaNumerico();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, filtro.getVig());
			st.setLong(i++, filtro.getInst());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
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
			String sql = rb.getString("getEscalaConceptual.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.grado");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, filtro.getVig());
			st.setLong(i++, filtro.getInst());
			st.setLong(i++, codigoNivelEval);

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, filtro.getSede());
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, filtro.getJornd());
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, filtro.getMetodo());
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(filtro.getGrado());
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, filtro.getGrado());

			rs = st.executeQuery();
			// list = getCollection(rs);
			i = 1;
			while (rs.next()) {
				i = 1;
				item = new ItemVO(rs.getInt(i++), rs.getString(i++));
				l.add(item);
			}
			// System.out.println("COMPARATIVOS: ESCALA CONCEPTUAL: "+l.size());
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
		return l;
	}

	public int getNivelGrado(long grado) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getNivelGrado"));
			st.setLong(i++, grado);
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

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean insertarSolicitud(FiltroReportesVO filtro) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;

		NivelEvalVO nivelVO = new NivelEvalVO();
		nivelVO.setCod_nivel_eval(1);
		TipoEvalVO tipoEval;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativo.insertar"));
			st.setLong(posicion++, filtro.getTipoReporte());
			st.setLong(posicion++, filtro.getVig());

			if (filtro.getInst() > 0) {
				nivelVO = getNivelEval(filtro.getInst());
				tipoEval = getTipoEval(filtro);
				filtro.setNumPer(nivelVO.getNumPer());
				filtro.setNomPerFinal(nivelVO.getNomPerFinal());
				filtro.setTipoEscala((int) tipoEval.getCod_tipo_eval());
			} else {
				filtro.setNumPer(-99);
				filtro.setNomPerFinal("FINAL");
				filtro.setTipoEscala(3);
			}

			st.setLong(posicion++, nivelVO.getCod_nivel_eval());
			// st.setLong(posicion++,filtro.getProv());
			// st.setString(posicion++,filtro.getProv_nombre());
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
			if (filtro.getGrupo() < 0) {
				filtro.setGrupo_nombre("");
			}
			st.setString(posicion++, filtro.getGrupo_nombre());
			// System.out.println("APOYO***********************************************ESCALA NOMBRE  "+filtro.getEscala_nombre());
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
			st.setInt(posicion++, filtro.getConAreAsi());
			st.setInt(posicion++, filtro.getTipoEscala());
			if (filtro.getTipoReporte() != 2)
				filtro.setConGraGru(-99);
			/*
			 * else{ if(filtro.getInst()>0 && filtro.getSede()>0 &&
			 * filtro.getJornd()>0) filtro.setConGraGru(2); else
			 * filtro.setConGraGru(2); }
			 */
			st.setInt(posicion++, filtro.getConGraGru());
			st.setInt(posicion++, filtro.getDacompromediar());
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

	public void insertarReporte(FiltroReportesVO filtro) {
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
	public List getSolicitudes() {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		FiltroReportesVO rep = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.rep_generar"));
			posicion = 1;
			st.clearParameters();
			st.setLong(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			rs = st.executeQuery();
			// System.out.println("ejecutn boletin_a_generar");
			while (rs.next()) {
				posicion = 1;
				rep = new FiltroReportesVO();
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
				rep.setTipoEscala(rs.getInt(posicion++));
				rep.setConGraGru(rs.getInt(posicion++));
				// rep.setProv(rs.getLong(posicion++));
				// rep.setProv_nombre(rs.getString(posicion++));
				rep.setConAreAsi(rs.getInt(posicion++));
				rep.setDacompromediar(rs.getInt(posicion++));
				list.add(rep);

			}

			// if(!list.isEmpty()){
			// System.out.println("APOYO:REP COMPARATIVOS:nnnnSI HAY REPORTES POR GENERAR!!!!");
			// }
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			list = null;
			// throw new Exception("Error de datos: "+sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			list = null;
			// throw new Exception("Error interno: "+sqle.getMessage());
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
	public boolean llenarTablas(FiltroReportesVO reporte, ReporteVO rep) {
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
				// System.out.println("APOYO:REP COMPARATIVOS:consec: "+reporte.getConsec()+" Inicia procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("APOYO:REP COMPARATIVOS:consec: "+reporte.getConsec()+" Fin procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.close();
			}
			return true;
		} catch (SQLException e) {
			System.out
					.println("APOYO:REP COMPARATIVOS:LLENAR TABLAS: excepcinnSQL: "
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
			System.out
					.println("APOYO:REP COMPARATIVOS:LLENAR TABLAS: excepcinn: "
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
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.updateDaCom"));
			st.setInt(posicion++, estado);
			st.setString(posicion++, fGen);
			st.setString(posicion++, fFin);
			st.setLong(posicion++, consec);
			st.executeUpdate();
			return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
			// throw new Exception("Error de datos: "+sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
			// throw new Exception("Error interno: "+sqle.getMessage());
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
	public boolean updateReporte(ReporteVO reporte) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			// System.out.println("APOYO:REP COMP UPDATE REPORTE: USU:"+reporte.getUsuario()+"  RECURSO:"+reporte.getRecurso()+" TIPO:"+reporte.getTipo()+"   NOMBRE:"+reporte.getNombre()+"  MODULO:"+reporte.getModulo()+"   ESTADO:"+reporte.getEstado());
			java.sql.Date fecActual = new java.sql.Date(new Date().getTime());
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("comparativos.updateReporte"));
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
			return false;
			// throw new Exception("Error de datos: "+sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
			// throw new Exception("Error interno: "+sqle.getMessage());
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
	public FiltroReportesVO datosConv(FiltroReportesVO rep, ReporteVO reporte) {
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
						.getString("comparativos.getDatosConvenciones"));
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
	public boolean validarDatosReporte(FiltroReportesVO rep) {
		// System.out.println("APOYO:ENTRO A VALIDAR DATOS REPORTE COMPARATIVO");
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

	public String getFechaPromedios(int nivel, long codigo) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		System.out.println("APOYO:ENTRO A GETFECHA PROMEDIOS, NIVEL: " + nivel
				+ "   , CODIGO: " + codigo);
		ResultSet rs = null;
		int i = 1;
		String fechaProm = null;
		try {
			cn = cursor.getConnection();
			String sql = rb.getString("getFecha.central");
			if (nivel == ParamsVO.NIVEL_CENTRAL) {
				sql = rb.getString("getFecha.central");
			} else if (nivel == ParamsVO.NIVEL_MUN) {
				sql = rb.getString("getFecha.municipio");
			} else if (nivel == ParamsVO.NIVEL_INST) {
				sql = rb.getString("getFecha.colegio");
			}

			st = cn.prepareStatement(sql);
			if (nivel == ParamsVO.NIVEL_PROV || nivel == ParamsVO.NIVEL_MUN
					|| nivel == ParamsVO.NIVEL_INST) {
				st.setLong(i++, codigo);
			}
			rs = st.executeQuery();
			if (rs.next()) {
				fechaProm = rs.getString(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return fechaProm;
	}

	public boolean updateFechaPromedios(int nivel, long mun, long inst)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		// System.out.println("APOYO:ENTRO A UPDATE_FECHA PROMEDIOS, NIVEL: "+nivel+" ,  MUN:"+mun+", INST:"+inst);
		ResultSet rs = null;
		int i = 1;
		String fechaProm = null;
		try {
			cn = cursor.getConnection();
			String sql = rb.getString("getFecha.central");
			if (nivel == ParamsVO.NIVEL_CENTRAL) {
				sql = rb.getString("updateFecha.central");
				st = cn.prepareStatement(sql);
				st.executeUpdate();
				st.close();
			}
			if (nivel <= ParamsVO.NIVEL_MUN) {
				sql = rb.getString("updateFecha.municipio");
				i = 1;
				st = cn.prepareStatement(sql);
				st.setLong(i++, mun);
				st.setLong(i++, mun);
				st.executeUpdate();
				st.close();
			}
			if (nivel <= ParamsVO.NIVEL_INST) {
				sql = rb.getString("updateFecha.colegio");
				i = 1;
				st = cn.prepareStatement(sql);
				st.setLong(i++, inst);
				st.setLong(i++, inst);
				st.setLong(i++, mun);
				st.setLong(i++, mun);
				st.executeUpdate();
				st.close();
			}

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
		return true;
	}

	/**
	 * Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>
	 **/
	public boolean actPromGrupoAsig(Connection con, long vig, long mun,
			long inst, long per, long nivelEval, long tipoNivelEval, int accion) {
		// Connection con=null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {

			// con=cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call ACT_PROM_GRUPO_ASIG(?,?,?,?,?,?,?,?,?,?,?,?)}");
			// cstmt.setLong(posicion++,prov);
			cstmt.setLong(posicion++, mun);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, vig);
			cstmt.setLong(posicion++, per);
			cstmt.setLong(posicion++, nivelEval);
			cstmt.setLong(posicion++, -9);
			cstmt.setInt(posicion++, accion);
			// System.out.println("APOYO:PROMEDIO GRUPO ASIG: REP_COMPARATIVOS - INST: "+inst+" Inicia procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.executeUpdate();
			// System.out.println("APOYO:PROMEDIO GRUPO ASIG: REP_COMPARATIVOS - INST: "+inst+" Fin procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.close();

		} catch (SQLException e) {
			System.out
					.println("APOYO:PROMEDIO ASIGNATURA GRUPO:excepcinn sql!");
			e.printStackTrace();

			return false;
		} catch (Exception e) {
			System.out.println("APOYO:PROMEDIO ASIGNATURA GRUPO:excepcinn!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				// OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}// fin de preparedstatements

	/**
	 * Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>
	 **/
	public boolean actPromGrupoArea(Connection con, long vig, long prov,
			long mun, long inst, long per, long nivelEval, long tipoNivelEval,
			int accion) {
		// Connection con=null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {

			// con=cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call ACT_PROM_GRUPO_AREA(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, prov);
			cstmt.setLong(posicion++, mun);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, vig);
			cstmt.setLong(posicion++, per);
			cstmt.setLong(posicion++, nivelEval);
			cstmt.setLong(posicion++, tipoNivelEval);
			cstmt.setInt(posicion++, accion);

			// System.out.println("APOYO:PROMEDIO GRUPO AREA: REP_COMPARATIVOS - INST: "+inst+" Inicia procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.executeUpdate();
			// System.out.println("APOYO:PROMEDIO GRUPO AREA: REP_COMPARATIVOS - INST: "+inst+" Fin procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.close();

		} catch (SQLException e) {
			System.out.println("APOYO:PROMEDIO AREA:excepcinn sql!");
			e.printStackTrace();

			return false;
		} catch (Exception e) {
			System.out.println("APOYO:PROMEDIO AREA:excepcinn!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				// OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}// fin de preparedstatements

	/**
	 * Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>
	 **/
	public boolean actPromGrupoAsigMEN(Connection con, long vig, long mun,
			long inst, long per, long nivelEval, long tipoNivelEval, int accion) {
		// Connection con=null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {

			// con=cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call ACT_PROM_GRUPO_ASIG_MEN(?,?,?,?,?,?,?,?,?,?,?,?)}");
			// cstmt.setLong(posicion++,prov);
			cstmt.setLong(posicion++, mun);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, vig);
			cstmt.setLong(posicion++, per);
			cstmt.setLong(posicion++, nivelEval);
			cstmt.setLong(posicion++, tipoNivelEval);
			cstmt.setInt(posicion++, accion);
			// System.out.println("APOYO:PROMEDIO GRUPO ASIGMEN: REP_COMPARATIVOS - INST: "+inst+" Inicia procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.executeUpdate();
			// System.out.println("PROMEDIO GRUPO ASIGMEN: REP_COMPARATIVOS - INST: "+inst+" Fin procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.close();

		} catch (SQLException e) {
			System.out
					.println("APOYO:PROMEDIO ASIGNATURA GRUPO:excepcinn sql!");
			e.printStackTrace();

			return false;
		} catch (Exception e) {
			System.out.println("APOYO:PROMEDIO ASIGNATURA GRUPO:excepcinn!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				// OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}// fin de preparedstatements

	/**
	 * Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>
	 **/
	public boolean actPromGrupoAreaMEN(Connection con, long vig, long prov,
			long mun, long inst, long per, long nivelEval, long tipoNivelEval,
			int accion) {
		// Connection con=null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {

			// con=cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call ACT_PROM_GRUPO_AREA_MEN(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, prov);
			cstmt.setLong(posicion++, mun);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, -9);
			cstmt.setLong(posicion++, vig);
			cstmt.setLong(posicion++, per);
			cstmt.setLong(posicion++, nivelEval);
			cstmt.setLong(posicion++, tipoNivelEval);
			cstmt.setInt(posicion++, accion);

			// System.out.println("APOYO:PROMEDIO GRUPO AREAMEN: REP_COMPARATIVOS - INST: "+inst+" Inicia procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.executeUpdate();
			// System.out.println("APOYO:PROMEDIO GRUPO AREAMEN: REP_COMPARATIVOS - INST: "+inst+" Fin procedimiento Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.close();

		} catch (SQLException e) {
			System.out.println("APOYO:PROMEDIO AREA:excepcinn sql!");
			e.printStackTrace();

			return false;
		} catch (Exception e) {
			System.out.println("APOYO:PROMEDIO AREA:excepcinn!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				// OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}// fin de preparedstatements

}
