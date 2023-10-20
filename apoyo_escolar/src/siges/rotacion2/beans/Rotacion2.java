package siges.rotacion2.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class Rotacion2 implements Serializable, Cloneable{
	public static final long serialVersionUID = 1658;
    //desde base de datos
    private long institucion;
    private int sede;
    private int jornada;
    private int metodologia;
    private int grupo_;
    private int grado_;
    //grupo-asig
    private int grado;
    private int asignatura;
    private int bloqueInicial;
    private int categoria;    
    private int grupo;
    private long jergrupo;
    private int vigencia;
    private int ih;
    private int cupo;
    //OTROS
    private String docente;
    private String espacio;
    private String hora;
    //est
    private long estcodigo;
    private int estdurblq;
    private int estnumblq;
    
    private long usuario;
    
    private long idRotacion;
    private int estado;
    
    //Para manejo de Indice DocEF
    private int indiceDocEf; 
    //Para manejo de combinacion exitosa
    private int combinacionExitosa; 
    private int categoriaActual=-1; 
    
    /**
     * @return Devuelve hora.
     */
    public String getHora() {
        return (hora != null) ? hora : "";
    }
    /**
     * @param hora El hora a establecer.
     */
    public void setHora(String hora) {
        this.hora = hora;
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
    public String getDocente() {
        return docente!=null?docente:"";
    }
    public void setDocente(String docente) {
        this.docente = docente;
    }
    public String getEspacio() {
        return espacio!=null?espacio:"";
    }
    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }
	/**
	 * @return Return the indiceDocEf.
	 */
	public int getIndiceDocEf() {
		return indiceDocEf;
	}
	/**
	 * @param indiceDocEf The indiceDocEf to set.
	 */
	public void setIndiceDocEf(int indiceDocEf) {
		this.indiceDocEf = indiceDocEf;
	}
	/**
	 * @return Return the combinacionExitosa.
	 */
	public int getCombinacionExitosa() {
		return combinacionExitosa;
	}
	/**
	 * @param combinacionExitosa The combinacionExitosa to set.
	 */
	public void setCombinacionExitosa(int combinacionExitosa) {
		this.combinacionExitosa = combinacionExitosa;
	}
	/**
	 * @return Return the categoriaActual.
	 */
	public int getCategoriaActual() {
		return categoriaActual;
	}
	/**
	 * @param categoriaActual The categoriaActual to set.
	 */
	public void setCategoriaActual(int categoriaActual) {
		this.categoriaActual = categoriaActual;
	}
	/**
	 * @return Return the asignatura.
	 */
	public int getAsignatura() {
		return asignatura;
	}
	/**
	 * @param asignatura The asignatura to set.
	 */
	public void setAsignatura(int asignatura) {
		this.asignatura = asignatura;
	}
	/**
	 * @return Return the bloqueInicial.
	 */
	public int getBloqueInicial() {
		return bloqueInicial;
	}
	/**
	 * @param bloqueInicial The bloqueInicial to set.
	 */
	public void setBloqueInicial(int bloqueInicial) {
		this.bloqueInicial = bloqueInicial;
	}
	/**
	 * @return Return the categoria.
	 */
	public int getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria The categoria to set.
	 */
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return Return the cupo.
	 */
	public int getCupo() {
		return cupo;
	}
	/**
	 * @param cupo The cupo to set.
	 */
	public void setCupo(int cupo) {
		this.cupo = cupo;
	}
	/**
	 * @return Return the grado.
	 */
	public int getGrado() {
		return grado;
	}
	/**
	 * @param grado The grado to set.
	 */
	public void setGrado(int grado) {
		this.grado = grado;
	}
	/**
	 * @return Return the grupo.
	 */
	public int getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo The grupo to set.
	 */
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	/**
	 * @return Return the ih.
	 */
	public int getIh() {
		return ih;
	}
	/**
	 * @param ih The ih to set.
	 */
	public void setIh(int ih) {
		this.ih = ih;
	}
	/**
	 * @return Return the jergrupo.
	 */
	public long getJergrupo() {
		return jergrupo;
	}
	/**
	 * @param jergrupo The jergrupo to set.
	 */
	public void setJergrupo(long jergrupo) {
		this.jergrupo = jergrupo;
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
	 * @return Return the grado_.
	 */
	public int getGrado_() {
		return grado_;
	}
	/**
	 * @param grado_ The grado_ to set.
	 */
	public void setGrado_(int grado_) {
		this.grado_ = grado_;
	}
	/**
	 * @return Return the grupo_.
	 */
	public int getGrupo_() {
		return grupo_;
	}
	/**
	 * @param grupo_ The grupo_ to set.
	 */
	public void setGrupo_(int grupo_) {
		this.grupo_ = grupo_;
	}
	/**
	 * @return Return the institucion.
	 */
	public long getInstitucion() {
		return institucion;
	}
	/**
	 * @param institucion The institucion to set.
	 */
	public void setInstitucion(long institucion) {
		this.institucion = institucion;
	}
	/**
	 * @return Return the jornada.
	 */
	public int getJornada() {
		return jornada;
	}
	/**
	 * @param jornada The jornada to set.
	 */
	public void setJornada(int jornada) {
		this.jornada = jornada;
	}
	/**
	 * @return Return the metodologia.
	 */
	public int getMetodologia() {
		return metodologia;
	}
	/**
	 * @param metodologia The metodologia to set.
	 */
	public void setMetodologia(int metodologia) {
		this.metodologia = metodologia;
	}
	/**
	 * @return Return the sede.
	 */
	public int getSede() {
		return sede;
	}
	/**
	 * @param sede The sede to set.
	 */
	public void setSede(int sede) {
		this.sede = sede;
	}
	/**
	 * @return Return the estado.
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * @param estado The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	/**
	 * @return Return the estcodigo.
	 */
	public long getEstcodigo() {
		return estcodigo;
	}
	/**
	 * @param estcodigo The estcodigo to set.
	 */
	public void setEstcodigo(long estcodigo) {
		this.estcodigo = estcodigo;
	}
	/**
	 * @return Return the estdurblq.
	 */
	public int getEstdurblq() {
		return estdurblq;
	}
	/**
	 * @param estdurblq The estdurblq to set.
	 */
	public void setEstdurblq(int estdurblq) {
		this.estdurblq = estdurblq;
	}
	/**
	 * @return Return the estnumblq.
	 */
	public int getEstnumblq() {
		return estnumblq;
	}
	/**
	 * @param estnumblq The estnumblq to set.
	 */
	public void setEstnumblq(int estnumblq) {
		this.estnumblq = estnumblq;
	}
	/**
	 * @return Return the idRotacion.
	 */
	public long getIdRotacion() {
		return idRotacion;
	}
	/**
	 * @param idRotacion The idRotacion to set.
	 */
	public void setIdRotacion(long idRotacion) {
		this.idRotacion = idRotacion;
	}
	/**
	 * @return Return the usuario.
	 */
	public long getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario The usuario to set.
	 */
	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}
}