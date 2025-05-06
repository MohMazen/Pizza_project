// File: src/rapizz/view/GestionRapizzView.java
package rapizz.view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;

/**
 * Vue de gestion de l'application RaPizz.
 * Affiche les boutons de navigation et expose des getters pour le controller.
 */
public class GestionRapizzView extends JFrame {
    private FlatButton btnClients;
    private FlatButton btnRevenue;
    private FlatButton btnStock;
    private FlatButton btnAddIngredient;
    private FlatButton btnAddPizza;
    private FlatButton btnAddClient;
    private FlatButton btnBack;
    private FlatButton btnQuit;

    public GestionRapizzView() {
        super("RaPizz - Gestion");
        FlatLightLaf.setup();
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        int row = 0;
        btnClients = new FlatButton(); btnClients.setText("Voir Clients"); gbc.gridy = row++;
        panel.add(btnClients, gbc);

        btnRevenue = new FlatButton(); btnRevenue.setText("Chiffre d'Affaires"); gbc.gridy = row++;
        panel.add(btnRevenue, gbc);

        btnStock = new FlatButton(); btnStock.setText("Voir Stock"); gbc.gridy = row++;
        panel.add(btnStock, gbc);

        btnAddIngredient = new FlatButton(); btnAddIngredient.setText("Ajouter Ingrédient"); gbc.gridy = row++;
        panel.add(btnAddIngredient, gbc);

        btnAddPizza = new FlatButton(); btnAddPizza.setText("Ajouter Pizza"); gbc.gridy = row++;
        panel.add(btnAddPizza, gbc);

        btnAddClient = new FlatButton(); btnAddClient.setText("Ajouter Client"); gbc.gridy = row++;
        panel.add(btnAddClient, gbc);

        btnBack = new FlatButton(); btnBack.setText("Retour à l'accueil"); gbc.gridy = row++;
        panel.add(btnBack, gbc);

        btnQuit = new FlatButton(); btnQuit.setText("Quitter"); gbc.gridy = row++;
        panel.add(btnQuit, gbc);

        getContentPane().add(panel);
    }

    // Getters exposés pour le controller
    public FlatButton getBtnClients() { return btnClients; }
    public FlatButton getBtnRevenue() { return btnRevenue; }
    public FlatButton getBtnStock() { return btnStock; }
    public FlatButton getBtnAddIngredient() { return btnAddIngredient; }
    public FlatButton getBtnAddPizza() { return btnAddPizza; }
    public FlatButton getBtnAddClient() { return btnAddClient; }
    public FlatButton getBtnBack() { return btnBack; }
    public FlatButton getBtnQuit() { return btnQuit; }
}
