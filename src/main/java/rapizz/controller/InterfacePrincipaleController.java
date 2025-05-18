package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.view.LoginView;

public class InterfacePrincipaleController {
    private final Point_Pizzaria pizzeria;

    // Constructeur du contrôleur principal
    public InterfacePrincipaleController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
    }

    // Affiche la fenêtre de connexion
    public void showLogin() {
        new LoginView(new LoginController(pizzeria)).setVisible(true);
    }

    // Affiche la gestion de la pizzeria
    public void showGestion() {
        new GestionRapizzController(pizzeria);
    }

    // Ferme l'application
    public void exitApplication() {
        System.exit(0);
    }
}