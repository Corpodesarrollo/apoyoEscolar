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
			document.frmNuevo.target='frameCiudadania';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.ciuAlcance.value)==''){ forma.ciuAlcance.value=' ';	}
		if(trim(forma.ciuDificultad.value)==''){ forma.ciuDificultad.value=' ';}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.ciuAlcance,"- Alcance",1,300)
		validarCampo(forma.ciuAlcance,"- Dificultad",1,300)
	}
	