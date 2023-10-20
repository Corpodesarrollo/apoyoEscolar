package siges.gestionAcademica.regNotasVigencia.vo;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.Vo;

public class AreaVO extends Vo {
	private int arecodinst; 
	private int arecodmetod; 
	private int arecodigo; 
	private String arenombre; 
	private int areorden;
	private String areabrev; 
	private int arevigencia;
	private List listaEvalAreNota   = new ArrayList();
	private String finalEvalAreNota  ;
	private long arecodJerar;
	private int numPeriodos;
	private int cod_tipo_eval;
	
	private List listaAsig;
	
	
	
	public AreaVO(int numPeriodos){
		this.numPeriodos = numPeriodos;
		listaEvalAreNota = new ArrayList(numPeriodos); 
		calcularLista(); 
	}
	
	public String getAreabrev() {
		return areabrev;
	}
	
	public void setAreabrev(String areabrev) {
		this.areabrev = areabrev;
	}
	
	public int getArecodigo() {
		return arecodigo;
	}
	
	public void setArecodigo(int arecodigo) {
		this.arecodigo = arecodigo;
	}
	
	public int getArecodinst() {
		return arecodinst;
	}
	
	public void setArecodinst(int arecodinst) {
		this.arecodinst = arecodinst;
	}
	
	public int getArecodmetod() {
		return arecodmetod;
	}
	
	public void setArecodmetod(int arecodmetod) {
		this.arecodmetod = arecodmetod;
	}
	
	public String getArenombre() {
		return arenombre;
	}
	
	public void setArenombre(String arenombre) {
		this.arenombre = arenombre;
	}
	
	public int getAreorden() {
		return areorden;
	}
	
	public void setAreorden(int areorden) {
		this.areorden = areorden;
	}
	
	public int getArevigencia() {
		return arevigencia;
	}
	
	public void setArevigencia(int arevigencia) {
		this.arevigencia = arevigencia;
	}
	
	public List getListaAsig() {
		return listaAsig;
	}
	
	public void setListaAsig(List listaAsig) {
		this.listaAsig = listaAsig;
	}
	
	public long getArecodJerar() {
		return arecodJerar;
	}
	
	public void setArecodJerar(long arecodJerar) {
		this.arecodJerar = arecodJerar;
	}
	
	public List getListaEvalAreNota() {
		return listaEvalAreNota;
	}
	
	
	public double getListaEvalAreNota(int index) {
		if(index < numPeriodos && GenericValidator.isDouble((String)listaEvalAreNota.get(index))){
			return Double.parseDouble( (String)listaEvalAreNota.get(index)  );
		}else{
			return -99; 
		}
		
	}
	
	public void setListaEvalAreNota(List listaEvalAreNota) {
		this.listaEvalAreNota = listaEvalAreNota;
	}
	
	
	private void calcularLista(){ 
		for(int i=0;i< numPeriodos;i++){
			listaEvalAreNota.add("");
		}
		
	}
	
	
	public void setListaEvalAreNotaAdd(int index, String nota){
		if(index < numPeriodos){
			listaEvalAreNota.remove(index);
			if(GenericValidator.isDouble(nota) && Double.parseDouble(nota) > -999){
				
				listaEvalAreNota.add(index, nota);
			}else{
				listaEvalAreNota.add(index,"");
				
			}
		}
	}
	
	public int getNumPeriodos() {
		return numPeriodos;
	}
	
	
	
	public String getFinalEvalAreNota() {
		return finalEvalAreNota;
	}
	
	public void setFinalEvalAreNota(String finalEvalAreNota) {
		this.finalEvalAreNota = finalEvalAreNota;
	}
	
	
	
	
	public void setFinalEvalAreNotaAdd(String finalEvalAreNota) {
		String finalEvalAreNota_ ="";
		if(Double.parseDouble(finalEvalAreNota) > -999){
			finalEvalAreNota_ =""+finalEvalAreNota;
		}else{
			finalEvalAreNota_ = "";
		}
		this.finalEvalAreNota = finalEvalAreNota_;
	}
	
	public void setCod_tipo_eval(int cod_tipo_eval) {
		this.cod_tipo_eval = cod_tipo_eval;
	}
	
	
	
 
	
}
