package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Client;
import java.util.Vector;

public class LoginController {
    private Point_Pizzaria pizzeria;

    public LoginController() {
        this.pizzeria = new Point_Pizzaria("RaPizz", "a");
    }

    public LoginController(Point_Pizzaria pizzeria) {
        this.pizzeria = pizzeria;
    }

    public Client findClientByPhone(String phone) {
        Vector<Client> clients = pizzeria.getClients();
        for (Client c : clients) {
            if (isValidPhoneNumber(phone) && c.getTelephone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    public int authenticate(String phone) {
        return findClientByPhone(phone) != null ? 1 : 0;
    }

    public Client getClient(String phone) {
        return findClientByPhone(phone);
    }

    public Point_Pizzaria getPizzeria() {
        return pizzeria;
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}"); // Exemple : 10 chiffres
    }
}