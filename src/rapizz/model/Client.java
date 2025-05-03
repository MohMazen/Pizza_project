// ===== rapizz/model/Client.java =====
package rapizz.model;

import java.util.Vector;

public class Client {
	private String telephone;
	private String nom;
	private String adresse;
	private double solde;
	private Vector<Commande> commandes = new Vector<>();
	private Point_Pizzaria pizzaria;

	public Client(String telephone, String nom, String adresse, double solde, Point_Pizzaria pizzaria) {
		this.telephone = telephone;
		this.nom = nom;
		this.adresse = adresse;
		this.solde = solde;
		this.pizzaria = pizzaria;
	}

	public String getTelephone() { return telephone; }
	public String getNom() { return nom; }
	public String getAdresse() { return adresse; }

	public int verifierNumeroTelephone() {
		return (telephone != null && telephone.matches("\\d{10}")) ? 1 : 0;
	}

	public double getSolde() { return solde; }
	public void approvisionner(double montant) { solde += montant; }

	public int debiter(double montant) {
		if (solde >= montant) {
			solde -= montant;
			return 1;
		}
		return 0;
	}

	public void ajouterCommande(Commande c) { commandes.add(c); }
	public Vector<Commande> getCommandes() { return commandes; }


}
