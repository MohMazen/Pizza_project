// --- controller/OrderPizzaController.java ---
package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Pizza;
import rapizz.model.Client;
import rapizz.view.OrderPizzaView;

import javax.swing.*;

public class OrderPizzaController {
    private final Point_Pizzaria model;
    private final Client client;
    private final OrderPizzaView view;

    public OrderPizzaController(Point_Pizzaria model, Client client) {
        this.model  = model;
        this.client = client;
        this.view   = new OrderPizzaView(model);
        initController();
    }

    private void initController() {
        view.getBtnChoose().addActionListener(e -> onChoose());
        view.getChkMoto().addActionListener(e -> onChoose());
        view.getBtnOrder().addActionListener(e -> onOrder());
        view.getBtnQuit().addActionListener(e -> view.dispose());
    }

    private void onChoose() {
        String nomPizza = (String) view.getCbPizza().getSelectedItem();
        String taille   = (String) view.getCbSize().getSelectedItem();
        String qtyText  = view.getQtyField().getText().trim();

        if (!qtyText.matches("\\d+")) {
            JOptionPane.showMessageDialog(view, "Quantité invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            view.getTxtSummary().setText("");
            view.getBtnOrder().setEnabled(false);
            return;
        }

        int qty = Integer.parseInt(qtyText);
        Pizza pizza = model.getPizzaParNom(nomPizza);
        double base = pizza.getPrixBase();
        double prixUnitaire;
        switch (taille) {
            case "naine":   prixUnitaire = base - base/3.0; break;
            case "ogresse": prixUnitaire = base + base/3.0; break;
            default:        prixUnitaire = base;             break;
        }

        double total = prixUnitaire * qty;
        double fraisLivraison = view.getChkMoto().isSelected() ? 2.0 : 0.0;
        total += fraisLivraison;
        String vehicule = view.getChkMoto().isSelected() ? "moto" : "voiture";

        String txt = String.format(
                "%d × %s (%s)\nLivraison : %s (+%.2f€)\n\nTotal à payer : %.2f €",
                qty, nomPizza, taille,
                vehicule, fraisLivraison,
                total
        );
        view.getTxtSummary().setText(txt);
        view.getBtnOrder().setEnabled(client.getSolde() >= total);
    }

    private void onOrder() {
        String summary = view.getTxtSummary().getText();
        if (summary.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Veuillez d’abord cliquer sur « Calculer »",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Confirmation
        JOptionPane.showMessageDialog(
                view,
                summary,
                "Confirmation de commande",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Enregistrement
        String nomPizza = (String) view.getCbPizza().getSelectedItem();
        String taille   = (String) view.getCbSize().getSelectedItem();
        int qty         = Integer.parseInt(view.getQtyField().getText().trim());
        String vehicule = view.getChkMoto().isSelected() ? "moto" : "voiture";

        // Crée la commande en passant le type de véhicule
        model.creerCommande(client, nomPizza, taille, qty, vehicule);

        // Débit du compte avec le total exact (incluant les 2€ si moto)
        Pizza pizza = model.getPizzaParNom(nomPizza);
        double base = pizza.getPrixBase();
        double prixUnitaire = switch (taille) {
            case "naine"   -> base - base/3.0;
            case "ogresse" -> base + base/3.0;
            default        -> base;
        };
        double montant = prixUnitaire * qty + (vehicule.equals("moto") ? 2.0 : 0.0);
        client.approvisionner(-montant); // retire le montant du solde

        JOptionPane.showMessageDialog(
                view,
                "Votre commande a été enregistrée !",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );
        view.dispose();
    }
}
