function acepteNumeros(eve) {
	var key = nav4 ? eve.which : eve.keyCode;
	return (key <= 13 || (key >= 48 && key <= 57));
}

function lanzar(tipo) {
	document.frmNuevo.tipo.value = tipo;
	document.frmNuevo.target = "";
	document.frmNuevo.submit();
}

function guardar() {
	if (validarForma(document.frmNuevo)) {
		validarCampos(document.frmNuevo);
		document.frmNuevo.cmd.value = document.frmNuevo.GUARDAR.value;
		document.frmNuevo.submit();
	}
}

function validarCampos(forma) {
	// campo Otro Enfasis
	forma.horDificultad.disabled = false;
	if (trim(forma.horDificultad.value) == '') {
		forma.horDificultad.value = ' ';
	}
}

function hacerValidaciones_frmNuevo(forma) {
	validarCampoOpcional(forma.horDificultad, "- Dificultades", 1, 600)
}
