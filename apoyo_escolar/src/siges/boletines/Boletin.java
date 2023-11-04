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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import integraciones.api.reportes.dto.ReporteParametroDto;
import net.sf.jasperreports.engine.JRException;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.boletines.vo.DatosBoletinVO;
import siges.boletines.vo.ParamsVO;
import siges.boletines.vo.ReporteVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.usuario.dao.UsuarioDAO;
import siges.util.MailDTO;
import siges.util.Mailer;
import siges.util.dao.utilDAO;

/**
 * Nombre: Boletin<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados
 * <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class Boletin{
	
	private utilDAO utilDAO;
	
	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private String uriApiReport;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle rPath, rbBol;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanReports filtro;
	private String buscarcodigo;

	private final String modulo = "3";
	private byte[] bytes;
	private byte[] bytes1;
	private Map parameters;
	private Map copyparameters;

	private File reporte1_1;
	private File reporte1_2;
	private File reporte1_con;
	private File reporte2_1;
	private File reporte2_2;
	private File reporte3_1;
	private File reporte3_2;
	private File reporte4_1;
	private File reporte4_2;

	private File escudo;
	private String path, path_logos, path_escudo;
	private String context;
	private String codigoestu;
	private File reportFileNuevoFormato;
	private String vigencia;
	private long consecRep = 0;
	private String usuarioRep = "";
	private String nombreRep = "";
	private String nombreRepPdf = "";
	private ReporteLogrosDAO bolDAO;
	int dormir = 0;
	ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();

	/* Constructor de la clase */

	//public Boletin(Cursor c, String cont, String p, String p1, String p2, File reporte1_1_, File reporte1_2_, int nn) {
	public Boletin(Cursor c, String cont, String p, String p1, String p2) {
		cursor = c;
		path = p;
		path_logos = p1;
		path_escudo = p2;

		//reporte1_1 = reporte1_1_;
		//reporte1_2 = reporte1_2_;

		context = cont;
		rPath = ResourceBundle.getBundle("path");
		rbBol = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		// s=rb3.getString("valor_s");
		// s1=rb3.getString("valor_s1");
		// buscar=buscarjasper=insertar=existeboletin=null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
		vigencia = getVigencia();
		bolDAO = new ReporteLogrosDAO(cursor);
	}

//	@Override
//	public void run() {
//		//Object[] o = new Object[2];
//		int posicion = 1;
//		String[][] array = null;
//		dormir = 0;
//		String cola = null;
//		String puest = "-999";
//		// report= new reportes();
//		try {
//			Thread.sleep(60000);
//			dormir = Integer.parseInt(rbBol.getString("boletines.Dormir"));
//			while (ocupado) {
//				sleep(dormir);
//				System.out.println("estoy durmiendo: estado ocupado");
//			}
//			ocupado = true;
//			while (true) {
//				System.out.println("estoy despierto");
//				try {
//					// VALIDACION SI EL HILO ESTA EN PARAMETRO
//					if (!bolDAO.activo()) {// es porque en la tabla vale 0
//						Thread.sleep(dormir);
//						System.out.println("estoy durmiendo nuevamnete");
//						continue;
//					}
//					procesar_solicitudes();
//				} catch (Exception e) {
//					auditoriaReporte.insertarAuditoria("BOLETIN", 1,
//							"HILO REP BOLETINES: EXECPCION WHILE INTERNO: " + e.getMessage(), 0L);
//					e.printStackTrace();
//				}
//			}
//		} catch (InterruptedException ex) {
//			System.out.println(new Date() + " - REP boletines.: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
//			auditoriaReporte.insertarAuditoria("BOLETIN", 1,
//					"HILO REP BOLETINES: EXECPCION CATCH InterruptedException: " + ex.getMessage(), 0L);
//			ex.printStackTrace();
//			// limpiarTablas(consecBol);
//		} catch (Exception ex) {
//			System.out.println(new Date() + " - REP boletines.: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
//			auditoriaReporte.insertarAuditoria("BOLETIN", 1,
//					"HILO REP BOLETINES: EXECPCION CATCH Exception: " + ex.getMessage(), 0L);
//			ex.printStackTrace();
//			// limpiarTablas(consecBol);
//		} finally {
//			ocupado = false;
//		}
//	}
	@SuppressWarnings("rawtypes")
	public boolean procesar_solicitudes() {
		ReporteVO reporte = null;
		DatosBoletinVO rep = null;
		try {
			List reportesBol = reportes_Generar();
			Iterator repBol = reportesBol.iterator();
			if (reportesBol != null && reportesBol.size() > 0) {
				System.out.println("tengo pendiente reportes para generar");
				while (repBol.hasNext()) {
					reporte = null;
					rep = (DatosBoletinVO) repBol.next();
					reporte = setReporte(rep);
					parameters = setParametros(rep);
					if (!procesar(rep, parameters, reporte)) {
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN,	rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
						bolDAO.updateReporte(reporte);
						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
						continue;
					}
					if (!update_cola_boletines()) {
						continue;
					}
				}
			}
			return true;
		} catch (InterruptedException ex) {
			System.out.println(new Date() + " - REP boletines.: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out.println("REP boletines.: PROCESAR SOLICITUDES FALLO.");
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),	rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				bolDAO.updateReporte(reporte);
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		} catch (Exception ex) {
			System.out.println(new Date() + " - REP boletines.: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out.println("REP boletines.: PROCESAR SOLICITUDES FALLO.");
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				bolDAO.updateReporte(reporte);				
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} finally {
			System.out.println("ya no esto ocupado");
			ocupado = false;
		}
	}

	public List reportes_Generar() {
		int posicion = 1;
		String[][] array1 = null;
		// FiltroReportesVO rep=null;
		List list = new ArrayList();
		try {
			list = bolDAO.getSolicitudes();
			return list;
		} catch (Exception e) {
			System.out.println("REP boletines.: ERROR LISTADO REPORTES A GENERAR, MOTIVO: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
			} catch (Exception e) {
			}
		}

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean procesar(DatosBoletinVO rep, Map parameterscopy, ReporteVO reporte) {		
		list = new ArrayList();
		Zip zip = new Zip();
		Collection<String> list = new ArrayList<String>();
		String archivosalida = null;
		//o = new Object[2];
		int zise;
		bytes = null;
		bytes1 = null;
		int boletin = 1;
		Connection con = null;
		uriApiReport = rbBol.getString("boletines_uri_api_reporte");
		try {
			bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			rep.setDABOLFECHAGEN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
			rep.setDABOLFECHAFIN("");
			bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_EJE, rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
			reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
			reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: " + rep.getDABOLCONSEC());
			bolDAO.updateReporte(reporte);
			if (rep.getDABOLCONSEC() > 0) {
				int nivelGrado = bolDAO.getNivelGrado((int) rep.getDABOLGRADO());
				if (nivelGrado == 1) {
					if (rep.getDABOLTIPOEVALPREES() == 1) {
						boletin = 0;
					}
				}
				if (boletin == 1) {
					rep.setDABOLTIPOREP(ParamsVO.TIPO_REPORTE_ASIG);
					if (!bolDAO.llenarTablas(rep, reporte)) {
						return false;
					}
				} else {
					rep.setDABOLTIPOREP(ParamsVO.TIPO_REPORTE_DIM);
					if (!bolDAO.llenarTablasDim(rep, reporte)) {
						return false;
					}
				}
				// VALIDAR SI NO HUBO ERROR EN EL PROC
				if (bolDAO.validarEstadoReporte(rep.getDABOLCONSEC()) == ParamsVO.ESTADO_REPORTE_NOGEN)
					return true;
				/* Consulta para ejecutar el/los jasper */
				rep = bolDAO.datosConv(rep, reporte);
				if (bolDAO.validarDatosReporte(rep)) {
					parameterscopy.put("CONVINST", rep.getDABOLCONVINST());
					parameterscopy.put("CONVMEN", rep.getDABOLCONVMEN());

					long consecutivoConsultaExterna = bolDAO.getConsecutivoConsultasExternas();
					bolDAO.insertarConsultasExternas(consecutivoConsultaExterna, "", "", "", "BOL");
					String pinConsultaExterna = "BOL" + consecutivoConsultaExterna;
					parameterscopy.put("PINCONSULTAEXTERNA", pinConsultaExterna);

					//File reportFile = setFileJasper(rep);
					// 28/04/2020
					System.out.println(new java.sql.Timestamp(System.currentTimeMillis()).toString() + " - Archivo jasper a ejecutar: reemplazado por API");
					
					// 07/06/2020
					parameterscopy.put("METODOLOGIA", rep.getDABOLMETODOLOGIA());
					
					//20/07/2023
					parameterscopy.put("FILE_SYSTEM", Ruta.get(context,""));// TODO imagen estudiante
					
					con = bolDAO.getConnection();
					zise = 100000;
					
					if ((parameterscopy != null) && (!parameterscopy.values().equals("0")) && (con != null)) {						
						if (con != null)
							con.close();
						
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
						reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: " + rep.getDABOLCONSEC());
						bolDAO.updateReporte(reporte);
						//ponerArchivo(modulo, path, bytes, rep.getDABOLNOMBREPDF());
						archivosalida = Ruta.get(context, rPath.getString("boletines.PathBoletin"));
						list.add(archivosalida + rep.getDABOLNOMBREPDF());
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
							param.setTopic(0);// topic Boletines
							param.setTemplate(rep.getDABOLTIPOREP() == ParamsVO.TIPO_REPORTE_ASIG?"boletin":"boletin_dim");
							param.setFormat("pdf");
							param.setFileName(rep.DABOLNOMBREPDF);						
							param.setReportId(Integer.parseInt(String.valueOf(rep.getDABOLCONSEC())));
							
							param.setModulo(modulo);
							param.setPath(path_escudo);
							param.setNombrePDF(rep.getDABOLNOMBREPDF());
							param.setArchivoSalida(archivosalida);
							param.setNombreZIP(rep.getDABOLNOMBREZIP());
							param.setZise(zise);
							param.setList(list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining("@", "{", "}")));
							param.setBolDAO(null);//objectMapper.writeValueAsString(bolDAO.toString()));
							param.setReporteVO(objectMapper.writeValueAsString(rep));
							param.setReporte(objectMapper.writeValueAsString(reporte));
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
								// Realizar operaciones adicionales con la
								// respuesta obtenida
								/* llamado al consumidor JMS */
								//JmsListenerReport listenerReport = new JmsListenerReport(cursor, Integer.parseInt(String.valueOf(rep.getDABOLCONSEC())));
								//listenerReport.starListening();
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
//					if (zip.ponerZip(archivosalida, rep.getDABOLNOMBREZIP(), zise, list)) {
//						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
//						bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_GENOK,
//								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
//						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
//						reporte.setMensaje("REPORTE GENERADO SATISFACTORIAMENTE");
//						bolDAO.updateReporte(reporte);
//						bolDAO.updateConsultasExternas(consecutivoConsultaExterna, archivosalida,
//								rep.getDABOLNOMBREZIP(), "zip", "BOL");
//
//						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
//						return true;
//					}
				} // fin de rs
				else {					
					rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
					bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),	rep.getDABOLFECHAFIN());
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
					reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
					enviarNotificaciones(rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(),2, "porque no se encontraron registros para la generaci&oacuten");
							//rep.getDABOLUSUARIO(), parameterscopy.get("INSTITUCION").toString(), 2, "porque no se encontraron registros para la generaci&oacuten;");// FIXME se quita la opcion de envio de correo, para validar la ejecución del Hilo
					bolDAO.updateReporte(reporte);
					bolDAO.limpiarTablas(rep.getDABOLCONSEC());
					return true;
				}

			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: " + e.getMessage());
				bolDAO.updateReporte(reporte);
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				enviarNotificaciones(rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		} catch (JRException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: " + e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el
				// boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				enviarNotificaciones(rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: " + e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el
				// boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				enviarNotificaciones(rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : " + e.toString());
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, ERROR MEMORIA, EXCEPCION: " + e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el
				// boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				enviarNotificaciones(rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: " + e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el
				// boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				enviarNotificaciones(rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return true;
	}// Fin de Run

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes, y luego poder ser visualizado en <BR>
	 * la lista de reportes generados <BR>
	 * 
	 * @param byte
	 *            bit
	 **/

	public void ponerArchivo(String modulo, String path, byte[] bit, String archivostatic) {

		try {

			String archivosalida = Ruta.get(context, rPath.getString("boletines.PathBoletin"));
			File f = new File(archivosalida);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(f + File.separator + archivostatic);
			CopyUtils.copy(bit, fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			bolDAO.limpiarTablas(consecRep);
			bolDAO.limpiarTablasDim(consecRep);
		} finally {
			try {
			} catch (Exception inte) {
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

	public boolean updatePuestoBoletin(String puesto, String nombreboletinzip, String user, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rbBol.getString(prepared));
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

	public boolean update_cola_boletines() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbBol.getString("update_puesto_boletin_1"));
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
	 **/

	public String getVigencia() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String vig = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbBol.getString("vigencia"));
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
			mensaje = "VERIFIQUE LA SIGUIENTE informaciÃ³n: \n\n";
		}
		mensaje = "  - " + s + "\n";
	}

	public ReporteVO setReporte(DatosBoletinVO rep) {
		ReporteVO reporte = new ReporteVO();
		if (rep != null) {
			String pathComp = rbBol.getString("boletines.PathBoletines");
			reporte.setNombre(rep.getDABOLNOMBREZIP());
			reporte.setRecurso(pathComp + rep.getDABOLNOMBREZIP());
			reporte.setModulo(Integer.parseInt(ParamsVO.REP_MODULO));
			reporte.setFecha(rep.getDABOLFECHA());
			reporte.setTipo("zip");
			reporte.setUsuario(rep.getDABOLUSUARIO());
		}
		return reporte;
	}

	public File setFileJasper(DatosBoletinVO rep) {
		File reporte = null;
		if (rep != null) {
			if (rep.getDABOLTIPOREP() == ParamsVO.TIPO_REPORTE_ASIG) {
				reporte = reporte1_1;
			} else if (rep.getDABOLTIPOREP() == ParamsVO.TIPO_REPORTE_DIM) {
				reporte = reporte1_2;
			}
		}
		return reporte;
	}
	
	private void enviarNotificaciones(String usuarioId, String institucion,long instCod, long sede, long jornada, int tipoNotificacion, String detalleError) throws MessagingException{
		Mailer mailer = new Mailer();
		utilDAO = new utilDAO();
		Cursor cursor = new Cursor();
		
		String[] emails = {utilDAO.getMailNotinicationReportes(usuarioId)};
		
		UsuarioDAO objDatosUsuario = new UsuarioDAO(cursor);
		String strCuerpo= objDatosUsuario.cuerpoCorreoGeneracionBoletin().replace("{nombre}", utilDAO.getPersonaFullName(usuarioId)).replace("{institucion}", institucion);
		MailDTO mailDto = new MailDTO();
		mailDto.setEmails(emails);
		mailDto.setSubject("Generación de Reportes");
		
		switch (tipoNotificacion) {
			case 1:// generacion reporte
				mailDto.setContent(strCuerpo.replace("{estado}", detalleError));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			case 2:// no generado
				mailDto.setContent(strCuerpo.replace("{estado}", "no pudo ser generado, "+detalleError));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;	
	
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map setParametros(DatosBoletinVO bdt) {
		Map parametros = new HashMap();
		String archivoEscudo = bolDAO.getPathEscudo(new Long(bdt.getDABOLINST()));
		if (bdt != null) {
			parametros = new HashMap();			
			parametros.put("usuario", bdt.getDABOLUSUARIO());
			
			escudo = new File(archivoEscudo);			
			
			if (escudo.exists()) {				
				parametros.put("ESCUDO_COLEGIO", archivoEscudo);
				parametros.put("ESCUDO_COLEGIO_EXISTE", new Integer(1));
			} else 
				parametros.put("ESCUDO_COLEGIO_EXISTE", new Integer(0));							
			
		
			// System.out.println("Escudo sed " + path_escudo
			// + rbBol.getString("imagen"));
			parametros.put("ESCUDO_SED", path_escudo + rbBol.getString("imagen"));
			// System.out.println("periodo "
			// + new Integer((int) bdt.getDABOLPERIODO()));
			parametros.put("PERIODO", new Integer((int) bdt.getDABOLPERIODO()));

			// System.out.println("vigencia "
			// + new Integer(bdt.getDABOLVIGENCIA()));
			parametros.put("VIGENCIA", new Integer(bdt.getDABOLVIGENCIA()));

			// System.out.println("MOSTRAR DESCRIPT "
			// + new Integer(bdt.getDABOLDESCRIPTORES()));
			parametros.put("MOSTRAR_DESCRIPTOR", new Integer(bdt.getDABOLDESCRIPTORES()));

			// System.out.println("MOSTRAR LOGROS "
			// + new Integer(bdt.getDABOLLOGROS()));

			parametros.put("MOSTRAR_LOGROS", new Integer(bdt.getDABOLLOGROS()));

			// System.out.println("MOSTRAR LOGROS P "
			// + new Integer(bdt.getDABOLLOGROPEND()));
			parametros.put("MOSTRAR_LOGROS_P", new Integer(bdt.getDABOLLOGROPEND()));

			// System.out.println("MOSTRAR AREAS "
			// + new Integer(bdt.getDABOLAREA()));
			parametros.put("MOSTRAR_AREAS", new Integer(bdt.getDABOLAREA()));

			// System.out.println("MOSTRAR ASIG "
			// + new Integer(bdt.getDABOLASIG()));

			parametros.put("MOSTRAR_ASIG", new Integer(bdt.getDABOLASIG()));
			// System.out.println("consec " + new Long(bdt.getDABOLCONSEC()));
			parametros.put("CONSECBOL", new Long(bdt.getDABOLCONSEC()));
			// System.out.println("MOSTRAR_FIRMA_RECTOR "
			// + new Integer(bdt.getDABOLFIRREC()));
			parametros.put("MOSTRAR_FIRMA_RECTOR", new Integer(bdt.getDABOLFIRREC()));

			// System.out.println("MOSTRAR_FIRMA_DIR "
			// + new Integer(bdt.getDABOLFIRDIR()));
			parametros.put("MOSTRAR_FIRMA_DIRECTOR", new Integer(bdt.getDABOLFIRDIR()));

			parametros.put("NOMBRE_PER_FINAL", bdt.getDABOLNOMPERDEF());
			parametros.put("NUM_PERIODOS", new Integer(bdt.getDABOLNUMPER()));
			if (bdt.getDABOLSUBTITULO() != null && bdt.getDABOLSUBTITULO().trim().length() > 0)
				parametros.put("SUBTITULO", bdt.getDABOLSUBTITULO());
			else
				parametros.put("SUBTITULO", "LOGROS Y DECRIPTORES");
			parametros.put("RESOLUCION", bdt.getDABOLRESOLUCION());

			parametros.put("INSTITUCIONCOD", new Long(bdt.getDABOLINST()));
			parametros.put("INSTITUCION", bdt.getDABOLINSNOMBRE());
			parametros.put("SEDE", bdt.getDABOLSEDNOMBRE());
			parametros.put("JORNADA", bdt.getDABOLJORNOMBRE());
			
			parametros.put("MOSTRAR_PUESTO_ESTUDIANTE",new Integer(bdt.getDABOLPUEEST()));
			parametros.put("GRADO",bdt.getDABOLGRADO());
		}
		return parametros;
	}

}
