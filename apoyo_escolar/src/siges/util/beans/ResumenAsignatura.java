package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResumenAsignatura {

public ResumenAsignatura(){}

public static int i=0;
public static final int Tipo=i++;
public static final int Institucion=i++;
public static final int Sede=i++;
public static final int Jornada=i++;
public static final int Metodologia=i++;
public static final int Grado=i++;
public static final int Grupo=i++;
public static final int Periodo=i++;
public static final int Codigo=i++;
public static final int Apellido=i++;
public static final int Nombre=i++;
public static final int Asignatura=i++;
public static final int Nota=i++;
public static final int Fecha=i++;
public static final int Escala=i++;
public static final int Total=i++;

public static final int FIL=0;
public static final int COL=1;

public static String []propiedades={
        "plantilla.ResumenAsignatura.Tipo",
        "plantilla.ResumenAsignatura.Institucion",
        "plantilla.ResumenAsignatura.Sede",
        "plantilla.ResumenAsignatura.Jornada",
        "plantilla.ResumenAsignatura.Metodologia",
        "plantilla.ResumenAsignatura.Grado",
        "plantilla.ResumenAsignatura.Grupo",
        "plantilla.ResumenAsignatura.Periodo",
        "plantilla.ResumenAsignatura.Codigo",
        "plantilla.ResumenAsignatura.Apellido",
        "plantilla.ResumenAsignatura.Nombre",
        "plantilla.ResumenAsignatura.Asignatura",
        "plantilla.ResumenAsignatura.Nota",
        "plantilla.ResumenAsignatura.Fecha",
        "plantilla.ResumenAsignatura.Escala",
        "plantilla.ResumenAsignatura.Total"
};
public static int [][]ResAsig=new int[propiedades.length][2];


	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				ResAsig[i][FIL]=Integer.parseInt(p[FIL]);
				ResAsig[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Resumen de Asignatura:"+e);}
	}
}
