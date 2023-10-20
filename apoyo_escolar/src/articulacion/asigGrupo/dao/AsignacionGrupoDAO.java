package articulacion.asigGrupo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;


import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.util.Properties;
import articulacion.asigGrupo.vo.AsignacionArtEstGrupoVO;
import articulacion.asigGrupo.vo.AsignaturaVO;
import articulacion.asigGrupo.vo.EspecialidadVO;
import articulacion.asigGrupo.vo.EstudianteArtVO;
import articulacion.asigGrupo.vo.GrupoVO;
import articulacion.asigGrupo.vo.GrupoArtVO;

public class AsignacionGrupoDAO extends Dao {

	public AsignacionGrupoDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("articulacion.asigGrupo.bundle.sentencias");
	}
	
	public boolean eliminarEstudiantesGrupoAsignatura(List listaGruposArt,long asignatura){
		Connection cn=null;
		PreparedStatement st=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("eliminarAsignacion"));
			i=1;
			Iterator iteradorListaGruposArt = listaGruposArt.iterator();
			while(iteradorListaGruposArt.hasNext()){
				i=1;
				GrupoArtVO grupo = (GrupoArtVO)iteradorListaGruposArt.next();
				st.setLong(i++, grupo.getArtGruCodigo());
				//st.setLong(i++, Long.parseLong(estudiante.getArtGrupoCodigo()));
				//st.setLong(i++, asignatura);
				st.addBatch();
			}
			st.executeBatch();
			
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
	
	public boolean asignarEstudiantes(List listaEstudiantesAAsignar,long asignatura) {
		Connection cn=null;
		PreparedStatement st=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st = cn.prepareStatement(rb.getString("asignacionEstudiantes"));
			st.clearBatch();
			Iterator iteradorListaEstudiantesAsignar = listaEstudiantesAAsignar.iterator();
			while(iteradorListaEstudiantesAsignar.hasNext()){
				i=1;
				EstudianteArtVO estudiante = (EstudianteArtVO)iteradorListaEstudiantesAsignar.next();
				st.setLong(i++, estudiante.getEstCodigo());
				st.setLong(i++, Long.parseLong(estudiante.getArtGrupoCodigo()));
				st.setLong(i++, asignatura);
				st.addBatch();
			}
			st.executeBatch();
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
	
	public Collection getMetodoligasInstitucion(String instId,String query){
		try {
			List list = new ArrayList();
			Object [] o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = instId;
			list.add(o);
			return this.getFiltro(rb.getString(query),list);
		} catch (InternalErrorException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public List getEstudiantes(long grupo,int anho){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lEstArt=new ArrayList();
		EstudianteArtVO estArtVO=null;
		int i=0;
		try{
			if(grupo==0 || anho==0){
				return null;
			}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getEstudiantes"));
			i=1;
			st.setLong(i++,grupo);
			st.setInt(i++,anho);
			
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				estArtVO=new EstudianteArtVO();
				estArtVO.setEstCodigo(rs.getLong(i++));
				estArtVO.setEstNombre1(rs.getString(i++));
				estArtVO.setEstNombre2(rs.getString(i++));
				estArtVO.setEstApellido1(rs.getString(i++));
				estArtVO.setEstApellido2(rs.getString(i++));
				lEstArt.add(estArtVO);
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
		return lEstArt;
	}
	
	public List getListaAsignaciones(List grupos){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lAsignacion=new ArrayList();
		AsignacionArtEstGrupoVO asignacionArticulacion=null;
		int i=0;
		try{
			if(grupos != null && grupos.size() <= 0){
				return null;
			}
			cn=cursor.getConnection();
			
			Iterator iteradorListaGrupos = grupos.iterator();
			st=cn.prepareStatement(rb.getString("getListaAsignaciones"));
			while (iteradorListaGrupos.hasNext()){
				GrupoArtVO grupoArticulacion = (GrupoArtVO) iteradorListaGrupos.next();
				//mapaAsignaciones.put(estudianteArticulacion.getEstCodigo(),"");
				st.clearParameters();
				i=1;
				//(st.setLong(i++,codigoEstudiante);
				st.setLong(i++,grupoArticulacion.getArtGruCodigo());
				
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					asignacionArticulacion=new AsignacionArtEstGrupoVO();
					asignacionArticulacion.setArtGruCodigoEstudiante(rs.getLong(i++));
					asignacionArticulacion.setArtGruCodigoGrupo(rs.getLong(i++));
					asignacionArticulacion.setArtGruCodigoAsignatura((rs.getLong(i++)));
					lAsignacion.add(asignacionArticulacion);
				}
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
		return lAsignacion;
	}
	
	public List getAjaxGrupos(long institucion,int sede,int jornada,int grado, int metodologia){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lGrupo=new ArrayList();
		GrupoVO grupo=null;
		int i=0;
		try{
			if(grado==0 || metodologia==0){
				return null;
			}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxGrupos"));
			i=1;
			st.setLong(i++,institucion);
			st.setInt(i++,sede);
			st.setInt(i++,jornada);
			st.setLong(i++,grado);
			st.setLong(i++,metodologia);
			//st.setInt(i++,1);//asignaturas que no son complementarias
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				grupo=new GrupoVO();
				grupo.setGruJerGrupo(rs.getLong(i++));
				grupo.setGruCodigo(rs.getInt(i++));
				grupo.setGruNombre(rs.getString(i++));
				lGrupo.add(grupo);
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
		return lGrupo;
	}
	
	public List getAjaxGruposArticulacion(long institucion,int sede,int jornada, 
			int vigencia, int semestre,long especialidad, long asignatura){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lGrupoArt=new ArrayList();
		GrupoArtVO grupoArt=null;
		int i=0;
		try{
			if(vigencia==0 || semestre==0 || especialidad==0 || asignatura==0){
				return null;
			}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getGruposArticulacion"));
			i=1;
			st.setLong(i++,institucion);
			st.setInt(i++,sede);
			st.setInt(i++,jornada);
			st.setInt(i++,semestre);
			st.setInt(i++,vigencia);
			st.setLong(i++,asignatura);
			st.setLong(i++,especialidad);
			
			//st.setInt(i++,1);//asignaturas que no son complementarias
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				grupoArt=new GrupoArtVO();
				grupoArt.setArtGruCodigo(rs.getLong(i++));
				grupoArt.setArtGruConsecutivo(rs.getLong(i++));
				lGrupoArt.add(grupoArt);
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
		return lGrupoArt;
	}
	
	public List getAsignacionGrupo(long inst,int anho,long esp, int sem){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		AsignaturaVO lp=null;
		int i=0;
		try{
			if(anho==0 || sem==0){
				return null;
			}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,anho);
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

	public List getAjaxAsignatura(long inst,int anho,long esp, int sem){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		AsignaturaVO lp=null;
		int i=0;
		try{
			if(anho==0 || sem==0){
				return null;
			}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,anho);
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
