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

    public LoginView(LoginController controller) {
        this.controller = controller;
        FlatLightLaf.setup();
        setTitle("RaPizz - Connexion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = getJPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel label = new JLabel("Connexion Client");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label, gbc);

        gbc.gridy = 1;
        phoneField = new JTextField(15);
        phoneField.setToolTipText("Entrez votre numéro de téléphone");
        panel.add(phoneField, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(e -> onLogin());
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> onBack());
        panel.add(backButton, gbc);

        add(panel);
    }

    private JPanel getJPanel() {
        // Chargement de l’image via le classpath
        Image bgImage = new ImageIcon(getClass().getResource("/rapizz/resources/Background_pizza.png")).getImage();

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

    private void onLogin() {
        String phone = phoneField.getText().trim();
        Client client = controller.getClient(phone);
        if (client != null) {
            new InterfaceClientView(new InterfaceClientController(controller.getPizzeria(), client)).setVisible(true);
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

    private void onBack() {
        dispose();
        new InterfacePrincipaleView(new InterfacePrincipaleController(controller.getPizzeria())).setVisible(true);
    }
}