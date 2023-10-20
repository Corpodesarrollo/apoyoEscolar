package siges.grupoPeriodo.beans;

/** siges.grupoPeriodo.beans<br>
 * Funcinn:   
 * <br>
 */

public class AdminVO {
    private String adminHorario;
    private String adminInst;
    private String adminLectura;
    private String adminFechaBitacora;
    private String adminFechaBitacoraFinal;
	private String adminUsuarioBitacora;
    private int adminLogro;
    private int adminDescriptor;
    private float adminMaxAsignatura;
    private int adminPlanEstudios;
    
    public String getAdminFechaBitacoraFinal() {
		return adminFechaBitacoraFinal;
	}
	public void setAdminFechaBitacoraFinal(String adminFechaBitacoraFinal) {
		this.adminFechaBitacoraFinal = adminFechaBitacoraFinal;
	}

    /**
     * @return Returns the adminHorario.
     */
    public String getAdminHorario() {
        return adminHorario!=null?adminHorario:"";
    }
    /**
     * @param adminHorario The adminHorario to set.
     */
    public void setAdminHorario(String adminHorario) {
        this.adminHorario = adminHorario;
    }
    /**
     * @return Returns the adminInst.
     */
    public String getAdminInst() {
        return adminInst!=null?adminInst:"";
    }
    /**
     * @param adminInst The adminInst to set.
     */
    public void setAdminInst(String adminInst) {
        this.adminInst = adminInst;
    }
    /**
     * @return Returns the adminLectura.
     */
    public String getAdminLectura(){
        return adminLectura!=null?adminLectura:"";
    }
    /**
     * @param adminLectura The adminLectura to set.
     */
    public void setAdminLectura(String adminLectura) {
        this.adminLectura = adminLectura;
    }
    public String getAdminFechaBitacora() {
        return adminFechaBitacora!=null?adminFechaBitacora:"";
    }
    public void setAdminFechaBitacora(String adminFechaBitacora) {
        this.adminFechaBitacora = adminFechaBitacora;
    }
    public String getAdminUsuarioBitacora() {
        return adminUsuarioBitacora!=null?adminUsuarioBitacora:"";
    }
    public void setAdminUsuarioBitacora(String adminUsuarioBitacora) {
        this.adminUsuarioBitacora = adminUsuarioBitacora;
    }
	/**
	 * @return Return the adminDescriptor.
	 */
	public int getAdminDescriptor() {
		return adminDescriptor;
	}
	/**
	 * @param adminDescriptor The adminDescriptor to set.
	 */
	public void setAdminDescriptor(int adminDescriptor) {
		this.adminDescriptor = adminDescriptor;
	}
	/**
	 * @return Return the adminLogro.
	 */
	public int getAdminLogro() {
		return adminLogro;
	}
	/**
	 * @param adminLogro The adminLogro to set.
	 */
	public void setAdminLogro(int adminLogro) {
		this.adminLogro = adminLogro;
	}
	/**
	 * @return Return the adminMaxAsignatura.
	 */
	public float getAdminMaxAsignatura() {
		return adminMaxAsignatura;
	}
	/**
	 * @param adminMaxAsignatura The adminMaxAsignatura to set.
	 */
	public void setAdminMaxAsignatura(float adminMaxAsignatura) {
		this.adminMaxAsignatura = adminMaxAsignatura;
	}
	/**
	 * @return Return the adminPlanEstudios.
	 */
	public int getAdminPlanEstudios() {
		return adminPlanEstudios;
	}
	/**
	 * @param adminPlanEstudios The adminPlanEstudios to set.
	 */
	public void setAdminPlanEstudios(int adminPlanEstudios) {
		this.adminPlanEstudios = adminPlanEstudios;
	}
}
