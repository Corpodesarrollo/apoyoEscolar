package siges.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.util.beans.Area;
import siges.util.beans.Asignatura;
import siges.util.beans.BateriaDescriptor;
import siges.util.beans.BateriaLogro;
import siges.util.beans.Comportamiento;
import siges.util.beans.Preescolar;
import siges.util.beans.ResumenArea;
import siges.util.beans.ResumenAsignatura;
import siges.util.beans.ValidacionRotacion;

/**
 * Nombre: Init<br>
 * Descripcinn: Clase Inicial<br>
 * Funciones de la pngina: clase estatica que se carga al inicio de la
 * aplicacinn<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class Init extends HttpServlet {
	private static Acceso acceso;
	private static AccesoPaginas accesoPaginas;
	private static Logger logger;
	private static Recursos recursos;
	private static String debug;

	public void destroy() {
		// System.out.println("Desalojando acceso");
		// System.out.println("Desalojando logger");
		// System.out.println("Desalojando recursos");
		// DataSourceManager.closeDataSource();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// config.getServletContext().equals()
		debug = getServletConfig().getInitParameter("debug");
		Logger.setDebug(Integer.parseInt(debug));
	}

	static {
		Asignatura.Cargar();
		Area.Cargar();
		Preescolar.Cargar();
		BateriaLogro.Cargar();
		BateriaDescriptor.Cargar();
		ResumenAsignatura.Cargar();
		ResumenArea.Cargar();
		ValidacionRotacion.Cargar();
		Comportamiento.Cargar();
		Acceso.Cargar();
		AccesoPaginas.Cargar();
		Logger.Cargar();
		Logger.Cargar2();
		Recursos.Cargar();
	}
}