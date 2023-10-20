package siges.gestionAdministrativa.repResultadosAca.thread;



import java.io.File;
import java.util.Date;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import siges.dao.OperacionesGenerales;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.boletines.Boletin_;
import java.sql.Connection;
import java.sql.PreparedStatement;
import siges.boletines.beans.FiltroBeanReports;



/**
 * Servlet encargado de ejecutar reporte de Comparativo de vigencia anterior
 * @author xDEAMx
 *
 */
public class ControllerRepResultadosComVigAnterior   extends HttpServlet{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static int i=0;
  private Boletin_ boletin;
  private ResourceBundle rb;
//  private PreparedStatement pst=null;
//  private ResultSet rs=null;
  private Cursor cursor;
  private File reporte1_1;
  
  private FiltroBeanReports filtro;
  private Thread t;
    
  public void init(ServletConfig config) throws ServletException{
 	 
    ServletContext context=config.getServletContext();
    rb=ResourceBundle.getBundle("siges.gestionAdministrativa.repResultadosAca.bundle.repResultadosAca");
    
    String contextoTotal=context.getRealPath("/");
  	String path_jasper=Ruta2.get(context.getRealPath("/"),rb.getString("rep_ruta_jaspers"));
  	String path_escudo=Ruta.get(context.getRealPath("/"),rb.getString("rep_escudo"));
  	//String path2=Ruta2.get(context.getRealPath("/"),rb.getString("boletines_imgs_inst"));
  	String path2="";
  	reporte1_1=new File(path_jasper+rb.getString("rep.reporte4"));	
  	  	
  	int n=1;
	  //System.out.println(new Date()+" REPORTES BOLETINES (new): APOYO ESCOLAR 2010 INICIANDO...");
	  super.init(config);
	  cursor=new Cursor();
		if (!updateColaBolEstadoCero()){
	 		   System.out.println(new Date()+" REPORTES BOLETINES: NO ACTUALIZn LOS REPORTES Q ESTABAN EN ESTADO CERO.");
	 		   return;
	 		}	  
	  t=new Thread(new RepResultadosComVigAnterior(cursor,contextoTotal,path_jasper,path_escudo,reporte1_1,n++));	  
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
   
  public boolean updateColaBolEstadoCero(){
	    Connection con=null;
	  	PreparedStatement pst=null;
	  	int posicion=1;    		
	  	
	  	try{
			  con=cursor.getConnection();
			  pst=con.prepareStatement(rb.getString("rep.updateEnCola"));
				posicion=1;
			 	pst.executeUpdate();
			 	pst.close();
			 	System.out.println("REPORTES RESULTADOS: actualizn en cola los reportes Resultados generando");
			 	con.commit();
			 	}
	  	 catch(Exception e ){
	  	    System.out.println("REPORTES RESULTADOS: nOcurrin Excepcion al actualizar el estado a -1!");
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
