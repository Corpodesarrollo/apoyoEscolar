/**
 * 
 */
package siges.gestionAdministrativa.enviarMensajes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import articulacion.apcierArtic.vo.ParamsVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.enviarMensajes.vo.FiltroEnviarVO;
import siges.gestionAdministrativa.enviarMensajes.vo.ItemTableVO;
import siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO;
import siges.gestionAdministrativa.enviarMensajes.vo.PersonalVO;



/**
 * 17/05/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class EnviarMensajesDAO  extends Dao{
	private ResourceBundle rb;
	/**
	 * Constructor
	 *  
	 * @param c Objeto para la obtencinn de conexiones
	 */
	public EnviarMensajesDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("siges.gestionAdministrativa.enviarMensajes.bundle.enviarMensajes");
	}
	
	
	/**
	 * Devuelve una conexinn activa  
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
		return cursor.getConnection();
	}
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getLocalidades() throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("repCarnes.getLocalidades"));			
			rs = st.executeQuery();			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre("("+itemVO.getCodigo()+") "+rs.getString(posicion++));
				list.add(itemVO);
			}			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getColegios(long codLoc, long tipoCol) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("repCarnes.getColegios"));
			st.setLong(posicion++,codLoc);
			st.setLong(posicion++,tipoCol);
			st.setLong(posicion++,tipoCol);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			} 
			
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getJornada(long codInst, long codSede) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("repCarnes.getJornada"));
			st.setLong(posicion++,codInst);
			st.setLong(posicion++,codSede);
			st.setLong(posicion++,codSede);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}  
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	
	
	
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public boolean insertarMensaje(FiltroEnviarVO filtro) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int posicion = 1;
		
		try{			
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("msj.insert"));
			
			st.setLong(posicion++,filtro.getCodigo());
			st.setString(posicion++,filtro.getAsunto());
			st.setString(posicion++,filtro.getFechaIni());
			st.setString(posicion++,filtro.getFechaFin());
			st.setString(posicion++,filtro.getContenido());
			st.setInt(posicion++,filtro.getEnviado());
			st.setString(posicion++,filtro.getPerfiles());
			st.setString(posicion++,filtro.getLocalidades());
			st.setString(posicion++,filtro.getColegios());
			st.setString(posicion++,filtro.getSedes());
			st.setString(posicion++,filtro.getJornadas());
			st.setString(posicion++,filtro.getUsuario());	
			
			st.executeUpdate();
			return true;
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public boolean updateMensaje(FiltroEnviarVO filtro) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int posicion = 1;
		
		try{			
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("msj.update"));
			
			st.setString(posicion++,filtro.getAsunto());
			st.setString(posicion++,filtro.getFechaIni());
			st.setString(posicion++,filtro.getFechaFin());
			st.setString(posicion++,filtro.getContenido());
			st.setInt(posicion++,filtro.getEnviado());
			st.setString(posicion++,filtro.getPerfiles());
			st.setString(posicion++,filtro.getLocalidades());
			st.setString(posicion++,filtro.getColegios());
			st.setString(posicion++,filtro.getSedes());
			st.setString(posicion++,filtro.getJornadas());
			st.setString(posicion++,filtro.getUsuario());
			
			st.setLong(posicion++,filtro.getCodigo());
			st.executeUpdate();
			return true;
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
	
	
	public boolean deleteMensaje(long codigo) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int posicion = 1;
		
		try{			
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("msj.delete"));			
			st.setLong(posicion++,codigo);
			st.executeUpdate();
			return true;
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
	
	
	public FiltroEnviarVO getMensaje(long codigo){
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int posicion = 1;		
		FiltroEnviarVO msj=null;
		try{
			cn=cursor.getConnection();	      
			st=cn.prepareStatement(rb.getString("msj.getMensajes"));
			posicion=1;		  
			st.setLong(posicion++, codigo);
			
			rs=st.executeQuery();
			if (rs.next()){						
				posicion=1;
				msj=new FiltroEnviarVO();
				msj.setCodigo(rs.getLong(posicion++));						
				msj.setAsunto(rs.getString(posicion++));
				msj.setFecha(rs.getString(posicion++));
				msj.setFechaIni(rs.getString(posicion++));
				msj.setFechaFin(rs.getString(posicion++));
				msj.setContenido(rs.getString(posicion++));					
				msj.setEnviado(rs.getInt(posicion++));						
				msj.setPerfiles(rs.getString(posicion++));
				msj.setLocalidades(rs.getString(posicion++));
				msj.setColegios(rs.getString(posicion++));
				msj.setSedes(rs.getString(posicion++));
				msj.setJornadas(rs.getString(posicion++));
				msj.setUsuario(rs.getString(posicion++));					
			}				
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return null;
		}catch(Exception sqle){
			sqle.printStackTrace();
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return msj;
	}	
	
	
	public List getListaPerfil(long codPerfil) throws Exception {
		System.out.println("getListaPerfil");
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List list = new ArrayList();
		int posicion = 1;
		try {
			
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("enviarMensajes.getListaPerfil"));
			pst.setLong(posicion++, codPerfil);
			System.out.println("codPerfil " + codPerfil);
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				ItemVO itemVO= new ItemVO();
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			} 
			System.out.println("list " + list.size() );
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			
		}
		return list;
	}
	
	
	
	/**
	 * @param codInst
	 * @param codSede
	 * @return
	 * @throws Exception
	 */
	public List getListaJornd(String codInst, String codSede  ) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = 
			" SELECT DISTINCT SEDJORCODJOR, " +
			"       ( SELECT G_CONNOMBRE " +
			"         FROM G_CONSTANTE " +
			"         WHERE G_CONTIPO = 5 " +
			"         AND G_CONCODIGO = SEDJORCODJOR" +
			"        ) " +
			" FROM  SEDE_JORNADA " +
			" WHERE SEDJORCODINST in  ("+codInst+") " +
			" AND SEDJORCODSEDE in ("+codSede+") " +
			" ORDER BY 2" ;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}  
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public MensajesVO guardarMensajes(MensajesVO mVO) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		int posicion = 1;		
		int nuevoCodigo = 1;
		try{
			cn=cursor.getConnection();	      
			st=cn.prepareStatement(rb.getString("guardarMensajes.obtenerCodigo"));
			rs = st.executeQuery();
			if (rs.next()) {
				nuevoCodigo = rs.getInt(1) + 1;
			}
			rs.close();
			st.close();
			
			mVO.setMsjcodigo(nuevoCodigo); 
			
			st=cn.prepareStatement(rb.getString("guardarMensajes"));
			posicion=1;		  
			st.setInt(posicion++, mVO.getMsjcodigo()); 
			st.setString(posicion++, mVO.getMsjasunto()); 
			st.setString(posicion++, mVO.getMsjfechaini()); 
			st.setString(posicion++, mVO.getMsjfechafin()); 
			st.setString(posicion++, mVO.getMsjcontenido()); 
			st.setInt(posicion++,    mVO.getMsjenviadopor()); 
			
			if(mVO.getMsjenviadoaperfil() != null && mVO.getMsjenviadoaperfil().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoaperfil()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			if(mVO.getMsjenviadoalocal() != null && mVO.getMsjenviadoalocal().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoalocal()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			if(mVO.getMsjenviadoacoleg()!=null && mVO.getMsjenviadoacoleg().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoacoleg()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			
			if(mVO.getMsjenviadoasede() != null  && mVO.getMsjenviadoasede().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoasede()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			
			if(mVO.getMsjenviadoajorn() != null && mVO.getMsjenviadoajorn().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoajorn()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			st.setString(posicion++, mVO.getMsjusuario());
			st.executeUpdate();
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return mVO;
	}
	
	
	/**
	 * @param mVO
	 * @return
	 * @throws Exception
	 */
	public MensajesVO updateMensajes(MensajesVO mVO) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int posicion = 1;		
		try{
			cn=cursor.getConnection();	      
			st=cn.prepareStatement(rb.getString("updateMensajes"));
			posicion=1;		  
			st.setInt(posicion++, mVO.getMsjcodigo()); 
			st.setString(posicion++, mVO.getMsjasunto()); 
			st.setString(posicion++, mVO.getMsjfechaini()); 
			st.setString(posicion++, mVO.getMsjfechafin()); 
			st.setString(posicion++, mVO.getMsjcontenido()); 
			st.setInt(posicion++, mVO.getMsjenviadopor()); 
			if(mVO.getMsjenviadoaperfil() != null && mVO.getMsjenviadoaperfil().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoaperfil()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			if(mVO.getMsjenviadoalocal() != null && mVO.getMsjenviadoalocal().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoalocal()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			if(mVO.getMsjenviadoacoleg() != null && mVO.getMsjenviadoacoleg().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoacoleg()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			
			if(mVO.getMsjenviadoasede() != null && mVO.getMsjenviadoasede().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoasede()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			
			if(mVO.getMsjenviadoajorn() != null && mVO.getMsjenviadoajorn().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoajorn()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			} 
			st.setString(posicion++,  mVO.getMsjusuario());
			st.setInt(posicion++,  MensajesVO.EMAIL_MODIFICADO);
			//where 
			st.setInt(posicion++, mVO.getMsjcodigo());
			st.executeUpdate();
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return mVO;
	}
	
	
	/**
	 * @param mVO
	 * @return
	 * @throws Exception
	 */
	public MensajesVO updateMensajesSistema(MensajesVO mVO) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int posicion = 1;		
		try{
			cn=cursor.getConnection();	      
			st=cn.prepareStatement(rb.getString("updateMensajes"));
			posicion=1;		  
			st.setInt(posicion++, mVO.getMsjcodigo()); 
			st.setString(posicion++, mVO.getMsjasunto()); 
			st.setString(posicion++, mVO.getMsjfechaini()); 
			st.setString(posicion++, mVO.getMsjfechafin()); 
			st.setString(posicion++, mVO.getMsjcontenido()); 
			st.setInt(posicion++, mVO.getMsjenviadopor()); 
			if(mVO.getMsjenviadoaperfil() != null && mVO.getMsjenviadoaperfil().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoaperfil()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			if(mVO.getMsjenviadoalocal() != null && mVO.getMsjenviadoalocal().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoalocal()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			if(mVO.getMsjenviadoacoleg() != null && mVO.getMsjenviadoacoleg().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoacoleg()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			
			if(mVO.getMsjenviadoasede() != null && mVO.getMsjenviadoasede().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoasede()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			}
			
			if(mVO.getMsjenviadoajorn() != null && mVO.getMsjenviadoajorn().trim().length() > 0){
				st.setString(posicion++, ","+mVO.getMsjenviadoajorn()+","); 
			}else{
				st.setNull(posicion++,Types.VARCHAR);
			} 
			st.setString(posicion++,  mVO.getMsjusuario());
			st.setInt(posicion++,  MensajesVO.EMAIL_MODIFICADO_X_SISTEMA);
			//where 
			st.setInt(posicion++, mVO.getMsjcodigo());
			st.executeUpdate();
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return mVO;
	}
	
	/**
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public List listaMensajes(FiltroEnviarVO filtro) throws Exception{
		
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		int posicion = 1;		
		
		try{
			cn=cursor.getConnection(); 
			st=cn.prepareStatement(rb.getString("listaMensajes"));
			posicion=1;
			
			
			st.setString(posicion++,filtro.getFilFechaDesde());
			st.setString(posicion++,filtro.getFilFechaHasta());
			
			//pefil
			st.setLong(posicion++,filtro.getFilperfil());
			st.setLong(posicion++,filtro.getFilperfil());
			//localidad
			st.setLong(posicion++,filtro.getFillocal());
			st.setLong(posicion++,filtro.getFillocal());
			//colegio
			st.setLong(posicion++,filtro.getFilinst());
			st.setLong(posicion++,filtro.getFilinst());
			
			
			st.setLong(posicion++,filtro.getFilenviadopor());
			
			
			
			rs = st.executeQuery();
			while(rs.next()) {
				posicion=1;	
				
				//SELECT                           ,                    FROM MENSAJE
				MensajesVO mVO = new MensajesVO(); 
				mVO.setMsjcodigo(rs.getInt(posicion++)); //MSJCODIGO,  
				mVO.setMsjasunto(rs.getString(posicion++)); //MSJASUNTO, 
				mVO.setMsjfecha(rs.getString(posicion++));  //MSJFECHA,
				
				mVO.setMsjfechaini(rs.getString(posicion++));//MSJFECHAINI,  
				mVO.setMsjfechafin(rs.getString(posicion++)); //MSJFECHAFIN, 
				mVO.setMsjcontenido(rs.getString(posicion++)); //MSJCONTENIDO,   
				
				mVO.setMsjenviadopor(rs.getInt(posicion++)); //MSJENVIADOPOR, 
				mVO.setMsjenviadoaperfil(rs.getString(posicion++)); //MSJENVIADOAPERFIL, 
				mVO.setMsjenviadoalocal(rs.getString(posicion++)); //MSJENVIADOALOCAL,  
				
				mVO.setMsjenviadoacoleg(rs.getString(posicion++)); //MSJENVIADOACOLEG, 
				mVO.setMsjenviadoasede(rs.getString(posicion++)); //MSJENVIADOASEDE, 
				mVO.setMsjenviadoajorn(rs.getString(posicion++)); //MSJENVIADOAJORN
				
				mVO.setMsjusuario(rs.getString(posicion++)); //MSJUSUARIO 
				list.add(mVO);
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	/**
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public List listaMensajesSistema(String asunto, String codInst) throws Exception{
		
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		int posicion = 1;		
		
		try{
			cn=cursor.getConnection(); 
			st=cn.prepareStatement(rb.getString("listaMensajesSistema"));
			posicion=1;
			st.setString(posicion++,asunto);
			st.setString(posicion++,codInst);
			st.setLong(posicion++,ParamsVO.ENV_SISTEM);
			rs = st.executeQuery();
			while(rs.next()) {
				posicion=1;	
				
				//SELECT                           ,                    FROM MENSAJE
				MensajesVO mVO = new MensajesVO(); 
				mVO.setMsjcodigo(rs.getInt(posicion++)); //MSJCODIGO,  
				mVO.setMsjasunto(rs.getString(posicion++)); //MSJASUNTO, 
				mVO.setMsjfecha(rs.getString(posicion++));  //MSJFECHA,
				
				mVO.setMsjfechaini(rs.getString(posicion++));//MSJFECHAINI,  
				mVO.setMsjfechafin(rs.getString(posicion++)); //MSJFECHAFIN, 
				mVO.setMsjcontenido(rs.getString(posicion++)); //MSJCONTENIDO,   
				
				mVO.setMsjenviadopor(rs.getInt(posicion++)); //MSJENVIADOPOR, 
				mVO.setMsjenviadoaperfil(rs.getString(posicion++)); //MSJENVIADOAPERFIL, 
				mVO.setMsjenviadoalocal(rs.getString(posicion++)); //MSJENVIADOALOCAL,  
				
				mVO.setMsjenviadoacoleg(rs.getString(posicion++)); //MSJENVIADOACOLEG, 
				mVO.setMsjenviadoasede(rs.getString(posicion++)); //MSJENVIADOASEDE, 
				mVO.setMsjenviadoajorn(rs.getString(posicion++)); //MSJENVIADOAJORN
				
				mVO.setMsjusuario(rs.getString(posicion++)); //MSJUSUARIO 
				list.add(mVO);
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	/**
	 * @param codMensaje
	 * @return
	 * @throws Exception
	 */
	public MensajesVO obtenerMensajes(int codMensaje) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		int posicion = 1;		
		MensajesVO mVO = new MensajesVO();
		try{
			cn=cursor.getConnection();	      
			st=cn.prepareStatement(rb.getString("obtenerMensajes"));
			posicion=1;
			st.setInt(posicion++,codMensaje); 
			rs = st.executeQuery();
			if (rs.next()) {
				posicion=1;	
				mVO = new MensajesVO(); 
				mVO.setMsjcodigo(rs.getInt(posicion++)); 
				mVO.setMsjasunto(rs.getString(posicion++)); 
				mVO.setMsjfecha(rs.getString(posicion++)); 
				mVO.setMsjfechaini(rs.getString(posicion++)); 
				mVO.setMsjfechafin(rs.getString(posicion++)); 
				mVO.setMsjcontenido(rs.getString(posicion++));
				mVO.setMsjenviadopor(rs.getInt(posicion++)); 
				mVO.setMsjenviadoaperfil(rs.getString(posicion++));
				String str = mVO.getMsjenviadoaperfil();
				if(str != null)
					mVO.setMsjenviadoaperfil( str.substring(1,str.length()-1));
				
				mVO.setMsjenviadoalocal(rs.getString(posicion++)); 
				str = mVO.getMsjenviadoalocal();
				if(str != null)
					mVO.setMsjenviadoalocal( str.substring(1,str.length()-1));
				
				mVO.setMsjenviadoacoleg(rs.getString(posicion++)); 
				str = mVO.getMsjenviadoacoleg();
				if(str != null)
					mVO.setMsjenviadoacoleg( str.substring(1,str.length()-1));
				
				mVO.setMsjenviadoasede(rs.getString(posicion++)); 
				str = mVO.getMsjenviadoasede();
				if(str != null)
					mVO.setMsjenviadoasede( str.substring(1,str.length()-1));
				
				mVO.setMsjenviadoajorn(rs.getString(posicion++)); 
				str = mVO.getMsjenviadoajorn();
				if(str != null)
					mVO.setMsjenviadoajorn( str.substring(1,str.length()-1));
				
				mVO.setMsjusuario(rs.getString(posicion++));
				mVO.setMsjestado(rs.getInt(posicion++));
				mVO.setFormaEstado("1");
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(st);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return mVO;
	}
	
	
	
	public MensajesVO eliminarMensajes(int codMensaje) throws Exception{
		Connection cn=null;
		PreparedStatement st=null; 
		int posicion = 1;		
		MensajesVO mVO = new MensajesVO();
		try{
			cn=cursor.getConnection();	      
			st=cn.prepareStatement(rb.getString("eliminarMensajes"));
			posicion=1;
			st.setInt(posicion++,codMensaje); 
			st.executeUpdate();
			
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{ 
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return mVO;
	}
	
	
	public MensajesVO eliminarMensajesMsg(MensajesVO mVO) throws Exception{
		Connection cn=null;
		PreparedStatement st=null; 
		int posicion = 1;		 
		try{
			cn=cursor.getConnection();	      
			st=cn.prepareStatement(rb.getString("eliminarMensajesMsg"));
			posicion=1;
			st.setString(posicion++,mVO.getMsjenviadoacoleg());
			st.setString(posicion++,mVO.getMsjasunto());
			st.executeUpdate();
			
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{ 
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return mVO;
	}
	
	
	/**
	 * @param codInst
	 * @param codSede
	 * @return
	 * @throws Exception
	 */
	public List obtenerPefiles(String codPefiles ) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = 
			" SELECT DISTINCT PERFCODIGO, " +
			"       PERFNOMBRE " +
			" FROM PERFIL " +
			" WHERE PERFCODIGO IN  ("+ codPefiles+") " +
			" ORDER BY 2"; 
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}   
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	public List obtenerLocal(String codLocal ) throws Exception{
		System.out.println("obtenerLocal ");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = 
			" SELECT G_CONCODIGO,  " +
			"        G_CONCODIGO||' - '||G_CONNOMBRE " +
			" FROM G_CONSTANTE " +
			" WHERE G_CONTIPO = 7 " +
			" AND G_CONCODIGO  IN (" + codLocal + ")" +
			" ORDER BY G_CONCODIGO"; 
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}  
			System.out.println("list " + list.size() );
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	
	public List obtenerInst(String codInst) throws Exception{ 
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemTableVO itemVO = null;
		int posicion = 1;
		String sql = 
			" SELECT   G_CONCODIGO||':'||G_CONCODIGO||' - '||G_CONNOMBRE||':'||INSCODIGO,"+ 
			" INSNOMBRE"+ 
			" FROM    INSTITUCION , G_CONSTANTE "+ 
			" WHERE G_CONTIPO =7 AND G_CONCODIGO = INSCODLOCAL AND  INSCODIGO IN (" + codInst + ") ORDER BY 1";
		try{
			cn=cursor.getConnection(); 
			st=cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemTableVO();
				itemVO.setCodigo(rs.getString(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}   
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	
	public List obtenerSede(String[] codInstSede) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemTableVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection(); 
			for(int i= 0;i < codInstSede.length;i++){
				String sql = 
					" SELECT INSCODIGO||':'||INSNOMBRE||':'||SEDCODIGO, " +
					" SEDNOMBRE  " +
					" FROM INSTITUCION, SEDE " +
					" WHERE INSCODIGO = SEDCODINS " +
					" AND INSCODIGO  IN ("+ codInstSede[i].split("\\|")[0]  +") " +
					" AND SEDCODIGO IN ("+codInstSede[i].split("\\|")[1]+")";
				
				
				st = cn.prepareStatement(sql);
				rs = st.executeQuery();
				
				while (rs.next()) {
					posicion = 1;
					itemVO = new ItemTableVO();
					itemVO.setCodigo(rs.getString(posicion++));
					itemVO.setNombre(rs.getString(posicion++));
					list.add(itemVO);
				}  
				rs.close();
				st.close();
			} 
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
 
	public List obtenerJord(String codJord ) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = 
			" SELECT G_CONCODIGO, " +
			"        G_CONNOMBRE " +
			" FROM G_CONSTANTE " +
			" WHERE G_CONTIPO = 5 " +
			" AND G_CONCODIGO in ("+ codJord+") " +
			" ORDER BY 2";
		try{
			cn=cursor.getConnection(); 
			st=cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}   
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	
	public int getNivelPerfil(long perfil ) throws Exception{ 
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		int posicion = 1;
		
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getNivelPerfil"));
			st.setLong(posicion++,perfil);
			rs = st.executeQuery();
			
			if (rs.next()) {
				posicion = 1;
				return rs.getInt(posicion++);
			}  
			System.out.println("list " + list.size() );
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return -9;
	}
	
	
	/**
	 * @return
	 */
	public Map getMailParams() {
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet r=null;
		Map params= new HashMap();
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("enviarCorreo.params"));
			r = pst.executeQuery();
			while(r.next()){
				int i=1;
				params.put(r.getString(i++),r.getString(i++));
			}
		}catch(InternalErrorException in){ setMensaje("NO se puede establecer conexinn con la base de datos: "); return null;}
		catch(SQLException sqle){
			setMensaje("Error intentando asignar Periodos. Posible problema: ");
			System.out.println("ENTRO ERROR SQL PARAMS CORREO"+sqle);
			switch(sqle.getErrorCode()){
			default:
				setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
			}
			
		}finally{
			try{
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			}catch(InternalErrorException inte){}
		}
		return params;
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	public PersonalVO asignarPersonal(String id) {
		PersonalVO p = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("PersonalAsignar222"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, (id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				p = new PersonalVO();
				int i = 1;
				p.setPercodjerar(r.getString(i++));
				p.setPertipo(r.getString(i++));
				p.setPerexpdoccoddep(r.getString(i++));
				p.setPerexpdoccodmun(r.getString(i++));
				p.setPertipdocum(r.getString(i++));
				p.setPernumdocum(r.getString(i++));
				p.setPernombre1(r.getString(i++));
				p.setPernombre2(r.getString(i++));
				p.setPerapellido1(r.getString(i++));
				p.setPerapellido2(r.getString(i++));
				p.setPerfechanac(r.getString(i++));
				p.setPerlugnaccoddep(r.getString(i++));
				p.setPerlugnaccodmun(r.getString(i++));
				p.setPeredad(r.getString(i++));
				p.setPergenero(r.getString(i++));
				p.setPeretnia(r.getString(i++));
				p.setPerdireccion(r.getString(i++));
				p.setPertelefono(r.getString(i++));
				p.setPerzona(r.getString(i++));
				p.setPerlocvereda(r.getString(i++));
				p.setPerbarcorreg(r.getString(i++));
				p.setEstado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar información de personal. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return p;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return p;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaMensaje() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = 
			" ";
		try{
			cn=cursor.getConnection(); 
			st=cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}   
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	/**
	 * @param codPefiles
	 * @param codLocals
	 * @param codInsts
	 * @param codSedes
	 * @param codJords
	 * @return
	 * @throws Exception
	 */
	public List getListaPersona(String codPefiles, String codLocals, String codInsts, String codSedes, String codJords) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = 
			" ";
		try{
			cn=cursor.getConnection(); 
			st=cn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			}   
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	
	
	
	public int isValidaExisteMsg(MensajesVO msjVO) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int posicion = 1;
		
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("isValidaExisteMsg"));
			st.setString(posicion++,msjVO.getMsjasunto());
			st.setString(posicion++,msjVO.getMsjenviadoacoleg() );
			st.setLong(posicion++,ParamsVO.ENV_SISTEM);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				posicion = 1; 
				return rs.getInt(posicion++);
			}  
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return -9;
	}
}

