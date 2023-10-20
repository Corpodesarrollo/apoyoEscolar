package siges.institucion.correoLider;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.institucion.correoLider.beans.ParamsVO;
import siges.institucion.correoLider.dao.CorreoDAO;
import siges.login.beans.Login;

/**
 * 24/08/2007 
 * @author Latined
 * @version 1.2
 */
public class File extends Service{
	private String FICHA;
	private String FICHA_CORREO;
	private Cursor cursor;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		int CMD=getCmd(request,session,ParamsVO.CMD_FILE_ADJUNTO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		FICHA_CORREO=config.getInitParameter("FICHA_CORREO");
		System.out.println("LOS VALORES(file)="+CMD+"//"+TIPO);
		String params[]=null;
		try{
			switch(TIPO){
				case ParamsVO.FICHA_CORREO:
					FICHA=FICHA_CORREO;
					switch(CMD){
						case ParamsVO.CMD_FILE_ADJUNTO:
							//request.setAttribute("lInstitucion",correoDAO.getAjaxInstitucion(Integer.parseInt(params[0])));
							Login usuVO=(Login)session.getAttribute("login");
							String file=subirFile(request,usuVO);
							if(file==null){
								request.setAttribute("fileBandera","-1");
							}else{
								request.setAttribute("fileBandera","1");	
								request.setAttribute("fileRuta",file);
							}
							request.setAttribute("fileParam",String.valueOf(CMD));
						break;
					}
				break;
			}
			dispatcher[0]=String.valueOf(Params.INCLUDE);
			dispatcher[1]=FICHA;
			return dispatcher;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}
	
	private String subirFile(HttpServletRequest request,Login login){
		ResourceBundle rb = ResourceBundle.getBundle("siges.institucion.correoLider.bundle.correoLider");
		String pathSubir = Ruta.get(context, rb.getString("correo.pathSubir") + "." + login.getUsuarioId());
		String pathTemporal = Ruta.get(context, rb.getString("correo.temporal"));
		List fileItems;
		Iterator i;
		String fileName = null;
		FileItem item = null;
		try {
//			DiskFileUpload fu = new DiskFileUpload();
//			fileItems = fu.parseRequest(request, 4096, 1024 * 1024, pathTemporal);
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			/* SUBIR LOS ARCHIVOS */
			i = fileItems.iterator();
			while (i.hasNext()) {
				fileName = null;
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					fileName = item.getName();
					if (fileName.trim().compareTo("") != 0) {
						if (item.getFieldName().equals("archivo")) {
							java.io.File f = new java.io.File(pathSubir);
							if (!f.exists()){ FileUtils.forceMkdir(f); }
							fileName=fileName.substring(fileName.lastIndexOf('/')+1,fileName.length());
							fileName=fileName.substring(fileName.lastIndexOf('\\')+1,fileName.length());
							System.out.println(java.io.File.separator+"fila==="+fileName);
							f = new java.io.File(pathSubir + fileName);
							item.write(f);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileName;
	}
}
