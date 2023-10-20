package poa.seguimiento.vo;

import java.util.Date;

import siges.common.vo.Vo;

/**
 * Value Object usado para la comparacion de fechas de seguimiento
 *  
 *  
 */
public class SegFechasCompareVO  extends Vo {
	
	private Date fechaSegFin1;
	private Date fechaSegFin2;
	private Date fechaSegFin3;
	private Date fechaSegFin4;
	
	private boolean fechaCompareSeg1;
	private boolean fechaCompareSeg2;
	private boolean fechaCompareSeg3;
	private boolean fechaCompareSeg4;
	
	public Date getFechaSegFin1() {
		return fechaSegFin1;
	}
	public void setFechaSegFin1(Date fechaSegFin1) {
		this.fechaSegFin1 = fechaSegFin1;
	}
	public Date getFechaSegFin2() {
		return fechaSegFin2;
	}
	public void setFechaSegFin2(Date fechaSegFin2) {
		this.fechaSegFin2 = fechaSegFin2;
	}
	public Date getFechaSegFin3() {
		return fechaSegFin3;
	}
	public void setFechaSegFin3(Date fechaSegFin3) {
		this.fechaSegFin3 = fechaSegFin3;
	}
	public Date getFechaSegFin4() {
		return fechaSegFin4;
	}
	public void setFechaSegFin4(Date fechaSegFin4) {
		this.fechaSegFin4 = fechaSegFin4;
	}
	public boolean isFechaCompareSeg1() {
		return fechaCompareSeg1;
	}
	public void setFechaCompareSeg1(boolean fechaCompareSeg1) {
		this.fechaCompareSeg1 = fechaCompareSeg1;
	}
	public boolean isFechaCompareSeg2() {
		return fechaCompareSeg2;
	}
	public void setFechaCompareSeg2(boolean fechaCompareSeg2) {
		this.fechaCompareSeg2 = fechaCompareSeg2;
	}
	public boolean isFechaCompareSeg3() {
		return fechaCompareSeg3;
	}
	public void setFechaCompareSeg3(boolean fechaCompareSeg3) {
		this.fechaCompareSeg3 = fechaCompareSeg3;
	}
	public boolean isFechaCompareSeg4() {
		return fechaCompareSeg4;
	}
	public void setFechaCompareSeg4(boolean fechaCompareSeg4) {
		this.fechaCompareSeg4 = fechaCompareSeg4;
	}
		

}

