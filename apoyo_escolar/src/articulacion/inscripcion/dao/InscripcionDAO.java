package articulacion.inscripcion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import articulacion.inscripcion.vo.*;
import articulacion.repHorariosArt.vo.ItemVO;

public class InscripcionDAO extends Dao {
	public InscripcionDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.inscripcion.bundle.sentencias");
	}

	private long[][] getInscripcion(String[] a) {
		long[][] b = null;
		String val[] = null;
		if (a != null) {
			b = new long[a.length][2];
			for (int i = 0; i < a.length; i++) {
				val = a[i].split(":");
				b[i][0] = Long.parseLong(val[0]);
				b[i][1] = Long.parseLong(val[1]);
			}
		} else {
			b = new long[0][2];
		}
		return b;
	}

	public boolean insertar(FiltroInscripcionVO filtro, String[] inscripcion)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean retorno = false;
		String materia1 = null;
		String materia2 = null;
		int totalHoras = 0;
		long[] seleccionVO = null;
		long[] seleccionVO2 = null;
		try {
			long insc[][] = getInscripcion(inscripcion);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar solapamientos
			// for (int i = 0; i < insc.length; i++) {
			// System.out.println("insc: "+insc[i][0]+"-"+insc[i][1]);
			// }
			for (int i = 0; i < insc.length - 1; i++) {
				seleccionVO = insc[i];
				totalHoras = getTotalHoras(cn, seleccionVO);
				for (int j = i + 1; j < insc.length; j++) {
					seleccionVO2 = insc[j];
					pst = cn.prepareStatement(rb
							.getString("validarSolapamiento"));
					posicion = 1;
					pst.setLong(posicion++, seleccionVO[1]);
					pst.setLong(posicion++, seleccionVO[0]);
					pst.setLong(posicion++, seleccionVO2[1]);
					pst.setLong(posicion++, seleccionVO2[0]);
					pst.executeUpdate();
					rs = pst.executeQuery();
					if (rs.next()) {
						if (rs.getInt(1) < totalHoras) {
							materia1 = getNombreMateria(cn, seleccionVO[0]);
							materia2 = getNombreMateria(cn, seleccionVO2[0]);
							setMensaje("La asignatura '"
									+ materia1
									+ "' no se puede ingresar porque se solapa con '"
									+ materia2 + "'");
							return false;
						}
					}
					rs.close();
					pst.close();
				}
			}
			// validar solapamientos2
			for (int i = 0; i < insc.length; i++) {
				seleccionVO = insc[i];
				totalHoras = getTotalHoras(cn, seleccionVO);
				pst = cn.prepareStatement(rb.getString("validarSolapamiento2"));
				posicion = 1;
				pst.setLong(posicion++, seleccionVO[1]);
				pst.setLong(posicion++, seleccionVO[0]);
				pst.setLong(posicion++, filtro.getFilEstudiante());
				pst.setLong(posicion++, filtro.getFilVigencia());
				pst.setLong(posicion++, filtro.getFilPeriodo());
				pst.setLong(posicion++, seleccionVO[1]);
				pst.setLong(posicion++, seleccionVO[0]);
				pst.executeUpdate();
				rs = pst.executeQuery();
				if (rs.next()) {
					if (rs.getInt(1) < totalHoras) {
						materia1 = getNombreMateria(cn, seleccionVO[0]);
						setMensaje("La asignatura '"
								+ materia1
								+ "' no se puede ingresar porque se solapa con materias que ya estan inscritas");
						return false;
					}
				}
				rs.close();
				pst.close();
			}
			// traerme lo que estn inscrito que NO esta en lo que el usuario
			// pidio, para validar si se puede borrar
			if (insc != null && insc.length > 0) {
				String sql = rb.getString("getInscripcionBorrable.1");
				for (int i = 0; i < insc.length; i++) {
					sql += "(?,?),";
				}
				sql = sql.substring(0, sql.length() - 1);
				sql += " " + rb.getString("getInscripcionBorrable.2");
				// System.out.println(sql);
				pst = cn.prepareStatement(sql);
				posicion = 1;
				pst.setLong(posicion++, filtro.getFilEstudiante());
				pst.setInt(posicion++, filtro.getFilVigencia());
				pst.setInt(posicion++, filtro.getFilPeriodo());
				pst.setLong(posicion++, filtro.getFilInstitucion());
				pst.setInt(posicion++, filtro.getFilVigencia());
				pst.setInt(posicion++, filtro.getFilPeriodo());
				pst.setLong(posicion++, filtro.getFilSemestre());
				pst.setInt(posicion++, filtro.getFilComponente());
				pst.setLong(posicion++, filtro.getFilInstitucion());
				pst.setLong(posicion++, filtro.getFilSede());
				pst.setLong(posicion++, filtro.getFilJornada());
				pst.setInt(posicion++, filtro.getFilVigencia());
				pst.setInt(posicion++, filtro.getFilPeriodo());
				pst.setLong(posicion++, filtro.getFilSemestre());
				pst.setInt(posicion++, filtro.getFilComponente());
				for (int i = 0; i < insc.length; i++) {
					seleccionVO = insc[i];
					pst.setLong(posicion++, seleccionVO[1]);
					pst.setLong(posicion++, seleccionVO[0]);
				}
				rs = pst.executeQuery();
				while (rs.next()) {
					// son los registros que toca mirar si tienen informacion
					if (!validarEvaluacion(cn, rs.getLong(1), rs.getLong(2),
							filtro.getFilEstudiante())) {
						materia1 = getNombreMateria(cn, rs.getLong(2));
						setMensaje("La asignatura '"
								+ materia1
								+ "' no se puede cambiar de grupo porque tiene información asociada");
						return false;
					}
				}
				rs.close();
				pst.close();
			}
			// buscar lo que tiene inscrito para restaurar los cupos
			long grupo = 0;
			long asig = 0;
			int cupoG = 0;
			pst = cn.prepareStatement(rb.getString("getInscripcion"));
			posicion = 1;
			pst.setLong(posicion++, filtro.getFilEstudiante());
			pst.setLong(posicion++, filtro.getFilVigencia());
			pst.setLong(posicion++, filtro.getFilPeriodo());
			pst.setLong(posicion++, filtro.getFilSemestre());
			pst.setLong(posicion++, filtro.getFilComponente());
			pst.setLong(posicion++, filtro.getFilInstitucion());
			pst.setInt(posicion++, filtro.getFilSede());
			pst.setInt(posicion++, filtro.getFilJornada());
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				grupo = rs.getLong(posicion++);
				asig = rs.getLong(posicion++);
				cupoG = rs.getInt(posicion++);
				if (cupoG > 0) {
					restaurarCupoGeneral(cn, grupo, asig);
				} else {
					if (filtro.getFilNivelado() == 1) {// restar a nivelado
						restaurarCupoNivelado(cn, grupo, asig);
					} else {// restar a no nivelado
						restaurarCupoNoNivelado(cn, grupo, asig);
					}
				}
			}
			rs.close();
			pst.close();
			//
			// semestre
			pst = cn.prepareStatement(rb.getString("eliminacion"));
			posicion = 1;
			pst.setLong(posicion++, filtro.getFilEstudiante());
			pst.setInt(posicion++, filtro.getFilVigencia());
			pst.setInt(posicion++, filtro.getFilPeriodo());
			pst.setLong(posicion++, filtro.getFilInstitucion());
			pst.setInt(posicion++, filtro.getFilVigencia());
			pst.setInt(posicion++, filtro.getFilPeriodo());
			pst.setLong(posicion++, filtro.getFilSemestre());
			pst.setInt(posicion++, filtro.getFilComponente());

			pst.setLong(posicion++, filtro.getFilInstitucion());
			pst.setLong(posicion++, filtro.getFilSede());
			pst.setLong(posicion++, filtro.getFilJornada());
			pst.setInt(posicion++, filtro.getFilVigencia());
			pst.setInt(posicion++, filtro.getFilPeriodo());
			pst.setLong(posicion++, filtro.getFilSemestre());
			pst.setInt(posicion++, filtro.getFilComponente());

			pst.executeUpdate();
			pst.close();
			posicion = 1;
			// INSERTAR incluido ano y periodo
			for (int i = 0; i < insc.length; i++) {
				pst = cn.prepareStatement(rb.getString("insercion"));
				pst.clearParameters();
				posicion = 1;
				seleccionVO = insc[i];
				pst.setLong(posicion++, seleccionVO[1]);
				pst.setLong(posicion++, filtro.getFilEstudiante());
				pst.setLong(posicion++, seleccionVO[0]);
				pst.setLong(posicion++, filtro.getFilVigencia());
				pst.setLong(posicion++, filtro.getFilPeriodo());
				pst.executeUpdate();
				pst.close();
				construirCupo(cn, seleccionVO[1], seleccionVO[0],
						filtro.getFilNivelado(), filtro.getFilVigencia(),
						filtro.getFilPeriodo());
			}
			cn.commit();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	/*
	 * public boolean insertar(List lista, long est, FiltroInscripcionVO
	 * filtroInscripcion) throws Exception { Connection cn = null;
	 * PreparedStatement pst = null; ResultSet rs = null; int posicion = 1;
	 * boolean retorno = false; String materia1 = null; String materia2 = null;
	 * int totalHoras = 0; LSeleccionVO seleccionVO = null; LSeleccionVO
	 * seleccionVO2 = null; try { cleanMensaje(); long anhoVigencia =
	 * getVigenciaNumerico(); cn = cursor.getConnection();
	 * cn.setAutoCommit(false); long periodoVigencia = getPeriodo(cn); //
	 * validar solapamientos for (int i = 0; i < lista.size() - 1; i++) {
	 * seleccionVO = (LSeleccionVO) lista.get(i); totalHoras = getTotalHoras(cn,
	 * seleccionVO.getGrupo(), seleccionVO.getAsignatura()); for (int j = i + 1;
	 * j < lista.size(); j++) { seleccionVO2 = (LSeleccionVO) lista.get(j); pst
	 * = cn.prepareStatement(rb.getString("validarSolapamiento")); posicion = 1;
	 * pst.setLong(posicion++, seleccionVO.getGrupo()); pst.setLong(posicion++,
	 * seleccionVO.getAsignatura()); pst.setLong(posicion++,
	 * seleccionVO2.getGrupo()); pst.setLong(posicion++,
	 * seleccionVO2.getAsignatura()); pst.executeUpdate(); rs =
	 * pst.executeQuery(); if (rs.next()) { if (rs.getInt(1) < totalHoras) {
	 * materia1 = getNombreMateria(cn, seleccionVO.getAsignatura()); materia2 =
	 * getNombreMateria(cn, seleccionVO2.getAsignatura());
	 * setMensaje("La asignatura '" + materia1 +
	 * "' no se puede ingresar porque se solapa con '" + materia2 + "'"); return
	 * false; } } rs.close(); pst.close(); } } // validar solapamientos2 for
	 * (int i = 0; i < lista.size(); i++) { seleccionVO = (LSeleccionVO)
	 * lista.get(i); totalHoras = getTotalHoras(cn, seleccionVO.getGrupo(),
	 * seleccionVO.getAsignatura()); pst =
	 * cn.prepareStatement(rb.getString("validarSolapamiento2")); posicion = 1;
	 * pst.setLong(posicion++, seleccionVO.getGrupo()); pst.setLong(posicion++,
	 * seleccionVO.getAsignatura()); pst.setLong(posicion++, est);
	 * pst.setLong(posicion++, (anhoVigencia)); pst.setLong(posicion++,
	 * (periodoVigencia)); pst.setLong(posicion++, seleccionVO.getGrupo());
	 * pst.setLong(posicion++, seleccionVO.getAsignatura());
	 * pst.executeUpdate(); rs = pst.executeQuery(); if (rs.next()) { if
	 * (rs.getInt(1) < totalHoras) { materia1 = getNombreMateria(cn,
	 * seleccionVO.getAsignatura()); setMensaje("La asignatura '" + materia1 +
	 * "' no se puede ingresar porque se solapa con materias que ya estan inscritas"
	 * ); return false; } } rs.close(); pst.close(); } int nivelado=0; //buscar
	 * nibvelado pst = cn.prepareStatement(rb.getString("getNivelado"));
	 * posicion = 1; pst.setLong(posicion++, est); rs = pst.executeQuery(); if
	 * (rs.next()){ nivelado=rs.getInt(1); } rs.close(); pst.close(); //buscar
	 * lo que tiene inscrito para restaurar los cupos long grupo=0; long asig=0;
	 * int cupoG=0; pst = cn.prepareStatement(rb.getString("getInscripcion"));
	 * posicion = 1; pst.setLong(posicion++, est); pst.setLong(posicion++,
	 * (anhoVigencia)); pst.setLong(posicion++, (periodoVigencia));
	 * pst.setLong(posicion++, filtroInscripcion.getFilSemestre());
	 * pst.setLong(posicion++, filtroInscripcion.getFilComponente());
	 * rs=pst.executeQuery(); while(rs.next()){ posicion=1;
	 * grupo=rs.getLong(posicion++); asig=rs.getLong(posicion++);
	 * cupoG=rs.getInt(posicion++); if(cupoG>0){
	 * restaurarCupoGeneral(cn,grupo,asig); }else{ if(nivelado==1){//restar a
	 * nivelado restaurarCupoNivelado(cn,grupo,asig); }else{//restar a no
	 * nivelado restaurarCupoNoNivelado(cn,grupo,asig); } } }
	 * PERIODO,componente, semestre pst =
	 * cn.prepareStatement(rb.getString("eliminacion")); posicion = 1;
	 * pst.setLong(posicion++, est); pst.setLong(posicion++, (anhoVigencia));
	 * pst.setLong(posicion++, (periodoVigencia)); pst.setLong(posicion++,
	 * (filtroInscripcion.getFilInstitucion())); pst.setLong(posicion++,
	 * (anhoVigencia)); pst.setLong(posicion++, (periodoVigencia));
	 * pst.setLong(posicion++, (filtroInscripcion.getFilSemestre()));
	 * pst.setLong(posicion++, (filtroInscripcion.getFilComponente()));
	 * pst.executeUpdate(); pst.close(); posicion = 1; // INSERTAR incluido ano
	 * y periodo for (int i = 0; i < lista.size(); i++) { pst =
	 * cn.prepareStatement(rb.getString("insercion")); pst.clearParameters();
	 * posicion = 1; seleccionVO = (LSeleccionVO) lista.get(i);
	 * pst.setLong(posicion++, seleccionVO.getGrupo()); pst.setLong(posicion++,
	 * seleccionVO.getEstudiante()); pst.setLong(posicion++,
	 * seleccionVO.getAsignatura()); pst.setLong(posicion++, anhoVigencia);
	 * pst.setLong(posicion++, periodoVigencia); pst.executeUpdate();
	 * pst.close();
	 * construirCupo(cn,seleccionVO.getGrupo(),seleccionVO.getAsignatura
	 * (),nivelado,anhoVigencia,periodoVigencia); } cn.commit(); retorno = true;
	 * e.printStackTrace(); throw new Exception(e.getMessage()); } catch
	 * e.printStackTrace(); throw new Exception(e.getMessage()); } catch
	 * throw new Exception(e.getMessage()); } finally { try {
	 * OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(pst);
	 * OperacionesGenerales.closeConnection(cn); } catch (InternalErrorException
	 * inte) { } } return retorno; }
	 */
	private int getTotalHoras(Connection cn, long[] seleccion) throws Exception {
		int total = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			// calcular los parametros de horario
			ps = cn.prepareStatement(rb.getString("getTotalHoras"));
			posicion = 1;
			ps.setLong(posicion++, seleccion[1]);
			ps.setLong(posicion++, seleccion[0]);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
		return total;
	}

	private String getNombreMateria(Connection cn, long asignatura)
			throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			// calcular los parametros de horario
			ps = cn.prepareStatement(rb.getString("getNombreMateria"));
			posicion = 1;
			ps.setLong(posicion++, asignatura);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
		return null;
	}
	
	public List getListaespecialidades(String ARTESTNUMDOC) {
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = null;
		try {
			lista = new ArrayList();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEspecialidad"));
			ps.setString(1, ARTESTNUMDOC);
			rs = ps.executeQuery();
			while (rs.next()) {
				ItemVO item = new ItemVO();
				item.setCodigo(rs.getInt(1));
				item.setNombre(rs.getString(2));
				lista.add(item);
			}
			rs.close();
			ps.close();
			
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
			} catch (InternalErrorException e) {
			}
		}
		return lista;
	}
	
	/**
	 * Trae las especialidades dictadas por una institución
	 * @param ARTESTNUMDOC
	 * @return
	 */
	public List getListaAsignaturasFromEspecialidad(String idInstitucion, String idEspecialidad) {
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = null;
		try {
			lista = new ArrayList();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getAsignaturaEsp"));
			ps.setString(1, idInstitucion);
			ps.setString(2, idEspecialidad);
			rs = ps.executeQuery();
			while (rs.next()) {
				ItemVO item = new ItemVO();
				item.setCodigo(rs.getInt(1));
				item.setNombre(rs.getString(2));
				lista.add(item);
			}
			rs.close();
			ps.close();
			
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
			} catch (InternalErrorException e) {
			}
		}
		return lista;
	}
	
	
	/**
	 * Trae las especialidades dictadas por una institución
	 * @param ARTESTNUMDOC
	 * @return
	 */
	public List getListaespecialidadesInst(String idInstitucion) {
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = null;
		try {
			lista = new ArrayList();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEspecialidadInst"));
			ps.setString(1, idInstitucion);
			rs = ps.executeQuery();
			while (rs.next()) {
				ItemVO item = new ItemVO();
				item.setCodigo(rs.getInt(1));
				item.setNombre(rs.getString(2));
				lista.add(item);
			}
			rs.close();
			ps.close();
			
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
			} catch (InternalErrorException e) {
			}
		}
		return lista;
	}

	public long[] getListaespecialidades(FiltroInscripcionVO filtro) {

		int i = 0;
		int posicion = 1;
		long t[] = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long periodo = 0;
		try {

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getListaespecialidades"
					+ filtro.getFilComponente()));
			posicion = 1;
			periodo = getPeriodo(cn);
			ps.setLong(posicion++, filtro.getFilInstitucion());
			ps.setInt(posicion++, filtro.getFilSede());
			ps.setInt(posicion++, filtro.getFilJornada());
			ps.setLong(posicion++, getVigenciaNumerico());
			ps.setLong(posicion++, periodo);
			if (filtro.getFilComponente() == 2) {
				ps.setLong(posicion++, filtro.getFilEspecialidad());
			}
			ps.setLong(posicion++, filtro.getFilSemestre());
			rs = ps.executeQuery();
			List longs = new ArrayList();
			while (rs.next()) {
				posicion = 1;
				longs.add(new Long(rs.getLong(posicion++)));
			}
			if (!longs.isEmpty()) {
				t = new long[longs.size()];
				Iterator iterator = longs.iterator();
				while (iterator.hasNext())
					t[i++] = ((Long) iterator.next()).longValue();

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error Posible problema: " + sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return t;
	}

	public List getAllListaAsignatura(FiltroInscripcionVO filtro) {
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int i = 0;
		int posicion = 1;
		LAsignaturaVO lAsignaturaVO;
		List listaPlantilla = null;
		try {
			listaPlantilla = new ArrayList();
			cn = cursor.getConnection();
			int periodo = (int) getPeriodo(cn);
			filtro.setFilPeriodo(periodo);
			filtro.setFilVigencia((int) getVigenciaNumerico());
			// calcular si esta nivelado o no
			ps = cn.prepareStatement(rb.getString("getNivelado"));
			posicion = 1;
			ps.setLong(posicion++, filtro.getFilEstudiante());
			rs = ps.executeQuery();
			if (rs.next()) {
				filtro.setFilNivelado(rs.getInt(1));
			}
			rs.close();
			ps.close();
			// calcular los parametros de horario
			ps = cn.prepareStatement(rb.getString("getparamsHorario"));
			posicion = 1;
			ps.setLong(posicion++, filtro.getFilInstitucion());
			ps.setInt(posicion++, filtro.getFilSede());
			ps.setInt(posicion++, filtro.getFilJornada());
			ps.setLong(posicion++, filtro.getFilVigencia());
			ps.setLong(posicion++, filtro.getFilPeriodo());
			ps.setInt(posicion++, filtro.getFilComponente());
			rs = ps.executeQuery();
			if (rs.next()) {
				posicion = 1;
				filtro.setFilHini(rs.getInt(posicion++));
				filtro.setFilMini(rs.getInt(posicion++));
				filtro.setFilDuracion(rs.getInt(posicion++));
				filtro.setFilClasesXbloque(rs.getInt(posicion++));
				filtro.setFilBloques(rs.getInt(posicion++));
			}
			rs.close();
			ps.close();
			// System.out.println("calcula inscripcion: "+filtro.getFilComponente()+"//"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+getVigenciaNumerico()+"//"+periodo);
			if (filtro.getFilComponente() != 0) {
				ps = cn.prepareStatement(rb.getString("getListaAsignatura"
						+ filtro.getFilComponente()));
				posicion = 1;
				ps.setLong(posicion++, filtro.getFilInstitucion());
				ps.setInt(posicion++, filtro.getFilSede());
				ps.setInt(posicion++, filtro.getFilJornada());
				ps.setLong(posicion++, getVigenciaNumerico());
				ps.setLong(posicion++, periodo);
				ps.setInt(posicion++, filtro.getFilComponente());
				if (filtro.getFilComponente() == 2)
					ps.setLong(posicion++, filtro.getFilEspecialidad());
				ps.setLong(posicion++, filtro.getFilSemestre());
				rs = ps.executeQuery();
				List listaDias = null;
				while (rs.next()) {
					i = 1;
					lAsignaturaVO = new LAsignaturaVO();
					lAsignaturaVO.setCodigo(rs.getLong(i++));
					lAsignaturaVO.setCodigoGrupo(rs.getLong(i++));
					// System.out.println("Encuentra asignatura: "+lAsignaturaVO.getCodigo()+"//"+lAsignaturaVO.getCodigoGrupo());
					lAsignaturaVO.setIdentificacion(rs.getString(i++));
					lAsignaturaVO.setNombre(rs.getString(i++));
					lAsignaturaVO.setGrupo(rs.getString(i++));
					lAsignaturaVO.setDocente(rs.getString(i++));
					lAsignaturaVO.setCreditos(rs.getString(i++));
					lAsignaturaVO.setCupoGeneral(rs.getInt(i++));
					lAsignaturaVO.setCupoNivelado(rs.getInt(i++));
					lAsignaturaVO.setCupoNoNivelado(rs.getInt(i++));
					lAsignaturaVO.setDescripcion(rs.getString(i++));
					getChecked(cn, lAsignaturaVO, filtro);
					listaDias = getDias(cn, lAsignaturaVO, filtro);
					lAsignaturaVO.setListaDia(listaDias);
					listaPlantilla.add(lAsignaturaVO);
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
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException e) {
			}
		}
		return listaPlantilla;
	}

	private List getDias(Connection cn, LAsignaturaVO as,
			FiltroInscripcionVO filtro) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = new ArrayList();
		int posicion = 1;
		LDiaVO diaVO;
		int hini = filtro.getFilHini();
		int mini = filtro.getFilMini();
		int duracion = filtro.getFilDuracion();
		try {
			ps = cn.prepareStatement(rb.getString("getDiasHorario"));
			posicion = 1;
			ps.setLong(posicion++, as.getCodigoGrupo());
			ps.setLong(posicion++, as.getCodigo());
			ps.setLong(posicion++, filtro.getFilInstitucion());
			ps.setInt(posicion++, filtro.getFilSede());

			ps.setLong(posicion++, as.getCodigoGrupo());
			ps.setLong(posicion++, as.getCodigo());
			ps.setLong(posicion++, filtro.getFilInstitucion());
			ps.setInt(posicion++, filtro.getFilSede());
			rs = ps.executeQuery();
			while (rs.next()) {
				posicion = 1;
				diaVO = new LDiaVO();
				diaVO.setDia(rs.getString(posicion++));
				diaVO.setHora(rs.getInt(posicion++));
				diaVO.setEspacioFisico(rs.getString(posicion++));
				diaVO.setHoraIni(getTiempo(diaVO.getHora() - 1, hini, mini,
						duracion));
				diaVO.setHoraFin(getTiempo(diaVO.getHora(), hini, mini,
						duracion));
				lista.add(diaVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
		return lista;
	}

	private void getChecked(Connection cn, LAsignaturaVO asignaturaVO,
			FiltroInscripcionVO filtro) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		int posicion = 1;
		int inscritoGeneral = 0;
		int inscritoNivelado = 0;
		int inscritoNoNivelado = 0;
		try {
			long asig = asignaturaVO.getCodigo();
			long grupo = asignaturaVO.getCodigoGrupo();
			ps = cn.prepareStatement(rb.getString("getChecked"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, filtro.getFilEstudiante());
			ps.setLong(posicion++, asig);
			ps.setLong(posicion++, getVigenciaNumerico());
			ps.setLong(posicion++, filtro.getFilPeriodo());
			rs = ps.executeQuery();
			if (rs.next()) {
				asignaturaVO.setChecked("checked");
				asignaturaVO.setCupoLibre(true);
			} else {
				asignaturaVO.setChecked("");
			}
			rs.close();
			ps.close();
			// traer los cupos inscritos
			ps = cn.prepareStatement(rb.getString("getCupoInscrito"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, asig);
			rs = ps.executeQuery();
			if (rs.next()) {
				inscritoGeneral = rs.getInt(1);
				inscritoNivelado = rs.getInt(2);
				inscritoNoNivelado = rs.getInt(3);
			}
			rs.close();
			ps.close();

			// calcular el total de cupos
			if (asignaturaVO.getCupoGeneral() > 0) {
				asignaturaVO.setCupo(asignaturaVO.getCupoGeneral()
						- inscritoGeneral);
				// System.out.println("Asi quedo cupo General:  "+asignaturaVO.getCupo());
			} else {
				if (filtro.getFilNivelado() == 1) {
					asignaturaVO.setCupo(asignaturaVO.getCupoNivelado()
							- inscritoNivelado);
					// System.out.println("Asi quedo cupo Nivelado:  "+asignaturaVO.getCupo());
				} else {
					asignaturaVO.setCupo(asignaturaVO.getCupoNoNivelado()
							- inscritoNoNivelado);
					// System.out.println("Asi quedo cupo No Nivelado:  "+asignaturaVO.getCupo());
				}
			}
			asignaturaVO.setNivelado(filtro.getFilNivelado());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
	}

	private String getTiempo(int clase, int hini, int mini, int duracion) {
		// System.out.println(clase+"valor original: "+String.valueOf(hini)+" :
		// "+String.valueOf(mini)+"|"+duracion);
		if (clase == 0) {
			return String.valueOf(hini) + " : " + String.valueOf(mini);
		}
		int m = 0;
		int h = 0;
		int hfin = 0;
		int mfin = 0;
		m = mini;
		m += duracion * clase;
		h = Math.round(m / 60);
		m = Math.round(m % 60);
		hfin = hini + h;
		mfin = m;
		// System.out.println("valor devuelto: "+String.valueOf(hfin)+" :
		// "+String.valueOf(mfin));
		return String.valueOf(hfin) + " : " + String.valueOf(mfin);
	}

	private long getPeriodo(Connection cn) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = cn.prepareStatement(rb.getString("getPeriodo"));
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}

		return 0;
	}

	private void restaurarCupoGeneral(Connection cn, long grupo, long asig)
			throws Exception {
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			ps = cn.prepareStatement(rb.getString("restaurarCupoGeneral"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, asig);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} catch (Exception sqle) {
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
	}

	private void restaurarCupoNivelado(Connection cn, long grupo, long asig)
			throws Exception {
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			ps = cn.prepareStatement(rb.getString("restaurarCupoNivelado"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, asig);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} catch (Exception sqle) {
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
	}

	private void restaurarCupoNoNivelado(Connection cn, long grupo, long asig)
			throws Exception {
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			ps = cn.prepareStatement(rb.getString("restaurarCupoNoNivelado"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, asig);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} catch (Exception sqle) {
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
	}

	private void construirCupo(Connection cn, long grupo, long asig,
			int nivelado, long anho, long per) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1;
		int cupoG = 0;
		try {
			ps = cn.prepareStatement(rb.getString("getCupoGeneral"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			rs = ps.executeQuery();
			if (rs.next()) {
				cupoG = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if (cupoG > 0) {//
				ps = cn.prepareStatement(rb.getString("construirCupoGeneral"));
				posicion = 1;
				ps.setLong(posicion++, grupo);
				ps.setLong(posicion++, asig);
				ps.executeUpdate();
				ps.close();
			} else {
				if (nivelado == 1) {
					ps = cn.prepareStatement(rb
							.getString("construirCupoNivelado"));
					posicion = 1;
					ps.setLong(posicion++, grupo);
					ps.setLong(posicion++, asig);
					ps.executeUpdate();
					ps.close();
				} else {
					ps = cn.prepareStatement(rb
							.getString("construirCupoNoNivelado"));
					posicion = 1;
					ps.setLong(posicion++, grupo);
					ps.setLong(posicion++, asig);
					ps.executeUpdate();
					ps.close();
				}
			}
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} catch (Exception sqle) {
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
	}

	public List getListaJornada(long inst, long sede) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JornadaVO a = null;
		List la = new ArrayList();
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getJornada"));
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);
			posicion = 1;
			rs = ps.executeQuery();
			while (rs.next()) {
				a = new JornadaVO();
				posicion = 1;
				a.setCodigo(rs.getInt(posicion++));
				a.setNombre(rs.getString(posicion++));
				la.add(a);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error Posible problema: " + sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return la;
	}

	private boolean validarEvaluacion(Connection cn, long grupo,
			long asignatura, long estudiante) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			ps = cn.prepareStatement(rb.getString("validarEvaluacion1"));
			posicion = 1;
			ps.setLong(posicion++, asignatura);
			ps.setLong(posicion++, estudiante);
			rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
			rs.close();
			ps = cn.prepareStatement(rb.getString("validarEvaluacion2"));
			posicion = 1;
			ps.setLong(posicion++, asignatura);
			ps.setLong(posicion++, estudiante);
			ps.setLong(posicion++, grupo);
			rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}
		return true;
	}

	/**
	 * Retorna el listado de alumnos inscritos por grupo
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param filtroInscripcion
	 * @return
	 */
	public Object getGrupoInscritos( Login usuVO2,
			FiltroInscripcionVO filtroInscripcion) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List la = new ArrayList();
		InscripcionVO insc = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getAlumnosGrupo"));
			ps.setString(posicion++, getCurrentYear());
			ps.setLong(posicion++, filtroInscripcion.getFilSemestre());
			ps.setLong(posicion++, filtroInscripcion.getFilAsignatura());
			rs = ps.executeQuery();
			while (rs.next()) {
				posicion = 1;
				insc = new InscripcionVO();
				insc.setNombreEstudiante(rs.getString(posicion++));
				insc.setArtinsGrupo(rs.getInt(posicion++));
				insc.setArtinsCodest(rs.getInt(posicion++));
				la.add(insc);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error Posible problema: " + sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return la;
	}
	
	/**
	 * Aprueba las solicitudes de inscripcion realizadas por los estudiantes
	 * @param filtroInscripcion
	 * @param inscripcion
	 * @param usuVO2
	 * @return
	 */
	public boolean aprobarInscripciones(FiltroInscripcionVO filtroInscripcion,
			String[] inscripcion, Login usuVO2) {
		Connection cn = null;
		PreparedStatement ps = null;
		try{
			cn = cursor.getConnection();
			for(int i = 0; i < inscripcion.length; i++){
				String[] split = inscripcion[i].split(":");
				ps = cn.prepareStatement(rb.getString("doInscripcion"));
				ps.setString(1, split[0]); //Código del estudiante
				ps.setString(2, split[1]); //Grupo
				ps.setString(3, split[2]); //Asignatura
				ps.executeUpdate();
				
				ps = cn.prepareStatement(rb.getString("deleteInscripcion"));
				ps.setString(1, getCurrentYear());
				ps.setString(2, split[2]); //Asignatura
				ps.setString(3, split[0]); //Código del estudiante
				ps.setString(4, split[1]); //Grupo
				ps.executeUpdate();
				
			}
			return true;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Retorna el año actual
	 * @return
	 */
	private String getCurrentYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return Integer.toString(year);
	}


}
