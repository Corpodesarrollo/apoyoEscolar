package siges.conflicto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.rotacion.beans.Rotacion;
import siges.conflicto.beans.*;

public class ConflictoEscolarDAO extends Dao {

    public String sentencia;
    public String mensaje;
    public String buscar;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;
    public static final int GRUPOCERRADO=1;
    public static final int GRUPOABIERTO=0;
    public static final int PERIODOCERRADO=1;
    public static final int PERIODOABIERTO=0;

    public ConflictoEscolarDAO(Cursor cur) {
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("conflicto");
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
    
    
    public boolean isPeriodoCerrado(ConflictoEscolar ce, Login l){
    	Connection cn=null;
    	PreparedStatement pst=null;
    	int posicion=1,estado=-1;
    	ResultSet rs=null;
    	try{
    		cn=cursor.getConnection();
    		if(ce.getCeperiodo().equals("10")){
    			pst=cn.prepareStatement(rb.getString("Obtener.Periodo1"));
    		}else if(ce.getCeperiodo().equals("20")){
    			pst=cn.prepareStatement(rb.getString("Obtener.Periodo2"));
    		}else{
    			pst=cn.prepareStatement(rb.getString("Obtener.Periodo"+ce.getCeperiodo()));
    		}
    		
    		pst.clearParameters();
    		posicion=1;
      						
    		if(l.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(l.getInstId().trim()));
    		if(ce.getCesede().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(ce.getCesede().trim()));
    		if(ce.getCejornada().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(ce.getCejornada().trim()));
    		if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
    		rs=pst.executeQuery();
    		if(rs.next())	estado=rs.getInt(1);
    		if(estado!=-1){
    			if(estado==PERIODOCERRADO){
    				return true;
    			}
    		}
    	}catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
    	catch(SQLException sqle){sqle.printStackTrace();
    	try{cn.rollback();}catch(SQLException se){}
    	setMensaje("Error SQL intentando ingresar información de Medidas Institucionales. Posible problema: ");
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
    	return false;
    }
    
    public TipoConflicto asignarTipoConflicto(String jerjorn, String per, int cat){
        TipoConflicto tc=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
        	System.out.println("asignarTipoConflicto-per: "+per+" jorn: "+jerjorn+ " cat: "+cat);
        	cn=cursor.getConnection();
        	pst=cn.prepareStatement(rb.getString("Asignar.TipoConflicto"));
        	pst.clearParameters();
        	posicion=1;
        	if(jerjorn.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(jerjorn.trim()));
        	if(per.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(per.trim()));
        	pst.setInt(posicion++,cat);
        	if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
        	else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
        	rs = pst.executeQuery();
        	tc=new TipoConflicto();
        	Collection list = new ArrayList();
        	Object[] o;
        	while (rs.next()){
        		i=1;
        		o=new Object[3];//CAMBIO
        		o[0]=rs.getString(i++);
        		o[1]=rs.getString(i++);
        		o[2]=rs.getString(i++);//NUEVO CAMPO
        		System.out.println("valor de VALORNUM_ENTESC : "+rs.getString(3));
        		list.add(o);
        	}
        	tc.setTipoconflicto(getFiltroMatriz(list));
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return tc;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return tc;
    }
    
    public boolean actualizarOtro(String otro){
        Connection cn=null;
      		PreparedStatement pst=null;
      		int posicion=1;
      		ResultSet rs=null;
      		try{
      		    cn=cursor.getConnection(); cn.setAutoCommit(false);
      		    pst=cn.prepareStatement(rb.getString("Actualizar.Otro"));
      		    pst.clearParameters();
      		    posicion=1;
      		    if(otro.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
      		    else pst.setString(posicion++,otro);
      		    pst.executeUpdate();
      		    
      		    cn.commit();
      		    cn.setAutoCommit(true);
      		}catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
      		catch(SQLException sqle){sqle.printStackTrace();
      		try{cn.rollback();}catch(SQLException se){}
      		setMensaje("Error SQL intentando ingresar información de Medidas Institucionales. Posible problema: ");
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
    
    public ResolucionConflictos asignarResolucionConflicto(String jerjorn, String per, int cat){
    	ResolucionConflictos rc=null;
    	Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        String otro="";
        try{
        	cn=cursor.getConnection();
        	pst=cn.prepareStatement(rb.getString("Asignar.FormaResolucion"));
        	pst.clearParameters();
        	posicion=1;
        	if(jerjorn.equals(""))
        		pst.setNull(posicion++,java.sql.Types.NULL);
        	else
        		pst.setLong(posicion++,Long.parseLong(jerjorn.trim()));
        	if(per.equals(""))
        		pst.setNull(posicion++,java.sql.Types.NULL);
        	else
        		pst.setLong(posicion++,Long.parseLong(per.trim()));
        	
        	pst.setInt(posicion++,cat);
        	
        	if(getVigencia().equals("")) 
        		pst.setNull(posicion++,java.sql.Types.NULL);
        	else
        		pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
        	
        	rs = pst.executeQuery();
        	
        	rc=new ResolucionConflictos();
        	Collection list = new ArrayList();
        	List listaOtro = new ArrayList();
        	Object[] o;
        	while (rs.next()){
        		i=1;
        		o=new Object[2];
        		o[0]=rs.getString(i++);
        		o[1]=rs.getString(i++);
        		list.add(o);
        		listaOtro.add(rs.getString(i++));
        	}
        	String [] vectorOtro= new String[listaOtro.size()];
        	int j=0;
        	Iterator iterator = listaOtro.iterator();
			while (iterator.hasNext()) {
				vectorOtro[j]=(String) iterator.next();
				j++;
			}
        	rc.setResotro(vectorOtro);
            rc.setResconflicto(getFiltroMatriz(list));
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return rc;
   	    }finally{
   	        try{
   	        	OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return rc;
    }
    
    public Proyectos asignarProyectos(String jerjorn, String per, int cat){
        Proyectos pr=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
            cn=cursor.getConnection();
            pst=cn.prepareStatement(rb.getString("Asignar.TipoConflicto"));
            pst.clearParameters();
            posicion=1;
            if(jerjorn.equals("")) 
            	pst.setNull(posicion++,java.sql.Types.NULL);
            else 
            	pst.setLong(posicion++,Long.parseLong(jerjorn.trim()));
            if(per.equals("")) 
            	pst.setNull(posicion++,java.sql.Types.NULL);
            else 
            	pst.setLong(posicion++,Long.parseLong(per.trim()));
            
            pst.setInt(posicion++,cat);
            if(getVigencia().equals("")) 
            	pst.setNull(posicion++,java.sql.Types.NULL);
            else 
            	pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
            
            rs = pst.executeQuery();
            pr=new Proyectos();
            Collection list = new ArrayList();
            Object[] o;
            while (rs.next()){
            	i=1;
            	o=new Object[2];
            	o[0]=rs.getString(i++);
            	o[1]=rs.getString(i++);
            	list.add(o);
            }
            pr.setProyvalor(getFiltroMatriz(list));
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return pr;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return pr;
    }
    
    public MedidasInst asignarMedidasInst(String jerjorn, String per, int cat){
        MedidasInst mi=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
            cn=cursor.getConnection();
            pst=cn.prepareStatement(rb.getString("Asignar.MedidasInst"));
            pst.clearParameters();
            posicion=1;
            if(jerjorn.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(jerjorn.trim()));
            if(per.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(per.trim()));
            pst.setInt(posicion++,cat);
            if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
            rs = pst.executeQuery();
            mi=new MedidasInst();
            Collection list = new ArrayList();
            Object[] o;
            while (rs.next()){
            	i=1;
            	o=new Object[2];
            	o[0]="t"+rs.getString(i++)+"|"+rs.getString(i++);
            	o[1]=rs.getString(i++);
            	list.add(o);
            }
            mi.setMedtexto(getFiltroMatriz(list));
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
   	    switch(sqle.getErrorCode()){
   	    default:
   	        setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return mi;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return mi;
    }
    
    public InfluenciaConflictos asignarInfluencia(String jerjorn, String per, int cat){
        InfluenciaConflictos ic=null;
        Connection cn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
            cn=cursor.getConnection();
            pst=cn.prepareStatement(rb.getString("Asignar.Influencia"));
            pst.clearParameters();
            posicion=1;
            if(jerjorn.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(jerjorn.trim()));
            if(per.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(per.trim()));
            pst.setInt(posicion++,cat);
            if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
            else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
            rs = pst.executeQuery();
            ic=new InfluenciaConflictos();
            Collection list = new ArrayList();
            Object[] o;
            while (rs.next()){
            	i=1;
            	o=new Object[2];
            	o[0]="d"+rs.getString(i++);
            	o[1]=rs.getString(i++);
            	list.add(o);
            }
            ic.setInftexto(getFiltroMatriz(list));
        }catch(InternalErrorException in){in.printStackTrace(); setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
        catch(SQLException sqle){sqle.printStackTrace();
        setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
        switch(sqle.getErrorCode()){
        default:
        	setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
   	    }
   	    return ic;
   	    }finally{
   	        try{
   	            OperacionesGenerales.closeResultSet(rs);
   	            OperacionesGenerales.closeStatement(pst);
   	            OperacionesGenerales.closeConnection(cn);
   	        }catch(InternalErrorException inte){}
   	    }
   	    return ic;
    }
    
    public boolean insertarMedidasInst(String jerjorn, String periodo, String clase, String tipo, String valor, int cat){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
        ResultSet rs=null;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Insertar.MedidasInst"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(jerjorn.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jerjorn));
   	        if(periodo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(periodo));
   	        if(clase.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(clase));
   	        if(tipo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(tipo));
   	        if(valor.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(valor));
   	        pst.setInt(posicion++,cat);
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        pst.executeUpdate();
   	        
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
   	    	setMensaje("Error SQL intentando ingresar información de Medidas Institucionales. Posible problema: ");
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
    
    public boolean insertarInfluencias(String jerjorn, String periodo, String tipo, String valor, int cat){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
        ResultSet rs=null;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Insertar.Influencias"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(jerjorn.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jerjorn));
   	        if(periodo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(periodo));
   	        if(tipo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(tipo));
   	        if(valor.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setString(posicion++,valor);
   	        pst.setInt(posicion++,cat);
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        
   	        System.out.println(" - filas afectadas_: "+pst.executeUpdate());
   	        
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
   	    	setMensaje("Error SQL intentando ingresar información de Influencias en resolucinn de conflictos. Posible problema: ");
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
    
    public boolean insertarTipoConflicto(String jerjorn, String periodo, String tipo, String valor, String valor_ent_esc,int cat){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
        ResultSet rs=null;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        
//   	        System.out.println("***insertarTipoConflicto***");
//   	        System.out.println("jerjorn: "+jerjorn);
//   	        System.out.println("periodo: "+periodo);
//   	        System.out.println("tipo: "+periodo);
//   	        System.out.println("valor: "+valor);
//   	        System.out.println("valor_ent_esc: "+valor_ent_esc);
//   	        System.out.println("cat: "+cat);
   	     
   	        pst=cn.prepareStatement(rb.getString("Insertar.TipoConflicto"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(jerjorn.equals("")) 
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else 
   	        	pst.setLong(posicion++,Long.parseLong(jerjorn));
   	        if(periodo.equals("")) 
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else 
   	        	pst.setLong(posicion++,Long.parseLong(periodo));
   	        if(tipo.equals("")) 
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else 
   	        	pst.setLong(posicion++,Long.parseLong(tipo));
   	        if(valor.equals("")) 
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else 
   	        	pst.setLong(posicion++,Long.parseLong(valor));
   	        
   	        pst.setInt(posicion++,cat);
   	        if(getVigencia().equals("")) 
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
	        else 
	        	pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        if(valor_ent_esc.equals("") || valor_ent_esc.equals("-9")) 
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else 
   	        	pst.setLong(posicion++,Long.parseLong(valor_ent_esc));
   	        pst.executeUpdate();
      	  			
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
   	    	setMensaje("Error SQL intentando ingresar información de Tipo de Conflicto. Posible problema: ");
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
    
    public boolean insertarFormaResolucion(String jerjorn, String periodo, String tipo, String valor, int cat, String otro){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
        ResultSet rs=null;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Insertar.FormaResolucion"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(jerjorn.equals(""))
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else
   	        	pst.setLong(posicion++,Long.parseLong(jerjorn));
   	        if(periodo.equals(""))
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else
   	        	pst.setLong(posicion++,Long.parseLong(periodo));
   	        if(tipo.equals(""))
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else
   	        	pst.setLong(posicion++,Long.parseLong(tipo));
   	        if(valor.equals(""))
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else
   	        	pst.setLong(posicion++,Long.parseLong(valor));
   	        
   	        pst.setInt(posicion++,cat);
   	        pst.setString(posicion++,otro);
   	        if(getVigencia().equals(""))
   	        	pst.setNull(posicion++,java.sql.Types.NULL);
   	        else
   	        	pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        pst.executeUpdate();
      	  			
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
   	    	setMensaje("Error SQL intentando ingresar información de Resolucinn de Conflicto. Posible problema: ");
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
    
    public boolean eliminarTipoConflicto(String jerjorn, String periodo, int cat){
   	    Connection cn=null;
   	    PreparedStatement pst=null;
   	    int posicion=1;
        ResultSet rs=null;
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Eliminar.TipoConflicto"));
   	        pst.clearParameters();
   	        posicion=1;
   	        if(jerjorn.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jerjorn));
   	        if(periodo.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(periodo));
   	        pst.setInt(posicion++,cat);
   	        if(getVigencia().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
	        else pst.setLong(posicion++,Long.parseLong(getVigencia().trim()));
   	        pst.executeUpdate();
   	        cn.commit();
   	        cn.setAutoCommit(true);
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
   	    	setMensaje("Error SQL intentando eliminar información de Conflicto Escolar. Posible problema: ");
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
    
    public String obtenerJerar_1_6(String sede, String jornada, Login l){
        Connection cn=null;
   	    PreparedStatement pst=null;
   	    ResultSet rs=null;
   	    int posicion=1;
        String jerar="";
   	    try{
   	        cn=cursor.getConnection(); cn.setAutoCommit(false);
   	        pst=cn.prepareStatement(rb.getString("Asignar.Jerar_1_6"));
   	        pst.clearParameters();
   	        posicion=1;
   	        
   	        if(l.getInstId().equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(l.getInstId().trim()));
   	        if(sede.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(sede.trim()));
   	        if(jornada.equals("")) pst.setNull(posicion++,java.sql.Types.NULL);
   	        else pst.setLong(posicion++,Long.parseLong(jornada.trim()));
   	        rs = pst.executeQuery();
   	        if(rs.next()){
   	            jerar=rs.getString(1);
   	        }
   	    }catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return "";}
   	    catch(SQLException sqle){sqle.printStackTrace();
   	    try{cn.rollback();}catch(SQLException se){}
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

}