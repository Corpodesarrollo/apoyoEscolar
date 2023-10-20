package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Recuperacion {

public Recuperacion(){}

public static int i=0;
public static final int Tipo=i++;
public static final int PlanEstudios=i++;
public static final int JerarquiaLogro=i++;
public static final int Cerrado=i++;
public static final int NivelLogro=i++;
public static final int JerarquiaGrupo=i++;
public static final int EscalaLogro1=i++;
public static final int EscalaLogro2=i++;
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
public static final int PeriodoActual1=i++;
public static final int PeriodoActual2=i++;
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
public static final int EvaluacionLogro=i++;
public static final int TxtCerrado=i++;

public static final int FIL=0;
public static final int COL=1;

public static String []propiedades={
        "plantilla.Recuperacion.Tipo",	
        "plantilla.Recuperacion.PlanEstudios",
        "plantilla.Recuperacion.JerarquiaLogro",
        "plantilla.Recuperacion.Cerrado",
        "plantilla.Recuperacion.NivelLogro",
        "plantilla.Recuperacion.JerarquiaGrupo",
        "plantilla.Recuperacion.EscalaLogro1",
        "plantilla.Recuperacion.EscalaLogro2",
        "plantilla.Recuperacion.ListaLogro1",
        "plantilla.Recuperacion.ListaLogro2",
        "plantilla.Recuperacion.Institucion1",
        "plantilla.Recuperacion.Institucion2",
        "plantilla.Recuperacion.Sede1",
        "plantilla.Recuperacion.Sede2",
        "plantilla.Recuperacion.Jornada1",
        "plantilla.Recuperacion.Jornada2",
        "plantilla.Recuperacion.Grado1",
        "plantilla.Recuperacion.Grado2",
        "plantilla.Recuperacion.Grupo1",
        "plantilla.Recuperacion.Grupo2",
        "plantilla.Recuperacion.Asignatura1",
        "plantilla.Recuperacion.Asignatura2",
        "plantilla.Recuperacion.Periodo1",
        "plantilla.Recuperacion.Periodo2",
        "plantilla.Recuperacion.PeriodoActual1",
        "plantilla.Recuperacion.PeriodoActual2",
        "plantilla.Recuperacion.Docente1",
        "plantilla.Recuperacion.Docente2",
        "plantilla.Recuperacion.Fecha1",
        "plantilla.Recuperacion.Fecha2",
        "plantilla.Recuperacion.Metodologia1",
        "plantilla.Recuperacion.Metodologia2",
        "plantilla.Recuperacion.Logros1",
        "plantilla.Recuperacion.Logros2",
        "plantilla.Recuperacion.Codigo",
        "plantilla.Recuperacion.Apellido",
        "plantilla.Recuperacion.Nombre",
        "plantilla.Recuperacion.EvaluacionLogro",
        "plantilla.Recuperacion.TxtCerrado"
};
public static int [][]Rec=new int[propiedades.length][2];


	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				Rec[i][FIL]=Integer.parseInt(p[FIL]);
				Rec[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Recuperacinn:"+e);}
	}
	static{
	    Cargar();   
	}
}