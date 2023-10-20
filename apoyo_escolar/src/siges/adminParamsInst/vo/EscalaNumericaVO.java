package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class EscalaNumericaVO  extends Vo{
	
	private long insnumvigencia;
	private long insnumcodinst;
	private long insnumniveval; 
	private long insnumcodsede = -99;
	private long insnumcodjorn = -99;
	private long insnumcodmetod = -99;
	private long insnumcodnivel = -99; 
	private long insnumcodgrado  = -99; 
	private String insnumvalmin = "0.00"; 
	private String insnumvalmax = "0.00";
	private String insnumvalminAntes; 
	private String insnumvalmaxAntes;
	private long insnumequmen; 
	private String insnumequmenNom; 
	private long insnumequinst;
	private String insnumequinstNom;
	private String insnumdescripcion;
	private long insnumorden;
	private boolean edicion;
	private int yaFueUtilizado = 0;
	private String msgValidarRango; 
	
	
	
	public boolean isEdicion() {
		return edicion;
	}
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}
	public long getInsnumcodgrado() {
		return insnumcodgrado;
	}
	public void setInsnumcodgrado(long insnumcodgrado) {
		this.insnumcodgrado = insnumcodgrado;
	}
	public long getInsnumcodinst() {
		return insnumcodinst;
	}
	public void setInsnumcodinst(long insnumcodinst) {
		this.insnumcodinst = insnumcodinst;
	}
	public long getInsnumcodjorn() {
		return insnumcodjorn;
	}
	public void setInsnumcodjorn(long insnumcodjorn) {
		this.insnumcodjorn = insnumcodjorn;
	}
	public long getInsnumcodmetod() {
		return insnumcodmetod;
	}
	public void setInsnumcodmetod(long insnumcodmetod) {
		this.insnumcodmetod = insnumcodmetod;
	}
	public long getInsnumcodnivel() {
		return insnumcodnivel;
	}
	public void setInsnumcodnivel(long insnumcodnivel) {
		this.insnumcodnivel = insnumcodnivel;
	}
	public long getInsnumcodsede() {
		return insnumcodsede;
	}
	public void setInsnumcodsede(long insnumcodsede) {
		this.insnumcodsede = insnumcodsede;
	}
	public String getInsnumdescripcion() {
		return insnumdescripcion;
	}
	public void setInsnumdescripcion(String insnumdescripcion) {
		this.insnumdescripcion = insnumdescripcion;
	}
	public long getInsnumequinst() {
		return insnumequinst;
	}
	public void setInsnumequinst(long insnumequinst) {
		this.insnumequinst = insnumequinst;
	}
	public long getInsnumequmen() {
		return insnumequmen;
	}
	public void setInsnumequmen(long insnumequmen) {
		this.insnumequmen = insnumequmen;
	}
	public long getInsnumniveval() {
		return insnumniveval;
	}
	public void setInsnumniveval(long insnumniveval) {
		this.insnumniveval = insnumniveval;
	}
	public long getInsnumorden() {
		return insnumorden;
	}
	public void setInsnumorden(long insnumorden) {
		this.insnumorden = insnumorden;
	}
 
	 
	public long getInsnumvigencia() {
		return insnumvigencia;
	}
	public void setInsnumvigencia(long insnumvigencia) {
		this.insnumvigencia = insnumvigencia;
	}
	public String getInsnumvalmax() {
		return insnumvalmax;
	}
	public void setInsnumvalmax(String insnumvalmax) {
		this.insnumvalmax = insnumvalmax;
	}
	public String getInsnumvalmin() {
		return insnumvalmin;
	}
	public void setInsnumvalmin(String insnumvalmin) {
		this.insnumvalmin = insnumvalmin;
	}
	public String getInsnumequmenNom() {
		return insnumequmenNom;
	}
	public void setInsnumequmenNom(String insnumequmenNom) {
		this.insnumequmenNom = insnumequmenNom;
	}
	public String getInsnumequinstNom() {
		return insnumequinstNom;
	}
	public void setInsnumequinstNom(String insnumequinstNom) {
		this.insnumequinstNom = insnumequinstNom;
	}
	public int getYaFueUtilizado() {
		return yaFueUtilizado;
	}
	public void setYaFueUtilizado(int yaFueUtilizado) {
		this.yaFueUtilizado = yaFueUtilizado;
	}
	public String getInsnumvalmaxAntes() {
		return insnumvalmaxAntes;
	}
	public void setInsnumvalmaxAntes(String insnumvalmaxAntes) {
		this.insnumvalmaxAntes = insnumvalmaxAntes;
	}
	public String getInsnumvalminAntes() {
		return insnumvalminAntes;
	}
	public void setInsnumvalminAntes(String insnumvalminAntes) {
		this.insnumvalminAntes = insnumvalminAntes;
	}
	public String getMsgValidarRango() {
		return msgValidarRango;
	}
	public void setMsgValidarRango(String msgValidarRango) {
		this.msgValidarRango = msgValidarRango;
	}

	}
