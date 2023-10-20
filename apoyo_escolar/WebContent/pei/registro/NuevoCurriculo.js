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
			document.frmNuevo.target='frameCurriculo';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.curAlcance.value)==''){ forma.curAlcance.value=' ';	}
		if(trim(forma.curDificultad.value)==''){ forma.curDificultad.value=' ';}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.curAlcance,"- Alcance",1,300)
		validarCampo(forma.curAlcance,"- Dificultad",1,300)
	}
	