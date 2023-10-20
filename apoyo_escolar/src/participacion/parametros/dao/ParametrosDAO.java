package participacion.parametros.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import participacion.parametros.vo.DiscapacidadVO;
import participacion.parametros.vo.EtniaVO;
import participacion.parametros.vo.LgtbVO;
import participacion.parametros.vo.OcupacionVO;
import participacion.parametros.vo.RolVO;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.universidad.vo.UniversidadVO;

public class ParametrosDAO extends Dao {
	public ParametrosDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("participacion.parametros.bundle.parametros");
	}

	public boolean insertarDiscapacidad(DiscapacidadVO discapacidadVO) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		// System.out.println("llego al metodo insertar del Dao ");
		try {

			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb
					.getString("insercionDiscapacidad"));

			prepST.setInt(posicion++, discapacidadVO.getCodigo());
			prepST.setString(posicion++, discapacidadVO.getNombre());
			prepST.setString(posicion++, discapacidadVO.getDescripcion());

			prepST.execute();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno = false;
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
			}
			e.printStackTrace();
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public boolean insertarEtnia(EtniaVO etniaVO) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		// System.out.println("llego al metodo insertar del Dao ");
		try {
			// System.out.println("insercionEtnia ");
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("insercionEtnia"));

			prepST.setInt(posicion++, etniaVO.getCodigo());
			prepST.setString(posicion++, etniaVO.getNombre());

			prepST.execute();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			setMensaje("Error :" + e.getMensaje());
			System.out.println("Error " + e.getMensaje());
			retorno = false;
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
			}
			e.printStackTrace();
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	/**
	 * @function:
	 * @param etniaVO
	 * @return
	 */
	public boolean insertarLgtb(LgtbVO lgtbVO) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		// System.out.println("llego al metodo insertar del Dao ");
		try {

			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("insercionLgtb"));

			prepST.setInt(posicion++, lgtbVO.getCodigo());
			prepST.setString(posicion++, lgtbVO.getNombre());
			prepST.setString(posicion++, lgtbVO.getDescripcion());

			prepST.execute();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno = false;
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
			}
			e.printStackTrace();
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	/**
	 * @function:
	 * @param discapacidadVO
	 * @return
	 */
	public boolean insertarOcupacion(OcupacionVO ocupacionVO) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		// System.out.println("llego al metodo insertar del Dao ");
		try {

			conect = cursor.getConnection();
			prepST = conect
					.prepareStatement(rb.getString("insercionOcupacion"));

			prepST.setInt(posicion++, ocupacionVO.getCodigo());
			prepST.setString(posicion++, ocupacionVO.getNombre());
			prepST.setString(posicion++, ocupacionVO.getDescripcion());

			prepST.execute();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno = false;
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
			}
			e.printStackTrace();
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public boolean insertarRol(RolVO rolVO) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		// System.out.println("llego al metodo insertar del Dao ");
		try {

			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("insercionRol"));

			prepST.setInt(posicion++, rolVO.getCodigo());
			prepST.setString(posicion++, rolVO.getNombre());
			prepST.setString(posicion++, rolVO.getDescripcion());
			prepST.setInt(posicion++, Integer.parseInt(rolVO.getTipos()));

			prepST.execute();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno = false;
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
			}
			e.printStackTrace();
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public boolean eliminarDiscapacidad(String cod) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			// System.out.println("eliminacionDiscapacidad " + cod);
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacionDiscapacidad"));
			ps.setInt(1, Integer.parseInt(cod));
			// System.out.println("eliminacionDiscapacidad " + cod);
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error eliminanado Perfil. Posible problema: "
					+ getErrorCode(e));
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			return false;
		} catch (Exception sqle) {
			setMensaje("Error eliminando Registro. Posible problema: "
					+ sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public boolean eliminarEtnia(String cod) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			// System.out.println("eliminarEtnia " + cod);
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacionEtnia"));
			ps.setInt(1, Integer.parseInt(cod));
			// System.out.println("eliminarEtnia " + cod);
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error eliminanado Perfil. Posible problema: "
					+ getErrorCode(e));
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			return false;
		} catch (Exception sqle) {
			setMensaje("Error eliminando Registro. Posible problema: "
					+ sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public boolean eliminarLgtb(String cod) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			// System.out.println("eliminarLgtb " + cod);
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacionLgtb"));
			ps.setInt(1, Integer.parseInt(cod));
			// System.out.println("eliminarLgtb " + cod);
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error eliminanado Perfil. Posible problema: "
					+ getErrorCode(e));
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			return false;
		} catch (Exception sqle) {
			setMensaje("Error eliminando Registro. Posible problema: "
					+ sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public boolean eliminarOcupacion(String cod) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			// System.out.println("eliminarOcupacion " + cod);
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacionOcupacion"));
			ps.setInt(1, Integer.parseInt(cod));
			// System.out.println("eliminarOcupacion " + cod);
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error eliminanado Perfil. Posible problema: "
					+ getErrorCode(e));
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			return false;
		} catch (Exception sqle) {
			setMensaje("Error eliminando Registro. Posible problema: "
					+ sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public boolean eliminarRol(String cod) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			// System.out.println("eliminarRol " + cod);
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacionRol"));
			ps.setInt(1, Integer.parseInt(cod));
			// System.out.println("eliminarRol " + cod);
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error eliminanado Perfil. Posible problema: "
					+ getErrorCode(e));
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			return false;
		} catch (Exception sqle) {
			setMensaje("Error eliminando Registro. Posible problema: "
					+ sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public DiscapacidadVO asignarDiscapacidad(String cod) {
		DiscapacidadVO discapacidadVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignacionDiscapacidad"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, Integer.parseInt(cod));
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				discapacidadVO = new DiscapacidadVO();
				discapacidadVO.setFormaEstado("1");
				// este orden es igual al orden de la base de datos
				discapacidadVO.setCodigo(rs.getInt(i++));
				discapacidadVO.setNombre(rs.getString(i++));
				discapacidadVO.setDescripcion(rs.getString(i++));

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return discapacidadVO;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public EtniaVO asignarEtnia(String cod) {
		EtniaVO etniaVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignacionEtnia"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, Integer.parseInt(cod));
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				etniaVO = new EtniaVO();
				etniaVO.setFormaEstado("1");
				// este orden es igual al orden de la base de datos
				etniaVO.setCodigo(rs.getInt(i++));
				etniaVO.setNombre(rs.getString(i++));

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return etniaVO;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public LgtbVO asignarLgtb(String cod) {
		LgtbVO lgtbVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignacionLgtb"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, Integer.parseInt(cod));
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				lgtbVO = new LgtbVO();
				lgtbVO.setFormaEstado("1");
				// este orden es igual al orden de la base de datos
				lgtbVO.setCodigo(rs.getInt(i++));
				lgtbVO.setNombre(rs.getString(i++));
				lgtbVO.setDescripcion(rs.getString(i++));

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lgtbVO;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public OcupacionVO asignarOcupacion(String cod) {
		OcupacionVO ocupacionVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignacionOcupacion"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, Integer.parseInt(cod));
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				ocupacionVO = new OcupacionVO();
				ocupacionVO.setFormaEstado("1");
				// este orden es igual al orden de la base de datos
				ocupacionVO.setCodigo(rs.getInt(i++));
				ocupacionVO.setNombre(rs.getString(i++));
				ocupacionVO.setDescripcion(rs.getString(i++));
				// System.out.println("ocupacionVO.setNombre "
				// + ocupacionVO.getNombre());

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return ocupacionVO;
	}

	/**
	 * @function:
	 * @param cod
	 * @return
	 */
	public RolVO asignarRol(String cod) {
		RolVO rolVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignacionRol"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, Integer.parseInt(cod));
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				rolVO = new RolVO();
				rolVO.setFormaEstado("1");
				// este orden es igual al orden de la base de datos
				rolVO.setCodigo(rs.getInt(i++));
				rolVO.setNombre(rs.getString(i++));
				rolVO.setDescripcion(rs.getString(i++));
				rolVO.setTipos(String.valueOf(rs.getInt(i++)));
				// System.out.println("rolVO.setNombre " + rolVO.getNombre());

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return rolVO;
	}

	/**
	 * @function:
	 * @param discapacidadVO1
	 * @param discapacidadVO2
	 * @return
	 */
	public boolean actualizarDiscapacidad(DiscapacidadVO discapacidadVO1,
			DiscapacidadVO discapacidadVO2) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizarDiscapacidad"));

			ps.setInt(posicion++, discapacidadVO1.getCodigo());

			if (discapacidadVO1.getNombre() == null || discapacidadVO1.getNombre().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, discapacidadVO1.getNombre());

			if ( discapacidadVO1.getDescripcion() == null || discapacidadVO1.getDescripcion().trim().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, discapacidadVO1.getDescripcion());

			ps.setLong(posicion++, discapacidadVO1.getCodigo());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 2292:
				setMensaje("Registros Asociados");
				break;
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			}
			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return retorno;
	}

	/**
	 * @function:
	 * @param etniaVO1
	 * @param etniaVO
	 * @return
	 */
	public boolean actualizarEtnia(EtniaVO etniaVO1, EtniaVO etniaVO) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println("actualizarEtnia 1");
			ps = cn.prepareStatement(rb.getString("actualizarEtnia"));
			// System.out.println("actualizarEtnia 1");
			ps.setInt(posicion++, etniaVO1.getCodigo());
			// System.out.println("actualizarEtnia 1");
			if (etniaVO1.getNombre().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, etniaVO1.getNombre());
			// System.out.println("actualizarEtnia 1.6");

			ps.setLong(posicion++, etniaVO1.getCodigo());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 2292:
				setMensaje("Registros Asociados");
				break;
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			}
			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}
			System.out.println("Error : " + e.getMessage());
			return false;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return retorno;
	}

	public boolean actualizarLgtb(LgtbVO LgtbVO1, LgtbVO LgtbVO) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizarLgtb"));

			ps.setInt(posicion++, LgtbVO1.getCodigo());

			if (LgtbVO1.getNombre().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, LgtbVO1.getNombre());

			if (LgtbVO1.getDescripcion() == null || LgtbVO1.getDescripcion().trim().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, LgtbVO1.getDescripcion());

			ps.setLong(posicion++, LgtbVO1.getCodigo());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 2292:
				setMensaje("Registros Asociados");
				break;
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			}
			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return retorno;
	}

	public boolean actualizarOcupacion(OcupacionVO ocupacionVO1,
			OcupacionVO ocupacionVO) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizarOcupacion"));

			ps.setInt(posicion++, ocupacionVO1.getCodigo());

			if (ocupacionVO1.getNombre().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, ocupacionVO1.getNombre());

			if (ocupacionVO1.getDescripcion() == null || ocupacionVO1.getDescripcion().trim().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, ocupacionVO1.getDescripcion());

			ps.setLong(posicion++, ocupacionVO1.getCodigo());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 2292:
				setMensaje("Registros Asociados");
				break;
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			}
			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return retorno;
	}

	/**
	 * @function:
	 * @param discapacidadVO1
	 * @param discapacidadVO2
	 * @return
	 */
	public boolean actualizarRol(RolVO rolVO1, RolVO rolVO) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		// System.out.println("actulai rol ");
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizarRol"));

			ps.setInt(posicion++, rolVO1.getCodigo());

			if (rolVO1.getNombre().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, rolVO1.getNombre());

			if (rolVO1.getDescripcion() == null || rolVO1.getDescripcion().trim().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, rolVO1.getDescripcion());

			if (rolVO1.getTipos().trim().equals(""))
				ps.setNull(posicion++, Types.INTEGER);
			else
				ps.setInt(posicion++, Integer.parseInt(rolVO1.getTipos()));

			// System.out.println("actulai rol 2");

			ps.setLong(posicion++, rolVO1.getCodigo());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 2292:
				setMensaje("Registros Asociados");
				break;
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			}
			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return retorno;
	}

	/**
	 * @function:
	 * @return
	 */
	public List getAllListaDiscapacidades() {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		DiscapacidadVO discapacidadVO = null;

		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(rb.getString("listaDiscapacidad"));
			while (rs.next()) {
				i = 1;
				discapacidadVO = new DiscapacidadVO();

				discapacidadVO.setCodigo(rs.getInt(i++));
				discapacidadVO.setNombre(rs.getString(i++));
				discapacidadVO.setDescripcion(rs.getString(i++));

				lista.add(discapacidadVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
	 * @function:
	 * @return
	 */
	public List getAllListaEtnias() {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		EtniaVO etniaVO = null;

		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(rb.getString("listaEtnia"));
			while (rs.next()) {
				i = 1;
				etniaVO = new EtniaVO();

				etniaVO.setCodigo(rs.getInt(i++));
				etniaVO.setNombre(rs.getString(i++));

				lista.add(etniaVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
	 * @function:
	 * @return
	 */
	public List getAllListaLgtb() {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		LgtbVO lgtbVO = null;

		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(rb.getString("listaLgtb"));
			while (rs.next()) {
				i = 1;
				lgtbVO = new LgtbVO();

				lgtbVO.setCodigo(rs.getInt(i++));
				lgtbVO.setNombre(rs.getString(i++));
				lgtbVO.setDescripcion(rs.getString(i++));

				lista.add(lgtbVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
	 * @function:
	 * @return
	 */
	public List getAllListaOcupacion() {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		OcupacionVO ocupacionVO = null;

		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(rb.getString("listaOcupacion"));
			while (rs.next()) {
				i = 1;
				ocupacionVO = new OcupacionVO();

				ocupacionVO.setCodigo(rs.getInt(i++));
				ocupacionVO.setNombre(rs.getString(i++));
				ocupacionVO.setDescripcion(rs.getString(i++));

				lista.add(ocupacionVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
	 * @function:
	 * @return
	 */
	public List getAllListaRol() {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		RolVO rolVO = null;

		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(rb.getString("listaRol"));
			while (rs.next()) {
				i = 1;
				rolVO = new RolVO();

				rolVO.setCodigo(rs.getInt(i++));
				rolVO.setNombre(rs.getString(i++));
				rolVO.setDescripcion(rs.getString(i++));
				rolVO.setTipos(rs.getString(i++));

				lista.add(rolVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
	 * @function:
	 * @param dato
	 * @return
	 */
	public boolean validars(int dato) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("validarUniversidad"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, (dato));
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public boolean valPadre(String dato) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("validarhijoesp"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, Integer.parseInt(dato));
			rs = ps.executeQuery();

			if (rs.next())
				return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}
}
