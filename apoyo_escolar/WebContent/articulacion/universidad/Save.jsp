<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="universidadVO" class="articulacion.universidad.vo.UniversidadVO" scope="session"/>
<jsp:setProperty name="universidadVO" property="*"/>
<c:import url="/articulacion/universidad/Nuevo.do"/>