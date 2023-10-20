/**
 * 
 */
package articulacion.encuesta.vo;

/**
 * 18/11/2007 
 * @author Latined
 * @version 1.2
 */
public class AsignaturaVO {
	private long codigo;
	private String nombre;
	private long intensidad;
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
	 * @return Return the intensidad.
	 */
	public long getIntensidad() {
		return intensidad;
	}
	/**
	 * @param intensidad The intensidad to set.
	 */
	public void setIntensidad(long intensidad) {
		this.intensidad = intensidad;
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
}
