package siges.institucion.beans;

public class Transporte implements Cloneable{
  private String tracodjerar;
  private String tracodruta;
  private String tranombre;
  private String traconductor;
  private String tracelular;
  private String tracupos;
  private String traplaca;
  private String traciudad;
  private String tradescripcion;
  private String tracostocompleto;
  private String tracostomedia;
  private String estado;

/**
 * asigna el campo tracostomedia
 *	@param String s
 */
	public void setTracostomedia(String s){
		this.tracostomedia=s;
	}
/**
 * retorna el campo tracostomedia
 *	@return String
 */
	public String getTracostomedia(){
		return tracostomedia !=null ? tracostomedia : "";
	}
/**
 * asigna el campo tracostocompleto
 *	@param String s
 */
	public void setTracostocompleto(String s){
		this.tracostocompleto=s;
	}
/**
 * retorna el campo tracostocompleto
 *	@return String
 */
	public String getTracostocompleto(){
		return tracostocompleto !=null ? tracostocompleto : "";
	}
/**
 * asigna el campo tradescripcion
 *	@param String s
 */
	public void setTradescripcion(String s){
		this.tradescripcion=s;
	}
/**
 * retorna el campo tradescripcion
 *	@return String
 */
	public String getTradescripcion(){
		return tradescripcion !=null ? tradescripcion : "";
	}
/**
 * asigna el campo traplaca
 *	@param String s
 */
	public void setTraplaca(String s){
		this.traplaca=s;
	}
/**
 * retorna el campo traplaca
 *	@return String
 */
	public String getTraplaca(){
		return traplaca !=null ? traplaca : "";
	}
/**
 * asigna el campo traciudad
 *	@param String s
 */
	public void setTraciudad(String s){
		this.traciudad=s;
	}
/**
 * retorna el campo traciudad
 *	@return String
 */
	public String getTraciudad(){
		return traciudad !=null ? traciudad : "";
	}
/**
 * asigna el campo tracupos
 *	@param String s
 */
	public void setTracupos(String s){
		this.tracupos=s;
	}
/**
 * retorna el campo tracupos
 *	@return String
 */
	public String getTracupos(){
		return tracupos !=null ? tracupos : "";
	}
/**
 * asigna el campo tracelular
 *	@param String s
 */
	public void setTracelular(String s){
		this.tracelular=s;
	}
/**
 * retorna el campo tracelular
 *	@return String
 */
	public String getTracelular(){
		return tracelular !=null ? tracelular : "";
	}
/**
 * asigna el campo traconductor
 *	@param String s
 */
	public void setTraconductor(String s){
		this.traconductor=s;
	}
/**
 * retorna el campo traconductor
 *	@return String
 */
	public String getTraconductor(){
		return traconductor !=null ? traconductor : "";
	}
/**
 * asigna el campo tranombre
 *	@param String s
 */
	public void setTranombre(String s){
		this.tranombre=s;
	}
/**
 * retorna el campo tranombre
 *	@return String
 */
	public String getTranombre(){
		return tranombre !=null ? tranombre : "";
	}
/**
 * asigna el campo tracodruta
 *	@param String s
 */
	public void setTracodruta(String s){
		this.tracodruta=s;
	}
/**
 * retorna el campo tracodruta
 *	@return String
 */
	public String getTracodruta(){
		return tracodruta !=null ? tracodruta : "";
	}
/**
 * asigna el campo tracodjerar
 *	@param String s
 */
	public void setTracodjerar(String s){
		this.tracodjerar=s;
	}
/**
 * retorna el campo tracodjerar
 *	@return String
 */
	public String getTracodjerar(){
		return tracodjerar !=null ? tracodjerar : "";
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
/////
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