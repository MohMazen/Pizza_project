// --- view/OrderPizzaView.java ---
package rapizz.view;

import rapizz.model.Point_Pizzaria;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class OrderPizzaView extends JFrame {
    private JComboBox<String> cbPizza;
    private JComboBox<String> cbSize;
    private JTextField qtyField;
    private JCheckBox chkMoto;
    private JButton btnChoose;
    private JButton btnQuit;
    private JButton btnOrder;
    private JTextArea txtSummary;

    public OrderPizzaView(Point_Pizzaria model) {
        super("Commander une Pizza");
        FlatLightLaf.setup();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);

        initComponents(model);
        setVisible(true);
    }

    private void initComponents(Point_Pizzaria model) {
        setLayout(new BorderLayout(10, 10));

        // ← PANNEAU GAUCHE : tous les contrôles
        JPanel left = new JPanel(new GridBagLayout());
        left.setBorder(BorderFactory.createTitledBorder("Options de commande"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int y = 0;

        // Pizza
        gbc.gridx = 0; gbc.gridy = y;
        left.add(new JLabel("Pizza :"), gbc);
        gbc.gridx = 1;
        cbPizza = new JComboBox<>(model.getNomsPizzas());
        left.add(cbPizza, gbc);

        // Taille
        gbc.gridx = 0; gbc.gridy = ++y;
        left.add(new JLabel("Taille :"), gbc);
        gbc.gridx = 1;
        cbSize = new JComboBox<>(new String[]{"naine", "humaine", "ogresse"});
        left.add(cbSize, gbc);

        // Quantité
        gbc.gridx = 0; gbc.gridy = ++y;
        left.add(new JLabel("Quantité :"), gbc);
        gbc.gridx = 1;
        qtyField = new JTextField(5);
        left.add(qtyField, gbc);

        // Checkbox moto
        gbc.gridx = 0; gbc.gridy = ++y; gbc.gridwidth = 2;
        chkMoto = new JCheckBox("Livraison en moto (+2€)");
        left.add(chkMoto, gbc);
        gbc.gridwidth = 1;

        // Bouton Choisir
        gbc.gridx = 0; gbc.gridy = ++y;
        btnChoose = new JButton("Calculer");
        left.add(btnChoose, gbc);

        // Bouton Quitter
        gbc.gridx = 1;
        btnQuit = new JButton("Annuler");
        left.add(btnQuit, gbc);

        add(left, BorderLayout.WEST);

        // ← PANNEAU DROIT : résumé + commander
        JPanel right = new JPanel(new BorderLayout(10, 10));
        right.setBorder(BorderFactory.createTitledBorder("Résumé de la commande"));

        txtSummary = new JTextArea();
        txtSummary.setEditable(false);
        txtSummary.setLineWrap(true);
        txtSummary.setWrapStyleWord(true);
        right.add(new JScrollPane(txtSummary), BorderLayout.CENTER);

        btnOrder = new JButton("Commander");
        btnOrder.setEnabled(false);
        JPanel pnlBtn = new JPanel();
        pnlBtn.add(btnOrder);
        right.add(pnlBtn, BorderLayout.SOUTH);

        add(right, BorderLayout.CENTER);
    }

    // Getters pour le controller
    public JComboBox<String> getCbPizza()   { return cbPizza;   }
    public JComboBox<String> getCbSize()    { return cbSize;    }
    public JTextField getQtyField()         { return qtyField;  }
    public JCheckBox getChkMoto()           { return chkMoto;   }
    public JButton getBtnChoose()           { return btnChoose; }
    public JButton getBtnQuit()             { return btnQuit;   }
    public JButton getBtnOrder()            { return btnOrder;  }
    public JTextArea getTxtSummary()        { return txtSummary;}
}
