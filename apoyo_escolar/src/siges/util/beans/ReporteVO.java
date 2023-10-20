package siges.util.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.ItemVO;

/**
 * @author desarrollo
 * @Date 24-10-10
 */
public class ReporteVO {
	
	public static final List lista = new ArrayList(); 
	
	public static final String REP_PLANTILLA_EVAL     = "Plantillas de evaluación";
	public static final int   _REP_PLANTILLA_EVAL     = 1;
	
	public static final String REP_PLANTILLA_MASIVAS  = "Plantillas masivas";
	public static final int   _REP_PLANTILLA_MASIVAS  = 2;
	
	public static final String REP_BOLETINES          = "Boletines";
	public static final int   _REP_BOLETINES          = 3;
	
	public static final String REP_CERTIFICADOS       = "Certificados";
	public static final int  _REP_CERTIFICADOS        = 4;
	
	public static final String REP_PLANTILLA_LOGROS   = "Plantillas Logros";
	public static final int   _REP_PLANTILLA_LOGROS   = 5;
	
	//* REPORTE MODULO 6: inconsistencia eval deshabilitado
	
	public static final String REP_CONSTANCIAS         = "Constancias";
	public static final int  _REP_CONSTANCIAS         = 7;
	
	public static final String REP_LIBRO_NOTAS         = "Libro de notas";
	public static final int   _REP_LIBRO_NOTAS        = 8;
	
	public static final String REP_PLAN_ESTUDIO        = "Reportes de plan de estudios";
	public static final int   _REP_PLAN_ESTUDIO       = 9;
	
    //* REPORTE MODULO 10: reporte logros : Rep gestacin deshabilitado
	//* REPORTE MODULO 11: Reporte descriptores deshabilitado
	//* REPORTE MODULO 12: Reporte reporte_inf_basica deshabilitado
	//* REPORTE MODULO 13: NN
	
	
	public static final String REP_ESTADISTICOS        = "Reportes estadíticos";
	public static final int   _REP_ESTADISTICOS       = 114;
	//* REPORTE MODULO 14: Reporte Asistencia boletines pertenece al grupo de estadÃ­sticos
	//* REPORTE MODULO 15: Reporte Eval logro Est pertenece al grupo de estadÃ­sticos
	//* REPORTE MODULO 16: Reporte Eval asignatura Est pertenece al grupo de estadÃ­sticos
    //* REPORTE MODULO 17: Reporte Eval Ã¡rea Est pertenece al grupo de estadÃ­sticos
    //* REPORTE MODULO 18: Reporte Eval logro por grupo pertenece al grupo de estadÃ­sticos
	//* REPORTE MODULO 19: Reporte Eval asignatura por grupo pertenece al grupo de estadÃ­sticos
	
	public static final String REP_CARNES              = "Carnés";
	public static final int   _REP_CARNES             = 20;
	
	//* REPORTE MODULO 21: Reporte reporte_accesos deshabilitado
	
	public static final String REP_RESUMENES_AREA     = "Resúmenes Área";
	public static final int  _REP_RESUMENES_AREA      = 22;

	public static final String REP_RESUMENES_ASIG      = "Resúmenes Asignatura";
	public static final int  _REP_RESUMENES_ASIG       = 23;
	
	public static final String REP_HORARIOS_GRUP       = "Reportes de Horarios por grupo";
	public static final int   _REP_HORARIOS_GRUP       = 24;
	
	public static final String REP_HORARIOS_DOCENT     = "Reportes de Horarios por docente";
	public static final int   _REP_HORARIOS_DOCENT     = 25;
	
	public static final String REP_HORARIOS_ESP_FISICO = "Reportes de Horarios por Espacio Fisico";
	public static final int   _REP_HORARIOS_ESP_FISICO = -26;
	
	//* REPORTE MODULO 27: Reporte no existe NN.
	
	//* Este reporte esta pendiente a la fecha (24-10-10)
	public static final String REP_LOGRO_PENDIENTE     = "Logros pendientes";
	public static final int   _REP_LOGRO_PENDIENTE    = 28;

   //*	Estos reportes estan deshabilitado ( Lira 21-10-10)
   //	public static final String REP_NUTRICION           = "Reportes de NutriciÃ³n";
   //	public static final int   _REP_NUTRICION           = 29;
	
   //* REPORTE MODULO 30: Reporte Gestacion (No existe o no se encontro)
  //   public static final String REP_GESTACION           = "Reportes de GestaciÃ³n";
  //   public static final int   _REP_GESTACION           = 30;
	
   //* REPORTE MODULO 31: Reporte Lideres (No existe o no se encontro).
	
   //* REPORTE MODULO 35: Reporte Area por grupo deshabilitado
   //* REPORTE MODULO 36: Reporte EvalucaciÃ³n tipo descriptor
	
	public static final String REP_INF_ESTUD_Y_DOCEN   = "Info. de estudiantes y docentes";
	public static final int   _REP_INF_ESTUD_Y_DOCEN  = 37;
	
	
	public static final String REP_HORARIO_ART         = "Reportes Horarios Art.";
	public static final int   _REP_HORARIO_ART        = 45;
	
	public static final String REP_ENCUESTA            = "Reportes Encuesta";
	public static final int   _REP_ENCUESTA           = 48;
	
	public static final String REP_ENCUESTA_CONSOLIDADO   = "Reportes Encuesta Consolidad por encuesta";
	public static final int   _REP_ENCUESTA_CONSOLIDADO  = 49;
	
	public static final String REP_ENCUESTA_GENERAL   = "Reportes Encuesta Consolidad encuesta General";
	public static final int   _REP_ENCUESTA_GENERAL = 50;
	
	public static final String REP_ENCUESTA_MOTV_PREFE   = "Reportes Encuesta Motivo preferencias";
	public static final int   _REP_ENCUESTA_MOTV_PREFE= 51;
	
	public static final String REP_PLAN_ESTUDIO_ART    = "Reportes Plan Estudios Art.";
	public static final int   _REP_PLAN_ESTUDIO_ART   = 52;
	
	public static final String REP_ESTUDIO_ART         = "Reportes Estudiantes Art.";
	public static final int   _REP_ESTUDIO_ART        = 55;
	
	public static final String REP_ENCUESTA_EST_X_TUTOR   = "Reportes Encuesta Estudiantes por tutor";
	public static final int   _REP_ENCUESTA_EST_X_TUTOR   = 55;
	
	public static final String REP_ENCUESTA_EST_X_GRUP   = "Reportes Estudiantes por grupo";
	public static final int   _REP_ENCUESTA_EST_X_GRUP   = 57;
	
	public static final String REP_COMPARATIVOS        = "Reportes Comparativos";
	public static final int   _REP_COMPARATIVOS       = 60;
	
	public static final String REP_RESULT_ACADEMICOS   = "Reportes Resultados Académicos";
	public static final int   _REP_RESULT_ACADEMICOS  = 26;
	
	public static final String REP_RESULT_COMPARATIVO_VIGENCIA_ANTERIOR   = "Reportes Comparativo vigencia anterior";
	public static final int   _REP_RESULT_COMPARATIVO_VIGENCIA_ANTERIOR  = 27;
	
	public static final String REP_PLANTILLA_BATERIA   = "Plantillas Bateria";
	public static final int   _REP_PLANTILLA_BATERIA  = 102;
	
	//public static final String REP_CONFLICTO           = "Conflicto Escolar";
	//public static final int   _REP_CONFLICTO          = 103;
	
	public static final String REP_RESULT_IMPORTACION  = "Resultado de importación (Inconsistencias)";
	public static final int  _REP_RESULT_IMPORTACION  = 111;
	
//	public static final String REP_ORGANIZA_ESCOLAR    = "Reportes de OrganizaciÃ³n Escolar";
//	public static final int   _REP_ORGANIZA_ESCOLAR   = 120;
	
	//REP_PUESTOS
	public static final String REP_RESULT_PUESTOS  = "Reportes Puestos";
	public static final int   _REP_RESULT_PUESTOS  = 80;
	
	
	public static final int ORDEN_ASC=0; 
	public static final int ORDEN_DESC=2; 
	
    private long repTipo; 
    private int repOrden;
    private String repFecha;
    
     static{
    	lista.add(new ItemVO(_REP_PLANTILLA_MASIVAS, REP_PLANTILLA_MASIVAS));
    	lista.add(new ItemVO(_REP_PLANTILLA_EVAL, REP_PLANTILLA_EVAL));
    	lista.add(new ItemVO(_REP_PLANTILLA_BATERIA, REP_PLANTILLA_BATERIA));
    	lista.add(new ItemVO(_REP_PLANTILLA_LOGROS, REP_PLANTILLA_LOGROS));
    	
    	//lista.add(new ItemVO(_REP_CONFLICTO, REP_CONFLICTO));
    	lista.add(new ItemVO(_REP_BOLETINES, REP_BOLETINES));
    	lista.add(new ItemVO(_REP_LOGRO_PENDIENTE, REP_LOGRO_PENDIENTE));
    	lista.add(new ItemVO(_REP_LIBRO_NOTAS,REP_LIBRO_NOTAS));
    	lista.add(new ItemVO(_REP_RESUMENES_AREA, REP_RESUMENES_AREA));
    	lista.add(new ItemVO(_REP_RESUMENES_ASIG, REP_RESUMENES_ASIG));
    	lista.add(new ItemVO(_REP_CERTIFICADOS, REP_CERTIFICADOS));
    	lista.add(new ItemVO(_REP_CONSTANCIAS, REP_CONSTANCIAS));
    	lista.add(new ItemVO(_REP_CARNES, REP_CARNES));
    	lista.add(new ItemVO(_REP_RESULT_IMPORTACION, REP_RESULT_IMPORTACION));
    	lista.add(new ItemVO(_REP_PLAN_ESTUDIO, REP_PLAN_ESTUDIO));
    	lista.add(new ItemVO(_REP_INF_ESTUD_Y_DOCEN, REP_INF_ESTUD_Y_DOCEN));
    	lista.add(new ItemVO(_REP_ESTADISTICOS, REP_ESTADISTICOS));
//    	lista.add(new ItemVO(_REP_NUTRICION, REP_NUTRICION));
//    	lista.add(new ItemVO(_REP_GESTACION, REP_GESTACION));
    	lista.add(new ItemVO(_REP_HORARIOS_GRUP, REP_HORARIOS_GRUP));
    	lista.add(new ItemVO(_REP_HORARIOS_DOCENT, REP_HORARIOS_DOCENT));
    	lista.add(new ItemVO(_REP_HORARIOS_ESP_FISICO, REP_HORARIOS_ESP_FISICO));
//    	lista.add(new ItemVO(_REP_ORGANIZA_ESCOLAR, REP_ORGANIZA_ESCOLAR));
    	lista.add(new ItemVO(_REP_ENCUESTA, REP_ENCUESTA));
    	lista.add(new ItemVO(_REP_HORARIO_ART, REP_HORARIO_ART));
    	lista.add(new ItemVO(_REP_PLAN_ESTUDIO_ART, REP_PLAN_ESTUDIO_ART));
    	lista.add(new ItemVO(_REP_ESTUDIO_ART, REP_ESTUDIO_ART));
    	lista.add(new ItemVO(_REP_COMPARATIVOS, REP_COMPARATIVOS));
    	lista.add(new ItemVO(_REP_RESULT_COMPARATIVO_VIGENCIA_ANTERIOR,REP_RESULT_COMPARATIVO_VIGENCIA_ANTERIOR));
    	lista.add(new ItemVO(_REP_RESULT_ACADEMICOS, REP_RESULT_ACADEMICOS));
    	lista.add(new ItemVO(_REP_ENCUESTA_EST_X_GRUP, REP_ENCUESTA_EST_X_GRUP));
    	 
    	lista.add(new ItemVO(_REP_RESULT_PUESTOS, REP_RESULT_PUESTOS));//REP_PUESTOS
    	Collections.sort(lista, new Comparator(){
    		 
    		 public int compare(Object o1, Object o2) {
    	    	    ItemVO rep1 = (ItemVO) o1;
    	    	    ItemVO rep2 = (ItemVO) o2;
    	    	    return rep1.getNombre().compareTo(rep2.getNombre());
    	    	  }

    	    	  public boolean equals(Object o) {
    	    	    return this == o;
    	    	  }
    	});
        
    }
    
    /**
     * @return Returns the repFecha.
     */
    public String getRepFecha() {
        return repFecha!=null?repFecha:"";
    }
    /**
     * @param repFecha The repFecha to set.
     */
    public void setRepFecha(String repFecha) {
        this.repFecha = repFecha;
    }
 
 
    /**
     * @return Returns the repTipo.
     */
	public long getRepTipo() {
		return repTipo;
	}
	public void setRepTipo(long repTipo) {
		this.repTipo = repTipo;
	}
	public int getRepOrden() {
		return repOrden;
	}
	public void setRepOrden(int repOrden) {
		this.repOrden = repOrden;
	}
 
 
    
     
}
