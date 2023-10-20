package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResumenArea{

public ResumenArea(){}

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
public static final int Area=i++;
public static final int Nota=i++;
public static final int Fecha=i++;
public static final int Escala=i++;
public static final int Total=i++;

public static final int FIL=0;
public static final int COL=1;

public static String []propiedades={
        "plantilla.ResumenArea.Tipo",
        "plantilla.ResumenArea.Institucion",
        "plantilla.ResumenArea.Sede",
        "plantilla.ResumenArea.Jornada",
        "plantilla.ResumenArea.Metodologia",
        "plantilla.ResumenArea.Grado",
        "plantilla.ResumenArea.Grupo",
        "plantilla.ResumenArea.Periodo",
        "plantilla.ResumenArea.Codigo",
        "plantilla.ResumenArea.Apellido",
        "plantilla.ResumenArea.Nombre",
        "plantilla.ResumenArea.Area",
        "plantilla.ResumenArea.Nota",
        "plantilla.ResumenArea.Fecha",
        "plantilla.ResumenArea.Escala",
        "plantilla.ResumenArea.Total"
};
public static int [][]ResArea=new int[propiedades.length][2];


	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				ResArea[i][FIL]=Integer.parseInt(p[FIL]);
				ResArea[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Resumen de Area:"+e);}
	}
	
}
