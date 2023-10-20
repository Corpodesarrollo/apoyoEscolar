package poa.reportes.vo;

import java.util.Date;

import siges.common.vo.Vo;

/**
 * Value Object Resultado de Fechas de Seguimiento POA.
 * 
 * 
 */
public class FechasSegColegioVO extends Vo {
	private Date progFechaFin1;
	private Date progFechaFin2;
	private Date progFechaFin3;
	private Date progFechaFin4;

	public Date getProgFechaFin1() {
		return progFechaFin1;
	}

	public void setProgFechaFin1(Date progFechaFin1) {
		this.progFechaFin1 = progFechaFin1;
	}

	public Date getProgFechaFin2() {
		return progFechaFin2;
	}

	public void setProgFechaFin2(Date progFechaFin2) {
		this.progFechaFin2 = progFechaFin2;
	}

	public Date getProgFechaFin3() {
		return progFechaFin3;
	}

	public void setProgFechaFin3(Date progFechaFin3) {
		this.progFechaFin3 = progFechaFin3;
	}

	public Date getProgFechaFin4() {
		return progFechaFin4;
	}

	public void setProgFechaFin4(Date progFechaFin4) {
		this.progFechaFin4 = progFechaFin4;
	}

}
