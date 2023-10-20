package siges.rotacion.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class Rotacion implements Serializable, Cloneable{
	private int vigencia;
	
    //filtro espfis x jornada
    private String fespacio;
    private String fsede;
    private String fjornada;
    
    //estructura
    private String rotstrcodigo;
    private String rotstrnombre;
    private String rotstrsede;
    private String rotstrjornada;
    private String rotstrmetodologia;
    private String rotstrdurblq;
    private String rotstrdurhor;
    private String rotstrnumblq;
    private String rotstrsemcic;
    private String rotstrhoraini;
    private String rotstrhorafin;
    private String rotstrjercodigo;
    private String rotstrihtotal;
    
    //estructura grado
    private String rtesgr_strcod;
    private String []rtesgrgrado;
    private String []rtesgrvalidar;
    
    //tipo receso
    private String rttiecodigo;
    private String rttienombre;
    private String rttieorden;
    
    //receso
    private String []rotreccodigo;
    private String []rotrec_strcod;
    private String []rotrechoraini;
    private String []rotrechorafin;
    private String []rotrec_tiprec;
    private String []rotrecdescripcion;
    
    //fijar espacios
    private String roteagjerjornada;
    private String roteagjergrado;
    private String roteagespacio;
    private String roteagasignatura;
    private String roteagsede;
    private String roteagjornada;
    private String []roteaggrados;
    private String roteagexclusivo;
    
    //fijar docentes
    private String rotdagjerjornada;
    private String rotdagjergrado;
    private String rotdagdocente;
    private String rotdagasignatura;
    private String rotdagsede;
    private String rotdagjornada;
    private String rotdaghoras;
    private String []rotdaggrados;
    
    //inhabilitar espacios
    private String rotefjestructura;
    private String rotefjsede;
    private String rotefjjornada;
    private String rotefjespacio;
    private String rotefjdia;
    private String rotefjhoraini;
    private String rotefjhorafin;
    private String rotefjcausa;
    
    //esp inhabilitar docentes
    private String rotihdestructura;
    private String rotihdsede;
    private String rotihdjornada;
    private String rotihddocente;
    private String rotihddia;
    private String rotihdhoraini;
    private String rotihdhorafin;
    private String rotihdcausa;

    //fijar asignatura
    private String rotfasestructura;
    private String rotfasjergrado;
    private String rotfasasignatura;
    private String rotfassede;
    private String rotfasjornada;
    private String rotfasblqminimo;
    private String []rotfasgrados;
    
    //fijar espacio por docente
    private String rtefdoespacio;
    private String rtefdodocente;
    private String rtefdojercodigo;
    private String rtefdosede;
    private String rtefdojornada;
    
    //prioridad
    private String []estructura_;
    private String []grado_;
    private String []asignatura_;
    private String priorizar_;
    private String instjerar_;
    private String prioAsig1;
    private String prioAsig2;
    //private String asig;
    //private String grado;
    
    //inhabilitar HORAS
    private String rotihestructura;
    private String rotihsede;
    private String rotihjornada;
    private String rotihdia;
    private String rotihhoraini;
    private String rotihhorafin;
    private String rotihcausa;
    
    private String estado;
    
    /**
     * @return Devuelve rotstrihtotal.
     */
    public String getRotstrihtotal() {
        return (rotstrihtotal != null) ? rotstrihtotal : "";
    }
    /**
     * @param rotstrihtotal El rotstrihtotal a establecer.
     */
    public void setRotstrihtotal(String rotstrihtotal) {
        this.rotstrihtotal = rotstrihtotal;
    }
    /**
     * @return Devuelve rtesgrvalidar.
     */
    public String[] getRtesgrvalidar() {
        return rtesgrvalidar;
    }
    /**
     * @param rtesgrvalidar El rtesgrvalidar a establecer.
     */
    public void setRtesgrvalidar(String[] rtesgrvalidar) {
        this.rtesgrvalidar = rtesgrvalidar;
    }
    /**
     * @return Devuelve instjerar.
     */
    public String getInstjerar_() {
        return (instjerar_ != null) ? instjerar_ : "";
    }
    /**
     * @param instjerar El instjerar a establecer.
     */
    public void setInstjerar_(String instjerar_) {
        this.instjerar_ = instjerar_;
    }
    /**
     * @return Devuelve priorizar_.
     */
    public String getPriorizar_() {
        return (priorizar_ != null) ? priorizar_ : "";
    }
    /**
     * @param priorizar_ El priorizar_ a establecer.
     */
    public void setPriorizar_(String priorizar_) {
        this.priorizar_ = priorizar_;
    }
    /**
     * @return Devuelve asig.
     */
    /*public String getAsig() {
        return (asig != null) ? asig : "";
    }*/
    /**
     * @param asig El asig a establecer.
     */
    /*public void setAsig(String asig) {
        this.asig = asig;
    }*/
    /**
     * @return Devuelve grado.
     */
    /*public String getGrado() {
        return (grado != null) ? grado : "";
    }*/
    /**
     * @param grado El grado a establecer.
     */
    /*public void setGrado(String grado) {
        this.grado = grado;
    }*/
    /**
     * @return Devuelve asignatura_.
     */
    public String[] getAsignatura_() {
        return asignatura_;
    }
    /**
     * @param asignatura_ El asignatura_ a establecer.
     */
    public void setAsignatura_(String[] asignatura_) {
        this.asignatura_ = asignatura_;
    }
    /**
     * @return Devuelve estructura_.
     */
    public String[] getEstructura_() {
        return estructura_;
    }
    /**
     * @param estructura_ El estructura_ a establecer.
     */
    public void setEstructura_(String[] estructura_) {
        this.estructura_ = estructura_;
    }
    /**
     * @return Devuelve grados_.
     */
    public String[] getGrado_() {
        return grado_;
    }
    /**
     * @param grados_ El grados_ a establecer.
     */
    public void setGrado_(String[] grado_) {
        this.grado_ = grado_;
    }
    /**
     * @return Devuelve rtefdodocente.
     */
    public String getRtefdodocente() {
        return (rtefdodocente != null) ? rtefdodocente : "";
    }
    /**
     * @param rtefdodocente El rtefdodocente a establecer.
     */
    public void setRtefdodocente(String rtefdodocente) {
        this.rtefdodocente = rtefdodocente;
    }
    /**
     * @return Devuelve rtefdoespacio.
     */
    public String getRtefdoespacio() {
        return (rtefdoespacio != null) ? rtefdoespacio : "";
    }
    /**
     * @param rtefdoespacio El rtefdoespacio a establecer.
     */
    public void setRtefdoespacio(String rtefdoespacio) {
        this.rtefdoespacio = rtefdoespacio;
    }
    /**
     * @return Devuelve rtefdojercodigo.
     */
    public String getRtefdojercodigo() {
        return (rtefdojercodigo != null) ? rtefdojercodigo : "";
    }
    /**
     * @param rtefdojercodigo El rtefdojercodigo a establecer.
     */
    public void setRtefdojercodigo(String rtefdojercodigo) {
        this.rtefdojercodigo = rtefdojercodigo;
    }
    /**
     * @return Devuelve rtefdojornada.
     */
    public String getRtefdojornada() {
        return (rtefdojornada != null) ? rtefdojornada : "";
    }
    /**
     * @param rtefdojornada El rtefdojornada a establecer.
     */
    public void setRtefdojornada(String rtefdojornada) {
        this.rtefdojornada = rtefdojornada;
    }
    /**
     * @return Devuelve rtefdosede.
     */
    public String getRtefdosede() {
        return (rtefdosede != null) ? rtefdosede : "";
    }
    /**
     * @param rtefdosede El rtefdosede a establecer.
     */
    public void setRtefdosede(String rtefdosede) {
        this.rtefdosede = rtefdosede;
    }
    /**
     * @return Devuelve rotfasestructura.
     */
    public String getRotfasestructura() {
        return (rotfasestructura != null) ? rotfasestructura : "";
    }
    /**
     * @param rotfasestructura El rotfasestructura a establecer.
     */
    public void setRotfasestructura(String rotfasestructura) {
        this.rotfasestructura = rotfasestructura;
    }
    /**
     * @return Devuelve rotfasasignatura.
     */
    public String getRotfasasignatura() {
        return (rotfasasignatura != null) ? rotfasasignatura : "";
    }
    /**
     * @param rotfasasignatura El rotfasasignatura a establecer.
     */
    public void setRotfasasignatura(String rotfasasignatura) {
        this.rotfasasignatura = rotfasasignatura;
    }
    /**
     * @return Devuelve rotfasblqminimo.
     */
    public String getRotfasblqminimo() {
        return (rotfasblqminimo != null) ? rotfasblqminimo : "";
    }
    /**
     * @param rotfasblqminimo El rotfasblqminimo a establecer.
     */
    public void setRotfasblqminimo(String rotfasblqminimo) {
        this.rotfasblqminimo = rotfasblqminimo;
    }
    /**
     * @return Devuelve rotfasgrados.
     */
    public String[] getRotfasgrados() {
        return rotfasgrados;
    }
    /**
     * @param rotfasgrados El rotfasgrados a establecer.
     */
    public void setRotfasgrados(String[] rotfasgrados) {
        this.rotfasgrados = rotfasgrados;
    }
    /**
     * @return Devuelve rotfasjergrado.
     */
    public String getRotfasjergrado() {
        return (rotfasjergrado != null) ? rotfasjergrado : "";
    }
    /**
     * @param rotfasjergrado El rotfasjergrado a establecer.
     */
    public void setRotfasjergrado(String rotfasjergrado) {
        this.rotfasjergrado = rotfasjergrado;
    }
    /**
     * @return Devuelve rotfasjornada.
     */
    public String getRotfasjornada() {
        return (rotfasjornada != null) ? rotfasjornada : "";
    }
    /**
     * @param rotfasjornada El rotfasjornada a establecer.
     */
    public void setRotfasjornada(String rotfasjornada) {
        this.rotfasjornada = rotfasjornada;
    }
    /**
     * @return Devuelve rotfassede.
     */
    public String getRotfassede() {
        return (rotfassede != null) ? rotfassede : "";
    }
    /**
     * @param rotfassede El rotfassede a establecer.
     */
    public void setRotfassede(String rotfassede) {
        this.rotfassede = rotfassede;
    }
    /**
     * @return Devuelve rotdaghoras.
     */
    public String getRotdaghoras() {
        return (rotdaghoras != null) ? rotdaghoras : "";
    }
    /**
     * @param rotdaghoras El rotdaghoras a establecer.
     */
    public void setRotdaghoras(String rotdaghoras) {
        this.rotdaghoras = rotdaghoras;
    }
    /**
     * @return Devuelve rotdagjergrado.
     */
    public String getRotdagjergrado() {
        return (rotdagjergrado != null) ? rotdagjergrado : "";
    }
    /**
     * @param rotdagjergrado El rotdagjergrado a establecer.
     */
    public void setRotdagjergrado(String rotdagjergrado) {
        this.rotdagjergrado = rotdagjergrado;
    }
    /**
     * @return Devuelve rotdagjerjornada.
     */
    public String getRotdagjerjornada() {
        return (rotdagjerjornada != null) ? rotdagjerjornada : "";
    }
    /**
     * @param rotdagjerjornada El rotdagjerjornada a establecer.
     */
    public void setRotdagjerjornada(String rotdagjerjornada) {
        this.rotdagjerjornada = rotdagjerjornada;
    }
    /**
     * @return Devuelve rotihddia.
     */
    public String getRotihddia() {
        return (rotihddia != null) ? rotihddia : "";
    }
    /**
     * @param rotihddia El rotihddia a establecer.
     */
    public void setRotihddia(String rotihddia) {
        this.rotihddia = rotihddia;
    }
    /**
     * @return Devuelve rotihdestructura.
     */
    public String getRotihdestructura() {
        return (rotihdestructura != null) ? rotihdestructura : "";
    }
    /**
     * @param rotihdestructura El rotihdestructura a establecer.
     */
    public void setRotihdestructura(String rotihdestructura) {
        this.rotihdestructura = rotihdestructura;
    }
    /**
     * @return Devuelve rotefjdia.
     */
    public String getRotefjdia() {
        return (rotefjdia != null) ? rotefjdia : "";
    }
    /**
     * @param rotefjdia El rotefjdia a establecer.
     */
    public void setRotefjdia(String rotefjdia) {
        this.rotefjdia = rotefjdia;
    }
    /**
     * @return Devuelve rotefjestructura.
     */
    public String getRotefjestructura() {
        return (rotefjestructura != null) ? rotefjestructura : "";
    }
    /**
     * @param rotefjestructura El rotefjestructura a establecer.
     */
    public void setRotefjestructura(String rotefjestructura) {
        this.rotefjestructura = rotefjestructura;
    }
    /**
     * @return Devuelve roteagjergrado.
     */
    public String getRoteagjergrado() {
        return (roteagjergrado != null) ? roteagjergrado : "";
    }
    /**
     * @param roteagjergrado El roteagjergrado a establecer.
     */
    public void setRoteagjergrado(String roteagjergrado) {
        this.roteagjergrado = roteagjergrado;
    }
    /**
     * @return Devuelve roteagjerjornada.
     */
    public String getRoteagjerjornada() {
        return (roteagjerjornada != null) ? roteagjerjornada : "";
    }
    /**
     * @param roteagjerjornada El roteagjerjornada a establecer.
     */
    public void setRoteagjerjornada(String roteagjerjornada) {
        this.roteagjerjornada = roteagjerjornada;
    }
    /**
     * @return Devuelve rotstrjercodigo.
     */
    public String getRotstrjercodigo() {
        return (rotstrjercodigo != null) ? rotstrjercodigo : "";
    }
    /**
     * @param rotstrjercodigo El rotstrjercodigo a establecer.
     */
    public void setRotstrjercodigo(String rotstrjercodigo) {
        this.rotstrjercodigo = rotstrjercodigo;
    }
    /**
     * @return Devuelve fespacio.
     */
    public String getFespacio() {
        return (fespacio != null) ? fespacio : "";
    }
    /**
     * @param fespacio El fespacio a establecer.
     */
    public void setFespacio(String fespacio) {
        this.fespacio = fespacio;
    }
    /**
     * @return Devuelve fjornada.
     */
    public String getFjornada() {
        return (fjornada != null) ? fjornada : "";
    }
    /**
     * @param fjornada El fjornada a establecer.
     */
    public void setFjornada(String fjornada) {
        this.fjornada = fjornada;
    }
    /**
     * @return Devuelve fsede.
     */
    public String getFsede() {
        return (fsede != null) ? fsede : "";
    }
    /**
     * @param fsede El fsede a establecer.
     */
    public void setFsede(String fsede) {
        this.fsede = fsede;
    }
    /**
     * @return Devuelve rotihdcausa.
     */
    public String getRotihdcausa() {
        return (rotihdcausa != null) ? rotihdcausa : "";
    }
    /**
     * @param rotihdcausa El rotihdcausa a establecer.
     */
    public void setRotihdcausa(String rotihdcausa) {
        this.rotihdcausa = rotihdcausa;
    }
    /**
     * @return Devuelve rotihddocente.
     */
    public String getRotihddocente() {
        return (rotihddocente != null) ? rotihddocente : "";
    }
    /**
     * @param rotihddocente El rotihddocente a establecer.
     */
    public void setRotihddocente(String rotihddocente) {
        this.rotihddocente = rotihddocente;
    }
    /**
     * @return Devuelve rotihdhorafin.
     */
    public String getRotihdhorafin() {
        return (rotihdhorafin != null) ? rotihdhorafin : "";
    }
    /**
     * @param rotihdhorafin El rotihdhorafin a establecer.
     */
    public void setRotihdhorafin(String rotihdhorafin) {
        this.rotihdhorafin = rotihdhorafin;
    }
    /**
     * @return Devuelve rotihdhoraini.
     */
    public String getRotihdhoraini() {
        return (rotihdhoraini != null) ? rotihdhoraini : "";
    }
    /**
     * @param rotihdhoraini El rotihdhoraini a establecer.
     */
    public void setRotihdhoraini(String rotihdhoraini) {
        this.rotihdhoraini = rotihdhoraini;
    }
    /**
     * @return Devuelve rotihdjornada.
     */
    public String getRotihdjornada() {
        return (rotihdjornada != null) ? rotihdjornada : "";
    }
    /**
     * @param rotihdjornada El rotihdjornada a establecer.
     */
    public void setRotihdjornada(String rotihdjornada) {
        this.rotihdjornada = rotihdjornada;
    }
    /**
     * @return Devuelve rotihdsede.
     */
    public String getRotihdsede() {
        return (rotihdsede != null) ? rotihdsede : "";
    }
    /**
     * @param rotihdsede El rotihdsede a establecer.
     */
    public void setRotihdsede(String rotihdsede) {
        this.rotihdsede = rotihdsede;
    }
    /**
     * @return Devuelve rotefjcausa.
     */
    public String getRotefjcausa() {
        return (rotefjcausa != null) ? rotefjcausa : "";
    }
    /**
     * @param rotefjcausa El rotefjcausa a establecer.
     */
    public void setRotefjcausa(String rotefjcausa) {
        this.rotefjcausa = rotefjcausa;
    }
    /**
     * @return Devuelve rotefjespacio.
     */
    public String getRotefjespacio() {
        return (rotefjespacio != null) ? rotefjespacio : "";
    }
    /**
     * @param rotefjespacio El rotefjespacio a establecer.
     */
    public void setRotefjespacio(String rotefjespacio) {
        this.rotefjespacio = rotefjespacio;
    }
    /**
     * @return Devuelve rotefjhorafin.
     */
    public String getRotefjhorafin() {
        return (rotefjhorafin != null) ? rotefjhorafin : "";
    }
    /**
     * @param rotefjhorafin El rotefjhorafin a establecer.
     */
    public void setRotefjhorafin(String rotefjhorafin) {
        this.rotefjhorafin = rotefjhorafin;
    }
    /**
     * @return Devuelve rotefjhoraini.
     */
    public String getRotefjhoraini() {
        return (rotefjhoraini != null) ? rotefjhoraini : "";
    }
    /**
     * @param rotefjhoraini El rotefjhoraini a establecer.
     */
    public void setRotefjhoraini(String rotefjhoraini) {
        this.rotefjhoraini = rotefjhoraini;
    }
    /**
     * @return Devuelve rotefjjornada.
     */
    public String getRotefjjornada() {
        return (rotefjjornada != null) ? rotefjjornada : "";
    }
    /**
     * @param rotefjjornada El rotefjjornada a establecer.
     */
    public void setRotefjjornada(String rotefjjornada) {
        this.rotefjjornada = rotefjjornada;
    }
    /**
     * @return Devuelve rotefjsede.
     */
    public String getRotefjsede() {
        return (rotefjsede != null) ? rotefjsede : "";
    }
    /**
     * @param rotefjsede El rotefjsede a establecer.
     */
    public void setRotefjsede(String rotefjsede) {
        this.rotefjsede = rotefjsede;
    }
    /**
     * @return Devuelve rotdagasignatura.
     */
    public String getRotdagasignatura() {
        return (rotdagasignatura != null) ? rotdagasignatura : "";
    }
    /**
     * @param rotdagasignatura El rotdagasignatura a establecer.
     */
    public void setRotdagasignatura(String rotdagasignatura) {
        this.rotdagasignatura = rotdagasignatura;
    }
    /**
     * @return Devuelve rotdagdocente.
     */
    public String getRotdagdocente() {
        return (rotdagdocente != null) ? rotdagdocente : "";
    }
    /**
     * @param rotdagdocente El rotdagdocente a establecer.
     */
    public void setRotdagdocente(String rotdagdocente) {
        this.rotdagdocente = rotdagdocente;
    }
    /**
     * @return Devuelve rotdaggrados.
     */
    public String[] getRotdaggrados() {
        return rotdaggrados;
    }
    /**
     * @param rotdaggrados El rotdaggrados a establecer.
     */
    public void setRotdaggrados(String[] rotdaggrados) {
        this.rotdaggrados = rotdaggrados;
    }
    /**
     * @return Devuelve rotdagjornada.
     */
    public String getRotdagjornada() {
        return (rotdagjornada != null) ? rotdagjornada : "";
    }
    /**
     * @param rotdagjornada El rotdagjornada a establecer.
     */
    public void setRotdagjornada(String rotdagjornada) {
        this.rotdagjornada = rotdagjornada;
    }
    /**
     * @return Devuelve rotdagsede.
     */
    public String getRotdagsede() {
        return (rotdagsede != null) ? rotdagsede : "";
    }
    /**
     * @param rotdagsede El rotdagsede a establecer.
     */
    public void setRotdagsede(String rotdagsede) {
        this.rotdagsede = rotdagsede;
    }
    /**
     * @return Devuelve roteagasignatura.
     */
    public String getRoteagasignatura() {
        return (roteagasignatura != null) ? roteagasignatura : "";
    }
    /**
     * @param roteagasignatura El roteagasignatura a establecer.
     */
    public void setRoteagasignatura(String roteagasignatura) {
        this.roteagasignatura = roteagasignatura;
    }
    /**
     * @return Devuelve roteagespacio.
     */
    public String getRoteagespacio() {
        return (roteagespacio != null) ? roteagespacio : "";
    }
    /**
     * @param roteagespacio El roteagespacio a establecer.
     */
    public void setRoteagespacio(String roteagespacio) {
        this.roteagespacio = roteagespacio;
    }
    /**
     * @return Devuelve roteaggrados.
     */
    public String []getRoteaggrados() {
        return roteaggrados;
    }
    /**
     * @param roteaggrados El roteaggrados a establecer.
     */
    public void setRoteaggrados(String []roteaggrados) {
        this.roteaggrados = roteaggrados;
    }
    /**
     * @return Devuelve roteagjornada.
     */
    public String getRoteagjornada() {
        return (roteagjornada != null) ? roteagjornada : "";
    }
    /**
     * @param roteagjornada El roteagjornada a establecer.
     */
    public void setRoteagjornada(String roteagjornada) {
        this.roteagjornada = roteagjornada;
    }
    /**
     * @return Devuelve roteagsede.
     */
    public String getRoteagsede() {
        return (roteagsede != null) ? roteagsede : "";
    }
    /**
     * @param roteagsede El roteagsede a establecer.
     */
    public void setRoteagsede(String roteagsede) {
        this.roteagsede = roteagsede;
    }

    /**
     * @return Devuelve rotstrsede.
     */
    public String getRotstrsede() {
        return (rotstrsede != null) ? rotstrsede : "";
    }
    /**
     * @param rotstrsede El rotstrsede a establecer.
     */
    public void setRotstrsede(String rotstrsede) {
        this.rotstrsede = rotstrsede;
    }
    /**
     * @return Devuelve rotrec_strcod.
     */
    public String []getRotrec_strcod() {
        return rotrec_strcod;
    }
    /**
     * @param rotrec_strcod El rotrec_strcod a establecer.
     */
    public void setRotrec_strcod(String []rotrec_strcod) {
        this.rotrec_strcod = rotrec_strcod;
    }
    /**
     * @return Devuelve rotrec_tiprec.
     */
    public String []getRotrec_tiprec() {
        return rotrec_tiprec;
    }
    /**
     * @param rotrec_tiprec El rotrec_tiprec a establecer.
     */
    public void setRotrec_tiprec(String []rotrec_tiprec) {
        this.rotrec_tiprec = rotrec_tiprec;
    }
    /**
     * @return Devuelve rotreccodigo.
     */
    public String []getRotreccodigo() {
        return rotreccodigo;
    }
    /**
     * @param rotreccodigo El rotreccodigo a establecer.
     */
    public void setRotreccodigo(String []rotreccodigo) {
        this.rotreccodigo = rotreccodigo;
    }
    /**
     * @return Devuelve rotrecdescripcion.
     */
    public String []getRotrecdescripcion() {
        return rotrecdescripcion;
    }
    /**
     * @param rotrecdescripcion El rotrecdescripcion a establecer.
     */
    public void setRotrecdescripcion(String []rotrecdescripcion) {
        this.rotrecdescripcion = rotrecdescripcion;
    }
    /**
     * @return Devuelve rotrechorafin.
     */
    public String []getRotrechorafin() {
        return rotrechorafin;
    }
    /**
     * @param rotrechorafin El rotrechorafin a establecer.
     */
    public void setRotrechorafin(String []rotrechorafin) {
        this.rotrechorafin = rotrechorafin;
    }
    /**
     * @return Devuelve rotrechoraini.
     */
    public String []getRotrechoraini() {
        return rotrechoraini;
    }
    /**
     * @param rotrechoraini El rotrechoraini a establecer.
     */
    public void setRotrechoraini(String []rotrechoraini) {
        this.rotrechoraini = rotrechoraini;
    }
    /**
     * @return Devuelve rotstrcodigo.
     */
    public String getRotstrcodigo() {
        return (rotstrcodigo != null) ? rotstrcodigo : "";
    }
    /**
     * @param rotstrcodigo El rotstrcodigo a establecer.
     */
    public void setRotstrcodigo(String rotstrcodigo) {
        this.rotstrcodigo = rotstrcodigo;
    }
    /**
     * @return Devuelve rotstrdurblq.
     */
    public String getRotstrdurblq() {
        return (rotstrdurblq != null) ? rotstrdurblq : "";
    }
    /**
     * @param rotstrdurblq El rotstrdurblq a establecer.
     */
    public void setRotstrdurblq(String rotstrdurblq) {
        this.rotstrdurblq = rotstrdurblq;
    }
    /**
     * @return Devuelve rotstrhorafin.
     */
    public String getRotstrhorafin() {
        return (rotstrhorafin != null) ? rotstrhorafin : "";
    }
    /**
     * @param rotstrhorafin El rotstrhorafin a establecer.
     */
    public void setRotstrhorafin(String rotstrhorafin) {
        this.rotstrhorafin = rotstrhorafin;
    }
    /**
     * @return Devuelve rotstrhorini.
     */
    public String getRotstrhoraini() {
        return (rotstrhoraini != null) ? rotstrhoraini : "";
    }
    /**
     * @param rotstrhorini El rotstrhorini a establecer.
     */
    public void setRotstrhoraini(String rotstrhoraini) {
        this.rotstrhoraini = rotstrhoraini;
    }
    /**
     * @return Devuelve rotstrjornada.
     */
    public String getRotstrjornada() {
        return (rotstrjornada != null) ? rotstrjornada : "";
    }
    /**
     * @param rotstrjornada El rotstrjornada a establecer.
     */
    public void setRotstrjornada(String rotstrjornada) {
        this.rotstrjornada = rotstrjornada;
    }
    /**
     * @return Devuelve rotstrnombre.
     */
    public String getRotstrnombre() {
        return (rotstrnombre != null) ? rotstrnombre : "";
    }
    /**
     * @param rotstrnombre El rotstrnombre a establecer.
     */
    public void setRotstrnombre(String rotstrnombre) {
        this.rotstrnombre = rotstrnombre;
    }
    /**
     * @return Devuelve rotstrnumblq.
     */
    public String getRotstrnumblq() {
        return (rotstrnumblq != null) ? rotstrnumblq : "";
    }
    /**
     * @param rotstrnumblq El rotstrnumblq a establecer.
     */
    public void setRotstrnumblq(String rotstrnumblq) {
        this.rotstrnumblq = rotstrnumblq;
    }
    /**
     * @return Devuelve rotstrsemcic.
     */
    public String getRotstrsemcic() {
        return (rotstrsemcic != null) ? rotstrsemcic : "";
    }
    /**
     * @param rotstrsemcic El rotstrsemcic a establecer.
     */
    public void setRotstrsemcic(String rotstrsemcic) {
        this.rotstrsemcic = rotstrsemcic;
    }
    /**
     * @return Devuelve rtesgr_strcod.
     */
    public String getRtesgr_strcod() {
        return (rtesgr_strcod != null) ? rtesgr_strcod : "";
    }
    /**
     * @param rtesgr_strcod El rtesgr_strcod a establecer.
     */
    public void setRtesgr_strcod(String rtesgr_strcod) {
        this.rtesgr_strcod = rtesgr_strcod;
    }
    /**
     * @return Devuelve rtesgrgrado.
     */
    public String[] getRtesgrgrado() {
        //return (rtesgrgrado != null) ? rtesgrgrado : "";
        return rtesgrgrado;
    }
    /**
     * @param rtesgrgrado El rtesgrgrado a establecer.
     */
    public void setRtesgrgrado(String[] rtesgrgrado) {
        this.rtesgrgrado = rtesgrgrado;
    }
    /**
     * @return Devuelve rttiecodigo.
     */
    public String getRttiecodigo() {
        return (rttiecodigo != null) ? rttiecodigo : "";
    }
    /**
     * @param rttiecodigo El rttiecodigo a establecer.
     */
    public void setRttiecodigo(String rttiecodigo) {
        this.rttiecodigo = rttiecodigo;
    }
    /**
     * @return Devuelve rttienombre.
     */
    public String getRttienombre() {
        return (rttienombre != null) ? rttienombre : "";
    }
    /**
     * @param rttienombre El rttienombre a establecer.
     */
    public void setRttienombre(String rttienombre) {
        this.rttienombre = rttienombre;
    }
    /**
     * @return Devuelve rttieorden.
     */
    public String getRttieorden() {
        return (rttieorden != null) ? rttieorden : "";
    }
    /**
     * @param rttieorden El rttieorden a establecer.
     */
    public void setRttieorden(String rttieorden) {
        this.rttieorden = rttieorden;
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
    public String getRoteagexclusivo() {
        return roteagexclusivo!=null?roteagexclusivo:"";
    }
    public void setRoteagexclusivo(String roteagexclusivo) {
        this.roteagexclusivo = roteagexclusivo;
    }
    public String getRotihcausa() {
        return rotihcausa!=null?rotihcausa:"";
    }
    public void setRotihcausa(String rotihcausa) {
        this.rotihcausa = rotihcausa;
    }
    public String getRotihdia() {
        return rotihdia!=null?rotihdia:"";
    }
    public void setRotihdia(String rotihdia) {
        this.rotihdia = rotihdia;
    }
    public String getRotihestructura() {
        return rotihestructura!=null?rotihestructura:"";
    }
    public void setRotihestructura(String rotihestructura) {
        this.rotihestructura = rotihestructura;
    }
    public String getRotihhorafin() {
        return rotihhorafin!=null?rotihhorafin:"";
    }
    public void setRotihhorafin(String rotihhorafin) {
        this.rotihhorafin = rotihhorafin;
    }
    public String getRotihhoraini() {
        return rotihhoraini!=null?rotihhoraini:"";
    }
    public void setRotihhoraini(String rotihhoraini) {
        this.rotihhoraini = rotihhoraini;
    }
    public String getRotihjornada() {
        return rotihjornada!=null?rotihjornada:"";
    }
    public void setRotihjornada(String rotihjornada) {
        this.rotihjornada = rotihjornada;
    }
    public String getRotihsede() {
        return rotihsede!=null?rotihsede:"";
    }
    public void setRotihsede(String rotihsede) {
        this.rotihsede = rotihsede;
    }
    public String getRotstrdurhor() {
        return rotstrdurhor!=null?rotstrdurhor:"";
    }
    public void setRotstrdurhor(String rotstrdurhor) {
        this.rotstrdurhor = rotstrdurhor;
    }
	/**
	 * @return Returns the prioAsig1.
	 */
	public String getPrioAsig1() {
		return prioAsig1!=null?prioAsig1:"";
	}
	/**
	 * @param prioAsig1 The prioAsig1 to set.
	 */
	public void setPrioAsig1(String prioAsig1) {
		this.prioAsig1 = prioAsig1;
	}
	/**
	 * @return Returns the prioAsig2.
	 */
	public String getPrioAsig2() {
		return prioAsig2!=null?prioAsig2:"";
	}
	/**
	 * @param prioAsig2 The prioAsig2 to set.
	 */
	public void setPrioAsig2(String prioAsig2) {
		this.prioAsig2 = prioAsig2;
	}
	/**
	 * @return Return the vigencia.
	 */
	public int getVigencia() {
		return vigencia;
	}
	/**
	 * @param vigencia The vigencia to set.
	 */
	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}
	/**
	 * @return Return the rotstrmetodologia.
	 */
	public final String getRotstrmetodologia() {
		return rotstrmetodologia;
	}
	/**
	 * @param rotstrmetodologia The rotstrmetodologia to set.
	 */
	public final void setRotstrmetodologia(String rotstrmetodologia) {
		this.rotstrmetodologia = rotstrmetodologia;
	}
}