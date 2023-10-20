package siges.adminParamsInst.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.apache.commons.validator.GenericValidator;

import siges.adminParamsInst.vo.EscalaConceptualVO;
import siges.adminParamsInst.vo.EscalaNumericaVO;
import siges.adminParamsInst.vo.FiltroEscalaNumVO;
import siges.adminParamsInst.vo.FiltroEscalaVO;
import siges.adminParamsInst.vo.FiltroNivelEvalVO;
import siges.adminParamsInst.vo.InstNivelEvaluacionVO;
import siges.adminParamsInst.vo.InstParVO;
import siges.adminParamsInst.vo.ParamsVO;
import siges.adminParamsInst.vo.PeriodoPrgfechasVO;
import siges.adminParamsInst.vo.PonderacionPorPeriodoVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO;
import siges.gestionAdministrativa.enviarMensajes.vo.PersonalVO;
import siges.login.beans.Login;
import siges.adminParamsInst.vo.TipoEvaluacionInstAsigVO;

/**
 * Objeto de acceso a datos para el mndulo de aprobacinn del POA. 26/01/2010
 * 
 * @autho r Athenea TA
 * @version 1.1
 */
public class AdminParametroInstDAO extends Dao {
	
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	private Logger logger = Logger.getLogger(AdminParametroInstDAO.class.getName());

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto de obtencinn de conexiones a la base de datos
	 */
	public AdminParametroInstDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("siges.adminParamsInst.bundle.AdminParamsInst");
	}

	public Cursor getCursor() throws Exception {
		return cursor;
	}

	/**
	 * Calcula la lista de numero de periodos
	 * 
	 * @return Lista de Nnmero de periodos
	 * @throws Exception
	 */
	public List getNumeroPeriodos() throws Exception {

		List list = new ArrayList();
		ItemVO itemVO = null;
		try {

			for (int i = 1; i <= ParamsVO.NUM_PERIODOS; i++) {
				itemVO = new ItemVO();
				itemVO.setCodigo(i);
				itemVO.setNombre(i + "");
				list.add(itemVO);
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		return list;
	}

	/**
	 * Calcula la lista de tipos de periodo
	 * 
	 * @return Lista de tipos de periodo
	 * @throws Exception
	 */
	public List getTipoPeriodos() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("periodo.getTipoPeriodos"));
			st.setInt(posicion++, ParamsVO.CONST_TIPO_PERIODOS);
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
	 * Calcula la lista de tipos de periodo
	 * 
	 * @return Lista de tipos de periodo
	 * @throws Exception
	 */
	public List getTipoCombinacion() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("periodo.getTipoCombinacion"));
			// st.setInt(posicion++,ParamsVO.CONST_TIPO_COMBINACION);
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
	 * Obtiene datos del periodo
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            codigo de la institucinn
	 * @return datos del periodo
	 * @throws Exception
	 */
	public InstParVO getPeriodoInstActual(long vigencia, long institucion)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = new InstParVO();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("periodo.getPeriodoInstActual"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, institucion);
			rs = st.executeQuery();

			instParVO.setInsparvigencia(vigencia);
			instParVO.setInsparcodinst(institucion);

			if (rs.next()) {
				posicion = 1;
				instParVO.setInsparnumper(rs.getLong(posicion++));
				instParVO.setInspartipper(rs.getLong(posicion++));
				instParVO.setInsparnomperdef(rs.getString(posicion++));
				instParVO.setInsparniveval(rs.getLong(posicion++));
				instParVO.setInsparnivevalAntes(instParVO.getInsparniveval());
				instParVO.setInsparsubtitbol(rs.getString(posicion++));
				instParVO.setPorcentajeperdida(rs.getDouble(posicion++));
				instParVO.setInsparevaldescriptores(rs.getString(posicion++));
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
		// System.out.println("instParVO.setInsparvigenci "
		// +instParVO.getInsparvigencia());

		return instParVO;
	}

	/**
	 * @param instParVO
	 * @return
	 * @throws Exception
	 */
	public InstParVO guardarPeriodoInstActual(InstParVO instParVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("periodo.guardarPeriodoInstActual"));
			posicion = 1;
			st.setLong(posicion++, instParVO.getInsparvigencia());
			st.setLong(posicion++, instParVO.getInsparcodinst());
			st.setLong(posicion++, instParVO.getInsparnumper());
			st.setLong(posicion++, instParVO.getInspartipper());
			st.setString(posicion++, instParVO.getInsparnomperdef());
			st.setLong(posicion++, instParVO.getInsparniveval());
			st.setString(posicion++, instParVO.getInsparsubtitbol());
			st.setDouble(posicion++, instParVO.getPorcentajeperdida());
			st.setString(posicion++, instParVO.getInsparevaldescriptores());
			
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return instParVO;
	}

	/**
	 * @param instParVO
	 * @return
	 * @throws Exception
	 */
	public InstParVO actualizarPeriodoInstActual(InstParVO instParVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("periodo.actualizarPeriodoInstActual"));
			posicion = 1;

			st.setLong(posicion++, instParVO.getInsparnumper());
			st.setLong(posicion++, instParVO.getInspartipper());
			st.setString(posicion++, instParVO.getInsparnomperdef());
			st.setLong(posicion++, instParVO.getInsparniveval());
			st.setString(posicion++, instParVO.getInsparsubtitbol());
			st.setDouble(posicion++, instParVO.getPorcentajeperdida());
			st.setString(posicion++, instParVO.getInsparevaldescriptores());
			// where
			st.setLong(posicion++, instParVO.getInsparvigencia());
			st.setLong(posicion++, instParVO.getInsparcodinst());
            
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return instParVO;
	}

	/**
	 * @param vigencia
	 * @param institucion
	 * @return
	 * @throws Exception
	 */
	public boolean isExistePeriodoRelacionada(InstParVO filtro)
			throws Exception {
		// System.out.println("isExistePeriodoRelacionada ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("periodo.isExistePeriodoRelacionada1"));
			posicion = 1;

			st.setLong(posicion++, filtro.getInsparvigencia());
			st.setLong(posicion++, filtro.getInsparcodinst());
			st.setLong(posicion++, filtro.getInsparniveval());

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				// System.out.println("true 1");
				return true;
			}
			rs.close();
			st.close();
			//

			st = cn.prepareStatement(rb
					.getString("periodo.isExistePeriodoRelacionada2"));
			posicion = 1;

			st.setLong(posicion++, filtro.getInsparvigencia());
			st.setLong(posicion++, filtro.getInsparcodinst());

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				// System.out.println("true 2");
				return true;
			}
			//
			rs.close();
			st.close();

			st = cn.prepareStatement(rb
					.getString("periodo.isExistePeriodoRelacionada3"));
			posicion = 1;

			st.setLong(posicion++, filtro.getInsparvigencia());
			st.setLong(posicion++, filtro.getInsparcodinst());

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				// System.out.println("true 3");
				return true;
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
		return false;
	}

	/**
	 * @param vigencia
	 * @param institucion
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteNivelEval(long vigencia, long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("periodo.isExistePeriodoRelacionada1.1"));
			posicion = 1;

			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, inst);

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				// System.out.println("isExisteNivelEval_ si ");

				return true;
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
		return false;
	}

	/**
	 * @param vigencia
	 * @param institucion
	 * @return
	 * @throws Exception
	 */
	public boolean isExistePeriodo(long vigencia, long institucion)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("periodo.isExistePeriodo"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, institucion);
			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
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
			}
		}
		return false;
	}

	/**
	 * Calcula la lista de todos los periodos
	 * 
	 * @return Lista de periodos
	 * @throws Exception
	 */
	public List getAllPeriodoInst(String institucion) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("periodo.getAllPeriodoInst"));
			posicion = 1;
			st.setLong(posicion++, Long.valueOf(institucion).longValue());
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				instParVO = new InstParVO();
				instParVO.setInsparvigencia(rs.getLong(posicion++));
				instParVO.setInsparcodinst(rs.getLong(posicion++));
				instParVO.setInsparnumper(rs.getLong(posicion++));
				instParVO.setInspartipper(rs.getLong(posicion++));
				instParVO.setInspartipperNom(rs.getString(posicion++));
				instParVO.setInsparnomperdef(rs.getString(posicion++));
				instParVO.setInsparniveval(rs.getLong(posicion++));
				instParVO.setInsparcodcombNom(rs.getString(posicion++));
				list.add(instParVO);
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

	// NIVEL EVALUACION

	/**
	 * @return
	 * @throws Exception
	 */
	public List getNivelEvaluacion() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("nivelEval.getNivelEvaluacion"));
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				InstNivelEvaluacionVO n = new InstNivelEvaluacionVO();

				list.add(instParVO);
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
	 * @param instParVO
	 * @return
	 * @throws Exception
	 */
	public boolean isExiteNivelEvaluacion(
			InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			// instNivelEval.isExiteNivelEvaluacion
			sql += " " + rb.getString("instNivelEval.isExiteNivelEvaluacion");
			// metodo
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);

			posicion = 1;
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++,
					instNivelEvaluacionVO.getInsnivcodniveleval());
			/*
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivcodmetod());
			 * st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			 * st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivcodnivel());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivcodgrado());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivtipoevalasig());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivtipoevalprees());
			 * st.setDouble(posicion++,
			 * instNivelEvaluacionVO.getInsnivvalminnum());
			 * st.setDouble(posicion++,
			 * instNivelEvaluacionVO.getInsnivvalmaxnum());
			 * st.setDouble(posicion++,
			 * instNivelEvaluacionVO.getInsnivvalaprobnum());
			 */

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodmetod());
			}

			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			}

			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			}

			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodnivel());
			}

			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodgrado());
			}

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
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
			}
		}
		return false;
	}

	/**
	 * @param instParVO
	 * @return
	 * @throws Exception
	 */
	public InstNivelEvaluacionVO actualizarNivelEvaluacion(InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		String sql = "";
		int posicion = 0;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			sql += " " + rb.getString("instNivelEval.actualizarNivelEvaluacionv2");
			
			// Método
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodmetod() > -99) ? rb.getString("instNivelEval.METODO") : rb.getString("instNivelEval.METODO_IS_NULL"));

			// Sede
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodsede() > -99) ? rb.getString("instNivelEval.SEDE") : rb.getString("instNivelEval.SEDE_IS_NULL"));

			// Jornada
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodjorn() > -99) ? rb.getString("instNivelEval.JORND") : rb.getString("instNivelEval.JORND_IS_NULL"));

			// Nivel
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodnivel() > -99) ? rb.getString("instNivelEval.NIVEL") : rb.getString("instNivelEval.NIVEL_IS_NULL"));

			// Grado
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodgrado() > -99) ? rb.getString("instNivelEval.GRADO") : rb.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);

			posicion = 1;
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivtipoevalasig());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivtipoevalprees());
			st.setDouble(posicion++, getDouble(truncar(convertir(instNivelEvaluacionVO.getInsnivvalminnum()))));
			st.setDouble(posicion++, getDouble(truncar(convertir(instNivelEvaluacionVO.getInsnivvalmaxnum()))));
			st.setDouble(posicion++, getDouble(truncar(convertir(instNivelEvaluacionVO.getInsnivvalaprobnum()))));
			
			if (instNivelEvaluacionVO.getInsnivmodoeval() > 0) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivmodoeval());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivmodoevallogro());
			
			// WHERE
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodniveleval());

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodmetod());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodnivel());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodgrado());
			}

			int i = st.executeUpdate();
			
			if (i == 0) {
				throw new Exception("No se actualizo la informacion");
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return instNivelEvaluacionVO;
		
	}

	/**
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public InstNivelEvaluacionVO guardarNivelEvaluacion(InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		String sql = "";
		int posicion = 0;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			sql += " " + rb.getString("instNivelEval.guardarNivelEvaluacionv2");

			st = cn.prepareStatement(sql);
			
			posicion = 1;
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodniveleval());

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodmetod());
			} else {
				st.setNull(posicion++, Types.INTEGER);
			}
			
			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			} else {
				st.setNull(posicion++, Types.INTEGER);
			}
			
			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			} else {
				st.setNull(posicion++, Types.INTEGER);
			}
			
			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodnivel());
			} else {
				st.setNull(posicion++, Types.INTEGER);
			}
			
			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodgrado());
			} else {
				st.setNull(posicion++, Types.INTEGER);
			}

			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivtipoevalasig());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivtipoevalprees());
			st.setDouble(posicion++, getDouble(truncar(convertir(instNivelEvaluacionVO.getInsnivvalminnum()))));
			st.setDouble(posicion++, getDouble(truncar(convertir(instNivelEvaluacionVO.getInsnivvalmaxnum()))));
			st.setDouble(posicion++, getDouble(truncar(convertir(instNivelEvaluacionVO.getInsnivvalaprobnum()))));
			
			if (instNivelEvaluacionVO.getInsnivmodoeval() > 0) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivmodoeval());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivmodoevallogro());
			
			st.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return instNivelEvaluacionVO;
		
	}

	/**
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public boolean isUtilizadoNivelEval(
			InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {
		// System.out.println("entro a isUtilizadoNivelEval");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		String sql = "";
		try {
			cn = cursor.getConnection();

			// Validacion en ESCALA NUMERICA
			sql += " " + rb.getString("instNivelEval.isUtilizadoNivelEval.1");
			// metodo
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			// System.out.println("isUtilizadoNivelEval = sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			// System.out.println("instNivelEvaluacionVO.getInsnivvigencia() " +
			// instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			// System.out.println("instNivelEvaluacionVO.getInsnivcodinst() "
			// +instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++,
					instNivelEvaluacionVO.getInsnivcodniveleval());
			// System.out.println("instNivelEvaluacionVO.getInsnivcodniveleval() "
			// + instNivelEvaluacionVO.getInsnivcodniveleval());

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodmetod());
			}
			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			}
			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			}
			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodnivel());
			}
			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodgrado());
			}

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {

				return true;
			}

			rs.close();
			st.close();

			// Validacion en ESCALA NUMERICA
			sql = " " + rb.getString("instNivelEval.isUtilizadoNivelEval.2");
			// metodo
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			// System.out.println("isUtilizadoNivelEval = 	sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			// System.out.println("instNivelEvaluacionVO.getInsnivvigencia() " +
			// instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			// System.out.println("instNivelEvaluacionVO.getInsnivcodinst() "
			// +instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++,
					instNivelEvaluacionVO.getInsnivcodniveleval());
			// System.out.println("instNivelEvaluacionVO.getInsnivcodniveleval() "
			// + instNivelEvaluacionVO.getInsnivcodniveleval());

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodmetod());
			}

			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			}
			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			}
			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodnivel());
			}
			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodgrado());
			}

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {

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
			}
		}
		// System.out.println("return false");
		return false;
	}

	/**
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public InstNivelEvaluacionVO obtenerInstNivelEval(InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {
		
		String sql = "";
		int posicion = 0;
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();

			sql += " " + rb.getString("instNivelEval.obtenerInstNivelEvalv2");
			
			// metodo
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodmetod() > -99) ? rb.getString("instNivelEval.METODO") : rb.getString("instNivelEval.METODO_IS_NULL"));

			// sede
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodsede() > -99) ? rb.getString("instNivelEval.SEDE") : rb.getString("instNivelEval.SEDE_IS_NULL"));

			// jorn
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodjorn() > -99) ? rb.getString("instNivelEval.JORND") : rb.getString("instNivelEval.JORND_IS_NULL"));

			// nivel
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodnivel() > -99) ? rb.getString("instNivelEval.NIVEL") : rb.getString("instNivelEval.NIVEL_IS_NULL"));

			// grado
			sql += " " + ((instNivelEvaluacionVO.getInsnivcodgrado() > -99) ? rb.getString("instNivelEval.GRADO") : rb.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodniveleval());

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodmetod());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodnivel());
			}
			
			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodgrado());
			}
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				posicion = 1;
				instNivelEvaluacionVO.setInsnivvigencia(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodinst(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodniveleval(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodmetod(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodsede(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodjorn(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodnivel(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodgrado(rs.getLong(posicion++));
				// where
				instNivelEvaluacionVO.setInsnivtipoevalasig(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivtipoevalasigAntes(instNivelEvaluacionVO.getInsnivtipoevalasig());

				instNivelEvaluacionVO.setInsnivtipoevalprees(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivtipoevalpreesAntes(instNivelEvaluacionVO.getInsnivtipoevalprees());

				instNivelEvaluacionVO.setInsnivvalminnum(convertir(getDouble(truncar(rs.getDouble(posicion++)))));
				instNivelEvaluacionVO.setInsnivvalminnumAntes(instNivelEvaluacionVO.getInsnivvalminnum());
				instNivelEvaluacionVO.setInsnivvalmaxnum(convertir(getDouble(truncar(rs.getDouble(posicion++)))));
				instNivelEvaluacionVO.setInsnivvalmaxnumAntes(instNivelEvaluacionVO.getInsnivvalmaxnum());

				instNivelEvaluacionVO.setInsnivvalaprobnum(convertir(getDouble(truncar(rs.getDouble(posicion++)))));
				instNivelEvaluacionVO.setInsnivmodoeval(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivmodoevalAntes(instNivelEvaluacionVO.getInsnivmodoeval());

				instNivelEvaluacionVO.setEvalasignatura(rs.getString(posicion++));
				
				instNivelEvaluacionVO.setEdicion(true);
				
				instNivelEvaluacionVO.setInsnivmodoevallogro(rs.getLong(posicion++));
				
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
		
		return instNivelEvaluacionVO;
		
	}

	/**
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public InstNivelEvaluacionVO eliminarInstNivelEval(
			InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		String sql = "";
		try {
			cn = cursor.getConnection();

			sql += " " + rb.getString("instNivelEval.eliminarInstNivelEval");
			// metodo
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			// System.out.println("instNivelEvaluacionVO.getInsnivvigencia() " +
			// instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			// System.out.println("instNivelEvaluacionVO.getInsnivcodinst() "
			// +instNivelEvaluacionVO.getInsnivcodinst());
			// System.out.println("instNivelEvaluacionVO.getInsnivcodniveleval() "
			// + instNivelEvaluacionVO.getInsnivcodniveleval());
			st.setLong(posicion++,
					instNivelEvaluacionVO.getInsnivcodniveleval());

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodmetod());
			}
			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			}
			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			}
			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodnivel());
			}
			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodgrado());
			}

			st.executeUpdate();
			/*
			 * if ( rs.next() ) {
			 * 
			 * posicion=1;
			 * instNivelEvaluacionVO.setInsnivvigencia(rs.getLong(posicion++));
			 * instNivelEvaluacionVO.setInsnivcodinst(rs.getLong(posicion++));
			 * instNivelEvaluacionVO
			 * .setInsnivcodniveleval(rs.getLong(posicion++));
			 * 
			 * instNivelEvaluacionVO.setInsnivcodmetod(rs.getLong(posicion++));
			 * instNivelEvaluacionVO.setInsnivcodsede(rs.getLong(posicion++));
			 * instNivelEvaluacionVO.setInsnivcodjorn(rs.getLong(posicion++));
			 * instNivelEvaluacionVO.setInsnivcodnivel(rs.getLong(posicion++));
			 * instNivelEvaluacionVO.setInsnivcodgrado(rs.getLong(posicion++));
			 * //where
			 * instNivelEvaluacionVO.setInsnivtipoevalasig(rs.getLong(posicion
			 * ++));
			 * instNivelEvaluacionVO.setInsnivtipoevalprees(rs.getLong(posicion
			 * ++));
			 * instNivelEvaluacionVO.setInsnivvalminnum(rs.getDouble(posicion
			 * ++));
			 * instNivelEvaluacionVO.setInsnivvalmaxnum(rs.getDouble(posicion
			 * ++));
			 * instNivelEvaluacionVO.setInsnivvalaprobnum(rs.getDouble(posicion
			 * ++)); }
			 */
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
		return instNivelEvaluacionVO;
	}

	/**
	 * @param filtroEscalaNumVO
	 * @return
	 * @throws Exception
	 */
	public List getListaNivelEval(FiltroNivelEvalVO filtroNivelEvalVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			// System.out.println(rb.getString("instNivelEval.getListaNivelEval.1")+" "+rb.getString("instNivelEval.getListaNivelEval.2"));
			st = cn.prepareStatement(rb
					.getString("instNivelEval.getListaNivelEval.1v2")
					+ " "
					+ rb.getString("instNivelEval.getListaNivelEval.2"));
			posicion = 1;
			String sql = rb.getString("instNivelEval.getListaNivelEval.1v2")
					+ " " + rb.getString("instNivelEval.getListaNivelEval.2");

			st.setLong(posicion++, filtroNivelEvalVO.getFilVigencia());
			st.setLong(posicion++, filtroNivelEvalVO.getFilInst());
			st.setLong(posicion++, filtroNivelEvalVO.getFilniveval());

			sql += filtroNivelEvalVO.getFilVigencia() + " "
					+ filtroNivelEvalVO.getFilInst() + " "
					+ filtroNivelEvalVO.getFilniveval();

			/*
			 * System.out.println("posicion " + posicion);
			 * System.out.println("iltroNivelEvalVO.getFilMetodo() " +
			 * filtroNivelEvalVO.getFilMetodo());
			 * System.out.println("filtroNivelEvalVO.getFilSede() " +
			 * filtroNivelEvalVO.getFilSede());
			 * System.out.println("filtroNivelEvalVO.getFilJorn() " +
			 * filtroNivelEvalVO.getFilJorn());
			 * System.out.println("filtroNivelEvalVO.getFilNivel() " +
			 * filtroNivelEvalVO.getFilNivel());
			 * System.out.println("filtroNivelEvalVO.getFilGrado() " +
			 * filtroNivelEvalVO.getFilGrado());
			 */
			// Metodologia
			if (filtroNivelEvalVO.getFilMetodo() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroNivelEvalVO.getFilMetodo());
			}
			st.setLong(posicion++, filtroNivelEvalVO.getFilMetodo());

			// Sede
			if (filtroNivelEvalVO.getFilSede() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroNivelEvalVO.getFilSede());
			}

			st.setLong(posicion++, filtroNivelEvalVO.getFilSede());

			// Jornada
			if (filtroNivelEvalVO.getFilJorn() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroNivelEvalVO.getFilJorn());
			}

			st.setLong(posicion++, filtroNivelEvalVO.getFilJorn());

			// Nivel
			if (filtroNivelEvalVO.getFilNivel() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroNivelEvalVO.getFilNivel());
			}
			st.setLong(posicion++, filtroNivelEvalVO.getFilNivel());

			// Grado
			if (filtroNivelEvalVO.getFilGrado() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroNivelEvalVO.getFilGrado());
			}
			st.setLong(posicion++, filtroNivelEvalVO.getFilGrado());

			rs = st.executeQuery();

			sql += filtroNivelEvalVO.getFilMetodo() + " "
					+ filtroNivelEvalVO.getFilSede() + " "
					+ filtroNivelEvalVO.getFilJorn() + " ";
			sql += filtroNivelEvalVO.getFilNivel() + " "
					+ filtroNivelEvalVO.getFilGrado();

			while (rs.next()) {
				posicion = 1;
				InstNivelEvaluacionVO instNivelEvaluacionVO = new InstNivelEvaluacionVO();

				instNivelEvaluacionVO.setInsnivvigencia(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodinst(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodniveleval(rs
						.getLong(posicion++));

				instNivelEvaluacionVO.setInsnivcodmetod(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodsede(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodjorn(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodnivel(rs.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivcodgrado(rs.getLong(posicion++));
				// where
				instNivelEvaluacionVO.setInsnivtipoevalasig(rs
						.getLong(posicion++));
				instNivelEvaluacionVO.setInsnivtipoevalprees(rs
						.getLong(posicion++));
				instNivelEvaluacionVO
						.setInsnivtipoevalpreesAntes(instNivelEvaluacionVO
								.getInsnivtipoevalprees());
				instNivelEvaluacionVO
						.setInsnivvalminnum(convertir(getDouble(truncar(rs
								.getDouble(posicion++)))));
				instNivelEvaluacionVO
						.setInsnivvalmaxnum(convertir(getDouble(truncar(rs
								.getDouble(posicion++)))));
				instNivelEvaluacionVO
						.setInsnivvalaprobnum(convertir(getDouble(truncar(rs
								.getDouble(posicion++)))));
				
				instNivelEvaluacionVO.setInsnivmodoeval(rs.getLong(posicion++));
				instNivelEvaluacionVO
						.setInsnivmodoevalAntes(instNivelEvaluacionVO
								.getInsnivmodoeval());
				list.add(instNivelEvaluacionVO);
				
				//instNivelEvaluacionVO.setEvalperiodo(rs.getString(posicion++));
				instNivelEvaluacionVO.setEvalasignatura(rs.getString(posicion++));
				
			
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

	// ESCALA CONCEPTUAL

	/**
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isCambioLireral(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("escalaConcep.isCambioLireral"));
			posicion = 1;
			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconniveval());
			st.setString(posicion++, escalaConceptualVO.getInsconliteral());

			rs = st.executeQuery();
			if (rs.next()) {
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
			}
		}
		return false;
	}

	/**
	 * Valida que el literal no exista
	 * 
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteValorNumerico(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		try {

			// System.out.println("isExisteNombreLiteral ------------------------------");
			FiltroEscalaVO filtroEscalaVO = new FiltroEscalaVO();
			filtroEscalaVO.setFilInst(escalaConceptualVO.getInsconcodinst());
			filtroEscalaVO.setFilVigencia(escalaConceptualVO
					.getInsconvigencia());
			filtroEscalaVO.setFilniveval(escalaConceptualVO.getInsconniveval());

			filtroEscalaVO.setFilSede(escalaConceptualVO.getInsconcodsede());
			filtroEscalaVO.setFilJorn(escalaConceptualVO.getInsconcodjorn());
			filtroEscalaVO.setFilMetodo(escalaConceptualVO.getInsconcodmetod());
			filtroEscalaVO.setFilNivel(escalaConceptualVO.getInsconcodnivel());
			filtroEscalaVO.setFilGrado(escalaConceptualVO.getInsconcodgrado());

			escalaConceptualVO.getInsconcodigo();
			List lista = getListaEscalaConcep(filtroEscalaVO);

			for (int i = 0; i < lista.size(); i++) {
				EscalaConceptualVO n = new EscalaConceptualVO();
				n = (EscalaConceptualVO) lista.get(i);
				// System.out.println(n.getInsconcodigo() +" = " +
				// escalaConceptualVO.getInsconcodigo() );

				if (n.getInsconcodigo() != escalaConceptualVO.getInsconcodigo()) {
					// System.out.println(n.getInsconliteral() + " = " +
					// escalaConceptualVO.getInsconliteral());
					if (n.getInsconvalnum() == escalaConceptualVO
							.getInsconvalnum()) {
						// System.out.println("true ");
						return true;
					} else {
						// System.out.println("no iguales");
					}
				}
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		// System.out.println("false");
		return false;
	}

	/**
	 * Valida que todas las escalas del MEN halla sigo utilizadas
	 * 
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String isAsignarEquiMEN(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		try {

			// System.out.println("isExisteNombreLiteral ------------------------------");
			FiltroEscalaVO filtroEscalaVO = new FiltroEscalaVO();
			filtroEscalaVO.setFilInst(escalaConceptualVO.getInsconcodinst());
			filtroEscalaVO.setFilVigencia(escalaConceptualVO
					.getInsconvigencia());
			filtroEscalaVO.setFilniveval(escalaConceptualVO.getInsconniveval());

			filtroEscalaVO.setFilSede(escalaConceptualVO.getInsconcodsede());
			filtroEscalaVO.setFilJorn(escalaConceptualVO.getInsconcodjorn());
			filtroEscalaVO.setFilMetodo(escalaConceptualVO.getInsconcodmetod());
			filtroEscalaVO.setFilNivel(escalaConceptualVO.getInsconcodnivel());
			filtroEscalaVO.setFilGrado(escalaConceptualVO.getInsconcodgrado());

			escalaConceptualVO.getInsconcodigo();
			List lista = getListaEscalaConcep(filtroEscalaVO);

			// if(lista.size() < getEscalaMEN().size() ){
			// actualizarNivelEvaluacionRango(escalaConceptualVO,0);
			// return
			// " Pero falta asignar rangos con la equivalencia de la escala MEN.";
			// }
			actualizarNivelEvaluacionRango(escalaConceptualVO, 1);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		// System.out.println("false");
		return null;
	}

	/**
	 * Valida que el literal no exista
	 * 
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteEquiMEN(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		try {

			// System.out.println("isExisteNombreLiteral ------------------------------");
			FiltroEscalaVO filtroEscalaVO = new FiltroEscalaVO();
			filtroEscalaVO.setFilInst(escalaConceptualVO.getInsconcodinst());
			filtroEscalaVO.setFilVigencia(escalaConceptualVO
					.getInsconvigencia());
			filtroEscalaVO.setFilniveval(escalaConceptualVO.getInsconniveval());

			filtroEscalaVO.setFilSede(escalaConceptualVO.getInsconcodsede());
			filtroEscalaVO.setFilJorn(escalaConceptualVO.getInsconcodjorn());
			filtroEscalaVO.setFilMetodo(escalaConceptualVO.getInsconcodmetod());
			filtroEscalaVO.setFilNivel(escalaConceptualVO.getInsconcodnivel());
			filtroEscalaVO.setFilGrado(escalaConceptualVO.getInsconcodgrado());

			escalaConceptualVO.getInsconcodigo();
			List lista = getListaEscalaConcep(filtroEscalaVO);

			for (int i = 0; i < lista.size(); i++) {
				EscalaConceptualVO n = new EscalaConceptualVO();
				n = (EscalaConceptualVO) lista.get(i);
				// System.out.println(n.getInsconcodigo() +" = " +
				// escalaConceptualVO.getInsconcodigo() );

				if (n.getInsconcodigo() != escalaConceptualVO.getInsconcodigo()) {
					// System.out.println(n.getInsconliteral() + " = " +
					// escalaConceptualVO.getInsconliteral());
					if (n.getInsconequmen() == escalaConceptualVO
							.getInsconequmen()) {
						// System.out.println("true ");
						return true;
					} else {
						// System.out.println("no iguales");
					}
				}
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		// System.out.println("false");
		return false;
	}

	/**
	 * @function: Valida si existe el Nivel de Evaluacio Institucion para porder
	 *            registrar la escala conceptual o numnrica/porcentual
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String isExisteNivelEval_(EscalaConceptualVO escalaConceptualVO,
			int tipoEscala) throws Exception {
		String respuesta = "";
		try {

			InstNivelEvaluacionVO nivelEvaluacionVO = new InstNivelEvaluacionVO();

			nivelEvaluacionVO.setInsnivcodinst(escalaConceptualVO
					.getInsconcodinst());
			nivelEvaluacionVO.setInsnivvigencia(escalaConceptualVO
					.getInsconvigencia());
			nivelEvaluacionVO.setInsnivcodniveleval(escalaConceptualVO
					.getInsconniveval());

			nivelEvaluacionVO.setInsnivcodsede(escalaConceptualVO
					.getInsconcodsede());
			nivelEvaluacionVO.setInsnivcodjorn(escalaConceptualVO
					.getInsconcodjorn());
			nivelEvaluacionVO.setInsnivcodmetod(escalaConceptualVO
					.getInsconcodmetod());
			nivelEvaluacionVO.setInsnivcodnivel(escalaConceptualVO
					.getInsconcodnivel());
			nivelEvaluacionVO.setInsnivcodgrado(escalaConceptualVO
					.getInsconcodgrado());

			// Si es O significa que no a sigo creado el nivel de evaluacion
			// System.out.println("obtenerInstNivelEval( nivelEvaluacionVO).getInsnivtipoevalasig() "
			// + obtenerInstNivelEval(
			// nivelEvaluacionVO).getInsnivtipoevalasig());
			if (obtenerInstNivelEval(nivelEvaluacionVO).getInsnivtipoevalasig() != tipoEscala/*
																							 * ParamsVO
																							 * .
																							 * ESCALA_CONCEPTUAL
																							 */) {

				respuesta += "Antes de registrar esta informaciÃ³n usted debe crear el Nivel de Evaluacinn  de Institucinn  con 'Tipo de Evaluacinn Conceptual' para la combinacinn "
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodsede() > -99 ? " SEDE"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodjorn() > -99 ? " JORNADA"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodmetod() > -99 ? " METODOLOGIA"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodnivel() > -99 ? " NIVEL"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodgrado() > -99 ? " GRADO"
								: "") + " con los siguientes datos:";

				if (nivelEvaluacionVO.getInsnivcodsede() > -99) {
					respuesta += "\n  - Sede: "
							+ getNomSede(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodsede());
				}
				if (nivelEvaluacionVO.getInsnivcodjorn() > -99) {
					respuesta += "\n  - Jornada: "
							+ getNomJod(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodsede(),
									nivelEvaluacionVO.getInsnivcodjorn());
				}

				if (nivelEvaluacionVO.getInsnivcodmetod() > -99) {
					respuesta += "\n  - Metodologia: "
							+ getNomMed(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodsede(),
									nivelEvaluacionVO.getInsnivcodmetod());
				}
				if (nivelEvaluacionVO.getInsnivcodnivel() > -99) {
					respuesta += "\n  - Nivel; "
							+ getNomNivel(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodmetod(),
									nivelEvaluacionVO.getInsnivcodnivel());
				}
				if (nivelEvaluacionVO.getInsnivcodgrado() > -99) {
					respuesta += "\n  - Grado: "
							+ getNomGrad(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodmetod(),
									nivelEvaluacionVO.getInsnivcodnivel(),
									nivelEvaluacionVO.getInsnivcodgrado());
				}

				return respuesta;
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		return respuesta;
	}

	/**
	 * @function: Valida si existe el Nivel de Evaluacio Institucion para porder
	 *            registrar la escala conceptual o numnrica/porcentual
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String isExisteNivelEval_(EscalaNumericaVO escalaNumericaVO,
			int tipoEscala, int tipoEscala2, String msjTipo) throws Exception {
		String respuesta = "";
		try {

			InstNivelEvaluacionVO nivelEvaluacionVO = new InstNivelEvaluacionVO();

			nivelEvaluacionVO.setInsnivcodinst(escalaNumericaVO
					.getInsnumcodinst());
			nivelEvaluacionVO.setInsnivvigencia(escalaNumericaVO
					.getInsnumvigencia());
			nivelEvaluacionVO.setInsnivcodniveleval(escalaNumericaVO
					.getInsnumniveval());

			nivelEvaluacionVO.setInsnivcodsede(escalaNumericaVO
					.getInsnumcodsede());
			nivelEvaluacionVO.setInsnivcodjorn(escalaNumericaVO
					.getInsnumcodjorn());
			nivelEvaluacionVO.setInsnivcodmetod(escalaNumericaVO
					.getInsnumcodmetod());
			nivelEvaluacionVO.setInsnivcodnivel(escalaNumericaVO
					.getInsnumcodnivel());
			nivelEvaluacionVO.setInsnivcodgrado(escalaNumericaVO
					.getInsnumcodgrado());

			// System.out.println("ENVA VALIDAR ---------------------");
			if (obtenerInstNivelEval(nivelEvaluacionVO).getInsnivtipoevalasig() != tipoEscala
					&& obtenerInstNivelEval(nivelEvaluacionVO)
							.getInsnivtipoevalasig() != tipoEscala2) {

				respuesta += "Antes de registrar esta informaciÃ³n usted debe crear el Nivel de Evaluacinn de Institucinn con "
						+ msjTipo
						+ " para la combinacinn "
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodsede() > -99 ? " SEDE"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodjorn() > -99 ? " JORNADA"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodmetod() > -99 ? " METODOLOGIA"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodnivel() > -99 ? " NIVEL"
								: "")
						+ ""
						+ (nivelEvaluacionVO.getInsnivcodgrado() > -99 ? " GRADO"
								: "") + " con los siguientes datos:";

				if (nivelEvaluacionVO.getInsnivcodsede() > -99) {
					respuesta += "\n  - Sede: "
							+ getNomSede(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodsede());
				}
				if (nivelEvaluacionVO.getInsnivcodjorn() > -99) {
					respuesta += "\n  - Jornada: "
							+ getNomJod(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodsede(),
									nivelEvaluacionVO.getInsnivcodjorn());
				}

				if (nivelEvaluacionVO.getInsnivcodmetod() > -99) {
					respuesta += "\n  - Metodologia: "
							+ getNomMed(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodsede(),
									nivelEvaluacionVO.getInsnivcodmetod());
				}
				if (nivelEvaluacionVO.getInsnivcodnivel() > -99) {
					respuesta += "\n  - Nivel; "
							+ getNomNivel(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodmetod(),
									nivelEvaluacionVO.getInsnivcodnivel());
				}
				if (nivelEvaluacionVO.getInsnivcodgrado() > -99) {
					respuesta += "\n  - Grado: "
							+ getNomGrad(nivelEvaluacionVO.getInsnivcodinst(),
									nivelEvaluacionVO.getInsnivcodmetod(),
									nivelEvaluacionVO.getInsnivcodnivel(),
									nivelEvaluacionVO.getInsnivcodgrado());
				}

				return respuesta;
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		return respuesta;
	}

	/**
	 * @function: Metodo que retorna el nombre de la sede segundo su institucion
	 *            y codigo
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String getNomSede(long codInst, long codSede) throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getNomSede"));
			posicion = 1;
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codSede);
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				return rs.getString(posicion);
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
		return "";
	}

	/**
	 * @function: Metodo que retorna el nombre de la jornada segundo su
	 *            institucion y codigo
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String getNomJod(long codInst, long codSede, long codJod)
			throws Exception {
		// System.out.println("getNomJod ");

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getNomJod"));
			posicion = 1;
			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst);
			st.setLong(posicion++, codSede);
			// System.out.println("codSede " + codSede);
			st.setLong(posicion++, codSede);
			st.setLong(posicion++, codJod);
			// System.out.println("codJod " + codJod);
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				return rs.getString(posicion);
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
		return "";
	}

	/**
	 * @function: Metodo que retorna el nombre de la jornada segundo su
	 *            institucion y codigo
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String getNomMed(long codInst, long codSede, long codMed)
			throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getNomMed"));
			posicion = 1;
			st.setLong(posicion++, codInst);
			// st.setLong(posicion++, codSede );
			st.setLong(posicion++, codMed);
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				return rs.getString(posicion);
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
		return "";
	}

	/**
	 * @function: Metodo que retorna el nombre de la jornada segundo su
	 *            institucion y codigo
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String getNomNivel(long codInst, long codMed, long codNivel)
			throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getNomNivel"));
			posicion = 1;
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codMed);
			st.setLong(posicion++, codMed);
			st.setLong(posicion++, codNivel);

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				return rs.getString(posicion);
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
		return "";
	}

	/**
	 * @function: Metodo que retorna el nombre de la jornada segundo su
	 *            institucion y codigo
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public String getNomGrad(long codInst, long codMed, long codNivel,
			long codGrado) throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getNomGrado"));
			posicion = 1;

			st.setLong(posicion++, codNivel);
			st.setLong(posicion++, codNivel);

			st.setLong(posicion++, codInst);

			st.setLong(posicion++, codMed);
			st.setLong(posicion++, codMed);

			st.setLong(posicion++, codGrado);

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				return rs.getString(posicion);
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
		return "";
	}

	/**
	 * Valida que el literal no halla sido usado en Evaluacion Area
	 * 
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isEvaluacionArea(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		ResultSet rs2 = null;
		PreparedStatement st2 = null;
		int posicion = 0;
		long codG_Jeraquia = 0;

		try {
			cn = cursor.getConnection();

			// Obtener el codigo g_jerarquia
			st = cn.prepareStatement(rb
					.getString("escalaConcep.isEvaluacion.1"));
			posicion = 1;

			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());

			// st.setLong(posicion++, escalaConceptualVO.getInsconcodigo() );
			// st.setDouble(posicion++, escalaConceptualVO.getInsconvalnum() );

			rs = st.executeQuery();

			while (rs.next()) {
				codG_Jeraquia = rs.getLong(1);

				// Consultar si ese
				st2 = cn.prepareStatement(rb
						.getString("escalaConcep.isEvaluacion.2"));
				posicion = 1;

				st.setLong(posicion++, codG_Jeraquia);
				st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());

				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());

				// st.setDouble(posicion++, escalaConceptualVO.getInsconvalnum()
				// );
				rs2 = st2.executeQuery();
				if (rs2.next()) {
					return true;
				}

				rs2.close();
				st2.close();

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
				OperacionesGenerales.closeResultSet(rs2);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	/**
	 * Valida que el literal no halla sido usado en Evaluacion Area
	 * 
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isEvaluacionAsignatura(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		ResultSet rs2 = null;
		PreparedStatement st2 = null;
		int posicion = 0;
		long codG_Jeraquia = 0;

		try {
			cn = cursor.getConnection();

			// Obtener el codigo g_jerarquia
			st = cn.prepareStatement(rb
					.getString("escalaConcep.isEvaluacionAsignatura.1"));
			posicion = 1;

			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());

			st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());

			// st.setLong(posicion++, escalaConceptualVO.getInsconcodigo() );
			// st.setDouble(posicion++, escalaConceptualVO.getInsconvalnum() );

			rs = st.executeQuery();

			while (rs.next()) {
				codG_Jeraquia = rs.getLong(1);

				// Consultar si ese
				st2 = cn.prepareStatement(rb
						.getString("escalaConcep.isEvaluacionAsignatura.2"));
				posicion = 1;

				st.setLong(posicion++, codG_Jeraquia);
				st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());

				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
				st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());

				// st.setDouble(posicion++, escalaConceptualVO.getInsconvalnum()
				// );
				rs2 = st2.executeQuery();
				if (rs2.next()) {
					return true;
				}

				rs2.close();
				st2.close();

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
				OperacionesGenerales.closeResultSet(rs2);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	/**
	 * Valida que el literal no exista
	 * 
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteNombreLiteral(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		try {

			// System.out.println("isExisteNombreLiteral ------------------------------");
			FiltroEscalaVO filtroEscalaVO = new FiltroEscalaVO();
			filtroEscalaVO.setFilInst(escalaConceptualVO.getInsconcodinst());
			filtroEscalaVO.setFilVigencia(escalaConceptualVO
					.getInsconvigencia());
			filtroEscalaVO.setFilniveval(escalaConceptualVO.getInsconniveval());

			filtroEscalaVO.setFilSede(escalaConceptualVO.getInsconcodsede());
			filtroEscalaVO.setFilJorn(escalaConceptualVO.getInsconcodjorn());
			filtroEscalaVO.setFilMetodo(escalaConceptualVO.getInsconcodmetod());
			filtroEscalaVO.setFilNivel(escalaConceptualVO.getInsconcodnivel());
			filtroEscalaVO.setFilGrado(escalaConceptualVO.getInsconcodgrado());

			List lista = getListaEscalaConcep(filtroEscalaVO);

			for (int i = 0; i < lista.size(); i++) {
				EscalaConceptualVO n = new EscalaConceptualVO();
				n = (EscalaConceptualVO) lista.get(i);
				// System.out.println(n.getInsconcodigo() +" = " +
				// escalaConceptualVO.getInsconcodigo() );

				if (n.getInsconcodigo() != escalaConceptualVO.getInsconcodigo()) {
					// System.out.println(n.getInsconliteral() + " = " +
					// escalaConceptualVO.getInsconliteral());
					if (n.getInsconliteral()
							.trim()
							.toLowerCase()
							.equals(escalaConceptualVO.getInsconliteral()
									.trim().toLowerCase())
							|| n.getInsconnombre().equals(
									escalaConceptualVO.getInsconnombre())

					) {
						// System.out.println("true ");
						return true;
					} else {
						// System.out.println("no iguales");
					}
				}
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		// System.out.println("false");
		return false;
	}

	/**
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteValorNum(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("escalaConcep.isExisteValorNum"));
			posicion = 1;

			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodnivel());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
			st.setDouble(posicion++, escalaConceptualVO.getInsconvalnum());
			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				// System.out.println("true");
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
			}
		}
		return false;
	}

	/**
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public EscalaConceptualVO guardarEscalaConp(
			EscalaConceptualVO escalaConceptualVO) throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {

			cn = cursor.getConnection();

			sql += " " + rb.getString("escalaConcep.obtenerCodigoEscalaConp.1");
			// sede
			sql += " "
					+ ((escalaConceptualVO.getInsconcodsede() > -99) ? rb
							.getString("escalaConcep.SEDE") : rb
							.getString("escalaConcep.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaConceptualVO.getInsconcodjorn() > -99) ? rb
							.getString("escalaConcep.JORND") : rb
							.getString("escalaConcep.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaConceptualVO.getInsconcodmetod() > -99) ? rb
							.getString("escalaConcep.METODO") : rb
							.getString("escalaConcep.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaConceptualVO.getInsconcodnivel() > -99) ? rb
							.getString("escalaConcep.NIVEL") : rb
							.getString("escalaConcep.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaConceptualVO.getInsconcodgrado() > -99) ? rb
							.getString("escalaConcep.GRADO") : rb
							.getString("escalaConcep.GRADO_IS_NULL"));

			// sql += " " +
			// rb.getString("escalaConcep.obtenerCodigoEscalaConp.2");

			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconniveval());

			// sede
			if (escalaConceptualVO.getInsconcodsede() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());
			}
			// jornada
			if (escalaConceptualVO.getInsconcodjorn() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());
			}
			// metodo
			if (escalaConceptualVO.getInsconcodmetod() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
			}
			// nivel
			if (escalaConceptualVO.getInsconcodnivel() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodnivel());
			}
			// grado
			if (escalaConceptualVO.getInsconcodgrado() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());
			}

			rs = st.executeQuery();
			if (rs.next()) {
				escalaConceptualVO.setInsconcodigo(rs.getInt(1) + 1);
				// System.out.println("escalaConceptualVO.setInsconcodigo " +
				// escalaConceptualVO.getInsconcodigo());
			} else {
				escalaConceptualVO.setInsconcodigo(1);
			}
			rs.close();
			st.close();

			posicion = 1;
			st = cn.prepareStatement(rb
					.getString("escalaConcep.guardarEscalaConp"));
			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconniveval());

			if (escalaConceptualVO.getInsconcodsede() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			if (escalaConceptualVO.getInsconcodjorn() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			if (escalaConceptualVO.getInsconcodmetod() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			if (escalaConceptualVO.getInsconcodnivel() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodnivel());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			if (escalaConceptualVO.getInsconcodgrado() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
			st.setString(posicion++, escalaConceptualVO.getInsconliteral());
			st.setString(posicion++, escalaConceptualVO.getInsconnombre());
			st.setString(posicion++, escalaConceptualVO.getInscondescripcion());

			st.setLong(posicion++, escalaConceptualVO.getInsconorden());
			st.setDouble(posicion++, escalaConceptualVO.getInsconvalnum());
			st.setLong(posicion++, escalaConceptualVO.getInsconequmen());

			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return escalaConceptualVO;
	}

	/**
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public EscalaConceptualVO actualizarEscalaConp(
			EscalaConceptualVO escalaConceptualVO) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {
			cn = cursor.getConnection();

			posicion = 1;
			sql += " " + rb.getString("escalaConcep.actualizarEscalaConp");
			// sede
			sql += " "
					+ ((escalaConceptualVO.getInsconcodsede() > -99) ? rb
							.getString("escalaConcep.SEDE") : rb
							.getString("escalaConcep.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaConceptualVO.getInsconcodjorn() > -99) ? rb
							.getString("escalaConcep.JORND") : rb
							.getString("escalaConcep.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaConceptualVO.getInsconcodmetod() > -99) ? rb
							.getString("escalaConcep.METODO") : rb
							.getString("escalaConcep.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaConceptualVO.getInsconcodnivel() > -99) ? rb
							.getString("escalaConcep.NIVEL") : rb
							.getString("escalaConcep.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaConceptualVO.getInsconcodgrado() > -99) ? rb
							.getString("escalaConcep.GRADO") : rb
							.getString("escalaConcep.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);

			if (escalaConceptualVO.getInsconcodsede() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			if (escalaConceptualVO.getInsconcodjorn() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			if (escalaConceptualVO.getInsconcodmetod() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			if (escalaConceptualVO.getInsconcodnivel() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodnivel());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			if (escalaConceptualVO.getInsconcodgrado() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			st.setString(posicion++, escalaConceptualVO.getInsconliteral());
			st.setString(posicion++, escalaConceptualVO.getInsconnombre());
			st.setString(posicion++, escalaConceptualVO.getInscondescripcion());

			st.setLong(posicion++, escalaConceptualVO.getInsconorden());
			st.setDouble(posicion++, escalaConceptualVO.getInsconvalnum());
			st.setLong(posicion++, escalaConceptualVO.getInsconequmen());

			// where
			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			// System.out.println("escalaConceptualVO.getInsconvigencia() " +
			// escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			// System.out.println("escalaConceptualVO.getInsconcodinst() " +
			// escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconniveval());
			// System.out.println("escalaConceptualVO.getInsconniveval() " +
			// escalaConceptualVO.getInsconniveval());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodigo());
			// System.out.println("escalaConceptualVO.getInsconcodigo() " +
			// escalaConceptualVO.getInsconcodigo());

			// sede
			if (escalaConceptualVO.getInsconcodsede() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());
			}
			// jornada
			if (escalaConceptualVO.getInsconcodjorn() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());
			}
			// metodo
			if (escalaConceptualVO.getInsconcodmetod() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
			}
			// nivel
			if (escalaConceptualVO.getInsconcodnivel() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodnivel());
			}
			// grado
			if (escalaConceptualVO.getInsconcodgrado() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());
			}

			int i = st.executeUpdate();

			if (i == 0)
				throw new Exception("No se actualizo ningun registro");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return escalaConceptualVO;
	}

	/**
	 * @param escalaConceptualVO
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteEscalaConp(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("periodo.isExistePeriodo"));
			posicion = 1;
			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
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
			}
		}
		return false;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaEscalaConcep(FiltroEscalaVO filtroEscalaVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("escalaConcep.getlistaEscalaConcep"));
			// System.out.println("" +
			// rb.getString("escalaConcep.getlistaEscalaConcep"));
			posicion = 1;

			st.setLong(posicion++, filtroEscalaVO.getFilVigencia());
			// System.out.println("filtroEscalaVO.getFilVigencia() " +
			// filtroEscalaVO.getFilVigencia());
			st.setLong(posicion++, filtroEscalaVO.getFilInst());
			// System.out.println("filtroEscalaVO.getFilInst() " +
			// filtroEscalaVO.getFilInst());
			st.setLong(posicion++, filtroEscalaVO.getFilniveval());
			// System.out.println("filtroEscalaVO.getFilniveval() " +
			// filtroEscalaVO.getFilniveval());
			// System.out.println("filtroEscalaVO.getFilInst() " +
			// filtroEscalaVO.getFilInst());

			/*
			 * System.out.println("filtroEscalaVO.getFilSede() " +
			 * filtroEscalaVO.getFilSede());
			 * System.out.println("filtroEscalaVO.getFilJorn() " +
			 * filtroEscalaVO.getFilJorn());
			 * System.out.println("filtroEscalaVO.getFilMetodo() " +
			 * filtroEscalaVO.getFilMetodo());
			 * System.out.println("filtroEscalaVO.getFilNivel() " +
			 * filtroEscalaVO.getFilNivel());
			 * System.out.println("filtroEscalaVO.getFilGrado() "+
			 * filtroEscalaVO.getFilNivel());
			 */
			// Sede
			if (filtroEscalaVO.getFilSede() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaVO.getFilSede());
			}
			st.setLong(posicion++, filtroEscalaVO.getFilSede());

			// Jornada
			if (filtroEscalaVO.getFilJorn() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaVO.getFilJorn());
			}
			st.setLong(posicion++, filtroEscalaVO.getFilJorn());

			// Metodologia
			if (filtroEscalaVO.getFilMetodo() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaVO.getFilMetodo());
			}
			st.setLong(posicion++, filtroEscalaVO.getFilMetodo());

			// Nivel
			if (filtroEscalaVO.getFilNivel() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaVO.getFilNivel());
			}
			st.setLong(posicion++, filtroEscalaVO.getFilNivel());

			// Grado
			if (filtroEscalaVO.getFilGrado() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaVO.getFilGrado());
			}
			st.setLong(posicion++, filtroEscalaVO.getFilGrado());

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				escalaConceptualVO = new EscalaConceptualVO();
				escalaConceptualVO.setInsconvigencia(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodinst(rs.getLong(posicion++));
				escalaConceptualVO.setInsconniveval(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodsede(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodjorn(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodmetod(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodnivel(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodgrado(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodigo(rs.getLong(posicion++));
				escalaConceptualVO.setInsconliteral(rs.getString(posicion++));
				escalaConceptualVO.setInsconnombre(rs.getString(posicion++));
				escalaConceptualVO.setInscondescripcion(rs
						.getString(posicion++));
				escalaConceptualVO.setInsconorden(rs.getLong(posicion++));
				escalaConceptualVO.setInsconvalnum(rs.getDouble(posicion++));
				escalaConceptualVO.setInsconequmen(rs.getLong(posicion++));
				escalaConceptualVO.setInsconequmenNom(rs.getString(posicion++));
				list.add(escalaConceptualVO);
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
	 * @param codInst
	 * @param codVig
	 * @param codNivelEva
	 * @param codCodigo
	 * @return
	 * @throws Exception
	 */
	public EscalaConceptualVO getEscalaConcep(long codInst, long codVig,
			long codNivelEva, long codCodigo, long codsede, long codjornd,
			long codmetodo, long codnivel, long codgrado) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
		try {
			cn = cursor.getConnection();
			String sql = "";

			sql += rb.getString("escalaConcep.getObtenerEscalaConcep.1");

			// sede
			sql += " "
					+ ((codsede > -99) ? rb.getString("escalaConcep.SEDE") : rb
							.getString("escalaConcep.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((codjornd > -99) ? rb.getString("escalaConcep.JORND")
							: rb.getString("escalaConcep.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((codmetodo > -99) ? rb.getString("escalaConcep.METODO")
							: rb.getString("escalaConcep.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((codnivel > -99) ? rb.getString("escalaConcep.NIVEL")
							: rb.getString("escalaConcep.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((codgrado > -99) ? rb.getString("escalaConcep.GRADO")
							: rb.getString("escalaConcep.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codVig);
			st.setLong(posicion++, codNivelEva);
			st.setLong(posicion++, codCodigo);

			// sede
			if (codsede > -99) {
				st.setLong(posicion++, codsede);
			}
			// jornada
			if (codjornd > -99) {
				st.setLong(posicion++, codjornd);
			}
			// metodo
			if (codmetodo > -99) {
				st.setLong(posicion++, codmetodo);
			}
			// nivel
			if (codnivel > -99) {
				st.setLong(posicion++, codnivel);
			}
			// grado
			if (codgrado > -99) {
				st.setLong(posicion++, codgrado);
			}

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				escalaConceptualVO = new EscalaConceptualVO();
				escalaConceptualVO.setInsconvigencia(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodinst(rs.getLong(posicion++));
				escalaConceptualVO.setInsconniveval(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodsede(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodjorn(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodmetod(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodnivel(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodgrado(rs.getLong(posicion++));
				escalaConceptualVO.setInsconcodigo(rs.getLong(posicion++));
				escalaConceptualVO.setInsconliteral(rs.getString(posicion++));
				escalaConceptualVO.setInsconliteralAntes(escalaConceptualVO
						.getInsconliteral());
				escalaConceptualVO.setInsconnombre(rs.getString(posicion++));
				escalaConceptualVO.setInsconnombreAntes(escalaConceptualVO
						.getInsconnombre());
				escalaConceptualVO.setInscondescripcion(rs
						.getString(posicion++));
				escalaConceptualVO.setInsconorden(rs.getLong(posicion++));
				escalaConceptualVO.setInsconvalnum(rs.getDouble(posicion++));
				escalaConceptualVO.setInsconequmen(rs.getLong(posicion++));
				escalaConceptualVO.setEdicion(1 > 0);

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
		return escalaConceptualVO;
	}

	/**
	 * @param codInst
	 * @param codVig
	 * @param codNivelEva
	 * @param codCodigo
	 * @return
	 * @throws Exception
	 */
	public void eliminarEscalaConcep(long codInst, long codVig,
			long codNivelEva, long codCodigo, long codsede, long codjornd,
			long codmetodo, long codnivel, long codgrado) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		String sql = "";
		try {
			cn = cursor.getConnection();

			sql += " " + rb.getString("escalaConcep.eliminarEscalaConcep.1");
			// sede
			sql += " "
					+ ((codsede > -99) ? rb.getString("escalaConcep.SEDE") : rb
							.getString("escalaConcep.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((codjornd > -99) ? rb.getString("escalaConcep.JORND")
							: rb.getString("escalaConcep.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((codmetodo > -99) ? rb.getString("escalaConcep.METODO")
							: rb.getString("escalaConcep.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((codnivel > -99) ? rb.getString("escalaConcep.NIVEL")
							: rb.getString("escalaConcep.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((codgrado > -99) ? rb.getString("escalaConcep.GRADO")
							: rb.getString("escalaConcep.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codVig);
			st.setLong(posicion++, codNivelEva);
			st.setLong(posicion++, codCodigo);

			// sede
			if (codsede > -99) {
				st.setLong(posicion++, codsede);
			}
			// jornada
			if (codjornd > -99) {
				st.setLong(posicion++, codjornd);
			}
			// metodo
			if (codmetodo > -99) {
				st.setLong(posicion++, codmetodo);
			}
			// nivel
			if (codnivel > -99) {
				st.setLong(posicion++, codnivel);
			}
			// grado
			if (codgrado > -99) {
				st.setLong(posicion++, codgrado);
			}

			st.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getlistaVigencia(Login usuVO) throws Exception {

		List list = new ArrayList();
		ItemVO itemVO = null;
		try {

			for (int i = 2005; i <= getVigenciaInst(Long.parseLong(usuVO
					.getInstId())); i++) {
				itemVO = new ItemVO();
				itemVO.setCodigo(i);
				itemVO.setNombre(i + "");
				list.add(itemVO);
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {

		}
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getMetodologia(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("escalaConcep.getMetodologia"));
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
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

	// sede

	/**
	 * @return
	 * @throws Exception
	 */
	public List getSede(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getSede"));
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

	// sede

	/**
	 * @return
	 * @throws Exception
	 */
	/*
	 * public List getJornada(long codInst) throws Exception{
	 * 
	 * Connection cn=null; PreparedStatement st=null; ResultSet rs=null; List
	 * list = new ArrayList(); ItemVO itemVO = null; int posicion = 1; try{
	 * cn=cursor.getConnection();
	 * st=cn.prepareStatement(rb.getString("escalaConcep.getJornada2"));
	 * st.setLong(posicion++,codInst); rs = st.executeQuery();
	 * 
	 * while (rs.next()) { posicion = 1; itemVO = new ItemVO();
	 * itemVO.setCodigo(rs.getInt(posicion++));
	 * itemVO.setNombre(rs.getString(posicion++)); list.add(itemVO); }
	 * System.out.println(" getMetodologia");
	 * 
	 * 
	 * }catch(SQLException sqle){ sqle.printStackTrace(); throw new
	 * Exception("Error de datos: "+sqle.getMessage()); }catch(Exception sqle){
	 * sqle.printStackTrace(); throw new
	 * Exception("Error interno: "+sqle.getMessage()); }finally{ try{
	 * OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeConnection(cn); }catch(InternalErrorException
	 * inte){} } return list; }
	 */

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
			st = cn.prepareStatement(rb.getString("escalaConcep.getJornada"));
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
		// System.out.println("getMetodologia ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("escalaConcep.getMetodologia"));
			st.setLong(posicion++, codInst);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}

			// System.out.println("list.size() " + list.size());
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
	public List getNivel(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getNivel"));
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
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
	public List getNivel(long codInst, long codMetod) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getNivel"));
			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst );
			st.setLong(posicion++, codMetod);
			// System.out.println("codMetod " + codMetod);
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
	 * @return
	 * @throws Exception
	 */
	public List getNivel(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getNivel2"));
			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst );
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
	 * @return
	 * @throws Exception
	 */
	public List getGrado(EscalaConceptualVO escalaConceptualVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getGrado"));
			st.setLong(posicion++, escalaConceptualVO.getInsconcodnivel());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());

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
	public List getGrado(long codInst, long codMetod, long codNivel)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getGrado"));

			st.setLong(posicion++, codNivel);
			// System.out.println("codNivel " + codNivel);
			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst);
			st.setLong(posicion++, codMetod);
			// System.out.println("codMetod " + codMetod);
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
	 * @return
	 * @throws Exception
	 */
	public List getGrado(long codInst, long codNivel) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getGrado3"));

			st.setLong(posicion++, codNivel);
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
	 * @return
	 * @throws Exception
	 */
	public List getGradoMetodologia(long codInst, long codMetodo)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getGrado4"));

			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codMetodo);
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
	 * @return
	 * @throws Exception
	 */
	public List getGrado(long codInst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getGrado2"));

			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst);
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
	 * @return
	 * @throws Exception
	 */
	public InstNivelEvaluacionVO getNivelEvaluacion(long codNivelEva)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		InstNivelEvaluacionVO nivelEvaluacionVO = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("escalaConcep.getNivelEvaluacion"));
			st.setLong(posicion++, codNivelEva);
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;

			}

			if (nivelEvaluacionVO == null) {
				throw new Exception(
						"El codigo de Nivel de Evaluacion no existe.");
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
		return nivelEvaluacionVO;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getEscalaMEN() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("escalaConcep.getEscalaMEN"));
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

	// ESCALA NUMERICA

	/**
	 * @param escalaNumericaVO
	 * @return
	 * @throws Exception
	 */
	public EscalaNumericaVO guardarEscalaNumerica(
			EscalaNumericaVO escalaNumericaVO) throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("escalaConcep.guardarEscalaNum"));
			posicion = 1;
			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			} else {
				st.setNull(posicion++, Types.NUMERIC);
			}

			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmin())
							.doubleValue());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmax())
							.doubleValue());
			st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());
			st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			st.setString(posicion++, escalaNumericaVO.getInsnumdescripcion());
			st.setLong(posicion++, escalaNumericaVO.getInsnumorden());

			st.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return escalaNumericaVO;
	}

	/**
	 * Actualiza el registro en la table INTITUCION_ESCALA_NUMERICA la llave
	 * primaria ser vigencia, nivelEval, Institucion , EquivalenciaMen y la
	 * combinacion de nivelEval sede,jornada, metodologia,nivel, grado
	 * 
	 * @param escalaNumericaVO
	 * @return
	 * @throws Exception
	 */
	public EscalaNumericaVO actualizarEscalaNum(
			EscalaNumericaVO escalaNumericaVO) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {
			cn = cursor.getConnection();

			posicion = 1;
			sql += " " + rb.getString("escalaNum.actualizarEscalaNum");
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmin())
							.doubleValue());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmax())
							.doubleValue());
			st.setString(posicion++, escalaNumericaVO.getInsnumdescripcion());
			st.setLong(posicion++, escalaNumericaVO.getInsnumorden());
			st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			// where
			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++,escalaNumericaVO.getInsnumequmen());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalminAntes())
							.doubleValue());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmaxAntes())
							.doubleValue());
			// st.setLong(posicion++,escalaNumericaVO.getInsnumequinst());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// System.out.println("escalaNumericaVO.getInsnumcodsede() " +
			// escalaNumericaVO.getInsnumcodsede());
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// /System.out.println("escalaNumericaVO.getInsnumcodjorn()  " +
			// escalaNumericaVO.getInsnumcodjorn() );
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// System.out.println("escalaNumericaVO.getInsnumcodmetod()  " +
			// escalaNumericaVO.getInsnumcodmetod() );
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// System.out.println("escalaNumericaVO.getInsnumcodnivel()   " +
			// escalaNumericaVO.getInsnumcodnivel() );
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}
			// System.out.println("escalaNumericaVO.getInsnumcodgrado() " +
			// escalaNumericaVO.getInsnumcodgrado());

			int i = st.executeUpdate();

			if (i == 0)
				throw new Exception("No se actualizo ningun registro");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return escalaNumericaVO;
	}

	/**
	 * @param filtroEscalaNumVO
	 * @return
	 * @throws Exception
	 */
	public List getListaEscalaNum(FiltroEscalaNumVO filtroEscalaNumVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		String sql = "";
		try {
			cn = cursor.getConnection();
			sql += " " + rb.getString("escalaNum.getlistaEscalaNum.1");
			sql += " " + rb.getString("escalaNum.getlistaEscalaNum.1.1");
			sql += " " + rb.getString("escalaNum.getlistaEscalaNum.1.2");
			sql += " " + rb.getString("escalaNum.getlistaEscalaNum.1.3");
			// System.out.println(" getListaEscalaNum sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, filtroEscalaNumVO.getFilNumVigencia());
			st.setLong(posicion++, filtroEscalaNumVO.getFilNumInst());
			st.setLong(posicion++, filtroEscalaNumVO.getFilNumniveval());
			// System.out.println("filtroEscalaNumVO.getFilNumniveval() " +
			// filtroEscalaNumVO.getFilNumniveval());

			// Sede
			if (filtroEscalaNumVO.getFilNumSede() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaNumVO.getFilNumSede());
			}
			st.setLong(posicion++, filtroEscalaNumVO.getFilNumSede());
			// Jornada
			if (filtroEscalaNumVO.getFilNumJorn() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaNumVO.getFilNumJorn());
			}
			st.setLong(posicion++, filtroEscalaNumVO.getFilNumJorn());
			// Metodologia
			if (filtroEscalaNumVO.getFilNumMetodo() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaNumVO.getFilNumMetodo());
			}
			st.setLong(posicion++, filtroEscalaNumVO.getFilNumMetodo());
			// Nivel
			if (filtroEscalaNumVO.getFilNumNivel() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaNumVO.getFilNumNivel());
			}
			st.setLong(posicion++, filtroEscalaNumVO.getFilNumNivel());
			// Grado
			if (filtroEscalaNumVO.getFilNumGrado() <= -99) {
				st.setLong(posicion++, -99);
			} else {
				st.setLong(posicion++, filtroEscalaNumVO.getFilNumGrado());
			}
			st.setLong(posicion++, filtroEscalaNumVO.getFilNumGrado());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				EscalaNumericaVO escalaNumericaVO = new EscalaNumericaVO();
				escalaNumericaVO.setInsnumvigencia(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodinst(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumniveval(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodsede(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodjorn(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodmetod(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodnivel(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodgrado(rs.getLong(posicion++));

				escalaNumericaVO.setInsnumvalmin(""
						+ convertir(rs.getDouble(posicion++)));
				escalaNumericaVO.setInsnumvalmax(""
						+ convertir(rs.getDouble(posicion++)));

				escalaNumericaVO.setInsnumvalminAntes(escalaNumericaVO
						.getInsnumvalmin());
				escalaNumericaVO.setInsnumvalmaxAntes(escalaNumericaVO
						.getInsnumvalmin());

				escalaNumericaVO.setInsnumequmen(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumequinst(rs.getLong(posicion++));

				escalaNumericaVO.setInsnumdescripcion(rs.getString(posicion++));
				escalaNumericaVO.setInsnumorden(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumequmenNom(rs.getString(posicion++));
				escalaNumericaVO.setInsnumequinstNom(rs.getString(posicion++));
				list.add(escalaNumericaVO);
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
		return list;
	}

	/**
	 * @param vig
	 * @param codinst
	 * @param codniveval
	 * @param codsede
	 * @param codjornd
	 * @param codmetodo
	 * @param codnivel
	 * @param codgrado
	 * @return
	 * @throws Exception
	 */
	public List getListaEscalaInst_(long vig, long codinst, long codniveval,
			long codsede, long codjornd, long codmetodo, long codnivel,
			long codgrado) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = "";
		try {
			cn = cursor.getConnection();
			sql += " " + rb.getString("escalaConcep.listaEscalaInst_.1");

			// sede
			sql += " "
					+ ((codsede > -99) ? rb.getString("escalaConcep.SEDE") : rb
							.getString("escalaConcep.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((codjornd > -99) ? rb.getString("escalaConcep.JORND")
							: rb.getString("escalaConcep.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((codmetodo > -99) ? rb.getString("escalaConcep.METODO")
							: rb.getString("escalaConcep.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((codnivel > -99) ? rb.getString("escalaConcep.NIVEL")
							: rb.getString("escalaConcep.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((codgrado > -99) ? rb.getString("escalaConcep.GRADO")
							: rb.getString("escalaConcep.GRADO_IS_NULL"));

			sql += " " + rb.getString("escalaConcep.listaEscalaInst_.2");

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);

			st.setLong(posicion++, vig);
			// System.out.println("vig " + vig);

			st.setLong(posicion++, codinst);
			// System.out.println("codinst " + codinst);

			st.setLong(posicion++, codniveval);
			/*
			 * System.out.println("codniveval " + codniveval);
			 * System.out.println("codsede " + codsede);
			 * System.out.println("codjornd " + codjornd);
			 * System.out.println("codmetodo " + codmetodo);
			 * System.out.println("codnivel " + codnivel);
			 * System.out.println("codgrado " + codgrado);
			 */
			// sede
			if (codsede > -99) {
				st.setLong(posicion++, codsede); // System.out.println("codsede "
													// + codsede);
			}
			// jornada
			if (codjornd > -99) {
				st.setLong(posicion++, codjornd); // System.out.println("codjornd "
													// + codjornd);
			}
			// metodo
			if (codmetodo > -99) {
				st.setLong(posicion++, codmetodo); // System.out.println("codmetodo "
													// + codmetodo);
			}
			// nivel
			if (codnivel > -99) {
				st.setLong(posicion++, codnivel); // System.out.println("codnivel "
													// + codnivel);
			}
			// grado
			if (codgrado > -99) {
				st.setLong(posicion++, codgrado); // System.out.println("codgrado "
													// + codgrado);
			}

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
	 * Consulta para conocer si el registro ya existe, retorna TRUE si existe,
	 * FALSE si no.
	 * 
	 * @param escalaNumericaVO
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteEscalaNumerica(EscalaNumericaVO escalaNumericaVO)
			throws Exception {

		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {

			cn = cursor.getConnection();
			sql += " " + rb.getString("escalaNum.isExisteEscalaNumerica.1");
			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			// System.out.println("sql isExisteEscalaNumerica  " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			// System.out.println("escalaNumericaVO.getInsnumvigencia() " +
			// escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			// System.out.println("escalaNumericaVO.getInsnumcodinst() " +
			// escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// System.out.println("escalaNumericaVO.getInsnumniveval() " +
			// escalaNumericaVO.getInsnumniveval());
			st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());
			// System.out.println("escalaNumericaVO.getInsnumequmen() " +
			// escalaNumericaVO.getInsnumequmen());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				// System.out.println("true");
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
			}
		}
		return false;
	}

	/**
	 * @param codInst
	 * @param codVig
	 * @param codNivelEva
	 * @param codEqvInst
	 * @param codsede
	 * @param codjornd
	 * @param codmetodo
	 * @param codnivel
	 * @param codgrado
	 * @return
	 * @throws Exception
	 */
	public EscalaNumericaVO getEscalaNumerica(long codInst, long codVig,
			long codNivelEva, long codEqvInst, long codEqvMEN, long codsede,
			long codjornd, long codmetodo, long codnivel, long codgrado,
			double minValor, double maxValor) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		EscalaNumericaVO escalaNumericaVO = new EscalaNumericaVO();
		try {
			cn = cursor.getConnection();
			String sql = "";

			sql += rb.getString("escalaNum.getObtenerEscalaNum.1");

			// sede
			sql += " "
					+ ((codsede > -99) ? rb.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((codjornd > -99) ? rb.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((codmetodo > -99) ? rb.getString("escalaNum.METODO")
							: rb.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((codnivel > -99) ? rb.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((codgrado > -99) ? rb.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, codVig);
			// System.out.println("codVig " + codVig);
			st.setLong(posicion++, codInst);
			// System.out.println("codInst  " + codInst);

			st.setLong(posicion++, codNivelEva);
			// System.out.println("codNivelEva " + codNivelEva);
			// st.setLong(posicion++, codEqvInst); //Equivalencia instuticion
			// System.out.println("codEqvInst " + codEqvInst);
			// st.setLong(posicion++, codEqvMEN);

			st.setDouble(posicion++, minValor);
			st.setDouble(posicion++, maxValor);

			// sede
			if (codsede > -99) {
				st.setLong(posicion++, codsede);
			}
			// jornada
			if (codjornd > -99) {
				st.setLong(posicion++, codjornd);
			}
			// metodo
			if (codmetodo > -99) {
				st.setLong(posicion++, codmetodo);
			}
			// nivel
			if (codnivel > -99) {
				st.setLong(posicion++, codnivel);
			}
			// grado
			if (codgrado > -99) {
				st.setLong(posicion++, codgrado);
			}

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				escalaNumericaVO.setInsnumvigencia(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodinst(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumniveval(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodsede(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodjorn(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodmetod(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodnivel(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumcodgrado(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumvalmin(""
						+ convertir(rs.getDouble(posicion++)));
				escalaNumericaVO.setInsnumvalmax(""
						+ convertir(rs.getDouble(posicion++)));

				escalaNumericaVO.setInsnumvalminAntes(escalaNumericaVO
						.getInsnumvalmin());
				escalaNumericaVO.setInsnumvalmaxAntes(escalaNumericaVO
						.getInsnumvalmax());

				escalaNumericaVO.setInsnumequmen(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumequinst(rs.getLong(posicion++));
				escalaNumericaVO.setInsnumdescripcion(rs.getString(posicion++));
				escalaNumericaVO.setInsnumorden(rs.getLong(posicion++));
				escalaNumericaVO.setEdicion(1 > 0);

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
		return escalaNumericaVO;
	}

	/**
	 * @function:
	 * @param codInst
	 * @param codVig
	 * @param codNivelEva
	 * @param codEqvIns
	 * @param codEqvMem
	 * @param codsede
	 * @param codjornd
	 * @param codmetodo
	 * @param codnivel
	 * @param codgrado
	 * @param valorMin
	 * @param valorMax
	 * @throws Exception
	 */
	public void eliminarEscalaNum_(long codInst, long codVig, long codNivelEva,
			long codEqvIns, long codEqvMem, long codsede, long codjornd,
			long codmetodo, long codnivel, long codgrado, double valorMin,
			double valorMax) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		String sql = "";
		try {
			cn = cursor.getConnection();
			sql += " " + rb.getString("escalaNum.eliminarEscalaNum.1");
			// sede
			sql += " "
					+ ((codsede > -99) ? rb.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((codjornd > -99) ? rb.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((codmetodo > -99) ? rb.getString("escalaNum.METODO")
							: rb.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((codnivel > -99) ? rb.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((codgrado > -99) ? rb.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, codVig);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codNivelEva);
			st.setDouble(posicion++, valorMin);
			st.setDouble(posicion++, valorMax);

			// st.setLong(posicion++, codEqvIns);
			// st.setLong(posicion++, codEqvMem);

			// sede
			if (codsede > -99) {
				st.setLong(posicion++, codsede);
			}
			// jornada
			if (codjornd > -99) {
				st.setLong(posicion++, codjornd);
			}
			// metodo
			if (codmetodo > -99) {
				st.setLong(posicion++, codmetodo);
			}
			// nivel
			if (codnivel > -99) {
				st.setLong(posicion++, codnivel);
			}
			// grado
			if (codgrado > -99) {
				st.setLong(posicion++, codgrado);
			}

			int i = st.executeUpdate();
			if (i == 0)
				throw new Exception("No se elimino ningun registro.");

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	/**
	 * @param escalaCncepVO
	 * @return
	 * @throws Exception
	 */
	public boolean isvalidarLiteralEvaluacion(EscalaConceptualVO escalaCncepVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		long codJerarquia = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			// Obtener codigo jerarquia
			st = cn.prepareStatement(rb
					.getString("escalaConcep.obtenerCOdigoJerarquia"));
			posicion = 1;
			st.setLong(posicion++, escalaCncepVO.getInsconvigencia());
			st.setLong(posicion++, escalaCncepVO.getInsconcodinst());

			st.setLong(posicion++, escalaCncepVO.getInsconcodsede());
			st.setLong(posicion++, escalaCncepVO.getInsconcodsede());

			st.setLong(posicion++, escalaCncepVO.getInsconcodjorn());
			st.setLong(posicion++, escalaCncepVO.getInsconcodjorn());

			st.setLong(posicion++, escalaCncepVO.getInsconcodmetod());
			st.setLong(posicion++, escalaCncepVO.getInsconcodmetod());

			st.setLong(posicion++, escalaCncepVO.getInsconcodgrado());
			st.setLong(posicion++, escalaCncepVO.getInsconcodgrado());

			rs = st.executeQuery();

			if (rs.next()) {
				codJerarquia = rs.getLong(1);
			} else {
				throw new Exception("No se encontrar el codigo de jerarquia.");
			}
			rs.close();
			st.close();

			/*
			 * st=cn.prepareStatement(rb.getString(
			 * "escalaConcep.validarEvaluacionArea")); posicion=1;
			 * st.setLong(posicion++,codJerarquia);
			 * st.setLong(posicion++,escalaCncepVO.getInsconvigencia());
			 * 
			 * for(int i=0;i<26;i++){
			 * st.setLong(posicion++,escalaCncepVO.getInsconcodigo()); }
			 * 
			 * 
			 * 
			 * rs=st.executeQuery(); if(rs.next() && rs.getInt(1) > 0){ return
			 * true; }
			 */

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
		return false;
	}

	/**
	 * Valida que se hallan creado todo los rangos para la escala numerica.
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public String isValidarRangosAsignar(EscalaNumericaVO escalaNumericaVO)
			throws Exception {
		// System.out.println("-------------- isValidarRangosAsignar");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int cantidadEstNum = 0;
		int TotalRangoEstNum = 0;
		int numRangos = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			/**
			 * Traer suma total de rangos
			 * */

			sql += rb.getString("escalaNum.isValidarRangos.1");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmin())
							.doubleValue());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmax())
							.doubleValue());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();
			//
			if (rs.next()) {
				posicion = 1;
				System.out.println(truncar(rs.getDouble(1)));
				cantidadEstNum = (int) (truncar(rs.getDouble(posicion++)));
				// System.out.println("cantidadEstNum - " + cantidadEstNum);
				cantidadEstNum += (truncar(convertir(escalaNumericaVO
						.getInsnumvalmax())) - truncar(convertir(escalaNumericaVO
						.getInsnumvalmin())));
				// System.out.println("cantidadEstNum " + cantidadEstNum);
			}
			rs.close();
			st.close();

			/**
			 * Obtener el numero de rangos
			 * */

			sql = rb.getString("escalaNum.isValidarRangos.1numRangos");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);

			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmin())
							.doubleValue());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmax())
							.doubleValue());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();

			//
			if (rs.next()) {
				numRangos = rs.getInt(1);
				// System.out.println("numRangos " + numRangos);
			}
			rs.close();
			st.close();

			/**
			 * Traer rango de INSTITUCION_NIVEL_EVALUACION
			 */

			sql = rb.getString("escalaNum.isValidarRangos.2");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				TotalRangoEstNum = (int) truncar(rs.getDouble(posicion++));
				// System.out.println("Total_ 2 " + TotalRangoEstNum);
			}
			rs.close();
			st.close();

			// System.out.println(cantidadEstNum + numRangos + " == "
			// + TotalRangoEstNum);
			if (!((cantidadEstNum + numRangos) == TotalRangoEstNum)) {
				actualizarNivelEvaluacionRango(escalaNumericaVO, 0);
				return " Pero faltan rangos por asignar.";
				/*
				 * "\n    - Suma de rangos en la escala numnrica  : "+
				 * convertir(getDouble( cantidadEstNum)) +
				 * "\n    - Valor del rango asignado en Nivel de Evaluacinn  : "
				 * + convertir(getDouble(TotalRangoEstNum));
				 */
			}

			// if(getEscalaMEN().size() != (numRangos + 1) ){
			// actualizarNivelEvaluacionRango(escalaNumericaVO,0);
			// return
			// " Pero falta asignar rangos con la equivalencia de la escala MEN.";
			// }

			actualizarNivelEvaluacionRango(escalaNumericaVO, 1);
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

		return null;
	}

	/**
	 * Valida que se hallan creado todo los rangos para la escala numerica.
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public String isValidarRangosAsignarEliminar(
			EscalaNumericaVO escalaNumericaVO) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int cantidadEstNum = 0;
		int TotalRangoEstNum = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			/**
			 * Traer suma total de rangos
			 * */

			sql += rb.getString("escalaNum.isValidarRangos.1");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmin())
							.doubleValue());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmax())
							.doubleValue());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();
			//
			if (rs.next()) {
				posicion = 1;
				cantidadEstNum = truncar(rs.getDouble(posicion++));
				// System.out.println("cantidadEstNum " + cantidadEstNum);

			}
			rs.close();
			st.close();

			/**
			 * Traer rango de INSTITUCION_NIVEL_EVALUACION
			 */

			sql = rb.getString("escalaNum.isValidarRangos.2");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				TotalRangoEstNum = truncar(rs.getDouble(posicion++));
				// System.out.println("Total_ 2 " + TotalRangoEstNum);
			}
			rs.close();
			st.close();

			// ///////////////////////////
			/**
			 * Actualizar INSNIVRANGOS 1 si los rango estan completos 0 si falta
			 * rangos por asignar
			 */
			sql = " "
					+ rb.getString("instNivelEval.actualizarNivelEvaluacionRango");
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);
			posicion = 1;
			if (cantidadEstNum == TotalRangoEstNum) {
				st.setInt(posicion++, 1);// Si tiene todos lo rangos asignados
			} else {
				st.setInt(posicion++, 0);// No tiene todos lo rangos asignados
			}

			// where
			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());

			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			int i = st.executeUpdate();

			rs.close();
			st.close();

			// System.out.println(cantidadEstNum +" == " + TotalRangoEstNum);
			if (cantidadEstNum == TotalRangoEstNum) {
				return null;
			} else {
				return " ";

				// "\n    - Suma de rangos en la escala numnrica  : "+
				// convertir(getDouble( cantidadEstNum)) +
				// "\n    - Valor del rango asignado en Nivel de Evaluacinn  : "
				// + convertir(getDouble(TotalRangoEstNum));

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

	}

	/**
	 * Valida que el nuevo rango minimo no se sobresolape con otro rango
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public String isValidarRangosSobreLapenMinMax(
			EscalaNumericaVO escalaNumericaVO) throws Exception {
		// System.out.println("Valida que el valor minimo no se sobrelape  ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int TotalRangoMaz = 0;
		int TotalRangoMin = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			sql += rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.1");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			sql += rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.2");
			sql += rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.3");
			sql += rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.4");
			sql += rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.5");

			// System.out.println("sql " + sql);

			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());
			if (GenericValidator.isDouble(escalaNumericaVO
					.getInsnumvalminAntes())) {
				st.setDouble(posicion++,
						Double.valueOf(escalaNumericaVO.getInsnumvalminAntes())
								.doubleValue());
				st.setDouble(posicion++,
						Double.valueOf(escalaNumericaVO.getInsnumvalmaxAntes())
								.doubleValue());
			} else {
				st.setDouble(posicion++,
						Double.valueOf(escalaNumericaVO.getInsnumvalmin())
								.doubleValue());
				st.setDouble(posicion++,
						Double.valueOf(escalaNumericaVO.getInsnumvalmax())
								.doubleValue());

			}
			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			// caso 1
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmin()));
			// System.out.println("convertir(escalaNumericaVO.getInsnumvalmin()) "
			// + convertir(escalaNumericaVO.getInsnumvalmin()));
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmin()));
			// caso 2
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmax()));
			// System.out.println("convertir(escalaNumericaVO.getInsnumvalmax()) "
			// + convertir(escalaNumericaVO.getInsnumvalmax()));
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmax()));

			// caso 3
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmin()));
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmax()));

			// caso 4
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmin()));
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmax()));

			// System.out.println("escalaNumericaVO.getInsnumvalmin() " +
			// escalaNumericaVO.getInsnumvalmin());
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				TotalRangoMin = truncar(rs.getDouble(posicion++));
				TotalRangoMaz = truncar(rs.getDouble(posicion++));
				return " " + convertir(getDouble(TotalRangoMin)) + " - "
						+ convertir(getDouble(TotalRangoMaz));
			}

			// System.out.println(TotalRangoMin + " * " + TotalRangoMaz);
			// System.out.println(" convertir(escalaNumericaVO.getInsnumvalmin())  "
			// +
			// convertir(Double.valueOf(escalaNumericaVO.getInsnumvalmin()).doubleValue()
			// ));
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
		// System.out.println("false  ");
		return null;
	}

	/**
	 * Valida que el nuevo rango no sea identico a otro existente otro rango
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public String isValidarRangosSobreLapenMinMaxIguales(
			EscalaNumericaVO escalaNumericaVO) throws Exception {
		// System.out.println("Valida que el valor minimo no se sobrelape  ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int TotalRangoMaz = 0;
		int TotalRangoMin = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			sql += rb
					.getString("escalaNum.isValidarRangosSobreLapenMinMaxIguales.1");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			/*
			 * sql +=
			 * rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.2"); sql
			 * += rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.3");
			 * sql +=
			 * rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.4"); sql
			 * += rb.getString("escalaNum.isValidarRangosSobreLapenMinMax.5");
			 */

			// System.out.println("sql " + sql);

			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmin()));
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmax()));

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			// caso 1

			// System.out.println("escalaNumericaVO.getInsnumvalmin() " +
			// escalaNumericaVO.getInsnumvalmin());
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				TotalRangoMin = truncar(rs.getDouble(posicion++));
				TotalRangoMaz = truncar(rs.getDouble(posicion++));
				return " " + convertir(getDouble(TotalRangoMin)) + " - "
						+ convertir(getDouble(TotalRangoMaz));
			}

			// System.out.println(TotalRangoMin + " * " + TotalRangoMaz);
			// System.out.println(" convertir(escalaNumericaVO.getInsnumvalmin())  "
			// +
			// convertir(Double.valueOf(escalaNumericaVO.getInsnumvalmin()).doubleValue()
			// ));
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
		// System.out.println("false  ");
		return null;
	}

	/**
	 * Valida que el nuevo rango maximo no se sobresolape con otro rango
	 * existente
	 * 
	 * @param instNivelEvaluacionVO
	 * @return Retorna el un string con lo rangos con los cuales se cruza, si no
	 *         se solapa con ninguno retorna null
	 * @throws Exception
	 */
	public String isValidarRangosSobreLapenMax(EscalaNumericaVO escalaNumericaVO)
			throws Exception {
		// System.out.println("Valida que el valor minimo no se sobrelape  ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int TotalRangoMaz = 0;
		int TotalRangoMin = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			sql += rb.getString("escalaNum.isValidarRangosSobreLapenMin.1");
			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			sql += rb.getString("escalaNum.isValidarRangosSobreLapenMin.2");

			// System.out.println("sql " + sql);

			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmax()));
			st.setDouble(posicion++,
					convertir(escalaNumericaVO.getInsnumvalmax()));
			// System.out.println("escalaNumericaVO.getInsnumvalmax() " +
			// escalaNumericaVO.getInsnumvalmin());
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				TotalRangoMin = truncar(rs.getDouble(posicion++));
				TotalRangoMaz = truncar(rs.getDouble(posicion++));
				return " " + convertir(getDouble(TotalRangoMin)) + " y "
						+ convertir(getDouble(TotalRangoMaz));
			}

			// System.out.println(TotalRangoMin + " * " + TotalRangoMaz);
			// System.out.println(" convertir(escalaNumericaVO.getInsnumvalmin())  "
			// +
			// convertir(Double.valueOf(escalaNumericaVO.getInsnumvalmin()).doubleValue()
			// ));
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
		// System.out.println("false  ");
		return null;
	}

	/**
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public String isValidarRangosTotalMaxMin(EscalaNumericaVO escalaNumericaVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int cantidadEstNum = 0;
		int TotalRangoEstNum = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			sql += rb.getString("escalaNum.isValidarRangos.1");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("escalaNum.SEDE") : rb
							.getString("escalaNum.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("escalaNum.JORND") : rb
							.getString("escalaNum.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("escalaNum.METODO") : rb
							.getString("escalaNum.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("escalaNum.NIVEL") : rb
							.getString("escalaNum.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("escalaNum.GRADO") : rb
							.getString("escalaNum.GRADO_IS_NULL"));

			// sql += rb.getString("escalaNum.isValidarRangos.1.1");
			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmin())
							.doubleValue());
			st.setDouble(posicion++,
					Double.valueOf(escalaNumericaVO.getInsnumvalmax())
							.doubleValue());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				cantidadEstNum = truncar(rs.getDouble(posicion++));
				// System.out.println("cantidadEstNum " + cantidadEstNum);
				cantidadEstNum += truncar(convertir(escalaNumericaVO
						.getInsnumvalmax()))
						- truncar(convertir(escalaNumericaVO.getInsnumvalmin()));
				// System.out.println(" convertir(escalaNumericaVO.getInsnumvalmax()- escalaNumericaVO.getInsnumvalmin() ) "
				// +
				// convertir(Double.valueOf(escalaNumericaVO.getInsnumvalmax()).doubleValue()
				// - Double.valueOf(escalaNumericaVO.getInsnumvalmin()
				// ).doubleValue() ));
				// System.out.println("cantidadEstNum " + cantidadEstNum);
			}
			rs.close();
			st.close();

			sql = rb.getString("escalaNum.isValidarRangos.2");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				TotalRangoEstNum = truncar(rs.getDouble(posicion++));
			}
			// System.out.println(cantidadEstNum + " --- " + TotalRangoEstNum);
			// System.out.println(cantidadEstNum + " > " + TotalRangoEstNum);
			if ((cantidadEstNum > TotalRangoEstNum)) {
				return "\n    - Suma de rangos en la escala numnrica: "
						+ convertir(getDouble(cantidadEstNum))
						+ "\n    - Valor del rango asignado en Nivel de Evaluacinn: "
						+ convertir(getDouble(TotalRangoEstNum));
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
		return null;
	}

	/**
	 * Valida si el rango de escala conceptual esta dentro del rango definido en
	 * INSTITUCION_NIVEL_EVALUACION
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public String isValidarRangosMaxMin(EscalaNumericaVO escalaNumericaVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int rangoTotalMinimo = 0;
		int rangoTotalMaximo = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();

			sql += rb.getString("escalaNum.isValidarRangos.MaxMin");

			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jornada
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);
			posicion = 1;

			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequinst());
			// st.setLong(posicion++, escalaNumericaVO.getInsnumequmen());

			// sede
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			// jornada
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			// metodo
			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			// nivel
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			// grado
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				rangoTotalMinimo = truncar(rs.getDouble(posicion++));
				rangoTotalMaximo = truncar(rs.getDouble(posicion++));
			}

			// System.out.println("rangoTotalMinimo " + rangoTotalMinimo);
			// System.out.println("escalaNumericaVO.getInsnumvalmin()  " +
			// escalaNumericaVO.getInsnumvalmin());

			// System.out.println("rangoTotalMaximo " + rangoTotalMaximo);
			// System.out.println("escalaNumericaVO.getInsnumvalmax() " +
			// escalaNumericaVO.getInsnumvalmax());

			if ((int) (truncar(convertir(escalaNumericaVO.getInsnumvalmax()))) > rangoTotalMaximo
					|| (int) (truncar(convertir(escalaNumericaVO
							.getInsnumvalmin()))) < rangoTotalMinimo) {

				escalaNumericaVO
						.setInsnumvalmin(convertir(getDouble(truncar(convertir(escalaNumericaVO
								.getInsnumvalmin())))));
				escalaNumericaVO
						.setInsnumvalmax(convertir(getDouble(truncar(convertir(escalaNumericaVO
								.getInsnumvalmax())))));

				return "" + convertir(getDouble(rangoTotalMinimo)) + " a "
						+ convertir(getDouble(rangoTotalMaximo));
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
		// System.out.println("null");
		return null;
	}

	/**
	 * Retona el numero double con dos decimales 00.00
	 * 
	 * @param dou
	 * @return
	 */
	private String convertir(double valor) {
		String valor_ = "" + valor;
		String valor__ = "";
		if (valor_.lastIndexOf(".") > 0) {
			if (valor_.substring(valor_.lastIndexOf("."), valor_.length())
					.length() < 3) {
				valor__ = valor_ + "0";
			} else if (valor_.substring(valor_.lastIndexOf("."),
					valor_.length()).length() < 2) {
				valor__ = valor_ + "00";
			} else {
				valor__ = valor_;
			}

		} else {
			valor__ = valor_;
		}
		// System.out.println("-- " +
		// valor_.substring(valor_.lastIndexOf("."),valor_.length() ).length());
		// System.out.println("valor__  " + valor__ );
		return valor__;
	}

	/**
	 * @param valor
	 * @return
	 */
	private double convertir(String valor) {
		return Double.valueOf(valor).doubleValue();
	}

	/**
	 * Metodo que recibe un numero entero y retorna el double resultado de su
	 * division sobre 100
	 * 
	 * @param num
	 * @return
	 */
	private double getDouble(int num) {
		return (new Double(new Double(num).doubleValue() / 100.00))
				.doubleValue();
	}

	/**
	 * Metodo que recibe un numero double lo multiplica por 100 lo redondea y
	 * retorna la parte entera
	 * 
	 * @param num
	 * @return
	 */
	private int truncar(double num) {
		double valor = 0;

		valor = num;

		valor = valor * 100;
		valor = java.lang.Math.round(valor);
		// System.out.println("((int)valor) " + );
		// valor = ((int)valor)/100;

		return ((int) valor);

	}

	/**
	 * Valida que el nivel de evaluacion no se halla usado en en la tabla
	 * INSTITUCION_ESCALA_NUMERICA
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public boolean isValidarUsoNivelEvalEscalaNum(
			InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		String sql = "";
		try {
			cn = cursor.getConnection();

			// st=cn.prepareStatement(rb.getString("instNivelEval.isValidarUsoNivelEval"));

			sql += " " + rb.getString("instNivelEval.isUtilizadoNivelEval.2");
			// metodo
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((instNivelEvaluacionVO.getInsnivcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);

			posicion = 1;
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++,
					instNivelEvaluacionVO.getInsnivcodniveleval());
			/*
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivcodmetod());
			 * st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			 * st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivcodnivel());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivcodgrado());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivtipoevalasig());
			 * st.setLong(posicion++,
			 * instNivelEvaluacionVO.getInsnivtipoevalprees());
			 * st.setDouble(posicion++,
			 * instNivelEvaluacionVO.getInsnivvalminnum());
			 * st.setDouble(posicion++,
			 * instNivelEvaluacionVO.getInsnivvalmaxnum());
			 * st.setDouble(posicion++,
			 * instNivelEvaluacionVO.getInsnivvalaprobnum());
			 */

			if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodmetod());
			}

			if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodsede());
			}

			if (instNivelEvaluacionVO.getInsnivcodjorn() > -99) {
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodjorn());
			}

			if (instNivelEvaluacionVO.getInsnivcodnivel() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodnivel());
			}

			if (instNivelEvaluacionVO.getInsnivcodgrado() > -99) {
				st.setLong(posicion++,
						instNivelEvaluacionVO.getInsnivcodgrado());
			}

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
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
			}
		}
		return false;
	}

	/**
	 * Actualiza el campo INSNIVRANGO que me indica si la escala esta completa y
	 * no tiene ninguna problema
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public boolean actualizarNivelEvaluacionRango(
			EscalaNumericaVO escalaNumericaVO, int valor) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {

			cn = cursor.getConnection();

			// ///////////////////////////
			/**
			 * Actualizar INSNIVRANGOS 1 si los rango estan completos 0 si falta
			 * rangos por asignar
			 */
			sql = " "
					+ rb.getString("instNivelEval.actualizarNivelEvaluacionRango");
			// metodo
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaNumericaVO.getInsnumcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setInt(posicion++, valor);// Si tiene todos lo rangos asignados

			// where
			st.setLong(posicion++, escalaNumericaVO.getInsnumvigencia());
			st.setLong(posicion++, escalaNumericaVO.getInsnumcodinst());
			st.setLong(posicion++, escalaNumericaVO.getInsnumniveval());

			if (escalaNumericaVO.getInsnumcodmetod() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodmetod());
			}
			if (escalaNumericaVO.getInsnumcodsede() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodsede());
			}
			if (escalaNumericaVO.getInsnumcodjorn() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodjorn());
			}
			if (escalaNumericaVO.getInsnumcodnivel() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodnivel());
			}
			if (escalaNumericaVO.getInsnumcodgrado() > -99) {
				st.setLong(posicion++, escalaNumericaVO.getInsnumcodgrado());
			}

			int i = st.executeUpdate();

			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	/**
	 * Actualiza el campo INSNIVRANGO que me indica si la escala esta completa y
	 * no tiene ninguna problema en la tabla INSTITUCION_ESCALA_NUMERICA
	 * 
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws Exception
	 */
	public boolean actualizarNivelEvaluacionRango(
			EscalaConceptualVO escalaConceptualVO, int valor) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {

			cn = cursor.getConnection();

			// ///////////////////////////
			/**
			 * Actualizar INSNIVRANGOS 1 si los rango estan completos 0 si falta
			 * rangos por asignar
			 */
			sql = " "
					+ rb.getString("instNivelEval.actualizarNivelEvaluacionRango");
			// metodo
			sql += " "
					+ ((escalaConceptualVO.getInsconcodmetod() > -99) ? rb
							.getString("instNivelEval.METODO") : rb
							.getString("instNivelEval.METODO_IS_NULL"));
			// sede
			sql += " "
					+ ((escalaConceptualVO.getInsconcodsede() > -99) ? rb
							.getString("instNivelEval.SEDE") : rb
							.getString("instNivelEval.SEDE_IS_NULL"));
			// jorn
			sql += " "
					+ ((escalaConceptualVO.getInsconcodjorn() > -99) ? rb
							.getString("instNivelEval.JORND") : rb
							.getString("instNivelEval.JORND_IS_NULL"));
			// nivel
			sql += " "
					+ ((escalaConceptualVO.getInsconcodnivel() > -99) ? rb
							.getString("instNivelEval.NIVEL") : rb
							.getString("instNivelEval.NIVEL_IS_NULL"));
			// grado
			sql += " "
					+ ((escalaConceptualVO.getInsconcodgrado() > -99) ? rb
							.getString("instNivelEval.GRADO") : rb
							.getString("instNivelEval.GRADO_IS_NULL"));

			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setInt(posicion++, valor);// Si tiene todos lo rangos asignados

			// where
			st.setLong(posicion++, escalaConceptualVO.getInsconvigencia());
			st.setLong(posicion++, escalaConceptualVO.getInsconcodinst());
			st.setLong(posicion++, escalaConceptualVO.getInsconniveval());

			if (escalaConceptualVO.getInsconcodmetod() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodmetod());
			}
			if (escalaConceptualVO.getInsconcodsede() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodsede());
			}
			if (escalaConceptualVO.getInsconcodjorn() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodjorn());
			}
			if (escalaConceptualVO.getInsconcodnivel() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodnivel());
			}
			if (escalaConceptualVO.getInsconcodgrado() > -99) {
				st.setLong(posicion++, escalaConceptualVO.getInsconcodgrado());
			}

			int i = st.executeUpdate();

			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	/**
	 * @function: Valida si existen escalas conceptuales o numricas para este
	 *            nivel de evaluacion de institucion
	 * @param request
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws ServletException
	 */
	public String isValidarModificarInstNivelEval(
			InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {
		// System.out.println(formaFecha.format(new java.util.Date())
		// + " isValidarModificarInstNivelEval ----------- "
		// + instNivelEvaluacionVO.getInsnivvalminnum() + "-");
		String resp = null;
		try {

			if (instNivelEvaluacionVO.getInsnivvalminnumAntes() != null
					&& instNivelEvaluacionVO.getInsnivvalminnumAntes().trim()
							.length() > 1
					&& GenericValidator.isDouble(instNivelEvaluacionVO
							.getInsnivvalminnumAntes())) {
				if ((instNivelEvaluacionVO.getInsnivtipoevalasig() == ParamsVO.TIPO_EVAL_ASIG_NUM || instNivelEvaluacionVO
						.getInsnivtipoevalasig() == ParamsVO.TIPO_EVAL_ASIG_NUM)
						&& truncar(convertir(instNivelEvaluacionVO
								.getInsnivvalminnum())) != truncar(convertir(instNivelEvaluacionVO
								.getInsnivvalminnumAntes()))
						|| truncar(convertir(instNivelEvaluacionVO
								.getInsnivvalmaxnum())) != truncar(convertir(instNivelEvaluacionVO
								.getInsnivvalmaxnumAntes()))) {

					FiltroEscalaNumVO filtroEscalaNumVO = new FiltroEscalaNumVO();

					filtroEscalaNumVO.setFilNumVigencia(instNivelEvaluacionVO
							.getInsnivvigencia());
					filtroEscalaNumVO.setFilNumInst(instNivelEvaluacionVO
							.getInsnivcodinst());
					filtroEscalaNumVO.setFilNumniveval(instNivelEvaluacionVO
							.getInsnivcodniveleval());
					filtroEscalaNumVO.setFilNumSede(instNivelEvaluacionVO
							.getInsnivcodsede());
					filtroEscalaNumVO.setFilNumJorn(instNivelEvaluacionVO
							.getInsnivcodjorn());
					filtroEscalaNumVO.setFilNumMetodo(instNivelEvaluacionVO
							.getInsnivcodmetod());
					filtroEscalaNumVO.setFilNumNivel(instNivelEvaluacionVO
							.getInsnivcodnivel());
					filtroEscalaNumVO.setFilNumGrado(instNivelEvaluacionVO
							.getInsnivcodgrado());

					//
					if (getListaEscalaNum(filtroEscalaNumVO).size() > 0) {
						// Informaci{on actualizada satifactoriamente

						return "No es posible modificar el valor Mínimo o Máximo porque existen rangos relacionados. Si desea modificar esta información primero debe eliminar los rangos que hacen referencia a esta escala.";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new ServletException("" + e.getMessage());

		}
		return resp;
	}

	/**
	 * @function: Pregunta si se han configurado niveles de evaluacinn para la
	 *            institucinn
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public String isExisteInstNivelEval(long vigencia, long codInst)
			throws Exception {
		// System.out.println("isExisteInstNivelEval");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		int posicion = 1;
		String mensaje = "";
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("isExisteInstNivelEval.ASIGNATURA"));
			posicion = 1;
			// System.out.println("consulta validacion "
			// + rb.getString("isExisteInstNivelEval.ASIGNATURA"));
			st.setLong(posicion++, vigencia);
			// System.out.println("vigencia  " + vigencia);
			st.setLong(posicion++, codInst);
			// System.out.println("codInst " + codInst);
			st.setLong(posicion++, vigencia);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Nnmero de estudiantes evaluados por asignatrua "
						+ rs.getInt(1) + ".";
			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb.getString("isExisteInstNivelEval.AREA"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Nnmero de estudiantes evaluados por area "
						+ rs.getInt(1) + ".";
			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb
					.getString("isExisteInstNivelEval.COMPOR"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Nnmero de estudiantes evaluados por comportamiento  "
						+ rs.getInt(1) + ".";
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
		return mensaje;
	}

	/**
	 * @function: Pregunta si se han configurado niveles de evaluacinn para la
	 *            institucinn
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public String isExisteInstNivelEvalPree(long vigencia, long codInst) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		int i = 1;
		int posicion = 1;
		String mensaje = "";
		
		try {

			cn = cursor.getConnection();

			posicion = 1;
			
			st = cn.prepareStatement(rb.getString("isExisteInstNivelEval.PREES"));
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Número de estudiantes de preescolar evaluados por dimensiones " + rs.getInt(1) + ".";
			}
			
			rs.close();
			st.close();

			posicion = 1;
			
			st = cn.prepareStatement(rb.getString("isExisteInstNivelEval.PREES_ASIG"));
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Número de estudiantes de preescolar evaluados por asignatura "	+ rs.getInt(1) + ".";
			}
			
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("Error sqle " + sqle.getMessage());
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
		
		return mensaje;
		
	}

	
	/**
	 * @function: Elimina los registros de INSTITUCION_NIVEL_EVALUACION segnn
	 *            los parnmetros vigencia y cndigo de la institucinn
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public void eliminarAllInstNivelEval(long vigencia, long codInst)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 1;
		int posicion = 1;
		String sql = "";
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("eliminarAllInstNivelEval"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			st.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	/**
	 * @function: Pregunta si se han creado literales o escalas conceptuales
	 *            para determinada vigencia y cndigo de institucinn
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteEscalaConceptual(long vigencia, long codInst)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		int posicion = 1;
		String sql = "";
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("isExisteEscalaConceptual"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				return true;
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
		return false;
	}

	/**
	 * @function: Elimina los registros de nivel evalacion de institucion escala
	 *            conceptual y escala numerica segun lo parnmetros vigencia e
	 *            institucinn
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public void eliminarEscalaEnCascada(long vigencia, long codInst)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 1;
		int posicion = 1;
		String sql = "";
		try {

			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			st = cn.prepareStatement(rb.getString("eliminarAllInstNivelEval"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			st.executeUpdate();

			st = cn.prepareStatement(rb
					.getString("eliminarAllEscalaConceptual"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			st.executeUpdate();

			st = cn.prepareStatement(rb.getString("eliminarAllEscalaNumerico"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			st.executeUpdate();

			cn.setAutoCommit(true);
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	/**
	 * @function: Pregunta si se han creado literales o escalas numnricas para
	 *            determinada vigencia y cndigo de institucinn
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public boolean isExisteEscalaNumerico(long vigencia, long codInst)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		int posicion = 1;
		String sql = "";
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("isExisteEscalaNumerico"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				return true;
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
		return false;
	}

	/**
	 * @function: Elimina los registros de escala numerica segun lo parnmetros
	 *            vigencia e institucinn
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public void eliminarEscalaNumerico(long vigencia, long codInst)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 1;
		int posicion = 1;
		String sql = "";
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("eliminarAllEscalaNumerico"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);

			st.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	/**
	 * @function: Pregunta si se han utlizado un determinado literal en
	 *            evaluacinn de asignatura o area o de comportamiento
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public String isUsoEscala(long vigencia, long codInst, long codLiteral)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		int posicion = 1;
		String sql = "";
		String mensaje = "";
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("isUsoEscala.ASIGNATRUA"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codLiteral);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Nnmero de estudiantes evaluados por asignatura "
						+ rs.getInt(1) + ".";
			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb.getString("isUsoEscala.AREA"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codLiteral);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Nnmero de estudiantes evaluados por area "
						+ rs.getInt(1) + ".";
			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb.getString("isUsoEscala.COMP"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codLiteral);

			rs = st.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				mensaje += "\n     - Nnmero de estudiantes evaluados por comportamiento "
						+ rs.getInt(1) + ".";
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
		return mensaje;
	}

	/**
	 * @function: Pregunta SI SE HA EVALUADO PARA UN PERIODO MAYOR AL QUE SE
	 *            QUIERE ACTUALIZAR
	 * @param filtroEvaluacion
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public boolean isNumPeriodoInstNivelEval(long vigencia, long codInst,
			long numPerd) throws Exception {
		// System.out.println("isNumPeriodoInstNivelEval numPerd" + numPerd);

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		int posicion = 1;
		String Sql = "";
		try {

			cn = cursor.getConnection();

			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.ASIGNATURA_7");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.ASIGNATURA_6");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.ASIGNATURA_5");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.ASIGNATURA_4");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.ASIGNATURA_3");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.ASIGNATURA_2");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.ASIGNATURA_1");

			// System.out.println(Sql);
			st = cn.prepareStatement(Sql);
			posicion = 1;
			// Periodo 7
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 6
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 5
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 4
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 3
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 2
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 1
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			rs = st.executeQuery();

			if (rs.next()) {
				// System.out.println("rs.getInt(1)>  " + rs.getInt(1));
				if (numPerd < rs.getInt(1)) {
					return true;
				}
			}
			rs.close();
			st.close();

			Sql = " " + rb.getString("isNumPeriodoInstNivelEval.AREA_7");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.AREA_6");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.AREA_5");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.AREA_4");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.AREA_3");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.AREA_2");
			Sql += " " + rb.getString("isNumPeriodoInstNivelEval.AREA_1");

			// System.out.println(Sql);
			st = cn.prepareStatement(Sql);
			posicion = 1;
			// Periodo 7
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 6
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 5
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 4
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 3
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 2
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			// Periodo 1
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, vigencia);

			rs = st.executeQuery();

			if (rs.next()) {
				// System.out.println("rs.getInt(1)>  " + rs.getInt(1));
				if (numPerd < rs.getInt(1)) {
					return true;
				}
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
		return false;
	}

	public boolean isExistePerProg(long vigencia, long institucion)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = null;
		List list = new ArrayList();
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("periodo.isExistePeriodo"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, institucion);
			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
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
			}
		}
		return false;
	}

	/**
	 * @param pVO
	 * @return
	 * @throws Exception
	 */
	public PeriodoPrgfechasVO guardarPerProgFechas(PeriodoPrgfechasVO pVO)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		// java.util.Date fecha = new java.util.Date();
		// DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("perdProgFechas.guardar"));
			posicion = 1;

			// , , , , , , , PRF_INI_PER2, PRF_FIN_PER2, PRF_CIERREP2,
			// PRF_ALERTAP2, PRF_DIASP2, PRF_INI_PER3, PRF_FIN_PER3,
			// PRF_CIERREP3,PRF_ALERTAP3, PRF_DIASP3, PRF_INI_PER4,
			// PRF_FIN_PER4, PRF_CIERREP4, PRF_ALERTAP4, PRF_DIASP4,
			// PRF_INI_PER5, PRF_FIN_PER5, PRF_CIERREP5, PRF_ALERTAP5,
			// PRF_DIASP5, PRF_INI_PER6, PRF_FIN_PER6,
			// PRF_CIERREP6,PRF_ALERTAP6, PRF_DIASP6, PRF_INI_PER7,
			// PRF_FIN_PER7, PRF_CIERREP7, PRF_ALERTAP7,PRF_DIASP7, PRF_USUARIO,
			// PRF_FECHA
			st.setInt(posicion++, pVO.getPrfvigencia());// PRFVIGENCIA
			st.setInt(posicion++, pVO.getPrfcodinst());// PRFCODINST

			// PRF_INI_PER1
			/*
			 * System.out.println("pVO.getPrf_ini_per1() " +
			 * pVO.getPrf_ini_per1()); if(pVO.getPrf_ini_per1() != null &&
			 * pVO.getPrf_ini_per1().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_ini_per1()).getTime() ));
			 * }else{ st.setNull(posicion++, Types.DATE); } //PRF_FIN_PER1
			 * System.out.println("pVO.getPrf_fin_per1() " +
			 * pVO.getPrf_fin_per1()); if(pVO.getPrf_fin_per1() != null &&
			 * pVO.getPrf_fin_per1().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_fin_per1()).getTime() ));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * st.setInt(posicion++, pVO.getPrf_cierrep1()); //PRF_CIERREP1
			 * st.setInt(posicion++, pVO.getPrf_alertap1()); //PRF_ALERTAP1
			 * st.setInt(posicion++, pVO.getPrf_diasp1());//PRF_DIASP1
			 * if(pVO.getPrf_ini_per2() != null &&
			 * pVO.getPrf_ini_per2().trim().length()>0){
			 * //st.setDate(posicion++, new
			 * Date(dateFormat.parse(pVO.getPrf_ini_per2()).getTime() ));
			 * st.setString(posicion++, pVO.getPrf_ini_per1()); }else{
			 * st.setNull(posicion++, Types.DATE); }
			 * 
			 * if(pVO.getPrf_fin_per2() != null &&
			 * pVO.getPrf_fin_per2().trim().length()>0){
			 * //st.setDate(posicion++, new
			 * Date(dateFormat.parse(pVO.getPrf_fin_per2()).getTime() ));
			 * st.setString(posicion++, pVO.getPrf_ini_per1()); }else{
			 * st.setNull(posicion++, Types.DATE); } st.setInt(posicion++,
			 * pVO.getPrf_cierrep2()); st.setInt(posicion++,
			 * pVO.getPrf_alertap2()); st.setInt(posicion++,
			 * pVO.getPrf_diasp2());
			 * 
			 * if(pVO.getPrf_ini_per3() != null &&
			 * pVO.getPrf_ini_per3().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_ini_per3()).getTime() ));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * if(pVO.getPrf_fin_per3() != null &&
			 * pVO.getPrf_fin_per3().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_fin_per3()).getTime()));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * st.setInt(posicion++, pVO.getPrf_cierrep3());
			 * st.setInt(posicion++, pVO.getPrf_alertap3());
			 * st.setInt(posicion++, pVO.getPrf_diasp3());
			 * 
			 * 
			 * if(pVO.getPrf_ini_per4() != null &&
			 * pVO.getPrf_ini_per4().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_ini_per4()).getTime() ));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * if(pVO.getPrf_fin_per4() != null &&
			 * pVO.getPrf_fin_per4().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_fin_per4()).getTime() ));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * st.setInt(posicion++, pVO.getPrf_cierrep4());
			 * st.setInt(posicion++, pVO.getPrf_alertap4());
			 * st.setInt(posicion++, pVO.getPrf_diasp4());
			 * 
			 * if(pVO.getPrf_ini_per5() != null &&
			 * pVO.getPrf_ini_per5().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_ini_per5() ).getTime()));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * if(pVO.getPrf_ini_per5() != null &&
			 * pVO.getPrf_fin_per5().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_fin_per5()).getTime() ));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * st.setInt(posicion++, pVO.getPrf_cierrep5());
			 * st.setInt(posicion++, pVO.getPrf_alertap5());
			 * st.setInt(posicion++, pVO.getPrf_diasp5());
			 * 
			 * if(pVO.getPrf_ini_per6() != null &&
			 * pVO.getPrf_ini_per6().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_ini_per6()).getTime() ));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * if(pVO.getPrf_fin_per6() != null &&
			 * pVO.getPrf_fin_per6().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_fin_per6()).getTime()));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * 
			 * 
			 * st.setInt(posicion++, pVO.getPrf_cierrep6());
			 * st.setInt(posicion++, pVO.getPrf_alertap6());
			 * st.setInt(posicion++, pVO.getPrf_diasp6());
			 * if(pVO.getPrf_ini_per7() != null &&
			 * pVO.getPrf_ini_per7().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_ini_per7() ).getTime()));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * if(pVO.getPrf_fin_per7() != null &&
			 * pVO.getPrf_fin_per7().trim().length()>0){ st.setDate(posicion++,
			 * new Date(dateFormat.parse(pVO.getPrf_fin_per7() ).getTime()));
			 * }else{ st.setNull(posicion++, Types.DATE); }
			 * 
			 * st.setInt(posicion++, pVO.getPrf_cierrep7());
			 * st.setInt(posicion++, pVO.getPrf_alertap7());
			 * st.setInt(posicion++, pVO.getPrf_diasp7());
			 */
			st.setString(posicion++, pVO.getPrf_ini_per1());
			st.setString(posicion++, pVO.getPrf_fin_per1());
			st.setInt(posicion++, pVO.getPrf_cierrep1());
			st.setInt(posicion++, pVO.getPrf_alertap1());
			st.setInt(posicion++, pVO.getPrf_diasp1());

			st.setString(posicion++, pVO.getPrf_ini_per2());
			st.setString(posicion++, pVO.getPrf_fin_per2());
			st.setInt(posicion++, pVO.getPrf_cierrep2());
			st.setInt(posicion++, pVO.getPrf_alertap2());
			st.setInt(posicion++, pVO.getPrf_diasp2());

			st.setString(posicion++, pVO.getPrf_ini_per3());
			st.setString(posicion++, pVO.getPrf_fin_per3());
			st.setInt(posicion++, pVO.getPrf_cierrep3());
			st.setInt(posicion++, pVO.getPrf_alertap3());
			st.setInt(posicion++, pVO.getPrf_diasp3());

			st.setString(posicion++, pVO.getPrf_ini_per4());
			st.setString(posicion++, pVO.getPrf_fin_per4());
			st.setInt(posicion++, pVO.getPrf_cierrep4());
			st.setInt(posicion++, pVO.getPrf_alertap4());
			st.setInt(posicion++, pVO.getPrf_diasp4());

			st.setString(posicion++, pVO.getPrf_ini_per5());
			st.setString(posicion++, pVO.getPrf_fin_per5());
			st.setInt(posicion++, pVO.getPrf_cierrep5());
			st.setInt(posicion++, pVO.getPrf_alertap5());
			st.setInt(posicion++, pVO.getPrf_diasp5());

			st.setString(posicion++, pVO.getPrf_ini_per6());
			st.setString(posicion++, pVO.getPrf_fin_per6());
			st.setInt(posicion++, pVO.getPrf_cierrep6());
			st.setInt(posicion++, pVO.getPrf_alertap6());
			st.setInt(posicion++, pVO.getPrf_diasp6());

			st.setString(posicion++, pVO.getPrf_ini_per7());
			st.setString(posicion++, pVO.getPrf_fin_per7());
			st.setInt(posicion++, pVO.getPrf_cierrep7());
			st.setInt(posicion++, pVO.getPrf_alertap7());
			st.setInt(posicion++, pVO.getPrf_diasp7());

			st.setLong(posicion++, pVO.getPrf_usuario());
			// st.setString(posicion++, pVO.getPrf_fecha());
			st.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return pVO;
	}

	/**
	 * @param pVO
	 * @return
	 * @throws Exception
	 */
	public PeriodoPrgfechasVO actualizarPerProgFechas(PeriodoPrgfechasVO pVO)
			throws Exception {
		// System.out.println("actualizarPerProgFechas");
		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("perdProgFechas.actualizar"));
			posicion = 1;
			st.setString(posicion++, pVO.getPrf_ini_per1());
			st.setString(posicion++, pVO.getPrf_fin_per1());
			st.setInt(posicion++, pVO.getPrf_cierrep1());
			st.setInt(posicion++, pVO.getPrf_alertap1());
			st.setInt(posicion++, pVO.getPrf_diasp1());

			st.setString(posicion++, pVO.getPrf_ini_per2());
			st.setString(posicion++, pVO.getPrf_fin_per2());
			st.setInt(posicion++, pVO.getPrf_cierrep2());
			st.setInt(posicion++, pVO.getPrf_alertap2());
			st.setInt(posicion++, pVO.getPrf_diasp2());

			st.setString(posicion++, pVO.getPrf_ini_per3());
			st.setString(posicion++, pVO.getPrf_fin_per3());
			st.setInt(posicion++, pVO.getPrf_cierrep3());
			st.setInt(posicion++, pVO.getPrf_alertap3());
			st.setInt(posicion++, pVO.getPrf_diasp3());

			st.setString(posicion++, pVO.getPrf_ini_per4());
			st.setString(posicion++, pVO.getPrf_fin_per4());
			st.setInt(posicion++, pVO.getPrf_cierrep4());
			st.setInt(posicion++, pVO.getPrf_alertap4());
			st.setInt(posicion++, pVO.getPrf_diasp4());

			st.setString(posicion++, pVO.getPrf_ini_per5());
			st.setString(posicion++, pVO.getPrf_fin_per5());
			st.setInt(posicion++, pVO.getPrf_cierrep5());
			st.setInt(posicion++, pVO.getPrf_alertap5());
			st.setInt(posicion++, pVO.getPrf_diasp5());

			st.setString(posicion++, pVO.getPrf_ini_per6());
			st.setString(posicion++, pVO.getPrf_fin_per6());
			st.setInt(posicion++, pVO.getPrf_cierrep6());
			st.setInt(posicion++, pVO.getPrf_alertap6());
			st.setInt(posicion++, pVO.getPrf_diasp6());

			st.setString(posicion++, pVO.getPrf_ini_per7());
			st.setString(posicion++, pVO.getPrf_fin_per7());
			st.setInt(posicion++, pVO.getPrf_cierrep7());
			st.setInt(posicion++, pVO.getPrf_alertap7());
			st.setInt(posicion++, pVO.getPrf_diasp7());

			st.setLong(posicion++, pVO.getPrf_usuario());
			// st.setString(posicion++, pVO.getPrf_fecha());
			// where
			st.setInt(posicion++, pVO.getPrfvigencia());
			st.setInt(posicion++, pVO.getPrfcodinst());

			int i = st.executeUpdate();

			// System.out.println("actualizo i = " + i);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return pVO;
	}

	/**
	 * @param vigencia
	 * @param institucion
	 * @return
	 * @throws Exception
	 */
	public boolean isExistePerProFecha(long vigencia, long institucion)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("perdProgFechas.isExistePerProFecha"));
			posicion = 1;
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, institucion);
			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
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
			}
		}
		return false;
	}

	public PeriodoPrgfechasVO getPerProgFechas(PeriodoPrgfechasVO pVO)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int count = 0;
		int posicion = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("perdProgFechas.getPerdProgFechas"));
			posicion = 1;
			st.setLong(posicion++, pVO.getPrfcodinst());
			st.setLong(posicion++, pVO.getPrfvigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				pVO.setPrf_ini_per1(rs.getString(posicion++));
				pVO.setPrf_fin_per1(rs.getString(posicion++));
				pVO.setPrf_cierrep1(rs.getInt(posicion++));
				pVO.setPrf_alertap1(rs.getInt(posicion++));
				pVO.setPrf_diasp1(rs.getInt(posicion++));

				pVO.setPrf_ini_per2(rs.getString(posicion++));
				pVO.setPrf_fin_per2(rs.getString(posicion++));
				pVO.setPrf_cierrep2(rs.getInt(posicion++));
				pVO.setPrf_alertap2(rs.getInt(posicion++));
				pVO.setPrf_diasp2(rs.getInt(posicion++));

				pVO.setPrf_ini_per3(rs.getString(posicion++));
				pVO.setPrf_fin_per3(rs.getString(posicion++));
				pVO.setPrf_cierrep3(rs.getInt(posicion++));
				pVO.setPrf_alertap3(rs.getInt(posicion++));
				pVO.setPrf_diasp3(rs.getInt(posicion++));

				pVO.setPrf_ini_per4(rs.getString(posicion++));
				pVO.setPrf_fin_per4(rs.getString(posicion++));
				pVO.setPrf_cierrep4(rs.getInt(posicion++));
				pVO.setPrf_alertap4(rs.getInt(posicion++));
				pVO.setPrf_diasp4(rs.getInt(posicion++));
				pVO.setPrf_ini_per5(rs.getString(posicion++));
				pVO.setPrf_fin_per5(rs.getString(posicion++));
				pVO.setPrf_cierrep5(rs.getInt(posicion++));
				pVO.setPrf_alertap5(rs.getInt(posicion++));
				pVO.setPrf_diasp5(rs.getInt(posicion++));
				pVO.setPrf_ini_per6(rs.getString(posicion++));
				pVO.setPrf_fin_per6(rs.getString(posicion++));
				pVO.setPrf_cierrep6(rs.getInt(posicion++));
				pVO.setPrf_alertap6(rs.getInt(posicion++));
				pVO.setPrf_diasp6(rs.getInt(posicion++));
				pVO.setPrf_ini_per7(rs.getString(posicion++));
				pVO.setPrf_fin_per7(rs.getString(posicion++));
				pVO.setPrf_cierrep7(rs.getInt(posicion++));
				pVO.setPrf_alertap7(rs.getInt(posicion++));
				pVO.setPrf_diasp7(rs.getInt(posicion++));
				pVO.setPrf_usuario(rs.getLong(posicion++));
				pVO.setPrf_fecha(rs.getString(posicion++));

			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb
					.getString("perdProgFechas.obtenerEstadoPerd"));
			posicion = 1;
			st.setLong(posicion++, pVO.getPrfcodinst());
			st.setLong(posicion++, pVO.getPrfvigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				pVO.setPerestado1(rs.getInt(posicion++));
				pVO.setPerestado2(rs.getInt(posicion++));
				pVO.setPerestado3(rs.getInt(posicion++));

				pVO.setPerestado4(rs.getInt(posicion++));
				pVO.setPerestado5(rs.getInt(posicion++));
				pVO.setPerestado6(rs.getInt(posicion++));
				pVO.setPerestado7(rs.getInt(posicion++));
			}

			rs.close();
			st.close();

			st = cn.prepareStatement(rb
					.getString("perdProgFechas.countEstadoPerd"));
			posicion = 1;
			st.setLong(posicion++, pVO.getPrfcodinst());
			st.setLong(posicion++, pVO.getPrfvigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				count = rs.getInt(posicion++);

			}
			rs.close();
			st.close();

			if (count > 0) {
				pVO.setPerestado1(pVO.getPerestado1() / count);
				pVO.setPerestado2(pVO.getPerestado2() / count);
				pVO.setPerestado3(pVO.getPerestado3() / count);

				pVO.setPerestado4(pVO.getPerestado4() / count);
				pVO.setPerestado5(pVO.getPerestado5() / count);
				pVO.setPerestado6(pVO.getPerestado6() / count);
				pVO.setPerestado7(pVO.getPerestado7() / count);
			} else {
				new Exception("No existen registro en la tabla periodo");
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
		return pVO;
	}

	/**
	 * @funtion Actualiza al estado 0 todos los registro que esten en esta 1
	 *          Nota: El servidor puede ser reiniciado justo cuando se este
	 *          ejecutando el cierre de periodo de un colegio.
	 * @return
	 * @throws Exception
	 */
	public boolean updateEstado() throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("hiloPerProgFecha.updateEnCola"));
			pst.setInt(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			pst.setInt(posicion++, ParamsVO.HILO_TIPO_PER_PROG_FECHAS);
			pst.setInt(posicion++, ParamsVO.ESTADO_REPORTE_EJE);
			posicion = 1;
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);

			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return true;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaPredProgFechas() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		List lista = new ArrayList();

		int posicion = 1;
		int count = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.getListaPredProgFechas"));
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				PeriodoPrgfechasVO pVO = new PeriodoPrgfechasVO();
				pVO.setPrfcodinst(rs.getInt(posicion++));
				pVO.setPrfvigencia(rs.getInt(posicion++));
				pVO.setPrf_ini_per1(rs.getString(posicion++));
				pVO.setPrf_fin_per1(rs.getString(posicion++));
				pVO.setPrf_cierrep1(rs.getInt(posicion++));
				pVO.setPrf_alertap1(rs.getInt(posicion++));
				pVO.setPrf_diasp1(rs.getInt(posicion++));

				pVO.setPrf_ini_per2(rs.getString(posicion++));
				pVO.setPrf_fin_per2(rs.getString(posicion++));
				pVO.setPrf_cierrep2(rs.getInt(posicion++));
				pVO.setPrf_alertap2(rs.getInt(posicion++));
				pVO.setPrf_diasp2(rs.getInt(posicion++));

				pVO.setPrf_ini_per3(rs.getString(posicion++));
				pVO.setPrf_fin_per3(rs.getString(posicion++));
				pVO.setPrf_cierrep3(rs.getInt(posicion++));
				pVO.setPrf_alertap3(rs.getInt(posicion++));
				pVO.setPrf_diasp3(rs.getInt(posicion++));

				pVO.setPrf_ini_per4(rs.getString(posicion++));
				pVO.setPrf_fin_per4(rs.getString(posicion++));
				pVO.setPrf_cierrep4(rs.getInt(posicion++));
				pVO.setPrf_alertap4(rs.getInt(posicion++));
				pVO.setPrf_diasp4(rs.getInt(posicion++));
				pVO.setPrf_ini_per5(rs.getString(posicion++));
				pVO.setPrf_fin_per5(rs.getString(posicion++));
				pVO.setPrf_cierrep5(rs.getInt(posicion++));
				pVO.setPrf_alertap5(rs.getInt(posicion++));
				pVO.setPrf_diasp5(rs.getInt(posicion++));
				pVO.setPrf_ini_per6(rs.getString(posicion++));
				pVO.setPrf_fin_per6(rs.getString(posicion++));
				pVO.setPrf_cierrep6(rs.getInt(posicion++));
				pVO.setPrf_alertap6(rs.getInt(posicion++));
				pVO.setPrf_diasp6(rs.getInt(posicion++));
				pVO.setPrf_ini_per7(rs.getString(posicion++));
				pVO.setPrf_fin_per7(rs.getString(posicion++));
				pVO.setPrf_cierrep7(rs.getInt(posicion++));
				pVO.setPrf_alertap7(rs.getInt(posicion++));
				pVO.setPrf_diasp7(rs.getInt(posicion++));
				pVO.setPrf_usuario(rs.getLong(posicion++));
				pVO.setPrf_fecha(rs.getString(posicion++));

				st2 = cn.prepareStatement(rb
						.getString("perdProgFechas.obtenerEstadoPerd"));
				posicion = 1;
				st2.setLong(posicion++, pVO.getPrfcodinst());
				st2.setLong(posicion++, pVO.getPrfvigencia());
				rs2 = st2.executeQuery();
				if (rs2.next()) {
					posicion = 1;
					pVO.setPerestado1(rs2.getInt(posicion++));
					pVO.setPerestado2(rs2.getInt(posicion++));
					pVO.setPerestado3(rs2.getInt(posicion++));

					pVO.setPerestado4(rs2.getInt(posicion++));
					pVO.setPerestado5(rs2.getInt(posicion++));
					pVO.setPerestado6(rs2.getInt(posicion++));
					pVO.setPerestado7(rs2.getInt(posicion++));
				}

				rs2.close();
				st2.close();

				st2 = cn.prepareStatement(rb
						.getString("perdProgFechas.countEstadoPerd"));
				posicion = 1;
				st2.setLong(posicion++, pVO.getPrfcodinst());
				st2.setLong(posicion++, pVO.getPrfvigencia());
				rs2 = st2.executeQuery();
				if (rs2.next()) {
					posicion = 1;
					count = rs2.getInt(posicion++);

				}
				rs2.close();
				st2.close();
				if (count > 0) {
					pVO.setPerestado1(pVO.getPerestado1() / count);
					pVO.setPerestado2(pVO.getPerestado2() / count);
					pVO.setPerestado3(pVO.getPerestado3() / count);

					pVO.setPerestado4(pVO.getPerestado4() / count);
					pVO.setPerestado5(pVO.getPerestado5() / count);
					pVO.setPerestado6(pVO.getPerestado6() / count);
					pVO.setPerestado7(pVO.getPerestado7() / count);
				} else {
					new Exception("No existen registro en la tabla periodo");
				}
				//
				// System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				// System.out.println(pVO.getPerestado1() );
				// System.out.println(pVO.getPerestado2() );
				// System.out.println(pVO.getPerestado3() );
				//
				// System.out.println(pVO.getPerestado4() );
				// System.out.println(pVO.getPerestado5() );
				// System.out.println(pVO.getPerestado6() );
				// System.out.println(pVO.getPerestado7() );
				//
				rs2.close();
				st2.close();
				lista.add(pVO);
			}

			rs.close();
			st.close();
			// for(int i=0;i<lista.size();i++ ){
			// PeriodoPrgfechasVO pVO = (PeriodoPrgfechasVO)lista.get(i);
			//
			//
			// }
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {

				OperacionesGenerales.closeResultSet(rs2);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	/**
	 * Obtiene los registroS para realizar el cierre
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getListaPredProgFechasCierre() throws Exception {
		// logger.fine()
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		PeriodoPrgfechasVO pVO;
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.getListaPredProgFechasCierre"));
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				pVO = new PeriodoPrgfechasVO();
				// 1, PRFCODINST, PRFVIGENCIA, to_char(PRF_INI_PER1,
				// 'DD/MM/YYYY'), to_char(PRF_FIN_PER1, 'DD/MM/YYYY'),
				// PRF_CIERREP1, PRF_ALERTAP1, PRF_DIASP1

				pVO.setPrf_periodo(rs.getInt(posicion++));
				pVO.setPrfcodinst(rs.getInt(posicion++));
				pVO.setPrfvigencia(rs.getInt(posicion++));

				pVO.setPrf_ini_perCerrar(rs.getString(posicion++));
				pVO.setPrf_fin_perCerrar(rs.getString(posicion++));
				pVO.setPrf_cierrepCerrar(rs.getInt(posicion++));
				pVO.setPrf_alertapCerrar(rs.getInt(posicion++));
				pVO.setPrf_diaspCerrar(rs.getInt(posicion++));

				lista.add(pVO);
			}

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	/**
	 * @param pVO
	 * @return
	 * @throws Exception
	 */
	public void cerrarPeriodoTotal(PeriodoPrgfechasVO pVO) throws Exception {
		int posicion;
		Connection cn = null;
		int n;
		int estado = 1;
		long institucion = pVO.getPrfcodinst();
		long per = pVO.getPrf_periodo();
		long vigencia = pVO.getPrfvigencia();
		PreparedStatement pst = null;

		try {

			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			// PARA ASIGNATURAS
			pst = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.actualizarGruposAsignatura"
							+ per));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, estado);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			n = 0;
			n = pst.executeUpdate();
			// System.out.println("cerrando grupos="+n);
			pst.close();

			pst = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.cierre.cerrarTodosAsignaturas"
							+ per));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, vigencia);
			pst.setLong(posicion++, estado);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			pst.setLong(posicion++, vigencia);
			n = 0;
			n = pst.executeUpdate();
			// System.out.println("cerrando grupos="+n);
			pst.close();

			// PARA AREAS
			pst = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.actualizarGruposArea" + per));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, estado);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			n = 0;
			n = pst.executeUpdate();
			// System.out.println("cerrando grupos="+n);
			pst.close();
			pst = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.cierre.cerrarTodosAreas" + per));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, vigencia);
			pst.setLong(posicion++, estado);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			pst.setLong(posicion++, vigencia);
			n = 0;
			n = pst.executeUpdate();
			// System.out.println("cerrando grupos2="+n);
			pst.close();
			// preescolar
			pst = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.actualizarGruposPreescolar"
							+ per));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, estado);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			n = 0;
			n = pst.executeUpdate();
			// System.out.println("cerrando grupos pree="+n);
			pst.close();
			pst = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.cierre.cerrarTodosPreescolar"
							+ per));
			pst.clearBatch();
			posicion = 1;
			pst.setLong(posicion++, vigencia);
			pst.setLong(posicion++, estado);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			pst.addBatch();
			n = 0;
			n = pst.executeBatch().length;
			// System.out.println("Inserto pree"+n);
			pst.close();
			// pst=cn.prepareStatement(rb.getString((tipo==-1?"hiloPerProgFecha.ActualizarEstadoPeriodo"+per:"ActualizarEstadoPeriodoActualizacion"+per)));
			pst = cn.prepareStatement(rb
					.getString((1 == -1 ? "hiloPerProgFecha.ActualizarEstadoPeriodo"
							+ per
							: "hiloPerProgFecha.ActualizarEstadoPeriodoActualizacion"
									+ per)));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, estado);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			n = 0;
			n = pst.executeUpdate();
			// System.out.println("Cerro "+n);
			cn.commit();

			promedioAreasAsignaturas(vigencia, institucion, 0, 0, (int) per, 1,
					0, 0);

			callPuestoEst(vigencia, institucion, 0, 0, (int) per);
			cn.setAutoCommit(true);
			System.out
					.println("SE REALIZO CIERRE DEL COLEGIO "
							+ institucion
							+ "  POR CIERRE AUTOMATICO DEL SISTEMA ###########################################");
		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(in);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			throw new Exception(sqle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
				throw new Exception(inte);
			}

		}

	}

	/**
	 * @funtion Ejecuta procedimiento para el calculo de la nota de area<BR>
	 * @param vig
	 * @param inst
	 * @param sede
	 * @param jor
	 * @param per
	 * @param accion
	 * @param jerGrupo
	 * @param asig
	 * @return
	 */
	public void promedioAreasAsignaturas(long vig, long inst, long sede,
			long jor, int per, int accion, long jerGrupo, long asig)
			throws Exception {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;

		try {

			con = cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{call ACT_PROM_AREA_ASIG(?,?,?,?,?,?,?,?)}");
			cstmt.setLong(posicion++, vig);
			cstmt.setInt(posicion++, per);
			cstmt.setLong(posicion++, inst);
			cstmt.setLong(posicion++, sede);
			cstmt.setLong(posicion++, jor);
			cstmt.setLong(posicion++, jerGrupo);
			cstmt.setLong(posicion++, asig);
			cstmt.setLong(posicion++, accion);
			System.out
					.println("ACT PROMEDIO AREA_ASIGNATURA:  - Inicia procedimiento Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.executeUpdate();
			System.out
					.println("ACT PROMEDIO AREA_ASIGNATURA: - Fin procedimiento Hora: "
							+ new java.sql.Timestamp(System.currentTimeMillis())
									.toString());
			cstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}

	/**
	 * @param vig
	 * @param inst
	 * @param sede
	 * @param jor
	 * @param per
	 * @param accion
	 * @param jerGrupo
	 * @param asig
	 * @return
	 */
	public void callPuestoEst(long vig, long inst, long sede, long jor, int per)
			throws Exception {
		// System.out.println("callPuestoEst");
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;

		try {

			con = cursor.getConnection();
			/* callable statement */
			cstmt = con
					.prepareCall("{CALL ESTUDIANTE_PUESTO( ?, ?, ?, ?, ?,-99 ,-99, -99, -99, TO_CHAR(SYSTIMESTAMP,'DD-MON-YYYYHH24:MI:SS.FF'))}");
			cstmt.setLong(posicion++, vig);
			// System.out.println("vig " + vig);
			cstmt.setInt(posicion++, per);
			// System.out.println("per " + per);
			cstmt.setLong(posicion++, inst);
			// System.out.println("inst " + inst);
			cstmt.setLong(posicion++, sede);
			// System.out.println("sede " + sede);
			cstmt.setLong(posicion++, jor);
			// System.out.println("jor " + jor);
			// System.out.println("callPuestoEst:  - Inicia procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.executeUpdate();
			// System.out.println("callPuestoEst: - Fin procedimiento Hora: "
			// + new java.sql.Timestamp(System.currentTimeMillis())
			// .toString());
			cstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}

	public String getListaPerfiles() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		PeriodoPrgfechasVO pVO;
		String str = "";
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getListaPerfiles"));
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				lista.add("" + rs.getInt(posicion++));
			}

			// System.out.println("lista " + lista.size() );
			for (int i = 0; i < lista.size(); i++) {
				str += lista.get(i) + (i == (lista.size() - 1) ? "" : ",");
			}

			return str;
		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
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
	}

	/**
	 * Registra los hilos correspondientes
	 * 
	 * @return
	 * @throws Exception
	 */
	public List registrarHilosPerprgF(PeriodoPrgfechasVO pVO, int tipoHilo)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		List lista = new ArrayList();

		int posicion = 1;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.registrarHilos"));

			st.setInt(posicion++, pVO.getPrfvigencia());
			st.setInt(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			st.setString(posicion++, "-999");
			st.setInt(posicion++, tipoHilo);
			st.setInt(posicion++, pVO.getPrfcodinst());

			st.executeUpdate();
			st.close();

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	/**
	 * Actualiza el estado del hilo en la tabla DATOS_HILOS
	 * 
	 * @param pVO
	 * @param estado
	 * @param msg
	 * @throws Exception
	 */
	public int updateHiloPerPrg(PeriodoPrgfechasVO pVO, int estadoActual,
			int estadoNuevo, String msg) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		int numReg = -99;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.updateHiloPerPrg"));

			st.setString(posicion++, msg);
			st.setInt(posicion++, estadoNuevo);
			// where
			st.setInt(posicion++, pVO.getPrfvigencia());
			st.setInt(posicion++, estadoActual);
			st.setString(posicion++, "-999");
			st.setInt(posicion++, ParamsVO.HILO_TIPO_PER_PROG_FECHAS);
			st.setInt(posicion++, pVO.getPrfcodinst());
			numReg = st.executeUpdate();

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return numReg;
	}

	/**
	 * Consulta si ya existe un registro en la tabla DATOS_HILOS
	 * 
	 * @param pVO
	 * @param estadoNuevo
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public int isValidarHiloPerPrg(PeriodoPrgfechasVO pVO, int estadoNuevo,
			String msg) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		int numReg = 0 - 99;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.isValidarHiloPerPrg"));

			// where
			st.setInt(posicion++, pVO.getPrfvigencia());
			st.setString(posicion++, "-999");
			st.setInt(posicion++, ParamsVO.HILO_TIPO_PER_PROG_FECHAS);
			st.setInt(posicion++, pVO.getPrfcodinst());
			rs = st.executeQuery();
			if (rs.next()) {
				numReg = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {

				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return numReg;
	}

	/**
	 * Actualiza el estado del hilo en la tabla DATOS_HILOS
	 * 
	 * @param pVO
	 * @param estado
	 * @param msg
	 * @throws Exception
	 */
	public void deleteHiloPerPrg(PeriodoPrgfechasVO pVO) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("hiloPerProgFecha.deleteHiloPerPrg"));

			// where
			st.setInt(posicion++, pVO.getPrfvigencia());
			st.setInt(posicion++, ParamsVO.ESTADO_REPORTE_EJE);
			st.setString(posicion++, "-999");
			st.setInt(posicion++, ParamsVO.HILO_TIPO_PER_PROG_FECHAS);
			st.setInt(posicion++, pVO.getPrfcodinst());
			st.executeUpdate();

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	/**
	 * @return
	 */
	public Map getMailParams() throws Exception {
		// System.out.println("getMailParams");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		Map params = new HashMap();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("enviarCorreo.params"));
			pst.setInt(posicion++, ParamsVO.CORREO_SEC);
			r = pst.executeQuery();
			while (r.next()) {
				int i = 1;
				params.put(r.getString(i++), r.getString(i++));
			}
			// System.out.println("params " + params.size() );
		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return params;
	}

	/**
	 * Consulta los mensajes que estan en estado en cola o que han sido
	 * actualizados y estan dentro de la fecha rango
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getListaMensaje() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("enviarCorreo.listaMensajes"));
			st.setInt(posicion++, MensajesVO.EMAIL_EN_COLAR);
			st.setInt(posicion++, MensajesVO.EMAIL_MODIFICADO);
			st.setInt(posicion++, MensajesVO.EMAIL_ENVIANDO);
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;

				// SELECT , FROM MENSAJE
				MensajesVO mVO = new MensajesVO();
				mVO.setMsjcodigo(rs.getInt(posicion++)); // MSJCODIGO,
				mVO.setMsjasunto(rs.getString(posicion++)); // MSJASUNTO,
				mVO.setMsjfecha(rs.getString(posicion++)); // MSJFECHA,

				mVO.setMsjfechaini(rs.getString(posicion++));// MSJFECHAINI,
				mVO.setMsjfechafin(rs.getString(posicion++)); // MSJFECHAFIN,
				mVO.setMsjcontenido(rs.getString(posicion++)); // MSJCONTENIDO,

				mVO.setMsjenviadopor(rs.getInt(posicion++)); // MSJENVIADOPOR,
				mVO.setMsjenviadoaperfil(rs.getString(posicion++)); // MSJENVIADOAPERFIL,
				mVO.setMsjenviadoalocal(rs.getString(posicion++)); // MSJENVIADOALOCAL,

				mVO.setMsjenviadoacoleg(rs.getString(posicion++)); // MSJENVIADOACOLEG,
				mVO.setMsjenviadoasede(rs.getString(posicion++)); // MSJENVIADOASEDE,
				mVO.setMsjenviadoajorn(rs.getString(posicion++)); // MSJENVIADOAJORN

				mVO.setMsjusuario(rs.getString(posicion++)); // MSJUSUARIO
				mVO.setMsjestado(rs.getInt(posicion++)); // MSJESTADO
				list.add(mVO);
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
	 * Obtiene el email de las personal a las cuales esta dirigido el mensaje
	 * 
	 * @param codPefiles
	 * @param codLocals
	 * @param codInsts
	 * @param codSedes
	 * @param codJords
	 * @return
	 * @throws Exception
	 */
	public List getListaPersona(String codPefiles, String codLocals,
			String codInsts, String codSedes, String codJords) throws Exception {
		// System.out.println("getListaPersona");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;

		codPefiles = (codPefiles != null && codPefiles.trim().length() > 0) ? codPefiles
				.substring(1, codPefiles.length() - 1) : "-99";
		codLocals = (codLocals != null && codLocals.trim().length() > 0) ? codLocals
				.substring(1, codLocals.length() - 1) : "-99";
		codInsts = (codInsts != null && codInsts.trim().length() > 0) ? codInsts
				.substring(1, codInsts.length() - 1) : "-99";
		codSedes = (codSedes != null && codSedes.trim().length() > 0) ? "'"
				+ codSedes.substring(1, codSedes.length() - 1) + "'" : "-99";
		codJords = (codJords != null && codJords.trim().length() > 0) ? codJords
				.substring(1, codJords.length() - 1) : "-99";
		/*
		 * System.out.println("codPefiles " + codPefiles);
		 * System.out.println("codLocals " + codLocals);
		 * System.out.println("codInsts " + codInsts);
		 * System.out.println("codSedes " + codSedes);
		 * System.out.println("codJords " + codJords);
		 */
		String sql = " SELECT DISTINCT PEREMAIL" + " FROM USUARIO, "
				+ " PERSONAL   " + " WHERE (INSTR('"
				+ codPefiles
				+ "',USUPERFCODIGO ) > 0)"
				+ " AND  USUCODJERAR IN  "
				+ "      (  SELECT DISTINCT G_JERCODIGO"
				+ "         FROM G_JERARQUIA    "
				+ "         WHERE (-99 in ("
				+ codLocals
				+ ") or  G_JERLOCAL IN ("
				+ codLocals
				+ "))"
				+ "         AND   (-99 in ("
				+ codInsts
				+ ") or  G_JERINST in  ("
				+ codInsts
				+ ") )"
				+ "         AND   ('-99' in ("
				+ codSedes
				+ ") or  G_JERINST||'|'||G_JERSEDE in  ("
				+ codSedes
				+ "))    "
				+ "         AND   (-99 in ("
				+ codJords
				+ ") or  G_JERJORN in ("
				+ codJords
				+ "))   "
				+ "      ) "
				+ " and PERNUMDOCUM||'' = USUPERNUMDOCUM||''"
				+ " AND PEREMAIL IS NOT NULL " + " ";

		try {
			cn = cursor.getConnection();
			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				PersonalVO personaVO = new PersonalVO();
				personaVO.setPeremail(rs.getString(posicion++));
				list.add(personaVO);
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
	public String getNombreInst(int codInst) throws Exception {
		// System.out.println("getNombreInst");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getNombreInst"));
			st.setInt(posicion++, codInst);
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				return rs.getString(posicion++);
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
		return null;
	}

	/**
	 * Actualiza el estado del hilo en la tabla DATOS_HILOS
	 * 
	 * @param pVO
	 * @param estado
	 * @param msg
	 * @throws Exception
	 */
	public void updateEstadoMsj(MensajesVO msjVo, int estado) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("enviarCorreo..updateEstadoMsj"));
			posicion = 1;
			st.setInt(posicion++, estado);
			// where
			st.setInt(posicion++, msjVo.getMsjcodigo());
			st.executeUpdate();

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

	}

	/**
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	public String getHorasTipo(int tipo) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("enviarCorreo.getHorasTipo"));
			posicion = 1;
			st.setInt(posicion++, tipo);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			// sqle.printStackTrace();
			logger.severe(" getListaPredProgFechasCierre " + sqle);
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return null;
	}
	
	public boolean isExistePonderacionPrgPeriodo(PonderacionPorPeriodoVO pVO) throws Exception {
		
		int posicion = 0;
		ResultSet rs = null;
		Connection cn = null;
		InstParVO instParVO = null;
		PreparedStatement st = null;
		
		List list = new ArrayList();
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ponderacionporperiodo.consultarxparametrosbasicos"));
			posicion = 1;

			st.setLong(posicion++, pVO.getPrfcodinst());
			st.setLong(posicion++, pVO.getPrfvigencia());

			rs = st.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
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
			} catch (InternalErrorException inte) {}
		}
		
		return false;
		
	}


	public PonderacionPorPeriodoVO consultarPonderacionPrgPeriodo(PonderacionPorPeriodoVO pVO) throws Exception {
		
		int posicion = 0;
		ResultSet rs = null;
		Connection cn = null;
		InstParVO instParVO = null;
		PreparedStatement st = null;
		
		List list = new ArrayList();
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("ponderacionporperiodo.consultarxparametrosbasicos"));
			posicion = 1;
	
			st.setLong(posicion++, pVO.getPrfcodinst());
	        st.setLong(posicion++, pVO.getPrfvigencia());
	        
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				pVO.setPrfcodinst(rs.getInt(posicion++));
				pVO.setPrfvigencia(rs.getInt(posicion++));
				pVO.setPrfperiodo1(rs.getDouble(posicion++));
				pVO.setPrfperiodo2(rs.getDouble(posicion++));
				pVO.setPrfperiodo3(rs.getDouble(posicion++));
				pVO.setPrfperiodo4(rs.getDouble(posicion++));
				pVO.setPrfperiodo5(rs.getDouble(posicion++));
				pVO.setPrfperiodo6(rs.getDouble(posicion++));
				pVO.setPrfTipoEvaluacion(rs.getInt(posicion++));
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
			} catch (InternalErrorException inte) {}
		}
		
		return pVO;
		
	}


	public PonderacionPorPeriodoVO  guardarPonderacionPrgPeriodo(PonderacionPorPeriodoVO pVO) throws Exception{    
	    Connection cn = null;
		PreparedStatement st = null;
		int posicion = 0;
		String sql = "";
		try {
			cn = cursor.getConnection();
			sql += " " + rb.getString("ponderacionporperiodo.guardar");
			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setLong(posicion++,  pVO.getPrfcodinst());
			st.setLong(posicion++, pVO.getPrfvigencia());
			st.setDouble(posicion++,  pVO.getPrfperiodo1());
			st.setDouble(posicion++,  pVO.getPrfperiodo2());
			st.setDouble(posicion++,  pVO.getPrfperiodo3());
			st.setDouble(posicion++,  pVO.getPrfperiodo4());
			st.setDouble(posicion++,  pVO.getPrfperiodo5());
			st.setDouble(posicion++,  pVO.getPrfperiodo6());
			
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	return pVO;
}

	public PonderacionPorPeriodoVO actualizarPonderacionPrgPeriodo(PonderacionPorPeriodoVO pVO) throws Exception {
	
		String sql = "";
		int posicion = 0;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			sql += " " + rb.getString("ponderacionporperiodo.actualizar");
			
			st = cn.prepareStatement(sql);
			posicion = 1;
			st.setLong(posicion++,  pVO.getPrfcodinst());
			st.setLong(posicion++, pVO.getPrfvigencia());
			st.setDouble(posicion++,  pVO.getPrfperiodo1());
			st.setDouble(posicion++,  pVO.getPrfperiodo2());
			st.setDouble(posicion++,  pVO.getPrfperiodo3());
			st.setDouble(posicion++,  pVO.getPrfperiodo4());
			st.setDouble(posicion++,  pVO.getPrfperiodo5());
			st.setDouble(posicion++,  pVO.getPrfperiodo6());
			//where
			st.setLong(posicion++,  pVO.getPrfcodinst());
			st.setLong(posicion++, pVO.getPrfvigencia());
			
			st.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {}
		}
		
		return pVO;
		
	}
	

	public int obtenerNumerodePeriodo(int vigencia,int cod_inst)throws Exception {
		
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = null;
		List list = new ArrayList();
		int posicion = 0;
		int periodo=0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("periodo.numeroperiodosregistrados"));
			posicion = 1;
	
			st.setLong(posicion++, vigencia);
			st.setLong(posicion++, cod_inst);
			
			rs = st.executeQuery();
			if (rs.next()) {
				 periodo=rs.getInt(1);
				
			}
			rs.close();
			st.close();
			//
	
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
		
		return periodo;
		
	}
	

	public InstParVO getInstdeVigenciaMayor(long institucion) throws Exception {
	
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InstParVO instParVO = new InstParVO();
		int posicion = 0;
		
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("vigencia.mayororganizada"));
			posicion = 1;
			
			st.setLong(posicion++, institucion);
			rs = st.executeQuery();
			
			
			instParVO.setInsparcodinst(institucion);
			
			while (rs.next()) {
				posicion = 1;
				instParVO.setInsparnumper(rs.getLong(posicion++));
				instParVO.setInspartipper(rs.getLong(posicion++));
				instParVO.setInsparnomperdef(rs.getString(posicion++));
				instParVO.setInsparniveval(rs.getLong(posicion++));
				instParVO.setInsparnivevalAntes(instParVO.getInsparniveval());
				instParVO.setInsparsubtitbol(rs.getString(posicion++));
				instParVO.setPorcentajeperdida(rs.getDouble(posicion++));
				instParVO.setInsparevaldescriptores(rs.getString(posicion++));
				instParVO.setInsparvigencia(rs.getLong(posicion++));
				break;
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
		
		return instParVO;
		
	}
	
 
	/**
	 * Se envia el codigo de institucion y vigencia para consultar el tipo de evaluacion
	 * @param l
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public String getConsultarTipoEvaluacion(long l, long m) throws Exception {

		int posicion = 0;
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		InstParVO instParVO = new InstParVO();
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("obtenertipoEvaluacion"));
			posicion = 1;
	
			st.setLong(posicion++, l);
			st.setLong(posicion++, m);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
			    return rs.getString(posicion++);
				
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
			} catch (InternalErrorException inte) {}
			
		}
	
		return null;
	
	}
	
	
	// Guarda los datos en la tabla PONDERACION_INST_PERIODO dependiendo de lo
	// seleccionado en la seccion: Periodo, del Nivel de Evaluación 	
	public InstNivelEvaluacionVO guardarPonderacionPeriodos(InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		int posicion;
		ResultSet rs = null;
		Connection cn = null;
		String strConsulta = "";
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			// Se elimina la información que ya está registrada
			strConsulta = "DELETE FROM PONDERACION_INST_PERIODO WHERE COD_INST = ? AND VIGENCIA = ? ";
			st = cn.prepareStatement(strConsulta);
			posicion = 1;

			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			
			st.executeUpdate();
			
			

			// Se inserta la nueva información
			strConsulta = "INSERT INTO PONDERACION_INST_PERIODO (COD_INST, VIGENCIA, ID_TIPO_EVALUACION) VALUES (?, ?, ?) ";
			st = cn.prepareStatement(strConsulta);
			posicion = 1;

			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivmodoeval());
			
			st.executeUpdate();
			
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
			
		} catch (Exception sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return instNivelEvaluacionVO;
		
	}
	
	
	// Elimina los datos en la tabla PONDERACION_INST_PERIODO
	public InstNivelEvaluacionVO eliminarPonderacionPeriodos(InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		int posicion;
		Connection cn = null;
		String strConsulta = "";
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			// Se elimina la información que ya está registrada
			strConsulta = "DELETE FROM PONDERACION_INST_PERIODO WHERE COD_INST = ? AND VIGENCIA = ? ";
			st = cn.prepareStatement(strConsulta);
			posicion = 1;

			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			
			st.executeUpdate();
			
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
			
		} catch (Exception sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return instNivelEvaluacionVO;
		
	}
	
	
	// Guarda los datos en la tabla TIPO_EVALUACION_INST_ASIG dependiendo de lo
	// seleccionado en la seccion: Asignatura, del Nivel de Evaluación 	
	public InstNivelEvaluacionVO guardarPonderacionAsignatura(InstNivelEvaluacionVO instNivelEvaluacionVO, TipoEvaluacionInstAsigVO tipoEvaluacionInstAsigVO) throws Exception {

		int posicion;
		ResultSet rs = null;
		Connection cn = null;
		String strConsulta = "";
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			// Se elimina la información que ya está registrada
			strConsulta = "DELETE FROM TIPO_EVALUACION_INST_ASIG WHERE INSNIVCODINST = ? AND INSNIVVIGENCIA = ? ";
			st = cn.prepareStatement(strConsulta);
			posicion = 1;
	
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			
			st.executeUpdate();
			
			
			
			// Selecciono las jornadas del Establecimiento Educativo
			strConsulta = "SELECT DISTINCT G_JERJORN FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=6 AND G_JERINST=? ORDER BY G_JERJORN";
			st = cn.prepareStatement(strConsulta);
			posicion = 1;

			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				
				strConsulta = "INSERT INTO TIPO_EVALUACION_INST_ASIG (INSNIVCODINST, INSNIVVIGENCIA, INSNIVCODJORN, INSNIVTIPOEVAL) VALUES (?, ?, ?, ?) ";
				
				st = cn.prepareStatement(strConsulta);
				posicion = 1;

				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
				st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
				st.setString(posicion++, rs.getString("G_JERJORN"));
				st.setInt(posicion++, tipoEvaluacionInstAsigVO.getTipEvalTipoEval());
				
				st.executeUpdate();
				
			}
				
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
			
		} catch (Exception sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return instNivelEvaluacionVO;
		
	}
	
	
	// Guarda los datos en la tabla TIPO_EVALUACION_INST_ASIG dependiendo de lo
	// seleccionado en la seccion: Asignatura, del Nivel de Evaluación 	
	public InstNivelEvaluacionVO eliminarPonderacionAsignatura(InstNivelEvaluacionVO instNivelEvaluacionVO) throws Exception {

		int posicion;
		Connection cn = null;
		String strConsulta = "";
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			// Se elimina la información que ya está registrada
			strConsulta = "DELETE FROM TIPO_EVALUACION_INST_ASIG WHERE INSNIVCODINST = ? AND INSNIVVIGENCIA = ? ";
			st = cn.prepareStatement(strConsulta);
			posicion = 1;
	
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivcodinst());
			st.setLong(posicion++, instNivelEvaluacionVO.getInsnivvigencia());
			
			st.executeUpdate();
				
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
			
		} catch (Exception sqle) {
			
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return instNivelEvaluacionVO;
		
	}
	
	
//	public TipoEvaluacionInstAsigVO obtenerTipoEvaluacionInstAsig(TipoEvaluacionInstAsigVO tipoEvaluacionInstAsigVO) throws Exception {
//		
//		int posicion;
//		ResultSet rs = null;
//		Connection cn = null;
//		String strConsulta = "";
//		PreparedStatement st = null;
//		
//		try {
//			
//			cn = cursor.getConnection();
//
//			strConsulta = "SELECT INSNIVCODJORN, INSNIVTIPOEVAL FROM TIPO_EVALUACION_INST_ASIG WHERE INSNIVVIGENCIA=? AND INSNIVCODINST=?";
//			
//			st = cn.prepareStatement(strConsulta);
//			posicion = 1;
//
//			st.setLong(posicion++, tipoEvaluacionInstAsigVO.getTipEvalVigencia());
//			st.setLong(posicion++, tipoEvaluacionInstAsigVO.getTipEvalCodInst());
//
//			rs = st.executeQuery();
//			
//			if (rs.next()) {
//				
//				posicion = 1;
//				tipoEvaluacionInstAsigVO.setTipEvalCodJorn(rs.getInt(posicion++));
//				tipoEvaluacionInstAsigVO.setTipEvalTipoEval(rs.getInt(posicion++));
//			}
//			
//		} catch (SQLException sqle) {
//			sqle.printStackTrace();
//			throw new Exception("Error de datos: " + sqle.getMessage());
//		} catch (Exception sqle) {
//			sqle.printStackTrace();
//			throw new Exception("Error interno: " + sqle.getMessage());
//		} finally {
//			try {
//				OperacionesGenerales.closeResultSet(rs);
//				OperacionesGenerales.closeStatement(st);
//				OperacionesGenerales.closeConnection(cn);
//			} catch (InternalErrorException inte) {
//			}
//		}
//		
//		return tipoEvaluacionInstAsigVO;
//		
//	}
	
	
	public TipoEvaluacionInstAsigVO buscarAsignaturaNivelEval(TipoEvaluacionInstAsigVO tipoEvaluacionInstAsigVO) throws Exception {
		
		int posicion;
		ResultSet rs = null;
		Connection cn = null;
		String strConsulta = "";
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			
			strConsulta = "SELECT DISTINCT INSNIVTIPOEVAL FROM TIPO_EVALUACION_INST_ASIG WHERE INSNIVVIGENCIA=? AND INSNIVCODINST=?";
			
			st = cn.prepareStatement(strConsulta);
			
			posicion = 1;
			st.setLong(posicion++, tipoEvaluacionInstAsigVO.getTipEvalVigencia());
			st.setLong(posicion++, tipoEvaluacionInstAsigVO.getTipEvalCodInst());
			
			rs = st.executeQuery();

			if (rs.next()) {
				tipoEvaluacionInstAsigVO.setTipEvalTipoEval(rs.getInt(1));
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
		
		return tipoEvaluacionInstAsigVO;
		
	}
		
}
