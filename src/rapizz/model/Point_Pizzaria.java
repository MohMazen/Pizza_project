// File: Point_Pizzaria.java
package rapizz.model;

import java.util.*;

/**
 * Point de production et de livraison de pizzas.
 * Gère le catalogue, le stock, les clients et les commandes.
 */
public class Point_Pizzaria {
	private String nom;
	private String adresse;
	private Vector<Client> clients        = new Vector<>();
	private Vector<Pizza> menu            = new Vector<>();
	private Vector<Ingredient> stock      = new Vector<>();
	private Vector<Commande> commandes    = new Vector<>();
	private int nextOrderId               = 1;
	private Livreur livreur;

	public Point_Pizzaria(String nom, String adresse) {
		this.nom     = nom;
		this.adresse = adresse;
	}

	public String getNom() {
		return nom;
	}

	public String getAdresse() {
		return adresse;
	}

	/** Ajoute un client à la base. */
	public void ajouterClient(Client c) {
		clients.add(c);
	}

	/** Recherche un client par son numéro de téléphone. */
	public Client findClientByPhone(String phone) {
		for (Client c : clients) {
			if (c.getTelephone().equals(phone)) {
				return c;
			}
		}
		return null;
	}

	/** Ajoute une pizza au menu. */
	public void ajouterPizza(Pizza p) {
		menu.add(p);
	}

	/** Récupère une pizza du menu par son nom (insensible à la casse). */
	public Pizza getPizzaParNom(String nomPizza) {
		for (Pizza p : menu) {
			if (p.getNom().equalsIgnoreCase(nomPizza)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Approvisionne le stock en ingrédients.
	 * Si l’ingrédient existe, on augmente la quantité.
	 */
	public void ajouterIngredient(String nom, int quantite) {
		for (Ingredient i : stock) {
			if (i.getNom().equalsIgnoreCase(nom)) {
				i.setQuantite(i.getQuantite() + quantite);
				return;
			}
		}
		stock.add(new Ingredient(nom, quantite, null, this));
	}

	/** Affiche la liste des clients (nom – téléphone). */
	public String afficherClients() {
		StringBuilder sb = new StringBuilder();
		for (Client c : clients) {
			sb.append(c.getNom())
					.append(" – ")
					.append(c.getTelephone())
					.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Calcule la trésorerie totale,
	 * c’est-à-dire la somme payée par tous les clients.
	 */
	public double getTresorerie() {
		double total = 0;
		for (Client c : clients) {
			for (Commande com : c.getCommandes()) {
				total += com.calculPrixTotal();
			}
		}
		return total;
	}

	public Vector<Pizza> getMenu() {
		return menu;
	}

	public Vector<Ingredient> getStock() {
		return stock;
	}

	public Vector<Client> getClients() {
		return clients;
	}

	/** Affiche les quantités restantes de chaque ingrédient. */
	public String afficherQuantiteIngredients() {
		StringBuilder sb = new StringBuilder();
		for (Ingredient ing : stock) {
			sb.append(ing.getNom())
					.append(" : ")
					.append(ing.getQuantite())
					.append("\n");
		}
		return sb.toString();
	}

	/** Retourne un tableau des noms de pizzas disponibles. */
	public String[] getNomsPizzas() {
		return menu.stream()
				.map(Pizza::getNom)
				.toArray(String[]::new);
	}

	/**
	 * Crée une nouvelle commande et programme la gratuité
	 * en cas de délai dépassé (30 min voiture, 15 min moto).
	 * @return l’identifiant de la commande.
	 */
	public int creerCommande(Client client, String nomPizza, String taille, int qty, String typeVehicule) {
		Pizza pizza = getPizzaParNom(nomPizza);
		double base = pizza.getPrixBase();
		double prixUnitaire;
		switch (taille.toLowerCase()) {
			case "naine":
				prixUnitaire = base - (base / 3.0);
				break;
			case "ogresse":
				prixUnitaire = base + (base / 3.0);
				break;
			case "humaine":
			default:
				prixUnitaire = base;
				break;
		}

		double totalCommande = prixUnitaire * qty;
		Date dateCommande = new Date();

		// Instanciation et enregistrement
		Commande cmd = new Commande(nextOrderId, client, pizza, taille, qty, totalCommande, dateCommande);
		commandes.add(cmd);
		client.ajouterCommande(cmd);
		final int orderId = nextOrderId++;

		// Timer pour offrir la commande si retard
		int delayMinutes = typeVehicule.equalsIgnoreCase("moto") ? 15 : 30;
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Commande c = getCommandeById(orderId);
				if (c != null && c.isLivree() == 0 && c.getQuantite() > 10) {
					c.setGratuite();
				}
			}
		}, delayMinutes * 60 * 1000L);

		return orderId;
	}

	/** Recherche une commande par son identifiant. */
	public Commande getCommandeById(int id) {
		for (Commande c : commandes) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Débite le compte du client pour une commande donnée.
	 */
	public void debiterSoldeClient(Client client, int qty, String nomPizza, String taille) {
		Pizza pizza = getPizzaParNom(nomPizza);
		double base = pizza.getPrixBase();
		double prixUnitaire;
		switch (taille.toLowerCase()) {
			case "naine":
				prixUnitaire = base - (base / 3.0);
				break;
			case "ogresse":
				prixUnitaire = base + (base / 3.0);
				break;
			case "humaine":
			default:
				prixUnitaire = base;
				break;
		}
		double total = prixUnitaire * qty;
		client.debiter(total);
	}

	/**
	 * Affiche l’état des commandes d’un client,
	 * avec le temps restant avant retard.
	 */
	public String afficherCommandesClient(Client client) {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();

		for (Commande cmd : client.getCommandes()) {
			String vehicule = cmd.getVehicule();
			int dureeLivMs = vehicule.equalsIgnoreCase("moto")
					? 15 * 60 * 1000
					: 30 * 60 * 1000;
			long elapsedMs   = now.getTime() - cmd.getDate().getTime();
			long remainingMs = dureeLivMs - elapsedMs;

			String timerStr;
			if (remainingMs > 0) {
				int m = (int)(remainingMs / 60000);
				int s = (int)((remainingMs % 60000) / 1000);
				timerStr = String.format("%02d:%02d", m, s);
			} else {
				// Retard → commande offerte
				cmd.setGratuite();
				timerStr = "00:00";
			}

			sb.append("Commande n°").append(cmd.getId())
					.append(" – Date : ").append(cmd.getDate())
					.append(" – Total : ").append(cmd.calculPrixTotal()).append(" €")
					.append(cmd.isGratuite() == 1 ? " (gratuite)" : "")
					.append(" – Véhicule : ").append(vehicule)
					.append(" – Temps restant : ").append(timerStr)
					.append("\n");
		}

		return sb.toString();
	}

	/** @return la liste des noms d’ingrédients en stock. */
	public List<String> getListeIngredients() {
		List<String> liste = new ArrayList<>();
		for (Ingredient ing : stock) {
			liste.add(ing.getNom());
		}
		return liste;
	}
}
