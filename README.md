# Galedwell
Galedwell is RPG mod that remakes all aspects of Minecraft. It's inspired by Elder Scrolls game series.

What is done:
- Weight-based inventory<br>
- Attributes<br>
- Skills<br>
- Own GUI system<br>
- Weapons (will be added more in future)<br>
- Armor (will be added more in future)<br>
- Clothes (will be added more in future)<br>
- Block destroying speed is based on attributes and skills<br>
- Damage is based on attributes and skills<br>

What is planned:
- NPCs<br>
- Quests<br>
- Factions<br>
- Advanced crafting<br>
- Advanced magic system (with spell creation and effects)<br>


###How to help
To start editing code:
 1. download Intellij IDEA and Git
 2. Clone this repo:<br>
    `git clone https://github.com/iammaxim/galedwell`
 3. Then go to newly created folder and type this in terminal/command prompt:<br>
    `gradlew setupDecompWorkspace`<br>
    (or, if you are on Linux/Mac, `./gradlew setupDecompWorkspace`).
    This will download Forge and all needed libraries.
 4. Then, open project in IDEA (select this folder).
 5. Now, type in terminal/command prompt `gradlew genIntellijRuns` (or `./gradlew genIntellijRuns`).
    This will create run configurations for client and server.
 6. Switch back to IDEA. It will ask you to reload project. Reload it. Now click on `Edit configurations...` and change field `Use classpath of module` to `Galedwell_main` on server and client configurations. If External libraries have not appeared, click on `Gradle` on right toolbar and click `Refresh all Gradle projects` (Toolbars can be enabled by clicking on button in left bottom corner).
 7. Now you should be able to run Minecraft! If you are going to test server, add `--username <Your minecraft username>` to program arguments in `Minecraft Client` configuration.