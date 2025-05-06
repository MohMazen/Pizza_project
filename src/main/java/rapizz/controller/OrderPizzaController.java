// --- controller/OrderPizzaController.java ---
package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Pizza;
import rapizz.model.Client;
import rapizz.view.OrderPizzaView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPizzaController {
    private final Point_Pizzaria model;
    private final Client client;
    private final OrderPizzaView view;

    // panier en mémoire
    private final List<OrderLine> orderLines = new ArrayList<>();
    private double totalOrder = 0.0;

    // représentant une ligne de commande
    private static class OrderLine {
        String nomPizza, taille;
        int qty;
        double unitPrice, lineTotal;

        OrderLine(String nomPizza, String taille, int qty, double unitPrice) {
            this.nomPizza   = nomPizza;
            this.taille     = taille;
            this.qty        = qty;
            this.unitPrice  = unitPrice;
            this.lineTotal  = unitPrice * qty;
        }

        void addQuantity(int moreQty) {
            this.qty       += moreQty;
            this.lineTotal  = this.unitPrice * this.qty;
        }
    }

    public OrderPizzaController(Point_Pizzaria model, Client client) {
        this.model  = model;
        this.client = client;
        this.view   = new OrderPizzaView(model);
        initController();
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAddLine());
        view.getChkMoto().addActionListener(e -> updateSummary());
        view.getBtnOrder().addActionListener(e -> onOrder());
        view.getBtnQuit().addActionListener(e -> view.dispose());
    }

    private void onAddLine() {
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
        double unitPrice = switch (taille) {
            case "naine"   -> base - base/3.0;
            case "ogresse" -> base + base/3.0;
            default        -> base;
        };

        // Recherche d'une ligne existante même pizza+taille
        boolean merged = false;
        for (OrderLine line : orderLines) {
            if (line.nomPizza.equals(nomPizza) && line.taille.equals(taille)) {
                // retire l'ancien montant
                totalOrder -= line.lineTotal;
                // fusionne les quantités
                line.addQuantity(qty);
                // ré-ajoute le nouveau total de cette ligne
                totalOrder += line.lineTotal;
                merged = true;
                break;
            }
        }
        if (!merged) {
            OrderLine newLine = new OrderLine(nomPizza, taille, qty, unitPrice);
            orderLines.add(newLine);
            totalOrder += newLine.lineTotal;
        }

        view.getQtyField().setText("");
        updateSummary();
        view.getBtnOrder().setEnabled(client.getSolde() >= (totalOrder + getDeliveryFee()));
    }

    private double getDeliveryFee() {
        return view.getChkMoto().isSelected() ? 2.0 : 0.0;
    }

    private void updateSummary() {
        StringBuilder sb = new StringBuilder();
        for (OrderLine l : orderLines) {
            sb.append(String.format("%d × %s (%s) = %.2f €%n",
                    l.qty, l.nomPizza, l.taille, l.lineTotal));
        }
        if (!orderLines.isEmpty()) sb.append("\n");
        double fee = getDeliveryFee();
        if (fee > 0) {
            sb.append(String.format("Frais livraison (moto) = %.2f €%n%n", fee));
        }
        sb.append(String.format("Total = %.2f €", totalOrder + fee));
        view.getTxtSummary().setText(sb.toString());
    }

    private void onOrder() {
        if (orderLines.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucune pizza dans le panier", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmation
        JOptionPane.showMessageDialog(
                view,
                view.getTxtSummary().getText(),
                "Confirmation de votre commande",
                JOptionPane.INFORMATION_MESSAGE
        );

        String vehicule = view.getChkMoto().isSelected() ? "moto" : "voiture";
        // Création des commandes et débit du compte
        for (OrderLine l : orderLines) {
            model.creerCommande(client, l.nomPizza, l.taille, l.qty, vehicule);
            client.approvisionner(-l.lineTotal);
        }
        double fee = getDeliveryFee();
        if (fee > 0) client.approvisionner(-fee);

        JOptionPane.showMessageDialog(
                view,
                "Votre commande a bien été enregistrée !",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );
        view.dispose();
    }
}
