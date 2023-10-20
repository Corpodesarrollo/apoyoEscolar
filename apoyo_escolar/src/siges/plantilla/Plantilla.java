package siges.plantilla;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.dao.Cursor;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Properties;

/**
 * siges.plantilla<br>
 * Funcinn: HIlo que llam las clases de generacion masiva de las plantillas
 * solicitadas por lote y llama a la clase que genera los archivos fisicos <br>
 */
public class Plantilla extends HttpServlet implements Runnable {
	private Thread t;
	private static boolean running = true;
	private ResourceBundle rb;
	private PlantillaDAO plantillaDAO;
	private Cursor cursor;
	private String path;

	public void init(ServletConfig config) throws ServletException {
		// System.out.println("PLANTILLASLINEA INICIANDO");
		super.init(config);
		rb = ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		path = getServletContext().getRealPath("/");
		t = new Thread(this);
		t.setName("PlantillasLinea");
		t.start();
	}

	public void destroy() {
		try {
			running = false;
		} catch (Exception ex) {
		}
	}

	public void run() {
		try {
			int dormir = Integer.parseInt(rb.getString("plantillaLinea.sleep"));
			long tempSleep = Long.parseLong(rb
					.getString("plantillaLinea.tempSleep"));
			int cont = 0;
			while (true) {
				if (!running) {
					// System.out.println("PLANTILLASLINEA FINALIZANDO");
					return;
				}
				Thread.sleep(10000);
				procesar();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Hilo PLANTILLASLINEA : " + ex);
		}
	}

	public void procesar() {
		cursor = new Cursor();
		plantillaDAO = new PlantillaDAO(cursor);
		FiltroPlantilla filtroPlantilla[];
		filtroPlantilla = plantillaDAO.getPila();
		plantillaDAO.actualizarTodos();
		if (filtroPlantilla == null || filtroPlantilla[0] == null) {
			// System.out.println("//HILO DE PLANTILLAS LINEA sin solicitudes");
			return;
		}
		// System.out.println("//ENTRA EL HILO DE PLANTILLAS LINEA"+filtroPlantilla.length);
		int tipo = 0;
		for (int i = 0; i < filtroPlantilla.length; i++) {
			// procesar fila a fila
			PlantillaUtil pl = null;
			try {
				if (filtroPlantilla[i] != null) {
					plantillaDAO.actualizarPlantillas(
							filtroPlantilla[i].getUsuarioPlantilla(),
							filtroPlantilla[i].getNombrePlantilla(), 0, "");
					tipo = Integer.parseInt(filtroPlantilla[i]
							.getTipoPlantilla());
					// System.out.println("tipo="+tipo+"//"+filtroPlantilla[i].getNombrePlantilla());
					pl = new PlantillaUtil(plantillaDAO, filtroPlantilla[i],
							path, tipo);
					// System.out.println("//plantillas tipo*****="+tipo+"//"+filtroPlantilla[i].getNombrePlantilla());
					switch (tipo) {
					case Properties.PLANTILLALOGROASIG:// asignatura
						pl.asignarListaLogroAsigZip();
						break;
					case Properties.PLANTILLAAREADESC:// area
						pl.asignarListaDescAreaZip();
						break;
					case Properties.PLANTILLAPREE:// preescolar
						pl.asignarListaPreescolarZip();
						break;
					case Properties.PLANTILLARECUPERACION:// preescolar
						pl.asignarListaRecuperacionZip();
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				plantillaDAO.actualizarPlantillas(
						filtroPlantilla[i].getUsuarioPlantilla(),
						filtroPlantilla[i].getNombrePlantilla(), 2,
						e.getMessage());
			}
		}
	}
}
