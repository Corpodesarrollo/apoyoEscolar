package articulacion.asigGrupo.vo;

import siges.common.vo.Vo;

public class GrupoVO extends Vo{

	private long gruJerGrupo;
	private int gruCodigo;
	private String gruNombre;
	
	public long getGruJerGrupo() {
		return gruJerGrupo;
	}
	public void setGruJerGrupo(long gruJerGrupo) {
		this.gruJerGrupo = gruJerGrupo;
	}
	public int getGruCodigo() {
		return gruCodigo;
	}
	public void setGruCodigo(int gruCodigo) {
		this.gruCodigo = gruCodigo;
	}
	public String getGruNombre() {
		return gruNombre;
	}
	public void setGruNombre(String gruNombre) {
		this.gruNombre = gruNombre;
	}
	
}
