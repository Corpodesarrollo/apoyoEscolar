package siges.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
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

public class ControllerHiloIntegracioMatr extends HttpServlet implements
		Runnable {
	private Thread t;
	utilDAO util;
	private static boolean running = true;
	private String path = null;
	SimpleDateFormat sdf = new java.text.SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss.SSS");

	public void init(ServletConfig config) throws ServletException {
		// System.out.println("HILO_INTEGRACION INICIANDO");
		super.init(config);
		path = getServletContext().getRealPath("/");
		t = new Thread(this);
		t.setName("HILO_INTEGRACION");
		t.start();
	}

	public void destroy() {
		// System.out.println("HILO_INTEGRACION DESTORY");
		running = false;
	}

	public void run() {
		// System.out.println("HILO_INTEGRACION INICIANDO");
		try {
			util = new utilDAO(path);
			// System.out.println("Despertando HILO_INTEGRACION: "+new
			// java.sql.Timestamp(System.currentTimeMillis()));
			// System.out.println(sdf.format(new java.util.Date()) + " run ");

			String params[] = util.getParametrosHiloIntegracion(running);
			int dormir;
			long tempSleep = 20000;
			int cont = 0;
			while (true) {
				if (!running) {
					// System.out.println("HILO_INTEGRACION FINALIZANDO");
					return;
				}
				// System.out.println("HILO INTEGRACION params[0] " +
				// params[0]);
				if (params[0].equals("1")) {
					dormir = Integer.parseInt(params[3]);
					// dormir=1;
					Thread.sleep(tempSleep);
					cont++;
					if (cont >= (dormir * 3)) {
						cont = 0;
						procesar(params);
					}
				} else {
					if (!running) {
						// System.out.println("HILO_INTEGRACION FINALIZANDO");
						return;
					}
					Thread.sleep(60000);
				}

				// System.out.println(sdf.format(new java.util.Date()) +
				// " run despues");
				params = util.getParametrosHiloIntegracion(running);
			}
		} catch (Exception ex) {
			System.out.println("error HILO_INTEGRACION " + ex);
			ex.printStackTrace();
			System.out.println("Insertando mensaje ");
			try {
				util.setMensaje(ex.getMessage());
			} catch (Exception e) {
				System.out.println("Error insertando mensaje " + ex);
				ex.printStackTrace();

			}
		}
	}

	/**
	 * @function:
	 * @param params
	 * @throws Exception
	 */
	public void procesar(String[] params) throws Exception {
		try {
			// System.out.println("Despertando HILO_INTEGRACION: "+new
			// java.sql.Timestamp(System.currentTimeMillis()));
			java.text.SimpleDateFormat sdf;
			ResourceBundle rb = ResourceBundle.getBundle("common");
			long dia = Long.parseLong(params[1]);
			String time = params[2] + ":00:00";

			// System.out.println("time " + time);
			// System.out.println("params[2] " + params[2]);
			sdf = new java.text.SimpleDateFormat("H:m:s");
			sdf.setLenient(false);
			Time ss = new java.sql.Time(sdf.parse(time).getTime());
			Calendar cc2 = Calendar.getInstance();
			cc2.setTimeInMillis(ss.getTime());
			int horaArchivo = cc2.get(Calendar.HOUR_OF_DAY);
			int minutoArchivo = 00;
			String horaMinuArchivo = horaArchivo + ":00";
			int diaArchivo = (int) dia;

			Calendar cc = Calendar.getInstance();
			int horaActual = cc.get(Calendar.HOUR_OF_DAY);
			int minutoActual = cc.get(Calendar.MINUTE);
			String horaMinuActual = horaActual + ":00";
			int diaActual = cc.get(Calendar.DAY_OF_WEEK) - 1;

			// System.out.println("horaMinuActual " + horaMinuActual);
			// System.out.println("horaMinuArchivo " + horaMinuArchivo);
			// System.out.println("diaArchivo " + diaArchivo);
			// System.out.println("diaActual " + diaActual);

			if (diaArchivo == 0
					&& horaMinuActual.trim().equals(horaMinuArchivo.trim())) {
				procesarPeticion();
			}
			if (diaActual == diaArchivo
					&& horaMinuActual.trim().equals(horaMinuArchivo.trim())) {
				procesarPeticion();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);

		}
	}

	/**
	 * @function:
	 * @throws Exception
	 */
	public void procesarPeticion() throws Exception {
		try {
			util.callProcedimientoHiloIntegracion();
		} catch (Exception e) {
			// System.out.println("ERROR EN HILO INTEGRACION " + e.getMessage()
			// );
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}