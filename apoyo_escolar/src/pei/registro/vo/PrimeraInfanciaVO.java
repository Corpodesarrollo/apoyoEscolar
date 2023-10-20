package pei.registro.vo;

import siges.common.vo.Vo;

public class PrimeraInfanciaVO extends Vo {
	private long priCodigoInstitucion;
	private int priConsecutivo;
	private String priGrado;
	private int priCursos;
	private Long priEstudiantes;
	
	private boolean priDisabled;

	public long getPriCodigoInstitucion() {
		return priCodigoInstitucion;
	}

	public void setPriCodigoInstitucion(long priCodigoInstitucion) {
		this.priCodigoInstitucion = priCodigoInstitucion;
	}

	public int getPriConsecutivo() {
		return priConsecutivo;
	}

	public void setPriConsecutivo(int priConsecutivo) {
		this.priConsecutivo = priConsecutivo;
	}

	public String getPriGrado() {
		return priGrado;
	}

	public void setPriGrado(String priGrado) {
		this.priGrado = priGrado;
	}

	public int getPriCursos() {
		return priCursos;
	}

	public void setPriCursos(int priCursos) {
		this.priCursos = priCursos;
	}

	public Long getPriEstudiantes() {
		return priEstudiantes;
	}

	public void setPriEstudiantes(Long priEstudiantes) {
		this.priEstudiantes = priEstudiantes;
	}

	public boolean isPriDisabled() {
		return priDisabled;
	}

	public void setPriDisabled(boolean priDisabled) {
		this.priDisabled = priDisabled;
	}

}
