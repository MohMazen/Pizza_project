// File: src/rapizz/controller/AddPizzaController.java
package rapizz.controller;

import rapizz.model.Pizza;
import rapizz.model.Point_Pizzaria;
import rapizz.view.AddPizzaView;

import javax.swing.*;
import java.util.List;

public class AddPizzaController {
    private final Point_Pizzaria model;
    private final AddPizzaView view;

    public AddPizzaController(Point_Pizzaria model, AddPizzaView view) {
        this.model = model;
        this.view = view;
        // Remplissage de la checklist grâce au modèle
        List<String> options = model.getListeIngredients();
        view.setIngredientsOptions(options);

        initController();
        view.setVisible(true);
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnBack().addActionListener(e -> onBack());
    }

    private void onAdd() {
        String name = view.getPizzaName();
        String priceText = view.getPriceText();
        List<String> selected = view.getSelectedIngredients();

        if (name.isEmpty() || !priceText.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(view, "Nom ou prix invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Sélectionnez au moins un ingrédient.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price = Double.parseDouble(priceText);
        Pizza p = new Pizza(name, price);
        model.ajouterPizza(p);

        JOptionPane.showMessageDialog(view, "Pizza ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        onBack();
    }

    private void onBack() {
        new GestionRapizzController(model);
        view.dispose();
    }
}
