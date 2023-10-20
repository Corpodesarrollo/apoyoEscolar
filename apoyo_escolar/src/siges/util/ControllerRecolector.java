package siges.util;

import java.sql.Time;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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

public class ControllerRecolector extends HttpServlet implements Runnable {
	private Thread t;
	utilDAO util;
	private static boolean running = true;
	private String path = null;

	public void init(ServletConfig config) throws ServletException {
		// System.out.println("RECOLECTOR INICIANDO");
		super.init(config);
		path = getServletContext().getRealPath("/");
		t = new Thread(this);
		t.setName("recolector");
		t.start();
	}

	public void destroy() {
		running = false;
	}

	public void run() {
		try {
			util = new utilDAO(path);
			long params[] = util.getParametrosRecolector(running);
			int dormir;
			long tempSleep = 20000;
			int cont = 0;
			while (true) {
				if (!running) {
					// System.out.println("RECOLECTOR FINALIZANDO");
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
					if (!running) {
						// System.out.println("RECOLECTOR FINALIZANDO");
						return;
					}
					Thread.sleep(60000);
				}
				params = util.getParametrosRecolector(running);
			}
		} catch (Exception ex) {
			System.out.println("Recolector " + ex);
		}
	}

	public void procesar(long[] params) {
		try {
			// System.out.println("Despertando Recolector: "+new
			// java.sql.Timestamp(System.currentTimeMillis()));
			java.text.SimpleDateFormat sdf;
			ResourceBundle rb = ResourceBundle.getBundle("common");
			long dia = params[1];
			long time = params[2];
			sdf = new java.text.SimpleDateFormat("H:m:s");
			sdf.setLenient(false);
			Time ss = new java.sql.Time(sdf.parse(time + ":00:00").getTime());
			Calendar cc2 = Calendar.getInstance();
			cc2.setTimeInMillis(ss.getTime());
			int horaArchivo = cc2.get(Calendar.HOUR_OF_DAY);
			int diaArchivo = (int) dia;
			Calendar cc = Calendar.getInstance();
			int horaActual = cc.get(Calendar.HOUR_OF_DAY);
			int diaActual = cc.get(Calendar.DAY_OF_WEEK) - 1;
			if (diaArchivo == 0 && horaActual == horaArchivo)
				procesarPeticion();
			if (diaActual == diaArchivo && horaActual == horaArchivo)
				procesarPeticion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void procesarPeticion() {
		long[][] params = util.getParametrosTipo();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (util.borrarTipo(params[i])) {
					// System.out.println("RECOLECTOR: Tipo["+i+"] Borro satisfactoriamente");
				}
				// else {
				// //
				// System.out.println("RECOLECTOR: Tipo["+i+"] Arrojo error:"+util.getMensaje());
				// }
			}
		}
		// else {
		// // System.out.println("RECOLECTOR: No hay parametros de recolector");
		// }
		// System.out.println("Terminando Recolector: "+new
		// java.sql.Timestamp(System.currentTimeMillis()));
	}

	/*
	 * public void procesarPeticion(String carpeta){ Connection cn=null;
	 * PreparedStatement pst=null; try{ cn=DataSourceManager.getConnection(1);
	 * ResourceBundle rb=ResourceBundle.getBundle("common");
	 * pst=cn.prepareStatement(rb.getString("recolector.consulta"));
	 * pst.executeUpdate();
	 * System.out.println("Recolector: Eliminando Archivos y Registros"); File
	 * f=new File(carpeta); if(f!=null && f.exists())
	 * FileUtils.cleanDirectory(f);
	 * System.out.println("Recolector: Eliminacinn Satisfactoria");
	 * }catch(IOException e){System.out.println("Error Recolector "+e);}
	 * catch(Exception e){System.out.println("Error Recolector "+e);} finally{
	 * try{OperacionesGenerales.closeStatement(pst);OperacionesGenerales.
	 * closeConnection(cn); System.out.println("Terminando Recolector: "+new
	 * java.sql.Timestamp(System.currentTimeMillis())); }catch(Exception e){} }
	 * }
	 */
}