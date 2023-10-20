package siges.util;

import java.sql.Connection;
import java.sql.PreparedStatement;

import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Nombre: Logger<br>
 * Descripcinn: Manejo del Logger<br>
 * Funciones de la pngina: Implementar el logger para que sea usado en cualquier
 * clae que lo llame<br>
 * Entidades afectadas: log_application<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class Logger {
	private static final int N = 1;
	private static int debug;
	private static int posicion;
	private static final String a = "INSERT INTO log_application (log_usuario, log_mensaje, log_nivel, log_tipo, log_clase) VALUES (substr(?,1,15),substr(?,1,500),?,?,substr(?,1,150))";
	private static final String b = "INSERT INTO log_application (log_usuario, log_mensaje, log_aviso, log_nivel, log_tipo, log_clase) VALUES (substr(?,1,15),substr(?,1,500),substr(?,1,500),?,?,substr(?,1,150))";
	private static final String c = "INSERT INTO log_application (log_usuario, log_mensaje, log_aviso, log_periodo, log_nivel, log_tipo, log_clase) VALUES (substr(?,1,15),substr(?,1,500),substr(?,1,500),substr(?,1,1),?,?,substr(?,1,150))";

	private Logger() {
	}

	/**
	 * Funcinn: Cerrar Conexinn<br>
	 **/
	public void cerrar() {
	}

	/**
	 * Funcinn: Carga el logger en la aplicacinn<br>
	 **/
	static public void Cargar() {
		// System.out.println("Logger -Iniciando-");
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(a);
			pst.clearParameters();
			posicion = 1;
			pst.setString(posicion++, "0");
			pst.setString(posicion++, "INICIANDO LOGGER");
			pst.setInt(posicion++, 0);
			pst.setInt(posicion++, 1);
			pst.setString(posicion++, "siges.util.Logger");
			pst.executeUpdate();
		} catch (Exception sqle) {
			System.out.println("Logger -No iniciado: Conexion rechazada: -");
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException e) {
			}
		}
		// System.out.println("Logger -Listo-");
	}

	static public void Cargar2() {
		Connection cn = null;
		try {
			cn = DataSourceManager.getConnection(2);
		} catch (Exception sqle) {
			System.out.println("Logger2 -No iniciado: Conexion rechazada: -");
			return;
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException e) {
			}
		}
		// System.out.println("Logger2 -Listo-");
	}

	/**
	 * Funcinn: Manda el log a la base de datos<br>
	 * 
	 * @param String
	 *            usuario
	 * @param String
	 *            mensaje
	 * @param int nivel
	 * @param int tipo
	 * @param String
	 *            cliente
	 * 
	 **/
	static public void print(String usuario, String mensaje, int nivel,
			int tipo, String cliente) {
		Connection cn = null;
		PreparedStatement pst = null;
		int n = 0;
		if (nivel <= debug) {
			if (nivel == -9)
				System.out.println("-SIGES LOGGER-" + mensaje);
			try {
				cn = DataSourceManager.getConnection(1);
				pst = cn.prepareStatement(a);
				pst.clearParameters();
				posicion = 1;
				pst.setString(posicion++, usuario);
				if (mensaje == null)
					pst.setString(posicion++, "");
				else
					pst.setString(posicion++,
							mensaje.length() > 400 ? mensaje.substring(0, 490)
									: mensaje);
				pst.setInt(posicion++, nivel);
				pst.setInt(posicion++, tipo);
				pst.setString(posicion++, cliente);
				n = pst.executeUpdate();
				pst.close();
			} catch (Exception sqle) {
				System.out.println("-SIGES LOGGER- Error en Logger: "
						+ sqle.getMessage());
			} finally {
				try {
					OperacionesGenerales.closeStatement(pst);
					OperacionesGenerales.closeConnection(cn);
				} catch (InternalErrorException e) {
				}
			}
		}
	}

	static public void print(String usuario, String mensaje, String aviso,
			int nivel, int tipo, String cliente) {
		Connection cn = null;
		PreparedStatement pst = null;
		int n = 0;
		if (nivel <= debug) {
			if (nivel == -9)
				System.out.println("-SIGES LOGGER-" + mensaje);
			try {
				cn = DataSourceManager.getConnection(1);
				pst = cn.prepareStatement(b);
				pst.clearParameters();
				posicion = 1;
				pst.setString(posicion++, usuario);
				if (mensaje == null)
					pst.setString(posicion++, "");
				else
					pst.setString(posicion++,
							mensaje.length() > 400 ? mensaje.substring(0, 490)
									: mensaje);
				pst.setString(posicion++, aviso);
				pst.setInt(posicion++, nivel);
				pst.setInt(posicion++, tipo);
				pst.setString(posicion++, cliente);
				n = pst.executeUpdate();
				pst.close();
			} catch (Exception sqle) {
				System.out.println("-SIGES LOGGER- Error en Logger: "
						+ sqle.getMessage());
			} finally {
				try {
					OperacionesGenerales.closeStatement(pst);
					OperacionesGenerales.closeConnection(cn);
				} catch (InternalErrorException e) {
				}
			}
		}
	}

	static public void print(String usuario, String mensaje, String aviso,
			String periodo, int nivel, int tipo, String cliente) {
		Connection cn = null;
		PreparedStatement pst = null;
		int n = 0;
		if (nivel <= debug) {
			if (nivel == -9)
				// System.out.println("-SIGES LOGGER-" + mensaje);
				try {
					cn = DataSourceManager.getConnection(1);
					pst = cn.prepareStatement(c);
					pst.clearParameters();
					posicion = 1;
					pst.setString(posicion++, usuario);
					if (mensaje == null)
						pst.setString(posicion++, "");
					else
						pst.setString(
								posicion++,
								mensaje.length() > 400 ? mensaje.substring(0,
										490) : mensaje);
					pst.setString(posicion++, aviso);
					pst.setString(posicion++, periodo);
					pst.setInt(posicion++, nivel);
					pst.setInt(posicion++, tipo);
					pst.setString(posicion++, cliente);
					n = pst.executeUpdate();
					pst.close();
				} catch (Exception sqle) {
					System.out.println("-SIGES LOGGER- Error en Logger: "
							+ sqle.getMessage());
				} finally {
					try {
						OperacionesGenerales.closeStatement(pst);
						OperacionesGenerales.closeConnection(cn);
					} catch (InternalErrorException e) {
					}
				}
		}
	}

	/**
	 * Funcinn: Fijar debug<br>
	 * 
	 * @param int n
	 **/
	static public void setDebug(int n) {
		debug = n;
	}

	/**
	 * Funcinn: obtener recurso<br>
	 * 
	 * @return int
	 **/
	static public int getDebug() {
		return debug;
	}
}