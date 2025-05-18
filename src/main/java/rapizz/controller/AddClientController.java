package rapizz.controller;

import rapizz.model.Client;
import rapizz.model.Point_Pizzaria;
import rapizz.view.AddClientView;

import javax.swing.*;

// Contrôleur pour ajouter un client
public class AddClientController {
    private final Point_Pizzaria model;
    private final AddClientView view;

    // Constructeur
    public AddClientController(Point_Pizzaria model, AddClientView view) {
        this.model = model;
        this.view = view;
        initController();
        view.setVisible(true); // Affiche la vue
    }

    // Initialise les boutons
    private void initController() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnBack().addActionListener(e -> onBack());
    }

    // Ajoute un client
    private void onAdd() {
        String name  = view.getNameField().getText().trim();
        String addr  = view.getAddressField().getText().trim();
        String phone = view.getPhoneField().getText().trim();

        // Vérifie les champs
        if (name.isEmpty() || addr.isEmpty() || !phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(
                view,
                "Veuillez remplir tous les champs correctement (téléphone : 10 chiffres).",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Vérifie si le client existe déjà
        if (model.findClientByPhone(phone) != null) {
            JOptionPane.showMessageDialog(
                view,
                "Ce client existe déjà.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Ajoute le client
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

    // Retour à la gestion principale
    private void onBack() {
        new GestionRapizzController(model);
        view.dispose();
    }
}