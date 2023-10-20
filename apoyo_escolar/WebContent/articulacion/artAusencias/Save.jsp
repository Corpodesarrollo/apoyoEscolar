<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="estDiasVO" class="articulacion.artAusencias.vo.EstDiasVO" scope="session"/>
<jsp:setProperty name="estDiasVO" property="*"/>
<c:import url="/articulacion/artAusencias/Lista.do"/>