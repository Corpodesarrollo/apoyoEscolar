/**
 * 
 */
package siges.gestionAcademica.inasistencia.vo;

import java.util.List;

/**
 * 3/09/2007 
 * @author Latined
 * @version 1.2
 */
public class InasistenciaEstudiante {
	private long evalCodigo;
	private int evalConsecutivo;
	private String evalNombre;
	private String evalApellido;
	private List evalFallas; //<<FallaEstudiante>>
	
	/**
	 * @return Return the evalApellido.
	 */
	public String getEvalApellido() {
		return evalApellido;
	}
	/**
	 * @param evalApellido The evalApellido to set.
	 */
	public void setEvalApellido(String evalApellido) {
		this.evalApellido = evalApellido;
	}
	/**
	 * @return Return the evalCodigo.
	 */
	public long getEvalCodigo() {
		return evalCodigo;
	}
	/**
	 * @param evalCodigo The evalCodigo to set.
	 */
	public void setEvalCodigo(long evalCodigo) {
		this.evalCodigo = evalCodigo;
	}
	/**
	 * @return Return the evalConsecutivo.
	 */
	public int getEvalConsecutivo() {
		return evalConsecutivo;
	}
	/**
	 * @param evalConsecutivo The evalConsecutivo to set.
	 */
	public void setEvalConsecutivo(int evalConsecutivo) {
		this.evalConsecutivo = evalConsecutivo;
	}
	/**
	 * @return Return the evalNombre.
	 */
	public String getEvalNombre() {
		return evalNombre;
	}
	/**
	 * @param evalNombre The evalNombre to set.
	 */
	public void setEvalNombre(String evalNombre) {
		this.evalNombre = evalNombre;
	}
	/**
	 * @return Return the evalFallas.
	 */
	public List getEvalFallas() {
		return evalFallas;
	}
	/**
	 * @param evalFallas The evalFallas to set.
	 */
	public void setEvalFallas(List evalFallas) {
		this.evalFallas = evalFallas;
	}
}
