package siges.gestionAdministrativa.repResultadosAca.thread;

import java.io.File;
import java.sql.Time;
import java.util.Date;
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
import siges.boletines.Boletin_;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import siges.boletines.beans.FiltroBeanReports;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerRepResultadosAca__  extends HttpServlet{
  private static int i=0;
  private Boletin_ boletin;
  private ResourceBundle rb;
//  private PreparedStatement pst=null;
//  private ResultSet rs=null;
  private Cursor cursor;
  private File reporte1_1;
  private File reporte1_2;
  private File reporte1_con;
  private File reporte2_1;
  private File reporte2_2;
  private File reporte3_1;
  private File reporte3_2;
  private File reporte4_1;  
  private File reporte4_2;
  private FiltroBeanReports filtro;
  private Thread t;
    
  public void init(ServletConfig config) throws ServletException{
 	 
    ServletContext context=config.getServletContext();
    rb=ResourceBundle.getBundle("siges.gestionAdministrativa.repResultadosAca.bundle.repResultadosAca");
 	String contextoTotal=context.getRealPath("/");
  	String path=Ruta2.get(context.getRealPath("/"),rb.getString("comparativos_ruta_jaspers"));
  	String path1=Ruta2.get(context.getRealPath("/"),rb.getString("comparativos_imgs"));
  	//String path2=Ruta2.get(context.getRealPath("/"),rb.getString("boletines_imgs_inst"));
  	reporte1_1=new File(path+rb.getString("comp.reporte1_1"));	
  	reporte1_2=new File(path+rb.getString("comp.reporte1_2"));
  	reporte1_con=new File(path+rb.getString("comp.reporte1_con"));
  	
	
  	int n=1;
	  System.out.println(new Date()+" REPORTES COMPARATIVOS: APOYO ESCOLAR 2010 INICIANDO...");
	  super.init(config);
	  cursor=new Cursor();
		if (!updateColaCompEstadoCero()){
	 		   System.out.println(new Date()+" REPORTES COMPARATIVOS: NO ACTUALIZn LOS REPORTES Q ESTABAN EN ESTADO CERO.");
	 		   return;
	 		}	  
	  //t=new Thread(new Comparativo(cursor,contextoTotal,path,path1,path2,reportFile,reportFile1,n++));	  
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
   
	public boolean updateColaCompEstadoCero(){
    Connection con=null;
  	PreparedStatement pst=null;
  	int posicion=1;    		
  	
  	try{
		  con=cursor.getConnection();
		  pst=con.prepareStatement(rb.getString("comparativo.updateEnCola"));
			posicion=1;
		 	pst.executeUpdate();
		 	pst.close();
		 	System.out.println("REPORTES COMPARATIVOS: actualizn en cola los reportes comparativos generando");
		 	con.commit();
		 	}
  	 catch(Exception e ){
  	    System.out.println("REPORTES COMPARATIVOS: nOcurrin Excepcion al actualizar el estado a -1!");
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