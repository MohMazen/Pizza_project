package rapizz.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;


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
        // Couleur de fond rouge (HSB: hue=0, saturation=1, brightness=1)
        Color C_BACK = new Color(255, 156, 0, 255);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(C_BACK);

        // Panel de boutons (vertical)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(C_BACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, -100));

        btnClients        = createMainButton("Voir Clients");
        btnRevenue        = createMainButton("Chiffre d'Affaires");
        btnStock          = createMainButton("Voir Stock");
        btnAddIngredient  = createMainButton("Ajouter Ingrédient");
        btnAddPizza       = createMainButton("Ajouter Pizza");
        btnAddClient      = createMainButton("Ajouter Client");

        buttonPanel.add(btnClients);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnRevenue);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnStock);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnAddIngredient);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnAddPizza);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnAddClient);

        // Chargement de l'image à droite
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rapizz/resources/logo2.png")));
        Image scaledImage = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(scaledImage));

        // Panel est (boutons + image)
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
        eastPanel.setBackground(C_BACK);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 60));
        eastPanel.add(buttonPanel);
        eastPanel.add(Box.createHorizontalStrut(30));
        eastPanel.add(lblImage);

        // Panel bas : retour et quitter
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        bottomPanel.setBackground(C_BACK);

        btnBack = createButton("Retour à l'accueil");
        btnQuit = createButton("Quitter");

        bottomPanel.add(btnBack);
        bottomPanel.add(btnQuit);

        mainPanel.add(eastPanel, BorderLayout.WEST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
    }

    /**
     * Crée un FlatButton pour les boutons principaux (largeur commune).
     */
    private FlatButton createMainButton(String text) {
        FlatButton btn = new FlatButton();
        btn.setText(text);
        btn.setPreferredSize(new Dimension(250, 60));
        btn.putClientProperty("JButton.buttonType", "roundRect");
        return btn;
    }

    /**
     * Crée un FlatButton pour les boutons secondaires (même largeur, hauteur originale).
     */
    private FlatButton createButton(String text) {
        FlatButton btn = new FlatButton();
        btn.setText(text);
        btn.setPreferredSize(new Dimension(250, 45));
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