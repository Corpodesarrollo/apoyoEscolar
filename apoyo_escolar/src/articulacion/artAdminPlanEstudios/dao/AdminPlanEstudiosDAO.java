/**
 * 
 */
package articulacion.artAdminPlanEstudios.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.artAdminPlanEstudios.vo.FiltroBorrarVO;
import articulacion.artAdminPlanEstudios.vo.ParamsVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * 11/12/2007 
 * @author Latined
 * @version 1.2
 */
public class AdminPlanEstudiosDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c
	 */
	public AdminPlanEstudiosDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("articulacion.artAdminPlanEstudios.bundle.AdminPlanEstudios");
	}

	
	public synchronized List borrarPlan(FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//VALIDACION 1
			st=cn.prepareStatement(rb.getString("validar.evalSemestre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Evaluacinn final de semestre");
			}
			rs.close(); st.close();
			//VALIDACION 2
			st=cn.prepareStatement(rb.getString("validar.evalBimestre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Evaluacinn final de bimestre");
			}
			rs.close(); st.close();
			//VALIDACION 3
			st=cn.prepareStatement(rb.getString("validar.evalPrueba"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Evaluacinn final de pruebas");
			}
			rs.close(); st.close();
			//VALIDACION 5
			st=cn.prepareStatement(rb.getString("validar.ausencias"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Ausencias");
			}
			rs.close(); st.close();
			/*
			//VALIDACION 4
			st=cn.prepareStatement(rb.getString("validar.evalCierre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Cierre de pruebas");
			}
			rs.close(); st.close();
			*/
			//VALIDACION 6
			/*
			st=cn.prepareStatement(rb.getString("validar.retiro"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Cancelacinn de asignaturas");
			}
			rs.close(); st.close();
			*/
			//VALIDACION 7
			st=cn.prepareStatement(rb.getString("validar.inscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Inscripcinn de asignaturas");
			}
			rs.close(); st.close();
			//VALIDACION 8
			st=cn.prepareStatement(rb.getString("validar.preInscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Preinscripcinn de asignaturas");
			}
			rs.close(); st.close();
			
			//BORRAR DEPENDIENDO DEL TIPO
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
				case ParamsVO.OPCION_ASIGNATURA:
				case ParamsVO.OPCION_PRUEBA:
				case ParamsVO.OPCION_ASIGNACION:
				case ParamsVO.OPCION_GRUPO:	
				case ParamsVO.OPCION_HORARIO:
					borrarHistoricoAsignatura(cn,filtro);
				break;
			}
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
				case ParamsVO.OPCION_ASIGNACION:
				case ParamsVO.OPCION_GRUPO:	
				case ParamsVO.OPCION_HORARIO:
					borrarHistoricoGrupo(cn,filtro);
				break;
			}
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
				case ParamsVO.OPCION_HORARIO:
//					System.out.println("Borra Opcion de horario");
					borrarHistoricoHorario(cn,filtro);
				break;
			}
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
					l=borrarTodo(cn,filtro);
				break;
				case ParamsVO.OPCION_AREA:
					l=borrarArea(cn,filtro);
				break;
				case ParamsVO.OPCION_ASIGNATURA:
					l=borrarAsignatura(cn,filtro);
				break;
				case ParamsVO.OPCION_PRUEBA:
					l=borrarPrueba(cn,filtro);
				break;
				case ParamsVO.OPCION_ASIGNACION:
					l=borrarAsignacion(cn,filtro);
				break;
				case ParamsVO.OPCION_ESCALA:
					l=borrarEscala(cn,filtro);
				break;
				case ParamsVO.OPCION_GRUPO:
					l=borrarGrupo(cn,filtro);
				break;
				case ParamsVO.OPCION_HORARIO:
					l=borrarHorario(cn,filtro);
				break;
			}
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			throw new Exception("Hay registros asociados en "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	private List borrarEscala(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("borrar.escala"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Escalas valorativas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	private List borrarPrueba(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("borrar.prueba"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Pruebas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}


	private List borrarHorario(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("borrar.horario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Clases del horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.paramHorario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	private List borrarGrupo(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			//eliminacion 1
			//
			st=cn.prepareStatement(rb.getString("borrar.horario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Clases del horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.paramHorario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.inscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Inscripciones de estudiantes: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupoAsignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones de grupo y asignatura: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Grupos: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	private List borrarAsignacion(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("borrar.horario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Clases del horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.paramHorario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignacionAcademica"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones acadnmicas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	private List borrarAsignatura(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("borrar.horario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Clases del horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.paramHorario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("borrar.asignacionAcademica"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones acadnmicas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			//
			st=cn.prepareStatement(rb.getString("borrar.inscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Inscripciones de estudiantes: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupoAsignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones de grupo y asignatura: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Grupos: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			
			st=cn.prepareStatement(rb.getString("borrar.prueba"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Pruebas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.cierre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.retiro"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaComplementaria"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaCorrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaPrerrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	private List borrarArea(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("borrar.horario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Clases del horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.paramHorario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("borrar.asignacionAcademica"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones acadnmicas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			//
			st=cn.prepareStatement(rb.getString("borrar.inscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Inscripciones de estudiantes: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupoAsignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones de grupo y asignatura: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Grupos: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.prueba"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Pruebas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			
			st=cn.prepareStatement(rb.getString("borrar.cierre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.retiro"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaComplementaria"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaCorrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaPrerrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.area"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Areas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	private List borrarTodo(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("borrar.horario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Clases del horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.paramHorario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("borrar.asignacionAcademica"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones acadnmicas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			//
			st=cn.prepareStatement(rb.getString("borrar.inscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Inscripciones de estudiantes: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupoAsignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaciones de grupo y asignatura: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.grupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Grupos: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			
			st=cn.prepareStatement(rb.getString("borrar.prueba"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Pruebas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.cierre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.retiro"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaComplementaria"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaCorrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignaturaPrerrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.asignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.area"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Areas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.escala"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Escalas valorativas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}


	public synchronized List duplicarPlan(FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.escala"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Escala valorativa");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.area"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Area");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.asignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Asignatura");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.grupo"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Grupos");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.prueba"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Prueba");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.asignaturaComplementaria"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Asignaturas complementarias");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.asignaturaCorrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Correquisitos de Asignatura");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.asignaturaPrerrequisito"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Prerrequisitos de Asignatura");
			}
			rs.close(); st.close();
			//VALIDACION 5
			st=cn.prepareStatement(rb.getString("validar.ausencias"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Ausencias");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.horario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Horario");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.paramHorario"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Parametros de horario");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.grupoAsignatura"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Grupos");
			}
			rs.close(); st.close();
			//VALIDACION 9
			st=cn.prepareStatement(rb.getString("validar.asignacionAcademica"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Asignacinn acadnmica");
			}
			rs.close(); st.close();
			//VALIDACION 1
			st=cn.prepareStatement(rb.getString("validar.evalSemestre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Evaluacinn final de semestre");
			}
			rs.close(); st.close();
			//VALIDACION 2
			st=cn.prepareStatement(rb.getString("validar.evalBimestre"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Evaluacinn final de bimestre");
			}
			rs.close(); st.close();
			//VALIDACION 3
			st=cn.prepareStatement(rb.getString("validar.evalPrueba"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Evaluacinn final de pruebas");
			}
			rs.close(); st.close();
			//VALIDACION 6
			st=cn.prepareStatement(rb.getString("validar.inscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Inscripcinn de asignaturas");
			}
			rs.close(); st.close();
			//VALIDACION 8
			st=cn.prepareStatement(rb.getString("validar.preInscripcion"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("Preinscripcinn de asignaturas");
			}
			rs.close(); st.close();
			//validar las tablas reales de la cuestion
//			System.out.println("VALOR DE TIPO DE ACCION: "+filtro.getFilOpcion());
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
				case ParamsVO.OPCION_ASIGNATURA:
				case ParamsVO.OPCION_PRUEBA:
				case ParamsVO.OPCION_ASIGNACION:
				case ParamsVO.OPCION_GRUPO:	
				case ParamsVO.OPCION_HORARIO:
					borrarHistoricoAsignaturaNew(cn,filtro);
				break;
			}
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
				case ParamsVO.OPCION_ASIGNACION:
				case ParamsVO.OPCION_GRUPO:	
				case ParamsVO.OPCION_HORARIO:
					borrarHistoricoGrupoNew(cn,filtro);
				break;
			}
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
				case ParamsVO.OPCION_HORARIO:
					borrarHistoricoHorarioNew(cn,filtro);
				break;
			}
			switch(filtro.getFilOpcion()){
				case ParamsVO.OPCION_TODO:
					l=duplicarTodo(cn,filtro);
				break;
				case ParamsVO.OPCION_AREA:
					l=duplicarArea(cn,filtro);
				break;
				case ParamsVO.OPCION_ASIGNATURA:
					l=duplicarAsignatura(cn,filtro);
				break;
				case ParamsVO.OPCION_PRUEBA:
					l=duplicarPrueba(cn,filtro);
				break;
				case ParamsVO.OPCION_ASIGNACION:
					l=duplicarAsignacion(cn,filtro);
				break;
				case ParamsVO.OPCION_ESCALA:
					l=duplicarEscala(cn,filtro);
				break;
				case ParamsVO.OPCION_GRUPO:
					l=duplicarGrupo(cn,filtro);
				break;
				case ParamsVO.OPCION_HORARIO:
					l=duplicarHorario(cn,filtro);
				break;
			}
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			throw new Exception("En la vigencia destino hay registros en "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	private List duplicarArea(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("duplicar.area"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("áreas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	private List duplicarEscala(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			//eliminacion 1
			st=cn.prepareStatement(rb.getString("duplicar.escala"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Escalas valorativas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	private List duplicarAsignatura(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		CallableStatement cst = null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("duplicar.area"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("áreas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.asignatura"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular el total de asignaturas
			st=cn.prepareStatement(rb.getString("duplicar.asignatura2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
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
		return l;
	}


	private List duplicarGrupo(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		ResultSet rs=null;
		CallableStatement cst = null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("duplicar.area"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Escalas valorativas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.asignatura"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular el total de asignaturas
			st=cn.prepareStatement(rb.getString("duplicar.asignatura2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.grupo"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular total de grupos creados
			st=cn.prepareStatement(rb.getString("duplicar.grupo2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Grupos: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
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
		return l;
	}

	private void borrarHistoricoAsignatura(Connection cn,FiltroBorrarVO filtro)throws Exception{
		PreparedStatement st=null;
		int i=0;
		try{
			st=cn.prepareStatement(rb.getString("borrar.historicoAsignaturaOld"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.historicoAsignaturaNew"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.executeUpdate();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
	}
	private void borrarHistoricoGrupo(Connection cn,FiltroBorrarVO filtro)throws Exception{
		PreparedStatement st=null;
		int i=0;
		try{
			st=cn.prepareStatement(rb.getString("borrar.historicoGrupoOld"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.historicoGrupoNew"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.executeUpdate();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
	}

	private void borrarHistoricoHorario(Connection cn,FiltroBorrarVO filtro)throws Exception{
		PreparedStatement st=null;
		int i=0;
		try{
//			System.out.println("//"+filtro.getFilAnhoVigencia()+"//"+filtro.getFilPerVigencia()+"//"+filtro.getFilInstitucion());
			st=cn.prepareStatement(rb.getString("borrar.historicoHorarioOld"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.executeUpdate();
			st.close();
			st=cn.prepareStatement(rb.getString("borrar.historicoHorarioNew"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.executeUpdate();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
	}

	private void borrarHistoricoAsignaturaNew(Connection cn,FiltroBorrarVO filtro)throws Exception{
		PreparedStatement st=null;
		int i=0;
		try{
			st=cn.prepareStatement(rb.getString("borrar.historicoAsignaturaNew"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.executeUpdate();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
	}
	
	private void borrarHistoricoGrupoNew(Connection cn,FiltroBorrarVO filtro)throws Exception{
		PreparedStatement st=null;
		int i=0;
		try{
			st=cn.prepareStatement(rb.getString("borrar.historicoGrupoNew"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.executeUpdate();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
	}

	private void borrarHistoricoHorarioNew(Connection cn,FiltroBorrarVO filtro)throws Exception{
		PreparedStatement st=null;
		int i=0;
		try{
			st=cn.prepareStatement(rb.getString("borrar.historicoHorarioNew"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.executeUpdate();
			st.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
			}catch(InternalErrorException inte){}
		}
	}
	
	private List duplicarPrueba(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		CallableStatement cst = null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("duplicar.area"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("áreas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.asignatura"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular el total de asignaturas
			st=cn.prepareStatement(rb.getString("duplicar.asignatura2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.prueba"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Pruebas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
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
		return l;
	}

	private List duplicarAsignacion(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		CallableStatement cst = null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("duplicar.area"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("áreas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.asignatura"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular el total de asignaturas
			st=cn.prepareStatement(rb.getString("duplicar.asignatura2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.asignacion"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignacinn de docentes: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
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
		return l;
	}

	private List duplicarHorario(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		CallableStatement cst = null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("duplicar.area"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("áreas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.asignatura"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular el total de asignaturas
			st=cn.prepareStatement(rb.getString("duplicar.asignatura2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.asignacion"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignacinn de docentes: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.grupo"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular total de grupos creados
			st=cn.prepareStatement(rb.getString("duplicar.grupo2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Grupos: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			//parametros de horario y horario
			cst = cn.prepareCall(rb.getString("duplicar.horario"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			st=cn.prepareStatement(rb.getString("duplicar.horario2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.horario3"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Clases de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
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
		return l;
	}

	private List duplicarTodo(Connection cn,FiltroBorrarVO filtro)throws Exception{
		List l=new ArrayList();
		PreparedStatement st=null;
		CallableStatement cst = null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		int i=0;
		int tot=0;		
		try{
			st=cn.prepareStatement(rb.getString("duplicar.area"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("áreas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.asignatura"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular el total de asignaturas
			st=cn.prepareStatement(rb.getString("duplicar.asignatura2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Asignaturas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.prueba"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Pruebas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.asignacion"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Asignacinn de docentes: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
			cst = cn.prepareCall(rb.getString("duplicar.grupo"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			//calcular total de grupos creados
			st=cn.prepareStatement(rb.getString("duplicar.grupo2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Grupos: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			//parametros de horario y horario
			cst = cn.prepareCall(rb.getString("duplicar.horario"));
			i=1;
			cst.clearParameters();
			cst.setLong(i++,666);
			cst.setLong(i++,filtro.getFilInstitucion());
			cst.setLong(i++,filtro.getFilMetodologia());
			cst.setLong(i++,filtro.getFilAnhoVigencia());
			cst.setLong(i++,filtro.getFilPerVigencia());
			cst.setLong(i++,filtro.getFilAnhoVigencia2());
			cst.setLong(i++,filtro.getFilPerVigencia2());
			cst.executeUpdate();
			cst.close();
			st=cn.prepareStatement(rb.getString("duplicar.horario2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Parnmetros de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.horario3"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			rs=st.executeQuery();
			if(rs.next()){
				tot=rs.getInt(1);
			}else{
				tot=0;
			}
			itemVO=new ItemVO();
			itemVO.setNombre("Clases de horario: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			rs.close();
			st.close();
			st=cn.prepareStatement(rb.getString("duplicar.escala"));
			i=1;
			st.setLong(i++,filtro.getFilAnhoVigencia2());
			st.setLong(i++,filtro.getFilPerVigencia2());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setLong(i++,filtro.getFilMetodologia());
			st.setLong(i++,filtro.getFilAnhoVigencia());
			st.setLong(i++,filtro.getFilPerVigencia());
			tot=st.executeUpdate();
			itemVO=new ItemVO();
			itemVO.setNombre("Escalas valorativas: ");
			itemVO.setCodigo(tot);
			l.add(itemVO);
			st.close();
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
		return l;
	}

}
