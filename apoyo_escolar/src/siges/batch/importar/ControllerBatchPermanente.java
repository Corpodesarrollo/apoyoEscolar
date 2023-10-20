package siges.batch.importar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.validator.GenericValidator;

import siges.dao.Cursor;
import siges.dao.Ruta2;
import siges.exceptions.InternalErrorException;

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

public class ControllerBatchPermanente extends HttpServlet implements Runnable {
	private Thread t;
	private static int i = 0;
	private static boolean running = true;
	ResourceBundle rb;

	public void init(ServletConfig config) throws ServletException {
		// System.out.println("IMPORTAR PERMANENTE INICIANDO");
		super.init(config);
		t = new Thread(this);
		t.setName("ImportarPermanente");
		t.start();
	}

	public void destroy() {
		running = false;
	}

	public void run() {
		try {
			while (true) {
				if (!running) {
					// System.out.println("IMPORTAR PERMANENTE FINALIZANDO");
					return;
				}
				Thread.sleep(60000);
				procesarBatch();
			}
		} catch (Exception e) {
		}
	}

	public void procesarBatch() {
		// System.out.println("Batch Permanente: Satisfactorio");
		Cursor c = new Cursor();
		Collection list = new ArrayList();
		Object[] o = null;
		String[] archs;
		String[] fileNames;
		String[][] paths;
		int intMayor;
		String[] strMayor = null;
		String strCarpeta = null;
		try {
			rb = ResourceBundle.getBundle("batch");
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
				intMayor = 0;
				System.out.println("Permanente inicia:"
						+ new java.sql.Timestamp(System.currentTimeMillis()));
				for (int i = 0; i < fileNames.length; i++) {
					list = new ArrayList();
					pathTotal = Ruta2.get(getServletContext(), fileNames[i]);
					f = new File(pathTotal);
					if (f.exists() && f.isDirectory()) {
						archs = f.list();
						if (archs != null) {
							if (archs.length > i) {
								// System.out.println("Mayor:"+pathTotal);
								intMayor = archs.length;
								strMayor = archs;
								strCarpeta = pathTotal;
							}
						}
					}
				}
				if (intMayor > 0 && strMayor != null) {
					list = new ArrayList();
					for (int j = 0; j < strMayor.length; j++) {
						if (!strMayor[j].trim().endsWith(".zip")) {
							o = new Object[3];
							o[0] = strCarpeta;
							o[1] = strMayor[j];
							o[2] = strMayor[j].substring(0,
									strMayor[j].indexOf("_"));
							if (GenericValidator.isInt((String) o[2])) {
								list.add(o);
							}
						}
					}
					paths = getFiltroMatriz(list);
					Importar p = new Importar(getServletContext().getRealPath(
							"/"), files[i], paths, c);
					p.setName("P-" + files[i]);
					// p.start();
					p.run();
				}
				System.out.println("Permanente termina:");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Servlet Importar Permanente:" + e);
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