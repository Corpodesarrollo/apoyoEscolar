/**
 * 
 */
package siges.rotacion.beans;

import siges.common.vo.Vo;

/**
 * 11/08/2007 
 * @author Latined
 * @version 1.2
 */
public class DocXGrupoVO extends Vo{
	private long docGruInstitucion; 
	private int docGruSede; 
	private int docGruJornada; 
	private int docGruMetodologia; 
	private long docGruDocente; 
	private int docGruAsignatura; 
	private int docGruGrado;
	private long docGruJerGrado; 
	private long[] docGruGrupo;
	private String docGruNomAsignatura;
	private String docGruNomGrado;
	private String docGruNomGrupo;
	private int docGruVigencia;
	
	/**
	 * @return Return the docGruAsignatura.
	 */
	public int getDocGruAsignatura() {
		return docGruAsignatura;
	}
	/**
	 * @param docGruAsignatura The docGruAsignatura to set.
	 */
	public void setDocGruAsignatura(int docGruAsignatura) {
		this.docGruAsignatura = docGruAsignatura;
	}
	/**
	 * @return Return the docGruDocente.
	 */
	public long getDocGruDocente() {
		return docGruDocente;
	}
	/**
	 * @param docGruDocente The docGruDocente to set.
	 */
	public void setDocGruDocente(long docGruDocente) {
		this.docGruDocente = docGruDocente;
	}
	/**
	 * @return Return the docGruGrado.
	 */
	public int getDocGruGrado() {
		return docGruGrado;
	}
	/**
	 * @param docGruGrado The docGruGrado to set.
	 */
	public void setDocGruGrado(int docGruGrado) {
		this.docGruGrado = docGruGrado;
	}
	/**
	 * @return Return the docGruInstitucion.
	 */
	public long getDocGruInstitucion() {
		return docGruInstitucion;
	}
	/**
	 * @param docGruInstitucion The docGruInstitucion to set.
	 */
	public void setDocGruInstitucion(long docGruInstitucion) {
		this.docGruInstitucion = docGruInstitucion;
	}
	/**
	 * @return Return the docGruJornada.
	 */
	public int getDocGruJornada() {
		return docGruJornada;
	}
	/**
	 * @param docGruJornada The docGruJornada to set.
	 */
	public void setDocGruJornada(int docGruJornada) {
		this.docGruJornada = docGruJornada;
	}
	/**
	 * @return Return the docGruSede.
	 */
	public int getDocGruSede() {
		return docGruSede;
	}
	/**
	 * @param docGruSede The docGruSede to set.
	 */
	public void setDocGruSede(int docGruSede) {
		this.docGruSede = docGruSede;
	}
	/**
	 * @return Return the docGruMetodologia.
	 */
	public int getDocGruMetodologia() {
		return docGruMetodologia;
	}
	/**
	 * @param docGruMetodologia The docGruMetodologia to set.
	 */
	public void setDocGruMetodologia(int docGruMetodologia) {
		this.docGruMetodologia = docGruMetodologia;
	}
	/**
	 * @return Return the docGruNomAsignatura.
	 */
	public String getDocGruNomAsignatura() {
		return docGruNomAsignatura;
	}
	/**
	 * @param docGruNomAsignatura The docGruNomAsignatura to set.
	 */
	public void setDocGruNomAsignatura(String docGruNomAsignatura) {
		this.docGruNomAsignatura = docGruNomAsignatura;
	}
	/**
	 * @return Return the docGruNomGrado.
	 */
	public String getDocGruNomGrado() {
		return docGruNomGrado;
	}
	/**
	 * @param docGruNomGrado The docGruNomGrado to set.
	 */
	public void setDocGruNomGrado(String docGruNomGrado) {
		this.docGruNomGrado = docGruNomGrado;
	}
	/**
	 * @return Return the docGruNomGrupo.
	 */
	public String getDocGruNomGrupo() {
		return docGruNomGrupo;
	}
	/**
	 * @param docGruNomGrupo The docGruNomGrupo to set.
	 */
	public void setDocGruNomGrupo(String docGruNomGrupo) {
		this.docGruNomGrupo = docGruNomGrupo;
	}
	/**
	 * @return Return the docGruJerGrado.
	 */
	public long getDocGruJerGrado() {
		return docGruJerGrado;
	}
	/**
	 * @param docGruJerGrado The docGruJerGrado to set.
	 */
	public void setDocGruJerGrado(long docGruJerGrado) {
		this.docGruJerGrado = docGruJerGrado;
	}
	/**
	 * @return Return the docGruGrupo.
	 */
	public long[] getDocGruGrupo() {
		return docGruGrupo;
	}
	/**
	 * @param docGruGrupo The docGruGrupo to set.
	 */
	public void setDocGruGrupo(long[] docGruGrupo) {
		this.docGruGrupo = docGruGrupo;
	}
	/**
	 * @return Return the docGruVigencia.
	 */
	public int getDocGruVigencia() {
		return docGruVigencia;
	}
	/**
	 * @param docGruVigencia The docGruVigencia to set.
	 */
	public void setDocGruVigencia(int docGruVigencia) {
		this.docGruVigencia = docGruVigencia;
	}
}
