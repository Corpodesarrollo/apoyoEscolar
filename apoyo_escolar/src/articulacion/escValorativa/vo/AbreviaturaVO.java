package articulacion.escValorativa.vo;

import siges.common.vo.Vo;

public class AbreviaturaVO extends Vo{

	private int codigo;
	private String nombre;
	private String abreviatura;
	private float raIni;
	private float raFin;
	
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
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public float getRaFin() {
		return raFin;
	}
	public void setRaFin(float raFin) {
		this.raFin = raFin;
	}
	public float getRaIni() {
		return raIni;
	}
	public void setRaIni(float raIni) {
		this.raIni = raIni;
	}
}