package articulacion.horarioArticulacion.vo;

import siges.common.vo.Vo;

public class FiltroHorarioVO extends Vo{
	private long filInstitucion;
	private int filSede;
	private int filJornada;
	private int filAnhoVigencia;
	private int filPerVigencia;
	private int filComponente;
	private int filEspecialidad;
	private int filSemestre;
	private int filAsignatura;
	private int filGrupo;
	private int filDocente;
	private ParametroVO parametroVO;
	private int filIH;
	private int filDisabled;//caso en el que si vale 1 es porque no se debe dejar registar nada
	
	/**
	 * @return Returns the filAnhoVigencia.
	 */
	public int getFilAnhoVigencia() {
		return filAnhoVigencia;
	}
	/**
	 * @param filAnhoVigencia The filAnhoVigencia to set.
	 */
	public void setFilAnhoVigencia(int filAnhoVigencia) {
		this.filAnhoVigencia = filAnhoVigencia;
	}
	/**
	 * @return Returns the filAsignatura.
	 */
	public int getFilAsignatura() {
		return filAsignatura;
	}
	/**
	 * @param filAsignatura The filAsignatura to set.
	 */
	public void setFilAsignatura(int filAsignatura) {
		this.filAsignatura = filAsignatura;
	}
	/**
	 * @return Returns the filComponente.
	 */
	public int getFilComponente() {
		return filComponente;
	}
	/**
	 * @param filComponente The filComponente to set.
	 */
	public void setFilComponente(int filComponente) {
		this.filComponente = filComponente;
	}
	/**
	 * @return Returns the filDocente.
	 */
	public int getFilDocente() {
		return filDocente;
	}
	/**
	 * @param filDocente The filDocente to set.
	 */
	public void setFilDocente(int filDocente) {
		this.filDocente = filDocente;
	}
	/**
	 * @return Returns the filGrupo.
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
	 * @return Returns the filInstitucion.
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
	 * @return Returns the filJornada.
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
	 * @return Returns the filPerVigencia.
	 */
	public int getFilPerVigencia() {
		return filPerVigencia;
	}
	/**
	 * @param filPerVigencia The filPerVigencia to set.
	 */
	public void setFilPerVigencia(int filPerVigencia) {
		this.filPerVigencia = filPerVigencia;
	}
	/**
	 * @return Returns the filSede.
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
	 * @return Returns the filSemestre.
	 */
	public int getFilSemestre() {
		return filSemestre;
	}
	/**
	 * @param filSemestre The filSemestre to set.
	 */
	public void setFilSemestre(int filSemestre) {
		this.filSemestre = filSemestre;
	}
	/**
	 * @return Returns the filEspecialidad.
	 */
	public int getFilEspecialidad() {
		return filEspecialidad;
	}
	/**
	 * @param filEspecialidad The filEspecialidad to set.
	 */
	public void setFilEspecialidad(int filEspecialidad) {
		this.filEspecialidad = filEspecialidad;
	}
	/**
	 * @return Returns the parametroVO.
	 */
	public ParametroVO getParametroVO() {
		return parametroVO;
	}
	/**
	 * @param parametroVO The parametroVO to set.
	 */
	public void setParametroVO(ParametroVO parametroVO) {
		this.parametroVO = parametroVO;
	}
	/**
	 * @return Returns the filIH.
	 */
	public int getFilIH() {
		return filIH;
	}
	/**
	 * @param filIH The filIH to set.
	 */
	public void setFilIH(int filIH) {
		this.filIH = filIH;
	}
	/**
	 * @return Returns the filDisabled.
	 */
	public int getFilDisabled() {
		return filDisabled;
	}
	/**
	 * @param filDisabled The filDisabled to set.
	 */
	public void setFilDisabled(int filDisabled) {
		this.filDisabled = filDisabled;
	}
	
}
