// File: src/rapizz/controller/AddClientController.java
package rapizz.controller;

import rapizz.model.Client;
import rapizz.model.Point_Pizzaria;
import rapizz.view.AddClientView;

import javax.swing.*;

public class AddClientController {
    private final Point_Pizzaria model;
    private final AddClientView view;

    public AddClientController(Point_Pizzaria model, AddClientView view) {
        this.model = model;
        this.view = view;
        initController();
        // Affiche la vue après avoir rattache les listeners
        view.setVisible(true);
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnBack().addActionListener(e -> onBack());
    }

    private void onAdd() {
        String name  = view.getNameField().getText().trim();
        String addr  = view.getAddressField().getText().trim();
        String phone = view.getPhoneField().getText().trim();

        if (name.isEmpty() || addr.isEmpty() || !phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(
                    view,
                    "Veuillez remplir tous les champs correctement (téléphone : 10 chiffres).",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (model.findClientByPhone(phone) != null) {
            JOptionPane.showMessageDialog(
                    view,
                    "Ce client existe déjà.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Client c = new Client(phone, name, addr, 0.0, model);
        model.ajouterClient(c);

        JOptionPane.showMessageDialog(
                view,
                "Client ajouté avec succès.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );

        onBack();
    }

    private void onBack() {
        // Retour à l'écran de gestion et fermeture de cette fenêtre
        new GestionRapizzController(model);
        view.dispose();
    }
}
