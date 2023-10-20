package siges.gestionAdministrativa.enviarMensajes.vo;

import siges.common.vo.Vo;

public class ItemTableVO extends Vo {
	
	private String codigo;
	private String nombre;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
