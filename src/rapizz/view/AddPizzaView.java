// File: src/rapizz/view/AddPizzaView.java
package rapizz.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;

/**
 * Vue pour ajouter une nouvelle pizza avec sélection d'ingrédients.
 */
public class AddPizzaView extends JFrame {
    private JTextField nameField;
    private JTextField priceField;
    private JPanel ingredientsPanel;
    private FlatButton btnAdd;
    private FlatButton btnBack;

    public AddPizzaView() {
        super("Ajouter Pizza");
        FlatLightLaf.setup();
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Informations pizza
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Nom :"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Prix de base :"), gbc);
        priceField = new JTextField(5);
        gbc.gridx = 1;
        inputPanel.add(priceField, gbc);

        main.add(inputPanel, BorderLayout.NORTH);

        // Checklist ingrédients
        ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(ingredientsPanel);
        scroll.setPreferredSize(new Dimension(200, 150));
        main.add(new JLabel("Sélectionnez les ingrédients :"), BorderLayout.CENTER);
        main.add(scroll, BorderLayout.SOUTH);

        // Boutons
        JPanel buttons = new JPanel();
        btnAdd = new FlatButton();
        btnAdd.setText("Ajouter");
        btnBack = new FlatButton();
        btnBack.setText("Retour");
        buttons.add(btnAdd);
        buttons.add(btnBack);

        getContentPane().add(main, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
    }

    /**
     * Remplit la checklist d'ingrédients.
     */
    public void setIngredientsOptions(List<String> ingredients) {
        ingredientsPanel.removeAll();
        for (String ing : ingredients) {
            JCheckBox cb = new JCheckBox(ing);
            ingredientsPanel.add(cb);
        }
        ingredientsPanel.revalidate();
        ingredientsPanel.repaint();
        pack();
    }

    /**
     * Retourne la liste des ingrédients sélectionnés.
     */
    public java.util.List<String> getSelectedIngredients() {
        java.util.List<String> selected = new java.util.ArrayList<>();
        for (Component c : ingredientsPanel.getComponents()) {
            if (c instanceof JCheckBox && ((JCheckBox)c).isSelected()) {
                selected.add(((JCheckBox)c).getText());
            }
        }
        return selected;
    }

    public String getPizzaName() { return nameField.getText().trim(); }
    public String getPriceText() { return priceField.getText().trim(); }
    public FlatButton getBtnAdd() { return btnAdd; }
    public FlatButton getBtnBack() { return btnBack; }
}
