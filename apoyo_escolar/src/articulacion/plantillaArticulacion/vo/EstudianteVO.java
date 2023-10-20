package articulacion.plantillaArticulacion.vo;

import siges.common.vo.Vo;

public class EstudianteVO extends Vo{
	private int codigo;
	private String abreviatura;
	private String documento;
	private String apellidos1;
	private String apellidos2;
	private String nombre1;
	private String nombre2;
	private long grupo;
	private long abreviaturaCodigo;
	private String especialidad;
	private String semestre;
	private String nivelado;
	
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getApellidos1() {
		return (apellidos1!=null)? apellidos1: "";
		//return apellidos1;
	}
	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}
	public String getApellidos2() {
		return (apellidos2!=null)? apellidos2: "";
		//return apellidos2;
	}
	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public long getGrupo() {
		return grupo;
	}
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}
	public String getNombre1() {
		return (nombre1!=null)? nombre1 : "";
		
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	public String getNombre2() {
		return (nombre2!=null)? nombre2 : "";
		
	}
	public void setNombre2(String nombre2) {
		
		
		this.nombre2 = nombre2;
	}
	public long getAbreviaturaCodigo() {
		return abreviaturaCodigo;
	}
	public void setAbreviaturaCodigo(long abreviaturaCodigo) {
		this.abreviaturaCodigo = abreviaturaCodigo;
	}
	/**
	 * @return Return the especialidad.
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
	/**
	 * @return Return the nivelado.
	 */
	public String getNivelado() {
		return nivelado;
	}
	/**
	 * @param nivelado The nivelado to set.
	 */
	public void setNivelado(String nivelado) {
		this.nivelado = nivelado;
	}
	/**
	 * @return Return the semestre.
	 */
	public String getSemestre() {
		return semestre;
	}
	/**
	 * @param semestre The semestre to set.
	 */
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
	

	
	
}
