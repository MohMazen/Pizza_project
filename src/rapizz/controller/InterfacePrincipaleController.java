package rapizz.controller;

import rapizz.model.Point_Pizzaria;

/**
 * Contrôleur pour l'interface principale de l'application.
 */
public class InterfacePrincipaleController {
    private Point_Pizzaria pizzeria;

    public InterfacePrincipaleController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
    }

    /**
     * Ouvre la fenêtre de connexion client.
     */
    public void showLogin(javax.swing.JFrame currentFrame) {
        currentFrame.dispose();
        new rapizz.view.LoginView(new LoginController(pizzeria)).setVisible(true);
    }

    /**
     * Ouvre la future interface de gestion.
     */
    public void showGestion(javax.swing.JFrame currentFrame) {
        currentFrame.dispose();
        new rapizz.view.GestionRapizzView().setVisible(true);
    }

    /**
     * Quitte l'application.
     */
    public void exitApplication() {
        System.exit(0);
    }
}
