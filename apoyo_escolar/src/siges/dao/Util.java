package siges.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.exceptions.InternalErrorException;
import siges.personal.beans.FiltroPersonal;

/**
 * Nombre: Util Descripcion: Realiza operaciones en la BD Funciones de la
 * pagina: Realiza la logica de negocio Entidades afectadas: todas las tablas de
 * la BD Fecha de modificacinn: Feb-05
 * 
 * @author Latined
 * @version $v 1.3 $
 */

public class Util {
	private Cursor cursor;

	public String sentencia;

	public String mensaje;

	public String buscar;

	private java.text.SimpleDateFormat sdf;

	private java.sql.Timestamp f2;

	private ResourceBundle rb, rb2;

	public int[] resultado;

	/**
	 * Constructor de la clase
	 */
	public Util() {
		sentencia = null;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		resultado = new int[3];
		rb = ResourceBundle.getBundle("operaciones");
		rb2 = ResourceBundle.getBundle("preparedstatements");
	}

	/**
	 * Constructor de la clase
	 */
	public Util(Cursor cur) {
		this.cursor = cur;
		sentencia = null;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("operaciones");
		rb2 = ResourceBundle.getBundle("preparedstatements");
	}

	/**
	 * Funcinn: Cerrar conecciones<br>
	 */
	public void cerrar() {
		try {
			if (cursor != null)
				cursor.cerrar();
		} catch (Exception e) {
		}
	}

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param String
	 *            consulta
	 * @return Collection
	 */
	public Collection getFiltro(String consulta) throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consulta);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			Collection list = new ArrayList();
			Object[] o;
			int i;
			while (rs.next()) {
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
	}

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param String
	 *            [] consulta
	 * @return Collection
	 */
	public Collection[] getFiltro(String[] consulta)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			int m = 0, i = 0;
			Collection[] list = new ArrayList[consulta.length];
			Object[] o;
			for (int j = 0; j < consulta.length; j++) {
				list[j] = new ArrayList();
				pst = cn.prepareStatement(consulta[j]);
				rs = pst.executeQuery();
				m = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					o = new Object[m];
					for (i = 1; i <= m; i++)
						o[i - 1] = rs.getString(i);
					list[j].add(o);
				}
			}
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
	}

	/**
	 * Funcinn: Ejecuta un consulta con parametros en la base de datos y
	 * devuelte el resultado<br>
	 * 
	 * @param String
	 *            consulta
	 * @param Collection
	 *            lista
	 * @return String[]
	 */
	public String[] getFiltroArray(String consulta, Collection lista)
			throws InternalErrorException {
		Collection a = getFiltro(consulta, lista);
		if (!a.isEmpty()) {
			String[] m = new String[a.size()];
			int i = 0;
			Iterator iterator = a.iterator();
			while (iterator.hasNext())
				m[i++] = (String) ((Object[]) iterator.next())[0];
			return m;
		}
		return null;
	}

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param String
	 *            consulta
	 * @return String[]
	 */
	public String[] getFiltroArray(String consulta)
			throws InternalErrorException {
		Collection a = getFiltro(consulta);
		if (!a.isEmpty()) {
			String[] m = new String[a.size()];
			int i = 0;
			Iterator iterator = a.iterator();
			while (iterator.hasNext())
				m[i++] = (String) ((Object[]) iterator.next())[0];
			return m;
		}
		return null;
	}

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado
	 * como una matriz<br>
	 * 
	 * @param String
	 *            consulta
	 * @param Collection
	 *            lista
	 * @return String[][]
	 */
	public String[][] getFiltroMatriz(String consulta, Collection lista)
			throws InternalErrorException {
		Collection a = getFiltro(consulta, lista);
		Object[] o;
		if (!a.isEmpty()) {
			int i = 0, j = -1;
			Iterator iterator = a.iterator();
			o = ((Object[]) iterator.next());
			// System.out.println(o.length);
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
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado
	 * como una matriz<br>
	 * 
	 * @param String
	 *            consulta
	 * @return String[][]
	 */
	public String[][] getFiltroMatriz(String consulta)
			throws InternalErrorException {
		Collection a = getFiltro(consulta);
		Object[] o;
		if (!a.isEmpty()) {
			int i = 0, j = -1;
			Iterator iterator = a.iterator();
			o = ((Object[]) iterator.next());
			// System.out.println(o.length);
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
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado
	 * como una matriz<br>
	 * 
	 * @param Collection
	 *            lista
	 * @return String[][]
	 */
	public String[][] getFiltroMatriz(Collection a)
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

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param Collection
	 *            lista
	 * @param int[] zz
	 * @return String[]
	 */
	public String[] getFiltroArray(Collection a, int[] zz)
			throws InternalErrorException {
		if (!a.isEmpty()) {
			String[] m = new String[a.size()];
			String l = "";
			int i = 0;
			Object[] o;
			Iterator iterator = a.iterator();
			while (iterator.hasNext()) {
				l = "";
				o = (Object[]) iterator.next();
				for (int j = 0; j < zz.length; j++)
					l += (o[zz[j]] != null ? o[zz[j]] : "") + "|";
				m[i++] = l.substring(0, l.lastIndexOf("|"));
			}
			return m;
		}
		return null;
	}

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param String
	 *            consulta
	 * @param Collection
	 *            lista
	 * @return Collection
	 */
	public Collection getFiltro(String consulta, Collection lista)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consulta);
			pst.clearParameters();
			Iterator iterator = lista.iterator();
			int posicion = 1;
			while (iterator.hasNext()) {
				Object[] fila = (Object[]) iterator.next();
				switch (((Integer) fila[0]).intValue()) {
				case java.sql.Types.VARCHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.INTEGER:
					pst.setInt(posicion++,
							Integer.parseInt(((String) fila[1]).trim()));
					break;
				case java.sql.Types.DATE:
					pst.setDate(posicion++,
							new java.sql.Date(sdf.parse((String) fila[1])
									.getTime()));
					break;
				case java.sql.Types.NULL:
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
					break;
				case java.sql.Types.DOUBLE:
					pst.setLong(posicion++, (((Long) fila[1])).longValue());
					break;
				case java.sql.Types.CHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.BIGINT:
					pst.setLong(posicion++,
							Long.parseLong(((String) fila[1]).trim()));
					break;
				}
			}
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			Collection list = new ArrayList();
			Object[] o;
			int i = 0;
			int f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			// System.out.println("LISTA USUARIOS ASIGNAR LISTA: "+list.size());
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} catch (java.text.ParseException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
	}
	
	public Collection getFiltroMesaAyuda(String consulta, Collection lista)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consulta);
			pst.clearParameters();
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			Collection list = new ArrayList();
			Object[] o;
			int i = 0;
			int f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			// System.out.println("LISTA USUARIOS ASIGNAR LISTA: "+list.size());
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
	}


	public Collection[] getfiltroasdoc(String ins, String vig, String iddoc) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection[] list = null;
		Object[] o;
		try {
			cn = cursor.getConnection();
			list = new Collection[3];
			int pos = 1;
			// areas
			pst = cn.prepareStatement("select distinct arecodigo,arenombre,arecodmetod from rot_doc_asig_grado,asignatura,area where arecodigo=asicodarea and arecodinst=asicodinst and asivigencia=arevigencia and rotdagasignatura=asicodigo and rotdagdocente=? and rotdagvigencia=asivigencia and asicodinst=? and asivigencia=? order by 1");
			pst.setString(pos++, iddoc);
			pst.setString(pos++, ins);
			pst.setString(pos++, vig);
			rs = pst.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			pst.close();

			pos = 1;
			// asignaturas
			pst = cn.prepareStatement("select distinct asicodarea,asicodigo,asinombre,asicodmetod from rot_doc_asig_grado,asignatura where rotdagasignatura=asicodigo and rotdagdocente=? and rotdagvigencia=asivigencia and asicodinst=? and asivigencia=? order by 1");
			pst.setString(pos++, iddoc);
			pst.setString(pos++, ins);
			pst.setString(pos++, vig);
			rs = pst.executeQuery();
			list[1] = getCollection(rs);
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

	public Collection[] getfiltroas(String ins, String vig) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection[] list = null;
		Object[] o;
		try {
			cn = cursor.getConnection();
			list = new Collection[3];
			int pos = 1;
			// areas
			pst = cn.prepareStatement("select arecodigo,arenombre,arecodmetod from area where arecodinst=? and arevigencia=? order by 1");
			pst.setString(pos++, ins);
			pst.setString(pos++, vig);
			rs = pst.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			pst.close();

			pos = 1;
			// asignaturas
			pst = cn.prepareStatement("select asicodarea,asicodigo,asinombre,asicodmetod from asignatura where asicodinst=? and asivigencia=? order by 1");
			pst.setString(pos++, ins);
			pst.setString(pos++, vig);
			rs = pst.executeQuery();
			list[1] = getCollection(rs);
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

	public static Collection getCollection(ResultSet rs) throws SQLException {
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
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param String
	 *            consulta
	 * @param Collection
	 *            lista
	 * @return Collection
	 */
	public Collection[] getFiltro(String[] consulta, Collection[] lista)
			throws InternalErrorException {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = cursor.getConnection();
			Collection[] list = new ArrayList[consulta.length];
			for (int zz = 0; zz < consulta.length; zz++) {
				pst = conn.prepareStatement(consulta[zz]);
				pst.clearParameters();
				Iterator iterator = lista[zz].iterator();
				int posicion = 1;
				while (iterator.hasNext()) {
					Object[] fila = (Object[]) iterator.next();
					switch (((Integer) fila[0]).intValue()) {
					case java.sql.Types.VARCHAR:
						pst.setString(posicion++, ((String) fila[1]).trim());
						break;
					case java.sql.Types.INTEGER:
						pst.setInt(posicion++,
								Integer.parseInt(((String) fila[1]).trim()));
						break;
					case java.sql.Types.DATE:
						pst.setDate(posicion++,
								new java.sql.Date(sdf.parse((String) fila[1])
										.getTime()));
						break;
					case java.sql.Types.NULL:
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
						break;
					case java.sql.Types.DOUBLE:
						pst.setLong(posicion++, (((Long) fila[1])).longValue());
						break;
					case java.sql.Types.CHAR:
						pst.setString(posicion++, ((String) fila[1]).trim());
						break;
					case java.sql.Types.BIGINT:
						pst.setLong(posicion++,
								Long.parseLong(((String) fila[1]).trim()));
						break;
					}
				}
				rs = pst.executeQuery();
				int m = rs.getMetaData().getColumnCount();
				list[zz] = new ArrayList();
				Object[] o;
				int i = 0;
				int f = 0;
				while (rs.next()) {
					f++;
					o = new Object[m];
					for (i = 1; i <= m; i++)
						o[i - 1] = rs.getString(i);
					list[zz].add(o);
				}
				rs.close();
				pst.close();
			}
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} catch (java.text.ParseException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(conn);
			cursor.cerrar();
		}
	}

	public int[] getResultado() {
		return resultado;
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		mensaje += s;
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param filtro
	 * @return
	 * @throws InternalErrorException
	 */
	public Collection getBuscarPersonal(FiltroPersonal fil)
			throws InternalErrorException {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String numDoc = (fil.getId().trim().length() == 0 ? "-99" : fil.getId());
		String codPerf = fil.getTipoPersona();
		String codInst = fil.getInsttitucion();
		String codSede = fil.getSede();
		String codJorn = fil.getJornada();
		String nom1 = (fil.getNombre1().trim().length() == 0 ? "-99" : fil
				.getNombre1());
		String nom2 = (fil.getNombre2().trim().length() == 0 ? "-99" : fil
				.getNombre2());
		String apell1 = (fil.getApellido1().trim().length() == 0 ? "-99" : fil
				.getApellido1());
		String apell2 = (fil.getApellido2().trim().length() == 0 ? "-99" : fil
				.getApellido2());

		String sql = " select distinct   PerNumDocum,"
				+ "                    concat(PerNombre1,concat(' ',PerNombre2)),concat(PerApellido1,concat(' ',PerApellido2)),"
				+ "                    PERTIPDOCUM,"
				+ "                    PerNombre1,"
				+ "                    PerNombre2,"
				+ "                    PerApellido1,"
				+ "                    PerApellido2"
				+ "     from personal per," + "          usuario usu "
				+ "     where  pernumdocum = usu.USUPERNUMDOCUM||''  "
				+ "        and  ( '-99' = '"
				+ numDoc
				+ "' or per.pernumdocum like '%'||'"
				+ numDoc
				+ "'||'%')"
				+ "        and  ((0 = "
				+ codPerf
				+ " and usu.USUPERFCODIGO in ('422', '421', '423', '410')   )  or  usu.USUPERFCODIGO = '"
				+ codPerf
				+ "')"
				+ "        and usu.USUCODJERAR in ("
				+ "             select g_jercodigo "
				+ "             from g_jerarquia"
				+ "             where g_jerinst = "
				+ codInst
				+ ""
				+ "              and  (-99="
				+ codSede
				+ " or g_jersede ="
				+ codSede
				+ ")"
				+ "              and  (-99="
				+ codJorn
				+ " or g_jerjorn ="
				+ codJorn
				+ ")"
				+ "        )"
				+ "        and ('-99'= '"
				+ nom1
				+ "' or UPPER(PerNombre1) like   '%'||UPPER('"
				+ nom1
				+ "')||'%' )"
				+ "        and ('-99'= '"
				+ nom2
				+ "' or UPPER(PerNombre2) like   '%'||UPPER('"
				+ nom2
				+ "')||'%' )"
				+ "        and ('-99'= '"
				+ apell1
				+ "' or UPPER(PerApellido1) like   '%'||UPPER('"
				+ apell1
				+ "')||'%' )"
				+ "        and ('-99'='"
				+ apell2
				+ "' or UPPER(PerApellido2) like   '%'||UPPER('"
				+ apell2
				+ "')||'%' )" + "        order by " + fil.getOrden();

		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			Collection list = new ArrayList();
			Object[] o;
			int i;
			while (rs.next()) {
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
	}

	public List getListaVigenciaInst(long inst) throws Exception {
		// System.out.println("getListaVigenciaInst ");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ItemVO itemVO = null;
		List listaVigencia = new ArrayList();
		int i = 0;
		int vigenciaIni = 0;
		int vigenciaFin = 0;
		try {
			if (listaVigencia.size() > 0)
				return listaVigencia;
			cn = cursor.getConnection();
			pst = cn.prepareStatement("SELECT TO_NUMBER(VALOR), INSVIGENCIA FROM PARAMETRO, INSTITUCION where TIPO=3 and NOMBRE='VIGENCIAINICIAL' AND INSCODIGO = "
					+ inst);
			rs = pst.executeQuery();
			if (rs.next()) {
				vigenciaIni = rs.getInt(1);
				vigenciaFin = rs.getInt(2);
			}
			for (i = vigenciaIni; i <= (vigenciaFin); i++) {
				itemVO = new ItemVO();
				itemVO.setCodigo(i);
				itemVO.setNombre(String.valueOf(i));
				listaVigencia.add(itemVO);
			}
			// System.out.println("listaVigencia " + listaVigencia.size() );
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle);
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return listaVigencia;
	}
}
