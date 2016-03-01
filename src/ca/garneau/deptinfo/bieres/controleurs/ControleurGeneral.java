package ca.garneau.deptinfo.bieres.controleurs;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.garneau.deptinfo.bieres.beans.ConnexionBean;
import ca.garneau.deptinfo.bieres.classes.Biere;
import ca.garneau.deptinfo.bieres.classes.ConnexionMode;
import ca.garneau.deptinfo.bieres.modeles.ModeleConnexion;
import ca.garneau.deptinfo.bieres.modeles.ModeleRechBieres;
import ca.garneau.deptinfo.util.ReqPrepBdUtil;



/**
 * Contrôleur général pour les ressources publiques.
 * @author Stéphane Lapointe
 * @author VOS NOMS COMPLETS ICI
 */
public class ControleurGeneral extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	// Attributs
	// =========
	/**
	 * URI sans le context path.
	 */
	protected String uri;
	
	/**
	 * Vue à afficher (chemin du fichier sur le serveur).
	 */
	protected String vue;
	
	/**
	 * Fragment de la vue (chemin du fichier sur le serveur) à charger
	 * dans la zone de contenu si la vue est créée à partir du gabarit.
	 */
	protected String vueContenu;
	
	/**
	 * Sous-titre de la vue si la vue est créée à partir du gabarit.
	 */
	protected String vueSousTitre;
	
	/**
	 * Permet d'effectuer les traitements avant de gérer la ressource demandée.
	 * @param request La requête HTTP.
	 * @param response La réponse HTTP.
	 */
	protected void preTraitement(HttpServletRequest request, HttpServletResponse response) {
		// Récupération de l'URI sans le context path.
		this.uri = request.getRequestURI().substring(request.getContextPath().length());
		this.vue = null;
		this.vueContenu = null;
		this.vueSousTitre = null;		
	}



	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Opérations pré-traitement.
		preTraitement(request, response);

		// ================================
		// Gestion de la ressource demandée
		// ================================

		// Accueil
		// Recherche de bières.
		if (this.uri.equals("/rech-bieres") || this.uri.equals("/") || this.uri.equals("")) {
			
			// Création du modèle pour rechercher des bières.
			ModeleRechBieres mrf = new ModeleRechBieres();
			
			// Est-ce que le formulaire de recherche de bières a été soumis ?
			if (request.getParameter("rech-bieres") != null)
			{
				// Appel de la méthode du modèle qui recherche des bières.
				// Les informations produites (i.e., la liste des bières trouvés)
				// sont conservées dans un attribut de la requête.
				try {
					ArrayList<String> paramRecherche = mrf.rechercherBieres(
							request.getParameter("mot-cle"),
							request.getParameter("taux-minimum"),
							request.getParameter("taux-maximum"),
							request.getParameter("categorie")
							);
					
					if (paramRecherche != null){
						request.getSession().setAttribute("mot-cle", paramRecherche.get(0));						request.getSession().setAttribute("mot-cle", paramRecherche.get(0));
						request.getSession().setAttribute("taux-minimum", paramRecherche.get(1));
						request.getSession().setAttribute("taux-maximum", paramRecherche.get(2));
						request.getSession().setAttribute("categorie", paramRecherche.get(3));
					}
					
				} catch (NamingException | SQLException e) {
					throw new ServletException(e);
				}
				
			}
			
			// Ajout du modèle dans les attributs de la requête.
			request.setAttribute("modRechBieres", mrf);


			// Appel du modèle pour la recherche de bières
			// si le formulaire a été soumis.
			vue = "/WEB-INF/vues/gabarit-vues.jsp";
			vueContenu = "/WEB-INF/vues/general/rech-bieres.jsp";
			vueSousTitre = "Rechercher des bière";

		// Détails d'une bière.
		} else if (this.uri.equals("/details-biere")) {
			
			// Verification que cest bien un numero de biere.
			try{
				int noBiere = Integer.parseInt(request.getParameter("noBiere"));

				// Début de la requête (sans les conditions)
				String reqSQLRechBieres = "SELECT id, brasseur_id, nom, cat_id, style_id, abv, description, image, derniere_modification FROM bieres " +
										"WHERE id = " + noBiere;


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
				ResultSet rs = utilBd.executerRequeteSelect();

				// Objet pour conserver une bière trouvée.
				Biere biere;
				// Parcours des bières trouvées.

				if (rs.first()){
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
					
					request.setAttribute("biere", biere);
				}
			}
			catch(NumberFormatException | NamingException | SQLException e){
				throw new ServletException(e);
			}
			
			vue = "/WEB-INF/vues/gabarit-vues.jsp";
			vueContenu = "/WEB-INF/vues/general/biere.jsp";
			vueSousTitre = "Détails d'une bière";

		// Affichage des brasseurs de bière.
		} else if (this.uri.equals("/brasseurs")) {
			vue = "/WEB-INF/vues/gabarit-vues.jsp";
			vueContenu = "/WEB-INF/vues/general/brasseurs.jsp";
			vueSousTitre = "Les brasseurs de bière";


		// Méthode HTTP non permise
		// ========================
		} else if (this.uri.equals("/connexion")) {
			
		// Ressource non disponible
		// ========================
		} else if (this.uri.equals("/deconnexion")) {
			request.getSession().setAttribute("modConnexion", null);
			response.sendRedirect(request.getHeader("rech-bieres"));
		} else {
			// On retourne immédiatement le code d'erreur HTTP 404;
			// la réponse sera interceptée par la page d'erreur "erreur-404.jsp".
			response.sendError(404);
			
		}  // Fin du branchement en fonction de la ressource demandée.
		
		// Opérations post-traitement.
		postTraitement(request, response);

	}

	/**
	 * Permet de gérer les ressources POST suivantes :
	 * 		"/connexion"	:	Connexion
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Opérations pré-traitement.
		preTraitement(request, response);

		// ================================
		// Gestion de la ressource demandée
		// ================================

		// Connexion
		// =========
		if (this.uri.equals("/connexion")) {

			// Redirection côté client vers la section pour les membres.
			// Note : Aucune vue ne sera produite comme réponse à cette requête;
			// La requête subséquente vers la section "membre" (faite par le navigateur Web)
			// produira la vue correspondant à la page d'accueil des membres.
			
			ModeleConnexion mc = new ModeleConnexion();
			
			
			
			if (request.getParameter("identifiant") != null){
				// Verification et validation des informations.
				try {
					mc.connexion(
						request.getParameter("identifiant"),
						request.getParameter("motPasse"),
						(request.getParameter("typeConn") != null));
				}
				catch (NamingException | SQLException e){
					throw new ServletException(e);
				}
				
			}

			// On set le modele de connexion.
			request.getSession().setAttribute("modConnexion", mc);
			
			// On redirect dependement du resultat.
			if (mc.getConnexionBean() != null){
				if (mc.getConnexionBean().getModeConn() == ConnexionMode.ADMIN){
					response.sendRedirect("admin");
				}
				else if (mc.getConnexionBean().getModeConn() == ConnexionMode.MEMBRE){
					response.sendRedirect("membre");
				}
			}
			else
				response.sendRedirect(request.getHeader("referer"));

		// Méthode HTTP non permise
		// ========================
		} else if (uri.equals("/") || uri.equals("") || uri.equals("/rech-bieres") ) {
			// On retourne immédiatement le code d'erreur HTTP 405;
			// la réponse sera interceptée par la page d'erreur "erreur-405.jsp".
			response.sendError(405);

		// Ressource non disponible
		// ========================
		} else {
			// On retourne immédiatement le code d'erreur HTTP 404;
			// la réponse sera interceptée par la page d'erreur "erreur-404.jsp".
			response.sendError(404);

		} // Fin du branchement en fonction de la ressource demandée.

		// Opérations post-traitement.
		postTraitement(request, response);
	}


	/**
	 * Permet d'effectuer les traitements suite à la gestion de la ressource demandée.
	 * @param request La requête HTTP.
	 * @param response La réponse HTTP.
	 */
	protected void postTraitement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Doit-on transférer le contrôle vers une vue ?
		if (this.vue != null) {
			// Doit-on conserver les informations pour la production d'une vue à partir du gabarit ?
			if (this.vueContenu != null || this.vueSousTitre != null) {
				// On conserve le chemin du fichier du fragment de la vue ainsi que le
				// sous-titre de la vue dans les attributs de la requête;
				// ces informations serviront à générer la vue à partir du gabarit.
				request.setAttribute("vueContenu", this.vueContenu);
				request.setAttribute("vueSousTitre", this.vueSousTitre);
			}
			// Transfert du contrôle de l'exécution à la vue.
			request.getRequestDispatcher(this.vue).forward(request, response);
		}
	}

}
