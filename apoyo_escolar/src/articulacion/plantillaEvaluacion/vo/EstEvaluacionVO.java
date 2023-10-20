package articulacion.plantillaEvaluacion.vo;

import siges.common.vo.Vo;

public class EstEvaluacionVO extends Vo{

	private long codigo;
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
	
	
}
