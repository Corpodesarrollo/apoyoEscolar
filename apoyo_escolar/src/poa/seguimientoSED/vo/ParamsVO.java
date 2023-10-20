package poa.seguimientoSED.vo;

import siges.common.vo.Params;

/**
 * Value Object para el manejo de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO  extends Params{

	public static final int FICHA_ACTIVIDAD=1;
	public static final int FICHA_DEFAULT=FICHA_ACTIVIDAD;

	public static final int CMD_APROBAR_COLEGIO=10;
	public static final int CMD_APROBAR_SED=11;
	public static final int CMD_RECHAZAR_SED=12;
	public static final int CMD_AJAX_LINEA=15;
	public static final int CMD_AJAX_COLEGIO=16;
	public static final int CMD_EDITAR_COL=20;
	public static final int CMD_BUSCAR_COL=21;

	public static final String TABLA_ACTIVIDAD="POA_PLANACTIVIDAD";
	public static final String TABLA_FINANCIACION="POA_PLANACT_FTEFIN";

	public static final String CAMPO_PLACVIGENCIA="PLACVIGENCIA"; 
	public static final String CAMPO_PLACCODINST ="PLACCODINST";
	public static final String CAMPO_PLACCODIGO ="PLACCODIGO";
	public static final String CAMPO_PLACORDEN ="PLACORDEN";
	public static final String CAMPO_PLACOBJETIVO ="PLACOBJETIVO";
	public static final String CAMPO_PLACNOMBRE ="PLACNOMBRE";
	public static final String CAMPO_PLACCODARGESTION ="PLACCODARGESTION";
	public static final String CAMPO_PLACPONDERADO ="PLACPONDERADO";
	public static final String CAMPO_PLACCODLIACCION ="PLACCODLIACCION";
	public static final String CAMPO_PLACCODTIMETA ="PLACCODTIMETA";
	public static final String CAMPO_PLACCANTIDAD ="PLACCANTIDAD";
	public static final String CAMPO_PLACCODUNMEDIDA ="PLACCODUNMEDIDA";
	public static final String CAMPO_PLACOTROCUAL ="PLACOTROCUAL";
	public static final String CAMPO_PLACPRESUPUESTO ="PLACPRESUPUESTO";
	public static final String CAMPO_PLACNOMBRERESPON ="PLACNOMBRERESPON";
	public static final String CAMPO_PLACFECHATERMIN ="PLACFECHATERMIN";
	public static final String CAMPO_PLACPERIODO1 ="PLACPERIODO1";
	public static final String CAMPO_PLACPERIODO2 ="PLACPERIODO2";
	public static final String CAMPO_PLACPERIODO3 ="PLACPERIODO3";
	public static final String CAMPO_PLACPERIODO4="PLACPERIODO4";

	public static final String CAMPO_PLACFECHAREAL ="SEGFECHACUMPLIMT";
	public static final String CAMPO_PLACSEGUIMIENTO1 ="SEGPERIODO1";
	public static final String CAMPO_PLACSEGUIMIENTO2 ="SEGPERIODO2";
	public static final String CAMPO_PLACSEGUIMIENTO3 ="SEGPERIODO3";
	public static final String CAMPO_PLACSEGUIMIENTO4 ="SEGPERIODO4";

	public static final String CAMPO_PLACPORCENTAJE1 ="SEGPORCEJECP1";
	public static final String CAMPO_PLACPORCENTAJE2 ="SEGPORCEJECP2";
	public static final String CAMPO_PLACPORCENTAJE3 ="SEGPORCEJECP3";
	public static final String CAMPO_PLACPORCENTAJE4 ="SEGPORCEJECP4";
	
	public static final String CAMPO_PLACVERIFICACION1 ="SEGFTEVERIFP1";
	public static final String CAMPO_PLACVERIFICACION2 ="SEGFTEVERIFP2";
	public static final String CAMPO_PLACVERIFICACION3 ="SEGFTEVERIFP3";
	public static final String CAMPO_PLACVERIFICACION4 ="SEGFTEVERIFP4";

	public static final String CAMPO_PLACLOGRO1 ="SEGANALISISP1";
	public static final String CAMPO_PLACLOGRO2 ="SEGANALISISP2";
	public static final String CAMPO_PLACLOGRO3 ="SEGANALISISP3";
	public static final String CAMPO_PLACLOGRO4 ="SEGANALISISP4";

	public static final String CAMPO_PLFTVIGENCIA="PLFTVIGENCIA"; 
	public static final String CAMPO_PLFTCODINST="PLFTCODINST"; 
	public static final String CAMPO_PLFTCODPLAACT="PLFTCODPLAACT"; 
	public static final String CAMPO_PLFTCODFUFINANC="PLFTCODFUFINANC";

	/**
	 * @return Returns the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Returns the FICHA_ACTIVIDAD.
	 */
	public int getFICHA_ACTIVIDAD() {
		return FICHA_ACTIVIDAD;
	}
	/**
	 * @return Return the cAMPO_PLACCANTIDAD.
	 */
	public  String getCAMPO_PLACCANTIDAD() {
		return CAMPO_PLACCANTIDAD;
	}
	/**
	 * @return Return the cAMPO_PLACCODARGESTION.
	 */
	public  String getCAMPO_PLACCODARGESTION() {
		return CAMPO_PLACCODARGESTION;
	}
	/**
	 * @return Return the cAMPO_PLACCODIGO.
	 */
	public  String getCAMPO_PLACCODIGO() {
		return CAMPO_PLACCODIGO;
	}
	/**
	 * @return Return the cAMPO_PLACCODINST.
	 */
	public  String getCAMPO_PLACCODINST() {
		return CAMPO_PLACCODINST;
	}
	/**
	 * @return Return the cAMPO_PLACCODLIACCION.
	 */
	public  String getCAMPO_PLACCODLIACCION() {
		return CAMPO_PLACCODLIACCION;
	}
	/**
	 * @return Return the cAMPO_PLACCODTIMETA.
	 */
	public  String getCAMPO_PLACCODTIMETA() {
		return CAMPO_PLACCODTIMETA;
	}
	/**
	 * @return Return the cAMPO_PLACCODUNMEDIDA.
	 */
	public  String getCAMPO_PLACCODUNMEDIDA() {
		return CAMPO_PLACCODUNMEDIDA;
	}
	/**
	 * @return Return the cAMPO_PLACFECHATERMIN.
	 */
	public  String getCAMPO_PLACFECHATERMIN() {
		return CAMPO_PLACFECHATERMIN;
	}
	/**
	 * @return Return the cAMPO_PLACNOMBRE.
	 */
	public  String getCAMPO_PLACNOMBRE() {
		return CAMPO_PLACNOMBRE;
	}
	/**
	 * @return Return the cAMPO_PLACNOMBRERESPON.
	 */
	public  String getCAMPO_PLACNOMBRERESPON() {
		return CAMPO_PLACNOMBRERESPON;
	}
	/**
	 * @return Return the cAMPO_PLACOBJETIVO.
	 */
	public  String getCAMPO_PLACOBJETIVO() {
		return CAMPO_PLACOBJETIVO;
	}
	/**
	 * @return Return the cAMPO_PLACORDEN.
	 */
	public  String getCAMPO_PLACORDEN() {
		return CAMPO_PLACORDEN;
	}
	/**
	 * @return Return the cAMPO_PLACOTROCUAL.
	 */
	public  String getCAMPO_PLACOTROCUAL() {
		return CAMPO_PLACOTROCUAL;
	}
	/**
	 * @return Return the cAMPO_PLACPERIODO1.
	 */
	public  String getCAMPO_PLACPERIODO1() {
		return CAMPO_PLACPERIODO1;
	}
	/**
	 * @return Return the cAMPO_PLACPERIODO2.
	 */
	public  String getCAMPO_PLACPERIODO2() {
		return CAMPO_PLACPERIODO2;
	}
	/**
	 * @return Return the cAMPO_PLACPERIODO3.
	 */
	public  String getCAMPO_PLACPERIODO3() {
		return CAMPO_PLACPERIODO3;
	}
	/**
	 * @return Return the cAMPO_PLACPERIODO4.
	 */
	public  String getCAMPO_PLACPERIODO4() {
		return CAMPO_PLACPERIODO4;
	}
	/**
	 * @return Return the cAMPO_PLACPONDERADO.
	 */
	public  String getCAMPO_PLACPONDERADO() {
		return CAMPO_PLACPONDERADO;
	}
	/**
	 * @return Return the cAMPO_PLACPRESUPUESTO.
	 */
	public  String getCAMPO_PLACPRESUPUESTO() {
		return CAMPO_PLACPRESUPUESTO;
	}
	/**
	 * @return Return the cAMPO_PLACVIGENCIA.
	 */
	public  String getCAMPO_PLACVIGENCIA() {
		return CAMPO_PLACVIGENCIA;
	}
	/**
	 * @return Return the cAMPO_PLFTCODFUFINANC.
	 */
	public  String getCAMPO_PLFTCODFUFINANC() {
		return CAMPO_PLFTCODFUFINANC;
	}
	/**
	 * @return Return the cAMPO_PLFTCODINST.
	 */
	public  String getCAMPO_PLFTCODINST() {
		return CAMPO_PLFTCODINST;
	}
	/**
	 * @return Return the cAMPO_PLFTCODPLAACT.
	 */
	public  String getCAMPO_PLFTCODPLAACT() {
		return CAMPO_PLFTCODPLAACT;
	}
	/**
	 * @return Return the cAMPO_PLFTVIGENCIA.
	 */
	public  String getCAMPO_PLFTVIGENCIA() {
		return CAMPO_PLFTVIGENCIA;
	}
	/**
	 * @return Return the cMD_AJAX_LINEA.
	 */
	public int getCMD_AJAX_LINEA() {
		return CMD_AJAX_LINEA;
	}
	/**
	 * @return Return the tABLA_ACTIVIDAD.
	 */
	public  String getTABLA_ACTIVIDAD() {
		return TABLA_ACTIVIDAD;
	}
	/**
	 * @return Return the tABLA_FINANCIACION.
	 */
	public  String getTABLA_FINANCIACION() {
		return TABLA_FINANCIACION;
	}
	/**
	 * @return Return the cMD_APROBAR_COLEGIO.
	 */
	public int getCMD_APROBAR_COLEGIO() {
		return CMD_APROBAR_COLEGIO;
	}
	/**
	 * @return Return the cMD_APROBAR_SED.
	 */
	public int getCMD_APROBAR_SED() {
		return CMD_APROBAR_SED;
	}
	/**
	 * @return Return the cMD_RECHAZAR_SED.
	 */
	public int getCMD_RECHAZAR_SED() {
		return CMD_RECHAZAR_SED;
	}
	/**
	 * @return Return the cMD_AJAX_COLEGIO.
	 */
	public int getCMD_AJAX_COLEGIO() {
		return CMD_AJAX_COLEGIO;
	}
	/**
	 * @return Return the cMD_BUSCAR_COL.
	 */
	public int getCMD_BUSCAR_COL() {
		return CMD_BUSCAR_COL;
	}
	/**
	 * @return Return the cMD_EDITAR_COL.
	 */
	public int getCMD_EDITAR_COL() {
		return CMD_EDITAR_COL;
	}
	/**
	 * @return Return the cAMPO_PLACFECHAREAL.
	 */
	public final String getCAMPO_PLACFECHAREAL() {
		return CAMPO_PLACFECHAREAL;
	}
	/**
	 * @return Return the cAMPO_PLACLOGRO1.
	 */
	public final String getCAMPO_PLACLOGRO1() {
		return CAMPO_PLACLOGRO1;
	}
	/**
	 * @return Return the cAMPO_PLACLOGRO2.
	 */
	public final String getCAMPO_PLACLOGRO2() {
		return CAMPO_PLACLOGRO2;
	}
	/**
	 * @return Return the cAMPO_PLACLOGRO3.
	 */
	public final String getCAMPO_PLACLOGRO3() {
		return CAMPO_PLACLOGRO3;
	}
	/**
	 * @return Return the cAMPO_PLACLOGRO4.
	 */
	public final String getCAMPO_PLACLOGRO4() {
		return CAMPO_PLACLOGRO4;
	}
	/**
	 * @return Return the cAMPO_PLACPORCENTAJE1.
	 */
	public final String getCAMPO_PLACPORCENTAJE1() {
		return CAMPO_PLACPORCENTAJE1;
	}
	/**
	 * @return Return the cAMPO_PLACPORCENTAJE2.
	 */
	public final String getCAMPO_PLACPORCENTAJE2() {
		return CAMPO_PLACPORCENTAJE2;
	}
	/**
	 * @return Return the cAMPO_PLACPORCENTAJE3.
	 */
	public final String getCAMPO_PLACPORCENTAJE3() {
		return CAMPO_PLACPORCENTAJE3;
	}
	/**
	 * @return Return the cAMPO_PLACPORCENTAJE4.
	 */
	public final String getCAMPO_PLACPORCENTAJE4() {
		return CAMPO_PLACPORCENTAJE4;
	}
	/**
	 * @return Return the cAMPO_PLACSEGUIMIENTO1.
	 */
	public final String getCAMPO_PLACSEGUIMIENTO1() {
		return CAMPO_PLACSEGUIMIENTO1;
	}
	/**
	 * @return Return the cAMPO_PLACSEGUIMIENTO2.
	 */
	public final String getCAMPO_PLACSEGUIMIENTO2() {
		return CAMPO_PLACSEGUIMIENTO2;
	}
	/**
	 * @return Return the cAMPO_PLACSEGUIMIENTO3.
	 */
	public final String getCAMPO_PLACSEGUIMIENTO3() {
		return CAMPO_PLACSEGUIMIENTO3;
	}
	/**
	 * @return Return the cAMPO_PLACSEGUIMIENTO4.
	 */
	public final String getCAMPO_PLACSEGUIMIENTO4() {
		return CAMPO_PLACSEGUIMIENTO4;
	}
	/**
	 * @return Return the cAMPO_PLACVERIFICACION1.
	 */
	public final String getCAMPO_PLACVERIFICACION1() {
		return CAMPO_PLACVERIFICACION1;
	}
	/**
	 * @return Return the cAMPO_PLACVERIFICACION2.
	 */
	public final String getCAMPO_PLACVERIFICACION2() {
		return CAMPO_PLACVERIFICACION2;
	}
	/**
	 * @return Return the cAMPO_PLACVERIFICACION3.
	 */
	public final String getCAMPO_PLACVERIFICACION3() {
		return CAMPO_PLACVERIFICACION3;
	}
	/**
	 * @return Return the cAMPO_PLACVERIFICACION4.
	 */
	public final String getCAMPO_PLACVERIFICACION4() {
		return CAMPO_PLACVERIFICACION4;
	}
}
