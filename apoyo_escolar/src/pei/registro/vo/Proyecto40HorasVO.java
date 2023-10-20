package pei.registro.vo;

import siges.common.vo.Vo;

public class Proyecto40HorasVO extends Vo {
	private long horCodigoInstitucion;
	private int horConsecutivo;
	private int horEstudiantes;
	private String horCentro;
	
	private boolean horDisabled;

	public long getHorCodigoInstitucion() {
		return horCodigoInstitucion;
	}

	public void setHorCodigoInstitucion(long horCodigoInstitucion) {
		this.horCodigoInstitucion = horCodigoInstitucion;
	}

	public int getHorConsecutivo() {
		return horConsecutivo;
	}

	public void setHorConsecutivo(int horConsecutivo) {
		this.horConsecutivo = horConsecutivo;
	}

	public int getHorEstudiantes() {
		return horEstudiantes;
	}

	public void setHorEstudiantes(int horEstudiantes) {
		this.horEstudiantes = horEstudiantes;
	}

	public String getHorCentro() {
		return horCentro;
	}

	public void setHorCentro(String horCentro) {
		this.horCentro = horCentro;
	}

	public boolean isHorDisabled() {
		return horDisabled;
	}

	public void setHorDisabled(boolean horDisabled) {
		this.horDisabled = horDisabled;
	}

	
}
