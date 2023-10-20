package siges.parametro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.parametro.beans.Parametros;
import siges.usuario.beans.Usuario;
import siges.util.Recursos;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ParametroDAO extends siges.dao.Dao{

    private Cursor cursor;
    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private java.sql.Timestamp f2;
    private ResourceBundle rbParam;
    public int []resultado;

    /**
     *	Constructor de la clase
     **/
    public ParametroDAO(Cursor cur){
        super(cur);
        this.cursor=cur;
        sentencia=null;
        mensaje="";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rbParam=ResourceBundle.getBundle("siges.parametro.bundle.parametro");
    }

    public Parametros obtenerParams(){
        Parametros p=null;
        Connection cn=null;
        Statement st=null;
        ResultSet rs=null;
        int posicion=1;
        try {
            cn = cursor.getConnection();
            st=cn.createStatement();
            rs = st.executeQuery(rbParam.getString("asignarParam"));
            posicion = 1;
            if(rs.next()){
                p=new Parametros();
                int i=1;
                p.setG_parvigencia(rs.getString(i++));
                p.setG_parhorario(rs.getString(i++));
                p.setG_parrec_dia(rs.getString(i++));
                p.setG_parrec_hora(rs.getString(i++));
                p.setG_parrec_sleep(rs.getString(i++));
                p.setG_parrec_activo(rs.getString(i++));
                p.setG_parpla_activo(rs.getString(i++));
                p.setG_parpla_dia(rs.getString(i++));
                p.setG_parpla_hora(rs.getString(i++));
                p.setG_parpla_sleep(rs.getString(i++));
                //p.setG_parpla_periodo(rs.getString(i++));
                p.setG_parimp_activo(rs.getString(i++));
                p.setG_parimp_dia(rs.getString(i++));
                p.setG_parimp_hora(rs.getString(i++));
                p.setG_parimp_sleep(rs.getString(i++));
                p.setG_parboletin(rs.getString(i++));
                p.setG_parlibro(rs.getString(i++));
                p.setG_parintegracion(rs.getString(i++));
                p.setG_parestadistico(rs.getString(i++));
                p.setG_parcertificado(rs.getString(i++));
                
                
                p.setG_parintegracionMatr(rs.getString(i++));
                p.setG_parintegracionMatr_dia(rs.getInt(i++));
                p.setG_parintegracionMatr_hora(rs.getInt(i++));
                p.setG_parintegracionMatr_msj(rs.getString(i++));
                
                
            }
        }catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        }catch (SQLException sqle) {
            sqle.printStackTrace();
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando obtener Parnmetros. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return null;
        }finally {
            try {
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(st);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {}
        }
        return p;
    }

    public boolean actualizarGeneral(Parametros p){
        int posicion; //posicion inicial de llenado del preparedstatement
        Connection cn=null;
        PreparedStatement pst=null;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);

            posicion=1;
            pst=cn.prepareStatement(rbParam.getString("ActualizarGeneral"));
            pst.clearParameters();
            pst.setInt(posicion++,Integer.parseInt(p.getG_parboletin().trim()));
            pst.setInt(posicion++,Integer.parseInt(p.getG_parhorario().trim()));
            pst.setInt(posicion++,Integer.parseInt(p.getG_parintegracion().trim()));
            pst.setInt(posicion++,Integer.parseInt(p.getG_parlibro().trim()));
            pst.setInt(posicion++,Integer.parseInt(p.getG_parvigencia().trim()));
            pst.setInt(posicion++,Integer.parseInt(p.getG_parestadistico().trim()));
            pst.setInt(posicion++,Integer.parseInt(p.getG_parcertificado().trim()));
            
            //System.out.println("p.getG_parintegracionMatr() " + p.getG_parintegracionMatr());
            //adicionado el 15-05-10
            pst.setInt(posicion++,Integer.parseInt(p.getG_parintegracionMatr().trim()));
            pst.setInt(posicion++,p.getG_parintegracionMatr_dia());
            pst.setInt(posicion++,p.getG_parintegracionMatr_hora());
            
            pst.executeUpdate();

            cn.commit();
            Recursos.setRecurso(Recursos.VIGENCIA);
            cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException s){}
        setMensaje("Error intentando actualizar información de Parnmetros.("+sqle.getErrorCode()+") Posible problema: ");
        switch(sqle.getErrorCode()){
        default:
            setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
        }
        return false;
        }
        finally{
            try{
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }

    public boolean actualizarRecolector(Parametros p){
        int posicion; //posicion inicial de llenado del preparedstatement
        Connection cn=null;
        PreparedStatement pst=null;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);

            if(p.getG_parrec_activo().equals("0")){
                posicion=1;
                pst=cn.prepareStatement(rbParam.getString("ActualizarRecolector0"));
                pst.clearParameters();
                pst.setInt(posicion++,Integer.parseInt(p.getG_parrec_activo().trim()));
            }else if(p.getG_parrec_activo().equals("1")){
                posicion=1;
                pst=cn.prepareStatement(rbParam.getString("ActualizarRecolector1"));
                pst.clearParameters();
                pst.setInt(posicion++,Integer.parseInt(p.getG_parrec_activo().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parrec_dia().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parrec_hora().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parrec_sleep().trim()));
            }
            pst.executeUpdate();

            cn.commit();
            cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException s){}
        setMensaje("Error intentando actualizar información de Parnmetros.("+sqle.getErrorCode()+") Posible problema: ");
        switch(sqle.getErrorCode()){
        default:
            setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
        }
        return false;
        }
        finally{
            try{
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
  	
    public boolean actualizarPlantillasBatch(Parametros p){
        int posicion; //posicion inicial de llenado del preparedstatement
        Connection cn=null;
        PreparedStatement pst=null;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);

            if(p.getG_parpla_activo().equals("0")){
                posicion=1;
                pst=cn.prepareStatement(rbParam.getString("ActualizarPbatch0"));
                pst.clearParameters();
                pst.setInt(posicion++,Integer.parseInt(p.getG_parpla_activo().trim()));
            }else if(p.getG_parpla_activo().equals("1")){
                posicion=1;
                pst=cn.prepareStatement(rbParam.getString("ActualizarPbatch1"));
                pst.clearParameters();
                pst.setInt(posicion++,Integer.parseInt(p.getG_parpla_activo().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parpla_dia().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parpla_hora().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parpla_sleep().trim()));
                //pst.setInt(posicion++,Integer.parseInt(p.getG_parpla_periodo().trim()));
            }
            pst.executeUpdate();

            cn.commit();
            cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException s){}
        setMensaje("Error intentando actualizar información de Parnmetros.("+sqle.getErrorCode()+") Posible problema: ");
        switch(sqle.getErrorCode()){
        default:
            setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
        }
        return false;
        }
        finally{
            try{
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }

    public boolean actualizarImportarBatch(Parametros p){
        int posicion; //posicion inicial de llenado del preparedstatement
        Connection cn=null;
        PreparedStatement pst=null;
        try{
            cn=cursor.getConnection(); cn.setAutoCommit(false);

            if(p.getG_parimp_activo().equals("0")){
                posicion=1;
                pst=cn.prepareStatement(rbParam.getString("ActualizarIbatch0"));
                pst.clearParameters();
                pst.setInt(posicion++,Integer.parseInt(p.getG_parimp_activo().trim()));
            }else if(p.getG_parimp_activo().equals("1")){
                posicion=1;
                pst=cn.prepareStatement(rbParam.getString("ActualizarIbatch1"));
                pst.clearParameters();
                pst.setInt(posicion++,Integer.parseInt(p.getG_parimp_activo().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parimp_dia().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parimp_hora().trim()));
                pst.setInt(posicion++,Integer.parseInt(p.getG_parimp_sleep().trim()));
            }

            pst.executeUpdate();

            cn.commit();
            cn.setAutoCommit(true);
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
        catch(SQLException sqle){sqle.printStackTrace();
        try{cn.rollback();}catch(SQLException s){}
        setMensaje("Error intentando actualizar información de Parnmetros.("+sqle.getErrorCode()+") Posible problema: ");
        switch(sqle.getErrorCode()){
        default:
            setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
        }
        return false;
        }
        finally{
            try{
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }

}