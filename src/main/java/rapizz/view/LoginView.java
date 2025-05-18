package rapizz.view;

import rapizz.controller.InterfacePrincipaleController;
import rapizz.controller.LoginController;
import rapizz.controller.InterfaceClientController;
import rapizz.model.Client;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class LoginView extends JFrame {
    private JTextField phoneField;
    private final LoginController controller;

    // Constructeur
    public LoginView(LoginController controller) {
        this.controller = controller;
        FlatLightLaf.setup(); // Applique le thème FlatLaf
        setTitle("RaPizz - Connexion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 550);
        setLocationRelativeTo(null); // Centre la fenêtre
        initComponents();            // Initialise les composants graphiques
        setVisible(true);            // Affiche la fenêtre
    }

    // Initialise les champs et boutons
    private void initComponents() {
        JPanel panel = getJPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0)); // Espace en haut
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Titre
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel label = new JLabel("Connexion Client");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, gbc);

        // Champ téléphone
        gbc.gridy = 1;
        phoneField = new JTextField(20);
        phoneField.setToolTipText("Entrez votre numéro de téléphone");
        panel.add(phoneField, gbc);

        // Bouton Se connecter
        gbc.gridy = 2; gbc.gridwidth = 1; gbc.gridx = 0;
        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(e -> onLogin());
        panel.add(loginButton, gbc);

        // Bouton Retour
        gbc.gridx = 1;
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> onBack());
        panel.add(backButton, gbc);

        add(panel);
    }

    // Panel avec image de fond personnalisée
    private JPanel getJPanel() {
        Image bgImage = new ImageIcon(getClass().getResource("/rapizz/resources/inscription_client.png")).getImage();

        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    // Action de connexion
    private void onLogin() {
        String phone = phoneField.getText().trim();
        Client client = controller.getClient(phone);
        if (client != null) {
            new InterfaceClientController(controller.getPizzeria(), client);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Client introuvable ou numéro invalide !",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Action retour au menu principal
    private void onBack() {
        dispose();
        new InterfacePrincipaleView(new InterfacePrincipaleController(controller.getPizzeria())).setVisible(true);
    }
}