<%@page pageEncoding="UTF-8"%>
<!-- 
<img src="${pageContext.request.contextPath}/images/logo-biere.png" alt="Logo Les bières du monde" />
-->
<div id="section-connexion">

	<%-- Formulaire de connexion --%>
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
	</form>

</div>

<h1>Les bières du monde</h1>
