package rapizz;

import rapizz.model.*;
import rapizz.controller.InterfacePrincipaleController;
import rapizz.view.InterfacePrincipaleView;

public class Main {
    public static void main(String[] args) {
        // Création d'un point de vente de pizzeria
        Point_Pizzaria pz = new Point_Pizzaria("RaPizz", "10 rue de la Pizza, Paris");

        // Ajout des ingrédients disponibles dans la pizzeria
        pz.ajouterIngredient("Tomate", 100);
        pz.ajouterIngredient("Mozzarella", 100);
        pz.ajouterIngredient("Jambon", 50);
        pz.ajouterIngredient("Champignons", 50);

        // Création et ajout de la pizza Margherita
        Pizza margherita = new Pizza("Margherita", 8.0);
        margherita.addIngredient(new Ingredient("Tomate", 1));
        margherita.addIngredient(new Ingredient("Mozzarella", 1));
        pz.ajouterPizza(margherita);

        // Création et ajout de la pizza Reine
        Pizza reine = new Pizza("Reine", 10.0);
        reine.addIngredient(new Ingredient("Tomate", 1));
        reine.addIngredient(new Ingredient("Mozzarella", 1));
        reine.addIngredient(new Ingredient("Jambon", 1));
        pz.ajouterPizza(reine);

        // Création des clients et ajout à la pizzeria
        Client alice = new Client("0698775432", "Alice", "12 avenue des Champs", 50.0, pz);
        Client bob   = new Client("0698765432", "Bob",   "34 boulevard Saint-Germain", 30.0, pz);
        pz.ajouterClient(alice);
        pz.ajouterClient(bob);

        // Lancement de l'interface graphique principale
        new InterfacePrincipaleView(new InterfacePrincipaleController(pz));
    }
}