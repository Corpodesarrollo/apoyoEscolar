package siges.institucion.organizacion.dao;

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
import siges.institucion.organizacion.beans.*;
import siges.login.beans.Login;

public class OrganizacionDAO extends Dao {

    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;

    public OrganizacionDAO(Cursor cur) {
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("siges.institucion.organizacion.organizacion");
    }

    public boolean eliminarAsociacion(String id, String id2) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("AsociacionEliminar"));
            pst.clearParameters();
            pst.setLong(posicion++, Long.parseLong(id.trim()));
            pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;
        } catch (SQLException sqle) {
            try {cn.rollback();} catch (SQLException s) {}
            setMensaje("Error intentando eliminar Asociacion de padres. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {}
        }
        return true;
    }
    
    public boolean eliminarAsociacionIntegrantesVO(String id, String id2,AsociacionVO asociacionVO) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        try{
			long tipoId=Long.parseLong(id);
			String numId=(id2);
			long inst=Long.parseLong(asociacionVO.getAsoInst());	
			long cod=Long.parseLong(asociacionVO.getAsoCodigo());
			long vigencia=getVigenciaNumerico();
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("AsociacionIntegrantesEliminar"));
            pst.clearParameters();
            pst.setLong(posicion++, tipoId);
            pst.setString(posicion++, numId);
            pst.setLong(posicion++, cod);
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        }catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;
        }catch(SQLException sqle){
            try {cn.rollback();} catch (SQLException s) {}
            setMensaje("Error intentando eliminar Asociacion Integrantes. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {}
        }
        return true;
    }
    
    public boolean compararBeans(AsociacionVO a, AsociacionVO a2) {
    	if (!a.getAsoCodigo().equals(a2.getAsoCodigo()))return false; 
    	if (!a.getAsoInst().equals(a2.getAsoInst()))return false; 
    	if (!a.getAsoNombre().equals(a2.getAsoNombre()))return false; 
    	if (!a.getAsoFechaConstitucion().equals(a2.getAsoFechaConstitucion()))return false; 
    	if (!a.getAsoPersoneria().equals(a2.getAsoPersoneria()))return false; 
    	if (!a.getAsoVigencia().equals(a2.getAsoVigencia()))return false; 
    	if (!a.getAsoFechaInicio().equals(a2.getAsoFechaInicio()))return false; 
    	if (!a.getAsoFechaFin().equals(a2.getAsoFechaFin()))return false;
        return true;
    }
    
    public boolean compararBeans(AsociacionIntegrantesVO a, AsociacionIntegrantesVO a2) {
    	if (!a.getAsoIntCodigo().equals(a2.getAsoIntCodigo()))return false;
    	if (!a.getAsoIntInst().equals(a2.getAsoIntInst()))return false;
    	if (!a.getAsoIntTipodoc().equals(a2.getAsoIntTipodoc()))return false;
    	if (!a.getAsoIntNumdoc().equals(a2.getAsoIntNumdoc()))return false;
    	if (!a.getAsoIntCargo().equals(a2.getAsoIntCargo()))return false;
    	if (!a.getAsoIntNombre().equals(a2.getAsoIntNombre()))return false;
    	if (!a.getAsoIntApellido().equals(a2.getAsoIntApellido()))return false;
    	if (!a.getAsoIntTelefono().equals(a2.getAsoIntTelefono()))return false;
    	if (!a.getAsoIntCorreo().equals(a2.getAsoIntCorreo()))return false; 
    	if (!a.getAsoIntGenero().equals(a2.getAsoIntGenero()))return false;
        return true;
    }
    
    public boolean compararBeans(ConsejoDirectivoVO c, ConsejoDirectivoVO c2) {
    	if (!c.getConDirInst().equals(c2.getConDirInst()))return false;
    	if (!c.getConDirSede().equals(c2.getConDirSede()))return false;
    	if (!c.getConDirJornada().equals(c2.getConDirJornada()))return false; 
    	if (!c.getConDirCargo().equals(c2.getConDirCargo()))return false;
    	if (!c.getConDirNombre().equals(c2.getConDirNombre()))return false;
    	if (!c.getConDirApellido().equals(c2.getConDirApellido()))return false;
    	if (!c.getConDirTipoDoc().equals(c2.getConDirTipoDoc()))return false;
    	if (!c.getConDirNumDoc().equals(c2.getConDirNumDoc()))return false;
    	if (!c.getConDirTelefono().equals(c2.getConDirTelefono()))return false;
    	if (!c.getConDirCorreo().equals(c2.getConDirCorreo()))return false;
    	if (!c.getConDirGenero().equals(c2.getConDirGenero()))return false;
        return true;
    }
    
    public boolean compararBeans(ConsejoAcademicoVO c, ConsejoAcademicoVO c2) {
    	if (!c.getConAcaInst().equals(c2.getConAcaInst()))return false;
    	if (!c.getConAcaSede().equals(c2.getConAcaSede()))return false;
    	if (!c.getConAcaJornada().equals(c2.getConAcaJornada()))return false;
    	if (!c.getConAcaCargo().equals(c2.getConAcaCargo()))return false;
    	if (!c.getConAcaTipoDoc().equals(c2.getConAcaTipoDoc()))return false;
    	if (!c.getConAcaNumDoc().equals(c2.getConAcaNumDoc()))return false;
    	if (!c.getConAcaTelefono().equals(c2.getConAcaTelefono()))return false;
    	if (!c.getConAcaCorreo().equals(c2.getConAcaCorreo()))return false;
        return true;
    }
    
    public boolean compararBeans(LiderVO l, LiderVO l2) {
    	if (!l.getLidCodJerar().equals(l2.getLidCodJerar()))return false;
    	if (!l.getLidCodEstud().equals(l2.getLidCodEstud()))return false;
    	if (!l.getLidCargo().equals(l2.getLidCargo()))return false;
    	if (!l.getLidTelefono().equals(l2.getLidTelefono()))return false;
    	if (!l.getLidCorreo().equals(l2.getLidCorreo()))return false;
    	if (!l.getLidFechaInicio().equals(l2.getLidFechaInicio()))return false;
    	if (!l.getLidEstado().equals(l2.getLidEstado()))return false;
    	if (!l.getLidInst().equals(l2.getLidInst()))return false;
    	if (!l.getLidSede().equals(l2.getLidSede()))return false;
    	if (!l.getLidJor().equals(l2.getLidJor()))return false;
    	if (!l.getLidMet().equals(l2.getLidMet()))return false;
    	if (!l.getLidGrado().equals(l2.getLidGrado()))return false;
    	if (!l.getLidGrupo().equals(l2.getLidGrupo()))return false;
        return true;
    }
    

    public boolean actualizar(AsociacionVO a){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
        	long inst=Long.parseLong(a.getAsoInst());
        	long id=Long.parseLong(a.getAsoCodigo());
        	long vigencia=getVigenciaNumerico();
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("AsociacionActualizar"));
            pst.clearParameters();
            if (a.getAsoNombre().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoNombre().trim()); 
			if(a.getAsoFechaConstitucion().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(a.getAsoFechaConstitucion()).getTime()));
            if (a.getAsoPersoneria().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoPersoneria().trim()); 
            if (a.getAsoVigencia().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(a.getAsoVigencia()));
			if(a.getAsoFechaInicio().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(a.getAsoFechaInicio()).getTime()));
			if(a.getAsoFechaFin().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(a.getAsoFechaFin()).getTime()));
            //where
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++,id);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;
        } catch (SQLException sqle) {
        	sqle.printStackTrace();
            try{cn.rollback();} catch (SQLException s){}
            setMensaje("Error intentando actualizar asociacinn. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch (Exception sqle) {
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();} catch (SQLException s){}
            setMensaje("Error intentando actualizar asociacinn. Posible problema: "+sqle);
            return false;
        }finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }
    
    public boolean actualizar(AsociacionIntegrantesVO a,AsociacionIntegrantesVO a2){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try{
        	long inst=Long.parseLong(a.getAsoIntInst());
        	long id=Long.parseLong(a.getAsoIntCodigo());
        	long tipoOld=Long.parseLong(a2.getAsoIntTipodoc());
        	long tipoNew=Long.parseLong(a.getAsoIntTipodoc());
        	long vigencia=getVigenciaNumerico();
        	String numOld=a2.getAsoIntNumdoc();
        	String numNew=a.getAsoIntNumdoc();
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            if(tipoOld!=tipoNew || !numOld.equals(numNew)){
	            pst = cn.prepareStatement(rb.getString("AsociacionIntegrantesExists"));
	            pst.clearParameters();
	            posicion = 1;
	            pst.setLong(posicion++, inst);
	            pst.setLong(posicion++,id);
	            pst.setLong(posicion++,tipoNew);
	            pst.setString(posicion++,numNew);
	            pst.setLong(posicion++,vigencia);
	            rs=pst.executeQuery();
	            if(rs.next()){
	            	setMensaje("La identificacinn ya existe para esta misma asociacinn");
	            	return false;
	            }
	            rs.close();pst.close();
            }    
            posicion = 1;
            pst = cn.prepareStatement(rb.getString("AsociacionIntegrantesActualizar"));
            pst.clearParameters();
            pst.setLong(posicion++, tipoNew);
            pst.setString(posicion++, numNew);
            if (a.getAsoIntCargo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(a.getAsoIntCargo()));
            if (a.getAsoIntNombre().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntNombre().trim());
            if (a.getAsoIntApellido().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntApellido().trim());
            if (a.getAsoIntTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntTelefono().trim());
            if (a.getAsoIntCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntCorreo().trim());
            if (a.getAsoIntGenero().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntGenero().trim());
            //Where
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++,id);
            pst.setLong(posicion++,tipoOld);
            pst.setString(posicion++,numOld);
            pst.setLong(posicion++, vigencia);
            int t=pst.executeUpdate();
            System.out.println("Actualizao"+t);
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;
        } catch (SQLException sqle) {
        	sqle.printStackTrace();
            try{cn.rollback();} catch (SQLException s){}
            setMensaje("Error intentando actualizar Integrante. Posible problema: ");
            switch (sqle.getErrorCode()) {
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch (Exception sqle) {
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();} catch (SQLException s){}
            setMensaje("Error intentando actualizar Integrante. Posible problema: "+sqle);
            return false;
        }finally {
            try {
            	OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }
    
    public synchronized boolean insertar(AsociacionVO a){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        long id=-1;
        try {
        	long vigencia=getVigenciaNumerico();
        	long inst=Long.parseLong(a.getAsoInst());
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("AsociacionGetId"));
            pst.clearParameters();            
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, vigencia);
            rs=pst.executeQuery();
            if(rs.next()){
                id=rs.getLong(1);
            }
            rs.close();pst.close();
            a.setAsoCodigo(""+id);
            pst = cn.prepareStatement(rb.getString("AsociacionInsertar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++,id);
            pst.setLong(posicion++, inst);
            if (a.getAsoNombre().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoNombre().trim()); 
			if(a.getAsoFechaConstitucion().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(a.getAsoFechaConstitucion()).getTime()));
            if (a.getAsoPersoneria().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoPersoneria().trim()); 
            if (a.getAsoVigencia().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(a.getAsoVigencia()));
			if(a.getAsoFechaInicio().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(a.getAsoFechaInicio()).getTime()));
			if(a.getAsoFechaFin().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(a.getAsoFechaFin()).getTime()));
			pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Asociacinn. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Asociacinn. Posible problema: "+sqle);
            return false;
        }        finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean insertar(AsociacionIntegrantesVO a){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try {
        	long vigencia=getVigenciaNumerico();
        	long inst=Long.parseLong(a.getAsoIntInst());
        	long cod=Long.parseLong(a.getAsoIntCodigo());
        	long tipoId=Long.parseLong(a.getAsoIntTipodoc());
        	String numId=(a.getAsoIntNumdoc());
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("AsociacionIntegrantesExists"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++,cod);
            pst.setLong(posicion++,tipoId);
            pst.setString(posicion++,numId);
            pst.setLong(posicion++, vigencia);
            rs=pst.executeQuery();
            if(rs.next()){
            	setMensaje("La identificacinn ya existe para esta misma asociacinn, no se pudo guardar");
            	return false;
            }
            rs.close();pst.close();
            pst = cn.prepareStatement(rb.getString("AsociacionIntegrantesInsertar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++,cod);
            pst.setLong(posicion++, inst);
            if (a.getAsoIntTipodoc().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(a.getAsoIntTipodoc()));
            if (a.getAsoIntNumdoc().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, (a.getAsoIntNumdoc()));
            if (a.getAsoIntCargo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(a.getAsoIntCargo()));
            if (a.getAsoIntNombre().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntNombre().trim());
            if (a.getAsoIntApellido().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntApellido().trim());
            if (a.getAsoIntTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntTelefono().trim());
            if (a.getAsoIntCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntCorreo().trim());
            if (a.getAsoIntGenero().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, a.getAsoIntGenero().trim());
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar integrante de asociacinn. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar integrante de asociacinn. Posible problema: "+sqle);
            return false;
        }finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean insertar(ConsejoDirectivoVO c){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try {
        	long vigencia=getVigenciaNumerico();
        	long inst=Long.parseLong(c.getConDirInst());
        	long tipoDoc=Long.parseLong(c.getConDirTipoDoc());
        	String numDoc=c.getConDirNumDoc();
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("ConsejoDirectivoExists"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, tipoDoc);
            pst.setString(posicion++, numDoc);
            pst.setLong(posicion++, vigencia);
            rs=pst.executeQuery();
            if(rs.next()){
            	setMensaje("Ya existe un integrante con esa identificacinn");
            	return false;
            }
            rs.close();pst.close();
            pst = cn.prepareStatement(rb.getString("ConsejoDirectivoInsertar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, inst);
            if (c.getConDirSede().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirSede()));
            if (c.getConDirJornada().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirJornada()));
            if (c.getConDirCargo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirCargo()));
            if (c.getConDirNombre().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirNombre());
            if (c.getConDirApellido().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirApellido());
            pst.setLong(posicion++, tipoDoc);
            pst.setString(posicion++,numDoc);
            if (c.getConDirTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirTelefono());
            if (c.getConDirCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirCorreo());
            if (c.getConDirGenero().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirGenero()));
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Consejo Directivo. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Consejo Directivo. Posible problema: "+sqle);
            return false;
        } finally{
            try{
            	OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean actualizar(ConsejoDirectivoVO c,ConsejoDirectivoVO c2){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try {
        	long inst=Long.parseLong(c.getConDirInst());
        	long tipoOld=Long.parseLong(c2.getConDirTipoDoc());
        	long tipoNew=Long.parseLong(c.getConDirTipoDoc());
        	long vigencia=getVigenciaNumerico();
        	String numOld=c2.getConDirNumDoc();
        	String numNew=c.getConDirNumDoc();
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            if(tipoOld!=tipoNew || !numOld.equals(numNew)){
                pst = cn.prepareStatement(rb.getString("ConsejoDirectivoExists"));
                pst.clearParameters();
                posicion = 1;
                pst.setLong(posicion++, inst);
                pst.setLong(posicion++, tipoNew);
                pst.setString(posicion++, numNew);
                pst.setLong(posicion++, vigencia);
                rs=pst.executeQuery();
                if(rs.next()){
                	setMensaje("Ya existe un integrante con esa identificacinn");
                	return false;
                }
                rs.close();pst.close();
            }
            pst = cn.prepareStatement(rb.getString("ConsejoDirectivoActualizar"));
            pst.clearParameters();
            posicion = 1;
            if (c.getConDirSede().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirSede()));
            if (c.getConDirJornada().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirJornada()));
            if (c.getConDirCargo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirCargo()));
            if (c.getConDirNombre().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirNombre());
            if (c.getConDirApellido().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirApellido());
            pst.setLong(posicion++, tipoNew);
            pst.setString(posicion++,numNew);
            if (c.getConDirTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirTelefono());
            if (c.getConDirCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConDirCorreo());
            if (c.getConDirGenero().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConDirGenero()));
            //where
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, tipoOld);
            pst.setString(posicion++,numOld);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando actualizar Consejo Directivo. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Consejo Directivo. Posible problema: "+sqle);
            return false;
        } finally{
            try{
            	OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean eliminarConsejoDirectivo(Login l,String id,String id2){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
        	long inst=Long.parseLong(l.getInstId());
        	long tipoOld=Long.parseLong(id);
        	long vigencia=getVigenciaNumerico();
        	String numOld=id2;
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("ConsejoDirectivoEliminar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, tipoOld);
            pst.setString(posicion++,numOld);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando eliminar Consejo Directivo. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando eliminar Consejo Directivo. Posible problema: "+sqle);
            return false;
        } finally{
            try{
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean insertar(ConsejoAcademicoVO c){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try {
        	long vigencia=getVigenciaNumerico();
        	long inst=Long.parseLong(c.getConAcaInst());
        	long sede=Long.parseLong(c.getConAcaSede());
        	long jor=Long.parseLong(c.getConAcaJornada());
        	String numDoc=c.getConAcaNumDoc();
        	long tipoDoc=-1;
            cn = cursor.getConnection();
            cn.setAutoCommit(false);            
            pst = cn.prepareStatement(rb.getString("ConsejoAcademicoGetTipo"));
            pst.clearParameters();
            posicion = 1;
            pst.setString(posicion++,numDoc);
            rs=pst.executeQuery();
            if(rs.next()){
            	c.setConAcaTipoDoc(rs.getString(1));
            	tipoDoc=rs.getLong(1);
            }else{
            	setMensaje("No se encontro el tipo de documento");
            	return false;
            }
            rs.close();pst.close();
            pst = cn.prepareStatement(rb.getString("ConsejoAcademicoExists"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sede);
            pst.setLong(posicion++, jor);
            pst.setLong(posicion++, tipoDoc);
            pst.setString(posicion++, numDoc);
            pst.setLong(posicion++, vigencia);
            rs=pst.executeQuery();
            if(rs.next()){
            	setMensaje("Ya existe un integrante con esa identificacinn");
            	return false;
            }
            rs.close();pst.close();            
            pst = cn.prepareStatement(rb.getString("ConsejoAcademicoInsertar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++,inst);
            pst.setLong(posicion++,sede);
            pst.setLong(posicion++,jor);
            if (c.getConAcaCargo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConAcaCargo()));
            pst.setLong(posicion++, tipoDoc);
            if (c.getConAcaNumDoc().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConAcaNumDoc());
            if (c.getConAcaTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConAcaTelefono());
            if (c.getConAcaCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConAcaCorreo());
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Consejo Academico. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Consejo Academico. Posible problema: "+sqle);
            return false;
        } finally{
            try{
            	OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean actualizar(ConsejoAcademicoVO c,ConsejoAcademicoVO c2){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try {
        	long inst=Long.parseLong(c.getConAcaInst());
        	long sedeOld=Long.parseLong(c2.getConAcaSede());
        	long jorOld=Long.parseLong(c2.getConAcaJornada());
        	long tipoOld=Long.parseLong(c2.getConAcaTipoDoc());
        	String numOld=c2.getConAcaNumDoc();
        	long sedeNew=Long.parseLong(c.getConAcaSede());
        	long jorNew=Long.parseLong(c.getConAcaJornada());
        	long tipoNew=Long.parseLong(c.getConAcaTipoDoc());
        	String numNew=c.getConAcaNumDoc();
        	long vigencia=getVigenciaNumerico();
            cn = cursor.getConnection();            
            cn.setAutoCommit(false);
            if(tipoOld!=tipoNew || !numOld.equals(numNew)){
                pst = cn.prepareStatement(rb.getString("ConsejoAcademicoExists"));
                pst.clearParameters();
                posicion = 1;
                pst.setLong(posicion++, inst);
                pst.setLong(posicion++, sedeNew);
                pst.setLong(posicion++, jorNew);
                pst.setLong(posicion++, tipoNew);
                pst.setString(posicion++, numNew);
                pst.setLong(posicion++, vigencia);
                rs=pst.executeQuery();
                if(rs.next()){
                	setMensaje("Ya existe un integrante con esa identificacinn");
                	return false;
                }
                rs.close();pst.close();            
            }
            pst = cn.prepareStatement(rb.getString("ConsejoAcademicoActualizar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, sedeNew);
            pst.setLong(posicion++, jorNew);
            if (c.getConAcaCargo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(c.getConAcaCargo()));
            pst.setLong(posicion++, tipoNew);
            pst.setString(posicion++,numNew);
            if (c.getConAcaTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConAcaTelefono());
            if (c.getConAcaCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,c.getConAcaCorreo());
            //where
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sedeOld);
            pst.setLong(posicion++, jorOld);
            pst.setLong(posicion++, tipoOld);
            pst.setString(posicion++, numOld);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando actualizar Consejo Academico. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando actualizar Consejo Academico. Posible problema: "+sqle);
            return false;
        } finally{
            try{
            	OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean eliminarConsejoAcademico(Login l,String id,String id2,String id3,String id4){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
        	long inst=Long.parseLong(l.getInstId());
        	long sedeOld=Long.parseLong(id);
        	long jorOld=Long.parseLong(id2);
        	long tipoOld=Long.parseLong(id3);
        	long vigencia=getVigenciaNumerico();
        	String numOld=id4;
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("ConsejoAcademicoEliminar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sedeOld);
            pst.setLong(posicion++, jorOld);
            pst.setLong(posicion++, tipoOld);
            pst.setString(posicion++, numOld);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando eliminar Consejo Academico. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando eliminar Consejo Academico. Posible problema: "+sqle);
            return false;
        } finally{
            try{
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean insertar(LiderVO l){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try {
        	long vigencia=getVigenciaNumerico();
        	long inst=Long.parseLong(l.getLidInst());
        	long sede=Long.parseLong(l.getLidSede());
        	long jor=Long.parseLong(l.getLidJor());
        	long met=Long.parseLong(l.getLidMet());
        	long gra=Long.parseLong(l.getLidGrado());
        	long gru=Long.parseLong(l.getLidGrupo());
        	long estudiante=Long.parseLong(l.getLidCodEstud());
        	long cargo=Long.parseLong(l.getLidCargo());
        	long sedeJornada=Long.parseLong(l.getLidSedeJornada());
        	long nivel=7;
        	long jer=-1;
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("getJerarquia"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, nivel);
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sede);
            pst.setLong(posicion++, jor);
            pst.setLong(posicion++, met);
            pst.setLong(posicion++, gra);
            rs=pst.executeQuery();
            if(rs.next()){
            	jer=rs.getLong(1);
            }
            rs.close();pst.close();
            if(jer==-1){
            	setMensaje("No se encontro la jerarquia de la ubicacinn");
            	return false;
            }
            //validar lo de si es personero que se ingrese por sede y jornada
            if(cargo==Params.PARAM_PERSONERO || cargo==Params.PARAM_CONTRALOR || cargo==Params.PARAM_PRESIDENTE){
            	if(sedeJornada==Params.PARAM_SEDE){
                    pst = cn.prepareStatement(rb.getString("liderExisteSede"));
                    pst.clearParameters();
                    posicion = 1;
                    pst.setLong(posicion++, cargo);
                    pst.setLong(posicion++, inst);
                    pst.setLong(posicion++, sede);
                    pst.setLong(posicion++, vigencia);
                    rs=pst.executeQuery();
                    if(rs.next()){
                    	setMensaje("Ya existe un estudiante para esa misma sede");
                    	return false;
                    }
                    rs.close();pst.close();
            	}
            	if(sedeJornada==Params.PARAM_SEDE_JORNADA){
                    pst = cn.prepareStatement(rb.getString("liderExisteSedeJornada"));
                    pst.clearParameters();
                    posicion = 1;
                    pst.setLong(posicion++, cargo);
                    pst.setLong(posicion++, inst);
                    pst.setLong(posicion++, sede);
                    pst.setLong(posicion++, jor);
                    pst.setLong(posicion++, vigencia);
                    rs=pst.executeQuery();
                    if(rs.next()){
                    	setMensaje("Ya existe un estudiante para esa misma sede y jornada");
                    	return false;
                    }
                    rs.close();pst.close();
            	}
            }
            pst = cn.prepareStatement(rb.getString("LiderExists"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, jer);
            pst.setLong(posicion++, estudiante);
            pst.setLong(posicion++, vigencia);
            rs=pst.executeQuery();
            if(rs.next()){
            	setMensaje("Ya existe un estudiante asignado para ese mismo grado / grupo");
            	return false;
            }
            rs.close(); pst.close();
            pst = cn.prepareStatement(rb.getString("LiderInsertar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, jer);
            if(gru==-9) pst.setLong(posicion++, 0);
            else pst.setLong(posicion++, gru);
            pst.setLong(posicion++, estudiante);
            pst.setLong(posicion++,cargo);
            if (l.getLidTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,l.getLidTelefono());
            if (l.getLidCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,l.getLidCorreo());
			if(l.getLidFechaInicio().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(l.getLidFechaInicio()).getTime()));
            if (l.getLidSedeJornada().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(l.getLidSedeJornada()));
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Lider. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Lider. Posible problema: "+sqle);
            return false;
        } finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean actualizar(LiderVO l,LiderVO l2){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try {
        	long inst=Long.parseLong(l.getLidInst());
        	long sedeOld=Long.parseLong(l2.getLidSede());
        	long jorOld=Long.parseLong(l2.getLidJor());
        	long metOld=Long.parseLong(l2.getLidMet());
        	long graOld=Long.parseLong(l2.getLidGrado());
        	long gruOld=Long.parseLong(l2.getLidGrupo());
        	long estOld=Long.parseLong(l2.getLidCodEstud());
        	long sedeNew=Long.parseLong(l.getLidSede());
        	long jorNew=Long.parseLong(l.getLidJor());
        	long metNew=Long.parseLong(l.getLidMet());
        	long graNew=Long.parseLong(l.getLidGrado());
        	long gruNew=Long.parseLong(l.getLidGrupo());
        	long estNew=Long.parseLong(l.getLidCodEstud());
        	long sedeJornada=Long.parseLong(l.getLidSedeJornada());
        	long cargo=Long.parseLong(l.getLidCargo());
        	long vigencia=getVigenciaNumerico();
        	long nivelOld=7;
        	long nivelNew=7;
        	long jerOld=-1;
        	long jerNew=-1;
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            //jerarquia vieja
            pst = cn.prepareStatement(rb.getString("getJerarquia"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, nivelOld);
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sedeOld);
            pst.setLong(posicion++, jorOld);
            pst.setLong(posicion++, metOld);
            pst.setLong(posicion++, graOld);
            rs=pst.executeQuery();
            if(rs.next()){
            	jerOld=rs.getLong(1);
            }
            rs.close();pst.close();
            //jerarquia nueva
            pst = cn.prepareStatement(rb.getString("getJerarquia"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, nivelNew);
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sedeNew);
            pst.setLong(posicion++, jorNew);
            pst.setLong(posicion++, metNew);
            pst.setLong(posicion++, graNew);
            rs=pst.executeQuery();
            if(rs.next()){
            	jerNew=rs.getLong(1);
            }
            rs.close();pst.close();
            if(jerNew==-1){
            	setMensaje("No se encontro la jerarquia de la ubicacinn");
            	return false;
            }
            //validar lo de si es personero que se ingrese por sede y jornada
            if(sedeOld!=sedeNew && jorOld!=jorNew){
	            if(cargo==Params.PARAM_PERSONERO || cargo==Params.PARAM_CONTRALOR || cargo==Params.PARAM_PRESIDENTE){
	            	if(sedeJornada==Params.PARAM_SEDE){
	                    pst = cn.prepareStatement(rb.getString("liderExisteSede"));
	                    pst.clearParameters();
	                    posicion = 1;
	                    pst.setLong(posicion++, cargo);
	                    pst.setLong(posicion++, inst);
	                    pst.setLong(posicion++, sedeNew);
	                    pst.setLong(posicion++, vigencia);
	                    rs=pst.executeQuery();
	                    if(rs.next()){
	                    	setMensaje("Ya existe un estudiante para esa misma sede");
	                    	return false;
	                    }
	                    rs.close();pst.close();
	            	}
	            	if(sedeJornada==Params.PARAM_SEDE_JORNADA){
	                    pst = cn.prepareStatement(rb.getString("liderExisteSedeJornada"));
	                    pst.clearParameters();
	                    posicion = 1;
	                    pst.setLong(posicion++, cargo);
	                    pst.setLong(posicion++, inst);
	                    pst.setLong(posicion++, sedeNew);
	                    pst.setLong(posicion++, jorNew);
	                    pst.setLong(posicion++, vigencia);
	                    rs=pst.executeQuery();
	                    if(rs.next()){
	                    	setMensaje("Ya existe un estudiante para esa misma sede y jornada");
	                    	return false;
	                    }
	                    rs.close();pst.close();
	            	}
	            }
            }
            pst = cn.prepareStatement(rb.getString("LiderActualizar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, jerNew);
            if(gruNew==-9) pst.setLong(posicion++, 0);
            else pst.setLong(posicion++, gruNew);
            pst.setLong(posicion++, estNew);
            if (l.getLidCargo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(l.getLidCargo()));
            if (l.getLidTelefono().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,l.getLidTelefono());
            if (l.getLidCorreo().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++,l.getLidCorreo());
			if(l.getLidFechaInicio().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			else pst.setDate(posicion++,new java.sql.Date(sdf.parse(l.getLidFechaInicio()).getTime()));
			pst.setLong(posicion++, sedeJornada);
			//where
            pst.setLong(posicion++, jerOld);
            pst.setLong(posicion++, gruOld);
            pst.setLong(posicion++, estOld);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando actualizar Lider. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando actualizar Lider. Posible problema: "+sqle);
            return false;
        } finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
    public synchronized boolean eliminarLider(String id,String id2,String id3){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
        	long est=Long.parseLong(id);
        	long jer=Long.parseLong(id2);
        	long grupo=Long.parseLong(id3);
        	long vigencia=getVigenciaNumerico();
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("LiderEliminar"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++, jer);
            pst.setLong(posicion++, grupo);
            pst.setLong(posicion++, est);
            pst.setLong(posicion++, vigencia);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando eliminar Lider. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando eliminar Lider. Posible problema: "+sqle);
            return false;
        } finally{
            try{
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    
	public Collection getAsociacionesIntegrantes(AsociacionVO asociacionVO){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try{
			long vigencia=getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaAsociacionesIntegrantes"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(asociacionVO.getAsoInst()));
			ps.setLong(posicion++, Long.parseLong(asociacionVO.getAsoCodigo()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de asociacionesIntegrantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection getLideres(Login l){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try{
			long vigencia=getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaLideres"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getInstId()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de asociacionesIntegrantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection getEstudiantesGrado(Login l,String[] params){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try{
			long inst=Long.parseLong(l.getInstId());
			long sede=Long.parseLong(params[0]);
			long jor=Long.parseLong(params[1]);
			long met=Long.parseLong(params[2]);
			long gra=Long.parseLong(params[3]);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("EstudiantesGrado"));
			posicion = 1;
			ps.setLong(posicion++,inst);
			ps.setLong(posicion++,sede);
			ps.setLong(posicion++,jor);
			ps.setLong(posicion++,met);
			ps.setLong(posicion++,gra);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener Estudiantes de grado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection getEstudiantesGrupo(Login l,String[] params){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try{
			long inst=Long.parseLong(l.getInstId());
			long sede=Long.parseLong(params[0]);
			long jor=Long.parseLong(params[1]);
			long met=Long.parseLong(params[2]);
			long gra=Long.parseLong(params[3]);
			long gru=Long.parseLong(params[4]);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("EstudiantesGrupo"));
			posicion = 1;
			ps.setLong(posicion++,inst);
			ps.setLong(posicion++,sede);
			ps.setLong(posicion++,jor);
			ps.setLong(posicion++,met);
			ps.setLong(posicion++,gra);
			ps.setLong(posicion++,gru);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener Estudiantes de grupo. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection[] getSedes(Login login){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection []list = new Collection[2];
		try{
			long inst=Long.parseLong(login.getInstId());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("filtroSedeInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("filtroSedeJornadaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de gobiernos. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection[] getConsejos(Login login){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection []list = new Collection[2];
		try{
			long vigencia=getVigenciaNumerico();
			long inst=Long.parseLong(login.getInstId());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaConsejosDirectivos"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("listaConsejosAcademicos"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de consejos. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection[] getFiltrosLider(Login login){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection []list = new Collection[5];
		try{
			long inst=Long.parseLong(login.getInstId());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("filtroSedeInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("filtroSedeJornadaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("listaMetodologiaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[2] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("filtroSedeJornadaGradoInstitucion2"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[3] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("filtroSedeJornadaGradoGrupoInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[4] = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener filtros de lideres. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection[] getSedesDocentes(Login login){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection []list = new Collection[3];
		try{
			long inst=Long.parseLong(login.getInstId());
			long jerarquia=0;
			cn = cursor.getConnection();
			//obtener el codigo de jerarquia de colegio
			ps = cn.prepareStatement(rb.getString("Lista.jerarquiaColegio"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			if(rs.next()){jerarquia=rs.getLong(1);}
			ps = cn.prepareStatement(rb.getString("Lista.filtroSedeInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Lista.filtroSedeJornadaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Lista.listaDocentes"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, jerarquia);
			rs = ps.executeQuery();
			list[2] = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de gobiernos. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public AsociacionVO getAsociacion(Login login){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AsociacionVO a=null; 
		try{
			long vigencia=getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getAsociacion"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(login.getInstId()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			int i=1;
			if(rs.next()){
				a=new AsociacionVO();
				a.setAsoCodigo(rs.getString(i++)); 
				a.setAsoInst(rs.getString(i++)); 
				a.setAsoNombre(rs.getString(i++)); 
				a.setAsoFechaConstitucion(rs.getString(i++)); 
				a.setAsoPersoneria(rs.getString(i++)); 
				a.setAsoVigencia(rs.getString(i++)); 
				a.setAsoFechaInicio(rs.getString(i++)); 
				a.setAsoFechaFin(rs.getString(i++));
				a.setAsoEstado("1");
			}else{
				a=new AsociacionVO();
				a.setAsoInst(login.getInstId());				
				a.setAsoEstado("");
			}
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de Asociacion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return a;
	}
	
	
	public AsociacionIntegrantesVO getAsociacionIntegrantesVO(String id,String id2,AsociacionVO asociacionVO){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AsociacionIntegrantesVO a=null; 
		try{
			long vigencia=getVigenciaNumerico();
			long tipoId=Long.parseLong(id);
			String numId=(id2);
			long inst=Long.parseLong(asociacionVO.getAsoInst());	
			long cod=Long.parseLong(asociacionVO.getAsoCodigo());	
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getAsociacionIntegrantes"));
			posicion = 1;
			ps.setLong(posicion++,tipoId);
			ps.setString(posicion++,numId);
			ps.setLong(posicion++,inst);
			ps.setLong(posicion++,cod);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			int i=1;
			if(rs.next()){
				a=new AsociacionIntegrantesVO();
				a.setAsoIntCodigo(rs.getString(i++));
				a.setAsoIntInst(rs.getString(i++));
				a.setAsoIntTipodoc(rs.getString(i++));
				a.setAsoIntNumdoc(rs.getString(i++));
				a.setAsoIntCargo(rs.getString(i++));
				a.setAsoIntNombre(rs.getString(i++));
				a.setAsoIntApellido(rs.getString(i++));
				a.setAsoIntTelefono(rs.getString(i++));
				a.setAsoIntCorreo(rs.getString(i++)); 
				a.setAsoIntGenero(rs.getString(i++));
				a.setAsoIntEstado("1");
			}
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de AsociacionIntegrantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return a;
	}
	
	public LiderVO getLiderVO(String id,String id2,String id3){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LiderVO l=null; 
		try{
			long vigencia=getVigenciaNumerico();
			long est=Long.parseLong(id);
			long jer=Long.parseLong(id2);
			long gru=Long.parseLong(id3);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getLider"));
			posicion = 1;
			ps.setLong(posicion++,jer);
			ps.setLong(posicion++,gru);
			ps.setLong(posicion++,est);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			int i=1;
			if(rs.next()){
				l=new LiderVO();
				l.setLidInst(rs.getString(i++));
				l.setLidSede(rs.getString(i++));
				l.setLidJor(rs.getString(i++));
				l.setLidMet(rs.getString(i++));
				l.setLidGrado(rs.getString(i++));
				l.setLidGrupo(rs.getString(i++));
				l.setLidCodJerar(rs.getString(i++));
				l.setLidCodEstud(rs.getString(i++));
				l.setLidCargo(rs.getString(i++));
				l.setLidTelefono(rs.getString(i++));
				l.setLidCorreo(rs.getString(i++));
				l.setLidFechaInicio(rs.getString(i++));
				l.setLidSedeJornada(rs.getString(i++));
				l.setLidFrmEstado("1");
			}
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de AsociacionIntegrantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return l;
	}
	
	public ConsejoDirectivoVO getConsejoDirectivoVO(Login login,String id,String id2){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConsejoDirectivoVO a=null; 
		try{
			long vigencia=getVigenciaNumerico();
			long tipoId=Long.parseLong(id);
			String numId=(id2);
			long inst=Long.parseLong(login.getInstId());	
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getConsejoDirectivo"));
			posicion = 1;
			ps.setLong(posicion++,inst);
			ps.setLong(posicion++,tipoId);
			ps.setString(posicion++,numId);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			int i=1;
			if(rs.next()){
				a=new ConsejoDirectivoVO();
				a.setConDirInst(rs.getString(i++));
				a.setConDirSede(rs.getString(i++));
				a.setConDirJornada(rs.getString(i++)); 
				a.setConDirCargo(rs.getString(i++));
				a.setConDirNombre(rs.getString(i++));
				a.setConDirApellido(rs.getString(i++));
				a.setConDirTipoDoc(rs.getString(i++));
				a.setConDirNumDoc(rs.getString(i++));
				a.setConDirTelefono(rs.getString(i++));
				a.setConDirCorreo(rs.getString(i++));
				a.setConDirGenero(rs.getString(i++));
				a.setConDirEstado(rs.getString(i++));
				a.setConDirFrmEstado("1");
			}
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de Consejo Directivo. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return a;
	}
	public ConsejoAcademicoVO getConsejoAcademicoVO(Login login,String id,String id2,String id3,String id4){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConsejoAcademicoVO a=null; 
		try{
			long inst=Long.parseLong(login.getInstId());	
			long sede=Long.parseLong(id);
			long jor=Long.parseLong(id2);
			long tipoId=Long.parseLong(id3);
			long vigencia=getVigenciaNumerico();
			String numId=(id4);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getConsejoAcademico"));
			posicion = 1;
			ps.setLong(posicion++,inst);
			ps.setLong(posicion++,sede);
			ps.setLong(posicion++,jor);
			ps.setLong(posicion++,tipoId);
			ps.setString(posicion++,numId);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			int i=1;
			if(rs.next()){
				a=new ConsejoAcademicoVO();
				a.setConAcaInst(rs.getString(i++));
				a.setConAcaSede(rs.getString(i++));
				a.setConAcaJornada(rs.getString(i++));
				a.setConAcaCargo(rs.getString(i++));
				a.setConAcaTipoDoc(rs.getString(i++));
				a.setConAcaNumDoc(rs.getString(i++));
				a.setConAcaTelefono(rs.getString(i++));
				a.setConAcaCorreo(rs.getString(i++));
				a.setConAcaEstado(rs.getString(i++));
				a.setConAcaFrmEstado("1");
			}
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener información de Consejo Acadnmico. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return a;
	}
}