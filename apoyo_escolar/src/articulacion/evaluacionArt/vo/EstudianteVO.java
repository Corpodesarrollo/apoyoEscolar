package articulacion.evaluacionArt.vo;

import siges.common.vo.Vo;

public class EstudianteVO extends Vo{

	private long codigo;
	private String documento;
	private String nombre;
	private String apellido;
	private float notaSubP1;
	private float notaSubP2;
	private float notaSubP3;
	private float notaSubP4;
	private float notaSubP5;
	private float notaNum;
	private String notaCon;
	private boolean estado;
	
	public String getNotaCon() {
		return notaCon;
	}
	public void setNotaCon(String notaCon) {
		this.notaCon = notaCon;
	}
	public float getNotaNum() {
		return notaNum;
	}
	public void setNotaNum(float notaNum) {
		this.notaNum = notaNum;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public float getNotaSubP1() {
		return notaSubP1;
	}
	public void setNotaSubP1(float notaSubP1) {
		this.notaSubP1 = notaSubP1;
	}
	public float getNotaSubP2() {
		return notaSubP2;
	}
	public void setNotaSubP2(float notaSubP2) {
		this.notaSubP2 = notaSubP2;
	}
	public float getNotaSubP3() {
		return notaSubP3;
	}
	public void setNotaSubP3(float notaSubP3) {
		this.notaSubP3 = notaSubP3;
	}
	public float getNotaSubP4() {
		return notaSubP4;
	}
	public void setNotaSubP4(float notaSubP4) {
		this.notaSubP4 = notaSubP4;
	}
	public float getNotaSubP5() {
		return notaSubP5;
	}
	public void setNotaSubP5(float notaSubP5) {
		this.notaSubP5 = notaSubP5;
	}
	
}
