package siges.util;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		31/05/2021	JORGE CAMACHO	Se modificó la consulta del método login0() porque en la BD no existe el perfil 210

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.security.MessageDigest;

import siges.dao.Dao;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;


/**
 * Nombre: Acceso<br>
 * Descripcinn: Validar existencia de Login para permitir Acceso<br>
 * Funciones de la pngina: Recibir peticiones de Logibn y devolverlas<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class Acceso {

	private static String[] consultas;

	private static ResourceBundle rb;
	
	private static Cursor cursor = new Cursor();
	
	
	
	
	
	private Acceso() {
	}
	
	
	
	
	
	/**
	 * Funcinn: Cargar los datos necesarios para validar acceso<br>
	 */
	static public void Cargar() {
		
		rb = ResourceBundle.getBundle("siges.login.bundle.login");
		
		String[] p = {
				rb.getString("loginUsuario1"),				// 0
				rb.getString("loginUsuario2"),				// 1
				rb.getString("loginUsuario3"),				// 2
				rb.getString("loginUsuario4"),				// 3
				rb.getString("loginUsuario6"),				// 4
				rb.getString("Perfil1"),					// 5
				rb.getString("Perfil2"),					// 6
				rb.getString("Perfil3"),					// 7
				rb.getString("Perfil4"),					// 8
				rb.getString("Perfil6"),					// 9
				rb.getString("loginPerfilUsuario1"),		// 10
				rb.getString("loginPerfilUsuario2"),		// 11
				rb.getString("loginPerfilUsuario3"),		// 12
				rb.getString("loginPerfilUsuario4"),		// 13
				rb.getString("loginPerfilUsuario6"),		// 14
				rb.getString("loginUsuario224"),			// 15
				rb.getString("login.Institucion"),			// 16
				rb.getString("login.Sede"),					// 17
				rb.getString("login.Jornada"),				// 18
				rb.getString("login.Sede2"),				// 19
				rb.getString("login.Jornada2"),				// 20
				rb.getString("login.Institucion3"),			// 21
				rb.getString("login.Sede3"),				// 22
				rb.getString("login.Jornada3"),				// 23
				rb.getString("metodologiaInstitucion"),		// 24
				rb.getString("loginPerfilEstudiante"),		// 25
				rb.getString("loginPerfilArtEstudiante"),	// 26
				rb.getString("getPerfil"), 					// 27
				rb.getString("loginUsuario44"),				// 28
				rb.getString("loginUsuario0"),				// 29
		};
		consultas = p;
		p = null;
		// System.out.println("Acceso -Listo-");
	}
	
	
	
	
	
	/**
	 * Funcinn: Cerrar conexinn<br>
	 */
	public void cerrar() {
	}

	static public int validar() {
		Connection cn = null;
		CallableStatement pst = null;
		ResultSet rs = null;
		String con = "{call PK_NOTAS.notas_boletines(,?,)}";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareCall(con);
			pst.setInt(1, 1);
			pst.executeUpdate();
			// System.out.println("si funco");
		} catch (InternalErrorException e) {
			return 1;
		} catch (SQLException e) {
			System.out.println("error en callable:" + e);
			return 1;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return 1;
	}

	static public int publico(String a) {
		int n = -1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (a == null)
			return n;
		String con = rb.getString("publico");
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			rs = pst.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (InternalErrorException e) {
			return n;
		} catch (SQLException e) {
			System.out.println("Error validar servicio:" + e);
			return n;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return n;
	}

	static public String privado(String a, String b) {
		String n = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (a == null)
			return n;
		String con = rb.getString("privado");
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setLong(1, Long.parseLong(a));
			pst.setString(2, b);
			rs = pst.executeQuery();
			if (rs.next())
				return rs.getString(1);
		} catch (InternalErrorException e) {
			return n;
		} catch (SQLException e) {
			System.out.println("Error validar servicio privado:" + e);
			return n;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return n;
	}

	/**
	 * Funcinn: Encripta una cadena String a MD5
	 * 
	 * @param Usuario
	 * 
	 * @return String
	 **/
	public static String md5(String clear) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(clear.getBytes());
		int size = b.length;
		StringBuffer h = new StringBuffer(size);
		// algoritmo y arreglo md5
		for (int i = 0; i < size; i++) {
			int u = b[i] & 255;
			if (u < 16) {
				h.append("0" + Integer.toHexString(u));
			} else {
				h.append(Integer.toHexString(u));
			}
		}
		// clave encriptada
		return h.toString();
	}

	/**
	 * Funcinn: Validar Login y Clave<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @return String[][]
	 * @throws Exception
	 */
	static public String[][] autorizado(String a, String b) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		
		if (a == null || b == null) {
			Logger.print(a, "Error al traer valores de usuario y contraseña: "+ a +" "+ b , 0, 1, "");
			return null;
			
		}

		String con = rb.getString("autorizado");
		
		try {
			cn = cursor.getConnection();

			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			pst.setString(2, md5(b));
			rs = pst.executeQuery();
			String str[][] = getFiltroMatriz(getCollection(rs));

			return str;
			
		} catch (InternalErrorException e) {
			Logger.print(a, "Error al traer valores de usuario y contraseña: "+ e.getMensaje()  , 0, 1, "");
			return null;
		} catch (SQLException e) {
			Logger.print(a, "Error al traer valores de usuario y contraseña: "+ e.getMessage() , 0, 1, "");
			System.out.println("Error Acceso Autorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
				
				Logger.print(a, "Error al traer valores de usuario y contraseña: "+ e.getMessage() , 0, 1, "");
			}
		}
		
	}
	
	
	
	
	
	/**
	 * Funcinn: Validar Login y Clave<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @return String[][]
	 * @throws Exception
	 */
	static public String[][] autorizadoEstudiante(String a, String b)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (a == null || b == null)
			{
			Logger.print(a, "Error al traer valores de usuario y contraseña: "+ a+" " + b , 0, 1, "");
			return null;
			}
			
		String con = rb.getString("autorizadoEstudiante");
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			pst.setString(2, md5(b));
			rs = pst.executeQuery();
			String str[][] = getFiltroMatriz(getCollection(rs));
			return str;
		} catch (InternalErrorException e) {
			Logger.print(a, "Error al traer valores de usuario y contraseña: "+ e.getMensaje() , 0, 1, "");
			return null;
		} catch (SQLException e) {
			Logger.print(a, "Error al traer valores de usuario y contraseña: "+ e.getMessage() , 0, 1, "");
			System.out.println("Error Acceso Autorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public String[] getMetodologia(String a) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[] met = new String[2];
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consultas[24]);
			pst.setString(1, a);
			rs = pst.executeQuery();
			if (rs.next()) {
				met[0] = rs.getString(1);
				met[1] = rs.getString(2);
			}
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error get Metodologias:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return met;
	}

	/**
	 * Funcinn: Validar Login y Clave<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @return String[][]
	 * @throws Exception
	 */
	static public String[][] autorizado(String a, String b, String c, String d)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (a == null || b == null || c == null || d == null)
			return null;
		String con = "select PERFNIVEL,UsuCodJerar,UsuPerfCodigo,USUPERNUMDOCUM from usuario,PERFIL where UsuPerfCodigo=PERFCODIGO and UsuLogin=? and UsuPassword=? and UsuCodJerar=? and UsuPerfCodigo=?";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			pst.setString(2, md5(b));
			pst.setLong(3, Long.parseLong(c));
			pst.setLong(4, Long.parseLong(d));
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			Collection list = new ArrayList();
			Object[] o;
			int i = 0;
			while (rs.next()) {
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			String str[][] = getFiltroMatriz(list);
			return str;
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error AccesoAutorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public String pass(String a) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String ret = null;
		String con = "SELECT DISTINCT USUPASSWORD FROM USUARIO WHERE USULOGIN=?";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			rs = pst.executeQuery();
			while (rs.next()) {
				ret = rs.getString(1);
			}
			return ret;
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error AccesoAutorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public String nombrecompleto(String a, String b) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String ret = null;
		String con = "SELECT distinct nvl(EstApellido1,'')||' '||nvl(EstApellido2,'')||' '|| nvl(EstNombre1,'')||' '||nvl(EstNombre2,'') nombcompl FROM PROMOCION, G_JERARQUIA, ESTUDIANTE WHERE EstTipoDoc = ? AND EstNumDoc = ? AND ProCodJerar = G_JerCodigo AND ProCodEstud = EstCodigo AND ProCodJerar IN (SELECT G_JerCodigo FROM G_JERARQUIA WHERE G_Jertipo = 1 AND G_JerNivel = 8 AND ProCodJerar = G_JerCodigo AND ProCodEstud = EstCodigo)";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setInt(1, Integer.parseInt(a));
			pst.setString(2, b);
			rs = pst.executeQuery();
			while (rs.next()) {
				ret = rs.getString(1);
			}
			return ret;
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error AccesoAutorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public String instestudiante(String a, String b) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String ret = null;
		String con = "select g_jerinst from estudiante,g_jerarquia where esttipodoc=? and estnumdoc=? and estgrupo=g_jercodigo";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setInt(1, Integer.parseInt(a));
			pst.setString(2, b);
			rs = pst.executeQuery();
			while (rs.next()) {
				ret = rs.getString(1);
			}
			return ret;
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error AccesoAutorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public String getasistencias(String a, String b) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String ret = null;
		String con = "select count(*) from asistencia,estudiante where estnumdoc=? and asicodperso=estcodigo and asivigencia=?";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			pst.setInt(2, Integer.parseInt(b));
			rs = pst.executeQuery();
			while (rs.next()) {
				ret = rs.getString(1);
			}
			return ret;
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error AccesoAutorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public String getretardos(String a, String b) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String ret = null;
		String con = "select count(*) from retardo,estudiante where estnumdoc=? and retcodestud=estcodigo and retvigencia=?";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			pst.setInt(2, Integer.parseInt(b));
			rs = pst.executeQuery();
			while (rs.next()) {
				ret = rs.getString(1);
			}
			return ret;
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error AccesoAutorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public String getsalidastarde(String a, String b) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String ret = null;
		String con = "select count(*) from salida_tarde,estudiante where estnumdoc=? and saltarcodestud=estcodigo and saltarvigencia=?";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(con);
			pst.setString(1, a);
			pst.setInt(2, Integer.parseInt(b));
			rs = pst.executeQuery();
			while (rs.next()) {
				ret = rs.getString(1);
			}
			return ret;
		} catch (InternalErrorException e) {
			return null;
		} catch (SQLException e) {
			System.out.println("Error AccesoAutorizado:" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public Collection getTipoDoc() {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("SELECT g_concodigo,g_conNombre FROM G_Constante WHERE G_ConTipo=10 ORDER BY G_ConOrden");
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close(); // System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	static public Collection getgradocol(String tip, String doc) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("SELECT g_jerinst,insnombre,g_jersede,sednombre,g_jerjorn,g_connombre,g_jergrado,g_granombre,g_jergrupo,provigencia,protipoprom,(SELECT G_CONNOMBRE FROM G_CONSTANTE where g_jermetod=G_CONCODIGO and G_CONTIPO=3 and rownum=1) metodologia FROM PROMOCION, G_JERARQUIA, ESTUDIANTE,g_grado,institucion,sede,g_constante WHERE EstTipoDoc = ? AND EstNumDoc = ? AND ProCodJerar = G_JerCodigo AND ProCodEstud = EstCodigo and inscodigo=g_jerinst and g_gracodigo=g_jergrado and sedcodins=g_jerinst and sedcodigo=g_jersede and g_contipo=5 and g_concodigo=g_jerjorn AND ProCodJerar IN (SELECT G_JerCodigo FROM G_JERARQUIA WHERE G_Jertipo = 1 AND G_JerNivel = 8 AND ProCodJerar = G_JerCodigo AND ProCodEstud = EstCodigo)");
			pst.setInt(1, Integer.parseInt(tip));
			pst.setString(2, doc);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close(); // System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	static public Collection getasistencia(String doc, String vig) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("select asimotivo,decode(asimotivo,null,'Sin Motivo',0,'Sin Justificar',(SELECT G_CONNOMBRE FROM G_CONSTANTE where asimotivo=G_CONCODIGO and G_CONTIPO=15 and rownum=1)) motivo,count(*) from asistencia,estudiante where estnumdoc=? and asicodperso=estcodigo and asivigencia=? group by asimotivo");
			pst.setString(1, doc);
			pst.setInt(2, Integer.parseInt(vig));
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close(); // System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	static public String[][] getFiltroMatriz(Collection a)
			throws InternalErrorException {
		Object[] o;
		if (!a.isEmpty()) {
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

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param Collection
	 *            lista
	 * @return String[]
	 */
	static public String[] getFiltroArray(Collection a)
			throws InternalErrorException {
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
	
	
	
	
	
	/**
	 * Funcinn: Obetener perfiles del usuario logeado<br>
	 * 
	 * @param String
	 *            [][] params
	 * @return Collection
	 */
	@SuppressWarnings("rawtypes")
	static public Collection getPerfiles(String[][] params) {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		
		Collection list = null;
		Collection list2 = new ArrayList();
		
		try {
			
			cn = cursor.getConnection();
			
			for (int i = 0; i < params.length; i++) {
				
				int n = Integer.parseInt(params[i][0]);
				
				switch (n) {
				case 0:
					pst = cn.prepareStatement(rb.getString("PerfilPOA"));
					// System.out.println("ENTRO GET PERFILES POA: param1: "+params[i][1]+" -param2: "+params[i][2]);
					break;

				case 1:
					pst = cn.prepareStatement(consultas[5]);
					// c.addAll(u.getFiltro(consultas[5],list));
					break;
				case 2:
					pst = cn.prepareStatement(consultas[6]);
					// c.addAll(u.getFiltro(consultas[6],list));
					break;
				case 3:
					pst = cn.prepareStatement(consultas[7]);
					// c.addAll(u.getFiltro(consultas[7],list));
					break;
				case 4:
					pst = cn.prepareStatement(consultas[8]);
					// c.addAll(u.getFiltro(consultas[8],list));
					break;
				case 6:
					pst = cn.prepareStatement(consultas[9]);
					// c.addAll(u.getFiltro(consultas[9],list));
					break;
				}
				
				pst.setString(1, (params[i][1]));
				pst.setString(2, (params[i][2]));
				
				rs = pst.executeQuery();
				int m = rs.getMetaData().getColumnCount();
				
				list = new ArrayList();
				Object[] o;
				int j = 0;
				
				while (rs.next()) {
					o = new Object[m];
					for (j = 1; j <= m; j++)
						o[j - 1] = rs.getString(j);
					list.add(o);
				}
				
				if (list.size() > 0) {
					list2.addAll(list);
				}
				
				rs.close();
				pst.close();
				
			}
			
			// System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: " + e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
			
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		
		return null;
		
	}
	
	
	
	
	
	static public Collection getlogrosdesc(String ins, String vig) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("select nvl(insparsubtitbol,'Logros'),nvl(insparevaldescriptores,'Descriptores') from institucion_parametro where insparcodinst=? and insparvigencia=?");
			pst.setString(1, ins);
			// if (n == 6) pst.setString(2, (params[i][2]));
			pst.setString(2, vig);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close();
			// System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obetener perfiles del usuario logeado<br>
	 * 
	 * @param String
	 *            [][] params
	 * @return Collection
	 */
	static public Collection[] ponerPerfiles(int n, long jer) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection[] list = null;
		int nivel = n;
		try {
			int tt = 0;
			cn = cursor.getConnection();
			switch (nivel) {
			case 2:
				list = new Collection[3];
				for (int i = 16; i < 19; i++) {
					switch (i) {
					case 16:
						pst = cn.prepareStatement(consultas[16]);
						break;
					case 17:
						pst = cn.prepareStatement(consultas[17]);
						break;
					case 18:
						pst = cn.prepareStatement(consultas[18]);
						break;
					}
					pst.setLong(1, jer);
					rs = pst.executeQuery();
					list[tt] = getCollection(rs);
					rs.close();
					pst.close();
					tt++;
				}
				break;
			case 4:
				list = new Collection[2];
				for (int i = 19; i < 21; i++) {
					switch (i) {
					case 19:
						pst = cn.prepareStatement(consultas[19]);
						break;
					case 20:
						pst = cn.prepareStatement(consultas[20]);
						break;
					}
					pst.setLong(1, jer);
					rs = pst.executeQuery();
					list[tt] = getCollection(rs);
					rs.close();
					pst.close();
					tt++;
				}
				break;
			}
			return list;
		} catch (Exception e) {
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	static public Collection[] ponerPerfilesTodos(String[][] params) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection[] list = null;
		try {
			cn = cursor.getConnection();
			list = new Collection[3];
			long jer = 0;
			long niv = 0;
			String inst = "";
			for (int i = 0; i < params.length; i++) {
				niv = Long.parseLong(params[i][0]);
				if (niv == 4) {
					if (i == 0)
						inst = inst + "?";
					else
						inst = inst + ",?";
				}
			}
			// INSTITUCIONES
			// System.out.println(params.length + "//" + consultas[21] + inst +
			// ")");
			pst = cn.prepareStatement(consultas[21] + inst
					+ ") order by INSNOMBRE ");
			// System.out.println(consultas[21]+inst+")");
			int pos = 1;
			for (int i = 0; i < params.length; i++) {
				jer = Long.parseLong(params[i][1]);
				niv = Long.parseLong(params[i][0]);
				if (niv == 4) {
					pst.setLong(pos++, jer);
				}
			}
			rs = pst.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			pst.close();
			// SEDES
			pst = cn.prepareStatement(consultas[22] + inst
					+ ") order by SEDCODIGO ");
			pos = 1;
			for (int i = 0; i < params.length; i++) {
				jer = Long.parseLong(params[i][1]);
				niv = Long.parseLong(params[i][0]);
				if (niv == 4) {
					pst.setLong(pos++, jer);
				}
			}
			rs = pst.executeQuery();
			list[1] = getCollection(rs);
			rs.close();
			pst.close();
			// JORNADAS
			pst = cn.prepareStatement(consultas[23] + inst
					+ ") ORDER BY SEDJORCODJOR");
			pos = 1;
			for (int i = 0; i < params.length; i++) {
				jer = Long.parseLong(params[i][1]);
				niv = Long.parseLong(params[i][0]);
				if (niv == 4) {
					pst.setLong(pos++, jer);
				}
			}
			rs = pst.executeQuery();
			list[2] = getCollection(rs);
			rs.close();
			pst.close();
			// System.out.println(list[0].size()+"//"+list[1].size()+"//"+list[2].size());
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	static public Collection[] getGradoGrupo(String ins) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection[] list = null;
		try {
			cn = cursor.getConnection();
			list = new Collection[6];
			int pos = 1;
			// SEDES
			pst = cn.prepareStatement("select SEDCODIGO,SEDNOMBRE,SEDCODINS from sede where sedcodins=? order by 1");
			pst.setString(pos++, ins);
			rs = pst.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			pst.close();

			pos = 1;
			// JORANADAS
			pst = cn.prepareStatement("select distinct SEDJORCODJOR,G_ConNombre from sede_jornada,g_constante where sedjorcodinst=? and G_ConTipo=5 and G_ConCodigo=SEDJORCODJOR order by 1");
			pst.setString(pos++, ins);
			rs = pst.executeQuery();
			list[1] = getCollection(rs);
			rs.close();
			pst.close();
			pos = 1;

			// GRADO
			pst = cn.prepareStatement("SELECT distinct GRACODIGO,G_GRANOMBRE,GRACODINST FROM GRADO, G_GRADO WHERE GraCodInst=? and G_GRACODIGO=GraCodigo order by 1");
			pst.setString(pos++, ins);
			rs = pst.executeQuery();
			list[2] = getCollection(rs);
			rs.close();
			pst.close();
			// GRUPO
			pos = 1;
			pst = cn.prepareStatement("select distinct GruCodigo,GruNombre,a.G_JerGrado,G_JerSede,G_JerJorn from grupo, g_jerarquia  a, estudiante e where a.G_JerTipo=1 and a.G_JerNivel=7 and a.G_JerInst=? and a.G_JerCodigo=GruCodJerar and e.estgrupo=grujergrupo order by GRUNOMBRE");
			pst.setLong(pos++, Long.parseLong(ins));
			rs = pst.executeQuery();
			list[3] = getCollection(rs);
			rs.close();
			pst.close();

			pos = 1;
			// JORNADA FILTRO
			pst = cn.prepareStatement("select SEDJORCODJOR,G_ConNombre,sedjorcodsede from sede_jornada,g_constante where sedjorcodinst=? and G_ConTipo=5 and G_ConCodigo=SEDJORCODJOR order by 1");
			pst.setString(pos++, ins);
			rs = pst.executeQuery();
			list[4] = getCollection(rs);
			rs.close();
			pst.close();

			pos = 1;
			// GRUPO FILTRO
			pst = cn.prepareStatement("select distinct GruCodigo,GruNombre,a.G_JerGrado from grupo, g_jerarquia  a where a.G_JerTipo=1 and a.G_JerNivel=7 and a.G_JerInst=? and a.G_JerCodigo=GruCodJerar order by 1");
			pst.setString(pos++, ins);
			rs = pst.executeQuery();
			list[5] = getCollection(rs);
			rs.close();
			pst.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	static public Collection[] getvalidaciones(String ins, String vig) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection[] list = null;
		try {
			cn = cursor.getConnection();
			list = new Collection[3];
			int pos = 1;
			// estudiantes null
			pst = cn.prepareStatement("select insnombre,sednombre,G_ConNombre,granombre,grunombre from estudiante e,g_jerarquia,institucion,sede,g_constante,grado,grupo where not exists(select distinct procodestud,insnombre,sednombre,G_ConNombre,granombre from Promocion,g_jerarquia,institucion,sede,g_constante,grado,grupo where procodinst=? and provigencia=? and g_jerarquia.g_jerinst=promocion.procodinst and g_jerarquia.g_jergrado=promocion.procodgrado and promocion.procodjerar=g_jerarquia.g_jercodigo and sedcodins=procodinst and sedcodigo=g_jersede and inscodigo=procodinst and g_constante.g_contipo=5 and g_constante.g_concodigo=g_jerarquia.g_jerjorn and gracodinst=procodinst and gracodigo=procodgrado and grujergrupo=procodjerar and e.estcodigo=procodestud and gracodmetod=g_jermetod) and EstGrupo=G_JerCodigo and ESTESTADO>99 and ESTESTADO<200 and G_JerTipo=1 and G_JerNivel=8 and G_JerInst=? and g_jerinst=inscodigo and sedcodins=g_jerinst and g_jersede=sedcodigo and g_constante.g_contipo=5 and g_constante.g_concodigo=g_jerarquia.g_jerjorn and gracodinst=g_jerinst and gracodigo=G_JerGrado and grujergrupo=g_jercodigo and grucodigo=g_jergrupo and gracodmetod=g_jermetod group by grunombre,granombre,G_ConNombre,sednombre,insnombre");
			pst.setString(pos++, ins);
			pst.setString(pos++, vig);
			pst.setString(pos++, ins);
			rs = pst.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			pst.close();

			pos = 1;
			// estudiantes pendiente
			pst = cn.prepareStatement("select count(procodprom),insnombre,sednombre,G_ConNombre,granombre,grunombre no_estado from Promocion,g_jerarquia,sede,institucion,g_constante,grado,grupo where procodinst=? and provigencia=? and procodprom in(5) and g_jerarquia.g_jerinst=promocion.procodinst  and g_jerarquia.g_jergrado=promocion.procodgrado and promocion.procodjerar=g_jerarquia.g_jercodigo and sedcodins=procodinst and sedcodigo=g_jersede and inscodigo=procodinst and g_constante.g_contipo=5 and g_constante.g_concodigo=g_jerarquia.g_jerjorn and gracodinst=procodinst and gracodigo=procodgrado and grujergrupo=procodjerar and gracodmetod=g_jermetod group by insnombre,sednombre,G_ConNombre,granombre,grunombre");
			pst.setString(pos++, ins);
			pst.setString(pos++, vig);
			rs = pst.executeQuery();
			list[1] = getCollection(rs);
			rs.close();
			pst.close();
			pos = 1;

			// grupos cerrados
			pst = cn.prepareStatement("select '1' as percodigo,nvl(PerEstado1,0),'2' as percodigo,nvl(PerEstado2,0),'3' as percodigo,nvl(PerEstado3,0),'4' as percodigo,nvl(PerEstado4,0),'5' as percodigo,nvl(PerEstado5,0),'6' as percodigo,nvl(PerEstado6,0),'7' as percodigo,nvl(PerEstado7,0),sednombre,G_ConNombre from periodo,sede,g_constante where percodinst=? and pervigencia=? and sedcodins=percodinst and sedcodigo=percodsede and g_constante.g_contipo=5 and g_constante.g_concodigo=percodjornada");
			pst.setString(pos++, ins);
			pst.setString(pos++, vig);
			rs = pst.executeQuery();
			list[2] = getCollection(rs);
			rs.close();
			pst.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	
	
	
	
	/**
	 * Funcinn: Obtener Usuario<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            param
	 * @param String
	 *            perf
	 * @return boolean
	 */
	static public Login getUsuario(String a, String b, String[][] param, String perf, String inst, String sed, String jor) {

		switch (Integer.parseInt(param[0][0])) {
		case 0: //nivel central poa
			return login0(a, b, param, perf);
			
		case 1:// departamento
			return login1(a, b, param, perf);
			
		case 2:// municipio
			return login2(a, b, param, perf);
			
		case 3:// nucleo
			return login3(a, b, param, perf);
			
		case 4:// inst
			return login4(a, b, param, perf, inst);
			
		case 6:// docente
			return login6(a, b, param, perf);
			
		}
		
		return null;
		
	}
	
	
	
	
	
	
	private static Login login0(String a, String b, String[][] param, String perf) {
		
		String[][] us = null;
		Connection cn = null;
		
		Object[] o;
		Collection list;
		
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		list = new ArrayList();
		
		try {
			
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consultas[29]);
			pst.setString(1, a);
			pst.setString(2, md5(b));
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			
			if (us != null) {
				Login l = new Login();
				
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(us[0][3]);
				l.setUsuarioJerar(us[0][2]);
				l.setUsuarioId(us[0][0]);
				//l.setDepId(us[0][z++]);
				//l.setDep(us[0][z++]);
				l.setNivel(us[0][9]);
				l.setNomPerfil(us[0][6]);
				return l;
			}
		} catch (Exception e) {
			System.out.println("error" + e);
		} finally {
			try {
				// c.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obtener Usuario<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            param
	 * @param String
	 *            perf
	 * @return boolean
	 */
	static public Login getUsuarioEstudiante(String[][] param) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Login l = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consultas[25]);
			pst.setLong(1, Long.parseLong(param[0][0]));
			rs = pst.executeQuery();
			if (rs.next()) {
				i = 1;
				l = new Login();
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(rs.getString(i++));
				l.setUsuarioJerar(rs.getString(i++));
				l.setUsuarioId(rs.getString(i++));
				l.setNivel(rs.getString(i++));
				l.setUsuario(rs.getString(i++));
				l.setInstId(rs.getString(i++));
				l.setInst(rs.getString(i++));
				l.setSedeId(rs.getString(i++));
				l.setSede(rs.getString(i++));
				l.setJornadaId(rs.getString(i++));
				l.setJornada(rs.getString(i++));
				l.setMetodologiaId(rs.getString(i++));
				l.setMetodologia(rs.getString(i++));
				l.setGradoId(rs.getString(i++));
				l.setGrado(rs.getString(i++));
				l.setGrupoId(rs.getString(i++));
				l.setGrupo(rs.getString(i++));
				l.setCodigoEst(rs.getString(i++));
			}
			if (l != null) {
				// calcular los datos de art_estudiante
				rs.close();
				pst.close();
				pst = cn.prepareStatement(consultas[26]);
				pst.setLong(1, Long.parseLong(l.getCodigoEst()));
				rs = pst.executeQuery();
				if (rs.next()) {
					i = 1;
					l.setArtEspId(rs.getString(i++));
					l.setArtSemId(rs.getString(i++));
					l.setArtGrupoId(rs.getString(i++));
				} else {
					Logger.print(l.getUsuario(), "Error al traer los datos de Art_estudiante: "+ l.getUsuario() , 0, 1, "Clase Acceso,Metodo getUsuarioEstudiante");
					return null;
				}
				rs.close();
				pst.close();
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}
			}
			return l;
		} catch (Exception e) {
			Logger.print(l.getUsuario(), "Error al traer los datos de Art_estudiante: "+ e.getMessage() , 0, 1, "Clase Acceso,Metodo getUsuarioEstudiante");
			System.out.println("error" + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
				Logger.print(l.getUsuario(), "Error al traer los datos de Art_estudiante: "+ e.getMessage() , 0, 1, "Clase Acceso,Metodo getUsuarioEstudiante");
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obtener Usuario<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login getUsuario2(String a, String b) {
		a = a.replace('\'', ' ').replace('"', ' ').trim();
		b = b.replace('\'', ' ').replace('"', ' ').trim();
		return loginSoporte(a, b);
	}

	static public Login getUsuario4(String a, String b) {
		a = a.replace('\'', ' ').replace('"', ' ').trim();
		b = b.replace('\'', ' ').replace('"', ' ').trim();
		return loginInstitucion(a, b);
	}

	/**
	 * Funcinn: Obtener Usuario Ingeniero de Soporte<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login loginSoporte(String a, String b) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[][] us = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			// cn=c.getConnection(1);
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consultas[15]);
			pst.setString(1, a);
			pst.setString(2, b);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			if (us != null) {
				Login l = new Login();
				int z = 0;
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(us[0][z++]);
				l.setUsuarioJerar(us[0][z++]);
				l.setUsuarioId(us[0][z++]);
				l.setDepId(us[0][z++]);
				l.setDep(us[0][z++]);
				l.setMunId(us[0][z++]);
				l.setMun(us[0][z++]);
				l.setUsuario(us[0][z++] + " " + us[0][z++] + " " + us[0][z++]);
				l.setNivel(us[0][z++]);
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}
				return l;
			}
		} catch (Exception e) {
			System.out.println("ErrorLogin224:" + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	static public Login loginInstitucion(String a, String b) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[][] us = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consultas[15]);
			pst.setString(1, a);
			pst.setString(2, b);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			if (us != null) {
				Login l = new Login();
				int z = 0;
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(us[0][z++]);
				l.setUsuarioJerar(us[0][z++]);
				l.setUsuarioId(us[0][z++]);
				l.setDepId(us[0][z++]);
				l.setDep(us[0][z++]);
				l.setMunId(us[0][z++]);
				l.setMun(us[0][z++]);
				l.setUsuario(us[0][z++] + " " + us[0][z++] + " " + us[0][z++]);
				l.setNivel(us[0][z++]);
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}
				return l;
			}
		} catch (Exception e) {
			System.out.println("ErrorLogin224:" + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obtener Usuario nucleo<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login login3(String a, String b, String[][] param, String perf) {
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			cn = cursor.getConnection();
			if (perf == null) {
				pst = cn.prepareStatement(consultas[2]);
				pst.setString(1, (param[0][1]));
				pst.setString(2, (param[0][3]));
			} else {
				pst = cn.prepareStatement(consultas[12]);
				pst.setString(1, (perf));
				pst.setString(2, (param[0][3]));
			}
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			if (us != null) {
				Login l = new Login();
				int z = 0;
				/*
				 * PerNumDocum,PerTipo,PerNombre1,PerNombre2,PerApellido1,a.
				 * G_ConNombre,G_JerMunic, b.G_ConNombre,G_JerLocal,NUCNOMBRE
				 */
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(param[0][2]);
				l.setUsuarioJerar(param[0][1]);
				l.setUsuarioId(us[0][z++]);
				l.setTipo(us[0][z++]);
				l.setUsuario(us[0][z++] + " " + us[0][z++] + " " + us[0][z++]);
				l.setDepId(us[0][z++]);
				l.setDep(us[0][z++]);
				l.setMunId(us[0][z++]);
				l.setMun(us[0][z++]);
				l.setLocId(us[0][z++]);
				l.setLoc(us[0][z++]);
				l.setNivel(param[0][0]);
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}
				return l;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		} finally {
			try {
				// c.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obtener Usuario Institucinn<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login login1(String a, String b, String[][] param, String perf) {
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			// cn=c.getConnection(1);
			cn = cursor.getConnection();
			if (perf == null) {
				pst = cn.prepareStatement(consultas[0]);
				pst.setString(1, a);
				pst.setString(2, md5(b));
			} else {
				pst = cn.prepareStatement(consultas[10]);
				pst.setString(1, a);
				pst.setString(2, md5(b));
				pst.setString(3, perf);
			}
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			if (us != null) {
				Login l = new Login();
				int z = 0;
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(us[0][z++]);
				l.setUsuarioJerar(us[0][z++]);
				l.setUsuarioId(us[0][z++]);
				l.setDepId(us[0][z++]);
				l.setDep(us[0][z++]);
				l.setNivel(us[0][z++]);
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}
				return l;
			}
		} catch (Exception e) {
			System.out.println("error" + e);
		} finally {
			try {
				// c.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obtener Usuario Institucinn<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login login2(String a, String b, String[][] param, String perf) {
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			// cn=c.getConnection(1);
			cn = cursor.getConnection();
			if (perf == null) {
				pst = cn.prepareStatement(consultas[1]);
				pst.setString(1, (param[0][1]));
				pst.setString(2, (param[0][3]));
			} else {
				pst = cn.prepareStatement(consultas[11]);
				pst.setString(1, (perf));
				pst.setString(2, (param[0][3]));
			}
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			if (us != null) {
				Login l = new Login();
				int z = 0;
				/*
				 * PerNumDocum,PerTipo,PerNombre1,PerNombre2,PerApellido1,
				 * InsCodDepto
				 * ,a.G_ConNombre,InsCodMun,b.G_ConNombre,InsCodLocal,
				 * NUCNOMBRE,InsCodigo,InsNombre
				 */
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(param[0][2]);
				l.setUsuarioJerar(param[0][1]);
				l.setUsuarioId(us[0][z++]);
				l.setTipo(us[0][z++]);
				l.setUsuario(us[0][z++] + " " + us[0][z++] + " " + us[0][z++]);
				l.setDepId(us[0][z++]);
				l.setDep(us[0][z++]);
				l.setMunId(us[0][z++]);
				l.setMun(us[0][z++]);
				l.setNivel(param[0][0]);
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}
				return l;
			}
		} catch (Exception e) {
			System.out.println("error: " + e);
		} finally {
			try {
				// c.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obtener Usuario Institucinn<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login login4(String a, String b, String[][] param,
			String perf, String inst) {
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			cn = cursor.getConnection();
			if (perf == null) {
				if (inst != null) {
					pst = cn.prepareStatement(consultas[3]);
					pst.setString(1, inst);
					pst.setString(2, (param[0][3]));
				} else {
					pst = cn.prepareStatement(consultas[28]);
					pst.setString(1, param[0][1]);
					pst.setString(2, param[0][3]);
				}
			} else {
				pst = cn.prepareStatement(consultas[13]);
				pst.setString(1, (perf));
				pst.setString(2, (param[0][3]));
			}
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			if (us != null) {

				Login l = new Login();
				int z = 0;
				/*
				 * PerNumDocum,PerTipo,PerNombre1,PerNombre2,PerApellido1,
				 * InsCodDepto
				 * ,a.G_ConNombre,InsCodMun,b.G_ConNombre,InsCodLocal,
				 * NUCNOMBRE,InsCodigo,InsNombre
				 */
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(param[0][2]);
				l.setUsuarioJerar(param[0][1]);
				l.setUsuarioId(us[0][z++]);
				l.setTipo(us[0][z++]);
				l.setUsuario(us[0][z++] + " " + us[0][z++] + " " + us[0][z++]);
				l.setDepId(us[0][z++]);
				l.setDep(us[0][z++]);
				l.setMunId(us[0][z++]);
				l.setMun(us[0][z++]);
				l.setLocId(us[0][z++]);
				l.setLoc(us[0][z++]);
				l.setInstId(us[0][z++]);
				l.setInst(us[0][z++]);
				l = login4Params(l);
				l.setNivel(param[0][0]);
				// System.out.println("valor de mun: "+l.getMunId()+"//"+l.getMun());
				// metodologia
				pst = cn.prepareStatement(consultas[24]);
				pst.setString(1, l.getInstId());
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setMetodologiaId(rs.getString(1));
					l.setMetodologia(rs.getString(2));
				}
				rs.close();
				pst.close();
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}
				return l;
			}
		} catch (Exception e) {
			System.out.println("error:: " + e);
		} finally {
			try {
				// c.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Funcinn: Obtener Usuario Institucinn<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login login6(String a, String b, String[][] param, String perf) {
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list;
		Object[] o;
		list = new ArrayList();
		try {
			cn = cursor.getConnection();
			if (perf == null) {
				pst = cn.prepareStatement(consultas[4]);
				pst.setString(1, (param[0][1]));
				pst.setString(2, (param[0][3]));
			} else {
				pst = cn.prepareStatement(consultas[14]);
				pst.setString(1, (perf));
				pst.setString(2, (param[0][3]));
			}
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			rs.close();
			pst.close();
			us = getFiltroMatriz(list);
			if (us != null) {
				Login l = new Login();
				int z = 0;
				/*
				 * PerNumDocum,PerTipo,PerNombre1,PerNombre2,PerApellido1,
				 * InsCodDepto
				 * ,a.G_ConNombre,InsCodMun,b.G_ConNombre,InsCodLocal,
				 * NUCNOMBRE,InsCodigo,InsNombre
				 */
				l.setVigencia_actual(new Dao(cursor).getVigenciaNumerico());
				l.setPerfil(param[0][2]);
				l.setUsuarioJerar(param[0][1]);
				l.setUsuarioId(us[0][z++]);
				l.setTipo(us[0][z++]);
				l.setUsuario(us[0][z++] + " " + us[0][z++] + " " + us[0][z++]);
				l.setDepId(us[0][z++]);
				l.setDep(us[0][z++]);
				l.setMunId(us[0][z++]);
				l.setMun(us[0][z++]);
				l.setLocId(us[0][z++]);
				l.setLoc(us[0][z++]);
				l.setInstId(us[0][z++]);
				l.setInst(us[0][z++]);
				l = login4Params(l);
				l.setSedeId(us[0][z++]);
				l.setSede(us[0][z++]);
				l.setJornadaId(us[0][z++]);
				l.setJornada(us[0][z++]);
				l.setNivel(param[0][0]);
				// metodologia
				pst = cn.prepareStatement(consultas[24]);
				pst.setString(1, l.getInstId());
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setMetodologiaId(rs.getString(1));
					l.setMetodologia(rs.getString(2));
				}
				rs.close();
				pst.close();
				// calcular el perfil
				pst = cn.prepareStatement(consultas[27]);
				pst.setLong(1, Long.parseLong(l.getPerfil()));
				rs = pst.executeQuery();
				if (rs.next()) {
					l.setNomPerfil(rs.getString(1));
				}

				return l;
			}
		} catch (Exception e) {
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static Collection getCollection(ResultSet rs) throws SQLException {
		Collection list = null;
		int m = rs.getMetaData().getColumnCount();
		list = new ArrayList();
		Object[] o;
		int i = 0;
		while (rs.next()) {
			o = new Object[m];
			for (i = 1; i <= m; i++) {
				o[i - 1] = rs.getString(i);
			}
			list.add(o);
		}
		return list;
	}

	/**
	 * Funcinn: Obtener Usuario Institucinn<br>
	 * 
	 * @param String
	 *            a
	 * @param String
	 *            b
	 * @param String
	 *            perf
	 * @return Login
	 */
	static public Login login4Params(Login login) {
		// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** ");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("getParametrosInstitucion"));
			long vigenciaActual = getVigenciaInst(Long.parseLong(login
					.getInstId()));
			pst.setLong(1, vigenciaActual);
			pst.setLong(2, Long.parseLong(login.getInstId()));
			rs = pst.executeQuery();
			int f = 0;
			if (rs.next()) {
				f = 1;
				login.setLogNumPer(rs.getLong(f++));
				login.setLogTipoPer(rs.getLong(f++));
				login.setLogNomPerDef(rs.getString(f++));
				login.setLogNivelEval(rs.getLong(f++));
				login.setLogSubTitBol(rs.getString(f++));
				login.setVigencia_inst(vigenciaActual);
			}
			rs.close();
			pst.close();

		} catch (Exception e) {
			System.out.println("error:: " + e);
		} finally {
			try {
				// c.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return login;
	}

	static public long getVigencia() {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("Vigencia"));
			rs = pst.executeQuery();
			if (rs.next()) {
				long vig = rs.getLong(1);
				return vig;
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	static public long getVigenciaInst(long codInst) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("getVigenciaInst"));
			pst.setLong(1, codInst);
			rs = pst.executeQuery();
			if (rs.next()) {
				long vig = rs.getLong(1);
				return vig;
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return 0;
	}
	
	/**
	 * Dado el Dane de un instituto retorna el id en apoyo escolar
	 * @param codInst
	 * @return
	 */
	static public String getInstCodFromDane(long codInst) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("instDane"));
			pst.setLong(1, codInst);
			rs = pst.executeQuery();
			if (rs.next()) {
				String vig = rs.getString(1);
				return vig;
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

}