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
			document.frmNuevo.target='frameOtroProyecto';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.proyNombre.value)==''){ forma.proyNombre.value=' ';	}
		if(trim(forma.proyEntidad.value)==''){ forma.proyEntidad.value=' ';	}
		if(trim(forma.proyAlcance.value)==''){ forma.proyAlcance.value=' ';	}
		if(trim(forma.proyDificultad.value)==''){ forma.proyDificultad.value=' ';}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.proyNombre,"- Nombre",1,120)
		validarCampo(forma.proyEntidad,"- Entidad",1,120)
		validarCampo(forma.proyAlcance,"- Alcance",1,600)
		validarCampo(forma.proyDificultad,"- Dificultad",1,600)
	}
	