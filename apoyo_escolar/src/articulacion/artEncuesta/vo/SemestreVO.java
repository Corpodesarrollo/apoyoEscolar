/**
 * 
 */
package articulacion.artEncuesta.vo;

import java.util.List;

/**
 * 18/11/2007 
 * @author Latined
 * @version 1.2
 */
public class SemestreVO {
	private int codigo;
	private String nombre;
	private List listaArea;
	private int size;
	
	/**
	 * @return Return the codigo.
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Return the listaArea.
	 */
	public List getListaArea() {
		return listaArea;
	}
	/**
	 * @param listaArea The listaArea to set.
	 */
	public void setListaArea(List listaArea) {
		this.listaArea = listaArea;
	}
	/**
	 * @return Return the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Return the size.
	 */
	public int getSize() {
		if(listaArea==null) return 1;
		int count=listaArea.size()+1;
		return count;
	}
}
