import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InterfaceClient {
    private Point_Pizzaria pointPizzaria;
    private ImageIcon logoIcon;

    public InterfaceClient(Point_Pizzaria p) {
        this.pointPizzaria = p;
        // Charger et redimensionner le logo
        logoIcon = new ImageIcon("C:/path/to/logo.png");
        logoIcon = redimensionnerImage(logoIcon, 80, 80);
        afficherConnexion();
    }

    private ImageIcon redimensionnerImage(ImageIcon icon, int largeur, int hauteur) {
        Image image = icon.getImage();
        Image imgRedim = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        return new ImageIcon(imgRedim);
    }

    // Applique un style uniforme aux boutons
    private void styliserBouton(JButton btn, int w, int h) {
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
    }

    public void afficherConnexion() {
        JFrame frame = new JFrame("Connexion Client");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        JLabel titre = new JLabel("Bienvenue chez RaPizz", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        top.add(titre, BorderLayout.NORTH);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(logoLabel, BorderLayout.CENTER);
        frame.add(top, BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JPanel champs = new JPanel(new GridLayout(2, 1, 10, 10));
        champs.setBackground(Color.WHITE);
        JTextField phoneField = new JTextField();
        champs.add(new JLabel("Téléphone : "));
        champs.add(phoneField);
        centre.add(champs, BorderLayout.CENTER);
        frame.add(centre, BorderLayout.CENTER);

        JButton btnConnexion = new JButton("Se connecter");
        styliserBouton(btnConnexion, 120, 30);
        btnConnexion.addActionListener(e -> {
            String phone = phoneField.getText().trim();
            Client clientTrouve = null;
            for (Client c : pointPizzaria.getClients()) {
                if (c.getTelephone().equals(phone)) { clientTrouve = c; break; }
            }
            if (clientTrouve != null) {
                frame.dispose(); afficherPagePrincipale(clientTrouve);
            } else {
                JOptionPane.showMessageDialog(frame, "Client non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel bas = new JPanel();
        bas.setBackground(Color.WHITE);
        bas.add(btnConnexion);
        frame.add(bas, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void afficherPagePrincipale(Client client) {
        JFrame frame = new JFrame("Page principale - Client");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Logo en haut
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(logoLabel, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(4, 1, 15, 15));
        centre.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        centre.setBackground(Color.WHITE);

        JButton btnCommander     = new JButton("Commander une pizza");
        JButton btnVoirCommandes = new JButton("Voir mes commandes");
        JButton btnVoirSolde     = new JButton("Voir mon solde");
        JButton btnAjouterSolde  = new JButton("Ajouter du solde");
        for (JButton btn : Arrays.asList(btnCommander, btnVoirCommandes, btnVoirSolde, btnAjouterSolde)) {
            styliserBouton(btn, 140, 40);
        }

        btnCommander.addActionListener(e -> { frame.dispose(); afficherInterfaceCommande(client); });
        btnVoirCommandes.addActionListener(e -> { frame.dispose(); afficherMenuClient(client); });
        btnVoirSolde.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "Votre solde actuel est de : " + client.getSold() + " €","Solde", JOptionPane.INFORMATION_MESSAGE));
        btnAjouterSolde.addActionListener(e -> {
            String montantStr = JOptionPane.showInputDialog(frame, "Montant à ajouter :");
            try { int montant = Integer.parseInt(montantStr);
                if (montant > 0) { client.approvisionner(montant);
                    JOptionPane.showMessageDialog(frame,
                            "Montant ajouté ! Nouveau solde : " + client.getSold() + " €"); }
                else JOptionPane.showMessageDialog(frame, "Veuillez entrer un montant positif.","Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Montant invalide.","Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        centre.add(btnCommander);
        centre.add(btnVoirCommandes);
        centre.add(btnVoirSolde);
        centre.add(btnAjouterSolde);
        frame.add(centre, BorderLayout.CENTER);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
    }

    public void afficherInterfaceCommande(Client client) {
        JFrame commandeFrame = new JFrame("Nouvelle Commande");
        commandeFrame.setSize(800, 550);
        commandeFrame.setLocationRelativeTo(null);
        commandeFrame.setLayout(new BorderLayout());
        commandeFrame.getContentPane().setBackground(Color.WHITE);

        // Top: logo + action buttons
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(Color.WHITE);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topContainer.add(logoLabel, BorderLayout.NORTH);

        // Stockage des lignes de commande
        java.util.List<Ligne_Com> lignes = new ArrayList<>();

        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.WHITE);
        JButton commanderBtn = new JButton("Commander");
        JButton retourBtn    = new JButton("Retour");
        styliserBouton(commanderBtn, 100, 28);
        styliserBouton(retourBtn,    100, 28);
        actionPanel.add(commanderBtn);
        actionPanel.add(retourBtn);
        topContainer.add(actionPanel, BorderLayout.SOUTH);
        commandeFrame.add(topContainer, BorderLayout.NORTH);

        // Center: récapitulatif de la commande avec prix par article
        final JTextArea recap = new JTextArea();
        recap.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recap);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Récapitulatif"));
        commandeFrame.add(scrollPane, BorderLayout.CENTER);

        // South: formulaire d'ajout de pizza
        JPanel formPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.setBackground(Color.WHITE);
        JComboBox<String> menuPizza  = new JComboBox<>();
        JComboBox<String> menuTaille = new JComboBox<>(new String[]{"naine","humaine","ogresse"});
        JTextField quantiteField     = new JTextField("1");
        JButton ajouterPizzaBtn      = new JButton("Ajouter");
        JCheckBox rapideChk          = new JCheckBox("Livraison rapide");
        styliserBouton(ajouterPizzaBtn, 100, 28);

        for (Pizza p : pointPizzaria.getMenu()) {
            if (!p.getNom().equalsIgnoreCase("Pizza Gratuite")) {
                menuPizza.addItem(p.getNom());
            }
        }

        // Action d'ajout
        ajouterPizzaBtn.addActionListener(e -> {
            String nom = (String) menuPizza.getSelectedItem();
            String taille = (String) menuTaille.getSelectedItem();
            int qte;
            try { qte = Integer.parseInt(quantiteField.getText()); }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(null, "Quantité invalide"); return; }

            Pizza base = pointPizzaria.getPizzaParNom(nom);
            if (base == null) { JOptionPane.showMessageDialog(null, "Pizza introuvable"); return; }

            double unitPrice = base.getPrixBase();
            switch (taille.toLowerCase()) {
                case "naine":  unitPrice *= 2.0/3.0; break;
                case "ogresse": unitPrice *= 4.0/3.0; break;
                default: break;
            }
            double lineTotal = unitPrice * qte;

            // Création et stockage de la ligne
            Commande temp = new Commande(0, new Date());
            Ligne_Com ligne = new Ligne_Com(temp.Num_com, qte, temp,
                    new Pizza(nom, taille, base.getPrixBase(), pointPizzaria));
            lignes.add(ligne);

            recap.append(qte + " x " + nom + " (" + taille + ") = "
                            + String.format("%.2f", lineTotal) + " € \n");
        });

        formPanel.add(menuPizza);
        formPanel.add(menuTaille);
        formPanel.add(quantiteField);
        formPanel.add(ajouterPizzaBtn);
        formPanel.add(rapideChk);
        commandeFrame.add(formPanel, BorderLayout.SOUTH);

        // Actions pour commander et retour
        commanderBtn.addActionListener(e -> {
            if (lignes.isEmpty()) {
                JOptionPane.showMessageDialog(commandeFrame, "Veuillez ajouter au moins une pizza.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Commande cmd = new Commande(new Random().nextInt(100000), new Date());
            for (Ligne_Com l : lignes) cmd.ajouterLigneCommande(l);
            cmd.ajouterClient(client);
            if (rapideChk.isSelected()) cmd.setSurcharge(2.0);
            Livreur choisi = pointPizzaria.getLivreur().stream()
                    .filter(lv -> rapideChk.isSelected() ? lv.getType_Vec().equalsIgnoreCase("moto")
                            : lv.getType_Vec().equalsIgnoreCase("voiture"))
                    .findFirst().orElse(pointPizzaria.getLivreur().isEmpty() ? null : pointPizzaria.getLivreur().get(0));
            if (choisi != null) cmd.setLivreur(choisi);
            client.ajouterCommande(cmd);
            client.ajouterPizzaGratuiteSiEligibilite();
            cmd.validerCommande();
            JOptionPane.showMessageDialog(commandeFrame, "Commande validée !", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            commandeFrame.dispose();
            afficherPagePrincipale(client);
        });
        retourBtn.addActionListener(e -> {
            commandeFrame.dispose();
            afficherPagePrincipale(client);
        });

        commandeFrame.setVisible(true);
    }

    public void afficherMenuClient(Client client) {
        JFrame menuFrame = new JFrame("Mes commandes");
        menuFrame.setSize(500, 400);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(new BorderLayout());
        menuFrame.getContentPane().setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuFrame.add(logoLabel, BorderLayout.NORTH);

        JTextArea commandesArea = new JTextArea(); commandesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(commandesArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historique des commandes"));

        for (Commande c : client.getCommandes()) {
            commandesArea.append(c.afficherDetailsCommande() + "\n--------------------------\n");
        }

        JButton retour = new JButton("Retour"); styliserBouton(retour, 100, 28);
        retour.addActionListener(e -> { menuFrame.dispose(); afficherPagePrincipale(client); });
        JPanel basMenu = new JPanel(); basMenu.setBackground(Color.WHITE); basMenu.add(retour);

        menuFrame.add(scrollPane, BorderLayout.CENTER);
        menuFrame.add(basMenu, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
    }
}
