package siges.util.beans;

import java.util.ResourceBundle;

/**
 * @author John
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Area {

	public Area(){}
	
	public static int i=0;	
	public static final int PonerMetodologia=i++;
	public static final int Tipo=i++;
	public static final int PlanEstudios=i++;
	public static final int JerarquiaArea=i++;
	public static final int Cerrado=i++;
	public static final int NivelLogro=i++;
	public static final int JerarquiaGrupo=i++;
	public static final int EscalaArea1=i++;
	public static final int EscalaArea2=i++;
	public static final int Ausencia1=i++;
	public static final int Ausencia2=i++;
	public static final int Fortaleza1=i++;
	public static final int Fortaleza2=i++;
	public static final int Dificultad1=i++;
	public static final int Dificultad2=i++;
	public static final int Recomendacion1=i++;
	public static final int Recomendacion2=i++;
	public static final int Estrategia1=i++;
	public static final int Estrategia2=i++;
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
	public static final int Area1=i++;
	public static final int Area2=i++;
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
	public static final int Recuperacion=i++;
	public static final int AusenciaJus=i++;
	public static final int AusenciaNoJus=i++;
	public static final int Motivo=i++;
	public static final int Porcentaje=i++;
	public static final int EvalFortaleza=i++;
	public static final int EvalDificultad=i++;
	public static final int EvalRecomendacion=i++;
	public static final int EvalEstrategia=i++;
	public static final int TxtCerrado=i++;
	
	public static final int FIL=0;
	public static final int COL=1;

	public static String []propiedades={
	  	"plantilla.Area.PonerMetodologia",
			"plantilla.Area.Tipo",
			"plantilla.Area.PlanEstudios",
			"plantilla.Area.JerarquiaArea",
			"plantilla.Area.Cerrado",
			"plantilla.Area.NivelLogro",
			"plantilla.Area.JerarquiaGrupo",
			"plantilla.Area.EscalaArea1",
			"plantilla.Area.EscalaArea2",
			"plantilla.Area.Ausencia1",
			"plantilla.Area.Ausencia2",
			"plantilla.Area.Fortaleza1",
			"plantilla.Area.Fortaleza2",
			"plantilla.Area.Dificultad1",
			"plantilla.Area.Dificultad2",
			"plantilla.Area.Recomendacion1",
			"plantilla.Area.Recomendacion2",
			"plantilla.Area.Estrategia1",
			"plantilla.Area.Estrategia2",
			"plantilla.Area.Institucion1",
			"plantilla.Area.Institucion2",
			"plantilla.Area.Sede1",
			"plantilla.Area.Sede2",
			"plantilla.Area.Jornada1",
			"plantilla.Area.Jornada2",
			"plantilla.Area.Grado1",
			"plantilla.Area.Grado2",
			"plantilla.Area.Grupo1",
			"plantilla.Area.Grupo2",
			"plantilla.Area.Area1",
			"plantilla.Area.Area2",
			"plantilla.Area.Periodo1",
			"plantilla.Area.Periodo2",
			"plantilla.Area.Docente1",
			"plantilla.Area.Docente2",
			"plantilla.Area.Fecha1",
			"plantilla.Area.Fecha2",
			"plantilla.Area.Metodologia1",
			"plantilla.Area.Metodologia2",
			"plantilla.Area.Codigo",
			"plantilla.Area.Apellido",
			"plantilla.Area.Nombre",
			"plantilla.Area.Evaluacion",
			"plantilla.Area.Recuperacion",
			"plantilla.Area.AusenciaJus",
			"plantilla.Area.AusenciaNoJus",
			"plantilla.Area.Motivo",
			"plantilla.Area.Porcentaje",
			"plantilla.Area.EvalFortaleza",
			"plantilla.Area.EvalDificultad",
			"plantilla.Area.EvalRecomendacion",
			"plantilla.Area.EvalEstrategia",
			"plantilla.Area.TxtCerrado"
	};
	
	public static int [][]Area=new int[propiedades.length][2];
	
	static public void Cargar() {
		try{
			ResourceBundle rb= ResourceBundle.getBundle("excel");
			String []p;
			for(int i=0;i<propiedades.length;i++){
				p=rb.getString(propiedades[i]).replace('.',':').split(":");
				Area[i][FIL]=Integer.parseInt(p[FIL]);
				Area[i][COL]=Integer.parseInt(p[COL]);
			}
		}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Area:"+e);}
	}	
}
