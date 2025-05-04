// File: Client.java
package rapizz.model;

import java.util.Vector;

/**
 * Représente un client identifié par son téléphone,
 * avec son solde prépayé et son historique de commandes.
 */
public class Client {
	private String telephone;
	private String nom;
	private String adresse;
	private double solde;
	private Vector<Commande> commandes = new Vector<>();
	private Point_Pizzaria pizzaria;

	/**
	 * Initialise un client abonné à un point Pizzaria.
	 */
	public Client(String telephone, String nom, String adresse, double solde, Point_Pizzaria pizzaria) {
		this.telephone = telephone;
		this.nom = nom;
		this.adresse = adresse;
		this.solde = solde;
		this.pizzaria = pizzaria;
	}

	/** @return le numéro de téléphone. */
	public String getTelephone() {
		return telephone;
	}

	/** @return le nom du client. */
	public String getNom() {
		return nom;
	}

	/** @return l’adresse de livraison. */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * Vérifie que le téléphone fait bien 10 chiffres.
	 * @return 1 si valide, 0 sinon.
	 */
	public int verifierNumeroTelephone() {
		return (telephone != null && telephone.matches("\\d{10}")) ? 1 : 0;
	}

	/** @return le solde restant sur le compte. */
	public double getSolde() {
		return solde;
	}

	/** Ajoute un montant au solde. */
	public void approvisionner(double montant) {
		solde += montant;
	}

	/**
	 * Débite le compte du client.
	 * @param montant Montant à débiter.
	 * @return 1 si l’opération réussit, 0 si solde insuffisant.
	 */
	public int debiter(double montant) {
		if (solde >= montant) {
			solde -= montant;
			return 1;
		}
		return 0;
	}

	/** Ajoute une commande à l’historique. */
	public void ajouterCommande(Commande c) {
		commandes.add(c);
	}

	/** @return l’historique des commandes. */
	public Vector<Commande> getCommandes() {
		return commandes;
	}
}
