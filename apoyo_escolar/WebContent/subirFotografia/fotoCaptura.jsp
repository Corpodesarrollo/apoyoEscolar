<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="siges.subirFotografia.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="siges.filtro.vo.FichaVO" scope="page"/>
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/>
<html>
<head>




    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Capturar Foto Estudiante</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel='stylesheet' type='text/css' media='screen' href='photo/demo/style/webcam-demo.css'>
    <script src="//code.jquery.com/jquery-3.3.1.min.js"></script>  
    <script src="photo/dist/webcam-easy.min.js"></script> 
</head>



<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">

<FORM ACTION='<c:url value="/subirFotografia/PhotoUploader.do"/>' METHOD="POST" name='frmNuevo' >
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_SUBIR}"/>'>
		<input type="hidden" name="cmd" VALUE='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
		<INPUT TYPE="hidden" NAME="height" VALUE='130'>
		<input type="hidden" id="filArchivo2" name="filArchivo2" style='width:400px;'>
    <main id="webcam-app">
        <div class="form-control webcam-start" id="webcam-control">
                <label class="form-switch">
                <input type="checkbox" id="webcam-switch">
                <i></i> 
                <span id="webcam-caption">Click para Iniciar Camara</span>
                </label>      
                <button id="cameraFlip" class="btn d-none"></button>                  
        </div>
        
        <div id="errorMsg" class="col-12 col-md-6 alert-danger d-none">
            Fail to start camera, please allow permision to access camera. <br/>
            If you are browsing through social media built in browsers, you would need to open the page in Sarafi (iPhone)/ Chrome (Android)
            <button id="closeError" class="btn btn-primary ml-3">OK</button>
        </div>
        <div class="md-modal md-effect-12">
            <div id="app-panel" class="app-panel md-content row p-0 m-0">     
                <div id="webcam-container" class="webcam-container col-12 d-none p-0 m-0">
                    <video id="webcam" autoplay playsinline width="640" height="480"></video>
                    <canvas id="canvas" class="d-none"></canvas>
                    <div class="flash"></div>
                    <audio id="snapSound" src="photo/demo/audio/snap.wav" preload = "auto"></audio>
                </div>
                <div id="cameraControls" class="cameraControls">
                    <a href="#" id="exit-app" title="Cerrar" class="d-none"><i class="material-icons">exit_to_app</i></a>
                    <a href="#" id="take-photo" title="Tomar Foto"><i class="material-icons">camera_alt</i></a>
                    <a href="#" id="download-photo" download="photo.jpeg" target="_blank" title="Descargar Foto" class="d-none">
					<i class="material-icons">file_download</i></a>  
                    <a href="#" id="resume-camera"  title="Reanudar cámara" class="d-none"><i class="material-icons">camera_front</i></a>
                    <a href="#" id="save-photo" title="Guardar Foto App Apoyo Escolar" class="d-none"><i class="material-icons">save</i></a>
                    
                     
                    

                     
                   
                     
                </div>
            </div>        
        </div>
        <div class="md-overlay"></div>
    </main>
    <script src='photo/demo/js/app.js'></script>
    
    </FORM>
</body>
</html>