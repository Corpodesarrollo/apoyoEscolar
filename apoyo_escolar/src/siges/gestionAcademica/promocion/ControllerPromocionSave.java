package siges.gestionAcademica.promocion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import articulacion.apcierArtic.vo.ParamsVO;

import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.gestionAcademica.promocion.beans.FiltroPromocion;
import siges.gestionAcademica.promocion.dao.PromocionDAO;
import siges.gestionAdministrativa.cierreVigencia.dao.CierreVigenciaDAO;
import siges.gestionAdministrativa.cierreVigencia.vo.ItemVO;
import siges.util.Acceso;
import siges.util.Logger;
import util.BitacoraCOM;
import util.LogPromocionDto;

/**
*	Nombre:	ControllerEvaluacionSave<BR>
*	Descripcinn:	Controla la negociacinn para buscar los datos de evaluacion e ingresar y actualizar la evaluacinn en linea de 	logros, descriptores,areas,asignaturas, promocion <br>
*	Funciones de la pngina:	Ingresa a la bese de datos para buscar los estudiantes a evaluar y para ingresar las notas dadas por el usuario en el formulario de evaluacinn<BR>
*	Entidades afectadas:	Todas las tablas de evaluacion y promocion de los estudiantes	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class ControllerPromocionSave extends HttpServlet{
	private String mensaje;
	private String buscar;
	private boolean err;
	private Cursor cursor;
	private PromocionDAO promocionDAO;//
	private ResourceBundle rb;
	private int z;
	private CierreVigenciaDAO cierreVigenciaDAO=new CierreVigenciaDAO(new Cursor());
	private Login usuVO;
	private PromocionDAO evaluacionDAO;

	/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	  HttpSession session= request.getSession();
	  Login login=null;
	  FiltroPromocion filtroEvaluacion=null;
	  evaluacionDAO=new PromocionDAO(cursor);
	  int tipo;
		rb=ResourceBundle.getBundle("siges.gestionAcademica.promocion.bundle.promocion");		
		String sig;
		String ant;
		String er;
		String home;
		String sentencia;
		String boton;
		String id;
		String sig1;
		String sig2;
		String sig3;
		String FICHA_CONSULTAR_CIERRE=getServletConfig().getInitParameter("FICHA_CONSULTAR_CIERRE");
		sig3=getServletConfig().getInitParameter("sig3");
		sig1=getServletConfig().getInitParameter("sig1");
		sig2=getServletConfig().getInitParameter("sig2");
		sig=getServletConfig().getInitParameter("sig");
		ant=getServletConfig().getInitParameter("ant");
		er=getServletContext().getInitParameter("error");
		home=getServletContext().getInitParameter("home");
		cursor=new Cursor();
		err=false;
		mensaje=null;
		promocionDAO=new PromocionDAO(cursor);
		try{
		boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesi�n para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		//traer de session los beans de datos
		login=(Login)session.getAttribute("login");
		request.setAttribute("Vigenciaact", Long.toString(login.getVigencia_inst()));
		request.setAttribute("Validaviginst", login.getInstId());
		validarvigprom(request,login.getInstId());
		request.setAttribute("ValidaGuardar", Long.toString(login.getVigencia_inst()));
		filtroEvaluacion=(FiltroPromocion)session.getAttribute("filtroPromocion");
		filtroEvaluacion.setMunicipio(login.getMunId());
		filtroEvaluacion.setLocalidad(login.getLocId());
		filtroEvaluacion.setInstitucion(login.getInstId());
		filtroEvaluacion.setJornada(login.getJornadaId());
		filtroEvaluacion.setSede(login.getSedeId());
		//
		//System.out.println("-SIGES-"+(login!=null?login.getUsuarioId():"")+"Recepcion Promocion Save");
		if(request.getParameter("tipo")==null || request.getParameter("tipo").equals("")){
			setMensaje("Acceso denegado no hay una ficha definida");
			request.setAttribute("mensaje",getMensaje());
			return er;
		}
		tipo=Integer.parseInt((String)request.getParameter("tipo"));
		usuVO = (Login) session.getAttribute("login");
		switch(tipo){
		case 2:
			cargarFiltro(request);
			actualVigencia(request,usuVO);
			String np=Long.toString(usuVO.getLogNumPer());
			validarcierre(request,usuVO.getInstId(),Long.toString(usuVO.getVigencia_inst()),np);
			return sig2;
		case 4:
			String ins=usuVO.getInstId();
			cargarFiltro2(request,ins);
			return FICHA_CONSULTAR_CIERRE;
		}
		if(boton.equals("Buscar")){
			switch(tipo){
				case 1://logro
			    asignarListaPromocion(request,login,filtroEvaluacion);
				  return (sig);
			}
    }
		if(boton.equals("Aceptar")){
			switch(tipo){
				case 1://logros
					if(!insertarPromocion(request,login,filtroEvaluacion)){
						System.out.println("mensaje " + mensaje);
						request.setAttribute("mensaje",mensaje);
					}else{
						request.setAttribute("mensaje","Los datos fuernn registrados satisfactoriamente");
					}
					return sig;
			}
    }
		if(boton.equals("Cancelar")){
			borrarBeans(request);
			sig=home;
    }
		}catch(Exception e){e.printStackTrace();}
		return sig;
	}
	
	private void cargarFiltro2(HttpServletRequest request,String ins) throws ServletException{
		List listaVigencia = new ArrayList();
		long codInst =  0;
		int parVigencia = 0;
		try{
			
			codInst = Long.parseLong(ins);
			parVigencia = cierreVigenciaDAO.getVigenciaInst( codInst );
			ItemVO itemVO = new  ItemVO(parVigencia,parVigencia+"");
			listaVigencia.add(itemVO);
			request.setAttribute("listaVigencia2", listaVigencia);
			List listaTablaHilo = cierreVigenciaDAO.getCierreHilosVO(codInst,usuVO.getUsuarioId());
			request.setAttribute("listaTablaHilo2",listaTablaHilo);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println( e.getMessage() );
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		
	}
	
	private void actualVigencia(HttpServletRequest request, Login usuVO) throws ServletException{
		System.out.println(new Date() + " - actualVigencia" );
		try{
			
			
			request.setAttribute("perVigencia", ""+cierreVigenciaDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())) );
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println( e.getMessage() );
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		
	}
	
	private void cargarFiltro(HttpServletRequest request) throws ServletException{
		List listaVigencia = new ArrayList();
		long codInst =  0;
		int parVigencia = 0;
		try{
			
			codInst = Long.parseLong(usuVO.getInstId());
			parVigencia = cierreVigenciaDAO.getVigenciaInst( codInst );
			ItemVO itemVO = new  ItemVO(parVigencia,parVigencia+"");
			listaVigencia.add(itemVO);
			request.setAttribute("listaVigencia", listaVigencia);
			List listaTablaHilo = cierreVigenciaDAO.getCierreHilosVO(codInst,usuVO.getUsuarioId());
			request.setAttribute("listaTablaHilo",listaTablaHilo);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println( e.getMessage() );
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		
	}
	
	public void validarvigprom(HttpServletRequest req, String ins){
		try{
		Collection colgg[] = evaluacionDAO.getEstadoPromo(ins);
		req.setAttribute("Validavig", colgg[0]);
		}catch(Exception e){
			System.out.println("error validar vigencia promocion");
		}
	}
	
	public void validarcierre(HttpServletRequest req, String ins,String vig,String np) {
		Collection colgg[] = Acceso.getvalidaciones(ins, vig);
		req.setAttribute("Estudiantesn", colgg[0]);
		req.setAttribute("Estudiantesp", colgg[1]);
		req.setAttribute("Periodosa", colgg[2]);
		req.setAttribute("nperiodos", np);
	}

	/**
	 *	Busca los estudiantes para la promocinn y pone los datos en la session de usuario
	 *	@param HttpServletRequest request
	 */
	public void asignarListaPromocion(HttpServletRequest request,Login login,FiltroPromocion filtroEvaluacion)throws ServletException, IOException{
		System.out.println(new Date() + ":: asignarListaPromocion");
		String s;
		HttpSession session= request.getSession();
		Collection[] col=new Collection[4];
		try{  
			filtroEvaluacion.setVigencia(promocionDAO.getVigencia());
			//ESTUDIANTES
			String grupo=	filtroEvaluacion.getGrupo().substring(filtroEvaluacion.getGrupo().lastIndexOf("|")+1,filtroEvaluacion.getGrupo().length());
			col[0]=promocionDAO.getEstudiantes(filtroEvaluacion);
			session.setAttribute("filtroResultado",col[0]);
			//JERARQUIA DEL GRUPO AL QUE PERTENECE
			filtroEvaluacion.setJerarquiagrupo(promocionDAO.getJerarquiaGrupo(col[0]));
			//NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE AREA
			col[2]=promocionDAO.getNotasProm(filtroEvaluacion);
			
			int[] zz={0,1};
			
			filtroEvaluacion.setNota(promocionDAO.getFiltroArray(col[2],zz));
			filtroEvaluacion.setActualizar(filtroEvaluacion.getNota());
			
			zz[0]=0;
			zz[1]=2; 
			filtroEvaluacion.setMotivo(promocionDAO.getFiltroArray(col[2],zz));	
			
			zz[0]=0;
			zz[1]=3; 
			filtroEvaluacion.setfecha_estado(promocionDAO.getFiltroArray(col[2],zz));	
			
			Logger.print(login.getUsuarioId(),"Promocinn Inst:"+filtroEvaluacion.getInstitucion()+" Sede:"+filtroEvaluacion.getSede()+" Jorn:"+filtroEvaluacion.getJornada()+" Gra:"+filtroEvaluacion.getGrado()+" Grupo:"+grupo,2,1,this.toString());
		}catch(Exception th){
			System.out.println("th " + th.getMessage());
		  th.printStackTrace();
			throw new	ServletException(th);
		}
	}

	/**
	 *	Ingresa o actualiza la promocinn de los estudiantes del formulario de promocion
	 *	@param HttpServletRequest request
	 *	@return boolean
 */
	public boolean insertarPromocion(HttpServletRequest request,Login login,FiltroPromocion filtroEvaluacion)throws ServletException, IOException{
		System.out.println(new Date() + ":: insertarPromocion+" );
		Collection[] col=new Collection[6];
		int[] zz = {0,1};
		try{
		
			
			
			col[2]=promocionDAO.getNotasProm(filtroEvaluacion);
			filtroEvaluacion.setActualizar(promocionDAO.getFiltroArray(col[2],zz));
			
			
			String validar1 =  promocionDAO.validarTipoPromocion(filtroEvaluacion);
			if(validar1.trim().length() > 0 ){
				System.out.println("validacion 1 " + validar1);
				request.setAttribute("mensaje",validar1);
				setMensaje(validar1);
				return false;
			}
			
			
			String validar2 =  promocionDAO.validarTipoSemes2(filtroEvaluacion);
			if(validar2.trim().length() > 0 ){
				System.out.println("validacion 2 " + validar2);
				request.setAttribute("mensaje",validar2);
				setMensaje(validar2);
				return false;
			}
			
			if(!promocionDAO.insertarEvalPromocion(filtroEvaluacion)){			
				setMensaje(promocionDAO.getMensaje());
				System.out.println("información registrada satisfactoriamente.");
				request.setAttribute("mensaje","información registrada satisfactoriamente.");
				return false;
			} 
	 
			String grupo=	filtroEvaluacion.getGrupo().substring(filtroEvaluacion.getGrupo().lastIndexOf("|")+1,filtroEvaluacion.getGrupo().length());
			//NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE AREA
			col[2]=promocionDAO.getNotasProm(filtroEvaluacion);
			col[5]=promocionDAO.getTipoPromocion( );
			
			zz[0]=0;
			zz[1]=1; 
			filtroEvaluacion.setNota(promocionDAO.getFiltroArray(col[2],zz));
			
			//filtroEvaluacion.setSemestres(promocionDAO.getFiltroArray(col[5],zz));
			
			filtroEvaluacion.setActualizar(filtroEvaluacion.getNota());
			zz[0]=0;
			zz[1]=2; 
			filtroEvaluacion.setMotivo(promocionDAO.getFiltroArray(col[2],zz));
			zz[0]=0;
			zz[1]=3; 
			filtroEvaluacion.setfecha_estado(promocionDAO.getFiltroArray(col[2],zz));

			
			Logger.print(login.getUsuarioId(),"Promocinn Inst:"+filtroEvaluacion.getInstitucion()+" Sede:"+filtroEvaluacion.getSede()+" Jorn:"+filtroEvaluacion.getJornada()+" Gra:"+filtroEvaluacion.getGrado()+" Grupo:"+grupo,6,1,this.toString());
			try {
				LogPromocionDto logPeriodo= new LogPromocionDto();
				logPeriodo.setInstitucion(login.getInst());
				logPeriodo.setSede(login.getSede());
				logPeriodo.setJornada(login.getJornada());
				logPeriodo.setAsignatura(filtroEvaluacion.getAsignatura());
				logPeriodo.setPorcentaje(filtroEvaluacion.getPorcentaje()[0]);
				logPeriodo.setArea(filtroEvaluacion.getArea());				
				BitacoraCOM.insertarBitacora(
						Long.parseLong(login.getInstId()), 
						Integer.parseInt(login.getJornada()),
						4 ,
						login.getPerfil(), 
						Integer.parseInt(login.getSedeId()),
						1112, 
						2, login.getUsuario(), new Gson().toJson(logPeriodo));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		 }catch(Exception e){ 
			 System.out.println("e.getMessage() " + e.getMessage());
		    setMensaje(e.getMessage());
		    return false;	
		 }
		return true;
	}
	/**
 * Elimina del contexto de la sesinn los beans de información del formulario de evaluacinn
 *	@param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request){
	   request.getSession().removeAttribute("filtroEvaluacion");     	
	}

/**
 *	Trae de sesion los datos de la credencial de acceso del usuario y del formulario de filtro de busqueda y evaluacion de logros,etc
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{
	  HttpSession session= request.getSession();
	  if(session.getAttribute("login")==null){return false;}
	  if(session.getAttribute("filtroPromocion")==null){return false;}
		return true;
	}		

/**
*	Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

/**
*	Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  	String s=process(request, response);
	  if(s!=null && !s.equals(""))
	    ir(1,s,request,response);
	  return;
	}

/**
*	Redirige el control a otro servlet
*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
*	@param	String s
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Login login=(Login)request.getSession().getAttribute("login");
		//System.out.println("-SIGES-"+(login!=null?login.getUsuarioId():"")+"Despacho Promocion Save");
	  if(cursor!=null)cursor.cerrar();
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);		
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){
		if (!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
/**
*	Retorna una variable que contiene los mensajes que se van a enviar a la vista
*	@return String	
**/
	public String getMensaje(){
		return mensaje;
	}
}