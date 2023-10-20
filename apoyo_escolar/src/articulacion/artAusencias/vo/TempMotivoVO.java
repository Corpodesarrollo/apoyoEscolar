package articulacion.artAusencias.vo;

import siges.common.vo.Vo;

public class TempMotivoVO extends Vo{

	private long estudiante;
	private String dia;
	private String numDia;
	private int motivo;
//	private int grupo;
	private String descripcion;
	private long[] asignaturas;
	private int[] claseAsig;
	
	public long[] getAsignaturas() {
		return asignaturas;
	}
	public void setAsignaturas(long[] asignaturas) {
		this.asignaturas = asignaturas;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public long getEstudiante() {
		return estudiante;
	}
	public void setEstudiante(long estudiante) {
		this.estudiante = estudiante;
	//	System.out.println(estudiante);
	}
	public int getMotivo() {
		return motivo;
	}
	public void setMotivo(int motivo) {
		this.motivo = motivo;
	//	System.out.println(motivo);
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	//	System.out.println(dia);
	}
	public int[] getClaseAsig() {
		return claseAsig;
	}
	public void setClaseAsig(int[] claseAsig) {
		this.claseAsig = claseAsig;
	}
	public String getNumDia() {
		return numDia;
	}
	public void setNumDia(String numDia) {
		this.numDia = numDia;
	}
}
