package articulacion.artAusencias.vo;

import siges.common.vo.Vo;

public class MotivoVO extends Vo{

	private long codigo;
	private String nombre;
	private String clase;
	
	
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
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	
}
