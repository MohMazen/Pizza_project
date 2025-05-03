package rapizz.model;

	import java.util.Vector;
	import java.util.Date;

	public class Commande {
	    private static final int LIMITE_GRATUITE = 10;
	    private int id;
	    private Client client;
	    private Vector<Ligne_Com> lignes = new Vector<>();
	    private Date date;
	    private int tempsLivraison;
		private String vehicule;

	    public Commande(int nextOrderId, Client client, Pizza pizza, String taille, int qty, double total, Date date) {
	        this.client = client;
	        this.date = date;
	    }

	    public void AjouterLigne(Pizza pizza, String taille, int quantite) {
	        lignes.add(new Ligne_Com(this, pizza, taille, quantite));
	    }

	    public double calculPrixTotalRaw() {
	        double total = 0;
	        for (Ligne_Com lc : lignes) {
	            double base = lc.getPizza().getPrixBase();
	            switch (lc.getTaille().toLowerCase()) {
	                case "naine": base *= 2.0 / 3.0; break;
	                case "ogresse": base *= 4.0 / 3.0; break;
	                default: break;
	            }
	            total += base * lc.getQuantite();
	        }
	        return total;
	    }

	    public double calculPrixTotal() {
	        double total = calculPrixTotalRaw();
	        int totalPizzas = 0;
	        for (Ligne_Com lc : lignes) totalPizzas += lc.getQuantite();
	        if (totalPizzas >= LIMITE_GRATUITE) {
	            total -= lignes.get(0).getPizza().getPrixBase();
	        }
	        return Math.max(0, total);
	    }

	    public int valider(Point_Pizzaria pizzaria) {
	        double montant = calculPrixTotal();
	        if (client.debiter(montant) == 1) {
	            client.ajouterCommande(this);
	            return 1;
	        }
	        return 0;
	    }

	    public void setTempsLivraison(int minutes) { this.tempsLivraison = minutes; }
	    public int getTempsLivraison() { return tempsLivraison; }
	    public int getId() { return id; }

	    public Date getDate() {
	        return date;
	    }

	    public int isGratuite() {
	        int totalPizzas = 0;
	        for (Ligne_Com lc : lignes) totalPizzas += lc.getQuantite();
	        return totalPizzas >= LIMITE_GRATUITE ? 1 : 0;
	    }

	    public int isLivree() {
	        for (Ligne_Com lc : lignes) {
	            if (lc.isLivree() == 1) return 1;
	        }
	        return 0;
	    }

	    public int getQuantite() {
	        int total = 0;
	        for (Ligne_Com lc : lignes) total += lc.getQuantite();
	        return total;
	    }

	    public void setGratuite() {
	        for (Ligne_Com lc : lignes) {
	            lc.setGratuite();
	        }
	    }

	    public String getVehicule() {
	        return vehicule;
	    }
	}