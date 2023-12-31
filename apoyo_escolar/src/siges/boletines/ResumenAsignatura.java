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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.FiltroNivelEvalVO;
import siges.adminParamsInst.vo.InstNivelEvaluacionVO;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.boletines.vo.DatosBoletinVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;

/**
 * Nombre: ResumenArea<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: ResumenArea <BR>
 * Fecha de modificacinn: 30/04/2010 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ResumenAsignatura extends Thread {
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
	private FiltroBeanReports filtro;
	private String buscarcodigo;
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	private final String modulo = "23";
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
	private File reportFileConceptual;
	private File reportFile1;
	private File reportFile2;
	private File reportFile5;
	private File reportFile5_11;
	private File reportFileLogros;
	private File reportFileLogros5_11;
	private File reportFileDescriptores;
	private File reportFileninguno;
	private File reportFileLogros5;
	private File reportFilePreescolarSinImgs;
	private File reportFilePreescolar5SinImgs;
	private File escudo;
	private String path,
	// path_logos,
			path_escudos;
	private String context;
	private String codigoestu;
	private File reportFileNuevoFormato;
	// private PreparedStatement pst;
	// private CallableStatement cstmt;
	// private ResultSet rs;
	private reportes report;
	private String vigencia;
	private long consecBol = 0;
	private String usuarioBol = "";
	private String nombreBol = "";
	private String nombreBolPdf = "";
	private ReporteLogrosDAO reportesDAO;
	private AdminParametroInstDAO parametroInstDAO;

	/* Constructor de la clase */

	public ResumenAsignatura(Cursor c, String cont, String p, String p1,
			String p2, File reportF, File reportF1, File reportC, int nn) {
		super("hilo_ResumenAsignatura " + nn);
		cursor = c;
		path = p;
		// path_logos=p1;
		path_escudos = p2;
		reportFile = reportF;
		reportFile1 = reportF1;
		reportFileConceptual = reportC;
		context = cont;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		s = rb3.getString("valor_s");
		s1 = rb3.getString("valor_s1");
		buscar = buscarjasper = insertar = existeboletin = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
		vigencia = getVigencia();
		reportesDAO = new ReporteLogrosDAO(cursor);
		parametroInstDAO = new AdminParametroInstDAO(cursor);
	}

	public void run() {
		 System.out.println("RUN MCUELLAR---------");
		Object[] o = new Object[2];
		int posicion = 1;
		String[][] array = null;
		int dormir = 0;
		String cola = null;
		String puest = "-999";
		report = new reportes();
		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rb3.getString("boletines.Dormir"));
			while (ocupado) {
				 System.out.println(getName() + ":Espera Thread");
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
				 System.out
				 .println("MCUELLAR .... BOLETINES: BOLETINES A GENERAR");
				List resumenes = resumen_Generar();
				Iterator bols = resumenes.iterator();
				if (resumenes != null && resumenes.size() > 0) {
					 System.out
					 .println("RESUMEN ASIGNATURA: SI HAY RESUMENES POR GENERAR: CANTIDAD "
					 + resumenes.size());
					while (bols.hasNext()) {
						DatosBoletinVO bdt = (DatosBoletinVO) bols.next();
						// System.out
						// .println("RESUMEN ASIGNATURA: RESUMEN A GENERAR CONSEC: "
						// + bdt.getDABOLCONSEC());
						parameters = new HashMap();
						parameters.put("usuario", bdt.getDABOLUSUARIO());
						usuarioBol = bdt.getDABOLUSUARIO();
						nombreBol = bdt.getDABOLNOMBREZIP();
						nombreBolPdf = bdt.getDABOLNOMBREPDF();
						escudo = new File(path_escudos + "e"
								+ bdt.getDANE().trim() + ".gif");
						System.out.println("escudo: " + escudo);
						if (escudo.exists()) {
							parameters.put("ESCUDO_COLEGIO", path_escudos + "e"
									+ bdt.getDANE().trim() + ".gif");
							parameters.put("ESCUDO_COLEGIO_EXISTE",
									new Integer(1));
						} else {
							escudo = new File(path_escudos + "e"
									+ bdt.getDANE().trim() + ".GIF");
							if (escudo.exists()) {
								parameters.put("ESCUDO_COLEGIO", path_escudos
										+ "e" + bdt.getDANE().trim() + ".GIF");
								parameters.put("ESCUDO_COLEGIO_EXISTE",
										new Integer(1));
							} else {
								parameters.put("ESCUDO_COLEGIO_EXISTE",
										new Integer(0));
							}
							// parameters.put("ESCUDO_COLEGIO_EXISTE",new
							// Integer(0));
						}

						parameters.put("ESCUDO_SED",
								path_escudos + rb3.getString("imagen"));

						parameters.put("PERIODO",
								new Integer((int) bdt.getDABOLPERIODO()));
						parameters.put("VIGENCIA",
								new Integer(bdt.getDABOLVIGENCIA()));

						parameters.put("MOSTRAR_DESCRIPTOR",
								new Integer(bdt.getDABOLDESCRIPTORES()));
						parameters.put("MOSTRAR_LOGROS",
								new Integer(bdt.getDABOLLOGROS()));
						parameters.put("MOSTRAR_LOGROS_P",
								new Integer(bdt.getDABOLTOTLOGROS()));
						parameters.put("MOSTRAR_AREAS",
								new Integer(bdt.getDABOLAREA()));
						parameters.put("MOSTRAR_ASIG",
								new Integer(bdt.getDABOLASIG()));

						parameters.put("CONSECBOL",
								new Long(bdt.getDABOLCONSEC()));
						consecBol = bdt.getDABOLCONSEC();
						parameters.put("NOMBRE_PER_FINAL",
								bdt.getDABOLNOMPERDEF());
						parameters.put("NUM_PERIODOS",
								new Integer(bdt.getDABOLNUMPER()));
						// parameters.put("SUBTITULO",bdt.getDABOLSUBTITULO());
						parameters.put("SUBTITULO", "LOGROS Y DECRIPTORES");
						parameters.put("RESOLUCION", bdt.getDABOLRESOLUCION());
						parameters.put("INSTITUCION", bdt.getDABOLINSNOMBRE());
						parameters.put("SEDE", bdt.getDABOLSEDNOMBRE());
						parameters.put("JORNADA", bdt.getDABOLJORNOMBRE());

						// nuevos parametros del jasper de nuevo boletin

						// System.out.println("MCUELLAR  PARAMETROS: "
						// + parameters.values());

						if (!process(bdt, parameters)) {
							// System.out
							// .println("RESUMEN ASIGNATURA: PROCCES FALLO.");
							ponerReporteMensaje(
									"2",
									modulo,
									usuarioBol,
									rb3.getString("resumenes.PathResumenes")
											+ filtro.getNombreboletinzip() + "",
									"zip", "" + filtro.getNombreboletinzip(),
									"ReporteActualizarBoletinPaila",
									"Problema en process");
							updateDatosBoletin("2", nombreBol, "0", usuarioBol);
							updateReporte(nombreBol, usuarioBol, "2");
							continue;
						}
						if (!updatePuestoBoletin(puest, nombreBol, usuarioBol,
								"update_puesto_boletin")) {
							// System.out
							// .println("RESUMEN ASIGNATURA: **NO Se actualizn el puesto del resumen en Datos_Boletin**");
							continue;
						}
						if (!update_cola_boletines()) {
							// System.out
							// .println("RESUMEN ASIGNATURA: NO Se actualizn la lista de los reportes en cola");
							continue;
						}
						// System.out.println("RESUMEN ASIGNATURA: " + getName()
						// + ":Sale While resumenes");
					}
				} else {
					Thread.sleep(dormir);
					// System.out
					// .println("RESUMEN ASIGNATURA: NO HAY RESUMENES AREA A GENERAR");
					// System.out.println("**Se mandaron vaciar las tablas ya que no hay boletines por generar**");
					// vaciarTablas();
					// System.out.print("*");
				}
			}
		} catch (InterruptedException ex) {
			System.out
					.println(new Date()
							+ " - RESUMEN ASIGNATURA: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletin",
					"Ocurrin Interrupted excepcinn en el Hilo:_" + ex);
			updateDatosBoletin("2", nombreBol, "-1", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			limpiarTablas(consecBol);
		} catch (Exception ex) {
			System.out
					.println(new Date()
							+ " - RESUMEN ASIGNATURA: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en el Hilo:_" + ex);
			updateDatosBoletin("2", nombreBol, "-1", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			limpiarTablas(consecBol);
		} finally {
			ocupado = false;
		}
	}

	public List resumen_Generar() {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String[][] array1 = null;
		DatosBoletinVO db = null;
		List listdb = new ArrayList();
		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("resumen_asig_a_generar"));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();
			// System.out.println("ejecutn boletin_a_generar");

			int m = rs.getMetaData().getColumnCount();

			int i = 0;
			int f = 0;
			while (rs.next()) {
				f++;
				int pos = 1;
				db = new DatosBoletinVO();
				db.setDABOLINST(rs.getLong(pos++));
				db.setDABOLSEDE(rs.getLong(pos++));
				db.setDABOLJORNADA(rs.getLong(pos++));
				db.setDABOLMETODOLOGIA(rs.getLong(pos++));
				db.setDABOLGRADO(rs.getLong(pos++));
				db.setDABOLGRUPO(rs.getLong(pos++));
				db.setDABOLPERIODO(rs.getLong(pos++));
				db.setDABOLPERIODONOM(rs.getString(pos++));
				db.setDABOLCEDULA(rs.getString(pos++));
				db.setDABOLFECHA(rs.getString(pos++));
				db.setDABOLUSUARIO(rs.getString(pos++));
				db.setDABOLNOMBREZIP(rs.getString(pos++));
				db.setDABOLNOMBREPDF(rs.getString(pos++));
				db.setDABOLNOMBREPDFPRE(rs.getString(pos++));
				db.setDABOLDESCRIPTORES(rs.getInt(pos++));
				db.setDABOLLOGROS(rs.getInt(pos++));
				db.setDABOLTOTLOGROS(rs.getInt(pos++));
				db.setDABOLAREA(rs.getInt(pos++));
				db.setDABOLASIG(rs.getInt(pos++));
				db.setDABOLCONSEC(rs.getLong(pos++));
				db.setDABOLINSNOMBRE(rs.getString(pos++));
				db.setDABOLSEDNOMBRE(rs.getString(pos++));
				db.setDABOLJORNOMBRE(rs.getString(pos++));
				// db.setDABOLJORNOMBRE(rs.getString(pos++));
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
				listdb.add(db);

			}

			// if (!listdb.isEmpty()) {
			// System.out
			// .println("RESUMEN ASIGNATURAS:nnnnSI HAY RESUMENES ASIG POR GENERAR!!!!");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en el Hilo: " + e);
			updateDatosBoletin("2", nombreBol, "-1", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return listdb;
	}

	public boolean process(DatosBoletinVO dbt, Map parameterscopy) {

		// parameterscopy=parameters;
		// System.out.println("MCUELLAR PROCESS");
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
		int boletin = 1;

		try {
			// limpiarTablas(dbt.getDABOLCONSEC());
			// System.out.println("nSe limpiaron las tablas antes de ser llenadas!");
			updateDatosBoletin("0", nombreBol, "-1", usuarioBol);
			updateReporte(nombreBol, usuarioBol, "0");
			ponerReporteMensajeListo(modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarGenerando");
			updateDatosBoletinFechaGen(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"0", nombreBol, usuarioBol);
			// System.out.println("RESUMEN ASIGNATURA: NIVEL GRADO: "
			// + dbt.getDABOLGRADO());
			int nivelGrado = reportesDAO.getNivelGrado((int) dbt
					.getDABOLGRADO());
			if (nivelGrado == 1) {
				if (dbt.getDABOLTIPOEVALPREES() == 1) {
					boletin = 0;
				}
			}

			if (boletin == 1) {
				// System.out
				// .println("RESUMEN ASIGNATURA: ENTRO A GENERAR RESUMEN POR ASIGNATURA");
				if (!boletin(dbt.getDABOLCONSEC())) {
					// System.out
					// .println("RESUMEN ASIGNATURA: ERROR PROCEDIMIENTO RESUMEN");
					return false;
				}
				// VALIDAR SI NO HUBO ERROR EN EL PROC
				// System.out.println("VALIDAD MCUELLAR");
				if (validarEstadoBol(dbt.getDABOLCONSEC()) == 2)
					return true;
				/* Consulta para ejecutar el/los jasper */
				con = cursor.getConnection();
				pst = con
						.prepareStatement(rb3.getString("validarDatosBoletin"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, dbt.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					try {
						rs.close();
						pst.close();
						DatosBoletinVO dbTemp = datosConv(dbt);
						if (dbTemp != null) {
							parameterscopy.put("CONVINST",
									dbTemp.getDABOLCONVINST());
							parameterscopy.put("CONVMEN",
									dbTemp.getDABOLCONVMEN());
						}
						// System.out
						// .println("RESUMEN ASIGNATURA: HAY DATOS SE MANDA EJECUTAR EL JASPER");
						String rutaXSL = "";
						if ((reportFile.getPath() != null)
								&& (parameterscopy != null)
								&& (!parameterscopy.values().equals("0"))
								&& (con != null)) {
							InstNivelEvaluacionVO instNivelEvaluacionVO = null;
							try {
								FiltroNivelEvalVO filtroNivelEvalVO = new FiltroNivelEvalVO();
								filtroNivelEvalVO.setFilVigencia(dbt
										.getDABOLVIGENCIA());
								filtroNivelEvalVO
										.setFilInst(dbt.getDABOLINST());
								filtroNivelEvalVO.setFilniveval(dbt
										.getDABOLNIVEVAL());
								// filtroNivelEvalVO.setFilGrado(dbt.getDABOLGRADO());
								List datos = parametroInstDAO
										.getListaNivelEval(filtroNivelEvalVO);
								if (datos != null && datos.size() > 0) {
									if (datos.size() == 1) {
										instNivelEvaluacionVO = (InstNivelEvaluacionVO) datos
												.get(0);
									} else {
										Iterator iteDatos = datos.iterator();
										while (iteDatos.hasNext()) {
											instNivelEvaluacionVO = (InstNivelEvaluacionVO) iteDatos
													.next();
											long gradoVO = instNivelEvaluacionVO
													.getInsnivcodgrado();
											long gradoFiltro = dbt
													.getDABOLGRADO();
											if (instNivelEvaluacionVO
													.getInsnivcodsede() != -99
													&& instNivelEvaluacionVO
															.getInsnivcodsede() == dbt
															.getDABOLSEDE()) {
												// Nivel de evaluacion por sede.
												break;
											} else if (instNivelEvaluacionVO
													.getInsnivcodjorn() != -99
													&& instNivelEvaluacionVO
															.getInsnivcodjorn() == dbt
															.getDABOLJORNADA()) {
												// Nivel de evaluacion por
												// jornada.
												break;
											} else if (instNivelEvaluacionVO
													.getInsnivcodgrado() != -99
													&& instNivelEvaluacionVO
															.getInsnivcodgrado() == dbt
															.getDABOLGRADO()) {
												// Nivel de evaluacion por
												// grado.
												break;
											}
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								instNivelEvaluacionVO = null;
							}

							/***
							 * MCUELLAR. VALIDACION PARA SABER EL TIPO DE
							 * EVALUACION
							 ***/
							if (instNivelEvaluacionVO != null
									&& instNivelEvaluacionVO
											.getInsnivtipoevalasig() == 2) {
								// System.out
								// .println("RESUMEN ASIGNATURA CONCEPTUAL:  SE MANDO EJECUTAR JASPER");
								// bytes=JasperRunManager.runReportToPdf(reportFile.getPath(),parameterscopy,con);
								String nombreXLS = nombreBolPdf.replaceAll(
										"pdf", "xls");
								archivosalida = Ruta.get(context,
										r.getString("boletines.PathResumen"));
								rutaXSL = archivosalida;
								rutaXSL = getArchivoXLS(reportFileConceptual,
										parameterscopy, archivosalida,
										nombreXLS);
							} else {
								// System.out
								// .println("RESUMEN ASIGNATURA:  SE MANDO EJECUTAR JASPER");
								// bytes=JasperRunManager.runReportToPdf(reportFile.getPath(),parameterscopy,con);
								String nombreXLS = nombreBolPdf.replaceAll(
										"pdf", "xls");
								archivosalida = Ruta.get(context,
										r.getString("boletines.PathResumen"));
								rutaXSL = archivosalida;
								rutaXSL = getArchivoXLS(reportFile,
										parameterscopy, archivosalida,
										nombreXLS);
							}

						}
						ponerReporteMensajeListo(modulo, usuarioBol,
								rb3.getString("resumenes.PathResumenes")
										+ nombreBol + "", "zip",
								"" + nombreBol, "ReporteActualizarListo");
						// ponerArchivo(modulo,path,bytes1,filtro.getNombreboletinpre());
						// ponerArchivo(modulo,path,bytes,nombreBolPdf);
						// archivosalida=Ruta.get(context,r.getString("boletines.PathResumen"));
						// list.add(archivosalida+filtro.getNombreboletinpre());//pdf
						// list.add(archivosalida+nombreBolPdf);//pdf
						// System.out
						// .println("RESUMEN ASIG NOMBRE  ** ARCHIVO XLS: "
						// + rutaXSL);
						list.add(rutaXSL);// XLS
						zise = 100000;

						if (zip.ponerZip(archivosalida, nombreBol, zise, list)) {
							updateDatosBoletin("1", nombreBol, "0", usuarioBol);
							updateDatosBoletinFechaFin(new java.sql.Timestamp(
									System.currentTimeMillis()).toString(),
									"1", nombreBol, usuarioBol);
							updateReporte(nombreBol, usuarioBol, "1");
							// siges.util.Logger.print(usuarioBol,"Se genern el Boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
							limpiarTablas(consecBol);
							return true;
						}
					} catch (Exception e) {
						rs.close();
						pst.close();
						ponerReporteMensaje("2", modulo, usuarioBol,
								rb3.getString("resumenes.PathResumenes")
										+ nombreBol + "", "zip",
								"" + nombreBol,
								"ReporteActualizarBoletinGenerando",
								"nError generando el reporte!");
						System.out.println("nError generando el reporte!");
						updateDatosBoletin("2", nombreBol, "0", usuarioBol);
						updateReporte(nombreBol, usuarioBol, "2");
						// //siges.util.Logger.print(usuarioBol,"No se encontrarnn registros para generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
						limpiarTablas(consecBol);
						return true;
					}
				} else {
					rs.close();
					pst.close();
					ponerReporteMensaje("2", modulo, usuarioBol,
							rb3.getString("resumenes.PathResumenes")
									+ nombreBol + "", "zip", "" + nombreBol,
							"ReporteActualizarBoletinGenerando",
							"nNo se encontraron registros para generar el resumen!");
					// System.out
					// .println("nNo se encontraron registros para generar el resumen!");
					updateDatosBoletin("2", nombreBol, "0", usuarioBol);
					updateReporte(nombreBol, usuarioBol, "2");
					// //siges.util.Logger.print(usuarioBol,"No se encontrarnn registros para generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
					limpiarTablas(consecBol);
					return true;
				}
				rs.close();
				pst.close();
			} else if (boletin == 0) {
				// System.out
				// .println("RESUMEN ASIGNATURA: ENTRO A GENERAR RESUMEN ASIG POR DIMENSIONES ** ");
				if (!boletin_dim(dbt.getDABOLCONSEC())) {
					// System.out
					// .println("RESUMEN ASIGNATURA: ERROR PROCEDIMIENTO DIMENSIONES **");
					return false;
				}
				// VALIDAR SI NO HUBO ERROR EN EL PROC
				if (validarEstadoBol(dbt.getDABOLCONSEC()) == 2)
					return true;
				/* Consulta para ejecutar el/los jasper */
				con = cursor.getConnection();
				pst = con
						.prepareStatement(rb3.getString("validarDatosBoletin"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, dbt.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					rs.close();
					pst.close();
					DatosBoletinVO dbTemp = datosConv(dbt);
					if (dbTemp != null) {
						parameterscopy.put("CONVINST",
								dbTemp.getDABOLCONVINST());
						parameterscopy.put("CONVMEN", dbTemp.getDABOLCONVMEN());
					}
					// System.out
					// .println("RESUMEN ASIGNATURA: HAY DATOS SE MANDA EJECUTAR EL JASPER DIMENSIONES");
					if ((reportFile1.getPath() != null)
							&& (parameterscopy != null)
							&& (!parameterscopy.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("RESUMEN ASIGNATURA:  SE MANDO EJECUTAR JASPER DIMENSIONES");
						bytes = JasperRunManager.runReportToPdf(
								reportFile1.getPath(), parameterscopy, con);
					}
					ponerReporteMensajeListo(modulo, usuarioBol,
							rb3.getString("resumenes.PathResumenes")
									+ nombreBol + "", "zip", "" + nombreBol,
							"ReporteActualizarListo");
					// ponerArchivo(modulo,path,bytes1,filtro.getNombreboletinpre());
					ponerArchivo(modulo, path, bytes, nombreBolPdf);
					archivosalida = Ruta.get(context,
							r.getString("boletines.PathResumen"));
					// list.add(archivosalida+filtro.getNombreboletinpre());//pdf
					list.add(archivosalida + nombreBolPdf);// pdf
					zise = 100000;

					if (zip.ponerZip(archivosalida, nombreBol, zise, list)) {
						updateDatosBoletin("1", nombreBol, "0", usuarioBol);
						updateDatosBoletinFechaFin(new java.sql.Timestamp(
								System.currentTimeMillis()).toString(), "1",
								nombreBol, usuarioBol);
						updateReporte(nombreBol, usuarioBol, "1");
						// //siges.util.Logger.print(usuarioBol,"Se genern el Boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
						limpiarTablas(consecBol);
						return true;
					}
				}// fin de rs
				else {
					rs.close();
					pst.close();
					ponerReporteMensaje("2", modulo, usuarioBol,
							rb3.getString("resumenes.PathResumenes")
									+ nombreBol + "", "zip", "" + nombreBol,
							"ReporteActualizarBoletinGenerando",
							"nNo se encontraron registros para generar el boletin!");
					// System.out
					// .println("nNo se encontraron registros para generar el boletin!");
					updateDatosBoletin("2", nombreBol, "0", usuarioBol);
					updateReporte(nombreBol, usuarioBol, "2");
					// //siges.util.Logger.print(usuarioBol,"No se encontrarnn registros para generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
					limpiarTablas(consecBol);
					return true;
				}
				rs.close();
				pst.close();

			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinGenerando",
					"Ocurrio una excepcinn interna:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			limpiarTablas(consecBol);
			return false;
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinGenerando",
					"Ocurrio excepcinn en el Jasper:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			// siges.util.Logger.print(usuarioBol,"JRExcepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			limpiarTablas(consecBol);
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinGenerando",
					"Ocurrio excepcinn SQL:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			// siges.util.Logger.print(usuarioBol,"SQLExcepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			limpiarTablas(consecBol);
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			// siges.util.Logger.print(usuarioBol,"OutOfMemoryError al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			limpiarTablas(consecBol);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinGenerando",
					"Ocurrin excepcinn:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			limpiarTablas(consecBol);
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
	public boolean boletin(long consec) {
		// System.out.println("MCUELLAR BOLETIN");
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {
			if (consec > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con.prepareCall("{call PK_BOLETIN.PK_BOL_INICIAL(?)}");
				cstmt.setLong(posicion++, consec);
				// System.out.println("RESUMEN ASIGNATURA: ASIGNATURA - consec: "
				// + consec
				// + " Inicia procedimiento Hora: "
				// + new java.sql.Timestamp(System.currentTimeMillis())
				// .toString());
				cstmt.executeUpdate();
				// System.out
				// .println("RESUMEN ASIGNATURA: ASIGNATURA - Fin procedimiento Hora: "
				// + new java.sql.Timestamp(System
				// .currentTimeMillis()).toString());
				cstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinGenerando",
					"Ocurrio excepcinn SQL:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateDatosBoletinFechaFin(
					new java.sql.Timestamp(System.currentTimeMillis())
							.toString(),
					"2", nombreBol, usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			// //siges.util.Logger.print(usuarioBol,"SQLExcepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinPaila",
					"Ocurrin excepcinn prepareds:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			limpiarTablas(consecBol);
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
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
	public boolean boletin_dim(long consec) {
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;

		try {
			if (consec > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con
						.prepareCall("{call PK_BOLETIN_DIMENSIONES.BOL_DIM_INICIAL(?)}");
				cstmt.setLong(posicion++, consec);
				// System.out
				// .println("RESUMEN ASIGNATURA: DIMENSIONES **  - consec "
				// + consec
				// + " Inicia procedimiento Hora: "
				// + new java.sql.Timestamp(System
				// .currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out
				// .println("RESUMEN ASIGNATURA: DIMENSIONES ** - Fin procedimiento Hora: "
				// + new java.sql.Timestamp(System
				// .currentTimeMillis()).toString());
				cstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, usuarioBol,
					rb3.getString("resumenes.PathResumenes") + nombreBol + "",
					"zip", "" + nombreBol, "ReporteActualizarBoletinPaila",
					"Ocurrin excepcinn prepareds:_" + e);
			updateDatosBoletin("2", nombreBol, "0", usuarioBol);
			updateReporte(nombreBol, usuarioBol, "2");
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
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
	public int validarEstadoBol(long consec) {
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		try {
			if (consec > 0) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rb3
						.getString("validarEstadoBoletin"));
				posicion = 1;
				pst.setLong(1, consec);
				rs = pst.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// ponerReporteMensaje("2",modulo,usuarioBol,rb3.getString("resumenes.PathResumenes")+nombreBol+"","zip",""+nombreBol,"ReporteActualizarBoletinGenerando","Ocurrio excepcinn SQL:_"+e);
			// updateDatosBoletin("2",nombreBol,"0",usuarioBol);
			// updateDatosBoletinFechaFin(new
			// java.sql.Timestamp(System.currentTimeMillis()).toString(),"2",nombreBol,usuarioBol);
			// updateReporte(nombreBol,usuarioBol,"2");
			// //siges.util.Logger.print(usuarioBol,"SQLExcepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			limpiarTablas(consecBol);
			return estado;
		} catch (Exception e) {
			e.printStackTrace();
			// ponerReporteMensaje("2",modulo,usuarioBol,rb3.getString("resumenes.PathResumenes")+nombreBol+"","zip",""+nombreBol,"ReporteActualizarBoletinPaila","Ocurrin excepcinn prepareds:_"+e);
			// updateDatosBoletin("2",nombreBol,"0",usuarioBol);
			// updateReporte(nombreBol,usuarioBol,"2");
			limpiarTablas(consecBol);
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
			return estado;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return estado;
	}// fin de preparedstatements

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
	public DatosBoletinVO datosConv(DatosBoletinVO db) {
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		try {
			if (db != null) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rb3
						.getString("getDatosConvenciones"));
				posicion = 1;
				pst.setLong(1, db.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					db.setDABOLCONVINST(rs.getString(1));
					db.setDABOLCONVMEN(rs.getString(2));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// ponerReporteMensaje("2",modulo,usuarioBol,rb3.getString("resumenes.PathResumenes")+nombreBol+"","zip",""+nombreBol,"ReporteActualizarBoletinGenerando","Ocurrio excepcinn SQL:_"+e);
			// updateDatosBoletin("2",nombreBol,"0",usuarioBol);
			// updateDatosBoletinFechaFin(new
			// java.sql.Timestamp(System.currentTimeMillis()).toString(),"2",nombreBol,usuarioBol);
			// updateReporte(nombreBol,usuarioBol,"2");
			// //siges.util.Logger.print(usuarioBol,"SQLExcepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			// limpiarTablas(consecBol);
			return db;
		} catch (Exception e) {
			e.printStackTrace();
			// ponerReporteMensaje("2",modulo,usuarioBol,rb3.getString("resumenes.PathResumenes")+nombreBol+"","zip",""+nombreBol,"ReporteActualizarBoletinPaila","Ocurrin excepcinn prepareds:_"+e);
			// updateDatosBoletin("2",nombreBol,"0",usuarioBol);
			// updateReporte(nombreBol,usuarioBol,"2");
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
			return db;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return db;
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
					r.getString("boletines.PathResumen"));
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
			limpiarTablas(consecBol);
		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
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
			con.commit();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
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

	public void updateDatosBoletin(String nuevoestado, String nombreboletin,
			String estadoanterior, String user) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("update_datos_boletin"));
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
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
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

			pst = con.prepareStatement(rb3
					.getString("update_reporte_resumen_asig"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (nombreboletin));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			// limpiarTablas(consecBol);limpiarTablasDim(consecBol);
		} catch (Exception e) {
			e.printStackTrace();
			// limpiarTablas(consecBol);limpiarTablasDim(consecBol);
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

	public boolean updatePuestoBoletin(String puesto, String nombreboletinzip,
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
	 **/

	public void updateDatosBoletinFechaGen(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_boletin_fecha_gen"));
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
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
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

	public void updateDatosBoletinFechaFin(String nuevafecha, String estado,
			String nombreboletin1, String user1) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3
					.getString("update_datos_boletin_fecha_fin"));
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
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
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
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
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
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consecBol);
			limpiarTablasDim(consecBol);
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

	public void limpiarTablas(long usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN.pk_bol_borrar_tablas(?)}");
			cstmt.setLong(posicion++, usuarioid);
			cstmt.executeUpdate();
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

	public void limpiarTablasDim(long usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN_DIMENSIONES.PK_BOL_DIM_BORRAR_TABLAS(?)}");
			cstmt.setLong(posicion++, usuarioid);
			cstmt.executeUpdate();
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
			cstmt = con.prepareCall("{call PK_NOTAS.notas_vaciar_tablas(?)}");
			cstmt.setLong(posicion++, Long.parseLong("1"));
			cstmt.executeUpdate();
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

	public boolean update_cola_boletines() {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3
					.getString("update_puesto_resumenAsig_1"));
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

			/**
			 * Codigo: oct04Jasper01 Modificado por: Grupo de Desarrollo REDP -
			 * MICUELLARI Se adiciona este parametro de configuracion a la
			 * generacion del reporte.
			 */
			xslExporter.setParameter(
					JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,
					Boolean.TRUE);

			xslExporter.setParameter(
					JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.TRUE);
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT,
					jasperPrint);
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

}
