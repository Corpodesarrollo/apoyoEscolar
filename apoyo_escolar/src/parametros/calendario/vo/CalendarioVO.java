package parametros.calendario.vo;

public class CalendarioVO {
	
	String fecha;
	String motivo;
	/**
	 * Estado 1 = nuevo registro
	 * Estado 2 = Actualizar registro
	 */
	int estado = 1;
	
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
