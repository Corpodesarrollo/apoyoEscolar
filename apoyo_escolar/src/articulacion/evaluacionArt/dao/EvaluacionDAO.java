package articulacion.evaluacionArt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.evaluacionArt.vo.AsignaturaVO;
import articulacion.evaluacionArt.vo.DatosVO;
import articulacion.evaluacionArt.vo.EspecialidadVO;
import articulacion.evaluacionArt.vo.EstEvaluacionVO;
import articulacion.evaluacionArt.vo.EstudianteVO;
import articulacion.evaluacionArt.vo.EvaluacionVO;
import articulacion.evaluacionArt.vo.GrupoVO;
import articulacion.evaluacionArt.vo.LimitesVO;
import articulacion.evaluacionArt.vo.PruebaVO;
import articulacion.evaluacionArt.vo.RangosVO;
import articulacion.evaluacionArt.vo.JornadaVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class EvaluacionDAO extends Dao {

	private ResourceBundle rb;

	public EvaluacionDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.evaluacionArt.bundle.sentencias");
	}

	public List getJornada(String inst, String sede) {
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
			st.setInt(i++, Integer.parseInt(sede));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				jornadaVO = new JornadaVO();
				jornadaVO.setSede(rs.getInt(i++));
				jornadaVO.setCodigo(rs.getInt(i++));
				jornadaVO.setNombre(rs.getString(i++));
				l.add(jornadaVO);
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

	public List getEstudiantes(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		EstudianteVO estudianteVO = null;
		// EstudianteVO estudianteVO2=null;
		int i = 1;
		try {
			cn = cursor.getConnection();

			// System.out.println("inst"+datosVO.getInstitucion());
			// System.out.println("sede"+datosVO.getSede());
			// System.out.println("jornada"+datosVO.getJornada());
			// System.out.println("metod"+datosVO.getMetodologia());
			// System.out.println("metod"+datosVO.getComponente());
			// System.out.println("especialidad"+datosVO.getEspecialidad());
			// System.out.println("Grupo"+datosVO.getGrupo());
			// System.out.println("prueba"+datosVO.getPrueba());

			if (datosVO.getComponente() == 1)
				st = cn.prepareStatement(rb.getString("getEstudiantes"
						+ datosVO.getOrdenado()));
			if (datosVO.getComponente() == 2)
				st = cn.prepareStatement(rb.getString("getEstudiantes"
						+ (datosVO.getOrdenado() + 2)));
			st.setLong(i++, datosVO.getInstitucion());
			st.setLong(i++, datosVO.getSede());
			// st.setLong(i++,datosVO.getJornada());
			st.setLong(i++, datosVO.getMetodologia());
			st.setLong(i++, datosVO.getGrupo());
			st.setLong(i++, datosVO.getPrueba());
			st.setInt(i++, datosVO.getBimestre());
			if (datosVO.getComponente() == 2)
				st.setLong(i++, datosVO.getEspecialidad());

			rs = st.executeQuery();

			while (rs.next()) {
				i = 1;
				// System.out.println("encuentra estudiantes");
				estudianteVO = new EstudianteVO();
				estudianteVO.setCodigo(rs.getInt(1));
				estudianteVO.setDocumento(rs.getString(2));
				estudianteVO.setApellido(rs.getString(3));
				estudianteVO.setNombre(rs.getString(4));
				if (rs.getString(5) != null)
					estudianteVO.setNotaSubP1(rs.getFloat(5));
				else
					estudianteVO.setNotaSubP1(-1);
				if (rs.getString(6) != null)
					estudianteVO.setNotaSubP2(rs.getFloat(6));
				else
					estudianteVO.setNotaSubP2(-1);
				if (rs.getString(7) != null)
					estudianteVO.setNotaSubP3(rs.getFloat(7));
				else
					estudianteVO.setNotaSubP3(-1);
				if (rs.getString(8) != null)
					estudianteVO.setNotaSubP4(rs.getFloat(8));
				else
					estudianteVO.setNotaSubP4(-1);
				if (rs.getString(9) != null)
					estudianteVO.setNotaSubP5(rs.getFloat(9));
				else
					estudianteVO.setNotaSubP5(-1);
				if (rs.getString(10) != null && !rs.getString(10).equals(""))
					estudianteVO.setNotaNum(rs.getFloat(10));
				else
					estudianteVO.setNotaNum(-1);
				estudianteVO.setNotaCon(rs.getString(11));
				l.add(estudianteVO);
			}

			/*
			 * estudianteVO=new EstudianteVO(); estudianteVO.setCodigo(388224);
			 * estudianteVO.setApellido("salazar");
			 * estudianteVO.setNombre("nelson andres"); l.add(estudianteVO);
			 * 
			 * estudianteVO=new EstudianteVO(); estudianteVO.setCodigo(378725);
			 * estudianteVO.setApellido("perez");
			 * estudianteVO.setNombre("Linda Johanna"); l.add(estudianteVO);
			 * 
			 * estudianteVO=new EstudianteVO(); estudianteVO.setCodigo(345944);
			 * estudianteVO.setApellido("suarez");
			 * estudianteVO.setNombre("Maria del Pilar"); l.add(estudianteVO);
			 * 
			 * estudianteVO=new EstudianteVO(); estudianteVO.setCodigo(391824);
			 * estudianteVO.setApellido("bobadilla");
			 * estudianteVO.setNombre("heidy Julie"); l.add(estudianteVO);
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

	public List getListaRangos(DatosVO datosVO) {
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

	public List getAjaxGrupo(long inst, int metod, int sede, int jornada,
			int anVigencia, int perVigencia, int componente, long especialidad) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		GrupoVO lp = null;
		int i = 0;
		try {
			if (inst == 0 || sede == 0 || jornada == 0 || anVigencia == 0
					|| perVigencia == 0 || componente == -99) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxGrupo" + componente));
			i = 1;

			// System.out.println("jornada "+jornada);
			/*
			 * System.out.println("************************************");
			 * System.out.println("inst "+inst);
			 * System.out.println("sede "+sede);
			 * System.out.println("jornada "+jornada);
			 * System.out.println("anVigencia "+anVigencia);
			 * System.out.println("perVigencia "+perVigencia);
			 * System.out.println("componente "+componente);
			 * System.out.println("especialidad "+especialidad);
			 */

			st.setLong(i++, inst);
			st.setLong(i++, sede);
			st.setLong(i++, jornada);
			st.setLong(i++, anVigencia);
			st.setLong(i++, perVigencia);
			st.setLong(i++, componente);
			if (componente == 2)
				st.setLong(i++, especialidad);

			// System.out.println("Metodo grupo");
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
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

	public List getAjaxAsignatura(long grupo) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		AsignaturaVO lp = null;
		int i = 0;
		try {
			// System.out.println("ANTES DEL IF "+grupo);
			if (grupo == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i = 1;
			st.setLong(i++, grupo);
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

	public List getAjaxPrueba(long asignatura, int prueba) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		AsignaturaVO lp = null;
		int i = 1;
		try {
			if (asignatura == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxPrueba"));
			st.setLong(i++, asignatura);
			st.setInt(i++, prueba);
			rs = st.executeQuery();
			// System.out.println("antes de la consulta  ->"+asignatura);
			while (rs.next()) {
				i = 1;
				// System.out.println("encuentra pruebas");
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
	 * public boolean insertaEvaluacion(DatosVO datosVO, long est, double
	 * notPru1, double notPru2, double notPru3, double notPru4, double notPru5,
	 * double notNum, String notCon, boolean estado, List compa) { boolean
	 * retorno=true; Connection cn=null; PreparedStatement ps=null; int
	 * posicion=1; try { cn=cursor.getConnection();
	 * 
	 * /* System.out.println("************************************");
	 * System.out.println("inst "+datosVO.getInstitucion());
	 * System.out.println("metod "+datosVO.getMetodologia());
	 * System.out.println("ano "+datosVO.getAnVigencia());
	 * System.out.println("per "+datosVO.getPerVigencia());
	 * System.out.println("asig "+datosVO.getAsignatura());
	 * System.out.println("prueba "+datosVO.getPrueba());
	 * System.out.println("est "+est); System.out.println("notNum "+notNum);
	 * System.out.println("notCon "+notCon);
	 * System.out.println("estado "+estado);
	 */

	/*
	 * Iterator iter=compa.iterator(); EstEvaluacionVO ev; boolean
	 * actualiza=false,inserta=true; //System.out.println("codigo="
	 * +est+" notNum= "+notNum+" notCon= "+notCon+" estado= "+estado);
	 * while(iter.hasNext()){ ev=(EstEvaluacionVO)iter.next(); //
	 * System.out.println("codigo="
	 * +ev.getCodigo()+" notNum= "+ev.getNotaNum()+" notCon= "
	 * +ev.getNotaCon()+" estado= "+ev.isEstado()); // System.out.println(
	 * "****************************************************************");
	 * if(est==ev.getCodigo()){
	 * if(!notCon.equals(ev.getNotaCon())||notNum!=ev.getNotaPru1
	 * ()||notNum!=ev.getNotaPru2
	 * ()||notNum!=ev.getNotaPru3()||notNum!=ev.getNotaPru4
	 * ()||notNum!=ev.getNotaPru5
	 * ()||notNum!=ev.getNotaNum()||estado!=ev.isEstado()){
	 * System.out.println("Actualizar"); actualiza=true; } inserta=false; } }
	 * 
	 * if(inserta){ ps=cn.prepareStatement(rb.getString("insertar_Evaluacion"));
	 * System.out.println("Insertar");
	 * ps.setLong(posicion++,datosVO.getAsignatura());
	 * ps.setLong(posicion++,datosVO.getPrueba());
	 * ps.setInt(posicion++,datosVO.getBimestre()); ps.setLong(posicion++,est);
	 * ps.setDouble(posicion++,notPru1); ps.setDouble(posicion++,notPru2);
	 * ps.setDouble(posicion++,notPru3); ps.setDouble(posicion++,notPru4);
	 * ps.setDouble(posicion++,notPru5); ps.setDouble(posicion++,notNum);
	 * if(!notCon.equals("")) ps.setString(posicion++,notCon); else
	 * ps.setNull(posicion++,Types.VARCHAR); //
	 * ps.setBoolean(posicion++,estado);
	 * 
	 * ps.executeUpdate(); } if(actualiza){ // ***************Actualizar
	 * ******************************** try { posicion=1;
	 * ps=cn.prepareStatement(rb.getString("actualizar_Evaluacion"));
	 * ps.setDouble(posicion++,notPru1); ps.setDouble(posicion++,notPru2);
	 * ps.setDouble(posicion++,notPru3); ps.setDouble(posicion++,notPru4);
	 * ps.setDouble(posicion++,notPru5); ps.setDouble(posicion++,notNum);
	 * if(!notCon.equals("")) ps.setString(posicion++,notCon); else
	 * ps.setNull(posicion++,Types.VARCHAR);
	 * ps.setLong(posicion++,datosVO.getAsignatura());
	 * ps.setLong(posicion++,datosVO.getPrueba()); ps.setLong(posicion++,est);
	 * ps.executeUpdate(); } catch (SQLException e1) { retorno=false;
	 * e1.printStackTrace(); }
	 * //************************************************************ }
	 * 
	 * } catch (InternalErrorException e) { retorno=false; } catch (SQLException
	 * e) { switch (e.getErrorCode()) { case 1: case 2291:
	 * //setMensaje("Cndigo duplicado");
	 * 
	 * break; } }finally{ try{ OperacionesGenerales.closeStatement(ps);
	 * OperacionesGenerales.closeConnection(cn); }catch(InternalErrorException
	 * inte){} } return retorno;
	 */
	// }

	public List getEstEvaluacion(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		EstEvaluacionVO estEvaluacionVO = null;
		int i, pos = 1;
		try {
			cn = cursor.getConnection();

			st = cn.prepareStatement(rb.getString("getEstEvaluacion"));
			st.setLong(pos++, datosVO.getAsignatura());
			st.setLong(pos++, datosVO.getPrueba());
			st.setInt(pos++, datosVO.getBimestre());

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				estEvaluacionVO = new EstEvaluacionVO();
				estEvaluacionVO.setCodigo(rs.getInt(i++));
				estEvaluacionVO.setNotaPru1(rs.getFloat(i++));
				estEvaluacionVO.setNotaPru2(rs.getFloat(i++));
				estEvaluacionVO.setNotaPru3(rs.getFloat(i++));
				estEvaluacionVO.setNotaPru4(rs.getFloat(i++));
				estEvaluacionVO.setNotaPru5(rs.getFloat(i++));
				estEvaluacionVO.setNotaNum(rs.getFloat(i++));
				estEvaluacionVO.setNotaCon(rs.getString(i++));
				// estEvaluacionVO.setEstado(rs.getBoolean(i++));

				l.add(estEvaluacionVO);
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

	public LimitesVO getLimites() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		LimitesVO limitesVO = null;
		Connection cn = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getLimites"));
			rs = ps.executeQuery();
			while (rs.next()) {
				i = 1;
				limitesVO = new LimitesVO();
				limitesVO.setLimitA(rs.getInt(i++));
				limitesVO.setLimitB(rs.getInt(i++));
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
		return limitesVO;
	}

	public List getListaPruebas(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpru = new ArrayList();
		PruebaVO lp = null;
		int i = 0;
		try {
			if (datosVO.getPrueba() == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getPruebas"));
			i = 1;
			st.setLong(i++, datosVO.getPrueba());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new PruebaVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lp.setPorcentaje(rs.getFloat(i++));
				lpru.add(lp);
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
		return lpru;
	}

	public List getSubPruebas(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpru = new ArrayList();
		PruebaVO lp = null;
		int i = 0;
		try {
			if (datosVO.getPrueba() == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getSubPru"));
			i = 1;
			st.setLong(i++, datosVO.getAsignatura());
			st.setLong(i++, datosVO.getPrueba());
			rs = st.executeQuery();
			// System.out.println("antes "+datosVO.getAsignatura()+" "+datosVO.getPrueba());

			while (rs.next()) {
				i = 1;
				// System.out.println("encuentra sub pruebas");
				lp = new PruebaVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lp.setPorcentaje(rs.getFloat(i++));
				lpru.add(lp);
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
		return lpru;
	}

	public boolean isCerrada(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean retorno = false;
		int i = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getCierre"));
			ps.setLong(i++, datosVO.getGrupo());
			ps.setLong(i++, datosVO.getAsignatura());
			ps.setLong(i++, datosVO.getPrueba());
			ps.setLong(i++, datosVO.getBimestre());
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getBoolean(1))
					retorno = true;
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
		return retorno;
	}

	public boolean modificarCierre(Connection cn, boolean estado,
			DatosVO datosVO) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 1;
		try {
			ps = cn.prepareStatement(rb.getString("buscaCierre"));
			ps.setLong(i++, datosVO.getAsignatura());
			ps.setLong(i++, datosVO.getPrueba());
			ps.setLong(i++, datosVO.getGrupo());
			ps.setInt(i++, datosVO.getBimestre());
			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				// System.out.println("modifica cierre");
				ps = cn.prepareStatement(rb.getString("modificarCierre"));
				ps.setBoolean(i++, estado);
				ps.setLong(i++, datosVO.getAsignatura());
				ps.setLong(i++, datosVO.getPrueba());
				ps.setLong(i++, datosVO.getGrupo());
				ps.setInt(i++, datosVO.getBimestre());
				ps.execute();
			} else {
				i = 1;
				// System.out.println("Inserta cierre");
				ps = cn.prepareStatement(rb.getString("insertaCierre"));
				ps.setLong(i++, datosVO.getAsignatura());
				ps.setLong(i++, datosVO.getPrueba());
				ps.setLong(i++, datosVO.getGrupo());
				ps.setBoolean(i++, estado);
				ps.setInt(i++, datosVO.getBimestre());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			// setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
			// setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public String getDocente(long insti, long sede, long jornada, long grupo,
			long asignatura) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 1;
		String docente = "";
		try {

			/*
			 * System.out.println("**************Docente**********************");
			 * System.out.println("inst "+insti);
			 * System.out.println("sede "+sede);
			 * System.out.println("jornada "+jornada);
			 * System.out.println("grupo "+grupo);
			 * System.out.println("asignatura "+asignatura);
			 */

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getDocente"));
			ps.setLong(i++, insti);
			ps.setLong(i++, sede);
			ps.setLong(i++, jornada);
			ps.setLong(i++, grupo);
			ps.setLong(i++, asignatura);

			rs = ps.executeQuery();
			while (rs.next()) {
				i = 1;
				docente = rs.getString(i++);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos del docente. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos del docente. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		// System.out.println("Docente encontrado------>"+docente);
		return docente;
	}

	public boolean insertar(EvaluacionVO evaluacionVO, DatosVO datosVO) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int posicion = 1;
		int x = ver(evaluacionVO);
		try {
			cn = cursor.getConnection();
			/*
			 * if(evaluacionVO.getArtEvaCodEstudiante()!=null){ for(int
			 * a=0;a<evaluacionVO.getArtEvaCodEstudiante().length;a++){
			 * System.out
			 * .println(evaluacionVO.getArtEvaCodEstudiante()[a]+": "+
			 * evaluacionVO .
			 * getArtEvaNotaNumSp1()[a]+" - "+evaluacionVO.getArtEvaNotaNumSp2()
			 * [a]+" - "+evaluacionVO.getArtEvaNotaNumSp3()[a]); } }
			 */
			cn.setAutoCommit(false);
			if (evaluacionVO.getArtEvaCodEstudiante() != null) {
				for (int a = 0; a < evaluacionVO.getArtEvaCodEstudiante().length; a++) {
					posicion = 1;
					if (!existe(cn, datosVO,
							evaluacionVO.getArtEvaCodEstudiante()[a])) {
						if (evaluacionVO.getArtEvaNotaNum()[a] != -1) {
							ps1 = cn.prepareStatement(rb
									.getString("insertar_Evaluacion" + x));
							ps1.setLong(posicion++, datosVO.getAsignatura());
							ps1.setLong(posicion++, datosVO.getPrueba());
							ps1.setLong(posicion++, datosVO.getBimestre());
							ps1.setLong(posicion++,
									evaluacionVO.getArtEvaCodEstudiante()[a]);
							switch (x) {
							case 5:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp3()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp3()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp4()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp4()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp5()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp5()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps1.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);

								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps1.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								break;
							case 4:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp3()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp3()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp4()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp4()[a]);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps1.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps1.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								break;
							case 3:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp3()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp3()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps1.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps1.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								break;
							case 2:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps1.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps1.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								break;
							case 1:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps1.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps1.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps1.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps1.setNull(posicion++, Types.VARCHAR);
								break;
							}
							ps1.executeUpdate();
							ps1.close();
						}
					} else {
						if (evaluacionVO.getArtEvaNotaNum()[a] != -1) {
							ps2 = cn.prepareStatement(rb
									.getString("actualizar_Evaluacion" + x));
							switch (x) {
							case 5:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp3()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp3()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp4()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp4()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp5()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp5()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps2.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps2.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								break;
							case 4:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp3()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp3()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp4()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp4()[a]);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps2.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps2.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								break;
							case 3:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp3()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp3()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps2.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps2.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								break;
							case 2:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNumSp2()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp2()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps2.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps2.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								break;
							case 1:
								if (evaluacionVO.getArtEvaNotaNumSp1()[a] != -1)
									ps2.setDouble(
											posicion++,
											evaluacionVO.getArtEvaNotaNumSp1()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (evaluacionVO.getArtEvaNotaNum()[a] != -1)
									ps2.setDouble(posicion++,
											evaluacionVO.getArtEvaNotaNum()[a]);
								else
									ps2.setNull(posicion++, Types.VARCHAR);
								if (!evaluacionVO.getArtEvaNotaConc()[a]
										.equals(""))
									ps2.setString(posicion++,
											evaluacionVO.getArtEvaNotaConc()[a]);
								else {
									ps2.setNull(posicion++, Types.VARCHAR);
								}
								break;
							}
							ps2.setLong(posicion++, datosVO.getAsignatura());
							ps2.setLong(posicion++, datosVO.getPrueba());
							ps2.setLong(posicion++, datosVO.getBimestre());
							ps2.setLong(posicion++,
									evaluacionVO.getArtEvaCodEstudiante()[a]);
							ps2.executeUpdate();
							ps2.close();
						} else {
							borrarEstudiante(cn,
									evaluacionVO.getArtEvaCodEstudiante()[a]);
						}
					}
				}
			}
			modificarCierre(cn, evaluacionVO.isArtEvaEstado(), datosVO);
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				e.printStackTrace();
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
				e.printStackTrace();
			}
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps1);
				OperacionesGenerales.closeStatement(ps2);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return retorno;
	}

	private void borrarEstudiante(Connection cn, long cod) {
		PreparedStatement ps = null;
		try {
			ps = cn.prepareStatement(rb.getString("borra_estudiante"));
			ps.setLong(1, cod);
			ps.executeUpdate();
			// System.out.println("Borra estudiante= "+cod);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException e) {
				e.printStackTrace();
			}
		}

	}

	private int ver(EvaluacionVO evaluacionVO) {
		int c = 0;
		if (evaluacionVO.getArtEvaNotaNumSp1() != null) {
			c++;
		}
		if (evaluacionVO.getArtEvaNotaNumSp2() != null) {
			c++;
		}
		if (evaluacionVO.getArtEvaNotaNumSp3() != null) {
			c++;
		}
		if (evaluacionVO.getArtEvaNotaNumSp4() != null) {
			c++;
		}
		if (evaluacionVO.getArtEvaNotaNumSp5() != null) {
			c++;
		}
		return c;
	}

	public boolean existe(Connection cn, DatosVO datosVO, long codigo)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean retorno = false;
		int i = 1;
		try {
			ps = cn.prepareStatement(rb.getString("existeEstudiante"));
			ps.setLong(i++, datosVO.getAsignatura());
			ps.setLong(i++, datosVO.getPrueba());
			ps.setLong(i++, datosVO.getBimestre());
			ps.setLong(i++, codigo);
			rs = ps.executeQuery();
			while (rs.next()) {
				// System.out.println("existe el alumno en la prueba");
				retorno = true;
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
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}
}
