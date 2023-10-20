package siges.rotacion2.beans;

import java.util.Collection;

/** siges.rotacion2.beans<br>
 * Funcinn:   
 * <br>
 */

public class Validacion {
    private String institucion_;
    private String sede_;
    private String jornada_;
    private String metodologia;
    private String nombreGrupo;
    private String nombreGrado;
    private String usuario;
    private Collection hoja1;
    private Collection hoja2;
    private Collection hoja3;

    public Collection getHoja1() {
        return hoja1;
    }
    public void setHoja1(Collection hoja1) {
        this.hoja1 = hoja1;
    }
    public Collection getHoja2() {
        return hoja2;
    }
    public void setHoja2(Collection hoja2) {
        this.hoja2 = hoja2;
    }
    public Collection getHoja3() {
        return hoja3;
    }
    public void setHoja3(Collection hoja3) {
        this.hoja3 = hoja3;
    }
    public String getMetodologia() {
        return metodologia;
    }
    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getInstitucion_() {
    return institucion_;
    }
    public void setInstitucion_(String institucion_) {
        this.institucion_ = institucion_;
    }
    public String getJornada_() {
        return jornada_;
    }
    public void setJornada_(String jornada_) {
        this.jornada_ = jornada_;
    }
    public String getSede_() {
        return sede_;
    }
    public void setSede_(String sede_) {
        this.sede_ = sede_;
    }
	/**
	 * @return Return the nombreGrado.
	 */
	public String getNombreGrado() {
		return nombreGrado;
	}
	/**
	 * @param nombreGrado The nombreGrado to set.
	 */
	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}
	/**
	 * @return Return the nombreGrupo.
	 */
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	/**
	 * @param nombreGrupo The nombreGrupo to set.
	 */
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
}
