package siges.gestionAdministrativa.gestionActividades.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int CMD_EDITAR = 1;
	public static final int CMD_ELIMINAR = 2;
	public static final int CMD_BUSCAR = 3;
	public static final int CMD_NUEVO = 4;
	public static final int CMD_GUARDAR = 6;
	
	public static final int FICHA_GESTION_ACTIVIDADES = 5;
	
	public int getFICHA_GESTION_ACTIVIDADES() {
		return FICHA_GESTION_ACTIVIDADES;
	}
	
	public int getCMD_GUARDAR() {
		return CMD_GUARDAR;
	}

	public int getCMD_EDITAR() {
		return CMD_EDITAR;
	}
	
	public int getCMD_ELIMINAR() {
		return CMD_ELIMINAR;
	}
	
	public int getCMD_BUSCAR() {
		return CMD_BUSCAR;
	}
	
	public int getCMD_NUEVO() {
		return CMD_NUEVO;
	}

}
