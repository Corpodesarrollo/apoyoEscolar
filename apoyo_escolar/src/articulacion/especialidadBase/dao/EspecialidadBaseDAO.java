package articulacion.especialidadBase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import articulacion.especialidadBase.vo.EspecialidadBaseVO;
import siges.exceptions.InternalErrorException;
import articulacion.universidad.vo.UniversidadVO;

public class EspecialidadBaseDAO extends Dao {
	public EspecialidadBaseDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.especialidadBase.bundle.sentencias");
	}

	public boolean insertar(EspecialidadBaseVO especialidad) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;

		try {
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("insercion"));

			prepST.setInt(posicion++, especialidad.getGespbasCodigo());
			prepST.setString(posicion++, especialidad.getGespbasNombre());

			prepST.execute();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno = false;
		} catch (SQLException e) {
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

	public boolean eliminar(String codigo) {
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacion"));
			posicion = 1;
			if (codigo.equals(""))
				ps.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				ps.setInt(posicion++, Integer.parseInt(codigo));

			ps.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			// setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
			switch (sqle.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado ");
				break;
			case 2292:
				setMensaje("Registros Asociados ");
				break;
			}
			return false;
		} catch (Exception sqle) {

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

	public EspecialidadBaseVO asignar(String codUniv) {
		EspecialidadBaseVO especialidadBaseVo = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignacion"));
			ps.clearParameters();
			posicion = 1;
			ps.setInt(posicion++, Integer.parseInt(codUniv));
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				especialidadBaseVo = new EspecialidadBaseVO();
				especialidadBaseVo.setFormaEstado("1");
				// este orden es igual al orden de la base de datos
				especialidadBaseVo.setGespbasCodigo(rs.getInt(i++));
				especialidadBaseVo.setGespbasNombre(rs.getString(i++));

			}
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
		return especialidadBaseVo;
	}

	public boolean actualizar(EspecialidadBaseVO especialidadbase1,
			EspecialidadBaseVO especialidadbase2) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizar"));

			ps.setLong(posicion++, especialidadbase1.getGespbasCodigo());

			if (especialidadbase1.getGespbasNombre().equals(""))
				ps.setNull(posicion++, Types.ARRAY);
			else
				ps.setString(posicion++, especialidadbase1.getGespbasNombre());

			ps.setLong(posicion++, especialidadbase2.getGespbasCodigo());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado ");
				break;
			case 2292:
				setMensaje("Registros Asociados ");
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

	public List getAllListaEspecialidadBase() {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		List listaEspecialidadBase = new ArrayList();
		EspecialidadBaseVO especialidadBaseVO = null;

		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(rb.getString("lista"));
			while (rs.next()) {
				i = 1;
				especialidadBaseVO = new EspecialidadBaseVO();

				especialidadBaseVO.setGespbasCodigo(rs.getInt(i++));
				especialidadBaseVO.setGespbasNombre(rs.getString(i++));

				listaEspecialidadBase.add(especialidadBaseVO);
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
		return listaEspecialidadBase;

	}

	public boolean validars(int dato) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("validarEspecialidadBase"));
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
			// System.out.println("estra a la funcion que es padre");
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
