// File: src/rapizz/view/GestionRapizzView.java
package rapizz.view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;

/**
 * Vue de gestion de l'application RaPizz.
 * Boutons à droite, Retour et Quitter centrés, tous 200x45.
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
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Panel de droite : boutons principaux
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 40));

        btnClients        = createButton("Voir Clients");
        btnRevenue        = createButton("Chiffre d'Affaires");
        btnStock          = createButton("Voir Stock");
        btnAddIngredient  = createButton("Ajouter Ingrédient");
        btnAddPizza       = createButton("Ajouter Pizza");
        btnAddClient      = createButton("Ajouter Client");

        rightPanel.add(btnClients);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnRevenue);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnStock);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnAddIngredient);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnAddPizza);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnAddClient);

        // Panel bas : retour et quitter
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        bottomPanel.setBackground(Color.WHITE);

        btnBack = createButton("Retour à l'accueil");
        btnQuit = createButton("Quitter");

        bottomPanel.add(btnBack);
        bottomPanel.add(btnQuit);

        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
    }

    private FlatButton createButton(String text) {
        FlatButton btn = new FlatButton();
        btn.setText(text);
        btn.setPreferredSize(new Dimension(200, 45)); // même taille pour tous
        btn.putClientProperty("JButton.buttonType", "roundRect");
        return btn;
    }

    // Getters pour le contrôleur
    public FlatButton getBtnClients()        { return btnClients; }
    public FlatButton getBtnRevenue()        { return btnRevenue; }
    public FlatButton getBtnStock()          { return btnStock; }
    public FlatButton getBtnAddIngredient()  { return btnAddIngredient; }
    public FlatButton getBtnAddPizza()       { return btnAddPizza; }
    public FlatButton getBtnAddClient()      { return btnAddClient; }
    public FlatButton getBtnBack()           { return btnBack; }
    public FlatButton getBtnQuit()           { return btnQuit; }
}
