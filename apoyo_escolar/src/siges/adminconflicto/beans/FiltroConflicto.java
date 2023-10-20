package siges.adminconflicto.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class FiltroConflicto  implements Serializable, Cloneable{

    //filtro
    private String catnombre;
    private String clanombre;
    private String fcategoria;
    private String fclase;

    /**
     * @return Devuelve catnombre.
     */
    public String getCatnombre() {
        return (catnombre != null) ? catnombre : "";
    }
    /**
     * @param catnombre El catnombre a establecer.
     */
    public void setCatnombre(String catnombre) {
        this.catnombre = catnombre;
    }
    /**
     * @return Devuelve clanombre.
     */
    public String getClanombre() {
        return (clanombre != null) ? clanombre : "";
    }
    /**
     * @param clanombre El clanombre a establecer.
     */
    public void setClanombre(String clanombre) {
        this.clanombre = clanombre;
    }
    /**
     * @return Devuelve fcategoria.
     */
    public String getFcategoria() {
        return (fcategoria != null) ? fcategoria : "";
    }
    /**
     * @param fcategoria El fcategoria a establecer.
     */
    public void setFcategoria(String fcategoria) {
        this.fcategoria = fcategoria;
    }
    /**
     * @return Devuelve fclase.
     */
    public String getFclase() {
        return (fclase != null) ? fclase : "";
    }
    /**
     * @param fclase El fclase a establecer.
     */
    public void setFclase(String fclase) {
        this.fclase = fclase;
    }
    /**
   	*	Hace una copia del objeto mismo
   	*	@return Object 
   	*/
    public Object clone() {
        Object o = null;
        try{
            o = super.clone();
        }
        catch(CloneNotSupportedException e) {
            System.err.println("MyObject can't clone");
        }
        return o;
    }

}