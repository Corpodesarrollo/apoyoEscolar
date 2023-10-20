<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="discapacidadVO" class="participacion.parametros.vo.DiscapacidadVO" scope="session"/>
<jsp:setProperty name="discapacidadVO" property="*"/>
<jsp:useBean id="etniaVO" class="participacion.parametros.vo.EtniaVO" scope="session"/>
<jsp:setProperty name="etniaVO" property="*"/>
<jsp:useBean id="lgtbVO" class="participacion.parametros.vo.LgtbVO" scope="session"/>
<jsp:setProperty name="lgtbVO" property="*"/>
<jsp:useBean id="ocupacionVO" class="participacion.parametros.vo.OcupacionVO" scope="session"/>
<jsp:setProperty name="ocupacionVO" property="*"/>
<jsp:useBean id="rolVO" class="participacion.parametros.vo.RolVO" scope="session"/>
<jsp:setProperty name="rolVO" property="*"/>
<c:import url="/participacion/parametros/Nuevo.do"/>