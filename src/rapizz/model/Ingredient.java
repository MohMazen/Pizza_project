// ===== rapizz/model/Ingredient.java =====
package rapizz.model;

public class Ingredient {
	private String nom;
	private int quantite;
	private Pizza pizza;
	private Point_Pizzaria pizzaria;

	public Ingredient(String nom, int quantite) {
		this.nom = nom;
		this.quantite = quantite;
	}

	public Ingredient(String nom, int quantite, Pizza pizza, Point_Pizzaria pizzaria) {
		this.nom = nom;
		this.quantite = quantite;
		this.pizza = pizza;
		this.pizzaria = pizzaria;
	}

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public int getQuantite() { return quantite; }
	public void setQuantite(int quantite) { this.quantite = quantite; }

	public Pizza getPizza() { return pizza; }
	public void setPizza(Pizza pizza) { this.pizza = pizza; }

	public Point_Pizzaria getPizzaria() { return pizzaria; }
	public void setPizzaria(Point_Pizzaria pizzaria) { this.pizzaria = pizzaria; }
}