package siges.importar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.importar.beans.FiltroBeanEvaluacion;
import siges.login.beans.Login;
import siges.plantilla.beans.DimensionesVO;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.Logros;
import siges.plantilla.beans.NivelEvalVO;
import siges.plantilla.beans.ParamsVO;
import siges.plantilla.beans.TipoEvalVO;

/**
 * siges.importar.dao<br>
 * Funcinn: Clase de acceso a datos que obtiene los datos necesarios para
 * validar la informacion de las plantillas a importar asi como la accion de
 * importar las listas de evaluacion y de bateria <br>
 */
public class ImportarDAO extends Dao {
	public String sentencia;
	public String buscar;
	private ResourceBundle rb;
	private java.text.SimpleDateFormat sdf;
	public int[] resultado;
	public int[] resultado2;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * @param cur
	 */
	public ImportarDAO(Cursor cur) {
		super(cur);
		sentencia = null;
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("siges.importar.bundle.importar");
		resultado = new int[3];
		resultado2 = new int[3];
	}

	/**
	 * @param logros
	 * @return<br> Return Type: String[][]<br>
	 *             Version 1.1.<br>
	 */
	public String[][] getLogrosActuales(Logros logros) {
		// System.out.println("getLogrosActuales");
		String resul[][] = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long jerarquia = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// logro.ListaInstitucion.0

			long codInst = Long.parseLong(logros.getPlantillaInstitucion());
			long codMetod = Long.parseLong(logros.getPlantillaMetodologia());
			long codGrad = Long.parseLong(logros.getPlantillaGrado());
			long codAsig = Long.parseLong(logros.getPlantillaAsignatura());
			long vigInst = getVigenciaInst(codInst);

			pst = cn.prepareStatement(rb.getString("logro.ListaInstitucion.0"));
			pst.setLong(posicion++, codInst);
			pst.setLong(posicion++, codMetod);
			pst.setLong(posicion++, codGrad);
			pst.setLong(posicion++, codAsig);
			pst.setLong(posicion++, vigInst);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerarquia = rs.getLong(1);
			}
			rs.close();
			pst.close();
			if (logros.getEstadoPlanEstudios() == 0) {
				pst = cn.prepareStatement(rb
						.getString("logro.ListaInstitucion"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, jerarquia);
				pst.setLong(posicion++, vigInst);
			} else {
				pst = cn.prepareStatement(rb
						.getString("logro.ListaInstitucion.docente"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, jerarquia);
				pst.setLong(posicion++, vigInst);
				pst.setLong(posicion++, logros.getPlantillaDocente());
			}
			rs = pst.executeQuery();
			Collection list = getCollection(rs);
			if (list != null && !list.isEmpty()) {
				return getFiltroMatriz(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener logros actuales. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return resul;
	}

	public boolean isCodigo(Collection a) {
		boolean band = true;
		String[] m = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (a != null && !a.isEmpty()) {
				m = new String[a.size()];
				String z;
				int i = 0;
				Iterator iterator = a.iterator();
				while (iterator.hasNext()) {
					z = (String) iterator.next();
					m[i++] = z;
				}
			}
			if (m != null) {
				cn = cursor.getConnection();
				cn.setAutoCommit(false);
				String con = rb.getString("getCodigosErrados");
				for (int j = 0; j < m.length; j++) {
					if (j != 0)
						con += " " + rb.getString("getCodigosErrados.1");
					con += " " + rb.getString("getCodigosErrados.2");
				}
				// System.out.println(con);
				pst = cn.prepareStatement(con);
				pst.clearParameters();
				long cod = 0;
				for (int j = 0; j < m.length; j++) {
					cod = Long.parseLong(m[j]);
					pst.setLong(posicion++, cod);
					pst.setLong(posicion++, cod);
				}
				rs = pst.executeQuery();
				if (!rs.next())
					return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Codigos errados. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return band;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: String[][]<br>
	 *             Version 1.1.<br>
	 */
	public String[][] getEstudiantes(FiltroPlantilla filtro) {
		String resul[][] = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("listaEstudianteLogro1"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			rs = pst.executeQuery();
			Collection list = getCollection(rs);
			if (list != null && !list.isEmpty()) {
				return getFiltroMatriz(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estudidntes. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return resul;
	}

	public String[] setCodigosDuplicados(String[] est) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			if (est != null) {
				pst = cn.prepareStatement(rb.getString("isCodigosErrado"));
				for (int i = 0; i < est.length; i++) {
					pst.clearParameters();
					pst.setLong(1, Long.parseLong(est[i]));
					rs = pst.executeQuery();
					if (rs.next()) {
						est[i] = rs.getString(1);
					}
					rs.close();
				}
				pst.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			setMensaje("Error intentando obtener Codigos . Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return est;
	}

	/**
	 * @param tipo
	 * @return<br> Return Type: String[][]<br>
	 *             Version 1.1.<br>
	 */
	public String[][] getEscala(int tipo) {
		String resul[][] = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("listaEscalaInstitucion"));
			pst.clearParameters();
			pst.setLong(posicion++, (tipo));
			rs = pst.executeQuery();
			Collection list = getCollection(rs);

			if (list != null && !list.isEmpty()) {
				return getFiltroMatriz(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Escala. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return resul;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: String[][]<br>
	 *             Version 1.1.<br>
	 */
	public String[][] getDescriptores(FiltroPlantilla filtro) {
		String resul[][] = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long jer = 0;
		try {
			int vigencia = (int) getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));

			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb
					.getString("descriptor.ListaInstitucion.0"));

			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			pst.setInt(posicion++, Integer.parseInt(filtro.getMetodologia()));
			pst.setInt(posicion++, Integer.parseInt(filtro.getGrado()));
			pst.setInt(posicion++, Integer.parseInt(filtro.getArea()));
			pst.setInt(posicion++, vigencia);
			rs = pst.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			rs.close();
			pst.close();
			if (filtro.getFilPlanEstudios() == 0) {
				pst = cn.prepareStatement(rb
						.getString("descriptor.ListaInstitucion2"));
			} else {
				pst = cn.prepareStatement(rb
						.getString("descriptor.ListaInstitucion2.docente"));
			}
			posicion = 1;
			pst.setLong(posicion++, jer);
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setLong(posicion++, vigencia);
			if (filtro.getFilPlanEstudios() == 1)
				pst.setLong(posicion++, Long.parseLong(filtro.getDocente()));
			rs = pst.executeQuery();
			Collection list = getCollection(rs);
			if (list != null && !list.isEmpty()) {
				return getFiltroMatriz(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Descriptores. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return resul;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean getCierreAsignatura(FiltroPlantilla filtro) {
		int tipo = 2;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String estado = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ filtro.getPeriodo()));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getJerarquiagrupo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getAsignatura()));
			pst.setLong(posicion++, tipo);
			pst.setLong(posicion++, vigencia);
			rs = pst.executeQuery();
			if (rs.next()) {
				estado = rs.getString(1);
			}
			if (estado == null)
				return true;
			if (estado.equals("1"))
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo cierre de Asignatura. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean getValidacionHorarioAsig(FiltroPlantilla filtro,
			String usuario, String perfil) {
		int tipo = 2;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String estado = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			long inst = Long.parseLong(filtro.getInstitucion());
			long usu = Long.parseLong(usuario);
			long perf = Long.parseLong(perfil);
			long asig = Long.parseLong(filtro.getAsignatura());
			cn = cursor.getConnection();
			// validar si metodologia es libre o no
			String libres = null;
			ps = cn.prepareStatement(rb.getString("HorarioLibre"));
			rs = ps.executeQuery();
			if (rs.next()) {
				libres = rs.getString(1);
			}
			rs.close();
			ps.close();
			if (libres != null) {
				if (libres.indexOf(",") != -1) {
					String[] lib = libres.split(",");
					if (lib != null) {
						for (int r = 0; r < lib.length; r++) {
							if (lib[r].equals(filtro.getMetodologia())) {
								return true;
							}
						}
					}
				} else {
					if (libres.equals(filtro.getMetodologia())) {
						return true;
					}
				}
			}
			// validacion de seguridad de horarios habilitada
			long nn;
			ps = cn.prepareStatement(rb.getString("EstadoLectura"));
			rs = ps.executeQuery();
			if (rs.next())
				return true;
			rs.close();
			ps.close();
			//
			// Permisos de Perfil
			ps = cn.prepareStatement(rb.getString("estadoPerfil"));
			posicion = 1;
			ps.setLong(posicion++, (perf));
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			// validacion contra la tabla
			ps = cn.prepareStatement(rb.getString("estadoLecturaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, (inst));
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			//Mcuellar: SE adiciona la validacion que el usuario que importa debe ser el mismo que llega en el excel (Cuando el ultimo no se nulo)
			System.out.println("filtro.getDocente(): " + filtro.getDocente());
			String docente = (filtro.getDocente() == null || filtro.getDocente().equals("")) ? null : filtro.getDocente();
			System.out.println("docente: " + docente);
			if(docente == null || (docente != null && (docente.equals(usuario)))){
				// validacion contra horarios
				ps = cn.prepareStatement(rb.getString("horario"));
				posicion = 1;
				ps.setLong(posicion++,
						Long.parseLong("0" + filtro.getJerarquiagrupo()));
				ps.setLong(posicion++, usu);
				ps.setLong(posicion++, asig);
				ps.setString(posicion++, filtro.getInstitucion());
	//			ps.setLong(posicion++, (asig));
	//			ps.setLong(posicion++, usu);
	//			ps.setLong(posicion++, (asig));
	//			ps.setLong(posicion++, usu);
	//			ps.setLong(posicion++, (asig));
	//			ps.setLong(posicion++, usu);
	//			ps.setLong(posicion++, (asig));
	//			ps.setLong(posicion++, usu);
	//			ps.setLong(posicion++, (asig));
	//			ps.setLong(posicion++, usu);
	//			ps.setLong(posicion++, (asig));
	//			ps.setLong(posicion++, usu);
	//			ps.setLong(posicion++, (asig));
				rs = ps.executeQuery();
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo cierre de Asignatura. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean getValidacionHorarioArea(FiltroPlantilla filtro,
			String usuario, String perfil) {
		int tipo = 2;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String estado = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			long inst = Long.parseLong(filtro.getInstitucion());
			long usu = Long.parseLong(usuario);
			long perf = Long.parseLong(perfil);
			long area = Long.parseLong(filtro.getArea());
			cn = cursor.getConnection();
			// validar si metodologia es libre o no
			String libres = null;
			ps = cn.prepareStatement(rb.getString("HorarioLibre"));
			rs = ps.executeQuery();
			if (rs.next()) {
				libres = rs.getString(1);
			}
			rs.close();
			ps.close();
			if (libres != null) {
				if (libres.indexOf(",") != -1) {
					String[] lib = libres.split(",");
					if (lib != null) {
						for (int r = 0; r < lib.length; r++) {
							if (lib[r].equals(filtro.getMetodologia())) {
								return true;
							}
						}
					}
				} else {
					if (libres.equals(filtro.getMetodologia())) {
						return true;
					}
				}
			}
			// validacion de seguridad de horarios habilitada
			long nn;
			ps = cn.prepareStatement(rb.getString("EstadoLectura"));
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			// Permisos de Perfil
			ps = cn.prepareStatement(rb.getString("estadoPerfil"));
			posicion = 1;
			ps.setLong(posicion++, perf);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			// validacion contra la tabla
			ps = cn.prepareStatement(rb.getString("estadoLecturaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			// validacion contra horarios
			ps = cn.prepareStatement(rb.getString("horario2"));
			posicion = 1;
			ps.setLong(posicion++,
					Long.parseLong("0" + filtro.getJerarquiagrupo()));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo cierre de Asignatura. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean getValidacionHorarioPree(FiltroPlantilla filtro,
			String usuario, String perfil) {
		int tipo = 2;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// long vigencia=
			// getVigenciaInst(Long.parseLong(filtro.getInstitucion()));
			long inst = Long.parseLong(filtro.getInstitucion());
			long usu = Long.parseLong(usuario);
			long perf = Long.parseLong(perfil);
			long area = Long.parseLong(filtro.getArea());
			cn = cursor.getConnection();
			// validar si metodologia es libre o no
			String libres = null;
			ps = cn.prepareStatement(rb.getString("HorarioLibre"));
			rs = ps.executeQuery();
			if (rs.next()) {
				libres = rs.getString(1);
			}
			rs.close();
			ps.close();
			if (libres != null) {
				if (libres.indexOf(",") != -1) {
					String[] lib = libres.split(",");
					if (lib != null) {
						for (int r = 0; r < lib.length; r++) {
							if (lib[r].equals(filtro.getMetodologia())) {
								return true;
							}
						}
					}
				} else {
					if (libres.equals(filtro.getMetodologia())) {
						return true;
					}
				}
			}
			// validacion de seguridad de horarios habilitada
			long nn;
			ps = cn.prepareStatement(rb.getString("EstadoLectura"));
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			//
			// Permisos de Perfil APLICA PARA LODE ERMISO 1 OSEA como ing de
			// soporte o coordinador de notas
			ps = cn.prepareStatement(rb.getString("estadoPerfil"));
			posicion = 1;
			ps.setLong(posicion++, perf);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			// validacion contra la tabla
			ps = cn.prepareStatement(rb.getString("estadoLecturaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
			// /validacion como tal de horarios
			ps = cn.prepareStatement(rb.getString("horario2"));
			posicion = 1;
			ps.setLong(posicion++,
					Long.parseLong("0" + filtro.getJerarquiagrupo()));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			ps.setLong(posicion++, usu);
			ps.setLong(posicion++, (area));
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo cierre de Asignatura. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean getCierreArea(FiltroPlantilla filtro) {
		int tipo = 1;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String estado = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ExisteGrupoCerrado"
					+ filtro.getPeriodo()));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getJerarquiagrupo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getArea()));
			pst.setLong(posicion++, tipo);
			pst.setLong(posicion++, vigencia);
			rs = pst.executeQuery();
			if (rs.next()) {
				estado = rs.getString(1);
			}
			if (estado == null)
				return true;
			if (estado.equals("1"))
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo cierre de area. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean getCierrePreescolar(FiltroPlantilla filtro) {
		int tipo = 1;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb
					.getString("ExisteGrupoCerradoPreescolar"
							+ filtro.getPeriodo()));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(filtro.getJerarquiagrupo()));
			pst.setLong(posicion++, tipo);
			pst.setLong(posicion++, vigencia);
			rs = pst.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo cierre de Preescolar. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: String[][]<br>
	 *             Version 1.1.<br>
	 */
	public String[][] getLogros(FiltroPlantilla filtro) {
		String resul[][] = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long jer = 0;
		try {
			int vigencia = (int) getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("listaLogroAsignatura.0"));
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			pst.setInt(posicion++, Integer.parseInt(filtro.getMetodologia()));
			pst.setInt(posicion++, Integer.parseInt(filtro.getGrado()));
			pst.setInt(posicion++, Integer.parseInt(filtro.getAsignatura()));
			pst.setInt(posicion++, vigencia);
			rs = pst.executeQuery();
			if (rs.next()) {
				jer = rs.getLong(1);
			}
			rs.close();
			pst.close();
			if (filtro.getFilPlanEstudios() == 0)
				pst = cn.prepareStatement(rb.getString("listaLogroAsignatura"));
			else
				pst = cn.prepareStatement(rb
						.getString("listaLogroAsignatura.docente"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, jer);
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			pst.setInt(posicion++, vigencia);
			if (filtro.getFilPlanEstudios() == 1)
				pst.setLong(posicion++, Long.parseLong(filtro.getDocente()));
			rs = pst.executeQuery();
			Collection list = getCollection(rs);
			if (list != null && !list.isEmpty()) {
				return getFiltroMatriz(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Logros. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return resul;
	}

	/**
	 * @param logros
	 * @return<br> Return Type: String[][]<br>
	 *             Version 1.1.<br>
	 */
	public String[][] getDescriptoresActuales(Logros logros) {
		// System.out.println("getDescriptoresActuales " );
		String resul[][] = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long jerarquia = 0;
		try {

			// g_jerinst=? AND G_JERMETOD=? AND g_jergrado=? AND G_JERAREAASIG=?
			// AND G_JERvigencia=?
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb
					.getString("descriptor.ListaInstitucion.0"));
			pst.clearParameters();
			pst.setLong(posicion++,
					Long.parseLong(logros.getPlantillaInstitucion()));
			// System.out.println("logros.getPlantillaInstitucion() " +
			// logros.getPlantillaInstitucion());

			pst.setLong(posicion++,
					Long.parseLong(logros.getPlantillaMetodologia()));
			// System.out.println("logros.getPlantillaMetodologia()" +
			// logros.getPlantillaMetodologia());

			pst.setLong(posicion++, Long.parseLong(logros.getPlantillaGrado()));
			// System.out.println("logros.getPlantillaGrado()" +
			// logros.getPlantillaGrado());

			pst.setLong(posicion++,
					Long.parseLong(logros.getPlantillaAsignatura()));
			// / System.out.println("logros.getPlantillaAsignatura() " +
			// logros.getPlantillaAsignatura());

			pst.setLong(posicion++, getVigenciaInst(Long.parseLong(logros
					.getPlantillaInstitucion())));
			rs = pst.executeQuery();
			if (rs.next()) {
				jerarquia = rs.getLong(1);
			}
			rs.close();
			pst.close();
			// System.out.println("jerarquia " + jerarquia);
			// System.out.println("logros.getEstadoPlanEstudios() "
			// + logros.getEstadoPlanEstudios());
			// System.out.println("etVigenciaNumerico() "
			// + getVigenciaInst(Long.parseLong(logros
			// .getPlantillaInstitucion())));
			if (logros.getEstadoPlanEstudios() == 0) {
				pst = cn.prepareStatement(rb
						.getString("descriptor.ListaInstitucion"));
				posicion = 1;
				pst.setLong(posicion++, jerarquia);
				pst.setLong(posicion++, getVigenciaInst(Long.parseLong(logros
						.getPlantillaInstitucion())));
			} else {
				pst = cn.prepareStatement(rb
						.getString("descriptor.ListaInstitucion.docente"));
				posicion = 1;
				pst.setLong(posicion++, jerarquia);
				pst.setLong(posicion++, getVigenciaInst(Long.parseLong(logros
						.getPlantillaInstitucion())));
				pst.setLong(posicion++, logros.getPlantillaDocente());
			}
			rs = pst.executeQuery();
			Collection list = getCollection(rs);
			// System.out.println("list " + list.size());
			if (list != null && !list.isEmpty()) {
				return getFiltroMatriz(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener descriptores actuales. Posible problema: "
					+ e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return resul;
	}

	/**
	 * @param filtro
	 * @param fort
	 * @param difi
	 * @param reco
	 * @param est
	 * @param actual
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean importarBateriaDescriptor(Logros filtro, String[][] fort,
			String[][] difi, String[][] reco, String[][] est, String[][] actual) {
		int posicion = 1;
		Connection cn = null;
		// PreparedStatement pst=null,pst2=null;
		PreparedStatement ps = null;
		resultado = new int[4];
		// Collection list;
		// Object[] o=new Object[2];
		// Integer entero=new Integer(java.sql.Types.BIGINT);
		int i, k;
		String[][] not = null;
		long jerar = 0;
		ResultSet rs = null;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getPlantillaInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("descriptor.Jerarquia"));
			ps.setLong(posicion++,
					Long.parseLong(filtro.getPlantillaInstitucion()));
			ps.setLong(posicion++, Long.parseLong(filtro.getPlantillaGrado()));
			ps.setLong(posicion++,
					Long.parseLong(filtro.getPlantillaAsignatura()));
			ps.setLong(posicion++,
					Long.parseLong(filtro.getPlantillaMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jerar = rs.getLong(1);
			}
			rs.close();
			ps.close();
			// LOGCODJERAR, LOGCODIGO, LOGCODPERINICIAL, LOGCODPERFINAL,
			// LOGNOMBRE, LOGABREV, LOGDESCRIPCION
			resultado[0] = ((fort != null ? fort.length : 0)
					+ (difi != null ? difi.length : 0)
					+ (reco != null ? reco.length : 0) + (est != null ? est.length
					: 0));// cantidad de logros evaluados
			// System.out.println("lista total de notas="+(not!=null?not.length:0)+"");
			// System.out.println("lista de registros que ya estan="+(actual!=null?actual.length:0)+"");
			// System.out.println("lista de notas a actualizar(no borrar)="+(c!=null?c.length:0)+"");
			String codigologro = null;
			String perIni = null, perFin = null, nom = null, abrev = null, desc = null, orden = null;
			String perIniBD = null, perFinBD = null, nomBD = null, abrevBD = null, descBD = null, tipoBD = null, ordenBD = null;
			// int cont=0;
			int ins = 0;
			/* para fortaleza */
			for (int s = 1; s < 5; s++) {
				switch (s) {
				case 1:
					not = fort;
					break;
				case 2:
					not = difi;
					break;
				case 3:
					not = reco;
					break;
				case 4:
					not = est;
					break;
				}
				if (not != null) {
					for (i = 0; i < not.length; i++) {// iterar por cada logro
						codigologro = null;
						ins = 0;
						if (filtro.getEstadoPlanEstudios() == 0) {
							ps = cn.prepareStatement(rb
									.getString("descriptor.Insertar"));
						} else {
							ps = cn.prepareStatement(rb
									.getString("descriptor.Insertar.docente"));
						}
						if (not[i][0] != null) {
							perIni = not[i][0];
							perFin = not[i][1];
							nom = not[i][2];
							abrev = not[i][3] != null ? not[i][3].replace(',',
									'.') : "";
							desc = not[i][4] != null ? not[i][4] : "";
							orden = not[i][5] != null ? not[i][5] : "0";
							// System.out.println("actual.length " +
							// actual.length);
							if (actual != null) {
								for (k = 0; k < actual.length; k++) {
									perIniBD = actual[k][2];
									perFinBD = actual[k][3];
									nomBD = actual[k][4];
									abrevBD = actual[k][5];
									descBD = actual[k][6] != null ? actual[k][6]
											: "";
									tipoBD = actual[k][7];
									ordenBD = actual[k][8];
									// System.out.println("ACTUALIZA DESCRIPTORE ordenBD ---fffffffffffffffffffffffff----------------------------"+
									// ordenBD);

									// System.out.println(perIni + "=" +
									// perIniBD
									// + "_" + perFin + "=" + perFinBD
									// + "_" + nomBD + "=" + nom + "_"
									// + tipoBD + "=" + s);
									if (perIni.equals(perIniBD)
											&& perFin.equals(perFinBD)
											&& nom.equals(nomBD)
											&& tipoBD.equals("" + s)) {
										if (!abrev.equals(abrevBD)
												|| !desc.equals(descBD)
												|| !orden.equals(ordenBD)) {
											resultado[1] += 1;
											// System.out.println(" ACTUALIZAR  ");
											codigologro = actual[k][1];
											if (filtro.getEstadoPlanEstudios() == 0) {
												ps = cn.prepareStatement(rb
														.getString("descriptor.Actualizar"));
											} else {
												ps = cn.prepareStatement(rb
														.getString("descriptor.Actualizar.docente"));
											}
											ins = 1;
										} else {
											// System.out.println(" LO DEJA COMO ESTA ");
											ps = null;
										}
										break;
									}
								}
							}
						}
						if (ps != null && not[i][0] != null
								&& !not[i][0].equals("")) {
							if (ins == 0) {
								// System.out.println(" INSERTAR  ");
								resultado[2] += 1;
							}
							ps.clearParameters();
							posicion = 1;
							ps.setLong(posicion++, Long.parseLong(perIni));
							ps.setLong(posicion++, Long.parseLong(perFin));
							ps.setString(posicion++, nom);
							ps.setString(posicion++, abrev);
							if (desc == null || desc.equals(""))
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// LOGDESCRIPCION,
							else
								ps.setString(posicion++, desc);
							ps.setLong(
									posicion++,
									Long.parseLong(orden) > 99 ? 99 : Long
											.parseLong(orden));

							ps.setInt(posicion++, s);
							if (jerar == 0)
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// LOGCODJERAR
							else
								ps.setLong(posicion++, (jerar));
							if (ins == 1) {
								if (codigologro == null
										|| codigologro.equals(""))
									ps.setNull(posicion++,
											java.sql.Types.VARCHAR);
								else
									ps.setLong(posicion++,
											Long.parseLong(codigologro.trim()));
							}
							ps.setLong(posicion++, vigencia);
							if (filtro.getEstadoPlanEstudios() == 1) {
								ps.setLong(posicion++,
										filtro.getPlantillaDocente());
							}
							int r = ps.executeUpdate();
							ps.close();
							// System.out.println((ins==0?"insertando":"actualizando")+"= CANTIDAD ="+r);
						}
					}
				}
			}
			// System.out.println("\n----------------");
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar bateria de descriptores Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar bateria de descriptores Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @function: Funcinn que limpia, actualiza e inserta las notas y ausencia
	 *            de los estudiantes que deacuerdo a los datos del archivo de
	 *            Excel.
	 * @param filtro
	 * @param estu
	 * @param not
	 * @return
	 */
	public boolean importarEvalPreescolar(FiltroPlantilla filtro,
			String[] estu, String[][] not, int cantDim) {
		Connection cn = null;
		resultado = new int[4];
		int posicion = 1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			long jerGrupo = Long.parseLong(filtro.getJerarquiagrupo());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			resultado[0] = (not != null ? not.length : 0);

			/**
			 * (1) Actualizar todo los registros a NULL segun el periodo (COMO
			 * NUESTRO DELETE)
			 * ***************************************************
			 * */
			for (int r = 0; r < not.length; r++) {
				String consulta = rb
						.getString("EvaluacionPreescolar.notas.updateNULL"
								+ filtro.getPeriodo());
				posicion = 1;
				ps = cn.prepareStatement(consulta);
				ps.setLong(posicion++, vigencia);
				ps.setLong(posicion++, Long.parseLong(estu[r].trim()));
				ps.setLong(posicion++, jerGrupo);
				int update = ps.executeUpdate();
				ps.close();
			}

			/**
			 * (2) Eliminar los registro basura que estan en NULL
			 * ***************************************************
			 * */
			for (int r = 0; r < not.length; r++) {
				posicion = 1;
				ps = cn.prepareStatement(rb
						.getString("EvaluacionPreescolar.notas.DeleteNULL"));
				ps.setLong(posicion++, vigencia);
				ps.setLong(posicion++, jerGrupo);
				ps.setLong(posicion++, Long.parseLong(estu[r].trim()));

				int nunEliminado = ps.executeUpdate();

				ps.close();

				if (nunEliminado > 0) {
					resultado[3] += nunEliminado;
				}
			}

			// System.out.println("not.lengt " + not.length);
			// System.out.println("cantDim " + cantDim);

			/**
			 * (3) FOR para update or insert
			 * ***************************************************
			 * */
			for (int l = 0; l < cantDim; l++) { // DIMENSION
				for (i = 0; i < not.length; i++) {// notas

					// System.out.println("not[i][l+cantDim] "
					// + not[i][l + cantDim]);
					if (not[i][l + cantDim] != null) { // NOTAS DIMENSION

						// 1.1. Actualizar con nueva nota
						int band = 0;

						ps = cn.prepareStatement(rb
								.getString("EvaluacionPreescolar.actualizar"
										+ filtro.getPeriodo()));
						// System.out.println("sql " +
						// rb.getString("EvaluacionPreescolar.actualizar"+filtro.getPeriodo()));
						// System.out.println("not[i][l+5].trim( " +
						// not[i][l+5].trim());
						posicion = 1;

						if (not[i][l] != null) {
							ps.setString(posicion++, not[i][l].trim());
						} else {
							ps.setNull(posicion++, Types.VARCHAR);
						}

						ps.setString(posicion++, not[i][l + cantDim].trim());
						ps.setLong(posicion++, jerGrupo);
						ps.setLong(posicion++, Long.parseLong(estu[i].trim()));
						ps.setLong(posicion++, l + 1);
						ps.setLong(posicion++, vigencia);

						band = ps.executeUpdate();
						ps.close();

						// System.out.println("band " + band);
						// 1.1. Actualizar con nueva nota
						if (band == 0) {
							ps = cn.prepareStatement(rb
									.getString("EvaluacionPreescolar.insertar"
											+ filtro.getPeriodo()));
							posicion = 1;
							if (not[i][l] != null) {
								ps.setString(posicion++, not[i][l].trim());
							} else {
								ps.setNull(posicion++, Types.VARCHAR);
							}
							ps.setString(posicion++, not[i][l + cantDim].trim());
							ps.setLong(posicion++, jerGrupo);
							ps.setLong(posicion++,
									Long.parseLong(estu[i].trim()));
							ps.setLong(posicion++, l + 1);
							ps.setLong(posicion++, vigencia);

							band = ps.executeUpdate();
							resultado[2] += 1;
							// System.out.println("inserto "
							// + Long.parseLong(estu[i].trim()));
						} else {
							resultado[1] += 1;
							// System.out.println("actualizo "
							// + Long.parseLong(estu[i].trim()));
						}

					} // if(not[i][l+5]!=null){ //NOTAS DIMENSION

				}// for(i=0;i<not.length;i++){//notas
			}// for(int l=0;l<5;l++){ // DIMENSION

			/**
			 * (2) Eliminar los registro basura que estan en NULL
			 * ***************************************************
			 * */
			for (int r = 0; r < not.length; r++) {
				posicion = 1;
				ps = cn.prepareStatement(rb
						.getString("EvaluacionPreescolar.notas.DeleteNULL"));
				ps.setLong(posicion++, vigencia);
				ps.setLong(posicion++, jerGrupo);
				ps.setLong(posicion++, Long.parseLong(estu[r].trim()));

				int nunEliminado = ps.executeUpdate();

				ps.close();

				if (nunEliminado > 0) {
					resultado[3] += nunEliminado;
				}
			}

			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println(sqle.getMessage());
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Preescolar. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Preescolar a estudiantes. Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @param estu
	 * @param not
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean importarEvalPreescolarAntes(FiltroPlantilla filtro,
			String[] estu, String[][] not) {
		Connection cn = null;
		resultado = new int[4];
		int posicion = 1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int otroPeriodo, periodoVacio;
		int i;
		int j;
		int k;
		String[][] actual;
		// if(not!=null){
		// for(int t=0;t<not.length;t++){
		// System.out.println("Valor de la Nota:"+estu[t]+":"+not[t][5]+"|"+not[t][6]+"|"+not[t][7]+"|"+not[t][8]+"|"+not[t][9]);
		// }
		// }
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			long jerGrupo = Long.parseLong(filtro.getJerarquiagrupo());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			// traer la lista de notas que hay
			String consulta = rb.getString("EvaluacionPreescolar.notas") + " "
					+ rb.getString("EvaluacionPreescolar.notas.1");
			for (int r = 0; r < estu.length; r++) {
				if (r == 0) {
					consulta += " "
							+ rb.getString("EvaluacionPreescolar.notas.2");
				} else {
					consulta += " "
							+ rb.getString("EvaluacionPreescolar.notas.3");
				}
			}
			consulta += " " + rb.getString("EvaluacionPreescolar.notas.4");
			ps = cn.prepareStatement(consulta);
			// EVAPRECODESTU,EVAPRECODDIM,EVAPREEVALU1,EVAPREEVALU2,EVAPREEVALU3,EVAPREEVALU4,EVAPREEVALU5
			ps.setLong(posicion++, jerGrupo);
			ps.setLong(posicion++, vigencia);

			for (int r = 0; r < estu.length; r++) {
				ps.setString(posicion++, estu[r]);
			}

			rs = ps.executeQuery();
			actual = getFiltroMatriz(getCollection(rs));

			rs.close();
			ps.close();

			resultado[0] = (not != null ? not.length : 0);
			// System.out.println("lista total de notas="+(not!=null?not.length:0)+"");
			// System.out.println("lista de registros que ya estan="+(actual!=null?actual.length:0)+"");
			String c[][] = new String[actual != null ? actual.length : 1][actual != null ? actual[0].length
					: 1];
			// System.out.println("lista de notas a actualizar(no borrar)="
			// + (c != null ? c.length : 0) + "");

			int cont = 0;
			int ins = 0;
			if (not != null) {
				String codBD;
				String dimBD;
				String cod;
				String dim;
				for (i = 0; i < not.length; i++) {// iterar por estu-nota

					cod = estu[i];
					ins = 0;
					// ps=cn.prepareStatement(rb.getString("EvaluacionPreescolar.insertar"+filtro.getPeriodo()));
					for (int l = 0; l < 5; l++) {// 5 porque hay cambia a nota
													// conceptual
						ins = 0;
						dim = String.valueOf(l + 1);
						if (not[i][l + 5] != null) {
							// System.out.println("actual " + actual);
							// System.out.println("plantilla= " + estu[i] +
							// " - "
							// + not[i][l + 5] + "i " + i + " l " + l);
							ps = cn.prepareStatement(rb
									.getString("EvaluacionPreescolar.insertar"
											+ filtro.getPeriodo()));

							if (actual != null) {
								for (k = 0; k < actual.length; k++) {
									ps = cn.prepareStatement(rb
											.getString("EvaluacionPreescolar.insertar"
													+ filtro.getPeriodo()));
									codBD = actual[k][0];
									dimBD = actual[k][1];
									if (cod.equals(codBD) && dim.equals(dimBD)) {
										// System.out.println("encontrado");
										c[cont][0] = codBD;
										c[cont++][1] = dimBD;
										resultado[1] += 1;
										// System.out.println(" ACTUALIZAR ");
										ps = cn.prepareStatement(rb
												.getString("EvaluacionPreescolar.actualizar"
														+ filtro.getPeriodo()));
										ins = 1;
										break;
									}
								}
							}
						}
						// System.out.println("valor de ins="+ins+": "+not[i][l]+": "+(ps!=null?"ps existe":"ps no existe"));
						// if(ps!=null && not[i][l+5]!=null &&
						// !not[i][l+5].equals("")){
						if (ps != null && not[i][l + 5] != null) {
							if (ins == 0) {
								// System.out.println(" INSERTAR  "+not[i][l+5]);
								resultado[2] += 1;
							}
							ps.clearParameters();
							posicion = 1;
							if (not[i][l + 5].equals("")) {
								// System.out.println(" lo pone a null:"+dim+"//"+estu[i]);
								ps.setNull(posicion++, java.sql.Types.VARCHAR);
								ps.setNull(posicion++, java.sql.Types.VARCHAR);
							} else {
								// System.out.println("NO lo pone a null"+dim+"//"+estu[i]);
								if (not[i][l] == null)
									ps.setNull(posicion++,
											java.sql.Types.VARCHAR);
								else
									ps.setString(posicion++, not[i][l].trim());
								ps.setString(posicion++, not[i][l + 5].trim());
							}
							ps.setLong(posicion++, jerGrupo);
							ps.setLong(posicion++,
									Long.parseLong(estu[i].trim()));
							ps.setLong(posicion++, (l + 1));// EVAPRECODDIM
							ps.setLong(posicion++, vigencia);
							ps.executeUpdate();
							ps.close();
							// System.out.println((ins==0?"insertando":"actualizando")+"= CANTIDAD ="+r);
						}
					}
				}
			}
			// System.out.println("\n--REVISAR ELIMINADOS");
			if (actual != null) {
				int band = -1;
				for (i = 0; i < actual.length; i++) {
					band = 1;
					for (j = 0; j < (c != null ? c.length : 0); j++) {
						if (c[j][0] != null) {
							if (c[j][0].equals(actual[i][0])
									&& c[j][1].equals(actual[i][1])) {
								band = 2;
								break;
							}
						}
					}
					if (band == 1) {
						otroPeriodo = 0;
						periodoVacio = 0;
						int perAbrev = Integer.parseInt(filtro.getPeriodo());
						for (int periodos = 2; periodos < actual[i].length; periodos++) {
							if ((periodos - 1) != perAbrev
									&& actual[i][periodos] != null
									&& !actual[i][periodos].equals(""))
								otroPeriodo++;
							if ((periodos - 1) == perAbrev
									&& (actual[i][periodos] == null || actual[i][periodos]
											.equals("")))
								periodoVacio = 1;
						}
						if (otroPeriodo > 0) {// actualizar a vacio
							if (periodoVacio == 0) {
								resultado[3] += 1;
								// System.out.println("ACTUALIZAR A VACIO="+actual[i][0]);
								ps = cn.prepareStatement(rb
										.getString("EvaluacionPreescolar.actualizar"
												+ filtro.getPeriodo()));
								ps.clearParameters();
								posicion = 1;
								ps.setNull(posicion++, java.sql.Types.VARCHAR);
								ps.setNull(posicion++, java.sql.Types.VARCHAR);
								ps.setLong(posicion++, jerGrupo);// jerarquia
								ps.setLong(posicion++,
										Long.parseLong(actual[i][0]));// est
								ps.setLong(posicion++,
										Long.parseLong(actual[i][1]));// dim
								ps.setLong(posicion++, vigencia);// vigencia
								ps.executeUpdate();
								ps.close();
							}
						} else {// borrar registro
							resultado[3] += 1;
							// System.out.println("ELIMINAR="+actual[i][0]);
							ps = cn.prepareStatement(rb
									.getString("EvaluacionPreescolar.eliminar"));
							ps.clearParameters();
							posicion = 1;
							ps.setLong(posicion++, Long.parseLong(filtro
									.getJerarquiagrupo().trim()));
							ps.setLong(posicion++, Long.parseLong(actual[i][0]));
							ps.setLong(posicion++, Long.parseLong(actual[i][1]));
							ps.setLong(posicion++, vigencia);
							ps.executeUpdate();
							ps.close();
							// System.out.println(" _ELIMINADO= CANTIDAD:"+r);
						}
					}
				}
			}
			// System.out.println("\n----------------");
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println(sqle.getMessage());
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Preescolar. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Preescolar a estudiantes. Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @param estu
	 * @param motivo
	 * @param escala
	 * @param not
	 * @param log
	 * @param escala2
	 * @param not2
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean importarEvalAsignatura(FiltroPlantilla filtro,
			String[] estu, String motivo[][], String[][] escala,
			String[][] not, String[] log, String[][] escala2, String[][] not2) {
		resultado = new int[4];
		resultado2 = new int[4];
		Connection cn = null;
		// PreparedStatement pst=null,pst2=null,pst3=null;
		long jer = 0;
		long jerGrupo = 0;
		ResultSet rs = null;
		int posicion = 1;
		PreparedStatement ps = null;
		int perAbrev = 0;
		int i, j, k, otro, vacio;
		String[][] actual;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));

			// TRAER JERARQUIA DE ASIG
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (filtro.getJerarquiaLogro() == null
					|| filtro.getJerarquiaLogro().trim().equals("")) {
				// traer jerarquia de asignatura
				ps = cn.prepareStatement(rb.getString("jerarquiaAsignatura"));
				posicion = 1;
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getAsignatura()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, vigencia);
				rs = ps.executeQuery();
				if (rs.next())
					jer = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jer = Long.parseLong(filtro.getJerarquiaLogro().trim());
			}

			if (filtro.getJerarquiagrupo() == null
					|| filtro.getJerarquiagrupo().trim().equals("")) {
				// traer jerarquia de asignatura
				ps = cn.prepareStatement(rb.getString("jerarquiaGrupo"));
				posicion = 1;
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getSede()));
				ps.setLong(posicion++, Long.parseLong(filtro.getJornada()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
				rs = ps.executeQuery();
				if (rs.next())
					jerGrupo = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jerGrupo = Long.parseLong(filtro.getJerarquiagrupo().trim());
			}
			// abreviatura de periodo
			perAbrev = Integer.parseInt(filtro.getPeriodo().trim());

			// Numero de estudiantes evaluados
			resultado[0] = (not != null ? not.length : 0);

			/**
			 * (1) Actualizar todo los registros a NULL (COMO NUESTRO DELETE)
			 * ***************************************************
			 * */

			for (int r = 0; r < not.length; r++) { // EvaluacionPreescolar.notas.updateNULL
													// Asignatura
				String consulta = rb
						.getString("EvaluacionAsignatura.notas.updateNULL"
								+ filtro.getPeriodo());
				posicion = 1;

				ps = cn.prepareStatement(consulta);
				ps.setLong(posicion++, vigencia);
				ps.setLong(posicion++, jer);
				ps.setLong(posicion++, Long.parseLong(estu[r].trim()));
				int ii = ps.executeUpdate();
				ps.close();
			}

			/**
			 * (2) Eliminar los registro basura que estan en NULL
			 * ***************************************************
			 * */
			for (int r = 0; r < not.length; r++) {
				posicion = 1;
				ps = cn.prepareStatement(rb
						.getString("EvaluacionAsignatura.notas.DeleteNULL"));
				ps.setLong(posicion++, vigencia);
				ps.setLong(posicion++, jer);
				ps.setLong(posicion++, Long.parseLong(estu[r].trim()));

				int nunEliminado = ps.executeUpdate();

				ps.close();

				if (nunEliminado > 0) {
					resultado[3] += nunEliminado;
				}
			}

			/**
			 * (3) FOR para update or insert
			 * ***************************************************
			 * */
			for (i = 0; i < not.length; i++) {// notas
				if (not[i][0] != null) { // NOTAS DIMENSION

					// 1.1. Actualizar con nueva nota
					int band = 0;

					ps = cn.prepareStatement(rb
							.getString("plantilla.actualizarEvaluacionAsignatura"
									+ filtro.getPeriodo()));
					// System.out.println("sql " +
					// rb.getString("EvaluacionPreescolar.actualizar"+filtro.getPeriodo()));
					// System.out.println("not[i][l+5].trim( " +
					// not[i][l+5].trim());
					posicion = 1;

					ps.setDouble(posicion++,
							Double.parseDouble(not[i][0].trim()));
					if (GenericValidator.isDouble(not[i][2])) {
						ps.setDouble(posicion++,
								Double.parseDouble(not[i][2].trim()));
					} else {
						ps.setNull(posicion++, Types.DOUBLE);
					}

					if (not[i][1] != null) {
						ps.setString(posicion++, not[i][1].trim());
					} else {
						ps.setNull(posicion++, Types.VARCHAR);
					}
					ps.setLong(posicion++, vigencia);
					ps.setLong(posicion++, jer);
					ps.setLong(posicion++, Long.parseLong(estu[i].trim()));

					band = ps.executeUpdate();
					ps.close();

					// System.out.println("band " + band);
					// 1.1. Actualizar con nueva nota
					if (band == 0) {
						ps = cn.prepareStatement(rb
								.getString("plantilla.insertarEvaluacionAsignatura"
										+ filtro.getPeriodo()));
						posicion = 1;
						// ps.setString(posicion++,not[i][0].trim());
						ps.setDouble(posicion++,
								Double.parseDouble(not[i][0].trim()));

						if (GenericValidator.isDouble(not[i][2])) {
							ps.setDouble(posicion++,
									Double.parseDouble(not[i][2].trim()));
						} else {
							ps.setNull(posicion++, Types.DOUBLE);
						}

						if (not[i][1] != null) {
							ps.setString(posicion++, not[i][1].trim());
						} else {
							ps.setNull(posicion++, Types.VARCHAR);
						}
						ps.setLong(posicion++, vigencia);
						ps.setLong(posicion++, jer);
						ps.setLong(posicion++, Long.parseLong(estu[i].trim()));

						ps.executeUpdate();
						ps.close();
						resultado[2] += 1;

					} else {
						resultado[1] += 1;
					}

				} // if(not[i][l+5]!=null){ //NOTAS DIMENSION
			}// for(i=0;i<not.length;i++){//notas

			String consulta = "";
			consulta = rb.getString("EvaluacionLogroEliminarTodos") + " "
					+ rb.getString("EvaluacionLogroEliminarTodos.1");
			for (int r = 0; r < estu.length; r++) {
				if (r == 0) {
					consulta += " "
							+ rb.getString("EvaluacionLogroEliminarTodos.2");
				} else {
					consulta += " "
							+ rb.getString("EvaluacionLogroEliminarTodos.3");
				}
			}
			consulta += " " + rb.getString("EvaluacionLogroEliminarTodos.4");
			if (filtro.getFilPlanEstudios() == 1) {
				consulta += " "
						+ rb.getString("EvaluacionLogroEliminarTodos.5");
			}
			ps = cn.prepareStatement(consulta);
			posicion = 1;
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, perAbrev);
			ps.setLong(posicion++, vigencia);
			ps.setLong(posicion++, jerGrupo);
			for (int r = 0; r < estu.length; r++) {
				ps.setString(posicion++, estu[r]);
			}
			if (filtro.getFilPlanEstudios() == 1) {
				ps.setLong(posicion++, Long.parseLong(filtro.getDocente()));
			}
			ps.executeUpdate();
			ps.close();
			int cont = 0;
			int ins = 0;
			if (not2 != null) {
				resultado2[0] = (not2.length);
				for (i = 0; i < not2.length; i++) {// iterar por estu-nota
					if (not2[i] != null && not2[i].length > 0) {

						for (j = 0; j < not2[i].length; j++) {// iterar por
																// logro
							ins = 0;
							if (not2[i][j] != null && !not2[i][j].equals("")) {

								resultado2[2] += 1;
								ps = cn.prepareStatement(rb
										.getString("EvaluacionLogroInsertar"));
								ps.clearParameters();
								posicion = 1;
								ps.setLong(posicion++,
										Long.parseLong(not2[i][j].trim()));// EvaLogEvalu
								ps.setLong(posicion++, jer);// EvaLogCodJerar,
								ps.setLong(posicion++,
										Long.parseLong(estu[i].trim()));// EvaLogCodEstu,
								ps.setLong(posicion++,
										Long.parseLong(log[j].trim()));// EvaLogCodLogro,

								ps.setLong(posicion++, perAbrev);// EvaLogCodPerio
								ps.setLong(posicion++, vigencia);// vigencia
								ps.executeUpdate();
								ps.close();
							}
						}
					}
				}
			}
			cn.commit();
			cn.setAutoCommit(true);

		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar asignaturas a estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar asignaturas a estudiantes. Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @param estu
	 * @param motivo
	 * @param escala
	 * @param not
	 * @param log
	 * @param escala2
	 * @param not2
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean importarEvalAsignaturaAntes(FiltroPlantilla filtro,
			String[] estu, String motivo[][], String[][] escala,
			String[][] not, String[] log, String[][] escala2, String[][] not2) {
		resultado = new int[4];
		resultado2 = new int[4];
		Connection cn = null;
		// PreparedStatement pst=null,pst2=null,pst3=null;
		long jer = 0;
		long jerGrupo = 0;
		ResultSet rs = null;
		int posicion = 1;
		PreparedStatement ps = null;
		int perAbrev = 0;
		int i, j, k, otro, vacio;
		String[][] actual;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			// TRAER JERARQUIA DE ASIG
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (filtro.getJerarquiaLogro() == null
					|| filtro.getJerarquiaLogro().trim().equals("")) {
				// traer jerarquia de asignatura
				ps = cn.prepareStatement(rb.getString("jerarquiaAsignatura"));
				posicion = 1;
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getAsignatura()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, vigencia);
				rs = ps.executeQuery();
				if (rs.next())
					jer = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jer = Long.parseLong(filtro.getJerarquiaLogro().trim());
			}
			if (filtro.getJerarquiagrupo() == null
					|| filtro.getJerarquiagrupo().trim().equals("")) {
				// traer jerarquia de asignatura
				ps = cn.prepareStatement(rb.getString("jerarquiaGrupo"));
				posicion = 1;
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getSede()));
				ps.setLong(posicion++, Long.parseLong(filtro.getJornada()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
				rs = ps.executeQuery();
				if (rs.next())
					jerGrupo = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jerGrupo = Long.parseLong(filtro.getJerarquiagrupo().trim());
			}
			// abreviatura de periodo
			perAbrev = Integer.parseInt(filtro.getPeriodo().trim());
			// traer las notas de los logros
			String consulta = rb.getString("plantilla.NotaAsignatura") + " "
					+ rb.getString("plantilla.NotaAsignatura.1");
			for (int r = 0; r < estu.length; r++) {
				if (r == 0) {
					consulta += " "
							+ rb.getString("plantilla.NotaAsignatura.2");
				} else {
					consulta += " "
							+ rb.getString("plantilla.NotaAsignatura.3");
				}
			}
			consulta += " " + rb.getString("plantilla.NotaAsignatura.4");
			ps = cn.prepareStatement(consulta);
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(filtro.getJerarquiagrupo()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, vigencia);
			for (int r = 0; r < estu.length; r++) {
				ps.setString(posicion++, estu[r]);
			}
			rs = ps.executeQuery();
			actual = getFiltroMatriz(getCollection(rs));
			rs.close();
			ps.close();
			resultado[0] = (not != null ? not.length : 0);
			// System.out.println("lista total de notas="+(not!=null?not.length:0)+"");
			// System.out.println("lista de registros que ya estan="+(actual!=null?actual.length:0)+"");
			// String c[][]=new String[actual!=null?
			// actual.length:1][actual!=null? actual[0].length:1];
			String c[][] = new String[not != null ? not.length : 1][actual != null ? actual[0].length
					: 1];
			// System.out.println(not.length+"//"+estu.length+"-lista de notas a actualizar(no borrar)="+(c!=null?c.length:0)+"");
			int cont = 0;
			int ins = 0;
			if (not != null) {
				for (i = 0; i < not.length; i++) {// iterar por estu-nota
					ins = 0;
					ps = cn.prepareStatement(rb
							.getString("plantilla.insertarEvaluacionAsignatura"
									+ perAbrev));
					// System.out.println("plantilla= "+estu[i]+"-"+not[i][0]);
					if (not[i][0] != null) {
						for (k = 0; k < (actual != null ? actual.length : 0); k++) {
							// System.out.println("actual= "+actual[k][0]+"-"+estu[i]);
							if (estu[i].equals(actual[k][0])) {
								// System.out.println("encontrado");
								c[cont++][0] = actual[k][0];
								resultado[1] += 1;
								// System.out.print(" ACTUALIZAR -"+estu[i]+"-");
								ps = cn.prepareStatement(rb
										.getString("plantilla.actualizarEvaluacionAsignatura"
												+ perAbrev));
								ins = 1;
								break;
							}
						}
					}
					if (ps != null && not[i][0] != null
							&& !not[i][0].equals("")) {
						if (ins == 0) {
							// System.out.print(" INSERTAR  -"+estu[i]+"-");
							resultado[2] += 1;
						}
						ps.clearParameters();
						posicion = 1;
						// ps.setLong(posicion++,Long.parseLong(not[i][0].trim()));

						ps.setDouble(posicion++,
								Double.parseDouble(not[i][0].trim()));

						if (not[i][1] == null)
							ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiAusJus
						else
							ps.setLong(posicion++,
									Long.parseLong(not[i][1].trim()));
						/*
						 * if(not[i][2]==null)
						 * ps.setNull(posicion++,java.sql.Types
						 * .VARCHAR);//EvaAsiAusNoJus else
						 * ps.setLong(posicion++,
						 * Long.parseLong(not[i][2].trim()));
						 * if(not[i][3]==null)
						 * ps.setNull(posicion++,java.sql.Types
						 * .VARCHAR);//EvaAsiMotAus else
						 * ps.setLong(posicion++,Long
						 * .parseLong(not[i][3].trim())); if(not[i][4]==null)
						 * ps.setNull(posicion++,java.sql.Types.VARCHAR);//
						 * EvaAsiPorcAus else
						 * ps.setLong(posicion++,Long.parseLong
						 * (not[i][4].trim()));
						 */
						ps.setLong(posicion++, vigencia);
						ps.setLong(posicion++, (jer));
						ps.setLong(posicion++, Long.parseLong(estu[i].trim()));
						int r = ps.executeUpdate();
						ps.close();
						// System.out.println((ins==0?"insertando":"actualizando")+"= CANTIDAD ="+r);
					}
				}
			}
			// System.out.println("\n--REVISAR ELIMINADOS");
			if (actual != null) {
				int band = -1;
				for (i = 0; i < actual.length; i++) {
					band = 1;
					for (j = 0; j < (c != null ? c.length : 0); j++) {
						if (c[j][0] != null) {
							if (c[j][0].equals(actual[i][0])) {
								band = 2;
								// System.out.println("bandera esta"+actual[i][0]);
								break;
							}
						}
					}
					if (band == 1) {
						// System.out.println("bandera no esta "+actual[i][0]);
						otro = 0;
						vacio = 0;
						for (int yy = 1; yy < actual[i].length; yy++) {
							if (yy == perAbrev
									&& (actual[i][yy] == null || actual[i][yy]
											.equals("")))
								vacio = 1;
							if (yy == perAbrev && actual[i][yy] != null
									&& !actual[i][yy].equals(""))
								resultado[3] += 1;
							if (yy != perAbrev && actual[i][yy] != null
									&& !actual[i][yy].equals(""))
								otro++;
						}
						if (otro > 0) {// actualizar a vacio
							if (vacio == 0) {
								ps = cn.prepareStatement(rb
										.getString("plantilla.actualizarEvaluacionAsignatura"
												+ perAbrev));
								ps.clearParameters();
								posicion = 1;
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiEvalu
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiAusJus
								/*
								 * ps.setNull(posicion++,java.sql.Types.VARCHAR);
								 * //EvaAsiAusNoJus
								 * ps.setNull(posicion++,java.sql
								 * .Types.VARCHAR);//EvaAsiMotAus
								 * ps.setNull(posicion
								 * ++,java.sql.Types.VARCHAR);//EvaAsiPorcAus
								 */
								ps.setLong(posicion++, vigencia);// vigencia
								ps.setLong(posicion++, (jer));// EvaAsiCodJerar
								ps.setLong(posicion++,
										Long.parseLong(actual[i][0].trim()));// EvaAsiCodEstud
								int r = ps.executeUpdate();
								ps.close();
								// System.out.println("limpiando CANTIDAD ="+r);
							}
						} else {// borrar registro
							ps = cn.prepareStatement(rb
									.getString("plantilla.eliminarEvaluacionAsignatura"));
							ps.clearParameters();
							posicion = 1;
							ps.setLong(posicion++, (jer));
							ps.setLong(posicion++,
									Long.parseLong(actual[i][0].trim()));
							ps.setLong(posicion++, (vigencia));
							int r = ps.executeUpdate();
							ps.close();
							// System.out.println(" _ELIMINADO -"+actual[i][0]+"- CANTIDAD"+r);
						}
					}
				}
			}
			// System.out.println("\n----------------");
			/* PARA LA CUESTION DE IMPORTACION DE LOGROS */
			/*
			 * //traer las notas de los logros
			 * ps=cn.prepareStatement(rb.getString("listaLogroNotaEstudiante"));
			 * posicion=1;
			 * ps.setLong(posicion++,Long.parseLong(filtro.getJerarquiagrupo
			 * ())); ps.setLong(posicion++,jer);
			 * ps.setLong(posicion++,perAbrev); ps.setLong(posicion++,vigencia);
			 * rs=ps.executeQuery(); actual=getFiltroMatriz(getCollection(rs));
			 * rs.close();ps.close();
			 * 
			 * pst=cn.prepareStatement(rb.getString("EvaluacionLogroInsertar"));
			 * pst2
			 * =cn.prepareStatement(rb.getString("EvaluacionLogroActualizar"));
			 * pst3
			 * =cn.prepareStatement(rb.getString("EvaluacionLogroEliminar"));
			 * resultado2[0]=(not2!=null?not2.length:0); c=new
			 * String[actual!=null? actual.length:1][actual!=null?
			 * actual[0].length:1]; cont=0;ins=0; if(not2!=null){
			 * for(i=0;i<not2.length;i++){//iterar por estu-nota
			 * for(j=0;j<not2[i].length;j++){//iterar por logro ins=0; ps=pst;
			 * //
			 * System.out.println("plantilla= "+estu[i]+"-"+log[j]+"-"+enca[9][
			 * 0]+"-"); if(not2[i][j]!=null){
			 * for(k=0;k<(actual!=null?actual.length:0);k++){
			 * //System.out.println
			 * ("actual= "+actual[k][0]+"-"+actual[k][1]+"-"+actual[k][2]+"-");
			 * if(estu[i].equals(actual[k][0]) && log[j].equals(actual[k][1]) &&
			 * (String.valueOf(jer)).equals(actual[k][2])){
			 * c[cont][0]=actual[k][0]; c[cont][1]=actual[k][1];
			 * c[cont++][2]=actual[k][2]; if(!not2[i][j].equals(actual[k][3])){
			 * resultado2[1]+=1; //System.out.println(" ACTUALIZAR  "); ps=pst2;
			 * ins=1; }else{ //System.out.println(" LO DEJA COMO ESTA ");
			 * ps=null; } break; } } } if(ps!=null && not2[i][j]!=null &&
			 * !not2[i][j].equals("")){ if(ins==0){
			 * //System.out.println(" INSERTAR  "); resultado2[2]+=1;}
			 * ps.clearParameters(); posicion=1;
			 * ps.setLong(posicion++,Long.parseLong
			 * (not2[i][j].trim()));//EvaLogEvalu
			 * ps.setLong(posicion++,jer);//EvaLogCodJerar,
			 * ps.setLong(posicion++
			 * ,Long.parseLong(estu[i].trim()));//EvaLogCodEstu,
			 * ps.setLong(posicion
			 * ++,Long.parseLong(log[j].trim()));//EvaLogCodLogro,
			 * ps.setLong(posicion++,perAbrev);//EvaLogCodPerio
			 * ps.setLong(posicion++,vigencia);//vigencia int
			 * r=ps.executeUpdate();
			 * //System.out.println((ins==0?"insertando":"actualizando"
			 * )+"= CANTIDAD ="+r); } } } }
			 * //System.out.println("\n--REVISAR ELIMINADOS"); if(actual!=null){
			 * int band=-1; for(i=0;i<actual.length;i++){ band=1;
			 * for(j=0;j<(c!=null ? c.length:0);j++){ if(c[j][0]!=null){
			 * if(c[j][0].equals(actual[i][0]) && c[j][1].equals(actual[i][1])
			 * && c[j][2].equals(actual[i][2])){ band=2; break; } } }
			 * if(band==1){ resultado2[3]+=1; //System.out.println("ELIMINAR=");
			 * ps=pst3; ps.clearParameters(); posicion=1;
			 * ps.setLong(posicion++,jer);//EvaLogCodJerar
			 * ps.setLong(posicion++,
			 * Long.parseLong(actual[i][0].trim()));//EvaLogCodEstu
			 * ps.setLong(posicion
			 * ++,Long.parseLong(actual[i][1].trim()));//EvaLogCodLogro,
			 * ps.setLong(posicion++,perAbrev);//EvaLogCodPerio
			 * ps.setLong(posicion++,vigencia);//vigencia int
			 * r=ps.executeUpdate();
			 * //System.out.println(" _ELIMINADO= CANTIDAD"+r); } } }
			 * //System.out.println("\n----------------");
			 */
			// LO NUEVO
			consulta = rb.getString("EvaluacionLogroEliminarTodos") + " "
					+ rb.getString("EvaluacionLogroEliminarTodos.1");
			for (int r = 0; r < estu.length; r++) {
				if (r == 0) {
					consulta += " "
							+ rb.getString("EvaluacionLogroEliminarTodos.2");
				} else {
					consulta += " "
							+ rb.getString("EvaluacionLogroEliminarTodos.3");
				}
			}
			consulta += " " + rb.getString("EvaluacionLogroEliminarTodos.4");
			if (filtro.getFilPlanEstudios() == 1) {
				consulta += " "
						+ rb.getString("EvaluacionLogroEliminarTodos.5");
			}
			ps = cn.prepareStatement(consulta);
			posicion = 1;
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, perAbrev);
			ps.setLong(posicion++, vigencia);
			ps.setLong(posicion++, jerGrupo);
			for (int r = 0; r < estu.length; r++) {
				ps.setString(posicion++, estu[r]);
			}
			if (filtro.getFilPlanEstudios() == 1) {
				ps.setLong(posicion++, Long.parseLong(filtro.getDocente()));
			}
			ps.executeUpdate();
			ps.close();
			cont = 0;
			ins = 0;
			if (not2 != null) {
				resultado2[0] = (not2.length);
				for (i = 0; i < not2.length; i++) {// iterar por estu-nota
					if (not2[i] != null && not2[i].length > 0) {
						for (j = 0; j < not2[i].length; j++) {// iterar por
																// logro
							ins = 0;
							if (not2[i][j] != null && !not2[i][j].equals("")) {
								resultado2[2] += 1;
								ps = cn.prepareStatement(rb
										.getString("EvaluacionLogroInsertar"));
								ps.clearParameters();
								posicion = 1;
								ps.setLong(posicion++,
										Long.parseLong(not2[i][j].trim()));// EvaLogEvalu
								ps.setLong(posicion++, jer);// EvaLogCodJerar,
								ps.setLong(posicion++,
										Long.parseLong(estu[i].trim()));// EvaLogCodEstu,
								ps.setLong(posicion++,
										Long.parseLong(log[j].trim()));// EvaLogCodLogro,
								ps.setLong(posicion++, perAbrev);// EvaLogCodPerio
								ps.setLong(posicion++, vigencia);// vigencia
								ps.executeUpdate();
								ps.close();
							}
						}
					}
				}
			}
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar asignaturas a estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar asignaturas a estudiantes. Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @return<br> Return Type: int[]<br>
	 *             Version 1.1.<br>
	 */
	public int[] getResultado() {
		return resultado;
	}

	/**
	 * @return<br> Return Type: int[]<br>
	 *             Version 1.1.<br>
	 */
	public int[] getResultado2() {
		return resultado2;
	}

	/**
	 * @param filtro
	 * @param not
	 * @param actual
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean importarBateriaLogro(Logros filtro, String[][] not,
			String[][] actual) {
		int posicion = 1;
		Connection cn = null;
		// PreparedStatement pst=null,pst2=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		resultado = new int[4];
		int i;
		int k;
		long jerar = 0;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getPlantillaInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			/*
			 * boolean setCodigo=false; if(actual!=null){
			 * jerar=Long.parseLong(actual[0][0]); if(jerar!=0){ setCodigo=true;
			 * } } if(!setCodigo){
			 */
			ps = cn.prepareStatement(rb.getString("logro.Jerarquia"));
			ps.setLong(posicion++,
					Long.parseLong(filtro.getPlantillaInstitucion()));
			ps.setLong(posicion++, Long.parseLong(filtro.getPlantillaGrado()));
			ps.setLong(posicion++,
					Long.parseLong(filtro.getPlantillaAsignatura()));
			ps.setLong(posicion++,
					Long.parseLong(filtro.getPlantillaMetodologia()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
			if (rs.next()) {
				jerar = rs.getLong(1);
			}
			rs.close();
			ps.close();
			// }
			// LOGCODJERAR, LOGCODIGO, LOGCODPERINICIAL, LOGCODPERFINAL,
			// LOGNOMBRE, LOGABREV, LOGDESCRIPCION
			/*
			 * if(filtro.getEstadoPlanEstudios()==0){
			 * pst=cn.prepareStatement(rb.getString("logro.Insertar"));
			 * pst2=cn.prepareStatement(rb.getString("logro.Actualizar"));
			 * }else{
			 * pst=cn.prepareStatement(rb.getString("logro.Insertar.docente"));
			 * pst2
			 * =cn.prepareStatement(rb.getString("logro.Actualizar.docente")); }
			 */
			resultado[0] = (not != null ? not.length : 0);// cantidad de logros
															// evaluados
			// System.out.println("lista total de notas="+(not!=null?not.length:0)+"");
			// System.out.println("lista de registros que ya estan="+(actual!=null?actual.length:0)+"");
			// System.out.println("lista de notas a actualizar(no borrar)="+(c!=null?c.length:0)+"");
			String codigologro = null;
			String perIni = null, perFin = null, nom = null, abrev = null, desc = null, orden = null;
			String perIniBD = null, perFinBD = null, nomBD = null, abrevBD = null, descBD = null, ordenBD;
			// int cont=0;
			int ins = 0;
			if (not != null) {
				for (i = 0; i < not.length; i++) {// iterar por cada logro
					codigologro = null;
					ins = 0;
					if (filtro.getEstadoPlanEstudios() == 0) {
						ps = cn.prepareStatement(rb.getString("logro.Insertar"));
					} else {
						ps = cn.prepareStatement(rb
								.getString("logro.Insertar.docente"));
					}
					if (not[i][0] != null) {
						perIni = not[i][0];
						perFin = not[i][1];
						nom = not[i][2];
						abrev = not[i][3];
						desc = not[i][4] != null ? not[i][4] : "";
						orden = not[i][5] != null ? not[i][5] : "0";
						if (actual != null) {
							for (k = 0; k < actual.length; k++) {
								perIniBD = actual[k][2];
								perFinBD = actual[k][3];
								nomBD = actual[k][4];
								abrevBD = actual[k][5];
								descBD = actual[k][6] != null ? actual[k][6]
										: "";
								ordenBD = actual[k][6] != null ? actual[k][6]
										: "";

								if (perIni.equals(perIniBD)
										&& perFin.equals(perFinBD)
										&& nom.equals(nomBD)) {
									if (!abrev.equals(abrevBD)
											|| !desc.equals(descBD)
											|| !orden.equals(ordenBD)) {
										resultado[1] += 1;
										codigologro = actual[k][1];
										// ps=pst2;

										if (filtro.getEstadoPlanEstudios() == 0) {
											ps = cn.prepareStatement(rb
													.getString("logro.Actualizar"));
										} else {
											ps = cn.prepareStatement(rb
													.getString("logro.Actualizar.docente"));

										}
										ins = 1;
									} else {
										ps = null;
									}
									break;
								}
							}
						}
					}
					if (ps != null && not[i][0] != null
							&& !not[i][0].equals("")) {
						if (ins == 0) {
							resultado[2] += 1;
						}
						ps.clearParameters();
						posicion = 1;
						ps.setLong(posicion++, Long.parseLong(perIni));
						ps.setLong(posicion++, Long.parseLong(perFin));
						ps.setString(posicion++, nom);
						ps.setString(posicion++, abrev);

						if (GenericValidator.isInt(orden)) {
							ps.setLong(
									posicion++,
									Long.parseLong(orden) > 99 ? 99 : Long
											.parseLong(orden));
						} else {
							ps.setLong(posicion++, Long.parseLong("0"));
						}

						if (not[i][4] == null || not[i][4].equals(""))
							ps.setNull(posicion++, java.sql.Types.VARCHAR);// LOGDESCRIPCION,
						else
							ps.setString(posicion++, desc);
						ps.setLong(posicion++, jerar);
						if (ins == 1) {
							if (codigologro == null || codigologro.equals(""))
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// LOGCODIGO,
							else
								ps.setLong(posicion++,
										Long.parseLong(codigologro.trim()));
						}
						ps.setLong(posicion++, getVigenciaInst(Long
								.parseLong(filtro.getPlantillaInstitucion())));
						if (filtro.getEstadoPlanEstudios() == 1) {
							ps.setLong(posicion++, filtro.getPlantillaDocente());
						}
						int r = ps.executeUpdate();
						ps.close();
						// System.out.println((ins==0?"insertando":"actualizando")+"= CANTIDAD ="+r);
					}
				}
			}
			// System.out.println("\n----------------");
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			in.printStackTrace();
			System.out.println(in.getMensaje());
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println(sqle.getMessage());
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar bateria de logros Posible problema:");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar bateria de logros Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				// OperacionesGenerales.closeStatement(pst);
				// OperacionesGenerales.closeStatement(pst2);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @param estu
	 * @param motivo
	 * @param escala
	 * @param not
	 * @param escala2
	 * @param not2
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean importarEvalArea(FiltroPlantilla filtro, String[] estu,
			String motivo[][], String[][] escala, String[][] not,
			String[][] escala2, String[][] not2) {
		resultado = new int[4];
		resultado2 = new int[4];
		Connection cn = null;
		// PreparedStatement pst=null,pst2=null,pst3=null;
		long jer = 0;
		long jerGrupo = 0;
		ResultSet rs = null;
		int posicion = 1;
		PreparedStatement ps = null;
		int perAbrev = 0;
		int i;
		int j;
		int k;
		int otro, vacio;
		String consulta = "";
		String[][] actual;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (filtro.getJerarquiaLogro() == null
					|| filtro.getJerarquiaLogro().trim().equals("")) {
				// traer jerarquia de area
				ps = cn.prepareStatement(rb.getString("jerarquiaArea"));
				posicion = 1;
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getArea()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, vigencia);
				rs = ps.executeQuery();
				if (rs.next())
					jer = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jer = Long.parseLong(filtro.getJerarquiaLogro().trim());
			}
			if (filtro.getJerarquiagrupo() == null
					|| filtro.getJerarquiagrupo().trim().equals("")) {
				ps = cn.prepareStatement(rb.getString("jerarquiaGrupo"));
				posicion = 1;
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getSede()));
				ps.setLong(posicion++, Long.parseLong(filtro.getJornada()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
				rs = ps.executeQuery();
				if (rs.next())
					jerGrupo = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jerGrupo = Long.parseLong(filtro.getJerarquiagrupo().trim());
			}

			perAbrev = Integer.parseInt(filtro.getPeriodo());

			resultado[0] = (not != null ? not.length : 0);

			if (not != null) {

				/**
				 * (1) Actualizar todos los registrodsa NULL (COMO NUESTRO
				 * DELETE) ***************************************************
				 * */

				for (int r = 0; r < not.length; r++) {

					consulta = rb
							.getString("plantilla.actualizarEvaluacionArea.notas.updateNULL"
									+ filtro.getPeriodo());
					posicion = 1;
					// System.out.println("consulta " + consulta);
					ps = cn.prepareStatement(consulta);
					// System.out.println("jerGrupo " + jerGrupo);
					ps.setLong(posicion++, vigencia);
					ps.setLong(posicion++, jer);
					ps.setLong(posicion++, Long.parseLong(estu[r].trim()));
					ps.executeUpdate();
					ps.close();
				}

				/**
				 * (2) Eliminar los registro basura que estan en NULL
				 * ***************************************************
				 * */
				for (int r = 0; r < not.length; r++) {
					posicion = 1;
					ps = cn.prepareStatement(rb
							.getString("plantilla.actualizarEvaluacionArea.notas.DeleteNULL"));
					ps.setLong(posicion++, jer);
					ps.setLong(posicion++, Long.parseLong(estu[r].trim()));
					ps.setLong(posicion++, vigencia);

					int nunEliminado = ps.executeUpdate();

					ps.close();

					if (nunEliminado > 0) {
						resultado[3] += nunEliminado;
					}
				}

				/**
				 * (3) FOR para update or insert
				 * ***************************************************
				 * */
				for (i = 0; i < not.length; i++) {// notas

					if (not[i][0] != null) { // NOTAS DIMENSION

						// 1.1. Actualizar con nueva nota
						int band = 0;

						ps = cn.prepareStatement(rb
								.getString("plantilla.actualizarEvaluacionArea"
										+ perAbrev));
						// System.out.println("sql " +
						// rb.getString("EvaluacionPreescolar.actualizar"+filtro.getPeriodo()));
						// System.out.println("not[i][l+5].trim( " +
						// not[i][l+5].trim());
						posicion = 1;

						ps.setDouble(posicion++,
								Double.parseDouble(not[i][0].trim()));
						if (not[i][2] == null) {
							ps.setNull(posicion++, java.sql.Types.VARCHAR);// recuperacion
						} else {
							ps.setDouble(posicion++,
									Double.parseDouble(not[i][2].trim()));
						}

						if (not[i][1] == null) {
							ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiAusJus
						} else {
							ps.setLong(posicion++,
									Long.parseLong(not[i][1].trim()));
						}

						ps.setLong(posicion++, vigencia);
						ps.setLong(posicion++, jer);
						ps.setLong(posicion++, Long.parseLong(estu[i].trim()));

						band = ps.executeUpdate();
						ps.close();

						// System.out.println("band " + band);
						// 1.1. Actualizar con nueva nota
						if (band == 0) {
							ps = cn.prepareStatement(rb
									.getString("plantilla.insertarEvaluacionArea"
											+ perAbrev));
							posicion = 1;
							ps.setDouble(posicion++,
									Double.parseDouble(not[i][0].trim()));

							if (not[i][2] == null) {
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// recuperacion
							} else {
								ps.setDouble(posicion++,
										Double.parseDouble(not[i][2].trim()));
							}

							if (not[i][1] == null)
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiAusJus
							else
								ps.setLong(posicion++,
										Long.parseLong(not[i][1].trim()));
							ps.setLong(posicion++, vigencia);
							ps.setLong(posicion++, jer);
							ps.setLong(posicion++,
									Long.parseLong(estu[i].trim()));

							band = ps.executeUpdate();
							resultado[2] += 1;
						} else {
							resultado[1] += 1;
						}

					} // if(not[i][l+5]!=null){ //NOTAS DIMENSION

				}// for(i=0;i<not.length;i++){//notas
			}

			// DESCRIPTORES
			// LO NUEVO
			consulta = rb.getString("EvaluacionDescEliminarTodos") + " "
					+ rb.getString("EvaluacionDescEliminarTodos.1");
			for (int r = 0; r < estu.length; r++) {
				if (r == 0) {
					consulta += " "
							+ rb.getString("EvaluacionDescEliminarTodos.2");
				} else {
					consulta += " "
							+ rb.getString("EvaluacionDescEliminarTodos.3");
				}
			}
			consulta += " " + rb.getString("EvaluacionDescEliminarTodos.4");
			if (filtro.getFilPlanEstudios() == 1) {
				consulta += " " + rb.getString("EvaluacionDescEliminarTodos.5");
			}
			ps = cn.prepareStatement(consulta);
			posicion = 1;
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, perAbrev);
			ps.setLong(posicion++, vigencia);
			ps.setLong(posicion++, jerGrupo);
			for (int r = 0; r < estu.length; r++) {
				ps.setString(posicion++, estu[r]);
			}
			if (filtro.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, Long.parseLong(filtro.getDocente()));
			ps.executeUpdate();
			ps.close();
			int cont = 0;
			int ins = 0;
			long est;
			long descriptor;
			String desc[];
			if (not2 != null) {
				resultado2[0] = (estu != null ? estu.length : 0);
				for (i = 0; i < not2.length; i++) {
					if ((estu.length) > i) {
						est = Long.parseLong(estu[i]);
						// System.out.println("estudiante="+est);
						for (j = 0; j < not2[i].length; j++) {
							desc = null;
							// System.out.println("not2="+not2[i][j]+"/"+"estunot="+estunot[i][j]);
							if (not2[i][j] != null && not2[i][j].length() > 0)
								desc = not2[i][j].split(",");
							// iterar desde desc para encontrar los nuevos e
							// insertarlos
							if (desc != null) {
								for (k = 0; k < desc.length; k++) {
									descriptor = Long.parseLong(desc[k].trim());
									resultado2[1] += 1;
									ps = cn.prepareStatement(rb
											.getString("EvaluacionDescriptorInsertar"));
									ps.clearParameters();
									posicion = 1;
									ps.setLong(posicion++, descriptor);// codigo
																		// de
																		// desc
									ps.setInt(posicion++, (j + 1));// tipo
									ps.setLong(posicion++, jer);// codjerar
									ps.setLong(posicion++, (est));// estudiante
									ps.setLong(posicion++, perAbrev);
									ps.setLong(posicion++, vigencia);
									int r = ps.executeUpdate();
									ps.close();
									// System.out.println("insertando CANTIDAD ="+r);
								}
							}
						}
					}
				}
			}
			// System.out.println("\n----------------");
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Areas a estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Areas a estudiantes. Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @param filtro
	 * @param estu
	 * @param motivo
	 * @param escala
	 * @param not
	 * @param escala2
	 * @param not2
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean importarEvalAreaAntes(FiltroPlantilla filtro, String[] estu,
			String motivo[][], String[][] escala, String[][] not,
			String[][] escala2, String[][] not2) {
		resultado = new int[4];
		resultado2 = new int[4];
		Connection cn = null;
		// PreparedStatement pst=null,pst2=null,pst3=null;
		long jer = 0;
		long jerGrupo = 0;
		ResultSet rs = null;
		int posicion = 1;
		PreparedStatement ps = null;
		int perAbrev = 0;
		int i;
		int j;
		int k;
		int otro, vacio;
		String[][] actual;
		try {
			long vigencia = getVigenciaInst(Long.parseLong(filtro
					.getInstitucion()));
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (filtro.getJerarquiaLogro() == null
					|| filtro.getJerarquiaLogro().trim().equals("")) {
				// traer jerarquia de area
				ps = cn.prepareStatement(rb.getString("jerarquiaArea"));
				posicion = 1;
				ps.setInt(posicion++, 1);
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getArea()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, vigencia);
				rs = ps.executeQuery();
				if (rs.next())
					jer = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jer = Long.parseLong(filtro.getJerarquiaLogro().trim());
			}
			if (filtro.getJerarquiagrupo() == null
					|| filtro.getJerarquiagrupo().trim().equals("")) {
				ps = cn.prepareStatement(rb.getString("jerarquiaGrupo"));
				posicion = 1;
				ps.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
				ps.setLong(posicion++, Long.parseLong(filtro.getSede()));
				ps.setLong(posicion++, Long.parseLong(filtro.getJornada()));
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
				ps.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
				rs = ps.executeQuery();
				if (rs.next())
					jerGrupo = rs.getLong(1);
				rs.close();
				ps.close();
			} else {
				jerGrupo = Long.parseLong(filtro.getJerarquiagrupo().trim());
			}
			// CODIGO DE PERIODO
			perAbrev = Integer.parseInt(filtro.getPeriodo());
			// TRAER LAS NOTAS DE LA BASE DE DATOS
			String consulta = rb.getString("plantilla.NotaArea") + " "
					+ rb.getString("plantilla.NotaArea.1");
			for (int r = 0; r < estu.length; r++) {
				if (r == 0) {
					consulta += " " + rb.getString("plantilla.NotaArea.2");
				} else {
					consulta += " " + rb.getString("plantilla.NotaArea.3");
				}
			}
			consulta += " " + rb.getString("plantilla.NotaArea.4");
			ps = cn.prepareStatement(consulta);
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(filtro.getJerarquiagrupo()));
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, vigencia);
			for (int r = 0; r < estu.length; r++) {
				ps.setString(posicion++, estu[r]);
			}
			rs = ps.executeQuery();
			actual = getFiltroMatriz(getCollection(rs));
			rs.close();
			ps.close();
			// System.out.println("valor de jerarquia"+jer);
			resultado[0] = (not != null ? not.length : 0);
			// System.out.println("lista total de notas="+(not!=null?not.length:0)+"");
			// System.out.println("lista de registros que ya estan="+(actual!=null?actual.length:0)+"");
			String c[][] = new String[not != null ? not.length : 1][actual != null ? actual[0].length
					: 1];
			// System.out.println("lista de notas a actualizar(no borrar)="+(c!=null?c.length:0)+"");
			int cont = 0;
			int ins = 0;
			if (not != null) {
				for (i = 0; i < not.length; i++) {// iterar por estu-nota
					ins = 0;
					ps = cn.prepareStatement(rb
							.getString("plantilla.insertarEvaluacionArea"
									+ perAbrev));
					// System.out.println("plantilla= "+estu[i]+"-"+not[i][0]);
					if (not[i][0] != null) {
						for (k = 0; k < (actual != null ? actual.length : 0); k++) {
							// System.out.println("actual= "+actual[k][0]+"-"+actual[k][1]);
							if (estu[i].equals(actual[k][0])) {
								c[cont++][0] = actual[k][0];
								// System.out.println(" ACTUALIZAR ");
								resultado[1] += 1;
								ps = cn.prepareStatement(rb
										.getString("plantilla.actualizarEvaluacionArea"
												+ perAbrev));
								ins = 1;
								break;
							}
						}
					}
					if (ps != null && not[i][0] != null
							&& !not[i][0].equals("")) {
						if (ins == 0) { // System.out.println(" INSERTAR  ");
							resultado[2] += 1;
						}
						ps.clearParameters();
						posicion = 1;
						// ps.setLong(posicion++,Long.parseLong(not[i][0].trim()));
						ps.setDouble(posicion++,
								Double.parseDouble(not[i][0].trim()));
						if (not[i][1] == null)
							ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiAusJus
						else
							ps.setLong(posicion++,
									Long.parseLong(not[i][1].trim()));
						/*
						 * if(not[i][2]==null)
						 * ps.setNull(posicion++,java.sql.Types
						 * .VARCHAR);//EvaAsiAusNoJus else
						 * ps.setLong(posicion++,
						 * Long.parseLong(not[i][2].trim()));
						 * if(not[i][3]==null)
						 * ps.setNull(posicion++,java.sql.Types
						 * .VARCHAR);//EvaAsiMotAus else
						 * ps.setLong(posicion++,Long
						 * .parseLong(not[i][3].trim())); if(not[i][4]==null)
						 * ps.setNull(posicion++,java.sql.Types.VARCHAR);//
						 * EvaAsiPorcAus else
						 * ps.setLong(posicion++,Long.parseLong
						 * (not[i][4].trim()));
						 */
						ps.setLong(posicion++, vigencia);
						ps.setLong(posicion++, jer);
						ps.setLong(posicion++, Long.parseLong(estu[i].trim()));
						int r = ps.executeUpdate();
						ps.close();
						// System.out.println((ins==0?"insertando":"actualizando")+"= CANTIDAD ="+r);
					}
				}
			}
			// System.out.println("\n--REVISAR ELIMINADOS");
			if (actual != null) {
				int band = -1;
				for (i = 0; i < actual.length; i++) {
					band = 1;
					for (j = 0; j < (c != null ? c.length : 0); j++) {
						if (c[j][0] != null) {
							if (c[j][0].equals(actual[i][0])) {
								band = 2;
								break;
							}
						}
					}
					if (band == 1) {
						otro = 0;
						vacio = 0;
						for (int yy = 1; yy < actual[i].length; yy++) {
							if (yy == perAbrev && actual[i][yy] != null
									&& !actual[i][yy].equals(""))
								resultado[3] += 1;
							if (yy != perAbrev && actual[i][yy] != null
									&& !actual[i][yy].equals(""))
								otro++;
							if (yy == perAbrev
									&& (actual[i][yy] == null || actual[i][yy]
											.equals("")))
								vacio = 1;
						}
						if (otro > 0) {// actualizar a vacio
							if (vacio == 0) {
								ps = cn.prepareStatement(rb
										.getString("plantilla.actualizarEvaluacionArea"
												+ perAbrev));
								ps.clearParameters();
								posicion = 1;
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiEvalu
								ps.setNull(posicion++, java.sql.Types.VARCHAR);// EvaAsiAusJus
								/*
								 * ps.setNull(posicion++,java.sql.Types.VARCHAR);
								 * //EvaAsiAusNoJus
								 * ps.setNull(posicion++,java.sql
								 * .Types.VARCHAR);//EvaAsiMotAus
								 * ps.setNull(posicion
								 * ++,java.sql.Types.VARCHAR);//EvaAsiPorcAus
								 */
								ps.setLong(posicion++, vigencia);// VIGENCIA
								ps.setLong(posicion++, jer);// EvaAsiCodJerar
								ps.setLong(posicion++,
										Long.parseLong(actual[i][0].trim()));// EvaAsiCodEstud
								int r = ps.executeUpdate();
								ps.close();
								// System.out.println("limpiando CANTIDAD ="+r);
							}
						} else {// borrar registro
							ps = cn.prepareStatement(rb
									.getString("plantilla.eliminarEvaluacionArea"));
							ps.clearParameters();
							posicion = 1;
							ps.setLong(posicion++, jer);
							ps.setLong(posicion++,
									Long.parseLong(actual[i][0].trim()));
							ps.setLong(posicion++, vigencia);// VIGENCIA
							int r = ps.executeUpdate();
							ps.close();
							// System.out.println(" _ELIMINADO= CANTIDAD"+r);
						}
					}
				}
			}
			// System.out.println("\n----------------");
			// LA CUESTION DE IMPORTACION DE DESCRIPTORES
			/*
			 * //traer una version de las notas
			 * ps=cn.prepareStatement(rb.getString
			 * ("listaDescriptorNotaEstudiante3")); posicion=1;
			 * ps.setLong(posicion
			 * ++,Long.parseLong(filtro.getJerarquiagrupo()));
			 * ps.setLong(posicion++,jer); ps.setLong(posicion++,perAbrev);
			 * ps.setLong(posicion++,vigencia); rs=ps.executeQuery();
			 * actual=getFiltroMatriz(getCollection(rs)); rs.close();ps.close();
			 * 
			 * pst=cn.prepareStatement(rb.getString("EvaluacionDescriptorInsertar"
			 * )); pst2=cn.prepareStatement(rb.getString(
			 * "EvaluacionDescriptorActualizar"));
			 * pst3=cn.prepareStatement(rb.getString
			 * ("EvaluacionDescriptorEliminar")); String valor="",valor_="";
			 * String [][]estunot=new String[estu.length][4];
			 * resultado2[0]=(estu!=null?estu.length:0);
			 * for(m=0;m<(estu!=null?estu.length:0);m++){ valor="";
			 * for(y=0;y<(actual!=null?actual.length:0);y++){
			 * if(estu[m].equals(actual[y][0])){ if(actual[y][1].equals("1")){
			 * if(estunot[m][0]==null)estunot[m][0]="";
			 * estunot[m][0]+=actual[y][2]+","; } if(actual[y][1].equals("2")){
			 * if(estunot[m][1]==null)estunot[m][1]="";
			 * estunot[m][1]+=actual[y][2]+","; } if(actual[y][1].equals("3")){
			 * if(estunot[m][2]==null)estunot[m][2]="";
			 * estunot[m][2]+=actual[y][2]+","; } if(actual[y][1].equals("4")){
			 * if(estunot[m][3]==null)estunot[m][3]="";
			 * estunot[m][3]+=actual[y][2]+","; } } } if(estunot[m][0]!=null &&
			 * estunot[m][0].lastIndexOf(",")!=-1)
			 * estunot[m][0]=estunot[m][0].substring
			 * (0,estunot[m][0].lastIndexOf(",")); if(estunot[m][1]!=null &&
			 * estunot[m][1].lastIndexOf(",")!=-1)
			 * estunot[m][1]=estunot[m][1].substring
			 * (0,estunot[m][1].lastIndexOf(",")); if(estunot[m][2]!=null &&
			 * estunot[m][2].lastIndexOf(",")!=-1)
			 * estunot[m][2]=estunot[m][2].substring
			 * (0,estunot[m][2].lastIndexOf(",")); if(estunot[m][3]!=null &&
			 * estunot[m][3].lastIndexOf(",")!=-1)
			 * estunot[m][3]=estunot[m][3].substring
			 * (0,estunot[m][3].lastIndexOf(",")); } boolean insertar=true;
			 * String est; String desc[]; String desc0[]; if(not2!=null){
			 * for(i=0;i<not2.length;i++){ if((estu.length)>i){ est=estu[i];
			 * //System.out.println("estudiante="+est);
			 * for(j=0;j<not2[i].length;j++){ desc0=desc=null;
			 * //System.out.println
			 * ("not2="+not2[i][j]+"/"+"estunot="+estunot[i][j]);
			 * if(not2[i][j]!=null &&
			 * not2[i][j].length()>0)desc=not2[i][j].split(",");
			 * if(estunot[i][j]!=null && estunot[i][j].length()>0)
			 * desc0=estunot[i][j].split(","); //
			 * for(k=0;k<(desc!=null?desc.length:0);k++){ //
			 * System.out.println("desc="+desc[k]); // } //
			 * for(k=0;k<(desc0!=null?desc0.length:0);k++){ //
			 * System.out.println("desc0="+desc0[k]); // } //iterar desde desc
			 * para encontrar los nuevos e insertarlos if(desc!=null){
			 * for(k=0;k<desc.length;k++){ insertar=true;
			 * for(l=0;l<(desc0!=null?desc0.length:0);l++){
			 * if(desc[k].equals(desc0[l])){ insertar=false; break; } }
			 * if(insertar){ resultado2[1]+=1;
			 * //System.out.println("insertar "+desc[k]); ps=pst;
			 * ps.clearParameters(); posicion=1;
			 * ps.setLong(posicion++,Long.parseLong(desc[k].trim()));//codigo de
			 * desc ps.setInt(posicion++,(j+1));//tipo
			 * ps.setLong(posicion++,jer);//codjerar
			 * ps.setLong(posicion++,Long.parseLong(est.trim()));//estudiante
			 * ps.setLong(posicion++,perAbrev); ps.setLong(posicion++,vigencia);
			 * int r=ps.executeUpdate();
			 * //System.out.println("insertando CANTIDAD ="+r); } } } //iterar
			 * desde desc0 para encontrar los quitados y eliminarlos
			 * for(k=0;k<(desc0!=null?desc0.length:0);k++){ insertar=true;
			 * for(l=0;l<(desc!=null?desc.length:0);l++){
			 * if(desc0[k].equals(desc[l])){ insertar=false; break; } }
			 * if(insertar){ resultado2[3]+=1;
			 * //System.out.println("Eliminar "+desc0[k]); ps=pst3;
			 * ps.clearParameters(); posicion=1; ps.setLong(posicion++,jer);
			 * ps.setLong(posicion++,Long.parseLong(est.trim()));
			 * ps.setLong(posicion++,perAbrev);
			 * ps.setLong(posicion++,Long.parseLong(desc0[k].trim()));
			 * ps.setLong(posicion++,vigencia); int r=ps.executeUpdate();
			 * //System.out.println("Eliminando CANTIDAD ="+r); } } } } } }
			 */
			// LO NUEVO
			consulta = rb.getString("EvaluacionDescEliminarTodos") + " "
					+ rb.getString("EvaluacionDescEliminarTodos.1");
			for (int r = 0; r < estu.length; r++) {
				if (r == 0) {
					consulta += " "
							+ rb.getString("EvaluacionDescEliminarTodos.2");
				} else {
					consulta += " "
							+ rb.getString("EvaluacionDescEliminarTodos.3");
				}
			}
			consulta += " " + rb.getString("EvaluacionDescEliminarTodos.4");
			if (filtro.getFilPlanEstudios() == 1) {
				consulta += " " + rb.getString("EvaluacionDescEliminarTodos.5");
			}
			ps = cn.prepareStatement(consulta);
			posicion = 1;
			ps.setLong(posicion++, jer);
			ps.setLong(posicion++, perAbrev);
			ps.setLong(posicion++, vigencia);
			ps.setLong(posicion++, jerGrupo);
			for (int r = 0; r < estu.length; r++) {
				ps.setString(posicion++, estu[r]);
			}
			if (filtro.getFilPlanEstudios() == 1)
				ps.setLong(posicion++, Long.parseLong(filtro.getDocente()));
			ps.executeUpdate();
			ps.close();
			cont = 0;
			ins = 0;
			long est;
			long descriptor;
			String desc[];
			if (not2 != null) {
				resultado2[0] = (estu != null ? estu.length : 0);
				for (i = 0; i < not2.length; i++) {
					if ((estu.length) > i) {
						est = Long.parseLong(estu[i]);
						// System.out.println("estudiante="+est);
						for (j = 0; j < not2[i].length; j++) {
							desc = null;
							// System.out.println("not2="+not2[i][j]+"/"+"estunot="+estunot[i][j]);
							if (not2[i][j] != null && not2[i][j].length() > 0)
								desc = not2[i][j].split(",");
							// iterar desde desc para encontrar los nuevos e
							// insertarlos
							if (desc != null) {
								for (k = 0; k < desc.length; k++) {
									descriptor = Long.parseLong(desc[k].trim());
									resultado2[1] += 1;
									ps = cn.prepareStatement(rb
											.getString("EvaluacionDescriptorInsertar"));
									ps.clearParameters();
									posicion = 1;
									ps.setLong(posicion++, descriptor);// codigo
																		// de
																		// desc
									ps.setInt(posicion++, (j + 1));// tipo
									ps.setLong(posicion++, jer);// codjerar
									ps.setLong(posicion++, (est));// estudiante
									ps.setLong(posicion++, perAbrev);
									ps.setLong(posicion++, vigencia);
									int r = ps.executeUpdate();
									ps.close();
									// System.out.println("insertando CANTIDAD ="+r);
								}
							}
						}
					}
				}
			}
			// System.out.println("\n----------------");
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Areas a estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar Areas a estudiantes. Posible problema: "
					+ e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * @function: Retorna la escala segun el tipo de evaluacion de institucion
	 * @param n
	 * @param login
	 * @return
	 */
	public String[][] getEscalaNivelEval(FiltroPlantilla filtroPlantilla)
			throws Exception {
		// System.out.println(new Date() + "getEscalaNivelEval ");
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		Collection list = null;
		String resul[][] = null;
		try {

			// System.out.println("filtroPlantilla inst "
			// + filtroPlantilla.getInstitucion());
			// System.out.println("filtroPlantilla sede "
			// + filtroPlantilla.getSede());
			// System.out.println("filtroPlantilla jornada "
			// + filtroPlantilla.getJornada());
			// System.out.println("filtroPlantilla.getMetodologia() "
			// + filtroPlantilla.getMetodologia());
			// System.out.println("filtroPlantilla.getGrado() "
			// + filtroPlantilla.getGrado());

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
			filtroPlantilla.setFilCodModoEval(tipoEvalVO.getCod_modo_eval());
			filtroPlantilla.setFilCodTipoEvalPres(tipoEvalVO
					.getCod_tipo_eval_pree());

			// 2. Si es conceptual obtener escalas sino retorna el max y min
			if (tipoEvalVO.getCod_tipo_eval() == ParamsVO.ESCALA_CONCEPTUAL) {
				// System.out.println("TIPO DE EVALUACION CONCEPTUAL "
				// + getEscalaConceptual(filtroEvaluacion, login).size());
				return getFiltroMatriz(getEscalaConceptual(filtroEvaluacion,
						login));
			} else {
				// System.out.println("TIPO DE EVALUACION NUM O PORCENTUAL");
				list = new ArrayList();
				Object[] O = new Object[3];
				O[0] = "";
				O[1] = "" + tipoEvalVO.getEval_min();
				O[2] = "" + tipoEvalVO.getEval_max();
				list.add(O);
				resul = getFiltroMatriz(list);
				// list2.add( tipoEvalVO.getEval_min());
			}

		} catch (Exception sqle) {
			sqle.printStackTrace();
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
		return resul;
	}

	public TipoEvalVO getTipoEval(FiltroBeanEvaluacion filtroEvaluacion,
			Login login) throws Exception {
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

			filtroEvaluacion.setInstitucion(login.getInstId());
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

			// System.out.println("CONSULTA getTipoEval" + sql);
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
				tipoEvalVO.setCod_modo_eval(rs.getInt(i++));
				tipoEvalVO.setCod_tipo_eval_pree(rs.getInt(i++));
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
			vigencia = getVigenciaInst(Long.parseLong(filtroEvaluacion
					.getInstitucion()));
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
	 * @function:
	 * @param filtroPlantilla
	 * @return
	 * @throws Exception
	 */
	public String[][] getEscalaPreescolar() throws Exception {
		// System.out.println("getEscalaPreescolar ");
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String[][] resul = null;

		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEscalaPreescolar"));
			rs = ps.executeQuery();

			int ind = 0;
			Collection list = getCollection(rs);

			if (list != null && !list.isEmpty()) {
				return getFiltroMatriz(list);
			}

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
		return null;
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
	public List getListaDimenciones(long vig, long inst, long metod, List l)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getListaDimenciones"));
			/*
			 * st.setLong(i++,vig); st.setLong(i++,inst); st.setLong(i++,metod);
			 */

			rs = st.executeQuery();
			// while(rs.next()){
			// i=1;
			// DimensionesVO dim = new DimensionesVO();
			// //dim.setDimcodinst(rs.getLong(i++));
			// //dim.setDimcodmetod(rs.getLong(i++));
			// dim.setDimcodigo(rs.getLong(i++));
			// dim.setDimnombre(rs.getString(i++));
			// dim.setDimorden(rs.getLong(i++));
			// dim.setDimabrev(rs.getString(i++));
			// //dim.setDimvigencia(rs.getLong(i++));
			// l.add(dim);
			// }

			DimensionesVO dim = new DimensionesVO();
			dim.setDimcodigo(1);
			dim.setDimnombre("Corporal");
			dim.setDimorden(1);
			dim.setDimabrev("Corporal");
			l.add(dim);

			dim = new DimensionesVO();
			dim.setDimcodigo(2);
			dim.setDimnombre("Cognitiva");
			dim.setDimorden(2);
			dim.setDimabrev("Cognitiva");
			l.add(dim);

			dim = new DimensionesVO();
			dim.setDimcodigo(3);
			dim.setDimnombre("Comunicativa");
			dim.setDimorden(3);
			dim.setDimabrev("Comunicativa");
			l.add(dim);

			dim = new DimensionesVO();
			dim.setDimcodigo(4);
			dim.setDimnombre("ntica");
			dim.setDimorden(4);
			dim.setDimabrev("ntica");
			l.add(dim);

			dim = new DimensionesVO();
			dim.setDimcodigo(5);
			dim.setDimnombre("Estntica");
			dim.setDimorden(5);
			dim.setDimabrev("Estntica");
			l.add(dim);

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
	public boolean isGradoPreecolar_(String codGrado) throws Exception {
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
			ps.setLong(posicion++, Long.parseLong(codGrado));// codGrado
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}

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
