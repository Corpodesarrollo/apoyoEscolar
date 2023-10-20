package articulacion.especialidad.vo;

import java.sql.Date;

import siges.common.vo.Vo;

public class EspecialidadVO extends Vo {
	private int carCodinst;
	private int carCouniv;
	private int carCodigo;
	private String carNombre;
	private String carRegicfes;
	private String carNumaprob;
	private String carFecaprob;
	private int carTipperiodo;
	private int carNumperiodo;
	private String carTitulo;
	private String carDescripcion;
	private int carTipoPrograma;
	private String carUniversidad;

	public int getCarCodigo() {
		return carCodigo;
	}

	public void setCarCodigo(int carCodigo) {
		this.carCodigo = carCodigo;
	}

	public int getCarCodinst() {
		return carCodinst;
	}

	public void setCarCodinst(int carCodinst) {
		this.carCodinst = carCodinst;
	}

	public int getCarCouniv() {
		return carCouniv;
	}

	public void setCarCouniv(int carCouniv) {
		this.carCouniv = carCouniv;
	}

	public String getCarDescripcion() {
		return (carDescripcion != null) ? carDescripcion : "";
	}

	public void setCarDescripcion(String carDescripcion) {
		this.carDescripcion = carDescripcion;
	}

	public String getCarFecaprob() {
		return carFecaprob;
	}

	public void setCarFecaprob(String carFecaprob) {
		// System.out.println("la fecha llega asi "+carFecaprob);
		this.carFecaprob = carFecaprob;
	}

	public String getCarNombre() {
		return (carNombre != null) ? carNombre : "";
	}

	public void setCarNombre(String carNombre) {
		this.carNombre = carNombre;
	}

	public String getCarNumaprob() {
		return (carNumaprob != null) ? carNumaprob : "";
	}

	public void setCarNumaprob(String carNumaprob) {
		this.carNumaprob = carNumaprob;
	}

	public int getCarNumperiodo() {
		return carNumperiodo;
	}

	public void setCarNumperiodo(int carNumperiodo) {
		this.carNumperiodo = carNumperiodo;
	}

	public String getCarRegicfes() {
		return (carRegicfes != null) ? carRegicfes : "";
	}

	public void setCarRegicfes(String carRegicfes) {
		this.carRegicfes = carRegicfes;
	}

	public int getCarTipperiodo() {
		return carTipperiodo;
	}

	public void setCarTipperiodo(int carTipperiodo) {
		this.carTipperiodo = carTipperiodo;
	}

	public String getCarTitulo() {
		return (carTitulo != null) ? carTitulo : "";
	}

	public void setCarTitulo(String carTitulo) {
		this.carTitulo = carTitulo;
	}

	public int getCarTipoPrograma() {
		return carTipoPrograma;
	}

	public void setCarTipoPrograma(int carTipoPrograma) {
		this.carTipoPrograma = carTipoPrograma;
	}

	public String getCarUniversidad() {
		return carUniversidad;
	}

	public void setCarUniversidad(String carUniversidad) {
		this.carUniversidad = carUniversidad;
	}

}
