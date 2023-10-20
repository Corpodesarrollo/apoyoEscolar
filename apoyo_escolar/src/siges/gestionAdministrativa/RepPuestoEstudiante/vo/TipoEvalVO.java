/**
 * 
 */
package siges.gestionAdministrativa.RepPuestoEstudiante.vo;

import java.util.List;



/**
 * 10/03/2010
 * @author Athenea TA
 * @version 1.1
 */
public class TipoEvalVO{

	private long cod_tipo_eval;
	private double eval_max;
	private double eval_min;
	private int modo_eval;
	private int tipo_eval_prees;
	private long cod_nivel_eval;
	private List escala;
	
	public long getCod_tipo_eval() {
		return cod_tipo_eval;
	}
	public void setCod_tipo_eval(long codTipoEval) {
		cod_tipo_eval = codTipoEval;
	}
	public double getEval_max() {
		return eval_max;
	}
	public void setEval_max(double evalMax) {
		eval_max = evalMax;
	}
	public double getEval_min() {
		return eval_min;
	}
	public void setEval_min(double evalMin) {
		eval_min = evalMin;
	}
	public int getModo_eval() {
		return modo_eval;
	}
	public void setModo_eval(int modoEval) {
		modo_eval = modoEval;
	}
	public int getTipo_eval_prees() {
		return tipo_eval_prees;
	}
	public void setTipo_eval_prees(int tipoEvalPrees) {
		tipo_eval_prees = tipoEvalPrees;
	}
	public final long getCod_nivel_eval() {
		return cod_nivel_eval;
	}
	public final void setCod_nivel_eval(long codNivelEval) {
		cod_nivel_eval = codNivelEval;
	}
	public List getEscala() {
		return escala;
	}
	public void setEscala(List escala) {
		this.escala = escala;
	}
	
	
	
		
	
}
