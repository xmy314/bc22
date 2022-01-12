# Battlecode 2022 Scaffold

This is the Battlecode 2022 scaffold, containing an `examplefuncsplayer`. Read https://play.battlecode.org/getting-started!

### Project Structure

- `README.md`
    This file.
- `build.gradle`
    The Gradle build file used to build and run players.
- `src/`
    Player source code.
- `test/`
    Player test code.
- `client/`
    Contains the client. The proper executable can be found in this folder (don't move this!)
- `build/`
    Contains compiled player code and other artifacts of the build process. Can be safely ignored.
- `matches/`
    The output folder for match files.
- `maps/`
    The default folder for custom maps.
- `gradlew`, `gradlew.bat`
    The Unix (OS X/Linux) and Windows versions, respectively, of the Gradle wrapper. These are nifty scripts that you can execute in a terminal to run the Gradle build tasks of this project. If you aren't planning to do command line development, these can be safely ignored.
- `gradle/`
    Contains files used by the Gradle wrapper scripts. Can be safely ignored.


### Useful Commands

- `./gradlew run`
    Runs a game with the settings in gradle.properties
- `./gradlew update`
    Update to the newest version! Run every so often


BasicPlayer is the base model to get started.
* uses a flawed lead farm strategy.

Rushy is a modified version of BasicPlayer.
* modified econ logic to be more offensive and anti-rush at the beginning. not good though. goes to lead farming after a bit.
* adapted to the meta of unrolling loops for path finding. got vision radius squared 20 breadth first to 1500-3000 bytecode.
* realize that miners can mine multiple times per turn.

Rushy_v1 is an improved version Rushy.
* Major upgrade to the communication system:
  * Old system:
    * Stores 10 coordinates of speciality, and 4 bits for each coordinate.
  * New system:
    * divides the map into 64 chunks and store 4 bit of information for each.
  * Benefit:
    * miners and soldiers are way more active explores which benefits early stage economy.
    * soldiers and miners are better at coordinated movements.
* Unit now memorizes their target for a while to avoid moving back and forth. (not perfect)

Rushy_v2 is an improved version Rushy_v1.
* Major upgrade to the economy system:
    * Old system:
        * want area/4 miners.
    * New system:
        * want area/16 miner.
    * Benefit:
        * more soldiers are built early game without losing on economy.
* various adaptation to the balance patch.



TODO:
* balance archon building power.
* miner micro.
* implement laboratory and sages?
