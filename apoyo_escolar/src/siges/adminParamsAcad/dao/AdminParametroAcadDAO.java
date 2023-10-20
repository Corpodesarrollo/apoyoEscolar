package siges.adminParamsAcad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.adminParamsAcad.vo.DimensionVO;
import siges.adminParamsAcad.vo.NivelEvaluacionVO;
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
public class AdminParametroAcadDAO extends Dao {
	private ResourceBundle rb;

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto de obtencinn de conexiones a la base de datos
	 */
	public AdminParametroAcadDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.adminParamsAcad.bundle.AdminParamsAcad");
	}

	// NIVEL EVALUACION

	/**
	 * @return
	 * @throws Exception
	 */
	public List getNivelEvaluacion() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("nivelEval.getNivelEvaluacion"));
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				NivelEvaluacionVO n = new NivelEvaluacionVO();
				n.setG_nivevacodigo(rs.getLong(posicion++));
				n.setG_nivevasede(rs.getLong(posicion++));
				n.setG_nivevajorn(rs.getLong(posicion++));
				n.setG_nivevametod(rs.getLong(posicion++));
				n.setG_nivevanivel(rs.getLong(posicion++));
				n.setG_nivevagrado(rs.getLong(posicion++));
				n.setG_nivevanombre(rs.getString(posicion++));
				n.setG_nivevaorden(rs.getLong(posicion++));
				n.setG_nivevaestado(rs.getLong(posicion++));
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
	 * @param instParVO
	 * @return
	 * @throws Exception
	 */
	public void actualizarNivelEvaluacion(List lista) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("nivelEval.actualizarNivelEvaluacion"));
			posicion = 1;

			for (int i = 0; i < lista.size(); i++) {
				posicion = 1;
				NivelEvaluacionVO nivelEvaluacionVO = (NivelEvaluacionVO) lista
						.get(i);
				st.setLong(posicion++, nivelEvaluacionVO.getG_nivevaestado());
				// where
				st.setLong(posicion++, nivelEvaluacionVO.getG_nivevacodigo());
				st.addBatch();
			}

			st.executeBatch();

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

	}

	// DIMENSIONES

	/**
	 * @return
	 * @throws Exception
	 */
	public List getDimensiones() throws Exception {

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
	 * @return
	 * @throws Exception
	 */
	public boolean insertarDimension(DimensionVO d) throws Exception {
		// System.out.println("PARAMACADEMICOS: ENTRO INSERTAR DIMENSION DAO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		boolean cont = true;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("dimension.insertar"));
			st.setLong(1, d.getCodigo());
			st.setString(2, d.getNombre());
			st.executeUpdate();
			cont = true;
		} catch (SQLException sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
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
	public boolean actualizarDimension(DimensionVO d) throws Exception {
		// System.out.println("PARAMACADEMICOS: ENTRO ACTUALIZAR DIMENSION DAO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		boolean cont = true;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("dimension.actualizar"));
			st.setString(1, d.getNombre());
			st.setLong(2, d.getCodigo());
			st.executeUpdate();
			cont = true;
		} catch (SQLException sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
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
	public boolean eliminarDimension(long codigo) throws Exception {
		// System.out.println("PARAMACADEMICOS: ENTRO ELIMINAR DIMENSION DAO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		boolean cont = true;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("dimension.eliminar"));
			st.setLong(1, codigo);
			st.executeUpdate();
			cont = true;
		} catch (SQLException sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
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
	public boolean validarDimension(long codigo) throws Exception {
		// System.out.println("PARAMACADEMICOS: ENTRO VALIDAR DIMENSION DAO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		boolean cont = true;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("dimension.validar"));
			st.setLong(1, codigo);
			rs = st.executeQuery();
			if (rs.next())
				cont = true;
			else
				cont = false;
		} catch (SQLException sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			cont = false;
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
	public DimensionVO editarDimension(long codigo) throws Exception {
		// System.out
		// .println("PARAMACADEMICOS: ENTRO EDITAR DIMENSION DAO codigo: "
		// + codigo);
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		boolean cont = true;
		DimensionVO d = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("dimension.getDimension"));
			st.setLong(1, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				int i = 1;
				d = new DimensionVO();
				d.setCodigo(rs.getLong(i++));
				d.setNombre(rs.getString(i++));
				d.setInsertar(0);
			}
			cont = true;
		} catch (SQLException sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			cont = false;
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
	 * @return
	 * @throws Exception
	 */
	public long getCodigoNuevoDimension() throws Exception {
		// System.out.println("PARAMACADEMICOS: ENTRO CODIGO NUEVO DIMENSION DAO");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		long codigo = 0;
		boolean cont = true;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("dimension.getMaxCodigo"));
			rs = st.executeQuery();
			if (rs.next())
				codigo = rs.getLong(1);
		} catch (SQLException sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			cont = false;
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

}
