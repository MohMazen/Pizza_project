// src/rapizz/controller/InterfaceClientController.java
package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Client;
import rapizz.view.InterfaceClientView;
import rapizz.view.InterfacePrincipaleView;
import rapizz.controller.OrderPizzaController;

import javax.swing.*;

/**
 * Contrôleur pour l'espace client.
 */
public class InterfaceClientController {
    private final Point_Pizzaria pizzeria;
    private final Client client;
    private final InterfaceClientView view;

    public InterfaceClientController(Point_Pizzaria pizzeria, Client client) {
        this.pizzeria = pizzeria;
        this.client    = client;
        this.view      = new InterfaceClientView(this);
    }

    /** Ouvre la vue de commande de pizza. */
    public void showOrderPizza() {
        // Instancie OrderPizzaController qui crée et affiche OrderPizzaView
        new OrderPizzaController(pizzeria, client);
        view.dispose();
    }

    /** Affiche un récapitulatif des commandes du client. */
    public void showOrders() {
        // On suppose que Point_Pizzaria a une méthode afficherCommandesClient(Client)
        String summary = pizzeria.afficherCommandesClient(client);
        JOptionPane.showMessageDialog(
                view,
                summary,
                "Résumé de vos commandes",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    // src/rapizz/controller/InterfaceClientController.java

    /** Affiche le solde du client. */
    public void showBalance() {
        double solde = client.getSolde();
        JOptionPane.showMessageDialog(
                view,
                String.format("Votre solde : %.2f €", solde),
                "Solde",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /** Permet d'ajouter du solde au compte client. */
    public void addBalance() {
        String input = JOptionPane.showInputDialog(view, "Montant à ajouter :");
        // Vérifie un nombre positif avec éventuellement deux décimales
        if (input != null && input.matches("\\d+(\\.\\d{1,2})?")) {
            double montant = Double.parseDouble(input);
            client.approvisionner(montant);
            JOptionPane.showMessageDialog(
                    view,
                    String.format("Solde mis à jour ! Nouveau solde : %.2f €", client.getSolde()),
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    view,
                    "Montant invalide, veuillez entrer un nombre (ex. 12.50).",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /** Retourne à l'interface principale et ferme la vue client. */
    public void returnToMain() {
        new InterfacePrincipaleView(new InterfacePrincipaleController(pizzeria)).setVisible(true);
        view.dispose();
    }
}
