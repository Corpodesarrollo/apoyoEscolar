/**
 * 
 */
package siges.plantilla.beans;



/**
 * 10/03/2010
 * @author Athenea TA
 * @version 1.1
 */
public class TipoEvalVO{

	public long cod_tipo_eval;
	public long cod_modo_eval;
	public long cod_tipo_eval_pree;
	public double eval_max;
	public double eval_min;
	public int eval_rangos_completos;
	
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
	public int getEval_rangos_completos() {
		return eval_rangos_completos;
	}
	public void setEval_rangos_completos(int eval_rangos_completos) {
		this.eval_rangos_completos = eval_rangos_completos;
	}
	public long getCod_modo_eval() {
		return cod_modo_eval;
	}
	public void setCod_modo_eval(long cod_modo_eval) {
		this.cod_modo_eval = cod_modo_eval;
	}
	public long getCod_tipo_eval_pree() {
		return cod_tipo_eval_pree;
	}
	public void setCod_tipo_eval_pree(long cod_tipo_eval_pree) {
		this.cod_tipo_eval_pree = cod_tipo_eval_pree;
	}
	
	
	
		
	
}
