package articulacion.artAusencias.vo;

import siges.common.vo.Vo;

public class DiaVO extends Vo{

	private int codigo;
	private int numDia;
	private String nombre;
	private boolean check;
	private boolean habilitar;
	
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public boolean isHabilitar() {
		return habilitar;
	}
	public void setHabilitar(boolean habilitar) {
		this.habilitar = habilitar;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumDia() {
		return numDia;
	}
	public void setNumDia(int numDia) {
		this.numDia = numDia;
	}
	
}
