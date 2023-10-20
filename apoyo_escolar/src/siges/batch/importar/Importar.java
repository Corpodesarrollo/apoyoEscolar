package siges.batch.importar;

import java.io.File;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.dao.Util;
import siges.importar.dao.ImportarDAO;
import siges.io.Zip;
import siges.util.Properties;

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

public class Importar {
	private String[][] archivos;
	private String realPath;
	private String usuario;
	private String name;
	private Cursor c;
	private static boolean ocupado = false;
	private ResourceBundle rb, rb2;
	private Collection list;
	private Object[] o;

	public Importar(String path, String usu, String[][] archs, Cursor cursor) {
		archivos = archs;
		realPath = path;
		c = cursor;
		usuario = usu;
		rb = ResourceBundle.getBundle("batch");
		rb2 = ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		list = new ArrayList();
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name != null ? name : "";
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void run() {
		// System.out.println(this.getName()+": Entra");
		try {
			// while(ocupado){
			// System.out.println(this.getName()+": Duerme");
			// sleep(1000);
			// }
			ocupado = true;
			procesar();
			// System.out.println(this.getName() + ": Sale");
		} catch (Exception e) {
			System.out.println(this.getName() + ": Error Importar Batch:"
					+ e.toString());
		} finally {
			ocupado = false;
		}
	}

	public void procesar() {
		int posicion;
		File f = null;
		Util u = new Util(c);
		ImportarDAO importarDAO = new ImportarDAO(c);
		Excel excel = new Excel(importarDAO);
		String pathPlantilla = null;
		String pathDownload = null;
		String relativo = null;
		String path = null;
		String nombre[];
		int size = 100000;
		int tipo;
		boolean band = true;
		try {
			// System.out.println("Matriz "+archivos.length);
			for (int i = 0; i < archivos.length; i++) {
				band = true;
				excel = new Excel(importarDAO);
				if (!GenericValidator.isInt(archivos[i][2]))
					continue;
				tipo = Integer.parseInt(archivos[i][2]);
				String params[] = { archivos[i][1].split("_")[1], "", "" };
				nombre = new String[2];
				nombre[0] = archivos[i][0];
				nombre[1] = archivos[i][1];

				switch (tipo) {
				case Properties.PLANTILLALOGROASIG:
					// System.out.println("Asignatura " + archivos[i][1]);
					path = rb2.getString("asignatura.PathAsignatura") + "."
							+ usuario;// ruta de carpetas
					relativo = Ruta.getRelativo(realPath, path);
					pathPlantilla = Ruta2.get(realPath,
							rb2.getString("asignatura.PathPlantilla"))
							+ rb2.getString("asignatura.Plantilla");// archivo
																	// de
																	// plantilla
					pathDownload = Ruta.get(realPath, path);// path del nuevo
															// archivo
					if (excel.validarFormatoAsignatura(tipo, nombre,
							pathPlantilla, pathDownload, params)) {
						if (excel.importarAsignatura(tipo, nombre,
								pathPlantilla, pathDownload, usuario)) {
							band = false;
						}
					}
					break;
				case Properties.PLANTILLAAREADESC:
					// System.out.println("Area " + archivos[i][1]);
					path = rb2.getString("area.PathArea") + "." + usuario;// ruta
																			// de
																			// carpetas
					relativo = Ruta.getRelativo(realPath, path);
					pathPlantilla = Ruta2.get(realPath,
							rb2.getString("area.PathPlantilla"))
							+ rb2.getString("area.Plantilla");// archivo de
																// plantilla
					pathDownload = Ruta.get(realPath, path);// path del nuevo
															// archivo
					if (excel.validarFormatoArea(tipo, nombre, pathPlantilla,
							pathDownload, params)) {
						if (excel.importarArea(tipo, nombre, pathPlantilla,
								pathDownload, usuario)) {
							band = false;
						}
					}
					break;
				case Properties.PLANTILLAPREE:
					// System.out.println("Preescolar " + archivos[i][1]);
					path = rb2.getString("preescolar.PathPreescolar") + "."
							+ usuario;// ruta de carpetas
					relativo = Ruta.getRelativo(realPath, path);
					pathPlantilla = Ruta2.get(realPath,
							rb2.getString("preescolar.PathPlantilla"))
							+ rb2.getString("preescolar.Plantilla");// archivo
																	// de
																	// plantilla
					pathDownload = Ruta.get(realPath, path);// path del nuevo
															// archivo
					if (excel.validarFormatoPreescolar(tipo, nombre,
							pathPlantilla, pathDownload, params)) {
						if (excel.importarPreescolar(tipo, nombre,
								pathPlantilla, pathDownload, usuario)) {
							band = false;
						}
					}
					break;
				case Properties.PLANTILLABATLOGRO:
					// System.out.println("Bat Logro " + archivos[i][1]);
					path = rb2.getString("plantillaLogro.PathPlantillaLogro")
							+ "." + usuario;// ruta de carpetas
					relativo = Ruta.getRelativo(realPath, path);
					pathPlantilla = Ruta2.get(realPath,
							rb2.getString("plantillaLogro.PathPlantilla"))
							+ rb2.getString("plantillaLogro.Plantilla");// archivo
																		// de
																		// plantilla
					pathDownload = Ruta.get(realPath, path);// path del nuevo
															// archivo
					if (excel.validarFormatoBateriaLogro(tipo, nombre,
							pathPlantilla, pathDownload, params)) {
						if (excel.importarBateriaLogro(tipo, nombre,
								pathPlantilla, pathDownload, usuario)) {
							band = false;
						}
					}
					break;
				case Properties.PLANTILLABATDESCRIPTOR:
					// System.out.println("Bat Desc " + archivos[i][1]);
					path = rb2
							.getString("plantillaDescriptor.PathPlantillaDescriptor")
							+ "." + usuario;// ruta de carpetas
					relativo = Ruta.getRelativo(realPath, path);
					pathPlantilla = Ruta2.get(realPath,
							rb2.getString("plantillaDescriptor.PathPlantilla"))
							+ rb2.getString("plantillaDescriptor.Plantilla");// archivo
																				// de
																				// plantilla
					pathDownload = Ruta.get(realPath, path);// path del nuevo
															// archivo
					if (excel.validarFormatoBateriaDescriptor(tipo, nombre,
							pathPlantilla, pathDownload, params)) {
						if (excel.importarBateriaDescriptor(tipo, nombre,
								pathPlantilla, pathDownload, usuario)) {
							band = false;
						}
					}
					break;
				}
				if (!band) {
					// System.out.println("Borrar: " + nombre[0] + nombre[1]);
					FileUtils.forceDelete(new File(nombre[0] + nombre[1]));
				}
			}
			f = new File(archivos[0][0] + archivos[0][1]);
			String parent = f.getParent();
			f = new File(parent);
			if (f.isDirectory()) {
				String[] files = f.list();
				if (files != null && files.length > 0) {
					list = new ArrayList();
					for (int t = 0; t < files.length; t++) {
						list.add(parent + File.separator + files[t]);
					}
					String nom = getNombre();
					Zip zip = new Zip();
					if (zip.ponerZip(pathDownload, nom, size, list)) {
						ponerReporte(usuario, relativo + nom, "zip", nom, 6);
						// System.out.println("Generar Zip");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// throw new NullPointerException();
			return;
		} finally {
			u.cerrar();
			c.cerrar();
		}
	}

	public String getNombre() {
		String nom = "";
		Timestamp f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		nom = "Archivos_NO_Procesados_De_IMPORTACION_BATCH("
				+ f2.toString().replace(':', '-').replace('.', '-') + ").zip";
		return nom;
	}

	/**
	 * Funcion: Registra en la tabla de reportes los datos pasados como
	 * parametro<BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 **/
	public void ponerReporte(String us, String rec, String tipo, String nombre,
			int modulo) {
		ResourceBundle rb3 = ResourceBundle.getBundle("operaciones");
		Collection list = new ArrayList();
		Object[] o = new Object[2];
		Integer cadena = new Integer(Types.VARCHAR);
		Integer entero = new Integer(Types.INTEGER);
		o[0] = cadena;
		o[1] = us;
		list.add(o);// usuario
		o = new Object[2];
		o[0] = cadena;
		o[1] = rec;
		list.add(o);// rec
		o = new Object[2];
		o[0] = cadena;
		o[1] = tipo;
		list.add(o);// tipo
		o = new Object[2];
		o[0] = cadena;
		o[1] = nombre;
		list.add(o);// nombre
		o = new Object[2];
		o[0] = entero;
		o[1] = "" + modulo;
		list.add(o);// modulo
		c.registrar(rb3.getString("ReporteInsertar"), list);
	}
}
