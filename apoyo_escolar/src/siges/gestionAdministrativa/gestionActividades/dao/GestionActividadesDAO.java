package siges.gestionAdministrativa.gestionActividades.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.gestionActividades.vo.FiltroGestionVO;
import siges.gestionAdministrativa.gestionActividades.vo.GestionActVO;

public class GestionActividadesDAO extends Dao {

	public GestionActividadesDAO(Cursor cursor) {
		super(cursor);
		rb=ResourceBundle.getBundle("siges.gestionAdministrativa.gestionActividades.bundle.gestionActividades");
	}
	
	/**
	 * Crea un nuevo registro en la tabla AGE_ACTIVIDAD
	 * @param act
	 * @return
	 */
	public boolean saveActividad(GestionActVO act){
		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("actividad.Guardar"));
			st.setInt(posicion++, act.getNivel());
			st.setLong(posicion++, act.getCodJerarquia());
			st.setString(posicion++, act.getNombre());
			st.setString(posicion++, act.getDescripcion());
			st.setString(posicion++, act.getFecha());
			st.setString(posicion++, act.getHoraInicial());
			st.setString(posicion++, act.getHoraFinal());
			st.setString(posicion++, act.getLugar());
			st.setString(posicion++, act.getParticipantes());
			st.setString(posicion++, act.getUsuario());
			st.setString(posicion++, act.getFecha());
			st.setInt(posicion++, act.getEstado());
			int r = st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 * Trae las actividades que cumplen el criterio de busqueda
	 * @param codInst
	 * @param codSede
	 * @return
	 * @throws Exception
	 */
	public List getActividades( FiltroGestionVO filtroGestion) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		GestionActVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("actividades.getlista"));
			st.setInt(posicion++, filtroGestion.getEstado()-1);
			if(filtroGestion.getEstado() == 1){//Todos los estados
				st = cn.prepareStatement(rb
						.getString("actividades.getlistaTodas"));
				posicion = 1;
			}
			st.setInt(posicion++, filtroGestion.getNivel());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new GestionActVO();
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setNivel(rs.getInt(posicion++));
				itemVO.setCodJerarquia(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				itemVO.setDescripcion(rs.getString(posicion++));
				itemVO.setFecha(rs.getString(posicion++));
				itemVO.setHoraInicial(rs.getString(posicion++));
				itemVO.setHoraFinal(rs.getString(posicion++));
				itemVO.setLugar(rs.getString(posicion++));
				itemVO.setParticipantes(rs.getString(posicion++));
				itemVO.setUsuario(rs.getString(posicion++));
				itemVO.setEstado(rs.getInt(posicion++));
				list.add(itemVO);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	/**
	 * Edita un registro en la tabla AGE_ACTIVIDAD
	 * @param act
	 * @return
	 */
	public boolean updateActividad(GestionActVO act) {
		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("actividad.Editar"));
			st.setInt(posicion++, act.getNivel());
			st.setLong(posicion++, act.getCodJerarquia());
			st.setString(posicion++, act.getNombre());
			st.setString(posicion++, act.getDescripcion());
			st.setString(posicion++, act.getFecha());
			st.setString(posicion++, act.getHoraInicial());
			st.setString(posicion++, act.getHoraFinal());
			st.setString(posicion++, act.getLugar());
			st.setString(posicion++, act.getParticipantes());
			st.setString(posicion++, act.getUsuario());
			st.setString(posicion++, act.getFecha());
			st.setInt(posicion++, act.getEstado()-1);
			st.setLong(posicion++, act.getCodigo());
			int r = st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 * Trae un registro en la tabla AGE_ACTIVIDAD dado su pk
	 * @param act
	 * @return
	 */
	public GestionActVO getActividad(long id) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		GestionActVO act = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("actividad.Consultar"));
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				act = new GestionActVO();
				act.setCodigo(rs.getLong(posicion++));
				act.setNivel(rs.getInt(posicion++));
				posicion++;//act.setCodJerarquia(rs.getLong(posicion++));
				act.setNombre(rs.getString(posicion++));
				act.setDescripcion(rs.getString(posicion++));
				act.setFecha(rs.getString(posicion++));
				act.setHoraInicial(rs.getString(posicion++));
				act.setHoraFinal(rs.getString(posicion++));
				act.setLugar(rs.getString(posicion++));
				act.setParticipantes(rs.getString(posicion++));
				act.setUsuario(rs.getString(posicion++));
				act.setEstado(rs.getInt(posicion++));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return act;
	}

}
