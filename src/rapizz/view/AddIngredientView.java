package rapizz.view;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import javax.swing.*;
import java.awt.*;

/**
 * Vue pour ajouter un ingrédient et sa quantité.
 */
public class AddIngredientView extends JFrame {
    private JTextField nameField;
    private JTextField qtyField;
    private FlatButton btnAdd;
    private FlatButton btnBack;

    public AddIngredientView() {
        super("Ajouter Ingrédient");
        FlatLightLaf.setup();
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();                             // taille adaptée au contenu
        setLocationRelativeTo(null);
        setVisible(true);                   // rend la fenêtre visible
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

        // Quantité
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Quantité :"), gbc);
        qtyField = new JTextField(5);
        gbc.gridx = 1;
        panel.add(qtyField, gbc);

        // Bouton Ajouter
        btnAdd = new FlatButton();
        btnAdd.setText("Ajouter");
        btnAdd.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAdd, gbc);

        // Bouton Retour
        btnBack = new FlatButton();
        btnBack.setText("Retour");
        btnBack.setPreferredSize(new Dimension(100, 30));
        gbc.gridy = 3;
        panel.add(btnBack, gbc);

        getContentPane().add(panel);        // ajout du panel au frame
    }

    // Getters pour le contrôleur
    public JTextField getNameField() { return nameField; }
    public JTextField getQtyField()  { return qtyField;  }
    public FlatButton getBtnAdd()    { return btnAdd;    }
    public FlatButton getBtnBack()   { return btnBack;   }
}
