package rapizz.view;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import rapizz.controller.InterfaceClientController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Vue pour l'espace client avec disposition 2x2 des boutons et bouton retour centré dessous.
 */
public class InterfaceClientView extends JFrame {
    private FlatButton btnOrderPizza;
    private FlatButton btnViewOrders;
    private FlatButton btnViewBalance;
    private FlatButton btnAddBalance;
    private FlatButton btnBack;

    public InterfaceClientView(InterfaceClientController controller) {
        super("RaPizz - Espace Client");
        FlatLightLaf.setup();

        // Panneau de fond avec couleur rouge
        BackgroundPanel background = new BackgroundPanel(null); // Pas d'image de fond
        background.setBackground(new Color(140, 32, 32)); // Rouge en mode RGB
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Ajout de l'image en haut
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/rapizz/resources/pizzaiolo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            JLabel topImage = new JLabel(new ImageIcon(scaledImage));
            topImage.setHorizontalAlignment(SwingConstants.CENTER);
            background.add(topImage, BorderLayout.NORTH);
        } catch (Exception e) {
            System.err.println("Image du haut non trouvée : /pizzaiolo.png");
        }

        // Panneau principal vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 30, 20, 30));

        // Panneau 2x2 pour les 4 boutons
        JPanel gridButtons = new JPanel(new GridLayout(2, 2, 30, 20));
        gridButtons.setOpaque(false);

        btnOrderPizza = new FlatButton();
        btnOrderPizza.setText("Commander une pizza");
        styleButton(btnOrderPizza, 245, 191, 66); // Jaune
        btnOrderPizza.addActionListener(e -> {
            controller.showOrderPizza();
            dispose();
        });
        gridButtons.add(btnOrderPizza);

        btnViewOrders = new FlatButton();
        btnViewOrders.setText("Voir mes commandes");
        styleButton(btnViewOrders, 245, 191, 66); // Jaune
        btnViewOrders.addActionListener(e -> controller.showOrders());
        gridButtons.add(btnViewOrders);

        btnViewBalance = new FlatButton();
        btnViewBalance.setText("Voir mon solde");
        styleButton(btnViewBalance, 245, 191, 66); // Jaune
        btnViewBalance.addActionListener(e -> controller.showBalance());
        gridButtons.add(btnViewBalance);

        btnAddBalance = new FlatButton();
        btnAddBalance.setText("Ajouter au solde");
        styleButton(btnAddBalance, 245, 191, 66); // Jaune
        btnAddBalance.addActionListener(e -> controller.addBalance());
        gridButtons.add(btnAddBalance);

        mainPanel.add(gridButtons);

        // Espacement vertical entre la grille et le bouton retour
        mainPanel.add(Box.createVerticalStrut(30));

        // Bouton "Retour à l'accueil" centré
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        btnBack = new FlatButton();
        btnBack.setText("Retour à l'accueil");
        styleButton(btnBack, 245, 191, 66); // Jaune
        btnBack.addActionListener(e -> controller.returnToMain());
        bottomPanel.add(btnBack);
        mainPanel.add(bottomPanel);

        // Ajout au fond
        background.add(mainPanel, BorderLayout.CENTER);

        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true); // nécessaire !
    }

    /**
     * Applique un style uniforme aux boutons.
     */
    private void styleButton(FlatButton btn, int r, int g, int b) {
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.setPreferredSize(new Dimension(140, 35));
        btn.setBackground(new Color(r, g, b));
        btn.setForeground(Color.BLACK);
    }

    /**
     * Panneau personnalisé pour dessiner une image de fond.
     */
    private static class BackgroundPanel extends JPanel {
        private Image background;

        public BackgroundPanel(String imagePath) {
            if (imagePath != null) {
                try {
                    background = ImageIO.read(new File(imagePath));
                } catch (IOException e) {
                    System.err.println("Impossible de charger l'image de fond : " + imagePath);
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}