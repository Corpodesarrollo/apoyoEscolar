package siges.institucion.organizacion;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.institucion.organizacion.beans.*;
import siges.institucion.organizacion.dao.OrganizacionDAO;
import siges.login.beans.Login;

/**
*	Nombre:	
*	Descripcion:	Controla la peticion de insertar un nuevo registro 
*	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
*	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
*	Funciones de la pagina:	Valida, inserta un nuevo registro y sede el control a la pagina de resultados 
*	Entidades afectadas:	Tablas de información del egresado 
*	Fecha de modificacinn:	01/12/04
*	@author Pasantes UD
*	@version $v 1.2 $
*/
public class ControllerOrganizacionSave extends HttpServlet{	
	private Cursor cursor;//objeto que maneja las sentencias sql
	private OrganizacionDAO organizacionDAO;
	private String mensaje;//mensaje en caso de error
	private final String REP_OK="La información fue ingresada satisfactoriamente ";
	private static String FICHA_GOB_;
	private static String FICHA_ASO_;
	private static String FICHA_ASO2_;
	private static String FICHA_LID_;
	private static String FICHA_ANT_;
	private static String FICHA_HOME_;
	private static String FICHA_ERR_;

/**
*	Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	  HttpSession session = request.getSession();
		AsociacionVO asociacionVO=null,asociacionVO2=null;
		AsociacionIntegrantesVO asociacionIntegrantesVO=null,asociacionIntegrantesVO2=null;
		ConsejoDirectivoVO consejoDirectivoVO=null,consejoDirectivoVO2=null;
		ConsejoAcademicoVO consejoAcademicoVO=null,consejoAcademicoVO2=null;
		LiderVO liderVO=null,liderVO2=null;
		Login login=null;
		int tipo;
		String boton;
		FICHA_GOB_="/institucion/organizacion/ControllerOrganizacionEdit.do?tipo2="+Params.FICHA_GOB;
		FICHA_ASO_="/institucion/organizacion/ControllerOrganizacionEdit.do?tipo2="+Params.FICHA_ASO;
		FICHA_LID_="/institucion/organizacion/ControllerOrganizacionEdit.do?tipo2="+Params.FICHA_LID;
		FICHA_ASO2_="/institucion/organizacion/ControllerOrganizacionEdit.do?tipo2="+Params.FICHA_ASO;
		FICHA_ANT_="/institucion/organizacion/ControllerOrganizacionEdit.do";
		FICHA_ERR_="/error.jsp";
		FICHA_HOME_="/bienvenida.jsp";
		mensaje=null;		
		cursor=new Cursor();
		try{
		organizacionDAO=new OrganizacionDAO(cursor);
		boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("Cancelar");
		System.out.println("VALOR DE BOTON="+boton);
		if(boton.equals("Cancelar")){
			borrarBeans(request,session);
			return FICHA_HOME_;
		}
		if(boton.equals("Guardar")){
			//obtener objetos
			login=(Login)session.getAttribute("login");
		  	asociacionVO=(AsociacionVO)session.getAttribute("asociacionVO");
			asociacionVO2=(AsociacionVO)session.getAttribute("asociacionVO2");
			asociacionIntegrantesVO=(AsociacionIntegrantesVO)session.getAttribute("asociacionIntegrantesVO");
			asociacionIntegrantesVO2=(AsociacionIntegrantesVO)session.getAttribute("asociacionIntegrantesVO2");
			consejoDirectivoVO=(ConsejoDirectivoVO)session.getAttribute("consejoDirectivoVO");
			consejoDirectivoVO2=(ConsejoDirectivoVO)session.getAttribute("consejoDirectivoVO2");
			consejoAcademicoVO=(ConsejoAcademicoVO)session.getAttribute("consejoAcademicoVO");
			consejoAcademicoVO2=(ConsejoAcademicoVO)session.getAttribute("consejoAcademicoVO2");
			liderVO=(LiderVO)session.getAttribute("liderVO");
			liderVO2=(LiderVO)session.getAttribute("liderVO2");
			tipo=getTipo(request,session);
			System.out.println("VALOR DE tipo="+tipo);
			switch(tipo){
				case Params.FICHA_GOB2:
					if(login==null || login.getInstId().equals("")){
						setMensaje("No se puede insertar el registro de consejo directivo ya que no hay información del colegio");
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_GOB_);
					}
					if(consejoDirectivoVO.getConDirFrmEstado().equals("")){
						insertarRegistroConsejoDirectivo(request,session,consejoDirectivoVO,consejoDirectivoVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_GOB_);
					}
					if(consejoDirectivoVO.getConDirFrmEstado().equals("1")){
						actualizarRegistroConsejoDirectivo(request,session,consejoDirectivoVO,consejoDirectivoVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_GOB_);
					}
				break;
				case Params.FICHA_GOB4:
					if(login==null || login.getInstId().equals("")){
						setMensaje("No se puede insertar el registro de consejo academico ya que no hay información del colegio");
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_GOB_);
					}
					if(consejoAcademicoVO.getConAcaFrmEstado().equals("")){
						insertarRegistroConsejoAcademico(request,session,consejoAcademicoVO,consejoAcademicoVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_GOB_);
					}
					if(consejoAcademicoVO.getConAcaFrmEstado().equals("1")){
						actualizarRegistroConsejoAcademico(request,session,consejoAcademicoVO,consejoAcademicoVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_GOB_);
					}
				break;
				case Params.FICHA_ASO:
						if(login==null || login.getInstId().equals("")){
							setMensaje("No se puede insertar la asociacinn ya que no hay información del colegio");
							request.setAttribute("mensaje",getMensaje());
							return (FICHA_ASO_);
						}
						if(asociacionVO.getAsoEstado().equals("")){
							insertarRegistroAsociacion(request,session,asociacionVO,asociacionVO2);
							request.setAttribute("mensaje",getMensaje());
							return (FICHA_ASO_);
						}
						if(asociacionVO.getAsoEstado().equals("1")){
							actualizarRegistroAsociacion(request,session,asociacionVO,asociacionVO2);
							request.setAttribute("mensaje",getMensaje());
							return (FICHA_ASO_);
						}
					break;	
				case Params.FICHA_ASO2:
					if(login==null || login.getInstId().equals("")){
						setMensaje("No se puede insertar el integrante ya que no hay información del colegio");
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_ASO_);
					}
					if(asociacionIntegrantesVO.getAsoIntEstado().equals("")){
						insertarRegistroAsociacionIntegrantes(request,session,asociacionIntegrantesVO,asociacionIntegrantesVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_ASO_);
					}
					if(asociacionIntegrantesVO.getAsoIntEstado().equals("1")){
						actualizarRegistroAsociacionIntegrantes(request,session,asociacionIntegrantesVO,asociacionIntegrantesVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_ASO_);
					}
				break;	
				case Params.FICHA_LID2:
					if(login==null || login.getInstId().equals("")){
						setMensaje("No se puede insertar el lider ya que no hay información del colegio");
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_LID_);
					}
					if(liderVO.getLidFrmEstado().equals("")){
						insertarRegistroLider(request,session,liderVO,liderVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_LID_);
					}
					if(liderVO.getLidFrmEstado().equals("1")){
						actualizarRegistroLider(request,session,liderVO,liderVO2);
						request.setAttribute("mensaje",getMensaje());
						return (FICHA_LID_);
					}
				break;	
			}			
		}
	return FICHA_ERR_;
	}catch(Exception e){
	    e.printStackTrace();
			System.out.println("Error "+this+": "+e.toString());
			throw new	ServletException(e);
		}finally{
		  if(cursor!=null)cursor.cerrar();if(organizacionDAO!=null)organizacionDAO.cerrar();
		}
  }

	private int getTipo(HttpServletRequest request,HttpSession session){
			if(request.getParameter("tipo2")==null || ((String)request.getParameter("tipo2")).equals("")){
					borrarBeans(request,session);
					session.removeAttribute("editar2");
					session.removeAttribute("subframe2");
					return Params.FICHA_DEFAULT;
				}else
				    return Integer.parseInt((String)request.getParameter("tipo2"));
	}

	public boolean compararFichasAsociacion(AsociacionVO asociacionVO,AsociacionVO asociacionVO2){
			return organizacionDAO.compararBeans(asociacionVO,asociacionVO2);
		}
	
	public boolean compararFichasAsociacionIntegrantes(AsociacionIntegrantesVO asociacionIntegrantesVO,AsociacionIntegrantesVO asociacionIntegrantesVO2){
		return organizacionDAO.compararBeans(asociacionIntegrantesVO,asociacionIntegrantesVO2);
	}

	public boolean compararFichasConsejoDirectivo(ConsejoDirectivoVO consejoDirectivoVO,ConsejoDirectivoVO consejoDirectivoVO2){
		return organizacionDAO.compararBeans(consejoDirectivoVO,consejoDirectivoVO2);
	}
	
	public boolean compararFichasConsejoAcademico(ConsejoAcademicoVO consejoAcademicoVO,ConsejoAcademicoVO consejoAcademicoVO2){
		return organizacionDAO.compararBeans(consejoAcademicoVO,consejoAcademicoVO2);
	}
	
	public boolean compararFichasLider(LiderVO liderVO,LiderVO liderVO2){
		return organizacionDAO.compararBeans(liderVO,liderVO2);
	}
	
	/*
	public boolean actualizarRegistroGobierno(HttpServletRequest request,HttpSession session,Gobierno gobierno,Gobierno gobierno2) throws ServletException, IOException{
		if(compararFichasGobierno(gobierno,gobierno2)){
			setMensaje(REP_OK);
					session.removeAttribute("nuevoGobierno");
					session.removeAttribute("nuevoGobierno2");
					return true;
				}
				if(!organizacionDAO.actualizar(gobierno)){
					setMensaje(organizacionDAO.getMensaje());
					restaurarBeansGobierno(request,gobierno2);
					return false;
				}
				setMensaje(REP_OK);
				recargarBeansGobierno(request,gobierno);
				session.removeAttribute("nuevoGobierno");
				session.removeAttribute("nuevoGobierno2");
		return true;
	}
	*/
	
	public boolean actualizarRegistroAsociacion(HttpServletRequest request,HttpSession session,AsociacionVO asociacionVO,AsociacionVO asociacionVO2) throws ServletException, IOException{
			if(compararFichasAsociacion(asociacionVO,asociacionVO2)){
				setMensaje(REP_OK);
				return true;
			}
			if(!organizacionDAO.actualizar(asociacionVO)){
				setMensaje(organizacionDAO.getMensaje());
				restaurarBeansAsociacion(request,asociacionVO2);
				return false;
			}
			setMensaje(REP_OK);
			recargarBeansAsociacion(request,asociacionVO);
			return true;
		}
	
	public boolean actualizarRegistroAsociacionIntegrantes(HttpServletRequest request,HttpSession session,AsociacionIntegrantesVO asociacionIntegrantesVO,AsociacionIntegrantesVO asociacionIntegrantesVO2) throws ServletException, IOException{
		if(compararFichasAsociacionIntegrantes(asociacionIntegrantesVO,asociacionIntegrantesVO2)){
			setMensaje(REP_OK);
			return true;
		}
		if(!organizacionDAO.actualizar(asociacionIntegrantesVO,asociacionIntegrantesVO2)){
			setMensaje(organizacionDAO.getMensaje());
			restaurarBeansAsociacionIntegrantes(request,asociacionIntegrantesVO2);
			return false;
		}
		setMensaje(REP_OK);
		recargarBeansAsociacionIntegrantes(request,asociacionIntegrantesVO);
		return true;
	}
	
	public boolean actualizarRegistroConsejoAcademico(HttpServletRequest request,HttpSession session,ConsejoAcademicoVO consejoAcademicoVO,ConsejoAcademicoVO consejoAcademicoVO2) throws ServletException, IOException{
		if(compararFichasConsejoAcademico(consejoAcademicoVO,consejoAcademicoVO2)){
			setMensaje(REP_OK);
			return true;
		}
		if(!organizacionDAO.actualizar(consejoAcademicoVO,consejoAcademicoVO2)){
			setMensaje(organizacionDAO.getMensaje());
			restaurarBeansConsejoAcademico(request,consejoAcademicoVO2);
			return false;
		}
		setMensaje(REP_OK);
		recargarBeansConsejoAcademico(request,consejoAcademicoVO);
		return true;
	}
	
	public boolean actualizarRegistroConsejoDirectivo(HttpServletRequest request,HttpSession session,ConsejoDirectivoVO consejoDirectivoVO,ConsejoDirectivoVO consejoDirectivoVO2) throws ServletException, IOException{
		if(compararFichasConsejoDirectivo(consejoDirectivoVO,consejoDirectivoVO2)){
			setMensaje(REP_OK);
			return true;
		}
		if(!organizacionDAO.actualizar(consejoDirectivoVO,consejoDirectivoVO2)){
			setMensaje(organizacionDAO.getMensaje());
			restaurarBeansConsejoDirectivo(request,consejoDirectivoVO2);
			return false;
		}
		setMensaje(REP_OK);
		recargarBeansConsejoDirectivo(request,consejoDirectivoVO);
		return true;
	}
	
	public boolean actualizarRegistroLider(HttpServletRequest request,HttpSession session,LiderVO liderVO,LiderVO liderVO2) throws ServletException, IOException{
		if(compararFichasLider(liderVO,liderVO2)){
			setMensaje(REP_OK);
			return true;
		}
		if(!organizacionDAO.actualizar(liderVO,liderVO2)){
			setMensaje(organizacionDAO.getMensaje());
			restaurarBeansLider(request,liderVO2);
			return false;
		}
		setMensaje(REP_OK);
		recargarBeansLider(request,liderVO);
		return true;
	}
	
/**
 *	inserta las cinco fichas en la base de datos
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	/*
	public boolean insertarRegistroGobierno(HttpServletRequest request,HttpSession session,Gobierno gobierno,Gobierno gobierno2) throws ServletException, IOException{
		if(!organizacionDAO.insertar(gobierno)){
		    setMensaje(organizacionDAO.getMensaje());
		    return false;
		}
		gobierno.setEstado("1");
		session.removeAttribute("nuevoGobierno");
		session.removeAttribute("nuevoGobierno2");
		setMensaje(REP_OK);
		return true;
	}
	*/

	public boolean insertarRegistroAsociacion(HttpServletRequest request,HttpSession session,AsociacionVO asociacionVO,AsociacionVO asociacionVO2) throws ServletException, IOException{
		if(!organizacionDAO.insertar(asociacionVO)){
		    setMensaje(organizacionDAO.getMensaje());
		    return false;
		}
		asociacionVO.setAsoEstado("1");
		setMensaje(REP_OK);
		return true;
	}
	
	public boolean insertarRegistroAsociacionIntegrantes(HttpServletRequest request,HttpSession session,AsociacionIntegrantesVO asociacionIntegrantesVO,AsociacionIntegrantesVO asociacionIntegrantesVO2) throws ServletException, IOException{
		if(!organizacionDAO.insertar(asociacionIntegrantesVO)){
		    setMensaje(organizacionDAO.getMensaje());
		    return false;
		}
		session.removeAttribute("asociacionIntegrantesVO");
		session.removeAttribute("asociacionIntegrantesVO2");
		setMensaje(REP_OK);
		return true;
	}
	
	public boolean insertarRegistroConsejoAcademico(HttpServletRequest request,HttpSession session,ConsejoAcademicoVO consejoAcademicoVO,ConsejoAcademicoVO consejoAcademicoVO2) throws ServletException, IOException{
		if(!organizacionDAO.insertar(consejoAcademicoVO)){
		    setMensaje(organizacionDAO.getMensaje());
		    return false;
		}
		session.removeAttribute("consejoAcademicoVO");
		session.removeAttribute("consejoAcademicoVO2");
		setMensaje(REP_OK);
		return true;
	}
	
	public boolean insertarRegistroConsejoDirectivo(HttpServletRequest request,HttpSession session,ConsejoDirectivoVO consejoDirectivoVO,ConsejoDirectivoVO consejoDirectivoVO2) throws ServletException, IOException{
		if(!organizacionDAO.insertar(consejoDirectivoVO)){
		    setMensaje(organizacionDAO.getMensaje());
		    return false;
		}
		session.removeAttribute("consejoDirectivoVO");
		session.removeAttribute("consejoDirectivoVO2");
		setMensaje(REP_OK);
		return true;
	}
	
	public boolean insertarRegistroLider(HttpServletRequest request,HttpSession session,LiderVO liderVO,LiderVO liderVO2) throws ServletException, IOException{
		if(!organizacionDAO.insertar(liderVO)){
		    setMensaje(organizacionDAO.getMensaje());
		    return false;
		}
		session.removeAttribute("liderVO");
		session.removeAttribute("liderVO2");
		setMensaje(REP_OK);
		return true;
	}
	
/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request,HttpSession session){
		session.removeAttribute("nuevoGobierno");
		session.removeAttribute("nuevoGobierno2");
	}


	
	/**
	 *	Referencia al bean del usuario con la información proporcionada por el bean de respaldo
	 *	@param int n
	 *	@param HttpServletRequest request
	 */
		public void restaurarBeansAsociacion(HttpServletRequest request,AsociacionVO asociacion2) throws ServletException, IOException{
			request.getSession().setAttribute("nuevoAsociacion",(AsociacionVO)asociacion2.clone());
		}
		
		public void restaurarBeansAsociacionIntegrantes(HttpServletRequest request,AsociacionIntegrantesVO asociacionIntegrantesVO2) throws ServletException, IOException{
			request.getSession().setAttribute("asociacionIntegrantesVO",(AsociacionIntegrantesVO)asociacionIntegrantesVO2.clone());
		}
		
		public void restaurarBeansConsejoAcademico(HttpServletRequest request,ConsejoAcademicoVO consejoAcademicoVO2) throws ServletException, IOException{
			request.getSession().setAttribute("consejoAcademicoVO",(ConsejoAcademicoVO)consejoAcademicoVO2.clone());
		}
		
		public void restaurarBeansConsejoDirectivo(HttpServletRequest request,ConsejoDirectivoVO consejoDirectivoVO2) throws ServletException, IOException{
			request.getSession().setAttribute("consejoDirectivoVO",(ConsejoDirectivoVO)consejoDirectivoVO2.clone());
		}
		
		public void restaurarBeansLider(HttpServletRequest request,LiderVO liderVO2) throws ServletException, IOException{
			request.getSession().setAttribute("liderVO",(LiderVO)liderVO2.clone());
		}
	/**
	 *	Referencia al bean de respaldo con la nueva información proporcionada por el bean modificado por el usuario
	 *	@param int n
	 *	@param HttpServletRequest request
	 */
		public void recargarBeansAsociacion(HttpServletRequest request,AsociacionVO asociacion) throws ServletException, IOException{
					request.getSession().setAttribute("nuevoAsociacion2",(AsociacionVO)asociacion.clone());
		}
		
		public void recargarBeansAsociacionIntegrantes(HttpServletRequest request,AsociacionIntegrantesVO asociacionIntegrantesVO) throws ServletException, IOException{
			request.getSession().setAttribute("asociacionIntegrantesVO2",(AsociacionIntegrantesVO)asociacionIntegrantesVO.clone());
		}
		
		public void recargarBeansConsejoDirectivo(HttpServletRequest request,ConsejoDirectivoVO consejoDirectivoVO) throws ServletException, IOException{
			request.getSession().setAttribute("consejoDirectivoVO2",(ConsejoDirectivoVO)consejoDirectivoVO.clone());
		}
		
		public void recargarBeansConsejoAcademico(HttpServletRequest request,ConsejoAcademicoVO consejoAcademicoVO) throws ServletException, IOException{
			request.getSession().setAttribute("consejoAcademicoVO2",(ConsejoAcademicoVO)consejoAcademicoVO.clone());
		}

		public void recargarBeansLider(HttpServletRequest request,LiderVO liderVO) throws ServletException, IOException{
			request.getSession().setAttribute("liderVO2",(LiderVO)liderVO.clone());
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
		mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		mensaje+="  - "+s+"\n";
	}

/**
*	Retorna una variable que contiene los mensajes que se van a enviar a la vista
*	@return String	
**/
	public String getMensaje(){
		return mensaje;
	}
}