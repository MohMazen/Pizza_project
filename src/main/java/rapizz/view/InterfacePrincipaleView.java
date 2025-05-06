package rapizz.view;

import rapizz.controller.InterfacePrincipaleController;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;

/**
 * Vue principale de l'application, avec image de fond redimensionnée et boutons centrés.
 */
public class InterfacePrincipaleView extends JFrame {
    private final InterfacePrincipaleController controller;

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
        JPanel backgroundPanel = getJPanel();

        // Chargement du logo via le classpath
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rapizz/resources/logo.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(300, 160, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        backgroundPanel.add(logoLabel, BorderLayout.NORTH);

        // Panel central pour centrer les boutons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 90, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);

        // Panel pour Connexion et Gestion côte à côte
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        topButtons.setOpaque(false);

        FlatButton loginButton = new FlatButton();
        loginButton.setText("Connexion Client");
        styleButton(loginButton, new Dimension(150, 40), new Color(255, 34, 34));
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(e -> { controller.showLogin(); dispose(); });
        topButtons.add(loginButton);

        FlatButton gestionButton = new FlatButton();
        gestionButton.setText("Gestion Pizzeria");
        styleButton(gestionButton, new Dimension(150, 40), new Color(76, 175, 80));
        gestionButton.setForeground(Color.BLACK);
        gestionButton.addActionListener(e -> { controller.showGestion(); dispose(); });
        topButtons.add(gestionButton);

        gbc.gridy = 0;
        centerPanel.add(topButtons, gbc);

        // Bouton Quitter
        FlatButton quitButton = new FlatButton();
        quitButton.setText("Quitter");
        styleButton(quitButton, new Dimension(150, 40), new Color(255, 255, 255));
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

    private JPanel getJPanel() {
        Image bgImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rapizz/resources/Background_pizza.png"))).getImage();

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
        return backgroundPanel;
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
