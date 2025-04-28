
import java.io.*;
import java.util.*;

public class Ingredient {
	public String Nom;
	public int Quantitée;

	Point_Pizzaria pizzaria;
	Pizza nom_pizza;

	// Constructor
	public Ingredient(String nomIng, int quantite) {
		Nom = nomIng;
		Quantitée = quantite;
	}

	public Ingredient(String nomIng, int quantite, Pizza pizza, Point_Pizzaria pz) {
		Nom = nomIng;
		Quantitée = quantite;
		nom_pizza = pizza;
		pizzaria = pz;
	}

	// Getters
	public String getNom() {
		return Nom;
	}

	public int getQuantitée() {
		return Quantitée;
	}

	public Point_Pizzaria getPizzaria() {
		return pizzaria;
	}

	public Pizza getNom_pizza() {
		return nom_pizza;
	}

	// Setters
	public void setNom(String nom) {
		this.Nom = nom;
	}

	public void setQuantitée(int quantite) {
		this.Quantitée = quantite;
	}

	public void setPizzaria(Point_Pizzaria pz) {
		this.pizzaria = pz;
	}

	public void setNom_pizza(Pizza pizza) {
		this.nom_pizza = pizza;
	}


}