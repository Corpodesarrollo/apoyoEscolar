package siges.institucion.organizacion.beans;

public class LiderVO  implements Cloneable{
	private String lidCodJerar;
	private String lidCodEstud;
	private String lidCargo;
	private String lidTelefono;
	private String lidCorreo;
	private String lidFechaInicio;
	private String lidEstado;
	private String lidInst;
	private String lidSede;
	private String lidJor;
	private String lidMet;
	private String lidGrado;
	private String lidGrupo;
	private String lidSedeJornada;
	private String lidFrmEstado;


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


	public String getLidCargo() {
		return lidCargo!=null?lidCargo:"";
	}


	public void setLidCargo(String lidCargo) {
		this.lidCargo = lidCargo;
	}


	public String getLidCodEstud() {
		return lidCodEstud!=null?lidCodEstud:"";
	}


	public void setLidCodEstud(String lidCodEstud) {
		this.lidCodEstud = lidCodEstud;
	}


	public String getLidCodJerar() {
		return lidCodJerar!=null?lidCodJerar:"";
	}


	public void setLidCodJerar(String lidCodJerar) {
		this.lidCodJerar = lidCodJerar;
	}


	public String getLidCorreo() {
		return lidCorreo!=null?lidCorreo:"";
	}


	public void setLidCorreo(String lidCorreo) {
		this.lidCorreo = lidCorreo;
	}


	public String getLidEstado() {
		return lidEstado!=null?lidEstado:"";
	}


	public void setLidEstado(String lidEstado) {
		this.lidEstado = lidEstado;
	}


	public String getLidFechaInicio() {
		return lidFechaInicio!=null?lidFechaInicio:"";
	}


	public void setLidFechaInicio(String lidFechaInicio) {
		this.lidFechaInicio = lidFechaInicio;
	}


	public String getLidTelefono() {
		return lidTelefono!=null?lidTelefono:"";
	}


	public void setLidTelefono(String lidTelefono) {
		this.lidTelefono = lidTelefono;
	}


	public String getLidGrado() {
		return lidGrado;
	}


	public void setLidGrado(String lidGrado) {
		this.lidGrado = lidGrado;
	}


	public String getLidGrupo() {
		return lidGrupo;
	}


	public void setLidGrupo(String lidGrupo) {
		this.lidGrupo = lidGrupo;
	}


	public String getLidInst() {
		return lidInst!=null?lidInst:"";
	}


	public void setLidInst(String lidInst) {
		this.lidInst = lidInst;
	}


	public String getLidJor() {
		return lidJor!=null?lidJor:"";
	}


	public void setLidJor(String lidJor) {
		this.lidJor = lidJor;
	}


	public String getLidMet() {
		return lidMet!=null?lidMet:"";
	}


	public void setLidMet(String lidMet) {
		this.lidMet = lidMet;
	}


	public String getLidSede() {
		return lidSede!=null?lidSede:"";
	}


	public void setLidSede(String lidSede) {
		this.lidSede = lidSede;
	}


	/**
	 * @return Returns the lidFrmEstado.
	 */
	public String getLidFrmEstado() {
		return lidFrmEstado!=null?lidFrmEstado:"";
	}


	/**
	 * @param lidFrmEstado The lidFrmEstado to set.
	 */
	public void setLidFrmEstado(String lidFrmEstado) {
		this.lidFrmEstado = lidFrmEstado;
	}


	public String getLidSedeJornada() {
		return lidSedeJornada!=null?lidSedeJornada:"";
	}


	public void setLidSedeJornada(String lidSedeJornada) {
		this.lidSedeJornada = lidSedeJornada;
	}
}
