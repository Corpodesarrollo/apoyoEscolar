package articulacion.importarEvaluacion.objetos;

import siges.common.vo.Vo;

public class EspecialidadesVO extends Vo{
	private int codigo;
	private String especialidad;

	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}	
	
}
