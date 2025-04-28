
import java.io.*;
import java.util.*;

public class Pizza {
	public String nom;
	public String Taille;
	public double prixBase;

	Vector<Ligne_Com> listLigne = new Vector<Ligne_Com>();
	Vector<Ingredient> List_ing = new Vector<Ingredient>();
	Point_Pizzaria pizzaria;

	// Constructor
	public Pizza(String nomPizza, String taille, double prix) {
		nom = nomPizza;
		Taille = taille;
		prixBase = prix;
	}

	public Pizza(String nomPizza, String taille, double prix, Point_Pizzaria pz) {
		nom = nomPizza;
		Taille = taille;
		prixBase = prix;
		pizzaria = pizzaria;
	}

	// Getters
	public String getNom() {
		return nom;
	}

	public String getTaille() {
		return Taille;
	}

	public double getPrixBase() {
		return prixBase;
	}

	public Vector<Ligne_Com> getListLigne() {
		return listLigne;
	}

	public Vector<Ingredient> getListIng() {
		return List_ing;
	}

	public Point_Pizzaria getPizzaria() {
		return pizzaria;
	}

	// Setters
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setTaille(String taille) {
		this.Taille = taille;
	}

	public void setPrixBase(double prix) {
		this.prixBase = prix;
	}

	public void setListLigne(Vector<Ligne_Com> listLigne) {
		this.listLigne = listLigne;
	}

	public void setListIng(Vector<Ingredient> listIng) {
		this.List_ing = listIng;
	}

	public void setPizzaria(Point_Pizzaria pz) {
		this.pizzaria = pz;
	}

}