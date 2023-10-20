package siges.ruta.vo;

public class ParamsVO implements Cloneable {
	public static final String SMS="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
    public static final int FICHA_NUTRICION_FILTRO=1;
    public static final int FICHA_NUTRICION_RESULTADO=11;
    public static final int FICHA_NUTRICION_GUARDAR=12;
    public static final int FICHA_GESTACION_FILTRO=2;
    public static final int FICHA_GESTACION_RESULTADO=21;
    public static final int FICHA_GESTACION_GUARDAR=22;
    public static final int FICHA_DEFAULT=FICHA_NUTRICION_FILTRO;
	
    /**
   	*	Hace una copia del objeto mismo
   	*	@return Object 
   	*/
    public Object clone() {
        Object o = null;
        try{
            o = super.clone();
        }
        catch(CloneNotSupportedException e) {
            System.err.println("MyObject can't clone");
        }
        return o;
    }

	/**
	 * @return Returns the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	public int getFICHA_GESTACION_FILTRO() {
		return FICHA_GESTACION_FILTRO;
	}

	public int getFICHA_GESTACION_GUARDAR() {
		return FICHA_GESTACION_GUARDAR;
	}

	public int getFICHA_GESTACION_RESULTADO() {
		return FICHA_GESTACION_RESULTADO;
	}

	public int getFICHA_NUTRICION_FILTRO() {
		return FICHA_NUTRICION_FILTRO;
	}

	public int getFICHA_NUTRICION_RESULTADO() {
		return FICHA_NUTRICION_RESULTADO;
	}

	/**
	 * @return Returns the sMS.
	 */
	public String getSMS() {
		return SMS;
	}

	/**
	 * @return Returns the fICHA_NUTRICION_GUARDAR.
	 */
	public int getFICHA_NUTRICION_GUARDAR() {
		return FICHA_NUTRICION_GUARDAR;
	}
	
}
