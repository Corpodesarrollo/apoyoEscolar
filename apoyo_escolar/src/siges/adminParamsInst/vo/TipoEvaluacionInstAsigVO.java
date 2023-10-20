package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

public class TipoEvaluacionInstAsigVO extends Vo {

	
	private int tipEvalTipoEval;
	private long tipEvalCodJorn;
	private long tipEvalCodInst;
	private long tipEvalVigencia;

	
	public long getTipEvalCodJorn() {
		return tipEvalCodJorn;
	}

	public void setTipEvalCodJorn(long tipEvalCodJorn) {
		this.tipEvalCodJorn = tipEvalCodJorn;
	}

	public int getTipEvalTipoEval() {
		return tipEvalTipoEval;
	}

	public void setTipEvalTipoEval(int tipEvalTipoEval) {
		this.tipEvalTipoEval = tipEvalTipoEval;
	}

	public long getTipEvalCodInst() {
		return tipEvalCodInst;
	}

	public void setTipEvalCodInst(long tipEvalCodInst) {
		this.tipEvalCodInst = tipEvalCodInst;
	}

	public long getTipEvalVigencia() {
		return tipEvalVigencia;
	}

	public void setTipEvalVigencia(long tipEvalVigencia) {
		this.tipEvalVigencia = tipEvalVigencia;
	}
	
}
