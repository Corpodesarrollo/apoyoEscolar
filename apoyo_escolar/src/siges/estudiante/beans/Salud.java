package siges.estudiante.beans;

import java.io.Serializable;

/**
*	Nombre:	Salud<BR>
*	Descripcinn:	Bean de informacion de salud del estudiante	<BR>
*	Funciones de la pngina:	Almacenar la información de Salud del formulario	<BR>
*	Entidades afectadas:	Salud	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class Salud implements Serializable, Cloneable{
	private String saltipoperso;
	private String salcodperso;
	private String saltiposangre;
	private String saleps;
	private String salars;
	private String salalergias;
	private String salenfermedades;
	private String salmedicamentos;
	private String saltelemerg;
	private String salperemerg;
	private String salestado;

/**
 * asigna el campo saltipoperso
 *	@param String s
 */
	public void setSaltipoperso(String s){
		this.saltipoperso=s;
	}
/**
 * retorna el campo saltipoperso
 *	@return String
 */
	public String getSaltipoperso(){
		return saltipoperso !=null ? saltipoperso : "";
	}

/**
 * asigna el campo salcodperso
 *	@param String s
 */
	public void setSalcodperso(String s){
		this.salcodperso=s;
	}
/**
 * retorna el campo salcodperso
 *	@return String
 */
	public String getSalcodperso(){
		return salcodperso !=null ? salcodperso : "";
	}

/**
 * asigna el campo Saltiposangre
 *	@param String s
 */
	public void setSaltiposangre(String s){
		this.saltiposangre=s;
	}
/**
 * retorna el campo saltiposangre
 *	@return String
 */
	public String getSaltiposangre(){
		return saltiposangre !=null ? saltiposangre : "";
	}

/**
 * asigna el campo Saleps
 *	@param String s
 */
	public void setSaleps(String s){
		this.saleps=s;
	}
/**
 * retorna el campo saleps
 *	@return String
 */
	public String getSaleps(){
		return saleps  !=null ? saleps : "";
	}

/**
 * asigna el campo Salars
 *	@param String s
 */
	public void setSalars(String s){
		this.salars=s;
	}
/**
 * retorna el campo salars
 *	@return String
 */
	public String getSalars(){
		return salars !=null ? salars : "";
	}

/**
 * asigna el campo Salalergias
 *	@param String s
 */
	public void setSalalergias(String s){
		this.salalergias=s;
	}
/**
 * retorna el campo salalergias
 *	@return String
 */
	public String getSalalergias(){
		return salalergias !=null ? salalergias : "";
	}

/**
 * asigna el campo Salenfermedades
 *	@param String s
 */
	public void setSalenfermedades(String s){
		this.salenfermedades=s;
	}
/**
 * retorna el campo salenfermedades
 *	@return String
 */
	public String getSalenfermedades(){
		return salenfermedades!=null ? salenfermedades : "";
	}

/**
 * asigna el campo Salmedicamentos
 *	@param String s
 */
	public void setSalmedicamentos(String s){
		this.salmedicamentos=s;
	}
/**
 * retorna el campo salmedicamentos
 *	@return String
 */
	public String getSalmedicamentos(){
		return salmedicamentos !=null ? salmedicamentos : "";
	}

/**
 * asigna el campo Saltelemerg
 *	@param String s
 */
	public void setSaltelemerg(String s){
		this.saltelemerg=s;
	}
/**
 * retorna el campo saltelemerg
 *	@return String
 */
	public String getSaltelemerg(){
		return saltelemerg !=null ? saltelemerg : "";
	}

/**
 * asigna el campo Salperemerg
 *	@param String s
 */
	public void setSalperemerg(String s){
		this.salperemerg=s;
	}
/**
 * retorna el campo salperemerg
 *	@return String
 */
	public String getSalperemerg(){
		return salperemerg !=null ? salperemerg : "";
	}

/**
 * asigna el campo estado
 *	@param String s
 */
	public void setSalestado(String s){
		this.salestado=s;
	}	

/**
 * retorna el campo estado
 *	@return String
 */
	public String getSalestado(){
		return salestado !=null ? salestado : "";
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