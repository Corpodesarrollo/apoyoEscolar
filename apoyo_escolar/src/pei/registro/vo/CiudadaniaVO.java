package pei.registro.vo;

import siges.common.vo.Vo;

public class CiudadaniaVO extends Vo {
	private Long ciuCodigoInstitucion;
	private int ciuConsecutivo;
	private String ciuAlcance;
	private String ciuDificultad;
	
	private boolean ciuDisabled;

	public Long getCiuCodigoInstitucion() {
		return ciuCodigoInstitucion;
	}

	public void setCiuCodigoInstitucion(Long ciuCodigoInstitucion) {
		this.ciuCodigoInstitucion = ciuCodigoInstitucion;
	}

	public int getCiuConsecutivo() {
		return ciuConsecutivo;
	}

	public void setCiuConsecutivo(int ciuConsecutivo) {
		this.ciuConsecutivo = ciuConsecutivo;
	}

	public String getCiuAlcance() {
		return ciuAlcance;
	}

	public void setCiuAlcance(String ciuAlcance) {
		this.ciuAlcance = ciuAlcance;
	}

	public String getCiuDificultad() {
		return ciuDificultad;
	}

	public void setCiuDificultad(String ciuDificultad) {
		this.ciuDificultad = ciuDificultad;
	}

	public boolean isCiuDisabled() {
		return ciuDisabled;
	}

	public void setCiuDisabled(boolean ciuDisabled) {
		this.ciuDisabled = ciuDisabled;
	}
}
