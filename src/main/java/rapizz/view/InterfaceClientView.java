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
        background.setBackground(Color.RED);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Ajout de l'image en haut
        try {
            JLabel topImage = new JLabel(new ImageIcon("rapizz/resources/top_image.png"));
            topImage.setHorizontalAlignment(SwingConstants.CENTER);
            background.add(topImage, BorderLayout.NORTH);
        } catch (Exception e) {
            System.err.println("Image du haut non trouvée : rapizz/resources/top_image.png");
        }

        // Panneau principal vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(300, 30, 20, 30));

        // Panneau 2x2 pour les 4 boutons
        JPanel gridButtons = new JPanel(new GridLayout(2, 2, 30, 20));
        gridButtons.setOpaque(false);

        btnOrderPizza = new FlatButton();
        btnOrderPizza.setText("Commander une pizza");
        styleButton(btnOrderPizza, Color.YELLOW);
        btnOrderPizza.addActionListener(e -> controller.showOrderPizza());
        gridButtons.add(btnOrderPizza);

        btnViewOrders = new FlatButton();
        btnViewOrders.setText("Voir mes commandes");
        styleButton(btnViewOrders, Color.YELLOW);
        btnViewOrders.addActionListener(e -> controller.showOrders());
        gridButtons.add(btnViewOrders);

        btnViewBalance = new FlatButton();
        btnViewBalance.setText("Voir mon solde");
        styleButton(btnViewBalance, Color.YELLOW);
        btnViewBalance.addActionListener(e -> controller.showBalance());
        gridButtons.add(btnViewBalance);

        btnAddBalance = new FlatButton();
        btnAddBalance.setText("Ajouter au solde");
        styleButton(btnAddBalance, Color.YELLOW);
        btnAddBalance.addActionListener(e -> controller.addBalance());
        gridButtons.add(btnAddBalance);

        mainPanel.add(gridButtons);

        // Espacement vertical entre la grille et le bouton retour
        mainPanel.add(Box.createVerticalStrut(30));

        // Bouton "Retour à l'accueil" centré
        btnBack = new FlatButton();
        btnBack.setText("Retour à l'accueil");
        styleButton(btnBack, Color.YELLOW);
        btnBack.addActionListener(e -> controller.returnToMain());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
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
    private void styleButton(FlatButton btn, Color bgColor) {
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.setPreferredSize(new Dimension(140, 35));
        btn.setBackground(bgColor);
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

    public static void main(String[] args) {
        InterfaceClientController controller = new InterfaceClientController(null, null);
        new InterfaceClientView(controller);
    }
}
