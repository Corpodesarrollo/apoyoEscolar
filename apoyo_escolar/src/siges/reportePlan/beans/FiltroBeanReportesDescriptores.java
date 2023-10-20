package siges.reportePlan.beans;

import java.io.*;

/**
*	Nombre: FiltroBeanLibro.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Libro de notas<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del libro a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanReportesDescriptores implements Serializable, Cloneable{

    private String institucion;
    private String filvigencia;
    private String sede;
    private String sedenom;
    private String jornada;
    private String jornadanom;
    private String metodologia;
    private String grado;
    private String area;
    private String periodoinicial;
    private String periodofinal;
    private String orden;
    private String usuarioid;
    private String dane;
    
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
  * asigna el campo sedenom
  *	@param String s
  */
 	public void setSedenom(String s){
 		this.sedenom=s;
 	}	

 /**
  * retorna el campo sedenom 
  *	@return String
  */
 	public String getSedenom(){	
 		return sedenom !=null ? sedenom : "";
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
  * asigna el campo jornadanom
  *	@param String s
  */
 	public void setJornadanom(String s){
 		this.jornadanom=s;
 	}	

 /**
  * retorna el campo jornadanom
  *	@return String
  */
 	public String getJornadanom(){
 		return jornadanom !=null ? jornadanom : "";
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
 	public void setArea(String s){
 	   this.area=s;
 	}	

 /**
  * retorna el campo  
  *	@return String
 */
 	public String getArea(){
 	   return area !=null ? area : "";
 	}
 	

 /**
  * asigna el campo 
  *	@param String s
 */
 	public void setPeriodoinicial(String s){
 	   this.periodoinicial=s;
 	}	

 /**
 * retorna el campo  
  *	@return String
 */
 	public String getPeriodoinicial(){
 	   return periodoinicial !=null ? periodoinicial : "";
 	}

 /**
  * asigna el campo 
  *	@param String s
 */
 	public void setPeriodofinal(String s){
 	   this.periodofinal=s;
 	}	

 /**
 * retorna el campo  
  *	@return String
 */
 	public String getPeriodofinal(){
 	   return periodofinal !=null ? periodofinal : "";
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

public String getFilvigencia() {
	return filvigencia;
}

public void setFilvigencia(String filvigencia) {
	this.filvigencia = filvigencia;
}

public String getDane() {
	return dane;
}

public void setDane(String dane) {
	this.dane = dane;
}	

}