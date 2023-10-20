package articulacion.retiroEstudiantes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.retiroEstudiantes.vo.JornadaVO;
import articulacion.retiroEstudiantes.vo.MotivoRetiroVO;
import articulacion.retiroEstudiantes.vo.PopUpMotivoVO;
import articulacion.retiroEstudiantes.vo.SedeVO;
import articulacion.retiroEstudiantes.vo.AsignaturaVO;
import articulacion.retiroEstudiantes.vo.DatosPlanEvalVO;
import articulacion.retiroEstudiantes.vo.EspecialidadVO;
import articulacion.retiroEstudiantes.vo.EstEvaluacionVO;
import articulacion.retiroEstudiantes.vo.EstudianteVO;
import articulacion.retiroEstudiantes.vo.GrupoVO;
import articulacion.retiroEstudiantes.vo.RangosVO;
import articulacion.apcierArtic.vo.DatosVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.inscripcion.vo.FiltroInscripcionVO;
import articulacion.inscripcion.vo.LAsignaturaVO;
import articulacion.inscripcion.vo.LDiaVO;
import articulacion.inscripcion.vo.LSeleccionVO;

public class EvaluacionDAO extends Dao {

	private ResourceBundle rb;

	public EvaluacionDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.retiroEstudiantes.bundle.sentencias");
	}

	public boolean insertar(List lista, DatosPlanEvalVO datosVO)
			throws Exception {
		Connection conect = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean retorno = false;
		PopUpMotivoVO motivoVO = null;
		try {
			conect = cursor.getConnection();
			conect.setAutoCommit(false);
			int nivelado = 0;
			// buscar nibvelado
			pst = conect.prepareStatement(rb.getString("getNivelado"));
			posicion = 1;
			pst.setLong(posicion++, datosVO.getEstudiante());
			rs = pst.executeQuery();
			if (rs.next()) {
				nivelado = rs.getInt(1);
			}
			rs.close();
			pst.close();
			// System.out.println("LO QUE VALE EL ESTUDIANTE AL GUARDAR:"+datosVO.getEstudiante()+"nivelado?"+nivelado+"//"+datosVO.getAnVigencia()+"//"+datosVO.getPerVigencia()+"//"+datosVO.getSemestre()+"//"+datosVO.getComponente());
			// buscar lo que tiene inscrito para restaurar los cupos
			for (int i = 0; i < lista.size(); i++) {
				motivoVO = (PopUpMotivoVO) lista.get(i);
				posicion = 1;
				restaurarCupos(conect, nivelado, datosVO, motivoVO);
				pst = conect.prepareStatement(rb.getString("eliminacion"));
				pst.setLong(posicion++, motivoVO.getCodigoGrupo());
				pst.setLong(posicion++, motivoVO.getCodigoEstudiante());
				pst.setLong(posicion++, motivoVO.getCodigoAsignatura());
				pst.setLong(posicion++, datosVO.getAnVigencia());
				pst.setLong(posicion++, datosVO.getPerVigencia());
				pst.executeUpdate();
				pst.close();
				// voy aca no me restaura la cuestion
			}
			for (int i = 0; i < lista.size(); i++) {
				pst = conect.prepareStatement(rb.getString("insercion"));
				pst.clearParameters();
				posicion = 1;
				motivoVO = (PopUpMotivoVO) lista.get(i);
				pst.setLong(posicion++, motivoVO.getCodigoAsignatura());
				pst.setLong(posicion++, motivoVO.getCodigoEstudiante());
				pst.setLong(posicion++, motivoVO.getCodigoMotivo());
				pst.setLong(posicion++, motivoVO.getCodigoGrupo());
				pst.setString(posicion++, motivoVO.getDescripcion());

				pst.executeUpdate();
				pst.close();
				// System.out.println("Inserto correctamente la inscripcion");
			}
			conect.commit();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	private void restaurarCupos(Connection cn, int nivelado,
			DatosPlanEvalVO datosVO, PopUpMotivoVO motivo) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		long grupo = 0;
		long asig = 0;
		int cupoG = 0;
		int posicion = 1;
		try {
			// System.out.println("valores: " + datosVO.getEstudiante() + "//"
			// + datosVO.getAnVigencia() + "//" + datosVO.getPerVigencia()
			// + "//" + datosVO.getSemestre() + "//"
			// + datosVO.getComponente() + "//"
			// + motivo.getCodigoAsignatura());
			pst = cn.prepareStatement(rb.getString("getInscripcion"));
			posicion = 1;
			pst.setLong(posicion++, datosVO.getEstudiante());
			pst.setLong(posicion++, datosVO.getAnVigencia());
			pst.setLong(posicion++, datosVO.getPerVigencia());
			pst.setLong(posicion++, motivo.getCodigoAsignatura());
			pst.setLong(posicion++, datosVO.getComponente());
			rs = pst.executeQuery();
			while (rs.next()) {
				// System.out.println("Encuentra asignacion");
				posicion = 1;
				grupo = rs.getLong(posicion++);
				asig = rs.getLong(posicion++);
				cupoG = rs.getInt(posicion++);
				if (cupoG > 0) {
					// System.out.println("reataura General");
					restaurarCupoGeneral(cn, grupo, asig);
				} else {
					if (nivelado == 1) {// restar a nivelado
						// System.out.println("reataura nivelado");
						restaurarCupoNivelado(cn, grupo, asig);
					} else {// restar a no nivelado
						// System.out.println("reataura no nivelado");
						restaurarCupoNoNivelado(cn, grupo, asig);
					}
				}
			}
			rs.close();
			pst.close();
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
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getAllMotivo() {
		// System.out.println("Aca entra para averiguarme los mnotivos");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		MotivoRetiroVO lp = null;
		int i = 0;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getMotivo"));
			i = 1;
			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("Encontro registros de motivo");
				i = 1;
				lp = new MotivoRetiroVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setMotivo(rs.getString(i++));
				lpA.add(lp);
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
		return lpA;
	}

	public EspecialidadVO[] getListaEspecialidad(String insti) {
		EspecialidadVO[] especialidad = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List la = new ArrayList();
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEspecialidad"));
			ps.setString(posicion++, insti);
			posicion = 1;
			rs = ps.executeQuery();
			EspecialidadVO a = null;
			while (rs.next()) {
				a = new EspecialidadVO();
				posicion = 1;
				a.setCodigo(rs.getInt(posicion++));
				a.setNombre(rs.getString(posicion++));
				la.add(a);
			}
			rs.close();
			ps.close();
			if (!la.isEmpty()) {
				int i = 0;
				especialidad = new EspecialidadVO[la.size()];
				Iterator iterator = la.iterator();
				while (iterator.hasNext())
					especialidad[i++] = (EspecialidadVO) (iterator.next());
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
		return especialidad;
	}

	public List getListaRangos(DatosPlanEvalVO datosVO) {

		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List l = new ArrayList();
		RangosVO rangosVO = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			if (datosVO != null) {

				ps = cn.prepareStatement(rb.getString("getRangos"));
				ps.setLong(i++, datosVO.getInstitucion());
				ps.setLong(i++, datosVO.getMetodologia());
				ps.setInt(i++, datosVO.getAnVigencia());
				ps.setInt(i++, datosVO.getPerVigencia());

				rs = ps.executeQuery();
				while (rs.next()) {
					i = 1;
					rangosVO = new RangosVO();
					rangosVO.setConceptual(rs.getString(i++));
					rangosVO.setInicio(rs.getFloat(i++));
					rangosVO.setFin(rs.getFloat(i++));
					l.add(rangosVO);
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
			} catch (InternalErrorException inte) {
			}
		}
		return l;

	}

	// aca van las cuestiones
	public List getAjaxGrupo(long inst, int sede, int jornada, int metod,
			int grado) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		GrupoVO lp = null;
		int i = 1;
		try {
			if (inst == 0 || sede == 0 || jornada == 0 || grado == 0
					|| metod == 0) {
				return null;
			}
			cn = cursor.getConnection();

			if (grado < 3)
				grado = 10;
			else
				grado = 11;

			st = cn.prepareStatement(rb.getString("getGrupo"));

			st.setLong(i++, inst);
			st.setLong(i++, sede);
			st.setLong(i++, jornada);
			st.setLong(i++, metod);
			st.setLong(i++, grado);
			rs = st.executeQuery();

			while (rs.next()) {
				i = 1;
				// System.out.println("Encuentra datos en grupo");
				lp = new GrupoVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setConsecutivo(rs.getString(i++));
				lpA.add(lp);
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
		return lpA;
	}

	public List getAjaxAsignatura(long inst, int metod, int anVigencia,
			int perVigencia, int componente, long especialidad) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		AsignaturaVO lp = null;
		int i = 0;
		try {
			// System.out.println("ANTES DEL IF"+componente);
			if (inst == 0 || metod == 0 || anVigencia == 0 || perVigencia == 0
					|| componente == -99) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxAsignatura"
					+ componente));
			i = 1;

			st.setLong(i++, inst);
			st.setLong(i++, metod);
			st.setLong(i++, anVigencia);
			st.setLong(i++, perVigencia);
			st.setLong(i++, componente);
			if (componente == 2)
				st.setLong(i++, especialidad);

			rs = st.executeQuery();
			// System.out.println("Metodo asignatura");
			while (rs.next()) {
				i = 1;
				// System.out.println("Encuentra datos en Asignatura");
				lp = new AsignaturaVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
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
		return lpA;
	}

	/*
	 * public List getAjaxAsignatura(long grupo){ Connection cn=null;
	 * PreparedStatement st=null; ResultSet rs=null; List lpA=new ArrayList();
	 * AsignaturaVO lp=null; int i=0; try{ //
	 * System.out.println("ANTES DEL IF "+grupo); if(grupo==0){return null;}
	 * cn=cursor.getConnection();
	 * st=cn.prepareStatement(rb.getString("getAjaxAsignatura")); i=1;
	 * st.setLong(i++,grupo); rs=st.executeQuery(); //
	 * System.out.println("Metodo asignatura"); while(rs.next()){ i=1;
	 * System.out.println("Encuentra datos en Asignatura"); lp=new
	 * AsignaturaVO(); lp.setCodigo(rs.getInt(i++));
	 * lp.setNombre(rs.getString(i++)); lpA.add(lp); } }catch(SQLException
	 * sqle){ sqle.printStackTrace();
	 * setMensaje("Error obteniendo datos. Posible problema: "
	 * +getErrorCode(sqle)); }catch(Exception sqle){ sqle.printStackTrace();
	 * setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
	 * }finally{ try{ OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeConnection(cn); }catch(InternalErrorException
	 * inte){} } return lpA; }
	 */

	public List getAjaxPrueba(long asignatura) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		AsignaturaVO lp = null;
		int i = 0;
		try {
			if (asignatura == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxPrueba"));
			i = 1;
			st.setLong(i++, asignatura);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new AsignaturaVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
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
		return lpA;
	}

	public void setReporte(String us, String rec, String tipo, String nombre,
			int modulo) {
		Connection cn = null;
		PreparedStatement ps = null;
		// System.out.println("inserto en repnrtes");
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ReporteInsertar"));
			int posicion = 1;
			ps.setString(posicion++, us);
			ps.setString(posicion++, rec);
			ps.setString(posicion++, tipo);
			ps.setString(posicion++, nombre);
			ps.setInt(posicion++, modulo);
			ps.executeUpdate();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar reporte. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return;
	}

	public List getJornada(String inst, String sede) {
		// System.out.println("Entra a la  consulta de las sedes");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		JornadaVO jornadaVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getJornada"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));

			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("Encuentra la consulta de las sedes");
				i = 1;
				jornadaVO = new JornadaVO();
				jornadaVO.setSede(rs.getInt(i++));
				jornadaVO.setCodigo(rs.getInt(i++));
				jornadaVO.setNombre(rs.getString(i++));
				l.add(jornadaVO);
			}
			/*
			 * System.out.println("Sede "+jornadaVO.getSede());
			 * System.out.println("Codigo "+jornadaVO.getCodigo());
			 * System.out.println("Nombre "+jornadaVO.getNombre());
			 */
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

	public List getSedes(String inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		SedeVO sedeVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getSede"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				sedeVO = new SedeVO();
				sedeVO.setCodigo(rs.getInt(i++));
				sedeVO.setNombre(rs.getString(i++));

				l.add(sedeVO);
			}
			/*
			 * System.out.println("Sede Codigo "+sedeVO.getCodigo());
			 * System.out.println("Sede Nombre "+sedeVO.getNombre());
			 */
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

	public List getAllListaAsignatura(DatosPlanEvalVO filtro) {
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int i = 0;
		int posicion = 1;
		LAsignaturaVO lAsignaturaVO;
		List listaPlantilla = null;
		long periodo = 0;
		// long nivelado=0;
		try {
			cn = cursor.getConnection();
			listaPlantilla = new ArrayList();
			// System.out.println("la institucion es "+filtro.getFilInstitucion());
			periodo = getPeriodo(cn);
			if (filtro.getComponente() != 0) {
				ps = cn.prepareStatement(rb.getString("getListaAsignatura"
						+ filtro.getComponente()));
				posicion = 1;
				ps.setLong(posicion++, filtro.getInstitucion());
				ps.setLong(posicion++, filtro.getSede());
				ps.setLong(posicion++, filtro.getJornada());
				ps.setLong(posicion++, filtro.getAnVigencia());
				ps.setLong(posicion++, filtro.getPerVigencia());
				ps.setLong(posicion++, filtro.getComponente());
				if (filtro.getComponente() == 2)
					ps.setLong(posicion++, filtro.getEspecialidad());
				ps.setLong(posicion++, filtro.getEstudiante());
				// UNION
				ps.setLong(posicion++, filtro.getInstitucion());
				ps.setLong(posicion++, filtro.getSede());
				ps.setLong(posicion++, filtro.getJornada());
				ps.setLong(posicion++, filtro.getAnVigencia());
				ps.setLong(posicion++, filtro.getPerVigencia());
				ps.setLong(posicion++, filtro.getComponente());
				if (filtro.getComponente() == 2)
					ps.setLong(posicion++, filtro.getEspecialidad());
				ps.setLong(posicion++, filtro.getEstudiante());
				rs = ps.executeQuery();
				List listaDias = null;
				while (rs.next()) {
					i = 1;
					lAsignaturaVO = new LAsignaturaVO();
					lAsignaturaVO.setNombreEstado(rs.getString(i++));
					lAsignaturaVO.setEstado(rs.getInt(i++));
					lAsignaturaVO.setCodigo(rs.getLong(i++));
					lAsignaturaVO.setCodigoGrupo(rs.getLong(i++));
					lAsignaturaVO.setIdentificacion(rs.getString(i++));
					lAsignaturaVO.setNombre(rs.getString(i++));
					lAsignaturaVO.setGrupo(rs.getString(i++));
					lAsignaturaVO.setDocente(rs.getString(i++));
					lAsignaturaVO.setCreditos(rs.getString(i++));
					lAsignaturaVO.setCupoGeneral(rs.getInt(i++));
					lAsignaturaVO.setCupoNivelado(rs.getInt(i++));
					lAsignaturaVO.setCupoNoNivelado(rs.getInt(i++));
					lAsignaturaVO.setDescripcion(rs.getString(i++));

					getChecked(cn, lAsignaturaVO, lAsignaturaVO.getCodigo(),
							lAsignaturaVO.getCodigoGrupo(),
							filtro.getEstudiante(), periodo);
					listaDias = getDias(cn, lAsignaturaVO.getCodigo(),
							lAsignaturaVO.getCodigoGrupo(),
							filtro.getInstitucion(), filtro.getSede(),
							filtro.getJornada(), filtro.getComponente(),
							periodo);
					lAsignaturaVO.setListaDia(listaDias);
					listaPlantilla.add(lAsignaturaVO);
				}
				// System.out
				// .println("tamano lista::::::" + listaPlantilla.size());
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

	private long getPeriodo(Connection cn) {
		long periodo = 0;

		ResultSet rs = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {

			ps = cn.prepareStatement(rb.getString("getPeriodo"));

			rs = ps.executeQuery();

			while (rs.next()) {
				posicion = 1;
				periodo = rs.getLong(posicion++);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			// throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			// throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}

		return periodo;
	}

	private int getAllNivelado(Connection cn, long codigo) {
		int periodo = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {

			ps = cn.prepareStatement(rb.getString("getNivelado"));
			ps.setLong(posicion++, codigo);
			rs = ps.executeQuery();

			while (rs.next()) {
				posicion = 1;
				periodo = rs.getInt(posicion++);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			// throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			// throw new Exception(sqle);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
			}
		}

		return periodo;
	}

	private void getChecked(Connection cn, LAsignaturaVO asignaturaVO,
			long asig, long grupo, long est, long periodo) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		int posicion = 1;
		int inscritoGeneral = 0;
		int inscritoNivelado = 0;
		int inscritoNoNivelado = 0;
		int nivelado = 0;
		try {
			ps = cn.prepareStatement(rb.getString("getChecked"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, est);
			ps.setLong(posicion++, asig);
			ps.setLong(posicion++, getVigenciaNumerico());
			ps.setLong(posicion++, periodo);
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
				// System.out.println("Asi quedo cupo General:  "
				// + asignaturaVO.getCupo() + "//"
				// + asignaturaVO.getCupoGeneral() + "//"
				// + inscritoGeneral);
			} else {
				ps = cn.prepareStatement(rb.getString("getNivelado"));
				posicion = 1;
				ps.setLong(posicion++, est);
				rs = ps.executeQuery();
				if (rs.next()) {
					nivelado = rs.getInt(1);
				}
				rs.close();
				ps.close();
				if (nivelado == 1) {
					asignaturaVO.setCupo(asignaturaVO.getCupoNivelado()
							- inscritoNivelado);
					// System.out.println("Asi quedo cupo Nivelado:  "
					// + asignaturaVO.getCupo());
				} else {
					asignaturaVO.setCupo(asignaturaVO.getCupoNoNivelado()
							- inscritoNoNivelado);
					// System.out.println("Asi quedo cupo No Nivelado:  "
					// + asignaturaVO.getCupo());
				}
			}
			asignaturaVO.setNivelado(nivelado);
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

	private List contar(Connection cn, String c, long b) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		List contret = new ArrayList();
		int posicion = 1;
		try {

			ps = cn.prepareStatement(rb.getString("conteo"));
			posicion = 1;
			if (c != "") {
				// System.out.println("estoy cansada");
			}
			ps.setString(posicion++, c);
			ps.setLong(posicion++, b);
			rs = ps.executeQuery();
			// System.out.println("lo hace jo");
			while (rs.next()) {
				posicion = 1;
				LAsignaturaVO dVO = new LAsignaturaVO();
				// dVO.setC(rs.getString(posicion++));
				contret.add(dVO);
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
		return contret;
	}

	private List getDias(Connection cn, long asig, long grupo, long inst,
			long sede, long jor, long comp, long periodo) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		List lista = new ArrayList();
		int posicion = 1;
		LDiaVO diaVO;
		int hini = 0;
		int mini = 0;
		int duracion = 0;
		int clasesXbloque = 0;
		int bloques = 0;
		try {
			// calcular los parametros de horario
			ps = cn.prepareStatement(rb.getString("getparamsHorario"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);
			ps.setLong(posicion++, jor);
			ps.setLong(posicion++, getVigenciaNumerico());
			ps.setLong(posicion++, periodo);
			ps.setLong(posicion++, comp);
			rs = ps.executeQuery();
			if (rs.next()) {
				posicion = 1;
				hini = rs.getInt(posicion++);
				mini = rs.getInt(posicion++);
				duracion = rs.getInt(posicion++);
				clasesXbloque = rs.getInt(posicion++);
				bloques = rs.getInt(posicion++);
			}
			ps = cn.prepareStatement(rb.getString("getDiasHorario"));
			posicion = 1;
			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, asig);
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);

			ps.setLong(posicion++, grupo);
			ps.setLong(posicion++, asig);
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);
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

	private String getTiempo(int clase, int hini, int mini, int duracion) {
		// System.out.println(clase+"valor original: "+String.valueOf(hini)+" : "+String.valueOf(mini)+"|"+duracion);
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
		// System.out.println("valor devuelto: "+String.valueOf(hfin)+" : "+String.valueOf(mfin));
		return String.valueOf(hfin) + " : " + String.valueOf(mfin);
	}

	public PopUpMotivoVO getMotivo(long est, long asig, long gru) {
		// System.out.println("Aca entra para traer el motivo");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PopUpMotivoVO motivoVO = null;
		int i = 0;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getMotivoVO"));
			i = 1;
			st.setLong(i++, est);
			st.setLong(i++, asig);
			st.setLong(i++, gru);
			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("Encontro registros de motivo");
				i = 1;
				motivoVO = new PopUpMotivoVO();
				motivoVO.setCodigoMotivo(rs.getInt(i++));
				motivoVO.setFecha(rs.getString(i++));
				motivoVO.setDescripcion(rs.getString(i++));
				motivoVO.setFormaEstado("1");
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
		return motivoVO;
	}

	public List getAjaxEstudiante(long inst, int sede, int jor, int grado,
			long grupo) {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List estudiante = new ArrayList();
		EstudianteVO estudianteVO = null;
		long jerarquia = 0;
		int i = 0;
		int grado1 = 0;
		try {
			if (grado < 3)
				grado1 = 10;
			else
				grado1 = 11;
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getJerarquia"));
			i = 1;
			st.setLong(i++, inst);
			st.setLong(i++, sede);
			st.setLong(i++, jor);
			st.setLong(i++, grado1);
			st.setLong(i++, grupo);
			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("Encontro registros de motivo");
				jerarquia = rs.getLong(1);
			}
			/*
			 * System.out.println("herara "+jerarquia);
			 * System.out.println("herara "+grado);
			 */
			if (jerarquia != 0) {

				st = cn.prepareStatement(rb.getString("getEstudiante"));
				i = 1;

				st.setLong(i++, jerarquia);
				st.setLong(i++, grado);
				rs = st.executeQuery();
				while (rs.next()) {
					// System.out.println("Encontro registros de estudiantes");
					estudianteVO = new EstudianteVO();
					estudianteVO.setCodigo(rs.getLong(1));
					estudianteVO.setNombre(rs.getString(2));
					estudiante.add(estudianteVO);
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

		return estudiante;
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
}
