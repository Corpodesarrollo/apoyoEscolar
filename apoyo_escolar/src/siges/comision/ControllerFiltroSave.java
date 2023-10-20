package siges.comision; 

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import siges.dao.*;
import siges.comision.beans.*;
import siges.login.beans.*;

/**
*	Nombre:	ControllerFiltroEdit<br>
*	Descripcinn:	<br>
*	Funciones de la pngina:	<br>
*	Entidades afectadas:	<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerFiltroSave extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private String buscar;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;//
	private Login login;
	private FiltroBeanCom filtro;
	private HttpSession session;

/**
*	Funcinn: Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		session = request.getSession();
		String sig,sig2,sig3;
		String ant;
		String er;
		String home;
		String sentencia;
		String boton;
		String id;
		sig="/comision/FiltroResultado.jsp";
		sig2="/comision/ControllerNuevoEdit.do";
		sig3="/comision/ControllerFiltroSave.do";
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		cursor=new Cursor();
		err=false;
		mensaje=null;
//		if(!cursor.abrir(1)){
//			setMensaje("No se pudo abrir la conexinn a la base de datos");
//			request.setAttribute("mensaje",mensaje);
//			return er;
//		}
		util=new Util(cursor);
		boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		//System.out.println("cupido: "+boton);
		if(boton.equals("Editar")){
			borrarBeans(request);
			sig=sig2;	
		}
		
		if(boton.equals("")){
			if(!asignarBeans(request)){
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje",mensaje);
				return er;
			}		
			asignarLista(request);
    }			
		return sig;
		}	

/**
 * Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
 * @param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
		session.removeAttribute("nuevoComision");
   	session.removeAttribute("encabezado");
 	  session.removeAttribute("subframe");
	}

/**
 *	Funcinn: Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		login=(Login)session.getAttribute("login");
		filtro=(FiltroBeanCom)session.getAttribute("filtroComision");
		return true;
	}		

/**
 *	Funcinn: Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public void asignarLista(HttpServletRequest request)throws ServletException, IOException{ 
		String s;
		try{
			s="select EstCodigo,EstNumDoc,CONCAT(EstNombre1,concat(' ',EstNombre2)),CONCAT(EstApellido1,concat(' ',EstApellido2)) from estudiante ";
			s+="where EstGrupo in( ";
			s+="select G_JerCodigo from g_jerarquia where ";
			s+="G_JerTipo=1 ";
			s+="and G_JerNivel=8 ";
			s+="and G_JerInst="+login.getInstId()+" ";
			if(filtro.getSede()!=null && !filtro.getSede().equals("-1"))
				s+="and G_JerSede="+filtro.getSede()+" ";
			if(filtro.getJornada()!=null && !filtro.getJornada().equals("-1"))
				s+="and G_JerJorn="+filtro.getJornada()+" ";
			if(filtro.getGrado()!=null && !filtro.getGrado().equals("-1"))
				s+="and G_JerGrado="+filtro.getGrado()+" ";
			if(filtro.getGrupo()!=null && !filtro.getGrupo().equals("-1"))
				s+="and G_JerGrupo="+filtro.getGrupo()+" ";
			s+=") ";
			if(!filtro.getId().trim().equals(""))
				s+="and EstNumDoc="+filtro.getId().trim()+" ";
			if(!filtro.getNombre1().trim().equals(""))
				s+="and EstNombre1='"+filtro.getNombre1().trim()+"' ";
			if(!filtro.getNombre2().trim().equals(""))
				s+="and EstNombre2='"+filtro.getNombre2().trim()+"' ";
			if(!filtro.getApellido1().trim().equals(""))
				s+="and EstApellido1='"+filtro.getApellido1().trim()+"' ";
			if(!filtro.getApellido2().trim().equals(""))
				s+="and EstApellido2='"+filtro.getApellido2().trim()+"' ";
			switch(Integer.parseInt(filtro.getOrden())){
				case -1://codigo interno
					s+=" order by EstCodigo";
				break;
				case 0://numerro de identificacion
					s+=" order by EstNumDoc";
				break;
				case 1://nombre
					s+=" order by EstNombre1,EstNombre2";
				break;
				case 2://apellido
					s+=" order by EstApellido1,EstApellido2";
				break;
			}
			System.out.println(s);
			session.setAttribute("filtroEstudiantes",util.getFiltro(s));
		}catch(Throwable th){
			System.out.println("error "+th);
			throw new	ServletException(th);
		}
	}
	
/**
*	Funcinn: Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

/**
*	Funcinn: Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  	String s=process(request, response);
	  if(s!=null && !s.equals(""))
	  	ir(1,s,request,response);
	}

/**
*	Funcinn: Redirige el control a otro servlet
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
*	Funcinn: Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){
		if (!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}