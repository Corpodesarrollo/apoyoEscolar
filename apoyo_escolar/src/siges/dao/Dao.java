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

import siges.common.vo.FiltroCommonVO;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.common.vo.TipoEvalVO;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.repCarnes.vo.ParamsVO;
import siges.plantilla.beans.NivelEvalVO;
import siges.util.Recursos;

/**
 * @author USUARIO
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class Dao {
	private String mensaje;

	public Cursor cursor;

	private java.text.SimpleDateFormat sdf;

	public ResourceBundle rb;

	private static List listaVigencia = new ArrayList();

	public Dao(Cursor c) {
		cursor = c;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("siges.dao.bundle.dao");
	}

	public String getErrorCode(SQLException sqle) {
		String sms = "";
		int code = sqle.getErrorCode();
		switch (code) {

		default:
			return (sqle.getMessage().replace('\'', '`').replace('"', 'n'));
		}
	}

	public void pintar(String[][] a) {
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(" _ " + a[i][j]);
			}
			System.out.println();
		}
	}

	public Collection getCollection(ResultSet rs) throws SQLException {
		Collection list = null;
		// System.out.println("getCollection ");
		int m = rs.getMetaData().getColumnCount();
		list = new ArrayList();
		Object[] o;
		int i = 0;
		int ff = 0;
		while (rs.next()) {
			ff++;
			o = new Object[m];
			for (i = 1; i <= m; i++) {
				// System.out.println("rs.getString(i) " + rs.getString(i));
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
				int z = 1;
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
					// System.out.println("o[zz[j] " + o[zz[j] ] );
					l += (o[zz[j]] != null ? o[zz[j]] : "") + "|";
				m[i++] = l.substring(0, l.lastIndexOf("|"));
				// System.out.println("m[i++] " + m[i-1]);
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
					pst.setLong(posicion++,Long.parseLong(((String) fila[1]).trim()));
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
	
	public Collection getAsignaturasMetodologia(String consulta,String codigoInstitucion, int vigencia, int metodologia)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consulta);
			pst.clearParameters();
			int posicion = 1;
			
			pst.setLong(posicion++, Long.parseLong(codigoInstitucion));
			pst.setInt(posicion++, vigencia);
			pst.setInt(posicion++, metodologia);
			
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

	public Collection[] getEstadoPromo(String ins)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection[] list = new Collection[1];
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("SELECT DISTINCT CIERRE_VIGENCIA,ESTADO_PROMOCION,codinst from param_promocion where codinst=?");
			pst.setString(1, ins);
			rs = pst.executeQuery();
			list[0] = getCollection(rs);
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

	public String getVigencia() throws InternalErrorException {
		int[] zz = { 0 };
		return getFiltroArray(Recursos.recurso[Recursos.VIGENCIA], zz)[0];
	}

	public long getVigenciaNumerico() throws InternalErrorException {
		// System.out.println("getVigenciaNumerico ");
		// int[]zz={0};
		// return
		// Long.parseLong(getFiltroArray(Recursos.recurso[Recursos.VIGENCIA],zz)[0]);
		return Recursos.vigencia;
	}

	public long getPeriodoNumerico() throws InternalErrorException {
		// int[]zz={0};
		// return
		// Long.parseLong(getFiltroArray(Recursos.recurso[Recursos.VIGENCIA],zz)[1]);
		return Recursos.periodo;
	}

	public long getVigenciaNumericoPOA() throws InternalErrorException {
		// int[]zz={0};
		// return
		// Long.parseLong(getFiltroArray(Recursos.recurso[Recursos.VIGENCIA],zz)[0]);
		Recursos.CargarPOA();
		return Recursos.vigenciaPOA;
	}

	public long getPeriodoNumericoPOA() throws InternalErrorException {
		// int[]zz={0};
		// return
		// Long.parseLong(getFiltroArray(Recursos.recurso[Recursos.VIGENCIA],zz)[1]);

		return Recursos.periodoPOA;
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
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		mensaje = s;
	}

	/**
	 * Funcinn: Cerrar conecciones <br>
	 */
	public void cerrar() {
		try {
			if (cursor != null)
				cursor.cerrar();
		} catch (Exception e) {
		}
	}

	public void cleanMensaje() {
		mensaje = "";
	}

	public boolean validar(String act, String rec, int tipo) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString(rec));
			ps.clearParameters();
			posicion = 1;

			if (tipo == Params.ENTERO) {
				if (act.equals(""))
					ps.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					ps.setInt(posicion++, Integer.parseInt(act));
			} else if (tipo == Params.CADENA) {
				if (act.equals(""))
					ps.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					ps.setString(posicion++, (act));
			}

			rs = ps.executeQuery();
			if (rs.next())
				return true;
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
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public List getListaVigencia() {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ItemVO itemVO = null;
		int i = 0;
		int vigenciaIni = 0;
		int vigenciaFin = 0;
		try {
			// if(listaVigencia.size()>0)return listaVigencia;
			cn = cursor.getConnection();
			pst = cn.prepareStatement("SELECT TO_NUMBER(VALOR),to_number(to_char(sysdate,'yyyy')) FROM PARAMETRO where TIPO=3 and NOMBRE='VIGENCIAINICIAL'");
			rs = pst.executeQuery();
			if(listaVigencia != null && listaVigencia.size()>0){
				listaVigencia.clear();
			}
			if (rs.next()) {
				vigenciaIni = rs.getInt(1);
				vigenciaFin = rs.getInt(2);
			}
			for (i = vigenciaIni; i <= (vigenciaFin + 1); i++) {
				itemVO = new ItemVO();
				itemVO.setCodigo(i);
				itemVO.setNombre(String.valueOf(i));
				listaVigencia.add(itemVO);
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
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaVigencia;
	}

	/**
	 * @function Obteniene el ano de vigencia de colegio que recibe como
	 *           parametro
	 * @param codInst
	 * @return
	 * @throws Exception
	 */
	public int getVigenciaInst(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int anioVigencia = 0;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getVigenciaInst"));
			pst.setLong(posicion++, codInst);
			rs = pst.executeQuery();

			if (rs.next()) {
				posicion = 1;
				anioVigencia = rs.getInt(posicion++);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}

		return anioVigencia;
	}

	/**
	 * Metodo que retorna el listado de vigencias segun la vigencia de la
	 * institucinn
	 * 
	 * @param inst
	 *            codigo de la institucinn
	 * @return Retorn la lista
	 * @throws Exception
	 */
	public List getListaVigenciaInst(long inst) throws Exception {
		// System.out.println("getListaVigenciaInst ");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ItemVO itemVO = null;
		int i = 0;
		int vigenciaIni = 0;
		int vigenciaFin = 0;
		try {
			// if(listaVigencia.size()>0)return listaVigencia;
			cn = cursor.getConnection();
			pst = cn.prepareStatement("SELECT TO_NUMBER(VALOR), INSVIGENCIA FROM PARAMETRO, INSTITUCION where TIPO=3 and NOMBRE='VIGENCIAINICIAL' AND INSCODIGO = "
					+ inst);
			rs = pst.executeQuery();
			if (rs.next()) {
				vigenciaIni = rs.getInt(1);
				vigenciaFin = rs.getInt(2);
			}
			listaVigencia.clear();
			for (i = vigenciaIni; i <= (vigenciaFin); i++) {
				itemVO = new ItemVO();
				// System.out.println("i " + i);
				itemVO.setCodigo(i);
				itemVO.setNombre(String.valueOf(i));
				listaVigencia.add(itemVO);
			}
			// System.out.println("listaVigencia " + listaVigencia.size() );
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
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaVigencia;
	}

	/**
	 * @return
	 * @throws InternalErrorException
	 */
	public List getListaLocal() throws Exception {
		// System.out.println("getListaLocal");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {

			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("ajax.getListaLocalidad"));
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size() );
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);

		}
		return list;
	}

	/**
	 * @return
	 * @throws InternalErrorException
	 */
	public List getListaInst(long codLoc) throws Exception {
		// System.out.println("filtro carne getListaColegio");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {

			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("ajax.getListaColegio"));
			pst.setLong(posicion, codLoc);
			// System.out.println("codLoc " + codLoc);
			// System.out.println();
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size() );
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);

		}
		return list;
	}

	// AJAX

	/**
	 * @param codInst
	 * @param codSede
	 * @return
	 * @throws Exception
	 */
	public List getListaJornd(long codInst, long codSede) throws Exception {
		// System.out.println("getJornada ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ajax.getJornada"));
			st.setLong(posicion++, codInst);
			// System.out.println("SEDES ---------------");
			// System.out.println("codInst " + codInst);
			st.setLong(posicion++, codSede);
			st.setLong(posicion++, codSede);
			// System.out.println("codSede " + codSede);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size() );
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
	 * @param codInst
	 * @return
	 * @throws Exception
	 */
	public List getListaMetod(long codInst) throws Exception {
		// System.out.println("getListaMetod " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ajax.getMetodologia"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				if (itemVO.getNombre().length() > 30) {
					itemVO.setNombre(itemVO.getNombre().substring(0, 30));
				}
				list.add(itemVO);
			}

			// System.out.println("list.size() " + list.size() );
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public List getListaSede(long codInst) throws Exception {
		// System.out.println("getSede -");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ajax.getSede"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}

			// System.out.println("list " + list.size() );
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public List getListaGrado(FiltroCommonVO filtroVO) throws Exception {
		// System.out.println("getListaGrado ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ajax.getGrado"));

			st.setInt(posicion++, filtroVO.getFilinst());
			st.setInt(posicion++, filtroVO.getFilsede());
			st.setInt(posicion++, filtroVO.getFiljornd());
			st.setInt(posicion++, filtroVO.getFilmetod());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size() );

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
	 * @function: Metndo que obtiene el listado de grupos
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public List getListaGrupo(FiltroCommonVO filtroVO) throws Exception {
		// System.out.println("getListaGrupo " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ajax.getGrupo"));

			st.setInt(posicion++, filtroVO.getFilinst());
			st.setInt(posicion++, filtroVO.getFilsede());
			st.setInt(posicion++, filtroVO.getFiljornd());
			st.setInt(posicion++, filtroVO.getFilmetod());
			st.setInt(posicion++, filtroVO.getFilgrado());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size() );

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
	 * @function: Metndo que obtiene el listado de grupos
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public List getListaArea(FiltroCommonVO filtroVO) throws Exception {
		// System.out.println("getListaArea " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			int vigencia = getVigenciaInst(filtroVO.getFilinst());
			// System.out.println("vigencia " + vigencia);
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ajax.getArea"));
			st.setInt(posicion++, vigencia);
			st.setInt(posicion++, filtroVO.getFilinst());
			st.setInt(posicion++, filtroVO.getFilmetod());
			st.setInt(posicion++, filtroVO.getFilgrado());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}
			// System.out.println("list " + list.size());

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
	 * @param codInst
	 * @return
	 * @throws Exception
	 */
	public List getSede(long codInst) throws Exception {
		// System.out.println("getSede -");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ajax.getSede"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}

			// System.out.println("list " + list.size() );
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
	 * @param cn
	 * @param filtroReporte
	 * @return
	 * @throws Exception
	 */
	public int getTipoEval_codigoNivelEval(Connection cn,
			FiltroCommonVO filtroReporte) throws Exception {

		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		int codigoNivelEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();

		try {
			vigencia = getVigenciaInst(filtroReporte.getFilinst());

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, filtroReporte.getFilinst());
			rs = st.executeQuery();

			if (rs.next()) {
				codigoNivelEval = rs.getInt(1);
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return codigoNivelEval;
	}

	/**
	 * Metodo que consulta los datos de nivel de evaluacion: - Tipo de
	 * evaluacion segun el filtro - Maximo y Minnmo si es escala nnmerica -
	 * Bandera que indica si los rangos estan completos - Tipo de evaluacinn
	 * para preescolar
	 * 
	 * @param cn
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public TipoEvalVO getTipoEval(Connection cn, FiltroCommonVO filtroVO)
			throws Exception {

		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		TipoEvalVO tipoEvalVO = new TipoEvalVO();
		try {

			vigencia = getVigenciaInst(filtroVO.getFilinst());
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, filtroVO.getFilinst());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
			st.setLong(i++, codigoNivelEval);
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));
			}
			rs.close();
			st.close();

			// TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql = rb.getString("getTipoEval.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getTipoEval.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getTipoEval.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getTipoEval.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + " " + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");

			System.out.println("sql " + sql);

			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, vigencia);
			st.setLong(i++, filtroVO.getFilinst());

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, filtroVO.getFilsede());
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, filtroVO.getFiljornd());
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, filtroVO.getFilmetod());
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado((int) filtroVO.getFilgrado());
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1) {
				st.setLong(i++, filtroVO.getFilgrado());
			}

			rs = st.executeQuery();
			i = 1;

			if (rs.next()) {
				i = 1;
				tipoEvalVO.setCod_tipo_eval(rs.getLong(i++));
				tipoEvalVO.setEval_min(rs.getDouble(i++));
				tipoEvalVO.setEval_max(rs.getDouble(i++));
				tipoEvalVO.setEval_rangos_completos(rs.getInt(i++));
				tipoEvalVO.setCod_modo_eval(rs.getInt(i++));
				tipoEvalVO.setCod_tipo_eval_pree(rs.getLong(i++));
				tipoEvalVO.setEval_min_aprob(rs.getInt(i++));
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return tipoEvalVO;
	}
	
	/**
	 * Metodo que consulta los datos de nivel de evaluacion para registro de notas por vigencia: - Tipo de
	 * evaluacion segun el filtro - Maximo y Minnmo si es escala nnmerica -
	 * Bandera que indica si los rangos estan completos - Tipo de evaluacinn
	 * para preescolar
	 * 
	 * @param cn
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public TipoEvalVO getTipoEvalRegistroNotas(Connection cn, FiltroCommonVO filtroVO)
			throws Exception {

		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		TipoEvalVO tipoEvalVO = new TipoEvalVO();
		try {

			//vigencia = getVigenciaInst(filtroVO.getFilinst());
			vigencia = filtroVO.getFilvigencia();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, filtroVO.getFilvigencia());
			st.setLong(i++, filtroVO.getFilinst());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
			st.setLong(i++, codigoNivelEval);
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));
			}
			rs.close();
			st.close();

			// TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql = rb.getString("getTipoEval.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getTipoEval.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getTipoEval.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getTipoEval.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + " " + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");

			System.out.println("sql " + sql);

			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, vigencia);
			st.setLong(i++, filtroVO.getFilinst());

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, filtroVO.getFilsede());
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, filtroVO.getFiljornd());
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, filtroVO.getFilmetod());
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado((int) filtroVO.getFilgrado());
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1) {
				st.setLong(i++, filtroVO.getFilgrado());
			}

			rs = st.executeQuery();
			i = 1;

			if (rs.next()) {
				i = 1;
				tipoEvalVO.setCod_tipo_eval(rs.getLong(i++));
				tipoEvalVO.setEval_min(rs.getDouble(i++));
				tipoEvalVO.setEval_max(rs.getDouble(i++));
				tipoEvalVO.setEval_rangos_completos(rs.getInt(i++));
				tipoEvalVO.setCod_modo_eval(rs.getInt(i++));
				tipoEvalVO.setCod_tipo_eval_pree(rs.getLong(i++));
				tipoEvalVO.setEval_min_aprob(rs.getInt(i++));
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return tipoEvalVO;
	}

	/**
	 * @param grado
	 * @return
	 * @throws Exception
	 */
	public int getNivelGrado(int grado) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getNivelGrado"));
			st.setInt(i++, grado);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected String getResolInst(Connection cn, long inst) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		long codigo = 0;
		String resol = "";
		boolean cont = true;
		try {
			st = cn.prepareStatement(rb.getString("boletinGetResolInst"));
			st.setLong(1, inst);
			rs = st.executeQuery();
			if (rs.next())
				resol = rs.getString(1);
		} catch (SQLException sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			cont = false;
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return resol;
	}

	public int getInstSector(long codInst) throws Exception {
		System.out.println("getInstSector ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getInstSector"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				return rs.getInt(posicion++);
			}

			System.out.println("list.size() " + list.size());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public List getListaTipoColegio() throws Exception {
		List tipoCol = new ArrayList();
		ItemVO itemReporte;
		itemReporte = new ItemVO();
		itemReporte.setCodigo(ParamsVO.TIPO_COL_OFICIAL);
		itemReporte.setNombre(ParamsVO.TIPO_COL_OFICIAL_);
		tipoCol.add(itemReporte);
		itemReporte = new ItemVO();
		itemReporte.setCodigo(ParamsVO.TIPO_COL_CONCESION);
		itemReporte.setNombre(ParamsVO.TIPO_COL_CONCESION_);
		tipoCol.add(itemReporte);
		itemReporte = new ItemVO();
		itemReporte.setCodigo(ParamsVO.TIPO_COL_CONVENIO);
		itemReporte.setNombre(ParamsVO.TIPO_COL_CONVENIO_);
		tipoCol.add(itemReporte);
		return tipoCol;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getTiposDoc() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getTiposDoc"));
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				ItemVO n = new ItemVO();
				n.setCodigo(rs.getInt(posicion++));
				n.setNombre(rs.getString(posicion++));
				list.add(n);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		// System.out.println("list " + list.size() );
		return list;
	}

	/**
	 * @param metod
	 * @param grado
	 * @param vigencia
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public List getEscalaConceptual(FiltroCommonVO filtroVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		List l = new ArrayList();
		ItemVO item = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, filtroVO.getFilvigencia());
			st.setLong(i++, filtroVO.getFilinst());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
				System.out.println("codigoNivelEval  " + codigoNivelEval);
			}
			rs.close();
			st.close();
			i = 1;
			// traer parametros de nivel evaluacion
			st = cn.prepareStatement(rb.getString("getParamsNivelEval"));
			st.setLong(i++, codigoNivelEval);
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				nivelEval.setCod_nivel_eval(rs.getLong(i++));
				nivelEval.setEval_sede(rs.getInt(i++));
				nivelEval.setEval_jornada(rs.getInt(i++));
				nivelEval.setEval_metod(rs.getInt(i++));
				nivelEval.setEval_nivel(rs.getInt(i++));
				nivelEval.setEval_grado(rs.getInt(i++));
			}
			rs.close();
			st.close();

			// TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
			String sql = rb.getString("getEscalaConceptual.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.grado");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, filtroVO.getFilvigencia());
			st.setLong(i++, filtroVO.getFilinst());
			st.setLong(i++, codigoNivelEval);

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, filtroVO.getFilsede());
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, filtroVO.getFiljornd());
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, filtroVO.getFilmetod());
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(filtroVO.getFilgrado());
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, filtroVO.getFilgrado());

			rs = st.executeQuery();

			while (rs.next()) {
				i = 1;
				item = new ItemVO(rs.getInt(i++), rs.getString(i++));
				l.add(item);
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
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
}
