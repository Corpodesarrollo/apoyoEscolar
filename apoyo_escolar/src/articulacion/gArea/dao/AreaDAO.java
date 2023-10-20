package articulacion.gArea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.gArea.vo.GAreaVO;



	public class AreaDAO extends Dao{
		public AreaDAO(Cursor c){
			super(c);
			rb=ResourceBundle.getBundle("articulacion.gArea.bundle.sentencias");
		}
	
	
	
	public boolean insertar(GAreaVO area){
		Connection conect=null;
        PreparedStatement prepST=null;
        int posicion=1;
        boolean retorno=false;
        try {
        	
			conect=cursor.getConnection();
			prepST=conect.prepareStatement(rb.getString("insercion"));
			
			prepST.setLong(posicion++,area.getGeArtAreaCodigo());
			prepST.setString(posicion++,area.getGeArtAreaNombre());
			prepST.setInt(posicion++,area.getGeArtAreaTipo());
			prepST.execute();
			retorno=true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno=false;
		} catch (SQLException e) {
			switch(e.getErrorCode()){
				case 1:case 2291: 
					setMensaje("Cndigo duplicado ");
		  		break;
				case 2292:
					setMensaje("Registros Asociados ");
		  		break;
		  		//Longitud de campo
				case 1401:
					setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
					break;
			}
			e.printStackTrace();
			retorno=false;
		}finally{
			try{
	        	OperacionesGenerales.closeStatement(prepST);
	        	OperacionesGenerales.closeConnection(conect);
				}catch(InternalErrorException inte){}
		}
		return retorno;
	}
	public boolean eliminar(String codArea){
		Connection cn = null;
		PreparedStatement ps = null;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminacion"));
			ps.setLong(1,Long.parseLong(codArea));
			ps.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			//setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
			switch(sqle.getErrorCode()){
				//Violacinn de llave primaria
			case 2292:
				setMensaje("Registros Asociados ");
			}
			return false;
		}catch(Exception sqle){
			setMensaje("Error eliminando Perfil. Posible problema: "+sqle.toString());
			return false;
		}
		finally{
			try{
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	public GAreaVO asignar(String codArea){
		GAreaVO areaVO=null;
		Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
        	
            cn=cursor.getConnection();
            ps=cn.prepareStatement(rb.getString("asignacion"));
            ps.clearParameters();
            posicion=1;
  			ps.setLong(posicion++,Long.parseLong(codArea));
            rs=ps.executeQuery();
            if(rs.next()){
            	i=1;
            	areaVO=new GAreaVO();
            	areaVO.setFormaEstado("1");
            	areaVO.setGeArtAreaCodigo(rs.getLong(i++));
            	areaVO.setGeArtAreaNombre(rs.getString(i++));
            	areaVO.setGeArtAreaTipo(rs.getInt(i++));
            }
        }catch(SQLException sqle){
        	sqle.printStackTrace();
        	try{
        		cn.rollback();
        		}
        	catch(SQLException s){}
        	setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
        }catch(Exception sqle){
        	sqle.printStackTrace();
        	setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
        }
        finally{
			try{
        	OperacionesGenerales.closeResultSet(rs);
        	OperacionesGenerales.closeStatement(ps);
        	OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
        }
        return areaVO;
	}
	
	public boolean actualizar(GAreaVO area1,GAreaVO area2){
		boolean retorno=false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion=1;
			try {
				cn = cursor.getConnection();
				cn.setAutoCommit(false);
				ps = cn.prepareStatement(rb.getString("actualizar"));
				
				ps.setLong(posicion++,area1.getGeArtAreaCodigo());
				
				if(area1.getGeArtAreaNombre().equals(""))
					ps.setNull(posicion++,Types.VARCHAR);
				else
					ps.setString(posicion++,area1.getGeArtAreaNombre());
				
				if(area1.getGeArtAreaTipo()==0)
					ps.setNull(posicion++,Types.VARCHAR);
				else
					ps.setInt(posicion++,area1.getGeArtAreaTipo());
				
				ps.setLong(posicion++,area2.getGeArtAreaCodigo());
				ps.execute();
				cn.commit();
				retorno=true;
			}catch(SQLException e){
				e.printStackTrace();
				switch (e.getErrorCode()) {
				case 1: case 2291:
					setMensaje("Cndigo duplicado ");
				break;
				case 2292:
					setMensaje("Registros Asociados ");
				break;
				}
				try{
					cn.rollback();//en caso que falle la actualizacion, sedebe retroceder la accinn
				}catch(SQLException s){}
				
				return false;
			}catch(Exception e){
				e.printStackTrace();
				setMensaje("Error actualizando Area. Posible problema: "+e.toString());
				return false;
			}
			finally{
				try{
					OperacionesGenerales.closeStatement(ps);
					OperacionesGenerales.closeConnection(cn);
				}catch(InternalErrorException inte){}
			}
			
		return retorno;
	}
	
	public List getAllListaArea(){
		Connection cn=null;
		Statement st=null;
		ResultSet rs=null;
		List listaArea=new ArrayList();
		GAreaVO areaVO=null;
		
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.createStatement();
			rs=st.executeQuery(rb.getString("lista"));
			while(rs.next()){
				i=1;
				areaVO=new GAreaVO();
				areaVO.setGeArtAreaCodigo(rs.getLong(i++));
				areaVO.setGeArtAreaNombre(rs.getString(i++));
				areaVO.setGeArtAreaTipo(rs.getInt(i++));
				
				listaArea.add(areaVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return listaArea;
	
	}
}
