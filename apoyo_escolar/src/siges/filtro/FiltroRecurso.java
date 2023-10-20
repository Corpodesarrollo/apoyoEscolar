package siges.filtro;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ResourceBundle;
import java.io.BufferedInputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.filtro.dao.FiltroDAO;

/**
 * Nombre: Fil troRecurso<BR>
 * Descripcinn: Controlador de acceso a recursos estaticos provenientes de la
 * carpeta privada y exclusiva para poner contenido con acceso restringido a los
 * usuarios con permisos <BR>
 * Funciones de la pngina: Recibir cualquier peticion que se dirija a la carpeta
 * privada del sistema y validar su acceso <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public final class FiltroRecurso implements Filter {
	private FilterConfig filterConfig = null;

	private ResourceBundle rb = ResourceBundle.getBundle("mime");

	private ResourceBundle rb2 = ResourceBundle.getBundle("path");

	private ServletContext ctx;

	int direccionamiento = Integer.parseInt(rb2.getString("path.contexto"));

	String base = null;

	private FiltroDAO filtroDAO = new FiltroDAO(new Cursor());

	/*
	 * Inicializa el filtro
	 * 
	 * @param FilterConfig
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/*
	 * Realiza el proceso de filtrado para permitir o no que se accese al
	 * recurso solicitado @param ServletRequest request @param ServletResponse
	 * response @param FilterChain chain
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		ctx = session.getServletContext();
		String servicio = req.getServletPath();
		String tipo = req.getParameter("tipo");
		String cmd = req.getParameter("cmd");
		// System.out.println("cmd en filtro Recursos cmd " + cmd );
		if (tipo == null) {
			// System.out.println("EN ARCHIVO TIPO ES NULL");
			res.sendError(404);
			return;
		}
		String tipo_ = rb.getString(tipo);
		// System.out
		// .println("RECURSO SOLICITADO: " + servicio + "_tipo=" + tipo_);
		//
		if (session == null) {
			res.sendError(403);
			return;
		}
		// revisar que tipo de direccionamiento de carpetas se utiliza
		if (direccionamiento == 1) {// USA EL CONTEXTO INTERNO DE LA APLICACION
			chain.doFilter(request, response);
			return;
		}
		// TOCA TRAER EL ARCHIVO Y ABRIRLO
		if (base == null) {
			base = filtroDAO.getBase();
			base = base.replace('.', File.separatorChar);
		}
		// /System.out.println("VALOR DE LA BASE: " + base);
		servicio = base
				+ servicio.substring(1, servicio.length()).replace('/',
						File.separatorChar);
		// System.out.println("VALOR DE ARCHIVO: " + servicio);
		// ABRIR EL ARCHIVO Y ENVIARLO
		File f = new File(servicio);
		if (!f.exists()) {
			// System.out.println("ARCHIVO NO EXISTE:");
			res.sendError(404);
			return;
		}
		// poner el archivo
		String nombre = f.getName();
		res.setContentType(tipo_);
		res.setHeader("Content-Disposition", "attachment; filename=\"" + nombre
				+ "\"");
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		int BUFFER_SIZE = 100000;
		int count;
		try {
			byte[] data = new byte[BUFFER_SIZE];
			fi = new FileInputStream(f);
			origin = new BufferedInputStream(fi, BUFFER_SIZE);
			ouputStream = res.getOutputStream();
			while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
				ouputStream.write(data, 0, count);
			}
			ouputStream.flush();
			ouputStream.close();
			origin.close();
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();
				// System.out.println("en finally cmd " + cmd);
				if (cmd != null && cmd.equals("DeleteFile") && f != null
						&& f.exists()) {
					// System.out.println("borrar archvio f. " + f.getPath());
					f.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// chain.doFilter(request, response);
		return;
	}

	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (s != null) {
			RequestDispatcher rd = filterConfig.getServletContext()
					.getRequestDispatcher(s);
			if (a == 1)
				rd.include(request, response);
			else
				rd.forward(request, response);
		}
	}

	/**
	 * Coloca el filtro fuera de servicio
	 * 
	 */
	public void destroy() {
		this.filterConfig = null;
	}

}