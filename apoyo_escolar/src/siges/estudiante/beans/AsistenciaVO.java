package siges.estudiante.beans;

public class AsistenciaVO  implements Cloneable{
    private String asiTipoPer;
    private String asiCodPer;
    private String asiCodigo;
    private String asiFecha;
    private String asiMotivo;
    private String asiObservacion;
    private String asiJustificada;
    private String asiEstado;
    private String asiPeriodo;
    
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

    public String getAsiCodigo() {
        return asiCodigo!=null?asiCodigo:"";
    }
    public void setAsiCodigo(String asiCodigo) {
        this.asiCodigo = asiCodigo;
    }
    public String getAsiCodPer() {
        return asiCodPer!=null?asiCodPer:"";
    }
    public void setAsiCodPer(String asiCodPer) {
        this.asiCodPer = asiCodPer;
    }
    public String getAsiFecha() {
        return asiFecha!=null?asiFecha:"";
    }
    public void setAsiFecha(String asiFecha) {
        this.asiFecha = asiFecha;
    }
    public String getAsiJustificada() {
        return asiJustificada!=null?asiJustificada:"";
    }
    public void setAsiJustificada(String asiJustificada) {
        this.asiJustificada = asiJustificada;
    }
    public String getAsiMotivo() {
        return asiMotivo!=null?asiMotivo:"";
    }
    public void setAsiMotivo(String asiMotivo) {
        this.asiMotivo = asiMotivo;
    }
    public String getAsiObservacion() {
        return asiObservacion!=null?asiObservacion:"";
    }
    public void setAsiObservacion(String asiObservacion) {
        this.asiObservacion = asiObservacion;
    }
    public String getAsiTipoPer() {
        return asiTipoPer!=null?asiTipoPer:"";
    }
    public void setAsiTipoPer(String asiTipoPer) {
        this.asiTipoPer = asiTipoPer;
    }
    public String getAsiEstado() {
        return asiEstado!=null?asiEstado:"";
    }
    public void setAsiEstado(String asiEstado) {
        this.asiEstado = asiEstado;
    }

	/**
	 * @return Return the asiPeriodo.
	 */
	public String getAsiPeriodo() {
		return asiPeriodo;
	}

	/**
	 * @param asiPeriodo The asiPeriodo to set.
	 */
	public void setAsiPeriodo(String asiPeriodo) {
		this.asiPeriodo = asiPeriodo;
	}

}
