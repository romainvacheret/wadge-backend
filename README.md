# Wadge-BackEnd

![Java CI](https://github.com/RomainVacheret/Wadge-BackEnd/workflows/Java%20CI/badge.svg)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/RomainVacheret/Wadge-BackEnd)
[![License](https://img.shields.io/github/license/RomainVacheret/Wadge-BackEnd.svg?style=flat-square)](LICENSE)
[![Code Quality](https://sonarcloud.io/api/project_badges/measure?project=RomainVacheret_Wadge-BackEnd&metric=alert_status)](https://sonarcloud.io/dashboard?id=RomainVacheret_Wadge-BackEnd)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=RomainVacheret_Wadge-BackEnd&metric=sqale_index)](https://sonarcloud.io/dashboard?id=RomainVacheret_Wadge-BackEnd)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=RomainVacheret_Wadge-BackEnd&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=RomainVacheret_Wadge-BackEnd)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=RomainVacheret_Wadge-BackEnd&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=RomainVacheret_Wadge-BackEnd)
[![codecov](https://codecov.io/gh/RomainVacheret/Wadge-BackEnd/branch/master/graph/badge.svg?token=5HD33M3ONC)](https://codecov.io/gh/RomainVacheret/Wadge-BackEnd)

# Que faisons nous ?
Wadge est une application utilisable par tous, qui propose une gestion d'un frigo virtuel.  
Fini le gachis !   
Nous vous proposons des recettes en fonction des fruits & légumes de saison tout en prenant en compte vos avis ainsi que ce que vous possédez dans votre frigo !

# Installation
## Prérequis
* [Télécharger le JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
* [Télécharger Git](https://git-scm.com/downloads)
* [Télécharger Python 3.9](https://www.python.org/downloads/release/python-390/)
* [Installer Gradle](https://gradle.org/install/)
* [Installer Docker](https://www.docker.com/get-started)

## Démarrage
* Cloner le repository  
* Lancer le docker
* Lancer le projet
* Installer les dépendances
* Lancer le script de création de données

```Bash
git clone https://github.com/RomainVacheret/wadge-backend.git
cd wadge-backend/db
docker compose up
// Dans un autre terminal
cd wadge-backend
./gradlew bootRun
// Dans un autre terminal
cd wadge-backend/setup
python3 -m pip install -r requirements.txt
python3 main.py

```

# Une application en 3 parties
Ce projet est découpé en 3 parties :
* [Partie Front End](https://github.com/RomainVacheret/wadge-frontend)
* [Partie Back End](https://github.com/RomainVacheret/wadge-backend)
* [Partie Machine Learning](https://github.com/RomainVacheret/wadge-ml)

# Auteurs
* **Fanny Lourioux** - [Github](https://github.com/FannyLourioux) - [LinkedIn](https://www.linkedin.com/in/fanny-lourioux-4744941a0/)
* **Trystan Roches** - [Github](https://github.com/Trystan4) - [LinkedIn](https://www.linkedin.com/in/trystan-roches-4a6ba0171/)
* **Romain Vacheret** - [Github](https://github.com/RomainVacheret) - [LinkedIn](https://www.linkedin.com/in/romain-vacheret-b58270189/)
* **Celia Ihdene** - [Github](https://github.com/CeliaIHDENE) - [LinkedIn](https://www.linkedin.com/in/celia-ihdene/)
