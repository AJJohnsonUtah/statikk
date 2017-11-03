#Statikk
--------
Statikk is a League of Legends data analytics platform, providing a suite of tools for players to use to improve and learn about the game.

## Project Setup
1. Clone the project locally
2. Import the root directory into your IDE of choice (this was developed in NetBeans IDE 8.2)
    - The root directory contains a pom.xml file which indicates that this is a multi-module maven project. The three modules are: domain, data-miner, and web-api.
3. Test the projects first, either by running their tests, or by building them (which should automatically run the tests). Running `mvn verify` in the root directory of the project should build and test all projects.
4. For the front-end, open the web-app directory in an IDE that is TypeScript/Angular friendly (NetBeans is *not*). I recommend Visual Studio Code or IntelliJ (but that costs $)
5. From the web-app directory, run `npm install` to install the project's dependencies.
6. From the web-app directory, run `npm start`; the project should compile, and you should be able to open the front end by navigating to http://localhost:4200 in a browser.

## Module Descriptions
### Domain
The domain module contains all Models, DAOs (Data Access Objects) and Services that interact with the database. Additionally, the service that interacts with the Riot API is also in this module (RiotAPIService).

### Web API
The web-api module is a lightweight REST API that produces data for the Angular application (web-app module). Run this module when developing the front-end.

### Data Miner
The data-miner module is an application that will constantly be running, making calls to the Riot API fetching pseudo-random data which is used to aggregate data into the Statikk database. This project does not need to be running for the application to work.

### Web App
The web-app module is the front-end Angular application. It is not Java, so it's not a real module in the maven project.

### Statikk Database
The database is a MySQL database that was configured with a weird user and password. Feel free to change the username/password to one of your choosing (stored in application.properties now, but will be refactored to avoid that). The database schema is called `statikk`, and the create table/index/etc. statements are in the file domain/src/main/resources/statikk.sql.