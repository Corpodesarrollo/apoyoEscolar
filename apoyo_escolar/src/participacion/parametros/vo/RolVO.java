package participacion.parametros.vo;

import siges.common.vo.Vo;

public class RolVO extends Vo{
	private int codigo;
	private String nombre;
	private String descripcion;
	private String tipos;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipos() {
		return tipos;
	}
	public void setTipos(String tipos) {
		this.tipos = tipos;
	}

	
	
}
