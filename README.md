# Devathon Project
This is @MiniDigger's entry into the 2016 devathon contest. The theme was machines.

# What is this?
In short, this is a simple game where you rewire machines so that they work again.
It is fully custom and features a level editor!

# How do I use it?
Just clone this repo, compile it with maven 
(make sure you got the spigot jars in your local maven repo, aka you need to run buildtools)
and drop the jar into your plugins folder.

# The concept
The concept of the game is that you are an engineer who needs to fix machines. For that, you need to rewire them. 
You goal is it to connect all ends of the different wires. What makes this hard is that wires can't overlap each other.

# How to play?
There are some pre-generated levels (Level1 and Level2 for now, provided with the plugin) and the possibility to generate random levels. 
You can generate your own levels, more on that below.  
To play a pre-generated level, do ```/game level <levelname>```, eg ```/game level Level1```.
It will then search for a schematic of that level in your worlds structures folder and spawn it into the world. 
It will also set the start and end points for the pipes based on what is defined on the signs (more on that below).  
To play a random level, do ```/game start <difficulty>```, eg ```/game start NORMAL```. The possible difficulties are EASY (5x5), NORMAL (10x10) and HARD (15x15),
but it is easily expandable by adding new enum constants to the difficulty enum.  
When playing a random game, it will randomly generate start and end points, so not every board may be solveable. 
But don't give up easily! Try multiple ways to solve the puzzle. If you still don't think that you can finish it, 
do ```/game abort``` to abort the game.

# How does it all work?
You maybe are wondering how I am able to save all the structures. Since we are not allowed to use worldedit 
or stuff like that, I wrote my own schematic system. Well, I just ripped it out of vanilla.  
The detection of lines gave me some headache. I wanted to allow the lines to touch themselves. The 
problem with that is that the line doesn't know in which direction it should continue, thats where the wrench comes in.
The wrench allows you to set in which direction the line should continue on a block. It is not nessasary to do that on every block,
just do it on every block that doesn't behave like you want it to.  
You can take a look at the algorithm here: https://github.com/JoinDevathon/MiniDigger-2016/blob/master/src/main/java/org/devathon/contest2016/game/Board.java#L118

# Screenshots

A random easy level: ![http://i.minidigger.me/2016/11/javaw_06_13-20-04.png](http://i.minidigger.me/2016/11/javaw_06_13-20-04.png)  

A random normal level ![http://i.minidigger.me/2016/11/javaw_06_13-21-19.png](http://i.minidigger.me/2016/11/javaw_06_13-21-19.png)  

A random hard level ![http://i.minidigger.me/2016/11/javaw_06_13-22-10.png](http://i.minidigger.me/2016/11/javaw_06_13-22-10.png)

# Video
Showing how to play a easy game ![http://i.minidigger.me/2016/11/2016-11-06_13-23-07.gif](http://i.minidigger.me/2016/11/2016-11-06_13-23-07.gif)

# How do I create new levels?

Great question. This plugin was build with extendability in mind. Nearly everything is easily changeable
(not in the config, but in the enums, time was limited...) or expendable. For example, you can add a
new difficulty by adding a new entry in the difficulty enum or a new block in the tile type enum (you can even add other blocks, not only wool)
 
If you want to create a new map, you just need to provide a schematic in your worlds structures folder. 
You can create those easily using the structure block.  
First you want to build the map. I suggest you to start in the middle, building the board. The board should match one of the difficulties in size.
Then you need to add a structure block. It has to be placed in a special location. You need to subtract (-1, -1, -1) from the first location of the board.
![http://i.minidigger.me/2016/11/mspaint_06_13-37-16.png](http://i.minidigger.me/2016/11/mspaint_06_13-37-16.png)
Next to the block you need to have a sign. In the first line, write ```[LEVEL]```, in the second write the name and in the third the difficulty.
Next to that you can then define the start and end locations like this this: first line ```[TILETYPE]```, eg ```[GREEN]```, second line ```startx:starty```, eg ```0:0```
and in the third the same for the end location of that line.  
A finished tile then could look like this ![http://i.minidigger.me/2016/11/javaw_06_13-41-24.png](http://i.minidigger.me/2016/11/javaw_06_13-41-24.png).  
Make sure that you configure the structure block to contain all blocks your level should have!  
Then you just need to his save in the structure block and you are able to play you level! Remember, the command was ```/game level <levelname>```.
