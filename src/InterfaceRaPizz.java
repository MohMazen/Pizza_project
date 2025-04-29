import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InterfaceRaPizz extends JFrame {

    private JButton btnAfficherClients;
    private JButton btnAfficherTresorerie;
    private JButton btnVoirInventaire;
    private JButton btnAjouterIngredient;
    private JButton btnAjouterPizza;
    private JButton btnAjouterClient;    // nouveau bouton
    private JButton btnAccueil;

    private Point_Pizzaria pizzaria;

    public InterfaceRaPizz(Point_Pizzaria pz) {
        this.pizzaria = pz;

        setTitle("RaPizz - Système de gestion");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 250));

        JLabel logoLabel = new JLabel("RaPizza", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logoLabel.setForeground(new Color(220, 90, 60));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainPanel.add(logoLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 15, 15));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        btnAfficherClients      = new JButton("Afficher les Clients");
        btnAfficherTresorerie   = new JButton("Afficher la trésorerie");
        btnVoirInventaire       = new JButton("Voir l'inventaire");
        btnAjouterIngredient    = new JButton("Ajouter un ingrédient");
        btnAjouterPizza         = new JButton("Ajouter une pizza");
        btnAjouterClient        = new JButton("Ajouter un client");  // instanciation
        btnAccueil              = new JButton("Accueil");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{
                btnAfficherClients,
                btnAfficherTresorerie,
                btnVoirInventaire,
                btnAjouterIngredient,
                btnAjouterPizza,
                btnAjouterClient,   // ajout dans le menu
                btnAccueil
        }) {
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(30, 30, 30));
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            buttonPanel.add(btn);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        setupActions();
    }

    private void setupActions() {
        btnAfficherClients.addActionListener(e -> {
            String infos = pizzaria.afficherClients();
            JOptionPane.showMessageDialog(this, infos, "Clients", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAfficherTresorerie.addActionListener(e -> {
            double treso = pizzaria.getTresorerie();
            JOptionPane.showMessageDialog(
                    this,
                    "Trésorerie actuelle : " + String.format("%.2f", treso) + " €",
                    "Trésorerie",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnVoirInventaire.addActionListener(e -> {
            String inv = pizzaria.afficherQuantiteIngredients();
            JOptionPane.showMessageDialog(this, inv, "Inventaire", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAjouterIngredient.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nom de l'ingrédient :");
            String qte = JOptionPane.showInputDialog(this, "Quantité à ajouter :");
            try {
                int quant = Integer.parseInt(qte);
                boolean trouve = false;
                for (Ingredient ing : pizzaria.getList_ing()) {
                    if (ing.getNom().equalsIgnoreCase(nom)) {
                        pizzaria.ajouterQuantiteIngredient(nom, quant);
                        trouve = true;
                        break;
                    }
                }
                if (!trouve) {
                    pizzaria.ajouterIngredient(new Ingredient(nom, quant));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantité invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAjouterPizza.addActionListener(e ->
                SwingUtilities.invokeLater(this::openAddPizzaInterface)
        );

        btnAjouterClient.addActionListener(e ->
                SwingUtilities.invokeLater(this::openAddClientInterface)
        );
        btnAccueil.addActionListener(e -> {
            dispose();
            new InterfacePrincipale(pizzaria);
        });
    }

    private void openAddPizzaInterface() {
        // ... code existant pour ajouter une pizza ...
    }

    private void openAddClientInterface() {
        JFrame frame = new JFrame("Ajouter un nouveau client");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(this);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Nom et champ
        JPanel nomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        nomPanel.add(new JLabel("Nom :"));
        JTextField nomField = new JTextField();
        nomField.setPreferredSize(new Dimension(300, 30));
        nomPanel.add(nomField);
        form.add(nomPanel);

        form.add(Box.createRigidArea(new Dimension(0, 10)));

        // Adresse et champ
        JPanel adrPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        adrPanel.add(new JLabel("Adresse :"));
        JTextField adrField = new JTextField();
        adrField.setPreferredSize(new Dimension(300, 30));
        adrPanel.add(adrField);
        form.add(adrPanel);

        form.add(Box.createRigidArea(new Dimension(0, 10)));

        // Téléphone et champ
        JPanel telPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        telPanel.add(new JLabel("Téléphone :"));
        JTextField telField = new JTextField();
        telField.setPreferredSize(new Dimension(200, 30));
        telPanel.add(telField);
        form.add(telPanel);

        form.add(Box.createRigidArea(new Dimension(0, 10)));

        // Solde initial et champ
        JPanel soldePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        soldePanel.add(new JLabel("Solde initial (€) :"));
        JTextField soldeField = new JTextField();
        soldeField.setPreferredSize(new Dimension(100, 30));
        soldePanel.add(soldeField);
        form.add(soldePanel);

        form.add(Box.createRigidArea(new Dimension(0, 20)));

        // Boutons Ajouter / Accueil
        JPanel btns = new JPanel();
        JButton ajouter = new JButton("Ajouter");
        JButton accueil = new JButton("Accueil");
        btns.add(ajouter);
        btns.add(accueil);

        frame.add(form, BorderLayout.CENTER);
        frame.add(btns, BorderLayout.SOUTH);

        ajouter.addActionListener(e -> {
            String nom = nomField.getText().trim();
            String adr = adrField.getText().trim();
            String tel = telField.getText().trim();
            String soldeStr = soldeField.getText().trim();
            if (nom.isEmpty() || adr.isEmpty() || tel.isEmpty() || soldeStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Tous les champs sont requis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int solde;
            try {
                solde = Integer.parseInt(soldeStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Solde invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int newId = pizzaria.getClients().size() + 1;
            Client client = new Client(newId, nom, adr, tel, solde, pizzaria);
            pizzaria.ajouterClient(client);
            JOptionPane.showMessageDialog(frame, "Client \"" + nom + "\" ajouté avec succès !");
            frame.dispose();
        });

        accueil.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}
