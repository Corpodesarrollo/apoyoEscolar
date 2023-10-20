package siges.institucion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.dao.*;
import siges.institucion.beans.FichaVO;
import siges.institucion.dao.FichaDAO;
import siges.login.beans.*;
import siges.util.Properties;

/**
 *	Nombre:	ControllerNuevoEdit
 *	Descripcion:	Controla el formulario de nuevo registro por parte de la orientadora 
 *	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
 *	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
 *	Funciones de la pagina:	Procesar la peticion y enviar el control al formulario de nuevo registro
 *	Entidades afectadas:	Tablas maestras en modo de solo lectura
 *	Fecha de modificacinn:	01/12/04
 *	@author Pasantes UD
 *	@version $v 1.2 $
 */
public class ControllerFichaSave extends HttpServlet{
    private Cursor cursor;//objeto que maneja las sentencias sql
    private FichaDAO fichaDAO;//
    private ResourceBundle rb;
    private final int TIPO_DEFAULT=1;
    private final int GUARDAR_FICHA=2;
    private final String sms="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - ";
    
    /**
     *	Procesa la peticion HTTP
     *	@param	HttpServletRequest request
     *	@param	HttpServletResponse response
     **/
    public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        Login login;
        FichaVO fichaVO;
        String err;
        rb=ResourceBundle.getBundle("siges.institucion.bundle.institucion");
        String er;
        String home;
        int tipo;
        String sig=getServletConfig().getInitParameter("sig");
        String ant=getServletConfig().getInitParameter("ant");
        er=getServletContext().getInitParameter("error");
        home=getServletContext().getInitParameter("home");
        cursor=new Cursor();
        err=null;
        try{
            fichaDAO=new FichaDAO(cursor);
            tipo=getTipo(request);
            if(!asignarBeans(request)){
                request.setAttribute("mensaje",sms+"Error capturando datos de sesinn para el usuario");
                return(er);
            }
            login=(Login)request.getSession().getAttribute("login");
            fichaVO=(FichaVO)request.getSession().getAttribute("fichaVO");
            switch(tipo){
            	case TIPO_DEFAULT:
            	    //err=editarFicha(request,login,fichaVO);
              break;	
              case GUARDAR_FICHA:
                  if(!fichaDAO.actualizarFicha(fichaVO)){
                      request.setAttribute("mensaje",sms+fichaDAO.getMensaje());
                      return sig;
                  }
                  request.setAttribute("mensaje",sms+"información registrada satisfactoriamente");
              break;
            }
            
            if(err!=null){
                request.setAttribute("mensaje",err);
                return er;
            }			
            return sig;
        }catch(Exception e){
            System.out.println("Error "+this+": "+e.toString());
            throw new	ServletException(e);
        }finally{
            if(cursor!=null)cursor.cerrar();
        }
    }	
    
    public int getTipo(HttpServletRequest request){
        int tipo_=TIPO_DEFAULT;
        if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
            borrarBeans(request);
            request.getSession().removeAttribute("editar");
            tipo_=1;
        }else{
            tipo_=Integer.parseInt((String)request.getParameter("tipo"));
        }    
        return tipo_;
    }
    /**
     * Elimina del contexto de la sesion los beans de informacion del usuario
     *	@param HttpServletRequest request
     */
    public void borrarBeans(HttpServletRequest request){
        request.getSession().removeAttribute("fichaVO");
    }
    
    /**
     *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
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
        doPost(request,response);
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
}
