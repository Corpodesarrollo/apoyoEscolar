package articulacion.evaluacionArt.vo;

import siges.common.vo.Vo;

public class EvaluacionVO extends Vo{

	private long artEvaCodAsig;
	private long artEvaCodPrueba;
	private long[] artEvaCodEstudiante;
	private double [] artEvaNotaNum;
	private double [] artEvaNotaNumSp1;
	private double [] artEvaNotaNumSp2;
	private double [] artEvaNotaNumSp3;
	private double [] artEvaNotaNumSp4;
	private double [] artEvaNotaNumSp5;
	private String[] artEvaNotaConc;
	private boolean artEvaEstado;
	private String artEvaFechaEstado;
	
	private double[] subPru1;
	private double[] subPru2;
	private double[] subPru3;
	private double[] subPru4;
	private double[] subPru5;
	
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
	public String getArtEvaFechaEstado() {
		return artEvaFechaEstado;
	}
	public void setArtEvaFechaEstado(String artEvaFechaEstado) {
		this.artEvaFechaEstado = artEvaFechaEstado;
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
	public double[] getArtEvaNotaNumSp1() {
		return artEvaNotaNumSp1;
	}
	public void setArtEvaNotaNumSp1(double[] artEvaNotaNumSp1) {
		this.artEvaNotaNumSp1 = artEvaNotaNumSp1;
	//	System.out.println("asigna nota evaluacion 1");
	}
	public double[] getArtEvaNotaNumSp2() {
		return artEvaNotaNumSp2;
	}
	public void setArtEvaNotaNumSp2(double[] artEvaNotaNumSp2) {
	//	System.out.println("asigna nota evaluacion 2");
		this.artEvaNotaNumSp2 = artEvaNotaNumSp2;
	}
	public double[] getArtEvaNotaNumSp3() {
		return artEvaNotaNumSp3;
	}
	public void setArtEvaNotaNumSp3(double[] artEvaNotaNumSp3) {
	//	System.out.println("asigna nota evaluacion 3");
		this.artEvaNotaNumSp3 = artEvaNotaNumSp3;
	}
	public double[] getArtEvaNotaNumSp4() {
		return artEvaNotaNumSp4;
	}
	public void setArtEvaNotaNumSp4(double[] artEvaNotaNumSp4) {
		this.artEvaNotaNumSp4 = artEvaNotaNumSp4;
	//	System.out.println("asigna nota evaluacion 4");
	}
	public double[] getArtEvaNotaNumSp5() {
		return artEvaNotaNumSp5;
	}
	public void setArtEvaNotaNumSp5(double[] artEvaNotaNumSp5) {
		this.artEvaNotaNumSp5 = artEvaNotaNumSp5;
	//	System.out.println("asigna nota evaluacion 5");
	}
	public double[] getSubPru1() {
		return subPru1;
	}
	public void setSubPru1(double[] subPru1) {
		this.subPru1 = subPru1;
	}
	public double[] getSubPru2() {
		return subPru2;
	}
	public void setSubPru2(double[] subPru2) {
		this.subPru2 = subPru2;
	}
	public double[] getSubPru3() {
		return subPru3;
	}
	public void setSubPru3(double[] subPru3) {
		this.subPru3 = subPru3;
	}
	public double[] getSubPru4() {
		return subPru4;
	}
	public void setSubPru4(double[] subPru4) {
		this.subPru4 = subPru4;
	}
	public double[] getSubPru5() {
		return subPru5;
	}
	public void setSubPru5(double[] subPru5) {
		this.subPru5 = subPru5;
	}
}
