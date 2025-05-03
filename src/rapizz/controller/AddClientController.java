// --- src/rapizz/controller/AddClientController.java ---
package rapizz.controller;

import rapizz.model.Client;
import rapizz.model.Point_Pizzaria;
import rapizz.view.AddClientView;
import rapizz.view.GestionRapizzView;

import javax.swing.*;

public class AddClientController {
    private final Point_Pizzaria model;
    private final AddClientView view;

    public AddClientController(Point_Pizzaria model, AddClientView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnBack().addActionListener(e -> onBack());
    }

    private void onAdd() {
        String name  = view.getNameField().getText().trim();
        String addr  = view.getAddressField().getText().trim();
        String phone = view.getPhoneField().getText().trim();

        // Validation des champs
        if (name.isEmpty() || addr.isEmpty() || !phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(
                    view,
                    "Veuillez remplir tous les champs correctement (téléphone : 10 chiffres).",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Vérifier l’existence du client
        if (model.findClientByPhone(phone) != null) {
            JOptionPane.showMessageDialog(
                    view,
                    "Ce client existe déjà.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Création et ajout du client
        Client c = new Client(phone, name, addr, 0.0, model);
        model.ajouterClient(c);

        JOptionPane.showMessageDialog(
                view,
                "Client ajouté avec succès.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Retour à l'écran de gestion
        openGestion();
        view.dispose();
    }

    private void onBack() {
        // Simple retour sans ajout
        openGestion();
        view.dispose();
    }

    private void openGestion() {
        GestionRapizzView gestionView = new GestionRapizzView();
        new GestionRapizzController(model);
        gestionView.setVisible(true);
    }
}
