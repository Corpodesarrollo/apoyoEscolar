/**
 * 
 */
package siges.util.beans;

import java.util.ResourceBundle;

/**
 * 14/09/2007 
 * @author Latined
 * @version 1.2
 */
public class Comportamiento {
	public Comportamiento(){}
	
	public static int i=0;
	public static final int Tipo=i++;
	public static final int TxtCerrado=i++;
	public static final int Cerrado=i++;
	public static final int JerarquiaGrupo=i++;
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
	public static final int Periodo1=i++;
	public static final int Periodo2=i++;
	public static final int Docente1=i++;
	public static final int Docente2=i++;
	public static final int Fecha1=i++;
	public static final int Fecha2=i++;
	public static final int Metodologia1=i++;
	public static final int Metodologia2=i++;
	public static final int Codigo=i++;
	public static final int Apellido=i++;
	public static final int Nombre=i++;
	public static final int EvaluacionConceptual=i++;
	public static final int Observacion=i++;
	public static final int EscalaComportamiento1=i++;
	public static final int EscalaComportamiento2=i++;
	
	public static final int FIL=0;
	public static final int COL=1;
	
	public static String []propiedades={
		"plantilla.Comportamiento.Tipo",
		"plantilla.Comportamiento.TxtCerrado",
		"plantilla.Comportamiento.Cerrado",
		"plantilla.Comportamiento.JerarquiaGrupo",
		"plantilla.Comportamiento.Institucion1",
		"plantilla.Comportamiento.Institucion2",
		"plantilla.Comportamiento.Sede1",
		"plantilla.Comportamiento.Sede2",
		"plantilla.Comportamiento.Jornada1",
		"plantilla.Comportamiento.Jornada2",
		"plantilla.Comportamiento.Grado1",
		"plantilla.Comportamiento.Grado2",
		"plantilla.Comportamiento.Grupo1",
		"plantilla.Comportamiento.Grupo2",
		"plantilla.Comportamiento.Periodo1",
		"plantilla.Comportamiento.Periodo2",
		"plantilla.Comportamiento.Docente1",
		"plantilla.Comportamiento.Docente2",
		"plantilla.Comportamiento.Fecha1",
		"plantilla.Comportamiento.Fecha2",
		"plantilla.Comportamiento.Metodologia1",
		"plantilla.Comportamiento.Metodologia2",
		"plantilla.Comportamiento.Codigo",
		"plantilla.Comportamiento.Apellido",
		"plantilla.Comportamiento.Nombre",
		"plantilla.Comportamiento.EvaluacionConceptual",
		"plantilla.Comportamiento.Observacion",
		"plantilla.Comportamiento.EscalaComportamiento1",
		"plantilla.Comportamiento.EscalaComportamiento2"
	};
	public static int [][]plantilla=new int[propiedades.length][2];

	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				plantilla[i][FIL]=Integer.parseInt(p[FIL]);
				plantilla[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-APOYO- Error Cargando Bean de Comportamiento:"+e);}
	}
}
