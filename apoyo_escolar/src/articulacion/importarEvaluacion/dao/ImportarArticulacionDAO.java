package articulacion.importarEvaluacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import articulacion.importarEvaluacion.objetos.LimitesVO;
import articulacion.importarEvaluacion.objetos.RangosVO;
import siges.exceptions.InternalErrorException;
import articulacion.importarEvaluacion.objetos.CodigoEstudianteVO;
import articulacion.importarEvaluacion.objetos.DatosVO;
import articulacion.importarEvaluacion.objetos.EliminadoVO;
import articulacion.importarEvaluacion.objetos.EstudianteVO;
import articulacion.importarEvaluacion.objetos.ResultadoVO;
import articulacion.importarEvaluacion.objetos.VaciosVO;
import articulacion.importarEvaluacion.objetos.EspecialidadesVO;
import articulacion.plantillaArticulacion.vo.JornadaVO;

public class ImportarArticulacionDAO extends Dao {

	public ImportarArticulacionDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("articulacion.importarEvaluacion.bundle.sentencias");
	}

	public List getAntiguos(DatosVO filter, String metodologia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		// JornadaVO jornadaVO=null;
		CodigoEstudianteVO codigoVO = null;
		int i = 0;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAntiguos"));
			i = 1;
			st.setInt(i++, Integer.parseInt(filter.getInstitucion()));
			st.setInt(i++, Integer.parseInt(metodologia));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, getPeriodoNumerico());
			st.setInt(i++, Integer.parseInt(filter.getAsignatura()));
			st.setInt(i++, Integer.parseInt(filter.getPrueba()));

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				codigoVO = new CodigoEstudianteVO();
				codigoVO.setCodigo(rs.getLong(i++));
				codigoVO.setNotaNumerica(rs.getDouble(i++));
				codigoVO.setNotaConceptual(rs.getString(i++));

				lista.add(codigoVO);
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
		return lista;
	}

	public ResultadoVO importarEstudiante(DatosVO filtro, List l, String metodologia) {
		ResultadoVO resultadoVO = new ResultadoVO();
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		//List antiguos;
		EstudianteVO estudianteVO = null;
		//antiguos = getAntiguos(filtro, metodologia);
		int n=0;//cantidad de eliminados
		int m=0;//cantidad de ingresados
		float notaMax=0;
		try {
			cn = cursor.getConnection();
			//obtener el MAX
			pst = cn.prepareStatement(rb.getString("getMaximaNota"));
			rs=pst.executeQuery();
			if(rs.next()){
				notaMax=rs.getFloat(1);
			}
			rs.close();
			pst.close();
			//borrar todo lo de ese grupo-asig-bimestre
			pst = cn.prepareStatement(rb.getString("eliminacionTotal"));
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(filtro.getAsignatura()));
			pst.setLong(posicion++, Long.parseLong(filtro.getPrueba()));
			pst.setLong(posicion++, filtro.getBimestre());
			n=pst.executeUpdate();
			pst.close();
			if (l != null && l.size() > 0) {
				/*YA NO SE USA PORQUE SE BORRA LA ASIGNACION DE TODOS.
				for (int i = 0; i < l.size(); i++) {
					estudianteVO = (EstudianteVO) l.get(i);
					pst = cn.prepareStatement(rb.getString("eliminacion"));
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(filtro.getAsignatura()));
					pst.setLong(posicion++, Long.parseLong(filtro.getPrueba()));
					pst.setLong(posicion++, filtro.getBimestre());
					pst.setLong(posicion++, estudianteVO.getCodigo());
					n+=pst.executeUpdate();
					pst.close();
				}
				*/
				for (int i = 0; i < l.size(); i++) {
					estudianteVO = (EstudianteVO) l.get(i);
					pst = cn.prepareStatement(rb.getString("insercion"));
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(filtro.getAsignatura()));
					pst.setLong(posicion++, Long.parseLong(filtro.getPrueba()));
					pst.setLong(posicion++, filtro.getBimestre());
					pst.setLong(posicion++, estudianteVO.getCodigo());
					pst.setDouble(posicion++, estudianteVO.getNotanum0());
					pst.setDouble(posicion++, estudianteVO.getNotanum1());
					pst.setDouble(posicion++, estudianteVO.getNotanum2());
					pst.setDouble(posicion++, estudianteVO.getNotanum3());
					pst.setDouble(posicion++, estudianteVO.getNotanum4());
					pst.setDouble(posicion++, (estudianteVO.getNotanum()*notaMax)/100);
					pst.setString(posicion++, estudianteVO.getNotaC());
					m+=pst.executeUpdate();
					pst.close();
				}
			}
			if(m>=n){
				resultadoVO.setIngresados(m-n);
				resultadoVO.setActualizados(n);
				resultadoVO.setEliminados(((n-m)<0?0:(n-m)));
			}else{
				resultadoVO.setIngresados(((m-n)<0?0:(m-n)));
				resultadoVO.setActualizados(m);
				resultadoVO.setEliminados(n-m);
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar la evaluaciones a los estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar las evaluaciones a los estudiantes. Posible problema: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {}
		}
		return resultadoVO;
	}

	public LimitesVO getLimites() {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LimitesVO limitesVO = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getLimites"));
			rs = ps.executeQuery();
			while (rs.next()) {
				i = 1;
				limitesVO = new LimitesVO();
				limitesVO.setLimitA(rs.getInt(i++));
				limitesVO.setLimitB(rs.getInt(i++));
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
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return limitesVO;
	}

	public List getListaRangos(DatosVO filtroEncabezado) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List l = new ArrayList();
		RangosVO rangosVO = null;
		int i = 1;
		try {
			if (filtroEncabezado != null) {
				cn = cursor.getConnection();
				int ano = filtroEncabezado.getAnVigencia();
				int periodo = filtroEncabezado.getPerVigencia();
				long metodologia = Integer.parseInt(filtroEncabezado.getMetodologia());
				ps = cn.prepareStatement(rb.getString("getRangos"));
				ps.setLong(i++, Long.parseLong(filtroEncabezado.getInstitucion()));
				ps.setLong(i++, metodologia);
				ps.setInt(i++, ano);
				ps.setInt(i++, periodo);
				rs = ps.executeQuery();
				while (rs.next()) {
					i = 1;
					rangosVO = new RangosVO();
					rangosVO.setConceptual(rs.getString(i++));
					rangosVO.setInicio(rs.getFloat(i++));
					rangosVO.setFin(rs.getFloat(i++));
					l.add(rangosVO);
				}
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
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;

	}

	public boolean evaluarCierre(long codigoEstudiante) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List l = new ArrayList();
		RangosVO rangosVO = null;
		boolean cierre = true;
		int i = 1;
		int cir = 0;
		try {
			cn = cursor.getConnection();
			if (codigoEstudiante != 0) {
				ps = cn.prepareStatement(rb.getString("getCierre"));
				ps.setLong(i++, codigoEstudiante);

				// ps.setInt(i++,filtroEncabezado.getAnVigencia());
				// ps.setInt(i++,filtroEncabezado.getPerVigencia());

				rs = ps.executeQuery();
				while (rs.next()) {
					i = 1;
					rangosVO = new RangosVO();
					cir = rs.getInt(i++);

					if (cir == 0)
						cierre = true;
					else
						cierre = false;
				}

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
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return cierre;

	}

}
