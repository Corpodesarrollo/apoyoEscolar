/**
 * 
 */
package siges.gestionAdministrativa.padreFlia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.padreFlia.vo.EstudianteMarcarVO;
import siges.gestionAdministrativa.padreFlia.vo.FiltroBoletinVO;
;


/**
 * 17/05/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class PadreFliaDAO  extends Dao{
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	/**
	 * Constructor
	 *  
	 * @param c Objeto para la obtencinn de conexiones
	 */
	public PadreFliaDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("siges.gestionAdministrativa.padreFlia.bundle.padreFlia");
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
	public List getJornada(long codInst, long codSede  ) throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getJornada"));
			st.setLong(posicion++,codInst);
			//System.out.println("SEDES ---------------");
			System.out.println("codInst " + codInst);
			st.setLong(posicion++,codSede);
			st.setLong(posicion++,codSede);
			System.out.println("codSede " + codSede);
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
	public List getMetodologia(long codInst ) throws Exception{
		System.out.println("getMetodologia " );
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getMetodologia"));
			st.setLong(posicion++,codInst);
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				if (itemVO.getNombre() != null && itemVO.getNombre().length() > 30 ) {
					itemVO.setNombre(itemVO.getNombre().substring(0,30) );
				}
		 		list.add(itemVO);
			} 
			
			System.out.println("list.size() " + list.size() );
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
	public List getGrado(FiltroBoletinVO plantillaBoletionVO) throws Exception{
		System.out.println("getGrado ");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getGrado"));
			
			
			st.setLong(posicion++,plantillaBoletionVO.getPlabolinst() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolsede() );
			st.setLong(posicion++,plantillaBoletionVO.getPlaboljornd() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolmetodo() );
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			} 
			System.out.println("list " + list.size()  );
			
			
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
	 * @function:  
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public String getNombreEstudiante(long codInst,long tipoDoc, String numDOc) throws Exception{
		System.out.println("getNombreEstudiante ");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		FiltroBoletinVO plantillaBoletionVO2 = new FiltroBoletinVO();
		String nombreEst ="-1";
		int posicion = 1;
		
		
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getNombreEstudiante"));
			
			
			st.setString(posicion++,numDOc.trim());
//			System.out.println("numDOc " + numDOc);
			st.setLong(posicion++,tipoDoc);
//			System.out.println("tipoDoc " + tipoDoc);
			
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1; 
				nombreEst = rs.getString(1);
			} 
			System.out.println("				nombreEst " + 				nombreEst);			
			
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
		return nombreEst;
	}
	
	
	
	
	
	
	/**
	 * @function:  
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public List getGrupo(FiltroBoletinVO plantillaBoletionVO) throws Exception{
		System.out.println("getGrupo " );
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		
		int posicion = 1;
		ItemVO itemVO = null;
		
		List list = new ArrayList();
		
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getGrupo"));
			
			
			st.setLong(posicion++,plantillaBoletionVO.getPlabolinst() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolsede() );
			st.setLong(posicion++,plantillaBoletionVO.getPlaboljornd() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolmetodo() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolgrado() );
			
			
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1; 
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
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getSede(long codInst ) throws Exception{
		System.out.println("getSede -");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getSede"));
			st.setLong(posicion++,codInst);
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
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getTiposDoc() throws Exception{
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		List list = new ArrayList();
		int posicion=0;
		try{
			cn=cursor.getConnection();			
			st=cn.prepareStatement(rb.getString("getTiposDoc"));
			rs=st.executeQuery();			
			while(rs.next()){
				posicion=1;
				ItemVO n =new ItemVO();
				n.setCodigo(rs.getInt(posicion++));
				n.setNombre(rs.getString(posicion++));
				list.add(n);
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
		System.out.println("list " + list.size() );
		return list;
	}
	
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getDANE(FiltroBoletinVO plantillaBoletionVO) throws Exception{
		System.out.println("getDANE ");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		List list = new ArrayList();
		int posicion=0;
		String dane = "";
		try{
			cn=cursor.getConnection();		
			
			posicion=1;
			st=cn.prepareStatement(rb.getString("cargarOtroDatos"));
			st.setLong(posicion, plantillaBoletionVO.getPlabolinst());
			rs=st.executeQuery();			
			while(rs.next()){
				posicion=1;
				//	plantillaBoletionVO.setPlabolinstResolucion(rs.getString(posicion++));
				dane = rs.getString(posicion++);
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
		return dane;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getCoordinadorNombre(FiltroBoletinVO plantillaBoletionVO) throws Exception{
		System.out.println("getCoordinadorNombre ");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		List list = new ArrayList();
		int posicion=0;
		String numDocCoord = "";
		String nombCoord = "";
		try{
			cn=cursor.getConnection();		
			
			posicion=1;
			st=cn.prepareStatement(rb.getString("getCoordinadorNombre.1"));
			st.setLong(posicion++,plantillaBoletionVO.getPlabolinst() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolsede() );
			st.setLong(posicion++,plantillaBoletionVO.getPlaboljornd() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolmetodo() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolgrado() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolgrupo() );
			rs=st.executeQuery();			
			while(rs.next()){
				posicion=1;
				//	plantillaBoletionVO.setPlabolinstResolucion(rs.getString(posicion++));
				numDocCoord = rs.getString(1);
			}	
			rs.close();
			st.close();
			
			st=cn.prepareStatement(rb.getString("getCoordinadorNombre.2"));
			st.setString(posicion++,numDocCoord);
			rs=st.executeQuery();			
			if(rs.next()){
				posicion=1;
				//	plantillaBoletionVO.setPlabolinstResolucion(rs.getString(posicion++));
				nombCoord = rs.getString(1);
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
		return nombCoord;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getResolInst(long inst) throws Exception{
		System.out.println("boletin : getResolInst");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		List list = new ArrayList();
		long codigo=0;
		String resol="";
		boolean cont=true;
		try{
			cn=cursor.getConnection();			
			st=cn.prepareStatement(rb.getString("boletinGetResolInst"));
			st.setLong(1, inst);
			System.out.println("inst" + inst);
			rs=st.executeQuery();		
			if(rs.next()){
				resol=rs.getString(1);
			}
			System.out.println("resol " + resol);
		}catch(SQLException sqle){
			cont=false;
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			cont=false;
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{	
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}		
		return resol;
	}
	
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaEst(FiltroBoletinVO filtroBoletinVO) throws Exception{
		System.out.println("listaEst");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		List list = new ArrayList();
		int posicion = 1;
		EstudianteMarcarVO estudinateMarcarVO = null;
	 try{
			cn=cursor.getConnection();			
			st=cn.prepareStatement(rb.getString("listaEst" + filtroBoletinVO.getPlabolperido()));
			
		//	System.out.println("CONSULTA EST " + rb.getString("listaEst" + filtroBoletinVO.getPlabolperido()));
			posicion = 1;
			st.setLong(posicion++, filtroBoletinVO.getPlabolinst());
		//	System.out.println("filtroBoletinVO.getPlabolinst() " + filtroBoletinVO.getPlabolinst());
			st.setLong(posicion++, filtroBoletinVO.getPlabolsede());
			//System.out.println("filtroBoletinVO.getPlabolsede() "  + filtroBoletinVO.getPlabolsede());
			st.setLong(posicion++, filtroBoletinVO.getPlaboljornd());
			//System.out.println("filtroBoletinVO.getPlaboljornd() " + filtroBoletinVO.getPlaboljornd());
			st.setLong(posicion++, filtroBoletinVO.getPlabolmetodo());
			//System.out.println("filtroBoletinVO.getPlabolmetodo() " + filtroBoletinVO.getPlabolmetodo());
			st.setLong(posicion++, filtroBoletinVO.getPlabolgrado());
			//System.out.println("filtroBoletinVO.getPlabolgrado() " + filtroBoletinVO.getPlabolgrado());
			st.setLong(posicion++, filtroBoletinVO.getPlabolgrupo());
			//System.out.println("filtroBoletinVO.getPlabolgrupo() " + filtroBoletinVO.getPlabolgrupo());
			rs=st.executeQuery();		
			
			
			
			while(rs.next()){
				posicion = 1;
				estudinateMarcarVO = new EstudianteMarcarVO();
				estudinateMarcarVO.setEstcodigo(rs.getLong(posicion++) );
				estudinateMarcarVO.setEstapellido(rs.getString(posicion++) );
				estudinateMarcarVO.setEstnombre(rs.getString(posicion++) ); 
				estudinateMarcarVO.setEstconsulta(rs.getInt(posicion++) );
				list.add(estudinateMarcarVO);
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
	 * @function:  
	 * @param filtroBoletinVO
	 * @param listaEst
	 * @return
	 * @throws Exception
	 */
	public List guardarEst(FiltroBoletinVO filtroBoletinVO, List listaEst) throws Exception{
		System.out.println("guardarEst");
		Connection cn=null;
		PreparedStatement st=null; 
		List list = new ArrayList();
		int posicion = 1; 
	 try{
			cn=cursor.getConnection();			
			st=cn.prepareStatement(rb.getString("guardarEst" + filtroBoletinVO.getPlabolperido()));
			posicion=1;
			//System.out.println("ACTUA " + rb.getString("guardarEst" + filtroBoletinVO.getPlabolperido()));
			for (int i = 0; i < listaEst.size(); i++) {
	 
				posicion=1;
				EstudianteMarcarVO estudinateMarcarVO2 = (EstudianteMarcarVO)listaEst.get(i);
				st.setLong(posicion++, estudinateMarcarVO2.getEstconsulta());
				System.out.println("estudinateMarcarVO2.getEstconsulta() " + estudinateMarcarVO2.getEstconsulta());
				st.setLong(posicion++, estudinateMarcarVO2.getEstcodigo());
				System.out.println("estudinateMarcarVO2.getEstcodigo() " + estudinateMarcarVO2.getEstcodigo());
				st.addBatch();
			} 
			
			
			st.executeBatch();
			 
		}catch(SQLException sqle){
		    System.out.println("sqle " + sqle.getMessage()  );
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			System.out.println("sqle " + sqle.getMessage()  );
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{	 
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}		
		return list;
	}
	
	
	
  
		
}
