/**
 * 
 */
package articulacion.artRotacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.artRotacion.vo.FiltroRecesoVO;
import articulacion.artRotacion.vo.RecesoVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * 10/03/2008
 * 
 * @author Latined
 * @version 1.2
 */
public class ArtRecesoDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 */
	public ArtRecesoDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.artRotacion.bundle.artRotacion");
	}

	public List getAllTipoReceso() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			// System.out.println("Calculo de jor"+inst+"::"+sede+"::"+jor);
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("receso.getAllTipo"));
			i = 1;
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getInt(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error SQL: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error Interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List buscarReceso(FiltroRecesoVO filtroRecesoVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		RecesoVO recesoVO = null;
		int i = 0;
		try {
			// System.out.println("Buscar: "+filtroRecesoVO.getFilEstructura());
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("receso.buscar"));
			i = 1;
			st.setLong(i++, filtroRecesoVO.getFilEstructura());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				recesoVO = new RecesoVO();
				recesoVO.setResEstructura(filtroRecesoVO.getFilEstructura());
				recesoVO.setResCodigo(rs.getInt(i++));
				recesoVO.setResTipoNombre(rs.getString(i++));
				recesoVO.setResNombre(rs.getString(i++));
				recesoVO.setResClase(rs.getInt(i++));
				recesoVO.setResDuracionHor(rs.getInt(i++));
				recesoVO.setResDuracionMin(rs.getInt(i++));
				l.add(recesoVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error SQL: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error Interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public RecesoVO editarReceso(long estructura, int codigo) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		RecesoVO recesoVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("receso.editar"));
			i = 1;
			st.setLong(i++, estructura);
			st.setInt(i++, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				recesoVO = new RecesoVO();
				recesoVO.setResEstructura(estructura);
				recesoVO.setResCodigo(codigo);
				recesoVO.setResTipo(rs.getInt(i++));
				recesoVO.setResNombre(rs.getString(i++));
				recesoVO.setResClase(rs.getInt(i++));
				recesoVO.setResDuracionHor(rs.getInt(i++));
				recesoVO.setResDuracionMin(rs.getInt(i++));
				recesoVO.setFormaEstado("1");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error SQL: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error Interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return recesoVO;
	}

	public void eliminarReceso(long estructura, int codigo) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("receso.eliminar"));
			i = 1;
			st.setLong(i++, estructura);
			st.setInt(i++, codigo);
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error SQL: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error Interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void ingresarReceso(RecesoVO recesoVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			// System.out.println("Ingresa receso");
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("receso.getCodigo"));
			i = 1;
			st.setLong(i++, recesoVO.getResEstructura());
			rs = st.executeQuery();
			if (rs.next()) {
				recesoVO.setResCodigo(rs.getInt(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("receso.ingresar"));
			i = 1;
			st.setLong(i++, recesoVO.getResEstructura());
			st.setLong(i++, recesoVO.getResCodigo());
			st.setInt(i++, recesoVO.getResTipo());
			st.setString(i++, recesoVO.getResNombre());
			st.setInt(i++, recesoVO.getResClase());
			st.setInt(i++, recesoVO.getResDuracionHor());
			st.setInt(i++, recesoVO.getResDuracionMin());
			int n = st.executeUpdate();
			// System.out.println("Ingresn: "+n);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error SQL: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error Interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void actualizarReceso(RecesoVO recesoVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;
		try {
			// System.out.println("Actualiza receso");
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("receso.actualizar"));
			i = 1;
			st.setInt(i++, recesoVO.getResTipo());
			st.setString(i++, recesoVO.getResNombre());
			st.setInt(i++, recesoVO.getResClase());
			st.setInt(i++, recesoVO.getResDuracionHor());
			st.setInt(i++, recesoVO.getResDuracionMin());
			// w
			st.setLong(i++, recesoVO.getResEstructura());
			st.setLong(i++, recesoVO.getResCodigo());
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error SQL: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error Interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getAllClase(long est) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		int j = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("receso.getAllClase"));
			i = 1;
			st.setLong(i, est);
			rs = st.executeQuery();
			if (rs.next()) {
				j = rs.getInt(1);
			}
			for (i = 1; i <= j; i++) {
				item = new ItemVO();
				item.setCodigo(i);
				item.setNombre(String.valueOf(i));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error SQL: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error Interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}
}
