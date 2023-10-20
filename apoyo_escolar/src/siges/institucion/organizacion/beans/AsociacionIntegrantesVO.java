package siges.institucion.organizacion.beans;

public class AsociacionIntegrantesVO implements Cloneable{
	private String asoIntCodigo;
	private String asoIntInst;
	private String asoIntTipodoc;
	private String asoIntNumdoc;
	private String asoIntCargo;
	private String asoIntNombre;
	private String asoIntApellido;
	private String asoIntTelefono;
	private String asoIntCorreo; 
	private String asoIntGenero;
	private String asoIntEstado;

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
	 * @return Returns the asoIntApellido.
	 */
	public String getAsoIntApellido() {
		return asoIntApellido!=null?asoIntApellido:"";
	}

	/**
	 * @param asoIntApellido The asoIntApellido to set.
	 */
	public void setAsoIntApellido(String asoIntApellido) {
		this.asoIntApellido = asoIntApellido;
	}

	/**
	 * @return Returns the asoIntCargo.
	 */
	public String getAsoIntCargo() {
		return asoIntCargo!=null?asoIntCargo:"";
	}

	/**
	 * @param asoIntCargo The asoIntCargo to set.
	 */
	public void setAsoIntCargo(String asoIntCargo) {
		this.asoIntCargo = asoIntCargo;
	}

	/**
	 * @return Returns the asoIntCodigo.
	 */
	public String getAsoIntCodigo() {
		return asoIntCodigo!=null?asoIntCodigo:"";
	}

	/**
	 * @param asoIntCodigo The asoIntCodigo to set.
	 */
	public void setAsoIntCodigo(String asoIntCodigo) {
		this.asoIntCodigo = asoIntCodigo;
	}

	/**
	 * @return Returns the asoIntCorreo.
	 */
	public String getAsoIntCorreo() {
		return asoIntCorreo!=null?asoIntCorreo:"";
	}

	/**
	 * @param asoIntCorreo The asoIntCorreo to set.
	 */
	public void setAsoIntCorreo(String asoIntCorreo) {
		this.asoIntCorreo = asoIntCorreo;
	}

	/**
	 * @return Returns the asoIntGenero.
	 */
	public String getAsoIntGenero() {
		return asoIntGenero!=null?asoIntGenero:"";
	}

	/**
	 * @param asoIntGenero The asoIntGenero to set.
	 */
	public void setAsoIntGenero(String asoIntGenero) {
		this.asoIntGenero = asoIntGenero;
	}

	/**
	 * @return Returns the asoIntInst.
	 */
	public String getAsoIntInst() {
		return asoIntInst!=null?asoIntInst:"";
	}

	/**
	 * @param asoIntInst The asoIntInst to set.
	 */
	public void setAsoIntInst(String asoIntInst) {
		this.asoIntInst = asoIntInst;
	}

	/**
	 * @return Returns the asoIntNombre.
	 */
	public String getAsoIntNombre() {
		return asoIntNombre!=null?asoIntNombre:"";
	}

	/**
	 * @param asoIntNombre The asoIntNombre to set.
	 */
	public void setAsoIntNombre(String asoIntNombre) {
		this.asoIntNombre = asoIntNombre;
	}

	/**
	 * @return Returns the asoIntNumdoc.
	 */
	public String getAsoIntNumdoc() {
		return asoIntNumdoc!=null?asoIntNumdoc:"";
	}

	/**
	 * @param asoIntNumdoc The asoIntNumdoc to set.
	 */
	public void setAsoIntNumdoc(String asoIntNumdoc) {
		this.asoIntNumdoc = asoIntNumdoc;
	}

	/**
	 * @return Returns the asoIntTelefono.
	 */
	public String getAsoIntTelefono() {
		return asoIntTelefono!=null?asoIntTelefono:"";
	}

	/**
	 * @param asoIntTelefono The asoIntTelefono to set.
	 */
	public void setAsoIntTelefono(String asoIntTelefono) {
		this.asoIntTelefono = asoIntTelefono;
	}

	/**
	 * @return Returns the asoIntTipodoc.
	 */
	public String getAsoIntTipodoc() {
		return asoIntTipodoc!=null?asoIntTipodoc:"";
	}

	/**
	 * @param asoIntTipodoc The asoIntTipodoc to set.
	 */
	public void setAsoIntTipodoc(String asoIntTipodoc) {
		this.asoIntTipodoc = asoIntTipodoc;
	}

	public String getAsoIntEstado() {
		return asoIntEstado!=null?asoIntEstado:"";
	}

	public void setAsoIntEstado(String asoIntEstado) {
		this.asoIntEstado = asoIntEstado;
	}
}
