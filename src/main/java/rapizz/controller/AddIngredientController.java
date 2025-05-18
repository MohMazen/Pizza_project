package rapizz.controller;

    import rapizz.model.Point_Pizzaria;
    import rapizz.view.AddIngredientView;
    import rapizz.view.GestionRapizzView;

    import javax.swing.*;

    // Contrôleur pour ajouter un ingrédient
    public class AddIngredientController {
        private final Point_Pizzaria model;
        private final AddIngredientView view;

        // Constructeur
        public AddIngredientController(Point_Pizzaria model, AddIngredientView view) {
            this.model = model;
            this.view = view;
            initController();
        }

        // Initialise les boutons
        private void initController() {
            view.getBtnAdd().addActionListener(e -> onAdd());
            view.getBtnBack().addActionListener(e -> onBack());
        }

        // Ajoute un ingrédient
        private void onAdd() {
            String nom = view.getNameField().getText().trim();
            String qtyText = view.getQtyField().getText().trim();

            // Vérifie les champs
            if (nom.isEmpty() || !qtyText.matches("\\d+")) {
                JOptionPane.showMessageDialog(
                    view,
                    "Veuillez saisir un nom d’ingrédient et une quantité valides.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int quantite = Integer.parseInt(qtyText);
            model.ajouterIngredient(nom, quantite);
            JOptionPane.showMessageDialog(
                view,
                "Ingrédient ajouté avec succès.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
            );

            // Retour à la gestion
            openGestion();
            view.dispose();
        }

        // Retour sans ajout
        private void onBack() {
            openGestion();
            view.dispose();
        }

        // Ouvre la vue de gestion
        private void openGestion() {
            new GestionRapizzController(model);
            view.dispose();
        }
    }