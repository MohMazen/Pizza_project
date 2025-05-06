// File: Commande.java
package rapizz.model;

import java.util.Vector;
import java.util.Date;

/**
 * Modélise une commande de pizza,
 * contenant plusieurs lignes et gérant les promotions.
 */
public class Commande {
	public static final int LIMITE_GRATUITE = 10;
	private int id;
	private Client client;
	private Vector<Ligne_Com> lignes = new Vector<>();
	private Date date;
	private int tempsLivraison;    // en minutes
	private String vehicule;

	/**
	 * Initialise une commande unique (avant ajout de lignes).
	 */
	public Commande(int nextOrderId, Client client, Pizza pizza, String taille, int qty, Date date) {
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

	// --- Calculs de prix ---
	public double calculPrixTotalRaw() {
		double total = 0;
		for (Ligne_Com lc : lignes) {
			double base = lc.getPizza().getPrixBase();
			switch (lc.getTaille().toLowerCase()) {
				case "naine": base *= 2.0/3.0; break;
				case "ogresse": base *= 4.0/3.0; break;
				default: break;
			}
			total += base * lc.getQuantite();
		}
		return total;
	}

	public double calculPrixTotal() {
		double total = calculPrixTotalRaw();
		int totalPizzas = getQuantite();
		if (totalPizzas >= LIMITE_GRATUITE) {
			// Offre : la pizza la moins chère gratuite (ici la première)
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
	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getTempsLivraison() {
		return tempsLivraison;
	}

	public void setTempsLivraison(int minutes) {
		this.tempsLivraison = minutes;
	}

	public String getVehicule() {
		return vehicule;
	}

	public void setVehicule(String vehicule) {
		this.vehicule = vehicule;
	}

	public int isGratuite() {
		return (getQuantite() >= LIMITE_GRATUITE) ? 1 : 0;
	}

	/**
	 * Indique si toutes les lignes ont été livrées.
	 */
	public int isLivree() {
		for (Ligne_Com lc : lignes) {
			if (lc.isLivree() == 0) {
				return 0;
			}
		}
		return 1;
	}

	public int getQuantite() {
		int total = 0;
		for (Ligne_Com lc : lignes) {
			total += lc.getQuantite();
		}
		return total;
	}

	/**
	 * Marque toutes les lignes comme gratuites.
	 */
	public void setGratuite() {
		for (Ligne_Com lc : lignes) {
			lc.setGratuite();
		}
	}
}