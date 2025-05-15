package rapizz.view;

import rapizz.controller.InterfacePrincipaleController;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class InterfacePrincipaleView extends JFrame {
    private final InterfacePrincipaleController controller;

    public InterfacePrincipaleView(InterfacePrincipaleController controller) {
        this.controller = controller;
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

        // Panel central pour centrer les boutons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(250, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);

        // Panel pour Connexion et Gestion côte à côte
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        topButtons.setOpaque(false);

        JButton loginButton = new JButton("Connexion Client");
        styleButton(loginButton, new Dimension(250, 100), new Color(183, 31, 3));
        loginButton.addActionListener(e -> { controller.showLogin(); dispose(); });
        topButtons.add(loginButton);

        JButton gestionButton = new JButton("Gestion Pizzeria");
        styleButton(gestionButton, new Dimension(250, 100), new Color(183, 31, 3));
        gestionButton.addActionListener(e -> { controller.showGestion(); dispose(); });
        topButtons.add(gestionButton);

        gbc.gridy = 0;
        centerPanel.add(topButtons, gbc);

        // Bouton Quitter
        JButton quitButton = new JButton("Quitter");
        styleButton(quitButton, new Dimension(150, 40), new Color(183, 31, 3));
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
    private void styleButton(JButton button, Dimension size, Color bgColor) {
        button.setPreferredSize(size);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
}