package siges.gestionAdministrativa.repResultadosAca.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import siges.boletines.ControllerAuditoriaReporte;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.vo.DatosComVigAntVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.repResultadosAca.dao.RepResultadosAcaDAO;
import siges.gestionAdministrativa.repResultadosAca.vo.ParamsVO;
import siges.gestionAdministrativa.repResultadosAca.vo.ReporteVO;
import siges.io.Zip;

/**
 * Nombre: Reporte comparativo vigencisa anterior<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: <BR>
 * Fecha de creacinn: 02/06/2012 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class RepResultadosComVigAnterior extends Thread {
	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle rPath, rbRep;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanReports filtro;
	private String buscarcodigo;

	private final String modulo = "26";
	// private String s;
	// private String s1;
	private byte[] bytes;
	private byte[] bytes2;
	private byte[] bytes3;
	private Map parameters;
	private Map copyparameters;

	private File reporte1;

	private File escudo;
	private String path, path_escudo, path2;
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
	private RepResultadosAcaDAO bolDAO;
	int dormir = 0;
	ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();

	/* Constructor de la clase */

	public RepResultadosComVigAnterior(Cursor c, String cont, String p,
			String p1, File reporte1_1_, int nn) {
		super("HILO_REP_RESULTADOS_COMP: " + nn);
		cursor = c;
		path = p;
		path_escudo = p1;

		reporte1 = reporte1_1_;

		context = cont;
		rPath = ResourceBundle.getBundle("path");
		rbRep = ResourceBundle
				.getBundle("siges.gestionAdministrativa.repResultadosAca.bundle.repResultadosAca");

		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;

		bolDAO = new RepResultadosAcaDAO(cursor);
		// System.out.println(":Entra Thread de com vig ant");
	}

	public void run() {
		Object[] o = new Object[2];
		int posicion = 1;
		String[][] array = null;
		dormir = 0;
		String cola = null;
		String puest = "-999";

		try {
			Thread.sleep(60000);
			dormir = Integer.parseInt(rbRep.getString("rep.Dormir"));

			while (ocupado) {
				// System.out.println(getName() + ":Espera Thread");
				sleep(dormir);
			}

			ocupado = true;
			// System.out.println(getName() + ":Entra Thread");
			while (true) {
				try {
					procesar_solicitudes();
				} catch (Exception e) {
					auditoriaReporte.insertarAuditoria("RESULTADO COM VIG ANTERIOR", 0, "HILO REP RESULTADO COM VIG ANTERIOR.: EXECPCION WHILE INTERNO: "+e.getMessage(),0L);
					e.printStackTrace();
				}
			}
		} catch (InterruptedException ex) {
			auditoriaReporte.insertarAuditoria("RESULTADO COM VIG ANTERIOR", 0, "HILO REP RESULTADO COM VIG ANTERIOR.: EXECPCION WHILE InterruptedException: "+ex.getMessage(),0L);
			ex.printStackTrace();

		} catch (Exception ex) {
			auditoriaReporte.insertarAuditoria("RESULTADO COM VIG ANTERIOR", 0, "HILO REP RESULTADO COM VIG ANTERIOR.: EXECPCION WHILE Exception: "+ex.getMessage(),0L);
			ex.printStackTrace();

		} finally {
			ocupado = false;
		}
	}

	public boolean procesar_solicitudes() {
		ReporteVO reporte = null;
		DatosComVigAntVO rep = null;
		try {
			List reportesBol = reportes_Generar();
			Iterator repBol = reportesBol.iterator();
			if (reportesBol != null && reportesBol.size() > 0) {

				while (repBol.hasNext()) {
					reporte = null;
					rep = (DatosComVigAntVO) repBol.next();
					reporte = setReporte(rep);
					reporte.setModulo(27);

					parameters = setParametros(rep);
					/*
					 * System.out.println("PARAMETROS COM VIG ANT: " +
					 * parameters.values());
					 */
					if (!procesar(rep, parameters, reporte)) {

						rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitudCompVigenciaAnt(
								ParamsVO.ESTADO_REPORTE_NOGEN,
								rep.getDACOMPANTFECHAGEN(),
								rep.getDACOMPANTFECHAFIN(),
								rep.getDACOMPANTINST(),
								rep.getDACOMPANTVIGENCIA(),
								rep.getDACOMPANTPERIODO());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENREACION");

						bolDAO.updateReporte(reporte);
						// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());

						// //bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
						continue;
					}
					// if
					// (!updatePuestoBoletin(puesto,nombreBol,usuarioBol,"update_puesto_boletin_2")){
					// System.out.println("REP boletines.: **NO Se actualizn el puesto del boletin en Datos_Boletin**");
					// continue;
					// }
					if (!update_cola_certficados()) {
						// System.out
						// .println("HILO REP RESULTADOS: NO Se actualizn la lista de los reportes en cola");
						continue;
					}
					// System.out.println("HILO REP RESULTADOS: " + getName()
					// + ":Sale While reportes certificados.");
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
							+ " - HILO REP RESULTADOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out
						.println("HILO REP RESULTADOS: PROCESAR SOLICITUDES FALLO.");
				rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());

				bolDAO.updateSolicitudCompVigenciaAnt(
						ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO());

				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				reporte.setModulo(27);

				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				// //bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		} catch (Exception ex) {
			/*
			 * System.out .println(new Date() +
			 * " HILO REP RESULTADOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO"
			 * );
			 */
			ex.printStackTrace();
			try {
				System.out
						.println("HILO REP RESULTADOS.: PROCESAR SOLICITUDES FALLO.");
				rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitudCompVigenciaAnt(
						ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO());
				reporte.setModulo(27);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				// //bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
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
			list = bolDAO.getSolicitudesResCompViganeterior();
			return list;
		} catch (Exception e) {
			/*
			 * System.out .println(
			 * "HILO REP RESULTADOS.: ERROR LISTADO REPORTES A GENERAR, MOTIVO: "
			 * + e.getMessage());
			 */
			e.printStackTrace();
			return null;
		} finally {
			try {
			} catch (Exception e) {
			}
		}

	}

	public boolean procesar(DatosComVigAntVO rep, Map parameterscopy,
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
		bytes2 = null;
		bytes3 = null;
		String cont = null;
		int boletin = 1;
		Connection con = null;
		try {
			// //bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
			// System.out.println("nSe limpiaron las tablas antes de ser llenadas!");
			rep.setDACOMPANTFECHAGEN(new java.sql.Timestamp(System
					.currentTimeMillis()).toString());
			rep.setDACOMPANTFECHAFIN("");
			bolDAO.updateSolicitudCompVigenciaAnt(ParamsVO.ESTADO_REPORTE_EJE,
					rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
					rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
					rep.getDACOMPANTPERIODO());
			reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
			reporte.setMensaje("HILO REP RESULTADOS:REPORTE EN EJECUCION, CONSEC: "
					+ rep.DACOMPANTNOMBREZIP);
			bolDAO.updateReporte(reporte);
			if (rep.getDACOMPANTESTADO() == -1) {
				// System.out
				// .println("HILO REP RESULTADOS: ENTRO A GENERAR REPORTE. ");
				// if (!bolDAO.llenarTablas(rep, reporte)) {
				// System.out
				// .println("HILO REP RESULTADOS: ERROR PROCEDIMIENTO RESULTADOS ACA");
				// return false;
				// }
				// VALIDAR SI NO HUBO ERROR EN EL PROC
				if (bolDAO.validarEstadoReporteCompVigAnt(
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO()) == ParamsVO.ESTADO_REPORTE_NOGEN)
					return true;

				con = bolDAO.getConnection();
				// System.out
				// .println("HILO REP RESULTADOS.: HAY DATOS SE MANDA EJECUTAR EL JASPER");
				String nombrePdf = rep.getDACOMPANTNOMBREPDF();
				String nombrePdf1 = nombrePdf.replaceFirst(".pdf", "_1.xls");

				// ponerArchivo(modulo,path,bytes3,nombrePdf3);
				String ruta1 = "";

				archivosalida = Ruta.get(context,
						rPath.getString("resultados.PathResultados"));
				boolean continuar = true;
				if ((reporte1.getPath() != null) && (parameterscopy != null)
						&& (!parameterscopy.values().equals("0"))
						&& (con != null)) {
					// System.out
					// .println("HILO REP RESULTADOS.:  SE MANDO EJECUTAR JASPERS");
					// System.out
					// .println("HILO REP RESULTADOS.:  SE MANDO EJECUTAR JASPER 1");
					// bytes=JasperRunManager.runReportToPdf(reporte1.getPath(),parameterscopy,con);
					ruta1 = getArchivoXLS(reporte1, parameterscopy,
							archivosalida, nombrePdf1);

					// bytes2=JasperRunManager.runReportToPdf(reporte2.getPath(),parameterscopy,con);

					// System.out
					// .println("HILO REP RESULTADOS.:  SE EJECUTARON LOS 3 JASPERS EXCEL");
					/*
					 * COPIA DE UN SOLO REPORTE PARA CAMBIOS DE TODOS LOS
					 * REPORTES File reportFile=setFileJasper(rep);
					 * System.out.println
					 * ("HILO REP RESULTADOS JASPER: "+reportFile.getPath());
					 * con=bolDAO.getConnection(); System.out.println(
					 * "HILO REP RESULTADOS.: HAY DATOS SE MANDA EJECUTAR EL JASPER"
					 * ); if((reportFile.getPath()!=null) &&
					 * (parameterscopy!=null)
					 * &&(!parameterscopy.values().equals("0")) && (con!=null)){
					 * System.out.println(
					 * "HILO REP RESULTADOS.:  SE MANDO EJECUTAR JASPER");
					 * bytes=
					 * JasperRunManager.runReportToPdf(reportFile.getPath()
					 * ,parameterscopy,con); }
					 */

					if (con != null)
						con.close();
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
					reporte.setMensaje("REP_RESULTADOS:REPORTE EN EJECUCION, CONSEC: "
							+ rep.getDACOMPANTNOMBREZIP());
					bolDAO.updateReporte(reporte);
					// ponerArchivo(modulo,path,bytes1,filtro.getNombreboletinpre());

					// list.add(archivosalida+filtro.getNombreboletinpre());//pdf
					list.add(ruta1);// xls

					zise = 100000;
					// System.out.println("HILO REP RESULTADOS VA A COLOCAR .ZIP");
					if (zip.ponerZip(archivosalida,
							rep.getDACOMPANTNOMBREZIP(), zise, list)) {
						rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						bolDAO.updateSolicitudCompVigenciaAnt(
								ParamsVO.ESTADO_REPORTE_GENOK,
								rep.getDACOMPANTFECHAGEN(),
								rep.getDACOMPANTFECHAFIN(),
								rep.getDACOMPANTINST(),
								rep.getDACOMPANTVIGENCIA(),
								rep.getDACOMPANTPERIODO());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
						reporte.setMensaje("HILO REP RESULTADOS: REPORTE GENERADO SATISFACTORIAMENTE");
						bolDAO.updateReporte(reporte);
						// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
						// bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
						return true;
					}
				}// fin de rs
				else {
					rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
							.currentTimeMillis()).toString());
					bolDAO.updateSolicitudCompVigenciaAnt(
							ParamsVO.ESTADO_REPORTE_NOGEN,
							rep.getDACOMPANTFECHAGEN(),
							rep.getDACOMPANTFECHAFIN(), rep.getDACOMPANTINST(),
							rep.getDACOMPANTVIGENCIA(),
							rep.getDACOMPANTPERIODO());
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
					reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
					bolDAO.updateReporte(reporte);
					// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
					// bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
					// System.out
					// .println("HILO REP RESULTADOS: NO HAY REGISTROS REPORTE");
					return true;
				}

			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			try {
				rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitudCompVigenciaAnt(
						ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				// bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		} catch (JRException e) {
			e.printStackTrace();
			try {
				rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitudCompVigenciaAnt(
						ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				// bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitudCompVigenciaAnt(
						ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				// bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" HILO REP RESULTADOS WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			try {
				rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitudCompVigenciaAnt(
						ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, ERROR MEMORIA, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				// bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rep.setDACOMPANTFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				bolDAO.updateSolicitudCompVigenciaAnt(
						ParamsVO.ESTADO_REPORTE_NOGEN,
						rep.getDACOMPANTFECHAGEN(), rep.getDACOMPANTFECHAFIN(),
						rep.getDACOMPANTINST(), rep.getDACOMPANTVIGENCIA(),
						rep.getDACOMPANTPERIODO());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				bolDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				// bolDAO.limpiarTablas(rep.getDACOMPANTCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				bolDAO.eliminarregistrosreporteGenerado(rep
						.getDACOMPANTCONSECUTIVO());
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
			// bolDAO.limpiarTablas(consecRep);
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
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbRep.getString("update_puesto_rep_2"));
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

	public ReporteVO setReporte(DatosComVigAntVO rep) {
		ReporteVO reporte = new ReporteVO();
		if (rep != null) {
			String pathComp = rbRep.getString("rep.PathReporte");
			reporte.setNombre(rep.getDACOMPANTNOMBREZIP());
			reporte.setRecurso(pathComp + rep.getDACOMPANTNOMBREZIP());
			reporte.setModulo(Integer.parseInt(ParamsVO.REP_MODULO));
			reporte.setFecha(rep.getDACOMPANTFECHA());
			reporte.setTipo("zip");
			reporte.setUsuario(rep.getDACOMPANTUSUARIO());
		}
		return reporte;
	}

	public File setFileJasper(DatosComVigAntVO rep) {
		File reporte = null;
		if (rep != null) {
			reporte = reporte1;
		}

		return reporte;
	}

	/**
	 * Parametros a poner
	 * 
	 * @param bdt
	 * @return
	 * @throws Exception
	 */
	public Map setParametros(DatosComVigAntVO bdt) throws Exception {
		System.out.println("pone parametros");
		Map parametros = new HashMap();
		if (bdt != null) {
			parametros = new HashMap();
			parametros.put("usuario", bdt.getDACOMPANTUSUARIO());

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
							path_escudo + rbRep.getString("rep.imagen"));
				}
				// parameters.put("ESCUDO_COLEGIO_EXISTE",new Integer(0));
			}

			parametros.put("ESCUDO_SED",
					path_escudo + rbRep.getString("rep.imagen"));

			parametros
					.put("PERIODO", new Long((int) bdt.getDACOMPANTPERIODO()));
			parametros.put("anio", new Long(bdt.getDACOMPANTVIGENCIA()));
			parametros.put("instid", new Long(bdt.getDACOMPANTINST()));
			parametros.put("SUBREPORT_DIR", path);
			long vigenciaActual = bolDAO
					.getVigenciaInst(bdt.getDACOMPANTINST());
			parametros.put("anioactual", new Long(vigenciaActual));

			parametros.put("INSTITUCION",
					bolDAO.getNombreColegio(bdt.getDACOMPANTINST()));

			parametros.put("CONSEC", new Long(bdt.getDACOMPANTCONSECUTIVO()));
			parametros.put("DANE", bdt.getDANE());

			parametros.put(
					"contanio",
					new Long(bolDAO.contarRegistrosReporte(
							bdt.getDACOMPANTVIGENCIA(), bdt.getDACOMPANTINST(),
							(int) bdt.getDACOMPANTPERIODO(),
							bdt.getDACOMPANTCONSECUTIVO())));
			parametros.put(
					"contanioactual",
					new Long(bolDAO.contarRegistrosReporte(vigenciaActual,
							bdt.getDACOMPANTINST(),
							(int) bdt.getDACOMPANTPERIODO(),
							bdt.getDACOMPANTCONSECUTIVO())));

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
			// System.out.println("reporte " + reportFile.getPath());
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
			// System.out.println("ruta rep " + xlsFilesSource);
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
