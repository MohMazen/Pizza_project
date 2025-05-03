package rapizz.model;

import java.util.Vector;

public class Pizza {
	private String nom;
	private double prixBase;
	private Vector<Ingredient> ingredients = new Vector<>();

	public Pizza(String nom, double prixBase) {
		this.nom = nom;
		this.prixBase = prixBase;
	}

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public double getPrixBase() { return prixBase; }
	public void setPrixBase(double prixBase) { this.prixBase = prixBase; }

	public Vector<Ingredient> getIngredients() { return ingredients; }
	public void addIngredient(Ingredient ing) { ingredients.add(ing); }
}