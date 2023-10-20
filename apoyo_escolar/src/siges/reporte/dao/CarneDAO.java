/**
 * 
 */
package siges.reporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.reporte.beans.FiltroBoletinVO;
import siges.reporte.beans.ParamsVO;

;;

/**
 * 17/05/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class CarneDAO extends Dao {
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public CarneDAO(Cursor c) {
		super(c);
		// rb=ResourceBundle.getBundle("siges.gestionAdministrativa.padreFlia.bundle.padreFlia");

		rb = ResourceBundle.getBundle("siges.reporte.bundle.carnes");
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
	 * @throws InternalErrorException
	 */
	public List getListaLocalidad() throws InternalErrorException {
		// System.out.println("filtro carne getListaLocalidad");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {
			rb = ResourceBundle.getBundle("siges.reporte.bundle.carnes");

			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("carne.getListaLocalidad"));
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size());
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);

		}
		return list;
	}

	/**
	 * @return
	 * @throws InternalErrorException
	 */
	public List getListaColegio(long codLoc, long codSector)
			throws InternalErrorException {
		// System.out.println("filtro carne : getListaColegio");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {
			rb = ResourceBundle.getBundle("carne_preparedstatements");

			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("carne.getListaColegio"));
			pst.setLong(posicion++, codLoc);
			// System.out.println("codLoc " + codLoc);
			pst.setLong(posicion++, codSector);
			pst.setLong(posicion++, codSector);
			// System.out.println("codSector " + codSector);
			pst.setLong(posicion++, ParamsVO.TIPO_COL_CONCESION);
			pst.setLong(posicion++, ParamsVO.TIPO_COL_CONVENIO);
			pst.setLong(posicion++, ParamsVO.TIPO_COL_OFICIAL);

			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size());
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);

		}
		return list;
	}

	// AJAX

	/**
	 * @return
	 * @throws Exception
	 */
	public List getJornada(long codInst, long codSede) throws Exception {
		// System.out.println("getJornada ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("plantillaBoletin.getJornada"));
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
			// System.out.println("list " + list.size());
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
		// System.out.println("getMetodologia ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("plantillaBoletin.getMetodologia"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				if (itemVO.getNombre().length() > 30) {
					itemVO.setNombre(itemVO.getNombre().substring(0, 30));
				}
				list.add(itemVO);
			}

			// System.out.println("list.size() " + list.size());
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
	public List getGrado(FiltroBoletinVO plantillaBoletionVO) throws Exception {
		// System.out.println("getGrado ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("plantillaBoletin.getGrado"));

			st.setLong(posicion++, plantillaBoletionVO.getPlabolinst());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolsede());
			st.setLong(posicion++, plantillaBoletionVO.getPlaboljornd());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolmetodo());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size());

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
	public String getSed(long codInst, long tipoDoc, String numDOc)
			throws Exception {
		// System.out.println("getSed ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String nombreEst = "-1";
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("plantillaBoletin.getNombreEstudiante"));

			st.setString(posicion++, numDOc.trim());
			// System.out.println("numDOc " + numDOc);
			st.setLong(posicion++, tipoDoc);
			// System.out.println("tipoDoc " + tipoDoc);

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				nombreEst = rs.getString(1);
			}
			// System.out.println("				nombreEst " + nombreEst);

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
		return nombreEst;
	}

	/**
	 * @function:
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public List getGrupo(FiltroBoletinVO plantillaBoletionVO) throws Exception {
		// System.out.println("getGrupo ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("plantillaBoletin.getGrupo"));

			st.setLong(posicion++, plantillaBoletionVO.getPlabolinst());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolsede());
			st.setLong(posicion++, plantillaBoletionVO.getPlaboljornd());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolmetodo());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolgrado());

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size());

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
		// System.out.println("getSede -");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("plantillaBoletin.getSede"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}

			// System.out.println("list " + list.size());
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

}
