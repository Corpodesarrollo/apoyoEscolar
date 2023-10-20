/**
 * 
 */
package articulacion.artPlantillaFinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.artPlantillaFinal.vo.AsignaturaVO;
import articulacion.artPlantillaFinal.vo.EscalaVO;
import articulacion.artPlantillaFinal.vo.EstudianteVO;
import articulacion.artPlantillaFinal.vo.PlantillaFinalVO;
import articulacion.artPlantillaFinal.vo.UrlImportar;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;


/**
 * 5/12/2007 
 * @author Latined
 * @version 1.2
 */
public class PlantillaFinalDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c
	 */
	public PlantillaFinalDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("articulacion.artPlantillaFinal.bundle.PlantillaFinal");
	}
	
	public void getParametrosPlantilla(PlantillaFinalVO filtro)throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			filtro.setPlaNombreAnhoVigencia(String.valueOf(filtro.getPlaAnhoVigencia()));
			filtro.setPlaNombrePerVigencia(String.valueOf(filtro.getPlaPerVigencia()));
			filtro.setPlaNombreSemestre(String.valueOf(filtro.getPlaSemestre()));
			filtro.setPlaNombreComponente(filtro.getPlaComponente()==1?"Acadnmico":"Tncnico");
			
			st=cn.prepareStatement(rb.getString("getNombreGrado"));
			i=1;
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaGrado());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setPlaNombreGrado(rs.getString(1));
			}
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("getNombreGrupo"));
			i=1;
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaSede());
			st.setInt(i++,filtro.getPlaJornada());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaGrado());
			st.setInt(i++,filtro.getPlaGrupo());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setPlaNombreGrupo(rs.getString(1));
			}
			rs.close();
			st.close();
			//especialidad
			if(filtro.getPlaEspecialidad()!=0 && filtro.getPlaEspecialidad()!=-99){
				st=cn.prepareStatement(rb.getString("getNombreEspecialidad"));
				i=1;
				st.setLong(i++,filtro.getPlaInstitucion());
				st.setLong(i++,filtro.getPlaEspecialidad());
				rs=st.executeQuery();
				if(rs.next()){
					filtro.setPlaNombreEspecialidad(rs.getString(1));
				}
				rs.close();
				st.close();
			}else{
				filtro.setPlaNombreEspecialidad("");
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}
		catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
    }

	public List getEstudiantePlantilla(PlantillaFinalVO filtro,List listaAsig)throws Exception{
		List l=new ArrayList();
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		EstudianteVO estudiante=null;
		int i=0;
		try{
			//System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn=cursor.getConnection();
			//calcular el codigo de jerarquia
			st=cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i=1;
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaSede());
			st.setInt(i++,filtro.getPlaJornada());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaGrado());
			st.setInt(i++,filtro.getPlaGrupo());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setPlaJerarquia(rs.getLong(1));
			}else{
				throw new Exception("Jerarquia no encontrada");
			}
			rs.close();
			st.close();
			//System.out.println("1.))"+"getEstudianteComportamiento."+filtro.getPlaOrden()+"."+filtro.getPlaPeriodo());
			//System.out.println("2."+filtro.getPlaJerarquia());
			st=cn.prepareStatement(rb.getString("getEstudiantePlantilla."+filtro.getPlaOrden()));
			i=1;
			st.setLong(i++,filtro.getPlaJerarquia());
			rs=st.executeQuery();
			int index=1;
			while(rs.next()){
				i=1;
				estudiante=new EstudianteVO();
				estudiante.setEstCodigo(rs.getLong(i++));
				estudiante.setEstApellido(rs.getString(i++));
				estudiante.setEstNombre(rs.getString(i++));
				estudiante.setEstConsecutivo(index++);
				estudiante.setEstNota(getNota(cn,filtro,estudiante.getEstCodigo()));
				l.add(estudiante);
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}
		catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	private List getNota(Connection cn,PlantillaFinalVO filtro,long codigo)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			st=cn.prepareStatement(rb.getString("getNota."+filtro.getPlaComponente()));
			st.setLong(i++,codigo);
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaAnhoVigencia());
			st.setInt(i++,filtro.getPlaPerVigencia());
			st.setInt(i++,filtro.getPlaComponente());
			if(filtro.getPlaComponente()==2){
				st.setLong(i++,filtro.getPlaEspecialidad());
			}
			rs=st.executeQuery();
			while(rs.next()){
				l.add(rs.getString(1));
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	

	public UrlImportar insertarEvaluacionFinal(PlantillaFinalVO filtro,List estudiantes,List asignaturas,UrlImportar url) throws Exception{
		Connection cn=null;
		PreparedStatement st=null,st2=null;
		ResultSet rs=null;
		List resultado=new ArrayList();
		List listaNota=null;
		int totEst=0,totIngr=0,totAct=0,totElim=0;
		int i=0;
		float nota=0;
		String notaCon=null;
		String sql="";
		long valores[]=null;
		EstudianteVO estudiante=null;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			if(estudiantes!=null && asignaturas!=null){
				totEst=estudiantes.size();
				valores=new long[asignaturas.size()];
				sql=rb.getString("eliminarNotaEstudiante.1");
				for(int k=0;k<asignaturas.size();k++){
					sql+=" ?,";
					valores[k]=((AsignaturaVO)asignaturas.get(k)).getAsiCodigo();
				}
				sql=sql.substring(0,sql.length()-1);
				sql+=" "+rb.getString("eliminarNotaEstudiante.2");
				st=cn.prepareStatement(sql);
				st.clearBatch();
				for(int j=0;j<estudiantes.size();j++){
					i=1;
					estudiante=(EstudianteVO)estudiantes.get(j);
					if(estudiante!=null){
						st.setLong(i++,estudiante.getEstCodigo());
						for(int k=0;k<valores.length;k++){
							st.setLong(i++,valores[k]);
						}
						st.addBatch();
					}
				}
				int tot[]=st.executeBatch();
				for(int k=0;k<tot.length;k++){
					//System.out.println("ELIMINO: "+tot[k]);
					totElim+=Math.abs(tot[k]);
				}
				st.close();
				st=cn.prepareStatement(rb.getString("ingresarNotaEstudiante"));
				st.clearBatch();
				for(int j=0;j<estudiantes.size();j++){
					estudiante=(EstudianteVO)estudiantes.get(j);
					listaNota=estudiante.getEstNota();
					if(estudiante!=null){						
						for(int k=0;k<valores.length;k++){
							if(listaNota.get(k)!=null){
								i=1;
								nota=Float.parseFloat((String)listaNota.get(k));
								notaCon=getNotaCon(cn,filtro,((nota*100)/filtro.getPlaNotaMax()));
								st.setLong(i++,estudiante.getEstCodigo());
								st.setLong(i++,valores[k]);
								st.setFloat(i++,nota);
								st.setFloat(i++,nota);
								st.setString(i++,notaCon);
								st.setString(i++,notaCon);
								st.addBatch();
								totIngr++;
							}	
						}
					}
				}
				st.executeBatch();
			}
			ItemVO itemVO=new ItemVO();
			//total estdiantes
			itemVO.setNombre("Total estudiantes:");
			itemVO.setCodigo(totEst);
			resultado.add(itemVO);
			//total ingresados
			itemVO=new ItemVO();
			itemVO.setNombre("Total ingresados:");
			if(totIngr==totElim) itemVO.setCodigo(0);
			if(totIngr>totElim) itemVO.setCodigo(totIngr-totElim); 
			if(totIngr<totElim) itemVO.setCodigo(0);
			resultado.add(itemVO);
			//total actualizados
			itemVO=new ItemVO();
			itemVO.setNombre("Total actualizados:");
			if(totIngr==totElim) itemVO.setCodigo(totIngr);
			if(totIngr>totElim) itemVO.setCodigo(totIngr-totElim); 
			if(totIngr<totElim) itemVO.setCodigo(totElim-totIngr);
			resultado.add(itemVO);
			//total eliminados
			itemVO=new ItemVO();
			itemVO.setNombre("Total eliminados:");
			if(totIngr==totElim) itemVO.setCodigo(0);
			if(totIngr>totElim) itemVO.setCodigo(0); 
			if(totIngr<totElim) itemVO.setCodigo(totElim-totIngr);
			resultado.add(itemVO);
			url.setResultado(resultado);
			cn.commit();
			
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}
		catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return url;
	}
	
	//
	private String getNotaCon(Connection cn,PlantillaFinalVO filtro,float nota)throws Exception{
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			st=cn.prepareStatement(rb.getString("getNotaCon"));
			int i=1;
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setLong(i++,filtro.getPlaMetodologia());
			st.setLong(i++,filtro.getPlaAnhoVigencia());
			st.setLong(i++,filtro.getPlaPerVigencia());
			st.setFloat(i++,nota);			
			rs=st.executeQuery();
			if(rs.next()) return rs.getString(1);
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return null;
	}
	
    public List getSedes(String inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO sedeVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getSede"));
			i=1;
			st.setInt(i++,Integer.parseInt(inst));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				sedeVO=new ItemVO();
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

    public List getJornada(String inst, String sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO jornadaVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getJornada"));
			i=1;
			st.setInt(i++,Integer.parseInt(inst));
	
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				jornadaVO=new ItemVO();
				jornadaVO.setPadre(rs.getInt(i++));
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
    
	public List getAjaxGrupo(long inst, int sede, int jornada, int grado, String metodologia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ItemVO lp = null;

		int i = 0;
		try {
			if (inst == 0 || sede == 0 || jornada == 0 || grado == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxGrupo"));
			i = 1;
			st.setLong(i++, inst);
			st.setLong(i++, sede);
			st.setLong(i++, jornada);
			st.setLong(i++, grado);
			st.setLong(i++, Long.parseLong(metodologia));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ItemVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lpA;
	}

	public List getAjaxEspecialidad(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ItemVO lp = null;
		int i = 0;
		try {
			if (inst == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxEspecialidad"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ItemVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lpA;
	}

	public List getAsignaturaPlantilla(PlantillaFinalVO filtro)throws Exception{
		List l=new ArrayList();
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		AsignaturaVO asignatura=null;
		int i=0;
		try{
			//System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAsignaturaPlantilla."+filtro.getPlaComponente()));
			i=1;
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaAnhoVigencia());
			st.setInt(i++,filtro.getPlaPerVigencia());
			st.setInt(i++,filtro.getPlaComponente());
			if(filtro.getPlaComponente()==2){
				st.setLong(i++,filtro.getPlaEspecialidad());
			}
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				asignatura=new AsignaturaVO();
				asignatura.setAsiCodigo(rs.getLong(i++));
				asignatura.setAsiNombre(rs.getString(i++));
				asignatura.setAsiAbreviatura(rs.getString(i++));
				l.add(asignatura);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	public List getEscala(PlantillaFinalVO filtro)throws Exception{
		List l=new ArrayList();
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		EscalaVO escala=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getEscala"));
			i=1;
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaAnhoVigencia());
			st.setInt(i++,filtro.getPlaPerVigencia());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				escala=new EscalaVO();
				escala.setEscNombre(rs.getString(i++));
				escala.setEscValorMinimo(rs.getFloat(i++));
				escala.setEscValorMaximo(rs.getFloat(i++));
				l.add(escala);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	public float getNotaMax()throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getValorMax"));
			rs=st.executeQuery();
			if(rs.next()){
				return rs.getFloat(1);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return 0;
	}

	public List getEstudianteEval(PlantillaFinalVO filtro,List listaAsig)throws Exception{
		List l=new ArrayList();
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		EstudianteVO estudiante=null;
		int i=0;
		try{
			//System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn=cursor.getConnection();
			//calcular el codigo de jerarquia
			st=cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i=1;
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaSede());
			st.setInt(i++,filtro.getPlaJornada());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaGrado());
			st.setInt(i++,filtro.getPlaGrupo());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setPlaJerarquia(rs.getLong(1));
			}else{
				throw new Exception("Jerarquia no encontrada");
			}
			rs.close();
			st.close();
			//System.out.println("1.))"+"getEstudianteComportamiento."+filtro.getPlaOrden()+"."+filtro.getPlaPeriodo());
			//System.out.println("2."+filtro.getPlaJerarquia());
			st=cn.prepareStatement(rb.getString("getEstudiantePlantilla."+filtro.getPlaOrden()));
			i=1;
			st.setLong(i++,filtro.getPlaJerarquia());
			rs=st.executeQuery();
			int index=1;
			while(rs.next()){
				i=1;
				estudiante=new EstudianteVO();
				estudiante.setEstCodigo(rs.getLong(i++));
				estudiante.setEstApellido(rs.getString(i++));
				estudiante.setEstNombre(rs.getString(i++));
				estudiante.setEstConsecutivo(index++);
				estudiante.setEstNota(getNotaEval(cn,filtro,estudiante.getEstCodigo()));
				l.add(estudiante);
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}
		catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	private List getNotaEval(Connection cn,PlantillaFinalVO filtro,long codigo)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ResultSet rs=null;
		ItemVO nota=null;
		int i=1;
		try{
			st=cn.prepareStatement(rb.getString("getNotaEval."+filtro.getPlaComponente()));
			st.setLong(i++,codigo);
			st.setLong(i++,filtro.getPlaInstitucion());
			st.setInt(i++,filtro.getPlaMetodologia());
			st.setInt(i++,filtro.getPlaAnhoVigencia());
			st.setInt(i++,filtro.getPlaPerVigencia());
			st.setInt(i++,filtro.getPlaComponente());
			if(filtro.getPlaComponente()==2){
				st.setLong(i++,filtro.getPlaEspecialidad());
			}
			rs=st.executeQuery();
			while(rs.next()){
				nota=new ItemVO();
				nota.setCodigo(rs.getLong(1));
				nota.setNombre(rs.getString(2));
				l.add(nota);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	public void insertarEvaluacionFinal(PlantillaFinalVO filtro,String[] estudiantes,String[] asignaturas,String[] nota) throws Exception{
		Connection cn=null;
		PreparedStatement st=null,st2=null;
		ResultSet rs=null;
		int i=0;
		String notaCon=null;
		String sql="";
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			if(estudiantes!=null){
				sql=rb.getString("eliminarNotaEstudiante.1");
				for(int k=0;k<asignaturas.length;k++){
					sql+=" ?,";
				}
				sql=sql.substring(0,sql.length()-1);
				sql+=" "+rb.getString("eliminarNotaEstudiante.2");
				st=cn.prepareStatement(sql);
				st.clearBatch();
				for(int j=0;j<estudiantes.length;j++){
					i=1;
					st.setLong(i++,Long.parseLong(estudiantes[j]));
					for(int k=0;k<asignaturas.length;k++){
						st.setLong(i++,Long.parseLong(asignaturas[k]));
					}
					st.addBatch();
				}
				st.executeBatch();
				st.close();
				st=cn.prepareStatement(rb.getString("ingresarNotaEstudiante"));
				st.clearBatch();
				String []datos;
				long estudiante=0;
				long asignatura=0;
				float eval=0;
				if(nota!=null){
					for(int k=0;k<nota.length;k++){
						datos=nota[k].replace('|',':').split(":");
						estudiante=Long.parseLong(datos[0]);
						asignatura=Long.parseLong(datos[1]);
						eval=Float.parseFloat(datos[2]);
						if(eval!=-99){
							i=1;
							notaCon=getNotaCon(cn,filtro,((eval*100)/filtro.getPlaNotaMax()));
							st.setLong(i++,estudiante);
							st.setLong(i++,asignatura);
							st.setFloat(i++,eval);
							st.setFloat(i++,eval);
							st.setString(i++,notaCon);
							st.setString(i++,notaCon);
							st.addBatch();
						}
					}
					st.executeBatch();
				}
			}
			cn.commit();
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}
}
