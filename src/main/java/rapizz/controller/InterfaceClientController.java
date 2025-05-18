// src/rapizz/controller/InterfaceClientController.java
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

    public InterfaceClientController(Point_Pizzaria pizzeria, Client client) {
        this.pizzeria = pizzeria;
        this.client    = client;
        this.view      = new InterfaceClientView();
        initController();
        view.setVisible(true);
    }

    private void initController() {
        view.getBtnOrderPizza().addActionListener(e -> showOrderPizza());
        view.getBtnViewOrders().addActionListener(e -> showOrders());
        view.getBtnViewBalance().addActionListener(e -> showBalance());
        view.getBtnAddBalance().addActionListener(e -> addBalance());
        view.getBtnBack().addActionListener(e -> returnToMain());
    }

    public void showOrderPizza() {
        view.setVisible(false);
        if (orderController == null) {
            orderController = new OrderPizzaController(pizzeria, client, this);
        } else {
            orderController.view.setVisible(true);
        }
    }

    public void showClientView() {
        view.setVisible(true);
    }

    public void showOrders() {
        String summary = pizzeria.afficherCommandesClient(client);
        JOptionPane.showMessageDialog(view, summary, "Résumé de vos commandes", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showBalance() {
        double solde = client.getSolde();
        JOptionPane.showMessageDialog(view, String.format("Votre solde : %.2f €", solde), "Solde", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addBalance() {
        String input = JOptionPane.showInputDialog(view, "Montant à ajouter :");
        if (input != null && input.matches("\\d+(\\.\\d{1,2})?")) {
            client.approvisionner(Double.parseDouble(input));
            JOptionPane.showMessageDialog(view, String.format("Nouveau solde : %.2f €", client.getSolde()), "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Montant invalide (ex. 12.50)", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void returnToMain() {
        view.setVisible(false);
        new InterfacePrincipaleView(new InterfacePrincipaleController(pizzeria)).setVisible(true);
    }
}
