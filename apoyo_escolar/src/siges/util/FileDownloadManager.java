package siges.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

 

/**
* Clase que centraliza las operaciones necesarias para obtener un archivo
* comprimido de la aplicación luego de estar cifrado por la misma
* 
* @author <i>José Luis Lopez Bernal</i> <br>
*         <a href=
*         "mailto:jose.lopez@linktic.com">jose.lopez@linktic.com</a><br>
*         <b><i>Linktic<br>
*         
* 
* @version 1.0
* 
* @date 06 de Agosto de 2023
*/
public class FileDownloadManager {

 

    private static final String CONTENT_TYPE = "application/octet-stream";
    private static final Logger log = Logger.getLogger(FileDownloadManager.class.getName());
    private static final int BUFFER_SIZE = 100000;

 

    /**
     * Metodo que escribe un archivo dada la ruta de origen y el OutputStream
     * 
     * @throws IOException en caso de error en la escritura del archivo
     */
    public synchronized void writeFile2HTTPResponse(final String sourceFile, final String targetFileName,
            final HttpServletResponse response) throws Exception, IllegalAccessException {

//    	    File zipFile = new File(sourceFile);
//    	    final Path path = Paths.get(sourceFile);
//    	        if (zipFile.exists()) {
//    	            response.setContentType("application/zip");
//    	            response.setHeader("Content-Disposition", "attachment; filename=\"" + targetFileName + "\"");
//
//    	            try (InputStream inputStream = new FileInputStream(zipFile);
//    	                 OutputStream outputStream = response.getOutputStream()) {    	            	
//    	            	outputStream.write(Files.readAllBytes(path));
//    	                outputStream.flush(); // Asegura que todos los datos se envíen
//    	            }
//    	        } else {
//    	            response.getWriter().write("El archivo ZIP no existe.");
//    	            }
//    	        }   	

//        // es importante validar que el archivo origen exista antes de modificar
//        // el response
        final File source = new File(sourceFile);
        if (!source.exists()) {
            throw new Exception("el archivo no se encuentra en la ubicaciÃ³n especificada.");
        }

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment;filename=\"" + targetFileName + "\"");
        FileInputStream in = null;
        try {
        	final ServletOutputStream anOs = response.getOutputStream();
            in = new FileInputStream(source);

            final byte[] buff = new byte[BUFFER_SIZE];
            int count;
 
            while ((count = in.read(buff)) != -1) {
                anOs.write(buff, 0, count);
            }
            in.close();
        } catch (FileNotFoundException ef) {
            log.severe(ef.getMessage());
            throw ef;
        } catch (IOException eio) {
            log.severe(eio.getMessage());
            throw eio;
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw e;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                log.severe(e.getMessage());
                throw e;
            }
        }
    }

}
