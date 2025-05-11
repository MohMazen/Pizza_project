package rapizz.model;

import java.util.*;

/**
 * Point de production et de livraison de pizzas.
 * Gère le catalogue, le stock, les clients et les commandes.
 */
public class Point_Pizzaria {
    private String nom;
    private String adresse;
    private Vector<Client> clients = new Vector<>();
    private Vector<Pizza> menu = new Vector<>();
    private Vector<Ingredient> stock = new Vector<>();
    private Vector<Commande> commandes = new Vector<>();
    private int nextOrderId = 1;
    private Livreur livreur;

    // --- Constructeurs ---
    public Point_Pizzaria(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    // --- Getters & Setters ---
    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Vector<Pizza> getMenu() {
        return menu;
    }

    public Vector<Ingredient> getStock() {
        return stock;
    }

    public Vector<Client> getClients() {
        return clients;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    // --- Méthodes ---
    public void ajouterClient(Client c) {
        clients.add(c);
    }

    public Client findClientByPhone(String phone) {
        for (Client c : clients) {
            if (c.getTelephone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    public String afficherClients() {
        StringBuilder sb = new StringBuilder();
        for (Client c : clients) {
            sb.append(c.getNom())
              .append(" – ")
              .append(c.getTelephone())
              .append("\n");
        }
        return sb.toString();
    }

    public void ajouterPizza(Pizza p) {
        menu.add(p);
    }

    public Pizza getPizzaParNom(String nomPizza) {
        for (Pizza p : menu) {
            if (p.getNom().equalsIgnoreCase(nomPizza)) {
                return p;
            }
        }
        return null;
    }

    public void ajouterIngredient(String nom, int quantite) {
        for (Ingredient i : stock) {
            if (i.getNom().equalsIgnoreCase(nom)) {
                i.setQuantite(i.getQuantite() + quantite);
                return;
            }
        }
        stock.add(new Ingredient(nom, quantite, null, this));
    }

    public String afficherQuantiteIngredients() {
        StringBuilder sb = new StringBuilder();
        for (Ingredient ing : stock) {
            sb.append(ing.getNom())
              .append(" : ")
              .append(ing.getQuantite())
              .append("\n");
        }
        return sb.toString();
    }

    public int consommerIngredients(Pizza pizza, int qty) {
        for (Ingredient recIng : pizza.getIngredients()) {
            int needed = recIng.getQuantite() * qty;
            Ingredient stockIng = null;
            for (Ingredient i : stock) {
                if (i.getNom().equalsIgnoreCase(recIng.getNom())) {
                    stockIng = i;
                    break;
                }
            }
            if (stockIng == null || stockIng.getQuantite() < needed) {
                return 0;
            }
        }
        for (Ingredient recIng : pizza.getIngredients()) {
            int needed = recIng.getQuantite() * qty;
            for (Ingredient i : stock) {
                if (i.getNom().equalsIgnoreCase(recIng.getNom())) {
                    i.setQuantite(i.getQuantite() - needed);
                    break;
                }
            }
        }
        return 1;
    }

    public double getTresorerie() {
        double total = 0;
        for (Client c : clients) {
            for (Commande com : c.getCommandes()) {
                total += com.calculPrixTotal();
            }
        }
        return total;
    }

    public void creerCommande(Client client, String nomPizza, String taille, int qty, String typeVehicule) {
        Pizza pizza = getPizzaParNom(nomPizza);
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza inconnue : " + nomPizza);
        }
        if (consommerIngredients(pizza, qty) == 0) {
            throw new IllegalStateException("Stock insuffisant pour préparer la commande");
        }

        double base = pizza.getPrixBase();
        double prixUnitaire;
        switch (taille.toLowerCase()) {
            case "naine":
                prixUnitaire = base * 2.0 / 3.0;
                break;
            case "ogresse":
                prixUnitaire = base * 4.0 / 3.0;
                break;
            default:
                prixUnitaire = base;
                break;
        }

        Date dateCommande = new Date();
        Commande cmd = new Commande(nextOrderId, client, pizza, taille, qty, dateCommande);
        cmd.setVehicule(typeVehicule);
        int delayMinutes = typeVehicule.equalsIgnoreCase("moto") ? 15 : 30;
        cmd.setTempsLivraison(delayMinutes);

        commandes.add(cmd);
        client.ajouterCommande(cmd);
        final int orderId = nextOrderId++;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Commande c = getCommandeById(orderId);
                if (c != null && c.isLivree() == 0 && c.getQuantite() >= Commande.LIMITE_GRATUITE) {
                    c.setGratuite();
                }
            }
        }, delayMinutes * 60 * 1000L);

	}

    public Commande getCommandeById(int id) {
        for (Commande c : commandes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public String afficherCommandesClient(Client client) {
        StringBuilder sb = new StringBuilder();
        Date now = new Date();

        for (Commande cmd : client.getCommandes()) {
            long dureeMs = cmd.getTempsLivraison() * 60 * 1000L;
            long elapsedMs = now.getTime() - cmd.getDate().getTime();
            long remainingMs = dureeMs - elapsedMs;
            String timerStr;

            if (remainingMs > 0) {
                int m = (int) (remainingMs / 60000);
                int s = (int) ((remainingMs % 60000) / 1000);
                timerStr = String.format("%02d:%02d", m, s);
            } else {
                cmd.setGratuite();
                timerStr = "00:00";
            }

            sb.append("Commande n°").append(cmd.getId())
              .append(" – Total : ").append(cmd.calculPrixTotal()).append(" €")
              .append(cmd.isGratuite() == 1 ? " (gratuite)" : "")
              .append(" – Véhicule : ").append(cmd.getVehicule())
              .append(" – Temps restant : ").append(timerStr)
              .append("\n");
        }
        return sb.toString();
    }

    public String[] getNomsPizzas() {
        return menu.stream()
                   .map(Pizza::getNom)
                   .toArray(String[]::new);
    }

    public List<String> getListeIngredients() {
        List<String> liste = new ArrayList<>();
        for (Ingredient ing : stock) {
            liste.add(ing.getNom());
        }
        return liste;
    }
}