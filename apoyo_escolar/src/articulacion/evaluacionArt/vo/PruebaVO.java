package articulacion.evaluacionArt.vo;

import siges.common.vo.Vo;

public class PruebaVO extends Vo{

	private long codigo;
	private long codigo2;
	private String nombre;
	private float porcentaje;
	private int cantidad;
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public long getCodigo2() {
		return codigo2;
	}
	public void setCodigo2(long codigo2) {
		this.codigo2 = codigo2;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	
}
