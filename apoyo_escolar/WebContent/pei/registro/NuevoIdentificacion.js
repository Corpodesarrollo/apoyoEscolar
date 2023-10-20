function reSize()
	{
		try{
			//frame SENA
			var oFrame	=	document.getElementById("frameAjuste");
			alturaPagina=oFrame.contentWindow.document.body.scrollHeight;
			oFrame.style.height=alturaPagina;
		}
		catch(e)
		{
		window.status =	'Error: ' + e.number + '; ' + e.description;
		}
	}	

function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function lanzar(tipo){
	  	document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.target="";
		document.frmNuevo.action='SaveIdentificacion.jsp';
		document.frmNuevo.submit();
	}
	
	function completar(obj){
		if(obj.checked==true){
			if(confirm('Si selecciona esta opciñn, no podrñ alterar la informaciñn posteriormente. ñDesea seleccionarla?')){
				document.frmNuevo.idenEstado.value=document.frmNuevo.ESTADO_PEI_COMPLETO.value;
			}else{
				document.frmNuevo.idenEstado.value=document.frmNuevo.ESTADO_PEI_EN_CONSTRUCCION.value;
				obj.checked=false;
			}
		}else{
			document.frmNuevo.idenEstado.value=document.frmNuevo.ESTADO_PEI_EN_CONSTRUCCION.value;			
		}			
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione uno-","-99");
	}
		
	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarCampos(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.action='SaveIdentificacion.jsp';
			document.frmNuevo.submit();
		}
	}

	function setCheckEnfasis(item){
		
		if(item){
			document.frmNuevo.idenEnfasisOtro.disabled=false;
		}else{
			document.frmNuevo.idenEnfasisOtro.disabled=true;
			document.frmNuevo.idenEnfasisOtro.value='';
		}
			
	}
	
	function setCheckEnfoque(item){
		
		if(item){
			document.frmNuevo.idenEnfoqueOtro.disabled=false;
		}else{
			document.frmNuevo.idenEnfoqueOtro.disabled=true;
			document.frmNuevo.idenEnfoqueOtro.value='';
		}
			
	}
	
	function setEnfasis(forma){
		var band=false;
		for(var i=0;i<forma.idenEnfasis.length;i++){
			if(forma.idenEnfasis[i].checked==true){
				band=true;
				if(forma.idenEnfasis_[i].value=='true'){
					forma.idenEnfasisOtro.disabled=false;
				}else{
					forma.idenEnfasisOtro.value="";
					forma.idenEnfasisOtro.disabled=true;
				}
			}	
		}
		if(band==false){
			forma.idenEnfasisOtro.value="";
			forma.idenEnfasisOtro.disabled=true;
		}
	}
	
	function setEnfoque(forma){
		var band=false;
		for(var i=0;i<forma.idenEnfoque.length;i++){
			if(forma.idenEnfoque[i].checked==true){
				band=true;
				if(forma.idenEnfoque_[i].value=='true'){
					forma.idenEnfoqueOtro.disabled=false;
				}else{
					forma.idenEnfoqueOtro.value="";
					forma.idenEnfoqueOtro.disabled=true;
				}
			}	
		}
		if(band==false){
			forma.idenEnfoqueOtro.value="";
			forma.idenEnfoqueOtro.disabled=true;
		}
	}
	
	function validarCampos(forma){
		//campo Otro Enfasis
		forma.idenEnfasisOtro.disabled=false;
		if(trim(forma.idenEnfasisOtro.value)==''){
			forma.idenEnfasisOtro.value=' ';
		}
		//campo otro Enfoque
		forma.idenEnfoqueOtro.disabled=false;
		if(trim(forma.idenEnfoqueOtro.value)==''){
			forma.idenEnfoqueOtro.value=' ';
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.idenRector, "- Nombre del rector", 0, 250);
		validarCampo(forma.idenTelefono, "- Tel?fono", 0, 250);
		validarCampo(forma.idenCorreo, "- Correo", 0, 250);
		validarCampo(forma.idenEstudiantes, "- Estudiantes matriculados", 0, 20);
		validarEntero(forma.idenJornadas, "- N?mero de jornadas", 1, 999);
		
		validarCampo(forma.idenNombre, "- Nombre del PEI", 1, 120);
		
		//validacion de enfasis
		var band=false;
		if(forma.idenEnfasis.length){
			for (var n=0;n<forma.idenEnfasis.length;n++){
				if(forma.idenEnfasis[n].checked){
					if(forma.idenEnfasis_[n].value=='true'){
						band=true;
						break;		
					}
				}
			}
		}else{
			if(forma.idenEnfasis.checked){
				if(forma.idenEnfasis_.value=='true'){
					band=true;
				}
			}
		}
		if(band==true){	
			validarCampo(forma.idenEnfasisOtro, "- Otro ?nfasis", 1, 100)
		}
		//fin de validacion de enfasis

		//validarSeleccion(forma.idenEnfoque, "- Enfoque")
		//validacion de enfasis
		band=false;
		if(forma.idenEnfoque.length){
			for (var n=0;n<forma.idenEnfoque.length;n++){
				if(forma.idenEnfoque[n].checked){
					if(forma.idenEnfoque_[n].value=='true'){
						band=true;
						break;		
					}
				}
			}
		}else{
			if(forma.idenEnfoque.checked){
				if(forma.idenEnfoque_.value=='true'){
					band=true;
				}
			}
		}
		if(band==true){	
			validarCampo(forma.idenEnfoqueOtro, "- Otro enfoque", 1, 100)
		}
		//fin de validacion de enfasis
	}
	
	function setCeros(){
		var forma=document.frmNuevo;
	}
	
	function reporte(){
 
		document.frmNuevo.cmd.value = document.frmNuevo.GENERAR.value;
		//document.frmNuevo.action = document.frmNuevo.action2.value;      
		//document.frmNuevo.target = "_black";
		document.frmNuevo.submit();
	}
