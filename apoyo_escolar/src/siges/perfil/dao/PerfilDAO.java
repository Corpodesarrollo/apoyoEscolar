package siges.perfil.dao;

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

public class PerfilDAO extends siges.dao.Dao{
	
	private Cursor cursor;
	public String sentencia;
	public String mensaje;
	public String buscar;
	private java.text.SimpleDateFormat sdf;
	private java.sql.Timestamp f2;
	private PreparedStatement pst,pst2,pst3;
	private Connection cn;
	private ResourceBundle rbPerfil;
	public int []resultado;

/**
*	Constructor de la clase
**/
	/*public PerfilDAO(){
		sentencia=null;
		mensaje="";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		resultado=new int[3];
		rbPerfil=ResourceBundle.getBundle("perfil");
	}*/

/**
*	Constructor de la clase
**/
public PerfilDAO(Cursor cur){
	super(cur);
	this.cursor=cur;
	sentencia=null;
	mensaje="";
	sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	sdf.setLenient(false);
	rbPerfil=ResourceBundle.getBundle("perfil");
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
	rbPerfil=null;
	if(cursor!=null)cursor.cerrar();
	}catch(Exception e){}	
}

/**
*	Funcinn: Eliminar un Perfil de la base de datos<br>
*	@param	String perf
*	@return	boolean
**/
public boolean eliminarPerfil(String perf){
int posicion=1;
try{
	cn=cursor.getConnection(); cn.setAutoCommit(false);
	pst=cn.prepareStatement(rbPerfil.getString("PerfilEliminar"));
	pst.clearParameters();
	if(perf.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
	else pst.setString(posicion++,perf.trim());
	pst.executeUpdate();
	cn.commit();
	cn.setAutoCommit(true);
}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
catch(SQLException sqle){
	try{cn.rollback();}catch(SQLException s){}
		setMensaje("Error SQL intentando ELIMINAR Perfil. Posible problema: ");
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
*	Funcinn: Asignar el bean Perifl con datos de la base de datos<br>
*	@param	String perf
*	@return	Perfil
**/
public Perfil asignarPerfil(String perf){
	Perfil p=null;
	ResultSet rs=null;
	int posicion=1;
	try{
		cn=cursor.getConnection();
		pst=cn.prepareStatement(rbPerfil.getString("PerfilAsignar"));
		pst.clearParameters();
		if(perf.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
		else pst.setString(posicion++,perf.trim());
		rs = pst.executeQuery();
		if(rs.next()){
			p=new Perfil();
			int i=1;
			p.setEstado("1");
			p.setPerfcodigo(rs.getString(i++));
			p.setPerfnombre(rs.getString(i++));
			p.setPerfabreviatura(rs.getString(i++));
			p.setPerfdescripcion(rs.getString(i++));
			p.setPerfnivel(rs.getString(i++));
		}
	}
	catch(InternalErrorException in){ setMensaje("No se puede estabecer conexinn con la base de datos: ");	return null;}
	catch(SQLException sqle){
 		setMensaje("Error intentando asignar información de Perfil. Posible problema: ");
		switch(sqle.getErrorCode()){
			default:
				setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
		}
		return p;
	}finally{
		try{
		OperacionesGenerales.closeResultSet(rs);
		OperacionesGenerales.closeStatement(pst);
		OperacionesGenerales.closeConnection(cn);
		cursor.cerrar();
		}catch(InternalErrorException inte){}
	}
	return p;
}

/**
*	Funcinn: Actualiza información del Perfil en la base de datos <br>
*	@param	Perfil p
*	@return	boolean
**/
public boolean actualizar(Perfil p){
			//System.out.println("!! actualizar perfil ");
			int posicion=1; //posicion inicial de llenado del preparedstatement
			try{
				cn=cursor.getConnection(); cn.setAutoCommit(false);
				pst=cn.prepareStatement(rbPerfil.getString("PerfilActualizar"));
				pst.clearParameters();
				if(p.getPerfnombre().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
				else pst.setString(posicion++,p.getPerfnombre().trim());
				if(p.getPerfabreviatura().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
				else pst.setString(posicion++,p.getPerfabreviatura().trim());
				if(p.getPerfdescripcion().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
				else pst.setString(posicion++,p.getPerfdescripcion().trim());
				if(p.getPerfnivel().equals("")) pst.setNull(posicion++,java.sql.Types.NUMERIC);
				else pst.setString(posicion++,p.getPerfnivel().trim());
				if(p.getPerfcodigo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
				else pst.setString(posicion++,p.getPerfcodigo().trim());
				System.out.println("NIVEL: "+p.getPerfnivel());
				pst.executeUpdate();
				cn.commit();
				cn.setAutoCommit(true);
			}catch(InternalErrorException in){in.printStackTrace(); setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
			catch(SQLException sqle){sqle.printStackTrace();
				try{cn.rollback();}catch(SQLException s){}
		 		setMensaje("Error intentando actualizar información de perfil.("+sqle.getErrorCode()+") Posible problema: ");
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

/**
*	Funcinn: Inserta información del perfil en la base de datos <br>
*	@param	Perfil p
*	@return	boolean
**/
public boolean insertar(Perfil s){
	int posicion=1; //posicion inicial de llenado del preparedstatement
	try{
		cn=cursor.getConnection(); cn.setAutoCommit(false);
		pst=cn.prepareStatement(rbPerfil.getString("PerfilInsertar"));
		pst.clearParameters();
		if(s.getPerfcodigo().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
		else pst.setString(posicion++,s.getPerfcodigo().trim());
		if(s.getPerfnombre().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
		else pst.setString(posicion++,s.getPerfnombre().trim());			
		if(s.getPerfdescripcion().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
		else pst.setString(posicion++,s.getPerfdescripcion().trim());			
		if(s.getPerfabreviatura().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
		else pst.setString(posicion++,s.getPerfabreviatura().trim());
		if(s.getPerfnivel().equals("")) pst.setNull(posicion++,java.sql.Types.NUMERIC);
		else pst.setString(posicion++,s.getPerfnivel().trim());
		pst.executeUpdate();
		cn.commit();
		cn.setAutoCommit(true);
	}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
	catch(SQLException sqle){
		try{cn.rollback();}catch(SQLException se){}
 		setMensaje("Error SQL intentando ingresar información de Perfil. Posible problema: ");
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
		*	Funcinn: Compara beans de Perfil <br>
		*	@param	Perfil c
		* @param	Perfil c2
		*	@return boolean	
		**/
		public boolean compararBeans(Perfil c, Perfil c2){
			if(!c.getPerfabreviatura().equals(c2.getPerfabreviatura())) return false;
			if(!c.getPerfcodigo().equals(c2.getPerfcodigo())) return false;
			if(!c.getPerfdescripcion().equals(c2.getPerfdescripcion())) return false;
			if(!c.getPerfnombre().equals(c2.getPerfnombre())) return false;
			if(!c.getPerfnivel().equals(c2.getPerfnivel())) return false;
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