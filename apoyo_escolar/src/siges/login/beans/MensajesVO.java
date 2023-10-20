package siges.login.beans;

import siges.common.vo.Vo;

public class MensajesVO extends Vo {
	private int msjcodigo;
	private String msjasunto;
	private String msjfecha;
	private String msjfechaini;
	private String msjfechafin;
	private String msjcontenido;
	private int msjenviadopor;
	private String msjenviadoaperfil;
	private String msjenviadoalocal;
	private String msjenviadoacoleg;
	private String msjenviadoasede;
	private String msjenviadoajorn;
	private String msjusuario;

	public static String formatoInst(String codInst) {
		String[] array = codInst.split("\\,");
		String resul = "";

		if (codInst == null) {
			// System.out.println("codInst es null");
			return null;
		}

		for (int i = 0; i < array.length; i++) {
			String array2[] = ((String) array[i]).split("\\|");
			resul += array2[array2.length - 1] + ",";
		}
		if (resul.length() > 0) {
			resul = resul.substring(0, resul.length() - 1);
		}
		return resul;
	}

	public static String formatoSede(String codSede) {
		String[] array = codSede.split("\\,");
		String resul = "";

		if (codSede == null) {
			// System.out.println("codSede es null");
			return null;
		}

		for (int i = 0; i < array.length; i++) {
			String array2[] = ((String) array[i]).split("\\|");
			resul += array2[1] + ",";
		}
		if (resul.length() > 0) {
			resul = resul.substring(0, resul.length() - 1);
		}
		return resul;
	}

	public static String[] formatoSedeArray(String codSede) {
		if (codSede == null) {
			// System.out.println("formatoSedeArray codSede es null");
			return null;
		}
		String[] array = codSede.split("\\,");
		return array;
	}

	public String getMsjasunto() {
		return msjasunto;
	}

	public void setMsjasunto(String msjasunto) {
		this.msjasunto = msjasunto;
	}

	public int getMsjcodigo() {
		return msjcodigo;
	}

	public void setMsjcodigo(int msjcodigo) {
		this.msjcodigo = msjcodigo;
	}

	public String getMsjcontenido() {
		return msjcontenido;
	}

	public void setMsjcontenido(String msjcontenido) {
		this.msjcontenido = msjcontenido;
	}

	public String getMsjenviadoacoleg() {
		return msjenviadoacoleg;
	}

	public void setMsjenviadoacoleg(String msjenviadoacoleg) {
		this.msjenviadoacoleg = msjenviadoacoleg;
	}

	public String getMsjenviadoajorn() {
		return msjenviadoajorn;
	}

	public void setMsjenviadoajorn(String msjenviadoajorn) {
		this.msjenviadoajorn = msjenviadoajorn;
	}

	public String getMsjenviadoalocal() {
		return msjenviadoalocal;
	}

	public void setMsjenviadoalocal(String msjenviadoalocal) {
		this.msjenviadoalocal = msjenviadoalocal;
	}

	public String getMsjenviadoaperfil() {
		return msjenviadoaperfil;
	}

	public void setMsjenviadoaperfil(String msjenviadoaperfil) {
		this.msjenviadoaperfil = msjenviadoaperfil;
	}

	public String getMsjenviadoasede() {
		return msjenviadoasede;
	}

	public void setMsjenviadoasede(String msjenviadoasede) {
		this.msjenviadoasede = msjenviadoasede;
	}

	public int getMsjenviadopor() {
		return msjenviadopor;
	}

	public void setMsjenviadopor(int msjenviadopor) {
		this.msjenviadopor = msjenviadopor;
	}

	public String getMsjfecha() {
		return msjfecha;
	}

	public void setMsjfecha(String msjfecha) {
		this.msjfecha = msjfecha;
	}

	public String getMsjfechafin() {
		return msjfechafin;
	}

	public void setMsjfechafin(String msjfechafin) {
		this.msjfechafin = msjfechafin;
	}

	public String getMsjfechaini() {
		return msjfechaini;
	}

	public void setMsjfechaini(String msjfechaini) {
		this.msjfechaini = msjfechaini;
	}

	public String getMsjusuario() {
		return msjusuario;
	}

	public void setMsjusuario(String msjusuario) {
		this.msjusuario = msjusuario;
	}

}
