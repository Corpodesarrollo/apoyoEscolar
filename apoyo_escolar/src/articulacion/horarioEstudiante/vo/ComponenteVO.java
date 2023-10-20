package articulacion.horarioEstudiante.vo;

import siges.common.vo.Vo;

public class ComponenteVO extends Vo {

	private int componente;
	private int jornada;

	public int getJornada() {
		return jornada;
	}

	public void setJornada(int jornada) {
		// System.out.println("LLEGA A SEJORNADA="+jornada);
		this.jornada = jornada;
	}

	public int getComponente() {
		return componente;
	}

	public void setComponente(int componente) {
		// System.out.println("LLEGA A SETCOMPONENTE="+componente);
		this.componente = componente;
	}

}
