package siges.grupoPeriodo.beans;

/**
*	Nombre:	AbrirGrupo<BR>
*	Descripcinn:	Bean de información de la tabla 'cierre grupo y periodo'	<BR>
*	Funciones de la pngina:	almacenar informacion de cierre de grupos y periodo	<BR>
*	Entidades afectadas:	Cierre_grupo y Periodo	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class AbrirGrupo implements Cloneable{
   private String sede;//1
   private String jornada;//2
   private String grado;//3
   private String grupo;//4
   private String periodo;//4
   private String asignatura;//4
   private String metodologia;//4
   private String area;//4
   private String tipoapertura;//4

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
 * asigna el campo Periodo
 *	@param String s
 */
	public void setPeriodo(String s){
		this.periodo=s;
	}	

/**
 * retorna el campo Periodo
 *	@return String
 */
	public String getPeriodo(){
		return periodo !=null ? periodo : "";
	}

/**
 * asigna el campo asignatura
 *	@param String s
 */
	public void setAsignatura(String s){
		this.asignatura=s;
	}	

/**
 * retorna el campo asignatura
 *	@return String
 */
	public String getAsignatura(){
		return asignatura !=null ? asignatura : "";
	}

/**
 * asigna el campo area
 *	@param String s
 */
	public void setArea(String s){
		this.area=s;
	}	

/**
 * retorna el campo area
 *	@return String
 */
	public String getArea(){
		return area !=null ? area : "";
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
 * @return Returns the metodologia.
 */
public String getMetodologia() {
	return metodologia!=null?metodologia:"";
}
/**
 * @param metodologia The metodologia to set.
 */
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
/**
 * @return Returns the tipoapertura.
 */
public String getTipoapertura() {
	return tipoapertura!=null?tipoapertura:"";
}
/**
 * @param tipoapertura The tipoapertura to set.
 */
public void setTipoapertura(String tipoapertura) {
	this.tipoapertura = tipoapertura;
}
}