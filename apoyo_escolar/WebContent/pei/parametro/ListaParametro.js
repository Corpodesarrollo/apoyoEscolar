	function eliminar(tipo,cod){
		if(confirm('ñConfirma la eliminaciñn del parñmetro?')){
			document.frmFiltro.filTipo.value=tipo;
			document.frmFiltro.filCodigo.value=cod;
			document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
			document.frmFiltro.submit();
		}
	}

	function editar(tipo,cod){
		document.frmFiltro.filTipo.value=tipo;
		document.frmFiltro.filCodigo.value=cod;
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		document.frmFiltro.submit();
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}

	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filTipo, "- Tipo de parñmetro", 1)
		validarLista(forma.filEstado, "- Estado", 0)
	}