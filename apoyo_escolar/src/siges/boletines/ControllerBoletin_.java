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

public class ControllerBoletin_ extends HttpServlet {
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
	private Thread t;

	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		rb = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		String contextoTotal = context.getRealPath("/");
		String path = Ruta2.get(context.getRealPath("/"),
				rb.getString("boletines_ruta_jaspers"));
		String path1 = Ruta2.get(context.getRealPath("/"),
				rb.getString("boletines_imgs"));
		String path2 = Ruta2.get(context.getRealPath("/"),
				rb.getString("boletines_imgs_inst"));
		reportFile = new File(path + rb.getString("rep"));
		reportFile1 = new File(path + rb.getString("repre"));
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
		// System.out.println("BOLETINES: APOYO ESCOLAR 2010 INICIANDO...");
		super.init(config);
		cursor = new Cursor();
		if (!updateColaBoletinesEstadoCero()) {
			// System.out
			// .println("n****NO ACTUALIZn LOS BOLETINES Q ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
			return;
		}
		t = new Thread(new Boletin_(cursor, contextoTotal, path, path1, path2,
				reportFile, reportFile1, n++));
		t.start();
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
			pst = con.prepareStatement(rb
					.getString("actualizar_boletines_en_cola"));
			posicion = 1;
			pst.executeUpdate();
			pst.close();
			// System.out.println("actualizn en cola los boletines generando");
			con.commit();
		} catch (Exception e) {
			// System.out
			// .println("nOcurrin Excepcion al actualizar el estado a -1!");
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
