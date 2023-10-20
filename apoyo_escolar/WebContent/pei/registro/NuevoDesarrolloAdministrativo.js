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

	function validarCampos(forma){
		//campo Otro
		forma.desEiDocumentoCual.disabled=false;
		if(trim(forma.desEiDocumentoCual.value)==''){
			forma.desEiDocumentoCual.value=' ';
		}
		//dificultades
		/*forma.desDificultadAdmin.disabled=false;
		if(trim(forma.desDificultadAdmin.value)==''){
			forma.desDificultadAdmin.value=' ';
		}
		forma.desDificultadManual.disabled=false;
		if(trim(forma.desDificultadManual.value)==''){
			forma.desDificultadManual.value=' ';
		}
		forma.desDificultadInventario.disabled=false;
		if(trim(forma.desDificultadInventario.value)==''){
			forma.desDificultadInventario.value=' ';
		}
		forma.desDificultadEscolar.disabled=false;
		if(trim(forma.desDificultadEscolar.value)==''){
			forma.desDificultadEscolar.value=' ';
		}
		forma.desDificultadComunicacion.disabled=false;
		if(trim(forma.desDificultadComunicacion.value)==''){
			forma.desDificultadComunicacion.value=' ';
		}*/
	}
	
	function hacerValidaciones_frmNuevo(forma){
		if(isSelected(forma.desEiDocumento_)==true){
			validarCampo(forma.desEiDocumentoCual, "- Cual otro Documento", 1, 100)
		}
		if(isSelected(forma.desEtapaAdmin)==true){
			validarCampo(forma.desDificultadAdmin, "- Dificultad en Procedimientos Administrativos", 1, 600)
		}
		if(isSelected(forma.desEtapaManual)==true){
			validarCampo(forma.desDificultadManual, "- Dificultad en Manual de funciones", 1, 600)
		}
		if(isSelected(forma.desEtapaInventario)==true){
			validarCampo(forma.desDificultadInventario, "- Dificultad en Procedimiento de manejo de inventarios", 1, 600)
		}
		if(isSelected(forma.desEtapaEscolar)==true){
			validarCampo(forma.desDificultadEscolar, "- Dificultad en Sistema de Informaciñn Escolar", 1, 600)
		}
		if(isSelected(forma.desEtapaComunicacion)==true){
			validarCampo(forma.desDificultadComunicacion, "- Dificultad en Estrategia de comunicaciones en la instituciñn", 1, 600)
		}
	}
	
	function valorMaximoPorcentaje(eve){
		var is_ie8 = navigator.userAgent.toLowerCase().indexOf('msie 8.0') > -1;
		
		var caja = eve.currentTarget;
		
		if(caja.value > 100 || caja.value < 0){
			caja.value='';
			alert("Debe ingresar un valor entre 0 y 100");
		}
	}
	
	/*function cambioCheck(obj,cual){
		if(isSelected(obj)==true){
			cual.disabled=false;
		}else{
			cual.disabled=true;
			cual.value='';
		}
	}*/
	
	function cambioValor(obj,cual){
		if(obj.checked==true){
			cual.value='1';
		}else{
			cual.value='0';
		}
	}
	
	function isSelected(campo){
		if(campo){
			if(campo.length){
				for(var i=0;i<campo.length;i++){
					if(campo[i].checked==true){
						return true;
					}
				}
			}else{
				if(campo.checked==true){
					return true;
				}
			}
		}
		return false;
	}

