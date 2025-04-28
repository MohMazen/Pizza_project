import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InterfaceRaPizz extends JFrame {

    private JButton btnAfficherClients;
    private JButton btnAfficherSoldeTotal;
    private JButton btnVoirInventaire;
    private JButton btnAjouterIngredient;
    private JButton btnQuitter;

    private Point_Pizzaria pizzaria;

    public InterfaceRaPizz(Point_Pizzaria pz) {
        this.pizzaria = pz;

        setTitle("RaPizz - Système de gestion");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel("RaPizza", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logoLabel.setForeground(new Color(220, 90, 60));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainPanel.add(logoLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setLayout(new GridLayout(5, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        btnAfficherClients = new JButton("Afficher les Clients");
        btnAfficherSoldeTotal = new JButton("Afficher le solde total");
        btnVoirInventaire = new JButton("Voir l'inventaire");
        btnAjouterIngredient = new JButton("Ajouter un ingrédient");
        btnQuitter = new JButton("Retour à l'accueil");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        JButton[] buttons = {
                btnAfficherClients,
                btnAfficherSoldeTotal,
                btnVoirInventaire,
                btnAjouterIngredient,
                btnQuitter
        };
        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(30, 30, 30));
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            buttonPanel.add(btn);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        setupActions();
    }

    private void setupActions() {

        btnAfficherClients.addActionListener(e -> {
            String infosClients = pizzaria.afficherClients();
            JOptionPane.showMessageDialog(this, infosClients, "Clients enregistrés", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAfficherSoldeTotal.addActionListener(e -> {
            int total = pizzaria.getSoldeTotalClients();
            JOptionPane.showMessageDialog(this, "Solde total de tous les clients : " + total + " €", "Solde global", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVoirInventaire.addActionListener(e -> {
            String inventaire = pizzaria.afficherQuantiteIngredients();
            JOptionPane.showMessageDialog(this, inventaire, "Inventaire", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAjouterIngredient.addActionListener(e -> {
            String nomIng = JOptionPane.showInputDialog("Nom de l'ingrédient à ajouter ou augmenter :");
            String qteStr = JOptionPane.showInputDialog("Quantité à ajouter :");

            try {
                int qte = Integer.parseInt(qteStr);
                boolean trouvé = false;

                for (Ingredient ing : pizzaria.getList_ing()) {
                    if (ing.getNom().equalsIgnoreCase(nomIng)) {
                        pizzaria.ajouterQuantiteIngredient(nomIng, qte);
                        JOptionPane.showMessageDialog(this, "Quantité mise à jour !");
                        trouvé = true;
                        break;
                    }
                }

                if (!trouvé) {
                    Ingredient newIng = new Ingredient(nomIng, qte);
                    pizzaria.ajouterIngredient(newIng);
                    JOptionPane.showMessageDialog(this, "Nouvel ingrédient ajouté !");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantité invalide.");
            }
        });

        btnQuitter.addActionListener(e -> {
            dispose();
            new InterfacePrincipale(pizzaria);
        });

    }
}
