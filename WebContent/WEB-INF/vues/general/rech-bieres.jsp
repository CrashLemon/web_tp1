<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Recherche de bières</h2>

<c:set var="erreurRecherche" value="${requestScope.modRechBieres.erreurDeRecherche}"/>

<!-- Formulaire de recherche de bières -->
<div id="form-rech-bieres">
	<form method="get" action="${pageContext.request.contextPath}/rech-bieres">
		<p>
			<%-- Champ texte pour le mot clé; initialisé avec la valeur du paramètre
				 pour les critères de recherche --%>
			<label for="mot-cle">Mot clé : </label>
			<c:choose>
				<c:when test="${sessionScope['mot-cle'] != null && erreurRecherche == null}">
					<input type="text" name="mot-cle" id="mot-cle" value="${sessionScope['mot-cle']}" />	
				</c:when>
				<c:otherwise>
					<input type="text" name="mot-cle" id="mot-cle" value="${fn:trim(param['mot-cle'])}" />
				</c:otherwise>
			</c:choose>
		</p>
		<p>
			<label for="taux-minimum">Taux minimum : </label>
			<c:choose>
				<c:when test="${sessionScope['taux-minimum'] != null && erreurRecherche == null}">
					<input type="text" name="taux-minimum" id="taux-minimum" value="${sessionScope['taux-minimum']}" />	
				</c:when>
				<c:otherwise>
					<input type="text" name="taux-minimum" id="taux-minimum" value="${fn:trim(param['taux-minimum'])}" />
				</c:otherwise>
			</c:choose>
		</p>
		<p>
			<label for="taux-maximum">Taux maximum : </label>
			<c:choose>
				<c:when test="${sessionScope['taux-maximum'] != null && erreurRecherche == null}">
					<input type="text" name="taux-maximum" id="taux-maximum" value="${sessionScope['taux-maximum']}" />	
				</c:when>
				<c:otherwise>
					<input type="text" name="taux-maximum" id="taux-maximum" value="${fn:trim(param['taux-maximum'])}" />
				</c:otherwise>
			</c:choose>
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
		
		<p>
			<input type="submit" name="rech-bieres" value="Rechercher les bieres" class="btn" />
		</p>
	</form>
</div>  <!-- Fin de la division "formRechBieres" -->

<c:choose>

	<c:when test="${erreurRecherche != null}">
		<h2>Erreur!</h2>
		<p>Erreur: <c:out value="${erreurRecherche}"/></p>
	</c:when>

	<c:when test="${not empty param['rech-bieres']}">
	
		<h2>Bières trouvées</h2>
		
		<c:set var="urlPage" scope="request" value="rech-bieres?${requestScope['javax.servlet.forward.query_string']}"/>
		<c:set var="noPage" scope="request" value="${param['noPage']}"/>
		
		<c:set var="toRemove" scope="request">&noPage=${noPage}</c:set>
		<c:set var="urlPage" scope="request" value="${fn:replace(urlPage, toRemove, '')}"/>
		
		<c:choose>
			
			<c:when test="${empty requestScope.modRechBieres.lstBieresTrouvees}">
				<p>Aucune bière trouvée</p>
			</c:when>
				
			<c:otherwise>
				
				<c:set var="nombreBiere" scope="request" value="${0}"/>
				
				<c:forEach var="biere" items="${requestScope.modRechBieres.lstBieresTrouvees}">
					<c:set var="nombreBiere" scope="request" value="${nombreBiere + 1}"/>
				</c:forEach>
				
				
				<p>${nombreBiere} bières trouvées.</p>

				<table>
					<tr>
						<th>Nom</th>
						<th>Brasseur</th>
						<th>Catégorie</th>
						<th>Taux d'alcool</th>
					</tr>

					<%-- Parcours et affichage des bières trouvées --%>
					<c:forEach begin="${(noPage * 10)}" end="${(noPage * 10) + 10}" varStatus="loop">
						<c:if test="${loop.index >= 0 && loop.index < nombreBiere}">
							<c:set var="biere" value="${requestScope.modRechBieres.lstBieresTrouvees[loop.index]}"/>
					    		<tr>
									<tr>
									<td><a href="${pageContext.request.contextPath}/details-biere?noBiere=${biere.noBiere}">${biere.nom}</a></td>
									<td>${biere.noBrasseur}</td>
									<c:choose>
										<c:when test="${listecategories.rows[biere.noCategorie - 1] != null}">
											<td>${listecategories.rows[biere.noCategorie - 1].nom}</td>
										</c:when>
										<c:otherwise>
											<td>Aucune</td>
										</c:otherwise>
									</c:choose>	
									<td>${biere.tauxAlcool} %</td>
								</tr>
						</c:if>
					</c:forEach>
				</table>
				
				<!-- Barre de navigation pour les pages de résultats de la recherche de bières -->
				<div id="nav-page-bieres">
					
					<c:if test="${noPage > 0}">
						<a href="${urlPage}&noPage=${noPage - 1}">
							<img src="${pageContext.request.contextPath}/images/fleche-gauche.png" alt="Page précédente"/>
							Page précédente
						</a>
					</c:if>
					|
					<c:if test="${(noPage + 1) * 10 < nombreBiere}">
						<a href="${urlPage}&noPage=${noPage + 1}">
							Page suivante
							<img src="${pageContext.request.contextPath}/images/fleche-droite.png" alt="Page suivante"/>
						</a>
					</c:if>
				</div>
	
			</c:otherwise>
	
		</c:choose>
				
	</c:when>
		
</c:choose>
