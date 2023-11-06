package siges.util.dao;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;

import siges.dao.Cursor;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.institucion.correoLider.beans.ParamsMail;
import siges.usuario.dao.UsuarioDAO;
import siges.util.MailDTO;
import siges.util.Mailer;

public class utilDAO {
	private ResourceBundle rb;
	private static final int PLANTILLAS_BATCH = 0;
	private static final int PLANTILLAS_EVALUACION = 1;
	private static final int BOLETINES_LIBROS_RESUMENES = 2;
	private static final int CERTIFICADOS_CONSTANCIAS_CARNETS = 3;
	private static final int RESULTADOS_IMPORTACION = 4;
	private static final int REPORTES_PLAN = 5;
	private static final int INFO_ESTUDIANTES = 6;
	private static final int REPORTES_ESTADISTICOS = 7;

	public static final int TIPO_BOLETIN = 1;
	public static final int TIPO_CONSTANCIA = 2;
	public static final int TIPO_PAZ_SALVO = 3;
	public static final int TIPO_CERTIFICADO = 4;
	public static final int TIPO_RESUMEN = 5;
	public static final int TIPO_LIBRO_NOTAS = 6;

	private String mensaje;
	private String path;

	public utilDAO(String p) {
		rb = ResourceBundle.getBundle("common");
		mensaje = "";
		path = p;
	}

	public utilDAO() {
		rb = ResourceBundle.getBundle("common");
		mensaje = "";
	}

	public long[] getParametrosRecolector(boolean running) {
		if (!running)
			return null;
		long[] params = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("recolector.consultaTiempos"));
			rs = pst.executeQuery();
			if (rs.next()) {
				params = new long[4];
				params[0] = rs.getLong(1);
				params[1] = rs.getLong(2);
				params[2] = rs.getLong(3);
				params[3] = rs.getLong(4);
			}
		} catch (Exception e) {
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return params;
	}

	public long[][] getParametrosTipo() {
		long[][] params = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String parametros = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("recolector.consultaTipos"));
			rs = pst.executeQuery();
			if (rs.next()) {
				parametros = rs.getString(1);
			}
			rs.close();
			pst.close();
			if (parametros != null) {
				String[] tipos = parametros.split(",");
				String[] valores = null;
				if (tipos != null) {
					params = new long[tipos.length][2];
					for (int i = 0; i < tipos.length; i++) {
						valores = tipos[i].split(":");
						if (valores != null) {
							params[i][0] = Long.parseLong(valores[0]);
							params[i][1] = Long.parseLong(valores[1]);
						}
					}
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return params;
	}

	public long[] getParametrosImpotar(boolean running) {
		if (!running)
			return null;
		long[] params = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("importar.consultaTiempos"));
			rs = pst.executeQuery();
			if (rs.next()) {
				params = new long[4];
				params[0] = rs.getLong(1);
				params[1] = rs.getLong(2);
				params[2] = rs.getLong(3);
				params[3] = rs.getLong(4);
			}
		} catch (Exception e) {
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return params;
	}

	public long[] getParametrosPlantilla(boolean running) {
		if (!running)
			return null;
		long[] params = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("plantilla.consultaTiempos"));
			rs = pst.executeQuery();
			if (rs.next()) {
				params = new long[5];
				params[0] = rs.getLong(1);
				params[1] = rs.getLong(2);
				params[2] = rs.getLong(3);
				params[3] = rs.getLong(4);
				params[4] = rs.getLong(5);
			}
		} catch (Exception e) {
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return params;
	}

	public boolean borrarTipo(long[] params) {
		boolean band = true;
		int tipo = (int) params[0];
		int valor = (int) params[1];
		if (valor == -1)
			return true;
		int nConsultas = 0;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			switch (tipo) {
			case PLANTILLAS_BATCH:
				return borrarArchivosBatch(valor);
			default:
				cn = DataSourceManager.getConnection(1);
				nConsultas = Integer.parseInt(rb.getString("tipo" + tipo));
				pst = cn.prepareStatement(rb.getString("consulta" + tipo));
				pst.setInt(1, valor);
				rs = pst.executeQuery();
				String[][] c = getFiltroMatriz(getCollection(rs));
				if (c != null && c.length > 0) {
					String archivo = null;
					int n = 0;
					int m = 0;
					for (int j = 0; j < c.length; j++) {
						archivo = Ruta.getArchivo(path, c[j][0]);
						File f = new File(archivo);
						if (f != null && f.exists()) {
							try {
								FileUtils.forceDelete(f);
							} catch (IOException e) {
								System.out.println("RECOLECTOR: Error borrando " + c[j][0] + ": " + e);
							}
							;
							m++;
						}
					}
					System.out.println("RECOLECTOR: Archivos borrados =" + m);
					for (int i = 0; i < nConsultas; i++) {
						pst = cn.prepareStatement(rb.getString("delete" + tipo + "" + i));
						pst.setInt(1, valor);
						n = pst.executeUpdate();
						pst.close();
						System.out.println("RECOLECTOR: Registros borrados: en " + i + "=" + n);
					}
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "RECOLECTOR: Error " + e;
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return band;
	}

	public boolean borrarArchivosBatch(int valor) {
		ResourceBundle rbun = ResourceBundle.getBundle("batch");
		String baseBatch = rbun.getString("path.raiz");
		String pathBase = null;
		Ruta.get(path, baseBatch);
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("getSolicitudes"));
			pst.setInt(1, valor);
			rs = pst.executeQuery();
			String[] solicitudes = getFiltroArray(getCollection(rs));
			rs.close();
			pst.close();
			File f = null;
			if (solicitudes != null && solicitudes.length > 0) {
				for (int j = 0; j < solicitudes.length; j++) {
					f = new File(Ruta.get(path, baseBatch + "." + solicitudes[j]));
					if (f != null && f.exists() && f.isDirectory()) {
						try {
							FileUtils.cleanDirectory(f);
						} catch (IOException e) {
						}
						;
						System.out.println("RECOLECTOR: Institucion borrada=" + solicitudes[j]);
						pst = cn.prepareStatement(rb.getString("updateSolicitudes"));
						pst.setString(1, solicitudes[j]);
						pst.executeUpdate();
						pst.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensaje = "RECOLECTOR: Error " + e;
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/*
	 * public boolean borrarArchivosBatch(int valor){ ResourceBundle
	 * rbun=ResourceBundle.getBundle("batch"); String
	 * baseBatch=rbun.getString("path.raiz"); String
	 * pathBase=null;Ruta.get(path,baseBatch); Connection cn=null;
	 * PreparedStatement pst=null; ResultSet rs=null; try{
	 * cn=DataSourceManager.getConnection(1);
	 * pst=cn.prepareStatement(rb.getString("getInstituciones"));
	 * rs=pst.executeQuery(); String []inst=getFiltroArray(getCollection(rs));
	 * rs.close(); pst.close();
	 * pst=cn.prepareStatement(rb.getString("getSedesJornadas"));
	 * rs=pst.executeQuery(); String [][]jor=getFiltroMatriz(getCollection(rs));
	 * rs.close(); pst.close(); if(inst!=null){ String carpeta=null; File
	 * f=null; String []archivos=null; for(int j=0;j<inst.length;j++){
	 * if(jor!=null){ for(int k=0;k<jor.length;k++){
	 * if(jor[k][0].equals(inst[j])){
	 * pathBase=Ruta.get(path,baseBatch+"."+jor[k]
	 * [0]+"."+jor[k][1]+"."+jor[k][2]); //System.out.println(pathBase); f=new
	 * File(pathBase); if(f!=null && f.exists() && f.isDirectory()){
	 * archivos=f.list(); if(archivos!=null && archivos.length>0){ f=new
	 * File(pathBase+archivos[0]); if(f!=null && f.exists() &&
	 * !f.isDirectory()){ Date d=new Date(f.lastModified());
	 * pst=cn.prepareStatement(rb.getString("getValidacionFecha"));
	 * pst.setDate(1,d); pst.setInt(2,valor); rs=pst.executeQuery();
	 * if(rs.next()){ int nn=rs.getInt(1); if(nn==0){ f=new
	 * File(Ruta.get(path,baseBatch+"."+jor[k][0])); if(f!=null && f.exists() &&
	 * f.isDirectory()){ try{FileUtils.cleanDirectory(f);}catch(IOException
	 * e){}; System.out.println("RECOLECTOR: Institucion borrada="+jor[k][0]); }
	 * } } rs.close();pst.close(); break; } } } } } } } } }catch(Exception
	 * e){e.printStackTrace(); mensaje="RECOLECTOR: Error "+e; return false;}
	 * finally{ try{ OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(pst);
	 * OperacionesGenerales.closeConnection(cn); }catch(Exception e){} } return
	 * true; }
	 */

	public String[] getFiltroArray(Collection a) throws InternalErrorException {
		if (a != null && !a.isEmpty()) {
			String[] m = new String[a.size()];
			String z;
			int i = 0;
			Iterator iterator = a.iterator();
			Object[] o;
			while (iterator.hasNext()) {
				z = "";
				o = (Object[]) iterator.next();
				for (int j = 0; j < o.length; j++)
					z += (o[j] != null ? o[j] : "") + "|";
				m[i++] = z.substring(0, z.lastIndexOf("|"));
			}
			return m;
		}
		return null;
	}

	public String[][] getFiltroMatriz(Collection a) throws InternalErrorException {
		Object[] o;
		if (a != null && !a.isEmpty()) {
			int i = 0, j = -1;
			Iterator iterator = a.iterator();
			o = ((Object[]) iterator.next());
			String[][] m = new String[a.size()][o.length];
			iterator = a.iterator();
			while (iterator.hasNext()) {
				j++;
				o = ((Object[]) iterator.next());
				for (i = 0; i < o.length; i++) {
					m[j][i] = (String) o[i];
				}
			}
			return m;
		}
		return null;
	}

	public Collection getCollection(ResultSet rs) throws SQLException {
		Collection list = null;
		int m = rs.getMetaData().getColumnCount();
		list = new ArrayList();
		Object[] o;
		int i = 0;
		int ff = 0;
		while (rs.next()) {
			ff++;
			o = new Object[m];
			for (i = 1; i <= m; i++) {
				o[i - 1] = rs.getString(i);

			}
			list.add(o);
		}
		return list;
	}

	/**
	 * @return Returns the mensaje.
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param running
	 * @return
	 */
	public String[] getParametrosHiloIntegracion(boolean running) throws Exception {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		// System.out.println(sdf.format(new java.util.Date()) +
		// " getParametrosHiloIntegracion ");
		if (!running)
			return null;
		String[] params = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("integracionHilo.consultaTiempos"));
			rs = pst.executeQuery();
			if (rs.next()) {
				params = new String[4];
				params[0] = "" + rs.getLong(1);
				params[1] = "" + rs.getLong(2);
				params[2] = "" + rs.getLong(3);
				params[3] = "" + rs.getLong(4);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return params;
	}

	/**
	 * @param vigencia
	 * @return
	 * @throws Exception
	 */
	public int callProcedimientoHiloIntegracion() throws Exception {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		System.out.println(sdf.format(new java.util.Date()) + " callProcedimientoHiloIntegracion ");

		Connection cn = null;
		CallableStatement cst = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int vig = 0;
		int flag = 0;
		int posicion = 1;
		long codJerarquia = 0;
		long codInstitucion = 0;
		try {

			cn = DataSourceManager.getConnection(1);

			// 1.Borrar
			pst = cn.prepareStatement(rb.getString("dropSequences"));
			pst.executeUpdate();
			pst.close();

			// 2. Obtener codigo
			pst = cn.prepareStatement(rb.getString("obtenerCodigoJerarquia"));
			posicion = 1;
			rs = pst.executeQuery();

			if (rs.next()) {
				codJerarquia = rs.getLong(posicion++);
			}
			rs.close();
			pst.close();

			System.out.println("Crear SEQUENCE con valor " + codJerarquia);
			pst = cn.prepareStatement("CREATE SEQUENCE CODIGO_JERARQUIA START WITH " + codJerarquia
					+ " MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE NOCACHE NOORDER");
			posicion = 1;
			pst.executeUpdate();
			pst.close();
			System.out.println("se creo la SEQUENCE");

			// CREAR SECUECNIA DE INSTITUCION

			// 1.Borrar
			pst = cn.prepareStatement(rb.getString("borrarSecInst"));
			pst.executeUpdate();
			pst.close();

			// 2. Obtener codigo
			pst = cn.prepareStatement(rb.getString("selectMaxInstitucion"));
			posicion = 1;
			rs = pst.executeQuery();

			if (rs.next()) {
				codInstitucion = rs.getLong(posicion++);
			}
			rs.close();
			pst.close();

			// 3.Crear
			System.out.println("Nuevo codigo para sequences Institucion :" + codInstitucion);
			pst = cn.prepareStatement("CREATE SEQUENCE CODIGO_INSTITUCION START WITH " + codInstitucion
					+ " MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE NOCACHE NOORDER");

			posicion = 1;
			pst.executeUpdate();
			pst.close();

			pst = cn.prepareStatement(rb.getString("integracionHilo.getVigenciaActual"));
			rs = pst.executeQuery();
			if (rs.next()) {
				vig = rs.getInt(1);
			}
			// rs.close();
			// pst.close();

			String fechaIncio = sdf.format(new java.util.Date());
			setMensaje(
					"PROCESANDO: \n Actualizar Matriculas con vigencia " + vig + "  [fecha inicio " + fechaIncio + "]");
			System.out.println("vigencia actual " + vig);

			cst = cn.prepareCall(rb.getString("integracionHilo.procedimiento"));
			cst.setInt(1, vig);
			cst.executeUpdate();
			setMensaje("PROCESO FINALIZADO: \n [fecha inicio:" + fechaIncio + "   -  fecha fin:"
					+ sdf.format(new java.util.Date()) + "]");

			System.out.println(sdf.format(new java.util.Date()) + " Sin problema en callProcedimientoHiloIntegracion");

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return flag;
	}

	/**
	 * @function: Guarda el mensaje enviado como para metro en la base de datos.
	 * @param msj
	 * @throws Exception
	 */
	public void setMensaje(String msj) throws Exception {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		// System.out.println(sdf.format(new java.util.Date())
		// + " getParametrosHiloIntegracion ");

		int posicion = 1;
		PreparedStatement pst = null;
		Connection cn = null;
		try {

			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("integracionHilo.mensaje"));
			pst.setString(posicion++, msj);
			pst.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public ParamsMail getParamsMail() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ParamsMail mail = null;

		try {
			cn = DataSourceManager.getConnection(1);
			st = cn.prepareStatement(rb.getString("getParamsCorreo"));
			rs = st.executeQuery();
			while (rs.next()) {
				if (mail == null)
					mail = new ParamsMail();
				if (rs.getString(1).equals("FROM")) {
					mail.setFrom(rs.getString(2));
				}
				if (rs.getString(1).equals("MAILHOST")) {
					mail.setHost(rs.getString(2));
				}
				if (rs.getString(1).equals("PASSWORD")) {
					mail.setPassword(rs.getString(2));
				}
				if (rs.getString(1).equals("PORT")) {
					mail.setPort(rs.getString(2));
				}

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return mail;
	}

	public void enviarNotificaciones(Integer tipoReporte, String usuarioId, String institucion, long instCod, long sede,
			long jornada, int tipoNotificacion, String detalleError) throws MessagingException {
		Mailer mailer = new Mailer();
		Cursor cursor = new Cursor();

		String[] emails = { getMailNotificationReportes(usuarioId) };

		UsuarioDAO objDatosUsuario = new UsuarioDAO(cursor);
		String strCuerpo = objDatosUsuario.cuerpoCorreoGeneracionBoletin()
				.replace("{nombre}", getPersonaFullName(usuarioId)).replace("{institucion}", institucion);
		MailDTO mailDto = new MailDTO();
		mailDto.setEmails(emails);
		mailDto.setSubject("Generación de Reportes");

		switch (tipoReporte) {
		case 1: // TIPO BOLETIN
			switch (tipoNotificacion) {
			case 1:// generacion reporte
				mailDto.setContent(strCuerpo.replace("{estado}", detalleError).replace("{tipoReporte}", "boletin"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			case 2:// no generado
				mailDto.setContent(
						strCuerpo.replace("{estado}", "no pudo ser generado, ").replace("{tipoReporte}", "boletin"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			}
			break;
		case 2:// TIPO CONSTANCIA
			switch (tipoNotificacion) {
			case 1:// generacion reporte
				mailDto.setContent(strCuerpo.replace("{estado}", detalleError).replace("{tipoReporte}", "constancia"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			case 2:// no generado
				mailDto.setContent(
						strCuerpo.replace("{estado}", "no pudo ser generado, ").replace("{tipoReporte}", "constancia"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			}
		case 4:// TIPO_CERTIFICADO
			switch (tipoNotificacion) {
			case 1:// generacion reporte
				mailDto.setContent(strCuerpo.replace("{estado}", detalleError).replace("{tipoReporte}", "certificado"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			case 2:// no generado
				mailDto.setContent(strCuerpo.replace("{estado}", "no pudo ser generado, ").replace("{tipoReporte}",
						"certificado"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			}
		case 5:// TIPO_RESUMEN
			switch (tipoNotificacion) {
			case 1:// generacion reporte
				mailDto.setContent(strCuerpo.replace("{estado}", detalleError).replace("{tipoReporte}", "resumen"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			case 2:// no generado
				mailDto.setContent(
						strCuerpo.replace("{estado}", "no pudo ser generado, ").replace("{tipoReporte}", "resumen"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			}
		case 6:// TIPO_LIBRO_NOTAS
			switch (tipoNotificacion) {
			case 1:// generacion reporte
				mailDto.setContent(
						strCuerpo.replace("{estado}", detalleError).replace("{tipoReporte}", "libro de notas"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			case 2:// no generado
				mailDto.setContent(strCuerpo.replace("{estado}", "no pudo ser generado, ").replace("{tipoReporte}",
						"libro de notas"));
				mailer.notificacionReporte(usuarioId, instCod, sede, jornada, mailDto);
				break;
			}
		}

	}

	public String getMailNotificationReportes(String numDocumUsuario) {
		String mailNotificacion = "";
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("mail.reportes.notificaciones"));
			pst.setString(posicion++, numDocumUsuario);
			rs = pst.executeQuery();
			if (rs.next()) {
				mailNotificacion = rs.getString(1);
			}
		} catch (Exception e) {
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return mailNotificacion;
	}

	public String getPersonaFullName(String numDocumUsuario) {
		String mailNotificacion = "";
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("mail.reportes.persona.notificaciones"));
			pst.setString(posicion++, numDocumUsuario);
			rs = pst.executeQuery();
			if (rs.next()) {
				mailNotificacion = rs.getString(1);
			}
		} catch (Exception e) {
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return mailNotificacion;
	}

}
