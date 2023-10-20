package siges.filtro.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import siges.dao.OperacionesGenerales;
import siges.filtro.vo.FichaVO;
import siges.filtro.vo.RecursoVO;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.login.beans.*;
import siges.exceptions.InternalErrorException;

public class FiltroDAO extends Dao {
	
	public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;
    
    public FiltroDAO(Cursor cur){
    	super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("common");
    }
    
    public FichaVO[] getFichas(String serv, Login log){
		FichaVO[] f=null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection c=new ArrayList();
		try{
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getFichas"));
			posicion = 1;
			ps.setString(posicion++,serv);
			ps.setInt(posicion++,Integer.parseInt(log.getPerfil()));
			rs = ps.executeQuery();
			FichaVO ff=null;
			while(rs.next()){
				ff=new FichaVO();
				ff.setFichaId(rs.getString(1));
				ff.setFichaNombre(rs.getString(2));
				ff.setFichaUrl(rs.getString(3));
				ff.setFichaUrl2(rs.getString(4));
				c.add(ff);
			}			
			rs.close();ps.close();
			if(!c.isEmpty()){
				int i=0;
				f=new FichaVO[c.size()];
				Iterator iterator =c.iterator();
				while (iterator.hasNext())
					f[i++]=(FichaVO)(iterator.next());
			}
		}catch(SQLException sqle){sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando validar servicio Posible problema: "+sqle);
		}catch(Exception sqle){sqle.printStackTrace();
			setMensaje("Error intentando validar servicio Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){inte.printStackTrace();}
		}
		return f;
	}

	/**
	 * @function:  Retorna un objeto con la fotografia del estudiante
	 * @param cdigo
	 * @return
	 * @throws Exception
	 */
	public RecursoVO getFotografiaEstudiante(long codigo) throws Exception{
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		RecursoVO recursoVO=null;
		int posicion=1;
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getFotografiaEstudiante"));
			posicion=1;
			pst.setLong(posicion++,codigo);
			rs=pst.executeQuery();
			if(rs.next()){
				posicion=1;
				recursoVO=new RecursoVO();
				recursoVO.setMime("image/jpg");
				Blob blob = rs.getBlob(posicion++);
				if (blob != null) {
					long len = blob.length();
					recursoVO.setBytes(blob.getBytes((long) 1, (int) len));
				}
				return recursoVO;
			}
		}catch(Exception sqle){
			throw new Exception(sqle.getMessage(),sqle);
		}finally{
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return null;
	}

	/**
	 * @function:  Retorna un objeto con la fotografia del estudiante
	 * @param cdigo
	 * @return
	 * @throws Exception
	 */
	public RecursoVO getFotografiaPersonal(long codigo) throws Exception{
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		RecursoVO recursoVO=null;
		int posicion=1;
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getFotografiaPersonal"));
			posicion=1;
			pst.setLong(posicion++,codigo);
			rs=pst.executeQuery();
			if(rs.next()){
				posicion=1;
				recursoVO=new RecursoVO();
				recursoVO.setMime("image/jpg");
				Blob blob = rs.getBlob(posicion++);
				if (blob != null) {
					long len = blob.length();
					recursoVO.setBytes(blob.getBytes((long) 1, (int) len));
				}
				return recursoVO;
			}
		}catch(Exception sqle){
			throw new Exception(sqle.getMessage(),sqle);
		}finally{
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return null;
	}

    public String getBase(){
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getBase"));
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}		
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){inte.printStackTrace();}
		}
		return null;
	}
}
