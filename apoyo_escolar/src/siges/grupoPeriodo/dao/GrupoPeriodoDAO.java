package siges.grupoPeriodo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.evaluacion.beans.NivelEvalVO;
import siges.evaluacion.beans.ParamsVO;
import siges.evaluacion.beans.TipoEvalVO;
import siges.exceptions.InternalErrorException;
import siges.grupoPeriodo.beans.AbrirGrupo;
import siges.grupoPeriodo.beans.CierreVO;

public class GrupoPeriodoDAO  extends Dao{
    public String sentencia;
    public String buscar;
    private ResourceBundle rb;
    private java.text.SimpleDateFormat sdf;
    
    public GrupoPeriodoDAO(Cursor cur){
        super(cur);
        sentencia=null;
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb=ResourceBundle.getBundle("grupoPeriodo");
    }

  	/**
  	*	Funcinn:  Abrir Grupo<br>
  	*	@param	String inst
  	*	@param	AbrirGrupo a
  	*	@return boolean	
  	**/
  	public boolean abrirGrupo(String inst, AbrirGrupo a, long nivelEval){
  		Connection cn=null;
  		PreparedStatement pst=null;
  	  ResultSet rs=null;
  	  long jerGrupo=-1;
  	  long cerrado=-1;
  	  int estado=0;
  		try{			
 	  	  long institucion=Long.parseLong(inst.trim());
 	  	  long sede=Long.parseLong(a.getSede());
 	  	  long jornada=Long.parseLong(a.getJornada());
 	  	  long vigencia=getVigenciaInst(institucion);  	
  			
  			TipoEvalVO tipoEval= getTipoEval(institucion, sede, jornada);
  			
  			cn=cursor.getConnection(); cn.setAutoCommit(false);
  			//traer jerarquia de grupo
  			pst=cn.prepareStatement(rb.getString("jerarquiaGrupo"));
  			pst.clearParameters();
  			int posicion=1;
  			pst.setLong(posicion++,institucion);			
  			pst.setLong(posicion++,Long.parseLong(a.getSede().trim()));
  			pst.setLong(posicion++,Long.parseLong(a.getJornada().trim()));
  			pst.setLong(posicion++,Long.parseLong(a.getMetodologia().trim()));
  			pst.setLong(posicion++,Long.parseLong(a.getGrado().trim()));
  			pst.setLong(posicion++,Long.parseLong(a.getGrupo().trim()));
  			rs=pst.executeQuery();
  			if(rs.next())
  			  jerGrupo=rs.getLong(1);
  			rs.close();
  			pst.close();
  			if(jerGrupo!=-1){
  			  //ver si existe registro de cierre
//  				System.out.println("APOYO: ABRIR GRUPO: GRUPO: "+jerGrupo+"   PERIODO:"+a.getPeriodo()+"   ASIG:"+a.getAsignatura()+"   TIPO APER:"+a.getTipoapertura()+"   VIG: "+vigencia);
  			  pst=cn.prepareStatement(rb.getString("estadoGrupo"+a.getPeriodo()));
  				pst.clearParameters();
  				posicion=1;
  				pst.setLong(posicion++,jerGrupo);			
  				pst.setLong(posicion++,Long.parseLong(a.getAsignatura().trim()));
  				pst.setLong(posicion++,Long.parseLong(a.getTipoapertura().trim()));
  				pst.setLong(posicion++,vigencia);
  				rs=pst.executeQuery();
  				if(rs.next())
  				  cerrado=rs.getLong(1);
  				rs.close();
  				pst.close();
  				if(cerrado!=-1){
  					if(cerrado!=0){
  						pst=cn.prepareStatement(rb.getString("actualizarEstadoGrupo"+a.getPeriodo()));
  						pst.clearParameters();
  						posicion=1;
  						pst.setInt(posicion++,estado);
  						pst.setLong(posicion++,jerGrupo);
  						pst.setLong(posicion++,Long.parseLong(a.getAsignatura().trim()));
  						pst.setLong(posicion++,Long.parseLong(a.getTipoapertura().trim()));
  	  				pst.setLong(posicion++,vigencia);
  						int n=pst.executeUpdate();
  						//System.out.println("Abriendo: "+n);
  						pst.close();
  		  			pst=cn.prepareStatement(rb.getString("ActualizarEstadoPeriodoActualizacion"+a.getPeriodo()));
  		  			pst.clearParameters();
  		  			posicion=1;
  		  			pst.setInt(posicion++,estado);
  		  			pst.setLong(posicion++,institucion);
  		  			pst.setLong(posicion++,sede);
  		  			pst.setLong(posicion++,jornada);
  		  			pst.setLong(posicion++,vigencia);
  		  			pst.executeUpdate();
  		  			pst.close();  						
  				}
  			}
  			}else{
  			 	setMensaje("Jerarquna de grupo no encontrada");
  			 	return false;	
  			}
  			cn.commit();
  			cn.setAutoCommit(true);
  			
  			
  			if(tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_NUM || tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_PORCENTUAL){
				if(tipoEval.getModo_eval()==2){
					
					/*
					if(promedioAsignaturas(vigencia, institucion, sede, jornada, Integer.parseInt(a.getPeriodo()),2,jerGrupo,Long.parseLong(a.getAsignatura().trim()))){
//						System.out.println("PROMEDIO ASIGNATURA: CIERRE PERIODO SATISFACTORIO");
					}else{
//	  					System.out.println("PROMEDIO ASIGNATURA: CIERRE PERIODO ERROR");
	  				}
					
					if(promedioAreasAsignaturas(vigencia, institucion, sede, jornada, Integer.parseInt(a.getPeriodo()),2,jerGrupo,Long.parseLong(a.getAsignatura().trim()))){
//						System.out.println("PROMEDIO AREAS_ASIGNATURA: ABRIR GRUPO SATISFACTORIO");
					}else
//	  					System.out.println("PROMEDIO AREAS_ASIGNATURA: ABRIR GRUPO ERROR");
					
					
					if(promedioAreas(vigencia, institucion, sede, jornada, Integer.parseInt(a.getPeriodo()),2,jerGrupo,Long.parseLong(a.getAsignatura().trim()),nivelEval)){
//						System.out.println("PROMEDIO AREA: ABRIR GRUPO SATISFACTORIO");
		  			}else{
//	  					System.out.println("PROMEDIO AREA: ABRIR GRUPO ERROR");
	  				}*/
					// Se realiza el llamado al nuevo metodo para ejecutar el nuevo procedimiento que consolida el llamado de los tres procedimientos
					calculoPromedioGeneralizado(vigencia, institucion, sede, jornada, Integer.parseInt(a.getPeriodo()), 2, jerGrupo, Long.parseLong(a.getAsignatura().trim()), nivelEval);
				}

  			}
  			
  		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
  		catch(SQLException sqle){
  			try{cn.rollback();}catch(SQLException s){}
  	 		setMensaje("Error intentando abrir grupo. Posible problema: ");
  			switch(sqle.getErrorCode()){
  				default:
  					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
  			}
  			return false;
  		} catch (Exception e) {
			e.printStackTrace();
		}finally{
  			try{
  			OperacionesGenerales.closeResultSet(rs);
  			OperacionesGenerales.closeStatement(pst);
  			OperacionesGenerales.closeConnection(cn);
  			cursor.cerrar();
  			}catch(InternalErrorException inte){}
  		}
  		return true;
  	}

  	/**
  	*	Funcinn:  Cerrar Periodo<br>
  	*	@param	String inst
  	*	@param	String per
  	*	@param	int est
  	*	@param	int tipo
  	*	@return	booolean
  	**/
  	public boolean cerrarPeriodo(CierreVO cierreVO, long nivelEval){
  		Connection cn=null;
  		PreparedStatement pst=null,pst2=null,pst3=null;
  		try{
  		  long inst=Long.parseLong(cierreVO.getCieInst());
  		  long sede=Long.parseLong(cierreVO.getCieSed());
  		  long jor=Long.parseLong(cierreVO.getCieJor());
  		  long per=Long.parseLong(cierreVO.getCiePer());
  		  long est=1;
  		  long tipo=Long.parseLong(cierreVO.getCieEsta());
  		  long vig=getVigenciaInst(inst);
  		  TipoEvalVO tipoEval= getTipoEval(inst, sede, jor);
  			cn=cursor.getConnection(); cn.setAutoCommit(false);			
  			pst=cn.prepareStatement(rb.getString((tipo==-1?"ActualizarEstadoPeriodo"+per:"ActualizarEstadoPeriodoActualizacion"+per)));
  			pst.clearParameters();
  			int posicion=1;
  			pst.setLong(posicion++,est);
  			pst.setLong(posicion++,(inst));
  			pst.setLong(posicion++,(sede));
  			pst.setLong(posicion++,(jor));
  			pst.setLong(posicion++,vig);
  			pst.executeUpdate();
  			int cant=pst.executeUpdate();
  			//System.out.println("cerrando periodo="+cant);
  			cn.commit();
  			cn.setAutoCommit(true);
  			/*if(promedioAreas(vig, inst, sede, jor, (int)per,1,0,0,nivelEval)){
					System.out.println("PROMEDIO AREA: CIERRE PERIODO SATISFACTORIO");
	  		}*/
  			
  		//	if(tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_NUM || tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_PORCENTUAL){
			//	if(tipoEval.getModo_eval()==2){
  			/*
  			if(promedioAsignaturas(vig, inst, sede, jor,(int)per,1,0,0)){
//				System.out.println("PROMEDIO ASIGNATURA: CIERRE PERIODO SATISFACTORIO");
			}else{
//					System.out.println("PROMEDIO ASIGNATURA: CIERRE PERIODO ERROR");
			}
  			
			if(promedioAreasAsignaturas(vig, inst, sede, jor,(int)per,1,0,0)){
//				System.out.println("PROMEDIO AREAS_ASIGNATURA: CIERRE PERIODO SATISFACTORIO");
			}else
//				System.out.println("PROMEDIO AREA_ASIGNATURA: CIERRE PERIODO NO FUE SATISFACTORIO");
			
  			if(promedioAreas(vig, inst, sede, jor, (int)per,1,0,0,nivelEval)){
//					System.out.println("PROMEDIO AREA: CIERRE PERIODO SATISFACTORIO");
			}else{
//				System.out.println("PROMEDIO AREA: CIERRE PERIODO ERROR");
			}  
			*/
  			// Llamado de metodo que llama procedimiento que consolida el llamado de los 3 procedimiento de promedio 
  			calculoPromedioGeneralizado(vig, inst, sede, jor, (int)per, 1, 0, 0, nivelEval);
			//	}
				
  		//	}
  			
  			
  			//Calcular puesto de estudiante
  			if(callPuestoEst(vig, inst, sede, jor,(int)per)){
//  				System.out.println("callPuestoEst: CIERRE PERIODO SATISFACTORIO");
  			}else{
//  				System.out.println("callPuestoEst: CIERRE PERIODO ERROR");
  			}
  		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
  		catch(SQLException sqle){
  			try{cn.rollback();}catch(SQLException s){}
  	 		setMensaje("Error intentando cerrar periodo. Posible problema: ");
  			switch(sqle.getErrorCode()){
  				default:
  					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
  			}
  			return false;
  		}catch (Exception e) {
			e.printStackTrace();
		}finally{
  			try{
  			OperacionesGenerales.closeStatement(pst);
  			OperacionesGenerales.closeConnection(cn);
  			cursor.cerrar();
  			}catch(InternalErrorException inte){}
  		}
  		return true;
  	}

  	
  	/**
  	*	Funcinn:  Cerrar Periodo<br>
  	*	@param	String inst
  	*	@param	String per
  	*	@param	int est
  	*	@param	int tipo
  	*	@return	booolean
  	**/
  	public boolean abrirPeriodo(CierreVO cierreVO, long nivelEval){
  		Connection cn=null;
  		PreparedStatement pst=null,pst2=null,pst3=null;
  		int cant;
  		try{
 	  		long institucion=Long.parseLong(cierreVO.getCieInst());
 	  		long per=Long.parseLong(cierreVO.getCiePer());
 	  		long sede=Long.parseLong(cierreVO.getCieSed());
 	  		long jornada=Long.parseLong(cierreVO.getCieJor());
 	  		int est=0;
 	  		long tipo=Long.parseLong(cierreVO.getCieEsta());
    		long vigencia=getVigenciaInst(institucion);
    		
    		TipoEvalVO tipoEval= getTipoEval(institucion, sede, jornada);
    		
    		//String inst,String per,int est,int tipo
  			cn=cursor.getConnection(); cn.setAutoCommit(false);			
  			pst=cn.prepareStatement(rb.getString("ActualizarEstadoPeriodoActualizacion"+per));
  			pst.clearParameters();
  			int posicion=1;
  			pst.setInt(posicion++,est);
  			pst.setLong(posicion++,institucion);
  			pst.setLong(posicion++,sede);
  			pst.setLong(posicion++,jornada);
  			pst.setLong(posicion++,vigencia);
  			cant=pst.executeUpdate();
  			//System.out.println("abriendo perioodo="+cant);
  			pst.close();
  			
  			pst=cn.prepareStatement(rb.getString("actualizarGruposAsignatura"+per));
  			pst.clearParameters();
  			posicion=1;
  			pst.setInt(posicion++,est);
  			pst.setLong(posicion++,institucion);
  			pst.setLong(posicion++,sede);
  			pst.setLong(posicion++,jornada);
  			pst.setLong(posicion++,vigencia);
  			pst.executeUpdate();
  			cant=pst.executeUpdate();
  			//System.out.println("abriendo grupos="+cant);  			
  			pst.close();
  			
  			pst=cn.prepareStatement(rb.getString("actualizarGruposArea"+per));
  			pst.clearParameters();
  			posicion=1;
  			pst.setInt(posicion++,est);
  			pst.setLong(posicion++,institucion);
  			pst.setLong(posicion++,sede);
  			pst.setLong(posicion++,jornada);
  			pst.setLong(posicion++,vigencia);
  			pst.executeUpdate();
  			cant=pst.executeUpdate();
  			//System.out.println("abriendo grupos="+cant);  			
  			pst.close();
  			
  			pst=cn.prepareStatement(rb.getString("actualizarGruposPreescolar"+per));
  			pst.clearParameters();
  			posicion=1;
  			pst.setInt(posicion++,est);
  			pst.setLong(posicion++,institucion);
  			pst.setLong(posicion++,sede);
  			pst.setLong(posicion++,jornada);
  			pst.setLong(posicion++,vigencia);
  			pst.executeUpdate();
  			cant=pst.executeUpdate();
  			//System.out.println("abriendo grupos="+cant);  			
  			pst.close();
  			
  			cn.commit();
  			cn.setAutoCommit(true);
  			
  			
  			/*
  			if(promedioAsignaturas(vigencia, institucion, sede, jornada, (int)per,2,0,0)){
//				System.out.println("PROMEDIO ASIGNATURA: ABRIR PERIODO SATISFACTORIO");
			}else{
//					System.out.println("PROMEDIO ASIGNATURA: ABRIR PERIODO TOTAL ERROR");
			}
  			
  			if(promedioAreasAsignaturas(vigencia, institucion, sede, jornada,(int)per,2,0,0)){
//				System.out.println("PROMEDIO AREAS_ASIGNATURA: CIERRE PERIODOTOTAL SATISFACTORIO");
			}else
//				System.out.println("PROMEDIO AREA_ASIGNATURA: CIERRE PERIODOTOTAL NO FUE SATISFACTORIO");
  			
  			
  			if(promedioAreas(vigencia, institucion, sede, jornada, (int)per,2,0,0,nivelEval)){
//					System.out.println("PROMEDIO AREA: ABRIR PERIODO  SATISFACTORIO");
				}else{
//					System.out.println("PROMEDIO AREA: ABRIR PERIODO ERROR");
				}
				
				*/
  			// Se realiza el llamado al metodo que llamar el pocedimiento que consolida el llamado de los 3 procedimiento de promedio
  			calculoPromedioGeneralizado(vigencia, institucion, sede, jornada, (int)per, 2, 0, 0, nivelEval);
  			
  		/*	if(tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_NUM || tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_PORCENTUAL){
  				if(tipoEval.getModo_eval()==2){
  	  				
  				}
  					  				
  				}*/
  			
  		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
  		catch(SQLException sqle){
  			try{cn.rollback();}catch(SQLException s){}
  	 		setMensaje("Error intentando abrir periodo. Posible problema: ");
  			switch(sqle.getErrorCode()){
  				default:
  					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
  			}
  			return false;
  		} catch (Exception e) {
			e.printStackTrace();
		}finally{
  			try{
  			OperacionesGenerales.closeStatement(pst);
  			OperacionesGenerales.closeConnection(cn);
  			cursor.cerrar();
  			}catch(InternalErrorException inte){}
  		}
  		return true;
  	}
  	
  	public boolean cerrarPeriodoTotal(CierreVO cierreVO, long nivelEval){
  	  int posicion;
  		Connection cn=null;
  		int n;
  		int estado=1;
  		long tipo;
  		PreparedStatement pst=null,pst2=null,pst3=null;
  			try{
 		  		long institucion=Long.parseLong(cierreVO.getCieInst());
 		  		long sede=Long.parseLong(cierreVO.getCieSed());
 		  		long jor=Long.parseLong(cierreVO.getCieJor());
 		  		long per=Long.parseLong(cierreVO.getCiePer());
  	  		long vigencia=getVigenciaInst(institucion);
  	  		tipo=Long.parseLong(cierreVO.getCieEsta());
  	  		TipoEvalVO tipoEval= getTipoEval(institucion, sede, jor);
  	  	
  				cn=cursor.getConnection(); cn.setAutoCommit(false);			
  				//PARA ASIGNATURAS
  				pst=cn.prepareStatement(rb.getString("actualizarGruposAsignatura"+per));
  				pst.clearParameters();
  				posicion=1;
  				pst.setLong(posicion++,estado);
  				pst.setLong(posicion++,institucion);
  				pst.setLong(posicion++,sede);
  				pst.setLong(posicion++,jor);
  				pst.setLong(posicion++,vigencia);
  				n=pst.executeUpdate();
    			//System.out.println("cerrando grupos="+n);
    			pst.close();
  				
  				pst=cn.prepareStatement(rb.getString("cierre.cerrarTodosAsignaturas"+per));
  				pst.clearParameters();
  				posicion=1;
  				pst.setLong(posicion++,vigencia);
  				pst.setLong(posicion++,estado);
  				pst.setLong(posicion++,institucion);
  				pst.setLong(posicion++,sede);
  				pst.setLong(posicion++,jor);
  				pst.setLong(posicion++,vigencia);
  				pst.setLong(posicion++,vigencia);
  				n=pst.executeUpdate();
    			//System.out.println("cerrando grupos="+n);
    			pst.close();
    			//PARA AREAS
  				pst=cn.prepareStatement(rb.getString("actualizarGruposArea"+per));
  				pst.clearParameters();
  				posicion=1;
  				pst.setLong(posicion++,estado);
  				pst.setLong(posicion++,institucion);
  				pst.setLong(posicion++,sede);
  				pst.setLong(posicion++,jor);
  				pst.setLong(posicion++,vigencia);
  				n=pst.executeUpdate();
    			//System.out.println("cerrando grupos="+n);
    			pst.close();
    			pst=cn.prepareStatement(rb.getString("cierre.cerrarTodosAreas"+per));
  				pst.clearParameters();
  				posicion=1;
  				pst.setLong(posicion++,vigencia);
  				pst.setLong(posicion++,estado);
  				pst.setLong(posicion++,institucion);
  				pst.setLong(posicion++,sede);
  				pst.setLong(posicion++,jor);
  				pst.setLong(posicion++,vigencia);
  				pst.setLong(posicion++,vigencia);
  				n=pst.executeUpdate();
    			//System.out.println("cerrando grupos2="+n);
    			pst.close();
  				//preescolar
  				pst=cn.prepareStatement(rb.getString("actualizarGruposPreescolar"+per));
  				pst.clearParameters();
  				posicion=1;
  				pst.setLong(posicion++,estado);
  				pst.setLong(posicion++,institucion);
  				pst.setLong(posicion++,sede);
  				pst.setLong(posicion++,jor);
  				pst.setLong(posicion++,vigencia);
  				n=pst.executeUpdate();
    			//System.out.println("cerrando grupos pree="+n);
    			pst.close();
  				pst=cn.prepareStatement(rb.getString("cierre.cerrarTodosPreescolar"+per));
  				pst.clearBatch();
   				posicion=1;
   				pst.setLong(posicion++,vigencia);
   				pst.setLong(posicion++,estado);
   				pst.setLong(posicion++,institucion);
   				pst.setLong(posicion++,sede);
   				pst.setLong(posicion++,jor);
   				pst.setLong(posicion++,vigencia);
   				pst.addBatch();  					
  				n=pst.executeBatch().length;  				
  				//System.out.println("Inserto pree"+n);
  				pst.close();
  				pst=cn.prepareStatement(rb.getString((tipo==-1?"ActualizarEstadoPeriodo"+per:"ActualizarEstadoPeriodoActualizacion"+per)));
  				pst.clearParameters();
  				posicion=1;
    			pst.setInt(posicion++,estado);
    			pst.setLong(posicion++,institucion);
  				pst.setLong(posicion++,sede);
  				pst.setLong(posicion++,jor);
    			pst.setLong(posicion++,vigencia);
  				n=pst.executeUpdate();
  				//System.out.println("Cerro "+n);				
  				cn.commit();
  				cn.setAutoCommit(true);
  				
  				/*if(promedioAreas(vigencia, institucion, sede, jor, (int)per,1,0,0,nivelEval)){
  					System.out.println("PROMEDIO AREA: CIERRE PERIODO SATISFACTORIO");
  	  			}*/
  				
  			//	if(tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_NUM || tipoEval.getCod_tipo_eval()==ParamsVO.TIPO_EVAL_ASIG_PORCENTUAL){
				//	if(tipoEval.getModo_eval()==2){
  				/*
  				if(promedioAsignaturas(vigencia, institucion, sede, jor,(int)per,1,0,0)){
//					System.out.println("PROMEDIO ASIGNATURA: CIERRE PERIODO SATISFACTORIO");
				}else{
//  					System.out.println("PROMEDIO ASIGNATURA: CIERRE PERIODO TOTAL ERROR");
  				}
  				
				if(promedioAreasAsignaturas(vigencia, institucion, sede, jor,(int)per,1,0,0)){
//					System.out.println("PROMEDIO AREAS_ASIGNATURA: CIERRE PERIODOTOTAL SATISFACTORIO");
				}else
//					System.out.println("PROMEDIO AREA_ASIGNATURA: CIERRE PERIODOTOTAL NO FUE SATISFACTORIO");
				
				if(promedioAreas(vigencia, institucion, sede, jor, (int)per,1,0,0,nivelEval)){
//  					System.out.println("PROMEDIO AREA: CIERRE PERIODO TOTAL SATISFACTORIO");
  				}else{
//  					System.out.println("PROMEDIO AREA: CIERRE PERIODO TOTAL ERROR");
  				}
  				*/
  				// Se realiza el llamado el procedimiento que consolida el llamado de los 3 procedimiento de promedio
  				calculoPromedioGeneralizado(vigencia, institucion, sede, jor, (int)per, 1, 0, 0, nivelEval);
						/*
		  				*/
					/*}
						}*/		
					
		  			//Calcular puesto de estudiante
		  			if(callPuestoEst(vigencia, institucion, sede, jor,(int)per)){
//		  				System.out.println("callPuestoEst: CIERRE PERIODO SATISFACTORIO");
		  			}else{
//		  				System.out.println("callPuestoEst: CIERRE PERIODO ERROR");
		  			}
  				
  			}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
  			catch(SQLException sqle){
  				sqle.printStackTrace();
  				try{cn.rollback();}catch(SQLException s){}
  		 		setMensaje("Error intentando cerrar periodo. Posible problema: ");
  				switch(sqle.getErrorCode()){
  					default:
  						setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
  				}
  				return false;
  			} catch (Exception e) {
				e.printStackTrace();
			}finally{
  				try{
  				OperacionesGenerales.closeStatement(pst);
  				OperacionesGenerales.closeConnection(cn);
  				cursor.cerrar();
  				}catch(InternalErrorException inte){}
  			}
  			return true;
  		}
  	
  	public Collection getGruposPreescolarAbiertos(String inst,String per){
    		Connection cn=null;
    		PreparedStatement pst=null,pst2=null,pst3=null;
    		ResultSet rs=null;
    		Collection list=null;
    		long institucion=Long.parseLong(inst);
    		try{
    			long vigencia=getVigenciaInst(institucion);
    			cn=cursor.getConnection();			
    			pst=cn.prepareStatement(rb.getString("listaGruposAbiertosPreescolar"+per));
    			pst.clearParameters();
    			int posicion=1;
    			pst.setLong(posicion++,institucion);
    			pst.setLong(posicion++,vigencia);
    			pst.setLong(posicion++,institucion);
    			pst.setLong(posicion++,vigencia);
    			pst.setLong(posicion++,institucion);
    			pst.setLong(posicion++,vigencia);
    			pst.setLong(posicion++,institucion);
    			pst.setLong(posicion++,vigencia);
    			pst.setLong(posicion++,institucion);
    			pst.setLong(posicion++,vigencia);
    			rs=pst.executeQuery();
    			list=getCollection(rs);
    		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
    		catch(SQLException sqle){
    			try{cn.rollback();}catch(SQLException s){}
    	 		setMensaje("Error intentando obtener grupos abiertos pree. Posible problema: ");
    			switch(sqle.getErrorCode()){
    				default:
    					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
    			}
    			return list;
    		} catch (Exception e) {
				e.printStackTrace();
			}finally{
    			try{
    			OperacionesGenerales.closeResultSet(rs);
    			OperacionesGenerales.closeStatement(pst);
    			OperacionesGenerales.closeConnection(cn);
    			cursor.cerrar();
    			}catch(InternalErrorException inte){}
    		}
    		return list;
  	}
  	
  	
  	public Collection getGruposAsignaturaAbiertos(String inst,String per){
    		Connection cn=null;
    		PreparedStatement pst=null,pst2=null,pst3=null;
    		ResultSet rs=null;
    		Collection list=null;
    		long institucion=Long.parseLong(inst);
    		try{
    			cn=cursor.getConnection();
    			//System.out.println(rb.getString("listaGruposAsignaturaAbiertos"+per));
    			pst=cn.prepareStatement(rb.getString("listaGruposAsignaturaAbiertos"+per));
    			pst.clearParameters();
    			int posicion=1;
    			pst.setLong(posicion++,institucion);
    			pst.setLong(posicion++,getVigenciaInst(institucion));
    			pst.setLong(posicion++,getVigenciaInst(institucion));
    			rs=pst.executeQuery();
    			list=getCollection(rs);
    		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
    		catch(SQLException sqle){
    		  sqle.printStackTrace();
    			try{cn.rollback();}catch(SQLException s){}
    	 		setMensaje("Error intentando obtener grupos abiertos. Posible problema: ");
    			switch(sqle.getErrorCode()){
    				default:
    					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
    			}
    			return list;
    		} catch (Exception e) {
				e.printStackTrace();
			}finally{
    			try{
    			OperacionesGenerales.closeResultSet(rs);
    			OperacionesGenerales.closeStatement(pst);
    			OperacionesGenerales.closeConnection(cn);
    			cursor.cerrar();
    			}catch(InternalErrorException inte){}
    		}
    		return list;
  	}
  	
  	public Collection [] getGruposAbiertos(CierreVO cierreVO){
  		Connection cn=null;
  		PreparedStatement pst=null,pst2=null,pst3=null;
  		ResultSet rs=null;
  		Collection []list=new Collection[3];
  		try{
  		  long inst=Long.parseLong(cierreVO.getCieInst());
  		  long sed=Long.parseLong(cierreVO.getCieSed());
  		  long jor=Long.parseLong(cierreVO.getCieJor());
  		  long per=Long.parseLong(cierreVO.getCiePer());
  		  long vig=getVigenciaInst(inst);
  			cn=cursor.getConnection();			
  			pst=cn.prepareStatement(rb.getString("listaGruposAreaAbiertos"+per));
  			pst.clearParameters();
  			int posicion=1;
  			pst.setLong(posicion++,(inst));
  			pst.setLong(posicion++,(sed));
  			pst.setLong(posicion++,(jor));
  			pst.setLong(posicion++,vig);
  			pst.setLong(posicion++,vig);
  			rs=pst.executeQuery();
  			list[0]=getCollection(rs);
  			//
  			pst.close();
  			pst=cn.prepareStatement(rb.getString("listaGruposAsignaturaAbiertos"+per));
  			pst.clearParameters();
  			posicion=1;
  			pst.setLong(posicion++,(inst));
  			pst.setLong(posicion++,(sed));
  			pst.setLong(posicion++,(jor));
  			pst.setLong(posicion++,vig);
  			pst.setLong(posicion++,vig);
  			rs=pst.executeQuery();
  			list[1]=getCollection(rs);
  			//
  			pst.close();
  			pst=cn.prepareStatement(rb.getString("listaGruposAbiertosPreescolar"+per));
  			pst.clearParameters();
  			posicion=1;
  			pst.setLong(posicion++,inst);
  			pst.setLong(posicion++,(sed));
  			pst.setLong(posicion++,(jor));
  			pst.setLong(posicion++,vig);
  			rs=pst.executeQuery();
  			list[2]=getCollection(rs);
  		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
  		catch(SQLException sqle){
  		  sqle.printStackTrace();
  			try{cn.rollback();}catch(SQLException s){}
  	 		setMensaje("Error intentando obtener grupos abiertos. Posible problema: ");
  			switch(sqle.getErrorCode()){
  				default:
  					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
  			}
  			return list;
  		} catch (Exception e) {
			e.printStackTrace();
		}finally{
  			try{
  			OperacionesGenerales.closeResultSet(rs);
  			OperacionesGenerales.closeStatement(pst);
  			OperacionesGenerales.closeConnection(cn);
  			cursor.cerrar();
  			}catch(InternalErrorException inte){}
  		}
  		return list;
	}

  	public Collection getPeriodos(String inst){
  		Connection cn=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		Collection list=null;
  		try{
  			cn=cursor.getConnection();			
  			pst=cn.prepareStatement(rb.getString("listaPeriodosInstitucion"));
  			pst.clearParameters();
  			int posicion=1;
  			pst.setLong(posicion++,Long.parseLong(inst));
  			pst.setInt(posicion++,getVigenciaInst(Long.parseLong(inst)));
  			rs=pst.executeQuery();
  			list=getCollection(rs);
  		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
  		catch(SQLException sqle){
  		  sqle.printStackTrace();
  			try{cn.rollback();}catch(SQLException s){}
  	 		setMensaje("Error intentando obtener periodos. Posible problema: ");
  			switch(sqle.getErrorCode()){
  				default:
  					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
  			}
  			return list;
  		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
  			try{
  			OperacionesGenerales.closeResultSet(rs);
  			OperacionesGenerales.closeStatement(pst);
  			OperacionesGenerales.closeConnection(cn);
  			cursor.cerrar();
  			}catch(InternalErrorException inte){}
  		}
  		return list;
	}
  	public Collection getSedesJornadas(String inst){
    		Connection cn=null;
    		PreparedStatement pst=null;
    		ResultSet rs=null;
    		Collection list=null;
    		try{
    			cn=cursor.getConnection();			
    			pst=cn.prepareStatement(rb.getString("listaSedesJornadas"));
    			pst.clearParameters();
    			int posicion=1;
    			pst.setLong(posicion++,Long.parseLong(inst));
    			rs=pst.executeQuery();
    			list=getCollection(rs);
    		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
    		catch(SQLException sqle){
    		  sqle.printStackTrace();
    			try{cn.rollback();}catch(SQLException s){}
    	 		setMensaje("Error intentando obtener periodos. Posible problema: ");
    			switch(sqle.getErrorCode()){
    				default:
    					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
    			}
    			return list;
    		}finally{
    			try{
    			OperacionesGenerales.closeResultSet(rs);
    			OperacionesGenerales.closeStatement(pst);
    			OperacionesGenerales.closeConnection(cn);
    			cursor.cerrar();
    			}catch(InternalErrorException inte){}
    		}
    		return list;
  	}
  	
  	
  	
  	/**
	*	Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>	
	**/
  	/**
	*	Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>	
	**/
	public boolean promedioAreas(long vig, long inst, long sede, long jor, int per, int accion, long jerGrupo, long asig, long nivEval){
		Connection con=null;
		CallableStatement cstmt=null;
		Collection list = new ArrayList();
		ResultSet rs=null;
		int posicion=1; 		
		
		try{    			      		    
	            	  	
     	  	 con=cursor.getConnection();
    			 /*callable statement*/         	  	         	  	
    			 cstmt=con.prepareCall("{call ACT_PROM_AREA(?,?,?,?,?,?,?,?,?)}");
    			 cstmt.setLong(posicion++,vig);        			 
    			 cstmt.setInt(posicion++,per);
    			 cstmt.setLong(posicion++,inst);
    			 cstmt.setLong(posicion++,sede);
    			 cstmt.setLong(posicion++,jor);
    			 cstmt.setLong(posicion++,jerGrupo);
    			 cstmt.setLong(posicion++,asig);
    			 cstmt.setLong(posicion++,accion);
    			 cstmt.setLong(posicion++,nivEval);
//    			 System.out.println("PROMEDIO AREA: GRUPO CIERRE - sede: "+sede+" Inicia procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());			 
    			 cstmt.executeUpdate();
//    			 System.out.println("PROMEDIO AREA: GRUPO CIERRE - Fin procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());
    			 cstmt.close();
			
		}
		 catch(SQLException e){
			 System.out.println("PROMEDIO AREA:excepcinn sql!");
			   e.printStackTrace();
			  	 
			   return false;
		   }  
		catch(Exception e ){
			System.out.println("PROMEDIO AREA:excepcinn!");
			e.printStackTrace();		  
		  	return false;
  	}finally{
		try{
		    OperacionesGenerales.closeResultSet(rs);
		    OperacionesGenerales.closeCallableStatement(cstmt);
		    OperacionesGenerales.closeConnection(con);
		    }catch(Exception e){}    
		}
return true;
}	
	
	
	public TipoEvalVO getTipoEval(long inst, long sede, long jor) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		long vigencia=0;
		long codigoNivelEval=0;
		int tipoEval=0;
		NivelEvalVO nivelEval=new NivelEvalVO();
		TipoEvalVO tipoEvalVO=new TipoEvalVO();
		try{
			cn=cursor.getConnection();
			vigencia=getVigenciaNumerico();
			st=cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++,getVigenciaNumerico());
			st.setLong(i++,inst);
			rs=st.executeQuery();
			if(rs.next()){
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i=1;
			//traer parametros de nivel evaluacion
			st=cn.prepareStatement(rb.getString("getParamsNivelEval"));
			st.setLong(i++,codigoNivelEval);
			rs=st.executeQuery();
			i=1;
			if(rs.next()){
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));			
			}
			rs.close();
			st.close();
			
			//TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql=rb.getString("getTipoEval.select");
			if(nivelEval.getEval_sede()==1)
				sql=sql+" "+rb.getString("getTipoEval.sede");
			if(nivelEval.getEval_jornada()==1)
				sql=sql+" "+rb.getString("getTipoEval.jornada");			
			st=cn.prepareStatement(sql);
			i=1;
			st.setLong(i++,getVigenciaNumerico());
			st.setLong(i++,inst);
			
			if(nivelEval.getEval_sede()==1)
				st.setLong(i++,sede);
			if(nivelEval.getEval_jornada()==1)
				st.setLong(i++,jor);
			
			rs=st.executeQuery();
			i=1;
			if(rs.next()){
				tipoEvalVO.setCod_tipo_eval(rs.getLong(i++));
				tipoEvalVO.setEval_min(rs.getDouble(i++));
				tipoEvalVO.setEval_max(rs.getDouble(i++));	
				tipoEvalVO.setModo_eval(rs.getInt(i++));
			}
			rs.close();
			st.close();
			
			
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
		return tipoEvalVO;
	}
	
	
	
	public Collection getAreasInst(long inst) {
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;		
		long codigoNivelEval=0;
		int tipoEval=0;
		NivelEvalVO nivelEval=new NivelEvalVO();
		TipoEvalVO tipoEvalVO=new TipoEvalVO();
		Collection col=null;
		try{
			cn=cursor.getConnection();
			long vigencia=getVigenciaInst(inst);
			st=cn.prepareStatement(rb.getString("filtroAreaInstitucion"));
			st.setLong(i++,inst);
			st.setLong(i++,vigencia);
			st.setLong(i++,inst);
			rs=st.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			col = new ArrayList();
			Object[] o;
			i = 0;
			int f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				col.add(o);
			}
			return col;
				
		}catch(SQLException sqle){
			sqle.printStackTrace();
			//throw new Exception("Error de datos: "+sqle.getMessage());
			return null;
		}catch(Exception sqle){
			sqle.printStackTrace();
			//throw new Exception("Error interno: "+sqle.getMessage());
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		//return col;
	}
	
	
	
	/**
	*	Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>	
	**/
	public boolean promedioAsignaturas(long vig, long inst, long sede, long jor, int per, int accion, long jerGrupo, long asig){
		Connection con=null;
		CallableStatement cstmt=null;
		Collection list = new ArrayList();
		ResultSet rs=null;
		int posicion=1; 		
		
		try{    			      		    
	            	  	
     	  	 con=cursor.getConnection();
    			 /*callable statement*/         	  	         	  	
    			 cstmt=con.prepareCall("{call ACT_PROM_ASIGNATURA(?,?,?,?,?,?,?,?)}");
    			 cstmt.setLong(posicion++,vig);        			 
    			 cstmt.setInt(posicion++,per);
    			 cstmt.setLong(posicion++,inst);
    			 cstmt.setLong(posicion++,sede);
    			 cstmt.setLong(posicion++,jor);
    			 cstmt.setLong(posicion++,jerGrupo);
    			 cstmt.setLong(posicion++,asig);
    			 cstmt.setLong(posicion++,accion);
//    			 System.out.println("ACT PROMEDIO ASIGNATURA:  - Inicia procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());			 
    			 cstmt.executeUpdate();
//    			 System.out.println("ACT PROMEDIO ASIGNATURA: - Fin procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());
    			 cstmt.close();
			
		}
		 catch(SQLException e){
			 System.out.println("PROMEDIO AREA:excepcinn sql!");
			   e.printStackTrace();
			  	 
			   return false;
		   }  
		catch(Exception e ){
			System.out.println("PROMEDIO AREA:excepcinn!");
			e.printStackTrace();		  
		  	return false;
  	}finally{
		try{
		    OperacionesGenerales.closeResultSet(rs);
		    OperacionesGenerales.closeCallableStatement(cstmt);
		    OperacionesGenerales.closeConnection(con);
		    }catch(Exception e){}    
		}
return true;
}


	
	
	/**
	 * @param vig
	 * @param inst
	 * @param sede
	 * @param jor
	 * @param per
	 * @param accion
	 * @param jerGrupo
	 * @param asig
	 * @return
	 */
	public boolean callPuestoEst(long vig, long inst, long sede, long jor, int per){
//		System.out.println("grupoPeriodo : callPuestoEst");
		Connection con=null;
		CallableStatement cstmt=null;
		Collection list = new ArrayList();
		ResultSet rs=null;
		int posicion=1; 		
		
		try{    			      		    
	            	  	
     	  	 con=cursor.getConnection();
    			 /*callable statement*/         	  	         	  	
    			 cstmt=con.prepareCall("{CALL ESTUDIANTE_PUESTO( ?, ?, ?, ?, ?,-99 ,-99, -99, -99, TO_CHAR(SYSTIMESTAMP,'DD-MON-YYYYHH24:MI:SS.FF'))}");
    			 cstmt.setLong(posicion++,vig);        			 
    			 cstmt.setInt(posicion++,per);
    			 cstmt.setLong(posicion++,inst);
    			 cstmt.setLong(posicion++,sede);
    			 cstmt.setLong(posicion++,jor);
//    			 System.out.println("callPuestoEst:  - Inicia procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());			 
    			 cstmt.executeUpdate();
//    			 System.out.println("callPuestoEst: - Fin procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());
    			 cstmt.close();
			
		}
		 catch(SQLException e){
			 System.out.println("callPuestoEst:excepcinn sql!" +e.getMessage());
			   e.printStackTrace();
			  	 
			   return false;
		   }  
		catch(Exception e ){
			System.out.println("callPuestoEst: excepcinn!");
			e.printStackTrace();		  
		  	return false;
  	}finally{
		try{
		    OperacionesGenerales.closeResultSet(rs);
		    OperacionesGenerales.closeCallableStatement(cstmt);
		    OperacionesGenerales.closeConnection(con);
		    }catch(Exception e){}    
		}
return true;
}
	
	
	
	/**
	*	Funcinn: Ejecuta procedimiento para el calculo de la nota de area<BR>	
	**/
	public boolean promedioAreasAsignaturas(long vig, long inst, long sede, long jor, int per, int accion, long jerGrupo, long asig){
		Connection con=null;
		CallableStatement cstmt=null;
		Collection list = new ArrayList();
		ResultSet rs=null;
		int posicion=1; 		
		
		try{    			      		    
	            	  	
     	  	 con=cursor.getConnection();
    			 /*callable statement*/         	  	         	  	
			 cstmt=con.prepareCall("{call ACT_PROM_AREA_ASIG(?,?,?,?,?,?,?,?)}");
			 cstmt.setLong(posicion++,vig);        			 
			 cstmt.setInt(posicion++,per);
			 cstmt.setLong(posicion++,inst);
			 cstmt.setLong(posicion++,sede);
			 cstmt.setLong(posicion++,jor);
			 cstmt.setLong(posicion++,jerGrupo);
			 cstmt.setLong(posicion++,asig);
			 cstmt.setLong(posicion++,accion);
//			 System.out.println("ACT PROMEDIO AREA_ASIGNATURA:  - Inicia procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());			 
			 cstmt.executeUpdate();
//			 System.out.println("ACT PROMEDIO AREA_ASIGNATURA: - Fin procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());
			 cstmt.close();
			
		}
		 catch(SQLException e){
			 System.out.println("PROMEDIO AREA_ASIG:excepcinn sql!");
			   e.printStackTrace();
			  	 
			   return false;
		   }  
		catch(Exception e ){
			System.out.println("PROMEDIO AREA_ASIG:excepcinn!");
			e.printStackTrace();		  
		  	return false;
  	}finally{
		try{
		    OperacionesGenerales.closeResultSet(rs);
		    OperacionesGenerales.closeCallableStatement(cstmt);
		    OperacionesGenerales.closeConnection(con);
		    }catch(Exception e){}    
		}
return true;
}
	
	public boolean calculoPromedioGeneralizado(long vig, long inst, long sede, long jor, int per, int accion, long jerGrupo, long asig,long nivEval) {
		Connection con=null;
		CallableStatement cstmt=null;
		Collection list = new ArrayList();
		ResultSet rs=null;
		int posicion=1; 		
		
		try{    			      		    
	            	  	
     	  	 con=cursor.getConnection();
    			 /*callable statement*/         	  	         	  	
			 cstmt=con.prepareCall("{call ACT_PROM_ASIG_ASIG_AREA_AREA(?,?,?,?,?,?,?,?,?)}");
			 cstmt.setLong(posicion++,vig);        			 
			 cstmt.setInt(posicion++,per);
			 cstmt.setLong(posicion++,inst);
			 cstmt.setLong(posicion++,sede);
			 cstmt.setLong(posicion++,jor);
			 cstmt.setLong(posicion++,jerGrupo);
			 cstmt.setLong(posicion++,asig);
			 cstmt.setLong(posicion++,accion);
			 cstmt.setLong(posicion++, nivEval);
//			 System.out.println("ACT PROMEDIO AREA_ASIGNATURA:  - Inicia procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());			 
			 cstmt.executeUpdate();
//			 System.out.println("ACT PROMEDIO AREA_ASIGNATURA: - Fin procedimiento Hora: "+new java.sql.Timestamp(System.currentTimeMillis()).toString());
			 cstmt.close();
			
		}
		 catch(SQLException e){
			 System.out.println("PROMEDIO Asignatura, Area_Asignatura,Area:excepcinn sql!");
			   e.printStackTrace();
			  	 
			   return false;
		   }  
		catch(Exception e ){
			System.out.println("PROMEDIO Asignatura, Area_Asignatura,Area:excepcinn!");
			e.printStackTrace();		  
		  	return false;
		}finally{
  			try{
			    OperacionesGenerales.closeResultSet(rs);
			    OperacionesGenerales.closeCallableStatement(cstmt);
			    OperacionesGenerales.closeConnection(con);
		    }catch(Exception e){}    
		}
		return true;
	}
	
	
	
	
	
}
