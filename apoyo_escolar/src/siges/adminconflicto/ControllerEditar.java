package siges.adminconflicto;

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
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.adminconflicto.beans.*;
import siges.adminconflicto.dao.*;
;

/**
 * @author Administrador
 *
 */
public class ControllerEditar extends HttpServlet{

    private String mensaje;
    private String buscar;
    private boolean err;
    private Util util;
    private Cursor cursor;
    private ResourceBundle rb;
    private Collection list;
    private Object[] o;
   	
    /**
   	*	Procesa la peticion HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
    public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession();
        rb=ResourceBundle.getBundle("adminconflicto");
        AdminConflictoDAO acDAO;
        String sig="/adminconflicto/NuevoCategoria.jsp";
        String sig1="/adminconflicto/NuevoClase.jsp";
        String sig2="/adminconflicto/NuevoTipo.jsp";
        String p_inte=this.getServletContext().getInitParameter("integrador");
        String p_home=this.getServletContext().getInitParameter("home");
        String p_login=this.getServletContext().getInitParameter("login");
        String p_error=this.getServletContext().getInitParameter("er");
        String dato;
        String consulta,fcategoria,fclase,catnombre,clanombre;
        cursor=new Cursor();
        util=new Util(cursor);
        acDAO=new AdminConflictoDAO(cursor);
        int tipo;
        try{
            Login login=(Login)session.getAttribute("login");
            AdminConflicto ac=(AdminConflicto)session.getAttribute("adminconflicto");
            AdminConflicto ac2=(AdminConflicto)session.getAttribute("adminconflicto2");
            if(!asignarBeans(request)){
                setMensaje("Error capturando datos de sesinn para el usuario");
                request.setAttribute("mensaje",mensaje);
                return(p_error);
            }

            if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
                borrarBeans(request);
                tipo=0;
            }else{
                tipo=Integer.parseInt((String)request.getParameter("tipo"));
            }

            switch(tipo){
/****
 * CATEGORIA
 */
            case 0:
                borrarBeans(request);
                session.removeAttribute("filtroconflicto");
                break;
            case 1:
                if(ac.getEstado().equals("1")){
                    if(acDAO.compararBeansCategoria(ac,ac2)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");
                    }else if(acDAO.actualizarCategoria(ac)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;
                    }
                }else if(!ac.getEstado().equals("1")){
                    if(acDAO.insertarCategoria(ac)){
                        request.setAttribute("mensaje","La información fue ingresada satisfactoriamente");
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;
                    }
                }
  		            request.setAttribute("refrescar","1");
  		            borrarBeans(request);
                break;
            case 2:
                dato=request.getParameter("id");
                ac=acDAO.asignarCategoria(dato);
                if(ac!=null){
                    session.setAttribute("adminconflicto",ac);
                    session.setAttribute("adminconflicto2",ac.clone());
                }else{
                    request.setAttribute("mensaje",acDAO.getMensaje());
                    return p_error;    
                }
                break;
            case 3:
                dato=request.getParameter("id");
                if(!acDAO.validarExistenciaCategoria(dato)){
                    request.setAttribute("mensaje","No se puede eliminar, tiene registros asociados");
                }else if(acDAO.eliminarCategoria(dato)){
                    request.setAttribute("mensaje","Operacion satisfecha");
                }else{
      		            request.setAttribute("mensaje",acDAO.getMensaje());
                }
                request.setAttribute("refrescar","1");
      		        borrarBeans(request);
      		        break;
/****
 * CLASE
 */
            case 10:
                borrarBeans(request);
                session.removeAttribute("filtroconflicto");
                sig=sig1;
                break;
            case 11:
                if(ac.getEstado().equals("1")){
                    if(acDAO.compararBeansClase(ac,ac2)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");
                    }else if(acDAO.actualizarClase(ac)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;
                    }
                }else if(!ac.getEstado().equals("1")){
                    if(acDAO.insertarClase(ac)){
                        request.setAttribute("mensaje","La información fue ingresada satisfactoriamente");
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;
                    }
                }
  		            request.setAttribute("refrescar","1");
  		            borrarBeans(request);
  		            sig=sig1;
                break;
            case 12:
                dato=request.getParameter("id");
                ac=acDAO.asignarClase(dato);
                if(ac!=null){
                    session.setAttribute("adminconflicto",ac);
                    session.setAttribute("adminconflicto2",ac.clone());
                }else{
                    request.setAttribute("mensaje",acDAO.getMensaje());
                    return p_error;
                }
                sig=sig1;
                break;
            case 13:
                dato=request.getParameter("id");
                if(!acDAO.validarExistenciaClase(dato)){
                    request.setAttribute("mensaje","No se puede eliminar, tiene registros asociados");
                }else if(acDAO.eliminarClase(dato)){
                    request.setAttribute("mensaje","Operacion satisfecha");
                }else{
      		            request.setAttribute("mensaje",acDAO.getMensaje());
                }
                request.setAttribute("refrescar","1");
      		        borrarBeans(request);
      		        sig=sig1;
      		        break;
/****
 * TIPO
 */
            case 20:
                borrarBeans(request);
                request.setAttribute("tcategoria",util.getFiltro(rb.getString("ListaCategoria")));
   	            request.setAttribute("tclase",util.getFiltro(rb.getString("ListaClase")));
                sig=sig2;
                break;
            case 21:
                if(ac.getEstado().equals("1")){
                    if(acDAO.compararBeansTipo(ac,ac2)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");
                    }else if(acDAO.actualizarTipo(ac)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;
                    }
                }else if(!ac.getEstado().equals("1")){
                    if(acDAO.insertarTipo(ac)){
                        request.setAttribute("mensaje","La información fue ingresada satisfactoriamente");
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;
                    }
                }
  		            request.setAttribute("refrescar","1");
  		            borrarBeans(request);
  		            session.removeAttribute("filtroconflicto");
  		            sig=sig2;
                break;
            case 22:
                dato=request.getParameter("id");
                ac=acDAO.asignarTipo(dato);
                if(ac!=null){
                    session.setAttribute("adminconflicto",ac);
                    session.setAttribute("adminconflicto2",ac.clone());
                }else{
                    request.setAttribute("mensaje",acDAO.getMensaje());
                    return p_error;    
                }
                request.setAttribute("tcategoria",util.getFiltro(rb.getString("ListaCategoria")));
   	            request.setAttribute("tclase",util.getFiltro(rb.getString("ListaClase")));
   	            
                sig=sig2;
                break;
            case 23:
                dato=request.getParameter("id");
                if(!acDAO.validarExistenciaTipo(dato)){
                    request.setAttribute("mensaje","No se puede eliminar, tiene registros asociados");
                }else if(acDAO.eliminarTipo(dato)){
                    request.setAttribute("mensaje","Operacion satisfecha");
                }else{
      		            request.setAttribute("mensaje",acDAO.getMensaje());
                }
      		        borrarBeans(request);
      		        session.removeAttribute("filtroconflicto");
      		        sig=sig2;
      		        break;
            }

            return sig;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error "+this+": "+e.toString());
            throw new	ServletException(e);
        }finally{
            if(cursor!=null)cursor.cerrar();
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
   	*	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
   	*	@param HttpServletRequest request
   	*	@return boolean 
   	*/
    public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
        HttpSession session = request.getSession();
        if(session.getAttribute("login")==null)
            return false;
        return true;
    }

    /**
    * Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
    *	@param HttpServletRequest request
    */
    public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
        HttpSession session=request.getSession();
        session.removeAttribute("adminconflicto");
        session.removeAttribute("adminconflicto2");
        request.removeAttribute("guia");
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
    *	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
    *	@param	String s
    **/
    public void setMensaje2(String s){
        if (!err){
            err=true;
            mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
        }
        mensaje="  - "+s+"\n";
    }

}