package articulacion.importarArticulacion.objetos;

import siges.common.vo.Vo;

public class VaciosVO extends Vo{
	
	private long codigo;

	private long especialidad;
	private long semestre;
	private long grupo;
	private int nivelado1;
	private String nivelado;

	private String abreviatura;
	private String documento;
	private String apellidos1;
	private String apellidos2;
	private String nombre1;
	private String nombre2;
	private String tipoDoc;
	
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getApellidos1() {
		return apellidos1;
	}
	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}
	public String getApellidos2() {
		return apellidos2;
	}
	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
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
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public long getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(long especialidad) {
		this.especialidad = especialidad;
	}
	public String getNivelado() {
		return nivelado;
	}
	public void setNivelado(String nivelado) {
		this.nivelado = nivelado;
	}
	public long getSemestre() {
		return semestre;
	}
	public void setSemestre(long semestre) {
		this.semestre = semestre;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public int getNivelado1() {
		return nivelado1;
	}
	public void setNivelado1(int nivelado1) {
		this.nivelado1 = nivelado1;
	}
	
	

	
	
}
