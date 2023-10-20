package siges.batch.plantilla;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.FileUtils;

import siges.batch.plantilla.DAO.PlantillaBatchDAO;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.plantilla.beans.FiltroPlantilla;
import siges.util.dao.utilDAO;

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

public class ControllerPlantilla extends HttpServlet implements Runnable {
	private Thread t;
	private static int i = 0;
	ResourceBundle rb;
	private static boolean running = true;
	private PlantillaBatchDAO dao;

	public void init(ServletConfig config) throws ServletException {
		// System.out.println("PLANTILLAS INICIANDO");
		super.init(config);
		t = new Thread(this);
		t.setName("Plantillas");
		t.start();
	}

	public void destroy() {
		running = false;
	}

	public void run() {
		try {
			utilDAO util = new utilDAO();
			long params[] = util.getParametrosPlantilla(running);
			/*
			 * Thread.sleep(20000); rb=ResourceBundle.getBundle("batch");
			 * procesarBatch(params);
			 */
			int dormir;
			long tempSleep = 20000;
			int cont = 0;
			while (true) {
				if (!running) {
					// System.out.println("PLANTILLAS FINALIZANDO");
					return;
				}
				if (params[0] == 1) {
					dormir = (int) params[3];
					Thread.sleep(tempSleep);
					cont++;
					if (cont >= (dormir * 3)) {
						cont = 0;
						procesar(params);
					}
				} else {
					// System.out.println("Plantillas no activo");
					Thread.sleep(60000);
				}
				params = util.getParametrosPlantilla(running);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Plantillas " + ex);
		}
	}

	public void procesar(long[] params) {
		// System.out.println("Despertando Batch Plantillas: "
		// + new java.sql.Timestamp(System.currentTimeMillis()));
		try {
			java.text.SimpleDateFormat sdf;
			rb = ResourceBundle.getBundle("batch");
			String path = rb.getString("plantilla.carpeta");
			long time = params[2];
			long dia = params[1];
			sdf = new java.text.SimpleDateFormat("H:m:s");
			sdf.setLenient(false);
			Time ss = new java.sql.Time(sdf.parse(time + ":00:00").getTime());
			// String carpeta=Ruta.get(getServletContext(),path);
			Calendar cc2 = Calendar.getInstance();
			cc2.setTimeInMillis(ss.getTime());
			int horaArchivo = cc2.get(Calendar.HOUR_OF_DAY);
			int diaArchivo = (int) (dia);
			Calendar cc = Calendar.getInstance();
			int horaActual = cc.get(Calendar.HOUR_OF_DAY);
			int diaActual = cc.get(Calendar.DAY_OF_WEEK) - 1;
			if (diaArchivo == 0 && horaActual == horaArchivo)
				procesarBatch(params);
			if (diaActual == diaArchivo && horaActual == horaArchivo)
				procesarBatch(params);
		} catch (Exception e) {
			System.out.println("Error procesar plantillas ");
			e.printStackTrace();
		}
	}

	public void procesarBatch(long[] params) {
		// System.out.println("Batch Plantillas: Satisfactorio");
		Cursor c = new Cursor();
		dao = new PlantillaBatchDAO(c);
		try {
			if (c.abrir(1)) {
				FiltroPlantilla d = new FiltroPlantilla();
				String[] inst = dao.getFiltroArray(rb
						.getString("dato.directorios"));
				String path = rb.getString("path.raiz");
				String pathAbsoluto = getServletContext().getRealPath("/");// path
																			// del
																			// origen
																			// de
																			// archivos
				String pathTotal;
				File f = new File(pathAbsoluto);
				if (!f.exists())
					FileUtils.forceMkdir(f);
				for (int j = 0; j < inst.length; j++) {
					pathTotal = path + "." + inst[j];
					pathTotal = Ruta.get(pathAbsoluto, pathTotal);
					f = new File(pathTotal);
					if (!f.exists()) {
						FileUtils.forceMkdir(f);
					}
				}
				dao.actualizarTodas();
				String[][] instituciones = dao.getFiltroMatriz(rb
						.getString("dato.instituciones"));
				if (instituciones != null) {
					Plantilla[] p = new Plantilla[instituciones.length];
					long insti = 0;
					System.out
							.println("INICIA PLANTILLA:"
									+ new java.sql.Timestamp(System
											.currentTimeMillis()));
					for (int j = 0; j < instituciones.length; j++) {
						String insti2 = (instituciones[j][0]);
						String per2 = (instituciones[j][2]);
						dao.actualizarInst(insti2, per2, -1, 0, "");
						// System.out.println("22222 ="+insti2);
						d.setInstitucion(instituciones[j][0]);
						d.setInstitucion_(instituciones[j][1]);
						d.setPeriodo(instituciones[j][2]);
						d.setPeriodo_(instituciones[j][2]);
						d.setPeriodoAbrev(instituciones[j][2]);
						p[j] = new Plantilla(c, d, getServletContext()
								.getRealPath("/"), params[4]);
						p[j].setName("P-" + instituciones[j][0] + "-"
								+ instituciones[j][2]);
						p[j].run();
						// termino inst bien
						dao.finalizarInst(insti2, per2, 0, 1, "");
					}
					// System.out
					// .println("TERMINA PLANTILLA:"
					// + new java.sql.Timestamp(System
					// .currentTimeMillis()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Procesar Batch:" + e);
		} finally {
			if (c != null)
				c.cerrar();
		}
	}

}
