# Projet PROJ831 : Meta-Campus - Application dashboard étudiant

Projet finale de l'année scolaire qui consiste à réaliser une application destinée à aider les étudiants dans leur apprentissage, en leur fournissant des informations diverses, telles que l’avancement temporel des enseignements, les travaux à rendre, le niveau de charge de la semaine, toutes les informations concernant les modules...

---
## Table des matières
- [Fonctionnalités](#fonctionnalités)
- [Conception](#conception)
- [Utilisation](#utilisation)

---
## Conception
La première étape du projet a été la conception préliminaire de l'application. Cela se traduit par un moment de réflexion sur l'application que nous voulons réaliser, avant de réaliser les diagrammes d'utilisation, d'état, Entité-Association,  de séquences et de classes ainsi que la maquette.

### Modèle E/A

Voici le modèle Entité-Association de la base de donnée liée à notre application :

![Modèle EA](Images/Modele_EA.png)

Les entités fonctionnelles de notre application sont donc :
- Ecole
- Filiere
- Promo
- Eleve
- Enseignant
- UE
- Module
- Travail
- Note
- Cours

Le principe de notre application est donc le suivant :
L'école contient des promos. Une promo correspond à une filière (IDU3, MM4...) et une année, exemple : IDU3 de 2022. Une filière contient des UE qui sont composés de modules. Un module est composé de différents cours, et un module peut être suivi par plusieurs filières.
Un élève appartient à une promo qui contient plusieurs élèves. Chaque élève possède plusieurs notes qui sont associées à un travail. Un travail s'inscrit dans un module et est préparé par un enseignant. Un enseignant enseigne un ou plusieurs modules.


Nous avons implémenté notre base de données avec phpMyAdmin en SQL. 

### Diagramme de classes

Nous avons réfléchit à un diagramme de classe à partir de ces entités qui servira de base à l'implémentation du code, les cardinalités sont notés suivant la norme UML (les classes utils ne sont pas affichées sur ce diagramme) :

![Diagramme de classes](Images/diagrammeClasse.png)

A noter que `Fillière` est une énumération au sens Java, donc un type spécial de classe qui peut posséder des attributs comme une classe normale.

Pour cette application, nous avons implémenté les patterns suivant :

- `Singleton` au travers de la classe Ecole, afin qu'il n'y ait qu'une seule instance d'école contenant la liste de toutes les promos présente dans l'application.
- `Factory` au travers de la classe CoursFactory, qui permet de créer des CM, TD, TP, EXAM ou SPECIAL : différents types de cours avec des durées différentes et potentiellement d'autres attributs.

### Diagrammes de séquences

Voici les diagrammes de séquence qui vont nous permettre de tracer le chemin des appels de fonction pour chaque cas d'utilisation :

##### Connexion d'un utilisateur

![Diagramme de Séquence pour la connexion](Images/sequenceConnection.png)

L'utilisateur va demander à l'application de le connecter à l'aide d'un login et d'un mot de passe. Cette dernière va le connecter ou non selon la validité de ses identifiants.

##### Consulter les cours de la semaine

![Diagramme de Séquence pour la consultation des cours de la semaine](Images/consulterCoursSemaine.png)

Pour obtenir ses cours de la semaine, l'élève utilisateur va d'abord se connecter puis obtenir depuis son objet Eleve les jours de la semaine. Pour chaque jour de la semaine, on récupère tous les modules suivis par l'élève. Pour chaque module, on récupère les cours de ce module. Pour chaque cours, on récupère la date du cours. Si le cours a lieu ce jour là, on l'ajoute à la liste des cours de la semaine. Après avoir loopé pour chaque jour, on retourne la liste des cours de la semaine.

##### Consulter les travaux à faire

![Diagramme de Séquence pour la consultation des travaux à faire](Images/consulterTravailAFaire.png)

Après connexion, l'utilisateur récupère l'ensemble de ses travaux avec son objet Eleve, puis pour chaque travaux on récupère sa date et on vérifie si le travail n'est pas passé. Si c'est le cas on l'ajoute à la liste des travaux de la semaine. A la fin, on retourne cette liste.


##### Consulter les notes

![Diagramme de Séquence pour la consultation des notes](Images/consulterNotes.png)

Les notes sont stockées dans l'attribut informations de l'objet Eleve, donc il suffit de retourner cet attribut.

---
## Fonctionnalités

### Application

Lors du lancement de l'application, vous devez vous connecter :

![Page de connection](Images/pageDeConnection.png)

Vous arrivez ensuite sur une page d'accueil :

![Page d'accueil](Images/ScreenAccueil.png)


Nous pouvons voir sur cette fenêtre :
 - le nom de l'élève et de la promo
 - la charge de travail de la semaine
 - les dernières notes
 - les travaux à rendre et leurs date

A partir de cette page, nous pouvons accéder à l'emploi du temps généré à partir de la base de donnée en cliquant sur `Planning` : 

![Emploi du temps](Images/emploiDuTemps.png)

Sur la page `Planning`, il est possible de voir l'emploi du temps de la seamine en cours et de changer de semaine. Il est également possible de recharger l'emploi du temps pour obtenir les denières modifications.

Il est possible d'ajouter un cour en appuyant sur le bouton `Ajouter un cour`:

![Ajouter un cour](Images/ajouterCour.png)

Nous pouvons aussi accéder à l'ensemble des notes de tous les modules, ainsi que les moyennes en cliquant sur `Mes Notes` :

![Modules notes](Images/modules.png)

Depuis cette page, on peut voir l'avancement du module en fonction du nombre de cours passés, les travaux à rendre pour chaque module, les notes et les moyennes de chaque module.

Il est possible d'ajouter une note depuis la page d'accueil en appuyant sur le bouton `Ajouter une note`:

![Ajouter une note](Images/ajouterNote.png)

Il est possible de visualiser les informations des modules en appuyant sur le bouton `Module` /

![Modules](Images/infoModule.png)

Sur cette page il est possible de recharger les informations des modules.

Enfin, nous pouvons accéder à notre compte en cliquant sur le nom de l'utilisateur :

![Mon compte](Images/monCompte.png)



### Scraping

Pour que notre application soit utile, il nous faut des données. Pour ce faire, nous remplissons notre base de données à partir d'informations récupérées sur différents sites web à l'aide de scraping. 
Dans un premier temps, lors de chaque connexion à l'application, nous allons récupérer les informations disponibles sur l'intranet de l'université (USMB) ainsi que l'intranet de l'école Polytech.
Nous récupérons alors des données telles que le nom, prénom, date de naissance de l'utilisateur ainsi que l'ine, les polypoints, le mail... (Cette opération de scraping prend entre 5 et 10 secondes)

En outre, nous avons implémenté, dans notre application, un bouton pour actualiser les modules. Nous allons alors réaliser un scraping sur la page des modules d'une filière cible que nous pouvons retrouver sur l'intranet de l'école. Cette opération va récupérer toutes les informations importants concernant les modules ainsi que les informations sur les enseignants et les unités d'enseignements (UE). (Cette opération prend entre 10 et 20 secondes).

Enfin, un autre bouton est disponible, dans la page du planning, pour actualiser les cours. Nous allons récupérer les informations directement sur le site nous permettant de récupérer notre planning.
Nous pouvons alors répertorier tous les cours que nous avons eût tout au long de l'année scolaire. Cette opération prend un certain temps car le site disponible est vraiment instable et il y a beaucoup de données à récupérer. A noter qu'une fois que l'actualisation de cours a été réalisée, les cours seront chargés pour l'année entière. 

---
## Difficultés rencontrées

En ce qui concerne les difficultés que nous avons rencontrées, celles-ci sont principalement basées sur le scraping. En effet, nous sommes dépendants des sites web où nous récupérons nos informations. 
Par exemple, le site permettant de récupérer l'emploi du temps n'est pas très réactif et nous avons dû ajouter des temps de delay pour être sûrs que le scraping fonctionnera bien. Aussi, la plateforme n'est pas accessible à certaines heures ce qui empêche donc la récupération de données.

Le scraping n'a pu être effectué qu'avec des comptes étudiants IDU4, il est donc possible que celui ne fonctionne pas correctement avec d'autres comptes.

---
## Utilisation

Prérequis: 

- Avoir une base de données mysql et importer le fichier .sql fourni.

- Avoir Java Runtime Environment à jour (version 17 ou plus).

- Avoir installé GoogleChrome (pour le scraping )


Lancement:

- Modifier les informations du fichier config.yml avec les informations de sa propre base de données.

- Lancer le projet Maven avec les arguments `clean javafx:run` 

- Se connecter avec un login étudiant d'un élève de Polytech Annecy.

exemple: 

         login --> gueriotb
         mdp --> xxxxxxx

---
### Crédits
Projet conçu et élaboré par GUERIOT Benjamin et NICOLAS Thomas.
