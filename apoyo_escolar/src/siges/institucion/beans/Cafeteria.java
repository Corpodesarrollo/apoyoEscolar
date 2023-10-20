package siges.institucion.beans;

public class Cafeteria implements Cloneable{
	private String rescodjerar;
  private String restiposerv;
  private String resdescripcion;
  private String reshorario;
  private String rescosto;
  private String estado;

/**
 * asigna el campo rescodjerar
 *	@param String s
 */
	public void setRescodjerar(String s){
		this.rescodjerar=s;
	}	

/**
 * retorna el campo rescodjerar
 *	@return String
 */
	public String getRescodjerar(){
		return rescodjerar !=null ? rescodjerar : "";
	}
/**
 * asigna el campo resTipoServ
 *	@param String s
 */
	public void setRestiposerv(String s){
		this.restiposerv=s;
	}	

/**
 * retorna el campo resTipoServ
 *	@return String
 */
	public String getRestiposerv(){
		return restiposerv !=null ? restiposerv : "";
	}
/**
 * asigna el campo resdescripcion
 *	@param String s
 */
	public void setResdescripcion(String s){
		this.resdescripcion=s;
	}	

/**
 * retorna el campo gobCodJerar
 *	@return String
 */
	public String getResdescripcion(){
		return resdescripcion !=null ? resdescripcion : "";
	}
/**
 * asigna el campo reshorario
 *	@param String s
 */
	public void setReshorario(String s){
		this.reshorario=s;
	}	

/**
 * retorna el campo reshorario
 *	@return String
 */
	public String getReshorario(){
		return reshorario !=null ? reshorario : "";
	}
/**
 * asigna el campo rescosto
 *	@param String s
 */
	public void setRescosto(String s){
		this.rescosto=s;
	}	

/**
 * retorna el campo rescosto
 *	@return String
 */
	public String getRescosto(){
		return rescosto !=null ? rescosto : "";
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