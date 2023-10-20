package poa.seguimiento.vo;

import java.math.BigDecimal;
import java.util.Date;

import siges.common.vo.Vo;

/**
 * Value Object del manejo del formulario de ingreso/edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ArchivoSegVO extends Vo {
	
	private BigDecimal idEvidenciaSeguimiento;
	private String nombreArchivo;
	private String usuarioResponsable;
	private Date fechaCreacion;
		
	
	private String usuarioSesion;
		
	
	public BigDecimal getIdEvidenciaSeguimiento() {
		return idEvidenciaSeguimiento;
	}
	public void setIdEvidenciaSeguimiento(BigDecimal idEvidenciaSeguimiento) {
		this.idEvidenciaSeguimiento = idEvidenciaSeguimiento;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getUsuarioResponsable() {
		return usuarioResponsable;
	}
	public void setUsuarioResponsable(String usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioSesion() {
		return usuarioSesion;
	}
	public void setUsuarioSesion(String usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
	
}
