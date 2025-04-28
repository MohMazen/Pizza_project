
import java.io.*;
import java.util.*;

public class Livreur {
	public int id_liv;
	public String Nom_L;
	public String Type_Vec;

	Vector<Commande> num_com = new Vector<Commande>();
	Point_Pizzaria pizzaria;

	// Constructor
	public Livreur(int id, String nomL, String typeVec) {
		id_liv = id;
		Nom_L = nomL;
		Type_Vec = typeVec;
	}

	public Livreur(int id, String nomL, String typeVec, Point_Pizzaria pz) {
		id_liv = id;
		Nom_L = nomL;
		Type_Vec = typeVec;
		pizzaria = pz;
	}

	// Getters
	public int getId_liv() {
		return id_liv;
	}

	public String getNom_L() {
		return Nom_L;
	}

	public String getType_Vec() {
		return Type_Vec;
	}

	public Vector<Commande> getNum_com() {
		return num_com;
	}

	public Point_Pizzaria getPizzaria() {
		return pizzaria;
	}

	// Setters
	public void setId_liv(int id_liv) {
		this.id_liv = id_liv;
	}

	public void setNom_L(String nom_L) {
		this.Nom_L = nom_L;
	}

	public void setType_Vec(String type_Vec) {
		this.Type_Vec = type_Vec;
	}

	public void setNum_com(Vector<Commande> num_com) {
		this.num_com = num_com;
	}

	public void setPizzaria(Point_Pizzaria pz) {
		this.pizzaria = pz;
	}
}