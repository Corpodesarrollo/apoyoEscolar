package siges.grupo.beans;

import java.io.*;

/**
*	Nombre: GruposBean.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Grupo<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del grupo a asignar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	25/01/2006	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class GrupoBean implements Serializable, Cloneable{
/************************************/
   private String institucionnew; 
   private String sedenew;
   private String jornadanew;
   private String metodologianew;
   private String gradonew;
   /************************************/      
   private String tipoespacionew;
   private String espacionew;
   private String coordinadornew;   
   /************************************/
   private String codigonew;
   private String nombrenew;
   private String cuponew;
   private String ordennew; 
   private String jerarquiagrupo;

   
 /**
  * asigna el campo institucion
  *	@param String s
  */
 	public void setInstitucionnew(String s){
 		this.institucionnew=s;
 	}	

	/**
  * retorna el campo institucion 
  *	@return String
  */
 	public String getInsitucionnew(){
 		return institucionnew !=null ? institucionnew : "";
 	}
   
   
/**
 * asigna el campo sede
 *	@param String s
 */
	public void setSedenew(String s){
		this.sedenew=s;
	}	

/**
 * retorna el campo sede 
 *	@return String
 */
	public String getSedenew(){
		return sedenew !=null ? sedenew : "";
	}

/**
 * asigna el campo jornada
 *	@param String s
 */
	public void setJornadanew(String s){
		this.jornadanew=s;
	}	

/**
 * retorna el campo jornada
 *	@return String
 */
	public String getJornadanew(){
		return jornadanew !=null ? jornadanew : "";
	}

/**
 * asigna el campo metodologia
 *	@param String s
 */
	public void setMetodologianew(String s){
		this.metodologianew=s;
	}	

/**
 * retorna el campo metodologia
 *	@return String
 */
	public String getMetodologianew(){
	    return metodologianew !=null ? metodologianew : "";
	}
		
/**
 * asigna el campo 
 *	@param String s
 */
	public void setGradonew(String s){
		this.gradonew=s;
	}	

/**
 * retorna el campo 
 *	@return String
 */
	public String getGradonew(){
		return gradonew !=null ? gradonew : "";
	}


/**
 * asigna el campo 
 *	@param String s
*/
 public void setTipoespacionew(String s){
		this.tipoespacionew=s;
 }	

/**
 * retorna el campo 
 *	@return String
*/
 public String getTipoespacionew(){
	 return tipoespacionew !=null? tipoespacionew : "";
}
	
/**
 * asigna el campo 
 *	@param String s
*/
  public void setEspacionew(String s){
 		this.espacionew=s;
 }	

/**
 * retorna el campo 
 *	@return String
*/
 public String getEspacionew(){
	 return espacionew !=null ? espacionew : "";
}
 
/**
 * asigna el campo 
 *	@param String s
*/
  public void setCoordinadornew(String s){
 		this.coordinadornew=s;
 }	

/**
 * retorna el campo 
 *	@return String
*/
 public String getCoordinadornew(){
	 return coordinadornew !=null ? coordinadornew : "";
}


/**
 * asigna el campo 
 *	@param String s
*/
  public void setCodigonew(String s){
 		this.codigonew=s;
 }	

/**
 * retorna el campo 
 *	@return String
*/
 public String getCodigonew(){
	 return codigonew !=null ? codigonew : "";
}
 
 
/**
 * asigna el campo 
 *	@param String s
*/
  public void setNombrenew(String s){
 		this.nombrenew=s;
 }	

/**
 * retorna el campo 
 *	@return String
*/
 public String getNombrenew(){
	 return nombrenew !=null ? nombrenew : "";
}

 
/**
 * asigna el campo 
 *	@param String s
*/
  public void setCuponew(String s){
 		this.cuponew=s;
 }	

/**
 * retorna el campo 
 *	@return String
*/
 public String getCuponew(){
   return cuponew !=null ? cuponew : "";
}
 
 
/**
 * asigna el campo 
 *	@param String s
*/
  public void setOrdennew(String s){
 		this.ordennew=s;
 }	

/**
 * retorna el campo 
 *	@return String
*/
 public String getOrdennew(){
   return ordennew !=null ? ordennew : "";
}
 

/**
 * asigna el campo 
 *	@param String s
*/
  public void setJerarquiagrupo(String s){
 		this.jerarquiagrupo=s;
 }
 
 /**
 * retorna el campo 
 *	@return String
*/
 public String getJerarquiagrupo(){
   return jerarquiagrupo !=null ? jerarquiagrupo : "";
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