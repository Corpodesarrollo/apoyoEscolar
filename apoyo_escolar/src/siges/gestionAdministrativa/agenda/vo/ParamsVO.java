package siges.gestionAdministrativa.agenda.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int CMD_EDITAR = 1;
	public static final int CMD_ELIMINAR = 2;
	public static final int CMD_BUSCAR = 3;
	public static final int CMD_NUEVO = 4;
	public static final int CMD_GUARDAR = 6;
	
	public static final int FICHA_TAREAS = 5;
	public static final int FICHA_CIRCULARES = 6;
	public static final int FICHA_PERMISOS = 7;
	
	public static final int CMD_AJAX_INSTANCIA0=21;
	
	public int getCMD_AJAX_INSTANCIA0() {
		return CMD_AJAX_INSTANCIA0;
	}
	
	public int getFICHA_PERMISOS() {
		return FICHA_PERMISOS;
	}
	
	public int getFICHA_TAREAS() {
		return FICHA_TAREAS;
	}
	
	public int getFICHA_CIRCULARES() {
		return FICHA_CIRCULARES;
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
