# **Application de Gestion de Profils Utilisateurs**

## **Description du Projet**
Cette application de bureau développée en Java avec Swing permet de gérer les profils utilisateurs. Elle offre les fonctionnalités suivantes :  
- **Créer** un nouvel utilisateur.  
- **Modifier** les informations d’un utilisateur existant.  
- **Supprimer** un utilisateur.

Ce projet s’inscrit dans le cadre académique de la 1ère année du cycle ingénieur en génie logiciel. Il est conçu pour démontrer une maîtrise des bases de développement d’interfaces graphiques (GUI) avec Swing et des concepts fondamentaux de la programmation orientée objet en Java.

---

## **Fonctionnalités**
1. **Gestion des utilisateurs** :
   - Ajout d’un profil avec les informations de base : nom, prénom, email, et rôle.  
   - Modification des données d’un profil existant.  
   - Suppression définitive d’un profil.  

2. **Interface utilisateur** :  
   - Interface intuitive et conviviale développée avec Swing.  
   - Gestion des erreurs et validations des champs (exemple : email obligatoire).  

3. **Sauvegarde des données** (optionnel pour un projet académique) :  
   - Les données peuvent être stockées temporairement en mémoire (utilisation d'une structure comme une **ArrayList**) ou sauvegardées dans un fichier local (utilisation de **fichiers texte** ou **JSON**).  

---

## **Prérequis**
Avant de commencer, assurez-vous d’avoir les éléments suivants installés sur votre machine :  
1. **Java Development Kit (JDK)** : Version 11 ou supérieure.  
2. **IDE** : IntelliJ IDEA, Eclipse, ou NetBeans recommandé.  
3. **Système d’exploitation** : Windows, Linux ou macOS.  

---

## **Installation et Exécution**
### Étape 1 : Cloner le dépôt  
Clonez le projet depuis le dépôt GitHub ou récupérez les fichiers du projet :  
```bash
git clone https://github.com/votre-utilisateur/projet-gestion-profil.git
cd projet-gestion-profil
```

### Étape 2 : Importer dans l’IDE  
1. Ouvrez votre IDE préféré (IntelliJ IDEA recommandé).  
2. Importez le projet comme un **projet Java existant**.  
3. Configurez votre JDK si nécessaire.

### Étape 3 : Compiler et exécuter  
1. Compilez le projet en exécutant le fichier principal (probablement `Main.java`).  
2. Exécutez pour lancer l’application.  

---

## **Structure du Projet**
- **src/** : Contient les fichiers source Java.  
  - **Main.java** : Point d’entrée de l’application.  
  - **User.java** : Classe représentant le modèle d’utilisateur (nom, prénom, email, rôle).  
  - **UserManager.java** : Classe pour la gestion des utilisateurs (CRUD).  
  - **MainFrame.java** : Classe pour la fenêtre principale de l’interface utilisateur.  
- **resources/** : Contient les fichiers nécessaires (images, fichiers de configuration).  

---

## **Contributeurs**
- **Nom complet** : Chouaib Saad  
- **Année académique** : 2024 - 2025  

---

## **Licence**
Tous droits réservés © **Chouaib Saad** 2024 - 2025.  
Ce projet est protégé par les droits d’auteur et ne peut être reproduit ou distribué sans autorisation préalable.
