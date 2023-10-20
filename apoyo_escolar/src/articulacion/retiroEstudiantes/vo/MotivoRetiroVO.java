package articulacion.retiroEstudiantes.vo;

import siges.common.vo.Vo;

public class MotivoRetiroVO extends Vo{

	private long codigo;
	private String motivo;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	
}
