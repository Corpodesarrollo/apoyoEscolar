/**
 * 
 */
package siges.gestionAdministrativa.integracionNomina.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.padreFlia.vo.EstudianteMarcarVO;
import siges.gestionAdministrativa.padreFlia.vo.FiltroBoletinVO;
;


/**
 * 17/05/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class IntegracionNominaDAO  extends Dao{
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	/**
	 * Constructor
	 *  
	 * @param c Objeto para la obtencinn de conexiones
	 */
	public IntegracionNominaDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("siges.gestionAdministrativa.integracionNomina.bundle.integracionNomina");
	}
	
	
	/**
	 * Devuelve una conexinn activa  
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
		return cursor.getConnection();
	}
	
	
	
	
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public void callProcedNomina(String usu) throws Exception{
		System.out.println("callProcedNomina " + new Date() );
		Connection cn=null;
		CallableStatement ct=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			ct=cn.prepareCall(rb.getString("callProcedNomina"));
			ct.setString(posicion++,usu);
			System.out.println(new Date() + " Fin callProcedNomina procedimineto " + ct.execute());
		 
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(ct);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	 
	}
	
	  
		
}
