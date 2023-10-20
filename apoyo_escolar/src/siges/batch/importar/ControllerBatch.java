package siges.batch.importar;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.FileUtils;

import siges.dao.Cursor;
import siges.dao.Ruta2;
import siges.exceptions.InternalErrorException;
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

public class ControllerBatch extends HttpServlet implements Runnable {
	private Thread t;
	private static int i = 0;
	ResourceBundle rb;
	private static boolean running = true;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("IMPORTAR BATCH INICIANDO");
		super.init(config);
		t = new Thread(this);
		t.setName("ImportarBatch");
		t.start();
	}

	public void destroy() {
		running = false;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			utilDAO util = new utilDAO();
			long params[] = util.getParametrosImpotar(running);
			int dormir;
			long tempSleep = 20000;
			int cont = 0;
			while (true) {
				if (!running) {
					System.out.println("IMPORTAR BATCH FINALIZANDO");
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
					Thread.sleep(60000);
				}
				params = util.getParametrosImpotar(running);
			}
		} catch (Exception ex) {
			System.out.println("Plantillas " + ex);
		}
	}

	public void procesar(long[] params) {
		// System.out.println("Despertando Batch Importar: "+new
		// java.sql.Timestamp(System.currentTimeMillis()));
		try {
			java.text.SimpleDateFormat sdf;
			rb = ResourceBundle.getBundle("batch");
			String path = rb.getString("importar.carpeta");
			long dia = params[1];
			long time = params[2];
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
				procesarBatch();
			if (diaActual == diaArchivo && horaActual == horaArchivo)
				procesarBatch();
		} catch (Exception ex) {
			System.out.println("Batch importar " + ex);
		}
	}

	public void procesarBatch() {
		// System.out.println("Batch importar: Satisfactorio");
		Cursor c = new Cursor();
		Collection list = new ArrayList();
		Object[] o = null;
		String[] archs;
		String[] fileNames;
		String[][] paths;
		try {
			String pathBase = rb.getString("importar.carpeta");
			String pathTotal = Ruta2.get(getServletContext(), pathBase);
			File f = new File(pathTotal);
			if (f.exists() && f.isDirectory()) {
				String[] files = f.list();
				if (files == null)
					return;
				for (int i = 0; i < files.length; i++) {
					// System.out.println(pathBase+"."+files[i]);
					list.add(pathBase + "." + files[i]);
				}
				fileNames = getFiltroArray(list);
				if (list.size() == 0)
					return;
				Importar[] p = new Importar[fileNames.length];
				// System.out.println("Batch Importar inicia:"
				// + new java.sql.Timestamp(System.currentTimeMillis()));
				for (int i = 0; i < fileNames.length; i++) {
					list = new ArrayList();
					pathTotal = Ruta2.get(getServletContext(), fileNames[i]);
					f = new File(pathTotal);
					if (f.exists() && f.isDirectory()) {
						archs = f.list();
						if (archs != null) {
							for (int j = 0; j < archs.length; j++) {
								f = new File(pathTotal + archs[j]);
								if (!f.exists() || f.isDirectory()
										|| archs[j].indexOf("_") == -1
										|| !archs[j].endsWith(".xls")) {
									FileUtils.forceDelete(f);
									System.out
											.println("Borro elemento extrano:"
													+ archs[j]);
									continue;
								}
								o = new Object[3];
								o[0] = pathTotal;
								o[1] = archs[j];
								o[2] = archs[j].substring(0,
										archs[j].indexOf("_"));
								list.add(o);
							}
						}
					}
					if (list.size() > 0) {
						paths = getFiltroMatriz(list);
						p[i] = new Importar(getServletContext()
								.getRealPath("/"), files[i], paths, c);
						p[i].setName("P-" + files[i]);
						p[i].run();
					}
				}
				System.out.println("Batch Importar termina:"
						+ new java.sql.Timestamp(System.currentTimeMillis()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Servlet Importar Batch:" + e);
		} finally {
			if (c != null)
				c.cerrar();
		}
	}

	public String[] getFiltroArray(Collection a) throws InternalErrorException {
		if (a != null && !a.isEmpty()) {
			String[] m = new String[a.size()];
			int i = 0;
			Iterator iterator = a.iterator();
			while (iterator.hasNext()) {
				m[i++] = (String) iterator.next();
			}
			return m;
		}
		return null;
	}

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado
	 * como una matriz<br>
	 * 
	 * @param Collection
	 *            lista
	 * @return String[][]
	 **/
	public String[][] getFiltroMatriz(Collection a)
			throws InternalErrorException {
		Object[] o;
		if (!a.isEmpty()) {
			int i = 0, j = -1;
			Iterator iterator = a.iterator();
			o = ((Object[]) iterator.next());
			String[][] m = new String[a.size()][o.length];
			iterator = a.iterator();
			while (iterator.hasNext()) {
				j++;
				o = ((Object[]) iterator.next());
				for (i = 0; i < o.length; i++) {
					m[j][i] = (String) o[i];
				}
			}
			return m;
		}
		return null;
	}

}