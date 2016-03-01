<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Détails de la bière no. ${param['noBiere']}</h2>

<c:if test="${not empty requestScope.biere}">
	
	<c:if test="${not empty biere.image}">
		<img src="images/bieres/${biere.image}" alt="Photo de ${biere.nom}" />
	</c:if>
	<p>Nom: ${biere.nom}</p>
	<p>No. Brasseur: ${biere.noBrasseur}</p>
	<p>No. Categorie: ${biere.noCategorie}</p>
	<p>No. Style: ${biere.noStyle}</p>
	<p>Taux d'alcohol: ${biere.tauxAlcool}</p>
	<p>Derniere modification: ${biere.dateModification}</p>
	
	<h2>Description</h2> 
	
	<p>${biere.description}</p>
</c:if>