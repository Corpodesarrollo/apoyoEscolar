package siges.adminconflicto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

import siges.adminconflicto.beans.*;
import siges.dao.*;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerFiltro extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;
    private boolean err;
    private Cursor cursor;
    private Util util;
    private ResourceBundle rb;
   	
   	/**
   	*	Procesa la peticion HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   	    HttpSession session = request.getSession();
   	    rb=ResourceBundle.getBundle("adminconflicto");
   	    String sig="/adminconflicto/ListaCategoria.jsp";
   	    String sig1="/adminconflicto/ListaClase.jsp";
   	    String sig2="/adminconflicto/ListaTipo.jsp";
   	    String sig2f="/adminconflicto/FiltroTipo.jsp";
   	    String p_error=this.getServletContext().getInitParameter("er");
   	    String consulta,fcategoria,fclase,catnombre,clanombre;
   	    cursor=new Cursor();
   	    util=new Util(cursor);
   	    int tipo;
   	    try{
   	        FiltroConflicto fc=(FiltroConflicto)session.getAttribute("filtroconflicto");
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
   	        case 2:
   	            request.setAttribute("guia",request.getParameter("id"));
   	        case 0:case 1:case 3:
   	            request.setAttribute("tcategoria",util.getFiltro(rb.getString("ListaCategoria")));
   	            break;
/****
 * CLASE
 */
   	        case 12:
   	            request.setAttribute("guia",request.getParameter("id"));
   	        case 10:case 11:case 13:
   	            request.setAttribute("tclase",util.getFiltro(rb.getString("ListaClase")));
   	            sig=sig1;
   	            break;
/****
 * TIPO
 */
   	        case 20:case 21:case 22:case 23:
   	            String buscar=(String)(request.getParameter("estadof")==null?"-1":request.getParameter("estadof"));
   	            
   	            if(buscar.equals("1")){
   	                if(fc.getFcategoria().equals(""))	fcategoria="-1";
   	                else	fcategoria=fc.getFcategoria();
   	   	            if(fc.getFclase().equals(""))	fclase="-1";
   	   	            else	fclase=fc.getFclase();
   	   	            if(fc.getCatnombre().equals(""))	catnombre="-1";
   	   	            else	catnombre=fc.getCatnombre();
   	   	            if(fc.getClanombre().equals(""))	clanombre="-1";
   	   	            else	clanombre=fc.getClanombre();
   	   	            
   	   	            consulta=rb.getString("BuscarTipo");
   	   	            if(!fcategoria.equals("-1") && fclase.equals("-1")){
   	   	                consulta+=" and ct.IDCATEGORIA='"+fcategoria+"' order by orden asc";
   	   	            }else if(fcategoria.equals("-1") && !fclase.equals("-1")){
   	   	                consulta+=" and cl.IDCLASE='"+fclase+"' order by orden asc";
   	   	            }else if(!fcategoria.equals("-1") && !fclase.equals("-1")){
   	   	                consulta+=" and ct.IDCATEGORIA='"+fcategoria+"' and cl.IDCLASE='"+fclase+"' order by orden asc";
   	   	            }else{
   	   	                consulta+=" order by orden asc";
   	   	            }
   	   	            
   	   	            request.setAttribute("ttipo",util.getFiltro(consulta));
   	   	            
   	   	            sig=sig2;
   	            }else{
   	                request.setAttribute("tcategoria",util.getFiltro(rb.getString("ListaCategoria")));
   	   	            request.setAttribute("tclase",util.getFiltro(rb.getString("ListaClase")));
   	   	            
   	   	            sig=sig2f;
   	            }
   	            request.setAttribute("guia",request.getParameter("id"));
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