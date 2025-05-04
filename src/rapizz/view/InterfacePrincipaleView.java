package rapizz.view;

import rapizz.controller.InterfacePrincipaleController;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;

/**
 * Vue principale de l'application, avec image de fond redimensionnée et boutons centrés.
 */
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
        setSize(800, 600);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Chargement de l'image de fond
        String imagePath = "src/rapizz/pics/background_pizza.png";
        Image bgImage = new ImageIcon(imagePath).getImage();

        // Panel pour le fond
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setOpaque(true);

        // Titre
        JLabel titleLabel = new JLabel("Bienvenue chez RaPizz");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel central pour centrer les boutons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);

        // Panel pour Connexion et Gestion côte à côte
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        topButtons.setOpaque(false);
        loginButton = new FlatButton();
        loginButton.setText("Connexion");
        styleButton(loginButton, new Dimension(150, 40), new Color(255, 87, 34));
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(e -> { controller.showLogin(); dispose(); });
        topButtons.add(loginButton);
        gestionButton = new FlatButton();
        gestionButton.setText("Gestion Pizzeria");
        styleButton(gestionButton, new Dimension(150, 40), new Color(76, 175, 80));
        gestionButton.setForeground(Color.BLACK);
        gestionButton.addActionListener(e -> { controller.showGestion(); dispose(); });
        topButtons.add(gestionButton);

        gbc.gridy = 0;
        centerPanel.add(topButtons, gbc);

        // Bouton Quitter en dessous
        quitButton = new FlatButton();
        quitButton.setText("Quitter");
        styleButton(quitButton, new Dimension(150, 40), new Color(66, 66, 66));
        quitButton.setForeground(Color.BLACK);
        quitButton.addActionListener(e -> controller.exitApplication());
        gbc.gridy = 1;
        centerPanel.add(quitButton, gbc);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(backgroundPanel);
        // Redessiner le fond au redimensionnement
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                backgroundPanel.repaint();
            }
        });
    }

    // Méthode utilitaire pour styliser un bouton
    private void styleButton(FlatButton button, Dimension size, Color bgColor) {
        button.setPreferredSize(size);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBorderPainted(false);
    }
}
