package articulacion.retiroEstudiantes.vo;

import siges.common.vo.Vo;

public class RangosVO extends Vo{

	private String conceptual;
	private float inicio;
	private float fin;
	
	public String getConceptual() {
		return conceptual;
	}
	public float getFin() {
		return fin;
	}
	public float getInicio() {
		return inicio;
	}
	public void setConceptual(String conceptual) {
		this.conceptual = conceptual;
	}
	public void setFin(float fin) {
		this.fin = fin;
	}
	public void setInicio(float inicio) {
		this.inicio = inicio;
	}
	
}
