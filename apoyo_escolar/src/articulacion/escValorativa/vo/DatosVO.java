package articulacion.escValorativa.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{

	private int institucion;
	private int metodologia;
	private int anVigencia;
	private int perVigencia;
	
	public int getAnVigencia() {
		return anVigencia;
	}
	public void setAnVigencia(int anVigencia) {
		this.anVigencia = anVigencia;
	}
	public int getInstitucion() {
		return institucion;
	}
	public void setInstitucion(int institucion) {
		this.institucion = institucion;
	}
	public int getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(int metodologia) {
		this.metodologia = metodologia;
	}
	public int getPerVigencia() {
		return perVigencia;
	}
	public void setPerVigencia(int perVigencia) {
		this.perVigencia = perVigencia;
	}

		
}
