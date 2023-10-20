package siges.librodeNotas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import siges.boletines.ControllerAuditoriaReporte;
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

public class Libro extends Thread {
	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle rPath, rbBol;
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
	private String path, path1, path2;
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
	ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();

	/* Constructor de la clase */

	public Libro(Cursor c, String cont, String p, String p1, String p2,
			File reporte1_1_, File reporte1_2_, int nn) {
		super("HILO_REP_LIBROS: " + nn);
		cursor = c;
		path = p;
		path1 = p1;
		path2 = p2;

		reporte1_1 = reporte1_1_;
		reporte1_2 = reporte1_2_;

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
		// vigencia=getVigencia();
		bolDAO = new ReporteLogrosDAO(cursor);
	}

	public void run() {
		Object[] o = new Object[2];
		int posicion = 1;
		String[][] array = null;
		dormir = 0;
		String cola = null;
		String puest = "-999";
		// report= new reportes();
		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rbBol.getString("boletines.Dormir"));

			while (ocupado) {
				// System.out.println(getName() + ":Espera Thread");
				sleep(dormir);
			}

			ocupado = true;
			// System.out.println(getName()+":Entra Thread");
			while (true) {
				try {
					// VALIDACION SI EL HILO ESTA EN PARAMETRO
					if (!bolDAO.activo()) {// es porque en la tabla vale 0
						Thread.sleep(dormir);
						continue;
					}
					procesar_solicitudes();
				} catch (Exception e) {
					auditoriaReporte.insertarAuditoria("LIBRO", 0, "HILO REP LIBRO.: EXECPCION WHILE INTERNO: "+e.getMessage(),0L);
					e.printStackTrace();
				}

			}
		} catch (InterruptedException ex) {
			System.out.println(new Date()+ " - HILO REP LIBROS.: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			auditoriaReporte.insertarAuditoria("LIBRO", 0, "HILO REP LIBRO.: EXECPCION WHILE InterruptedException: "+ex.getMessage(),0L);
			ex.printStackTrace();
		} catch (Exception ex) {
			System.out.println(new Date()+ " - HILO REP LIBROS.: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			auditoriaReporte.insertarAuditoria("LIBRO", 0, "HILO REP LIBRO.: EXECPCION WHILE Exception: "+ex.getMessage(),0L);
			ex.printStackTrace();
		} finally {
			ocupado = false;
		}
	}

	public boolean procesar_solicitudes() {
		ReporteVO reporte = null;
		DatosBoletinVO rep = null;
		try {
			List reportesBol = reportes_Generar();
			Iterator repBol = reportesBol.iterator();
			if (reportesBol != null && reportesBol.size() > 0) {
				// System.out
				// .println("HILO REP LIBROS.: SI HAY LIBROS POR GENERAR: CANTIDAD "
				// + reportesBol.size());
				while (repBol.hasNext()) {
					reporte = null;
					rep = (DatosBoletinVO) repBol.next();
					reporte = setReporte(rep);
					// System.out
					// .println("HILO REP LIBROS.: REPORTE A GENERAR CONSEC: "
					// + rep.getDABOLCONSEC());
					parameters = setParametros(rep);
					// System.out.println("PARAMETROS: " + parameters.values());
					if (!procesar(rep, parameters, reporte)) {
						// System.out
						// .println("HILO REP LIBROS: PROCESAR SOLICITUDES FALLO.");
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_NOGEN,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENREACION");
						bolDAO.updateReporte(reporte);
						// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
						continue;
					}
					// if
					// (!updatePuestoBoletin(puesto,nombreBol,usuarioBol,"update_puesto_boletin_2")){
					// System.out.println("REP boletines.: **NO Se actualizn el puesto del boletin en Datos_Boletin**");
					// continue;
					// }
					if (!update_cola_certficados()) {
						// System.out
						// .println("HILO REP LIBROS: NO Se actualizn la lista de los reportes en cola");
						continue;
					}
					// System.out.println("HILO REP LIBROS: " + getName()
					// + ":Sale While reportes libros.");
				}
			} else {
				Thread.sleep(dormir);
				// System.out.println("REP boletines.: NO HAY BOLETINES A GENERAR");
				// System.out.println("**Se mandaron vaciar las tablas ya que no hay boletines por generar**");
				// vaciarTablas();
				// System.out.print("*");
			}
			return true;
		} catch (InterruptedException ex) {
			System.out
					.println(new Date()
							+ " - HILO REP LIBROS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out
						.println("HILO REP LIBROS: PROCESAR SOLICITUDES FALLO.");
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
				e.printStackTrace();
			}
			return false;

		} catch (Exception ex) {
			System.out
					.println(new Date()
							+ " HILO REP LIBROS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out
						.println("HILO REP LIBROS.: PROCESAR SOLICITUDES FALLO.");
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
			list = bolDAO.getSolicitudesLibros();
			return list;
		} catch (Exception e) {
			System.out
					.println("HILO REP LIBROS.: ERROR LISTADO REPORTES A GENERAR, MOTIVO: "
							+ e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
			} catch (Exception e) {
			}
		}

	}

	public boolean procesar(DatosBoletinVO rep, Map parameterscopy,
			ReporteVO reporte) {

		// parameterscopy=parameters;

		Object[] o;
		list = new ArrayList();
		String nombre;
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
		Connection con = null;
		try {
			bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			// System.out.println("nSe limpiaron las tablas antes de ser llenadas!");
			rep.setDABOLFECHAGEN(new java.sql.Timestamp(System
					.currentTimeMillis()).toString());
			rep.setDABOLFECHAFIN("");
			bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
					ParamsVO.ESTADO_REPORTE_EJE, rep.getDABOLFECHAGEN(),
					rep.getDABOLFECHAFIN());
			reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
			reporte.setMensaje("HILO REP LIBROS:REPORTE EN EJECUCION, CONSEC: "
					+ rep.getDABOLCONSEC());
			bolDAO.updateReporte(reporte);
			if (rep.getDABOLCONSEC() > 0) {
				// System.out
				// .println("HILO REP LIBROS: ENTRO A GENERAR REPORTE. ");

				int nivelGrado = bolDAO
						.getNivelGrado((int) rep.getDABOLGRADO());
				if (nivelGrado == 1) {
					if (rep.getDABOLTIPOEVALPREES() == 1) {
						boletin = 0;
					}
				}
				if (boletin == 1) {
					rep.setDABOLTIPOREP(ParamsVO.TIPO_REPORTE_ASIG);
					if (!bolDAO.llenarTablasCertLibro(rep, reporte)) {
						// System.out
						// .println("HILO REP LIBROS: ERROR PROCEDIMIENTO LIBRO ASIG");
						return false;
					}
				} else {
					rep.setDABOLTIPOREP(ParamsVO.TIPO_REPORTE_DIM);
					if (!bolDAO.llenarTablasDimCertLibro(rep, reporte)) {
						// System.out
						// .println("HILO REP LIBROS.: ERROR PROCEDIMIENTO LIBRO DIM");
						return false;
					}
				}
				if (rep.getDABOLGRADO() < 0) {
					if (rep.getDABOLTIPOEVALPREES() == 1) {
						if (!bolDAO.llenarTablasDimCertLibro(rep, reporte)) {
							// System.out
							// .println("HILO REP LIBROS.: ERROR PROCEDIMIENTO LIBRO DIM: GRADO MENOR Q CERO");
							return false;
						}
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
					File reportFile = setFileJasper(rep);
					// System.out.println("HILO REP LIBROS JASPER: "
					// + reportFile.getPath());
					con = bolDAO.getConnection();
					// System.out
					// .println("HILO REP LIBROS.: HAY DATOS SE MANDA EJECUTAR EL JASPER");
					if ((reportFile.getPath() != null)
							&& (parameterscopy != null)
							&& (!parameterscopy.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("HILO REP LIBROS.:  SE MANDO EJECUTAR JASPER");
						bytes = JasperRunManager.runReportToPdf(
								reportFile.getPath(), parameterscopy, con);
					}
					if (con != null)
						con.close();
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
					reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: "
							+ rep.getDABOLCONSEC());
					bolDAO.updateReporte(reporte);
					// ponerArchivo(modulo,path,bytes1,filtro.getNombreboletinpre());
					ponerArchivo(modulo, path, bytes, rep.getDABOLNOMBREPDF());
					archivosalida = Ruta.get(context,
							rPath.getString("libro.PathLibro"));
					// list.add(archivosalida+filtro.getNombreboletinpre());//pdf
					list.add(archivosalida + rep.getDABOLNOMBREPDF());// pdf
					zise = 100000;
					// System.out.println("HILO REP LIBROS VA A COLOCAR .ZIP");
					if (zip.ponerZip(archivosalida, rep.getDABOLNOMBREZIP(),
							zise, list)) {
						rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitud(rep.getDABOLCONSEC(),
								ParamsVO.ESTADO_REPORTE_GENOK,
								rep.getDABOLFECHAGEN(), rep.getDABOLFECHAFIN());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
						reporte.setMensaje("REPORTE GENERADO SATISFACTORIAMENTE");
						bolDAO.updateReporte(reporte);
						// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
						bolDAO.limpiarTablas(rep.getDABOLCONSEC());
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
					// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
					bolDAO.limpiarTablas(rep.getDABOLCONSEC());
					// System.out
					// .println("HILO REP LIBROS: NO SE HAY REGISTROS REPORTE");
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
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
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
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
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
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				bolDAO.limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return true;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" HILO REP LIBROS WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return true;
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return true;
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
	 * @param byte bit
	 **/

	public void ponerArchivo(String modulo, String path, byte[] bit,
			String archivostatic) {

		try {

			String archivosalida = Ruta.get(context,
					rPath.getString("libro.PathLibro"));
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
			mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje = "  - " + s + "\n";
	}

	public ReporteVO setReporte(DatosBoletinVO rep) {
		ReporteVO reporte = new ReporteVO();
		if (rep != null) {
			String pathComp = rbBol.getString("boletines.PathLibros");
			reporte.setNombre(rep.getDABOLNOMBREZIP());
			reporte.setRecurso(pathComp + rep.getDABOLNOMBREZIP());
			reporte.setModulo(Integer.parseInt(ParamsVO.REP_MODULO_LIBRO));
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

	public Map setParametros(DatosBoletinVO bdt) {
		Map parametros = new HashMap();
		if (bdt != null) {
			parametros = new HashMap();
			parametros.put("usuario", bdt.getDABOLUSUARIO());
			escudo = new File(path2 + "e" + bdt.getDANE().trim() + ".gif");
			// System.out.println("escudo: " + escudo);
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
							path1 + rbBol.getString("imagen"));
				}
				// parameters.put("ESCUDO_COLEGIO_EXISTE",new Integer(0));
			}

			parametros.put("ESCUDO_SED", path2 + rbBol.getString("imagen"));

			parametros.put("PERIODO", new Integer((int) bdt.getDABOLPERIODO()));
			parametros.put("VIGENCIA", new Integer(bdt.getDABOLVIGENCIA()));

			parametros.put("MOSTRAR_OBS", new Integer(bdt.getDABOLFIRREC()));
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
			parametros.put("INSTITUCION", bdt.getDABOLINSNOMBRE());
			parametros.put("SEDE", bdt.getDABOLSEDNOMBRE());
			parametros.put("JORNADA", bdt.getDABOLJORNOMBRE());
			parametros.put("DANE", bdt.getDANE());
		}
		return parametros;
	}

}
