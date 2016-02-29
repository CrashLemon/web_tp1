<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<h2>Recherche de bières</h2>

<!-- Formulaire de recherche de bières -->
<div id="form-rech-bieres">
	<form method="get" action="${pageContext.request.contextPath}/rech-bieres">
		<p>
			<%-- Champ texte pour le mot clé; initialisé avec la valeur du paramètre
				 pour les critères de recherche --%>
			<label for="mot-cle">Mot clé : </label>
			<input type="text" name="mot-cle" id="mot-cle" value="${fn:trim(param['mot-cle'])}" />
		</p>
		<p>
			<label for="taux-minimum">Taux minimum : </label>
			<input type="text" name="taux-minimum" id="taux-minimum" value="${fn:trim(param['taux-minimum'])}" />
		</p>
		<p>
			<label for="taux-maximum">Taux maximum : </label>
			<input type="text" name="taux-maximum" id="taux-maximum" value="${fn:trim(param['taux-maximum'])}" />
		</p>
		<p>
			<%-- Requête pour les catégories --%>
			<sql:query var="listecategories" dataSource="jdbc/bieres">
				SELECT id, nom 
				FROM categories
			</sql:query>

			<label for="categorie">Catégorie : </label>

			<select name="categorie" id="categorie">

				<%-- Production de la liste pour les différentes catégories --%>
				<option>Toutes</option>

				<c:forEach var="categorie" items="${listecategories.rows}">
					<c:choose>
						<%-- Vérification si l'option doit être sélectionnée par défaut
							 en fonction de la valeur du bean pour les critères de recherche) --%>
						<c:when test="${param['categorie'] == categorie.nom}">
							<option selected="selected">${categorie.nom}</option>
						</c:when>
						<c:otherwise>
							<option value="${categorie.id}">${categorie.nom}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>

			</select>
		
		</p>
		
		<c:set var="erreurRecherche" scope="session" value="${requestScope.modRechBieres.erreurDeRecherche}"/>
		<c:if test="${erreurRecherche != null}">
			<p><c:out value="${erreurRecherche}"/></p>
		</c:if>
		
		<p>
			<input type="submit" name="rech-bieres" value="Rechercher les bieres" class="btn" />
		</p>
	</form>
</div>  <!-- Fin de la division "formRechBieres" -->


<c:if test="${not empty param['rech-bieres']}">

	<h2>Bières trouvées</h2>

	<c:choose>

		<c:when test="${empty requestScope.modRechBieres.lstBieresTrouvees}">
			<p>Aucune bière trouvée</p>
		</c:when>
			
		<c:otherwise>

			<table>
				<tr>
					<th>Nom</th>
					<th>Brasseur</th>
					<th>Catégorie</th>
					<th>Taux d'alcool</th>
				</tr>
				<%-- Parcours et affichage des bières trouvées --%>
				<c:forEach var="biere" items="${requestScope.modRechBieres.lstBieresTrouvees}">
					<tr>
						<tr>
						<td><a href="${pageContext.request.contextPath}/details-biere?noBiere=${biere.noBiere}">${biere.nom}</a></td>
						<td>${biere.noBrasseur}</td>
						<td>${biere.noCategorie}</td>
						<td>${biere.tauxAlcool} %</td>
					</tr>
				</c:forEach>
			</table>
			
			<!-- Barre de navigation pour les pages de résultats de la recherche de bières -->
			<div id="nav-page-bieres">

				<a href="#">
					<img src="${pageContext.request.contextPath}/images/fleche-gauche.png" alt="Page précédente"/>
					Page précédente
				</a>
				|
				<a href="#">
					Page suivante
					<img src="${pageContext.request.contextPath}/images/fleche-droite.png" alt="Page suivante"/>
				</a>
			</div>

		</c:otherwise>

	</c:choose>
			
</c:if>
