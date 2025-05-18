package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Client;
import rapizz.view.InterfaceClientView;
import rapizz.view.InterfacePrincipaleView;

import javax.swing.*;

public class InterfaceClientController {
    Point_Pizzaria pizzeria;
    Client client;
    InterfaceClientView view;
    OrderPizzaController orderController;

    // Constructeur du contrôleur client
    public InterfaceClientController(Point_Pizzaria pizzeria, Client client) {
        this.pizzeria = pizzeria;
        this.client    = client;
        this.view      = new InterfaceClientView();
        initController();
        view.setVisible(true);
    }

    // Initialise les actions des boutons de la vue client
    private void initController() {
        view.getBtnOrderPizza().addActionListener(e -> showOrderPizza());
        view.getBtnViewOrders().addActionListener(e -> showOrders());
        view.getBtnViewBalance().addActionListener(e -> showBalance());
        view.getBtnAddBalance().addActionListener(e -> addBalance());
        view.getBtnBack().addActionListener(e -> returnToMain());
    }

    // Affiche la fenêtre de commande de pizza
    public void showOrderPizza() {
        view.setVisible(false);
        if (orderController == null) {
            orderController = new OrderPizzaController(pizzeria, client, this);
        } else {
            orderController.view.setVisible(true);
        }
    }

    // Affiche la vue client principale
    public void showClientView() {
        view.setVisible(true);
    }

    // Affiche les commandes du client
    public void showOrders() {
        String summary = pizzeria.afficherCommandesClient(client);
        JOptionPane.showMessageDialog(view, summary, "Résumé de vos commandes", JOptionPane.INFORMATION_MESSAGE);
    }

    // Affiche le solde du client
    public void showBalance() {
        double solde = client.getSolde();
        JOptionPane.showMessageDialog(view, String.format("Votre solde : %.2f €", solde), "Solde", JOptionPane.INFORMATION_MESSAGE);
    }

    // Permet d'ajouter un montant au solde du client
    public void addBalance() {
        String input = JOptionPane.showInputDialog(view, "Montant à ajouter :");
        if (input != null && input.matches("\\d+(\\.\\d{1,2})?")) {
            client.approvisionner(Double.parseDouble(input));
            JOptionPane.showMessageDialog(view, String.format("Nouveau solde : %.2f €", client.getSolde()), "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Montant invalide (ex. 12.50)", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Retourne à la fenêtre principale de l'application
    public void returnToMain() {
        view.setVisible(false);
        new InterfacePrincipaleView(new InterfacePrincipaleController(pizzeria)).setVisible(true);
    }
}