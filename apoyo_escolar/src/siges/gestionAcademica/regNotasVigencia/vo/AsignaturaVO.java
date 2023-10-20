package siges.gestionAcademica.regNotasVigencia.vo;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.Vo;

public class AsignaturaVO extends Vo {
	private int asicodinst; 
	private int asicodmetod; 
	private int asicodarea; 
	private int asicodigo; 
	private String asinombre; 
	private int asiorden; 
	private String asiabrev; 
	private int asivigencia; 
	private int asiprioridad; 
	private int asiestado;
	private long asicodjerar;
	private int numPeriodos;
	private List listaEvalAsiNota = new ArrayList();
	private String finalEvalAsiNota;
	
   public AsignaturaVO(int numPeriodos){
	   this.numPeriodos = numPeriodos;
	   calcularLista();
   }
	
	public String getAsiabrev() {
		return asiabrev;
	}
	public void setAsiabrev(String asiabrev) {
		this.asiabrev = asiabrev;
	}
	public int getAsicodarea() {
		return asicodarea;
	}
	public void setAsicodarea(int asicodarea) {
		this.asicodarea = asicodarea;
	}
	public int getAsicodigo() {
		return asicodigo;
	}
	public void setAsicodigo(int asicodigo) {
		this.asicodigo = asicodigo;
	}
	public int getAsicodinst() {
		return asicodinst;
	}
	public void setAsicodinst(int asicodinst) {
		this.asicodinst = asicodinst;
	}
	public int getAsicodmetod() {
		return asicodmetod;
	}
	public void setAsicodmetod(int asicodmetod) {
		this.asicodmetod = asicodmetod;
	}
	public int getAsiestado() {
		return asiestado;
	}
	public void setAsiestado(int asiestado) {
		this.asiestado = asiestado;
	}
	public String getAsinombre() {
		return asinombre;
	}
	public void setAsinombre(String asinombre) {
		this.asinombre = asinombre;
	}
	public int getAsiorden() {
		return asiorden;
	}
	public void setAsiorden(int asiorden) {
		this.asiorden = asiorden;
	}
	public int getAsiprioridad() {
		return asiprioridad;
	}
	public void setAsiprioridad(int asiprioridad) {
		this.asiprioridad = asiprioridad;
	}
	public int getAsivigencia() {
		return asivigencia;
	}
	public void setAsivigencia(int asivigencia) {
		this.asivigencia = asivigencia;
	}
	public long getAsicodjerar() {
		return asicodjerar;
	}
	public void setAsicodjerar(long asicodjerar) {
		this.asicodjerar = asicodjerar;
	}
	
	public List getListaEvalAsiNota() {
	
		return listaEvalAsiNota;
	}
	

	public double getListaEvalAsiNota(int index) {
		 if(index < numPeriodos && GenericValidator.isDouble((String)listaEvalAsiNota.get(index))){
			return Double.parseDouble( (String)listaEvalAsiNota.get(index)  );
		}else{
			return -99; 
		}
		
	}
	
	private void calcularLista(){
		for(int i=0;i< numPeriodos;i++){
			listaEvalAsiNota.add(i,"");
		};
	}
	
	public void setListaEvalAsiNota(List listaEvalAsiNota) {
		this.listaEvalAsiNota = listaEvalAsiNota;
	}
	
	public void setListaEvalAsiNotaAdd(int index, String nota) {
		if(index < numPeriodos){
			this.listaEvalAsiNota.remove(index);
			if(GenericValidator.isDouble(nota) && Double.parseDouble(nota) > -999){
				this.listaEvalAsiNota.add(index, nota);
			}else{
				this.listaEvalAsiNota.add(index,"");
			}
		}
	}
	
	
	public int getNumPeriodos() {
		return numPeriodos;
	}
	public void setNumPeriodos(int numPeriodos) {
		this.numPeriodos = numPeriodos;
	}
	public String getFinalEvalAsiNota() {
		return finalEvalAsiNota;
	}
	public void setFinalEvalAsiNota(String finalEvalAsiNota) {
		String finalEvalAreNota_ ="";
		if(GenericValidator.isDouble(finalEvalAsiNota) && Double.parseDouble(finalEvalAsiNota) > -999){
			finalEvalAreNota_ =""+finalEvalAsiNota;
		}else{
			finalEvalAreNota_ = "";
		}
		this.finalEvalAsiNota = finalEvalAreNota_; 
	}

	public void setFinalEvalAsiNotaAdd(String finalEvalAsiNota) {
		String finalEvalAsiNota_ ="";
		if(Double.parseDouble(finalEvalAsiNota) > -999){
			finalEvalAsiNota_ =""+finalEvalAsiNota;
		}else{
			finalEvalAsiNota_ = "";
		}
		this.finalEvalAsiNota = finalEvalAsiNota_;
	}
}
