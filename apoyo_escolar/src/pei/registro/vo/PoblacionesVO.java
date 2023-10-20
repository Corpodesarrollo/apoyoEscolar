package pei.registro.vo;

import siges.common.vo.Vo;

public class PoblacionesVO extends Vo {
	private long pobCodigoInstitucion;
	private int pobConsecutivo;
	private String pobLineaPolitica;
	private int pobEstudiantes;
	
	private boolean horDisabled;

	public long getPobCodigoInstitucion() {
		return pobCodigoInstitucion;
	}

	public void setPobCodigoInstitucion(long pobCodigoInstitucion) {
		this.pobCodigoInstitucion = pobCodigoInstitucion;
	}

	public int getPobConsecutivo() {
		return pobConsecutivo;
	}

	public void setPobConsecutivo(int pobConsecutivo) {
		this.pobConsecutivo = pobConsecutivo;
	}

	public String getPobLineaPolitica() {
		return pobLineaPolitica;
	}

	public void setPobLineaPolitica(String pobLineaPolitica) {
		this.pobLineaPolitica = pobLineaPolitica;
	}

	public int getPobEstudiantes() {
		return pobEstudiantes;
	}

	public void setPobEstudiantes(int pobEstudiantes) {
		this.pobEstudiantes = pobEstudiantes;
	}

	public boolean isHorDisabled() {
		return horDisabled;
	}

	public void setHorDisabled(boolean horDisabled) {
		this.horDisabled = horDisabled;
	}
	
	
}
