package siges.boletines;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.boletines.beans.FiltroBeanReports;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;

/**
 * Nombre: <BR>
 * Descripcinn: <BR>
 * Funciones de la pngina: <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerResumenAreaAsig extends HttpServlet {
	private static int i = 0;
	private Boletin_ boletin;
	private ResourceBundle rb;
	// private PreparedStatement pst=null;
	// private ResultSet rs=null;
	private Cursor cursor;
	private File reportFile;
	private File reportFile1;
	private File reportFile2;
	private File reportFile5;
	private File reportFile5_11;
	private File reportFileLogros;
	private File reportFileDescriptores;
	private File reportFileninguno;
	private File reportFileLogros5;
	private File reportFileLogros5_11;
	private File reportFilePreescolar_sin_img;
	private File reportFilePreescolar5_sin_img;
	private File reportFileNuevoFormato;
	private FiltroBeanReports filtro;

	@Override
	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		rb = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		String contextoTotal = context.getRealPath("/");
		String path = Ruta2.get(context.getRealPath("/"), rb.getString("boletines_ruta_jaspers"));
		String path1_logos = Ruta.get(context.getRealPath("/"), rb.getString("boletines_logos"));
		// System.out.println("String path1_logos " + path1_logos);
		String path_escudo = Ruta.get(context.getRealPath("/"), rb.getString("boletines_imgs_inst"));
		// System.out.println("path_escudo " + path_escudo);
		reportFile = new File(path + rb.getString("repResumenAreaAsig"));
		reportFile1 = new File(path + rb.getString("repreResumenAreaAsig"));
		/*
		 * reportFile5=new File(path+rb.getString("rep5")); reportFile5_11=new
		 * File(path+rb.getString("rep5_11")); reportFile1=new
		 * File(path+rb.getString("repre")); reportFile2=new
		 * File(path+rb.getString("repre5")); reportFileLogros=new
		 * File(path+rb.getString("logros")); reportFileninguno=new
		 * File(path+rb.getString("ninguno")); reportFileDescriptores=new
		 * File(path+rb.getString("descriptores")); reportFileLogros5=new
		 * File(path+rb.getString("logros5")); reportFileLogros5_11=new
		 * File(path+rb.getString("logros5_11"));
		 * reportFilePreescolar_sin_img=new
		 * File(path+rb.getString("repPre_sin_img"));
		 * reportFilePreescolar5_sin_img=new
		 * File(path+rb.getString("repPre5_sin_img"));
		 * reportFileNuevoFormato=new
		 * File(path+rb.getString("reportFileNuevoFormato"));
		 */
		int n = 1;
		System.out.println("RESUMEN AREA-ASIGNATURA: APOYO ESCOLAR 2023 INICIANDO...");
		super.init(config);
		cursor = new Cursor();
		if (!updateColaBoletinesEstadoCero()) {
			// System.out.println("n****NO ACTUALIZn LOS RESUMENES AREA Q
			// ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
			return;
		}
		// t=new Thread(new ResumenArea(cursor,contextoTotal,path,path1_logos
		// ,path_escudo,reportFile,reportFile1,n++));
		// t.start();
		ResumenAreaAsig rA = new ResumenAreaAsig(cursor, contextoTotal, path);
		rA.procesar_solicitudes();// se dispara el procesamiento de solicitudes
		return;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public boolean updateColaBoletinesEstadoCero() {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("actualizar_resumenAreaAsig_en_cola"));
			posicion = 1;
			pst.executeUpdate();
			pst.close();
			// System.out.println("actualizn en cola los resumenes area asignatura
			// generando");
			con.commit();
		} catch (Exception e) {
			System.out.println("Area-Asig: Ocurrio Excepcion al actualizar el estado a -1!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

}
