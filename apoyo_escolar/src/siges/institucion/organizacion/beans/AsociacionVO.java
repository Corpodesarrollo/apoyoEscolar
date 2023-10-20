package siges.institucion.organizacion.beans;

public class AsociacionVO implements Cloneable{
	private String asoCodigo; 
	private String asoInst; 
	private String asoNombre; 
	private String asoFechaConstitucion; 
	private String asoPersoneria; 
	private String asoVigencia; 
	private String asoFechaInicio; 
	private String asoFechaFin;
	private String asoEstado;

    /**
     *	Hace una copia del objeto mismo
     *	@return Object 
     */
    	public Object clone(){
    		Object o = null;
    		try{
    			o = super.clone();
    		}catch(CloneNotSupportedException e){
    			System.err.println("MyObject can't clone");
    		}
    		return o;
        }

	/**
	 * @return Returns the asoCodigo.
	 */
	public String getAsoCodigo() {
		return asoCodigo!=null?asoCodigo:"";
	}

	/**
	 * @param asoCodigo The asoCodigo to set.
	 */
	public void setAsoCodigo(String asoCodigo) {
		this.asoCodigo = asoCodigo;
	}

	/**
	 * @return Returns the asoEstado.
	 */
	public String getAsoEstado() {
		return asoEstado!=null?asoEstado:"";
	}

	/**
	 * @param asoEstado The asoEstado to set.
	 */
	public void setAsoEstado(String asoEstado) {
		this.asoEstado = asoEstado;
	}

	/**
	 * @return Returns the asoFechaConstitucion.
	 */
	public String getAsoFechaConstitucion() {
		return asoFechaConstitucion!=null?asoFechaConstitucion:"";
	}

	/**
	 * @param asoFechaConstitucion The asoFechaConstitucion to set.
	 */
	public void setAsoFechaConstitucion(String asoFechaConstitucion) {
		this.asoFechaConstitucion = asoFechaConstitucion;
	}

	/**
	 * @return Returns the asoFechaFin.
	 */
	public String getAsoFechaFin() {
		return asoFechaFin!=null?asoFechaFin:"";
	}

	/**
	 * @param asoFechaFin The asoFechaFin to set.
	 */
	public void setAsoFechaFin(String asoFechaFin) {
		this.asoFechaFin = asoFechaFin;
	}

	/**
	 * @return Returns the asoFechaInicio.
	 */
	public String getAsoFechaInicio() {
		return asoFechaInicio!=null?asoFechaInicio:"";
	}

	/**
	 * @param asoFechaInicio The asoFechaInicio to set.
	 */
	public void setAsoFechaInicio(String asoFechaInicio) {
		this.asoFechaInicio = asoFechaInicio;
	}

	/**
	 * @return Returns the asoInst.
	 */
	public String getAsoInst() {
		return asoInst!=null?asoInst:"";
	}

	/**
	 * @param asoInst The asoInst to set.
	 */
	public void setAsoInst(String asoInst) {
		this.asoInst = asoInst;
	}

	/**
	 * @return Returns the asoNombre.
	 */
	public String getAsoNombre() {
		return asoNombre!=null?asoNombre:"";
	}

	/**
	 * @param asoNombre The asoNombre to set.
	 */
	public void setAsoNombre(String asoNombre) {
		this.asoNombre = asoNombre;
	}

	/**
	 * @return Returns the asoPersoneria.
	 */
	public String getAsoPersoneria() {
		return asoPersoneria!=null?asoPersoneria:"";
	}

	/**
	 * @param asoPersoneria The asoPersoneria to set.
	 */
	public void setAsoPersoneria(String asoPersoneria) {
		this.asoPersoneria = asoPersoneria;
	}

	/**
	 * @return Returns the asoVigencia.
	 */
	public String getAsoVigencia() {
		return asoVigencia!=null?asoVigencia:"";
	}

	/**
	 * @param asoVigencia The asoVigencia to set.
	 */
	public void setAsoVigencia(String asoVigencia) {
		this.asoVigencia = asoVigencia;
	}

}
