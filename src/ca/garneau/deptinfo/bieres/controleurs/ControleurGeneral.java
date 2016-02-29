package ca.garneau.deptinfo.bieres.controleurs;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.garneau.deptinfo.bieres.classes.ConnexionMode;
import ca.garneau.deptinfo.bieres.modeles.ModeleRechBieres;


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
					mrf.rechercherBieres(
							request.getParameter("mot-cle"),
							request.getParameter("taux-minimum"),
							request.getParameter("taux-maximum"),
							request.getParameter("categorie")
							);
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
			
			// On retourne immédiatement le code d'erreur HTTP 405;
			// la réponse sera interceptée par la page d'erreur "erreur-405.jsp".
			response.sendError(405);
			
		// Ressource non disponible
		// ========================
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

			// TEMPORAIRE POUR SIMULER LE FAIT D'ÊTRE CONNECTÉ
			// ***********************************************
			request.getSession().setAttribute("id_membre", 5);
			request.getSession().setAttribute("nomUtil", "luna");
			request.getSession().setAttribute("nom", "Luna");
			request.getSession().setAttribute("modeConn", ConnexionMode.MEMBRE);
			// NOTE: DEVRA ÊTRE IMPLEMENTÉ À L'AIDE DU BEAN DE CONNEXION
			// *********************************************************

			// Redirection côté client vers la section pour les membres.
			// Note : Aucune vue ne sera produite comme réponse à cette requête;
			// La requête subséquente vers la section "membre" (faite par le navigateur Web)
			// produira la vue correspondant à la page d'accueil des membres.
			response.sendRedirect("membre");

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
