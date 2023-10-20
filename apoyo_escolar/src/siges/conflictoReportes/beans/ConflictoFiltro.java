package siges.conflictoReportes.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ConflictoFiltro implements Serializable, Cloneable{
    
    private String local1;
    private String nomlocal1;
    private String periodo1;
    private String nomperiodo1;
    
    private String sede2;
    private String nomsede2;
    private String jorn2;
    private String nomjorn2;
    private String periodo2;
    private String nomperiodo2;
    private String metodologia2;
    private String nommetodologia2;
    
    private String local3;
    private String nomlocal3;
    private String colegio3;
    private String nomcolegio3;
    private String sede3;
    private String nomsede3;
    private String jorn3;
    private String nomjorn3;
    private String periodo3;
    private String nomperiodo3;
    private String jerjornada3;
    private String codigojerar3;
    
    /**
     * @return Devuelve nomjorn2.
     */
    public String getNomjorn2() {
        return (nomjorn2 != null) ? nomjorn2 : "";
    }
    /**
     * @param nomjorn2 El nomjorn2 a establecer.
     */
    public void setNomjorn2(String nomjorn2) {
        this.nomjorn2 = nomjorn2;
    }
    /**
     * @return Devuelve nomperiodo2.
     */
    public String getNomperiodo2() {
        return (nomperiodo2 != null) ? nomperiodo2 : "";
    }
    /**
     * @param nomperiodo2 El nomperiodo2 a establecer.
     */
    public void setNomperiodo2(String nomperiodo2) {
        this.nomperiodo2 = nomperiodo2;
    }
    /**
     * @return Devuelve nomsede2.
     */
    public String getNomsede2() {
        return (nomsede2 != null) ? nomsede2 : "";
    }
    /**
     * @param nomsede2 El nomsede2 a establecer.
     */
    public void setNomsede2(String nomsede2) {
        this.nomsede2 = nomsede2;
    }
    /**
     * @return Devuelve nomlocal1.
     */
    public String getNomlocal1() {
        return (nomlocal1 != null) ? nomlocal1 : "";
    }
    /**
     * @param nomlocal1 El nomlocal1 a establecer.
     */
    public void setNomlocal1(String nomlocal1) {
        this.nomlocal1 = nomlocal1;
    }
    /**
     * @return Devuelve nomperiodo1.
     */
    public String getNomperiodo1() {
        return (nomperiodo1 != null) ? nomperiodo1 : "";
    }
    /**
     * @param nomperiodo1 El nomperiodo1 a establecer.
     */
    public void setNomperiodo1(String nomperiodo1) {
        this.nomperiodo1 = nomperiodo1;
    }
    /**
     * @return Devuelve colegio3.
     */
    public String getColegio3() {
        return (colegio3 != null) ? colegio3 : "";
    }
    /**
     * @param colegio3 El colegio3 a establecer.
     */
    public void setColegio3(String colegio3) {
        this.colegio3 = colegio3;
    }
    /**
     * @return Devuelve jorn2.
     */
    public String getJorn2() {
        return (jorn2 != null) ? jorn2 : "";
    }
    /**
     * @param jorn2 El jorn2 a establecer.
     */
    public void setJorn2(String jorn2) {
        this.jorn2 = jorn2;
    }
    /**
     * @return Devuelve jorn3.
     */
    public String getJorn3() {
        return (jorn3 != null) ? jorn3 : "";
    }
    /**
     * @param jorn3 El jorn3 a establecer.
     */
    public void setJorn3(String jorn3) {
        this.jorn3 = jorn3;
    }
    /**
     * @return Devuelve local1.
     */
    public String getLocal1() {
        return (local1 != null) ? local1 : "";
    }
    /**
     * @param local1 El local1 a establecer.
     */
    public void setLocal1(String local1) {
        this.local1 = local1;
    }
    /**
     * @return Devuelve local3.
     */
    public String getLocal3() {
        return (local3 != null) ? local3 : "";
    }
    /**
     * @param local3 El local3 a establecer.
     */
    public void setLocal3(String local3) {
        this.local3 = local3;
    }
    /**
     * @return Devuelve periodo1.
     */
    public String getPeriodo1() {
        return (periodo1 != null) ? periodo1 : "";
    }
    /**
     * @param periodo1 El periodo1 a establecer.
     */
    public void setPeriodo1(String periodo1) {
        this.periodo1 = periodo1;
    }
    /**
     * @return Devuelve periodo2.
     */
    public String getPeriodo2() {
        return (periodo2 != null) ? periodo2 : "";
    }
    /**
     * @param periodo2 El periodo2 a establecer.
     */
    public void setPeriodo2(String periodo2) {
        this.periodo2 = periodo2;
    }
    /**
     * @return Devuelve periodo3.
     */
    public String getPeriodo3() {
        return (periodo3 != null) ? periodo3 : "";
    }
    /**
     * @param periodo3 El periodo3 a establecer.
     */
    public void setPeriodo3(String periodo3) {
        this.periodo3 = periodo3;
    }
    /**
     * @return Devuelve sede2.
     */
    public String getSede2() {
        return (sede2 != null) ? sede2 : "";
    }
    /**
     * @param sede2 El sede2 a establecer.
     */
    public void setSede2(String sede2) {
        this.sede2 = sede2;
    }
    /**
     * @return Devuelve sede3.
     */
    public String getSede3() {
        return (sede3 != null) ? sede3 : "";
    }
    /**
     * @param sede3 El sede3 a establecer.
     */
    public void setSede3(String sede3) {
        this.sede3 = sede3;
    }
    public String getJerjornada3() {
		return (jerjornada3 != null) ? jerjornada3 : "";
	}
	public void setJerjornada3(String jerjornada3) {
		this.jerjornada3 = jerjornada3;
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
	public String getNomperiodo3() {
		return (nomperiodo3 != null) ? nomperiodo3 : "";
	}
	public void setNomperiodo3(String nomperiodo3) {
		this.nomperiodo3 = nomperiodo3;
	}
	public String getCodigojerar3() {
		return (codigojerar3 != null) ? codigojerar3 : "";
	}
	public void setCodigojerar3(String codigojerar3) {
		this.codigojerar3 = codigojerar3;
	}
	public String getNomlocal3() {
		return (nomlocal3 != null) ? nomlocal3 : "";
	}
	public void setNomlocal3(String nomlocal3) {
		this.nomlocal3 = nomlocal3;
	}
	public String getNomcolegio3() {
		return (nomcolegio3 != null) ? nomcolegio3 : "";
	}
	public void setNomcolegio3(String nomcolegio3) {
		this.nomcolegio3 = nomcolegio3;
	}
	public String getNomjorn3() {
		return (nomjorn3 != null) ? nomjorn3 : "";
	}
	public void setNomjorn3(String nomjorn3) {
		this.nomjorn3 = nomjorn3;
	}
	public String getNomsede3() {
		return (nomsede3 != null) ? nomsede3 : "";
	}
	public void setNomsede3(String nomsede3) {
		this.nomsede3 = nomsede3;
	}
	/**
	 * @return Return the metodologia2.
	 */
	public final String getMetodologia2() {
		return metodologia2;
	}
	/**
	 * @param metodologia2 The metodologia2 to set.
	 */
	public final void setMetodologia2(String metodologia2) {
		this.metodologia2 = metodologia2;
	}
	/**
	 * @return Return the nommetodologia2.
	 */
	public final String getNommetodologia2() {
		return nommetodologia2;
	}
	/**
	 * @param nommetodologia2 The nommetodologia2 to set.
	 */
	public final void setNommetodologia2(String nommetodologia2) {
		this.nommetodologia2 = nommetodologia2;
	}

}