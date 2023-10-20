package siges.adminconflicto.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class AdminConflicto  implements Serializable, Cloneable{

    //categoria
    private String idcategoria;
    private String valorcategoria;
    private String ordencategoria;
    
    //clase
    private String idclase;
    private String valorclase;
    private String ordenclase;
    
    //tipo
    private String idtipo;
    private String valortipo;
    private String tipodesctipo;
    private String descripciontipo;
    private String clasetipo;
    private String categoriatipo;
    private String ordentipo;
    
    
    private String estado;
    
    /**
     * @return Devuelve categoriatipo.
     */
    public String getCategoriatipo() {
        return (categoriatipo != null) ? categoriatipo : "";
    }
    /**
     * @param categoriatipo El categoriatipo a establecer.
     */
    public void setCategoriatipo(String categoriatipo) {
        this.categoriatipo = categoriatipo;
    }
    /**
     * @return Devuelve clasetipo.
     */
    public String getClasetipo() {
        return (clasetipo != null) ? clasetipo : "";
    }
    /**
     * @param clasetipo El clasetipo a establecer.
     */
    public void setClasetipo(String clasetipo) {
        this.clasetipo = clasetipo;
    }
    /**
     * @return Devuelve descripciontipo.
     */
    public String getDescripciontipo() {
        return (descripciontipo != null) ? descripciontipo : "";
    }
    /**
     * @param descripciontipo El descripciontipo a establecer.
     */
    public void setDescripciontipo(String descripciontipo) {
        this.descripciontipo = descripciontipo;
    }
    /**
     * @return Devuelve idtipo.
     */
    public String getIdtipo() {
        return (idtipo != null) ? idtipo : "";
    }
    /**
     * @param idtipo El idtipo a establecer.
     */
    public void setIdtipo(String idtipo) {
        this.idtipo = idtipo;
    }
    /**
     * @return Devuelve ordentipo.
     */
    public String getOrdentipo() {
        return (ordentipo != null) ? ordentipo : "";
    }
    /**
     * @param ordentipo El ordentipo a establecer.
     */
    public void setOrdentipo(String ordentipo) {
        this.ordentipo = ordentipo;
    }
    /**
     * @return Devuelve tipodesctipo.
     */
    public String getTipodesctipo() {
        return (tipodesctipo != null) ? tipodesctipo : "";
    }
    /**
     * @param tipodesctipo El tipodesctipo a establecer.
     */
    public void setTipodesctipo(String tipodesctipo) {
        this.tipodesctipo = tipodesctipo;
    }
    /**
     * @return Devuelve valortipo.
     */
    public String getValortipo() {
        return (valortipo != null) ? valortipo : "";
    }
    /**
     * @param valortipo El valortipo a establecer.
     */
    public void setValortipo(String valortipo) {
        this.valortipo = valortipo;
    }
    /**
     * @return Devuelve idclase.
     */
    public String getIdclase() {
        return (idclase != null) ? idclase : "";
    }
    /**
     * @param idclase El idclase a establecer.
     */
    public void setIdclase(String idclase) {
        this.idclase = idclase;
    }
    /**
     * @return Devuelve ordenclase.
     */
    public String getOrdenclase() {
        return (ordenclase != null) ? ordenclase : "";
    }
    /**
     * @param ordenclase El ordenclase a establecer.
     */
    public void setOrdenclase(String ordenclase) {
        this.ordenclase = ordenclase;
    }
    /**
     * @return Devuelve valorclase.
     */
    public String getValorclase() {
        return (valorclase != null) ? valorclase : "";
    }
    /**
     * @param valorclase El valorclase a establecer.
     */
    public void setValorclase(String valorclase) {
        this.valorclase = valorclase;
    }
    /**
     * @return Devuelve estado.
     */
    public String getEstado() {
        return (estado != null) ? estado : "";
    }
    /**
     * @param estado El estado a establecer.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * @return Devuelve idcategoria.
     */
    public String getIdcategoria() {
        return (idcategoria != null) ? idcategoria : "";
    }
    /**
     * @param idcategoria El idcategoria a establecer.
     */
    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }
    /**
     * @return Devuelve ordencategoria.
     */
    public String getOrdencategoria() {
        return (ordencategoria != null) ? ordencategoria : "";
    }
    /**
     * @param ordencategoria El ordencategoria a establecer.
     */
    public void setOrdencategoria(String ordencategoria) {
        this.ordencategoria = ordencategoria;
    }
    /**
     * @return Devuelve valorcategoria.
     */
    public String getValorcategoria() {
        return (valorcategoria != null) ? valorcategoria : "";
    }
    /**
     * @param valorcategoria El valorcategoria a establecer.
     */
    public void setValorcategoria(String valorcategoria) {
        this.valorcategoria = valorcategoria;
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