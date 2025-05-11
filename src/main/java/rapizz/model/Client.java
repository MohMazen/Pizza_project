package rapizz.model;

import java.util.Vector;

public class Client {
	private String telephone;
	private String nom;
	private String adresse;
	private double solde;
	private Vector<Commande> commandes = new Vector<>();

	// --- Constructeur ---
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

	public double getSolde() {
		return solde;
	}

	// --- méthodes ---
	public void approvisionner(double montant) {
		solde += montant;
	}

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