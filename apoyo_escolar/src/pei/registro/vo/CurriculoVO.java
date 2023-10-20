package pei.registro.vo;

import siges.common.vo.Vo;

public class CurriculoVO extends Vo {
	private long curCodigoInstitucion;
	private int curConsecutivo;
	private String curAlcance;
	private String curDificultad;
	
	private boolean curDisabled;

	public boolean isCurDisabled() {
		return curDisabled;
	}
	public void setCurDisabled(boolean curDisabled) {
		this.curDisabled = curDisabled;
	}
	public long getCurCodigoInstitucion() {
		return curCodigoInstitucion;
	}
	public void setCurCodigoInstitucion(long curCodigoInstitucion) {
		this.curCodigoInstitucion = curCodigoInstitucion;
	}
	public int getCurConsecutivo() {
		return curConsecutivo;
	}
	public void setCurConsecutivo(int curConsecutivo) {
		this.curConsecutivo = curConsecutivo;
	}
	public String getCurAlcance() {
		return curAlcance;
	}
	public void setCurAlcance(String curAlcance) {
		this.curAlcance = curAlcance;
	}
	public String getCurDificultad() {
		return curDificultad;
	}
	public void setCurDificultad(String curDificultad) {
		this.curDificultad = curDificultad;
	}
	
}
