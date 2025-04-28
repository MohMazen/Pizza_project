
import java.util.*;

public class Client {
	public int id_Client;
	public String Nom;
	public String Adresse;
	public String telephone;
	public int sold;

	Vector<Commande> num_com = new Vector<Commande>();
	Point_Pizzaria pizzaria;
	// Constructor
	public Client(int id_Client, String Nom, String Adresse, String telephone, int sold) {
		this.id_Client = id_Client;
		this.Nom = Nom;
		this.Adresse = Adresse;
		this.telephone = telephone;
		this.sold = sold;
	}

	public Client(int id_Client, String Nom, String Adresse, String telephone, int sold, Point_Pizzaria pizzaria) {
		this.id_Client = id_Client;
		this.Nom = Nom;
		this.Adresse = Adresse;
		this.telephone = telephone;
		this.sold = sold;
		this.pizzaria = pizzaria;
	}

	// Getters
	public int getId_Client() {
		return id_Client;
	}

	public String getNom() {
		return Nom;
	}

	public String getAdresse() {
		return Adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public int getSold() {
		return sold;
	}

	public Vector<Commande> getCommandes() {
		return num_com;
	}

	public Point_Pizzaria getPizzaria() {
		return pizzaria;
	}

	// Setters
	public void setId_Client(int id_Client) {
		this.id_Client = id_Client;
	}

	public void setNom(String nom) {
		this.Nom = nom;
	}

	public void setAdresse(String adresse) {
		this.Adresse = adresse;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public void setCommandes(Vector<Commande> commandes) {
		this.num_com = commandes;
	}

	public void setPizzaria(Point_Pizzaria pizzaria) {
		this.pizzaria = pizzaria;
	}

	// methodes
	public void afficherInfos() {
		System.out.println("ID Client : " + id_Client);
		System.out.println("Nom : " + Nom);
		System.out.println("Adresse : " + Adresse);
		System.out.println("Téléphone : " + telephone);
		System.out.println("Solde : " + sold + "€");
	}

	public void ajouterCommande(Commande commande) {
		num_com.add(commande);
	}

	public int verifierSolde(int montant) {
		if (sold >= montant) {
			return 1; // solde suffisant
		} else {
			return 0; // solde insuffisant
		}
	}

	public void debiter(int montant) {
		if (verifierSolde(montant) == 1) {
			sold -= montant;
			System.out.println("Débit de " + montant + "€ effectué. Nouveau solde : " + sold + "€");
		} else {
			System.out.println("Solde insuffisant.");
		}
	}

	public void approvisionner(int montant) {
		sold += montant;
		System.out.println("Compte crédité de " + montant + "€. Nouveau solde : " + sold + "€");
	}

	public void ajouterPizzaGratuiteSiEligibilite() {
		int totalPizzas = 0;

		// Calcul du total de pizzas commandées
		for (Commande commande : num_com) {
			for (Ligne_Com ligne : commande.listLigne) {
				totalPizzas += ligne.Quantité;
			}
		}

		if (totalPizzas >= 10 && !num_com.isEmpty()) {
			Commande derniereCommande = num_com.lastElement();

			// On cherche une pizza gratuite dans le menu du point pizzaria
			for (Pizza pizza : pizzaria.Menu) {
				if (pizza.nom.equalsIgnoreCase("Pizza Gratuite")) {
					// Création d'une ligne de commande gratuite
					Ligne_Com pizzaGratuite = new Ligne_Com(derniereCommande.Num_com, 1, derniereCommande, pizza);
					derniereCommande.listLigne.add(pizzaGratuite);

					System.out.println("Une pizza gratuite a été ajoutée à votre commande !");
					return;
				}
			}

			System.out.println("Pizza gratuite introuvable dans le menu du point pizzaria.");
		} else {
			System.out.println("Moins de 10 pizzas commandées. Pas encore de pizza gratuite.");
		}
	}

}
