// File: src/rapizz/view/InterfacePrincipaleView.java
package rapizz.view;

import rapizz.controller.InterfacePrincipaleController;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;

public class InterfacePrincipaleView extends JFrame {
    private FlatButton loginButton;
    private FlatButton gestionButton;
    private FlatButton quitButton;
    private InterfacePrincipaleController controller;

    public InterfacePrincipaleView(InterfacePrincipaleController controller) {
        this.controller = controller;
        FlatLightLaf.setup();
        setTitle("RaPizz - Menu Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;

        // Bouton Connexion Client
        gbc.gridx = 0; gbc.gridy = 0;
        loginButton = new FlatButton();
        loginButton.setText("Connexion Client");
        loginButton.setPreferredSize(new Dimension(160, 50));
        loginButton.addActionListener(e -> {
            controller.showLogin();
            dispose();
        });
        panel.add(loginButton, gbc);

        // Bouton Gestion
        gbc.gridy = 1;
        gestionButton = new FlatButton();
        gestionButton.setText("Gestion");
        gestionButton.setPreferredSize(new Dimension(160, 50));
        gestionButton.addActionListener(e -> {
            controller.showGestion();
            dispose();
        });
        panel.add(gestionButton, gbc);

        // Bouton Quitter
        gbc.gridy = 2;
        quitButton = new FlatButton();
        quitButton.setText("Quitter");
        quitButton.setPreferredSize(new Dimension(160, 50));
        quitButton.addActionListener(e -> controller.exitApplication());
        panel.add(quitButton, gbc);

        add(panel);
    }
}
