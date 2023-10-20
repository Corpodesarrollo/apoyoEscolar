package siges.personal.beans;


/** siges.personal.beans<br>
 * Funcinn:   
 * <br>
 */

public class LaboralVO implements Cloneable{
    private String labCodPerso;
    private String labId;
    private String labNomEntid;
    private String labFechaInicio; 
    private String labFechaFinal;
    private String labCargo;
    private String labDescripcion; 
    private String labContacto;
    private String labTelefono;
  	private String labEstado;

  	public Object clone() {
  			Object o = null;
  			try {
  				o = super.clone();
  			} catch(CloneNotSupportedException e) {
  				System.err.println("MyObject can't clone");
  			}
  			return o;
  		}		
    public String getLabCargo() {
        return labCargo!=null?labCargo:"";
    }
    public void setLabCargo(String labCargo) {
        this.labCargo = labCargo;
    }
    public String getLabCodPerso() {
        return labCodPerso!=null?labCodPerso:"";
    }
    public void setLabCodPerso(String labCodPerso) {
        this.labCodPerso = labCodPerso;
    }
    public String getLabContacto() {
        return labContacto!=null?labContacto:"";
    }
    public void setLabContacto(String labContacto) {
        this.labContacto = labContacto;
    }
    public String getLabDescripcion() {
        return labDescripcion!=null?labDescripcion:"";
    }
    public void setLabDescripcion(String labDescripcion) {
        this.labDescripcion = labDescripcion;
    }
    public String getLabEstado() {
        return labEstado!=null?labEstado:"";
    }
    public void setLabEstado(String labEstado) {
        this.labEstado = labEstado;
    }
    public String getLabFechaFinal() {
        return labFechaFinal!=null?labFechaFinal:"";
    }
    public void setLabFechaFinal(String labFechaFinal) {
        this.labFechaFinal = labFechaFinal;
    }
    public String getLabFechaInicio() {
        return labFechaInicio!=null?labFechaInicio:"";
    }
    public void setLabFechaInicio(String labFechaInicio) {
        this.labFechaInicio = labFechaInicio;
    }
    public String getLabNomEntid() {
        return labNomEntid!=null?labNomEntid:"";
    }
    public void setLabNomEntid(String labNomEntid) {
        this.labNomEntid = labNomEntid;
    }
    public String getLabTelefono() {
        return labTelefono!=null?labTelefono:"";
    }
    public void setLabTelefono(String labTelefono) {
        this.labTelefono = labTelefono;
    }
    public String getLabId() {
        return labId!=null?labId:"";
    }
    public void setLabId(String labId) {
        this.labId = labId;
    }
}
