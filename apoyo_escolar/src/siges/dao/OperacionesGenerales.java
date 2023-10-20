package siges.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import siges.exceptions.InternalErrorException;

/**
 * Nombre: OperacionesGenerales Descripcion: Operaciones con la BD Parametro de
 * entrada: no aplica Parametro de salida: no aplica Funciones de la pagina:
 * Ejecuta operaciones para cerrar objetos referentes a la conexion a la BD
 * Entidades afectadas: no aplica Fecha de modificacinn:Feb-05
 * 
 * @author Athenea
 * @version $v 1.3 $
 */
public final class OperacionesGenerales {

	/**
	 * constructor de la clase
	 */
	private OperacionesGenerales() {
	}

	/**
	 * It closes a <code>ResultSet</code> if not <code>null</code>.
	 * 
	 * @param ResultSet
	 *            resultSet
	 */
	public static void closeResultSet(ResultSet resultSet) throws InternalErrorException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * It closes a <code>Statement</code> if not <code>null</code>.
	 * 
	 * @param Statement
	 *            statement
	 */
	public static void closeStatement(Statement statement) throws InternalErrorException {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// throw new InternalErrorException(e);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * It closes a <code>CallableStatement</code> if not <code>null</code>.
	 * 
	 * @param Statement
	 *            statement
	 */

	public static void closeCallableStatement(CallableStatement callablestatement) throws InternalErrorException {
		if (callablestatement != null) {
			try {
				callablestatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// throw new InternalErrorException(e);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * It closes a <code>Connection</code> if not <code>null</code>.
	 * 
	 * @param Connection
	 *            connection
	 */
	public static void closeConnection(Connection connection) throws InternalErrorException {
		if (connection != null) {
			try {
				if (!connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// throw new InternalErrorException(e);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
}