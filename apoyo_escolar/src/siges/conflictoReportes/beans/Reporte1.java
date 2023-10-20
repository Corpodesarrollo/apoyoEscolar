package siges.conflictoReportes.beans;

import java.util.ResourceBundle;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class Reporte1 {

    public Reporte1(){}

    public static int i=0;
    public static final int Localidad=i++;
    public static final int Periodo=i++;
    public static final int Usuario=i++;
    public static final int Fecha=i++;
    public static final int Inicolegio=i++;
    public static final int Inisede=i++;
    public static final int Inijornada=i++;

    public static final int FIL=0;
    public static final int COL=1;

    public static String []propiedades={
      "reporte1.localidad",
      "reporte1.periodo",
      "reporte1.usuario",
      "reporte1.fecha",
      "reporte1.inicolegio",
      "reporte1.inisede",
      "reporte1.inijornada",
    };

    public static int [][]Rep1=new int[propiedades.length][2];

    static public void Cargar() {
        try{
            ResourceBundle rb= ResourceBundle.getBundle("conflictoReportes");
            String []p;
            for(int i=0;i<propiedades.length;i++){
                p=rb.getString(propiedades[i]).replace('.',':').split(":");
                Rep1[i][FIL]=Integer.parseInt(p[FIL]);
                Rep1[i][COL]=Integer.parseInt(p[COL]);
            }
      			}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Reporte 1:"+e);}
    }

    static{
        Cargar();
    }

}
