package siges.rotacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.rotacion.beans.DocXGrupoVO;
import siges.rotacion.beans.EspXGradoVO;
import siges.rotacion.beans.FiltroDocXGrupoVO;
import siges.rotacion.beans.FiltroEspXGradoVO;
import siges.rotacion.beans.FiltroRotacion;
import siges.rotacion.beans.Horario;
import siges.rotacion.beans.Rotacion;
import siges.rotacion.beans.forma.AsignaturaVO;
import siges.rotacion.beans.forma.DocenteVO;
import siges.rotacion.beans.forma.EspacioVO;
import siges.rotacion.beans.forma.EstructuraGradoVO;
import siges.rotacion.beans.forma.EstructuraVO;
import siges.rotacion.beans.forma.FijarAsignaturaVO;
import siges.rotacion.beans.forma.FijarEspacioDocenteVO;
import siges.rotacion.beans.forma.FijarEspacioVO;
import siges.rotacion.beans.forma.GradoVO;
import siges.rotacion.beans.forma.GrupoVO;
import siges.rotacion.beans.forma.InhabilitarDocenteVO;
import siges.rotacion.beans.forma.InhabilitarEspacioVO;
import siges.rotacion.beans.forma.InhabilitarHoraVO;
import siges.rotacion.beans.forma.JornadaVO;
import siges.rotacion.beans.forma.SedeVO;
import siges.rotacion2.beans.ClaseAsignaturaVO;
import siges.rotacion2.beans.DiaAsignaturaVO;
import siges.rotacion2.beans.HorarioAsignaturaVO;

/**
 * @author Administrador
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class RotacionDAO extends siges.dao.Dao {

	private Cursor cursor;

	public String sentencia;

	public String mensaje;

	public String buscar;

	private java.text.SimpleDateFormat sdf;

	private java.sql.Timestamp f2;

	private ResourceBundle rbRot;

	public int[] resultado;

	private String jerarquia = "";

	/**
	 * Constructor de la clase
	 */
	public RotacionDAO(Cursor cur) {
		super(cur);
		this.cursor = cur;
		sentencia = null;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rbRot = ResourceBundle.getBundle("siges.rotacion.bundle.rotacion");
	}

	public String isLocked(Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		// Collection list = null;
		try {
			long inst = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(!l.getSedeId().equals("") ? l
					.getSedeId() : "-1");
			long jor = Long.parseLong(!l.getJornadaId().equals("") ? l
					.getJornadaId() : "-1");
			cn = cursor.getConnection();
			if (sede == -1) {
				pst = cn.prepareStatement(rbRot.getString("Reportelocked1"));
				pst.clearParameters();
				int posicion = 1;
				pst.setLong(posicion++, inst);
			} else {
				pst = cn.prepareStatement(rbRot.getString("Reportelocked2"));
				pst.clearParameters();
				int posicion = 1;
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
			}
			rs = pst.executeQuery();
			if (rs.next()) {
				return "1";
			} else {
				return "0";
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
			}
		}
		return "0";
	}

	/**
	 * Comparar Beans de Espacio
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansFijarEspacio(Rotacion r, Rotacion r2) {
		int cont;
		if (!r.getRoteagjerjornada().equals(r2.getRoteagjerjornada()))
			return false;
		if (!r.getRoteagespacio().equals(r2.getRoteagespacio()))
			return false;
		if (!r.getRoteagasignatura().equals(r2.getRoteagasignatura()))
			return false;
		if (r.getRoteaggrados() == null && r2.getRoteaggrados() != null)
			return false;
		if (r.getRoteaggrados() != null && r2.getRoteaggrados() == null)
			return false;
		if (r.getRoteaggrados() != null && r2.getRoteaggrados() != null) {
			cont = 0;
			if (r.getRoteaggrados().length != r2.getRoteaggrados().length)
				return false;
			else {
				for (int a = 0; a < r.getRoteaggrados().length; a++) {
					if (!r.getRoteaggrados()[a].equals(r2.getRoteaggrados()[a])) {
						cont++;
						;
					}
				}
				if (!(r.getRoteaggrados().length == cont)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Comparar Beans de Asignatura
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansFijarAsignatura(Rotacion r, Rotacion r2) {
		int cont;
		if (!r.getRotfasestructura().equals(r2.getRotfasestructura()))
			return false;
		if (!r.getRotfasblqminimo().equals(r2.getRotfasblqminimo()))
			return false;
		if (!r.getRotfasasignatura().equals(r2.getRotfasasignatura()))
			return false;
		if (r.getRotfasgrados() == null && r2.getRotfasgrados() != null)
			return false;
		if (r.getRotfasgrados() != null && r2.getRotfasgrados() == null)
			return false;
		if (r.getRotfasgrados() != null && r2.getRotfasgrados() != null) {
			cont = 0;
			if (r.getRotfasgrados().length != r2.getRotfasgrados().length)
				return false;
			else {
				for (int a = 0; a < r.getRotfasgrados().length; a++) {
					if (!r.getRotfasgrados()[a].equals(r2.getRotfasgrados()[a])) {
						cont++;
						;
					}
				}
				if (!(r.getRotfasgrados().length == cont)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Comparar Beans de EspFisJor
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansInhEspacios(Rotacion r, Rotacion r2) {
		if (!r.getRotefjestructura().equals(r2.getRotefjestructura()))
			return false;
		if (!r.getRotefjespacio().equals(r2.getRotefjespacio()))
			return false;
		if (!r.getRotefjhoraini().equals(r2.getRotefjhoraini()))
			return false;
		if (!r.getRotefjhorafin().equals(r2.getRotefjhorafin()))
			return false;
		if (!r.getRotefjcausa().equals(r2.getRotefjcausa()))
			return false;
		if (!r.getRotefjdia().equals(r2.getRotefjdia()))
			return false;
		return true;
	}

	/**
	 * Comparar Beans de Inhabilitar Docente
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansInhDocente(Rotacion r, Rotacion r2) {
		if (!r.getRotihdestructura().equals(r2.getRotihdestructura()))
			return false;
		if (!r.getRotihddocente().equals(r2.getRotihddocente()))
			return false;
		if (!r.getRotihdhoraini().equals(r2.getRotihdhoraini()))
			return false;
		if (!r.getRotihdhorafin().equals(r2.getRotihdhorafin()))
			return false;
		if (!r.getRotihdcausa().equals(r2.getRotihdcausa()))
			return false;
		if (!r.getRotihddia().equals(r2.getRotihddia()))
			return false;
		return true;
	}

	/**
	 * Comparar Beans de Inhabilitar Hora
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansInhHora(Rotacion r, Rotacion r2) {
		if (!r.getRotihestructura().equals(r2.getRotihestructura()))
			return false;
		if (!r.getRotihhoraini().equals(r2.getRotihhoraini()))
			return false;
		if (!r.getRotihhorafin().equals(r2.getRotihhorafin()))
			return false;
		if (!r.getRotihcausa().equals(r2.getRotihcausa()))
			return false;
		if (!r.getRotihdia().equals(r2.getRotihdia()))
			return false;
		return true;
	}

	/**
	 * Comparar Beans de Fijar Espacio por Docente
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansFijarEspacioDocente(Rotacion r, Rotacion r2) {
		if (!r.getRtefdoespacio().equals(r2.getRtefdoespacio()))
			return false;
		if (!r.getRtefdodocente().equals(r2.getRtefdodocente()))
			return false;
		if (!r.getRtefdojercodigo().equals(r2.getRtefdojercodigo()))
			return false;
		return true;
	}

	/**
	 * Comparar Beans de Docente
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansFijarDocente(Rotacion r, Rotacion r2) {
		int cont;
		if (!r.getRotdagjornada().equals(r2.getRotdagjornada()))
			return false;
		if (!r.getRotdagdocente().equals(r2.getRotdagdocente()))
			return false;
		if (!r.getRotdagasignatura().equals(r2.getRotdagasignatura()))
			return false;
		if (!r.getRotdaghoras().equals(r2.getRotdaghoras()))
			return false;
		if (r.getRotdaggrados() == null && r2.getRotdaggrados() != null)
			return false;
		if (r.getRotdaggrados() != null && r2.getRotdaggrados() == null)
			return false;
		if (r.getRotdaggrados() != null && r2.getRotdaggrados() != null) {
			cont = 0;
			if (r.getRotdaggrados().length != r2.getRotdaggrados().length)
				return false;
			else {
				for (int a = 0; a < r.getRotdaggrados().length; a++) {
					if (!r.getRotdaggrados()[a].equals(r2.getRotdaggrados()[a])) {
						cont++;
						;
					}
				}
				if (!(r.getRotdaggrados().length == cont)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Comparar Beans de TipoReceso
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansEstructura(Rotacion r, Rotacion r2) {
		int cont;
		// if(!r.getRotstrcodigo().equals(r2.getRotstrcodigo())) return false;

		if (!r.getRotstrdurblq().equals(r2.getRotstrdurblq()))
			return false;
		if (!r.getRotstrdurhor().equals(r2.getRotstrdurhor()))
			return false;
		if (!r.getRotstrhorafin().equals(r2.getRotstrhorafin()))
			return false;
		if (!r.getRotstrhoraini().equals(r2.getRotstrhoraini()))
			return false;
		if (!r.getRotstrjercodigo().equals(r2.getRotstrjercodigo()))
			return false;
		if (!r.getRotstrnombre().equals(r2.getRotstrnombre()))
			return false;
		if (!r.getRotstrnumblq().equals(r2.getRotstrnumblq()))
			return false;
		if (!r.getRotstrsemcic().equals(r2.getRotstrsemcic()))
			return false;
		if (r.getRtesgrgrado() == null && r2.getRtesgrgrado() != null)
			return false;
		if (r.getRtesgrgrado() != null && r2.getRtesgrgrado() == null)
			return false;
		if (r.getRtesgrgrado() != null && r2.getRtesgrgrado() != null) {
			cont = 0;
			if (r.getRtesgrgrado().length != r2.getRtesgrgrado().length)
				return false;
			else {
				for (int a = 0; a < r.getRtesgrgrado().length; a++) {
					if (!r.getRtesgrgrado()[a].equals(r2.getRtesgrgrado()[a])) {
						cont++;
					}
				}
				if (!(r.getRtesgrgrado().length == cont)) {
					return false;
				}
			}
		}
		return true;

		// return false;
	}

	/**
	 * Comparar Beans de TipoReceso
	 * 
	 * @param r
	 * @param r2
	 * @return
	 */
	public boolean compararBeansPriorizar(Rotacion r, Rotacion r2) {
		int cont;
		if (!r.getEstructura_().equals(r2.getEstructura_()))
			return false;
		if (!r.getPriorizar_().equals(r2.getPriorizar_()))
			return false;
		if (!r.getInstjerar_().equals(r2.getInstjerar_()))
			return false;
		if (r.getEstructura_() == null && r2.getEstructura_() != null)
			return false;
		if (r.getEstructura_() != null && r2.getEstructura_() == null)
			return false;
		if (r.getEstructura_() != null && r2.getEstructura_() != null) {
			cont = 0;
			if (r.getEstructura_().length != r2.getEstructura_().length)
				return false;
			else {
				for (int a = 0; a < r.getEstructura_().length; a++) {
					if (!r.getEstructura_()[a].equals(r2.getEstructura_()[a])) {
						cont++;
					}
				}
				if (!(r.getEstructura_().length == cont)) {
					return false;
				}
			}
		}
		if (r.getGrado_() == null && r2.getGrado_() != null)
			return false;
		if (r.getGrado_() != null && r2.getGrado_() == null)
			return false;
		if (r.getGrado_() != null && r2.getGrado_() != null) {
			cont = 0;
			if (r.getGrado_().length != r2.getGrado_().length)
				return false;
			else {
				for (int a = 0; a < r.getGrado_().length; a++) {
					if (!r.getGrado_()[a].equals(r2.getGrado_()[a])) {
						cont++;
					}
				}
				if (!(r.getGrado_().length == cont)) {
					return false;
				}
			}
		}
		if (r.getAsignatura_() == null && r2.getAsignatura_() != null)
			return false;
		if (r.getAsignatura_() != null && r2.getAsignatura_() == null)
			return false;
		if (r.getAsignatura_() != null && r2.getAsignatura_() != null) {
			cont = 0;
			if (r.getAsignatura_().length != r2.getAsignatura_().length)
				return false;
			else {
				for (int a = 0; a < r.getAsignatura_().length; a++) {
					if (!r.getAsignatura_()[a].equals(r2.getAsignatura_()[a])) {
						cont++;
					}
				}
				if (!(r.getAsignatura_().length == cont)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean insertarFijarAsignatura(Rotacion r) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			for (int i = 0; i < (r.getRotfasgrados() == null ? 0 : r
					.getRotfasgrados().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.FijarAsignatura"));
				pst.clearParameters();
				posicion = 1;

				if (r.getRotfasestructura().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotfasestructura().trim()));
				if (r.getRotfasasignatura().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotfasasignatura().trim()));
				if (r.getRotfasgrados()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotfasgrados()[i].trim()));
				if (r.getRotfasblqminimo().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotfasblqminimo().trim()));
				pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando insertar Fijar Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarFijarEspacio(Rotacion r, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			ResultSet rs = null;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Asignar.Jerar_1_6"));
			pst.clearParameters();
			posicion = 1;

			pst.setLong(posicion++, Long.parseLong(l.getInstId().trim()));
			if (r.getRoteagsede().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRoteagsede().trim()));
			if (r.getRoteagjornada().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRoteagjornada().trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				r.setRoteagjerjornada(rs.getString(1));
			}
			rs.close();
			pst.close();
			for (int i = 0; i < (r.getRoteaggrados() == null ? 0 : r
					.getRoteaggrados().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.FijarEspacio"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++,
						Long.parseLong(r.getRoteagjerjornada().trim()));
				if (r.getRoteaggrados()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteaggrados()[i].trim()));
				if (r.getRoteagespacio().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteagespacio().trim()));
				if (r.getRoteagasignatura().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteagasignatura().trim()));
				if (r.getRoteagexclusivo().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteagexclusivo().trim()));
				pst.setInt(posicion++, r.getVigencia());
				pst.setInt(posicion++,
						Integer.parseInt(r.getRotstrmetodologia()));
				pst.executeUpdate();
				pst.close();
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Espacio-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarInhEspacios(Rotacion r, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Insertar.InhEspacios"));
			pst.clearParameters();
			posicion = 1;

			if (r.getRotefjestructura().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotefjestructura().trim()));
			if (r.getRotefjespacio().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotefjespacio().trim()));
			if (r.getRotefjhoraini().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotefjhoraini().trim());
			if (r.getRotefjhorafin().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotefjhorafin().trim());
			if (r.getRotefjcausa().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotefjcausa().trim());
			if (r.getRotefjdia().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(r.getRotefjdia().trim()));
			pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Espacio-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarInhDocente(Rotacion r, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Insertar.InhDocente"));
			pst.clearParameters();
			posicion = 1;
			if (r.getRotihdestructura().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotihdestructura().trim()));
			if (r.getRotihddocente().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotihddocente().trim()));
			if (r.getRotihdhoraini().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotihdhoraini().trim());
			if (r.getRotihdhorafin().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotihdhorafin().trim());
			if (r.getRotihdcausa().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotihdcausa().trim());
			if (r.getRotihddia().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(r.getRotihddia().trim()));
			pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarInhHora(Rotacion r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long est = Long.parseLong(r.getRotihestructura());
			long hi = Long.parseLong(r.getRotihhoraini());
			long hf = Long.parseLong(r.getRotihhorafin());
			long dia = Long.parseLong(r.getRotihdia());
			String c = r.getRotihcausa();
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Insertar.InhHora"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, est);
			pst.setLong(posicion++, hi);
			pst.setLong(posicion++, hf);
			pst.setString(posicion++, c);
			pst.setLong(posicion++, dia);
			pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarFijarEspacioDocente(Rotacion r) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot
					.getString("Insertar.FijarEspacioDocente"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(r.getRtefdoespacio().trim()));
			pst.setLong(posicion++, Long.parseLong(r.getRtefdodocente().trim()));
			pst.setLong(posicion++,
					Long.parseLong(r.getRtefdojercodigo().trim()));
			pst.setLong(posicion++, r.getVigencia());
			pst.executeUpdate();

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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Docente Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertarFijarDocente(Rotacion r, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			ResultSet rs = null;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			pst = cn.prepareStatement(rbRot.getString("Asignar.Jerar_1_6"));
			pst.clearParameters();
			posicion = 1;

			if (l.getInstId().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(l.getInstId().trim()));
			if (r.getRotdagsede().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotdagsede().trim()));
			if (r.getRotdagjornada().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotdagjornada().trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				r.setRotdagjerjornada(rs.getString(1));
			}
			rs = null;

			for (int i = 0; i < (r.getRotdaggrados() == null ? 0 : r
					.getRotdaggrados().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.FijarDocente"));
				pst.clearParameters();
				posicion = 1;

				if (r.getRotdagjerjornada().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdagjerjornada().trim()));
				if (r.getRotdaggrados()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdaggrados()[i].trim()));
				if (r.getRotdagdocente().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdagdocente().trim()));
				if (r.getRotdagasignatura().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdagasignatura().trim()));
				if (r.getRotdaghoras().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setInt(posicion++,
							Integer.parseInt(r.getRotdaghoras().trim()));
				pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Docente-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public Rotacion asignarPrioridad(String instjerar, Login l)
			throws NumberFormatException, Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		Collection list = new ArrayList();
		Collection list2 = new ArrayList();
		Object[] o, o2;
		String consulta = "";
		try {
			cn = cursor.getConnection();
			r = new Rotacion();
			r.setEstado("");
			pst = cn.prepareStatement(rbRot.getString("Asignar.Prioridad"));
			pst.clearParameters();
			posicion = 1;

			if (instjerar.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(instjerar.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				// r=new Rotacion();
				int i = 1;
				r.setEstado("1");
				r.setInstjerar_(rs.getString(i++));
				r.setPriorizar_(rs.getString(i++));
			}
			rs = null;

			if (r.getEstado().equals("1")) {
				// traer estructura
				list = new ArrayList();
				consulta = rbRot.getString("Asignar.PrioridadEstructura");
				if (l.getSedeId() != "" && l.getJornadaId() != "") {
					consulta += " and g_jersede=? and g_jerjorn=?";
				}
				pst = cn.prepareStatement(consulta);
				pst.clearParameters();
				posicion = 1;
				if (l.getInstId().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(l.getInstId().trim()));

				if (l.getSedeId() != "" && l.getJornadaId() != "") {
					if (l.getSedeId().equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(l.getSedeId().trim()));
					if (l.getJornadaId().equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(l.getJornadaId().trim()));
				}
				rs = pst.executeQuery();
				while (rs.next()) {
					int j = 1;
					o2 = new Object[1];
					o2[0] = rs.getString(j++);
					list2.add(o2);
				}
				r.setEstructura_(getFiltroArray(list2));
				rs = null;

				// traer grados
				list = new ArrayList();
				consulta = rbRot.getString("Asignar.PrioridadGrado");
				if (l.getSedeId() != "" && l.getJornadaId() != "") {
					consulta += " and g_jersede=? and g_jerjorn=?";
				}
				pst = cn.prepareStatement(consulta);
				pst.clearParameters();
				posicion = 1;
				if (l.getInstId().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(l.getInstId().trim()));

				if (l.getSedeId() != "" && l.getJornadaId() != "") {
					if (l.getSedeId().equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(l.getSedeId().trim()));
					if (l.getJornadaId().equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(l.getJornadaId().trim()));
				}
				rs = pst.executeQuery();
				while (rs.next()) {
					int j = 1;
					o = new Object[1];
					o[0] = rs.getString(j++);
					list.add(o);
				}
				r.setGrado_(getFiltroArray(list));
				rs = null;

				// traer asignaturas
				list = new ArrayList();
				pst = cn.prepareStatement(rbRot
						.getString("Asignar.PrioridadAsignatura"));
				pst.clearParameters();
				posicion = 1;

				if (l.getInstId().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(l.getInstId().trim()));
				if (l.getMetodologiaId().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(l.getMetodologiaId().trim()));

				long vigencia = getVigenciaInst(Long.parseLong(l.getInstId()
						.trim()));
				pst.setLong(posicion++, vigencia);

				rs = pst.executeQuery();
				while (rs.next()) {
					int j = 1;
					o = new Object[1];
					o[0] = rs.getString(j++);
					list.add(o);
				}
				r.setAsignatura_(getFiltroArray(list));
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Fijar Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public String obtenerJerar_1_6(String sede, String jornada, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		String jerar = "";
		try {
			int posicion = 1;
			ResultSet rs = null;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Asignar.Jerar_1_6"));
			pst.clearParameters();
			posicion = 1;

			if (l.getInstId().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(l.getInstId().trim()));
			if (sede.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(sede.trim()));
			if (jornada.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(jornada.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				jerar = rs.getString(1);
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return "";
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return "";
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return jerar;
	}

	public String[] obtenerEspAsigGrado(Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		String[] str = new String[2];
		try {
			int posicion = 1;
			ResultSet rs = null;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Asignar.EspAsigGrado"));
			pst.clearParameters();
			posicion = 1;

			if (l.getInstId().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(l.getInstId().trim()));
			if (l.getMetodologiaId().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(l.getMetodologiaId().trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				str[0] = rs.getString(1);
				str[1] = rs.getString(2);
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return str;
	}

	public String obtenerJerar_1_4(Login l) {
		String jerar = "";
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			ResultSet rs = null;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Asignar.Jerar_1_4"));
			pst.clearParameters();
			posicion = 1;

			if (l.getInstId().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(l.getInstId().trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				jerar = rs.getString(1);
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return "";
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return "";
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return jerar;
	}

	public boolean actualizarPrioridad(Rotacion r, Rotacion r2, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			int posicion = 1;
			long instOld = Long.parseLong(!r.getInstjerar_().equals("") ? r
					.getInstjerar_() : "0");
			long prioOld = Long.parseLong(!r.getPriorizar_().equals("") ? r
					.getPriorizar_() : "0");
			long instNew = Long.parseLong(!r2.getInstjerar_().equals("") ? r2
					.getInstjerar_() : "0");
			long prioNew = Long.parseLong(!r2.getPriorizar_().equals("") ? r2
					.getPriorizar_() : "0");
			long inst = Long.parseLong(l.getInstId());
			int met = Integer.parseInt(r.getRotstrmetodologia());
			long vig = r.getVigencia();

			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (r.getEstado().equals("1")) {
				pst = cn.prepareStatement(rbRot
						.getString("Actualizar.Prioridad"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, instOld);
				pst.setLong(posicion++, prioOld);
				pst.setLong(posicion++, instNew);
				pst.setLong(posicion++, r.getVigencia());
				pst.executeUpdate();
				pst.close();
			} else {
				pst = cn.prepareStatement(rbRot.getString("Insertar.Prioridad"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, instOld);
				pst.setLong(posicion++, prioOld);
				pst.setLong(posicion++, r.getVigencia());
				pst.executeUpdate();
				pst.close();
			}
			String a[] = r.getEstructura_();
			String[] b = null;
			if (a != null) {
				for (int h = 0; h < a.length; h++) {
					pst = cn.prepareStatement(rbRot
							.getString("Actualizar.PrioridadEstructura"));
					pst.clearParameters();
					posicion = 1;
					if (a[h] == null || a[h].equals("")) {
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					} else {
						b = a[h].replace('|', ':').split(":");
						pst.setLong(posicion++, Long.parseLong(b[0]));
						pst.setLong(posicion++, Long.parseLong(b[1]));
						pst.setLong(posicion++, r.getVigencia());
					}
					pst.executeUpdate();
					pst.close();
				}
			}
			a = r.getGrado_();
			if (a != null) {
				for (int i = 0; i < a.length; i++) {
					pst = cn.prepareStatement(rbRot
							.getString("Actualizar.PrioridadGrado"));
					pst.clearParameters();
					posicion = 1;
					if (a == null || a.equals("")) {
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					} else {
						b = a[i].replace('|', ':').split(":");
						pst.setLong(posicion++, Long.parseLong(b[0]));
						pst.setLong(posicion++, Long.parseLong(b[1]));
						pst.setLong(posicion++, Long.parseLong(b[2]));
					}
					pst.executeUpdate();
					pst.close();
				}
			}
			// poner las vigencias en 0
			pst = cn.prepareStatement(rbRot
					.getString("Actualizar.PrioridadAsignaturaTodos"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, 0);
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, vig);
			pst.executeUpdate();
			pst.close();
			// poner solo las 2 asignaturas escogidas
			pst = cn.prepareStatement(rbRot
					.getString("Actualizar.PrioridadAsignatura"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, 2);
			pst.setLong(posicion++, Long.parseLong(r.getPrioAsig1()));
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, vig);
			pst.executeUpdate();
			pst.close();
			// poner solo las 2 asignaturas escogidas
			pst = cn.prepareStatement(rbRot
					.getString("Actualizar.PrioridadAsignatura"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, 1);
			pst.setLong(posicion++, Long.parseLong(r.getPrioAsig2()));
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, vig);
			pst.executeUpdate();
			pst.close();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Prioridad. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;

	}

	public boolean insertarEstructura(Rotacion r, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long inst = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(r.getRotstrsede());
			long jor = Long.parseLong(r.getRotstrjornada());
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Asignar.Jerar_1_6"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			rs = pst.executeQuery();
			if (rs.next()) {
				r.setRotstrjercodigo(rs.getString(1));
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rbRot.getString("Insertar.Estructura"));
			pst.clearParameters();
			pst.setString(posicion++, r.getRotstrnombre().trim());
			if (r.getRotstrdurblq().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrdurblq().trim()));
			if (r.getRotstrdurhor().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrdurhor().trim()));
			if (r.getRotstrnumblq().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrnumblq().trim()));
			if (r.getRotstrsemcic().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrsemcic().trim()));
			if (r.getRotstrhoraini().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotstrhoraini().trim());
			if (r.getRotstrhorafin().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotstrhorafin().trim());
			pst.setLong(posicion++,
					Long.parseLong(r.getRotstrjercodigo().trim()));
			pst.setInt(posicion++, r.getVigencia());
			pst.setInt(posicion++, Integer.parseInt(r.getRotstrmetodologia()));
			pst.executeUpdate();
			pst.close();

			for (int i = 0; i < (r.getRtesgrgrado() == null ? 0 : r
					.getRtesgrgrado().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.EstructuraGrado"));
				pst.clearParameters();
				posicion = 1;

				if (r.getRtesgrgrado()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRtesgrgrado()[i].trim()));
				pst.executeUpdate();
				pst.close();
			}

			for (int j = 0; j < (r.getRotrec_tiprec() == null ? 0 : r
					.getRotrec_tiprec().length); j++) {
				if (!r.getRotrec_tiprec()[j].equals("-1")) {
					pst = cn.prepareStatement(rbRot
							.getString("Insertar.Receso"));
					pst.clearParameters();
					posicion = 1;

					if (r.getRotrechoraini()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setString(posicion++,
								r.getRotrechoraini()[j].trim());
					if (r.getRotrechorafin()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setString(posicion++,
								r.getRotrechorafin()[j].trim());
					if (r.getRotrec_tiprec()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(r.getRotrec_tiprec()[j].trim()));
					if (r.getRotrecdescripcion()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setString(posicion++,
								r.getRotrecdescripcion()[j].trim());
					pst.executeUpdate();
					pst.close();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Tipo de Receso. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarFijarEspacio(Rotacion r, Rotacion r2, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Eliminar.FijarEspacio"));
			pst.clearParameters();
			posicion = 1;

			if (r2.getRoteagjerjornada().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRoteagjerjornada().trim()));
			if (r2.getRoteagespacio().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRoteagespacio().trim()));
			if (r2.getRoteagasignatura().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRoteagasignatura().trim()));
			pst.setInt(posicion++, r.getVigencia());
			pst.setInt(posicion++, Integer.parseInt(r.getRotstrmetodologia()));
			pst.executeUpdate();
			pst.close();
			for (int i = 0; i < (r.getRoteaggrados() == null ? 0 : r
					.getRoteaggrados().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.FijarEspacio"));
				pst.clearParameters();
				posicion = 1;

				if (r.getRoteagjerjornada().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteagjerjornada().trim()));
				if (r.getRoteaggrados()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteaggrados()[i].trim()));
				if (r.getRoteagespacio().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteagespacio().trim()));
				if (r.getRoteagasignatura().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteagasignatura().trim()));
				if (r.getRoteagexclusivo().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRoteagexclusivo().trim()));
				pst.setInt(posicion++, r.getVigencia());
				pst.setInt(posicion++,
						Integer.parseInt(r.getRotstrmetodologia()));
				pst.executeUpdate();
				pst.close();
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información de Espacio-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarInhEspacios(Rotacion r, Rotacion r2) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Actualizar.InhEspacios"));
			pst.clearParameters();

			if (r.getRotefjestructura().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotefjestructura().trim()));
			if (r.getRotefjespacio().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotefjespacio().trim()));
			if (r.getRotefjhoraini().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotefjhoraini().trim());
			if (r.getRotefjhorafin().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotefjhorafin().trim());
			if (r.getRotefjcausa().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotefjcausa().trim());
			if (r.getRotefjdia().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(r.getRotefjdia().trim()));
			if (r2.getRotefjestructura().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRotefjestructura().trim()));
			if (r2.getRotefjespacio().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRotefjespacio().trim()));

			pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información de Espacio Fnsicos por Jornada. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarInhDocente(Rotacion r, Rotacion r2) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Actualizar.InhDocente"));
			pst.clearParameters();
			long estOld = Long.parseLong(r2.getRotihdestructura());
			long docOld = Long.parseLong(r2.getRotihddocente());
			long hiOld = Long.parseLong(r2.getRotihdhoraini());
			long hfOld = Long.parseLong(r2.getRotihdhorafin());
			long diaOld = Long.parseLong(r2.getRotihddia());

			long est = Long.parseLong(r.getRotihdestructura());
			long doc = Long.parseLong(r.getRotihddocente());
			long hi = Long.parseLong(r.getRotihdhoraini());
			long hf = Long.parseLong(r.getRotihdhorafin());
			long dia = Long.parseLong(r.getRotihddia());
			String c = r.getRotihdcausa();

			pst.setLong(posicion++, est);
			pst.setLong(posicion++, doc);
			pst.setLong(posicion++, hi);
			pst.setLong(posicion++, hf);
			pst.setString(posicion++, c);
			pst.setLong(posicion++, dia);
			pst.setLong(posicion++, estOld);
			pst.setLong(posicion++, docOld);
			pst.setLong(posicion++, hiOld);
			pst.setLong(posicion++, hfOld);
			pst.setLong(posicion++, diaOld);
			pst.executeUpdate();

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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información de Docente Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarInhHora(Rotacion r, Rotacion r2) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Actualizar.Inhhora"));
			pst.clearParameters();
			long estOld = Long.parseLong(r2.getRotihestructura());
			long hiOld = Long.parseLong(r2.getRotihhoraini());
			long hfOld = Long.parseLong(r2.getRotihhorafin());
			long diaOld = Long.parseLong(r2.getRotihdia());

			long est = Long.parseLong(r.getRotihestructura());
			long hi = Long.parseLong(r.getRotihhoraini());
			long hf = Long.parseLong(r.getRotihhorafin());
			long dia = Long.parseLong(r.getRotihdia());
			String c = r.getRotihcausa();

			pst.setLong(posicion++, est);
			pst.setLong(posicion++, hi);
			pst.setLong(posicion++, hf);
			pst.setString(posicion++, c);
			pst.setLong(posicion++, dia);
			pst.setLong(posicion++, estOld);
			pst.setLong(posicion++, hiOld);
			pst.setLong(posicion++, hfOld);
			pst.setLong(posicion++, diaOld);
			pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información de HOra Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarFijarEspacioDocente(Rotacion r, Rotacion r2) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot
					.getString("Actualizar.FijarEspacioDocente"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(r.getRtefdoespacio().trim()));
			pst.setLong(posicion++, Long.parseLong(r.getRtefdodocente().trim()));
			pst.setString(posicion++, r.getRtefdojercodigo().trim());
			// w
			pst.setLong(posicion++,
					Long.parseLong(r2.getRtefdoespacio().trim()));
			pst.setLong(posicion++,
					Long.parseLong(r2.getRtefdodocente().trim()));
			pst.setLong(posicion++,
					Long.parseLong(r2.getRtefdojercodigo().trim()));
			pst.setInt(posicion++, r.getVigencia());
			pst.executeUpdate();
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información Espacio por Docente. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarFijarDocente(Rotacion r, Rotacion r2) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			pst = cn.prepareStatement(rbRot.getString("Eliminar.FijarDocente"));
			pst.clearParameters();
			posicion = 1;

			if (r2.getRotdagjerjornada().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRotdagjerjornada().trim()));
			if (r2.getRotdagdocente().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRotdagdocente().trim()));
			if (r2.getRotdagasignatura().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r2.getRotdagasignatura().trim()));
			pst.executeUpdate();

			for (int i = 0; i < (r.getRotdaggrados() == null ? 0 : r
					.getRotdaggrados().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.FijarDocente"));
				pst.clearParameters();
				posicion = 1;

				if (r.getRotdagjerjornada().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdagjerjornada().trim()));
				if (r.getRotdaggrados()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdaggrados()[i].trim()));
				if (r.getRotdagdocente().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdagdocente().trim()));
				if (r.getRotdagasignatura().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotdagasignatura().trim()));
				if (r.getRotdaghoras().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setInt(posicion++,
							Integer.parseInt(r.getRotdaghoras().trim()));
				pst.executeUpdate();
			}

			/*
			 * pst=cn.prepareStatement(rbRot.getString("Actualizar.FijarDocente")
			 * ); pst.clearParameters(); posicion=1;
			 * 
			 * if(r.getRotdagjerjornada().equals(""))
			 * pst.setNull(posicion++,java.sql.Types.NULL); else
			 * pst.setLong(posicion
			 * ++,Long.parseLong(r.getRotdagjerjornada().trim()));
			 * if(r.getRotdagdocente().equals(""))
			 * pst.setNull(posicion++,java.sql.Types.NULL); else
			 * pst.setLong(posicion
			 * ++,Long.parseLong(r.getRotdagdocente().trim()));
			 * if(r.getRotdagasignatura().equals(""))
			 * pst.setNull(posicion++,java.sql.Types.NULL); else
			 * pst.setLong(posicion
			 * ++,Long.parseLong(r.getRotdagasignatura().trim()));
			 * if(r2.getRotdagjerjornada().equals(""))
			 * pst.setNull(posicion++,java.sql.Types.NULL); else
			 * pst.setLong(posicion
			 * ++,Long.parseLong(r2.getRotdagjerjornada().trim()));
			 * if(r2.getRotdagdocente().equals(""))
			 * pst.setNull(posicion++,java.sql.Types.NULL); else
			 * pst.setLong(posicion
			 * ++,Long.parseLong(r2.getRotdagdocente().trim()));
			 * if(r2.getRotdagasignatura().equals(""))
			 * pst.setNull(posicion++,java.sql.Types.NULL); else
			 * pst.setLong(posicion
			 * ++,Long.parseLong(r2.getRotdagasignatura().trim()));
			 * 
			 * pst.executeUpdate();
			 */
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información de Docente-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarFijarAsignatura(Rotacion r, Rotacion r2) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot
					.getString("Eliminar.FijarAsignatura"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++,
					Long.parseLong(r2.getRotfasestructura().trim()));
			pst.setLong(posicion++,
					Long.parseLong(r2.getRotfasasignatura().trim()));
			pst.executeUpdate();
			for (int i = 0; i < (r.getRotfasgrados() == null ? 0 : r
					.getRotfasgrados().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.FijarAsignatura"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++,
						Long.parseLong(r.getRotfasestructura().trim()));
				pst.setLong(posicion++,
						Long.parseLong(r.getRotfasasignatura().trim()));
				if (r.getRotfasgrados()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRotfasgrados()[i].trim()));
				pst.setLong(posicion++,
						Long.parseLong(r.getRotfasblqminimo().trim()));
				pst.executeUpdate();
				pst.close();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información de Fijar Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarEstructura(Rotacion r, Rotacion r2) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			for (int i = 0; i < (r2.getRotrec_tiprec() == null ? 0 : r2
					.getRotrec_tiprec().length); i++) {
				if (!r2.getRotrec_tiprec()[i].equals("-1")) {
					pst = cn.prepareStatement(rbRot
							.getString("Eliminar.Receso"));
					pst.clearParameters();
					posicion = 1;

					if (r2.getRotrec_strcod()[i].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(r2.getRotrec_strcod()[i].trim()));
					pst.executeUpdate();
				}
			}

			for (int j = 0; j < (r.getRotrec_tiprec() == null ? 0 : r
					.getRotrec_tiprec().length); j++) {
				if (!r.getRotrec_tiprec()[j].equals("-1")) {
					pst = cn.prepareStatement(rbRot
							.getString("Insertar.Receso2"));
					pst.clearParameters();
					posicion = 1;

					if (r2.getRotstrcodigo().equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(r2.getRotstrcodigo().trim()));
					if (r.getRotrechoraini()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setString(posicion++,
								r.getRotrechoraini()[j].trim());
					if (r.getRotrechorafin()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setString(posicion++,
								r.getRotrechorafin()[j].trim());
					if (r.getRotrec_tiprec()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setLong(posicion++,
								Long.parseLong(r.getRotrec_tiprec()[j].trim()));
					if (r.getRotrecdescripcion()[j].equals(""))
						pst.setNull(posicion++, java.sql.Types.NULL);
					else
						pst.setString(posicion++,
								r.getRotrecdescripcion()[j].trim());
					pst.executeUpdate();
				}
			}

			for (int i = 0; i < (r2.getRtesgrgrado() == null ? 0 : r2
					.getRtesgrgrado().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Eliminar.EstructuraGrado"));
				pst.clearParameters();
				posicion = 1;

				if (r2.getRotstrcodigo().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r2.getRotstrcodigo().trim()));
				if (r2.getRtesgrgrado()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r2.getRtesgrgrado()[i].trim()));
				pst.executeUpdate();
			}

			for (int i = 0; i < (r.getRtesgrgrado() == null ? 0 : r
					.getRtesgrgrado().length); i++) {
				pst = cn.prepareStatement(rbRot
						.getString("Insertar.EstructuraGrado2"));
				pst.clearParameters();
				posicion = 1;

				if (r2.getRotstrcodigo().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r2.getRotstrcodigo().trim()));
				if (r.getRtesgrgrado()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRtesgrgrado()[i].trim()));
				pst.executeUpdate();
			}

			pst = cn.prepareStatement(rbRot.getString("Actualizar.Estructura"));
			pst.clearParameters();
			posicion = 1;
			pst.setString(posicion++, r.getRotstrnombre().trim());
			if (r.getRotstrdurblq().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrdurblq().trim()));
			if (r.getRotstrdurhor().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrdurhor().trim()));
			if (r.getRotstrnumblq().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrnumblq().trim()));
			if (r.getRotstrsemcic().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrsemcic().trim()));
			if (r.getRotstrhoraini().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotstrhoraini().trim());
			if (r.getRotstrhorafin().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, r.getRotstrhorafin().trim());
			pst.setLong(posicion++,
					Long.parseLong(r.getRotstrjercodigo().trim()));
			pst.setInt(posicion++, r.getVigencia());
			// where
			pst.setLong(posicion++, Long.parseLong(r2.getRotstrcodigo().trim()));
			pst.executeUpdate();
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
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando actualizar información de Estructura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public Rotacion asignarFijarEspacio(String id, String id2, String id3,
			String id4, String id5, String inst) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		Collection list = new ArrayList();
		Object[] o;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Asignar.FijarEspacio"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.setLong(posicion++, Long.parseLong(id3.trim()));
			pst.setInt(posicion++, Integer.parseInt(id4.trim()));
			pst.setInt(posicion++, Integer.parseInt(id5.trim()));
			rs = pst.executeQuery();
			// int inicio = 0;
			int i = 1;
			if (rs.next()) {
				i = 1;
				r = new Rotacion();
				r.setEstado("1");
				r.setRoteagjerjornada(rs.getString(i++));
				r.setRoteagespacio(rs.getString(i++));
				r.setRoteagasignatura(rs.getString(i++));
				r.setRoteagexclusivo(rs.getString(i++));
				r.setRoteagsede(rs.getString(i++));
				r.setRoteagjornada(rs.getString(i++));
				r.setVigencia(rs.getInt(i++));
				r.setRotstrmetodologia(rs.getString(i++));
			} else {
				setMensaje("No se encontro el registro");
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rbRot
					.getString("Asignar.FijarEspacioGrado"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.setLong(posicion++, Long.parseLong(id3.trim()));
			pst.setInt(posicion++, Integer.parseInt(id4.trim()));
			pst.setInt(posicion++, Integer.parseInt(id5.trim()));
			rs = pst.executeQuery();
			while (rs.next()) {
				int j = 1;
				o = new Object[1];
				o[0] = rs.getString(j++);
				list.add(o);
			}
			r.setRoteaggrados(getFiltroArray(list));
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Espacio-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public Rotacion asignarFijarEspacioDocente(String id, String id2,
			String id3, String id4) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("Asignar.FijarEspacioDocente"));
			pst.clearParameters();
			posicion = 1;

			pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.setLong(posicion++, Long.parseLong(id3.trim()));
			pst.setLong(posicion++, Long.parseLong(id4.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				r = new Rotacion();
				int i = 1;
				r.setEstado("1");
				r.setRtefdoespacio(rs.getString(i++));
				r.setRtefdodocente(rs.getString(i++));
				r.setRtefdojercodigo(rs.getString(i++));
				r.setRtefdosede(rs.getString(i++));
				r.setRtefdojornada(rs.getString(i++));
				r.setVigencia(rs.getInt(i++));
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Espacio por Docente. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public Rotacion asignarInhEspacios(String[] aa) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			long id = Long.parseLong(aa[0]);
			long id2 = Long.parseLong(aa[1]);
			long id3 = Long.parseLong(aa[2]);
			long id4 = Long.parseLong(aa[3]);
			long id5 = Long.parseLong(aa[4]);
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Asignar.InhEspacios"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, id);
			pst.setLong(posicion++, id2);
			pst.setLong(posicion++, id3);
			pst.setLong(posicion++, id4);
			pst.setLong(posicion++, id5);
			rs = pst.executeQuery();
			if (rs.next()) {
				r = new Rotacion();
				int i = 1;
				r.setEstado("1");
				r.setRotefjestructura(rs.getString(i++));
				r.setRotefjespacio(rs.getString(i++));
				r.setRotefjhoraini(rs.getString(i++));
				r.setRotefjhorafin(rs.getString(i++));
				r.setRotefjcausa(rs.getString(i++));
				r.setRotefjdia(rs.getString(i++));
			} else {
				setMensaje("El registro no se encuentra");
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Espacio Fnsico - Jornada. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public Rotacion asignarInhDocente(String[] aa) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			long id = Long.parseLong(aa[0]);
			long id2 = Long.parseLong(aa[1]);
			long id3 = Long.parseLong(aa[2]);
			long id4 = Long.parseLong(aa[3]);
			long id5 = Long.parseLong(aa[4]);
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Asignar.InhDocente"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, id);
			pst.setLong(posicion++, id2);
			pst.setLong(posicion++, id3);
			pst.setLong(posicion++, id4);
			pst.setLong(posicion++, id5);
			rs = pst.executeQuery();
			if (rs.next()) {
				r = new Rotacion();
				int i = 1;
				r.setEstado("1");
				r.setRotihdestructura(rs.getString(i++));
				r.setRotihddocente(rs.getString(i++));
				r.setRotihddia(rs.getString(i++));
				r.setRotihdhoraini(rs.getString(i++));
				r.setRotihdhorafin(rs.getString(i++));
				r.setRotihdcausa(rs.getString(i++));
				r.setRotihdsede(rs.getString(i++));
				r.setRotihdjornada(rs.getString(i++));
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Docente Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public Rotacion asignarInhHora(String[] id) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			long est = Long.parseLong(id[0]);
			long dia = Long.parseLong(id[1]);
			long hi = Long.parseLong(id[2]);
			long hf = Long.parseLong(id[3]);
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Asignar.InhHora"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, est);
			pst.setLong(posicion++, dia);
			pst.setLong(posicion++, hi);
			pst.setLong(posicion++, hf);
			rs = pst.executeQuery();
			if (rs.next()) {
				r = new Rotacion();
				int i = 1;
				r.setEstado("1");
				r.setRotihestructura(rs.getString(i++));
				r.setRotihhoraini(rs.getString(i++));
				r.setRotihhorafin(rs.getString(i++));
				r.setRotihcausa(rs.getString(i++));
				r.setRotihdia(rs.getString(i++));
			} else {
				setMensaje("NO se encontro el registro");
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Hora Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public Rotacion asignarFijarAsignatura(String id, String id2) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		String str = null, asig = null;
		String blq = null;
		Collection list = new ArrayList();
		Object[] o;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("Asignar.FijarAsignatura"));
			pst.clearParameters();
			posicion = 1;

			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			rs = pst.executeQuery();
			while (rs.next()) {
				r = new Rotacion();
				int i = 1;
				r.setEstado("1");
				str = rs.getString(i++);
				asig = rs.getString(i++);
				blq = rs.getString(i++);
				// sede=rs.getString(i++);
				// jorn=rs.getString(i++);
				o = new Object[1];
				o[0] = rs.getString(i++);
				list.add(o);
			}

			r.setRotfasestructura(str);
			r.setRotfasasignatura(asig);
			r.setRotfasblqminimo(blq);
			// r.setRotfassede(sede);
			// r.setRotfasjornada(jorn);
			r.setRotfasgrados(getFiltroArray(list));
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Fijar Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public Rotacion asignarFijarDocente(String id, String id2, String id3,
			String inst) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1;
		Collection list = new ArrayList();
		Object[] o;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Asignar.FijarDocente"));
			pst.clearParameters();
			posicion = 1;

			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			if (id3.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id3.trim()));
			// System.out.println("VALE: "+id+" - "+id2+" - "+id3);
			rs = pst.executeQuery();
			if (rs.next()) {
				r = new Rotacion();
				int i = 1;
				r.setEstado("1");
				r.setRotdagjerjornada(rs.getString(i++));
				r.setRotdagdocente(rs.getString(i++));
				r.setRotdagasignatura(rs.getString(i++));
				r.setRotdagsede(rs.getString(i++));
				r.setRotdagjornada(rs.getString(i++));
			}
			rs = null;

			pst = cn.prepareStatement(rbRot
					.getString("Asignar.FijarDocenteGrado"));
			pst.clearParameters();
			posicion = 1;

			if (inst.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(inst.trim()));
			if (r.getRotdagsede().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotdagsede().trim()));
			if (r.getRotdagjornada().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotdagjornada().trim()));
			rs = pst.executeQuery();

			while (rs.next()) {
				int j = 1;
				o = new Object[1];
				o[0] = rs.getString(j++);
				list.add(o);
				r.setRotdaghoras(rs.getString(j++));
			}
			r.setRotdaggrados(getFiltroArray(list));
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Docente-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public boolean validarInhHora(Rotacion r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			long est = Long.parseLong(r.getRotihestructura());
			long hi = Long.parseLong(r.getRotihhoraini());
			long hf = Long.parseLong(r.getRotihhorafin());
			long dia = Long.parseLong(r.getRotihdia());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("validar.InhabilidadHora"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, est);
			pst.setLong(posicion++, dia);
			pst.setLong(posicion++, hi);
			pst.setLong(posicion++, hf);
			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando validar horas inhabilitadas. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public boolean validarIHEstructura(Rotacion r, Login l)
			throws NumberFormatException, Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		int cant = -1;
		try {
			cn = cursor.getConnection();
			for (i = 0; i < r.getRtesgrgrado().length; i++) {
				pst = cn.prepareStatement(rbRot
						.getString("validar.IHEstructura"));
				pst.clearParameters();
				posicion = 1;
				if (l.getInstId().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(l.getInstId().trim()));
				if (l.getMetodologiaId().equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(l.getMetodologiaId().trim()));
				if (r.getRtesgrgrado()[i].equals(""))
					pst.setNull(posicion++, java.sql.Types.NULL);
				else
					pst.setLong(posicion++,
							Long.parseLong(r.getRtesgrgrado()[i].trim()));
				long vigencia = getVigenciaInst(Long.parseLong(l.getInstId()
						.trim()));

				pst.setLong(posicion++, vigencia);
				rs = pst.executeQuery();
				if (rs.next()) {
					cant = rs.getInt(1);
				}
				// System.out.println("MIRA: "+cant+" >
				// "+(Integer.parseInt(r.getRotstrihtotal())*5));
				if (cant != -1) {
					if (cant > (Integer.parseInt(r.getRotstrihtotal()) * 5)) {
						return false;
					}
				}
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean validaGradoEstructura(Rotacion r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1, i = 1, j;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("validar.GradoEstructura"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++,
					Long.parseLong(r.getRotstrjercodigo().trim()));
			if (r.getRotstrcodigo().equals(""))
				pst.setLong(posicion++, -1);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrcodigo().trim()));
			pst.setInt(posicion++, r.getVigencia());
			pst.setInt(posicion++, Integer.parseInt(r.getRotstrmetodologia()));
			rs = pst.executeQuery();
			Collection list = new ArrayList();
			Object[] o;
			// System.out.println("jer: "+r.getRotstrjercodigo());
			while (rs.next()) {
				i = 1;
				o = new Object[1];
				o[0] = rs.getString(i++);
				// System.out.println("grd: "+o[0]);
				list.add(o);
			}
			r.setRtesgrvalidar(getFiltroArray(list));

			if (r.getRtesgrvalidar() != null) {
				for (i = 0; i < r.getRtesgrgrado().length; i++) {
					for (j = 0; j < r.getRtesgrvalidar().length; j++) {
						if (r.getRtesgrgrado()[i]
								.equals(r.getRtesgrvalidar()[j])) {
							return false;
						}
					}
				}
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public Rotacion asignarEstructura(String id) {
		Connection cn = null;
		PreparedStatement pst = null;
		Rotacion r = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Asignar.Estructura"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				r = new Rotacion();
				i = 1;
				r.setEstado("1");
				r.setRotstrcodigo(rs.getString(i++));
				r.setRotstrnombre(rs.getString(i++));
				r.setRotstrdurblq(rs.getString(i++));
				r.setRotstrdurhor(rs.getString(i++));
				r.setRotstrnumblq(rs.getString(i++));
				r.setRotstrsemcic(rs.getString(i++));
				r.setRotstrhoraini(rs.getString(i++));
				r.setRotstrhorafin(rs.getString(i++));
				r.setRotstrjercodigo(rs.getString(i++));
				r.setVigencia(rs.getInt(i++));
			}

			rs = null;
			pst = cn.prepareStatement(rbRot.getString("Buscar.Jerarquia_1_6"));
			pst.clearParameters();
			posicion = 1;

			if (r.getRotstrjercodigo().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(r.getRotstrjercodigo().trim()));
			rs = pst.executeQuery();

			if (rs.next()) {
				r.setRotstrsede(rs.getString(2));
				r.setRotstrjornada(rs.getString(3));
			}

			rs = null;
			pst = cn.prepareStatement(rbRot
					.getString("Asignar.EstructuraGrado"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			Collection list = new ArrayList();
			Object[] o;
			while (rs.next()) {
				i = 1;
				o = new Object[1];
				o[0] = rs.getString(i++);
				list.add(o);
			}
			r.setRtesgr_strcod(r.getRotstrcodigo());
			r.setRtesgrgrado(getFiltroArray(list));

			rs = null;
			pst = cn.prepareStatement(rbRot
					.getString("Asignar.EstructuraReceso"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			Collection listA = new ArrayList();
			Object[] oA;
			Collection listB = new ArrayList();
			Object[] oB;
			Collection listC = new ArrayList();
			Object[] oC;
			Collection listD = new ArrayList();
			Object[] oD;
			Collection listE = new ArrayList();
			Object[] oE;
			Collection listF = new ArrayList();
			Object[] oF;
			while (rs.next()) {
				i = 1;
				oA = new Object[1];
				oA[0] = rs.getString(i++);
				listA.add(oA);
				oB = new Object[1];
				oB[0] = rs.getString(i++);
				listB.add(oB);
				oC = new Object[1];
				oC[0] = rs.getString(i++);
				listC.add(oC);
				oD = new Object[1];
				oD[0] = rs.getString(i++);
				listD.add(oD);
				oE = new Object[1];
				oE[0] = rs.getString(i++);
				listE.add(oE);
				oF = new Object[1];
				oF[0] = rs.getString(i++);
				listF.add(oF);
			}
			r.setRotrechoraini(getFiltroArray(listA));
			r.setRotrechorafin(getFiltroArray(listB));
			r.setRotrec_tiprec(getFiltroArray(listC));
			r.setRotrecdescripcion(getFiltroArray(listD));
			r.setRotreccodigo(getFiltroArray(listE));
			r.setRotrec_strcod(getFiltroArray(listF));
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de la Estructura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public boolean eliminarFijarEspacioDocente(String id, String id2,
			String id3, String id4) {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot
					.getString("Eliminar.FijarEspacioDocente"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.setLong(posicion++, Long.parseLong(id3.trim()));
			pst.setLong(posicion++, Long.parseLong(id4.trim()));
			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Espacio por Docente. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarFijarEspacio(String id, String id2, String id3,
			String id4, int met) {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Eliminar.FijarEspacio"));
			pst.clearParameters();
			posicion = 1;

			pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.setLong(posicion++, Long.parseLong(id3.trim()));
			pst.setLong(posicion++, Long.parseLong(id4.trim()));
			pst.setInt(posicion++, met);
			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Espacio-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarInhEspacios(String aa[]) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long id = Long.parseLong(aa[0]);
			long id2 = Long.parseLong(aa[1]);
			long id3 = Long.parseLong(aa[2]);
			long id4 = Long.parseLong(aa[3]);
			long id5 = Long.parseLong(aa[4]);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Eliminar.InhEspacios"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, id);
			pst.setLong(posicion++, id2);
			pst.setLong(posicion++, id3);
			pst.setLong(posicion++, id4);
			pst.setLong(posicion++, id5);
			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Espacio-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarInhDocente(String[] aa) {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			long id = Long.parseLong(aa[0]);
			long id2 = Long.parseLong(aa[1]);
			long id3 = Long.parseLong(aa[2]);
			long id4 = Long.parseLong(aa[3]);
			long id5 = Long.parseLong(aa[4]);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Eliminar.InhDocente"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, id);
			pst.setLong(posicion++, id2);
			pst.setLong(posicion++, id3);
			pst.setLong(posicion++, id4);
			pst.setLong(posicion++, id5);
			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Docente Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarInhHora(String[] aa) {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			long id = Long.parseLong(aa[0]);
			long id2 = Long.parseLong(aa[1]);
			long id3 = Long.parseLong(aa[2]);
			long id4 = Long.parseLong(aa[3]);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Eliminar.InhHora"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, id);
			pst.setLong(posicion++, id2);
			pst.setLong(posicion++, id3);
			pst.setLong(posicion++, id4);
			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Docente Inhabilitado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarFijarAsignatura(String id, String id2) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot
					.getString("Eliminar.FijarAsignatura"));
			pst.clearParameters();
			posicion = 1;

			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));

			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Fijar Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarFijarDocente(String id, String id2, String id3) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Eliminar.FijarDocente"));
			pst.clearParameters();
			posicion = 1;

			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			if (id3.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id3.trim()));

			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Docente-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarEstructura(String id) {
		System.out.println("eliminarEstructura");
		System.out.println("id " + id);

		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		ResultSet rs = null;
		StringBuffer strMsg = new StringBuffer();
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			pst = cn.prepareStatement(rbRot.getString("Eliminar.Consultarief"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				strMsg.append("\n    - Existe información en 'Inhabilitar Espacios Fnsicos' que esta relacionada a la estructura.");

			}
			rs = null;

			pst = cn.prepareStatement(rbRot.getString("Eliminar.Consultaridoc"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				strMsg.append("\n    - Existen docentes deshabilitados para esta estructura.");
			}

			// ROT_FIJAR_ASIGNATURA
			rs = null;

			pst = cn.prepareStatement(rbRot.getString("Eliminar.ConsultarAsig"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				strMsg.append("\n    - Existe información en 'Bloque Inicial por Asignatura' que esta relacionada a la estructura.");
			}

			// Eliminar.ConsultarInHabliHora
			pst = cn.prepareStatement(rbRot
					.getString("Eliminar.ConsultarInHabliHora"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				strMsg.append("\n    - Existe información en 'Inhabilitar Horas' que esta relacionada a la estructura.");
			}

			if (strMsg.toString().length() > 0) {
				strMsg.insert(0,
						"No es posible eliminar este registro, existen registros asociados: ");
				setMensaje(strMsg.toString());
				System.out.println("No se puede eliminar " + getMensaje());
				return false;
			}
			rs = null;

			pst = cn.prepareStatement(rbRot.getString("Eliminar.Receso"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals("")) {
				pst.setNull(posicion++, java.sql.Types.NULL);
			} else {
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			}
			pst.executeUpdate();

			pst = cn.prepareStatement(rbRot
					.getString("Eliminar.EstructuraGrado2"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.executeUpdate();

			pst = cn.prepareStatement(rbRot.getString("Eliminar.Estructura"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.executeUpdate();

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
			setMensaje("Error SQL intentando ELIMINAR Estructura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
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

	public List getAjaxDocente(long inst, int sed, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		DocenteVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAjaxDocente"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sed);
			st.setInt(i++, jor);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new DocenteVO();
				lp.setCodigo(rs.getLong(i++));
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

	public List getAllSede(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		SedeVO lp = null;
		int i = 0;
		try {
			// System.out.println("Calculo de sedes"+inst+"::"+sede);
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAllSede"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new SedeVO();
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

	public List getAllJornada(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		JornadaVO lp = null;
		int i = 0;
		try {
			// System.out.println("Calculo de jor"+inst+"::"+sede+"::"+jor);
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAllJornada"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new JornadaVO();
				lp.setSede(rs.getInt(i++));
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

	public List getAjaxAsignatura(long inst, int sed, int jor, int met,
			long doc, long vig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		AsignaturaVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAjaxAsignatura"));
			i = 1;
			st.setLong(i++, doc);
			st.setLong(i++, inst);
			st.setInt(i++, sed);
			st.setInt(i++, jor);
			st.setInt(i++, met);
			st.setLong(i++, vig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new AsignaturaVO();
				lp.setCodigo(rs.getLong(i++));
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

	public List getAjaxGrado(long inst, int sed, int jor, int met, long doc,
			long asig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		GradoVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAjaxGrado"));
			i = 1;
			st.setLong(i++, doc);
			st.setLong(i++, asig);
			st.setLong(i++, inst);
			st.setInt(i++, sed);
			st.setInt(i++, jor);
			st.setInt(i++, met);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new GradoVO();
				lp.setCodigo(rs.getLong(i++));
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

	public List getAjaxGrupo(long inst, int sed, int jor, int met, long doc,
			long asig, long gra) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		GrupoVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAjaxGrupo"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sed);
			st.setInt(i++, jor);
			st.setInt(i++, met);
			st.setLong(i++, gra);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new GrupoVO();
				lp.setCodigo(rs.getLong(i++));
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

	public List getAllDocenteGrupo(FiltroDocXGrupoVO filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		DocXGrupoVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAllDocenteGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilDocente());
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setLong(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("trae la lista de docenteGrupo");
				i = 1;
				lp = new DocXGrupoVO();
				lp.setDocGruJerGrado(rs.getLong(i++));
				lp.setDocGruDocente(rs.getLong(i++));
				lp.setDocGruAsignatura(rs.getInt(i++));
				lp.setDocGruNomGrado(rs.getString(i++));
				lp.setDocGruNomAsignatura(rs.getString(i++));
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

	public DocXGrupoVO getDocenteGrupo(long jerGrado, long doc, long asig,
			long vig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DocXGrupoVO lp = null;
		List l = new ArrayList();
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getDocXGrupo"));
			i = 1;
			st.setLong(i++, jerGrado);
			st.setLong(i++, doc);
			st.setLong(i++, asig);
			st.setLong(i++, vig);
			rs = st.executeQuery();
			int it = 0;
			while (rs.next()) {
				i = 1;
				if (it == 0) {
					lp = new DocXGrupoVO();
					lp.setDocGruJerGrado(rs.getLong(i++));
					lp.setDocGruInstitucion(rs.getLong(i++));
					lp.setDocGruSede(rs.getInt(i++));
					lp.setDocGruJornada(rs.getInt(i++));
					lp.setDocGruMetodologia(rs.getInt(i++));
					lp.setDocGruGrado(rs.getInt(i++));
					lp.setDocGruDocente(rs.getLong(i++));
					lp.setDocGruAsignatura(rs.getInt(i++));
					lp.setDocGruVigencia((int) vig);
					l.add(rs.getString(i++));
					lp.setFormaEstado("1");
					it++;
				} else {
					l.add(rs.getString(9));
				}
			}
			if (lp != null) {
				long[] lista = new long[l.size()];
				for (i = 0; i < l.size(); i++) {
					lista[i] = Long.parseLong((String) l.get(i));
				}
				lp.setDocGruGrupo(lista);
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
		return lp;
	}

	//
	public boolean actualizarDocenteGrupo(DocXGrupoVO doc) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("docenteXGrupo.Eliminar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, doc.getDocGruJerGrado());
			pst.setLong(posicion++, doc.getDocGruDocente());
			pst.setLong(posicion++, doc.getDocGruAsignatura());
			pst.setLong(posicion++, doc.getDocGruVigencia());
			pst.executeUpdate();
			pst.close();
			long[] gru = doc.getDocGruGrupo();
			if (gru != null) {
				pst = cn.prepareStatement(rbRot
						.getString("docenteXGrupo.Insertar"));
				pst.clearBatch();
				for (int i = 0; i < gru.length; i++) {
					posicion = 1;
					pst.setLong(posicion++, doc.getDocGruJerGrado());
					pst.setLong(posicion++, doc.getDocGruDocente());
					pst.setLong(posicion++, doc.getDocGruAsignatura());
					pst.setLong(posicion++, gru[i]);
					pst.setLong(posicion++, doc.getDocGruVigencia());
					pst.addBatch();
				}
				pst.executeBatch();
				pst.close();
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(
					"NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			switch (sqle.getErrorCode()) {
			// Violacinn de llave primaria
			case 1:
			case 2291:
				setMensaje("Ya existe una asignacinn del mismo grado - asignatura");
				return false;
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			throw new Exception(getMensaje());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean ingresarDocenteGrupo(DocXGrupoVO doc) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// calcular la jerarquia de grado
			pst = cn.prepareStatement(rbRot
					.getString("docenteXGrupo.getJerarquia"));
			posicion = 1;
			pst.setLong(posicion++, doc.getDocGruInstitucion());
			pst.setLong(posicion++, doc.getDocGruSede());
			pst.setLong(posicion++, doc.getDocGruJornada());
			pst.setLong(posicion++, doc.getDocGruMetodologia());
			pst.setLong(posicion++, doc.getDocGruGrado());
			rs = pst.executeQuery();
			if (rs.next()) {
				doc.setDocGruJerGrado(rs.getLong(1));
			} else {
				setMensaje("NO se encontrn jerarquia");
				return false;
			}
			long[] gru = doc.getDocGruGrupo();
			if (gru != null) {
				pst = cn.prepareStatement(rbRot
						.getString("docenteXGrupo.Insertar"));
				pst.clearBatch();
				for (int i = 0; i < gru.length; i++) {
					posicion = 1;
					pst.setLong(posicion++, doc.getDocGruJerGrado());
					pst.setLong(posicion++, doc.getDocGruDocente());
					pst.setLong(posicion++, doc.getDocGruAsignatura());
					pst.setLong(posicion++, gru[i]);
					pst.setLong(posicion++, doc.getDocGruVigencia());
					pst.addBatch();
				}
				pst.executeBatch();
				pst.close();
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(
					"NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			switch (sqle.getErrorCode()) {
			// Violacinn de llave primaria
			case 1:
			case 2291:
				setMensaje("Ya existe una asignacinn del mismo grado - asignatura");
				return false;
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
				throw new Exception(getMensaje());
			}
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean ingresarEspacioGrado(EspXGradoVO esp) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			long[] gra = esp.getEspGraGrado();
			if (gra != null) {
				pst = cn.prepareStatement(rbRot
						.getString("espacioXGrado.Insertar"));
				pst.clearBatch();
				for (int i = 0; i < gra.length; i++) {
					posicion = 1;
					pst.setInt(posicion++, esp.getEspGraEspacio());
					pst.setInt(posicion++, esp.getEspGraVigencia());
					pst.setLong(posicion++, esp.getEspGraInstitucion());
					pst.setInt(posicion++, esp.getEspGraSede());
					pst.setInt(posicion++, esp.getEspGraJornada());
					pst.setInt(posicion++, esp.getEspGraMetodologia());
					pst.setLong(posicion++, gra[i]);
					pst.addBatch();
				}
				pst.executeBatch();
				pst.close();
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(
					"NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			switch (sqle.getErrorCode()) {
			// Violacinn de llave primaria
			case 1:
			case 2291:
				setMensaje("Ya existe una asignacinn del mismo espacio - grado");
				return false;
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
				throw new Exception(getMensaje());
			}
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarDocenteGrupo(long jerGrado, long doc, long asig)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("docenteXGrupo.Eliminar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, jerGrado);
			pst.setLong(posicion++, doc);
			pst.setLong(posicion++, asig);
			pst.executeUpdate();
			pst.close();
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			throw new Exception(
					"NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			throw new Exception(getMensaje());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public List getAllEspacioGrado(FiltroEspXGradoVO filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		EspXGradoVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAllEspacioGrado"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new EspXGradoVO();
				lp.setEspGraInstitucion(rs.getLong(i++));
				lp.setEspGraSede(rs.getInt(i++));
				lp.setEspGraJornada(rs.getInt(i++));
				lp.setEspGraMetodologia(rs.getInt(i++));
				lp.setEspGraEspacio(rs.getInt(i++));
				lp.setEspGraNombreEspacio(rs.getString(i++));
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

	public List getAjaxEspacio(long inst, int sed, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		EspacioVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAjaxEspacio"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sed);
			st.setInt(i++, jor);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new EspacioVO();
				lp.setCodigo(rs.getLong(i++));
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

	public List getAjaxGrado2(long inst, int met, int sed, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		GradoVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getAjaxGrado2"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sed);
			st.setInt(i++, jor);
			st.setInt(i++, met);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new GradoVO();
				lp.setCodigo(rs.getLong(i++));
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

	public EspXGradoVO getEspacioGrado(long inst, int sede, int jor, int metod,
			int esp, long vig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		EspXGradoVO lp = null;
		List l = new ArrayList();
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("getEspXGrado"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jor);
			st.setInt(i++, metod);
			st.setInt(i++, esp);
			st.setLong(i++, vig);
			rs = st.executeQuery();
			int it = 0;
			while (rs.next()) {
				i = 1;
				if (it == 0) {
					lp = new EspXGradoVO();
					lp.setEspGraInstitucion(rs.getLong(i++));
					lp.setEspGraSede(rs.getInt(i++));
					lp.setEspGraJornada(rs.getInt(i++));
					lp.setEspGraMetodologia(rs.getInt(i++));
					lp.setEspGraEspacio(rs.getInt(i++));
					l.add(rs.getString(i++));
					lp.setFormaEstado("1");
					it++;
				} else {
					l.add(rs.getString(6));
				}
			}
			if (lp != null) {
				long[] lista = new long[l.size()];
				for (i = 0; i < l.size(); i++) {
					lista[i] = Long.parseLong((String) l.get(i));
				}
				lp.setEspGraGrado(lista);
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
		return lp;
	}

	public boolean eliminarEspacioGrado(long inst, int sede, int jor,
			int metod, int esp, long vig) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("espacioXGrado.Eliminar"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, esp);
			pst.setLong(posicion++, vig);
			pst.setLong(posicion++, inst);
			pst.setInt(posicion++, sede);
			pst.setInt(posicion++, jor);
			pst.setInt(posicion++, metod);
			pst.executeUpdate();
			pst.close();
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			throw new Exception(
					"NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			throw new Exception(getMensaje());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarEspacioGrado(EspXGradoVO esp) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("espacioXGrado.Eliminar"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, esp.getEspGraEspacio());
			pst.setInt(posicion++, esp.getEspGraVigencia());
			pst.setLong(posicion++, esp.getEspGraInstitucion());
			pst.setInt(posicion++, esp.getEspGraSede());
			pst.setInt(posicion++, esp.getEspGraJornada());
			pst.setInt(posicion++, esp.getEspGraMetodologia());
			pst.executeUpdate();
			pst.close();
			long[] gra = esp.getEspGraGrado();
			if (gra != null) {
				pst = cn.prepareStatement(rbRot
						.getString("espacioXGrado.Insertar"));
				pst.clearBatch();
				for (int i = 0; i < gra.length; i++) {
					System.out.println("gradoo=" + gra[i]);
					posicion = 1;
					pst.setInt(posicion++, esp.getEspGraEspacio());
					pst.setInt(posicion++, esp.getEspGraVigencia());
					pst.setLong(posicion++, esp.getEspGraInstitucion());
					pst.setInt(posicion++, esp.getEspGraSede());
					pst.setInt(posicion++, esp.getEspGraJornada());
					pst.setInt(posicion++, esp.getEspGraMetodologia());
					pst.setLong(posicion++, gra[i]);
					pst.addBatch();
				}
				pst.executeBatch();
				pst.close();
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			throw new Exception(
					"NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			switch (sqle.getErrorCode()) {
			// Violacinn de llave primaria
			case 1:
			case 2291:
				setMensaje("Ya existe una asignacinn del mismo espacio - grado");
				return false;
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			throw new Exception(getMensaje());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public List getAllEstructura(long inst, int sede, int jornada,
			FiltroRotacion filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		EstructuraVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.Estructura") + " "
					+ rbRot.getString("Lista.Estructura.1") + " "
					+ rbRot.getString("Lista.Estructura.2"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, filtro.getFilAnhoVigencia());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new EstructuraVO();
				lp.setEstCodigo(rs.getLong(i++));
				lp.setEstNombre(rs.getString(i++));
				i++;
				lp.setEstDurBloque(rs.getInt(i++));
				lp.setEstBloqueXJornada(rs.getInt(i++));
				lp.setEstSemanaXCiclo(rs.getInt(i++));
				i++;
				i++;
				lp.setEstSede(rs.getInt(i++));
				lp.setEstJornada(rs.getInt(i++));
				lp.setEstMetodologia(rs.getInt(i++));
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

	public List getAllEstructuraGrado(long inst, int sede, int jornada,
			FiltroRotacion filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		EstructuraGradoVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.EstructuraGrado")
					+ " " + rbRot.getString("Lista.EstructuraGrado.1") + " "
					+ rbRot.getString("Lista.EstructuraGrado.2"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, filtro.getFilAnhoVigencia());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new EstructuraGradoVO();
				lp.setEstCodigo(rs.getLong(i++));
				lp.setEstGrado(rs.getInt(i++));
				lp.setEstPrioridad(rs.getInt(i++));
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

	public List getAllFijarEspacio(long inst, int sede, int jornada,
			FiltroRotacion filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		FijarEspacioVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.FijarEspacio"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, filtro.getFilAnhoVigencia());
			st.setInt(i++, filtro.getFilMetodologia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new FijarEspacioVO();
				lp.setFijJerarquia(rs.getLong(i++));
				lp.setFijEspacio(rs.getLong(i++));
				lp.setFijAsignatura(rs.getLong(i++));
				lp.setFijVigencia(rs.getInt(i++));
				lp.setFijNomSede(rs.getString(i++));
				lp.setFijNomJornada(rs.getString(i++));
				lp.setFijNomAsignatura(rs.getString(i++));
				lp.setFijNomEspacio(rs.getString(i++));
				lp.setFijExclusivo(rs.getString(i++));
				lp.setFijMetodologia(rs.getInt(i++));
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

	public List getAllVigencia(long codInst) {
		List l = new ArrayList();
		try {
			long vig = getVigenciaInst(codInst);
			l.add(String.valueOf(vig - 1));
			l.add(String.valueOf(vig));
			l.add(String.valueOf(vig + 1));
			return l;
		} catch (Exception e) {
			return null;
		}
	}

	public List getAllFijarAsignatura(long inst, int met, int sede,
			int jornada, int vigencia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		FijarAsignaturaVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.FijarAsignatura"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, met);
			st.setInt(i++, vigencia);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new FijarAsignaturaVO();
				lp.setFijEstructura(rs.getLong(i++));
				lp.setFijAsignatura(rs.getLong(i++));
				lp.setFijBloque(rs.getInt(i++));
				lp.setFijNomEstructura(rs.getString(i++));
				lp.setFijNomAsignatura(rs.getString(i++));
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

	public List getAllFijarEspacioDocente(long inst, int sede, int jornada,
			int vigencia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		FijarEspacioDocenteVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot
					.getString("Lista.FijarEspacioDocente"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, vigencia);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new FijarEspacioDocenteVO();
				lp.setFijEspacio(rs.getLong(i++));
				lp.setFijDocente(rs.getLong(i++));
				lp.setFijJerarquia(rs.getLong(i++));
				lp.setFijNomEspacio(rs.getString(i++));
				lp.setFijNomDocente(rs.getString(i++));
				lp.setFijNomSede(rs.getString(i++));
				lp.setFijVigencia(rs.getInt(i++));
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

	public List getAllInhabilitarEspacio(long inst, int sede, int jornada,
			FiltroRotacion filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		InhabilitarEspacioVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.filtroEspFisJor"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, filtro.getFilAnhoVigencia());
			st.setInt(i++, filtro.getFilMetodologia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new InhabilitarEspacioVO();
				lp.setInhNomEstructura(rs.getString(i++));
				lp.setInhNomEspacio(rs.getString(i++));
				lp.setInhNomDia(rs.getString(i++));
				lp.setInhDia(rs.getInt(i++));
				lp.setInhHoraIni(rs.getInt(i++));
				lp.setInhHoraFin(rs.getInt(i++));
				lp.setInhEstructura(rs.getLong(i++));
				lp.setInhEspacio(rs.getLong(i++));
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

	public List getAllInhabilitarDocente(long inst, int sede, int jornada,
			FiltroRotacion filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		InhabilitarDocenteVO lp = null;
		long jerarquia = 0;
		int i = 0;
		try {
			cn = cursor.getConnection();
			// obtener jerarquia 1-6
			st = cn.prepareStatement(rbRot.getString("Asignar.Jerar_1_6"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			rs = st.executeQuery();
			if (rs.next()) {
				jerarquia = rs.getLong(1);
			} else {
				return null;
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rbRot.getString("Lista.InhDocente"));
			i = 1;
			st.setLong(i++, jerarquia);
			st.setInt(i++, filtro.getFilAnhoVigencia());
			st.setInt(i++, filtro.getFilMetodologia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new InhabilitarDocenteVO();
				lp.setInhEstructura(rs.getLong(i++));
				lp.setInhDocente(rs.getLong(i++));
				lp.setInhDia(rs.getInt(i++));
				lp.setInhHoraIni(rs.getInt(i++));
				lp.setInhHoraFin(rs.getInt(i++));
				lp.setInhNomEstructura(rs.getString(i++));
				lp.setInhNomDocente(rs.getString(i++));
				lp.setInhNomDia(rs.getString(i++));
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

	public List getAllInhabilitarHora(long inst, int sede, int jornada,
			FiltroRotacion filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		InhabilitarHoraVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.InhHora"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, filtro.getFilAnhoVigencia());
			st.setInt(i++, filtro.getFilMetodologia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new InhabilitarHoraVO();
				lp.setInhEstructura(rs.getLong(i++));
				lp.setInhDia(rs.getInt(i++));
				lp.setInhHoraIni(rs.getInt(i++));
				lp.setInhHoraFin(rs.getInt(i++));
				lp.setInhNomEstructura(rs.getString(i++));
				lp.setInhNomDia(rs.getString(i++));
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

	public List getAllGrado(long inst, int met) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		String[] lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot
					.getString("Lista.filtroSedeJornadaGradoInstitucion2"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, met);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 0;
				lp = new String[6];
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
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

	public List getAllAsignatura(long inst, int met, int vig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		String[] lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.Asignatura"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, met);
			st.setInt(i++, vig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 0;
				lp = new String[4];
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
				lp[i] = (rs.getString(++i));
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

	public int getMetodologia(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot.getString("Lista.filtroMetodologia"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
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
		return 0;
	}

	public List getAllMetodologia(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbRot
					.getString("Lista.MetodologiaInstitucion"));
			st.setLong(1, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				item = new ItemVO();
				item.setCodigo(rs.getInt(1));
				item.setNombre(rs.getString(2));
				l.add(item);
			}
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

	public Collection getDocentes(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			if (h == null || h.getInst().equals("") || h.getSede().equals("")
					|| h.getJornada().equals("")
					|| h.getMetodologia().equals("") || h.getGrado().equals("")
					|| h.getAsignatura().equals("")) {
				return null;
			}
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long asig = Long.parseLong(h.getAsignatura());
			long vig = Long.parseLong(String.valueOf(h.getVigencia()));
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.docenteCarga"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, asig);
			pst.setLong(pos++, vig);
			rs = pst.executeQuery();
			list = getCollection(rs);

			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] obj1 = (Object[]) it.next();
				System.out.println("Reconoce:" + (String) obj1[0] + "-"
						+ (String) obj1[1]);
				System.out.println("//");
				// System.out.println("Reconoce:"+it.next());
				Integer ret = this.getHorasLibresxDocente((String) obj1[0],
						String.valueOf(h.getVigencia()));
				if (Integer.parseInt(String.valueOf(ret)) <= 0) {
					list.remove(obj1);
				}

			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetDOCSEDJOR: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public String getDocentesxDoc(String cedula) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		String nombre = "";
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.docentexdoc"));
			int pos = 1;

			pst.setString(pos++, cedula);
			rs = pst.executeQuery();

			while (rs.next()) {
				nombre = rs.getString(1);
				break;
			}
			return nombre;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetDOCSEDJOR: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getAsignaturas(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			if (h == null || h.getInst().equals("")
					|| h.getMetodologia().equals("") || h.getGrado().equals("")) {
				return null;
			}
			if (h.getGrado().equals("0")) {
				list = new ArrayList();
				Object[] o = new Object[3];
				o[0] = "1";
				o[1] = "Corporal";
				o[2] = "Corpo";
				list.add(o);
				o = new Object[3];
				o[0] = "2";
				o[1] = "Comunicativa";
				o[2] = "Comun";
				list.add(o);
				o = new Object[3];
				o[0] = "3";
				o[1] = "Cognitiva";
				o[2] = "Cogni";
				list.add(o);
				o = new Object[3];
				o[0] = "4";
				o[1] = "ntica";
				o[2] = "ntica";
				list.add(o);
				o = new Object[3];
				o[0] = "5";
				o[1] = "Estntica";
				o[2] = "Estet";
				list.add(o);
				return list;
			}
			cn = cursor.getConnection();
			int posicion = 1;
			pst = cn.prepareStatement(rbRot.getString("horario.asignatura"));
			pst.setLong(posicion++, Long.parseLong(h.getInst()));
			pst.setLong(posicion++, Long.parseLong(h.getMetodologia()));
			pst.setLong(posicion++, Long.parseLong(h.getGrado()));
			pst.setLong(posicion++, h.getVigencia());
			rs = pst.executeQuery();
			list = getCollection(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error obteniendo Asignaturas para horario: "
					+ e);
			setMensaje("Error obteniendo Asignaturas para horario: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Integer getHorasLibresxDocente(String docente, String vigencia) {

		Integer horxdocvigencia = getHorasLibresxDocentexVigencia(docente,
				vigencia);
		Integer horxdochorario = getHorasLibresxDocenteHorario(docente);
		return new Integer(horxdocvigencia.intValue()
				- horxdochorario.intValue());
	}

	public Integer getHorasLibresxDocentexVigencia(String docente,
			String vigencia) {
		Integer horas = new Integer(0);

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			if (docente == null || docente.equals("") || vigencia == null
					|| vigencia.equals("")) {
				return null;
			}
			long ldocente = Long.parseLong(docente);
			long lvigencia = Long.parseLong(vigencia);

			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("horario.totalasignadoxdocente"));
			int pos = 1;
			pst.setLong(pos++, lvigencia);
			pst.setLong(pos++, ldocente);
			rs = pst.executeQuery();
			while (rs.next()) {
				horas = new Integer(rs.getInt(1));
				break;
			}
			return horas;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetDOCSEDJOR: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Integer getHorasLibresxDocenteHorario(String docente) {
		Integer horas = new Integer(0);

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			if (docente == null || docente.equals("")) {
				return null;
			}
			long ldocente = Long.parseLong(docente);

			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("horario.totalactualhorarioxdocente"));
			int pos = 1;
			pst.setLong(pos++, ldocente);
			pst.setLong(pos++, ldocente);
			pst.setLong(pos++, ldocente);
			pst.setLong(pos++, ldocente);
			pst.setLong(pos++, ldocente);
			pst.setLong(pos++, ldocente);
			pst.setLong(pos++, ldocente);
			rs = pst.executeQuery();

			while (rs.next()) {
				horas = new Integer(rs.getInt(1));
				break;
			}
			return horas;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetDOCSEDJOR: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}

	}

	public Collection getDocentesGrado(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			if (h == null || h.getInst().equals("") || h.getSede().equals("")
					|| h.getJornada().equals("")
					|| h.getMetodologia().equals("") || h.getGrado().equals("")) {
				return null;
			}
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());

			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("rot2.docenteGrado"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, h.getVigencia());
			rs = pst.executeQuery();
			list = getCollection(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetDOCSEDJOR: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getAsignaturasGrado(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			if (h == null || h.getInst().equals("") || h.getSede().equals("")
					|| h.getJornada().equals("")
					|| h.getMetodologia().equals("") || h.getGrado().equals("")) {
				System.out
						.println("parametros de horario insuficientes para retornar Asignaturas");
				return null;
			}
			long inst = Long.parseLong(h.getInst());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long vig = h.getVigencia();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("rot2.asignaturaGrado"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, vig);
			rs = pst.executeQuery();
			list = getCollection(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetAsig: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public String[] getHorarioEstructura(Horario h) {
		String params[] = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long gra = Long.parseLong(h.getGrado());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("horarioEstructura.select"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, h.getVigencia());
			rs = pst.executeQuery();
			if (rs.next()) {
				params = new String[3];
				params[0] = rs.getString(1);
				params[1] = rs.getString(2);
				params[2] = rs.getString(3);
			}
			return params;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetHorarioEstrucuta: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getHorario(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		String ih = null;
		String cupo = "0";
		String ihDoc = "0";
		long jerGrupo = 0;
		try {
			long inst = Long.parseLong(h.getInst());
			long sed = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long gru = Long.parseLong(h.getGrupo());
			long asig = Long.parseLong(h.getAsignatura());
			long vig = h.getVigencia();
			long doc = Long.parseLong(h.getDocente());
			long jerGrado = 0;
			int pos = 1;
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.grupo"));
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sed);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, gru);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerGrupo = rs.getLong(1);
			}
			if (jerGrupo == 0) {
				setMensaje("Grupo no encontrado");
				return null;
			}
			h.setJerGrupo(String.valueOf(jerGrupo));
			rs.close();
			pst.close();
			// traer el codigo de jerarquia 1 7
			// horario.jerGrado
			pst = cn.prepareStatement(rbRot.getString("horario.jerGrado"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sed);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerGrado = rs.getLong(1);
				h.setJerGrado(rs.getString(1));
			} else {
				setMensaje("Grado no encontrado");
				return null;
			}
			rs.close();
			pst.close();
			// consulta
			pst = cn.prepareStatement(rbRot.getString("horario.select"));
			pos = 1;
			pst.setLong(pos++, jerGrupo);
			pst.setLong(pos++, vig);
			rs = pst.executeQuery();
			list = getCollection(rs);
			rs.close();
			pst.close();
			// lista nombres docentes del horario dia1
			pst = cn.prepareStatement(rbRot.getString("horario.jerGrado"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sed);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerGrado = rs.getLong(1);
				h.setJerGrado(rs.getString(1));
			} else {
				setMensaje("Grado no encontrado");
				return null;
			}
			rs.close();
			pst.close();
			// consulta
			/*
			 * pst = cn.prepareStatement(rbRot.getString(
			 * "horario.selectdocumentosdocentes")); pos = 1; pst.setLong(pos++,
			 * jerGrupo); pst.setLong(pos++, vig); rs = pst.executeQuery();
			 * Collection listadocentes = getCollection(rs); rs.close();
			 * pst.close();
			 * 
			 * ArrayList listacompleta=new ArrayList(); Iterator it =
			 * listadocentes.iterator(); while(it.hasNext()) { Object[]
			 * obj1=(Object[])it.next();
			 * 
			 * for(int w=0;w<obj1.length;w++){ listacompleta.add(obj1[w]); } }
			 * HashSet hs = new HashSet(); //Lo cargamos con los valores del
			 * array, esto hace quite los repetidos hs.addAll(listacompleta);
			 * //Limpiamos el array listacompleta.clear(); //Agregamos los
			 * valores sin repetir listacompleta.addAll(hs);
			 */

			// INTENSIDAD HORARIA DE ASIGNATURA
			pst = cn.prepareStatement(rbRot.getString("horario.ih"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, met);
			pst.setLong(pos++, asig);
			pst.setLong(pos++, vig);
			rs = pst.executeQuery();
			if (rs.next()) {
				ih = rs.getString(1);
			}
			h.setIh(ih);
			rs.close();
			pst.close();
			// CUPO DE GRUPO
			pst = cn.prepareStatement(rbRot.getString("horario.cupo"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sed);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, gru);
			rs = pst.executeQuery();
			if (rs.next()) {
				cupo = rs.getString(1);
			}
			rs.close();
			pst.close();
			h.setCupo(cupo);
			// horario.docenteIH
			// IH DEL DOCENTE
			pst = cn.prepareStatement(rbRot.getString("horario.docenteIH"));
			pos = 1;
			pst.setLong(pos++, jerGrado);
			pst.setLong(pos++, doc);
			pst.setLong(pos++, asig);
			pst.setLong(pos++, vig);
			rs = pst.executeQuery();
			if (rs.next()) {
				ihDoc = rs.getString(1);
			}
			h.setIhDocente(ihDoc);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetHorario: " + e);
			// siges.util.Logger.print("-99","Error parametros:
			// "+e,0,1,this.toString());
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getNombrexDocentes(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		String ih = null;
		String cupo = "0";
		String ihDoc = "0";
		long jerGrupo = 0;
		try {
			long inst = Long.parseLong(h.getInst());
			long sed = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long gru = Long.parseLong(h.getGrupo());
			long asig = Long.parseLong(h.getAsignatura());
			long vig = h.getVigencia();
			long doc = Long.parseLong(h.getDocente());
			long jerGrado = 0;
			int pos = 1;
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.grupo"));
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sed);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, gru);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerGrupo = rs.getLong(1);
			}
			if (jerGrupo == 0) {
				setMensaje("Grupo no encontrado");
				return null;
			}
			h.setJerGrupo(String.valueOf(jerGrupo));
			rs.close();
			pst.close();
			// traer el codigo de jerarquia 1 7
			// horario.jerGrado
			pst = cn.prepareStatement(rbRot.getString("horario.jerGrado"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sed);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerGrado = rs.getLong(1);
				h.setJerGrado(rs.getString(1));
			} else {
				setMensaje("Grado no encontrado");
				return null;
			}
			rs.close();
			pst.close();
			// consulta

			pst = cn.prepareStatement(rbRot.getString("horario.select"));
			pos = 1;
			pst.setLong(pos++, jerGrupo);
			pst.setLong(pos++, vig);
			rs = pst.executeQuery();
			list = getCollection(rs);
			rs.close();
			pst.close();
			// lista nombres docentes del horario dia1
			pst = cn.prepareStatement(rbRot.getString("horario.jerGrado"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sed);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			pst.setLong(pos++, gra);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerGrado = rs.getLong(1);
				h.setJerGrado(rs.getString(1));
			} else {
				setMensaje("Grado no encontrado");
				return null;
			}
			rs.close();
			pst.close();
			// consulta
			pst = cn.prepareStatement(rbRot
					.getString("horario.selectdocumentosdocentes"));
			pos = 1;
			pst.setLong(pos++, jerGrupo);
			pst.setLong(pos++, vig);
			System.out.println("listadodoc " + jerGrupo + " " + vig);
			rs = pst.executeQuery();
			Collection listadocentes = getCollection(rs);
			rs.close();
			pst.close();

			ArrayList listacompletadocentes = new ArrayList();
			ArrayList listacompletaasignaturas = new ArrayList();
			int posi = 0;
			Iterator it = listadocentes.iterator();
			while (it.hasNext()) {
				Object[] obj1 = (Object[]) it.next();

				for (int w = 0; w < obj1.length; w++) {
					if (obj1[w] != null) {
						if (!listacompletadocentes.contains(obj1[w])) {
							listacompletadocentes.add(obj1[w]);
							listacompletaasignaturas.add(obj1[w + 1]);
						} else {
							posi = listacompletadocentes.indexOf(obj1[w]);
							if (!listacompletaasignaturas.get(posi).equals(
									obj1[w + 1])) {
								listacompletadocentes.add(obj1[w]);
								listacompletaasignaturas.add(obj1[w + 1]);
							}
						}
					}
					w++;
				}
			}
			// HashSet hs = new HashSet();
			// HashSet hs1 = new HashSet();
			// Lo cargamos con los valores del array, esto hace quite los
			// repetidos
			// hs.addAll(listacompletadocentes);
			// hs1.addAll(listacompletaasignaturas);
			// Limpiamos el array
			// listacompletadocentes.clear();
			// listacompletaasignaturas.clear();
			// Agregamos los valores sin repetir
			// listacompletadocentes.addAll(hs);
			// listacompletaasignaturas.addAll(hs1);

			ArrayList listaconnombreydoc = new ArrayList();
			for (int po = 0; po < listacompletadocentes.size(); po++) {
				try {
					if (((String) listacompletaasignaturas.get(po)) == null
							|| ((String) listacompletadocentes.get(po)) == null) {
						continue;
					}
					pst = cn.prepareStatement(rbRot
							.getString("horario.selectnombreasigdocumentosdocente"));
					pos = 1;
					int ais = Integer
							.parseInt((String) listacompletaasignaturas.get(po));
					int vige = h.getVigencia();
					int grado = Integer.parseInt(h.getJerGrado());
					int instu = Integer.parseInt(h.getInst());
					int meto = Integer.parseInt(h.getMetodologia());

					String cedula = (String) listacompletadocentes.get(po);

					pst.setLong(pos++, ais);
					pst.setLong(pos++, vige);
					pst.setLong(pos++, instu);
					pst.setLong(pos++, meto);
					pst.setString(pos++, cedula);
					pst.setLong(pos++, grado);
					rs = pst.executeQuery();
					while (rs.next()) {
						String[] tmp = new String[2];
						tmp[0] = rs.getString(1);
						tmp[1] = rs.getString(2);
						listaconnombreydoc.add(tmp);
					}

					rs.close();
					pst.close();
				} catch (NullPointerException ex) {

				}
			}

			return listaconnombreydoc;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetHorario: " + e);
			// siges.util.Logger.print("-99","Error parametros:
			// "+e,0,1,this.toString());
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getEspacios(Horario h) {
		// String params[] = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.espacio"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			rs = pst.executeQuery();
			list = getCollection(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetEspacio: " + e);
			// siges.util.Logger.print("-99","Error parametros:
			// "+e,0,1,this.toString());
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getInhabilidadesEspacio(Horario r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[][] a = null;
		Collection list = null;
		try {
			long inst = Long.parseLong(r.getInst());
			long sede = Long.parseLong(r.getSede());
			long jor = Long.parseLong(r.getJornada());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("horario.getEspacioInhabilidades"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, r.getVigencia());
			rs = pst.executeQuery();
			a = getFiltroMatriz(getCollection(rs));
			if (a != null) {
				list = new ArrayList();
				Object[] o = new Object[3];
				String dia;
				String espacio;
				int hi, hf;
				for (int i = 0; i < a.length; i++) {
					espacio = a[i][0];
					dia = a[i][1];
					hi = Integer.parseInt(a[i][2]);
					hf = Integer.parseInt(a[i][3]);
					for (int j = hi; j <= hf; j++) {
						o = new Object[3];
						o[0] = espacio;
						o[1] = dia;
						o[2] = "" + j;
						list.add(o);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetInhabilidades: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return list;
	}

	public Collection getRestriccionesHorario(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list, list2 = null;
		try {
			// System.out.println("ESTA ES LA VIGENCIA DE LA VAINA: "+h.getVigencia());
			long docente = Long.parseLong(h.getDocente());
			String doc = h.getDocente();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.restriccion"));
			int posicion = 1;
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, docente);
			pst.setInt(posicion++, h.getVigencia());
			rs = pst.executeQuery();
			list = getCollection(rs);
			String[][] m = getFiltroMatriz(list);
			if (m != null) {
				list2 = new ArrayList();
				String grupo;
				String clase;
				String dia;
				// String asig;
				String valor;
				for (int i = 0; i < m.length; i++) {
					grupo = m[i][0];
					clase = m[i][1];
					if (GenericValidator.isLong(m[i][3]) && doc.equals(m[i][2])
							&& h.getJornada().equals(m[i][16])) {
						dia = "1";
						valor = grupo + "|" + clase + "|" + dia + "|" + m[i][3];
						list2.add(valor);
					}
					if (GenericValidator.isLong(m[i][5]) && doc.equals(m[i][4])
							&& h.getJornada().equals(m[i][16])) {
						dia = "2";
						valor = grupo + "|" + clase + "|" + dia + "|" + m[i][5];
						list2.add(valor);
					}
					if (GenericValidator.isLong(m[i][7]) && doc.equals(m[i][6])
							&& h.getJornada().equals(m[i][16])) {
						dia = "3";
						valor = grupo + "|" + clase + "|" + dia + "|" + m[i][7];
						list2.add(valor);
					}
					if (GenericValidator.isLong(m[i][9]) && doc.equals(m[i][8])
							&& h.getJornada().equals(m[i][16])) {
						dia = "4";
						valor = grupo + "|" + clase + "|" + dia + "|" + m[i][9];
						list2.add(valor);
					}
					if (GenericValidator.isLong(m[i][11])
							&& doc.equals(m[i][10])
							&& h.getJornada().equals(m[i][16])) {
						dia = "5";
						valor = grupo + "|" + clase + "|" + dia + "|"
								+ m[i][11];
						list2.add(valor);
					}
					if (GenericValidator.isLong(m[i][13])
							&& doc.equals(m[i][12])
							&& h.getJornada().equals(m[i][16])) {
						dia = "6";
						valor = grupo + "|" + clase + "|" + dia + "|"
								+ m[i][13];
						list2.add(valor);
					}
					if (GenericValidator.isLong(m[i][15])
							&& doc.equals(m[i][14])
							&& h.getJornada().equals(m[i][16])) {
						dia = "7";
						valor = grupo + "|" + clase + "|" + dia + "|"
								+ m[i][15];
						list2.add(valor);
					}
				}
			}
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetRestriccionesHorario: " + e);
			// siges.util.Logger.print("-99","Error parametros:
			// "+e,0,1,this.toString());
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getInhabilidadesDocente(Horario r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[][] a = null;
		Collection list = null;
		try {
			long doc = Long.parseLong(r.getDocente());
			long inst = Long.parseLong(r.getInst());
			long sede = Long.parseLong(r.getSede());
			long jor = Long.parseLong(r.getJornada());
			long gra = Long.parseLong(r.getGrado());
			// System.out.println("valores:::"+r.getDocente()+"//"+r.getInst()+"//"+r.getSede()+"//"+r.getJornada()+"//"+r.getGrado());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("horario.getInhabilidades"));
			int pos = 1;
			pst.setLong(pos++, doc);
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, r.getVigencia());
			pst.setLong(pos++, gra);
			rs = pst.executeQuery();
			a = getFiltroMatriz(getCollection(rs));
			if (a != null) {
				list = new ArrayList();
				Object[] o = new Object[2];
				String dia;
				int hi, hf;
				for (int i = 0; i < a.length; i++) {
					dia = a[i][0];
					hi = Integer.parseInt(a[i][1]);
					hf = Integer.parseInt(a[i][2]);
					for (int j = hi; j <= hf; j++) {
						o = new Object[2];
						o[0] = dia;
						o[1] = "" + j;
						list.add(o);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetInhabilidades: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return list;
	}

	public Collection getInhabilidadesHora(Horario r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[][] a = null;
		Collection list = null;
		try {
			long inst = Long.parseLong(r.getInst());
			long sede = Long.parseLong(r.getSede());
			long jor = Long.parseLong(r.getJornada());
			long gra = Long.parseLong(r.getGrado());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot
					.getString("horario.getInhabilidadesHora"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, gra);
			pst.setLong(pos++, r.getVigencia());
			rs = pst.executeQuery();
			a = getFiltroMatriz(getCollection(rs));
			if (a != null) {
				list = new ArrayList();
				Object[] o = new Object[2];
				String dia;
				int hi, hf;
				for (int i = 0; i < a.length; i++) {
					dia = a[i][0];
					hi = Integer.parseInt(a[i][1]);
					hf = Integer.parseInt(a[i][2]);
					for (int j = hi; j <= hf; j++) {
						o = new Object[2];
						o[0] = dia;
						o[1] = "" + j;
						list.add(o);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetInhabilidadesHora: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return list;
	}

	public Collection getHorarioGrupo(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.grupo"));
			pst.setLong(1, Long.parseLong(h.getInst()));
			pst.setLong(2, Long.parseLong(h.getSede()));
			pst.setLong(3, Long.parseLong(h.getJornada()));
			pst.setLong(4, Long.parseLong(h.getMetodologia()));
			pst.setLong(5, Long.parseLong(h.getGrado()));
			pst.setLong(6, Long.parseLong(h.getGrupo()));
			rs = pst.executeQuery();
			if (rs.next()) {
				jerarquia = rs.getString(1);
			}
			if (jerarquia == null || jerarquia.trim().equals("")) {
				setMensaje("Grupo no encontrado");
				System.out.println("Jerarquia de Grupo no encontrado");
				return null;
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rbRot.getString("horario.select"));
			int pos = 1;
			pst.setLong(pos++, Long.parseLong(jerarquia));
			rs = pst.executeQuery();
			list = getCollection(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetHorario: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @function:
	 * @return
	 */
	public String getJeraquia() {
		return jerarquia;
	}

	public boolean borrarHorarioRotacion(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		// Collection list = null;
		// String[] a;
		// String[] a_;
		// int pos = 1;
		// int horas = 1;
		// int dia, hora;
		// boolean band = false;
		try {
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long gra = Long.parseLong(h.getGrado());
			long gru = Long.parseLong(h.getGrupo());
			long asig = Long.parseLong(h.getAsignatura());
			int posicion = 1;
			// System.out.println("::::" + inst + "//" + sede + "//" + jor +
			// "//" + gra + "//" + gru + "//" + asig);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// sede-jornada
			if (gra == -1 && gru == -1 && asig == -1) {
				System.out.println("SEDE JOrnada");
				posicion = 1;
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarJornada"));
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, h.getVigencia());
				pst.executeUpdate();
				pst.close();
				// borrar la IH real
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarJornadaIH"));
				posicion = 1;
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.executeUpdate();
				pst.close();
				cn.commit();
				return true;
			}
			// sede-jornada-grado
			if (gra != -1 && gru == -1 && asig == -1) {
				System.out.println("SEDE JOrnada Grado");
				posicion = 1;
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarGrado"));
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, h.getVigencia());
				pst.executeUpdate();
				pst.close();
				// borrar la IH real
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarGradoIH"));
				posicion = 1;
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, gra);
				pst.executeUpdate();
				pst.close();
				cn.commit();
				return true;
			}
			// sede-jornada-grado-grupo
			if (gra != -1 && gru != -1 && asig == -1) {
				System.out.println("SEDE JOrnada Grado Grupo");
				posicion = 1;
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarGradoGrupo"));
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, gru);
				pst.setLong(posicion++, h.getVigencia());
				pst.executeUpdate();
				cn.commit();
				return true;
			}
			// sede-jornada-grado-asignatura
			if (gra != -1 && gru == -1 && asig != -1) {
				System.out.println("SEDE JOrnada Grado ASig");
				// delete
				posicion = 1;
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarGradoAsignatura"));
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, h.getVigencia());
				pst.executeUpdate();
				pst.close();
				// actualizacion
				for (int t = 1; t <= 7; t++) {
					posicion = 1;
					pst = cn.prepareStatement(rbRot
							.getString("horario.borrarGradoAsignatura" + t));
					pst.setLong(posicion++, inst);
					pst.setLong(posicion++, sede);
					pst.setLong(posicion++, jor);
					pst.setLong(posicion++, gra);
					pst.setLong(posicion++, asig);
					pst.setLong(posicion++, h.getVigencia());
					pst.executeUpdate();
					pst.close();
				}
				// borrar IH
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarGradoAsignaturaIH"));
				posicion = 1;
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, asig);
				pst.executeUpdate();
				pst.close();
				cn.commit();
				return true;
			}
			// sede-jornada-grado-grupo-asignatura
			if (gra != -1 && gru != -1 && asig != -1) {
				System.out.println("SEDE JOrnada Grado Grupo Aisg");
				// delete
				posicion = 1;
				pst = cn.prepareStatement(rbRot
						.getString("horario.borrarGradoGrupoAsignatura"));
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, gru);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, asig);
				pst.setLong(posicion++, h.getVigencia());
				pst.executeUpdate();
				pst.close();
				// actualizacion
				for (int t = 1; t <= 7; t++) {
					posicion = 1;
					pst = cn.prepareStatement(rbRot
							.getString("horario.borrarGradoGrupoAsignatura" + t));
					pst.setLong(posicion++, inst);
					pst.setLong(posicion++, sede);
					pst.setLong(posicion++, jor);
					pst.setLong(posicion++, gra);
					pst.setLong(posicion++, gru);
					pst.setLong(posicion++, asig);
					pst.setLong(posicion++, h.getVigencia());
					pst.executeUpdate();
					pst.close();
				}
				cn.commit();
				return true;
			}
			cn.setAutoCommit(true);
			return true;
		} catch (Exception e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			e.printStackTrace();
			System.out.println("ERROR BORRAR HOR: " + e);
			setMensaje("" + e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public boolean guardarHorarioRotacion(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String[] a;
		String[] a_;
		int pos = 1;
		int horas = 1;
		int dia, hora;
		boolean band = false;
		long doc, asig, gru;
		try {
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long gra = Long.parseLong(h.getGrado());
			long docente = Long.parseLong(h.getDocente());
			long ihReal = Long.parseLong(h.getIhReal());
			long jerGrado = Long.parseLong(h.getJerGrado());
			a = h.getHoras();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot
					.getString("horarioEstructura.select"));
			int posicion = 1;
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, h.getVigencia());
			rs = pst.executeQuery();
			if (rs.next()) {
				horas = rs.getInt(1);
			}
			rs.close();
			pst.close();
			if (h.getJerGrupo().equals("")) {
				setMensaje("No se pudo guardar el horario debido a que el grupo no esta registrado.");
				return false;
			}
			pst = cn.prepareStatement(rbRot.getString("horario.delete"));
			pst.setLong(pos++, Long.parseLong(h.getJerGrupo()));
			pst.setLong(pos++, h.getVigencia());
			int n = pst.executeUpdate();
			pst.close();
			int[] hh = new int[horas];
			if (a != null) {
				pos = 1;
				// System.out.println("long de a="+a.length);
				for (int j = 0; j < a.length; j++) {
					if (a[j].trim().equals("null")) {
						return true;
					}
					if (!a[j].trim().equals("")) {
						// System.out.println("valor de A="+a[j]);
						pos = 1;
						if (a[j].trim().endsWith("|"))
							a[j] = a[j].substring(0, a[j].length());
						a_ = a[j].replace('|', ':').split(":");
						gru = Long.parseLong(a_[0]);
						hora = Integer.parseInt(a_[1]);
						dia = Integer.parseInt(a_[2]);
						asig = Long.parseLong(a_[3]);
						doc = Long.parseLong(a_[4]);
						band = false;
						if (hh[hora - 1] == 0) {
							band = true;
							hh[hora - 1] = 1;
						}
						if (band) {
							// System.out.print("inserta=");
							pst = cn.prepareStatement(rbRot
									.getString("horario.insert" + dia));
						} else {
							// System.out.print("actualiza=");
							pst = cn.prepareStatement(rbRot
									.getString("horario.update" + dia));
						}
						pst.setLong(pos++, doc);
						pst.setLong(pos++, asig);
						if (a_.length == 6)
							pst.setLong(pos++, Long.parseLong(a_[5]));
						else
							pst.setNull(pos++, java.sql.Types.VARCHAR);
						pst.setLong(pos++, gru);
						pst.setLong(pos++, hora);
						pst.setLong(pos++, h.getVigencia());
						n = pst.executeUpdate();
						pst.close();
						// System.out.println("cantidad="+n);
					}
				}
				// actualizar IH REAL
				asig = Long.parseLong(h.getAsignatura());
				pst = cn.prepareStatement(rbRot
						.getString("horario.actualizarIH"));
				pos = 1;
				pst.setLong(pos++, ihReal);
				pst.setLong(pos++, docente);
				pst.setLong(pos++, asig);
				pst.setLong(pos++, jerGrado);
				n = pst.executeUpdate();
				pst.close();
			}
			cn.commit();
			cn.setAutoCommit(true);
			return true;
		} catch (Exception e) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			e.printStackTrace();
			System.out.println("ERROR INSERTAR HOR: " + e);
			// siges.util.Logger.print("-99","Error parametros:
			// "+e,0,1,this.toString());
			setMensaje("" + e);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public String getEditable(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String valor = "2";
		boolean edit = false;
		try {
			long institucion = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long grado = Long.parseLong(h.getGrado());
			long grupo = Long.parseLong(h.getGrupo());
			long gradoRot = Long.parseLong(h.getGradoRotacion());
			long grupoRot = Long.parseLong(h.getGrupoRotacion());

			if (gradoRot == -9 && grupoRot == -9) {// todo es editable
				valor = "1";// si es editable
				edit = true;
			}
			if (gradoRot != -9 && grupoRot == -9) {// solo los grupo de ese
													// grados
				if (gradoRot == grado) {
					valor = "1";// si es editable
					edit = true;
				} else {
					valor = "2";// No es editable
				}
			}
			if (gradoRot != -9 && grupoRot != -9) {// solo este grado
				if (gradoRot == grado && grupoRot == grupo) {
					valor = "1";// si es editable
					edit = true;
				} else {
					valor = "2";// NO es editable
				}
			}
			if (edit) {
				// System.out.println("vALOR DE ESTADO DE GRUPO: ENTRA POR EDIT=TRUE");
				cn = cursor.getConnection();
				pst = cn.prepareStatement(rbRot
						.getString("rot2.getEstadoGrupo"));
				pst.clearParameters();
				int posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, grado);
				pst.setLong(posicion++, grupo);
				rs = pst.executeQuery();
				long est = -1;
				if (rs.next()) {
					// System.out.println("vALOR DE ESTADO DE GRUPO: SI ARROJA EL NEXT"
					// + rs.getInt(1));
					est = rs.getInt(1);
				}
				if (est > 1) {
					valor = "" + est;// NO es editable
				}
			}
			// System.out.println("vALOR DE ESTADO DE GRUPO: " + valor);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetFiltroRot2: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return valor;
	}

	public List getHorarioPropuestoAsignatura(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List l = new ArrayList();
		List listaGrupo = new ArrayList();
		long a[] = null;
		try {
			int clases = 0;
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jornada = Long.parseLong(h.getJornada());
			long metodologia = Long.parseLong(h.getMetodologia());
			long grado = Long.parseLong(h.getGrado());
			// long grupo=Long.parseLong(h.getGrupo());
			long vigencia = h.getVigencia();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.allGrupo"));
			pst.setLong(1, inst);
			pst.setLong(2, sede);
			pst.setLong(3, jornada);
			pst.setLong(4, metodologia);
			pst.setLong(5, grado);
			rs = pst.executeQuery();
			while (rs.next()) {
				a = new long[2];
				a[0] = rs.getLong(1);
				a[1] = rs.getLong(2);
				listaGrupo.add(a);
			}
			rs.close();
			pst.close();
			long grupos[][] = new long[listaGrupo.size()][2];
			for (int i = 0; i < listaGrupo.size(); i++) {
				a = (long[]) listaGrupo.get(i);
				grupos[i] = a;
			}
			// traer la cantidad de clases del horario
			pst = cn.prepareStatement(rbRot
					.getString("horarioEstructura.select"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jornada);
			pst.setLong(pos++, grado);
			pst.setLong(pos++, vigencia);
			rs = pst.executeQuery();
			if (rs.next()) {
				clases = rs.getInt(1);
			}
			rs.close();
			pst.close();
			// traer las asignaturas del grado
			HorarioAsignaturaVO horarioAsignaturaVO = null;
			pst = cn.prepareStatement(rbRot.getString("horario.asignatura"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, metodologia);
			pst.setLong(pos++, grado);
			pst.setLong(pos++, vigencia);
			rs = pst.executeQuery();
			while (rs.next()) {
				horarioAsignaturaVO = new HorarioAsignaturaVO();
				horarioAsignaturaVO.setNombreAsignatura(rs.getString(2) + " ("
						+ rs.getString(4) + ")");
				horarioAsignaturaVO.setListaClase(getListaClase(cn, h, grupos,
						clases, rs.getLong(1)));
				l.add(horarioAsignaturaVO);
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetHorarioAsignaturaPropuesto: " + e);
			setMensaje(e.getMessage());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getHorarioPropuestoGrupo(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.grupo"));
			pst.setLong(1, Long.parseLong(h.getInst()));
			pst.setLong(2, Long.parseLong(h.getSede()));
			pst.setLong(3, Long.parseLong(h.getJornada()));
			pst.setLong(4, Long.parseLong(h.getMetodologia()));
			pst.setLong(5, Long.parseLong(h.getGrado()));
			pst.setLong(6, Long.parseLong(h.getGrupo()));
			rs = pst.executeQuery();
			if (rs.next()) {
				jerarquia = rs.getString(1);
			}
			if (jerarquia == null || jerarquia.trim().equals("")) {
				setMensaje("Grupo no encontrado");
				System.out.println("Jerarquia de Grupo no encontrado");
				return null;
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rbRot
					.getString("horario.selectPropuesto"));
			int pos = 1;
			pst.setLong(pos++, Long.parseLong(jerarquia));
			rs = pst.executeQuery();
			list = getCollection(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetHorarioPropuesto: " + e);
			// siges.util.Logger.print("-99","Error parametros:
			// "+e,0,1,this.toString());
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection getInconsistenciasGenerales(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			long id = Long.parseLong(h.getIdSolicitud());
			int pos = 1;
			pst = cn.prepareStatement(rbRot
					.getString("rot2.inconsistenciaGeneral"));
			pst.setLong(pos++, id);
			rs = pst.executeQuery();
			list = getCollection(rs);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetInconsistenciasGenerales: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return list;
	}

	public List getListaClase(Connection cn, Horario h, long[][] grupos,
			int clases, long asignatura) throws Exception {
		List l = new ArrayList();
		try {
			ClaseAsignaturaVO claseAsignaturaVO = null;
			for (int i = 1; i <= clases; i++) {
				claseAsignaturaVO = new ClaseAsignaturaVO();
				claseAsignaturaVO.setNombreClase(String.valueOf(i));
				claseAsignaturaVO.setListaDia(getListaDia(cn, h, grupos,
						clases, asignatura, i));
				l.add(claseAsignaturaVO);
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public List getListaDia(Connection cn, Horario h, long[][] grupos,
			int clases, long asignatura, int clase) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List l = new ArrayList();
		int pos = 1;
		try {
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			DiaAsignaturaVO diaAsignaturaVO = null;
			for (int i = 1; i <= 5; i++) {
				diaAsignaturaVO = new DiaAsignaturaVO();
				for (int j = 0; j < grupos.length; j++) {
					pst = cn.prepareStatement(rbRot.getString("horario.getDia."
							+ i));
					pos = 1;
					pst.setLong(pos++, grupos[j][0]);
					pst.setLong(pos++, clase);
					pst.setLong(pos++, asignatura);
					pst.setLong(pos++, inst);
					pst.setLong(pos++, sede);
					// System.out.println("VALORES DE CONSULTA: "+grupos[j][0]+"//"+clase+"//"+asignatura);
					rs = pst.executeQuery();
					if (rs.next()) {
						if (diaAsignaturaVO.getEstado() == 0) {
							diaAsignaturaVO.setNombreEspacio(rs.getString(1));
							diaAsignaturaVO.setNombreDocente(rs.getString(2));
							diaAsignaturaVO.setNombreGrupo(String
									.valueOf(grupos[j][1]));
							diaAsignaturaVO.setEstado(1);
						} else if (diaAsignaturaVO.getEstado() == 1) {
							diaAsignaturaVO.setNombreEspacio2(rs.getString(1));
							diaAsignaturaVO.setNombreDocente2(rs.getString(2));
							diaAsignaturaVO.setNombreGrupo2(String
									.valueOf(grupos[j][1]));
							diaAsignaturaVO.setEstado(2);
						} else if (diaAsignaturaVO.getEstado() == 2) {
							diaAsignaturaVO.setNombreEspacio3(rs.getString(1));
							diaAsignaturaVO.setNombreDocente3(rs.getString(2));
							diaAsignaturaVO.setNombreGrupo3(String
									.valueOf(grupos[j][1]));
							diaAsignaturaVO.setEstado(3);
						}
					}
					rs.close();
					pst.close();
				}
				l.add(diaAsignaturaVO);
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
			} catch (Exception e) {
			}
		}
	}

	public Collection getEstadoGrupos(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGradoRotacion());
			long gru = Long.parseLong(h.getGrupoRotacion());
			cn = cursor.getConnection();
			if (gra == -9 && gru == -9) {
				pst = cn.prepareStatement(rbRot
						.getString("rot2.getEstadoGrupos1"));
				int pos = 1;
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, jor);
				pst.setLong(pos++, met);
			}
			if (gra != -9 && gru == -9) {
				pst = cn.prepareStatement(rbRot
						.getString("rot2.getEstadoGrupos2"));
				int pos = 1;
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, jor);
				pst.setLong(pos++, met);
				pst.setLong(pos++, gra);
			}
			if (gra != -9 && gru != -9) {
				pst = cn.prepareStatement(rbRot
						.getString("rot2.getEstadoGrupos3"));
				int pos = 1;
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, jor);
				pst.setLong(pos++, met);
				pst.setLong(pos++, gra);
				pst.setLong(pos++, gru);
			}
			rs = pst.executeQuery();
			list = getCollection(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetEstado de Grupos: " + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public Collection[] getFiltroRot2(Login l, Horario h) {
		Collection[] list = new Collection[2];
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long inst = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			// long gra = Long.parseLong(h.getGrado());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("rot2.filtroGrado"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			rs = pst.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rbRot.getString("rot2.filtroGrupo"));
			pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			pst.setLong(pos++, met);
			rs = pst.executeQuery();
			list[1] = getCollection(rs);
			rs.close();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetFiltroRot2: " + e);
			setMensaje("" + e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return list;
	}

	public List getHorarioFalla(Horario h) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List l = new ArrayList();
		long jerGrupo = 0;
		try {
			int clases = 0;
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jornada = Long.parseLong(h.getJornada());
			long metodologia = Long.parseLong(h.getMetodologia());
			long grado = Long.parseLong(h.getGrado());
			long grupo = Long.parseLong(h.getGrupo());
			long vigencia = h.getVigencia();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("horario.grupo"));
			pst.setLong(1, inst);
			pst.setLong(2, sede);
			pst.setLong(3, jornada);
			pst.setLong(4, metodologia);
			pst.setLong(5, grado);
			pst.setLong(6, grupo);
			rs = pst.executeQuery();
			if (rs.next()) {
				jerGrupo = rs.getLong(1);
			}
			rs.close();
			pst.close();
			// traer la cantidad de clases del horario
			pst = cn.prepareStatement(rbRot
					.getString("horarioEstructura.select"));
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jornada);
			pst.setLong(pos++, grado);
			pst.setLong(pos++, vigencia);
			rs = pst.executeQuery();
			if (rs.next()) {
				clases = rs.getInt(1);
			}
			rs.close();
			pst.close();
			// traer las iteraciones del grupo
			HorarioAsignaturaVO horarioAsignaturaVO = null;
			pst = cn.prepareStatement(rbRot
					.getString("horario.getIteracionesGrupo"));
			pos = 1;
			pst.setLong(pos++, jerGrupo);
			pst.setLong(pos++, inst);
			pst.setLong(pos++, metodologia);
			pst.setLong(pos++, vigencia);
			pst.setLong(pos++, grado);
			rs = pst.executeQuery();
			while (rs.next()) {
				horarioAsignaturaVO = new HorarioAsignaturaVO();
				horarioAsignaturaVO.setNombreIteracion(rs.getString(1));
				horarioAsignaturaVO.setNombreAsignatura(rs.getString(2));
				horarioAsignaturaVO.setListaClase(getListaClase(cn, h,
						jerGrupo, clases, rs.getLong(1)));
				l.add(horarioAsignaturaVO);
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR GetHorarioAsignaturaPropuesto: " + e);
			setMensaje(e.getMessage());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	public List getListaClase(Connection cn, Horario h, long jerGrupo,
			int clases, long iteracion) throws Exception {
		List l = new ArrayList();
		try {
			ClaseAsignaturaVO claseAsignaturaVO = null;
			for (int i = 1; i <= clases; i++) {
				claseAsignaturaVO = new ClaseAsignaturaVO();
				claseAsignaturaVO.setNombreClase(String.valueOf(i));
				claseAsignaturaVO.setListaDia(getListaDia(cn, h, jerGrupo,
						clases, iteracion, i));
				l.add(claseAsignaturaVO);
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public List getListaDia(Connection cn, Horario h, long jerGrupo,
			int clases, long iteracion, int clase) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List l = new ArrayList();
		int pos = 1;
		try {
			long inst = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			int met = Integer.parseInt(h.getMetodologia());
			long vig = h.getVigencia();
			DiaAsignaturaVO diaAsignaturaVO = null;
			for (int i = 1; i <= 5; i++) {
				diaAsignaturaVO = new DiaAsignaturaVO();
				pst = cn.prepareStatement(rbRot
						.getString("horario.getDiaFallo." + i));
				pos = 1;
				pst.setLong(pos++, jerGrupo);
				pst.setLong(pos++, iteracion);
				pst.setLong(pos++, clase);
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, met);
				pst.setLong(pos++, vig);
				// System.out.println("VALORES DE CONSULTA: "+grupos[j][0]+"//"+clase+"//"+asignatura);
				rs = pst.executeQuery();
				if (rs.next()) {
					diaAsignaturaVO.setNombreEspacio(rs.getString(1));
					diaAsignaturaVO.setNombreAsignatura(rs.getString(2));
					diaAsignaturaVO.setNombreDocente(rs.getString(3));
					diaAsignaturaVO.setNombreGrupo(h.getGrupo());
					diaAsignaturaVO.setEstado(1);
				}
				rs.close();
				pst.close();
				l.add(diaAsignaturaVO);
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
			} catch (Exception e) {
			}
		}
	}
}