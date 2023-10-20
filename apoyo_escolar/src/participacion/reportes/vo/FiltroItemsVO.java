package participacion.reportes.vo;

import siges.common.vo.Vo;

/**
 * Value Object de manejo del formulario de filtro de seguimiento
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroItemsVO  extends Vo{
	private int filNivel;
	private int filInstancia;
	private int filRango;
	private int filRol;
	private int filLocalidad=-1;
	private long filColegio;
	private long filParticipante;
	private int filTieneLocalidad;
	private int filTieneColegio;
	
	
	
	private String filNombreNivel;
	private String filNombreInstancia;
	private String filNombreRango;
	private String filNombreLocalidad;
	private String filNombreColegio;
	
	
	/**
	 * @return Return the filRol.
	 */
	public int getFilRol() {
		return filRol;
	}
	/**
	 * @param filRol The filRol to set.
	 */
	public void setFilRol(int filRol) {
		this.filRol = filRol;
	}
	/**
	 * @return Return the filParticipante.
	 */
	public long getFilParticipante() {
		return filParticipante;
	}
	/**
	 * @param filParticipante The filParticipante to set.
	 */
	public void setFilParticipante(long filParticipante) {
		this.filParticipante = filParticipante;
	}
	
	/**
	 * @return Return the filNivel.
	 */
	public int getFilNivel() {
		return filNivel;
	}
	/**
	 * @param filNivel The filNivel to set.
	 */
	public void setFilNivel(int filNivel) {
		this.filNivel = filNivel;
	}
	/**
	 * @return Return the filInstancia.
	 */
	public int getFilInstancia() {
		return filInstancia;
	}
	/**
	 * @param filInstancia The filInstancia to set.
	 */
	public void setFilInstancia(int filInstancia) {
		this.filInstancia = filInstancia;
	}
	/**
	 * @return Return the filRango.
	 */
	public int getFilRango() {
		return filRango;
	}
	/**
	 * @param filRango The filRango to set.
	 */
	public void setFilRango(int filRango) {
		this.filRango = filRango;
	}
	/**
	 * @return Return the filLocalidad.
	 */
	public int getFilLocalidad() {
		return filLocalidad;
	}
	/**
	 * @param filLocalidad The filLocalidad to set.
	 */
	public void setFilLocalidad(int filLocalidad) {
		this.filLocalidad = filLocalidad;
	}
	/**
	 * @return Return the filColegio.
	 */
	public long getFilColegio() {
		return filColegio;
	}
	/**
	 * @param filColegio The filColegio to set.
	 */
	public void setFilColegio(long filColegio) {
		this.filColegio = filColegio;
	}
	/**
	 * @return Return the filTieneLocalidad.
	 */
	public int getFilTieneLocalidad() {
		return filTieneLocalidad;
	}
	/**
	 * @param filTieneLocalidad The filTieneLocalidad to set.
	 */
	public void setFilTieneLocalidad(int filTieneLocalidad) {
		this.filTieneLocalidad = filTieneLocalidad;
	}
	/**
	 * @return Return the filTieneColegio.
	 */
	public int getFilTieneColegio() {
		return filTieneColegio;
	}
	/**
	 * @param filTieneColegio The filTieneColegio to set.
	 */
	public void setFilTieneColegio(int filTieneColegio) {
		this.filTieneColegio = filTieneColegio;
	}
	/**
	 * @return Return the filNombreNivel.
	 */
	public String getFilNombreNivel() {
		return filNombreNivel;
	}
	/**
	 * @param filNombreNivel The filNombreNivel to set.
	 */
	public void setFilNombreNivel(String filNombreNivel) {
		this.filNombreNivel = filNombreNivel;
	}
	/**
	 * @return Return the filNombreInstancia.
	 */
	public String getFilNombreInstancia() {
		return filNombreInstancia;
	}
	/**
	 * @param filNombreInstancia The filNombreInstancia to set.
	 */
	public void setFilNombreInstancia(String filNombreInstancia) {
		this.filNombreInstancia = filNombreInstancia;
	}
	/**
	 * @return Return the filNombreRango.
	 */
	public String getFilNombreRango() {
		return filNombreRango;
	}
	/**
	 * @param filNombreRango The filNombreRango to set.
	 */
	public void setFilNombreRango(String filNombreRango) {
		this.filNombreRango = filNombreRango;
	}
	/**
	 * @return Return the filNombreLocalidad.
	 */
	public String getFilNombreLocalidad() {
		return filNombreLocalidad;
	}
	/**
	 * @param filNombreLocalidad The filNombreLocalidad to set.
	 */
	public void setFilNombreLocalidad(String filNombreLocalidad) {
		this.filNombreLocalidad = filNombreLocalidad;
	}
	/**
	 * @return Return the filNombreColegio.
	 */
	public String getFilNombreColegio() {
		return filNombreColegio;
	}
	/**
	 * @param filNombreColegio The filNombreColegio to set.
	 */
	public void setFilNombreColegio(String filNombreColegio) {
		this.filNombreColegio = filNombreColegio;
	}

	
	
	
}
