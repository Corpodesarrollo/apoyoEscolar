package siges.boletines.beans;

import java.io.*;

/**
*	Nombre: FiltroBeanCertificados.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Certificado<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del certificado a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanCertificados implements Serializable, Cloneable{

   private String institucion;
   private String instituciondane;
   private String nominstitucion;
   private String sede;
   private String nomsede;
   private String jornada;
   private String metodologia;
   private String grado;
   private String grupo;
   private String ano;
   private String id;   
   private String orden;
   private String usuarioid;
   private String nombrecertificado;
   private String nombrecertificadopre;
   private String nombrecertificadozip;
   private String ano_actual;
   
   
   
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
  * asigna el campo instituciondane
  *	@param String s
  */
 	public void setInstituciondane(String s){
 		this.instituciondane=s;
 	}	
 	
 /**
  * retorna el campo instituciondane
  *	@return String
  */
 	public String getInstituciondane(){
 		return instituciondane !=null ? instituciondane : "";
 	}
 	
 	
 /**
 * asigna el campo nominstitucion
 *	@param String s
 */
	public void setNominstitucion(String s){
		this.nominstitucion=s;
	}	

/**
 * retorna el campo nominstitucion
 *	@return String
 */
	public String getNominstitucion(){
		return nominstitucion !=null ? nominstitucion : "";
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
 * asigna el campo nomsede
 *	@param String s
 */
	public void setNomsede(String s){
		this.nomsede=s;
	}	

/**
 * retorna el campo nomsede 
 *	@return String
 */
	public String getNomsede(){
		return nomsede !=null ? nomsede : "";
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
 * asigna el campo id
 *	@param String s
 */
	public void setId(String s){
		this.id=s;
	}	

/**
 * retorna el campo id
 *	@return String
 */
	public String getId(){
		return id !=null ? id.trim() : "";
	}

	
/**
 * asigna el campo ano
 *	@param String s
 */
	public void setAno(String s){
		this.ano=s;
	}	

/**
 * retorna el campo ano
 *	@return String
 */
	public String getAno(){
		return ano !=null ? ano : "";
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
 * asigna el campo nombrecertificado
 *	@param String s
 */
	public void setNombrecertificado(String s){
		this.nombrecertificado=s;
	}	

/**
 * retorna el campo nombrecertificado 
 *	@return String
 */
	public String getNombrecertificado(){
		return nombrecertificado !=null ? nombrecertificado : "";
	}
			

/**
 * asigna el campo nombrecertificadopre
 *	@param String s
*/
	public void setNombrecertificadopre(String s){
	   this.nombrecertificadopre=s;
	}	

/**
 * retorna el campo nombrecertificadopre 
 *	@return String
*/
	public String getNombrecertificadopre(){
	   return nombrecertificadopre !=null ? nombrecertificadopre : "";
	}
	
/**
 * asigna el campo nombrecertificadozip
 *	@param String s
*/
	public void setNombrecertificadozip(String s){
	   this.nombrecertificadozip=s;
	}	
		
/**
 * retorna el campo nombrecertificadozip 
 *	@return String
*/
	public String getNombrecertificadozip(){
	   return nombrecertificadozip !=null ? nombrecertificadozip : "";
	}
	

/**
 * asigna el campo ano_actual
 *	@param String s
*/
	public void setAno_actual(String s){
	   this.ano_actual=s;
	}	
			
/**
 * retorna el campo ano_actual
 *	@return String
*/
	public String getAno_actual(){
	   return ano_actual !=null ? ano_actual : "";
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