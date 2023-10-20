package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Preescolar {

public Preescolar(){}

public static int i=0;
public static final int PonerMetodologia=i++;
public static final int Tipo=i++;
public static final int NivelLogro=i++;
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
public static final int Dimension1=i++;
public static final int Dimension2=i++;
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
public static final int Evaluacion=i++;
public static final int EvaluacionConceptual=i++;
public static final int TxtCerrado=i++;

public static final int FIL=0;
public static final int COL=1;



public static final String labelINSTITUCION ="Institucinn :";
public static final String labelSEDE ="Sede :";
public static final String labelGRADO ="Grado :";
public static final String labelGRUPO ="Grupo :";
public static final String labelDIMENSION ="Dimensinn :";
public static final String labelPERIODO ="Pernodo :";
public static final String labelMETOD ="Metodologia: ";
public static final String labelFECHA ="Fecha :";
public static final String labelDOCENTE ="Docente :";
public static final String labelJORD ="Jornada :";
public static final String labelDATO_EVAL ="EVALUACION DE DIMENSION";
public static final String labelDATO_FIJAR_EVAL ="FIJAR EVALUACION";
public static final String labelDATOEST ="DATOS DE ESTUDIANTE";

public static final String labelNOMBRE ="NOMBRE";
public static final String labelCONSECUTIVO ="CnDIGO";
public static final String labelAPELLIDO ="APELLIDO";


public static String []propiedades={
  	"plantilla.Preescolar.PonerMetodologia",
		"plantilla.Preescolar.Tipo",
		"plantilla.Preescolar.NivelLogro",
		"plantilla.Preescolar.Cerrado",
		"plantilla.Preescolar.JerarquiaGrupo",
		"plantilla.Preescolar.Institucion1",
		"plantilla.Preescolar.Institucion2",
		"plantilla.Preescolar.Sede1",
		"plantilla.Preescolar.Sede2",
		"plantilla.Preescolar.Jornada1",
		"plantilla.Preescolar.Jornada2",
		"plantilla.Preescolar.Grado1",
		"plantilla.Preescolar.Grado2",
		"plantilla.Preescolar.Grupo1",
		"plantilla.Preescolar.Grupo2",
		"plantilla.Preescolar.Dimension1",
		"plantilla.Preescolar.Dimension2",
		"plantilla.Preescolar.Periodo1",
		"plantilla.Preescolar.Periodo2",
		"plantilla.Preescolar.Docente1",
		"plantilla.Preescolar.Docente2",
		"plantilla.Preescolar.Fecha1",
		"plantilla.Preescolar.Fecha2",
		"plantilla.Preescolar.Metodologia1",
		"plantilla.Preescolar.Metodologia2",
		"plantilla.Preescolar.Codigo",
		"plantilla.Preescolar.Apellido",
		"plantilla.Preescolar.Nombre",
		"plantilla.Preescolar.Evaluacion",
		"plantilla.Preescolar.EvaluacionConceptual",
		"plantilla.Preescolar.TxtCerrado"
		};
	public static int [][]Pree=new int[propiedades.length][2];


	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				Pree[i][FIL]=Integer.parseInt(p[FIL]);
				Pree[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Preescolar:"+e);}
	}
}