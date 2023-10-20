package siges.reporte;

import java.io.File;
import java.sql.Time;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.reporte.AreaGrupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import siges.reporte.beans.FiltroBeanEvaluacionTipoDescriptor;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerTipoDescriptor  extends HttpServlet{
  private static int i=0;
  private AreaGrupo area;
  private ResourceBundle rb;
  private PreparedStatement pst=null;
  private ResultSet rs=null;
  private Cursor cursor;
  private File reportFile;
  private File reportFileGrado;
  private FiltroBeanEvaluacionTipoDescriptor filtro;
  private Thread t;
  private String path;
  private String path1;
    
  
  public void init(ServletConfig config) throws ServletException{
 	 
    ServletContext context=config.getServletContext();
    rb=ResourceBundle.getBundle("preparedstatements_reportesEstadisticas");
 	  String contextoTotal=context.getRealPath("/");
		path=context.getRealPath(rb.getString("ruta_jaspers"));
		path1=context.getRealPath(rb.getString("ruta_img"));
		reportFile=new File(path+File.separator+rb.getString("jasper_tipo_descriptor_grupo"));
		reportFileGrado=new File(path+File.separator+rb.getString("jasper_tipo_descriptor_grado"));
  	int n=1;
	  System.out.println("TIPO_DESCRIPTOR INICIANDO");
	  super.init(config);
	  cursor=new Cursor();
		if (!updateColaReporteEstadoCero()){
	 		   System.out.println("n****NO ACTUALIZn LAS TIPO_DESCRIPTOR QUE ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
	 		   return;
	 		}
	  t=new Thread(new TipoDescriptor(cursor,contextoTotal,path,path1,reportFile,reportFileGrado,n++));
	  t.start();
	  return;
  }		
  
	/**
	*	Funcinn: Ejecuta los prepared statements correspondientes para actualzar el estado y ponerlo en la cola<BR>
	*	@param	String 
	*	@param	String 
	*	@param	String 
	*	@param	String 
	**/
   
	public boolean updateColaReporteEstadoCero(){
    Connection con=null;
  	PreparedStatement pst=null;
  	int posicion=1;    		
  	
  	try{
		  con=cursor.getConnection();
		  pst=con.prepareStatement(rb.getString("actualizar_tipo_descriptor_en_cola"));
			posicion=1;
			pst.clearParameters();
		 	pst.executeUpdate();
		 	pst.close();
		 	con.commit();
		 	}
  	 catch(Exception e ){
  		  e.printStackTrace();
		  	return false;
    	}finally{
  		try{
  		    OperacionesGenerales.closeStatement(pst);
  		    OperacionesGenerales.closeConnection(con);
  		    }catch(Exception e){}
  		}
  		return true;
}
  
  
  public void destroy(){
    try{
      t.stop();
  	  System.out.println("TIPO_DESCRIPTOR FINALIZANDO");
    }catch(Exception ex){}
  }
  
}
