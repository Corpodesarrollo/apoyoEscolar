package siges.ruta.vo;

import java.io.*;

/**
*	Nombre: FiltroBeanFormulario.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Reporte<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del reporte a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanGestVO implements Serializable, Cloneable{

   private String institucion; 
   private String sede;
   private String jornada;
   private String metodologia;
   private String grado;
   private String grupo;
   private String periodo;
   private String periodonom;
   private String orden;
   private String estudiante;
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
 * asigna el campo periodo
 *	@param String s
 */
	public void setPeriodo(String s){
		this.periodo=s;
	}	

/**
 * retorna el campo periodo
 *	@return String
 */
	public String getPeriodo(){
		return periodo !=null ? periodo : "";
	}
	

/**
 * asigna el campo periodonom
 *	@param String s
 */
	public void setPeriodonom(String s){
		this.periodonom=s;
	}	

/**
 * retorna el campo periodonom
 *	@return String
 */
	public String getPeriodonom(){
		return periodonom !=null ? periodonom : "";
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
 * asigna el campo estudiante
 *	@param String s
 */
	public void setEstudiante(String s){
		this.estudiante=s;
	}	

/**
 * retorna el campo estudiante
 *	@return String
 */
	public String getEstudiante(){
		return estudiante !=null ? estudiante : "";
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