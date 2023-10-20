package siges.gestionAdministrativa.repCarnes.thread;

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
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.comparativos.dao.ComparativosDAO;
import siges.gestionAdministrativa.comparativos.vo.FiltroReportesVO;
import siges.gestionAdministrativa.comparativos.vo.ParamsVO;
import siges.gestionAdministrativa.comparativos.vo.ReporteVO;
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

public class RepCarne extends Thread {
	private static boolean ocupado = false;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private ResourceBundle rPath, rbComp;
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
	private final String modulo = "3";
	// private String s;
	// private String s1;
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
	private ComparativosDAO compDAO;
	int dormir = 0;
	ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();

	/* Constructor de la clase */

	public RepCarne(Cursor c, String cont, String p, String p1, String p2,
			File reporte1_1_, File reporte1_2_, File reporte1_con_,
			File reporte2_1_, File reporte2_2_, File reporte3_1_,
			File reporte3_2_, File reporte4_1_, File reporte4_2_, int nn) {
		super("HILO_REP_COMPARATIVOS: " + nn);
		cursor = c;
		path = p;
		path1 = p1;
		path2 = p2;

		reporte1_1 = reporte1_1_;
		reporte1_2 = reporte1_2_;
		reporte1_con = reporte1_con_;
		reporte2_1 = reporte2_1_;
		reporte2_2 = reporte2_2_;
		reporte3_1 = reporte3_1_;
		reporte3_2 = reporte3_2_;
		reporte4_1 = reporte4_1_;
		reporte4_2 = reporte4_2_;
		context = cont;
		rPath = ResourceBundle.getBundle("path");
		rbComp = ResourceBundle
				.getBundle("siges.gestionAdministrativa.comparativos.bundle.comparativos");
		// s=rb3.getString("valor_s");
		// s1=rb3.getString("valor_s1");
		buscar = buscarjasper = insertar = existeboletin = null;
		err = false;
		mensaje = null;
		codigoestu = null;
		bytes = null;
		vigencia = getVigencia();
		compDAO = new ComparativosDAO(cursor);
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
			dormir = Integer.parseInt(rbComp.getString("comparativos.Dormir"));

			while (ocupado) {
				System.out.println(getName() + ":Espera Thread");
				sleep(dormir);
			}

			ocupado = true;
			while (true) {
				try {
					procesar_solicitudes();
				} catch (Exception e) {
					auditoriaReporte.insertarAuditoria("CARNE", 0, "HILO REP CARNE.: EXECPCION WHILE INTERNO: "+e.getMessage(),0L);
				}
			}
		} catch (InterruptedException ex) {
			System.out.println(new Date()+ " - REP COMPARATIVOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			auditoriaReporte.insertarAuditoria("CARNE", 0, "HILO REP CARNE.: EXECPCION WHILE InterruptedException: "+ex.getMessage(),0L);
			ex.printStackTrace();
		} catch (Exception ex) {
			System.out.println(new Date()+ " - REP COMPARATIVOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			auditoriaReporte.insertarAuditoria("CARNE", 0, "HILO REP CARNE.: EXECPCION WHILE Exception: "+ex.getMessage(),0L);
			ex.printStackTrace();
		} finally {
			ocupado = false;
		}
	}

	public boolean procesar_solicitudes() {
		ReporteVO reporte = null;
		FiltroReportesVO rep = null;
		try {
			List reportesComp = reportes_Generar();
			Iterator repComp = reportesComp.iterator();
			if (reportesComp != null && reportesComp.size() > 0) {
				// System.out
				// .println("REP COMPARATIVOS: SI HAY BOLETINES POR GENERAR: CANTIDAD "
				// + reportesComp.size());
				while (repComp.hasNext()) {
					rep = (FiltroReportesVO) repComp.next();
					reporte = null;
					// System.out
					// .println("REP COMPARATIVOS: REPORTE A GENERAR CONSEC: "
					// + rep.getConsec());
					parameters = setParametros(rep);
					// System.out.println("PARAMETROS: " + parameters.values());
					reporte = setReporte(rep);
					if (!procesar(rep, parameters, reporte)) {
						// System.out
						// .println("REP COMPARATIVOS: PROCESAR SOLICITUDES FALLO.");
						rep.setFechaFin(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						compDAO.updateSolicitud(rep.getConsec(),
								ParamsVO.ESTADO_REPORTE_NOGEN,
								rep.getFechaGen(), rep.getFechaFin());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
						reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
						compDAO.updateReporte(reporte);
						// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
						compDAO.limpiarTablas(rep.getConsec());
						continue;
					}
					// if
					// (!updatePuestoBoletin(puesto,nombreBol,usuarioBol,"update_puesto_boletin_2")){
					// System.out.println("REP COMPARATIVOS: **NO Se actualizn el puesto del boletin en Datos_Boletin**");
					// continue;
					// }
					if (!update_cola_boletines()) {
						// System.out
						// .println("REP COMPARATIVOS: NO Se actualizn la lista de los reportes en cola");
						continue;
					}
					// System.out.println("REP COMPARATIVOS: " + getName()
					// + ":Sale While reportes Comparativos");
				}
			} else {
				Thread.sleep(dormir);
				// System.out.println("REP COMPARATIVOS: NO HAY BOLETINES A GENERAR");
				// System.out.println("**Se mandaron vaciar las tablas ya que no hay boletines por generar**");
				// vaciarTablas();
				// System.out.print("*");
			}
			return true;
		} catch (InterruptedException ex) {
			System.out
					.println(new Date()
							+ " - REP COMPARATIVOS: EXECPCION EN HILO RUN,INTERRUPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out
						.println("REP COMPARATIVOS: PROCESAR SOLICITUDES FALLO.");
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				compDAO.updateSolicitud(rep.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
						rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				compDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				compDAO.limpiarTablas(rep.getConsec());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		} catch (Exception ex) {
			System.out
					.println(new Date()
							+ " - REP COMPARATIVOS: EXECPCION EN HILO RUN,EXCEPCION. SE CAYO HILO");
			ex.printStackTrace();
			try {
				System.out
						.println("REP COMPARATIVOS: PROCESAR SOLICITUDES FALLO.");
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				compDAO.updateSolicitud(rep.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
						rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
				compDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				compDAO.limpiarTablas(rep.getConsec());
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
			list = compDAO.getSolicitudes();
			return list;
		} catch (Exception e) {
			System.out
					.println("REP COMPARATIVOS: ERROR LISTADO REPORTES A GENERAR, MOTIVO: "
							+ e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
			} catch (Exception e) {
			}
		}

	}

	public boolean procesar(FiltroReportesVO rep, Map parameterscopy,
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
		try {
			compDAO.limpiarTablas(rep.getConsec());
			// System.out.println("nSe limpiaron las tablas antes de ser llenadas!");
			rep.setFechaGen(new java.sql.Timestamp(System.currentTimeMillis())
					.toString());
			rep.setFechaFin("");
			compDAO.updateSolicitud(rep.getConsec(),
					ParamsVO.ESTADO_REPORTE_EJE, rep.getFechaGen(),
					rep.getFechaFin());
			reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
			reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: "
					+ rep.getConsec());
			compDAO.updateReporte(reporte);
			if (rep.getConsec() > 0) {
				// System.out
				// .println("REP COMPARATIVOS: ENTRO A GENERAR REPORTE. TIPO: "
				// + rep.getTipoReporte());
				if (!compDAO.llenarTablas(rep, reporte)) {
					// System.out
					// .println("REP COMPARATIVOS: ERROR PROCEDIMIENTO BOLETIN");
					return false;
				}
				// VALIDAR SI NO HUBO ERROR EN EL PROC
				if (compDAO.validarEstadoReporte(rep.getConsec()) == ParamsVO.ESTADO_REPORTE_NOGEN)
					return true;
				/* Consulta para ejecutar el/los jasper */
				rep = compDAO.datosConv(rep, reporte);
				if (compDAO.validarDatosReporte(rep)) {
					parameterscopy.put("CONVINST", rep.getConvInts());
					parameterscopy.put("CONVMEN", rep.getConvMen());
					File reportFile = setFileJasper(rep);
					Connection con = compDAO.getConnection();
					// System.out
					// .println("REP COMPARATIVOS: HAY DATOS SE MANDA EJECUTAR EL JASPER");
					if ((reportFile.getPath() != null)
							&& (parameterscopy != null)
							&& (!parameterscopy.values().equals("0"))
							&& (con != null)) {
						// System.out
						// .println("REP COMPARATIVOS:  SE MANDO EJECUTAR JASPER");
						bytes = JasperRunManager.runReportToPdf(
								reportFile.getPath(), parameterscopy, con);
					}
					// Cerrar conexion despues del llamado del jasper
					if (con != null)
						con.close();
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_EJE);
					reporte.setMensaje("REPORTE EN EJECUCION, CONSEC: "
							+ rep.getConsec());
					compDAO.updateReporte(reporte);
					// ponerArchivo(modulo,path,bytes1,filtro.getNombreboletinpre());
					ponerArchivo(modulo, path, bytes, nombreRepPdf);
					archivosalida = Ruta.get(context,
							rPath.getString("boletines.PathBoletin"));
					// list.add(archivosalida+filtro.getNombreboletinpre());//pdf
					list.add(archivosalida + nombreRepPdf);// pdf
					zise = 100000;

					if (zip.ponerZip(archivosalida, nombreRep, zise, list)) {
						rep.setFechaFin(new java.sql.Timestamp(System
								.currentTimeMillis()).toString());
						compDAO.updateSolicitud(rep.getConsec(),
								ParamsVO.ESTADO_REPORTE_GENOK,
								rep.getFechaGen(), rep.getFechaFin());
						reporte.setEstado(ParamsVO.ESTADO_REPORTE_GENOK);
						reporte.setMensaje("REPORTE GENERADO SATISFACTORIAMENTE");
						compDAO.updateReporte(reporte);
						// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
						compDAO.limpiarTablas(rep.getConsec());
						return true;
					}
				}// fin de rs
				else {
					rep.setFechaFin(new java.sql.Timestamp(System
							.currentTimeMillis()).toString());
					compDAO.updateSolicitud(rep.getConsec(),
							ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
							rep.getFechaFin());
					reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
					reporte.setMensaje("NO SE GENERO REPORTE, MOTIVO: NO SE ENCONTRARON REGISTROS PARA LA GENERACION");
					compDAO.updateReporte(reporte);
					// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
					compDAO.limpiarTablas(rep.getConsec());
					return true;
				}

			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			try {
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				compDAO.updateSolicitud(rep.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
						rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				compDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				compDAO.limpiarTablas(rep.getConsec());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		} catch (JRException e) {
			e.printStackTrace();
			try {
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				compDAO.updateSolicitud(rep.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
						rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				compDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				compDAO.limpiarTablas(rep.getConsec());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				compDAO.updateSolicitud(rep.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
						rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				compDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				compDAO.limpiarTablas(rep.getConsec());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			System.out
					.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "
							+ e.toString());
			try {
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				compDAO.updateSolicitud(rep.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
						rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, ERROR MEMORIA, EXCEPCION: "
						+ e.getMessage());
				compDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				compDAO.limpiarTablas(rep.getConsec());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rep.setFechaFin(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				compDAO.updateSolicitud(rep.getConsec(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getFechaGen(),
						rep.getFechaFin());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("OCURRIO ERROR GENERACION REPORTE, EXCEPCION: "
						+ e.getMessage());
				compDAO.updateReporte(reporte);
				// siges.util.Logger.print(usuarioBol,"Excepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
				compDAO.limpiarTablas(rep.getConsec());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {

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
					rPath.getString("comparativos.PathComparativo"));
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
			compDAO.limpiarTablas(consecRep);
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

			pst = con.prepareStatement(rbComp.getString(prepared));
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
			pst = con.prepareStatement(rbComp
					.getString("update_puesto_boletin_1"));
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
			pst = con.prepareStatement(rbComp.getString("vigencia"));
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

	public ReporteVO setReporte(FiltroReportesVO rep) {
		ReporteVO reporte = new ReporteVO();
		if (rep != null) {
			String pathComp = rPath.getString("comparativos.PathComparativo");
			reporte.setNombre(rep.getNombre_zip());
			reporte.setRecurso(pathComp + rep.getNombre_zip());
			reporte.setModulo(Integer.parseInt(ParamsVO.REP_MODULO));
			reporte.setFecha(rep.getFecha());
			reporte.setTipo("zip");
			reporte.setUsuario(rep.getUsuario());
		}
		return reporte;
	}

	public File setFileJasper(FiltroReportesVO rep) {
		File reporte = null;
		if (rep != null) {
			if (rep.getTipoReporte() == ParamsVO.TIPO_REPORTE_1) {
				if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_NUM) {
					reporte = reporte1_1;
				} else if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_CON
						|| rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_MEN) {
					reporte = reporte1_con;
				}
			} else if (rep.getTipoReporte() == ParamsVO.TIPO_REPORTE_2) {
				if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_NUM) {
					reporte = reporte2_1;
				} else if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_CON
						|| rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_MEN) {
					reporte = reporte2_2;
				}
			} else if (rep.getTipoReporte() == ParamsVO.TIPO_REPORTE_3) {
				if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_NUM) {
					reporte = reporte3_1;
				} else if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_CON
						|| rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_MEN) {
					reporte = reporte3_2;
				}
			} else if (rep.getTipoReporte() == ParamsVO.TIPO_REPORTE_4) {
				if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_NUM) {
					reporte = reporte4_1;
				} else if (rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_CON
						|| rep.getTipoEscala() == ParamsVO.TIPO_REPORTE_ESCALA_MEN) {
					reporte = reporte4_1;
				}
			}
		}
		return reporte;
	}

	public Map setParametros(FiltroReportesVO rep) {
		Map parametros = new HashMap();
		if (rep != null) {
			parametros = new HashMap();
			parametros.put("usuario", rep.getUsuario());
			parametros.put("ESCUDO_SED", path1 + rbComp.getString("imagen"));
			parametros.put("PERIODO", new Integer((int) rep.getPeriodo()));
			parametros.put("VIGENCIA", new Integer((int) rep.getVig()));
			parametros.put("CONSECCOM", new Long(rep.getConsec()));
			parametros.put("NOMBRE_PER_FINAL", rep.getNomPerFinal());
			parametros.put("NUM_PERIODOS", new Integer((int) rep.getNumPer()));
			parametros.put("INSTITUCION", rep.getInst_nombre());
			parametros.put("SEDE", rep.getSede_nombre());
			parametros.put("JORNADA", rep.getJornd_nombre());
		}
		return parametros;
	}

}
