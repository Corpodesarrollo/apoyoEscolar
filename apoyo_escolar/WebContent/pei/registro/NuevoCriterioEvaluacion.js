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
			document.frmNuevo.target='frameCriterioEvaluacion';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.criAlcance.value)==''){ forma.criAlcance.value=' ';	}
		if(trim(forma.criDificultad.value)==''){ forma.criDificultad.value=' ';}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.criAlcance,"- Alcance",1,300)
		validarCampo(forma.criDificultad,"- Dificultad",1,300)
	}
	