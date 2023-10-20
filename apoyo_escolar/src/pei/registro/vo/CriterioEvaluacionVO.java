package pei.registro.vo;

import siges.common.vo.Vo;

public class CriterioEvaluacionVO extends Vo {
	private long criCodigoInstitucion;
	private int criConsecutivo;
	private String criAlcance;
	private String criDificultad;
	
	private boolean criDisabled;

	public long getCriCodigoInstitucion() {
		return criCodigoInstitucion;
	}

	public void setCriCodigoInstitucion(long criCodigoInstitucion) {
		this.criCodigoInstitucion = criCodigoInstitucion;
	}

	public int getCriConsecutivo() {
		return criConsecutivo;
	}

	public void setCriConsecutivo(int criConsecutivo) {
		this.criConsecutivo = criConsecutivo;
	}

	public String getCriAlcance() {
		return criAlcance;
	}

	public void setCriAlcance(String criAlcance) {
		this.criAlcance = criAlcance;
	}

	public String getCriDificultad() {
		return criDificultad;
	}

	public void setCriDificultad(String criDificultad) {
		this.criDificultad = criDificultad;
	}

	public boolean isCriDisabled() {
		return criDisabled;
	}

	public void setCriDisabled(boolean criDisabled) {
		this.criDisabled = criDisabled;
	}

	
	
}
