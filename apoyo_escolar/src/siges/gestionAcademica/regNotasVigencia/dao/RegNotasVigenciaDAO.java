/**
 * 
 */
package siges.gestionAcademica.regNotasVigencia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.FiltroCommonVO;
import siges.common.vo.ItemVO;
import siges.common.vo.TipoEvalVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAcademica.regNotasVigencia.vo.AreaVO;
import siges.gestionAcademica.regNotasVigencia.vo.AsignaturaVO;
import siges.gestionAcademica.regNotasVigencia.vo.EstudianteRegNotasVO;
import siges.gestionAcademica.regNotasVigencia.vo.FiltroRegNotasVO;
import siges.gestionAcademica.regNotasVigencia.vo.InstitucionParametroVO;
import siges.gestionAcademica.regNotasVigencia.vo.ParamsVO;
import siges.login.beans.Login;
import siges.reporte.beans.FiltroBoletinVO;

public class RegNotasVigenciaDAO extends Dao {
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public RegNotasVigenciaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAcademica.regNotasVigencia.bundle.regNotasVigencia");
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
			st = cn.prepareStatement(rb.getString("regNotas.getJornada"));
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
			st = cn.prepareStatement(rb.getString("regNotas.getMetodologia"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				if (itemVO.getNombre() != null
						&& itemVO.getNombre().length() > 30) {
					itemVO.setNombre(itemVO.getNombre().substring(0, 30));
				}
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
			st = cn.prepareStatement(rb.getString("regNotas.getGrado"));

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
					.getString("regNotas.getNombreEstudiante"));

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
		// System.out.println("getGrupo " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("regNotas.getGrupo"));

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
		// System.out.println("getSede -");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("regNotas.getSede"));
			st.setLong(posicion++, codInst);
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
	public List getTiposDoc() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getTiposDoc"));
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
		// System.out.println("list " + list.size() );
		return list;
	}

	/**
	 * Metodo que retorna el listado de estudiantes
	 * 
	 * @param filtroRegNotasVO
	 * @return
	 * @throws Exception
	 */
	public List getListaEstudiante(FiltroRegNotasVO filtroRegNotasVO)
			throws Exception {
		// System.out.println("regNotasVigencia : getListaEstudiante");

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		long count = 0;
		long totalPag = 0;
		try {
			cn = cursor.getConnection();

			// System.out.println("filtroRegNotasVO.getNumPag()  " +
			// filtroRegNotasVO.getNumPag() );
			// System.out.println("filtroRegNotasVO.getCantPags() " +
			// filtroRegNotasVO.getCantPags());
			// cosunta count
			st = cn.prepareStatement(rb
					.getString("regNotas.getEstudianteCount"));
			posicion = 1;
			st.setInt(posicion++, filtroRegNotasVO.getFilTipoDoc());
			st.setInt(posicion++, filtroRegNotasVO.getFilTipoDoc());
			// System.out.println("filtroRegNotasVO.getFilTipoDoc() " +
			// filtroRegNotasVO.getFilTipoDoc());

			if (filtroRegNotasVO.getFilNumDoc() == null
					|| filtroRegNotasVO.getFilNumDoc().trim().length() == 0) {
				st.setString(posicion++, "-99");

			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilNumDoc());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilNumDoc());
			// System.out.println("filtroRegNotasVO.getFilNumDoc() " +
			// filtroRegNotasVO.getFilNumDoc());

			if (filtroRegNotasVO.getFilApell1() == null
					|| filtroRegNotasVO.getFilApell1().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilApell1());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilApell1());
			// System.out.println("filtroRegNotasVO.getFilApell1() " +
			// filtroRegNotasVO.getFilApell1());

			if (filtroRegNotasVO.getFilApell2() == null
					|| filtroRegNotasVO.getFilApell2().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilApell2());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilApell2());
			// System.out.println("filtroRegNotasVO.getFilApell2() " +
			// filtroRegNotasVO.getFilApell2());

			if (filtroRegNotasVO.getFilNom1() == null
					|| filtroRegNotasVO.getFilNom1().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilNom1());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilNom1());
			// System.out.println("filtroRegNotasVO.getFilNom1() " +
			// filtroRegNotasVO.getFilNom1());

			if (filtroRegNotasVO.getFilNom2() == null
					|| filtroRegNotasVO.getFilNom2().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilNom2());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilNom2());

			// System.out.println("filtroRegNotasVO.getFilNom2() "+
			// filtroRegNotasVO.getFilNom2());

			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				count = rs.getLong(posicion++);

			}
			// System.out.println("CANTIDAD DE REGISTRO " + count);
			if (count > 0) {
				totalPag = 1;
			}
			if (count > filtroRegNotasVO.getCantPags()) {
				totalPag = count / filtroRegNotasVO.getCantPags();
				if ((float) count / (float) filtroRegNotasVO.getCantPags() > (float) totalPag) {
					totalPag += 1;
				}
			}
			// System.out.println("totalPag " + totalPag);
			filtroRegNotasVO.setTotalPag(totalPag);

			rs.close();
			st.close();

			// consulta datos
			st = cn.prepareStatement(rb.getString("regNotas.getEstudiante"));
			posicion = 1;
			st.setInt(posicion++, filtroRegNotasVO.getFilTipoDoc());
			st.setInt(posicion++, filtroRegNotasVO.getFilTipoDoc());
			// System.out.println("filtroRegNotasVO.getFilTipoDoc() " +
			// filtroRegNotasVO.getFilTipoDoc());

			if (filtroRegNotasVO.getFilNumDoc() == null
					|| filtroRegNotasVO.getFilNumDoc().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilNumDoc());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilNumDoc());
			// System.out.println("filtroRegNotasVO.getFilNumDoc() " +
			// filtroRegNotasVO.getFilNumDoc());

			if (filtroRegNotasVO.getFilApell1() == null
					|| filtroRegNotasVO.getFilApell1().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilApell1());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilApell1());
			// System.out.println("filtroRegNotasVO.getFilApell1() " +
			// filtroRegNotasVO.getFilApell1());

			if (filtroRegNotasVO.getFilApell2() == null
					|| filtroRegNotasVO.getFilApell2().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilApell2());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilApell2());
			// System.out.println("filtroRegNotasVO.getFilApell2() " +
			// filtroRegNotasVO.getFilApell2());

			if (filtroRegNotasVO.getFilNom1() == null
					|| filtroRegNotasVO.getFilNom1().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilNom1());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilNom1());
			// System.out.println("filtroRegNotasVO.getFilNom1() " +
			// filtroRegNotasVO.getFilNom1());

			if (filtroRegNotasVO.getFilNom2() == null
					|| filtroRegNotasVO.getFilNom2().trim().length() == 0) {
				st.setString(posicion++, "-99");
			} else {
				st.setString(posicion++, filtroRegNotasVO.getFilNom2());
			}
			st.setString(posicion++, filtroRegNotasVO.getFilNom2());

			st.setLong(posicion++, filtroRegNotasVO.getCantPags()
					* filtroRegNotasVO.getNumPag());
			st.setLong(posicion++,
					(filtroRegNotasVO.getCantPags() * (filtroRegNotasVO
							.getNumPag() - 1)));

			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				EstudianteRegNotasVO e = new EstudianteRegNotasVO();

				e.setNumFila(rs.getLong(posicion++));
				e.setEstcodigo(rs.getInt(posicion++));
				e.setEsttipodoc(rs.getInt(posicion++));
				e.setEstnumdoc(rs.getString(posicion++));
				e.setEstapellidos(rs.getString(posicion++));
				e.setEstnombres(rs.getString(posicion++));
				list.add(e);
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
		// System.out.println("list " + list.size() );
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
	public EstudianteRegNotasVO getNombreEstudiante(long codEst)
			throws Exception {
		// System.out.println("regNotasVigencia: getNombreEstudiante ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		EstudianteRegNotasVO estudianteVO = new EstudianteRegNotasVO();
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("regNotasVigencia.getNombreEstudiante"));
			st.setLong(posicion++, codEst);
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				estudianteVO.setEstcodigo(rs.getLong(posicion++));
				estudianteVO.setEstgrupo(rs.getLong(posicion++));
				estudianteVO.setEsttipodoc(rs.getInt(posicion++));
				estudianteVO.setEsttipodocNom(rs.getString(posicion++));
				estudianteVO.setEstnumdoc(rs.getString(posicion++));
				estudianteVO.setEstnombres(rs.getString(posicion++));
				estudianteVO.setEstapellidos(rs.getString(posicion++));
			}

			// System.out.println("estudianteVO.setEstnombre( " +
			// estudianteVO.getEstnombres());
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
		return estudianteVO;
	}

	/**
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public List getListaAreas(FiltroRegNotasVO filtroVO,
			EstudianteRegNotasVO estudianteRegNotasVO, TipoEvalVO tipoEvalVO,
			int numPer) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		int posicion = 1;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("regNotasVigencia.getListaAreas"));
			st.setLong(posicion++, estudianteRegNotasVO.getEstcodigo());
			// System.out.println("estudianteRegNotasVO.getEstcodigo() " +
			// estudianteRegNotasVO.getEstcodigo());
			st.setInt(posicion++, filtroVO.getFilvigencia());
			// System.out.println("filtroVO.getFilvigencia() " +
			// filtroVO.getFilvigencia());
			st.setInt(posicion++, filtroVO.getFilinst());
			// System.out.println("filtroVO.getFilinst()  " +
			// filtroVO.getFilinst() );
			st.setInt(posicion++, filtroVO.getFilmetod());
			// System.out.println("filtroVO.getFilmetod() " +
			// filtroVO.getFilmetod());
			st.setInt(posicion++, filtroVO.getFilgrado());
			// System.out.println("filtroVO.getFilgrado() " +
			// filtroVO.getFilgrado());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				AreaVO areaVO = new AreaVO(numPer);
				areaVO.setArecodigo(rs.getInt(posicion++));
				areaVO.setArenombre(rs.getString(posicion++));
				areaVO.setArecodJerar(rs.getLong(posicion++));

				if (tipoEvalVO.getCod_tipo_eval() != ParamsVO.TIPO_EVAL_ASIG_CONCEPTUAL) {
					areaVO.setListaEvalAreNotaAdd(0,
							"" + rs.getDouble(posicion++));
					areaVO.setListaEvalAreNotaAdd(1,
							"" + rs.getDouble(posicion++));
					areaVO.setListaEvalAreNotaAdd(2,
							"" + rs.getDouble(posicion++));
					areaVO.setListaEvalAreNotaAdd(3,
							"" + rs.getDouble(posicion++));
					areaVO.setListaEvalAreNotaAdd(4,
							"" + rs.getDouble(posicion++));
					areaVO.setListaEvalAreNotaAdd(5,
							"" + rs.getDouble(posicion++));
					areaVO.setFinalEvalAreNotaAdd("" + rs.getDouble(posicion++));
				} else {
					areaVO.setListaEvalAreNotaAdd(0,
							"" + rs.getLong(posicion++));
					areaVO.setListaEvalAreNotaAdd(1,
							"" + rs.getLong(posicion++));
					areaVO.setListaEvalAreNotaAdd(2,
							"" + rs.getLong(posicion++));
					areaVO.setListaEvalAreNotaAdd(3,
							"" + rs.getLong(posicion++));
					areaVO.setListaEvalAreNotaAdd(4,
							"" + rs.getLong(posicion++));
					areaVO.setListaEvalAreNotaAdd(5,
							"" + rs.getLong(posicion++));
					areaVO.setFinalEvalAreNotaAdd("" + rs.getLong(posicion++));

				}
				// System.out.println("areaVO.getArecodigo() " +
				// areaVO.getArecodigo());
				// System.out.println("areaVO.getArenombre() " +
				// areaVO.getArenombre());
				// System.out.println("areaVO.getArecodJerar() " +
				// areaVO.getArecodJerar());
				lista.add(areaVO);
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
		return lista;
	}

	/**
	 * @param filtroVO
	 * @param areaVO
	 * @return
	 * @throws Exception
	 */
	public List getListaAsignaturas(FiltroRegNotasVO filtroVO, AreaVO areaVO,
			EstudianteRegNotasVO estudianteRegNotasVO, TipoEvalVO tipoEvalVO,
			int numPeriodos) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		int posicion = 1;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("regNotasVigencia.getListaAsig"));

			st.setLong(posicion++, estudianteRegNotasVO.getEstcodigo());
			st.setInt(posicion++, filtroVO.getFilvigencia());
			st.setInt(posicion++, filtroVO.getFilinst());
			st.setInt(posicion++, filtroVO.getFilmetod());
			st.setInt(posicion++, filtroVO.getFilgrado());
			st.setInt(posicion++, areaVO.getArecodigo());

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				AsignaturaVO a = new AsignaturaVO(numPeriodos);
				a.setAsicodigo(rs.getInt(posicion++));
				a.setAsinombre(rs.getString(posicion++));
				a.setAsicodjerar(rs.getLong(posicion++));
				if (tipoEvalVO.getCod_tipo_eval() != ParamsVO.TIPO_EVAL_ASIG_CONCEPTUAL) {
					a.setListaEvalAsiNotaAdd(0, "" + rs.getDouble(posicion++));
					a.setListaEvalAsiNotaAdd(1, "" + rs.getDouble(posicion++));
					a.setListaEvalAsiNotaAdd(2, "" + rs.getDouble(posicion++));
					a.setListaEvalAsiNotaAdd(3, "" + rs.getDouble(posicion++));
					a.setListaEvalAsiNotaAdd(4, "" + rs.getDouble(posicion++));
					a.setListaEvalAsiNotaAdd(5, "" + rs.getDouble(posicion++));
					a.setFinalEvalAsiNotaAdd("" + rs.getDouble(posicion++));
				} else {
					a.setListaEvalAsiNotaAdd(0, "" + rs.getLong(posicion++));
					a.setListaEvalAsiNotaAdd(1, "" + rs.getLong(posicion++));
					a.setListaEvalAsiNotaAdd(2, "" + rs.getLong(posicion++));
					a.setListaEvalAsiNotaAdd(3, "" + rs.getLong(posicion++));
					a.setListaEvalAsiNotaAdd(4, "" + rs.getLong(posicion++));
					a.setListaEvalAsiNotaAdd(5, "" + rs.getLong(posicion++));
					a.setFinalEvalAsiNotaAdd("" + rs.getLong(posicion++));
				}
				lista.add(a);
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
		return lista;
	}

	/**
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public InstitucionParametroVO getListaPeriodos(FiltroCommonVO filtroVO)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		InstitucionParametroVO itemVO = new InstitucionParametroVO();
		List list = new ArrayList();

		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("regNotasVigencia.getListaPeriodos"));
			st.setInt(posicion++, filtroVO.getFilvigencia());
			st.setInt(posicion++, filtroVO.getFilinst());

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;

				itemVO.setInsparvigencia(rs.getInt(posicion++));
				itemVO.setInsparcodinst(rs.getInt(posicion++));
				itemVO.setInsparnumper(rs.getInt(posicion++));
				itemVO.setInsparnomperdef(rs.getString(posicion++));
				itemVO.setInsparniveval(rs.getInt(posicion++));

			}
			itemVO.calcularLista();

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
		return itemVO;

	}

	/**
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public TipoEvalVO getTipoNivelEval(FiltroCommonVO filtroVO)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		int posicion = 1;
		TipoEvalVO tipoEvalVO = null;

		try {
			cn = cursor.getConnection();
			tipoEvalVO = getTipoEvalRegistroNotas(cn, filtroVO);
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

	public List obtenerNotasAnteriores(int tipo, int vigencia,
			long codAsigJerar, long codEst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		List list = new ArrayList();

		try {

			cn = cursor.getConnection();
			if (tipo == 1) {
				st = cn.prepareStatement(rb
						.getString("guardarLogNotas.getNotasAnterioresArea"));
			} else if (tipo == 2) {
				st = cn.prepareStatement(rb
						.getString("guardarLogNotas.getNotasAnterioresAsig"));
			}
			st.setInt(posicion++, vigencia);
			st.setLong(posicion++, codAsigJerar);
			st.setLong(posicion++, codEst);

			rs = st.executeQuery();
			posicion = 1;
			if (rs.next()) {

				for (int i = 1; i < 8; i++) {
					list.add(rs.getDouble(i) + "");
				}

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
	 * @function: Metndo que guarda los cambios hechos a las notas
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public List guardarLogNotas(FiltroCommonVO filtroVO, List listaArea,
			EstudianteRegNotasVO estVO, Login usuVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			// ARREAS
			for (int i = 0; i < listaArea.size(); i++) {
				AreaVO areaVO = (AreaVO) listaArea.get(i);			
				List areaVO2 = obtenerNotasAnteriores(1,filtroVO.getFilvigencia(), areaVO.getArecodJerar(),	estVO.getEstcodigo());
				// ACTUALIZAR SI EXISTE
				st = cn.prepareStatement(rb.getString("guardarLogNotas.insertLog"));
				//for (int i2 = 0; i < areaVO2.size(); i2++) {
					//System.out.println("nombre Area 2: "+ areaVO2.get(i)); 
				//}

				for (int i1 = 0; i1 < 6; i1++) {
					if (areaVO.getListaEvalAreNota(i1) > -99) {
						st.clearParameters();
						double a = 0;
						if (areaVO2.size() > 0) {
							a = Double.parseDouble(areaVO2.get(i1) + "");
						}

						if (a != areaVO.getListaEvalAreNota(i1)) {

							st.setString(1, usuVO.getUsuarioId());
							st.setString(2, "Se modificn la nota del área "
									+ areaVO.getArenombre() + ", periodo "
									+ (i1 + 1) + ", la cual era " + a + " por "
									+ areaVO.getListaEvalAreNota(i1));
							st.setLong(3, estVO.getEstcodigo());
							st.setLong(4, areaVO.getArecodJerar());
							st.setLong(5, filtroVO.getFilvigencia());
							st.setLong(6, filtroVO.getFilsede());
							st.setLong(7, filtroVO.getFiljornd());
							st.setLong(8, filtroVO.getFilmetod());
							st.setLong(9, filtroVO.getFilgrado());
							st.setLong(10, filtroVO.getFilgrupo());
							st.setLong(11, filtroVO.getFilinst());
							int flag = st.executeUpdate();

						}

					}
				}
				st.clearParameters();
				if (GenericValidator.isDouble(areaVO.getFinalEvalAreNota()) && Double.parseDouble(areaVO.getFinalEvalAreNota()) > -99) {
					double a = 0;
					if (areaVO2.size() > 0) {
						a = Double.parseDouble(areaVO2.get(6) + "");
					}
					if (Double.parseDouble(areaVO.getFinalEvalAreNota()) != a) {
						st.setString(1, usuVO.getUsuarioId());

						st.setString(2, "Se modificn la nota del área "
								+ areaVO.getArenombre()
								+ " nltimo periodo, la cual era " + a + " por "
								+ areaVO.getFinalEvalAreNota());
						st.setLong(3, estVO.getEstcodigo());
						st.setLong(4, areaVO.getArecodJerar());
						st.setLong(5, filtroVO.getFilvigencia());
						st.setLong(6, filtroVO.getFilsede());
						st.setLong(7, filtroVO.getFiljornd());
						st.setLong(8, filtroVO.getFilmetod());
						st.setLong(9, filtroVO.getFilgrado());
						st.setLong(10, filtroVO.getFilgrupo());
						st.setLong(11, filtroVO.getFilinst());
						int flag = st.executeUpdate();

					}
				}

			}

			// ASIGNATURAS

			for (int i = 0; i < listaArea.size(); i++) {
				AreaVO areaVO_ = (AreaVO) listaArea.get(i);
				List listaAsig = areaVO_.getListaAsig();

				for (int j = 0; j < listaAsig.size(); j++) {

					AsignaturaVO asigVO = (AsignaturaVO) listaAsig.get(j);
					List asigVO2 = obtenerNotasAnteriores(2,filtroVO.getFilvigencia(), asigVO.getAsicodjerar(),estVO.getEstcodigo());
					// ACTUALIZAR SI EXISTE
					for (int i1 = 0; i1 < 6; i1++) {
						st.clearParameters();
						if (asigVO.getListaEvalAsiNota(i1) > -99) {
							double a = 0;
							if (asigVO2.size() > 0) {
								a = Double.parseDouble(asigVO2.get(i1) + "");
							}
							if (a != (asigVO.getListaEvalAsiNota(i1))) {

								st.setString(1, usuVO.getUsuarioId());
								st.setString(2,
										"Se modificn la nota de la asignatura "
												+ asigVO.getAsinombre()
												+ ", periodo "+ (i1 + 1)
												+ ", la cual era "+ a
												+ " por "+ asigVO.getListaEvalAsiNota(i1));
								st.setLong(3, estVO.getEstcodigo());
								st.setLong(4, asigVO.getAsicodjerar());
								st.setLong(5, filtroVO.getFilvigencia());
								st.setLong(6, filtroVO.getFilsede());
								st.setLong(7, filtroVO.getFiljornd());
								st.setLong(8, filtroVO.getFilmetod());
								st.setLong(9, filtroVO.getFilgrado());
								st.setLong(10, filtroVO.getFilgrupo());
								st.setLong(11, filtroVO.getFilinst());
								int flag = st.executeUpdate();

							}
						}
					}
					st.clearParameters();
					if (GenericValidator.isLong(asigVO.getFinalEvalAsiNota())
							&& Long.parseLong(asigVO.getFinalEvalAsiNota()) > -99) {
						double a = 0;
						if (asigVO2.size() > 0) {
							a = Double.parseDouble(asigVO2.get(6) + "");
						}
						if (Double.parseDouble(asigVO.getFinalEvalAsiNota()) != a) {
							st.setString(1, usuVO.getUsuarioId());
							st.setString(
									2,
									"Se modificn la nota de la asignatura "
											+ asigVO.getAsinombre()
											+ " nltimo periodo, la cual era "
											+ a + " por "
											+ asigVO.getFinalEvalAsiNota());
							st.setLong(3, estVO.getEstcodigo());
							st.setLong(4, asigVO.getAsicodjerar());
							st.setLong(5, filtroVO.getFilvigencia());
							st.setLong(6, filtroVO.getFilsede());
							st.setLong(7, filtroVO.getFiljornd());
							st.setLong(8, filtroVO.getFilmetod());
							st.setLong(9, filtroVO.getFilgrado());
							st.setLong(10, filtroVO.getFilgrupo());
							st.setLong(11, filtroVO.getFilinst());
							int flag = st.executeUpdate();

						}
					}
					// where

				}
			}
			st.close();

			cn.setAutoCommit(true);
			cn.commit();
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

		return listaArea;

	}

	/**
	 * @function: Metndo que obtiene el listado de grupos
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public List guardarRegNota(FiltroCommonVO filtroVO, List listaArea,
			EstudianteRegNotasVO estVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			// ARREAS
			for (int i = 0; i < listaArea.size(); i++) {
				AreaVO areaVO = (AreaVO) listaArea.get(i);
				// ACTUALIZAR SI EXISTE
				st = cn.prepareStatement(rb.getString("guardarRegNota.updateAREA"));
				// System.out.println("sql " +
				// rb.getString("guardarRegNota.updateAREA"));
				posicion = 1;
				for (int i1 = 0; i1 < 6; i1++) {
					if (areaVO.getListaEvalAreNota(i1) > -99) {
						st.setDouble(posicion++, areaVO.getListaEvalAreNota(i1));
					} else {
						st.setNull(posicion++, Types.NUMERIC);
					}
				}
				if (GenericValidator.isDouble(areaVO.getFinalEvalAreNota())	&& Double.parseDouble(areaVO.getFinalEvalAreNota()) > -99) {
					st.setDouble(posicion++, Double.parseDouble(areaVO.getFinalEvalAreNota()));
				} else {
					st.setNull(posicion++, Types.NUMERIC);
				}
				// where
				st.setInt(posicion++, filtroVO.getFilvigencia());
				st.setLong(posicion++, areaVO.getArecodJerar());
				st.setLong(posicion++, estVO.getEstcodigo());
				int flag = st.executeUpdate();
				st.close();

				if (flag == 0) {
					// INSERTAR SI NO EXISTE
					st = cn.prepareStatement(rb.getString("guardarRegNota.insertAREA"));
					// System.out.println("SQL "
					// + rb.getString("guardarRegNota.insertAREA"));
					// st.clearParameters();
					posicion = 1;
					st.setInt(posicion++, filtroVO.getFilvigencia());
					st.setLong(posicion++, areaVO.getArecodJerar());
					st.setLong(posicion++, estVO.getEstcodigo());
					for (int i1 = 0; i1 < 6; i1++) {
						if (areaVO.getListaEvalAreNota(i1) > -99) {
							st.setDouble(posicion++,
									areaVO.getListaEvalAreNota(i1));
						} else {
							st.setNull(posicion++, Types.NUMERIC);
						}
					}
					if (GenericValidator.isDouble(areaVO.getFinalEvalAreNota())
							&& Double.parseDouble(areaVO.getFinalEvalAreNota()) > -99) {
						st.setDouble(posicion++, Double.parseDouble(areaVO
								.getFinalEvalAreNota()));
					} else {
						st.setNull(posicion++, Types.NUMERIC);
					}
					st.executeUpdate();
					st.close();
				}
			}

			// ASIGNATURAS

			for (int i = 0; i < listaArea.size(); i++) {
				AreaVO areaVO_ = (AreaVO) listaArea.get(i);
				List listaAsig = areaVO_.getListaAsig();
				for (int j = 0; j < listaAsig.size(); j++) {

					AsignaturaVO asigVO = (AsignaturaVO) listaAsig.get(j);
					// ACTUALIZAR SI EXISTE
					st = cn.prepareStatement(rb
							.getString("guardarRegNota.updateASIG"));
					// System.out.println("sql "
					// + rb.getString("guardarRegNota.updateASIG"));
					// st.clearParameters();
					posicion = 1;

					for (int i1 = 0; i1 < 6; i1++) {
						if (asigVO.getListaEvalAsiNota(i1) > -99) {
							st.setDouble(posicion++,
									asigVO.getListaEvalAsiNota(i1));
						} else {
							st.setNull(posicion++, Types.NUMERIC);
						}
					}
					if (GenericValidator.isDouble(asigVO.getFinalEvalAsiNota())
							&& Double.parseDouble(asigVO.getFinalEvalAsiNota()) > -99) {
						st.setDouble(posicion++, Double.parseDouble(asigVO
								.getFinalEvalAsiNota()));
					} else {
						st.setNull(posicion++, Types.NUMERIC);
					}
					// where
					st.setInt(posicion++, filtroVO.getFilvigencia());
					st.setLong(posicion++, asigVO.getAsicodjerar());
					st.setLong(posicion++, estVO.getEstcodigo());

					int flag = st.executeUpdate();
					st.close();

					if (flag == 0) {

						// INSERTAR SI NO EXISTE
						st = cn.prepareStatement(rb
								.getString("guardarRegNota.insertASIG"));
						// System.out.println("SQL "
						// + rb.getString("guardarRegNota.insertASIG"));
						// st.clearParameters();
						posicion = 1;
						st.setInt(posicion++, filtroVO.getFilvigencia());
						st.setLong(posicion++, asigVO.getAsicodjerar());
						st.setLong(posicion++, estVO.getEstcodigo());

						for (int i1 = 0; i1 < 6; i1++) {
							if (asigVO.getListaEvalAsiNota(i1) > -99) {
								st.setDouble(posicion++,
										asigVO.getListaEvalAsiNota(i1));
							} else {
								st.setNull(posicion++, Types.NUMERIC);
							}
						}
						if (GenericValidator.isDouble(asigVO
								.getFinalEvalAsiNota())
								&& Double.parseDouble(asigVO
										.getFinalEvalAsiNota()) > -99) {
							st.setDouble(posicion++, Double.parseDouble(asigVO
									.getFinalEvalAsiNota()));
						} else {
							st.setNull(posicion++, Types.NUMERIC);
						}
						st.executeUpdate();
						st.close();
					}
				}
			} // for(int i=0;i< listaArea.size();i++ ){

			cn.setAutoCommit(true);
			cn.commit();
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

	public ArrayList obtenerDatosPromocion(long codInst, long vigencia,
			long grado, long metod, long tipoprom, long codest)
			throws Exception {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb
					.getString("regNotasVigencia.obtenerPromocion"));
			pst.setLong(posicion++, codInst);
			pst.setLong(posicion++, vigencia);
			pst.setLong(posicion++, grado);
			pst.setLong(posicion++, metod);
			pst.setLong(posicion++, tipoprom);
			pst.setLong(posicion++, codest);

			// System.out.println(rb
			// .getString("regNotasVigencia.obtenerPromocion"));
			// System.out.println(codInst);
			// System.out.println(vigencia);
			// System.out.println(grado);
			// System.out.println(metod);
			// System.out.println(tipoprom);
			// System.out.println(codest);

			rs = pst.executeQuery();

			if (rs.next()) {
				posicion = 1;
				list.add(rs.getInt(posicion++) + "");
				list.add(rs.getString(posicion++));
				list.add(rs.getInt(posicion++) + "");
				list.add(rs.getInt(posicion++) + "");
			}
			// else {
			//
			// System.out.println("hohsoahsoa");
			// }

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}

		return list;
	}

	public int guardarPromocion(long codInst, long vigencia, long grado,
			long metod, long codest, int prom, int tipoProm2, String obs,
			int tipoProm1) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		List list = new ArrayList();
		int flag = -1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			// ARREAS
			st = cn.prepareStatement(rb
					.getString("regNotasVigencia.guardarPromocion"));
			st.setLong(1, prom);
			st.setString(2, obs);
			st.setLong(3, tipoProm2);
			st.setLong(4, codInst);
			st.setLong(5, vigencia);
			st.setLong(6, grado);
			st.setLong(7, metod);
			st.setLong(8, codest);
			st.setLong(9, tipoProm1);

			flag = st.executeUpdate();
			st.close();

			cn.setAutoCommit(true);
			cn.commit();
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

		return flag;

	}

}
