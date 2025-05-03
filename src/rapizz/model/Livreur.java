// File: src/rapizz/model/Livreur.java
package rapizz.model;

import java.util.Vector;

public class Livreur {
	private int id_liv;
	private String nom_L;
	private String type_Vec;
	private Vector<Commande> num_com = new Vector<>();
	private Point_Pizzaria pizzaria;

	public Livreur(int id, String nomL, String typeVec) {
		this.id_liv = id;
		this.nom_L = nomL;
		this.type_Vec = typeVec;
	}
	public Livreur(int id, String nomL, String typeVec, Point_Pizzaria pz) {
		this(id, nomL, typeVec);
		this.pizzaria = pz;
	}
	public int getId_liv() { return id_liv; }
	public void setId_liv(int id_liv) { this.id_liv = id_liv; }
	public String getNom_L() { return nom_L; }
	public void setNom_L(String nom_L) { this.nom_L = nom_L; }
	public String getType_Vec() { return type_Vec; }
	public void setType_Vec(String type_Vec) { this.type_Vec = type_Vec; }
	public Vector<Commande> getNum_com() { return num_com; }
	public void setNum_com(Vector<Commande> num_com) { this.num_com = num_com; }
	public Point_Pizzaria getPizzaria() { return pizzaria; }
	public void setPizzaria(Point_Pizzaria pizzaria) { this.pizzaria = pizzaria; }
}
