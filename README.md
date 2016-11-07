#Hieranarchy

#What is it?

Hieranarchy is the name of the game I made for LD 29 that inspired me to make this engine. That was the original aim of the engine, to be used to rewrite the game, but it took a different turn to be a tool for anyone to easily create games with no programming experience necessary.

#How does it work?

In its current state it doesn't do much but look ugly! The plan is that you will have a project that holds a bunch of maps and other data. In these maps is more data but also logic will be available, most likely in a way similar to Unreal Engine's blueprint system or MIT's Scratch. This will help to diversify a game but also make it more interesting.

#What is left to do?

Currently, a lot.

Features:
- Map and State system which allows to dynamically load and swap states and maps
- Dynamic tile system which allows you to create and save different tiles
- Project system which saves and loads maps
- Basic tile creation and editing in an editor
- Mostly complete UI system

What is left to come is:
- Add project interface
- Improve links between interfaces
- Better control over map and project data
- Entities as a whole
- Image editor for sprites
- Possible sound editor?
- and whatever anyone else wants to suggest!

#Can I test it?
Sure anyone can download the source and build it themselves, however you would need to download GSON and LWJGL 3 and then add them to the build path etc. So I have added a build folder that has the runnable jar that will be updated fairly often. All you need to do to run this is to download the jar and the assets folder, have them in the same application, and then run the jar and it works :D

#Credits
This project was created, designed and written by Joshua Bradbury
Thanks go to:
- s0ulan (https://github.com/soulan) - For helping fix the font rendering
