
    function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function lanzar(tipo){
	  	document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarCampos(document.frmNuevo);
			if(document.frmNuevo.docArchivo){
				document.frmNuevo.encoding="multipart/form-data";
			}
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function validarCampos(forma){
		//campo Otro Enfasis
		forma.docDescripcion.disabled=false;
		if(trim(forma.docDescripcion.value)==''){
			forma.docDescripcion.value=' ';
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.docNombre, "- Nombre", 1, 200)
		validarCampoOpcional(forma.docDescripcion, "- Descripciñn", 1, 600)
		if(forma.docArchivo){
			validarCampo(forma.docArchivo, "- Documento", 1, 2000)
			validarExtension(forma.docArchivo, "- Documento (debe ser PDF)", extensiones)
		}
	}
	
