/**
 * 
 */
package siges.institucion.correoLider.beans;

import siges.common.vo.Params;

/**
 * 24/08/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
    public static final int FICHA_CORREO=1;
    public static final int FICHA_DEFAULT=FICHA_CORREO;
    public static final int CMD_AJAX_INSTITUCION=11;
    public static final int CMD_AJAX_CARGO=12;

    public static final int CMD_FILE_ADJUNTO=13;

    public static final int TIPO_CARGO_TODOS=-99;
    public static final int TIPO_CARGO_LIDER=1;
    public static final int TIPO_CARGO_GOBIERNO=2;
    public static final int TIPO_CARGO_ASOCIACION=3;
    
    /**
	 * @return Return the fICHA_CORREO.
	 */
	public int getFICHA_CORREO() {
		return FICHA_CORREO;
	}
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the cMD_AJAX_CARGO.
	 */
	public int getCMD_AJAX_CARGO() {
		return CMD_AJAX_CARGO;
	}
	/**
	 * @return Return the cMD_AJAX_INSTITUCION.
	 */
	public int getCMD_AJAX_INSTITUCION() {
		return CMD_AJAX_INSTITUCION;
	}
	/**
	 * @return Return the tIPO_CARGO_ASOCIACION.
	 */
	public int getTIPO_CARGO_ASOCIACION() {
		return TIPO_CARGO_ASOCIACION;
	}
	/**
	 * @return Return the tIPO_CARGO_GOBIERNO.
	 */
	public int getTIPO_CARGO_GOBIERNO() {
		return TIPO_CARGO_GOBIERNO;
	}
	/**
	 * @return Return the tIPO_CARGO_LIDER.
	 */
	public int getTIPO_CARGO_LIDER() {
		return TIPO_CARGO_LIDER;
	}
	/**
	 * @return Return the tIPO_CARGO_TODOS.
	 */
	public int getTIPO_CARGO_TODOS() {
		return TIPO_CARGO_TODOS;
	}
	/**
	 * @return Return the cMD_FILE_ADJUNTO.
	 */
	public int getCMD_FILE_ADJUNTO() {
		return CMD_FILE_ADJUNTO;
	}
}
