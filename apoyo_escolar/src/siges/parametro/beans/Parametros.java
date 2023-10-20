package siges.parametro.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class Parametros implements Serializable, Cloneable{
	private String g_parvigencia;
	private String g_parhorario;
	private String g_parrec_dia;
	private String g_parrec_hora;
	private String g_parrec_sleep;
	private String g_parrec_activo;
	private String g_parpla_activo;
	private String g_parpla_dia;
	private String g_parpla_hora;
	private String g_parpla_sleep;
	//private String g_parpla_periodo;
	private String g_parimp_activo;
	private String g_parimp_dia;
	private String g_parimp_hora;
	private String g_parimp_sleep;
	private String g_parboletin;
	private String g_parlibro;
	private String g_parintegracion;
	private String g_parestadistico;
	private String g_parcertificado;
	
	private String g_parintegracionMatr;
	private int g_parintegracionMatr_dia;
	private int g_parintegracionMatr_hora;
	private String g_parintegracionMatr_msj;
	
    /**
     * @return Devuelve g_parcertificado.
     */
    public String getG_parcertificado() {
        return (g_parcertificado != null) ? g_parcertificado : "";
    }
    /**
     * @param g_parcertificado El g_parcertificado a establecer.
     */
    public void setG_parcertificado(String g_parcertificado) {
        this.g_parcertificado = g_parcertificado;
    }
    /**
     * @return Devuelve g_parestadistico.
     */
    public String getG_parestadistico() {
        return (g_parestadistico != null) ? g_parestadistico : "";
    }
    /**
     * @param g_parestadistico El g_parestadistico a establecer.
     */
    public void setG_parestadistico(String g_parestadistico) {
        this.g_parestadistico = g_parestadistico;
    }
    /**
     * @return Devuelve g_parintegracion.
     */
    public String getG_parintegracion() {
        return (g_parintegracion != null) ? g_parintegracion : "";
    }
    /**
     * @param g_parintegracion El g_parintegracion a establecer.
     */
    public void setG_parintegracion(String g_parintegracion) {
        this.g_parintegracion = g_parintegracion;
    }
    /**
     * @return Devuelve g_parboletin.
     */
    public String getG_parboletin() {
        return (g_parboletin != null) ? g_parboletin : "";
    }
    /**
     * @param g_parboletin El g_parboletin a establecer.
     */
    public void setG_parboletin(String g_parboletin) {
        this.g_parboletin = g_parboletin;
    }
    /**
     * @return Devuelve g_parhorario.
     */
    public String getG_parhorario() {
        return (g_parhorario != null) ? g_parhorario : "";
    }
    /**
     * @param g_parhorario El g_parhorario a establecer.
     */
    public void setG_parhorario(String g_parhorario) {
        this.g_parhorario = g_parhorario;
    }
    /**
     * @return Devuelve g_parimp_activo.
     */
    public String getG_parimp_activo() {
        return (g_parimp_activo != null) ? g_parimp_activo : "";
    }
    /**
     * @param g_parimp_activo El g_parimp_activo a establecer.
     */
    public void setG_parimp_activo(String g_parimp_activo) {
        this.g_parimp_activo = g_parimp_activo;
    }
    /**
     * @return Devuelve g_parimp_dia.
     */
    public String getG_parimp_dia() {
        return (g_parimp_dia != null) ? g_parimp_dia : "";
    }
    /**
     * @param g_parimp_dia El g_parimp_dia a establecer.
     */
    public void setG_parimp_dia(String g_parimp_dia) {
        this.g_parimp_dia = g_parimp_dia;
    }
    /**
     * @return Devuelve g_parimp_hora.
     */
    public String getG_parimp_hora() {
        return (g_parimp_hora != null) ? g_parimp_hora : "";
    }
    /**
     * @param g_parimp_hora El g_parimp_hora a establecer.
     */
    public void setG_parimp_hora(String g_parimp_hora) {
        this.g_parimp_hora = g_parimp_hora;
    }
    /**
     * @return Devuelve g_parimp_sleep.
     */
    public String getG_parimp_sleep() {
        return (g_parimp_sleep != null) ? g_parimp_sleep : "";
    }
    /**
     * @param g_parimp_sleep El g_parimp_sleep a establecer.
     */
    public void setG_parimp_sleep(String g_parimp_sleep) {
        this.g_parimp_sleep = g_parimp_sleep;
    }
    /**
     * @return Devuelve g_parlibro.
     */
    public String getG_parlibro() {
        return (g_parlibro != null) ? g_parlibro : "";
    }
    /**
     * @param g_parlibro El g_parlibro a establecer.
     */
    public void setG_parlibro(String g_parlibro) {
        this.g_parlibro = g_parlibro;
    }
    /**
     * @return Devuelve g_parpla_activo.
     */
    public String getG_parpla_activo() {
        return (g_parpla_activo != null) ? g_parpla_activo : "";
    }
    /**
     * @param g_parpla_activo El g_parpla_activo a establecer.
     */
    public void setG_parpla_activo(String g_parpla_activo) {
        this.g_parpla_activo = g_parpla_activo;
    }
    /**
     * @return Devuelve g_parpla_dia.
     */
    public String getG_parpla_dia() {
        return (g_parpla_dia != null) ? g_parpla_dia : "";
    }
    /**
     * @param g_parpla_dia El g_parpla_dia a establecer.
     */
    public void setG_parpla_dia(String g_parpla_dia) {
        this.g_parpla_dia = g_parpla_dia;
    }
    /**
     * @return Devuelve g_parpla_hora.
     */
    public String getG_parpla_hora() {
        return (g_parpla_hora != null) ? g_parpla_hora : "";
    }
    /**
     * @param g_parpla_hora El g_parpla_hora a establecer.
     */
    public void setG_parpla_hora(String g_parpla_hora) {
        this.g_parpla_hora = g_parpla_hora;
    }
    /**
     * @return Devuelve g_parpla_periodo.
     */
    /*public String getG_parpla_periodo() {
        return (g_parpla_periodo != null) ? g_parpla_periodo : "";
    }*/
    /**
     * @param g_parpla_periodo El g_parpla_periodo a establecer.
     */
    /*public void setG_parpla_periodo(String g_parpla_periodo) {
        this.g_parpla_periodo = g_parpla_periodo;
    }*/
    /**
     * @return Devuelve g_parpla_sleep.
     */
    public String getG_parpla_sleep() {
        return (g_parpla_sleep != null) ? g_parpla_sleep : "";
    }
    /**
     * @param g_parpla_sleep El g_parpla_sleep a establecer.
     */
    public void setG_parpla_sleep(String g_parpla_sleep) {
        this.g_parpla_sleep = g_parpla_sleep;
    }
    /**
     * @return Devuelve g_parrec_activo.
     */
    public String getG_parrec_activo() {
        return (g_parrec_activo != null) ? g_parrec_activo : "";
    }
    /**
     * @param g_parrec_activo El g_parrec_activo a establecer.
     */
    public void setG_parrec_activo(String g_parrec_activo) {
        this.g_parrec_activo = g_parrec_activo;
    }
    /**
     * @return Devuelve g_parrec_dia.
     */
    public String getG_parrec_dia() {
        return (g_parrec_dia != null) ? g_parrec_dia : "";
    }
    /**
     * @param g_parrec_dia El g_parrec_dia a establecer.
     */
    public void setG_parrec_dia(String g_parrec_dia) {
        this.g_parrec_dia = g_parrec_dia;
    }
    /**
     * @return Devuelve g_parrec_hora.
     */
    public String getG_parrec_hora() {
        return (g_parrec_hora != null) ? g_parrec_hora : "";
    }
    /**
     * @param g_parrec_hora El g_parrec_hora a establecer.
     */
    public void setG_parrec_hora(String g_parrec_hora) {
        this.g_parrec_hora = g_parrec_hora;
    }
    /**
     * @return Devuelve g_parrec_sleep.
     */
    public String getG_parrec_sleep() {
        return (g_parrec_sleep != null) ? g_parrec_sleep : "";
    }
    /**
     * @param g_parrec_sleep El g_parrec_sleep a establecer.
     */
    public void setG_parrec_sleep(String g_parrec_sleep) {
        this.g_parrec_sleep = g_parrec_sleep;
    }
    /**
     * @return Devuelve g_parvigencia.
     */
    public String getG_parvigencia() {
        return (g_parvigencia != null) ? g_parvigencia : "";
    }
    /**
     * @param g_parvigencia El g_parvigencia a establecer.
     */
    public void setG_parvigencia(String g_parvigencia) {
        this.g_parvigencia = g_parvigencia;
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
	public String getG_parintegracionMatr() {
		return g_parintegracionMatr;
	}
	public void setG_parintegracionMatr(String matr) {
		g_parintegracionMatr = matr;
	}
	public int getG_parintegracionMatr_dia() {
		return g_parintegracionMatr_dia;
	}
	public void setG_parintegracionMatr_dia(int matr_dia) {
		g_parintegracionMatr_dia = matr_dia;
	}
	public int getG_parintegracionMatr_hora() {
		return g_parintegracionMatr_hora;
	}
	public void setG_parintegracionMatr_hora(int matr_hora) {
		g_parintegracionMatr_hora = matr_hora;
	}
	public String getG_parintegracionMatr_msj() {
		return g_parintegracionMatr_msj;
	}
	public void setG_parintegracionMatr_msj(String matr_msj) {
		g_parintegracionMatr_msj = matr_msj;
	}

}