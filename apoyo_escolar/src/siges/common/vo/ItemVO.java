/**
 * 
 */
package siges.common.vo;

/**
 * 25/08/2007 
 * @author Latined
 * @version 1.2
 */
public class ItemVO {
	private long codigo;
	private String nombre;
	private String codigo2;
	private long padre;
	private String padre2;
	
	
	public ItemVO(){
		
	}
	public ItemVO(long codigo,String nombre){
		this.codigo=codigo;
		this.nombre=nombre;
	}
	public ItemVO(long codigo,String nombre,long padre){
		this.codigo=codigo;
		this.nombre=nombre;
		this.padre=padre;
	}
	/**
	 * @return Return the codigo.
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Return the codigo2.
	 */
	public String getCodigo2() {
		return codigo2;
	}
	/**
	 * @param codigo2 The codigo2 to set.
	 */
	public void setCodigo2(String codigo2) {
		this.codigo2 = codigo2;
	}
	/**
	 * @return Return the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Return the padre.
	 */
	public long getPadre() {
		return padre;
	}
	/**
	 * @param padre The padre to set.
	 */
	public void setPadre(long padre) {
		this.padre = padre;
	}
	/**
	 * @return Return the padre2.
	 */
	public String getPadre2() {
		return padre2;
	}
	/**
	 * @param padre2 The padre2 to set.
	 */
	public void setPadre2(String padre2) {
		this.padre2 = padre2;
	}
}
