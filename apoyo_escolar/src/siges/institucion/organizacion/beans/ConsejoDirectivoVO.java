package siges.institucion.organizacion.beans;

public class ConsejoDirectivoVO  implements Cloneable{
	private String conDirInst;
	private String conDirSede;
	private String conDirJornada; 
	private String conDirCargo;
	private String conDirNombre;
	private String conDirApellido;
	private String conDirTipoDoc;
	private String conDirNumDoc;
	private String conDirTelefono;
	private String conDirCorreo;
	private String conDirGenero;
	private String conDirEstado;
	private String conDirFrmEstado;

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

	public String getConDirApellido() {
		return conDirApellido!=null?conDirApellido:"";
	}

	public void setConDirApellido(String conDirApellido) {
		this.conDirApellido = conDirApellido;
	}

	public String getConDirCargo() {
		return conDirCargo!=null?conDirCargo:"";
	}

	public void setConDirCargo(String conDirCargo) {
		this.conDirCargo = conDirCargo;
	}

	public String getConDirCorreo() {
		return conDirCorreo!=null?conDirCorreo:"";
	}

	public void setConDirCorreo(String conDirCorreo) {
		this.conDirCorreo = conDirCorreo;
	}

	public String getConDirEstado() {
		return conDirEstado!=null?conDirEstado:"";
	}

	public void setConDirEstado(String conDirEstado) {
		this.conDirEstado = conDirEstado;
	}

	public String getConDirGenero() {
		return conDirGenero!=null?conDirGenero:"";
	}

	public void setConDirGenero(String conDirGenero) {
		this.conDirGenero = conDirGenero;
	}

	public String getConDirInst() {
		return conDirInst!=null?conDirInst:"";
	}

	public void setConDirInst(String conDirInst) {
		this.conDirInst = conDirInst;
	}

	public String getConDirJornada() {
		return conDirJornada!=null?conDirJornada:"";
	}

	public void setConDirJornada(String conDirJornada) {
		this.conDirJornada = conDirJornada;
	}

	public String getConDirNombre() {
		return conDirNombre!=null?conDirNombre:"";
	}

	public void setConDirNombre(String conDirNombre) {
		this.conDirNombre = conDirNombre;
	}

	public String getConDirSede() {
		return conDirSede!=null?conDirSede:"";
	}

	public void setConDirSede(String conDirSede) {
		this.conDirSede = conDirSede;
	}

	public String getConDirTelefono() {
		return conDirTelefono!=null?conDirTelefono:"";
	}

	public void setConDirTelefono(String conDirTelefono) {
		this.conDirTelefono = conDirTelefono;
	}

	public String getConDirTipoDoc() {
		return conDirTipoDoc!=null?conDirTipoDoc:"";
	}

	public void setConDirTipoDoc(String conDirTipoDoc) {
		this.conDirTipoDoc = conDirTipoDoc;
	}

	public String getConDirNumDoc() {
		return conDirNumDoc!=null?conDirNumDoc:"";
	}

	public void setConDirNumDoc(String conDirNumDoc) {
		this.conDirNumDoc = conDirNumDoc;
	}

	/**
	 * @return Returns the conDirFrmEstado.
	 */
	public String getConDirFrmEstado() {
		return conDirFrmEstado!=null?conDirFrmEstado:"";
	}

	/**
	 * @param conDirFrmEstado The conDirFrmEstado to set.
	 */
	public void setConDirFrmEstado(String conDirFrmEstado) {
		this.conDirFrmEstado = conDirFrmEstado;
	}
}
