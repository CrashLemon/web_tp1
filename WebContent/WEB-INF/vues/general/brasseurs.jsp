<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<h2>Les brasseurs</h2>

<%-- Requête sélectionnant les brasseurs --%>
<sql:query var="brasseurs" dataSource="jdbc/bieres">
	SELECT *
	FROM brasseurs
</sql:query>

<%-- Parcours et affichage des brasseurs --%>
<c:forEach var="infoB" items="${brasseurs.rows}">
	<div class="brasseur">
		<h3>Brasseur no. ${infoB.id}</h3>
		<div class="details-brasseur">
		
			<c:if test="${not empty infoB.image}">
				<img src="images/brasseurs/brasseur-${infoB.id}.${infoB.image}" alt="Photo de ${infoB.nom}" />
			</c:if>
		
			Infos : ${infoB.nom}<br />
			<a href="${infoB.site_web}">${infoB.site_web}</a><br />
			
		</div>
	</div>
</c:forEach>