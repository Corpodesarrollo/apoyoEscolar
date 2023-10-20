package siges.boletines;

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

import siges.boletines.beans.FiltroBeanCertificados;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;

/**
 * Nombre: Certificado<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class Certificado_ extends Thread {

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
	private FiltroBeanCertificados filtro;
	private String buscarcodigo;
	private final String modulo = "4";
	private String s;
	private String s1;
	private String archivo, archivopre, archivozip;
	private String buscar, buscarjasper;
	private String insertar;
	private String existecertificado;
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
	private File escudo;
	private String path, path1, path2;
	private String context;
	private String codigoestu;
	private certificados certificados1;
	private String vigencia;

	// Constructor de la clase
	public Certificado_(Cursor c, String cont, String p, String p1, String p2,
			File reportF, File reportF1, int nn) {
		super("hilo_certificado-" + nn);
		cursor = c;
		path = p;
		path1 = p1;
		path2 = p2;
		reportFile = reportF;
		reportFile1 = reportF1;
		context = cont;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle.getBundle("preparedstatementscertificados");
		buscar = buscarjasper = insertar = existecertificado = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		s = rb3.getString("valor_s");
		s1 = rb3.getString("valor_s1");
	}

	public void run() {
		Object[] o = new Object[2];
		int posicion = 1;
		String[][] array = null;
		int dormir = 0;
		String cola = null;
		String puest = "-999";
		certificados1 = new certificados();
		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rb3.getString("certificados.Dormir"));
			while (ocupado) {
				// System.out.println(getName()+":Espera Thread Certificado");
				sleep(dormir);
			}
			ocupado = true;
			// System.out.println(getName() + ":Entra Thread Certificado");
			while (true) {
				if (!activo()) {// es porque en la tabla vale 0
				// System.out.println("**ESTA APAGADO HILO - CERTIFICADO**");
					Thread.sleep(dormir);
					continue;
				}
				// System.out.println("**ESTA PRENDIDO HILO - CERTIFICADO**");
				array = certificado_Generar();
				if (array != null) {
					// System.out.println("**ARRAY DIFERENTE DE NULL**");
					for (int i = 0; i < array.length; i++) {
						filtro = new FiltroBeanCertificados();
						// llenar el bean
						filtro.setInstitucion(array[i][0]);
						filtro.setNominstitucion(array[i][1]);
						filtro.setSede(array[i][2]);
						filtro.setJornada(array[i][3]);
						filtro.setMetodologia(array[i][4]);
						filtro.setGrado(array[i][5]);
						filtro.setGrupo(array[i][6]);
						filtro.setAno(array[i][7]);
						filtro.setId(array[i][8]);
						filtro.setUsuarioid(array[i][10]);
						filtro.setOrden(array[i][11]);
						filtro.setNombrecertificadozip(array[i][12]);
						filtro.setNombrecertificado(array[i][13]);
						filtro.setNombrecertificadopre(array[i][14]);
						filtro.setInstituciondane(array[i][15]);
						filtro.setNomsede(array[i][16]);
						parameters = new HashMap();
						parameters.put("usuario", ((String) filtro
								.getUsuarioid().trim()));
						parameters.put("detalle_certificado",
								path + rb3.getString("subrep"));
						parameters.put("LogoCundinamarca",
								path1 + rb3.getString("imagen"));
						/* PARAMETROS PARA EL JASPER DE PREESCOLAR */
						parameters.put("detalle_certificado_preescolar", path
								+ rb3.getString("reppre1"));
						escudo = new File(path2 + "e"
								+ filtro.getInstituciondane().trim() + ".gif");
						// System.out.println("escudo: " + escudo);
						if (escudo.exists())
							parameters.put("LogoInstitucion", path2 + "e"
									+ filtro.getInstituciondane().trim()
									+ ".gif");
						// else
						// parameters.put("LogoInstitucion",path1+rb3.getString("imagen"));

						// System.out.println("***VALORES DE PARAMETROS***"
						// + parameters.values());

						if (!procesamientoCertificado(filtro, parameters)) {
							// System.out
							// .println("nNO TERMINn PROCESS... OCURRIn ERROR..MIRAR TRACE!");
							ponerReporteMensaje(
									"2",
									modulo,
									filtro.getUsuarioid(),
									rb3.getString("certificados.PathCertificados")
											+ filtro.getNombrecertificadozip()
											+ "", "zip6",
									"" + filtro.getNombrecertificadozip(),
									"ReporteActualizarBoletinPaila",
									"Problema en process");
							updateDatosCertificado("2",
									filtro.getNombrecertificadozip(), "0",
									filtro.getUsuarioid());
							updateReporte(filtro.getNombrecertificadozip(),
									filtro.getUsuarioid(), "2");
							continue;
						}

						if (!updatePuestoCertificado(puest,
								filtro.getNombrecertificadozip(),
								filtro.getUsuarioid(),
								"update_puesto_certificado_2")) {
							// System.out
							// .println(" **NO Se actualizn el puesto del reporte en Datos_Certificado**");
							continue;
						}
						if (!update_cola_certificados()) {
							// System.out
							// .println(" NO Se actualizn la lista de los reportes en cola");
							continue;
						}
						// System.out.println(getName() +
						// ":Sale While Boletines");
					}
				} else {
					Thread.sleep(dormir);
					// System.out.println("**Se mandaron vaciar las tablas ya que no hay certificados por generar**");
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
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletin",
					"Ocurrin Interrupted excepcinn en el Hilo:_" + ex);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "-1",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
			limpiarTablas(filtro.getUsuarioid());
		} catch (Exception ex) {
			ex.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en el Hilo:_" + ex);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "-1",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
			limpiarTablas(filtro.getUsuarioid());
		} finally {
			ocupado = false;
		}
	}

	public String[][] certificado_Generar() {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String[][] array1 = null;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("certificado_a_generar"));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();
			// System.out.println("ejecutn certificado_a_generar");

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
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en el Hilo: " + e);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "-1",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
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

	public boolean procesamientoCertificado(FiltroBeanCertificados filtro,
			Map parameterscopy) {

		parameterscopy = parameters;
		list = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Zip zip = new Zip();
		Collection list = new ArrayList();
		String archivosalida = null;
		Object[] o = new Object[2];
		int zise;
		int posicion = 1;

		try {
			limpiarTablas(filtro.getUsuarioid());
			// System.out
			// .println("nSe limpiaron las tablas antes de ser llenadas!");
			updateDatosCertificado("0", filtro.getNombrecertificadozip(), "-1",
					filtro.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "0");
			ponerReporteMensajeListo(
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarGenerando");
			updateDatosCertificadoFechaGen(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"0", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());

			if (!preparedstatementscertificados(filtro)) {
				// System.out
				// .println("nNO TERMINn PREPAREDSTATEMENTS... OCURRIn ERROR..MIRAR TRACE!");
				return false;
			}
			con = cursor.getConnection();
			/* Consulta para ejecutar el/los jasper */
			pst = con.prepareStatement(rb3.getString("query2jasper"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			rs = pst.executeQuery();

			if (rs.next()) {
				rs.close();
				pst.close();
				// System.out
				// .println("nnnnnnnnnnnnnnnnSE DEBEN EJECUTAR LOS DOS JASPERnnnnnnnnnnnnnnnnnnnnn");
				if ((reportFile.getPath() != null)
						&& (reportFile1.getPath() != null)
						&& (parameterscopy != null)
						&& (!parameterscopy.values().equals("0"))
						&& (con != null)) {
					// System.out
					// .println("***Se mandn ejecutar el jasper de preescolar****");
					bytes1 = JasperRunManager.runReportToPdf(
							reportFile1.getPath(), parameterscopy, con);
					// System.out
					// .println("***Se mandn ejecutar el jasper de los demns grados****");
					bytes = JasperRunManager.runReportToPdf(
							reportFile.getPath(), parameterscopy, con);
				}
				ponerReporteMensajeListo(
						modulo,
						filtro.getUsuarioid(),
						rb3.getString("certificados.PathCertificados")
								+ filtro.getNombrecertificadozip() + "",
						"zip6", "" + filtro.getNombrecertificadozip(),
						"ReporteActualizarListo");
				ponerArchivo(modulo, path, bytes1,
						filtro.getNombrecertificadopre());
				ponerArchivo(modulo, path, bytes, filtro.getNombrecertificado());
				archivosalida = Ruta.get(context,
						r.getString("boletines.PathCertificado"));

				list.add(archivosalida + filtro.getNombrecertificadopre());
				list.add(archivosalida + filtro.getNombrecertificado());
				zise = 100000;

				if (zip.ponerZip(archivosalida,
						filtro.getNombrecertificadozip(), zise, list)) {
					updateDatosCertificado("1",
							filtro.getNombrecertificadozip(), "0",
							filtro.getUsuarioid());
					updateDatosCertificadoFechaFin(new java.sql.Timestamp(
							System.currentTimeMillis()).toString(), "1",
							filtro.getNombrecertificadozip(),
							filtro.getUsuarioid());
					updateReporte(filtro.getNombrecertificadozip(),
							filtro.getUsuarioid(), "1");
					limpiarTablas(filtro.getUsuarioid());
					return true;
				}
			}// fin de rs
			else {
				rs.close();
				pst.close();
				pst = con.prepareStatement(rb3.getString("queryjasperpre"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
				rs = pst.executeQuery();

				if (rs.next()) {
					rs.close();
					pst.close();
					// System.out
					// .println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE PREESCOLAR nNICAMENTEnnnnnnnnnnnnnnnnnnnnn");
					if ((reportFile1.getPath() != null)
							&& (parameterscopy != null)
							&& (!parameterscopy.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("***Se mandn ejecutar el jasper de preescolar****");
						bytes1 = JasperRunManager.runReportToPdf(
								reportFile1.getPath(), parameterscopy, con);
					}
					ponerReporteMensajeListo(modulo, filtro.getUsuarioid(),
							rb3.getString("certificados.PathCertificados")
									+ filtro.getNombrecertificadozip() + "",
							"zip6", "" + filtro.getNombrecertificadozip(),
							"ReporteActualizarListo");
					ponerArchivo(modulo, path, bytes1,
							filtro.getNombrecertificadopre());
					archivosalida = Ruta.get(context,
							r.getString("boletines.PathCertificado"));
					list.add(archivosalida + filtro.getNombrecertificadopre());
					zise = 100000;
					if (zip.ponerZip(archivosalida,
							filtro.getNombrecertificadozip(), zise, list)) {
						updateDatosCertificado("1",
								filtro.getNombrecertificadozip(), "0",
								filtro.getUsuarioid());
						updateDatosCertificadoFechaFin(new java.sql.Timestamp(
								System.currentTimeMillis()).toString(), "1",
								filtro.getNombrecertificadozip(),
								filtro.getUsuarioid());
						updateReporte(filtro.getNombrecertificadozip(),
								filtro.getUsuarioid(), "1");
						limpiarTablas(filtro.getUsuarioid());
						return true;
					}
				}// fin de rs
				else {
					rs.close();
					pst.close();
					pst = con.prepareStatement(rb3.getString("queryjaspergig"));
					posicion = 1;
					pst.clearParameters();
					pst.setLong(posicion++,
							Long.parseLong(filtro.getUsuarioid()));
					rs = pst.executeQuery();

					if (rs.next()) {
						rs.close();
						pst.close();
						// System.out
						// .println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE LOS DEMnS GRADOS nNICAMENTEnnnnnnnnnnnnnnnnnnnnn");
						if ((reportFile.getPath() != null)
								&& (parameterscopy != null)
								&& (!parameterscopy.values().equals("0"))
								&& (con != null)) {
							// System.out
							// .println("***Se mandn ejecutar el jasper de los demns grados nNICAMENTE****");
							bytes = JasperRunManager.runReportToPdf(
									reportFile.getPath(), parameterscopy, con);
						}
						ponerReporteMensajeListo(
								modulo,
								filtro.getUsuarioid(),
								rb3.getString("certificados.PathCertificados")
										+ filtro.getNombrecertificadozip() + "",
								"zip6", "" + filtro.getNombrecertificadozip(),
								"ReporteActualizarListo");
						ponerArchivo(modulo, path, bytes,
								filtro.getNombrecertificado());
						archivosalida = Ruta.get(context,
								r.getString("boletines.PathCertificado"));
						list.add(archivosalida + filtro.getNombrecertificado());
						zise = 100000;
						if (zip.ponerZip(archivosalida,
								filtro.getNombrecertificadozip(), zise, list)) {
							updateDatosCertificado("1",
									filtro.getNombrecertificadozip(), "0",
									filtro.getUsuarioid());
							updateDatosCertificadoFechaFin(
									new java.sql.Timestamp(System
											.currentTimeMillis()).toString(),
									"1", filtro.getNombrecertificadozip(),
									filtro.getUsuarioid());
							updateReporte(filtro.getNombrecertificadozip(),
									filtro.getUsuarioid(), "1");
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
								rb3.getString("certificados.PathCertificados")
										+ filtro.getNombrecertificadozip() + "",
								"zip6", "" + filtro.getNombrecertificadozip(),
								"ReporteActualizarBoletinGenerando",
								"nNo se encontraron registros para generar el certificado!");
						// System.out
						// .println("nNo se encontraron registros para generar el certificado!");
						updateDatosCertificado("2",
								filtro.getNombrecertificadozip(), "0",
								filtro.getUsuarioid());
						updateReporte(filtro.getNombrecertificadozip(),
								filtro.getUsuarioid(), "2");
						limpiarTablas(filtro.getUsuarioid());
						return true;
					}
				}
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio una excepcion interna:_" + e);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "0",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "0",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion SQL:_" + e);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "0",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
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
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "0",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletinGenerando", "Ocurrio excepcion:_"
							+ e);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "0",
					filtro.getUsuarioid());
			updateDatosCertificadoFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", filtro.getNombrecertificadozip(), filtro
							.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
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
	public boolean preparedstatementscertificados(FiltroBeanCertificados filtro) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			vigencia = getVigencia();

			if (vigencia == null) {
				// System.out.println("*LA VIGENCIA ES NULA");
				return false;
			}
			// System.out
			// .println("preparedstatementscertificados: VIGENCIA DEL CERTIFICADO: "
			// + vigencia);

			con = cursor.getConnection();
			// System.out.println("INST: " + filtro.getInstitucion());
			// System.out.println("SEDE: " + filtro.getSede());
			// System.out.println("JORNADA: " + filtro.getJornada());
			// System.out.println("METODOLOGIA: " + filtro.getMetodologia());
			// System.out.println("GRADO: " + filtro.getGrado());
			// System.out.println("GRUPO: " + filtro.getGrupo());
			// System.out.println("AnO: " + filtro.getAno().trim());
			// System.out.println("CEDULA ESTUDIANTE : " +
			// filtro.getId().trim());
			// System.out.println("vigencia : " + vigencia);

			cstmt = con
					.prepareCall("{call PK_CERTIFICADOS.prepareds_certificados(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getAno().trim()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			cstmt.setString(posicion++, (filtro.getId().trim()));
			cstmt.setLong(posicion++, Long.parseLong(vigencia));
			cstmt.setString(posicion++, (filtro.getNominstitucion().trim()));
			cstmt.setLong(posicion++,
					Long.parseLong(filtro.getInstituciondane().trim()));
			// System.out
			// .println("Inicia procedimiento prepareds_certificados.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out
			// .println("Fin procedimiento prepareds_certificados.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje(
					"2",
					modulo,
					filtro.getUsuarioid(),
					rb3.getString("certificados.PathCertificados")
							+ filtro.getNombrecertificadozip() + "", "zip6", ""
							+ filtro.getNombrecertificadozip(),
					"ReporteActualizarBoletinPaila",
					"Ocurrin excepcinn prepareds:_" + e);
			updateDatosCertificado("2", filtro.getNombrecertificadozip(), "0",
					filtro.getUsuarioid());
			updateReporte(filtro.getNombrecertificadozip(),
					filtro.getUsuarioid(), "2");
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
	}

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
					r.getString("boletines.PathCertificado"));
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
		} catch (InternalErrorException e) {
			e.printStackTrace();
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
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	public void updateReporte(String nombrecertificado, String user,
			String estado) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("update_reporte"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nombrecertificado));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
			pst.close();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
					.prepareCall("{call PK_CERTIFICADOS.certificados_borrar(?)}");
			cstmt.setLong(posicion++, Long.parseLong(usuarioid));
			// System.out.println("Inicia borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("Fin borrar.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
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

	public void updateDatosCertificado(String nuevoestado,
			String nombreboletin, String estadoanterior, String user) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_certificado"));
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

	public void updateDatosCertificadoFechaFin(String nuevafecha,
			String estado, String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_certificado_fecha_fin"));
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

	public void updateDatosCertificadoFechaGen(String nuevafecha,
			String estado, String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_certificado_fecha_gen"));
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

	public boolean updatePuestoCertificado(String puesto,
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

	public boolean update_cola_certificados() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3
					.getString("update_puesto_certificado_1"));
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
					.prepareCall("{call PK_CERTIFICADOS.certificados_vaciar(?)}");
			cstmt.setLong(posicion++, Long.parseLong("1"));
			// System.out.println("Inicia vaciar_tablas.. Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
			cstmt.executeUpdate();
			// System.out.println("Fin vaciar_tablas.. Hora: "+new
			// java.sql.Timestamp(System.currentTimeMillis()).toString());
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
