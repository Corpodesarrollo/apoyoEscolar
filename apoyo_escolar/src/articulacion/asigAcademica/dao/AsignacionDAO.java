package articulacion.asigAcademica.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asigAcademica.vo.AsignacionVO;
import articulacion.asigAcademica.vo.AsignaturaVO;
import articulacion.asigAcademica.vo.DatosVO;
import articulacion.asigAcademica.vo.DocenteVO;
import articulacion.asigAcademica.vo.EspecialidadVO;
import articulacion.asigAcademica.vo.JornadaVO;
import articulacion.asigAcademica.vo.SedeVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class AsignacionDAO extends Dao {

	public AsignacionDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("articulacion.asigAcademica.bundle.sentencias");
	}
	
	public boolean actualizar(AsignacionVO asignacionVO,DatosVO filtro) {
		Connection cn=null;
		PreparedStatement st=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			st=cn.prepareStatement(rb.getString("actualizarAsignacion"));
			i=1;
			st.setLong(i++,asignacionVO.getAsigIntHor());
			st.setLong(i++,asignacionVO.getAsigDocente());
			st.setLong(i++,asignacionVO.getAsigAsignatura());
			st.setLong(i++,asignacionVO.getAsigSemestre());
			st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("actualizarAsignacionTotal"));
			i=1;
//			System.out.print("valores$$$="+filtro.getAsigIntHorTotal()+"//"+filtro.getAsigIntHorExtra());
			st.setLong(i++,filtro.getAsigIntHorTotal());
			st.setLong(i++,filtro.getAsigIntHorExtra());
			st.setLong(i++,filtro.getInstitucion());
			st.setLong(i++,filtro.getSede());
			st.setLong(i++,filtro.getJornada());
			st.setLong(i++,asignacionVO.getAsigDocente());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}

	public boolean ingresar(AsignacionVO asignacionVO,DatosVO filtro) {
		Connection cn=null;
		PreparedStatement st=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			st=cn.prepareStatement(rb.getString("ingresarAsignacion"));
			i=1;
			st.setLong(i++,asignacionVO.getAsigDocente());
			st.setLong(i++,asignacionVO.getAsigAsignatura());
			st.setLong(i++,asignacionVO.getAsigSemestre());
			st.setLong(i++,asignacionVO.getAsigIntHor());
			st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("actualizarAsignacionTotal"));
			i=1;
			st.setLong(i++,filtro.getAsigIntHorTotal());
			st.setLong(i++,filtro.getAsigIntHorExtra());
			st.setLong(i++,filtro.getInstitucion());
			st.setLong(i++,filtro.getSede());
			st.setLong(i++,filtro.getJornada());
			st.setLong(i++,asignacionVO.getAsigDocente());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	public boolean validar(AsignacionVO asignacionVO) {
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("validarAsignacion"));
			i=1;
			st.setLong(i++,asignacionVO.getAsigDocente());
			st.setLong(i++,asignacionVO.getAsigAsignatura());
			st.setLong(i++,asignacionVO.getAsigSemestre());
			rs=st.executeQuery();
			if(rs.next()){
				return false;
			}
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
		return true;
	}
	
	public AsignacionVO getAsignacion(long docente,long asignatura,long semestre,long institucion,long sede,long jornada,DatosVO filtro){
		AsignacionVO asignacionVO=null;
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAsignacion"));
			i=1;
			st.setLong(i++,docente);
			st.setLong(i++,asignatura);
			st.setLong(i++,semestre);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				asignacionVO=new AsignacionVO();
				asignacionVO.setAsigComponente(rs.getInt(i++));
				asignacionVO.setAsigEspecialidad(rs.getLong(i++));
				asignacionVO.setAsigDocente(rs.getLong(i++));
				asignacionVO.setAsigAsignatura(rs.getLong(i++));
				asignacionVO.setAsigSemestre(rs.getInt(i++));
				asignacionVO.setAsigIntHor(rs.getInt(i++));
				asignacionVO.setFormaEstado("1");
			}
			rs.close();
			st.close();
			//ih real
			st=cn.prepareStatement(rb.getString("getAsignacionReal"));
			i=1;
			st.setLong(i++,docente);
			st.setLong(i++,asignatura);
			st.setLong(i++,institucion);
			st.setInt(i++,filtro.getAnho());
			st.setInt(i++,filtro.getPeriodo());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				//System.out.println(asignatura+"Entra a ihreal parcial"+rs.getInt(1));
				filtro.setAsigIntHorReal(rs.getInt(i++));
			}
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
		return asignacionVO;
	}
	
	public boolean eliminar(long docente,long asignatura,long semestre) {
		Connection cn=null;
		PreparedStatement st=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("eliminarAsignacion"));
			i=1;
			st.setLong(i++,docente);
			st.setLong(i++,asignatura);
			st.setLong(i++,semestre);
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje(" "+getErrorCode(sqle));
			return false;
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje(" "+sqle.toString());
			return false;
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	public List getSede(long inst,int sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		SedeVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getSede"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new SedeVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
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
		return lpA;
	}
	public List getJornada(long inst,int sede,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		JornadaVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getJornada"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new JornadaVO();
				lp.setSede(rs.getInt(i++));
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
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
		return lpA;
	}

	public List getAjaxDocente(long inst,int sed,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		DocenteVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxDocente"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sed);
			st.setInt(i++,jor);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new DocenteVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
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
		return lpA;
	}
	
	public List getAllAsignacion(DatosVO filtro){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		AsignacionVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAllAsignacion"));
			i=1;
			st.setLong(i++,filtro.getDocente());
			st.setLong(i++,filtro.getInstitucion());
			st.setInt(i++,filtro.getSede());
			st.setInt(i++,filtro.getJornada());
			st.setInt(i++,filtro.getAnho());
			st.setInt(i++,filtro.getPeriodo());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new AsignacionVO();
				lp.setAsigDocente(rs.getLong(i++));
				lp.setAsigAsignatura(rs.getLong(i++));
				lp.setAsigSemestre(rs.getInt(i++));
				lp.setAsigNombreAsignatura(rs.getString(i++));
				lp.setAsigIntHor(rs.getInt(i++));
				lp.setAsigAnho(rs.getInt(i++));
				lp.setAsigPeriodo(rs.getInt(i++));
				lpA.add(lp);
			}
			rs.close();
			st.close();
			//buscar el total
			st=cn.prepareStatement(rb.getString("getAsignacionTotal"));
			i=1;
			st.setLong(i++,filtro.getInstitucion());
			st.setInt(i++,filtro.getSede());
			st.setInt(i++,filtro.getJornada());
			st.setLong(i++,filtro.getDocente());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				filtro.setAsigIntHorTotal(rs.getInt(i++));
				filtro.setAsigIntHorExtra(rs.getInt(i++));
			}
			rs.close();st.close();
			//ih real
			st=cn.prepareStatement(rb.getString("getAllAsignacionReal"));
			i=1;
			st.setLong(i++,filtro.getDocente());
			st.setLong(i++,filtro.getInstitucion());
			st.setInt(i++,filtro.getAnho());
			st.setInt(i++,filtro.getPeriodo());
			rs=st.executeQuery();
			if(rs.next()){
				//System.out.println("Entra por aca a coger el Real="+rs.getInt(1));
				i=1;
				filtro.setAsigIntHorReal(rs.getInt(i++));
			}
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
		return lpA;
	}
	public List getAjaxEspecialidad(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		EspecialidadVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxEspecialidad"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new EspecialidadVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
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
		return lpA;
	}

	public List getAjaxAsignatura(long inst,int anho,int per,int comp,long esp, int sem){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		AsignaturaVO lp=null;
		int i=0;
		try{
			if(anho==0 || per==0){
				return null;
			}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,anho);
			st.setInt(i++,per);
			st.setInt(i++,comp);
			st.setLong(i++,esp);
			st.setLong(i++,esp);
			st.setInt(i++,sem);
			//st.setInt(i++,1);//asignaturas que no son complementarias
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new AsignaturaVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
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
		return lpA;
	}

}
