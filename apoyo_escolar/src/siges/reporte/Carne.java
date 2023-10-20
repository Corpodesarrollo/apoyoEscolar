package siges.reporte;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.reporte.beans.FiltroBeanCarne;

/**
 * Nombre: Boletin<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: CARNE_ESTUDIANTE <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class Carne extends Thread {
	private static boolean ocupado = false;

	private Cursor cursor;// objeto que maneja las sentencias sql

	// private Zip zip;

	// private Thread t;

	// private Login login;

	private String mensaje;

	private boolean err;

	private ResourceBundle r, rb3;

	private Collection list;

	private Object[] o;

	// private java.sql.Timestamp f2;

	private FiltroBeanCarne filtro;

	// private String buscarcodigo;

	private final String modulo = "20";

	private String s;

	private String s1;

	// private String archivo, archivopre, archivozip;

	// private String ant;

	// private String er;

	// private String sig;

	// private String home;

	private byte[] bytes;

	// private byte[] bytes1;

	private Map parameters;

	private File reportFile_;

	private String path, path_img, path_escudos, path_logos, path_fondo;

	private String context;

	// private String codigoestu;

	// private PreparedStatement pst;

	// private CallableStatement cstmt;

	private ResultSet rs;

	// private ControllerCarneFiltroSave filtroSave;

	private String vigencia;

	private File escudo;

	private static String TIPO_FORMATO_1 = "1";
	private static String TIPO_FORMATO_2 = "2";
	private static String TIPO_FORMATO_3 = "3";
	private static String TIPO_FORMATO_11 = "11";
	private static String TIPO_FORMATO_12 = "12";
	private static String TIPO_FORMATO_13 = "13";

	/* Constructor de la clase */
	// t=new Thread(new
	// Carne(cursor,contextoTotal,path,path1,path2,reportFile,n++));
	public Carne(Cursor c, String cont, String p, String p1, String p2,
			String p3, String p4, File reportF, int nn) {
		super("hilo_carne_" + nn);
		cursor = c;
		path = p;
		path_img = p1;
		path_escudos = p2;
		path_logos = p3;
		path_fondo = p4;
		// reportFile = reportF;
		context = cont;
		rb3 = ResourceBundle.getBundle("siges.reporte.bundle.carnes");
		s = rb3.getString("valor_s");
		s1 = rb3.getString("valor_s1");
		err = false;
		mensaje = null;
		// codigoestu = null;
		bytes = null;
	}

	public void run() {
		// Object[] o = new Object[2];
		// int posicion = 1;
		// int uno = 0;
		String[][] array = null;
		int dormir = 0;
		// String cola = null;
		String puest = "-999";
		// filtroSave = new ControllerCarneFiltroSave();
		try {
			Thread.sleep(20000);
			dormir = Integer.parseInt(rb3.getString("reportes.Dormir"));
			while (ocupado) {
				// System.out.println(getName() + ":Espera Thread");
				sleep(dormir);
			}
			ocupado = true;
			// System.out.println(getName() + ":Entra Thread");

			while (true) {
				if (!activo()) {// es porque en la tabla vale 0
					// System.out.println("**ESTA APAGADO**");
					Thread.sleep(dormir);
					continue;
				}
				// System.out.println(" reporte_Generar()");

				array = reporte_Generar();
				if (array != null) {
					// System.out.println(getName()
					// + " [CARNES APOYO]  SI HAY CARNES POR GENERAR "
					// + array.length);
					for (int i = 0; i < array.length; i++) {
						filtro = new FiltroBeanCarne();
						// llenar el bean
						filtro.setInstitucion(array[i][0]);
						filtro.setSede(array[i][1]);
						filtro.setJornada(array[i][2]);
						filtro.setMetodologia(array[i][3]);
						filtro.setGrado(array[i][4]);
						filtro.setGrupo(array[i][5]);
						filtro.setId(array[i][6]);
						filtro.setUsuarioid(array[i][8]);
						filtro.setOrden(array[i][9]);
						filtro.setNombrezip(array[i][10]);
						filtro.setNombrepdf(array[i][11]);
						filtro.setInstituciondane(array[i][12]);
						filtro.setFormatoCarne(array[i][13]);

						/**/
						// System.out.println("filtro.getFormatoCarne() "
						// + filtro.getFormatoCarne());

						if ((TIPO_FORMATO_2).equals(filtro.getFormatoCarne())) {
							reportFile_ = new File(path + File.separator
									+ rb3.getString("jasper_carneVariosCarta"));
						} else if ((TIPO_FORMATO_3).equals(filtro
								.getFormatoCarne())) {
							reportFile_ = new File(path + File.separator
									+ rb3.getString("jasper_carneVariosOficio"));
						} else if ((TIPO_FORMATO_1).equals(filtro
								.getFormatoCarne())) {
							reportFile_ = new File(path + File.separator
									+ rb3.getString("jasper_carnes"));
						} else if ((TIPO_FORMATO_12).equals(filtro
								.getFormatoCarne())) {
							reportFile_ = new File(path + File.separator
									+ rb3.getString("carneCarta_admin_docen"));
						} else if ((TIPO_FORMATO_13).equals(filtro
								.getFormatoCarne())) {
							reportFile_ = new File(path + File.separator
									+ rb3.getString("carneOficio_admin_docen"));
						} else if ((TIPO_FORMATO_11).equals(filtro
								.getFormatoCarne())) {
							reportFile_ = new File(path + File.separator
									+ rb3.getString("carne_admin_docen"));
						}
						parameters = new HashMap();

						parameters.put("usuario", ((String) filtro
								.getUsuarioid().trim()));
						parameters.put("institucion",
								(Long.valueOf(filtro.getInstitucion())));
						parameters
								.put("sede", (Long.valueOf(filtro.getSede())));
						parameters.put("jornada",
								(Long.valueOf(filtro.getJornada())));
						parameters.put("metod",
								(Long.valueOf(filtro.getMetodologia().trim())));
						parameters.put("grado",
								(Long.valueOf(filtro.getGrado().trim())));
						parameters.put("grupo",
								(Long.valueOf(filtro.getGrupo().trim())));

						/**
						 * =================================================
						 * Parametros carnes de Estudiantes
						 * =================================================
						 * */

						if ((TIPO_FORMATO_1).equals(filtro.getFormatoCarne())
								|| (TIPO_FORMATO_2).equals(filtro
										.getFormatoCarne())
								|| (TIPO_FORMATO_3).equals(filtro
										.getFormatoCarne())) {

							// RUTAS DE FOTOS ESTUDIANTES
							String rutaFotos = getFoto(context);
							parameters.put("RUTA_FOTOS", rutaFotos);

							// RUTA ESCUDO COLEGIO
							escudo = new File(path_escudos + File.separator
									+ "e" + filtro.getInstituciondane().trim()
									+ ".gif");
							if (escudo.exists()) {
								parameters.put("LogoInstitucion", path_logos
										+ File.separator + "e"
										+ filtro.getInstituciondane().trim()
										+ ".gif");
							} else {
								parameters.put(
										"LogoInstitucion",
										path_escudos + File.separator
												+ rb3.getString("imagen"));
							}

							// FONDO DE CARNES Y LOGO DE LA SED
							String imgFondo = path_fondo + File.separator
									+ rb3.getString("imagenFondo");
							if (isValidaImagen(new File(imgFondo))) {
								parameters.put("Fondo", imgFondo);
							}
							String imgLogosSED = path_logos + File.separator
									+ rb3.getString("imagenLogosSED");
							if (isValidaImagen(new File(imgLogosSED))) {
								parameters.put("LogosSED", imgLogosSED);
							}

						}

						/**
						 * =================================================
						 * Parametros carnes de Admin y docentes
						 * =================================================
						 * */

						if ((TIPO_FORMATO_11).equals(filtro.getFormatoCarne())
								|| (TIPO_FORMATO_12).equals(filtro
										.getFormatoCarne())
								|| (TIPO_FORMATO_13).equals(filtro
										.getFormatoCarne())) {

							// FONDO Y LOGO SED
							String rutaLogosSED_1 = path_logos + File.separator
									+ rb3.getString("imagenLogosSED_1");

							if (isValidaImagen(new File(rutaLogosSED_1))) {
								parameters
										.put("LogosSED_1",
												path_logos
														+ File.separator
														+ rb3.getString("imagenLogosSED_1"));
							}

							// LOGO SED
							String rutaFondo_1 = path_fondo + File.separator
									+ rb3.getString("imagenFondo_1");
							if (isValidaImagen(new File(rutaFondo_1))) {
								parameters.put("Fondo_1", rutaFondo_1);
							}
						}

						if (!proceso_carne(filtro, parameters)) {
							updateDatosCarne("2", filtro.getNombrezip(), "0",
									filtro.getUsuarioid());
							updateReporte(modulo, filtro.getNombrezip(),
									filtro.getUsuarioid(), "2");
							continue;
						}
						if (!updatePuestoCarne(puest, filtro.getNombrezip(),
								filtro.getUsuarioid(), "update_puesto_carne_2")) {
							continue;
						}
						if (!update_cola_carnes()) {
							continue;
						}
					}
				} else {
					Thread.sleep(dormir);
					vaciarTablas(filtro);
				}
			}

		} catch (InterruptedException ex) {
			System.out.println(getName() + ":Muere hilo");
			ex.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletin",
					"Ocurrio Interrupted excepcinn en el Hilo:_" + ex);
			updateDatosCarne("2", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
		} catch (Exception ex) {
			System.out.println(getName() + ":Muere hilo");
			ex.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletin",
					"Ocurrio excepcinn en el Hilo:_" + ex);
			updateDatosCarne("2", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
		} finally {
			ocupado = false;
		}
	}

	public String[][] reporte_Generar() {
		Connection con = null;
		PreparedStatement pst = null;
		String[][] array1 = null;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("reporte_a_generar"));
			pst.clearParameters();
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int i = 0;
			while (rs.next()) {
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}

			if (!list.isEmpty()) {
				i = 0;
				int j = -1;
				Iterator iterator = list.iterator();
				o = ((Object[]) iterator.next());
				array1 = new String[list.size()][o.length];
				iterator = list.iterator();
				while (iterator.hasNext()) {
					j++;
					o = ((Object[]) iterator.next());
					for (i = 0; i < o.length; i++) {
						array1[j][i] = (String) o[i];
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletin",
					"Ocurrio excepcinn en el Hilo: " + e);
			updateDatosCarne("2", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return array1;
	}

	public boolean proceso_carne(FiltroBeanCarne filtro, Map parameterscopy) {
		parameterscopy = parameters;
		// Object[] o;
		list = new ArrayList();
		// String nombre;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Zip zip = new Zip();
		Collection list = new ArrayList();
		String archivosalida = null;
		o = new Object[2];
		int zise;
		int posicion = 1;
		// String periodoabrev1 = null;
		bytes = null;
		// bytes1 = null;
		// String cont = null;

		try {
			limpiarTablas(filtro);
			// System.out.println("nSe limpiaron las tablas antes de ser llenadas!");
			updateDatosCarne("0", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"0");
			ponerReporteMensajeListo(modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarGenerando");
			updateDatosCarneFechaGen(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"0", filtro.getNombrezip(), filtro.getUsuarioid());

			if (!proceso(filtro)) {
				// System.out
				// .println(getName()
				// + "NO TERMINO CALLABLESTATEMENT OCURRIO ERROR MIRAR TRACE");
				return false;
			}
			/* Consulta para ejecutar el/los jasper */
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("queryjasper"));
			posicion = 1;
			pst.clearParameters();
			// System.out
			// .println("------------------------------------  filtro.getUsuarioid()  "
			// + filtro.getUsuarioid());
			pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			pst.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));

			rs = pst.executeQuery();
			if (rs.next()) {
				// System.out
				// .println("si hay registros -----------------------------------------");
				rs.close();
				pst.close();
				// System.out.println("SE DEBE EJECUTAR EL JASPER DE CARNES");
				if ((reportFile_.getPath() != null) && (parameters != null)
						&& (!parameters.values().equals("0")) && (con != null)) {
					archivosalida = Ruta.get(context,
							rb3.getString("carne.PathCarne"));
					// System.out.println(getName()
					// + "Se mando ejecutar el jasper de CARNES");
					// System.out.println("reportFile_.getPath() "
					// + reportFile_.getPath());
					// parameters.put(
					// "Fondo",
					// path1 + File.separator
					// + rb3.getString("imagenFondo"));
					// parameters.put(
					// "LogosSED",
					// path1 + File.separator
					// + rb3.getString("imagenLogosSED"));
					// System.out.println("parameters.put( " +
					// parameters.get("LogosSED") );
					// System.out.println("usuario" + parameters.get("usuario")
					// );
					bytes = JasperRunManager.runReportToPdf(
							reportFile_.getPath(), parameters, con);
					// System.out.println(getName()
					// + "Termino de ejecutar el jasper de CARNES");
					if (!ponerArchivo(modulo, path, bytes,
							filtro.getNombrepdf(), context))
						limpiarTablas(filtro);
				}
				list = new ArrayList();
				list.add(archivosalida + filtro.getNombrepdf());
				zise = 100000;
				if (zip.ponerZip(archivosalida, filtro.getNombrezip(), zise,
						list)) {
					updateDatosCarne("1", filtro.getNombrezip(), "0",
							filtro.getUsuarioid());
					// System.out.println(getName() + "CARNE EXITOSO");
					updateDatosCarneFechaFin(
							new java.sql.Timestamp(System.currentTimeMillis())
									.toString(),
							"1", filtro.getNombrezip(), filtro.getUsuarioid());
					if (!updateReporteEstado(
							modulo,
							filtro.getUsuarioid(),
							rb3.getString("carnes.PathCarnes")
									+ filtro.getNombrezip() + "", "zip20", ""
									+ filtro.getNombrezip(),
							"ReporteActualizarListo")) {
						ponerReporteMensaje(
								"2",
								modulo,
								filtro.getUsuarioid(),
								rb3.getString("carnes.PathCarnes")
										+ filtro.getNombrezip() + "", "zip20",
								"" + filtro.getNombrezip(),
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha final del reporte");
						limpiarTablas(filtro);
						return false;
					}
					if (!updateReporteFecha("update_reporte", modulo,
							filtro.getNombrezip(), filtro.getUsuarioid(), "1")) {
						limpiarTablas(filtro);
						return false;
					}
					limpiarTablas(filtro);
					return true;
				} else {
					// System.out.println(getName() + "CARNE ERROR ZIP");
					updateDatosCarne("2", filtro.getNombrezip(), "0",
							filtro.getUsuarioid());
					ponerReporteMensaje(
							"2",
							modulo,
							filtro.getUsuarioid(),
							rb3.getString("carnes.PathCarnes")
									+ filtro.getNombrezip() + "", "zip20", ""
									+ filtro.getNombrezip(),
							"ReporteActualizarBoletinPaila",
							"Ocurrio problema al hacer zip");
					limpiarTablas(filtro);
				}
			} else {
				// System.out
				// .println("no  hay registros -----------------------------------------");
				rs.close();
				pst.close();
				updateDatosCarne("2", filtro.getNombrezip(), "0",
						filtro.getUsuarioid());
				ponerReporteMensaje(
						"2",
						modulo,
						filtro.getUsuarioid(),
						rb3.getString("carnes.PathCarnes")
								+ filtro.getNombrezip() + "", "zip20", ""
								+ filtro.getNombrezip(),
						"ReporteActualizarBoletinGenerando",
						"nNo se encontraron registros para generar el reporte de Carnes!");
				// System.out
				// .println(getName()
				// +
				// "No se encontraron registros para generar el reporte de Carnes");
				if (!updateReporteFecha("update_reporte", modulo,
						filtro.getNombrezip(), filtro.getUsuarioid(), "2")) {
					limpiarTablas(filtro);
					return false;
				}
				limpiarTablas(filtro);
				return true;
			}
			return true;
		} catch (InternalErrorException e) {
			System.out.println(getName() + "CARNE:Error INTERNAL");
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio una excepcinn interna:_" + e);
			updateDatosCarne("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
			return false;
		} catch (JRException e) {
			System.out.println(getName() + "CARNE:Error JASPER");
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcinn en el Jasper:_" + e);
			updateDatosCarne("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
			return false;
		} catch (SQLException e) {
			System.out.println(getName() + "CARNE:Error SQL");
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcinn SQL:_" + e);
			updateDatosCarne("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			System.out.println(getName() + "CARNE:Error MEMORIA");
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosCarne("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
			return false;
		} catch (Exception e) {
			System.out.println(getName() + "CARNE:Error INTERNO");
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio excepcinn:_"
							+ e);
			updateDatosCarne("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosCarneFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}// Fin de Run

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 */
	public boolean proceso(FiltroBeanCarne filtro) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			vigencia = getVigencia();
			// System.out
			// .println(getName()
			// +
			// "USUARIO--INST-SEDE-JORNADA-METODOLOGIA-GRADO-GRUPO-ID-VIGENCIA:"
			// + filtro.getUsuarioid() + "--"
			// + filtro.getInstitucion() + "-" + filtro.getSede()
			// + "-" + filtro.getJornada() + "-"
			// + filtro.getMetodologia() + "-" + filtro.getGrado()
			// + "-" + filtro.getGrupo() + "-" + filtro.getId()
			// + "-" + vigencia);
			cstmt = con
					.prepareCall("{call PK_CARNES.prepareds_carnes(?,?,?,? ,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			if (filtro.getId().trim().length() > 0) {
				cstmt.setString(posicion++, filtro.getId().trim());
			} else {
				cstmt.setNull(posicion++, Types.VARCHAR);
			}
			cstmt.setString(posicion++, vigencia.trim());
			cstmt.setLong(posicion++, Long.parseLong(filtro.getFormatoCarne()));
			// System.out.println(getName()
			// + "Inicia procedimiento prepareds_carnes.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println(getName()
			// + "Fin procedimiento prepareds_carnes.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("carnes.PathCarnes") + filtro.getNombrezip()
							+ "", "zip20", "" + filtro.getNombrezip(),
					"ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn prepareds:_" + e);
			updateDatosCarne("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}// fin de preparedstatements

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes, y luego poder ser visualizado en <BR>
	 * la lista de reportes generados <BR>
	 * 
	 * @param byte bit
	 */

	public boolean ponerArchivo(String modulo, String path, byte[] bit,
			String archivostatic, String context1) {

		Connection con = null;
		try {
			con = cursor.getConnection();
			String archivosalida = Ruta.get(context1,
					rb3.getString("carne.PathCarne"));
			File f = new File(archivosalida);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(f + File.separator
					+ archivostatic);
			CopyUtils.copy(bit, fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public void ponerReporte(String modulo, String us, String rec, String tipo,
			String nombre, String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (estado));
			pst.executeUpdate();
			con.commit();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public void updateDatosCarne(String nuevoestado, String nombreboletin,
			String estadoanterior, String user) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("update_datos_carne"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(nuevoestado));
			pst.setString(posicion++, (nombreboletin));
			pst.setLong(posicion++, Long.parseLong(estadoanterior));
			pst.setString(posicion++, (user));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public boolean updateReporteFecha(String preparedstatement, String modulo,
			String nombreboletin, String user, String estado) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(preparedstatement));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombreboletin));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
			con.commit();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */
	public boolean updateReporteEstado(String modulo, String us, String rec,
			String tipo, String nombre, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			con.commit();
			pst.close();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public void updateReporte(String modulo, String nombreboletin, String user,
			String estado) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("update_reporte"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombreboletin));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public boolean updatePuestoCarne(String puesto, String nombreboletinzip,
			String user, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (puesto));
			pst.setString(posicion++, (nombreboletinzip));
			pst.setString(posicion++, (user));
			pst.executeUpdate();
			pst.close();
			con.commit();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public void updateDatosCarneFechaGen(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_carne_fecha_gen"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nuevafecha));
			pst.setString(posicion++, (nombreboletin1));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.setString(posicion++, (user1));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public void updateDatosCarneFechaFin(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_carne_fecha_fin"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nuevafecha));
			pst.setString(posicion++, (nombreboletin1));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.setString(posicion++, (user1));
			pst.executeUpdate();
			pst.close();
			con.commit();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public void ponerReporteMensaje(String estado, String modulo, String us,
			String rec, String tipo, String nombre, String prepared,
			String mensaje) {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (estado));
			pst.setString(posicion++, (mensaje));
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public void ponerReporteMensajeListo(String modulo, String us, String rec,
			String tipo, String nombre, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			con.commit();
			pst.close();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablas(FiltroBeanCarne filtro) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			/* callable statement */
			cstmt = con.prepareCall("{call PK_CARNES.carnes_borrar(?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			cstmt.executeUpdate();
			cstmt.close();
			// System.out.println("Se limpio la tabla de carnn");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * limpia las tablas de boletines cuando el hilo esta durmiendo
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void vaciarTablas(FiltroBeanCarne filtro) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con.prepareCall("{call PK_CARNES.carnes_vaciar(?)}");
			cstmt.setLong(posicion++, Long.parseLong("1"));
			cstmt.executeUpdate();
			cstmt.close();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 */

	public boolean update_cola_carnes() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("update_puesto_carne_1"));
			posicion = 1;
			pst.clearParameters();
			pst.executeUpdate();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 */

	public String getVigencia() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String vig = null;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("vigencia"));
			rs = pst.executeQuery();
			if (rs.next())
				vig = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return vig;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 */

	public boolean activo() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String act = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("activo"));
			posicion = 1;
			rs = pst.executeQuery();
			if (rs.next())
				act = rs.getString(1);
			if (act.equals("0"))
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje = "  - " + s + "\n";
	}

	/**
	 * @param sc
	 * @return
	 */
	public String getFoto(String sc) {

		Connection con = null;
		String archivosalida = "";
		try {
			// con = cursor.getConnection();
			archivosalida = Ruta.get(sc, rb3.getString("foto.pathSubir"));

		} catch (Exception e) {
			e.printStackTrace();
			archivosalida = null;
		} finally {

		}
		return archivosalida;
	}

	/**
	 * @param imgRuta
	 * @return
	 */
	private boolean isValidaImagen(File imgRuta) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(imgRuta);
			if (image != null) {
				// System.out.println("imagen valida");
				return true;
			}
		} catch (IOException e) {
			System.out.println("IO exception imagen " + imgRuta.toString());
			e.printStackTrace();
		}
		// System.out.println("imagen no valida " + imgRuta.toString());
		return false;
	}

}
