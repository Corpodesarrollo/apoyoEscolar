package articulacion.asignatura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asignatura.vo.DatosVO;
import articulacion.asignatura.vo.RequisitoVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class CoRequisitoDAO extends Dao {

	public CoRequisitoDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.asignatura.bundle.sentencias");
	}

	// public List getCoRequisitos(int componente, int especialidad, int
	// area,int periodo,int codigo, long anoVig, int periodoVig){
	public List getCoRequisitos(DatosVO datosVO, AsignaturaVO asignaturaVO) {
		// int componente, int especialidad, int area,int periodo,int codigo,
		// long anoVig, int periodoVig
		// componente,especialidad,area,periodo,asignaturaVO.getArtAsigCodigo(),anoVig,periodoVig
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		RequisitoVO requisitoVO = null;
		int posicion = 1, i = 0;
		try {
			int componente = Integer.parseInt(datosVO.getComponente());
			cn = cursor.getConnection();
			pt = cn.prepareStatement(rb.getString("lista_Corequisito."
					+ datosVO.getComponente()));
			pt.setLong(posicion++, asignaturaVO.getArtAsigCodigo());
			pt.setLong(posicion++, asignaturaVO.getArtAsigCodDinst());
			pt.setInt(posicion++, asignaturaVO.getArtAsigCodMetod());
			pt.setInt(posicion++, componente);
			if (componente != 1)
				pt.setInt(posicion++, datosVO.getEspecialidad());
			pt.setInt(posicion++, datosVO.getPeriodo());
			pt.setLong(posicion++, datosVO.getAnoVigencia());
			pt.setInt(posicion++, datosVO.getPerVigencia());
			pt.setLong(posicion++, asignaturaVO.getArtAsigCodigo());
			rs = pt.executeQuery();
			while (rs.next()) {
				i = 1;
				requisitoVO = new RequisitoVO();
				requisitoVO.setAsigCodigo(rs.getInt(i++));
				requisitoVO.setAsigReCodigo(rs.getInt(i++));
				requisitoVO.setAsigNombre(rs.getString(i++));
				requisitoVO.setAsigChecked(rs.getInt(i++));
				if (requisitoVO.getAsigChecked() == 1) {
					requisitoVO.setAsigChecked_("checked");
				}
				lista.add(requisitoVO);
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

	public List getListaCoReqIns(int asigCodigo) {
		Connection conect = null;
		PreparedStatement prepST = null;
		ResultSet rs = null;
		List l = new ArrayList();
		RequisitoVO requisitoVO = null;
		int posicion = 1;
		try {
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("ListaCoreqIns"));
			prepST.setInt(posicion++, asigCodigo);
			rs = prepST.executeQuery();
			while (rs.next()) {
				posicion = 1;
				requisitoVO = new RequisitoVO();
				requisitoVO.setAsigCodigo(rs.getInt(posicion++));
				requisitoVO.setAsigReCodigo(rs.getInt(posicion++));
				requisitoVO.setAsigNombre(rs.getString(posicion++));
				l.add(requisitoVO);
			}
		} catch (InternalErrorException e) {
			// System.out.println("problema interno en correquisito");
			e.printStackTrace();
		} catch (SQLException e) {
			// System.out.println("problema sql en correquisito");
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

	public boolean insertar(RequisitoVO requisito) {
		Connection conect = null;
		PreparedStatement prepST = null;
		int posicion = 1;
		boolean retorno = false;
		try {
			// System.out.println("ENTRA A INSERTAR REGISTROS:"+requisito.getAsigCodigo());
			long req[] = requisito.getNuevoRequisito();
			conect = cursor.getConnection();
			conect.setAutoCommit(false);
			// eliminar los correquisitos
			prepST = conect.prepareStatement(rb
					.getString("eliminar_CorequisitoTotal"));
			prepST.setLong(1, requisito.getAsigCodigo());
			prepST.executeUpdate();
			prepST.close();
			prepST = conect.prepareStatement(rb
					.getString("inserta_Corequisito"));
			prepST.clearBatch();
			if (req != null) {
				// System.out.println("si hay prerequisitos");
				for (int i = 0; i < req.length; i++) {
					posicion = 1;
					if (req[i] != 0) {
						// System.out.println("ingresa: "+req[i]);
						prepST.setLong(posicion++, requisito.getAsigCodigo());
						prepST.setLong(posicion++, req[i]);
						prepST.addBatch();
					}
				}
				prepST.executeBatch();
			}
			conect.commit();
			retorno = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conect.rollback();
			} catch (SQLException s) {
			}
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return retorno;
	}

	public boolean eliminar(int asigCodigo, String asigCoCodigo) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminar_Corequisito"));
			ps.setInt(1, asigCodigo);
			ps.setInt(2, Integer.parseInt(asigCoCodigo));
			ps.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			// setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
			return false;
		} catch (Exception sqle) {
			// setMensaje("Error eliminando Corequisito. Posible problema: "+sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				// System.out.println("cierra conexion en correquisito");
			} catch (InternalErrorException inte) {
				System.out.println("El problema es cerrando");
				inte.printStackTrace();
			}
		}
		return true;
	}

}
