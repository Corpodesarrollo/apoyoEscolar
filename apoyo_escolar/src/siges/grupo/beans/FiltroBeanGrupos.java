package siges.grupo.beans;

import java.io.*;

/**
*	Nombre: FiltroBeanGrupos.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Grupo<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del grupo a asignar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	25/01/2006	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanGrupos implements Serializable, Cloneable{

   private String institucion; 
   private String sede;
   private String jornada;
   private String metodologia;
   private String grado;
   private String orden;
      
   
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
 * asigna el campo metodologia
 *	@param String s
 */
	public void setMetodologia(String s){
		this.metodologia=s;
	}	

/**
 * retorna el campo metodologia
 *	@return String
 */
	public String getMetodologia(){
	    return metodologia !=null ? metodologia : "";
	}
		
/**
 * asigna el campo 
 *	@param String s
 */
	public void setGrado(String s){
		this.grado=s;
	}	

/**
 * retorna el campo 
 *	@return String
 */
	public String getGrado(){
		return grado !=null ? grado : "";
	}


/**
 * asigna el campo 
 *	@param String s
*/
 public void setOrden(String s){
		this.orden=s;
 }	

/**
 * retorna el campo 
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