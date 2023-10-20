package siges.gestionAdministrativa.agenda.vo;


import siges.common.vo.Vo;

/**
 * Objeto de tipo permiso.
 * AGE_CIRCULAR
 * @author Camaleon
 *
 */
public class PermisosVO extends Vo{
	
	private long estudiante;
	private long codigo;
	private long codgrupo;
	private String fecha;
	private int horaInicio;
	private int horaFin;
	private int minIni;
	private int minFin;
	private int motivo;
	private String motivoString;
	private String observaciones;
	private int estado = 0;
	private String estadoString;

	private String observacionesResponsable;
	private long usuario;
	private String fechaRegistro;
	
	private int nivel = 510; //Padre de FAMILIA;
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getMotivoString() {
		return motivoString;
	}
	public void setMotivoString(String motivoString) {
		this.motivoString = motivoString;
	}
	
	public long getEstudiante() {
		return estudiante;
	}
	public void setEstudiante(long estudiante) {
		this.estudiante = estudiante;
	}
	public long getCodigo() {
		return codigo;
	}
	public String getEstadoString() {
		return estadoString;
	}
	public void setEstadoString(String estadoString) {
		this.estadoString = estadoString;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public long getCodgrupo() {
		return codgrupo;
	}
	public void setCodgrupo(long codgrupo) {
		this.codgrupo = codgrupo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fechaInicio) {
		this.fecha = fechaInicio;
	}
	public int getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public int getMinIni() {
		return minIni;
	}
	public void setMinIni(int minIni) {
		this.minIni = minIni;
	}
	public int getMinFin() {
		return minFin;
	}
	public void setMinFin(int minFin) {
		this.minFin = minFin;
	}
	public int getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}
	public int getMotivo() {
		return motivo;
	}
	public void setMotivo(int motivo) {
		this.motivo = motivo;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getObservacionesResponsable() {
		return observacionesResponsable;
	}
	public void setObservacionesResponsable(String observacionesResponsable) {
		this.observacionesResponsable = observacionesResponsable;
	}
	public long getUsuario() {
		return usuario;
	}
	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
	
}
