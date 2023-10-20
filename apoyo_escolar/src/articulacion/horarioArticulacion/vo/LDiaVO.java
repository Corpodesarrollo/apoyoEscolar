package articulacion.horarioArticulacion.vo;

public class LDiaVO {
	private boolean check;
	private int dia;
	private long esp;
	private String asignatura;
	private String docente;
	private String grupo;
	private String espacio;
	private String especialidad;
	private long idAsignatura;
	private long idGrupo;
	private long idDocente;
	
	private String checked;//si esta checkeado
	private String disabled;//si esta habilitado
	private String style;//si esta checkeado
	
	/**
	 * @return Returns the check.
	 */
	public boolean isCheck() {
		return check;
	}
	/**
	 * @param check The check to set.
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}
	/**
	 * @return Returns the dia.
	 */
	public int getDia() {
		return dia;
	}
	/**
	 * @param dia The dia to set.
	 */
	public void setDia(int dia) {
		this.dia = dia;
	}
	/**
	 * @return Returns the docente.
	 */
	public String getDocente() {
		return docente;
	}
	/**
	 * @param docente The docente to set.
	 */
	public void setDocente(String docente) {
		this.docente = docente;
	}
	/**
	 * @return Returns the espacio.
	 */
	public String getEspacio() {
		return espacio;
	}
	/**
	 * @param espacio The espacio to set.
	 */
	public void setEspacio(String espacio) {
		this.espacio = espacio;
	}
	/**
	 * @return Returns the asignatura.
	 */
	public String getAsignatura() {
		return asignatura;
	}
	/**
	 * @param asignatura The asignatura to set.
	 */
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	/**
	 * @return Returns the checked.
	 */
	public String getChecked() {
		return checked;
	}
	/**
	 * @param checked The checked to set.
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}
	/**
	 * @return Returns the disabled.
	 */
	public String getDisabled() {
		return disabled;
	}
	/**
	 * @param disabled The disabled to set.
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	/**
	 * @return Returns the esp.
	 */
	public long getEsp() {
		return esp;
	}
	/**
	 * @param esp The esp to set.
	 */
	public void setEsp(long esp) {
		this.esp = esp;
	}
	/**
	 * @return Returns the grupo.
	 */
	public String getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo The grupo to set.
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	/**
	 * @return Returns the idAsignatura.
	 */
	public long getIdAsignatura() {
		return idAsignatura;
	}
	/**
	 * @param idAsignatura The idAsignatura to set.
	 */
	public void setIdAsignatura(long idAsignatura) {
		this.idAsignatura = idAsignatura;
	}
	/**
	 * @return Returns the idDocente.
	 */
	public long getIdDocente() {
		return idDocente;
	}
	/**
	 * @param idDocente The idDocente to set.
	 */
	public void setIdDocente(long idDocente) {
		this.idDocente = idDocente;
	}
	/**
	 * @return Returns the idGrupo.
	 */
	public long getIdGrupo() {
		return idGrupo;
	}
	/**
	 * @param idGrupo The idGrupo to set.
	 */
	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}
	/**
	 * @return Returns the style.
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style The style to set.
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return Returns the especialidad.
	 */
	public String getEspecialidad() {
		return especialidad;
	}
	/**
	 * @param especialidad The especialidad to set.
	 */
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
}
