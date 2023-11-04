package siges.gestionAdministrativa.boletinPublico.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		20/05/2020	JORGE CAMACHO	Se modificÛ el mÈtodo getTipoEval_() para corregir el c·lculo de la vigencia
//		2.0		02/07/2020	JORGE CAMACHO	En el mÈtodo inserrDatosBoletin() se eliminÛ el mÈtodo getTipoEval_() y se reemplazÛ por el mÈtodo getTipoEvaPreescolar()
//											donde se retorna la columna INSNIVTIPOEVALPREES y asÌ se inserta correctamente el dato en la tabla DATOS_BOLETIN
//		3.0		29/09/2020	JORGE CAMACHO	Se editÛ el mÈtodo validarPermiso() para mejorar el seguimiento en el debug


import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO;
import siges.gestionAdministrativa.boletinPublico.vo.ConsultaExterma;
import siges.gestionAdministrativa.boletinPublico.vo.DatosBoletinVO;
import siges.gestionAdministrativa.boletinPublico.vo.EstudianteVO;
import siges.gestionAdministrativa.boletinPublico.vo.ParamsVO;
import siges.gestionAdministrativa.boletinPublico.vo.PlantillaBoletionPubVO;
import siges.gestionAdministrativa.boletinPublico.vo.TipoEvalVO;
import siges.login.beans.Login;
import siges.plantilla.beans.NivelEvalVO;


/**
 * 17/05/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class BoletinPublicoDAO extends Dao {
	
	private ResourceBundle rb;
	private java.sql.Timestamp f2;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public BoletinPublicoDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("siges.gestionAdministrativa.boletinPublico.bundle.boletinPublico");
	}
	
	
	
	
	
	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getJornada(long codInst, long codSede) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("plantillaBoletin.getJornada"));
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
	 * @return
	 * @throws Exception
	 */
	public List getMetodologia(long codInst) throws Exception {
		// System.out.println("getMetodologia " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("plantillaBoletin.getMetodologia"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				// System.out.println("itemVO.getNombre().length() " +
				// itemVO.getNombre().length());
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
	 * @return
	 * @throws Exception
	 */
	public List getGrado(PlantillaBoletionPubVO plantillaBoletionVO)
			throws Exception {
		// System.out.println("getGrado ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("plantillaBoletin.getGrado"));

			st.setLong(posicion++, plantillaBoletionVO.getPlabolinst());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolsede());
			st.setLong(posicion++, plantillaBoletionVO.getPlaboljornd());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolmetodo());
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
	 * @function:
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public EstudianteVO getNombreEstudiante(long tipoDoc, String numDOc,
			long codEst) throws Exception {
		// System.out.println("BoletinPublico: getNombreEstudiante ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PlantillaBoletionVO plantillaBoletionVO2 = new PlantillaBoletionVO();
		EstudianteVO estudianteVO = new EstudianteVO();
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getNombreEstudiante"));

			st.setString(posicion++, numDOc.trim());
			// System.out.println("numDOc " + numDOc);
			st.setLong(posicion++, tipoDoc);
			// System.out.println("tipoDoc " + tipoDoc);
			st.setLong(posicion++, codEst);
			// System.out.println("codEst " + codEst);

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				estudianteVO.setEstcodigo(rs.getLong(posicion++));
				estudianteVO.setEstgrupo(rs.getLong(posicion++));
				// System.out.println("estudianteVO.setEstgrupo( " +
				// estudianteVO.getEstgrupo());
				estudianteVO.setEstnombre(rs.getString(posicion++));
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
		return estudianteVO;
	}

	/**
	 * @function: Obtiene los datos de jeraquia deacuerdo con el codigo de grupo
	 *            que esta en estudiante
	 * @param estudianteVO
	 * @return
	 * @throws Exception
	 */
	public PlantillaBoletionPubVO getDatosJerarquia(EstudianteVO estudianteVO)
			throws Exception {
		// System.out.println("BoletinPublico: getDatosJerarquia ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PlantillaBoletionPubVO plantillaBoletionPubVO = new PlantillaBoletionPubVO();

		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getDatosJerarquia"));

			st.setLong(posicion++, estudianteVO.getEstgrupo());
			// System.out.println("estudianteVO.getEstgrupo()  " +
			// estudianteVO.getEstgrupo());

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				plantillaBoletionPubVO.setPlabolinst(rs.getLong(posicion++));
				plantillaBoletionPubVO.setPlabolsede(rs.getLong(posicion++));
				plantillaBoletionPubVO.setPlaboljornd(rs.getLong(posicion++));
				plantillaBoletionPubVO.setPlabolmetodo(rs.getLong(posicion++));
				plantillaBoletionPubVO.setPlabolgrado(rs.getLong(posicion++));
				plantillaBoletionPubVO.setPlabolgrupo(rs.getLong(posicion++));
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
		return plantillaBoletionPubVO;
	}

	/**
	 * @function:
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public List getGrupo(PlantillaBoletionPubVO plantillaBoletionVO)
			throws Exception {
		// System.out.println("BoletinPublico: getGrupo " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("plantillaBoletin.getGrupo"));

			st.setLong(posicion++, plantillaBoletionVO.getPlabolinst());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolsede());
			st.setLong(posicion++, plantillaBoletionVO.getPlaboljornd());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolmetodo());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolgrado());

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
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

	public boolean isPerCerrado(PlantillaBoletionPubVO filtro) throws Exception {
		// System.out.println("BoletinPublico: isPerCerrado " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("plantillaBoletin.isPerCerrado."
							+ filtro.getPlabolperido()));
			// System.out.println("consulta " +
			// rb.getString("plantillaBoletin.isPerCerrado." +
			// filtro.getPlabolperido()));
			st.setLong(posicion++, filtro.getPlabolvigencia());
			// System.out.println("filtro.getPlabolvigencia() "
			// +filtro.getPlabolvigencia());
			st.setLong(posicion++, filtro.getPlabolinst());
			// System.out.println("filtro.getPlabolinst() " +
			// filtro.getPlabolinst());
			st.setLong(posicion++, filtro.getPlabolsede());
			// System.out.println("filtro.getPlabolsede() " +
			// filtro.getPlabolsede()) ;
			st.setLong(posicion++, filtro.getPlaboljornd());
			// System.out.println("filtro.getPlaboljornd() " +
			// filtro.getPlaboljornd());
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				// System.out.println("resultado " + rs.getLong(posicion));
				return true;
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
				inte.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @param plantillaBoletionVO
	 * @return
	 * @throws Exception
	 */
	public ItemVO getGrupoJer(PlantillaBoletionPubVO plantillaBoletionVO)
			throws Exception {
		// System.out.println("BoletinPublico: getGrupoJer " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("plantillaBoletin.getGrupoJer"));

			st.setLong(posicion++, plantillaBoletionVO.getPlabolinst());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolsede());
			st.setLong(posicion++, plantillaBoletionVO.getPlaboljornd());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolmetodo());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolgrado());
			// System.out.println("plantillaBoletionVO.getPlabolgrupo()  ");
			st.setLong(posicion++, plantillaBoletionVO.getPlabolgrupo());

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));

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
		return itemVO;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getSede(long codInst) throws Exception {
		// System.out.println("BoletinPublico: getSede");

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("plantillaBoletin.getSede"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
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
	 * @return
	 * @throws Exception
	 */
	public String getDANE(PlantillaBoletionVO plantillaBoletionVO)
			throws Exception {
		// System.out.println("BoletinPublico : getDANE ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		String dane = "";
		try {
			cn = cursor.getConnection();

			posicion = 1;
			st = cn.prepareStatement(rb.getString("cargarOtroDatos"));
			st.setLong(posicion, plantillaBoletionVO.getPlabolinst());
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				// plantillaBoletionVO.setPlabolinstResolucion(rs.getString(posicion++));
				dane = rs.getString(posicion++);
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
		return dane;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String getCoordinadorNombre(PlantillaBoletionVO plantillaBoletionVO)
			throws Exception {
		// System.out.println("getCoordinadorNombre ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 0;
		String numDocCoord = "";
		String nombCoord = "";
		try {
			cn = cursor.getConnection();

			posicion = 1;
			st = cn.prepareStatement(rb.getString("getCoordinadorNombre.1"));
			st.setLong(posicion++, plantillaBoletionVO.getPlabolinst());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolsede());
			st.setLong(posicion++, plantillaBoletionVO.getPlaboljornd());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolmetodo());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolgrado());
			st.setLong(posicion++, plantillaBoletionVO.getPlabolgrupo());
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				// plantillaBoletionVO.setPlabolinstResolucion(rs.getString(posicion++));
				numDocCoord = rs.getString(1);
			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb.getString("getCoordinadorNombre.2"));
			st.setString(posicion++, numDocCoord);
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				// plantillaBoletionVO.setPlabolinstResolucion(rs.getString(posicion++));
				nombCoord = rs.getString(1);
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
		return nombCoord;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String getResolInst(long inst) throws Exception {
		// System.out.println("boletin : getResolInst");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		long codigo = 0;
		String resol = "";
		boolean cont = true;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("boletinGetResolInst"));
			st.setLong(1, inst);
			// System.out.println("inst" + inst);
			rs = st.executeQuery();
			if (rs.next()) {
				resol = rs.getString(1);
			}
			// System.out.println("resol " + resol);
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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return resol;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String getListaMEN(long inst) throws Exception {
		// System.out.println("getListaMEN");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		long codigo = 0;
		String resol = "";
		boolean cont = true;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getListaMEN"));
			rs = st.executeQuery();
			while (rs.next()) {
				resol += "  " + rs.getString(1);
			}
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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return resol;
	}

	/**
	 * @function: Retorna la escala segun el tipo de evaluacion de institucion
	 * @param n
	 * @param login
	 * @return
	 */
	public String getEscalaNivelEval(
			PlantillaBoletionPubVO PlantillaBoletionPubVO) throws Exception {
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		String escala = "";
		try {

			// 1. Obtener el nivel eval y el tipo eval (1.NUMERICO,
			// 2.CONCEPTUAL, 3.PORCENTUAL)

			TipoEvalVO tipoEvalVO = getTipoEval(cn, PlantillaBoletionPubVO);

			// 2. Si es conceptual obtener escalas, sino retorna el max y min
			// System.out.println("tipoEvalVO.getCod_tipo_eval()  " +
			// tipoEvalVO.getCod_tipo_eval() );
			if (tipoEvalVO.getCod_tipo_eval() == ParamsVO.ESCALA_CONCEPTUAL) {
				return getEscalaConceptual(PlantillaBoletionPubVO);
			} else {

				escala = "Mnnimo " + tipoEvalVO.getEval_min() + "  - Mnximo "
						+ tipoEvalVO.getEval_max();

			}

		} catch (Exception sqle) {

			setMensaje("Error intentando obtener escala de AREA/ASIG. Posible problema: ");
			System.out.println("sqle.getMessage() " + sqle.getMessage());
			throw new Exception(sqle.getMessage());

		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return escala;
	}

	// CAMBIOS EVLUACION PARAMETRSO INSTITICION

	public TipoEvalVO getTipoEval(Connection cn,
			PlantillaBoletionPubVO plantillaBoletionVO) throws Exception {

		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		TipoEvalVO tipoEvalVO = new TipoEvalVO();
		try {

			vigencia = getVigenciaInst(plantillaBoletionVO.getPlabolinst());
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, plantillaBoletionVO.getPlabolinst());
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
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");

			// System.out.println("CONSULTA  " + sql);
			st = cn.prepareStatement(sql);
			i = 1;

			/*
			 * System.out.println("plantillaBoletionVO.getPlabolinst() " +
			 * plantillaBoletionVO.getPlabolinst());
			 * System.out.println("plantillaBoletionVO.getPlabolsede()  " +
			 * plantillaBoletionVO.getPlabolsede() );
			 * System.out.println("plantillaBoletionVO.getPlaboljornd() " +
			 * plantillaBoletionVO.getPlaboljornd());
			 * System.out.println("plantillaBoletionVO.getPlabolmetodo() " +
			 * plantillaBoletionVO.getPlabolmetodo());
			 * System.out.println("plantillaBoletionVO.getPlabolgrado() " +
			 * plantillaBoletionVO.getPlabolgrado()); System.out.println(
			 * "getVigenciaInst( plantillaBoletionVO.getPlabolinst()) " +
			 * getVigenciaInst( plantillaBoletionVO.getPlabolinst()));
			 */st.setLong(i++,
					getVigenciaInst(plantillaBoletionVO.getPlabolinst()));
			st.setLong(i++, plantillaBoletionVO.getPlabolinst());

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, plantillaBoletionVO.getPlabolsede());
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, plantillaBoletionVO.getPlaboljornd());
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, plantillaBoletionVO.getPlabolmetodo());
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado((int) plantillaBoletionVO
						.getPlabolgrado());
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1) {
				st.setLong(i++, plantillaBoletionVO.getPlabolgrado());
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

	@Override
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

	public String getEscalaConceptual(PlantillaBoletionPubVO plantillaBoletionVO)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		List l = new ArrayList();
		ItemVO item = null;
		Collection list = null;
		String escalaConc = "";
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaInst(plantillaBoletionVO.getPlabolinst());
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, plantillaBoletionVO.getPlabolinst());
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
			String sql = rb.getString("getEscalaConceptual.select");
			if (nivelEval.getEval_sede() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.sede");
			if (nivelEval.getEval_jornada() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.jornada");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.metod");
			if (nivelEval.getEval_nivel() == 1)
				// sql=sql+rb.getString("getTipoEval.nivel");
				sql = sql + rb.getString("getEscalaConceptual.nivel");

			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getEscalaConceptual.grado");

			// System.out.println("CONCEPTUALO sql" + sql);
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++,
					getVigenciaInst(plantillaBoletionVO.getPlabolinst()));
			st.setLong(i++, plantillaBoletionVO.getPlabolinst());
			st.setLong(i++, codigoNivelEval);

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, plantillaBoletionVO.getPlabolsede());
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, plantillaBoletionVO.getPlaboljornd());
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, plantillaBoletionVO.getPlabolmetodo());
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado((int) plantillaBoletionVO
						.getPlabolgrado());
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, plantillaBoletionVO.getPlabolgrado());

			rs = st.executeQuery();
			i = 1;
			while (rs.next()) {
				// System.out.println("escalaConc " + escalaConc);
				escalaConc += rs.getString(1);
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
		return escalaConc;
	}

	public Login login4Params(long inst) {
		// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** ");
		String[][] us = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list;
		Object[] o;
		Login login = new Login();
		list = new ArrayList();
		try {
			// cn=c.getConnection(1);
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("getParametrosInstitucion"));
			long vigenciaActual = getVigenciaInst(inst);

			pst.setLong(1, vigenciaActual);
			// System.out.println("vigenciaActual " + vigenciaActual);
			pst.setLong(2, inst);
			// System.out.println("inst " + inst);
			rs = pst.executeQuery();
			list = new ArrayList();
			int j = 0, f = 0;
			if (rs.next()) {
				// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** OK VIGENCIA: "+vigenciaActual+" ** INSTITUCION: "+login.getInstId());
				f = 1;
				login.setLogNumPer(rs.getLong(f++));
				login.setLogTipoPer(rs.getLong(f++));
				login.setLogNomPerDef(rs.getString(f++));
				login.setLogNivelEval(rs.getLong(f++));
			}
			// else
			// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION *** NO ENCONTRO DATOS ");
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

	/**
	 * @return
	 * @throws Exception
	 */
	public String getnombreInst(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		String nombreInst = "";
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getnombreInst"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				nombreInst = rs.getString(posicion++);
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
		return nombreInst;
	}

	/**
	 * @function: Inserta la informaci√≥n en DATOS_BOLETIN necesario para generar
	 *            un boletnn.
	 * @param filtro
	 * @param login
	 * @return
	 */
	public PlantillaBoletionPubVO inserrDatosBoletin(PlantillaBoletionPubVO filtro, Login login) {
		
		Connection cn = null;
		PreparedStatement pst = null;
		
		int posicion = 1;
		
		String nom = "";
		String archivo = "";
		String archivopre = "";
		String archivozip = "";
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		
		try {

			nom = "_Per_" + filtro.getPlabolperido() + "_" + filtro.getPlabolnumdoc() + "_Fec_" + f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
			archivo = "Bol_" + nom + ".pdf";
			archivopre = "Bol_Pre_" + nom + ".pdf";
			archivozip = "Bol_" + nom + ".zip";

			cn = cursor.getConnection();

			pst = cn.prepareStatement(rb.getString("insert_datos_boletin"));
			System.out.println(rb.getString("insert_datos_boletin"));
			
			pst.setLong(posicion++, filtro.getPlabolinst()); 												// DABOLINST
			pst.setLong(posicion++, filtro.getPlabolsede());												// DABOLSEDE
			pst.setLong(posicion++, filtro.getPlaboljornd());												// DABOLJORNADA
			pst.setLong(posicion++, filtro.getPlabolmetodo());												// DABOLMETODOLOGIA
			pst.setLong(posicion++, filtro.getPlabolgrado());												// DABOLGRADO
			pst.setLong(posicion++, -9/* filtro.getPlabolgrupo() */);										// DABOLGRUPO
			pst.setLong(posicion++, filtro.getPlabolperido());												// DABOLPERIODO
			pst.setString(posicion++, "" + filtro.getPlabolperido());										// DABOLPERIODONOM
			pst.setString(posicion++, filtro.getPlabolnumdoc());											// DABOLCEDULA

			pst.setString(posicion++, f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-'));	// DABOLFECHA
			pst.setString(posicion++, archivozip);															// DABOLNOMBREZIP
			filtro.setPlabolarchivozip(archivozip);
			
			pst.setString(posicion++, archivo);																// DABOLNOMBREPDF
			pst.setString(posicion++, archivopre);															// DABOLNOMBREPDFPRE
			pst.setLong(posicion++, Long.parseLong("-1"));													// DABOLESTADO
			pst.setString(posicion++, login.getUsuarioId());// login.getUsuarioId()							// DABOLUSUARIO
			pst.setLong(posicion++, 1);// MostrarDescriptores()												// DABOLDESCRIPTORES
			pst.setLong(posicion++, 1);// MostrarLogros() 													// DABOLLOGROS
			pst.setLong(posicion++, -9);// ConAusenciasT 													// DABOLINATOT
			pst.setLong(posicion++, -9);// isConAusenciasD 													// DABOLINADET
			pst.setLong(posicion++, -9);// ConFirmaRector() 												// DABOLFIRREC
			pst.setLong(posicion++, -9);// ConFirmaRector() 												// DABOLFIRDIR
			pst.setLong(posicion++, -9);// MostrarLogrosP 													// DABOLTOTLOGROS
			pst.setLong(posicion++, -9);// FormatoDos 														// DABOLFORDOS
			pst.setLong(posicion++, 1);// MostrarAreas 														// DABOLAREA
			pst.setLong(posicion++, 1);// MostrarAsignaturas() 												// DABOLASIG
			// long consecRepBol= getConsecReporte(cn);
			// pst.setLong(posicion++,consecRepBol); //DABOLCONSEC
			pst.setString(posicion++, filtro.getPlabolinstBNomb());											// DABOLINSNOMBRE
			pst.setString(posicion++, filtro.getPlabolsedeNomb()); 											// DABOLSEDNOMBRE
			pst.setString(posicion++, filtro.getPlaboljorndNomb()); 										// DABOLJORNOMBRE
			pst.setLong(posicion++, filtro.getPlabolvigencia()); 											// DABOLVIGENCIA

			String resol = getResolInst(cn, filtro.getPlabolinst());
			pst.setString(posicion++, resol);																// DABOLRESOLUCION

			int codigoNivelEval = getTipoEval_codigoNivelEval(cn, filtro);
			pst.setLong(posicion++, codigoNivelEval); 														// DABOLNIVEVAL
			
			pst.setLong(posicion++, filtro.getPlabolperidoNum());											// DABOLNUMPER
			pst.setString(posicion++, filtro.getPlabolperidoNomb());										// DABOLNOMPERDEF
			
			//int tipoPrees = getTipoEval_(cn, filtro);
			int tipoPrees = getTipoEvaPreescolar(cn, filtro);
			pst.setInt(posicion++, tipoPrees);																// DABOLTIPOEVALPREES
			
			pst.setLong(posicion++, filtro.getPlaboltipodoc()); 											// DABOLTIPODOC

			pst.executeUpdate();
			pst.close();

		} catch (Exception e) {
			
			System.out.println("ERROR inserrDatosBoletin" + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {}
			
		}
		
		return filtro;
		
	}
	
	
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public PlantillaBoletionPubVO getReporteConsecutivo(
			PlantillaBoletionPubVO filtro) throws Exception {
		// System.out.println("getReporteConsecutivo ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("boletines.getConsecutivo"));
			st.setString(posicion++, filtro.getPlabolarchivozip());
			// System.out.println("filtro.getPlabolarchivozip() " +
			// filtro.getPlabolarchivozip());
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				filtro.setPlabolconsecRepBol(rs.getLong(posicion++));
				// System.out.println("CONSECUTIVO DEL BOLETIN TIPO 4 " +
				// filtro.getPlabolconsecRepBol());
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
			throw new Exception("Error de datos: " + sql.getMessage());

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
		return filtro;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean updateReporte(PlantillaBoletionPubVO filtro, Login login,
			long estado) throws Exception {
		// System.out.println("updateReporte ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("boletines.updateReporte"));
			posicion = 1;
			st.setLong(posicion++, estado);
			// where
			st.setLong(posicion++, filtro.getPlabolconsecRepBol());
			int band = st.executeUpdate();

			if (band == 1) {
				// System.out.println("Actualizo la estado " + estado);
				return true;
			} else {
				throw new Exception("Error en boletin publico");
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			// throw new Exception("Error de datos: "+sqle.getMessage());
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			// throw new Exception("Error interno: "+sqle.getMessage());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @function:
	 * @param cn
	 * @return
	 * @throws Exception
	 */
	public long getConsecReporte(Connection cn) throws Exception {
		// System.out.println("BOLETINES Y RESUMENES: ENTRO CODIGO NUEVO CONSEC DAO");

		PreparedStatement st = null;
		ResultSet rs = null;
		long codigo = 0;
		try {

			st = cn.prepareStatement(rb.getString("boletinRepGetConsec"));
			rs = st.executeQuery();
			if (rs.next())
				codigo = rs.getLong(1);
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
		return codigo;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getResolInst(Connection cn, long inst) throws Exception {
		// System.out.println("BOLETINES: ENTRO RESOL INST DAO");

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
	
	
	
	
	
	/**
	 * @function:
	 * @param cn
	 * @param filtroReporte
	 * @return
	 * @throws Exception
	 */
	public int getTipoEval_(Connection cn, PlantillaBoletionPubVO filtroReporte) throws Exception {

		ResultSet rs = null;
		PreparedStatement st = null;
		
		int i = 1;
		int tipoEval = 0;
		long vigencia = 0;
		long codigoNivelEval = 0;
		
		NivelEvalVO nivelEval = new NivelEvalVO();

		try {
			
			cn = cursor.getConnection();
			vigencia = getVigenciaInst(filtroReporte.getPlabolinst());
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, filtroReporte.getPlabolinst());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			
			i = 1;
			// TRAER PARAMETROS DE NIVEL EVALUACION
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
				sql = sql + rb.getString("getTipoEval.nivel");
			
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");
			
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, vigencia);
			st.setLong(i++, filtroReporte.getPlabolinst());

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, filtroReporte.getPlabolsede());
			
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, filtroReporte.getPlaboljornd());
			
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, filtroReporte.getPlabolmetodo());
			
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado((int) filtroReporte.getPlabolgrado());
				st.setInt(i++, nivelGrado);
			}
			
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, filtroReporte.getPlabolgrado());

			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				tipoEval = rs.getInt(i++);
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
		return tipoEval;
	}
	
	
	
	
	
	/**
	 * @function:
	 * @param cn
	 * @param filtroReporte
	 * @return
	 * @throws Exception
	 */
	public int getTipoEval_codigoNivelEval(Connection cn, PlantillaBoletionPubVO filtroReporte) throws Exception {

		ResultSet rs = null;
		PreparedStatement st = null;
		
		int i = 1;
		long vigencia = 0;
		int codigoNivelEval = 0;

		try {
			
			cn = cursor.getConnection();
			vigencia = getVigenciaInst(filtroReporte.getPlabolinst());
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, filtroReporte.getPlabolinst());
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
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 **/
	@SuppressWarnings({ "rawtypes", "unused" })
	public boolean callProcedimientoBoletinAsig(long consec) throws Exception {
		
		ResultSet rs = null;
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		
		int posicion = 1;

		try {
			
			if (consec > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con.prepareCall("{call PK_BOLETIN.PK_BOL_INICIAL(?)}");
				cstmt.setLong(posicion++, consec);
				// System.out.println("BOLETINES: ASIGNATURA - consec: "+consec+" Inicia procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("BOLETINES: ASIGNATURA - Fin procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		
		return true;
		
	}
	
	
	
	
	
	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 **/
	@SuppressWarnings({ "rawtypes", "unused" })
	public boolean callProcedimientoBoletinDim(long consec) throws Exception {
		
		ResultSet rs = null;
		Connection con = null;
		CallableStatement cstmt = null;
		
		Collection list = new ArrayList();
		
		int posicion = 1;

		try {
			
			if (consec > 0) {
				
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con.prepareCall(" {call PK_BOLETIN_DIMENSIONES.BOL_DIM_INICIAL(?)} ");
				cstmt.setLong(posicion++, consec);
				// System.out.println("BOLETINES: DIMENSIONES - consec: "+consec+" Inicia procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("BOLETINES: DIMENSIONES - Fin procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		
		return true;
		
	}
	
	
	
	
	
	/**
	 * @function:
	 * @param consecutivo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public boolean validarDatosBoletin(long consecutivo) throws Exception {
		// System.out.println("validarDatosBoletin");

		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		long codigo = 0;
		int posicion = 1;
		
		try {
			
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("validarDatosBoletin"));
			posicion = 1;
			// System.out.println("consecutivo " + consecutivo);
			st.setLong(posicion++, consecutivo);
			rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return false;
		
	}
	
	
	
	
	
	/**
	 * @function:
	 * @param consecutivo
	 * @return
	 * @throws Exception
	 */
	public boolean validarPermiso(PlantillaBoletionPubVO pb) throws Exception {

		long codigo = 0;
		int posicion = 1;
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		System.out.println("validarPermiso");
		
		try {
			
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("validarPermiso" + pb.getPlabolperido()));
			posicion = 1;

			st.setLong(posicion++, pb.getPlabolcodigoest());
			rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return false;
		
	}
	
	
	
	
	
	/**
	 * @function:
	 * @param db
	 * @return
	 */
	public DatosBoletinVO datosConv(DatosBoletinVO db) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		try {
			if (db != null) {
				cn = cursor.getConnection();
				pst = cn.prepareStatement(rb.getString("getDatosConvenciones"));
				posicion = 1;
				pst.setLong(1, db.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					db.setDABOLCONVINST(rs.getString(1));
					db.setDABOLCONVMEN(rs.getString(2));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// ponerReporteMensaje("2",modulo,usuarioBol,rb3.getString("boletines.PathBoletines")+nombreBol+"","zip",""+nombreBol,"ReporteActualizarBoletinGenerando","Ocurrio excepcinn SQL:_"+e);
			// updateDatosBoletin("2",nombreBol,"0",usuarioBol);
			// updateDatosBoletinFechaFin(new
			// java.sql.Timestamp(System.currentTimeMillis()).toString(),"2",nombreBol,usuarioBol);
			// updateReporte(nombreBol,usuarioBol,"2");
			// //siges.util.Logger.print(usuarioBol,"SQLExcepcinn al generar el boletin:_Institucion:_"+filtro.getInsitucion()+"_Usuario:_"+usuarioBol+"_NombreBoletin:_"+nombreBol+"",3,1,this.toString());
			// limpiarTablas(consecBol);
			return db;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return db;
	}// fin de preparedstatements
	
	
	
	
	
	/**
	 * @function:
	 * @return
	 * @throws Exception
	 */
	public DatosBoletinVO getBoletin(long consecutivo) throws Exception {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		int posicion = 1;
		DatosBoletinVO db = null;
		
		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString("boletin_a_generar"));
			posicion = 1;
			pst.setLong(posicion++, consecutivo);
			rs = pst.executeQuery();

			while (rs.next()) {
				int pos = 1;
				db = new DatosBoletinVO();
				db.setDABOLINST(rs.getLong(pos++));
				db.setDABOLSEDE(rs.getLong(pos++));
				db.setDABOLJORNADA(rs.getLong(pos++));
				db.setDABOLMETODOLOGIA(rs.getLong(pos++));
				db.setDABOLGRADO(rs.getLong(pos++));
				db.setDABOLGRUPO(rs.getLong(pos++));
				db.setDABOLPERIODO(rs.getLong(pos++));
				db.setDABOLPERIODONOM(rs.getString(pos++));
				db.setDABOLCEDULA(rs.getString(pos++));
				db.setDABOLFECHA(rs.getString(pos++));
				db.setDABOLUSUARIO(rs.getString(pos++));
				db.setDABOLNOMBREZIP(rs.getString(pos++));
				// System.out.println("db.setDABOLNOMBREZIP " +
				// db.getDABOLNOMBREZIP());
				db.setDABOLNOMBREPDF(rs.getString(pos++));
				db.setDABOLNOMBREPDFPRE(rs.getString(pos++));
				db.setDABOLDESCRIPTORES(rs.getInt(pos++));
				db.setDABOLLOGROS(rs.getInt(pos++));
				db.setDABOLTOTLOGROS(rs.getInt(pos++));
				db.setDABOLAREA(rs.getInt(pos++));
				db.setDABOLASIG(rs.getInt(pos++));
				db.setDABOLCONSEC(rs.getLong(pos++));
				db.setDABOLINSNOMBRE(rs.getString(pos++));
				db.setDABOLSEDNOMBRE(rs.getString(pos++));
				db.setDABOLJORNOMBRE(rs.getString(pos++));
				// db.setDABOLJORNOMBRE(rs.getString(pos++));
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return db;
	}
	
	
	
	
	
	public DatosBoletinVO getBoletin2(long consecutivo) throws Exception {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		DatosBoletinVO db = null;
		try {
			con = cursor.getConnection();
			
			pst = con.prepareStatement(rb.getString("solicitar_reprocesamiento"));
			posicion = 1;
			pst.setLong(posicion++, consecutivo);
			int registrosAfectados = pst.executeUpdate();
			con.commit();
			
			pst.clearParameters();
			rs = null;
			pst = con.prepareStatement(rb.getString("boletin_a_generar2"));
			posicion = 1;
			pst.setLong(posicion++, consecutivo);
			rs = pst.executeQuery();

			while (rs.next()) {
				int pos = 1;
				db = new DatosBoletinVO();
				db.setDABOLINST(rs.getLong(pos++));
				db.setDABOLSEDE(rs.getLong(pos++));
				db.setDABOLJORNADA(rs.getLong(pos++));
				db.setDABOLMETODOLOGIA(rs.getLong(pos++));
				db.setDABOLGRADO(rs.getLong(pos++));
				db.setDABOLGRUPO(rs.getLong(pos++));
				db.setDABOLPERIODO(rs.getLong(pos++));
				db.setDABOLPERIODONOM(rs.getString(pos++));
				db.setDABOLCEDULA(rs.getString(pos++));
				db.setDABOLFECHA(rs.getString(pos++));
				db.setDABOLUSUARIO(rs.getString(pos++));
				db.setDABOLNOMBREZIP(rs.getString(pos++));
				// System.out.println("db.setDABOLNOMBREZIP " +
				// db.getDABOLNOMBREZIP());
				db.setDABOLNOMBREPDF(rs.getString(pos++));
				db.setDABOLNOMBREPDFPRE(rs.getString(pos++));
				db.setDABOLDESCRIPTORES(rs.getInt(pos++));
				db.setDABOLLOGROS(rs.getInt(pos++));
				db.setDABOLTOTLOGROS(rs.getInt(pos++));
				db.setDABOLAREA(rs.getInt(pos++));
				db.setDABOLASIG(rs.getInt(pos++));
				db.setDABOLCONSEC(rs.getLong(pos++));
				db.setDABOLINSNOMBRE(rs.getString(pos++));
				db.setDABOLSEDNOMBRE(rs.getString(pos++));
				db.setDABOLJORNOMBRE(rs.getString(pos++));
				// db.setDABOLJORNOMBRE(rs.getString(pos++));
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return db;
	}
	
	public ConsultaExterma getConsultaExterna(String pin){

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		ConsultaExterma consultaExterna = null;
		try {
			con = cursor.getConnection();
			
			String tipo = pin.substring(0, 3);
			long consecutivo =  Long.parseLong(pin.substring(3));
			
			pst = con.prepareStatement(rb.getString("getConsultaExterna"));
			posicion = 1;
			pst.setLong(posicion++, consecutivo);
			pst.setString(posicion++, tipo);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				int pos = 1;
				consultaExterna = new ConsultaExterma();
				consultaExterna.setConsultaExternaTipo(rs.getString(pos++));
				consultaExterna.setConsultaExternaConsecutivo(rs.getLong(pos++));
				consultaExterna.setConsultaExternaRutaArchivo(rs.getString(pos++));
				consultaExterna.setConsultaExternaNombreArchivo(rs.getString(pos++));
				consultaExterna.setConsultaExternaExtensionArchivo(rs.getString(pos++));
			}

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
		return consultaExterna;
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablasAsig(long usuarioid) {
		// System.out.println("limpiarTablas");
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN.pk_bol_borrar_tablas(?)}");
			cstmt.setLong(posicion++, usuarioid);
			cstmt.executeUpdate();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @function:
	 * @param usuarioid
	 */
	public void limpiarTablasDim(long usuarioid) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN_DIMENSIONES.PK_BOL_DIM_BORRAR_TABLAS(?)}");
			cstmt.setLong(posicion++, usuarioid);
			cstmt.executeUpdate();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}
	
	
	
	
	public int getTipoEvaPreescolar(Connection cn, PlantillaBoletionPubVO filtroReporte) throws Exception {

		ResultSet rs = null;
		PreparedStatement st = null;
		
		int i = 1;
		int tipoEval = 0;
		long vigencia = 0;
		long codigoNivelEval = 0;
		
		NivelEvalVO nivelEval = new NivelEvalVO();

		try {
			
			cn = cursor.getConnection();
			vigencia = getVigenciaInst(filtroReporte.getPlabolinst());
			
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, filtroReporte.getPlabolinst());
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			rs.close();
			st.close();
			
			
			
			
			
			// TRAER PARAMETROS DE NIVEL EVALUACION
			i = 1;
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
				sql = sql + rb.getString("getTipoEval.nivel");
			
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");
			
			
			st = cn.prepareStatement(sql);
			
			i = 1;
			st.setLong(i++, vigencia);
			st.setLong(i++, filtroReporte.getPlabolinst());

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, filtroReporte.getPlabolsede());
			
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, filtroReporte.getPlaboljornd());
			
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++, filtroReporte.getPlabolmetodo());
			
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado((int) filtroReporte.getPlabolgrado());
				st.setInt(i++, nivelGrado);
			}
			
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, filtroReporte.getPlabolgrado());

			rs = st.executeQuery();
			
			if (rs.next()) {
				tipoEval = rs.getInt("INSNIVTIPOEVALPREES");
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
		
		return tipoEval;
		
	}
	
	
}

