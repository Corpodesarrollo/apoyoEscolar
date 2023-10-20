package siges.boletines;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import siges.boletines.beans.FiltroBeanFormulario;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.io.Zip;

/**
 * Nombre: ReporteLogroPendiente<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ReporteLogroPendiente extends Thread {

	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle r, rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanFormulario filtro;
	private String buscarcodigo;
	private final String modulo = "28";
	// private String s;
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
	private Map parameters;
	private File reportFile;
	private String path, path1;
	private String context;
	private String codigoestu;
	private String vigencia;
	private ReporteLogrosDAO reporteDao;
	private static final int tipo = 3;

	/* Constructor de la clase */

	public ReporteLogroPendiente(Cursor c, String cont, String p, String p1,
			File reportF, int nn) {
		super("hilo_logro_pendiente" + nn);
		cursor = c;
		path = p;
		path1 = p1;
		reportFile = reportF;
		context = cont;
		rb3 = ResourceBundle.getBundle("preparedstatements_logros_pendientes");
		// s=rb3.getString("valor_s");
		s1 = rb3.getString("valor_s1");
		buscar = buscarjasper = insertar = existeboletin = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
		reporteDao = new ReporteLogrosDAO(cursor);
		vigencia = reporteDao.getVigenciaActual();
	}

	public void run() {
		Object[] o = new Object[2];
		int posicion = 1;
		String[][] array = null;
		int dormir = 0;
		String cola = null;
		String puest = "-999";
		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rb3.getString("reportes.Dormir"));
			while (ocupado) {
				// System.out.println(getName()+":Espera Thread");
				sleep(dormir);
			}
			ocupado = true;
			// System.out.println(getName()+":Entra Thread");

			while (true) {
				if (!reporteDao.activo()) {
					Thread.sleep(dormir);
					continue;
				}
				array = reporteDao.reporteGenerar(filtro, modulo,
						"reporte_a_generar");
				if (array != null) {
					for (int i = 0; i < array.length; i++) {
						filtro = new FiltroBeanFormulario();
						// llenar el bean
						filtro.setInstitucion(array[i][0]);
						filtro.setSede(array[i][1]);
						filtro.setJornada(array[i][2]);
						filtro.setMetodologia(array[i][3]);
						filtro.setGrado(array[i][4]);
						filtro.setGrupo(array[i][5]);
						filtro.setEstudiante(array[i][6]);
						filtro.setPeriodo(array[i][7]);
						filtro.setPeriodonom(array[i][8]);
						filtro.setUsuarioid(array[i][10]);
						filtro.setOrden(array[i][11]);
						filtro.setNombrereportezip(array[i][12]);
						filtro.setNombrereporte(array[i][13]);
						parameters = new HashMap();
						parameters.put("LogoCundinamarca",
								path1 + rb3.getString("imagen"));
						parameters.put("usuario",
								((String) array[i][10]).trim());
						parameters.put("tipo", (String.valueOf(tipo)));
						parameters.put("logros_pendientes",
								path + rb3.getString("logros_pendientes"));

						// System.out
						// .println("PARAMETROS: " + parameters.values());

						if (!procesoJasper(filtro, parameters)) {
							// System.out
							// .println("nNO TERMINn PROCESS... OCURRIn ERROR..MIRAR TRACE!");
							reporteDao
									.ponerReporteMensaje(
											"2",
											modulo,
											filtro.getUsuarioid(),
											rb3.getString("reportes.PathReportes")
													+ filtro.getNombrereportezip()
													+ "", "zip",
											"" + filtro.getNombrereportezip(),
											"ReporteActualizarBoletinPaila",
											"Problema en procesoJasper");
							reporteDao.updateDatosReporte("2",
									filtro.getNombrereportezip(), "0",
									filtro.getUsuarioid(),
									"update_datos_reporte");
							reporteDao.updateReporte(modulo,
									filtro.getNombrereportezip(),
									filtro.getUsuarioid(), "2",
									"update_reporte");
							continue;
						}
						if (!reporteDao.updatePuestoReporte(puest,
								filtro.getNombrereportezip(),
								filtro.getUsuarioid(),
								"update_puesto_reporte_2")) {
							// System.out
							// .println(" **NO Se actualizn el puesto del reporte en DATOS_REPORTE_ESTUDIANTE**");
							continue;
						}
						if (!reporteDao
								.updateColaReportes("update_puesto_reporte_1")) {
							// System.out
							// .println(" NO Se actualizn la lista de los reportes en cola");
							continue;
						}
						// System.out.println(getName()
						// + ":Sale While ReporteEstudiante");
					}
				} else {
					Thread.sleep(dormir);
					reporteDao.vaciarTablas();
					// System.out.print("*");
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			reporteDao.ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletin",
					"Ocurrio Interrupted excepcion en el Hilo:_" + ex);
			reporteDao.updateDatosReporte("2", filtro.getNombrereportezip(),
					"-1", filtro.getUsuarioid(), "update_datos_reporte");
			reporteDao.updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(), "2", filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "update_datos_reporte_fecha_fin");
			reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			reporteDao.limpiarTablas(filtro.getUsuarioid());
		} catch (Exception ex) {
			ex.printStackTrace();
			reporteDao.ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletin",
					"Ocurrio excepcion en el Hilo:_" + ex);
			reporteDao.updateDatosReporte("2", filtro.getNombrereportezip(),
					"-1", filtro.getUsuarioid(), "update_datos_reporte");
			reporteDao.updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(), "2", filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "update_datos_reporte_fecha_fin");
			reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			reporteDao.limpiarTablas(filtro.getUsuarioid());
		} finally {
			ocupado = false;
		}
	}

	public boolean procesoJasper(FiltroBeanFormulario filtro, Map parameterscopy) {

		parameterscopy = parameters;
		Object[] o;
		list = new ArrayList();
		String nombre;
		Zip zip = new Zip();
		Collection list = new ArrayList();
		String archivosalida = null;
		o = new Object[2];
		int zise;
		int posicion = 1;
		bytes = null;

		try {
			reporteDao.limpiarTablas(filtro.getUsuarioid());
			// System.out
			// .println("nSe limpiaron las tablas antes de ser llenadas!");
			reporteDao.updateDatosReporte("0", filtro.getNombrereportezip(),
					"-1", filtro.getUsuarioid(), "update_datos_reporte");
			reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "0", "update_reporte");
			reporteDao.ponerReporteMensajeListo(
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarGenerando");
			reporteDao.updateDatosReporteFechaGen(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(), "0", filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "update_datos_reporte_fecha_gen");

			// System.out.println("nombre del periodo: " +
			// filtro.getPeriodonom());
			if (!reporteDao.callableStatements(filtro, vigencia, modulo, tipo)) {
				// System.out
				// .println("nNO TERMINn CALLABLES... OCURRIn ERROR..MIRAR TRACE!");
				return false;
			}

			if (reporteDao.ejecutarJasper(filtro.getUsuarioid())) {
				// System.out
				// .println("SE DEBE EJECUTAR EL JASPER DE LOGROS PENDIENTES");
				bytes = reporteDao.getArrayBytes(filtro, reportFile,
						parameterscopy, modulo);

				if (bytes != null) {
					reporteDao.ponerReporteMensajeListo(
							modulo,
							filtro.getUsuarioid(),
							rb3.getString("reportes.PathReportes")
									+ filtro.getNombrereportezip() + "", "zip",
							"" + filtro.getNombrereportezip(),
							"ReporteActualizarListo");
					reporteDao.ponerArchivo(filtro.getUsuarioid(), modulo,
							path, bytes, filtro.getNombrereporte(), context);
					archivosalida = Ruta.get(context,
							rb3.getString("reportes.PathReporte"));
					list.add(archivosalida + filtro.getNombrereporte());// pdf
					zise = 100000;

					if (zip.ponerZip(archivosalida,
							filtro.getNombrereportezip(), zise, list)) {
						reporteDao.updateDatosReporte("1",
								filtro.getNombrereportezip(), "0",
								filtro.getUsuarioid(), "update_datos_reporte");
						reporteDao.updateDatosReporteFechaFin(
								new java.sql.Timestamp(System
										.currentTimeMillis()).toString(), "1",
								filtro.getNombrereportezip(), filtro
										.getUsuarioid(),
								"update_datos_reporte_fecha_fin");
						reporteDao.updateReporte(modulo,
								filtro.getNombrereportezip(),
								filtro.getUsuarioid(), "1", "update_reporte");
						siges.util.Logger.print(
								filtro.getUsuarioid(),
								"Se genern el Reporte:_Institucion:_"
										+ filtro.getInsitucion() + "_Usuario:_"
										+ filtro.getUsuarioid()
										+ "_NombreReporte:_"
										+ filtro.getNombrereportezip() + "", 3,
								1, this.toString());
						reporteDao.limpiarTablas(filtro.getUsuarioid());
						return true;
					}

				}
				// else{
				// System.out.println("array de bytes es null");
				// }
			} else {
				reporteDao.ponerReporteMensaje(
						"2",
						modulo,
						filtro.getUsuarioid(),
						rb3.getString("reportes.PathReportes")
								+ filtro.getNombrereportezip() + "", "zip", ""
								+ filtro.getNombrereportezip(),
						"ReporteActualizarBoletinGenerando",
						"No se encontraron registros para generar el reporte");
				// System.out
				// .println("nNo se encontraron registros para generar el reporte!");
				reporteDao.updateDatosReporte("2",
						filtro.getNombrereportezip(), "0",
						filtro.getUsuarioid(), "update_datos_reporte");
				reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
						filtro.getUsuarioid(), "2", "update_reporte");
				reporteDao.limpiarTablas(filtro.getUsuarioid());
				siges.util.Logger.print(filtro.getUsuarioid(),
						"No se encontrarnn registros para generar el reporte:_Institucion:_"
								+ filtro.getInsitucion() + "_Usuario:_"
								+ filtro.getUsuarioid() + "_NombreReporte:_"
								+ filtro.getNombrereportezip() + "", 3, 1,
						this.toString());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDao.ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("reportes.PathReportes")
							+ filtro.getNombrereportezip() + "", "zip", ""
							+ filtro.getNombrereportezip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			reporteDao.updateDatosReporte("2", filtro.getNombrereportezip(),
					"0", filtro.getUsuarioid(), "update_datos_reporte");
			reporteDao.updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(), "2", filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "update_datos_reporte_fecha_fin");
			reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			reporteDao.limpiarTablas(filtro.getUsuarioid());
			return false;
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
