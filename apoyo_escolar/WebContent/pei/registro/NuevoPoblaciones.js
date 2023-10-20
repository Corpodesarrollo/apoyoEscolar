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
			document.frmNuevo.target='framePoblaciones';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.pobLineaPolitica.value)==''){ forma.pobLineaPolitica.value=' ';	}
		if(trim(forma.pobEstudiantes.value)==''){ forma.pobEstudiantes.value=' ';}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.pobLineaPolitica,"- Linea de Politica Implementada",1,100);
		validarCampo(forma.pobEstudiantes,"- Cantidad de Estudiantes",1,4);
		if(forma.pobEstudiantes.value < 1){
			appendErrorMessage("- Cantidad de Estudiantes debe ser mayor a 0");
			return false;
		}
	}
	