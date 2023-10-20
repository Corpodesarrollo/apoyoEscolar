package poa.aprobarCambios.vo;

import java.sql.Timestamp;

import siges.common.vo.Vo;

/**
 * Value Object del manejo del formulario de ingreso/edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class CambioVO  extends Vo{
	
	private int vigencia;
	private int nivel;
	private long entidad;
	
	private String fechaSol;
	private String fechaSol_;
	private String asunto;
	private String solicitud;
	private int estado;
	private String fechaEstado;
	private String nombreEstado;
	private String observacion;
	private String usuario;
	private String usuarioEstado;
	private String disabled;
	private boolean desHabilitado;

	
	public int getVigencia() {
		return vigencia;
	}
	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public long getEntidad() {
		return entidad;
	}
	public void setEntidad(long entidad) {
		this.entidad = entidad;
	}
	public String getFechaSol() {
		return fechaSol;
	}
	public void setFechaSol(String fechaSol) {
		this.fechaSol = fechaSol;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public boolean isDesHabilitado() {
		return desHabilitado;
	}
	public void setDesHabilitado(boolean desHabilitado) {
		this.desHabilitado = desHabilitado;
	}
	
	public String getUsuarioEstado() {
		return usuarioEstado;
	}
	public void setUsuarioEstado(String usuarioEstado) {
		this.usuarioEstado = usuarioEstado;
	}
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	public String getFechaSol_() {
		return fechaSol_;
	}
	public void setFechaSol_(String fechaSol) {
		fechaSol_ = fechaSol;
	}
	
	
	
	
	 
}
