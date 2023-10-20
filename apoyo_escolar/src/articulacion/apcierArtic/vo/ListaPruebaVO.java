package articulacion.apcierArtic.vo;

import siges.common.vo.Vo;

public class ListaPruebaVO extends Vo{

	private long codigo;
	private String nombre;
	private int bimestre;
	private boolean estado;
	private String fecha;
	
	public boolean isEstado() {
		return estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
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
	public int getBimestre() {
		return bimestre;
	}
	public void setBimestre(int bimestre) {
		this.bimestre = bimestre;
	}
	
}
