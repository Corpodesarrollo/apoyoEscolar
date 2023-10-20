package articulacion.gAsignatura.dao;

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
import articulacion.gAsignatura.vo.GAsignaturaVO;
import articulacion.gAsignatura.vo.ListaAreaVO;

public class GAsignaturaDAO extends Dao {
	public GAsignaturaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.gAsignatura.bundle.sentencias");
	}

	public boolean insertar(GAsignaturaVO GAsignatura) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		try {

			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("insercion"));
			// System.out.println("El codigo de area es:"+GAsignatura.getGeArtAsigCodArea());
			prepST.setLong(posicion++, GAsignatura.getGeArtAsigCodArea());
			prepST.setLong(posicion++, GAsignatura.getGeArtAsigCodigo());
			prepST.setString(posicion++, GAsignatura.getGeArtAsigNombre());

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
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
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

	public boolean eliminar(String codGAsignatura) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacion"));
			ps.setLong(1, Long.parseLong(codGAsignatura));
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

	public GAsignaturaVO asignar(String codGAsignatura, long codArea) {
		GAsignaturaVO GAsignaturaVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignacion"));
			ps.setLong(posicion++, Long.parseLong(codGAsignatura));
			ps.setLong(posicion++, codArea);
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				GAsignaturaVO = new GAsignaturaVO();
				GAsignaturaVO.setFormaEstado("1");
				GAsignaturaVO.setGeArtAsigCodArea(rs.getInt(i++));
				GAsignaturaVO.setGeArtAsigCodigo(rs.getLong(i++));
				GAsignaturaVO.setGeArtAsigNombre(rs.getString(i++));

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
		return GAsignaturaVO;
	}

	public boolean actualizar(GAsignaturaVO GAsignatura1,
			GAsignaturaVO GAsignatura2) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizar"));

			ps.setLong(posicion++, GAsignatura1.getGeArtAsigCodigo());

			if (GAsignatura1.getGeArtAsigNombre().equals(""))
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setString(posicion++, GAsignatura1.getGeArtAsigNombre());

			if (GAsignatura1.getGeArtAsigCodArea() == 0)
				ps.setNull(posicion++, Types.VARCHAR);
			else
				ps.setLong(posicion++, GAsignatura1.getGeArtAsigCodArea());

			ps.setLong(posicion++, GAsignatura2.getGeArtAsigCodigo());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}
			setMensaje("Error actualizando Area. Posible problema: "
					+ getErrorCode(e));
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			setMensaje("Error actualizando Area. Posible problema: "
					+ e.toString());
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

	public List getAllListaGAsignatura(long codigo) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List listaGAsignatura = new ArrayList();
		GAsignaturaVO gAsignaturaVO = null;

		int i = 0;
		try {
			// System.out.println("El codigo es:"+codigo);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("lista"));
			ps.setLong(1, codigo);
			rs = ps.executeQuery();

			while (rs.next()) {
				i = 1;
				gAsignaturaVO = new GAsignaturaVO();
				gAsignaturaVO.setGeArtAsigCodigo(rs.getLong(i++));
				gAsignaturaVO.setGeArtAsigNombre(rs.getString(i++));
				gAsignaturaVO.setGeArtAsigCodArea(rs.getLong(i++));

				listaGAsignatura.add(gAsignaturaVO);
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
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaGAsignatura;
	}

	public List getListaAreas() {
		Connection conect = null;
		PreparedStatement prepST = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ListaAreaVO areaVO = null;
		int posicion = 1;
		try {
			areaVO = new ListaAreaVO();
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("ListaAreas"));
			rs = prepST.executeQuery();
			while (rs.next()) {
				posicion = 1;
				areaVO = new ListaAreaVO();
				areaVO.setCodigo(rs.getLong(posicion++));
				areaVO.setNombre(rs.getString(posicion++));
				l.add(areaVO);
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}
}
