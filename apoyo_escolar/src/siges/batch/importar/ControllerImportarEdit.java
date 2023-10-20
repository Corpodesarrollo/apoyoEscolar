package siges.batch.importar;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.login.beans.Login;
import siges.util.Properties;

/**
*	Nombre:	ControllerImportarEdit<BR>
*	Descripcinn:	Controla la vista del formulario de importacinn de plantillas de importacinn y de plantillas de evaluacinn<BR>
*	Funciones de la pngina:	Redirigir el control a la pagina de importacion respectiva	<BR>
*	Entidades afectadas:	No aplica	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class ControllerImportarEdit extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private String buscar;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario	
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;

/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session= request.getSession();
		Login login;
		rb=ResourceBundle.getBundle("batch");
		String sig,sig2,sig3,sig4,sig5;
		String ant;
		String er;
		String home;
		int tipo;
		sig="/batch/importar/ImportarBateriaLogro.jsp";
		sig2="/batch/importar/ImportarBateriaDescriptor.jsp";
		sig3="/batch/importar/ImportarAsignatura.jsp";
		sig4="/batch/importar/ImportarArea.jsp";
		sig5="/batch/importar/ImportarPreescolar.jsp";
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		err=false;
		mensaje=null;
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			session.removeAttribute("editar");
			tipo=Properties.PLANTILLABATLOGRO;
		}else{
			  String valorCampo=(String)request.getParameter("tipo");
				try{
				  tipo=Integer.parseInt((String)request.getParameter("tipo"));
				}catch(NumberFormatException e){
				  tipo=Integer.parseInt(valorCampo.substring(valorCampo.length()-1,valorCampo.length()));
				}
		}	
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		login=(Login)request.getSession().getAttribute("login");
		//poner cantidad de archivos en Batch
    String pathBase=rb.getString("importar.carpeta")+"."+login.getUsuarioId();
    String pathTotal=Ruta2.get(getServletContext(),pathBase);
    File f=new File(pathTotal);
  	int cont1=0;
  	int cont2=0;
  	int cont3=0;
  	int cont4=0;
  	int cont5=0;
    if(f.exists() && f.isDirectory()){
    	String pre;
      String []files=f.list();
      if(files!=null){
	      for(int i=0;i<files.length;i++){
	      	pre=files[i].substring(0,files[i].indexOf("_"));
	      	if(pre.equals("1")){
	      		cont1++;
	      	}
	      	if(pre.equals("2")){
	      		cont2++;
	      	}
	      	if(pre.equals("3")){
	      		cont3++;
	      	}
	      	if(pre.equals("4")){
	      		cont4++;
	      	}
	      	if(pre.equals("5")){
	      		cont5++;
	      	}
	      }	      
      } 
    }
    request.setAttribute("cont1",""+cont1);
    request.setAttribute("cont2",""+cont2);
    request.setAttribute("cont3",""+cont3);
    request.setAttribute("cont4",""+cont4);
    request.setAttribute("cont5",""+cont5);
    //poner cantidad de archivos en Batch
		switch(tipo){
			case Properties.PLANTILLABATLOGRO: break;
			case Properties.PLANTILLABATDESCRIPTOR: 
				sig=sig2; break;
			case Properties.PLANTILLALOGROASIG: 
				sig=sig3; break;
			case Properties.PLANTILLAAREADESC: 
				sig=sig4; break;
			case Properties.PLANTILLAPREE: 
				sig=sig5; break;
		}
		if(err){
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		return sig;
	}	

/**
 *	Trae de session los datos de la credencial de acceso del usuario
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		if(request.getSession().getAttribute("login")==null)
			return false;
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
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){
		if(!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}