package articulacion.asignatura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asignatura.vo.ComplementariaVO;
import articulacion.asignatura.vo.DatosVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class ComplementariaDAO extends Dao {

	public ComplementariaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.asignatura.bundle.sentencias");
	}

	public List getComplementarias(DatosVO datosVO, AsignaturaVO asignaturaVO) {
		// String componente, int area,int periodo,int codigo, long anoVig, int
		// periodoVig, int especialidad
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		ComplementariaVO complementariaVO = null;
		int posicion = 1, i = 0;
		try {
			int componente = Integer.parseInt(datosVO.getComponente());
			cn = cursor.getConnection();
			pt = cn.prepareStatement(rb.getString("lista_Complementaria."
					+ componente));
			pt.setLong(posicion++, asignaturaVO.getArtAsigCodigo());
			pt.setLong(posicion++, asignaturaVO.getArtAsigCodDinst());
			pt.setInt(posicion++, asignaturaVO.getArtAsigCodMetod());
			pt.setInt(posicion++, componente);
			pt.setLong(posicion++, datosVO.getArea());
			if (componente != 1)
				pt.setInt(posicion++, datosVO.getEspecialidad());
			pt.setInt(posicion++, datosVO.getPeriodo());
			pt.setLong(posicion++, datosVO.getAnoVigencia());
			pt.setInt(posicion++, datosVO.getPerVigencia());
			pt.setLong(posicion++, asignaturaVO.getArtAsigCodigo());
			rs = pt.executeQuery();
			while (rs.next()) {
				i = 1;
				// System.out.println("Encuentra datos ");
				complementariaVO = new ComplementariaVO();
				complementariaVO.setAsigCodigo(rs.getInt(i++));
				complementariaVO.setAsigComCodigo(rs.getInt(i++));
				complementariaVO.setAsigNombre(rs.getString(i++));
				complementariaVO.setAsigChecked(rs.getInt(i++));
				if (complementariaVO.getAsigChecked() == 1) {
					complementariaVO.setAsigChecked_("checked");
				}
				lista.add(complementariaVO);
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
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return lista;
	}

	public List getListaComplementariaIns(int asigCodigo) {
		Connection conect = null;
		PreparedStatement prepST = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ComplementariaVO complementariaVO = null;
		int posicion = 1;
		try {
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb
					.getString("ListaComplementariaIns"));
			prepST.setInt(posicion++, asigCodigo);
			rs = prepST.executeQuery();
			while (rs.next()) {
				posicion = 1;
				complementariaVO = new ComplementariaVO();
				complementariaVO.setAsigCodigo(rs.getInt(posicion++));
				complementariaVO.setAsigComCodigo(rs.getInt(posicion++));
				complementariaVO.setAsigNombre(rs.getString(posicion++));
				l.add(complementariaVO);
			}
		} catch (InternalErrorException e) {
			// System.out.println("El problema es interno");
			e.printStackTrace();
		} catch (SQLException e) {
			// System.out.println("El problema es sql");
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return l;
	}

	public boolean insertar(ComplementariaVO complementaria) {
		Connection cn = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		try {
			long com[] = complementaria.getNuevoComplemento();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// elimina complementarias
			prepST = cn.prepareStatement(rb
					.getString("eliminar_ComplementariaTotal"));
			prepST.setLong(1, complementaria.getAsigCodigo());
			prepST.executeUpdate();
			prepST.close();
			// ingresa
			if (com != null) {
				prepST = cn.prepareStatement(rb
						.getString("inserta_Complementaria"));
				prepST.clearBatch();
				for (int i = 0; i < com.length; i++) {
					posicion = 1;
					if (com[i] != 0) {
						prepST.setLong(posicion++,
								complementaria.getAsigCodigo());
						prepST.setLong(posicion++, com[i]);
						prepST.addBatch();
					}
				}
				prepST.executeBatch();
			}
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			e.printStackTrace();
			retorno = false;
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return retorno;
	}

	public boolean eliminar(int asigCodigo, String asigComCodigo) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println("Entra a eliminacion");
			ps = cn.prepareStatement(rb.getString("eliminar_Complementaria"));
			ps.setInt(1, asigCodigo);
			ps.setInt(2, Integer.parseInt(asigComCodigo));
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			// System.out.println("Falln algo"+sqle);
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			// setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
			return false;
		} catch (Exception sqle) {
			setMensaje("Error eliminando Complementaria. Posible problema: "
					+ sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return true;
	}

}
