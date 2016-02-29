package ca.garneau.deptinfo.bieres.classes;

import java.util.Date;

public class Biere {
	
	/**
	 * Numéro de la bière.
	 */
	private int noBiere;
	
	/**
	 * Numéro du brasseur de la bière.
	 */
	private int noBrasseur;
	
	/**
	 * Nom de la bière.
	 */
	private String nom;
	
	/**
	 * Description de la bière.
	 */
	private String description;
	
	/**
	 * Date de modification de la bière.
	 */
	private Date dateModification;
	
	/**
	 * Taux d'alcool de la bière.
	 */
	private float tauxAlcool;
	
	/**
	 * Numéro de catégorie de la bière.
	 */
	private int noCategorie;
	
	/**
	 * Numéro de style de la bière.
	 */
	private int noStyle;
	
	/**
	 * Image de la bière
	 */
	private String image;
	
	// Constructeur
	/**
	 * Initialise la bière avec les paramètres spécifiés.
	 * @param nom Le nom de la bière.
	 * @param categorie La catégorie de la bière.
	 * @param tauxAlcool Le taux d'alcool de la bière.
	 * @param dateModification La date de modification de la bière.
	 * @param description La description de la bière.
	 * @param image L'image de la bière.
	 */
	public Biere(int no, int noBrasseur, String nom, int noCategorie, int noStyle, int tauxAlcool, Date dateModification, String description, String image) {
		this.setNoBiere(no);
		this.setNoBrasseur(noBrasseur);
		this.setNom(nom);
		this.setNoCategorie(noCategorie);
		this.setNoStyle(noStyle);
		this.setTauxAlcool(tauxAlcool);
		this.setDateModification(dateModification);
		this.setDescription(description);
		this.setImage(image);
	}

	
	public int getNoBiere() {
		return noBiere;
	}

	public void setNoBiere(int noBiere) {
		this.noBiere = noBiere;
	}
	
	public int getNoBrasseur() {
		return noBrasseur;
	}

	public void setNoBrasseur(int noBrasseur) {
		this.noBrasseur = noBrasseur;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}
	
	public int getNoStyle() {
		return noStyle;
	}

	public void setNoStyle(int noStyle) {
		this.noStyle = noStyle;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDateModification() {
		return dateModification;
	}


	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}


	public float getTauxAlcool() {
		return tauxAlcool;
	}


	public void setTauxAlcool(float tauxAlcool) {
		this.tauxAlcool = tauxAlcool;
	}


}
