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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import siges.boletines.beans.FiltroBeanFormulario;
import siges.boletines.dao.ReporteLogrosDAO;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerReporte  extends HttpServlet{
  private static int i=0;  
  private ResourceBundle rb;
  private Cursor cursor;
  private File reportFile;
  private FiltroBeanFormulario filtro;
  private Thread t;
  private ReporteLogrosDAO reporteDao;
    
  public void init(ServletConfig config) throws ServletException{
 	 
    ServletContext context=config.getServletContext();
    rb=ResourceBundle.getBundle("preparedstatements_logros_pendientes");
 	String contextoTotal=context.getRealPath("/");
  	String path=Ruta2.get(context.getRealPath("/"),rb.getString("ruta_jaspers"));
  	String path1=Ruta2.get(context.getRealPath("/"),rb.getString("ruta_img"));
  	
  	reportFile=	new File(path+rb.getString("jasper_logros"));	
  	int n=1;
	  System.out.println("LOGROS_PEND INICIANDO");
	  super.init(config);
	  cursor=new Cursor();
	  reporteDao=new ReporteLogrosDAO(cursor);
	  
		if (!reporteDao.updateColaReportesEstadoCero("actualizar_reportes_en_cola")){
	 		   System.out.println("n****NO ACTUALIZn LOS REPORTES Q ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
	 		   return;
	 		}	  
	  t=new Thread(new ReporteLogroPendiente(cursor,contextoTotal,path,path1,reportFile,n++));
	  t.start();
	  return;
  }		
  
}
