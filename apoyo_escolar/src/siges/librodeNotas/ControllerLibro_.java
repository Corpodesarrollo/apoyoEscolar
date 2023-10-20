package siges.librodeNotas;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import siges.dao.Ruta2;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.librodeNotas.beans.FiltroBeanLibro;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerLibro_ extends HttpServlet{
  private static int i=0;
  private Libro_ libro;
  private ResourceBundle rb;
  private PreparedStatement pst=null;
  private ResultSet rs=null;
  private Cursor cursor;
  private File reportFile;
  private File reportFile1;
  private FiltroBeanLibro filtro;
  private Thread t;
  private ResourceBundle r;
    
  public void init(ServletConfig config) throws ServletException{
    rb=ResourceBundle.getBundle("preparedstatements_librosdenotas");  	
 	 
    ServletContext context=config.getServletContext();
 	String contextoTotal=context.getRealPath("/");
  	String path=Ruta2.get(context.getRealPath("/"),rb.getString("jaspers"));
  	String path1=Ruta2.get(context.getRealPath("/"),rb.getString("imgs"));
  	String path2=Ruta2.get(context.getRealPath("/"),rb.getString("boletines_imgs_inst"));
  	reportFile=new File(path+rb.getString("rep"));	
	reportFile1=new File(path+rb.getString("repre"));
  	int n=1; 
	System.out.println("LIBRO_NOTAS 2007 INICIANDOO");
	super.init(config);
	cursor=new Cursor();	  
	if (!updateColaLibrosEstadoCero()){
		System.out.println("n****NO ACTUALIZn LOS LIBROS Q ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
		return;
	}
	t=new Thread(new Libro_(cursor,contextoTotal,path,path1,path2,reportFile,reportFile1,n++)); 
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
   
	public boolean updateColaLibrosEstadoCero(){
    Connection con=null;
  	PreparedStatement pst=null;
  	int posicion=1;    		
  	
  	try{
		  con=cursor.getConnection();
		  pst=con.prepareStatement(rb.getString("actualizar_libros_en_cola"));
			posicion=1;
		 	pst.executeUpdate();
		 	pst.close();
		 	con.commit();
		 	System.out.println("actualizn en cola los libros generando");
	   }
  	 catch(Exception e ){
  	    System.out.println("nOcurrin Excepcion al actualizar el estado a -1!");
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
  	  System.out.println("LIBRO_NOTAS 2006 FINALIZANDO");
    }catch(Exception ex){}
  }
  
}
