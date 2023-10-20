package siges.institucion.organizacion.beans;

public class Params{
    public static final int FICHA_GOB=1;
    public static final int FICHA_GOB2=11;
    public static final int FICHA_GOB3=12;
    public static final int FICHA_GOB4=13;
    public static final int FICHA_ASO=2;
    public static final int FICHA_ASO2=21;
    public static final int FICHA_LID=3;
    public static final int FICHA_LID2=31;
    public static final int FILTRO_LID=39;
    public static final int FICHA_DEFAULT=FICHA_LID;
    
    public static final int PARAM_PERSONERO=1;
    public static final int PARAM_CONTRALOR=2;
    public static final int PARAM_PRESIDENTE=3;
    public static final int PARAM_CONSEJO=4;

    public static final String PARAM_PERSONERO_="Personero estudiantil";
    public static final String PARAM_CONTRALOR_="Contralor";
    public static final String PARAM_PRESIDENTE_="Presidente del consejo estudiantil";
    public static final String PARAM_CONSEJO_="Integrante del consejo estudiantil";
    
    public static final int PARAM_SEDE=1;
    public static final int PARAM_SEDE_JORNADA=2;
    
    public int getFICHA_GOB() {
        return FICHA_GOB;
    }
    public int getFICHA_ASO() {
        return FICHA_ASO;
    }
    public int getFICHA_LID() {
        return FICHA_LID;
    }
    public int getFICHA_DEFAULT() {
        return FICHA_DEFAULT;
    }
	public int getFICHA_ASO2() {
		return FICHA_ASO2;
	}
	public int getFICHA_GOB2() {
		return FICHA_GOB2;
	}
	public int getFICHA_LID2() {
		return FICHA_LID2;
	}
	public int getFICHA_GOB3() {
		return FICHA_GOB3;
	}
	public int getFICHA_GOB4() {
		return FICHA_GOB4;
	}
	public int getFILTRO_LID() {
		return FILTRO_LID;
	}
	public int getPARAM_CONSEJO() {
		return PARAM_CONSEJO;
	}
	public int getPARAM_CONTRALOR() {
		return PARAM_CONTRALOR;
	}
	public int getPARAM_PERSONERO() {
		return PARAM_PERSONERO;
	}
	public int getPARAM_PRESIDENTE() {
		return PARAM_PRESIDENTE;
	}
	public int getPARAM_SEDE() {
		return PARAM_SEDE;
	}
	public int getPARAM_SEDE_JORNADA() {
		return PARAM_SEDE_JORNADA;
	}
}
