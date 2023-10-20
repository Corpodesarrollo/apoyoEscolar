/**
 * 
 */
package siges.evaluacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.parametro.vo.VigenciaPoaVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.evaluacion.beans.EvaluacionEstudiante;
import siges.evaluacion.beans.FiltroBeanEvaluacion;
import siges.evaluacion.beans.FiltroComportamiento;
import siges.exceptions.InternalErrorException;
import siges.importar.beans.UrlImportar;
import siges.login.beans.Login;

/**
 * 1/09/2007 
 * @author Latined
 * @version 1.2
 */
public class Evaluacion2DAO extends Dao{

	public Evaluacion2DAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("siges.evaluacion.bundle.evaluacion2");
	}

	public List getAllGrado(long inst, int sede,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO itemVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAllGrado"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				itemVO=new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));
				l.add(itemVO);
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

	public List getAllGradoDimension(long inst, int sede,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO itemVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAllGradoDimension"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				itemVO=new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));
				l.add(itemVO);
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
	
	public List getAllGrupo(long inst, int sede,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO itemVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAllGrupo"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sede);
			st.setInt(i++,jor);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				itemVO=new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));//gra
				itemVO.setPadre2(rs.getString(i++));//meto
				l.add(itemVO);
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
	
	
	public List getEstudianteComportamiento(FiltroComportamiento filtro, long tipoEval){
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
			st=cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,filtro.getFilSede());
			st.setInt(i++,filtro.getFilJornada());
			st.setInt(i++,filtro.getFilMetodologia());
			st.setInt(i++,filtro.getFilGrado());
			st.setInt(i++,filtro.getFilGrupo());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilJerarquia(rs.getLong(1));
			}else{
				//System.out.println("Jerarquia no encontrada");
				throw new Exception("Jerarquia no encontrada");
			}
			rs.close();
			st.close();
			//System.out.println("1.))"+"getEstudianteComportamiento."+filtro.getFilOrden()+"."+filtro.getFilPeriodo());
			//System.out.println("2."+filtro.getFilJerarquia());
			st=cn.prepareStatement(rb.getString("getEstudianteComportamiento."+filtro.getFilOrden()+"."+filtro.getFilPeriodo()));
			i=1;
			st.setLong(i++,tipoEval);
			st.setLong(i++,filtro.getFilJerarquia());
			st.setLong(i++,getVigenciaNumerico());
			rs=st.executeQuery();
			int index=1;
			while(rs.next()){
				i=1;
				estudiante=new EvaluacionEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				//estudiante.setEvalNota(rs.getInt(i++));
				estudiante.setEvalNota_(rs.getString(i++));
				estudiante.setEvalObservacion(rs.getString(i++));
				l.add(estudiante);
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
	
	public List getEstudianteComportamientoPlantilla(FiltroComportamiento filtro){
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
			st=cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,filtro.getFilSede());
			st.setInt(i++,filtro.getFilJornada());
			st.setInt(i++,filtro.getFilMetodologia());
			st.setInt(i++,filtro.getFilGrado());
			st.setInt(i++,filtro.getFilGrupo());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilJerarquia(rs.getLong(1));
			}else{
				//System.out.println("Jerarquia no encontrada");
				throw new Exception("Jerarquia no encontrada");
			}
			rs.close();
			st.close();
			//System.out.println("1.))"+"getEstudianteComportamiento."+filtro.getFilOrden()+"."+filtro.getFilPeriodo());
			//System.out.println("2."+filtro.getFilJerarquia());
			st=cn.prepareStatement(rb.getString("getEstudianteComportamientoPlantilla."+filtro.getFilOrden()+"."+filtro.getFilPeriodo()));
			i=1;
			st.setLong(i++,filtro.getFilJerarquia());
			st.setLong(i++,getVigenciaNumerico());
			rs=st.executeQuery();
			int index=1;
			while(rs.next()){
				i=1;
				estudiante=new EvaluacionEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				estudiante.setEvalNotaCon(rs.getString(i++));
				estudiante.setEvalObservacion(rs.getString(i++));
				l.add(estudiante);
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
	
	public List getEscalaComportamiento(){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO itemVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getEscalaComportamiento"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				itemVO=new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre2(rs.getString(i++));
				l.add(itemVO);
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
	//

	public boolean insertarEvalComportamiento(FiltroComportamiento filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null,st2=null;
		ResultSet rs=null;
		String param[]=null;
		int i=0;
		long jerarquia=0;
		long codigo=0;
		double nota=0.0;
		String obs=null;
		String sql="",sql2="";
		long valores[]=null;
		try{
			String [] eval=filtro.getEval();
			long vig=getVigenciaNumerico();
			jerarquia=filtro.getFilJerarquia();
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//System.out.println("EVAL_COMP: ENTRO A GUARDAR");
			if(eval!=null){
				//System.out.println("EVAL_COMP: EVAL NO ES NULL");
				valores=new long[eval.length];
				//System.out.println("Valor de jerarquia de grupo="+filtro.getFilJerarquia());
				st=cn.prepareStatement(rb.getString("ingresarEstudianteComportamiento."+filtro.getFilPeriodo()));
				st2=cn.prepareStatement(rb.getString("actualizarEstudianteComportamiento."+filtro.getFilPeriodo()));
				st.clearBatch(); st2.clearBatch();
				int indice=0;
				for(int j=0;j<eval.length;j++){
					i=1;
					//System.out.println("Eval="+eval[j]);
					param=eval[j].replace('|','&').split("&");
					//System.out.println("param="+param.length);
					if(param!=null && param.length>1){
						codigo=Long.parseLong(param[0]);
//						nota=Double.parseDouble(param[1]);
						//System.out.println("EVAL_COMP: CODIGO:"+codigo+"   NOTA: "+nota);
//						if(param.length==3) obs=param[2]; else obs=null;
						if(param.length==2) obs=param[1]; else obs=null;
						valores[indice++]=codigo;
						sql2+="?,";
						if(!existeEstudianteComp(cn,jerarquia,codigo)){//ingresar
							//System.out.println("EVAL_COMP: INGRESAR JERAR: "+jerarquia+"  CODIGOEST: "+codigo+"  VIG:"+vig+"   nota: "+nota);
							st.setLong(i++,jerarquia);
							st.setLong(i++,codigo);  
							st.setLong(i++,vig);
							st.setDouble(i++,nota);
							if(obs==null)st.setNull(i++,Types.VARCHAR);
							else st.setString(i++,obs);
							st.addBatch();
						}else{//actualizar
							//System.out.println("EVAL_COMP: ACTUALIZAR JERAR: "+jerarquia+"  CODIGOEST: "+codigo+"  VIG:"+vig+"   nota: "+nota);
							st2.setDouble(i++,nota);
							if(obs==null)st2.setNull(i++,Types.VARCHAR);
							else st2.setString(i++,obs);
							st2.setLong(i++,jerarquia);
							st2.setLong(i++,codigo);
							st2.setLong(i++,vig);
							st2.addBatch();
						}
					}
				}
				st.executeBatch();
				st2.executeBatch();
				st.close();
				st2.close();
				if(sql2.length()>0){
					sql2=sql2.substring(0,sql2.length()-1);
					sql=rb.getString("getCountEstudianteComportamiento.1."+filtro.getFilPeriodo())+sql2+rb.getString("getCountEstudianteComportamiento.2");
				}else{
					sql=rb.getString("getCountEstudianteComportamiento.3."+filtro.getFilPeriodo());
				}
				st=cn.prepareStatement(sql);
				i=1;
				st.setLong(i++,jerarquia);
				st.setLong(i++,vig);
				for(int j=0;j<indice;j++){
					st.setLong(i++,valores[j]);
				}
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					if(rs.getInt(2)==0){//se puede eliminar todo
						st2=cn.prepareStatement(rb.getString("eliminarEstudianteComportamiento"));
						st2.setLong(i++,jerarquia);
						st2.setLong(i++,rs.getLong(1));
						st2.setLong(i++,vig);
						st2.executeUpdate();
						st2.close();
					}else{//se actualiza a null porque hay mas de un registro alli
						st2=cn.prepareStatement(rb.getString("actualizarEstudianteComportamiento."+filtro.getFilPeriodo()));
						st2.setNull(i++,Types.VARCHAR);
						st2.setNull(i++,Types.VARCHAR);
						st2.setLong(i++,jerarquia);
						st2.setLong(i++,rs.getLong(1));
						st2.setLong(i++,vig);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	public boolean insertarEvalDimension(FiltroComportamiento filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null,st2=null;
		ResultSet rs=null;
		String param[]=null;
		int i=0;
		long jerarquia=0;
		long codigo=0;
		int nota=0;
		String obs=null;
		String sql="",sql2="";
		long valores[]=null;
		try{
			String [] eval=filtro.getEval();
			String [] eval3=filtro.getEval3();
			String [] eval2=filtro.getEval2();
			long vig=getVigenciaNumerico();
			int dim=filtro.getFilDimension();
			jerarquia=filtro.getFilJerarquia();
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			if(eval!=null){
				valores=new long[eval.length];
				st=cn.prepareStatement(rb.getString("ingresarEstudianteDimension."+filtro.getFilPeriodo()));
				st2=cn.prepareStatement(rb.getString("actualizarEstudianteDimension."+filtro.getFilPeriodo()));
				st.clearBatch(); st2.clearBatch();
				int indice=0;
				for(int j=0;j<eval.length;j++){
					i=1;
					//System.out.println("Eval="+eval[j]);
					param=eval[j].replace('|',':').split(":");
					//System.out.println("Valor de log de param="+param.length);
					if(param!=null && param.length>0){
						codigo=Long.parseLong(param[0]);
						nota=Integer.parseInt(eval2[j]);
						obs=eval3[j];
						
						//8/Oct/2021 John Heredia se inactiva validacion metodo eval.length no permite obtener cuando la tabla solo tiene 1 registro
						//el arreglo de String ya no concatena en el formulario EvaluacionPreescolar2.jsp
						/*
						nota=Integer.parseInt(param[1]);
						if(param.length>=3){
							if(param.length==3){
								obs=param[2];
							}else{
								obs="";
								for(int tt=2;tt<param.length;tt++){
									obs+=param[tt]+":";
								}
							}
						}else{
							obs=null;
						}
						if(nota==-99 && param.length==2){ continue; }
						*/
						valores[indice++]=codigo;
						sql2+="?,";
						if(!existeEstudianteDim(cn,jerarquia,codigo,dim)){//ingresar
							st=cn.prepareStatement(rb.getString("ingresarEstudianteDimension."+filtro.getFilPeriodo()));
							st.setLong(i++,jerarquia);
							st.setLong(i++,codigo);
							st.setInt(i++,dim);
							st.setLong(i++,vig);
							if(nota==-99)st.setNull(i++,Types.VARCHAR);
							else st.setInt(i++,nota);
							if(obs==null)st.setNull(i++,Types.VARCHAR);
							else st.setString(i++,obs);
							st.executeUpdate();
							st.close();
						}else{//actualizar							
							st2=cn.prepareStatement(rb.getString("actualizarEstudianteDimension."+filtro.getFilPeriodo()));
							if(nota==-99) st2.setNull(i++,java.sql.Types.VARCHAR);
							else st2.setInt(i++,nota);
							if(obs==null)st2.setNull(i++,Types.VARCHAR);
							else st2.setString(i++,obs);
							st2.setLong(i++,jerarquia);
							st2.setLong(i++,codigo);
							st2.setInt(i++,dim);
							st2.setLong(i++,vig);
							st2.executeUpdate();
							st2.close();
						}
					}
				}
				if(sql2.length()>0){
					sql2=sql2.substring(0,sql2.length()-1);
					sql=rb.getString("getCountEstudianteDimension.1."+filtro.getFilPeriodo())+sql2+rb.getString("getCountEstudianteDimension.2");
				}else{
					sql=rb.getString("getCountEstudianteDimension.3."+filtro.getFilPeriodo());
				}
				if(st!=null)st.close();
				if(st2!=null)st2.close();
				st=cn.prepareStatement(sql);
				i=1;
				st.setLong(i++,jerarquia);
				st.setInt(i++,dim);
				st.setLong(i++,vig);
				for(int j=0;j<indice;j++){
					st.setLong(i++,valores[j]);
				}
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					if(rs.getInt(2)==0){//se puede eliminar todo
						st2=cn.prepareStatement(rb.getString("eliminarEstudianteDimension"));
						st2.setLong(i++,jerarquia);
						st2.setLong(i++,rs.getLong(1));
						st2.setInt(i++,dim);
						st2.setLong(i++,vig);
						st2.executeUpdate();
						st2.close();
					}else{//se actualiza a null porque hay mas de un registro alli
						st2=cn.prepareStatement(rb.getString("actualizarEstudianteDimension."+filtro.getFilPeriodo()));
						st2.setNull(i++,Types.VARCHAR);
						st2.setNull(i++,Types.VARCHAR);
						st2.setLong(i++,jerarquia);
						st2.setLong(i++,rs.getLong(1));
						st2.setInt(i++,dim);
						st2.setLong(i++,vig);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
			actualizarCierreDimension(cn,filtro);
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	private void actualizarCierreDimension(Connection cn,FiltroComportamiento filtro)throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		int posicion=0;
		try{
			if (filtro.getFilCerrar()==1) {
				int estado = -1;
				ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"+filtro.getFilPeriodo()));
				ps.clearParameters();
				posicion = 1;
				ps.setLong(posicion++, filtro.getFilJerarquia());
				ps.setInt(posicion++, filtro.getFilDimension());
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, getVigenciaNumerico());
				rs = ps.executeQuery();
				if (rs.next()) 
					estado = rs.getInt(1);
				rs.close();
				ps.close();
				if (estado == -1) ps = cn.prepareStatement(rb.getString("InsertarCierreGrupo"+filtro.getFilPeriodo()));
				if (estado != -1) ps = cn.prepareStatement(rb.getString("ActualizarCierreGrupo"+filtro.getFilPeriodo()));
				if (ps != null){ 
					ps.clearParameters();
					posicion = 1;
					ps.setInt(posicion++, 1);
					ps.setLong(posicion++, filtro.getFilJerarquia());
					ps.setLong(posicion++, filtro.getFilDimension());
					ps.setLong(posicion++, 1);
					ps.setLong(posicion++,getVigenciaNumerico());
					ps.executeUpdate();
				}
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			}catch(InternalErrorException inte){}
		}
	}
	
	private boolean existeEstudianteComp(Connection cn,long grupo,long codigo)throws Exception{
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			st=cn.prepareStatement(rb.getString("existeEstudianteComp"));
			int i=1;
			st.setLong(i++,grupo);
			st.setLong(i++,codigo);
			st.setLong(i++,getVigenciaNumerico());
			rs=st.executeQuery();
			if(rs.next()) return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return false;
	}

	private boolean existeEstudianteDim(Connection cn,long grupo,long codigo,int dimension)throws Exception{
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			st=cn.prepareStatement(rb.getString("existeEstudianteDimension"));
			int i=1;
			st.setLong(i++,grupo);
			st.setLong(i++,codigo);
			st.setInt(i++,dimension);
			st.setLong(i++,getVigenciaNumerico());
			rs=st.executeQuery();
			if(rs.next()) return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return false;
	}
	
	public List getAllDimension(long inst, int sede,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO itemVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAllDimension"));
			i=1;
			//st.setLong(i++,inst);
			rs=st.executeQuery();
			long grado=0;
			while(rs.next()){
				i=1;
				//grado=rs.getInt(1);			
				itemVO=new ItemVO(); itemVO.setCodigo(rs.getInt(1)); itemVO.setNombre(rs.getString(2)); itemVO.setPadre(grado);
				l.add(itemVO);
			
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

	public List getEstudianteDimension(FiltroComportamiento filtro){
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
			st=cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,filtro.getFilSede());
			st.setInt(i++,filtro.getFilJornada());
			st.setInt(i++,filtro.getFilMetodologia());
			st.setInt(i++,filtro.getFilGrado());
			st.setInt(i++,filtro.getFilGrupo());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilJerarquia(rs.getLong(1));
			}
			
			rs.close();
			st.close();
			//System.out.println("1.))"+"getEstudianteDimension."+filtro.getFilOrden()+"."+filtro.getFilPeriodo());
			//System.out.println("2."+filtro.getFilJerarquia());
			st=cn.prepareStatement(rb.getString("getEstudianteDimension."+filtro.getFilOrden()+"."+filtro.getFilPeriodo()));
			i=1;
			st.setLong(i++,filtro.getFilJerarquia());
			st.setInt(i++,filtro.getFilDimension());
			st.setLong(i++,getVigenciaNumerico());
			rs=st.executeQuery();
			int index=1;
			while(rs.next()){
				i=1;
				estudiante=new EvaluacionEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				estudiante.setEvalNota(rs.getInt(i++));
				estudiante.setEvalObservacion(rs.getString(i++));
				l.add(estudiante);
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

	public int getEstadoCierreDim(FiltroComportamiento f) throws Exception{
		int tipo=1;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			System.out.println("EVAL LINEA DIM: GRUPOJERAR:"+f.getFilJerarquia()+"  FILDIM:"+f.getFilDimension()+"  TIPO:"+tipo+"   VIG:"+getVigenciaNumerico());
			ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"+f.getFilPeriodo()));
			posicion = 1;
			ps.setLong(posicion++, f.getFilJerarquia());
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, getVigenciaNumerico());
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			throw new Exception(in.getMessage()); 
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			throw new Exception(sqle.getMessage()); 
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}
	
    public int getEstadoHorarioDim(FiltroComportamiento f,Login l) throws Exception{
    	int posicion = 1;
    	Connection cn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
    		cn = cursor.getConnection();
    		//validacion de seguridad de horarios habilitada
    		long nn;
    		ps = cn.prepareStatement(rb.getString("EstadoLecturaSistema"));
    		rs = ps.executeQuery();
    		if(rs.next()){
    		   return 0;}
    		rs.close();ps.close();
    		//validacion contra Institucion
    		ps = cn.prepareStatement(rb.getString("EstadoLecturaInstitucion"));
    		posicion = 1;
    		ps.setLong(posicion++, Long.parseLong(l.getInstId()));
    		rs = ps.executeQuery();
    		if(rs.next()){
    			return 0;
    		}
    		rs.close();ps.close();
    		//validacion contra horarios
    		long user=Long.parseLong(l.getUsuarioId());
    		ps = cn.prepareStatement(rb.getString("isDocenteHorarioDim"));
    		posicion = 1;
    		ps.setLong(posicion++, f.getFilJerarquia());
    		ps.setLong(posicion++, user);
    		ps.setLong(posicion++, f.getFilDimension());
    		ps.setLong(posicion++, user);
    		ps.setLong(posicion++, f.getFilDimension());
    		ps.setLong(posicion++, user);
    		ps.setLong(posicion++, f.getFilDimension());
    		ps.setLong(posicion++, user);
    		ps.setLong(posicion++, f.getFilDimension());
    		ps.setLong(posicion++, user);
    		ps.setLong(posicion++, f.getFilDimension());
    		ps.setLong(posicion++, user);
    		ps.setLong(posicion++, f.getFilDimension());
    		ps.setLong(posicion++, user);
    		ps.setLong(posicion++, f.getFilDimension());
    		rs = ps.executeQuery();
    		if(rs.next()){
    			return 0;
    		}else{
    			return 1;
    		}
    	} catch (InternalErrorException in) {
    		setMensaje("NO se puede estabecer conexinn con la base de datos: ");
    		throw new Exception(in.getMessage());
    	} catch (SQLException sqle) {
    		setMensaje("Error intentando obtener estado horario area. Posible problema: ");
    		throw new Exception(sqle.getMessage());
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
	
	public void getParametrosComportamiento(FiltroComportamiento filtro){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			switch(filtro.getFilPeriodo()){
				case 1:
					filtro.setFilNombrePeriodo("Primero");
				break;
				case 2:
					filtro.setFilNombrePeriodo("Segundo");
				break;
				case 3:
					filtro.setFilNombrePeriodo("Tercero");
				break;
				case 4:
					filtro.setFilNombrePeriodo("Cuarto");
				break;
				case 5:
					filtro.setFilNombrePeriodo("Informe final");
				break;
			}
			st=cn.prepareStatement(rb.getString("getNombreGrado"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,filtro.getFilMetodologia());
			st.setInt(i++,filtro.getFilGrado());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilNombreGrado(rs.getString(1));
			}
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("getNombreGrupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,filtro.getFilSede());
			st.setInt(i++,filtro.getFilJornada());
			st.setInt(i++,filtro.getFilMetodologia());
			st.setInt(i++,filtro.getFilGrado());
			st.setInt(i++,filtro.getFilGrupo());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilNombreGrupo(rs.getString(1));
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
    }

	public UrlImportar insertarEvalComportamiento(FiltroComportamiento filtro,List estudiantes,UrlImportar url) throws Exception{
		Connection cn=null;
		PreparedStatement st=null,st2=null;
		ResultSet rs=null;
		List resultado=new ArrayList();
		int totEst=0,totIngr=0,totAct=0,totElim=0;
		int i=0;
		long jerarquia=0;
		long codigo=0;
		double nota=0.0;
		String obs=null;
		String sql="",sql2="";
		long valores[]=null;
		EvaluacionEstudiante estudiante=null;
		try{
			//String [] eval=filtro.getEval();
			long vig=getVigenciaNumerico();
			jerarquia=filtro.getFilJerarquia();
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			if(estudiantes!=null){
				totEst=estudiantes.size();
				valores=new long[estudiantes.size()];
				st=cn.prepareStatement(rb.getString("ingresarEstudianteComportamiento."+filtro.getFilPeriodo()));
				st2=cn.prepareStatement(rb.getString("actualizarEstudianteComportamiento."+filtro.getFilPeriodo()));
				st.clearBatch(); st2.clearBatch();
				int indice=0;
				for(int j=0;j<estudiantes.size();j++){
					i=1;
					estudiante=(EvaluacionEstudiante)estudiantes.get(j);
					//System.out.println("Eval="+eval[j]);
					//param=eval[j].replace('|',':').split(":");
					if(estudiante!=null && estudiante.getEvalNota()>0){
						codigo=estudiante.getEvalCodigo();
						nota=estudiante.getEvalNota();
						obs=estudiante.getEvalObservacion();
						valores[indice++]=codigo;
						sql2+="?,";
						if(!existeEstudianteComp(cn,jerarquia,codigo)){//ingresar
							st.setLong(i++,jerarquia);
							st.setLong(i++,codigo);
							st.setLong(i++,vig);
							st.setDouble(i++,nota);
							if(obs==null)st.setNull(i++,Types.VARCHAR);
							else st.setString(i++,obs);
							st.addBatch();
							totIngr++;
						}else{//actualizar
							st2.setDouble(i++,nota);
							if(obs==null)st2.setNull(i++,Types.VARCHAR);
							else st2.setString(i++,obs);
							st2.setLong(i++,jerarquia);
							st2.setLong(i++,codigo);
							st2.setLong(i++,vig);
							st2.addBatch();
							totAct++;
						}
					}
				}
				st.executeBatch();
				st2.executeBatch();
				st.close();
				st2.close();
				//System.out.println("cantidad de indice="+indice);
				if(sql2.length()>0){
					sql2=sql2.substring(0,sql2.length()-1);
					sql=rb.getString("getCountEstudianteComportamiento.1."+filtro.getFilPeriodo())+sql2+rb.getString("getCountEstudianteComportamiento.2");
				}else{
					sql=rb.getString("getCountEstudianteComportamiento.3."+filtro.getFilPeriodo());
				}
				st=cn.prepareStatement(sql);
				i=1;
				st.setLong(i++,jerarquia);
				st.setLong(i++,vig);
				for(int j=0;j<indice;j++){
					st.setLong(i++,valores[j]);
				}
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					if(rs.getInt(2)==0){//se puede eliminar todo
						st2=cn.prepareStatement(rb.getString("eliminarEstudianteComportamiento"));
						st2.setLong(i++,jerarquia);
						st2.setLong(i++,rs.getLong(1));
						st2.setLong(i++,vig);
						st2.executeUpdate();
						st2.close();
						totElim++;
					}else{//se actualiza a null porque hay mas de un registro alli
						st2=cn.prepareStatement(rb.getString("actualizarEstudianteComportamiento."+filtro.getFilPeriodo()));
						st2.setNull(i++,Types.VARCHAR);
						st2.setNull(i++,Types.VARCHAR);
						st2.setLong(i++,jerarquia);
						st2.setLong(i++,rs.getLong(1));
						st2.setLong(i++,vig);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
			ItemVO itemVO=new ItemVO();
			//total estdiantes
			itemVO.setNombre("Total estudiantes:");
			itemVO.setCodigo(totEst);
			resultado.add(itemVO);
			//total ingresados
			itemVO=new ItemVO();
			itemVO.setNombre("Total ingresados:");
			itemVO.setCodigo(totIngr);
			resultado.add(itemVO);
			//total actualizados
			itemVO=new ItemVO();
			itemVO.setNombre("Total actualizados:");
			itemVO.setCodigo(totAct);
			resultado.add(itemVO);
			//total eliminados
			itemVO=new ItemVO();
			itemVO.setNombre("Total eliminados:");
			itemVO.setCodigo(totElim);
			resultado.add(itemVO);
			url.setResultado(resultado);
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return url;
	}

	public List getAllMetodologia(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO itemVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAllMetodologia"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				itemVO=new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				l.add(itemVO);
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
	
	
	public int getNivelGrado(int grado) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getNivelGrado"));
			st.setInt(i++,grado);
			rs=st.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return 0;
	}
	
}
