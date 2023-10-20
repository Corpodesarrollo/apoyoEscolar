/**
 * 
 */
package siges.evaluacion.beans;



/**
 * 10/03/2010
 * @author Athenea TA
 * @version 1.1
 */
public class TipoEvalVO{

	public long cod_tipo_eval;
	public double eval_max;
	public double eval_min;
	public int tipo_eval_prees;
	public int modo_eval;
	public double tipoEvalAprobMin;
	
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
	public int getTipo_eval_prees() {
		return tipo_eval_prees;
	}
	public void setTipo_eval_prees(int tipoEvalPrees) {
		tipo_eval_prees = tipoEvalPrees;
	}
	public int getModo_eval() {
		return modo_eval;
	}
	public void setModo_eval(int modoEval) {
		modo_eval = modoEval;
	}
	/**
	 * @return the tipoEvalAprobMin
	 */
	public double getTipoEvalAprobMin() {
		return tipoEvalAprobMin;
	}
	/**
	 * @param tipoEvalAprobMin the tipoEvalAprobMin to set
	 */
	public void setTipoEvalAprobMin(double tipoEvalAprobMin) {
		this.tipoEvalAprobMin = tipoEvalAprobMin;
	}
	
	
	
		
	
}
