
import java.io.*;
import java.util.*;

public class Ligne_Com {
	public int Id_com;
	public int Quantité;

	Commande num_com;
	Pizza nom_pizza;

	// Constructor
	public Ligne_Com(int id, int qte) {
		Id_com = id;
		Quantité = qte;
	}

	public Ligne_Com(int id, int qte, Commande com, Pizza pizza) {
		Id_com = id;
		Quantité = qte;
		num_com = com;
		nom_pizza = pizza;
	}

	// Getters
	public int getId_com() {
		return Id_com;
	}

	public int getQuantité() {
		return Quantité;
	}

	public Commande getNum_com() {
		return num_com;
	}

	public Pizza getNom_pizza() {
		return nom_pizza;
	}

	// Setters
	public void setId_com(int id_com) {
		this.Id_com = id_com;
	}

	public void setQuantité(int qte) {
		this.Quantité = qte;
	}

	public void setNum_com(Commande com) {
		this.num_com = com;
	}

	public void setNom_pizza(Pizza pizza) {
		this.nom_pizza = pizza;
	}


}