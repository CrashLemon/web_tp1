<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<%-- Requête sélectionnant les informations du membre du personnel --%>
<sql:query var="infoPerso" dataSource="jdbc/bieres">
	SELECT st.*, a.*, ci.city, co.country
	FROM staff st
	INNER JOIN address a
	ON st.address_id = a.address_id
	INNER JOIN city ci
	ON a.city_id = ci.city_id
	INNER JOIN country co
	ON ci.country_id = co.country_id
	WHERE st.staff_id = ?
	<sql:param value="${sessionScope['id_membre']}" />
</sql:query>

<%-- Affichage des informations du membre du personnel --%>
<h2>Vos informations</h2>
<c:forEach var="infoP" items="${infoPerso.rows}" end="0">
	<div class="infoPerso">
		<c:if test="${not empty infoP.picture}">
			<img src="images/photos/staff-${infoP.staff_id}.${infoP.picture}" alt="Photo de ${infoP.first_name} ${infoP.last_name}" />
		</c:if>
		<p>${infoP.first_name} ${infoP.last_name}</p>
		<p><a href="mailto:${infoP.email}?subject=Via%20Site%20Web">${infoP.email}</a></p>
		<p>${infoP.address}</p>
		<p>${infoP.district}, ${infoP.city}, ${infoP.postal_code}, ${infoP.country}</p>
		<div style="clear:both;"></div>
	</div>
	<c:set var="store_id_perso" value="${infoP.store_id}" />
</c:forEach>

<%-- Requête sélectionnant les informations du club vidéo où travaille le membre du personnel --%>
<sql:query var="infoClub" dataSource="jdbc/sakila">
	SELECT sr.store_id, st.*, a.*, ci.city, co.country
	FROM store sr
	INNER JOIN staff st
	ON sr.manager_staff_id = st.staff_id
	INNER JOIN address a
	ON sr.address_id = a.address_id
	INNER JOIN city ci
	ON a.city_id = ci.city_id
	INNER JOIN country co
	ON ci.country_id = co.country_id
	WHERE st.store_id = ?
	<sql:param value="${store_id_perso}" />
</sql:query>

<%-- Affichage des informations sur le club vidéo --%>
<c:forEach var="infoC" items="${infoClub.rows}" end="0">
	<div class="club-video">
		<h3>Votre club vidéo (no. ${infoC.store_id})</h3>
		<div class="gerant">
			Gérant : ${infoC.first_name} ${infoC.last_name}<br />
			<a href="mailto:${infoC.email}?subject=Via%20Site%20Web">${infoC.email}</a><br />
			<c:if test="${not empty infoC.picture}">
				<img src="images/photos/staff-${infoC.staff_id}.${infoC.picture}" alt="Photo de ${infoC.first_name} ${infoC.last_name}" />
			</c:if>
		</div>
		<p>${infoC.address} </p>
		<p>${infoC.district}, ${infoC.city}, ${infoC.country}</p>
	</div>
</c:forEach>

