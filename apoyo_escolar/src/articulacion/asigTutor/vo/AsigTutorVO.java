package articulacion.asigTutor.vo;

import siges.common.vo.Vo;

public class AsigTutorVO extends Vo{
	
	private long[] artHisTutCodEst;
	private long artHisTutCodTutor;
	private int artHisTutSemestre;
	private long[] check;
	private long[] ct;
	
	public long[] getArtHisTutCodEst() {
		return artHisTutCodEst;
	}
	public long getArtHisTutCodTutor() {
		return artHisTutCodTutor;
	}
	public int getArtHisTutSemestre() {
		return artHisTutSemestre;
	}
	public long[] getCheck() {
		return check;
	}
	public void setArtHisTutCodEst(long[] artHisTutCodEst) {
		this.artHisTutCodEst = artHisTutCodEst;
	}
	public void setArtHisTutCodTutor(long artHisTutCodTutor) {
		this.artHisTutCodTutor = artHisTutCodTutor;
	}
	public void setArtHisTutSemestre(int artHisTutSemestre) {
		this.artHisTutSemestre = artHisTutSemestre;
	}
	public void setCheck(long[] check) {
		this.check = check;
	}
	public long[] getCt() {
		return ct;
	}
	public void setCt(long[] ct) {
		this.ct = ct;
	}
	
	
}
