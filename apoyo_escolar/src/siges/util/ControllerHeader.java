package siges.util;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import siges.dao.*;
import siges.login.beans.Login;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import siges.dao.DataSourceManager;


/**
*	Nombre:	ControllerHeader<br>
*	Descripcinn:	Maneja el header de la pngina<br>
*	Funciones de la pngina:	Envia al integrador lo que debe pintar el header<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerHeader extends HttpServlet{
	private String mensaje="";

/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		String view=null;
		String categoria=null;
		Connection cn=null;
		Statement pst=null;
		ResultSet rs=null;
		String dane=null;
		Login login=(Login)req.getSession().getAttribute("login");  	
			try{
		  	String stm ="";
				if(login!=null){
					view = "/header.jsp";
					/*ESCUDO*/
					if(req.getSession().getAttribute("imagenEscudo")==null && login!=null && !login.getInstId().equals("")){					
					  ResourceBundle rb=ResourceBundle.getBundle("path");
						String path=rb.getString("escudo.PathBajar");
						String relativo=Ruta.getRelativo(getServletContext(),path);
						String absoluto=Ruta.get(getServletContext(),path);
						File f=null;
						cn=DataSourceManager.getConnection(1);
						pst=cn.createStatement();
						rs=pst.executeQuery("select inscoddane from institucion where inscodigo="+login.getInstId());				
						if(rs.next()){dane=rs.getString(1);}
						rs.close();pst.close();
						f=new File(absoluto+"e"+dane+".gif");
						if(f.exists()){
						  req.getSession().setAttribute("imagenEscudo",relativo+"e"+dane+".gif");
						  req.getSession().setAttribute("extensionEscudo","gif");
						  f=null;
						  System.out.println("SI ESCUDO "+dane+"GIF");
							return view;
						}
						f=new File(absoluto+"e"+dane+".jpg");
						if(f.exists()){
						  req.getSession().setAttribute("imagenEscudo",relativo+"e"+dane+".jpg");
						  req.getSession().setAttribute("extensionEscudo","jpg");
						  f=null;
						  System.out.println("SI ESCUDO "+dane+"JPG");
							return view;
						}
					  System.out.println("NO ESCUDO "+dane);
						req.getSession().setAttribute("imagenEscudo",null);
				    return view;
					}
				}else{
			    	view="/error.jsp";
						return view;				
		    }
			}catch(Exception th){
			    req.setAttribute("mensaje",th.getMessage());
			    return "/error.jsp";
			}finally{
			   try{ 
				   OperacionesGenerales.closeResultSet(rs);
				   OperacionesGenerales.closeStatement(pst);
				   OperacionesGenerales.closeConnection(cn);
			   }catch(Exception e){}
			}
		return "/header.jsp";
	}

	/**
*	Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		doPost(req, res);
  }

/**
*	Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
  public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
  	String s=process(req, res);
	  if(s!=null && !s.equals(""))
	  	ir(1,s,req,res);		
  }

/**
*	recibe el url de destino, el request y el response y manda el control a la pagina indicada
*	@param:	int a
*	@param:	String s
*	@param:	HttpServletRequest request
*	@param:	HttpServletResponse response
**/
	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param:	String s
**/
	public void setMensaje(String s){
		mensaje+=" "+s+"\n";
	}
	}