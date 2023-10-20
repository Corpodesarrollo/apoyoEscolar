/**
 * 
 */
package siges.gestionAcademica.regNotasVigencia.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class EstudianteRegNotasVO {
   private String estnombres;
   private String estapellidos;
   private String estnumdoc;
   private int esttipodoc;
   private String esttipodocNom;
   private long estcodigo;
   private long numFila;
   private long estgrupo;
 
public String getEstnombres() {
	return estnombres;
}
public void setEstnombres(String estnombres) {
	this.estnombres = estnombres;
}
public String getEstapellidos() {
	return estapellidos;
}
public void setEstapellidos(String estapellidos) {
	this.estapellidos = estapellidos;
}
public String getEstnumdoc() {
	return estnumdoc;
}
public void setEstnumdoc(String estnumdoc) {
	this.estnumdoc = estnumdoc;
}
public int getEsttipodoc() {
	return esttipodoc;
}
public void setEsttipodoc(int esttipodoc) {
	this.esttipodoc = esttipodoc;
}
public long getNumFila() {
	return numFila;
}
public void setNumFila(long numFila) {
	this.numFila = numFila;
}
public long getEstcodigo() {
	return estcodigo;
}
public void setEstcodigo(long estcodigo) {
	this.estcodigo = estcodigo;
}
public long getEstgrupo() {
	return estgrupo;
}
public void setEstgrupo(long estgrupo) {
	this.estgrupo = estgrupo;
}
public String getEsttipodocNom() {
	return esttipodocNom;
}
public void setEsttipodocNom(String esttipodocNom) {
	this.esttipodocNom = esttipodocNom;
}
}
