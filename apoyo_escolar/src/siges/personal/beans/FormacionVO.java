package siges.personal.beans;


/** siges.personal.beans<br>
 * Funcinn:   
 * <br>
 */

public class FormacionVO implements Cloneable{
    private String forCodPerso;
    private String forId;
    private String forNomInst;
    private String forPrograma; 
    private String forSemestres;
    private String forTermino;
    private String forAnho; 
    private String forTitulo;
    private String forCarrera;
  	private String forEstado;

  	public Object clone() {
  			Object o = null;
  			try {
  				o = super.clone();
  			} catch(CloneNotSupportedException e) {
  				System.err.println("MyObject can't clone");
  			}
  			return o;
  		}
  	
    public String getForAnho() {
        return forAnho!=null?forAnho:"";
    }
    public void setForAnho(String forAnho) {
        this.forAnho = forAnho;
    }
    public String getForCodPerso() {
        return forCodPerso!=null?forCodPerso:"";
    }
    public void setForCodPerso(String forCodPerso) {
        this.forCodPerso = forCodPerso;
    }
    public String getForId() {
        return forId!=null?forId:"";
    }
    public void setForId(String forId) {
        this.forId = forId;
    }
    public String getForNomInst() {
        return forNomInst!=null?forNomInst:"";
    }
    public void setForNomInst(String forNomInst) {
        this.forNomInst = forNomInst;
    }
    public String getForPrograma() {
        return forPrograma!=null?forPrograma:"";
    }
    public void setForPrograma(String forPrograma) {
        this.forPrograma = forPrograma;
    }
    public String getForSemestres() {
        return forSemestres!=null?forSemestres:"";
    }
    public void setForSemestres(String forSemestres) {
        this.forSemestres = forSemestres;
    }
    public String getForTermino() {
        return forTermino!=null?forTermino:"";
    }
    public void setForTermino(String forTermino) {
        this.forTermino = forTermino;
    }
    public String getForTitulo() {
        return forTitulo!=null?forTitulo:"";
    }
    public void setForTitulo(String forTitulo) {
        this.forTitulo = forTitulo;
    }
    public String getForCarrera(){
        return forCarrera;
    }
    public void setForCarrera(String forCarrera){
        this.forCarrera = forCarrera;
    }
    public String getForEstado(){
        return forEstado!=null?forEstado:"";
    }
    public void setForEstado(String forEstado){
        this.forEstado = forEstado;
    }
}
