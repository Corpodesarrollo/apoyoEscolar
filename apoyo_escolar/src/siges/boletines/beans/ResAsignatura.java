/*
 * Created on 10/08/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package siges.boletines.beans;

import java.io.Serializable;

/** siges.boletines.beans<br>
 * Funcinn:   
 * <br>
 */

public class ResAsignatura implements Cloneable{
    private String institucion; 
    private String sede;
    private String jornada;
    private String metodologia;
    private String grado;
    private String grupo;
    private String periodo;

  	public Object clone() {
  			Object o = null;
  			try {
  				o = super.clone();
  			} catch(CloneNotSupportedException e) {
  				System.err.println("MyObject can't clone");
  			}
  			return o;
  	    }	

    public String getGrado() {
        return grado;
    }
    public void setGrado(String grado) {
        this.grado = grado;
    }
    public String getGrupo() {
        return grupo;
    }
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    public String getInstitucion() {
        return institucion;
    }
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    public String getJornada() {
        return jornada;
    }
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }
    public String getMetodologia() {
        return metodologia;
    }
    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }
    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    public String getSede() {
        return sede;
    }
    public void setSede(String sede) {
        this.sede = sede;
    }
}
