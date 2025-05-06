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

    /**
	 * Initialise un client abonné à un point Pizzaria.
	 */
	public Client(String telephone, String nom, String adresse, double solde, Point_Pizzaria pizzaria) {
		this.telephone = telephone;
		this.nom = nom;
		this.adresse = adresse;
		this.solde = solde;
    }

	// --- Getters & Setters ---
	public String getTelephone() {
		return telephone;
	}

	public String getNom() {
		return nom;
	}

	/**
	 * Vérifie que le téléphone fait bien 10 chiffres.
	 * @return 1 si valide, 0 sinon.
	 */
	public int verifierNumeroTelephone() {
		return (telephone != null && telephone.matches("\\d{10}")) ? 1 : 0;
	}

	public double getSolde() {
		return solde;
	}

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

	public void ajouterCommande(Commande c) {
		commandes.add(c);
	}

	public Vector<Commande> getCommandes() {
		return commandes;
	}
}