package articulacion.asignatura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.grupoArt.vo.JornadaVO;
import articulacion.grupoArt.vo.SedeVO;
import articulacion.asignatura.vo.ListaAreaVO;
import articulacion.asignatura.vo.ListaEspecialidadVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class FiltroDAO extends Dao {

	private ResourceBundle rb;

	public FiltroDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.asignatura.bundle.sentencias");
	}

	public ListaEspecialidadVO[] getListaEspecialidad(String insti) {
		ListaEspecialidadVO[] especialidad = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List la = new ArrayList();
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEspecialidad"));
			ps.setString(posicion++, insti);
			posicion = 1;
			rs = ps.executeQuery();
			ListaEspecialidadVO a = null;

			while (rs.next()) {
				a = new ListaEspecialidadVO();
				posicion = 1;
				a.setCodigo(rs.getInt(posicion++));
				a.setNombre(rs.getString(posicion++));
				a.setCodTipoPeriodo(rs.getString(posicion++));
				// a.setTipoPeriodo(rs.getString(posicion++));
				la.add(a);
			}
			rs.close();
			ps.close();
			if (!la.isEmpty()) {
				int i = 0;
				especialidad = new ListaEspecialidadVO[la.size()];
				Iterator iterator = la.iterator();
				while (iterator.hasNext())
					especialidad[i++] = (ListaEspecialidadVO) (iterator.next());
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error Posible problema: " + sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return especialidad;
	}

	public List getListaAreas(String insti, String metod, int tipo) {
		Connection conect = null;
		PreparedStatement prepST = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ListaAreaVO areaVO = null;
		int posicion = 1;
		try {
			areaVO = new ListaAreaVO();
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("getAreas" + tipo));
			if (tipo == 1) {
				prepST.setString(posicion++, insti);
				prepST.setString(posicion++, metod);
			}
			rs = prepST.executeQuery();
			while (rs.next()) {
				posicion = 1;
				areaVO = new ListaAreaVO();
				areaVO.setCodigo(rs.getInt(posicion++));
				areaVO.setNombre(rs.getString(posicion++));
				if (tipo == 1) {
					areaVO.setPeriodo(rs.getInt(posicion++));
					areaVO.setAno(rs.getInt(posicion++));
				}
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
