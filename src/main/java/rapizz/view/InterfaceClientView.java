// src/rapizz/view/InterfaceClientView.java
package rapizz.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InterfaceClientView extends JFrame {
    private JButton btnOrderPizza;
    private JButton btnViewOrders;
    private JButton btnViewBalance;
    private JButton btnAddBalance;
    private JButton btnBack;

    public InterfaceClientView() {
        super("RaPizz - Espace Client");

        // Fond
        BackgroundPanel background = new BackgroundPanel("src/main/resources/rapizz/resources/Background_pizza.png");
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Conteneur principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(300, 30, 20, 30));

        // Grille 2×2
        JPanel grid = new JPanel(new GridLayout(2, 2, 30, 20));
        grid.setOpaque(false);

        btnOrderPizza = new JButton("Commander une pizza");
        styleButton(btnOrderPizza);
        grid.add(btnOrderPizza);

        btnViewOrders = new JButton("Voir mes commandes");
        styleButton(btnViewOrders);
        grid.add(btnViewOrders);

        btnViewBalance = new JButton("Voir mon solde");
        styleButton(btnViewBalance);
        grid.add(btnViewBalance);

        btnAddBalance = new JButton("Ajouter au solde");
        styleButton(btnAddBalance);
        grid.add(btnAddBalance);

        mainPanel.add(grid);
        mainPanel.add(Box.createVerticalStrut(30));

        // Bouton retour
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);
        btnBack = new JButton("Accueil");
        styleButton(btnBack);
        bottom.add(btnBack);
        mainPanel.add(bottom);

        background.add(mainPanel, BorderLayout.CENTER);

        pack();
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void styleButton(JButton b) {
        b.setPreferredSize(new Dimension(140,35));
        b.setBackground(new Color(245,191,66));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("SansSerif", Font.BOLD, 18));
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
    }

    // === Getters pour le contrôleur ===
    public JButton getBtnOrderPizza()  { return btnOrderPizza;  }
    public JButton getBtnViewOrders()  { return btnViewOrders;  }
    public JButton getBtnViewBalance() { return btnViewBalance; }
    public JButton getBtnAddBalance()  { return btnAddBalance;  }
    public JButton getBtnBack()        { return btnBack;       }

    // Panneau de fond
    private static class BackgroundPanel extends JPanel {
        private Image background;
        public BackgroundPanel(String path) {
            try { background = ImageIO.read(new File(path)); }
            catch (IOException e) { System.err.println("Fond introuvable : "+path); }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background!=null) g.drawImage(background,0,0,getWidth(),getHeight(),this);
        }
    }
}
