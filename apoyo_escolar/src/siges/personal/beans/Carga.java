package siges.personal.beans;

/** siges.personal.beans<br>
 * Funcinn:   
 * <br>
 */
public class Carga implements Cloneable {
    private String rotdagjerjornada;
    private String rotdagjergrado;
    private String rotdagdocente;
    private String rotdagasignatura;
    private String rotdagsede;
    private String rotdagjornada;
    private String rotdaghoras;
    private String rotdagmetodologia;
    private String []rotdaggrados;
    private String []rotdagIHgrados_;
    private String estado;
    private int rotdagVigencia;

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
	 * @return Returns the estado.
	 */
	public String getEstado() {
		return estado!=null?estado:"";
	}
	/**
	 * @param estado The estado to set.
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
			try {
				o = super.clone();
			} catch(CloneNotSupportedException e) {
				System.err.println("MyObject can't clone");
			}
			return o;
	    }
    public String getRotdagmetodologia() {
        return rotdagmetodologia!=null?rotdagmetodologia:"";
    }
    
    public void setRotdagmetodologia(String rotdagmetodologia) {
        this.rotdagmetodologia = rotdagmetodologia;
    }
	/**
	 * @return Returns the rotdagIHgrados_.
	 */
	public String[] getRotdagIHgrados_() {
		return rotdagIHgrados_;
	}
	/**
	 * @param rotdagIHgrados_ The rotdagIHgrados_ to set.
	 */
	public void setRotdagIHgrados_(String[] rotdagIHgrados_) {
		this.rotdagIHgrados_ = rotdagIHgrados_;
	}
	/**
	 * @return Return the rotdagVigencia.
	 */
	public int getRotdagVigencia() {
		return rotdagVigencia;
	}
	/**
	 * @param rotdagVigencia The rotdagVigencia to set.
	 */
	public void setRotdagVigencia(int rotdagVigencia) {
		this.rotdagVigencia = rotdagVigencia;
	}
}
