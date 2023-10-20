package siges.batch.plantilla.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class PlantillaBatchDAO extends Dao{
    private Cursor cursor;
    ResourceBundle rb;
    
    public PlantillaBatchDAO(Cursor c){
        super(c);
        cursor=c;
        rb=ResourceBundle.getBundle("batch");
    }

    public void finalizarInst(String inst,String per,int estado0,int estado1,String msg){
        String val;
  			Connection cn=null;
  			PreparedStatement pst=null;
  			String con=rb.getString("solicitud.actualizar3");		
  			try{
  			  cn=DataSourceManager.getConnection(1);
  				pst=cn.prepareStatement(con);
  				int pos=1;
  				pst.setLong(pos++,estado1);
  				pst.setString(pos++,msg);
  				pst.setString(pos++,inst);
  				pst.setString(pos++,per);
  				pst.setLong(pos++,estado0);
  				int n=pst.executeUpdate();
  				//System.out.println("aqctualizo="+n);
  			}catch(InternalErrorException e){return;}
  			catch(SQLException e){System.out.println("Error act inst :"+e);return;}
  			finally{
  				try{
  					OperacionesGenerales.closeStatement(pst);
  					OperacionesGenerales.closeConnection(cn);
  				}catch(Exception e){}
  			}
    	}

    public void actualizarInst(String inst,String per,int estado0,int estado1,String msg){
        String val;
  			Connection cn=null;
  			PreparedStatement pst=null;
  			String con=rb.getString("solicitud.actualizar2");		
  			try{
  			  cn=DataSourceManager.getConnection(1);
  				pst=cn.prepareStatement(con);
  				int pos=1;
  				pst.setLong(pos++,estado1);
  				pst.setString(pos++,msg);
  				pst.setString(pos++,inst);
  				pst.setString(pos++,per);
  				pst.setLong(pos++,estado0);
  				int n=pst.executeUpdate();
  				//System.out.println("aqctualizo="+n);
  			}catch(InternalErrorException e){return;}
  			catch(SQLException e){System.out.println("Error act inst :"+e);return;}
  			finally{
  				try{
  					OperacionesGenerales.closeStatement(pst);
  					OperacionesGenerales.closeConnection(cn);
  				}catch(Exception e){}
  			}
    	}

    public void actualizarTodas(){
        String val;
    		Connection cn=null;
    		PreparedStatement pst=null;
    		String con=rb.getString("solicitud.actualizar");		
    		try{
    		  cn=DataSourceManager.getConnection(1);
    			pst=cn.prepareStatement(con);
    			pst.setLong(1,-1);
    			pst.setLong(2,0);
    			int n=pst.executeUpdate();
    			//System.out.println("aqctualizo="+n);
    		}catch(InternalErrorException e){return;}
    		catch(SQLException e){System.out.println("Error act inst :"+e);return;}
    		finally{
    			try{
    				OperacionesGenerales.closeStatement(pst);
    				OperacionesGenerales.closeConnection(cn);
    			}catch(Exception e){}
    		}
    	}
    
}
