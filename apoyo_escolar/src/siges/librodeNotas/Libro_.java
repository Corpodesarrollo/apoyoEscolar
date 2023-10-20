package siges.librodeNotas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.librodeNotas.beans.FiltroBeanLibro;
import siges.login.beans.Login;

/**
 * Nombre: Boletin<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class Libro_ extends Thread {

	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private Login login;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle r, rb3;
	private Collection list;
	private Object[] o;
	private File escudo;
	private java.sql.Timestamp f2;
	private FiltroBeanLibro filtro;
	private String buscarcodigo;
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	private final String modulo = "8";
	private String s;
	private String s1;
	private String archivo, archivopre, archivozip;
	private String buscar, buscarjasper;
	private String insertar;
	private String existeboletin;
	private String ant;
	private String er;
	private String sig;
	private String home;
	private byte[] bytes;
	private byte[] bytes1;
	private Map parameters;
	private Map copyparameters;
	private File reportFile;
	private File reportFile1;
	private String path, path1, path2;
	private String context;
	private String codigoestu;
	private PreparedStatement pst = null;
	private CallableStatement cstmt;
	private ResultSet rs = null;
	private libros libro;
	private String vigenciaLibro;
	private String vigenciaSistema;
	private final String quintoper = "5";
	private String nombreperiodo;

	/* Constructor de la clase */

	public Libro_(Cursor c, String cont, String p, String p1, String p2,
			File reportF, File reportF1, int nn) {
		super("hilo_libro_notas" + nn);
		cursor = c;
		path = p;
		path1 = p1;
		path2 = p2;
		reportFile = reportF;
		reportFile1 = reportF1;
		context = cont;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle.getBundle("preparedstatements_librosdenotas");
		buscar = buscarjasper = insertar = existeboletin = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
		s = rb3.getString("Libros");
		s1 = rb3.getString("Filtro");
		nombreperiodo = rb3.getString("quintoperiodo");
	}

	public void run() {
		Connection con = null;
		Object[] o = new Object[2];
		int posicion = 1;
		String[][] array = null;
		int dormir = 0;
		String cola = null;
		String puest = "-999";
		libro = new libros();

		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rb3.getString("libros.Dormir"));
			while (ocupado) {
				System.out.println(getName() + ":Espera Thread");
				sleep(dormir);
			}
			ocupado = true;
			// System.out.println(getName()+":Entra Thread");

			while (true) {
				if (!activo()) {// es porque en la tabla vale 0
					// System.out.println("**ESTA APAGADO**");
					Thread.sleep(dormir);
					continue;
				}
				array = libro_Generar();
				if (array != null) {
					for (int i = 0; i < array.length; i++) {
						filtro = new FiltroBeanLibro();
						// llenar el bean
						filtro.setInstitucion(array[i][0]);
						filtro.setSede(array[i][1]);
						filtro.setJornada(array[i][2]);
						filtro.setMetodologia(array[i][3]);
						filtro.setGrado(array[i][4]);
						filtro.setUsuarioid(array[i][6]);
						filtro.setOrden(array[i][7]);
						filtro.setNombrelibrozip(array[i][8]);
						filtro.setNombrelibro(array[i][9]);
						filtro.setNombrelibropre(array[i][10]);
						filtro.setAno(array[i][11]);
						filtro.setInstituciondane(array[i][12]);
						parameters = new HashMap();
						parameters
								.put("usuario", ((String) array[i][6]).trim());
						parameters.put("libro_final",
								path + rb3.getString("subrep"));
						parameters.put("pie_libro_final",
								path + rb3.getString("subrep4"));
						// parameters.put("LogoCundinamarca",path1+rb3.getString("imagen"));

						escudo = new File(path2 + "e"
								+ filtro.getInstituciondane().trim() + ".gif");
						System.out.println("escudo: " + escudo);
						if (escudo.exists())
							parameters.put("LogoCundinamarca", path2 + "e"
									+ filtro.getInstituciondane().trim()
									+ ".gif");
						else
							parameters.put("LogoCundinamarca",
									path1 + rb3.getString("imagen"));

						parameters.put("LogoSED",
								path1 + rb3.getString("imagen"));

						parameters.put("DimensionPreescolar",
								path1 + rb3.getString("imagen_preescolar"));
						parameters.put("DimensionPreescolarImagen",
								path1 + rb3.getString("imagen_preescolar2"));
						/* NUEVOS PARAMETROS PARA LOS JASPERS DE PREESCOLAR */
						parameters.put("cuerpo_libro_preescolar",
								path + rb3.getString("reppre1"));
						parameters.put("pie_libro_preescolar",
								path + rb3.getString("reppre2"));
						parameters.put("promocion_estudiantes",
								path + rb3.getString("promocion"));
						parameters.put("promocion_estudiantes_preesco", path
								+ rb3.getString("promocion_preesco"));
						parameters.put("codigo_institucion", filtro
								.getInsitucion().trim());

						if (filtro.getOrden().equals("-9"))
							parameters.put("orden", "ENC_APE_ESTUDIANTE");
						if (filtro.getOrden().equals("0"))
							parameters.put("orden", "ENC_DOC_ESTUDIANTE");
						if (filtro.getOrden().equals("1"))
							parameters.put("orden", "ENC_NOM_ESTUDIANTE");
						if (filtro.getOrden().equals("2"))
							parameters.put("orden", "ENC_EST_CODIGO");
						else
							parameters.put("orden",
									"ENC_APE_ESTUDIANTE,ENC_NOM_ESTUDIANTE");

						System.out.println("PARAMETERS DE LOS JASPERS: "
								+ parameters.values());
						// llamar a procces
						if (!process(filtro, parameters)) {
							System.out
									.println("nNO TERMINn PROCESS... OCURRIn ERROR..MIRAR TRACE!");
							ponerReporteMensaje(
									"2",
									modulo,
									filtro.getUsuarioid(),
									rb3.getString("libro.PathLibros")
											+ filtro.getNombrelibrozip() + "",
									"zip1", "" + filtro.getNombrelibrozip(),
									"ReporteActualizarBoletinPaila",
									"problema en process");
							updateDatosLibro("2", filtro.getNombrelibrozip(),
									"-1", filtro.getUsuarioid());
							updateReporte(filtro.getNombrelibrozip(),
									filtro.getUsuarioid(), "2");
							continue;
						}

						if (!updatePuestoLibro(puest,
								filtro.getNombrelibrozip(),
								filtro.getUsuarioid(), "update_puesto_libro_2")) {
							System.out
									.println(" **NO Se actualizn el puesto del libro en Datos_Libro**");
							continue;
						}

						if (!update_cola_libro()) {
							System.out
									.println(" No se actualizn la lista de los libros en cola");
							continue;
						}
						System.out.println(getName() + ":Sale While Thread");
					}
				} else {
					Thread.sleep(dormir);
					// System.out.println("**Se mandaron vaciar las tablas ya que no hay libros de notas por generar**");
					vaciarTablas();
					// System.out.print("*");
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletin",
					"Ocurrin Interrupted excepcinn en el Hilo:_" + ex);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "-1",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception ex) {
			ex.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en el Hilo:_" + ex);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "-1",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
		} finally {
			ocupado = false;
		}
	}

	public String[][] libro_Generar() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		String[][] array1 = null;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("libro_a_generar"));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();
			System.out.println("ejecutn libro_a_generar");

			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int i = 0;
			int f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}

			if (!list.isEmpty()) {
				System.out
						.println("nnnnSI HAY LIBROS DE NOTAS POR GENERAR!!!!");
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
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en el Hilo: " + e);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "-1",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
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

	public boolean process(FiltroBeanLibro filtro, Map parameterscopy) {

		parameterscopy = parameters;
		Object[] o;
		list = new ArrayList();
		String nombre;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Zip zip = new Zip();

		Collection list = new ArrayList();
		String archivosalida = null;
		o = new Object[2];
		int zise;
		int posicion = 1;
		String quintoperiodo = null;
		bytes = null;
		bytes1 = null;
		String cont = null;

		try {
			quintoperiodo = "5";
			limpiarTablas(filtro.getUsuarioid());
			System.out
					.println("nSe limpiaron las tablas antes de ser llenadas!");
			updateDatosLibro("0", filtro.getNombrelibrozip(), "-1",
					filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"0");
			ponerReporteMensajeListo(
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarGenerando");
			updateDatosBoletinFechaGen(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"0", filtro.getNombrelibrozip(), filtro.getUsuarioid());

			if (!preparedstatementsLibro(filtro.getUsuarioid(),
					filtro.getInsitucion(), filtro.getGrado(),
					filtro.getMetodologia(), filtro.getSede(),
					filtro.getJornada(), quintoper, nombreperiodo)) {
				System.out
						.println("nNO TERMINn LOS PREPAREDSTATEMENTS... OCURRIn ERROR..MIRAR TRACE!");
				return false;
			}
			con = cursor.getConnection();
			System.out
					.println("SE MANDARON A EJECUTAR LAS CONSULTAS PARA SABER CUAL(ES) JASPER(S)SE DEBE(N) EJECUTAR HORA: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			/* Consulta para ejecutar el/los jasper */
			pst = con.prepareStatement(rb3.getString("query2jasper"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			rs = pst.executeQuery();

			if (rs.next()) {
				rs.close();
				pst.close();
				System.out
						.println("nnnnnnnnnnnnnnnnSE DEBEN EJECUTAR LOS DOS JASPERnnnnnnnnnnnnnnnnnnnnn");
				if ((reportFile.getPath() != null)
						&& (reportFile1.getPath() != null)
						&& (parameterscopy != null)
						&& (!parameterscopy.values().equals("0"))
						&& (con != null)) {
					System.out
							.println("***Se mandn ejecutar el jasper de preescolar****");
					bytes1 = JasperRunManager.runReportToPdf(
							reportFile1.getPath(), parameterscopy, con);
					System.out
							.println("***Se mandn ejecutar el jasper de los demns grados****");
					bytes = JasperRunManager.runReportToPdf(
							reportFile.getPath(), parameterscopy, con);
				}// rs
				ponerReporteMensajeListo(
						modulo,
						filtro.getUsuarioid(),
						rb3.getString("libro.PathLibros")
								+ filtro.getNombrelibrozip() + "", "zip1", ""
								+ filtro.getNombrelibrozip(),
						"ReporteActualizarListo");
				ponerArchivo(modulo, path, bytes1, filtro.getNombrelibropre());
				ponerArchivo(modulo, path, bytes, filtro.getNombrelibro());
				archivosalida = Ruta.get(context,
						rb3.getString("libro.PathLibros"));
				list.add(archivosalida + filtro.getNombrelibropre());// pdf
				list.add(archivosalida + filtro.getNombrelibro());// pdf
				zise = 100000;

				if (zip.ponerZip(archivosalida, filtro.getNombrelibrozip(),
						zise, list)) {
					updateDatosLibro("1", filtro.getNombrelibrozip(), "0",
							filtro.getUsuarioid());
					updateDatosLibroFechaFin(
							new java.sql.Timestamp(System.currentTimeMillis())
									.toString(),
							"1", filtro.getNombrelibrozip(), filtro
									.getUsuarioid());
					updateReporte(filtro.getNombrelibrozip(),
							filtro.getUsuarioid(), "1");
					limpiarTablas(filtro.getUsuarioid());
					return true;
				}
			}// fin de rs
			else {
				rs.close();
				pst.close();
				/* Consulta para ejecutar el/los jasper */
				pst = con.prepareStatement(rb3.getString("queryjasperpre"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
				rs = pst.executeQuery();

				if (rs.next()) {
					rs.close();
					pst.close();
					System.out
							.println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE PREESCOLAR nNICAMENTEnnnnnnnnnnnnnnnnnnnnn");
					if ((reportFile1.getPath() != null)
							&& (parameterscopy != null)
							&& (!parameterscopy.values().equals("0"))
							&& (con != null)) {
						System.out
								.println("***Se mandn ejecutar el jasper de preescolar****");
						bytes1 = JasperRunManager.runReportToPdf(
								reportFile1.getPath(), parameters, con);
					}
					ponerReporteMensajeListo(
							modulo,
							filtro.getUsuarioid(),
							rb3.getString("libro.PathLibros")
									+ filtro.getNombrelibrozip() + "", "zip1",
							"" + filtro.getNombrelibrozip(),
							"ReporteActualizarListo");
					ponerArchivo(modulo, path, bytes1,
							filtro.getNombrelibropre());
					archivosalida = Ruta.get(context,
							rb3.getString("libro.PathLibros"));
					list.add(archivosalida + filtro.getNombrelibropre());// pdf
					zise = 100000;

					if (zip.ponerZip(archivosalida, filtro.getNombrelibrozip(),
							zise, list)) {
						updateDatosLibro("1", filtro.getNombrelibrozip(), "0",
								filtro.getUsuarioid());
						updateDatosLibroFechaFin(
								new java.sql.Timestamp(
										System.currentTimeMillis()).toString(),
								"1", filtro.getNombrelibrozip(),
								filtro.getUsuarioid());
						updateReporte(filtro.getNombrelibrozip(),
								filtro.getUsuarioid(), "1");
						limpiarTablas(filtro.getUsuarioid());
						return true;
					}
				} else {
					rs.close();
					pst.close();
					/* Consulta para ejecutar el/los jasper */
					pst = con.prepareStatement(rb3.getString("queryjaspergig"));
					posicion = 1;
					pst.clearParameters();
					pst.setLong(posicion++,
							Long.parseLong(filtro.getUsuarioid()));
					rs = pst.executeQuery();

					if (rs.next()) {
						rs.close();
						pst.close();
						System.out
								.println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE LOS DEMnS GRADOS nNICAMENTEnnnnnnnnnnnnnnnnnnnnn");
						if ((reportFile.getPath() != null)
								&& (parameterscopy != null)
								&& (!parameterscopy.values().equals("0"))
								&& (con != null))
							bytes = JasperRunManager.runReportToPdf(
									reportFile.getPath(), parameterscopy, con);

						ponerReporteMensajeListo(
								modulo,
								filtro.getUsuarioid(),
								rb3.getString("libro.PathLibros")
										+ filtro.getNombrelibrozip() + "",
								"zip1", "" + filtro.getNombrelibrozip(),
								"ReporteActualizarListo");
						ponerArchivo(modulo, path, bytes,
								filtro.getNombrelibro());
						archivosalida = Ruta.get(context,
								rb3.getString("libro.PathLibros"));
						list.add(archivosalida + filtro.getNombrelibro());// pdf
						zise = 100000;

						if (zip.ponerZip(archivosalida,
								filtro.getNombrelibrozip(), zise, list)) {
							System.out.println("ENTRn A PONER ZIP ");
							updateDatosLibro("1", filtro.getNombrelibrozip(),
									"0", filtro.getUsuarioid());
							updateDatosLibroFechaFin(new java.sql.Timestamp(
									System.currentTimeMillis()).toString(),
									"1", filtro.getNombrelibrozip(),
									filtro.getUsuarioid());
							updateReporte(filtro.getNombrelibrozip(),
									filtro.getUsuarioid(), "1");
							System.out.println("PARAMETROS: "
									+ parameters.values());
							limpiarTablas(filtro.getUsuarioid());
							return true;
						}
					} else {
						rs.close();
						pst.close();
						ponerReporteMensaje(
								"2",
								modulo,
								filtro.getUsuarioid(),
								rb3.getString("libro.PathLibros")
										+ filtro.getNombrelibrozip() + "",
								"zip1", "" + filtro.getNombrelibrozip(),
								"ReporteActualizarBoletinGenerando",
								"nNo se encontraron registros para generar el libro de notas!");
						System.out
								.println("nNo se encontraron registros para generar el libro!");
						updateDatosLibro("2", filtro.getNombrelibrozip(), "0",
								filtro.getUsuarioid());
						updateReporte(filtro.getNombrelibrozip(),
								filtro.getUsuarioid(), "2");
						// limpiarTablas(filtro.getUsuarioid());
						System.out.println("***NO se limpiaron las tablas***");
						return true;
					}
					rs.close();
					pst.close();
				}
				rs.close();
				pst.close();
			}
			rs.close();
			pst.close();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrin una excepcinn interna:_" + e);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "0",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrin excepcinn en el Jasper:_" + e);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "0",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrin excepcinn en el Jasper:_" + e);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "0",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte_" + e);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "0",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		}

		catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletinGenerando", "Ocurrin excepcinn:_"
							+ e);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "0",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
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
	 **/
	public boolean preparedstatementsLibro(String usuarioid,
			String institucioncod, String gradocod, String metodologiacod,
			String sedecod, String jornadacod, String quintopercod,
			String nombreperiodo1) {
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		Object[] o = new Object[2];
		buscarcodigo = null;
		int posicion = 1;
		int grado = -9;
		int intperiodoabrev = 0;

		try {
			vigenciaLibro = filtro.getAno();
			vigenciaSistema = getVigencia();

			if (vigenciaLibro.equals("")) {
				vigenciaLibro = getVigencia();
			}
			System.out.println("VIGENCIA DEL LIBRO DE NOTAS: " + vigenciaLibro);
			System.out.println("VIGENCIA DEL SISTEMA: " + vigenciaSistema);
			grado = Integer.parseInt(filtro.getGrado());

			System.out.println("!usuario:! " + filtro.getUsuarioid());
			System.out.println("!inst:! " + filtro.getInsitucion());
			System.out.println("!Sede:! " + filtro.getSede());
			System.out.println("!Jornada:! " + filtro.getJornada());
			System.out.println("!metod:! " + filtro.getMetodologia());
			System.out.println("!grado:! " + filtro.getGrado());
			System.out.println("!ano:! " + vigenciaLibro);
			System.out.println("!periodo:! " + quintopercod);
			System.out.println("!nombre_periodo:! " + nombreperiodo1);

			con = cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call PK_LIBRO_NOTAS.notas_prepareds_libro_notas(?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInsitucion()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			cstmt.setLong(posicion++, Long.parseLong(vigenciaLibro));
			cstmt.setLong(posicion++, Long.parseLong(quintopercod));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));// 1
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));// 2
			cstmt.setString(posicion++, nombreperiodo1);
			cstmt.setLong(posicion++, Long.parseLong(vigenciaSistema));// 3
			System.out
					.println("Inicia procedimiento notas_prepareds_libro_notas.. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.executeUpdate();
			System.out
					.println("Fin procedimiento notas_prepareds_libro_notas.. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("libro.PathLibros")
							+ filtro.getNombrelibrozip() + "", "zip1", ""
							+ filtro.getNombrelibrozip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrin excepcinn prepareds:_" + e);
			updateDatosLibro("2", filtro.getNombrelibrozip(), "0",
					filtro.getUsuarioid());
			updateDatosLibroFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrelibrozip(), filtro.getUsuarioid());
			updateReporte(filtro.getNombrelibrozip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
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
	 **/

	public void ponerArchivo(String modulo, String path, byte[] bit,
			String archivostatic) {

		Connection con = null;
		try {
			con = cursor.getConnection();
			String archivosalida = Ruta.get(context,
					r.getString("libro.PathLibro"));
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
			limpiarTablas(filtro.getUsuarioid());
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (InternalErrorException inte) {
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
	 **/

	public void updateDatosLibro(String nuevoestado, String nombreboletin,
			String estadoanterior, String user) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("update_datos_libro"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(nuevoestado));
			pst.setString(posicion++, (nombreboletin));
			pst.setLong(posicion++, Long.parseLong(estadoanterior));
			pst.setString(posicion++, (user));
			pst.executeUpdate();
			con.commit();
			System.out.println("nnSe actualizn en datos_libro!!!!");

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
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
	 **/

	public void updateReporte(String nombreboletin, String user, String estado) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("update_reporte_libro"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nombreboletin));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			con.commit();
			System.out
					.println("nnSe actualizn la fecha en la tabla Reporte!!!!");
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
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
	 **/

	public boolean updatePuestoLibro(String puesto, String nombreboletinzip,
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
			con.commit();
			System.out.println("nnSe ejecutn update_puesto_libro!!!!");
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
	 **/

	public void updateDatosBoletinFechaGen(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_libro_fecha_gen"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nuevafecha));
			pst.setString(posicion++, (nombreboletin1));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.setString(posicion++, (user1));
			pst.executeUpdate();
			con.commit();
			// System.out.println("nnSe insertn la nueva fecha GEN en datos_boletin!!!!");

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
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
	 **/

	public void updateDatosLibroFechaFin(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_libro_fecha_fin"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nuevafecha));
			pst.setString(posicion++, (nombreboletin1));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.setString(posicion++, (user1));
			pst.executeUpdate();
			con.commit();
			System.out
					.println("nnSe insertn la nueva fecha FIN en datos_libro!!!!");

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
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
	 **/

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
			con.commit();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
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
	 **/

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
			// System.out.println("nActualizn la tabla Reporte_Listo!");

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(filtro.getUsuarioid());
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

	public void limpiarTablas(String usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_LIBRO_NOTAS.libro_notas_borrar(?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			System.out.println("Inicia libro_notas_borrar.. Hora: "
					+ new java.sql.Timestamp(System.currentTimeMillis())
							.toString());
			cstmt.executeUpdate();
			System.out.println("Fin libro_notas_borrar.. Hora: "
					+ new java.sql.Timestamp(System.currentTimeMillis())
							.toString());
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * limpia las tablas de libros cuando el hilo esta durmiendo
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void vaciarTablas() {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call PK_LIBRO_NOTAS.libro_notas_vaciar_tablas(?)}");
			cstmt.setLong(posicion++, Long.parseLong("1"));
			System.out.println("Inicia libro_notas_vaciar_tablas.. Hora: "
					+ new java.sql.Timestamp(System.currentTimeMillis())
							.toString());
			cstmt.executeUpdate();
			System.out.println("Fin libro_notas_vaciar_tablas.. Hora: "
					+ new java.sql.Timestamp(System.currentTimeMillis())
							.toString());
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
	 **/

	public boolean update_cola_libro() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("update_puesto_libro_1"));
			posicion = 1;
			pst.clearParameters();
			pst.executeUpdate();
			con.commit();
			System.out.println("nnSe ejecutn update_puesto_libro_1!!!!");
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
	 **/

	public String getVigencia() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String vig = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("vigencia"));
			posicion = 1;
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
	 **/

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
	 **/
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje = "  - " + s + "\n";
	}

}
