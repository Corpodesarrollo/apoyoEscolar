package siges.institucion.beans;

/**
*	Nombre:	
*	Descripcion:	
*	Parametro de entrada:	
*	Parametro de salida:	
*	Funciones de la pagina:	
*	Entidades afectadas:	
*	Fecha de modificacinn:	
*	@author 
*	@version 
*/

public class FiltroInstitucion implements Cloneable{
   private String municipio;//1
   private String nucleo;//1
   private String institucion;//1
   private String dane;//1
   private String nombre;//1
   private String orden;//1

/**
 * asigna el campo municipio
 *	@param String s
 */
	public void setMunicipio(String s){
		this.municipio=s;
	}	

/**
 * retorna el campo municipio
 *	@return String
 */
	public String getMunicipio(){
		return municipio !=null ? municipio : "";
	}

/**
 * asigna el campo nucleo 
 *	@param String s
 */
	public void setNucleo(String s){
		this.nucleo=s;
	}	

/**
 * retorna el campo sede 
 *	@return String
 */
	public String getNucleo(){
		return nucleo !=null ? nucleo : "";
	}
/**
 * asigna el campo institucion
 *	@param String s
 */
	public void setInstitucion(String s){
		this.institucion=s;
	}	

/**
 * retorna el campo institucion
 *	@return String
 */
	public String getInstitucion(){
		return institucion !=null ? institucion : "";
	}

/**
 * asigna el campo dane
 *	@param String s
 */
	public void setDane(String s){
		this.dane=s;
	}	

/**
 * retorna el campo dane
 *	@return String
 */
	public String getDane(){
		return dane !=null ? dane : "";
	}
/**
 * asigna el campo nombre
 *	@param String s
 */
	public void setNombre(String s){
		this.nombre=s;
	}	

/**
 * retorna el campo nombre
 *	@return String
 */
	public String getNombre(){
		return nombre !=null ? nombre : "";
	}

/**
 * asigna el campo orden
 *	@param String s
 */
	public void setOrden(String s){
		this.orden=s;
	}

/**
 * retorna el campo orden
 *	@return String
 */
	public String getOrden(){
		return orden !=null ? orden : "";
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