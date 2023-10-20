package articulacion.horarioArticulacion.vo;

import java.util.List;

import siges.common.vo.Vo;

public class HorarioVO extends Vo{
	private List horEncabezado;
	private List horClase;
	private List horEspacio;

	public List getHorEncabezado() {
		return horEncabezado;
	}

	public void setHorEncabezado(List horEncabezado) {
		this.horEncabezado = horEncabezado;
	}

	/**
	 * @return Returns the horClase.
	 */
	public List getHorClase() {
		return horClase;
	}

	/**
	 * @param horClase The horClase to set.
	 */
	public void setHorClase(List horClase) {
		this.horClase = horClase;
	}

	/**
	 * @return Returns the horEspacio.
	 */
	public List getHorEspacio() {
		return horEspacio;
	}

	/**
	 * @param horEspacio The horEspacio to set.
	 */
	public void setHorEspacio(List horEspacio) {
		this.horEspacio = horEspacio;
	}

}
