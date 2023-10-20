/**
 * 
 */
package siges.gestionAdministrativa.boletin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.boletin.vo.ParamsVO;
import siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO;
import siges.gestionAdministrativa.boletin.vo.TipoEvalVO;
import siges.plantilla.beans.NivelEvalVO;


/**
 * 17/05/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class BoletinDAO  extends Dao{
	private ResourceBundle rb;
	/**
	 * Constructor
	 *  
	 * @param c Objeto para la obtencinn de conexiones
	 */
	public BoletinDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("siges.gestionAdministrativa.boletin.bundle.boletin");
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
				if (itemVO.getNombre().length() > 30 ) {
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
	 * Trae las asignaturas de un colegio dado el codigo del instituto, la metodologna y el ano
	 * @param plantillaBoletionVO PlantillaBoletinVO
	 * @return Lista de todas las asignaturas
	 * @throws Exception
	 */
	public List getAsignaturas(PlantillaBoletionVO plantillaBoletionVO) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getAsignaturas"));
			
			st.setLong(posicion++,plantillaBoletionVO.getPlabolinst() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolmetodo() );
			st.setLong(posicion++, Calendar.getInstance().get(Calendar.YEAR));
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
	public List getGrado(PlantillaBoletionVO plantillaBoletionVO) throws Exception{
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
	 * Retorna todos los grupos de una institucinn
	 * @param plantillaBoletionVO
	 * @return
	 * @throws Exception
	 */
	public List getGrupoInst(long inst, long sede, long jornd) throws Exception{
		System.out.println("getGrupo " );
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		
		int posicion = 1;
		ItemVO itemVO = null;
		
		List list = new ArrayList();
		
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("plantillaBoletin.getGrupoInst"));
			
			st.setLong(posicion++, inst);
			st.setLong(posicion++, sede);
			st.setLong(posicion++, jornd);
			
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
	 * @function:  
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public List getGrupo(PlantillaBoletionVO plantillaBoletionVO) throws Exception{
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
	public String getDANE(PlantillaBoletionVO plantillaBoletionVO) throws Exception{
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
	public String getCoordinadorNombre(PlantillaBoletionVO plantillaBoletionVO) throws Exception{
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
	public String getListaMEN(long inst) throws Exception{
		System.out.println("getListaMEN");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null; 
		List list = new ArrayList();
		long codigo=0;
		String resol="";
		boolean cont=true;
		try{
			cn=cursor.getConnection();			
			st=cn.prepareStatement(rb.getString("getListaMEN"));
			rs=st.executeQuery();		
			while(rs.next()){
				resol +="  " +rs.getString(1) ;
			}	
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
	 * @function:  Retorna la escala segun el tipo de evaluacion de institucion
	 * @param n
	 * @param login
	 * @return
	 */
	public String getEscalaNivelEval(PlantillaBoletionVO plantillaBoletionVO  ) throws Exception {
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = ""; 
		String escala = "";
		try {
			//1. Obtener el nivel eval y el tipo eval (1.NUMERICO, 2.CONCEPTUAL, 3.PORCENTUAL) 
			TipoEvalVO tipoEvalVO = getTipoEval( plantillaBoletionVO); 
			
			//2. Si es conceptual obtener escalas, sino retorna el max y min
//			System.out.println("tipoEvalVO.getCod_tipo_eval()  " + tipoEvalVO.getCod_tipo_eval() );
			if(tipoEvalVO.getCod_tipo_eval() == ParamsVO.ESCALA_CONCEPTUAL ){ 
				 return  getEscalaConceptual( plantillaBoletionVO);
			}else{
			   escala = "Mnnimo " + tipoEvalVO.getEval_min() +"  - Mnximo "+tipoEvalVO.getEval_max();
			}
			
		}catch (Exception sqle) {
			 
			setMensaje("Error intentando obtener escala de AREA/ASIG. Posible problema: ");
		   System.out.println("sqle.getMessage() " + sqle.getMessage());
			throw new Exception(sqle.getMessage());
			
		}
		finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return escala;
	}
	
	

//	CAMBIOS EVLUACION PARAMETRSO INSTITICION
		 
		public TipoEvalVO getTipoEval( PlantillaBoletionVO plantillaBoletionVO ) throws Exception{
			Connection cn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			int i=1;
			long vigencia=0;
			long codigoNivelEval=0;
			int tipoEval=0;
			NivelEvalVO nivelEval=new NivelEvalVO();
			TipoEvalVO tipoEvalVO=new TipoEvalVO();
			try{
				cn=cursor.getConnection();
				vigencia=getVigenciaInst(plantillaBoletionVO.getPlabolinst());
				st=cn.prepareStatement(rb.getString("getNivelEval"));
				st.setLong(i++,vigencia);
				st.setLong(i++,plantillaBoletionVO.getPlabolinst() );
				rs=st.executeQuery();
				if(rs.next()){
					codigoNivelEval = rs.getLong(1);
				}
				rs.close();
				st.close();
				i=1;
				//traer parametros de nivel evaluacion
				st=cn.prepareStatement(rb.getString("getParamsNivelEval"));
				st.setLong(i++,codigoNivelEval);
				rs=st.executeQuery();
				i=1;
				if(rs.next()){
					nivelEval.setCod_nivel_eval(rs.getLong(i++));
					nivelEval.setEval_sede(rs.getInt(i++));
					nivelEval.setEval_jornada(rs.getInt(i++));
					nivelEval.setEval_metod(rs.getInt(i++));
					nivelEval.setEval_nivel(rs.getInt(i++));
					nivelEval.setEval_grado(rs.getInt(i++));			
				}
				rs.close();
				st.close();
				
				//TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
				String sql=rb.getString("getTipoEval.select");
				if(nivelEval.getEval_sede()==1)
					sql=sql+" "+rb.getString("getTipoEval.sede");
				if(nivelEval.getEval_jornada()==1)
					sql=sql+" "+rb.getString("getTipoEval.jornada");
				if(nivelEval.getEval_metod()==1)
					sql=sql+" "+rb.getString("getTipoEval.metod");
				if(nivelEval.getEval_nivel()==1)
					sql=sql+rb.getString("getTipoEval.nivel");
				if(nivelEval.getEval_grado()==1)
					sql=sql+" "+rb.getString("getTipoEval.grado");
				
			 System.out.println("CONSULTA  " + sql);
				st=cn.prepareStatement(sql);
				i=1;
			 	st.setLong(i++,getVigenciaInst( plantillaBoletionVO.getPlabolinst()));
				st.setLong(i++,plantillaBoletionVO.getPlabolinst() );
				
				if(nivelEval.getEval_sede()==1)
					st.setLong(i++, plantillaBoletionVO.getPlabolsede() );
				if(nivelEval.getEval_jornada()==1)
					st.setLong(i++,plantillaBoletionVO.getPlaboljornd()  );
				if(nivelEval.getEval_metod()==1)
					st.setLong(i++,plantillaBoletionVO.getPlabolmetodo()  );
				if(nivelEval.getEval_nivel()==1){
					int nivelGrado=getNivelGrado((int)plantillaBoletionVO.getPlabolgrado()  );
					st.setInt(i++,nivelGrado);
				} 
				if(nivelEval.getEval_grado()==1){
					st.setLong(i++,plantillaBoletionVO.getPlabolgrado() );
				}
				
				
				rs=st.executeQuery();
				i=1;
				if(rs.next()){
					i=1;
					tipoEvalVO.setCod_tipo_eval(rs.getLong(i++));
					 tipoEvalVO.setEval_min(rs.getDouble(i++));
					tipoEvalVO.setEval_max(rs.getDouble(i++));
					 tipoEvalVO.setEval_rangos_completos(rs.getInt(i++));
					tipoEvalVO.setCod_modo_eval(rs.getInt(i++));
					tipoEvalVO.setCod_tipo_eval_pree(rs.getLong(i++));
				}
				rs.close();
				st.close();
				
				
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
			return tipoEvalVO;
		}
		

		public int getNivelGrado(int grado) throws Exception{
			Connection cn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			int i=1;
			try{
				cn=cursor.getConnection();
				st=cn.prepareStatement(rb.getString("getNivelGrado"));
				st.setInt(i++,grado);
				rs=st.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
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
			return 0;
		}
		
		

		public String getEscalaConceptual(PlantillaBoletionVO plantillaBoletionVO ) throws Exception{
			Connection cn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			int i=1;
			long vigencia=0;
			long codigoNivelEval=0;
			int tipoEval=0;
			NivelEvalVO nivelEval=new NivelEvalVO();
			List l=new ArrayList();
			ItemVO item=null;
			Collection list = null;
			String escalaConc = "";
			try{
				cn=cursor.getConnection();
				vigencia=getVigenciaInst(plantillaBoletionVO.getPlabolinst());
				st=cn.prepareStatement(rb.getString("getNivelEval"));
				st.setLong(i++, vigencia);
				st.setLong(i++,plantillaBoletionVO.getPlabolinst() );
				rs=st.executeQuery();
				if(rs.next()){
					codigoNivelEval = rs.getLong(1);
				}
				rs.close();
				st.close();
				i=1;
				//traer parametros de nivel evaluacion
				st=cn.prepareStatement(rb.getString("getParamsNivelEval"));
				st.setLong(i++,codigoNivelEval);
				rs=st.executeQuery();
				i=1;
				if(rs.next()){
					nivelEval.setCod_nivel_eval(rs.getLong(i++));
					nivelEval.setEval_sede(rs.getInt(i++));
					nivelEval.setEval_jornada(rs.getInt(i++));
					nivelEval.setEval_metod(rs.getInt(i++));
					nivelEval.setEval_nivel(rs.getInt(i++));
					nivelEval.setEval_grado(rs.getInt(i++));			
				}
				rs.close();
				st.close();
				
				//TRAER TIPO DE EVALUACION SEGUN NIVEL DE EVAL Y PARAMETROS
				String sql=rb.getString("getEscalaConceptual.select");
				if(nivelEval.getEval_sede()==1)
					sql=sql+" "+rb.getString("getEscalaConceptual.sede");
				if(nivelEval.getEval_jornada()==1)
					sql=sql+" "+rb.getString("getEscalaConceptual.jornada");
				if(nivelEval.getEval_metod()==1)
					sql=sql+" "+rb.getString("getEscalaConceptual.metod");
				if(nivelEval.getEval_nivel()==1)
					//sql=sql+rb.getString("getTipoEval.nivel");
					sql=sql+rb.getString("getEscalaConceptual.nivel");
					
				if(nivelEval.getEval_grado()==1)
					sql=sql+" "+rb.getString("getEscalaConceptual.grado");
				
				System.out.println("CONCEPTUALO sql" + sql);
				st=cn.prepareStatement(sql);
				i=1;
				st.setLong(i++, getVigenciaInst(plantillaBoletionVO.getPlabolinst()));
				st.setLong(i++,plantillaBoletionVO.getPlabolinst() );
				st.setLong(i++,codigoNivelEval);
				
				if(nivelEval.getEval_sede()==1)
					st.setLong(i++,plantillaBoletionVO.getPlabolsede());
				if(nivelEval.getEval_jornada()==1)
					st.setLong(i++,plantillaBoletionVO.getPlaboljornd());
				if(nivelEval.getEval_metod()==1)
					st.setLong(i++,plantillaBoletionVO.getPlabolmetodo());
				if(nivelEval.getEval_nivel()==1){
					int nivelGrado=getNivelGrado((int)plantillaBoletionVO.getPlabolgrado());
					st.setInt(i++,nivelGrado);
				}
				if(nivelEval.getEval_grado()==1)
					st.setLong(i++,plantillaBoletionVO.getPlabolgrado());
				
				
				rs=st.executeQuery(); 
				i=1;
				while(rs.next()){
				System.out.println("escalaConc " + escalaConc);
					escalaConc += rs.getString(1);
				}
				rs.close();
				st.close();
				
				
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
			return escalaConc;
		}
		
		
		/**
	  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
	  	*	@param	String us
	  	*	@param	String rec
	  	*	@param	String tipo
	  	*	@param	String nombre
	  	*	@param	String prepared
	  	**/
	  	public void ponerReporte(String modulo,String us,String rec,String tipo,String nombre,String estado){	
	  		Connection con=null;
	  		PreparedStatement pst=null;
	  		int posicion=1;
	    		
	  		try{ 
	  		  con=cursor.getConnection(); 
	  		  
	  			pst=con.prepareStatement(rb.getString("ReporteInsertarEstado"));
	  			posicion=1;
	  			pst.clearParameters();
	  			pst.setString(posicion++,(us));
	  			pst.setString(posicion++,(rec));
	  			pst.setString(posicion++,(tipo));
	  			pst.setString(posicion++,(nombre));
	  			pst.setString(posicion++,(modulo));
	  			pst.setString(posicion++,(estado));		
	  			pst.executeUpdate();
	  			pst.close();
	  					
	  	  }	
	  		catch(InternalErrorException e ){
	  		    e.printStackTrace();
	  			}
	  		catch(Exception e ){
	  		    e.printStackTrace();
	  			}
	  		finally{
	  			try{
	  			    OperacionesGenerales.closeStatement(pst);
	  			    OperacionesGenerales.closeConnection(con);
	  			    }catch(Exception e){}
	  	  }
	  }

		
}
