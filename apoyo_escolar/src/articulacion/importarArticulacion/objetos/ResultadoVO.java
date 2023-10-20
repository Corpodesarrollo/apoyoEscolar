package articulacion.importarArticulacion.objetos;

public class ResultadoVO {
	private int actualizados=0;
	private int eliminados=0;
	private int ingresados=0;
	
	
	
	public int getActualizados() {
		return actualizados;
	}
	public void setActualizados(int actualizados) {
		this.actualizados = actualizados;
	}
	public int getEliminados() {
		return eliminados;
	}
	public void setEliminados(int eliminados) {
		this.eliminados = eliminados;
	}
	public int getIngresados() {
		return ingresados;
	}
	public void setIngresados(int ingresados) {
		this.ingresados = ingresados;
	}
}
