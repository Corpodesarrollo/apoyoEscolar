package siges.grupoPeriodo.beans;

/**
*	Nombre:	AbrirGrupo<BR>
*	Descripcinn:	Bean de información de la tabla 'cierre grupo y periodo'	<BR>
*	Funciones de la pngina:	almacenar informacion de cierre de grupos y periodo	<BR>
*	Entidades afectadas:	Cierre_grupo y Periodo	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class CierreVO implements Cloneable{
   private String cieSed;//1
   private String cieJor;//2
   private String ciePer;//3
   private String cieEsta;//4
   private String cieInst;//4


/**
 *	Hace una copia del objeto mismo
 *	@return Object 
 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
    }	
/**
 * @return Returns the cieEsta.
 */
public String getCieEsta() {
    return cieEsta!=null?cieEsta:"";
}
/**
 * @param cieEsta The cieEsta to set.
 */
public void setCieEsta(String cieEsta) {
    this.cieEsta = cieEsta;
}
/**
 * @return Returns the cieJor.
 */
public String getCieJor() {
    return cieJor!=null?cieJor:"";
}
/**
 * @param cieJor The cieJor to set.
 */
public void setCieJor(String cieJor) {
    this.cieJor = cieJor;
}
/**
 * @return Returns the ciePer.
 */
public String getCiePer() {
    return ciePer!=null?ciePer:"";
}
/**
 * @param ciePer The ciePer to set.
 */
public void setCiePer(String ciePer) {
    this.ciePer = ciePer;
}
/**
 * @return Returns the cieSed.
 */
public String getCieSed() {
    return cieSed!=null?cieSed:"";
}
/**
 * @param cieSed The cieSed to set.
 */
public void setCieSed(String cieSed) {
    this.cieSed = cieSed;
}
/**
 * @return Returns the cieInst.
 */
public String getCieInst() {
    return cieInst!=null?cieInst:"";
}
/**
 * @param cieInst The cieInst to set.
 */
public void setCieInst(String cieInst) {
    this.cieInst = cieInst;
}
}