package siges.admincursos.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class AdminCursos implements Serializable, Cloneable{
    
    private String nombre;
    private String cupos;
    private String grupos;
    private String jornada;
    private String cupototal;
    private String visible;
    private String responsable;
    private String correoresp;
    private String nivel;
    private String codigo;
    private String estado;
    
    /**
     * @return Devuelve codigo.
     */
    public String getCodigo() {
        return (codigo != null) ? codigo : "";
    }
    /**
     * @param codigo El codigo a establecer.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    /**
     * @return Devuelve correoresp.
     */
    public String getCorreoresp() {
        return (correoresp != null) ? correoresp : "";
    }
    /**
     * @param correoresp El correoresp a establecer.
     */
    public void setCorreoresp(String correoresp) {
        this.correoresp = correoresp;
    }
    /**
     * @return Devuelve cupos.
     */
    public String getCupos() {
        return (cupos != null) ? cupos : "";
    }
    /**
     * @param cupos El cupos a establecer.
     */
    public void setCupos(String cupos) {
        this.cupos = cupos;
    }
    /**
     * @return Devuelve cupototal.
     */
    public String getCupototal() {
        return (cupototal != null) ? cupototal : "";
    }
    /**
     * @param cupototal El cupototal a establecer.
     */
    public void setCupototal(String cupototal) {
        this.cupototal = cupototal;
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
     * @return Devuelve grupos.
     */
    public String getGrupos() {
        return (grupos != null) ? grupos : "";
    }
    /**
     * @param grupos El grupos a establecer.
     */
    public void setGrupos(String grupos) {
        this.grupos = grupos;
    }
    /**
     * @return Devuelve jornada.
     */
    public String getJornada() {
        return (jornada != null) ? jornada : "";
    }
    /**
     * @param jornada El jornada a establecer.
     */
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }
    /**
     * @return Devuelve nivel.
     */
    public String getNivel() {
        return (nivel != null) ? nivel : "";
    }
    /**
     * @param nivel El nivel a establecer.
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    /**
     * @return Devuelve nombre.
     */
    public String getNombre() {
        return (nombre != null) ? nombre : "";
    }
    /**
     * @param nombre El nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * @return Devuelve responsable.
     */
    public String getResponsable() {
        return (responsable != null) ? responsable : "";
    }
    /**
     * @param responsable El responsable a establecer.
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    /**
     * @return Devuelve visible.
     */
    public String getVisible() {
        return (visible != null) ? visible : "";
    }
    /**
     * @param visible El visible a establecer.
     */
    public void setVisible(String visible) {
        this.visible = visible;
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
