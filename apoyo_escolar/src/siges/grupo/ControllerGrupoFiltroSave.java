package siges.grupo; 

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
import siges.grupo.beans.GrupoBean;
import siges.login.beans.Login;
import siges.util.Properties;
import siges.grupo.dao.GrupoDAO;
import siges.grupo.beans.*;

/**
 *	Nombre:	ControllerGrupoFiltroSave
 *	Descripcion:	 
 *	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
 *	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
 *	Funciones de la pagina:	Procesar la peticion y enviar el control al formulario de nuevo registro
 *	Entidades afectadas:	Tablas maestras en modo de solo lectura
 *	Fecha de modificacinn:	25/01/06
 *	@author Latined
 *	@version $v 1.0 $
 */
public class ControllerGrupoFiltroSave extends HttpServlet{
    private String mensaje;//mensaje en caso de error
    private String buscar;
    private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
    private Cursor cursor;//objeto que maneja las sentencias sql
    private Login login;
    private Util util;
    private ResourceBundle rb,rb1;
    private FiltroBeanGrupos filtroG;
    private GrupoBean newgrupo;
    private HttpSession session;
    private GrupoDAO grupoDAO;
    
    /**
     *	Procesa la peticion HTTP
     *	@param	HttpServletRequest request
     *	@param	HttpServletResponse response
     **/
    public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        session = request.getSession();
        String sig,sig2,sig3,sig4;
        String ant;
        String er;
        String home;
        String sentencia;
        String boton;
        int tipo;
        String id;
        String id2;
        String id_grado;
        String id_metod;
        sig=getServletConfig().getInitParameter("sig");
        sig2=getServletConfig().getInitParameter("sig2");//NUEVOEDIT.DO
        sig3=getServletConfig().getInitParameter("sig3");
        sig4=getServletConfig().getInitParameter("sig4");
        ant=getServletConfig().getInitParameter("ant");
        er=getServletContext().getInitParameter("error");
        home=getServletContext().getInitParameter("home");
        rb=ResourceBundle.getBundle("grupo");
        rb1=ResourceBundle.getBundle("preparedstatementsgrupos");
        cursor=new Cursor();
//        newgrupo=new GrupoBean();
        grupoDAO=new GrupoDAO(cursor);
        err=false;
        mensaje=null;
        
        try{
            util=new Util(cursor);            
            boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");

    				if(!asignarBeans(request)){
      					setMensaje("Error capturando datos de sesinn para el usuario");
      					request.setAttribute("mensaje",mensaje);
      					return er;
      				}		
            /*EDITAR EL GRUPO*/
            
    				if(boton.equals("Editar")){
    						sig=editarGrupo(request,sig2);
    			    }

/*BUSCAR EL GRUPO*/            
        		if(boton.equals("")){
        				if(!asignarBeans(request)){
        					setMensaje("Error capturando datos de sesinn para el usuario");
        					request.setAttribute("mensaje",mensaje);
        					return er;
        				}		
            }
            
            if(!resultadoGrupo(request)){
                setMensaje("- Error procesando la consulta de grupos");
                request.setAttribute("mensaje",mensaje);
                return ant;
           }                
        }catch(Exception e){
            e.printStackTrace();
            throw new	ServletException(e);
        }finally{
            if(cursor!=null)cursor.cerrar();if(grupoDAO!=null)grupoDAO.cerrar();if(util!=null)util.cerrar();
        }
        return sig;
    }	
        
    
    public String getValorSQL(String a){
        a=a.trim().replace('*',' ').replace('\'',' ').replace(',',' ');
        return a;
    }
    

    
  	public String editarGrupo(HttpServletRequest request,String sig2) throws ServletException, IOException{
  			request.setAttribute("tipo","1");
  			String id=(request.getParameter("id")!=null)?request.getParameter("id"):new String("");
  			String id2=(request.getParameter("id2")!=null)?request.getParameter("id2"):new String("");
  			GrupoBean grupo;
  			grupo=grupoDAO.asignarGrupo(id,id2,login);
  			if(grupo!=null){
  				request.getSession().setAttribute("newgroup",grupo);
  				request.getSession().setAttribute("newgroup2",grupo.clone()) ;				
  				request.getSession().setAttribute("subframe","/grupo/FiltroResultado.jsp");
  				return sig2+"?tipo=1";
  			}else{
  				setMensaje(grupoDAO.getMensaje());
  				request.setAttribute("mensaje",mensaje);
  				return sig2;
  			}		
  		}
    
    
    /**
     *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
     *	@param HttpServletRequest request
     *	@return boolean
     */
    public boolean resultadoGrupo(HttpServletRequest request)throws ServletException, IOException{
        String s;
        Collection lista=new ArrayList();
        Object[] o=new Object[2];
        
        try{            
            s=rb.getString("grupo.buscar");
            /*SOLO POR INSTITUCION*/
            if(filtroG.getSede().equals("-9")){
                o=new Object[2];
                o[0]=Properties.ENTEROLARGO;
                o[1]=login.getInstId();
                lista.add(o);
                s+=" "+rb.getString("grupo.where1");
            }else{
                /*SOLO POR INSTITUCION, SEDE, JORNADA*/
                    //solo jornada
                    if(!filtroG.getSede().equals("-9") && !filtroG.getJornada().equals("-9") && filtroG.getMetodologia().equals("-9") && filtroG.getGrado().equals("-9")){
                        o=new Object[2];
                        o[0]=Properties.ENTEROLARGO;
                        o[1]=login.getInstId();
                        lista.add(o);
                        o=new Object[2];
                        o[0]=Properties.ENTEROLARGO;
                        o[1]=filtroG.getSede();
                        lista.add(o);
                        o=new Object[2];
                        o[0]=Properties.ENTEROLARGO;
                        o[1]=filtroG.getJornada();
                        lista.add(o);
                        s+=" "+rb.getString("grupo.where3");
                        System.out.println("NIVEL: SEDE - JORNADA");
                        System.out.println("INT: "+login.getInstId());
                        System.out.println("SEDE: "+filtroG.getSede());
                        System.out.println("JORNADA: "+filtroG.getJornada());
                       }
                    }
            /*METODOLOGIA*/
            
            if(!filtroG.getSede().equals("-9") && !filtroG.getJornada().equals("-9") && !filtroG.getMetodologia().equals("-9") && filtroG.getGrado().equals("-9")){
                o=new Object[2];
                o[0]=Properties.ENTEROLARGO;
                o[1]=login.getInstId();
                lista.add(o);
                o=new Object[2];
                o[0]=Properties.ENTEROLARGO;
                o[1]=filtroG.getSede();
                lista.add(o);
                o=new Object[2];
                o[0]=Properties.ENTEROLARGO;
                o[1]=filtroG.getJornada();
                lista.add(o);
                o=new Object[2];
                o[0]=Properties.ENTEROLARGO;
                o[1]=filtroG.getMetodologia();
                lista.add(o);
                s+=" "+rb.getString("grupo.where4");
                System.out.println("NIVEL: SEDE-JORNADA-METODOLOGIA");
                System.out.println("INT: "+login.getInstId());
                System.out.println("SEDE: "+filtroG.getSede());
                System.out.println("JORNADA: "+filtroG.getJornada());
                System.out.println("METODOLOGIA: "+filtroG.getMetodologia());
            }
            else{
                if(!filtroG.getSede().equals("-9") && !filtroG.getJornada().equals("-9") && !filtroG.getMetodologia().equals("-9") && !filtroG.getGrado().equals("-9")){
                    o=new Object[2];
                    o[0]=Properties.ENTEROLARGO;
                    o[1]=login.getInstId();
                    lista.add(o);
                    o=new Object[2];
                    o[0]=Properties.ENTEROLARGO;
                    o[1]=filtroG.getSede();
                    lista.add(o);
                    o=new Object[2];
                    o[0]=Properties.ENTEROLARGO;
                    o[1]=filtroG.getJornada();
                    lista.add(o);
                    o=new Object[2];
                    o[0]=Properties.ENTEROLARGO;
                    o[1]=filtroG.getMetodologia();
                    lista.add(o);
                    o=new Object[2];
                    o[0]=Properties.ENTEROLARGO;
                    o[1]=filtroG.getGrado();
                    lista.add(o);
                    s+=" "+rb.getString("grupo.where5");
                    System.out.println("NIVEL: SEDE-JORNADA-METODOLOGIA-GRADO");
                    System.out.println("INT: "+login.getInstId());
                    System.out.println("SEDE: "+filtroG.getSede());
                    System.out.println("JORNADA: "+filtroG.getJornada());
                    System.out.println("METODOLOGIA: "+filtroG.getMetodologia());
                    System.out.println("GRADO: "+filtroG.getGrado());
                }
            }
            
            System.out.println("ORDEN: "+filtroG.getOrden());
            /*ORDEN*/
            if(!filtroG.getOrden().equals("")){
	            switch(Integer.parseInt(filtroG.getOrden())){
	            case -9:
	                s+=" "+rb.getString("grupo.orden-9");
	                break;
	            case 1:
	                s+=" "+rb.getString("grupo.orden1");
	                break;
	            }
            } 
            //System.out.println("CONSULTA DE GRUPOS: "+s);
            request.getSession().setAttribute("filtroListaGrupo",util.getFiltro(s,lista));
            
        }catch(Exception th){
           th.printStackTrace();
          return false;            
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
   	 * Elimina del contexto de la sesion los beans de informacion del usuario
   	 *	@param HttpServletRequest request
   	 */
   		public void borrarNewBeansGrupos(HttpServletRequest request) throws ServletException, IOException{  
   	    session.removeAttribute("newgroup");
   		}
          
      /**
       * Elimina del contexto de la sesion los beans de informacion del usuario
       *	@param HttpServletRequest request
       */
      public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{       
          request.getSession().removeAttribute("filtrogroup"); 
          request.getSession().removeAttribute("newgroup");
      }

      
     /**
       *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
       *	@param HttpServletRequest request
       *	@return boolean 
       */
      public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
          if(request.getSession().getAttribute("filtrogroup")==null)
              return false;
          login=(Login)request.getSession().getAttribute("login");
          filtroG=(FiltroBeanGrupos)request.getSession().getAttribute("filtrogroup");
          return true;
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
            mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
        }
        mensaje+="  - "+s+"\n";
    }
}