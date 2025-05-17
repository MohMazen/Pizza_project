package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.model.Pizza;
import rapizz.model.Client;
import rapizz.view.InterfaceClientView;
import rapizz.view.InterfacePrincipaleView;
import rapizz.view.OrderPizzaView;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderPizzaController {
    private final Point_Pizzaria model;
    private final Client client;
    private final OrderPizzaView view;

    private final List<OrderLine> orderLines = new ArrayList<>();
    private BigDecimal totalOrder = BigDecimal.ZERO;
    private static final double DELIVERY_FEE_MOTO = 2.0;
    private LoginController controller;

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

    public OrderPizzaController(Point_Pizzaria model, Client client) {
        this.model = model;
        this.client = client;
        this.view = new OrderPizzaView(model);
        initController();
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAddLine());
        view.getChkMoto().addActionListener(e -> updateSummary());
        view.getBtnOrder().addActionListener(e -> onOrder());
        view.getBtnQuit().addActionListener(e -> {
            view.dispose();
            new InterfaceClientView(new InterfaceClientController(model, client)).setVisible(true);
        });
    }

    private void onAddLine() {
        String nomPizza = (String) view.getCbPizza().getSelectedItem();
        String taille = (String) view.getCbSize().getSelectedItem();
        String qtyText = view.getQtyField().getText().trim();

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
            case "naine" -> base.subtract(base.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP));
            case "ogresse" -> base.add(base.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP));
            default -> base;
        };

        boolean merged = false;
        for (OrderLine line : orderLines) {
            if (line.nomPizza.equals(nomPizza) && line.taille.equals(taille)) {
                totalOrder = totalOrder.subtract(line.lineTotal);
                line.addQuantity(qty);
                totalOrder = totalOrder.add(line.lineTotal);
                merged = true;
                break;
            }
        }
        if (!merged) {
            OrderLine newLine = new OrderLine(nomPizza, taille, qty, unitPrice);
            orderLines.add(newLine);
            totalOrder = totalOrder.add(newLine.lineTotal);
        }

        view.getQtyField().setText("");
        updateSummary();
        view.getBtnOrder().setEnabled(client.getSolde() >= totalOrder.add(BigDecimal.valueOf(getDeliveryFee())).doubleValue());
    }

    private double getDeliveryFee() {
        return view.getChkMoto().isSelected() ? DELIVERY_FEE_MOTO : 0.0;
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
        sb.append(String.format("Total = %.2f €", totalOrder.add(BigDecimal.valueOf(fee)).doubleValue()));
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
        if (fee > 0) client.approvisionner(-fee);

        JOptionPane.showMessageDialog(
                view,
                "Votre commande a bien été enregistrée !",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}