package siges.ruta;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.io.Zip;
import siges.ruta.dao.ReporteDAO;
import siges.ruta.vo.FiltroBeanGestVO;

/**
 * Nombre: ReporteRutaG<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ReporteRutaG extends Thread {
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
	private FiltroBeanGestVO filtro;
	private String buscarcodigo;
	private final String modulo = "30";
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
	private Map parameters;
	private File reportFile;
	private String path, path1;
	private String context;
	private String codigoestu;
	private String vigencia;
	private ReporteDAO reporteDao;
	private final String tipo = "2";
	private final int tipoG = 2;

	/* Constructor de la clase */

	public ReporteRutaG(Cursor c, String cont, String p, String p1,
			File reportF, int nn) {
		super("hilo_gestacion" + nn);
		cursor = c;
		path = p;
		path1 = p1;
		reportFile = reportF;
		context = cont;
		rb3 = ResourceBundle.getBundle("siges.ruta.reportes_ruta");
		s = rb3.getString("valor_s");
		s1 = rb3.getString("valor_s1");
		buscar = buscarjasper = insertar = existeboletin = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
		reporteDao = new ReporteDAO(cursor);
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
				System.out.println(getName() + ":Espera Thread");
				sleep(dormir);
			}
			ocupado = true;
			// System.out.println(getName()+":Entra Thread");

			while (true) {
				if (!reporteDao.activo()) {
					Thread.sleep(dormir);
					continue;
				}
				array = reporteDao.reporteGenerarG(filtro, modulo,
						"reporte_a_generar");
				if (array != null) {
					for (int i = 0; i < array.length; i++) {
						filtro = new FiltroBeanGestVO();
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
						parameters.put("usuario",
								((String) array[i][10]).trim());
						parameters.put("tipo", tipo);

						System.out
								.println("PARAMETROS: " + parameters.values());

						if (!procesoJasper(filtro, parameters)) {
							System.out
									.println("nNO TERMINn PROCESS... OCURRIn ERROR..MIRAR TRACE!");
							// reporteDao.ponerReporteMensaje("2",modulo,filtro.getUsuarioid(),rb3.getString("reportes.PathReportes")+filtro.getNombrereportezip()+"","zip",""+filtro.getNombrereportezip(),"ReporteActualizarBoletinPaila","Problema en procesoJasper");
							reporteDao.updateDatosReporte("2",
									filtro.getNombrereportezip(), "0",
									filtro.getUsuarioid(),
									"update_datos_reporte", 2);
							reporteDao.updateReporte(modulo,
									filtro.getNombrereportezip(),
									filtro.getUsuarioid(), "2",
									"update_reporte");
							continue;
						}
						if (!reporteDao.updatePuestoReporte(puest,
								filtro.getNombrereportezip(),
								filtro.getUsuarioid(),
								"update_puesto_reporte_2", tipoG)) {
							System.out
									.println(" **NO Se actualizn el puesto del reporte en DATOS_REPORTE_NUTRICION**");
							continue;
						}
						if (!reporteDao.updateColaReportes(
								"update_puesto_reporte_1", 2)) {
							System.out
									.println(" NO Se actualizn la lista de los reportes en cola");
							continue;
						}
						System.out.println(getName()
								+ ":Sale While ReporteGestacion");
					}
				} else {
					Thread.sleep(dormir);
					reporteDao.vaciarTablasG();
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
					"-1", filtro.getUsuarioid(), "update_datos_reporte", 2);
			reporteDao.updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(), "2", filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "update_datos_reporte_fecha_fin", 2);
			reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			reporteDao.limpiarTablasG(filtro.getUsuarioid());
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
					"-1", filtro.getUsuarioid(), "update_datos_reporte", 2);
			reporteDao.updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(), "2", filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "update_datos_reporte_fecha_fin", 2);
			reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			reporteDao.limpiarTablasG(filtro.getUsuarioid());
		} finally {
			ocupado = false;
		}
	}

	public boolean procesoJasper(FiltroBeanGestVO filtro, Map parameterscopy) {

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
			reporteDao.limpiarTablasG(filtro.getUsuarioid());
			System.out
					.println("nSe limpiaron las tablas antes de ser llenadas!");
			reporteDao.updateDatosReporte("0", filtro.getNombrereportezip(),
					"-1", filtro.getUsuarioid(), "update_datos_reporte", 2);
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
					filtro.getUsuarioid(), "update_datos_reporte_fecha_gen", 2);

			if (!reporteDao.callableStatementsG(filtro, vigencia, modulo)) {
				System.out
						.println("nNO TERMINn CALLABLES... OCURRIn ERROR..MIRAR TRACE!");
				return false;
			}

			if (reporteDao.ejecutarJasper(filtro.getUsuarioid(),
					"queryEjecutarjasperG")) {
				System.out.println("SE DEBE EJECUTAR EL JASPER DE GESTACION");

				if (reporteDao.getGExcel(filtro, reportFile, parameterscopy,
						modulo, context)) {
					reporteDao.ponerReporteMensajeListo(
							modulo,
							filtro.getUsuarioid(),
							rb3.getString("reportes.PathReportes")
									+ filtro.getNombrereportezip() + "", "zip",
							"" + filtro.getNombrereportezip(),
							"ReporteActualizarListo");
					archivosalida = Ruta.get(context,
							rb3.getString("reportes.PathReporte"));
					list.add(archivosalida + filtro.getNombrereporte());// xls
					zise = 100000;

					if (zip.ponerZip(archivosalida,
							filtro.getNombrereportezip(), zise, list)) {
						reporteDao.updateDatosReporte("1",
								filtro.getNombrereportezip(), "0",
								filtro.getUsuarioid(), "update_datos_reporte",
								2);
						reporteDao.updateDatosReporteFechaFin(
								new java.sql.Timestamp(System
										.currentTimeMillis()).toString(), "1",
								filtro.getNombrereportezip(), filtro
										.getUsuarioid(),
								"update_datos_reporte_fecha_fin", 2);
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
						reporteDao.limpiarTablasG(filtro.getUsuarioid());
						return true;
					} else {
						reporteDao.ponerReporteMensaje(
								"2",
								modulo,
								filtro.getUsuarioid(),
								rb3.getString("reportes.PathReportes")
										+ filtro.getNombrereportezip() + "",
								"zip", "" + filtro.getNombrereportezip(),
								"ReporteActualizarBoletinPaila",
								"Problema al hacer ZIP");
						return false;
					}
				} else {
					System.out.println("no se genero el archivo excel");
				}
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
				System.out
						.println("nNo se encontraron registros para generar el reporte!");
				reporteDao.updateDatosReporte("2",
						filtro.getNombrereportezip(), "0",
						filtro.getUsuarioid(), "update_datos_reporte", 2);
				reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
						filtro.getUsuarioid(), "2", "update_reporte");
				siges.util.Logger.print(filtro.getUsuarioid(),
						"No se encontrarnn registros para generar el reporte:_Institucion:_"
								+ filtro.getInsitucion() + "_Usuario:_"
								+ filtro.getUsuarioid() + "_NombreReporte:_"
								+ filtro.getNombrereportezip() + "", 3, 1,
						this.toString());
				reporteDao.limpiarTablasG(filtro.getUsuarioid());
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
					"0", filtro.getUsuarioid(), "update_datos_reporte", 2);
			reporteDao.updateDatosReporteFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(), "2", filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "update_datos_reporte_fecha_fin", 2);
			reporteDao.updateReporte(modulo, filtro.getNombrereportezip(),
					filtro.getUsuarioid(), "2", "update_reporte");
			siges.util.Logger.print(
					filtro.getUsuarioid(),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ filtro.getInsitucion() + "_Usuario:_"
							+ filtro.getUsuarioid() + "_NombreReporte:_"
							+ filtro.getNombrereportezip() + "", 3, 1,
					this.toString());
			reporteDao.limpiarTablasG(filtro.getUsuarioid());
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
