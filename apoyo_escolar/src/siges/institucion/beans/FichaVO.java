package siges.institucion.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;


public class FichaVO implements Cloneable{

    private String fichaInstitucion;
    private String fichaEnfasis;
    private String fichaModalidad;
    private String fichaResolucion;
    private String fichaFechaResolucion;
    private String fichaEspecialidad;
    private String fichaTotDoc;
    private String fichaTotEst;
    private String fichaTotSedes;
    private int []fichaPrograma;
    private String fichaTotJornadas;
    private Collection fichaDocXGen;
    private Collection fichaDocXEdad;
    private Collection fichaDocXEscalafon;
    private Collection fichaEstXJorGrado;
    private Collection fichaEstXGrado;
    private Collection fichaEspacioXTipo;
    
   private List listaSede = new ArrayList();
   private long totalConsolGeneroM;
   private long totalConsolGeneroF;
   
	
	private long totalConsolEdadM_20_30;
	private long totalConsolEdadF_20_30;
	
	private long totalConsolEdadM_30_40;
	private long totalConsolEdadF_30_40;
	
	private long totalConsolEdadM_40_50;
	private long totalConsolEdadF_40_50;
	
	private long totalConsolEdadM_50_mas;
	private long totalConsolEdadF_50_mas;
	
	private long totalConsolEstGeneroM;
	private long totalConsolEstGeneroF;
	
	private long totalConsolEstGeneroGradoM;
	private long totalConsolEstGeneroGradoF;
   
   
   private List listaGradoGenero = new ArrayList();
 
   private int totalConsoEspacio = 0;
    
   
   
   
   
   
   
   
   
    private String fichaEstado;
    
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
    /**
     * @return Returns the fichaEnfasis.
     */
    public String getFichaEnfasis() {
        return fichaEnfasis!=null?fichaEnfasis:"";
    }
    /**
     * @param fichaEnfasis The fichaEnfasis to set.
     */
    public void setFichaEnfasis(String fichaEnfasis) {
        this.fichaEnfasis = fichaEnfasis;
    }
    /**
     * @return Returns the fichaInstitucion.
     */
    public String getFichaInstitucion() {
        return fichaInstitucion!=null?fichaInstitucion:"";
    }
    /**
     * @param fichaInstitucion The fichaInstitucion to set.
     */
    public void setFichaInstitucion(String fichaInstitucion) {
        this.fichaInstitucion = fichaInstitucion;
    }
    /**
     * @return Returns the fichaModalidad.
     */
    public String getFichaModalidad() {
        return fichaModalidad!=null?fichaModalidad:"";
    }
    /**
     * @param fichaModalidad The fichaModalidad to set.
     */
    public void setFichaModalidad(String fichaModalidad) {
        this.fichaModalidad = fichaModalidad;
    }
    /**
     * @return Returns the fichaEstado.
     */
    public String getFichaEstado() {
        return fichaEstado!=null?fichaEstado:"";
    }
    /**
     * @param fichaEstado The fichaEstado to set.
     */
    public void setFichaEstado(String fichaEstado) {
        this.fichaEstado = fichaEstado;
    }
    /**
     * @return Returns the fichaDocXGen.
     */
    public Collection getFichaDocXGen() {
        return fichaDocXGen;
    }
    /**
     * @param fichaDocXGen The fichaDocXGen to set.
     */
    public void setFichaDocXGen(Collection fichaDocXGen) {
        this.fichaDocXGen = fichaDocXGen;
    }
    /**
     * @return Returns the fichaTotDoc.
     */
    public String getFichaTotDoc() {
        return fichaTotDoc!=null?fichaTotDoc:"0";
    }
    /**
     * @param fichaTotDoc The fichaTotDoc to set.
     */
    public void setFichaTotDoc(String fichaTotDoc) {
        this.fichaTotDoc = fichaTotDoc;
    }
    /**
     * @return Returns the fichaTotEst.
     */
    public String getFichaTotEst() {
        return fichaTotEst!=null?fichaTotEst:"";
    }
    /**
     * @param fichaTotEst The fichaTotEst to set.
     */
    public void setFichaTotEst(String fichaTotEst) {
        this.fichaTotEst = fichaTotEst;
    }
    /**
     * @return Returns the fichaDocXEdad.
     */
    public Collection getFichaDocXEdad() {
        return fichaDocXEdad;
    }
    /**
     * @param fichaDocXEdad The fichaDocXEdad to set.
     */
    public void setFichaDocXEdad(Collection fichaDocXEdad) {
        this.fichaDocXEdad = fichaDocXEdad;
    }
    /**
     * @return Returns the fichaDocXEscalafon.
     */
    public Collection getFichaDocXEscalafon() {
        return fichaDocXEscalafon;
    }
    /**
     * @param fichaDocXEscalafon The fichaDocXEscalafon to set.
     */
    public void setFichaDocXEscalafon(Collection fichaDocXEscalafon) {
        this.fichaDocXEscalafon = fichaDocXEscalafon;
    }
    /**
     * @return Returns the fichaEspacioXTipo.
     */
    public Collection getFichaEspacioXTipo() {
        return fichaEspacioXTipo;
    }
    /**
     * @param fichaEspacioXTipo The fichaEspacioXTipo to set.
     */
    public void setFichaEspacioXTipo(Collection fichaEspacioXTipo) {
        this.fichaEspacioXTipo = fichaEspacioXTipo;
    }
    /**
     * @return Returns the fichaEstXGrado.
     */
    public Collection getFichaEstXGrado() {
        return fichaEstXGrado;
    }
    /**
     * @param fichaEstXGrado The fichaEstXGrado to set.
     */
    public void setFichaEstXGrado(Collection fichaEstXGrado) {
        this.fichaEstXGrado = fichaEstXGrado;
    }
    /**
     * @return Returns the fichaEstXJorGrado.
     */
    public Collection getFichaEstXJorGrado() {
        return fichaEstXJorGrado;
    }
    /**
     * @param fichaEstXJorGrado The fichaEstXJorGrado to set.
     */
    public void setFichaEstXJorGrado(Collection fichaEstXJorGrado) {
        this.fichaEstXJorGrado = fichaEstXJorGrado;
    }
    /**
     * @return Returns the fichaTotJornadas.
     */
    public String getFichaTotJornadas() {
        return fichaTotJornadas!=null?fichaTotJornadas:"";
    }
    /**
     * @param fichaTotJornadas The fichaTotJornadas to set.
     */
    public void setFichaTotJornadas(String fichaTotJornadas) {
        this.fichaTotJornadas = fichaTotJornadas;
    }
    /**
     * @return Returns the fichaTotSedes.
     */
    public String getFichaTotSedes() {
        return fichaTotSedes!=null?fichaTotSedes:"";
    }
    /**
     * @param fichaTotSedes The fichaTotSedes to set.
     */
    public void setFichaTotSedes(String fichaTotSedes) {
        this.fichaTotSedes = fichaTotSedes;
    }
    public String getFichaEspecialidad() {
        return fichaEspecialidad!=null?fichaEspecialidad:"";
    }
    public void setFichaEspecialidad(String fichaEspecialidad) {
        this.fichaEspecialidad = fichaEspecialidad;
    }
	/**
	 * @return Return the fichaFechaResolucion.
	 */
	public String getFichaFechaResolucion() {
		return fichaFechaResolucion;
	}
	/**
	 * @param fichaFechaResolucion The fichaFechaResolucion to set.
	 */
	public void setFichaFechaResolucion(String fichaFechaResolucion) {
		this.fichaFechaResolucion = fichaFechaResolucion;
	}
	/**
	 * @return Return the fichaResolucion.
	 */
	public String getFichaResolucion() {
		return fichaResolucion;
	}
	/**
	 * @param fichaResolucion The fichaResolucion to set.
	 */
	public void setFichaResolucion(String fichaResolucion) {
		this.fichaResolucion = fichaResolucion;
	}
	/**
	 * @return Return the fichaPrograma.
	 */
	public final int[] getFichaPrograma() {
		return fichaPrograma;
	}
	/**
	 * @param fichaPrograma The fichaPrograma to set.
	 */
	public final void setFichaPrograma(int[] fichaPrograma) {
		this.fichaPrograma = fichaPrograma;
	}
	public List getListaSede() {
		return listaSede;
	}
	public void setListaSede(List listaSede) {
		this.listaSede = listaSede;
	}
	public long getTotalConsolGeneroF() {
		return totalConsolGeneroF;
	}
	public void setTotalConsolGeneroF(long totalConsolGeneroF) {
		this.totalConsolGeneroF = totalConsolGeneroF;
	}
	
	public void addTotalConsolGeneroF(long total) {
		this.totalConsolGeneroF += total;
	}
	public long getTotalConsolGeneroM() {
		return totalConsolGeneroM;
	}
	public void setTotalConsolGeneroM(long totalConsolGeneroM) {
		this.totalConsolGeneroM = totalConsolGeneroM;
	}
	
	public void addTotalConsolGeneroM(long total) {
		this.totalConsolGeneroM += total;
	}
	public long getTotalConsolEdadF_20_30() {
		return totalConsolEdadF_20_30;
	}
	public void addTotalConsolEdadF_20_30(long totalConsolEdadF_20_30) {
		this.totalConsolEdadF_20_30 += totalConsolEdadF_20_30;
	}
	public long getTotalConsolEdadF_30_40() {
		return totalConsolEdadF_30_40;
	}
	public void addTotalConsolEdadF_30_40(long totalConsolEdadF_30_40) {
		this.totalConsolEdadF_30_40 += totalConsolEdadF_30_40;
	}
	public long getTotalConsolEdadF_40_50() {
		return totalConsolEdadF_40_50;
	}
	public void addTotalConsolEdadF_40_50(long totalConsolEdadF_40_50) {
		this.totalConsolEdadF_40_50 += totalConsolEdadF_40_50;
	}
	public long getTotalConsolEdadF_50_mas() {
		return totalConsolEdadF_50_mas;
	}
	public void addTotalConsolEdadF_50_mas(long totalConsolEdadF_50_mas) {
		this.totalConsolEdadF_50_mas += totalConsolEdadF_50_mas;
	}
	public long getTotalConsolEdadM_20_30() {
		return totalConsolEdadM_20_30;
	}
	public void addTotalConsolEdadM_20_30(long totalConsolEdadM_20_30) {
		this.totalConsolEdadM_20_30 += totalConsolEdadM_20_30;
	}
	public long getTotalConsolEdadM_30_40() {
		return totalConsolEdadM_30_40;
	}
	public void addTotalConsolEdadM_30_40(long total) {
		this.totalConsolEdadM_30_40 += total;
	}
	public long getTotalConsolEdadM_40_50() {
		return totalConsolEdadM_40_50;
	}
	public void addTotalConsolEdadM_40_50(long totalConsolEdadM_40_50) {
		this.totalConsolEdadM_40_50 += totalConsolEdadM_40_50;
	}
	public long getTotalConsolEdadM_50_mas() {
		return totalConsolEdadM_50_mas;
	}
	public void addTotalConsolEdadM_50_mas(long totalConsolEdadM_50_mas) {
		this.totalConsolEdadM_50_mas += totalConsolEdadM_50_mas;
	}
	public long getTotalConsolEstGeneroF() {
		return totalConsolEstGeneroF;
	}
	public void addTotalConsolEstGeneroF(long totalConsolEstGeneroF) {
		this.totalConsolEstGeneroF += totalConsolEstGeneroF;
	}
	public long getTotalConsolEstGeneroM() {
		return totalConsolEstGeneroM;
	}
	public void addTotalConsolEstGeneroM(long totalConsolEstGeneroM) {
		this.totalConsolEstGeneroM += totalConsolEstGeneroM;
	}
	public long getTotalConsolEstGeneroGradoF() {
		return totalConsolEstGeneroGradoF;
	}
	public void addTotalConsolEstGeneroGradoF(long totalConsolEstGeneroGradoF) {
		this.totalConsolEstGeneroGradoF += totalConsolEstGeneroGradoF;
	}
	public long getTotalConsolEstGeneroGradoM() {
		return totalConsolEstGeneroGradoM;
	}
	public void addTotalConsolEstGeneroGradoM(long totalConsolEstGeneroGradoM) {
		this.totalConsolEstGeneroGradoM += totalConsolEstGeneroGradoM;
	}
	public List getListaGradoGenero() {
		return listaGradoGenero;
	}
	public void setListaGradoGenero(List listaGradoGenero) {
		this.listaGradoGenero = listaGradoGenero;
	}
	public int getTotalConsoEspacio() {
		return totalConsoEspacio;
	}
	public void addTotalConsoEspacio(int totalConsoEspacio) {
		this.totalConsoEspacio += totalConsoEspacio;
	}
}
