package siges.dao;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import javax.sql.DataSource;
import javax.naming.*;
import siges.exceptions.*;

/**
 * Nombre: Cursor Descripcion: Administra la conexion a la base de datos y las operaciones basicas Funciones de la pagina: Abre y cierra conxiones,statements,preparedstatements, etc Entidades afectadas: Todas las tablas de la BD Fecha de modificacinn: 01/10/04
 * 
 * @author latined
 * @version $v 1.2 $
 */
public class Cursor {
	private String msg;

	private String a;

	private java.text.SimpleDateFormat sdf, sdf1;

	private java.util.ResourceBundle rb;

	private String motor, driver, host, puerto, bd, usuario, password, url;

	private String[] jndi = { "_", "siges", "matriculas" };

	private int tipo;

	/**
	 * Constructor de la clase
	 */
	public Cursor() {
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		sdf.setLenient(false);
		sdf1.setLenient(false);
		a = msg = "";
		tipo = 1;
	}

	/**
	 * Constructor de la clase
	 */
	public Cursor(int tipoConexion) {
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		sdf.setLenient(false);
		sdf1.setLenient(false);
		a = msg = "";
		tipo = tipoConexion;
	}
	
	/**
	 * Funcinn: Retorna el objeto Connection que se inicializo en el constructor
	 * 
	 * @return Connection
	 */
	public Connection getConnection() throws InternalErrorException {
		try {
			Connection con = DataSourceManager.getConnection(tipo);
			return con;
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Funcinn: Retorna el objeto Connection que se inicializo en el constructor y la forma por la cual debe conectarse
	 * 
	 * @return Connection
	 */
	public Connection getConnection(int n) throws InternalErrorException {
		try {
			Connection con = DataSourceManager.getConnection(n);
			return con;
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	public void cerrar() {
		// try{
		// //fuenteDatos=null;
		// OperacionesGenerales.closeStatement(st);
		// OperacionesGenerales.closeStatement(pst);
		// OperacionesGenerales.closeConnection(cn);
		// }catch(InternalErrorException inte){}
	}

	/**
	 * Abre el objeto conexion y asigna el Statement
	 * 
	 * @return boolean
	 */
	public boolean abrir2() {
		Connection cn = null;
		Statement st = null;
		try {
			rb = java.util.ResourceBundle.getBundle("sql");
			motor = rb.getString("conexion.motor");// nombre del motor de base de datos
			driver = rb.getString("conexion.driver");// nombre de la clase jdbc para la conexion
			host = rb.getString("conexion.host");// direccion IP del servidor de base de datos
			puerto = rb.getString("conexion.puerto");// nombre del puerto para la comunicacion con la base de datos
			bd = rb.getString("conexion.bd");// nombre de la instancia a utilizar
			usuario = rb.getString("conexion.usuario");// nombre del usuario de la base de datos
			password = rb.getString("conexion.password");// password del usuario de la base de datos
			Class.forName(driver);
			cn = DriverManager.getConnection(getUrl(), usuario, password);
			st = cn.createStatement();
		} catch (ClassNotFoundException cnfe) {
			setMensaje("Error sql buscando los controladores: " + String.valueOf(cnfe));
			return false;
		} catch (SQLException sqle) {
			setMensaje("Error sql intentando abrir la conexion: " + String.valueOf(sqle));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		msg = "";
		return true;
	}

	/**
	 * Abre el objeto conexion y asigna el Statement
	 * 
	 * @return boolean
	 */
	public boolean abrir(int n) {
		Connection cn = null;
		try {
			tipo = n;
			cn = DataSourceManager.getConnection(n);
			if (cn == null) {
				setMensaje("No hay una conexion establecida");
				return false;
			}
		} catch (InternalErrorException e) {
			setMensaje(e.getMensaje());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: retorna un objeto Statement usando el objeto Conection abierto en la funcion 'abrir'
	 * 
	 * @return Statement
	 */
	public Statement cursor() {
		Statement stm = null;
		Connection cn = null;

		try {
			cn = getConnection();
			stm = cn.createStatement();
		} catch (Exception sqle) {
			setMensaje("Error intentando crear un Statement: " + String.valueOf(sqle));
			System.out.println("Error en cursor: " + sqle);
		} finally {
			try {
				OperacionesGenerales.closeStatement(stm);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return stm;
	}

	/**
	 * Funcinn: Recibe el nombre de una tabla de la base de datos y retorna un array con los nombres de los campos de dicha tabla
	 * 
	 * @param String
	 *            tabla
	 * @return String[]
	 */
	public String[] getColumnas(String tabla) {
		String campo[] = new String[15];
		Connection cn = null;
		ResultSet r = null;
		Statement st = null;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery("Select * from " + tabla);
			for (int i = 1; i <= campo.length; i++)
				campo[i] = (r.getMetaData().getColumnName(i) != null) ? r.getMetaData().getColumnName(i) : new String("");
			r.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de busqueda de metadatos: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return campo;
	}

	/**
	 * Funcinn: Evalua si la consulta SQL arroja por lo menos un registro
	 * 
	 * @param String
	 *            buscar
	 * @return boolean true=posee minimo un registro; false=no arrojo resultados
	 */
	public boolean existe(String buscar) {
		Connection cn = null;
		ResultSet r = null;
		Statement st = null;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (!r.next()) {
				//System.out.println("NO ENCONTRO HIJO");
				r.close();
				return false;
			} else {
				//System.out.println("SI ENCONTRO HIJO");
				r.close();
				return true;
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de existencia: " + sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Evalua si la consulta SQL arroja por lo menos un registro
	 * 
	 * @param String
	 *            buscar
	 * @return boolean true=posee minimo un registro; false=no arrojo resultados
	 */
	public boolean existe1(String[] buscar) {
		Connection cn = null;
		ResultSet r = null;
		Statement st = null;
		try {
			cn = getConnection();
			for (int i = 0; i < buscar.length; i++) {
				if (buscar[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(buscar[i]);
					if (r.next()) {
						//System.out.println("---SI ENCONTRn HIJO---");
						return true;
					}
					r.close();
					st.close();
				}
			}
			//System.out.println("---NO ENCONTRn HIJOS---");
			return false;

			// if (r == null || !r.next()){
			// System.out.println("---NO ENCONTRn HIJOS---");
			// r.close();
			// return false;
			// }else{
			// System.out.println("---SI ENCONTRn HIJOS---");
			// r.close();
			// return true;
			// }
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de existencia: " + sqle.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: una sentencia de actualizacion de datos y retorna true si la ejecucion fue exitosa o false si la ejecucion arrojo una exepcion
	 * 
	 * @param String
	 *            insertar
	 * @return boolean
	 */
	public boolean registrar(String insertar) {
		int cantidad;
		Connection cn = null;
		Statement st = null;
		try {
			cn = getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			//System.out.println("Inicia Eliminar " + (insertar) + ": " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
			cantidad = st.executeUpdate(insertar);
			//System.out.println("Fin Eliminar " + (insertar) + ": " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
			cn.commit();
			return true;
		} catch (InternalErrorException e) {
			setMensaje("Error obteniendo conexinn: " + e.toString());
			return false;
		} catch (SQLException sqle) {

			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");

			switch (sqle.getErrorCode()) {
			// Violacinn de llave primaria
			case 1:
			case 2291:
				setMensaje("Ya existe un registro con el mismo cndigo");
				break;
			// Violacinn de llave secundaria
			case 2292:
				setMensaje("No se puede realizar la operacinn ya que tiene registros asociados");
				break;
			// Longitud de campo
			case 1401:
				setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
				break;

			default:
				setMensaje(sqle.toString().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: una sentencia de actualizacion de datos y retorna true si la ejecucion fue exitosa o false si la ejecucion arrojo una exepcion
	 * 
	 * @param String
	 *            insertar
	 * @return boolean
	 */
	public boolean registrar_transaccion(String[] insertar) {
		int cantidad;
		Connection cn = null;
		Statement st = null;
		try {
			cn = getConnection();
			cn.setAutoCommit(false);

			// for (int k = 0; k < insertar.length; k++)
			// System.out.println("TRANSACCInN: " + insertar[k]);

			for (int i = 0; i < insertar.length; i++) {
				if (insertar[i] != null) {
					st = cn.createStatement();
					//System.out.println("Inicia Eliminar " + (i) + ": " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
					cantidad = st.executeUpdate(insertar[i]);
					st.close();
					//System.out.println("Termina Eliminar " + (i) + ": " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
				}
			}
			cn.commit();
			return true;
		} catch (InternalErrorException e) {
			setMensaje("Error obteniendo conexinn: " + e.toString());
			return false;
		} catch (SQLException sqle) {

			try {
				cn.rollback();
				//System.out.println("***ROLLBACK**");
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");

			switch (sqle.getErrorCode()) {
			// Violacinn de llave primaria
			case 1:
			case 2291:
				setMensaje("Ya existe un registro con el mismo cndigo");
				break;
			// Violacinn de llave secundaria
			case 2292:
				setMensaje("No se puede realizar la operacinn ya que tiene registros asociados");
				break;
			// Longitud de campo
			case 1401:
				setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
				break;
			default:
				setMensaje(sqle.toString().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: retorna un array con las dos primeras columnas de la consulta enviada como parametro
	 * 
	 * @param String
	 *            buscar
	 * @return String[][]
	 */

	public String[][] getDatoMaestro(String buscar) {
		String n[][] = null;
		Connection cn = null;
		ResultSet r = null;
		Statement st = null;
		int i = 0;
		int j = 0;
		int filas = 1;
		int columnas;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (r.next()) {
				while (r.next()) {// recorro el resulset para encontrar el numero de registros y crear el array
					if (r.getString(1) != null)
						filas++;
				}
				r.close();
				st.close();
				st = cn.createStatement();
				r = st.executeQuery(buscar);
				columnas = r.getMetaData().getColumnCount();
				n = new String[filas][columnas];// creo el array
				while (r.next()) {// itero por cada registro y anexo esta informacion al array
					for (i = 0; i < columnas; i++) {
						n[j][i] = r.getString(i + 1);
					}
					j++;
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia: " + sqle.getMessage());
			//System.out.println("excepcion");
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return n;
	}

	/**
	 * Funcinn: retorna un registro a partir de una consulta sql
	 * 
	 * @param String
	 *            buscar
	 * @return String[]
	 */
	public String[] nombre(String buscar) {
		// int a = 1;
		String n[] = null;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (r.next()) {
				n = new String[r.getMetaData().getColumnCount()];
				for (int i = 0; i < r.getMetaData().getColumnCount(); i++)
					n[i] = r.getString(i + 1) != null ? r.getString(i + 1) : "";
			}
			return n;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado retornar sentencia de busqueda: " + sqle.toString());
			return n;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: retorna un registro a partir de una consulta sql
	 * 
	 * @param String
	 *            buscar
	 * @return String[]
	 */
	public String[] getFila(String buscar) {
		// int a = 1;
		String n[] = null;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (r.next()) {
				n = new String[r.getMetaData().getColumnCount()];
				for (int i = 0; i < r.getMetaData().getColumnCount(); i++)
					n[i] = r.getString(i + 1) != null ? r.getString(i + 1) : "";
			}
			return n;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			//System.out.println(sqle.toString());
			setMensaje("Error sql intentado retornar sentencia de busqueda: " + sqle.toString());
			return n;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: retorna un registro a partir de una consulta sql
	 * 
	 * @param String
	 *            buscar
	 * @return String[]
	 */
	public String[] getRegistro(PreparedStatement pst) {
		// int a = 1;
		String s[] = null;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = pst.executeQuery();
			if (r.next()) {
				s = new String[r.getMetaData().getColumnCount()];
				for (int i = 0; i < r.getMetaData().getColumnCount(); i++)
					s[i] = r.getString(i + 1) != null ? r.getString(i + 1) : "";
			}
			return s;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia preparada: " + sqle.toString());
			return s;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: un registro a partir de una consulta sql y dos parametros que indican desde y hasta que columna se debe mostrar
	 * 
	 * @param String
	 *            buscar
	 * @param int
	 *            inicio
	 * @param int
	 *            fin
	 * @return String[]
	 */
	public String[] nombre(String buscar, int inicio, int fin) {
		int a = 1;
		String n[] = new String[12];
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (r.next()) {
				for (int i = inicio; i <= fin; i++)
					n[a++] = r.getString(i) != null ? r.getString(i) : "";
			}
			return n;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia: " + sqle.toString());
			return n;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Recibe una sentencia sql y retorna el entero de la primera columna del primera registro
	 * 
	 * @param String
	 *            buscar
	 * @return int
	 */
	public int cod(String buscar) {
		int num = 0;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (r.next())
				num = r.getInt(1);
			r.close();
			return num;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return -1;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado traer codigo: " + sqle.toString());
			return -1;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String lista(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td >" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		for (i = 1; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td >" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listadatomaestro(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		for (i = 1; i < 2; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 2; i < 3; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		for (i = 3; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='15' maxlength='15' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 4; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listadivision(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql

					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		for (i = 2; i < 3; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 3; i < 5; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 5; i < 6; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='20' maxlength='20' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 6; i < 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listaparametro(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		try {
			cn = getConnection();

			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		for (i = 1; i < 2; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 2; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String lista3(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + " style='width: 150px' disabled >\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "<input type='hidden' name='lista1' value=" + texto[2] + ">";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}

		for (i = 1; i < 2; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' readonly maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 2; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		for (i = 4; i < 5; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='15' maxlength='15' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listadatogeneral(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		for (i = 2; i < 3; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 3; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		for (i = 4; i < 5; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='15' maxlength='15' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 5; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listaggrado(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + " style='width: 150px' disabled >\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "<input type='hidden' name='lista1' value=" + texto[1] + ">";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}

		for (i = 2; i < 3; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' readonly maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 3; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		for (i = 4; i < 5; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='15' maxlength='15' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 5; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listagrado(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		for (i = 1; i <= 7; i++) {
			if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
				try {
					cn = getConnection();
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td >" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
				} catch (InternalErrorException in) {
					setMensaje("NO se puede estabecer conexinn con la base de datos: ");
					return null;
				} catch (Exception sqle) {
					setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
					return null;// significa que hubo un error sql
				} finally {
					try {
						OperacionesGenerales.closeResultSet(r);
						OperacionesGenerales.closeStatement(st);
						OperacionesGenerales.closeConnection(cn);
					} catch (InternalErrorException inte) {
					}
				}
			}
		}
		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		a += "</tr>";
		for (i = 1; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td >" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listagrupo(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		for (i = 1; i <= 7; i++) {
			if (criterioLista[i] != null) {
				try {
					cn = getConnection();
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					// a+="</tr>\n";
					// if(i%2==0)a+="</tr>\n";
				} catch (InternalErrorException in) {
					setMensaje("NO se puede estabecer conexinn con la base de datos: ");
					return null;
				} catch (Exception sqle) {
					setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
					return null;// significa que hubo un error sql
				} finally {
					try {
						OperacionesGenerales.closeResultSet(r);
						OperacionesGenerales.closeStatement(st);
						OperacionesGenerales.closeConnection(cn);
					} catch (InternalErrorException inte) {
					}
				}
			}
		}
		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		a += "</tr>";
		for (i = 1; i < 2; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		for (i = 2; i < 5; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */
	public String listalogro(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();

			for (i = 1; i < 6; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		a += "</tr>";

		for (i = 5; i < 6; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 == 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<input  name='texto" + i + "' type='text' size='97' maxlength='200' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		for (i = 6; i < 7; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 == 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='12' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}

		for (i = 7; i < 10; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<textarea  name='texto" + i + "' cols='95' rows='4'>" + texto[i] + "</textarea>";// muestra el textarea
				a += "</td>";
				// a+="</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listaeditlogro(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();

			for (i = 1; i < 2; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' disabled onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					a += "<input type='hidden' name='lista1' value=" + texto[1] + ">";
					r.close();
					st.close();
				}
			}

			for (i = 2; i < 3; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' disabled onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					a += "<input type='hidden' name='lista2' value=" + texto[2] + ">";
					r.close();
					st.close();
				}
			}

			for (i = 3; i < 6; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		a += "</tr>";

		for (i = 5; i < 6; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 == 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<input  name='texto" + i + "' type='text' size='97' maxlength='200' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		for (i = 6; i < 7; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 == 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='12' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}

		for (i = 7; i < 10; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td >" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<textarea  name='texto" + i + "' cols='95' rows='4'>" + texto[i] + "</textarea>";// muestra el textarea
				a += "</td>";
				// a+="</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listadescriptor(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();

			for (i = 1; i < 7; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		a += "</tr>";

		for (i = 6; i < 7; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<input  name='texto" + i + "' type='text' size='97' maxlength='200' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		for (i = 7; i < 8; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='12' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}

		for (i = 8; i < 9; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 == 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<textarea  name='texto" + i + "' cols='95' rows='4'>" + texto[i] + "</textarea>";// muestra el textarea
				a += "</td>";
				// a+="</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listaeditdescriptor(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		try {
			cn = getConnection();

			for (i = 1; i < 2; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' disabled onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					a += "<input type='hidden' name='lista1' value=" + texto[1] + ">";
					r.close();
					st.close();
				}
			}

			for (i = 2; i < 3; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' disabled onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					a += "<input type='hidden' name='lista2' value=" + texto[2] + ">";
					r.close();
					st.close();
				}
			}
			// System.out.println("DESCRIPTOREDIT OK...");
			for (i = 3; i < 7; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		a += "</tr>";

		for (i = 6; i < 7; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<input  name='texto" + i + "' type='text' size='97' maxlength='200' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		for (i = 7; i < 8; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='12' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}

		for (i = 8; i < 10; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 == 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td colspan=3>";
				a += "<textarea  name='texto" + i + "' cols='95' rows='4'>" + texto[i] + "</textarea>";// muestra el textarea
				a += "</td>";
				// a+="</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */
	public String listaasignatura(String[] tituloCheck, String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden, String[] chk) {

		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					r.close();
					st.close();
				}
			}

			for (int j = 0; j < hidden.length; j++) {
				if (hidden[j] != null) {
					if (j == 0) {
						a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
					} else {
						a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
					}
				}
			}
			a += "</tr>";

			for (i = 3; i < 4; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 != 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}

			for (i = 4; i < 5; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 != 0)
						a += "<tr>";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					a += "</tr>";
				}
			}

			for (i = 5; i < 6; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 != 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='15' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}
			a += "</table><table width='100%'>";
			a += "<tr>";
			a += "<th colspan=4><span class='style2'>*</span><B>" + "Grados" + ":</B></th>\n";
			a += "</tr>";

			for (i = 1; i <= 5; i++) {
				int xx = 0;
				if (tituloCheck[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(tituloCheck[i]);
					while (r.next()) {
						xx++;
						if (xx % 2 != 0)
							a += "<tr>\n";
						a += "<td colspan=1 valign='top'>";
						if (xx != 1)
							a += "<input type='checkbox' name='chk' value='" + r.getString(1) + "' onClick='javaScript:if(this.checked)document.forms[1].ih[" + (xx - 1) + "].disabled=false;'";
						else
							a += "<input type='checkbox' name='chk' value='" + r.getString(1) + "' onClick='javaScript:if(this.checked)document.forms[1].ih.disabled=false;'";
						if (chk != null) {
							for (int k = 0; k < chk.length; k++) {
								if (r.getString(1).equals(chk[k])) {
									a += " CHECKED ";
								}
							}
						}
						a += "> " + r.getString(2) + "\n";
						a += "<td align='right'><b>IH:</b> <input type='text' name='ih' value='' size='2' maxlength='2'>";
						a += "</td>";
					}
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */
	public String listeditaasignatura(String[] tituloCheck, String[] tituloCheckSelected, String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden, String[] chk) {

		String a = "";
		String selected1 = null;
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		try {
			cn = getConnection();
			for (i = 1; i < 2; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' disabled onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					a += "<input type='hidden' name='lista1' value='" + texto[1] + "'>";
					r.close();
					st.close();
				}
			}

			for (i = 2; i < 3; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' disabled onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i])) {
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
							selected1 = r.getString(2);
						} else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					// a+="</tr>\n";
					// if(i%2==0)a+="</tr>\n";
					a += "<input type='hidden' name='lista2' value='" + texto[2] + "'>";
					a += "<input type='hidden' name='nombre1' value =" + selected1 + ">";
					r.close();
					st.close();
				}
			}

		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		a += "</tr>";

		for (i = 3; i < 4; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 == 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		for (i = 4; i < 5; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		for (i = 5; i < 6; i++) {
			if (tituloTexto[i] != null) {
				if (i % 2 != 0)
					a += "<tr>\n";
				// a+="<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='15' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				// a+="</tr>";
			}
		}
		a += "</table><table width='100%'>";
		a += "<tr >";
		a += "<th colspan=4 ><span class='style2'>*</span><B>" + "Grados" + ":</B></th>\n";
		a += "</tr>";

		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				int xx = 0;
				if (tituloCheck[i] != null) {// esta posicion del array contiene una consulta sql
					if (tituloCheckSelected[i] != null) {// consulta sql de los grados seleccionados en grado_asignatura
						String matrix[][] = getDatoMaestro(tituloCheckSelected[i]);
						st = cn.createStatement();
						r = st.executeQuery(tituloCheck[i]);
						while (r.next()) {
							xx++;
							if (xx % 2 != 0)
								a += "<tr>\n";
							a += "<td colspan=1 valign='top'>";
							a += "<input type='checkbox' name='chk' value='" + r.getString(1) + "' onClick='javaScript:if(this.checked)document.forms[1].ih[" + (xx - 1) + "].disabled=false;'";
							if (chk != null) {
								for (int k = 0; k < chk.length; k++) {
									if (r.getString(1).equals(chk[k])) {
										a += " CHECKED ";
									}
								}
							}
							a += "> " + r.getString(2) + "<br>\n";
							String valor = null;
							if (matrix != null) {
								for (int y = 0; y < matrix.length; y++) {
									if (matrix[y][0] != null) {
										if (r.getString(1).equals(matrix[y][0])) {
											valor = matrix[y][1];
											break;
										}
									}
								}
							}
							a += "<td align='right'><b>IH:</b> <input type='text' name='ih' value='" + (valor != null ? valor : "") + "' size='2' maxlength='2'>";
							a += "</td>";
						}
						r.close();
						st.close();
					}
				}
			}
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listaarea(String[] tituloCheck, String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden, String[] chk) {

		String a = "";
		int i;
		ResultSet r = null;
		Connection cn = null;
		Statement st = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					// a+="</tr>\n";
					// if(i%2==0)a+="</tr>\n";
					r.close();
					st.close();
				}
			}

			for (int j = 0; j < hidden.length; j++) {
				if (hidden[j] != null) {
					if (j == 0) {
						a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
					} else {
						a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
					}
				}
			}
			a += "</tr>";
			for (i = 2; i < 3; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 != 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}
			for (i = 3; i < 4; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 == 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}
			for (i = 4; i <= 5; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 == 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input name='texto" + i + "' type='text' size='15' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}
			a += "<tr >";
			a += "<th colspan=4 ><span class='style2'>*</span><B>" + "Grados" + ":</B></th>\n";
			a += "</tr>";

			for (i = 1; i <= 5; i++) {
				int xx = 0;
				if (tituloCheck[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(tituloCheck[i]);
					while (r.next()) {
						xx++;
						if (xx % 2 != 0)
							a += "<tr>\n";
						a += "<td colspan=2>";
						a += "<input type='checkbox' name='chk' value='" + r.getString(1) + "'";
						if (chk != null) {
							for (int k = 0; k < chk.length; k++) {
								if (r.getString(1).equals(chk[k])) {
									a += " CHECKED ";
								}
							}
						}
						a += "> " + r.getString(2) + "<br>\n";
						a += "</td>";
					}
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listaeditarea(String[] tituloCheck, String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden, String[] chk) {

		String a = "";
		int i;
		ResultSet r = null;
		Connection cn = null;
		Statement st = null;
		String selected = null;
		try {
			cn = getConnection();
			for (i = 1; i < 2; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					if (i % 2 != 0)
						a += "<tr>\n";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>\n";// muestra el titulo del select
					a += "<td>\n";
					a += "<select name='lista" + i + "' disabled style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					a += "<input type='hidden' name='lista1' value=" + texto[1] + ">";
					a += "<input type='hidden' name='nombre' value =" + selected + ">";
					//System.out.println("nombre: " + selected);
					r.close();
					st.close();
				}
			}

			for (int j = 0; j < hidden.length; j++) {
				if (hidden[j] != null) {
					if (j == 0) {
						a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
					} else {
						a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
					}
				}
			}
			a += "</tr>";
			for (i = 2; i < 3; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 != 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}
			for (i = 3; i < 4; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 == 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}
			for (i = 4; i <= 5; i++) {
				if (tituloTexto[i] != null) {
					if (i % 2 == 0)
						a += "<tr>\n";
					// a+="<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='15' maxlength='10' value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					// a+="</tr>";
				}
			}
			a += "<tr >";
			a += "<th colspan=4 ><span class='style2'>*</span><B>" + "Grados" + ":</B></th>\n";
			a += "</tr>";

			for (i = 1; i <= 5; i++) {
				int xx = 0;
				if (tituloCheck[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(tituloCheck[i]);
					while (r.next()) {
						xx++;
						if (xx % 2 != 0)
							a += "<tr>\n";
						a += "<td colspan=2>";
						a += "<input type='checkbox' name='chk' value='" + r.getString(1) + "'";
						if (chk != null) {
							for (int k = 0; k < chk.length; k++) {
								if (r.getString(1).equals(chk[k])) {
									a += " CHECKED ";
								}
							}
						}
						a += "> " + r.getString(2) + "<br>\n";
						a += "</td>";
					}
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String lista1(String[] tituloCheck, String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden, String[] chk) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 3; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td >" + tituloLista[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<select name='lista" + i + "'style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>\n";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>\n";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>\n";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>\n";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>\n";
					a += "</tr>\n";
					r.close();
					st.close();
				}
			}
			for (int j = 0; j < hidden.length; j++) {
				if (hidden[j] != null) {
					if (j == 0) {
						a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
					} else {
						a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
					}
				}
			}

			for (i = 1; i <= 5; i++) {
				if (tituloTexto[i] != null) {
					a += "<tr>";
					a += "<td >" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>\n";// muestra el input de tipo text
					a += "</td>";
					a += "</tr>";
				}
			}

			for (i = 1; i <= 5; i++) {
				if (tituloCheck[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(tituloCheck[i]);
					a += "<tr>";
					a += "<td ><B>" + "Grado" + ":</B></td>\n";
					a += "<td>";
					while (r.next()) {
						a += "<input type='checkbox' name='chk' onClick='horas(document.forms[1],chk.length)' value='" + r.getString(1) + "' ";
						if (chk != null) {
							for (int k = 0; k < chk.length; k++) {

								if (r.getString(1).equals(chk[k])) {
									a += " CHECKED ";
								}
							}
						}
						a += "> " + r.getString(2) + "<br>\n";
					}
					a += "</td>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String lista2(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden, String cont) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql

					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td >" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
			for (int j = 0; j < hidden.length; j++) {
				if (hidden[j] != null) {
					if (j == 0) {
						a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
					} else {
						a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
					}
				}
			}
			// if(cont==0){
			for (i = 1; i <= 9; i++) {
				if (tituloTexto[i] != null) {
					a += "<tr>";
					a += "<td >" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
					a += "<td>";
					a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' " + cont + " value='" + texto[i] + "'>";// muestra el input de tipo text
					a += "</td>";
					a += "</tr>";
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */
	public String listatextarea(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td ><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + "' style='width: 200px' onChange='lista(document.forms[1]," + i + ")'>\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}
		for (i = 1; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td ><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<textarea name='texto" + i + "' cols='80' rows='4'>" + texto[i] + "</textarea>";// muestra el textarea
				a += "</td>";
				a += "</tr>";
			}
		}
		for (i = 4; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td >" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<textarea name='texto" + i + "' cols='80' rows='4'>" + texto[i] + "</textarea>";// muestra el textarea
				a += "</td>";
				a += "</tr>";
			}
		}
		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listanoedit(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;

		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + " style='width: 150px' disabled >\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "<input type='hidden' name='lista1' value=" + texto[4] + ">";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}

		for (i = 1; i < 2; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' readonly  maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 2; i < 3; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		for (i = 3; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='15' maxlength='15' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 4; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		return a;
	}

	/**
	 * retorna un string donde se indican los campos del formulario de nuevo dato maestro y de editar dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            tituloLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloTexto
	 * @param String[]
	 *            texto
	 * @param String[]
	 *            hidden **
	 * @return String
	 */

	public String listadatomaestronoedit(String[] criterioLista, String[] tituloLista, String[] lista, String[] tituloTexto, String[] texto, String[] hidden) {
		String a = "";
		int i;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		try {
			cn = getConnection();
			for (i = 1; i <= 5; i++) {
				if (criterioLista[i] != null) {// esta posicion del array contiene una consulta sql
					st = cn.createStatement();
					r = st.executeQuery(criterioLista[i]);
					a += "<tr>";
					a += "<td><span class='style2'>*</span>" + tituloLista[i] + ":</td>";// muestra el titulo del select
					a += "<td>";
					a += "<select name='lista" + i + " style='width: 150px' disabled >\n";
					if (lista[i].equals("null"))
						a += "<option value='null' selected>--seleccione una--</option>";// muestra a la primera opcion como predefinida
					else
						a += "<option value='null'>--seleccione una--</option>";// la primera opcion no es predefinida
					while (r.next()) {
						if (r.getString(1).equals(lista[i]))
							a += "<option selected value='" + r.getString(1) + "'>";// muestra a esta opcion como predefinida
						else
							a += "<option value='" + r.getString(1) + "'>";// no muestra a esta opcion como predefinida
						a += r.getString(2);
					}
					a += "</td>";
					a += "<input type='hidden' name='lista1' value=" + texto[4] + ">";
					a += "</tr>";
					r.close();
					st.close();
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de tabla: " + sqle.toString());
			return null;// significa que hubo un error sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		for (int j = 0; j < hidden.length; j++) {
			if (hidden[j] != null) {
				if (j == 0) {
					a += "<input type='hidden' name='text' value='" + hidden[j] + "'>";// muestra como oculto el parametro text
				} else {
					a += "<input type='hidden' name='text" + (j + 1) + "' value='" + hidden[j] + "'>";
				}
			}
		}

		for (i = 1; i < 2; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' readonly maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 2; i < 3; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='35' maxlength='100' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}
		for (i = 3; i < 4; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='15' maxlength='15' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		for (i = 4; i <= 9; i++) {
			if (tituloTexto[i] != null) {
				a += "<tr>";
				a += "<td><span class='style2'>*</span>" + tituloTexto[i] + ":</td>";// muestra el titulo del input de tipo text
				a += "<td>";
				a += "<input  name='texto" + i + "' type='text' size='5' maxlength='5' value='" + texto[i] + "'>";// muestra el input de tipo text
				a += "</td>";
				a += "</tr>";
			}
		}

		return a;
	}

	/**
	 * Funcinn: Retorna un string donde se indican los campos del formulario de criterios de busqueda de dato maestro
	 * 
	 * @param String[]
	 *            criterioLista
	 * @param String[]
	 *            lista
	 * @param String[]
	 *            tituloLista
	 */
	public String filtro(String[] criterioLista, String[] lista, String[] tituloLista, int dato) {
		ResultSet r = null;
		Connection cn = null;
		Statement st = null;
		String a = "";
		try {
			cn = getConnection();

			switch (dato) {

			case 29:
				for (int i = 1; i <= 4; i++) {
					if (criterioLista[i] != null) {// si en la posicion del array hay una consulta sql
						a += "<tr>\n";
						a += "<td><span class='style2'>*</span>";
						// a+=tituloLista[i];//muestra el titulo del select
						a += "" + tituloLista[i] + "";
						a += "</td>\n";
						a += "<td>\n";
						a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
						st = cn.createStatement();
						r = st.executeQuery(criterioLista[i]);
						if (lista[i].equals("0"))// significa que el select tendra como opcion por defecto la primera
							a += "<option selected value='0'>--seleccione una--</option>";
						else
							a += "<option value='0'>TODOS</option>";// significa que la primera no es la predefinida
						while (r.next()) {
							if (r.getString(1).equals(lista[i]))// significa que esta opcion es la predefinida
								a += "<option selected value='" + r.getString(1) + "'>";
							else
								a += "<option value='" + r.getString(1) + "'>";// esta opcion no es la predefinida
							a += r.getString(2);
							a += "</option>\n";
						}
						a += "</select>\n";
						a += "</td>\n";
						a += "</tr>\n";
						r.close();st.close();
					}
				}
				break;
			case 30:
				for (int i = 1; i <= 4; i++) {
					if (criterioLista[i] != null) {// si en la posicion del array hay una consulta sql
						a += "<tr>\n";
						a += "<td><span class='style2'>*</span>";
						// a+=tituloLista[i];//muestra el titulo del select
						a += "" + tituloLista[i] + "";
						a += "</td>\n";
						a += "<td>\n";
						a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
						st = cn.createStatement();
						r = st.executeQuery(criterioLista[i]);
						if (lista[i].equals("0"))// significa que el select tendra como opcion por defecto la primera
							a += "<option selected value='0'>--seleccione una--</option>";
						else
							a += "<option value='0'>TODOS</option>";// significa que la primera no es la predefinida
						while (r.next()) {
							if (r.getString(1).equals(lista[i]))// significa que esta opcion es la predefinida
								a += "<option selected value='" + r.getString(1) + "'>";
							else
								a += "<option value='" + r.getString(1) + "'>";// esta opcion no es la predefinida
							a += r.getString(2);
							a += "</option>\n";
						}
						a += "</select>\n";
						a += "</td>\n";
						a += "</tr>\n";
						r.close();st.close();
					}
				}
				break;
			case 37:
				for (int i = 1; i <= 4; i++) {
					if (criterioLista[i] != null) {// si en la posicion del array hay una consulta sql
						a += "<tr>\n";
						a += "<td><span class='style2'>*</span>";
						// a+=tituloLista[i];//muestra el titulo del select
						a += "" + tituloLista[i] + "";
						a += "</td>\n";
						a += "<td>\n";
						a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
						st = cn.createStatement();
						r = st.executeQuery(criterioLista[i]);
						if (lista[i].equals("0"))// significa que el select tendra como opcion por defecto la primera
							a += "<option selected value='-99'>--seleccione una--</option>";
						else
							a += "<option value='-99'>TODOS</option>";// significa que la primera no es la predefinida
						while (r.next()) {
							if (r.getString(1).equals(lista[i]))// significa que esta opcion es la predefinida
								a += "<option selected value='" + r.getString(1) + "'>";
							else
								a += "<option value='" + r.getString(1) + "'>";// esta opcion no es la predefinida
							a += r.getString(2);
							a += "</option>\n";
						}
						a += "</select>\n";
						a += "</td>\n";
						a += "</tr>\n";
						r.close();st.close();
					}
				}
				break;
			case 39:
				for (int i = 1; i <= 3; i++) {
					if (criterioLista[i] != null) {// si en la posicion del array hay una consulta sql
						a += "<tr>\n";
						a += "<td><span class='style2'>*</span>";
						// a+=tituloLista[i];//muestra el titulo del select
						a += "" + tituloLista[i] + "";
						a += "</td>\n";
						a += "<td>\n";
						a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
						st = cn.createStatement();
						r = st.executeQuery(criterioLista[i]);
						if (lista[i].equals("0"))// significa que el select tendra como opcion por defecto la primera
							a += "<option selected value='-99'>--seleccione una--</option>";
						else
							a += "<option value='-99'>TODOS</option>";// significa que la primera no es la predefinida
						while (r.next()) {
							if (r.getString(1).equals(lista[i]))// significa que esta opcion es la predefinida
								a += "<option selected value='" + r.getString(1) + "'>";
							else
								a += "<option value='" + r.getString(1) + "'>";// esta opcion no es la predefinida
							a += r.getString(2);
							a += "</option>\n";
						}
						a += "</select>\n";
						a += "</td>\n";
						a += "</tr>\n";
						r.close();st.close();
					}
				}
				for (int i = 4; i <= 5; i++) {
					if (criterioLista[i] != null) {// si en la posicion del array hay una consulta sql
						a += "<tr>\n";
						a += "<td>";
						// a+=tituloLista[i];//muestra el titulo del select
						a += "" + tituloLista[i] + "";
						a += "</td>\n";
						a += "<td>\n";
						a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
						st = cn.createStatement();
						r = st.executeQuery(criterioLista[i]);
						if (lista[i].equals("0"))// significa que el select tendra como opcion por defecto la primera
							a += "<option selected value='-99'>TODOS</option>";
						else
							a += "<option value='-99'>TODOS</option>";// significa que la primera no es la predefinida
						while (r.next()) {
							if (r.getString(1).equals(lista[i]))// significa que esta opcion es la predefinida
								a += "<option selected value='" + r.getString(1) + "'>";
							else
								a += "<option value='" + r.getString(1) + "'>";// esta opcion no es la predefinida
							a += r.getString(2);
							a += "</option>\n";
						}
						a += "</select>\n";
						a += "</td>\n";
						a += "</tr>\n";
						r.close();st.close();
					}
				}
				break;
			default:
				for (int i = 1; i <= 4; i++) {
					if (criterioLista[i] != null) {// si en la posicion del array hay una consulta sql
						a += "<tr>\n";
						a += "<td >";
						// a+=tituloLista[i];//muestra el titulo del select
						a += "" + tituloLista[i] + "";
						a += "</td>\n";
						a += "<td>\n";
						a += "<select name='lista" + i + "' style='width: 150px' onChange='lista(document.forms[1]," + i + ")'>\n";
						st = cn.createStatement();
						r = st.executeQuery(criterioLista[i]);
						if (lista[i].equals("0"))// significa que el select tendra como opcion por defecto la primera
							a += "<option selected value='0'>TODOS</option>";
						else
							a += "<option value='0'>TODOS</option>";// significa que la primera no es la predefinida
						while (r.next()) {
							if (r.getString(1).equals(lista[i]))// significa que esta opcion es la predefinida
								a += "<option selected value='" + r.getString(1) + "'>";
							else
								a += "<option value='" + r.getString(1) + "'>";// esta opcion no es la predefinida
							a += r.getString(2);
							a += "</option>\n";
						}
						a += "</select>\n";
						a += "</td>\n";
						a += "</tr>\n";
						r.close();st.close();
					}
				}
			}// end switch
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error sql intentado ejecutar sentencia de filtro: " + sqle.toString());
			return null;// retorna null si ocurre una excepcion sql
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return a;
	}

	/**
	 * Funcinn: retorna un string donde se indican las filas y columnas de una lista de dato maestro
	 * 
	 * @param String
	 *            editar
	 * @param String
	 *            consulta
	 * @param String[]
	 *            titulo
	 * @param int
	 *            inicio
	 * @param int
	 *            fin
	 * @param int
	 *            dato
	 */
	public String getTablaMaestra(String editar, String consulta, String[] titulo, int inicio, int fin, int dato) {
		int i, j = 0;
		String a = "";
		if (existe(consulta)) {
			ResultSet r = null;
			Connection cn = null;
			Statement st = null;
			try {
				cn = getConnection();
				st = cn.createStatement();
				r = st.executeQuery(consulta);
				a += "<tr>\n";
				a += "<th  width='45'>Opción</th>\n";
				for (i = inicio; i <= fin; i++) {
					a += "<th >";
					a += titulo[i];// coloca el titulo a todas las columnas de la tabla
					a += "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</th>\n";
				}
				a += "</tr>\n";
				while (r.next()) {
					j += 1;
					a += "<tr>\n";
					a += "<td align='center' ><b>";
					if (editar == null) {// significa que se va a mostrar las filas sin la posibilidad de editar alguna
						a += j;
					} else {
						switch (dato) {
						case 28:// caso en el cual el hipervinculo tiene que mandar dos parametros ya que esta tabla contine una llave principal compuesta de dos campos
							a += "<input type='hidden' name ='text2'>";
							//a += "<input type='radio' name ='text' value='" + r.getString(4) + "'><input type='hidden' name ='valor2' value='" + r.getString(5) + "'>";
							a += "<a href='javaScript:editar2("+ r.getString(4)+","+r.getString(5)+ ");'><img border='0' src='../etc/img/editar.png' width='15' height='15'></a>";
							a += "<a href='javaScript:eliminar2("+ r.getString(4)+","+r.getString(5)+ ");'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>";
							break;
						case 30:// caso en el cual el hipervinculo tiene que mandar dos parametros ya que esta tabla contine una llave principal compuesta de dos campos
							a += "<input type='hidden' name ='text2'>";
							//a += "<input type='radio' name ='text' value='" + r.getString(5) + "'><input type='hidden' name ='valor2' value='" + r.getString(6) + "'>";
							a += "<a href='javaScript:editar2("+ r.getString(5)+","+r.getString(6)+ ");'><img border='0' src='../etc/img/editar.png' width='15' height='15'></a>";
							a += "<a href='javaScript:eliminar2("+ r.getString(5)+","+r.getString(6)+ ");'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>";
							break;
						case 31:
							a += "<input type='hidden' name ='text_' value='" + r.getString(3) + "'>";
							a += "<input type='hidden' name ='ext' value='1'>";
							//a += "<input type='radio' name ='text' value='" + r.getString(3) + "'>";
							a += "<a href='javaScript:editar1("+ r.getString(3)+ ");'><img border='0' src='../etc/img/editar.png' width='15' height='15'></a>";
							a += "<a href='javaScript:eliminar1("+ r.getString(3)+ ");'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>";
							break;
						case 37:
							a += "<input type='hidden' name ='text2'>";
							a += "<input type='hidden' name ='text_' value='" + r.getString(5) + "'>";
							a += "<input type='hidden' name ='ext' value='1'>";
							//a += "<input type='radio' name ='text' value='" + r.getString(5) + "'><input type='hidden' name ='valor2' value='" + r.getString(6) + "'>";
							a += "<a href='javaScript:editar2("+ r.getString(5)+","+r.getString(6)+ ");'><img border='0' src='../etc/img/editar.png' width='15' height='15'></a>";
							a += "<a href='javaScript:eliminar2("+ r.getString(5)+","+r.getString(6)+ ");'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>";
							break;
						case 39:
							a += "<input type='hidden' name ='text2'>";
							a += "<input type='hidden' name ='text_' value='" + r.getString(6) + "'>";
							a += "<input type='hidden' name ='ext' value='1'>";
							//a += "<input type='radio' name ='text' value='" + r.getString(6) + "'><input type='hidden' name ='valor2' value='" + r.getString(7) + "'>";
							a += "<a href='javaScript:editar2("+ r.getString(6)+","+r.getString(7)+ ");'><img border='0' src='../etc/img/editar.png' width='15' height='15'></a>";
							a += "<a href='javaScript:eliminar2("+ r.getString(6)+","+r.getString(7)+ ");'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>";
							break;
						default:// caso en el cual el hipervinculo solo manda un parametro (id de la tabla)
							a += "<input type='hidden' name ='text_' value='" + r.getString(1) + "'>";
							a += "<input type='hidden' name ='ext' value='1'>";
							//<a href='javaScript:editar();'><img border='0' src="etc/img/editar.png" width='15' height='15'></a>
							//a += "<input type='radio' name ='text' value='" + r.getString(1) + "'>";
							//a += "<input type='hidden' name ='text' value='"+ r.getString(1)+ "'>";
							a += "<a href='javaScript:editar1("+ r.getString(1)+ ");'><img border='0' src='../etc/img/editar.png' width='15' height='15'></a>";
							a += "<a href='javaScript:eliminar1("+ r.getString(1)+ ");'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>";
						}
					}
					a += "</b></td>\n";
					for (i = inicio; i <= fin; i++) {
						a += "<td  align='left'>";
						if (r.getString(i) != null)
							a += r.getString(i);// llena cada fila con los datos correspondientes
						a += "</td>\n";
					}
					a += "</tr>\n";
				}
				return a;
			} catch (InternalErrorException in) {
				setMensaje("NO se puede estabecer conexinn con la base de datos: ");
				//System.out.println("error tabla maestra" + in);
				return null;
			} catch (Exception sqle) {
				setMensaje("Error sql intentado ejecutar sentencia de tabla maestra: " + sqle.toString());
				//System.out.println("error en funcinn getTablaMaestra: " + sqle);
				return null;// retorna null si ocurre una exepcion sql
			} finally {
				try {
					OperacionesGenerales.closeResultSet(r);
					OperacionesGenerales.closeStatement(st);
					OperacionesGenerales.closeConnection(cn);
				} catch (InternalErrorException inte) {
				}
			}
		} else {// caso en el cual la consulta no arrojo resultados
			return "<tr>\n<td >\nNO HAY REGISTROS DE ESTA BUSQUEDA</td>\n</tr>\n";
		}
	}

	/**
	 * Funcinn: Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		this.msg += s;
	}

	/**
	 * Funcinn: Retorna una variable que contiene los mensajes que se van a enviar a la vista
	 * 
	 * @return String
	 */
	public String getMensaje() {
		return msg;
	}

	/**
	 * Funcinn: retorna un array con las dos primeras columnas de la consulta enviada como parametro
	 * 
	 * @param String
	 *            buscar
	 * @return String[][]
	 */
	public String[] getColumna(String buscar) {
		String n[] = null;
		Connection cn = null;
		Statement st = null;
		ResultSet r = null;
		int j = 0, filas = 1;

		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (r.next()) {
				while (r.next())
					filas++;
				r.close();st.close();
				n = new String[filas];
				st = cn.createStatement();
				r = st.executeQuery(buscar);
				while (r.next())
					n[j++] = r.getString(1);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			setMensaje("Error intentado ejecutar sentencia: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return n;
	}

	/**
	 * Funcinn: Verifica si la sentencia se ejecuta<br>
	 * 
	 * @param String
	 *            consulta
	 * @param Collection
	 *            lista
	 * @return boolean
	 */
	public boolean registrar(String consulta, Collection lista) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {

			cn = getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(consulta);
			pst.clearParameters();
			Iterator iterator = lista.iterator();
			int posicion = 1;
			while (iterator.hasNext()) {
				Object[] fila = (Object[]) iterator.next();
				switch (((Integer) fila[0]).intValue()) {
				case java.sql.Types.VARCHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.INTEGER:
					pst.setLong(posicion++, Long.parseLong(((String) fila[1]).trim()));
					break;
				case java.sql.Types.DATE:
					pst.setDate(posicion++, new java.sql.Date(sdf.parse((String) fila[1]).getTime()));
					break;
				case java.sql.Types.NULL:
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
					break;
				case java.sql.Types.DOUBLE:
					pst.setLong(posicion++, (((Long) fila[1])).longValue());
					break;
				case java.sql.Types.CHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.BIGINT:
					pst.setLong(posicion++, Long.parseLong(((String) fila[1]).trim()));
					break;
				case java.sql.Types.TIMESTAMP:
					pst.setTimestamp(posicion++, new java.sql.Timestamp(sdf1.parse("" + fila[1]).getTime()));
					break;
				}
			}
			pst.executeUpdate();
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {

			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando hacer rollBack. Posible problema: ");

			switch (sqle.getErrorCode()) {
			// Violacinn de llave primaria
			case 1:
			case 2291:
				setMensaje("Ya existe un registro con el mismo cndigo");
				break;
			// Violacinn de llave secundaria
			case 2292:
				setMensaje("No se puede realizar la operacinn ya que tiene registros asociados");
				break;
			// Longitud de campo
			case 1401:
				setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
				break;

			default:
				setMensaje(sqle.toString().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha: " + e.toString());
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
	 * Funcinn: Conectar a base de datos Oracle por resource bundle<br>
	 * 
	 * @return String
	 */
	public String getUrl() {
		if (motor.equals("oracle") && host != null && puerto != null && bd != null)
			return ("jdbc:oracle:thin:@" + host + ":" + puerto + ":" + bd);
		else
			return ("jdbc:mysql://" + host + "/" + bd);
	}
}
