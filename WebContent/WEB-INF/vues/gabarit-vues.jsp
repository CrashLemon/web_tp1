<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>

<html xml:lang="fr-ca" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Les Bières du Monde :: ${requestScope["vueSousTitre"]}</title>

		<meta charset="UTF-8" />

		<meta name="description" content="biere" />
		<meta name="keywords" content="biere, critique" />
		<meta name="author" content="Stéphane Lapointe, Karine Filiatreault" />
		<meta name="author" content="Étudiants du cours 420-02E-FX" />

		<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo-biere-url.png" />

		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/biere-design.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/biere-base.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/biere-contenu.css" />

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
				<c:import url="${requestScope['vueContenu']}" />
			</div>

			<footer>
				<c:import url="/WEB-INF/vues/pied-page.jsp" />
			</footer>
			
		</div>  <!-- Fin "Page" -->

	</body>
</html>
