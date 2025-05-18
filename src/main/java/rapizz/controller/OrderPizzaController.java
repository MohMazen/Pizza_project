package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Pizza;
import rapizz.model.Client;
import rapizz.model.Commande;
import rapizz.view.OrderPizzaView;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contrôleur pour la prise de commande de pizzas.
 */
public class OrderPizzaController {
    Point_Pizzaria model;
    Client client;
    OrderPizzaView view;
    InterfaceClientController parentController;

    List<OrderLine> orderLines = new ArrayList<>();
    static final double DELIVERY_FEE_MOTO = 2.0;

    private static class OrderLine {
        String nomPizza, taille;
        int qty;
        BigDecimal unitPrice, lineTotal;

        OrderLine(String nomPizza, String taille, int qty, BigDecimal unitPrice) {
            this.nomPizza = nomPizza;
            this.taille = taille;
            this.qty = qty;
            this.unitPrice = unitPrice;
            this.lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));
        }

        void addQuantity(int moreQty) {
            this.qty += moreQty;
            this.lineTotal = this.unitPrice.multiply(BigDecimal.valueOf(this.qty));
        }
    }

    public OrderPizzaController(Point_Pizzaria model, Client client, InterfaceClientController parentController) {
        this.model = model;
        this.client = client;
        this.parentController = parentController;
        this.view = new OrderPizzaView(model);
        initController();
        view.setVisible(true);
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAddLine());
        view.getChkMoto().addActionListener(e -> updateSummary());
        view.getBtnOrder().addActionListener(e -> onOrder());
        view.getBtnQuit().addActionListener(e -> onQuit());
    }

    private void onAddLine() {
        String nomPizza = (String) view.getCbPizza().getSelectedItem();
        String taille   = (String) view.getCbSize().getSelectedItem();
        String qtyText  = view.getQtyField().getText().trim();

        if (nomPizza == null || taille == null) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une pizza et une taille", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!qtyText.matches("\\d+")) {
            JOptionPane.showMessageDialog(view, "Quantité invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int qty = Integer.parseInt(qtyText);
        Pizza pizza = model.getPizzaParNom(nomPizza);
        BigDecimal base = BigDecimal.valueOf(pizza.getPrixBase());
        BigDecimal unitPrice = switch (taille) {
            case "naine"  -> base.subtract(base.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP));
            case "ogresse"-> base.add   (base.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP));
            default       -> base;
        };

        boolean merged = false;
        for (OrderLine line : orderLines) {
            if (line.nomPizza.equals(nomPizza) && line.taille.equals(taille)) {
                line.addQuantity(qty);
                merged = true;
                break;
            }
        }
        if (!merged) {
            orderLines.add(new OrderLine(nomPizza, taille, qty, unitPrice));
        }

        view.getQtyField().setText("");
        updateSummary();
        double total = calculateOrderTotal() + getDeliveryFee();
        view.getBtnOrder().setEnabled(client.getSolde() >= total);
    }

    private double getDeliveryFee() {
        return view.getChkMoto().isSelected() ? DELIVERY_FEE_MOTO : 0.0;
    }

    private double calculateOrderTotal() {
        if (orderLines.isEmpty()) return 0.0;
        // Création d'une commande temporaire pour bénéficier du calcul de prix
        OrderLine first = orderLines.get(0);
        Pizza p = model.getPizzaParNom(first.nomPizza);
        Commande temp = new Commande(0, client, p, first.taille, first.qty, new Date());
        for (int i = 1; i < orderLines.size(); i++) {
            OrderLine l = orderLines.get(i);
            p = model.getPizzaParNom(l.nomPizza);
            temp.AjouterLigne(p, l.taille, l.qty);
        }
        return temp.calculPrixTotal();
    }

    private void updateSummary() {
        StringBuilder sb = new StringBuilder();
        for (OrderLine l : orderLines) {
            sb.append(String.format("%d × %s (%s) = %.2f €%n",
                    l.qty, l.nomPizza, l.taille, l.lineTotal.doubleValue()));
        }
        if (!orderLines.isEmpty()) sb.append("\n");
        double fee = getDeliveryFee();
        if (fee > 0) {
            sb.append(String.format("Frais livraison (moto) = %.2f €%n%n", fee));
        }
        double total = calculateOrderTotal() + fee;
        sb.append(String.format("Total = %.2f €", total));
        view.getTxtSummary().setText(sb.toString());
    }

    private void onOrder() {
        if (orderLines.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucune pizza dans le panier", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(
                view,
                view.getTxtSummary().getText(),
                "Confirmation de votre commande",
                JOptionPane.INFORMATION_MESSAGE
        );

        String vehicule = view.getChkMoto().isSelected() ? "moto" : "voiture";
        for (OrderLine l : orderLines) {
            if (client.getSolde() < l.lineTotal.doubleValue()) {
                JOptionPane.showMessageDialog(view, "Solde insuffisant pour cette commande", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.creerCommande(client, l.nomPizza, l.taille, l.qty, vehicule);
            client.approvisionner(-l.lineTotal.doubleValue());
        }
        double fee = getDeliveryFee();
        if (fee > 0) {
            client.approvisionner(-fee);
        }

        JOptionPane.showMessageDialog(
                view,
                "Votre commande a bien été enregistrée !",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );

        orderLines.clear();
        view.getTxtSummary().setText("");
        view.setVisible(false);
        parentController.showClientView();
    }

    private void onQuit() {
        view.setVisible(false);
        parentController.showClientView();
    }
}