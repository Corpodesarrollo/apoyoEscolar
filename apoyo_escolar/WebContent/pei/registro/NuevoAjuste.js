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
			document.frmNuevo.target='frameAjuste';
			document.frmNuevo.submit();
			parent.close();
		}
	}

	function validarCampos(forma){
		//campo
		if(trim(forma.ajuAjuste.value)==''){
			forma.ajuAjuste.value=' ';
		}
		if(trim(forma.ajuResolucion.value)==''){
			forma.ajuResolucion.value=' ';
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.ajuVigencia,"- Año",1);
		validarCampo(forma.ajuAjuste,"- Ajuste",1,500);
		validarCampo(forma.ajuResolucion,"- Resolucion",1,10);
		if(forma.ajuResolucion.value < 1){
			appendErrorMessage("- No. de Resolucion debe ser mayor a 0");
			return false;
		}
	}
	