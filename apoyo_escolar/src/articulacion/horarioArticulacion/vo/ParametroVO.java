package articulacion.horarioArticulacion.vo;

import siges.common.vo.Vo;

public class ParametroVO extends Vo{

	private long parInstitucion;
	private int parSede;
	private int parJornada;
	private int parAnhoVigencia;
	private int parPerVigencia;
	private int parComponente;
	private int parSabado;
	private int parDomingo;
	private int parHoraIni;
	private int parMinIni;
	private int parHoraFin;
	private int parMinFin;
	private int parDuracion;
	private int parHorasBloque;
	private int parBloques;

	private String parNombreSede; 
	private String parNombreJornada; 
	private String parNombreComponente; 
	private String parHoraInicio; 
	private String parHoraFinalizacion;
	
	/**
	 * @return Returns the parAnhoVigencia.
	 */
	public int getParAnhoVigencia() {
		return parAnhoVigencia;
	}
	/**
	 * @param parAnhoVigencia The parAnhoVigencia to set.
	 */
	public void setParAnhoVigencia(int parAnhoVigencia) {
		this.parAnhoVigencia = parAnhoVigencia;
	}
	/**
	 * @return Returns the parBloques.
	 */
	public int getParBloques() {
		return parBloques;
	}
	/**
	 * @param parBloques The parBloques to set.
	 */
	public void setParBloques(int parBloques) {
		this.parBloques = parBloques;
	}
	/**
	 * @return Returns the parComponente.
	 */
	public int getParComponente() {
		return parComponente;
	}
	/**
	 * @param parComponente The parComponente to set.
	 */
	public void setParComponente(int parComponente) {
		this.parComponente = parComponente;
	}
	/**
	 * @return Returns the parDomingo.
	 */
	public int getParDomingo() {
		return parDomingo;
	}
	/**
	 * @param parDomingo The parDomingo to set.
	 */
	public void setParDomingo(int parDomingo) {
		this.parDomingo = parDomingo;
	}
	/**
	 * @return Returns the parDuracion.
	 */
	public int getParDuracion() {
		return parDuracion;
	}
	/**
	 * @param parDuracion The parDuracion to set.
	 */
	public void setParDuracion(int parDuracion) {
		this.parDuracion = parDuracion;
	}
	/**
	 * @return Returns the parHoraFin.
	 */
	public int getParHoraFin() {
		return parHoraFin;
	}
	/**
	 * @param parHoraFin The parHoraFin to set.
	 */
	public void setParHoraFin(int parHoraFin) {
		this.parHoraFin = parHoraFin;
	}
	/**
	 * @return Returns the parHoraIni.
	 */
	public int getParHoraIni() {
		return parHoraIni;
	}
	/**
	 * @param parHoraIni The parHoraIni to set.
	 */
	public void setParHoraIni(int parHoraIni) {
		this.parHoraIni = parHoraIni;
	}
	/**
	 * @return Returns the parHorasBloque.
	 */
	public int getParHorasBloque() {
		return parHorasBloque;
	}
	/**
	 * @param parHorasBloque The parHorasBloque to set.
	 */
	public void setParHorasBloque(int parHorasBloque) {
		this.parHorasBloque = parHorasBloque;
	}
	/**
	 * @return Returns the parInstitucion.
	 */
	public long getParInstitucion() {
		return parInstitucion;
	}
	/**
	 * @param parInstitucion The parInstitucion to set.
	 */
	public void setParInstitucion(long parInstitucion) {
		this.parInstitucion = parInstitucion;
	}
	/**
	 * @return Returns the parJornada.
	 */
	public int getParJornada() {
		return parJornada;
	}
	/**
	 * @param parJornada The parJornada to set.
	 */
	public void setParJornada(int parJornada) {
		this.parJornada = parJornada;
	}
	/**
	 * @return Returns the parMinFin.
	 */
	public int getParMinFin() {
		return parMinFin;
	}
	/**
	 * @param parMinFin The parMinFin to set.
	 */
	public void setParMinFin(int parMinFin) {
		this.parMinFin = parMinFin;
	}
	/**
	 * @return Returns the parMinIni.
	 */
	public int getParMinIni() {
		return parMinIni;
	}
	/**
	 * @param parMinIni The parMinIni to set.
	 */
	public void setParMinIni(int parMinIni) {
		this.parMinIni = parMinIni;
	}
	/**
	 * @return Returns the parPerVigencia.
	 */
	public int getParPerVigencia() {
		return parPerVigencia;
	}
	/**
	 * @param parPerVigencia The parPerVigencia to set.
	 */
	public void setParPerVigencia(int parPerVigencia) {
		this.parPerVigencia = parPerVigencia;
	}
	/**
	 * @return Returns the parSabado.
	 */
	public int getParSabado() {
		return parSabado;
	}
	/**
	 * @param parSabado The parSabado to set.
	 */
	public void setParSabado(int parSabado) {
		this.parSabado = parSabado;
	}
	/**
	 * @return Returns the parSede.
	 */
	public int getParSede() {
		return parSede;
	}
	/**
	 * @param parSede The parSede to set.
	 */
	public void setParSede(int parSede) {
		this.parSede = parSede;
	}
	public String getParHoraFinalizacion() {
		return parHoraFinalizacion;
	}
	public void setParHoraFinalizacion(String parHoraFinalizacion) {
		this.parHoraFinalizacion = parHoraFinalizacion;
	}
	public String getParHoraInicio() {
		return parHoraInicio;
	}
	public void setParHoraInicio(String parHoraInicio) {
		this.parHoraInicio = parHoraInicio;
	}
	public String getParNombreJornada() {
		return parNombreJornada;
	}
	public void setParNombreJornada(String parNombreJornada) {
		this.parNombreJornada = parNombreJornada;
	}
	public String getParNombreSede() {
		return parNombreSede;
	}
	public void setParNombreSede(String parNombreSede) {
		this.parNombreSede = parNombreSede;
	}
	/**
	 * @return Return the parNombreComponente.
	 */
	public String getParNombreComponente() {
		return parNombreComponente;
	}
	/**
	 * @param parNombreComponente The parNombreComponente to set.
	 */
	public void setParNombreComponente(String parNombreComponente) {
		this.parNombreComponente = parNombreComponente;
	}
}
