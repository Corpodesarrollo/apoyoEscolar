package siges.adminParamsAcad.service;

import java.util.List;

import org.apache.commons.validator.GenericValidator;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.dao.Cursor;

/**
 * Maneja las peticiones referentes a calculo de valores de los formularios 
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax {
	
	
	/**
	 * Objeto de acceso a datos 
	 */
	private AdminParametroInstDAO planeacionDAO=new AdminParametroInstDAO(new Cursor());
	
	public Ajax() {
	}
	
	
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaSede(String inst) throws Exception{
		
		return planeacionDAO.getSede(Long.valueOf(inst).longValue());
	}
	
	
	
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaJornada(String inst, String sede) throws Exception{
		
		return planeacionDAO.getJornada(Long.valueOf(inst).longValue(),   Long.valueOf(sede).longValue());
	}
	
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaMetodologia(String inst) throws Exception{
		
		return planeacionDAO.getMetodologia(Long.valueOf(inst).longValue());
	}
	
	//metodo que implementa el servicio descrito en el dwr.xml
	public List listaNivel(String inst, String metod) throws Exception{
		
		return planeacionDAO.getNivel(Long.valueOf(inst).longValue(), Long.valueOf(metod).longValue());
	}
	
	
	//metodo que implementa el servicio descrito en el dwr.xml
	public List listaNivel2(String inst, String metod) throws Exception{
		
		return planeacionDAO.getNivel(Long.valueOf(inst).longValue());
	}
	
	
	
	//metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado(String inst, String metod,String nvl) throws Exception{
		
		return planeacionDAO.getGrado(Long.valueOf(inst).longValue(), Long.valueOf(metod).longValue(),Long.valueOf(nvl).longValue());
	}
	
	//metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado2(String inst) throws Exception{
		
		return planeacionDAO.getGrado(Long.valueOf(inst).longValue());
	}
	
	//metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado3(String inst, String nvl) throws Exception{
		
		return planeacionDAO.getGrado(Long.valueOf(inst).longValue(),Long.valueOf(nvl).longValue());
	}
	
	//metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado4(String inst, String metodo) throws Exception{
		
		return planeacionDAO.getGradoMetodologia(Long.valueOf(inst).longValue(),Long.valueOf(metodo).longValue());
	}
	
	
	public List listaEscalaMEN() throws Exception{
		
		return planeacionDAO.getEscalaMEN();
	}
	
	
	
/////////////////////////////////
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaSede_(String inst) throws Exception{
		
		return planeacionDAO.getSede(Long.valueOf(inst).longValue());
	}
	
	
	
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaJornada_(String inst, String sede) throws Exception{
		
		return planeacionDAO.getJornada(Long.valueOf(inst).longValue(), Long.valueOf(sede).longValue());
	}
	
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaMetodologia_(String inst) throws Exception{
		
		return planeacionDAO.getMetodologia(Long.valueOf(inst).longValue());
	}
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaNivel_(String inst, String metod) throws Exception{
		
		return planeacionDAO.getNivel(Long.valueOf(inst).longValue(), Long.valueOf(metod).longValue());
	}
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaNivel2_(String inst, String metod) throws Exception{
		
		return planeacionDAO.getNivel(Long.valueOf(inst).longValue());
	}
	
	
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado_(String inst, String metod,String nvl) throws Exception{
		
		return planeacionDAO.getGrado(Long.valueOf(inst).longValue(), Long.valueOf(metod).longValue(),Long.valueOf(nvl).longValue());
	}
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado2_(String inst) throws Exception{
		
		return planeacionDAO.getGrado(Long.valueOf(inst).longValue());
	}
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado3_(String inst, String nvl) throws Exception{
		
		return planeacionDAO.getGrado(Long.valueOf(inst).longValue(),Long.valueOf(nvl).longValue());
	}
	
//	metodo que implementa el servicio descrito en el dwr.xml
	public List listaGrado4_(String inst, String metodo) throws Exception{
		
		return planeacionDAO.getGradoMetodologia(Long.valueOf(inst).longValue(),Long.valueOf(metodo).longValue());
	}
	
	public List listaEscalaInst_(String vig, String inst,String niveval, String sede, String jornd, String metodo, String nivel , String grado) throws Exception{
		
		return planeacionDAO.getListaEscalaInst_(getLong(vig),getLong(inst),getLong(niveval),getLong(sede),getLong(jornd),getLong(metodo),getLong(nivel),getLong(grado));
	}
	
	private long getLong(String str){
		return GenericValidator.isLong(str)? Long.valueOf(str).longValue(): -99;
	}
}
