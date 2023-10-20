package siges.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import siges.exceptions.InternalErrorException;
import siges.institucion.beans.Institucion;
import siges.institucion.beans.Sede;

/**
 * Nombre: Util Descripcion: Realiza operaciones en la BD Funciones de la
 * pagina: Realiza la logica de negocio Entidades afectadas: todas las tablas de
 * la BD Fecha de modificacinn: Feb-05
 * 
 * @author Latined
 * @version $v 1.3 $
 */
public class Integracion {
	private Cursor cursor;
	public String sentencia;
	public String mensaje;
	public String buscar;
	private java.text.SimpleDateFormat sdf, sdf2;
	private java.sql.Timestamp f2;
	private PreparedStatement pst, pst2, pst3;
	private Connection cn;
	private ResourceBundle rb, rb2;
	public int[] resultado;

	/**
	 * Constructor de la clase
	 **/
	public Integracion(Cursor cur) {
		this.cursor = cur;
		sentencia = null;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setLenient(false);
		sdf2.setLenient(false);
		rb = ResourceBundle.getBundle("integracion");
	}

	/**
	 * Funcinn: Cerrar conecciones<br>
	 **/
	public void cerrar() {
		try {
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeStatement(pst2);
			OperacionesGenerales.closeStatement(pst3);
			OperacionesGenerales.closeConnection(cn);
			rb = rb2 = null;
			if (cursor != null)
				cursor.cerrar();
		} catch (Exception e) {
		}
	}

	/*
	 * :new.IN_CODI_ID, //0 :new.CONS_SEDE, //1 :new.JR_CODI_ID, //2
	 * :new.CODIGO_METODOLOGIA, //3 :new.GR_CODI_ID, //4 :new.GP_CODI_ID, //5
	 * :new.DD_CODI_EXPE, //6 :new.DM_CODI_EXPE, //7 :new.TI_CODI_ID, //8
	 * :new.AL_NUME_ID, //9 :new.AL_PRIM_APEL, //10 :new.AL_SEGU_APEL, //11
	 * :new.AL_PRIM_NOMB, //12 :new.AL_SEGU_NOMB, //13 :new.AL_AO_ESTA, //14
	 */
	public boolean insertarAlumno(String[] p) {
		System.out.println("-SIGES- insertar alumno=");
		int posicion = 1, n;
		String consulta = "select INSCODDEPTO,INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane="
				+ p[0];
		Statement st = null;
		String[] inst = new String[4];
		String sed = "";
		long grado = -1, grupo = 0;
		ResultSet rs = null;
		// for(int k=0;k<p.length;k++) System.out.println("p["+k+"]="+p[k]);
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (!rs.next()) {
				setMensaje("Codigo DANE no existe");
				return false;
			} else {
				inst[0] = rs.getString(1);
				inst[1] = rs.getString(2);
				inst[2] = rs.getString(3);
				inst[3] = rs.getString(4);
				String consulta2 = "SELECT SEDCODIGO FROM SIGES.SEDE where SEDCODINS="
						+ inst[3] + " and SEDCODIGO=" + p[1];
				rs = st.executeQuery(consulta2);
				if (!rs.next()) {
					setMensaje("Codigo DANE de sede no existe");
					return false;
				} else {
					sed = rs.getString(1);
				}
			}
			// VALIDAR SI LA JORNADA YA EXISTE, SINO SE INSERTA/
			pst = cn.prepareStatement(rb
					.getString("jornada.consultaActualizar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst[3]));
			pst.setLong(posicion++, Long.parseLong(p[2].trim()));
			rs = pst.executeQuery();
			System.out.println("consulta para ver si jornada existe");
			if (!rs.next()) {
				// insertar en jornada
				// System.out.println(rb.getString("jornada.insertarJornada"));
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.insertarJornada"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				n = pst.executeUpdate();
				System.out.println("insertar jornada =" + n);
				// insertar en sede jornada
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.insertarSedeJornada"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(sed.trim()));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				n = pst.executeUpdate();
				System.out.println("insertar sede jornada =" + n);
				// insertar en sede jornada jerarquia
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.insertarJornadaJerarquia"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[0]));
				pst.setLong(posicion++, Long.parseLong(inst[1]));
				pst.setLong(posicion++, Long.parseLong(inst[2]));
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(sed.trim()));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				n = pst.executeUpdate();
				System.out.println("insertar sede jornada jerarquia=" + n);
			} else {
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.consultaActualizar2"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(sed.trim()));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				rs = pst.executeQuery();
				if (!rs.next()) {
					// insertar en sede jornada
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("jornada.insertarSedeJornada"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(sed.trim()));
					pst.setLong(posicion++, Long.parseLong(p[2].trim()));
					n = pst.executeUpdate();
					System.out.println("insertar sede jornada-- =" + n);
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("jornada.insertarJornadaJerarquia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[0]));
					pst.setLong(posicion++, Long.parseLong(inst[1]));
					pst.setLong(posicion++, Long.parseLong(inst[2]));
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(sed.trim()));
					pst.setLong(posicion++, Long.parseLong(p[2].trim()));
					n = pst.executeUpdate();
					System.out
							.println("insertar sede jornada jerarquia que estaba en grado="
									+ n);
				}
			}
			// VALIDAR SI LA METODOLOGIA YA EXISTE, SINO SE INSERTA
			pst.close();
			pst = cn.prepareStatement(rb
					.getString("metodologia.consultaActualizar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst[3]));
			pst.setLong(posicion++, Long.parseLong(p[3].trim()));
			rs = pst.executeQuery();
			System.out.println("consulta para ver si metodologia existe");
			if (!rs.next()) {
				// insertar en metodologia
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("metodologia.insertarMetodologia"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				n = pst.executeUpdate();
				System.out.println("insert metodologia =" + n);
			}
			// VALIDAR SI GRADO YA EXISTE, SINO SE INSERTA
			pst.close();
			pst = cn.prepareStatement(rb.getString("grado.consultaActualizar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst[3]));
			pst.setLong(posicion++, Long.parseLong(p[3].trim()));
			pst.setLong(posicion++, Long.parseLong(p[4].trim()));
			rs = pst.executeQuery();
			System.out.println("consulta para ver si grado existe");
			if (!rs.next()) {
				// insertar en grado
				pst.close();
				pst = cn.prepareStatement(rb.getString("grado.insertarGrado"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				n = pst.executeUpdate();
				System.out.println("insert grado =" + n);
				// insertar en grado jerartqui
				pst.close();
				pst = cn.prepareStatement(rb.getString("grado.insertarGrado2"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[0]));
				pst.setLong(posicion++, Long.parseLong(inst[1]));
				pst.setLong(posicion++, Long.parseLong(inst[2]));
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[1]));
				pst.setLong(posicion++, Long.parseLong(p[2]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				n = pst.executeUpdate();
				System.out.println("insert grado jerarquia =" + n);
			} else {
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("grupo.consultaJerarquia"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[1]));
				pst.setLong(posicion++, Long.parseLong(p[2]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				rs = pst.executeQuery();
				if (!rs.next()) {
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("grado.insertarGrado2"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[0]));
					pst.setLong(posicion++, Long.parseLong(inst[1]));
					pst.setLong(posicion++, Long.parseLong(inst[2]));
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(p[1]));
					pst.setLong(posicion++, Long.parseLong(p[2]));
					pst.setLong(posicion++, Long.parseLong(p[3].trim()));
					pst.setLong(posicion++, Long.parseLong(p[4].trim()));
					n = pst.executeUpdate();
					System.out.println("insert grado jerarquia =" + n);
				} else {
					grado = rs.getLong(1);
				}
			}
			// VALIDAR SI GRUPO EXISTE
			if (p[5] == null || p[5].equals("0") || p[5].equals("null")) {
				p[5] = "0";
			}
			if (!p[5].equals("0")) {
				if (grado == -1) {
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("grupo.consultaJerarquia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(p[1]));
					pst.setLong(posicion++, Long.parseLong(p[2]));
					pst.setLong(posicion++, Long.parseLong(p[3].trim()));
					pst.setLong(posicion++, Long.parseLong(p[4].trim()));
					rs = pst.executeQuery();
					if (rs.next()) {
						grado = rs.getLong(1);
					}// significa que tiene un codigo de jerarquia para grado
					System.out.println("Traer jerarquia de grado=" + grado);
				}
				if (grado != -1) {
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("grupo.consultaActualizar"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, grado);
					pst.setLong(posicion++, Long.parseLong(p[5].trim()));
					rs = pst.executeQuery();
					System.out.println("consulta para ver si existe grupo");
					if (!rs.next()) {
						// INSERTAR EN GRUPO y jerarquia el grupo que no existe
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("grupo.insertarGrupo"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, grado);
						pst.setLong(posicion++, Long.parseLong(p[5]));
						pst.setString(posicion++, p[5]);
						n = pst.executeUpdate();
						System.out.println("insert grupo =" + n);
						// insertar en grupo de jerarquia
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("grupo.insertarGrupo2"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, Long.parseLong(inst[0]));
						pst.setLong(posicion++, Long.parseLong(inst[1]));
						pst.setLong(posicion++, Long.parseLong(inst[2]));
						pst.setLong(posicion++, Long.parseLong(inst[3]));
						pst.setLong(posicion++, Long.parseLong(sed));
						pst.setLong(posicion++, Long.parseLong(p[2]));
						pst.setLong(posicion++, Long.parseLong(p[3]));
						pst.setLong(posicion++, Long.parseLong(p[4].trim()));
						pst.setLong(posicion++, Long.parseLong(p[5].trim()));
						n = pst.executeUpdate();
						System.out.println("insert grupo jerar=" + n);
					} else {
						// actualizar cupo +1
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("grupo.ActualizarCupo"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, grado);
						pst.setLong(posicion++, Long.parseLong(p[5]));
						n = pst.executeUpdate();
						System.out.println("Actualizar cupo=" + n);
					}
				}
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("estudiante.consultaGrupo"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[1]));
				pst.setLong(posicion++, Long.parseLong(p[2]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				pst.setLong(posicion++, Long.parseLong(p[5].trim()));
				rs = pst.executeQuery();
				System.out.println("Traer jerarquia de grupo");
				if (rs.next()) {
					grupo = rs.getLong(1);
				} else {
					System.out.println("no hay grupo en jerarquia");
				}
			} else {
				System.out.println("grupo es 0 desde afuera");
			}
			/* INSERTAR AL ESTUDIANTE */
			pst.close();
			pst = cn.prepareStatement(rb.getString("estudiante.insertar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(p[6]));
			pst.setLong(posicion++, Long.parseLong(p[7]));
			pst.setLong(posicion++, Long.parseLong(p[8]));
			pst.setString(posicion++, p[9]);
			pst.setString(posicion++, p[10]);
			pst.setString(posicion++, p[11]);
			pst.setString(posicion++, p[12]);
			pst.setString(posicion++, p[13]);
			pst.setLong(posicion++, Long.parseLong(p[14]));
			pst.setLong(posicion++, grupo);
			n = pst.executeUpdate();
			System.out.println("inserto estudiante =" + n + " en grupo:"
					+ grupo);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarIdAlumno(String[] p) {
		int posicion = 1;
		String consulta = "SELECT ESTCODIGO FROM SIGES.ESTUDIANTE where ESTNUMDOC='"
				+ p[4] + "' and ESTTIPODOC=" + p[5];
		Statement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar si este id no existe en otro estudiante
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (!rs.next() || p[4].equals(p[3])) {
				// Hacer cambios de una
				pst = cn.prepareStatement(rb
						.getString("estudiante.actualizarId"));
				pst.clearParameters();
				posicion = 1;
				if (p[0] == null)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, p[0].trim());
				if (p[1] == null)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, p[1].trim());
				if (p[2] == null)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, p[2].trim());
				if (p[4] == null)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, p[4].trim());
				if (p[6] == null)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, p[6].trim());
				if (p[3] == null)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, p[3].trim());
				if (p[5] == null)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, p[5].trim());
				int n = pst.executeUpdate();
				System.out.println("cambio Id a estudiante:" + n);
			} else {
				setMensaje("La identificacinn ya existe");
				return false;
			}
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarNombreAlumno(String[] p) {
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb
					.getString("estudiante.actualizarNombre"));
			pst.clearParameters();
			posicion = 1;
			if (p[0] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, p[0].trim());
			if (p[1] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, p[1].trim());
			if (p[2] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, p[2].trim());
			if (p[3] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, p[3].trim());
			if (p[4] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
			pst.setString(posicion++, p[8].trim());
			// pst.setString(posicion++,p[9].trim());
			pst.setDate(posicion++, new java.sql.Date(sdf.parse(p[9].trim())
					.getTime()));
			// pst.setNull(posicion++,java.sql.Types.VARCHAR);
			if (p[5] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, p[5].trim());
			if (p[6] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(p[6].trim()));
			int n = pst.executeUpdate();
			System.out.println("cambio Nombre a estudiante=" + n);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("parseando fecha: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
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

	public boolean actualizarVigenciaAlumno(String[] p) {
		int posicion = 1;
		try {
			// System.out.println("fecha: "+p[2]);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb
					.getString("estudiante.actualizarVigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setTimestamp(posicion++,
					new java.sql.Timestamp(sdf2.parse(p[2].trim()).getTime()));
			if (p[0] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(p[0].trim()));
			if (p[1] == null)
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, p[1].trim());
			int n = pst.executeUpdate();
			System.out.println("cambio Vigencia a estudiante=" + n);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Parseando fecha: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
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

	public int existeEstudiate(String tipo, String id) {
		String consulta = "SELECT ESTCODIGO FROM SIGES.ESTUDIANTE where ESTTIPODOC="
				+ tipo + " and ESTNUMDOC='" + id + "'";
		Statement st = null;
		ResultSet rs = null;
		try {
			// System.out.println(consulta);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (!rs.next()) {
				return 0;
			}
		} catch (InternalErrorException in) {
			System.out.println("-SIGES- error en INTEGRACION existe:" + in);
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return -1;
		} catch (SQLException sqle) {
			System.out.println("-SIGES- error en INTEGRACION existe:" + sqle);
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return -1;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return 1;
	}

	/**
	 * Funcinn: Insertar Institucinn<br>
	 * 
	 * @param Institucion
	 *            i
	 * @return boolean
	 * 
	 *         :new.IN_CODI_ID, //0 :new.CONS_SEDE, //1 :new.JR_CODI_ID,2
	 *         :new.CODIGO_METODOLOGIA,3 :new.GR_CODI_ID,4 :new.GP_CODI_ID,5
	 *         :new.TI_CODI_ID,8 :new.AL_NUME_ID,9
	 **/
	public boolean actualizarAlumno(String[] p) {
		// System.out.println("-SIGES- actualizar alumno=");
		int posicion = 1, n;
		String consulta = "select INSCODDEPTO,INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane="
				+ p[0];
		Statement st = null;
		String[] inst = new String[4];
		String[] old = new String[6];
		String sed = "";
		long grado = -1, grupo = 0;
		ResultSet rs = null;
		// for(int k=0;k<p.length;k++) System.out.println("p["+k+"]="+p[k]);
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (!rs.next()) {
				setMensaje("Codigo DANE no existe");
				return false;
			} else {
				inst[0] = rs.getString(1);
				inst[1] = rs.getString(2);
				inst[2] = rs.getString(3);
				inst[3] = rs.getString(4);
				rs.close();
				String consulta2 = "SELECT SEDCODIGO FROM SIGES.SEDE where SEDCODINS="
						+ inst[3] + " and SEDCODIGO=" + p[1];
				// System.out.println(consulta2);
				rs = st.executeQuery(consulta2);
				if (!rs.next()) {
					setMensaje("Codigo DANE de sede no existe");
					return false;
				} else {
					sed = rs.getString(1);
					rs.close();
				}
			}
			// traer la informacion actual del alumno
			pst = cn.prepareStatement(rb
					.getString("estudiante.ConsultarJerarquia"));
			// System.out.println(rb.getString("estudiante.ConsultarJerarquia"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(p[8]));
			pst.setString(posicion++, (p[9]));
			rs = pst.executeQuery();
			if (rs.next()) {
				old[0] = rs.getString(1);
				old[1] = rs.getString(2);
				old[2] = rs.getString(3);
				old[3] = rs.getString(4);
				old[4] = rs.getString(5);
				old[5] = rs.getString(6);
				rs.close();
			}
			// else {
			// System.out.println("no hay registros " + p[8] + "/" + p[9]);
			// }
			pst.close();
			// VALIDAR SI LA JORNADA YA EXISTE, SINO SE INSERTA/
			pst = cn.prepareStatement(rb
					.getString("jornada.consultaActualizar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst[3]));
			pst.setLong(posicion++, Long.parseLong(p[2].trim()));
			rs = pst.executeQuery();
			// System.out.println("consulta para ver si jornada existe");
			if (!rs.next()) {
				// insertar en jornada
				// System.out.println(rb.getString("jornada.insertarJornada"));
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.insertarJornada"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				n = pst.executeUpdate();
				// System.out.println("insertar jornada =" + n);
				// insertar en sede jornada
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.insertarSedeJornada"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(sed.trim()));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				n = pst.executeUpdate();
				// System.out.println("insertar sede jornada =" + n);
				// insertar en sede jornada jerarquia
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.insertarJornadaJerarquia"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[0]));
				pst.setLong(posicion++, Long.parseLong(inst[1]));
				pst.setLong(posicion++, Long.parseLong(inst[2]));
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(sed.trim()));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				n = pst.executeUpdate();
				// System.out.println("insertar sede jornada jerarquia=" + n);
			} else {
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("jornada.consultaActualizar2"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(sed.trim()));
				pst.setLong(posicion++, Long.parseLong(p[2].trim()));
				rs = pst.executeQuery();
				if (!rs.next()) {
					// insertar en sede jornada
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("jornada.insertarSedeJornada"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(sed.trim()));
					pst.setLong(posicion++, Long.parseLong(p[2].trim()));
					n = pst.executeUpdate();
					// System.out.println("insertar sede jornada =" + n);
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("jornada.insertarJornadaJerarquia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[0]));
					pst.setLong(posicion++, Long.parseLong(inst[1]));
					pst.setLong(posicion++, Long.parseLong(inst[2]));
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(sed.trim()));
					pst.setLong(posicion++, Long.parseLong(p[2].trim()));
					n = pst.executeUpdate();
					// System.out
					// .println("insertar sede jornada jerarquia que estaba en grado="
					// + n);
				}
			}
			// VALIDAR SI LA METODOLOGIA YA EXISTE, SINO SE INSERTA
			pst.close();
			pst = cn.prepareStatement(rb
					.getString("metodologia.consultaActualizar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst[3]));
			pst.setLong(posicion++, Long.parseLong(p[3].trim()));
			rs = pst.executeQuery();
			// System.out.println("consulta para ver si metodologia existe");
			if (!rs.next()) {
				// insertar en metodologia
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("metodologia.insertarMetodologia"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				n = pst.executeUpdate();
				// System.out.println("insert metodologia =" + n);
			}
			// VALIDAR SI GRADO YA EXISTE, SINO SE INSERTA
			pst.close();
			pst = cn.prepareStatement(rb.getString("grado.consultaActualizar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst[3]));
			pst.setLong(posicion++, Long.parseLong(p[3].trim()));
			pst.setLong(posicion++, Long.parseLong(p[4].trim()));
			rs = pst.executeQuery();
			// System.out.println("consulta para ver si grado existe");
			if (!rs.next()) {
				// insertar en grado
				pst.close();
				pst = cn.prepareStatement(rb.getString("grado.insertarGrado"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				n = pst.executeUpdate();
				// System.out.println("insert grado =" + n);
				// insertar en grado jerartqui
				pst.close();
				pst = cn.prepareStatement(rb.getString("grado.insertarGrado2"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[0]));
				pst.setLong(posicion++, Long.parseLong(inst[1]));
				pst.setLong(posicion++, Long.parseLong(inst[2]));
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[1]));
				pst.setLong(posicion++, Long.parseLong(p[2]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				n = pst.executeUpdate();
				// System.out.println("insert grado jerarquia =" + n);
			} else {
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("grupo.consultaJerarquia"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[1]));
				pst.setLong(posicion++, Long.parseLong(p[2]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				rs = pst.executeQuery();
				if (!rs.next()) {
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("grado.insertarGrado2"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[0]));
					pst.setLong(posicion++, Long.parseLong(inst[1]));
					pst.setLong(posicion++, Long.parseLong(inst[2]));
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(p[1]));
					pst.setLong(posicion++, Long.parseLong(p[2]));
					pst.setLong(posicion++, Long.parseLong(p[3].trim()));
					pst.setLong(posicion++, Long.parseLong(p[4].trim()));
					n = pst.executeUpdate();
					// System.out.println("insert grado jerarquia 2=" + n);
				} else {
					grado = rs.getLong(1);
					// System.out
					// .println("Grado ya existe con jerarquia:" + grado);
				}
			}
			// VALIDAR SI GRUPO EXISTE
			if (p[5] == null || p[5].equals("0") || p[5].equals("null")) {
				p[5] = "0";
			}
			if (!p[5].equals("0")) {
				if (grado == -1) {
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("grupo.consultaJerarquia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, Long.parseLong(inst[3]));
					pst.setLong(posicion++, Long.parseLong(p[1]));
					pst.setLong(posicion++, Long.parseLong(p[2]));
					pst.setLong(posicion++, Long.parseLong(p[3].trim()));
					pst.setLong(posicion++, Long.parseLong(p[4].trim()));
					rs = pst.executeQuery();
					if (rs.next()) {
						grado = rs.getLong(1);
					}// significa que tiene un codigo de jerarquia para grado
						// System.out.println("Traer jerarquia de grado=" +
						// grado);
				}
				if (grado != -1) {
					pst.close();
					pst = cn.prepareStatement(rb
							.getString("grupo.consultaActualizar"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, grado);
					pst.setLong(posicion++, Long.parseLong(p[5].trim()));
					rs = pst.executeQuery();
					// System.out.println("consulta para ver si existe grupo");
					if (!rs.next()) {
						// INSERTAR EN GRUPO y jerarquia el grupo que no existe
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("grupo.insertarGrupo"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, grado);
						pst.setLong(posicion++, Long.parseLong(p[5]));
						pst.setString(posicion++, p[5]);
						n = pst.executeUpdate();
						System.out.println("insert grupo =" + n
								+ " para grupo: " + p[5]);
						// insertar en grupo de jerarquia
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("grupo.insertarGrupo2"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, Long.parseLong(inst[0]));
						pst.setLong(posicion++, Long.parseLong(inst[1]));
						pst.setLong(posicion++, Long.parseLong(inst[2]));
						pst.setLong(posicion++, Long.parseLong(inst[3]));
						pst.setLong(posicion++, Long.parseLong(sed));
						pst.setLong(posicion++, Long.parseLong(p[2]));
						pst.setLong(posicion++, Long.parseLong(p[3]));
						pst.setLong(posicion++, Long.parseLong(p[4].trim()));
						pst.setLong(posicion++, Long.parseLong(p[5].trim()));
						n = pst.executeUpdate();
						// System.out.println("insert grupo jerar=" + n);
					}
				}
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("estudiante.consultaGrupo"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst[3]));
				pst.setLong(posicion++, Long.parseLong(p[1]));
				pst.setLong(posicion++, Long.parseLong(p[2]));
				pst.setLong(posicion++, Long.parseLong(p[3].trim()));
				pst.setLong(posicion++, Long.parseLong(p[4].trim()));
				pst.setLong(posicion++, Long.parseLong(p[5].trim()));
				rs = pst.executeQuery();
				// System.out.println("Traer jerarquia de grupo");
				if (rs.next()) {
					grupo = rs.getLong(1);
					// System.out.println("grupo es=" + grupo);
				} else {
					// System.out.println("no hay grupo en jerarquia");
				}
			} else {
				// System.out.println("grupo es 0 desde afuera");
			}
			/* ACTUALIZAR AL ESTUDIANTE */
			pst.close();
			pst = cn.prepareStatement(rb.getString("estudiante.actualizar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, grupo);
			pst.setString(posicion++, p[9]);
			n = pst.executeUpdate();
			// System.out.println("--" + grupo + " -- " + p[9]);
			// System.out.println("ACTUALIZO estudiante=" + n);
			// INSERTAR HISTORICO DE CAMBIOS
			pst.close();
			pst = cn.prepareStatement(rb.getString("historicoEstudiante"));
			pst.clearParameters();
			posicion = 1;
			int tipo = 0;
			if (!inst[3].equals(old[0])) {
				tipo = 1;
			}
			if (!p[1].equals(old[1])) {
				tipo = 2;
			}
			if (!p[2].equals(old[2])) {
				tipo = 3;
			}
			if (!p[3].equals(old[3])) {
				tipo = 4;
			}
			if (!p[4].equals(old[4])) {
				tipo = 5;
			}
			if (!p[5].equals(old[5])) {
				tipo = 6;
			}
			pst.setLong(posicion++, tipo);
			pst.setLong(posicion++,
					Long.parseLong(old[0] != null ? old[0] : "0"));
			pst.setLong(posicion++,
					Long.parseLong(inst[3] != null ? inst[3] : "0"));
			pst.setLong(posicion++,
					Long.parseLong(old[1] != null ? old[1] : "0"));
			pst.setLong(posicion++, Long.parseLong(p[1] != null ? p[1] : "0"));
			pst.setLong(posicion++,
					Long.parseLong(old[2] != null ? old[2] : "0"));
			pst.setLong(posicion++, Long.parseLong(p[2] != null ? p[2] : "0"));
			pst.setLong(posicion++,
					Long.parseLong(old[3] != null ? old[3] : "0"));
			pst.setLong(posicion++, Long.parseLong(p[3] != null ? p[3] : "0"));
			pst.setLong(posicion++,
					Long.parseLong(old[4] != null ? old[4] : "0"));
			pst.setLong(posicion++, Long.parseLong(p[4] != null ? p[4] : "0"));
			pst.setLong(posicion++,
					Long.parseLong(old[5] != null ? old[5] : "0"));
			pst.setLong(posicion++, Long.parseLong(p[5] != null ? p[5] : "0"));
			pst.setLong(posicion++, Long.parseLong(p[9] != null ? p[9] : "0"));
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			n = pst.executeUpdate();
			System.out.println("Inserto historico=" + n);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (Exception sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema-: " + sqle);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Insertar Institucinn<br>
	 * 
	 * @param Institucion
	 *            i
	 * @return boolean
	 **/
	public boolean eliminarSede(String[] as) {
		String daneinst = as[0];
		String danesede = as[1];
		String cons = as[2];
		int posicion = 1;
		String consulta = "select inscodigo from institucion where inscoddane="
				+ daneinst;
		Statement st = null;
		String inst = "";
		ResultSet rs = null;
		try {// inst viejo,inst nuevo, dane sede, cons sede
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next())
				inst = rs.getString(1);
			// validar si tiene jornadas
			pst = cn.prepareStatement(rb.getString("sede.deleteSede0"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst.trim()));
			pst.setLong(posicion++, Long.parseLong(cons.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				// poner estado en 'I'
				pst.close();
				pst = cn.prepareStatement(rb.getString("sede.estadoSede"));
				pst.clearParameters();
				posicion = 1;
				pst.setString(posicion++, "I");
				pst.setLong(posicion++, Long.parseLong(inst.trim()));
				pst.setLong(posicion++, Long.parseLong(cons.trim()));
				int n = pst.executeUpdate();
				// System.out.println("cambio estado a sede =" + n);
				setMensaje("La sede tiene jornadas, se cambia estado a Inactiva");
			} else {
				// elimina en sede y jerarquia
				if (daneinst != null && danesede != null && cons != null) {
					for (int j = 1; j <= 2; j++) {
						pst = cn.prepareStatement(rb
								.getString("sede.deleteSede" + j));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, Long.parseLong(inst.trim()));
						pst.setLong(posicion++, Long.parseLong(cons.trim()));
						int n = pst.executeUpdate();
						pst.close();
						// System.out
						// .println("elimino sede para  " + j + " =" + n);
					}
				}
			}
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Insertar Institucinn<br>
	 * 
	 * @param Institucion
	 *            i
	 * @return boolean
	 **/
	public boolean insertarSede(Sede s) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		String consulta = "select inscodigo,inscodlocal from institucion where inscoddane="
				+ s.getSedcodins();
		Statement st = null;
		String jer = "", jer2 = "";
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next()) {
				jer = rs.getString(1);
				jer2 = rs.getString(2);
			}
			pst = cn.prepareStatement(rb.getString("sede.insertar"));
			pst.clearParameters();
			// sedcodins,sedcodigo,sedcoddaneanterior,sednombre
			if (jer.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(jer.trim()));
			if (s.getSedcodigo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(s.getSedcodigo().trim()));
			if (s.getSedcoddaneanterior().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(s.getSedcoddaneanterior().trim()));
			if (s.getSednombre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSednombre().trim());
			int n = pst.executeUpdate();
			// System.out.println("insertando la sede " + n);
			st = cn.createStatement();
			posicion = 1;
			pst.close();
			pst = cn.prepareStatement(rb.getString("sede.insertar2"));
			pst.clearParameters();
			// G_JerDepto, G_JerMunic, G_JerLocal, G_JerInst, G_JerSede
			if (s.getSedresguardo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(s.getSedresguardo().trim()));
			if (s.getSeddireccion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(s.getSeddireccion().trim()));
			if (jer2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(jer2.trim()));
			if (jer.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(jer.trim()));
			if (s.getSedcodigo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(s.getSedcodigo().trim()));
			n = pst.executeUpdate();
			// System.out.println("insertando jerarquia en sede " + n);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException ss) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Insertar Institucinn<br>
	 * 
	 * @param Institucion
	 *            i
	 * @return boolean
	 **/
	public boolean insertarInsitucion(Institucion i) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		String consulta = "select inscodigo from institucion where inscoddane="
				+ i.getInscoddane();
		Statement st = null;
		String jer = "";
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("institucion.insertar"));
			pst.clearParameters();
			// 1
			if (i.getInscoddane().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(i.getInscoddane().trim()));
			// 2
			if (i.getInscoddepto().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(i.getInscoddepto().trim()));
			// 3
			if (i.getInscodmun().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(i.getInscodmun().trim()));
			// 4
			if (i.getInscodlocal().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(i.getInscodlocal().trim()));
			// 5
			if (i.getInsnombre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, i.getInsnombre().trim());
			// 6
			if (i.getInsestado().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, i.getInsestado().trim());
			// 7
			if (i.getInssector().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(i.getInssector().trim()));
			int n = pst.executeUpdate();
			// System.out.println("inserto institucion:" + n);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next())
				jer = rs.getString(1);
			posicion = 1;
			pst.close();
			pst = cn.prepareStatement(rb.getString("institucion.insertar2"));
			pst.clearParameters();
			pst.setInt(posicion++, 1);// TIPO 1
			pst.setInt(posicion++, 4);// NIVEL=4 INSTITUCIONES
			if (i.getInscoddepto().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(i.getInscoddepto().trim()));
			if (i.getInscodmun().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(i.getInscodmun().trim()));
			if (i.getInscodlocal().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(i.getInscodlocal().trim()));
			if (jer.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(jer.trim()));
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			n = pst.executeUpdate();
			// System.out.println("insertando la institucion en jer:" + n);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			System.out.println("System " + sqle);
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarSectorInsitucion(String[] as) {
		String a = as[0];
		String b = as[1];
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("institucion.updateSector"));
			pst.clearParameters();
			if (a != null && b != null) {
				pst.setString(posicion++, b.equals("2") ? "I" : "A");
				pst.setString(posicion++, b);
				if (a.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(a.trim()));
				int n = pst.executeUpdate();
				// System.out.println("Actualizo sector: " + n);
				cn.commit();
				cn.setAutoCommit(true);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
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

	public boolean actualizarNombreInsitucion(String[] as) {
		String a = as[0];
		String b = as[1];
		String c = as[2];
		String consulta = "select inscodigo,insnombre from institucion where inscoddane="
				+ as[0];
		long inst = 0;
		String nom = "";
		Statement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next()) {
				inst = rs.getLong(1);
				nom = rs.getString(2);
			}
			pst = cn.prepareStatement(rb.getString("institucion.updateNombre"));
			pst.clearParameters();
			if (b.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, b.trim());
			if (c.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.trim());
			if (a.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.trim()));
			int n = pst.executeUpdate();
			// System.out.println("Actualizo nombre: " + n);
			pst.close();
			pst = cn.prepareStatement(rb.getString("historicoInstitucion"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, 1);
			pst.setLong(posicion++, inst);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setString(posicion++, nom);
			pst.setString(posicion++, b);
			n = pst.executeUpdate();
			// System.out.println("Inserto historico: " + n);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/*
	 * Actualizacion de institucion en sede
	 */
	public boolean actualizarDaneInstitucion(String[] as) {
		String daneInst0 = as[0];
		String daneInst1 = as[1];
		String sede0 = as[2];
		String sede1 = as[3];
		String nomsede = as[4];
		int posicion = 1, j, i, n;
		boolean band = false;
		String consulta = "select INSCODDEPTO,INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane="
				+ daneInst0;
		String consulta2 = "select INSCODDEPTO,INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane="
				+ daneInst1;
		Statement st = null;
		String inst0[] = new String[4];
		String inst1[] = new String[4];
		ResultSet rs = null;
		Collection c = new ArrayList();
		Collection c2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next()) {
				inst0[0] = rs.getString(1);
				inst0[1] = rs.getString(2);
				inst0[2] = rs.getString(3);
				inst0[3] = rs.getString(4);
			}
			rs = st.executeQuery(consulta2);
			if (rs.next()) {
				inst1[0] = rs.getString(1);
				inst1[1] = rs.getString(2);
				inst1[2] = rs.getString(3);
				inst1[3] = rs.getString(4);
			}
			if (inst0[3].equals("") || inst1[3].equals("") || sede0 == null
					|| sede0.equals("") || sede1 == null || sede1.equals("")) {
				setMensaje("Faltan parametros en la posicinn:");
				return false;
			}
			/* VALIDAR JORNADAS DE ESA SEDE */
			pst = cn.prepareStatement(rb.getString("sede.buscarJornada"));// JORNADAS
																			// DE
																			// LA
																			// SEDE
																			// A
																			// CAMBIAR
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst0[3].trim()));
			pst.setLong(posicion++, Long.parseLong(sede0.trim()));
			rs = pst.executeQuery();
			c = new ArrayList();
			while (rs.next()) {
				c.add(rs.getString(1));
			}
			String sedeJornada[] = getFiltroArray(c);
			pst.close();
			pst = cn.prepareStatement(rb.getString("sede.buscarJornada2"));// JORNADAS
																			// DE
																			// LA
																			// NUEVA
																			// INSTITUCION
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
			rs = pst.executeQuery();
			c2 = new ArrayList();
			while (rs.next()) {
				c2.add(rs.getString(1));
			}
			String jornada[] = getFiltroArray(c2);
			if (sedeJornada != null) {
				for (i = 0; i < sedeJornada.length; i++) {
					band = false;
					for (j = 0; j < (jornada != null ? jornada.length : 0); j++) {
						if (sedeJornada[i].equals(jornada[j]))
							band = true;
					}
					if (!band) {// INSERTAR JORNADAS QUE NO ESTAN
					// System.out
					// .println("Insertar jornada=" + sedeJornada[i]);
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("sede.insertJornada"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
						pst.setLong(posicion++,
								Long.parseLong(sedeJornada[i].trim()));
						n = pst.executeUpdate();
						System.out.println("Inserto jornada=" + n);
					}
				}
			}
			/* VALIDAR METODOLOGIAS */
			pst.close();
			pst = cn.prepareStatement(rb.getString("sede.buscarMetodologia"));// met
																				// DE
																				// LA
																				// SEDE
																				// A
																				// CAMBIAR
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst0[3].trim()));
			pst.setLong(posicion++, Long.parseLong(sede0.trim()));
			rs = pst.executeQuery();
			c = new ArrayList();
			while (rs.next()) {
				c.add(rs.getString(1));
			}
			String metodJornada[] = getFiltroArray(c);
			pst.close();
			pst = cn.prepareStatement(rb.getString("sede.buscarMetodologia2"));// metodologias
																				// DE
																				// LA
																				// NUEVA
																				// INSTITUCION
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
			rs = pst.executeQuery();
			c2 = new ArrayList();
			while (rs.next()) {
				c2.add(rs.getString(1));
			}
			String metodologia[] = getFiltroArray(c2);
			if (metodJornada != null) {
				for (i = 0; i < metodJornada.length; i++) {
					band = false;
					for (j = 0; j < (metodologia != null ? metodologia.length
							: 0); j++) {
						if (metodJornada[i].equals(metodologia[j]))
							band = true;
					}
					if (!band) {// INSERTAR METODOLOGIAS QUE NO ESTAN
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("sede.insertMetodologia"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
						pst.setLong(posicion++,
								Long.parseLong(metodJornada[i].trim()));
						n = pst.executeUpdate();
						// System.out.println("Inserto metodologia=" + n);
					}
				}
			}
			/* VALIDAR GRADOS */
			pst.close();
			pst = cn.prepareStatement(rb.getString("sede.buscarGrado"));// met
																		// DE LA
																		// SEDE
																		// A
																		// CAMBIAR
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst0[3].trim()));
			pst.setLong(posicion++, Long.parseLong(sede0.trim()));
			rs = pst.executeQuery();
			c = new ArrayList();
			Object[] o;
			while (rs.next()) {
				o = new Object[4];
				o[0] = rs.getString(1);
				o[1] = rs.getString(2);
				o[2] = rs.getString(3);
				o[3] = rs.getString(4);
				c.add(o);
			}
			String graJornada[][] = getFiltroMatriz(c);
			pst.close();
			pst = cn.prepareStatement(rb.getString("sede.buscarGrado2"));// metodologias
																			// DE
																			// LA
																			// NUEVA
																			// INSTITUCION
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
			rs = pst.executeQuery();
			c2 = new ArrayList();
			while (rs.next()) {
				o = new Object[2];
				o[0] = rs.getString(1);
				o[1] = rs.getString(2);
				c2.add(o);
			}
			String grado[][] = getFiltroMatriz(c2);
			if (graJornada != null) {
				for (i = 0; i < graJornada.length; i++) {
					band = false;
					for (j = 0; j < (grado != null ? grado.length : 0); j++) {
						if (graJornada[i][0].equals(grado[j][0])
								&& graJornada[i][1].equals(grado[j][1]))
							band = true;
					}
					if (!band) {// INSERTAR GRADOS QUE NO ESTAN
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("sede.insertGrado"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
						pst.setLong(posicion++,
								Long.parseLong(graJornada[i][0].trim()));
						pst.setLong(posicion++,
								Long.parseLong(graJornada[i][1].trim()));
						pst.setLong(posicion++,
								Long.parseLong(graJornada[i][1].trim()));
						n = pst.executeUpdate();
						// System.out.println("Inserto grado=" + n);
						pst.close();
						pst = cn.prepareStatement(rb
								.getString("sede.insertGrado2"));
						pst.clearParameters();
						posicion = 1;
						pst.setLong(posicion++, Long.parseLong(inst1[0].trim()));
						pst.setLong(posicion++, Long.parseLong(inst1[1].trim()));
						pst.setLong(posicion++, Long.parseLong(inst1[2].trim()));
						pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
						pst.setLong(posicion++,
								Long.parseLong(graJornada[i][2].trim()));
						pst.setLong(posicion++,
								Long.parseLong(graJornada[i][3].trim()));
						pst.setLong(posicion++,
								Long.parseLong(graJornada[i][0].trim()));
						pst.setLong(posicion++,
								Long.parseLong(graJornada[i][1].trim()));
						n = pst.executeUpdate();
						// System.out.println("Inserto grado jerarquia=" + n);
					}
				}
			}
			/* actualizacion en sede */
			// System.out.println(inst1[3].trim() + "//" + sede1.trim() + "//"
			// + inst0[3].trim() + "//" + sede0.trim());
			for (j = 1; j <= 6; j++) {
				pst = cn.prepareStatement(rb
						.getString("sede.updateDaneInstitucion" + j));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(inst1[3].trim()));
				pst.setLong(posicion++, Long.parseLong(sede1.trim()));
				pst.setLong(posicion++, Long.parseLong(inst0[3].trim()));
				pst.setLong(posicion++, Long.parseLong(sede0.trim()));
				n = pst.executeUpdate();
				pst.close();
				// System.out.println("Actualizo Institucion a sede para  " + j
				// + " =" + n);
			}
			pst.close();
			pst = cn.prepareStatement(rb.getString("historicoInstitucion"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, 6);
			pst.setLong(posicion++, Long.parseLong(inst0[3]));
			pst.setLong(posicion++, Long.parseLong(inst1[3]));
			pst.setLong(posicion++, Long.parseLong(sede0));
			pst.setLong(posicion++, Long.parseLong(sede1));
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			n = pst.executeUpdate();
			// System.out.println("Inserto historico: " + n);
			// HISTORICO EN ESTUDIANTE
			pst.close();
			pst = cn.prepareStatement(rb.getString("historicoEstudiante2"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst0[3]));
			pst.setLong(posicion++, Long.parseLong(sede0));
			pst.setString(posicion++, nomsede);
			pst.setLong(posicion++, Long.parseLong(inst1[3]));
			pst.setLong(posicion++, Long.parseLong(sede1));
			n = pst.executeUpdate();
			// System.out.println(""+inst0[3]+"/"+sede0+"/"+inst1[3]+"/"+sede1);
			// System.out.println("Inserto historico2: " + n);
			cn.commit();
			cn.setAutoCommit(true);
			// System.out.println("Actualizo Institucion a sede:");
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			System.out.println(sqle.getMessage());
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarConsecutivoSede(String[] as) {
		String a = as[0];
		String b = as[1];
		String c = as[2];
		String d = as[3];
		int posicion = 1;
		String consulta = "select inscodigo from institucion where inscoddane="
				+ b;
		Statement st = null;
		long inst = 0;
		int n = 0;
		ResultSet rs = null;
		try {// inst vieja, inst nueva, consecutivo viejo,consecutivo nuevo
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next())
				inst = rs.getLong(1);
			for (int j = 1; j <= 6; j++) {
				pst = cn.prepareStatement(rb.getString("sede.updateConsecutivo"
						+ j));
				pst.clearParameters();
				posicion = 1;
				if (d.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(d.trim()));
				if (inst == 0)
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, inst);
				if (c.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(c.trim()));
				n = pst.executeUpdate();
				// System.out.println("Actualizo consecutivo sede para  " + j
				// + " =" + n);
				pst.close();
			}
			pst.close();
			pst = cn.prepareStatement(rb.getString("historicoInstitucion"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, 5);
			pst.setLong(posicion++, inst);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setLong(posicion++, Long.parseLong(c));
			pst.setLong(posicion++, Long.parseLong(d));
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			n = pst.executeUpdate();
			// System.out.println("Inserto historico: " + n);
			cn.commit();
			cn.setAutoCommit(true);
			// System.out.println("Actualizo consecutivo sede: ");
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarNombreSede(String[] as) {
		String a = as[0];
		String b = as[1];
		String c = as[2];
		String d = as[3];
		String e = as[4];
		String consulta = "select inscodigo,INSSECTOR,INSESTADO from institucion where inscoddane="
				+ as[0];
		long inst = 0, sector = 0;
		String estado = "";
		Statement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next()) {
				inst = rs.getLong(1);
				sector = rs.getLong(2);
				estado = rs.getString(3);
			}
			if (sector == 1 && estado.equals("A")) {
				pst = cn.prepareStatement(rb.getString("sede.updateNombre"));
				pst.clearParameters();
				if (e.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, e.trim());
				if (a.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(a.trim()));
				if (b.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(b.trim()));
				if (c.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(c.trim()));
				int n = pst.executeUpdate();
				// System.out.println("Actualizo nombre sede: " + n);
				pst.close();
				pst = cn.prepareStatement(rb.getString("historicoInstitucion"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, 3);
				pst.setLong(posicion++, inst);
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
				pst.setString(posicion++, d);
				pst.setString(posicion++, e);
				n = pst.executeUpdate();
				// System.out.println("Inserto historico: " + n);
				cn.commit();
				cn.setAutoCommit(true);
			} else {
				setMensaje("Institucion inactiva o privada ");
				return false;
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			System.out.println("nombre sede " + sqle);
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarDaneSede(String[] as) {
		String a = as[0];
		String b = as[1];
		String c = as[2];
		String d = as[3];
		String consulta = "select inscodigo from institucion where inscoddane="
				+ as[0];
		long inst = 0;
		Statement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {// dane inst, consecutivo sede, dane sede viejo, dane sede nuevo
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next()) {
				inst = rs.getLong(1);
			}
			pst = cn.prepareStatement(rb.getString("sede.updateDane"));
			pst.clearParameters();
			if (a != null && b != null && c != null) {
				if (d.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(d.trim()));
				if (a.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(a.trim()));
				if (b.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(b.trim()));
				int n = pst.executeUpdate();
				// System.out.println("Actualizo dane de sede =" + n);
				pst.close();
				pst = cn.prepareStatement(rb.getString("historicoInstitucion"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, 4);
				pst.setLong(posicion++, inst);
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
				pst.setLong(posicion++, Long.parseLong(as[2]));
				pst.setLong(posicion++, Long.parseLong(as[3]));
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
				n = pst.executeUpdate();
				// System.out.println("Inserto historico: " + n);
				cn.commit();
				cn.setAutoCommit(true);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/*
	 * Actualizar dane de institucion en archivo1
	 */
	public boolean actualizarDaneInsitucion(String[] as) {
		String a = as[0];
		String b = as[1];
		String consulta = "select inscodigo from institucion where inscoddane="
				+ as[0];
		long inst = 0;
		Statement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next()) {
				inst = rs.getLong(1);
			}
			pst = cn.prepareStatement(rb.getString("institucion.updateDane"));
			pst.clearParameters();
			if (b.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(b.trim()));
			if (a.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.trim()));
			int n = pst.executeUpdate();
			// System.out.println("Actualizo Dane: " + n);
			pst.close();
			pst = cn.prepareStatement(rb.getString("historicoInstitucion"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, 2);
			pst.setLong(posicion++, inst);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setLong(posicion++, Long.parseLong(as[0]));
			pst.setLong(posicion++, Long.parseLong(as[1]));
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			n = pst.executeUpdate();
			System.out.println("Inserto historico: " + n);
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarDeptoSede(String[] a) {
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("sede.updateDepto"));
			pst.clearParameters();
			if (a != null) {
				if (a[3].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[3].trim());
				if (a[4].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[4].trim());
				if (a[0].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(a[0].trim()));
				if (a[2].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(a[2].trim()));
				// System.out.println("insertando la cuestion de sede_"+rb.getString("sede.updateDepto"));
				// System.out.println(a[3]+"//"+a[4]+"//"+a[0]+"//"+a[2]);
				int n = pst.executeUpdate();
				// System.out.println("inserto"+n);
				cn.commit();
				cn.setAutoCommit(true);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
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

	public boolean actualizarDeptoInstitucion(String[] a) {
		int posicion = 1;
		String consulta = "SELECT INSCODIGO from INSTITUCION where INSCODDANE="
				+ a[0];
		Statement st = null;
		String jer = "";
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			rs = st.executeQuery(consulta);
			if (rs.next())
				jer = rs.getString(1);
			pst = cn.prepareStatement(rb.getString("institucion.updateDepto"));
			pst.clearParameters();
			if (a != null) {
				if (a[1].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[1].trim());
				if (a[2].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[2].trim());
				if (a[3].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[3].trim());
				if (a[0].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(a[0].trim()));
				pst.executeUpdate();
				// System.out.println("insertando la cuestion de institucion");
				posicion = 1;
				pst.close();
				pst = cn.prepareStatement(rb
						.getString("institucion.updateDepto2"));
				pst.clearParameters();
				if (a[1].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[1].trim());
				if (a[2].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[2].trim());
				if (a[3].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, a[3].trim());
				if (jer.equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(jer.trim()));
				pst.executeUpdate();
				// System.out
				// .println("insertando la cuestion de jerarquia en institucion");
				cn.commit();
				cn.setAutoCommit(true);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		mensaje += s;
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}

	public String[] getFiltroArray(Collection a) throws InternalErrorException {
		if (!a.isEmpty()) {
			String[] m = new String[a.size()];
			String z;
			int i = 0;
			Iterator iterator = a.iterator();
			Object o;
			while (iterator.hasNext()) {
				z = "";
				o = (Object) iterator.next();
				// for(int j=0;j<o.length;j++)
				// z+=(o[j]!=null?o[j]:"")+"|";
				// m[i++]=z.substring(0,z.lastIndexOf("|"));
				m[i++] = (String) o;
			}
			return m;
		}
		return null;
	}

	public String[][] getFiltroMatriz(Collection a)
			throws InternalErrorException {
		Object[] o;
		if (!a.isEmpty()) {
			int i = 0, j = -1;
			Iterator iterator = a.iterator();
			o = ((Object[]) iterator.next());
			String[][] m = new String[a.size()][o.length];
			iterator = a.iterator();
			while (iterator.hasNext()) {
				j++;
				o = ((Object[]) iterator.next());
				for (i = 0; i < o.length; i++) {
					m[j][i] = (String) o[i];
				}
			}
			return m;
		}
		return null;
	}

}
/*
 * if(.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR); else
 * pst.setInt(posicion++,Integer.parseInt(.trim()));
 * pst.setLong(posicion++,Long.parseLong(.trim()));
 * pst.setString(posicion++,.trim()); pst.setDate(posicion++,new
 * java.sql.Date(sdf.parse(basica.getNacimiento()).getTime()));
 */

/*
 * public boolean actualizarAlumno(String daneinst,String sede,String jor,String
 * met,String gra,String gru,String est){ int posicion=1; String consulta=
 * "select INSCODDEPTO,INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane="
 * +daneinst; Statement st=null; String[] inst=new String[4]; long grado=-1;
 * ResultSet rs=null; try{ cn=cursor.getConnection(); cn.setAutoCommit(false);
 * st=cn.createStatement(); rs=st.executeQuery(consulta); if(!rs.next()){
 * setMensaje("Codigo DANE no existe"); return false; }else{
 * inst[0]=rs.getString
 * (1);inst[1]=rs.getString(2);inst[2]=rs.getString(3);inst[3]=rs.getString(4);
 * String consulta2="SELECT SEDCODIGO FROM SIGES.SEDE where SEDCODINS="+inst[3]+
 * " and SEDCODIGO="+sede; rs=st.executeQuery(consulta2); if(!rs.next()){
 * setMensaje("Codigo DANE de sede no existe"); return false; } }
 * 
 * //VALIDAR SI LA JORNADA YA EXISTE, SINO SE INSERTA/
 * pst=cn.prepareStatement(rb.getString("jornada.consultaActualizar"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(jor.trim())); rs=pst.executeQuery();
 * if(!rs.next()){ //insertar en jornada
 * pst=cn.prepareStatement(rb.getString("jornada.insertarJornada"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(jor.trim())); int
 * n=pst.executeUpdate(); System.out.println("insert jornada ="+n); //insertar
 * en sede jornada
 * pst=cn.prepareStatement(rb.getString("jornada.insertarSedeJornada"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(sede.trim()));
 * pst.setLong(posicion++,Long.parseLong(jor.trim())); n=pst.executeUpdate();
 * System.out.println("insert sede jornada ="+n); //insertar en sede jornada
 * jerarquia
 * pst=cn.prepareStatement(rb.getString("jornada.insertarJornadaJerarquia"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[0]));
 * pst.setLong(posicion++,Long.parseLong(inst[1]));
 * pst.setLong(posicion++,Long.parseLong(inst[2]));
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(sede.trim()));
 * pst.setLong(posicion++,Long.parseLong(jor.trim())); n=pst.executeUpdate();
 * System.out.println("insert sede jornada ="+n); } //VALIDAR SI LA METODOLOGIA
 * YA EXISTE, SINO SE INSERTA/
 * pst=cn.prepareStatement(rb.getString("metodologia.consultaActualizar"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(met.trim())); rs=pst.executeQuery();
 * if(!rs.next()){ //insertar en metodologia
 * pst=cn.prepareStatement(rb.getString("metodologia.insertarMetodologia"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(met.trim())); int
 * n=pst.executeUpdate(); System.out.println("insert metodologia ="+n); }
 * //VALIDAR SI LA GRADO YA EXISTE, SINO SE INSERTA/
 * pst=cn.prepareStatement(rb.getString("grado.consultaActualizar"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(met.trim()));
 * pst.setLong(posicion++,Long.parseLong(gra.trim())); rs=pst.executeQuery();
 * if(!rs.next()){ //insertar en grado
 * pst=cn.prepareStatement(rb.getString("grado.insertarGrado"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(met.trim()));
 * pst.setLong(posicion++,Long.parseLong(gra.trim()));
 * pst.setLong(posicion++,Long.parseLong(gra.trim())); int
 * n=pst.executeUpdate(); System.out.println("insert grado ="+n); } //VALIDAR SI
 * GRUPO EXSTE/ if(gru!=null && !gru.equals("0")){
 * pst=cn.prepareStatement(rb.getString("grupo.consultaJerarquia"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(sede));
 * pst.setLong(posicion++,Long.parseLong(jor));
 * pst.setLong(posicion++,Long.parseLong(met.trim()));
 * pst.setLong(posicion++,Long.parseLong(gra.trim())); rs=pst.executeQuery();
 * if(rs.next()){grado=rs.getLong(1);} if(grado!=-1){
 * pst=cn.prepareStatement(rb.getString("grupo.consultaActualizar"));
 * pst.clearParameters(); posicion=1; pst.setLong(posicion++,grado);
 * pst.setLong(posicion++,Long.parseLong(gru.trim())); rs=pst.executeQuery();
 * if(!rs.next()){ //insertar en grupo
 * pst=cn.prepareStatement(rb.getString("grupo.insertarGrupo"));
 * pst.clearParameters(); posicion=1; pst.setLong(posicion++,grado);
 * pst.setLong(posicion++,Long.parseLong(gru)); pst.setString(posicion++,gru);
 * int n=pst.executeUpdate(); //insertar en grupo de jerarquia
 * pst=cn.prepareStatement(rb.getString("grupo.insertarJerarquia"));
 * pst.clearParameters(); posicion=1;
 * pst.setLong(posicion++,Long.parseLong(inst[0]));
 * pst.setLong(posicion++,Long.parseLong(inst[1]));
 * pst.setLong(posicion++,Long.parseLong(inst[2]));
 * pst.setLong(posicion++,Long.parseLong(inst[3]));
 * pst.setLong(posicion++,Long.parseLong(sede));
 * pst.setLong(posicion++,Long.parseLong(jor));
 * pst.setLong(posicion++,Long.parseLong(met));
 * pst.setLong(posicion++,Long.parseLong(gra.trim()));
 * pst.setLong(posicion++,Long.parseLong(gru.trim())); n=pst.executeUpdate();
 * System.out.println("insert grupo="+n); } } }else{ //poner grupo 0 a
 * estudiante
 * 
 * } cn.commit(); cn.setAutoCommit(true); }catch(InternalErrorException in){
 * setMensaje("NO se puede estabecer conexinn con la base de datos: "); return
 * false;} catch(SQLException sqle){ try{cn.rollback();}catch(SQLException s){}
 * setMensaje("Posible problema: "); switch(sqle.getErrorCode()){ default:
 * setMensaje(sqle.getMessage().replace('\'','`').replace('"','n')); } return
 * false; }finally{ try{ OperacionesGenerales.closeResultSet(rs);
 * OperacionesGenerales.closeStatement(st);
 * OperacionesGenerales.closeStatement(pst);
 * OperacionesGenerales.closeConnection(cn); }catch(InternalErrorException
 * inte){} } return true; }
 */