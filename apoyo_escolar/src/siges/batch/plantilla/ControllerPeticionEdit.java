package siges.batch.plantilla;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.util.Properties;
import siges.util.Recursos;

/**
*	Nombre:	ControllerImportarEdit<BR>
*	Descripcinn:	Controla la vista del formulario de importacinn de plantillas de importacinn y de plantillas de evaluacinn<BR>
*	Funciones de la pngina:	Redirigir el control a la pagina de importacion respectiva	<BR>
*	Entidades afectadas:	No aplica	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class ControllerPeticionEdit extends HttpServlet{
	private String mensaje;
	private String buscar;
	private boolean err;	
	private ResourceBundle rb;

/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session= request.getSession();
		Login login;
		rb=ResourceBundle.getBundle("batch");
		String sig;
		String ant;
		String er;
		String home;
		int tipo;
		sig="/batch/plantilla/solicitud.jsp";
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		err=false;
		mensaje=null;
		try{
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			session.removeAttribute("editar");
			tipo=Properties.PLANTILLABATLOGRO;
		}else{
			  String valorCampo=(String)request.getParameter("tipo");
			  tipo=Integer.parseInt((String)request.getParameter("tipo"));
		}	
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		login=(Login)request.getSession().getAttribute("login");
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		if(boton==null || boton.equals("")){
			request.setAttribute("periodos",Recursos.recursoEstatico[Recursos.PERIODO]);
		}
		if(boton.equals("Aceptar")){		  
			String per=request.getParameter("periodo");
		  if(ponerSolicitud(login,per)){  
		      setMensaje("Su solicitud fue recibida, manana las plantillas apareceran en la ventana de 'Home'");
		  }else{
		      setMensaje("No se pudo anexar su solicitud debido a errores intentando agregar la peticinn");
		  }
		  request.setAttribute("mensaje",mensaje);
		  request.setAttribute("periodos",Recursos.recursoEstatico[Recursos.PERIODO]);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sig;
	}	

	
	public boolean ponerSolicitud(Login login,String periodo){
      String val;
			Connection cn=null;
			PreparedStatement pst=null;
			ResultSet rs=null;
			boolean band=true;
			try{
				long inst=Long.parseLong(login.getInstId());
				long per=Long.parseLong(periodo);
			  cn=DataSourceManager.getConnection(1);
			  pst=cn.prepareStatement(rb.getString("solicitud.buscar"));			  
			  int pos=1;
				pst.setLong(pos++,inst);
				pst.setLong(pos++,per);
				rs=pst.executeQuery();
			  if(rs.next())
			  	band=false;
			  rs.close();
			  pst.close();
			  if(band){
					pst=cn.prepareStatement(rb.getString("solicitud.insertar"));
					pos=1;
					pst.setLong(pos++,inst);
					pst.setLong(pos++,-1);
					pst.setLong(pos++,per);
					pst.executeUpdate();
			  }
			}catch(InternalErrorException e){return false;}
			catch(SQLException e){e.printStackTrace();System.out.println("Error Insertar Demanda:"+e);return false;}
			finally{
				try{
					OperacionesGenerales.closeStatement(pst);
					OperacionesGenerales.closeConnection(cn);
				}catch(Exception e){}
			}
	    return true;
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