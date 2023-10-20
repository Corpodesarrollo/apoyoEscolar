package siges.util; 

import siges.common.vo.Params;

/**
*	Nombre:	Logger<br>
*	Descripcinn:	Manejo del Logger<br>
*	Funciones de la pngina:	Implementar el logger para que sea usado en cualquier clae que lo llame<br>
*	Entidades afectadas:	log_application<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class Properties extends Params{  
	public static final int LOGRO=1;
	public static final int ASIGNATURA=2;
	public static final int AREA=3;
	public static final int DESCRIPTOR=4;
	public static final int BATERIALOGRO=5;
	public static final int BATERIADESCRIPTOR=7;
	public static final int PREESCOLAR=8;

	public static final int TIPOLOGRO=1;
	public static final int TIPOASIGNATURA=2;
	public static final int TIPOAREA=3;
	public static final int TIPODESCRIPTOR=5;
	public static final int TIPOBATERIALOGRO=4;
	public static final int TIPOBATERIADESCRIPTOR=6;
	public static final int TIPOPREESCOLAR=8;
	
	public static final int PLANTILLALOGROASIG=1;
	public static final int PLANTILLAAREADESC=2;
	public static final int PLANTILLAPREE=3;
	public static final int PLANTILLABATLOGRO=4;
	public static final int PLANTILLABATDESCRIPTOR=5;
	public static final int PLANTILLARECUPERACION=6;

	public static final Integer CADENA=new Integer(java.sql.Types.VARCHAR);
	public static final Integer ENTERO=new Integer(java.sql.Types.INTEGER);
	public static final Integer FECHA=new Integer(java.sql.Types.DATE);
	public static final Integer NULO=new Integer(java.sql.Types.NULL);
	public static final Integer DOBLE=new Integer(java.sql.Types.DOUBLE);
	public static final Integer CARACTER=new Integer(java.sql.Types.CHAR);
	public static final Integer ENTEROLARGO=new Integer(java.sql.Types.BIGINT);

	public static final int FILAMENSAJE=0;
	public static final int COLUMNAMENSAJE=1;
	
	public static final int _SELF=0;
	public static final int CENTRO=1;
	public static final int _TOP=2;
	public static final int _BLANK=3;
	
	/**
	 * @return Return the _BLANK.
	 */
	public int get_BLANK() {
		return _BLANK;
	}

	/**
	 * @return Return the _SELF.
	 */
	public int get_SELF() {
		return _SELF;
	}

	/**
	 * @return Return the _TOP.
	 */
	public int get_TOP() {
		return _TOP;
	}

	/**
	 * @return Return the aREA.
	 */
	public int getAREA() {
		return AREA;
	}

	/**
	 * @return Return the aSIGNATURA.
	 */
	public int getASIGNATURA() {
		return ASIGNATURA;
	}

	/**
	 * @return Return the bATERIADESCRIPTOR.
	 */
	public int getBATERIADESCRIPTOR() {
		return BATERIADESCRIPTOR;
	}

	/**
	 * @return Return the bATERIALOGRO.
	 */
	public int getBATERIALOGRO() {
		return BATERIALOGRO;
	}

	/**
	 * @return Return the cADENA.
	 */
	public Integer getCADENA() {
		return CADENA;
	}

	/**
	 * @return Return the cARACTER.
	 */
	public Integer getCARACTER() {
		return CARACTER;
	}

	/**
	 * @return Return the cENTRO.
	 */
	public int getCENTRO() {
		return CENTRO;
	}

	/**
	 * @return Return the cOLUMNAMENSAJE.
	 */
	public int getCOLUMNAMENSAJE() {
		return COLUMNAMENSAJE;
	}

	/**
	 * @return Return the dESCRIPTOR.
	 */
	public int getDESCRIPTOR() {
		return DESCRIPTOR;
	}

	/**
	 * @return Return the dOBLE.
	 */
	public Integer getDOBLE() {
		return DOBLE;
	}

	/**
	 * @return Return the eNTERO.
	 */
	public Integer getENTERO() {
		return ENTERO;
	}

	/**
	 * @return Return the eNTEROLARGO.
	 */
	public Integer getENTEROLARGO() {
		return ENTEROLARGO;
	}

	/**
	 * @return Return the fECHA.
	 */
	public Integer getFECHA() {
		return FECHA;
	}

	/**
	 * @return Return the fILAMENSAJE.
	 */
	public int getFILAMENSAJE() {
		return FILAMENSAJE;
	}

	/**
	 * @return Return the lOGRO.
	 */
	public int getLOGRO() {
		return LOGRO;
	}

	/**
	 * @return Return the nULO.
	 */
	public Integer getNULO() {
		return NULO;
	}

	/**
	 * @return Return the pLANTILLAAREADESC.
	 */
	public int getPLANTILLAAREADESC() {
		return PLANTILLAAREADESC;
	}

	/**
	 * @return Return the pLANTILLABATDESCRIPTOR.
	 */
	public int getPLANTILLABATDESCRIPTOR() {
		return PLANTILLABATDESCRIPTOR;
	}

	/**
	 * @return Return the pLANTILLABATLOGRO.
	 */
	public int getPLANTILLABATLOGRO() {
		return PLANTILLABATLOGRO;
	}

	/**
	 * @return Return the pLANTILLALOGROASIG.
	 */
	public int getPLANTILLALOGROASIG() {
		return PLANTILLALOGROASIG;
	}

	/**
	 * @return Return the pLANTILLAPREE.
	 */
	public int getPLANTILLAPREE() {
		return PLANTILLAPREE;
	}

	/**
	 * @return Return the pLANTILLARECUPERACION.
	 */
	public int getPLANTILLARECUPERACION() {
		return PLANTILLARECUPERACION;
	}

	/**
	 * @return Return the pREESCOLAR.
	 */
	public int getPREESCOLAR() {
		return PREESCOLAR;
	}

	/**
	 * @return Return the tIPOAREA.
	 */
	public int getTIPOAREA() {
		return TIPOAREA;
	}

	/**
	 * @return Return the tIPOASIGNATURA.
	 */
	public int getTIPOASIGNATURA() {
		return TIPOASIGNATURA;
	}

	/**
	 * @return Return the tIPOBATERIADESCRIPTOR.
	 */
	public int getTIPOBATERIADESCRIPTOR() {
		return TIPOBATERIADESCRIPTOR;
	}

	/**
	 * @return Return the tIPOBATERIALOGRO.
	 */
	public int getTIPOBATERIALOGRO() {
		return TIPOBATERIALOGRO;
	}

	/**
	 * @return Return the tIPODESCRIPTOR.
	 */
	public int getTIPODESCRIPTOR() {
		return TIPODESCRIPTOR;
	}

	/**
	 * @return Return the tIPOLOGRO.
	 */
	public int getTIPOLOGRO() {
		return TIPOLOGRO;
	}

	/**
	 * @return Return the tIPOPREESCOLAR.
	 */
	public int getTIPOPREESCOLAR() {
		return TIPOPREESCOLAR;
	}
}