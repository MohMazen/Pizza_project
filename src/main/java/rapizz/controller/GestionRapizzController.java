// File: src/rapizz/controller/GestionRapizzController.java
package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.view.GestionRapizzView;
import rapizz.view.AddIngredientView;
import rapizz.view.AddPizzaView;
import rapizz.view.AddClientView;
import rapizz.view.InterfacePrincipaleView;

import javax.swing.JOptionPane;

/**
 * Contrôleur pour la gestion RaPizz.
 */
public class GestionRapizzController {
    private final Point_Pizzaria pizzeria;
    private final GestionRapizzView view;

    public GestionRapizzController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
        this.view = new GestionRapizzView();
        initController();  // rattache listeners
        view.setVisible(true); // affiche la vue
    }

    private void initController() {
        view.getBtnClients().addActionListener(e -> showClients());
        view.getBtnRevenue().addActionListener(e -> showRevenue());
        view.getBtnStock().addActionListener(e -> showStock());
        view.getBtnAddIngredient().addActionListener(e -> {
            view.dispose(); new AddIngredientController(pizzeria, new AddIngredientView());
        });
        view.getBtnAddPizza().addActionListener(e -> {
            view.dispose(); new AddPizzaController(pizzeria, new AddPizzaView());
        });
        view.getBtnAddClient().addActionListener(e -> {
            view.dispose(); new AddClientController(pizzeria, new AddClientView());
        });
        view.getBtnBack().addActionListener(e -> {
            new InterfacePrincipaleView(new InterfacePrincipaleController(pizzeria)).setVisible(true);
            view.dispose();
        });
        view.getBtnQuit().addActionListener(e -> System.exit(0));
    }

    private void showClients() {
        String info = pizzeria.afficherClients();
        JOptionPane.showMessageDialog(view, info, "Liste des clients", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRevenue() {
        double ca = pizzeria.getTresorerie();
        JOptionPane.showMessageDialog(view, String.format("Chiffre d'affaires : %.2f €", ca), "Revenu", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showStock() {
        String stock = pizzeria.afficherQuantiteIngredients();
        JOptionPane.showMessageDialog(view, stock, "Stock d'ingrédients", JOptionPane.INFORMATION_MESSAGE);
    }
}
