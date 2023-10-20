package siges.common.vo;

public class Vo implements Cloneable{
	
	private String formaEstado;
	private String formaDisabled;	
	private boolean edicion;
    /**
     *	Hace una copia del objeto mismo
     *	@return Object 
     */
    public Object clone(){
    	Object o = null;
    	try{
    		o = super.clone();
    	}catch(CloneNotSupportedException e){
    		System.err.println("No se puede clonar "+this);
    	}
    	return o;
    }

	public String getFormaEstado() {
		return (formaEstado != null) ? formaEstado : "";
	}

	public void setFormaEstado(String formaEstado) {
		this.formaEstado = formaEstado;
	}

	/**
	 * @return Return the formaDisabled.
	 */
	public String getFormaDisabled() {
		return formaDisabled;
	}

	/**
	 * @param formaDisabled The formaDisabled to set.
	 */
	public void setFormaDisabled(String formaDisabled) {
		this.formaDisabled = formaDisabled;
	}

	/**
	 * @return the edicion
	 */
	public boolean isEdicion() {
		return edicion;
	}

	/**
	 * @param edicion the edicion to set
	 */
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}

}
