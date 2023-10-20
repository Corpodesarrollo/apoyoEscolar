package articulacion.encuesta.vo;

import java.util.List;

public class UniversidadVO {

	private long codigo;
	private String nombre;
	private List listaColegio;
	private int size;
	/**
	 * @return Returns the codigo.
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Returns the listaColegio.
	 */
	public List getListaColegio() {
		return listaColegio;
	}
	/**
	 * @param listaColegio The listaColegio to set.
	 */
	public void setListaColegio(List listaColegio) {
		this.listaColegio = listaColegio;
	}
	/**
	 * @return Returns the nombre.
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
		//return listaColegio!=null?listaColegio.size():1;
		if(listaColegio==null) return 1;
		int count=listaColegio.size()+1;
		/*
		ColegioVO col=null;
		for(int i=0;i<listaColegio.size();i++){
			col=(ColegioVO)listaColegio.get(i);
			if(col!=null) count+=col.getSize();
		}
		*/
		return count;
	}
	/**
	 * @param size The size to set.
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
