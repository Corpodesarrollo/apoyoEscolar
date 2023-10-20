package articulacion.asigTutor.dao;


import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asigTutor.vo.AsigTutorVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.asigTutor.vo.DatosVO;
import articulacion.asigTutor.vo.EstudianteVO;
import articulacion.asigTutor.vo.JornadaVO;
import articulacion.asigTutor.vo.SedeVO;
import articulacion.asigTutor.vo.EspecialidadVO;
import articulacion.asigTutor.vo.DocenteVO;

public class AsigTutorDAO extends Dao {
	
    private ResourceBundle rb;
    
    public AsigTutorDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("articulacion.asigTutor.bundle.sentencias");
	}
    
    public List getJornada(String inst, String sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		JornadaVO jornadaVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getJornada"));
			i=1;
			st.setInt(i++,Integer.parseInt(inst));
		//	st.setInt(i++,Integer.parseInt(sede));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				jornadaVO=new JornadaVO();
				jornadaVO.setSede(rs.getInt(i++));
				jornadaVO.setCodigo(rs.getInt(i++));
				jornadaVO.setNombre(rs.getString(i++));
				l.add(jornadaVO);
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
		return l;
	}
    public List getSedes(String inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		SedeVO sedeVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getSede"));
			i=1;
			st.setInt(i++,Integer.parseInt(inst));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				sedeVO=new SedeVO();
				sedeVO.setCodigo(rs.getInt(i++));
				sedeVO.setNombre(rs.getString(i++));
				l.add(sedeVO);
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
		return l;
	}
   public boolean asignaTutor(long estudiante,long tutor, int semestre, long check, boolean nuevo){
	    Connection cn=null;
		PreparedStatement ps=null;
		int posicion=1;
		try {
			cn=cursor.getConnection();
		if(check!=0 && nuevo){
			try {
				ps=cn.prepareStatement(rb.getString("asignaTutor"));
				ps.setLong(posicion++,estudiante);
				ps.setLong(posicion++,tutor);
				ps.setInt(posicion++,semestre);
				ps.execute();
			}catch(SQLException e){
				e.printStackTrace();
				switch (e.getErrorCode()) {
				case 1: case 2291:
					setMensaje("Cndigo duplicado");
				}
			}
		}
		try {
			posicion=1;
			ps=cn.prepareStatement(rb.getString("cambiarTutor"));
			if(check!=0)
				ps.setLong(posicion++,tutor);
			else
				ps.setNull(posicion++,Types.VARCHAR);
			ps.setLong(posicion++,estudiante);
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1: case 2291:
				setMensaje("Cndigo duplicado");
			}
		}
		} catch (InternalErrorException e1) {
			e1.printStackTrace();
		}finally{
			try{
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	   return true; 
   }
   public List getEstudiantes(DatosVO datosVO){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		EstudianteVO estudianteVO=null;
		int i=1;
		try{
			cn=cursor.getConnection();
	/*		System.out.println("institu = "+datosVO.getInstitucion());
			System.out.println("sede ="+datosVO.getSede());
			System.out.println("jornada ="+datosVO.getJornada());
			System.out.println("methodologia ="+datosVO.getMetodologia());
			System.out.println("Especialidad ="+datosVO.getEspecialidad());
			System.out.println("Semestre ="+datosVO.getSemestre());
			System.out.println("methodologia ="+datosVO.getMetodologia());*/
			
			
			if(datosVO.getEspecialidad()==0 && datosVO.getSemestre()==0){
				st=cn.prepareStatement(rb.getString("getEstudiantes1"));
			}
			if(datosVO.getEspecialidad()!=0 && datosVO.getSemestre()==0){
				st=cn.prepareStatement(rb.getString("getEstudiantes2"));
			}
			if(datosVO.getEspecialidad()==0 && datosVO.getSemestre()!=0){
				st=cn.prepareStatement(rb.getString("getEstudiantes3"));
			}
			if(datosVO.getEspecialidad()!=0 && datosVO.getSemestre()!=0){
				st=cn.prepareStatement(rb.getString("getEstudiantes4"));
			}
			
			
			st.setLong(i++,datosVO.getInstitucion());
			st.setLong(i++,datosVO.getSede());
		//	st.setLong(i++,datosVO.getJornada());
			st.setLong(i++,datosVO.getMetodologia());
			
			if(datosVO.getEspecialidad()!=0 && datosVO.getSemestre()==0)
				st.setLong(i++,datosVO.getEspecialidad());
			if(datosVO.getEspecialidad()==0 && datosVO.getSemestre()!=0)
				st.setLong(i++,datosVO.getSemestre());
			if(datosVO.getEspecialidad()!=0 && datosVO.getSemestre()!=0){
				st.setLong(i++,datosVO.getEspecialidad());
				st.setLong(i++,datosVO.getSemestre());
			}
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
//				System.out.println("encuentra estudiantes");
				estudianteVO=new EstudianteVO();
				estudianteVO.setCodigo(rs.getInt(i++));
				estudianteVO.setDocumento(rs.getString(i++));
				estudianteVO.setNombre(rs.getString(i++)+" "+rs.getString(i++));
				estudianteVO.setCodTutor(rs.getInt(i++));
				estudianteVO.setNomTutor(rs.getString(i++)+" "+rs.getString(i++));
				l.add(estudianteVO);
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
		return l;
	}
   public EspecialidadVO[] getListaEspecialidad(String insti){
		EspecialidadVO[] especialidad=null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List la=new ArrayList();
		try{
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEspecialidad"));
			ps.setString(posicion++,insti);
			posicion = 1;
			rs = ps.executeQuery();
			EspecialidadVO a=null;
			while(rs.next()){
				a=new EspecialidadVO();
				posicion = 1;
				a.setCodigo(rs.getInt(posicion++));
				a.setNombre(rs.getString(posicion++));
				la.add(a);
			}			
			rs.close();
			ps.close();
			if(!la.isEmpty()){
				int i=0;
				especialidad=new EspecialidadVO[la.size()];
				Iterator iterator =la.iterator();
				while (iterator.hasNext())
					especialidad[i++]=(EspecialidadVO)(iterator.next());
			}
		}catch(SQLException sqle){sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error Posible problema: "+sqle);
		}catch(Exception sqle){sqle.printStackTrace();
			setMensaje("Error Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){inte.printStackTrace();}
		}
		return especialidad;
	}

   public List getAjaxDocente(long inst,int sede, int jornada){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		DocenteVO lp=null;
		int i=0;
		try{
			if(inst==0 || sede==0 || jornada==0 ){return null;}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxDocente"));
			i=1;
			st.setLong(i++,inst);
			st.setLong(i++,sede);
			st.setLong(i++,jornada);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				
				lp=new DocenteVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++)+" "+rs.getString(i++));
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
