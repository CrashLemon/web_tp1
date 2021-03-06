<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul id="menu-princ">
	<li><a href="${pageContext.request.contextPath}/rech-bieres">Recherche de bières</a></li>
	<li><a href="${pageContext.request.contextPath}/brasseurs">Les brasseurs</a></li>
	
	<!-- À changer -->
	<c:if test="${sessionScope.modConnexion.connexionBean.getModeConn() == 'ADMIN'}">
		<li><a href="${pageContext.request.contextPath}/admin">Vos informations</a></li>
		<li><a href="${pageContext.request.contextPath}/admin/ajout-biere">Ajout d'une bière</a></li>
	</c:if>
	
	<c:if test="${sessionScope.modConnexion.connexionBean.getModeConn() == 'MEMBRE'}">
		<li><a href="${pageContext.request.contextPath}/membre">Vos informations</a></li>
	</c:if>
</ul>  <!-- Fin "menu-princ" -->
