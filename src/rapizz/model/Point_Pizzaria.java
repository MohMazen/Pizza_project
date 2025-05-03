package rapizz.model;

	            import java.util.*;

public class Point_Pizzaria {
	                private String nom;
	                private String adresse;
	                private Vector<Client> clients = new Vector<>();
	                private Vector<Pizza> menu = new Vector<>();
	                private Vector<Ingredient> stock = new Vector<>();
	                private Vector<Commande> commandes = new Vector<>();
	                private int nextOrderId = 1;
	                private Livreur livreur;

	                public Point_Pizzaria(String nom, String adresse) {
	                    this.nom = nom;
	                    this.adresse = adresse;
	                    this.livreur = livreur;
	                }

	                public String getNom() {
	                    return nom;
	                }

	                public String getAdresse() {
	                    return adresse;
	                }

	                public void ajouterClient(Client c) {
	                    clients.add(c);
	                }

	                public Client findClientByPhone(String phone) {
	                    for (Client c : clients) {
	                        if (c.getTelephone().equals(phone)) return c;
	                    }
	                    return null;
	                }

	                public void ajouterPizza(Pizza p) {
	                    menu.add(p);
	                }

	                public Pizza getPizzaParNom(String nomPizza) {
	                    for (Pizza p : menu) {
	                        if (p.getNom().equalsIgnoreCase(nomPizza)) return p;
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

	                public double getTresorerie() {
	                    double total = 0;
	                    for (Client c : clients) {
	                        for (Commande com : c.getCommandes()) {
	                            total += com.calculPrixTotal();
	                        }
	                    }
	                    return total;
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

	                public String[] getNomsPizzas() {
	                    return menu.stream()
	                               .map(Pizza::getNom)
	                               .toArray(String[]::new);
	                }

	                public int creerCommande(Client client, String nomPizza, String taille, int qty, String typeVehicule) {
	                    Pizza pizza = getPizzaParNom(nomPizza);
	                    double base = pizza.getPrixBase();
	                    double prixUnitaire;
	                    switch (taille.toLowerCase()) {
	                        case "naine":
	                            prixUnitaire = base - (base / 3.0);
	                            break;
	                        case "ogresse":
	                            prixUnitaire = base + (base / 3.0);
	                            break;
	                        case "humaine":
	                        default:
	                            prixUnitaire = base;
	                            break;
	                    }

	                    double total = prixUnitaire * qty;
	                    Date dateCommande = new Date();

	                    Commande cmd = new Commande(
	                        nextOrderId, client, pizza, taille, qty, total, dateCommande
	                    );
	                    commandes.add(cmd);
	                    client.ajouterCommande(cmd);

	                    final int orderId = nextOrderId++;
	                    int delayMinutes = typeVehicule.equalsIgnoreCase("moto") ? 15 : 30;
	                    new Timer().schedule(new TimerTask() {
	                        @Override
	                        public void run() {
	                            Commande c = getCommandeById(orderId);
	                            if (c != null && c.isLivree() == 0 && c.getQuantite() > 10) {
	                                c.setGratuite();
	                            }
	                        }
	                    }, delayMinutes * 60 * 1000L);

	                    return orderId;
	                }

	                public Commande getCommandeById(int id) {
	                    for (Commande c : commandes) {
	                        if (c.getId() == id) return c;
	                    }
	                    return null;
	                }

	                public void debiterSoldeClient(Client client, int qty, String nomPizza, String taille) {
	                    Pizza pizza = getPizzaParNom(nomPizza);
	                    double base = pizza.getPrixBase();
	                    double prixUnitaire;
	                    switch (taille.toLowerCase()) {
	                        case "naine":
	                            prixUnitaire = base - (base / 3.0);
	                            break;
	                        case "ogresse":
	                            prixUnitaire = base + (base / 3.0);
	                            break;
	                        case "humaine":
	                        default:
	                            prixUnitaire = base;
	                            break;
	                    }

	                    double total = prixUnitaire * qty;
	                    client.debiter(total);
	                }

					public String afficherCommandesClient(Client client) {
						StringBuilder sb = new StringBuilder();
						Date now = new Date();

						for (Commande cmd : client.getCommandes()) {
							// on récupère directement le véhicule lié à la commande
							String vehicule = cmd.getVehicule();

							// durée max selon le véhicule
							int dureeLivMs = vehicule.equalsIgnoreCase("moto")
									? 15 * 60 * 1000
									: 30 * 60 * 1000;

							long elapsedMs   = now.getTime() - cmd.getDate().getTime();
							long remainingMs = dureeLivMs - elapsedMs;

							String timerStr;
							if (remainingMs > 0) {
								int m = (int)(remainingMs / 60000);
								int s = (int)((remainingMs % 60000) / 1000);
								timerStr = String.format("%02d:%02d", m, s);
							} else {
								cmd.setGratuite();           // dépassement → gratuite
								timerStr = "00:00";
							}

							sb.append("Commande n°").append(cmd.getId())
									.append(" – Date : ").append(cmd.getDate())
									.append(" – Total : ").append(cmd.calculPrixTotal())
									.append(" €")
									.append(cmd.isGratuite() == 1 ? " (gratuite)" : "")
									.append(" – Véhicule : ").append(vehicule)
									.append(" – Temps restant : ").append(timerStr)
									.append("\n");
						}

						return sb.toString();
					}

					public List<String> getListeIngredients() {
						List<String> ingredients = new ArrayList<>();
						for (Ingredient ing : stock) {
							ingredients.add(ing.getNom());
						}
						return ingredients;
					}
				}