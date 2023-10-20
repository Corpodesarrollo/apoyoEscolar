/**
 * 
 */
package siges.gestionAcademica.inasistencia.vo;

import siges.common.vo.Vo;

/**
 * 3/09/2007 
 * @author Latined
 * @version 1.2
 */
public class FiltroInasistencia extends Vo{
	private long filInstitucion;
	private int filMetodologia;
	private int filSede;
	private int filJornada;
	private int filGrado=-99;
	private int filGrupo=-99;
	private int filPeriodo=-99;
	private int filOrden;
	private int filVigencia;
	private long filJerarquia;
	private int filMes;
	private int filDia;
	private int filTotalDias;
	private String []falla;	
	private int filSabado;
	private int filDomingo;
	private long filAsignatura;
	private long filQuincena;
	/**
	 * @return Return the filGrado.
	 */
	public int getFilGrado() {
		return filGrado;
	}
	/**
	 * @param filGrado The filGrado to set.
	 */
	public void setFilGrado(int filGrado) {
		this.filGrado = filGrado;
	}
	/**
	 * @return Return the filGrupo.
	 */
	public int getFilGrupo() {
		return filGrupo;
	}
	/**
	 * @param filGrupo The filGrupo to set.
	 */
	public void setFilGrupo(int filGrupo) {
		this.filGrupo = filGrupo;
	}
	/**
	 * @return Return the filInstitucion.
	 */
	public long getFilInstitucion() {
		return filInstitucion;
	}
	/**
	 * @param filInstitucion The filInstitucion to set.
	 */
	public void setFilInstitucion(long filInstitucion) {
		this.filInstitucion = filInstitucion;
	}
	/**
	 * @return Return the filJerarquia.
	 */
	public long getFilJerarquia() {
		return filJerarquia;
	}
	/**
	 * @param filJerarquia The filJerarquia to set.
	 */
	public void setFilJerarquia(long filJerarquia) {
		this.filJerarquia = filJerarquia;
	}
	/**
	 * @return Return the filJornada.
	 */
	public int getFilJornada() {
		return filJornada;
	}
	/**
	 * @param filJornada The filJornada to set.
	 */
	public void setFilJornada(int filJornada) {
		this.filJornada = filJornada;
	}
	/**
	 * @return Return the filMes.
	 */
	public int getFilMes() {
		return filMes;
	}
	/**
	 * @param filMes The filMes to set.
	 */
	public void setFilMes(int filMes) {
		this.filMes = filMes;
	}
	/**
	 * @return Return the filMetodologia.
	 */
	public int getFilMetodologia() {
		return filMetodologia;
	}
	/**
	 * @param filMetodologia The filMetodologia to set.
	 */
	public void setFilMetodologia(int filMetodologia) {
		this.filMetodologia = filMetodologia;
	}
	/**
	 * @return Return the filOrden.
	 */
	public int getFilOrden() {
		return filOrden;
	}
	/**
	 * @param filOrden The filOrden to set.
	 */
	public void setFilOrden(int filOrden) {
		this.filOrden = filOrden;
	}
	/**
	 * @return Return the filPeriodo.
	 */
	public int getFilPeriodo() {
		return filPeriodo;
	}
	/**
	 * @param filPeriodo The filPeriodo to set.
	 */
	public void setFilPeriodo(int filPeriodo) {
		this.filPeriodo = filPeriodo;
	}
	/**
	 * @return Return the filSede.
	 */
	public int getFilSede() {
		return filSede;
	}
	/**
	 * @param filSede The filSede to set.
	 */
	public void setFilSede(int filSede) {
		this.filSede = filSede;
	}
	/**
	 * @return Return the filVigencia.
	 */
	public int getFilVigencia() {
		return filVigencia;
	}
	/**
	 * @param filVigencia The filVigencia to set.
	 */
	public void setFilVigencia(int filVigencia) {
		this.filVigencia = filVigencia;
	}
	/**
	 * @return Return the filTotalDias.
	 */
	public int getFilTotalDias() {
		return filTotalDias;
	}
	/**
	 * @param filTotalDias The filTotalDias to set.
	 */
	public void setFilTotalDias(int filTotalDias) {
		this.filTotalDias = filTotalDias;
	}
	/**
	 * @return Return the filDia.
	 */
	public int getFilDia() {
		return filDia;
	}
	/**
	 * @param filDia The filDia to set.
	 */
	public void setFilDia(int filDia) {
		this.filDia = filDia;
	}
	/**
	 * @return Return the falla.
	 */
	public String[] getFalla() {
		return falla;
	}
	/**
	 * @param falla The falla to set.
	 */
	public void setFalla(String[] falla) {
		this.falla = falla;
	}
	/**
	 * @return Return the filDomingo.
	 */
	public int getFilDomingo() {
		return filDomingo;
	}
	/**
	 * @param filDomingo The filDomingo to set.
	 */
	public void setFilDomingo(int filDomingo) {
		this.filDomingo = filDomingo;
	}
	/**
	 * @return Return the filSabado.
	 */
	public int getFilSabado() {
		return filSabado;
	}
	/**
	 * @param filSabado The filSabado to set.
	 */
	public void setFilSabado(int filSabado) {
		this.filSabado = filSabado;
	}
	/**
	 * @return Return the filAsignatura.
	 */
	public long getFilAsignatura() {
		return filAsignatura;
	}
	/**
	 * @param filAsignatura The filAsignatura to set.
	 */
	public void setFilAsignatura(long filAsignatura) {
		this.filAsignatura = filAsignatura;
	}
	public long getFilQuincena() {
		return filQuincena;
	}
	public void setFilQuincena(long filQuincena) {
		this.filQuincena = filQuincena;
	}
}
