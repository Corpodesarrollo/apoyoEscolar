<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="especialidadVO" class="articulacion.especialidad.vo.EspecialidadVO" scope="session"/>
<jsp:setProperty name="especialidadVO" property="*"/>
<c:import url="/articulacion/especialidad/Nuevo.do"/>