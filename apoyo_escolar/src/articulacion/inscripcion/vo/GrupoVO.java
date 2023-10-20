package articulacion.inscripcion.vo;

import siges.common.vo.Vo;

public class GrupoVO extends Vo{
	private int grupo;
	private int cupos;
	public int getCupos() {
		return cupos;
	}
	public void setCupos(int cupos) {
		this.cupos = cupos;
	}
	public int getGrupo() {
		return grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	
	
}
