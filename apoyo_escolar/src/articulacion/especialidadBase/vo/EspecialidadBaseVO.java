package articulacion.especialidadBase.vo;


import siges.common.vo.Vo;

public class EspecialidadBaseVO extends Vo{
	private int  gespbasCodigo;
	private String gespbasNombre;
	
	public int getGespbasCodigo() {
		return gespbasCodigo;
	}
	public void setGespbasCodigo(int gespbasCodigo) {
		this.gespbasCodigo = gespbasCodigo;
	}
	public String getGespbasNombre() {
		return (gespbasNombre!=null)? gespbasNombre : "";
	}
	public void setGespbasNombre(String gespbasNombre) {
		this.gespbasNombre = gespbasNombre;
	}
	
	

	
	
	
}

