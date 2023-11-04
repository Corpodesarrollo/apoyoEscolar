package siges.librodeNotas;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.boletines.Boletin_;
import siges.boletines.beans.FiltroBeanReports;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta2;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerLibro  extends HttpServlet{
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
    
  @Override
public void init(ServletConfig config) throws ServletException{
 	 
    ServletContext context=config.getServletContext();
    rb=ResourceBundle.getBundle("siges.librodeNotas.bundle.libros");
 	String contextoTotal=context.getRealPath("/");
  	String path=Ruta2.get(context.getRealPath("/"),rb.getString("libros_ruta_jaspers"));
  	reporte1_1=new File(path+rb.getString("libros.reporte1_1"));	
  	reporte1_2=new File(path+rb.getString("libros.reporte1_2"));
  	
  	int n=1;
//	System.out.println(new Date()+" REPORTES LIBROS (new): APOYO ESCOLAR 2010 INICIANDO...");
	  super.init(config);
	  cursor=new Cursor();
		if (!updateColaBolEstadoCero()){
//	 		   System.out.println(new Date()+" REPORTES LIBROS: NO ACTUALIZn LOS REPORTES Q ESTABAN EN ESTADO CERO.");
	 		   return;
	 		}	  
	  Libro l = new Libro(cursor,contextoTotal,path);	  
	  l.procesar_solicitudes();// se dispara el procesamiento de solicitudes
	  return;
  }		
  
	/**
	*	Funcinn: Ejecuta los prepared statements correspondientes para actualzar el estado y ponerlo en la cola<BR>
	*	@param	String 
	*	@param	String 
	*	@param	String 
	*	@param	String 
	**/
   
	public boolean updateColaBolEstadoCero(){
    Connection con=null;
  	PreparedStatement pst=null;
  	int posicion=1;    		
  	
  	try{
		  con=cursor.getConnection();
		  pst=con.prepareStatement(rb.getString("libros.updateEnCola"));
			posicion=1;
		 	pst.executeUpdate();
		 	pst.close();
		 	System.out.println("REPORTES LIBROS: actualizn en cola los reportes libros generando");
		 	con.commit();
		 	}
  	 catch(Exception e ){
  	    System.out.println("REPORTES LIBROS: nOcurrin Excepcion al actualizar el estado a -1!");
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
