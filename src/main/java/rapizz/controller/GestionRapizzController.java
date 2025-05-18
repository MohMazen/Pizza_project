package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.view.GestionRapizzView;
import rapizz.view.AddIngredientView;
import rapizz.view.AddPizzaView;
import rapizz.view.AddClientView;
import rapizz.view.InterfacePrincipaleView;

import javax.swing.JOptionPane;

public class GestionRapizzController {
    private final Point_Pizzaria pizzeria;
    private final GestionRapizzView view;

    // Constructeur du contrôleur de gestion
    public GestionRapizzController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
        this.view = new GestionRapizzView();
        initController();
        view.setVisible(true);
    }

    // Initialise les actions des boutons de la vue de gestion
    private void initController() {
        // Affiche la liste des clients
        view.getBtnClients().addActionListener(e -> showClients());
        // Affiche le chiffre d'affaires
        view.getBtnRevenue().addActionListener(e -> showRevenue());
        // Affiche le stock d'ingrédients
        view.getBtnStock().addActionListener(e -> showStock());
        // Ouvre la fenêtre d'ajout d'ingrédient
        view.getBtnAddIngredient().addActionListener(e -> {
            view.dispose();
            new AddIngredientController(pizzeria, new AddIngredientView());
        });
        // Ouvre la fenêtre d'ajout de pizza
        view.getBtnAddPizza().addActionListener(e -> {
            view.dispose();
            new AddPizzaController(pizzeria, new AddPizzaView());
        });
        // Ouvre la fenêtre d'ajout de client
        view.getBtnAddClient().addActionListener(e -> {
            view.dispose();
            new AddClientController(pizzeria, new AddClientView());
        });
        // Retour à l'interface principale
        view.getBtnBack().addActionListener(e -> {
            new InterfacePrincipaleView(new InterfacePrincipaleController(pizzeria)).setVisible(true);
            view.dispose();
        });
        // Quitte l'application
        view.getBtnQuit().addActionListener(e -> System.exit(0));
    }

    // Affiche la liste des clients dans une boîte de dialogue
    private void showClients() {
        String info = pizzeria.afficherClients();
        JOptionPane.showMessageDialog(view, info, "Liste des clients", JOptionPane.INFORMATION_MESSAGE);
    }

    // Affiche le chiffre d'affaires dans une boîte de dialogue
    private void showRevenue() {
        double ca = pizzeria.getTresorerie();
        JOptionPane.showMessageDialog(view, String.format("Chiffre d'affaires : %.2f €", ca), "Revenu", JOptionPane.INFORMATION_MESSAGE);
    }

    // Affiche le stock d'ingrédients dans une boîte de dialogue
    private void showStock() {
        String stock = pizzeria.afficherQuantiteIngredients();
        JOptionPane.showMessageDialog(view, stock, "Stock d'ingrédients", JOptionPane.INFORMATION_MESSAGE);
    }
}