package articulacion.retiroEstudiantes.vo;

import siges.common.vo.Vo;

public class EvaluacionVO extends Vo{

	private long artEvaCodInst;
	private long artEvaCodMetod;
	private int artEvaAnVigencia;
	private int artEvaPerVigencia;
	private long artEvaCodAsig;
	private long artEvaCodPrueba;
	private long[] artEvaCodEstudiante;
	private double [] artEvaNotaNum;
	private String[] artEvaNotaConc;
	private boolean artEvaEstado;
	private String artEvaFechaEstado;
	
	public int getArtEvaAnVigencia() {
		return artEvaAnVigencia;
	}
	public void setArtEvaAnVigencia(int artEvaAnVigencia) {
		this.artEvaAnVigencia = artEvaAnVigencia;
	}
	public long getArtEvaCodAsig() {
		return artEvaCodAsig;
	}
	public void setArtEvaCodAsig(long artEvaCodAsig) {
		this.artEvaCodAsig = artEvaCodAsig;
	}
	public long[] getArtEvaCodEstudiante() {
		return artEvaCodEstudiante;
	}
	public void setArtEvaCodEstudiante(long[] artEvaCodEstudiante) {
		this.artEvaCodEstudiante = artEvaCodEstudiante;
	}
	public long getArtEvaCodInst() {
		return artEvaCodInst;
	}
	public void setArtEvaCodInst(long artEvaCodInst) {
		this.artEvaCodInst = artEvaCodInst;
	}
	public long getArtEvaCodMetod() {
		return artEvaCodMetod;
	}
	public void setArtEvaCodMetod(long artEvaCodMetod) {
		this.artEvaCodMetod = artEvaCodMetod;
	}
	public long getArtEvaCodPrueba() {
		return artEvaCodPrueba;
	}
	public void setArtEvaCodPrueba(long artEvaCodPrueba) {
		this.artEvaCodPrueba = artEvaCodPrueba;
	}
	public boolean isArtEvaEstado() {
		return artEvaEstado;
	}
	public void setArtEvaEstado(boolean artEvaEstado) {
		this.artEvaEstado = artEvaEstado;
	}
	public String[] getArtEvaNotaConc() {
		return artEvaNotaConc;
	}
	public void setArtEvaNotaConc(String[] artEvaNotaConc) {
		this.artEvaNotaConc = artEvaNotaConc;
	}
	public double[] getArtEvaNotaNum() {
		return artEvaNotaNum;
	}
	public void setArtEvaNotaNum(double[] artEvaNotaNum) {
		this.artEvaNotaNum = artEvaNotaNum;
	}
	public int getArtEvaPerVigencia() {
		return artEvaPerVigencia;
	}
	public void setArtEvaPerVigencia(int artEvaPerVigencia) {
		this.artEvaPerVigencia = artEvaPerVigencia;
	}
	public String getArtEvaFechaEstado() {
		return artEvaFechaEstado;
	}
	public void setArtEvaFechaEstado(String artEvaFechaEstado) {
		this.artEvaFechaEstado = artEvaFechaEstado;
	}
	
	
}
