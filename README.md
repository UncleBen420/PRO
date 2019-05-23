
# Outil pour navigation, l’étiquetage et l’annotation d’images

Une application qui permet pour la navigation, l'étiquetage et l'annotation d'une banque d'image recueillie dans des crapauducs.

Ce logiciel a été développé comme projet de semestre(PRO) à l'HEIG-VD en 2018/19.

Development team:

| Name                            | Email                           | Github      |
|---------------------------------|---------------------------------|-------------|
| Gaëtan Bacso                    | gaetan.bacso@heig-vd.ch         | gaeba95     |
| Rémy Vuagniaux (chef de projet) | remy.vuagniaux@heig-vd.ch       | UncleBen420 |
| Edoardo Carpita                 | edoardo.carpita@heig-vd.ch      | ecarpita93  |
| Rafael Da Cunha Garcia          | rafael.dacunhagarcia@heig-vd.ch | Rafiibro    |
| Miguel Angelo Lopes Gouveia     | miguel.lopesgouveia@heig-vd.ch  | endmon      |
| Marion Dutu Launay	             | marion.dutulaunay@heig-vd.ch    | MarionDL    |

## Dependencies

L'application requiert Java 8 ou plus et les librairies suivantes :

* Google Json 2.8.5
* Junit 5.4.0
* Maven 4.0.0
* JavaFX

## Build and install

1.  Téléchargez le projet sur votre machine: git clone git@github.com:UncleBen420/PRO.git

2.  Déplacez vous à la racine du projet: cd PRO/

3.  Compilez le projet: mvn install

3b. Si vous avez déjà compilez le projet, rajoutez l'option clean pour nettoyer des compilation précédentes et supprimer les  fichiers history.json et cheminPasser.json :  mvn clean install; rm jsonFiles/history.json jsonFiles/cheminPasser.json

*Pour Windows PowerShell: mvn clean install; rm .\jsonFiles\cheminPasser.json; rm .\jsonFiles\history.json*

4.  Compléter le champ imageBankPath dans config/conf.properties, avec votre chemin d'accès jusqu'a la banque d'images.

*Pour Windows, mettre des doubles \\\, ex: C:\\\Users\\\John\\\Documents\\\PRO\\\config\\\conf.properties*

## Run

1. Depuis la racine du projet:  java -jar target/PRO_Crapauduc_viewer-0.0.1-SNAPSHOT-jar-with-dependencies.jar

## Documentation

User manual: [see file](doc/Manuel_Utilisation.pdf)

API documentation: [see file](doc/documentation.pdf)
