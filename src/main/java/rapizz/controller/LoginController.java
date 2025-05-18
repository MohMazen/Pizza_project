package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Client;
import rapizz.view.InterfacePrincipaleView;

import java.util.Vector;

public class LoginController {
    private Point_Pizzaria pizzeria;

    // Constructeur par défaut
    public LoginController() {
        this.pizzeria = new Point_Pizzaria("RaPizz", "a");
    }

    // Constructeur avec modèle existant
    public LoginController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
    }

    // Recherche un client par téléphone
    public Client findClientByPhone(String phone) {
        Vector<Client> clients = pizzeria.getClients();
        for (Client c : clients) {
            if (isValidPhoneNumber(phone) && c.getTelephone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    // Authentifie un client (1 si trouvé, 0 sinon)
    public int authenticate(String phone) {
        return findClientByPhone(phone) != null ? 1 : 0;
    }

    // Retourne le client trouvé
    public Client getClient(String phone) {
        return findClientByPhone(phone);
    }

    // Retourne la pizzeria
    public Point_Pizzaria getPizzeria() {
        return pizzeria;
    }

    // Vérifie le format du numéro de téléphone
    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Action retour à l'interface principale
    public void handleBackAction() {
        InterfacePrincipaleView interfaceView = new InterfacePrincipaleView(new InterfacePrincipaleController(pizzeria));
        interfaceView.setVisible(true);
        interfaceView.dispose();
    }
}