package articulacion.importarEvaluacion.objetos;

import siges.common.vo.Vo;

public class CodigoEstudianteVO extends Vo{


	private long codigo;
	private double notaNumerica;
	private String notaConceptual;
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getNotaConceptual() {
		return notaConceptual;
	}
	public void setNotaConceptual(String notaConceptual) {
		this.notaConceptual = notaConceptual;
	}
	public double getNotaNumerica() {
		return notaNumerica;
	}
	public void setNotaNumerica(double notaNumerica) {
		this.notaNumerica = notaNumerica;
	}
	
	

	
	
	}
