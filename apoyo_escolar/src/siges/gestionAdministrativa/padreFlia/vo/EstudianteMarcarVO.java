/**
 * 
 */
package siges.gestionAdministrativa.padreFlia.vo;


/**
 * 28/04/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class EstudianteMarcarVO  {
	private long estgrupo; 
	private long esttipodoc; 
	private String estnumdoc; 
	private String estnombre;
	private int estconsulta;
	private String estapellido;
	private long estconsultabol;
	private long estcodigo;
	
	
	public long getEstconsultabol() {
		return estconsultabol;
	}
	public void setEstconsultabol(long estconsultabol) {
		this.estconsultabol = estconsultabol;
	}
	public long getEstgrupo() {
		return estgrupo;
	}
	public void setEstgrupo(long estgrupo) {
		this.estgrupo = estgrupo;
	}
	public String getEstnombre() {
		return estnombre;
	}
	public void setEstnombre(String estnombre) {
		this.estnombre = estnombre;
	}
	public String getEstnumdoc() {
		return estnumdoc;
	}
	public void setEstnumdoc(String estnumdoc) {
		this.estnumdoc = estnumdoc;
	}
	public long getEsttipodoc() {
		return esttipodoc;
	}
	public void setEsttipodoc(long esttipodoc) {
		this.esttipodoc = esttipodoc;
	}
	public String getEstapellido() {
		return estapellido;
	}
	public void setEstapellido(String estapellido) {
		this.estapellido = estapellido;
	}
	public int getEstconsulta() {
		return estconsulta;
	}
	public void setEstconsulta(int estconsulta) {
		this.estconsulta = estconsulta;
	}
	public long getEstcodigo() {
		return estcodigo;
	}
	public void setEstcodigo(long estcodigo) {
		this.estcodigo = estcodigo;
	}
 
	 
}
