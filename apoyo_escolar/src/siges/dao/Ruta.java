package siges.dao;

import javax.servlet.ServletContext;

import siges.filtro.dao.FiltroDAO;

/**
 * Nombre: Ruta<br>
 * Descripcinn: Convetir rutas relativas en absolutas<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public final class Ruta {
	public final static char sep = java.io.File.separatorChar;
	// private static String base=null;

	/**
	 * constructor de la clase
	 **/
	private Ruta() {
	}

	/**
	 * Retorna la direccinn absoluta de disco hasta la raiz de la aplicacion
	 * 
	 * @param HttpServletRequest
	 *            req
	 * @return String
	 */
	public static String get(ServletContext sc) {
		java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("path");
		int val = Integer.parseInt(rb.getString("path.contexto"));
		String ruta = null;
		if (val == 1) {
			ruta = sc.getRealPath("/");
		} else {
			ruta = rb.getString("path.base").replace('.', sep);
			// ruta = base;
		}
		String dir = rb.getString("path.subirDefault");
		if (ruta.substring(ruta.length() - 1).equals("/")) {
			ruta += dir.replace('.', '/') + "/";
		} else {
			ruta += dir.replace('.', '\\') + "\\";
		}
		return ruta;
	}

	/**
	 * Retorna la direccinn absoluta de disco hasta la raiz de la aplicacion
	 * 
	 * @param HttpServletRequest
	 *            req
	 * @return String
	 */
	public static String get(String ruta) {
		String dir = java.util.ResourceBundle.getBundle("path").getString("path.subirDefault");
		if (ruta.substring(ruta.length() - 1).equals("/")) {
			ruta += dir.replace('.', '/') + "/";
		} else {
			ruta += dir.replace('.', '\\') + "\\";
		}
		return ruta;
	}

	/**
	 * Retorna la direccinn absoluta de disco hasta la raiz de la aplicacion,
	 * incluyendo la direccion enviada en 'dir'
	 * 
	 * @param HttpServletRequest
	 *            req
	 * @param String
	 *            dir
	 * @return String
	 */
	public static String get(ServletContext sc, String dir) {
		java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("path");
		int val = Integer.parseInt(rb.getString("path.contexto"));
		String ruta = null;
		if (val == 1) {
			ruta = sc.getRealPath("/");
		} else {
			ruta = rb.getString("path.base").replace('.', sep);
			// ruta = base;
		}
		String ultimoCarater = ruta.substring(ruta.length() - 1);
		if (!ultimoCarater.equals("\\") && !ultimoCarater.equals("/")) {
			if (ruta.contains("/")) {
				ruta = ruta + "/";
			} else {
				ruta = ruta + "\\";
			}
		}
		if (ruta.substring(ruta.length() - 1).equals("/")) {
			ruta += dir.replace('.', '/') + "/";
		} else {
			ruta += dir.replace('.', '\\') + "\\";
		}
		return ruta;
	}

	/**
	 * Retorna la direccinn absoluta de disco hasta la raiz de la aplicacion,
	 * incluyendo la direccion enviada en 'dir'
	 * 
	 * @param HttpServletRequest
	 *            req
	 * @param String
	 *            dir
	 * @return String
	 */
	public static String get(String ruta, String dir) {
		java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("path");
		int val = Integer.parseInt(rb.getString("path.contexto"));
		if (val != 1) {
			ruta = rb.getString("path.base").replace('.', sep);
			// ruta = base;
		}

		if (ruta.substring(ruta.length() - 1).equals("/")) {
			ruta += dir.replace('.', '/') + "/";
		} else {
			ruta += dir.replace('.', '\\') + "\\";
		}
		return ruta;
	}

	/**
	 * Retorna la direccinn absoluta de disco hasta la raiz de la aplicacion,
	 * incluyendo la direccion enviada en 'dir'
	 * 
	 * @param HttpServletRequest
	 *            req
	 * @param String
	 *            dir
	 * @return String
	 */
	public static String getArchivo(ServletContext sc, String dir) {
		java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("path");
		int val = Integer.parseInt(rb.getString("path.contexto"));
		String ruta = null;
		if (val == 1) {
			ruta = sc.getRealPath("/");
		} else {
			ruta = rb.getString("path.base").replace('.', sep);
			// ruta = base;
		}
		String path = dir.substring(0, dir.lastIndexOf("."));
		String ext = dir.substring(dir.lastIndexOf("."), dir.length());
		if (ruta.substring(ruta.length() - 1).equals("/")) {
			ruta += path.replace('.', '/');
		} else {
			path = path.replace('/', '\\');
			ruta += path.replace('.', '\\');
		}
		return ruta + ext;
	}

	public static String getArchivo(String ruta, String dir) {
		java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("path");
		String path = dir.substring(0, dir.lastIndexOf("."));
		String ext = dir.substring(dir.lastIndexOf("."), dir.length());
		if (ruta.substring(ruta.length() - 1).equals("/")) {
			ruta += path.replace('.', '/');
		} else {
			path = path.replace('/', '\\');
			ruta += path.replace('.', '\\');
		}
		return ruta + ext;
	}

	/**
	 * Funcinn: Obtener ruta relativa<br>
	 * 
	 * @param ServletContext
	 *            sc
	 * @param String
	 *            dir
	 * @return String
	 **/
	public static String getRelativo(ServletContext sc, String dir) {
		String algo = "";
		algo += dir.replace('.', '/') + "/";
		return algo;
	}

	public static String getRelativo(String ruta, String dir) {
		String algo = "";
		algo += dir.replace('.', '/') + "/";
		return algo;
	}

	// private static void init(){
	// base=(new FiltroDAO(new Cursor()).getBase()).replace('.',sep);
	// }
	//
	// static{
	// init();
	// }
}
