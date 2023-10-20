package siges.reporte.beans;

import java.io.Serializable;

/**
*	Nombre:	Filtro
*	Descripcion:	Datos de informacion basica del egresado
*	Parametro de entrada:	no aplica
*	Parametro de salida:	no aplica
*	Funciones de la pagina:	Almacenar los datos del egresado para un nuevo registro o para editar
*	Entidades afectadas:	no aplica
*	Fecha de modificacinn:	01/12/04
*	@author Pasantes UD
*	@version $v 1.2 $
*/

public class LocalidadVO{
	private long locCodigo;
	private String locNombre;
	public long getLocCodigo() {
		return locCodigo;
	}
	public void setLocCodigo(long locCodigo) {
		this.locCodigo = locCodigo;
	}
	public String getLocNombre() {
		return locNombre;
	}
	public void setLocNombre(String locNombre) {
		this.locNombre = locNombre;
	}
	
}