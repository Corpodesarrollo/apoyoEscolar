package siges.adminParamsAcad.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class DimensionVO  extends Vo{
	   private long  codigo; 	  
	   private String  nombre;
	   private int insertar;
	   
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getInsertar() {
		return insertar;
	}
	public void setInsertar(int insertar) {
		this.insertar = insertar;
	} 
	    
	   
	 
 }
