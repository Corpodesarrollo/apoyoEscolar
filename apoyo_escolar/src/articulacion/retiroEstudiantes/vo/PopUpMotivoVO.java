package articulacion.retiroEstudiantes.vo;

import siges.common.vo.Vo;

public class PopUpMotivoVO extends Vo{
	private long codigoMotivo;
	private long codigoEstudiante;
	private String nombreMotivo;
	private String descripcion;
	private String fecha;

	private long codigoAsignatura;
	private long codigoGrupo;
	private int indice;
	
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public String getNombreMotivo() {
		return nombreMotivo;
	}
	public void setNombreMotivo(String nombreMotivo) {
		this.nombreMotivo = nombreMotivo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public long getCodigoAsignatura() {
		return codigoAsignatura;
	}
	public void setCodigoAsignatura(long codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}
	public long getCodigoGrupo() {
		return codigoGrupo;
	}
	public void setCodigoGrupo(long codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}
	public long getCodigoMotivo() {
		return codigoMotivo;
	}
	public void setCodigoMotivo(long codigoMotivo) {
		this.codigoMotivo = codigoMotivo;
	}
	public long getCodigoEstudiante() {
		return codigoEstudiante;
	}
	public void setCodigoEstudiante(long codigoEstudiante) {
		this.codigoEstudiante = codigoEstudiante;
	}
}
