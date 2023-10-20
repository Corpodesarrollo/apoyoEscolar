package articulacion.evaluacionArt.vo;

import siges.common.vo.Vo;

public class EstEvaluacionVO extends Vo{

	private long codigo;
	private float notaPru1;
	private float notaPru2;
	private float notaPru3;
	private float notaPru4;
	private float notaPru5;
	private float notaNum;
	private String notaCon;
	private boolean estado;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
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
	public float getNotaPru1() {
		return notaPru1;
	}
	public void setNotaPru1(float notaPru1) {
		this.notaPru1 = notaPru1;
	}
	public float getNotaPru2() {
		return notaPru2;
	}
	public void setNotaPru2(float notaPru2) {
		this.notaPru2 = notaPru2;
	}
	public float getNotaPru3() {
		return notaPru3;
	}
	public void setNotaPru3(float notaPru3) {
		this.notaPru3 = notaPru3;
	}
	public float getNotaPru4() {
		return notaPru4;
	}
	public void setNotaPru4(float notaPru4) {
		this.notaPru4 = notaPru4;
	}
	public float getNotaPru5() {
		return notaPru5;
	}
	public void setNotaPru5(float notaPru5) {
		this.notaPru5 = notaPru5;
	}
	
	
}
