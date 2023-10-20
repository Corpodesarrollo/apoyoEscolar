package siges.estudiante.beans;

import java.io.Serializable;

/**
*	Nombre:	Convivencia <BR>
*	Descripcion:	Datos de informacion de Convivencia del egresado	<BR>
*	Funciones de la pagina:	Almacenar los datos del egresado para un nuevo registro o para editar	<BR>
*	Entidades afectadas:	Convivencia	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class Convivencia implements Serializable, Cloneable{

	private String concodigo;
	private String concodinst;
	private String contipoperso;
	private String concodperso;
	private String contipo;
	private String condescripcion;
	private String confecha;
	private String conacuerdos;
	private String conseguimiento;
	private String estado;
	private String periodo;
	private String idClase;
	private String idTipo;


	/**
	 * @return Return the periodo.
	 */
	public String getPeriodo() {
		return periodo;
	}
	/**
	 * @param periodo The periodo to set.
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
/**
 * asigna el campo concodigo
 *	@param String s
 */
	public void setConcodigo(String s){
		this.concodigo=s;
	}
/**
 * @return Return the idClase.
 */
public String getIdClase() {
	return idClase;
}
/**
 * @param idClase The idClase to set.
 */
public void setIdClase(String idClase) {
	this.idClase = idClase;
}
/**
 * @return Return the idTipo.
 */
public String getIdTipo() {
	return idTipo;
}
/**
 * @param idTipo The idTipo to set.
 */
public void setIdTipo(String idTipo) {
	this.idTipo = idTipo;
}
/**
 * retorna el campo concodigo
 *	@return String
 */
	public String getConcodigo(){
		return concodigo !=null ? concodigo : "";
	}

/**
 * asigna el campo concodinst
 *	@param String s
 */
	public void setConcodinst(String s){
		this.concodinst=s;
	}
/**
 * retorna el campo concodinst
 *	@return String
 */
	public String getConcodinst(){
		return concodinst !=null ? concodinst : "";
	}

/**
 * asigna el campo contipoperso
 *	@param String s
 */
	public void setContipoperso(String s){
		this.contipoperso=s;
	}
/**
 * retorna el campo contipoperso
 *	@return String
 */
	public String getContipoperso(){
		return contipoperso !=null ? contipoperso : "";
	}

/**
 * asigna el campo concodperso
 *	@param String s
 */
	public void setConcodperso(String s){
		this.concodperso=s;
	}
/**
 * retorna el campo concodperso
 *	@return String
 */
	public String getConcodperso(){
		return concodperso !=null ? concodperso : "";
	}

/**
 * asigna el campo contipo
 *	@param String s
 */
	public void setContipo(String s){
		this.contipo=s;
	}
/**
 * retorna el campo contipo
 *	@return String
 */
	public String getContipo(){
		return contipo !=null ? contipo : "";
	}

/**
 * asigna el campo condescripcion
 *	@param String s
 */
	public void setCondescripcion(String s){
		this.condescripcion=s;
	}
/**
 * retorna el campo condescripcion
 *	@return String
 */
	public String getCondescripcion(){
		return condescripcion !=null ? condescripcion : "";
	}

/**
 * asigna el campo Confecha
 *	@param String s
 */
	public void setConfecha(String s){
		this.confecha=s;
	}
/**
 * retorna el campo confecha
 *	@return String
 */
	public String getConfecha(){
		return confecha !=null ? confecha : "";
	}

/**
 * asigna el campo Conacuerdos
 *	@param String s
 */
	public void setConacuerdos(String s){
		this.conacuerdos=s;
	}
/**
 * retorna el campo conacuerdos
 *	@return String
 */
	public String getConacuerdos(){
		return conacuerdos !=null ? conacuerdos : "";
	}

/**
 * asigna el campo conseguimiento
 *	@param String s
 */
	public void setConseguimiento(String s){
		this.conseguimiento=s;
	}
/**
 * retorna el campo conseguimiento
 *	@return String
 */
	public String getConseguimiento(){
		return conseguimiento !=null ? conseguimiento : "";
	}

/**
 * asigna el campo estado
 *	@param String s
 */
	public void setEstado(String s){
		this.estado=s;
	}	

/**
 * retorna el campo estado
 *	@return String
 */
	public String getEstado(){
		return estado !=null ? estado : "";
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