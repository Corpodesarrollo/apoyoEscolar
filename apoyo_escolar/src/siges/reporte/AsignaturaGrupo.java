package siges.reporte;

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
import siges.login.beans.Login;
import siges.reporte.beans.FiltroBeanEvaluacionAsignaturaGrupo;

/**
 * Nombre: Boletin<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: AsignaturaGrupo <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class AsignaturaGrupo extends Thread {
	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private Login login;
	private String mensaje;
	private boolean err;
	private ResourceBundle r, rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanEvaluacionAsignaturaGrupo filtro;
	private String buscarcodigo;
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	private final String modulo = "19";
	private final static String tipoasignatura = "1";
	private String s;
	private String s1;
	private String archivo, archivozip;
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
	private File reportFileGrado;
	private String path, path1;
	private String context;
	private String codigoestu;
	private PreparedStatement pst;
	private CallableStatement cstmt;
	private ResultSet rs;
	private ControllerFiltroSave filtroSave;
	private String vigencia;

	/* Constructor de la clase */
	public AsignaturaGrupo(Cursor c, String cont, String p, String p1,
			File reportF, File reportFGrado, int nn) {
		super("hilo_asignatura_" + nn);
		cursor = c;
		path = p;
		path1 = p1;
		reportFile = reportF;
		reportFileGrado = reportFGrado;
		context = cont;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle
				.getBundle("preparedstatements_reportesEstadisticas");
		s = rb3.getString("valor_s");
		s1 = rb3.getString("valor_s1");
		buscar = buscarjasper = insertar = existeboletin = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
	}

	public void run() {
		Object[] o = new Object[2];
		int posicion = 1;
		int uno = 0;
		String[][] array = null;
		int dormir = 0;
		String cola = null;
		String puest = "-999";
		String[] evaluaciones = null;
		filtroSave = new ControllerFiltroSave();
		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rb3.getString("reportes.Dormir"));
			while (ocupado) {
				System.out.println(getName() + ":Espera Thread");
				sleep(dormir);
			}
			ocupado = true;
			// System.out.println(getName()+":Entra Thread");

			while (true) {
				if (!activo()) {// es porque en la tabla vale 0
					Thread.sleep(dormir);
					continue;
				}
				array = reporte_Generar();
				if (array != null) {
					for (int i = 0; i < array.length; i++) {
						filtro = new FiltroBeanEvaluacionAsignaturaGrupo();
						// llenar el bean
						filtro.setInstitucion(array[i][0]);
						filtro.setSede(array[i][1]);
						filtro.setJornada(array[i][2]);
						filtro.setMetodologia(array[i][3]);
						filtro.setGrado(array[i][4]);
						filtro.setGrupo(array[i][5]);
						filtro.setPeriodo(array[i][6]);
						filtro.setEvaluaciones((array[i][7].replace('.', ':'))
								.split(":"));
						filtro.setUsuarioid(array[i][8]);
						filtro.setVigencia(array[i][9]);
						filtro.setNombrezip(array[i][10]);
						filtro.setNombrepdf(array[i][11]);
						parameters = new HashMap();
						evaluaciones = filtro.getEvaluaciones();
						System.out.println("-- evaluaciones.length: "
								+ evaluaciones.length);

						parameters.put("usuario", ((String) filtro
								.getUsuarioid().trim()));

						if (evaluaciones.length >= 1)
							parameters.put("p1", (String) evaluaciones[0]);
						if (evaluaciones.length >= 2)
							parameters.put("p2", (String) evaluaciones[1]);
						if (evaluaciones.length >= 3)
							parameters.put("p3", (String) evaluaciones[2]);
						if (evaluaciones.length >= 4)
							parameters.put("p4", (String) evaluaciones[3]);
						if (evaluaciones.length >= 5)
							parameters.put("p5", (String) evaluaciones[4]);
						if (evaluaciones.length >= 6)
							parameters.put("p6", (String) evaluaciones[5]);
						if (evaluaciones.length >= 7)
							parameters.put("p7", (String) evaluaciones[6]);
						if (evaluaciones.length >= 8)
							parameters.put("p8", (String) evaluaciones[7]);
						if (evaluaciones.length >= 8)
							parameters.put("p9", (String) evaluaciones[8]);
						if (evaluaciones.length >= 10)
							parameters.put("p10", (String) evaluaciones[9]);

						parameters.put("tipo", ((String) tipoasignatura));
						parameters.put(
								"grafica",
								path + File.separator
										+ rb3.getString("grafica_asignatura"));

						System.out
								.println("PARAMETERS :" + parameters.values());

						if (!proceso_asignatura_grupo(filtro, parameters)) {
							System.out
									.println("nNO TERMINn PROCESS... OCURRIn ERROR..MIRAR TRACE!");
							ponerReporteMensaje("2", modulo,
									filtro.getUsuarioid(),
									rb3.getString("reporte.PathReporte")
											+ filtro.getNombrezip() + "",
									"zip5", "" + filtro.getNombrezip(),
									"ReporteActualizarBoletinPaila",
									"Problema en process");
							updateDatosAsignatura("2", filtro.getNombrezip(),
									"0", filtro.getUsuarioid());
							updateReporte(modulo, filtro.getNombrezip(),
									filtro.getUsuarioid(), "2");
							continue;
						}
						if (!updatePuestoAsignatura(puest,
								filtro.getNombrezip(), filtro.getUsuarioid(),
								"update_puesto_reporte_2_asig")) {
							System.out
									.println(" **NO Se actualizn el puesto de la asignatura en DATOS_ASIG_GRUPO**");
							continue;
						}
						if (!update_cola_asignaturas()) {
							System.out
									.println(" NO Se actualizn la lista de los reportes en cola");
							continue;
						}
						System.out.println(getName()
								+ ":Sale While Asignaturas");
					}
				} else {
					Thread.sleep(dormir);
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
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(), "ReporteActualizarBoletin",
					"Ocurrio Interrupted excepcinn en el Hilo:_" + ex);
			updateDatosAsignatura("2", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception ex) {
			ex.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(), "ReporteActualizarBoletin",
					"Ocurrio excepcinn en el Hilo:_" + ex);
			updateDatosAsignatura("2", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
		} finally {
			ocupado = false;
		}
	}

	public String[][] reporte_Generar() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		String[][] array1 = null;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("reporte_a_generar_asig"));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, Integer.parseInt(tipoasignatura));
			rs = pst.executeQuery();
			// System.out.println("ejecutn reporte_a_generar");

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
				System.out.println("nnnnSI HAY REPORTES POR GENERAR!!!!");
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
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(), "ReporteActualizarBoletin",
					"Ocurrio excepcinn en el Hilo: " + e);
			updateDatosAsignatura("2", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
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

	public boolean proceso_asignatura_grupo(
			FiltroBeanEvaluacionAsignaturaGrupo filtro, Map parameterscopy) {

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
		String periodoabrev1 = null;
		bytes = null;
		bytes1 = null;
		String cont = null;

		try {
			limpiarTablas(filtro.getUsuarioid());
			System.out
					.println("nSe limpiaron las tablas antes de ser llenadas!");
			updateDatosAsignatura("0", filtro.getNombrezip(), "-1",
					filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"0");
			ponerReporteMensajeListo(
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(), "ReporteActualizarGenerando");
			updateDatosReporteFechaGen(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"0", filtro.getNombrezip(), filtro.getUsuarioid());

			if (!proceso(filtro)) {
				System.out
						.println("nNO TERMINn CALLABLESTATEMENT... OCURRIn ERROR..MIRAR TRACE!");
				return false;
			}
			/* Consulta para ejecutar el/los jasper */
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3
					.getString("jasper_evaluacion_asignaturas_grupo"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			pst.setInt(posicion++, Integer.parseInt(tipoasignatura));
			rs = pst.executeQuery();
			if (rs.next()) {
				rs.close();
				pst.close();

				if (!filtro.getGrado().equals("-9")
						&& filtro.getGrupo().equals("-9")) {
					System.out
							.println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE ASIGNATURAS POR GRADOnnnnnnnnnnnnnnnnnnnnn");
					if ((reportFileGrado.getPath() != null)
							&& (parameters != null)
							&& (!parameters.values().equals("0"))
							&& (con != null)) {
						archivosalida = Ruta
								.get(context,
										rb3.getString("reportes.PathReportesEstadisticos"));
						bytes = JasperRunManager.runReportToPdf(
								reportFileGrado.getPath(), parameters, con);
						if (!ponerArchivo(modulo, path, bytes,
								filtro.getNombrepdf(), context))
							limpiarTablas(filtro.getUsuarioid());
					}
				} else {
					if (!filtro.getGrado().equals("-9")
							&& !filtro.getGrupo().equals("-9")) {
						System.out
								.println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE ASIGNATURAS POR GRUPOnnnnnnnnnnnnnnnnnnnnn");
						if ((reportFile.getPath() != null)
								&& (parameters != null)
								&& (!parameters.values().equals("0"))
								&& (con != null)) {
							archivosalida = Ruta
									.get(context,
											rb3.getString("reportes.PathReportesEstadisticos"));
							bytes = JasperRunManager.runReportToPdf(
									reportFile.getPath(), parameters, con);
							if (!ponerArchivo(modulo, path, bytes,
									filtro.getNombrepdf(), context))
								limpiarTablas(filtro.getUsuarioid());
						}
					}
				}

				list = new ArrayList();
				list.add(archivosalida + filtro.getNombrepdf());
				zise = 100000;

				if (zip.ponerZip(archivosalida, filtro.getNombrezip(), zise,
						list)) {
					updateDatosAsignatura("1", filtro.getNombrezip(), "0",
							filtro.getUsuarioid());
					updateDatosAsignaturaFechaFin(
							new java.sql.Timestamp(System.currentTimeMillis())
									.toString(),
							"1", filtro.getNombrezip(), filtro.getUsuarioid());
					if (!updateReporteEstado(
							modulo,
							filtro.getUsuarioid(),
							rb3.getString("reporte.PathReporte")
									+ filtro.getNombrezip() + "", "zip5", ""
									+ filtro.getNombrezip(),
							"ReporteActualizarListo")) {
						ponerReporteMensaje(
								"2",
								modulo,
								filtro.getUsuarioid(),
								rb3.getString("reporte.PathReporte")
										+ filtro.getNombrezip() + "", "zip5",
								"" + filtro.getNombrezip(),
								"ReporteActualizarBoletinPaila",
								"Ocurrio problema al actualizar fecha final del reporte");
						limpiarTablas(filtro.getUsuarioid());
						return false;
					}
					if (!updateReporteFecha("update_reporte_general", modulo,
							filtro.getNombrezip(), filtro.getUsuarioid(), "1")) {
						limpiarTablas(filtro.getUsuarioid());
						return false;
					}
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
						rb3.getString("reporte.PathReporte")
								+ filtro.getNombrezip() + "",
						"zip5",
						"" + filtro.getNombrezip(),
						"ReporteActualizarBoletinPaila",
						"nNo se encontraron registros para generar el reporte de Evaluacinn de asignaturas por grupo!");
				System.out
						.println("nNo se encontraron registros para generar el reporte de Evaluacinn de asignaturas por grupo!");
				if (!updateReporteFecha("update_reporte_general", modulo,
						filtro.getNombrezip(), filtro.getUsuarioid(), "2")) {
					limpiarTablas(filtro.getUsuarioid());
					return false;
				}
				limpiarTablas(filtro.getUsuarioid());
				return true;
			}
			return true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio una excepcinn interna:_" + e);
			updateDatosAsignatura("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcinn en el Jasper:_" + e);
			updateDatosAsignatura("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcinn SQL:_" + e);
			updateDatosAsignatura("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
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
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosAsignatura("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio excepcinn:_"
							+ e);
			updateDatosAsignatura("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateDatosAsignaturaFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrezip(), filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
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
	public boolean proceso(FiltroBeanEvaluacionAsignaturaGrupo filtro) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-GRUPO");
			System.out.println("SEDE: " + filtro.getSede());
			System.out.println("JORNADA: " + filtro.getJornada());
			System.out.println("METOD: " + filtro.getMetodologia());
			System.out.println("GRADO: " + filtro.getGrado());
			System.out.println("GRUPO: " + filtro.getGrupo());
			System.out.println("PERIODO: " + filtro.getPeriodo().trim());
			System.out.println("Vigencia: " + filtro.getVigencia().trim());

			cstmt = con
					.prepareCall("{call PK_ESTADISTICOS.estadisticos_asignaturas_grupo(?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInsitucion()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtro.getPeriodo().trim()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getVigencia()));
			cstmt.setInt(posicion++, Integer.parseInt(tipoasignatura));
			System.out
					.println("Inicia procedimiento estadisticos_asignaturas_grupo.. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.executeUpdate();
			System.out
					.println("Fin procedimiento estadisticos_asignaturas_grupo.. Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reporte.PathReporte")
							+ filtro.getNombrezip() + "", "zip5",
					"" + filtro.getNombrezip(),
					"ReporteActualizarBoletinPaila",
					"Ocurrio excepcinn prepareds:_" + e);
			updateDatosAsignatura("2", filtro.getNombrezip(), "0",
					filtro.getUsuarioid());
			updateReporte(modulo, filtro.getNombrezip(), filtro.getUsuarioid(),
					"2");
			limpiarTablas(filtro.getUsuarioid());
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
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
	 **/

	public boolean ponerArchivo(String modulo, String path, byte[] bit,
			String archivostatic, String context1) {

		Connection con = null;
		try {
			con = cursor.getConnection();
			String archivosalida = Ruta.get(context1,
					rb3.getString("reportes.PathReportesEstadisticos"));
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
	 **/

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

	public void updateDatosAsignatura(String nuevoestado, String nombreboletin,
			String estadoanterior, String user) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con
					.prepareStatement(rb3.getString("update_datos_asignatura"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(nuevoestado));
			pst.setString(posicion++, (nombreboletin));
			pst.setLong(posicion++, Long.parseLong(estadoanterior));
			pst.setString(posicion++, (user));
			pst.executeUpdate();
			pst.close();
			con.commit();
			System.out.println("nnSe actualizn en datos_logro_grupo!!!!");

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
			System.out
					.println("nnSe actualizn la fecha en la tabla Reporte!!!!");

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
			System.out.println("nnSE ACTUALIZn EL ESTADO DEL REPORTE!!");
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

	public boolean updatePuestoAsignatura(String puesto,
			String nombreboletinzip, String user, String prepared) {
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
			pst.setInt(posicion++, Integer.parseInt(tipoasignatura));
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
	 **/

	public void updateDatosReporteFechaGen(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_logro_fecha_gen"));
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

	public void updateDatosAsignaturaFechaFin(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_asignatura_fecha_fin"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nuevafecha));
			pst.setString(posicion++, (nombreboletin1));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.setString(posicion++, (user1));
			pst.executeUpdate();
			pst.close();
			con.commit();
			System.out
					.println("nnSe insertn la nueva fecha FIN en datos_logro_grupo!!!!");

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
			pst.close();
			con.commit();
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
			/* callable statement */
			cstmt = con
					.prepareCall("{call PK_ESTADISTICOS.asignaturas_grupo_borrar(?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			cstmt.setInt(posicion++, Integer.parseInt(tipoasignatura));
			System.out.println("Inicia asignaturas_grupo_borrar.. Hora: "
					+ new java.sql.Timestamp(System.currentTimeMillis())
							.toString());
			cstmt.executeUpdate();
			System.out.println("Fin asignaturas_grupo_borrar.. Hora: "
					+ new java.sql.Timestamp(System.currentTimeMillis())
							.toString());
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
	 * 
	 * limpia las tablas de boletines cuando el hilo esta durmiendo
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
					.prepareCall("{call PK_ESTADISTICOS.asignaturas_grupo_vaciar(?,?)}");
			cstmt.setLong(posicion++, Long.parseLong("1"));
			cstmt.setInt(posicion++, Integer.parseInt(tipoasignatura));
			// System.out.println("Inicia asignaturas_grupo_vaciar.. Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.executeUpdate();
			// System.out.println("Fin asignaturas_grupo_vaciar.. Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
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
	 **/

	public boolean update_cola_asignaturas() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3
					.getString("update_puesto_reporte_1_asig"));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, Integer.parseInt(tipoasignatura));
			pst.executeUpdate();
			con.commit();
			System.out.println("nnSe ejecutn update_puesto_reporte_1!!!!");
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
			// System.out.println("activo: "+act);
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
