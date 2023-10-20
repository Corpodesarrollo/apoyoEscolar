package articulacion.asignatura.vo;

import siges.common.vo.Vo;

public class DatosPruebaVO extends Vo {

	private long codigo;
	private String nombre;

	public long getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setCodigo(long codigo) {
		// System.out.println("asigna "+codigo);
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
