package participacion.lideres.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author John
 * 
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Zip {
	private String mensaje;// mensaje en caso de error

	/**
	 * Funcinn: pone un archivo como zip en el destino indicado <BR>
	 * 
	 * @param String
	 *            destino
	 * @param int BUFFER_SIZE
	 * @param Collection
	 *            files
	 * @return boolean
	 **/
	public boolean ponerZip(String ruta, String archivo, int BUFFER_SIZE,
			Collection files) {
		String filename;
		int count;
		ZipOutputStream out = null;
		FileOutputStream dest = null;
		BufferedInputStream origin = null;
		FileInputStream fi = null;
		try {
			File f = new File(ruta);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			dest = new FileOutputStream(ruta + archivo);// Reference to our zip
														// file
			out = new ZipOutputStream(new BufferedOutputStream(dest));// Wrap
																		// our
																		// destination
																		// zipfile
																		// with
																		// a
																		// ZipOutputStream
			File f2;
			byte[] data = new byte[BUFFER_SIZE];// Create a byte[] buffer that
												// we will read data from the
												// source files into and then
												// transfer it to the zip file
			for (Iterator i = files.iterator(); i.hasNext();) {// Iterate over
																// all of the
																// files in our
																// list
				filename = (String) i.next();// Get a BufferedInputStream that
												// we can use to read the source
												// file
				fi = new FileInputStream(filename);
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				f2 = new File(filename);
				ZipEntry entry = new ZipEntry(f2.getName());// Setup the entry
															// in the zip file
				out.putNextEntry(entry);
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();// Close the source file
				fi.close();
				f2 = null;
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Haciendo ZIP: " + e);
			// e.printStackTrace();
			setMensaje("Error Haciendo ZIP: " + e);
			return false;
		} finally {
			try {
				if (out != null)
					;
				out.close();
				if (dest != null)
					;
				dest.close();
				if (origin != null)
					;
				origin.close();
				if (fi != null)
					;
				fi.close();
				File zz;
				for (Iterator i = files.iterator(); i.hasNext();) {
					zz = new File((String) i.next());
					if (zz.exists())
						FileUtils.forceDelete(zz);
				}
			} catch (Exception ex) {
				System.out.println("error finally zip" + ex);
			}
		}
		return true;
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		mensaje += s;
	}

	/**
	 * Funcinn: retorna la lista de mensajes a <BR>
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}

}
