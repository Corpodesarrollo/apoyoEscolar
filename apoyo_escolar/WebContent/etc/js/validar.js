/**
Estas funciones estñn probadas en browsers Netscape 4.x y Explorer 4.x.
*/

/*
<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		27/04/2018		JORGE CAMACHO		SE AGREGÓ LA FUNCIÓN validarDominioCorreo()
			2.0		04/05/2018		JORGE CAMACHO		SE AGREGÓ LA FUNCIÓN validarUsuarioComoContrasena()
			3.0		21/08/2018		JORGE CAMACHO		SE AGREGÓ LA FUNCIÓN validarReusarContrasena()  
 -->
*/

function rtrim(cadena) {
    cadena += "";
    for (var i = cadena.length -1; (i >= 0) && ((cadena.charAt(i) == ' ')); i--)
        ;
    return cadena.substring(0, i+1);
}

function ltrim(cadena) {
    cadena += "";
    for (var i = 0; (i < cadena.length) && ((cadena.charAt(i) == ' ')); i++)
        ;
    if (i == cadena.length) {
        return "";
    }
    return cadena.substring(i);
}

function trim(cadena) {
    return ltrim(rtrim(cadena));
}

/** Las siguientes funciones se utilizan para validar un nñmero de punto flotante. */
var r1Float = new RegExp("(\,\,)|([0-9]{4}\,)|(^[0]{0,}\,)|([\,]+[0-9]{0,2}[\,$])|(\\..*[\\.\,])");
var r2Float = new RegExp("(((\\[?)[0-9]\\,[0-9]{3}(\\]?))|(^[0-9]*))(\\.[0-9]+)?$")

function isFloat(number) {
	number = trim(number)
	return (!r1Float.test(number) && r2Float.test(number));
}

/*Esta funcion recibe un float y un rango y lo valida*/
function isFloatRange(number, min, max)  {
	if (!isFloat(number)) {
		return false;
	}
	var theValue = (number.split(",").join(""))*1;
	return theValue >= min && theValue <= max
}

var r1Int = new RegExp("(\,\,)|([0-9]{4}\,)|(^[0]{0,}\,)|([\,]+[0-9]{0,2}[\,$])");
var r2Int = new RegExp("((\\[?)[0-9]\\,[0-9]{3}(\\]?)$)|(^[0-9]+)$")

function isInt(number) {
	number = trim(number);
	return (!r1Int.test(number) && r2Int.test(number));
}

/*Esta funcion recibe un entero y un rango y lo valida*/
function isIntRange(number, min, max)  {
	if (!isInt(number)) {
		return false;
	}
	var theValue = (number.split(",").join(""))*1;
	return theValue >= min && theValue <= max
}


/*Esta funcion valida una hora y retorna falso o verdadero*/
/* Recibe el formato de HH:MM*/
function isTime(time)  {
	var HP
	time = trim(time)
	HP = time.split(":")
	if (HP.length != 2) {
		return false;
	}
	return(HP[0] < 24 && HP[0] >= 0 && HP[1] < 60 && HP[1] >= 0)
}


/*Esta funcion valida una fecha y retorna falso o verdadero*/
/* Recibe el formato de Dia/Mes/Ano*/
function isDateDMY(date)  {
	var FP
	date = trim(date)
	FP = date.split("/")
	if (FP.length != 3) {
		return false
	}
	return isSplitDateDMY(FP[0], FP[1], FP[2])
}


function isSplitDateDMY(day, month, year)  {
	if (!isInt(day) || !isInt(month) || !isInt(year)) {
		return false;
	}
	date = new Date(year, month-1, day)
	return ((date.getDate() == (day)*1) && ((date.getMonth()+1) == (month)*1) && (date.getFullYear() == (year)*1));
}


// Retorna si la fecha inferior efectivamente es inferior
// que la fecha superior. La fecha debe encontrarse en formato dd/mm/yyyy
// -1 a < b, 0 a == b, 1 a > b
function compareDatesDMY(a, b) {
	var firstDateArray, secondDateArray;
	firstDateArray = a.split("/");
	secondDateArray = b.split("/");
	aDate = new Date(firstDateArray[2], firstDateArray[1]-1, firstDateArray[0]);
	bDate = new Date(secondDateArray[2], secondDateArray[1]-1, secondDateArray[0]);
	if (aDate.getTime() < bDate.getTime()) {
		return -1;
	}
	if (aDate.getTime() == bDate.getTime()) {
		return 0;
	}
	return 1;
}

function isChecked(field) {
	if (!field) {
		return false;
	}
	if (field.length) {
		for (var i = 0; i < field.length; i++) {
			if (field[i].checked) {
				return true;
			}
		}
		return false;
	}
	return field.checked;
}


/** Valida un email */
function isEmail(str) {
  var r1 = new RegExp("(@.*@)|(\\.\\.)|(@\\.)|(^\\.)");
  var r2 = new RegExp("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");
  return (!r1.test(str) && r2.test(str));
}


/** Estas son la validaciones en si de las formas */

var _campoError = null;
var _errores    = "";

function appendErrorMessage(msgError) {
    _errores += ((_errores != "") ? "\n":"") + "        " + msgError;
}

function validarLista(lista, msgError, indiceInicial, funcion) {
    if (!indiceInicial && indiceInicial != 0) {
        indiceInicial = 1;
    }
    if (lista.selectedIndex < indiceInicial ||
        (funcion ? !eval("funcion(lista)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = lista;
        }
        return false;
    }
    return true;
}


function validarSeleccion(campo, msgError, funcion) {
    if (!isChecked(campo) ||
        	(funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            if (campo.length) {
                _campoError = campo[0]
            } else {
                _campoError = campo;
            }
        }
        return false;
    }
    return true;
}

function validarCampo(campo, msgError, longitudMinima, longitudMaxima, funcion) {

    if (!longitudMinima  && longitudMinima != 0) {
        longitudMinima = 1
    }
    if (!longitudMaxima ) {
		longitudMaxima = Number.MAX_VALUE
    }
    var value = trim(campo.value)

    if (value.length < longitudMinima || value.length > longitudMaxima ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = campo;

        }
        return false;
    }
    return true;
}

function validarEnteroCeroaCien(campo, msgError, longitudMinima, longitudMaxima, funcion){
	
	if(validarCampo(campo, msgError, longitudMinima, longitudMaxima, funcion)){
		 var value = trim(campo.value);
	if (value < 0 || value > 100 || (funcion ? !eval("funcion(campo)") : false)) {
	        appendErrorMessage(msgError);
	        if (_campoError == null) {
	            _campoError = campo;

	        }
	        return false;
	    }
	    return true;
	}
	return false;
}

function validarCampoOculto(campo, msgError,  campo2,longitudMinima, longitudMaxima, funcion) {

    if (!longitudMinima  && longitudMinima != 0) {
        longitudMinima = 1
    }
    if (!longitudMaxima ) {
		longitudMaxima = Number.MAX_VALUE
    }
    var value = trim(campo.value)

    if (value.length < longitudMinima || value.length > longitudMaxima ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null && campo2!="" && campo2!=null) {
            _campoError = campo2;

        }
        return false;
    }
    return true;
}

function validarCampoOpcional(campo, msgError, longitudMinima, longitudMaxima, funcion) {
	if (trim(campo.value).length > 0) {
		return validarCampo(campo, msgError, longitudMinima, longitudMaxima, funcion)
	}
}

function validarFecha(dia, mes, ano, msgError, funcion) {
	 if (!isIntRange(dia.value, 1, 31)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = dia;
        }
        return false;
	}
    if (mes.selectedIndex < 1) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = mes;
        }
        return false;
    }
	if (!isInt(ano.value)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = ano;
        }
        return false;
	}
    if (!isDateDMY(dia.value + "/" + mes.options[mes.selectedIndex].value + "/" + ano.value) ||
        (funcion ? !eval("funcion(dia, mes, ano)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = dia;
        }
        return false;
    }
    return true;
}


function validarFechaOpcional(dia, mes, ano, msgError, funcion) {
	if (trim(dia.value).length > 0 ||
			mes.selectedIndex > 0 || trim(ano.value).length > 0) {
		return validarFecha(dia, mes, ano, msgError, funcion)
	}
}


function validarRangoFechas(dia1,mes1,ano1,dia2,mes2,ano2,msgError,funcion) {
	if (compareDatesDMY(dia1.value + "/" + mes1.value + "/" + ano1.value, dia2.value + "/" + mes2.value + "/" + ano2.value) >= 0) {
        if (_campoError == null) {
            _campoError = dia1;
        }
        return false;
	}
	return true;
}

function validarComparacionFechas(fecha1,fecha2,msgError) {
	alert(fecha1.value);
	//alert(fecha2.value);
	var FP
	var FP1
	var date
	var date2
	date = trim(fecha1.value)
	FP = date.split("/")
	if (FP.length != 3) {
		return false
	}

	fechaInic=FP[2]+"/"+FP[1]+"/"+FP[0]

	date1 = trim(fecha2.value)
			FP1 = date1.split("/")
			if (FP1.length != 3) {
				return false
	}
	fechaFin=FP1[2]+"/"+FP1[1]+"/"+FP1[0]

	if (compareDatesDMY(fechaInic, fechaFin) >= 0) {

		alert("la fecha inicial debe ser menor que la fecha Fin");
		 return false;
        if (_campoError == null) {
            _campoError = msgError;
        }
        return false;
	}

	return true;
}



function validarRangoFechasPeriodo(fecha1,fecha2,msgError,funcion) {
  var firstDateArray, secondDateArray;
	firstDateArray = fecha1.value.split("/");
	secondDateArray = fecha2.value.split("/");
	
	//a?o
	if(firstDateArray[2] > secondDateArray[2]){
	   if (_campoError == null) {
            _campoError = fecha2;
        }
        appendErrorMessage(msgError)
        return false;
	}else if(firstDateArray[2] != secondDateArray[2]) 	return true;
	
	
	
	//mes
	if(firstDateArray[1] > secondDateArray[1]){
	   if (_campoError == null) {
            _campoError = fecha2;
        }
        appendErrorMessage(msgError)
        return false;
	}else if(firstDateArray[1] != secondDateArray[1]) 	return true;
	
	
	// dia
	if(firstDateArray[0]  >= secondDateArray[0]){
	   if (_campoError == null) {
            _campoError = fecha2;
        }
        appendErrorMessage(msgError)
        return false;
	} else	return true;
   
}

function validarEntero(campo, msgError, limiteInferior, limiteSuperior,
		funcion) {
	if (!limiteInferior && limiteInferior != 0) {
		limiteInferior = Number.NEGATIVE_INFINITY
		limiteSuperior = Number.MAX_VALUE
	}
	if (!isIntRange(campo.value, limiteInferior, limiteSuperior) ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = campo;
        }
        return false;
	}
	return true;
}

function validarEnteroOpcional(campo, msgError, limiteInferior, limiteSuperior, funcion) {
	if (trim(campo.value).length > 0) {
		return validarEntero(campo, msgError, limiteInferior, limiteSuperior, funcion)
	}
}


function validarFloat(campo, msgError, limiteInferior, limiteSuperior, funcion) {

	if (!limiteInferior && limiteInferior != 0) {
		limiteInferior = Number.NEGATIVE_INFINITY
		limiteSuperior = Number.MAX_VALUE
	}
	if (!isFloatRange(campo.value, limiteInferior, limiteSuperior) ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = campo;
        }
        return false;
	}
	return true;
}

function validarFloatOpcional(campo, msgError, limiteInferior, limiteSuperior, funcion) {
	if (trim(campo.value).length > 0) {
		return validarFloat(campo, msgError, limiteInferior, limiteSuperior, funcion)
	}
}

function validarEmail(campo, msgError) {
	if (!isEmail(campo.value)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = campo;
        }
        return false;
	}
	return true;
}


function validarEmailOpcional(campo, msgError) {
	if (trim(campo.value).length > 0) {
		return validarEmail(campo, msgError);
	}
}


var _mensajeAdvertencia = "Por favor verifique la siguiente informacion: \n\n";

function validarForma(forma) {
	_errores = ""
	_campoError = null;

	eval("hacerValidaciones_" + forma.name +"(forma)");
	if (_errores != "") {
		alert(_mensajeAdvertencia + _errores);
		_campoError.focus();
		return false;
	}
	return true;
}


 function controlarLongitudTextArea(teclaPresionada, campo, maximaLongitud) {
     var longitud = campo.value.length;

     var valTecla = teclaPresionada.which ? parseInt(teclaPresionada.which, 10)
             : (teclaPresionada.keyCode ? parseInt(teclaPresionada.keyCode, 10)
 : null);

     if (valTecla != null) {
         if ((valTecla != 8) && (valTecla != 46)) {
             if (longitud > maximaLongitud-1) {
                 return false;
             }
         }
     }
     return true;
 }

function validarFechaAIN(campoFecha,msgError) {
    var a, mes, dia, anyo;
    a=campoFecha.value;
	anyo=a.split("/")[0];
    mes=a.split("/")[1];
 	dia=a.split("/")[2];
if (anyo>0 && mes!=null && dia!=null){
    if ((mes<1) || (mes>12))
    {
	appendErrorMessage(msgError+" mes");
	   if (_campoError == null) {
	       _campoError = campoFecha;
	   }
	return false;
    }
    if ((mes==2) && ((dia<1) || (dia>29)))
    {
  	appendErrorMessage(msgError+" dia");
	   if (_campoError == null) {
	       _campoError = campoFecha;
	   }
   	return false;
    }
   if (((mes==1) || (mes==3) || (mes==5) || (mes==7) || (mes==8) || (mes==10) || (mes==12)) && ((dia<1) || (dia>31)))
   {
	  appendErrorMessage(msgError+" dia");
	   if (_campoError == null) {
	       _campoError = campoFecha;
	   }
	  return false;
   }
   if (((mes==4) || (mes==6) || (mes==9) || (mes==11)) && ((dia<1) || (dia>30)))
   {
      appendErrorMessage(msgError+" dia");
	   if (_campoError == null) {
	       _campoError = campoFecha;
	   }
      return false;
   }
   if ((anyo<1900) || (anyo>2050))
   {
 	appendErrorMessage(msgError+" ano");
	   if (_campoError == null) {
	       _campoError = campoFecha;
	   }
 	return false;
   }
   return true;
}else {appendErrorMessage(msgError+" ");
	   if (_campoError == null) {
	       _campoError = campoFecha;
	   }return false;}
}

function validarRangoFechasAIN(dia1,mes1,ano1,dia2,mes2,ano2,msgError,funcion) {
	if (compareDatesDMY(dia1 + "/" + mes1+ "/" + ano1, dia2 + "/" + mes2 + "/" + ano2) >= 0) {
        if (_campoError == null) {
            _campoError = dia1;
        }
        return false;
	}
	return true;
}


/**********nuevas ********************/


// Retorna si la fecha inferior efectivamente es inferior
// que la fecha superior. La fecha debe encontrarse en formato yyyy/mm/dd
// -1 a < b, 0 a == b, 1 a > b
function compareDatesDMYNuevo(a, b) {
	var firstDateArray, secondDateArray;
	firstDateArray = a.split("/");
	secondDateArray = b.split("/");
	//aDate = new Date(firstDateArray[0], firstDateArray[1]-1, firstDateArray[2]);
	//bDate = new Date(secondDateArray[0], secondDateArray[1]-1, secondDateArray[2]);
	aDate = new Date(firstDateArray[2], firstDateArray[1]-1, firstDateArray[0]);
	bDate = new Date(secondDateArray[2], secondDateArray[1]-1, secondDateArray[0]);
	if (aDate.getTime() < bDate.getTime()) {
		return -1;
	}
	if (aDate.getTime() == bDate.getTime()) {
		return 0;
	}
	return 1;
}



function validarRangoFechas(fecha1,fecha2,msgError,funcion) {

	var firstDateArray, secondDateArray;
	firstDateArray = fecha1.value.split("/");
	secondDateArray = fecha2.value.split("/");

	if (compareDatesDMYNuevo(firstDateArray[0] + "/" + firstDateArray[1] + "/" + firstDateArray[2], secondDateArray[0] + "/" + secondDateArray[1] + "/" + secondDateArray[2]) >= 0) {
        if (_campoError == null) {
            _campoError = fecha1;
        }
        appendErrorMessage(msgError)
        return false;
	}
	return true;
}

/*NUEVO 25 NOV 2003*/

function validarFloatOpcionalSinFoco(campo, msgError, limiteInferior, limiteSuperior, funcion) {
	if (trim(campo.value).length > 0) {
		return validarFloatSinFoco(campo, msgError, limiteInferior, limiteSuperior, funcion)
	}
}

function validarFloatSinFoco(campo, msgError, limiteInferior, limiteSuperior, funcion) {
	if (!limiteInferior && limiteInferior != 0) {
		limiteInferior = Number.NEGATIVE_INFINITY
		limiteSuperior = Number.MAX_VALUE
	}
	if (!isFloatRange(campo.value, limiteInferior, limiteSuperior) ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        
        return false;
	}
	return true;
}


function validarRangoAno(campo1, campo2, campo3, campo4, msgError){
	if(campo2.value == campo4.value){
		if(campo3.value <= campo1.value){		
			if (_campoError == null) {
	            _campoError = campo1;
	        }
	        appendErrorMessage(msgError)
	        return false;
		}
	}else{	
	 	if(campo4.value < campo2.value){	 	
	 		if (_campoError == null) {
	            _campoError = campo2;
	        }
	        appendErrorMessage(msgError)
	        return false;
		}
	}
	return true;
}

/* NUEVAS 9 DIC 2003*/
/*Valida una fecha que se encuentra en un solo campo. en formato
DD/MM/YYYY*/
function validarFechaUnCampo(campo, msgError, funcion){
    var value = trim(campo.value)
    if (value.length == 0 || value.length > 10 ||
     !isDateDMY(value) ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = campo;
        }
        return false;
    }
    return true;
}

function validarFechaOpcional(fecha, msgError, funcion) {
	if (fecha.value != "") {
		return validarFechaUnCampo(fecha, msgError, funcion)
	}
}

function validarFloatSinFoco(campo,campo2, msgError, limiteInferior, limiteSuperior, funcion) {
	if (!limiteInferior && limiteInferior != 0) {
		limiteInferior = Number.NEGATIVE_INFINITY
		limiteSuperior = Number.MAX_VALUE
	}
	var msg="";
    msg=((campo.value.indexOf(",")<0)?"":" (No se permiten comas)");
	if (!isFloatRange(campo.value, limiteInferior, limiteSuperior) ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError+msg)
        if (_campoError == null) {
            _campoError = campo2;
        }
        return false;
	}
	return true;
}

function validarFloatOpcionalSinFoco(campo, campo2, msgError, limiteInferior, limiteSuperior, funcion) {
	if (trim(campo.value).length > 0) {
		return validarFloatSinFoco(campo, campo2,msgError, limiteInferior, limiteSuperior, funcion)
	}
}
function validarMenorIgualSinFoco(campo1, campo2,campo3,msgError){
	if(parseFloat(campo1.value) > parseFloat(campo2.value)){
		appendErrorMessage(msgError);
		if (_campoError == null) {
            _campoError = campo3;
        }
		return false;
	}else{
		return true;
	}
}	

function validarEnteroSinFoco(campo, campo2,msgError, limiteInferior, limiteSuperior,funcion) {
	if (!limiteInferior && limiteInferior != 0) {
		limiteInferior = Number.NEGATIVE_INFINITY
		limiteSuperior = Number.MAX_VALUE
	}
	if (!isIntRange(campo.value, limiteInferior, limiteSuperior) ||
        (funcion ? !eval("funcion(campo)") : false)) {
        appendErrorMessage(msgError)
        if (_campoError == null) {
            _campoError = campo2;
        }
        return false;
	}
	return true;
}
function validarEnteroOpcionalSinFoco(campo, campo2,msgError, limiteInferior, limiteSuperior, funcion) {
	if (trim(campo.value).length > 0) {
		return validarEnteroSinFoco(campo, campo2,msgError, limiteInferior, limiteSuperior, funcion)
	}
}
//nevos
function validarNumeros(msgError, limiteInferior, limiteSuperior,funcion) {
	if (!limiteInferior && limiteInferior != 0) {
		limiteInferior = Number.NEGATIVE_INFINITY
		limiteSuperior = Number.MAX_VALUE
	}
	if(limiteInferior>limiteSuperior || (funcion ? !eval("funcion(campo)") : false)){
        appendErrorMessage(msgError)
        return false;
	}
	return true;
}
//NUEVOS ATHENEA
//metodos para validar un campo con una fecha
function esDigito(sChr){
	var sCod = sChr.charCodeAt(0);
	return ((sCod > 47) && (sCod < 58));
}
				
function valSep(oTxt){
	var bOk = false;
	bOk = bOk || ((oTxt.value.charAt(2) == "-") && (oTxt.value.charAt(5) == "-"));
	bOk = bOk || ((oTxt.value.charAt(2) == "/") && (oTxt.value.charAt(5) == "/"));
	return bOk;
}
				
function finMes(oTxt){
	var nMes = parseInt(oTxt.value.substr(3, 2), 10);
	var nRes = 0;
	switch (nMes){
		case 1: nRes = 31; break;
		case 2: nRes = 29; break;
		case 3: nRes = 31; break;
		case 4: nRes = 30; break;
		case 5: nRes = 31; break;
		case 6: nRes = 30; break;
		case 7: nRes = 31; break;
		case 8: nRes = 31; break;
		case 9: nRes = 30; break;
		case 10: nRes = 31; break;
		case 11: nRes = 30; break;
		case 12: nRes = 31; break;
	}
	return nRes;
}
				
function valDia(oTxt){
	var bOk = false;
	var nDia = parseInt(oTxt.value.substr(0, 2), 10);
	bOk = bOk || ((nDia >= 1) && (nDia <= finMes(oTxt)));
	return bOk;
}
				
function valMes(oTxt){
	var bOk = false;
	var nMes = parseInt(oTxt.value.substr(3, 2), 10);
	bOk = bOk || ((nMes >= 1) && (nMes <= 12));
	return bOk;
}
				
function valAno(oTxt){
	var bOk = true;
	var nAno = oTxt.value.substr(6);
	bOk = bOk && ((nAno.length == 2) || (nAno.length == 4));
	if (bOk){
		for (var i = 0; i < nAno.length; i++){
			bOk = bOk && esDigito(nAno.charAt(i));
		}
	}
	return bOk;
}

function valFecha(oTxt,msgError){
	var bOk = true;
	if (oTxt.value != ""){
		bOk = bOk && (valAno(oTxt));
		bOk = bOk && (valMes(oTxt));
		bOk = bOk && (valDia(oTxt));
		bOk = bOk && (valSep(oTxt));
		if (!bOk){
		   appendErrorMessage(msgError)
			if (_campoError == null) {
				_campoError = oTxt;
			}
			return false;
		}
	}
	return true;
}		 

//correo
function validarcorreo(correo, msgError) {
	if (correo.value.indexOf('@', 0) == -1 || correo.value.indexOf('.', 0) == -1) {
	   appendErrorMessage(msgError)
		if (_campoError == null) {
			_campoError = correo;
		}
		return false;
	}
	return true;
}

function validarCorreoOpcional(correo, msgError) {
	if (correo.value != "") {
		if (correo.value.indexOf('@', 0) == -1 || correo.value.indexOf('.', 0) == -1) {
		   appendErrorMessage(msgError)
			if (_campoError == null) {
				_campoError = correo;
			}
			return false;
		}
	}
	return true;
}

function validarDominioCorreo(correo, msgError) {
	
	var dominio = "";
	var posicionArroba = 0;
	
	var expr1 = "educacionbogota.edu.co";
	var expr2 = "educacionbogota.gov.co";
	
	posicionArroba = correo.value.indexOf('@');
	
	if (posicionArroba > 0) {
		
		dominio = correo.value.toLowerCase().substring(posicionArroba+1);
		
		if (dominio != expr1 && dominio != expr2) {
			
			appendErrorMessage(msgError);
			
			if (_campoError == null) {
				_campoError = correo;
			}
			
			return false;
		}	
		
		return true;
		
	}
	
	return false;
}


//contraseña y confirmación de contraseña
function validarContrasena(contrasena1, contrasena2, msgError) {
	if (contrasena1.value.length == 0 || contrasena2.value.length == 0) {
	   appendErrorMessage(msgError)
		if (_campoError == null) {
			_campoError = contrasena1;
		}
		return false;
	}
	
	if (contrasena1.value != contrasena2.value) {
		appendErrorMessage(msgError)
		if (_campoError == null) {
			_campoError = contrasena1;
		}
		return false;
	}
}

// Valida que el número de documento no sea usado como contraseña
function validarUsuarioComoContrasena(numeroDocumento, contrasena, msgError) {
	if (numeroDocumento.value == contrasena.value) {
		appendErrorMessage(msgError);
		if (_campoError == null) {
			_campoError = contrasena;
		}
		return false;
	}
}

//Valida que la nueva contraseña no sea la misma que la actual
function validarReusarContrasena(contrasenaActual, contrasenaNueva, msgError) {
	if (contrasenaActual.value == contrasenaNueva.value) {
		appendErrorMessage(msgError);
		if (_campoError == null) {
			_campoError = contrasenaNueva;
		}
		return false;
	}
}

//validacion de extension
function validarExtension(file,msg,extension) {
	var archivo=file.value;
	if (archivo=="") 
		return true;			
	while (archivo.indexOf("\\") != -1)
		archivo = archivo.slice(archivo.indexOf("\\") + 1);
		//ext = archivo.slice(archivo.indexOf(".")).toLowerCase();
		ext = archivo.slice(archivo.lastIndexOf(".")).toLowerCase();
		if (extension) {
			if (extension.length) {
				for (var i = 0; i < extension.length; i++) {
					if (extension[i]==ext) {
						return true;
					}
				}
			}
		}
		appendErrorMessage(msg);
		if (_campoError == null) 
			_campoError = file;
		return false;		
}

//validacion de extension que no debe estar
function validarNoExtension(file,msg,extension) {
	var archivo=file.value;
	if (archivo=="") 
		return true;			
	while (archivo.indexOf("\\") != -1)
		archivo = archivo.slice(archivo.indexOf("\\") + 1);
		//ext = archivo.slice(archivo.indexOf(".")).toLowerCase();
		ext = archivo.slice(archivo.lastIndexOf(".")).toLowerCase();
		if (extension) {
			if (extension.length) {
				for (var i = 0; i < extension.length; i++) {
					if (extension[i]==ext) {
						appendErrorMessage(msg);
						if (_campoError == null) 
							_campoError = file;
						return false;		
					}
				}
			}
		}
		return true;
}

function mensaje(msg){
	if (msg && msg.value && msg.value!=""){	
		alert(msg.value);
	}
}

function textCounter(field, countfield, maxlimit) {
	var sal=field.value.match(/\n+/g);
	var lon=field.value.length;
	lon=lon+(sal?sal.length : 0);
	if (lon > maxlimit) // if too long...trim it!
		field.value = field.value.substring(0, (maxlimit-(sal?sal.length : 0)));
		// otherwise, update 'characters left' counter
	else
	countfield.value = maxlimit - field.value.length;
}

function borrar_combo(combo) {
	for(var i = combo.length - 1; i >= 0; i--) {
		if(navigator.appName == "Netscape") combo.options[i] = null;
		else combo.remove(i);
	}
	combo.options[0] = new Option("-Seleccione uno-","-99");
}

function getCantidad(forma, tipo, nombre) {
	var n=0;
	for(var i=0;i<forma.elements.length;i++){
		if(forma.elements[i].type==tipo && forma.elements[i].name==nombre){n++;}
	}
	return n;
}