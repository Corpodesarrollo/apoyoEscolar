package siges.util;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class UrlHilo extends Thread{
  private String[] consulta;
  private String[] params;//dominio, //mensaje // logger
    
  public UrlHilo(String[] consulta, String[] params){
    this.consulta=consulta;
    this.params=params;
  }
    
  /*
   * Corre el hilo que hace la urlconnection
   * @see java.lang.Runnable#run()
   */
   public void run(){
   	try{
   		java.net.URL url=new URL(params[0]);
    	URLConnection con=url.openConnection();
    	con.setDoOutput(true);
    	OutputStreamWriter buffersalida = new OutputStreamWriter(con.getOutputStream());		
    	if(consulta!=null){    			
	    	for(int i=0;i<consulta.length;i++){
	    		if(i==0) buffersalida.write(consulta[i]);
	    		else buffersalida.write("&"+consulta[i]);
	    	}
    	}
    	buffersalida.flush();buffersalida.close();
    	InputStream is=con.getInputStream();is.close();
    	}catch(Exception e){
   			try{
					Connection conn=DriverManager.getConnection("jdbc:default:connection:");
    			PreparedStatement stmt = conn.prepareStatement(params[2]);
    			stmt.setString(1,"-1");
    			stmt.setString(2,params[1]+"("+e+").");
    			stmt.setString(3,"9");
    			stmt.setString(4,"1");
    			stmt.setString(5,"siges.util.UrlOracle");
    			stmt.executeUpdate();
    			stmt.close();
    		}catch(SQLException es){return;}    		    
    	}  		
    }

   /*
    * Log de transacciones
    */
  	public void log(String mensaje){
		try{
			Connection conn=DriverManager.getConnection("jdbc:default:connection:");
			PreparedStatement stmt = conn.prepareStatement(params[2]);
			stmt.setString(1,"-1");
			stmt.setString(2,""+mensaje+".");
			stmt.setString(3,"9");
			stmt.setString(4,"1");
			stmt.setString(5,"siges.util.UrlOracle");
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException es){return;}    		    
  }	
}