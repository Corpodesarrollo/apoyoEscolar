package siges.gestionAcademica.regNotasVigencia.vo;

import java.util.ArrayList;
import java.util.List;

import siges.common.vo.ItemVO;
import siges.common.vo.Vo;

public class InstitucionParametroVO extends Vo {
	
	private int insparvigencia; 
	private int insparcodinst; 
	private int insparnumper; 
	private int insparniveval;
	private String insparnomperdef;
	private List listaPerd = new ArrayList();
	
	public int getInsparcodinst() {
		return insparcodinst;
	}
	public void setInsparcodinst(int insparcodinst) {
		this.insparcodinst = insparcodinst;
	}
	public int getInsparniveval() {
		return insparniveval;
	}
	public void setInsparniveval(int insparniveval) {
		this.insparniveval = insparniveval;
	}
	public String getInsparnomperdef() {
		return insparnomperdef;
	}
	public void setInsparnomperdef(String insparnomperdef) {
		this.insparnomperdef = insparnomperdef;
	}
	public int getInsparnumper() {
		return insparnumper;
	}
	public void setInsparnumper(int insparnumper) {
		this.insparnumper = insparnumper;
	}
	public int getInsparvigencia() {
		return insparvigencia;
	}
	public void setInsparvigencia(int insparvigencia) {
		this.insparvigencia = insparvigencia;
	}
	public List getListaPerd() {
		return listaPerd;
	}
	public void setListaPerd(List listaPerd) {
		this.listaPerd = listaPerd;
	}
	
	public void calcularLista(){
		for(int i=1; i <= insparnumper;i++){
			listaPerd.add(new ItemVO(i,"Periodo "+i));
		}
	}


}
