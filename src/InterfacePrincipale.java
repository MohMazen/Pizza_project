import javax.swing.*;
import java.awt.*;

public class InterfacePrincipale {
    public InterfacePrincipale(Point_Pizzaria pizzaria) {
        JFrame frame = new JFrame("Bienvenue chez RaPizz");
        frame.setSize(600, 400); // ✅ même taille que souhaitée
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Logo
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\chaab\\Downloads\\17 avr. 2025, 10_40_33.png");
        logoIcon = redimensionnerImage(logoIcon, 100, 100); // ✅ Même taille que dans InterfaceClient

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(logoLabel, BorderLayout.NORTH);

        // Titre
        JLabel titre = new JLabel("Choisissez votre interface", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        // Centre avec titre + boutons
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        centre.add(titre, BorderLayout.NORTH);

        // Panel pour boutons avec taille réduite verticalement
        JPanel boutonPanel = new JPanel(new GridLayout(2, 1, 20, 15));

        JButton btnClient = new JButton("Espace Client");
        JButton btnGestion = new JButton("Espace Gestion");

        Dimension btnSize = new Dimension(200, 35); // ✅ Taille réduite verticalement

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{btnClient, btnGestion}) {
            btn.setPreferredSize(btnSize);
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(30, 30, 30));
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }

        boutonPanel.add(btnClient);
        boutonPanel.add(btnGestion);
        centre.add(boutonPanel, BorderLayout.CENTER);
        frame.add(centre, BorderLayout.CENTER);

        // Actions
        btnClient.addActionListener(e -> {
            frame.dispose();
            new InterfaceClient(pizzaria);
        });

        btnGestion.addActionListener(e -> {
            frame.dispose();
            new InterfaceRaPizz(pizzaria).setVisible(true);
        });

        frame.setVisible(true);
    }

    // ✅ Méthode pour redimensionner l'image
    private ImageIcon redimensionnerImage(ImageIcon icon, int largeur, int hauteur) {
        Image image = icon.getImage();
        Image imageRedimensionnee = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        return new ImageIcon(imageRedimensionnee);
    }
}
