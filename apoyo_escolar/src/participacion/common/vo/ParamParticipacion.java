/**
 * 
 */
package participacion.common.vo;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamParticipacion {
	public static final int NIVEL_INSTANCIA_DISTRITAL=1;
	public static final int NIVEL_INSTANCIA_CENTRAL=2;
	public static final int NIVEL_INSTANCIA_LOCAL=3;
	public static final int NIVEL_INSTANCIA_COLEGIO=4;

	public static final String NIVEL_INSTANCIA_DISTRITAL_="Nivel Distrital";
	public static final String NIVEL_INSTANCIA_CENTRAL_="Nivel Central";
	public static final String NIVEL_INSTANCIA_LOCAL_="Nivel Local";
	public static final String NIVEL_INSTANCIA_COLEGIO_="Nivel Colegio";

	public static final int RANGO_ACTIVO=1;
	public static final int RANGO_INACTIVO=0;

	public static final int LOGIN_NIVEL_DEPARTAMENTO=1;
	public static final int LOGIN_NIVEL_LOCALIDAD=2;
	public static final int LOGIN_NIVEL_COLEGIO=4;
	public static final int LOGIN_NIVEL_SEDE_JORNADA=6;
	/**
	 * @return Return the lOGIN_NIVEL_COLEGIO.
	 */
	public int getLOGIN_NIVEL_COLEGIO() {
		return LOGIN_NIVEL_COLEGIO;
	}
	/**
	 * @return Return the lOGIN_NIVEL_DEPARTAMENTO.
	 */
	public int getLOGIN_NIVEL_DEPARTAMENTO() {
		return LOGIN_NIVEL_DEPARTAMENTO;
	}
	/**
	 * @return Return the lOGIN_NIVEL_LOCALIDAD.
	 */
	public int getLOGIN_NIVEL_LOCALIDAD() {
		return LOGIN_NIVEL_LOCALIDAD;
	}
	/**
	 * @return Return the lOGIN_NIVEL_SEDE_JORNADA.
	 */
	public int getLOGIN_NIVEL_SEDE_JORNADA() {
		return LOGIN_NIVEL_SEDE_JORNADA;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_CENTRAL.
	 */
	public int getNIVEL_INSTANCIA_CENTRAL() {
		return NIVEL_INSTANCIA_CENTRAL;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_CENTRAL_.
	 */
	public String getNIVEL_INSTANCIA_CENTRAL_() {
		return NIVEL_INSTANCIA_CENTRAL_;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_COLEGIO.
	 */
	public int getNIVEL_INSTANCIA_COLEGIO() {
		return NIVEL_INSTANCIA_COLEGIO;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_COLEGIO_.
	 */
	public String getNIVEL_INSTANCIA_COLEGIO_() {
		return NIVEL_INSTANCIA_COLEGIO_;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_DISTRITAL.
	 */
	public int getNIVEL_INSTANCIA_DISTRITAL() {
		return NIVEL_INSTANCIA_DISTRITAL;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_DISTRITAL_.
	 */
	public String getNIVEL_INSTANCIA_DISTRITAL_() {
		return NIVEL_INSTANCIA_DISTRITAL_;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_LOCAL.
	 */
	public int getNIVEL_INSTANCIA_LOCAL() {
		return NIVEL_INSTANCIA_LOCAL;
	}
	/**
	 * @return Return the nIVEL_INSTANCIA_LOCAL_.
	 */
	public String getNIVEL_INSTANCIA_LOCAL_() {
		return NIVEL_INSTANCIA_LOCAL_;
	}
	/**
	 * @return Return the rANGO_ACTIVO.
	 */
	public int getRANGO_ACTIVO() {
		return RANGO_ACTIVO;
	}
	/**
	 * @return Return the rANGO_INACTIVO.
	 */
	public int getRANGO_INACTIVO() {
		return RANGO_INACTIVO;
	}
}
