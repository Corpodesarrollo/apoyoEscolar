/**
 * 
 */
package siges.rotacion2.thread.vo;

/**
 * 31/08/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class RotacionVO {
	private long rotId;
	private long rotInstitucion;
	private int rotSede;
	private int rotJornada;
	private int rotMetodologia;
	private int rotGrado;
	private int rotGrupo;
	private long rotUsuario;
	private int rotVigencia;
	private int rotGradoSolicitud;
	//solicitud
	private int rotEstado;
	private String rotMensaje;
	//estructura
	private long rotEstructura;
	private int rotClases;
	private int rotBloques;
	//procesamiento de la solicitud
	private long rotJerarquiaGrupo;
	private long rotAsignatura;
	private long rotDocente;
	private long rotEspacio;
	private int rotHora;
	private int rotBloqueInicial;
	//proceso de iteracion
    private int rotCombinacionExitosa; 
    private int rotCategoriaActual=-1; 
    //Para manejo de Indice DocEF
    private int rotIndiceDocEf; 
	//asignatura
    private int rotCategoria;
    private int rotIH;
    private int rotBloque;
    
    
	
	/**
	 * @return Return the rotId.
	 */
	public final long getRotId() {
		return rotId;
	}

	/**
	 * @param rotId The rotId to set.
	 */
	public final void setRotId(long rotId) {
		this.rotId = rotId;
	}

	/**
	 * @return Return the rotEstado.
	 */
	public final int getRotEstado() {
		return rotEstado;
	}

	/**
	 * @param rotEstado The rotEstado to set.
	 */
	public final void setRotEstado(int rotEstado) {
		this.rotEstado = rotEstado;
	}

	/**
	 * @return Return the rotMensaje.
	 */
	public final String getRotMensaje() {
		return rotMensaje;
	}

	/**
	 * @param rotMensaje The rotMensaje to set.
	 */
	public final void setRotMensaje(String rotMensaje) {
		this.rotMensaje = rotMensaje;
	}

	/**
	 * @return Return the rotGrado.
	 */
	public final int getRotGrado() {
		return rotGrado;
	}

	/**
	 * @param rotGrado The rotGrado to set.
	 */
	public final void setRotGrado(int rotGrado) {
		this.rotGrado = rotGrado;
	}

	/**
	 * @return Return the rotGrupo.
	 */
	public final int getRotGrupo() {
		return rotGrupo;
	}

	/**
	 * @param rotGrupo The rotGrupo to set.
	 */
	public final void setRotGrupo(int rotGrupo) {
		this.rotGrupo = rotGrupo;
	}

	/**
	 * @return Return the rotInstitucion.
	 */
	public final long getRotInstitucion() {
		return rotInstitucion;
	}

	/**
	 * @param rotInstitucion The rotInstitucion to set.
	 */
	public final void setRotInstitucion(long rotInstitucion) {
		this.rotInstitucion = rotInstitucion;
	}

	/**
	 * @return Return the rotJornada.
	 */
	public final int getRotJornada() {
		return rotJornada;
	}

	/**
	 * @param rotJornada The rotJornada to set.
	 */
	public final void setRotJornada(int rotJornada) {
		this.rotJornada = rotJornada;
	}

	/**
	 * @return Return the rotMetodologia.
	 */
	public final int getRotMetodologia() {
		return rotMetodologia;
	}

	/**
	 * @param rotMetodologia The rotMetodologia to set.
	 */
	public final void setRotMetodologia(int rotMetodologia) {
		this.rotMetodologia = rotMetodologia;
	}

	/**
	 * @return Return the rotSede.
	 */
	public final int getRotSede() {
		return rotSede;
	}

	/**
	 * @param rotSede The rotSede to set.
	 */
	public final void setRotSede(int rotSede) {
		this.rotSede = rotSede;
	}

	/**
	 * @return Return the rotUsuario.
	 */
	public final long getRotUsuario() {
		return rotUsuario;
	}

	/**
	 * @param rotUsuario The rotUsuario to set.
	 */
	public final void setRotUsuario(long rotUsuario) {
		this.rotUsuario = rotUsuario;
	}

	/**
	 * @return Return the rotVigencia.
	 */
	public final int getRotVigencia() {
		return rotVigencia;
	}

	/**
	 * @param rotVigencia The rotVigencia to set.
	 */
	public final void setRotVigencia(int rotVigencia) {
		this.rotVigencia = rotVigencia;
	}

	/**
	 * @return Return the rotAsignatura.
	 */
	public final long getRotAsignatura() {
		return rotAsignatura;
	}

	/**
	 * @param rotAsignatura The rotAsignatura to set.
	 */
	public final void setRotAsignatura(long rotAsignatura) {
		this.rotAsignatura = rotAsignatura;
	}

	/**
	 * @return Return the rotDocente.
	 */
	public final long getRotDocente() {
		return rotDocente;
	}

	/**
	 * @param rotDocente The rotDocente to set.
	 */
	public final void setRotDocente(long rotDocente) {
		this.rotDocente = rotDocente;
	}

	/**
	 * @return Return the rotEspacio.
	 */
	public final long getRotEspacio() {
		return rotEspacio;
	}

	/**
	 * @param rotEspacio The rotEspacio to set.
	 */
	public final void setRotEspacio(long rotEspacio) {
		this.rotEspacio = rotEspacio;
	}

	/**
	 * @return Return the rotEstructura.
	 */
	public final long getRotEstructura() {
		return rotEstructura;
	}

	/**
	 * @param rotEstructura The rotEstructura to set.
	 */
	public final void setRotEstructura(long rotEstructura) {
		this.rotEstructura = rotEstructura;
	}

	/**
	 * @return Return the rotBloques.
	 */
	public final int getRotBloques() {
		return rotBloques;
	}

	/**
	 * @param rotBloques The rotBloques to set.
	 */
	public final void setRotBloques(int rotBloques) {
		this.rotBloques = rotBloques;
	}

	/**
	 * @return Return the rotClases.
	 */
	public final int getRotClases() {
		return rotClases;
	}

	/**
	 * @param rotClases The rotClases to set.
	 */
	public final void setRotClases(int rotClases) {
		this.rotClases = rotClases;
	}

	/**
	 * @return Return the rotJerarquiaGrupo.
	 */
	public final long getRotJerarquiaGrupo() {
		return rotJerarquiaGrupo;
	}

	/**
	 * @param rotJerarquiaGrupo The rotJerarquiaGrupo to set.
	 */
	public final void setRotJerarquiaGrupo(long rotJerarquiaGrupo) {
		this.rotJerarquiaGrupo = rotJerarquiaGrupo;
	}

	/**
	 * @return Return the rotCategoriaActual.
	 */
	public final int getRotCategoriaActual() {
		return rotCategoriaActual;
	}

	/**
	 * @param rotCategoriaActual The rotCategoriaActual to set.
	 */
	public final void setRotCategoriaActual(int rotCategoriaActual) {
		this.rotCategoriaActual = rotCategoriaActual;
	}

	/**
	 * @return Return the rotCombinacionExitosa.
	 */
	public final int getRotCombinacionExitosa() {
		return rotCombinacionExitosa;
	}

	/**
	 * @param rotCombinacionExitosa The rotCombinacionExitosa to set.
	 */
	public final void setRotCombinacionExitosa(int rotCombinacionExitosa) {
		this.rotCombinacionExitosa = rotCombinacionExitosa;
	}

	/**
	 * @return Return the rotBloque.
	 */
	public final int getRotBloque() {
		return rotBloque;
	}

	/**
	 * @param rotBloque The rotBloque to set.
	 */
	public final void setRotBloque(int rotBloque) {
		this.rotBloque = rotBloque;
	}

	/**
	 * @return Return the rotCategoria.
	 */
	public final int getRotCategoria() {
		return rotCategoria;
	}

	/**
	 * @param rotCategoria The rotCategoria to set.
	 */
	public final void setRotCategoria(int rotCategoria) {
		this.rotCategoria = rotCategoria;
	}

	/**
	 * @return Return the rotIH.
	 */
	public final int getRotIH() {
		return rotIH;
	}

	/**
	 * @param rotIH The rotIH to set.
	 */
	public final void setRotIH(int rotIH) {
		this.rotIH = rotIH;
	}

	/**
	 * @return Return the rotHora.
	 */
	public final int getRotHora() {
		return rotHora;
	}

	/**
	 * @param rotHora The rotHora to set.
	 */
	public final void setRotHora(int rotHora) {
		this.rotHora = rotHora;
	}

	/**
	 * @return Return the rotBloqueInicial.
	 */
	public final int getRotBloqueInicial() {
		return rotBloqueInicial;
	}

	/**
	 * @param rotBloqueInicial The rotBloqueInicial to set.
	 */
	public final void setRotBloqueInicial(int rotBloqueInicial) {
		this.rotBloqueInicial = rotBloqueInicial;
	}

	/**
	 * @return Return the rotIndiceDocEf.
	 */
	public final int getRotIndiceDocEf() {
		return rotIndiceDocEf;
	}

	/**
	 * @param rotIndiceDocEf The rotIndiceDocEf to set.
	 */
	public final void setRotIndiceDocEf(int rotIndiceDocEf) {
		this.rotIndiceDocEf = rotIndiceDocEf;
	}

	/**
	 * @return Return the rotGradoSolicitud.
	 */
	public final int getRotGradoSolicitud() {
		return rotGradoSolicitud;
	}

	/**
	 * @param rotGradoSolicitud The rotGradoSolicitud to set.
	 */
	public final void setRotGradoSolicitud(int rotGradoSolicitud) {
		this.rotGradoSolicitud = rotGradoSolicitud;
	}

}
