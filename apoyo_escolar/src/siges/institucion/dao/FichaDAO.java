package siges.institucion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.FiltroCommonVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.institucion.beans.FichaVO;
import siges.perfil.beans.Perfil;

/**
 * siges.institucion.dao<br>
 * Funcinn: <br>
 */

public class FichaDAO extends Dao {
	private Cursor cursor;
	private ResourceBundle rb;

	public FichaDAO(Cursor c) {
		super(c);
		cursor = c;
		// siges.institucion.bundle.institucion
		rb = ResourceBundle.getBundle("siges.institucion.bundle.institucion");
	}

	public FichaVO getFicha1(FichaVO f) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			long inst = Long.parseLong(f.getFichaInstitucion());
			cn = cursor.getConnection();
			// calcular datos basicos
			pst = cn.prepareStatement(rb.getString("lista.ficha1"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			if (r.next()) {
				int i = 1;
				f.setFichaEnfasis(r.getString(i++));
				f.setFichaModalidad(r.getString(i++));
				f.setFichaResolucion(r.getString(i++));
				f.setFichaFechaResolucion(r.getString(i++));
				f.setFichaEstado("1");
			}
			r.close();
			pst.close();
			posicion = 1;
			// calcular todas las especialidades
			pst = cn.prepareStatement(rb.getString("lista.ficha2"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			if (r.next()) {
				f.setFichaEspecialidad(r.getString(1));
			}
			r.close();
			pst.close();
			posicion = 1;
			// poner total docentes
			pst = cn.prepareStatement(rb.getString("lista.ficha3"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			if (r.next()) {
				f.setFichaTotDoc(r.getString(1));
			}
			r.close();
			pst.close();
			posicion = 1;
			// poner total ninos
			pst = cn.prepareStatement(rb.getString("lista.ficha4"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			if (r.next()) {
				f.setFichaTotEst(r.getString(1));
			}
			r.close();
			pst.close();
			posicion = 1;
			// NUMERO DE SEDE
			pst = cn.prepareStatement(rb.getString("lista.ficha8"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			if (r.next()) {
				f.setFichaTotSedes(r.getString(1));
			}
			r.close();
			pst.close();
			posicion = 1;
			// NUMERO DE JORNADAS
			pst = cn.prepareStatement(rb.getString("lista.ficha9"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			if (r.next()) {
				f.setFichaTotJornadas(r.getString(1));
			}
			r.close();
			pst.close();
			// lista de tipos de programa
			List l = new ArrayList();
			pst = cn.prepareStatement(rb.getString("lista.fichaPrograma"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			while (r.next()) {
				l.add(r.getString(1));
			}
			r.close();
			if (l.size() > 0) {
				int[] a = new int[l.size()];
				for (int i = 0; i < l.size(); i++) {
					a[i] = Integer.parseInt((String) l.get(i));
				}
				f.setFichaPrograma(a);
			}

		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error SQL intentando asignar Ficha Tecnica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar Ficha Tncnica. Posible problema: "
					+ sqle);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	public FichaVO getFicha2(FichaVO f) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			long inst = Long.parseLong(f.getFichaInstitucion());
			cn = cursor.getConnection();
			// poner docentes por genero
			/*
			 * pst=cn.prepareStatement(rb.getString("lista.ficha5"));
			 * pst.clearParameters(); pst.setLong(posicion++,inst); r =
			 * pst.executeQuery(); f.setFichaDocXGen(getCollection(r));
			 * r.close(); pst.close(); posicion=1; //poner docentes por edad
			 * pst=cn.prepareStatement(rb.getString("lista.ficha6"));
			 * pst.clearParameters(); pst.setLong(posicion++,inst); r =
			 * pst.executeQuery(); f.setFichaDocXEdad(getCollection(r));
			 * r.close(); pst.close();
			 */
			posicion = 1;
			//
			// poner docentes por escalafon-genero
			pst = cn.prepareStatement(rb.getString("lista.ficha7a1"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			f.setFichaDocXEscalafon(getCollection(r));
			r.close();
			pst.close();
			posicion = 1;
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error SQL intentando asignar Ficha Tecnica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar Ficha Tncnica. Posible problema: "
					+ sqle);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	/**
	 * @param f
	 * @return
	 */
	public FichaVO getFicha3(FichaVO f) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			long inst = Long.parseLong(f.getFichaInstitucion());
			cn = cursor.getConnection();

			/*
			 * pst=cn.prepareStatement(rb.getString("lista.ficha7"));
			 * pst.clearParameters(); pst.setLong(posicion++,inst); r =
			 * pst.executeQuery(); f.setFichaDocXEscalafon(getCollection(r));
			 * r.close(); pst.close();
			 */

			// NUMERO DE NInOS POR GRADO X GENERO
			/*
			 * pst=cn.prepareStatement(rb.getString("lista.ficha10"));
			 * pst.clearParameters(); pst.setLong(posicion++,inst); r =
			 * pst.executeQuery(); f.setFichaEstXGrado(getCollection(r));
			 * r.close(); pst.close(); posicion=1; //NUMERO DE NInOS POR
			 * SEDE-JORNADA-GRADO X GENERO
			 * pst=cn.prepareStatement(rb.getString("lista.ficha11"));
			 * pst.clearParameters(); pst.setLong(posicion++,inst); r =
			 * pst.executeQuery(); f.setFichaEstXJorGrado(getCollection(r));
			 * r.close(); pst.close(); posicion=1;
			 */
			// NUMERO DE ESPACIOS FISICOS POR SEDE X TIPO
			pst = cn.prepareStatement(rb.getString("lista.ficha12"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			f.setFichaEspacioXTipo(getCollection(r));
			r.close();
			pst.close();
			posicion = 1;
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error SQL intentando asignar Ficha Tecnica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar Ficha Tncnica. Posible problema: "
					+ sqle);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	public boolean actualizarFicha(FichaVO f) {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			long inst = Long.parseLong(f.getFichaInstitucion());
			long enfasis = Long.parseLong(f.getFichaEnfasis());
			long modalidad = Long.parseLong(f.getFichaModalidad());
			int[] valores = f.getFichaPrograma();
			String a = f.getFichaEspecialidad();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("update.ficha1"));
			pst.clearParameters();
			pst.setLong(posicion++, enfasis);
			pst.setLong(posicion++, modalidad);
			if (f.getFichaResolucion() == null
					|| f.getFichaResolucion().equals(""))
				pst.setNull(posicion++, Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFichaResolucion());
			if (f.getFichaFechaResolucion() == null
					|| f.getFichaFechaResolucion().equals(""))
				pst.setNull(posicion++, Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFichaFechaResolucion());
			pst.setLong(posicion++, inst);
			pst.executeUpdate();
			pst.close();
			/*
			 * posicion=1; if(a!=null && !a.equals("")){
			 * pst=cn.prepareStatement(rb.getString("update.ficha2"));
			 * pst.clearParameters(); pst.setLong(posicion++,Long.parseLong(a));
			 * pst.setLong(posicion++,inst); pst.executeUpdate(); pst.close(); }
			 */
			// programas
			pst = cn.prepareStatement(rb.getString("update.fichaPrograma1"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, inst);
			pst.executeUpdate();
			pst.close();

			if (valores != null && valores.length > 0) {
				pst = cn.prepareStatement(rb.getString("update.fichaPrograma2"));
				pst.clearBatch();
				for (int i = 0; i < valores.length; i++) {
					if (valores[i] == -99)
						continue;
					posicion = 1;
					pst.setLong(posicion++, inst);
					pst.setInt(posicion++, valores[i]);
					pst.addBatch();
				}
				pst.executeBatch();
				pst.close();
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error SQL intentando actualizar Ficha Tecnica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando actualizar Ficha Tncnica. Posible problema: "
					+ sqle);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param codInst
	 * @return
	 * @throws Exception
	 */
	public long getTotalDocentesInst(long codInst) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		long totalDocentes = -9;
		String sql = " SELECT count(*) " + "  from ( "
				+ "  SELECT   distinct DOCSEDJORNUMDOCUM  "
				+ "  FROM DOCENTE_SEDE_JORNADA,       "
				+ "      USUARIO,       " + "      g_jerarquia,       "
				+ "      PERSONAL   " + " where g_jerinst = ?   "
				+ " and G_JERINST=DOCSEDJORCODINST  "
				+ " and G_JERSEDE=DOCSEDJORCODSEDE  "
				+ " and G_JERJORN=DOCSEDJORCODJOR   "
				+ " and DOCSEDJORNUMDOCUM=USUPERNUMDOCUM   "
				+ " and PERNUMDOCUM = DOCSEDJORNUMDOCUM   "
				+ " and USUCODJERAR = g_jercodigo  "
				+ " and USUperfcodigo =  ? "
				+ " AND  PERGENERO IS NOT  NULL  )";
		try {

			cn = cursor.getConnection();
			// poner docentes por genero
			// pst=cn.prepareStatement(rb.getString("lista.ficha5"));
			pst = cn.prepareStatement(sql);
			posicion = 1;
			pst.setLong(posicion++, codInst);
			pst.setString(posicion++, "" + Perfil.PERFIL_DOCENTE);
			r = pst.executeQuery();
			if (r.next()) {
				totalDocentes = r.getLong(1);
			} else {
				throw new Exception("No hay docentes para esta institucinn.");
			}
			// System.out.println("totalDocentes " + totalDocentes);
		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			sqle.printStackTrace();

			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return totalDocentes;
	}

	/**
	 * @param codInst
	 * @param sedCodigo
	 * @param jorCodigo
	 * @param genero
	 * @return
	 * @throws Exception
	 */
	public long getCountDocentes(long codInst, long sedCodigo, long jorCodigo,
			long genero) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		long totalGenero = -9;

		String sql = " SELECT count(*)" + "  from ( "
				+ "  SELECT   distinct DOCSEDJORNUMDOCUM "
				+ "  FROM DOCENTE_SEDE_JORNADA,       "
				+ "      USUARIO,       " + "      g_jerarquia,       "
				+ "      PERSONAL   " + " where g_jerinst = ?   "
				+ " and G_JERINST=DOCSEDJORCODINST  " + " and G_JERSEDE=? "
				+ " and G_JERSEDE=DOCSEDJORCODSEDE  " + " and G_JERJORN=? "
				+ " and G_JERJORN=DOCSEDJORCODJOR   "
				+ " and DOCSEDJORNUMDOCUM=USUPERNUMDOCUM   "
				+ " and PERNUMDOCUM = DOCSEDJORNUMDOCUM   "
				+ " and USUCODJERAR = g_jercodigo  "
				+ " and USUperfcodigo =  ? " + " AND  PERGENERO = ?)";
		try {

			cn = cursor.getConnection();

			pst = cn.prepareStatement(sql);
			posicion = 1;
			pst.setLong(posicion++, codInst);
			pst.setLong(posicion++, sedCodigo);
			pst.setLong(posicion++, jorCodigo);
			pst.setString(posicion++, "" + Perfil.PERFIL_DOCENTE);
			pst.setLong(posicion++, genero);
			r = pst.executeQuery();

			if (r.next()) {
				totalGenero = r.getLong(1);
			}

		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			sqle.printStackTrace();

			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return totalGenero;
	}

	/**
	 * @function Realiza el conteo de los docentes segnn el rango de edad y
	 *           genero
	 * @param codInst
	 * @param sedCodigo
	 * @param jorCodigo
	 * @param genero
	 * @return
	 * @throws Exception
	 */
	public long getCountDocentesEdad(long codInst, long sedCodigo,
			long jorCodigo, long genero, long edadIni, long edadFin)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		long totalGenero = -9;

		/*
		 * String sql = " SELECT count(*)" + "  from ( " +
		 * "  SELECT   distinct DOCSEDJORNUMDOCUM " +
		 * "  FROM DOCENTE_SEDE_JORNADA,       " + "      USUARIO,       " +
		 * "      g_jerarquia,       " + "      PERSONAL   " +
		 * " where g_jerinst = ?   " + " and G_JERINST=DOCSEDJORCODINST  " +
		 * " and G_JERSEDE=? " + " and G_JERSEDE=DOCSEDJORCODSEDE  " +
		 * " and G_JERJORN=? " + " and G_JERJORN=DOCSEDJORCODJOR   " +
		 * " and DOCSEDJORNUMDOCUM=USUPERNUMDOCUM   " +
		 * " and PERNUMDOCUM = DOCSEDJORNUMDOCUM   " +
		 * " and USUCODJERAR = g_jercodigo  " + " and USUperfcodigo =  ? " +
		 * " AND PERGENERO = ?" + " AND PEREDAD IS NOT NULL" +
		 * " AND PEREDAD BETWEEN ? AND ?)";
		 */

		String sql = " SELECT count(*)"
				+ "  from ( "
				+ "  SELECT distinct DOCSEDJORNUMDOCUM "
				+ "  FROM DOCENTE_SEDE_JORNADA,       "
				+ "      USUARIO,       "
				+ "      g_jerarquia,       "
				+ "      PERSONAL   "
				+ " where g_jerinst = ?   "
				+ " and G_JERINST=DOCSEDJORCODINST  "
				+ " and G_JERSEDE=? "
				+ " and G_JERSEDE=DOCSEDJORCODSEDE  "
				+ " and G_JERJORN=? "
				+ " and G_JERJORN=DOCSEDJORCODJOR   "
				+ " and DOCSEDJORNUMDOCUM=USUPERNUMDOCUM   "
				+ " and PERNUMDOCUM = DOCSEDJORNUMDOCUM   "
				+ " and USUCODJERAR = g_jercodigo  "
				+ " and USUperfcodigo =  ? "
				+ " AND PERGENERO = ?"
				+ " AND PERGENERO IS NOT NULL"
				+ " AND perfechanac IS NOT NULL"
				+ " AND (((to_date(perfechanac,'DD/MM/YY') - to_date(SYSDATE,'DD/MM/YY')) / 365) ) BETWEEN ? AND ?)";

		try {

			cn = cursor.getConnection();
			// System.out.println(" getCountDocentesEdad sql " + sql);
			pst = cn.prepareStatement(sql);
			posicion = 1;
			pst.setLong(posicion++, codInst);
			pst.setLong(posicion++, sedCodigo);
			pst.setLong(posicion++, jorCodigo);
			pst.setString(posicion++, "" + Perfil.PERFIL_DOCENTE);
			pst.setLong(posicion++, genero);
			pst.setLong(posicion++, edadIni);
			pst.setLong(posicion++, edadFin);

			r = pst.executeQuery();

			if (r.next()) {
				totalGenero = r.getLong(1);
			}

		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			sqle.printStackTrace();

			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return totalGenero;
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
			st = cn.prepareStatement(rb.getString("institucion.getGrado"));

			st.setInt(posicion++, filtroVO.getFilinst());
			st.setInt(posicion++, filtroVO.getFilsede());
			st.setInt(posicion++, filtroVO.getFilsede());
			st.setInt(posicion++, filtroVO.getFiljornd());
			st.setInt(posicion++, filtroVO.getFiljornd());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				// En este caso padre guarda la metodologia
				itemVO.setPadre(rs.getInt(posicion++));
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
	 * @function Metodo que realiza el conteo de estudiante deacuerdo a los
	 *           parametros de institucion, sede, jornada y genero
	 * @param codInst
	 * @param sedCodigo
	 * @param jorCodigo
	 * @param genero
	 * @return
	 * @throws Exception
	 */
	public long getCountEst(long codInst, long sedCodigo, long jorCodigo,
			long graCodigo, long metodCodigo, int genero) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		long totalGenero = -9;

		String sql = " select count(*) " + " from (    "
				+ "    SELECT distinct ESTCODIGO     "
				+ "     FROM  ESTUDIANTE E,              "
				+ "           G_JERARQUIA a,              "
				+ "           G_JERARQUIA b,               "
				+ "           GRUPO g        "
				+ "      WHERE a.G_JerTipo  =1        "
				+ "        and a.G_JerNivel =7        "
				+ "      AND   b.G_JERTIPO  = 1        "
				+ "      AND   b.G_JERNIVEL = 8        "
				+ "      AND   b.G_JERINST = ?        "
				+ "      AND (( ? = -99) OR ( b.G_JERSEDE  = ? ))        "
				+ "      AND (( ? = -99) OR ( b.G_JERJORN  = ? ))        "
				+ "      AND (( ? = -99) OR ( b.G_JERGRADO = ? ))        "
				+ "      AND (( ? = -99) OR ( b.G_JerMetod = ? ))        "
				+ "      AND b.G_JerInst =  a.G_JerInst         "
				+ "      and b.G_JerSede =  a.G_JerSede         "
				+ "      and b.G_JerJorn =  a.G_JerJorn         "
				+ "      and b.G_JerMetod = a.G_JerMetod        "
				+ "      and b.G_JerGrado = a.G_JerGrado        "
				+ "      and g.GruCodigo= b.G_Jergrupo        "
				+ "      and b.g_jercodigo = estgrupo         "
				+ "      AND g.GRUCODJERAR = a.g_jercodigo        "
				+ "      AND ESTESTADO BETWEEN 100 AND 199        "
				+ "      AND estgenero is not null "
				+ "      AND estgenero = ?       " + "  )";
		try {

			cn = cursor.getConnection();

			pst = cn.prepareStatement(sql);
			posicion = 1;
			pst.setLong(posicion++, codInst);
			pst.setLong(posicion++, sedCodigo);
			pst.setLong(posicion++, sedCodigo);
			pst.setLong(posicion++, jorCodigo);
			pst.setLong(posicion++, jorCodigo);
			pst.setLong(posicion++, graCodigo);
			pst.setLong(posicion++, graCodigo);
			pst.setLong(posicion++, metodCodigo);
			pst.setLong(posicion++, metodCodigo);

			pst.setLong(posicion++, genero);

			r = pst.executeQuery();

			if (r.next()) {
				totalGenero = r.getLong(1);
			}

		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			sqle.printStackTrace();

			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return totalGenero;
	}

	/**
	 * @param filtroVO
	 * @return
	 * @throws Exception
	 */
	public List getListaGrados(FiltroCommonVO filtroVO) throws Exception {
		// System.out.println("getListaGrado ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("institucion.getGrados"));

			st.setInt(posicion++, filtroVO.getFilinst());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				// En este caso padre guarda la metodologia
				itemVO.setPadre(rs.getInt(posicion++));
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
	 * @param inst
	 * @param sede
	 * @return
	 * @throws Exception
	 */
	public List getListaEspacios(long inst, long sede) throws Exception {
		// System.out.println("getListaGrado ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("institucion.espacio"));

			st.setLong(posicion++, inst);
			st.setLong(posicion++, sede);

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setCodigo2("" + rs.getInt(posicion++));
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

}
