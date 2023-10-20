package navegador.adminNavegador.dao;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import navegador.adminNavegador.vo.SeccionNavegadorVO;
import navegador.exception.NavegadorException;
import navegador.exception.NavegadorIntegrityException;
import navegador.exception.NavegadorValidateException;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class AdminNavegadorDAO extends Dao {
	public AdminNavegadorDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("navegador.adminNavegador.bundle.sentencias");
	}

	public synchronized void insertar(SeccionNavegadorVO seccionNavegadorVO) throws NavegadorException{
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection(3);
			cn.setAutoCommit(false);
			// calculo de codigo
			pst = cn.prepareStatement(rb.getString("getCodigoSeccion"));
			posicion = 1;
			rs = pst.executeQuery();
			if (rs.next()) {
				seccionNavegadorVO.setCodigo(rs.getInt(1));
			}
			rs.close();
			pst.close();
			// Insercion
			pst = cn.prepareStatement(rb.getString("insercion"));
			pst.setLong(posicion++, seccionNavegadorVO.getCodigo());
			pst.setString(posicion++, seccionNavegadorVO.getNombre());
			pst.setString(posicion++, seccionNavegadorVO.getTipoMime());
			pst.setString(posicion++, seccionNavegadorVO.getNombreArchivo());
			if (seccionNavegadorVO.getArchivo() == null)
				pst.setNull(posicion++, Types.VARCHAR);
			else
				pst.setBinaryStream(posicion++, new java.io.ByteArrayInputStream(seccionNavegadorVO.getArchivo()), seccionNavegadorVO.getArchivo().length);

			pst.setString(posicion++, seccionNavegadorVO.getTipoMimeFondo());
			pst.setString(posicion++, seccionNavegadorVO.getNombreArchivoFondo());
			if (seccionNavegadorVO.getArchivoFondo() == null)
				pst.setNull(posicion++, Types.VARCHAR);
			else
				pst.setBinaryStream(posicion++, new java.io.ByteArrayInputStream(seccionNavegadorVO.getArchivoFondo()), seccionNavegadorVO.getArchivoFondo().length);

			pst.setInt(posicion++, seccionNavegadorVO.getOrden());
			pst.setString(posicion++, seccionNavegadorVO.getDescripcion());
			pst.executeUpdate();
			cn.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			try { cn.rollback(); } catch (SQLException ee) { }
			throw new NavegadorException(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			try { cn.rollback(); } catch (SQLException ee) { }
			throw new NavegadorException(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void eliminar(long parametro) throws NavegadorException{
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection(3);
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacion"));
			ps.setLong(posicion++, parametro);
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			try { cn.rollback(); } catch (SQLException s) { }
			switch (sqle.getErrorCode()) {
				// Violacinn de llave primaria
				case 1:
				case 2291:
					throw new NavegadorIntegrityException("Ya existe un registro con el mismo cndigo. No se puede Actualizar");
				// Longitud de campo
				case 1401:
					throw new NavegadorValidateException("Alguno(s) campo(s) exceden la longitud permitida");
				default:
					throw new NavegadorException(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
		}catch (Exception sqle) {
			setMensaje("Error eliminando Perfil. Posible problema: " + sqle.toString());
			throw new NavegadorException(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {}
		}
	}

	public SeccionNavegadorVO asignar(long parametro) throws NavegadorException{
		SeccionNavegadorVO seccionNavegadorVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection(3);
			ps = cn.prepareStatement(rb.getString("asignacion"));
			ps.clearParameters();
			posicion = 1;
			ps.setLong(posicion++, parametro);
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				seccionNavegadorVO = new SeccionNavegadorVO();
				seccionNavegadorVO.setEstado(1);
				seccionNavegadorVO.setCodigo(rs.getInt(i++));
				seccionNavegadorVO.setNombre(rs.getString(i++));
				seccionNavegadorVO.setTipoMime(rs.getString(i++));
				seccionNavegadorVO.setNombreArchivo(rs.getString(i++));
				Blob blob = rs.getBlob(i++);
				if (blob != null) {
					long len = blob.length();
					byte[] data = blob.getBytes((long) 1, (int) len);
					seccionNavegadorVO.setArchivo(data);
				}
				seccionNavegadorVO.setTipoMimeFondo(rs.getString(i++));
				seccionNavegadorVO.setNombreArchivoFondo(rs.getString(i++));
				Blob blob1 = rs.getBlob(i++);
				if (blob1 != null) {
					long len1 = blob1.length();
					byte[] data1 = blob1.getBytes((long) 1, (int) len1);
					seccionNavegadorVO.setArchivoFondo(data1);
				}
				seccionNavegadorVO.setOrden(rs.getInt(i++));
				seccionNavegadorVO.setDescripcion(rs.getString(i++));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {cn.rollback(); } catch (SQLException s) { }
			throw new NavegadorException(getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new NavegadorException(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return seccionNavegadorVO;
	}

	public void actualizar(SeccionNavegadorVO seccionNavegadorVO) throws NavegadorException {
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection(3);
			cn.setAutoCommit(false);
			if (seccionNavegadorVO.getTipoMime() == null || seccionNavegadorVO.getTipoMime().equals("") && seccionNavegadorVO.getTipoMimeFondo() == null || seccionNavegadorVO.getTipoMimeFondo().equals("")) {
				ps = cn.prepareStatement(rb.getString("actualizar0-0"));
				posicion = 1;
				ps.setString(posicion++, seccionNavegadorVO.getNombre());
				ps.setString(posicion++, seccionNavegadorVO.getNombreArchivo());
				ps.setString(posicion++, seccionNavegadorVO.getNombreArchivoFondo());
				ps.setInt(posicion++, seccionNavegadorVO.getOrden());
				ps.setString(posicion++, seccionNavegadorVO.getDescripcion());
				ps.setInt(posicion++, seccionNavegadorVO.getCodigo());
				ps.executeUpdate();
			} else {
				if (seccionNavegadorVO.getTipoMime() != null || !seccionNavegadorVO.getTipoMime().equals("") && seccionNavegadorVO.getTipoMimeFondo() == null || seccionNavegadorVO.getTipoMimeFondo().equals("")) {
					ps = cn.prepareStatement(rb.getString("actualizar1-0"));
					ps.setString(posicion++, seccionNavegadorVO.getNombre());
					ps.setString(posicion++, seccionNavegadorVO.getTipoMime());
					ps.setString(posicion++, seccionNavegadorVO.getNombreArchivo());
					if (seccionNavegadorVO.getArchivo() == null)
						ps.setNull(posicion++, Types.VARCHAR);
					else
						ps.setBinaryStream(posicion++, new java.io.ByteArrayInputStream(seccionNavegadorVO.getArchivo()), seccionNavegadorVO.getArchivo().length);

					ps.setString(posicion++, seccionNavegadorVO.getNombreArchivoFondo());
					ps.setInt(posicion++, seccionNavegadorVO.getOrden());
					ps.setString(posicion++, seccionNavegadorVO.getDescripcion());
					ps.setInt(posicion++, seccionNavegadorVO.getCodigo());
					ps.executeUpdate();
				} else {
					if (seccionNavegadorVO.getTipoMime() == null || seccionNavegadorVO.getTipoMime().equals("") && seccionNavegadorVO.getTipoMimeFondo() != null || !seccionNavegadorVO.getTipoMimeFondo().equals("")) {
						ps = cn.prepareStatement(rb.getString("actualizar0-1"));
						ps.setString(posicion++, seccionNavegadorVO.getNombre());
						ps.setString(posicion++, seccionNavegadorVO.getNombreArchivo());
						ps.setString(posicion++, seccionNavegadorVO.getTipoMimeFondo());
						ps.setString(posicion++, seccionNavegadorVO.getNombreArchivoFondo());
						if (seccionNavegadorVO.getArchivoFondo() == null)
							ps.setNull(posicion++, Types.VARCHAR);
						else
							ps.setBinaryStream(posicion++, new java.io.ByteArrayInputStream(seccionNavegadorVO.getArchivoFondo()), seccionNavegadorVO.getArchivoFondo().length);

						ps.setInt(posicion++, seccionNavegadorVO.getOrden());
						ps.setString(posicion++, seccionNavegadorVO.getDescripcion());
						ps.setInt(posicion++, seccionNavegadorVO.getCodigo());
						ps.executeUpdate();
					} else {
						ps = cn.prepareStatement(rb.getString("actualizar1-1"));
						ps.setString(posicion++, seccionNavegadorVO.getNombre());
						ps.setString(posicion++, seccionNavegadorVO.getTipoMime());
						ps.setString(posicion++, seccionNavegadorVO.getNombreArchivo());
						if (seccionNavegadorVO.getArchivo() == null)
							ps.setNull(posicion++, Types.VARCHAR);
						else
							ps.setBinaryStream(posicion++, new java.io.ByteArrayInputStream(seccionNavegadorVO.getArchivo()), seccionNavegadorVO.getArchivo().length);

						ps.setString(posicion++, seccionNavegadorVO.getTipoMimeFondo());
						ps.setString(posicion++, seccionNavegadorVO.getNombreArchivoFondo());
						if (seccionNavegadorVO.getArchivoFondo() == null)
							ps.setNull(posicion++, Types.VARCHAR);
						else
							ps.setBinaryStream(posicion++, new java.io.ByteArrayInputStream(seccionNavegadorVO.getArchivoFondo()), seccionNavegadorVO.getArchivoFondo().length);

						ps.setInt(posicion++, seccionNavegadorVO.getOrden());
						ps.setString(posicion++, seccionNavegadorVO.getDescripcion());
						ps.setInt(posicion++, seccionNavegadorVO.getCodigo());
						ps.executeUpdate();
					}
				}
			}
			cn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {cn.rollback();} catch (SQLException s) {}
			throw new NavegadorException(getErrorCode(e));
		} catch (Exception e) {
			e.printStackTrace();
			throw new NavegadorException(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getAllListaSeccioNavegador() throws NavegadorException{
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = new ArrayList();
		SeccionNavegadorVO seccionNavegadorVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection(3);
			ps = cn.prepareStatement(rb.getString("lista"));
			rs = ps.executeQuery();
			while (rs.next()) {
				i = 1;
				seccionNavegadorVO = new SeccionNavegadorVO();
				seccionNavegadorVO.setCodigo(rs.getInt(i++));
				seccionNavegadorVO.setNombre(rs.getString(i++));
				seccionNavegadorVO.setOrden(rs.getInt(i++));

				lista.add(seccionNavegadorVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new NavegadorException(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new NavegadorException(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;

	}

	public List getAllListaTipoMime() throws NavegadorException {
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = new ArrayList();
		try {
			cn = cursor.getConnection(3);
			ps = cn.prepareStatement(rb.getString("listaTipoMime"));
			rs = ps.executeQuery();
			while (rs.next()) {
				lista.add(rs.getString(1));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new NavegadorException(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new NavegadorException(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	public List getAllListaTipoMimeImagen() throws NavegadorException{
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = new ArrayList();
		try {
			cn = cursor.getConnection(3);
			ps = cn.prepareStatement(rb.getString("listaTipoMimeImagen"));
			rs = ps.executeQuery();
			while (rs.next()) {
				lista.add(rs.getString(1));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new NavegadorException(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new NavegadorException(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

}
