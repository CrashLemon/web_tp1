package ca.garneau.deptinfo.bieres.modeles;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import ca.garneau.deptinfo.bieres.beans.ConnexionBean;
import ca.garneau.deptinfo.bieres.classes.Biere;
import ca.garneau.deptinfo.bieres.classes.ConnexionMode;
import ca.garneau.deptinfo.bieres.classes.SHA;
import ca.garneau.deptinfo.util.ReqPrepBdUtil;

/**
 * Modèle pour la recherche de bières.
 * Permet de valider les paramètres de la requête et si ceux-ci sont valides
 * de créer la requête SQL pour la recherche de bières (la requête n'est pas exécutée ici)
 * ainsi que le bean permettant de conserver ces paramètres valides dans la session utilisateur.
 * @author Stéphane Lapointe
 */
public class ModeleConnexion {
	
	private ConnexionBean connexionBean;
	private String erreurDeConnexion = null;
	
	// Constructeur
	// ============
	/**
	 * Initialise les attributs du modèle de recherche de bières.
	 */
	public ModeleConnexion() {
		this.connexionBean = null;
	}
	
	// Getters et Setters
	// ==================
	public ConnexionBean getConnexionBean(){
		return this.connexionBean;
	}
	
	public void setConnexionBean(ConnexionBean connexionBean){
		this.connexionBean = connexionBean;
	}
	
	public String getErreurDeConnexion() {
		return this.erreurDeConnexion;
	}
	
	public void setErreurDeConnexion(String erreur) {
		this.erreurDeConnexion = erreur;
	}

	
	// Méthodes
	// ========
	
	/**
	 * Permet de rechercher les bières correspondant aux critères de recherche.
	 * @param identifiant: Identifiant.
	 * @param motPasse: Le mot de passe.
	 * @param estAdmin: Est-ce un administrateur?
	 * @throws NamingException S'il est impossible de trouver la source de données.
	 * @throws SQLException S'il y a une erreur SQL quelconque.
	 */
	public void connexion(String identifiant, String motPasse, boolean estAdmin) throws NamingException, SQLException {
		
		// ID de ladmin
		int idAdmin = 0;
		
		// Liste des paramètres pour la requête SQL.
		ArrayList<Object> params = new ArrayList<Object>();
		
		// Verification des informations de connexion.
		if (identifiant == null || identifiant.isEmpty()){
			setErreurDeConnexion("Identifiant invalide.");
			return;
		}
		else if (motPasse == null || motPasse.isEmpty()){
			setErreurDeConnexion("Mot de passe invalide.");
			return;
		}
		
		// Verification du no des employe si admin.
		if (estAdmin){
			try{
				idAdmin = Integer.parseInt(identifiant);
			}
			catch(NumberFormatException e){
				setErreurDeConnexion("L'identifiant n'est pas un chiffre.");
				return;
			}
		}
		
		// Convertissement du mot de passe en SHA-256
		try {
			motPasse = SHA.hash256(motPasse);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// Debut de la requete.
		String strReq = "SELECT * FROM membres WHERE username = ? AND password = ?";
		
		if (estAdmin){
			strReq = "SELECT * FROM employes WHERE no = ? AND password = ? ";
			params.add(idAdmin);
		}else{
			params.add(identifiant);
		}
		
		params.add(motPasse);
		
		// Source de données (JNDI).
		String nomDataSource = "jdbc/bieres";

		// Création de l'objet pour l'accès à la BD.
		ReqPrepBdUtil utilBd = new ReqPrepBdUtil(nomDataSource);

		// Obtention de la connexion à la BD.
		utilBd.ouvrirConnexion();
		
		// Préparation de la requête SQL.
		utilBd.preparerRequete(strReq, false);
		
		// Exécution de la requête tout en lui passant les paramètres pour l'exécution.
		// Note : On peut passer comme paramètres un tableau directement.
		ResultSet rs = utilBd.executerRequeteSelect(params.toArray());

		if (rs.first()){
			connexionBean = new ConnexionBean();
			
			if(estAdmin){
				connexionBean.setModeConn(ConnexionMode.ADMIN);
				connexionBean.setNom(rs.getString("prenom") + " " + rs.getString("nom"));
				connexionBean.setNoUtil(rs.getInt("no"));
			}
			else{
				connexionBean.setModeConn(ConnexionMode.MEMBRE);
				connexionBean.setNomUtil(rs.getString("username"));
			}

		}
		
		
	}
	

}
