// File: Commande.java
package rapizz.model;

import java.util.Vector;
import java.util.Date;

/**
 * Modélise une commande de pizza,
 * contenant plusieurs lignes et gérant les promotions.
 */
public class Commande {
	private static final int LIMITE_GRATUITE = 10;
	private int id;
	private Client client;
	private Vector<Ligne_Com> lignes = new Vector<>();
	private Date date;
	private int tempsLivraison;
	private String vehicule;

	/** Constructeur de la commande. */
	public Commande(int nextOrderId, Client client, Pizza pizza, String taille, int qty, double total, Date date) {
		this.id     = nextOrderId;
		this.client = client;
		this.date   = date;
		AjouterLigne(pizza, taille, qty);
	}

	/**
	 * Ajoute une ligne à la commande.
	 */
	public void AjouterLigne(Pizza pizza, String taille, int quantite) {
		lignes.add(new Ligne_Com(this, pizza, taille, quantite));
	}

	/** Calcule le prix total sans promotion. */
	public double calculPrixTotalRaw() {
		double total = 0;
		for (Ligne_Com lc : lignes) {
			double base = lc.getPizza().getPrixBase();
			switch (lc.getTaille().toLowerCase()) {
				case "naine":
					base *= 2.0 / 3.0;
					break;
				case "ogresse":
					base *= 4.0 / 3.0;
					break;
				default:
					break;
			}
			total += base * lc.getQuantite();
		}
		return total;
	}

	/**
	 * Calcule le prix total en appliquant une pizza gratuite
	 * si le nombre total atteint LIMITE_GRATUITE.
	 */
	public double calculPrixTotal() {
		double total = calculPrixTotalRaw();
		int totalPizzas = 0;
		for (Ligne_Com lc : lignes) {
			totalPizzas += lc.getQuantite();
		}
		if (totalPizzas >= LIMITE_GRATUITE) {
			// Offre : la première ligne est gratuite
			total -= lignes.get(0).getPizza().getPrixBase();
		}
		return Math.max(0, total);
	}

	/**
	 * Valide la commande : débite le client et l’ajoute à son historique.
	 * @return 1 si validée, 0 si solde insuffisant.
	 */
	public int valider(Point_Pizzaria pizzaria) {
		double montant = calculPrixTotal();
		if (client.debiter(montant) == 1) {
			client.ajouterCommande(this);
			return 1;
		}
		return 0;
	}

	// --- Getters & Setters ---
	public void setTempsLivraison(int minutes) {
		this.tempsLivraison = minutes;
	}

	public int getTempsLivraison() {
		return tempsLivraison;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	/** Indique si la commande est gratuite selon LIMITE_GRATUITE. */
	public int isGratuite() {
		int totalPizzas = 0;
		for (Ligne_Com lc : lignes) {
			totalPizzas += lc.getQuantite();
		}
		return (totalPizzas >= LIMITE_GRATUITE) ? 1 : 0;
	}

	/**
	 * Indique si la commande a déjà été livrée
	 * (toutes les lignes marquées gratuites).
	 */
	public int isLivree() {
		for (Ligne_Com lc : lignes) {
			if (lc.isLivree() == 1) {
				return 1;
			}
		}
		return 0;
	}

	/** @return la quantité totale de pizzas commandées. */
	public int getQuantite() {
		int total = 0;
		for (Ligne_Com lc : lignes) {
			total += lc.getQuantite();
		}
		return total;
	}

	/** Marque toutes les lignes comme gratuites. */
	public void setGratuite() {
		for (Ligne_Com lc : lignes) {
			lc.setGratuite();
		}
	}

	/** @return le moyen de livraison ("moto" ou "voiture"). */
	public String getVehicule() {
		return vehicule;
	}
}
