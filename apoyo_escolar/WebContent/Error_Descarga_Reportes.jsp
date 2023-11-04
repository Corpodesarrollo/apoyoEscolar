<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
    // Funci�n para mostrar la alerta de error
    function mostrarError(mensaje) {
        document.getElementById("errorMessage").innerHTML = mensaje;
        document.getElementById("errorAlert").style.display = "block";
    }

    // Llama a esta funci�n cuando el archivo no existe o no est� disponible
    // Por ejemplo, en tu servlet despu�s de verificar la disponibilidad del archivo
    function archivoNoDisponible() {
        mostrarError("El archivo no existe o no est� disponible para su descarga.");
    }
</script>
</head>
<body>
<div id="errorAlert" style="display: none;">
    <p id="errorMessage"></p>
</div>
</body>
</html>