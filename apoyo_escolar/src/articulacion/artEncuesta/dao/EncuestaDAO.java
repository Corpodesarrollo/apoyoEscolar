/**
 * 
 */
package articulacion.artEncuesta.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import siges.common.vo.ItemVO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import siges.io.Zip;
import siges.login.beans.Login;
import net.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import articulacion.artEncuesta.vo.ColegioVO;
import articulacion.artEncuesta.vo.ConsolidadoVO;
import articulacion.artEncuesta.vo.Encuesta3VO;
import articulacion.artEncuesta.vo.Encuesta4VO;
import articulacion.artEncuesta.vo.EncuestaVO;
import articulacion.artEncuesta.vo.Encuesta2VO;
import articulacion.artEncuesta.vo.EspecialidadVO;
import articulacion.artEncuesta.vo.EstudianteVO;
import articulacion.artEncuesta.vo.LocalidadVO;
import articulacion.artEncuesta.vo.MostrarEncuestaVO;
import articulacion.artEncuesta.vo.ParamsVO;
import articulacion.artEncuesta.vo.UniversidadVO;
import siges.common.vo.ItemVO;
import siges.dao.*;
import siges.exceptions.InternalErrorException;

/**
 * 5/12/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class EncuestaDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 */
	private String mensaje;

	public EncuestaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.artEncuesta.bundle.Encuesta");
	}

	public List getSedes(String inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO sedeVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getSede"));
			i = 1;
			// System.out.println("INSTITUCION"+ inst);
			st.setInt(i++, Integer.parseInt(inst));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				sedeVO = new ItemVO();
				sedeVO.setCodigo(rs.getInt(i++));
				sedeVO.setNombre(rs.getString(i++));
				l.add(sedeVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getJornada(String inst, String sede) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO jornadaVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getJornada"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));
			st.setInt(i++, Integer.parseInt(sede));

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				jornadaVO = new ItemVO();
				jornadaVO.setPadre(rs.getInt(i++));
				jornadaVO.setCodigo(rs.getInt(i++));
				jornadaVO.setNombre(rs.getString(i++));
				l.add(jornadaVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getAjaxGrupo(long inst, int sede, int jornada, int grado,
			String metodologia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ItemVO lp = null;

		int i = 0;
		try {
			if (inst == 0 || sede == 0 || jornada == 0 || grado == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxGrupo"));
			i = 1;
			st.setLong(i++, inst);
			st.setLong(i++, sede);
			st.setLong(i++, jornada);
			st.setLong(i++, grado);
			st.setLong(i++, Long.parseLong(metodologia));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ItemVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lpA;
	}

	public List getEstudianteEnc(EncuestaVO filtro) throws Exception {
		List l = new ArrayList();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		EstudianteVO estudiante = null;
		int i = 0;
		try {
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// System.out.println("1.))"+"getEstudianteComportamiento."+filtro.getPlaOrden()+"."+filtro.getPlaPeriodo());
			// System.out.println("2."+filtro.getPlaJerarquia());
			int estado = filtro.getPlaEstado();
			if (filtro.getPlaOrden() == -99) {
				filtro.setPlaOrden(1);
			}
			if (estado == -99)
				st = cn.prepareStatement(rb.getString("getEstudianteEncuesta")
						+ " "
						+ rb.getString("getEstudianteEncuesta.orden"
								+ filtro.getPlaOrden()));
			if (estado == 1)
				st = cn.prepareStatement(rb.getString("getEstudianteEncuesta1")
						+ " "
						+ rb.getString("getEstudianteEncuesta.orden"
								+ filtro.getPlaOrden()));
			if (estado == 2)
				st = cn.prepareStatement(rb.getString("getEstudianteEncuesta2")
						+ " "
						+ rb.getString("getEstudianteEncuesta.orden"
								+ filtro.getPlaOrden()));
			if (estado == 3)
				st = cn.prepareStatement(rb.getString("getEstudianteEncuesta3")
						+ " "
						+ rb.getString("getEstudianteEncuesta.orden"
								+ filtro.getPlaOrden()));
			i = 1;
			st.setLong(i++, filtro.getPlaInstitucion());
			st.setInt(i++, filtro.getPlaSede());
			st.setInt(i++, filtro.getPlaJornada());
			st.setInt(i++, filtro.getPlaMetodologia());
			st.setInt(i++, filtro.getPlaGrado());
			st.setInt(i++, filtro.getPlaGrupo());
			st.setInt(i++, filtro.getPlaGrupo());
			rs = st.executeQuery();
			int index = 1;
			while (rs.next()) {
				i = 1;
				estudiante = new EstudianteVO();
				estudiante.setEstApellido(rs.getString(i++));
				estudiante.setEstNombre(rs.getString(i++));
				estudiante.setEstNumDoc(rs.getString(i++));
				estudiante.setEstTipoDoc(rs.getString(i++));
				estudiante.setEstCodigo(rs.getLong(i++));
				estudiante.setEstEstado(rs.getString(i++));
				estudiante.setEstFechaEnc(rs.getString(i++));
				estudiante.setEstConsecutivo(index++);
				l.add(estudiante);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getAjaxGrado(long inst, int sede, int jornada,
			String metodologia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ItemVO lp = null;
		int i = 0;
		try {
			if (inst == 0 || sede == 0 || jornada == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxGrado"));
			i = 1;
			st.setLong(i++, inst);
			st.setLong(i++, Long.parseLong(metodologia));
			st.setLong(i++, sede);
			st.setLong(i++, jornada);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ItemVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lpA;
	}

	public boolean generarReporte(EncuestaVO filtro,
			HttpServletRequest request, long usuario, Map parameters,
			int modulo, String nivel) throws Exception {
		String[] reportes = null;
		String contextoTotal;
		ServletContext context = (ServletContext) request.getSession()
				.getServletContext();
		contextoTotal = context.getRealPath("/");
		File reportFile;
		File reportFile2;
		File reportFile3;
		String path;
		String path1;
		String path2;
		File escudo = null;
		String nombrerep1;
		String nombrerep2;
		String nombrerep3;
		String localidad;
		String colegio;
		String sede;
		String jornada;
		String archivo;
		String archivoExcel;
		String archivozip;
		String dane;
		Connection con = null;

		String archivosalida = null;
		String archivosalidaExcel = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		java.sql.Timestamp f2;
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		path = Ruta2
				.get(context.getRealPath("/"), rb.getString("ruta_jaspers"));
		path1 = Ruta2.get(context.getRealPath("/"), rb.getString("ruta_img"));
		path2 = Ruta2.get(context.getRealPath("/"),
				rb.getString("ruta_img_inst"));
		parameters.put("SUBREPORT_DIR", path);
		parameters.put("PATH_ICONO_SECRETARIA", path1 + rb.getString("imagen"));
		parameters.put("PATH_ICONO_ALCALDIA",
				path1 + rb.getString("imagenAlcaldia"));

		dane = getDaneInstitucion(filtro.getPlaInstitucion(),
				filtro.getPlaLocalidad());
		escudo = new File(path2 + "e" + dane + ".gif");
		if (escudo.exists())
			parameters.put("PATH_ICONO_INSTITUCION", path2 + "e" + dane
					+ ".gif");
		else
			parameters.put("PATH_ICONO_INSTITUCION",
					path1 + rb.getString("imagen"));

		reportFile = new File(path + rb.getString("jasper_listado"));
		byte[] reporte1 = null;
		localidad = (!(filtro.getPlaLocalidad() == -99) ? "Localidad_"
				+ filtro.getPlaLocalidad() : "");
		colegio = (!(filtro.getPlaInstitucion() == -99) ? "_Colegio_"
				+ filtro.getPlaInstitucion() + "_" : "");
		sede = (!(filtro.getPlaSede() == -99) ? "Sede_" + filtro.getPlaSede()
				: "");
		// System.out.println("*SEDE*"+sede);
		jornada = (!(filtro.getPlaJornada() == -99) ? "_Jornada_"
				+ filtro.getPlaJornada() : "");
		// System.out.println("*JORNADA*"+jornada);

		nombrerep1 = sede
				+ jornada
				+ "_Fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-');
		archivo = "Estudiantes_x_encuesta_" + nombrerep1 + ".pdf";
		archivoExcel = "Estudiantes_x_encuesta_" + nombrerep1 + ".xls";
		archivozip = "Estudiantes_x_encuesta_" + nombrerep1 + ".zip";

		if (modulo == ParamsVO.moduloListado) {
			reportFile = new File(path + rb.getString("jasper_listado"));
			sede = (!(filtro.getPlaSede() == -99) ? "Sede_"
					+ filtro.getPlaSede() : "");
			jornada = (!(filtro.getPlaJornada() == -99) ? "_Jornada_"
					+ filtro.getPlaJornada() : "");
			if (nivel.equals("4") || nivel.equals("6")) {
				nombrerep1 = sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
			} else if (nivel.equals("1") || nivel.equals("2")) {
				nombrerep1 = localidad
						+ colegio
						+ sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
			}

			archivo = "Estudiantes_x_encuesta_" + nombrerep1 + ".pdf";
			archivoExcel = "Estudiantes_x_encuesta_" + nombrerep1 + ".xls";
			archivozip = "Estudiantes_x_encuesta_" + nombrerep1 + ".zip";

		} else if (modulo == ParamsVO.moduloConsolidado) {
			reportFile = new File(path + rb.getString("jasper_consolidado"));
			if (nivel.equals("4") || nivel.equals("6")) {
				nombrerep1 = sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
			} else if (nivel.equals("1") || nivel.equals("2")) {
				nombrerep1 = localidad
						+ colegio
						+ sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
			}
			archivo = "consolidado_x_encuesta_" + nombrerep1 + ".pdf";
			archivoExcel = "consolidado_x_encuesta_" + nombrerep1 + ".xls";
			archivozip = "consolidado_x_encuesta_" + nombrerep1 + ".zip";

		} else if (modulo == ParamsVO.moduloConsolidadoGeneral) {
			if (filtro.getNumeroPregunta() == -99) {
				reportFile = new File(path
						+ rb.getString("jasper_consolidado_general"));
				if (nivel.equals("4") || nivel.equals("6")) {
					nombrerep1 = sede
							+ jornada
							+ "_Fecha_"
							+ f2.toString().replace(' ', '_').replace(':', '-')
									.replace('.', '-');
				} else if (nivel.equals("1") || nivel.equals("2")) {
					nombrerep1 = localidad
							+ colegio
							+ sede
							+ jornada
							+ "_Fecha_"
							+ f2.toString().replace(' ', '_').replace(':', '-')
									.replace('.', '-');
				}
				archivo = "consolidado_general_" + nombrerep1 + ".pdf";
				archivoExcel = "consolidado_general_" + nombrerep1 + ".xls";
				archivozip = "consolidado_general_" + nombrerep1 + ".zip";
			} else {
				reportFile = new File(path
						+ rb.getString("jasper_consolidado_pregunta"));
				if (nivel.equals("4") || nivel.equals("6")) {
					nombrerep1 = sede
							+ jornada
							+ "_Fecha_"
							+ f2.toString().replace(' ', '_').replace(':', '-')
									.replace('.', '-');
				} else if (nivel.equals("1") || nivel.equals("2")) {
					nombrerep1 = localidad
							+ colegio
							+ sede
							+ jornada
							+ "_Fecha_"
							+ f2.toString().replace(' ', '_').replace(':', '-')
									.replace('.', '-');
				}
				archivo = "consolidado_general_pregunta_"
						+ filtro.getNumeroPregunta() + "_" + nombrerep1
						+ ".pdf";
				archivoExcel = "consolidado_general_pregunta"
						+ filtro.getNumeroPregunta() + "_" + nombrerep1
						+ ".xls";
				archivozip = "consolidado_general_pregunta"
						+ filtro.getNumeroPregunta() + "_" + nombrerep1
						+ ".zip";

			}

		} else if (modulo == ParamsVO.moduloMotivosPreferencias) {
			reportFile = new File(path
					+ rb.getString("jasper_motivos_preferencias"));
			if (nivel.equals("4") || nivel.equals("6")) {
				nombrerep1 = sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
			} else if (nivel.equals("1") || nivel.equals("2")) {
				nombrerep1 = localidad
						+ colegio
						+ sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
			}
			archivo = "motivos_preferencias_" + nombrerep1 + ".pdf";
			archivoExcel = "motivos_preferencias_" + nombrerep1 + ".xls";
			archivozip = "motivos_preferencias_" + nombrerep1 + ".zip";

		}
		if (!isWorking(String.valueOf(usuario), String.valueOf(modulo))) {
			ponerReporte(String.valueOf(modulo), String.valueOf(usuario),
					rb.getString("reporte.PathReporte") + archivozip + "",
					"zip", "" + archivozip, "0", "ReporteInsertarEstado");
			reporte1 = getArrayBytes(new Long(filtro.getPlaInstitucion()),
					String.valueOf(usuario), archivozip, reportFile,
					parameters, String.valueOf(modulo), archivosalidaExcel,
					contextoTotal, archivoExcel);
			updateReporteEstado(String.valueOf(modulo),
					String.valueOf(usuario),
					rb.getString("reporte.PathReporte") + archivozip + "",
					"zip", "" + archivozip, "ReporteActualizarListo");
			ponerArchivo(reporte1, archivo, contextoTotal);
			archivosalida = Ruta.get(contextoTotal,
					rb.getString("reportes.PathReporte"));

			list.add(archivosalida + archivo);// pdf
			list.add(archivosalida + archivoExcel);// xls
			zise = 100000;
			if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
				updateReporteFecha("update_reporte_general",
						String.valueOf(modulo), archivozip,
						String.valueOf(usuario), "1");
				return true;
			} else {
				ponerReporteMensaje("4", String.valueOf(modulo),
						String.valueOf(usuario),
						rb.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip,
						"ReporteActualizarHorarioFallo",
						"Ocurrin problema al hacer zip");
				updateReporteFecha("update_reporte_general",
						String.valueOf(modulo), archivozip,
						String.valueOf(usuario), "2");
				setMensaje("Error al crear zip");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} else {
			setMensaje("Usted ya mandn generar un reporte de encuesta \nPor favor espere que termine, para solicitar uno nuevo");
			request.setAttribute("mensaje", mensaje);
			return false;
		}

	}

	private String getDaneInstitucion(long plaInstitucion, long localidad)
			throws Exception {
		String daneInst = "";
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getDaneInst"));
			posicion = 1;
			pst.setLong(posicion++, plaInstitucion);
			pst.setLong(posicion++, localidad);
			rs = pst.executeQuery();
			while (rs.next()) {
				daneInst = rs.getString(1);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return daneInst;
	}

	// FUNCION PARA VERIFICAR Q EL USUARIO NO ESTE GENRANDO MAS REPORTES
	public boolean isWorking(String idUsuario, String modulo) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("isWorking"));
			posicion = 1;
			pst.setString(posicion++, idUsuario);
			pst.setString(posicion++, modulo);
			rs = pst.executeQuery();
			if (rs.next())
				return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return false;
	}

	// GETARAYBYTE
	public byte[] getArrayBytes(Long idInstitucion, String idUsuario,
			String nombreReporteZip, File reportFile, Map parameterscopy,
			String modulo, String archivosalidaExcel, String contextoTotal,
			String archivo) {
		byte[] bytes = null;
		Connection con = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				// System.out.println("***Se mandn ejecutar el jasper****");
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
						parameterscopy, con);
				archivosalidaExcel = Ruta.get(contextoTotal,
						rb.getString("reportes.PathReporte"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportFile.getPath(), parameterscopy, con);
				String xlsFileName = archivo;
				String xlsFilesSource = archivosalidaExcel;
				File f = new File(xlsFilesSource);
				if (!f.exists())
					FileUtils.forceMkdir(f);
				// System.out.println("SALIDA DEL XLS: "+xlsFilesSource +
				// xlsFileName);
				// Creacion del XLS SIRVE BIEN SIN IMAGENES
				/*
				 * JRXlsExporter exporter = new JRXlsExporter();
				 * exporter.setParameter(JRExporterParameter.JASPER_PRINT,
				 * jasperPrint);
				 * exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME
				 * ,xlsFilesSource + xlsFileName);
				 * exporter.setParameter(JRXlsExporterParameter
				 * .IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				 * exporter.exportReport();
				 */

				// USANDO API EXCEL
				JExcelApiExporter xslExporter = new JExcelApiExporter();
				JRXlsExporter exporter = new JRXlsExporter();
				xslExporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				xslExporter.setParameter(
						JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				xslExporter
						.setParameter(
								JExcelApiExporterParameter.OUTPUT_FILE_NAME.OUTPUT_FILE_NAME,
								xlsFilesSource + xlsFileName);
				xslExporter.exportReport();
			}
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, idUsuario,
					rb.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(idUsuario,
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			// System.out.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "+e.toString());
			ponerReporteMensaje("2", modulo, idUsuario,
					rb.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(String.valueOf(idUsuario),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, idUsuario,
					rb.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(idUsuario,
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return bytes;
	}

	// ACTUALIZAR ESTADO DE REPORTE
	public void updateReporte(String modulo, String nombrereporte, String user,
			String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombrereporte));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
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

	// PONER MENSAJE DEL REPORTE
	public boolean ponerReporteMensaje(String estado, String modulo, String us,
			String rec, String tipo, String nombre, String prepared,
			String mensaje) {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(prepared));
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
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ponerReporteMensaje: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public void updateReporteEstado(String modulo, String us, String rec,
			String tipo, String nombre, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString(prepared));
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

	// PONER ARCHIVO EN DISCO
	public void ponerArchivo(byte[] bit, String archivostatic, String context) {

		try {
			String archivosalida = Ruta.get(context,
					rb.getString("reportes.PathReporte"));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// PONER FECHA RERPOTE
	public void updateReporteFecha(String preparedstatement, String modulo,
			String nombreboletin, String user, String estado) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(preparedstatement));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombreboletin));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
			con.commit();
			// System.out.println("nnSe actualizn la fecha en la tabla Reporte!!!!");

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

	public void setMensaje(String s) {
		mensaje = "  - " + s + "\n";
	}

	public List getConsolidadoEnc(Encuesta2VO filtro) throws Exception {
		List l = new ArrayList();
		long cantidadEstudiantes;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sqlConsolidado = "";
		ConsolidadoVO consolidado = null;
		int i = 0;

		try {
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// armar sql segun parametros
			sqlConsolidado = rb.getString("getTotalEstudiantes.select");
			if (filtro.getPlaLocalidad() != -99) {
				sqlConsolidado = sqlConsolidado
						+ rb.getString("getConsolidadoEncuesta.localidad");
				if (filtro.getPlaInstitucion() != -99) {
					sqlConsolidado = sqlConsolidado
							+ rb.getString("getConsolidadoEncuesta.colegio");
					if (filtro.getPlaSede() != -99) {
						sqlConsolidado = sqlConsolidado
								+ rb.getString("getConsolidadoEncuesta.sede");
						if (filtro.getPlaJornada() != -99) {
							sqlConsolidado = sqlConsolidado
									+ rb.getString("getConsolidadoEncuesta.jornada");
							if (filtro.getPlaGrado() != -99) {
								sqlConsolidado = sqlConsolidado
										+ rb.getString("getConsolidadoEncuesta.grado");
								if (filtro.getPlaGrupo() != -99) {
									sqlConsolidado = sqlConsolidado
											+ rb.getString("getConsolidadoEncuesta.grupo");
								}
							}
						}
					}
				}
			}
			sqlConsolidado = sqlConsolidado
					+ rb.getString("getConsolidadoEncuesta.metodologia");
			sqlConsolidado = sqlConsolidado
					+ rb.getString("getConsolidadoEncuesta.grados");
			st = cn.prepareStatement(sqlConsolidado);
			i = 1;
			if (filtro.getPlaLocalidad() != -99) {
				st.setLong(i++, filtro.getPlaLocalidad());
				st.setLong(i++, filtro.getPlaLocalidad());
				if (filtro.getPlaInstitucion() != -99) {
					st.setLong(i++, filtro.getPlaInstitucion());
					if (filtro.getPlaSede() != -99) {
						st.setInt(i++, filtro.getPlaSede());
						if (filtro.getPlaJornada() != -99) {
							st.setInt(i++, filtro.getPlaJornada());
							if (filtro.getPlaGrado() != -99) {
								st.setInt(i++, filtro.getPlaGrado());
								if (filtro.getPlaGrupo() != -99) {
									st.setInt(i++, filtro.getPlaGrupo());
								}
							}
						}
					}
				}
			}
			st.setInt(i++, filtro.getPlaMetodologia());
			// fin armado sql
			rs = st.executeQuery();
			if (rs.next()) {
				cantidadEstudiantes = rs.getLong("CANTIDAD");
			} else {
				throw new Exception("NO SE PUDO CALCULAR TOTAL ESTUDIANTES");
			}
			rs.close();
			st.close();
			// armar sql consolidado segun parametros
			sqlConsolidado = "";
			sqlConsolidado = rb.getString("getConsolidadoEncuesta.select");
			if (filtro.getPlaLocalidad() != -99) {
				sqlConsolidado = sqlConsolidado
						+ rb.getString("getConsolidadoEncuesta.localidad");
				if (filtro.getPlaInstitucion() != -99) {
					sqlConsolidado = sqlConsolidado
							+ rb.getString("getConsolidadoEncuesta.colegio");
					if (filtro.getPlaSede() != -99) {
						sqlConsolidado = sqlConsolidado
								+ rb.getString("getConsolidadoEncuesta.sede");
						if (filtro.getPlaJornada() != -99) {
							sqlConsolidado = sqlConsolidado
									+ rb.getString("getConsolidadoEncuesta.jornada");
							if (filtro.getPlaGrado() != -99) {
								sqlConsolidado = sqlConsolidado
										+ rb.getString("getConsolidadoEncuesta.grado");
								if (filtro.getPlaGrupo() != -99) {
									sqlConsolidado = sqlConsolidado
											+ rb.getString("getConsolidadoEncuesta.grupo");
								}
							}
						}
					}
				}
			}
			sqlConsolidado = sqlConsolidado
					+ rb.getString("getConsolidadoEncuesta.metodologia");
			sqlConsolidado = sqlConsolidado
					+ rb.getString("getConsolidadoEncuesta.grados");
			sqlConsolidado = sqlConsolidado
					+ rb.getString("getConsolidadoEncuesta.final");
			// System.out.println("CONSULTA ");
			st = cn.prepareStatement(sqlConsolidado);
			i = 1;
			st.setLong(i++, cantidadEstudiantes);
			if (filtro.getPlaLocalidad() != -99) {
				st.setLong(i++, filtro.getPlaLocalidad());
				st.setLong(i++, filtro.getPlaLocalidad());
				if (filtro.getPlaInstitucion() != -99) {
					st.setLong(i++, filtro.getPlaInstitucion());
					if (filtro.getPlaSede() != -99) {
						st.setInt(i++, filtro.getPlaSede());
						if (filtro.getPlaJornada() != -99) {
							st.setInt(i++, filtro.getPlaJornada());
							if (filtro.getPlaGrado() != -99) {
								st.setInt(i++, filtro.getPlaGrado());
								if (filtro.getPlaGrupo() != -99) {
									st.setInt(i++, filtro.getPlaGrupo());
								}
							}
						}
					}
				}
			}
			st.setInt(i++, filtro.getPlaMetodologia());
			// fin armado sql consolidado
			rs = st.executeQuery();

			int index = 1;
			while (rs.next()) {
				i = 1;
				consolidado = new ConsolidadoVO();
				consolidado.setConNumero(rs.getDouble(i++));
				consolidado.setConEstado(rs.getString(i++));
				consolidado.setConPorcentaje(rs.getDouble(i++));
				consolidado.setEstConsecutivo(index++);
				l.add(consolidado);
			}
			filtro.setTotalEstudiantes((int) cantidadEstudiantes);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;

	}

	public void ponerReporte(String modulo, String us, String rec, String tipo,
			String nombre, String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(prepared));
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
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ponerReporte: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	// ENCUESTA
	public MostrarEncuestaVO getEncuesta(long codigo) throws Exception {
		MostrarEncuestaVO encuestaVO = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getEncuesta2"));
			posicion = 1;
			pst.setLong(posicion++, codigo);
			rs = pst.executeQuery();

			if (rs.next()) {

				posicion = 1;
				encuestaVO = new MostrarEncuestaVO();
				encuestaVO.setCodigo(rs.getLong(posicion++));
				encuestaVO.setPregunta2a(rs.getString(posicion++));
				encuestaVO.setPregunta2b(rs.getString(posicion++));
				encuestaVO.setPregunta3(rs.getInt(posicion++));
				encuestaVO.setPregunta4(rs.getInt(posicion++));
				encuestaVO.setPregunta5a(rs.getInt(posicion++));
				encuestaVO.setPregunta5b(rs.getInt(posicion++));
				encuestaVO.setPregunta5c(rs.getString(posicion++));
				encuestaVO.setPregunta6a(rs.getLong(posicion++));
				encuestaVO.setPregunta6b(rs.getLong(posicion++));
				encuestaVO.setPregunta6(encuestaVO.getPregunta6a() + "|"
						+ encuestaVO.getPregunta6b());
				encuestaVO.setPregunta7a(rs.getLong(posicion++));
				encuestaVO.setPregunta7b(rs.getLong(posicion++));
				encuestaVO.setPregunta7(encuestaVO.getPregunta7a() + "|"
						+ encuestaVO.getPregunta7b());
			}
			rs.close();
			pst.close();
			if (encuestaVO != null) {
				pst = cn.prepareStatement(rb.getString("getEncuesta1"));
				posicion = 1;
				pst.setLong(posicion++, codigo);
				rs = pst.executeQuery();
				List l = new ArrayList();
				while (rs.next()) {
					l.add(rs.getString(1) + "|" + rs.getString(2));

				}
				String a[] = new String[l.size()];
				for (int i = 0; i < l.size(); i++) {
					a[i] = (String) l.get(i);
				}
				encuestaVO.setPregunta1(a);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return encuestaVO;
	}

	public List getListaAsignatura() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("MATEMnTICAS");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("CIENCIAS NATURALES");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("CIENCIAS SOCIALES");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("ESPAnOL");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("INGLES");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("TECNOLOGnA E INFORMnTICA");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("nTICA");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("EDUCACInN FnSICA");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("EDUCACInN RELIGIOSA");
			lista.add(itemVO);

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	// LISTA IMPORTANCIA ASIGNATURAS
	public List getListaImportancia() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion);
			itemVO.setNombre(String.valueOf(posicion++));
			lista.add(itemVO);

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	// LISTA DE CICLOS
	public List getListaCiclo() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Tncnico Laboral");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Tncnico Profesional");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Profesional");
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaInteres() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Continuar Estudios en la Universidad");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Hacer un Curso en el SENA u Otra Organizacinn");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Conseguir Trabajo");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Estudiar y Trabajar");
			lista.add(itemVO);

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaCarrera() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Medicina");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Derecho");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Administracinn de Empresas");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Ingenierna");
			itemVO.setPadre(1);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Odontologna");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Psicologna");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Contadurna");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Comunicacinn Social");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Arquitectura");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Economna");
			itemVO.setPadre(0);
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Otra. nCunl?");
			itemVO.setPadre(2);
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaIngenieria() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Administrativa Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Agrncola, Forestal Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Agroindustrial, Alimentos Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Agronnmica, Pecuaria Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Ambiental, Sanitaria Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Biomndica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Civil Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("De Minas, Metalurgia Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("De Sistemas, Telemntica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Elnctrica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Electrnnica, Telecomunicaciones Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Industrial Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Mecnnica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(posicion++);
			itemVO.setNombre("Qunmica Y Afines");
			lista.add(itemVO);

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaEspecialidad() throws Exception {
		List lista = new ArrayList();
		UniversidadVO universidadVO = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getUniversidades"));
			posicion = 1;
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				universidadVO = new UniversidadVO();
				universidadVO.setCodigo(rs.getLong(posicion++));
				universidadVO.setNombre(rs.getString(posicion++));
				universidadVO.setListaColegio(getAllColegio(cn,
						universidadVO.getCodigo()));
				lista.add(universidadVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return lista;
	}

	// GETALLCOLEGIO
	public List getAllColegio(Connection cn, long univ) throws Exception {
		List lista = new ArrayList();
		ColegioVO colegioVO = null;
		int posicion = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = cn.prepareStatement(rb.getString("getColegios"));
			posicion = 1;
			pst.setLong(posicion++, univ);
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				colegioVO = new ColegioVO();
				colegioVO.setCodigo(rs.getLong(posicion++));
				colegioVO.setNombre(rs.getString(posicion++));
				colegioVO.setLocalidad(rs.getString(posicion++));
				colegioVO.setListaEspecialidad(getAllEspecialidad(cn, univ,
						colegioVO.getCodigo()));
				lista.add(colegioVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
		}
		return lista;
	}

	public List getAllEspecialidad(Connection cn, long univ, long col)
			throws Exception {
		List lista = new ArrayList();
		EspecialidadVO especialidadVO = null;
		int posicion = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = cn.prepareStatement(rb.getString("getEspecialidades"));
			posicion = 1;
			pst.setLong(posicion++, col);
			pst.setLong(posicion++, univ);
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				especialidadVO = new EspecialidadVO();
				especialidadVO.setCodigo(rs.getLong(posicion++));
				especialidadVO.setNombre(rs.getString(posicion++));
				especialidadVO.setCiclo(rs.getString(posicion++));
				lista.add(especialidadVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
		}
		return lista;
	}

	// FUNCION GET LISTA LOCALIDADES

	public List getLocalidades() throws Exception {
		List lista = new ArrayList();
		Connection cn = null;
		LocalidadVO localidadVO = null;
		int posicion = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getLocalidades"));
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				localidadVO = new LocalidadVO();
				localidadVO.setCodigo(rs.getLong(posicion++));
				localidadVO.setLocalidad(rs.getString(posicion++));
				lista.add(localidadVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return lista;
	}

	// FUNCION GET LISTA COLEGIOS SEGUN LOCALIDAD

	public List getColegiosFiltro(long loc) throws Exception {
		List lista = new ArrayList();
		Connection cn = null;
		ColegioVO colegioVO = null;
		int posicion = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getColegiosFiltro"));
			posicion = 1;
			pst.setLong(posicion++, loc);
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				colegioVO = new ColegioVO();
				colegioVO.setCodigo(rs.getLong(posicion++));
				colegioVO.setNombre(rs.getString(posicion++));
				lista.add(colegioVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return lista;
	}

	public boolean getExistenEncuestados(long usuarioID) throws Exception {
		boolean continuar = false;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;

		try {
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getExistenciaEncuestados"));
			i = 1;
			st.setLong(i++, usuarioID);
			rs = st.executeQuery();
			if (rs.next()) {
				continuar = true;
			} else {
				continuar = false;
			}
			rs.close();
			st.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return continuar;
	}

	// FUNCION PARA ELIMINAR REGISTROS DE LA TABLA TEMPORAL DE ENCUESTADOS
	public void deleteEncuestados(long usuarioID) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("deleteEncuestados"));
			i = 1;
			st.setLong(i++, usuarioID);
			st.executeUpdate();
			st.close();
			// cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	public void insertarEncuestadosTemporal(Encuesta3VO filtro3, long usuarioID)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("insertaEncuestados"));
			i = 1;
			st.setLong(i++, usuarioID);
			st.setLong(i++, filtro3.getPlaLocalidad());
			st.setLong(i++, filtro3.getPlaLocalidad());
			st.setLong(i++, filtro3.getPlaInstitucion());
			st.setLong(i++, filtro3.getPlaInstitucion());
			st.setInt(i++, filtro3.getPlaSede());
			st.setInt(i++, filtro3.getPlaSede());
			st.setInt(i++, filtro3.getPlaJornada());
			st.setInt(i++, filtro3.getPlaJornada());
			st.setInt(i++, filtro3.getPlaMetodologia());
			st.setInt(i++, filtro3.getPlaMetodologia());
			st.setInt(i++, filtro3.getPlaGrado());
			st.setInt(i++, filtro3.getPlaGrado());
			st.setInt(i++, filtro3.getPlaGrupo());
			st.setInt(i++, filtro3.getPlaGrupo());
			int n = st.executeUpdate();
			// System.out.println("ingreso"+n);
			st.close();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	// FUNCION PARA INSERTAR ESTUDIANTES FILTRO DE MOTIVO DE PREFERENCIAS
	public void insertarEncuestadosTemporalPref(Encuesta4VO filtro4,
			long usuarioID) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;

		try {
			// System.out.println("ENTOR A INSERTAR ENCUESTADOS EN MOTIVOS");
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("insertaEncuestadosPreferencias"));
			i = 1;
			st.setLong(i++, usuarioID);
			st.setLong(i++, filtro4.getPlaLocalidad());
			st.setLong(i++, filtro4.getPlaLocalidad());
			st.setLong(i++, filtro4.getPlaInstitucion());
			st.setLong(i++, filtro4.getPlaInstitucion());
			st.setInt(i++, filtro4.getPlaSede());
			st.setInt(i++, filtro4.getPlaSede());
			st.setInt(i++, filtro4.getPlaJornada());
			st.setInt(i++, filtro4.getPlaJornada());
			st.setInt(i++, filtro4.getPlaMetodologia());
			st.setInt(i++, filtro4.getPlaMetodologia());
			st.setInt(i++, filtro4.getPlaGrado());
			st.setInt(i++, filtro4.getPlaGrado());
			st.executeUpdate();
			st.close();
			// cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo e insertando datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo e insertando  datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

}
