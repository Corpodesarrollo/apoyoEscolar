package siges.evaluacion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import siges.boletines.beans.AlarmaBean;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.evaluacion.beans.EvaluacionEstudiante;
import siges.evaluacion.beans.FiltroBeanEvaluacion;
import siges.evaluacion.beans.FiltroComportamiento;
import siges.evaluacion.beans.NivelEvalVO;
import siges.evaluacion.beans.ParamsVO;
import siges.evaluacion.beans.TipoEvalVO;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;

/**
 * @author USUARIO
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class EvaluacionDAO extends Dao {
	public String sentencia;

	public String buscar;

	private ResourceBundle rb2;
	private ResourceBundle rb;

	private java.text.SimpleDateFormat sdf;

	public EvaluacionDAO(Cursor cur) {
		super(cur);
		sentencia = null;
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("siges.evaluacion.bundle.evaluacion");
		rb2 = ResourceBundle.getBundle("siges.observacion.bundle.observacion");
	}
	
	public FiltroBeanEvaluacion getCodigoJerarquiaGrupo(FiltroBeanEvaluacion filtro){
		List l=new ArrayList();
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		EvaluacionEstudiante estudiante=null;
		int i=0;
		try{
			//System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn=cursor.getConnection();
			//calcular el codigo de jerarquia
			st=cn.prepareStatement(rb2.getString("getJerarquiaGrupo"));
			i=1;
			st.setLong(i++,Long.parseLong(filtro.getInstitucion()));
			st.setInt(i++,Integer.parseInt(filtro.getSede()));
			st.setInt(i++,Integer.parseInt(filtro.getJornada()));
			st.setInt(i++,Integer.parseInt(filtro.getMetodologia()));
			st.setInt(i++,Integer.parseInt(filtro.getGrado()));
			st.setInt(i++,Integer.parseInt(filtro.getGrupo().substring(filtro.getGrupo().lastIndexOf("|") + 1,filtro.getGrupo().length())));
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setJerarquiagrupo(""+rs.getLong(3));
			}else{
				//System.out.println("Jerarquia no encontrada");
				throw new Exception("Jerarquia no encontrada");
			}
			rs.close();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return filtro;
	}
	
	public boolean getDocenteEnGrupo(FiltroBeanEvaluacion filtro, long asignatura, Connection cn){
		List l=new ArrayList();
		//Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		EvaluacionEstudiante estudiante=null;
		int i=0;
		int conteo =0;
		try{
			//cn=cursor.getConnection();
			//calcular el codigo de jerarquia
			st=cn.prepareStatement(rb.getString("evaluacion.docenteEnGrupo"));
			i=1;
			st.setLong(i++,filtro.getFilDocente());
			st.setLong(i++,Long.parseLong(filtro.getAsignatura().substring(filtro.getAsignatura().lastIndexOf('|') + 1,filtro.getAsignatura().length())));
			st.setLong(i++,Long.parseLong(filtro.getInstitucion()));
			st.setInt(i++,Integer.parseInt(filtro.getSede()));
			st.setInt(i++,Integer.parseInt(filtro.getJornada()));
			st.setInt(i++,Integer.parseInt(filtro.getGrado()));
			st.setInt(i++,Integer.parseInt(filtro.getGrupo().substring(filtro.getGrupo().lastIndexOf('|') + 1,filtro.getGrupo().length())));
			st.setInt(i++,Integer.parseInt(filtro.getMetodologia()));
			st.setInt(i++,Integer.parseInt(filtro.getVigencia()));
			rs=st.executeQuery();
			
			if(rs.next()){
				conteo = rs.getInt(1);
			}else{
				//System.out.println("Jerarquia no encontrada");
				throw new Exception("No hay conteo");
			}
			rs.close();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				
			}catch(InternalErrorException inte){}
		}
		if(conteo > 0){
			return true;
		}else{
			return false;
		}
		
	}

	public Collection getNotasDesc(FiltroBeanEvaluacion f) {
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaArea"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			if (jer == 0)
				return null;
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0) {
				ps = cn.prepareStatement(rb
						.getString("listaDescriptorNotaEstudiante2"));
			} else {
				ps = cn.prepareStatement(rb
						.getString("listaDescriptorNotaEstudiante2.docente"));
			}
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getDescriptor()));
			ps.setLong(posicion++, vigencia);
			if (f.getFilPlanEstudios() == 1) {
				ps.setLong(posicion++, f.getFilDocente());
			}
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
			setMensaje("Error intentando obtener notas de Desc. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public String getEstadoNotasAsig(FiltroBeanEvaluacion f) {
		int tipo = 2;
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return "0";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	public String getEstadoNotasArea(FiltroBeanEvaluacion f) {
		int tipo = 1;
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return "0";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	public String getEstadoNotasPree(FiltroBeanEvaluacion f) {
		int tipo = 1;
		String area = f.getArea();
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return "0";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Insertar Evaluacinn Asignatura <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public Collection getDescriptor(FiltroBeanEvaluacion f) {
		String area = f.getArea().substring(f.getArea().lastIndexOf("|") + 1,
				f.getArea().length());
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("filtroDescriptor3.0"));
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0)
				ps = cn.prepareStatement(rb.getString("filtroDescriptor3"));
			else
				ps = cn.prepareStatement(rb
						.getString("filtroDescriptor3.docente"));
			posicion = 1;
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getDescriptor()));
			ps.setLong(posicion++, vigencia);
			if (f.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, f.getFilDocente());
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener descriptores. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	/**
	 * Funcinn: Insertar Evaluacinn Asignatura <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public Collection getEstudiantes(FiltroBeanEvaluacion f) {
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		// String jer = "";
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaEstudianteLogro"
					+ (Integer.parseInt(f.getOrden()) + 1)));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	/**
	 * Funcinn: Insertar Evaluacinn Asignatura <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public Collection getLogro(FiltroBeanEvaluacion f) {
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf("|") + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaLogroAsignatura.0"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0) {
				ps = cn.prepareStatement(rb.getString("listaLogroAsignatura"));
			} else {
				ps = cn.prepareStatement(rb
						.getString("listaLogroAsignatura.docente"));
			}

			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, vigencia);
			if (f.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, f.getFilDocente());
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener logros. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getNotasPree(FiltroBeanEvaluacion f) {
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		String jer = null;
		Collection list = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("EvaluacionPreescolar.notas"
					+ f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(f.getArea()));
			ps.setLong(posicion++, vigencia);
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
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getNotasLogro(FiltroBeanEvaluacion f) {
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf("|") + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaAsignatura"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			if (jer == 0)
				return null;
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0) {
				ps = cn.prepareStatement(rb
						.getString("listaLogroNotaEstudiante"));
			} else {
				ps = cn.prepareStatement(rb
						.getString("listaLogroNotaEstudiante.docente"));
			}
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, vigencia);
			if (f.getFilPlanEstudios() == 1) {
				ps.setLong(posicion++, f.getFilDocente());
			}
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
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getNotasRecLogro(FiltroBeanEvaluacion f) {
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf("|") + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaAsignatura"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			if (jer == 0)
				return null;
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0)
				ps = cn.prepareStatement(rb
						.getString("listaLogroNotaEstudiante2"));
			else
				ps = cn.prepareStatement(rb
						.getString("listaLogroNotaEstudiante2.docente"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, vigencia);
			if (f.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, f.getFilDocente());
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
			setMensaje("Error intentando obtener notas de Rec Logro. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	/**
	 * Funcinn: Insertar Evaluacinn Asignatura <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public Collection getEscala(int n) {
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		String jer = "";
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaEscala"));
			ps.setInt(1, n);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener escala de AREA/ASIG. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	/**
	 * Funcinn: Retorna el codigo de jerarquia de <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public String getJerarquiaGrupo(Collection c) {
		if (!c.isEmpty()) {
			Iterator iterator = c.iterator();
			Object[] o;
			if (iterator.hasNext()) {
				o = (Object[]) iterator.next();
				return ((String) (o[6] != null ? o[6] : ""));
			}
		}
		return "";
	}

	/**
	 * Funcinn: Insertar Evaluacinn de logro <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public boolean insertarEvalLogro(FiltroBeanEvaluacion f) {
		int posicion = 1;
		// System.out.println("EVALUACION: ENTRO INSERTAR LOGRO");
		Connection cn = null;
		// PreparedStatement pst = null, pst2 = null, pst3 = null;
		PreparedStatement ps = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			int x, y, z;
			String a[] = f.getNota();
			// System.out.println("a= lista total de
			// notas="+(a!=null?a.length:0)+"");
			String b[] = f.getActualizar();// lista de registros que ya estan
			// System.out.println("b= lista de registros que ya
			// estan"+(b!=null?b.length:0)+"");
			String c[] = new String[b != null ? b.length : 1];
			// System.out.println("c= lista de notas a actualizar(no
			// borrar)"+(c!=null?c.length:0)+"");
			String m[] = new String[4];
			int cont = 0;
			int ins = 0;
			// /
			// System.out.println("-//-//-//-");
			// for(int i=0;i<a.length;i++){
			// System.out.println(" ,a["+i+"]="+a[i]);
			// }
			// System.out.println("-//-//-//-");
			// for(int i=0;i<(b!=null?b.length:0);i++){
			// System.out.println(" ,b["+i+"]="+b[i]);
			// }
			// System.out.println("-//-//-//-");
			// /
			if (a != null) {
				// System.out.println("EVAL: LOGRO NOTA NO ES NULL");
				for (int i = 0; i < a.length; i++) {
					ins = 0;
					m = a[i].replace('|', ':').split(":");
					if (!m[0].equals("-1")) {
						// System.out.println("EVALLOGRO ,a["+i+"]="+a[i]);
						ps = cn.prepareStatement(rb
								.getString("EvaluacionLogroInsertar"));
						if (b != null) {
							x = a[i].lastIndexOf("|");
							for (int j = 0; j < b.length; j++) {
								y = b[j].lastIndexOf("|");
								// System.out.print("EVALLOGRO @,b["+j+"]="+b[j]);
								if (x != -1 && y != -1) {
									if (a[i].substring(0, x).equals(
											b[j].substring(0, y))) {
										c[cont++] = a[i].substring(0,
												a[i].lastIndexOf("|"));
										if (!a[i].substring(x, a[i].length())
												.equals(b[j].substring(y,
														b[j].length()))) {
											// System.out.println("EVALLOGRO: ACTUALIZAR ");
											if (ps != null)
												ps.close();
											ps = cn.prepareStatement(rb
													.getString("EvaluacionLogroActualizar"));
											ins = 1;
										} else {
											// System.out.println(" LO DEJA COMO ESTA ");
											ps = null;
										}
										break;
									}
								}
							}
						}
						if (ps != null) {
							// if(ins==0 && !m[0].equals("-1"))
							// System.out.println("EVALLOGRO: INSERTAR ");
							ps.clearParameters();
							posicion = 1;
							ps.setLong(posicion++, Long.parseLong(m[3].trim()));
							ps.setLong(posicion++, Long.parseLong(m[2].trim()));
							ps.setLong(posicion++, Long.parseLong(m[0].trim()));
							ps.setLong(posicion++, Long.parseLong(m[1].trim()));
							ps.setLong(posicion++,
									Long.parseLong(f.getPeriodo().trim()));
							ps.setLong(posicion++, vigencia);
							int r = ps.executeUpdate();
							ps.close();

							// System.out.println((ins==0?"insertando":"actualizando")+"=
							// CANTIDAD ="+r);
						}
					}
				}
				if (b != null) {
					// System.out.println("EVAL LOGRO: --REVISAR ELIMINADOS");
					String str;
					int band = -1;
					for (int i = 0; i < b.length; i++) {
						band = 1;
						// System.out.println("\n
						// b["+i+"]="+b[i].substring(0,b[i].lastIndexOf("|")));
						for (int j = 0; j < (c != null ? c.length : 0); j++) {
							if (c[j] != null) {
								// System.out.print(" ,c["+j+"]="+c[j]);
								if (c[j].equals(b[i].substring(0,
										b[i].lastIndexOf("|")))) {
									band = 2;
									break;
								}
							}
						}
						if (band == 1) {
							m = b[i].replace('|', ':').split(":");
							// System.out.println("EVAL LOGRO: ELIMINAR="+b[i]);
							ps = cn.prepareStatement(rb
									.getString("EvaluacionLogroEliminar"));
							ps.clearParameters();
							posicion = 1;
							ps.setLong(posicion++, Long.parseLong(m[2].trim()));
							ps.setLong(posicion++, Long.parseLong(m[0].trim()));
							ps.setLong(posicion++, Long.parseLong(m[1].trim()));
							ps.setLong(posicion++,
									Long.parseLong(f.getPeriodo().trim()));
							ps.setLong(posicion++, vigencia);
							int r = ps.executeUpdate();
							ps.close();

							// System.out.print(" _ELIMINADO= CANTIDAD"+r);
						}
					}
				}
			}
			// System.out.println("\n----------------");
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
			setMensaje("Error intentando ingresar logros a estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Insertar Evaluacinn Asignatura <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public boolean insertarEvalAsignatura(FiltroBeanEvaluacion f,
			TipoEvalVO tipoEval, long nivelEval) {
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		long vigencia;
		Connection cn = null;
		PreparedStatement st = null, st2 = null, st3 = null, st4 = null;
		ResultSet rs = null;
		int i, j;
		long valores[] = null;
		String param[] = null;
		long codigo = 0;
		double nota = 0;
		int ausencia = -99;
		String sql = "", sql2 = "";
		try {
			// System.out.println("EVAL ASIG: ENTRO A INSERTAR DAO");
			vigencia = Long.parseLong(getVigencia());
			long jerarAsig = Long.parseLong(f.getJerarquiaAreaAsig());
			// System.out.println("EVAL ASIG: JERAR: "+jerarAsig);
			long jerarGrupo = Long.parseLong(f.getJerarquiagrupo());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			String a[] = f.getNota();
			String aus[] = f.getAusjus();
			int indice = 0;
			if (a != null) {
				// System.out.println("EVAL ASIG: A NO ES NULO");
				valores = new long[a.length];
				st = cn.prepareStatement(rb
						.getString("plantilla.insertarEvaluacionAsignatura"
								+ f.getPeriodo()));
				st2 = cn.prepareStatement(rb
						.getString("plantilla.actualizarEvaluacionAsignatura"
								+ f.getPeriodo()));
				st3 = cn.prepareStatement(rb
						.getString("plantilla.insertarEvaluacionAsignaturaNull"
								+ f.getPeriodo()));
				st4 = cn.prepareStatement(rb
						.getString("plantilla.actualizarEvaluacionAsignaturaNull"
								+ f.getPeriodo()));
				st.clearBatch();
				st2.clearBatch();
				st3.clearBatch();
				st4.clearBatch();
				for (j = 0; j < a.length; j++) {
					// System.out.println("EVAL ASIG: FOR A");
					i = 1;
					param = a[j].replace('|', ':').split(":");
					if (param != null && param.length > 1) {
						// System.out.println("EVAL ASIG: PARAM VIENE");
						codigo = Long.parseLong(param[0]);
						nota = Double.parseDouble(param[1]);
						/*
						 * if(param.length>2)
						 * System.out.println("EVAL ASIG: NOTA: "
						 * +nota+"  RECUPERACION: "+param[2]); else
						 * System.out.println
						 * ("EVAL ASIG: NOTA: "+nota+"  RECUPERACION: NO VIENE"
						 * );
						 */
						// ps.setNull(posicion++, java.sql.Types.VARCHAR);
						if (aus != null && aus[j] != null && !aus[j].equals("")) {
							ausencia = Integer.parseInt(aus[j]);
						} else {
							ausencia = -99;
						}
						// System.out.println("Valor de AUS: "+aus[j]+"//"+ausencia);
						valores[indice++] = codigo;
						sql2 += "?,";
						if (!existeEstudianteAsig(cn, jerarAsig, codigo)) {// ingresar
							if (ausencia != -99) {
								st.setLong(i++, jerarAsig);
								st.setLong(i++, codigo);
								st.setLong(i++, vigencia);
								st.setDouble(i++, nota);
								st.setInt(i++, ausencia);
								if (param.length > 2)
									st.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st.setNull(i++, java.sql.Types.DOUBLE);
								st.addBatch();
							} else {
								st3.setLong(i++, jerarAsig);
								st3.setLong(i++, codigo);
								st3.setLong(i++, vigencia);
								st3.setDouble(i++, nota);
								if (param.length > 2)
									st3.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st3.setNull(i++, java.sql.Types.DOUBLE);
								st3.addBatch();
							}
						} else {// actualizar
							if (ausencia != -99) {
								st2.setDouble(i++, nota);
								st2.setInt(i++, ausencia);
								if (param.length > 2)
									st2.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st2.setNull(i++, java.sql.Types.DOUBLE);
								st2.setLong(i++, jerarAsig);
								st2.setLong(i++, codigo);
								st2.setLong(i++, vigencia);
								st2.addBatch();
							} else {
								st4.setDouble(i++, nota);
								if (param.length > 2)
									st4.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st4.setNull(i++, java.sql.Types.DOUBLE);
								st4.setLong(i++, jerarAsig);
								st4.setLong(i++, codigo);
								st4.setLong(i++, vigencia);
								st4.addBatch();
							}
						}
					}
					// else
					// System.out
					// .println("EVAL ASIG: PARAM NO VIENE ***************************");
				}
				st.executeBatch();
				st2.executeBatch();
				st3.executeBatch();
				st4.executeBatch();
				st.close();
				st2.close();
				st3.close();
				st4.close();
				if (sql2.length() > 0) {
					sql2 = sql2.substring(0, sql2.length() - 1);
					sql = rb.getString("getCountEstudianteAsignatura.1."
							+ f.getPeriodo())
							+ sql2
							+ rb.getString("getCountEstudianteAsignatura.2");
				} else {
					sql = rb.getString("getCountEstudianteAsignatura.3."
							+ f.getPeriodo());
				}
				st = cn.prepareStatement(sql);
				i = 1;
				st.setLong(i++, jerarGrupo);
				st.setLong(i++, jerarAsig);
				st.setLong(i++, vigencia);
				for (j = 0; j < indice; j++) {
					st.setLong(i++, valores[j]);
				}
				rs = st.executeQuery();
				while (rs.next()) {
					i = 1;
					if (rs.getInt(2) == 0) {// se puede eliminar todo
						st2 = cn.prepareStatement(rb
								.getString("plantilla.eliminarEvaluacionAsignatura"));
						st2.setLong(i++, jerarAsig);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vigencia);
						st2.executeUpdate();
						st2.close();
					} else {// se actualiza a null porque hay mas de un registro
							// alli
						st2 = cn.prepareStatement(rb
								.getString("plantilla.eliminarEvaluacionAsignaturaNull"
										+ f.getPeriodo()));
						st2.setLong(i++, jerarAsig);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vigencia);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
			actualizarCierreAsignatura(cn, f, asig, tipoEval, nivelEval);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando evaluar por asigantura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeStatement(st3);
				OperacionesGenerales.closeStatement(st4);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: insertarEvalDescriptor <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public boolean insertarEvalDescriptor(FiltroBeanEvaluacion f) {
		int posicion = 1;
		String area = f.getArea().substring(f.getArea().lastIndexOf("|") + 1,
				f.getArea().length());
		Connection cn = null;
		PreparedStatement ps = null;
		long jer = 0;
		ResultSet rs = null;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// int x, y, z;
			ps = cn.prepareStatement(rb.getString("jerarquiaArea"));
			posicion = 1;
			ps.setInt(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next())
				jer = rs.getLong(1);
			rs.close();
			ps.close();
			String a[] = f.getNota();
			String b[] = f.getActualizar();// lista de registros que ya estan
			// System.out.println("b= lista de registros que ya
			// estan"+(b!=null?b.length:0)+"");
			String c[] = new String[b != null ? b.length : 1];
			// System.out.println("c= lista de notas a actualizar(no
			// borrar)"+(c!=null?c.length:0)+"");
			int cont = 0;
			int ins = 0;
			int i, j, k, l;
			boolean insertar = true;
			String desc[];
			String desc0[];
			String est;
			String[] temp;
			for (i = 0; i < (a != null ? a.length : 0); i++) {
				ins = 0;
				desc0 = desc = temp = null;
				temp = a[i].replace('|', ':').split(":");
				est = temp[0];
				if (temp.length > 1)
					desc = temp[1].split(",");
				// System.out.println("estudisnte "+est);
				for (j = 0; j < (b != null ? b.length : 0); j++) {
					if (est.equals(b[j].substring(0, b[j].lastIndexOf("|")))) {
						temp = b[j].replace('|', ':').split(":");
						if (temp.length > 1)
							desc0 = temp[1].split(",");
						// iterar desde desc para encontrar los nuevos e
						// insertarlos
						for (k = 0; k < (desc != null ? desc.length : 0); k++) {
							insertar = true;
							for (l = 0; l < (desc0 != null ? desc0.length : 0); l++) {
								if (desc[k].equals(desc0[l])) {
									insertar = false;
									break;
								}
							}
							if (insertar) {
								// System.out.println("insertar "+desc[k]);
								// System.out.println("insertar -" + desc[k] +
								// "-"+ f.getDescriptor() + "-" + jer + "-"+ est
								// + "-" + f.getPeriodo());
								ps = cn.prepareStatement(rb
										.getString("EvaluacionDescriptorInsertar"));
								ps.clearParameters();
								posicion = 1;
								ps.setLong(posicion++,
										Long.parseLong(desc[k].trim()));
								ps.setInt(posicion++, Integer.parseInt(f
										.getDescriptor().trim()));
								ps.setLong(posicion++, jer);
								ps.setLong(posicion++,
										Long.parseLong(est.trim()));
								ps.setLong(posicion++,
										Long.parseLong(f.getPeriodo().trim()));
								ps.setLong(posicion++, vigencia);
								int r = ps.executeUpdate();
								ps.close();

								// System.out.println("insertando CANTIDAD ="+r);
							}
						}
						// iterar desde desc0 para encontrar los quitados y
						// eliminarlos
						for (k = 0; k < (desc0 != null ? desc0.length : 0); k++) {
							insertar = true;
							for (l = 0; l < (desc != null ? desc.length : 0); l++) {
								if (desc0[k].equals(desc[l])) {
									insertar = false;
									break;
								}
							}
							if (insertar) {
								// System.out.println("Eliminar "+desc0[k]);
								ps = cn.prepareStatement(rb
										.getString("EvaluacionDescriptorEliminar"));
								ps.clearParameters();
								posicion = 1;
								ps.setLong(posicion++, jer);
								ps.setLong(posicion++,
										Long.parseLong(est.trim()));
								ps.setLong(posicion++,
										Long.parseLong(f.getPeriodo().trim()));
								ps.setLong(posicion++,
										Long.parseLong(desc0[k].trim()));
								ps.setLong(posicion++, vigencia);
								int r = ps.executeUpdate();
								ps.close();

								// System.out.println("Eliminando CANTIDAD ="+r);
							}
						}
					}
				}
			}
			// System.out.println("\n----------------");
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar descriptores a estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Insertar Evaluacinn de logro <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public boolean insertarEvalRecuperacion(FiltroBeanEvaluacion f) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		boolean act = true;
		try {
			long vigencia = Long.parseLong(getVigencia());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			int x, y, z;
			String a[] = f.getNota();
			// System.out.println("a= lista total de
			// notas="+(a!=null?a.length:0)+"");
			String b[] = f.getActualizar();// lista de registros que ya estan
			// System.out.println("b= lista de registros que ya
			// estan"+(b!=null?b.length:0)+"");
			String c[] = new String[b != null ? b.length : 1];
			// System.out.println("c= lista de notas a actualizar(no
			// borrar)"+(c!=null?c.length:0)+"");
			String m[] = new String[4];
			int cont = 0;
			int ins = 0;
			// /
			/*
			 * System.out.println("-//-//-//-"); for(int i=0;i <a.length;i++){
			 * System.out.println(" ,a["+i+"]="+a[i]); }
			 * System.out.println("-//-//-//-"); for(int
			 * i=0;i<(b!=null?b.length:0);i++){
			 * System.out.println(",b["+i+"]="+b[i]); }
			 * System.out.println("-//-//-//-");
			 */
			// /
			if (a != null) {
				for (int i = 0; i < a.length; i++) {
					ins = 0;
					m = a[i].replace('|', ':').split(":");
					if (!m[0].equals("-1")) {
						// System.out.println(" ,a["+i+"]="+a[i]);
						ps = null;
						act = true;
						if (b != null) {
							x = a[i].lastIndexOf("|");
							for (int j = 0; j < b.length; j++) {
								y = b[j].lastIndexOf("|");
								// System.out.print(" @,b["+j+"]="+b[j]);
								if (x != -1 && y != -1) {
									if (a[i].substring(0, x).equals(
											b[j].substring(0, y))) {
										c[cont++] = a[i].substring(0,
												a[i].lastIndexOf("|"));
										if (!a[i].substring(x, a[i].length())
												.equals(b[j].substring(y,
														b[j].length()))) {
											// System.out.println(" ACTUALIZAR Osea Recupera "+a[i].substring(x,a[i].length())+"..."+b[j].substring(y,b[j].length()));
											// System.out.println("a[i].substring(x,a[i].length())+"-
											// -"+"|-99-");
											// System.out.println("nota "+a[i].substring(x,a[i].length()));
											if (ps != null)
												ps.close();
											ps = cn.prepareStatement(rb
													.getString("EvaluacionLogroRecuperar"));
											ins = 1;
										} else {
											// System.out.println(" LO DEJA COMO ESTA ");
											if (ps != null)
												ps.close();
											ps = cn.prepareStatement(rb
													.getString("EvaluacionLogroRecuperar"));
											act = false;
										}
										break;
									}
								}
							}
						}
						if (ps != null) {
							// if(ins==0 && !m[0].equals("-1"))
							// System.out.println(" INSERTAR ");
							ps.clearParameters();
							posicion = 1;
							if (act) {
								if (f.getPeriodo2().equals(""))
									ps.setNull(posicion++,
											java.sql.Types.VARCHAR);
								else
									ps.setLong(posicion++, Long.parseLong(f
											.getPeriodo2().trim()));
							} else {
								ps.setNull(posicion++, java.sql.Types.VARCHAR);
							}
							ps.setLong(posicion++, Long.parseLong(m[2].trim()));
							ps.setLong(posicion++, Long.parseLong(m[0].trim()));
							ps.setLong(posicion++, Long.parseLong(m[1].trim()));
							ps.setLong(posicion++,
									Long.parseLong(f.getPeriodo().trim()));
							ps.setLong(posicion++, vigencia);
							int r = ps.executeUpdate();
							ps.close();

							// System.out.println((ins==0?"insertando":"actualizando")+"=CANTIDAD ="+r);
						}
					}
				}
			}
			// System.out.println("\n----------------");
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
			setMensaje("Error intentando ingresar Recuperacion a estudiantes. Posible problema: ");
			// System.out.println(": "+sqle);
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public String getEstadoHorarioAsig(FiltroBeanEvaluacion f, Login l) {
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			
			long nn;
			ps = cn.prepareStatement(rb.getString("EstadoLectura"));
			rs = ps.executeQuery();
			if (rs.next()){
				return "0";
			}
			rs.close();
			ps.close();
			
			
			// validar si metodologia es libre o no
			String libres = null;
			ps = cn.prepareStatement(rb.getString("HorarioLibre"));
			rs = ps.executeQuery();
			if (rs.next()) {
				libres = rs.getString(1);
			}
			rs.close();
			ps.close();
			if (libres != null) {
				if (libres.indexOf(",") != -1) {
					String[] lib = libres.split(",");
					if (lib != null) {
						for (int r = 0; r < lib.length; r++) {
							if (lib[r].equals(f.getMetodologia())) {
								return "0";
							}
						}
					}
				} else {
					if (libres.equals(f.getMetodologia())) {
						return "0";
					}
				}
			}
			// validacion de seguridad de horarios habilitada
			
			//
			// Permisos de Perfil
			ps = cn.prepareStatement(rb.getString("estadoPerfil"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getPerfil()));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			}
			rs.close();
			ps.close();
			
			// validacion contra la tabla
			ps = cn.prepareStatement(rb.getString("estadoLecturaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getInstId()));
			rs = ps.executeQuery();
			if (rs.next()) {
				//validacion de perfil docente dictando en grupo
				return "0";
			}else{
				if (Long.parseLong(l.getPerfil().trim()) == 422){// perfil docente ?
					
					if(f.getFilDocente() == 0){
						ps = cn.prepareStatement(rb.getString("numeroDocumentoUsuario"));
						ps.setString(1, l.getUsuarioId());
						rs = ps.executeQuery();
						if (rs.next()) {
							long idDocente = rs.getLong(1);
							f.setFilDocente(idDocente);
						}
						rs.close();
						ps.close();
					}
					if(getDocenteEnGrupo(f, Long.parseLong(asig), cn)){//docente dictando en grupo ? 
						return "0";
					}else{
						return "1";
					}
				}
			}
			rs.close();
			ps.close();
			// validacion contra horarios
			ps = cn.prepareStatement(rb.getString("horario"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setString(posicion++, asig);
			ps.setString(posicion++, f.getInstitucion());
//			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
//			ps.setLong(posicion++, Long.parseLong(asig));
//			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
//			ps.setLong(posicion++, Long.parseLong(asig));
//			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
//			ps.setLong(posicion++, Long.parseLong(asig));
//			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
//			ps.setLong(posicion++, Long.parseLong(asig));
//			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
//			ps.setLong(posicion++, Long.parseLong(asig));
//			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
//			ps.setLong(posicion++, Long.parseLong(asig));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			} else {
				return "1";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estado de Horario. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	public String getEstadoHorarioArea(FiltroBeanEvaluacion f, Login l) {
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			// validar si metodologia es libre o no
			String libres = null;
			ps = cn.prepareStatement(rb.getString("HorarioLibre"));
			rs = ps.executeQuery();
			if (rs.next()) {
				libres = rs.getString(1);
			}
			rs.close();
			ps.close();
			if (libres != null) {
				if (libres.indexOf(",") != -1) {
					String[] lib = libres.split(",");
					if (lib != null) {
						for (int r = 0; r < lib.length; r++) {
							if (lib[r].equals(f.getMetodologia())) {
								return "0";
							}
						}
					}
				} else {
					if (libres.equals(f.getMetodologia())) {
						return "0";
					}
				}
			}
			// validacion de seguridad de horarios habilitada
			long nn;
			ps = cn.prepareStatement(rb.getString("EstadoLectura"));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			}
			rs.close();
			ps.close();
			// Permisos de Perfil
			ps = cn.prepareStatement(rb.getString("estadoPerfil"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getPerfil()));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			}
			rs.close();
			ps.close();
			// validacion contra la tabla
			ps = cn.prepareStatement(rb.getString("estadoLecturaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getInstId()));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			}
			rs.close();
			ps.close();
			// validacion contra horarios
			ps = cn.prepareStatement(rb.getString("horario2"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			} else {
				// Validacion tipo de evaluacion cualitativa
				ps = cn.prepareStatement(rb.getString("validarTipoEvaluacionCualitativaYNumerica"));
				posicion = 1;
				ps.setLong(posicion++, Long.parseLong(f.getVigencia()));
				ps.setLong(posicion++, Long.parseLong(l.getInstId()));
				ps.setLong(posicion++, Long.parseLong(f.getGrado()));
				rs = ps.executeQuery();
				if(rs.next()) {
					return "0";
				}
				return "1";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estado horario area. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	public String getEstadoHorarioPree(FiltroBeanEvaluacion f, Login l) {
		String area = f.getArea();
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			// validar si metodologia es libre o no
			String libres = null;
			ps = cn.prepareStatement(rb.getString("HorarioLibre"));
			rs = ps.executeQuery();
			if (rs.next()) {
				libres = rs.getString(1);
			}
			rs.close();
			ps.close();
			if (libres != null) {
				if (libres.indexOf(",") != -1) {
					String[] lib = libres.split(",");
					if (lib != null) {
						for (int r = 0; r < lib.length; r++) {
							if (lib[r].equals(f.getMetodologia())) {
								return "0";
							}
						}
					}
				} else {
					if (libres.equals(f.getMetodologia())) {
						return "0";
					}
				}
			}
			// validacion de seguridad de horarios habilitada
			long nn;
			ps = cn.prepareStatement(rb.getString("EstadoLectura"));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			}
			rs.close();
			ps.close();
			//
			// Permisos de Perfil APLICA PARA LODE ERMISO 1 OSEA como ing de
			// soporte o coordinador de notas
			ps = cn.prepareStatement(rb.getString("estadoPerfil"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getPerfil()));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			}
			rs.close();
			ps.close();
			// validacion contra la tabla
			ps = cn.prepareStatement(rb.getString("estadoLecturaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getInstId()));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			}
			rs.close();
			ps.close();
			// /validacion como tal de horarios
			ps = cn.prepareStatement(rb.getString("horario2"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(l.getUsuarioId()));
			ps.setLong(posicion++, Long.parseLong(area));
			rs = ps.executeQuery();
			if (rs.next()) {
				return "0";
			} else {
				return "1";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estado horario area. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Insertar Evaluacinn Asignatura <br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public Collection getEstudiantesAsignatura(FiltroBeanEvaluacion f,
			long tipoEval) {
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long jer = 0;
		long jerAsig = 0;
		Collection list = null;
		try {
			list = new ArrayList();
			String dato[];
			long vig = getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getJerarquia18"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
				f.setJerarquiagrupo(rs.getString(1));
			}
			rs.close();
			ps.close();
			// System.out
			// .println("******************EVALUACION ASIG: JERARQUIA GRUPO "
			// + jer);
			ps = cn.prepareStatement(rb.getString("getJerarquia34"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			if (rs.next()) {
				jerAsig = rs.getLong(1);
				f.setJerarquiaAreaAsig(rs.getString(1));
			}
			rs.close();
			ps.close();

			// System.out
			// .println("******************EVALUACION ASIG: JERARQUIA ASIG "
			// + jerAsig + "     vigencia" + vig);
			// buscar estudiantes
			ps = cn.prepareStatement(rb.getString("listaEstudianteAsignatura."
					+ f.getPeriodo() + "." + f.getOrden()));
			posicion = 1;
			ps.setLong(posicion++, tipoEval);
			/*
			 * ps.setLong(posicion++, vig); ps.setLong(posicion++,
			 * Long.parseLong(f.getGrado()));
			 */
			ps.setLong(posicion++, tipoEval);
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, jerAsig);
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			while (rs.next()) {
				dato = new String[9];
				posicion = 0;
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				list.add(dato);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
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

	/**
	 * Permite concoer un area apatir d elos datos de una asignatura
	 * 
	 * @param public String getAreaxcodAsignatura(FiltroBeanEvaluacion f
	 * @param asigcode
	 * @return
	 * @throws SQLException
	 * @throws InternalErrorException
	 */
	public String getAreaxcodAsignatura(FiltroBeanEvaluacion f, String asigcode)
			throws SQLException, InternalErrorException {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement("select arenombre from asignatura,area where asicodinst=? and asivigencia=? and asicodigo=? and asicodmetod=? and asicodarea=arecodigo and asivigencia=arevigencia and arecodinst=asicodinst and arecodmetod=asicodmetod");
			int posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getVigencia()));
			ps.setLong(posicion++, Long.parseLong(asigcode));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			rs = ps.executeQuery();
			if (rs.next()) {

				return rs.getString(1);
			}
			return "Area N/A";
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			sqle.printStackTrace();
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
	}

	public ArrayList getDocenteInstJornSed(FiltroBeanEvaluacion f) {
		ArrayList list = new ArrayList();
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			cn = cursor.getConnection();
			posicion = 1;
			// se consultadoc num del docente
			ps = cn.prepareStatement(rb.getString("getDocenteInstJornSede"));
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));

			rs = ps.executeQuery();
			posicion = 1;

			String comtmp = "";
			while (rs.next()) {
				list.add(rs.getString(1));
			}

		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			sqle.printStackTrace();
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

	/**
	 * Se obtiene lista de documentos de docentes que dictan una asignatura de
	 * un grupo en un vigencia.
	 * 
	 * @param f
	 * @return lista docentes
	 */
	public ArrayList getDocenteDictaAsignatura(FiltroBeanEvaluacion f) {
		ArrayList list = new ArrayList();

		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());

		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// el id del grupo que se refleja en horgrupo de horario
		long jer = 0;

		try {
			String dato[];
			long vig = getVigenciaNumerico();

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getJerarquia18"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
				f.setJerarquiagrupo(rs.getString(1));
			}
			rs.close();
			ps.close();
			posicion = 1;
			// se consultadoc num del docente
			ps = cn.prepareStatement(rb
					.getString("getDecoentexhorariogrupovigencia"));
			// System.out.println(jer);

			ps.setLong(posicion++, jer);
			// System.out.println(vig);
			ps.setLong(posicion++, vig);
			// System.out.println(jer);
			ps.setLong(posicion++, Long.parseLong(asig));
			// System.out.println(Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(asig));

			rs = ps.executeQuery();
			posicion = 1;

			String comtmp = "";
			while (rs.next()) {
				dato = new String[6];
				// lista temporal para anadir docentes sin repetir de un mismo
				// horario

				posicion = 0;
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);

				for (int i = 0; i < dato.length; i++) {
					if (dato[i] != null) {
						if (!list.contains(dato[i])) {
							list.add(dato[i]);
							// System.out.println("Doc " + dato[i]);
						}
					}
				}

			}

		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			sqle.printStackTrace();
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

	/**
	 * Metodo que lista los coordinadores de un colegio y una sede especifico
	 * Perfil:coord academicos (421)
	 * 
	 * @param f
	 * @return
	 * @throws InternalErrorException
	 */
	public ArrayList getCoordinadores(Login l) throws InternalErrorException {
		ArrayList listdocumentos = new ArrayList();

		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// el id del grupo que se refleja en horgrupo de horario
		long jer = 0;

		try {
			posicion = 1;
			cn = cursor.getConnection();
			// se consultadoc num del docente
			ps = cn.prepareStatement(rb
					.getString("getCordinadoresInstxsedexjornada"));
			ps.setString(posicion++, l.getInstId());
			ps.setLong(posicion++, Integer.parseInt(l.getSedeId()));
			ps.setLong(posicion++, Integer.parseInt(l.getJornadaId()));

			rs = ps.executeQuery();
			posicion = 1;

			String comtmp = "";
			while (rs.next()) {
				listdocumentos.add(rs.getString(1));
			}

		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			sqle.printStackTrace();
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
		return listdocumentos;
	}

	/**
	 * Funcinn: Traer lo estudiantes de una asignatura predeterminada <br>
	 * 
	 * @param notaminima
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @return boolean
	 */
	public Collection getEstudiantesConsultaPierdenAsignatura(
			FiltroBeanEvaluacion f, long tipoEval, int totalperiodos,
			double notaminima) {
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long jer = 0;
		long jerAsig = 0;
		Collection list = null;
		try {
			list = new ArrayList();
			String dato[];
			long vig = getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getJerarquia18"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
				f.setJerarquiagrupo(rs.getString(1));
			}
			rs.close();
			ps.close();
			// System.out
			// .println("******************EVALUACION ASIG: JERARQUIA GRUPO "
			// + jer);
			ps = cn.prepareStatement(rb.getString("getJerarquia34"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			if (rs.next()) {
				jerAsig = rs.getLong(1);
				f.setJerarquiaAreaAsig(rs.getString(1));
			}
			rs.close();
			ps.close();

			// System.out
			// .println("******************EVALUACION ASIG: JERARQUIA ASIG "
			// + jerAsig + "     vigencia" + vig);
			// buscar estudiantes
			ps = cn.prepareStatement(rb
					.getString("listaEstudianteAsignaturaCOnsultaper."
							+ totalperiodos));
			posicion = 1;

			for (int i = 0; i < totalperiodos; i++) {
				ps.setLong(posicion++, tipoEval);
				ps.setLong(posicion++, tipoEval);
			}

			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, jerAsig);
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			while (rs.next()) {
				boolean adicionaralista = false;
				// se cuadra dimension por datos basicos mas totalperiosos con
				// su repsectiva recuperacinn
				int dimension = 6 + (totalperiodos * 2);
				dato = new String[dimension];
				posicion = 0;
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);

				for (int i = 0; i < totalperiodos; i++) {
					int eval = posicion;
					int evalrec = posicion + 1;
					try {
						// nota normal
						dato[posicion] = rs.getString(++posicion);
						// nota recuperacinn
						dato[posicion] = rs.getString(++posicion);

						double notaevaluar = 0;
						if (dato[eval] != null && dato[evalrec] != null) {

							dato[eval] = dato[eval].replace(',', '.');
							dato[evalrec] = dato[evalrec].replace(',', '.');

							if (Double.parseDouble(dato[eval]) < Double
									.parseDouble(dato[evalrec])) {
								notaevaluar = Double.parseDouble(dato[evalrec]);
								dato[eval] = String.valueOf(notaevaluar);
							} else {
								notaevaluar = Double.parseDouble(dato[eval]);
								dato[eval] = String.valueOf(notaevaluar);
							}
						} else {
							if (dato[eval] != null && dato[evalrec] == null) {
								dato[eval] = dato[eval].replace(',', '.');
								notaevaluar = Double.parseDouble(dato[eval]);
								dato[eval] = String.valueOf(notaevaluar);
							} else {
								if (dato[eval] == null && dato[evalrec] != null) {
									dato[evalrec] = dato[evalrec].replace(',',
											'.');
									notaevaluar = Double
											.parseDouble(dato[evalrec]);
									dato[eval] = String.valueOf(notaevaluar);
								} else {
									dato[eval] = "NR";
									dato[evalrec] = "NR";
								}
							}
						}

						if (!dato[eval].equals("NR")) {
							if (Double.parseDouble(dato[eval]) < notaminima) {
								adicionaralista = true;
							}
						}

					} catch (NullPointerException ex) {
						// significa no registrado (NR)
						dato[eval] = "NR";
						dato[evalrec] = "NR";

					} catch (NumberFormatException ex) {
						if (Float.parseFloat(dato[eval]) < notaminima) {
							adicionaralista = true;
						}
					}

				}

				if (adicionaralista) {
					list.add(dato);
				}

			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			sqle.printStackTrace();
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

	/**
	 * Funcinn: Consultar Asignatura x Periodo Consulta perdida de estudiantes<br>
	 * 
	 * @param FiltroBeanEvaluacion
	 *            f
	 * @param long tipoeval
	 * @param double paramnotaminima
	 * @return boolean
	 */
	public Collection getEstudiantesAsignaturaxPeriodoConsultaperdida(
			FiltroBeanEvaluacion f, long tipoEval, double paramnotaminima) {
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long jer = 0;
		long jerAsig = 0;
		Collection list = null;
		try {
			list = new ArrayList();
			String dato[];
			long vig = getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getJerarquia18"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
				f.setJerarquiagrupo(rs.getString(1));
			}
			rs.close();
			ps.close();
			// System.out
			// .println("******************EVALUACION ASIG: JERARQUIA GRUPO "
			// + jer);
			ps = cn.prepareStatement(rb.getString("getJerarquia34"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			if (rs.next()) {
				jerAsig = rs.getLong(1);
				f.setJerarquiaAreaAsig(rs.getString(1));
			}
			rs.close();
			ps.close();

			// System.out
			// .println("******************EVALUACION ASIG: JERARQUIA ASIG "
			// + jerAsig + "     vigencia" + vig);
			// buscar estudiantes
			ps = cn.prepareStatement(rb.getString("listaEstudianteAsignatura."
					+ f.getPeriodo() + "." + f.getOrden()));
			posicion = 1;
			ps.setLong(posicion++, tipoEval);
			ps.setLong(posicion++, tipoEval);
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, jerAsig);
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			while (rs.next()) {
				dato = new String[9];
				posicion = 0;
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);

				boolean adicionaralista = false;
				int eval = posicion;
				int evalrec = posicion + 2;

				try {
					// nota normal
					dato[posicion] = rs.getString(++posicion);
					// nota por defecto
					dato[posicion] = rs.getString(++posicion);
					// nota recuperacinn
					dato[posicion] = rs.getString(++posicion);

					double notaevaluar = 0;
					if (dato[eval] != null && dato[evalrec] != null) {

						dato[eval] = dato[eval].replace(',', '.');
						dato[evalrec] = dato[evalrec].replace(',', '.');

						if (Double.parseDouble(dato[eval]) < Double
								.parseDouble(dato[evalrec])) {
							notaevaluar = Double.parseDouble(dato[evalrec]);
							dato[eval] = String.valueOf(notaevaluar);
						} else {
							notaevaluar = Double.parseDouble(dato[eval]);
							dato[eval] = String.valueOf(notaevaluar);
						}
					} else {
						if (dato[eval] != null && dato[evalrec] == null) {
							dato[eval] = dato[eval].replace(',', '.');
							notaevaluar = Double.parseDouble(dato[eval]);
							dato[eval] = String.valueOf(notaevaluar);
						} else {
							if (dato[eval] == null && dato[evalrec] != null) {
								dato[evalrec] = dato[evalrec].replace(',', '.');
								notaevaluar = Double.parseDouble(dato[evalrec]);
								dato[eval] = String.valueOf(notaevaluar);
							} else {
								dato[eval] = "NR";
								dato[evalrec] = "NR";
							}
						}
					}

					if (!dato[eval].equals("NR")) {
						if (Double.parseDouble(dato[eval]) < paramnotaminima) {
							adicionaralista = true;
						}
					}

				} catch (NullPointerException ex) {
					// significa no registrado (NR)
					dato[eval] = "NR";
					dato[evalrec] = "NR";

				} catch (NumberFormatException ex) {
					if (Float.parseFloat(dato[eval]) < paramnotaminima) {
						adicionaralista = true;
					}
				}

				if (adicionaralista) {
					list.add(dato);
				}

			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			in.printStackTrace();
			return null;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
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

	/**
	 * Metodo que permite evaluar que estudiantes estan perdiendo una asignatura
	 * por inasistencia
	 * 
	 * @param f
	 * @param tipoEval
	 * @param minimoinasistencia
	 * @return
	 */
	public Collection getEstudiantesAsignaturaxPeriodoConsultaperdidaxInasistencia(
			FiltroBeanEvaluacion f, long tipoEval, int[] minimoinasitencias,
			ArrayList listasignaturas, boolean periodo) {
		String[] asig = new String[listasignaturas.size()];

		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());

		int posicion = 1;
		int p = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long jer = 0;

		Collection list = null;
		try {
			list = new ArrayList();
			String dato[];
			long vig = getVigenciaNumerico();
			cn = cursor.getConnection();

			for (int i = 0; i < listasignaturas.size(); i++) {

				asig[i] = String
						.valueOf(listasignaturas.get(i))
						.substring(
								String.valueOf(listasignaturas.get(i))
										.lastIndexOf('|') + 1,
								String.valueOf(listasignaturas.get(i)).length());

				ps = cn.prepareStatement(rb.getString("getJerarquia34"));
				posicion = 1;
				ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(f.getGrado()));
				ps.setLong(posicion++, Long.parseLong(asig[i]));
				ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
				ps.setLong(posicion++, vig);
				rs = ps.executeQuery();
				if (rs.next()) {
					// jerAsig = rs.getLong(1);
					asig[i] = rs.getString(1);
					f.setJerarquiaAreaAsig(rs.getString(1));
				}
				rs.close();
				ps.close();

			}

			ps = cn.prepareStatement(rb.getString("getJerarquia18"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
				f.setJerarquiagrupo(rs.getString(1));
			}
			rs.close();
			ps.close();

			// System.out
			// .println("******************EVALUACION ASIG: JERARQUIA ASIG "
			// + jerAsig + "     vigencia" + vig);
			// buscar estudiantes
			String coma = ",";
			String espacio = " ";

			String query = rb.getString("consultarinasistenciaxasignaturap1");

			String partequery = ", ";

			for (int j = 0; j < asig.length; j++) {

				String partequeryanadir = "";

				// if (periodo) {
				String o = "";
				for (int k = 1; k <= Integer.parseInt(f.getPeriodo()); k++) {
					if (k == Integer.parseInt(f.getPeriodo())) {
						o = o + " nvl(EvaAsiAusJus" + k + ",0) ";
					} else {
						o = o + " nvl(EvaAsiAusJus" + k + ",0) + ";
					}

				}
				partequeryanadir = "nvl((select "
						+ o
						+ " from evaluacion_asignatura where  EvaAsiCodJerar=? and EVAASIVIGENCIA=? and EvaAsiCodEstud=EstCodigo  ),0) as inasistencia"
						+ j;
				// } else {
				// partequeryanadir =
				// "nvl((select nvl(EvaAsiAusJus1,0)+nvl(EvaAsiAusJus2,0)+nvl(EvaAsiAusJus3,0)+nvl(EvaAsiAusJus4,0)+nvl(EvaAsiAusJus5,0)+nvl(EvaAsiAusJus6,0) from evaluacion_asignatura where EvaAsiCodEstud=EstCodigo and EVAASIVIGENCIA=? and EvaAsiCodJerar=?),0) as inasistencia"
				// + j;
				// }
				partequery += partequeryanadir;

				if (j != (asig.length - 1)) {
					partequery += coma;
				} else {
					partequery += espacio;
				}

			}

			query += partequery;
			query = query.concat(rb
					.getString("consultarinasistenciaxasignaturap2"));

			ps = cn.prepareStatement(query);
			posicion = 1;

			for (int j = 0; j < asig.length; j++) {

				//
				ps.setLong(posicion++, Long.parseLong(asig[j]));
				//
				ps.setLong(posicion++, vig);
				// if (periodo) {
				// ps.setLong(posicion++, Integer.parseInt(f.getPeriodo()));
				// }
			}
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();

			// variable aser usada si la consulta es por periodos para ajustar
			// el recorridodel array datos[]
			int quitaracumulado = 0;

			while (rs.next()) {
				// if (periodo) {
				dato = new String[8 + asig.length];
				quitaracumulado = 2;
				// } else {
				// dato = new String[6 + asig.length];
				// }
				posicion = 0;
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);

				// if (periodo) {
				// se setan valores de grado y grupo que vienen d ela
				// consulta
				dato[posicion] = f.getGrado();
				++posicion;

				dato[posicion] = f.getGrupo();
				++posicion;
				// }

				boolean adicionaralista = false;

				for (int j = 0; j < asig.length; j++) {
					int eval = posicion;
					dato[posicion] = rs.getString(++posicion - quitaracumulado);

					try {

						if (dato[eval] != null) {

							if (Integer.parseInt(dato[eval]) > minimoinasitencias[j]) {
								// System.out.println(Integer.parseInt(dato[eval])
								// + " " + minimoinasitencias[j]);
								// System.out.println("--" + true);
								adicionaralista = true;
							}

						}

					} catch (NullPointerException ex) {
						// significa no registrado (NR)
						dato[eval] = "NR";
					} catch (NumberFormatException ex) {
						dato[eval] = "NR";
					}
				}

				if (adicionaralista) {
					// for (int i = 0; i < dato.length; i++) {
					// System.out.println("Dato " + i + " " + dato[i]);
					// }

					list.add(dato);
				}

			}

		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.print(sqle.getMessage());
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
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

	private boolean existeEstudianteAsig(Connection cn, long jerarAsig,
			long codigo) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = cn.prepareStatement(rb.getString("existeEstudianteAsig"));
			int i = 1;
			st.setLong(i++, jerarAsig);
			st.setLong(i++, codigo);
			st.setLong(i++, getVigenciaNumerico());
			rs = st.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	private void actualizarCierreAsignatura(Connection cn,
			FiltroBeanEvaluacion filtro, String asignatura,
			TipoEvalVO tipoEval, long nivelEval) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 0;
		try {
			if (filtro.getCerrar().equals("1")) {
				int estado = -1;
				ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
						+ filtro.getPeriodo()));
				ps.clearParameters();
				posicion = 1;
				ps.setLong(posicion++,
						Long.parseLong(filtro.getJerarquiagrupo()));
				ps.setLong(posicion++, Long.parseLong(asignatura));
				ps.setInt(posicion++, 2);
				ps.setLong(posicion++, getVigenciaNumerico());
				rs = ps.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
				rs.close();
				ps.close();
				if (estado == -1)
					ps = cn.prepareStatement(rb.getString("InsertarCierreGrupo"
							+ filtro.getPeriodo()));
				if (estado != -1)
					ps = cn.prepareStatement(rb
							.getString("ActualizarCierreGrupo"
									+ filtro.getPeriodo()));
				if (ps != null) {
					ps.clearParameters();
					posicion = 1;
					ps.setInt(posicion++, 1);
					ps.setLong(posicion++,
							Long.parseLong(filtro.getJerarquiagrupo()));
					ps.setLong(posicion++, Long.parseLong(asignatura));
					ps.setLong(posicion++, 2);
					ps.setLong(posicion++, getVigenciaNumerico());
					ps.executeUpdate();
					ps.close();
				}

				// PROMEDIAR AREAS
				// System.out.println(new Date()
				// + "  EVAL ASIG: PROMEDIO AREAS********************");
				if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_ASIG_NUM
						|| tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_ASIG_PORCENTUAL)
					if (tipoEval.getModo_eval() == 2) {
						if (promedioAreaAsignatura(cn, getVigenciaNumerico(),
								Long.parseLong(filtro.getInstitucion()),
								Long.parseLong(filtro.getJerarquiagrupo()),
								Long.parseLong(asignatura),
								Integer.parseInt(filtro.getPeriodo()),
								Long.parseLong(filtro.getSede()),
								Long.parseLong(filtro.getJornada()))) {
							// System.out
							// .println("APOYO:PROMEDIO AREA_ASIGNATURA: CIERRE ASIG SATISFACTORIO");
						}
						// else {
						// System.out
						// .println("APOYO:PROMEDIO AREA_ASIGNATURA: CIERRE ASIG NO FUE SATISFACTORIO");
						// }
						/*
						 * if(promedioAsignaturas(cn, getVigenciaNumerico(),
						 * Long.parseLong(filtro.getInstitucion()),
						 * Long.parseLong(filtro.getSede()),
						 * Long.parseLong(filtro.getJornada()),
						 * Integer.parseInt(filtro.getPeriodo()), 1,
						 * Long.parseLong(filtro.getJerarquiagrupo()),
						 * Long.parseLong(asignatura))){ System.out.println(new
						 * Date()+"PROMEDIO AREA: CIERRE ASIG SATISFACTORIO");
						 * }else{ System.out.println(new
						 * Date()+"PROMEDIO AREA: CIERRE ASIG NO FUE SATISFACTORIO"
						 * ); }
						 * if(promedioAreas(cn,getVigenciaNumerico(),Long.parseLong
						 * (filtro.getInstitucion()),
						 * Long.parseLong(filtro.getJerarquiagrupo()),
						 * Long.parseLong(asignatura),
						 * Integer.parseInt(filtro.getPeriodo
						 * ()),Long.parseLong(filtro
						 * .getSede()),Long.parseLong(filtro
						 * .getJornada()),nivelEval)){ System.out.println(new
						 * Date()+"PROMEDIO AREA: CIERRE ASIG SATISFACTORIO");
						 * }else{ System.out.println(new
						 * Date()+"PROMEDIO AREA: CIERRE ASIG NO FUE SATISFACTORIO"
						 * ); }
						 */
					}
				// System.out.println(new Date()
				// + " EVAL ASIG:FIN PROMEDIO AREAS********************");

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>
	 **/
	public boolean promedioAreas(Connection con, long vig, long inst,
			long codJerGrupo, long asig, int per, long sede, long jor,
			long nivelEval) {
		// Connection con=null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {

			// con=cursor.getConnection();
			/* callable statement */
			cstmt = con.prepareCall("{call ACT_PROM_AREA(?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, vig);
			cstmt.setInt(posicion++, per);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, sede);
			cstmt.setLong(posicion++, jor);
			cstmt.setLong(posicion++, codJerGrupo);
			cstmt.setLong(posicion++, asig);
			cstmt.setLong(posicion++, 1);
			cstmt.setLong(posicion++, nivelEval);
			// System.out.println("PROMEDIO AREA: ASIGNATURA CIERRE - jerGrupo: "
			// + codJerGrupo
			// + " Inicia procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out
			// .println("PROMEDIO AREA: ASIGNATURA CIERRE - Fin procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (SQLException e) {
			System.out.println("PROMEDIO AREA:excepcinn sql!");
			e.printStackTrace();

			return false;
		} catch (Exception e) {
			System.out.println("PROMEDIO AREA:excepcinn!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				// OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta procedimiento para el calculo de la nota de asignatura
	 * final<BR>
	 **/
	public boolean promedioAsignaturas(Connection con, long vig, long inst,
			long sede, long jor, int per, int accion, long jerGrupo, long asig) {
		// Connection con=null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {

			// con=cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call ACT_PROM_ASIGNATURA(?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, vig);
			cstmt.setInt(posicion++, per);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, sede);
			cstmt.setLong(posicion++, jor);
			cstmt.setLong(posicion++, jerGrupo);
			cstmt.setLong(posicion++, asig);
			cstmt.setLong(posicion++, accion);
			// System.out
			// .println("ACT PROMEDIO ASIGNATURA:  - Inicia procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out
			// .println("ACT PROMEDIO ASIGNATURA: - Fin procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (SQLException e) {
			System.out.println("PROMEDIO AREA:excepcinn sql!");
			e.printStackTrace();

			return false;
		} catch (Exception e) {
			System.out.println("PROMEDIO AREA:excepcinn!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				// OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	public Collection getEstudiantesArea(FiltroBeanEvaluacion f, long tipoEval) {
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long jer = 0;
		long jerAsig = 0;
		Collection list = null;
		try {
			list = new ArrayList();
			String dato[];
			long vig = getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getJerarquia18"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
				f.setJerarquiagrupo(rs.getString(1));
			}
			rs.close();
			ps.close();

			ps = cn.prepareStatement(rb.getString("getJerarquia24"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			if (rs.next()) {
				jerAsig = rs.getLong(1);
				f.setJerarquiaAreaAsig(rs.getString(1));
			}
			rs.close();
			ps.close();
			// buscar estudiantes
			ps = cn.prepareStatement(rb.getString("listaEstudianteArea."
					+ f.getPeriodo() + "." + f.getOrden()));
			posicion = 1;
			ps.setLong(posicion++, tipoEval);
			/*
			 * ps.setLong(posicion++, Long.parseLong(area));
			 * ps.setLong(posicion++, vig);
			 */
			ps.setLong(posicion++, tipoEval);
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, jerAsig);
			ps.setLong(posicion++, vig);
			rs = ps.executeQuery();
			while (rs.next()) {
				dato = new String[9];
				posicion = 0;
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				dato[posicion] = rs.getString(++posicion);
				list.add(dato);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			in.printStackTrace();
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			sqle.printStackTrace();
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

	public boolean insertarEvalArea(FiltroBeanEvaluacion f) {
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		long vigencia;
		Connection cn = null;
		PreparedStatement st = null, st2 = null, st3 = null, st4 = null;
		ResultSet rs = null;
		int i, j;
		long valores[] = null;
		String param[] = null;
		long codigo = 0;
		int ausencia = -99;
		double nota = 0;
		String sql = "", sql2 = "";
		try {
			vigencia = Long.parseLong(getVigencia());
			long jerarAsig = Long.parseLong(f.getJerarquiaAreaAsig());
			// System.out.println("EVAL AREA: JERAR: " + jerarAsig);
			long jerarGrupo = Long.parseLong(f.getJerarquiagrupo());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			String a[] = f.getNota();
			String aus[] = f.getAusjus();
			int indice = 0;
			if (a != null) {
				valores = new long[a.length];
				st = cn.prepareStatement(rb
						.getString("plantilla.insertarEvaluacionArea"
								+ f.getPeriodo()));
				st2 = cn.prepareStatement(rb
						.getString("plantilla.actualizarEvaluacionArea"
								+ f.getPeriodo()));
				st3 = cn.prepareStatement(rb
						.getString("plantilla.insertarEvaluacionAreaNull"
								+ f.getPeriodo()));
				st4 = cn.prepareStatement(rb
						.getString("plantilla.actualizarEvaluacionAreaNull"
								+ f.getPeriodo()));
				st.clearBatch();
				st2.clearBatch();
				st3.clearBatch();
				st4.clearBatch();
				for (j = 0; j < a.length; j++) {
					i = 1;
					param = a[j].replace('|', ':').split(":");
					if (param != null && param.length > 1) {
						codigo = Long.parseLong(param[0]);
						nota = Double.parseDouble(param[1]);

						// if (param.length > 2)
						// System.out.println("EVAL ASIG: NOTA: " + nota
						// + "  RECUPERACION: " + param[2]);
						// else
						// System.out.println("EVAL ASIG: NOTA: " + nota
						// + "  RECUPERACION: NO VIENE");

						if (aus != null && aus[j] != null && !aus[j].equals("")) {
							ausencia = Integer.parseInt(aus[j]);
						} else {
							ausencia = -99;
						}
						valores[indice++] = codigo;
						sql2 += "?,";
						if (!existeEstudianteArea(cn, jerarAsig, codigo)) {// ingresar
							if (ausencia != -99) {
								st.setLong(i++, jerarAsig);
								st.setLong(i++, codigo);
								st.setLong(i++, vigencia);
								st.setDouble(i++, nota);
								st.setInt(i++, ausencia);
								if (param.length > 2)
									st.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st.setNull(i++, java.sql.Types.DOUBLE);
								st.addBatch();
							} else {
								st3.setLong(i++, jerarAsig);
								st3.setLong(i++, codigo);
								st3.setLong(i++, vigencia);
								st3.setDouble(i++, nota);
								if (param.length > 2)
									st3.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st3.setNull(i++, java.sql.Types.DOUBLE);
								st3.addBatch();
							}
						} else {// actualizar
							if (ausencia != -99) {
								st2.setDouble(i++, nota);
								st2.setInt(i++, ausencia);
								if (param.length > 2)
									st2.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st2.setNull(i++, java.sql.Types.DOUBLE);
								st2.setLong(i++, jerarAsig);
								st2.setLong(i++, codigo);
								st2.setLong(i++, vigencia);
								st2.addBatch();
							} else {
								st4.setDouble(i++, nota);
								if (param.length > 2)
									st4.setDouble(i++,
											Double.parseDouble(param[2]));
								else
									st4.setNull(i++, java.sql.Types.DOUBLE);
								st4.setLong(i++, jerarAsig);
								st4.setLong(i++, codigo);
								st4.setLong(i++, vigencia);
								st4.addBatch();
							}
						}
					}
				}
				st.executeBatch();
				st2.executeBatch();
				st3.executeBatch();
				st4.executeBatch();
				st.close();
				st2.close();
				st3.close();
				st4.close();
				if (sql2.length() > 0) {
					sql2 = sql2.substring(0, sql2.length() - 1);
					sql = rb.getString("getCountEstudianteArea.1."
							+ f.getPeriodo())
							+ sql2 + rb.getString("getCountEstudianteArea.2");
				} else {
					sql = rb.getString("getCountEstudianteArea.3."
							+ f.getPeriodo());
				}
				st = cn.prepareStatement(sql);
				i = 1;
				st.setLong(i++, jerarGrupo);
				st.setLong(i++, jerarAsig);
				st.setLong(i++, vigencia);
				for (j = 0; j < indice; j++) {
					st.setLong(i++, valores[j]);
				}
				rs = st.executeQuery();
				while (rs.next()) {
					i = 1;
					if (rs.getInt(2) == 0) {// se puede eliminar todo
						st2 = cn.prepareStatement(rb
								.getString("plantilla.eliminarEvaluacionArea"));
						st2.setLong(i++, jerarAsig);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vigencia);
						st2.executeUpdate();
						st2.close();
					} else {// se actualiza a null porque hay mas de un registro
							// alli
						st2 = cn.prepareStatement(rb
								.getString("plantilla.eliminarEvaluacionAreaNull"
										+ f.getPeriodo()));
						st2.setLong(i++, jerarAsig);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vigencia);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
			actualizarCierreArea(cn, f, area);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando evaluar por area. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeStatement(st3);
				OperacionesGenerales.closeStatement(st4);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	private void actualizarCierreArea(Connection cn,
			FiltroBeanEvaluacion filtro, String area) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 0;
		try {
			if (filtro.getCerrar().equals("1")) {
				int estado = -1;
				ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
						+ filtro.getPeriodo()));
				ps.clearParameters();
				posicion = 1;
				ps.setLong(posicion++,
						Long.parseLong(filtro.getJerarquiagrupo()));
				ps.setLong(posicion++, Long.parseLong(area));
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, getVigenciaNumerico());
				rs = ps.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
				rs.close();
				ps.close();
				if (estado == -1)
					ps = cn.prepareStatement(rb.getString("InsertarCierreGrupo"
							+ filtro.getPeriodo()));
				if (estado != -1)
					ps = cn.prepareStatement(rb
							.getString("ActualizarCierreGrupo"
									+ filtro.getPeriodo()));
				if (ps != null) {
					ps.clearParameters();
					posicion = 1;
					ps.setInt(posicion++, 1);
					ps.setLong(posicion++,
							Long.parseLong(filtro.getJerarquiagrupo()));
					ps.setLong(posicion++, Long.parseLong(area));
					ps.setLong(posicion++, 1);
					ps.setLong(posicion++, getVigenciaNumerico());
					ps.executeUpdate();
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
	}

	private boolean existeEstudianteArea(Connection cn, long jerarAsig,
			long codigo) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = cn.prepareStatement(rb.getString("existeEstudianteArea"));
			int i = 1;
			st.setLong(i++, jerarAsig);
			st.setLong(i++, codigo);
			st.setLong(i++, getVigenciaNumerico());
			rs = st.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public int getEstadoPlan(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("eval.getEstadoPlan"));
			st.setLong(i++, inst);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
		return 0;
	}

	public int getNivelPerfil(int perfil) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("eval.getNivelPerfil"));
			st.setInt(i++, perfil);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
		return 0;
	}

	public int getNivelPerfil(Connection cn, int perfil) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			st = cn.prepareStatement(rb.getString("eval.getNivelPerfil"));
			st.setInt(i++, perfil);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public int getEstadoPlan(Connection cn, long inst) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			st = cn.prepareStatement(rb.getString("eval.getEstadoPlan"));
			st.setLong(i++, inst);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public List getListaDocenteArea(long inst, int metod, int vig, int grado,
			long area) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("eval.listaDocenteArea"));
			st.setInt(i++, vig);
			st.setLong(i++, area);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaDocenteAsignatura(long inst, int metod, int vig,
			int grado, long asig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("eval.listaDocenteAsignatura"));
			st.setInt(i++, vig);
			st.setLong(i++, asig);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	// CAMBIOS EVLUACION PARAMETRSO INSTITICION

	public TipoEvalVO getTipoEval(String metod, String grado, Login login)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		TipoEvalVO tipoEvalVO = new TipoEvalVO();
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaNumerico();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, Long.parseLong(login.getInstId()));
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
			st.setLong(i++, codigoNivelEval);
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));
			}
			rs.close();
			st.close();

			// TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql = rb.getString("getTipoEval.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getTipoEval.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getTipoEval.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getTipoEval.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, Long.parseLong(login.getInstId()));

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, Long.parseLong(login.getSedeId()));
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, Long.parseLong(login.getJornadaId()));
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, Long.parseLong(metod));
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(Integer.parseInt(grado));
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, Long.parseLong(grado));

			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				tipoEvalVO.setCod_tipo_eval(rs.getLong(i++));
				tipoEvalVO.setEval_min(rs.getDouble(i++));
				tipoEvalVO.setEval_max(rs.getDouble(i++));
				tipoEvalVO.setTipo_eval_prees(rs.getInt(i++));
				tipoEvalVO.setModo_eval(rs.getInt(i++));
				tipoEvalVO.setTipoEvalAprobMin(rs.getDouble(i++));
//				tipoEvalVO.setTipoEvalAprobMin(rs.getInt(i++));
			} else {
				tipoEvalVO = null;
			}
			rs.close();
			st.close();

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
		return tipoEvalVO;
	}

	public Collection getEscalaConceptual(String metod, String grado,
			Login login) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		List l = new ArrayList();
		ItemVO item = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaNumerico();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, Long.parseLong(login.getInstId()));
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
			st.setLong(i++, codigoNivelEval);
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));
			}
			rs.close();
			st.close();

			// TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql = rb.getString("getEscalaConceptual.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.grado");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, Long.parseLong(login.getInstId()));
			st.setLong(i++, codigoNivelEval);

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, Long.parseLong(login.getSedeId()));
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, Long.parseLong(login.getJornadaId()));
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, Long.parseLong(metod));
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(Integer.parseInt(grado));
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, Long.parseLong(grado));

			rs = st.executeQuery();
			list = getCollection(rs);
			i = 1;
			while (rs.next()) {
				// System.out.println("ESCALA DAO, ENCONTRO DATOS");
				item = new ItemVO(rs.getInt(i++), rs.getString(i++));
				l.add(item);
			}
			rs.close();
			st.close();

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

	public List getEscalaConceptualComp(String metod, String grado, Login login)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		List l = new ArrayList();
		ItemVO item = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaNumerico();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, Long.parseLong(login.getInstId()));
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
			st.setLong(i++, codigoNivelEval);
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));
			}
			rs.close();
			st.close();

			// TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql = rb.getString("getEscalaConceptual.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.grado");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, Long.parseLong(login.getInstId()));
			st.setLong(i++, codigoNivelEval);

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, Long.parseLong(login.getSedeId()));
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, Long.parseLong(login.getJornadaId()));
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, Long.parseLong(metod));
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(Integer.parseInt(grado));
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, Long.parseLong(grado));

			rs = st.executeQuery();
			// list = getCollection(rs);
			i = 1;
			l = new ArrayList();
			while (rs.next()) {
				i = 1;
				item = new ItemVO(rs.getLong(i++), rs.getString(i++));
				l.add(item);
			}
			rs.close();
			st.close();

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
		return l;
	}

	public int getNivelGrado(int grado) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getNivelGrado"));
			st.setInt(i++, grado);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
		return 0;
	}

	/**
	 * Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>
	 **/
	public boolean promedioAreaAsignatura(Connection con, long vig, long inst,
			long codJerGrupo, long asig, int per, long sede, long jor) {
		// Connection con=null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {

			// con=cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call ACT_PROM_AREA_ASIG(?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, vig);
			cstmt.setInt(posicion++, per);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, sede);
			cstmt.setLong(posicion++, jor);
			cstmt.setLong(posicion++, codJerGrupo);
			cstmt.setLong(posicion++, asig);
			cstmt.setLong(posicion++, 1);
			// System.out
			// .println("PROMEDIO ASIGNATURA: ASIGNATURA CIERRE - jerGrupo: "
			// + codJerGrupo
			// + " Inicia procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out
			// .println("PROMEDIO ASIGNATURA: ASIGNATURA CIERRE - Fin procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (SQLException e) {
			System.out.println("PROMEDIO ASIGNATURA:excepcinn sql!");
			e.printStackTrace();

			return false;
		} catch (Exception e) {
			System.out.println("PROMEDIO ASIGNATURA:excepcinn!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				// OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Metodo para consultar la intensidad horaria de una asignatura
	 * 
	 * @param ins
	 * @param grado
	 * @param metodologia
	 * @param asignatura
	 * @param vigencia
	 * @return
	 * @throws Exception
	 */
	public int consultarIntensidadHorariaporasignatura(String ins,
			String grado, String metodologia, String asignatura, String vigencia)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("consultarintensidadhorariadeasignatura"));
			st.setLong(i++, Integer.parseInt(ins));
			st.setLong(i++, Integer.parseInt(grado));
			st.setLong(i++, Integer.parseInt(metodologia));
			st.setLong(i++, Integer.parseInt(asignatura));
			st.setLong(i++, Integer.parseInt(vigencia));

			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(5);
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
		return 0;
	}

	public int consultarIntensidadHorariaporarea(String ins, String grado,
			String metodologia, String vigencia, String area) throws Exception {
		int intensidadhoraria = 0;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("consultarintensidadhorariadearea"));
			st.setLong(i++, Integer.parseInt(ins));
			st.setLong(i++, Integer.parseInt(grado));
			st.setLong(i++, Integer.parseInt(metodologia));
			st.setLong(i++, Integer.parseInt(vigencia));
			st.setLong(i++, Integer.parseInt(area));

			rs = st.executeQuery();

			while (rs.next()) {
				intensidadhoraria = intensidadhoraria + rs.getInt(5);
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
		return intensidadhoraria;
	}

	/**
	 * Metodo para agregar las alarmas en el sistema de perdida de estudiante
	 * 
	 * @param alarma
	 * @return
	 */
	public int insertarAlarma(AlarmaBean alarma) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		// Statement st = null;
		ResultSet rs = null;
		// String jer = "";
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("insertaralarma"));
			posicion = 1;
			ps.setString(posicion++, alarma.getAlar_asi());
			ps.setString(posicion++, alarma.getAlar_area());
			ps.setString(posicion++, alarma.getAlar_docdocente());
			ps.setString(posicion++, alarma.getAlar_nombreestudiante());
			ps.setString(posicion++, alarma.getAlar_motivoinasistencia());
			ps.setString(posicion++, alarma.getAlar_motivobajorendimiento());
			ps.setString(posicion++, alarma.getAlar_grupo());
			ps.setString(posicion++, alarma.getAlar_grado());
			ps.setString(posicion++, alarma.getAlar_estado());

			int ret = ps.executeUpdate();

			ps.close();

			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			in.printStackTrace();
			return 0;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return 0;
		} finally {
			try {
				// OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return 1;
	}

	/**
	 * Mntodo que devuelve la observacinn de un periodo.
	 * 
	 * @param alarma
	 * @return obs
	 */
	public String getObservacionPeriodo(AlarmaBean observacion) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String obs = "";
		int i = 0;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getObservacionPeriodo."
					+ observacion.getAlar_periodo()));
			i = 1;
			pst.setLong(i++, Long.parseLong(observacion.getAlar_codest()));
			pst.setLong(i++, Long.parseLong(observacion.getAlar_vigencia()));
			rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) != null) {
					obs = rs.getString(1);
				}
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
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return obs;
	}

	public void guardarObservacionEstudiante(AlarmaBean observacion) {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 0;
		String obs = "";
		try {
			cn = cursor.getConnection();

			pst = cn.prepareStatement(rb2
					.getString("actualizarObservacionEstudiante."
							+ observacion.getAlar_periodo()));
			posicion = 1;

			if (getObservacionPeriodo(observacion).trim().equals("")) {
				// System.out.println("Est " + observacion.getAlar_codest() +
				// " "
				// + observacion.getAlar_motivobajorendimiento()
				// + observacion.getAlar_motivoinasistencia() + " "
				// + observacion.getAlar_periodo() + " "
				// + observacion.getAlar_asi());
				if (observacion.getAlar_motivobajorendimiento() != null
						&& observacion.getAlar_motivobajorendimiento().equals(
								"X")) {
					obs = "El estudiante presenta dificultades en el rendimiento de la siguiente asignatira "
							+ observacion.getAlar_asi() + ".";
				}
				if (observacion.getAlar_motivoinasistencia() != null
						&& observacion.getAlar_motivoinasistencia().equals("X")) {
					if (obs.length() <= 0) {
						obs = "El estudiante presenta excesivas fallas en la siguiente asignatura: "
								+ observacion.getAlar_asi() + ".";
					} else {
						obs = " El estudiante presenta excesivas fallas en la siguiente asignatura: "
								+ observacion.getAlar_asi() + ".";
					}
				}
				int o = obs.length() - 1;
				if (o > 3000) {
					o = 3000;
				}
				pst.setString(posicion++, obs.substring(0, o));

			} else {
				// System.out.println("Est2 " + observacion.getAlar_codest() +
				// " "
				// + observacion.getAlar_motivobajorendimiento()
				// + observacion.getAlar_motivoinasistencia() + " "
				// + observacion.getAlar_periodo() + " "
				// + observacion.getAlar_asi());

				if (observacion.getAlar_motivobajorendimiento() != null
						&& observacion.getAlar_motivobajorendimiento().equals(
								"X")) {
					obs = "El estudiante presenta dificultades en el rendimiento de la siguiente asignatura "
							+ observacion.getAlar_asi() + ".";
				}
				if (observacion.getAlar_motivoinasistencia() != null
						&& observacion.getAlar_motivoinasistencia().equals("X")) {
					if (obs.length() <= 0) {
						obs = "El estudiante presenta excesivas fallas en la siguiente asignatura: "
								+ observacion.getAlar_asi() + ".";
					} else {
						obs = " El estudiante presenta excesivas fallas en la siguiente asignatura: "
								+ observacion.getAlar_asi() + ".";
					}
				}
				obs = getObservacionPeriodo(observacion) + " " + obs;
				int o = obs.length() - 1;
				if (o > 3000) {
					o = 3000;
				}
				pst.setString(posicion++, obs.substring(0, o));

			}

			pst.setLong(posicion++,
					Long.parseLong(observacion.getAlar_grujerar()));
			pst.setLong(posicion++,
					Long.parseLong(observacion.getAlar_codest()));
			pst.setLong(posicion++,
					Long.parseLong(observacion.getAlar_vigencia()));
			int cant = pst.executeUpdate();

			pst.close();
			if (cant == 0) {
				pst = cn.prepareStatement(rb2
						.getString("ingresarObservacionEstudiante."
								+ observacion.getAlar_periodo()));

				posicion = 1;

				if (observacion.getAlar_motivobajorendimiento() != null
						&& observacion.getAlar_motivobajorendimiento().equals(
								"X")) {
					obs = "El estudiante presenta dificultades en el rendimiento de la siguiente asignatura "
							+ observacion.getAlar_asi();
				}
				if (observacion.getAlar_motivoinasistencia() != null
						&& observacion.getAlar_motivoinasistencia().equals("X")) {
					if (obs.length() <= 0) {
						obs = "El estudiante presenta excesivas fallas en la siguiente asignatura: "
								+ observacion.getAlar_asi();
					} else {
						obs = "\\nEl estudiante presenta excesivas fallas en la siguiente asignatura: "
								+ observacion.getAlar_asi();
					}
				}

				pst.setString(posicion++, obs);
				pst.setLong(posicion++,
						Long.parseLong(observacion.getAlar_grujerar()));
				pst.setLong(posicion++,
						Long.parseLong(observacion.getAlar_codest()));
				pst.setLong(posicion++,
						Long.parseLong(observacion.getAlar_vigencia()));

				pst.executeUpdate();
				pst.close();

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
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

}