import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Commande {
	public int Num_com;
	public Date Date_com;

	// Temps de livraison en minutes, calculé selon le livreur
	private int tempsLivraison;
	// Surcharge éventuelle (pour livraison rapide)
	private double surcharge;

	Vector<Ligne_Com> listLigne = new Vector<>();
	Vector<Client> id_client   = new Vector<>();
	Livreur livreur;

	// Constructeur de base
	public Commande(int num, Date date) {
		Num_com         = num;
		Date_com        = date;
		tempsLivraison  = 30;    // véhicule non affecté → valeur par défaut
		surcharge       = 0.0;
	}

	// Constructeur complet
	public Commande(int num, Date date, Vector<Ligne_Com> lignes, Livreur liv) {
		this.Num_com   = num;
		this.Date_com  = date;
		this.listLigne = lignes;
		this.surcharge = 0.0;
		setLivreur(liv);
	}

	// Affecte un livreur et met à jour le temps de livraison
	public void setLivreur(Livreur l) {
		this.livreur = l;
		if (l != null && "moto".equalsIgnoreCase(l.getType_Vec())) {
			this.tempsLivraison = 15;
		} else {
			this.tempsLivraison = 30;
		}
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public int getTempsLivraison() {
		return tempsLivraison;
	}

	public double getSurcharge() {
		return surcharge;
	}

	public int getNumCom() {
		return Num_com;
	}

	public void setSurcharge(double s) {
		this.surcharge = s;
	}

	// Ajouter une ligne de commande
	public void ajouterLigneCommande(Ligne_Com ligne) {
		listLigne.add(ligne);
	}

	// Ajouter un client à la commande
	public void ajouterClient(Client client) {
		id_client.add(client);
	}

	// Calcul du prix total : tailles + surcharge
	public double calculPrixTotal() {
		double total = 0.0;
		for (Ligne_Com ligne : listLigne) {
			Pizza pizza = ligne.getNom_pizza();
			double prix = pizza.getPrixBase();
			switch (pizza.getTaille().toLowerCase()) {
				case "naine":
					prix *= 2.0 / 3.0;
					break;
				case "ogresse":
					prix *= 4.0 / 3.0;
					break;
				default:
					break;
			}
			total += prix * ligne.getQuantité();
		}
		return total + surcharge;
	}

	// Vérifie que le client a assez de solde : renvoie 1 si suffisant, 0 sinon
	public int verifierSoldeClient() {
		if (id_client.isEmpty()) {
			return 0;
		}
		Client client = id_client.get(0);
		return client.getSold() >= calculPrixTotal() ? 1 : 0;
	}

	// Valide la commande : débite le solde ou affiche un message d'erreur
	public void validerCommande() {
		Client client = id_client.isEmpty() ? null : id_client.get(0);
		if (client == null) {
			System.out.println("Aucun client associé à la commande.");
			return;
		}
		if (verifierSoldeClient() == 1) {
			int montant = (int) Math.round(calculPrixTotal());
			client.debiter(montant);
			System.out.println("Commande validée et débit de " + montant + " € effectué.");
		} else {
			System.out.println("Solde insuffisant. Commande annulée.");
		}
	}

	// Affiche les détails complets de la commande
	public String afficherDetailsCommande() {
		StringBuilder sb = new StringBuilder();
		sb.append("Commande N°").append(Num_com).append("\n");
		if (livreur != null) {
			sb.append("Livreur : ").append(livreur.getNom_L())
					.append(" (véhicule ").append(livreur.getType_Vec())
					.append(", ").append(tempsLivraison).append(" min)").append("\n");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sb.append("Date : ").append(sdf.format(Date_com)).append("\n");

		for (Ligne_Com ligne : listLigne) {
			Pizza pizza = ligne.getNom_pizza();
			sb.append("- ").append(ligne.getQuantité())
					.append(" x ").append(pizza.getNom())
					.append(" (").append(pizza.getTaille()).append(")\n");
		}

		// Calcul du temps restant ou retard
		Date now = new Date();
		long diffMs = now.getTime() - Date_com.getTime();
		long minutesPass = diffMs / 60000;
		long secondsPass = (diffMs / 1000) % 60;
		long minutesRest = tempsLivraison - minutesPass;
		long secondsRest = 60 - secondsPass;

		if (minutesRest > 0 || secondsRest > 0) {
			sb.append("Temps restant : ")
					.append(minutesRest).append(" min ")
					.append(secondsRest).append(" s\n");
		} else {
			sb.append("Retard de ")
					.append(Math.abs(minutesRest)).append(" min ")
					.append(Math.abs(secondsRest)).append(" s\n");
		}

		sb.append("Total : ")
				.append(String.format("%.2f", calculPrixTotal()))
				.append(" €\n");

		return sb.toString();
	}
}