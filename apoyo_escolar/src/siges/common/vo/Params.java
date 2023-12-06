package siges.common.vo;

/*
 * Nota: Solo para almacenar codigos de tipos, niveles, opciones y comandos, los mensajes a usuario o cosas asi 
 * son en ResoursesVO
 */
public class Params {
	public static final int INCLUDE = 1;
	public static final int FORWARD = 2;
	public static final String SMS = "mensaje";
	public static final String DISABLED = "disabled";

	public static final int CADENA = java.sql.Types.VARCHAR;
	public static final int ENTERO = java.sql.Types.INTEGER;
	public static final int ENTEROLARGO = java.sql.Types.BIGINT;
	public static final int FECHA = java.sql.Types.DATE;
	public static final int NULL = java.sql.Types.NULL;
	public static final int CHAR = java.sql.Types.CHAR;
	public static final int BIGINT = java.sql.Types.BIGINT;

	public static final int ESTADO_ABIERTO = 1;
	public static final int ESTADO_CERRADO = 2;

	public static final int ESTADO_GENERADO = 1;
	public static final int ESTADO_ERROR = 2;

	public static final int TIPO_EVAL_ASIG_NUM = 1;
	public static final int TIPO_EVAL_ASIG_CONCEPTUAL = 2;
	public static final int TIPO_EVAL_ASIG_PORCENTUAL = 3;

	public static final int MODO_EVAL_ACOMULARIVO = 1;
	public static final int MODO_EVAL_PROMEDIADO = 2;

	public static final int TIPO_EVAL_PREES_CUALITATIVA = 1;
	public static final int TIPO_EVAL_PREES_ASIGTURA = 2;

	public static final int ESCALA_NUMERICA = 1;
	public static final int ESCALA_CONCEPTUAL = 2;
	public static final int ESCALA_PORCENTUAL = 3;
	public static final String ESCALA_CONCEPTUAL_ = "Conceptual";
	public static final String ESCALA_NUMERICA_ = "Numnrica/Porcentual";

	public static final int CMD_ACEPTAR = 1;
	public static final int CMD_CANCELAR = -1;
	public static final int CMD_BUSCAR = 2;
	public static final int CMD_GUARDAR = 3;
	public static final int CMD_GENERAR_HISTORICO = 8;
	public static final int CMD_validar = 8;
	public static final int CMD_ELIMINAR = 4;
	public static final int CMD_EDITAR = 5;
	public static final int CMD_NUEVO = 6;
	public static final int CMD_AJAX = 7;

	/*
	 * CMDns para el manejo de ajax
	 */
	public static final int CMD_AJAX_INST = 101;
	public static final int CMD_AJAX_LOC = 102;
	public static final int CMD_AJAX_SED = 103;
	public static final int CMD_AJAX_JORD = 104;
	public static final int CMD_AJAX_METD = 105;
	public static final int CMD_AJAX_GRAD = 106;
	public static final int CMD_AJAX_GRUP = 107;
	public static final int CMD_AJAX_AREA = 108;

	public static final int CMD_TAB_INSTANCIA = 90;

	public static final int ESTADO_FORM_EDICION = 1;

	public static final int PERIODO_PRIMERO = 1;
	public static final int PERIODO_SEGUNDO = 2;
	public static final int PERIODO_TERCERO = 3;
	public static final int PERIODO_CUARTO = 4;
	public static final int PERIODO_QUINTO = 5;

	public static final String PERIODO_PRIMERO_ = "Primero";
	public static final String PERIODO_SEGUNDO_ = "Segundo";
	public static final String PERIODO_TERCERO_ = "Tercero";
	public static final String PERIODO_CUARTO_ = "Cuarto";
	public static final String PERIODO_QUINTO_ = "Quinto";

	public static final int TIPO_DESCRIPTOR_FORTALEZA = 1;
	public static final int TIPO_DESCRIPTOR_DIFICULTAD = 2;
	public static final int TIPO_DESCRIPTOR_RECOMENDACION = 3;
	public static final int TIPO_DESCRIPTOR_ESTRATEGIA = 4;

	public static final String TIPO_DESCRIPTOR_FORTALEZA_ = "Fortaleza";
	public static final String TIPO_DESCRIPTOR_DIFICULTAD_ = "Dificultad";
	public static final String TIPO_DESCRIPTOR_RECOMENDACION_ = "Recomendacinn";
	public static final String TIPO_DESCRIPTOR_ESTRATEGIA_ = "Estrategia";

	public static final int ESTADO_REPORTE_COLA = -1;
	public static final int ESTADO_REPORTE_EJE = 0;
	public static final int ESTADO_REPORTE_GENOK = 1;
	public static final int ESTADO_REPORTE_NOGEN = 2;

	public static final String ARCHIVO_TIPO_ZIP = "zip";
	public static final String ARCHIVO_TIPO_XLS = "xls";
	public static final String ARCHIVO_TIPO_PDF = "pdf";

	public static final int TIPOREP_BOLETINES = 1;
	public static final int TIPOREP_ResumAreas = 2;
	public static final int TIPOREP_ResumAsigS = 3;
	public static final int TIPOREP_ResumAreaAsigS=3;
	public static final int TIPOREP_BolPadreFla = 4;
	public static final int TIPOREP_Certificados = 5;
	public static final int TIPOREP_RepLibroNota = 6;
	public static final int TIPOREP_RepResultados = 7;

	// Reportes estaditicos
	public static final int TIPOREP_RepEstdcAsig = 8;
	public static final int TIPOREP_RepEstdcArea = 9;
	public static final int TIPOREP_RepEstdcLogro = 10;
	public static final int TIPOREP_RepEstdcDesct = 11;
	public static final int TIPOREP_RepEstdcLogroGrad = 12;

	public static final int TIPO_COL_OFICIAL = 1;
	public static final int TIPO_COL_CONCESION = 2;
	public static final int TIPO_COL_CONVENIO = 3;

	public static final String TIPO_COL_OFICIAL_ = "OFICIAL";
	public static final String TIPO_COL_CONCESION_ = "CONCESION";
	public static final String TIPO_COL_CONVENIO_ = "CONVENIO";

	// tipos de hilos tabla DATOS_HILOS, columna DAHITIPOS
	public static final int HILO_TIPO_ACTUALIZA_MATR = 1;
	public static final int HILO_TIPO_CIERRE_VIG = 2;
	public static final int HILO_TIPO_ENVIAR_EMAIL = 3;
	public static final int HILO_TIPO_PER_PROG_FECHAS = 4; // CIERRE DE PERIODO
															// AUTOMATICO

	// Tipo de entidades que pueden crear mensajes en la tabla MENSAJES
	public static final int ENV_SISTEM = 1;
	public static final int ENV_SED = 2;
	public static final int ENV_LOC = 3;
	public static final int ENV_INST = 4;
	public static final String ENV_SISTEM_TEXT = "SISTEMA";
	public static final String ENV_SED_TEXT = "SED";
	public static final String ENV_LOC_TEXT = "LOCALIDAD";
	public static final String ENV_INST_TEXT = "COLEGIO";

	public static final int CMD_GENERAR = 8;
	public static final int CMD_TRAER_CONSTANCIA = 9;
	public static final int CMD_ENVIAR_CONSTANCIA = 10;
	public static final int CMD_BUSCAR_PADRE_FAMILIA = 11; 
	

	
	public int getCMD_BUSCAR_PADRE_FAMILIA() {
		return CMD_BUSCAR_PADRE_FAMILIA;
	}
	
	/**
	 * @return Returns the cMD_ACEPTAR.
	 */
	public int getCMD_ACEPTAR() {
		return CMD_ACEPTAR;
	}

	/**
	 * @return Returns the cMD_BUSCAR.
	 */
	public int getCMD_BUSCAR() {
		return CMD_BUSCAR;
	}

	/**
	 * @return Returns the cMD_CANCELAR.
	 */
	public int getCMD_CANCELAR() {
		return CMD_CANCELAR;
	}

	/**
	 * @return Returns the cMD_EDITAR.
	 */
	public int getCMD_EDITAR() {
		return CMD_EDITAR;
	}

	/**
	 * @return Returns the cMD_ELIMINAR.
	 */
	public int getCMD_ELIMINAR() {
		return CMD_ELIMINAR;
	}

	/**
	 * @return Returns the cMD_GUARDAR.
	 */
	public int getCMD_GUARDAR() {
		return CMD_GUARDAR;
	}

	/**
	 * @return Returns the cMD_NUEVO.
	 */
	public int getCMD_NUEVO() {
		return CMD_NUEVO;
	}

	/**
	 * @return Returns the cMD_GUARDAR.
	 */
	public int getCMD_GENERAR_HISTORICO() {
		return CMD_GENERAR_HISTORICO;
	}

	/**
	 * @return Returns the sMS.
	 */
	public String getSMS() {
		return SMS;
	}

	/**
	 * @return Returns the fORWARD.
	 */
	public int getFORWARD() {
		return FORWARD;
	}

	/**
	 * @return Returns the iNCLUDE.
	 */
	public int getINCLUDE() {
		return INCLUDE;
	}

	public int getESTADO_ABIERTO() {
		return ESTADO_ABIERTO;
	}

	public int getESTADO_CERRADO() {
		return ESTADO_CERRADO;
	}

	public int getCMD_TAB_INSTANCIA() {
		return CMD_TAB_INSTANCIA;
	}

	/**
	 * @return Returns the cMD_AJAX.
	 */
	public int getCMD_AJAX() {
		return CMD_AJAX;
	}

	public int getCMD_GENERAR() {
		return CMD_GENERAR;
	}

	public int getESCALA_CONCEPTUAL() {
		return ESCALA_CONCEPTUAL;
	}

	public int getESCALA_NUMERICA() {
		return ESCALA_NUMERICA;
	}

	public int getESCALA_PORCENTUAL() {
		return ESCALA_PORCENTUAL;
	}

	/*
	 * Metodos mutadores para CMDns ajax
	 */
	public int getCMD_AJAX_GRAD() {
		return CMD_AJAX_GRAD;
	}

	public int getCMD_AJAX_GRUP() {
		return CMD_AJAX_GRUP;
	}

	public int getCMD_AJAX_INST() {
		return CMD_AJAX_INST;
	}

	public int getCMD_AJAX_JORD() {
		return CMD_AJAX_JORD;
	}

	public int getCMD_AJAX_LOC() {
		return CMD_AJAX_LOC;
	}

	public int getCMD_AJAX_METD() {
		return CMD_AJAX_METD;
	}

	public int getCMD_AJAX_SED() {
		return CMD_AJAX_SED;
	}

	public int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}

}
