package siges.comision; 

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.dao.*;
import siges.comision.beans.*;
import siges.login.beans.*;

/**
*	Nombre:	ControllerNuevoEdit<br>
*	Descripcinn:	Nuevo y Editar Comisinn de Evaluacinn<br>
*	Funciones de la pngina:	Controla la vista del formulario nuevo y edicinn de Perfiles de usuario <br>
*	Entidades afectadas:	Tablas maestras de solo lectura<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerNuevoEdit extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private String buscar;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;//
	private HttpSession session;
	private Login login;
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;

/**
*	Funcinn: Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		session = request.getSession();
		rb=ResourceBundle.getBundle("preparedstatements");
		
		String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
		String er;//nombre de la pagina a la que ira si hay errores
		String home;//nombre de la pagina a la que ira si hay errores
		int tipo;

		String sig="/comision/NuevoComision.jsp";
		cursor=new Cursor();
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		err=false;
		mensaje=null;

//		if(!cursor.abrir(1)){
//			setMensaje("No se pudo abrir la conexinn a la base de datos");
//			request.setAttribute("mensaje",mensaje);
//			return er;
//		}
		util=new Util(cursor);
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			borrarBeans(request);
			session.removeAttribute("editar");
			session.removeAttribute("filtroConvivencia");
			tipo=1;
		}else
			tipo=Integer.parseInt((String)request.getParameter("tipo"));
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return(er);
		}
		switch(tipo){
			case 1://basica
				//editarBasica(request);
			break;

		}
		if(err){
			request.setAttribute("mensaje",mensaje);
			return er;
		}			
		return sig;
		}	
	
/**
 * Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
 * @param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
		session.removeAttribute("nuevoComision");		
	}

/**
 *	Funcinn: Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
 
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		login=(Login)session.getAttribute("login");
		return true;
	}		

/**
 *	Funcinn: Trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	informacion que se desee mostrar de la tabla de informacion educativa
 *	@param HttpServletRequest request
 */
	public void editarBasica(HttpServletRequest request) throws ServletException, IOException{
		try{
			int z=0;
			list = new ArrayList();
			o=new Object[2];
			o[z++]=new Integer(java.sql.Types.INTEGER);o[z++]=login.getInstId();
			list.add(o);
			session.setAttribute("filtroGrupos",util.getFiltro(rb.getString("filtroGruposInstitucion"),list));
		}catch(Throwable th){
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