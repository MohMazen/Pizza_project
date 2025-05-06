// File: src/rapizz/controller/InterfacePrincipaleController.java
package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.view.LoginView;
import rapizz.controller.LoginController;
import rapizz.controller.GestionRapizzController;

public class InterfacePrincipaleController {
    private final Point_Pizzaria pizzeria;

    public InterfacePrincipaleController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
    }

    public void showLogin() {
        new LoginView(new LoginController(pizzeria)).setVisible(true);
    }

    public void showGestion() {
        new GestionRapizzController(pizzeria);
    }

    public void exitApplication() {
        System.exit(0);
    }
}
