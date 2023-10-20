/**
 * 
 */
package siges.observacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.observacion.vo.ObservacionAsignaturaVO;
import siges.observacion.vo.ObservacionEstudianteVO;
import siges.observacion.vo.ObservacionGrupoVO;
import siges.observacion.vo.ObservacionPeriodoVO;
import siges.observacion.vo.ObservacionVO;

/**
 * 25/11/2007 
 * @author Latined
 * @version 1.2
 */
public class ObservacionDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c
	 */
	public ObservacionDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("siges.observacion.bundle.observacion");
	}

	public List getAllPeriodo(){
		List lista=new ArrayList();
		ItemVO itemVO=null;
		try{
			itemVO=new ItemVO(); itemVO.setCodigo(1); itemVO.setNombre("Primero"); lista.add(itemVO);
			itemVO=new ItemVO(); itemVO.setCodigo(2); itemVO.setNombre("Segundo"); lista.add(itemVO);
			itemVO=new ItemVO(); itemVO.setCodigo(3); itemVO.setNombre("Tercero"); lista.add(itemVO);
			itemVO=new ItemVO(); itemVO.setCodigo(4); itemVO.setNombre("Cuarto"); lista.add(itemVO);
			itemVO=new ItemVO(); itemVO.setCodigo(5); itemVO.setNombre("Informe final"); lista.add(itemVO);
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		return lista;
	}

	public List getAllObservacionPeriodo(ObservacionPeriodoVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List lista=new ArrayList();
		ObservacionVO observacionVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getAllObservacionPeriodo."+observacion.getObsPeriodo()));
			i=1;
			pst.setLong(i++,observacion.getObsInstitucion());
			pst.setLong(i++,getVigenciaNumerico());
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				observacionVO=new ObservacionVO();
				observacionVO.setCodigo(rs.getLong(i++));
				observacionVO.setNombre(rs.getString(i++));
				observacionVO.setObservacion(rs.getString(i++));
				lista.add(observacionVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lista;
	}
	
	public void guardarObservacionPeriodo(ObservacionPeriodoVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int posicion=0;
		boolean existe=false;
		long codigo;
		try{
			String codigos[]=observacion.getObsJerarquia();
			String observaciones[]=observacion.getObsObservacion();
			cn=cursor.getConnection();
			if(codigos!=null){
				for(int i=0;i<codigos.length;i++){
					codigo=Long.parseLong(codigos[i]);
					existe=false;
					pst=cn.prepareStatement(rb.getString("validarObservacionPeriodo"));
					posicion=1;
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					rs=pst.executeQuery();
					if(rs.next()){
						existe=true;
					}
					rs.close();
					pst.close();
					if(existe){
						pst=cn.prepareStatement(rb.getString("actualizarObservacionPeriodo."+observacion.getObsPeriodo()));
					}else{
						pst=cn.prepareStatement(rb.getString("ingresarObservacionPeriodo."+observacion.getObsPeriodo()));
					}
					posicion=1;
					if(observaciones==null || observaciones.length<codigos.length || observaciones[i]==null  || observaciones[i].trim().length()==0)pst.setNull(posicion++,Types.VARCHAR);
					else pst.setString(posicion++,observaciones[i]);
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					pst.executeUpdate();
					pst.close();
				}
				//Borrar 
				pst=cn.prepareStatement(rb.getString("eliminarObservacionPeriodo"));
				posicion=1;
				pst.setLong(posicion++,observacion.getObsInstitucion());
				pst.setLong(posicion++,getVigenciaNumerico());
				pst.executeUpdate();
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	public List getSede(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getSede"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new ItemVO();
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
	public List getJornada(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			//System.out.println("Calculo de jor"+inst+"::"+sede+"::"+jor);
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getJornada"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new ItemVO();
				lp.setPadre(rs.getInt(i++));
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

	public List getAllObservacionGrupo(ObservacionGrupoVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List lista=new ArrayList();
		ObservacionVO observacionVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getAllObservacionGrupo."+observacion.getObsPeriodo()));
			i=1;
			pst.setLong(i++,observacion.getObsInstitucion());
			pst.setLong(i++,observacion.getObsSede());
			pst.setLong(i++,observacion.getObsJornada());
			pst.setLong(i++,observacion.getObsMetodologia());
			pst.setLong(i++,getVigenciaNumerico());
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				observacionVO=new ObservacionVO();
				observacionVO.setCodigo(rs.getLong(i++));
				observacionVO.setNombre(rs.getString(i++));
				observacionVO.setObservacion(rs.getString(i++));
				lista.add(observacionVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lista;
	}

	public void guardarObservacionGrupo(ObservacionGrupoVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int posicion=0;
		boolean existe=false;
		long codigo;
		try{
			String codigos[]=observacion.getObsJerarquia();
			String observaciones[]=observacion.getObsObservacion();
			cn=cursor.getConnection();
			if(codigos!=null){
				for(int i=0;i<codigos.length;i++){
					codigo=Long.parseLong(codigos[i]);
					existe=false;
					pst=cn.prepareStatement(rb.getString("validarObservacionGrupo"));
					posicion=1;
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					rs=pst.executeQuery();
					if(rs.next()){
						existe=true;
					}
					rs.close();
					pst.close();
					if(existe){
						pst=cn.prepareStatement(rb.getString("actualizarObservacionGrupo."+observacion.getObsPeriodo()));
					}else{
						pst=cn.prepareStatement(rb.getString("ingresarObservacionGrupo."+observacion.getObsPeriodo()));
					}
					posicion=1;
					if(observaciones==null || observaciones.length<codigos.length || observaciones[i]==null  || observaciones[i].trim().length()==0)pst.setNull(posicion++,Types.VARCHAR);
					else pst.setString(posicion++,observaciones[i]);
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					pst.executeUpdate();
					pst.close();
				}
				//Borrar 
				pst=cn.prepareStatement(rb.getString("eliminarObservacionGrupo"));
				posicion=1;
				pst.setLong(posicion++,observacion.getObsInstitucion());
				pst.setLong(posicion++,observacion.getObsSede());
				pst.setLong(posicion++,observacion.getObsJornada());
				pst.setLong(posicion++,observacion.getObsMetodologia());
				pst.setLong(posicion++,getVigenciaNumerico());
				pst.executeUpdate();
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	public List getAllObservacionAsignatura(ObservacionAsignaturaVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List lista=new ArrayList();
		ObservacionVO observacionVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			//caluclar la jerarquia de grupo
			pst=cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i=1;
			pst.setLong(i++,observacion.getObsInstitucion());
			pst.setLong(i++,observacion.getObsSede());
			pst.setLong(i++,observacion.getObsJornada());
			pst.setLong(i++,observacion.getObsMetodologia());
			pst.setLong(i++,observacion.getObsGrado());
			pst.setLong(i++,observacion.getObsGrupo());
			rs=pst.executeQuery();
			if(rs.next()){
				observacion.setObsJerarquiaGrupo(rs.getLong(1));
			}
			rs.close();
			pst.close();
			System.out.println("observacion.getObsPeriodo() " + observacion.getObsPeriodo());
			pst=cn.prepareStatement(rb.getString("getAllObservacionAsignatura."+observacion.getObsPeriodo() ));
			i=1;
			pst.setLong(i++,observacion.getObsJerarquiaGrupo());
			pst.setLong(i++,observacion.getObsInstitucion());
			pst.setLong(i++,observacion.getObsMetodologia());
			pst.setLong(i++,getVigenciaNumerico());
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				observacionVO=new ObservacionVO();
				observacionVO.setCodigo(rs.getLong(i++));
				observacionVO.setNombre(rs.getString(i++));
				observacionVO.setObservacion(rs.getString(i++));
				lista.add(observacionVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lista;
	}

	public void guardarObservacionAsignatura(ObservacionAsignaturaVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int posicion=0;
		boolean existe=false;
		long codigo;
		try{
			String codigos[]=observacion.getObsAsignatura();
			String observaciones[]=observacion.getObsObservacion();
			cn=cursor.getConnection();
			if(codigos!=null){
				//System.out.println("longitudes: "+codigos.length+"//"+observaciones.length);
				for(int i=0;i<codigos.length;i++){
					codigo=Long.parseLong(codigos[i]);
					existe=false;
					pst=cn.prepareStatement(rb.getString("validarObservacionAsignatura"));
					posicion=1;
					pst.setLong(posicion++,observacion.getObsJerarquiaGrupo());
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					rs=pst.executeQuery();
					if(rs.next()){
						existe=true;
					}
					rs.close();
					pst.close();
					
					System.out.println("observacion.getObsPeriodo() " + observacion.getObsPeriodo());
					if(existe){
						pst=cn.prepareStatement(rb.getString("actualizarObservacionAsignatura."+observacion.getObsPeriodo()));
					}else{
						pst=cn.prepareStatement(rb.getString("ingresarObservacionAsignatura."+observacion.getObsPeriodo()));
					}
					posicion=1;
					if(observaciones==null || observaciones.length<codigos.length || observaciones[i]==null || observaciones[i].trim().length()==0)pst.setNull(posicion++,Types.VARCHAR);
					else pst.setString(posicion++,observaciones[i]);
					pst.setLong(posicion++,observacion.getObsJerarquiaGrupo());
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					pst.executeUpdate();
					pst.close();
				}
				//Borrar 
				pst=cn.prepareStatement(rb.getString("eliminarObservacionAsignatura"));
				posicion=1;
				pst.setLong(posicion++,observacion.getObsJerarquiaGrupo());
				pst.setLong(posicion++,observacion.getObsInstitucion());
				pst.setLong(posicion++,observacion.getObsMetodologia());
				pst.setLong(posicion++,getVigenciaNumerico());
				pst.setLong(posicion++,getVigenciaNumerico());
				pst.executeUpdate();
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
	
	public List getAllGrado(long inst,long met,long sede,long jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getGrado"));
			i=1;
			st.setLong(i++,inst);
			st.setLong(i++,sede);
			st.setLong(i++,jor);
			st.setLong(i++,met);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new ItemVO();
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

	public List getAllGrupo(long inst,long met,long sede,long jor,long grado){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getGrupo"));
			i=1;
			st.setLong(i++,inst);
			st.setLong(i++,sede);
			st.setLong(i++,jor);
			st.setLong(i++,met);
			st.setLong(i++,grado);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new ItemVO();
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

	public List getMetodologia(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getMetodologia"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new ItemVO();
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
	
	//Observacion estudiante
	
 
	public List getAllObservacionEstudiante(ObservacionEstudianteVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List lista=new ArrayList();
		ObservacionVO observacionVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			//caluclar la jerarquia de grupo
			pst=cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i=1;
			pst.setLong(i++,observacion.getObsInstitucion());
			pst.setLong(i++,observacion.getObsSede());
			pst.setLong(i++,observacion.getObsJornada());
			pst.setLong(i++,observacion.getObsMetodologia());
			pst.setLong(i++,observacion.getObsGrado());
			pst.setLong(i++,observacion.getObsGrupo());
			rs=pst.executeQuery();
			if(rs.next()){
				observacion.setObsJerarquia(rs.getLong("G_JERCODIGO"));
				System.out.println("observacion.setObsJerarquiaGrupo( " + observacion.getObsJerarquia());
			}
			rs.close();
			pst.close();
			System.out.println(rb.getString("getAllObservacionEstudiante."+observacion.getObsPeriodo()) + rb.getString("ordenObsEst."+observacion.getObsOrden()));
			pst=cn.prepareStatement(rb.getString("getAllObservacionEstudiante."+observacion.getObsPeriodo()) + rb.getString("ordenObsEst."+observacion.getObsOrden()));
			i=1;
			pst.setLong(i++,observacion.getObsJerarquia());
			//pst.setLong(i++,observacion.getObsInstitucion());
			//pst.setLong(i++,observacion.getObsMetodologia());
			pst.setLong(i++,getVigenciaNumerico());
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				observacionVO=new ObservacionVO();
				observacionVO.setCodigo(rs.getLong(i++));
				observacionVO.setApellido(rs.getString(i++));
				observacionVO.setNombre(rs.getString(i++));
				observacionVO.setObservacion(rs.getString(i++));
				lista.add(observacionVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lista;
	}
	
	

	public void guardarObservacionEstudiante(ObservacionEstudianteVO observacion){
		Connection cn=null;
		PreparedStatement pst=null;
		int posicion=0;
		long codigo;
		try{
			String codigos[]=observacion.getObsEstudiante();
			String observaciones[]=observacion.getObsObservacion();
			cn=cursor.getConnection();
			if(codigos!=null){
				System.out.println("longitudes: "+codigos.length+"//"+observaciones.length);
				for(int i=0;i<codigos.length;i++){
					codigo=Long.parseLong(codigos[i]);
					 
					/*pst=cn.prepareStatement(rb.getString("guardarObservacionEstudiante"));
					posicion=1;
					pst.setLong(posicion++,observacion.getObsJerarquiaGrupo());
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					rs=pst.executeQuery();
					if(rs.next()){
						existe=true;
					}*/
					pst=cn.prepareStatement(rb.getString("actualizarObservacionEstudiante."+observacion.getObsPeriodo()));
					posicion = 1;
					pst.setString(posicion++,observaciones[i]);
					pst.setLong(posicion++,observacion.getObsJerarquia());
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					int cant = pst.executeUpdate();
			
					pst.close();
					if(cant == 0){
						pst=cn.prepareStatement(rb.getString("ingresarObservacionEstudiante."+observacion.getObsPeriodo()));
					
					posicion=1;
					if(observaciones==null 
							 || observaciones.length<codigos.length 
							 || observaciones[i]==null 
							 || observaciones[i].trim().length()==0)
					  {	
						pst.setNull(posicion++,Types.VARCHAR);
					 }else{
						pst.setString(posicion++,observaciones[i]);
					  }	
					pst.setLong(posicion++,observacion.getObsJerarquia());
					pst.setLong(posicion++,codigo);
					pst.setLong(posicion++,getVigenciaNumerico());
					pst.executeUpdate();
					pst.close();
					
					}
				}
				//Borrar 
				pst=cn.prepareStatement(rb.getString("eliminarObservacionEstudiante"));
				posicion=1;
				
				
				pst.setLong(posicion++,observacion.getObsInstitucion());
				System.out.println("observacion.getObsInstitucion() " + observacion.getObsInstitucion());
				pst.setLong(posicion++,observacion.getObsSede());
				System.out.println("observacion.getObsSede() " + observacion.getObsSede());
				pst.setLong(posicion++,observacion.getObsJornada());
				System.out.println("observacion.getObsJornada() " + observacion.getObsJornada());
				pst.setLong(posicion++,observacion.getObsMetodologia());
				System.out.println("observacion.getObsMetodologia() " + observacion.getObsMetodologia());
				pst.setLong(posicion++,observacion.getObsGrado());
System.out.println("observacion.getObsGrado() " + observacion.getObsGrado());
				pst.setLong(posicion++,observacion.getObsGrupo());
				System.out.println("observacion.getObsGrupo() " + observacion.getObsGrupo());
				pst.setLong(posicion++,getVigenciaNumerico());
				System.out.println("getVigenciaNumerico() " + getVigenciaNumerico());
				pst.executeUpdate();
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
}
