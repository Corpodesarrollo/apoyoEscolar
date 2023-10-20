package siges.adminconflicto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.adminconflicto.beans.*;
import siges.conflicto.beans.InfluenciaConflictos;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class AdminConflictoDAO extends Dao{
    
    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;
    
    public AdminConflictoDAO(Cursor cur) {
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("adminconflicto");
    }
    
    public AdminConflicto asignarCategoria(String cat){
        AdminConflicto ac=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("AsignarCategoria"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(cat.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(cat.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next()){
    	      	    i=1;
    	      	    ac=new AdminConflicto();
    	      	    ac.setEstado("1");
    	      	    ac.setIdcategoria(rs.getString(i++));
    	      	    ac.setValorcategoria(rs.getString(i++));
    	      	    ac.setOrdencategoria(rs.getString(i++));
    	      	}
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
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
    
    public AdminConflicto asignarClase(String cla){
        AdminConflicto ac=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("AsignarClase"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(cla.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(cla.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next()){
    	      	    i=1;
    	      	    ac=new AdminConflicto();
    	      	    ac.setEstado("1");
    	      	    ac.setIdclase(rs.getString(i++));
    	      	    ac.setValorclase(rs.getString(i++));
    	      	    ac.setOrdenclase(rs.getString(i++));
    	      	}
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
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
    
    public AdminConflicto asignarTipo(String cla){
        AdminConflicto ac=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("AsignarTipo"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(cla.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(cla.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next()){
    	      	    i=1;
    	      	    ac=new AdminConflicto();
    	      	    ac.setEstado("1");
    	      	    ac.setIdtipo(rs.getString(i++));
    	      	    ac.setValortipo(rs.getString(i++));
    	      	    ac.setCategoriatipo(rs.getString(i++));
    	      	    ac.setClasetipo(rs.getString(i++));
    	      	    ac.setTipodesctipo(rs.getString(i++));
    	      	    ac.setDescripciontipo(rs.getString(i++));
    	      	    ac.setOrdentipo(rs.getString(i++));
    	      	}
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
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
    
    public boolean eliminarCategoria(String id){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("EliminarCategoria"));
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
   	    setMensaje("Error SQL intentando ELIMINAR Categoria de Conflicto escolar. Posible problema: ");
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
    
    public boolean eliminarClase(String id){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("EliminarClase"));
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
   	    setMensaje("Error SQL intentando ELIMINAR Clase de Conflicto escolar. Posible problema: ");
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
    
    public boolean eliminarTipo(String id){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("EliminarTipo"));
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
   	    setMensaje("Error SQL intentando ELIMINAR Tipo de Conflicto escolar. Posible problema: ");
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
    
    public boolean insertarCategoria(AdminConflicto ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("InsertarCategoria"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(ac.getValorcategoria().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getValorcategoria().trim());
   	        if(ac.getOrdencategoria().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getOrdencategoria().trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
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
    
    public boolean actualizarCategoria(AdminConflicto ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("ActualizarCategoria"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(ac.getValorcategoria().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getValorcategoria().trim());
   	        if(ac.getOrdencategoria().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getOrdencategoria().trim()));
   	        if(ac.getIdcategoria().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getIdcategoria().trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
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
    
    public boolean insertarClase(AdminConflicto ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("InsertarClase"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(ac.getValorclase().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getValorclase().trim());
   	        if(ac.getOrdenclase().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getOrdenclase().trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
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
    
    public boolean actualizarClase(AdminConflicto ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("ActualizarClase"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(ac.getValorclase().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getValorclase().trim());
   	        if(ac.getOrdenclase().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getOrdenclase().trim()));
   	        if(ac.getIdclase().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getIdclase().trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
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
    
    public boolean insertarTipo(AdminConflicto ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("InsertarTipo"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(ac.getValortipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getValortipo().trim());
   	        if(ac.getCategoriatipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getCategoriatipo().trim()));
   	        if(ac.getClasetipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getClasetipo().trim()));
   	        if(ac.getTipodesctipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getTipodesctipo().trim()));
   	        if(ac.getDescripciontipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getDescripciontipo().trim());
   	        if(ac.getOrdentipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getOrdentipo().trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
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
    
    public boolean actualizarTipo(AdminConflicto ac){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("ActualizarTipo"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(ac.getValortipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getValortipo().trim());
   	        if(ac.getCategoriatipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getCategoriatipo().trim()));
   	        if(ac.getClasetipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getClasetipo().trim()));
   	        if(ac.getTipodesctipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getTipodesctipo().trim()));
   	        if(ac.getDescripciontipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setString(posicion++,ac.getDescripciontipo().trim());
   	        if(ac.getOrdentipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getOrdentipo().trim()));
   	        if(ac.getIdtipo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(ac.getIdtipo().trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException se){}
        setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
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
    
    public boolean compararBeansCategoria(AdminConflicto ac,AdminConflicto ac2){
        if(!ac.getValorcategoria().equals(ac2.getValorcategoria())) return false;
        if(!ac.getOrdencategoria().equals(ac2.getOrdencategoria())) return false;
        return true;
    }
    
    public boolean compararBeansClase(AdminConflicto ac,AdminConflicto ac2){
        if(!ac.getValorclase().equals(ac2.getValorclase())) return false;
        if(!ac.getOrdenclase().equals(ac2.getOrdenclase())) return false;
        return true;
    }
    
    public boolean compararBeansTipo(AdminConflicto ac,AdminConflicto ac2){
        if(!ac.getValortipo().equals(ac2.getValortipo())) return false;
        if(!ac.getTipodesctipo().equals(ac2.getTipodesctipo())) return false;
        if(!ac.getDescripciontipo().equals(ac2.getDescripciontipo())) return false;
        if(!ac.getClasetipo().equals(ac2.getClasetipo())) return false;
        if(!ac.getCategoriatipo().equals(ac2.getCategoriatipo())) return false;
        if(!ac.getOrdentipo().equals(ac2.getOrdentipo())) return false;
        return true;
    }
    
    public boolean validarExistenciaCategoria(String id){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("ValidarCategoria_valor"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next())	return false;
    	      	rs.close();
    	      	pst.close();
    	      	pst=cn.prepareStatement(rb.getString("ValidarCategoria_tipo"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next())	return false;
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
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
    
    public boolean validarExistenciaClase(String id){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("ValidarClase_valor"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next())	return false;
    	      	rs.close();
    	      	pst.close();
    	      	pst=cn.prepareStatement(rb.getString("ValidarClase_tipo"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next())	return false;
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
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
    
    public boolean validarExistenciaTipo(String id){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
            cn=cursor.getConnection();
      	  			pst=cn.prepareStatement(rb.getString("ValidarTipo_valor"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next())	return false;
    	      	rs.close();
    	      	pst.close();
    	      	pst=cn.prepareStatement(rb.getString("ValidarTipo_Grupo"));
      	  			pst.clearParameters();
      	  			posicion=1;
      	  			if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      	  			else pst.setLong(posicion++,Long.parseLong(id.trim()));
      	  			rs = pst.executeQuery();
    	      	if(rs.next())	return false;
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
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

}