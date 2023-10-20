package siges.adminGrupo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.adminGrupo.vo.FiltroGrupoVO;
import siges.adminGrupo.vo.GrupoVO;
import siges.adminParamsAcad.vo.DimensionVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el mndulo de aprobacinn del POA. 26/01/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class AdminGrupoDAO extends Dao {
	private ResourceBundle rb;

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto de obtencinn de conexiones a la base de datos
	 */
	public AdminGrupoDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("siges.adminGrupo.bundle.adminGrupo");
	}

	// ADMIN GRUPO 16-04-10 WG

	/**
	 * @param competenciasVO
	 * @return
	 * @throws Exception
	 */

	public GrupoVO actualizarGrupo(GrupoVO d) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.actualizar"));

			st.setLong(posicion++, d.getCodEspacio());
			st.setString(posicion++, d.getDocDirector());
			st.setString(posicion++, d.getNombre());
			st.setInt(posicion++, d.getCupo());
			st.setLong(posicion++, d.getOrden());
			long jerGrupo = getJerGrupo(d);
			st.setLong(posicion++, jerGrupo);
			// where
			st.setLong(posicion++, d.getCodJerGrado());
			st.setLong(posicion++, d.getCodigo());

			d.setInsertar(0);

			int a = st.executeUpdate();
			if (a == 0) {
				throw new Exception("No se actualizo el registro");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return d;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public long getJerGrupo(GrupoVO grupo) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		long jerGrupo = 0;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.getJerarquia"));
			st.setLong(posicion++, grupo.getCodInst());
			st.setLong(posicion++, grupo.getCodSede());
			st.setLong(posicion++, grupo.getCodJorn());
			st.setLong(posicion++, grupo.getCodGrado());
			st.setLong(posicion++, grupo.getCodMetodo());
			st.setLong(posicion++, grupo.getCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				jerGrupo = rs.getInt(posicion++);
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
		return jerGrupo;
	}

	/**
	 * @param competenciasVO
	 * @return
	 * @throws Exception
	 */

	/*
	 * public GrupoVO guardarGrupo(GrupoVO d) throws Exception{
	 * 
	 * Connection cn = null; PreparedStatement st = null; ResultSet rs = null;
	 * int posicion = 0; try{ cn=cursor.getConnection(); posicion = 1;
	 * st=cn.prepareStatement(rb.getString("dimension.guardar"));
	 * st.setLong(posicion++, d.getCodinst()); st.setLong(posicion++,
	 * d.getCodmetod()); st.setLong(posicion++, d.getCoddim());
	 * st.setString(posicion++,d.getNombre()); st.setLong(posicion++,
	 * d.getOrden()); st.setString(posicion++,d.getAbrev());
	 * st.setLong(posicion++, d.getVigencia()); d.setEdicion(false);
	 * st.executeUpdate(); }catch(SQLException sqle){ sqle.printStackTrace();
	 * throw new Exception("Error de datos: "+sqle.getMessage());
	 * }catch(Exception sqle){ sqle.printStackTrace(); throw new
	 * Exception("Error interno: "+sqle.getMessage()); }finally{ try{
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeConnection(cn); }catch(InternalErrorException
	 * inte){} } return d; }
	 */

	/**
	 * @param competenciasVO
	 * @return
	 * @throws Exception
	 */

	public List listaGrupos(FiltroGrupoVO filtroGruposVO) throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		List lista = new ArrayList();

		try {

			// System.out.println("GRUPO: LISTA DAO");

			cn = cursor.getConnection();
			posicion = 1;
			String sql = rb.getString("grupo.select");
			sql = sql + " " + rb.getString("grupo.where1");
			if (filtroGruposVO.getFilCodGrado() > -99) {
				sql = sql + " " + rb.getString("grupo.where2");
			}
			st = cn.prepareStatement(sql);
			st.setLong(posicion++, filtroGruposVO.getFilCodInst());
			st.setLong(posicion++, filtroGruposVO.getFilCodSede());
			st.setLong(posicion++, filtroGruposVO.getFilCodJorn());
			st.setLong(posicion++, filtroGruposVO.getFilCodMetodo());
			if (filtroGruposVO.getFilCodGrado() > -99) {
				st.setLong(posicion++, filtroGruposVO.getFilCodGrado());
			}
			//
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				GrupoVO d = new GrupoVO();
				d.setNombreGrado(rs.getString(posicion++));
				d.setCodigo(rs.getLong(posicion++));
				d.setNombre(rs.getString(posicion++));
				d.setCupo(rs.getInt(posicion++));
				d.setNombreDirector(rs.getString(posicion++));
				d.setCodJerGrado(rs.getLong(posicion++));
				d.setCodGrado(rs.getLong(posicion++));
				d.setCodMetodo(rs.getLong(posicion++));
				d.setOrden(rs.getLong(posicion++));
				lista.add(d);
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
	 * @param GrupoVO
	 * @return
	 * @throws Exception
	 */

	public GrupoVO obtenerGrupo(GrupoVO d) throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {

			cn = cursor.getConnection();

			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.obtener"));

			st.setLong(posicion++, d.getCodJerGrado());
			st.setLong(posicion++, d.getCodigo());
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				d.setCodigo(rs.getLong(posicion++));
				d.setNombre(rs.getString(posicion++));
				d.setCupo(rs.getInt(posicion++));
				d.setCodJerGrado(rs.getLong(posicion++));
				d.setCodEspacio(rs.getLong(posicion++));
				d.setDocDirector(rs.getString(posicion++));
				d.setOrden(rs.getInt(posicion++));
				d.setCodGrado(rs.getLong(posicion++));
				d.setCodMetodo(rs.getLong(posicion++));
				d.setCodSede(rs.getLong(posicion++));
				d.setCodInst(rs.getLong(posicion++));
				d.setCodTipoEspacio(rs.getLong(posicion++));
				d.setCodJorn(rs.getLong(posicion++));
				d.setInsertar(0);
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
		return d;
	}

	/**
	 * @param competenciasVO
	 * @return
	 * @throws Exception
	 */

	public boolean eliminarGrupo(GrupoVO d) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.eliminar"));
			st.setLong(posicion++, d.getCodJerGrado());
			st.setLong(posicion++, d.getCodigo());
			int a = st.executeUpdate();
			if (a == 0) {
				return false;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getGrupo() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("dimension.getDimensiones"));
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				DimensionVO d = new DimensionVO();
				d.setCodigo(rs.getLong(posicion++));
				d.setNombre(rs.getString(posicion++));
				list.add(d);
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

	/**
	 * @param competenciasVO
	 * @return
	 * @throws Exception
	 */

	public boolean validarGrupo(long grupo) throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		boolean cont = false;
		try {

			cn = cursor.getConnection();

			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.validar"));
			// System.out.println("PARAMS GRUPO VALIDAR: JERGRUPO: " + grupo);
			st.setLong(posicion++, grupo);
			rs = st.executeQuery();
			if (rs.next()) {
				cont = true;
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
		return cont;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getlistaVigencia() throws Exception {

		List list = new ArrayList();
		ItemVO itemVO = null;
		try {

			for (int i = 2005; i <= Integer.valueOf(getVigencia()).longValue() + 1; i++) {
				itemVO = new ItemVO();
				itemVO.setCodigo(i);
				itemVO.setNombre(i + "");
				list.add(itemVO);
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getSedes(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.sedes"));
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

	/**
	 * @return
	 * @throws Exception
	 */
	public List getMetodologia(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.metodologias"));
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

	/**
	 * @return
	 * @throws Exception
	 */
	public List getJornadas(long codInst, long codSede) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.jornadas"));
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codSede);
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
	public List getGrados(long codInst, long codMetod) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.grados"));
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codMetod);
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
	public List getDirectores(long codInst, long codSede, long codJorn)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.listaDirector"));
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codSede);
			st.setLong(posicion++, codJorn);
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo2(rs.getString(posicion++));
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
	public List getTiposEspacio() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.tiposEspacio"));
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
	public List getEspaciosFisicos(long tipo, long inst, long sede)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("grupo.espaciosFisicos"));
			st.setLong(posicion++, inst);
			st.setLong(posicion++, sede);
			st.setLong(posicion++, tipo);
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
	 * @param codVig
	 * @param codInst
	 * @param codMetdo
	 * @param codArea
	 * @return
	 * @throws Exception
	 */
	public List getGrado(long codVig, long codInst, long codMetdo, long codArea)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			posicion = 1;
			st = cn.prepareStatement(rb.getString("competencias.getGrado"));

			st.setLong(posicion++, codVig);
			// System.out.println("codVig " + codVig);
			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst);
			st.setLong(posicion++, codMetdo);
			// System.out.println("codMetdo " + codMetdo);
			st.setLong(posicion++, codArea);
			// System.out.println("codArea " + codArea);
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

}
