package articulacion.grupoArt.vo;

import siges.common.vo.Vo;

public class JornadaVO extends Vo{
	private int codigo;
	private String nombre;
	private int sede;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getSede() {
		return sede;
	}
	public void setSede(int sede) {
		this.sede = sede;
	}
	
	
}
