package siges.conflictoReportes.beans;

import java.util.ResourceBundle;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class Reporte2 {

    public Reporte2(){}

    public static int i=0;
    public static final int Institucion=i++;
    public static final int Sede=i++;
    public static final int Jornada=i++;
    public static final int Periodo=i++;
    public static final int Fecha=i++;
    public static final int Usuario=i++;
    public static final int Ininumdocum=i++;
    public static final int Iniapellido=i++;
    public static final int Ininombre=i++;
    public static final int Inigrado=i++;
    public static final int Inigrupo=i++;

    public static final int FIL=0;
    public static final int COL=1;

    public static String []propiedades={
      "reporte2.institucion",
      "reporte2.sede",
      "reporte2.jornada",
      "reporte2.periodo",
      "reporte2.fecha",
      "reporte2.usuario",
      "reporte2.ininumdocum",
      "reporte2.iniapellido",
      "reporte2.ininombre",
      "reporte2.inigrado",
      "reporte2.inigrupo",
    };

    public static int [][]Rep2=new int[propiedades.length][2];

    static public void Cargar() {
        try{
            ResourceBundle rb= ResourceBundle.getBundle("conflictoReportes");
            String []p;
            for(int i=0;i<propiedades.length;i++){
                p=rb.getString(propiedades[i]).replace('.',':').split(":");
                Rep2[i][FIL]=Integer.parseInt(p[FIL]);
                Rep2[i][COL]=Integer.parseInt(p[COL]);
            }
      			}catch(Exception e){System.out.println("-SIGES- Error Cargando Bean de Reporte 2:"+e);}
    }

    static{
        Cargar();
    }

}
