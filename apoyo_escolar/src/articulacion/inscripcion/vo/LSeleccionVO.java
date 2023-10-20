package articulacion.inscripcion.vo;

public class LSeleccionVO {
	long asignatura;
	long grupo;
	long estudiante;
	
	
	public LSeleccionVO(long estudiante,long asignatura, long grupo) {
		this.estudiante = estudiante;		
		this.asignatura = asignatura;
		this.grupo = grupo;
	}
	/**
	 * @return Returns the asignatura.
	 */
	public long getAsignatura() {
		return asignatura;
	}
	/**
	 * @param asignatura The asignatura to set.
	 */
	public void setAsignatura(long asignatura) {
		this.asignatura = asignatura;
	}
	/**
	 * @return Returns the grupo.
	 */
	public long getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo The grupo to set.
	 */
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}
	/**
	 * @return Returns the estudiante.
	 */
	public long getEstudiante() {
		return estudiante;
	}
	/**
	 * @param estudiante The estudiante to set.
	 */
	public void setEstudiante(long estudiante) {
		this.estudiante = estudiante;
	}
}
