/**
 * 
 */
package articulacion.artPlantillaFinal.vo;

/**
 * 10/12/2007 
 * @author Latined
 * @version 1.2
 */
public class AsignaturaVO {
	private long asiCodigo; 
	private String asiNombre; 
	private String asiAbreviatura;
	
	/**
	 * @return Return the asiCodigo.
	 */
	public long getAsiCodigo() {
		return asiCodigo;
	}
	/**
	 * @param asiCodigo The asiCodigo to set.
	 */
	public void setAsiCodigo(long asiCodigo) {
		this.asiCodigo = asiCodigo;
	}
	/**
	 * @return Return the asiAbreviatura.
	 */
	public String getAsiAbreviatura() {
		return asiAbreviatura;
	}
	/**
	 * @param asiAbreviatura The asiAbreviatura to set.
	 */
	public void setAsiAbreviatura(String asiAbreviatura) {
		this.asiAbreviatura = asiAbreviatura;
	}
	/**
	 * @return Return the asiNombre.
	 */
	public String getAsiNombre() {
		return asiNombre;
	}
	/**
	 * @param asiNombre The asiNombre to set.
	 */
	public void setAsiNombre(String asiNombre) {
		this.asiNombre = asiNombre;
	}
}
