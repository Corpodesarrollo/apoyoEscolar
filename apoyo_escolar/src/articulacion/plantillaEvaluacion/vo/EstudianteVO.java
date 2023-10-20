package articulacion.plantillaEvaluacion.vo;

import siges.common.vo.Vo;

public class EstudianteVO extends Vo{

	
	private long codigo;
	private String documento;
	private String nombre;
	private String apellido;
	private String nombre1;
	private String apellido1;
	private float notaNum1;
	private float notaNum2;
	private float notaNum3;
	private float notaNum4;
	private float notaNum5;
	private boolean estado;
	

	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return (nombre!=null)? nombre : "";
		
	}
	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}
	public String getApellido() {
		return (apellido!=null)? apellido : "";
		
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDocumento() {
		return (documento!=null)? documento: "";
		
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getApellido1() {
		return (apellido1!=null)? apellido1: "";
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getNombre1() {
		return (nombre1!=null)? nombre1 : "";
		
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	/**
	 * @return Return the notaNum1.
	 */
	public float getNotaNum1() {
		return notaNum1;
	}
	/**
	 * @param notaNum1 The notaNum1 to set.
	 */
	public void setNotaNum1(float notaNum1) {
		this.notaNum1 = notaNum1;
	}
	/**
	 * @return Return the notaNum2.
	 */
	public float getNotaNum2() {
		return notaNum2;
	}
	/**
	 * @param notaNum2 The notaNum2 to set.
	 */
	public void setNotaNum2(float notaNum2) {
		this.notaNum2 = notaNum2;
	}
	/**
	 * @return Return the notaNum3.
	 */
	public float getNotaNum3() {
		return notaNum3;
	}
	/**
	 * @param notaNum3 The notaNum3 to set.
	 */
	public void setNotaNum3(float notaNum3) {
		this.notaNum3 = notaNum3;
	}
	/**
	 * @return Return the notaNum4.
	 */
	public float getNotaNum4() {
		return notaNum4;
	}
	/**
	 * @param notaNum4 The notaNum4 to set.
	 */
	public void setNotaNum4(float notaNum4) {
		this.notaNum4 = notaNum4;
	}
	/**
	 * @return Return the notaNum5.
	 */
	public float getNotaNum5() {
		return notaNum5;
	}
	/**
	 * @param notaNum5 The notaNum5 to set.
	 */
	public void setNotaNum5(float notaNum5) {
		this.notaNum5 = notaNum5;
	}
	
}
