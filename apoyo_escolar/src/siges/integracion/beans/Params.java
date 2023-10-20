package siges.integracion.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Params {
	//TIEMPOS DE TRIGGER
	public static final int T_BEFORE=1;
	public static final int T_AFTER=2;
	//CATEGORIAS DE TRIGGER
	public static final int C_INSTITUCION=1;
	public static final int C_SEDE=2;
	public static final int C_JORNADA=3;
	public static final int C_ESTUDIANTE=7;
	//FUNCIONES DE INSTITUCION
	public static final int F_INSERCIONINSTITUCION=1;
	public static final int F_ACTUALIZACIONDANE=2;
	public static final int F_ACTUALIZACIONNOMBRE=3;
	public static final int F_ACTUALIZACIONDEPTO=4;
	public static final int F_ACTUALIZACIONSECTOR=5;
	public static final int F_ELIMINACION=6;
	//FUNCIONES DE SEDE
	public static final int F_INSERCIONSEDE=1;
	public static final int F_ACTUALIZACIONDANESEDE=2;
	public static final int F_ACTUALIZACIONDANEINSTITUCION=3;
	public static final int F_ACTUALIZACIONNOMBRESEDE=4;
	public static final int F_ACTUALIZACIONDEPTOSEDE=5;
	public static final int F_ACTUALIZACIONCONSECUTIVOSEDE=6;
	public static final int F_ELIMINACIONSEDE=7;
	//func de jornada
	public static final int F_INSERCIONJORNADA=1;
	public static final int F_ACTUALIZACIONJORNADA=2;
	//FUNCIONES DE ESTUDIANTE
	public static final int F_ACTUALIZACIONNOMBREESTUDIANTE=4;	
	public static final int F_ACTUALIZACIONIDESTUDIANTE=5;	
	public static final int F_ACTUALIZACIONVIGENCIAESTUDIANTE=6;	
	public static final int F_INSERCIONESTUDIANTE=1;
	public static final int F_ACTUALIZACIONUBICACIONESTUDIANTE=7;
	
	//ESTADOS DE PILA
	public static final int NUEVO=0;
	public static final int TERMINADO=1;
	public static final int ERROR=2;
	private Params(){}
}
