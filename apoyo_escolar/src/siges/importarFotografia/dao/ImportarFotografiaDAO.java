/**
 * 
 */
package siges.importarFotografia.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.importarFotografia.vo.FotografiaVO;
import siges.importarFotografia.vo.HiloVO;

/**
 * 24/09/2008
 * 
 * @author Latined
 * @version 1.2
 */
public class ImportarFotografiaDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 */
	public ImportarFotografiaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.importarFotografia.bundle.importarFotografia");
	}

	public List importarEstudiante(List files, String path, long usuario,
			long consecutivo) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		String[] o = null;
		FotografiaVO fotografiaVO = null;
		int insertados = 0;
		int total = files.size();
		InputStream s = null;
		FileOutputStream fos = null;
		BufferedOutputStream dest = null;
		int BUFFER_SIZE = 8192;
		int count;
		// int n=0;
		try {
			cn = cursor.getConnection();
			// System.out.println("IMPORTARFOTOGRAFIA"+": itera por archivos");
			for (int j = 0; j < files.size(); j++) {
				o = (String[]) files.get(j);
				File f = new File(o[0] + o[1]);
				// System.out.println("IMPORTARFOTOGRAFIA"+": estudiante ***  "+o[0]);
				// System.out.println("IMPORTARFOTOGRAFIA"+": estudiante "+o[1]);
				fotografiaVO = getFotografiaEstudiante(cn, o[1]);
				if (fotografiaVO != null) {
					// System.out.println("IMPORTARFOTOGRAFIA"+": estudiante encontrado");
					// System.out.println("fotografia: "+fotografiaVO.getNumeroDocumento());
					// System.out.println("IMPORTARFOTOGRAFIA"+": subiendo a directorio fotoestduaiante");
					s = (InputStream) new FileInputStream(o[0] + o[1]);
					// String = path +
					// rb.getString("fotografia.pathSubir")+File.separator +
					// fotografiaVO.getNumeroDocumento()+".jpg";

					String destFN = Ruta.get(path,
							rb.getString("fotografia.pathSubirFinal"));
					java.io.File fd = new java.io.File(destFN);
					if (!fd.exists()) {
						FileUtils.forceMkdir(fd);
					}

					fos = new FileOutputStream(destFN
							+ fotografiaVO.getNumeroDocumento() + ".jpg");
					dest = new BufferedOutputStream(fos, BUFFER_SIZE);
					byte data[] = new byte[BUFFER_SIZE];

					while ((count = s.read(data, 0, BUFFER_SIZE)) != -1) {
						dest.write(data, 0, count);
					}
					s.close();

					dest.close();
					fos.close();

					st = cn.prepareStatement(rb
							.getString("actualizarEstudiante"));
					i = 1;
					st.setInt(i++, 1);
					st.setLong(i++, fotografiaVO.getNumeroDocumento());
					st.executeUpdate();
					st.close();
					// System.out.println("IMPORTARFOTOGRAFIA"+": foto subida OK");
					insertados++;
				} else {
					// System.out.println("IMPORTARFOTOGRAFIA"+": estudiante no encontrado");
				}
			}
			itemVO = new ItemVO(total, "Total archivos:");
			l.add(itemVO);
			itemVO = new ItemVO(insertados, "Total importados:");
			l.add(itemVO);
			// System.out.println("IMPORTARFOTOGRAFIA: TERMINO DE PROCESAR ARCHIVOS - UPDATE");
			updateHilo(String.valueOf(usuario), consecutivo, 2, total,
					insertados, "Proceso satisfactorio.");
		} catch (SQLException sqle) {
			System.out.println("IMPORTARFOTOGRAFIA"
					+ ": error de datos subiendo" + sqle.getMessage());
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			System.out.println("IMPORTARFOTOGRAFIA"
					+ ": error interno subiendo" + sqle.getMessage());
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

	/*
	 * private FotografiaVO getFotografiaEstudiante(Connection cn,String nombre)
	 * throws Exception { PreparedStatement pst = null; ResultSet rs = null;
	 * FotografiaVO f=null; int i = 0; int tipo=0; long numero=0; try { String
	 * valores[]=nombre.split("_"); String valores2[]=null; if(valores==null ||
	 * valores.length<2){ //System.out.println("archvo no tiene _"); return
	 * null; } pst =
	 * cn.prepareStatement(rb.getString("fotografiaEstudiante.tipoDocumento"));
	 * i=1; pst.setString(i++,valores[0]); rs = pst.executeQuery(); if
	 * (rs.next()) { tipo=rs.getInt(1); }else{
	 * //System.out.println("archvo no tiene tipo"); return null; } rs.close();
	 * pst.close(); //System.out.println("VAlor de numero:"+valores[1]);
	 * valores2=valores[1].replace('.',':').split(":");
	 * //System.out.println("VAlor de numero2:"+valores2.length);
	 * if(valores2==null || valores2.length<2){
	 * //System.out.println("archvo no tiene ."); return null; } pst =
	 * cn.prepareStatement
	 * (rb.getString("fotografiaEstudiante.numeroDocumento")); i=1;
	 * pst.setInt(i++,tipo); pst.setString(i++,valores2[0]); rs =
	 * pst.executeQuery(); if (rs.next()) { numero=rs.getLong(1); }else{
	 * //System.out.println("archvo no tiene numero"); return null; } f=new
	 * FotografiaVO(tipo,numero); }catch (Exception sqle) {
	 * sqle.printStackTrace(); throw new Exception(sqle.getMessage()); } finally
	 * { try { OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(pst); } catch (InternalErrorException
	 * inte) { } } return f; }
	 */

	private FotografiaVO getFotografiaEstudiante(Connection cn, String nombre)
			throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		FotografiaVO f = null;
		int i = 0;
		// int tipo=0;
		// long numero=0;
		try {
			String valores[] = nombre.split("_");
			if (valores == null)
				return null;
			if (valores.length > 1)
				valores[0] = valores[1];
			String valores2[] = valores[0].replace('.', ':').split(":");
			// System.out.println("VAlor de numero2:"+valores2.length);
			if (valores2 == null || valores2.length < 2) {
				// System.out.println("archvo no tiene .");
				return null;
			}
			pst = cn.prepareStatement(rb
					.getString("fotografiaEstudiante.numeroDocumento"));
			i = 1;
			// System.out.println("ESTCAMBIADO: "+valores2[0].replace((char)160,(char)32).trim());
			pst.setString(i++, valores2[0].replace((char) 160, (char) 32)
					.trim());
			rs = pst.executeQuery();
			if (!rs.next()) {
				return null;
			} else {
				f = new FotografiaVO(0, rs.getLong(1));
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	public List importarPersonal(List files) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		String[] o = null;
		FotografiaVO fotografiaVO = null;
		int insertados = 0;
		int total = files.size();
		FileInputStream s = null;
		try {
			cn = cursor.getConnection();
			for (int j = 0; j < files.size(); j++) {
				o = (String[]) files.get(j);
				File f = new File(o[0] + o[1]);
				fotografiaVO = getFotografiaPersonal(cn, o[1]);
				if (fotografiaVO != null) {
					// System.out.println("fotografia: "+fotografiaVO.getNumeroDocumento());
					s = new FileInputStream(f);
					st = cn.prepareStatement(rb.getString("actualizarPersonal"));
					i = 1;
					st.setBinaryStream(i++, s, (int) f.length());
					st.setString(i++, fotografiaVO.getNumeroDocumento2());
					st.executeUpdate();
					st.close();
					insertados++;
					s.close();
				}
			}
			itemVO = new ItemVO(total, "Total archivos:");
			l.add(itemVO);
			itemVO = new ItemVO(insertados, "Total importados:");
			l.add(itemVO);
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

	/*
	 * private FotografiaVO getFotografiaPersonal(Connection cn,String nombre)
	 * throws Exception { PreparedStatement pst = null; ResultSet rs = null;
	 * FotografiaVO f=null; int i = 0; int tipo=0; String numero=null; try {
	 * String valores[]=nombre.split("_"); String valores2[]=null;
	 * if(valores==null || valores.length<2){
	 * //System.out.println("archvo no tiene _"); return null; } pst =
	 * cn.prepareStatement(rb.getString("fotografiaPersonal.tipoDocumento"));
	 * i=1; pst.setString(i++,valores[0]); rs = pst.executeQuery(); if
	 * (rs.next()) { tipo=rs.getInt(1); }else{
	 * //System.out.println("archvo no tiene tipo"); return null; } rs.close();
	 * pst.close(); //System.out.println("VAlor de numero:"+valores[1]);
	 * valores2=valores[1].replace('.',':').split(":");
	 * //System.out.println("VAlor de numero2:"+valores2.length);
	 * if(valores2==null || valores2.length<2){
	 * //System.out.println("archvo no tiene ."); return null; } pst =
	 * cn.prepareStatement(rb.getString("fotografiaPersonal.numeroDocumento"));
	 * i=1; pst.setInt(i++,tipo); pst.setString(i++,valores2[0]); rs =
	 * pst.executeQuery(); if (rs.next()) { numero=rs.getString(1); }else{
	 * //System.out.println("archvo no tiene numero"); return null; } f=new
	 * FotografiaVO(tipo,numero); }catch (Exception sqle) {
	 * sqle.printStackTrace(); throw new Exception(sqle.getMessage()); } finally
	 * { try { OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(pst); } catch (InternalErrorException
	 * inte) { } } return f; }
	 */

	private FotografiaVO getFotografiaPersonal(Connection cn, String nombre)
			throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		FotografiaVO f = null;
		int i = 0;
		String numero = null;
		try {
			String valores[] = nombre.split("_");
			String valores2[] = null;
			if (valores == null) {
				// System.out.println("archvo no tiene _");
				return null;
			}
			if (valores.length > 1) {
				valores[0] = valores[1];
			}
			// System.out.println("VAlor de numero:"+valores[1]);
			valores2 = valores[0].replace('.', ':').split(":");
			// System.out.println("VAlor de numero2:"+valores2.length);
			if (valores2 == null || valores2.length < 2) {
				// System.out.println("archvo no tiene .");
				return null;
			}
			pst = cn.prepareStatement(rb
					.getString("fotografiaPersonal.numeroDocumento"));
			i = 1;
			pst.setString(i++, valores2[0]);
			rs = pst.executeQuery();
			if (rs.next()) {
				numero = rs.getString(1);
			} else {
				// System.out.println("archvo no tiene numero");
				return null;
			}
			f = new FotografiaVO(0, numero);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	public long getConsecutivoHilo(String usuario) throws Exception {
		PreparedStatement pst = null;
		Connection cn = null;
		ResultSet rs = null;
		FotografiaVO f = null;
		int i = 0;
		long numero = 0;
		try {
			// System.out.println("IMPORTARFOTOGRAFIA: GET CONSECUTIVO HILO");
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("hilo.getConsecutivo"));
			i = 1;
			pst.setString(i++, usuario);
			rs = pst.executeQuery();
			if (rs.next()) {
				numero = rs.getLong(1);
			} else {
				// System.out.println("archvo no tiene numero");
				return -1;
			}
			f = new FotografiaVO(0, numero);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return numero;
	}

	public boolean insertHilo(String usuario, long consec, int estado)
			throws Exception {
		PreparedStatement pst = null;
		Connection cn = null;
		ResultSet rs = null;
		FotografiaVO f = null;
		int i = 0;
		long numero = 0;
		boolean continuar = true;
		try {
			// System.out.println("IMPORTARFOTOGRAFIA: INSERT HILO: ESTADO: "
			// + estado);
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("hilo.insertar"));
			i = 1;
			pst.setString(i++, usuario.trim());
			pst.setInt(i++, estado);
			pst.setLong(i++, consec);
			pst.setString(i++, "Iniciando...");
			pst.executeUpdate();
			continuar = true;
		} catch (Exception sqle) {
			continuar = false;
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return continuar;
	}

	public boolean updateHilo(String usuario, long consec, int estado,
			int proce, int act, String msj) throws Exception {
		PreparedStatement pst = null;
		Connection cn = null;
		ResultSet rs = null;
		FotografiaVO f = null;
		int i = 0;
		long numero = 0;
		boolean continuar = true;
		try {
			// System.out.println("IMPORTARFOTOGRAFIA: UPDATE HILO: ESTADO: "
			// + estado + "    CONSEC: " + consec + "    USUARIO: "
			// + usuario + "   msj: " + msj);
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("hilo.update"));
			i = 1;
			pst.setInt(i++, estado);
			pst.setInt(i++, proce);
			pst.setInt(i++, act);
			pst.setString(i++, msj);
			pst.setString(i++, usuario.trim());
			pst.setLong(i++, consec);
			pst.executeUpdate();
			continuar = true;
		} catch (Exception sqle) {
			continuar = false;
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return continuar;
	}

	public boolean deleteHilo(String usuario) throws Exception {
		PreparedStatement pst = null;
		Connection cn = null;
		ResultSet rs = null;
		FotografiaVO f = null;
		int i = 0;
		long numero = 0;
		boolean continuar = true;
		try {
			// System.out.println("IMPORTARFOTOGRAFIA: DELETE HILO");
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("hilo.delete"));
			i = 1;
			pst.setString(i++, usuario);
			pst.executeUpdate();
			continuar = true;
		} catch (Exception sqle) {
			continuar = false;
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return continuar;
	}

	public List getHilosUsuario(String usuario) throws Exception {
		PreparedStatement pst = null;
		Connection cn = null;
		ResultSet rs = null;
		FotografiaVO f = null;
		int i = 0;
		long numero = 0;
		boolean continuar = true;
		List hilos = new ArrayList();
		HiloVO hilo = null;
		try {
			// System.out.println("IMPORTARFOTOGRAFIA: LISTA HILOS USUARIO");
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("hilo.getLista"));
			i = 1;
			pst.setString(i++, usuario);
			rs = pst.executeQuery();
			while (rs.next()) {
				i = 1;
				hilo = new HiloVO();
				String usu = rs.getString(i++);
				int estado = rs.getInt(i++);
				String fecha = rs.getString(i++);
				String msj = rs.getString(i++);
				int proce = rs.getInt(i++);
				int act = rs.getInt(i++);
				long consec = rs.getLong(i++);

				hilo.setUsuario(usu);
				hilo.setEstado(estado);
				hilo.setFecha(fecha);
				hilo.setMensaje(msj);
				hilo.setProc(proce);
				hilo.setAct(act);
				hilo.setConsecutivo(consec);

				hilos.add(hilo);
			}
			continuar = true;
		} catch (Exception sqle) {
			continuar = false;
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return hilos;
	}

}
