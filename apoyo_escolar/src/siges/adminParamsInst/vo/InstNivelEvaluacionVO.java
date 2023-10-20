package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class InstNivelEvaluacionVO extends Vo {
	
	
	private long insnivvigencia;
	private long insnivcodinst;
	private long insnivcodniveleval;
	private long insnivcodmetod = -99;
	private long insnivcodsede = -99;
	             
	private long insnivcodjorn = -99;
	private long insnivcodnivel = -99;
	private long insnivcodgrado = -99;
	private long insnivtipoevalasig = 0;
	private long insnivtipoevalasigAntes = 0;
	private long insnivtipoevalprees;
	private long insnivtipoevalpreesAntes;
	private String insnivvalminnum = "00.00";
	private String insnivvalmaxnum = "00.00";
	private String insnivvalminnumAntes;
	private String insnivvalmaxnumAntes;
	private String insnivvalaprobnum = "00.00";
	
	// Se comenta este ya que amplio el modulo de evaluacion
	private long insnivmodoeval;
	private long insnivmodoevalAntes;
	
	// Ampliaciones de modulo de evaluacion
	private String evalasignatura;
	private String evalperiodo;
	
	private boolean edicion;
	private int yaFueUtilizado = 0;
	
	private long insnivmodoevallogro;
	
	
	public long getInsnivcodgrado() {
		return insnivcodgrado;
	}
	
	public void setInsnivcodgrado(long insnivcodgrado) {
		this.insnivcodgrado = insnivcodgrado;
	}
	
	public long getInsnivcodinst() {
		return insnivcodinst;
	}
	
	public void setInsnivcodinst(long insnivcodinst) {
		this.insnivcodinst = insnivcodinst;
	}
	
	public long getInsnivcodjorn() {
		return insnivcodjorn;
	}
	
	public void setInsnivcodjorn(long insnivcodjorn) {
		this.insnivcodjorn = insnivcodjorn;
	}
	
	public long getInsnivcodmetod() {
		return insnivcodmetod;
	}
	
	public void setInsnivcodmetod(long insnivcodmetod) {
		this.insnivcodmetod = insnivcodmetod;
	}
	
	public long getInsnivcodnivel() {
		return insnivcodnivel;
	}
	
	public void setInsnivcodnivel(long insnivcodnivel) {
		this.insnivcodnivel = insnivcodnivel;
	}
	
	public long getInsnivcodniveleval() {
		return insnivcodniveleval;
	}
	
	public void setInsnivcodniveleval(long insnivcodniveleval) {
		this.insnivcodniveleval = insnivcodniveleval;
	}
	
	public long getInsnivcodsede() {
		return insnivcodsede;
	}
	
	public void setInsnivcodsede(long insnivcodsede) {
		this.insnivcodsede = insnivcodsede;
	}
	
	public long getInsnivtipoevalasig() {
		return insnivtipoevalasig;
	}
	
	public void setInsnivtipoevalasig(long insnivtipoevalasig) {
		this.insnivtipoevalasig = insnivtipoevalasig;
	}
	
	public long getInsnivtipoevalprees() {
		return insnivtipoevalprees;
	}
	
	public void setInsnivtipoevalprees(long insnivtipoevalprees) {
		this.insnivtipoevalprees = insnivtipoevalprees;
	}
	 
	public long getInsnivvigencia() {
		return insnivvigencia;
	}
	
	public void setInsnivvigencia(long insnivvigencia) {
		this.insnivvigencia = insnivvigencia;
	}
	
	public boolean isEdicion() {
		return edicion;
	}
	
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}
	
	public String getInsnivvalmaxnum() {
		return insnivvalmaxnum;
	}
	
	public void setInsnivvalmaxnum(String insnivvalmaxnum) {
		this.insnivvalmaxnum = insnivvalmaxnum;
	}
	
	public String getInsnivvalminnum() {
		return insnivvalminnum;
	}
	
	public void setInsnivvalminnum(String insnivvalminnum) {
		this.insnivvalminnum = insnivvalminnum;
	}
	
	public String getInsnivvalaprobnum() {
		return insnivvalaprobnum;
	}
	
	public void setInsnivvalaprobnum(String insnivvalaprobnum) {
		this.insnivvalaprobnum = insnivvalaprobnum;
	}
	
	public int getYaFueUtilizado() {
		return yaFueUtilizado;
	}
	
	public void setYaFueUtilizado(int yaFueUtilizado) {
		this.yaFueUtilizado = yaFueUtilizado;
	}
	
	public long getInsnivmodoeval() {
		return insnivmodoeval;
	}
	
	public void setInsnivmodoeval(long insnivmodoeval) {
		this.insnivmodoeval = insnivmodoeval;
	}
	
	public String getInsnivvalmaxnumAntes() {
		return insnivvalmaxnumAntes;
	}
	
	public void setInsnivvalmaxnumAntes(String insnivvalmaxnumAntes) {
		this.insnivvalmaxnumAntes = insnivvalmaxnumAntes;
	}
	
	public String getInsnivvalminnumAntes() {
		return insnivvalminnumAntes;
	}
	
	public void setInsnivvalminnumAntes(String insnivvalminnumAntes) {
		this.insnivvalminnumAntes = insnivvalminnumAntes;
	}
	
	public long getInsnivtipoevalasigAntes() {
		return insnivtipoevalasigAntes;
	}
	
	public void setInsnivtipoevalasigAntes(long insnivtipoevalasigAntes) {
		this.insnivtipoevalasigAntes = insnivtipoevalasigAntes;
	}
	
	public long getInsnivmodoevalAntes() {
		return insnivmodoevalAntes;
	}
	
	public void setInsnivmodoevalAntes(long insnivmodoevalAntes) {
		this.insnivmodoevalAntes = insnivmodoevalAntes;
	}
	
	public long getInsnivtipoevalpreesAntes() {
		return insnivtipoevalpreesAntes;
	}
	
	public void setInsnivtipoevalpreesAntes(long insnivtipoevalpreesAntes) {
		this.insnivtipoevalpreesAntes = insnivtipoevalpreesAntes;
	}
	
	/**
	 * @return the evalasignatura
	 */
	public String getEvalasignatura() {
		return evalasignatura;
	}
	
	/**
	 * @param evalasignatura the evalasignatura to set
	 */
	public void setEvalasignatura(String evalasignatura) {
		this.evalasignatura = evalasignatura;
	}
	
	/**
	 * @return the evalperiodo
	 */
	public String getEvalperiodo() {
		return evalperiodo;
	}
	
	/**
	 * @param evalperiodo the evalperiodo to set
	 */
	public void setEvalperiodo(String evalperiodo) {
		this.evalperiodo = evalperiodo;
	}

	public long getInsnivmodoevallogro() {
		return insnivmodoevallogro;
	}

	public void setInsnivmodoevallogro(long insnivmodoevallogro) {
		this.insnivmodoevallogro = insnivmodoevallogro;
	}
	
	
	
}
