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
 * Vue pour l'espace client avec disposition 2x2 boutons et image de fond.
 */
public class InterfaceClientView extends JFrame {
    private final FlatButton btnOrderPizza;
    private final FlatButton btnViewOrders;
    private final FlatButton btnViewBalance;
    private final FlatButton btnAddBalance;
    private final FlatButton btnBack;

    public InterfaceClientView(InterfaceClientController controller) {
        super("RaPizz - Espace Client");
        FlatLightLaf.setup();

        // Panneau de fond avec image
        BackgroundPanel background = new BackgroundPanel("rapizz/resources/Background_cilent.png");
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Augmente les marges
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Tableau 2x2 pour les 4 premiers boutons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 30, 30)); // Augmente l'espacement
        buttonPanel.setOpaque(false);

        btnOrderPizza = new FlatButton();
        btnOrderPizza.setText("Commander une pizza");
        styleButton(btnOrderPizza);
        btnOrderPizza.addActionListener(e -> controller.showOrderPizza());
        buttonPanel.add(btnOrderPizza);

        btnViewOrders = new FlatButton();
        btnViewOrders.setText("Voir mes commandes");
        styleButton(btnViewOrders);
        btnViewOrders.addActionListener(e -> controller.showOrders());
        buttonPanel.add(btnViewOrders);

        btnViewBalance = new FlatButton();
        btnViewBalance.setText("Voir mon solde");
        styleButton(btnViewBalance);
        btnViewBalance.addActionListener(e -> controller.showBalance());
        buttonPanel.add(btnViewBalance);

        btnAddBalance = new FlatButton();
        btnAddBalance.setText("Ajouter au solde");
        styleButton(btnAddBalance);
        btnAddBalance.addActionListener(e -> controller.addBalance());
        buttonPanel.add(btnAddBalance);

        background.add(buttonPanel, gbc);

        // Bouton retour centré en bas
        gbc.gridy = 1;
        gbc.insets = new Insets(40, 20, 50, 20); // Augmente les marges pour le bouton retour
        btnBack = new FlatButton();
        btnBack.setText("Retour à l'accueil");
        styleButton(btnBack);
        btnBack.addActionListener(e -> controller.returnToMain());
        background.add(btnBack, gbc);

        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Applique un style uniforme aux boutons.
     */
    private void styleButton(FlatButton btn) {
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.setPreferredSize(new Dimension(160, 40));
    }

    /**
     * Panneau personnalisé pour dessiner une image de fond.
     */
    private static class BackgroundPanel extends JPanel {
        private Image background;

        public BackgroundPanel(String imagePath) {
            try {
                background = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.err.println("Impossible de charger l'image de fond : " + imagePath);
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
