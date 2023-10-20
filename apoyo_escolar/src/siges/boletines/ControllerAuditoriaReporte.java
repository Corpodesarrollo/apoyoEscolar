package siges.boletines;

import java.io.File;
import java.net.InetAddress;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.boletines.Boletin_;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import siges.boletines.beans.FiltroBeanReports;

/**
 * Nombre: <BR>
 * Descripcinn: <BR>
 * Funciones de la pngina: <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerAuditoriaReporte {

	private Cursor cursor = new Cursor();

	public boolean insertarAuditoria(String tipoReporte, int codigoReporte, String comentarios, Long consecutivo) {
//		System.out.println("insertarAuditoria BBB");
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			String server = InetAddress.getLocalHost().toString();
			con = cursor.getConnection();
			pst = con.prepareStatement(
					"INSERT INTO AUDITORIA_REPORTES (TIPO_REPORTE,COD_REPORTE,CONSECUTIVO,SERVER,COMENTARIOS,FECHA) "
					+ "VALUES ('"+tipoReporte+"',"+codigoReporte+","+consecutivo+",'"+server+"','"+comentarios+"',sysdate)");
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

}
