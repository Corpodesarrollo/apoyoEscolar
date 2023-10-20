package articulacion.plantillaArticulacion.vo;

import siges.common.vo.Vo;

public class SedeVO extends Vo{
	private int codigo;
	private String nombre;
	
	
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

}
