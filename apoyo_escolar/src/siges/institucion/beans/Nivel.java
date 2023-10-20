package siges.institucion.beans;

import java.io.*;

public class Nivel implements Serializable, Cloneable{  
  private String nivcodinst;
  private String[] nivcodigo;
  private String[] nivnombre;
  private String[] nivcodmetod;
  private String[] nivorden;
  private String estado;

/**
 * asigna el campo Nivcodinst
 *	@param String s
 */
	public void setNivcodinst(String s){
		this.nivcodinst=s;
	}	

/**
 * retorna el campo Nivcodinst
 *	@return String
 */
	public String getNivcodinst(){
		return nivcodinst !=null ? nivcodinst : "";
	}
/////
/**
 * asigna el campo Nivcodigo
 *	@param String s
 */
	public void setNivcodigo(String [] s){
		this.nivcodigo=s;
	}	

/**
 * retorna el campo Nivcodigo
 *	@return String
 */
	public String[] getNivcodigo(){
		return nivcodigo;
	}
/////
/**
 * asigna el campo Nivnombre
 *	@param String s
 */
	public void setNivnombre(String[] s){
		this.nivnombre=s;
	}	

/**
 * retorna el campo Nivnombre
 *	@return String
 */
	public String[] getNivnombre(){
		return nivnombre;
	}
/////
/**
 * asigna el campo Nivcodmetod
 *	@param String s
 */
	public void setNivcodmetod(String[] s){
		this.nivcodmetod=s;
	}	

/**
 * retorna el campo Nivcodmetod
 *	@return String
 */
	public String[] getNivcodmetod(){
		return nivcodmetod;
	}
/////
/**
 * asigna el campo Nivorden
 *	@param String s
 */
	public void setNivorden(String[] s){
		this.nivorden=s;
	}	

/**
 * retorna el campo Nivorden
 *	@return String
 */
	public String[] getNivorden(){
		return nivorden;
	}
/////
/**
 * asigna el campo estado
 *	@param String s
 */
	public void setEstado(String s){
		this.estado=s;
	}	

/**
 * retorna el campo estado
 *	@return String
 */
	public String getEstado(){
		return estado !=null ? estado : "";
	}
/////

public String seleccion(String s){
	if(nivcodigo!=null){
		for(int i=0;i<nivcodigo.length;++i){
			if(nivcodigo[i].equals(s))
				return "CHECKED";
		}
	}
	return "";
}
/**
 *	Hace una copia del objeto mismo
 *	@return Object 
 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
    }	
}