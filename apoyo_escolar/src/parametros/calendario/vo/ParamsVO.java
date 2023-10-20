package parametros.calendario.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_DEFAULT = 1;
	public static final int CMD_NUEVO = 2;
	public static final int CMD_BUSCAR = 3;
	public static final int CMD_EDITAR = 4;
	public static final int CMD_GUARDAR = 5;
	public static final int CMD_ACTUALIZAR = 6;

	public int getCMD_ACTUALIZAR() {
		return CMD_ACTUALIZAR;
	}
	public int getFichaDefault() {
		return FICHA_DEFAULT;
	}
	public int getCMD_NUEVO() {
		return CMD_NUEVO;
	}
	public int getCMD_BUSCAR() {
		return CMD_BUSCAR;
	}
	public int getCMD_EDITAR() {
		return CMD_EDITAR;
	}
	public int getCMD_GUARDAR() {
		return CMD_GUARDAR;
	}

}
