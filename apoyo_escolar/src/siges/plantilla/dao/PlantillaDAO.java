package siges.plantilla.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.plantilla.beans.DimensionesVO;
import siges.plantilla.beans.FiltroBeanEvaluacion;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.Logros;
import siges.plantilla.beans.NivelEvalVO;
import siges.plantilla.beans.ParamsVO;
import siges.plantilla.beans.TipoEvalVO;

/**
 * siges.plantilla.dao<br>
 * Funcinn: Objeto de acceso a datos que obtiene los datos necesarios para
 * generar las plantillas <br>
 */
public class PlantillaDAO extends Dao {
	public String sentencia;

	public String buscar;

	private ResourceBundle rb;

	private java.text.SimpleDateFormat sdf;

	/**
	 * @param cur
	 */
	public PlantillaDAO(Cursor cur) {
		super(cur);
		sentencia = null;
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
	}

	/**
	 * @param f
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public Collection getEstudiantes(FiltroPlantilla f) throws Exception {
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaEstudianteLogro"
					+ (Integer.parseInt(f.getOrden()) + 1)));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
			// calcula la jerarquia de grupos
			ps = cn.prepareStatement(rb.getString("jerarquiaGrupo"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
			rs = ps.executeQuery();
			if (rs.next()) {
				f.setJerarquiagrupo(rs.getString(1));
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			// System.out.println();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
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

	/**
	 * @param c
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getJerarquiaGrupo(Collection c) {
		if (!c.isEmpty()) {
			Iterator iterator = c.iterator();
			Object[] o;
			if (iterator.hasNext()) {
				o = (Object[]) iterator.next();
				return ((String) (o[6] != null ? o[6] : ""));
			}
		}
		return "";
	}

	/**
	 * @param f
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getEstadoNotasAsig(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")) {
			return null;
		}
		int tipo = 2;
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ f.getPeriodo()));
			posicion = 1;
			if (f.getJerarquiagrupo().equals(""))
				ps.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				ps.setLong(posicion++, Long.parseLong(f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return "0";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param f
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public Collection getLogro(FiltroPlantilla f) {
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf("|") + 1,
				f.getAsignatura().length());
		// System.out.println("Codigo real de asignatura="+f.getAsignatura()+" Codigo cambiado="+asig);
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {

			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaLogroAsignatura.0"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0)
				ps = cn.prepareStatement(rb.getString("listaLogroAsignatura"));
			else
				ps = cn.prepareStatement(rb
						.getString("listaLogroAsignatura.docente"));
			posicion = 1;
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, vigencia);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			if (f.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, Long.parseLong(f.getDocente()));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: "
					+ in);
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener logros. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * @param f
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public String[] getNombres(FiltroPlantilla f) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		String[] a = new String[3];
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("plantillaLinea.nombres"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			rs = ps.executeQuery();
			if (rs.next()) {
				a[0] = rs.getString(1);
				a[1] = rs.getString(2);
				a[2] = rs.getString(3);
			}
			rs.close();
			ps.close();
			return a;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: "
					+ in);
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener logros. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
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
		return null;
	}

	/**
	 * @param n
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public Collection getEscala(int n) {
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaEscala"));
			ps.setInt(1, n);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener escala de AREA/ASIG. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
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

	public Collection getEscalaRecuperacion(int n) {
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaEscalaRecuperacion"));
			ps.setInt(1, n);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener escala de AREA/ASIG. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
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

	public void setReporteZip(String us, String rec, String tipo,
			String nombre, int modulo, int estado) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ReporteInsertar2"));
			int posicion = 1;
			ps.setString(posicion++, us);
			ps.setString(posicion++, rec);
			ps.setString(posicion++, tipo);
			ps.setString(posicion++, nombre);
			ps.setInt(posicion++, modulo);
			ps.setInt(posicion++, estado);
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
			setMensaje("Error intentando insertar reporte. Posible problema: ");
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

	public void setReporteActualizar(String us, String rec, int estado,
			String mensaje) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ReporteActualizar"));
			int posicion = 1;
			ps.setInt(posicion++, estado);
			ps.setString(posicion++, mensaje);
			ps.setString(posicion++, us);
			ps.setString(posicion++, rec);
			int n = ps.executeUpdate();
			// System.out.println("Actualiza: "+n+" para rec="+rec);
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
			setMensaje("Error intentando actualizar reporte. Posible problema: ");
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

	public void actualizarTodos() {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb
					.getString("plantillaHilo.actualizarTodos"));
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
			setMensaje("Error intentando actualizar Todos los reportes. Posible problema: ");
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

	public void actualizarPlantillas(String us, String rec, int estado,
			String mensaje) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("plantillaHilo.actualizar"));
			int posicion = 1;
			ps.setInt(posicion++, estado);
			ps.setString(posicion++, mensaje);
			ps.setString(posicion++, us);
			ps.setString(posicion++, rec);
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
			setMensaje("Error intentando actualizar plantillas. Posible problema: ");
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

	public void setReporte(String us, String rec, String tipo, String nombre,
			int modulo) {
		Connection cn = null;
		PreparedStatement ps = null;
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

	public boolean insertarPlantillas(Login login,
			FiltroPlantilla filtroPlantilla, int tipo, String[] ubicacion) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("plantillaHilo.insertar"));
			int posicion = 1;
			ps.setInt(posicion++, tipo);
			ps.setInt(posicion++, -1);
			ps.setString(posicion++, login.getUsuarioId());
			ps.setString(posicion++, filtroPlantilla.getInstitucion());
			ps.setString(posicion++, filtroPlantilla.getSede());
			ps.setString(posicion++, filtroPlantilla.getJornada());
			ps.setString(posicion++, filtroPlantilla.getGrado());
			ps.setString(posicion++, filtroPlantilla.getGrado_());
			ps.setString(posicion++, filtroPlantilla.getGrupo());
			ps.setString(posicion++, filtroPlantilla.getGrupo_());
			ps.setString(posicion++, filtroPlantilla.getArea());
			ps.setString(posicion++, filtroPlantilla.getArea_());
			ps.setString(posicion++, filtroPlantilla.getAsignatura());
			ps.setString(posicion++, filtroPlantilla.getAsignatura_());
			ps.setString(posicion++, filtroPlantilla.getMetodologia());
			ps.setString(posicion++, filtroPlantilla.getMetodologia_());
			ps.setString(posicion++, filtroPlantilla.getPeriodo());
			ps.setString(posicion++, filtroPlantilla.getPeriodo_());
			ps.setString(posicion++, filtroPlantilla.getOrden());
			ps.setString(posicion++, ubicacion[0]);
			ps.setString(posicion++, filtroPlantilla.getPeriodo2());
			ps.setString(posicion++, filtroPlantilla.getPeriodo2_());
			ps.setString(posicion++, filtroPlantilla.getDocente());
			ps.setString(posicion++, filtroPlantilla.getDocente_());
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar plantillas. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @param f
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public Collection getNotasLogro(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")
				|| f.getJerarquiagrupo().trim().equals("0")) {
			return new ArrayList();
		}
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf("|") + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaAsignatura"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			if (jer == 0)
				return null;
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0)
				ps = cn.prepareStatement(rb
						.getString("listaLogroNotaEstudiante"));
			else
				ps = cn.prepareStatement(rb
						.getString("listaLogroNotaEstudiante.docente"));
			posicion = 1;
			ps.setLong(posicion++,
					Long.parseLong("0" + f.getJerarquiagrupo().trim()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, vigencia);
			if (f.getFilPlanEstudios() == 1) {
				ps.setLong(posicion++, Long.parseLong(f.getDocente()));
			}
			// System.out.println(".@"+f.getJerarquiagrupo()+"//"+jer+"//"+f.getPeriodo()+"//"+vigencia+"//"+new
			// java.sql.Timestamp(System.currentTimeMillis()));
			rs = ps.executeQuery();
			// System.out.println(".@"+f.getJerarquiagrupo()+"//"+jer+"//"+f.getPeriodo()+"//"+vigencia+"//"+new
			// java.sql.Timestamp(System.currentTimeMillis()));
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			// d
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	/*
	 * public Collection getNotasRecuperacionLogro(FiltroPlantilla f) {
	 * if(f.getJerarquiagrupo().trim().equals("")){return new ArrayList();}
	 * //String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
	 * 1, f.getGrupo().length()); String asig =
	 * f.getAsignatura().substring(f.getAsignatura().lastIndexOf("|") +
	 * 1,f.getAsignatura().length()); int posicion = 1; Connection cn = null;
	 * PreparedStatement ps = null; Statement st = null; ResultSet rs = null;
	 * long jer = 0; Collection list = null; try{ long
	 * vigencia=getVigenciaInst(Long.parseLong(f.getInstitucion())); cn =
	 * cursor.getConnection(); ps =
	 * cn.prepareStatement(rb.getString("jerarquiaAsignatura")); posicion = 1;
	 * ps.setLong(posicion++, 1); ps.setLong(posicion++,
	 * Long.parseLong(f.getInstitucion())); ps.setLong(posicion++,
	 * Long.parseLong(f.getGrado())); ps.setLong(posicion++,
	 * Long.parseLong(asig)); ps.setLong(posicion++,
	 * Long.parseLong(f.getMetodologia())); ps.setLong(posicion++, vigencia); rs
	 * = ps.executeQuery(); if (rs.next()) { jer = rs.getLong(1); } if (jer ==
	 * 0) return null; rs.close(); ps.close(); ps =
	 * cn.prepareStatement(rb.getString
	 * ("listaRecuperacionLogroNotaEstudiante")); posicion = 1;
	 * ps.setLong(posicion++, Long.parseLong("0"+f.getJerarquiagrupo().trim()));
	 * ps.setLong(posicion++, jer); ps.setLong(posicion++,
	 * Long.parseLong(f.getPeriodo())); ps.setLong(posicion++, vigencia);
	 * ps.setLong(posicion++, 1); ps.setString(posicion++, "N"); rs =
	 * ps.executeQuery(); list = getCollection(rs);
	 * //System.out.println(f.getJerarquiagrupo()+"//"+jer); rs.close();
	 * ps.close(); } catch (InternalErrorException in) {
	 * setMensaje("NO se puede estabecer conexinn con la base de datos: ");
	 * return null; } catch (SQLException sqle){ sqle.printStackTrace(); try{
	 * cn.rollback(); }catch(SQLException s){} setMensaje(
	 * "Error intentando obtener notas de Recuperacion. Posible problema: ");
	 * switch (sqle.getErrorCode()) { default:
	 * setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n')); }
	 * return null; } finally { try { OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeStatement(ps);
	 * OperacionesGenerales.closeConnection(cn); cursor.cerrar(); } catch
	 * (InternalErrorException inte) { } } return list; }
	 */
	/**
	 * @param f
	 * @return<br> Return Type: Collection[]<br>
	 *             Version 1.1.<br>
	 */
	public Collection getNotasAsig(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")
				|| f.getJerarquiagrupo().equals("0")) {
			// System.out.println("--Entra por erorr en el DAO");
			return new ArrayList();
		}
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf('|') + 1,
				f.getAsignatura().length());
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		long vigencia;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = new ArrayList();
		try {
			vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaAsignatura"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			if (jer == 0) {
				return null;
			}
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb.getString("plantilla.NotaAsignatura"
					+ f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++,
					Long.parseLong("0" + f.getJerarquiagrupo().trim()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
			/*
			 * //ausencia ps =
			 * cn.prepareStatement(rb.getString("plantilla.AusenciaAsignatura"+
			 * f.getPeriodo())); posicion = 1; ps.setLong(posicion++,
			 * Long.parseLong("0"+f.getJerarquiagrupo().trim()));
			 * ps.setLong(posicion++, Long.parseLong(jer));
			 * ps.setLong(posicion++, vigencia); rs = ps.executeQuery(); list[1]
			 * = getCollection(rs); rs.close(); ps.close();
			 */
		} catch (InternalErrorException in) {
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * @param f
	 * @return<br> Return Type: Collection[]<br>
	 *             Version 1.1.<br>
	 */
	public Collection getNotasArea(FiltroPlantilla f) {
		// System.out.println("getNotasArea ");
		if (f.getJerarquiagrupo().trim().equals("")) {
			return new ArrayList();
		}
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		long vigencia;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = null;
		Collection list = new ArrayList();
		try {
			vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaArea"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getString(1);
			}
			if (jer == null) {
				System.out.println("No hay Codigo de Jerarquia.");
				return null;
			}
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb.getString("plantilla.NotaArea"
					+ f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++,
					Long.parseLong("0" + f.getJerarquiagrupo().trim()));
			ps.setLong(posicion++, Long.parseLong(jer));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
			// ausencia
			/*
			 * ps = cn.prepareStatement(rb.getString("plantilla.AusenciaArea"+
			 * f.getPeriodo())); posicion = 1; ps.setLong(posicion++,
			 * Long.parseLong("0"+f.getJerarquiagrupo().trim()));
			 * ps.setLong(posicion++, Long.parseLong(jer));
			 * ps.setLong(posicion++, vigencia); rs = ps.executeQuery(); list[1]
			 * = getCollection(rs); rs.close(); ps.close();
			 */
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * @param f
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getEstadoNotasArea(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")) {
			return null;
		}
		int tipo = 1;
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ f.getPeriodo()));
			posicion = 1;
			if (f.getJerarquiagrupo().equals(""))
				ps.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				ps.setLong(posicion++, Long.parseLong(f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return "0";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
		return null;
	}

	/**
	 * @param f
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public Collection getNotasDesc(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")) {
			return new ArrayList();
		}
		String area = f.getArea().substring(f.getArea().lastIndexOf('|') + 1,
				f.getArea().length());
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaArea"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			if (jer == 0)
				return null;
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0)
				ps = cn.prepareStatement(rb
						.getString("listaDescriptorNotaEstudiante"));
			else
				ps = cn.prepareStatement(rb
						.getString("listaDescriptorNotaEstudiante.docente"));
			posicion = 1;
			ps.setLong(posicion++,
					Long.parseLong("0" + f.getJerarquiagrupo().trim()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, vigencia);
			if (f.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, Long.parseLong(f.getDocente()));
			// System.out.println(".@"+f.getJerarquiagrupo()+"//"+jer+"//"+f.getPeriodo()+"//"+vigencia+"//"+new
			// java.sql.Timestamp(System.currentTimeMillis()));
			rs = ps.executeQuery();
			// System.out.println(".@"+f.getJerarquiagrupo()+"//"+jer+"//"+f.getPeriodo()+"//"+vigencia+"//"+new
			// java.sql.Timestamp(System.currentTimeMillis()));
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Desc. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * @param f
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public Collection getDescriptor(FiltroPlantilla f) {
		String area = f.getArea().substring(f.getArea().lastIndexOf("|") + 1,
				f.getArea().length());
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("filtroDescriptor2.0"));
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(area));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0)
				ps = cn.prepareStatement(rb.getString("filtroDescriptor2"));
			else
				ps = cn.prepareStatement(rb
						.getString("filtroDescriptor2.docente"));
			posicion = 1;
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, vigencia);
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
			if (f.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, Long.parseLong(f.getDocente()));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener descriptores. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * @param f
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getEstadoNotasPree(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")) {
			return null;
		}
		int tipo = 1;
		String area = f.getArea();
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb
					.getString("ExisteGrupoCerradoPreescolar" + f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++,
					Long.parseLong("0" + f.getJerarquiagrupo().trim()));
			ps.setLong(posicion++, tipo);
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				return "1";
			} else {
				return "0";
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estado de preescolar. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
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
		return area;
	}

	/**
	 * @param f
	 * @return<br> Return Type: Collection<br>
	 *             Version 1.1.<br>
	 */
	public Collection getNotasPree(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")) {
			return new ArrayList();
		}
		// no es igual al metodo del paquete de evaluaciones. Porque
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = null;
		Collection list = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("PlantillaPreescolar.notas"
					+ f.getPeriodo()));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong("0" + f.getJerarquiagrupo()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	public FiltroPlantilla[] getPila() {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		FiltroPlantilla[] filtroPlantilla = new FiltroPlantilla[5];
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("plantillaHilo.seleccionar"));
			rs = pst.executeQuery();
			int n = 0;
			int posicion = 1;
			while (rs.next()) {
				posicion = 1;
				filtroPlantilla[n] = new FiltroPlantilla();
				filtroPlantilla[n].setTipoPlantilla(rs.getString(posicion++));
				filtroPlantilla[n].setEstadoPlantilla(rs.getString(posicion++));
				filtroPlantilla[n]
						.setUsuarioPlantilla(rs.getString(posicion++));
				filtroPlantilla[n].setInstitucion(rs.getString(posicion++));
				filtroPlantilla[n].setSede(rs.getString(posicion++));
				filtroPlantilla[n].setJornada(rs.getString(posicion++));
				filtroPlantilla[n].setGrado(rs.getString(posicion++));
				filtroPlantilla[n].setGrado_(rs.getString(posicion++));
				filtroPlantilla[n].setGrupo(rs.getString(posicion++));
				filtroPlantilla[n].setGrupo_(rs.getString(posicion++));
				filtroPlantilla[n].setArea(rs.getString(posicion++));
				filtroPlantilla[n].setArea_(rs.getString(posicion++));
				filtroPlantilla[n].setAsignatura(rs.getString(posicion++));
				filtroPlantilla[n].setAsignatura_(rs.getString(posicion++));
				// filtroPlantilla[n].setAsignaturaAbre_(filtroPlantilla[n].getAsignatura_().substring(0,35)
				// );
				filtroPlantilla[n].setMetodologia(rs.getString(posicion++));
				filtroPlantilla[n].setMetodologia_(rs.getString(posicion++));
				filtroPlantilla[n].setPeriodo(rs.getString(posicion++));
				filtroPlantilla[n].setPeriodo_(rs.getString(posicion++));
				filtroPlantilla[n].setOrden(rs.getString(posicion++));
				filtroPlantilla[n].setNombrePlantilla(rs.getString(posicion++));
				filtroPlantilla[n].setPeriodo2(rs.getString(posicion++));
				filtroPlantilla[n].setPeriodo2_(rs.getString(posicion++));
				filtroPlantilla[n].setDocente(rs.getString(posicion++));
				filtroPlantilla[n].setDocente_(rs.getString(posicion++));
				filtroPlantilla[n].setFilPlanEstudios(getEstadoPlan(cn,
						Long.parseLong(filtroPlantilla[n].getInstitucion())));
				n++;
			}
			return filtroPlantilla;
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getEstudiantePreescolar(FiltroPlantilla filtro) {
		List l = new ArrayList();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		String grupo = filtro.getGrupo().substring(
				filtro.getGrupo().lastIndexOf("|") + 1,
				filtro.getGrupo().length());
		long jerGru = 0;
		try {
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, Long.parseLong(filtro.getInstitucion()));
			st.setInt(i++, Integer.parseInt(filtro.getSede()));
			st.setInt(i++, Integer.parseInt(filtro.getJornada()));
			st.setInt(i++, Integer.parseInt(filtro.getMetodologia()));
			st.setInt(i++, Integer.parseInt(filtro.getGrado()));
			st.setInt(i++, Integer.parseInt(grupo));
			rs = st.executeQuery();
			if (rs.next()) {
				jerGru = rs.getLong(1);
				filtro.setJerarquiagrupo(rs.getString(1));
			}
			rs.close();
			st.close();
			// System.out.println("1.))"+"getEstudianteDimension."+filtro.getFilOrden()+"."+filtro.getFilPeriodo());
			// System.out.println("2."+filtro.getFilJerarquia());
			st = cn.prepareStatement(rb.getString("getEstudianteDimension."
					+ filtro.getOrden() + "." + filtro.getPeriodo()));
			i = 1;
			st.setLong(i++, jerGru);
			st.setLong(i++,
					getVigenciaInst(Long.parseLong(filtro.getInstitucion())));
			rs = st.executeQuery();
			String registro[] = null;
			while (rs.next()) {
				i = 0;
				registro = new String[6];
				registro[i] = rs.getString(++i);
				registro[i] = rs.getString(++i);
				registro[i] = rs.getString(++i);
				registro[i] = rs.getString(++i);
				registro[i] = rs.getString(++i);
				registro[i] = rs.getString(++i);
				l.add(registro);
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

	public int getEstadoPlan(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.getEstadoPlan"));
			st.setLong(i++, inst);
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

	public List getListaDocenteArea(long inst, int metod, int vig, int grado,
			long area) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("descriptor.listaDocenteArea"));
			st.setInt(i++, vig);
			st.setLong(i++, area);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaDocenteAsignatura(long inst, int metod, int vig,
			int grado, long asig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("logro.listaDocenteAsignatura"));
			st.setInt(i++, vig);
			st.setLong(i++, asig);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public String getNombreDocente(long docente) throws Exception {
		System.out.println("getNombreDocente");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.getNombreDocente"));
			System.out.println("docente " + docente);
			st.setString(i++, docente + "");
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
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

	public List getListaAsignatura(long inst, int metod, int vig, int grado)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("listaPlantillaLogro1"));
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			st.setInt(i++, vig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaDocenteAsignatura(long inst, int metod, int vig,
			int grado) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("logro.listaDocenteAsignatura2"));
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				item.setPlantillaDocente(rs.getLong(i++));
				item.setPlantillaDocente_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaAsignaturaGrado(long inst, int metod, int vig)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("listaPlantillaLogro2"));
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, vig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaGrado(rs.getString(i++));
				item.setPlantillaGrado_(rs.getString(i++));
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaDocenteAsignaturaGrado(long inst, int metod, int vig)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("logro.listaDocenteAsignaturaGrado"));
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaGrado(rs.getString(i++));
				item.setPlantillaGrado_(rs.getString(i++));
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				item.setPlantillaDocente(rs.getLong(i++));
				item.setPlantillaDocente_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaArea(long inst, int metod, int vig, int grado)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("listaPlantillaDescriptor1"));
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			st.setInt(i++, vig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaDocenteArea(long inst, int metod, int vig, int grado)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("descriptor.listaDocenteArea2"));
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				item.setPlantillaDocente(rs.getLong(i++));
				item.setPlantillaDocente_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaDocenteAreaGrado(long inst, int metod, int vig)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("descriptor.listaDocenteAreaGrado"));
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaGrado(rs.getString(i++));
				item.setPlantillaGrado_(rs.getString(i++));
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				item.setPlantillaDocente(rs.getLong(i++));
				item.setPlantillaDocente_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaAreaGrado(long inst, int metod, int vig)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		Logros item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("listaPlantillaDescriptor2"));
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, vig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new Logros();
				item.setPlantillaGrado(rs.getString(i++));
				item.setPlantillaGrado_(rs.getString(i++));
				item.setPlantillaAsignatura(rs.getString(i++));
				item.setPlantillaAsignatura_(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaGrupos(FiltroPlantilla f) throws Exception {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO item = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("plantilla.listaGrupo"));
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setInt(posicion++, Integer.parseInt(f.getSede()));
			ps.setInt(posicion++, Integer.parseInt(f.getJornada()));
			ps.setInt(posicion++, Integer.parseInt(f.getMetodologia()));
			ps.setInt(posicion++, Integer.parseInt(f.getGrado()));
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new ItemVO();
				item.setCodigo(rs.getLong(1));
				item.setNombre(rs.getString(2));
				list.add(item);
			}
		} catch (InternalErrorException in) {
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public List getListaAsignaturas(FiltroPlantilla f) throws Exception {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO item = null;
		try {
			int vig = (int) getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			if (f.getFilPlanEstudios() == 0) {
				ps = cn.prepareStatement(rb.getString("plantilla.listaAsig"));
				ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
				ps.setInt(posicion++, Integer.parseInt(f.getMetodologia()));
				ps.setInt(posicion++, Integer.parseInt(f.getGrado()));
				ps.setInt(posicion++, vig);
				rs = ps.executeQuery();
				while (rs.next()) {
					item = new ItemVO();
					item.setCodigo(rs.getLong(1));
					item.setNombre(rs.getString(2));
					list.add(item);
				}
			} else {
				ps = cn.prepareStatement(rb
						.getString("logro.listaDocenteAsignatura2"));
				ps.setInt(posicion++, vig);
				ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
				ps.setInt(posicion++, Integer.parseInt(f.getMetodologia()));
				ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
				ps.setInt(posicion++, Integer.parseInt(f.getMetodologia()));
				ps.setInt(posicion++, Integer.parseInt(f.getGrado()));
				rs = ps.executeQuery();
				while (rs.next()) {
					item = new ItemVO();
					item.setCodigo(rs.getLong(1));
					item.setNombre(rs.getString(2));
					item.setPadre(rs.getLong(3));
					item.setPadre2(rs.getString(4));
					list.add(item);
				}
			}
		} catch (InternalErrorException in) {
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public int getEstadoPlan(Connection cn, long inst) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			st = cn.prepareStatement(rb.getString("logro.getEstadoPlan"));
			st.setLong(i++, inst);
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
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public List getListaAreas(FiltroPlantilla f) throws Exception {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO item = null;
		try {
			int vig = (int) getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			if (f.getFilPlanEstudios() == 0) {
				ps = cn.prepareStatement(rb.getString("plantilla.listaArea"));
				ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
				ps.setInt(posicion++, Integer.parseInt(f.getMetodologia()));
				ps.setInt(posicion++, Integer.parseInt(f.getGrado()));
				ps.setInt(posicion++, vig);
				rs = ps.executeQuery();
				while (rs.next()) {
					item = new ItemVO();
					item.setCodigo(rs.getLong(1));
					item.setNombre(rs.getString(2));
					list.add(item);
				}
			} else {
				ps = cn.prepareStatement(rb
						.getString("descriptor.listaDocenteArea2"));
				ps.setInt(posicion++, vig);
				ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
				ps.setInt(posicion++, Integer.parseInt(f.getMetodologia()));
				ps.setInt(posicion++, Integer.parseInt(f.getGrado()));
				rs = ps.executeQuery();
				while (rs.next()) {
					item = new ItemVO();
					item.setCodigo(rs.getLong(1));
					item.setNombre(rs.getString(2));
					item.setPadre(rs.getLong(3));
					item.setPadre2(rs.getString(4));
					list.add(item);
				}
			}
		} catch (InternalErrorException in) {
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	/*
	 * public Collection getNotasLogro(FiltroPlantilla f) {
	 * if(f.getJerarquiagrupo().trim().equals("")){return new ArrayList();}
	 * String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") + 1,
	 * f.getGrupo().length()); String asig =
	 * f.getAsignatura().substring(f.getAsignatura().lastIndexOf("|") +
	 * 1,f.getAsignatura().length()); int posicion = 1; Connection cn = null;
	 * PreparedStatement ps = null; Statement st = null; ResultSet rs = null;
	 * String jer = null; Collection list = null; try { long
	 * vigencia=getVigenciaInst(Long.parseLong(f.getInstitucion())); cn =
	 * cursor.getConnection(); ps =
	 * cn.prepareStatement(rb.getString("jerarquiaAsignatura")); posicion = 1;
	 * ps.setLong(posicion++, 1); ps.setLong(posicion++,
	 * Long.parseLong(f.getInstitucion())); ps.setLong(posicion++,
	 * Long.parseLong(f.getGrado())); ps.setLong(posicion++,
	 * Long.parseLong(asig)); ps.setLong(posicion++,
	 * Long.parseLong(f.getMetodologia())); ps.setLong(posicion++, vigencia); rs
	 * = ps.executeQuery(); if (rs.next()) { jer = rs.getString(1); } if (jer ==
	 * null) return null; rs.close(); ps.close(); ps =
	 * cn.prepareStatement(rb.getString("listaLogroNotaEstudiante")); posicion =
	 * 1; ps.setLong(posicion++,
	 * Long.parseLong("0"+f.getJerarquiagrupo().trim())); ps.setLong(posicion++,
	 * Long.parseLong(jer)); ps.setLong(posicion++,
	 * Long.parseLong(f.getPeriodo())); ps.setLong(posicion++, vigencia);
	 * //System
	 * .out.println(".@"+f.getJerarquiagrupo()+"//"+jer+"//"+f.getPeriodo
	 * ()+"//"+vigencia+"//"+new
	 * java.sql.Timestamp(System.currentTimeMillis())); rs = ps.executeQuery();
	 * /
	 * /System.out.println(".@"+f.getJerarquiagrupo()+"//"+jer+"//"+f.getPeriodo
	 * ()+"//"+vigencia+"//"+new
	 * java.sql.Timestamp(System.currentTimeMillis())); list =
	 * getCollection(rs); rs.close(); ps.close(); } catch
	 * (InternalErrorException in) {
	 * setMensaje("NO se puede estabecer conexinn con la base de datos: ");
	 * return null; } catch (SQLException sqle){ sqle.printStackTrace(); try{
	 * cn.rollback(); }catch(SQLException s){}
	 * setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
	 * switch (sqle.getErrorCode()) { default:
	 * setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n')); }
	 * return null; } finally { try { OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeStatement(ps);
	 * OperacionesGenerales.closeConnection(cn); cursor.cerrar(); } catch
	 * (InternalErrorException inte) { } } return list; }
	 */

	public Collection getNotasRecuperacionLogro(FiltroPlantilla f) {
		if (f.getJerarquiagrupo().trim().equals("")) {
			return new ArrayList();
		}
		// String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") +
		// 1, f.getGrupo().length());
		String asig = f.getAsignatura().substring(
				f.getAsignatura().lastIndexOf("|") + 1,
				f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		long jer = 0;
		Collection list = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(f.getInstitucion()));
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("jerarquiaAsignatura"));
			posicion = 1;
			ps.setLong(posicion++, 1);
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(asig));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			if (jer == 0)
				return null;
			rs.close();
			ps.close();
			if (f.getFilPlanEstudios() == 0) {
				ps = cn.prepareStatement(rb
						.getString("listaRecuperacionLogroNotaEstudiante"));
				posicion = 1;
				ps.setLong(posicion++,
						Long.parseLong("0" + f.getJerarquiagrupo().trim()));
				ps.setLong(posicion++, jer);
				ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
				ps.setLong(posicion++, vigencia);
				ps.setInt(posicion++, 1);
				ps.setString(posicion++, "N");
				rs = ps.executeQuery();
				list = getCollection(rs);
				rs.close();
				ps.close();
			} else {
				ps = cn.prepareStatement(rb
						.getString("listaRecuperacionLogroNotaEstudiante.docente"));
				posicion = 1;
				ps.setLong(posicion++,
						Long.parseLong("0" + f.getJerarquiagrupo().trim()));
				ps.setLong(posicion++, jer);
				ps.setLong(posicion++, Long.parseLong(f.getPeriodo()));
				ps.setLong(posicion++, vigencia);
				ps.setInt(posicion++, 1);
				ps.setString(posicion++, "N");
				ps.setLong(posicion++, Long.parseLong(f.getDocente()));
				rs = ps.executeQuery();
				list = getCollection(rs);
				rs.close();
				ps.close();
			}
			// System.out.println(f.getJerarquiagrupo()+"//"+jer);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Recuperacion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (NumberFormatException e) {
			// d
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	// CAMBIOS EVLUACION PARAMETRSO INSTITICION

	public TipoEvalVO getTipoEval(FiltroBeanEvaluacion filtroEvaluacion,
			Login login) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long vigencia = 0;
		long codigoNivelEval = 0;
		NivelEvalVO nivelEval = new NivelEvalVO();
		TipoEvalVO tipoEvalVO = new TipoEvalVO();
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaInst(Long.parseLong(login.getInstId()));
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, Long.parseLong(login.getInstId()));
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

			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, getVigenciaInst(Long.parseLong(login.getInstId())));
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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return tipoEvalVO;
	}

	public Collection getEscalaConceptual(
			FiltroBeanEvaluacion filtroEvaluacion, Login login)
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
		try {
			cn = cursor.getConnection();
			vigencia = getVigenciaInst(Long.parseLong(login.getInstId()));
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, vigencia);
			st.setLong(i++, Long.parseLong(login.getInstId()));
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
			list = getCollection(rs);
			i = 1;
			while (rs.next()) {
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
	public Collection getEscalaNivelEval(FiltroPlantilla filtroPlantilla)
			throws Exception {
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		Collection list = null;
		try {

			/*
			 * System.out.println("filtroPlantilla inst " +
			 * filtroPlantilla.getInstitucion() );
			 * System.out.println("filtroPlantilla sede " +
			 * filtroPlantilla.getSede() );
			 * System.out.println("filtroPlantilla jornada " +
			 * filtroPlantilla.getJornada() );
			 * System.out.println("filtroPlantilla.getMetodologia() " +
			 * filtroPlantilla.getMetodologia());
			 * System.out.println("filtroPlantilla.getGrado() " +
			 * filtroPlantilla.getGrado());
			 */
			Login login = new Login();
			login.setInstId(filtroPlantilla.getInstitucion());
			login.setSedeId(filtroPlantilla.getSede());
			login.setJornadaId(filtroPlantilla.getJornada());

			FiltroBeanEvaluacion filtroEvaluacion = new FiltroBeanEvaluacion();
			filtroEvaluacion.setMetodologia(filtroPlantilla.getMetodologia());
			filtroEvaluacion.setGrado(filtroPlantilla.getGrado());

			// 1. Obtener el nivel eval y el tipo eval (1.NUMERICO,
			// 2.CONCEPTUAL, 3.PORCENTUAL)

			TipoEvalVO tipoEvalVO = getTipoEval(filtroEvaluacion, login);

			filtroPlantilla.setFilCodTipoEval(tipoEvalVO.getCod_tipo_eval());
			filtroPlantilla.setRangosCompletos(tipoEvalVO
					.getEval_rangos_completos());
			filtroPlantilla.setFilCodModoEval(tipoEvalVO.getCod_modo_eval());
			filtroPlantilla.setFilCodTipoEvalPres(tipoEvalVO
					.getCod_tipo_eval_pree());

			// 2. Si es conceptual obtener escalas, sino retorna el max y min
			if (tipoEvalVO.getCod_tipo_eval() == ParamsVO.ESCALA_CONCEPTUAL) {
				// System.out.println("TIPO DE EVALUACION CONCEPTUAL " +
				// getEscalaConceptual( filtroEvaluacion, login).size());
				return getEscalaConceptual(filtroEvaluacion, login);
			} else {
				// System.out.println("TIPO DE EVALUACION NUM O PORCENTUAL");
				list = new ArrayList();
				Object[] O = new Object[3];
				O[0] = "";
				O[1] = "" + tipoEvalVO.getEval_min();
				O[2] = "" + tipoEvalVO.getEval_max();
				list.add(O);
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

	/**
	 * @function:
	 * @param filtroPlantilla
	 * @return
	 * @throws Exception
	 */
	public Collection getEscalaPreescolar() throws Exception {
		// System.out.println("getEscalaPreescolar ");
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		Collection list = null;

		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEscalaPreescolar"));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();

		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
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

	/**
	 * Metodo que obtiene todas las dimensiones creadas para una determinada
	 * institucion, metodologia y vigencia
	 * 
	 * @param inst
	 * @param metod
	 * @param vig
	 * @return
	 * @throws Exception
	 */
	public List getListaDimenciones(long vig, long inst, long metod)
			throws Exception {
		// System.out.println(new Date() + " - getListaDimenciones++ ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getListaDimenciones"));
			/*
			 * st.setLong(i++,vig); st.setLong(i++,inst); st.setLong(i++,metod);
			 */

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				DimensionesVO dim = new DimensionesVO();
				// dim.setDimcodinst(rs.getLong(i++));
				// dim.setDimcodmetod(rs.getLong(i++));
				dim.setDimcodigo(rs.getLong(i++));
				dim.setDimnombre(rs.getString(i++));
				dim.setDimorden(rs.getLong(i++));
				dim.setDimabrev(rs.getString(i++));
				// dim.setDimvigencia(rs.getLong(i++));
				l.add(dim);
			}
			// System.out.println("l " + l.size());
			/*
			 * 
			 * DimensionesVO dim = new DimensionesVO(); dim.setDimcodigo(1);
			 * dim.setDimnombre("Corporal"); dim.setDimorden(1);
			 * dim.setDimabrev("Corporal"); l.add(dim);
			 * 
			 * 
			 * dim = new DimensionesVO(); dim.setDimcodigo(2);
			 * dim.setDimnombre("Cognitiva"); dim.setDimorden(2);
			 * dim.setDimabrev("Cognitiva"); l.add(dim);
			 * 
			 * 
			 * dim = new DimensionesVO(); dim.setDimcodigo(3);
			 * dim.setDimnombre("Comunicativa"); dim.setDimorden(3);
			 * dim.setDimabrev("Comunicativa"); l.add(dim);
			 * 
			 * 
			 * dim = new DimensionesVO(); dim.setDimcodigo(4);
			 * dim.setDimnombre("ntica"); dim.setDimorden(4);
			 * dim.setDimabrev("ntica"); l.add(dim);
			 * 
			 * dim = new DimensionesVO(); dim.setDimcodigo(5);
			 * dim.setDimnombre("Estntica"); dim.setDimorden(5);
			 * dim.setDimabrev("Estntica"); l.add(dim);
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
		return l;
	}

	/**
	 * @function:
	 * @param codGrado
	 * @return
	 * @throws Exception
	 */
	public boolean isGradoPreecolar(String codGrado) throws Exception {
		// System.out.println("isGradoPreecolar");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("isGradoPreecolar"));
			posicion = 1;
			ps.setLong(posicion++, 1);// NIVEL PREECOLAR
			// System.out.println("codGrado " + codGrado);
			ps.setLong(posicion++, Long.parseLong(codGrado));// codGrado
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException sqle) {
			System.out.println("Error sql " + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}

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
		return false;
	}

}
