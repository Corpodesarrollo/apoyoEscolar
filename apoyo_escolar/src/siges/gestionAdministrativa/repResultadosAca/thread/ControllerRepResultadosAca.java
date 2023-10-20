package siges.gestionAdministrativa.repResultadosAca.thread;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.boletines.Boletin_;
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

public class ControllerRepResultadosAca extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int i = 0;
	private Boletin_ boletin;
	private ResourceBundle rb;
	private Cursor cursor;
	private File reporte1;
	private File reporte2;
	private File reporte3;

	private FiltroBeanReports filtro;
	private Thread t;

	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.repResultadosAca.bundle.repResultadosAca");
		String contextoTotal = context.getRealPath("/");
		String path = Ruta2.get(context.getRealPath("/"),
				rb.getString("rep_ruta_jaspers"));
		String path_escudo = Ruta.get(context.getRealPath("/"),
				rb.getString("rep_escudo"));
		String path2 = "";
		reporte1 = new File(path + rb.getString("rep.reporte1"));
		reporte2 = new File(path + rb.getString("rep.reporte2"));
		reporte3 = new File(path + rb.getString("rep.reporte3"));

		int n = 1;
//		System.out.println(new Date()+ " REPORTES RESULTADOS (new): APOYO ESCOLAR 2010 INICIANDO...");
		super.init(config);
		cursor = new Cursor();
		if (!updateColaBolEstadoCero()) {
//			System.out.println(new Date()+ " REPORTES RESULTADOS: NO ACTUALIZn LOS REPORTES Q ESTABAN EN ESTADO CERO.");
			return;
		}
		t = new Thread(new RepResultadosAca(cursor, contextoTotal, path,
				path_escudo, path2, reporte1, reporte2, reporte3, n++));
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

	public boolean updateColaBolEstadoCero() {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("rep.updateEnCola"));
			posicion = 1;
			pst.executeUpdate();
			pst.close();
			System.out
					.println("REPORTES RESULTADOS: actualizn en cola los reportes Resultados generando");
			con.commit();
		} catch (Exception e) {
			System.out
					.println("REPORTES RESULTADOS: nOcurrin Excepcion al actualizar el estado a -1!");
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