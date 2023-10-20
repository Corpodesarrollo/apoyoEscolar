	function cerrarVentana(){
		parent.close();
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarCampos(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.target='frameProgramaSENA';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.proCodigo,"- Programa",1)
		validarEntero(forma.proEstudiantes,"- Nñmero de estudiantes",1,9999999999)
	}
	