/**
 * 
 */
package siges.rotacion.beans.forma;

/**
 * 13/10/2007 
 * @author Latined
 * @version 1.2
 */
public class EstructuraVO {
	public String estNombre;
	public long estCodigo;
	public int estSede;
	public int estJornada;
	public int estMetodologia;
	public long estJerarquia;
	public String estHoraIni;
	public String estHoraFin;
	public int estDurBloque;
	public int estBloqueXJornada;
	public int estSemanaXCiclo;
	
	/**
	 * @return Return the estBloqueXJornada.
	 */
	public int getEstBloqueXJornada() {
		return estBloqueXJornada;
	}
	/**
	 * @param estBloqueXJornada The estBloqueXJornada to set.
	 */
	public void setEstBloqueXJornada(int estBloqueXJornada) {
		this.estBloqueXJornada = estBloqueXJornada;
	}
	/**
	 * @return Return the estCodigo.
	 */
	public long getEstCodigo() {
		return estCodigo;
	}
	/**
	 * @param estCodigo The estCodigo to set.
	 */
	public void setEstCodigo(long estCodigo) {
		this.estCodigo = estCodigo;
	}
	/**
	 * @return Return the estNombre.
	 */
	public String getEstNombre() {
		return estNombre;
	}
	/**
	 * @param estNombre The estNombre to set.
	 */
	public void setEstNombre(String estNombre) {
		this.estNombre = estNombre;
	}
	/**
	 * @return Return the estSemanaXCiclo.
	 */
	public int getEstSemanaXCiclo() {
		return estSemanaXCiclo;
	}
	/**
	 * @param estSemanaXCiclo The estSemanaXCiclo to set.
	 */
	public void setEstSemanaXCiclo(int estSemanaXCiclo) {
		this.estSemanaXCiclo = estSemanaXCiclo;
	}
	/**
	 * @return Return the estHoraFin.
	 */
	public String getEstHoraFin() {
		return estHoraFin;
	}
	/**
	 * @param estHoraFin The estHoraFin to set.
	 */
	public void setEstHoraFin(String estHoraFin) {
		this.estHoraFin = estHoraFin;
	}
	/**
	 * @return Return the estHoraIni.
	 */
	public String getEstHoraIni() {
		return estHoraIni;
	}
	/**
	 * @param estHoraIni The estHoraIni to set.
	 */
	public void setEstHoraIni(String estHoraIni) {
		this.estHoraIni = estHoraIni;
	}
	/**
	 * @return Return the estJerarquia.
	 */
	public long getEstJerarquia() {
		return estJerarquia;
	}
	/**
	 * @param estJerarquia The estJerarquia to set.
	 */
	public void setEstJerarquia(long estJerarquia) {
		this.estJerarquia = estJerarquia;
	}
	/**
	 * @return Return the estJornada.
	 */
	public int getEstJornada() {
		return estJornada;
	}
	/**
	 * @param estJornada The estJornada to set.
	 */
	public void setEstJornada(int estJornada) {
		this.estJornada = estJornada;
	}
	/**
	 * @return Return the estSede.
	 */
	public int getEstSede() {
		return estSede;
	}
	/**
	 * @param estSede The estSede to set.
	 */
	public void setEstSede(int estSede) {
		this.estSede = estSede;
	}
	/**
	 * @return Return the estDurBloque.
	 */
	public int getEstDurBloque() {
		return estDurBloque;
	}
	/**
	 * @param estDurBloque The estDurBloque to set.
	 */
	public void setEstDurBloque(int estDurBloque) {
		this.estDurBloque = estDurBloque;
	}
	/**
	 * @return Return the estMetodologia.
	 */
	public final int getEstMetodologia() {
		return estMetodologia;
	}
	/**
	 * @param estMetodologia The estMetodologia to set.
	 */
	public final void setEstMetodologia(int estMetodologia) {
		this.estMetodologia = estMetodologia;
	}
}
