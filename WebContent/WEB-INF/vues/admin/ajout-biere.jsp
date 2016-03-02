<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<form method="get" action="${pageContext.request.contextPath}/ajout-biere">
			<p>
			<sql:query var="listebrasseurs" dataSource="jdbc/bieres">
				SELECT id, nom
				FROM brasseurs
			</sql:query>

			<label for="brasseurs">Brasseurs : </label>

			<select name="brasseurs" id="brasseurs" style="width:100px;">
				<c:forEach var="brasseur" items="${listebrasseurs.rows}">
							<option value="${brasseur.id}">${brasseur.nom}</option>
				</c:forEach>
			</select>
		
		</p>
		
		<p>
			<label for="NomBiere">NomBiere : </label>
			<input type="text" name="NomBiere" id="NomBiere" />
		</p>
		<p>
			<%-- Requête pour les catégories --%>
			<sql:query var="listecategories" dataSource="jdbc/bieres">
				SELECT id, nom 
				FROM categories
			</sql:query>

			<label for="categorie"> : </label>

			<select name="categorie" id="categorie">
				<c:forEach var="categorie" items="${listecategories.rows}">
					<option value="${categorie.id}">${categorie.nom}</option>
				</c:forEach>
			</select>
			
		
		</p>
		
		<p>
			<sql:query var="listestyles" dataSource="jdbc/bieres">
				SELECT id, style_name
				FROM styles
			</sql:query>

			<label for="styles">Styles : </label>

			<select name="styles" id="styles">
				<c:forEach var="style" items="${listestyles.rows}">
					<option value="${style.id}">${style.style_name}</option>
				</c:forEach>
			</select>
		
		</p>
		
		<p>
			<label for="descr">Description : </label>
			<input type="text" name="descr" id="descr" />
		</p>
		
		<p>
			<input type="submit" name="ajout-bieres" value="Ajouter bière" class="btn" />
		</p>
	</form>