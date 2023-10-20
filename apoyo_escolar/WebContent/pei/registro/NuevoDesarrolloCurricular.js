	function reSize()
	{
		try{
			//frame SENA
			var oFrame	=	document.getElementById("frameProgramaSENA");
			alturaPagina=oFrame.contentWindow.document.body.scrollHeight;
			oFrame.style.height=alturaPagina;
			//frame Universidad
			var oFrame	=	document.getElementById("frameProgramaUniversidad");
			alturaPagina=oFrame.contentWindow.document.body.scrollHeight;
			oFrame.style.height=alturaPagina;
			//frame OtroProyecto
			var oFrame	=	document.getElementById("frameOtroProyecto");
			alturaPagina=oFrame.contentWindow.document.body.scrollHeight;
			oFrame.style.height=alturaPagina;
			//frame Capacitacion
			var oFrame	=	document.getElementById("frameCapacitacion");
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
	
	function valorMaximoPorcentaje(eve){
		var is_ie8 = navigator.userAgent.toLowerCase().indexOf('msie 8.0') > -1;
		
		var caja = eve.currentTarget;
		
		if(caja.value > 100 || caja.value < 0){
			caja.value='';
			alert("Debe ingresar un valor entre 0 y 100");
		}
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
		//los 5 campos de texto
		
		if(trim(forma.desAlcance1.value)==''){forma.desAlcance1.value=' ';}
		if(trim(forma.desAlcance2.value)==''){forma.desAlcance2.value=' ';}
		if(trim(forma.desAlcance3.value)==''){forma.desAlcance3.value=' ';}
		if(trim(forma.desAlcance4.value)==''){forma.desAlcance4.value=' ';}
		if(trim(forma.desAlcance5.value)==''){forma.desAlcance5.value=' ';}

		if(trim(forma.desDificultad1.value)==''){forma.desDificultad1.value=' ';}
		if(trim(forma.desDificultad2.value)==''){forma.desDificultad2.value=' ';}
		if(trim(forma.desDificultad3.value)==''){forma.desDificultad3.value=' ';}
		if(trim(forma.desDificultad4.value)==''){forma.desDificultad4.value=' ';}
		if(trim(forma.desDificultad5.value)==''){forma.desDificultad5.value=' ';}
		
		
		//CriterioPreparacion
		if(forma.desCriterioPreparacion){
			if(forma.desCriterioPreparacion.length){
				for(var i=0;i<forma.desCriterioPreparacion.length;i++){
					if(trim(forma.desCriterioPreparacion_[i].value)==''){
						forma.desCriterioPreparacion[i].value=forma.desCriterioPreparacion[i].value+'0';
					}else{
						forma.desCriterioPreparacion[i].value=forma.desCriterioPreparacion[i].value+forma.desCriterioPreparacion_[i].value;
					}
				}
			}
		}
		//CriterioFormulacion
		if(forma.desCriterioFormulacion){
			if(forma.desCriterioFormulacion.length){
				for(var i=0;i<forma.desCriterioFormulacion.length;i++){
					if(trim(forma.desCriterioFormulacion_[i].value)==''){
						forma.desCriterioFormulacion[i].value=forma.desCriterioFormulacion[i].value+'0';
					}else{
						forma.desCriterioFormulacion[i].value=forma.desCriterioFormulacion[i].value+forma.desCriterioFormulacion_[i].value;
					}
				}
			}
		}
		//CriterioEjecucion
		if(forma.desCriterioEjecucion){
			if(forma.desCriterioEjecucion.length){
				for(var i=0;i<forma.desCriterioEjecucion.length;i++){
					if(trim(forma.desCriterioEjecucion_[i].value)==''){
						forma.desCriterioEjecucion[i].value=forma.desCriterioEjecucion[i].value+'0';
					}else{
						forma.desCriterioEjecucion[i].value=forma.desCriterioEjecucion[i].value+forma.desCriterioEjecucion_[i].value;
					}
				}
			}
		}
		//CriterioSeguimiento
		if(forma.desCriterioSeguimiento){
			if(forma.desCriterioSeguimiento.length){
				for(var i=0;i<forma.desCriterioSeguimiento.length;i++){
					if(trim(forma.desCriterioSeguimiento_[i].value)==''){
						forma.desCriterioSeguimiento[i].value=forma.desCriterioSeguimiento[i].value+'0';
					}else{
						forma.desCriterioSeguimiento[i].value=forma.desCriterioSeguimiento[i].value+forma.desCriterioSeguimiento_[i].value;
					}
				}
			}
		}
		//checks que van despues de saber si es articulado o no
		/*if(forma.desMedia_.checked==true){
			forma.desMedia.value=1;
		}else{
			forma.desMedia.value=0;
		}*/
		if(forma.desArticUniversidad_.checked==true){
			forma.desArticUniversidad.value=1;
		}else{
			forma.desArticUniversidad.value=0;
		}
		if(forma.desArticSena_.checked==true){forma.desArticSena.value=1;
		}else{forma.desArticSena.value=0;}
		/*if(forma.desIntegradoSena_.checked==true){forma.desIntegradoSena.value=1;
		}else{forma.desIntegradoSena.value=0;}*/		
		//proyecto
		if(forma.desProyecto){
			if(forma.desProyecto.length){
				for(var i=0;i<forma.desProyecto.length;i++){
					if(trim(forma.desProyecto_[i].value)==''){
						forma.desProyecto_[i].value=' ';
					}
					forma.desProyecto[i].value=forma.desProyecto[i].value+getChecked(document.frmNuevo.elements['desProyecto'+i])+'|'+forma.desProyecto_[i].value+'|'+forma.desProyecto_Alcances[i].value;
				}
			}
		}
		
	}
	
	function hacerValidaciones_frmNuevo(forma){
		//CriterioPreparacion
		if(forma.desCriterioPreparacion){
			if(forma.desCriterioPreparacion.length){
				for(var i=0;i<forma.desCriterioPreparacion.length;i++){
					validarEnteroOpcional(forma.desCriterioPreparacion_[i],'- Criterio de alcance Preparaciñn (Mñximo '+forma.desCriterioPreparacion2[i].value+')',0,forma.desCriterioPreparacion2[i].value)
				}
			}
		}
		//CriterioFormulacion
		if(forma.desCriterioFormulacion){
			if(forma.desCriterioFormulacion.length){
				for(var i=0;i<forma.desCriterioFormulacion.length;i++){
					validarEnteroOpcional(forma.desCriterioFormulacion_[i],'- Criterio de alcance Formulaciñn (Mñximo '+forma.desCriterioFormulacion2[i].value+')',0,forma.desCriterioFormulacion2[i].value)
				}
			}
		}
		//CriterioFormulacion
		if(forma.desCriterioEjecucion){
			if(forma.desCriterioEjecucion.length){
				for(var i=0;i<forma.desCriterioEjecucion.length;i++){
					validarEnteroOpcional(forma.desCriterioEjecucion_[i],'- Criterio de alcance Ejecuciñn (Mñximo '+forma.desCriterioEjecucion2[i].value+')',0,forma.desCriterioEjecucion2[i].value)
				}
			}
		}
		if(forma.desCriterioSeguimiento){
			if(forma.desCriterioSeguimiento.length){
				for(var i=0;i<forma.desCriterioSeguimiento.length;i++){
					validarEnteroOpcional(forma.desCriterioSeguimiento_[i],'- Criterio de alcance Seguimiento (Mñximo '+forma.desCriterioSeguimiento2[i].value+')',0,forma.desCriterioSeguimiento2[i].value)
				}
			}
		}
		
	}
	
	function isTrue(campo){
		if(campo){
			if(campo.length){
				for(var i=0;i<campo.length;i++){
					if(campo[i].checked==true){
						if(parseInt(campo[i].value)==1){
							return true;
						}else{
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	function cambioArticulado(obj){
		if(isTrue(obj)==true){
			if(document.frmNuevo.desMedia_){
				document.frmNuevo.desMedia_.disabled=false;
				document.frmNuevo.desArticUniversidad_.disabled=false;
				document.frmNuevo.desArticSena_.disabled=false;
				document.frmNuevo.desIntegradoSena_.disabled=false;
			}
		}else{
			if(document.frmNuevo.desMedia_){
				document.frmNuevo.desMedia_.disabled=true;
				document.frmNuevo.desArticUniversidad_.disabled=true;
				document.frmNuevo.desArticSena_.disabled=true;
				document.frmNuevo.desIntegradoSena_.disabled=true;
				document.frmNuevo.desMedia_.checked=false;
				document.frmNuevo.desArticUniversidad_.checked=false;
				document.frmNuevo.desArticSena_.checked=false;
				document.frmNuevo.desIntegradoSena_.checked=false;
			}
		}
	}

	function cambioProyecto(obj,n){
		if(isSelected(obj)==true){
			if(document.frmNuevo.desProyecto_){
				document.frmNuevo.desProyecto_[n].disabled=false;
				document.frmNuevo.desProyecto_Alcances[n].disabled=false;
			}
		}else{
			if(document.frmNuevo.desProyecto_){
				document.frmNuevo.desProyecto_[n].disabled=true;
				document.frmNuevo.desProyecto_Alcances[n].disabled=true;
			}
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
	
	function getChecked(campo){
		if(campo){
			if(campo.length){
				for(var i=0;i<campo.length;i++){
					if(campo[i].checked==true){
						return campo[i].value;
					}
				}
			}else{
				if(campo.checked==true){
					return campo.value;
				}
			}
		}
		return 0;
	}

	function setProyecto(forma){
		if(forma.desProyecto){
			if(forma.desProyecto.length){
				for(var i=0;i<forma.desProyecto.length;i++){
					if(document.frmNuevo.elements['desProyecto'+i]){
						cambioProyecto(document.frmNuevo.elements['desProyecto'+i],i);
					}
				}
			}
		}
	}

	function setArticulado(forma){
		cambioArticulado(document.frmNuevo.desArticulado);
	}	