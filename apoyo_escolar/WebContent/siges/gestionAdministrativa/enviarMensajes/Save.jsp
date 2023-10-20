<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroMensajeVO" class="siges.gestionAdministrativa.enviarMensajes.vo.FiltroEnviarVO" scope="session"/>
<jsp:setProperty name="filtroMensajeVO" property="*"/>
<jsp:useBean  id="mensajesVO" class="siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO" scope="session"/>
<jsp:setProperty name="mensajesVO" property="*"/>
<c:import url="/siges/gestionAdministrativa/enviarMensajes.do"/>