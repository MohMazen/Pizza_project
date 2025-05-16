package rapizz.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GestionRapizzView extends JFrame {
  private JButton btnClients;
  private JButton btnRevenue;
  private JButton btnStock;
  private JButton btnAddIngredient;
  private JButton btnAddPizza;
  private JButton btnAddClient;
  private JButton btnBack;
  private JButton btnQuit;

  public GestionRapizzView() {
      super("RaPizz - Gestion");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(800, 600);
      setLocationRelativeTo(null);
      initComponents();
      setVisible(true);
  }

    private void initComponents() {
        // Chargement de l'image de fond
        JPanel backgroundPanel = getBackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(200, 0, 0, 0);

        // Panel pour les boutons principaux (2x3)
        JPanel mainButtonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        mainButtonPanel.setOpaque(false);

        btnClients = createButton("Voir Clients");
        btnRevenue = createButton("Chiffre d'Affaires");
        btnStock = createButton("Voir Stock");
        btnAddIngredient = createButton("Ajouter Ingrédient");
        btnAddPizza = createButton("Ajouter Pizza");
        btnAddClient = createButton("Ajouter Client");

        mainButtonPanel.add(btnClients);
        mainButtonPanel.add(btnRevenue);
        mainButtonPanel.add(btnStock);
        mainButtonPanel.add(btnAddIngredient);
        mainButtonPanel.add(btnAddPizza);
        mainButtonPanel.add(btnAddClient);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(mainButtonPanel, gbc);

        // Panel pour les boutons "Retour" et "Quitter"
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        bottomButtonPanel.setOpaque(false); // Rendre transparent pour voir l'image de fond

        btnBack = createButton("Retour à l'accueil");
        btnQuit = createButton("Quitter");

        bottomButtonPanel.add(btnBack);
        bottomButtonPanel.add(btnQuit);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        backgroundPanel.add(bottomButtonPanel, gbc);

        setContentPane(backgroundPanel);
    }

  private JPanel getBackgroundPanel() {
      Image bgImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rapizz/resources/Gestion_pizza.png"))).getImage();

      return new JPanel() {
          @Override
          protected void paintComponent(Graphics g) {
              super.paintComponent(g);
              if (bgImage != null) {
                  g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
              }
          }
      };
  }

  private JButton createButton(String text) {
      JButton btn = new JButton(text);
      btn.setPreferredSize(new Dimension(100, 100));
      btn.setBackground(new Color(255, 0, 0));
      btn.setForeground(Color.BLACK);
      btn.setFont(new Font("SansSerif", Font.BOLD, 16));
      btn.setFocusPainted(false);
      btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      return btn;
  }

  // Getters pour le contrôleur
  public JButton getBtnClients() { return btnClients; }
  public JButton getBtnRevenue() { return btnRevenue; }
  public JButton getBtnStock() { return btnStock; }
  public JButton getBtnAddIngredient() { return btnAddIngredient; }
  public JButton getBtnAddPizza() { return btnAddPizza; }
  public JButton getBtnAddClient() { return btnAddClient; }
  public JButton getBtnBack() { return btnBack; }
  public JButton getBtnQuit() { return btnQuit; }
}