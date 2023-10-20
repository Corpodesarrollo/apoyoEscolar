/**
 * 
 */
package participacion.acta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import participacion.acta.vo.ActaVO;
import participacion.acta.vo.FiltroActaVO;
import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.vo.ParamParticipacion;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * 30/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ActaDAO  extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c Objeto para la obtencinn de conexiones
	 */
	public ActaDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("participacion.acta.bundle.acta");
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
	 * Calcula la lista de niveles de instancia  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaNivel(int nivelUsuario) throws ParticipacionException{
		List l=new ArrayList();
		switch (nivelUsuario){
			case ParamParticipacion.LOGIN_NIVEL_DEPARTAMENTO:
				l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_DISTRITAL,ParamParticipacion.NIVEL_INSTANCIA_DISTRITAL_));
				l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_CENTRAL,ParamParticipacion.NIVEL_INSTANCIA_CENTRAL_));
				l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_LOCAL,ParamParticipacion.NIVEL_INSTANCIA_LOCAL_));
				l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_COLEGIO,ParamParticipacion.NIVEL_INSTANCIA_COLEGIO_));
			break;
			case ParamParticipacion.LOGIN_NIVEL_LOCALIDAD:
				l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_LOCAL,ParamParticipacion.NIVEL_INSTANCIA_LOCAL_));
				l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_COLEGIO,ParamParticipacion.NIVEL_INSTANCIA_COLEGIO_));
			break;
			case ParamParticipacion.LOGIN_NIVEL_COLEGIO: case ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA:
				l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_COLEGIO,ParamParticipacion.NIVEL_INSTANCIA_COLEGIO_));
			break;
		}
		return l;
	}
	
	/**
	 * Calcula la lista de instancias de un nivel  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaInstancia(int nivel) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("common.listaInstancia"));
			st.setInt(i++,nivel);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	public ActaVO actualizarActa(ActaVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("acta.actualizar"));
			i=1;
			st.setString(i++,item.getActFecha());
			st.setString(i++,item.getActHoraIni()+":"+item.getActMinIni());
			st.setString(i++,item.getActHoraFin()+":"+item.getActMinFin());
			st.setString(i++,item.getActLugar());
			st.setInt(i++,item.getActAsunto());
			st.setString(i++,item.getActAsistentes());
			st.setString(i++,item.getActAsistentesExt());
			st.setString(i++,item.getActElaborado());
			st.setString(i++,item.getActElaboradoRol());
			st.setString(i++,item.getActFechaProxima());
			st.setString(i++,item.getActDescripcion());
			st.setString(i++,item.getActDecision());
			st.setString(i++,item.getActCompromiso());
			st.setString(i++,item.getActResponsabilidad());
			st.setString(i++,item.getActDocumento());
			st.setString(i++,item.getActObservacion());
			if(item.getActParticipante()<=0) st.setNull(i++,Types.VARCHAR);
			else st.setLong(i++,item.getActParticipante());
			//w
			st.setInt(i++,item.getActInstancia());
			st.setInt(i++,item.getActRango());
			st.setInt(i++,item.getActCodigo());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			if(sqle.getErrorCode()==1){
				throw new IntegrityException("Cndigo duplicado");
			}
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
		return item;
	}

	public void eliminarActa(FiltroActaVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("acta.eliminar"));
			i=1;
			//w
			st.setInt(i++,item.getFilInstancia());
			st.setInt(i++,item.getFilRango());
			st.setInt(i++,item.getFilActa());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			if(sqle.getErrorCode()==1){
				throw new IntegrityException("Cndigo duplicado");
			}
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
	}

	public ActaVO getActa(FiltroActaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ActaVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("acta.obtener"));
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilRango());
			st.setInt(i++,filtro.getFilActa());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new ActaVO();
				item.setFormaEstado("1");
				item.setFormaDisabled("disabled");
				item.setActNivel(rs.getInt(i++));
				item.setActInstancia(rs.getInt(i++));
				item.setActRango(rs.getInt(i++));
				item.setActCodigo(rs.getInt(i++));
				item.setActLocalidad(rs.getInt(i++));
				item.setActColegio(rs.getLong(i++));
				item.setActFecha(rs.getString(i++));
				item.setActHoraIni(rs.getString(i++));
				item.setActMinIni(rs.getString(i++));
				item.setActHoraFin(rs.getString(i++));
				item.setActMinFin(rs.getString(i++));
				item.setActLugar(rs.getString(i++));
				item.setActAsunto(rs.getInt(i++));
				item.setActAsistentes(rs.getString(i++));
				item.setActAsistentesExt(rs.getString(i++));
				item.setActFecha2(rs.getString(i++));
				item.setActElaborado(rs.getString(i++));
				item.setActElaboradoRol(rs.getString(i++));
				item.setActFechaProxima(rs.getString(i++));
				item.setActDescripcion(rs.getString(i++));
				item.setActDecision(rs.getString(i++));
				item.setActCompromiso(rs.getString(i++));
				item.setActResponsabilidad(rs.getString(i++));
				item.setActDocumento(rs.getString(i++));
				item.setActObservacion(rs.getString(i++));
				item.setActParticipante(rs.getLong(i++));
				item.setActRol(rs.getInt(i++));
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
		return item;
	}

	public List getListaActa(FiltroActaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ActaVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//System.out.println(filtro.getFilLocalidad()+"//"+filtro.getFilColegio());
			String sql=rb.getString("acta.lista.1");
			if(filtro.getFilLocalidad()>0) sql+=" "+rb.getString("acta.lista.2");
			if(filtro.getFilColegio()>0) sql+=" "+rb.getString("acta.lista.3");
			sql+=" "+rb.getString("acta.lista.4");
			st=cn.prepareStatement(sql);
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilRango());
			if(filtro.getFilLocalidad()>0)st.setInt(i++,filtro.getFilLocalidad());
			if(filtro.getFilColegio()>0)st.setLong(i++,filtro.getFilColegio());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ActaVO();
				item.setActInstancia(rs.getInt(i++));
				item.setActRango(rs.getInt(i++));
				item.setActCodigo(rs.getInt(i++));
				item.setActLocalidad(rs.getInt(i++));
				item.setActColegio(rs.getLong(i++));
				item.setActNombreAsunto(rs.getString(i++));
				item.setActFecha(rs.getString(i++));
				item.setActHora1(rs.getString(i++));
				item.setActHora2(rs.getString(i++));
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

	public synchronized ActaVO ingresarActa(ActaVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//calcular el codigo
			st=cn.prepareStatement(rb.getString("acta.codigo"));
			st.setInt(i++,item.getActInstancia());
			st.setInt(i++,item.getActRango());
			rs=st.executeQuery();
			if(rs.next()){
				item.setActCodigo(rs.getInt(1));
			}
			rs.close();st.close();
			st=cn.prepareStatement(rb.getString("acta.ingresar."+item.getActNivel()));
			i=1;
			st.setInt(i++,item.getActInstancia());
			st.setInt(i++,item.getActRango());
			st.setInt(i++,item.getActCodigo());
			if(item.getActNivel()==ParamParticipacion.NIVEL_INSTANCIA_LOCAL){
				st.setInt(i++,item.getActLocalidad());
			}
			if(item.getActNivel()==ParamParticipacion.NIVEL_INSTANCIA_COLEGIO){
				st.setInt(i++,item.getActLocalidad());
				st.setLong(i++,item.getActColegio());
			}
			st.setString(i++,item.getActFecha());
			st.setString(i++,item.getActHoraIni()+":"+item.getActMinIni());
			st.setString(i++,item.getActHoraFin()+":"+item.getActMinFin());
			st.setString(i++,item.getActLugar());
			st.setInt(i++,item.getActAsunto());
			st.setString(i++,item.getActAsistentes());
			st.setString(i++,item.getActAsistentesExt());
			st.setString(i++,item.getActElaborado());
			st.setString(i++,item.getActElaboradoRol());
			st.setString(i++,item.getActFechaProxima());
			st.setString(i++,item.getActDescripcion());
			st.setString(i++,item.getActDecision());
			st.setString(i++,item.getActCompromiso());
			st.setString(i++,item.getActResponsabilidad());
			st.setString(i++,item.getActDocumento());
			st.setString(i++,item.getActObservacion());
			if(item.getActParticipante()<=0) st.setNull(i++,Types.VARCHAR);
			else st.setLong(i++,item.getActParticipante());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			if(sqle.getErrorCode()==1){
				throw new IntegrityException("Cndigo duplicado");
			}
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
		return item;
	}

	/**
	 * Calcula la lista de instancias de un nivel  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaRango(int instancia) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("common.listaRango"));
			st.setInt(i++,instancia);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	/**
	 * Calcula la lista de instancias de un nivel  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaLocalidad() throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("common.listaLocalidad"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	/**
	 * Calcula la lista de instancias de un nivel  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaColegio(int localidad) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("common.listaColegio"));
			st.setInt(i++,localidad);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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


	public List getListaRol(int instancia) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("common.listaRol"));
			st.setInt(i++,instancia);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	public List getListaParticipante(int instancia,long rango, int rol) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("common.listaParticipante"));
			st.setInt(i++,instancia);
			st.setLong(i++,rango);
			st.setInt(i++,rol);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	public List getListaAsunto() throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("common.listaAsunto"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	public List getListaHora() throws ParticipacionException{
		List l=new ArrayList();
		for(int i=0;i<=23;i++){
			l.add(new ItemVO(i,String.valueOf(i)));
		}
		return l;
	}
	
	public List getListaMinuto() throws ParticipacionException{
		List l=new ArrayList();
		for(int i=0;i<=59;i=i+5){
			l.add(new ItemVO(i,(i<10?"0"+String.valueOf(i):String.valueOf(i))));
		}
		return l;
	}
}
