	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.filLocalidad.disabled=false;
			document.frmFiltro.cmd.value=document.frmFiltro.NUEVO.value;
			document.frmFiltro.submit();
		}
	}

	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filLocalidad, "- Localidad", 1)
		validarLista(forma.filInstitucion, "- Instituciñn", 1)
	}

	function ajaxInstitucion(){
		borrar_combo(document.frmFiltro.filInstitucion); 
		document.frmAjax.ajax[0].value=document.frmFiltro.filLocalidad.value;
		document.frmAjax.cmd.value=document.frmAjax.AJAX.value;
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}
	