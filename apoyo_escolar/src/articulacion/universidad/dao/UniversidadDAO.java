package articulacion.universidad.dao;

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
import siges.exceptions.InternalErrorException;
import articulacion.universidad.vo.UniversidadVO;

public class UniversidadDAO extends Dao {
	public UniversidadDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.universidad.bundle.sentencias");
	}

	public boolean insertar(UniversidadVO universidad) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		// System.out.println("llego al metodo insertar del Dao ");
		try {

			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("insercion"));

			prepST.setInt(posicion++, universidad.getGuniCodigo());
			prepST.setString(posicion++, universidad.getGuniNombre());
			prepST.setString(posicion++, universidad.getGuniDireccionsp());
			prepST.setString(posicion++, universidad.getGuniTelefono());
			
			prepST.setString(posicion++,universidad.getGuniContacto());
			prepST.setString(posicion++,universidad.getGuniTelcontacto());
			prepST.setString(posicion++,universidad.getGuniCorreo());
			prepST.setInt(posicion++,universidad.getGuniEstado());
			
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

	public boolean eliminar(String codUniv) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacion"));
			ps.setInt(1, Integer.parseInt(codUniv));
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			// setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
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
			setMensaje("Error eliminando Perfil. Posible problema: "
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

	public UniversidadVO asignar(String codUniv) {
		UniversidadVO universidadVo = null;
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
				universidadVo = new UniversidadVO();
				universidadVo.setFormaEstado("1");
				// este orden es igual al orden de la base de datos
				universidadVo.setGuniCodigo(rs.getInt(i++));
				universidadVo.setGuniNombre(rs.getString(i++));
				universidadVo.setGuniDireccionsp(rs.getString(i++));
				universidadVo.setGuniTelefono(rs.getString(i++));
				
				universidadVo.setGuniContacto(rs.getString(i++));
				universidadVo.setGuniTelcontacto(rs.getString(i++));
				universidadVo.setGuniCorreo(rs.getString(i++));
				universidadVo.setGuniEstado(rs.getInt(i++));
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
		return universidadVo;
	}

	public boolean actualizar(UniversidadVO universidad1,
			UniversidadVO universidad2) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizar"));

			ps.setInt(posicion++, universidad1.getGuniCodigo());

			if (universidad1.getGuniNombre().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, universidad1.getGuniNombre());

			if (universidad1.getGuniDireccionsp().trim().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, universidad1.getGuniDireccionsp());

			if (universidad1.getGuniTelefono().trim().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, universidad1.getGuniTelefono());
			
			ps.setString(posicion++,universidad1.getGuniContacto());
			ps.setString(posicion++,universidad1.getGuniTelcontacto());
			ps.setString(posicion++,universidad1.getGuniCorreo());
			ps.setInt(posicion++,universidad1.getGuniEstado());
			
			
			
			
			ps.setLong(posicion++, universidad2.getGuniCodigo());
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

	public List getAllListaUniversidad() {
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		List listaUniversidad = new ArrayList();
		UniversidadVO universidadVO = null;

		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(rb.getString("lista"));
			while (rs.next()) {
				i = 1;
				universidadVO = new UniversidadVO();

				universidadVO.setGuniCodigo(rs.getInt(i++));
				universidadVO.setGuniNombre(rs.getString(i++));
				universidadVO.setGuniDireccionsp(rs.getString(i++));
				universidadVO.setGuniTelefono(rs.getString(i++));
				
				universidadVO.setGuniContacto(rs.getString(i++));
				universidadVO.setGuniTelcontacto(rs.getString(i++));
				universidadVO.setGuniCorreo(rs.getString(i++));
				universidadVO.setGuniEstado(rs.getInt(i++));
				
				listaUniversidad.add(universidadVO);
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
		return listaUniversidad;

	}

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
