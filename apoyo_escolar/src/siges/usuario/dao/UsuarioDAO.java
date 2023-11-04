package siges.usuario.dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.perfil.dao.PerfilDAO;
import siges.usuario.beans.Usuario;
import siges.util.Mailer;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		27/04/2018		JORGE CAMACHO		Se editó la función insertarPersonal() para almacenar el correo institucional
 * 		2.0		30/04/2018		JORGE CAMACHO		Se editó la función asignarPersonalUsuario() agregando el campo peremail,
 * 													Se editó la función actualizarPersonal() agregando el campo peremail,
 * 													Se editó la función insertarUsuario() para generar automáticamente la contraseña
 * 		3.0		02/05/2018		JORGE CAMACHO		Se agregaron las funciones: generarContrasena() y enviarCorreoContrasena()
 * 		4.0		03/05/2018		JORGE CAMACHO		Se editó la función actualizarUsuario() para generar automáticamente la contraseña
 * 		5.0		10/05/2018		JORGE CAMACHO		Se agregó la función: cuerpoCorreoContrasenaTemporal(), enviarRecordatorioContrasena(), cuerpoRecordatorioContrasena()
 * 													Se modificó la función asignarPersonalUsuario() para guardar información en el campo UsuPerNumDocum
 * 		6.0		15/05/2018		JORGE CAMACHO		Se agregó la función registroAuditoria()
 * 		7.0		18/05/2018		JORGE CAMACHO		Se agregó la función existeCorreoInstitucional(), enviarCorreoGrupoApoyoAccesos() y cuerpoCorreoGrupoApoyoAccesos()
 *
*/

/**
 * Nombre: Util Descripcion: Realiza operaciones en la BD Funciones de la
 * pagina: Realiza la logica de negocio Entidades afectadas: todas las tablas de
 * la BD Fecha de modificacinn: Feb-05 
 * 
 * @author Latined
 * @version $v 1.3 $
 */
public class UsuarioDAO extends siges.dao.Dao {
	
	public String buscar;
	public String mensaje;
	public String sentencia;
	public int[] resultado;
	
	private Cursor cursor;
	private java.sql.Timestamp f2;
	private ResourceBundle rbUsuario;
	private java.text.SimpleDateFormat sdf;
	
	/**
	 * Constructor de la clase
	 **/
	/*
	 * public UsuarioDAO(){ sentencia=null; mensaje=""; sdf = new
	 * java.text.SimpleDateFormat("dd/MM/yyyy"); sdf.setLenient(false);
	 * resultado=new int[3]; rbUsuario=ResourceBundle.getBundle("usuarios"); }
	 */

	/**
	 * Constructor de la clase
	 **/
	public UsuarioDAO(Cursor cur) {
		super(cur);
		this.cursor = cur;
		sentencia = null;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rbUsuario = ResourceBundle.getBundle("usuarios");
	}

	/**
	 * Funcinn: Cerrar conecciones<br>
	 **/
	@Override
	public void cerrar() {
		try {
			/*
			 * OperacionesGenerales.closeStatement(pst);
			 * OperacionesGenerales.closeStatement(pst2);
			 * OperacionesGenerales.closeStatement(pst3);
			 * OperacionesGenerales.closeConnection(cn);
			 */
			rbUsuario = null;
			if (cursor != null)
				cursor.cerrar();
		} catch (Exception e) {
		}
	}

	/**
	 * Funcinn: Encripta una cadena String a MD5
	 * 
	 * @param Usuario
	 * 
	 * @return String
	 **/
	public static String md5(String clear) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(clear.getBytes());
		int size = b.length;
		StringBuffer h = new StringBuffer(size);
		// algoritmo y arreglo md5
		for (int i = 0; i < size; i++) {
			int u = b[i] & 255;
			if (u < 16) {
				h.append("0" + Integer.toHexString(u));
			} else {
				h.append(Integer.toHexString(u));
			}
		}
		// clave encriptada
		return h.toString();
	}

	/**
	 * Funcinn: Actualiza información del usuario en la base de datos<br>
	 * 
	 * @param Usuario
	 *            u
	 * @return boolean
	 **/
	public boolean actualizarUsuario(Usuario u) {
		
		int posicion; // posicion inicial de llenado del preparedstatement

		Connection cn = null;
		String[] param = null;
		String[] param2 = null;
		PreparedStatement pst = null;
		
		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			if (u.getUsucodjerar().equals("")) {
				posicion = 1;
				pst = cn.prepareStatement(rbUsuario.getString("UsuarioCascadaClave"));
				pst.clearParameters();
				
				// La clave se genera automáticamente por el sistema
				u.setUsupassword(generarContrasena());
				if (u.getUsupassword().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else {
					try {
						pst.setString(posicion++, md5(u.getUsupassword().trim()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (u.getUsupernumdocum().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++,	Long.parseLong(u.getUsupernumdocum().trim()));
				
				pst.executeUpdate();
				
			} else {

				if (Integer.parseInt(u.getUsuperfcodigo()) > 420 && Integer.parseInt(u.getUsuperfcodigo()) < 500) {
					
					if (!existeDocSedJor(u.getUsucodjerar(), u.getUsupernumdocum())) {
						String consulta2 = "select G_JERINST, G_JERSEDE, G_JERJORN "
								+ "from g_jerarquia where G_JERCODIGO = '"
								+ u.getUsucodjerar()
								+ "' and g_jertipo = '1' and g_jernivel = '6'";
						param = cursor.nombre(consulta2);
					}
					
				} else {
					
					if (existeDocSedJor(u.getUsucodjerar2(), u.getUsupernumdocum())) {
						String consulta3 = "select DOCSEDJORNUMDOCUM, DOCSEDJORCODINST, DOCSEDJORCODSEDE, DOCSEDJORCODJOR "
								+ "from DOCENTE_SEDE_JORNADA, g_jerarquia "
								+ "where G_JERCODIGO = '"
								+ u.getUsucodjerar2()
								+ "' and DOCSEDJORNUMDOCUM = "
								+ u.getUsupernumdocum()
								+ " and g_jerinst=DOCSEDJORCODINST "
								+ "and g_jersede=DOCSEDJORCODSEDE "
								+ "and g_jerjorn=DOCSEDJORCODJOR";
						param2 = cursor.nombre(consulta3);
					}
					
				}
				
				cn = cursor.getConnection();
				cn.setAutoCommit(false);

				if (param != null) {

					posicion = 1;
					pst = cn.prepareStatement(rbUsuario.getString("UsuarioInsertarDocSedJor"));
					pst.clearParameters();
					
					if (param[0].equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setString(posicion++, param[0].trim());
					
					if (param[1].equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setInt(posicion++, Integer.parseInt(param[1].trim()));
					
					if (param[2].equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setInt(posicion++, Integer.parseInt(param[2].trim()));
					
					if (u.getUsupernumdocum().equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setLong(posicion++,	Long.parseLong(u.getUsupernumdocum().trim()));
					
					pst.executeUpdate();
					
				}

				if (param2 != null) {
					
					posicion = 1;
					pst = cn.prepareStatement(rbUsuario.getString("DocenteSedeJornadaEliminar"));
					pst.clearParameters();
					
					if (param2[0].equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setLong(posicion++,	Long.parseLong(param2[0].trim()));
					
					if (param2[1].equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setString(posicion++, param2[1].trim());
					
					if (param2[2].equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setInt(posicion++, Integer.parseInt(param2[2].trim()));
					
					if (param2[3].equals(""))
						pst.setNull(posicion++, java.sql.Types.VARCHAR);
					else
						pst.setInt(posicion++, Integer.parseInt(param2[3].trim()));
					
					pst.executeUpdate();
					
				}
				
				if (Integer.parseInt(u.getCodNivel()) == 0) {
					try {
						u.setUsucodjerar(String.valueOf(getCodJerarquia(Long.parseLong(u.getUsucoddependencia()))));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				posicion = 1;
				pst = cn.prepareStatement(rbUsuario.getString("UsuarioActualizar0"));
				pst.clearParameters();
				
				if (u.getUsuperfcodigo().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, u.getUsuperfcodigo().trim());
				
				if (u.getUsucodjerar().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setInt(posicion++, Integer.parseInt(u.getUsucodjerar().trim()));
				
				if (u.getUsucodjerar2().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setInt(posicion++, Integer.parseInt(u.getUsucodjerar2().trim()));
				
				if (u.getUsupernumdocum().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++,	Long.parseLong(u.getUsupernumdocum().trim()));
				
				pst.executeUpdate();

				// En las siguientes instrucciones se actualiza la contraseña a los 
				// demás accesos que tenga configurado el usuario en la base de datos
				// y se indica en la instruccion SQL que la contraseña es temporal
				posicion = 1;
				pst = cn.prepareStatement(rbUsuario.getString("UsuarioCascadaClave"));
				pst.clearParameters();
				
				// La clave se genera automáticamente por el sistema
				u.setUsupassword(generarContrasena());
				if (u.getUsupassword().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else {
					try {
						pst.setString(posicion++, md5(u.getUsupassword().trim()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (u.getUsupernumdocum().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++, Long.parseLong(u.getUsupernumdocum().trim()));
				
				pst.executeUpdate();
				
			}
			
			cn.commit();
			cn.setAutoCommit(true);
			
			try {
				// Se envía el password por correo
				enviarCorreoContrasena(u);
			} catch (Exception e) {
				setMensaje("La información fue actualizada satisfactoriamente pero NO se pudo enviar el correo con la contraseña temporal.");
				return false;
			}
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			
			setMensaje("Error intentando actualizar información de Usuario. (" + sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
				default:
					setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			
			return false;
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
		return true;
		
	}

	/**
	 * Funcinn: Subir al bean usuario informaciÃ³n desde la base de datos<br>
	 * 
	 * @param String
	 *            jerar
	 * @param String
	 *            perf
	 * @param String
	 *            docum
	 * @return Usuario
	 **/
	public Usuario asignarUsuario(String jerar, String perf, String docum) {
		Usuario u = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection cn = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioAsignar0"));
			pst.clearParameters();
			if (jerar.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(jerar.trim()));
			if (perf.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, perf.trim());
			if (docum.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(docum.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				u = new Usuario();
				int i = 1;
				u.setEstadousu("1");
				u.setUsucodjerar(rs.getString(i++));
				u.setUsulogin(rs.getString(i++));
				u.setUsupassword(rs.getString(i++));
				u.setUsuperfcodigo(rs.getString(i++));
				u.setUsupernumdocum(rs.getString(i++));
				u.setUsucodjerar2(u.getUsucodjerar());
				u.setUsuperfcodigo2(u.getUsuperfcodigo());
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar informaciÃ³n de Usuario. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return u;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return u;
	}

	/**
	 * Funcinn: Verifica si existe registro en sede jornada<br>
	 * 
	 * @param String
	 *            jerar
	 * @param String
	 *            docum
	 * @return boolean
	 **/
	public boolean existeDocSedJor(String jerar, String docum) {
		String consulta = "select DOCSEDJORNUMDOCUM from DOCENTE_SEDE_JORNADA,G_JERARQUIA"
				+ " where G_JERCODIGO="
				+ jerar
				+ " AND DOCSEDJORCODINST=G_JERINST AND DOCSEDJORCODSEDE=G_JERSEDE"
				+ " AND DOCSEDJORCODJOR=G_JERJORN AND DOCSEDJORNUMDOCUM="
				+ docum + "";
		return cursor.existe(consulta);
	}

	/**
	 * Funcinn: insertar información del usuario en la base de datos<br>
	 * 
	 * @param Usuario
	 *            s
	 * @return boolean
	 **/
	public boolean insertarUsuario(Usuario s) {
		
		int posicion; // posicion inicial de llenado del preparedstatement
		
		Connection cn = null;
		String[] param = null;
		PreparedStatement pst = null;
		
		if (Integer.parseInt(s.getUsuperfcodigo()) > 420 && Integer.parseInt(s.getUsuperfcodigo()) < 500) {
			if (!existeDocSedJor(s.getUsucodjerar(), s.getUsupernumdocum())) {
				String consulta2 = "select G_JERINST, G_JERSEDE, G_JERJORN from g_jerarquia where G_JERCODIGO = '" + s.getUsucodjerar()	+ "' and g_jertipo = '1' and g_jernivel = '6'";
				param = cursor.nombre(consulta2);
			}
		}
		
		if (Integer.parseInt(s.getCodNivel()) == 0) {
			try {
				s.setUsucodjerar(String.valueOf(getCodJerarquia(Long.parseLong(s.getUsucoddependencia()))));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			if (param != null) {
				posicion = 1;
				pst = cn.prepareStatement(rbUsuario.getString("UsuarioInsertarDocSedJor"));
				pst.clearParameters();
				
				if (param[0].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setString(posicion++, param[0].trim());
				
				if (param[1].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setInt(posicion++, Integer.parseInt(param[1].trim()));
				
				if (param[2].equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setInt(posicion++, Integer.parseInt(param[2].trim()));
				
				if (s.getUsupernumdocum().equals(""))
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else
					pst.setLong(posicion++,	Long.parseLong(s.getUsupernumdocum().trim()));
				
				pst.executeUpdate();
			}

			posicion = 1;
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioInsertar0"));
			pst.clearParameters();
			
			if (s.getUsucodjerar().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,	Long.parseLong(s.getUsucodjerar().trim()));
			
			if (s.getUsupernumdocum().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,	Long.parseLong(s.getUsupernumdocum().trim()));
			
			/*
			if (s.getUsulogin().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getUsulogin().trim());
			*/
			// Al campo LOGIN se le asigna el mismo número del documento
			pst.setLong(posicion++,	Long.parseLong(s.getUsupernumdocum().trim()));
			
			// La clave se genera automáticamente por el sistema
			s.setUsupassword(generarContrasena());
			if (s.getUsupassword().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else {
				try {
					pst.setString(posicion++, md5(s.getUsupassword().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (s.getUsuperfcodigo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getUsuperfcodigo().trim());
			
			pst.executeUpdate();
			
			// En las siguientes instrucciones se actualiza la contraseña a los 
			// demás accesos que tenga configurado el usuario en la base de datos
			// y se indica en la instruccion SQL que la contraseña es temporal
			posicion = 1;
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioCascadaClave"));
			pst.clearParameters();
			
			if (s.getUsupassword().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else {
				try {
					pst.setString(posicion++, md5(s.getUsupassword().trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (s.getUsupernumdocum().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,	Long.parseLong(s.getUsupernumdocum().trim()));
			
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
			
			try {
				// Se envía el password por correo
				enviarCorreoContrasena(s);
			} catch (Exception e) {
				setMensaje("La información fue ingresada satisfactoriamente pero NO se pudo enviar el correo con la contraseña temporal.");
				return false;
			}
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			
			setMensaje("Error SQL intentando ingresar información de Usuario. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			
			return false;
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
		return true;
		
	}

	/**
	 * Funcinn: Eliminar Docente Sede Jornada<br>
	 * 
	 * @param String
	 *            [] param
	 * @return boolean
	 **/
	public boolean eliminarDocenteSedeJornada(String jerar, String perf, String docum, String[] param) {
		int posicion;
		PreparedStatement pst = null;
		Connection cn = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println(param[0] + "-" + param[1] + "-" + param[2] +
			// "-"
			// + param[3] + "-");
			posicion = 1;
			pst = cn.prepareStatement(rbUsuario
					.getString("DocenteSedeJornadaEliminar"));
			pst.clearParameters();
			if (param[0].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, param[0].trim());
			if (param[1].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(param[1].trim()));
			if (param[2].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(param[2].trim()));
			if (param[3].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(param[3].trim()));
			pst.executeUpdate();

			posicion = 1;
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioEliminar"));
			pst.clearParameters();
			if (jerar.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(jerar.trim()));
			if (perf.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, perf.trim());
			if (docum.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(docum.trim()));
			pst.executeUpdate();

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
			setMensaje("Error SQL intentando ELIMINAR convivencia. Posible problema: ");
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
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Eliminar usuario de la base de datos<br>
	 * 
	 * @param String
	 *            jerar
	 * @param String
	 *            perf
	 * @param String
	 *            docum
	 * @return
	 **/
	public boolean eliminarUsuario(String jerar, String perf, String docum) {
		
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
				
		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioEliminar"));
			pst.clearParameters();
			
			if (jerar.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(jerar.trim()));
			
			if (perf.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, perf.trim());
			
			if (docum.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(docum.trim()));
			
			pst.executeUpdate();
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
			setMensaje("Error SQL intentando ELIMINAR Usuario. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			
			return false;
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
		return true;
		
	}

	/**
	 * Funcinn: Compara beans de Usuario <br>
	 * 
	 * @param Usuario
	 *            c
	 * @param Usuario
	 *            c2
	 * @return boolean
	 **/
	public boolean compararBeans(Usuario c, Usuario c2) {
		if (!c.getUsucodjerar().equals(c2.getUsucodjerar()))
			return false;
		if (!c.getUsulogin().equals(c2.getUsulogin()))
			return false;
		if (!c.getUsupassword().equals(c2.getUsupassword()))
			return false;
		if (!c.getUsuperfcodigo().equals(c2.getUsuperfcodigo()))
			return false;
		if (!c.getUsupernumdocum().equals(c2.getUsupernumdocum()))
			return false;
		if (!c.getPertipdocum().equals(c2.getPertipdocum()))
			return false;
		if (!c.getPernumdocum().equals(c2.getPernumdocum()))
			return false;
		if (!c.getPernombre1().equals(c2.getPernombre1()))
			return false;
		if (!c.getPernombre2().equals(c2.getPernombre2()))
			return false;
		if (!c.getPerapellido1().equals(c2.getPerapellido1()))
			return false;
		if (!c.getPerapellido2().equals(c2.getPerapellido2()))
			return false;
		return true;
	}

	/**
	 * Funcinn: Subir al bean usuario informaciÃ³n desde la base de datos<br>
	 * 
	 * @param String
	 *            jerar
	 * @param String
	 *            perf
	 * @param String
	 *            docum
	 * @return Usuario
	 **/
	public Usuario asignarPersonalUsuario(String docum) {
		
		Usuario u = null;
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		
		int posicion = 1;
		
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioAsignar"));
			pst.clearParameters();

			if (docum.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, docum.trim());
			
			rs = pst.executeQuery();
			if (rs.next()) {
				u = new Usuario();
				int i = 1;
				u.setEstadoper("1");
				u.setPertipdocum(rs.getString(i++));
				u.setPernumdocum(rs.getString(i++));
				u.setPernombre1(rs.getString(i++));
				u.setPernombre2(rs.getString(i++));
				u.setPerapellido1(rs.getString(i++));
				u.setPerapellido2(rs.getString(i++));
				u.setPernumdocum2(u.getPernumdocum());
				u.setPertipdocum2(u.getPertipdocum());
				u.setPercorreoinstitucional(rs.getString(i++));
				u.setUsupernumdocum(u.getPernumdocum());
			}
			
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede establecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Usuario. Posible problema: ");
			switch (sqle.getErrorCode()) {
				default:
					setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return u;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
		return u;
		
	}

	/**
	 * Funcinn: Actualiza la información del usuario en la base de datos<br>
	 * 
	 * @param Usuario
	 *            u
	 * @return boolean
	 **/
	public boolean actualizarPersonal(Usuario u) {
		
		Connection cn = null;
		PreparedStatement pst = null;
		
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioActualizar"));
			pst.clearParameters();
			
			if (u.getPertipdocum().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(u.getPertipdocum().trim()));
			
			if (u.getPernumdocum().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPernumdocum().trim());
			
			if (u.getPernombre1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPernombre1().trim().toUpperCase());
			
			if (u.getPernombre2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPernombre2().trim().toUpperCase());
			
			if (u.getPerapellido1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPerapellido1().trim().toUpperCase());
			
			if (u.getPerapellido2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPerapellido2().trim().toUpperCase());
			
			if (u.getPercorreoinstitucional().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPercorreoinstitucional().trim().toUpperCase());
			
			if (u.getPertipdocum2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(u.getPertipdocum2().trim()));
			
			if (u.getPernumdocum2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPernumdocum2().trim());
			
			pst.executeUpdate();

			posicion = 1;
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioActualizarDocumento"));
			pst.clearParameters();
			
			if (u.getPernumdocum().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPernumdocum().trim());
			
			if (u.getPernumdocum2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, u.getPernumdocum2().trim());
			
			pst.executeUpdate();

			cn.commit();
			cn.setAutoCommit(true);
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			
			setMensaje("Error intentando actualizar información de Usuario. (" + sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
		return true;
		
	}

	/**
	 * Funcinn: Inserta un registro de personal en la Base de Datos<br>
	 * 
	 * @param Usuario
	 *            s
	 * @return boolean
	 **/
	public boolean insertarPersonal(Usuario s) {
		
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		
		Connection cn = null;
		PreparedStatement pst = null;
		
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbUsuario.getString("UsuarioInsertar"));
			pst.clearParameters();
			
			if (s.getPertipdocum().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(s.getPertipdocum().trim()));
			
			if (s.getPernumdocum().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getPernumdocum().trim());
			
			if (s.getPernombre1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getPernombre1().trim().toUpperCase());
			
			if (s.getPernombre2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getPernombre2().trim().toUpperCase());
			
			if (s.getPerapellido1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getPerapellido1().trim().toUpperCase());
			
			if (s.getPerapellido2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getPerapellido2().trim().toUpperCase());
			
			if (s.getPercorreoinstitucional().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getPercorreoinstitucional().trim().toUpperCase());
			
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Usuario. Posible problema: " + sqle);
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Compara beans de Usuario <br>
	 * 
	 * @param Usuario
	 *            c
	 * @param Usuario
	 *            c2
	 * @return boolean
	 **/
	public boolean compararBeansUsu(Usuario c, Usuario c2) {
		if (!c.getUsucodjerar().equals(c2.getUsucodjerar()))
			return false;
		if (!c.getUsulogin().equals(c2.getUsulogin()))
			return false;
		if (!c.getUsupassword().equals(c2.getUsupassword()))
			return false;
		if (!c.getUsuperfcodigo().equals(c2.getUsuperfcodigo()))
			return false;
		if (!c.getUsupernumdocum().equals(c2.getUsupernumdocum()))
			return false;
		return true;
	}

	/**
	 * Funcinn: Compara beans de Usuario <br>
	 * 
	 * @param Usuario
	 *            c
	 * @param Usuario
	 *            c2
	 * @return boolean
	 **/
	public boolean compararBeansPer(Usuario c, Usuario c2) {
		
		if (!c.getPertipdocum().equals(c2.getPertipdocum()))
			return false;
		if (!c.getPernumdocum().equals(c2.getPernumdocum()))
			return false;
		if (!c.getPernombre1().equals(c2.getPernombre1()))
			return false;
		if (!c.getPernombre2().equals(c2.getPernombre2()))
			return false;
		if (!c.getPerapellido1().equals(c2.getPerapellido1()))
			return false;
		if (!c.getPerapellido2().equals(c2.getPerapellido2()))
			return false;
		if (!c.getPercorreoinstitucional().equals(c2.getPercorreoinstitucional()))
			return false;
		
		return true;
	}

	/**
	 * Funcinn: Actualiza la contrasena en la base de datos<br>
	 * 
	 * @param String
	 *            c
	 * @param String
	 *            d
	 * @return boolean
	 **/
	public boolean actualizar(String c, String d) {
		
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;
		
		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbUsuario.getString("ContrasenaActualizar"));
			pst.clearParameters();
			
			if (c.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else {
				try {
					pst.setString(posicion++, md5(c.trim()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (d.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(d.trim()));
			
			int i = pst.executeUpdate();
			if (i == 0) {
				setMensaje("No se actualizo el registro");
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
			
			setMensaje("Error intentando actualizar Contrasena. (" + sqle.getErrorCode() + ") Posible problema: ");
			
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			
			return false;
			
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
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
	@Override
	public void setMensaje(String s) {
		mensaje += s;
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 **/
	@Override
	public String getMensaje() {
		return mensaje;
	}

	public String[] parsear(String a, int num) {
		int aux = 0, aux2 = -1, cont = 0;
		String m[] = new String[num];
		if (num == 2) {
			aux = a.lastIndexOf("|");
			if (aux != -1) {
				m[0] = a.substring(0, aux);
				m[1] = a.substring(aux + 1, a.length());
			} else {
				m[0] = a;
				m[1] = "";
			}
			return m;
		}
		for (int j = 0; j < num; j++) {
			aux = a.indexOf("|", aux);
			if (aux != -1) {
				m[j] = a.substring(aux2 + 1, aux);
				aux2 = aux;
				aux++;
			} else
				m[j] = a.substring(aux2 + 1, a.length());
		}
		return m;
	}

	public List getDependencias() throws Exception {

		int posicion = 1;
		
		ResultSet rs = null;
		Connection cn = null;
		ItemVO itemVO = null;
		PreparedStatement st = null;
		List list = new ArrayList();
		
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getDependencias"));

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

	public long getCodJerarquia(long dep) throws Exception {

		int posicion = 1;
		long codJerar = 0;
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getJerarquiaPOA"));
			st.setLong(1, dep);
			rs = st.executeQuery();
			if (rs.next()) {
				codJerar = rs.getLong(posicion++);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return 0;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return 0;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return codJerar;
		
	}
	
	public String getNombreDependencia(int codigoDependencia) throws Exception {

		int posicion = 1;
		String nombreDependencia = "";
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getNombreDependencia"));
			st.setLong(1, codigoDependencia);
			
			rs = st.executeQuery();
			if (rs.next()) {
				nombreDependencia = rs.getString(posicion++);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return nombreDependencia;
		
	}
	
	public String getNombreTipoDocumento(int codigoTipoDocumento) throws Exception {

		int posicion = 1;
		String nombreTipoDocumento = "";
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getNombreTipoDocumento"));
			st.setLong(1, codigoTipoDocumento);
			
			rs = st.executeQuery();
			if (rs.next()) {
				nombreTipoDocumento = rs.getString(posicion++);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return nombreTipoDocumento;
		
	}
	
	public String getNombreLocalidad(int idLocalidad) throws Exception {

		int posicion = 1;
		String nombreLocalidad = "";
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getNombreLocalidad"));
			st.setLong(1, idLocalidad);
			
			rs = st.executeQuery();
			if (rs.next()) {
				nombreLocalidad = rs.getString(posicion++);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return nombreLocalidad;
		
	}
	
	public String getNombreInstitucion(int idInstitucion) throws Exception {

		int posicion = 1;
		String nombreInstitucion = "";
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getNombreInstitucion"));
			st.setLong(1, idInstitucion);
			
			rs = st.executeQuery();
			if (rs.next()) {
				nombreInstitucion = rs.getString(posicion++);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return nombreInstitucion;
		
	}
	
	public String getNombreSede(int idInstitucion, int idSede) throws Exception {

		int posicion = 1;
		String nombreSede = "";
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getNombreSede"));
			st.setLong(1, idInstitucion);
			st.setLong(2, idSede);
			
			rs = st.executeQuery();
			if (rs.next()) {
				nombreSede = rs.getString(posicion++);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return nombreSede;
		
	}
	
	public String getNombreJornada(int idJornada) throws Exception {

		int posicion = 1;
		String nombreJornada = "";
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rbUsuario.getString("getNombreJornada"));
			st.setLong(1, idJornada);
			
			rs = st.executeQuery();
			if (rs.next()) {
				nombreJornada = rs.getString(posicion++);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return nombreJornada;
		
	}
	
	private String generarContrasena() {
		
		String NUMEROS = "0123456789";
		String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
		
		String strPassword = "";
		String key = NUMEROS + MAYUSCULAS + MINUSCULAS;
		
		for (int i = 0; i < 20; i++) {
			strPassword += (key.charAt((int)(Math.random() * key.length())));
		}
		
		// Se verifica que la longitud de la contraseña sea de 12 caracteres
		strPassword = strPassword.trim();
		strPassword = strPassword.substring(0, 12);
 
		return strPassword;
		
	}
	
	private void enviarCorreoContrasena(Usuario s) throws Exception {
		
		Mailer mailer = new Mailer();
		
		try {
			// El objeto que llega no tiene los nombres ni apellidos ni correo institucional por lo que toca consultarlos a la base de datos
			Usuario objDatosUsuario = asignarPersonalUsuario(s.getUsupernumdocum());
			objDatosUsuario.setUsupassword(s.getUsupassword());
			
			String strAsunto = "Envío Contraseña Temporal";
			String strCuerpo = cuerpoCorreoContrasenaTemporal(objDatosUsuario);
			
			mailer.enviarCorreo(objDatosUsuario.getPercorreoinstitucional(), strAsunto, strCuerpo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error de correo: " + e.getMessage());
		}
		
	}
	
	private String cuerpoCorreoContrasenaTemporal(Usuario objUsuario) {
		
		String headHtml = "<html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> </head> <body>";
		
		String mensaje = "<table><tr><td>" +
	    				"<p>Estimad@ " + objUsuario.getPernombre1() + " " + objUsuario.getPerapellido1() + ",</p>" +
	    				"<p>A continuaci&oacute;n encontrar&aacute; la contraseña temporal que le ha sido asignada por el sistema: <b>" + objUsuario.getUsupassword() + "</b>" +
	    				"<p>Tan pronto ingrese al aplicativo se le solicitar&aacute; que la cambie por su seguridad.</p>" +
	    				"<p>Por favor, no responda este correo. Es un mensaje generado autom&aacute;ticamente</p><br>"+
	    				"</td></tr></table>";
	    
		String footerHtml = "</body> </html>";
		
		String cuerpoCorreo = headHtml + mensaje + footerHtml;
		
		return cuerpoCorreo;
	}
	
	public void registroAuditoria(String strLogin, java.sql.Date dtFecha, String strOperacion, String strObservaciones) {

		int posicion = 1; // posicion inicial de llenado del preparedstatement		
		
		Connection cn = null;
		PreparedStatement pst = null;
		
		//java.util.Date utilDate = new Date();
		//java.sql.Date date = new java.sql.Date(utilDate.getTime());
		
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbUsuario.getString("AuditoriaGestionUsuario"));
			pst.clearParameters();
			
			//pst.setDate(posicion++, new java.sql.Date(System.currentTimeMillis()));
			pst.setDate(posicion++, dtFecha);
			
			if (strLogin.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, strLogin.trim());
			
			if (strOperacion.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, strOperacion.trim());
			
			if (strObservaciones.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, strObservaciones.trim());
			
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);

		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexión con la base de datos: ");
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Auditoria. Posible problema: " + sqle);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
	}
	
	/**
	 * Verifica si el correo institucional ya está registrado en el sistema
	 * 
	 **/
	public boolean existeCorreoInstitucional(String strCorreo) {
		
		ResourceBundle props = ResourceBundle.getBundle("mail");
        
        String d_fromAddress = props.getString("mail.fromAddress");
		
        // 22/10/2018 - El correo registrado en el archivo de propiedades se puede repetir cuantas veces sea necesario
		if (!strCorreo.toLowerCase().equals(d_fromAddress.toLowerCase())) {
			
			String strConsulta = "SELECT PEREMAIL FROM PERSONAL WHERE (TRIM(PEREMAIL) = '" + strCorreo.trim().toLowerCase() + "') OR (TRIM(PEREMAIL) = '" + strCorreo.trim().toUpperCase() + "')";
			return cursor.existe(strConsulta);
			
		} else {
			
			return false;
			
		}
		
	}
	
	public void enviarCorreoGrupoApoyoAccesos(Login objLogin, Date strFechaCreacion, Usuario objUsuario, String strAsunto, HttpServletRequest request) throws Exception {
		
		Mailer mailer = new Mailer();
		
		try {

			// El objeto que llega no tiene los nombres ni apellidos ni correo institucional por lo que toca consultarlos a la base de datos
			Usuario objUsuarioAfectado = asignarPersonalUsuario(objUsuario.getUsupernumdocum());
			// Al nuevo objeto se le asignan los valores de los permisos otorgados para enviar un solo objeto a la siguiente función
			objUsuarioAfectado.setUsucodjerar(objUsuario.getUsucodjerar());
			objUsuarioAfectado.setUsucodjerar2(objUsuario.getUsucodjerar2());
			objUsuarioAfectado.setUsuperfcodigo(objUsuario.getUsuperfcodigo());
			objUsuarioAfectado.setUsuperfcodigo2(objUsuario.getUsuperfcodigo2());
			
			String strCuerpo = cuerpoCorreoGrupoApoyoAccesos(objLogin, strFechaCreacion, objUsuarioAfectado, strAsunto, request);
			
			mailer.enviarCorreoGrupoApoyo(strAsunto, strCuerpo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error de correo: " + e.getMessage());
		}
		
	}
	
	public String cuerpoCorreoGrupoApoyoAccesos(Login objLogin, Date strFechaCreacion, Usuario objUsuarioAfectado, String strAsunto, HttpServletRequest request) {
		
		PerfilDAO objPerfilDAO = new PerfilDAO(cursor);
		String headHtml = "<html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> </head> <body>";
		
		String[] params;
		String mensaje = "";
		String strConsulta = "";
		SimpleDateFormat formaFecha = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		
		try {
		
			switch (strAsunto) {
				case "Creación de Acceso":
					mensaje = "<table><tr><td>" +
		    				"<p><b>RESUMEN DE LA CREACIÓN DEL ACCESO:</b></p>" +
		    				"<p><b>FECHA:</b> " + formaFecha.format(strFechaCreacion) + "</p>" +
		    				"<p><b>USUARIO CREADOR:</b> " + objLogin.getUsuarioId() + " - " + objLogin.getNomPerfil() + "</p>" +
		    				"<p><b>NOMBRE COMPLETO DEL USUARIO AFECTADO:</b> " + objUsuarioAfectado.getPernombre1() + " " + objUsuarioAfectado.getPernombre2() + " " +
		    				objUsuarioAfectado.getPerapellido1() + " " + objUsuarioAfectado.getPerapellido2() + "</p>" +
		    				"<p><b>TIPO DE IDENTIFICACIÓN:</b> " + getNombreTipoDocumento(Integer.parseInt(objUsuarioAfectado.getPertipdocum())) + "</p>" +
		    				"<p><b>NÚMERO DE IDENTIFICACIÓN:</b> " + objUsuarioAfectado.getUsupernumdocum() + "</p>" +
		    				"<p><b>CORREO INSTITUCIONAL:</b> " + objUsuarioAfectado.getPercorreoinstitucional() + "</p>" +
		    				"<p><b>PERMISOS:</b> USUCODJERAR: " + objUsuarioAfectado.getUsucodjerar() + ", USUPERFCODIGO: " + objUsuarioAfectado.getUsuperfcodigo() + "</p>" +
		    				"<p><b>PERFIL:</b> " + objUsuarioAfectado.getUsuperfcodigo() + " - " + objPerfilDAO.asignarPerfil(objUsuarioAfectado.getUsuperfcodigo()).getPerfnombre() + "</p>";
					
					if (request.getParameter("usucoddependencia") != null) {
						mensaje = mensaje + "<p><b>DEPENDENCIA:</b> " + getNombreDependencia(Integer.parseInt(request.getParameter("usucoddependencia"))) + "</p>"; 
					}
					
					if (request.getParameter("municipio") != null) {
						mensaje = mensaje + "<p><b>LOCALIDAD:</b> " + request.getParameter("municipio") + " - " + getNombreLocalidad(Integer.parseInt(request.getParameter("municipio"))) + "</p>";
					}
					
					if (request.getParameter("institucion") != null) {
						mensaje = mensaje + "<p><b>ESTABLECIMIENTO EDUCATIVO:</b> " + getNombreInstitucion(Integer.parseInt(request.getParameter("institucion"))) + "</p>"; 
					}
					
					if (request.getParameter("sede") != null) {
						mensaje = mensaje + "<p><b>SEDE:</b> " + getNombreSede(Integer.parseInt(request.getParameter("institucion")), Integer.parseInt(request.getParameter("sede"))) + "</p>"; 
					}
					
					if (request.getParameter("jornada") != null) {
						mensaje = mensaje + "<p><b>JORNADA:</b> " + getNombreJornada(Integer.parseInt(request.getParameter("jornada"))) + "</p>";
					}
					
					mensaje = mensaje + "</td></tr></table><br/><br/><br/>";
					break;
					
				case "Modificación de Acceso":
					mensaje = "<table><tr><td>" +
	    				"<p><b>RESUMEN DE LA MODIFICACIÓN DEL ACCESO:</b></p>" +
	    				"<p><b>FECHA:</b> " + formaFecha.format(strFechaCreacion) + "</p>" +
	    				"<p><b>USUARIO MODIFICADOR:</b> " + objLogin.getUsuarioId() + " - " + objLogin.getNomPerfil() + "</p>" +
	    				"<p><b>NOMBRE COMPLETO DEL USUARIO AFECTADO:</b> " + objUsuarioAfectado.getPernombre1() + " " + objUsuarioAfectado.getPernombre2() + " " +
	    				objUsuarioAfectado.getPerapellido1() + " " + objUsuarioAfectado.getPerapellido2() + "</p>" +
	    				"<p><b>TIPO DE IDENTIFICACIÓN:</b> " + getNombreTipoDocumento(Integer.parseInt(objUsuarioAfectado.getPertipdocum())) + "</p>" +
	    				"<p><b>NÚMERO DE IDENTIFICACIÓN:</b> " + objUsuarioAfectado.getUsupernumdocum() + "</p>" +
	    				"<p><b>CORREO INSTITUCIONAL:</b> " + objUsuarioAfectado.getPercorreoinstitucional() + "</p><br /><br />" +
	    				
	    				// DATOS ANTERIORES DEL ACCESO
	    				"<p><b>PERMISOS ANTERIORES:</b> USUCODJERAR: " + objUsuarioAfectado.getUsucodjerar2() + ", USUPERFCODIGO: " + objUsuarioAfectado.getUsuperfcodigo2() + "</p>" + 
	    				"<p><b>PERFIL:</b> " + objUsuarioAfectado.getUsuperfcodigo2() + " - " + objPerfilDAO.asignarPerfil(objUsuarioAfectado.getUsuperfcodigo2()).getPerfnombre() + "</p>";
						
					strConsulta = "SELECT G_JERDEPTO, G_JERMUNIC, G_JERINST, G_JERSEDE, G_JERJORN FROM G_JERARQUIA WHERE G_JERCODIGO = '" + objUsuarioAfectado.getUsucodjerar2() + "'"; 
					params = cursor.nombre(strConsulta);
					
					if (params != null) {
						
						// Si el parámetro de la Institucion en nulo entonces se trata de una Dependencia
						if (params[2].equals("")) {
							mensaje = mensaje + "<p><b>DEPENDENCIA:</b> " + getNombreDependencia(Integer.parseInt(params[0])) + "</p>"; 
						} else {
						
							if (params[1] != null && !params[1].isEmpty()) {
								mensaje = mensaje + "<p><b>LOCALIDAD:</b> " + params[1] + " - " + getNombreLocalidad(Integer.parseInt(params[1])) + "</p>";
							}
							
							if (params[2] != null && !params[2].isEmpty()) {
								mensaje = mensaje + "<p><b>ESTABLECIMIENTO EDUCATIVO:</b> " + getNombreInstitucion(Integer.parseInt(params[2])) + "</p>";
							}
							
							if (params[3] != null  && !params[3].isEmpty()) {
								mensaje = mensaje + "<p><b>SEDE:</b> " + getNombreSede(Integer.parseInt(params[2]), Integer.parseInt(params[3])) + "</p>"; 
							}
							
							if (params[4] != null  && !params[4].isEmpty()) {
								mensaje = mensaje + "<p><b>JORNADA:</b> " + getNombreJornada(Integer.parseInt(params[4])) + "</p>";
							}
							
						}
						
					}
						
					// DATOS ACTUALES DEL ACCESO
					mensaje = mensaje + "<br /><br /><p><b>PERMISOS ACTUALES:</b> USUCODJERAR: " + objUsuarioAfectado.getUsucodjerar() + ", USUPERFCODIGO: " + objUsuarioAfectado.getUsuperfcodigo() + "</p>" + 
								"<p><b>PERFIL:</b> " + objUsuarioAfectado.getUsuperfcodigo() + " - " + objPerfilDAO.asignarPerfil(objUsuarioAfectado.getUsuperfcodigo()).getPerfnombre() + "</p>";
					
					strConsulta = "SELECT G_JERDEPTO, G_JERMUNIC, G_JERINST, G_JERSEDE, G_JERJORN FROM G_JERARQUIA WHERE G_JERCODIGO = '" + objUsuarioAfectado.getUsucodjerar() + "'"; 
					params = cursor.nombre(strConsulta);
					
					if (params != null) {
						
						// Si el parámetro de la Institucion en nulo entonces se trata de una Dependencia
						if (params[2].equals("")) {
							mensaje = mensaje + "<p><b>DEPENDENCIA:</b> " + getNombreDependencia(Integer.parseInt(params[0])) + "</p>"; 
						} else {
						
							if (params[1] != null && !params[1].isEmpty()) {
								mensaje = mensaje + "<p><b>LOCALIDAD:</b> " + params[1] + " - " + getNombreLocalidad(Integer.parseInt(params[1])) + "</p>";
							}
							
							if (params[2] != null && !params[2].isEmpty()) {
								mensaje = mensaje + "<p><b>ESTABLECIMIENTO EDUCATIVO:</b> " + getNombreInstitucion(Integer.parseInt(params[2])) + "</p>";
							}
							
							if (params[3] != null && !params[3].isEmpty()) {
								mensaje = mensaje + "<p><b>SEDE:</b> " + getNombreSede(Integer.parseInt(params[2]), Integer.parseInt(params[3])) + "</p>"; 
							}
							
							if (params[4] != null && !params[4].isEmpty()) {
								mensaje = mensaje + "<p><b>JORNADA:</b> " + getNombreJornada(Integer.parseInt(params[4])) + "</p>";
							}
							
						}
						
					}
					
					mensaje = mensaje + "</td></tr></table><br/><br/><br/>";
					break;
				case "Eliminación de Acceso":
					mensaje = "<table><tr><td>" +
		    				"<p><b>RESUMEN DE LA ELIMINACIÓN DEL ACCESO:</b></p>" +
		    				"<p><b>FECHA:</b> " + formaFecha.format(strFechaCreacion) + "</p>" +
		    				"<p><b>USUARIO MODIFICADOR:</b> " + objLogin.getUsuarioId() + " - " + objLogin.getNomPerfil() + "</p>" +
		    				"<p><b>NOMBRE COMPLETO DEL USUARIO AFECTADO:</b> " + objUsuarioAfectado.getPernombre1() + " " + objUsuarioAfectado.getPernombre2() + " " +
		    				objUsuarioAfectado.getPerapellido1() + " " + objUsuarioAfectado.getPerapellido2() + "</p>" +
		    				"<p><b>TIPO DE IDENTIFICACIÓN:</b> " + getNombreTipoDocumento(Integer.parseInt(objUsuarioAfectado.getPertipdocum())) + "</p>" +
		    				"<p><b>NÚMERO DE IDENTIFICACIÓN:</b> " + objUsuarioAfectado.getUsupernumdocum() + "</p>" +
		    				"<p><b>CORREO INSTITUCIONAL:</b> " + objUsuarioAfectado.getPercorreoinstitucional() + "</p>" +
							"<p><b>PERMISOS:</b> USUCODJERAR: " + objUsuarioAfectado.getUsucodjerar() + ", USUPERFCODIGO: " + objUsuarioAfectado.getUsuperfcodigo() + "</p>" +
		    				"<p><b>PERFIL:</b> " + objUsuarioAfectado.getUsuperfcodigo() + " - " + objPerfilDAO.asignarPerfil(objUsuarioAfectado.getUsuperfcodigo()).getPerfnombre() + "</p>";
					
					strConsulta = "SELECT G_JERDEPTO, G_JERMUNIC, G_JERINST, G_JERSEDE, G_JERJORN FROM G_JERARQUIA WHERE G_JERCODIGO = '" + objUsuarioAfectado.getUsucodjerar() + "'"; 
					params = cursor.nombre(strConsulta);
					
					if (params != null) {
						
						// Si el parámetro de la Institucion en nulo entonces se trata de una Dependencia
						if (params[2].equals("")) {
							mensaje = mensaje + "<p><b>DEPENDENCIA:</b> " + getNombreDependencia(Integer.parseInt(params[0])) + "</p>"; 
						} else {
						
							if (params[1] != null && !params[1].isEmpty()) {
								mensaje = mensaje + "<p><b>LOCALIDAD:</b> " + params[1] + " - " + getNombreLocalidad(Integer.parseInt(params[1])) + "</p>";
							}
							
							if (params[2] != null && !params[2].isEmpty()) {
								mensaje = mensaje + "<p><b>ESTABLECIMIENTO EDUCATIVO:</b> " + getNombreInstitucion(Integer.parseInt(params[2])) + "</p>";
							}
							
							if (params[3] != null && !params[3].isEmpty()) {
								mensaje = mensaje + "<p><b>SEDE:</b> " + getNombreSede(Integer.parseInt(params[2]), Integer.parseInt(params[3])) + "</p>"; 
							}
							
							if (params[4] != null && !params[4].isEmpty()) {
								mensaje = mensaje + "<p><b>JORNADA:</b> " + getNombreJornada(Integer.parseInt(params[4])) + "</p>";
							}
							
						}
						
					}
					
					mensaje = mensaje + "</td></tr></table><br/><br/><br/>";
					break;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	    
		String footerHtml = "</body> </html>";
		
		String cuerpoCorreo = headHtml + mensaje + footerHtml;
		
		return cuerpoCorreo;
	}
	
	public String cuerpoCorreoGeneracionBoletin() {
		
		String headHtml = "<html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> </head> <body>";
		
		String mensaje = "<table><tr><td>" +
	    				"<p>Apreciado(a) Secretario Acad&eacute;mico, {nombre}</p>" +
	    				"<br/>"+
	    				"<p>Le informamos que su reporte de tipo boletin solicitado para el colegio {institucion} {estado} </p>" +//esta listo para ser descargado	    				
	    				"</td></tr></table>";
	    
		String footerHtml = "</body> </html>";
		
		String cuerpoCorreo = headHtml + mensaje + footerHtml;
		
		return cuerpoCorreo;
	}

}
