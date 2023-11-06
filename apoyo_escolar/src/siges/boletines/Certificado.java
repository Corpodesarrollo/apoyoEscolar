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
import siges.dao.Ruta2;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.util.dao.utilDAO;

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

public class Certificado{
	
	private utilDAO utilDAO = new utilDAO();;
	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private String uriApiReport;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle rPath, rbBol;
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanReports filtro;
	private String buscarcodigo;

	private final String modulo = "3";
	// private String s;
	// private String s1;
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
	// private PreparedStatement pst;
	// private CallableStatement cstmt;
	// private ResultSet rs;
	// private reportes report;
	private String vigencia;
	private long consecRep = 0;
	private String usuarioRep = "";
	private String nombreRep = "";
	private String nombreRepPdf = "";
	private ReporteLogrosDAO bolDAO;
	int dormir = 0;

	/* Constructor de la clase */

	//public Certificado(Cursor c, String cont, String p, String p1, String p2,File reporte1_1_, File reporte1_2_, int nn) {
	public Certificado(Cursor c, String p) {
	    rb=ResourceBundle.getBundle("siges.boletines.bundle.boletines");
	    rPath = ResourceBundle.getBundle("path");
	    rbBol = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		String contextoTotal=Ruta.get(context, rPath.getString("boletines.PathBoletin"));
	  	String path=Ruta2.get(contextoTotal,rb.getString("boletines_ruta_jaspers"));
	  	//String path_logos=Ruta.get(contextoTotal,rb.getString("boletines_logos"));
	  	path_escudo=Ruta.get(contextoTotal,rb.getString("boletines_imgs_inst"));
	  	reporte1_1=new File(path+rb.getString("certificados.reporte1_1"));	
	  	reporte1_2=new File(path+rb.getString("certificados.reporte1_2"));
			
		cursor = c;
		path = p;	

		context = contextoTotal;
		// s=rb3.getString("valor_s");
		// s1=rb3.getString("valor_s1");
		// buscar=buscarjasper=insertar=existeboletin=null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
		// vigencia=getVigencia();
		bolDAO = new ReporteLogrosDAO(cursor);
	}

//	@Override
//	public void run() {
//		Object[] o = new Object[2];
//		int posicion = 1;
//		String[][] array = null;
//		dormir = 0;
//		String cola = null;
//		String puest = "-999";
//		// report= new reportes();
//		try {
//			Thread.sleep(60000);
//			dormir = Integer.parseInt(rbBol.getString("boletines.Dormir"));
//
//			while (ocupado) {
//				System.out.println(getName() + ":Espera Thread");
//				sleep(dormir);
//			}
//
//			ocupado = true;
//			// System.out.println(getName()+":Entra Thread");
//			while (true) {
//				// VALIDACION SI EL HILO ESTA EN PARAMETRO
//				if (!bolDAO.activo()) {// es porque en la tabla vale 0
//					// System.out.println("**ESTA APAGADO**");
//					Thread.sleep(dormir);
//					continue;
//				}
//				// System.out
//				// .println("HILO REP CERTIFICADOS.: ENTRO A REVISAR SOLICITUDES, PARA ATENDERLAS");
//				procesar_solicitudes();
//
//			}
//		} catch (InterruptedException ex) {
//			System.out
//					.println(new Date()
//							+ " - HILO REP CERTIFICADOS.: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
//			ex.printStackTrace();
//			// limpiarTablas(consecBol);
//		} catch (Exception ex) {
//			System.out
//					.println(new Date()
//							+ " - HILO REP CERTIFICADOS.: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
//			ex.printStackTrace();
//			// limpiarTablas(consecBol);
//		} finally {
//			ocupado = false;
//		}
//	}

	public boolean procesar_solicitudes() {
		ReporteVO reporte = null;
		DatosBoletinVO rep = null;
		try {
			List reportesBol = reportes_Generar();
			Iterator repBol = reportesBol.iterator();
			if (reportesBol != null && reportesBol.size() > 0) {
				while (repBol.hasNext()) {
					reporte = null;
					rep = (DatosBoletinVO) repBol.next();
					reporte = setReporte(rep);
					parameters = setParametros(rep);
					if (!procesar(rep, parameters, reporte)) {
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_NOGEN,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENREACION");
						bolDAO.updateReporte(reporte);
						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
						continue;
					}
					if (!update_cola_certficados()) {
						continue;
					}
				}
			}
			return true;
		} catch (InterruptedException ex) {
			System.out
					.println(new Date()
							+ " - HILO REP CERTIFICADOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out
						.println("HILO REP CERTIFICADOS: PROCESAR SOLICITUDES FALLO.");
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;

		} catch (Exception ex) {
			System.out
					.println(new Date()
							+ " HILO REP CERTIFICADOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out
						.println("HILO REP CERTIFICADOS.: PROCESAR SOLICITUDES FALLO.");
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} finally {
			ocupado = false;
		}
	}

	public List reportes_Generar() {
		int posicion = 1;
		String[][] array1 = null;
		// FiltroReportesVO rep=null;
		List list = new ArrayList();
		try {
			list = bolDAO.getSolicitudesCertificados();
			return list;
		} catch (Exception e) {
			System.out
					.println("HILO REP CERTIFICADOS.: ERROR LISTADO REPORTES A GENERAR, MOTIVO: "
							+ e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
			} catch (Exception e) {
			}
		}

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean procesar(DatosBoletinVO rep, Map parameterscopy,
			ReporteVO reporte) {

		list = new ArrayList();
		Zip zip = new Zip();
		Collection<String> list = new ArrayList<String>();
		String archivosalida = null;
		o = new Object[2];
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
			bolDAO.updateSolicitud(rep.getDABOLCONSEC(),ParamsVO.ESTADO_REPORTE_EJE, rep.getDABOLFECHAGEN(),rep.getDABOLFECHAFIN());
			reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
			reporte.setMensaje("HILO REP CERTIFICADOS:REPORTE EN EJECUCION, CONSEC: "+ rep.getDABOLCONSEC());
			bolDAO.updateReporte(reporte);
			if (rep.getDABOLCONSEC() > 0) {
				int nivelGrado = bolDAO	.getNivelGrado((int) rep.getDABOLGRADO());
				if (nivelGrado == 1) {
					if (rep.getDABOLTIPOEVALPREES() == 1) {
						boletin = 0;
					}
				}
				if (boletin == 1) {
					rep.setDABOLTIPOREP(ParamsVO.TIPO_REPORTE_ASIG);
					if (!bolDAO.llenarTablasCertLibro(rep, reporte)) {
						return false;
					}
				} else {
					rep.setDABOLTIPOREP(ParamsVO.TIPO_REPORTE_DIM);
					if (!bolDAO.llenarTablasDimCertLibro(rep, reporte)) {
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
					
					long consecutivoConsultaExterna = this.getConsecutivoConsultasExternas();
					this.insertarConsultasExternas(consecutivoConsultaExterna,"", "", "", "CER");
					String pinConsultaExterna = "CER"+consecutivoConsultaExterna;
					parameterscopy.put("PINCONSULTAEXTERNA", pinConsultaExterna);
					
					//20/07/2023
					parameterscopy.put("FILE_SYSTEM", Ruta.get(context,""));// TODO imagen estudiante
					
					//File reportFile = setFileJasper(rep);
					System.out.println("HILO REP CERTIFICADOS JASPER: Reemplazado por API"	);
					con = bolDAO.getConnection();
					zise = 100000;
					if ((parameterscopy != null)
							&& (!parameterscopy.values().equals("0"))
							&& (con != null)) {
//						bytes = JasperRunManager.runReportToPdf(
//								reportFile.getPath(), parameterscopy, con);
					}
					if (con != null)
						con.close();
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
					reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: "
							+ rep.getDABOLCONSEC());
					bolDAO.updateReporte(reporte);
					//ponerArchivo(modulo, path, bytes, rep.getDABOLNOMBREPDF());
					archivosalida = Ruta.get(context,
							rPath.getString("boletines.PathCertificado"));
					list.add(archivosalida + rep.getDABOLNOMBREPDF());// pdf
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
						param.setTopic(1);// topic Certificados
						param.setTemplate(rep.getDABOLTIPOREP() == ParamsVO.TIPO_REPORTE_ASIG?"certificado":"certificado_dim");
						param.setFormat("pdf");
						param.setFileName(rep.DABOLNOMBREPDF);						
						param.setReportId(Integer.parseInt(String.valueOf(rep.getDABOLCONSEC())));
						
						param.setModulo(modulo);
						param.setPath(archivosalida);
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
					/* fin consumo API */
//					if (zip.ponerZip(archivosalida, rep.getDABOLNOMBREZIP(),
//							zise, list)) {
//						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
//								.currentTimeMillis()).toString());
//						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
//								ParamsVO.ESTADO_REPORTE_GENOK,
//								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
//						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
//						reporte.setMensaje("REPORTE GENERADO SATISFACTORIAMENTE");
//						bolDAO.updateReporte(reporte);
//						this.updateConsultasExternas(consecutivoConsultaExterna, archivosalida, rep.getDABOLNOMBREZIP(), "zip", "CER");
//						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
//						return true;
//					}
				}// fin de rs
				else {
					rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
							.currentTimeMillis()).toString());
					bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
							ParamsVO.ESTADO_REPORTE_NOGEN,
							rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
					reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
					bolDAO.updateReporte(reporte);
					// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
					bolDAO.limpiarTablas(rep.getDABOLCONSEC());
					// System.out.println("HILO REP CERTIFICADOS: NO SE HAY REGISTROS REPORTE");
					utilDAO.enviarNotificaciones(siges.util.dao.utilDAO.TIPO_CERTIFICADO, rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(),2, "porque no se encontraron registros para la generaci&oacuten");
					return true;
				}

			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: " + e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				utilDAO.enviarNotificaciones(siges.util.dao.utilDAO.TIPO_CERTIFICADO, rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return false;
		} catch (JRException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(), ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),	rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: " + e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				utilDAO.enviarNotificaciones(siges.util.dao.utilDAO.TIPO_CERTIFICADO, rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				utilDAO.enviarNotificaciones(siges.util.dao.utilDAO.TIPO_CERTIFICADO, rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" HILO REP CERTIFICADOS WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, ERROR MEMORIA, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				utilDAO.enviarNotificaciones(siges.util.dao.utilDAO.TIPO_CERTIFICADO, rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
			} catch (Exception e1) {
				// TODO Auto-generated catch block				
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				utilDAO.enviarNotificaciones(siges.util.dao.utilDAO.TIPO_CERTIFICADO, rep.getDABOLUSUARIO(),parameterscopy.get("INSTITUCION").toString(),rep.getDABOLINST(), rep.getDABOLSEDE(),rep.getDABOLJORNADA(), 2, "porque se presento un error, consulte con su administrador");
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
	 * Ingresa un registro en la tabla de CONSULTAS_EXTERNAS
	 * 
	 **/
	public void insertarConsultasExternas(long consecutivoConsultaExterna ,String rutaArchivo, String nombreArchivo, String extensionArchivo, String tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			
			pst = con.prepareStatement(rbBol.getString("insertar_consulta_externa"));
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
			pst = con.prepareStatement(rbBol.getString("actualizar_consulta_externa"));
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
			pst = con.prepareStatement(rbBol.getString("consecutivo_consulta_externa"));
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
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes, y luego poder ser visualizado en <BR>
	 * la lista de reportes generados <BR>
	 * 
	 * @param byte bit
	 **/

	public void ponerArchivo(String modulo, String path, byte[] bit,
			String archivostatic) {

		try {

			String archivosalida = Ruta.get(context,
					rPath.getString("boletines.PathCertificado"));
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

	public boolean updatePuestoBoletin(String puesto, String nombreboletinzip,
			String user, String prepared) {
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

	public boolean update_cola_certficados() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbBol
					.getString("update_puesto_certficado_1"));
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
			String pathComp = rbBol.getString("boletines.PathCertificados");
			reporte.setNombre(rep.getDABOLNOMBREZIP());
			reporte.setRecurso(pathComp + rep.getDABOLNOMBREZIP());
			reporte.setModulo(Integer.parseInt(ParamsVO.REP_MODULO_CERT));
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

			parametros.put("ESCUDO_SED", path_escudo + rbBol.getString("imagen"));

			parametros.put("PERIODO", new Integer((int) bdt.getDABOLPERIODO()));
			parametros.put("VIGENCIA", new Integer(bdt.getDABOLVIGENCIA()));

			/*
			 * parametros.put("MOSTRAR_DESCRIPTOR",new
			 * Integer(bdt.getDABOLDESCRIPTORES()));
			 * parametros.put("MOSTRAR_LOGROS",new
			 * Integer(bdt.getDABOLLOGROS()));
			 * parametros.put("MOSTRAR_LOGROS_P",new
			 * Integer(bdt.getDABOLTOTLOGROS()));
			 * parametros.put("MOSTRAR_AREAS",new Integer(bdt.getDABOLAREA()));
			 * parametros.put("MOSTRAR_ASIG",new Integer(bdt.getDABOLASIG()));
			 */
			parametros.put("CONSECBOL", new Long(bdt.getDABOLCONSEC()));

			parametros.put("NOMBRE_PER_FINAL", bdt.getDABOLNOMPERDEF());
			parametros.put("NUM_PERIODOS", new Integer(bdt.getDABOLNUMPER()));
			// parameters.put("SUBTITULO",bdt.getDABOLSUBTITULO());
			// parametros.put("SUBTITULO","LOGROS Y DECRIPTORES");
			parametros.put("RESOLUCION", bdt.getDABOLRESOLUCION());
			parametros.put("INSTITUCIONCOD", new Long(bdt.getDABOLINST()));
			parametros.put("INSTITUCION", bdt.getDABOLINSNOMBRE());
			parametros.put("SEDE", bdt.getDABOLSEDNOMBRE());
			parametros.put("JORNADA", bdt.getDABOLJORNOMBRE());
			parametros.put("DANE", bdt.getDANE12());
		}
		return parametros;
	}

}
