package siges.institucion;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.common.vo.FiltroCommonVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.gestionAdministrativa.boletinPublico.vo.EstudianteVO;
import siges.institucion.beans.FichaVO;
import siges.institucion.beans.GradoItemVO;
import siges.institucion.beans.JorndItemVO;
import siges.institucion.beans.SedeItemVO;
import siges.institucion.dao.FichaDAO;
import siges.login.beans.Login;
import siges.personal.beans.Personal;

/**
 * Nombre: ControllerNuevoEdit Descripcion: Controla el formulario de nuevo
 * registro por parte de la orientadora Parametro de entrada: HttpServletRequest
 * request, HttpServletResponse response Parametro de salida: HttpServletRequest
 * request, HttpServletResponse response Funciones de la pagina: Procesar la
 * peticion y enviar el control al formulario de nuevo registro Entidades
 * afectadas: Tablas maestras en modo de solo lectura Fecha de modificacinn:
 * 01/12/04
 * 
 * @author Pasantes UD
 * @version $v 1.2 $
 */
public class ControllerFichaEdit extends HttpServlet {
	private Cursor cursor;// objeto que maneja las sentencias sql
	private FichaDAO fichaDAO;//
	private ResourceBundle rb;
	private final int TIPO_FICHA1 = 1;
	private final int TIPO_FICHA2 = 2;
	private final int TIPO_FICHA3 = 3;
	private final int TIPO_DEFAULT = TIPO_FICHA1;
	private final String sms = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - ";

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Login login;
		FichaVO fichaVO;
		String err;
		rb = ResourceBundle.getBundle("siges.institucion.bundle.institucion");
		String er;
		String home;
		int tipo;
		String sig = getServletConfig().getInitParameter("sig");
		String sig2 = getServletConfig().getInitParameter("sig2");
		String sig3 = getServletConfig().getInitParameter("sig3");
		String ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		err = null;
		try {
			fichaDAO = new FichaDAO(cursor);
			tipo = getTipo(request);
			if (!asignarBeans(request)) {
				request.setAttribute("mensaje", sms
						+ "Error capturando datos de sesinn para el usuario");
				return (er);
			}
			login = (Login) request.getSession().getAttribute("login");
			// fichaVO=(FichaVO)request.getSession().getAttribute("fichaVO");
			fichaVO = new FichaVO();
			fichaVO.setFichaInstitucion(login.getInstId());

			switch (tipo) {
			case TIPO_FICHA1:
				err = editarFicha1(request, fichaVO);
				sig = sig;
				break;
			case TIPO_FICHA2:
				err = editarFicha2(request, fichaVO);
				sig = sig2;
				break;
			case TIPO_FICHA3:
				err = editarFicha3(request, fichaVO);
				sig = sig3;
				break;
			}
			if (err != null) {
				request.setAttribute("mensaje", err);
				return er;
			}
			return sig;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
		}
	}

	public int getTipo(HttpServletRequest request) {
		int tipo_ = TIPO_DEFAULT;
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			borrarBeans(request);
			request.getSession().removeAttribute("editar");
			tipo_ = 1;
		} else {
			tipo_ = Integer.parseInt((String) request.getParameter("tipo"));
		}
		return tipo_;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("fichaVO");
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("login") == null)
			return false;
		return true;
	}

	public String editarFicha1(HttpServletRequest request, FichaVO fichaVO)
			throws ServletException, IOException {
		String retorno = null;
		try {
			fichaVO = fichaDAO.getFicha1(fichaVO);
			fichaVO.setFichaTotDoc(""
					+ fichaDAO.getTotalDocentesInst(Long.parseLong(fichaVO
							.getFichaInstitucion())));
			request.getSession().setAttribute("fichaVO", fichaVO);
		} catch (Exception e) {
			e.printStackTrace();
			retorno = "" + e;
			return retorno;
		}
		return retorno;
	}

	/**
	 * @param request
	 * @param fichaVO
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editarFicha2(HttpServletRequest request, FichaVO fichaVO)
			throws ServletException, IOException {
		String retorno = null;
		try {

			fichaVO = fichaDAO.getFicha2(fichaVO);
			long codInst = Long.parseLong(fichaVO.getFichaInstitucion());

			/*
			 * 1. TOTAL DOCENTES
			 */

			fichaVO.setFichaTotDoc("" + fichaDAO.getTotalDocentesInst(codInst));

			// obtener sedes
			List listaItem = fichaDAO.getListaSede(codInst);

			for (Iterator iter = listaItem.iterator(); iter.hasNext();) {
				ItemVO element = (ItemVO) iter.next();
				SedeItemVO sedeItemVO = new SedeItemVO(element.getCodigo(),
						element.getNombre());

				// * Obtener jornadas
				List listaJord = fichaDAO.getListaJornd(codInst,
						sedeItemVO.getSedCodigo());
				for (Iterator iter_ = listaJord.iterator(); iter_.hasNext();) {

					ItemVO element_ = (ItemVO) iter_.next();
					JorndItemVO jorndItemVO = new JorndItemVO(
							element_.getCodigo(), element_.getNombre());

					/*
					 * 2. DOCENTES POR GnNERO
					 */
					obtenerDocenteXgenero(sedeItemVO, jorndItemVO, fichaVO);

					/*
					 * 3. DOCENTES POR EDAD Y GnNERO
					 */
					obtenerDocenteXgeneroEdades(sedeItemVO, jorndItemVO,
							fichaVO);

					sedeItemVO.getListaJornada().add(jorndItemVO);
				}
				fichaVO.getListaSede().add(sedeItemVO);
			}

			request.getSession().removeAttribute("fichaVO");
			request.getSession().setAttribute("fichaVO", fichaVO);

		} catch (Exception e) {
			e.printStackTrace();
			retorno = "" + e;
			return retorno;
		}
		return retorno;
	}

	public String editarFicha3(HttpServletRequest request, FichaVO fichaVO)
			throws ServletException, IOException {
		String retorno = null;
		try {
			fichaVO = fichaDAO.getFicha1(fichaVO);
			fichaVO = fichaDAO.getFicha3(fichaVO);
			long codInst = Long.parseLong(fichaVO.getFichaInstitucion());

			// obtener sedes
			List listaItem = fichaDAO.getListaSede(codInst);

			for (Iterator iter = listaItem.iterator(); iter.hasNext();) {
				ItemVO element = (ItemVO) iter.next();
				SedeItemVO sedeItemVO = new SedeItemVO(element.getCodigo(),
						element.getNombre());

				// * Obtener jornadas
				List listaJord = fichaDAO.getListaJornd(codInst,
						sedeItemVO.getSedCodigo());
				for (Iterator iter_ = listaJord.iterator(); iter_.hasNext();) {

					ItemVO element_ = (ItemVO) iter_.next();
					JorndItemVO jorndItemVO = new JorndItemVO(
							element_.getCodigo(), element_.getNombre());

					FiltroCommonVO filtro = new FiltroCommonVO();
					filtro.setFilinst((int) codInst);
					filtro.setFilsede((int) sedeItemVO.getSedCodigo());
					filtro.setFiljornd((int) jorndItemVO.getJorCodigo());

					List listaGrado = fichaDAO.getListaGrado(filtro);
					for (Iterator iter_gra = listaGrado.iterator(); iter_gra
							.hasNext();) {
						ItemVO element_gra = (ItemVO) iter_gra.next();
						GradoItemVO gradoItemVO = new GradoItemVO(
								element_gra.getCodigo(),
								element_gra.getNombre(),
								(int) element_gra.getPadre());
						/*
						 * Obtener conteo de estudiantes
						 */
						obtenerEstudianteXgenero(sedeItemVO, jorndItemVO,
								gradoItemVO, fichaVO);
						jorndItemVO.getListaGrado().add(gradoItemVO);
					}
					sedeItemVO.getListaJornada().add(jorndItemVO);
				}

				/*
				 * Espacios fisicos
				 */

				List listaEspacio = fichaDAO.getListaEspacios(
						Long.parseLong(fichaVO.getFichaInstitucion()),
						sedeItemVO.getSedCodigo());

				if (listaEspacio.size() > 0) {
					sedeItemVO.setListaEspacio(listaEspacio);
					sedeItemVO.setBanderaEspacio(1);
					fichaVO.addTotalConsoEspacio(sedeItemVO.getTotalEspacio());
				}

				fichaVO.getListaSede().add(sedeItemVO);

			}

			/*
			 * Estudiante por grado y genero
			 */

			FiltroCommonVO filtro = new FiltroCommonVO();
			filtro.setFilinst((int) codInst);
			filtro.setFilsede((int) -9);
			filtro.setFiljornd((int) -9);
			List listaGrado = fichaDAO.getListaGrados(filtro);
			for (Iterator iter_gra = listaGrado.iterator(); iter_gra.hasNext();) {
				ItemVO element_gra = (ItemVO) iter_gra.next();
				GradoItemVO gradoItemVO = new GradoItemVO(
						element_gra.getCodigo(), element_gra.getNombre(),
						(int) element_gra.getPadre());
				/*
				 * Obtener conteo de estudiantes
				 */
				obtenerEstudianteXgeneroGrado(gradoItemVO, fichaVO);
				fichaVO.getListaGradoGenero().add(gradoItemVO);
			}

			request.getSession().removeAttribute("fichaVO");
			request.getSession().setAttribute("fichaVO", fichaVO);

		} catch (Exception e) {
			e.printStackTrace();
			retorno = "" + e;
			return retorno;
		}
		return retorno;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * @param sedeItemVO
	 * @param jorndItemVO
	 * @param codInst
	 * @return
	 */
	private void obtenerDocenteXgenero(SedeItemVO sedeItemVO,
			JorndItemVO jorndItemVO, FichaVO fichaVO) throws Exception {
		try {

			long codInst = Long.parseLong(fichaVO.getFichaInstitucion());

			long totalM = fichaDAO.getCountDocentes(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_MASCULINO);
			long totalF = fichaDAO.getCountDocentes(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_FEMENINO);

			jorndItemVO.setTotalF(totalF);
			jorndItemVO.setTotalM(totalM);

			fichaVO.addTotalConsolGeneroF(totalM);
			fichaVO.addTotalConsolGeneroM(totalF);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	/**
	 * @param sedeItemVO
	 * @param jorndItemVO
	 * @param fichaVO
	 * @throws Exception
	 */
	private void obtenerDocenteXgeneroEdades(SedeItemVO sedeItemVO,
			JorndItemVO jorndItemVO, FichaVO fichaVO) throws Exception {
		try {
			long codInst = Long.parseLong(fichaVO.getFichaInstitucion());

			long countM = 0;
			long countF = 0;

			long edadIni = 0;
			long edadFin = 0;

			// Rango de edad 20-30
			edadIni = 20;
			edadFin = 30;

			countM = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_MASCULINO, edadIni, edadFin);
			countF = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_FEMENINO, edadIni, edadFin);
			// System.out.println("countM " + countM);
			jorndItemVO.setTotalEdadM_20_30(countM);
			jorndItemVO.setTotalEdadF_20_30(countF);

			fichaVO.addTotalConsolEdadM_20_30(countM);
			fichaVO.addTotalConsolEdadF_20_30(countF);

			// Rango de edad 30-40
			edadIni = 30;
			edadFin = 40;

			countM = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_MASCULINO, edadIni, edadFin);
			countF = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_FEMENINO, edadIni, edadFin);
			jorndItemVO.setTotalEdadM_30_40(countM);
			jorndItemVO.setTotalEdadF_30_40(countF);

			fichaVO.addTotalConsolEdadM_30_40(countM);
			fichaVO.addTotalConsolEdadF_30_40(countF);

			// Rango de edad 40-50
			edadIni = 40;
			edadFin = 50;

			countM = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_MASCULINO, edadIni, edadFin);
			countF = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_FEMENINO, edadIni, edadFin);
			jorndItemVO.setTotalEdadM_40_50(countM);
			jorndItemVO.setTotalEdadF_40_50(countF);

			fichaVO.addTotalConsolEdadM_40_50(countM);
			fichaVO.addTotalConsolEdadF_40_50(countF);

			// Rango de edad 50-MAS
			edadIni = 50;
			edadFin = 500;

			countM = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_MASCULINO, edadIni, edadFin);
			countF = fichaDAO.getCountDocentesEdad(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					Personal.GENERO_FEMENINO, edadIni, edadFin);
			jorndItemVO.setTotalEdadM_50_mas(countM);
			jorndItemVO.setTotalEdadF_50_mas(countF);

			fichaVO.addTotalConsolEdadM_50_mas(countM);
			fichaVO.addTotalConsolEdadF_50_mas(countF);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	/**
	 * @param sedeItemVO
	 * @param jorndItemVO
	 * @param gradoItemVO
	 * @param fichaVO
	 */
	private void obtenerEstudianteXgenero(SedeItemVO sedeItemVO,
			JorndItemVO jorndItemVO, GradoItemVO gradoItemVO, FichaVO fichaVO)
			throws Exception {

		try {
			long totalEstM = 0;
			long totalEstF = 0;
			long codInst = Long.parseLong(fichaVO.getFichaInstitucion());
			totalEstM = fichaDAO.getCountEst(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					gradoItemVO.getGraCodigo(),
					gradoItemVO.getGraMetodCodigo(),
					EstudianteVO.GENERO_MASCULINO);

			totalEstF = fichaDAO.getCountEst(codInst,
					sedeItemVO.getSedCodigo(), jorndItemVO.getJorCodigo(),
					gradoItemVO.getGraCodigo(),
					gradoItemVO.getGraMetodCodigo(),
					EstudianteVO.GENERO_FEMENINO);

			gradoItemVO.setTotalEstM(totalEstM);
			gradoItemVO.setTotalEstF(totalEstF);

			fichaVO.addTotalConsolEstGeneroM(totalEstM);
			fichaVO.addTotalConsolEstGeneroF(totalEstF);
			// System.out.println("fichaVO.addTotalConsolEstGeneroM( " +
			// fichaVO.getTotalConsolEstGeneroM());
			// System.out.println("fichaVO.addTotalConsolEstGeneroF( " +
			// fichaVO.getTotalConsolEstGeneroF());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

	}

	/**
	 * @param sedeItemVO
	 * @param jorndItemVO
	 * @param gradoItemVO
	 * @param fichaVO
	 * @throws Exception
	 */
	private void obtenerEstudianteXgeneroGrado(GradoItemVO gradoItemVO,
			FichaVO fichaVO) throws Exception {
		// System.out.println("obtenerEstudianteXgeneroGrado " );

		try {
			long totalEstM = 0;
			long totalEstF = 0;
			long codInst = Long.parseLong(fichaVO.getFichaInstitucion());
			totalEstM = fichaDAO.getCountEst(codInst, -99, -99,
					gradoItemVO.getGraCodigo(),
					gradoItemVO.getGraMetodCodigo(),
					EstudianteVO.GENERO_MASCULINO);

			totalEstF = fichaDAO.getCountEst(codInst, -99, -99,
					gradoItemVO.getGraCodigo(),
					gradoItemVO.getGraMetodCodigo(),
					EstudianteVO.GENERO_FEMENINO);

			gradoItemVO.setTotalEstGradoM(totalEstM);
			gradoItemVO.setTotalEstGradoF(totalEstF);

			// System.out.println("totalEstM " + totalEstM);
			// System.out.println("totalEstF " + totalEstF);
			fichaVO.addTotalConsolEstGeneroGradoM(totalEstM);
			fichaVO.addTotalConsolEstGeneroGradoF(totalEstF);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

	}
}
