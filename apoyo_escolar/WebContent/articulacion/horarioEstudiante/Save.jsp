<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="componenteVO" class="articulacion.horarioEstudiante.vo.ComponenteVO" scope="session"/>
<jsp:setProperty name="componenteVO" property="*"/>
<c:import url="/articulacion/horarioEstudiante/Lista.do"/>