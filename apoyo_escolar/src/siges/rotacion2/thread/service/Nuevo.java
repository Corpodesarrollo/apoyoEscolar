/**
 * 
 */
package siges.rotacion2.thread.service;

import javax.servlet.ServletConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import siges.rotacion2.thread.thread.Rotacion2Thread;

/**
 * 31/08/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo  extends HttpServlet{
	public static final long serialVersionUID = 1;
    private Thread t;

    public void init(ServletConfig config) throws ServletException {
		System.out.println("ROTACION INICIANDO");
		super.init(config);
		t = new Thread(new Rotacion2Thread());
		t.setName("Rotacion.");
		t.start();
	}

	public void destroy() {
		Rotacion2Thread.setRunning(false);
	}
}
