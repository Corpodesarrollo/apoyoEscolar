package participacion.reportes.vo;

import siges.common.vo.Vo;

/**
 * Value Object del manejo del formulario de ingreso/edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ItemsCaracterizacionVO  extends Vo{

	private int itemNivel;
	private String nombreItemNivel;
	private int itemInstancia;
	private String nombreItemInstancia;
	private int itemRango;
	private String nombreItemRango;
	private long itemDaneColegio;
	private long itemColegio;
	private String nombreItemColegio;
	private int itemSede;
	private String nombreItemSede;
	private int itemJornada;
	private String nombreItemJornada;
	private int itemTipoDocumento;
	private String nombreItemTipoDocumento;
	private String itemNumeroDocumento;
	private String itemApellido1;
	private String itemApellido2;
	private String itemNombre1;
	private String itemNombre2;
	private int itemRol;
	private String itemNombreRol;
	private String itemTelefono;
	private String itemCelular;
	private String itemCorreoElectronico;	
	private int itemEdad;
	private String itemGenero;
	private int itemEtnia;
	private String nombreItemEtnia;
	private int itemLocalidadEstudio;
	private String nombreItemLocalidadEstudio;
	private int itemLocalidadResidencia;
	private String nombreItemLocalidadResidencia;
	private String itemEntidad;
	private int itemSectorEconomico;
	private String nombreItemSectorEconomico;
	private int itemDiscapacidad;
	private String nombreItemDiscapacidad;
	private int itemLgtb;
	private String nombreItemLgtb;
	private int itemOcupacion;
	private String nombreItemOcupacion;
	private int itemDesplazado;
	private String nombreItemDesplazado;
	private int itemAmenazado;
	private String nombreItemAmenazado;
	private int itemParcodigo;
	private int itemLocalidad=-1;
	private int itemTieneLocalidad;
	private int itemTieneColegio;
	
	/**
	 * @return Return the itemParcodigo.
	 */
	public int getItemParcodigo() {
		return itemParcodigo;
	}
	/**
	 * @param itemParcodigo The itemParcodigo to set.
	 */
	public void setItemParcodigo(int itemParcodigo) {
		this.itemParcodigo = itemParcodigo;
	}
	
	/**
	 * @return Return the itemNivel.
	 */
	public int getItemNivel() {
		return itemNivel;
	}
	/**
	 * @param itemNivel The itemNivel to set.
	 */
	public void setItemNivel(int itemNivel) {
		this.itemNivel = itemNivel;
	}
	/**
	 * @return Return the nombreItemNivel.
	 */
	public String getNombreItemNivel() {
		return nombreItemNivel;
	}
	/**
	 * @param nombreItemNivel The nombreItemNivel to set.
	 */
	public void setNombreItemNivel(String nombreItemNivel) {
		this.nombreItemNivel = nombreItemNivel;
	}
	/**
	 * @return Return the itemInstancia.
	 */
	public int getItemInstancia() {
		return itemInstancia;
	}
	/**
	 * @param itemInstancia The itemInstancia to set.
	 */
	public void setItemInstancia(int itemInstancia) {
		this.itemInstancia = itemInstancia;
	}
	/**
	 * @return Return the nombreItemInstancia.
	 */
	public String getNombreItemInstancia() {
		return nombreItemInstancia;
	}
	/**
	 * @param nombreItemInstancia The nombreItemInstancia to set.
	 */
	public void setNombreItemInstancia(String nombreItemInstancia) {
		this.nombreItemInstancia = nombreItemInstancia;
	}
	/**
	 * @return Return the itemRango.
	 */
	public int getItemRango() {
		return itemRango;
	}
	/**
	 * @param itemRango The itemRango to set.
	 */
	public void setItemRango(int itemRango) {
		this.itemRango = itemRango;
	}
	/**
	 * @return Return the nombreItemRango.
	 */
	public String getNombreItemRango() {
		return nombreItemRango;
	}
	/**
	 * @param nombreItemRango The nombreItemRango to set.
	 */
	public void setNombreItemRango(String nombreItemRango) {
		this.nombreItemRango = nombreItemRango;
	}
	/**
	 * @return Return the itemDaneColegio.
	 */
	public long getItemDaneColegio() {
		return itemDaneColegio;
	}
	/**
	 * @param itemDaneColegio The itemDaneColegio to set.
	 */
	public void setItemDaneColegio(long itemDaneColegio) {
		this.itemDaneColegio = itemDaneColegio;
	}
	/**
	 * @return Return the itemColegio.
	 */
	public long getItemColegio() {
		return itemColegio;
	}
	/**
	 * @param itemColegio The itemColegio to set.
	 */
	public void setItemColegio(long itemColegio) {
		this.itemColegio = itemColegio;
	}
	/**
	 * @return Return the nombreItemColegio.
	 */
	public String getNombreItemColegio() {
		return nombreItemColegio;
	}
	/**
	 * @param nombreItemColegio The nombreItemColegio to set.
	 */
	public void setNombreItemColegio(String nombreItemColegio) {
		this.nombreItemColegio = nombreItemColegio;
	}
	/**
	 * @return Return the itemSede.
	 */
	public int getItemSede() {
		return itemSede;
	}
	/**
	 * @param itemSede The itemSede to set.
	 */
	public void setItemSede(int itemSede) {
		this.itemSede = itemSede;
	}
	/**
	 * @return Return the nombreItemSede.
	 */
	public String getNombreItemSede() {
		return nombreItemSede;
	}
	/**
	 * @param nombreItemSede The nombreItemSede to set.
	 */
	public void setNombreItemSede(String nombreItemSede) {
		this.nombreItemSede = nombreItemSede;
	}
	/**
	 * @return Return the itemJornada.
	 */
	public int getItemJornada() {
		return itemJornada;
	}
	/**
	 * @param itemJornada The itemJornada to set.
	 */
	public void setItemJornada(int itemJornada) {
		this.itemJornada = itemJornada;
	}
	/**
	 * @return Return the nombreItemJornada.
	 */
	public String getNombreItemJornada() {
		return nombreItemJornada;
	}
	/**
	 * @param nombreItemJornada The nombreItemJornada to set.
	 */
	public void setNombreItemJornada(String nombreItemJornada) {
		this.nombreItemJornada = nombreItemJornada;
	}
	/**
	 * @return Return the itemTipoDocumento.
	 */
	public int getItemTipoDocumento() {
		return itemTipoDocumento;
	}
	/**
	 * @param itemTipoDocumento The itemTipoDocumento to set.
	 */
	public void setItemTipoDocumento(int itemTipoDocumento) {
		this.itemTipoDocumento = itemTipoDocumento;
	}
	/**
	 * @return Return the nombreItemTipoDocumento.
	 */
	public String getNombreItemTipoDocumento() {
		return nombreItemTipoDocumento;
	}
	/**
	 * @param nombreItemTipoDocumento The nombreItemTipoDocumento to set.
	 */
	public void setNombreItemTipoDocumento(String nombreItemTipoDocumento) {
		this.nombreItemTipoDocumento = nombreItemTipoDocumento;
	}
	/**
	 * @return Return the itemNumeroDocumento.
	 */
	public String getItemNumeroDocumento() {
		return itemNumeroDocumento;
	}
	/**
	 * @param itemNumeroDocumento The itemNumeroDocumento to set.
	 */
	public void setItemNumeroDocumento(String itemNumeroDocumento) {
		this.itemNumeroDocumento = itemNumeroDocumento;
	}
	/**
	 * @return Return the itemApellido1.
	 */
	public String getItemApellido1() {
		return itemApellido1;
	}
	/**
	 * @param itemApellido1 The itemApellido1 to set.
	 */
	public void setItemApellido1(String itemApellido1) {
		this.itemApellido1 = itemApellido1;
	}
	/**
	 * @return Return the itemApellido2.
	 */
	public String getItemApellido2() {
		return itemApellido2;
	}
	/**
	 * @param itemApellido2 The itemApellido2 to set.
	 */
	public void setItemApellido2(String itemApellido2) {
		this.itemApellido2 = itemApellido2;
	}
	/**
	 * @return Return the itemNombre1.
	 */
	public String getItemNombre1() {
		return itemNombre1;
	}
	/**
	 * @param itemNombre1 The itemNombre1 to set.
	 */
	public void setItemNombre1(String itemNombre1) {
		this.itemNombre1 = itemNombre1;
	}
	/**
	 * @return Return the itemNombre2.
	 */
	public String getItemNombre2() {
		return itemNombre2;
	}
	/**
	 * @param itemNombre2 The itemNombre2 to set.
	 */
	public void setItemNombre2(String itemNombre2) {
		this.itemNombre2 = itemNombre2;
	}
	/**
	 * @return Return the itemRol.
	 */
	public int getItemRol() {
		return itemRol;
	}
	/**
	 * @param itemRol The itemRol to set.
	 */
	public void setItemRol(int itemRol) {
		this.itemRol = itemRol;
	}
	/**
	 * @return Return the itemNombreRol.
	 */
	public String getItemNombreRol() {
		return itemNombreRol;
	}
	/**
	 * @param itemNombreRol The itemNombreRol to set.
	 */
	public void setItemNombreRol(String itemNombreRol) {
		this.itemNombreRol = itemNombreRol;
	}
	/**
	 * @return Return the itemTelefono.
	 */
	public String getItemTelefono() {
		return itemTelefono;
	}
	/**
	 * @param itemTelefono The itemTelefono to set.
	 */
	public void setItemTelefono(String itemTelefono) {
		this.itemTelefono = itemTelefono;
	}
	/**
	 * @return Return the itemCelular.
	 */
	public String getItemCelular() {
		return itemCelular;
	}
	/**
	 * @param itemCelular The itemCelular to set.
	 */
	public void setItemCelular(String itemCelular) {
		this.itemCelular = itemCelular;
	}
	/**
	 * @return Return the itemCorreoElectronico.
	 */
	public String getItemCorreoElectronico() {
		return itemCorreoElectronico;
	}
	/**
	 * @param itemCorreoElectronico The itemCorreoElectronico to set.
	 */
	public void setItemCorreoElectronico(String itemCorreoElectronico) {
		this.itemCorreoElectronico = itemCorreoElectronico;
	}
	/**
	 * @return Return the itemEdad.
	 */
	public int getItemEdad() {
		return itemEdad;
	}
	/**
	 * @param itemEdad The itemEdad to set.
	 */
	public void setItemEdad(int itemEdad) {
		this.itemEdad = itemEdad;
	}
	/**
	 * @return Return the itemGenero.
	 */
	public String getItemGenero() {
		return itemGenero;
	}
	/**
	 * @param itemGenero The itemGenero to set.
	 */
	public void setItemGenero(String itemGenero) {
		this.itemGenero = itemGenero;
	}
	/**
	 * @return Return the itemEtnia.
	 */
	public int getItemEtnia() {
		return itemEtnia;
	}
	/**
	 * @param itemEtnia The itemEtnia to set.
	 */
	public void setItemEtnia(int itemEtnia) {
		this.itemEtnia = itemEtnia;
	}
	/**
	 * @return Return the nombreItemEtnia.
	 */
	public String getNombreItemEtnia() {
		return nombreItemEtnia;
	}
	/**
	 * @param nombreItemEtnia The nombreItemEtnia to set.
	 */
	public void setNombreItemEtnia(String nombreItemEtnia) {
		this.nombreItemEtnia = nombreItemEtnia;
	}
	/**
	 * @return Return the itemLocalidadEstudio.
	 */
	public int getItemLocalidadEstudio() {
		return itemLocalidadEstudio;
	}
	/**
	 * @param itemLocalidadEstudio The itemLocalidadEstudio to set.
	 */
	public void setItemLocalidadEstudio(int itemLocalidadEstudio) {
		this.itemLocalidadEstudio = itemLocalidadEstudio;
	}
	/**
	 * @return Return the nombreItemLocalidadEstudio.
	 */
	public String getNombreItemLocalidadEstudio() {
		return nombreItemLocalidadEstudio;
	}
	/**
	 * @param nombreItemLocalidadEstudio The nombreItemLocalidadEstudio to set.
	 */
	public void setNombreItemLocalidadEstudio(String nombreItemLocalidadEstudio) {
		this.nombreItemLocalidadEstudio = nombreItemLocalidadEstudio;
	}
	/**
	 * @return Return the itemLocalidadResidencia.
	 */
	public int getItemLocalidadResidencia() {
		return itemLocalidadResidencia;
	}
	/**
	 * @param itemLocalidadResidencia The itemLocalidadResidencia to set.
	 */
	public void setItemLocalidadResidencia(int itemLocalidadResidencia) {
		this.itemLocalidadResidencia = itemLocalidadResidencia;
	}
	/**
	 * @return Return the nombreItemLocalidadResidencia.
	 */
	public String getNombreItemLocalidadResidencia() {
		return nombreItemLocalidadResidencia;
	}
	/**
	 * @param nombreItemLocalidadResidencia The nombreItemLocalidadResidencia to set.
	 */
	public void setNombreItemLocalidadResidencia(
			String nombreItemLocalidadResidencia) {
		this.nombreItemLocalidadResidencia = nombreItemLocalidadResidencia;
	}
	/**
	 * @return Return the itemEntidad.
	 */
	public String getItemEntidad() {
		return itemEntidad;
	}
	/**
	 * @param itemEntidad The itemEntidad to set.
	 */
	public void setItemEntidad(String itemEntidad) {
		this.itemEntidad = itemEntidad;
	}
	/**
	 * @return Return the itemSectorEconomico.
	 */
	public int getItemSectorEconomico() {
		return itemSectorEconomico;
	}
	/**
	 * @param itemSectorEconomico The itemSectorEconomico to set.
	 */
	public void setItemSectorEconomico(int itemSectorEconomico) {
		this.itemSectorEconomico = itemSectorEconomico;
	}
	/**
	 * @return Return the nombreItemSectorEconomico.
	 */
	public String getNombreItemSectorEconomico() {
		return nombreItemSectorEconomico;
	}
	/**
	 * @param nombreItemSectorEconomico The nombreItemSectorEconomico to set.
	 */
	public void setNombreItemSectorEconomico(String nombreItemSectorEconomico) {
		this.nombreItemSectorEconomico = nombreItemSectorEconomico;
	}
	/**
	 * @return Return the itemDiscapacidad.
	 */
	public int getItemDiscapacidad() {
		return itemDiscapacidad;
	}
	/**
	 * @param itemDiscapacidad The itemDiscapacidad to set.
	 */
	public void setItemDiscapacidad(int itemDiscapacidad) {
		this.itemDiscapacidad = itemDiscapacidad;
	}
	/**
	 * @return Return the nombreItemDiscapacidad.
	 */
	public String getNombreItemDiscapacidad() {
		return nombreItemDiscapacidad;
	}
	/**
	 * @param nombreItemDiscapacidad The nombreItemDiscapacidad to set.
	 */
	public void setNombreItemDiscapacidad(String nombreItemDiscapacidad) {
		this.nombreItemDiscapacidad = nombreItemDiscapacidad;
	}
	/**
	 * @return Return the itemLgtb.
	 */
	public int getItemLgtb() {
		return itemLgtb;
	}
	/**
	 * @param itemLgtb The itemLgtb to set.
	 */
	public void setItemLgtb(int itemLgtb) {
		this.itemLgtb = itemLgtb;
	}
	/**
	 * @return Return the nombreItemLgtb.
	 */
	public String getNombreItemLgtb() {
		return nombreItemLgtb;
	}
	/**
	 * @param nombreItemLgtb The nombreItemLgtb to set.
	 */
	public void setNombreItemLgtb(String nombreItemLgtb) {
		this.nombreItemLgtb = nombreItemLgtb;
	}
	/**
	 * @return Return the itemOcupacion.
	 */
	public int getItemOcupacion() {
		return itemOcupacion;
	}
	/**
	 * @param itemOcupacion The itemOcupacion to set.
	 */
	public void setItemOcupacion(int itemOcupacion) {
		this.itemOcupacion = itemOcupacion;
	}
	/**
	 * @return Return the nombreItemOcupacion.
	 */
	public String getNombreItemOcupacion() {
		return nombreItemOcupacion;
	}
	/**
	 * @param nombreItemOcupacion The nombreItemOcupacion to set.
	 */
	public void setNombreItemOcupacion(String nombreItemOcupacion) {
		this.nombreItemOcupacion = nombreItemOcupacion;
	}
	/**
	 * @return Return the itemDesplazado.
	 */
	public int getItemDesplazado() {
		return itemDesplazado;
	}
	/**
	 * @param itemDesplazado The itemDesplazado to set.
	 */
	public void setItemDesplazado(int itemDesplazado) {
		this.itemDesplazado = itemDesplazado;
	}
	/**
	 * @return Return the nombreItemDesplazado.
	 */
	public String getNombreItemDesplazado() {
		return nombreItemDesplazado;
	}
	/**
	 * @param nombreItemDesplazado The nombreItemDesplazado to set.
	 */
	public void setNombreItemDesplazado(String nombreItemDesplazado) {
		this.nombreItemDesplazado = nombreItemDesplazado;
	}
	/**
	 * @return Return the itemAmenazado.
	 */
	public int getItemAmenazado() {
		return itemAmenazado;
	}
	/**
	 * @param itemAmenazado The itemAmenazado to set.
	 */
	public void setItemAmenazado(int itemAmenazado) {
		this.itemAmenazado = itemAmenazado;
	}
	/**
	 * @return Return the nombreItemAmenazado.
	 */
	public String getNombreItemAmenazado() {
		return nombreItemAmenazado;
	}
	/**
	 * @param nombreItemAmenazado The nombreItemAmenazado to set.
	 */
	public void setNombreItemAmenazado(String nombreItemAmenazado) {
		this.nombreItemAmenazado = nombreItemAmenazado;
	}
	/**
	 * @return Return the itemLocalidad.
	 */
	public int getItemLocalidad() {
		return itemLocalidad;
	}
	/**
	 * @param itemLocalidad The itemLocalidad to set.
	 */
	public void setItemLocalidad(int itemLocalidad) {
		this.itemLocalidad = itemLocalidad;
	}
	/**
	 * @return Return the itemTieneLocalidad.
	 */
	public int getItemTieneLocalidad() {
		return itemTieneLocalidad;
	}
	/**
	 * @param itemTieneLocalidad The itemTieneLocalidad to set.
	 */
	public void setItemTieneLocalidad(int itemTieneLocalidad) {
		this.itemTieneLocalidad = itemTieneLocalidad;
	}
	/**
	 * @return Return the itemTieneColegio.
	 */
	public int getItemTieneColegio() {
		return itemTieneColegio;
	}
	/**
	 * @param itemTieneColegio The itemTieneColegio to set.
	 */
	public void setItemTieneColegio(int itemTieneColegio) {
		this.itemTieneColegio = itemTieneColegio;
	}
	
	
	
	
	
}
