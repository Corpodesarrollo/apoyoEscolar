package articulacion.artAusencias.vo;

import siges.common.vo.Vo;

public class AsignaturaVO extends Vo{

	private long codigo;
	private long clase;
	private String nombre;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getClase() {
		return clase;
	}
	public void setClase(long clase) {
		this.clase = clase;
	}
	
}
