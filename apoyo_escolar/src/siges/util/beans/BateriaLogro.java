package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BateriaLogro {

public BateriaLogro(){}

public static int i=0;
public static final int PonerMetodologia=i++;
public static final int Tipo=i++;
public static final int Nivel=i++;
public static final int PlanEstudios=i++;
public static final int ListaPeriodo1=i++;
public static final int ListaPeriodo2=i++;
public static final int Institucion1=i++;
public static final int Institucion2=i++;
public static final int Grado1=i++;
public static final int Grado2=i++;
public static final int Asignatura1=i++;
public static final int Asignatura2=i++;
public static final int Docente1=i++;
public static final int Docente2=i++;
public static final int Fecha1=i++;
public static final int Fecha2=i++;
public static final int Metodologia1=i++;
public static final int Metodologia2=i++;
public static final int PerInicial=i++;
public static final int PerFinal=i++;
public static final int Nombre=i++;
public static final int Abreviatura=i++;
public static final int Descripcion=i++;
public static final int Orden=i++;

public static final int FIL=0;
public static final int COL=1;

public static String []propiedades={
  		"plantilla.BatLogro.PonerMetodologia",
		"plantilla.BatLogro.Tipo",
		"plantilla.BatLogro.Nivel",
		"plantilla.BatLogro.PlanEstudios",
		"plantilla.BatLogro.ListaPeriodo1",
		"plantilla.BatLogro.ListaPeriodo2",
		"plantilla.BatLogro.Institucion1",
		"plantilla.BatLogro.Institucion2",
		"plantilla.BatLogro.Grado1",
		"plantilla.BatLogro.Grado2",
		"plantilla.BatLogro.Asignatura1",
		"plantilla.BatLogro.Asignatura2",
		"plantilla.BatLogro.Docente1",
		"plantilla.BatLogro.Docente2",
		"plantilla.BatLogro.Fecha1",
		"plantilla.BatLogro.Fecha2",
		"plantilla.BatLogro.Metodologia1",
		"plantilla.BatLogro.Metodologia2",
		"plantilla.BatLogro.PerInicial",
		"plantilla.BatLogro.PerFinal",
		"plantilla.BatLogro.Nombre",
		"plantilla.BatLogro.Abreviatura",
		"plantilla.BatLogro.Descripcion",
		"plantilla.BatLogro.Orden"
};
public static int [][]BatLog=new int[propiedades.length][2];


	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				BatLog[i][FIL]=Integer.parseInt(p[FIL]);
				BatLog[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de BatLogro:"+e);}
	}
}
