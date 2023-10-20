package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroEscalaNumVO  extends Vo{

	 private long filNumVigencia;
	 private long filNumInst;
	 private long filNumMetodo = -99;
	 private long filNumNivel = -99;
	 private long filNumGrado = -99;
	 private long filNumJorn = -99;
	 private long filNumSede = -99;
	 private long filNumniveval; 
	 
	public long getFilNumGrado() {
		return filNumGrado;
	}
	public void setFilNumGrado(long filNumGrado) {
		this.filNumGrado = filNumGrado;
	}
	public long getFilNumInst() {
		return filNumInst;
	}
	public void setFilNumInst(long filNumInst) {
		this.filNumInst = filNumInst;
	}
	public long getFilNumJorn() {
		return filNumJorn;
	}
	public void setFilNumJorn(long filNumJorn) {
		this.filNumJorn = filNumJorn;
	}
	public long getFilNumMetodo() {
		return filNumMetodo;
	}
	public void setFilNumMetodo(long filNumMetodo) {
		this.filNumMetodo = filNumMetodo;
	}
	public long getFilNumNivel() {
		return filNumNivel;
	}
	public void setFilNumNivel(long filNumNivel) {
		this.filNumNivel = filNumNivel;
	}
	public long getFilNumniveval() {
		return filNumniveval;
	}
	public void setFilNumniveval(long filNumniveval) {
		this.filNumniveval = filNumniveval;
	}
	public long getFilNumSede() {
		return filNumSede;
	}
	public void setFilNumSede(long filNumSede) {
		this.filNumSede = filNumSede;
	}
	public long getFilNumVigencia() {
		return filNumVigencia;
	}
	public void setFilNumVigencia(long filNumVigencia) {
		this.filNumVigencia = filNumVigencia;
	}
	 
	
	  }
