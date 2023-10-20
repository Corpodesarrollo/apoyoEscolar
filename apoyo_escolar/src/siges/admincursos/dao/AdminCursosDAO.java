package siges.admincursos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.admincursos.beans.AdminCursos;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class AdminCursosDAO extends Dao{
    
    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;
    
    public AdminCursosDAO (Cursor cur){
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("admincursos");
    }
    
    public boolean actualizarCursos(AdminCursos ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("ActualizarCursos"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(ac.getNombre().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getNombre().trim());
   	        if(ac.getCupos().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getCupos().trim());
   	        if(ac.getGrupos().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getGrupos().trim());
   	        if(ac.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getJornada().trim());
   	        if(ac.getCupototal().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getCupototal().trim());
   	        if(ac.getVisible().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getVisible().trim());
   	        if(ac.getNivel().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setInt(posicion++,Integer.parseInt(ac.getNivel().trim()));
   	        if(ac.getResponsable().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getResponsable().trim());
   	        if(ac.getCorreoresp().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getCorreoresp().trim());
   	        if(ac.getCodigo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setInt(posicion++,Integer.parseInt(ac.getCodigo().trim()));
   	        
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando actualizar registro de curso. Posible problema: ");
        switch(sqle.getErrorCode()){
        default:
            setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
        }
        return false;
        }finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public AdminCursos asignarCursos(String id){
        AdminCursos ac=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("AsignarCursos"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setInt(posicion++,Integer.parseInt(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next()){
    	      	    i=1;
    	      	    ac= new AdminCursos();
    	      	    ac.setEstado("1");
    	      	    ac.setNombre(rs.getString(i++));
    	      	    ac.setCupos(rs.getString(i++));
    	      	    ac.setGrupos(rs.getString(i++));
    	      	    ac.setJornada(rs.getString(i++));
    	      	    ac.setCupototal(rs.getString(i++));
    	      	    ac.setVisible(rs.getString(i++));
    	      	    ac.setCodigo(rs.getString(i++));
    	      	    ac.setNivel(rs.getString(i++));
    	      	    ac.setResponsable(rs.getString(i++));
    	      	    ac.setCorreoresp(rs.getString(i++));
    	      	}
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información del curso. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return ac;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return ac;
    }
    
    public boolean eliminarCursos(String id){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("EliminarCursos"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(id.trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);	        
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException s){}
   	    setMensaje("Error SQL intentando ELIMINAR curso. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return false;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return true;
    }
    
    public boolean insertarCursos(AdminCursos ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("InsertarCursos"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(ac.getNombre().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getNombre().trim());
   	        if(ac.getCupos().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getCupos().trim());
   	        if(ac.getGrupos().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getGrupos().trim());
   	        if(ac.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getJornada().trim());
   	        if(ac.getCupototal().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getCupototal().trim());
   	        if(ac.getVisible().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getVisible().trim());
   	        if(ac.getNivel().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setInt(posicion++,Integer.parseInt(ac.getNivel().trim()));
   	        if(ac.getResponsable().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getResponsable().trim());
   	        if(ac.getCorreoresp().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getCorreoresp().trim());
   	        
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando ingresar información del curso. Posible problema: ");
        switch(sqle.getErrorCode()){
        default:
            setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
        }
        return false;
        }finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public boolean validarExistenciaCursos(String id){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("ValidarCursos"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next())	return false;
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información del curso. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return false;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return true;    
    }
    
    public boolean compararBeansCursos(AdminCursos ac,AdminCursos ac2){
        if(!ac.getNombre().equals(ac2.getNombre())) return false;
        if(!ac.getCupos().equals(ac2.getCupos())) return false;
        if(!ac.getGrupos().equals(ac2.getGrupos())) return false;
        if(!ac.getJornada().equals(ac2.getJornada())) return false;
        if(!ac.getCupototal().equals(ac2.getCupototal())) return false;
        if(!ac.getVisible().equals(ac2.getVisible())) return false;
        if(!ac.getResponsable().equals(ac2.getResponsable())) return false;
        if(!ac.getCorreoresp().equals(ac2.getCorreoresp())) return false;
        return true;
    }
    
    
    public List getMetodologias(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO itemVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getMetodologias"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				itemVO=new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				//itemVO.setPadre(rs.getLong(i++));
				l.add(itemVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
    }

}