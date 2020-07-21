# 'n' stones - Kalaha Game

A Java RESTful Web Service that runs a game of n-stones Kalah. This web service should enable to let 2 human players to play the game, each in his own computer.

## About

Technologies used : Java, Spring Boot, Rest API
Database : In memory H2 database

## Game Rules with implementations

Each of the two players has 6 pits in front of him/her. To the right of the six pits, each player has a larger pit, his
Kalaha or house. At the start of the game, 'n' number of stones can be put in each pit.

Create a new game and a new game will be created with default pits and stones i.e. 6 pits each with 'n' stones.
Pits 1 to 6 are for Player One and Pits 8 to 13 are for Player two.

'n' can be configured within the application before running the application.

Player One is assumed to make the first move from the range of 1 to 6 pits.

### Errors for Invalid Moves

- Player One is supposed to make the first move with pits from the range - 1 to 6 otherwise an error would be returned.
- Every alternate move should be from the different player, unless the move is repeated in certain cases. Error will be returned otherwise.
- If invalid pit Id is given or pit with 0 stones in it is given, error would be returned.
- If the game is already ended and any of the players try to make another move, all the pits would be set to 0 and kalaha would be updated as per the game at that
  particular time.

### Code as per the functionality 

#### Create game

Initial number of stones can be configured in `application.properties` under the property - 

```
com.kalaha.game.noOfStones: 6
```

Game will be created and the URL will be returned, valid response would be - 

```
{
    "id": "1",
    "uri": "http://localhost:8080/games/1"
}
```
#### Make an invalid move/incorrect player made a move/invalid pit ID selected

In any of the error scenarios extra fields would be returned in the response along with the id, url and the current status of the pits - 

```
{
    "httpStatus": "BAD_REQUEST",
    "error": "Invalid move. Other player's turn",
    "id": 1,
    "url": "http://localhost:8080/games/1/pits/50",
    "status": {
        "1": 6,
        "2": 6,
        "3": 6,
        "4": 6,
        "5": 0,
        "6": 7,
        "7": 1,
        "8": 7,
        "9": 7,
        "10": 7,
        "11": 7,
        "12": 6,
        "13": 6,
        "14": 0
    }
}
```

#### Make a valid move

In case of a valid move, appropriate pits would be updated and the response would be generated - 

```
{
    "id": 1,
    "url": "http://localhost:8080/games/1/pits/2",
    "status": {
        "1": 6,
        "2": 0,
        "3": 7,
        "4": 7,
        "5": 7,
        "6": 7,
        "7": 1,
        "8": 7,
        "9": 6,
        "10": 6,
        "11": 6,
        "12": 6,
        "13": 6,
        "14": 0
    }
}
```


## Running the application

There are several ways to run the application. You can run it from the command line with included Maven Wrapper or Maven.

Once the app starts, go to the web browser and visit `http://localhost:8080/games` to create a new game.

`http://localhost:8080/games/{gameId}/pits/{pitId}` to make a move.

### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java and Maven installed:

```bash
$ java -version
java version "1.8.0_152"
Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)
```

```bash
$mvn -v
Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T13:28:13+05:30)
Maven home: D:\softwares\apache-maven-3.5.2\bin\..
Java version: 1.8.0_152, vendor: Oracle Corporation
Java home: C:\Program Files\Java\jdk1.8.0_152\jre
```

#### Using the Maven Plugin

The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. 
Applications run in an exploded form, as they do in your IDE. 
The following example shows a typical Maven command to run a Spring Boot application:
 
```bash
$ mvn spring-boot:run
``` 

To exit the application, press **ctrl-c**.