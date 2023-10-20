package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValidacionRotacion {

	public ValidacionRotacion(){}
	
	public static int i=0;	
	public static final int Institucion=i++;
	public static final int Sede=i++;
	public static final int Jornada=i++;
	public static final int Grado=i++;
	public static final int Grupo=i++;
	public static final int Fecha=i++;
	public static final int IAsignatura=i++;
	public static final int IGrado=i++;
	public static final int IIGrado=i++;
	public static final int IIAsignatura=i++;
	public static final int IIEspacio=i++;
	public static final int IICapacidad=i++;
	public static final int IIGrupo=i++;
	public static final int IICupo=i++;
	public static final int IIIDocumento=i++;
	public static final int IIINombre=i++;
	public static final int FIL=0;
	public static final int COL=1;

	public static String []propiedades={
	    "plantilla.Rotacion.Institucion",
	    "plantilla.Rotacion.Sede",
	    "plantilla.Rotacion.Jornada",
	    "plantilla.Rotacion.Grado",
	    "plantilla.Rotacion.Grupo",
	    "plantilla.Rotacion.Fecha",
	    "plantilla.Rotacion.1Asignatura",
	    "plantilla.Rotacion.1Grado",
	    "plantilla.Rotacion.2Grado",
	    "plantilla.Rotacion.2Asignatura",
	    "plantilla.Rotacion.2Espacio",
	    "plantilla.Rotacion.2Capacidad",
	    "plantilla.Rotacion.2Grupo",
	    "plantilla.Rotacion.2Cupo",
	    "plantilla.Rotacion.3Documento",
	    "plantilla.Rotacion.3Nombre"
	};
	
	public static int [][]Validacion=new int[propiedades.length][2];
	
	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel2");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				Validacion[i][FIL]=Integer.parseInt(p[FIL]);
				Validacion[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Validaciones:"+e);}
	}	
}
