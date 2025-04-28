import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Création du point RaPizz
        Point_Pizzaria pizzaria = new Point_Pizzaria(1, "RaPizz", "123 Rue de la Pizza");

        // Clients
        Client client1 = new Client(1, "Ali", "123 Rue", "0612345678", 100, pizzaria);
        Client client2 = new Client(2, "Sofia", "456 Avenue", "0623456789", 150, pizzaria);
        pizzaria.ajouterClient(client1);
        pizzaria.ajouterClient(client2);

        // Pizzas
        Pizza p1 = new Pizza("Margherita", "humaine", 10.0, pizzaria);
        Pizza p2 = new Pizza("Reine", "humaine", 12.0, pizzaria);
        Pizza gratuite = new Pizza("Pizza Gratuite", "humaine", 0.0, pizzaria);
        pizzaria.ajouterPizza(p1);
        pizzaria.ajouterPizza(p2);
        pizzaria.ajouterPizza(gratuite);

        // Ingrédients
        Ingredient i1 = new Ingredient("Fromage", 50);
        Ingredient i2 = new Ingredient("Tomate", 30);
        pizzaria.ajouterIngredient(i1);
        pizzaria.ajouterIngredient(i2);

        // Lancer l'interface principale
        SwingUtilities.invokeLater(() -> new InterfacePrincipale(pizzaria));
    }
}
