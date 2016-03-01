<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 
<img src="${pageContext.request.contextPath}/images/logo-biere.png" alt="Logo Les bières du monde" />
-->
<div id="section-connexion">

	<%-- Formulaire de connexion --%>
	<c:choose>
		<c:when test="${sessionScope.modConnexion.connexionBean == null}">
			<form method="post" action="${pageContext.request.contextPath}/connexion">
				<p>
					<label for="identifiant">Identifiant : </label>
					<input type="text" name="identifiant" id="identifiant" class="txt" />
				</p>
				<p>
					<label for="motPasse">Mot de passe : </label>
					<input type="password" name="motPasse" id="motPasse" class="txt" onfocus="this.value=''" />
				</p>
				<p>
					<label for="typeConn">Personnel : </label>
					<input type="checkbox" name="typeConn" id="typeConn" value="admin" />
					<input type="submit" name="btnConnexion" value="Connexion" class="btn" />
				</p>
				<c:if test="${sessionScope.modConnexion.erreurDeConnexion != null}">
					<p>${sessionScope.modConnexion.erreurDeConnexion}</p>
				</c:if>
			</form>
		</c:when>
		<c:otherwise>
			<form method="get" action="${pageContext.request.contextPath}/deconnexion">
				<c:choose>
					<c:when test="${sessionScope.modConnexion.connexionBean.getModeConn() == 'ADMIN'}">
						<p>(${sessionScope.modConnexion.connexionBean.nom})</p>
						<p>No: ${sessionScope.modConnexion.connexionBean.noUtil}</p>
						<p>Employe</p>
					</c:when>
					<c:when test="${sessionScope.modConnexion.connexionBean.getModeConn() == 'MEMBRE'}">
						<p>(${sessionScope.modConnexion.connexionBean.nomUtil})</p>
						<p>Membre</p>
					</c:when>
					<c:otherwise>
						<p>Aucune Connexion</p>
					</c:otherwise>
				</c:choose>
				<p>
					<input type="submit" name="btnDeconnexion" value="Deconnexion" class="btn" />
				</p>
			</form>
		</c:otherwise>
	</c:choose>

</div>

<h1>Les bières du monde</h1>
