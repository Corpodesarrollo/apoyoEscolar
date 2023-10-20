package siges.institucion.beans;

import java.io.*;

public class Jornada implements Serializable, Cloneable{  
  private String jorcodins;
  private String [] jorcodigo;
  private String estado;

/**
 * asigna el campo jorcodins
 *	@param String s
 */
	public void setJorcodins(String s){
		this.jorcodins=s;
	}	

/**
 * retorna el campo jorcodins
 *	@return String
 */
	public String getJorcodins(){
		return jorcodins !=null ? jorcodins : "";
	}
/////
/**
 * asigna el campo jorcodigo
 *	@param String s
 */
	public void setJorcodigo(String []s){
		this.jorcodigo=s;
	}	

/**
 * retorna el campo jorcodigo
 *	@return String
 */
	public String[] getJorcodigo(){
		return jorcodigo;
	}
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
	if(jorcodigo!=null){
		for(int i=0;i<jorcodigo.length;++i){
			if(jorcodigo[i].equals(s))
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