/**
 * 
 */
package siges.evaluacion.beans;

import siges.common.vo.Vo;

/**
 * 1/09/2007 
 * @author Latined
 * @version 1.2
 */
public class FiltroComportamiento extends Vo{
	private long filInstitucion;
	private int filMetodologia;
	private int filSede;
	private int filJornada;
	private int filGrado=-99;
	private int filGrupo=-99;
	private int filPeriodo=-99;
	private int filDimension=-99;
	private int filOrden;
	private int filVigencia;
	private long filJerarquia;
	private String[] eval;
	private int filCerrar;
	private int filLectura;

	private int filTipo;
	private String filNombreInstitucion;
	private String filNombreMetodologia;
	private String filNombreSede;
	private String filNombreJornada;
	private String filNombreGrado;
	private String filNombreGrupo;
	private String filNombrePeriodo;
	private String[] eval3;
	private String[] eval2;
	
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
	 * @return Return the eval.
	 */
	public String[] getEval() {
		return eval;
	}
	/**
	 * @param eval The eval to set.
	 */
	public void setEval(String[] eval) {
		this.eval = eval;
	}
	/**
	 * @return Return the filDimension.
	 */
	public int getFilDimension() {
		return filDimension;
	}
	/**
	 * @param filDimension The filDimension to set.
	 */
	public void setFilDimension(int filDimension) {
		this.filDimension = filDimension;
	}
	/**
	 * @return Return the filCerrar.
	 */
	public int getFilCerrar() {
		return filCerrar;
	}
	/**
	 * @param filCerrar The filCerrar to set.
	 */
	public void setFilCerrar(int filCerrar) {
		this.filCerrar = filCerrar;
	}
	/**
	 * @return Return the filLectura.
	 */
	public int getFilLectura() {
		return filLectura;
	}
	/**
	 * @param filLectura The filLectura to set.
	 */
	public void setFilLectura(int filLectura) {
		this.filLectura = filLectura;
	}
	/**
	 * @return Return the filNombreGrado.
	 */
	public String getFilNombreGrado() {
		return filNombreGrado;
	}
	/**
	 * @param filNombreGrado The filNombreGrado to set.
	 */
	public void setFilNombreGrado(String filNombreGrado) {
		this.filNombreGrado = filNombreGrado;
	}
	/**
	 * @return Return the filNombreGrupo.
	 */
	public String getFilNombreGrupo() {
		return filNombreGrupo;
	}
	/**
	 * @param filNombreGrupo The filNombreGrupo to set.
	 */
	public void setFilNombreGrupo(String filNombreGrupo) {
		this.filNombreGrupo = filNombreGrupo;
	}
	/**
	 * @return Return the filNombreInstitucion.
	 */
	public String getFilNombreInstitucion() {
		return filNombreInstitucion;
	}
	/**
	 * @param filNombreInstitucion The filNombreInstitucion to set.
	 */
	public void setFilNombreInstitucion(String filNombreInstitucion) {
		this.filNombreInstitucion = filNombreInstitucion;
	}
	/**
	 * @return Return the filNombreJornada.
	 */
	public String getFilNombreJornada() {
		return filNombreJornada;
	}
	/**
	 * @param filNombreJornada The filNombreJornada to set.
	 */
	public void setFilNombreJornada(String filNombreJornada) {
		this.filNombreJornada = filNombreJornada;
	}
	/**
	 * @return Return the filNombreMetodologia.
	 */
	public String getFilNombreMetodologia() {
		return filNombreMetodologia;
	}
	/**
	 * @param filNombreMetodologia The filNombreMetodologia to set.
	 */
	public void setFilNombreMetodologia(String filNombreMetodologia) {
		this.filNombreMetodologia = filNombreMetodologia;
	}
	/**
	 * @return Return the filNombrePeriodo.
	 */
	public String getFilNombrePeriodo() {
		return filNombrePeriodo;
	}
	/**
	 * @param filNombrePeriodo The filNombrePeriodo to set.
	 */
	public void setFilNombrePeriodo(String filNombrePeriodo) {
		this.filNombrePeriodo = filNombrePeriodo;
	}
	/**
	 * @return Return the filNombreSede.
	 */
	public String getFilNombreSede() {
		return filNombreSede;
	}
	/**
	 * @param filNombreSede The filNombreSede to set.
	 */
	public void setFilNombreSede(String filNombreSede) {
		this.filNombreSede = filNombreSede;
	}
	/**
	 * @return Return the filTipo.
	 */
	public int getFilTipo() {
		return filTipo;
	}
	/**
	 * @param filTipo The filTipo to set.
	 */
	public void setFilTipo(int filTipo) {
		this.filTipo = filTipo;
	}
	
	public String[] getEval3() {
		return eval3;
	}
	public void setEval3(String[] eval3) {
		this.eval3 = eval3;
	}
	public String[] getEval2() {
		return eval2;
	}
	public void setEval2(String[] eval2) {
		this.eval2 = eval2;
	}
	
}
