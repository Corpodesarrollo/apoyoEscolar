package siges.gestionAdministrativa.repResultadosAca.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import siges.boletines.ControllerAuditoriaReporte;
import siges.boletines.vo.DatosBoletinVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.repResultadosAca.dao.RepResultadosAcaDAO;
import siges.gestionAdministrativa.repResultadosAca.vo.ParamsVO;
import siges.gestionAdministrativa.repResultadosAca.vo.ReporteVO;
import siges.io.Zip;

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

public class RepResultadosAca extends Thread {
	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle rPath, rbRep;

	private Map parameters;

	private File reporte1;
	private File reporte2;
	private File reporte3;

	private File escudo;
	private String path_escudo, path2;
	private String context;
	private long consecRep = 0;
	private RepResultadosAcaDAO bolDAO;
	int dormir = 0;
	ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();

	/* Constructor de la clase */

	public RepResultadosAca(Cursor c, String cont, String p, String p1,
			String p2, File reporte1_1_, File reporte1_2_, File reporte1_3_,
			int nn) {
		super("HILO_REP_RESULTADOS: " + nn);
		cursor = c;
		path_escudo = p1;
		path2 = p2;

		reporte1 = reporte1_1_;
		reporte2 = reporte1_2_;
		reporte3 = reporte1_3_;

		context = cont;
		rPath = ResourceBundle.getBundle("path");
		rbRep = ResourceBundle
				.getBundle("siges.gestionAdministrativa.repResultadosAca.bundle.repResultadosAca");
		err = false;
		bolDAO = new RepResultadosAcaDAO(cursor);
	}

	@Override
	public void run() {
		dormir = 0;
		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rbRep.getString("rep.Dormir"));

			while (ocupado) {
				sleep(dormir);
			}

			ocupado = true;
			while (true) {
				try {
					procesar_solicitudes();
				}catch (Exception e) {
					auditoriaReporte.insertarAuditoria("RESULTADOS ACADEMICOS", 0, "HILO REP RESULTADOS ACADEMICOS.: EXECPCION WHILE INTERNO: "+e.getMessage(),0L);
				}
			}
		} catch (InterruptedException ex) {
			auditoriaReporte.insertarAuditoria("RESULTADOS ACADEMICOS", 0, "HILO REP RESULTADOS ACADEMICOS.: EXECPCION WHILE InterruptedException: "+ex.getMessage(),0L);
			ex.printStackTrace();
		} catch (Exception ex) {
			auditoriaReporte.insertarAuditoria("RESULTADOS ACADEMICOS", 0, "HILO REP RESULTADOS ACADEMICOS.: EXECPCION WHILE Exception: "+ex.getMessage(),0L);
			ex.printStackTrace();
		} finally {
			ocupado = false;
		}
	}

	public boolean procesar_solicitudes() {
		ReporteVO reporte = null;
		DatosBoletinVO rep = null;
		
		try {
			DatosBoletinVO reportesBol = null;
			
			/**
			 * Si hay reportes por generar...
			 * Antes de solicitar la generacinn de mns reportes a ORACLE
			 * primero verificamos que no existan mns reportes en estado 0 (Generando los datos en tablas temporales)
			 */
			if(!bolDAO.solicitudesPendientes()){
				reportesBol = reportes_Generar(); //Archivos pendientes por generar
			} 
			
			
			
			try {
				// Se procesan con el paquete de BD los reportes pendientes de procesar.
				/**
				 * Solicitamos a ORACLE la genereacinn de datos de un nuevo Reporte de resultados
				 */
				if (reportesBol != null) {
//					System.out.println("HILO REP RESULTADOS.: REPORTE A GENERAR CONSEC: "+ reportesBol);
					reporte = null;
					rep = reportesBol;
					reporte = setReporte(rep);
					parameters = setParametros(rep);
					System.out.println("PARAMETROS: "+parameters.values());
					if (!procesar(rep, parameters, reporte)) {
//						System.out.println("HILO REP RESULTADOS: PROCESAR SOLICITUDES FALLO.");
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_REVISADO,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENREACION");
						bolDAO.updateReporte(reporte);
					}
					if (!update_cola_certficados()) {
						System.out.println("HILO REP RESULTADOS: NO Se actualizn la lista de los reportes en cola");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			

			try {
				// Se revisan los reportes que ya terminaron en la base de datos por institucinn y se trata de generar el reporte en excel.
				String archivoColegio = bolDAO.getArchivoListoCol();
				if(archivoColegio != null && archivoColegio.length() > 0) {
					long start = System.currentTimeMillis();
					System.out.println("CONSTRUYENDO ARCHIVO DE COLEGIO (JASPER): " + archivoColegio);
					reporte = null;
					rep = bolDAO.getArchivoListoByConsecCol(Long.parseLong(archivoColegio));
					reporte = setReporte(rep);
					parameters = setParametros(rep);
					if(reporte.getEstado() == 2){ //No hubo datos
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_NOGEN,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACInN");
						bolDAO.updateReporte(reporte);
						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
						update_cola_certficados();
					}
					else if (!procesarCol(rep, parameters, reporte)) {
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_NOGEN,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACInN");
						bolDAO.updateReporte(reporte);
						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
						update_cola_certficados();
					} 
					long end = System.currentTimeMillis();
					System.out.println("TERMINO ARCHIVO DE COLEGIO (JASPER): " + archivoColegio + " El tiempo en millis: " + (end-start));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			
			/**
			 * Si hay datos listos para crear los reportes de la localidad
			 */
			String archivo = bolDAO.getArchivoListos(); //Archivos listos por generar por localidad
			if(archivo != null && archivo.length() > 0) 
			{
				System.out.println("CONSTRUYENDO ARCHIVO CSV POR LOCALIDAD: " + archivo);
				String[] consecutivos = archivo.split(";");
				BufferedWriter out2 = null;
				int band = 0;
				boolean continuar = true;
				for(int ii = 0 ; ii < consecutivos.length && !consecutivos[ii].equals("") && continuar; ii++){				
					List a = bolDAO.getArchivoListoByConsec(Long.parseLong(consecutivos[ii])); //Archivos pendientes por generar
					Iterator arcListos = a.iterator(); 
					while (arcListos.hasNext() && continuar) {
						reporte = null;
						rep = (DatosBoletinVO) arcListos.next();
						reporte = setReporte(rep);
						/*if(rep.getDABOLORDEN().equals("1") && rep.getDABOLESTACT() == 2){
						//if (bolDAO.validarDatosReporte(rep)) {
							bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
							ParamsVO.ESTADO_REPORTE_NOGEN,
							rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
							rep = null;
							continuar = false;
						} else{*/
							/**
							 * Solicitaron reporte de todos los colegios de la localidad
							 * Genere archivo CSV
							 */
							String archivosalida = "";
							if(rep.getAlllocalidad() == 1){										  
								  try {
									  if(band == 0){							  
									  	String fileName = rep.getDABOLNOMBREZIP();
									  	fileName = fileName.replaceAll(".zip", ".txt");
									  	archivosalida = Ruta.get(context,
												rPath.getString("resultados.PathResultados"));
									  	File f = new File(archivosalida);
										if (!f.exists())
											FileUtils.forceMkdir(f);
										//out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivosalida+fileName),"UTF-8"));
										//out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivosalida+fileName),"ISO-8859-1"));
										out2 = new BufferedWriter(new FileWriter(archivosalida+fileName)); //
								        band = 1;
									  }
								        int posicion = 1;
										
										//Escribo los estudiantes 
										ResultSet rs = getEstudiantes(rep.getDABOLCONSEC()); 
										if(rs != null){				
											int counter = 0;
											String[] nn = null;
											String[] n = null;
									        String nombreColegio = "";
									        String temp = "";
											while (rs.next()) {
												out2.write("\n");
												
												posicion = 1;
												long estcod = rs.getInt(posicion++); //Id del estudiante
												
												if(!temp.equals(rs.getString(3))){//Si es un colegio nuevo
													temp = rs.getString(3);
													nombreColegio = bolDAO.getNombreColegio(Long.parseLong(rs.getString(14)));
													
													//Escribo los encabezados del archivo
													String not = bolDAO.getNotasFromColegio(rep.getDABOLCONSEC(), rs.getLong(14));
											        out2.write("COLEGIO; SEDDANE; SEDNOMBRE; JORNOMBRE; GRACODIGO; DOC; NOMBRE1; NOMBRE2; NAPELLIDO1; APEILLiDO2; GRANOMBRE; NOMDIRGRUPO; GRUCODIGO;");
											        out2.write(not);
											        n = not.split(";");
											        nn = new String[n.length+1];
													for (int i=0; i<nn.length; i++)
												    {
														nn[i] = ";";
												    }
											        out2.write("\n");
												}			
												out2.write(nombreColegio);
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(Long.toString(estcod));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++));
												out2.write(" ;"+ rs.getString(posicion++) + " ;");	
												
												System.out
														.println("Iteracinn "+ counter++);
												
												//Escribo las notas del estudiante
												ResultSet notas = getNotasEstudiante(rep.getDABOLCONSEC(), estcod);
												if(notas != null){
													//String[] nt = nn;
													//posicion = 1;
													while (notas.next()) {
														//String nombreCurso = notas.getString(1);
														String notaCurso = notas.getString(5);
														if (notaCurso != null)
															out2.write(notaCurso +  ";");
														else
															out2.write(";");
														
														/**if(notaCurso != null){
															//Averigue la posicion de la nota y pongala en el vector	
															int pos = findPosition(n,nombreCurso);
															nt[pos+1] = notaCurso +  ";";
														}*/
													}	
													/**for(int i = 0; i < nt.length; i++){
														out2.write(nt[i]); //itere sobre el vector resultado e imprimalo
													}
													for (int i=0; i<nn.length; i++)
												    {
														nn[i] = ";";
												    }*/
												}
												closeDBEstudianteNotas();
											}
										}
										
										closeDBEstudiante();			
								    } 
								    catch (IOException e) 
								    { 
								       System.out.println("Exception ");
								       e.printStackTrace();
								    }
								//limpiar tablas
							}
						}
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_GENOK,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
					}
				//}
				if(out2 != null)
					out2.close();	
				if(rep != null){
				        rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_GENOK,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
						reporte.setMensaje("HILO REP RESULTADOS: REPORTE GENERADO SATISFACTORIAMENTE");
						bolDAO.updateReporte(reporte);
						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
				}
				return true;
				
			}
			/**
			 * Si no hay nada por hacer
			 * Duerma...
			 */
			else 
			{
				Thread.sleep(dormir);
			}
			return true;
		} catch (InterruptedException ex) {
			System.out.println(" - HILO REP RESULTADOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out.println("HILO REP RESULTADOS: PROCESAR SOLICITUDES FALLO.");
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_REVISADO, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				bolDAO.updateReporte(reporte);
				//bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		} catch (Exception ex) {
			System.out.println(" HILO REP RESULTADOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out.println("HILO REP RESULTADOS.: PROCESAR SOLICITUDES FALLO.");
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_REVISADO, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} finally {
			ocupado = false;
		}
	}



	public DatosBoletinVO reportes_Generar() {
		DatosBoletinVO list = null;
		try {
			list = bolDAO.getSolicitudes();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

	}
	
	public boolean procesarCol(DatosBoletinVO rep, Map parameterscopy,
			ReporteVO reporte) {

		Zip zip = new Zip();
		Collection list = new ArrayList();
		String archivosalida = null;
		int zise;
		Connection con = null;
		try {
			rep.setDABOLFECHAGEN(new java.sql.Timestamp(System
					.currentTimeMillis()).toString());
			rep.setDABOLFECHAFIN("");
			if (rep.getDABOLCONSEC() > 0) {
				if (bolDAO.validarDatosReporte(rep)) {
					con = bolDAO.getConnection();
					String nombrePdf = rep.getDABOLNOMBREPDF();
					String nombrePdf1 = nombrePdf
							.replaceFirst(".pdf", "_1.xls");
					String nombrePdf2 = nombrePdf
							.replaceFirst(".pdf", "_2.xls");
					String nombrePdf3 = nombrePdf
							.replaceFirst(".pdf", "_3.xls");
					String ruta1 = "";
					String ruta2 = "";
					String ruta3 = "";
					archivosalida = Ruta.get(context,
							rPath.getString("resultados.PathResultados"));
					boolean continuar = false;
					if ((reporte1.getPath() != null
							&& reporte2.getPath() != null && reporte3.getPath() != null)
							&& (parameterscopy != null)
							&& (!parameterscopy.values().equals("0"))
							&& (con != null)) {
						ruta1 = getArchivoXLS(reporte1, parameterscopy,
								archivosalida, nombrePdf1);
						if (continuar) {
							ruta2 = getArchivoXLS(reporte2, parameterscopy,
									archivosalida, nombrePdf2);
							ruta3 = getArchivoXLS(reporte3, parameterscopy,
									archivosalida, nombrePdf3);
						}
					}

					if (con != null)
						con.close();
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
					reporte.setMensaje("REP_RESULTADOS:REPORTE EN EJECUCION, CONSEC: "
							+ rep.getDABOLCONSEC());
					bolDAO.updateReporte(reporte);

					list.add(ruta1);// xls
					if (continuar) {
						list.add(ruta2);// xls
						list.add(ruta3);// xls
					}
					zise = 100000;
					if (zip.ponerZip(archivosalida, rep.getDABOLNOMBREZIP(),
							zise, list)) {
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_GENOK,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
						reporte.setMensaje("HILO REP RESULTADOS: REPORTE GENERADO SATISFACTORIAMENTE");
						bolDAO.updateReporte(reporte);
						//bolDAO.limpiarTablas(rep.getDABOLCONSEC());
						return true;
					}
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
					bolDAO.limpiarTablas(rep.getDABOLCONSEC());
					return true;
				}

			}
		} catch (InternalErrorException e) {
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
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		} catch (JRException e) {
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
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
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
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
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
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
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
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
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

	public boolean procesar(DatosBoletinVO rep, Map parameterscopy,
			ReporteVO reporte) {

		Connection con = null;
		try {
			bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			rep.setDABOLFECHAGEN(new java.sql.Timestamp(System
					.currentTimeMillis()).toString());
			rep.setDABOLFECHAFIN("");
			bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
					ParamsVO.ESTADO_REPORTE_EJE, rep.getDABOLFECHAGEN(),
					rep.getDABOLFECHAFIN());
			reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
			reporte.setMensaje("HILO REP RESULTADOS:REPORTE EN EJECUCION, CONSEC: "
					+ rep.getDABOLCONSEC());
			bolDAO.updateReporte(reporte);
			if (rep.getDABOLCONSEC() > 0) {
				System.out.println("HILO REP RESULTADOS: ENTRO A GENERAR REPORTE. DABOLCONSEC: " + rep.getDABOLCONSEC());
				long start = System.currentTimeMillis();
				if (!bolDAO.llenarTablas(rep, reporte)) {
					System.out.println("HILO REP RESULTADOS: ERROR PROCEDIMIENTO RESULTADOS ACA");
					return false;
				}
				// VALIDAR SI NO HUBO ERROR EN EL PROC
				if (bolDAO.validarEstadoReporte(rep.getDABOLCONSEC()) == ParamsVO.ESTADO_REPORTE_NOGEN)
					return true; //Ocurrieron erorres
				/**
				 * Aqui estaba anteriormente la generacinn del archivo jasper o .csv
				 * Se mueve dado que si Oracle no habia generado datos antes de llegar a este punto
				 * el reporte fallaba
				 */
				
				long end = System.currentTimeMillis();
				
				System.out.println("HILO REP RESULTADOS: FIN GENERAR REPORTE. DABOLCONSEC: " + rep.getDABOLCONSEC() + " Tiempo en millis: "+ (end-start));

			}
		} catch (InternalErrorException e) {
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
				//bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		} catch (JRException e) {
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
				//bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
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
				//bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			// System.out.println(" HILO REP RESULTADOS WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "+e.toString());
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
				//bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
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
				//bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
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

	private int findPosition(String[] n, String nombreCurso) {
		for (int i = 0 ; i < n.length; i++){
			if(n[i].trim().equals(nombreCurso.trim()))
				return i;
		}
		return 0;
	}

	/**
	 * Retorna los nombres de los cursos de un reporte dado su identificador
	 * @param conse
	 */
	private List getNombeMaterias(long conse) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement("select distinct A.ARENOMBRE from BOL_AREA A where A.ARECONSECBOL = "+ conse +" order by ARECODIGO");
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setNombre(rs.getString(++posicion));
				list.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return list;
		
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
					rPath.getString("resultados.PathResultados"));
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

	public boolean update_cola_certficados() {

		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbRep.getString("update_puesto_rep_1"));
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
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		if (!err) {
			err = true;
		}
	}

	public ReporteVO setReporte(DatosBoletinVO rep) {
		ReporteVO reporte = new ReporteVO();
		if (rep != null) {
			String pathComp = rbRep.getString("rep.PathReporte");
			reporte.setNombre(rep.getDABOLNOMBREZIP());
			reporte.setRecurso(pathComp + rep.getDABOLNOMBREZIP());
			reporte.setModulo(Integer.parseInt(ParamsVO.REP_MODULO));
			reporte.setFecha(rep.getDABOLFECHA());
			reporte.setTipo("zip");
			reporte.setUsuario(rep.getDABOLUSUARIO());
			reporte.setEstado(rep.getDABOLESTACT());
		}
		return reporte;
	}

	public File setFileJasper(DatosBoletinVO rep) {
		File reporte = null;
		if (rep != null) {
			if (rep.getDABOLTIPOREP() == ParamsVO.TIPO_REPORTE_1) {
				reporte = reporte1;
			} else if (rep.getDABOLTIPOREP() == ParamsVO.TIPO_REPORTE_2) {
				reporte = reporte2;
			} else if (rep.getDABOLTIPOREP() == ParamsVO.TIPO_REPORTE_3) {
				reporte = reporte3;
			}
		}
		return reporte;
	}

	public Map setParametros(DatosBoletinVO bdt) {
		Map parametros = new HashMap();
		if (bdt != null) {
			parametros = new HashMap();
			parametros.put("usuario", bdt.getDABOLUSUARIO());
			escudo = new File(path2 + "e" + bdt.getDANE().trim() + ".gif");
			if (escudo.exists()) {
				parametros.put("ESCUDO_COLEGIO", path2 + "e"
						+ bdt.getDANE().trim() + ".gif");
				parametros.put("ESCUDO_COLEGIO_EXISTE", new Integer(1));
			} else {
				escudo = new File(path2 + "e" + bdt.getDANE().trim() + ".GIF");
				if (escudo.exists()) {
					parametros.put("ESCUDO_COLEGIO", path2 + "e"
							+ bdt.getDANE().trim() + ".GIF");
					parametros.put("ESCUDO_COLEGIO_EXISTE", new Integer(1));
				} else {
					parametros.put("ESCUDO_COLEGIO_EXISTE", new Integer(1));
					parametros.put("ESCUDO_COLEGIO",
							path_escudo + rbRep.getString("rep.imagen"));
				}
			}

			parametros.put("ESCUDO_SED",
					path_escudo + rbRep.getString("rep.imagen"));

			parametros.put("PERIODO", new Integer((int) bdt.getDABOLPERIODO()));
			parametros.put("VIGENCIA", new Long(bdt.getDABOLVIGENCIA()));

			parametros.put("CONSECBOL", new Long(bdt.getDABOLCONSEC()));
			parametros.put("CONSEC", new Long(bdt.getDABOLCONSEC()));

			parametros.put("NOMBRE_PER_FINAL", bdt.getDABOLNOMPERDEF());
			parametros.put("NUM_PERIODOS", new Integer(bdt.getDABOLNUMPER()));
			parametros.put("RESOLUCION", bdt.getDABOLRESOLUCION());
			parametros.put("INSTITUCION", bdt.getDABOLINSNOMBRE());
			parametros.put("SEDE", bdt.getDABOLSEDNOMBRE());
			parametros.put("JORNADA", bdt.getDABOLJORNOMBRE());
			parametros.put("DANE", bdt.getDANE());
		}
		return parametros;
	}

	/**
	 * Construye el archivo y lo almacena en el servidor
	 * 
	 * @param reportFile
	 *            Archivo del reporte
	 * @param parameterscopy
	 *            Parametros del reporte
	 * @param rutaExcel
	 *            Ruta de almacenamiento del reporte
	 * @param archivo
	 *            Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoXLS(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = cursor.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			// USANDO API EXCEL
			JExcelApiExporter xslExporter = new JExcelApiExporter();
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT,
					jasperPrint);
			xslExporter.setParameter(
					JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			xslExporter.setParameter(
					JExcelApiExporterParameter.OUTPUT_FILE_NAME, xlsFilesSource
							+ xlsFileName);
			xslExporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}
	
	public ResultSet getNotasEstudiante(long consec, long codest) {
		try {
			OperacionesGenerales.closeResultSet(rs2);
			OperacionesGenerales.closeStatement(st2);
			if(cn2 != null && !cn2.isClosed())
				OperacionesGenerales.closeConnection(cn2);
			cn2 = cursor.getConnection();
			st2   = cn2.prepareStatement("select distinct A.ARENOMBRE, A.ARECODEST, A.ARECODIGO, A.EVAAREEVAL7,A.EVAAREMEN From BOL_AREA A where A.ARECONSECBOL = "+consec +" and A.ARECODEST = "+codest+" order by ARECODIGO");
			rs2 = st2.executeQuery();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} 
		return rs2;
	}
	
	
	
	
	Connection cn1 = null;
	PreparedStatement st1 = null;
	ResultSet rs1 = null;
	
	Connection cn2 = null;
	PreparedStatement st2 = null;
	ResultSet rs2 = null;
	
	public ResultSet getEstudiantes(long consec) {
		try {
			closeDBEstudiante();
			cn1 = cursor.getConnection();
			st1 = cn1.prepareStatement("select distinct EST.ESTCODIGO,SEDDANE,SEDNOMBRE, JORNOMBRE, GRACODIGO, EST.NOMTIPODOC||'. '||EST.ESTNUMDOC as DOC, ESTNOMBRE1, EST.ESTNOMBRE2, EST.ESTAPELLIDO1, EST.ESTAPELLIDO2, EST.GRANOMBRE, EST.NOMDIRGRUPO,GRUCODIGO, inscodigo from BOL_ESTUDIANTE EST where EST.ESTCONSECBOL = "+consec+" order by SEDNOMBRE, JORNOMBRE,GRACODIGO, GRUCODIGO,EST.ESTAPELLIDO1, EST.ESTAPELLIDO2, ESTNOMBRE1, EST.ESTNOMBRE2,ESTCODIGO");
			rs1 = st1.executeQuery();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} 
		return rs1;
	}
	
	public void closeDBEstudiante(){
		try {
			OperacionesGenerales.closeResultSet(rs1);
			OperacionesGenerales.closeStatement(st1);
			OperacionesGenerales.closeConnection(cn1);
		} catch (Exception inte) {
		}
	}
	
	public void closeDBEstudianteNotas(){
		try {
			OperacionesGenerales.closeResultSet(rs2);
			OperacionesGenerales.closeStatement(st2);
			OperacionesGenerales.closeConnection(cn2);
		} catch (Exception inte) {
		}
	}

}