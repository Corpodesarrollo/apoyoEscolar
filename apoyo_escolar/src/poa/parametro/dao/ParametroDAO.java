/**
 * 
 */
package poa.parametro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.parametro.vo.AreaGestionVO;
import poa.parametro.vo.FuenteFinanciacionVO;
import poa.parametro.vo.LineaAccionVO;
import poa.parametro.vo.ProgramacionFechasVO;
import poa.parametro.vo.TipoMetaVO;
import poa.parametro.vo.UnidadMedidaVO;

import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * 14/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParametroDAO extends Dao{
	
	/**
	 * Constructor
	 *  
	 * @param c Objeto para la obtencinn de conexiones
	 */
	public ParametroDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("poa.parametro.bundle.parametro");
	}

	/**
	 * Calcula la lista de areas de gestinn  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		AreaGestionVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("areaGestion.lista"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new AreaGestionVO();
				item.setAreCodigo(rs.getInt(i++));
				item.setAreNombre(rs.getString(i++));
				item.setArePonderador(rs.getFloat(i++));
				l.add(item);
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
		return l;
	}

	public List getListaFuenteFinanciacion() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		FuenteFinanciacionVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("fuenteFinanciacion.lista"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new FuenteFinanciacionVO();
				item.setFueCodigo(rs.getInt(i++));
				item.setFueNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaTipoMeta() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		TipoMetaVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("tipoMeta.lista"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new TipoMetaVO();
				item.setTipCodigo(rs.getInt(i++));
				item.setTipNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaUnidadMedida() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		UnidadMedidaVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("unidadMedida.lista"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new UnidadMedidaVO();
				item.setUniCodigo(rs.getInt(i++));
				item.setUniNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public List getListaLineaAccion(int areaGestion) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		LineaAccionVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("lineaAccion.lista"));
			st.setInt(i++,areaGestion);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new LineaAccionVO();
				item.setLinAreaGestion(rs.getInt(i++));
				item.setLinCodigo(rs.getInt(i++));
				item.setLinNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public AreaGestionVO getAreaGestion(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		AreaGestionVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("areaGestion.obtener"));
			i=1;
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new AreaGestionVO();
				item.setFormaEstado("1");
				item.setFormaDisabled(Params.DISABLED);
				item.setAreCodigo(rs.getInt(i++));
				item.setAreNombre(rs.getString(i++));
				item.setArePonderador(rs.getFloat(i++));
				item.setAreDescripcion(rs.getString(i++));
				item.setAreOrden(rs.getInt(i++));
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
		return item;
	}

	public FuenteFinanciacionVO getFuenteFinanciacion(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		FuenteFinanciacionVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("fuenteFinanciacion.obtener"));
			i=1;
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new FuenteFinanciacionVO();
				item.setFormaEstado("1");
				item.setFormaDisabled(Params.DISABLED);
				item.setFueCodigo(rs.getInt(i++));
				item.setFueNombre(rs.getString(i++));
				item.setFuePresupuesto(rs.getInt(i++)==1?true:false);
				item.setFueDescripcion(rs.getString(i++));
				item.setFueOrden(rs.getInt(i++));
				item.setFueOculta(rs.getInt(i++)==1?true:false);
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
		return item;
	}

	public TipoMetaVO getTipoMeta(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		TipoMetaVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("tipoMeta.obtener"));
			i=1;
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new TipoMetaVO();
				item.setFormaEstado("1");
				item.setFormaDisabled(Params.DISABLED);
				item.setTipCodigo(rs.getInt(i++));
				item.setTipNombre(rs.getString(i++));
				item.setTipDescripcion(rs.getString(i++));
				item.setTipOrden(rs.getInt(i++));
				item.setTipPorcentaje(rs.getInt(i++)==1?true:false);
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
		return item;
	}

	public UnidadMedidaVO getUnidadMedida(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		UnidadMedidaVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("unidadMedida.obtener"));
			i=1;
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new UnidadMedidaVO();
				item.setFormaEstado("1");
				item.setFormaDisabled(Params.DISABLED);
				item.setUniCodigo(rs.getInt(i++));
				item.setUniNombre(rs.getString(i++));
				item.setUniDescripcion(rs.getString(i++));
				item.setUniOrden(rs.getInt(i++));
				item.setUniCual(rs.getInt(i++)==1?true:false);
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
		return item;
	}

	public LineaAccionVO getLineaAccion(int areaGestion,int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		LineaAccionVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("lineaAccion.obtener"));
			i=1;
			st.setInt(i++,areaGestion);
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new LineaAccionVO();
				item.setFormaEstado("1");
				item.setFormaDisabled(Params.DISABLED);
				item.setLinAreaGestion(rs.getInt(i++));
				item.setLinCodigo(rs.getInt(i++));
				item.setLinNombre(rs.getString(i++));
				item.setLinDescripcion(rs.getString(i++));
				item.setLinOrden(rs.getInt(i++));
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
		return item;
	}

	public AreaGestionVO ingresarAreaGestion(AreaGestionVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar el codigo
			st=cn.prepareStatement(rb.getString("areaGestion.validarCodigo"));
			i=1;
			st.setInt(i++,item.getAreCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("El cndigo ya existe");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("areaGestion.insertar"));
			i=1;
			st.setInt(i++,item.getAreCodigo());
			st.setString(i++,item.getAreNombre());
			st.setFloat(i++,item.getArePonderador());
			st.setString(i++,item.getAreDescripcion());
			st.setInt(i++,item.getAreOrden());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public FuenteFinanciacionVO ingresarFuenteFinanciacion(FuenteFinanciacionVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar el codigo
			st=cn.prepareStatement(rb.getString("fuenteFinanciacion.validarCodigo"));
			i=1;
			st.setInt(i++,item.getFueCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("El cndigo ya existe");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("fuenteFinanciacion.insertar"));
			i=1;
			st.setInt(i++,item.getFueCodigo());
			st.setString(i++,item.getFueNombre());
			st.setInt(i++,item.isFuePresupuesto()?1:0);
			st.setString(i++,item.getFueDescripcion());
			st.setInt(i++,item.getFueOrden());
			st.setInt(i++,item.isFueOculta()?1:0);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public TipoMetaVO ingresarTipoMeta(TipoMetaVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar el codigo
			st=cn.prepareStatement(rb.getString("tipoMeta.validarCodigo"));
			i=1;
			st.setInt(i++,item.getTipCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("El cndigo ya existe");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("tipoMeta.insertar"));
			i=1;
			st.setInt(i++,item.getTipCodigo());
			st.setString(i++,item.getTipNombre());
			st.setString(i++,item.getTipDescripcion());
			st.setInt(i++,item.getTipOrden());
			st.setInt(i++,item.isTipPorcentaje()?1:0);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public UnidadMedidaVO ingresarUnidadMedida(UnidadMedidaVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar el codigo
			st=cn.prepareStatement(rb.getString("unidadMedida.validarCodigo"));
			i=1;
			st.setInt(i++,item.getUniCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("El cndigo ya existe");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("unidadMedida.insertar"));
			i=1;
			st.setInt(i++,item.getUniCodigo());
			st.setString(i++,item.getUniNombre());
			st.setString(i++,item.getUniDescripcion());
			st.setInt(i++,item.getUniOrden());
			st.setInt(i++,item.isUniCual()?1:0);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public LineaAccionVO ingresarLineaAccion(LineaAccionVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar el codigo
			st=cn.prepareStatement(rb.getString("lineaAccion.validarCodigo"));
			i=1;
			st.setInt(i++,item.getLinAreaGestion());
			st.setInt(i++,item.getLinCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("El cndigo ya existe");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("lineaAccion.insertar"));
			i=1;
			st.setInt(i++,item.getLinAreaGestion());
			st.setInt(i++,item.getLinCodigo());
			st.setString(i++,item.getLinNombre());
			st.setString(i++,item.getLinDescripcion());
			st.setInt(i++,item.getLinOrden());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public AreaGestionVO actualizarAreaGestion(AreaGestionVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//
			st=cn.prepareStatement(rb.getString("areaGestion.actualizar"));
			i=1;
			st.setString(i++,item.getAreNombre());
			st.setFloat(i++,item.getArePonderador());
			st.setString(i++,item.getAreDescripcion());
			st.setInt(i++,item.getAreOrden());
			st.setInt(i++,item.getAreCodigo());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public FuenteFinanciacionVO actualizarFuenteFinanciacion(FuenteFinanciacionVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//
			st=cn.prepareStatement(rb.getString("fuenteFinanciacion.actualizar"));
			i=1;
			st.setString(i++,item.getFueNombre());
			st.setInt(i++,item.isFuePresupuesto()?1:0);
			st.setString(i++,item.getFueDescripcion());
			st.setInt(i++,item.getFueOrden());
			st.setInt(i++,item.isFueOculta()?1:0);
			st.setInt(i++,item.getFueCodigo());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public TipoMetaVO actualizarTipoMeta(TipoMetaVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//
			st=cn.prepareStatement(rb.getString("tipoMeta.actualizar"));
			i=1;
			st.setString(i++,item.getTipNombre());
			st.setString(i++,item.getTipDescripcion());
			st.setInt(i++,item.getTipOrden());
			st.setInt(i++,item.isTipPorcentaje()?1:0);
			st.setInt(i++,item.getTipCodigo());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public UnidadMedidaVO actualizarUnidadMedida(UnidadMedidaVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//
			st=cn.prepareStatement(rb.getString("unidadMedida.actualizar"));
			i=1;
			st.setString(i++,item.getUniNombre());
			st.setString(i++,item.getUniDescripcion());
			st.setInt(i++,item.getUniOrden());
			st.setInt(i++,item.isUniCual()?1:0);
			st.setInt(i++,item.getUniCodigo());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public LineaAccionVO actualizarLineaAccion(LineaAccionVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//
			st=cn.prepareStatement(rb.getString("lineaAccion.actualizar"));
			i=1;
			st.setString(i++,item.getLinNombre());
			st.setString(i++,item.getLinDescripcion());
			st.setInt(i++,item.getLinOrden());
			st.setInt(i++,item.getLinAreaGestion());
			st.setInt(i++,item.getLinCodigo());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public void eliminarAreaGestion(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar eliminar
			st=cn.prepareStatement(rb.getString("areaGestion.validarEliminar"));
			i=1;
			st.setInt(i++,codigo);
			st.setInt(i++,codigo);
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Tiene información asociada en lnneas de accinn");
			}
			rs.close();
			st.close();
			//eliminar
			st=cn.prepareStatement(rb.getString("areaGestion.eliminar"));
			i=1;
			st.setInt(i++,codigo);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	public void eliminarFuenteFinanciacion(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar eliminar
			st=cn.prepareStatement(rb.getString("fuenteFinanciacion.validarEliminar"));
			i=1;
			st.setInt(i++,codigo);
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Tiene información asociada en planeacinn de actividades");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("fuenteFinanciacion.eliminar"));
			i=1;
			st.setInt(i++,codigo);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	public void eliminarTipoMeta(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar eliminar
			st=cn.prepareStatement(rb.getString("tipoMeta.validarEliminar"));
			i=1;
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Tiene información asociada en planeacinn de actividades");
			}
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("tipoMeta.eliminar"));
			i=1;
			st.setInt(i++,codigo);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
	
	public void eliminarUnidadMedida(int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar eliminar
			st=cn.prepareStatement(rb.getString("unidadMedida.validarEliminar"));
			i=1;
			st.setInt(i++,codigo);
			st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Tiene información asociada en planeacinn de actividades");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("unidadMedida.eliminar"));
			i=1;
			st.setInt(i++,codigo);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	public void eliminarLineaAccion(int areaGestion, int codigo) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar eliminar
			st=cn.prepareStatement(rb.getString("lineaAccion.validarEliminar"));
			i=1;
			st.setInt(i++,areaGestion); st.setInt(i++,codigo);
			st.setInt(i++,areaGestion); st.setInt(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Tiene información asociada en planeacinn de actividades");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("lineaAccion.eliminar"));
			i=1;
			st.setInt(i++,areaGestion);
			st.setInt(i++,codigo);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	public List getListaProgramacionFechas() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ProgramacionFechasVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("programacionFechas.lista"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ProgramacionFechasVO();
				item.setProgVigencia(rs.getInt(i++));
				item.setProgPlaneacionIni(rs.getString(i++));
				item.setProgPlaneacionFin(rs.getString(i++));
				item.setProgSeguimiento1Ini(rs.getString(i++));
				item.setProgSeguimiento1Fin(rs.getString(i++));
				item.setProgSeguimiento2Ini(rs.getString(i++));
				item.setProgSeguimiento2Fin(rs.getString(i++));
				item.setProgSeguimiento3Ini(rs.getString(i++));
				item.setProgSeguimiento3Fin(rs.getString(i++));
				item.setProgSeguimiento4Ini(rs.getString(i++));
				item.setProgSeguimiento4Fin(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	public ProgramacionFechasVO getProgramacionFechas(int vig) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ProgramacionFechasVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("programacionFechas.obtener"));
			i=1;
			st.setInt(i++,vig);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new ProgramacionFechasVO();
				item.setFormaEstado("1");
				item.setFormaDisabled(Params.DISABLED);
				item.setProgVigencia(rs.getInt(i++));
				item.setProgPlaneacionIni(rs.getString(i++));
				item.setProgPlaneacionFin(rs.getString(i++));
				item.setProgSeguimiento1Ini(rs.getString(i++));
				item.setProgSeguimiento1Fin(rs.getString(i++));
				item.setProgSeguimiento2Ini(rs.getString(i++));
				item.setProgSeguimiento2Fin(rs.getString(i++));
				item.setProgSeguimiento3Ini(rs.getString(i++));
				item.setProgSeguimiento3Fin(rs.getString(i++));
				item.setProgSeguimiento4Ini(rs.getString(i++));
				item.setProgSeguimiento4Fin(rs.getString(i++));
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
		return item;
	}

	public ProgramacionFechasVO ingresarProgramacionFechas(ProgramacionFechasVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//validar el codigo
			st=cn.prepareStatement(rb.getString("programacionFechas.validarInsertar"));
			i=1;
			st.setInt(i++,item.getProgVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Las fechas para esa vigencia ya existen");
			}
			rs.close();
			st.close();
			//
			st=cn.prepareStatement(rb.getString("programacionFechas.insertar"));
			i=1;
			st.setInt(i++,item.getProgVigencia());
			st.setString(i++,item.getProgPlaneacionIni());
			st.setString(i++,item.getProgPlaneacionFin());
			st.setString(i++,item.getProgSeguimiento1Ini());
			st.setString(i++,item.getProgSeguimiento1Fin());
			st.setString(i++,item.getProgSeguimiento2Ini());
			st.setString(i++,item.getProgSeguimiento2Fin());
			st.setString(i++,item.getProgSeguimiento3Ini());
			st.setString(i++,item.getProgSeguimiento3Fin());
			st.setString(i++,item.getProgSeguimiento4Ini());
			st.setString(i++,item.getProgSeguimiento4Fin());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}


	public ProgramacionFechasVO actualizarProgramacionFechas(ProgramacionFechasVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//
			st=cn.prepareStatement(rb.getString("programacionFechas.actualizar"));
			i=1;
			st.setString(i++,item.getProgPlaneacionIni());
			st.setString(i++,item.getProgPlaneacionFin());
			st.setString(i++,item.getProgSeguimiento1Ini());
			st.setString(i++,item.getProgSeguimiento1Fin());
			st.setString(i++,item.getProgSeguimiento2Ini());
			st.setString(i++,item.getProgSeguimiento2Fin());
			st.setString(i++,item.getProgSeguimiento3Ini());
			st.setString(i++,item.getProgSeguimiento3Fin());
			st.setString(i++,item.getProgSeguimiento4Ini());
			st.setString(i++,item.getProgSeguimiento4Fin());
			//w
			st.setInt(i++,item.getProgVigencia());
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public void eliminarProgramacionFechas(int vig) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			st=cn.prepareStatement(rb.getString("programacionFechas.eliminar"));
			i=1;
			st.setInt(i++,vig);
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
	
	public void guardarVigenciaPOA(int vig) throws Exception{
		Connection cn=null;
		PreparedStatement st=null; 
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//
			st=cn.prepareStatement(rb.getString("vigenciaPoa.guardarVigencia"));
			i=1;
			st.setInt(i++,vig);
	 
			st.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{ 
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		} 
	}
	
	public int getVigenciaPOA() throws Exception{
		Connection cn=null;
		PreparedStatement st=null; 
		ResultSet rs = null;
		try{
			cn=cursor.getConnection(); 
			//
			st=cn.prepareStatement(rb.getString("vigenciaPoa.getVigencia"));
			 
			rs  = st.executeQuery();
		 
			if (rs.next() ) {
				return rs.getInt(1);
			}else{
				throw new Exception("No existe vigencia para POA " );
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		} 
	 
	}
	

	public List listaVigencia( ) throws Exception{
		Connection cn=null;
		PreparedStatement st=null; 
		ResultSet rs = null;
		List listaVigencia = new ArrayList();
		ItemVO itemVO = null;
		try{
			cn=cursor.getConnection(); 
			//
			st=cn.prepareStatement(rb.getString("vigenciaPoa.listaVigencia"));
			 rs  = st.executeQuery();
		 
			if (rs.next() ) {
				itemVO=new ItemVO(rs.getInt(1),String.valueOf(rs.getInt(1)));
				listaVigencia.add(itemVO);
				itemVO=new ItemVO(rs.getInt(2),String.valueOf(rs.getInt(2)));
				listaVigencia.add(itemVO);
				itemVO=new ItemVO(rs.getInt(3),String.valueOf(rs.getInt(3)));
				listaVigencia.add(itemVO);
				
			}
	 
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{ 
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		} 
	 return listaVigencia;
	}

	public List getListaVigenciaPOA(){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		int i=0;
		int vigenciaIni=0;
		int vigenciaFin=0;
		List listaVigencia = new ArrayList();
		try{
			if(listaVigencia.size()>0)return listaVigencia; 
			cn=cursor.getConnection();
			pst=cn.prepareStatement("SELECT TO_NUMBER(VALOR),to_number(to_char(sysdate,'yyyy')) FROM PARAMETRO where TIPO=3 and NOMBRE='VIGENCIAINICIAL'");
			rs=pst.executeQuery();
			if(rs.next()){
				vigenciaIni=rs.getInt(1);
				vigenciaFin=rs.getInt(2);
			}
			for(i=vigenciaIni;i<=(vigenciaFin+1);i++){
				itemVO=new ItemVO();
				itemVO.setCodigo(i);
				itemVO.setNombre(String.valueOf(i));
				listaVigencia.add(itemVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return listaVigencia;
	}
}
