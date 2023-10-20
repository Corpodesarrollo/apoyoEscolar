package siges.integracion;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.integracion.beans.Params;
import siges.dao.Cursor;
import siges.integracion.dao.IntegracionDAO;
import siges.integracion.dao.MatriculasDAO;
import siges.integracion.beans.Colegio;
import siges.integracion.beans.Datos;
import siges.integracion.beans.Estudiante;
import siges.integracion.beans.Sede;
import siges.util.Logger;

public class ControllerIntegrador extends HttpServlet{
	public static final long serialVersionUID=1; 
	private Thread t=null,t2=null;
	private MatriculasDAO matriculasDAO=new MatriculasDAO(new Cursor(2));

	public void init(ServletConfig config) throws ServletException {
		System.out.println("INTEGRACION. INICIANDO");
		super.init(config);
		t = new Thread(new Integrador(matriculasDAO,1));
		t.setName("Integracion1.");
		t.start();
		t2 = new Thread(new Integrador(matriculasDAO,2));
		t2.setName("Integracion2.");
		t2.start();
	}

	public void destroy() {
		Integrador.setRunning(false);
	}

}
