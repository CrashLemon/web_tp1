<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html xml:lang="fr-ca" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Erreur: Exception Java</title>

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
				<c:import url="/WEB-INF/vues/entete.jsp" />
			</header>

			<nav>
				<c:import url="/WEB-INF/vues/menu.jsp" />
			</nav>

			<div id="contenu">
				<h2 class="erreurTitre">Erreur: Exception Java</h2>

				<h3>Message</h3>

				<p class="erreurMsg">${pageContext.exception.message} (${pageContext.exception.class.name})</p>

				<h3>Trace</h3>

				<ul>
					<c:forEach var="st" items="${pageContext.exception.stackTrace}">
						<li>${st}</li>
					</c:forEach>
				</ul>

			</div>  <!-- Fin "Contenu" -->
			
			<footer>
			<c:import url="/WEB-INF/vues/pied-page.jsp" />
		</footer>

		</div>  <!-- Fin "Page" -->

	</body>
</html>
