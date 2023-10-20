package pei.registro.vo;

import java.util.List;

import siges.common.vo.Vo;

public class DesarrolloCurricularVO extends Vo{
	private long desInstitucion;
	private int desFaseCurriculo;
	private int desHerramientaVida;
	private int desBaseConocimiento;
	
	private String desAlcance1;
	private String desAlcance2;
	private String desAlcance3;
	private String desAlcance4;
	private String desAlcance5;
	
	private String desDificultad1;
	private String desDificultad2;
	private String desDificultad3;
	private String desDificultad4;
	private String desDificultad5;
	
	private int desArticulado;
	private int desEstrategia;
	private int desMedia;
	private int desArticUniversidad;
	private int desArticSena;
	private int desIntegradoSena;
	private int desEgresado;
	private int desPasantia;

	private List desListaProyecto;
	private List desListaOtroProyecto;
	private List desListaCriterioPreparacion;
	private List desListaCriterioFormulacion;
	private List desListaCriterioEjecucion;
	private List desListaCriterioSeguimiento;
	private List desListaArticulacionSENA;
	private List desListaArticulacionUniversidad;
	private List desListaCapacitacion;
	
	private String[] desCriterioPreparacion;
	private String[] desCriterioFormulacion;
	private String[] desCriterioEjecucion;
	private String[] desCriterioSeguimiento;
	private String[] desProyecto;
	
	
	private int desEstado;
	private String desEstadoNombre;
	private boolean desDisabled;
	private String desDisabled_;
	
	/* Ajustes Junio 2013 */
	private int desEstructuraCurricular;
	private int desMallaCurricular;
	private int desPlanEstudios;
	private int desPropuestaCurricularCiclos;
	private String desOtroDiseno;
	private int desCiclos; 
	private int desCiclo1;
	private int desCiclo2;
	private int desCiclo3;
	private int desCiclo4;
	private int desCiclo5;
	private int desSistemaEvaluacion;
	private int desCriterioEvaluacion;
	private int desCiudadania;
	private int desAvanceCiudadania;
	private int desFaseCiudadania;
	private int desProyecto40Horas;
	private int desNecesidades;
	private int desCantidadNecesidades;
	private int desPrimeraInfancia;
	
	
	
	public int getDesEstructuraCurricular() {
		return desEstructuraCurricular;
	}
	public void setDesEstructuraCurricular(int desEstructuraCurricular) {
		this.desEstructuraCurricular = desEstructuraCurricular;
	}
	public int getDesMallaCurricular() {
		return desMallaCurricular;
	}
	public void setDesMallaCurricular(int desMallaCurricular) {
		this.desMallaCurricular = desMallaCurricular;
	}
	public int getDesPlanEstudios() {
		return desPlanEstudios;
	}
	public void setDesPlanEstudios(int desPlanEstudios) {
		this.desPlanEstudios = desPlanEstudios;
	}
	public int getDesPropuestaCurricularCiclos() {
		return desPropuestaCurricularCiclos;
	}
	public void setDesPropuestaCurricularCiclos(int desPropuestaCurricularCiclos) {
		this.desPropuestaCurricularCiclos = desPropuestaCurricularCiclos;
	}
	public String getDesOtroDiseno() {
		return desOtroDiseno;
	}
	public void setDesOtroDiseno(String desOtroDiseno) {
		this.desOtroDiseno = desOtroDiseno;
	}
	public int getDesCiclos() {
		return desCiclos;
	}
	public void setDesCiclos(int desCiclos) {
		this.desCiclos = desCiclos;
	}
	public int getDesCiclo1() {
		return desCiclo1;
	}
	public void setDesCiclo1(int desCiclo1) {
		this.desCiclo1 = desCiclo1;
	}
	public int getDesCiclo2() {
		return desCiclo2;
	}
	public void setDesCiclo2(int desCiclo2) {
		this.desCiclo2 = desCiclo2;
	}
	public int getDesCiclo3() {
		return desCiclo3;
	}
	public void setDesCiclo3(int desCiclo3) {
		this.desCiclo3 = desCiclo3;
	}
	public int getDesCiclo4() {
		return desCiclo4;
	}
	public void setDesCiclo4(int desCiclo4) {
		this.desCiclo4 = desCiclo4;
	}
	public int getDesCiclo5() {
		return desCiclo5;
	}
	public void setDesCiclo5(int desCiclo5) {
		this.desCiclo5 = desCiclo5;
	}
	public int getDesSistemaEvaluacion() {
		return desSistemaEvaluacion;
	}
	public void setDesSistemaEvaluacion(int desSistemaEvaluacion) {
		this.desSistemaEvaluacion = desSistemaEvaluacion;
	}
	public int getDesCriterioEvaluacion() {
		return this.desCriterioEvaluacion;
	}
	public void setDesCriterioEvaluacion(int desCriterioEvaluacion) {
		this.desCriterioEvaluacion = desCriterioEvaluacion;
	}
	public int getDesCiudadania() {
		return desCiudadania;
	}
	public void setDesCiudadania(int desCiudadania) {
		this.desCiudadania = desCiudadania;
	}
	public int getDesAvanceCiudadania() {
		return desAvanceCiudadania;
	}
	public void setDesAvanceCiudadania(int desAvanceCiudadania) {
		this.desAvanceCiudadania = desAvanceCiudadania;
	}
	public int getDesFaseCiudadania() {
		return desFaseCiudadania;
	}
	public void setDesFaseCiudadania(int desFaseCiudadania) {
		this.desFaseCiudadania = desFaseCiudadania;
	}
	public int getDesProyecto40Horas() {
		return desProyecto40Horas;
	}
	public void setDesProyecto40Horas(int desProyecto40Horas) {
		this.desProyecto40Horas = desProyecto40Horas;
	}
	public int getDesNecesidades() {
		return desNecesidades;
	}
	public void setDesNecesidades(int desNecesidades) {
		this.desNecesidades = desNecesidades;
	}
	public int getDesCantidadNecesidades() {
		return desCantidadNecesidades;
	}
	public void setDesCantidadNecesidades(int desCantidadNecesidades) {
		this.desCantidadNecesidades = desCantidadNecesidades;
	}
	public int getDesPrimeraInfancia() {
		return desPrimeraInfancia;
	}
	public void setDesPrimeraInfancia(int desPrimeraInfancia) {
		this.desPrimeraInfancia = desPrimeraInfancia;
	}
	
	
	/**
	 * @return the desInstitucion
	 */
	public long getDesInstitucion() {
		return desInstitucion;
	}
	/**
	 * @param desInstitucion the desInstitucion to set
	 */
	public void setDesInstitucion(long desInstitucion) {
		this.desInstitucion = desInstitucion;
	}
	/**
	 * @return the desFaseCurriculo
	 */
	public int getDesFaseCurriculo() {
		return desFaseCurriculo;
	}
	/**
	 * @param desFaseCurriculo the desFaseCurriculo to set
	 */
	public void setDesFaseCurriculo(int desFaseCurriculo) {
		this.desFaseCurriculo = desFaseCurriculo;
	}
	/**
	 * @return the desHerramientaVida
	 */
	public int getDesHerramientaVida() {
		return desHerramientaVida;
	}
	/**
	 * @param desHerramientaVida the desHerramientaVida to set
	 */
	public void setDesHerramientaVida(int desHerramientaVida) {
		this.desHerramientaVida = desHerramientaVida;
	}
	/**
	 * @return the desBaseConocimiento
	 */
	public int getDesBaseConocimiento() {
		return desBaseConocimiento;
	}
	/**
	 * @param desBaseConocimiento the desBaseConocimiento to set
	 */
	public void setDesBaseConocimiento(int desBaseConocimiento) {
		this.desBaseConocimiento = desBaseConocimiento;
	}
	/**
	 * @return the desAlcance1
	 */
	public String getDesAlcance1() {
		return desAlcance1;
	}
	/**
	 * @param desAlcance1 the desAlcance1 to set
	 */
	public void setDesAlcance1(String desAlcance1) {
		this.desAlcance1 = desAlcance1;
	}
	/**
	 * @return the desAlcance2
	 */
	public String getDesAlcance2() {
		return desAlcance2;
	}
	/**
	 * @param desAlcance2 the desAlcance2 to set
	 */
	public void setDesAlcance2(String desAlcance2) {
		this.desAlcance2 = desAlcance2;
	}
	/**
	 * @return the desAlcance3
	 */
	public String getDesAlcance3() {
		return desAlcance3;
	}
	/**
	 * @param desAlcance3 the desAlcance3 to set
	 */
	public void setDesAlcance3(String desAlcance3) {
		this.desAlcance3 = desAlcance3;
	}
	/**
	 * @return the desAlcance4
	 */
	public String getDesAlcance4() {
		return desAlcance4;
	}
	/**
	 * @param desAlcance4 the desAlcance4 to set
	 */
	public void setDesAlcance4(String desAlcance4) {
		this.desAlcance4 = desAlcance4;
	}
	/**
	 * @return the desAlcance5
	 */
	public String getDesAlcance5() {
		return desAlcance5;
	}
	/**
	 * @param desAlcance5 the desAlcance5 to set
	 */
	public void setDesAlcance5(String desAlcance5) {
		this.desAlcance5 = desAlcance5;
	}
	/**
	 * @return the desDificultad1
	 */
	public String getDesDificultad1() {
		return desDificultad1;
	}
	/**
	 * @param desDificultad1 the desDificultad1 to set
	 */
	public void setDesDificultad1(String desDificultad1) {
		this.desDificultad1 = desDificultad1;
	}
	/**
	 * @return the desDificultad2
	 */
	public String getDesDificultad2() {
		return desDificultad2;
	}
	/**
	 * @param desDificultad2 the desDificultad2 to set
	 */
	public void setDesDificultad2(String desDificultad2) {
		this.desDificultad2 = desDificultad2;
	}
	/**
	 * @return the desDificultad3
	 */
	public String getDesDificultad3() {
		return desDificultad3;
	}
	/**
	 * @param desDificultad3 the desDificultad3 to set
	 */
	public void setDesDificultad3(String desDificultad3) {
		this.desDificultad3 = desDificultad3;
	}
	/**
	 * @return the desDificultad4
	 */
	public String getDesDificultad4() {
		return desDificultad4;
	}
	/**
	 * @param desDificultad4 the desDificultad4 to set
	 */
	public void setDesDificultad4(String desDificultad4) {
		this.desDificultad4 = desDificultad4;
	}
	/**
	 * @return the desDificultad5
	 */
	public String getDesDificultad5() {
		return desDificultad5;
	}
	/**
	 * @param desDificultad5 the desDificultad5 to set
	 */
	public void setDesDificultad5(String desDificultad5) {
		this.desDificultad5 = desDificultad5;
	}
	/**
	 * @return the desArticulado
	 */
	public int getDesArticulado() {
		return desArticulado;
	}
	/**
	 * @param desArticulado the desArticulado to set
	 */
	public void setDesArticulado(int desArticulado) {
		this.desArticulado = desArticulado;
	}
	/**
	 * @return the desEstrategia
	 */
	public int getDesEstrategia() {
		return desEstrategia;
	}
	/**
	 * @param desEstrategia the desEstrategia to set
	 */
	public void setDesEstrategia(int desEstrategia) {
		this.desEstrategia = desEstrategia;
	}
	/**
	 * @return the desMedia
	 */
	public int getDesMedia() {
		return desMedia;
	}
	/**
	 * @param desMedia the desMedia to set
	 */
	public void setDesMedia(int desMedia) {
		this.desMedia = desMedia;
	}
	/**
	 * @return the desArticUniversidad
	 */
	public int getDesArticUniversidad() {
		return desArticUniversidad;
	}
	/**
	 * @param desArticUniversidad the desArticUniversidad to set
	 */
	public void setDesArticUniversidad(int desArticUniversidad) {
		this.desArticUniversidad = desArticUniversidad;
	}
	/**
	 * @return the desArticSena
	 */
	public int getDesArticSena() {
		return desArticSena;
	}
	/**
	 * @param desArticSena the desArticSena to set
	 */
	public void setDesArticSena(int desArticSena) {
		this.desArticSena = desArticSena;
	}
	/**
	 * @return the desIntegradoSena
	 */
	public int getDesIntegradoSena() {
		return desIntegradoSena;
	}
	/**
	 * @param desIntegradoSena the desIntegradoSena to set
	 */
	public void setDesIntegradoSena(int desIntegradoSena) {
		this.desIntegradoSena = desIntegradoSena;
	}
	/**
	 * @return the desEgresado
	 */
	public int getDesEgresado() {
		return desEgresado;
	}
	/**
	 * @param desEgresado the desEgresado to set
	 */
	public void setDesEgresado(int desEgresado) {
		this.desEgresado = desEgresado;
	}
	/**
	 * @return the desPasantia
	 */
	public int getDesPasantia() {
		return desPasantia;
	}
	/**
	 * @param desPasantia the desPasantia to set
	 */
	public void setDesPasantia(int desPasantia) {
		this.desPasantia = desPasantia;
	}
	/**
	 * @return the desEstado
	 */
	public int getDesEstado() {
		return desEstado;
	}
	/**
	 * @param desEstado the desEstado to set
	 */
	public void setDesEstado(int desEstado) {
		this.desEstado = desEstado;
	}
	/**
	 * @return the desEstadoNombre
	 */
	public String getDesEstadoNombre() {
		return desEstadoNombre;
	}
	/**
	 * @param desEstadoNombre the desEstadoNombre to set
	 */
	public void setDesEstadoNombre(String desEstadoNombre) {
		this.desEstadoNombre = desEstadoNombre;
	}
	/**
	 * @return the desDisabled
	 */
	public boolean isDesDisabled() {
		return desDisabled;
	}
	/**
	 * @param desDisabled the desDisabled to set
	 */
	public void setDesDisabled(boolean desDisabled) {
		this.desDisabled = desDisabled;
	}
	/**
	 * @return the desDisabled_
	 */
	public String getDesDisabled_() {
		return desDisabled_;
	}
	/**
	 * @param desDisabled the desDisabled_ to set
	 */
	public void setDesDisabled_(String desDisabled) {
		desDisabled_ = desDisabled;
	}
	/**
	 * @return the desListaProyecto
	 */
	public List getDesListaProyecto() {
		return desListaProyecto;
	}
	/**
	 * @param desListaProyecto the desListaProyecto to set
	 */
	public void setDesListaProyecto(List desListaProyecto) {
		this.desListaProyecto = desListaProyecto;
	}
	/**
	 * @return the desListaOtroProyecto
	 */
	public List getDesListaOtroProyecto() {
		return desListaOtroProyecto;
	}
	/**
	 * @param desListaOtroProyecto the desListaOtroProyecto to set
	 */
	public void setDesListaOtroProyecto(List desListaOtroProyecto) {
		this.desListaOtroProyecto = desListaOtroProyecto;
	}
	/**
	 * @return the desListaCriterioPreparacion
	 */
	public List getDesListaCriterioPreparacion() {
		return desListaCriterioPreparacion;
	}
	/**
	 * @param desListaCriterioPreparacion the desListaCriterioPreparacion to set
	 */
	public void setDesListaCriterioPreparacion(List desListaCriterioPreparacion) {
		this.desListaCriterioPreparacion = desListaCriterioPreparacion;
	}
	/**
	 * @return the desListaCriterioFormulacion
	 */
	public List getDesListaCriterioFormulacion() {
		return desListaCriterioFormulacion;
	}
	/**
	 * @param desListaCriterioFormulacion the desListaCriterioFormulacion to set
	 */
	public void setDesListaCriterioFormulacion(List desListaCriterioFormulacion) {
		this.desListaCriterioFormulacion = desListaCriterioFormulacion;
	}
	/**
	 * @return the desListaCriterioEjecucion
	 */
	public List getDesListaCriterioEjecucion() {
		return desListaCriterioEjecucion;
	}
	/**
	 * @param desListaCriterioEjecucion the desListaCriterioEjecucion to set
	 */
	public void setDesListaCriterioEjecucion(List desListaCriterioEjecucion) {
		this.desListaCriterioEjecucion = desListaCriterioEjecucion;
	}
	/**
	 * @return the desListaCriterioSeguimiento
	 */
	public List getDesListaCriterioSeguimiento() {
		return desListaCriterioSeguimiento;
	}
	/**
	 * @param desListaCriterioSeguimiento the desListaCriterioSeguimiento to set
	 */
	public void setDesListaCriterioSeguimiento(List desListaCriterioSeguimiento) {
		this.desListaCriterioSeguimiento = desListaCriterioSeguimiento;
	}
	/**
	 * @return the desListaArticulacionSENA
	 */
	public List getDesListaArticulacionSENA() {
		return desListaArticulacionSENA;
	}
	/**
	 * @param desListaArticulacionSENA the desListaArticulacionSENA to set
	 */
	public void setDesListaArticulacionSENA(List desListaArticulacionSENA) {
		this.desListaArticulacionSENA = desListaArticulacionSENA;
	}
	/**
	 * @return the desListaArticulacionUniversidad
	 */
	public List getDesListaArticulacionUniversidad() {
		return desListaArticulacionUniversidad;
	}
	/**
	 * @param desListaArticulacionUniversidad the desListaArticulacionUniversidad to set
	 */
	public void setDesListaArticulacionUniversidad(
			List desListaArticulacionUniversidad) {
		this.desListaArticulacionUniversidad = desListaArticulacionUniversidad;
	}
	/**
	 * @return the desListaCapacitacion
	 */
	public List getDesListaCapacitacion() {
		return desListaCapacitacion;
	}
	/**
	 * @param desListaCapacitacion the desListaCapacitacion to set
	 */
	public void setDesListaCapacitacion(List desListaCapacitacion) {
		this.desListaCapacitacion = desListaCapacitacion;
	}
	/**
	 * @return the desCriterioPreparacion
	 */
	public String[] getDesCriterioPreparacion() {
		return desCriterioPreparacion;
	}
	/**
	 * @param desCriterioPreparacion the desCriterioPreparacion to set
	 */
	public void setDesCriterioPreparacion(String[] desCriterioPreparacion) {
		this.desCriterioPreparacion = desCriterioPreparacion;
	}
	/**
	 * @return the desCriterioFormulacion
	 */
	public String[] getDesCriterioFormulacion() {
		return desCriterioFormulacion;
	}
	/**
	 * @param desCriterioFormulacion the desCriterioFormulacion to set
	 */
	public void setDesCriterioFormulacion(String[] desCriterioFormulacion) {
		this.desCriterioFormulacion = desCriterioFormulacion;
	}
	/**
	 * @return the desCriterioEjecucion
	 */
	public String[] getDesCriterioEjecucion() {
		return desCriterioEjecucion;
	}
	/**
	 * @param desCriterioEjecucion the desCriterioEjecucion to set
	 */
	public void setDesCriterioEjecucion(String[] desCriterioEjecucion) {
		this.desCriterioEjecucion = desCriterioEjecucion;
	}
	/**
	 * @return the desCriterioSeguimiento
	 */
	public String[] getDesCriterioSeguimiento() {
		return desCriterioSeguimiento;
	}
	/**
	 * @param desCriterioSeguimiento the desCriterioSeguimiento to set
	 */
	public void setDesCriterioSeguimiento(String[] desCriterioSeguimiento) {
		this.desCriterioSeguimiento = desCriterioSeguimiento;
	}
	/**
	 * @return the desProyecto
	 */
	public String[] getDesProyecto() {
		return desProyecto;
	}
	/**
	 * @param desProyecto the desProyecto to set
	 */
	public void setDesProyecto(String[] desProyecto) {
		this.desProyecto = desProyecto;
	}
	
	
	
}
