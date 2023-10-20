package siges.gestionAdministrativa.RepPuestoEstudiante.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.boletines.vo.DatosBoletinVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.RepPuestoEstudiante.dao.ReporteDAO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.FiltroReportesVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.ParamsVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.ReporteVO;
import siges.io.Zip;



public class Reportes extends Thread{
  private static boolean ocupado=false;        
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private ResourceBundle rPath,rbComp;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanReports filtro;
	private String buscarcodigo;
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private Integer cadena=new Integer(java.sql.Types.VARCHAR);
	private Integer fecha=new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo=new Integer(java.sql.Types.NULL);
	private Integer doble=new Integer(java.sql.Types.DOUBLE);
	private Integer caracter=new Integer(java.sql.Types.CHAR);
	private Integer enterolargo=new Integer(java.sql.Types.BIGINT);	
	private final String modulo="3";
	//private String s;
	//private String s1;
	private String archivo,archivopre,archivozip;	
	private String buscar,buscarjasper;
	private String insertar;
	private String existeboletin;
	private String ant;
	private String er;
	private String sig;
	private String home;
	private byte[] bytes;
	private byte[] bytes1;
	private Map parameters;
	private Map copyparameters;
	
	private File reporte1_1;
	
	
	private File reporte2_1;

	private File reporte3_1;

	private File reporte4_1;  
	
	private File reporte5_1;
	
	private File reporte6_1; 
	private File reporte7_1; 
	private File reporte8_1; 
	private File reporte9_1; 

	
	private File escudo;
	private String path,path1,path2;
	private String context;
	private String codigoestu;
	private File reportFileNuevoFormato;
//	private PreparedStatement pst;
//	private CallableStatement cstmt;
//	private ResultSet rs;
	//private reportes report; 
  private String vigencia;
  private long consecRep=0;
  private String usuarioRep="";
  private String nombreRep="";
  private String nombreRepPdf="";
  private ReporteDAO compDAO;
  int dormir=0;

	
/*Constructor de la clase*/	

	public Reportes(Cursor c,String cont,String p,String p1,String p2,File reporte1_1_,  File reporte2_1_ , File reporte3_1_, File reporte4_1_, File reporte5_1_,File reporte6_1_,File reporte7_1_,File reporte8_1_,File reporte9_1_, int nn){
			super("HILO_REP_PUESTOS: "+nn);
			cursor=c;
			path=p;
			path1=p1;
			path2=p2;
			
			reporte1_1=reporte1_1_;
			
			//reporte1_con=reporte1_con_;
			reporte2_1=reporte2_1_;
			
			reporte3_1=reporte3_1_;
			
			reporte4_1=reporte4_1_;  
			
			reporte5_1=reporte5_1_;
			
			reporte6_1=reporte6_1_;  
			reporte7_1=reporte7_1_;  
			reporte8_1=reporte8_1_;  
			reporte9_1=reporte9_1_;  
	
			context=cont;
	 	    rPath=ResourceBundle.getBundle("path"); 	   	
	 	   	rbComp=ResourceBundle.getBundle("siges.gestionAdministrativa.RepPuestoEstudiante.bundle.reporte");
	        //s=rb3.getString("valor_s");
	 	  	//s1=rb3.getString("valor_s1");
	 	   	buscar=buscarjasper=insertar=existeboletin=null;
			err=false;
			mensaje=null;
			codigoestu=null;
			bytes=null;
			
			compDAO = new ReporteDAO(cursor);
			//vigencia=compDAO.getVigenciaNumerico();
		}
		
	
	public void run(){
  	  Object[]o=new Object[2];
	    int posicion=1;
	    String[][] array=null;
	    dormir=0;
	    String cola=null;
	    String puest="-999";
	    //report= new reportes();
	    try{
	      Thread.sleep(60000);
	      dormir=Integer.parseInt(rbComp.getString("reportes.Dormir"));
	      
	  	  while(ocupado){
	  		  System.out.println(getName()+":Espera Thread");
	  		  sleep(dormir);
	  	  }
	  	  
	  	  ocupado=true;
		  System.out.println(getName()+":Entra Thread");
		  while(true){
			//VALIDACION SI EL HILO ESTA EN PARAMETRO
			// if(!activo()){//es porque en la tabla vale 0
			         //System.out.println("**ESTA APAGADO**");
			       //  Thread.sleep(dormir);
			        // continue;
			// }
//			  System.out.println("REP PUESTOS: ENTRO A REVISAR SOLICITUDES, PARA ATENDERLAS");
			  procesar_solicitudes(); 		          
		  }		    
	    }catch(InterruptedException ex){
	    	System.out.println(new Date()+" - REP PUESTOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
	        ex.printStackTrace();	        
	        //limpiarTablas(consecBol);	        
	    }
	    catch(Exception ex){
		     System.out.println(new Date()+" - REP PUESTOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
		     ex.printStackTrace();	     
	  		// limpiarTablas(consecBol);	  		
	    }	
	    finally{ocupado=false;}
		}
	
	

	
	public boolean procesar_solicitudes(){
		ReporteVO reporte = null;
		FiltroReportesVO rep=null;
		try{
		 List reportesComp=reportes_Generar();
	     Iterator repComp= reportesComp.iterator();
	  		if(reportesComp!=null && reportesComp.size()>0){
	  			System.out.println("REP PUESTOS: SI HAY REP PUESTOS POR GENERAR: CANTIDAD "+reportesComp.size());
    		while(repComp.hasNext()){	      			
    			rep = (FiltroReportesVO)repComp.next();
    			reporte=null;
    			reporte=setReporte(rep);
    			System.out.println("REP PUESTOS: REPORTE A GENERAR CONSEC: "+rep.getConsec());	  			
	  			parameters=setParametros(rep);
	  			System.out.println("PARAMETROS: "+parameters.values());
	  			
	  			if(!procesar(rep,parameters,reporte)){
	  				System.out.println("REP PUESTOS: PROCESAR SOLICITUDES FALLO.");
	  				rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());
			  		compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
			  		reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
			  		reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: ERROR AL PROCESAR SOLICITUD");
			  		compDAO.updateReporte(reporte);	
			  		//siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			  		compDAO.limpiarTablas(rep.getConsec());   			  		   
	  				continue;
	  			}	  			  			  			  		
	  			//if (!updatePuestoBoletin(puesto,nombreBol,usuarioBol,"update_puesto_boletin_2")){
	  			//	System.out.println("REP COMPARATIVOS: **NO Se actualizï¿½ el puesto del boletin en Datos_Boletin**");
	  			//	continue;
	  			//}	  			  		
	  			/*if(!update_cola_boletines()){
	  				System.out.println("REP COMPARATIVOS: NO Se actualizï¿½ la lista de los reportes en cola");
	  				continue;
	  			}*/
	  			
    		}
    		System.out.println("REP PUESTOS: "+getName()+":Sale While reportes Puestos");
	  		}else{
//	  			System.out.print("\n*NO HAY SOLICITUDES DE REP PUESTOS PARA PROCESAR ");	
	  			Thread.sleep(dormir);
	  			//System.out.println("REP COMPARATIVOS: NO HAY BOLETINES A GENERAR");
	  			//System.out.println("**Se mandaron vaciar las tablas ya que no hay boletines por generar**");
	  			//vaciarTablas();
	  						 			 
	  		}
	  		return true;
		}catch(InterruptedException ex){
	    	System.out.println(new Date()+" - REP PUESTOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
	        ex.printStackTrace();
	        try {
	        	System.out.println("REP PUESTOS: PROCESAR SOLICITUDES FALLO.");
				rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());	  		
				compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
		  		reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
		  		compDAO.updateReporte(reporte);	
		  		//siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
		  		compDAO.limpiarTablas(rep.getConsec());	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	  		        
	    }
	    catch(Exception ex){
		     System.out.println(new Date()+" - REP PUESTOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
		     ex.printStackTrace();
		     try {
		        	System.out.println("REP PUESTOS: PROCESAR SOLICITUDES FALLO.");
					rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());	  		
					compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
			  		reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
			  		compDAO.updateReporte(reporte);	
			  		//siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			  		compDAO.limpiarTablas(rep.getConsec());	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
	    }	
	    finally{ocupado=false;}
	}
	
	
	public List reportes_Generar(){
		int posicion=1;  
		String[][] array1=null;		
		//FiltroReportesVO rep=null;
		List list = new ArrayList();
		try{
			list=compDAO.getSolicitudes();
			return list;
		}	
		catch(Exception e){
			System.out.println("REP PUESTOS: ERROR LISTADO REPORTES A GENERAR, MOTIVO: "+e.getMessage());
		    e.printStackTrace();  
		    return null;
			}
		finally{
			try{
			 }catch(Exception e){}
	  }
		
	}
	
  public boolean procesar(FiltroReportesVO rep,Map parameterscopy, ReporteVO reporte){
      
  //parameterscopy=parameters;
  
   Object[] o;
   list=new ArrayList();
   String nombre;      
   Zip zip= new Zip();   
   Collection list = new ArrayList();
   String archivosalida=null;
   o=new Object[2];	
   int zise;
   int posicion=1;
   String periodoabrev1=null;
   bytes=null;
   bytes1=null;
   String cont=null;
   int boletin=1;
   Connection con=null;
   try{
	   compDAO.limpiarTablas(rep.getConsec());
	   //System.out.println("ï¿½Se limpiaron las tablas antes de ser llenadas!");		   
	   rep.setFechaGen(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	   rep.setFechaFin("");
	   compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_EJE, rep.getFechaGen(), rep.getFechaFin());
	   reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
	   reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: "+rep.getConsec());
	   compDAO.updateReporte(reporte);	     	   
	   if(rep.getConsec()>0){
		   con = compDAO.getConnection();
		   
		   System.out.println("REP PUESTOS: ENTRO A GENERAR REPORTE. TIPO: "+rep.getTipoReporte());
		   if (!compDAO.llenarTablas(rep, reporte)){
			   System.out.println("REP PUESTOS: ERROR PROCEDIMIENTO PK_REPORTES2");
			   return true;
		   }	  		
		   //VALIDAR SI NO HUBO ERROR EN EL PROC
		   if(compDAO.validarEstadoReporte(rep.getConsec())==ParamsVO.ESTADO_REPORTE_NOGEN){
			   System.out.println("GENERO ERROR EL REPORTE EN EL PROCEDIMIENTO PK_REPORTES2");
			   return true;
		   }
		   /*Consulta para ejecutar el/los jasper*/
		   //rep=compDAO.datosConv(rep, reporte);		   	  		
	   	  		if(compDAO.validarDatosReporte(rep)){
	   	  			System.out.println("REP PUESTOS: DESPUES DE VALIDAR DATOS EN EL REPORTE");
	   	  			parameterscopy.put("CONVINST", rep.getConvInts());
	   	  			parameterscopy.put("CONVMEN", rep.getConvMen());
	   	  			File reportFile=setFileJasper(rep);
	   	  			
	   	  			System.out.println("REP PUESTOS: HAY DATOS SE MANDA EJECUTAR EL JASPER");
	   	  			if((reportFile.getPath()!=null) && (parameterscopy!=null) &&(!parameterscopy.values().equals("0")) && (con!=null)){      	  	    
	   	  					System.out.println("REP PUESTOS:  SE MANDO EJECUTAR JASPER");  	  						
	   	  					bytes=JasperRunManager.runReportToPdf(reportFile.getPath(),parameterscopy,con);
	   	  			}	   	  	   
		   		    reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
		   		    reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: "+rep.getConsec());
		   		    compDAO.updateReporte(reporte);
	      		    //ponerArchivo(modulo,path,bytes1,filtro.getNombreboletinpre());   
		   		 if(!ponerArchivo(modulo,path,bytes,rep.getNombre_pdf())){
	      		    	System.out.println("ERROR AL PONER ARCHIVO EN DISCO.");
	      		    	rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	      		    	compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
				  		   reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				  		   reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: ERROR AL COPIAR A DISCO");
				  		 compDAO.updateReporte(reporte);	
				  		   //siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				  		compDAO.limpiarTablas(rep.getConsec());                		
			  		      return true;
	      		    	
	      		    }
	      		    ponerArchivo(modulo,path,bytes,rep.getNombre_pdf());
	      		    archivosalida=Ruta.get(context,rPath.getString("repPuestos.PathRepPuestos"));
	      		    String rutaExcel = getArchivoXLS(reportFile, parameterscopy, archivosalida, rep.getNombre_xls(), con);
	      		    //list.add(archivosalida+filtro.getNombreboletinpre());//pdf
	      		    System.out.println("APOYO:REPORT PÃœESTOS: ADD ARCHIVOS ZIP: EXCEL: "+rutaExcel);
	      		    list.add(archivosalida+rep.getNombre_pdf());//pdf
	      		    list.add(rutaExcel);
	      		    zise=100000;
	       				
	           		if(zip.ponerZip(archivosalida,rep.getNombre_zip(),zise,list)){
	           			   rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				  		   compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_GENOK, rep.getFechaGen(), rep.getFechaFin());
				  		   reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
				  		   reporte.setMensaje("REPORTE GENERADO SATISFACTORIAMENTE");
				  		   compDAO.updateReporte(reporte);
				  		   //siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				  		   compDAO.limpiarTablas(rep.getConsec());   		    
	        		    return true;
	        		  }	          		       	  	      	  	      	  	
	   	  		}//fin de rs
		   	  	else{			  		        
				  		   rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				  		   compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
				  		   reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				  		   reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				  		   compDAO.updateReporte(reporte);	
				  		   //siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				  		   compDAO.limpiarTablas(rep.getConsec());                		
			  		      return true;
			    	}
	   	  	
	   }
   } 
   
   catch(JRException e){
	   e.printStackTrace();
	   try {
		   rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());
		   compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
		   reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
		   reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "+e.getMessage());
		   compDAO.updateReporte(reporte);	
		   //siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
		   compDAO.limpiarTablas(rep.getConsec());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   return false;
   }
   catch(java.lang.OutOfMemoryError e){
	   e.printStackTrace();
	   System.out.println(" WARNING: Â¡Memoria Insuficiente para generar el reporte solicitado! : "+e.toString());
	   try {
		   rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());
		   compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
		   reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
		   reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, ERROR MEMORIA INSUFICIENTE, EXCEPCION: "+e.getMessage());
		   compDAO.updateReporte(reporte);	
		   //siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
		   compDAO.limpiarTablas(rep.getConsec());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   return false;
   }
   catch(Exception e){
	   e.printStackTrace();
	   try {
		   rep.setFechaFin(new java.sql.Timestamp(System.currentTimeMillis()).toString());
		   compDAO.updateSolicitud(rep.getConsec(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(), rep.getFechaFin());
		   reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
		   reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "+e.getMessage());
		   compDAO.updateReporte(reporte);	
		   //siges.util.Logger.print(usuarioBol,"Excepciï¿½n al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
		   compDAO.limpiarTablas(rep.getConsec());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   return false;
   }
   finally{
	   try{
		   if(con!=null)
			   con.close();
	   }
	   catch(Exception e){}
   }
   return true;
  }//Fin de Run
    
	/**
	*	Funciï¿½n: Pone el reporte generado por el reporteador en la tabla de reportes, y luego poder ser visualizado en <BR> la lista de reportes generados <BR>
	*	@param	byte bit
	**/
  
  public boolean ponerArchivo(String modulo,String path,byte[] bit,String archivostatic){    	    
		
		try{								
			String archivosalida=Ruta.get(context,rPath.getString("repPuestos.PathRepPuestos"));				
			File f=new File(archivosalida);
			if(!f.exists()) FileUtils.forceMkdir(f);			
			FileOutputStream fileOut= new FileOutputStream(f+File.separator+archivostatic);
			CopyUtils.copy(bit,fileOut);
			fileOut.flush();
			fileOut.close();
			return true;
		}catch(IOException e){
			e.printStackTrace();  
			compDAO.limpiarTablas(consecRep);  		
			
			return false;
		}		
		finally{
				try{				  
				}catch(Exception inte){}
			}		
	}
  
  public String getArchivoXLS(File reportFile, Map parameterscopy, String rutaExcel,String archivo, Connection cn)throws Exception {
		
		String archivoCompleto = null;
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameterscopy, cn);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			System.out.println("REP PUESTOS;EXCEL:"+archivoCompleto);
			File f = new File(xlsFilesSource);
			System.out.println("********************REP PUESTOS;EXCEL:1");
			if (!f.exists())
				FileUtils.forceMkdir(f);
			System.out.println("********************REP PUESTOS;EXCEL:2");
			// USANDO API EXCEL
			
			JExcelApiExporter xslExporter = new JExcelApiExporter();
			System.out.println("********************REP PUESTOS;EXCEL:3");
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			System.out.println("********************REP PUESTOS;EXCEL:4");
			xslExporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			System.out.println("********************REP PUESTOS;EXCEL:5");
			xslExporter.setParameter(JExcelApiExporterParameter.OUTPUT_FILE_NAME, xlsFilesSource + xlsFileName);
			System.out.println("********************REP PUESTOS;EXCEL:6");
			xslExporter.exportReport();
			System.out.println("********************REP PUESTOS;EXCEL:7");
			System.out.println("REP PUESTOS;EXCEL22:"+archivoCompleto);
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				//OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}

	
	/*public void ponerArchivo(String modulo,String path,byte[] bit,String archivostatic){    	    
		
		try{
								
			String archivosalida=Ruta.get(context,rPath.getString("comparativos.PathComparativo"));				
			File f=new File(archivosalida);
			if(!f.exists()) FileUtils.forceMkdir(f);			
			FileOutputStream fileOut= new FileOutputStream(f+File.separator+archivostatic);
			CopyUtils.copy(bit,fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(IOException e){
			e.printStackTrace();  
  			compDAO.limpiarTablas(consecRep);	  		
		}		
		finally{
				try{				  
				}catch(Exception inte){}
			}		
	}
*/
	
	 	


/**
*	Funciï¿½n: Actualiza el puesto del reporte en la tabla de reportes <BR>
*	@param	String puesto
*	@param	String nombreboletin
*	@param	String tipo
*	@param	String nombre
*	@param	String prepared
**/

public boolean updatePuestoBoletin(String puesto,String nombreboletinzip,String user,String prepared){	
	Connection con=null;
	PreparedStatement pst=null;
	int posicion=1;
		
	try{ 
	  con=cursor.getConnection(); 
	  
		pst=con.prepareStatement(rbComp.getString(prepared));
		posicion=1;
		pst.clearParameters();
		pst.setString(posicion++,(puesto));
		pst.setString(posicion++,(nombreboletinzip));
		pst.setString(posicion++,(user));
		pst.executeUpdate();
		pst.close();
		con.commit();
  }	
	catch(InternalErrorException e ){
	    e.printStackTrace();
			return false;
		}
	catch(Exception e ){
	    e.printStackTrace();
			return false;
			}
	finally{
		try{
		    OperacionesGenerales.closeStatement(pst);
		    OperacionesGenerales.closeConnection(con);
		    }catch(Exception e){}
  }
	return true;
}

	/**
	*	Funciï¿½n: Actualiza el puesto del reporte en la tabla de reportes <BR>
	*	@param	String puesto
	*	@param	String nombreboletin
	*	@param	String tipo
	*	@param	String nombre
	*	@param	String prepared
	**/

	public boolean update_cola_boletines(){
	    
		Connection con=null;
		PreparedStatement pst=null;  	
		int posicion=1;  		
  		
		try{
		  con=cursor.getConnection();   		  
   		pst=con.prepareStatement(rbComp.getString("update_puesto_boletin_1"));
   		posicion=1;
   		pst.clearParameters();
   	  pst.executeUpdate();
   	  con.commit();
	  }	
		catch(InternalErrorException e ){
				e.printStackTrace();
				return false;
			}
		catch(Exception e){
		    e.printStackTrace();
				return false;
			}
		finally{
			try{
			    OperacionesGenerales.closeStatement(pst);
			    OperacionesGenerales.closeConnection(con);
			    }catch(Exception e){}
	  }  
	return true;	
}
	
	
	
	 /**
	*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	*	@param	String s
	**/
		public void setMensaje(String s){
			if (!err){
				err=true;
				mensaje="VERIFIQUE LA SIGUIENTE INFORMACIÃ“N: \n\n";
			}
			mensaje="  - "+s+"\n";
		}	

		
		public ReporteVO setReporte(FiltroReportesVO rep){
			ReporteVO reporte=new ReporteVO();
			if (rep!=null){
				String pathComp=rbComp.getString("reportes.Pathcomparativo");
				reporte.setNombre(rep.getNombre_zip());
				reporte.setRecurso(pathComp+rep.getNombre_zip());
				reporte.setModulo(Integer.parseInt(ParamsVO.REP_MODULO));
				reporte.setFecha(rep.getFecha());
				reporte.setTipo("zip");
				reporte.setUsuario(rep.getUsuario());
			}			
			return reporte;
		}	

		public File setFileJasper(FiltroReportesVO rep){
			File reporte=null;
			if (rep!=null){
				System.out.println("REP PUESTOS TIPO ESCALA: "+rep.getTipoEscala());
				if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_1){
					reporte=reporte1_1;						
					
				}else
				if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_2){
									
					reporte=reporte2_1;						
					
				}else
				if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_3){
										
					reporte=reporte3_1;						
				
				}else
				if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_4){
										
					reporte=reporte4_1;						
					
				}
				else if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_5){
											
						reporte=reporte5_1;						
						
				}
				else if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_6){
					
					reporte=reporte6_1;						
					
				}
				else if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_7){
					
					reporte=reporte7_1;						
					
				}
				else if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_8){
					
					reporte=reporte8_1;						
					
				}
				else if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_9){
					
					reporte=reporte9_1;						
					
				}
			}		
			System.out.println("REP PUESTOS REPORTE: "+reporte.getAbsolutePath());
			return reporte;
		}
		
		public Map setParametros(FiltroReportesVO rep){
			Map parametros=new HashMap();
			if (rep!=null){
				parametros=new HashMap();
				parametros.put("CONSEC",new Long(rep.getConsec()));  
	  			parametros.put("usuario",rep.getUsuario());
	  			parametros.put("ESCUDO_SED",path1+rbComp.getString("imagen"));		  			
	  			parametros.put("PERIODO",new Integer((int)rep.getPeriodo()));
	  			parametros.put("VIGENCIA",new Integer((int)rep.getVig()));  		  			
	  						
	  			parametros.put("NOMBRE_PER_FINAL",rep.getNomPerFinal());		  			
	  			parametros.put("NUM_PERIODOS",new Integer((int)rep.getNumPer()));  			
	  			parametros.put("INSTITUCION",new Long(rep.getInst()));
	  			parametros.put("SEDE",rep.getSede_nombre());
	  			parametros.put("JORNADA",rep.getJornd_nombre());
	  			parametros.put("FILTRO_REPORTE","");
	  			parametros.put("TIPO_ESC",String.valueOf(rep.getTipoEscala()));
	  			parametros.put("TIPO_ESCALA",new Integer(rep.getTipoEscala()));
	  			if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_1){
	  				if(rep.getConAreAsi()==1)
	  					parametros.put("ASIGNATURAS","Ã�reas: "+rep.getAsigNombres());
	  				else
	  					parametros.put("ASIGNATURAS","Asignaturas: "+rep.getAsigNombres());
	  			}else{
	  				String numDoc="";
	  				if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_5){
	  					numDoc="; Docente: ";
	  					numDoc=numDoc+compDAO.getDocente(rep.getDocente());	  					
	  				}
	  				
	  				if(rep.getConAreAsi()==1)
	  					parametros.put("ASIGNATURAS","Ã�rea: "+rep.getAsigNombres());
	  				else
	  					parametros.put("ASIGNATURAS","Asignatura: "+rep.getAsigNombres()+numDoc);
	  			}
	  			parametros.put("CONAREASIG",""+rep.getConAreAsi());
	  			
	  			String filtro="";
	  			filtro="Vigencia: "+rep.getVig();
	  			filtro= filtro+"; MetodologÃ­a: "+rep.getMetodo_nombre();
	  			if(rep.getTipoReporte()!=ParamsVO.TIPO_REPORTE_3){
	  				if(rep.getPeriodo()!=7)
	  					filtro= filtro+"; PerÃ­odo: "+rep.getPeriodo();
	  				else
	  					filtro= filtro+"; PerÃ­odo: "+rep.getNomPerFinal();
	  			}
	  			if(rep.getProv()>0)
	  				filtro= filtro+"; Provincia: "+rep.getProv_nombre();
	  			if(rep.getLoc()>0)
	  				filtro= filtro+"; Municipio: "+rep.getLoc_nombre();
	  			if(rep.getInst()>0)
	  				filtro= filtro+"; Institucion: "+rep.getInst_nombre();
	  			if(rep.getZona()>0)
	  				filtro= filtro+"; Zona: "+rep.getZona_nombre();
	  			if(rep.getSede()>0)
	  				filtro= filtro+"; Sede: "+rep.getSede_nombre();
	  			if(rep.getJornd()>0)
	  				filtro= filtro+"; Jornada: "+rep.getJornd_nombre();
	  			if(rep.getGrado()>0)
	  				filtro= filtro+"; Grado: "+rep.getGrado_nombre();
	  			if(rep.getGrupo()>0)
	  				filtro= filtro+"; Grupo: "+rep.getGrupo_nombre();
	  			if(rep.getGrupo()>0)
	  				filtro= filtro+"; Grupo: "+rep.getGrupo_nombre();
	  			if(rep.getTipoReporte()==ParamsVO.TIPO_REPORTE_4){
	  				filtro= filtro+"; Valor Inicial: "+rep.getValorIni();
	  				filtro= filtro+"; Valor Final: "+rep.getValorFin();
	  				if(rep.getEscala()>0)
	  					filtro= filtro+"; Escala: "+rep.getEscala_nombre();
	  			}
	  			
	  			parametros.put("FILTRO_REPORTE",filtro);
	  			parametros.put("SUBREPORT_DIR",path);
	  			
	  			
	  			
			}			
			return parametros;
		}	

	
}
