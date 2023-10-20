package siges.personal;

import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.dao.Cursor;
import siges.personal.beans.FiltroBeanForm;
import siges.personal.dao.ReportePersonalDAO;

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

public class ControllerReporte extends HttpServlet {
	private static int i = 0;
	private ReportePersonal reportePersonal;
	private ResourceBundle rb;
	private Cursor cursor;
	private File reportFile;
	private File reportFile_Ind;
	private FiltroBeanForm filtro;
	private Thread t;
	private ReportePersonalDAO reporteDao;

	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		rb = ResourceBundle
				.getBundle("siges.personal.bundle.preparedstatements_hoja_vida_personal");
		String contextoTotal = context.getRealPath("/");
		String path = context.getRealPath(rb.getString("ruta_jaspers"));
		String path1 = context.getRealPath(rb.getString("ruta_img"));
		reportFile = new File(path + "/" + rb.getString("jasper_personal"));
		reportFile_Ind = new File(path + "/"
				+ rb.getString("jasper_personal_individual"));
		int n = 1;
		// System.out.println("HV PERSONAL INICIANDO");
		super.init(config);
		cursor = new Cursor();
		reporteDao = new ReportePersonalDAO(cursor);

		if (!reporteDao
				.updateColaReportesEstadoCero("actualizar_reportes_en_cola")) {
			// System.out
			// .println("n****NO ACTUALIZn LOS REPORTES Q ESTABAN EN ESTADO CERO.. A ESTADO -1****!");
			return;
		}
		t = new Thread(new ReportePersonal(cursor, contextoTotal, path, path1,
				reportFile, reportFile_Ind, n++));
		t.start();
		return;
	}

	public void destroy() {
		try {
			t.stop();
			// System.out.println("HV PERSONAL FINALIZANDO");
		} catch (Exception ex) {
		}
	}

}
