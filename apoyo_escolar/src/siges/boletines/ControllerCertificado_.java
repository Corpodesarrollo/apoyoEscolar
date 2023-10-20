package siges.boletines;

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
import siges.dao.Ruta2;
import siges.boletines.Certificado_;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import siges.boletines.beans.FiltroBeanCertificados;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerCertificado_  extends HttpServlet{
  private static int i=0;
  private Certificado_ certificado;
  private ResourceBundle rb;
  private PreparedStatement pst=null;
  private ResultSet rs=null;
  private Cursor cursor;
  private File reportFile;
  private File reportFile1;
  private FiltroBeanCertificados filtro;
  private Thread t;
    
  public void init(ServletConfig config) throws ServletException{
 	 
    ServletContext context=config.getServletContext();
    rb=ResourceBundle.getBundle("preparedstatementscertificados");
 	String contextoTotal=context.getRealPath("/");
 	String path=Ruta2.get(context.getRealPath("/"),rb.getString("certificados_ruta_jaspers"));
 	String path1=Ruta2.get(context.getRealPath("/"),rb.getString("certificados_imgs"));
 	String path2=Ruta2.get(context.getRealPath("/"),rb.getString("certificados_imgs_inst"));
  	reportFile=new File(path+rb.getString("rep"));	
	reportFile1=new File(path+rb.getString("reppre"));
			
  	int n=1;
	  System.out.println("***CERTIFICADOS 2009 INICIANDO***");
	  super.init(config);
	  cursor=new Cursor();
		if (!updateColaCertificadosEstadoCero()){
	 		   System.out.println("n****NO ACTUALIZn LOS CERTIFICADOS Q ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
	 		   return;
	 		}	  
	  t=new Thread(new Certificado_(cursor,contextoTotal,path,path1,path2,reportFile,reportFile1,n++));	  
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
   
	public boolean updateColaCertificadosEstadoCero(){
    Connection con=null;
  	PreparedStatement pst=null;
  	int posicion=1;    		
  	
  	try{
		  con=cursor.getConnection();
		  pst=con.prepareStatement(rb.getString("actualizar_certificados_en_cola"));
			posicion=1;
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
  
}
