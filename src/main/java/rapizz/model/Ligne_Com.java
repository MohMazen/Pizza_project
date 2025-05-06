// File: Ligne_Com.java
package rapizz.model;

/**
 * Représente une ligne de commande : une pizza, sa taille et la quantité.
 */
public class Ligne_Com {
	private Commande commande;
	private Pizza pizza;
	private String taille;    // "naine", "humaine" ou "ogresse"
	private int quantite;

	public Ligne_Com(Commande commande, Pizza pizza, String taille, int quantite) {
		this.commande = commande;
		this.pizza     = pizza;
		this.taille    = taille;
		this.quantite  = quantite;
	}

	// --- Getters & Setters ---
	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public String getTaille() {
		return taille;
	}

	public void setTaille(String taille) {
		this.taille = taille;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	/**
	 * Marque cette ligne comme gratuite (reste à livrer : 0 pizza).
	 */
	public void setGratuite() {
		this.quantite = 0;
		this.pizza    = null;
		this.taille   = null;
	}

	/**
	 * Indique si la ligne a été entièrement livrée (quantité à 0).
	 * @return 1 si gratuite, 0 sinon.
	 */
	public int isLivree() {
		return (this.quantite == 0) ? 1 : 0;
	}
}

