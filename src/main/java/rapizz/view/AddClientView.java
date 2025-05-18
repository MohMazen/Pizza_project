package rapizz.view;

import javax.swing.*;
import java.awt.*;

public class AddClientView extends JFrame {
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JButton btnAdd;
    private JButton btnBack;

    // Constructeur
    public AddClientView() {
        super("Ajouter Client");
        initComponents();                // Initialise les composants graphiques
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();                         // Ajuste la taille à son contenu
        setLocationRelativeTo(null);    // Centre la fenêtre
    }

    // Initialise les champs et boutons
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Champ nom
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nom :"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Champ adresse
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Adresse :"), gbc);
        addressField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        // Champ téléphone
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Téléphone :"), gbc);
        phoneField = new JTextField(10);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // Bouton Ajouter
        btnAdd = new JButton("Ajouter");
        btnAdd.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAdd, gbc);

        // Bouton Retour
        btnBack = new JButton("Retour");
        btnBack.setPreferredSize(new Dimension(100, 30));
        gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnBack, gbc);

        getContentPane().add(panel);
    }

    // Getters pour accéder aux champs et boutons
    public JTextField getNameField()    { return nameField;    }
    public JTextField getAddressField() { return addressField; }
    public JTextField getPhoneField()   { return phoneField;   }
    public JButton getBtnAdd()          { return btnAdd;       }
    public JButton getBtnBack()         { return btnBack;      }
}