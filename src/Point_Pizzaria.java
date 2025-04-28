
import java.io.*;
import java.util.*;

public class Point_Pizzaria {
	public int Id_P;
	public String adresse;
	public String Nom_P;

	Vector<Client> id_client = new Vector<Client>();
	Vector<Ingredient> List_ing = new Vector<Ingredient>();
	Vector<Pizza> Menu = new Vector<Pizza>();
	Vector<Livreur> livreur = new Vector<Livreur>();

	// Constructor
	public Point_Pizzaria(int id, String nom, String adr) {
		Id_P = id;
		Nom_P = nom;
		adresse = adr;
	}
	public Point_Pizzaria(int id, String nom, String adr, Vector<Client> clients, Vector<Ingredient> ingredients,
						  Vector<Pizza> menuPizzas, Vector<Livreur> livreurs) {
		Id_P = id;
		Nom_P = nom;
		adresse = adr;
		id_client = clients;
		List_ing = ingredients;
		Menu = menuPizzas;
		livreur = livreurs;
	}

	// Getters
	public int getId_P() {
		return Id_P;
	}

	public String getNom_P() {
		return Nom_P;
	}

	public String getAdresse() {
		return adresse;
	}

	public Vector<Client> getId_client() {
		return id_client;
	}

	public Vector<Ingredient> getList_ing() {
		return List_ing;
	}

	public Vector<Pizza> getMenu() {
		return Menu;
	}

	public Vector<Livreur> getLivreur() {
		return livreur;
	}

	// Setters
	public void setId_P(int id) {
		this.Id_P = id;
	}

	public void setNom_P(String nom) {
		this.Nom_P = nom;
	}

	public void setAdresse(String adr) {
		this.adresse = adr;
	}

	public void setId_client(Vector<Client> clients) {
		this.id_client = clients;
	}

	public void setList_ing(Vector<Ingredient> ingredients) {
		this.List_ing = ingredients;
	}

	public void setMenu(Vector<Pizza> menuPizzas) {
		this.Menu = menuPizzas;
	}

	public void setLivreur(Vector<Livreur> livreurs) {
		this.livreur = livreurs;
	}

	public void addClient(Client client) {
		id_client.add(client);
	}

	// Methodes
	// Ajouter un client à la pizzaria
	public void ajouterClient(Client client) {
		id_client.add(client);
	}

	// Retourner la liste des clients
	public Vector<Client> getClients() {
		return id_client;
	}

	// Ajouter un livreur
	public void ajouterLivreur(Livreur l) {
		livreur.add(l);
	}

	// Ajouter une pizza au menu
	public void ajouterPizza(Pizza pizza) {
		Menu.add(pizza);
	}

	// Ajouter un ingrédient à la pizzaria
	public void ajouterIngredient(Ingredient i) {
		List_ing.add(i);
	}

	// Obtenir les noms de toutes les pizzas disponibles
	public Vector<String> getNomsPizzas() {
		Vector<String> noms = new Vector<>();
		for (Pizza p : Menu) {
			noms.add(p.nom);
		}
		return noms;
	}

	// Récupérer une pizza par son nom
	public Pizza getPizzaParNom(String nomPizza) {
		for (Pizza p : Menu) {
			if (p.nom.equalsIgnoreCase(nomPizza)) {
				return p;
			}
		}
		return null;
	}

	// Afficher la quantité de chaque ingrédient
// Retourne une string avec les quantités d'ingrédients
	public String afficherQuantiteIngredients() {
		StringBuilder sb = new StringBuilder("Inventaire des ingrédients :\n");
		for (Ingredient ing : List_ing) {
			sb.append("- ").append(ing.getNom()).append(" : ").append(ing.getQuantitée()).append("\n");
		}
		return sb.toString();
	}

	// ajouter une quantité d'un ingrédient
	public void ajouterQuantiteIngredient(String nom, int quantite) {
		for (Ingredient ing : List_ing) {
			if (ing.Nom.equalsIgnoreCase(nom)) {
				ing.Quantitée += quantite;
				break;
			}
		}
	}

	// Afficher tous les clients
	public String afficherClients() {
		StringBuilder sb = new StringBuilder();
		for (Client client : id_client) {
			sb.append("Nom : ").append(client.getNom())
					.append(" | Téléphone : ").append(client.getTelephone())
					.append(" | Solde : ").append(client.getSold()).append(" €\n");
		}
		return sb.toString();
	}

	// Afficher le solde total de tous les clients
	public int getSoldeTotalClients() {
		int total = 0;
		for (Client c : id_client) {
			total += c.getSold();
		}
		return total;
	}

}