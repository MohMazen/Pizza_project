// GestionRapizzController.java
package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.view.GestionRapizzView;
import rapizz.view.AddIngredientView;
import rapizz.view.AddClientView;
import rapizz.view.InterfacePrincipaleView;

import javax.swing.JOptionPane;

/**
 * Contrôleur pour la gestion RaPizz.
 */
public class GestionRapizzController {
    private final Point_Pizzaria pizzeria;
    private final GestionRapizzView view;

    // Le contrôleur instancie la vue et attachs les listeners
    public GestionRapizzController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
        this.view = new GestionRapizzView();
    }

    private void initController() {
        view.getBtnClients().addActionListener(e -> showClients());
        view.getBtnRevenue().addActionListener(e -> showRevenue());
        view.getBtnStock().addActionListener(e -> showStock());
        view.getBtnAddIngredient().addActionListener(e -> showAddIngredient());
        view.getBtnAddClient().addActionListener(e -> showAddClient());
        view.getBtnBack().addActionListener(e -> returnToMain());
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

    private void showAddIngredient() {
        AddIngredientView ingView = new AddIngredientView();
        new AddIngredientController(pizzeria, ingView);
    }

    private void showAddClient() {
        AddClientView clientView = new AddClientView();
        new AddClientController(pizzeria, clientView);
    }

    private void returnToMain() {
        new InterfacePrincipaleView(new InterfacePrincipaleController(pizzeria)).setVisible(true);
        view.dispose();
    }
}
