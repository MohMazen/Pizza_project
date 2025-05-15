package rapizz.view;

import javax.swing.*;
import java.awt.*;

/**
 * Vue pour ajouter un client (nom, adresse, téléphone).
 */
public class AddClientView extends JFrame {
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JButton btnAdd;
    private JButton btnBack;

    public AddClientView() {
        super("Ajouter Client");
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();                             // taille adaptée au contenu
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nom
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nom :"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Adresse
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Adresse :"), gbc);
        addressField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        // Téléphone
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

        getContentPane().add(panel);        // ajout du panel au frame
    }

    // Getters pour le contrôleur
    public JTextField getNameField()    { return nameField;    }
    public JTextField getAddressField() { return addressField; }
    public JTextField getPhoneField()   { return phoneField;   }
    public JButton getBtnAdd()          { return btnAdd;       }
    public JButton getBtnBack()         { return btnBack;      }
}