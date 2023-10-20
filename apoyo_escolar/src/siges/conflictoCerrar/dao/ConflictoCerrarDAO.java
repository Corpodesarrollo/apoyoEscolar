package siges.conflictoCerrar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.conflictoCerrar.beans.*;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ConflictoCerrarDAO extends Dao{
    
    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;
    public static final int GRUPOCERRADO=1;
    public static final int GRUPOABIERTO=0;
    public static final int PERIODOCERRADO=1;
    public static final int PERIODOABIERTO=0;
    
    public ConflictoCerrarDAO(Cursor cur) {
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("conflictoCerrar");
    }
    
    public List obtenerPeridos(long vigencia){
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs=null;
    	int posicion=1;
    	List periodos = new ArrayList();

    	try{
    		cn=cursor.getConnection(); cn.setAutoCommit(false);
    		pst=cn.prepareStatement(rb.getString("periodos_vigencia"));
    		pst.clearParameters();
    		posicion=1;

    		pst.setLong(posicion++,vigencia);

    		rs = pst.executeQuery();
    		while(rs.next()){
    			periodos.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
    		}
    	}catch(InternalErrorException in){
    		in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return periodos;
    	}
    	catch(SQLException sqle){
    		sqle.printStackTrace();
    		try{
    			cn.rollback();
    		}catch(SQLException se){
    			
    		}
    		setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
    		switch(sqle.getErrorCode()){
    		default:
    			setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
    	}
    	return periodos;
    	}finally{
    		try{
    			OperacionesGenerales.closeResultSet(rs);
    			OperacionesGenerales.closeStatement(pst);
    			OperacionesGenerales.closeConnection(cn);
    		}catch(InternalErrorException inte){}
    	}
    	return periodos;
    }
    
    public boolean abrirGrupo(ConflictoCerrar cc, Login login){
    	System.out.println("*** en DAO:abrirGrupo ***");
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        boolean band=false;
        int estado=-1;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);
            
            if(cc.getPeriodo().equals("10")){
      			pst=cn.prepareStatement(rb.getString("EstadoCierreGrupo1"));
      		}else if(cc.getPeriodo().equals("20")){
      			pst=cn.prepareStatement(rb.getString("EstadoCierreGrupo2"));
      		}else{
      			pst=cn.prepareStatement(rb.getString("EstadoCierreGrupo"+cc.getPeriodo()));
   	        }
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	        pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	        pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	        if(cc.getMetodologia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getMetodologia().trim()));
   	        if(cc.getGrado().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrado().trim()));
   	        if(cc.getGrupo().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrupo().trim()));
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        rs=pst.executeQuery();
   	        if(rs.next())	estado=rs.getInt(1);
   	        rs.close();
   	        pst.close();
   	        System.out.println("*** estado ***"+estado);
   	        if(estado!=-1){
   	            if(estado==GRUPOCERRADO){
   	            	if(cc.getPeriodo().equals("10")){
	   	       			pst=cn.prepareStatement(rb.getString("ActualizarCierreGrupo1"));
	   	       		}else if(cc.getPeriodo().equals("20")){
	   	       			pst=cn.prepareStatement(rb.getString("ActualizarCierreGrupo2"));
	   	       		}else{
	   	       			pst=cn.prepareStatement(rb.getString("ActualizarCierreGrupo"+cc.getPeriodo()));
	   	       		}
   	                pst.clearParameters();
   	                posicion=1;
   	                
   	                pst.setInt(posicion++,GRUPOABIERTO);
   	                if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	                if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	                if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	    	        if(cc.getMetodologia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	    	        else pst.setLong(posicion++,Long.parseLong(cc.getMetodologia().trim()));
   	                if(cc.getGrado().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getGrado().trim()));
   	                if(cc.getGrupo().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getGrupo().trim()));
   	                if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	                pst.executeUpdate();
   	   	        
   	                band=true;
   	            }
   	        }
   	        cn.commit();
      						cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
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
        return band;
    }
    
    public boolean abrirPeriodo(ConflictoCerrar cc, Login login){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        boolean band=false;
        int estado=-1;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);
            if(cc.getPeriodo().equals("10")){
	       			pst=cn.prepareStatement(rb.getString("EstadoCierrePeriodo1"));
       		}else if(cc.getPeriodo().equals("20")){
       			pst=cn.prepareStatement(rb.getString("EstadoCierrePeriodo2"));
       		}else{
       			pst=cn.prepareStatement(rb.getString("EstadoCierrePeriodo"+cc.getPeriodo()));
       		}
            	
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	        if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	        if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        rs=pst.executeQuery();
   	        if(rs.next())	estado=rs.getInt(1);
   	        rs.close();
   	        pst.close();
   	        if(estado!=-1){
   	            if(estado==PERIODOCERRADO){
   	            	if(cc.getPeriodo().equals("10")){
   		       			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo1"));
	   	       		}else if(cc.getPeriodo().equals("20")){
	   	       			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo2"));
	   	       		}else{
	   	       			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo"+cc.getPeriodo()));
	   	       		}
   	                pst.clearParameters();
   	                posicion=1;

   	                pst.setInt(posicion++,PERIODOABIERTO);
   	                if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	                if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	                if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	                if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	                pst.executeUpdate();

   	                rs.close();
   	       	        pst.close();
   	       	        if(cc.getPeriodo().equals("10")){
			       		pst=cn.prepareStatement(rb.getString("ActualizarCierreAllGrupos1"));
	 	       		}else if(cc.getPeriodo().equals("20")){
	 	       			pst=cn.prepareStatement(rb.getString("ActualizarCierreAllGrupos2"));
	 	       		}else{
	 	       			pst=cn.prepareStatement(rb.getString("ActualizarCierreAllGrupos"+cc.getPeriodo()));
	 	       		}
	 	       		pst.clearParameters();
   	                posicion=1;

   	                pst.setInt(posicion++,GRUPOABIERTO);
   	                if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	                if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	                if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	                if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	                else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	                pst.executeUpdate();

   	                band=true;
   	            }
   	        }
   	        cn.commit();
      						cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
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
        return band;
    }
    
    public boolean cerrarPeriodoAllGrupos(ConflictoCerrar cc, Login login){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        boolean band=false;
        try{
            cn=cursor.getConnection();
            cn.setAutoCommit(false);
            //cerrar todos los grupos
            if(cc.getPeriodo().equals("10")){
	       		pst=cn.prepareStatement(rb.getString("ActualizarCierreAllGrupos1"));
       		}else if(cc.getPeriodo().equals("20")){
       			pst=cn.prepareStatement(rb.getString("ActualizarCierreAllGrupos2"));
       		}else{
       			pst=cn.prepareStatement(rb.getString("ActualizarCierreAllGrupos"+cc.getPeriodo()));
       		}
            
            pst.clearParameters();
            posicion=1;

            pst.setInt(posicion++,GRUPOCERRADO);
            if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
            if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
            if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
            if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
            pst.executeUpdate();
   	        pst.close();

   	        //cerrar periodo
   	        if(cc.getPeriodo().equals("10")){
	       		pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo1"));
    		}else if(cc.getPeriodo().equals("20")){
    			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo2"));
    		}else{
    			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo"+cc.getPeriodo()));
    		}
            
            pst.clearParameters();
            posicion=1;

            pst.setInt(posicion++,PERIODOCERRADO);
            if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
            if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
            if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
            if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
            pst.executeUpdate();
            
            band=true;
            cn.commit();
      						cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
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
        return band;
    }
    
    public boolean cerrarGrupo(ConflictoCerrar cc, Login login){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        boolean band=false;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);
            if(cc.getPeriodo().equals("10")){
	       		pst=cn.prepareStatement(rb.getString("EstadoCierreGrupo1"));
    		}else if(cc.getPeriodo().equals("20")){
    			pst=cn.prepareStatement(rb.getString("EstadoCierreGrupo2"));
    		}else{
    			pst=cn.prepareStatement(rb.getString("EstadoCierreGrupo"+cc.getPeriodo()));
    		}
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	        if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	        if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	        if(cc.getMetodologia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getMetodologia().trim()));
   	        if(cc.getGrado().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrado().trim()));
   	        if(cc.getGrupo().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrupo().trim()));
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        rs=pst.executeQuery();
   	        if(rs.next()){
   	            rs.close();
   	            pst.close();
	   	        if(cc.getPeriodo().equals("10")){
	 	       		pst=cn.prepareStatement(rb.getString("ActualizarCierreGrupo1"));
	     		}else if(cc.getPeriodo().equals("20")){
	     			pst=cn.prepareStatement(rb.getString("ActualizarCierreGrupo2"));
	     		}else{
	     			pst=cn.prepareStatement(rb.getString("ActualizarCierreGrupo"+cc.getPeriodo()));
	     		}
   	            pst.clearParameters();
   	            posicion=1;
   	            
   	            pst.setInt(posicion++,GRUPOCERRADO);
   	            if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	   	        if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	   	        if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	   	        if(cc.getMetodologia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getMetodologia().trim()));
   	   	        if(cc.getGrado().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrado().trim()));
   	   	        if(cc.getGrupo().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrupo().trim()));
   	   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	   	        pst.executeUpdate();
   	   	        
   	   	        band=true;
   	        }else{
   	            rs.close();
   	            pst.close();
   	            if(cc.getPeriodo().equals("10")){
	 	       		pst=cn.prepareStatement(rb.getString("InsertarCierreGrupo1"));
	     		}else if(cc.getPeriodo().equals("20")){
	     			pst=cn.prepareStatement(rb.getString("InsertarCierreGrupo2"));
	     		}else{
	     			pst=cn.prepareStatement(rb.getString("InsertarCierreGrupo"+cc.getPeriodo()));
	     		}
   	            pst.clearParameters();
   	            posicion=1;
   	            
   	            if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	   	        if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	   	        if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	   	        if(cc.getMetodologia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getMetodologia().trim()));
   	   	        if(cc.getGrado().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrado().trim()));
   	   	        if(cc.getGrupo().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getGrupo().trim()));
   	   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	   	        pst.setInt(posicion++,GRUPOCERRADO);
   	   	        pst.executeUpdate();
   	   	        
   	   	        band=true;
   	        }
   	        cn.commit();
      						cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
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
        return band;
    }
    
    public boolean cerrarPeriodo(ConflictoCerrar cc, Login login){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        boolean band=false;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);
            if(cc.getPeriodo().equals("10")){
      			pst=cn.prepareStatement(rb.getString("EstadoCierrePeriodo1"));
      		}else if(cc.getPeriodo().equals("20")){
      			pst=cn.prepareStatement(rb.getString("EstadoCierrePeriodo2"));
      		}else{
      			pst=cn.prepareStatement(rb.getString("EstadoCierrePeriodo"+cc.getPeriodo()));
      		}
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	        if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	        if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        rs=pst.executeQuery();
   	        if(rs.next()){
   	            rs.close();
   	            pst.close();
   	            
	   	        if(cc.getPeriodo().equals("10")){
	       			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo1"));
	       		}else if(cc.getPeriodo().equals("20")){
	       			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo2"));
	       		}else{
	       			pst=cn.prepareStatement(rb.getString("ActualizarCierrePeriodo"+cc.getPeriodo()));
	       		}
   	            pst.clearParameters();
   	            posicion=1;
   	            
   	            pst.setInt(posicion++,PERIODOCERRADO);
   	            if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	   	        if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	   	        if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	   	        pst.executeUpdate();
   	   	        band=true;
   	        }else{
   	            setMensaje("El grupo no se encontro dentro del sistema. No es posible cerrar el periodo.");
   	            band=false;
   	        }
   	        cn.commit();
      						cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
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
        return band;
    }
    
    public boolean isDirectorGrupo(String jerar,String grupo,String docum){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        try{
            cn=cursor.getConnection();
   	        pst=cn.prepareStatement(rb.getString("isDirectorGrupo"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(docum.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(docum.trim()));
   	        if(jerar.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jerar.trim()));
   	        if(grupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(grupo.trim()));
   	        rs=pst.executeQuery();
   	        if(rs.next()){
   	            return true;
   	        }
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
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
        return false;
    }
    
    public String getJerar_1_7(String sede, String jornada, String grado, String grupo, Login l){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    ResultSet rs=null;
   	    int posicion=1;
        String jerar="";
   	    try{
   	        cn=cursor.getConnection();
   	        pst=cn.prepareStatement(rb.getString("Asignar.Jerar_1_7"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(l.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(l.getInstId().trim()));
   	        if(sede.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(sede.trim()));
   	        if(jornada.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jornada.trim()));
   	        if(grado.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(grado.trim()));
   	        rs = pst.executeQuery();
   	        if(rs.next()){
   	            jerar=rs.getString(1);
   	        }
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return "";}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
   	    setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return "";
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return jerar;
    }
    
    public Collection getPeriodos(String inst){
        Connection cn=null;
      		PreparedStatement pst=null;
      		ResultSet rs=null;
      		Collection list=null;
      		try{
      		    cn=cursor.getConnection();
      		    if(Integer.parseInt(getVigencia()) >=2013){
      		    	pst=cn.prepareStatement(rb.getString("lista"));
      		    }
      		    else{
      		    	pst=cn.prepareStatement(rb.getString("listaAnterior"));
      		    }
      		    	
      		    pst.clearParameters();
      		    int posicion=1;
      		    pst.setLong(posicion++,Long.parseLong(inst));
      		    pst.setLong(posicion++,Long.parseLong(getVigencia()));
      		    rs=pst.executeQuery();
      		    list=getCollection(rs);
      		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
      		catch(SQLException sqle){
      		    sqle.printStackTrace();
      		    try{cn.rollback();}catch(SQLException s){}
      		    setMensaje("Error intentando obtener periodos. Posible problema: ");
      		    switch(sqle.getErrorCode()){
      		    default:
      		        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
      		    }
      		    return list;
      			}catch(Exception e){
      			    e.printStackTrace();
      			}
      			finally{
      			    try{
      			        OperacionesGenerales.closeResultSet(rs);
      			        OperacionesGenerales.closeStatement(pst);
      			        OperacionesGenerales.closeConnection(cn);
      			        cursor.cerrar();
      			    }catch(InternalErrorException inte){}
      			}
      			return list;
    }
    
    public Collection getSedesJornadas(String inst){
      		Connection cn=null;
      		PreparedStatement pst=null;
      		ResultSet rs=null;
      		Collection list=null;
      		try{
      		    cn=cursor.getConnection();			
      		    pst=cn.prepareStatement(rb.getString("listaSedesJornadas"));
      		    pst.clearParameters();
      		    int posicion=1;
      		    pst.setLong(posicion++,Long.parseLong(inst));
      		    rs=pst.executeQuery();
      		    list=getCollection(rs);
      		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
      		catch(SQLException sqle){
      		    sqle.printStackTrace();
      		    try{cn.rollback();}catch(SQLException s){}
      		    setMensaje("Error intentando obtener periodos. Posible problema: ");
      		    switch(sqle.getErrorCode()){
      		    default:
      		        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
      		    }
      		    return list;
      		}finally{
      		    try{
      		        OperacionesGenerales.closeResultSet(rs);
      		        OperacionesGenerales.closeStatement(pst);
      		        OperacionesGenerales.closeConnection(cn);
      		        cursor.cerrar();
      		    }catch(InternalErrorException inte){}
      		}
      		return list;
    }
    
    public Collection getGruposAbiertos(ConflictoCerrar cc, Login login){
        Connection cn=null;
      		PreparedStatement pst=null;
      		ResultSet rs=null;
      		Collection list=new ArrayList();
      		try{
      		    cn=cursor.getConnection();
	      		if(cc.getPeriodo().equals("10")){
	      			pst=cn.prepareStatement(rb.getString("gruposAbiertos1"));
	      		}else if(cc.getPeriodo().equals("20")){
	      			pst=cn.prepareStatement(rb.getString("gruposAbiertos2"));
	      		}else{
	      			pst=cn.prepareStatement(rb.getString("gruposAbiertos"+cc.getPeriodo()));
	      		}
      		    pst.clearParameters();
      		    int posicion=1;
      		    
      		    pst.setInt(posicion++,GRUPOABIERTO);
            if(login.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(login.getInstId().trim()));
   	        if(cc.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getSede().trim()));
   	        if(cc.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(cc.getJornada().trim()));
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        rs=pst.executeQuery();
   	        list=getCollection(rs);
      		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
      		catch(SQLException sqle){
      		    sqle.printStackTrace();
      		    try{cn.rollback();}catch(SQLException s){}
      		    setMensaje("Error intentando obtener periodos. Posible problema: ");
      		    switch(sqle.getErrorCode()){
      		    default:
      		        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
      		    }
      		    return list;
      		}finally{
      		    try{
      		        OperacionesGenerales.closeResultSet(rs);
      		        OperacionesGenerales.closeStatement(pst);
      		        OperacionesGenerales.closeConnection(cn);
      		        cursor.cerrar();
      		    }catch(InternalErrorException inte){}
      			}
      		return list;
    }
    
}