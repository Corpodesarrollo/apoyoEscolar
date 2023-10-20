/**
 * 
 */
package articulacion.importarEvaluacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.common.service.Service;
import siges.common.vo.Params;

/**
 * 27/11/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Lista extends Service {

	private String FICHA_NUEVO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		// System.out.println("PAGINA DE REDIRECCION:"+FICHA_NUEVO);
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_NUEVO;
		return dispatcher;
	}
}
