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
			document.frmNuevo.target='frameProyecto40Horas';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.horCentro.value)==''){ forma.horCentro.value=' ';	}
		if(trim(forma.horEstudiantes.value)==''){ forma.horEstudiantes.value=' ';}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.horCentro,"- Centros de Interes Establecidos",1,100);
		validarCampo(forma.horEstudiantes,"- Cantidad de Estudiantes",1,4);
		if(forma.horEstudiantes.value < 1){
			appendErrorMessage("- Cantidad de Estudiantes debe ser mayor a 0");
			return false;
		}
	}
	