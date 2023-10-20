package siges.grupoPeriodo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.grupoPeriodo.beans.AdminVO;
import siges.login.beans.Login;

/**
 * siges.grupoPeriodo.dao<br>
 * Funcinn: <br>
 */

public class AdminDAO extends Dao {
	private ResourceBundle rb;

	public AdminDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("administracion");
	}

	public AdminVO getAdminHorario(AdminVO adminVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long inst = Long.parseLong(adminVO.getAdminInst());
			long validacion = -1;
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getSystemHorario"));
			rs = pst.executeQuery();
			if (rs.next()) {
				validacion = rs.getLong(1);
			}
			rs.close();
			pst.close();
			adminVO.setAdminLectura(String.valueOf(validacion));
			// System.out.println("esto vale validacion" + validacion);
			pst = cn.prepareStatement(rb.getString("getAdminHorario"));
			pst.clearParameters();
			int posicion = 1;
			pst.setLong(posicion++, (inst));
			rs = pst.executeQuery();
			if (rs.next()) {
				adminVO.setAdminHorario(rs.getString(1));
				adminVO.setAdminLogro(rs.getInt(2));
				adminVO.setAdminDescriptor(rs.getInt(3));
				adminVO.setAdminMaxAsignatura(rs.getFloat(4));
				adminVO.setAdminPlanEstudios(rs.getInt(5));
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener adminHorario. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return adminVO;
	}

	public boolean setAdminHorario(AdminVO adminVO) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long inst = Long.parseLong(adminVO.getAdminInst());
			long hor = Long.parseLong(adminVO.getAdminHorario());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println("))))"+adminVO.getAdminLogro()+"-"+adminVO.getAdminDescriptor());
			pst = cn.prepareStatement(rb.getString("setAdminHorario"));
			pst.clearParameters();
			int posicion = 1;
			pst.setLong(posicion++, (hor));
			pst.setInt(posicion++, adminVO.getAdminLogro());
			pst.setInt(posicion++, adminVO.getAdminDescriptor());
			pst.setFloat(posicion++, adminVO.getAdminMaxAsignatura());
			pst.setInt(posicion++, adminVO.getAdminPlanEstudios());
			pst.setLong(posicion++, (inst));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener adminHorario. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public Collection getUsuariosBitacora(Login l) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaUsuarios"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getInstId()));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener lista Usuarios. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getBitacora(AdminVO a, Login l) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			String us = a.getAdminUsuarioBitacora();
			String fecha = a.getAdminFechaBitacora();
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			Date d = new java.sql.Date(sdf.parse((String) fecha).getTime());
			
			fecha = a.getAdminFechaBitacoraFinal();
			sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			Date f = new java.sql.Date(sdf.parse((String) fecha).getTime());
			
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaBitacora"));
			posicion = 1;
			ps.setString(posicion++, us);
			ps.setDate(posicion++, d);
			ps.setDate(posicion++, f);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Bitacora. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			setMensaje("error buscando bitacora:" + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}
}
