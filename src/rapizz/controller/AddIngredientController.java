// File: src/rapizz/controller/AddIngredientController.java
package rapizz.controller;

import rapizz.model.Point_Pizzaria;
import rapizz.view.AddIngredientView;
import rapizz.view.GestionRapizzView;

import javax.swing.*;

public class AddIngredientController {
    private final Point_Pizzaria model;
    private final AddIngredientView view;

    public AddIngredientController(Point_Pizzaria model, AddIngredientView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnBack().addActionListener(e -> onBack());
    }

    private void onAdd() {
        String nom     = view.getNameField().getText().trim();
        String qtyText = view.getQtyField().getText().trim();

        // Validation des champs
        if (nom.isEmpty() || !qtyText.matches("\\d+")) {
            JOptionPane.showMessageDialog(
                    view,
                    "Veuillez saisir un nom d’ingrédient et une quantité valides.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int quantite = Integer.parseInt(qtyText);
        model.ajouterIngredient(nom, quantite);
        JOptionPane.showMessageDialog(
                view,
                "Ingrédient ajouté avec succès.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Retour à l’écran de gestion
        openGestion();
        view.dispose();
    }

    private void onBack() {
        // Retour sans ajout
        openGestion();
        view.dispose();
    }

    private void openGestion() {
        GestionRapizzView gestionView = new GestionRapizzView();
        new GestionRapizzController(model);
        gestionView.setVisible(true);
    }
}
