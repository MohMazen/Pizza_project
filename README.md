# Pizza_project

## Description
**Pizza_project** est une application de gestion de pizzeria permettant de gérer les clients, les commandes, les pizzas, les ingrédients, et bien plus encore. Elle propose une interface utilisateur intuitive pour les employés et les clients.

---

## Fonctionnalités

### Gestion des clients
- **Ajouter un client** : Ajoutez un nouveau client avec ses informations personnelles (nom, adresse, numéro de téléphone, solde).
- **Voir les clients** : Affichez la liste des clients existants avec leurs informations.
- **Modifier ou supprimer un client** : Gérez les informations des clients ou supprimez-les.
- **Consulter et recharger le solde** : Consultez le solde d'un client et ajoutez du crédit si nécessaire.

### Gestion des pizzas
- **Ajouter une pizza** : Créez une nouvelle pizza en sélectionnant ses ingrédients et en définissant son prix.
- **Voir les pizzas** : Affichez la liste des pizzas disponibles dans le menu.
- **Modifier ou supprimer une pizza** : Mettez à jour les informations d'une pizza ou supprimez-la.

### Gestion des ingrédients
- **Ajouter un ingrédient** : Ajoutez un nouvel ingrédient avec sa quantité disponible.
- **Voir les ingrédients** : Consultez la liste des ingrédients disponibles.
- **Mise à jour automatique** : Les quantités d'ingrédients sont automatiquement mises à jour en fonction des commandes.

### Commandes
- **Passer une commande** : Sélectionnez un client, choisissez une pizza, sa taille, et la quantité pour passer une commande.
- **Options de livraison** : Choisissez le mode de livraison (par moto ou autre).
- **Récapitulatif de commande** : Affichez un résumé de la commande avant de la valider.

### Gestion des revenus
- **Chiffre d'affaires global** : Consultez le chiffre d'affaires total généré par la pizzeria.
- **Revenus par client ou pizza** : Analysez les revenus générés par chaque client ou chaque type de pizza.

### Interface utilisateur
- **Interface principale** : Accès rapide aux fonctionnalités principales (connexion client, gestion pizzeria).
- **Interface gestion** : Gérez les clients, pizzas, ingrédients, et commandes via une interface dédiée.
- **Interface client** : Permettez aux clients de passer des commandes et de consulter leurs informations.

---

## Méthodes du modèle

### Classe `Point_Pizzaria`
- **`ajouterIngredient(String nom, int quantite)`** : Ajoute un nouvel ingrédient avec son nom et sa quantité.
- **`ajouterPizza(Pizza pizza)`** : Ajoute une pizza au menu de la pizzeria.
- **`ajouterClient(Client client)`** : Ajoute un client à la liste des clients de la pizzeria.
- **`getChiffreAffaires()`** : Retourne le chiffre d'affaires total de la pizzeria.

### Classe `Pizza`
- **`addIngredient(Ingredient ingredient)`** : Ajoute un ingrédient à la pizza.
- **`getPrix()`** : Retourne le prix de la pizza.

### Classe `Client`
- **`ajouterSolde(double montant)`** : Ajoute un montant au solde du client.
- **`passerCommande(Pizza pizza, int quantite)`** : Permet au client de passer une commande pour une pizza donnée.

### Classe `Ingredient`
- **`reduireQuantite(int quantite)`** : Réduit la quantité disponible de l'ingrédient.
- **`getQuantite()`** : Retourne la quantité actuelle de l'ingrédient.

---

## Auteur
Projet développé par **MohMazen**.