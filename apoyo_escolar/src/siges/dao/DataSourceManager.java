package siges.dao;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;
import javax.naming.*;
import java.io.PrintWriter;
import siges.exceptions.*;

/**
 * Nombre: DataSourceManager<br>
 * Descripcinn: Crear datasource para ser usado por la aplicacinn<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class DataSourceManager {
	private static DataSource fuenteDatos;

	private static DataSource fuenteDatos2;
	
	private static DataSource fuenteDatosDuc;

	//private static String[] jndi = { "_", "siges", "matriculas", "navegador" };
	private static String[] jndi = { "_", "apoyoWeb","apoyoWeb","apoyoWeb" };

	/**
	 * Constructor de la clase
	 */
	private DataSourceManager() {
	}

	/**
	 * Funcinn: Init<br>
	 */
	static void init(int n) {
		String cadena = jndi[n];
		try {
			switch (n) {
			case 1:
				if (fuenteDatos == null) {
					System.out.println("-SIGES- INTENTANDO CREAR DATASOURCE");
					System.out.println("CADENA CONEXION ES:"+cadena);
					Context ctx = new InitialContext();
					Context ctx2 = (Context) ctx.lookup("java:comp/env");
					if (ctx == null) {
						System.out.println("-SIGES- No hay un context definido");
						return;
					}
					fuenteDatos = (DataSource) ctx.lookup("jdbc/" + cadena);
//					fuenteDatos = (DataSource) ctx.lookup(cadena);
					if (fuenteDatos == null) {
						System.out.println("-SIGES- No hay un origen de datos definido");
						return;
					}
					// fuenteDatos.setLogWriter(new PrintWriter(System.out));
				}
				break;
			case 2:
				if (fuenteDatos2 == null) {
					System.out.println("-SIGES2- INTENTANDO CREAR DATASOURCE");
					Context ctx = new InitialContext();
					Context ctx2 = (Context) ctx.lookup("java:comp/env");
					if (ctx == null) {
						System.out.println("-SIGES2- No hay un context definido");
						return;
					}
					fuenteDatos2 = (DataSource) ctx.lookup("jdbc/" + cadena);
//					fuenteDatos2 = (DataSource) ctx.lookup(cadena);
					if (fuenteDatos2 == null) {
						System.out.println("-SIGES2- No hay un origen de datos definido");
						return;
					}
					// fuenteDatos.setLogWriter(new PrintWriter(System.out));
				}
				break;
				case 3:
				if (fuenteDatosDuc == null) {
					System.out.println("-SIGESDUC- INTENTANDO CREAR DATASOURCE");
					Context ctx = new InitialContext();
					Context ctx2 = (Context) ctx.lookup("java:comp/env");
					if (ctx == null) {
						System.out.println("-SIGESDUC- No hay un context definido");
						return;
					}
//					fuenteDatosDuc = (DataSource) ctx.lookup("jdbc/" + cadena);
					fuenteDatosDuc = (DataSource) ctx.lookup(cadena);
					if (fuenteDatosDuc == null) {
						System.out.println("-SIGESDUC- No hay un origen de datos definido");
						return;
					}
				}
				break;
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
			System.out.println("-SIGES- Error iniciando JNDI " + cadena + ": " + String.valueOf(cnfe));
			return;
		}
	}

	/**
	 * Funcinn: Obtener Conexinn<br>
	 * 
	 * @return Connection
	 */
	static public Connection getConnection(int n) throws InternalErrorException {
		init(n);
		try {
			switch (n) {
			case 1:
				return fuenteDatos.getConnection();
			case 2:
				return fuenteDatos2.getConnection();
			case 3:
				return fuenteDatosDuc.getConnection();
			}
			throw new SQLException("-SIGES- Tipo de conexion no conocido, Use 1,2 o 3 solamente");
		} catch (SQLException cnfe) {
			System.out.println(cnfe.getMessage());
			cnfe.printStackTrace();
			System.out.println(n + "_SIGES_ Error sql obteniendo conexion - DEAM: " + String.valueOf(cnfe));
			System.out.println("--:" + cnfe.getErrorCode());
			// cnfe.printStackTrace();
			switch (cnfe.getErrorCode()) {
			case 0:
				throw new InternalErrorException(n + "El sistema esta ocupado, espere un momento y luego intente de nuevo");
			}
			throw new InternalErrorException(n + "-SIGES- Error sql obteniendo conexion: " + cnfe.getMessage());
		} catch (Exception cnfe) {
			System.out.println("-SIGES- Error obteniendo conexion: " + String.valueOf(cnfe));
			cnfe.printStackTrace();
			throw new InternalErrorException(n + "-SIGES- Error obteniendo conexion: " + "Fuente de Datos no existe:" + cnfe.getMessage());
		}
	}

	/**
	 * Funcinn: Cierra el Data Source<br>
	 */
	static public void closeDataSource() {
		fuenteDatos = null;
		System.out.println("-SIGES- Desalojando DataSource");
	}

	public void destroy() {
		fuenteDatos = null;
	}
}