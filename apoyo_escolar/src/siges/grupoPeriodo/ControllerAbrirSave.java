package siges.grupoPeriodo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import siges.dao.Cursor;
import siges.grupoPeriodo.beans.AbrirGrupo;
import siges.grupoPeriodo.beans.CierreVO;
import siges.grupoPeriodo.dao.GrupoPeriodoDAO;
import siges.login.beans.Login;
import siges.util.Logger;
import util.BitacoraCOM;
import util.BitacoraDto;
import util.LogAbrirGrupoDto;
import util.LogCerrarPeriodoDto;

/**
*	Nombre:	ControllerAbrirSave<BR>
*	Descripcinn:	Maneja la negociacion para abrir grupos en la base de datos y cerrar los periodos	<BR>
*	Funciones de la pngina:	Inserta o actualiza los registros en las tablas de cierre grupo y periodo	<BR>
*	Entidades afectadas:	Cierre_grupn y Periodo	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class ControllerAbrirSave extends HttpServlet{
	private String mensaje;
	private String buscar;
	private boolean err;
	private Cursor cursor;
	private GrupoPeriodoDAO grupoPeriodoDAO;
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;
	private int z;
	private int entero=java.sql.Types.INTEGER;
	private int cadena=java.sql.Types.VARCHAR;
	//private Login login;
	//private AbrirGrupo abrirGrupo;

/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		rb=ResourceBundle.getBundle("preparedstatements");
		AbrirGrupo abrirGrupo;
		CierreVO cierreVO;
		Login login;
		String sig,sig2,sig3;
		String ant;
		String er;
		String home;
		String sentencia;
		String boton;
		String id;
		sig="/grupoPeriodo/ControllerAbrirEdit.do";
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		cursor=new Cursor();
		err=false;
		mensaje=null;
		try{
		grupoPeriodoDAO= new GrupoPeriodoDAO(cursor);
		boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		login=(Login)request.getSession().getAttribute("login");
		abrirGrupo=(AbrirGrupo)request.getSession().getAttribute("abrirGrupo");
		cierreVO=(CierreVO)request.getSession().getAttribute("cierreVO");
		if(request.getParameter("tipo")==null || request.getParameter("tipo").equals("")){
			setMensaje("Acceso denegado no hay una ficha definida");
			request.setAttribute("mensaje",getMensaje());
			return er;
		}
				
		int tipo=Integer.parseInt((String)request.getParameter("tipo"));
		if(boton.equals("Guardar")){
			switch(tipo){
				case 1:
					if(!abrirGrupo(request,login,abrirGrupo))
						request.setAttribute("mensaje",mensaje);
					else
						request.setAttribute("mensaje","El grupo fue abierto satisfactorimente");
					return (sig+="?tipo="+tipo);
				case 2:
					
					if(request.getSession().getAttribute("objetoConsultaperdidas")!=null){
						request.getSession().removeAttribute("objetoConsultaperdidas");
					}
					request.getSession().setAttribute("objetoConsultaperdidas",cierreVO);
					
					
					if(!cerrarPeriodo(request,login,abrirGrupo,cierreVO)){
						request.setAttribute("mensaje",mensaje);
					}else{
						request.setAttribute("mensaje","El periodo fue cerrado satisfactoriamente");
					}	
					return (sig+="?tipo="+tipo);
			}
    }
		if(boton.equals("Abrir")){
			switch(tipo){
				case 2:
					if(!abrirPeriodo(request,login,abrirGrupo,cierreVO))
						request.setAttribute("mensaje",mensaje);
					else
						request.setAttribute("mensaje","El periodo fue abierto satisfactoriamente");
					return (sig+="?tipo="+tipo);
			}
    }
		if(boton.equals("Cancelar")){
			borrarBeans(request);
			sig=home;
    }
		if(boton.equals("Cerrar")){
		    //System.out.println("Cerrando ");
				if(!cerrarPeriodoTotal(request,login,abrirGrupo,cierreVO)){
				   request.setAttribute("mensaje",mensaje);
				}else
				   request.setAttribute("mensaje","El periodo fue cerrado satisfactoriamente.");
				return (sig+="?tipo="+tipo);
	    }
		return sig;
		}catch(Exception e){
		    e.printStackTrace();
				//System.out.println("Error "+this+":"+e.toString());
				throw new	ServletException(e);
			}finally{
				if(cursor!=null)cursor.cerrar();if(grupoPeriodoDAO!=null)grupoPeriodoDAO.cerrar();
			}
	}

/**
 * Elimina del contexto de la sesion los beans de informacion del formulario
 *	@param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request){
	    request.getSession().removeAttribute("abrirGrupo");
	}

/**
 *	Trae de sesion la credencial de acceso del usuario y los datos del formulario de consulta de apartura de grupos y cierre de periodo
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request){ 
		if(request.getSession().getAttribute("login")==null || request.getSession().getAttribute("abrirGrupo")==null  || request.getSession().getAttribute("cierreVO")==null)
			return false;
		return true;
	}		

	/**
	*	Funcion:  Registra la apertura de un grupo a partir de los datos del formulario de apertura de grupos<BR>
	*	@param	HttpServletRequest request
	*	@return	boolean
	**/
	public boolean abrirGrupo(HttpServletRequest request,Login login,AbrirGrupo abrirGrupo){
		if(!grupoPeriodoDAO.abrirGrupo(login.getInstId(),abrirGrupo,login.getLogNivelEval())){
			setMensaje(grupoPeriodoDAO.getMensaje());
			return false;
		}
		request.getSession().removeAttribute("abrirGrupo");
		Logger.print(login.getUsuarioId(),
		        "Abrir Grupo Inst:"+login.getInstId()+" Sede:"+login.getInstId()+" Jorn:"+login.getJornadaId()+" Met:"+abrirGrupo.getMetodologia()+" Gra:"+abrirGrupo.getGrado()+" Grupo:"+abrirGrupo.getGrupo()+" Asig:"+abrirGrupo.getAsignatura()+" Per:"+abrirGrupo.getPeriodo(),
		        7,1,this.toString());
		
		try {
			LogAbrirGrupoDto logGrupo= new LogAbrirGrupoDto();
			logGrupo.setInstitucion(login.getInst());
			logGrupo.setSede(login.getSede());
			logGrupo.setJornada(login.getJornada());
			logGrupo.setMetodologia(abrirGrupo.getMetodologia());
			logGrupo.setArea(abrirGrupo.getArea());
			logGrupo.setDimension("--");
			logGrupo.setAsignatura(abrirGrupo.getAsignatura());
			logGrupo.setAbrirPor(login.getUsuario());
			logGrupo.setGrado(abrirGrupo.getGrado());
			logGrupo.setGrupo(abrirGrupo.getGrupo());
			logGrupo.setPeriodo(abrirGrupo.getPeriodo());
			BitacoraCOM.insertarBitacora(
					Long.parseLong(login.getInstId()), 
					Integer.parseInt(login.getJornada()),
					4 ,
					login.getPerfil(), 
					Integer.parseInt(login.getSedeId()),
					1112, 
					1, login.getUsuario(), new Gson().toJson(logGrupo));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	/**
	*	Funcion:  Accede a la base de datos para verificar si se puede cerrar unperiod o partir de la consulta de los grupos cerrados y los que falta por cerrar<BR>
	*	@param	HttpServletRequest request
	*	@return	boolean
	**/
	public boolean cerrarPeriodo(HttpServletRequest request,Login login,AbrirGrupo abrirGrupo,CierreVO cierreVO){
		String periodo=cierreVO.getCiePer();
		String sede=cierreVO.getCieSed();
		String jor=cierreVO.getCieJor();
		String estadoInicial=cierreVO.getCieEsta();
		cierreVO.setCieInst(login.getInstId());
		/*** CAMBIOS MCUELLAR 28-11-2011
		 * Este cambio para obligar al usuario a reabrir los periodos posteriores y que el sistema recalcule correctamente.****/
		Collection periodosList = (Collection) request.getSession().getAttribute("periodosAbiertosList");
		Iterator itePeriodos = periodosList.iterator();
		boolean estadoPost=false;
		while(itePeriodos.hasNext()){
			Object[] datos = (Object[]) itePeriodos.next();
			if(datos[20].equals(sede) && datos[21].equals(jor)){
//				int posIni=periodo.equals("1")?5:periodo.equals("2")?9:periodo.equals("3")?13:periodo.equals("4")?23:periodo.equals("5")?27:17;
				int posIni=Integer.parseInt(periodo)+1;
				while(posIni<=login.getLogNumPer() && !estadoPost){
					int posPeriodo = posIni == 2 ? 5 : posIni == 3 ? 9 : posIni == 4 ? 13 : posIni == 5 ? 23 : posIni == 6 ? 27 : 17;
					if(datos[posPeriodo].equals("1")){
						estadoPost=true;
						break;
					}
					posIni++;
				}
			}
		}
		if(estadoPost){
			setMensaje("Para cerrar este periodo, primero debe abrir los periodos posteriores y luego cerrar en orden para que el sistema recalcule correctamente.");
			return false;
		}
		/*** FIN CAMBIOS MCUELLAR 28-11-2011****/
		
		int tipo=Integer.parseInt(estadoInicial);
			Collection []co=null;
			co=grupoPeriodoDAO.getGruposAbiertos(cierreVO);
			if(co[0].isEmpty() && co[1].isEmpty() && co[2].isEmpty()){
				if(!grupoPeriodoDAO.cerrarPeriodo(cierreVO,login.getLogNivelEval())){
					setMensaje(grupoPeriodoDAO.getMensaje());
				}else{
					setMensaje("El periodo ha sido cerrado satisfactoriamente");
					request.setAttribute("gruposAbiertos",co[0]);
					
					setearValoresdeConsultadeperdidadeAsignaturas(request, co[0]);
					
					Logger.print(login.getUsuarioId(),
					        "Cerrar Periodo Inst:"+login.getInstId()+" Sede:"+sede+" Jornada:"+jor+" Periodo:"+periodo,
					        7,1,this.toString());
					
					try {
						LogCerrarPeriodoDto logPeriodo= new LogCerrarPeriodoDto();
						logPeriodo.setInstitucion(login.getInst());
						logPeriodo.setSede(login.getSede());
						logPeriodo.setJornada(login.getJornada());
						logPeriodo.setPeriodo(abrirGrupo.getPeriodo());
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
					return false;
				}					    
			}else{
				if(co[1]!=null && !co[1].isEmpty()){
					co[1].addAll(co[0]);co[0]=co[1];
				}  
				if(co[2]!=null && !co[2].isEmpty()){
					co[2].addAll(co[0]);co[0]=co[2];
				}
				setMensaje("No se puede cerrar el periodo porque hay grupos aun abiertos, \n   Si desea cerrarlo de todas maneras, pulse en el botnn 'cerrar grupos'\n");
				if(co[0].size()>170){
					setMensaje("Nota:\n   La lista de grupos abiertos mostrada a continuacinn es solo una parte de la cantidad total de grupos abiertos");					
				}
				request.setAttribute("gruposAbiertos",co[0]);
				setearValoresdeConsultadeperdidadeAsignaturas(request, co[0]);
				return false;
			}
		return true;
	}

	public boolean abrirPeriodo(HttpServletRequest request,Login login,AbrirGrupo abrirGrupo,CierreVO cierreVO){
		String periodo=cierreVO.getCiePer();
		String sede=cierreVO.getCieSed();
		String jor=cierreVO.getCieJor();
		String estadoInicial=cierreVO.getCieEsta();
		cierreVO.setCieInst(login.getInstId());
		int tipo=Integer.parseInt(estadoInicial);
		if(!grupoPeriodoDAO.abrirPeriodo(cierreVO, login.getLogNivelEval())){
			setMensaje(grupoPeriodoDAO.getMensaje());
		}else{
			setMensaje("El periodo ha sido abierto satisfactoriamente");
			return false;
		}
		Logger.print(login.getUsuarioId(),
		        "Abrir Periodo Inst:"+login.getInstId()+" Sede:"+sede+" Jornada:"+jor+" Periodo:"+periodo,
		        7,1,this.toString());
		return true;
	}
	
	public boolean cerrarPeriodoTotal(HttpServletRequest request,Login login,AbrirGrupo abrirGrupo,CierreVO cierreVO){
			String periodo=cierreVO.getCiePer();
			String sede=cierreVO.getCieSed();
			String jor=cierreVO.getCieJor();
			String estadoInicial=cierreVO.getCieEsta();
			cierreVO.setCieInst(login.getInstId());
			if(!grupoPeriodoDAO.cerrarPeriodoTotal(cierreVO, login.getLogNivelEval())){
			    setMensaje(grupoPeriodoDAO.getMensaje());
			    return false;
			}
			Logger.print(login.getUsuarioId(),
			        "Cerrar Periodo Inst:"+login.getInstId()+" Sede:"+sede+" Jornada:"+jor+" Periodo:"+periodo,
			        7,1,this.toString());
			try {
				LogCerrarPeriodoDto logPeriodo= new LogCerrarPeriodoDto();
				logPeriodo.setInstitucion(login.getInst());
				logPeriodo.setSede(login.getSede());
				logPeriodo.setJornada(login.getJornada());
				logPeriodo.setPeriodo(abrirGrupo.getPeriodo());
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
	}

/**
*	Redirige el control a otro servlet
*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
*	@param	String s
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
	
	/**
	 * Metodo para agergar grados y tipos de metodologna para consulta de perdidas de asignaturas
	 * @param request
	 * @param co
	 */
	public void setearValoresdeConsultadeperdidadeAsignaturas(HttpServletRequest request, Collection co){
		
		Iterator iter = co.iterator();
		ArrayList listagrados=new ArrayList();
		ArrayList listametodologias=new ArrayList();
		while (iter.hasNext()) {
			Object[] datos = (Object[]) iter.next();
			if(!listagrados.contains(datos[1])){
				listagrados.add(datos[1]);
			}
			if(!listametodologias.contains(datos[0])){
				listametodologias.add(datos[0]);
			}
		}
	   request.getSession().setAttribute("gradosconsultar", listagrados);
	   request.getSession().setAttribute("metodologiasaconsultar", listametodologias);
	}
}