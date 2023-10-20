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
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.submit();
	}
	
	function validarCampos(forma){
		//campo Otro Enfasis
		forma.parDescripcion.disabled=false;
		if(trim(forma.parDescripcion.value)==''){
			forma.parDescripcion.value=' ';
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.parTipo, "- Tipo de parñmetro", 1)
		validarCampo(forma.parNombre, "- Nombre", 1, 120)
		validarEntero(forma.parOrden, "- Orden", 1, 999)
		if(isEspecial(forma.parTipo)) validarEntero(forma.parValor, "- Valor", 1, 999)
		validarLista(forma.parEstado, "- Estado", 1)
		validarCampoOpcional(forma.parAbreviatura, "- Abreviatura", 1, 10)
		validarCampoOpcional(forma.parDescripcion, "- Descripciñn", 1, 600)
	}
	
	function cambioTipo(forma){
		if(isEspecial(forma.parTipo)){
			if(document.getElementById('filaValor')){
				document.getElementById('filaValor').style.display='block';
			}
		}else{
			if(document.getElementById('filaValor')){
				document.getElementById('filaValor').style.display='none';
			}
		}
	}
	
	function isEspecial(campo){
		for (i=0;i<tipoEspecial.length;i++){
			if(tipoEspecial[i]==campo.options[campo.selectedIndex].value){
				return true;
			}
		}
		return false;
	}