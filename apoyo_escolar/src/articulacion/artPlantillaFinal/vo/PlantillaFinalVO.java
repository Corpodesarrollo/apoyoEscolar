/**
 * 
 */
package articulacion.artPlantillaFinal.vo;

import siges.common.vo.Vo;

/**
 * 5/12/2007 
 * @author Latined
 * @version 1.2
 */
public class PlantillaFinalVO extends Vo{
	private int plaTipo;
	private long plaInstitucion;
	private int plaMetodologia;
	private int plaSede;
	private int plaJornada;
	private int plaGrado=-99;
	private int plaGrupo=-99;
	private int plaOrden;
	private int plaAnhoVigencia;
	private int plaPerVigencia;
	private int plaSemestre;
	private int plaComponente;
	private long plaEspecialidad;
	private long plaJerarquia;
	private int plaTotalAsignaturas;
	private int plaTotalEstudiantes;
	private float plaNotaMax;
	
	private long plaAsignatura;

	private String plaNombreInstitucion;
	private String plaNombreMetodologia;
	private String plaNombreSede;
	private String plaNombreJornada;
	private String plaNombreGrado;
	private String plaNombreGrupo;
	private String plaNombrePerVigencia;
	private String plaNombreAnhoVigencia;
	private String plaNombreSemestre;
	private String plaNombreComponente;
	private String plaNombreEspecialidad;
	
	private String plaUrl;
	private String plaMensaje;

	/**
	 * @return Return the plaGrado.
	 */
	public int getPlaGrado() {
		return plaGrado;
	}

	/**
	 * @param plaGrado The plaGrado to set.
	 */
	public void setPlaGrado(int plaGrado) {
		this.plaGrado = plaGrado;
	}

	/**
	 * @return Return the plaGrupo.
	 */
	public int getPlaGrupo() {
		return plaGrupo;
	}

	/**
	 * @param plaGrupo The plaGrupo to set.
	 */
	public void setPlaGrupo(int plaGrupo) {
		this.plaGrupo = plaGrupo;
	}

	/**
	 * @return Return the plaInstitucion.
	 */
	public long getPlaInstitucion() {
		return plaInstitucion;
	}

	/**
	 * @param plaInstitucion The plaInstitucion to set.
	 */
	public void setPlaInstitucion(long plaInstitucion) {
		this.plaInstitucion = plaInstitucion;
	}

	/**
	 * @return Return the plaJerarquia.
	 */
	public long getPlaJerarquia() {
		return plaJerarquia;
	}

	/**
	 * @param plaJerarquia The plaJerarquia to set.
	 */
	public void setPlaJerarquia(long plaJerarquia) {
		this.plaJerarquia = plaJerarquia;
	}

	/**
	 * @return Return the plaJornada.
	 */
	public int getPlaJornada() {
		return plaJornada;
	}

	/**
	 * @param plaJornada The plaJornada to set.
	 */
	public void setPlaJornada(int plaJornada) {
		this.plaJornada = plaJornada;
	}

	/**
	 * @return Return the plaMetodologia.
	 */
	public int getPlaMetodologia() {
		return plaMetodologia;
	}

	/**
	 * @param plaMetodologia The plaMetodologia to set.
	 */
	public void setPlaMetodologia(int plaMetodologia) {
		this.plaMetodologia = plaMetodologia;
	}

	/**
	 * @return Return the plaNombreGrado.
	 */
	public String getPlaNombreGrado() {
		return plaNombreGrado;
	}

	/**
	 * @param plaNombreGrado The plaNombreGrado to set.
	 */
	public void setPlaNombreGrado(String plaNombreGrado) {
		this.plaNombreGrado = plaNombreGrado;
	}

	/**
	 * @return Return the plaNombreGrupo.
	 */
	public String getPlaNombreGrupo() {
		return plaNombreGrupo;
	}

	/**
	 * @param plaNombreGrupo The plaNombreGrupo to set.
	 */
	public void setPlaNombreGrupo(String plaNombreGrupo) {
		this.plaNombreGrupo = plaNombreGrupo;
	}

	/**
	 * @return Return the plaNombreInstitucion.
	 */
	public String getPlaNombreInstitucion() {
		return plaNombreInstitucion;
	}

	/**
	 * @param plaNombreInstitucion The plaNombreInstitucion to set.
	 */
	public void setPlaNombreInstitucion(String plaNombreInstitucion) {
		this.plaNombreInstitucion = plaNombreInstitucion;
	}

	/**
	 * @return Return the plaNombreJornada.
	 */
	public String getPlaNombreJornada() {
		return plaNombreJornada;
	}

	/**
	 * @param plaNombreJornada The plaNombreJornada to set.
	 */
	public void setPlaNombreJornada(String plaNombreJornada) {
		this.plaNombreJornada = plaNombreJornada;
	}

	/**
	 * @return Return the plaNombreMetodologia.
	 */
	public String getPlaNombreMetodologia() {
		return plaNombreMetodologia;
	}

	/**
	 * @param plaNombreMetodologia The plaNombreMetodologia to set.
	 */
	public void setPlaNombreMetodologia(String plaNombreMetodologia) {
		this.plaNombreMetodologia = plaNombreMetodologia;
	}

	/**
	 * @return Return the plaNombreSede.
	 */
	public String getPlaNombreSede() {
		return plaNombreSede;
	}

	/**
	 * @param plaNombreSede The plaNombreSede to set.
	 */
	public void setPlaNombreSede(String plaNombreSede) {
		this.plaNombreSede = plaNombreSede;
	}

	/**
	 * @return Return the plaOrden.
	 */
	public int getPlaOrden() {
		return plaOrden;
	}

	/**
	 * @param plaOrden The plaOrden to set.
	 */
	public void setPlaOrden(int plaOrden) {
		this.plaOrden = plaOrden;
	}

	/**
	 * @return Return the plaSede.
	 */
	public int getPlaSede() {
		return plaSede;
	}

	/**
	 * @param plaSede The plaSede to set.
	 */
	public void setPlaSede(int plaSede) {
		this.plaSede = plaSede;
	}

	/**
	 * @return Return the plaTipo.
	 */
	public int getPlaTipo() {
		return plaTipo;
	}

	/**
	 * @param plaTipo The plaTipo to set.
	 */
	public void setPlaTipo(int plaTipo) {
		this.plaTipo = plaTipo;
	}

	/**
	 * @return Return the plaUrl.
	 */
	public String getPlaUrl() {
		return plaUrl;
	}

	/**
	 * @param plaUrl The plaUrl to set.
	 */
	public void setPlaUrl(String plaUrl) {
		this.plaUrl = plaUrl;
	}

	/**
	 * @return Return the plaMensaje.
	 */
	public String getPlaMensaje() {
		return plaMensaje;
	}

	/**
	 * @param plaMensaje The plaMensaje to set.
	 */
	public void setPlaMensaje(String plaMensaje) {
		this.plaMensaje = plaMensaje;
	}

	/**
	 * @return Return the plaAnhoVigencia.
	 */
	public int getPlaAnhoVigencia() {
		return plaAnhoVigencia;
	}

	/**
	 * @param plaAnhoVigencia The plaAnhoVigencia to set.
	 */
	public void setPlaAnhoVigencia(int plaAnhoVigencia) {
		this.plaAnhoVigencia = plaAnhoVigencia;
	}

	/**
	 * @return Return the plaPerVigencia.
	 */
	public int getPlaPerVigencia() {
		return plaPerVigencia;
	}

	/**
	 * @param plaPerVigencia The plaPerVigencia to set.
	 */
	public void setPlaPerVigencia(int plaPerVigencia) {
		this.plaPerVigencia = plaPerVigencia;
	}

	/**
	 * @return Return the plaNombreAnhoVigencia.
	 */
	public String getPlaNombreAnhoVigencia() {
		return plaNombreAnhoVigencia;
	}

	/**
	 * @param plaNombreAnhoVigencia The plaNombreAnhoVigencia to set.
	 */
	public void setPlaNombreAnhoVigencia(String plaNombreAnhoVigencia) {
		this.plaNombreAnhoVigencia = plaNombreAnhoVigencia;
	}

	/**
	 * @return Return the plaNombrePerVigencia.
	 */
	public String getPlaNombrePerVigencia() {
		return plaNombrePerVigencia;
	}

	/**
	 * @param plaNombrePerVigencia The plaNombrePerVigencia to set.
	 */
	public void setPlaNombrePerVigencia(String plaNombrePerVigencia) {
		this.plaNombrePerVigencia = plaNombrePerVigencia;
	}

	/**
	 * @return Return the plaNombreSemestre.
	 */
	public String getPlaNombreSemestre() {
		return plaNombreSemestre;
	}

	/**
	 * @param plaNombreSemestre The plaNombreSemestre to set.
	 */
	public void setPlaNombreSemestre(String plaNombreSemestre) {
		this.plaNombreSemestre = plaNombreSemestre;
	}

	/**
	 * @return Return the plaSemestre.
	 */
	public int getPlaSemestre() {
		return plaSemestre;
	}

	/**
	 * @param plaSemestre The plaSemestre to set.
	 */
	public void setPlaSemestre(int plaSemestre) {
		this.plaSemestre = plaSemestre;
	}

	/**
	 * @return Return the plaComponente.
	 */
	public int getPlaComponente() {
		return plaComponente;
	}

	/**
	 * @param plaComponente The plaComponente to set.
	 */
	public void setPlaComponente(int plaComponente) {
		this.plaComponente = plaComponente;
	}

	/**
	 * @return Return the plaEspecialidad.
	 */
	public long getPlaEspecialidad() {
		return plaEspecialidad;
	}

	/**
	 * @param plaEspecialidad The plaEspecialidad to set.
	 */
	public void setPlaEspecialidad(long plaEspecialidad) {
		this.plaEspecialidad = plaEspecialidad;
	}

	/**
	 * @return Return the plaNombreComponente.
	 */
	public String getPlaNombreComponente() {
		return plaNombreComponente;
	}

	/**
	 * @param plaNombreComponente The plaNombreComponente to set.
	 */
	public void setPlaNombreComponente(String plaNombreComponente) {
		this.plaNombreComponente = plaNombreComponente;
	}

	/**
	 * @return Return the plaNombreEspecialidad.
	 */
	public String getPlaNombreEspecialidad() {
		return plaNombreEspecialidad;
	}

	/**
	 * @param plaNombreEspecialidad The plaNombreEspecialidad to set.
	 */
	public void setPlaNombreEspecialidad(String plaNombreEspecialidad) {
		this.plaNombreEspecialidad = plaNombreEspecialidad;
	}

	/**
	 * @return Return the plaTotalAsignaturas.
	 */
	public int getPlaTotalAsignaturas() {
		return plaTotalAsignaturas;
	}

	/**
	 * @param plaTotalAsignaturas The plaTotalAsignaturas to set.
	 */
	public void setPlaTotalAsignaturas(int plaTotalAsignaturas) {
		this.plaTotalAsignaturas = plaTotalAsignaturas;
	}

	/**
	 * @return Return the plaTotalEstudiantes.
	 */
	public int getPlaTotalEstudiantes() {
		return plaTotalEstudiantes;
	}

	/**
	 * @param plaTotalEstudiantes The plaTotalEstudiantes to set.
	 */
	public void setPlaTotalEstudiantes(int plaTotalEstudiantes) {
		this.plaTotalEstudiantes = plaTotalEstudiantes;
	}

	/**
	 * @return Return the plaNotaMax.
	 */
	public float getPlaNotaMax() {
		return plaNotaMax;
	}

	/**
	 * @param plaNotaMax The plaNotaMax to set.
	 */
	public void setPlaNotaMax(float plaNotaMax) {
		this.plaNotaMax = plaNotaMax;
	}

	public long getPlaAsignatura() {
		return plaAsignatura;
	}

	public void setPlaAsignatura(long plaAsignatura) {
		this.plaAsignatura = plaAsignatura;
	}



}
