<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<%-- Requête sélectionnant les informations du membre du personnel --%>

<h2>Vos informations</h2>

<p>Votre No. d'utilisateur: ${sessionScope.modConnexion.connexionBean.noUtil}</p>
<p>Votre nom: ${sessionScope.modConnexion.connexionBean.nom}</p>