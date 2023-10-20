package siges.institucion.organizacion.beans;

public class ConsejoAcademicoVO  implements Cloneable{
	private String conAcaInst;
	private String conAcaSede;
	private String conAcaJornada;
	private String conAcaCargo;
	private String conAcaTipoDoc;
	private String conAcaNumDoc;
	private String conAcaTelefono;
	private String conAcaCorreo;
	private String conAcaEstado;
	private String conAcaFrmEstado;

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

	public String getConAcaCargo() {
		return conAcaCargo!=null?conAcaCargo:"";
	}

	public void setConAcaCargo(String conAcaCargo) {
		this.conAcaCargo = conAcaCargo;
	}

	public String getConAcaCorreo() {
		return conAcaCorreo!=null?conAcaCorreo:"";
	}

	public void setConAcaCorreo(String conAcaCorreo) {
		this.conAcaCorreo = conAcaCorreo;
	}

	public String getConAcaEstado() {
		return conAcaEstado!=null?conAcaEstado:"";
	}

	public void setConAcaEstado(String conAcaEstado) {
		this.conAcaEstado = conAcaEstado;
	}

	public String getConAcaInst() {
		return conAcaInst!=null?conAcaInst:"";
	}

	public void setConAcaInst(String conAcaInst) {
		this.conAcaInst = conAcaInst;
	}

	public String getConAcaJornada() {
		return conAcaJornada!=null?conAcaJornada:"";
	}

	public void setConAcaJornada(String conAcaJornada) {
		this.conAcaJornada = conAcaJornada;
	}

	public String getConAcaNumDoc() {
		return conAcaNumDoc!=null?conAcaNumDoc:"";
	}

	public void setConAcaNumDoc(String conAcaNumDoc) {
		this.conAcaNumDoc = conAcaNumDoc;
	}

	public String getConAcaSede() {
		return conAcaSede!=null?conAcaSede:"";
	}

	public void setConAcaSede(String conAcaSede) {
		this.conAcaSede = conAcaSede;
	}

	public String getConAcaTelefono() {
		return conAcaTelefono!=null?conAcaTelefono:"";
	}

	public void setConAcaTelefono(String conAcaTelefono) {
		this.conAcaTelefono = conAcaTelefono;
	}

	public String getConAcaTipoDoc() {
		return conAcaTipoDoc!=null?conAcaTipoDoc:"";
	}

	public void setConAcaTipoDoc(String conAcaTipoDoc) {
		this.conAcaTipoDoc = conAcaTipoDoc;
	}

	/**
	 * @return Returns the conAcaFrmEstado.
	 */
	public String getConAcaFrmEstado() {
		return conAcaFrmEstado!=null?conAcaFrmEstado:"";
	}

	/**
	 * @param conAcaFrmEstado The conAcaFrmEstado to set.
	 */
	public void setConAcaFrmEstado(String conAcaFrmEstado) {
		this.conAcaFrmEstado = conAcaFrmEstado;
	}
}
