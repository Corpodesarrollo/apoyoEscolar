package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroNivelEvalVO  extends Vo{

	 private long filVigencia;
	 private long filInst;
	 private long filMetodo = -99;
	 private long filNivel = -99;
	 private long filGrado  = -99;
	 private long filJorn = -99;
	 private long filSede = -99;
	 private long filniveval;
	public long getFilVigencia() {
		return filVigencia;
	}

	public void setFilVigencia(long filVigencia) {
		this.filVigencia = filVigencia;
	}

	public long getFilGrado() {
		return filGrado;
	}

	public void setFilGrado(long filGrado) {
		this.filGrado = filGrado;
	}

	public long getFilMetodo() {
		return filMetodo;
	}

	public void setFilMetodo(long filMetodo) {
		this.filMetodo = filMetodo;
	}

	public long getFilNivel() {
		return filNivel;
	}

	public void setFilNivel(long filNivel) {
		this.filNivel = filNivel;
	}

	public long getFilJorn() {
		return filJorn;
	}

	public void setFilJorn(long filJorn) {
		this.filJorn = filJorn;
	}

	public long getFilSede() {
		return filSede;
	}

	public void setFilSede(long filSede) {
		this.filSede = filSede;
	}

	public long getFilInst() {
		return filInst;
	}

	public void setFilInst(long filInst) {
		this.filInst = filInst;
	}

	public long getFilniveval() {
		return filniveval;
	}

	public void setFilniveval(long filniveval) {
		this.filniveval = filniveval;
	}

 

 
 }
