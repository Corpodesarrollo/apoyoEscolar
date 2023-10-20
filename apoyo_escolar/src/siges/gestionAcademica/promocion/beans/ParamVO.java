/**
 * 
 */
package siges.gestionAcademica.promocion.beans;

import siges.common.vo.Params;

/**
 * 3/09/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamVO extends Params{
	public static final int TIPO_PROMO_1_COD = 1;
	public static final String TIPO_PROMO_1_TEX = "Semestre 1";
	public static final int TIPO_PROMO_2_COD = 2;
	public static final String TIPO_PROMO_2_TEX = "Semestre 2";
	public static final int TIPO_PROMO_3_COD = 3;
	public static final String TIPO_PROMO_3_TEX = "Anual";
	
	public static String getTIPO_PROMO(int caso){
		switch (caso) {
		case TIPO_PROMO_1_COD:
			 return TIPO_PROMO_1_TEX;
		case TIPO_PROMO_2_COD:
			 return TIPO_PROMO_2_TEX;
		case TIPO_PROMO_3_COD:
		default:
			return TIPO_PROMO_3_TEX;	 
		}
	}
}
