<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="instanciaVO" class="participacion.instancia.vo.InstanciaVO" scope="session"/>
<jsp:setProperty name="instanciaVO" property="*"/>
<jsp:useBean id="filtroInstanciaVO" class="participacion.instancia.vo.FiltroInstanciaVO" scope="session"/>
<jsp:setProperty name="filtroInstanciaVO" property="*"/>
<jsp:useBean id="objetivoVO" class="participacion.instancia.vo.ObjetivoVO" scope="session"/>
<jsp:setProperty name="objetivoVO" property="*"/>
<jsp:useBean id="representanteVO" class="participacion.instancia.vo.RepresentanteVO" scope="session"/>
<jsp:setProperty name="representanteVO" property="*"/>
<jsp:useBean id="documentoVO" class="participacion.instancia.vo.DocumentoVO" scope="session"/>
<jsp:setProperty name="documentoVO" property="*"/>
<c:import url="/participacion/instancia/Nuevo.do"/>