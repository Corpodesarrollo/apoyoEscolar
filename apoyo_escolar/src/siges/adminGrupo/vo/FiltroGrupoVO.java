package siges.adminGrupo.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroGrupoVO  extends Vo{
	
	private long filVigencia;
	private long filCodInst;
	private long filCodMetodo;	
	private long filCodSede;
	private long filCodJorn;
	private long filCodGrado;
	
	public long getFilVigencia() {
		return filVigencia;
	}
	public void setFilVigencia(long filVigencia) {
		this.filVigencia = filVigencia;
	}
	public long getFilCodInst() {
		return filCodInst;
	}
	public void setFilCodInst(long filCodInst) {
		this.filCodInst = filCodInst;
	}
	public long getFilCodMetodo() {
		return filCodMetodo;
	}
	public void setFilCodMetodo(long filCodMetodo) {
		this.filCodMetodo = filCodMetodo;
	}
	public long getFilCodSede() {
		return filCodSede;
	}
	public void setFilCodSede(long filCodSede) {
		this.filCodSede = filCodSede;
	}
	public long getFilCodJorn() {
		return filCodJorn;
	}
	public void setFilCodJorn(long filCodJorn) {
		this.filCodJorn = filCodJorn;
	}
	public long getFilCodGrado() {
		return filCodGrado;
	}
	public void setFilCodGrado(long filCodGrado) {
		this.filCodGrado = filCodGrado;
	}
	
	
	
	
 
	
}
