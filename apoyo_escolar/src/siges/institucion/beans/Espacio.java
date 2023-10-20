package siges.institucion.beans;

import java.io.*;

public class Espacio implements Serializable, Cloneable{
  private String espcodins;
  private String espcodsede;
  private String espcodigo;
  private String esptipo;
  private String espnombre;
  private String espcapacidad;
  private String esparea;
  private String esptipoocupante;
  private String espestado;
  private String espfuncion;
  private String espdescripcion;
  private String estado;
  private String[] sedjorespcodjor;

/**
 * asigna el campo Espcodins
 *	@param String s
 */
	public void setEspcodins(String s){
		this.espcodins=s;
	}	

/**
 * retorna el campo Espcodins
 *	@return String
 */
	public String getEspcodins(){
		return espcodins !=null ? espcodins : "";
	}
/////
/**
 * asigna el campo Espcodsede
 *	@param String s
 */
	public void setEspcodsede(String s){
		this.espcodsede=s;
	}	

/**
 * retorna el campo Espcodsede
 *	@return String
 */
	public String getEspcodsede(){
		return espcodsede !=null ? espcodsede : "";
	}
/////
/**
 * asigna el campo Espcodigo
 *	@param String s
 */
	public void setEspcodigo(String s){
		this.espcodigo=s;
	}	

/**
 * retorna el campo Espcodigo
 *	@return String
 */
	public String getEspcodigo(){
		return espcodigo !=null ? espcodigo : "";
	}
/////
/**
 * asigna el campo Esptipo
 *	@param String s
 */
	public void setEsptipo(String s){
		this.esptipo=s;
	}	

/**
 * retorna el campo Esptipo
 *	@return String
 */
	public String getEsptipo(){
		return esptipo !=null ? esptipo : "";
	}
/////
/**
 * asigna el campo Espnombre
 *	@param String s
 */
	public void setEspnombre(String s){
		this.espnombre=s;
	}	

/**
 * retorna el campo Espnombre
 *	@return String
 */
	public String getEspnombre(){
		return espnombre !=null ? espnombre : "";
	}
/////
/**
 * asigna el campo Espcapacidad
 *	@param String s
 */
	public void setEspcapacidad(String s){
		this.espcapacidad=s;
	}	

/**
 * retorna el campo Espcapacidad
 *	@return String
 */
	public String getEspcapacidad(){
		return espcapacidad !=null ? espcapacidad : "";
	}
/////
/**
 * asigna el campo Esparea
 *	@param String s
 */
	public void setEsparea(String s){
		this.esparea=s;
	}	

/**
 * retorna el campo Esparea
 *	@return String
 */
	public String getEsparea(){
		return esparea !=null ? esparea : "";
	}
/////
/**
 * asigna el campo Esptipoocupante 
 *	@param String s
 */
	public void setEsptipoocupante (String s){
		this.esptipoocupante =s;
	}	

/**
 * retorna el campo Esptipoocupante 
 *	@return String
 */
	public String getEsptipoocupante (){
		return esptipoocupante  !=null ? esptipoocupante  : "";
	}
/////
/**
 * asigna el campo Espestado
 *	@param String s
 */
	public void setEspestado(String s){
		this.espestado=s;
	}	

/**
 * retorna el campo Espestado
 *	@return String
 */
	public String getEspestado(){
		return espestado !=null ? espestado : "";
	}
/////
/**
 * asigna el campo Espfuncion
 *	@param String s
 */
	public void setEspfuncion(String s){
		this.espfuncion=s;
	}	

/**
 * retorna el campo Espfuncion
 *	@return String
 */
	public String getEspfuncion(){
		return espfuncion !=null ? espfuncion : "";
	}
/////
/**
 * asigna el campo Espdescripcion
 *	@param String s
 */
	public void setEspdescripcion(String s){
		this.espdescripcion=s;
	}	

/**
 * retorna el campo Espdescripcion
 *	@return String
 */
	public String getEspdescripcion(){
		return espdescripcion !=null ? espdescripcion : "";
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

/**
 * asigna el campo sedjorespcodjor
 *	@param String s
 */
	public void setSedjorespcodjor(String[] s){
		this.sedjorespcodjor=s;
	}	

/**
 * retorna el campo sedjorespcodjor
 *	@return String
 */
	public String[] getSedjorespcodjor(){
		return sedjorespcodjor;
	}
/////

public String seleccion(String r,String s){
	if(sedjorespcodjor!=null){
		for(int i=0;i<sedjorespcodjor.length;++i){
			if(sedjorespcodjor[i].equals(s) && espcodsede!=null && espcodsede.equals(r))
				return "SELECTED";
		}
	}
	return "";
}

public String seleccion(String s){
	if(sedjorespcodjor!=null){
		for(int i=0;i<sedjorespcodjor.length;++i){
			if(sedjorespcodjor[i].equals(s))
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