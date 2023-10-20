/**
 * 
 */
package participacion.acta.vo;

/**
 * 30/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroActaVO {
	private int filNivel;
	private int filInstancia;
	private int filRango;
	private int filLocalidad=-1;
	private long filColegio;
	private int filActa;
	
	private int filTieneLocalidad;
	private int filTieneColegio;
	
	/**
	 * @return Return the filActa.
	 */
	public final int getFilActa() {
		return filActa;
	}
	/**
	 * @param filActa The filActa to set.
	 */
	public final void setFilActa(int filActa) {
		this.filActa = filActa;
	}
	/**
	 * @return Return the filColegio.
	 */
	public final long getFilColegio() {
		return filColegio;
	}
	/**
	 * @param filColegio The filColegio to set.
	 */
	public final void setFilColegio(long filColegio) {
		this.filColegio = filColegio;
	}
	/**
	 * @return Return the filInstancia.
	 */
	public final int getFilInstancia() {
		return filInstancia;
	}
	/**
	 * @param filInstancia The filInstancia to set.
	 */
	public final void setFilInstancia(int filInstancia) {
		this.filInstancia = filInstancia;
	}
	/**
	 * @return Return the filLocalidad.
	 */
	public final int getFilLocalidad() {
		return filLocalidad;
	}
	/**
	 * @param filLocalidad The filLocalidad to set.
	 */
	public final void setFilLocalidad(int filLocalidad) {
		this.filLocalidad = filLocalidad;
	}
	/**
	 * @return Return the filNivel.
	 */
	public final int getFilNivel() {
		return filNivel;
	}
	/**
	 * @param filNivel The filNivel to set.
	 */
	public final void setFilNivel(int filNivel) {
		this.filNivel = filNivel;
	}
	/**
	 * @return Return the filRango.
	 */
	public final int getFilRango() {
		return filRango;
	}
	/**
	 * @param filRango The filRango to set.
	 */
	public final void setFilRango(int filRango) {
		this.filRango = filRango;
	}
	/**
	 * @return Return the filTieneColegio.
	 */
	public final int getFilTieneColegio() {
		return filTieneColegio;
	}
	/**
	 * @param filTieneColegio The filTieneColegio to set.
	 */
	public final void setFilTieneColegio(int filTieneColegio) {
		this.filTieneColegio = filTieneColegio;
	}
	/**
	 * @return Return the filTieneLocalidad.
	 */
	public final int getFilTieneLocalidad() {
		return filTieneLocalidad;
	}
	/**
	 * @param filTieneLocalidad The filTieneLocalidad to set.
	 */
	public final void setFilTieneLocalidad(int filTieneLocalidad) {
		this.filTieneLocalidad = filTieneLocalidad;
	}
}
