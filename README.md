# Devathon Project
This is @MiniDigger's entry into the 2016 devathon contest. The theme was machines.

# What is this?
In short, this is a simple game where you rewire machines so that they work again.
It is fully custom and features a level editor!

# How do I use it?
Just clone this repo, compile it with maven 
(make sure you got the spigot jars in your local maven repo, aka you need to run buildtools)
and drop the jar into your plugins folder.

# How does it all work?
You maybe are wondering how I am able to save all the structures. Since we are not allowed to use worldedit 
or stuff like that, I wrote my own schematic system. Well, I just ripped it out of vanilla.  
The detection of lines gave me some headache. I wanted to allow the lines to touch themselves. The 
problem with that is that the line doesn't know in which direction it should continue, thats where the wrench comes in.
The wrench allows you to set in which direction the line should continue on a block. It is not nessasary to do that on every block,
just do it on every block that doesn't behave like you want it to.  
You can take a look at the algorithm here: https://github.com/JoinDevathon/MiniDigger-2016/blob/master/src/main/java/org/devathon/contest2016/game/Board.java#L118

# Screenshots

to come

# Video

to come