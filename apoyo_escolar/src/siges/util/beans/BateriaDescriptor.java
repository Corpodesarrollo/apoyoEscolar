package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BateriaDescriptor {

public BateriaDescriptor(){}

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
public static final int Area1=i++;
public static final int Area2=i++;
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

public static final int FIL=0;
public static final int COL=1;

public static String []propiedades={
  	"plantilla.BatDescriptor.PonerMetodologia",
		"plantilla.BatDescriptor.Tipo",
		"plantilla.BatDescriptor.Nivel",
		"plantilla.BatDescriptor.PlanEstudios",
		"plantilla.BatDescriptor.ListaPeriodo1",
		"plantilla.BatDescriptor.ListaPeriodo2",
		"plantilla.BatDescriptor.Institucion1",
		"plantilla.BatDescriptor.Institucion2",
		"plantilla.BatDescriptor.Grado1",
		"plantilla.BatDescriptor.Grado2",
		"plantilla.BatDescriptor.Area1",
		"plantilla.BatDescriptor.Area2",
		"plantilla.BatDescriptor.Docente1",
		"plantilla.BatDescriptor.Docente2",
		"plantilla.BatDescriptor.Fecha1",
		"plantilla.BatDescriptor.Fecha2",
		"plantilla.BatDescriptor.Metodologia1",
		"plantilla.BatDescriptor.Metodologia2",
		"plantilla.BatDescriptor.PerInicial",
		"plantilla.BatDescriptor.PerFinal",
		"plantilla.BatDescriptor.Nombre",
		"plantilla.BatDescriptor.Abreviatura",
		"plantilla.BatDescriptor.Descripcion"
};
public static int [][]BatDes=new int[propiedades.length][2];


	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				BatDes[i][FIL]=Integer.parseInt(p[FIL]);
				BatDes[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Bat Desc:"+e);}
	}
}
