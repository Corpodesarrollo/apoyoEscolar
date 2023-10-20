package poa.seguimiento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.seguimiento.vo.ArchivoSegVO;
import poa.seguimiento.vo.FiltroSeguimientoVO;
import poa.seguimiento.vo.ParamsVO;
import poa.seguimiento.vo.SegFechasCompareVO;
import poa.seguimiento.vo.SeguimientoVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el modulo de seguimiento 14/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class SeguimientoDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public SeguimientoDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("poa.seguimiento.bundle.Seguimiento");
	}

	/**
	 * Calcula la lista de areas de gestinn
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de lineas de accinn de un area de gestinn
	 * 
	 * @param areaGestion
	 *            codigo del area de gestinn
	 * @return Lista de lineas de accinn
	 * @throws Exception
	 */
	public List getListaLineaAccion(int areaGestion) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaLineaAccion"));
			st.setInt(i++, areaGestion);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * calcula la lista de actividades con recursos del filtro de busqueda
	 * especificado
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return lista de actividades con recursos
	 * @throws Exception
	 */
	public List getListaActividades(FiltroSeguimientoVO filtro) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		List l = new ArrayList();
		SeguimientoVO item = null;
		
		int i = 0;
		int cons = 1;
		
		String calif;
		
		try {
			
			cn = cursor.getConnection();
			
			// Consultar si se puede aprobar
			st = cn.prepareStatement(rb.getString("poa.getEstado"));
			
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				filtro.setFilEstatoPOA(rs.getInt(1));
			} else {
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
			}
			
			rs.close();
			st.close();
			
			if (filtro.getFilEstatoPOA() != ParamPOA.ESTADO_POA_APROBADO_SED) {
				throw new Exception("El POA no ha sido aprobado por la Secretar�a De Educaci�n");
			}
			
			// C�lculo de la lista
			st = cn.prepareStatement(rb.getString("planeacion.lista2"));
			
			i = 1;
			
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilVigencia());
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				
				i = 1;
				item = new SeguimientoVO();
				// PLACVIGENCIA, PLACCODINST, PLACCODIGO,
				// substr(PLACOBJETIVO,0,100), substr(PLACNOMBRE,0,100),
				// ARGENOMBRE, , , , , , ,
				item.setPlaConsecutivo(cons++);
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestionNombre(rs.getString(i++));
				item.setPlaLineaAccionNombre(rs.getString(i++));
				item.setPlaSeguimiento1(rs.getString(i++));// LIACNOMBRE
				item.setPlaSeguimiento2(rs.getString(i++));// SEGPERIODO1
				item.setPlaSeguimiento3(rs.getString(i++));// SEGPERIODO2
				item.setPlaSeguimiento4(rs.getString(i++));// SEGPERIODO3
				// item.setPlaSeguimiento4(rs.getString(i++));//SEGPERIODO4
				item.setPlaccodobjetivo(rs.getInt(i++)); // PLACCODOBJETIVO
				item.setPlaccodobjetivoText(rs.getString(i++));// OBJNOMBRE
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				item.setSEGFECHAREALCUMPLIM(rs.getString(i++));
				item.setPRESUPUESTOEJECUTADO(rs.getInt(i++));
				
				calif = rs.getString(i++);				
				item.setCalifActividad(calif);
				
				l.add(item);
				
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
				
			} catch (InternalErrorException inte) {
			}
		}
		
		return l;
		
	}
	
	
	public List getListaActividadesAll(FiltroSeguimientoVO filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		SeguimientoVO item = null;
		int i = 0;
		int cons = 1;
		try {
			cn = cursor.getConnection();
			// saber si se puede aprobar
			st = cn.prepareStatement(rb.getString("poa.getEstado"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilEstatoPOA(rs.getInt(1));
			} else {
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
			}
			rs.close();
			st.close();
			// calculo de la lista
			st = cn.prepareStatement(rb.getString("planeacion.lista2"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new SeguimientoVO();
				// PLACVIGENCIA, PLACCODINST, PLACCODIGO,
				// substr(PLACOBJETIVO,0,100), substr(PLACNOMBRE,0,100),
				// ARGENOMBRE, , , , , , ,
				item.setPlaConsecutivo(cons++);
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestionNombre(rs.getString(i++));
				item.setPlaLineaAccionNombre(rs.getString(i++));
				item.setPlaSeguimiento1(rs.getString(i++));// LIACNOMBRE
				item.setPlaSeguimiento2(rs.getString(i++));// SEGPERIODO1
				item.setPlaSeguimiento3(rs.getString(i++));// SEGPERIODO2
				item.setPlaSeguimiento4(rs.getString(i++));// SEGPERIODO3
				// item.setPlaSeguimiento4(rs.getString(i++));//SEGPERIODO4
				item.setPlaccodobjetivo(rs.getInt(i++)); // PLACCODOBJETIVO
				item.setPlaccodobjetivoText(rs.getString(i++));// OBJNOMBRE
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				item.setSEGFECHAREALCUMPLIM(rs.getString(i++));
				item.setPRESUPUESTOEJECUTADO(rs.getInt(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Obtiene la actividad con recursos seleccionada
	 * 
	 * @param vigencia
	 * @param institucion
	 * @param codigo
	 * @param estadoPOA
	 * @param habil
	 * @param periodo
	 * @return Actividad para seguimiento
	 * @throws Exception
	 */
	public SeguimientoVO getActividad(int vigencia, long institucion, long codigo, int estadoPOA, boolean habil, int periodo) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		SeguimientoVO item = null;
		
		int i = 0;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.obtener2"));
			
			i = 1;
			
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
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
				item.setPlaSeguimiento1(rs.getString(i++)); //18
				item.setPlaSeguimiento2(rs.getString(i++));
				item.setPlaSeguimiento3(rs.getString(i++));
				item.setPlaSeguimiento4(rs.getString(i++));
				item.setPlaPorcentaje1(rs.getString(i++));//22
				item.setPlaPorcentaje2(rs.getString(i++));
				item.setPlaPorcentaje3(rs.getString(i++));
				item.setPlaPorcentaje4(rs.getString(i++));
				item.setPlaVerificacion1(removeWhiteSpace(rs.getString(i++)));
				item.setPlaLogros1(removeWhiteSpace(rs.getString(i++)));
				item.setPlaVerificacion2(removeWhiteSpace(rs.getString(i++)));
				item.setPlaLogros2(removeWhiteSpace(rs.getString(i++)));
				item.setPlaVerificacion3(removeWhiteSpace(rs.getString(i++)));
				item.setPlaLogros3(removeWhiteSpace(rs.getString(i++)));
				item.setPlaVerificacion4(removeWhiteSpace(rs.getString(i++)));
				item.setPlaLogros4(removeWhiteSpace(rs.getString(i++)));
				item.setPlaDisabled("disabled");
				
				if(item.getPlaVigencia() == getVigenciaNumericoPOA()){
					item.setPlaDisabled1(periodo == 1 ? null : "disabled");
					item.setPlaDisabled2(periodo == 2 ? null : "disabled");
					item.setPlaDisabled3(periodo == 3 ? null : "disabled");
					item.setPlaDisabled4(periodo == 4 ? null : "disabled");
				} else {
					item.setPlaDisabled1("disabled");
					item.setPlaDisabled2("disabled");
					item.setPlaDisabled3("disabled");
					item.setPlaDisabled4("disabled");
				}
				
				item.setPlaDesHabilitado(true);
				item.setPlaMostrarPorcentaje(rs.getInt(i++) == 1 ? true : false);			

				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				
				i = 37;
				item.setACCIONMEJORAMIENTO(rs.getString(i++));
				item.setTIPOGASTO(rs.getString(i++));
				item.setRUBROGASTO(rs.getString(i++));
				
				if(item.getTIPOGASTO() != null) {
					
					if(item.getTIPOGASTO().equalsIgnoreCase("Inversion")) {
						item.setPROYECTOINVERSION(rs.getString(i++));
					} else if(item.getTIPOGASTO().equalsIgnoreCase("Funcionamiento")) {
						item.setSUBNIVELGASTO(rs.getString(i++));
					} else{
						i++;
					}
					
				} else {
					
					i++;
					
				}
				
				item.setFUENTEFINANCIACION(rs.getString(i++));				
				item.setMONTOANUAL(rs.getLong(i++));
				item.setPPTOPARTICIPATIVO(rs.getLong(i++));
				
				
				String tipoA = rs.getString(i++);
				
				if(tipoA != null)
					item.setTIPOACTIVIDAD(tipoA.trim());
				
				i = 45;
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				item.setSEGFECHAREALCUMPLIM(rs.getString(i++));
				item.setPRESUPUESTOEJECUTADO1(rs.getInt(i++));
				item.setPRESUPUESTOEJECUTADO2(rs.getInt(i++));
				item.setPRESUPUESTOEJECUTADO3(rs.getInt(i++));
				item.setPRESUPUESTOEJECUTADO4(rs.getInt(i++));
				
				String otras = rs.getString(i++);
				
				if(otras != null)
					item.setCUAL(otras.trim());
				item.setPlaPonderador(rs.getInt(i++));
				
				item.setRESPONSABLE(rs.getString(i++));
				item.setPLACOTROCUAL(rs.getString(i++));
				
				item.setFechaModificacion1(rs.getDate(i++));
				item.setFechaModificacion2(rs.getDate(i++));
				item.setFechaModificacion3(rs.getDate(i++));
				item.setFechaModificacion4(rs.getDate(i++));
				item.setUsuarioModificacion1(rs.getString(i++));
				item.setUsuarioModificacion2(rs.getString(i++));
				item.setUsuarioModificacion3(rs.getString(i++));
				item.setUsuarioModificacion4(rs.getString(i++));
				
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
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return item;
		
	}
	
	
	/**
	 * Dado un string retorna una cadena sin espacios en blanco 
	 * @param string
	 * @return
	 */
	private String removeWhiteSpace(String string){
		if(string != null)
			return string.trim();
		else
			return string;
	}
	
	public SeguimientoVO getActividadPOA(int vigencia, long institucion,
			long codigo, int estadoPOA, boolean habil, int periodo)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		SeguimientoVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.obtener2"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
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
				item.setPlaDisabled1(periodo == 1 ? null : "disabled");
				item.setPlaDisabled2(periodo == 2 ? null : "disabled");
				item.setPlaDisabled3(periodo == 3 ? null : "disabled");
				item.setPlaDisabled4(periodo == 4 ? null : "disabled");
				item.setPlaDesHabilitado(true);
				item.setPlaMostrarPorcentaje(rs.getInt(i++) == 1 ? true : false);			

				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				// System.out.println("valor de mostrar porcentaje: "+item.isPlaMostrarPorcentaje());
				
				i = 37;
				item.setACCIONMEJORAMIENTO(rs.getString(i++));
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
				item.setMONTOANUAL(rs.getInt(i++));
				item.setPPTOPARTICIPATIVO(rs.getInt(i++));
				
				
				String tipoA = rs.getString(i++);
				if(tipoA != null)
					item.setTIPOACTIVIDAD(tipoA.trim());
				
				i = 45;
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				item.setSEGFECHAREALCUMPLIM(rs.getString(i++));
				item.setPRESUPUESTOEJECUTADO(rs.getInt(i++));
				String otras = rs.getString(i++);
				if(otras != null)
					item.setCUAL(otras.trim());
				item.setPlaPonderador(rs.getInt(i++));
			}
			rs.close();
			st.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Actualiza el seguimiento de la actividad
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public SeguimientoVO actualizarActividad(SeguimientoVO item, FiltroSeguimientoVO filtro) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		int i = 0;
		int tipo = 0;
		float at = 0;
	
		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			// Validar el tipo de meta
			st = cn.prepareStatement(rb.getString("planeacion.tipoMeta"));
			
			i = 1;
			
			st.setInt(i++, item.getPlaTipoMeta());
			rs = st.executeQuery();
			
			if (rs.next()) {
				tipo = rs.getInt(1);
			}
			
			rs.close();
			st.close();
			
			st = cn.prepareStatement(rb.getString("planeacion.actualizar"));
			i = 1;
			
			if (tipo == 0) {// es diferente a demanda
				if (item.getPlaSeguimiento1() != null && !item.getPlaSeguimiento1().equals("")) {
					at = at + Float.parseFloat(item.getPlaSeguimiento1());
				}
				
				if (item.getPlaSeguimiento2() != null && !item.getPlaSeguimiento2().equals("")) {
					at = at + Float.parseFloat(item.getPlaSeguimiento2());
				}
				
				if (item.getPlaSeguimiento3() != null && !item.getPlaSeguimiento3().equals("")) {
					at = at + Float.parseFloat(item.getPlaSeguimiento3());
				}
				
				if (item.getPlaSeguimiento4() != null && !item.getPlaSeguimiento4().equals("")) {
					at = at + Float.parseFloat(item.getPlaSeguimiento4());
				}
				
				//JORGE				
				if (filtro.getFilPeriodoHabil() == 1) {
					item.setPlaPorcentaje1(String.valueOf((at / item.getPlaCantidad()) * 100));
				}
				
				if (filtro.getFilPeriodoHabil() == 2) {
					item.setPlaPorcentaje2(String.valueOf((at / item.getPlaCantidad()) * 100));
				}
				
				if (filtro.getFilPeriodoHabil() == 3) {
					item.setPlaPorcentaje3(String.valueOf((at / item.getPlaCantidad()) * 100));
				}
				
				if (filtro.getFilPeriodoHabil() == 4) {
					item.setPlaPorcentaje4(String.valueOf((at / item.getPlaCantidad()) * 100));
				}
				
				/**
				 * Si no es de tipo demanda, todos los campos de demanda deben ponerse en cero
				 * modificado: 2013-10-30
				 * mcoral
				 */
				st.setString(i++, "");
				st.setString(i++, "");
				st.setString(i++, "");
				st.setString(i++, "");
				
			} else { //Es demanda
				st = cn.prepareStatement(rb.getString("planeacion.actualizarDemanda"));
				st.setString(i++, item.getPlaPorcentaje1());
				st.setString(i++, item.getPlaPorcentaje2());
				st.setString(i++, item.getPlaPorcentaje3());
				st.setString(i++, item.getPlaPorcentaje4());
			}
			
			st.setString(i++, item.getSEGFECHACUMPLIMT());
			st.setString(i++, item.getSEGFECHAREALCUMPLIM());
			st.setInt(i++, item.getPRESUPUESTOEJECUTADO1());
			st.setInt(i++, item.getPRESUPUESTOEJECUTADO2());
			st.setInt(i++, item.getPRESUPUESTOEJECUTADO3());
			st.setInt(i++, item.getPRESUPUESTOEJECUTADO4());
			st.setString(i++, item.getPlaSeguimiento1());
			st.setString(i++, item.getPlaSeguimiento2());
			st.setString(i++, item.getPlaSeguimiento3());
			st.setString(i++, item.getPlaSeguimiento4());
			st.setString(i++, item.getPlaSeguimiento1());
			st.setString(i++, item.getPlaSeguimiento2());
			st.setString(i++, item.getPlaSeguimiento3());
			st.setString(i++, item.getPlaSeguimiento4());
			st.setString(i++, item.getPlaVerificacion1());
			st.setString(i++, item.getPlaVerificacion2());
			st.setString(i++, item.getPlaVerificacion3());
			st.setString(i++, item.getPlaVerificacion4());
			st.setString(i++, item.getPlaLogros1());
			st.setString(i++, item.getPlaLogros2());
			st.setString(i++, item.getPlaLogros3());
			st.setString(i++, item.getPlaLogros4());
			
			//"Primer trimestre. ","Segundo trimestre. ","Tercer trimestre. ","Cuarto trimestre. "
			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");				
			//String fechaActualSys = sdf.format(new Date());	
			Date fechaActualSys2 = new Date();	
			String usuTemp1 = "";
			String usuTemp2 = "";
			String usuTemp3 = "";
			String usuTemp4 = "";
			
			java.sql.Date fechaModificacion1 = null;
			java.sql.Date fechaModificacion2 = null;
			java.sql.Date fechaModificacion3 = null;
			java.sql.Date fechaModificacion4 = null;
			
			if(item.getFechaModificacion1() != null){
				
				fechaModificacion1 = new java.sql.Date(item.getFechaModificacion1().getTime());				
			}
			if(item.getFechaModificacion2() != null){
				
				fechaModificacion2 = new java.sql.Date(item.getFechaModificacion2().getTime());				
			}
			if(item.getFechaModificacion3() != null){
							
				fechaModificacion3 = new java.sql.Date(item.getFechaModificacion3().getTime());				
			}
			if(item.getFechaModificacion4() != null){
				
				fechaModificacion4 = new java.sql.Date(item.getFechaModificacion4().getTime());				
			}
			
			if(item.getUsuarioModificacion1() != null){
				
				usuTemp1 = item.getUsuarioModificacion1();				
			}
			if(item.getUsuarioModificacion2() != null){
							
				usuTemp2 = item.getUsuarioModificacion2();				
			}
			if(item.getUsuarioModificacion3() != null){
				
				usuTemp3 = item.getUsuarioModificacion3();				
			}
			if(item.getUsuarioModificacion4() != null){
				
				usuTemp4 = item.getUsuarioModificacion4();				
			}
			
			if (filtro.getFilRangoFechas2().trim().equals("Primer trimestre.")) {
				item.setFechaModificacion1(fechaActualSys2);
				usuTemp1 = item.getUsuarioSesion();
				fechaModificacion1 = new java.sql.Date(item.getFechaModificacion1() == null ? null: item.getFechaModificacion1().getTime());
			

			} else if (filtro.getFilRangoFechas2().trim().equals("Segundo trimestre.")) {
				item.setFechaModificacion2(fechaActualSys2);
				usuTemp2 = item.getUsuarioSesion();

				fechaModificacion2 = new java.sql.Date(item.getFechaModificacion2() == null ? null: item.getFechaModificacion2().getTime());
				

			} else if (filtro.getFilRangoFechas2().trim().equals("Tercer trimestre.")) {
				item.setFechaModificacion3(fechaActualSys2);
				usuTemp3 = item.getUsuarioSesion();
			
				fechaModificacion3 = new java.sql.Date(item.getFechaModificacion3() == null ? null: item.getFechaModificacion3().getTime());
	

			} else if (filtro.getFilRangoFechas2().trim().equals("Cuarto trimestre.")) {
				item.setFechaModificacion4(fechaActualSys2);
				usuTemp4 = item.getUsuarioSesion();
				
				fechaModificacion4 = new java.sql.Date(item.getFechaModificacion4() == null ? null: item.getFechaModificacion4().getTime());

			} else {
				item.setFechaModificacion1(null);
				item.setFechaModificacion2(null);
				item.setFechaModificacion3(null);
				item.setFechaModificacion4(null);
			}
			
			/*st.setString(i++, item.getFechaModificacion1());
			st.setString(i++, item.getFechaModificacion2());
			st.setString(i++, item.getFechaModificacion3());
			st.setString(i++, item.getFechaModificacion4());*/

			st.setDate(i++, fechaModificacion1);
			st.setDate(i++, fechaModificacion2);
			st.setDate(i++, fechaModificacion3);
			st.setDate(i++, fechaModificacion4);
			
			st.setString(i++, usuTemp1);
			st.setString(i++, usuTemp2);
			st.setString(i++, usuTemp3);
			st.setString(i++, usuTemp4);
			
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());

			st.executeUpdate();
			
			cn.commit();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return item;
		
	}

	
	/**
	 * Calcula la lista de anos de vigencia
	 * 
	 * @return Lista de anhos de vigencia
	 * @throws Exception
	 */
	public List getListaVigenciaActual() throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ItemVO itemVO = null;
		List listaVigencia = new ArrayList();
		
		
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("planeacion.listaVigencia"));
			rs = pst.executeQuery();
			if (rs.next()) {
				itemVO = new ItemVO(rs.getInt(1), String.valueOf(rs.getInt(1)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(2), String.valueOf(rs.getInt(2)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(3), String.valueOf(rs.getInt(3)));
				listaVigencia.add(itemVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaVigencia;
	}

	/**
	 * Obtiene los comentarios de los campos de las tablas de actividad con
	 * recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public FiltroSeguimientoVO getLabels(FiltroSeguimientoVO filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (filtro == null) {
			filtro = new FiltroSeguimientoVO();
			filtro.setFilVigencia((int) getVigenciaNumericoPOA());
			filtro.setFilVigenciaPoa(String.valueOf(getVigenciaNumericoPOA()));
		}
		boolean band = false;
		String periodo[] = { "", "Primer trimestre. ", "Segundo trimestre. ",
				"Tercer trimestre. ", "Cuarto trimestre. " };
		try {

			cn = cursor.getConnection();
			// calcular si es fecha habil para poder guardar actividades
			for (int j = 1; j <= 4; j++) {
				pst = cn.prepareStatement(rb.getString("planeacion.rangoFecha."
						+ j));
				pst.setInt(1, filtro.getFilVigencia());
				rs = pst.executeQuery();
				if (rs.next()) {
					if (rs.getInt(3) == 1) {
						band = true;
						filtro.setFilRangoFechas(periodo[j] + "[ "
								+ rs.getString(1) + " - " + rs.getString(2)
								+ " ]");
						filtro.setFilRangoFechas2(periodo[j]);
						filtro.setFilFechaHabil(true);
						filtro.setFilPeriodoHabil(j);
						rs.close();
						pst.close();
						break;
					}
				}
				rs.close();
				pst.close();
			}
			if (band == false) {
				filtro.setFilRangoFechas("No hay trimestres por llenar");
				filtro.setFilFechaHabil(false);
			}
			pst = cn.prepareStatement(rb.getString("planeacion.label"));
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACVIGENCIA)) {
					filtro.setLblVigencia(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACOBJETIVO)) {
					filtro.setLblObjetivo(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRE)) {
					filtro.setLblActividad(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODARGESTION)) {
					filtro.setLblAreaGestion(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODLIACCION)) {
					filtro.setLblLineaAccion(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACFECHATERMIN)) {
					filtro.setLblFecha(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO1)) {
					filtro.setLblCronograma1(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO2)) {
					filtro.setLblCronograma2(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO3)) {
					filtro.setLblCronograma3(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO4)) {
					filtro.setLblCronograma4(rs.getString(2));
					continue;
				}

				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO1)) {
					filtro.setLblSeguimiento1(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO2)) {
					filtro.setLblSeguimiento2(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO3)) {
					filtro.setLblSeguimiento3(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACSEGUIMIENTO4)) {
					filtro.setLblSeguimiento4(rs.getString(2));
					continue;
				}

				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE1)) {
					filtro.setLblPorcentaje1(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE2)) {
					filtro.setLblPorcentaje2(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE3)) {
					filtro.setLblPorcentaje3(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPORCENTAJE4)) {
					filtro.setLblPorcentaje4(rs.getString(2));
					continue;
				}

				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION1)) {
					filtro.setLblVerificacion1(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION2)) {
					filtro.setLblVerificacion2(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION3)) {
					filtro.setLblVerificacion3(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACVERIFICACION4)) {
					filtro.setLblVerificacion4(rs.getString(2));
					continue;
				}

				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO1)) {
					filtro.setLblLogros1(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO2)) {
					filtro.setLblLogros2(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO3)) {
					filtro.setLblLogros3(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACLOGRO4)) {
					filtro.setLblLogros4(rs.getString(2));
					continue;
				}
			}
			rs.close();
			pst.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return filtro;
	}

	/**
	 * Calcula la lista de areas de gestinn
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion(int vigencia, long institucion, long codigo)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre((rs.getLong(i++))
						- getPonderadoArea(cn, vigencia, institucion, codigo,
								item.getCodigo()));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula el ponderado del area indicada
	 * 
	 * @param cn
	 * @param vig
	 * @param inst
	 * @param codigo
	 * @param area
	 * @return ponderado del area indicada
	 * @throws Exception
	 */
	public int getPonderadoArea(Connection cn, int vig, long inst, long codigo,
			long area) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			st = cn.prepareStatement(rb
					.getString("planeacion.getPonderadoArea"));
			i = 1;
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setLong(i++, codigo);
			st.setLong(i++, area);
			rs = st.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	/**
	 * Calcula la lista de tipos de meta
	 * 
	 * @return Lista de tipos de meta
	 * @throws Exception
	 */
	public List getListaTipoMeta() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.listaTipoMeta"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de unidades de medida
	 * 
	 * @return lista de unidades de medida
	 * @throws Exception
	 */
	public List getListaUnidadMedida() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaUnidadMedida"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getlistaObjetivos() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.getlistaObjetivos"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				// item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
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
