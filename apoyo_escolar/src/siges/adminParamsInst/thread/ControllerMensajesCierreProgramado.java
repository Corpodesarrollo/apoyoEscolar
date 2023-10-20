package siges.adminParamsInst.thread;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.dao.Cursor;

/**
 * Clase servlet encargada de realizar el llamodo y ejecucinn del hilo
 * "HiloTareasMsjPerPrg"
 * 
 * @see siges.adminParamsInst.thread.HiloTareasMsjPerPrg
 * @date 28/09/2010 <BR>
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerMensajesCierreProgramado extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static int i=0;
	// private ResourceBundle rb;
	private Cursor cursor;
	private Thread t;
	private AdminParametroInstDAO adminParametroInstDAO;
	private static Logger logger = Logger
			.getLogger(ControllerMensajesCierreProgramado.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// //System.out.println("[APOYO ESCOLAR] >>>>> PER PROG FECHAS");
		logger.fine("[APOYO ESCOLAR] > PER PROG FECHAS");
		// ServletContext context=config.getServletContext();

		logger.fine(new Date() + " PER PROG FECHAS: APOYO ESCOLAR INICIANDO...");
		super.init(config);
		cursor = new Cursor();
		try {
			logger.fine("inicia hilo dao");
			adminParametroInstDAO = new AdminParametroInstDAO(cursor);
			logger.fine("inicia hilo dao...");
			if (!adminParametroInstDAO.updateEstado()) {
				// //System.out.println("[APOYO ESCOLAR ]" + new
				// Date()+" PER PROG FECHAS: NO ACTUALIZn LOS REGISTRO Q ESTABAN EN ESTADO CERO.");
				return;
			}
			logger.fine("inicia hilo... ");
			t = new Thread(new HiloTareasMsjPerPrg(adminParametroInstDAO));
			t.start();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return;
	}

}
