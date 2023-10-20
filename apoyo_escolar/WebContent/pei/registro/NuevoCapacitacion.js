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
			document.frmNuevo.target='frameCapacitacion';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.capNombreFormacion.value)==''){
			forma.capNombreFormacion.value=' ';
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.capUniversidad,"- Universidad",1)
		validarCampo(forma.capNombreFormacion,"- Nombre",1,120)
		validarEntero(forma.capDocentes,"- Nñmero de docentes",1,999999)
	}
	
	