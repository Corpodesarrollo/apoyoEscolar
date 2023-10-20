/**
 * 
 */
package siges.plantilla.comportamiento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.importar.beans.EvaluacionEstudiante;
import siges.importar.beans.UrlImportar;
import siges.login.beans.Login;
import siges.plantilla.beans.FiltroBeanEvaluacion;
import siges.plantilla.beans.FiltroComportamiento;
import siges.plantilla.beans.NivelEvalVO;
import siges.plantilla.beans.ParamsVO;
import siges.plantilla.beans.TipoEvalVO;

/**
 * 1/09/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class ComportamientoDAO extends Dao {

	public ComportamientoDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.plantilla.comportamiento.bundle.comportamiento");
	}

	/**
	 * @function:
	 * @param inst
	 * @param sede
	 * @param jor
	 * @return
	 */
	public List getAllGrado(long inst, int sede, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllGrado"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));
				l.add(itemVO);
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

	/**
	 * @function:
	 * @param inst
	 * @param sede
	 * @param jor
	 * @return
	 */
	public List getAllGradoDimension(long inst, int sede, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllGradoDimension"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));
				l.add(itemVO);
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

	/**
	 * @function:
	 * @param inst
	 * @param sede
	 * @param jor
	 * @return
	 */
	public List getAllGrupo(long inst, int sede, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllGrupo"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jor);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));// gra
				itemVO.setPadre2(rs.getString(i++));// meto
				l.add(itemVO);
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

	/**
	 * @function:
	 * @param filtro
	 * @return
	 */
	public List getEstudianteComportamiento(FiltroComportamiento filtro) {
		List l = new ArrayList();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		EvaluacionEstudiante estudiante = null;
		int i = 0;
		try {
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilJerarquia(rs.getLong(1));
			} else {
				// System.out.println("Jerarquia no encontrada");
				throw new Exception("Jerarquia no encontrada");
			}
			rs.close();
			st.close();
			// System.out.println("1.))"+"getEstudianteComportamiento."+filtro.getFilOrden()+"."+filtro.getFilPeriodo());
			// System.out.println("2."+filtro.getFilJerarquia());
			st = cn.prepareStatement(rb
					.getString("getEstudianteComportamiento."
							+ filtro.getFilOrden() + "."
							+ filtro.getFilPeriodo()));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setLong(i++, getVigenciaInst(filtro.getFilInstitucion()));
			rs = st.executeQuery();
			int index = 1;
			while (rs.next()) {
				i = 1;
				estudiante = new EvaluacionEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				estudiante.setEvalNota(rs.getInt(i++));
				estudiante.setEvalObservacion(rs.getString(i++));
				l.add(estudiante);
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

	public List getEstudianteComportamientoPlantilla(FiltroComportamiento filtro) {
		System.out.println("getEstudianteComportamientoPlantilla");
		List l = new ArrayList();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		siges.plantilla.beans.EvaluacionEstudiante estudiante = null;
		int i = 0;
		try {
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilJerarquia(rs.getLong(1));
			} else {
				// System.out.println("Jerarquia no encontrada");
				throw new Exception("Jerarquia no encontrada");
			}
			rs.close();
			st.close();
			// System.out.println("1.))"+"getEstudianteComportamiento."+filtro.getFilOrden()+"."+filtro.getFilPeriodo());
			// System.out.println("2."+filtro.getFilJerarquia());
			st = cn.prepareStatement(rb
					.getString("getEstudianteComportamientoPlantilla."
							+ filtro.getFilOrden() + "."
							+ filtro.getFilPeriodo()));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setLong(i++, super.getVigenciaInst(filtro.getFilInstitucion()));
			rs = st.executeQuery();
			int index = 1;
			while (rs.next()) {
				i = 1;
				estudiante = new siges.plantilla.beans.EvaluacionEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				// estudiante.setEvalNotaCon(rs.getString(i++));
				estudiante.setEvalNotaConCodigo(rs.getDouble(i++));
				// System.out.println("estudiante.setEvalNombre( " +
				// estudiante.getEvalNombre());
				// System.out.println("estudiante.setEvalNotaConCodigo( " +
				// estudiante.getEvalNotaConCodigo());
				estudiante.setEvalObservacion(rs.getString(i++));
				l.add(estudiante);
			}
		} catch (SQLException sqle) {
			System.out.println("comportamiento SQL " + sqle.getMessage());
			sqle.printStackTrace();

			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			System.out.println("comportamiento " + sqle.getMessage());
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

	public List getEscalaComportamiento() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getEscalaComportamiento"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre2(rs.getString(i++));
				l.add(itemVO);
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

	//

	public boolean insertarEvalComportamiento(FiltroComportamiento filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null, st2 = null;
		ResultSet rs = null;
		String param[] = null;
		int i = 0;
		long jerarquia = 0;
		long codigo = 0;
		int nota = 0;
		String obs = null;
		String sql = "", sql2 = "";
		long valores[] = null;
		try {
			String[] eval = filtro.getEval();
			long vig = getVigenciaInst(filtro.getFilInstitucion());
			jerarquia = filtro.getFilJerarquia();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (eval != null) {
				valores = new long[eval.length];
				// System.out.println("Valor de jerarquia de grupo="+filtro.getFilJerarquia());
				st = cn.prepareStatement(rb
						.getString("ingresarEstudianteComportamiento."
								+ filtro.getFilPeriodo()));
				st2 = cn.prepareStatement(rb
						.getString("actualizarEstudianteComportamiento."
								+ filtro.getFilPeriodo()));
				st.clearBatch();
				st2.clearBatch();
				int indice = 0;
				for (int j = 0; j < eval.length; j++) {
					i = 1;
					// System.out.println("Eval="+eval[j]);
					param = eval[j].replace('|', '&').split("&");
					// System.out.println("param="+param.length);
					if (param != null && param.length > 1) {
						codigo = Long.parseLong(param[0]);
						nota = Integer.parseInt(param[1]);
						if (param.length == 3)
							obs = param[2];
						else
							obs = null;
						valores[indice++] = codigo;
						sql2 += "?,";
						if (!existeEstudianteComp(cn, jerarquia, codigo,
								filtro.getFilInstitucion())) {// ingresar
							st.setLong(i++, jerarquia);
							st.setLong(i++, codigo);
							st.setLong(i++, vig);
							st.setInt(i++, nota);
							if (obs == null)
								st.setNull(i++, Types.VARCHAR);
							else
								st.setString(i++, obs);
							st.addBatch();
						} else {// actualizar
							st2.setInt(i++, nota);
							if (obs == null)
								st2.setNull(i++, Types.VARCHAR);
							else
								st2.setString(i++, obs);
							st2.setLong(i++, jerarquia);
							st2.setLong(i++, codigo);
							st2.setLong(i++, vig);
							st2.addBatch();
						}
					}
				}
				st.executeBatch();
				st2.executeBatch();
				st.close();
				st2.close();
				if (sql2.length() > 0) {
					sql2 = sql2.substring(0, sql2.length() - 1);
					sql = rb.getString("getCountEstudianteComportamiento.1."
							+ filtro.getFilPeriodo())
							+ sql2
							+ rb.getString("getCountEstudianteComportamiento.2");
				} else {
					sql = rb.getString("getCountEstudianteComportamiento.3."
							+ filtro.getFilPeriodo());
				}
				st = cn.prepareStatement(sql);
				i = 1;
				st.setLong(i++, jerarquia);
				st.setLong(i++, vig);
				for (int j = 0; j < indice; j++) {
					st.setLong(i++, valores[j]);
				}
				rs = st.executeQuery();
				while (rs.next()) {
					i = 1;
					if (rs.getInt(2) == 0) {// se puede eliminar todo
						st2 = cn.prepareStatement(rb
								.getString("eliminarEstudianteComportamiento"));
						st2.setLong(i++, jerarquia);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vig);
						st2.executeUpdate();
						st2.close();
					} else {// se actualiza a null porque hay mas de un registro
							// alli
						st2 = cn.prepareStatement(rb
								.getString("actualizarEstudianteComportamiento."
										+ filtro.getFilPeriodo()));
						st2.setNull(i++, Types.VARCHAR);
						st2.setNull(i++, Types.VARCHAR);
						st2.setLong(i++, jerarquia);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vig);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
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
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarEvalDimension(FiltroComportamiento filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null, st2 = null;
		ResultSet rs = null;
		String param[] = null;
		int i = 0;
		long jerarquia = 0;
		long codigo = 0;
		int nota = 0;
		String obs = null;
		String sql = "", sql2 = "";
		long valores[] = null;
		try {
			String[] eval = filtro.getEval();
			long vig = getVigenciaInst(filtro.getFilInstitucion());
			int dim = filtro.getFilDimension();
			jerarquia = filtro.getFilJerarquia();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (eval != null) {
				valores = new long[eval.length];
				st = cn.prepareStatement(rb
						.getString("ingresarEstudianteDimension."
								+ filtro.getFilPeriodo()));
				st2 = cn.prepareStatement(rb
						.getString("actualizarEstudianteDimension."
								+ filtro.getFilPeriodo()));
				st.clearBatch();
				st2.clearBatch();
				int indice = 0;
				for (int j = 0; j < eval.length; j++) {
					i = 1;
					// System.out.println("Eval="+eval[j]);
					param = eval[j].replace('|', ':').split(":");
					// System.out.println("Valor de log de param="+param.length);
					if (param != null && param.length > 1) {
						codigo = Long.parseLong(param[0]);
						nota = Integer.parseInt(param[1]);
						if (param.length >= 3) {
							if (param.length == 3) {
								obs = param[2];
							} else {
								obs = "";
								for (int tt = 2; tt < param.length; tt++) {
									obs += param[tt] + ":";
								}
							}
						} else {
							obs = null;
						}
						if (nota == -99 && param.length == 2) {
							continue;
						}
						valores[indice++] = codigo;
						sql2 += "?,";
						if (!existeEstudianteDim(cn, jerarquia, codigo, dim,
								filtro.getFilInstitucion())) {// ingresar
							st = cn.prepareStatement(rb
									.getString("ingresarEstudianteDimension."
											+ filtro.getFilPeriodo()));
							st.setLong(i++, jerarquia);
							st.setLong(i++, codigo);
							st.setInt(i++, dim);
							st.setLong(i++, vig);
							if (nota == -99)
								st.setNull(i++, Types.VARCHAR);
							else
								st.setInt(i++, nota);
							if (obs == null)
								st.setNull(i++, Types.VARCHAR);
							else
								st.setString(i++, obs);
							st.executeUpdate();
							st.close();
						} else {// actualizar
							st2 = cn.prepareStatement(rb
									.getString("actualizarEstudianteDimension."
											+ filtro.getFilPeriodo()));
							if (nota == -99)
								st2.setNull(i++, java.sql.Types.VARCHAR);
							else
								st2.setInt(i++, nota);
							if (obs == null)
								st2.setNull(i++, Types.VARCHAR);
							else
								st2.setString(i++, obs);
							st2.setLong(i++, jerarquia);
							st2.setLong(i++, codigo);
							st2.setInt(i++, dim);
							st2.setLong(i++, vig);
							st2.executeUpdate();
							st2.close();
						}
					}
				}
				if (sql2.length() > 0) {
					sql2 = sql2.substring(0, sql2.length() - 1);
					sql = rb.getString("getCountEstudianteDimension.1."
							+ filtro.getFilPeriodo())
							+ sql2
							+ rb.getString("getCountEstudianteDimension.2");
				} else {
					sql = rb.getString("getCountEstudianteDimension.3."
							+ filtro.getFilPeriodo());
				}
				st = cn.prepareStatement(sql);
				i = 1;
				st.setLong(i++, jerarquia);
				st.setInt(i++, dim);
				st.setLong(i++, vig);
				for (int j = 0; j < indice; j++) {
					st.setLong(i++, valores[j]);
				}
				rs = st.executeQuery();
				while (rs.next()) {
					i = 1;
					if (rs.getInt(2) == 0) {// se puede eliminar todo
						st2 = cn.prepareStatement(rb
								.getString("eliminarEstudianteDimension"));
						st2.setLong(i++, jerarquia);
						st2.setLong(i++, rs.getLong(1));
						st2.setInt(i++, dim);
						st2.setLong(i++, vig);
						st2.executeUpdate();
						st2.close();
					} else {// se actualiza a null porque hay mas de un registro
							// alli
						st2 = cn.prepareStatement(rb
								.getString("actualizarEstudianteDimension."
										+ filtro.getFilPeriodo()));
						st2.setNull(i++, Types.VARCHAR);
						st2.setNull(i++, Types.VARCHAR);
						st2.setLong(i++, jerarquia);
						st2.setLong(i++, rs.getLong(1));
						st2.setInt(i++, dim);
						st2.setLong(i++, vig);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
			actualizarCierreDimension(cn, filtro);
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
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	private void actualizarCierreDimension(Connection cn,
			FiltroComportamiento filtro) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 0;
		try {
			if (filtro.getFilCerrar() == 1) {
				int estado = -1;
				ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
						+ filtro.getFilPeriodo()));
				ps.clearParameters();
				posicion = 1;
				ps.setLong(posicion++, filtro.getFilJerarquia());
				ps.setInt(posicion++, filtro.getFilDimension());
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++,
						getVigenciaInst(filtro.getFilInstitucion()));
				rs = ps.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
				rs.close();
				ps.close();
				if (estado == -1)
					ps = cn.prepareStatement(rb.getString("InsertarCierreGrupo"
							+ filtro.getFilPeriodo()));
				if (estado != -1)
					ps = cn.prepareStatement(rb
							.getString("ActualizarCierreGrupo"
									+ filtro.getFilPeriodo()));
				if (ps != null) {
					ps.clearParameters();
					posicion = 1;
					ps.setInt(posicion++, 1);
					ps.setLong(posicion++, filtro.getFilJerarquia());
					ps.setLong(posicion++, filtro.getFilDimension());
					ps.setLong(posicion++, 1);
					ps.setLong(posicion++,
							getVigenciaInst(filtro.getFilInstitucion()));
					ps.executeUpdate();
				}
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
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
	}

	private boolean existeEstudianteComp(Connection cn, long grupo,
			long codigo, long codInst) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = cn.prepareStatement(rb.getString("existeEstudianteComp"));
			int i = 1;
			st.setLong(i++, grupo);
			st.setLong(i++, codigo);
			st.setLong(i++, getVigenciaInst(codInst));
			rs = st.executeQuery();
			if (rs.next())
				return true;
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
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	private boolean existeEstudianteDim(Connection cn, long grupo, long codigo,
			int dimension, long codInst) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = cn.prepareStatement(rb.getString("existeEstudianteDimension"));
			int i = 1;
			st.setLong(i++, grupo);
			st.setLong(i++, codigo);
			st.setInt(i++, dimension);
			st.setLong(i++, getVigenciaInst(codInst));
			rs = st.executeQuery();
			if (rs.next())
				return true;
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
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public List getAllDimension(long inst, int sede, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllDimension"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			long grado = 0;
			while (rs.next()) {
				i = 1;
				grado = rs.getInt(1);
				if (grado <= 0 || grado == 40 || grado == 41) {
					itemVO = new ItemVO();
					itemVO.setCodigo(1);
					itemVO.setNombre("Corporal");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(2);
					itemVO.setNombre("Comunicativa");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(3);
					itemVO.setNombre("Cognitiva");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(4);
					itemVO.setNombre("ntica");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(5);
					itemVO.setNombre("Estntica");
					itemVO.setPadre(grado);
					l.add(itemVO);
				}
				if (grado == 30 || grado == 31) {
					itemVO = new ItemVO();
					itemVO.setCodigo(1);
					itemVO.setNombre("Comportamiento Social");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(2);
					itemVO.setNombre("Comunicativa");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(3);
					itemVO.setNombre("Cientnfica");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(4);
					itemVO.setNombre("Convivencia");
					itemVO.setPadre(grado);
					l.add(itemVO);
					itemVO = new ItemVO();
					itemVO.setCodigo(5);
					itemVO.setNombre("Lndica");
					itemVO.setPadre(grado);
					l.add(itemVO);
				}
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

	public List getEstudianteDimension(FiltroComportamiento filtro) {
		List l = new ArrayList();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		EvaluacionEstudiante estudiante = null;
		int i = 0;
		try {
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilJerarquia(rs.getLong(1));
			}

			rs.close();
			st.close();
			// System.out.println("1.))"+"getEstudianteDimension."+filtro.getFilOrden()+"."+filtro.getFilPeriodo());
			// System.out.println("2."+filtro.getFilJerarquia());
			st = cn.prepareStatement(rb.getString("getEstudianteDimension."
					+ filtro.getFilOrden() + "." + filtro.getFilPeriodo()));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setInt(i++, filtro.getFilDimension());
			st.setLong(i++, getVigenciaInst(filtro.getFilInstitucion()));
			rs = st.executeQuery();
			int index = 1;
			while (rs.next()) {
				i = 1;
				estudiante = new EvaluacionEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				estudiante.setEvalNota(rs.getInt(i++));
				estudiante.setEvalObservacion(rs.getString(i++));
				l.add(estudiante);
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

	public int getEstadoCierreDim(FiltroComportamiento f) throws Exception {
		int tipo = 1;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ f.getFilPeriodo()));
			posicion = 1;
			ps.setLong(posicion++, f.getFilJerarquia());
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, getVigenciaInst(f.getFilInstitucion()));
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public int getEstadoHorarioDim(FiltroComportamiento f, Login l)
			throws Exception {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			// validacion de seguridad de horarios habilitada
			long nn;
			ps = cn.prepareStatement(rb.getString("EstadoLecturaSistema"));
			rs = ps.executeQuery();
			if (rs.next()) {
				return 0;
			}
			rs.close();
			ps.close();
			// validacion contra Institucion
			ps = cn.prepareStatement(rb.getString("EstadoLecturaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(l.getInstId()));
			rs = ps.executeQuery();
			if (rs.next()) {
				return 0;
			}
			rs.close();
			ps.close();
			// validacion contra horarios
			long user = Long.parseLong(l.getUsuarioId());
			ps = cn.prepareStatement(rb.getString("isDocenteHorarioDim"));
			posicion = 1;
			ps.setLong(posicion++, f.getFilJerarquia());
			ps.setLong(posicion++, user);
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, user);
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, user);
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, user);
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, user);
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, user);
			ps.setLong(posicion++, f.getFilDimension());
			ps.setLong(posicion++, user);
			ps.setLong(posicion++, f.getFilDimension());
			rs = ps.executeQuery();
			if (rs.next()) {
				return 0;
			} else {
				return 1;
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			setMensaje("Error intentando obtener estado horario area. Posible problema: ");
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @function:
	 * @param filtro
	 * @param listaPeriodos
	 */
	public void getParametrosComportamiento(FiltroComportamiento filtro,
			List listaPeriodos) {
		System.out.println("getParametrosComportamiento ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();

			System.out.println("filtro.getFilPeriodo() "
					+ filtro.getFilPeriodo());
			if (listaPeriodos != null
					&& (listaPeriodos.size() + 1) > filtro.getFilPeriodo()) {
				int indice = filtro.getFilPeriodo() - 1;
				ItemVO item = (ItemVO) listaPeriodos.get(indice);
				filtro.setFilNombrePeriodo(item.getNombre());
			} else {
				filtro.setFilNombrePeriodo(" ");
			}

			st = cn.prepareStatement(rb.getString("getNombreGrado"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilNombreGrado(rs.getString(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("getNombreGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilNombreGrupo(rs.getString(1));
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			System.out.println("PARAM SQL" + sqle.getMessage());
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			System.out.println("PARAM " + sqle.getMessage());
			sqle.printStackTrace();
			System.out.println(sqle.getMessage());

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
	}

	/**
	 * @function:
	 * @param filtro
	 * @param estudiantes
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public UrlImportar insertarEvalComportamiento(FiltroComportamiento filtro,
			List estudiantes, UrlImportar url) throws Exception {
		Connection cn = null;
		PreparedStatement st = null, st2 = null;
		ResultSet rs = null;
		List resultado = new ArrayList();
		int totEst = 0, totIngr = 0, totAct = 0, totElim = 0;
		int i = 0;
		long jerarquia = 0;
		long codigo = 0;
		double nota = 0;
		String obs = null;
		String sql = "", sql2 = "";
		long valores[] = null;
		siges.plantilla.beans.EvaluacionEstudiante estudiante = null;
		try {
			// String [] eval=filtro.getEval();
			long vig = getVigenciaInst(filtro.getFilInstitucion());
			jerarquia = filtro.getFilJerarquia();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (estudiantes != null) {
				totEst = estudiantes.size();
				valores = new long[estudiantes.size()];

				st = cn.prepareStatement(rb
						.getString("ingresarEstudianteComportamiento."
								+ filtro.getFilPeriodo()));
				st2 = cn.prepareStatement(rb
						.getString("actualizarEstudianteComportamiento."
								+ filtro.getFilPeriodo()));

				st.clearBatch();
				st2.clearBatch();

				int indice = 0;
				for (int j = 0; j < estudiantes.size(); j++) {
					i = 1;
					estudiante = (siges.plantilla.beans.EvaluacionEstudiante) estudiantes
							.get(j);
					// System.out.println("Eval="+eval[j]);
					// param=eval[j].replace('|',':').split(":");
					if (estudiante != null
							&& estudiante.getEvalNotaConCodigo() > -1) {
						codigo = estudiante.getEvalCodigo();
						nota = estudiante.getEvalNotaConCodigo();
						obs = estudiante.getEvalObservacion();
						valores[indice++] = codigo;
						sql2 += "?,";
						if (!existeEstudianteComp(cn, jerarquia, codigo,
								filtro.getFilInstitucion())) {// ingresar
							st.setLong(i++, jerarquia);
							st.setLong(i++, codigo);
							st.setLong(i++, vig);
							st.setDouble(i++, nota);
							if (obs == null)
								st.setNull(i++, Types.VARCHAR);
							else
								st.setString(i++, obs);
							st.addBatch();
							totIngr++;
						} else {// actualizar
							st2.setDouble(i++, nota);
							if (obs == null)
								st2.setNull(i++, Types.VARCHAR);
							else
								st2.setString(i++, obs);
							st2.setLong(i++, jerarquia);
							st2.setLong(i++, codigo);
							st2.setLong(i++, vig);
							st2.addBatch();
							totAct++;
						}
					}
				}
				st.executeBatch();
				st2.executeBatch();
				st.close();
				st2.close();
				// System.out.println("cantidad de indice="+indice);
				if (sql2.length() > 0) {
					sql2 = sql2.substring(0, sql2.length() - 1);
					sql = rb.getString("getCountEstudianteComportamiento.1."
							+ filtro.getFilPeriodo())
							+ sql2
							+ rb.getString("getCountEstudianteComportamiento.2");
				} else {
					sql = rb.getString("getCountEstudianteComportamiento.3."
							+ filtro.getFilPeriodo());
				}
				st = cn.prepareStatement(sql);
				i = 1;
				st.setLong(i++, jerarquia);
				st.setLong(i++, vig);
				for (int j = 0; j < indice; j++) {
					st.setLong(i++, valores[j]);
				}
				rs = st.executeQuery();
				while (rs.next()) {
					i = 1;
					if (rs.getInt(2) == 0) {// se puede eliminar todo
						st2 = cn.prepareStatement(rb
								.getString("eliminarEstudianteComportamiento"));
						st2.setLong(i++, jerarquia);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vig);
						st2.executeUpdate();
						st2.close();
						totElim++;
					} else {// se actualiza a null porque hay mas de un registro
							// alli
						st2 = cn.prepareStatement(rb
								.getString("actualizarEstudianteComportamiento."
										+ filtro.getFilPeriodo()));
						st2.setNull(i++, Types.VARCHAR);
						st2.setNull(i++, Types.VARCHAR);
						st2.setLong(i++, jerarquia);
						st2.setLong(i++, rs.getLong(1));
						st2.setLong(i++, vig);
						st2.executeUpdate();
						st2.close();
					}
				}
				rs.close();
				st.close();
			}
			ItemVO itemVO = new ItemVO();
			// total estdiantes
			itemVO.setNombre("Total estudiantes:");
			itemVO.setCodigo(totEst);
			resultado.add(itemVO);
			// total ingresados
			itemVO = new ItemVO();
			itemVO.setNombre("Total ingresados:");
			itemVO.setCodigo(totIngr);
			resultado.add(itemVO);
			// total actualizados
			itemVO = new ItemVO();
			itemVO.setNombre("Total actualizados:");
			itemVO.setCodigo(totAct);
			resultado.add(itemVO);
			// total eliminados
			itemVO = new ItemVO();
			itemVO.setNombre("Total eliminados:");
			itemVO.setCodigo(totElim);
			resultado.add(itemVO);
			url.setResultado(resultado);
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
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return url;
	}

	public List getAllMetodologia(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllMetodologia"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				l.add(itemVO);
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

	// CAMBIOS EVLUACION PARAMETRSO INSTITICION

	public TipoEvalVO getTipoEval(FiltroBeanEvaluacion filtroEvaluacion,
			Login login) throws Exception {
		System.out.println("getTipoEval ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		TipoEvalVO tipoEvalVO = new TipoEvalVO();
		try {
			cn = cursor.getConnection();
			filtroEvaluacion.setInstitucion(login.getInstId().trim());
			vigencia = getVigenciaInst(Long.parseLong(filtroEvaluacion
					.getInstitucion()));
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, Long.parseLong(login.getInstId()));
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);

			}

			System.out.println("codigoNivelEval " + codigoNivelEval);
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
				sql = sql + " " + rb.getString("getTipoEval.jorn");
			if (nivelEval.getEval_metod() == 1)
				sql = sql + " " + rb.getString("getTipoEval.metod");
			if (nivelEval.getEval_nivel() == 1)
				sql = sql + rb.getString("getTipoEval.nivel");
			if (nivelEval.getEval_grado() == 1)
				sql = sql + " " + rb.getString("getTipoEval.grado");

			System.out.println("CONSULTA getTipoEval" + sql);
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, getVigenciaInst(Long.parseLong(filtroEvaluacion
					.getInstitucion())));
			st.setLong(i++, Long.parseLong(login.getInstId()));

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, Long.parseLong(login.getSedeId()));
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, Long.parseLong(login.getJornadaId()));
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++,
						Long.parseLong(filtroEvaluacion.getMetodologia()));
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(Integer
						.parseInt(filtroEvaluacion.getGrado()));
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, Long.parseLong(filtroEvaluacion.getGrado()));

			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				tipoEvalVO.setCod_tipo_eval(rs.getLong(i++));
				tipoEvalVO.setEval_min(rs.getDouble(i++));
				tipoEvalVO.setEval_max(rs.getDouble(i++));
				tipoEvalVO.setEval_rangos_completos(rs.getInt(i++));
			}
//			System.out.println("tipoEvalVO.setCod_tipo_eval( " + tipoEvalVO.getCod_tipo_eval());
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
		return tipoEvalVO;
	}

	public List getEscalaConceptual(FiltroBeanEvaluacion filtroEvaluacion,
			Login login) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		int tipoEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		List l = new ArrayList();
		ItemVO itemVO = null;
		List list = new ArrayList();
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaInst(Long.parseLong(filtroEvaluacion
					.getInstitucion()));
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, Long.parseLong(login.getInstId()));
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getLong(1);
			}
			System.out.println("codigoNivelEval " + codigoNivelEval);
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

			System.out.println("sql<<<<<<<<<<<<<<  " + sql);
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, getVigenciaInst(Long.parseLong(login.getInstId())));
			st.setLong(i++, Long.parseLong(login.getInstId()));
			st.setLong(i++, codigoNivelEval);

			if (nivelEval.getEval_sede() == 1)
				st.setLong(i++, Long.parseLong(login.getSedeId()));
			if (nivelEval.getEval_jornada() == 1)
				st.setLong(i++, Long.parseLong(login.getJornadaId()));
			if (nivelEval.getEval_metod() == 1)
				st.setLong(i++,
						Long.parseLong(filtroEvaluacion.getMetodologia()));
			if (nivelEval.getEval_nivel() == 1) {
				int nivelGrado = getNivelGrado(Integer
						.parseInt(filtroEvaluacion.getGrado()));
				st.setInt(i++, nivelGrado);
			}
			if (nivelEval.getEval_grado() == 1)
				st.setLong(i++, Long.parseLong(filtroEvaluacion.getGrado()));

			rs = st.executeQuery();
			// list = getCollection(rs);
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre2(rs.getString(i++));
				list.add(itemVO);
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
	 * @function: Retorna la escala segun el tipo de evaluacion de institucion
	 * @param n
	 * @param login
	 * @return
	 */
	public List getEscalaNivelEval(FiltroComportamiento filtroPlantilla)
			throws Exception {
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		List list = null;
		try {

			/*
			 * System.out.println("filtroPlantilla inst " +
			 * filtroPlantilla.getFilInstitucion() );
			 * System.out.println("filtroPlantilla sede " +
			 * filtroPlantilla.getFilSede() );
			 * System.out.println("filtroPlantilla jornada " +
			 * filtroPlantilla.getFilJornada() );
			 * System.out.println("filtroPlantilla.getMetodologia() " +
			 * filtroPlantilla.getFilMetodologia());
			 * System.out.println("filtroPlantilla.getGrado() " +
			 * filtroPlantilla.getFilGrado());
			 */
			Login login = new Login();
			login.setInstId(filtroPlantilla.getFilInstitucion() + "");
			login.setSedeId(filtroPlantilla.getFilSede() + "");
			login.setJornadaId(filtroPlantilla.getFilJornada() + "");

			FiltroBeanEvaluacion filtroEvaluacion = new FiltroBeanEvaluacion();
			filtroEvaluacion.setMetodologia(filtroPlantilla.getFilMetodologia()
					+ "");
			filtroEvaluacion.setGrado(filtroPlantilla.getFilGrado() + "");

			// 1. Obtener el nivel eval y el tipo eval (1.NUMERICO,
			// 2.CONCEPTUAL, 3.PORCENTUAL)

			TipoEvalVO tipoEvalVO = getTipoEval(filtroEvaluacion, login);
			filtroPlantilla.setFilCodTipoEval(tipoEvalVO.getCod_tipo_eval());
			filtroPlantilla.setRangosCompletos(tipoEvalVO
					.getEval_rangos_completos());

			// 2. Si es conceptual obtener escalas, sino retorna el max y min

			if (tipoEvalVO.getCod_tipo_eval() == ParamsVO.ESCALA_CONCEPTUAL) {
				// System.out.println("TIPO DE EVALUACION CONCEPTUAL " +
				// getEscalaConceptual( filtroEvaluacion, login).size());
				return getEscalaConceptual(filtroEvaluacion, login);
			} else {
				// System.out.println("TIPO DE EVALUACION NUM O PORCENTUAL");
				list = new ArrayList();

				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(0);
				itemVO.setNombre("" + tipoEvalVO.getEval_min());
				itemVO.setPadre2("" + tipoEvalVO.getEval_max());
				list.add(itemVO);
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
		return list;
	}

}
