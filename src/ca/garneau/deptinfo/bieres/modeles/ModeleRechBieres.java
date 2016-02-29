package ca.garneau.deptinfo.bieres.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import ca.garneau.deptinfo.bieres.classes.Biere;
import ca.garneau.deptinfo.util.ReqPrepBdUtil;

/**
 * Modèle pour la recherche de bières.
 * Permet de valider les paramètres de la requête et si ceux-ci sont valides
 * de créer la requête SQL pour la recherche de bières (la requête n'est pas exécutée ici)
 * ainsi que le bean permettant de conserver ces paramètres valides dans la session utilisateur.
 * @author Stéphane Lapointe
 */
public class ModeleRechBieres {
	

	// Attributs
	// =========
	
	// Erreur affiche lorsqu'il la requete n'est pas valide.
	private String erreurDeRecherche;
			
	/**
	 * Liste des bière trouvées.
	 */
	private ArrayList<Biere> lstBieresTrouvees;

	
	// Constructeur
	// ============
	/**
	 * Initialise les attributs du modèle de recherche de bières.
	 */
	public ModeleRechBieres() {
		this.lstBieresTrouvees = null;
	}

	
	// Getters et Setters
	// ==================

	/**
	 * Retourne la liste des bières trouvées.
	 * @return La liste des bières trouvées.
	 */
	public ArrayList<Biere> getLstBieresTrouvees() {
		return this.lstBieresTrouvees;
	}

	/**
	 * Modifie la liste des bières trouvées.
	 * @param lstBieresTrouvees La nouvelle liste des bières trouvées.
	 */
	public void setLstBieresTrouvees(ArrayList<Biere> lstBieresTrouvees) {
		this.lstBieresTrouvees = lstBieresTrouvees;
	}
	
	public String getErreurDeRecherche() {
		return this.erreurDeRecherche;
	}
	
	public void setErreurDeRecherche(String erreur) {
		this.erreurDeRecherche = erreur;
	}
	
	
	// Méthodes
	// ========
	
	/**
	 * Permet de rechercher les bières correspondant aux critères de recherche.
	 * @param motCle : mot clé.
	 * @param tauxMinimum : le taux minimum d'alcohol.
	 * @param tauxMaximum : le taux maximum d'alcohol.
	 * @param categorie : catégorie de la bière.
	 * @throws NamingException S'il est impossible de trouver la source de données.
	 * @throws SQLException S'il y a une erreur SQL quelconque.
	 */
	public void rechercherBieres(String motCle, String strTauxMin, String strTauxMax, String categorie) throws NamingException, SQLException {

		// Traitement des paramètres
		// =========================
		
		// Mot clé
		// ============
		// Est-ce que le mot clé doit être utilisé pour les critères de recherche ?
		if (motCle != null) {
			// On double les apostrophes pour la requête SQL.
			motCle = motCle.replaceAll("[']", "''");
			
		}

		// Catégorie
		// ============
		// Est-ce que la catégorie doit être utilisée pour les critères de recherche ?
		if (!categorie.equals("Toutes")) {
			// On double les apostrophes pour la requête SQL.
			categorie = categorie.replaceAll("[']", "''");
		}
		
		// Test
		
		// Y a-t-il au moins un critère de recherche qui est spécifié ?
		// Si oui, il faut effectuer la recherche des bières.
		if (motCle != null || strTauxMin != null || strTauxMax != null || !categorie.equals("Toutes")) {

			// Création de la requête SQL
			// ==========================
			
			// Liste de conditions pour la requête SQL.
			ArrayList<String> conditions = new ArrayList<String>();
			
			// Liste des paramètres pour la requête SQL.
			ArrayList<Object> params = new ArrayList<Object>();
			
			// Taux
			float f_tauxMin;
			float f_tauxMax;

			// Est-ce que le mot clé doit être utilisé pour les critères de recherche ?
			if (motCle != null) {
				// Création et ajout de la condition à la liste des conditions.
				conditions.add("( nom LIKE ? OR description LIKE ? )");
				// Ajout du paramètre à la liste des paramètres.
				params.add("%" + motCle + "%");
				// On ajoute le paramètre une 2ième fois car il est utilisé 2 fois dans la requête: pour le titre et pour la description
				params.add("%" + motCle + "%");
			}
			
			// Verification du taux minimum.
			if ( strTauxMin != null && !strTauxMin.isEmpty()){
				
				try{
					f_tauxMin = Float.parseFloat(strTauxMin);
				}
				catch (NumberFormatException nfe){
					setErreurDeRecherche("Taux minimum doit etre un nombre");
				}
			}
			
			// Verification du taux minimum.
			if ( strTauxMin != null && !strTauxMin.isEmpty()){
				try{
					f_tauxMin = Float.parseFloat(strTauxMin);
				}
				catch (NumberFormatException nfe){
					setErreurDeRecherche("Taux maximum doit etre un nombre");
				}
			}
			
			// TODO CHECK POUR LES < 0 ET PLuS GRAND QUE TAUX MAX ETC.....
			
			// Est-ce que la cotation doit être utilisée pour les critères de recherche ?
			if (!categorie.equals("Toutes")) {
				// Création et ajout de la condition à la liste des conditions.
				conditions.add("cat_id = ?");
				// Ajout du paramètre à la liste des paramètres.
				params.add(categorie);
			}

			
			// Début de la requête (sans les conditions)
			String reqSQLRechBieres = "SELECT id, brasseur_id, nom, cat_id, style_id, abv, description, image, derniere_modification FROM bieres";

			// Ajout des conditions à la requête s'il y en a.
			if (conditions.size() > 0) {
				// Ajout de la première condition.
				reqSQLRechBieres += " WHERE " + conditions.get(0);
				// Ajout des autres conditions.
				for (int i = 1; i < conditions.size(); i++) {
					reqSQLRechBieres += " AND " + conditions.get(i);
				}
			}

			
			// Ajout du critère de tri (en ordre croissant de nom).
			reqSQLRechBieres += " ORDER BY nom";

			// Exécution de la requête SQL
			// ===========================

			// Source de données (JNDI).
			String nomDataSource = "jdbc/bieres";

			// Création de l'objet pour l'accès à la BD.
			ReqPrepBdUtil utilBd = new ReqPrepBdUtil(nomDataSource);

			// Obtention de la connexion à la BD.
			utilBd.ouvrirConnexion();
			
			// Préparation de la requête SQL.
			utilBd.preparerRequete(reqSQLRechBieres, false);
			
			// Exécution de la requête tout en lui passant les paramètres pour l'exécution.
			// Note : On peut passer comme paramètres un tableau directement.
			ResultSet rs = utilBd.executerRequeteSelect(params.toArray());
			
			// Conservation des films trouvés dans une liste.
			this.lstBieresTrouvees = new ArrayList<Biere>();
			// Objet pour conserver une bière trouvée.
			Biere biere;
			// Parcours des bières trouvées.
			while (rs.next()) {
				// Création de l'objet "Biere".
				biere = new Biere(
						rs.getInt("id"),
						rs.getInt("brasseur_id"),
						rs.getString("nom"),
						rs.getInt("cat_id"),
						rs.getInt("style_id"),
						rs.getInt("abv"),
						rs.getDate("derniere_modification"),
						rs.getString("description"),
						rs.getString("image"));
				
				// Ajout de la bière dans la liste.
				this.lstBieresTrouvees.add(biere);
			}

			// Fermeture de la connexion à la BD.
			utilBd.fermerConnexion();
		}
		
	}
	

}
