package siges.boletines.beans;

import java.io.*;

/**
*	Nombre: FiltroBeanResumenAsignaturas.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Reporte<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del reporte a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:		<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanResumenAsignaturas implements Serializable, Cloneable{

   private String institucion;
   private String institucionnom;
   private String sede;
   private String sedenom;
   private String jornada;
   private String jornadanom;
   private String metodologia;
   private String metodologianom;
   private String grado;
   private String gradonom;
   private String grupo;
   private String gruponom;
   private String periodo;
   private String periodonom;
   private String orden;
   private String usuarioid;
   private String nombrereporte;
   private String nombrereportexls;
   private String nombrereportezip;
   private String formato;
   private String areassel;
   private String asigsel;
   
   

   /**
    * asigna el campo areassel
    *	@param String s
    */
   	public void setareassel(String s){
   		this.areassel=s;
   	}	

  	/**
    * retorna el campo areassel 
    *	@return String
    */
   	public String getareassel(){
   		return areassel !=null ? areassel : "";
   	}
   	
   	/**
     * asigna el campo asigsel
     *	@param String s
     */
    	public void setasigsel(String s){
    		this.asigsel=s;
    	}	

   	/**
     * retorna el campo asigsel 
     *	@return String
     */
    	public String getasigsel(){
    		return asigsel !=null ? asigsel : "";
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
 	public String getInsitucion(){
 		return institucion !=null ? institucion : "";
 	}
   
 /**
  * asigna el campo institucionnom
  *	@param String s
  */
 	public void setInstitucionnom(String s){
 		this.institucionnom=s;
 	}	

	/**
  * retorna el campo institucionnom 
  *	@return String
  */
 	public String getInstitucionnom(){
 		return institucionnom !=null ? institucionnom : "";
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
 * asigna el campo 
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
 * asigna el campo metodologianom
 *	@param String s
 */
	public void setMetodologianom(String s){
		this.metodologianom=s;
	}	

/**
 * retorna el campo metodologianom
 *	@return String
 */
	public String getMetodologianom(){
	    return metodologianom !=null ? metodologianom : "";
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
 * asigna el campo gradonom
 *	@param String s
 */
	public void setGradonom(String s){
		this.gradonom=s;
	}	

/**
 * retorna el campo gradonom 
 *	@return String
 */
	public String getGradonom(){
		return gradonom !=null ? gradonom : "";
	}
	
/**
 * asigna el campo grupo
 *	@param String s
 */
	public void setGrupo(String s){
		this.grupo=s;
	}	

/**
 * retorna el campo grupo
 *	@return String
 */
	public String getGrupo(){
		return grupo !=null ? grupo : "";
	}

/**
 * asigna el campo gruponom
 *	@param String s
 */
	public void setGruponom(String s){
		this.gruponom=s;
	}	

/**
 * retorna el campo gruponom
 *	@return String
 */
	public String getGruponom(){
		return gruponom !=null ? gruponom : "";
	}
	
	
/**
 * asigna el campo grupo
 *	@param String s
 */
	public void setPeriodo(String s){
		this.periodo=s;
	}	

/**
 * retorna el campo grupo
 *	@return String
 */
	public String getPeriodo(){
		return periodo !=null ? periodo : "";
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
 * asigna el campo periodo
 *	@param String s
*/
	public void setPeriodonom(String s){
		this.periodonom=s;
	}	

/**
 * retorna el campo periodo
 *	@return String
*/
	public String getPeriodonom(){
		return periodonom !=null ? periodonom : "";
	}


/**
 * asigna el campo nombrereporte
 *	@param String s
 */
	public void setNombrereporte(String s){
		this.nombrereporte=s;
	}	

/**
 * retorna el campo nombrereporte 
 *	@return String
 */
	public String getNombrereporte(){
		return nombrereporte !=null ? nombrereporte : "";
	}
		
/**
 * asigna el campo nombrereportexls
 *	@param String s
 */
	public void setNombrereportexls(String s){
		this.nombrereportexls=s;
	}
	
/**
 * retorna el campo nombrereportexls 
 *	@return String
 */
	public String getNombrereportexls(){
		return nombrereportexls !=null ? nombrereportexls : "";
	}
	
/**
 * asigna el campo nombrereportezip
 *	@param String s
*/
	public void setNombrereportezip(String s){
	   this.nombrereportezip=s;
	}	

/**
 * retorna el campo nombrereportezip 
 *	@return String
*/
	public String getNombrereportezip(){
	   return nombrereportezip !=null ? nombrereportezip : "";
	}
	

/**
 * asigna el campo formato
 *	@param String s
*/
	public void setFormato(String s){
	   this.formato=s;
	}	

/**
 * retorna el campo formato 
 *	@return String
*/
	public String getFormato(){
	   return formato !=null ? formato : "";
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