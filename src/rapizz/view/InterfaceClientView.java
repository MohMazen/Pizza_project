// src/rapizz/view/InterfaceClientView.java
package rapizz.view;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import rapizz.controller.InterfaceClientController;

import javax.swing.*;
import java.awt.*;

/**
 * Vue pour l'espace client : commander, voir commandes, solde, ajouter solde, retour.
 */
public class InterfaceClientView extends JFrame {
    private final FlatButton btnOrderPizza;
    private final FlatButton btnViewOrders;
    private final FlatButton btnViewBalance;
    private final FlatButton btnAddBalance;
    private final FlatButton btnBack;

    public InterfaceClientView(InterfaceClientController controller) {
        super("RaPizz - Espace Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        FlatLightLaf.setup();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.CENTER;

        int row = 0;

        btnOrderPizza = new FlatButton();
        btnOrderPizza.setText("Commander une Pizza");
        styleButton(btnOrderPizza);
        btnOrderPizza.addActionListener(e -> controller.showOrderPizza());
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(btnOrderPizza, gbc);

        btnViewOrders = new FlatButton();
        btnViewOrders.setText("Voir mes commandes");
        styleButton(btnViewOrders);
        btnViewOrders.addActionListener(e -> controller.showOrders());
        gbc.gridy = row++;
        panel.add(btnViewOrders, gbc);

        btnViewBalance = new FlatButton();
        btnViewBalance.setText("Voir mon solde");
        styleButton(btnViewBalance);
        btnViewBalance.addActionListener(e -> controller.showBalance());
        gbc.gridy = row++;
        panel.add(btnViewBalance, gbc);

        btnAddBalance = new FlatButton();
        btnAddBalance.setText("Ajouter du solde");
        styleButton(btnAddBalance);
        btnAddBalance.addActionListener(e -> controller.addBalance());
        gbc.gridy = row++;
        panel.add(btnAddBalance, gbc);

        btnBack = new FlatButton();
        btnBack.setText("Retour à l'accueil");
        styleButton(btnBack);
        btnBack.addActionListener(e -> controller.returnToMain());
        gbc.gridy = row;
        panel.add(btnBack, gbc);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(FlatButton btn) {
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.setPreferredSize(new Dimension(160, 40));
    }

}
