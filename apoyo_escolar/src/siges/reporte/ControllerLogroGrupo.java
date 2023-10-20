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
import siges.reporte.LogroGrupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import siges.reporte.beans.FiltroBeanEvaluacionLogroGrupo;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerLogroGrupo  extends HttpServlet{
  private static int i=0;
  private LogroGrupo logro;
  private ResourceBundle rb;
  private PreparedStatement pst=null;
  private ResultSet rs=null;
  private Cursor cursor;
  private File reportFile;
  private File reportFile_a;
  private FiltroBeanEvaluacionLogroGrupo filtro;
  private Thread t;
  private String path;
  private String path1;
    
  
  public void init(ServletConfig config) throws ServletException{
 	 
    ServletContext context=config.getServletContext();
    rb=ResourceBundle.getBundle("preparedstatements_reportesEstadisticas");
 	  String contextoTotal=context.getRealPath("/");
		path=context.getRealPath(rb.getString("ruta_jaspers"));
		path1=context.getRealPath(rb.getString("ruta_img"));
		reportFile=new File(path+File.separator+rb.getString("jasper_evaluacion_logro_grupo"));
		reportFile_a=new File(path+File.separator+rb.getString("jasper_evaluacion_logro_grupo_a"));
  	int n=1;
	  System.out.println("LOGROS 2006 INICIANDO");
	  super.init(config);
	  cursor=new Cursor();
		if (!updateColaLogrosEstadoCero()){
	 		   System.out.println("n****NO ACTUALIZn LOS LOGROS QUE ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
	 		   return;
	 	}
	  t=new Thread(new LogroGrupo(cursor,contextoTotal,path,path1,reportFile,reportFile_a,n++));
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
   
	public boolean updateColaLogrosEstadoCero(){
    Connection con=null;
  	PreparedStatement pst=null;
  	int posicion=1;    		
  	
  	try{
		  con=cursor.getConnection();
		  pst=con.prepareStatement(rb.getString("actualizar_logros_en_cola"));
			posicion=1;
		 	pst.executeUpdate();
		 	pst.close();
		 	System.out.println("actualizn en cola los reportes generando");
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
  	  System.out.println("LOGROS 2006 FINALIZANDO");
    }catch(Exception ex){}
  }
  
}
