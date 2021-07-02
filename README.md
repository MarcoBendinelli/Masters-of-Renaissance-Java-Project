# Prova Finale Ingegneria del Software 2020-2021
This is the project for the *Prova Finale* of the course _Ingegneria del Sofware_ held at Politecnico di Milano under the supervision of Prof. Alessandro Margara. 
# Authors

- ###  Andrea Bosisio ([@andreabosisio](https://github.com/andreabosisio))<br>andrea2.bosisio@mail.polimi.it
- ###  Matteo Beltrante ([@Beltrante](https://github.com/Beltrante))<br>matteo.beltrante@mail.polimi.it
- ###  Marco Bendinelli ([@MarcoBendinelli](https://github.com/MarcoBendinelli))<br>marco.bendinelli@mail.polimi.it

# Implemented Functionalities

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | ✅ |
| Complete rules | ✅ |
| Socket | ✅ |
| CLI | ✅ |
| GUI | ✅ |
| Resilience to disconnection (AF) | ✅ |
| Persistence (AF)| ✅ |
| Local Game (AF) | ✅ |
| Multiple Games (AF) | ❌ |
| Parameters Editor (AF) | ❌ |

# Masters of Renaissance
![](deliverables/utils/gameLogo.jpeg)
Full game rules can be consulted [here](https://craniointernational.com/products/masters-of-renaissance/).

## Setup

- In the [deliverables](deliverables) there is the jar file to start the application as both Server or Client.
- To run it as Server type the following command (as default it runs on port 1337):
    ```shell
    > java -jar AM65.jar -server
    ```
  This command can be followed by these arguments:
  - **-port**: followed by the desired port;
  
- To run it as Client type the following command:
    ```shell
    > java -jar AM65.jar
    ```
    This command MUST be followed by one of these arguments:
  - **-cli**: to start the Command Line Interface (NOTE: Windows users must run it using WSL);
  - **-gui**: to start the Graphical User Interface;
  
  The Server's IP and port can be specified during the execution.
  
 ## Local Game
 
 To play a local game run only the Client application and:
  - for the **CLI** just follow the instructions available during execution
  - for the **GUI** go to *Settings* and select "Local Game"
  
 ## Demo Utilites
 
 To facilitate the demo of the game, a cheat functionality has been implemented. It simply adds some Resources into the StrongBox.

<details>
  <summary>SPOILER: To use it type this command</summary>
     
    > cheat
     
</details>
 
 ## Tools
 
 * [DrawIO](http://draw.io) - UML Diagrams
 * [Maven](https://maven.apache.org/) - Dependency Management
 * [IntelliJ](https://www.jetbrains.com/idea/) - IDE
 * [JavaFX](https://openjfx.io) - Graphical Framework
 * [SceneBuilder](https://gluonhq.com/products/scene-builder/) - Visual Layout Tool for JavaFX
 
 ## License
 
 This project is developed in collaboration with [Politecnico di Milano](https://www.polimi.it) and [Cranio Creations](http://www.craniocreations.it).
 
