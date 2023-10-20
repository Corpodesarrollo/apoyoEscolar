/**
 * 
 */
package siges.importarFotografia.vo;

/**
 * 24/09/2008 
 * @author Latined
 * @version 1.2
 */
public class FotografiaVO {
	private int tipoDocumento;
	private long numeroDocumento;
	private String numeroDocumento2;
	
	/**
	 * Constructor
	 *  
	 * @param tipoDocumento
	 * @param numeroDocumento2
	 */
	public FotografiaVO(int tipoDocumento, String numeroDocumento2) {
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento2 = numeroDocumento2;
	}
	/**
	 * @return Return the numeroDocumento.
	 */
	public long getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento The numeroDocumento to set.
	 */
	public void setNumeroDocumento(long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @return Return the tipoDocumento.
	 */
	public int getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento The tipoDocumento to set.
	 */
	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * Constructor
	 *  
	 * @param tipoDocumento
	 * @param numeroDocumento
	 */
	public FotografiaVO(int tipoDocumento, long numeroDocumento) {
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @return Return the numeroDocumento2.
	 */
	public String getNumeroDocumento2() {
		return numeroDocumento2;
	}
	/**
	 * @param numeroDocumento2 The numeroDocumento2 to set.
	 */
	public void setNumeroDocumento2(String numeroDocumento2) {
		this.numeroDocumento2 = numeroDocumento2;
	}
}
