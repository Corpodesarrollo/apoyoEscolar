/**
 * 
 */
package participacion.instancia.dao;

import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.vo.ParamParticipacion;
import participacion.instancia.vo.DocumentoVO;
import participacion.instancia.vo.FiltroInstanciaVO;
import participacion.instancia.vo.FiltroRangoVO;
import participacion.instancia.vo.InstanciaVO;
import participacion.instancia.vo.ObjetivoVO;
import participacion.instancia.vo.RangoVO;
import participacion.instancia.vo.RepresentanteVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class InstanciaDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c Objeto para la obtencinn de conexiones
	 */
	public InstanciaDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("participacion.instancia.bundle.instancia");
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

	/**
	 * Calcula la lista de niveles de instancia  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaNivel() throws ParticipacionException{
		List l=new ArrayList();
		l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_DISTRITAL,ParamParticipacion.NIVEL_INSTANCIA_DISTRITAL_));
		l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_CENTRAL,ParamParticipacion.NIVEL_INSTANCIA_CENTRAL_));
		l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_LOCAL,ParamParticipacion.NIVEL_INSTANCIA_LOCAL_));
		l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_COLEGIO,ParamParticipacion.NIVEL_INSTANCIA_COLEGIO_));
		return l;
	}

	/**
	 * Calcula la lista de instancias de un nivel  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaInstancia(FiltroInstanciaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		InstanciaVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("instancia.lista"));
			st.setInt(i++,filtro.getFilNivel());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new InstanciaVO();
				item.setInstCodigo(rs.getInt(i++));
				item.setInstNombre(rs.getString(i++));
				item.setInstNorma(rs.getString(i++));
				item.setInstConforma(rs.getString(i++));
				item.setInstIntegrantes(rs.getString(i++));
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
	 * Calcula la instancia  
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public InstanciaVO getInstancia(FiltroInstanciaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		InstanciaVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("instancia.obtener"));
			st.setInt(i++,filtro.getFilInstancia());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new InstanciaVO();
				item.setFormaEstado("1");
				item.setInstCodigo(rs.getInt(i++));
				item.setInstNivel(rs.getInt(i++));
				item.setInstNombre(rs.getString(i++));
				item.setInstNorma(rs.getString(i++));
				item.setInstConforma(rs.getString(i++));
				item.setInstIntegrantes(rs.getString(i++));
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

	/**
	 * Ingresa la instancia  
	 * @param item
	 * @return
	 * @throws ParticipacionException
	 */
	public synchronized InstanciaVO ingresarInstancia(InstanciaVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//calcular el codigo
			st=cn.prepareStatement(rb.getString("instancia.codigo"));
			rs=st.executeQuery();
			if(rs.next()){
				item.setInstCodigo(rs.getInt(1));
			}
			rs.close();st.close();
			st=cn.prepareStatement(rb.getString("instancia.ingresar"));
			i=1;
			//ITCCODIGO, ITCCODNIVEL, ITCNOMBRE, ITCNORMA, ITCCONFORMA, ITCINTEGRANTES
			st.setInt(i++,item.getInstCodigo());
			st.setInt(i++,item.getInstNivel());
			st.setString(i++,item.getInstNombre());
			st.setString(i++,item.getInstNorma());
			st.setString(i++,item.getInstConforma());
			st.setString(i++,item.getInstIntegrantes());
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
	 * Ingresa la instancia  
	 * @param item
	 * @return
	 * @throws ParticipacionException
	 */
	public InstanciaVO actualizarInstancia(InstanciaVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("instancia.actualizar"));
			i=1;
			//ITCCODNIVEL, ITCNOMBRE, ITCNORMA, ITCCONFORMA, ITCINTEGRANTES,ITCCODIGO
			st.setInt(i++,item.getInstNivel());
			st.setString(i++,item.getInstNombre());
			st.setString(i++,item.getInstNorma());
			st.setString(i++,item.getInstConforma());
			st.setString(i++,item.getInstIntegrantes());
			//w
			st.setInt(i++,item.getInstCodigo());
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
	 * Ingresa la instancia  
	 * @param item
	 * @return
	 * @throws ParticipacionException
	 */
	public void eliminarInstancia(FiltroInstanciaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//validar
			st=cn.prepareStatement(rb.getString("instancia.validarEliminacion"));
			i=1;
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilInstancia());
			rs=st.executeQuery();
			if(rs.next()){
				switch(rs.getInt(1)){
					case 1: throw new IntegrityException("Hay objetivos asociados");
					case 2: throw new IntegrityException("Hay representantes asociados");
					case 3: throw new IntegrityException("Hay documentos asociados");
					case 4: throw new IntegrityException("Hay rangos asociados");
				}
			}
			rs.close(); st.close();
			st=cn.prepareStatement(rb.getString("instancia.eliminar"));
			i=1;
			st.setInt(i++,filtro.getFilInstancia());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			if(sqle.getErrorCode()==1){
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "+sqle.getMessage());
		}catch(IntegrityException e){
			throw new IntegrityException(e.getMessage());
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

	public List getListaObjetivo(InstanciaVO instancia) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ObjetivoVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("objetivo.lista"));
			st.setInt(i++,instancia.getInstCodigo());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ObjetivoVO();
				item.setObjInstancia(rs.getInt(i++));
				item.setObjCodigo(rs.getInt(i++));
				item.setObjDescripcion(rs.getString(i++));
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

	public List getListaRepresentante(InstanciaVO instancia) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		RepresentanteVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("representante.lista"));
			st.setInt(i++,instancia.getInstCodigo());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new RepresentanteVO();
				item.setRepInstancia(rs.getInt(i++));
				item.setRepCodigo(rs.getInt(i++));
				item.setRepCantidad(rs.getInt(i++));
				item.setRepElige(rs.getString(i++));
				item.setRepNombre(rs.getString(i++));
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

	public List getListaDocumento(InstanciaVO instancia) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		DocumentoVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("documento.lista"));
			st.setInt(i++,instancia.getInstCodigo());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new DocumentoVO();
				item.setDocInstancia(rs.getInt(i++));
				item.setDocCodigo(rs.getInt(i++));
				item.setDocNombre(rs.getString(i++));
				item.setDocFecha(rs.getString(i++));
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

	public ObjetivoVO getObjetivo(FiltroInstanciaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ObjetivoVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("objetivo.obtener"));
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilObjetivo());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new ObjetivoVO();
				item.setFormaEstado("1");
				item.setObjInstancia(rs.getInt(i++));
				item.setObjCodigo(rs.getInt(i++));
				item.setObjDescripcion(rs.getString(i++));
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

	public List getRepresentante(FiltroInstanciaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		RepresentanteVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("representante.obtener"));
			st.setInt(i++,filtro.getFilInstancia());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new RepresentanteVO();
				item.setFormaEstado("1");
				item.setRepCodigo(rs.getInt(i++));
				item.setRepNombre(rs.getString(i++));
				item.setRepInstancia(rs.getInt(i++));
				item.setRepCantidad(rs.getInt(i++));
				item.setRepElige(rs.getString(i++));
				item.setRepChecked(item.getRepInstancia()>0?"checked":null);
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

	public DocumentoVO getDocumento(FiltroInstanciaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		DocumentoVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("documento.obtener"));
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilDocumento());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new DocumentoVO();
				item.setFormaEstado("1");
				item.setDocInstancia(rs.getInt(i++));
				item.setDocCodigo(rs.getInt(i++));
				item.setDocNombre(rs.getString(i++));
				item.setDocDescripcion(rs.getString(i++));
				item.setDocFecha(rs.getString(i++));
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

	public synchronized ObjetivoVO ingresarObjetivo(ObjetivoVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//calcular el codigo
			st=cn.prepareStatement(rb.getString("objetivo.codigo"));
			st.setInt(i++,item.getObjInstancia());
			rs=st.executeQuery();
			if(rs.next()){
				item.setObjCodigo(rs.getInt(1));
			}
			rs.close();st.close();
			st=cn.prepareStatement(rb.getString("objetivo.ingresar"));
			i=1;
			//ITCCODIGO, ITCCODNIVEL, ITCNOMBRE, ITCNORMA, ITCCONFORMA, ITCINTEGRANTES
			st.setInt(i++,item.getObjInstancia());
			st.setInt(i++,item.getObjCodigo());
			st.setString(i++,item.getObjDescripcion());
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

	public synchronized DocumentoVO ingresarDocumento(DocumentoVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		FileOutputStream out=null;
		try{
			cn=cursor.getConnection();
			//calcular el codigo
			st=cn.prepareStatement(rb.getString("documento.codigo"));
			st.setInt(i++,item.getDocInstancia());
			rs=st.executeQuery();
			if(rs.next()){
				item.setDocCodigo(rs.getInt(1));
			}
			rs.close();st.close();
			//crear el archivo:
			out = new FileOutputStream(item.getDocRuta()+item.getDocCodigo()+item.getDocExtension());
			out.write(item.getDocArchivo());
			out.flush();
			item.setDocRuta(item.getDocCodigo()+item.getDocExtension());
			//
			st=cn.prepareStatement(rb.getString("documento.ingresar"));
			i=1;
			//DOCCODITC, DOCCODIGO, DOCNOMBRE,DOCARCHIVO, DOCDESCRIP, DOCFECHA,DOCTIPOMIME
			st.setInt(i++,item.getDocInstancia());
			st.setInt(i++,item.getDocCodigo());
			st.setString(i++,item.getDocNombre());
			//st.setBinaryStream(i++, new java.io.ByteArrayInputStream(item.getDocArchivo()), item.getDocArchivo().length);
			st.setString(i++,item.getDocDescripcion());
			st.setString(i++,item.getDocFecha());
			st.setString(i++,item.getDocTipoMime());
			st.setString(i++,item.getDocExtension());
			st.setString(i++,item.getDocRuta());
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
				if(out!=null){ out.close();}
			}catch(Exception inte){}
		}
		return item;
	}

	public RepresentanteVO ingresarRepresentante(InstanciaVO instancia,RepresentanteVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		String [] valor;
		try{
			String [] roles=item.getRepRoles();
			cn=cursor.getConnection();
			//eliminar los que ya hay
			st=cn.prepareStatement(rb.getString("representante.eliminar"));
			st.setInt(i++,instancia.getInstCodigo());
			st.executeUpdate();
			st.close();
			if(roles!=null){
				for(int j=0;j<roles.length;j++){
					valor=roles[j].split(":");
					if(valor!=null && valor.length==4){
						if(Integer.parseInt(valor[1])==1){
							st=cn.prepareStatement(rb.getString("representante.ingresar"));
							//REPCODITC, REPCODROL, REPCANTIDAD,REPELIGE
							i=1;
							st.setInt(i++,instancia.getInstCodigo());
							st.setInt(i++,Integer.parseInt(valor[0]));
							st.setInt(i++,Integer.parseInt(valor[2]));
							st.setString(i++,valor[3]);
							st.executeUpdate();
							st.close();
						}
					}
				}
			}
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

	public ObjetivoVO actualizarObjetivo(ObjetivoVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("objetivo.actualizar"));
			i=1;
			st.setString(i++,item.getObjDescripcion());
			//w
			st.setInt(i++,item.getObjInstancia());
			st.setInt(i++,item.getObjCodigo());
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

	public DocumentoVO actualizarDocumento(DocumentoVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("documento.actualizar"));
			i=1;
			//DOCNOMBRE,DOCARCHIVO, DOCDESCRIP, DOCFECHA,DOCTIPOMIME,DOCCODITC, DOCCODIGO, 
			st.setString(i++,item.getDocNombre());
			st.setString(i++,item.getDocDescripcion());
			st.setString(i++,item.getDocFecha());
			//w
			st.setInt(i++,item.getDocInstancia());
			st.setInt(i++,item.getDocCodigo());
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

	public void eliminarObjetivo(FiltroInstanciaVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("objetivo.eliminar"));
			i=1;
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilObjetivo());
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

	public void eliminarDocumento(FiltroInstanciaVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("documento.eliminar"));
			i=1;
			//w
			st.setInt(i++,item.getFilInstancia());
			st.setInt(i++,item.getFilDocumento());
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

	public List getListaRango(FiltroRangoVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		RangoVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("rango.lista"));
			st.setInt(i++,filtro.getFilInstancia());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new RangoVO();
				//RANCODITC, RANCODIGO, RANNOMBRE, TO_CHAR(RANFECHAINI,'dd/mm/yyyy'), TO_CHAR(RANFECHAFIN,'dd/mm/yyyy'), RANESTADO
				item.setRanInstancia(rs.getInt(i++));
				item.setRanCodigo(rs.getInt(i++));
				item.setRanNombre(rs.getString(i++));
				item.setRanFechaIni(rs.getString(i++));
				item.setRanFechaFin(rs.getString(i++));
				item.setRanEstado(rs.getInt(i++));
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

	public RangoVO getRango(FiltroRangoVO filtro) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		RangoVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("rango.obtener"));
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilRango());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				//RANCODITC, RANCODIGO, RANNOMBRE, TO_CHAR(RANFECHAINI,'dd/mm/yyyy'), TO_CHAR(RANFECHAFIN,'dd/mm/yyyy'), RANESTADO
				item=new RangoVO();
				item.setFormaEstado("1");
				item.setFormaDisabled("disabled");
				item.setRanNivel(rs.getInt(i++));
				item.setRanInstancia(rs.getInt(i++));
				item.setRanCodigo(rs.getInt(i++));
				item.setRanNombre(rs.getString(i++));
				item.setRanFechaIni(rs.getString(i++));
				item.setRanFechaFin(rs.getString(i++));
				item.setRanEstado(rs.getInt(i++));
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

	public synchronized RangoVO ingresarRango(RangoVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//validar 
			st=cn.prepareStatement(rb.getString("rango.validarIngreso"));
			i=1;
			st.setInt(i++,item.getRanInstancia());
			st.setString(i++,item.getRanNombre());
			rs=st.executeQuery();
			if(rs.next()){
				throw new IntegrityException("Nombre de rango repetido");
			}
			rs.close();st.close();
			//calcular el codigo
			st=cn.prepareStatement(rb.getString("rango.codigo"));
			i=1;
			st.setInt(i++,item.getRanInstancia());
			rs=st.executeQuery();
			if(rs.next()){
				item.setRanCodigo(rs.getInt(1));
			}
			rs.close();st.close();
			st=cn.prepareStatement(rb.getString("rango.ingresar"));
			i=1;
			//RANCODITC, RANCODIGO, RANNOMBRE, RANFECHAINI, RANFECHAFIN, RANESTADO
			st.setInt(i++,item.getRanInstancia());
			st.setInt(i++,item.getRanCodigo());
			st.setString(i++,item.getRanNombre());
			st.setString(i++,item.getRanFechaIni());
			st.setString(i++,item.getRanFechaFin());
			st.setInt(i++,item.getRanEstado());
			st.executeUpdate();
			st.close();
			if(item.getRanEstado()==ParamParticipacion.RANGO_ACTIVO){
				//hay que inhabilitar los demas
				st=cn.prepareStatement(rb.getString("rango.inhabilitarEstados"));
				i=1;
				st.setInt(i++,ParamParticipacion.RANGO_INACTIVO);
				st.setInt(i++,item.getRanInstancia());
				st.setInt(i++,item.getRanCodigo());
				st.executeUpdate();
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			if(sqle.getErrorCode()==1){
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "+sqle.getMessage());
		}catch(IntegrityException sqle){
			throw new IntegrityException(sqle.getMessage());
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

	public RangoVO actualizarRango(RangoVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//validar 
			st=cn.prepareStatement(rb.getString("rango.validarActualizacion"));
			st.setInt(i++,item.getRanInstancia());
			st.setString(i++,item.getRanNombre());
			st.setInt(i++,item.getRanCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				throw new IntegrityException("Nombre de rango repetido");
			}
			rs.close();st.close();
			st=cn.prepareStatement(rb.getString("rango.actualizar"));
			i=1;
			//RANCODITC, RANCODIGO, RANNOMBRE, RANFECHAINI, RANFECHAFIN, RANESTADO
			st.setString(i++,item.getRanNombre());
			st.setString(i++,item.getRanFechaIni());
			st.setString(i++,item.getRanFechaFin());
			st.setInt(i++,item.getRanEstado());
			//w
			st.setInt(i++,item.getRanInstancia());
			st.setInt(i++,item.getRanCodigo());
			st.executeUpdate();
			st.close();
			if(item.getRanEstado()==ParamParticipacion.RANGO_ACTIVO){
				//hay que inhabilitar los demas
				st=cn.prepareStatement(rb.getString("rango.inhabilitarEstados"));
				i=1;
				st.setInt(i++,ParamParticipacion.RANGO_INACTIVO);
				st.setInt(i++,item.getRanInstancia());
				st.setInt(i++,item.getRanCodigo());
				st.executeUpdate();
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			if(sqle.getErrorCode()==1){
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "+sqle.getMessage());
		}catch(IntegrityException sqle){
			throw new IntegrityException(sqle.getMessage());
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

	public void eliminarRango(FiltroRangoVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//validar 
			st=cn.prepareStatement(rb.getString("rango.validarEliminacion"));
			st.setInt(i++,item.getFilInstancia());
			st.setInt(i++,item.getFilRango());
			st.setInt(i++,item.getFilInstancia());
			st.setInt(i++,item.getFilRango());
			rs=st.executeQuery();
			if(rs.next()){
				switch(rs.getInt(1)){
					case 1: throw new IntegrityException("Tiene participantes asociados");		
					case 2: throw new IntegrityException("Tiene actas asociadas");		
				}
			}
			rs.close();st.close();
			st=cn.prepareStatement(rb.getString("rango.eliminar"));
			i=1;
			st.setInt(i++,item.getFilInstancia());
			st.setInt(i++,item.getFilRango());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			if(sqle.getErrorCode()==1){
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "+sqle.getMessage());
		}catch(IntegrityException sqle){
			throw new IntegrityException(sqle.getMessage());
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

	public void eliminarRepresentante(FiltroInstanciaVO item) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("representante.eliminar2"));
			i=1;
			//w
			st.setInt(i++,item.getFilInstancia());
			st.setInt(i++,item.getFilRepresentante());
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

	public DocumentoVO getDocumentoDescarga(FiltroInstanciaVO filtro,String ruta) throws ParticipacionException{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		DocumentoVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("documento.obtenerDescarga"));
			st.setInt(i++,filtro.getFilInstancia());
			st.setInt(i++,filtro.getFilDocumento());
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new DocumentoVO();
				item.setDocNombre(rs.getString(i++));
				item.setDocRuta(ruta+rs.getString(i++));
				item.setDocTipoMime(rs.getString(i++));
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
}
