package siges.institucion.beans;

import java.util.ArrayList;
import java.util.List;

public class JorndItemVO {
	
	private long jorCodigo;
	private String jordNombre;
	private long totalM;
	private long totalF;
	
	
	private long totalEdadM_20_30;
	private long totalEdadF_20_30;
	
	private long totalEdadM_30_40;
	private long totalEdadF_30_40;
	
	private long totalEdadM_40_50;
	private long totalEdadF_40_50;
	
	private long totalEdadM_50_mas;
	private long totalEdadF_50_mas;
	
	private List listaGrado = new ArrayList();
	 
	
	
	public String getJordNombre() {
		return jordNombre;
	}
	public JorndItemVO(long jorCodigo, String jordNombre) {
		super();
		this.jorCodigo = jorCodigo;
		this.jordNombre = jordNombre;
	}
	public void setJordNombre(String jordNombre) {
		this.jordNombre = jordNombre;
	}
	public long getTotalF() {
		return totalF;
	}
	public void setTotalF(long totalF) {
		this.totalF = totalF;
	}
	public long getTotalM() {
		return totalM;
	}
	public void setTotalM(long totalM) {
		this.totalM = totalM;
	}
	public long getJorCodigo() {
		return jorCodigo;
	}
	public void setJorCodigo(long jorCodigo) {
		this.jorCodigo = jorCodigo;
	}
 
	public long getTotalEdadF_30_40() {
		return totalEdadF_30_40;
	}
	public void setTotalEdadF_30_40(long totalEdadF_30_40) {
		this.totalEdadF_30_40 = totalEdadF_30_40;
	}
	public long getTotalEdadF_40_50() {
		return totalEdadF_40_50;
	}
	public void setTotalEdadF_40_50(long totalEdadF_40_50) {
		this.totalEdadF_40_50 = totalEdadF_40_50;
	}
	public long getTotalEdadF_50_mas() {
		return totalEdadF_50_mas;
	}
	public void setTotalEdadF_50_mas(long totalEdadF_50_mas) {
		this.totalEdadF_50_mas = totalEdadF_50_mas;
	}
 
	public long getTotalEdadM_30_40() {
		return totalEdadM_30_40;
	}
	public void setTotalEdadM_30_40(long totalEdadM_30_40) {
		this.totalEdadM_30_40 = totalEdadM_30_40;
	}
	public long getTotalEdadM_40_50() {
		return totalEdadM_40_50;
	}
	public void setTotalEdadM_40_50(long totalEdadM_40_50) {
		this.totalEdadM_40_50 = totalEdadM_40_50;
	}
	public long getTotalEdadM_50_mas() {
		return totalEdadM_50_mas;
	}
	public void setTotalEdadM_50_mas(long totalEdadM_50_mas) {
		this.totalEdadM_50_mas = totalEdadM_50_mas;
	}
	public long getTotalEdadF_20_30() {
		return totalEdadF_20_30;
	}
	public void setTotalEdadF_20_30(long totalEdadF_20_30) {
		this.totalEdadF_20_30 = totalEdadF_20_30;
	}
	public long getTotalEdadM_20_30() {
		return totalEdadM_20_30;
	}
	public void setTotalEdadM_20_30(long totalEdadM_20_30) {
		this.totalEdadM_20_30 = totalEdadM_20_30;
	}
	public List getListaGrado() {
		return listaGrado;
	}
	public void setListaGrado(List listaGrado) {
		this.listaGrado = listaGrado;
	}
	
}
