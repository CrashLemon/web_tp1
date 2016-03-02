<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!-- Affichage des informations du membre du personnel -->
<h2>Vos informations</h2>

<p>Votre username: ${sessionScope.modConnexion.connexionBean.nomUtil}</p>
<p>Votre email: ${sessionScope.modConnexion.connexionBean.email}</p>
<p>Votre dernier login: ${sessionScope.modConnexion.connexionBean.dernierLogin}</p>