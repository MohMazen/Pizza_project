// ===== rapizz/model/LigneCom.java =====
package rapizz.model;

public class Ligne_Com {
	private Commande commande;
	private Pizza pizza;
	private String taille;
	private int quantite;

	public Ligne_Com(Commande commande, Pizza pizza, String taille, int quantite) {
		this.commande = commande;
		this.pizza = pizza;
		this.taille = taille;
		this.quantite = quantite;
	}

	public Commande getCommande() { return commande; }
	public void setCommande(Commande commande) { this.commande = commande; }

	public Pizza getPizza() { return pizza; }
	public void setPizza(Pizza pizza) { this.pizza = pizza; }

	public String getTaille() { return taille; }
	public void setTaille(String taille) { this.taille = taille; }

	public int getQuantite() { return quantite; }
	public void setQuantite(int quantite) { this.quantite = quantite; }

	public void setGratuite() {
		this.quantite = 0;
		this.pizza = null;
		this.taille = null;
	}

	public int isLivree() {
		if (this.quantite == 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
