package participacion.lideres.vo;

import siges.common.vo.Vo;

/**
 * Value Object del manejo del formulario de ingreso/edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class LideresVO  extends Vo{

	private int lidNivel;
	
	private int lidInstancia;
	private int lidRango;
	private long lidParticipante;
	private int lidTipoDocumento;
	private String lidNumeroDocumento;
	private String lidApellido1;
	private String lidApellido2;
	private String lidNombre1;
	private String lidNombre2;
	private String lidCorreoElectronico;
	private String lidTelefono;
	private String lidCelular;
	private int lidEdad;
	private int lidGenero;
	private int lidEtnia;
	private int lidRol;
	private String lidNombreRol;
	private int lidSuplente;
	private int lidLocalidad=-1;
	private int lidTipoColegio;
	private long lidColegio;
	private int lidSede;
	private int lidJornada;
	private int lidGrado;
	private int lidGrupo;
	private int lidMet;
	private String lidEntidad;
	private int lidLocalidadResidencia;
	private int lidSectorEconomico;
	private int lidDiscapacidad;
	private int lidDesplazado;
	private int lidAmenazado;
	private int lidLgtb;
	private int lidOcupacion;
	private String lidFechaIngreso;
	private String lidFechaActualizacion;
	
	private String lidApellidos;
	private String lidNombres;
	
	private int lidTieneLocalidad;
	private int lidTieneColegio;
	
	
	/**
	 * @return Return the lidMet.
	 */
	public int getLidMet() {
		return lidMet;
	}
	/**
	 * @param lidMet The lidMet to set.
	 */
	public void setLidMet(int lidMet) {
		this.lidMet = lidMet;
	}
	/**
	 * @return Return the lidTieneLocalidad.
	 */
	public int getLidTieneLocalidad() {
		return lidTieneLocalidad;
	}
	/**
	 * @param lidTieneLocalidad The lidTieneLocalidad to set.
	 */
	public void setLidTieneLocalidad(int lidTieneLocalidad) {
		this.lidTieneLocalidad = lidTieneLocalidad;
	}
	/**
	 * @return Return the lidTieneColegio.
	 */
	public int getLidTieneColegio() {
		return lidTieneColegio;
	}
	/**
	 * @param lidTieneColegio The lidTieneColegio to set.
	 */
	public void setLidTieneColegio(int lidTieneColegio) {
		this.lidTieneColegio = lidTieneColegio;
	}
	/**
	 * @return Return the lidNivel.
	 */
	public int getLidNivel() {
		return lidNivel;
	}
	/**
	 * @return Return the lidApellidos.
	 */
	public String getLidApellidos() {
		return lidApellidos;
	}
	/**
	 * @param lidApellidos The lidApellidos to set.
	 */
	public void setLidApellidos(String lidApellidos) {
		this.lidApellidos = lidApellidos;
	}
	/**
	 * @return Return the lidNombres.
	 */
	public String getLidNombres() {
		return lidNombres;
	}
	/**
	 * @param lidNombres The lidNombres to set.
	 */
	public void setLidNombres(String lidNombres) {
		this.lidNombres = lidNombres;
	}
	/**
	 * @param lidNivel The lidNivel to set.
	 */
	public void setLidNivel(int lidNivel) {
		this.lidNivel = lidNivel;
	}
	/**
	 * @return Return the lidNombreRol.
	 */
	public String getLidNombreRol() {
		return lidNombreRol;
	}
	/**
	 * @param lidNombreRol The lidNombreRol to set.
	 */
	public void setLidNombreRol(String lidNombreRol) {
		this.lidNombreRol = lidNombreRol;
	}
	/**
	 * @return Return the lidInstancia.
	 */
	public int getLidInstancia() {
		return lidInstancia;
	}
	/**
	 * @param lidInstancia The lidInstancia to set.
	 */
	public void setLidInstancia(int lidInstancia) {
		this.lidInstancia = lidInstancia;
	}
	/**
	 * @return Return the lidRango.
	 */
	public int getLidRango() {
		return lidRango;
	}
	/**
	 * @param lidRango The lidRango to set.
	 */
	public void setLidRango(int lidRango) {
		this.lidRango = lidRango;
	}
	/**
	 * @return Return the lidParticipante.
	 */
	public long getLidParticipante() {
		return lidParticipante;
	}
	/**
	 * @param lidParticipante The lidParticipante to set.
	 */
	public void setLidParticipante(long lidParticipante) {
		this.lidParticipante = lidParticipante;
	}
	/**
	 * @return Return the lidTipoDocumento.
	 */
	public int getLidTipoDocumento() {
		return lidTipoDocumento;
	}
	/**
	 * @param lidTipoDocumento The lidTipoDocumento to set.
	 */
	public void setLidTipoDocumento(int lidTipoDocumento) {
		this.lidTipoDocumento = lidTipoDocumento;
	}
	/**
	 * @return Return the lidNumeroDocumento.
	 */
	public String getLidNumeroDocumento() {
		return lidNumeroDocumento;
	}
	/**
	 * @param lidNumeroDocumento The lidNumeroDocumento to set.
	 */
	public void setLidNumeroDocumento(String lidNumeroDocumento) {
		this.lidNumeroDocumento = lidNumeroDocumento;
	}
	/**
	 * @return Return the lidApellido1.
	 */
	public String getLidApellido1() {
		return lidApellido1;
	}
	/**
	 * @param lidApellido1 The lidApellido1 to set.
	 */
	public void setLidApellido1(String lidApellido1) {
		this.lidApellido1 = lidApellido1;
	}
	/**
	 * @return Return the lidApellido2.
	 */
	public String getLidApellido2() {
		return lidApellido2;
	}
	/**
	 * @param lidApellido2 The lidApellido2 to set.
	 */
	public void setLidApellido2(String lidApellido2) {
		this.lidApellido2 = lidApellido2;
	}
	/**
	 * @return Return the lidNombre1.
	 */
	public String getLidNombre1() {
		return lidNombre1;
	}
	/**
	 * @param lidNombre1 The lidNombre1 to set.
	 */
	public void setLidNombre1(String lidNombre1) {
		this.lidNombre1 = lidNombre1;
	}
	/**
	 * @return Return the lidNombre2.
	 */
	public String getLidNombre2() {
		return lidNombre2;
	}
	/**
	 * @param lidNombre2 The lidNombre2 to set.
	 */
	public void setLidNombre2(String lidNombre2) {
		this.lidNombre2 = lidNombre2;
	}
	/**
	 * @return Return the lidCorreoElectronico.
	 */
	public String getLidCorreoElectronico() {
		return lidCorreoElectronico;
	}
	/**
	 * @param lidCorreoElectronico The lidCorreoElectronico to set.
	 */
	public void setLidCorreoElectronico(String lidCorreoElectronico) {
		this.lidCorreoElectronico = lidCorreoElectronico;
	}
	/**
	 * @return Return the lidTelefono.
	 */
	public String getLidTelefono() {
		return lidTelefono;
	}
	/**
	 * @param lidTelefono The lidTelefono to set.
	 */
	public void setLidTelefono(String lidTelefono) {
		this.lidTelefono = lidTelefono;
	}
	/**
	 * @return Return the lidCelular.
	 */
	public String getLidCelular() {
		return lidCelular;
	}
	/**
	 * @param lidCelular The lidCelular to set.
	 */
	public void setLidCelular(String lidCelular) {
		this.lidCelular = lidCelular;
	}
	/**
	 * @return Return the lidEdad.
	 */
	public int getLidEdad() {
		return lidEdad;
	}
	/**
	 * @param lidEdad The lidEdad to set.
	 */
	public void setLidEdad(int lidEdad) {
		this.lidEdad = lidEdad;
	}
	/**
	 * @return Return the lidGenero.
	 */
	public int getLidGenero() {
		return lidGenero;
	}
	/**
	 * @param lidGenero The lidGenero to set.
	 */
	public void setLidGenero(int lidGenero) {
		this.lidGenero = lidGenero;
	}
	/**
	 * @return Return the lidEtnia.
	 */
	public int getLidEtnia() {
		return lidEtnia;
	}
	/**
	 * @param lidEtnia The lidEtnia to set.
	 */
	public void setLidEtnia(int lidEtnia) {
		this.lidEtnia = lidEtnia;
	}
	/**
	 * @return Return the lidRol.
	 */
	public int getLidRol() {
		return lidRol;
	}
	/**
	 * @param lidRol The lidRol to set.
	 */
	public void setLidRol(int lidRol) {
		this.lidRol = lidRol;
	}
	/**
	 * @return Return the lidSuplente.
	 */
	public int getLidSuplente() {
		return lidSuplente;
	}
	/**
	 * @param lidSuplente The lidSuplente to set.
	 */
	public void setLidSuplente(int lidSuplente) {
		this.lidSuplente = lidSuplente;
	}
	/**
	 * @return Return the lidLocalidad.
	 */
	public int getLidLocalidad() {
		return lidLocalidad;
	}
	/**
	 * @param lidLocalidad The lidLocalidad to set.
	 */
	public void setLidLocalidad(int lidLocalidad) {
		this.lidLocalidad = lidLocalidad;
	}
	/**
	 * @return Return the lidTipoColegio.
	 */
	public int getLidTipoColegio() {
		return lidTipoColegio;
	}
	/**
	 * @param lidTipoColegio The lidTipoColegio to set.
	 */
	public void setLidTipoColegio(int lidTipoColegio) {
		this.lidTipoColegio = lidTipoColegio;
	}
	/**
	 * @return Return the lidColegio.
	 */
	public long getLidColegio() {
		return lidColegio;
	}
	/**
	 * @param lidColegio The lidColegio to set.
	 */
	public void setLidColegio(long lidColegio) {
		this.lidColegio = lidColegio;
	}
	/**
	 * @return Return the lidSede.
	 */
	public int getLidSede() {
		return lidSede;
	}
	/**
	 * @param lidSede The lidSede to set.
	 */
	public void setLidSede(int lidSede) {
		this.lidSede = lidSede;
	}
	/**
	 * @return Return the lidJornada.
	 */
	public int getLidJornada() {
		return lidJornada;
	}
	/**
	 * @param lidJornada The lidJornada to set.
	 */
	public void setLidJornada(int lidJornada) {
		this.lidJornada = lidJornada;
	}
	/**
	 * @return Return the lidGrado.
	 */
	public int getLidGrado() {
		return lidGrado;
	}
	/**
	 * @param lidGrado The lidGrado to set.
	 */
	public void setLidGrado(int lidGrado) {
		this.lidGrado = lidGrado;
	}
	/**
	 * @return Return the lidGrupo.
	 */
	public int getLidGrupo() {
		return lidGrupo;
	}
	/**
	 * @param lidGrupo The lidGrupo to set.
	 */
	public void setLidGrupo(int lidGrupo) {
		this.lidGrupo = lidGrupo;
	}
	/**
	 * @return Return the lidEntidad.
	 */
	public String getLidEntidad() {
		return lidEntidad;
	}
	/**
	 * @param lidEntidad The lidEntidad to set.
	 */
	public void setLidEntidad(String lidEntidad) {
		this.lidEntidad = lidEntidad;
	}
	/**
	 * @return Return the lidLocalidadResidencia.
	 */
	public int getLidLocalidadResidencia() {
		return lidLocalidadResidencia;
	}
	/**
	 * @param lidLocalidadResidencia The lidLocalidadResidencia to set.
	 */
	public void setLidLocalidadResidencia(int lidLocalidadResidencia) {
		this.lidLocalidadResidencia = lidLocalidadResidencia;
	}
	/**
	 * @return Return the lidSectorEconomico.
	 */
	public int getLidSectorEconomico() {
		return lidSectorEconomico;
	}
	/**
	 * @param lidSectorEconomico The lidSectorEconomico to set.
	 */
	public void setLidSectorEconomico(int lidSectorEconomico) {
		this.lidSectorEconomico = lidSectorEconomico;
	}
	/**
	 * @return Return the lidDiscapacidad.
	 */
	public int getLidDiscapacidad() {
		return lidDiscapacidad;
	}
	/**
	 * @param lidDiscapacidad The lidDiscapacidad to set.
	 */
	public void setLidDiscapacidad(int lidDiscapacidad) {
		this.lidDiscapacidad = lidDiscapacidad;
	}
	/**
	 * @return Return the lidDesplazado.
	 */
	public int getLidDesplazado() {
		return lidDesplazado;
	}
	/**
	 * @param lidDesplazado The lidDesplazado to set.
	 */
	public void setLidDesplazado(int lidDesplazado) {
		this.lidDesplazado = lidDesplazado;
	}
	/**
	 * @return Return the lidAmenazado.
	 */
	public int getLidAmenazado() {
		return lidAmenazado;
	}
	/**
	 * @param lidAmenazado The lidAmenazado to set.
	 */
	public void setLidAmenazado(int lidAmenazado) {
		this.lidAmenazado = lidAmenazado;
	}
	/**
	 * @return Return the lidLgtb.
	 */
	public int getLidLgtb() {
		return lidLgtb;
	}
	/**
	 * @param lidLgtb The lidLgtb to set.
	 */
	public void setLidLgtb(int lidLgtb) {
		this.lidLgtb = lidLgtb;
	}
	/**
	 * @return Return the lidOcupacion.
	 */
	public int getLidOcupacion() {
		return lidOcupacion;
	}
	/**
	 * @param lidOcupacion The lidOcupacion to set.
	 */
	public void setLidOcupacion(int lidOcupacion) {
		this.lidOcupacion = lidOcupacion;
	}
	/**
	 * @return Return the lidFechaIngreso.
	 */
	public String getLidFechaIngreso() {
		return lidFechaIngreso;
	}
	/**
	 * @param lidFechaIngreso The lidFechaIngreso to set.
	 */
	public void setLidFechaIngreso(String lidFechaIngreso) {
		this.lidFechaIngreso = lidFechaIngreso;
	}
	/**
	 * @return Return the lidFechaActualizacion.
	 */
	public String getLidFechaActualizacion() {
		return lidFechaActualizacion;
	}
	/**
	 * @param lidFechaActualizacion The lidFechaActualizacion to set.
	 */
	public void setLidFechaActualizacion(String lidFechaActualizacion) {
		this.lidFechaActualizacion = lidFechaActualizacion;
	}
	
	
	
	
	
	
}
