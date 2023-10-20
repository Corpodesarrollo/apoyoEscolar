package articulacion.artAusencias.vo;

import java.util.List;

import siges.common.vo.Vo;

public class AsignaturaClaseVO extends Vo{

	long [] asignatura;
	int [] clase;
	
	public long[] getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(long[] asignatura) {
		this.asignatura = asignatura;
	}
	public int[] getClase() {
		return clase;
	}
	public void setClase(int[] clase) {
		this.clase = clase;
	}
	
	
}
