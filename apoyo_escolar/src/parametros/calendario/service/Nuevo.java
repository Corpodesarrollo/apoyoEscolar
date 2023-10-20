package parametros.calendario.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import parametros.calendario.dao.CalendarioDao;
import parametros.calendario.vo.CalendarioVO;
import parametros.calendario.vo.FiltroCalendarioVO;
import parametros.calendario.vo.ParamsVO;
import participacion.common.exception.ParticipacionException;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Nuevo extends Service{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String FICHA_CALENDARIO;
	private CalendarioDao calendarioDAO = new CalendarioDao(new Cursor());
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	}
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String dispatcher[]=new String[2];
		String FICHA=null;
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");

		
		FICHA_CALENDARIO=config.getInitParameter("FICHA_CALENDARIO");
		
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		
		FiltroCalendarioVO filtroCalendario=(FiltroCalendarioVO)session.getAttribute("filtroCalendarioVO");
		CalendarioVO calendario = (CalendarioVO)session.getAttribute("calendarioVO");
		String fecha = (String) request.getParameter("fechaEditar");
		if(calendario == null)
			calendario = new CalendarioVO();
		
		FICHA=FICHA_CALENDARIO;	
		
		switch (CMD){
			case ParamsVO.CMD_BUSCAR:
				calendarioNuevo(request,session,calendario);
				calendarioBuscar(request,session, filtroCalendario);
			break;	
			case ParamsVO.CMD_EDITAR:
				calendarioEditar(request,session,fecha, filtroCalendario);
				calendarioBuscar(request,session, filtroCalendario);
			break;	
			case ParamsVO.CMD_ACTUALIZAR:
				calendarioActualizar(request,session,calendario);
				calendarioNuevo(request,session,calendario);
				calendarioBuscar(request,session, filtroCalendario);			
			break;	
			case ParamsVO.CMD_GUARDAR:
				calendarioGuardar(request,session,calendario, filtroCalendario);
			break;		
			case ParamsVO.CMD_NUEVO:
				calendario = new CalendarioVO();
				calendarioNuevo(request,session,calendario);
			break;	
		}
		actaInit(request, session, usuVO, filtroCalendario, calendario);


		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}

	private void calendarioActualizar(HttpServletRequest request,
			HttpSession session, CalendarioVO calendario) {
		try {
			calendarioDAO.updateFecha(calendario.getFecha(), calendario.getMotivo());
			request.setAttribute(ParamsVO.SMS,"La fecha fue actualizada exitosamente");
		}  catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
		}
	}

	private void calendarioEditar(HttpServletRequest request,
			HttpSession session, String fecha, FiltroCalendarioVO filtro) {
		try {
			session.setAttribute("calendarioVO", calendarioDAO.buscarFecha(fecha));
		}  catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
		}
	}

	private void calendarioBuscar(HttpServletRequest request,
			HttpSession session, FiltroCalendarioVO filtroCalendario) {
		try {
			request.setAttribute("listaCalendario", calendarioDAO.getListaInstancia(getMonthName(filtroCalendario.getMes())));
		}  catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
		}
	}

	/**
	 * Guarda una nueva fecha festiva
	 */
	private void calendarioGuardar(HttpServletRequest request,
			HttpSession session, CalendarioVO calendario, FiltroCalendarioVO filtro) {
		try {
			if(calendarioDAO.nuevaFecha(calendario)){
				request.setAttribute(ParamsVO.SMS,"La fecha fue guardada exitosamente");
				calendario = new CalendarioVO();
				calendarioNuevo(request,session,calendario);
			}
			else
				request.setAttribute(ParamsVO.SMS,"La fecha ya ha sido registrada. No se guardo el nuevo registro");
			request.setAttribute("listaCalendario", session.getAttribute("listaCalendario"));
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
		}
	}

	private void actaInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroCalendarioVO filtroCalendario,
			CalendarioVO calendario) {
		try {
			if(filtroCalendario == null){
				filtroCalendario = new FiltroCalendarioVO();
				/**
				 * Por defecto se selecciona el mes actual
				 */
				int month = Calendar.getInstance().get(Calendar.MONTH);
				filtroCalendario.setMes(month+1);
				session.setAttribute("filtroCalendarioVO", filtroCalendario);
			}
			/**
			 * Se llena la lista con los meses del ano
			 * Por defecto se selecciona el mes actual
			 */	
			request.setAttribute("listaMeses", getMeses());
			
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
			e.printStackTrace();
		}
	}
	
	/**
	 * Dado el nnmero de mes, retorna el nombre del respectivo mes
	 * @param month
	 * @return
	 */
	private String getMonthName(int month) {
		if(month == 1)
			return "ENERO";
		else if(month == 2)
			return "FEBRERO";
		else if(month == 3)
			return "MARZO";
		else if(month == 4)
			return "ABRIL";
		else if(month == 5)
			return "MAYO";
		else if(month == 6)
			return "JUNIO";
		else if(month == 7)
			return "JULIO";
		else if(month == 8)
			return "AGOSTO";
		else if(month == 9)
			return "SEPTIEMBRE";
		else if(month == 10)
			return "OCTUBRE";
		else if(month == 11)
			return "NOVIEMBRE";
		else if(month == 11)
			return "DICIEMBRE";
		else
			return "TODOS";
	}

	/**
	 * Retorna una lista con todos los meses del ano
	 * se incluye la opcion de "TODOS"
	 */
	public List getMeses() throws Exception{
		ItemVO item=null;
		List l=new ArrayList();
		try{
			item=new ItemVO();
			item.setCodigo(1);
			item.setNombre("ENERO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(2);
			item.setNombre("FEBRERO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(3);
			item.setNombre("MARZO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(4);
			item.setNombre("ABRIL");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(5);
			item.setNombre("MAYO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(6);
			item.setNombre("JUNIO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(7);
			item.setNombre("JULIO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(8);
			item.setNombre("AGOSTO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(9);
			item.setNombre("SEPTIEMBRE");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(10);
			item.setNombre("OCTUBRE");
			l.add(item);
		
			item=new ItemVO();
			item.setCodigo(11);
			item.setNombre("NOVIEMBRE");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(12);
			item.setNombre("DICIEMBRE");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(13);
			item.setNombre("TODOS");
			l.add(item);
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}
		return l;
	}

	private void calendarioNuevo(HttpServletRequest request,
			HttpSession session, CalendarioVO calendario) throws ServletException {
		try{
			calendario = new CalendarioVO();
			calendario.setEstado(1); //Nuevo registro
			session.setAttribute("calendarioVO", calendario);
			request.setAttribute("listaCalendario", null);
		}catch(Exception e){
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		
	}

}
