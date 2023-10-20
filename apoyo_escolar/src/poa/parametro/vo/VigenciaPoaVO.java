/**
 * 
 */
package poa.parametro.vo;

import siges.common.vo.Vo;

/**
 * 14/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class VigenciaPoaVO extends Vo{
	private int vigencia;
	
	public VigenciaPoaVO(){
		
	}
	
	public VigenciaPoaVO(int vigencia_){
		vigencia = vigencia_;
	}
	
	public int getVigencia() {
		return vigencia;
	}
	
	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	} 	
}
