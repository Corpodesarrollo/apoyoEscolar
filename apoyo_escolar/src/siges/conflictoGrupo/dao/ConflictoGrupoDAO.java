package siges.conflictoGrupo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.conflicto.beans.ConflictoEscolar;
import siges.conflictoGrupo.beans.*;

public class ConflictoGrupoDAO extends Dao {

    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;
    public static final int GRUPOCERRADO=1;
    public static final int GRUPOABIERTO=0;
    public static final int PERIODOCERRADO=1;
    public static final int PERIODOABIERTO=0;

    public ConflictoGrupoDAO(Cursor cur) {
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("conflictoGrupo");
    }
    
    public boolean isGrupoCerrado(ConflictoFiltro cf, Login l){
        Connection cn=null;
        PreparedStatement pst=null;
        int posicion=1,estado=-1;
        ResultSet rs=null;
        try{
        	cn=cursor.getConnection();
        	pst=cn.prepareStatement(rb.getString("Obtener.Grupo"+cf.getPeriodo()));
        	pst.clearParameters();
        	posicion=1;
      						
        	if(l.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(l.getInstId().trim()));
        	if(cf.getSede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(cf.getSede().trim()));
        	if(cf.getJornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(cf.getJornada().trim()));
        	if(cf.getGrado().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(cf.getGrado().trim()));
        	if(cf.getGrupo().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(cf.getGrupo().trim()));
        	if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
        	rs=pst.executeQuery();
        	if(rs.next())	estado=rs.getInt(1);
        	if(estado!=-1){
        		if(estado==GRUPOCERRADO){
        			return true;
        		}
        	}
        }catch(InternalErrorException in){
        	in.printStackTrace();
        	setMensaje("NO se puede estabecer conexinn con la base de datos: "+in.getMessage());
        	return false;
        }catch(SQLException sqle){
        	sqle.printStackTrace();
        	setMensaje("Error SQL intentando acceder a informacion de Grupo [cerrado]. Posible problema: "+sqle.getMessage());
        	try{cn.rollback();}catch(SQLException se){}
        	setMensaje("Error SQL intentando acceder información de Grupo [cerrado]. Posible problema: "+sqle.getMessage());
        switch(sqle.getErrorCode()){
        default:
        	setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
        }
        return false;
        }catch(Exception ep){
        	ep.printStackTrace();
        	setMensaje("Error SQL intentando acceder a informacion de Grupo [cerrado]. Posible problema: "+ep.getMessage());
        }finally{
        	try{
        		OperacionesGenerales.closeResultSet(rs);
        		OperacionesGenerales.closeStatement(pst);
        		OperacionesGenerales.closeConnection(cn);
        	}catch(InternalErrorException inte){}
        }
        return false;
    }
    
    public ConflictoFiltro obtenerDirectorGrupo(String doc){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        ConflictoFiltro cf=null;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Buscar.CordGrupo"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(doc.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(doc));
   	        rs=pst.executeQuery();
   	        if(rs.next()){
   	        	int i=1;
   	        	cf=new ConflictoFiltro();
   	        	cf.setJergrupo(rs.getString(i++));
   	        	cf.setDocente(rs.getString(i++));
   	        	cf.setSede(rs.getString(i++));
   	        	cf.setJornada(rs.getString(i++));
   	        	cf.setGrado(rs.getString(i++));
   	        	cf.setGrupo(rs.getString(i++));
   	        }
   	        if(rs.next()){
   	        	return null;
   	        }
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){
   	    	in.printStackTrace();
   	        setMensaje("NO se puede estabecer conexinn con la base de datos: "+in.getMessage());
   	        return cf;
   	    }catch(SQLException sqle){
   	    	sqle.printStackTrace();
   	    	setMensaje("Error SQL intentando obtener información de director de grupo. Posible problema: "+sqle.getMessage());
   	    	try{cn.rollback();}catch(SQLException se){}
   	    	switch(sqle.getErrorCode()){
   	    	default:
   	    		setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    	}
   	    	return cf;
   	    }catch(Exception ep){
   	    	ep.printStackTrace();
   	    	setMensaje("Error SQL intentando obtener información de director de grupo. Posible problema: "+ep.getMessage());
   	    }finally{
   	        try{
   	        	OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return cf;
    }
    
    public ConflictoFiltro obtenerDirectorGrupo(String jerar,String grupo,String docum,String periodo){
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        ConflictoFiltro cf=null;
        try{
            cn=cursor.getConnection();
   	        pst=cn.prepareStatement(rb.getString("Asignar.CordGrupo"));
   	        pst.clearParameters();
   	        posicion=1;
   	        //System.out.println("docum: "+docum);
   	        //System.out.println("grupo: "+grupo);
   	        
   	        if(docum.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(docum.trim()));
   	        if(jerar.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jerar.trim()));
   	        if(grupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(grupo.trim()));
   	        rs=pst.executeQuery();
   	        if(rs.next()){
   	            int i=1;
   	            cf=new ConflictoFiltro();
   	            cf.setJergrupo(rs.getString(i++));
   	            cf.setDocente(rs.getString(i++));
   	            cf.setSede(rs.getString(i++));
   	            cf.setJornada(rs.getString(i++));
   	            cf.setGrado(rs.getString(i++));
   	            cf.setGrupo(rs.getString(i++));
   	            cf.setPeriodo(periodo);
   	        }else{
   	        	rs.close();
   	        	pst.close();
   	   	        pst=cn.prepareStatement(rb.getString("Asignar.CordGrupoAlternativo"));
   	   	        pst.clearParameters();
   	   	        posicion=1;
   	   	        if(jerar.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	   	        else pst.setLong(posicion++,Long.parseLong(jerar.trim()));
   	   	        rs=pst.executeQuery();
   	   	        if(rs.next()){
   	   	        	//System.out.println("***entro por alternativo***");
   	   	            int i=1;
   	   	            cf=new ConflictoFiltro();
   	   	            cf.setJergrupo(rs.getString(i++));
   	   	            cf.setDocente(docum);
   	   	            cf.setSede(rs.getString(i++));
   	   	            cf.setJornada(rs.getString(i++));
   	   	            cf.setGrado(rs.getString(i++));
   	   	            cf.setGrupo(grupo);
   	   	            cf.setPeriodo(periodo);
   	   	        }
   	        }
   	        
        }catch(InternalErrorException in){
        	in.printStackTrace();
        	setMensaje("NO se puede estabecer conexinn con la base de datos: "+in.getMessage());
        	return cf;
        }catch(SQLException sqle){
        	sqle.printStackTrace();
        	setMensaje("Error SQL intentando obtener información de director de grupo 2. Posible problema: "+sqle.getMessage());
        	try{cn.rollback();}catch(SQLException se){se.printStackTrace();}
        	switch(sqle.getErrorCode()){
        	default:
        		setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return cf;
   	    }catch(Exception ep){
   	    	ep.printStackTrace();
        	setMensaje("Error SQL intentando obtener información de director de grupo 2. Posible problema: "+ep.getMessage());
   	    }
        finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
        return cf;
    }
    
    public boolean insertarConflictoGrupo(String numalum, String tipo, String jergrupo, String docente, String periodo, String grupo,String numeve,String gene){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Insertar.ConflictoGrupo"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(numalum.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(numalum));
   	        if(tipo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(tipo));
   	        if(jergrupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jergrupo));
   	        if(docente.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(docente));
   	        if(periodo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(periodo));
   	        if(grupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(grupo));
   	        if(numeve.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(numeve));
   	        if(gene.equals("-9")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
   	        else pst.setLong(posicion++,Long.parseLong(gene));
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        pst.executeUpdate();
      	  			
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){
   	    	in.printStackTrace();
   	    	setMensaje("NO se puede estabecer conexinn con la base de datos: "+in.getMessage());
   	    	return false;
   	    }catch(SQLException sqle){
   	    	sqle.printStackTrace();
   	    	setMensaje("Error SQL intentando ingresar información de Conflicto Escolar por Grupo. Posible problema: "+sqle);
   	    	try{cn.rollback();}catch(SQLException se){}
   	    	switch(sqle.getErrorCode()){
   	    	default:
   	    	    setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    	}
   	    	return false;
   	    }catch(Exception ep){
   	    	ep.printStackTrace();
   	    	setMensaje("Error SQL intentando ingresar información de Conflicto Escolar por Grupo. Posible problema: "+ep);
   	    }finally{
   			try{
   			    OperacionesGenerales.closeStatement(pst);
   			    OperacionesGenerales.closeConnection(cn);
   			}catch(InternalErrorException inte){}
   	    }
   	    return true;
   	}
   
    /*modifico este prodemiento william -15-01-09*/
    public ConflictoGrupo asignarConflictoGrupo(String jergrupo, String grupo, String periodo){
        ConflictoGrupo cg=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1;
        String aux="-99";
        try{
            cn=cursor.getConnection();
            pst=cn.prepareStatement(rb.getString("Asignar.ConflictoGrupo"));
            pst.clearParameters();
            posicion=1;
            
            if(jergrupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(jergrupo.trim()));
            if(grupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(grupo.trim()));
            if(periodo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setInt(posicion++,Integer.parseInt(periodo.trim()));
            if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
            rs = pst.executeQuery();
            cg=new ConflictoGrupo();
            Collection list = new ArrayList();
            Collection list2 = new ArrayList();
            Collection list3 = new ArrayList();
            Object[] o;
            Object[] o2;
            Object[] o3;
            while (rs.next()){
            	o=new Object[2];
            	o[0]="cg"+rs.getString(1);
            	o[1]=rs.getString(2);
            	list.add(o);
            	aux=rs.getString(3);

            	o2=new Object[2];
            	o2[0]="cge"+rs.getString(1);
            	o2[1]=rs.getString(4);
            	list2.add(o2);

            	o3=new Object[2];
            	o3[0]="cgee"+rs.getString(1);
            	o3[1]=rs.getString(5);
            	list3.add(o3);
            	
            }
            cg.setNumalumnos(getFiltroMatriz(list));
            cg.setNumeventos(getFiltroMatriz(list2));
            cg.setGenero(getFiltroMatriz(list3));
            
            //System.out.println("valor de aux: "+aux);
            if(aux!=null){
                if(aux.equals("-99")){
                	rs.close();
                	pst.close();
                	pst=cn.prepareStatement(rb.getString("Asignar.Cupo"));
                	pst.clearParameters();
                	posicion=1;
                	if(jergrupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
                	else pst.setLong(posicion++,Long.parseLong(jergrupo.trim()));
                	if(grupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
                	else pst.setLong(posicion++,Long.parseLong(grupo.trim()));
                	rs = pst.executeQuery();
                	if(rs.next()){
                		aux=rs.getString(1);
                	}
                }
                cg.setCupo(aux);
            }
            
        }catch(InternalErrorException in){
        	in.printStackTrace(); 
        	setMensaje("No se puede estabecer conexinn con la base de datos: "+in.getMessage());	return null;}
   	    catch(SQLException sqle){
   	    	sqle.printStackTrace();
   	    	setMensaje("Error intentando asignar información de Conflicto Escolar por Grupo. Posible problema: "+sqle.getMessage());
   	    	switch(sqle.getErrorCode()){
   	    	default:
   	    		setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    	}
   	    	return cg;
   	    }catch(Exception ep){
   	    	ep.printStackTrace();
   	    	setMensaje("Error intentando asignar información de Conflicto Escolar por Grupo. Posible problema: "+ep.getMessage());
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return cg;
    }
    
    public boolean eliminarConflictoGrupo(String jergrupo, String grupo, String docente, String periodo){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
        ResultSet rs=null;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Eliminar.ConflictoGrupo"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(jergrupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jergrupo.trim()));
   	        if(grupo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(grupo.trim()));
   	        if(docente.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(docente.trim()));
   	        if(periodo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setInt(posicion++,Integer.parseInt(periodo.trim()));
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        pst.executeUpdate();
   	        
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){
   	    	in.printStackTrace();
   	    	setMensaje("NO se puede estabecer conexinn con la base de datos: "+in.getMensaje());
   	    	return false;
   	    }catch(SQLException sqle){
   	    	sqle.printStackTrace();
   	    	setMensaje("Error SQL intentando eliminar información de Conflicto Escolar por Grupo. Posible problema: "+sqle.getMessage());
   	    	try{cn.rollback();}catch(SQLException se){}
   	    	switch(sqle.getErrorCode()){
   	    	default:
   	    	    setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    	}
   	    	return false;
   	    }catch(Exception ep){
   	    	ep.printStackTrace();
   	    	setMensaje("Error SQL intentando eliminar información de Conflicto Escolar por Grupo. Posible problema: "+ep.getMessage());
   	    }finally{
   			try{
   			    OperacionesGenerales.closeStatement(pst);
   			    OperacionesGenerales.closeConnection(cn);
   			}catch(InternalErrorException inte){}
   	    }
   	    return true;
   	}
    
    public String obtenerJerar_1_7(String sede, String jornada, String met,String grado, Login l){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    ResultSet rs=null;
   	    int posicion=1;
        String jerar="";
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Asignar.Jerar_1_7"));
   	        pst.clearParameters();
   	        posicion=1;
   	        pst.setLong(posicion++,Long.parseLong(l.getInstId().trim()));
   	        pst.setLong(posicion++,Long.parseLong(sede.trim()));
   	        pst.setLong(posicion++,Long.parseLong(jornada.trim()));
   	        pst.setLong(posicion++,Long.parseLong(met.trim()));
   	        pst.setLong(posicion++,Long.parseLong(grado.trim()));
   	        rs = pst.executeQuery();
   	        if(rs.next()){
   	            jerar=rs.getString(1);
   	        }
   	    }catch(InternalErrorException in){
   	    	in.printStackTrace();
   	    	setMensaje("NO se puede estabecer conexinn con la base de datos: "+in.getMessage());
   	    	return "";
   	    }catch(SQLException sqle){
   	    	sqle.printStackTrace();
   	    	setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: "+sqle.getMessage());
   	    	try{cn.rollback();}catch(SQLException se){}
   	    	switch(sqle.getErrorCode()){
   	    	default:
   	    		setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    	}
   	    	return "";
   	    }catch(Exception ep){
   	    	ep.printStackTrace();
   	    	setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: "+ep.getMessage());
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return jerar;
    }
    
}