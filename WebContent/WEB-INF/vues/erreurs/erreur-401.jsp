<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html xml:lang="fr-ca" xmlns="http://www.w3.org/1999/xhtml">
	<head>
	
		<title>Bières :: Erreur :: Accès interdit</title>

		<meta charset="utf-8" />

		<meta name="description" content="Les bières du monde" />
		<meta name="keywords" content="bières du monde, bière, critique" />
		<meta name="author" content="Stéphane Lapointe" />
		<meta name="author" content="Étudiants du cours 420-02E-FX" />

		<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo-biere-url.png.png" />

		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sakila-design.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sakila-base.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sakila-contenu.css" />
	</head>

	<body>

		<div id="page">

		<header>
			<h1>Bières du Monde</h1>
		</header>

		<div id="contenu">
			<h2>Erreur</h2>

			<p id="msg-err-http-et-exception">Accès interdit (HTTP 401)</p>

			<%-- Affichage d'un lien approprié en fonction du mode de connexion --%>
			<c:choose>
				<c:when test="${sessionScope['modeConn'] == 'MEMBRE'}">
					<a href="${pageContext.request.contextPath}/membre">Retour à votre page personnelle</a>
				</c:when>
				<c:when test="${sessionScope['modeConn'] == 'ADMIN'}">
					<a href="${pageContext.request.contextPath}/admin">Retour à la page d'administration du site Web</a>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/">Retour à l'accueil</a>
				</c:otherwise>
			</c:choose>
		</div>

		<footer>
			<c:import url="/WEB-INF/vues/pied-page.jsp" />
		</footer>

		</div>  <!-- Fin de la division "page" -->

	</body>
</html>

