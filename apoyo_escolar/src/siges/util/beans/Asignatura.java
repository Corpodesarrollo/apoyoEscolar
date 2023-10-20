package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Asignatura {

public Asignatura(){}

public static int i=0;
public static final int PonerMetodologia=i++;
public static final int Tipo=i++;
public static final int PlanEstudios=i++;
public static final int JerarquiaLogro=i++;
public static final int Cerrado=i++;
public static final int NivelLogro=i++;
public static final int JerarquiaGrupo=i++;
public static final int EscalaAsignatura1=i++;
public static final int EscalaAsignatura2=i++;
public static final int EscalaLogro1=i++;
public static final int EscalaLogro2=i++;
public static final int Ausencia1=i++;
public static final int Ausencia2=i++;
public static final int ListaLogro1=i++;
public static final int ListaLogro2=i++;
public static final int Institucion1=i++;
public static final int Institucion2=i++;
public static final int Sede1=i++;
public static final int Sede2=i++;
public static final int Jornada1=i++;
public static final int Jornada2=i++;
public static final int Grado1=i++;
public static final int Grado2=i++;
public static final int Grupo1=i++;
public static final int Grupo2=i++;
public static final int Asignatura1=i++;
public static final int Asignatura2=i++;
public static final int Periodo1=i++;
public static final int Periodo2=i++;
public static final int Docente1=i++;
public static final int Docente2=i++;
public static final int Fecha1=i++;
public static final int Fecha2=i++;
public static final int Metodologia1=i++;
public static final int Metodologia2=i++;
public static final int Logros1=i++;
public static final int Logros2=i++;
public static final int Codigo=i++;
public static final int Apellido=i++;
public static final int Nombre=i++;
public static final int Evaluacion=i++;
public static final int Recuperacion=i++;
public static final int AusenciaJus=i++;
public static final int AusenciaNoJus=i++;
public static final int Motivo=i++;
public static final int Porcentaje=i++;
public static final int EvaluacionLogro=i++;
public static final int TxtCerrado=i++;

public static final int FIL=0;
public static final int COL=1;

public static String []propiedades={
  	"plantilla.Asignatura.PonerMetodologia",
		"plantilla.Asignatura.Tipo",
		"plantilla.Asignatura.PlanEstudios",
		"plantilla.Asignatura.JerarquiaLogro",
		"plantilla.Asignatura.Cerrado",
		"plantilla.Asignatura.NivelLogro",
		"plantilla.Asignatura.JerarquiaGrupo",
		"plantilla.Asignatura.EscalaAsignatura1",
		"plantilla.Asignatura.EscalaAsignatura2",
		"plantilla.Asignatura.EscalaLogro1",
		"plantilla.Asignatura.EscalaLogro2",
		"plantilla.Asignatura.Ausencia1",
		"plantilla.Asignatura.Ausencia2",
		"plantilla.Asignatura.ListaLogro1",
		"plantilla.Asignatura.ListaLogro2",
		"plantilla.Asignatura.Institucion1",
		"plantilla.Asignatura.Institucion2",
		"plantilla.Asignatura.Sede1",
		"plantilla.Asignatura.Sede2",
		"plantilla.Asignatura.Jornada1",
		"plantilla.Asignatura.Jornada2",
		"plantilla.Asignatura.Grado1",
		"plantilla.Asignatura.Grado2",
		"plantilla.Asignatura.Grupo1",
		"plantilla.Asignatura.Grupo2",
		"plantilla.Asignatura.Asignatura1",
		"plantilla.Asignatura.Asignatura2",
		"plantilla.Asignatura.Periodo1",
		"plantilla.Asignatura.Periodo2",
		"plantilla.Asignatura.Docente1",
		"plantilla.Asignatura.Docente2",
		"plantilla.Asignatura.Fecha1",
		"plantilla.Asignatura.Fecha2",
		"plantilla.Asignatura.Metodologia1",
		"plantilla.Asignatura.Metodologia2",
		"plantilla.Asignatura.Logros1",
		"plantilla.Asignatura.Logros2",
		"plantilla.Asignatura.Codigo",
		"plantilla.Asignatura.Apellido",
		"plantilla.Asignatura.Nombre",
		"plantilla.Asignatura.Evaluacion",
		"plantilla.Asignatura.Recuperacion",
		"plantilla.Asignatura.AusenciaJus",
		"plantilla.Asignatura.AusenciaNoJus",
		"plantilla.Asignatura.Motivo",
		"plantilla.Asignatura.Porcentaje",
		"plantilla.Asignatura.EvaluacionLogro",
		"plantilla.Asignatura.TxtCerrado"
};
public static int [][]Asig=new int[propiedades.length][2];


	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				Asig[i][FIL]=Integer.parseInt(p[FIL]);
				Asig[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Asignatura:"+e);}
	}
}
