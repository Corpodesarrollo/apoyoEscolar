package siges.rotacion.beans;

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

public class Horario implements Cloneable{
   private String inst;//1
   private String sede;//1
   private String jornada;//2
   private String grado;//3
   private String grupo;//4
   private String docente;//4
   private String asignatura;//4
   private String area;//4
   private String clases;//3
   private String sabado;//4
   private String domingo;//4
   private String metodologia;//4
   private String jerGrupo;//4
   private String jerGrado;//4
   private String []horas;//4
   private String cupo;//4
   private String ih;//4
   private String ihDocente;//4
   private String ihReal;//4
   private String idSolicitud;//4
   private String accion;//4
   //campos usados en rotacion 2 para mantener los datos de la peticion de rotacion2
   private String gradoRotacion;
   private String grupoRotacion;
   //campo que indica si se puede o no aprovar o rechazar el grupo
   private String editable;
   private int vigencia;
   /**
    * @return Returns the clases.
    */
   public String getClases() {
   	return clases!=null?clases:"";
   }
   /**
    * @param clases The clases to set.
    */
   public void setClases(String clases) {
   	this.clases = clases;
   }
   /**
    * @return Returns the domingo.
    */
   public String getDomingo() {
   	return domingo!=null?domingo:"0";
   }
   /**
    * @param domingo The domingo to set.
    */
   public void setDomingo(String domingo) {
   	this.domingo = domingo;
   }

   /**
    * @return Returns the sabado.
    */
   public String getSabado() {
   	return sabado!=null?sabado:"0";
   }
   /**
    * @param sabado The sabado to set.
    */
   public void setSabado(String sabado) {
   	this.sabado = sabado;
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
	public void setDocente(String s){
		this.docente=s;
	}	

/**
 * retorna el campo Periodo
 *	@return String
 */
	public String getDocente(){
		return docente !=null ? docente : "";
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
 * @return Devuelve inst.
 */
public String getInst() {
    return inst!=null?inst:"";
}
/**
 * @param inst El inst a establecer.
 */
public void setInst(String inst) {
    this.inst = inst;
}
/**
 * @return Devuelve metodologia.
 */
public String getMetodologia() {
    return metodologia!=null?metodologia:"";
}
/**
 * @param metodologia El metodologia a establecer.
 */
public void setMetodologia(String metodologia) {
    this.metodologia = metodologia;
}
/**
 * @return Returns the jerGrupo.
 */
public String getJerGrupo() {
	return jerGrupo!=null?jerGrupo:"";
}
/**
 * @param jerGrupo The jerGrupo to set.
 */
public void setJerGrupo(String jerGrupo) {
	this.jerGrupo = jerGrupo;
}
/**
 * @return Returns the notas.
 */
public String[] getHoras() {
	return horas;
}
/**
 * @param notas The notas to set.
 */
public void setHoras(String[] notas) {
	this.horas = notas;
}
public String getCupo() {
    return cupo!=null?cupo:"";
}
public void setCupo(String cupo) {
    this.cupo = cupo;
}
public String getIh() {
    return ih!=null?ih:"";
}
public void setIh(String ih) {
    this.ih = ih;
}
public String getIhDocente() {
    return ihDocente!=null?ihDocente:"";
}

public void setIhDocente(String ihDocente) {
    this.ihDocente = ihDocente;
}

public String getIhReal() {
    return ihReal!=null?ihReal:"";
}
public void setIhReal(String ihReal) {
    this.ihReal = ihReal;
}
/**
 * @return Returns the idSolicitud.
 */
public String getIdSolicitud() {
	return idSolicitud!=null?idSolicitud:"";
}
/**
 * @param idSolicitud The idSolicitud to set.
 */
public void setIdSolicitud(String idSolicitud) {
	this.idSolicitud = idSolicitud;
}

public String getAccion() {
    return accion!=null?accion:"";
}

public void setAccion(String accion) {
    this.accion = accion;
}
public String getGradoRotacion() {
    return gradoRotacion!=null?gradoRotacion:"";
}
public void setGradoRotacion(String gradoRotacion) {
    this.gradoRotacion = gradoRotacion;
}
public String getGrupoRotacion() {
    return grupoRotacion!=null?grupoRotacion:"";
}
public void setGrupoRotacion(String grupoRotacion) {
    this.grupoRotacion = grupoRotacion;
}

public String getEditable() {
    return editable!=null?editable:"";
}

public void setEditable(String editable) {
    this.editable = editable;
}
/**
 * @return Return the jerGrado.
 */
public String getJerGrado() {
	return jerGrado!=null?jerGrado:"";
}
/**
 * @param jerGrado The jerGrado to set.
 */
public void setJerGrado(String jerGrado) {
	this.jerGrado = jerGrado;
}
/**
 * @return Return the vigencia.
 */
public final int getVigencia() {
	return vigencia;
}
/**
 * @param vigencia The vigencia to set.
 */
public final void setVigencia(int vigencia) {
	this.vigencia = vigencia;
}

}