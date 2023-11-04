package siges.dao;

import javax.servlet.ServletContext;

/**
*	Nombre:	Ruta<br>
*	Descripcinn:	Convetir rutas relativas en absolutas<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public final class Ruta2 {
    public final static char sep = java.io.File.separatorChar;

/**
*	constructor de la clase
**/
	private Ruta2(){}

	/**
 *	Retorna la direccinn absoluta de disco hasta la raiz de la aplicacion, incluyendo la direccion enviada en 'dir'
 *	@param HttpServletRequest req
 *	@param String dir
 *	@return String 
 */
	public static String get(ServletContext sc,String dir){
		String ruta=null;
		ruta = sc.getRealPath("/");
		String ultimoCarater=ruta.substring(ruta.length() - 1);
		if(!ultimoCarater.equals("\\") && !ultimoCarater.equals("/")){
			if(ruta.contains("/")){
				ruta = ruta+"/";
			}else{
				ruta = ruta+"\\";
			}
		}
		if(ruta.substring(ruta.length()-1).equals("/")){
			ruta+=dir.replace('.','/')+"/";
		}else{
			ruta+=dir.replace('.','\\')+"\\";
		}	
		return ruta;		
	}	

	public static String get(String ruta,String dir){
		System.out.println("RUTA: "+ruta + " DIR: "+dir);
		java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("path");
		int val = Integer.parseInt(rb.getString("path.contexto"));
		if (val != 1) {
			ruta = rb.getString("path.base").replace('.', sep);
			// ruta = base;
		}

		if (ruta.substring(ruta.length() - 1).equals("/")) {
			ruta += dir.replace('.', '/') + "/";
		} else {
			ruta += dir.replace('.', '\\') + "\\";
		}
		return ruta;	
	}	
	
		/**
	 *	Retorna la direccinn absoluta de disco hasta la raiz de la aplicacion, incluyendo la direccion enviada en 'dir'
	 *	@param HttpServletRequest req
	 *	@param String dir
	 *	@return String 
	 */
	public static String getArchivo(ServletContext sc,String dir){
		String ruta=null;
		ruta = sc.getRealPath("/");
		String path=dir.substring(0,dir.lastIndexOf("."));
		String ext=dir.substring(dir.lastIndexOf("."),dir.length());
		if(ruta.substring(ruta.length()-1).equals("/")){				
			ruta+=path.replace('.','/');
		}else{
			path=path.replace('/','\\');
			ruta+=path.replace('.','\\');
		}
		return ruta+ext;		
	}	

	public static String getArchivo(String ruta,String dir){
		String path=dir.substring(0,dir.lastIndexOf("."));
		String ext=dir.substring(dir.lastIndexOf("."),dir.length());
		if(ruta.substring(ruta.length()-1).equals("/")){
			ruta+=path.replace('.','/');
		}else{
			path=path.replace('/','\\');
			ruta+=path.replace('.','\\');
		}
		return ruta+ext;		
	}
}
