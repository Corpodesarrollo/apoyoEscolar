package articulacion.encuesta.vo;

public class EspecialidadVO {

	private long codigo;
	private String nombre;
	private String ciclo;
	/**
	 * @return Returns the ciclo.
	 */
	public String getCiclo() {
		return ciclo;
	}
	/**
	 * @param ciclo The ciclo to set.
	 */
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	/**
	 * @return Returns the codigo.
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
	 * @return Returns the nombre.
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
