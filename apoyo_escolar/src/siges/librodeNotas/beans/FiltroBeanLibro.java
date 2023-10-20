package siges.librodeNotas.beans;

import java.io.Serializable;

/**
*	Nombre: FiltroBeanLibro.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Libro de notas<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del libro a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanLibro implements Serializable, Cloneable{

   private String institucion;
   private String instituciondane;
   private String sede;
   private String jornada;
   private String metodologia;
   private String grado;
   private String orden;
   private String usuarioid;
   private String nombrelibro;
   private String nombrelibropre;
   private String nombrelibrozip;
   private String ano;
   private int tipoProm;
   
   private long numPer;
   private String nomPerFinal;
   private int nivelEval;
   private long filInst;
   
   private String mostrarFirmaRector;
   
   

   
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
 * asigna el campo nombreboletin
 *	@param String s
 */
	public void setNombrelibro(String s){
		this.nombrelibro=s;
	}	

/**
 * retorna el campo nombreboletin 
 *	@return String
 */
	public String getNombrelibro(){
		return nombrelibro !=null ? nombrelibro : "";
	}
		

/**
 * asigna el campo nombreboletinpre
 *	@param String s
*/
	public void setNombrelibropre(String s){
	   this.nombrelibropre=s;
	}	

/**
 * retorna el campo nombreboletinpre 
 *	@return String
*/
	public String getNombrelibropre(){
	   return nombrelibropre !=null ? nombrelibropre : "";
	}
	
/**
 * asigna el campo nombreboletinzip
 *	@param String s
*/
	public void setNombrelibrozip(String s){
	   this.nombrelibrozip=s;
	}	

/**
 * retorna el campo nombreboletinzip 
 *	@return String
*/
	public String getNombrelibrozip(){
	   return nombrelibrozip !=null ? nombrelibrozip : "";
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

/**
 * @return Returns the instituciondane.
 */
public String getInstituciondane() {
	return instituciondane;
}

/**
 * @param instituciondane The instituciondane to set.
 */
public void setInstituciondane(String instituciondane) {
	this.instituciondane = instituciondane;
}

public int getTipoProm() {
	return tipoProm;
}

public void setTipoProm(int tipoProm) {
	this.tipoProm = tipoProm;
}

public long getNumPer() {
	return numPer;
}

public void setNumPer(long numPer) {
	this.numPer = numPer;
}

public String getNomPerFinal() {
	return nomPerFinal;
}

public void setNomPerFinal(String nomPerFinal) {
	this.nomPerFinal = nomPerFinal;
}

public int getNivelEval() {
	return nivelEval;
}

public void setNivelEval(int nivelEval) {
	this.nivelEval = nivelEval;
}

public long getFilInst() {
	return filInst;
}

public void setFilInst(long filInst) {
	this.filInst = filInst;
}

public String getInstitucion() {
	return institucion;
}

public String getMostrarFirmaRector() {
	return mostrarFirmaRector;
}

public void setMostrarFirmaRector(String mostrarFirmaRector) {
	this.mostrarFirmaRector = mostrarFirmaRector;
}	

}