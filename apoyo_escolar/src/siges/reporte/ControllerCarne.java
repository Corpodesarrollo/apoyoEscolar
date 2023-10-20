package siges.reporte;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.reporte.beans.FiltroBeanCarne;

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

public class ControllerCarne extends HttpServlet {
	private static int i = 0;
	private LogroGrupo logro;
	private ResourceBundle rb;
	private Cursor cursor;
	private File reportFile;
	private FiltroBeanCarne filtro;
	private Thread t;
	private String path;
	private String path_escudo;
	private String path_logo;
	private String path_img;
	private String path_fondo;

	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		rb = ResourceBundle.getBundle("siges.reporte.bundle.carnes");
		String contextoTotal = context.getRealPath("/");
		path = context.getRealPath(rb.getString("ruta_jaspers"));
		path_img = context.getRealPath(rb.getString("ruta_img"));
		path_escudo = Ruta.get("", rb.getString("ruta_escudo"));
		path_logo = Ruta.get("", rb.getString("ruta_logo"));
		path_fondo = Ruta.get("", rb.getString("ruta_fondo"));
		// reportFile=new
		// File(path+File.separator+rb.getString("jasper_carnes"));
		int n = 1;
		// System.out.println("CARNES INICIANDO");
		super.init(config);
		cursor = new Cursor();
		if (!updateColaCarnesEstadoCero()) {
			// System.out
			// .println("n****NO ACTUALIZn LOS CARNES QUE ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
			return;
		}
		t = new Thread(new Carne(cursor, contextoTotal, path, path_img,
				path_escudo, path_logo, path_fondo, reportFile, n++));
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

	public boolean updateColaCarnesEstadoCero() {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("actualizar_carnes_en_cola"));
			posicion = 1;
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (Exception e) {
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

	public void destroy() {
		try {
			t.stop();
			// System.out.println("CARNES FINALIZANDO");
		} catch (Exception ex) {
		}
	}

}
