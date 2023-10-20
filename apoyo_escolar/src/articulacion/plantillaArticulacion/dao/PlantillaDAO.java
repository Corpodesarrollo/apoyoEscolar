package articulacion.plantillaArticulacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.plantillaArticulacion.vo.ListaGrupoVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.plantillaArticulacion.vo.DatosVO;
import articulacion.plantillaArticulacion.vo.EspecialidadesVO;
import articulacion.plantillaArticulacion.vo.EstudianteVO;
import articulacion.plantillaArticulacion.vo.JerarquiaVO;

public class PlantillaDAO extends Dao {

	private ResourceBundle rb;

	public PlantillaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("articulacion.plantillaArticulacion.bundle.sentencias");
	}

	public List getAjaxGrupo(long inst, int sede, int jornada, int grado, String metodologia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ListaGrupoVO lp = null;

		int i = 0;
		try {
			if (inst == 0 || sede == 0 || jornada == 0 || grado == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxGrupo"));
			i = 1;
			st.setLong(i++, inst);
			st.setLong(i++, sede);
			st.setLong(i++, jornada);
			st.setLong(i++, grado);
			st.setLong(i++, Long.parseLong(metodologia));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ListaGrupoVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lpA;
	}

	public List getEstudiantes(String inst, long sede, long jornada, long grado, long grupo, String met) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lpA = new ArrayList();
		EstudianteVO lp = null;
		long jerar=0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("jerarquia"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));
			st.setLong(i++, (sede));
			st.setLong(i++, (jornada));
			st.setInt(i++, Integer.parseInt(met));
			st.setLong(i++, (grado));
			st.setLong(i++, (grupo));
			rs = st.executeQuery();
			if(rs.next()) {
				jerar=rs.getLong(1);
			}
			rs.close();
			st.close();
			// System.out.println("Codigo Jerarquia "+jerarquiaVO.getCodigo());
			st = cn.prepareStatement(rb.getString("getEstudiantes"));
			i = 1;
			st.setLong(i++, jerar);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new EstudianteVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setAbreviatura(rs.getString(i++));
				lp.setAbreviaturaCodigo(rs.getLong(i++));
				lp.setDocumento(rs.getString(i++));
				lp.setApellidos1(rs.getString(i++));
				lp.setApellidos2(rs.getString(i++));
				lp.setNombre1(rs.getString(i++));
				lp.setNombre2(rs.getString(i++));
				lp.setGrupo(rs.getInt(i++));
				lp.setEspecialidad(rs.getString(i++));
				lp.setSemestre(rs.getString(i++));
				lp.setNivelado(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) { }
		}
		return lpA;
	}

	public DatosVO getSedeJornada(String inst, long sede, long jornada, long grado, long grupo, String met) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosVO datosVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("sedeJornada"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));
			st.setLong(i++, (sede));
			st.setLong(i++, (jornada));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				datosVO = new DatosVO();
				datosVO.setSede_(rs.getString(i++));
				datosVO.setJornada_(rs.getString(i++));
				datosVO.setFecha(rs.getString(i++));
			}
			datosVO.setGrupo_(getGrupo(inst, sede, jornada, grupo, grado, met, cn));
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return datosVO;

	}

	public String getGrupo(String inst, long sede, long jornada, long grupo, long grado, String met, Connection cn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosVO datosVO = null;
		int i = 0;
		try {
			st = cn.prepareStatement(rb.getString("getGrupo"));
			i = 1;
			st.setLong(i++, (grupo));
			st.setInt(i++, Integer.parseInt(inst));
			st.setLong(i++, (sede));
			st.setLong(i++, (jornada));
			st.setInt(i++, Integer.parseInt(met));
			st.setLong(i++, (grado));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				datosVO = new DatosVO();
				datosVO.setGrupo_(rs.getString(i++));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return datosVO.getGrupo_();
	}

	public List getEspecialidades(String inst) {
		// System.out.println("llego a get especialidades");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lEspecialidades = new ArrayList();
		EspecialidadesVO especialidadesVO = null;
		int i = 0;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("especialidades"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				// System.out.println("hizo la consulta");
				especialidadesVO = new EspecialidadesVO();
				especialidadesVO.setCodigo(rs.getInt(i++));
				especialidadesVO.setEspecialidad(rs.getString(i++));
				lEspecialidades.add(especialidadesVO);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lEspecialidades;
	}

	public void setReporte(String us, String rec, String tipo, String nombre, int modulo) {
		Connection cn = null;
		PreparedStatement ps = null;
		// System.out.println("inserto en repnrtes");
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ReporteInsertar"));
			int posicion = 1;
			ps.setString(posicion++, us);
			ps.setString(posicion++, rec);
			ps.setString(posicion++, tipo);
			ps.setString(posicion++, nombre);
			ps.setInt(posicion++, modulo);
			ps.executeUpdate();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar reporte. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return;
	}

}
