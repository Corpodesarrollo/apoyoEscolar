package articulacion.importarEvaluacion.objetos;

import siges.common.vo.Vo;

public class EstudianteVO extends Vo{
	
	private long codigo;
	private long abreviaturaCodigo;
	private long especialidad;
	private long semestre;
	private long grupo;
	private double notanumBasico;
	private double notanumBasico1;
	private double notanumBasico2;
	private double notanumBasico3;
	private double notanumBasico4;
	private double notanum;
	private double notanum0;
	private double notanum1;
	private double notanum2;
	private double notanum3;
	private double notanum4;
	private int nivelado1;
	private int index;
	private String nivelado;
	private String notaC;

	private String abreviatura;
	private String documento;
	private String apellidos1;
	private String apellidos2;
	private String nombre1;
	private String nombre2;
	private String tipoDoc;
	
	private double calculada1;
	private double calculada2;
	private double calculada3;
	private double calculada4;
	private double calculada5;
	
	private float calculadaGeneral;
	
	
	private double porcentajeOne;
	
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
	public long getAbreviaturaCodigo() {
		return abreviaturaCodigo;
	}
	public void setAbreviaturaCodigo(long abreviaturaCodigo) {
		this.abreviaturaCodigo = abreviaturaCodigo;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getNotanum() {
		return notanum;
	}
	public void setNotanum(double notanum) {
		this.notanum = notanum;
	}
	public String getNotaC() {
		return notaC;
	}
	public void setNotaC(String notaC) {
		this.notaC = notaC;
	}
	public double getPorcentajeOne() {
		return porcentajeOne;
	}
	public void setPorcentajeOne(double porcentajeOne) {
		this.porcentajeOne = porcentajeOne;
	}
	public double getNotanum1() {
		return notanum1;
	}
	public void setNotanum1(double notanum1) {
		this.notanum1 = notanum1;
	}
	public double getCalculada1() {
		return calculada1;
	}
	public void setCalculada1(double calculada1) {
		this.calculada1 = calculada1;
	}
	public double getCalculada2() {
		return calculada2;
	}
	public void setCalculada2(double calculada2) {
		this.calculada2 = calculada2;
	}
	public double getCalculada3() {
		return calculada3;
	}
	public void setCalculada3(double calculada3) {
		this.calculada3 = calculada3;
	}
	public double getCalculada4() {
		return calculada4;
	}
	public void setCalculada4(double calculada4) {
		this.calculada4 = calculada4;
	}
	public double getCalculada5() {
		return calculada5;
	}
	public void setCalculada5(double calculada5) {
		this.calculada5 = calculada5;
	}
	public double getNotanum2() {
		return notanum2;
	}
	public void setNotanum2(double notanum2) {
		this.notanum2 = notanum2;
	}
	public double getNotanum3() {
		return notanum3;
	}
	public void setNotanum3(double notanum3) {
		this.notanum3 = notanum3;
	}
	public double getNotanum4() {
		return notanum4;
	}
	public void setNotanum4(double notanum4) {
		this.notanum4 = notanum4;
	}
	public float getCalculadaGeneral() {
		return calculadaGeneral;
	}
	public void setCalculadaGeneral(float calculadaGeneral) {
		this.calculadaGeneral = calculadaGeneral;
	}
	public double getNotanumBasico() {
		return notanumBasico;
	}
	public void setNotanumBasico(double notanumBasico) {
		this.notanumBasico = notanumBasico;
	}
	public double getNotanumBasico1() {
		return notanumBasico1;
	}
	public void setNotanumBasico1(double notanumBasico1) {
		this.notanumBasico1 = notanumBasico1;
	}
	public double getNotanumBasico2() {
		return notanumBasico2;
	}
	public void setNotanumBasico2(double notanumBasico2) {
		this.notanumBasico2 = notanumBasico2;
	}
	public double getNotanumBasico3() {
		return notanumBasico3;
	}
	public void setNotanumBasico3(double notanumBasico3) {
		this.notanumBasico3 = notanumBasico3;
	}
	public double getNotanumBasico4() {
		return notanumBasico4;
	}
	public void setNotanumBasico4(double notanumBasico4) {
		this.notanumBasico4 = notanumBasico4;
	}
	/**
	 * @return Return the notanum0.
	 */
	public double getNotanum0() {
		return notanum0;
	}
	/**
	 * @param notanum0 The notanum0 to set.
	 */
	public void setNotanum0(double notanum0) {
		this.notanum0 = notanum0;
	}
	
	

	
	
}
