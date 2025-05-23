# Board Game Hub

## About
The Board Game Hub is a desktop game launcher/hub made by Benjamin Jiemin Qin Berild and Stian Dolerud. There are currently two games available in the application: Connect Four and Snakes and Ladders.

## Installation
1. Make sure to have at Java 21 SDK or later installed. If not, visit [Oracle](https://www.oracle.com/java/technologies/downloads/#java21) and follow the installation instructions.
2. Go to the main applications repository on GitHub, [Board Game Hub](https://github.com/bjberild/MappeoppgaveV2025)
3. Go to the "Releases" page on the right sidebar.
4. Download the latest release available.
5. Run the JAR file from the downloaded release.

## Manually Building the app
If you download the source code you can build the app yourself.
1. Make sure to have at Java 21 SDK or later installed. If not, visit [Oracle](https://www.oracle.com/java/technologies/downloads/#java21) and follow the installation instructions.
2. Make sure to Maven installed. If not, visit [Apache Maven](https://maven.apache.org/install.html) and follow the instructions.
3. Through a terminal of your choice, run:
~~~
mvn clean install
~~~
4. If it builds successfully you can run the newly made jar file:
~~~
java -jar jarfilename.jar
~~~

## Saving Players and Boards (CSV & JSON)

While playing **Snakes & Ladders** in Board Game Hub, you can save your randomly generated board and players using the built-in buttons in the control panel.

### Saving Players to CSV

- **How:** Click the **“Save Players”** button in the control panel (at the bottom of the game view).
- **What it does:** This opens a file dialog where you can choose the location and filename for your CSV file. It will then save all current player data (names, tokens) to the selected CSV file.
- **Why:** This lets you reload the same set of players and use them during another session.

### Saving the Board to JSON

- **How:** Click the **“Save Board”** button in the control panel.
- **What it does:** This opens a file dialog where you can select the location and filename for the JSON file. The current game board configuration, including tile types and actions, will be saved as a JSON file.
- **Why:** This allows you to reuse or share custom board layouts and game states.
