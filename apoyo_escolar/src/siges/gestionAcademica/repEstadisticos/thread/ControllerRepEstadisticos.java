package siges.gestionAcademica.repEstadisticos.thread;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.dao.Cursor;
import siges.gestionAcademica.repEstadisticos.dao.RepEstadisticosDAO;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class ControllerRepEstadisticos  extends HttpServlet{
	private static int i=0;
	private ResourceBundle rb;
	private Cursor cursor;
	private Thread t;
	private RepEstadisticosDAO repEstadisticosDAO;
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException{
		System.out.println("[APOYO ESCOLAR] REP ESTADISTICOS");
		ServletContext context=config.getServletContext();
		String contextoTotal = context.getRealPath("/");
		
		System.out.println(new Date()+" REPORTES ESTADISTICOS: APOYO ESCOLAR INICIANDO...");
		super.init(config);
		cursor=new Cursor();
		try{
			repEstadisticosDAO =   new RepEstadisticosDAO(cursor);
			if (!repEstadisticosDAO.updateColaCompEstadoCero()){
				System.out.println("[APOYO ESCOLAR ]" + new Date()+" REPORTES ESTADISTICOS: NO ACTUALIZn LOS REPORTES Q ESTABAN EN ESTADO CERO.");
				return;
			}	  
			t=new Thread(new RepEstadisticos(repEstadisticosDAO, contextoTotal));	  
			t.start();
		}catch (Exception e) {
			
			System.out.println("" +  e.getMessage() );
			e.printStackTrace();
		}
		return;
	}		
	
	
	
	
	
	
}
