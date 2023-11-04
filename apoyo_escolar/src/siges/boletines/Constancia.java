package siges.boletines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import integraciones.api.reportes.dto.ReporteParametroDto;
import siges.boletines.beans.FiltroBeanConstancias;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;

/**
 * Nombre: Constancia<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class Constancia{

	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private String uriApiReport;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle r, rb3, rbBol;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanConstancias filtro;
	private String buscarcodigo;
	private final String modulo = "7";
	private String archivopre = null;
	private String archivo = null;
	private String archivozip = null;
	// private String s;
	private String s1;
	private String buscar, buscarjasper;
	private String insertar;
	private String existeconstancia;
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
	private constancias constancias1;

	public Constancia(Cursor c, FiltroBeanConstancias f, String cont, String a,
			String apre, String azip, java.sql.Timestamp fecha, Map parameters,
			String p, String p1, File reportF, File reportF1, int nn) {
		cursor = c;
		filtro = f;
		archivopre = apre;
		archivo = a;
		archivozip = azip;
		path = p;
		path1 = p1;
		reportFile = reportF;
		reportFile1 = reportF1;
		context = cont;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle.getBundle("siges.boletines.bundle.constancias");
		rbBol = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		f2 = fecha;
		copyparameters = parameters;
		buscar = buscarjasper = insertar = existeconstancia = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		// s=rb3.getString("valor_s");
		s1 = rb3.getString("valor_s1");
	}

//	@Override
//	public void run() {
//		Object[] o = new Object[2];
//		int posicion = 1;
//		int dormir = 0;
//		String puest = "-999";
//		constancias1 = new constancias();
//
//		try {
//			Thread.sleep(5000);
//			dormir = Integer.parseInt(rb3.getString("constancias.Dormir"));
//			while (ocupado) {
//				// System.out.println(":entro " + reportFile.getPath());
//				sleep(dormir);
//			}
//			ocupado = true;
//			// System.out.println(getName()+":Entra Thread");
//			if (!procesamientoConstancia(f2, copyparameters)) {
//				// System.out
//				// .println("nNO TERMINn PROCESS... OCURRIn ERROR..MIRAR TRACE!");
//				ponerReporteMensaje("2", modulo, filtro.getUsuarioid(), rb3.getString("constancias.PathConstancias")
//								+ archivozip + "", "zip7", "" + archivozip,	"ReporteActualizarBoletinPaila", "Problema en process");
//				updateReporte(archivozip, filtro.getUsuarioid(), "2");
//				return;
//			}
//			// System.out.println(getName() + ":Sale Thread");
//		} catch (InterruptedException ex) {
//			ex.printStackTrace();
//			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
//					rb3.getString("constancias.PathConstancias") + archivozip
//							+ "", "zip7", "" + archivozip,
//					"ReporteActualizarBoletin",
//					"Ocurrin Interrupted excepcinn en el Hilo:_" + ex);
//			updateReporte(archivozip, filtro.getUsuarioid(), "2");
//			limpiarTablas(filtro.getUsuarioid());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
//					rb3.getString("constancias.PathConstancias") + archivozip
//							+ "", "zip7", "" + archivozip,
//					"ReporteActualizarBoletin",
//					"Ocurrin Interrupted excepcinn en el Hilo:_" + ex);
//			updateReporte(archivozip, filtro.getUsuarioid(), "2");
//			limpiarTablas(filtro.getUsuarioid());
//		} finally {
//			ocupado = false;
//		}
//	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean procesamientoConstancia(java.sql.Timestamp date,
			Map parameterscopy) {
		list = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Zip zip = new Zip();
		Collection<String> list = new ArrayList<String>();
		String archivosalida = null;
		Object[] o = new Object[2];
		int zise;
		int posicion = 1;
		uriApiReport = rbBol.getString("boletines_uri_api_reporte");

		try {
			limpiarTablas(filtro.getUsuarioid());
			// System.out
			// .println("nSe limpiaron las tablas antes de ser llenadas!");
			updateReporte(archivozip, filtro.getUsuarioid(), "0");
			ponerReporteMensajeListo(modulo, filtro.getUsuarioid(),	rb3.getString("constancias.PathConstancias") + archivozip + "", "zip7", "" + archivozip, "ReporteActualizarGenerando");

			if (!preparedstatementsconstancias(filtro)) {
				// System.out
				// .println("nNO TERMINn PREPAREDSTATEMENTS... OCURRIn ERROR..MIRAR TRACE!");
				return false;
			}
			con = cursor.getConnection();
			/* Consulta para ejecutar el/los jasper */
			pst = con.prepareStatement(rb3.getString("constancia_est_actual"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			rs = pst.executeQuery();
			archivosalida = Ruta.get(context, r.getString("boletines.PathConstancia"));
			list.add(archivosalida + archivo);
			zise = 100000;				
					
			if (rs.next()) {
				rs.close();
				pst.close();
				long consecutivoConsultaExterna = this.getConsecutivoConsultasExternas();
				this.insertarConsultasExternas(consecutivoConsultaExterna,"", "", "", "CON");
				String pinConsultaExterna = "CON"+consecutivoConsultaExterna;
				parameterscopy.put("PINCONSULTAEXTERNA", pinConsultaExterna);
				
				
				if ((parameterscopy != null)&& (!parameterscopy.values().equals("0"))&& (con != null)) {		
					/* consumo API para la generación del reporte */
					try {
						final URL url = new URL(uriApiReport);// url API
						// report
						final java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
						conn.setDoOutput(true);
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
						conn.setRequestProperty("Accept", "application/json");
						OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
						ObjectMapper objectMapper = new ObjectMapper();
						ReporteParametroDto param = new ReporteParametroDto();
						
						param.setParams(parameterscopy);// parametros para
						// el reporte
						param.setTopic(2);// topic Constancias
						param.setTemplate("encabezado_constancia");
						param.setFormat("pdf");
						param.setFileName(archivo);						
						param.setReportId(Integer.parseInt(String.valueOf(filtro.getUsuarioid())));
						
						param.setModulo(modulo);
						param.setPath(path);
						param.setNombrePDF(archivo);
						param.setArchivoSalida(archivosalida);
						param.setNombreZIP(archivozip);
						param.setZise(zise);
						param.setList(list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining("@", "{", "}")));
						param.setBolDAO(null);//objectMapper.writeValueAsString(bolDAO.toString()));
						param.setReporteVO(null);
						param.setReporte(null);
						param.setContext(context);
						param.setConsecutivoConsultaExterna(consecutivoConsultaExterna);
						
						String json = objectMapper.writeValueAsString(param);
						System.out.println(json);
						
						outputStreamWriter.write(json);
						outputStreamWriter.flush();
						outputStreamWriter.close();
						// Obtener respuesta de la API
						int responseCode = conn.getResponseCode();
						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						
						String line;
						StringBuilder response = new StringBuilder();
						while ((line = reader.readLine()) != null) {
							response.append(line);
						}
						reader.close();
						// Procesar la respuesta
						if (responseCode == HttpURLConnection.HTTP_OK) {
							System.out.println("Respuesta de la API:" + response.toString());						
							return true; // reporte en topic para ser generado de forma asincrona 
						} else {
							System.out.println("Error al llamar a la API. Código de respuesta:" + responseCode);
						}
						// Cerrar conexión
						conn.disconnect();
						return true;
						
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}

				ponerReporteMensajeListo(modulo, filtro.getUsuarioid(),	rb3.getString("constancias.PathConstancias") + archivozip + "", "zip7", "" + archivozip, "ReporteActualizarListo");
				//ponerArchivo(modulo, path, bytes, archivo);				
				/* fin consumo API */
//				if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
//					updateReporte(archivozip, filtro.getUsuarioid(), "1");
//					this.updateConsultasExternas(consecutivoConsultaExterna, archivosalida, archivozip, "zip", "CON");
//					limpiarTablas(filtro.getUsuarioid());
//					return true;
//				}
			}
			}else {
				rs.close();
				pst.close();
				pst = con.prepareStatement(rb3.getString("constancia_est_graduado"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
				rs = pst.executeQuery();

				if (rs.next()) {
					rs.close();
					pst.close();
					// System.out
					// .println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DEL ESTUDIANTE GRADUADOnnnnnnnnnnnnnnnnnnnnn");
					long consecutivoConsultaExterna = this.getConsecutivoConsultasExternas();
					this.insertarConsultasExternas(consecutivoConsultaExterna,"", "", "", "CON");
					String pinConsultaExterna = "CON"+consecutivoConsultaExterna;
					parameterscopy.put("PINCONSULTAEXTERNA", pinConsultaExterna);
					zise = 100000;
					if ((parameterscopy != null)&& (!parameterscopy.values().equals("0"))&& (con != null)) {
						/* consumo API para la generación del reporte */
						try {
							final URL url = new URL(uriApiReport);// url API
							// report
							final java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
							conn.setDoOutput(true);
							conn.setRequestMethod("POST");
							conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
							conn.setRequestProperty("Accept", "application/json");
							OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
							ObjectMapper objectMapper = new ObjectMapper();
							ReporteParametroDto param = new ReporteParametroDto();
							
							param.setParams(parameterscopy);// parametros para
							// el reporte
							param.setTopic(2);// topic Constancias
							param.setTemplate("encabezado_constancia_graduado");
							param.setFormat("pdf");
							param.setFileName(archivo);						
							param.setReportId(Integer.parseInt(String.valueOf(filtro.getUsuarioid())));
							
							param.setModulo(modulo);
							param.setPath(path);
							param.setNombrePDF(archivo);
							param.setArchivoSalida(archivosalida);
							param.setNombreZIP(archivozip);
							param.setZise(zise);
							param.setList(list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining("@", "{", "}")));
							param.setBolDAO(null);//objectMapper.writeValueAsString(bolDAO.toString()));
							param.setReporteVO(null);
							param.setReporte(null);
							param.setContext(context);
							param.setConsecutivoConsultaExterna(consecutivoConsultaExterna);
							
							String json = objectMapper.writeValueAsString(param);
							System.out.println(json);
							
							outputStreamWriter.write(json);
							outputStreamWriter.flush();
							outputStreamWriter.close();
							// Obtener respuesta de la API
							int responseCode = conn.getResponseCode();
							BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							
							String line;
							StringBuilder response = new StringBuilder();
							while ((line = reader.readLine()) != null) {
								response.append(line);
							}
							reader.close();
							// Procesar la respuesta
							if (responseCode == HttpURLConnection.HTTP_OK) {
								System.out.println("Respuesta de la API:" + response.toString());						
								return true; // reporte en topic para ser generado de forma asincrona 
							} else {
								System.out.println("Error al llamar a la API. Código de respuesta:" + responseCode);
							}
							// Cerrar conexión
							conn.disconnect();
							return true;
							
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}

					}
					ponerReporteMensajeListo(modulo, filtro.getUsuarioid(),
							rb3.getString("constancias.PathConstancias")
									+ archivozip + "", "zip7", "" + archivozip,
							"ReporteActualizarListo");
					//ponerArchivo(modulo, path, bytes1, archivopre);
//					archivosalida = Ruta.get(context,
//							r.getString("boletines.PathConstancia"));
//					list.add(archivosalida + archivopre);
//					
//
//					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
//						updateReporte(archivozip, filtro.getUsuarioid(), "1");
//						this.updateConsultasExternas(consecutivoConsultaExterna, archivosalida, archivozip, "zip", "CON");
//						limpiarTablas(filtro.getUsuarioid());
//						return true;
//					}
				} else {
					rs.close();
					pst.close();
					ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
							rb3.getString("constancias.PathConstancias")
									+ archivozip + "", "zip7", "" + archivozip,
							"ReporteActualizarBoletinGenerando",
							"No se encontraron registros para generar la constancia!");
					// System.out
					// .println("No se encontraron registros para generar la constancia!");
					updateReporte(archivozip, filtro.getUsuarioid(), "2");
					limpiarTablas(filtro.getUsuarioid());
					return true;
				}
			}
		} catch (InternalErrorException e) {
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("constancias.PathConstancias") + archivozip
							+ "", "zip7", "" + archivozip,
					"ReporteActualizarBoletinGenerando",
					"Ocurrio una excepcion interna:_" + e);
			updateReporte(archivozip, filtro.getUsuarioid(), "2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (SQLException e) {
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("constancias.PathConstancias") + archivozip
							+ "", "zip7", "" + archivozip,
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion SQL:_" + e);
			updateReporte(archivozip, filtro.getUsuarioid(), "2");
			limpiarTablas(filtro.getUsuarioid());
			return false;
		} catch (Exception e) {
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("constancias.PathConstancias") + archivozip
							+ "", "zip7", "" + archivozip,
					"ReporteActualizarBoletinGenerando", "Ocurrio excepcion:_"
							+ e);
			updateReporte(archivozip, filtro.getUsuarioid(), "2");
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
	}
	
	
	
	public void insertarConsultasExternas(long consecutivoConsultaExterna ,String rutaArchivo, String nombreArchivo, String extensionArchivo, String tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			
			pst = con.prepareStatement(rb3.getString("insertar_consulta_externa"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, tipo);
			pst.setLong(posicion++, consecutivoConsultaExterna);
			pst.setString(posicion++, rutaArchivo);
			pst.setString(posicion++, nombreArchivo);
			pst.setString(posicion++, extensionArchivo);
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
	 * Actualiza un registro de la tabla CONSULTAS_EXTERNAS
	 * 
	 **/
	public void updateConsultasExternas(long consecutivoConsultaExterna, String rutaArchivo, String nombreArchivo, String extensionArchivo, String tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("actualizar_consulta_externa"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, tipo);
			pst.setString(posicion++, rutaArchivo);
			pst.setString(posicion++, nombreArchivo);
			pst.setString(posicion++, extensionArchivo);
			pst.setLong(posicion++, consecutivoConsultaExterna);
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
	 * Obtiene el proximo consecutivo de la tabla CONSULTAS_EXTERNAS
	 * 
	 **/
	public long getConsecutivoConsultasExternas() {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		ResultSet rs = null;
		long consecutivoConsultaExterna = 0;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("consecutivo_consulta_externa"));
			pst.clearParameters();
			rs = pst.executeQuery();
			if (rs.next())
				consecutivoConsultaExterna = rs.getLong(1);
			rs.close();
			pst.close();
			
			
		} catch (InternalErrorException e) {
			e.printStackTrace();
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
		return consecutivoConsultaExterna;
	}
	

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
	public boolean preparedstatementsconstancias(FiltroBeanConstancias filtro) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			// System.out.println("INSTITUCION: " + filtro.getInstitucionid());
			// System.out.println("SEDE: " + filtro.getSede());
			// System.out.println("JORNADA: " + filtro.getJornada());
			// System.out.println("METODOLOGIA: " + filtro.getMetodologia());
			// System.out.println("GRADO: " + filtro.getGrado());
			// System.out.println("GRUPO: " + filtro.getGrupo());
			// System.out.println("COMBO ESTUDIANTE: " +
			// filtro.getEstudiante());
			// System.out.println("AnO: " + filtro.getAno().trim());
			// System.out.println("CEDULA ESTUDIANTE: " +
			// filtro.getId().trim());
			// System.out.println("MOTIVO: " + filtro.getMotivo().trim());
			// System.out.println("AnO ACTUAL: " +
			// filtro.getAno_actual().trim());

			cstmt = con
					.prepareCall("{call PK_CONSTANCIAS.prepareds_constancias(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, Long.parseLong(filtro.getUsuarioid()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getInstitucionid()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getAno().trim()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getSede()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			cstmt.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			cstmt.setString(posicion++, filtro.getEstudiante());
			cstmt.setString(posicion++, filtro.getId().trim());
			cstmt.setString(posicion++, filtro.getMotivo().trim());
			cstmt.setLong(posicion++,
					Long.parseLong(filtro.getAno_actual().trim()));
			// System.out
			// .println("Inicia procedimiento prepareds_constancias.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out
			// .println("Fin procedimiento prepareds_constancias.. Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, filtro.getUsuarioid(),
					rb3.getString("constancias.PathConstancias") + archivozip
							+ "", "zip7", "" + archivozip,
					"ReporteActualizarBoletinPaila",
					"Ocurrin excepcinn prepareds:_" + e);
			updateReporte(archivozip, filtro.getUsuarioid(), "2");
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
			// /con=cursor.getConnection();
			String archivosalida = Ruta.get(context,
					r.getString("boletines.PathConstancia"));
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
		}

		finally {
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
					.prepareCall("{call PK_CONSTANCIAS.constancias_borrar(?)}");
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
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/
	/*
	 * public boolean generado(){ Connection con=null; PreparedStatement
	 * pst=null; ResultSet rs=null; int posicion=1; String act=null;
	 * 
	 * try{ con=cursor.getConnection();
	 * pst=con.prepareStatement(rb3.getString("activo")); posicion=1;
	 * pst.clearParameters(); pst.setString(posicion++,(filtro.getUsuarioid()));
	 * pst.setString(posicion++,modulo);
	 * System.out.println("archivozip: "+archivozip);
	 * pst.setString(posicion++,(archivozip)); rs=pst.executeQuery();
	 * if(rs.next()) return true;//se terminn de generar el reporte else return
	 * false;//ann no se ha generado } catch(Exception e){ e.printStackTrace();
	 * }finally{ try{ OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(pst);
	 * OperacionesGenerales.closeConnection(con); }catch(Exception e){} } return
	 * true; }
	 */

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
