// --- controller/OrderPizzaController.java (modifié) ---
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
        this.model = model;
        this.client = client;
        this.view = new OrderPizzaView(model);
        initController();
    }

    private void initController() {
        view.getBtnChoose().addActionListener(e -> onChoose());
        view.getChkMoto().addActionListener(e -> onChoose());   // recalcul si on coche/décoche
        view.getBtnOrder().addActionListener(e -> onOrder());
        view.getBtnQuit().addActionListener(e -> view.dispose());
    }

    private void onChoose() {
        String nomPizza = (String) view.getCbPizza().getSelectedItem();
        String taille   = (String) view.getCbSize().getSelectedItem();
        String qtyText  = view.getQtyField().getText().trim();

        if (!qtyText.matches("\\d+")) {
            JOptionPane.showMessageDialog(view, "Quantité invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int qty = Integer.parseInt(qtyText);
        Pizza pizza = model.getPizzaParNom(nomPizza);
        double base = pizza.getPrixBase();
        double prixUnitaire;
        switch (taille) {
            case "naine":  prixUnitaire = base - (base / 3.0); break;
            case "ogresse":prixUnitaire = base + (base / 3.0); break;
            default:       prixUnitaire = base;               break;
        }

        double total = prixUnitaire * qty;
        String vehicule = view.getChkMoto().isSelected() ? "moto" : "voiture";
        if (view.getChkMoto().isSelected()) {
            total += 2.0;  // +2 € si moto
        }

        view.getLblSummary().setText(
                String.format("Résumé : %d×%s (%s) + livraison %s = %.2f €",
                        qty, nomPizza, taille, vehicule, total)
        );

        // Active le bouton Commander si le solde couvre tout
        view.getBtnOrder().setEnabled(client.getSolde() >= total);
    }

    private void onOrder() {
        // Confirmation à l'écran
        JOptionPane.showMessageDialog(view,
                view.getLblSummary().getText(),
                "Confirmation de commande",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Enregistrement dans le modèle :
        String nomPizza = (String) view.getCbPizza().getSelectedItem();
        String taille   = (String) view.getCbSize().getSelectedItem();
        int qty         = Integer.parseInt(view.getQtyField().getText().trim());
        String vehicule = view.getChkMoto().isSelected() ? "moto" : "voiture";

        // ← Adapte ici la signature de creerCommande pour prendre en compte vehicule
        model.creerCommande(client, nomPizza, taille, qty, vehicule);

        // Débit du compte (à adapter si nécessaire pour inclure les 2 €)
        model.debiterSoldeClient(client, qty, nomPizza, taille);

        JOptionPane.showMessageDialog(view,
                "Votre commande a été enregistrée !",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );
        view.dispose();
    }
}
