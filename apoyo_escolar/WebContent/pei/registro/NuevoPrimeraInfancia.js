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
			document.frmNuevo.target='framePrimeraInfancia';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.priGrado.value)==''){ forma.priGrado.value=' ';	}
		if(trim(forma.priCursos.value)==''){ forma.priCursos.value=' ';}
		if(trim(forma.priEstudiantes.value)==''){ forma.priEstudiantes.value=' ';}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.priGrado,"- Grado",1,60);
		validarCampo(forma.priCursos,"- Cantidad de Cursos",1,3);
		validarCampo(forma.priEstudiantes,"- Cantidad de Estudiantes",1,4);
		if(forma.priEstudiantes.value < 1){
			appendErrorMessage("- Cantidad de Estudiantes debe ser mayor a 0");
			return false;
		}
	}
	