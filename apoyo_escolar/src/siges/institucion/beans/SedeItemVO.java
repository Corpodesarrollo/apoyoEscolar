package siges.institucion.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import siges.common.vo.ItemVO;

public class SedeItemVO {
	
	private long sedCodigo;
	private String sedNombre;
	private List listaJornada = new ArrayList();
	private List listaGrado = new ArrayList();
	private List listaEspacio = new ArrayList();
	private int banderaEspacio =0;
	private int totalEspacio =0;
	
	
	public SedeItemVO(long sedCodigo, String sedNombre) {
		super();
		this.sedCodigo = sedCodigo;
		this.sedNombre = sedNombre;
	}
	public List getListaJornada() {
		return listaJornada;
	}
	public void setListaJornada(List listaJornada) {
		this.listaJornada = listaJornada;
	}
	public String getSedNombre() {
		return sedNombre;
	}
	public void setSedNombre(String sedNombre) {
		this.sedNombre = sedNombre;
	}
	public long getSedCodigo() {
		return sedCodigo;
	}
	public void setSedCodigo(long sedCodigo) {
		this.sedCodigo = sedCodigo;
	}
	public List getListaGrado() {
		return listaGrado;
	}
	public void setListaGrado(List listaGrado) {
		this.listaGrado = listaGrado;
	}
	public List getListaEspacio() {
		return listaEspacio;
	}
	public void setListaEspacio(List listaEspacio) {
		this.listaEspacio = listaEspacio;
		for (Iterator iter = listaEspacio.iterator(); iter.hasNext();) {
			ItemVO element = (ItemVO) iter.next();
			totalEspacio +=element.getCodigo();
			
		}
	}
	public int getBanderaEspacio() {
		return banderaEspacio;
	}
	public void setBanderaEspacio(int banderaEspacio) {
		this.banderaEspacio = banderaEspacio;
	}
	public int getTotalEspacio() {
		return totalEspacio;
	}  
	
}
