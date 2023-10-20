/**
 * 
 */
package participacion.instancia.vo;

import siges.common.vo.Vo;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class RepresentanteVO extends Vo{
	private int repInstancia;
	private int repCodigo;
	private int repCantidad;
	private String repElige;
	private String repNombre;
	private String repChecked;
	private String [] repRoles;
	
	/**
	 * @return Return the repCodigo.
	 */
	public final int getRepCodigo() {
		return repCodigo;
	}
	/**
	 * @param repCodigo The repCodigo to set.
	 */
	public final void setRepCodigo(int repCodigo) {
		this.repCodigo = repCodigo;
	}
	/**
	 * @return Return the repElige.
	 */
	public final String getRepElige() {
		return repElige;
	}
	/**
	 * @param repElige The repElige to set.
	 */
	public final void setRepElige(String repElige) {
		this.repElige = repElige;
	}
	/**
	 * @return Return the repInstancia.
	 */
	public final int getRepInstancia() {
		return repInstancia;
	}
	/**
	 * @param repInstancia The repInstancia to set.
	 */
	public final void setRepInstancia(int repInstancia) {
		this.repInstancia = repInstancia;
	}
	/**
	 * @return Return the repNombre.
	 */
	public final String getRepNombre() {
		return repNombre;
	}
	/**
	 * @param repNombre The repNombre to set.
	 */
	public final void setRepNombre(String repNombre) {
		this.repNombre = repNombre;
	}
	/**
	 * @return Return the repChecked.
	 */
	public final String getRepChecked() {
		return repChecked;
	}
	/**
	 * @param repChecked The repChecked to set.
	 */
	public final void setRepChecked(String repChecked) {
		this.repChecked = repChecked;
	}
	/**
	 * @return Return the repRoles.
	 */
	public final String[] getRepRoles() {
		return repRoles;
	}
	/**
	 * @param repRoles The repRoles to set.
	 */
	public final void setRepRoles(String[] repRoles) {
		this.repRoles = repRoles;
	}
	/**
	 * @return Return the repCantidad.
	 */
	public final int getRepCantidad() {
		return repCantidad;
	}
	/**
	 * @param repCantidad The repCantidad to set.
	 */
	public final void setRepCantidad(int repCantidad) {
		this.repCantidad = repCantidad;
	}
	
}
