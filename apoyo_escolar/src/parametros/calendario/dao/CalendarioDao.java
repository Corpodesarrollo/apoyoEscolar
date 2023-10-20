package parametros.calendario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


import parametros.calendario.vo.CalendarioVO;
import participacion.common.exception.ParticipacionException;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class CalendarioDao extends Dao{

	public CalendarioDao(Cursor cursor) {
		super(cursor);
		rb=ResourceBundle.getBundle("parametros.calendario.bundle.calendario");
	}
	
	/**
	 * Retorna todo los fechas del calendario para la vigencia y mes dado
	 * @param vigencia
	 * @return
	 * @throws ParticipacionException
	 */
	public List getListaInstancia(String month) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		CalendarioVO item = null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getCalendario"));
			st.setString(i++,getCurrentVigencia());
			if(month.toUpperCase().equals("TODOS")){
				st=cn.prepareStatement(rb.getString("getCalendariotodos"));
				st.setString(1,getCurrentVigencia());
			} else {
				st.setString(i++,month);
			}
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new CalendarioVO();
				item.setFecha(rs.getString(i++));
				item.setMotivo(rs.getString(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	public boolean nuevaFecha(CalendarioVO calendario) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		List l=new ArrayList();
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			/**
			 * Verifica que el dia festivo no este ingresado
			 */
			st=cn.prepareStatement(rb.getString("fechaExiste"));
			st.setString(i,calendario.getFecha());
			st.executeQuery();
			rs=st.executeQuery();
			if(rs.next()){
				return false;
			}
			
			st=cn.prepareStatement(rb.getString("insertFecha"));
			st.setString(i++,calendario.getFecha());
			st.setString(i++,calendario.getMotivo());
			st.setString(i++,getCurrentVigencia());
			String[] fechaSplited = calendario.getFecha().split("/");
			st.setString(i++, getMonthName(Integer.parseInt(fechaSplited[1])));
			st.execute();
			return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
	
	/**
	 * Dado el nnmero de mes, retorna el nombre del respectivo mes
	 * @param month
	 * @return
	 */
	private String getMonthName(int month) {
		if(month == 1)
			return "ENERO";
		else if(month == 2)
			return "FEBRERO";
		else if(month == 3)
			return "MARZO";
		else if(month == 4)
			return "ABRIL";
		else if(month == 5)
			return "MAYO";
		else if(month == 6)
			return "JUNIO";
		else if(month == 7)
			return "JULIO";
		else if(month == 8)
			return "AGOSTO";
		else if(month == 9)
			return "SEPTIEMBRE";
		else if(month == 10)
			return "OCTUBRE";
		else if(month == 11)
			return "NOVIEMBRE";
		else
			return "DICIEMBRE";
	}
	
	/**
	 * Retorna el ano de la vigencia actual
	 * @return
	 */
	public String getCurrentVigencia(){
		return Calendar.getInstance().get(Calendar.YEAR) + "";
	}
	
	/**
	 * Retorna el mes de una fecha dada
	 * @param fecha
	 * @return
	 */
	public String getMesFromDate(String fecha){
		return "JUNIO";
	}

	/**
	 * Actualiza el motivo de una fecha dada su "fecha"
	 * @param fecha
	 */
	public void updateFecha(String fecha, String motivo) {
		Connection cn=null;
		PreparedStatement st=null;
		List l=new ArrayList();
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("updateFecha"));
			st.setString(i++,motivo);
			st.setString(i++,fecha);
			st.execute();
		}catch(Exception sqle){
			sqle.printStackTrace();
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	/**
	 * Retorna un objeto de tipo CalendarioVO dada la fecha (PK)
	 * @param fecha
	 * @return
	 */
	public CalendarioVO buscarFecha(String fecha) {
		Connection cn=null;
		PreparedStatement st=null;
		CalendarioVO cal =new CalendarioVO();
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getFecha"));
			st.setString(i++,fecha);
			rs = st.executeQuery();
			if(rs.next()){
				cal.setEstado(2); //Editando
				cal.setFecha(rs.getString(1));
				cal.setMotivo(rs.getString(2));
			}
			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return cal;
	}




}
