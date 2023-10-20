package pei.registro.vo;

import siges.common.vo.Vo;

public class AjusteVO extends Vo {
	private Long ajuCodigoInstitucion;
	private Long ajuResolucion;
	private int ajuVigencia;
	private String ajuAjuste;
	private String ajuFecha;
	private String ajuUsuario;
	
	public Long getAjuCodigoInstitucion() {
		return ajuCodigoInstitucion;
	}
	public void setAjuCodigoInstitucion(Long ajuCodigoInstitucion) {
		this.ajuCodigoInstitucion = ajuCodigoInstitucion;
	}
	public Long getAjuResolucion() {
		return ajuResolucion;
	}
	public void setAjuResolucion(Long ajuResolucion) {
		this.ajuResolucion = ajuResolucion;
	}
	public int getAjuVigencia() {
		return ajuVigencia;
	}
	public void setAjuVigencia(int ajuVigencia) {
		this.ajuVigencia = ajuVigencia;
	}
	public String getAjuAjuste() {
		return ajuAjuste;
	}
	public void setAjuAjuste(String ajuAjuste) {
		this.ajuAjuste = ajuAjuste;
	}
	public String getAjuFecha() {
		return ajuFecha;
	}
	public void setAjuFecha(String ajuFecha) {
		this.ajuFecha = ajuFecha;
	}
	public String getAjuUsuario() {
		return ajuUsuario;
	}
	public void setAjuUsuario(String ajuUsuario) {
		this.ajuUsuario = ajuUsuario;
	}
	
	
	
}
