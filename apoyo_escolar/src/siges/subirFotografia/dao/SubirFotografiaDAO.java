/**
 * 
 */
package siges.subirFotografia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.subirFotografia.vo.SubirFotografiaVO;

/**
 * 24/09/2008
 * 
 * @author Latined
 * @version 1.2
 */
public class SubirFotografiaDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 */
	public SubirFotografiaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.subirFotografia.bundle.subirFotografia");
	}

	public boolean actualizarFoto(int foto, long estudiante) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			// System.out.println("en DAO: actualizarFoto");

			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("foto.actualizar"));
			pst.setInt(posicion++, foto);
			// w--codigo del estudiante
			pst.setLong(posicion++, estudiante);
			pst.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			switch (e.getErrorCode()) {
			case 2292:
				throw new Exception(e.getMessage());
			case 1:
			case 2291:
				throw new Exception(e.getMessage());
			}
			throw new Exception(e.getMessage());
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarFotoPersonal(SubirFotografiaVO subirFotografiaVO,
			String pertipdocum) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			posicion = 1;
			// System.out.println("consulta " +
			// rb.getString("foto.actualizarPersonal"));
			pst = cn.prepareStatement(rb.getString("foto.actualizarPersonal"));

			pst.setBinaryStream(posicion++, new java.io.ByteArrayInputStream(
					subirFotografiaVO.getAyuArchivo()), subirFotografiaVO
					.getAyuArchivo().length);
			// w--codigo del estudiante
			pst.setInt(posicion++, Integer.parseInt(pertipdocum));
			// System.out.println("pertipdocum " + pertipdocum);
			pst.setString(posicion++, "" + subirFotografiaVO.getEstCodigo());
			// System.out.println("subirFotografiaVO.getEstCodigo() " +
			// subirFotografiaVO.getEstCodigo());

			int i = pst.executeUpdate();
			// System.out.println("Actualizo en foto personal = " + i);

			cn.setAutoCommit(true);
			cn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}

			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 2292:
				throw new Exception(e.getMessage());
			case 1:
			case 2291:
				throw new Exception(e.getMessage());
			}
			throw new Exception(e.getMessage());
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

}
