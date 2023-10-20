package siges.login.beans;

import siges.common.vo.Params;

public class ParamsVO  extends Params {
	public final static int TIPO_ALERTA=10;
	public final static int TIPO_MENSAJE=12;
	
	public static final int ENV_SISTEM = 1;
	public static final int ENV_SED = 2;
	public static final int ENV_LOC = 3;
	public static final int ENV_INST = 4;
	
	public static final String ENV_SISTEM_TEXT = "Sistema";
	public static final String ENV_SED_TEXT = "SED";
	public static final String ENV_LOC_TEXT = "LOCALIDAD";
	public static final String ENV_INST_TEXT = "COLEGIO";
	
	
	public   int getTIPO_ALERTA() {
		return TIPO_ALERTA;
	}
	
	public   int getTIPO_MENSAJE() {
		return TIPO_MENSAJE;
	}

	public   int getENV_INST() {
		return ENV_INST;
	}

	public   int getENV_LOC() {
		return ENV_LOC;
	}

	public   int getENV_SED() {
		return ENV_SED;
	}

	public   int getENV_SISTEM() {
		return ENV_SISTEM;
	}

	public   String getENV_INST_TEXT() {
		return ENV_INST_TEXT;
	}

	public   String getENV_LOC_TEXT() {
		return ENV_LOC_TEXT;
	}

	public   String getENV_SED_TEXT() {
		return ENV_SED_TEXT;
	}

	public   String getENV_SISTEM_TEXT() {
		return ENV_SISTEM_TEXT;
	}
	
}
