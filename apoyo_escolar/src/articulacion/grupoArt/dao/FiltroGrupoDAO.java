package articulacion.grupoArt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asignatura.vo.ListaAreaVO;
import articulacion.asignatura.vo.ListaEspecialidadVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.grupoArt.vo.JornadaVO;
import articulacion.grupoArt.vo.SedeVO;

public class FiltroGrupoDAO extends Dao {
	
    private ResourceBundle rb;
    
    public FiltroGrupoDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("articulacion.grupoArt.bundle.sentencias");
	}
    
    public List getJornada(String inst, String sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		JornadaVO jornadaVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getJornada"));
			i=1;
			st.setInt(i++,Integer.parseInt(inst));
			st.setInt(i++,Integer.parseInt(sede));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				jornadaVO=new JornadaVO();
				jornadaVO.setSede(rs.getInt(i++));
				jornadaVO.setCodigo(rs.getInt(i++));
				jornadaVO.setNombre(rs.getString(i++));
				l.add(jornadaVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
    public List getSedes(String inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		SedeVO sedeVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getSede"));
			i=1;
			st.setInt(i++,Integer.parseInt(inst));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				sedeVO=new SedeVO();
				sedeVO.setCodigo(rs.getInt(i++));
				sedeVO.setNombre(rs.getString(i++));
				l.add(sedeVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
   
}
