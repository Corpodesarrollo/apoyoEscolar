package siges.personal.beans;

import java.io.*;

/**
*	Nombre: FiltroBeanForm.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Reporte<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del reporte a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanForm implements Serializable, Cloneable{

   private String institucion; 
   private String sede;
   private String jornada;
   private String personal;
   private String orden;
   private String usuarioid;
   private String nombrereporte;
   private String nombrereportezip;
   
   
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
 	public String getInsitucion(){
 		return institucion !=null ? institucion : "";
 	}
   
   
/**
 * asigna el campo sede
 *	@param String s
 */
	public void setSede(String s){
		this.sede=s;
	}	

/**
 * retorna el campo sede 
 *	@return String
 */
	public String getSede(){
		return sede !=null ? sede : "";
	}

/**
 * asigna el campo jornada
 *	@param String s
 */
	public void setJornada(String s){
		this.jornada=s;
	}	

/**
 * retorna el campo jornada
 *	@return String
 */
	public String getJornada(){
		return jornada !=null ? jornada : "";
	}

/**
 * asigna el campo personal
 *	@param String s
 */
	public void setPersonal(String s){
		this.personal=s;
	}	

/**
 * retorna el campo personal
 *	@return String
 */
	public String getPersonal(){
		return personal !=null ? personal : "";
	}
	
/**
 * asigna el campo usuarioid
 *	@param String s
 */
	public void setUsuarioid(String s){
		this.usuarioid=s;
	}	
	/**
 * retorna el campo usuarioid
 *	@return String
 */
	public String getUsuarioid(){
		return usuarioid !=null ? usuarioid.trim() : "";
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
 * asigna el campo 
 *	@param String s
 */
	public void setNombrereporte(String s){
		this.nombrereporte=s;
	}
	
/**
 * retorna el campo 
 *	@return String
 */
	public String getNombrereporte(){
		return nombrereporte !=null ? nombrereporte.trim() : "";
	}
	
	
/**
 * asigna el campo 
 *	@param String s
 */
	public void setNombrereportezip(String s){
		this.nombrereportezip=s;
	}
		
/**
 * retorna el campo 
 *	@return String
 */
	public String getNombrereportezip(){
		return nombrereportezip !=null ? nombrereportezip.trim() : "";
	}
	
	
/**
 *	Hace una copia del objeto mismo
 *	@return Object 
 */
	public Object clone(){
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
    }	

}