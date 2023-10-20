package poa.seguimientoSED.dao;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		02/12/2019	JORGE CAMACHO	Se modificó el método getActividad para agregarle la asignación del campo PLACOTROCUAL al objeto

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.seguimientoSED.vo.ArchivoSegVO;
import poa.seguimientoSED.vo.ColegioPoaVO;
import poa.seguimientoSED.vo.FiltroSeguimientoVO;
import poa.seguimientoSED.vo.ParamsVO;
import poa.seguimientoSED.vo.SegFechasCompareVO;
import poa.seguimientoSED.vo.SeguimientoVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el mndulo de aprobacinn del POA. 
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class SeguimientoDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c Objeto de obtencinn de conexiones a la base de datos
	 */
	public SeguimientoDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("poa.seguimientoSED.bundle.Seguimiento");
	}
	

	
public List getListaFuenteFinanciacion() throws Exception {
		
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		
		l.add(new String[]{"Recursos Propios","recursos propios"});
		l.add(new String[]{"Transferencias DEE 2012","transferencias DEE 2012"});
		l.add(new String[]{"Transferencias DEE 2013","transferencias DEE 2013"});
		l.add(new String[]{"Otras Transferencias SED","otras transferencias sed"});
		l.add(new String[]{"Recursos Alcaldna Local","recursos alcaldia local"});
		l.add(new String[]{"Recursos Privados","recursos privados"});
		l.add(new String[]{"Otros Recursos","otros recursos"});
		l.add(new String[]{"Sin Recursos","sin recursos"});
	
		return l;
	}
	
public List getListaAccionMejoramiento() throws Exception {
		
		List l = new ArrayList();
		String itemLista[] = new String[2]; 

		l.add(new String[]{"Uso Eficiente del Agua","uso eficiente del agua"});
		l.add(new String[]{"Uso Eficiente de la Energia","uso eficiente de la energia"});
		l.add(new String[]{"Gestion Integral de Residuos","gestion integral de residuos"});
		l.add(new String[]{"Mejoramiento de las Condiciones Ambientales Internas","mejoramiento de las condiciones ambientales internas"});
		l.add(new String[]{"Criterios Ambientales Para las Compras y Gestion Contractual","criterios ambientales para las compras y gestion contractual"});
		l.add(new String[]{"Extension de Buenas Practicas Ambientales","extension de buenas practicas ambientales"});
		l.add(new String[]{"Otras","otras"});
		
		return l;
	}
	
	public List getListaRubroGasto(String tipoRubro) throws Exception {
	
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		
		if(tipoRubro == null){
			return l;
		}
		else if (tipoRubro.equalsIgnoreCase("Inversion")){
			l.add(new String[]{"Vitrina Pedagogica","vitrina pedagogica"});
			l.add(new String[]{"Escuela Ciudad Escuela","escuela ciudad escuela"});
			l.add(new String[]{"Medio Ambiente y Prevencion de Desastres","medio ambiente y prevencion de desastres"});
			l.add(new String[]{"Aprovechamiento del Tiempo Libre","aprovechamiento del tiempo libre"});
			l.add(new String[]{"Educacion Sexual","educacion sexual"});
			l.add(new String[]{"Compra Equipos Beneficio de los Estudiantes","compra equipos beneficio de los estudiantes"});
			l.add(new String[]{"Formacion de Valores","formacion de valores"});
			l.add(new String[]{"Formacion Tecnica y Para el Trabajo","formacion tecnica y para el trabajo"});
			l.add(new String[]{"Fomento de la Cultura","fomento de la cultura"});
			l.add(new String[]{"Otros Proyectos","otros proyectos"});
			l.add(new String[]{"Investigacinn y Estudios","Investigacinn y Estudios"});
			
		}else if(tipoRubro.equalsIgnoreCase("Funcionamiento")){
			l.add(new String[]{"Servicios Personales","servicios personales"});
			l.add(new String[]{"Gastos Generales","gastos generales"});
			l.add(new String[]{"Pasivos Exigibles Inversinn","Pasivos Exigibles Inversinn"});
		}
		
		
		return l;
	}
	
	/**
	 * Calcula la lista de Localidades  
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public List getListaLocalidad() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaLocalidad"));
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
	
	/**
	 * Calcula la lista de Ã¡reas de gestinn
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaAreaGestion"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
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

	/**
	 * Calcula la lista de lineas de accinn  
	 * @param areaGestion codigo del area de gestinn
	 * @return Lista de lineas de accinn
	 * @throws Exception
	 */
	public List getListaLineaAccion(int areaGestion) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaLineaAccion"));
			st.setInt(i++,areaGestion);
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

	/**
	 * Calcula la lista de actividades con recursos
	 * @param filtro Filtro de bnsqueda  
	 * @return Lista de actividades 
	 * @throws Exception
	 */
	public List getListaActividades(FiltroSeguimientoVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		SeguimientoVO item=null;
		int i=0;
		int cons=1;		
		String calif;
		
		try{
			cn=cursor.getConnection();
			//calcular el nombre del colegio
			st=cn.prepareStatement(rb.getString("poa.getNombreColegio"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilNombreColegio(rs.getString(1));
			}
			rs.close();
			st.close();
			//calculo de la lista
			st=cn.prepareStatement(rb.getString("planeacion.lista2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,filtro.getFilVigencia());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new SeguimientoVO();
				item.setPlaConsecutivo(cons++);
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestionNombre(rs.getString(i++));
				item.setPlaLineaAccionNombre(rs.getString(i++));
				item.setPlaSeguimiento1(rs.getString(i++));
				item.setPlaSeguimiento2(rs.getString(i++));
				item.setPlaSeguimiento3(rs.getString(i++));
				item.setPlaSeguimiento4(rs.getString(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				
				calif = rs.getString(i++);				
				item.setCalifActividad(calif);
				
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

	/**
	 * Obtiene la actividad con recursos  
	 * @param vigencia anho de vigencia
	 * @param institucion codigo de la institucinn
	 * @param codigo codigo de la actividad
	 * @return Actividad que se solicitn
	 * @throws Exception
	 */
	public SeguimientoVO getActividad(int vigencia, long institucion, long codigo) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		SeguimientoVO item = null;
		
		int i = 0;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.obtener2"));
			
			i = 1;
			
			st.setInt(i++,vigencia);
			st.setLong(i++,institucion);
			st.setLong(i++,codigo);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				i = 1;
				item = new SeguimientoVO();
				
				item.setFormaEstado("1");
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaOrden(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestion(rs.getInt(i++));
				item.setPlaLineaAccion(rs.getInt(i++));
				item.setPlaTipoMeta(rs.getInt(i++));
				item.setPlaCantidad(rs.getLong(i++));
				item.setPlaUnidadMedida(rs.getInt(i++));
				item.setPlaFecha(rs.getString(i++));
				item.setPlaCronograma1(rs.getString(i++));
				item.setPlaCronograma2(rs.getString(i++));
				item.setPlaCronograma3(rs.getString(i++));
				item.setPlaCronograma4(rs.getString(i++));
				item.setPlaFechaReal(rs.getString(i++));
				item.setPlaSeguimiento1(rs.getString(i++));
				item.setPlaSeguimiento2(rs.getString(i++));
				item.setPlaSeguimiento3(rs.getString(i++));
				item.setPlaSeguimiento4(rs.getString(i++));
				item.setPlaPorcentaje1(rs.getString(i++));
				item.setPlaPorcentaje2(rs.getString(i++));
				item.setPlaPorcentaje3(rs.getString(i++));
				item.setPlaPorcentaje4(rs.getString(i++));
				item.setPlaVerificacion1(rs.getString(i++));
				item.setPlaLogros1(rs.getString(i++));
				item.setPlaVerificacion2(rs.getString(i++));
				item.setPlaLogros2(rs.getString(i++));
				item.setPlaVerificacion3(rs.getString(i++));
				item.setPlaLogros3(rs.getString(i++));
				item.setPlaVerificacion4(rs.getString(i++));
				item.setPlaLogros4(rs.getString(i++));
				item.setPlaDisabled("disabled");
				item.setPlaDisabled1("disabled");
				item.setPlaDisabled2("disabled");
				item.setPlaDisabled3("disabled");
				item.setPlaDisabled4("disabled");
				item.setPlaDesHabilitado(true);
				item.setPlaMostrarPorcentaje(rs.getInt(i++)==1?true:false);
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				item.setTipoActividad(rs.getString(i++));
				/* PIMA - PIGA*/
				item.setAccionMejoramiento(rs.getString(i++));
				item.setAccionMejoramientoOtras(rs.getString(i++));
				
				item.setTIPOGASTO(rs.getString(i++));
				item.setRUBROGASTO(rs.getString(i++));				
				
				if(item.getTIPOGASTO() != null){
					if(item.getTIPOGASTO().equalsIgnoreCase("Inversion")){
						item.setPROYECTOINVERSION(rs.getString(i++));
					}else if(item.getTIPOGASTO().equalsIgnoreCase("Funcionamiento")){
						item.setSUBNIVELGASTO(rs.getString(i++));
					}else{
						i++;
					}
				}else{
					i++;
				}
				
				item.setFUENTEFINANCIACION(rs.getString(i++));
				item.setFUENTEFINANCIACIONOTROS(rs.getString(i++));
				item.setMONTOANUAL(rs.getLong(i++));
				item.setPPTOPARTICIPATIVO(rs.getLong(i++));
				
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				item.setSEGFECHAREALCUMPLIM(rs.getString(i++));
				item.setPRESUPUESTOEJECUTADO(rs.getInt(i++));
				
				item.setRESPONSABLE(rs.getString(i++));
				
				// DEMANDA
				item.setPlaPorcentaje1(rs.getString(i++));
				item.setPlaPorcentaje2(rs.getString(i++));
				item.setPlaPorcentaje3(rs.getString(i++));
				item.setPlaPorcentaje4(rs.getString(i++));
				
				item.setPLACOTROCUAL(rs.getString(i++));
				item.setPlaPonderador(rs.getInt(i++));
				
				item.setCalifEvalSeg1(rs.getString(i++));
				item.setCalifEvalSeg2(rs.getString(i++));
				item.setCalifEvalSeg3(rs.getString(i++));
				item.setCalifEvalSeg4(rs.getString(i++));				
				item.setObserEvalSeg1(rs.getString(i++));
				item.setObserEvalSeg2(rs.getString(i++));
				item.setObserEvalSeg3(rs.getString(i++));
				item.setObserEvalSeg4(rs.getString(i++));
				item.setEvalTexto1(rs.getString(i++));
				item.setEvalTexto2(rs.getString(i++));
				item.setEvalTexto3(rs.getString(i++));
				item.setEvalTexto4(rs.getString(i++));
				
				
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
		return item;
	}

	/**
	 * Calcula la lista de vigencias desde la anterior a la posterior a la actual  
	 * @return Lista de anhos de vigencia
	 * @throws Exception
	 */
	public List getListaVigenciaActual()throws Exception{
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		List listaVigencia=new ArrayList();
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("planeacion.listaVigencia"));
			rs=pst.executeQuery();
			if(rs.next()){
				itemVO=new ItemVO(rs.getInt(1),String.valueOf(rs.getInt(1)));
				listaVigencia.add(itemVO);
				itemVO=new ItemVO(rs.getInt(2),String.valueOf(rs.getInt(2)));
				listaVigencia.add(itemVO);
				itemVO=new ItemVO(rs.getInt(3),String.valueOf(rs.getInt(3)));
				listaVigencia.add(itemVO);
			}
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return listaVigencia;
	}


	/**
	 * Calcula los comentarios de las tablas relacionadas con actividades con recursos  
	 * @param filtro Filtro de bnsqueda de actividades 
	 * @return Filtro de bnsqueda de actividades
	 * @throws Exception
	 */
	public FiltroSeguimientoVO getLabels(FiltroSeguimientoVO filtro)throws Exception{
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		if(filtro==null) filtro=new FiltroSeguimientoVO();
		try{
			filtro.setFilVigencia((int)getVigenciaNumericoPOA());
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("planeacion.label"));
			rs=pst.executeQuery();
			while(rs.next()){
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACVIGENCIA)){ filtro.setLblVigencia(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACOBJETIVO)){ filtro.setLblObjetivo(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRE)){ filtro.setLblActividad(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODARGESTION)){ filtro.setLblAreaGestion(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODLIACCION)){ filtro.setLblLineaAccion(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACFECHATERMIN)){ filtro.setLblFecha(rs.getString(2));continue;}
				
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO1)){ filtro.setLblCronograma1(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO2)){ filtro.setLblCronograma2(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO3)){ filtro.setLblCronograma3(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO4)){ filtro.setLblCronograma4(rs.getString(2));continue;}

				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO1)){ filtro.setLblSeguimiento1(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO2)){ filtro.setLblSeguimiento2(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO3)){ filtro.setLblSeguimiento3(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO4)){ filtro.setLblSeguimiento4(rs.getString(2));continue;}

				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE1)){ filtro.setLblPorcentaje1(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE2)){ filtro.setLblPorcentaje2(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE3)){ filtro.setLblPorcentaje3(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE4)){ filtro.setLblPorcentaje4(rs.getString(2));continue;}

				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION1)){ filtro.setLblVerificacion1(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION2)){ filtro.setLblVerificacion2(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION3)){ filtro.setLblVerificacion3(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION4)){ filtro.setLblVerificacion4(rs.getString(2));continue;}

				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO1)){ filtro.setLblLogros1(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO2)){ filtro.setLblLogros2(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO3)){ filtro.setLblLogros3(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO4)){ filtro.setLblLogros4(rs.getString(2));continue;}
			}
			rs.close();
			pst.close();
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return filtro;
	}

	/**
	 * Calcula la lista de colegios que tienen el POA aprobado   
	 * @param filtro
	 * @return Lista de colegios
	 * @throws Exception
	 */
	public List getListaColegio(FiltroSeguimientoVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ColegioPoaVO item=null;
		int i=1;
		int cons=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaColegio"));
			st.setInt(i++,filtro.getFilLocalidad());
			st.setInt(i++,filtro.getFilVigencia());
			st.setInt(i++,ParamPOA.ESTADO_POA_APROBADO_SED);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ColegioPoaVO();
				item.setColConsecutivo(cons++);
				item.setColVigencia(filtro.getFilVigencia());
				item.setColColegio(rs.getLong(i++));
				item.setColNombre(rs.getString(i++));
				item.setColCodigo(rs.getInt(i++));
				item.setColEstado(rs.getInt(i++));
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

	/**
	 * Calcula la lista de estados de poa
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public String getNombreEstado(int estado) throws Exception{
		switch(estado){
			case ParamPOA.ESTADO_POA_APROBADO_COLEGIO: return ParamPOA.ESTADO_POA_APROBADO_COLEGIO_; 
			case ParamPOA.ESTADO_POA_APROBADO_SED: return ParamPOA.ESTADO_POA_APROBADO_SED_;
			case ParamPOA.ESTADO_POA_RECHAZADO_SED: return ParamPOA.ESTADO_POA_RECHAZADO_SED_; 
		}
		return null;
	}

	/**
	 * 	Calcula la lista de tipos de meta  
	 * @return Lista de tipos de meta
	 * @throws Exception
	 */
	public List getListaTipoMeta() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaTipoMeta"));
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

	/**
	 * Calcula la lista de unidades de medida  
	 * @return lista de unidades de medida
	 * @throws Exception
	 */
	public List getListaUnidadMedida() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaUnidadMedida"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
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
	
	

	public List getlistaObjetivos() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.getlistaObjetivos"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				//item.setPadre(rs.getLong(i++));
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
	

	public List getListaArchivosTrimestre(FiltroSeguimientoVO filtro, String codigo, int trimestre, 
		String codigoColegio) throws Exception {
		
		int i = 0;
				
		ResultSet rs = null;
		Connection cn = null;
		List listArchPrimerTrim = new ArrayList();
		ArchivoSegVO item = null;
		PreparedStatement st = null;
		
		try {
			cn = cursor.getConnection();
			
			// Calculo de la lista
			st = cn.prepareStatement(rb.getString("planeacion.listar.archivo.trimestre"));
			
			i = 1;				
			
			st.setInt(i++, filtro.getFilVigencia());
			//st.setLong(i++, filtro.getFilNivel());
			st.setLong(i++,Long.parseLong(codigo));
			st.setLong(i++, Long.parseLong(codigoColegio));
			st.setInt(i++, trimestre);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				i = 1;				
				item = new ArchivoSegVO();
				item.setIdEvidenciaSeguimiento(rs.getBigDecimal(i++)); 
				item.setNombreArchivo(rs.getString(i++)); 
				item.setUsuarioResponsable(rs.getString(i++));
				item.setFechaCreacion(rs.getDate(i++));
				
				listArchPrimerTrim.add(item);
			}
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch(Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch(InternalErrorException inte) {}
		}
		
		return listArchPrimerTrim;
		
	}
	

	public SegFechasCompareVO compararFechaFinSeg(int vigencia) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		SegFechasCompareVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.comparar.fechas"));
			i=1;
			st.setInt(i++,vigencia);			
			
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new SegFechasCompareVO();
				item.setFechaSegFin1(rs.getDate(i++));
				item.setFechaCompareSeg1(rs.getBoolean(i++));
				item.setFechaSegFin2(rs.getDate(i++));
				item.setFechaCompareSeg2(rs.getBoolean(i++));
				item.setFechaSegFin3(rs.getDate(i++));
				item.setFechaCompareSeg3(rs.getBoolean(i++));
				item.setFechaSegFin4(rs.getDate(i++));				
				item.setFechaCompareSeg4(rs.getBoolean(i++));
				
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
		return item;
	}

	
}

