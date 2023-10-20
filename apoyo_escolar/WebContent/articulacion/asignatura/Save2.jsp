<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="requisitoVO" class="articulacion.asignatura.vo.RequisitoVO" scope="session"/>
<jsp:setProperty name="requisitoVO" property="*"/>
<c:import url='/articulacion/asignatura/CoRequisito.do'/>