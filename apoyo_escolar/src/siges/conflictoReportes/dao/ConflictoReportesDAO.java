/*
 * Creado el 31/08/2006
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
package siges.conflictoReportes.dao;

import java.sql.Connection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import siges.dao.Ruta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.conflictoReportes.beans.*;
import siges.login.beans.Login;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ConflictoReportesDAO extends Dao{
    
    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;
    
    public ConflictoReportesDAO(Cursor cur) {
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("conflictoReportes");
    }
    
    
    public List obtenerPeridos(long vigencia){
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs=null;
    	int posicion=1;
    	List periodos = new ArrayList();

    	try{
    		cn=cursor.getConnection(); cn.setAutoCommit(false);
    		pst=cn.prepareStatement(rb.getString("periodos_vigencia"));
    		pst.clearParameters();
    		posicion=1;

    		pst.setLong(posicion++,vigencia);

    		rs = pst.executeQuery();
    		while(rs.next()){
    			periodos.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
    		}
    	}catch(InternalErrorException in){
    		in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return periodos;
    	}
    	catch(SQLException sqle){
    		sqle.printStackTrace();
    		try{
    			cn.rollback();
    		}catch(SQLException se){
    			
    		}
    		setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
    		switch(sqle.getErrorCode()){
    		default:
    			setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
    	}
    	return periodos;
    	}finally{
    		try{
    			OperacionesGenerales.closeResultSet(rs);
    			OperacionesGenerales.closeStatement(pst);
    			OperacionesGenerales.closeConnection(cn);
    		}catch(InternalErrorException inte){}
    	}
    	return periodos;
    }
    
    /**
     * genera el reporte en pdf
     * @param rf
     * @param p
     * @return
     */
    public byte[] correrjaper(String rf, Map p){
    	Connection con=null;
    	try{
    	con=cursor.getConnection();
    	return JasperRunManager.runReportToPdf(rf,p,con);
    	}catch(InternalErrorException e ){
    		setMensaje("Error: "+e.getMensaje());
    		e.printStackTrace();
    	}catch(Exception e ){
    		setMensaje("Error: "+e.getMessage());
    		e.printStackTrace();
    	}finally{
    		try{
    			OperacionesGenerales.closeConnection(con);
    		}catch(Exception e){}
    	}
    	return null;
    }
    
    /**
	 * Construye el archivo y lo almacena en el servidor
	 * 
	 * @param reportFile
	 *            Archivo del reporte
	 * @param parameterscopy
	 *            Parametros del reporte
	 * @param rutaExcel
	 *            Ruta de almacenamiento del reporte
	 * @param archivo
	 *            Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoXLS(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = cursor.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			// USANDO API EXCEL
			JExcelApiExporter xslExporter = new JExcelApiExporter();
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT,
					jasperPrint);
			xslExporter.setParameter(
					JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			xslExporter.setParameter(
					JExcelApiExporterParameter.OUTPUT_FILE_NAME, xlsFilesSource
							+ xlsFileName);
			xslExporter.exportReport();
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
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}
    
    /**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public boolean updateReporteEstado(String modulo,String us,String rec,String tipo,String nombre,String prepared){
  			Connection con=null;
  			PreparedStatement pst=null;
  			int posicion=1;

  			try{ 
  				  con=cursor.getConnection(); 
  				  
  					pst=con.prepareStatement(rb.getString(prepared));
  					posicion=1;
  					pst.clearParameters();
  					pst.setString(posicion++,(us));
  					pst.setString(posicion++,(rec));
  					pst.setString(posicion++,(tipo));
  					pst.setString(posicion++,(nombre));
  					pst.setString(posicion++,(modulo));	
  					pst.executeUpdate();
  					con.commit();
  					pst.close();		
  					//System.out.println("nnSE ACTUALIZn EL ESTADO DEL REPORTE!!");
  			  }	
  				catch(InternalErrorException e ){
  						System.out.println("Error Interno: "+e.toString());
  						return false;
  					}
  				catch(Exception e ){
  						System.out.println("Error Exception: "+e.toString());
  						return false;
  					}
  				finally{
  					try{
  					OperacionesGenerales.closeStatement(pst);
  					OperacionesGenerales.closeConnection(con);
  					}
  					catch(Exception e){}
  			  }
  		return true;		
  	}
    
    public void ponerReporte(String modulo,String us,String rec,String tipo,String nombre,String estado,String prepared){	
    	Connection con=null;
    	PreparedStatement pst=null;
    	int posicion=1;
  		try{ 
  			con=cursor.getConnection(); 
  			pst=con.prepareStatement(rb.getString(prepared));
  			posicion=1;
  			pst.clearParameters();
  			pst.setString(posicion++,(us));
  			pst.setString(posicion++,(rec));
  			pst.setString(posicion++,(tipo));
  			pst.setString(posicion++,(nombre));
  			pst.setString(posicion++,(modulo));
  			pst.setString(posicion++,(estado));		
  			pst.executeUpdate();
  	  }catch(InternalErrorException e ){
  		  e.printStackTrace();
  	  }catch(Exception e ){
  		  e.printStackTrace();
  	  }finally{
  		  try{
  			  OperacionesGenerales.closeStatement(pst);
  			  OperacionesGenerales.closeConnection(con);
  		  }catch(Exception e){}
  	  }
    }
    
    public void ponerReporteMensaje(String estado,String modulo,String us,String rec,String tipo,String nombre,String prepared,String mensaje){
  	    
			Connection con=null;
			PreparedStatement pst=null;
			int posicion=1;
	  		
			try{ 
			  con=cursor.getConnection(); 
			  
				pst=con.prepareStatement(rb.getString(prepared));
				posicion=1;
				pst.clearParameters();
				pst.setString(posicion++,(estado));
				pst.setString(posicion++,(mensaje));
				pst.setString(posicion++,(us));
				pst.setString(posicion++,(rec));
				pst.setString(posicion++,(tipo));
				pst.setString(posicion++,(nombre));
				pst.setString(posicion++,(modulo));	
				pst.executeUpdate();
				pst.close();
				con.commit();  						
		  }	
			catch(InternalErrorException e ){
			    e.printStackTrace();
				}
			catch(Exception e ){
			    e.printStackTrace();
				}
			finally{
				try{
				 OperacionesGenerales.closeStatement(pst);
				 OperacionesGenerales.closeConnection(con);
				}
				catch(Exception e){}
		  }    		    	    			
	}	
    
    public boolean existeGenracionReporte(Login login){
    	Connection cn=null;
   	    PreparedStatement pst=null;
   	    ResultSet rs=null;
   	    int posicion=1;
   	 try{
	        cn=cursor.getConnection(); cn.setAutoCommit(false);
	        pst=cn.prepareStatement(rb.getString("existe_reporte_generandose"));
	        pst.clearParameters();
	        posicion=1;
	        
	        pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
	        rs = pst.executeQuery();
   	        if(rs.next()){
   	            return true;
   	        }
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return false;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
    	return false;
    }
    
    public boolean existenDatosReporte(){
    	//toca hacerlo
    	return true;
    }
    
    public String obtenerJerar(ConflictoFiltro cf, int nivel){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    ResultSet rs=null;
   	    int posicion=1;
        String jerar="";
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Asignar.Jerar"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        pst.setInt(posicion++,nivel);
   	        if(cf.getLocal3().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cf.getLocal3().trim()));
   	        if(cf.getColegio3().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cf.getColegio3().trim()));
   	        if(cf.getSede3().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
	        else pst.setLong(posicion++,Long.parseLong(cf.getSede3().trim()));
   	        if(cf.getJorn3().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
	        else pst.setLong(posicion++,Long.parseLong(cf.getJorn3().trim()));
   	        rs = pst.executeQuery();
   	        if(rs.next()){
   	            jerar=rs.getString(1);
   	        }
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return "";}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return "";
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return jerar;
    }

}
