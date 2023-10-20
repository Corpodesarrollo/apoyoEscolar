package siges.estudiante.beans;

/** siges.estudiante.beans<br>
 * Funcinn:   
 * <br>
 */

public class AcademicaVO implements Cloneable{
    private String acaEstudiante;
    private String acaId;
    private String acaInst;
    private String acaGrado;
    private String acaTitulo;
    private String acaAnho;
    private String acaGradoEstado;
    private String acaEstado;
    
    

    /**
     *	Hace una copia del objeto mismo
     *	@return Object 
     */
    	public Object clone() {
    		Object o = null;
    		try {
    			o = super.clone();
    		} catch(CloneNotSupportedException e) {
    			System.err.println("MyObject can't clone");
    		}
    		return o;
        }	
    public String getAcaAnho() {
        return acaAnho!=null?acaAnho:"";
    }
    public void setAcaAnho(String acaAnho) {
        this.acaAnho = acaAnho;
    }
    public String getAcaEstado() {
        return acaEstado!=null?acaEstado:"";
    }
    public void setAcaEstado(String acaEstado) {
        this.acaEstado = acaEstado;
    }
    public String getAcaEstudiante() {
        return acaEstudiante!=null?acaEstudiante:"";
    }
    public void setAcaEstudiante(String acaEstudiante) {
        this.acaEstudiante = acaEstudiante;
    }
    public String getAcaGrado() {
        return acaGrado!=null?acaGrado:"";
    }
    public void setAcaGrado(String acaGrado) {
        this.acaGrado = acaGrado;
    }
    public String getAcaGradoEstado() {
        return acaGradoEstado!=null?acaGradoEstado:"";
    }
    public void setAcaGradoEstado(String acaGradoEstado) {
        this.acaGradoEstado = acaGradoEstado;
    }
    public String getAcaId() {
        return acaId!=null?acaId:"";
    }
    public void setAcaId(String acaId) {
        this.acaId = acaId;
    }
    public String getAcaInst() {
        return acaInst!=null?acaInst:"";
    }
    public void setAcaInst(String acaInst) {
        this.acaInst = acaInst;
    }
    public String getAcaTitulo() {
        return acaTitulo!=null?acaTitulo:"";
    }
    public void setAcaTitulo(String acaTitulo) {
        this.acaTitulo = acaTitulo;
    }
}
