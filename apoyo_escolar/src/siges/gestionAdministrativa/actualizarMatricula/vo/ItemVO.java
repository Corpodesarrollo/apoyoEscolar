/**
 * 
 */
package siges.gestionAdministrativa.actualizarMatricula.vo;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ItemVO {
	private int codigo;
	private  String nombre;
	
	public ItemVO(int codigo, String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
			
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
