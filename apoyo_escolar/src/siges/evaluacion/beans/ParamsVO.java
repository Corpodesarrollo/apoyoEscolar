/**
 * 
 */
package siges.evaluacion.beans;

import siges.common.vo.Params;

/**
 * 2/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int EVAL_LOG=1;
	public static final int EVAL_ASI=2;
	public static final int EVAL_DES=3;
	public static final int EVAL_ARE=4;
	public static final int EVAL_REC=5;
	public static final int EVAL_PRE=6;
	public static final int EVAL_OBS=7;
	public static final int EVAL_COMP=10;

	public static final int CMD_AJAX_DOCENTE=11;
	
	public static final int TIPO_EVAL_CONCEPTUAL=2;
	public static final int TIPO_EVAL_NUM=1;
	public static final int TIPO_EVAL_POR=3;
	public static final int TIPO_EVAL_MEN=4;
	
	public static final int TIPO_EVAL_PREES_DIM=1;
	public static final int TIPO_EVAL_PREES_ASI=2;
	
	/**
	 * @return Return the cMD_AJAX_DOCENTE.
	 */
	public int getCMD_AJAX_DOCENTE() {
		return CMD_AJAX_DOCENTE;
	}

	/**
	 * @return Return the eVAL_ARE.
	 */
	public int getEVAL_ARE() {
		return EVAL_ARE;
	}

	/**
	 * @return Return the eVAL_ASI.
	 */
	public int getEVAL_ASI() {
		return EVAL_ASI;
	}

	/**
	 * @return Return the eVAL_COMP.
	 */
	public int getEVAL_COMP() {
		return EVAL_COMP;
	}

	/**
	 * @return Return the eVAL_DES.
	 */
	public int getEVAL_DES() {
		return EVAL_DES;
	}

	/**
	 * @return Return the eVAL_LOG.
	 */
	public int getEVAL_LOG() {
		return EVAL_LOG;
	}

	/**
	 * @return Return the eVAL_OBS.
	 */
	public int getEVAL_OBS() {
		return EVAL_OBS;
	}

	/**
	 * @return Return the eVAL_PRE.
	 */
	public int getEVAL_PRE() {
		return EVAL_PRE;
	}

	/**
	 * @return Return the eVAL_REC.
	 */
	public int getEVAL_REC() {
		return EVAL_REC;
	}

	public  int getTipoEvalConceptual() {
		return TIPO_EVAL_CONCEPTUAL;
	}

	public  int getTipoEvalNum() {
		return TIPO_EVAL_NUM;
	}

	public  int getTipoEvalPor() {
		return TIPO_EVAL_POR;
	}

	public  int getTipoEvalMen() {
		return TIPO_EVAL_MEN;
	}

	public  int getTipoEvalPreesDim() {
		return TIPO_EVAL_PREES_DIM;
	}

	public  int getTipoEvalPreesAsi() {
		return TIPO_EVAL_PREES_ASI;
	}
}
