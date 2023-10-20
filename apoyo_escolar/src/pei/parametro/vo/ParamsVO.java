package pei.parametro.vo;

import pei.common.vo.ParamPEI;

public class ParamsVO extends ParamPEI{
	public static final int FICHA_PARAMETRO=1;
	public static final int FICHA_DEFAULT=FICHA_PARAMETRO;
	
	public static final String BEAN_PARAMETRO="parametroPEIVO";
	public static final String BEAN_FILTRO_PARAMETRO="filtroParametroPEIVO";
	/**
	 * @return the fichaParametro
	 */
	public int getFICHA_PARAMETRO() {
		return FICHA_PARAMETRO;
	}
	/**
	 * @return the fichaDefault
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return the beanParametro
	 */
	public String getBEAN_PARAMETRO() {
		return BEAN_PARAMETRO;
	}
	/**
	 * @return the beanFiltroParametro
	 */
	public String getBEAN_FILTRO_PARAMETRO() {
		return BEAN_FILTRO_PARAMETRO;
	}
	

}
