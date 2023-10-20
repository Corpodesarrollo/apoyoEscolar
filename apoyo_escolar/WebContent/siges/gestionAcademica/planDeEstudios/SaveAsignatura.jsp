<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="asignaturaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.AsignaturaVO" scope="session"/>
<jsp:setProperty name="asignaturaPlanVO" property="*"/>
<jsp:useBean id="filtroAsignaturaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.FiltroAsignaturaVO" scope="session"/>
<jsp:setProperty name="filtroAsignaturaPlanVO" property="*"/>
<c:import url="/planDeEstudios/Nuevo.do"/>