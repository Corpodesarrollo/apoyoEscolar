/**
 * 
 */
package articulacion.asigGrupo.vo;

import siges.common.vo.Vo;

/**
 * 30/07/2007 
 * @author Latined
 * @version 1.2
 */
public class AsignacionArtEstGrupoVO  extends Vo{
	
	private long artGruCodigoEstudiante;
	private long artGruCodigoGrupo;
	private long artGruCodigoAsignatura;
	
	public long getArtGruCodigoEstudiante() {
		return artGruCodigoEstudiante;
	}
	public void setArtGruCodigoEstudiante(long artGruCodigoEstudiante) {
		this.artGruCodigoEstudiante = artGruCodigoEstudiante;
	}
	public long getArtGruCodigoGrupo() {
		return artGruCodigoGrupo;
	}
	public void setArtGruCodigoGrupo(long artGruCodigoGrupo) {
		this.artGruCodigoGrupo = artGruCodigoGrupo;
	}
	public long getArtGruCodigoAsignatura() {
		return artGruCodigoAsignatura;
	}
	public void setArtGruCodigoAsignatura(long artGruCodigoAsignatura) {
		this.artGruCodigoAsignatura = artGruCodigoAsignatura;
	}
	

}
