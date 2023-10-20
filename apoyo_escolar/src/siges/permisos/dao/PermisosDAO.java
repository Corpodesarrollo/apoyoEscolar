package siges.permisos.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Iterator;

import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Cursor;
import siges.exceptions.InternalErrorException;
import siges.estudiante.beans.*;
import siges.institucion.beans.*;
import siges.evaluacion.beans.*;
import siges.personal.beans.*;
import siges.personal.beans.*;
import siges.usuario.beans.*;
import siges.perfil.beans.*;


/**
*	Nombre:	Util
*	Descripcion:	Realiza operaciones en la BD
*	Funciones de la pagina:	Realiza la logica de negocio
*	Entidades afectadas:	todas las tablas de la BD
*	Fecha de modificacinn:	Feb-05
*	@author Latined
*	@version $v 1.3 $
*/

public class PermisosDAO extends Dao{
	
	private Cursor cursor;
	public String sentencia;
	public String mensaje;
	public String buscar;
	private java.text.SimpleDateFormat sdf;
	private java.sql.Timestamp f2;
	private PreparedStatement pst,pst2,pst3;
	private Connection cn;
	private ResourceBundle rbPermisos;
	public int []resultado;

/**
*	Constructor de la clase
**/
	/*public PermisosDAO(){
		sentencia=null;
		mensaje="";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		resultado=new int[3];
		rbPermisos=ResourceBundle.getBundle("permisos");
	}*/

/**
*	Constructor de la clase
**/
public PermisosDAO(Cursor cur){
	super(cur);
	this.cursor=cur;
	sentencia=null;
	mensaje="";
	sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	sdf.setLenient(false);
	rbPermisos=ResourceBundle.getBundle("permisos");
}
	
/**
*	Funcinn:  Cerrar conecciones<br>
**/
public void cerrar(){
	try{		
	OperacionesGenerales.closeStatement(pst);
	OperacionesGenerales.closeStatement(pst2);
	OperacionesGenerales.closeStatement(pst3);
	OperacionesGenerales.closeConnection(cn);
	rbPermisos=null;
	if(cursor!=null)cursor.cerrar();
	}catch(Exception e){}	
}

/**
*	Funcinn: Elimina privilegios de la base de datos <br>
*	@param	String id
*	@return	boolean
**/
public boolean eliminarPermisos(String id){
	int posicion=1;
	try{
	    System.out.println(id+" - "+rbPermisos.getString("PermisosEliminar"));
		cn=cursor.getConnection(); cn.setAutoCommit(false);
		pst=cn.prepareStatement(rbPermisos.getString("PermisosEliminar"));
		pst.clearParameters();
		if(id.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
		else pst.setString(posicion++,id.trim());
		pst.executeUpdate();
		cn.commit();
		cn.setAutoCommit(true);
	}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
	catch(SQLException sqle){
		try{cn.rollback();}catch(SQLException s){}
 		setMensaje("Error SQL intentando ELIMINAR permisos. Posible problema: ");
		switch(sqle.getErrorCode()){
			default:
				setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
		}
		return false;
	}finally{
		try{
		OperacionesGenerales.closeStatement(pst);
		OperacionesGenerales.closeConnection(cn);
		cursor.cerrar();
		}catch(InternalErrorException inte){}
	}
	return true;
}

/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){
		mensaje+=s;	
	}

/**
*	Retorna una variable que contiene los mensajes que se van a enviar a la vista
*	@return String	
**/
	public String getMensaje(){
		return mensaje;
	}

	public String[] parsear(String a,int num){
			int aux=0,aux2=-1,cont=0;
			String m[]=new String[num];
			if(num==2){
				aux=a.lastIndexOf("|");
				if(aux!=-1){
					m[0]=a.substring(0,aux);
					m[1]=a.substring(aux+1,a.length());			
				}else{
					m[0]=a;m[1]="";
				}
				return m;
			}
			for(int j=0;j<num;j++){
				aux=a.indexOf("|",aux);
				if(aux!=-1){
					m[j]=a.substring(aux2+1,aux);
					aux2=aux; aux++;
				}else
					m[j]=a.substring(aux2+1,a.length());
			}
			return m;
		}
}